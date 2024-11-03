package net.silentclient.client.mixin.accessors;

import net.minecraft.entity.ai.EntityLookHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={EntityLookHelper.class})
public interface EntityLookHelperAccessor {
    @Accessor(value="deltaLookYaw")
    public float getDeltaLookYaw();

    @Accessor(value="deltaLookYaw")
    public void setDeltaLookYaw(float var1);

    @Accessor(value="deltaLookPitch")
    public float getDeltaLookPitch();

    @Accessor(value="deltaLookPitch")
    public void setDeltaLookPitch(float var1);

    @Accessor(value="isLooking")
    public boolean isLooking();

    @Accessor(value="isLooking")
    public void setLooking(boolean var1);

    @Accessor(value="posX")
    public double getPosX();

    @Accessor(value="posX")
    public void setPosX(double var1);

    @Accessor(value="posY")
    public double getPosY();

    @Accessor(value="posY")
    public void setPosY(double var1);

    @Accessor(value="posZ")
    public double getPosZ();

    @Accessor(value="posZ")
    public void setPosZ(double var1);
}
