package net.minecraft.src;

public class EntityMoveHelper
{
    private EntityLiving entity;
    private double posX;
    private double posY;
    private double posZ;
    private float speed;
    private boolean update;
    
    public EntityMoveHelper(final EntityLiving par1EntityLiving) {
        this.update = false;
        this.entity = par1EntityLiving;
        this.posX = par1EntityLiving.posX;
        this.posY = par1EntityLiving.posY;
        this.posZ = par1EntityLiving.posZ;
    }
    
    public boolean isUpdating() {
        return this.update;
    }
    
    public float getSpeed() {
        return this.speed;
    }
    
    public void setMoveTo(final double par1, final double par3, final double par5, final float par7) {
        this.posX = par1;
        this.posY = par3;
        this.posZ = par5;
        this.speed = par7;
        this.update = true;
    }
    
    public void onUpdateMoveHelper() {
        this.entity.setMoveForward(0.0f);
        if (this.update) {
            this.update = false;
            final int var1 = MathHelper.floor_double(this.entity.boundingBox.minY + 0.5);
            final double var2 = this.posX - this.entity.posX;
            final double var3 = this.posZ - this.entity.posZ;
            final double var4 = this.posY - var1;
            final double var5 = var2 * var2 + var4 * var4 + var3 * var3;
            if (var5 >= 2.500000277905201E-7) {
                final float var6 = (float)(Math.atan2(var3, var2) * 180.0 / 3.141592653589793) - 90.0f;
                this.entity.rotationYaw = this.limitAngle(this.entity.rotationYaw, var6, 30.0f);
                this.entity.setAIMoveSpeed(this.speed * this.entity.getSpeedModifier());
                if (var4 > 0.0 && var2 * var2 + var3 * var3 < 1.0) {
                    this.entity.getJumpHelper().setJumping();
                }
            }
        }
    }
    
    private float limitAngle(final float par1, final float par2, final float par3) {
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
