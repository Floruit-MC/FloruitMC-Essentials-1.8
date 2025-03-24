package com.hanielcota.essentials.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

@UtilityClass
public class TitleUtils {

    private static final String NMS_VERSION = "v1_8_R3"; // Ajuste se necessário (ex.: v1_8_R1, v1_8_R2)
    private static final String NMS_PACKAGE = "net.minecraft.server." + NMS_VERSION;
    private static final String CRAFTBUKKIT_PACKAGE = "org.bukkit.craftbukkit." + NMS_VERSION;

    // Classes e métodos pré-carregados
    private static final Class<?> PACKET_PLAY_OUT_TITLE_CLASS;
    private static final Class<?> I_CHAT_BASE_COMPONENT_CLASS;
    private static final Class<?> CHAT_SERIALIZER_CLASS;
    private static final Class<?> ENUM_TITLE_ACTION_CLASS;

    private static final Constructor<?> TITLE_PACKET_CONSTRUCTOR;
    private static final Method CHAT_SERIALIZER_METHOD;
    private static final Method GET_HANDLE_METHOD;
    private static final Method SEND_PACKET_METHOD;
    private static final Field PLAYER_CONNECTION_FIELD;

    private static final Object TITLE_ACTION;
    private static final Object SUBTITLE_ACTION;

    static {
        try {
            PACKET_PLAY_OUT_TITLE_CLASS = Class.forName(NMS_PACKAGE + ".PacketPlayOutTitle");
            I_CHAT_BASE_COMPONENT_CLASS = Class.forName(NMS_PACKAGE + ".IChatBaseComponent");
            CHAT_SERIALIZER_CLASS = Class.forName(NMS_PACKAGE + ".IChatBaseComponent$ChatSerializer");
            ENUM_TITLE_ACTION_CLASS = Class.forName(NMS_PACKAGE + ".PacketPlayOutTitle$EnumTitleAction");

            TITLE_PACKET_CONSTRUCTOR = PACKET_PLAY_OUT_TITLE_CLASS.getConstructor(ENUM_TITLE_ACTION_CLASS, I_CHAT_BASE_COMPONENT_CLASS, int.class, int.class, int.class);
            CHAT_SERIALIZER_METHOD = CHAT_SERIALIZER_CLASS.getMethod("a", String.class);
            GET_HANDLE_METHOD = Class.forName(CRAFTBUKKIT_PACKAGE + ".entity.CraftPlayer").getMethod("getHandle");
            SEND_PACKET_METHOD = Class.forName(NMS_PACKAGE + ".PlayerConnection").getMethod("sendPacket", Class.forName(NMS_PACKAGE + ".Packet"));
            PLAYER_CONNECTION_FIELD = Class.forName(NMS_PACKAGE + ".EntityPlayer").getField("playerConnection");

            TITLE_ACTION = ENUM_TITLE_ACTION_CLASS.getField("TITLE").get(null);
            SUBTITLE_ACTION = ENUM_TITLE_ACTION_CLASS.getField("SUBTITLE").get(null);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao inicializar TitleUtils: " + e.getMessage(), e);
        }
    }

    /**
     * Envia um título e subtítulo para todos os jogadores online com duração padrão.
     *
     * @param title O título a ser exibido.
     * @param subtitle O subtítulo a ser exibido.
     */
    public void sendTitleToAll(String title, String subtitle) {
        sendTitleToAll(title, subtitle, 10, 70, 20);
    }

    /**
     * Envia um título e subtítulo para todos os jogadores online com duração personalizada.
     *
     * @param title O título a ser exibido.
     * @param subtitle O subtítulo a ser exibido.
     * @param fadeIn Tempo de entrada (em ticks).
     * @param stay Tempo de permanência (em ticks).
     * @param fadeOut Tempo de saída (em ticks).
     */
    public void sendTitleToAll(String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            sendTitle(player, title, subtitle, fadeIn, stay, fadeOut);
        }
    }

    /**
     * Envia um título e subtítulo para um jogador específico com duração padrão.
     *
     * @param player O jogador que receberá o título.
     * @param title O título a ser exibido.
     * @param subtitle O subtítulo a ser exibido.
     */
    public void sendTitle(Player player, String title, String subtitle) {
        sendTitle(player, title, subtitle, 10, 70, 20);
    }

    /**
     * Envia um título e subtítulo para um jogador específico com duração personalizada.
     *
     * @param player O jogador que receberá o título.
     * @param title O título a ser exibido.
     * @param subtitle O subtítulo a ser exibido.
     * @param fadeIn Tempo de entrada (em ticks).
     * @param stay Tempo de permanência (em ticks).
     * @param fadeOut Tempo de saída (em ticks).
     */
    public void sendTitle(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        if (player == null) return;
        if (!player.isOnline()) return;

        try {
            Object craftPlayer = GET_HANDLE_METHOD.invoke(player);
            Object playerConnection = PLAYER_CONNECTION_FIELD.get(craftPlayer);

            Object titleComponent = CHAT_SERIALIZER_METHOD.invoke(null, "{\"text\":\"" + title + "\"}");
            Object subtitleComponent = CHAT_SERIALIZER_METHOD.invoke(null, "{\"text\":\"" + subtitle + "\"}");

            Object titlePacket = TITLE_PACKET_CONSTRUCTOR.newInstance(TITLE_ACTION, titleComponent, fadeIn, stay, fadeOut);
            Object subtitlePacket = TITLE_PACKET_CONSTRUCTOR.newInstance(SUBTITLE_ACTION, subtitleComponent, fadeIn, stay, fadeOut);

            SEND_PACKET_METHOD.invoke(playerConnection, titlePacket);
            SEND_PACKET_METHOD.invoke(playerConnection, subtitlePacket);
        } catch (Exception e) {
            Bukkit.getLogger().severe("Erro ao enviar título NMS: " + e.getMessage());
        }
    }
}