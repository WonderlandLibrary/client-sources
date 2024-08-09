package dev.excellent.client.module.impl.movement;

import dev.excellent.api.event.impl.player.UpdateEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.client.module.impl.combat.KillAura;
import dev.excellent.impl.util.pattern.Singleton;
import dev.luvbeeq.baritone.api.BaritoneUtils;

@ModuleInfo(name = "Sprint", description = "Активирует бег с спринтом.", category = Category.MOVEMENT)
public class Sprint extends Module {
    public static Singleton<Sprint> singleton = Singleton.create(() -> Module.link(Sprint.class));
    private final Listener<UpdateEvent> onUpdate = event -> {
        if (mc.player.isSwimming() || mc.currentScreen != null)
            return;

        if (KillAura.singleton.get().isEnabled() && KillAura.singleton.get().getTarget() != null)
            return;

        mc.gameSettings.keyBindSprint.setPressed(!mc.player.isSprinting()
                && mc.player.movementInput.isMovingForward()
                && mc.gameSettings.keyBindForward.isKeyDown()
                && !mc.gameSettings.keyBindSprint.isKeyDown()
                && mc.player.getFoodStats().getFoodLevel() > 6
                && !BaritoneUtils.isActive()
                && mc.player.getRidingEntity() == null
        );
    };

    @Override
    public void onDisable() {
        super.onDisable();
        if (mc.player != null) {
            mc.player.setSprinting(false);
        }
    }

}