//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package src.Wiksi.functions.impl.movement;

import com.google.common.eventbus.Subscribe;
import src.Wiksi.events.EventMotion;
import src.Wiksi.events.EventPacket;
import src.Wiksi.events.EventUpdate;
import src.Wiksi.functions.api.Category;
import src.Wiksi.functions.api.Function;
import src.Wiksi.functions.api.FunctionRegister;
import src.Wiksi.functions.settings.Setting;
import src.Wiksi.functions.settings.impl.ModeSetting;
import src.Wiksi.utils.player.MoveUtils;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.client.CConfirmTransactionPacket;
import net.minecraft.network.play.server.SPlayerPositionLookPacket;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

@FunctionRegister(
        name = "Speed",
        type = Category.Movement
)
public class Speed extends Function {
    private final ModeSetting spdMode = new ModeSetting("Режим", "Matrix", new String[]{"Matrix", "Timer", "Sunrise", "Really World", "InfinityHVH"});
    public boolean boosting;

    public Speed() {
        this.addSettings(new Setting[]{this.spdMode});
    }

    private void handleEventMove(EventMotion eventMove) {
        if (this.spdMode.is("Matrix") && !mc.player.isOnGround() && mc.player.fallDistance >= 0.5F && eventMove.isOnGround()) {
            this.applyMatrixSpeed();
        }

    }

    @Subscribe
    private void handleEventUpdate(EventUpdate eventUpdate) {
        switch ((String)this.spdMode.get()) {
            case "Matrix":
                if (mc.player.isOnGround() && MoveUtils.isMoving()) {
                    mc.player.jump();
                }
                break;
            case "Really World":
                this.handleRWMode();
                break;
            case "Timer":
                this.handleTimerMode();
        }

    }

    private void handlePacketEvent(EventPacket e) {
        if (this.spdMode.is("Really World")) {
            IPacket var3 = e.getPacket();
            if (var3 instanceof CConfirmTransactionPacket) {
            }

            var3 = e.getPacket();
            if (var3 instanceof SPlayerPositionLookPacket) {
                SPlayerPositionLookPacket p = (SPlayerPositionLookPacket)var3;
                mc.player.func_242277_a(new Vector3d(p.getX(), p.getY(), p.getZ()));
                mc.player.setRawPosition(p.getX(), p.getY(), p.getZ());
                this.toggle();
            }
        }

    }

    private void handleRWMode() {
        if (this.boosting) {
            if (mc.player.isOnGround() && !mc.gameSettings.keyBindJump.pressed) {
                mc.player.jump();
            }

            mc.timer.timerSpeed = mc.player.ticksExisted % 2 == 0 ? 1.5F : 1.2F;
        } else {
            mc.timer.timerSpeed = 0.05F;
        }

    }

    private void applyMatrixSpeed() {
        double speed = 2.0;
        Vector3d var10000 = mc.player.motion;
        var10000.x *= speed;
        var10000 = mc.player.motion;
        var10000.z *= speed;
    }

    private void handleTimerMode() {
        if (!mc.player.isInWater() && !mc.player.isInLava() && !mc.player.isOnLadder()) {
            float timerValue = 1.0F;
            if (mc.player.fallDistance <= 0.1F) {
                timerValue = 1.34F;
            }

            if (mc.player.fallDistance > 1.0F) {
                timerValue = 0.6F;
            }

            if (MoveUtils.isMoving()) {
                mc.timer.timerSpeed = 1.0F;
                if (mc.player.isOnGround()) {
                    if (!mc.gameSettings.keyBindJump.isKeyDown()) {
                        mc.player.jump();
                    }
                } else {
                    mc.timer.timerSpeed = timerValue;
                }
            } else {
                mc.timer.timerSpeed = 1.0F;
            }
        }

    }

    private void handleSunriseDamageMode(double radians) {
        radians = 4.0;
        if (MoveUtils.isMoving()) {
            if (mc.player.isOnGround()) {
                this.applySunriseGroundMotion(radians);
            } else if (mc.player.isInWater()) {
                this.applySunriseWaterMotion(radians);
            } else if (!mc.player.isOnGround()) {
                this.applySunriseAirMotion(radians);
            } else {
                this.applySunriseDefaultMotion(radians);
            }
        }

    }

    private void applySunriseGroundMotion(double radians) {
        mc.player.addVelocity((double)(-MathHelper.sin((float)radians)) * 9.5 / 24.5, 0.0, (double)MathHelper.cos((float)radians) * 9.5 / 24.5);
        MoveUtils.setMotion(MoveUtils.getMotion());
    }

    private void applySunriseWaterMotion(double radians) {
        mc.player.addVelocity((double)(-MathHelper.sin((float)radians)) * 9.5 / 24.5, 0.0, (double)MathHelper.cos((float)radians) * 9.5 / 24.5);
        MoveUtils.setMotion(MoveUtils.getMotion());
    }

    private void applySunriseAirMotion(double radians) {
        mc.player.addVelocity((double)(-MathHelper.sin((float)radians)) * 0.11 / 24.5, 0.0, (double)MathHelper.cos((float)radians) * 0.11 / 24.5);
        MoveUtils.setMotion(MoveUtils.getMotion());
    }

    private void applySunriseDefaultMotion(double radians) {
        mc.player.addVelocity((double)(-MathHelper.sin((float)radians)) * 0.005 * MoveUtils.getMotion(), 0.0, (double)MathHelper.cos((float)radians) * 0.005 * MoveUtils.getMotion());
        MoveUtils.setMotion(MoveUtils.getMotion());
    }

    public void onDisable() {
        mc.timer.timerSpeed = 1.0F;
        super.onDisable();
    }
}
