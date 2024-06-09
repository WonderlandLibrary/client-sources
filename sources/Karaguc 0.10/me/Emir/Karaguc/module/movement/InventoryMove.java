package me.Emir.Karaguc.module.movement;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import de.Hero.settings.Setting;
import me.Emir.Karaguc.Karaguc;
import me.Emir.Karaguc.event.EventTarget;
import me.Emir.Karaguc.event.events.EventUpdate;
import me.Emir.Karaguc.module.Category;
import me.Emir.Karaguc.module.Module;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.settings.KeyBinding;

public class InventoryMove extends Module {
    public InventoryMove() {
        super("InvMove", Keyboard.KEY_NONE, Category.MOVEMENT);
    }

    @Override
    public void setup() {
        ArrayList<String> options = new ArrayList<>();
        //TODO: Add more InventoryMove modes
        //options.add("Open-Inv");
        //options.add("Hypixel");
        options.add("AAC");
        //options.add("AACP");
        //options.add("GommeHD");
        Karaguc.instance.settingsManager.rSetting(new Setting("InventoryMove Mode", this, "AAC", options));
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if ((this.mc.currentScreen != null) && (!(this.mc.currentScreen instanceof GuiChat))) {
            KeyBinding[] key = {this.mc.gameSettings.keyBindForward, this.mc.gameSettings.keyBindBack,
                    this.mc.gameSettings.keyBindLeft, this.mc.gameSettings.keyBindRight, this.mc.gameSettings.keyBindSprint, this.mc.gameSettings.keyBindJump};
            KeyBinding[] arrayOfKeyBinding1;
            int j = (arrayOfKeyBinding1 = key).length;
            for (int i = 0; i < j; i++) {
                KeyBinding b = arrayOfKeyBinding1[i];
                KeyBinding.setKeyBindState(b.getKeyCode(), Keyboard.isKeyDown(b.getKeyCode()));
            }
        }
    }
}
