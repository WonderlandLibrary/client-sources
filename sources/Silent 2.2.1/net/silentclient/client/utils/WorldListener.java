package net.silentclient.client.utils;

import net.minecraft.entity.player.EntityPlayer;

import java.util.HashMap;

public class WorldListener {
    public static HashMap<String, EntityPlayer> players = new HashMap<>();

    public static void onPlayerJoin(EntityPlayer entityPlayer) {
        onPlayerLeave(entityPlayer);
        players.put(entityPlayer.getName().toLowerCase(), entityPlayer);
    }

    public static void onPlayerLeave(EntityPlayer entityPlayer) {
        players.remove(entityPlayer.getName().toLowerCase());
    }

    public static void onWorldSwitch() {
        players.clear();
    }
}
