package net.minecraft.src;

public abstract class EntityFlying extends EntityLiving
{
    public EntityFlying(final World par1World) {
        super(par1World);
    }
    
    @Override
    protected void fall(final float par1) {
    }
    
    @Override
    protected void updateFallState(final double par1, final boolean par3) {
    }
    
    @Override
    public void moveEntityWithHeading(final float par1, final float par2) {
        if (this.isInWater()) {
            this.moveFlying(par1, par2, 0.02f);
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.800000011920929;
            this.motionY *= 0.800000011920929;
            this.motionZ *= 0.800000011920929;
        }
        else if (this.handleLavaMovement()) {
            this.moveFlying(par1, par2, 0.02f);
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.5;
            this.motionY *= 0.5;
            this.motionZ *= 0.5;
        }
        else {
            float var3 = 0.91f;
            if (this.onGround) {
                var3 = 0.54600006f;
                final int var4 = this.worldObj.getBlockId(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ));
                if (var4 > 0) {
                    var3 = Block.blocksList[var4].slipperiness * 0.91f;
                }
            }
            final float var5 = 0.16277136f / (var3 * var3 * var3);
            this.moveFlying(par1, par2, this.onGround ? (0.1f * var5) : 0.02f);
            var3 = 0.91f;
            if (this.onGround) {
                var3 = 0.54600006f;
                final int var6 = this.worldObj.getBlockId(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ));
                if (var6 > 0) {
                    var3 = Block.blocksList[var6].slipperiness * 0.91f;
                }
            }
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= var3;
            this.motionY *= var3;
            this.motionZ *= var3;
        }
        this.prevLimbYaw = this.limbYaw;
        final double var7 = this.posX - this.prevPosX;
        final double var8 = this.posZ - this.prevPosZ;
        float var9 = MathHelper.sqrt_double(var7 * var7 + var8 * var8) * 4.0f;
        if (var9 > 1.0f) {
            var9 = 1.0f;
        }
        this.limbYaw += (var9 - this.limbYaw) * 0.4f;
        this.limbSwing += this.limbYaw;
    }
    
    @Override
    public boolean isOnLadder() {
        return false;
    }
}
