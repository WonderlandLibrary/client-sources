package net.minecraft.src;

import net.minecraft.server.*;
import java.util.*;
import java.util.concurrent.*;

public abstract class Entity
{
    private static int nextEntityID;
    public int entityId;
    public double renderDistanceWeight;
    public boolean preventEntitySpawning;
    public Entity riddenByEntity;
    public Entity ridingEntity;
    public boolean field_98038_p;
    public World worldObj;
    public double prevPosX;
    public double prevPosY;
    public double prevPosZ;
    public double posX;
    public double posY;
    public double posZ;
    public double motionX;
    public double motionY;
    public double motionZ;
    public float rotationYaw;
    public float rotationPitch;
    public float prevRotationYaw;
    public float prevRotationPitch;
    public final AxisAlignedBB boundingBox;
    public boolean onGround;
    public boolean isCollidedHorizontally;
    public boolean isCollidedVertically;
    public boolean isCollided;
    public boolean velocityChanged;
    protected boolean isInWeb;
    public boolean field_70135_K;
    public boolean isDead;
    public float yOffset;
    public float width;
    public float height;
    public float prevDistanceWalkedModified;
    public float distanceWalkedModified;
    public float distanceWalkedOnStepModified;
    public float fallDistance;
    private int nextStepDistance;
    public double lastTickPosX;
    public double lastTickPosY;
    public double lastTickPosZ;
    public float ySize;
    public float stepHeight;
    public boolean noClip;
    public float entityCollisionReduction;
    protected Random rand;
    public int ticksExisted;
    public int fireResistance;
    private int fire;
    protected boolean inWater;
    public int hurtResistantTime;
    private boolean firstUpdate;
    public String skinUrl;
    public String cloakUrl;
    protected boolean isImmuneToFire;
    protected DataWatcher dataWatcher;
    private double entityRiderPitchDelta;
    private double entityRiderYawDelta;
    public boolean addedToChunk;
    public int chunkCoordX;
    public int chunkCoordY;
    public int chunkCoordZ;
    public int serverPosX;
    public int serverPosY;
    public int serverPosZ;
    public boolean ignoreFrustumCheck;
    public boolean isAirBorne;
    public int timeUntilPortal;
    protected boolean inPortal;
    protected int field_82153_h;
    public int dimension;
    protected int teleportDirection;
    private boolean invulnerable;
    private UUID entityUniqueID;
    public EnumEntitySize myEntitySize;
    
    static {
        Entity.nextEntityID = 0;
    }
    
    public Entity(final World par1World) {
        this.entityId = Entity.nextEntityID++;
        this.renderDistanceWeight = 1.0;
        this.preventEntitySpawning = false;
        this.boundingBox = AxisAlignedBB.getBoundingBox(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        this.onGround = false;
        this.isCollided = false;
        this.velocityChanged = false;
        this.field_70135_K = true;
        this.isDead = false;
        this.yOffset = 0.0f;
        this.width = 0.6f;
        this.height = 1.8f;
        this.prevDistanceWalkedModified = 0.0f;
        this.distanceWalkedModified = 0.0f;
        this.distanceWalkedOnStepModified = 0.0f;
        this.fallDistance = 0.0f;
        this.nextStepDistance = 1;
        this.ySize = 0.0f;
        this.stepHeight = 0.0f;
        this.noClip = false;
        this.entityCollisionReduction = 0.0f;
        this.rand = new Random();
        this.ticksExisted = 0;
        this.fireResistance = 1;
        this.fire = 0;
        this.inWater = false;
        this.hurtResistantTime = 0;
        this.firstUpdate = true;
        this.isImmuneToFire = false;
        this.dataWatcher = new DataWatcher();
        this.addedToChunk = false;
        this.teleportDirection = 0;
        this.invulnerable = false;
        this.entityUniqueID = UUID.randomUUID();
        this.myEntitySize = EnumEntitySize.SIZE_2;
        this.worldObj = par1World;
        this.setPosition(0.0, 0.0, 0.0);
        if (par1World != null) {
            this.dimension = par1World.provider.dimensionId;
        }
        this.dataWatcher.addObject(0, (byte)0);
        this.dataWatcher.addObject(1, (short)300);
        this.entityInit();
    }
    
    protected abstract void entityInit();
    
    public DataWatcher getDataWatcher() {
        return this.dataWatcher;
    }
    
    @Override
    public boolean equals(final Object par1Obj) {
        return par1Obj instanceof Entity && ((Entity)par1Obj).entityId == this.entityId;
    }
    
    @Override
    public int hashCode() {
        return this.entityId;
    }
    
    protected void preparePlayerToSpawn() {
        if (this.worldObj != null) {
            while (this.posY > 0.0) {
                this.setPosition(this.posX, this.posY, this.posZ);
                if (this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty()) {
                    break;
                }
                ++this.posY;
            }
            final double motionX = 0.0;
            this.motionZ = motionX;
            this.motionY = motionX;
            this.motionX = motionX;
            this.rotationPitch = 0.0f;
        }
    }
    
    public void setDead() {
        this.isDead = true;
    }
    
    protected void setSize(final float par1, final float par2) {
        if (par1 != this.width || par2 != this.height) {
            this.width = par1;
            this.height = par2;
            this.boundingBox.maxX = this.boundingBox.minX + this.width;
            this.boundingBox.maxZ = this.boundingBox.minZ + this.width;
            this.boundingBox.maxY = this.boundingBox.minY + this.height;
        }
        final float var3 = par1 % 2.0f;
        if (var3 < 0.375) {
            this.myEntitySize = EnumEntitySize.SIZE_1;
        }
        else if (var3 < 0.75) {
            this.myEntitySize = EnumEntitySize.SIZE_2;
        }
        else if (var3 < 1.0) {
            this.myEntitySize = EnumEntitySize.SIZE_3;
        }
        else if (var3 < 1.375) {
            this.myEntitySize = EnumEntitySize.SIZE_4;
        }
        else if (var3 < 1.75) {
            this.myEntitySize = EnumEntitySize.SIZE_5;
        }
        else {
            this.myEntitySize = EnumEntitySize.SIZE_6;
        }
    }
    
    protected void setRotation(final float par1, final float par2) {
        this.rotationYaw = par1 % 360.0f;
        this.rotationPitch = par2 % 360.0f;
    }
    
    public void setPosition(final double par1, final double par3, final double par5) {
        this.posX = par1;
        this.posY = par3;
        this.posZ = par5;
        final float var7 = this.width / 2.0f;
        final float var8 = this.height;
        this.boundingBox.setBounds(par1 - var7, par3 - this.yOffset + this.ySize, par5 - var7, par1 + var7, par3 - this.yOffset + this.ySize + var8, par5 + var7);
    }
    
    public void setAngles(final float par1, final float par2) {
        final float var3 = this.rotationPitch;
        final float var4 = this.rotationYaw;
        this.rotationYaw += (float)(par1 * 0.15);
        this.rotationPitch -= (float)(par2 * 0.15);
        if (this.rotationPitch < -90.0f) {
            this.rotationPitch = -90.0f;
        }
        if (this.rotationPitch > 90.0f) {
            this.rotationPitch = 90.0f;
        }
        this.prevRotationPitch += this.rotationPitch - var3;
        this.prevRotationYaw += this.rotationYaw - var4;
    }
    
    public void onUpdate() {
        this.onEntityUpdate();
    }
    
    public void onEntityUpdate() {
        this.worldObj.theProfiler.startSection("entityBaseTick");
        if (this.ridingEntity != null && this.ridingEntity.isDead) {
            this.ridingEntity = null;
        }
        this.prevDistanceWalkedModified = this.distanceWalkedModified;
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.prevRotationPitch = this.rotationPitch;
        this.prevRotationYaw = this.rotationYaw;
        if (!this.worldObj.isRemote && this.worldObj instanceof WorldServer) {
            this.worldObj.theProfiler.startSection("portal");
            final MinecraftServer var2 = ((WorldServer)this.worldObj).getMinecraftServer();
            final int var3 = this.getMaxInPortalTime();
            if (this.inPortal) {
                if (var2.getAllowNether()) {
                    if (this.ridingEntity == null && this.field_82153_h++ >= var3) {
                        this.field_82153_h = var3;
                        this.timeUntilPortal = this.getPortalCooldown();
                        byte var4;
                        if (this.worldObj.provider.dimensionId == -1) {
                            var4 = 0;
                        }
                        else {
                            var4 = -1;
                        }
                        this.travelToDimension(var4);
                    }
                    this.inPortal = false;
                }
            }
            else {
                if (this.field_82153_h > 0) {
                    this.field_82153_h -= 4;
                }
                if (this.field_82153_h < 0) {
                    this.field_82153_h = 0;
                }
            }
            if (this.timeUntilPortal > 0) {
                --this.timeUntilPortal;
            }
            this.worldObj.theProfiler.endSection();
        }
        if (this.isSprinting() && !this.isInWater()) {
            final int var5 = MathHelper.floor_double(this.posX);
            final int var3 = MathHelper.floor_double(this.posY - 0.20000000298023224 - this.yOffset);
            final int var6 = MathHelper.floor_double(this.posZ);
            final int var7 = this.worldObj.getBlockId(var5, var3, var6);
            if (var7 > 0) {
                this.worldObj.spawnParticle("tilecrack_" + var7 + "_" + this.worldObj.getBlockMetadata(var5, var3, var6), this.posX + (this.rand.nextFloat() - 0.5) * this.width, this.boundingBox.minY + 0.1, this.posZ + (this.rand.nextFloat() - 0.5) * this.width, -this.motionX * 4.0, 1.5, -this.motionZ * 4.0);
            }
        }
        this.handleWaterMovement();
        if (this.worldObj.isRemote) {
            this.fire = 0;
        }
        else if (this.fire > 0) {
            if (this.isImmuneToFire) {
                this.fire -= 4;
                if (this.fire < 0) {
                    this.fire = 0;
                }
            }
            else {
                if (this.fire % 20 == 0) {
                    this.attackEntityFrom(DamageSource.onFire, 1);
                }
                --this.fire;
            }
        }
        if (this.handleLavaMovement()) {
            this.setOnFireFromLava();
            this.fallDistance *= 0.5f;
        }
        if (this.posY < -64.0) {
            this.kill();
        }
        if (!this.worldObj.isRemote) {
            this.setFlag(0, this.fire > 0);
            this.setFlag(2, this.ridingEntity != null);
        }
        this.firstUpdate = false;
        this.worldObj.theProfiler.endSection();
    }
    
    public int getMaxInPortalTime() {
        return 0;
    }
    
    protected void setOnFireFromLava() {
        if (!this.isImmuneToFire) {
            this.attackEntityFrom(DamageSource.lava, 4);
            this.setFire(15);
        }
    }
    
    public void setFire(final int par1) {
        int var2 = par1 * 20;
        var2 = EnchantmentProtection.func_92093_a(this, var2);
        if (this.fire < var2) {
            this.fire = var2;
        }
    }
    
    public void extinguish() {
        this.fire = 0;
    }
    
    protected void kill() {
        this.setDead();
    }
    
    public boolean isOffsetPositionInLiquid(final double par1, final double par3, final double par5) {
        final AxisAlignedBB var7 = this.boundingBox.getOffsetBoundingBox(par1, par3, par5);
        final List var8 = this.worldObj.getCollidingBoundingBoxes(this, var7);
        return var8.isEmpty() && !this.worldObj.isAnyLiquid(var7);
    }
    
    public void moveEntity(double par1, double par3, double par5) {
        if (this.noClip) {
            this.boundingBox.offset(par1, par3, par5);
            this.posX = (this.boundingBox.minX + this.boundingBox.maxX) / 2.0;
            this.posY = this.boundingBox.minY + this.yOffset - this.ySize;
            this.posZ = (this.boundingBox.minZ + this.boundingBox.maxZ) / 2.0;
        }
        else {
            this.worldObj.theProfiler.startSection("move");
            this.ySize *= 0.4f;
            final double var7 = this.posX;
            final double var8 = this.posY;
            final double var9 = this.posZ;
            if (this.isInWeb) {
                this.isInWeb = false;
                par1 *= 0.25;
                par3 *= 0.05000000074505806;
                par5 *= 0.25;
                this.motionX = 0.0;
                this.motionY = 0.0;
                this.motionZ = 0.0;
            }
            double var10 = par1;
            final double var11 = par3;
            double var12 = par5;
            final AxisAlignedBB var13 = this.boundingBox.copy();
            final boolean var14 = this.onGround && this.isSneaking() && this instanceof EntityPlayer;
            if (var14) {
                final double var15 = 0.05;
                while (par1 != 0.0) {
                    if (!this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox.getOffsetBoundingBox(par1, -1.0, 0.0)).isEmpty()) {
                        break;
                    }
                    if (par1 < var15 && par1 >= -var15) {
                        par1 = 0.0;
                    }
                    else if (par1 > 0.0) {
                        par1 -= var15;
                    }
                    else {
                        par1 += var15;
                    }
                    var10 = par1;
                }
                while (par5 != 0.0) {
                    if (!this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox.getOffsetBoundingBox(0.0, -1.0, par5)).isEmpty()) {
                        break;
                    }
                    if (par5 < var15 && par5 >= -var15) {
                        par5 = 0.0;
                    }
                    else if (par5 > 0.0) {
                        par5 -= var15;
                    }
                    else {
                        par5 += var15;
                    }
                    var12 = par5;
                }
                while (par1 != 0.0 && par5 != 0.0 && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox.getOffsetBoundingBox(par1, -1.0, par5)).isEmpty()) {
                    if (par1 < var15 && par1 >= -var15) {
                        par1 = 0.0;
                    }
                    else if (par1 > 0.0) {
                        par1 -= var15;
                    }
                    else {
                        par1 += var15;
                    }
                    if (par5 < var15 && par5 >= -var15) {
                        par5 = 0.0;
                    }
                    else if (par5 > 0.0) {
                        par5 -= var15;
                    }
                    else {
                        par5 += var15;
                    }
                    var10 = par1;
                    var12 = par5;
                }
            }
            List var16 = this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox.addCoord(par1, par3, par5));
            for (int var17 = 0; var17 < var16.size(); ++var17) {
                par3 = var16.get(var17).calculateYOffset(this.boundingBox, par3);
            }
            this.boundingBox.offset(0.0, par3, 0.0);
            if (!this.field_70135_K && var11 != par3) {
                par5 = 0.0;
                par3 = 0.0;
                par1 = 0.0;
            }
            final boolean var18 = this.onGround || (var11 != par3 && var11 < 0.0);
            for (int var19 = 0; var19 < var16.size(); ++var19) {
                par1 = var16.get(var19).calculateXOffset(this.boundingBox, par1);
            }
            this.boundingBox.offset(par1, 0.0, 0.0);
            if (!this.field_70135_K && var10 != par1) {
                par5 = 0.0;
                par3 = 0.0;
                par1 = 0.0;
            }
            for (int var19 = 0; var19 < var16.size(); ++var19) {
                par5 = var16.get(var19).calculateZOffset(this.boundingBox, par5);
            }
            this.boundingBox.offset(0.0, 0.0, par5);
            if (!this.field_70135_K && var12 != par5) {
                par5 = 0.0;
                par3 = 0.0;
                par1 = 0.0;
            }
            if (this.stepHeight > 0.0f && var18 && (var14 || this.ySize < 0.05f) && (var10 != par1 || var12 != par5)) {
                final double var20 = par1;
                final double var21 = par3;
                final double var22 = par5;
                par1 = var10;
                par3 = this.stepHeight;
                par5 = var12;
                final AxisAlignedBB var23 = this.boundingBox.copy();
                this.boundingBox.setBB(var13);
                var16 = this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox.addCoord(var10, par3, var12));
                for (int var24 = 0; var24 < var16.size(); ++var24) {
                    par3 = var16.get(var24).calculateYOffset(this.boundingBox, par3);
                }
                this.boundingBox.offset(0.0, par3, 0.0);
                if (!this.field_70135_K && var11 != par3) {
                    par5 = 0.0;
                    par3 = 0.0;
                    par1 = 0.0;
                }
                for (int var24 = 0; var24 < var16.size(); ++var24) {
                    par1 = var16.get(var24).calculateXOffset(this.boundingBox, par1);
                }
                this.boundingBox.offset(par1, 0.0, 0.0);
                if (!this.field_70135_K && var10 != par1) {
                    par5 = 0.0;
                    par3 = 0.0;
                    par1 = 0.0;
                }
                for (int var24 = 0; var24 < var16.size(); ++var24) {
                    par5 = var16.get(var24).calculateZOffset(this.boundingBox, par5);
                }
                this.boundingBox.offset(0.0, 0.0, par5);
                if (!this.field_70135_K && var12 != par5) {
                    par5 = 0.0;
                    par3 = 0.0;
                    par1 = 0.0;
                }
                if (!this.field_70135_K && var11 != par3) {
                    par5 = 0.0;
                    par3 = 0.0;
                    par1 = 0.0;
                }
                else {
                    par3 = -this.stepHeight;
                    for (int var24 = 0; var24 < var16.size(); ++var24) {
                        par3 = var16.get(var24).calculateYOffset(this.boundingBox, par3);
                    }
                    this.boundingBox.offset(0.0, par3, 0.0);
                }
                if (var20 * var20 + var22 * var22 >= par1 * par1 + par5 * par5) {
                    par1 = var20;
                    par3 = var21;
                    par5 = var22;
                    this.boundingBox.setBB(var23);
                }
            }
            this.worldObj.theProfiler.endSection();
            this.worldObj.theProfiler.startSection("rest");
            this.posX = (this.boundingBox.minX + this.boundingBox.maxX) / 2.0;
            this.posY = this.boundingBox.minY + this.yOffset - this.ySize;
            this.posZ = (this.boundingBox.minZ + this.boundingBox.maxZ) / 2.0;
            this.isCollidedHorizontally = (var10 != par1 || var12 != par5);
            this.isCollidedVertically = (var11 != par3);
            this.onGround = (var11 != par3 && var11 < 0.0);
            this.isCollided = (this.isCollidedHorizontally || this.isCollidedVertically);
            this.updateFallState(par3, this.onGround);
            if (var10 != par1) {
                this.motionX = 0.0;
            }
            if (var11 != par3) {
                this.motionY = 0.0;
            }
            if (var12 != par5) {
                this.motionZ = 0.0;
            }
            final double var20 = this.posX - var7;
            double var21 = this.posY - var8;
            final double var22 = this.posZ - var9;
            if (this.canTriggerWalking() && !var14 && this.ridingEntity == null) {
                final int var25 = MathHelper.floor_double(this.posX);
                final int var24 = MathHelper.floor_double(this.posY - 0.20000000298023224 - this.yOffset);
                final int var26 = MathHelper.floor_double(this.posZ);
                int var27 = this.worldObj.getBlockId(var25, var24, var26);
                if (var27 == 0) {
                    final int var28 = this.worldObj.blockGetRenderType(var25, var24 - 1, var26);
                    if (var28 == 11 || var28 == 32 || var28 == 21) {
                        var27 = this.worldObj.getBlockId(var25, var24 - 1, var26);
                    }
                }
                if (var27 != Block.ladder.blockID) {
                    var21 = 0.0;
                }
                this.distanceWalkedModified += (float)(MathHelper.sqrt_double(var20 * var20 + var22 * var22) * 0.6);
                this.distanceWalkedOnStepModified += (float)(MathHelper.sqrt_double(var20 * var20 + var21 * var21 + var22 * var22) * 0.6);
                if (this.distanceWalkedOnStepModified > this.nextStepDistance && var27 > 0) {
                    this.nextStepDistance = (int)this.distanceWalkedOnStepModified + 1;
                    if (this.isInWater()) {
                        float var29 = MathHelper.sqrt_double(this.motionX * this.motionX * 0.20000000298023224 + this.motionY * this.motionY + this.motionZ * this.motionZ * 0.20000000298023224) * 0.35f;
                        if (var29 > 1.0f) {
                            var29 = 1.0f;
                        }
                        this.playSound("liquid.swim", var29, 1.0f + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4f);
                    }
                    this.playStepSound(var25, var24, var26, var27);
                    Block.blocksList[var27].onEntityWalking(this.worldObj, var25, var24, var26, this);
                }
            }
            this.doBlockCollisions();
            final boolean var30 = this.isWet();
            if (this.worldObj.isBoundingBoxBurning(this.boundingBox.contract(0.001, 0.001, 0.001))) {
                this.dealFireDamage(1);
                if (!var30) {
                    ++this.fire;
                    if (this.fire == 0) {
                        this.setFire(8);
                    }
                }
            }
            else if (this.fire <= 0) {
                this.fire = -this.fireResistance;
            }
            if (var30 && this.fire > 0) {
                this.playSound("random.fizz", 0.7f, 1.6f + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4f);
                this.fire = -this.fireResistance;
            }
            this.worldObj.theProfiler.endSection();
        }
    }
    
    protected void doBlockCollisions() {
        final int var1 = MathHelper.floor_double(this.boundingBox.minX + 0.001);
        final int var2 = MathHelper.floor_double(this.boundingBox.minY + 0.001);
        final int var3 = MathHelper.floor_double(this.boundingBox.minZ + 0.001);
        final int var4 = MathHelper.floor_double(this.boundingBox.maxX - 0.001);
        final int var5 = MathHelper.floor_double(this.boundingBox.maxY - 0.001);
        final int var6 = MathHelper.floor_double(this.boundingBox.maxZ - 0.001);
        if (this.worldObj.checkChunksExist(var1, var2, var3, var4, var5, var6)) {
            for (int var7 = var1; var7 <= var4; ++var7) {
                for (int var8 = var2; var8 <= var5; ++var8) {
                    for (int var9 = var3; var9 <= var6; ++var9) {
                        final int var10 = this.worldObj.getBlockId(var7, var8, var9);
                        if (var10 > 0) {
                            Block.blocksList[var10].onEntityCollidedWithBlock(this.worldObj, var7, var8, var9, this);
                        }
                    }
                }
            }
        }
    }
    
    protected void playStepSound(final int par1, final int par2, final int par3, final int par4) {
        StepSound var5 = Block.blocksList[par4].stepSound;
        if (this.worldObj.getBlockId(par1, par2 + 1, par3) == Block.snow.blockID) {
            var5 = Block.snow.stepSound;
            this.playSound(var5.getStepSound(), var5.getVolume() * 0.15f, var5.getPitch());
        }
        else if (!Block.blocksList[par4].blockMaterial.isLiquid()) {
            this.playSound(var5.getStepSound(), var5.getVolume() * 0.15f, var5.getPitch());
        }
    }
    
    public void playSound(final String par1Str, final float par2, final float par3) {
        this.worldObj.playSoundAtEntity(this, par1Str, par2, par3);
    }
    
    protected boolean canTriggerWalking() {
        return true;
    }
    
    protected void updateFallState(final double par1, final boolean par3) {
        if (par3) {
            if (this.fallDistance > 0.0f) {
                this.fall(this.fallDistance);
                this.fallDistance = 0.0f;
            }
        }
        else if (par1 < 0.0) {
            this.fallDistance -= (float)par1;
        }
    }
    
    public AxisAlignedBB getBoundingBox() {
        return null;
    }
    
    protected void dealFireDamage(final int par1) {
        if (!this.isImmuneToFire) {
            this.attackEntityFrom(DamageSource.inFire, par1);
        }
    }
    
    public final boolean isImmuneToFire() {
        return this.isImmuneToFire;
    }
    
    protected void fall(final float par1) {
        if (this.riddenByEntity != null) {
            this.riddenByEntity.fall(par1);
        }
    }
    
    public boolean isWet() {
        return this.inWater || this.worldObj.canLightningStrikeAt(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)) || this.worldObj.canLightningStrikeAt(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY + this.height), MathHelper.floor_double(this.posZ));
    }
    
    public boolean isInWater() {
        return this.inWater;
    }
    
    public boolean handleWaterMovement() {
        if (this.worldObj.handleMaterialAcceleration(this.boundingBox.expand(0.0, -0.4000000059604645, 0.0).contract(0.001, 0.001, 0.001), Material.water, this)) {
            if (!this.inWater && !this.firstUpdate) {
                float var1 = MathHelper.sqrt_double(this.motionX * this.motionX * 0.20000000298023224 + this.motionY * this.motionY + this.motionZ * this.motionZ * 0.20000000298023224) * 0.2f;
                if (var1 > 1.0f) {
                    var1 = 1.0f;
                }
                this.playSound("liquid.splash", var1, 1.0f + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4f);
                final float var2 = MathHelper.floor_double(this.boundingBox.minY);
                for (int var3 = 0; var3 < 1.0f + this.width * 20.0f; ++var3) {
                    final float var4 = (this.rand.nextFloat() * 2.0f - 1.0f) * this.width;
                    final float var5 = (this.rand.nextFloat() * 2.0f - 1.0f) * this.width;
                    this.worldObj.spawnParticle("bubble", this.posX + var4, var2 + 1.0f, this.posZ + var5, this.motionX, this.motionY - this.rand.nextFloat() * 0.2f, this.motionZ);
                }
                for (int var3 = 0; var3 < 1.0f + this.width * 20.0f; ++var3) {
                    final float var4 = (this.rand.nextFloat() * 2.0f - 1.0f) * this.width;
                    final float var5 = (this.rand.nextFloat() * 2.0f - 1.0f) * this.width;
                    this.worldObj.spawnParticle("splash", this.posX + var4, var2 + 1.0f, this.posZ + var5, this.motionX, this.motionY, this.motionZ);
                }
            }
            this.fallDistance = 0.0f;
            this.inWater = true;
            this.fire = 0;
        }
        else {
            this.inWater = false;
        }
        return this.inWater;
    }
    
    public boolean isInsideOfMaterial(final Material par1Material) {
        final double var2 = this.posY + this.getEyeHeight();
        final int var3 = MathHelper.floor_double(this.posX);
        final int var4 = MathHelper.floor_float(MathHelper.floor_double(var2));
        final int var5 = MathHelper.floor_double(this.posZ);
        final int var6 = this.worldObj.getBlockId(var3, var4, var5);
        if (var6 != 0 && Block.blocksList[var6].blockMaterial == par1Material) {
            final float var7 = BlockFluid.getFluidHeightPercent(this.worldObj.getBlockMetadata(var3, var4, var5)) - 0.11111111f;
            final float var8 = var4 + 1 - var7;
            return var2 < var8;
        }
        return false;
    }
    
    public float getEyeHeight() {
        return 0.0f;
    }
    
    public boolean handleLavaMovement() {
        return this.worldObj.isMaterialInBB(this.boundingBox.expand(-0.10000000149011612, -0.4000000059604645, -0.10000000149011612), Material.lava);
    }
    
    public void moveFlying(float par1, float par2, final float par3) {
        float var4 = par1 * par1 + par2 * par2;
        if (var4 >= 1.0E-4f) {
            var4 = MathHelper.sqrt_float(var4);
            if (var4 < 1.0f) {
                var4 = 1.0f;
            }
            var4 = par3 / var4;
            par1 *= var4;
            par2 *= var4;
            final float var5 = MathHelper.sin(this.rotationYaw * 3.1415927f / 180.0f);
            final float var6 = MathHelper.cos(this.rotationYaw * 3.1415927f / 180.0f);
            this.motionX += par1 * var6 - par2 * var5;
            this.motionZ += par2 * var6 + par1 * var5;
        }
    }
    
    public int getBrightnessForRender(final float par1) {
        final int var2 = MathHelper.floor_double(this.posX);
        final int var3 = MathHelper.floor_double(this.posZ);
        if (this.worldObj.blockExists(var2, 0, var3)) {
            final double var4 = (this.boundingBox.maxY - this.boundingBox.minY) * 0.66;
            final int var5 = MathHelper.floor_double(this.posY - this.yOffset + var4);
            return this.worldObj.getLightBrightnessForSkyBlocks(var2, var5, var3, 0);
        }
        return 0;
    }
    
    public float getBrightness(final float par1) {
        final int var2 = MathHelper.floor_double(this.posX);
        final int var3 = MathHelper.floor_double(this.posZ);
        if (this.worldObj.blockExists(var2, 0, var3)) {
            final double var4 = (this.boundingBox.maxY - this.boundingBox.minY) * 0.66;
            final int var5 = MathHelper.floor_double(this.posY - this.yOffset + var4);
            return this.worldObj.getLightBrightness(var2, var5, var3);
        }
        return 0.0f;
    }
    
    public void setWorld(final World par1World) {
        this.worldObj = par1World;
    }
    
    public void setPositionAndRotation(final double par1, final double par3, final double par5, final float par7, final float par8) {
        this.posX = par1;
        this.prevPosX = par1;
        this.posY = par3;
        this.prevPosY = par3;
        this.posZ = par5;
        this.prevPosZ = par5;
        this.rotationYaw = par7;
        this.prevRotationYaw = par7;
        this.rotationPitch = par8;
        this.prevRotationPitch = par8;
        this.ySize = 0.0f;
        final double var9 = this.prevRotationYaw - par7;
        if (var9 < -180.0) {
            this.prevRotationYaw += 360.0f;
        }
        if (var9 >= 180.0) {
            this.prevRotationYaw -= 360.0f;
        }
        this.setPosition(this.posX, this.posY, this.posZ);
        this.setRotation(par7, par8);
    }
    
    public void setLocationAndAngles(final double par1, final double par3, final double par5, final float par7, final float par8) {
        this.posX = par1;
        this.prevPosX = par1;
        this.lastTickPosX = par1;
        final double lastTickPosY = par3 + this.yOffset;
        this.posY = lastTickPosY;
        this.prevPosY = lastTickPosY;
        this.lastTickPosY = lastTickPosY;
        this.posZ = par5;
        this.prevPosZ = par5;
        this.lastTickPosZ = par5;
        this.rotationYaw = par7;
        this.rotationPitch = par8;
        this.setPosition(this.posX, this.posY, this.posZ);
    }
    
    public float getDistanceToEntity(final Entity par1Entity) {
        final float var2 = (float)(this.posX - par1Entity.posX);
        final float var3 = (float)(this.posY - par1Entity.posY);
        final float var4 = (float)(this.posZ - par1Entity.posZ);
        return MathHelper.sqrt_float(var2 * var2 + var3 * var3 + var4 * var4);
    }
    
    public double getDistanceSq(final double par1, final double par3, final double par5) {
        final double var7 = this.posX - par1;
        final double var8 = this.posY - par3;
        final double var9 = this.posZ - par5;
        return var7 * var7 + var8 * var8 + var9 * var9;
    }
    
    public double getDistance(final double par1, final double par3, final double par5) {
        final double var7 = this.posX - par1;
        final double var8 = this.posY - par3;
        final double var9 = this.posZ - par5;
        return MathHelper.sqrt_double(var7 * var7 + var8 * var8 + var9 * var9);
    }
    
    public double getDistanceSqToEntity(final Entity par1Entity) {
        final double var2 = this.posX - par1Entity.posX;
        final double var3 = this.posY - par1Entity.posY;
        final double var4 = this.posZ - par1Entity.posZ;
        return var2 * var2 + var3 * var3 + var4 * var4;
    }
    
    public void onCollideWithPlayer(final EntityPlayer par1EntityPlayer) {
    }
    
    public void applyEntityCollision(final Entity par1Entity) {
        if (par1Entity.riddenByEntity != this && par1Entity.ridingEntity != this) {
            double var2 = par1Entity.posX - this.posX;
            double var3 = par1Entity.posZ - this.posZ;
            double var4 = MathHelper.abs_max(var2, var3);
            if (var4 >= 0.009999999776482582) {
                var4 = MathHelper.sqrt_double(var4);
                var2 /= var4;
                var3 /= var4;
                double var5 = 1.0 / var4;
                if (var5 > 1.0) {
                    var5 = 1.0;
                }
                var2 *= var5;
                var3 *= var5;
                var2 *= 0.05000000074505806;
                var3 *= 0.05000000074505806;
                var2 *= 1.0f - this.entityCollisionReduction;
                var3 *= 1.0f - this.entityCollisionReduction;
                this.addVelocity(-var2, 0.0, -var3);
                par1Entity.addVelocity(var2, 0.0, var3);
            }
        }
    }
    
    public void addVelocity(final double par1, final double par3, final double par5) {
        this.motionX += par1;
        this.motionY += par3;
        this.motionZ += par5;
        this.isAirBorne = true;
    }
    
    protected void setBeenAttacked() {
        this.velocityChanged = true;
    }
    
    public boolean attackEntityFrom(final DamageSource par1DamageSource, final int par2) {
        if (this.isEntityInvulnerable()) {
            return false;
        }
        this.setBeenAttacked();
        return false;
    }
    
    public boolean canBeCollidedWith() {
        return false;
    }
    
    public boolean canBePushed() {
        return false;
    }
    
    public void addToPlayerScore(final Entity par1Entity, final int par2) {
    }
    
    public boolean isInRangeToRenderVec3D(final Vec3 par1Vec3) {
        final double var2 = this.posX - par1Vec3.xCoord;
        final double var3 = this.posY - par1Vec3.yCoord;
        final double var4 = this.posZ - par1Vec3.zCoord;
        final double var5 = var2 * var2 + var3 * var3 + var4 * var4;
        return this.isInRangeToRenderDist(var5);
    }
    
    public boolean isInRangeToRenderDist(final double par1) {
        double var3 = this.boundingBox.getAverageEdgeLength();
        var3 *= 64.0 * this.renderDistanceWeight;
        return par1 < var3 * var3;
    }
    
    public String getTexture() {
        return null;
    }
    
    public boolean addNotRiddenEntityID(final NBTTagCompound par1NBTTagCompound) {
        final String var2 = this.getEntityString();
        if (!this.isDead && var2 != null) {
            par1NBTTagCompound.setString("id", var2);
            this.writeToNBT(par1NBTTagCompound);
            return true;
        }
        return false;
    }
    
    public boolean addEntityID(final NBTTagCompound par1NBTTagCompound) {
        final String var2 = this.getEntityString();
        if (!this.isDead && var2 != null && this.riddenByEntity == null) {
            par1NBTTagCompound.setString("id", var2);
            this.writeToNBT(par1NBTTagCompound);
            return true;
        }
        return false;
    }
    
    public void writeToNBT(final NBTTagCompound par1NBTTagCompound) {
        try {
            par1NBTTagCompound.setTag("Pos", this.newDoubleNBTList(this.posX, this.posY + this.ySize, this.posZ));
            par1NBTTagCompound.setTag("Motion", this.newDoubleNBTList(this.motionX, this.motionY, this.motionZ));
            par1NBTTagCompound.setTag("Rotation", this.newFloatNBTList(this.rotationYaw, this.rotationPitch));
            par1NBTTagCompound.setFloat("FallDistance", this.fallDistance);
            par1NBTTagCompound.setShort("Fire", (short)this.fire);
            par1NBTTagCompound.setShort("Air", (short)this.getAir());
            par1NBTTagCompound.setBoolean("OnGround", this.onGround);
            par1NBTTagCompound.setInteger("Dimension", this.dimension);
            par1NBTTagCompound.setBoolean("Invulnerable", this.invulnerable);
            par1NBTTagCompound.setInteger("PortalCooldown", this.timeUntilPortal);
            par1NBTTagCompound.setLong("UUIDMost", this.entityUniqueID.getMostSignificantBits());
            par1NBTTagCompound.setLong("UUIDLeast", this.entityUniqueID.getLeastSignificantBits());
            this.writeEntityToNBT(par1NBTTagCompound);
            if (this.ridingEntity != null) {
                final NBTTagCompound var2 = new NBTTagCompound("Riding");
                if (this.ridingEntity.addNotRiddenEntityID(var2)) {
                    par1NBTTagCompound.setTag("Riding", var2);
                }
            }
        }
        catch (Throwable var4) {
            final CrashReport var3 = CrashReport.makeCrashReport(var4, "Saving entity NBT");
            final CrashReportCategory var5 = var3.makeCategory("Entity being saved");
            this.func_85029_a(var5);
            throw new ReportedException(var3);
        }
    }
    
    public void readFromNBT(final NBTTagCompound par1NBTTagCompound) {
        try {
            final NBTTagList var2 = par1NBTTagCompound.getTagList("Pos");
            final NBTTagList var3 = par1NBTTagCompound.getTagList("Motion");
            final NBTTagList var4 = par1NBTTagCompound.getTagList("Rotation");
            this.motionX = ((NBTTagDouble)var3.tagAt(0)).data;
            this.motionY = ((NBTTagDouble)var3.tagAt(1)).data;
            this.motionZ = ((NBTTagDouble)var3.tagAt(2)).data;
            if (Math.abs(this.motionX) > 10.0) {
                this.motionX = 0.0;
            }
            if (Math.abs(this.motionY) > 10.0) {
                this.motionY = 0.0;
            }
            if (Math.abs(this.motionZ) > 10.0) {
                this.motionZ = 0.0;
            }
            final double data = ((NBTTagDouble)var2.tagAt(0)).data;
            this.posX = data;
            this.lastTickPosX = data;
            this.prevPosX = data;
            final double data2 = ((NBTTagDouble)var2.tagAt(1)).data;
            this.posY = data2;
            this.lastTickPosY = data2;
            this.prevPosY = data2;
            final double data3 = ((NBTTagDouble)var2.tagAt(2)).data;
            this.posZ = data3;
            this.lastTickPosZ = data3;
            this.prevPosZ = data3;
            final float data4 = ((NBTTagFloat)var4.tagAt(0)).data;
            this.rotationYaw = data4;
            this.prevRotationYaw = data4;
            final float data5 = ((NBTTagFloat)var4.tagAt(1)).data;
            this.rotationPitch = data5;
            this.prevRotationPitch = data5;
            this.fallDistance = par1NBTTagCompound.getFloat("FallDistance");
            this.fire = par1NBTTagCompound.getShort("Fire");
            this.setAir(par1NBTTagCompound.getShort("Air"));
            this.onGround = par1NBTTagCompound.getBoolean("OnGround");
            this.dimension = par1NBTTagCompound.getInteger("Dimension");
            this.invulnerable = par1NBTTagCompound.getBoolean("Invulnerable");
            this.timeUntilPortal = par1NBTTagCompound.getInteger("PortalCooldown");
            if (par1NBTTagCompound.hasKey("UUIDMost") && par1NBTTagCompound.hasKey("UUIDLeast")) {
                this.entityUniqueID = new UUID(par1NBTTagCompound.getLong("UUIDMost"), par1NBTTagCompound.getLong("UUIDLeast"));
            }
            this.setPosition(this.posX, this.posY, this.posZ);
            this.setRotation(this.rotationYaw, this.rotationPitch);
            this.readEntityFromNBT(par1NBTTagCompound);
        }
        catch (Throwable var6) {
            final CrashReport var5 = CrashReport.makeCrashReport(var6, "Loading entity NBT");
            final CrashReportCategory var7 = var5.makeCategory("Entity being loaded");
            this.func_85029_a(var7);
            throw new ReportedException(var5);
        }
    }
    
    protected final String getEntityString() {
        return EntityList.getEntityString(this);
    }
    
    protected abstract void readEntityFromNBT(final NBTTagCompound p0);
    
    protected abstract void writeEntityToNBT(final NBTTagCompound p0);
    
    protected NBTTagList newDoubleNBTList(final double... par1ArrayOfDouble) {
        final NBTTagList var2 = new NBTTagList();
        for (final double var5 : par1ArrayOfDouble) {
            var2.appendTag(new NBTTagDouble(null, var5));
        }
        return var2;
    }
    
    protected NBTTagList newFloatNBTList(final float... par1ArrayOfFloat) {
        final NBTTagList var2 = new NBTTagList();
        for (final float var5 : par1ArrayOfFloat) {
            var2.appendTag(new NBTTagFloat(null, var5));
        }
        return var2;
    }
    
    public float getShadowSize() {
        return this.height / 2.0f;
    }
    
    public EntityItem dropItem(final int par1, final int par2) {
        return this.dropItemWithOffset(par1, par2, 0.0f);
    }
    
    public EntityItem dropItemWithOffset(final int par1, final int par2, final float par3) {
        return this.entityDropItem(new ItemStack(par1, par2, 0), par3);
    }
    
    public EntityItem entityDropItem(final ItemStack par1ItemStack, final float par2) {
        final EntityItem var3 = new EntityItem(this.worldObj, this.posX, this.posY + par2, this.posZ, par1ItemStack);
        var3.delayBeforeCanPickup = 10;
        this.worldObj.spawnEntityInWorld(var3);
        return var3;
    }
    
    public boolean isEntityAlive() {
        return !this.isDead;
    }
    
    public boolean isEntityInsideOpaqueBlock() {
        for (int var1 = 0; var1 < 8; ++var1) {
            final float var2 = ((var1 >> 0) % 2 - 0.5f) * this.width * 0.8f;
            final float var3 = ((var1 >> 1) % 2 - 0.5f) * 0.1f;
            final float var4 = ((var1 >> 2) % 2 - 0.5f) * this.width * 0.8f;
            final int var5 = MathHelper.floor_double(this.posX + var2);
            final int var6 = MathHelper.floor_double(this.posY + this.getEyeHeight() + var3);
            final int var7 = MathHelper.floor_double(this.posZ + var4);
            if (this.worldObj.isBlockNormalCube(var5, var6, var7)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean interact(final EntityPlayer par1EntityPlayer) {
        return false;
    }
    
    public AxisAlignedBB getCollisionBox(final Entity par1Entity) {
        return null;
    }
    
    public void updateRidden() {
        if (this.ridingEntity.isDead) {
            this.ridingEntity = null;
        }
        else {
            this.motionX = 0.0;
            this.motionY = 0.0;
            this.motionZ = 0.0;
            this.onUpdate();
            if (this.ridingEntity != null) {
                this.ridingEntity.updateRiderPosition();
                this.entityRiderYawDelta += this.ridingEntity.rotationYaw - this.ridingEntity.prevRotationYaw;
                this.entityRiderPitchDelta += this.ridingEntity.rotationPitch - this.ridingEntity.prevRotationPitch;
                while (this.entityRiderYawDelta >= 180.0) {
                    this.entityRiderYawDelta -= 360.0;
                }
                while (this.entityRiderYawDelta < -180.0) {
                    this.entityRiderYawDelta += 360.0;
                }
                while (this.entityRiderPitchDelta >= 180.0) {
                    this.entityRiderPitchDelta -= 360.0;
                }
                while (this.entityRiderPitchDelta < -180.0) {
                    this.entityRiderPitchDelta += 360.0;
                }
                double var1 = this.entityRiderYawDelta * 0.5;
                double var2 = this.entityRiderPitchDelta * 0.5;
                final float var3 = 10.0f;
                if (var1 > var3) {
                    var1 = var3;
                }
                if (var1 < -var3) {
                    var1 = -var3;
                }
                if (var2 > var3) {
                    var2 = var3;
                }
                if (var2 < -var3) {
                    var2 = -var3;
                }
                this.entityRiderYawDelta -= var1;
                this.entityRiderPitchDelta -= var2;
                this.rotationYaw += (float)var1;
                this.rotationPitch += (float)var2;
            }
        }
    }
    
    public void updateRiderPosition() {
        if (this.riddenByEntity != null) {
            if (!(this.riddenByEntity instanceof EntityPlayer) || !((EntityPlayer)this.riddenByEntity).func_71066_bF()) {
                this.riddenByEntity.lastTickPosX = this.lastTickPosX;
                this.riddenByEntity.lastTickPosY = this.lastTickPosY + this.getMountedYOffset() + this.riddenByEntity.getYOffset();
                this.riddenByEntity.lastTickPosZ = this.lastTickPosZ;
            }
            this.riddenByEntity.setPosition(this.posX, this.posY + this.getMountedYOffset() + this.riddenByEntity.getYOffset(), this.posZ);
        }
    }
    
    public double getYOffset() {
        return this.yOffset;
    }
    
    public double getMountedYOffset() {
        return this.height * 0.75;
    }
    
    public void mountEntity(final Entity par1Entity) {
        this.entityRiderPitchDelta = 0.0;
        this.entityRiderYawDelta = 0.0;
        if (par1Entity == null) {
            if (this.ridingEntity != null) {
                this.setLocationAndAngles(this.ridingEntity.posX, this.ridingEntity.boundingBox.minY + this.ridingEntity.height, this.ridingEntity.posZ, this.rotationYaw, this.rotationPitch);
                this.ridingEntity.riddenByEntity = null;
            }
            this.ridingEntity = null;
        }
        else {
            if (this.ridingEntity != null) {
                this.ridingEntity.riddenByEntity = null;
            }
            this.ridingEntity = par1Entity;
            par1Entity.riddenByEntity = this;
        }
    }
    
    public void unmountEntity(final Entity par1Entity) {
        double var2 = this.posX;
        double var3 = this.posY;
        double var4 = this.posZ;
        if (par1Entity != null) {
            var2 = par1Entity.posX;
            var3 = par1Entity.boundingBox.minY + par1Entity.height;
            var4 = par1Entity.posZ;
        }
        for (double var5 = -1.5; var5 < 2.0; ++var5) {
            for (double var6 = -1.5; var6 < 2.0; ++var6) {
                if (var5 != 0.0 || var6 != 0.0) {
                    final int var7 = (int)(this.posX + var5);
                    final int var8 = (int)(this.posZ + var6);
                    final AxisAlignedBB var9 = this.boundingBox.getOffsetBoundingBox(var5, 1.0, var6);
                    if (this.worldObj.getCollidingBlockBounds(var9).isEmpty()) {
                        if (this.worldObj.doesBlockHaveSolidTopSurface(var7, (int)this.posY, var8)) {
                            this.setLocationAndAngles(this.posX + var5, this.posY + 1.0, this.posZ + var6, this.rotationYaw, this.rotationPitch);
                            return;
                        }
                        if (this.worldObj.doesBlockHaveSolidTopSurface(var7, (int)this.posY - 1, var8) || this.worldObj.getBlockMaterial(var7, (int)this.posY - 1, var8) == Material.water) {
                            var2 = this.posX + var5;
                            var3 = this.posY + 1.0;
                            var4 = this.posZ + var6;
                        }
                    }
                }
            }
        }
        this.setLocationAndAngles(var2, var3, var4, this.rotationYaw, this.rotationPitch);
    }
    
    public void setPositionAndRotation2(final double par1, double par3, final double par5, final float par7, final float par8, final int par9) {
        this.setPosition(par1, par3, par5);
        this.setRotation(par7, par8);
        final List var10 = this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox.contract(0.03125, 0.0, 0.03125));
        if (!var10.isEmpty()) {
            double var11 = 0.0;
            for (int var12 = 0; var12 < var10.size(); ++var12) {
                final AxisAlignedBB var13 = var10.get(var12);
                if (var13.maxY > var11) {
                    var11 = var13.maxY;
                }
            }
            par3 += var11 - this.boundingBox.minY;
            this.setPosition(par1, par3, par5);
        }
    }
    
    public float getCollisionBorderSize() {
        return 0.1f;
    }
    
    public Vec3 getLookVec() {
        return null;
    }
    
    public void setInPortal() {
        if (this.timeUntilPortal > 0) {
            this.timeUntilPortal = this.getPortalCooldown();
        }
        else {
            final double var1 = this.prevPosX - this.posX;
            final double var2 = this.prevPosZ - this.posZ;
            if (!this.worldObj.isRemote && !this.inPortal) {
                this.teleportDirection = Direction.getMovementDirection(var1, var2);
            }
            this.inPortal = true;
        }
    }
    
    public int getPortalCooldown() {
        return 900;
    }
    
    public void setVelocity(final double par1, final double par3, final double par5) {
        this.motionX = par1;
        this.motionY = par3;
        this.motionZ = par5;
    }
    
    public void handleHealthUpdate(final byte par1) {
    }
    
    public void performHurtAnimation() {
    }
    
    public void updateCloak() {
    }
    
    public ItemStack[] getLastActiveItems() {
        return null;
    }
    
    public void setCurrentItemOrArmor(final int par1, final ItemStack par2ItemStack) {
    }
    
    public boolean isBurning() {
        return this.fire > 0 || this.getFlag(0);
    }
    
    public boolean isRiding() {
        return this.ridingEntity != null || this.getFlag(2);
    }
    
    public boolean isSneaking() {
        return this.getFlag(1);
    }
    
    public void setSneaking(final boolean par1) {
        this.setFlag(1, par1);
    }
    
    public boolean isSprinting() {
        return this.getFlag(3);
    }
    
    public void setSprinting(final boolean par1) {
        this.setFlag(3, par1);
    }
    
    public boolean isInvisible() {
        return this.getFlag(5);
    }
    
    public boolean func_98034_c(final EntityPlayer par1EntityPlayer) {
        return this.isInvisible();
    }
    
    public void setInvisible(final boolean par1) {
        this.setFlag(5, par1);
    }
    
    public boolean isEating() {
        return this.getFlag(4);
    }
    
    public void setEating(final boolean par1) {
        this.setFlag(4, par1);
    }
    
    protected boolean getFlag(final int par1) {
        return (this.dataWatcher.getWatchableObjectByte(0) & 1 << par1) != 0x0;
    }
    
    protected void setFlag(final int par1, final boolean par2) {
        final byte var3 = this.dataWatcher.getWatchableObjectByte(0);
        if (par2) {
            this.dataWatcher.updateObject(0, (byte)(var3 | 1 << par1));
        }
        else {
            this.dataWatcher.updateObject(0, (byte)(var3 & ~(1 << par1)));
        }
    }
    
    public int getAir() {
        return this.dataWatcher.getWatchableObjectShort(1);
    }
    
    public void setAir(final int par1) {
        this.dataWatcher.updateObject(1, (short)par1);
    }
    
    public void onStruckByLightning(final EntityLightningBolt par1EntityLightningBolt) {
        this.dealFireDamage(5);
        ++this.fire;
        if (this.fire == 0) {
            this.setFire(8);
        }
    }
    
    public void onKillEntity(final EntityLiving par1EntityLiving) {
    }
    
    protected boolean pushOutOfBlocks(final double par1, final double par3, final double par5) {
        final int var7 = MathHelper.floor_double(par1);
        final int var8 = MathHelper.floor_double(par3);
        final int var9 = MathHelper.floor_double(par5);
        final double var10 = par1 - var7;
        final double var11 = par3 - var8;
        final double var12 = par5 - var9;
        final List var13 = this.worldObj.getCollidingBlockBounds(this.boundingBox);
        if (var13.isEmpty() && !this.worldObj.func_85174_u(var7, var8, var9)) {
            return false;
        }
        final boolean var14 = !this.worldObj.func_85174_u(var7 - 1, var8, var9);
        final boolean var15 = !this.worldObj.func_85174_u(var7 + 1, var8, var9);
        final boolean var16 = !this.worldObj.func_85174_u(var7, var8 - 1, var9);
        final boolean var17 = !this.worldObj.func_85174_u(var7, var8 + 1, var9);
        final boolean var18 = !this.worldObj.func_85174_u(var7, var8, var9 - 1);
        final boolean var19 = !this.worldObj.func_85174_u(var7, var8, var9 + 1);
        byte var20 = 3;
        double var21 = 9999.0;
        if (var14 && var10 < var21) {
            var21 = var10;
            var20 = 0;
        }
        if (var15 && 1.0 - var10 < var21) {
            var21 = 1.0 - var10;
            var20 = 1;
        }
        if (var17 && 1.0 - var11 < var21) {
            var21 = 1.0 - var11;
            var20 = 3;
        }
        if (var18 && var12 < var21) {
            var21 = var12;
            var20 = 4;
        }
        if (var19 && 1.0 - var12 < var21) {
            var21 = 1.0 - var12;
            var20 = 5;
        }
        final float var22 = this.rand.nextFloat() * 0.2f + 0.1f;
        if (var20 == 0) {
            this.motionX = -var22;
        }
        if (var20 == 1) {
            this.motionX = var22;
        }
        if (var20 == 2) {
            this.motionY = -var22;
        }
        if (var20 == 3) {
            this.motionY = var22;
        }
        if (var20 == 4) {
            this.motionZ = -var22;
        }
        if (var20 == 5) {
            this.motionZ = var22;
        }
        return true;
    }
    
    public void setInWeb() {
        this.isInWeb = true;
        this.fallDistance = 0.0f;
    }
    
    public String getEntityName() {
        String var1 = EntityList.getEntityString(this);
        if (var1 == null) {
            var1 = "generic";
        }
        return StatCollector.translateToLocal("entity." + var1 + ".name");
    }
    
    public Entity[] getParts() {
        return null;
    }
    
    public boolean isEntityEqual(final Entity par1Entity) {
        return this == par1Entity;
    }
    
    public float getRotationYawHead() {
        return 0.0f;
    }
    
    public void setRotationYawHead(final float par1) {
    }
    
    public boolean canAttackWithItem() {
        return true;
    }
    
    public boolean func_85031_j(final Entity par1Entity) {
        return false;
    }
    
    @Override
    public String toString() {
        return String.format("%s['%s'/%d, l='%s', x=%.2f, y=%.2f, z=%.2f]", this.getClass().getSimpleName(), this.getEntityName(), this.entityId, (this.worldObj == null) ? "~NULL~" : this.worldObj.getWorldInfo().getWorldName(), this.posX, this.posY, this.posZ);
    }
    
    public boolean isEntityInvulnerable() {
        return this.invulnerable;
    }
    
    public void func_82149_j(final Entity par1Entity) {
        this.setLocationAndAngles(par1Entity.posX, par1Entity.posY, par1Entity.posZ, par1Entity.rotationYaw, par1Entity.rotationPitch);
    }
    
    public void copyDataFrom(final Entity par1Entity, final boolean par2) {
        final NBTTagCompound var3 = new NBTTagCompound();
        par1Entity.writeToNBT(var3);
        this.readFromNBT(var3);
        this.timeUntilPortal = par1Entity.timeUntilPortal;
        this.teleportDirection = par1Entity.teleportDirection;
    }
    
    public void travelToDimension(final int par1) {
        if (!this.worldObj.isRemote && !this.isDead) {
            this.worldObj.theProfiler.startSection("changeDimension");
            final MinecraftServer var2 = MinecraftServer.getServer();
            final int var3 = this.dimension;
            final WorldServer var4 = var2.worldServerForDimension(var3);
            final WorldServer var5 = var2.worldServerForDimension(par1);
            this.dimension = par1;
            this.worldObj.removeEntity(this);
            this.isDead = false;
            this.worldObj.theProfiler.startSection("reposition");
            var2.getConfigurationManager().transferEntityToWorld(this, var3, var4, var5);
            this.worldObj.theProfiler.endStartSection("reloading");
            final Entity var6 = EntityList.createEntityByName(EntityList.getEntityString(this), var5);
            if (var6 != null) {
                var6.copyDataFrom(this, true);
                var5.spawnEntityInWorld(var6);
            }
            this.isDead = true;
            this.worldObj.theProfiler.endSection();
            var4.resetUpdateEntityTick();
            var5.resetUpdateEntityTick();
            this.worldObj.theProfiler.endSection();
        }
    }
    
    public float func_82146_a(final Explosion par1Explosion, final World par2World, final int par3, final int par4, final int par5, final Block par6Block) {
        return par6Block.getExplosionResistance(this);
    }
    
    public boolean func_96091_a(final Explosion par1Explosion, final World par2World, final int par3, final int par4, final int par5, final int par6, final float par7) {
        return true;
    }
    
    public int func_82143_as() {
        return 3;
    }
    
    public int getTeleportDirection() {
        return this.teleportDirection;
    }
    
    public boolean doesEntityNotTriggerPressurePlate() {
        return false;
    }
    
    public void func_85029_a(final CrashReportCategory par1CrashReportCategory) {
        par1CrashReportCategory.addCrashSectionCallable("Entity Type", new CallableEntityType(this));
        par1CrashReportCategory.addCrashSection("Entity ID", this.entityId);
        par1CrashReportCategory.addCrashSectionCallable("Entity Name", new CallableEntityName(this));
        par1CrashReportCategory.addCrashSection("Entity's Exact location", String.format("%.2f, %.2f, %.2f", this.posX, this.posY, this.posZ));
        par1CrashReportCategory.addCrashSection("Entity's Block location", CrashReportCategory.getLocationInfo(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)));
        par1CrashReportCategory.addCrashSection("Entity's Momentum", String.format("%.2f, %.2f, %.2f", this.motionX, this.motionY, this.motionZ));
    }
    
    public boolean canRenderOnFire() {
        return this.isBurning();
    }
    
    public boolean func_96092_aw() {
        return true;
    }
    
    public String getTranslatedEntityName() {
        return this.getEntityName();
    }
}
