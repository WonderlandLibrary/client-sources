package fr.dog.module.impl.movement;


import fr.dog.event.annotations.SubscribeEvent;
import fr.dog.event.impl.player.PlayerTickEvent;
import fr.dog.module.Module;
import fr.dog.module.ModuleCategory;
import net.minecraft.client.settings.KeyBinding;



public class SaveMoveKey extends Module {
    private boolean wasInventoryOpen = false;

    public SaveMoveKey() {
        super("SaveMoveKey", ModuleCategory.MOVEMENT);
    }

    @SubscribeEvent
    private void onPlayerTick(PlayerTickEvent event) {
        if (mc.currentScreen != null) {
            wasInventoryOpen = true;
        } else {
            if (wasInventoryOpen) {
                mc.addScheduledTask(() -> {
                    KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), org.lwjglx.input.Keyboard.isKeyDown(mc.gameSettings.keyBindForward.getKeyCode()));
                    KeyBinding.setKeyBindState(mc.gameSettings.keyBindBack.getKeyCode(), org.lwjglx.input.Keyboard.isKeyDown(mc.gameSettings.keyBindBack.getKeyCode()));
                    KeyBinding.setKeyBindState(mc.gameSettings.keyBindLeft.getKeyCode(), org.lwjglx.input.Keyboard.isKeyDown(mc.gameSettings.keyBindLeft.getKeyCode()));
                    KeyBinding.setKeyBindState(mc.gameSettings.keyBindRight.getKeyCode(), org.lwjglx.input.Keyboard.isKeyDown(mc.gameSettings.keyBindRight.getKeyCode()));
                    KeyBinding.setKeyBindState(mc.gameSettings.keyBindSprint.getKeyCode(), org.lwjglx.input.Keyboard.isKeyDown(mc.gameSettings.keyBindSprint.getKeyCode()));
                    KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), org.lwjglx.input.Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.getKeyCode()));
                });
                wasInventoryOpen = false;
            }
        }
    }
}
