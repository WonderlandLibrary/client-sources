package tk.rektsky.Module.Player;

import tk.rektsky.Module.*;
import tk.rektsky.Event.*;
import tk.rektsky.Event.Events.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.settings.*;

public class InventoryMove extends Module
{
    public KeyBinding[] affectedKeys;
    
    public InventoryMove() {
        super("InventoryMove", "Allowed you to move when you opened your inventory", 0, Category.PLAYER);
        this.affectedKeys = new KeyBinding[] { this.mc.gameSettings.keyBindForward, this.mc.gameSettings.keyBindBack, this.mc.gameSettings.keyBindRight, this.mc.gameSettings.keyBindLeft, this.mc.gameSettings.keyBindJump, this.mc.gameSettings.keyBindSprint };
    }
    
    @Override
    public void onEvent(final Event event) {
        if (!(event instanceof WorldTickEvent)) {
            return;
        }
        final Class[] notAffectedScreens = { GuiChat.class, GuiCommandBlock.class };
        if (this.mc.currentScreen == null) {
            return;
        }
        for (final Class screen : notAffectedScreens) {
            if (screen.getName().equals(this.mc.currentScreen.getClass().getName())) {
                return;
            }
        }
        for (final KeyBinding key : this.affectedKeys) {
            key.pressed = GameSettings.isKeyDown(key);
        }
    }
}
