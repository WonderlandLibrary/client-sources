package net.silentclient.client.emotes;

import net.minecraft.client.Minecraft;
import net.silentclient.client.Client;
import net.silentclient.client.emotes.config.EmotesConfig;
import net.silentclient.client.emotes.config.EmotesConfigType;
import net.silentclient.client.emotes.socket.EmoteSocket;
import net.silentclient.client.event.EventManager;
import net.silentclient.client.event.impl.KeyEvent;
import net.silentclient.client.mods.Mod;
import net.silentclient.client.mods.ModCategory;
import net.silentclient.client.mods.Setting;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public class EmotesMod extends Mod {
    public static boolean sending = false;

    public EmotesMod() {
        super("Emotes", ModCategory.SETTINGS, "silentclient/emotes/icons/default.png");
    }

    @Override
    public void setup() {
        this.addBooleanSetting("Emotes", this, true);
        this.addKeybindSetting("Emote Wheel Keybind", this, Keyboard.KEY_B);
        this.addBooleanSetting("Infinite Emotes", this, false);
        this.addBooleanSetting("Enable Running in Emotes", this, false);

        ArrayList<String> perspectives = new ArrayList<>();
        perspectives.add("Second Person");
        perspectives.add("Third Person");

        this.addModeSetting("Default Emote Perspective", this, "Second Person", perspectives);

        try {
            EmotesConfig.init();
        } catch (Exception err) {
            Client.logger.catching(err);
        }
    }

    @Override
    public void onEnable() {
        super.onEnable();
        if(Client.getInstance().getSettingsManager().getSettingByClass(EmotesMod.class, "Emotes").getValBoolean()) {
            EmoteSocket.get().connect();
        } else {
            EmoteSocket.get().disconnect();
        }
    }

    public static void onClick(KeyEvent event) {
        if(!Client.getInstance().getSettingsManager().getSettingByClass(EmotesMod.class, "Emotes").getValBoolean()) {
            return;
        }
        if(Minecraft.getMinecraft().thePlayer != null && Minecraft.getMinecraft().theWorld != null && Minecraft.getMinecraft().currentScreen == null && !sending) {
            EmotesConfigType.Bind bind = EmotesConfig.getBinds().get(event.getKey());
            if(bind != null) {
                EmotesMod.sending = true;
                (new Thread("EMOTES BIND:" + bind.emoteId) {
                    public void run() {
                        EmoteSocket.get().startEmote(bind.emoteId);
                        try {
                            Thread.sleep(2000L);
                        } catch (InterruptedException e) {
                            Client.logger.catching(e);
                        }
                        EmotesMod.sending = false;
                    }
                }).start();
            }
        }
    }

    @Override
    public void onChangeSettingValue(Setting setting) {
        super.onChangeSettingValue(setting);
        if(setting.getName().equals("Emotes")) {
            if(setting.getValBoolean()) {
                EmoteSocket.get().connect();
                EventManager.register(this);
            } else {
                EmoteSocket.get().disconnect();
                EventManager.unregister(this);
            }
        }
    }

    public static int getEmotePerspective() {
        if(Client.getInstance().getSettingsManager().getSettingByClass(EmotesMod.class, "Default Emote Perspective").getValString().equalsIgnoreCase("second person")) {
            return 2;
        } else {
            return 3;
        }
    }
}
