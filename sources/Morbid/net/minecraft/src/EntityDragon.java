package net.minecraft.src;

import java.util.*;

public class EntityDragon extends EntityLiving implements IBossDisplayData, IEntityMultiPart
{
    public double targetX;
    public double targetY;
    public double targetZ;
    public double[][] ringBuffer;
    public int ringBufferIndex;
    public EntityDragonPart[] dragonPartArray;
    public EntityDragonPart dragonPartHead;
    public EntityDragonPart dragonPartBody;
    public EntityDragonPart dragonPartTail1;
    public EntityDragonPart dragonPartTail2;
    public EntityDragonPart dragonPartTail3;
    public EntityDragonPart dragonPartWing1;
    public EntityDragonPart dragonPartWing2;
    public float prevAnimTime;
    public float animTime;
    public boolean forceNewTarget;
    public boolean slowed;
    private Entity target;
    public int deathTicks;
    public EntityEnderCrystal healingEnderCrystal;
    
    public EntityDragon(final World par1World) {
        super(par1World);
        this.ringBuffer = new double[64][3];
        this.ringBufferIndex = -1;
        this.prevAnimTime = 0.0f;
        this.animTime = 0.0f;
        this.forceNewTarget = false;
        this.slowed = false;
        this.deathTicks = 0;
        this.healingEnderCrystal = null;
        this.dragonPartArray = new EntityDragonPart[] { this.dragonPartHead = new EntityDragonPart(this, "head", 6.0f, 6.0f), this.dragonPartBody = new EntityDragonPart(this, "body", 8.0f, 8.0f), this.dragonPartTail1 = new EntityDragonPart(this, "tail", 4.0f, 4.0f), this.dragonPartTail2 = new EntityDragonPart(this, "tail", 4.0f, 4.0f), this.dragonPartTail3 = new EntityDragonPart(this, "tail", 4.0f, 4.0f), this.dragonPartWing1 = new EntityDragonPart(this, "wing", 4.0f, 4.0f), this.dragonPartWing2 = new EntityDragonPart(this, "wing", 4.0f, 4.0f) };
        this.setEntityHealth(this.getMaxHealth());
        this.texture = "/mob/enderdragon/ender.png";
        this.setSize(16.0f, 8.0f);
        this.noClip = true;
        this.isImmuneToFire = true;
        this.targetY = 100.0;
        this.ignoreFrustumCheck = true;
    }
    
    @Override
    public int getMaxHealth() {
        return 200;
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, new Integer(this.getMaxHealth()));
    }
    
    public double[] getMovementOffsets(final int par1, float par2) {
        if (this.health <= 0) {
            par2 = 0.0f;
        }
        par2 = 1.0f - par2;
        final int var3 = this.ringBufferIndex - par1 * 1 & 0x3F;
        final int var4 = this.ringBufferIndex - par1 * 1 - 1 & 0x3F;
        final double[] var5 = new double[3];
        double var6 = this.ringBuffer[var3][0];
        double var7 = MathHelper.wrapAngleTo180_double(this.ringBuffer[var4][0] - var6);
        var5[0] = var6 + var7 * par2;
        var6 = this.ringBuffer[var3][1];
        var7 = this.ringBuffer[var4][1] - var6;
        var5[1] = var6 + var7 * par2;
        var5[2] = this.ringBuffer[var3][2] + (this.ringBuffer[var4][2] - this.ringBuffer[var3][2]) * par2;
        return var5;
    }
    
    @Override
    public void onLivingUpdate() {
        if (!this.worldObj.isRemote) {
            this.dataWatcher.updateObject(16, this.health);
        }
        else {
            final float var1 = MathHelper.cos(this.animTime * 3.1415927f * 2.0f);
            final float var2 = MathHelper.cos(this.prevAnimTime * 3.1415927f * 2.0f);
            if (var2 <= -0.3f && var1 >= -0.3f) {
                this.worldObj.playSound(this.posX, this.posY, this.posZ, "mob.enderdragon.wings", 5.0f, 0.8f + this.rand.nextFloat() * 0.3f, false);
            }
        }
        this.prevAnimTime = this.animTime;
        if (this.health <= 0) {
            final float var1 = (this.rand.nextFloat() - 0.5f) * 8.0f;
            final float var2 = (this.rand.nextFloat() - 0.5f) * 4.0f;
            final float var3 = (this.rand.nextFloat() - 0.5f) * 8.0f;
            this.worldObj.spawnParticle("largeexplode", this.posX + var1, this.posY + 2.0 + var2, this.posZ + var3, 0.0, 0.0, 0.0);
        }
        else {
            this.updateDragonEnderCrystal();
            float var1 = 0.2f / (MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ) * 10.0f + 1.0f);
            var1 *= (float)Math.pow(2.0, this.motionY);
            if (this.slowed) {
                this.animTime += var1 * 0.5f;
            }
            else {
                this.animTime += var1;
            }
            this.rotationYaw = MathHelper.wrapAngleTo180_float(this.rotationYaw);
            if (this.ringBufferIndex < 0) {
                for (int var4 = 0; var4 < this.ringBuffer.length; ++var4) {
                    this.ringBuffer[var4][0] = this.rotationYaw;
                    this.ringBuffer[var4][1] = this.posY;
                }
            }
            if (++this.ringBufferIndex == this.ringBuffer.length) {
                this.ringBufferIndex = 0;
            }
            this.ringBuffer[this.ringBufferIndex][0] = this.rotationYaw;
            this.ringBuffer[this.ringBufferIndex][1] = this.posY;
            if (this.worldObj.isRemote) {
                if (this.newPosRotationIncrements > 0) {
                    final double var5 = this.posX + (this.newPosX - this.posX) / this.newPosRotationIncrements;
                    final double var6 = this.posY + (this.newPosY - this.posY) / this.newPosRotationIncrements;
                    final double var7 = this.posZ + (this.newPosZ - this.posZ) / this.newPosRotationIncrements;
                    final double var8 = MathHelper.wrapAngleTo180_double(this.newRotationYaw - this.rotationYaw);
                    this.rotationYaw += (float)(var8 / this.newPosRotationIncrements);
                    this.rotationPitch += (float)((this.newRotationPitch - this.rotationPitch) / this.newPosRotationIncrements);
                    --this.newPosRotationIncrements;
                    this.setPosition(var5, var6, var7);
                    this.setRotation(this.rotationYaw, this.rotationPitch);
                }
            }
            else {
                final double var5 = this.targetX - this.posX;
                double var6 = this.targetY - this.posY;
                final double var7 = this.targetZ - this.posZ;
                final double var8 = var5 * var5 + var6 * var6 + var7 * var7;
                if (this.target != null) {
                    this.targetX = this.target.posX;
                    this.targetZ = this.target.posZ;
                    final double var9 = this.targetX - this.posX;
                    final double var10 = this.targetZ - this.posZ;
                    final double var11 = Math.sqrt(var9 * var9 + var10 * var10);
                    double var12 = 0.4000000059604645 + var11 / 80.0 - 1.0;
                    if (var12 > 10.0) {
                        var12 = 10.0;
                    }
                    this.targetY = this.target.boundingBox.minY + var12;
                }
                else {
                    this.targetX += this.rand.nextGaussian() * 2.0;
                    this.targetZ += this.rand.nextGaussian() * 2.0;
                }
                if (this.forceNewTarget || var8 < 100.0 || var8 > 22500.0 || this.isCollidedHorizontally || this.isCollidedVertically) {
                    this.setNewTarget();
                }
                var6 /= MathHelper.sqrt_double(var5 * var5 + var7 * var7);
                final float var13 = 0.6f;
                if (var6 < -var13) {
                    var6 = -var13;
                }
                if (var6 > var13) {
                    var6 = var13;
                }
                this.motionY += var6 * 0.10000000149011612;
                this.rotationYaw = MathHelper.wrapAngleTo180_float(this.rotationYaw);
                final double var14 = 180.0 - Math.atan2(var5, var7) * 180.0 / 3.141592653589793;
                double var15 = MathHelper.wrapAngleTo180_double(var14 - this.rotationYaw);
                if (var15 > 50.0) {
                    var15 = 50.0;
                }
                if (var15 < -50.0) {
                    var15 = -50.0;
                }
                final Vec3 var16 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.targetX - this.posX, this.targetY - this.posY, this.targetZ - this.posZ).normalize();
                final Vec3 var17 = this.worldObj.getWorldVec3Pool().getVecFromPool(MathHelper.sin(this.rotationYaw * 3.1415927f / 180.0f), this.motionY, -MathHelper.cos(this.rotationYaw * 3.1415927f / 180.0f)).normalize();
                float var18 = (float)(var17.dotProduct(var16) + 0.5) / 1.5f;
                if (var18 < 0.0f) {
                    var18 = 0.0f;
                }
                this.randomYawVelocity *= 0.8f;
                final float var19 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ) * 1.0f + 1.0f;
                double var20 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ) * 1.0 + 1.0;
                if (var20 > 40.0) {
                    var20 = 40.0;
                }
                this.randomYawVelocity += (float)(var15 * (0.699999988079071 / var20 / var19));
                this.rotationYaw += this.randomYawVelocity * 0.1f;
                final float var21 = (float)(2.0 / (var20 + 1.0));
                final float var22 = 0.06f;
                this.moveFlying(0.0f, -1.0f, var22 * (var18 * var21 + (1.0f - var21)));
                if (this.slowed) {
                    this.moveEntity(this.motionX * 0.800000011920929, this.motionY * 0.800000011920929, this.motionZ * 0.800000011920929);
                }
                else {
                    this.moveEntity(this.motionX, this.motionY, this.motionZ);
                }
                final Vec3 var23 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.motionX, this.motionY, this.motionZ).normalize();
                float var24 = (float)(var23.dotProduct(var17) + 1.0) / 2.0f;
                var24 = 0.8f + 0.15f * var24;
                this.motionX *= var24;
                this.motionZ *= var24;
                this.motionY *= 0.9100000262260437;
            }
            this.renderYawOffset = this.rotationYaw;
            final EntityDragonPart dragonPartHead = this.dragonPartHead;
            final EntityDragonPart dragonPartHead2 = this.dragonPartHead;
            final float n = 3.0f;
            dragonPartHead2.height = n;
            dragonPartHead.width = n;
            final EntityDragonPart dragonPartTail1 = this.dragonPartTail1;
            final EntityDragonPart dragonPartTail2 = this.dragonPartTail1;
            final float n2 = 2.0f;
            dragonPartTail2.height = n2;
            dragonPartTail1.width = n2;
            final EntityDragonPart dragonPartTail3 = this.dragonPartTail2;
            final EntityDragonPart dragonPartTail4 = this.dragonPartTail2;
            final float n3 = 2.0f;
            dragonPartTail4.height = n3;
            dragonPartTail3.width = n3;
            final EntityDragonPart dragonPartTail5 = this.dragonPartTail3;
            final EntityDragonPart dragonPartTail6 = this.dragonPartTail3;
            final float n4 = 2.0f;
            dragonPartTail6.height = n4;
            dragonPartTail5.width = n4;
            this.dragonPartBody.height = 3.0f;
            this.dragonPartBody.width = 5.0f;
            this.dragonPartWing1.height = 2.0f;
            this.dragonPartWing1.width = 4.0f;
            this.dragonPartWing2.height = 3.0f;
            this.dragonPartWing2.width = 4.0f;
            final float var2 = (float)(this.getMovementOffsets(5, 1.0f)[1] - this.getMovementOffsets(10, 1.0f)[1]) * 10.0f / 180.0f * 3.1415927f;
            final float var3 = MathHelper.cos(var2);
            final float var25 = -MathHelper.sin(var2);
            final float var26 = this.rotationYaw * 3.1415927f / 180.0f;
            final float var27 = MathHelper.sin(var26);
            final float var28 = MathHelper.cos(var26);
            this.dragonPartBody.onUpdate();
            this.dragonPartBody.setLocationAndAngles(this.posX + var27 * 0.5f, this.posY, this.posZ - var28 * 0.5f, 0.0f, 0.0f);
            this.dragonPartWing1.onUpdate();
            this.dragonPartWing1.setLocationAndAngles(this.posX + var28 * 4.5f, this.posY + 2.0, this.posZ + var27 * 4.5f, 0.0f, 0.0f);
            this.dragonPartWing2.onUpdate();
            this.dragonPartWing2.setLocationAndAngles(this.posX - var28 * 4.5f, this.posY + 2.0, this.posZ - var27 * 4.5f, 0.0f, 0.0f);
            if (!this.worldObj.isRemote && this.hurtTime == 0) {
                this.collideWithEntities(this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.dragonPartWing1.boundingBox.expand(4.0, 2.0, 4.0).offset(0.0, -2.0, 0.0)));
                this.collideWithEntities(this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.dragonPartWing2.boundingBox.expand(4.0, 2.0, 4.0).offset(0.0, -2.0, 0.0)));
                this.attackEntitiesInList(this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.dragonPartHead.boundingBox.expand(1.0, 1.0, 1.0)));
            }
            final double[] var29 = this.getMovementOffsets(5, 1.0f);
            final double[] var30 = this.getMovementOffsets(0, 1.0f);
            final float var13 = MathHelper.sin(this.rotationYaw * 3.1415927f / 180.0f - this.randomYawVelocity * 0.01f);
            final float var31 = MathHelper.cos(this.rotationYaw * 3.1415927f / 180.0f - this.randomYawVelocity * 0.01f);
            this.dragonPartHead.onUpdate();
            this.dragonPartHead.setLocationAndAngles(this.posX + var13 * 5.5f * var3, this.posY + (var30[1] - var29[1]) * 1.0 + var25 * 5.5f, this.posZ - var31 * 5.5f * var3, 0.0f, 0.0f);
            for (int var32 = 0; var32 < 3; ++var32) {
                EntityDragonPart var33 = null;
                if (var32 == 0) {
                    var33 = this.dragonPartTail1;
                }
                if (var32 == 1) {
                    var33 = this.dragonPartTail2;
                }
                if (var32 == 2) {
                    var33 = this.dragonPartTail3;
                }
                final double[] var34 = this.getMovementOffsets(12 + var32 * 2, 1.0f);
                final float var35 = this.rotationYaw * 3.1415927f / 180.0f + this.simplifyAngle(var34[0] - var29[0]) * 3.1415927f / 180.0f * 1.0f;
                final float var36 = MathHelper.sin(var35);
                final float var37 = MathHelper.cos(var35);
                final float var38 = 1.5f;
                final float var39 = (var32 + 1) * 2.0f;
                var33.onUpdate();
                var33.setLocationAndAngles(this.posX - (var27 * var38 + var36 * var39) * var3, this.posY + (var34[1] - var29[1]) * 1.0 - (var39 + var38) * var25 + 1.5, this.posZ + (var28 * var38 + var37 * var39) * var3, 0.0f, 0.0f);
            }
            if (!this.worldObj.isRemote) {
                this.slowed = (this.destroyBlocksInAABB(this.dragonPartHead.boundingBox) | this.destroyBlocksInAABB(this.dragonPartBody.boundingBox));
            }
        }
    }
    
    private void updateDragonEnderCrystal() {
        if (this.healingEnderCrystal != null) {
            if (this.healingEnderCrystal.isDead) {
                if (!this.worldObj.isRemote) {
                    this.attackEntityFromPart(this.dragonPartHead, DamageSource.setExplosionSource(null), 10);
                }
                this.healingEnderCrystal = null;
            }
            else if (this.ticksExisted % 10 == 0 && this.getHealth() < this.getMaxHealth()) {
                this.setEntityHealth(this.getHealth() + 1);
            }
        }
        if (this.rand.nextInt(10) == 0) {
            final float var1 = 32.0f;
            final List var2 = this.worldObj.getEntitiesWithinAABB(EntityEnderCrystal.class, this.boundingBox.expand(var1, var1, var1));
            EntityEnderCrystal var3 = null;
            double var4 = Double.MAX_VALUE;
            for (final EntityEnderCrystal var6 : var2) {
                final double var7 = var6.getDistanceSqToEntity(this);
                if (var7 < var4) {
                    var4 = var7;
                    var3 = var6;
                }
            }
            this.healingEnderCrystal = var3;
        }
    }
    
    private void collideWithEntities(final List par1List) {
        final double var2 = (this.dragonPartBody.boundingBox.minX + this.dragonPartBody.boundingBox.maxX) / 2.0;
        final double var3 = (this.dragonPartBody.boundingBox.minZ + this.dragonPartBody.boundingBox.maxZ) / 2.0;
        for (final Entity var5 : par1List) {
            if (var5 instanceof EntityLiving) {
                final double var6 = var5.posX - var2;
                final double var7 = var5.posZ - var3;
                final double var8 = var6 * var6 + var7 * var7;
                var5.addVelocity(var6 / var8 * 4.0, 0.20000000298023224, var7 / var8 * 4.0);
            }
        }
    }
    
    private void attackEntitiesInList(final List par1List) {
        for (int var2 = 0; var2 < par1List.size(); ++var2) {
            final Entity var3 = par1List.get(var2);
            if (var3 instanceof EntityLiving) {
                var3.attackEntityFrom(DamageSource.causeMobDamage(this), 10);
            }
        }
    }
    
    private void setNewTarget() {
        this.forceNewTarget = false;
        if (this.rand.nextInt(2) == 0 && !this.worldObj.playerEntities.isEmpty()) {
            this.target = this.worldObj.playerEntities.get(this.rand.nextInt(this.worldObj.playerEntities.size()));
        }
        else {
            boolean var1 = false;
            do {
                this.targetX = 0.0;
                this.targetY = 70.0f + this.rand.nextFloat() * 50.0f;
                this.targetZ = 0.0;
                this.targetX += this.rand.nextFloat() * 120.0f - 60.0f;
                this.targetZ += this.rand.nextFloat() * 120.0f - 60.0f;
                final double var2 = this.posX - this.targetX;
                final double var3 = this.posY - this.targetY;
                final double var4 = this.posZ - this.targetZ;
                var1 = (var2 * var2 + var3 * var3 + var4 * var4 > 100.0);
            } while (!var1);
            this.target = null;
        }
    }
    
    private float simplifyAngle(final double par1) {
        return (float)MathHelper.wrapAngleTo180_double(par1);
    }
    
    private boolean destroyBlocksInAABB(final AxisAlignedBB par1AxisAlignedBB) {
        final int var2 = MathHelper.floor_double(par1AxisAlignedBB.minX);
        final int var3 = MathHelper.floor_double(par1AxisAlignedBB.minY);
        final int var4 = MathHelper.floor_double(par1AxisAlignedBB.minZ);
        final int var5 = MathHelper.floor_double(par1AxisAlignedBB.maxX);
        final int var6 = MathHelper.floor_double(par1AxisAlignedBB.maxY);
        final int var7 = MathHelper.floor_double(par1AxisAlignedBB.maxZ);
        boolean var8 = false;
        boolean var9 = false;
        for (int var10 = var2; var10 <= var5; ++var10) {
            for (int var11 = var3; var11 <= var6; ++var11) {
                for (int var12 = var4; var12 <= var7; ++var12) {
                    final int var13 = this.worldObj.getBlockId(var10, var11, var12);
                    if (var13 != 0) {
                        if (var13 != Block.obsidian.blockID && var13 != Block.whiteStone.blockID && var13 != Block.bedrock.blockID && this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing")) {
                            var9 = (this.worldObj.setBlockToAir(var10, var11, var12) || var9);
                        }
                        else {
                            var8 = true;
                        }
                    }
                }
            }
        }
        if (var9) {
            final double var14 = par1AxisAlignedBB.minX + (par1AxisAlignedBB.maxX - par1AxisAlignedBB.minX) * this.rand.nextFloat();
            final double var15 = par1AxisAlignedBB.minY + (par1AxisAlignedBB.maxY - par1AxisAlignedBB.minY) * this.rand.nextFloat();
            final double var16 = par1AxisAlignedBB.minZ + (par1AxisAlignedBB.maxZ - par1AxisAlignedBB.minZ) * this.rand.nextFloat();
            this.worldObj.spawnParticle("largeexplode", var14, var15, var16, 0.0, 0.0, 0.0);
        }
        return var8;
    }
    
    @Override
    public boolean attackEntityFromPart(final EntityDragonPart par1EntityDragonPart, final DamageSource par2DamageSource, int par3) {
        if (par1EntityDragonPart != this.dragonPartHead) {
            par3 = par3 / 4 + 1;
        }
        final float var4 = this.rotationYaw * 3.1415927f / 180.0f;
        final float var5 = MathHelper.sin(var4);
        final float var6 = MathHelper.cos(var4);
        this.targetX = this.posX + var5 * 5.0f + (this.rand.nextFloat() - 0.5f) * 2.0f;
        this.targetY = this.posY + this.rand.nextFloat() * 3.0f + 1.0;
        this.targetZ = this.posZ - var6 * 5.0f + (this.rand.nextFloat() - 0.5f) * 2.0f;
        this.target = null;
        if (par2DamageSource.getEntity() instanceof EntityPlayer || par2DamageSource.isExplosion()) {
            this.func_82195_e(par2DamageSource, par3);
        }
        return true;
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource par1DamageSource, final int par2) {
        return false;
    }
    
    protected boolean func_82195_e(final DamageSource par1DamageSource, final int par2) {
        return super.attackEntityFrom(par1DamageSource, par2);
    }
    
    @Override
    protected void onDeathUpdate() {
        ++this.deathTicks;
        if (this.deathTicks >= 180 && this.deathTicks <= 200) {
            final float var1 = (this.rand.nextFloat() - 0.5f) * 8.0f;
            final float var2 = (this.rand.nextFloat() - 0.5f) * 4.0f;
            final float var3 = (this.rand.nextFloat() - 0.5f) * 8.0f;
            this.worldObj.spawnParticle("hugeexplosion", this.posX + var1, this.posY + 2.0 + var2, this.posZ + var3, 0.0, 0.0, 0.0);
        }
        if (!this.worldObj.isRemote) {
            if (this.deathTicks > 150 && this.deathTicks % 5 == 0) {
                int var4 = 1000;
                while (var4 > 0) {
                    final int var5 = EntityXPOrb.getXPSplit(var4);
                    var4 -= var5;
                    this.worldObj.spawnEntityInWorld(new EntityXPOrb(this.worldObj, this.posX, this.posY, this.posZ, var5));
                }
            }
            if (this.deathTicks == 1) {
                this.worldObj.func_82739_e(1018, (int)this.posX, (int)this.posY, (int)this.posZ, 0);
            }
        }
        this.moveEntity(0.0, 0.10000000149011612, 0.0);
        final float n = this.rotationYaw + 20.0f;
        this.rotationYaw = n;
        this.renderYawOffset = n;
        if (this.deathTicks == 200 && !this.worldObj.isRemote) {
            int var4 = 2000;
            while (var4 > 0) {
                final int var5 = EntityXPOrb.getXPSplit(var4);
                var4 -= var5;
                this.worldObj.spawnEntityInWorld(new EntityXPOrb(this.worldObj, this.posX, this.posY, this.posZ, var5));
            }
            this.createEnderPortal(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posZ));
            this.setDead();
        }
    }
    
    private void createEnderPortal(final int par1, final int par2) {
        final byte var3 = 64;
        BlockEndPortal.bossDefeated = true;
        final byte var4 = 4;
        for (int var5 = var3 - 1; var5 <= var3 + 32; ++var5) {
            for (int var6 = par1 - var4; var6 <= par1 + var4; ++var6) {
                for (int var7 = par2 - var4; var7 <= par2 + var4; ++var7) {
                    final double var8 = var6 - par1;
                    final double var9 = var7 - par2;
                    final double var10 = var8 * var8 + var9 * var9;
                    if (var10 <= (var4 - 0.5) * (var4 - 0.5)) {
                        if (var5 < var3) {
                            if (var10 <= (var4 - 1 - 0.5) * (var4 - 1 - 0.5)) {
                                this.worldObj.setBlock(var6, var5, var7, Block.bedrock.blockID);
                            }
                        }
                        else if (var5 > var3) {
                            this.worldObj.setBlock(var6, var5, var7, 0);
                        }
                        else if (var10 > (var4 - 1 - 0.5) * (var4 - 1 - 0.5)) {
                            this.worldObj.setBlock(var6, var5, var7, Block.bedrock.blockID);
                        }
                        else {
                            this.worldObj.setBlock(var6, var5, var7, Block.endPortal.blockID);
                        }
                    }
                }
            }
        }
        this.worldObj.setBlock(par1, var3 + 0, par2, Block.bedrock.blockID);
        this.worldObj.setBlock(par1, var3 + 1, par2, Block.bedrock.blockID);
        this.worldObj.setBlock(par1, var3 + 2, par2, Block.bedrock.blockID);
        this.worldObj.setBlock(par1 - 1, var3 + 2, par2, Block.torchWood.blockID);
        this.worldObj.setBlock(par1 + 1, var3 + 2, par2, Block.torchWood.blockID);
        this.worldObj.setBlock(par1, var3 + 2, par2 - 1, Block.torchWood.blockID);
        this.worldObj.setBlock(par1, var3 + 2, par2 + 1, Block.torchWood.blockID);
        this.worldObj.setBlock(par1, var3 + 3, par2, Block.bedrock.blockID);
        this.worldObj.setBlock(par1, var3 + 4, par2, Block.dragonEgg.blockID);
        BlockEndPortal.bossDefeated = false;
    }
    
    @Override
    protected void despawnEntity() {
    }
    
    @Override
    public Entity[] getParts() {
        return this.dragonPartArray;
    }
    
    @Override
    public boolean canBeCollidedWith() {
        return false;
    }
    
    @Override
    public int getBossHealth() {
        return this.dataWatcher.getWatchableObjectInt(16);
    }
    
    @Override
    public World func_82194_d() {
        return this.worldObj;
    }
    
    @Override
    protected String getLivingSound() {
        return "mob.enderdragon.growl";
    }
    
    @Override
    protected String getHurtSound() {
        return "mob.enderdragon.hit";
    }
    
    @Override
    protected float getSoundVolume() {
        return 5.0f;
    }
}
