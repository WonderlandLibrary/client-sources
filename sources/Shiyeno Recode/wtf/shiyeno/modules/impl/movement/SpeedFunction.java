package wtf.shiyeno.modules.impl.movement;

import net.minecraft.network.IPacket;
import net.minecraft.network.play.client.CConfirmTransactionPacket;
import net.minecraft.network.play.server.SPlayerPositionLookPacket;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import wtf.shiyeno.events.Event;
import wtf.shiyeno.events.impl.packet.EventPacket;
import wtf.shiyeno.events.impl.player.EventMove;
import wtf.shiyeno.events.impl.player.EventUpdate;
import wtf.shiyeno.modules.Function;
import wtf.shiyeno.modules.FunctionAnnotation;
import wtf.shiyeno.modules.Type;
import wtf.shiyeno.modules.settings.Setting;
import wtf.shiyeno.modules.settings.imp.ModeSetting;
import wtf.shiyeno.util.IMinecraft;
import wtf.shiyeno.util.misc.TimerUtil;
import wtf.shiyeno.util.movement.MoveUtil;
import wtf.shiyeno.util.movement.MoveUtil.StrafeMovement;

@FunctionAnnotation(
        name = "Speed",
        type = Type.Movement
)
public class SpeedFunction extends Function {
    public final ModeSetting spdMode = new ModeSetting("Режим", "Matrix", new String[]{"Matrix", "Timer", "Sunrise Place", "Grim old", "IntaveAc"});
    public boolean boosting;
    public TimerUtil timerUtil = new TimerUtil();

    public SpeedFunction() {
        this.addSettings(new Setting[]{this.spdMode});
    }

    public void onEnable() {
        super.onEnable();
        this.timerUtil.reset();
        this.boosting = false;
    }

    public void onEvent(Event var1) {
        if (var1 instanceof EventPacket var2) {
            this.handlePacketEvent(var2);
        } else if (var1 instanceof EventMove) {
            this.handleEventMove((EventMove)var1);
        } else if (var1 instanceof EventUpdate) {
            this.handleEventUpdate((EventUpdate)var1);
        }

    }

    public void handleEventMove(EventMove var1) {
        if (this.spdMode.is("Matrix") && !mc.player.isOnGround() && mc.player.fallDistance >= 0.5F && var1.toGround()) {
            this.applyMatrixSpeed();
        }

    }

    public void handleEventUpdate(EventUpdate var1) {
        switch (this.spdMode.get()) {
            case "Matrix":
                if (mc.player.isOnGround() && MoveUtil.isMoving()) {
                    mc.player.jump();
                }
                break;
            case "Grim old":
                this.handleRWMode();
                break;
            case "Timer":
                this.handleTimerMode();
                break;
            case "Sunrise Place":
                this.handleSunriseDamageMode();
                break;
            case "IntaveAc":
                this.handleRW2Mode();
        }

    }

    public void handlePacketEvent(EventPacket var1) {
        if (this.spdMode.is("Grim old")) {
            IPacket var3 = var1.getPacket();
            if (var3 instanceof CConfirmTransactionPacket) {
                CConfirmTransactionPacket var2 = (CConfirmTransactionPacket)var3;
                var1.setCancel(false);
            }

            var3 = var1.getPacket();
            if (var3 instanceof SPlayerPositionLookPacket) {
                SPlayerPositionLookPacket var4 = (SPlayerPositionLookPacket)var3;
                mc.player.func_242277_a(new Vector3d(var4.getX(), var4.getY(), var4.getZ()));
                mc.player.setRawPosition(var4.getX(), var4.getY(), var4.getZ());
                this.toggle();
            }
        }

    }

    public void handleRWMode() {
        if (this.timerUtil.hasTimeElapsed(1150L)) {
            this.boosting = true;
        }

        if (this.timerUtil.hasTimeElapsed(7000L)) {
            this.boosting = true;
            this.timerUtil.reset();
        }

        if (this.boosting) {
            if (mc.player.isOnGround() && !mc.gameSettings.keyBindJump.pressed) {
                mc.player.jump();
            }

            mc.timer.timerSpeed = mc.player.ticksExisted % 4 == 0 ? 1.5F : 1.2F;
        } else {
            mc.timer.timerSpeed = 0.07F;
        }

    }

    public void applyMatrixSpeed() {
        double var1 = 2.0;
        Vector3d var10000 = mc.player.motion;
        var10000.x *= var1;
        var10000 = mc.player.motion;
        var10000.z *= var1;
        StrafeMovement.oldSpeed *= var1;
    }

    public void handleTimerMode() {
        if (!mc.player.isInWater() && !mc.player.isInLava() && !mc.player.isOnLadder()) {
            float var1 = 1.0F;
            if (mc.player.fallDistance <= 0.1F) {
                var1 = 1.34F;
            }

            if (mc.player.fallDistance > 1.0F) {
                var1 = 0.6F;
            }

            if (MoveUtil.isMoving()) {
                mc.timer.timerSpeed = 1.0F;
                if (mc.player.isOnGround()) {
                    if (!mc.gameSettings.keyBindJump.isKeyDown()) {
                        mc.player.jump();
                    }
                } else {
                    mc.timer.timerSpeed = var1;
                }
            } else {
                mc.timer.timerSpeed = 1.0F;
            }

        }
    }

    public void handleSunriseDamageMode() {
        double var1 = (double)MoveUtil.getDirection();
        if (MoveUtil.isMoving()) {
            if (mc.player.isOnGround()) {
                this.applySunriseGroundMotion(var1);
            } else if (mc.player.isInWater()) {
                this.applySunriseWaterMotion(var1);
            } else if (!mc.player.isOnGround()) {
                this.applySunriseAirMotion(var1);
            } else {
                this.applySunriseDefaultMotion(var1);
            }
        }

    }

    public void applySunriseGroundMotion(double var1) {
        mc.player.addVelocity((double)(-MathHelper.sin(var1)) * 9.5 / 24.5, 0.0, (double)MathHelper.cos(var1) * 9.5 / 24.5);
        MoveUtil.setMotion((double)MoveUtil.getMotion());
    }

    public void applySunriseWaterMotion(double var1) {
        mc.player.addVelocity((double)(-MathHelper.sin(var1)) * 9.5 / 24.5, 0.0, (double)MathHelper.cos(var1) * 9.5 / 24.5);
        MoveUtil.setMotion((double)MoveUtil.getMotion());
    }

    public void applySunriseAirMotion(double var1) {
        mc.player.addVelocity((double)(-MathHelper.sin(var1)) * 0.11 / 24.5, 0.0, (double)MathHelper.cos(var1) * 0.11 / 24.5);
        MoveUtil.setMotion((double)MoveUtil.getMotion());
    }

    public void applySunriseDefaultMotion(double var1) {
        mc.player.addVelocity((double)(-MathHelper.sin(var1)) * 0.005 * (double)MoveUtil.getMotion(), 0.0, (double)MathHelper.cos(var1) * 0.005 * (double)MoveUtil.getMotion());
        MoveUtil.setMotion((double)MoveUtil.getMotion());
    }

    public void handleRW2Mode() {
        mc.timer.timerSpeed = 1.0F;
        if (MoveUtil.isMoving() && !IMinecraft.mc.player.isInWater() && !IMinecraft.mc.player.isInLava() && !IMinecraft.mc.player.isOnLadder() && !mc.player.isRidingHorse()) {
            if ((double)mc.player.fallDistance < 0.1) {
                mc.timer.timerSpeed = 0.9F;
            } else if ((double)mc.player.fallDistance > 0.7) {
                mc.timer.timerSpeed = 2.0F;
            }

        }
    }

    public void onDisable() {
        mc.timer.timerSpeed = 1.0F;
        super.onDisable();
    }
}