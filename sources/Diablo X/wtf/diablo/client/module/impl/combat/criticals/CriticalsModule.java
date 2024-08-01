package wtf.diablo.client.module.impl.combat.criticals;

import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import wtf.diablo.client.core.impl.Diablo;
import wtf.diablo.client.event.impl.player.motion.MotionEvent;
import wtf.diablo.client.module.api.data.AbstractModule;
import wtf.diablo.client.module.api.data.ModuleCategoryEnum;
import wtf.diablo.client.module.api.data.ModuleMetaData;
import wtf.diablo.client.module.impl.combat.killaura.KillAuraModule;
import wtf.diablo.client.setting.impl.ModeSetting;

import java.util.Objects;

@ModuleMetaData(
        name = "Criticals",
        description = "Automatically crits players",
        category = ModuleCategoryEnum.COMBAT
)
public final class CriticalsModule extends AbstractModule {

    private final ModeSetting<EnumCriticalsMode> enumCriticalsModeModeSetting = new ModeSetting<>("Criticals Mode", EnumCriticalsMode.WATCHDOG);

    public CriticalsModule()
    {
        this.registerSettings(
                this.enumCriticalsModeModeSetting
        );
    }

    @EventHandler
    private final Listener<MotionEvent> motionEventListener = event ->
    {
        this.setSuffix(enumCriticalsModeModeSetting.getValue().getName());
        final KillAuraModule killAuraModule = Diablo.getInstance().getModuleRepository().getModuleInstance(KillAuraModule.class);

        if (killAuraModule.getTarget() == null) {
            return;
        }

        if (!(mc.thePlayer.onGround && mc.thePlayer.isCollidedVertically))
            return;

        if (Objects.requireNonNull(enumCriticalsModeModeSetting.getValue()) == EnumCriticalsMode.WATCHDOG) {
            //event.setY(event.getPosY() + 0.0625);
            event.setOnGround(false);
        }
    };
}