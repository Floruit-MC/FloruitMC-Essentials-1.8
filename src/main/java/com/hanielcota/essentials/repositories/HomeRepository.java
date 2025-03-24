package com.hanielcota.essentials.repositories;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.hanielcota.essentials.models.Home;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class HomeRepository {
    private final Connection connection;
    private final Cache<UUID, List<Home>> cache;

    public HomeRepository(String dbPath) throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
        this.cache = Caffeine.newBuilder()
                .expireAfterAccess(10, TimeUnit.MINUTES)
                .maximumSize(1000)
                .build();
        createTable();
    }

    private void createTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS homes (" +
                "player_id TEXT NOT NULL, " +
                "name TEXT NOT NULL, " +
                "world TEXT NOT NULL, " +
                "x REAL NOT NULL, " +
                "y REAL NOT NULL, " +
                "z REAL NOT NULL, " +
                "yaw REAL NOT NULL, " +
                "pitch REAL NOT NULL, " +
                "PRIMARY KEY (player_id, name)" +
                ")";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        }
    }

    public void saveHome(Home home) throws SQLException {
        String sql = "INSERT OR REPLACE INTO homes (player_id, name, world, x, y, z, yaw, pitch) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            Location loc = home.getLocation();
            pstmt.setString(1, home.getPlayerId());
            pstmt.setString(2, home.getName());
            pstmt.setString(3, loc.getWorld().getName());
            pstmt.setDouble(4, loc.getX());
            pstmt.setDouble(5, loc.getY());
            pstmt.setDouble(6, loc.getZ());
            pstmt.setFloat(7, loc.getYaw());
            pstmt.setFloat(8, loc.getPitch());
            pstmt.executeUpdate();
        }

        UUID playerId = UUID.fromString(home.getPlayerId());
        List<Home> playerHomes = fetchOrGetCachedHomes(playerId);
        updateHomeInList(playerHomes, home);
        cache.put(playerId, playerHomes);
    }

    public Home findHome(UUID playerId, String name) throws SQLException {
        List<Home> playerHomes = fetchOrGetCachedHomes(playerId);
        return findHomeInList(playerHomes, name);
    }

    public List<Home> findHomesByPlayer(UUID playerId) throws SQLException {
        return Collections.unmodifiableList(fetchOrGetCachedHomes(playerId));
    }

    public void deleteHome(UUID playerId, String name) throws SQLException {
        String sql = "DELETE FROM homes WHERE player_id = ? AND name = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, playerId.toString());
            pstmt.setString(2, name);
            pstmt.executeUpdate();
        }

        List<Home> playerHomes = cache.getIfPresent(playerId);
        if (playerHomes == null) {
            return;
        }

        playerHomes.removeIf(home -> home.getName().equalsIgnoreCase(name));
        cache.put(playerId, playerHomes);
    }

    public int countHomes(UUID playerId) throws SQLException {
        List<Home> playerHomes = fetchOrGetCachedHomes(playerId);
        return playerHomes.size();
    }

    public void close() throws SQLException {
        if (connection == null) {
            return;
        }

        connection.close();
        cache.invalidateAll();
    }

    private List<Home> fetchOrGetCachedHomes(UUID playerId) throws SQLException {
        List<Home> cachedHomes = cache.getIfPresent(playerId);
        if (cachedHomes != null) {
            return cachedHomes;
        }

        List<Home> homes = loadHomesFromDatabase(playerId);
        cache.put(playerId, homes);
        return homes;
    }

    private List<Home> loadHomesFromDatabase(UUID playerId) throws SQLException {
        List<Home> homes = new ArrayList<>();
        String sql = "SELECT player_id, name, world, x, y, z, yaw, pitch " +
                "FROM homes WHERE player_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, playerId.toString());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                World world = Bukkit.getWorld(rs.getString("world"));
                if (world == null) {
                    continue;
                }

                Home home = new Home(
                        rs.getString("player_id"),
                        rs.getString("name"),
                        new Location(world,
                                rs.getDouble("x"),
                                rs.getDouble("y"),
                                rs.getDouble("z"),
                                rs.getFloat("yaw"),
                                rs.getFloat("pitch")
                        )
                );
                homes.add(home);
            }
        }
        return homes;
    }

    private Home findHomeInList(List<Home> homes, String name) {
        for (Home home : homes) {
            if (!home.getName().equalsIgnoreCase(name)) {
                continue;
            }
            return home;
        }
        return null;
    }

    private void updateHomeInList(List<Home> homes, Home newHome) {
        Home existingHome = findHomeInList(homes, newHome.getName());
        if (existingHome != null) {
            homes.remove(existingHome);
        }
        homes.add(newHome);
    }
}