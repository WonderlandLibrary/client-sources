package digital.rbq.module.implement.Movement;

import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.client.gui.GuiChat;
import org.lwjgl.input.Keyboard;
import digital.rbq.event.UIRenderEvent;
import digital.rbq.module.Category;
import digital.rbq.module.Module;
import digital.rbq.module.value.BooleanValue;

public class InvMove extends Module {

    public static BooleanValue invJump = new BooleanValue("InvMove", "Jump", true);
    public static BooleanValue invSneak = new BooleanValue("InvMove", "Sneak", true);

    public InvMove() {
        super("InvMove", Category.Movement, false);
    }

    @EventTarget
    public void onRender2D(UIRenderEvent event) {
        if (mc.currentScreen != null && !(mc.currentScreen instanceof GuiChat)) {
            mc.gameSettings.keyBindForward.setPressed(Keyboard.isKeyDown(mc.gameSettings.keyBindForward.getKeyCode()));
            mc.gameSettings.keyBindBack.setPressed(Keyboard.isKeyDown(mc.gameSettings.keyBindBack.getKeyCode()));
            mc.gameSettings.keyBindLeft.setPressed(Keyboard.isKeyDown(mc.gameSettings.keyBindLeft.getKeyCode()));
            mc.gameSettings.keyBindRight.setPressed(Keyboard.isKeyDown(mc.gameSettings.keyBindRight.getKeyCode()));
            if (invJump.getValue())
                mc.gameSettings.keyBindJump.setPressed(Keyboard.isKeyDown(mc.gameSettings.keyBindJump.getKeyCode()));
            if (invSneak.getValue())
                mc.gameSettings.keyBindSneak.setPressed(Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.getKeyCode()));
        }
    }
}
