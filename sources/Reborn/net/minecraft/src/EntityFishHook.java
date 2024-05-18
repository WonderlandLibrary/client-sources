package net.minecraft.src;

import java.util.*;

public class EntityFishHook extends Entity
{
    private int xTile;
    private int yTile;
    private int zTile;
    private int inTile;
    private boolean inGround;
    public int shake;
    public EntityPlayer angler;
    private int ticksInGround;
    private int ticksInAir;
    private int ticksCatchable;
    public Entity bobber;
    private int fishPosRotationIncrements;
    private double fishX;
    private double fishY;
    private double fishZ;
    private double fishYaw;
    private double fishPitch;
    private double velocityX;
    private double velocityY;
    private double velocityZ;
    
    public EntityFishHook(final World par1World) {
        super(par1World);
        this.xTile = -1;
        this.yTile = -1;
        this.zTile = -1;
        this.inTile = 0;
        this.inGround = false;
        this.shake = 0;
        this.ticksInAir = 0;
        this.ticksCatchable = 0;
        this.bobber = null;
        this.setSize(0.25f, 0.25f);
        this.ignoreFrustumCheck = true;
    }
    
    public EntityFishHook(final World par1World, final double par2, final double par4, final double par6, final EntityPlayer par8EntityPlayer) {
        this(par1World);
        this.setPosition(par2, par4, par6);
        this.ignoreFrustumCheck = true;
        this.angler = par8EntityPlayer;
        par8EntityPlayer.fishEntity = this;
    }
    
    public EntityFishHook(final World par1World, final EntityPlayer par2EntityPlayer) {
        super(par1World);
        this.xTile = -1;
        this.yTile = -1;
        this.zTile = -1;
        this.inTile = 0;
        this.inGround = false;
        this.shake = 0;
        this.ticksInAir = 0;
        this.ticksCatchable = 0;
        this.bobber = null;
        this.ignoreFrustumCheck = true;
        this.angler = par2EntityPlayer;
        (this.angler.fishEntity = this).setSize(0.25f, 0.25f);
        this.setLocationAndAngles(par2EntityPlayer.posX, par2EntityPlayer.posY + 1.62 - par2EntityPlayer.yOffset, par2EntityPlayer.posZ, par2EntityPlayer.rotationYaw, par2EntityPlayer.rotationPitch);
        this.posX -= MathHelper.cos(this.rotationYaw / 180.0f * 3.1415927f) * 0.16f;
        this.posY -= 0.10000000149011612;
        this.posZ -= MathHelper.sin(this.rotationYaw / 180.0f * 3.1415927f) * 0.16f;
        this.setPosition(this.posX, this.posY, this.posZ);
        this.yOffset = 0.0f;
        final float var3 = 0.4f;
        this.motionX = -MathHelper.sin(this.rotationYaw / 180.0f * 3.1415927f) * MathHelper.cos(this.rotationPitch / 180.0f * 3.1415927f) * var3;
        this.motionZ = MathHelper.cos(this.rotationYaw / 180.0f * 3.1415927f) * MathHelper.cos(this.rotationPitch / 180.0f * 3.1415927f) * var3;
        this.motionY = -MathHelper.sin(this.rotationPitch / 180.0f * 3.1415927f) * var3;
        this.calculateVelocity(this.motionX, this.motionY, this.motionZ, 1.5f, 1.0f);
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
    
    public void calculateVelocity(double par1, double par3, double par5, final float par7, final float par8) {
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
    public void setPositionAndRotation2(final double par1, final double par3, final double par5, final float par7, final float par8, final int par9) {
        this.fishX = par1;
        this.fishY = par3;
        this.fishZ = par5;
        this.fishYaw = par7;
        this.fishPitch = par8;
        this.fishPosRotationIncrements = par9;
        this.motionX = this.velocityX;
        this.motionY = this.velocityY;
        this.motionZ = this.velocityZ;
    }
    
    @Override
    public void setVelocity(final double par1, final double par3, final double par5) {
        this.motionX = par1;
        this.velocityX = par1;
        this.motionY = par3;
        this.velocityY = par3;
        this.motionZ = par5;
        this.velocityZ = par5;
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.fishPosRotationIncrements > 0) {
            final double var21 = this.posX + (this.fishX - this.posX) / this.fishPosRotationIncrements;
            final double var22 = this.posY + (this.fishY - this.posY) / this.fishPosRotationIncrements;
            final double var23 = this.posZ + (this.fishZ - this.posZ) / this.fishPosRotationIncrements;
            final double var24 = MathHelper.wrapAngleTo180_double(this.fishYaw - this.rotationYaw);
            this.rotationYaw += (float)(var24 / this.fishPosRotationIncrements);
            this.rotationPitch += (float)((this.fishPitch - this.rotationPitch) / this.fishPosRotationIncrements);
            --this.fishPosRotationIncrements;
            this.setPosition(var21, var22, var23);
            this.setRotation(this.rotationYaw, this.rotationPitch);
        }
        else {
            if (!this.worldObj.isRemote) {
                final ItemStack var25 = this.angler.getCurrentEquippedItem();
                if (this.angler.isDead || !this.angler.isEntityAlive() || var25 == null || var25.getItem() != Item.fishingRod || this.getDistanceSqToEntity(this.angler) > 1024.0) {
                    this.setDead();
                    this.angler.fishEntity = null;
                    return;
                }
                if (this.bobber != null) {
                    if (!this.bobber.isDead) {
                        this.posX = this.bobber.posX;
                        this.posY = this.bobber.boundingBox.minY + this.bobber.height * 0.8;
                        this.posZ = this.bobber.posZ;
                        return;
                    }
                    this.bobber = null;
                }
            }
            if (this.shake > 0) {
                --this.shake;
            }
            if (this.inGround) {
                final int var26 = this.worldObj.getBlockId(this.xTile, this.yTile, this.zTile);
                if (var26 == this.inTile) {
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
            Vec3 var27 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ);
            Vec3 var28 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
            MovingObjectPosition var29 = this.worldObj.rayTraceBlocks(var27, var28);
            var27 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ);
            var28 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
            if (var29 != null) {
                var28 = this.worldObj.getWorldVec3Pool().getVecFromPool(var29.hitVec.xCoord, var29.hitVec.yCoord, var29.hitVec.zCoord);
            }
            Entity var30 = null;
            final List var31 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0, 1.0, 1.0));
            double var32 = 0.0;
            for (int var33 = 0; var33 < var31.size(); ++var33) {
                final Entity var34 = var31.get(var33);
                if (var34.canBeCollidedWith() && (var34 != this.angler || this.ticksInAir >= 5)) {
                    final float var35 = 0.3f;
                    final AxisAlignedBB var36 = var34.boundingBox.expand(var35, var35, var35);
                    final MovingObjectPosition var37 = var36.calculateIntercept(var27, var28);
                    if (var37 != null) {
                        final double var38 = var27.distanceTo(var37.hitVec);
                        if (var38 < var32 || var32 == 0.0) {
                            var30 = var34;
                            var32 = var38;
                        }
                    }
                }
            }
            if (var30 != null) {
                var29 = new MovingObjectPosition(var30);
            }
            if (var29 != null) {
                if (var29.entityHit != null) {
                    if (var29.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.angler), 0)) {
                        this.bobber = var29.entityHit;
                    }
                }
                else {
                    this.inGround = true;
                }
            }
            if (!this.inGround) {
                this.moveEntity(this.motionX, this.motionY, this.motionZ);
                final float var39 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
                this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0 / 3.141592653589793);
                this.rotationPitch = (float)(Math.atan2(this.motionY, var39) * 180.0 / 3.141592653589793);
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
                float var40 = 0.92f;
                if (this.onGround || this.isCollidedHorizontally) {
                    var40 = 0.5f;
                }
                final byte var41 = 5;
                double var42 = 0.0;
                for (int var43 = 0; var43 < var41; ++var43) {
                    final double var44 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (var43 + 0) / var41 - 0.125 + 0.125;
                    final double var45 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (var43 + 1) / var41 - 0.125 + 0.125;
                    final AxisAlignedBB var46 = AxisAlignedBB.getAABBPool().getAABB(this.boundingBox.minX, var44, this.boundingBox.minZ, this.boundingBox.maxX, var45, this.boundingBox.maxZ);
                    if (this.worldObj.isAABBInMaterial(var46, Material.water)) {
                        var42 += 1.0 / var41;
                    }
                }
                if (var42 > 0.0) {
                    if (this.ticksCatchable > 0) {
                        --this.ticksCatchable;
                    }
                    else {
                        short var47 = 500;
                        if (this.worldObj.canLightningStrikeAt(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY) + 1, MathHelper.floor_double(this.posZ))) {
                            var47 = 300;
                        }
                        if (this.rand.nextInt(var47) == 0) {
                            this.ticksCatchable = this.rand.nextInt(30) + 10;
                            this.motionY -= 0.20000000298023224;
                            this.playSound("random.splash", 0.25f, 1.0f + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4f);
                            final float var48 = MathHelper.floor_double(this.boundingBox.minY);
                            for (int var49 = 0; var49 < 1.0f + this.width * 20.0f; ++var49) {
                                final float var50 = (this.rand.nextFloat() * 2.0f - 1.0f) * this.width;
                                final float var51 = (this.rand.nextFloat() * 2.0f - 1.0f) * this.width;
                                this.worldObj.spawnParticle("bubble", this.posX + var50, var48 + 1.0f, this.posZ + var51, this.motionX, this.motionY - this.rand.nextFloat() * 0.2f, this.motionZ);
                            }
                            for (int var49 = 0; var49 < 1.0f + this.width * 20.0f; ++var49) {
                                final float var50 = (this.rand.nextFloat() * 2.0f - 1.0f) * this.width;
                                final float var51 = (this.rand.nextFloat() * 2.0f - 1.0f) * this.width;
                                this.worldObj.spawnParticle("splash", this.posX + var50, var48 + 1.0f, this.posZ + var51, this.motionX, this.motionY, this.motionZ);
                            }
                        }
                    }
                }
                if (this.ticksCatchable > 0) {
                    this.motionY -= this.rand.nextFloat() * this.rand.nextFloat() * this.rand.nextFloat() * 0.2;
                }
                final double var38 = var42 * 2.0 - 1.0;
                this.motionY += 0.03999999910593033 * var38;
                if (var42 > 0.0) {
                    var40 *= 0.9;
                    this.motionY *= 0.8;
                }
                this.motionX *= var40;
                this.motionY *= var40;
                this.motionZ *= var40;
                this.setPosition(this.posX, this.posY, this.posZ);
            }
        }
    }
    
    public void writeEntityToNBT(final NBTTagCompound par1NBTTagCompound) {
        par1NBTTagCompound.setShort("xTile", (short)this.xTile);
        par1NBTTagCompound.setShort("yTile", (short)this.yTile);
        par1NBTTagCompound.setShort("zTile", (short)this.zTile);
        par1NBTTagCompound.setByte("inTile", (byte)this.inTile);
        par1NBTTagCompound.setByte("shake", (byte)this.shake);
        par1NBTTagCompound.setByte("inGround", (byte)(this.inGround ? 1 : 0));
    }
    
    public void readEntityFromNBT(final NBTTagCompound par1NBTTagCompound) {
        this.xTile = par1NBTTagCompound.getShort("xTile");
        this.yTile = par1NBTTagCompound.getShort("yTile");
        this.zTile = par1NBTTagCompound.getShort("zTile");
        this.inTile = (par1NBTTagCompound.getByte("inTile") & 0xFF);
        this.shake = (par1NBTTagCompound.getByte("shake") & 0xFF);
        this.inGround = (par1NBTTagCompound.getByte("inGround") == 1);
    }
    
    @Override
    public float getShadowSize() {
        return 0.0f;
    }
    
    public int catchFish() {
        if (this.worldObj.isRemote) {
            return 0;
        }
        byte var1 = 0;
        if (this.bobber != null) {
            final double var2 = this.angler.posX - this.posX;
            final double var3 = this.angler.posY - this.posY;
            final double var4 = this.angler.posZ - this.posZ;
            final double var5 = MathHelper.sqrt_double(var2 * var2 + var3 * var3 + var4 * var4);
            final double var6 = 0.1;
            final Entity bobber = this.bobber;
            bobber.motionX += var2 * var6;
            final Entity bobber2 = this.bobber;
            bobber2.motionY += var3 * var6 + MathHelper.sqrt_double(var5) * 0.08;
            final Entity bobber3 = this.bobber;
            bobber3.motionZ += var4 * var6;
            var1 = 3;
        }
        else if (this.ticksCatchable > 0) {
            final EntityItem var7 = new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, new ItemStack(Item.fishRaw));
            final double var8 = this.angler.posX - this.posX;
            final double var9 = this.angler.posY - this.posY;
            final double var10 = this.angler.posZ - this.posZ;
            final double var11 = MathHelper.sqrt_double(var8 * var8 + var9 * var9 + var10 * var10);
            final double var12 = 0.1;
            var7.motionX = var8 * var12;
            var7.motionY = var9 * var12 + MathHelper.sqrt_double(var11) * 0.08;
            var7.motionZ = var10 * var12;
            this.worldObj.spawnEntityInWorld(var7);
            this.angler.addStat(StatList.fishCaughtStat, 1);
            this.angler.worldObj.spawnEntityInWorld(new EntityXPOrb(this.angler.worldObj, this.angler.posX, this.angler.posY + 0.5, this.angler.posZ + 0.5, this.rand.nextInt(6) + 1));
            var1 = 1;
        }
        if (this.inGround) {
            var1 = 2;
        }
        this.setDead();
        this.angler.fishEntity = null;
        return var1;
    }
    
    @Override
    public void setDead() {
        super.setDead();
        if (this.angler != null) {
            this.angler.fishEntity = null;
        }
    }
}
