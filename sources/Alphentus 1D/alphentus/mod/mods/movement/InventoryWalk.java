package alphentus.mod.mods.movement;

import alphentus.event.Event;
import alphentus.event.Type;
import alphentus.mod.Mod;
import alphentus.mod.ModCategory;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiMainMenu;
import org.lwjgl.input.Keyboard;

public class InventoryWalk extends Mod {

    public InventoryWalk() {
        super("InventoryWalk", Keyboard.KEY_NONE, true, ModCategory.MOVEMENT);
    }

    @EventTarget
    public void event(Event event) {
        if (!getState() || event.getType() != Type.TICKUPDATE)
            return;

        if (mc.currentScreen instanceof GuiChat || mc.currentScreen instanceof GuiMainMenu)
            return;

        mc.gameSettings.keyBindForward.pressed = mc.gameSettings.isKeyDown(mc.gameSettings.keyBindForward);
        mc.gameSettings.keyBindBack.pressed = mc.gameSettings.isKeyDown(mc.gameSettings.keyBindBack);
        mc.gameSettings.keyBindRight.pressed = mc.gameSettings.isKeyDown(mc.gameSettings.keyBindRight);
        mc.gameSettings.keyBindLeft.pressed = mc.gameSettings.isKeyDown(mc.gameSettings.keyBindLeft);
        mc.gameSettings.keyBindJump.pressed = mc.gameSettings.isKeyDown(mc.gameSettings.keyBindJump);
        mc.gameSettings.keyBindSprint.pressed = mc.gameSettings.isKeyDown(mc.gameSettings.keyBindSprint);
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
