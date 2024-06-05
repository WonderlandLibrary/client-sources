package net.minecraft.src;

import java.util.*;

public class EntityBoat extends Entity
{
    private boolean field_70279_a;
    private double speedMultiplier;
    private int boatPosRotationIncrements;
    private double boatX;
    private double boatY;
    private double boatZ;
    private double boatYaw;
    private double boatPitch;
    private double velocityX;
    private double velocityY;
    private double velocityZ;
    
    public EntityBoat(final World par1World) {
        super(par1World);
        this.field_70279_a = true;
        this.speedMultiplier = 0.07;
        this.preventEntitySpawning = true;
        this.setSize(1.5f, 0.6f);
        this.yOffset = this.height / 2.0f;
    }
    
    @Override
    protected boolean canTriggerWalking() {
        return false;
    }
    
    @Override
    protected void entityInit() {
        this.dataWatcher.addObject(17, new Integer(0));
        this.dataWatcher.addObject(18, new Integer(1));
        this.dataWatcher.addObject(19, new Integer(0));
    }
    
    @Override
    public AxisAlignedBB getCollisionBox(final Entity par1Entity) {
        return par1Entity.boundingBox;
    }
    
    @Override
    public AxisAlignedBB getBoundingBox() {
        return this.boundingBox;
    }
    
    @Override
    public boolean canBePushed() {
        return true;
    }
    
    public EntityBoat(final World par1World, final double par2, final double par4, final double par6) {
        this(par1World);
        this.setPosition(par2, par4 + this.yOffset, par6);
        this.motionX = 0.0;
        this.motionY = 0.0;
        this.motionZ = 0.0;
        this.prevPosX = par2;
        this.prevPosY = par4;
        this.prevPosZ = par6;
    }
    
    @Override
    public double getMountedYOffset() {
        return this.height * 0.0 - 0.30000001192092896;
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource par1DamageSource, final int par2) {
        if (this.isEntityInvulnerable()) {
            return false;
        }
        if (!this.worldObj.isRemote && !this.isDead) {
            this.setForwardDirection(-this.getForwardDirection());
            this.setTimeSinceHit(10);
            this.setDamageTaken(this.getDamageTaken() + par2 * 10);
            this.setBeenAttacked();
            final boolean var3 = par1DamageSource.getEntity() instanceof EntityPlayer && ((EntityPlayer)par1DamageSource.getEntity()).capabilities.isCreativeMode;
            if (var3 || this.getDamageTaken() > 40) {
                if (this.riddenByEntity != null) {
                    this.riddenByEntity.mountEntity(this);
                }
                if (!var3) {
                    this.dropItemWithOffset(Item.boat.itemID, 1, 0.0f);
                }
                this.setDead();
            }
            return true;
        }
        return true;
    }
    
    @Override
    public void performHurtAnimation() {
        this.setForwardDirection(-this.getForwardDirection());
        this.setTimeSinceHit(10);
        this.setDamageTaken(this.getDamageTaken() * 11);
    }
    
    @Override
    public boolean canBeCollidedWith() {
        return !this.isDead;
    }
    
    @Override
    public void setPositionAndRotation2(final double par1, final double par3, final double par5, final float par7, final float par8, final int par9) {
        if (this.field_70279_a) {
            this.boatPosRotationIncrements = par9 + 5;
        }
        else {
            final double var10 = par1 - this.posX;
            final double var11 = par3 - this.posY;
            final double var12 = par5 - this.posZ;
            final double var13 = var10 * var10 + var11 * var11 + var12 * var12;
            if (var13 <= 1.0) {
                return;
            }
            this.boatPosRotationIncrements = 3;
        }
        this.boatX = par1;
        this.boatY = par3;
        this.boatZ = par5;
        this.boatYaw = par7;
        this.boatPitch = par8;
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
        if (this.getTimeSinceHit() > 0) {
            this.setTimeSinceHit(this.getTimeSinceHit() - 1);
        }
        if (this.getDamageTaken() > 0) {
            this.setDamageTaken(this.getDamageTaken() - 1);
        }
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        final byte var1 = 5;
        double var2 = 0.0;
        for (int var3 = 0; var3 < var1; ++var3) {
            final double var4 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (var3 + 0) / var1 - 0.125;
            final double var5 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (var3 + 1) / var1 - 0.125;
            final AxisAlignedBB var6 = AxisAlignedBB.getAABBPool().getAABB(this.boundingBox.minX, var4, this.boundingBox.minZ, this.boundingBox.maxX, var5, this.boundingBox.maxZ);
            if (this.worldObj.isAABBInMaterial(var6, Material.water)) {
                var2 += 1.0 / var1;
            }
        }
        final double var7 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
        if (var7 > 0.26249999999999996) {
            final double var8 = Math.cos(this.rotationYaw * 3.141592653589793 / 180.0);
            final double var9 = Math.sin(this.rotationYaw * 3.141592653589793 / 180.0);
            for (int var10 = 0; var10 < 1.0 + var7 * 60.0; ++var10) {
                final double var11 = this.rand.nextFloat() * 2.0f - 1.0f;
                final double var12 = (this.rand.nextInt(2) * 2 - 1) * 0.7;
                if (this.rand.nextBoolean()) {
                    final double var13 = this.posX - var8 * var11 * 0.8 + var9 * var12;
                    final double var14 = this.posZ - var9 * var11 * 0.8 - var8 * var12;
                    this.worldObj.spawnParticle("splash", var13, this.posY - 0.125, var14, this.motionX, this.motionY, this.motionZ);
                }
                else {
                    final double var13 = this.posX + var8 + var9 * var11 * 0.7;
                    final double var14 = this.posZ + var9 - var8 * var11 * 0.7;
                    this.worldObj.spawnParticle("splash", var13, this.posY - 0.125, var14, this.motionX, this.motionY, this.motionZ);
                }
            }
        }
        if (this.worldObj.isRemote && this.field_70279_a) {
            if (this.boatPosRotationIncrements > 0) {
                final double var8 = this.posX + (this.boatX - this.posX) / this.boatPosRotationIncrements;
                final double var9 = this.posY + (this.boatY - this.posY) / this.boatPosRotationIncrements;
                final double var15 = this.posZ + (this.boatZ - this.posZ) / this.boatPosRotationIncrements;
                final double var16 = MathHelper.wrapAngleTo180_double(this.boatYaw - this.rotationYaw);
                this.rotationYaw += (float)(var16 / this.boatPosRotationIncrements);
                this.rotationPitch += (float)((this.boatPitch - this.rotationPitch) / this.boatPosRotationIncrements);
                --this.boatPosRotationIncrements;
                this.setPosition(var8, var9, var15);
                this.setRotation(this.rotationYaw, this.rotationPitch);
            }
            else {
                final double var8 = this.posX + this.motionX;
                final double var9 = this.posY + this.motionY;
                final double var15 = this.posZ + this.motionZ;
                this.setPosition(var8, var9, var15);
                if (this.onGround) {
                    this.motionX *= 0.5;
                    this.motionY *= 0.5;
                    this.motionZ *= 0.5;
                }
                this.motionX *= 0.9900000095367432;
                this.motionY *= 0.949999988079071;
                this.motionZ *= 0.9900000095367432;
            }
        }
        else {
            if (var2 < 1.0) {
                final double var8 = var2 * 2.0 - 1.0;
                this.motionY += 0.03999999910593033 * var8;
            }
            else {
                if (this.motionY < 0.0) {
                    this.motionY /= 2.0;
                }
                this.motionY += 0.007000000216066837;
            }
            if (this.riddenByEntity != null) {
                this.motionX += this.riddenByEntity.motionX * this.speedMultiplier;
                this.motionZ += this.riddenByEntity.motionZ * this.speedMultiplier;
            }
            double var8 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
            if (var8 > 0.35) {
                final double var9 = 0.35 / var8;
                this.motionX *= var9;
                this.motionZ *= var9;
                var8 = 0.35;
            }
            if (var8 > var7 && this.speedMultiplier < 0.35) {
                this.speedMultiplier += (0.35 - this.speedMultiplier) / 35.0;
                if (this.speedMultiplier > 0.35) {
                    this.speedMultiplier = 0.35;
                }
            }
            else {
                this.speedMultiplier -= (this.speedMultiplier - 0.07) / 35.0;
                if (this.speedMultiplier < 0.07) {
                    this.speedMultiplier = 0.07;
                }
            }
            if (this.onGround) {
                this.motionX *= 0.5;
                this.motionY *= 0.5;
                this.motionZ *= 0.5;
            }
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            if (this.isCollidedHorizontally && var7 > 0.2) {
                if (!this.worldObj.isRemote && !this.isDead) {
                    this.setDead();
                    for (int var17 = 0; var17 < 3; ++var17) {
                        this.dropItemWithOffset(Block.planks.blockID, 1, 0.0f);
                    }
                    for (int var17 = 0; var17 < 2; ++var17) {
                        this.dropItemWithOffset(Item.stick.itemID, 1, 0.0f);
                    }
                }
            }
            else {
                this.motionX *= 0.9900000095367432;
                this.motionY *= 0.949999988079071;
                this.motionZ *= 0.9900000095367432;
            }
            this.rotationPitch = 0.0f;
            double var9 = this.rotationYaw;
            final double var15 = this.prevPosX - this.posX;
            final double var16 = this.prevPosZ - this.posZ;
            if (var15 * var15 + var16 * var16 > 0.001) {
                var9 = (float)(Math.atan2(var16, var15) * 180.0 / 3.141592653589793);
            }
            double var18 = MathHelper.wrapAngleTo180_double(var9 - this.rotationYaw);
            if (var18 > 20.0) {
                var18 = 20.0;
            }
            if (var18 < -20.0) {
                var18 = -20.0;
            }
            this.setRotation(this.rotationYaw += (float)var18, this.rotationPitch);
            if (!this.worldObj.isRemote) {
                final List var19 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(0.20000000298023224, 0.0, 0.20000000298023224));
                if (var19 != null && !var19.isEmpty()) {
                    for (int var20 = 0; var20 < var19.size(); ++var20) {
                        final Entity var21 = var19.get(var20);
                        if (var21 != this.riddenByEntity && var21.canBePushed() && var21 instanceof EntityBoat) {
                            var21.applyEntityCollision(this);
                        }
                    }
                }
                for (int var20 = 0; var20 < 4; ++var20) {
                    final int var22 = MathHelper.floor_double(this.posX + (var20 % 2 - 0.5) * 0.8);
                    final int var23 = MathHelper.floor_double(this.posZ + (var20 / 2 - 0.5) * 0.8);
                    for (int var24 = 0; var24 < 2; ++var24) {
                        final int var25 = MathHelper.floor_double(this.posY) + var24;
                        final int var26 = this.worldObj.getBlockId(var22, var25, var23);
                        if (var26 == Block.snow.blockID) {
                            this.worldObj.setBlockToAir(var22, var25, var23);
                        }
                        else if (var26 == Block.waterlily.blockID) {
                            this.worldObj.destroyBlock(var22, var25, var23, true);
                        }
                    }
                }
                if (this.riddenByEntity != null && this.riddenByEntity.isDead) {
                    this.riddenByEntity = null;
                }
            }
        }
    }
    
    @Override
    public void updateRiderPosition() {
        if (this.riddenByEntity != null) {
            final double var1 = Math.cos(this.rotationYaw * 3.141592653589793 / 180.0) * 0.4;
            final double var2 = Math.sin(this.rotationYaw * 3.141592653589793 / 180.0) * 0.4;
            this.riddenByEntity.setPosition(this.posX + var1, this.posY + this.getMountedYOffset() + this.riddenByEntity.getYOffset(), this.posZ + var2);
        }
    }
    
    @Override
    protected void writeEntityToNBT(final NBTTagCompound par1NBTTagCompound) {
    }
    
    @Override
    protected void readEntityFromNBT(final NBTTagCompound par1NBTTagCompound) {
    }
    
    @Override
    public float getShadowSize() {
        return 0.0f;
    }
    
    @Override
    public boolean interact(final EntityPlayer par1EntityPlayer) {
        if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityPlayer && this.riddenByEntity != par1EntityPlayer) {
            return true;
        }
        if (!this.worldObj.isRemote) {
            par1EntityPlayer.mountEntity(this);
        }
        return true;
    }
    
    public void setDamageTaken(final int par1) {
        this.dataWatcher.updateObject(19, par1);
    }
    
    public int getDamageTaken() {
        return this.dataWatcher.getWatchableObjectInt(19);
    }
    
    public void setTimeSinceHit(final int par1) {
        this.dataWatcher.updateObject(17, par1);
    }
    
    public int getTimeSinceHit() {
        return this.dataWatcher.getWatchableObjectInt(17);
    }
    
    public void setForwardDirection(final int par1) {
        this.dataWatcher.updateObject(18, par1);
    }
    
    public int getForwardDirection() {
        return this.dataWatcher.getWatchableObjectInt(18);
    }
    
    public void func_70270_d(final boolean par1) {
        this.field_70279_a = par1;
    }
}
