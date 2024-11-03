package net.silentclient.client.keybinds;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.silentclient.client.mixin.SilentClientTweaker;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;

public class KeyBindManager {
//    public KeyBinding CLICKGUI = new KeyBinding("Silent Client - Mod Menu", Keyboard.KEY_RSHIFT, "Silent Client");
//    public KeyBinding PERSPECTIVE = new KeyBinding("Silent Client - Perspective", Keyboard.KEY_LMENU, "Silent Client");
//    public KeyBinding ZOOM = new KeyBinding("Silent Client - Zoom", Keyboard.KEY_C, "Silent Client");

    public KeyBindManager(GameSettings gameSettings) {
        if(SilentClientTweaker.hasOptifine) {
            try {
                this.unregisterKeybind(gameSettings, (KeyBinding) GameSettings.class.getField("ofKeyBindZoom").get(Minecraft.getMinecraft().gameSettings));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

//        this.registerKeyBind(gameSettings, CLICKGUI);
//        this.registerKeyBind(gameSettings, PERSPECTIVE);
//        this.registerKeyBind(gameSettings, ZOOM);
    }

    public void registerKeyBind(GameSettings gameSettings, KeyBinding key) {
        gameSettings.keyBindings = ArrayUtils.add(gameSettings.keyBindings, key);
    }

    public void unregisterKeybind(GameSettings gameSettings, KeyBinding key) {
        if (Arrays.asList(gameSettings.keyBindings).contains(key)) {
            gameSettings.keyBindings = ArrayUtils.remove(gameSettings.keyBindings, Arrays.asList(gameSettings.keyBindings).indexOf(key));
            key.setKeyCode(0);
        }
    }
}
