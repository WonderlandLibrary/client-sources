package wtf.diablo.client.module.impl.combat;

import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import wtf.diablo.client.core.impl.Diablo;
import wtf.diablo.client.event.impl.client.renderering.Render3DEvent;
import wtf.diablo.client.event.impl.player.motion.MoveEvent;
import wtf.diablo.client.module.api.data.AbstractModule;
import wtf.diablo.client.module.api.data.ModuleCategoryEnum;
import wtf.diablo.client.module.api.data.ModuleMetaData;
import wtf.diablo.client.module.impl.combat.killaura.KillAuraModule;
import wtf.diablo.client.module.impl.movement.FlightModule;
import wtf.diablo.client.module.impl.movement.speed.SpeedModule;
import wtf.diablo.client.setting.impl.BooleanSetting;
import wtf.diablo.client.setting.impl.ColorSetting;
import wtf.diablo.client.setting.impl.NumberSetting;
import wtf.diablo.client.util.mc.entity.EntityUtil;
import wtf.diablo.client.util.mc.player.movement.MovementUtil;
import wtf.diablo.client.util.render.RenderUtil;

import java.awt.*;

@ModuleMetaData(
        name = "Target Strafe",
        description = "Automatically strafes around the target",
        category = ModuleCategoryEnum.COMBAT
)
public final class TargetStrafeModule extends AbstractModule {
    private final NumberSetting<Double> range = new NumberSetting<>("Range", 3D, 0.25, 6D, 0.05D);
    private final BooleanSetting thirdPerson = new BooleanSetting("Third Person", true);
    private final ColorSetting rangeColor = new ColorSetting("Range Color", new Color(255, 255, 255, 50));
    private final BooleanSetting inAir = new BooleanSetting("In Air", true);
    private final BooleanSetting onGround = new BooleanSetting("On Ground", true);


    private int direction = 1;
    private boolean setView = false;

    public TargetStrafeModule() {
        this.registerSettings(
                this.range,
                this.thirdPerson,
                this.rangeColor,
                this.inAir,
                this.onGround
        );
    }

    @EventHandler
    private final Listener<MoveEvent> moveEventListener = event -> {
        this.setSuffix(range.getValue().toString());
        if ((Diablo.getInstance().getModuleRepository().getModuleInstance(SpeedModule.class).isEnabled() || Diablo.getInstance().getModuleRepository().getModuleInstance(FlightModule.class).isEnabled())) {
            if (Diablo.getInstance().getModuleRepository().getModuleInstance(KillAuraModule.class).getTarget() != null) {

                if (!(this.inAir.getValue() && this.onGround.getValue())) {
                    if (this.inAir.getValue() && mc.thePlayer.onGround) {
                        return;
                    }

                    if (this.onGround.getValue() && !mc.thePlayer.onGround) {
                        return;
                    }
                }

                if (mc.thePlayer.isCollidedHorizontally || !MovementUtil.isBlockUnder()) {
                    direction = -direction;
                } else {
                    if (mc.gameSettings.keyBindLeft.isPressed()) direction = 1;
                    if (mc.gameSettings.keyBindRight.isPressed()) direction = -1;
                }

                setSpeedVinceNewAutismo(event, MovementUtil.getBaseMoveSpeed() * 0.95F);

                return;
            }
        }

        if (setView) {
            mc.gameSettings.thirdPersonView = 0;
            setView = false;
        }
    };

    @EventHandler
    private final Listener<Render3DEvent> render3DEventListener = event -> {
        RenderUtil.drawCircle(event, range.getValue(), rangeColor.getValue());
    };

    public void setSpeedVinceNewAutismo(MoveEvent moveEvent, double speed) {
        double forward = mc.thePlayer.getDistanceToEntity(Diablo.getInstance().getModuleRepository().getModuleInstance(KillAuraModule.class).getTarget()) <= range.getValue() ? 0 : 1;
        double strafe = direction;
        double yaw = EntityUtil.getYaw(Diablo.getInstance().getModuleRepository().getModuleInstance(KillAuraModule.class).getTarget().getPositionVector());

        if (forward == 0 && strafe == 0) {
            moveEvent.setX(0);
            moveEvent.setZ(0);
        } else {
            if (thirdPerson.getValue() && !setView) {
                mc.gameSettings.thirdPersonView = 1;
                setView = true;
            }

            if (forward != 0) {
                if (strafe > 0) {
                    yaw += ((forward > 0) ? -45 : 45);
                } else if (strafe < 0) {
                    yaw += ((forward > 0) ? 45 : -45);
                }
                strafe = 0;
                if (forward > 0) {
                    forward = 1.0;
                } else if (forward < 0) {
                    forward = -1.0;
                }
            }
        }

        moveEvent.setX(forward * speed * -Math.sin(Math.toRadians(yaw)) + strafe * speed * Math.cos(Math.toRadians(yaw)));
        moveEvent.setZ(forward * speed * Math.cos(Math.toRadians(yaw)) - strafe * speed * -Math.sin(Math.toRadians(yaw)));
    }

}
