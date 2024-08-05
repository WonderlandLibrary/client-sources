package fr.dog.module.impl.movement;

import fr.dog.event.annotations.SubscribeEvent;
import fr.dog.event.impl.player.PlayerTickEvent;
import fr.dog.module.Module;
import fr.dog.module.ModuleCategory;
import fr.dog.property.impl.ModeProperty;
import net.minecraft.client.gui.GuiChat;
import org.lwjglx.input.Keyboard;

public class InvMove extends Module {
    private final ModeProperty mode = ModeProperty.newInstance("Mode", new String[]{"Watchdog Slow", "Normal"}, "Normal");

    public InvMove() {
        super("InvMove", ModuleCategory.MOVEMENT);
        this.registerProperties(mode);
    }

    @SubscribeEvent
    public void onUpdate(PlayerTickEvent event) {
        if (mc.currentScreen instanceof GuiChat) {
            return;
        }

        updateKeyBindings();

        if ("Watchdog Slow".equals(mode.getValue()) && mc.currentScreen != null) {
            mc.thePlayer.motionX *= 0.72;
            mc.thePlayer.motionZ *= 0.72;
        }
    }

    private void updateKeyBindings() {
        mc.gameSettings.keyBindForward.pressed = Keyboard.isKeyDown(mc.gameSettings.keyBindForward.getKeyCode());
        mc.gameSettings.keyBindBack.pressed = Keyboard.isKeyDown(mc.gameSettings.keyBindBack.getKeyCode());
        mc.gameSettings.keyBindLeft.pressed = Keyboard.isKeyDown(mc.gameSettings.keyBindLeft.getKeyCode());
        mc.gameSettings.keyBindRight.pressed = Keyboard.isKeyDown(mc.gameSettings.keyBindRight.getKeyCode());

        if (mc.currentScreen != null && "Watchdog Slow".equals(mode.getValue())) {
            mc.gameSettings.keyBindJump.pressed = false;
        } else {
            mc.gameSettings.keyBindJump.pressed = Keyboard.isKeyDown(mc.gameSettings.keyBindJump.getKeyCode());
        }
    }
}
