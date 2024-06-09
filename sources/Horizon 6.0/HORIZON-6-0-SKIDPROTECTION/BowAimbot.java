package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;

@ModInfo(Ø­áŒŠá = Category.COMBAT, Ý = 0, Â = "Shoots at the nearest target.", HorizonCode_Horizon_È = "BowAimbot")
public class BowAimbot extends Mod
{
    @Handler
    public void HorizonCode_Horizon_È(final EventUpdate event) {
        if (event.Ý() == EventUpdate.HorizonCode_Horizon_È.HorizonCode_Horizon_È) {
            final EntityLivingBase targetEntity = this.Å();
            if (this.Â.á.áŒŠá() != null && this.Â.á.áŒŠá().HorizonCode_Horizon_È() instanceof ItemBow && targetEntity != null) {
                final int bowCurrentCharge = this.Â.á.Ø­Æ();
                float bowVelocity = bowCurrentCharge / 20.0f;
                bowVelocity = (bowVelocity * bowVelocity + bowVelocity * 2.0f) / 3.0f;
                if (bowVelocity < 0.1) {
                    return;
                }
                if (bowVelocity > 1.0f) {
                    bowVelocity = 1.0f;
                }
                final double xDistance = targetEntity.ŒÏ - this.Â.á.ŒÏ;
                final double zDistance = targetEntity.Ê - this.Â.á.Ê;
                final double eyeDistance = targetEntity.Çªà¢ + targetEntity.Ðƒáƒ() - (this.Â.á.Çªà¢ + this.Â.á.Ðƒáƒ());
                final double trajectoryXZ = Math.sqrt(xDistance * xDistance + zDistance * zDistance);
                final double eyeTrajectoryXZ = Math.sqrt(trajectoryXZ * trajectoryXZ + eyeDistance * eyeDistance);
                final float yawTrajectory = (float)(Math.atan2(zDistance, xDistance) * 180.0 / 3.141592653589793) - 90.0f;
                final float pitchTrajectory = -this.HorizonCode_Horizon_È((float)trajectoryXZ, (float)eyeDistance, bowVelocity);
                event.HorizonCode_Horizon_È = yawTrajectory;
                event.Â = pitchTrajectory;
            }
        }
    }
    
    public EntityLivingBase Å() {
        EntityLivingBase currentEntity = null;
        double distanceToEntity = 1000.0;
        for (final Object currentObject : this.Â.áŒŠÆ.Â) {
            if (currentObject instanceof Entity) {
                final Entity targetEntity = (Entity)currentObject;
                if (!(targetEntity instanceof EntityPlayer) || targetEntity == this.Â.á || targetEntity.Ø­áŒŠá(this.Â.á) > 140.0f || !this.Â.á.µà(targetEntity) || ((EntityLivingBase)targetEntity).ÇªØ­ > 0 || FriendManager.HorizonCode_Horizon_È.containsKey(targetEntity.v_())) {
                    continue;
                }
                if (currentEntity == null) {
                    currentEntity = (EntityLivingBase)targetEntity;
                }
                final double xDistance = targetEntity.ŒÏ - this.Â.á.ŒÏ;
                final double zDistance = targetEntity.Ê - this.Â.á.Ê;
                final double eyeDistance = this.Â.á.Çªà¢ + this.Â.á.Ðƒáƒ() - targetEntity.Çªà¢;
                final double trajectoryXZ = Math.sqrt(xDistance * xDistance + zDistance * zDistance);
                final float trajectoryaw = (float)(Math.atan2(zDistance, xDistance) * 180.0 / 3.141592653589793) - 90.0f;
                final float trajectoryPitch = (float)(Math.atan2(eyeDistance, trajectoryXZ) * 180.0 / 3.141592653589793);
                final double xAngleDistance = this.HorizonCode_Horizon_È(trajectoryaw, this.Â.á.É % 360.0f);
                final double yAngleDistance = this.HorizonCode_Horizon_È(trajectoryPitch, this.Â.á.áƒ % 360.0f);
                final double entityDistance = Math.sqrt(xAngleDistance * xAngleDistance + yAngleDistance * yAngleDistance);
                if (entityDistance >= distanceToEntity) {
                    continue;
                }
                currentEntity = (EntityLivingBase)targetEntity;
                distanceToEntity = entityDistance;
            }
        }
        return currentEntity;
    }
    
    private float HorizonCode_Horizon_È(final float angleX, final float angleY, final float bowVelocity) {
        final float velocityIncrement = 0.006f;
        final float squareRootBow = bowVelocity * bowVelocity * bowVelocity * bowVelocity - velocityIncrement * (velocityIncrement * (angleX * angleX) + 2.0f * angleY * (bowVelocity * bowVelocity));
        return (float)Math.toDegrees(Math.atan((bowVelocity * bowVelocity - Math.sqrt(squareRootBow)) / (velocityIncrement * angleX)));
    }
    
    private float HorizonCode_Horizon_È(final float angle1, final float angle2) {
        float angleToEntity = Math.abs(angle1 - angle2) % 360.0f;
        if (angleToEntity > 180.0f) {
            angleToEntity = 360.0f - angleToEntity;
        }
        return angleToEntity;
    }
}
