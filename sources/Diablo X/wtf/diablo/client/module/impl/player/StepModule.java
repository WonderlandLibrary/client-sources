package wtf.diablo.client.module.impl.player;

import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import wtf.diablo.client.core.impl.Diablo;
import wtf.diablo.client.event.api.EventTypeEnum;
import wtf.diablo.client.event.impl.player.StepConfirmEvent;
import wtf.diablo.client.event.impl.player.motion.MotionEvent;
import wtf.diablo.client.module.api.data.AbstractModule;
import wtf.diablo.client.module.api.data.ModuleCategoryEnum;
import wtf.diablo.client.module.api.data.ModuleMetaData;
import wtf.diablo.client.module.api.management.repository.ModuleRepository;
import wtf.diablo.client.module.impl.movement.FlightModule;
import wtf.diablo.client.module.impl.movement.LongJumpModule;
import wtf.diablo.client.module.impl.movement.speed.SpeedModule;
import wtf.diablo.client.module.impl.player.scaffoldrecode.ScaffoldRecodeModule;
import wtf.diablo.client.setting.api.IMode;
import wtf.diablo.client.setting.impl.ModeSetting;
import wtf.diablo.client.util.mc.player.movement.MovementUtil;

//TODO: Finish step event (step confirm event) and finish Watchdog step
@ModuleMetaData(name = "Step", description = "Changes the default distance a player can step up blocks", category = ModuleCategoryEnum.PLAYER)
public final class StepModule extends AbstractModule {
    private final ModeSetting<StepMode> mode = new ModeSetting<>("Mode", StepMode.WATCHDOG);

    private boolean watchdogToggled;

    public StepModule() {
        this.registerSettings(mode);
    }

    @Override
    protected void onDisable() {
        super.onDisable();
        resetStepHeight();
        watchdogToggled = false;
    }

    @EventHandler
    private final Listener<StepConfirmEvent> stepConfirmEventListener = event -> {
        final double posX = mc.thePlayer.posX;
        final double posY = mc.thePlayer.posY;
        final double posZ = mc.thePlayer.posZ;

        double yChange = mc.thePlayer.getEntityBoundingBox().minY - posY;

        switch (mode.getValue()) {
            case WATCHDOG:
                if (mc.thePlayer.onGround && MovementUtil.isMoving() && yChange == 1.0F) {
                    mc.thePlayer.setPosition(posX, mc.thePlayer.lastTickPosY, posZ);
                    watchdogToggled = true;
                } else {
                    watchdogToggled = false;
                }

                return;
        }
    };

    @EventHandler
    private final Listener<MotionEvent> motionEventListener = event -> {
        if (event.getEventType() != EventTypeEnum.PRE)
            return;

        this.setSuffix(this.mode.getValue().name);

        final ModuleRepository moduleRepository = Diablo.getInstance().getModuleRepository();

        final ScaffoldRecodeModule scaffoldModule = moduleRepository.getModuleInstance(ScaffoldRecodeModule.class);
        final SpeedModule speedModule = moduleRepository.getModuleInstance(SpeedModule.class);
        final FlightModule flightModule = moduleRepository.getModuleInstance(FlightModule.class);
        final LongJumpModule longJumpModule = moduleRepository.getModuleInstance(LongJumpModule.class);

        if (scaffoldModule.isEnabled() || speedModule.isEnabled() || flightModule.isEnabled() || longJumpModule.isEnabled()) {
            resetStepHeight();
            return;
        }

        switch (mode.getValue()) {
            case WATCHDOG:
                if (watchdogToggled) {
                    resetStepHeight();
                } else {
                    mc.thePlayer.stepHeight = 1F;
                }

                if (watchdogToggled && mc.thePlayer.motionY == 0.1647732941679385) {
                    mc.thePlayer.motionY = -0.384;
                    watchdogToggled = false;
                    return;
                }

                if (watchdogToggled && mc.thePlayer.onGround && mc.thePlayer.isCollidedHorizontally) {
                    mc.thePlayer.motionY = 0.42;
                }
                return;
        }

    };

    private void resetStepHeight() {
        mc.thePlayer.stepHeight = 0.6F;
    }

    enum StepMode implements IMode {
        WATCHDOG("Watchdog");

        private final String name;

        StepMode(final String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }
}
