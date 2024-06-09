package host.kix.uzi.utilities.minecraft;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;

/**
 * Created by myche on 2/25/2017.
 */
public class AimUtil {

    public static float[] getBlockRotations(int x, int y, int z, EnumFacing facing) {
        Minecraft mc = Minecraft.getMinecraft();
        Entity temp = new EntitySnowball(mc.theWorld);
        temp.posX = x + 0.5;
        temp.posY = y + 0.5;
        temp.posZ = z + 0.5;
        return getAngles(temp);
    }

    public static float[] getAngles(Entity e) {
        Minecraft mc = Minecraft.getMinecraft();
        return new float[] { getYawChangeToEntity(e) + mc.thePlayer.rotationYaw, getPitchChangeToEntity(e) + mc.thePlayer.rotationPitch };
    }

    public static float[] getRotations(Location location){
        double diffX = location.getX() + 0.5 - Minecraft.getMinecraft().thePlayer.posX;
        double diffZ = location.getZ() + 0.5 - Minecraft.getMinecraft().thePlayer.posZ;
        double diffY = (location.getY() + 0.5)
                / 2.0D
                - (Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer
                .getEyeHeight());

        double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        float yaw = (float) (Math.atan2(diffZ, diffX) * 180.0D / Math.PI) - 90.0F;
        float pitch = (float) -(Math.atan2(diffY, dist) * 180.0D /  Math.PI);

        return new float[] { yaw, pitch };
    }

    public static float[] getRotations(Entity entity){
        if(entity == null)
            return null;

        double diffX = entity.posX - Minecraft.getMinecraft().thePlayer.posX;
        double diffZ = entity.posZ - Minecraft.getMinecraft().thePlayer.posZ;
        double diffY;
        if((entity instanceof EntityLivingBase)){
            EntityLivingBase elb = (EntityLivingBase) entity;
            diffY = elb.posY
                    + (elb.getEyeHeight() - 0.4)
                    - (Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer
                    .getEyeHeight());
        }else{
            diffY = (entity.boundingBox.minY + entity.boundingBox.maxY)
                    / 2.0D
                    - (Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer
                    .getEyeHeight());
        }
        double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        float yaw = (float) (Math.atan2(diffZ, diffX) * 180.0D / 3.141592653589793D) - 90.0F;
        float pitch = (float) -(Math.atan2(diffY, dist) * 180.0D / 3.141592653589793D);

        return new float[] { yaw, pitch };
    }

    public static float getYawChangeToEntity(Entity entity){
        Minecraft mc = Minecraft.getMinecraft();
        double deltaX = entity.posX - mc.thePlayer.posX;
        double deltaZ = entity.posZ - mc.thePlayer.posZ;
        double yawToEntity;
        if((deltaZ < 0.0D) && (deltaX < 0.0D)){
            yawToEntity = 90.0D + Math.toDegrees(Math.atan(deltaZ / deltaX));
        }else{
            if((deltaZ < 0.0D) && (deltaX > 0.0D)){
                yawToEntity = -90.0D
                        + Math.toDegrees(Math.atan(deltaZ / deltaX));
            }else{
                yawToEntity = Math.toDegrees(-Math.atan(deltaX / deltaZ));
            }
        }

        return MathHelper
                .wrapAngleTo180_float(-(mc.thePlayer.rotationYaw - (float) yawToEntity));
    }

    public static float getPitchChangeToEntity(Entity entity){
        Minecraft mc = Minecraft.getMinecraft();
        double deltaX = entity.posX - mc.thePlayer.posX;
        double deltaZ = entity.posZ - mc.thePlayer.posZ;
        double deltaY = entity.posY - 1.6D + entity.getEyeHeight() - 0.4
                - mc.thePlayer.posY;
        double distanceXZ = MathHelper.sqrt_double(deltaX * deltaX + deltaZ
                * deltaZ);

        double pitchToEntity = -Math.toDegrees(Math.atan(deltaY / distanceXZ));

        return -MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationPitch
                - (float) pitchToEntity);
    }

}
