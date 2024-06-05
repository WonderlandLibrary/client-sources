package net.minecraft.src;

import java.util.*;

public abstract class EntityFireball extends Entity
{
    private int xTile;
    private int yTile;
    private int zTile;
    private int inTile;
    private boolean inGround;
    public EntityLiving shootingEntity;
    private int ticksAlive;
    private int ticksInAir;
    public double accelerationX;
    public double accelerationY;
    public double accelerationZ;
    
    public EntityFireball(final World par1World) {
        super(par1World);
        this.xTile = -1;
        this.yTile = -1;
        this.zTile = -1;
        this.inTile = 0;
        this.inGround = false;
        this.ticksInAir = 0;
        this.setSize(1.0f, 1.0f);
    }
    
    @Override
    protected void entityInit() {
    }
    
    @Override
    public boolean isInRangeToRenderDist(final double par1) {
        double var3 = this.boundingBox.getAverageEdgeLength() * 4.0;
        var3 *= 64.0;
        return par1 < var3 * var3;
    }
    
    public EntityFireball(final World par1World, final double par2, final double par4, final double par6, final double par8, final double par10, final double par12) {
        super(par1World);
        this.xTile = -1;
        this.yTile = -1;
        this.zTile = -1;
        this.inTile = 0;
        this.inGround = false;
        this.ticksInAir = 0;
        this.setSize(1.0f, 1.0f);
        this.setLocationAndAngles(par2, par4, par6, this.rotationYaw, this.rotationPitch);
        this.setPosition(par2, par4, par6);
        final double var14 = MathHelper.sqrt_double(par8 * par8 + par10 * par10 + par12 * par12);
        this.accelerationX = par8 / var14 * 0.1;
        this.accelerationY = par10 / var14 * 0.1;
        this.accelerationZ = par12 / var14 * 0.1;
    }
    
    public EntityFireball(final World par1World, final EntityLiving par2EntityLiving, double par3, double par5, double par7) {
        super(par1World);
        this.xTile = -1;
        this.yTile = -1;
        this.zTile = -1;
        this.inTile = 0;
        this.inGround = false;
        this.ticksInAir = 0;
        this.shootingEntity = par2EntityLiving;
        this.setSize(1.0f, 1.0f);
        this.setLocationAndAngles(par2EntityLiving.posX, par2EntityLiving.posY, par2EntityLiving.posZ, par2EntityLiving.rotationYaw, par2EntityLiving.rotationPitch);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.yOffset = 0.0f;
        final double motionX = 0.0;
        this.motionZ = motionX;
        this.motionY = motionX;
        this.motionX = motionX;
        par3 += this.rand.nextGaussian() * 0.4;
        par5 += this.rand.nextGaussian() * 0.4;
        par7 += this.rand.nextGaussian() * 0.4;
        final double var9 = MathHelper.sqrt_double(par3 * par3 + par5 * par5 + par7 * par7);
        this.accelerationX = par3 / var9 * 0.1;
        this.accelerationY = par5 / var9 * 0.1;
        this.accelerationZ = par7 / var9 * 0.1;
    }
    
    @Override
    public void onUpdate() {
        if (!this.worldObj.isRemote && ((this.shootingEntity != null && this.shootingEntity.isDead) || !this.worldObj.blockExists((int)this.posX, (int)this.posY, (int)this.posZ))) {
            this.setDead();
        }
        else {
            super.onUpdate();
            this.setFire(1);
            if (this.inGround) {
                final int var1 = this.worldObj.getBlockId(this.xTile, this.yTile, this.zTile);
                if (var1 == this.inTile) {
                    ++this.ticksAlive;
                    if (this.ticksAlive == 600) {
                        this.setDead();
                    }
                    return;
                }
                this.inGround = false;
                this.motionX *= this.rand.nextFloat() * 0.2f;
                this.motionY *= this.rand.nextFloat() * 0.2f;
                this.motionZ *= this.rand.nextFloat() * 0.2f;
                this.ticksAlive = 0;
                this.ticksInAir = 0;
            }
            else {
                ++this.ticksInAir;
            }
            Vec3 var2 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ);
            Vec3 var3 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
            MovingObjectPosition var4 = this.worldObj.rayTraceBlocks(var2, var3);
            var2 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ);
            var3 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
            if (var4 != null) {
                var3 = this.worldObj.getWorldVec3Pool().getVecFromPool(var4.hitVec.xCoord, var4.hitVec.yCoord, var4.hitVec.zCoord);
            }
            Entity var5 = null;
            final List var6 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0, 1.0, 1.0));
            double var7 = 0.0;
            for (int var8 = 0; var8 < var6.size(); ++var8) {
                final Entity var9 = var6.get(var8);
                if (var9.canBeCollidedWith() && (!var9.isEntityEqual(this.shootingEntity) || this.ticksInAir >= 25)) {
                    final float var10 = 0.3f;
                    final AxisAlignedBB var11 = var9.boundingBox.expand(var10, var10, var10);
                    final MovingObjectPosition var12 = var11.calculateIntercept(var2, var3);
                    if (var12 != null) {
                        final double var13 = var2.distanceTo(var12.hitVec);
                        if (var13 < var7 || var7 == 0.0) {
                            var5 = var9;
                            var7 = var13;
                        }
                    }
                }
            }
            if (var5 != null) {
                var4 = new MovingObjectPosition(var5);
            }
            if (var4 != null) {
                this.onImpact(var4);
            }
            this.posX += this.motionX;
            this.posY += this.motionY;
            this.posZ += this.motionZ;
            final float var14 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.rotationYaw = (float)(Math.atan2(this.motionZ, this.motionX) * 180.0 / 3.141592653589793) + 90.0f;
            this.rotationPitch = (float)(Math.atan2(var14, this.motionY) * 180.0 / 3.141592653589793) - 90.0f;
            while (this.rotationPitch - this.prevRotationPitch < -180.0f) {
                this.prevRotationPitch -= 360.0f;
            }
            while (this.rotationPitch - this.prevRotationPitch >= 180.0f) {
                this.prevRotationPitch += 360.0f;
            }
            while (this.rotationYaw - this.prevRotationYaw < -180.0f) {
                this.prevRotationYaw -= 360.0f;
            }
            while (this.rotationYaw - this.prevRotationYaw >= 180.0f) {
                this.prevRotationYaw += 360.0f;
            }
            this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2f;
            this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2f;
            float var15 = this.getMotionFactor();
            if (this.isInWater()) {
                for (int var16 = 0; var16 < 4; ++var16) {
                    final float var17 = 0.25f;
                    this.worldObj.spawnParticle("bubble", this.posX - this.motionX * var17, this.posY - this.motionY * var17, this.posZ - this.motionZ * var17, this.motionX, this.motionY, this.motionZ);
                }
                var15 = 0.8f;
            }
            this.motionX += this.accelerationX;
            this.motionY += this.accelerationY;
            this.motionZ += this.accelerationZ;
            this.motionX *= var15;
            this.motionY *= var15;
            this.motionZ *= var15;
            this.worldObj.spawnParticle("smoke", this.posX, this.posY + 0.5, this.posZ, 0.0, 0.0, 0.0);
            this.setPosition(this.posX, this.posY, this.posZ);
        }
    }
    
    protected float getMotionFactor() {
        return 0.95f;
    }
    
    protected abstract void onImpact(final MovingObjectPosition p0);
    
    public void writeEntityToNBT(final NBTTagCompound par1NBTTagCompound) {
        par1NBTTagCompound.setShort("xTile", (short)this.xTile);
        par1NBTTagCompound.setShort("yTile", (short)this.yTile);
        par1NBTTagCompound.setShort("zTile", (short)this.zTile);
        par1NBTTagCompound.setByte("inTile", (byte)this.inTile);
        par1NBTTagCompound.setByte("inGround", (byte)(this.inGround ? 1 : 0));
        par1NBTTagCompound.setTag("direction", this.newDoubleNBTList(this.motionX, this.motionY, this.motionZ));
    }
    
    public void readEntityFromNBT(final NBTTagCompound par1NBTTagCompound) {
        this.xTile = par1NBTTagCompound.getShort("xTile");
        this.yTile = par1NBTTagCompound.getShort("yTile");
        this.zTile = par1NBTTagCompound.getShort("zTile");
        this.inTile = (par1NBTTagCompound.getByte("inTile") & 0xFF);
        this.inGround = (par1NBTTagCompound.getByte("inGround") == 1);
        if (par1NBTTagCompound.hasKey("direction")) {
            final NBTTagList var2 = par1NBTTagCompound.getTagList("direction");
            this.motionX = ((NBTTagDouble)var2.tagAt(0)).data;
            this.motionY = ((NBTTagDouble)var2.tagAt(1)).data;
            this.motionZ = ((NBTTagDouble)var2.tagAt(2)).data;
        }
        else {
            this.setDead();
        }
    }
    
    @Override
    public boolean canBeCollidedWith() {
        return true;
    }
    
    @Override
    public float getCollisionBorderSize() {
        return 1.0f;
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource par1DamageSource, final int par2) {
        if (this.isEntityInvulnerable()) {
            return false;
        }
        this.setBeenAttacked();
        if (par1DamageSource.getEntity() != null) {
            final Vec3 var3 = par1DamageSource.getEntity().getLookVec();
            if (var3 != null) {
                this.motionX = var3.xCoord;
                this.motionY = var3.yCoord;
                this.motionZ = var3.zCoord;
                this.accelerationX = this.motionX * 0.1;
                this.accelerationY = this.motionY * 0.1;
                this.accelerationZ = this.motionZ * 0.1;
            }
            if (par1DamageSource.getEntity() instanceof EntityLiving) {
                this.shootingEntity = (EntityLiving)par1DamageSource.getEntity();
            }
            return true;
        }
        return false;
    }
    
    @Override
    public float getShadowSize() {
        return 0.0f;
    }
    
    @Override
    public float getBrightness(final float par1) {
        return 1.0f;
    }
    
    @Override
    public int getBrightnessForRender(final float par1) {
        return 15728880;
    }
}
