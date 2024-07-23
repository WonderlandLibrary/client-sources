package io.github.liticane.monoxide.module.impl.movement;

import io.github.liticane.monoxide.listener.event.minecraft.player.movement.MovePlayerEvent;
import io.github.liticane.monoxide.module.api.Module;
import io.github.liticane.monoxide.module.api.data.ModuleData;
import io.github.liticane.monoxide.module.api.ModuleCategory;
import io.github.liticane.monoxide.util.interfaces.Methods;
import io.github.liticane.monoxide.value.impl.BooleanValue;
import io.github.liticane.monoxide.value.impl.ModeValue;
import io.github.liticane.monoxide.listener.event.minecraft.player.movement.UpdateMotionEvent;
import io.github.liticane.monoxide.listener.radbus.Listen;
import io.github.liticane.monoxide.util.player.movement.MoveUtil;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;

import java.util.function.Supplier;

@ModuleData(name = "Speed", description = "Makes you speedy", category = ModuleCategory.MOVEMENT)
public class SpeedModule extends Module {
    private final ModeValue mode = new ModeValue("Mode", this, new String[]{"BHop", "Vulcan", "Watchdog", "Verus"}),
            vulcanMode = new ModeValue("Vulcan Mode", this, new String[]{"Ground Strafe", "Semi-Strafe"}, new Supplier[]{() -> mode.is("Vulcan")}),
            watchdogMode = new ModeValue("Watchdog Mode", this, new String[]{"Ground Strafe", "Semi-Strafe"}, new Supplier[]{() -> mode.is("Watchdog")}),
            verusMode = new ModeValue("Watchdog Mode", this, new String[]{"Strafe", "Low-Hop"}, new Supplier[]{() -> mode.is("Verus")});
    private final BooleanValue fastStop = new BooleanValue("Fast Stop", this, true);

    @Override
    public String getSuffix() {
        return mode.getValue();
    }

    @Listen
    public void onMotionUpdateEvent(UpdateMotionEvent event) {
        switch (mode.getValue()) {
            case "BHop":
                if (mc.thePlayer.onGround) {
                    mc.thePlayer.jump();
                }
                MoveUtil.strafe(0.75F);
                break;
            case "Vulcan":
                switch (vulcanMode.getValue()) {
                    case "Ground Strafe": {
                        if (mc.thePlayer.onGround) {
                            mc.thePlayer.jump();
                            MoveUtil.strafe(0.43);
                        }
                    }
                    break;

                    case "Semi-Strafe": {
                        if (mc.thePlayer.onGround) {
                            mc.thePlayer.jump();
                            MoveUtil.strafe(0.43);
                        }
                        if (mc.thePlayer.offGroundTicks < 4) {
                            MoveUtil.strafe();
                        }
                    }

                    break;
                }
                break;
            case "Watchdog":
                switch (watchdogMode.getValue()) {
                    case "Ground Strafe": {
                        if (mc.thePlayer.onGround) {
                            mc.thePlayer.jump();
                            MoveUtil.strafe(0.485);
                        }
                        if (mc.thePlayer.hurtTime > 6) {
                            mc.thePlayer.motionX *= 1.007;
                            mc.thePlayer.motionZ *= 1.007;
                        }
                    }
                    break;

                    case "Semi-Strafe": {
                        if (mc.thePlayer.onGround) {
                            mc.thePlayer.jump();
                            MoveUtil.strafe(0.485);
                        }
                        if (mc.thePlayer.offGroundTicks <= 3) {
                            MoveUtil.strafe();
                        }
                        if (mc.thePlayer.hurtTime > 6) {
                            mc.thePlayer.motionX *= 1.007;
                            mc.thePlayer.motionZ *= 1.007;
                        }
                    }
                    break;
                }
                break;
            case "Verus":
                switch (verusMode.getValue()) {
                    case "Strafe":
                        if (mc.thePlayer.onGround) {
                            mc.thePlayer.jump();
                            MoveUtil.strafe(0.48);
                        }
                        if (Math.abs(mc.thePlayer.motionY) < 0.01) {
                            mc.thePlayer.motionY = (mc.thePlayer.motionY - 0.08) * 0.98F;
                        }
                        if (mc.thePlayer.hurtTime != 9) {
                            MoveUtil.strafe();
                        } else if (mc.thePlayer.isAirBorne) {
                            MoveUtil.strafe(0.65);
                        }
                        break;
                    case "Low-Hop":
                        if (mc.thePlayer.onGround) {
                            mc.thePlayer.jump();
                            MoveUtil.strafe(0.475);
                        } else {
                            if (mc.thePlayer.offGroundTicks > 2) {
                                mc.thePlayer.motionY -= 0.2;
                            }
                        }
                        MoveUtil.strafe(Math.max(MoveUtil.getSpeed(), mc.thePlayer.moveForward > 0 ? 0.33 : 0.3) + MoveUtil.getSpeedBoost(1));
                        break;
                }
                break;
        }
    }

    @Override
    public void onDisable() {
        if (fastStop.isEnabled())
            MoveUtil.stop();
        super.onDisable();
    }
}
