package net.silentclient.client.mods.player;

import net.silentclient.client.Client;
import net.silentclient.client.event.EventTarget;
import net.silentclient.client.event.impl.KeyEvent;
import net.silentclient.client.mods.Mod;
import net.silentclient.client.mods.ModCategory;
import org.lwjgl.input.Keyboard;

public class EmoteMod extends Mod {
    public static boolean active = false;

    public EmoteMod() {
        super("Emotes", ModCategory.MODS, null);
    }

    @Override
    public void setup() {
        super.setup();
        this.addKeybindSetting("Keybind", this, Keyboard.KEY_B);
    }

    @EventTarget
    public void onKey(KeyEvent event) {
        if(event.getKey() == Client.getInstance().getSettingsManager().getSettingByName(this, "Keybind").getKeybind()) {
            active = !active;
            if(active) {
            }
        }
    }
}
