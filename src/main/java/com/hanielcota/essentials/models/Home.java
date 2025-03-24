package com.hanielcota.essentials.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Location;

@Getter
@AllArgsConstructor
public class Home {

    private final String playerId;
    private final String name;
    private final Location location;

}