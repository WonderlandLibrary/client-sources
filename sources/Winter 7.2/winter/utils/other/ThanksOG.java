/*
 * Decompiled with CFR 0_122.
 */
package winter.utils.other;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ThanksOG {
    public static float[] getBlockRotations(int x2, int y2, int z2, EnumFacing facing) {
        Minecraft mc2 = Minecraft.getMinecraft();
        EntitySnowball temp = new EntitySnowball(mc2.theWorld);
        temp.posX = (double)x2 + 0.5;
        temp.posY = (double)y2 + 0.5;
        temp.posZ = (double)z2 + 0.5;
        return ThanksOG.getAngles(temp);
    }

    public static float[] getAngles(Entity e2) {
        Minecraft mc2 = Minecraft.getMinecraft();
        return new float[]{ThanksOG.getYawChangeToEntity(e2) + mc2.thePlayer.rotationYaw, ThanksOG.getPitchChangeToEntity(e2) + mc2.thePlayer.rotationPitch};
    }

    public static float[] getRotations(Entity entity) {
        double diffY;
        if (entity == null) {
            return null;
        }
        double diffX = entity.posX - Minecraft.getMinecraft().thePlayer.posX;
        double diffZ = entity.posZ - Minecraft.getMinecraft().thePlayer.posZ;
        if (entity instanceof EntityLivingBase) {
            EntityLivingBase elb = (EntityLivingBase)entity;
            diffY = elb.posY + ((double)elb.getEyeHeight() - 0.4) - (Minecraft.getMinecraft().thePlayer.posY + (double)Minecraft.getMinecraft().thePlayer.getEyeHeight());
        } else {
            diffY = (entity.boundingBox.minY + entity.boundingBox.maxY) / 2.0 - (Minecraft.getMinecraft().thePlayer.posY + (double)Minecraft.getMinecraft().thePlayer.getEyeHeight());
        }
        double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0 / 3.141592653589793) - 90.0f;
        float pitch = (float)(- Math.atan2(diffY, dist) * 180.0 / 3.141592653589793);
        return new float[]{yaw, pitch};
    }

    public static float getYawChangeToEntity(Entity entity) {
        Minecraft mc2 = Minecraft.getMinecraft();
        double deltaX = entity.posX - mc2.thePlayer.posX;
        double deltaZ = entity.posZ - mc2.thePlayer.posZ;
        double yawToEntity = deltaZ < 0.0 && deltaX < 0.0 ? 90.0 + Math.toDegrees(Math.atan(deltaZ / deltaX)) : (deltaZ < 0.0 && deltaX > 0.0 ? -90.0 + Math.toDegrees(Math.atan(deltaZ / deltaX)) : Math.toDegrees(- Math.atan(deltaX / deltaZ)));
        return MathHelper.wrapAngleTo180_float(- mc2.thePlayer.rotationYaw - (float)yawToEntity);
    }

    public static float getPitchChangeToEntity(Entity entity) {
        Minecraft mc2 = Minecraft.getMinecraft();
        double deltaX = entity.posX - mc2.thePlayer.posX;
        double deltaZ = entity.posZ - mc2.thePlayer.posZ;
        double deltaY = entity.posY - 1.6 + (double)entity.getEyeHeight() - 0.4 - mc2.thePlayer.posY;
        double distanceXZ = MathHelper.sqrt_double(deltaX * deltaX + deltaZ * deltaZ);
        double pitchToEntity = - Math.toDegrees(Math.atan(deltaY / distanceXZ));
        return - MathHelper.wrapAngleTo180_float(mc2.thePlayer.rotationPitch - (float)pitchToEntity);
    }
}

