package net.silentclient.client.emotes;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.silentclient.client.emotes.emoticons.Emote;
import net.silentclient.client.utils.WorldListener;

public class EmoteManager {
    public static void sendEmote(String name, int i) {
        play(name, i);
    }

    public static void play(String name, int i) {
        if (Minecraft.getMinecraft().theWorld != null) {
            String s = PlayerModelManager.get().map.get(i);
            EntityPlayer entityPlayer = EmoteManager.getPlayerEntityByName(name);
            if (entityPlayer != null && s != null) {
                Emote emote = PlayerModelManager.get().registry.get(s);
                if (EmoteControllerManager.controllers.get(entityPlayer.getName()) != null) {
                    EmoteControllerManager.controllers.get(entityPlayer.getName()).setEmote(entityPlayer, emote);
                }
            }
        }
    }

    public static void stop(String name) {
        if (Minecraft.getMinecraft().theWorld != null) {
            EntityPlayer entityPlayer = EmoteManager.getPlayerEntityByName(name);
            if (entityPlayer != null) {
                if (EmoteControllerManager.controllers.get(entityPlayer.getName()) != null) {
                    EmoteControllerManager.controllers.get(entityPlayer.getName()).resetEmote();
                }
            }
        }
    }

    public static EntityPlayer getPlayerEntityByName(String name)
    {
        return WorldListener.players.get(name.toLowerCase());
    }
}
