package wtf.diablo.client.module.impl.combat.killaura.rotations.management;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import wtf.diablo.client.event.impl.player.motion.MotionEvent;
import wtf.diablo.client.module.impl.combat.killaura.KillAuraModule;

public final class KillAuraRotationsHandler {
    private static final Minecraft mc = Minecraft.getMinecraft();

    public static float getPitchChangeToEntityHitPoint(Entity entity, KillAuraModule.EnumHitPoint hitpoint) {
        double deltaX = entity.posX - mc.thePlayer.posX;
        double deltaZ = entity.posZ - mc.thePlayer.posZ;
        double offset = 1.6;
        switch (hitpoint) { //leaked diablo feature (crazy)
            case HEAD:
                break;
            case CHEST:
                offset = 2;
                break;
            case LOWER_CHEST:
                offset = 2.275;
                break;
            case STOMACH:
                offset = 2.475;
                break;
            case COCK:
                offset = 2.655;
                break;
            case LEGS:
                offset = 2.825;
                break;
            case FEET:
                offset = 3.1;
                break;
            case TOES:
                offset = 3.225;
                break;

        }
        double deltaY = entity.posY - offset + entity.getEyeHeight() - mc.thePlayer.posY;
        double distanceXZ = MathHelper.sqrt_double(deltaX * deltaX + deltaZ * deltaZ);
        double pitchToEntity = -Math.toDegrees(Math.atan(deltaY / distanceXZ));
        return Double.isNaN(mc.thePlayer.rotationPitch - pitchToEntity) ? 0.0f : -MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationPitch - (float) pitchToEntity);
    }

    public static float getYawChangeToEntity(Entity entity) {
        double deltaX = entity.posX - mc.thePlayer.posX;
        double deltaZ = entity.posZ - mc.thePlayer.posZ;
        double yawToEntity;
        double v = Math.toDegrees(Math.atan(deltaZ / deltaX));
        if (deltaZ < 0.0 && deltaX < 0.0) {
            yawToEntity = 90.0 + v;
        } else if (deltaZ < 0.0 && deltaX > 0.0) {
            yawToEntity = -90.0 + v;
        } else {
            yawToEntity = Math.toDegrees(-Math.atan(deltaX / deltaZ));
        }
        return Double.isNaN(mc.thePlayer.rotationYaw - yawToEntity) ? 0.0f : MathHelper.wrapAngleTo180_float(-(mc.thePlayer.rotationYaw - (float) yawToEntity));
    }

    public static float[] getAnglesFromHitPoint(Entity e, KillAuraModule.EnumHitPoint hitpoint) {
        return new float[]{getYawChangeToEntity(e) + mc.thePlayer.rotationYaw, getPitchChangeToEntityHitPoint(e, hitpoint) + mc.thePlayer.rotationPitch};
    }

    public static void rotate(final MotionEvent update, final float yaw, final float pitch) {
        update.setYaw(yaw);
        update.setPitch(pitch);
        mc.thePlayer.rotationYawHead = yaw;
        mc.thePlayer.rotationPitchHead = pitch;
        mc.thePlayer.renderYawOffset = yaw;

    }

}
