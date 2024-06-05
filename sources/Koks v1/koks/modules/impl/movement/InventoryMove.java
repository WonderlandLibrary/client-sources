package koks.modules.impl.movement;

import koks.event.Event;
import koks.event.impl.EventUpdate;
import koks.modules.Module;
import net.minecraft.client.gui.GuiChat;
import org.lwjgl.input.Keyboard;

/**
 * @author avox | lmao | kroko
 * @created on 04.09.2020 : 15:57
 */
public class InventoryMove extends Module {

    public InventoryMove() {
        super("InventoryMove", "You can move in your inventory", Category.MOVEMENT);
    }

    @Override
    public void onEvent(Event event) {
        if(event instanceof EventUpdate) {
            if(mc.currentScreen instanceof GuiChat)return;
            mc.gameSettings.keyBindForward.pressed = Keyboard.isKeyDown(mc.gameSettings.keyBindForward.getKeyCode());
            mc.gameSettings.keyBindRight.pressed = Keyboard.isKeyDown(mc.gameSettings.keyBindRight.getKeyCode());
            mc.gameSettings.keyBindLeft.pressed = Keyboard.isKeyDown(mc.gameSettings.keyBindLeft.getKeyCode());
            mc.gameSettings.keyBindBack.pressed = Keyboard.isKeyDown(mc.gameSettings.keyBindBack.getKeyCode());

            mc.gameSettings.keyBindJump.pressed = Keyboard.isKeyDown(mc.gameSettings.keyBindJump.getKeyCode());
        }
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }
}
