package dev.excellent.client.module.impl.player;

import dev.excellent.api.event.impl.player.AttackEvent;
import dev.excellent.api.event.impl.player.UpdateEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.client.script.ScriptConstructor;
import dev.excellent.impl.value.impl.ModeValue;
import dev.excellent.impl.value.mode.SubMode;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.PlayerEntity;

@ModuleInfo(name = "Sprint Reset", description = "Автоматически сбрасывает спринт (подходит для легит юзеров).", category = Category.PLAYER)
public class SprintReset extends Module {
    private final ModeValue mode = new ModeValue("Mode", this)
            .add(
                    new SubMode("WTap"),
                    new SubMode("STap"),
                    new SubMode("ShiftTap"),
                    new SubMode("Blatant")
            ).setDefault("WTap");

    @Override
    public String getSuffix() {
        return mode.getValue().getName();
    }

    private final ScriptConstructor script = ScriptConstructor.create();
    private int attacks = 0;
    private int ticks = 0;
    private final Listener<AttackEvent> onAttack = event -> {
        if (event.getTarget() != null) {
            if (event.getTarget() instanceof PlayerEntity) {
                attacks++;
            }
        }
    };
    private final Listener<UpdateEvent> onUpdate = event -> {
        script.update();
        ticks++;
        if (ticks >= 10) {
            if (attacks > 0) {
                handleSprintReset();
                ticks = 0;
                attacks = 0;
            }
        }
    };

    private void handleSprintReset() {
        switch (mode.getValue().getName()) {
            case "WTap":
                if (mc.gameSettings.keyBindForward.isKeyDown()) {
                    KeyBinding.setKeyBindState(mc.gameSettings.keyBindSprint.getKeyCode(), false);
                    KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), false);
                }
                break;
            case "STap":
                if (!mc.gameSettings.keyBindBack.isKeyDown()) {
                    KeyBinding.setKeyBindState(mc.gameSettings.keyBindBack.getKeyCode(), true);
                }
                break;
            case "ShiftTap":
                if (!mc.gameSettings.keyBindSneak.isKeyDown()) {
                    KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), true);
                }
                break;
            case "Blatant":
                if (mc.gameSettings.keyBindForward.isKeyDown()) {
                    mc.player.setSprinting(false);
                }
        }
        script.cleanup().addStep(20, this::handleSprintResetCompletion);
    }

    private void handleSprintResetCompletion() {
        switch (mode.getValue().getName()) {
            case "WTap":
                activateForwardKey();
                break;
            case "STap":
                deactivateBackKey();
                break;
            case "ShiftTap":
                deactivateSneakKey();
                break;
            case "Blatant":
                mc.player.setSprinting(true);
        }
    }

    private void activateForwardKey() {
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), true);
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindSprint.getKeyCode(), true);
    }

    private void deactivateBackKey() {
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindBack.getKeyCode(), false);
    }

    private void deactivateSneakKey() {
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), false);
    }

}
