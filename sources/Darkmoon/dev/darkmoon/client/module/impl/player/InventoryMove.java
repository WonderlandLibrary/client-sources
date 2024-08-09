package dev.darkmoon.client.module.impl.player;

import com.darkmagician6.eventapi.EventTarget;
import dev.darkmoon.client.module.setting.impl.BooleanSetting;
import dev.darkmoon.client.event.player.EventUpdate;
import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.inventory.GuiEditSign;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

@ModuleAnnotation(name = "InventoryMove", category = Category.PLAYER)
public class InventoryMove extends Module {
    public static BooleanSetting safe = new BooleanSetting("Safe", false);

    @EventTarget
    public void onUpdate(EventUpdate event) {
        KeyBinding[] keys = {mc.gameSettings.keyBindForward, mc.gameSettings.keyBindBack,
                mc.gameSettings.keyBindLeft, mc.gameSettings.keyBindRight, mc.gameSettings.keyBindJump,
                mc.gameSettings.keyBindSprint};

        if (mc.currentScreen instanceof GuiChat || mc.currentScreen instanceof GuiEditSign)
            return;

        for (KeyBinding keyBinding : keys) {
            keyBinding.pressed = Keyboard.isKeyDown(keyBinding.getKeyCode());
        }
    }
}
