package net.minecraft.src;

public class EntityLookHelper
{
    private EntityLiving entity;
    private float deltaLookYaw;
    private float deltaLookPitch;
    private boolean isLooking;
    private double posX;
    private double posY;
    private double posZ;
    
    public EntityLookHelper(final EntityLiving par1EntityLiving) {
        this.isLooking = false;
        this.entity = par1EntityLiving;
    }
    
    public void setLookPositionWithEntity(final Entity par1Entity, final float par2, final float par3) {
        this.posX = par1Entity.posX;
        if (par1Entity instanceof EntityLiving) {
            this.posY = par1Entity.posY + par1Entity.getEyeHeight();
        }
        else {
            this.posY = (par1Entity.boundingBox.minY + par1Entity.boundingBox.maxY) / 2.0;
        }
        this.posZ = par1Entity.posZ;
        this.deltaLookYaw = par2;
        this.deltaLookPitch = par3;
        this.isLooking = true;
    }
    
    public void setLookPosition(final double par1, final double par3, final double par5, final float par7, final float par8) {
        this.posX = par1;
        this.posY = par3;
        this.posZ = par5;
        this.deltaLookYaw = par7;
        this.deltaLookPitch = par8;
        this.isLooking = true;
    }
    
    public void onUpdateLook() {
        this.entity.rotationPitch = 0.0f;
        if (this.isLooking) {
            this.isLooking = false;
            final double var1 = this.posX - this.entity.posX;
            final double var2 = this.posY - (this.entity.posY + this.entity.getEyeHeight());
            final double var3 = this.posZ - this.entity.posZ;
            final double var4 = MathHelper.sqrt_double(var1 * var1 + var3 * var3);
            final float var5 = (float)(Math.atan2(var3, var1) * 180.0 / 3.141592653589793) - 90.0f;
            final float var6 = (float)(-(Math.atan2(var2, var4) * 180.0 / 3.141592653589793));
            this.entity.rotationPitch = this.updateRotation(this.entity.rotationPitch, var6, this.deltaLookPitch);
            this.entity.rotationYawHead = this.updateRotation(this.entity.rotationYawHead, var5, this.deltaLookYaw);
        }
        else {
            this.entity.rotationYawHead = this.updateRotation(this.entity.rotationYawHead, this.entity.renderYawOffset, 10.0f);
        }
        final float var7 = MathHelper.wrapAngleTo180_float(this.entity.rotationYawHead - this.entity.renderYawOffset);
        if (!this.entity.getNavigator().noPath()) {
            if (var7 < -75.0f) {
                this.entity.rotationYawHead = this.entity.renderYawOffset - 75.0f;
            }
            if (var7 > 75.0f) {
                this.entity.rotationYawHead = this.entity.renderYawOffset + 75.0f;
            }
        }
    }
    
    private float updateRotation(final float par1, final float par2, final float par3) {
        float var4 = MathHelper.wrapAngleTo180_float(par2 - par1);
        if (var4 > par3) {
            var4 = par3;
        }
        if (var4 < -par3) {
            var4 = -par3;
        }
        return par1 + var4;
    }
}
