package gq.vapu.czfclient.Module.Modules.Blatant;

import gq.vapu.czfclient.API.EventHandler;
import gq.vapu.czfclient.API.Events.World.EventMove;
import gq.vapu.czfclient.Manager.ModuleManager;
import gq.vapu.czfclient.Module.Module;
import gq.vapu.czfclient.Module.ModuleType;
import gq.vapu.czfclient.Util.BlockUtils;
import net.minecraft.potion.Potion;
import net.minecraft.util.MovementInput;

public class Strafe extends Module {
    public Strafe() {
        super("Strafe", new String[]{"Strafe"}, ModuleType.Blatant);
        // super("PacketMotior", new String[]{"PacketMotior"}, ModuleType.Misc);
    }

    /*
     * public static void SmartMove(EventMove event) { double speed = 0.0; double
     * moveSpeed = Math.max(getBaseMoveSpeed(), speed); MovementInput var10000 =
     * mc.thePlayer.movementInput; float forward = MovementInput.moveForward;
     * var10000 = mc.thePlayer.movementInput; float strafe =
     * MovementInput.moveStrafe; float yaw = mc.thePlayer.rotationYaw; if
     * (forward == 0.0F && strafe == 0.0F) { event.x = 0.0D; event.z = 0.0D; } else
     * if (forward != 0.0F) { if (strafe >= 1.0F) { yaw += (float)(forward > 0.0F ?
     * -45 : 45); strafe = 0.0F; } else if (strafe <= -1.0F) { yaw +=
     * (float)(forward > 0.0F ? 45 : -45); strafe = 0.0F; }
     *
     * if (forward > 0.0F) { forward = 1.0F; } else if (forward < 0.0F) { forward =
     * -1.0F; } }
     *
     * double mx = Math.cos(Math.toRadians((double)(yaw + 90.0F))); double mz =
     * Math.sin(Math.toRadians((double)(yaw + 90.0F))); event.x = (double)forward *
     * moveSpeed * mx + (double)strafe * moveSpeed * mz; event.z = (double)forward *
     * moveSpeed * mz - (double)strafe * moveSpeed * mx;
     * mc.thePlayer.stepHeight = 0.6F; if (forward == 0.0F && strafe == 0.0F)
     * { event.x = 0.0D; event.z = 0.0D; }
     *
     * }
     */
    public static double getBaseMoveSpeed() {
        double baseSpeed;
        if (BlockUtils.isInLiquid()) {
            baseSpeed = 0.1D;
        } else {
            baseSpeed = 0.2873D;
        }

        if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            int amplifier = mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
            baseSpeed *= 1.0D + 0.2D * (double) (amplifier + 1);
        }

        return baseSpeed;
    }

    public void onEnable() {
        super.onEnable();
    }

    public void onDisable() {
        super.onDisable();
    }

    @EventHandler
    private void onMove(EventMove event) {
        if (!ModuleManager.getModuleByClass(Speed.class).isEnabled()) {
            double moveSpeed = Math.max(getBaseMoveSpeed(), getBaseMoveSpeed());
            MovementInput var10000 = mc.thePlayer.movementInput;
            float forward = MovementInput.moveForward;
            var10000 = mc.thePlayer.movementInput;
            float strafe = MovementInput.moveStrafe;
            float yaw = mc.thePlayer.rotationYaw;
            if (forward == 0.0F && strafe == 0.0F) {
                EventMove.x = 0.0D;
                EventMove.z = 0.0D;
            } else if (forward != 0.0F) {
                if (strafe >= 1.0F) {
                    yaw += (float) (forward > 0.0F ? -45 : 45);
                    strafe = 0.0F;
                } else if (strafe <= -1.0F) {
                    yaw += (float) (forward > 0.0F ? 45 : -45);
                    strafe = 0.0F;
                }

                if (forward > 0.0F) {
                    forward = 1.0F;
                } else if (forward < 0.0F) {
                    forward = -1.0F;
                }
            }

            double mx = Math.cos(Math.toRadians(yaw + 90.0F));
            double mz = Math.sin(Math.toRadians(yaw + 90.0F));
            EventMove.x = (double) forward * moveSpeed * mx + (double) strafe * moveSpeed * mz;
            EventMove.z = (double) forward * moveSpeed * mz - (double) strafe * moveSpeed * mx;
            mc.thePlayer.stepHeight = 0.6F;
            if (forward == 0.0F && strafe == 0.0F) {
                EventMove.x = 0.0D;
                EventMove.z = 0.0D;
            }

        }
    }
}
