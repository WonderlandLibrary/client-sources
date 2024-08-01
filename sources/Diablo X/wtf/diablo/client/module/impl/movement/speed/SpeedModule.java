package wtf.diablo.client.module.impl.movement.speed;

import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import wtf.diablo.client.core.impl.Diablo;
import wtf.diablo.client.event.api.EventTypeEnum;
import wtf.diablo.client.event.impl.player.motion.MotionEvent;
import wtf.diablo.client.module.api.data.AbstractModule;
import wtf.diablo.client.module.api.data.ModuleCategoryEnum;
import wtf.diablo.client.module.api.data.ModuleMetaData;
import wtf.diablo.client.module.impl.combat.killaura.KillAuraModule;
import wtf.diablo.client.setting.impl.ModeSetting;
import wtf.diablo.client.util.mc.player.movement.MovementUtil;

@ModuleMetaData(
        name = "Speed",
        description = "Allows you to move faster",
        category = ModuleCategoryEnum.MOVEMENT
)
public final class SpeedModule extends AbstractModule {
    private final ModeSetting<EnumSpeedModuleMode> enumSpeedModuleModeModeSetting = new ModeSetting<>("Mode", EnumSpeedModuleMode.VANILLA);

    public SpeedModule()
    {
        this.registerSettings(enumSpeedModuleModeModeSetting);
    }

    private int friction = 0, ticks;

    @EventHandler
    private final Listener<MotionEvent> motionEventListener = event ->
    {
        this.setSuffix(enumSpeedModuleModeModeSetting.getValue().getName());

        switch (enumSpeedModuleModeModeSetting.getValue()) {
            case WATCHDOG:
                if (mc.thePlayer.onGround) {
                    friction = 0;
                }

                if (event.getEventType() == EventTypeEnum.PRE) {
                    if (mc.thePlayer.onGround && MovementUtil.isMoving()) {
                        mc.thePlayer.motionY = 0.42F;
                        MovementUtil.setMotion(MovementUtil.getBaseMoveSpeed() * 1.6414F - (friction * 0.1252345F));
                        friction++;
                    }
                }
                break;
            case WATCHDOG_EXPERIMENTAL:
                if (mc.thePlayer.onGround) {
                    friction = 0;
                }

                if (Diablo.getInstance().getModuleRepository().getModuleInstance(KillAuraModule.class).getTarget() != null && event.getEventType() == EventTypeEnum.PRE) {
                    MovementUtil.strafe();
                }

                if (event.getEventType() == EventTypeEnum.PRE) {
                    if (mc.thePlayer.onGround && MovementUtil.isMoving()) {
                        mc.thePlayer.motionY = 0.42F;
                        MovementUtil.setMotion(MovementUtil.getBaseMoveSpeed() * 1.6414F - (friction * 0.1252345F));
                        friction++;
                    }
                }
                break;
            case VANILLA:
                MovementUtil.setMotion(MovementUtil.getPlayerSpeed());
                if (mc.thePlayer.onGround && MovementUtil.isMoving()) {
                    mc.thePlayer.jump();
                }
                break;
            case VERUS:
                if (event.getEventType() == EventTypeEnum.PRE) {
                    if (mc.thePlayer.onGround && MovementUtil.isMoving()) {
                        mc.thePlayer.jump();
                        //event.setY(0.42F);
                        MovementUtil.setMotion(0.445F);
                    } else {
                        MovementUtil.setMotion(0.32F);
                    }

                    if (!(mc.thePlayer.moveForward > 0)) {
                        MovementUtil.setMotion(0.3225);
                    }
                }
                break;
            case STRAFE:
                MovementUtil.strafe();
                if (this.mc.thePlayer.onGround && MovementUtil.isMoving()) {
                    this.mc.thePlayer.jump();
                }
        }
    };
}