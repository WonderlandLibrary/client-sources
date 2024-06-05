/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.eventbus.Subscribe
 *  org.lwjgl.input.Keyboard
 */
package cc.diablo.module.impl.player;

import cc.diablo.event.impl.UpdateEvent;
import cc.diablo.module.Category;
import cc.diablo.module.Module;
import com.google.common.eventbus.Subscribe;
import java.util.Arrays;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.MovementInput;
import org.lwjgl.input.Keyboard;

public class InventoryMove
extends Module {
    public InventoryMove() {
        super("Inventory Move", "Move while in GUI's", 0, Category.Player);
    }

    @Subscribe
    public void onUpdate(UpdateEvent e) {
        if (InventoryMove.mc.currentScreen != null && e.isPre() && !(InventoryMove.mc.currentScreen instanceof GuiChat)) {
            MovementInput.moveForward = 1.0f;
            if (Keyboard.isKeyDown((int)208)) {
                InventoryMove.mc.thePlayer.rotationPitch += 2.0f;
            }
            if (Keyboard.isKeyDown((int)200)) {
                InventoryMove.mc.thePlayer.rotationPitch -= 2.0f;
            }
            if (Keyboard.isKeyDown((int)205)) {
                InventoryMove.mc.thePlayer.rotationYaw += 2.0f;
            }
            if (Keyboard.isKeyDown((int)203)) {
                InventoryMove.mc.thePlayer.rotationYaw -= 2.0f;
            }
            KeyBinding[] keys = new KeyBinding[]{InventoryMove.mc.gameSettings.keyBindForward, InventoryMove.mc.gameSettings.keyBindBack, InventoryMove.mc.gameSettings.keyBindLeft, InventoryMove.mc.gameSettings.keyBindRight, InventoryMove.mc.gameSettings.keyBindJump};
            Arrays.stream(keys).forEach(key -> KeyBinding.setKeyBindState(key.getKeyCode(), Keyboard.isKeyDown((int)key.getKeyCode())));
        }
    }
}

