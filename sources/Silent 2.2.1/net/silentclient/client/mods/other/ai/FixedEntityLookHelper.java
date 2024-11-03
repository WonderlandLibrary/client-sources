package net.silentclient.client.mods.other.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityLookHelper;
import net.minecraft.util.MathHelper;
import net.silentclient.client.mixin.accessors.EntityLookHelperAccessor;
import net.silentclient.client.mixin.ducks.EntityLookHelperExt;

/**
 * Created by Dark on 7/20/2015.
 */
public class FixedEntityLookHelper extends EntityLookHelper
{
    public FixedEntityLookHelper(EntityLiving entity)
    {
        super(entity);
    }

    @Override
    public void onUpdateLook() {
        ((EntityLiving) ((EntityLookHelperExt) this).client$getEntity()).rotationPitch = 0.0f;
        if (((EntityLookHelperAccessor)((Object)this)).isLooking()) {
            ((EntityLookHelperAccessor)((Object)this)).setLooking(false);
            double d0 = ((EntityLookHelperAccessor)((Object)this)).getPosX() - ((EntityLiving) ((EntityLookHelperExt) this).client$getEntity()).posX;
            double d1 = ((EntityLookHelperAccessor)((Object)this)).getPosY() - (((EntityLiving) ((EntityLookHelperExt) this).client$getEntity()).posY + (double)((EntityLiving) ((EntityLookHelperExt) this).client$getEntity()).getEyeHeight());
            double d2 = ((EntityLookHelperAccessor)((Object)this)).getPosZ() - ((EntityLiving) ((EntityLookHelperExt) this).client$getEntity()).posZ;
            double d3 = MathHelper.sqrt_double(d0 * d0 + d2 * d2);
            float f = (float)((double)FixedEntityLookHelper.tan(d2, d0) * 180.0 / Math.PI) - 90.0f;
            float f1 = (float)(-((double)FixedEntityLookHelper.tan(d1, d3) * 180.0 / Math.PI));
            ((EntityLiving) ((EntityLookHelperExt) this).client$getEntity()).rotationPitch = this.updateRotation(((EntityLiving) ((EntityLookHelperExt) this).client$getEntity()).rotationPitch, f1, ((EntityLookHelperAccessor)((Object)this)).getDeltaLookPitch());
            ((EntityLiving) ((EntityLookHelperExt) this).client$getEntity()).rotationYawHead = this.updateRotation(((EntityLiving) ((EntityLookHelperExt) this).client$getEntity()).rotationYawHead, f, ((EntityLookHelperAccessor)((Object)this)).getDeltaLookYaw());
        } else {
            ((EntityLiving) ((EntityLookHelperExt) this).client$getEntity()).rotationYawHead = this.updateRotation(((EntityLiving) ((EntityLookHelperExt) this).client$getEntity()).rotationYawHead, ((EntityLiving) ((EntityLookHelperExt) this).client$getEntity()).renderYawOffset, 10.0f);
        }
        float f2 = MathHelper.wrapAngleTo180_float(((EntityLiving) ((EntityLookHelperExt) this).client$getEntity()).rotationYawHead - ((EntityLiving) ((EntityLookHelperExt) this).client$getEntity()).renderYawOffset);
        if (!((EntityLiving) ((EntityLookHelperExt) this).client$getEntity()).getNavigator().noPath()) {
            if (f2 < -75.0f) {
                ((EntityLiving) ((EntityLookHelperExt) this).client$getEntity()).rotationYawHead = ((EntityLiving) ((EntityLookHelperExt) this).client$getEntity()).renderYawOffset - 75.0f;
            }
            if (f2 > 75.0f) {
                ((EntityLiving) ((EntityLookHelperExt) this).client$getEntity()).rotationYawHead = ((EntityLiving) ((EntityLookHelperExt) this).client$getEntity()).renderYawOffset + 75.0f;
            }
        }
    }

    private float updateRotation(float a, float b, float c) {
        float f = MathHelper.wrapAngleTo180_float(b - a);
        if (f > c) {
            f = c;
        }
        if (f < -c) {
            f = -c;
        }
        return a + f;
    }


    public static float tan(double a, double b)
    {
        return FastTrig.atan2(a, b);
    }
}