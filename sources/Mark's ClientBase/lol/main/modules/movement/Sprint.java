package lol.main.modules.movement;

import lol.base.addons.CategoryAddon;
import lol.base.addons.ModuleAddon;
import lol.base.annotations.ModuleInfo;
import lol.base.radbus.Listen;
import lol.main.events.MotionEvent;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

@ModuleInfo(name = "Sprint", description = "Automatically sprints for you", category = CategoryAddon.MOVEMENT, keyBind = Keyboard.KEY_V)
public class Sprint extends ModuleAddon {

    @Listen
    public void onMotion(MotionEvent event) {
        mc.gameSettings.keyBindSprint.pressed = true;
    }

    public void onDisable() {
        mc.gameSettings.keyBindSprint.pressed = Keyboard.isKeyDown(mc.gameSettings.keyBindSprint.getKeyCode());
    }
}
