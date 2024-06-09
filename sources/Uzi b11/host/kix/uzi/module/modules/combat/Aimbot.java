package host.kix.uzi.module.modules.combat;

import com.darkmagician6.eventapi.SubscribeEvent;
import host.kix.uzi.Uzi;
import host.kix.uzi.events.UpdateEvent;
import host.kix.uzi.module.Module;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;

import java.util.Objects;

/**
 * Created by myche on 4/17/2017.
 */
public class Aimbot extends Module {

    public Aimbot() {
        super("Aimbot", 0, Category.COMBAT);
    }

    @SubscribeEvent
    public void update(UpdateEvent e) {
        final EntityPlayer target = this.getClosestPlayerToCursor();
        if (Objects.nonNull(target)) {
            mc.thePlayer.rotationYaw = mc.thePlayer.rotationYaw + (getYawChange(target) / 8F);
            mc.thePlayer.rotationPitch = mc.thePlayer.rotationPitch + (getPitchChange(target) / 8F);
        }
    }

    public float getPitchChange(final EntityLivingBase entity) {
        final double deltaX = entity.posX - mc.thePlayer.posX;
        final double deltaZ = entity.posZ - mc.thePlayer.posZ;
        final double deltaY = entity.posY - 2.2D + entity.getEyeHeight() - mc.thePlayer.posY;
        final double distanceXZ = MathHelper.sqrt_double(deltaX * deltaX + deltaZ * deltaZ);
        final double pitchToEntity = -Math.toDegrees(Math.atan(deltaY / distanceXZ));
        return -MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationPitch - (float) pitchToEntity);
    }

    public float getYawChange(final EntityLivingBase entity) {
        final double deltaX = entity.posX - mc.thePlayer.posX;
        final double deltaZ = entity.posZ - mc.thePlayer.posZ;
        double yawToEntity;

        if ((deltaZ < 0.0D) && (deltaX < 0.0D)) {
            yawToEntity = 90.0D + Math.toDegrees(Math.atan(deltaZ / deltaX));
        } else {
            if ((deltaZ < 0.0D) && (deltaX > 0.0D)) {
                yawToEntity = -90.0D + Math.toDegrees(Math.atan(deltaZ / deltaX));
            } else {
                yawToEntity = Math.toDegrees(-Math.atan(deltaX / deltaZ));
            }
        }

        return MathHelper.wrapAngleTo180_float(-(mc.thePlayer.rotationYaw - (float) yawToEntity));
    }


    private EntityPlayer getClosestPlayerToCursor() {
        float distance = 20F;
        EntityPlayer tempPlayer = null;
        for (final EntityPlayer player : mc.theWorld.playerEntities) {

            if (isValidEntity(player)) {
                final float yaw = getYawChange(player);
                final float pitch = getPitchChange(player);

                if (yaw > 20F || pitch > 20F) {
                    continue;
                }

                final float currentDistance = (yaw + pitch) / 2F;

                if (currentDistance <= distance) {
                    distance = currentDistance;
                    tempPlayer = player;
                }
            }
        }

        return tempPlayer;
    }

    private boolean isValidEntity(final EntityPlayer player) {
        return Objects.nonNull(player) && player.isEntityAlive() && player.getDistanceToEntity(mc.thePlayer) <= 3.0F &&
                player.ticksExisted > 20 && !player.isInvisibleToPlayer(mc.thePlayer) && !Uzi.getInstance().getFriendManager().get(player.getName()).isPresent();
    }


}
