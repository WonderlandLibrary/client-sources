package me.napoleon.napoline.modules.movement;

import me.napoleon.napoline.Napoline;
import me.napoleon.napoline.events.EventPreUpdate;
import me.napoleon.napoline.events.EventUpdate;
import me.napoleon.napoline.junk.values.type.Bool;
import me.napoleon.napoline.junk.values.type.Mode;
import me.napoleon.napoline.junk.values.type.Num;
import me.napoleon.napoline.manager.event.EventTarget;
import me.napoleon.napoline.modules.Mod;
import me.napoleon.napoline.modules.ModCategory;
import me.napoleon.napoline.utils.player.PlayerUtil;
import me.napoleon.napoline.utils.timer.TimerUtil;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;

public class AntiFall extends Mod {
    private boolean saveMe;
    private TimerUtil timer = new TimerUtil();
    private Mode<?> mode = new Mode<>("Mode", AntiMode.values(), AntiMode.Position);
    private Bool<Boolean> ov = new Bool<>("OnlyVoid", true);
    private Num<Double> distance = new Num<>("Distance", 10.0, 1.0, 10.0);

    public AntiFall() {
        super("AntiVoid", ModCategory.Movement,"Lag back when you fall to void");
        this.addValues(mode, ov, distance);
    }

    public static boolean isBlockUnder() {
        if (mc.thePlayer.posY < 0.0D) {
            return false;
        }
        for (int off = 0; off < (int) mc.thePlayer.posY + 2; off += 2) {
            AxisAlignedBB bb = mc.thePlayer.boundingBox.offset(0.0D, -off, 0.0D);
            if (!mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, bb).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private double[] getDiff(double moveSpeed) {
        double x = mc.thePlayer.movementInput.moveForward * moveSpeed
                * Math.cos(Math.toRadians(mc.thePlayer.rotationYaw + 90.0f))
                + mc.thePlayer.movementInput.moveStrafe * moveSpeed
                * Math.sin(Math.toRadians(mc.thePlayer.rotationYaw + 90.0f));
        double z = mc.thePlayer.movementInput.moveForward * moveSpeed
                * Math.sin(Math.toRadians(mc.thePlayer.rotationYaw + 90.0f))
                - mc.thePlayer.movementInput.moveStrafe * moveSpeed
                * Math.cos(Math.toRadians(mc.thePlayer.rotationYaw + 90.0f));
        return new double[]{x, z};
    }

    @EventTarget
    private void onMove(EventPreUpdate e) {
        if (Napoline.moduleManager.getModByClass(VoidJump.class).getState())
            return;
        if (mc.thePlayer.fallDistance > (Double) distance.getValue().doubleValue()) {
            if ((!this.ov.getValue() || !isBlockUnder())) {
                if (!this.saveMe) {
                    this.saveMe = true;
                    this.timer.reset();
                }

                if (this.mode.getValue() == AntiMode.Position) {
                    mc.thePlayer.motionY = 0;
                } else if (this.mode.getValue() == AntiMode.Motion) {
                    mc.thePlayer.motionY = 10;
                } else if (this.mode.getValue() == AntiMode.Teleport) {
                    mc.thePlayer.setPosition(mc.thePlayer.posX + 1,
                            mc.thePlayer.posY + distance.getValue(), mc.thePlayer.posZ);
                }
//                mc.thePlayer.fallDistance = 0.0F;
            }
        }
    }

    @EventTarget
    private void onUpdate(EventUpdate e) {
        if ((this.saveMe && this.timer.delay(200.0F)) || mc.thePlayer.isCollidedVertically) {
            this.saveMe = false;
            this.timer.reset();
        }
    }

    enum AntiMode {
        Motion, Position, Teleport
    }
}