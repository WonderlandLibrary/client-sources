package net.minecraft.src;

import java.util.*;

public abstract class EntityThrowable extends Entity implements IProjectile
{
    private int xTile;
    private int yTile;
    private int zTile;
    private int inTile;
    protected boolean inGround;
    public int throwableShake;
    private EntityLiving thrower;
    private String throwerName;
    private int ticksInGround;
    private int ticksInAir;
    
    public EntityThrowable(final World par1World) {
        super(par1World);
        this.xTile = -1;
        this.yTile = -1;
        this.zTile = -1;
        this.inTile = 0;
        this.inGround = false;
        this.throwableShake = 0;
        this.throwerName = null;
        this.ticksInAir = 0;
        this.setSize(0.25f, 0.25f);
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
    
    public EntityThrowable(final World par1World, final EntityLiving par2EntityLiving) {
        super(par1World);
        this.xTile = -1;
        this.yTile = -1;
        this.zTile = -1;
        this.inTile = 0;
        this.inGround = false;
        this.throwableShake = 0;
        this.throwerName = null;
        this.ticksInAir = 0;
        this.thrower = par2EntityLiving;
        this.setSize(0.25f, 0.25f);
        this.setLocationAndAngles(par2EntityLiving.posX, par2EntityLiving.posY + par2EntityLiving.getEyeHeight(), par2EntityLiving.posZ, par2EntityLiving.rotationYaw, par2EntityLiving.rotationPitch);
        this.posX -= MathHelper.cos(this.rotationYaw / 180.0f * 3.1415927f) * 0.16f;
        this.posY -= 0.10000000149011612;
        this.posZ -= MathHelper.sin(this.rotationYaw / 180.0f * 3.1415927f) * 0.16f;
        this.setPosition(this.posX, this.posY, this.posZ);
        this.yOffset = 0.0f;
        final float var3 = 0.4f;
        this.motionX = -MathHelper.sin(this.rotationYaw / 180.0f * 3.1415927f) * MathHelper.cos(this.rotationPitch / 180.0f * 3.1415927f) * var3;
        this.motionZ = MathHelper.cos(this.rotationYaw / 180.0f * 3.1415927f) * MathHelper.cos(this.rotationPitch / 180.0f * 3.1415927f) * var3;
        this.motionY = -MathHelper.sin((this.rotationPitch + this.func_70183_g()) / 180.0f * 3.1415927f) * var3;
        this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, this.func_70182_d(), 1.0f);
    }
    
    public EntityThrowable(final World par1World, final double par2, final double par4, final double par6) {
        super(par1World);
        this.xTile = -1;
        this.yTile = -1;
        this.zTile = -1;
        this.inTile = 0;
        this.inGround = false;
        this.throwableShake = 0;
        this.throwerName = null;
        this.ticksInAir = 0;
        this.ticksInGround = 0;
        this.setSize(0.25f, 0.25f);
        this.setPosition(par2, par4, par6);
        this.yOffset = 0.0f;
    }
    
    protected float func_70182_d() {
        return 1.5f;
    }
    
    protected float func_70183_g() {
        return 0.0f;
    }
    
    @Override
    public void setThrowableHeading(double par1, double par3, double par5, final float par7, final float par8) {
        final float var9 = MathHelper.sqrt_double(par1 * par1 + par3 * par3 + par5 * par5);
        par1 /= var9;
        par3 /= var9;
        par5 /= var9;
        par1 += this.rand.nextGaussian() * 0.007499999832361937 * par8;
        par3 += this.rand.nextGaussian() * 0.007499999832361937 * par8;
        par5 += this.rand.nextGaussian() * 0.007499999832361937 * par8;
        par1 *= par7;
        par3 *= par7;
        par5 *= par7;
        this.motionX = par1;
        this.motionY = par3;
        this.motionZ = par5;
        final float var10 = MathHelper.sqrt_double(par1 * par1 + par5 * par5);
        final float n = (float)(Math.atan2(par1, par5) * 180.0 / 3.141592653589793);
        this.rotationYaw = n;
        this.prevRotationYaw = n;
        final float n2 = (float)(Math.atan2(par3, var10) * 180.0 / 3.141592653589793);
        this.rotationPitch = n2;
        this.prevRotationPitch = n2;
        this.ticksInGround = 0;
    }
    
    @Override
    public void setVelocity(final double par1, final double par3, final double par5) {
        this.motionX = par1;
        this.motionY = par3;
        this.motionZ = par5;
        if (this.prevRotationPitch == 0.0f && this.prevRotationYaw == 0.0f) {
            final float var7 = MathHelper.sqrt_double(par1 * par1 + par5 * par5);
            final float n = (float)(Math.atan2(par1, par5) * 180.0 / 3.141592653589793);
            this.rotationYaw = n;
            this.prevRotationYaw = n;
            final float n2 = (float)(Math.atan2(par3, var7) * 180.0 / 3.141592653589793);
            this.rotationPitch = n2;
            this.prevRotationPitch = n2;
        }
    }
    
    @Override
    public void onUpdate() {
        this.lastTickPosX = this.posX;
        this.lastTickPosY = this.posY;
        this.lastTickPosZ = this.posZ;
        super.onUpdate();
        if (this.throwableShake > 0) {
            --this.throwableShake;
        }
        if (this.inGround) {
            final int var1 = this.worldObj.getBlockId(this.xTile, this.yTile, this.zTile);
            if (var1 == this.inTile) {
                ++this.ticksInGround;
                if (this.ticksInGround == 1200) {
                    this.setDead();
                }
                return;
            }
            this.inGround = false;
            this.motionX *= this.rand.nextFloat() * 0.2f;
            this.motionY *= this.rand.nextFloat() * 0.2f;
            this.motionZ *= this.rand.nextFloat() * 0.2f;
            this.ticksInGround = 0;
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
        if (!this.worldObj.isRemote) {
            Entity var5 = null;
            final List var6 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0, 1.0, 1.0));
            double var7 = 0.0;
            final EntityLiving var8 = this.getThrower();
            for (int var9 = 0; var9 < var6.size(); ++var9) {
                final Entity var10 = var6.get(var9);
                if (var10.canBeCollidedWith() && (var10 != var8 || this.ticksInAir >= 5)) {
                    final float var11 = 0.3f;
                    final AxisAlignedBB var12 = var10.boundingBox.expand(var11, var11, var11);
                    final MovingObjectPosition var13 = var12.calculateIntercept(var2, var3);
                    if (var13 != null) {
                        final double var14 = var2.distanceTo(var13.hitVec);
                        if (var14 < var7 || var7 == 0.0) {
                            var5 = var10;
                            var7 = var14;
                        }
                    }
                }
            }
            if (var5 != null) {
                var4 = new MovingObjectPosition(var5);
            }
        }
        if (var4 != null) {
            if (var4.typeOfHit == EnumMovingObjectType.TILE && this.worldObj.getBlockId(var4.blockX, var4.blockY, var4.blockZ) == Block.portal.blockID) {
                this.setInPortal();
            }
            else {
                this.onImpact(var4);
            }
        }
        this.posX += this.motionX;
        this.posY += this.motionY;
        this.posZ += this.motionZ;
        final float var15 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
        this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0 / 3.141592653589793);
        this.rotationPitch = (float)(Math.atan2(this.motionY, var15) * 180.0 / 3.141592653589793);
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
        float var16 = 0.99f;
        final float var17 = this.getGravityVelocity();
        if (this.isInWater()) {
            for (int var18 = 0; var18 < 4; ++var18) {
                final float var19 = 0.25f;
                this.worldObj.spawnParticle("bubble", this.posX - this.motionX * var19, this.posY - this.motionY * var19, this.posZ - this.motionZ * var19, this.motionX, this.motionY, this.motionZ);
            }
            var16 = 0.8f;
        }
        this.motionX *= var16;
        this.motionY *= var16;
        this.motionZ *= var16;
        this.motionY -= var17;
        this.setPosition(this.posX, this.posY, this.posZ);
    }
    
    protected float getGravityVelocity() {
        return 0.03f;
    }
    
    protected abstract void onImpact(final MovingObjectPosition p0);
    
    public void writeEntityToNBT(final NBTTagCompound par1NBTTagCompound) {
        par1NBTTagCompound.setShort("xTile", (short)this.xTile);
        par1NBTTagCompound.setShort("yTile", (short)this.yTile);
        par1NBTTagCompound.setShort("zTile", (short)this.zTile);
        par1NBTTagCompound.setByte("inTile", (byte)this.inTile);
        par1NBTTagCompound.setByte("shake", (byte)this.throwableShake);
        par1NBTTagCompound.setByte("inGround", (byte)(this.inGround ? 1 : 0));
        if ((this.throwerName == null || this.throwerName.length() == 0) && this.thrower != null && this.thrower instanceof EntityPlayer) {
            this.throwerName = this.thrower.getEntityName();
        }
        par1NBTTagCompound.setString("ownerName", (this.throwerName == null) ? "" : this.throwerName);
    }
    
    public void readEntityFromNBT(final NBTTagCompound par1NBTTagCompound) {
        this.xTile = par1NBTTagCompound.getShort("xTile");
        this.yTile = par1NBTTagCompound.getShort("yTile");
        this.zTile = par1NBTTagCompound.getShort("zTile");
        this.inTile = (par1NBTTagCompound.getByte("inTile") & 0xFF);
        this.throwableShake = (par1NBTTagCompound.getByte("shake") & 0xFF);
        this.inGround = (par1NBTTagCompound.getByte("inGround") == 1);
        this.throwerName = par1NBTTagCompound.getString("ownerName");
        if (this.throwerName != null && this.throwerName.length() == 0) {
            this.throwerName = null;
        }
    }
    
    @Override
    public float getShadowSize() {
        return 0.0f;
    }
    
    public EntityLiving getThrower() {
        if (this.thrower == null && this.throwerName != null && this.throwerName.length() > 0) {
            this.thrower = this.worldObj.getPlayerEntityByName(this.throwerName);
        }
        return this.thrower;
    }
}
