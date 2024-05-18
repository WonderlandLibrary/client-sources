package net.minecraft.src;

import java.util.*;

public class EntityArrow extends Entity implements IProjectile
{
    private int xTile;
    private int yTile;
    private int zTile;
    private int inTile;
    private int inData;
    private boolean inGround;
    public int canBePickedUp;
    public int arrowShake;
    public Entity shootingEntity;
    private int ticksInGround;
    private int ticksInAir;
    private double damage;
    private int knockbackStrength;
    
    public EntityArrow(final World par1World) {
        super(par1World);
        this.xTile = -1;
        this.yTile = -1;
        this.zTile = -1;
        this.inTile = 0;
        this.inData = 0;
        this.inGround = false;
        this.canBePickedUp = 0;
        this.arrowShake = 0;
        this.ticksInAir = 0;
        this.damage = 2.0;
        this.renderDistanceWeight = 10.0;
        this.setSize(0.5f, 0.5f);
    }
    
    public EntityArrow(final World par1World, final double par2, final double par4, final double par6) {
        super(par1World);
        this.xTile = -1;
        this.yTile = -1;
        this.zTile = -1;
        this.inTile = 0;
        this.inData = 0;
        this.inGround = false;
        this.canBePickedUp = 0;
        this.arrowShake = 0;
        this.ticksInAir = 0;
        this.damage = 2.0;
        this.renderDistanceWeight = 10.0;
        this.setSize(0.5f, 0.5f);
        this.setPosition(par2, par4, par6);
        this.yOffset = 0.0f;
    }
    
    public EntityArrow(final World par1World, final EntityLiving par2EntityLiving, final EntityLiving par3EntityLiving, final float par4, final float par5) {
        super(par1World);
        this.xTile = -1;
        this.yTile = -1;
        this.zTile = -1;
        this.inTile = 0;
        this.inData = 0;
        this.inGround = false;
        this.canBePickedUp = 0;
        this.arrowShake = 0;
        this.ticksInAir = 0;
        this.damage = 2.0;
        this.renderDistanceWeight = 10.0;
        this.shootingEntity = par2EntityLiving;
        if (par2EntityLiving instanceof EntityPlayer) {
            this.canBePickedUp = 1;
        }
        this.posY = par2EntityLiving.posY + par2EntityLiving.getEyeHeight() - 0.10000000149011612;
        final double var6 = par3EntityLiving.posX - par2EntityLiving.posX;
        final double var7 = par3EntityLiving.boundingBox.minY + par3EntityLiving.height / 3.0f - this.posY;
        final double var8 = par3EntityLiving.posZ - par2EntityLiving.posZ;
        final double var9 = MathHelper.sqrt_double(var6 * var6 + var8 * var8);
        if (var9 >= 1.0E-7) {
            final float var10 = (float)(Math.atan2(var8, var6) * 180.0 / 3.141592653589793) - 90.0f;
            final float var11 = (float)(-(Math.atan2(var7, var9) * 180.0 / 3.141592653589793));
            final double var12 = var6 / var9;
            final double var13 = var8 / var9;
            this.setLocationAndAngles(par2EntityLiving.posX + var12, this.posY, par2EntityLiving.posZ + var13, var10, var11);
            this.yOffset = 0.0f;
            final float var14 = (float)var9 * 0.2f;
            this.setThrowableHeading(var6, var7 + var14, var8, par4, par5);
        }
    }
    
    public EntityArrow(final World par1World, final EntityLiving par2EntityLiving, final float par3) {
        super(par1World);
        this.xTile = -1;
        this.yTile = -1;
        this.zTile = -1;
        this.inTile = 0;
        this.inData = 0;
        this.inGround = false;
        this.canBePickedUp = 0;
        this.arrowShake = 0;
        this.ticksInAir = 0;
        this.damage = 2.0;
        this.renderDistanceWeight = 10.0;
        this.shootingEntity = par2EntityLiving;
        if (par2EntityLiving instanceof EntityPlayer) {
            this.canBePickedUp = 1;
        }
        this.setSize(0.5f, 0.5f);
        this.setLocationAndAngles(par2EntityLiving.posX, par2EntityLiving.posY + par2EntityLiving.getEyeHeight(), par2EntityLiving.posZ, par2EntityLiving.rotationYaw, par2EntityLiving.rotationPitch);
        this.posX -= MathHelper.cos(this.rotationYaw / 180.0f * 3.1415927f) * 0.16f;
        this.posY -= 0.10000000149011612;
        this.posZ -= MathHelper.sin(this.rotationYaw / 180.0f * 3.1415927f) * 0.16f;
        this.setPosition(this.posX, this.posY, this.posZ);
        this.yOffset = 0.0f;
        this.motionX = -MathHelper.sin(this.rotationYaw / 180.0f * 3.1415927f) * MathHelper.cos(this.rotationPitch / 180.0f * 3.1415927f);
        this.motionZ = MathHelper.cos(this.rotationYaw / 180.0f * 3.1415927f) * MathHelper.cos(this.rotationPitch / 180.0f * 3.1415927f);
        this.motionY = -MathHelper.sin(this.rotationPitch / 180.0f * 3.1415927f);
        this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, par3 * 1.5f, 1.0f);
    }
    
    @Override
    protected void entityInit() {
        this.dataWatcher.addObject(16, (byte)0);
    }
    
    @Override
    public void setThrowableHeading(double par1, double par3, double par5, final float par7, final float par8) {
        final float var9 = MathHelper.sqrt_double(par1 * par1 + par3 * par3 + par5 * par5);
        par1 /= var9;
        par3 /= var9;
        par5 /= var9;
        par1 += this.rand.nextGaussian() * (this.rand.nextBoolean() ? -1 : 1) * 0.007499999832361937 * par8;
        par3 += this.rand.nextGaussian() * (this.rand.nextBoolean() ? -1 : 1) * 0.007499999832361937 * par8;
        par5 += this.rand.nextGaussian() * (this.rand.nextBoolean() ? -1 : 1) * 0.007499999832361937 * par8;
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
        this.setPosition(par1, par3, par5);
        this.setRotation(par7, par8);
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
            this.prevRotationPitch = this.rotationPitch;
            this.prevRotationYaw = this.rotationYaw;
            this.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            this.ticksInGround = 0;
        }
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.prevRotationPitch == 0.0f && this.prevRotationYaw == 0.0f) {
            final float var1 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
            final float n = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0 / 3.141592653589793);
            this.rotationYaw = n;
            this.prevRotationYaw = n;
            final float n2 = (float)(Math.atan2(this.motionY, var1) * 180.0 / 3.141592653589793);
            this.rotationPitch = n2;
            this.prevRotationPitch = n2;
        }
        final int var2 = this.worldObj.getBlockId(this.xTile, this.yTile, this.zTile);
        if (var2 > 0) {
            Block.blocksList[var2].setBlockBoundsBasedOnState(this.worldObj, this.xTile, this.yTile, this.zTile);
            final AxisAlignedBB var3 = Block.blocksList[var2].getCollisionBoundingBoxFromPool(this.worldObj, this.xTile, this.yTile, this.zTile);
            if (var3 != null && var3.isVecInside(this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ))) {
                this.inGround = true;
            }
        }
        if (this.arrowShake > 0) {
            --this.arrowShake;
        }
        if (this.inGround) {
            final int var4 = this.worldObj.getBlockId(this.xTile, this.yTile, this.zTile);
            final int var5 = this.worldObj.getBlockMetadata(this.xTile, this.yTile, this.zTile);
            if (var4 == this.inTile && var5 == this.inData) {
                ++this.ticksInGround;
                if (this.ticksInGround == 1200) {
                    this.setDead();
                }
            }
            else {
                this.inGround = false;
                this.motionX *= this.rand.nextFloat() * 0.2f;
                this.motionY *= this.rand.nextFloat() * 0.2f;
                this.motionZ *= this.rand.nextFloat() * 0.2f;
                this.ticksInGround = 0;
                this.ticksInAir = 0;
            }
        }
        else {
            ++this.ticksInAir;
            Vec3 var6 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ);
            Vec3 var7 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
            MovingObjectPosition var8 = this.worldObj.rayTraceBlocks_do_do(var6, var7, false, true);
            var6 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ);
            var7 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
            if (var8 != null) {
                var7 = this.worldObj.getWorldVec3Pool().getVecFromPool(var8.hitVec.xCoord, var8.hitVec.yCoord, var8.hitVec.zCoord);
            }
            Entity var9 = null;
            final List var10 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0, 1.0, 1.0));
            double var11 = 0.0;
            for (int var12 = 0; var12 < var10.size(); ++var12) {
                final Entity var13 = var10.get(var12);
                if (var13.canBeCollidedWith() && (var13 != this.shootingEntity || this.ticksInAir >= 5)) {
                    final float var14 = 0.3f;
                    final AxisAlignedBB var15 = var13.boundingBox.expand(var14, var14, var14);
                    final MovingObjectPosition var16 = var15.calculateIntercept(var6, var7);
                    if (var16 != null) {
                        final double var17 = var6.distanceTo(var16.hitVec);
                        if (var17 < var11 || var11 == 0.0) {
                            var9 = var13;
                            var11 = var17;
                        }
                    }
                }
            }
            if (var9 != null) {
                var8 = new MovingObjectPosition(var9);
            }
            if (var8 != null && var8.entityHit != null && var8.entityHit instanceof EntityPlayer) {
                final EntityPlayer var18 = (EntityPlayer)var8.entityHit;
                if (var18.capabilities.disableDamage || (this.shootingEntity instanceof EntityPlayer && !((EntityPlayer)this.shootingEntity).func_96122_a(var18))) {
                    var8 = null;
                }
            }
            if (var8 != null) {
                if (var8.entityHit != null) {
                    final float var19 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
                    int var20 = MathHelper.ceiling_double_int(var19 * this.damage);
                    if (this.getIsCritical()) {
                        var20 += this.rand.nextInt(var20 / 2 + 2);
                    }
                    DamageSource var21 = null;
                    if (this.shootingEntity == null) {
                        var21 = DamageSource.causeArrowDamage(this, this);
                    }
                    else {
                        var21 = DamageSource.causeArrowDamage(this, this.shootingEntity);
                    }
                    if (this.isBurning() && !(var8.entityHit instanceof EntityEnderman)) {
                        var8.entityHit.setFire(5);
                    }
                    if (var8.entityHit.attackEntityFrom(var21, var20)) {
                        if (var8.entityHit instanceof EntityLiving) {
                            final EntityLiving var22 = (EntityLiving)var8.entityHit;
                            if (!this.worldObj.isRemote) {
                                var22.setArrowCountInEntity(var22.getArrowCountInEntity() + 1);
                            }
                            if (this.knockbackStrength > 0) {
                                final float var23 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
                                if (var23 > 0.0f) {
                                    var8.entityHit.addVelocity(this.motionX * this.knockbackStrength * 0.6000000238418579 / var23, 0.1, this.motionZ * this.knockbackStrength * 0.6000000238418579 / var23);
                                }
                            }
                            if (this.shootingEntity != null) {
                                EnchantmentThorns.func_92096_a(this.shootingEntity, var22, this.rand);
                            }
                            if (this.shootingEntity != null && var8.entityHit != this.shootingEntity && var8.entityHit instanceof EntityPlayer && this.shootingEntity instanceof EntityPlayerMP) {
                                ((EntityPlayerMP)this.shootingEntity).playerNetServerHandler.sendPacketToPlayer(new Packet70GameEvent(6, 0));
                            }
                        }
                        this.playSound("random.bowhit", 1.0f, 1.2f / (this.rand.nextFloat() * 0.2f + 0.9f));
                        if (!(var8.entityHit instanceof EntityEnderman)) {
                            this.setDead();
                        }
                    }
                    else {
                        this.motionX *= -0.10000000149011612;
                        this.motionY *= -0.10000000149011612;
                        this.motionZ *= -0.10000000149011612;
                        this.rotationYaw += 180.0f;
                        this.prevRotationYaw += 180.0f;
                        this.ticksInAir = 0;
                    }
                }
                else {
                    this.xTile = var8.blockX;
                    this.yTile = var8.blockY;
                    this.zTile = var8.blockZ;
                    this.inTile = this.worldObj.getBlockId(this.xTile, this.yTile, this.zTile);
                    this.inData = this.worldObj.getBlockMetadata(this.xTile, this.yTile, this.zTile);
                    this.motionX = (float)(var8.hitVec.xCoord - this.posX);
                    this.motionY = (float)(var8.hitVec.yCoord - this.posY);
                    this.motionZ = (float)(var8.hitVec.zCoord - this.posZ);
                    final float var19 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
                    this.posX -= this.motionX / var19 * 0.05000000074505806;
                    this.posY -= this.motionY / var19 * 0.05000000074505806;
                    this.posZ -= this.motionZ / var19 * 0.05000000074505806;
                    this.playSound("random.bowhit", 1.0f, 1.2f / (this.rand.nextFloat() * 0.2f + 0.9f));
                    this.inGround = true;
                    this.arrowShake = 7;
                    this.setIsCritical(false);
                    if (this.inTile != 0) {
                        Block.blocksList[this.inTile].onEntityCollidedWithBlock(this.worldObj, this.xTile, this.yTile, this.zTile, this);
                    }
                }
            }
            if (this.getIsCritical()) {
                for (int var12 = 0; var12 < 4; ++var12) {
                    this.worldObj.spawnParticle("crit", this.posX + this.motionX * var12 / 4.0, this.posY + this.motionY * var12 / 4.0, this.posZ + this.motionZ * var12 / 4.0, -this.motionX, -this.motionY + 0.2, -this.motionZ);
                }
            }
            this.posX += this.motionX;
            this.posY += this.motionY;
            this.posZ += this.motionZ;
            final float var19 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0 / 3.141592653589793);
            this.rotationPitch = (float)(Math.atan2(this.motionY, var19) * 180.0 / 3.141592653589793);
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
            float var24 = 0.99f;
            final float var14 = 0.05f;
            if (this.isInWater()) {
                for (int var25 = 0; var25 < 4; ++var25) {
                    final float var23 = 0.25f;
                    this.worldObj.spawnParticle("bubble", this.posX - this.motionX * var23, this.posY - this.motionY * var23, this.posZ - this.motionZ * var23, this.motionX, this.motionY, this.motionZ);
                }
                var24 = 0.8f;
            }
            this.motionX *= var24;
            this.motionY *= var24;
            this.motionZ *= var24;
            this.motionY -= var14;
            this.setPosition(this.posX, this.posY, this.posZ);
            this.doBlockCollisions();
        }
    }
    
    public void writeEntityToNBT(final NBTTagCompound par1NBTTagCompound) {
        par1NBTTagCompound.setShort("xTile", (short)this.xTile);
        par1NBTTagCompound.setShort("yTile", (short)this.yTile);
        par1NBTTagCompound.setShort("zTile", (short)this.zTile);
        par1NBTTagCompound.setByte("inTile", (byte)this.inTile);
        par1NBTTagCompound.setByte("inData", (byte)this.inData);
        par1NBTTagCompound.setByte("shake", (byte)this.arrowShake);
        par1NBTTagCompound.setByte("inGround", (byte)(this.inGround ? 1 : 0));
        par1NBTTagCompound.setByte("pickup", (byte)this.canBePickedUp);
        par1NBTTagCompound.setDouble("damage", this.damage);
    }
    
    public void readEntityFromNBT(final NBTTagCompound par1NBTTagCompound) {
        this.xTile = par1NBTTagCompound.getShort("xTile");
        this.yTile = par1NBTTagCompound.getShort("yTile");
        this.zTile = par1NBTTagCompound.getShort("zTile");
        this.inTile = (par1NBTTagCompound.getByte("inTile") & 0xFF);
        this.inData = (par1NBTTagCompound.getByte("inData") & 0xFF);
        this.arrowShake = (par1NBTTagCompound.getByte("shake") & 0xFF);
        this.inGround = (par1NBTTagCompound.getByte("inGround") == 1);
        if (par1NBTTagCompound.hasKey("damage")) {
            this.damage = par1NBTTagCompound.getDouble("damage");
        }
        if (par1NBTTagCompound.hasKey("pickup")) {
            this.canBePickedUp = par1NBTTagCompound.getByte("pickup");
        }
        else if (par1NBTTagCompound.hasKey("player")) {
            this.canBePickedUp = (par1NBTTagCompound.getBoolean("player") ? 1 : 0);
        }
    }
    
    @Override
    public void onCollideWithPlayer(final EntityPlayer par1EntityPlayer) {
        if (!this.worldObj.isRemote && this.inGround && this.arrowShake <= 0) {
            boolean var2 = this.canBePickedUp == 1 || (this.canBePickedUp == 2 && par1EntityPlayer.capabilities.isCreativeMode);
            if (this.canBePickedUp == 1 && !par1EntityPlayer.inventory.addItemStackToInventory(new ItemStack(Item.arrow, 1))) {
                var2 = false;
            }
            if (var2) {
                this.playSound("random.pop", 0.2f, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7f + 1.0f) * 2.0f);
                par1EntityPlayer.onItemPickup(this, 1);
                this.setDead();
            }
        }
    }
    
    @Override
    protected boolean canTriggerWalking() {
        return false;
    }
    
    @Override
    public float getShadowSize() {
        return 0.0f;
    }
    
    public void setDamage(final double par1) {
        this.damage = par1;
    }
    
    public double getDamage() {
        return this.damage;
    }
    
    public void setKnockbackStrength(final int par1) {
        this.knockbackStrength = par1;
    }
    
    @Override
    public boolean canAttackWithItem() {
        return false;
    }
    
    public void setIsCritical(final boolean par1) {
        final byte var2 = this.dataWatcher.getWatchableObjectByte(16);
        if (par1) {
            this.dataWatcher.updateObject(16, (byte)(var2 | 0x1));
        }
        else {
            this.dataWatcher.updateObject(16, (byte)(var2 & 0xFFFFFFFE));
        }
    }
    
    public boolean getIsCritical() {
        final byte var1 = this.dataWatcher.getWatchableObjectByte(16);
        return (var1 & 0x1) != 0x0;
    }
}
