package me.napoleon.napoline.modules.movement.speed;

import static me.napoleon.napoline.utils.player.MoveUtils.setSpeed;

import me.napoleon.napoline.Napoline;
import me.napoleon.napoline.events.EventMove;
import me.napoleon.napoline.modules.combat.KillAura;
import me.napoleon.napoline.modules.movement.TargetStrafe;
import me.napoleon.napoline.utils.player.MoveUtils;
import me.napoleon.napoline.utils.player.PlayerUtil;
import net.minecraft.client.Minecraft;


public class Hypixel {
    public Minecraft mc = Minecraft.getMinecraft();
    private double speed;
    public double slow;
    private double distance;
    private double moveSpeed;
    public static int stage;

    public void onEnable() {
    }

    public void onDisable() {

    }

    public void onUpdate() {
    }

    public boolean canZoom() {

        return false;
    }

    public void onMove(EventMove e) {
        if (!PlayerUtil.isInLiquid()) {
            if (canZoom()) {
                moveSpeed = MoveUtils.getBaseMoveSpeed();
            }

            if (mc.thePlayer.onGround && stage == 2) {
//                mc.thePlayer.jump();
                e.setY(mc.thePlayer.motionY = (0.122989873798054674678216975 + MoveUtils.getJumpEffect() * 0.1));
                moveSpeed *= 0.4;
            } else if (stage == 3) {
                double diff = 0.66 * (distance - MoveUtils.getBaseMoveSpeed());
                moveSpeed = distance - diff;
            } else {
                if ((mc.thePlayer.isCollidedVertically) && stage > 0) {
                    stage = mc.thePlayer.moving() ? 1 : 0;
                    mc.timer.timerSpeed = 1.0f;
                }
                moveSpeed = distance - (distance / (mc.thePlayer.ticksExisted % 30 == 0 ? 120 : 130));
            }
            moveSpeed = Math.max(moveSpeed, MoveUtils.getBaseMoveSpeed());
            if (stage > 0) {
                if (KillAura.target == null) {
                    setSpeed(e, (((mc.thePlayer.motionY < 0.35 && mc.thePlayer.motionY > 0.1)) || ((mc.thePlayer.motionY < -0.1 && mc.thePlayer.motionY > -0.2))) ? moveSpeed * 1.2 : moveSpeed);
                } else {
                    setSpeed(e, moveSpeed);
                }
            }
            ++stage;
            TargetStrafe ts = (TargetStrafe) Napoline.moduleManager.getModByClass(TargetStrafe.class);
            if (ts.getState() && KillAura.target != null) {
                ts.strafe(e, moveSpeed);
            }
        }
    }
}
