package net.minecraft.entity;

import net.minecraft.command.*;
import java.util.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.player.*;
import net.minecraft.server.*;
import net.minecraft.world.*;
import net.minecraft.entity.item.*;
import net.minecraft.block.state.pattern.*;
import net.minecraft.item.*;
import net.minecraft.crash.*;
import net.minecraft.event.*;
import net.minecraft.enchantment.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import java.util.concurrent.*;

public abstract class Entity implements ICommandSender
{
    public double prevPosX;
    private static final AxisAlignedBB ZERO_AABB;
    public double posY;
    public double renderDistanceWeight;
    public int chunkCoordZ;
    public boolean noClip;
    public double lastTickPosY;
    public float prevRotationYaw;
    public boolean addedToChunk;
    private boolean isOutsideBorder;
    protected boolean inWater;
    public int hurtResistantTime;
    public float prevRotationPitch;
    public float stepHeight;
    private static final String[] I;
    public int chunkCoordY;
    public boolean isCollidedHorizontally;
    public boolean forceSpawn;
    public float width;
    public float fallDistance;
    protected DataWatcher dataWatcher;
    public boolean ignoreFrustumCheck;
    public float prevDistanceWalkedModified;
    public double motionX;
    public int serverPosX;
    public int timeUntilPortal;
    public double motionZ;
    public boolean preventEntitySpawning;
    public int fireResistance;
    public float distanceWalkedOnStepModified;
    public boolean isCollidedVertically;
    public World worldObj;
    public boolean isCollided;
    public boolean isAirBorne;
    public int chunkCoordX;
    public int serverPosZ;
    private double entityRiderPitchDelta;
    public double prevPosY;
    public double motionY;
    protected BlockPos field_181016_an;
    public boolean isDead;
    public Entity ridingEntity;
    private boolean invulnerable;
    public float height;
    public int serverPosY;
    private AxisAlignedBB boundingBox;
    protected EnumFacing field_181018_ap;
    private double entityRiderYawDelta;
    protected Random rand;
    public float rotationPitch;
    private int fire;
    protected boolean isImmuneToFire;
    public double prevPosZ;
    protected Vec3 field_181017_ao;
    private int entityId;
    private final CommandResultStats cmdResultStats;
    public double lastTickPosX;
    public boolean velocityChanged;
    public double posZ;
    public boolean onGround;
    private int nextStepDistance;
    protected boolean isInWeb;
    public float distanceWalkedModified;
    public Entity riddenByEntity;
    public double lastTickPosZ;
    protected UUID entityUniqueID;
    protected boolean inPortal;
    protected boolean firstUpdate;
    protected int portalCounter;
    private static int nextEntityID;
    public int dimension;
    public int ticksExisted;
    public float rotationYaw;
    public float entityCollisionReduction;
    public double posX;
    
    protected void setOnFireFromLava() {
        if (!this.isImmuneToFire) {
            this.attackEntityFrom(DamageSource.lava, 4.0f);
            this.setFire(0xE ^ 0x1);
        }
    }
    
    public void setPositionAndUpdate(final double n, final double n2, final double n3) {
        this.setLocationAndAngles(n, n2, n3, this.rotationYaw, this.rotationPitch);
    }
    
    public boolean attackEntityFrom(final DamageSource damageSource, final float n) {
        if (this.isEntityInvulnerable(damageSource)) {
            return "".length() != 0;
        }
        this.setBeenAttacked();
        return "".length() != 0;
    }
    
    public boolean isWet() {
        if (!this.inWater && !this.worldObj.canLightningStrike(new BlockPos(this.posX, this.posY, this.posZ)) && !this.worldObj.canLightningStrike(new BlockPos(this.posX, this.posY + this.height, this.posZ))) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    public void setPositionAndRotation2(final double n, double n2, final double n3, final float n4, final float n5, final int n6, final boolean b) {
        this.setPosition(n, n2, n3);
        this.setRotation(n4, n5);
        final List<AxisAlignedBB> collidingBoundingBoxes = this.worldObj.getCollidingBoundingBoxes(this, this.getEntityBoundingBox().contract(0.03125, 0.0, 0.03125));
        if (!collidingBoundingBoxes.isEmpty()) {
            double maxY = 0.0;
            final Iterator<AxisAlignedBB> iterator = collidingBoundingBoxes.iterator();
            "".length();
            if (3 <= 0) {
                throw null;
            }
            while (iterator.hasNext()) {
                final AxisAlignedBB axisAlignedBB = iterator.next();
                if (axisAlignedBB.maxY > maxY) {
                    maxY = axisAlignedBB.maxY;
                }
            }
            n2 += maxY - this.getEntityBoundingBox().minY;
            this.setPosition(n, n2, n3);
        }
    }
    
    public void moveEntity(double calculateXOffset, double n, double calculateZOffset) {
        if (this.noClip) {
            this.setEntityBoundingBox(this.getEntityBoundingBox().offset(calculateXOffset, n, calculateZOffset));
            this.resetPositionToBB();
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        else {
            this.worldObj.theProfiler.startSection(Entity.I["   ".length()]);
            final double posX = this.posX;
            final double posY = this.posY;
            final double posZ = this.posZ;
            if (this.isInWeb) {
                this.isInWeb = ("".length() != 0);
                calculateXOffset *= 0.25;
                n *= 0.05000000074505806;
                calculateZOffset *= 0.25;
                this.motionX = 0.0;
                this.motionY = 0.0;
                this.motionZ = 0.0;
            }
            double n2 = calculateXOffset;
            final double n3 = n;
            double n4 = calculateZOffset;
            int n5;
            if (this.onGround && this.isSneaking() && this instanceof EntityPlayer) {
                n5 = " ".length();
                "".length();
                if (4 <= 3) {
                    throw null;
                }
            }
            else {
                n5 = "".length();
            }
            final int n6 = n5;
            if (n6 != 0) {
                final double n7 = 0.05;
                "".length();
                if (3 <= 2) {
                    throw null;
                }
                while (calculateXOffset != 0.0) {
                    if (!this.worldObj.getCollidingBoundingBoxes(this, this.getEntityBoundingBox().offset(calculateXOffset, -1.0, 0.0)).isEmpty()) {
                        "".length();
                        if (4 != 4) {
                            throw null;
                        }
                        break;
                    }
                    else {
                        if (calculateXOffset < n7 && calculateXOffset >= -n7) {
                            calculateXOffset = 0.0;
                            "".length();
                            if (4 <= 0) {
                                throw null;
                            }
                        }
                        else if (calculateXOffset > 0.0) {
                            calculateXOffset -= n7;
                            "".length();
                            if (0 >= 3) {
                                throw null;
                            }
                        }
                        else {
                            calculateXOffset += n7;
                        }
                        n2 = calculateXOffset;
                    }
                }
                while (calculateZOffset != 0.0) {
                    if (!this.worldObj.getCollidingBoundingBoxes(this, this.getEntityBoundingBox().offset(0.0, -1.0, calculateZOffset)).isEmpty()) {
                        "".length();
                        if (-1 >= 3) {
                            throw null;
                        }
                        break;
                    }
                    else {
                        if (calculateZOffset < n7 && calculateZOffset >= -n7) {
                            calculateZOffset = 0.0;
                            "".length();
                            if (0 >= 1) {
                                throw null;
                            }
                        }
                        else if (calculateZOffset > 0.0) {
                            calculateZOffset -= n7;
                            "".length();
                            if (1 == 3) {
                                throw null;
                            }
                        }
                        else {
                            calculateZOffset += n7;
                        }
                        n4 = calculateZOffset;
                    }
                }
                while (calculateXOffset != 0.0 && calculateZOffset != 0.0 && this.worldObj.getCollidingBoundingBoxes(this, this.getEntityBoundingBox().offset(calculateXOffset, -1.0, calculateZOffset)).isEmpty()) {
                    if (calculateXOffset < n7 && calculateXOffset >= -n7) {
                        calculateXOffset = 0.0;
                        "".length();
                        if (3 <= 2) {
                            throw null;
                        }
                    }
                    else if (calculateXOffset > 0.0) {
                        calculateXOffset -= n7;
                        "".length();
                        if (4 <= 3) {
                            throw null;
                        }
                    }
                    else {
                        calculateXOffset += n7;
                    }
                    n2 = calculateXOffset;
                    if (calculateZOffset < n7 && calculateZOffset >= -n7) {
                        calculateZOffset = 0.0;
                        "".length();
                        if (1 >= 3) {
                            throw null;
                        }
                    }
                    else if (calculateZOffset > 0.0) {
                        calculateZOffset -= n7;
                        "".length();
                        if (4 != 4) {
                            throw null;
                        }
                    }
                    else {
                        calculateZOffset += n7;
                    }
                    n4 = calculateZOffset;
                }
            }
            final List<AxisAlignedBB> collidingBoundingBoxes = this.worldObj.getCollidingBoundingBoxes(this, this.getEntityBoundingBox().addCoord(calculateXOffset, n, calculateZOffset));
            final AxisAlignedBB entityBoundingBox = this.getEntityBoundingBox();
            final Iterator<AxisAlignedBB> iterator = collidingBoundingBoxes.iterator();
            "".length();
            if (3 <= 2) {
                throw null;
            }
            while (iterator.hasNext()) {
                n = iterator.next().calculateYOffset(this.getEntityBoundingBox(), n);
            }
            this.setEntityBoundingBox(this.getEntityBoundingBox().offset(0.0, n, 0.0));
            int n8;
            if (!this.onGround && (n3 == n || n3 >= 0.0)) {
                n8 = "".length();
                "".length();
                if (0 >= 3) {
                    throw null;
                }
            }
            else {
                n8 = " ".length();
            }
            final int n9 = n8;
            final Iterator<AxisAlignedBB> iterator2 = collidingBoundingBoxes.iterator();
            "".length();
            if (4 <= 1) {
                throw null;
            }
            while (iterator2.hasNext()) {
                calculateXOffset = iterator2.next().calculateXOffset(this.getEntityBoundingBox(), calculateXOffset);
            }
            this.setEntityBoundingBox(this.getEntityBoundingBox().offset(calculateXOffset, 0.0, 0.0));
            final Iterator<AxisAlignedBB> iterator3 = collidingBoundingBoxes.iterator();
            "".length();
            if (4 <= 3) {
                throw null;
            }
            while (iterator3.hasNext()) {
                calculateZOffset = iterator3.next().calculateZOffset(this.getEntityBoundingBox(), calculateZOffset);
            }
            this.setEntityBoundingBox(this.getEntityBoundingBox().offset(0.0, 0.0, calculateZOffset));
            if (this.stepHeight > 0.0f && n9 != 0 && (n2 != calculateXOffset || n4 != calculateZOffset)) {
                final double n10 = calculateXOffset;
                final double n11 = n;
                final double n12 = calculateZOffset;
                final AxisAlignedBB entityBoundingBox2 = this.getEntityBoundingBox();
                this.setEntityBoundingBox(entityBoundingBox);
                n = this.stepHeight;
                final List<AxisAlignedBB> collidingBoundingBoxes2 = this.worldObj.getCollidingBoundingBoxes(this, this.getEntityBoundingBox().addCoord(n2, n, n4));
                final AxisAlignedBB entityBoundingBox3 = this.getEntityBoundingBox();
                final AxisAlignedBB addCoord = entityBoundingBox3.addCoord(n2, 0.0, n4);
                double calculateYOffset = n;
                final Iterator<AxisAlignedBB> iterator4 = collidingBoundingBoxes2.iterator();
                "".length();
                if (1 < -1) {
                    throw null;
                }
                while (iterator4.hasNext()) {
                    calculateYOffset = iterator4.next().calculateYOffset(addCoord, calculateYOffset);
                }
                final AxisAlignedBB offset = entityBoundingBox3.offset(0.0, calculateYOffset, 0.0);
                double calculateXOffset2 = n2;
                final Iterator<AxisAlignedBB> iterator5 = collidingBoundingBoxes2.iterator();
                "".length();
                if (3 != 3) {
                    throw null;
                }
                while (iterator5.hasNext()) {
                    calculateXOffset2 = iterator5.next().calculateXOffset(offset, calculateXOffset2);
                }
                final AxisAlignedBB offset2 = offset.offset(calculateXOffset2, 0.0, 0.0);
                double calculateZOffset2 = n4;
                final Iterator<AxisAlignedBB> iterator6 = collidingBoundingBoxes2.iterator();
                "".length();
                if (2 <= 0) {
                    throw null;
                }
                while (iterator6.hasNext()) {
                    calculateZOffset2 = iterator6.next().calculateZOffset(offset2, calculateZOffset2);
                }
                final AxisAlignedBB offset3 = offset2.offset(0.0, 0.0, calculateZOffset2);
                final AxisAlignedBB entityBoundingBox4 = this.getEntityBoundingBox();
                double calculateYOffset2 = n;
                final Iterator<AxisAlignedBB> iterator7 = collidingBoundingBoxes2.iterator();
                "".length();
                if (2 < 1) {
                    throw null;
                }
                while (iterator7.hasNext()) {
                    calculateYOffset2 = iterator7.next().calculateYOffset(entityBoundingBox4, calculateYOffset2);
                }
                final AxisAlignedBB offset4 = entityBoundingBox4.offset(0.0, calculateYOffset2, 0.0);
                double calculateXOffset3 = n2;
                final Iterator<AxisAlignedBB> iterator8 = collidingBoundingBoxes2.iterator();
                "".length();
                if (-1 != -1) {
                    throw null;
                }
                while (iterator8.hasNext()) {
                    calculateXOffset3 = iterator8.next().calculateXOffset(offset4, calculateXOffset3);
                }
                final AxisAlignedBB offset5 = offset4.offset(calculateXOffset3, 0.0, 0.0);
                double calculateZOffset3 = n4;
                final Iterator<AxisAlignedBB> iterator9 = collidingBoundingBoxes2.iterator();
                "".length();
                if (3 < -1) {
                    throw null;
                }
                while (iterator9.hasNext()) {
                    calculateZOffset3 = iterator9.next().calculateZOffset(offset5, calculateZOffset3);
                }
                final AxisAlignedBB offset6 = offset5.offset(0.0, 0.0, calculateZOffset3);
                if (calculateXOffset2 * calculateXOffset2 + calculateZOffset2 * calculateZOffset2 > calculateXOffset3 * calculateXOffset3 + calculateZOffset3 * calculateZOffset3) {
                    calculateXOffset = calculateXOffset2;
                    calculateZOffset = calculateZOffset2;
                    n = -calculateYOffset;
                    this.setEntityBoundingBox(offset3);
                    "".length();
                    if (4 < 0) {
                        throw null;
                    }
                }
                else {
                    calculateXOffset = calculateXOffset3;
                    calculateZOffset = calculateZOffset3;
                    n = -calculateYOffset2;
                    this.setEntityBoundingBox(offset6);
                }
                final Iterator<AxisAlignedBB> iterator10 = collidingBoundingBoxes2.iterator();
                "".length();
                if (0 >= 4) {
                    throw null;
                }
                while (iterator10.hasNext()) {
                    n = iterator10.next().calculateYOffset(this.getEntityBoundingBox(), n);
                }
                this.setEntityBoundingBox(this.getEntityBoundingBox().offset(0.0, n, 0.0));
                if (n10 * n10 + n12 * n12 >= calculateXOffset * calculateXOffset + calculateZOffset * calculateZOffset) {
                    calculateXOffset = n10;
                    n = n11;
                    calculateZOffset = n12;
                    this.setEntityBoundingBox(entityBoundingBox2);
                }
            }
            this.worldObj.theProfiler.endSection();
            this.worldObj.theProfiler.startSection(Entity.I[0xAB ^ 0xAF]);
            this.resetPositionToBB();
            int isCollidedHorizontally;
            if (n2 == calculateXOffset && n4 == calculateZOffset) {
                isCollidedHorizontally = "".length();
                "".length();
                if (2 != 2) {
                    throw null;
                }
            }
            else {
                isCollidedHorizontally = " ".length();
            }
            this.isCollidedHorizontally = (isCollidedHorizontally != 0);
            int isCollidedVertically;
            if (n3 != n) {
                isCollidedVertically = " ".length();
                "".length();
                if (1 == 2) {
                    throw null;
                }
            }
            else {
                isCollidedVertically = "".length();
            }
            this.isCollidedVertically = (isCollidedVertically != 0);
            int onGround;
            if (this.isCollidedVertically && n3 < 0.0) {
                onGround = " ".length();
                "".length();
                if (1 < 0) {
                    throw null;
                }
            }
            else {
                onGround = "".length();
            }
            this.onGround = (onGround != 0);
            int isCollided;
            if (!this.isCollidedHorizontally && !this.isCollidedVertically) {
                isCollided = "".length();
                "".length();
                if (!true) {
                    throw null;
                }
            }
            else {
                isCollided = " ".length();
            }
            this.isCollided = (isCollided != 0);
            BlockPos down = new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY - 0.20000000298023224), MathHelper.floor_double(this.posZ));
            Block block = this.worldObj.getBlockState(down).getBlock();
            if (block.getMaterial() == Material.air) {
                final Block block2 = this.worldObj.getBlockState(down.down()).getBlock();
                if (block2 instanceof BlockFence || block2 instanceof BlockWall || block2 instanceof BlockFenceGate) {
                    block = block2;
                    down = down.down();
                }
            }
            this.updateFallState(n, this.onGround, block, down);
            if (n2 != calculateXOffset) {
                this.motionX = 0.0;
            }
            if (n4 != calculateZOffset) {
                this.motionZ = 0.0;
            }
            if (n3 != n) {
                block.onLanded(this.worldObj, this);
            }
            if (this.canTriggerWalking() && n6 == 0 && this.ridingEntity == null) {
                final double n13 = this.posX - posX;
                double n14 = this.posY - posY;
                final double n15 = this.posZ - posZ;
                if (block != Blocks.ladder) {
                    n14 = 0.0;
                }
                if (block != null && this.onGround) {
                    block.onEntityCollidedWithBlock(this.worldObj, down, this);
                }
                this.distanceWalkedModified += (float)(MathHelper.sqrt_double(n13 * n13 + n15 * n15) * 0.6);
                this.distanceWalkedOnStepModified += (float)(MathHelper.sqrt_double(n13 * n13 + n14 * n14 + n15 * n15) * 0.6);
                if (this.distanceWalkedOnStepModified > this.nextStepDistance && block.getMaterial() != Material.air) {
                    this.nextStepDistance = (int)this.distanceWalkedOnStepModified + " ".length();
                    if (this.isInWater()) {
                        float n16 = MathHelper.sqrt_double(this.motionX * this.motionX * 0.20000000298023224 + this.motionY * this.motionY + this.motionZ * this.motionZ * 0.20000000298023224) * 0.35f;
                        if (n16 > 1.0f) {
                            n16 = 1.0f;
                        }
                        this.playSound(this.getSwimSound(), n16, 1.0f + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4f);
                    }
                    this.playStepSound(down, block);
                }
            }
            try {
                this.doBlockCollisions();
                "".length();
                if (4 <= 1) {
                    throw null;
                }
            }
            catch (Throwable t) {
                final CrashReport crashReport = CrashReport.makeCrashReport(t, Entity.I[0x44 ^ 0x41]);
                this.addEntityCrashInfo(crashReport.makeCategory(Entity.I[0xB4 ^ 0xB2]));
                throw new ReportedException(crashReport);
            }
            final boolean wet = this.isWet();
            if (this.worldObj.isFlammableWithin(this.getEntityBoundingBox().contract(0.001, 0.001, 0.001))) {
                this.dealFireDamage(" ".length());
                if (!wet) {
                    this.fire += " ".length();
                    if (this.fire == 0) {
                        this.setFire(0x93 ^ 0x9B);
                        "".length();
                        if (-1 >= 0) {
                            throw null;
                        }
                    }
                }
            }
            else if (this.fire <= 0) {
                this.fire = -this.fireResistance;
            }
            if (wet && this.fire > 0) {
                this.playSound(Entity.I[0x66 ^ 0x61], 0.7f, 1.6f + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4f);
                this.fire = -this.fireResistance;
            }
            this.worldObj.theProfiler.endSection();
        }
    }
    
    public boolean isInRangeToRenderDist(final double n) {
        double averageEdgeLength = this.getEntityBoundingBox().getAverageEdgeLength();
        if (Double.isNaN(averageEdgeLength)) {
            averageEdgeLength = 1.0;
        }
        final double n2 = averageEdgeLength * 64.0 * this.renderDistanceWeight;
        if (n < n2 * n2) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public double getYOffset() {
        return 0.0;
    }
    
    protected String getSplashSound() {
        return Entity.I[0x3 ^ 0x8];
    }
    
    public boolean isRiding() {
        if (this.ridingEntity != null) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public Vec3 func_181014_aG() {
        return this.field_181017_ao;
    }
    
    public Vec3 getPositionEyes(final float n) {
        if (n == 1.0f) {
            return new Vec3(this.posX, this.posY + this.getEyeHeight(), this.posZ);
        }
        return new Vec3(this.prevPosX + (this.posX - this.prevPosX) * n, this.prevPosY + (this.posY - this.prevPosY) * n + this.getEyeHeight(), this.prevPosZ + (this.posZ - this.prevPosZ) * n);
    }
    
    public boolean isSneaking() {
        return this.getFlag(" ".length());
    }
    
    @Override
    public Entity getCommandSenderEntity() {
        return this;
    }
    
    @Override
    public boolean sendCommandFeedback() {
        return "".length() != 0;
    }
    
    public boolean verifyExplosion(final Explosion explosion, final World world, final BlockPos blockPos, final IBlockState blockState, final float n) {
        return " ".length() != 0;
    }
    
    public void performHurtAnimation() {
    }
    
    public boolean isInsideOfMaterial(final Material material) {
        final double n = this.posY + this.getEyeHeight();
        final BlockPos blockPos = new BlockPos(this.posX, n, this.posZ);
        final IBlockState blockState = this.worldObj.getBlockState(blockPos);
        if (blockState.getBlock().getMaterial() == material) {
            int n2;
            if (n < blockPos.getY() + " ".length() - (BlockLiquid.getLiquidHeightPercent(blockState.getBlock().getMetaFromState(blockState)) - 0.11111111f)) {
                n2 = " ".length();
                "".length();
                if (-1 >= 0) {
                    throw null;
                }
            }
            else {
                n2 = "".length();
            }
            final int n3 = n2;
            int length;
            if (n3 == 0 && this instanceof EntityPlayer) {
                length = "".length();
                "".length();
                if (1 < -1) {
                    throw null;
                }
            }
            else {
                length = n3;
            }
            return length != 0;
        }
        return "".length() != 0;
    }
    
    public boolean interactAt(final EntityPlayer entityPlayer, final Vec3 vec3) {
        return "".length() != 0;
    }
    
    public float getDistanceToEntity(final Entity entity) {
        final float n = (float)(this.posX - entity.posX);
        final float n2 = (float)(this.posY - entity.posY);
        final float n3 = (float)(this.posZ - entity.posZ);
        return MathHelper.sqrt_float(n * n + n2 * n2 + n3 * n3);
    }
    
    static {
        I();
        ZERO_AABB = new AxisAlignedBB(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
    }
    
    public boolean interactFirst(final EntityPlayer entityPlayer) {
        return "".length() != 0;
    }
    
    protected void setFlag(final int n, final boolean b) {
        final byte watchableObjectByte = this.dataWatcher.getWatchableObjectByte("".length());
        if (b) {
            this.dataWatcher.updateObject("".length(), (byte)(watchableObjectByte | " ".length() << n));
            "".length();
            if (0 >= 1) {
                throw null;
            }
        }
        else {
            this.dataWatcher.updateObject("".length(), (byte)(watchableObjectByte & (" ".length() << n ^ -" ".length())));
        }
    }
    
    public boolean isBurning() {
        int n;
        if (this.worldObj != null && this.worldObj.isRemote) {
            n = " ".length();
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        final int n2 = n;
        if (!this.isImmuneToFire && (this.fire > 0 || (n2 != 0 && this.getFlag("".length())))) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public void onDataWatcherUpdate(final int n) {
    }
    
    protected NBTTagList newDoubleNBTList(final double... array) {
        final NBTTagList list = new NBTTagList();
        final int length = array.length;
        int i = "".length();
        "".length();
        if (3 == 4) {
            throw null;
        }
        while (i < length) {
            list.appendTag(new NBTTagDouble(array[i]));
            ++i;
        }
        return list;
    }
    
    public void setAlwaysRenderNameTag(final boolean b) {
        final DataWatcher dataWatcher = this.dataWatcher;
        final int length = "   ".length();
        int n;
        if (b) {
            n = " ".length();
            "".length();
            if (1 >= 2) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        dataWatcher.updateObject(length, (byte)n);
    }
    
    protected boolean getFlag(final int n) {
        if ((this.dataWatcher.getWatchableObjectByte("".length()) & " ".length() << n) != 0x0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public boolean canRenderOnFire() {
        return this.isBurning();
    }
    
    public void setPosition(final double posX, final double posY, final double posZ) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        final float n = this.width / 2.0f;
        this.setEntityBoundingBox(new AxisAlignedBB(posX - n, posY, posZ - n, posX + n, posY + this.height, posZ + n));
    }
    
    public void writeToNBT(final NBTTagCompound nbtTagCompound) {
        try {
            final String s = Entity.I[0x1E ^ 0x10];
            final double[] array = new double["   ".length()];
            array["".length()] = this.posX;
            array[" ".length()] = this.posY;
            array["  ".length()] = this.posZ;
            nbtTagCompound.setTag(s, this.newDoubleNBTList(array));
            final String s2 = Entity.I[0xB9 ^ 0xB6];
            final double[] array2 = new double["   ".length()];
            array2["".length()] = this.motionX;
            array2[" ".length()] = this.motionY;
            array2["  ".length()] = this.motionZ;
            nbtTagCompound.setTag(s2, this.newDoubleNBTList(array2));
            final String s3 = Entity.I[0x99 ^ 0x89];
            final float[] array3 = new float["  ".length()];
            array3["".length()] = this.rotationYaw;
            array3[" ".length()] = this.rotationPitch;
            nbtTagCompound.setTag(s3, this.newFloatNBTList(array3));
            nbtTagCompound.setFloat(Entity.I[0x4 ^ 0x15], this.fallDistance);
            nbtTagCompound.setShort(Entity.I[0x2C ^ 0x3E], (short)this.fire);
            nbtTagCompound.setShort(Entity.I[0x5D ^ 0x4E], (short)this.getAir());
            nbtTagCompound.setBoolean(Entity.I[0x8 ^ 0x1C], this.onGround);
            nbtTagCompound.setInteger(Entity.I[0x97 ^ 0x82], this.dimension);
            nbtTagCompound.setBoolean(Entity.I[0x36 ^ 0x20], this.invulnerable);
            nbtTagCompound.setInteger(Entity.I[0xA7 ^ 0xB0], this.timeUntilPortal);
            nbtTagCompound.setLong(Entity.I[0x2C ^ 0x34], this.getUniqueID().getMostSignificantBits());
            nbtTagCompound.setLong(Entity.I[0x6 ^ 0x1F], this.getUniqueID().getLeastSignificantBits());
            if (this.getCustomNameTag() != null && this.getCustomNameTag().length() > 0) {
                nbtTagCompound.setString(Entity.I[0x27 ^ 0x3D], this.getCustomNameTag());
                nbtTagCompound.setBoolean(Entity.I[0x77 ^ 0x6C], this.getAlwaysRenderNameTag());
            }
            this.cmdResultStats.writeStatsToNBT(nbtTagCompound);
            if (this.isSilent()) {
                nbtTagCompound.setBoolean(Entity.I[0xDE ^ 0xC2], this.isSilent());
            }
            this.writeEntityToNBT(nbtTagCompound);
            if (this.ridingEntity != null) {
                final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
                if (this.ridingEntity.writeMountToNBT(nbtTagCompound2)) {
                    nbtTagCompound.setTag(Entity.I[0x9A ^ 0x87], nbtTagCompound2);
                    "".length();
                    if (3 >= 4) {
                        throw null;
                    }
                }
            }
        }
        catch (Throwable t) {
            final CrashReport crashReport = CrashReport.makeCrashReport(t, Entity.I[0x4A ^ 0x54]);
            this.addEntityCrashInfo(crashReport.makeCategory(Entity.I[0x7D ^ 0x62]));
            throw new ReportedException(crashReport);
        }
    }
    
    protected boolean pushOutOfBlocks(final double n, final double n2, final double n3) {
        final BlockPos blockPos = new BlockPos(n, n2, n3);
        final double n4 = n - blockPos.getX();
        final double n5 = n2 - blockPos.getY();
        final double n6 = n3 - blockPos.getZ();
        if (this.worldObj.func_147461_a(this.getEntityBoundingBox()).isEmpty() && !this.worldObj.isBlockFullCube(blockPos)) {
            return "".length() != 0;
        }
        int n7 = "   ".length();
        double n8 = 9999.0;
        if (!this.worldObj.isBlockFullCube(blockPos.west()) && n4 < n8) {
            n8 = n4;
            n7 = "".length();
        }
        if (!this.worldObj.isBlockFullCube(blockPos.east()) && 1.0 - n4 < n8) {
            n8 = 1.0 - n4;
            n7 = " ".length();
        }
        if (!this.worldObj.isBlockFullCube(blockPos.up()) && 1.0 - n5 < n8) {
            n8 = 1.0 - n5;
            n7 = "   ".length();
        }
        if (!this.worldObj.isBlockFullCube(blockPos.north()) && n6 < n8) {
            n8 = n6;
            n7 = (0xA ^ 0xE);
        }
        if (!this.worldObj.isBlockFullCube(blockPos.south()) && 1.0 - n6 < n8) {
            n7 = (0x6C ^ 0x69);
        }
        final float n9 = this.rand.nextFloat() * 0.2f + 0.1f;
        if (n7 == 0) {
            this.motionX = -n9;
        }
        if (n7 == " ".length()) {
            this.motionX = n9;
        }
        if (n7 == "   ".length()) {
            this.motionY = n9;
        }
        if (n7 == (0x68 ^ 0x6C)) {
            this.motionZ = -n9;
        }
        if (n7 == (0x29 ^ 0x2C)) {
            this.motionZ = n9;
        }
        return " ".length() != 0;
    }
    
    public boolean canBeCollidedWith() {
        return "".length() != 0;
    }
    
    public void setFire(final int n) {
        final int fireTimeForEntity = EnchantmentProtection.getFireTimeForEntity(this, n * (0xA4 ^ 0xB0));
        if (this.fire < fireTimeForEntity) {
            this.fire = fireTimeForEntity;
        }
    }
    
    public EnumFacing func_181012_aH() {
        return this.field_181018_ap;
    }
    
    public boolean isSprinting() {
        return this.getFlag("   ".length());
    }
    
    public boolean isEntityAlive() {
        int n;
        if (this.isDead) {
            n = "".length();
            "".length();
            if (1 <= -1) {
                throw null;
            }
        }
        else {
            n = " ".length();
        }
        return n != 0;
    }
    
    public void addVelocity(final double n, final double n2, final double n3) {
        this.motionX += n;
        this.motionY += n2;
        this.motionZ += n3;
        this.isAirBorne = (" ".length() != 0);
    }
    
    @Override
    public void setCommandStat(final CommandResultStats.Type type, final int n) {
        this.cmdResultStats.func_179672_a(this, type, n);
    }
    
    public boolean isPushedByWater() {
        return " ".length() != 0;
    }
    
    public void setDead() {
        this.isDead = (" ".length() != 0);
    }
    
    public UUID getUniqueID() {
        return this.entityUniqueID;
    }
    
    public void onKillCommand() {
        this.setDead();
    }
    
    @Override
    public String getName() {
        if (this.hasCustomName()) {
            return this.getCustomNameTag();
        }
        String entityString = EntityList.getEntityString(this);
        if (entityString == null) {
            entityString = Entity.I[0x6E ^ 0x59];
        }
        return StatCollector.translateToLocal(Entity.I[0x26 ^ 0x1E] + entityString + Entity.I[0x27 ^ 0x1E]);
    }
    
    public void copyLocationAndAnglesFrom(final Entity entity) {
        this.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch);
    }
    
    public double getDistanceSq(final double n, final double n2, final double n3) {
        final double n4 = this.posX - n;
        final double n5 = this.posY - n2;
        final double n6 = this.posZ - n3;
        return n4 * n4 + n5 * n5 + n6 * n6;
    }
    
    public void onStruckByLightning(final EntityLightningBolt entityLightningBolt) {
        this.attackEntityFrom(DamageSource.lightningBolt, 5.0f);
        this.fire += " ".length();
        if (this.fire == 0) {
            this.setFire(0x51 ^ 0x59);
        }
    }
    
    public NBTTagCompound getNBTTagCompound() {
        return null;
    }
    
    @Override
    public World getEntityWorld() {
        return this.worldObj;
    }
    
    public void setSprinting(final boolean b) {
        this.setFlag("   ".length(), b);
    }
    
    public boolean isSpectatedByPlayer(final EntityPlayerMP entityPlayerMP) {
        return " ".length() != 0;
    }
    
    public int getEntityId() {
        return this.entityId;
    }
    
    public boolean isInWater() {
        return this.inWater;
    }
    
    public AxisAlignedBB getCollisionBox(final Entity entity) {
        return null;
    }
    
    public void travelToDimension(final int dimension) {
        if (!this.worldObj.isRemote && !this.isDead) {
            this.worldObj.theProfiler.startSection(Entity.I[0x1D ^ 0x21]);
            final MinecraftServer server = MinecraftServer.getServer();
            final int dimension2 = this.dimension;
            final WorldServer worldServerForDimension = server.worldServerForDimension(dimension2);
            WorldServer worldServer = server.worldServerForDimension(dimension);
            this.dimension = dimension;
            if (dimension2 == " ".length() && dimension == " ".length()) {
                worldServer = server.worldServerForDimension("".length());
                this.dimension = "".length();
            }
            this.worldObj.removeEntity(this);
            this.isDead = ("".length() != 0);
            this.worldObj.theProfiler.startSection(Entity.I[0x27 ^ 0x1A]);
            server.getConfigurationManager().transferEntityToWorld(this, dimension2, worldServerForDimension, worldServer);
            this.worldObj.theProfiler.endStartSection(Entity.I[0x70 ^ 0x4E]);
            final Entity entityByName = EntityList.createEntityByName(EntityList.getEntityString(this), worldServer);
            if (entityByName != null) {
                entityByName.copyDataFromOld(this);
                if (dimension2 == " ".length() && dimension == " ".length()) {
                    entityByName.moveToBlockPosAndAngles(this.worldObj.getTopSolidOrLiquidBlock(worldServer.getSpawnPoint()), entityByName.rotationYaw, entityByName.rotationPitch);
                }
                worldServer.spawnEntityInWorld(entityByName);
            }
            this.isDead = (" ".length() != 0);
            this.worldObj.theProfiler.endSection();
            worldServerForDimension.resetUpdateEntityTick();
            worldServer.resetUpdateEntityTick();
            this.worldObj.theProfiler.endSection();
        }
    }
    
    public void setLocationAndAngles(final double lastTickPosX, final double lastTickPosY, final double lastTickPosZ, final float rotationYaw, final float rotationPitch) {
        this.posX = lastTickPosX;
        this.prevPosX = lastTickPosX;
        this.lastTickPosX = lastTickPosX;
        this.posY = lastTickPosY;
        this.prevPosY = lastTickPosY;
        this.lastTickPosY = lastTickPosY;
        this.posZ = lastTickPosZ;
        this.prevPosZ = lastTickPosZ;
        this.lastTickPosZ = lastTickPosZ;
        this.rotationYaw = rotationYaw;
        this.rotationPitch = rotationPitch;
        this.setPosition(this.posX, this.posY, this.posZ);
    }
    
    public void onChunkLoad() {
    }
    
    public void moveFlying(float n, float n2, final float n3) {
        final float n4 = n * n + n2 * n2;
        if (n4 >= 1.0E-4f) {
            float sqrt_float = MathHelper.sqrt_float(n4);
            if (sqrt_float < 1.0f) {
                sqrt_float = 1.0f;
            }
            final float n5 = n3 / sqrt_float;
            n *= n5;
            n2 *= n5;
            final float sin = MathHelper.sin(this.rotationYaw * 3.1415927f / 180.0f);
            final float cos = MathHelper.cos(this.rotationYaw * 3.1415927f / 180.0f);
            this.motionX += n * cos - n2 * sin;
            this.motionZ += n2 * cos + n * sin;
        }
    }
    
    public boolean doesEntityNotTriggerPressurePlate() {
        return "".length() != 0;
    }
    
    public void applyEntityCollision(final Entity entity) {
        if (entity.riddenByEntity != this && entity.ridingEntity != this && !entity.noClip && !this.noClip) {
            final double n = entity.posX - this.posX;
            final double n2 = entity.posZ - this.posZ;
            final double abs_max = MathHelper.abs_max(n, n2);
            if (abs_max >= 0.009999999776482582) {
                final double n3 = MathHelper.sqrt_double(abs_max);
                final double n4 = n / n3;
                final double n5 = n2 / n3;
                double n6 = 1.0 / n3;
                if (n6 > 1.0) {
                    n6 = 1.0;
                }
                final double n7 = n4 * n6;
                final double n8 = n5 * n6;
                final double n9 = n7 * 0.05000000074505806;
                final double n10 = n8 * 0.05000000074505806;
                final double n11 = n9 * (1.0f - this.entityCollisionReduction);
                final double n12 = n10 * (1.0f - this.entityCollisionReduction);
                if (this.riddenByEntity == null) {
                    this.addVelocity(-n11, 0.0, -n12);
                }
                if (entity.riddenByEntity == null) {
                    entity.addVelocity(n11, 0.0, n12);
                }
            }
        }
    }
    
    @Override
    public boolean canCommandSenderUseCommand(final int n, final String s) {
        return " ".length() != 0;
    }
    
    protected void setBeenAttacked() {
        this.velocityChanged = (" ".length() != 0);
    }
    
    public EntityItem dropItem(final Item item, final int n) {
        return this.dropItemWithOffset(item, n, 0.0f);
    }
    
    public void setAir(final int n) {
        this.dataWatcher.updateObject(" ".length(), (short)n);
    }
    
    protected void resetHeight() {
        float n = MathHelper.sqrt_double(this.motionX * this.motionX * 0.20000000298023224 + this.motionY * this.motionY + this.motionZ * this.motionZ * 0.20000000298023224) * 0.2f;
        if (n > 1.0f) {
            n = 1.0f;
        }
        this.playSound(this.getSplashSound(), n, 1.0f + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4f);
        final float n2 = MathHelper.floor_double(this.getEntityBoundingBox().minY);
        int length = "".length();
        "".length();
        if (0 >= 1) {
            throw null;
        }
        while (length < 1.0f + this.width * 20.0f) {
            this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX + (this.rand.nextFloat() * 2.0f - 1.0f) * this.width, n2 + 1.0f, this.posZ + (this.rand.nextFloat() * 2.0f - 1.0f) * this.width, this.motionX, this.motionY - this.rand.nextFloat() * 0.2f, this.motionZ, new int["".length()]);
            ++length;
        }
        int length2 = "".length();
        "".length();
        if (2 <= 1) {
            throw null;
        }
        while (length2 < 1.0f + this.width * 20.0f) {
            this.worldObj.spawnParticle(EnumParticleTypes.WATER_SPLASH, this.posX + (this.rand.nextFloat() * 2.0f - 1.0f) * this.width, n2 + 1.0f, this.posZ + (this.rand.nextFloat() * 2.0f - 1.0f) * this.width, this.motionX, this.motionY, this.motionZ, new int["".length()]);
            ++length2;
        }
    }
    
    public boolean isEntityEqual(final Entity entity) {
        if (this == entity) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public Entity[] getParts() {
        return null;
    }
    
    public boolean isOutsideBorder() {
        return this.isOutsideBorder;
    }
    
    public void setWorld(final World worldObj) {
        this.worldObj = worldObj;
    }
    
    public int getMaxFallHeight() {
        return "   ".length();
    }
    
    public float getExplosionResistance(final Explosion explosion, final World world, final BlockPos blockPos, final IBlockState blockState) {
        return blockState.getBlock().getExplosionResistance(this);
    }
    
    public void func_181015_d(final BlockPos field_181016_an) {
        if (this.timeUntilPortal > 0) {
            this.timeUntilPortal = this.getPortalCooldown();
            "".length();
            if (4 < 4) {
                throw null;
            }
        }
        else {
            if (!this.worldObj.isRemote && !field_181016_an.equals(this.field_181016_an)) {
                this.field_181016_an = field_181016_an;
                final BlockPattern.PatternHelper func_181089_f = Blocks.portal.func_181089_f(this.worldObj, field_181016_an);
                double n;
                if (func_181089_f.getFinger().getAxis() == EnumFacing.Axis.X) {
                    n = func_181089_f.func_181117_a().getZ();
                    "".length();
                    if (-1 >= 3) {
                        throw null;
                    }
                }
                else {
                    n = func_181089_f.func_181117_a().getX();
                }
                final double n2 = n;
                double n3;
                if (func_181089_f.getFinger().getAxis() == EnumFacing.Axis.X) {
                    n3 = this.posZ;
                    "".length();
                    if (0 >= 1) {
                        throw null;
                    }
                }
                else {
                    n3 = this.posX;
                }
                final double n4 = n3;
                int n5;
                if (func_181089_f.getFinger().rotateY().getAxisDirection() == EnumFacing.AxisDirection.NEGATIVE) {
                    n5 = " ".length();
                    "".length();
                    if (0 == 3) {
                        throw null;
                    }
                }
                else {
                    n5 = "".length();
                }
                this.field_181017_ao = new Vec3(Math.abs(MathHelper.func_181160_c(n4 - n5, n2, n2 - func_181089_f.func_181118_d())), MathHelper.func_181160_c(this.posY - 1.0, func_181089_f.func_181117_a().getY(), func_181089_f.func_181117_a().getY() - func_181089_f.func_181119_e()), 0.0);
                this.field_181018_ap = func_181089_f.getFinger();
            }
            this.inPortal = (" ".length() != 0);
        }
    }
    
    public void setEating(final boolean b) {
        this.setFlag(0x60 ^ 0x64, b);
    }
    
    public void func_181013_g(final float n) {
    }
    
    protected void setSize(final float width, final float height) {
        if (width != this.width || height != this.height) {
            final float width2 = this.width;
            this.width = width;
            this.height = height;
            this.setEntityBoundingBox(new AxisAlignedBB(this.getEntityBoundingBox().minX, this.getEntityBoundingBox().minY, this.getEntityBoundingBox().minZ, this.getEntityBoundingBox().minX + this.width, this.getEntityBoundingBox().minY + this.height, this.getEntityBoundingBox().minZ + this.width));
            if (this.width > width2 && !this.firstUpdate && !this.worldObj.isRemote) {
                this.moveEntity(width2 - this.width, 0.0, width2 - this.width);
            }
        }
    }
    
    public EntityItem entityDropItem(final ItemStack itemStack, final float n) {
        if (itemStack.stackSize != 0 && itemStack.getItem() != null) {
            final EntityItem entityItem = new EntityItem(this.worldObj, this.posX, this.posY + n, this.posZ, itemStack);
            entityItem.setDefaultPickupDelay();
            this.worldObj.spawnEntityInWorld(entityItem);
            return entityItem;
        }
        return null;
    }
    
    private boolean isLiquidPresentInAABB(final AxisAlignedBB axisAlignedBB) {
        if (this.worldObj.getCollidingBoundingBoxes(this, axisAlignedBB).isEmpty() && !this.worldObj.isAnyLiquid(axisAlignedBB)) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    protected void doBlockCollisions() {
        final BlockPos blockPos = new BlockPos(this.getEntityBoundingBox().minX + 0.001, this.getEntityBoundingBox().minY + 0.001, this.getEntityBoundingBox().minZ + 0.001);
        final BlockPos blockPos2 = new BlockPos(this.getEntityBoundingBox().maxX - 0.001, this.getEntityBoundingBox().maxY - 0.001, this.getEntityBoundingBox().maxZ - 0.001);
        if (this.worldObj.isAreaLoaded(blockPos, blockPos2)) {
            int i = blockPos.getX();
            "".length();
            if (4 == -1) {
                throw null;
            }
            while (i <= blockPos2.getX()) {
                int j = blockPos.getY();
                "".length();
                if (!true) {
                    throw null;
                }
                while (j <= blockPos2.getY()) {
                    int k = blockPos.getZ();
                    "".length();
                    if (1 < 0) {
                        throw null;
                    }
                    while (k <= blockPos2.getZ()) {
                        final BlockPos blockPos3 = new BlockPos(i, j, k);
                        final IBlockState blockState = this.worldObj.getBlockState(blockPos3);
                        try {
                            blockState.getBlock().onEntityCollidedWithBlock(this.worldObj, blockPos3, blockState, this);
                            "".length();
                            if (!true) {
                                throw null;
                            }
                        }
                        catch (Throwable t) {
                            final CrashReport crashReport = CrashReport.makeCrashReport(t, Entity.I[0x90 ^ 0x99]);
                            CrashReportCategory.addBlockInfo(crashReport.makeCategory(Entity.I[0x34 ^ 0x3E]), blockPos3, blockState);
                            throw new ReportedException(crashReport);
                        }
                        ++k;
                    }
                    ++j;
                }
                ++i;
            }
        }
    }
    
    protected HoverEvent getHoverEvent() {
        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
        final String entityString = EntityList.getEntityString(this);
        nbtTagCompound.setString(Entity.I[0xC6 ^ 0x8F], this.getUniqueID().toString());
        if (entityString != null) {
            nbtTagCompound.setString(Entity.I[0x47 ^ 0xD], entityString);
        }
        nbtTagCompound.setString(Entity.I[0x3A ^ 0x71], this.getName());
        return new HoverEvent(HoverEvent.Action.SHOW_ENTITY, new ChatComponentText(nbtTagCompound.toString()));
    }
    
    protected void applyEnchantments(final EntityLivingBase entityLivingBase, final Entity entity) {
        if (entity instanceof EntityLivingBase) {
            EnchantmentHelper.applyThornEnchantments((EntityLivingBase)entity, entityLivingBase);
        }
        EnchantmentHelper.applyArthropodEnchantments(entityLivingBase, entity);
    }
    
    public void setEntityId(final int entityId) {
        this.entityId = entityId;
    }
    
    public ItemStack[] getInventory() {
        return null;
    }
    
    public void mountEntity(final Entity ridingEntity) {
        this.entityRiderPitchDelta = 0.0;
        this.entityRiderYawDelta = 0.0;
        if (ridingEntity == null) {
            if (this.ridingEntity != null) {
                this.setLocationAndAngles(this.ridingEntity.posX, this.ridingEntity.getEntityBoundingBox().minY + this.ridingEntity.height, this.ridingEntity.posZ, this.rotationYaw, this.rotationPitch);
                this.ridingEntity.riddenByEntity = null;
            }
            this.ridingEntity = null;
            "".length();
            if (4 < -1) {
                throw null;
            }
        }
        else {
            if (this.ridingEntity != null) {
                this.ridingEntity.riddenByEntity = null;
            }
            if (ridingEntity != null) {
                Entity entity = ridingEntity.ridingEntity;
                "".length();
                if (0 >= 1) {
                    throw null;
                }
                while (entity != null) {
                    if (entity == this) {
                        return;
                    }
                    entity = entity.ridingEntity;
                }
            }
            this.ridingEntity = ridingEntity;
            ridingEntity.riddenByEntity = this;
        }
    }
    
    public AxisAlignedBB getCollisionBoundingBox() {
        return null;
    }
    
    public void setInvisible(final boolean b) {
        this.setFlag(0x56 ^ 0x53, b);
    }
    
    protected abstract void entityInit();
    
    public boolean writeToNBTOptional(final NBTTagCompound nbtTagCompound) {
        final String entityString = this.getEntityString();
        if (!this.isDead && entityString != null && this.riddenByEntity == null) {
            nbtTagCompound.setString(Entity.I[0xE ^ 0x3], entityString);
            this.writeToNBT(nbtTagCompound);
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public void readFromNBT(final NBTTagCompound nbtTagCompound) {
        try {
            final NBTTagList tagList = nbtTagCompound.getTagList(Entity.I[0x7F ^ 0x5F], 0x9E ^ 0x98);
            final NBTTagList tagList2 = nbtTagCompound.getTagList(Entity.I[0x0 ^ 0x21], 0x69 ^ 0x6F);
            final NBTTagList tagList3 = nbtTagCompound.getTagList(Entity.I[0x68 ^ 0x4A], 0x79 ^ 0x7C);
            this.motionX = tagList2.getDoubleAt("".length());
            this.motionY = tagList2.getDoubleAt(" ".length());
            this.motionZ = tagList2.getDoubleAt("  ".length());
            if (Math.abs(this.motionX) > 10.0) {
                this.motionX = 0.0;
            }
            if (Math.abs(this.motionY) > 10.0) {
                this.motionY = 0.0;
            }
            if (Math.abs(this.motionZ) > 10.0) {
                this.motionZ = 0.0;
            }
            final double double1 = tagList.getDoubleAt("".length());
            this.posX = double1;
            this.lastTickPosX = double1;
            this.prevPosX = double1;
            final double double2 = tagList.getDoubleAt(" ".length());
            this.posY = double2;
            this.lastTickPosY = double2;
            this.prevPosY = double2;
            final double double3 = tagList.getDoubleAt("  ".length());
            this.posZ = double3;
            this.lastTickPosZ = double3;
            this.prevPosZ = double3;
            final float float1 = tagList3.getFloatAt("".length());
            this.rotationYaw = float1;
            this.prevRotationYaw = float1;
            final float float2 = tagList3.getFloatAt(" ".length());
            this.rotationPitch = float2;
            this.prevRotationPitch = float2;
            this.setRotationYawHead(this.rotationYaw);
            this.func_181013_g(this.rotationYaw);
            this.fallDistance = nbtTagCompound.getFloat(Entity.I[0x6F ^ 0x4C]);
            this.fire = nbtTagCompound.getShort(Entity.I[0xA6 ^ 0x82]);
            this.setAir(nbtTagCompound.getShort(Entity.I[0xBD ^ 0x98]));
            this.onGround = nbtTagCompound.getBoolean(Entity.I[0x2A ^ 0xC]);
            this.dimension = nbtTagCompound.getInteger(Entity.I[0x58 ^ 0x7F]);
            this.invulnerable = nbtTagCompound.getBoolean(Entity.I[0x5D ^ 0x75]);
            this.timeUntilPortal = nbtTagCompound.getInteger(Entity.I[0x49 ^ 0x60]);
            if (nbtTagCompound.hasKey(Entity.I[0xA4 ^ 0x8E], 0x6E ^ 0x6A) && nbtTagCompound.hasKey(Entity.I[0x4 ^ 0x2F], 0x36 ^ 0x32)) {
                this.entityUniqueID = new UUID(nbtTagCompound.getLong(Entity.I[0x43 ^ 0x6F]), nbtTagCompound.getLong(Entity.I[0x90 ^ 0xBD]));
                "".length();
                if (4 < 1) {
                    throw null;
                }
            }
            else if (nbtTagCompound.hasKey(Entity.I[0x28 ^ 0x6], 0x7B ^ 0x73)) {
                this.entityUniqueID = UUID.fromString(nbtTagCompound.getString(Entity.I[0x3C ^ 0x13]));
            }
            this.setPosition(this.posX, this.posY, this.posZ);
            this.setRotation(this.rotationYaw, this.rotationPitch);
            if (nbtTagCompound.hasKey(Entity.I[0x19 ^ 0x29], 0x32 ^ 0x3A) && nbtTagCompound.getString(Entity.I[0x6E ^ 0x5F]).length() > 0) {
                this.setCustomNameTag(nbtTagCompound.getString(Entity.I[0x93 ^ 0xA1]));
            }
            this.setAlwaysRenderNameTag(nbtTagCompound.getBoolean(Entity.I[0x10 ^ 0x23]));
            this.cmdResultStats.readStatsFromNBT(nbtTagCompound);
            this.setSilent(nbtTagCompound.getBoolean(Entity.I[0x68 ^ 0x5C]));
            this.readEntityFromNBT(nbtTagCompound);
            if (this.shouldSetPosAfterLoading()) {
                this.setPosition(this.posX, this.posY, this.posZ);
                "".length();
                if (3 < -1) {
                    throw null;
                }
            }
        }
        catch (Throwable t) {
            final CrashReport crashReport = CrashReport.makeCrashReport(t, Entity.I[0x49 ^ 0x7C]);
            this.addEntityCrashInfo(crashReport.makeCategory(Entity.I[0x62 ^ 0x54]));
            throw new ReportedException(crashReport);
        }
    }
    
    public void setOutsideBorder(final boolean isOutsideBorder) {
        this.isOutsideBorder = isOutsideBorder;
    }
    
    public void spawnRunningParticles() {
        if (this.isSprinting() && !this.isInWater()) {
            this.createRunningParticles();
        }
    }
    
    public boolean canAttackWithItem() {
        return " ".length() != 0;
    }
    
    public boolean isInRangeToRender3d(final double n, final double n2, final double n3) {
        final double n4 = this.posX - n;
        final double n5 = this.posY - n2;
        final double n6 = this.posZ - n3;
        return this.isInRangeToRenderDist(n4 * n4 + n5 * n5 + n6 * n6);
    }
    
    public void setVelocity(final double motionX, final double motionY, final double motionZ) {
        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;
    }
    
    public void copyDataFromOld(final Entity entity) {
        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
        entity.writeToNBT(nbtTagCompound);
        this.readFromNBT(nbtTagCompound);
        this.timeUntilPortal = entity.timeUntilPortal;
        this.field_181016_an = entity.field_181016_an;
        this.field_181017_ao = entity.field_181017_ao;
        this.field_181018_ap = entity.field_181018_ap;
    }
    
    @Override
    public BlockPos getPosition() {
        return new BlockPos(this.posX, this.posY + 0.5, this.posZ);
    }
    
    public Entity(final World worldObj) {
        final int nextEntityID = Entity.nextEntityID;
        Entity.nextEntityID = nextEntityID + " ".length();
        this.entityId = nextEntityID;
        this.renderDistanceWeight = 1.0;
        this.boundingBox = Entity.ZERO_AABB;
        this.width = 0.6f;
        this.height = 1.8f;
        this.nextStepDistance = " ".length();
        this.rand = new Random();
        this.fireResistance = " ".length();
        this.firstUpdate = (" ".length() != 0);
        this.entityUniqueID = MathHelper.getRandomUuid(this.rand);
        this.cmdResultStats = new CommandResultStats();
        this.worldObj = worldObj;
        this.setPosition(0.0, 0.0, 0.0);
        if (worldObj != null) {
            this.dimension = worldObj.provider.getDimensionId();
        }
        (this.dataWatcher = new DataWatcher(this)).addObject("".length(), (byte)"".length());
        this.dataWatcher.addObject(" ".length(), (short)(150 + 92 + 10 + 48));
        this.dataWatcher.addObject("   ".length(), (byte)"".length());
        this.dataWatcher.addObject("  ".length(), Entity.I["".length()]);
        this.dataWatcher.addObject(0x7E ^ 0x7A, (byte)"".length());
        this.entityInit();
    }
    
    public boolean isInvisible() {
        return this.getFlag(0xC1 ^ 0xC4);
    }
    
    public void fall(final float n, final float n2) {
        if (this.riddenByEntity != null) {
            this.riddenByEntity.fall(n, n2);
        }
    }
    
    protected void setRotation(final float n, final float n2) {
        this.rotationYaw = n % 360.0f;
        this.rotationPitch = n2 % 360.0f;
    }
    
    public void extinguish() {
        this.fire = "".length();
    }
    
    public void updateRiderPosition() {
        if (this.riddenByEntity != null) {
            this.riddenByEntity.setPosition(this.posX, this.posY + this.getMountedYOffset() + this.riddenByEntity.getYOffset(), this.posZ);
        }
    }
    
    public EnumFacing getHorizontalFacing() {
        return EnumFacing.getHorizontal(MathHelper.floor_double(this.rotationYaw * 4.0f / 360.0f + 0.5) & "   ".length());
    }
    
    public void moveToBlockPosAndAngles(final BlockPos blockPos, final float n, final float n2) {
        this.setLocationAndAngles(blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5, n, n2);
    }
    
    public double getDistanceSqToCenter(final BlockPos blockPos) {
        return blockPos.distanceSqToCenter(this.posX, this.posY, this.posZ);
    }
    
    @Override
    public IChatComponent getDisplayName() {
        final ChatComponentText chatComponentText = new ChatComponentText(this.getName());
        chatComponentText.getChatStyle().setChatHoverEvent(this.getHoverEvent());
        chatComponentText.getChatStyle().setInsertion(this.getUniqueID().toString());
        return chatComponentText;
    }
    
    protected void dealFireDamage(final int n) {
        if (!this.isImmuneToFire) {
            this.attackEntityFrom(DamageSource.inFire, n);
        }
    }
    
    public boolean isInLava() {
        return this.worldObj.isMaterialInBB(this.getEntityBoundingBox().expand(-0.10000000149011612, -0.4000000059604645, -0.10000000149011612), Material.lava);
    }
    
    public void setEntityBoundingBox(final AxisAlignedBB boundingBox) {
        this.boundingBox = boundingBox;
    }
    
    public String getCustomNameTag() {
        return this.dataWatcher.getWatchableObjectString("  ".length());
    }
    
    public boolean isEating() {
        return this.getFlag(0x42 ^ 0x46);
    }
    
    public boolean isEntityInsideOpaqueBlock() {
        if (this.noClip) {
            return "".length() != 0;
        }
        final BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos(-"".length(), -"".length(), -"".length());
        int i = "".length();
        "".length();
        if (3 <= 1) {
            throw null;
        }
        while (i < (0x46 ^ 0x4E)) {
            final int floor_double = MathHelper.floor_double(this.posY + ((i >> "".length()) % "  ".length() - 0.5f) * 0.1f + this.getEyeHeight());
            final int floor_double2 = MathHelper.floor_double(this.posX + ((i >> " ".length()) % "  ".length() - 0.5f) * this.width * 0.8f);
            final int floor_double3 = MathHelper.floor_double(this.posZ + ((i >> "  ".length()) % "  ".length() - 0.5f) * this.width * 0.8f);
            if (mutableBlockPos.getX() != floor_double2 || mutableBlockPos.getY() != floor_double || mutableBlockPos.getZ() != floor_double3) {
                mutableBlockPos.func_181079_c(floor_double2, floor_double, floor_double3);
                if (this.worldObj.getBlockState(mutableBlockPos).getBlock().isVisuallyOpaque()) {
                    return " ".length() != 0;
                }
            }
            ++i;
        }
        return "".length() != 0;
    }
    
    public void setAngles(final float n, final float n2) {
        final float rotationPitch = this.rotationPitch;
        final float rotationYaw = this.rotationYaw;
        this.rotationYaw += (float)(n * 0.15);
        this.rotationPitch -= (float)(n2 * 0.15);
        this.rotationPitch = MathHelper.clamp_float(this.rotationPitch, -90.0f, 90.0f);
        this.prevRotationPitch += this.rotationPitch - rotationPitch;
        this.prevRotationYaw += this.rotationYaw - rotationYaw;
    }
    
    public Vec3 getLookVec() {
        return null;
    }
    
    public void addToPlayerScore(final Entity entity, final int n) {
    }
    
    public float getCollisionBorderSize() {
        return 0.1f;
    }
    
    protected NBTTagList newFloatNBTList(final float... array) {
        final NBTTagList list = new NBTTagList();
        final int length = array.length;
        int i = "".length();
        "".length();
        if (-1 >= 0) {
            throw null;
        }
        while (i < length) {
            list.appendTag(new NBTTagFloat(array[i]));
            ++i;
        }
        return list;
    }
    
    @Override
    public String toString() {
        final String s = Entity.I[0x38 ^ 0x2];
        final Object[] array = new Object[0xAB ^ 0xAC];
        array["".length()] = this.getClass().getSimpleName();
        array[" ".length()] = this.getName();
        array["  ".length()] = this.entityId;
        final int length = "   ".length();
        String worldName;
        if (this.worldObj == null) {
            worldName = Entity.I[0xC ^ 0x37];
            "".length();
            if (0 == 4) {
                throw null;
            }
        }
        else {
            worldName = this.worldObj.getWorldInfo().getWorldName();
        }
        array[length] = worldName;
        array[0x1 ^ 0x5] = this.posX;
        array[0x7C ^ 0x79] = this.posY;
        array[0xB0 ^ 0xB6] = this.posZ;
        return String.format(s, array);
    }
    
    public boolean getAlwaysRenderNameTagForRender() {
        return this.getAlwaysRenderNameTag();
    }
    
    public void onUpdate() {
        this.onEntityUpdate();
    }
    
    public float getEyeHeight() {
        return this.height * 0.85f;
    }
    
    public void playSound(final String s, final float n, final float n2) {
        if (!this.isSilent()) {
            this.worldObj.playSoundAtEntity(this, s, n, n2);
        }
    }
    
    public float getRotationYawHead() {
        return 0.0f;
    }
    
    public CommandResultStats getCommandStats() {
        return this.cmdResultStats;
    }
    
    public boolean isInvisibleToPlayer(final EntityPlayer entityPlayer) {
        int n;
        if (entityPlayer.isSpectator()) {
            n = "".length();
            "".length();
            if (1 == 3) {
                throw null;
            }
        }
        else {
            n = (this.isInvisible() ? 1 : 0);
        }
        return n != 0;
    }
    
    public boolean hitByEntity(final Entity entity) {
        return "".length() != 0;
    }
    
    @Override
    public void addChatMessage(final IChatComponent chatComponent) {
    }
    
    protected void playStepSound(final BlockPos blockPos, final Block block) {
        final Block.SoundType stepSound = block.stepSound;
        if (this.worldObj.getBlockState(blockPos.up()).getBlock() == Blocks.snow_layer) {
            final Block.SoundType stepSound2 = Blocks.snow_layer.stepSound;
            this.playSound(stepSound2.getStepSound(), stepSound2.getVolume() * 0.15f, stepSound2.getFrequency());
            "".length();
            if (4 < 1) {
                throw null;
            }
        }
        else if (!block.getMaterial().isLiquid()) {
            this.playSound(stepSound.getStepSound(), stepSound.getVolume() * 0.15f, stepSound.getFrequency());
        }
    }
    
    public void onKillEntity(final EntityLivingBase entityLivingBase) {
    }
    
    public double getDistanceSq(final BlockPos blockPos) {
        return blockPos.distanceSq(this.posX, this.posY, this.posZ);
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (-1 < -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public Vec3 getPositionVector() {
        return new Vec3(this.posX, this.posY, this.posZ);
    }
    
    public boolean isSilent() {
        if (this.dataWatcher.getWatchableObjectByte(0x63 ^ 0x67) == " ".length()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public double getDistance(final double n, final double n2, final double n3) {
        final double n4 = this.posX - n;
        final double n5 = this.posY - n2;
        final double n6 = this.posZ - n3;
        return MathHelper.sqrt_double(n4 * n4 + n5 * n5 + n6 * n6);
    }
    
    public boolean isOffsetPositionInLiquid(final double n, final double n2, final double n3) {
        return this.isLiquidPresentInAABB(this.getEntityBoundingBox().offset(n, n2, n3));
    }
    
    public float getBrightness(final float n) {
        final BlockPos blockPos = new BlockPos(this.posX, this.posY + this.getEyeHeight(), this.posZ);
        float lightBrightness;
        if (this.worldObj.isBlockLoaded(blockPos)) {
            lightBrightness = this.worldObj.getLightBrightness(blockPos);
            "".length();
            if (false) {
                throw null;
            }
        }
        else {
            lightBrightness = 0.0f;
        }
        return lightBrightness;
    }
    
    public MovingObjectPosition rayTrace(final double n, final float n2) {
        final Vec3 positionEyes = this.getPositionEyes(n2);
        final Vec3 look = this.getLook(n2);
        return this.worldObj.rayTraceBlocks(positionEyes, positionEyes.addVector(look.xCoord * n, look.yCoord * n, look.zCoord * n), "".length() != 0, "".length() != 0, " ".length() != 0);
    }
    
    public void func_174817_o(final Entity entity) {
        this.cmdResultStats.func_179671_a(entity.getCommandStats());
    }
    
    public double getMountedYOffset() {
        return this.height * 0.75;
    }
    
    protected final Vec3 getVectorForRotation(final float n, final float n2) {
        final float cos = MathHelper.cos(-n2 * 0.017453292f - 3.1415927f);
        final float sin = MathHelper.sin(-n2 * 0.017453292f - 3.1415927f);
        final float n3 = -MathHelper.cos(-n * 0.017453292f);
        return new Vec3(sin * n3, MathHelper.sin(-n * 0.017453292f), cos * n3);
    }
    
    public boolean hasCustomName() {
        if (this.dataWatcher.getWatchableObjectString("  ".length()).length() > 0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public void addEntityCrashInfo(final CrashReportCategory crashReportCategory) {
        crashReportCategory.addCrashSectionCallable(Entity.I[0x2F ^ 0x10], new Callable<String>(this) {
            private static final String[] I;
            final Entity this$0;
            
            private static void I() {
                (I = new String["  ".length()])["".length()] = I("cp", "CXXgR");
                Entity$1.I[" ".length()] = I("N", "gNPws");
            }
            
            @Override
            public String call() throws Exception {
                return String.valueOf(EntityList.getEntityString(this.this$0)) + Entity$1.I["".length()] + this.this$0.getClass().getCanonicalName() + Entity$1.I[" ".length()];
            }
            
            static {
                I();
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
            
            private static String I(final String s, final String s2) {
                final StringBuilder sb = new StringBuilder();
                final char[] charArray = s2.toCharArray();
                int length = "".length();
                final char[] charArray2 = s.toCharArray();
                final int length2 = charArray2.length;
                int i = "".length();
                while (i < length2) {
                    sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                    ++length;
                    ++i;
                    "".length();
                    if (4 < 4) {
                        throw null;
                    }
                }
                return sb.toString();
            }
        });
        crashReportCategory.addCrashSection(Entity.I[0x4 ^ 0x44], this.entityId);
        crashReportCategory.addCrashSectionCallable(Entity.I[0xC0 ^ 0x81], new Callable<String>(this) {
            final Entity this$0;
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
            
            private static String I(final String s, final String s2) {
                final StringBuilder sb = new StringBuilder();
                final char[] charArray = s2.toCharArray();
                int length = "".length();
                final char[] charArray2 = s.toCharArray();
                final int length2 = charArray2.length;
                int i = "".length();
                while (i < length2) {
                    sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                    ++length;
                    ++i;
                    "".length();
                    if (2 <= 0) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            @Override
            public String call() throws Exception {
                return this.this$0.getName();
            }
        });
        final String s = Entity.I[0x49 ^ 0xB];
        final String s2 = Entity.I[0x76 ^ 0x35];
        final Object[] array = new Object["   ".length()];
        array["".length()] = this.posX;
        array[" ".length()] = this.posY;
        array["  ".length()] = this.posZ;
        crashReportCategory.addCrashSection(s, String.format(s2, array));
        crashReportCategory.addCrashSection(Entity.I[0xFE ^ 0xBA], CrashReportCategory.getCoordinateInfo(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)));
        final String s3 = Entity.I[0x85 ^ 0xC0];
        final String s4 = Entity.I[0xF6 ^ 0xB0];
        final Object[] array2 = new Object["   ".length()];
        array2["".length()] = this.motionX;
        array2[" ".length()] = this.motionY;
        array2["  ".length()] = this.motionZ;
        crashReportCategory.addCrashSection(s3, String.format(s4, array2));
        crashReportCategory.addCrashSectionCallable(Entity.I[0x4A ^ 0xD], new Callable<String>(this) {
            final Entity this$0;
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
            
            @Override
            public String call() throws Exception {
                return this.this$0.riddenByEntity.toString();
            }
            
            private static String I(final String s, final String s2) {
                final StringBuilder sb = new StringBuilder();
                final char[] charArray = s2.toCharArray();
                int length = "".length();
                final char[] charArray2 = s.toCharArray();
                final int length2 = charArray2.length;
                int i = "".length();
                while (i < length2) {
                    sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                    ++length;
                    ++i;
                    "".length();
                    if (4 != 4) {
                        throw null;
                    }
                }
                return sb.toString();
            }
        });
        crashReportCategory.addCrashSectionCallable(Entity.I[0x6 ^ 0x4E], new Callable<String>(this) {
            final Entity this$0;
            
            private static String I(final String s, final String s2) {
                final StringBuilder sb = new StringBuilder();
                final char[] charArray = s2.toCharArray();
                int length = "".length();
                final char[] charArray2 = s.toCharArray();
                final int length2 = charArray2.length;
                int i = "".length();
                while (i < length2) {
                    sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                    ++length;
                    ++i;
                    "".length();
                    if (2 == 3) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            @Override
            public String call() throws Exception {
                return this.this$0.ridingEntity.toString();
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
        });
    }
    
    protected void createRunningParticles() {
        final IBlockState blockState = this.worldObj.getBlockState(new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY - 0.20000000298023224), MathHelper.floor_double(this.posZ)));
        if (blockState.getBlock().getRenderType() != -" ".length()) {
            final World worldObj = this.worldObj;
            final EnumParticleTypes block_CRACK = EnumParticleTypes.BLOCK_CRACK;
            final double n = this.posX + (this.rand.nextFloat() - 0.5) * this.width;
            final double n2 = this.getEntityBoundingBox().minY + 0.1;
            final double n3 = this.posZ + (this.rand.nextFloat() - 0.5) * this.width;
            final double n4 = -this.motionX * 4.0;
            final double n5 = 1.5;
            final double n6 = -this.motionZ * 4.0;
            final int[] array = new int[" ".length()];
            array["".length()] = Block.getStateId(blockState);
            worldObj.spawnParticle(block_CRACK, n, n2, n3, n4, n5, n6, array);
        }
    }
    
    public void setSilent(final boolean b) {
        final DataWatcher dataWatcher = this.dataWatcher;
        final int n = 0x4E ^ 0x4A;
        int n2;
        if (b) {
            n2 = " ".length();
            "".length();
            if (0 >= 2) {
                throw null;
            }
        }
        else {
            n2 = "".length();
        }
        dataWatcher.updateObject(n, (byte)n2);
    }
    
    protected String getSwimSound() {
        return Entity.I[0xA9 ^ 0xA1];
    }
    
    public int getAir() {
        return this.dataWatcher.getWatchableObjectShort(" ".length());
    }
    
    protected void updateFallState(final double n, final boolean b, final Block block, final BlockPos blockPos) {
        if (b) {
            if (this.fallDistance > 0.0f) {
                if (block != null) {
                    block.onFallenUpon(this.worldObj, blockPos, this, this.fallDistance);
                    "".length();
                    if (0 >= 3) {
                        throw null;
                    }
                }
                else {
                    this.fall(this.fallDistance, 1.0f);
                }
                this.fallDistance = 0.0f;
                "".length();
                if (3 < 1) {
                    throw null;
                }
            }
        }
        else if (n < 0.0) {
            this.fallDistance -= (float)n;
        }
    }
    
    public boolean isImmuneToExplosions() {
        return "".length() != 0;
    }
    
    protected abstract void writeEntityToNBT(final NBTTagCompound p0);
    
    public void setCurrentItemOrArmor(final int n, final ItemStack itemStack) {
    }
    
    public void setSneaking(final boolean b) {
        this.setFlag(" ".length(), b);
    }
    
    protected boolean canTriggerWalking() {
        return " ".length() != 0;
    }
    
    public boolean replaceItemInInventory(final int n, final ItemStack itemStack) {
        return "".length() != 0;
    }
    
    public void clientUpdateEntityNBT(final NBTTagCompound nbtTagCompound) {
    }
    
    public Vec3 getLook(final float n) {
        if (n == 1.0f) {
            return this.getVectorForRotation(this.rotationPitch, this.rotationYaw);
        }
        return this.getVectorForRotation(this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * n, this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * n);
    }
    
    public int getMaxInPortalTime() {
        return "".length();
    }
    
    public void handleStatusUpdate(final byte b) {
    }
    
    public void onCollideWithPlayer(final EntityPlayer entityPlayer) {
    }
    
    public boolean isEntityInvulnerable(final DamageSource damageSource) {
        if (this.invulnerable && damageSource != DamageSource.outOfWorld && !damageSource.isCreativePlayer()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    protected abstract void readEntityFromNBT(final NBTTagCompound p0);
    
    public AxisAlignedBB getEntityBoundingBox() {
        return this.boundingBox;
    }
    
    @Override
    public int hashCode() {
        return this.entityId;
    }
    
    public boolean getAlwaysRenderNameTag() {
        if (this.dataWatcher.getWatchableObjectByte("   ".length()) == " ".length()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public void onEntityUpdate() {
        this.worldObj.theProfiler.startSection(Entity.I[" ".length()]);
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
            this.worldObj.theProfiler.startSection(Entity.I["  ".length()]);
            final MinecraftServer minecraftServer = ((WorldServer)this.worldObj).getMinecraftServer();
            final int maxInPortalTime = this.getMaxInPortalTime();
            if (this.inPortal) {
                if (minecraftServer.getAllowNether()) {
                    if (this.ridingEntity == null) {
                        final int portalCounter = this.portalCounter;
                        this.portalCounter = portalCounter + " ".length();
                        if (portalCounter >= maxInPortalTime) {
                            this.portalCounter = maxInPortalTime;
                            this.timeUntilPortal = this.getPortalCooldown();
                            int length;
                            if (this.worldObj.provider.getDimensionId() == -" ".length()) {
                                length = "".length();
                                "".length();
                                if (3 == 4) {
                                    throw null;
                                }
                            }
                            else {
                                length = -" ".length();
                            }
                            this.travelToDimension(length);
                        }
                    }
                    this.inPortal = ("".length() != 0);
                    "".length();
                    if (2 <= 1) {
                        throw null;
                    }
                }
            }
            else {
                if (this.portalCounter > 0) {
                    this.portalCounter -= (0x24 ^ 0x20);
                }
                if (this.portalCounter < 0) {
                    this.portalCounter = "".length();
                }
            }
            if (this.timeUntilPortal > 0) {
                this.timeUntilPortal -= " ".length();
            }
            this.worldObj.theProfiler.endSection();
        }
        this.spawnRunningParticles();
        this.handleWaterMovement();
        if (this.worldObj.isRemote) {
            this.fire = "".length();
            "".length();
            if (4 <= 0) {
                throw null;
            }
        }
        else if (this.fire > 0) {
            if (this.isImmuneToFire) {
                this.fire -= (0x61 ^ 0x65);
                if (this.fire < 0) {
                    this.fire = "".length();
                    "".length();
                    if (3 <= 1) {
                        throw null;
                    }
                }
            }
            else {
                if (this.fire % (0x29 ^ 0x3D) == 0) {
                    this.attackEntityFrom(DamageSource.onFire, 1.0f);
                }
                this.fire -= " ".length();
            }
        }
        if (this.isInLava()) {
            this.setOnFireFromLava();
            this.fallDistance *= 0.5f;
        }
        if (this.posY < -64.0) {
            this.kill();
        }
        if (!this.worldObj.isRemote) {
            final int length2 = "".length();
            int n;
            if (this.fire > 0) {
                n = " ".length();
                "".length();
                if (2 < -1) {
                    throw null;
                }
            }
            else {
                n = "".length();
            }
            this.setFlag(length2, n != 0);
        }
        this.firstUpdate = ("".length() != 0);
        this.worldObj.theProfiler.endSection();
    }
    
    protected boolean shouldSetPosAfterLoading() {
        return " ".length() != 0;
    }
    
    public void updateRidden() {
        if (this.ridingEntity.isDead) {
            this.ridingEntity = null;
            "".length();
            if (2 <= 0) {
                throw null;
            }
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
                "".length();
                if (0 == 2) {
                    throw null;
                }
                while (this.entityRiderYawDelta >= 180.0) {
                    this.entityRiderYawDelta -= 360.0;
                }
                "".length();
                if (2 != 2) {
                    throw null;
                }
                while (this.entityRiderYawDelta < -180.0) {
                    this.entityRiderYawDelta += 360.0;
                }
                "".length();
                if (0 >= 2) {
                    throw null;
                }
                while (this.entityRiderPitchDelta >= 180.0) {
                    this.entityRiderPitchDelta -= 360.0;
                }
                "".length();
                if (4 < 4) {
                    throw null;
                }
                while (this.entityRiderPitchDelta < -180.0) {
                    this.entityRiderPitchDelta += 360.0;
                }
                double n = this.entityRiderYawDelta * 0.5;
                double n2 = this.entityRiderPitchDelta * 0.5;
                final float n3 = 10.0f;
                if (n > n3) {
                    n = n3;
                }
                if (n < -n3) {
                    n = -n3;
                }
                if (n2 > n3) {
                    n2 = n3;
                }
                if (n2 < -n3) {
                    n2 = -n3;
                }
                this.entityRiderYawDelta -= n;
                this.entityRiderPitchDelta -= n2;
            }
        }
    }
    
    public int getPortalCooldown() {
        return 1 + 63 + 180 + 56;
    }
    
    public boolean writeMountToNBT(final NBTTagCompound nbtTagCompound) {
        final String entityString = this.getEntityString();
        if (!this.isDead && entityString != null) {
            nbtTagCompound.setString(Entity.I[0x89 ^ 0x85], entityString);
            this.writeToNBT(nbtTagCompound);
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    private void resetPositionToBB() {
        this.posX = (this.getEntityBoundingBox().minX + this.getEntityBoundingBox().maxX) / 2.0;
        this.posY = this.getEntityBoundingBox().minY;
        this.posZ = (this.getEntityBoundingBox().minZ + this.getEntityBoundingBox().maxZ) / 2.0;
    }
    
    public void setRotationYawHead(final float n) {
    }
    
    public void setInWeb() {
        this.isInWeb = (" ".length() != 0);
        this.fallDistance = 0.0f;
    }
    
    public void setPositionAndRotation(final double n, final double n2, final double n3, final float n4, final float n5) {
        this.posX = n;
        this.prevPosX = n;
        this.posY = n2;
        this.prevPosY = n2;
        this.posZ = n3;
        this.prevPosZ = n3;
        this.rotationYaw = n4;
        this.prevRotationYaw = n4;
        this.rotationPitch = n5;
        this.prevRotationPitch = n5;
        final double n6 = this.prevRotationYaw - n4;
        if (n6 < -180.0) {
            this.prevRotationYaw += 360.0f;
        }
        if (n6 >= 180.0) {
            this.prevRotationYaw -= 360.0f;
        }
        this.setPosition(this.posX, this.posY, this.posZ);
        this.setRotation(n4, n5);
    }
    
    protected void preparePlayerToSpawn() {
        if (this.worldObj != null) {
            "".length();
            if (1 <= -1) {
                throw null;
            }
            while (this.posY > 0.0 && this.posY < 256.0) {
                this.setPosition(this.posX, this.posY, this.posZ);
                if (this.worldObj.getCollidingBoundingBoxes(this, this.getEntityBoundingBox()).isEmpty()) {
                    "".length();
                    if (1 <= 0) {
                        throw null;
                    }
                    break;
                }
                else {
                    ++this.posY;
                }
            }
            final double motionX = 0.0;
            this.motionZ = motionX;
            this.motionY = motionX;
            this.motionX = motionX;
            this.rotationPitch = 0.0f;
        }
    }
    
    protected final String getEntityString() {
        return EntityList.getEntityString(this);
    }
    
    protected void kill() {
        this.setDead();
    }
    
    public final boolean isImmuneToFire() {
        return this.isImmuneToFire;
    }
    
    public void setCustomNameTag(final String s) {
        this.dataWatcher.updateObject("  ".length(), s);
    }
    
    public EntityItem dropItemWithOffset(final Item item, final int n, final float n2) {
        return this.entityDropItem(new ItemStack(item, n, "".length()), n2);
    }
    
    public int getBrightnessForRender(final float n) {
        final BlockPos blockPos = new BlockPos(this.posX, this.posY + this.getEyeHeight(), this.posZ);
        int n2;
        if (this.worldObj.isBlockLoaded(blockPos)) {
            n2 = this.worldObj.getCombinedLight(blockPos, "".length());
            "".length();
            if (4 < 4) {
                throw null;
            }
        }
        else {
            n2 = "".length();
        }
        return n2;
    }
    
    public boolean canBePushed() {
        return "".length() != 0;
    }
    
    public double getDistanceSqToEntity(final Entity entity) {
        final double n = this.posX - entity.posX;
        final double n2 = this.posY - entity.posY;
        final double n3 = this.posZ - entity.posZ;
        return n * n + n2 * n2 + n3 * n3;
    }
    
    public boolean handleWaterMovement() {
        if (this.worldObj.handleMaterialAcceleration(this.getEntityBoundingBox().expand(0.0, -0.4000000059604645, 0.0).contract(0.001, 0.001, 0.001), Material.water, this)) {
            if (!this.inWater && !this.firstUpdate) {
                this.resetHeight();
            }
            this.fallDistance = 0.0f;
            this.inWater = (" ".length() != 0);
            this.fire = "".length();
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        else {
            this.inWater = ("".length() != 0);
        }
        return this.inWater;
    }
    
    private static void I() {
        (I = new String[0x7 ^ 0x4B])["".length()] = I("", "KbdcZ");
        Entity.I[" ".length()] = I("=\u00023\u0003#!.&\u00192\f\u0005$\u0001", "XlGjW");
        Entity.I["  ".length()] = I("!\u0005 %)=", "QjRQH");
        Entity.I["   ".length()] = I("\u001f\f\u000e(", "rcxMt");
        Entity.I[0xA6 ^ 0xA2] = I("0\u00065?", "BcFKu");
        Entity.I[0x27 ^ 0x22] = I("\u0010<\u00042\u001d::\u0006q\u0013= \b%\u000fs6\r>\u00158t\u0002>\u001a?=\u00128\u0019=", "STaQv");
        Entity.I[0x5F ^ 0x59] = I("\u001f9;\u0018\r#w-\u0014\u001040o\u0012\u0011?4$\u0014\u001dz1 \u0003Y98#\u001d\u0010)> \u001f", "ZWOqy");
        Entity.I[0x21 ^ 0x26] = I("\u0019\u0012\u001d\u0017$\u0006]\u0015\u001a1\u0011", "ksssK");
        Entity.I[0x34 ^ 0x3C] = I(",\u0018\u0003+o%\u001c\u001b:3*\u0015@=6\"\u0014", "KynNA");
        Entity.I[0xCD ^ 0xC4] = I("\u000e$'\u0002\u0004)\"%\tM(%?\u0007\u00194k<\u0007\u0019%k)\u0002\u0002. ", "MKKnm");
        Entity.I[0x8A ^ 0x80] = I("&+\u001f\u001a2D%\u0015\u00107\u0003g\u0013\u00165\b.\u0014\u001c=D0\u0019\r1", "dGpyY");
        Entity.I[0x2B ^ 0x20] = I("&\u0014\u00050J/\u0010\u001d!\u0016 \u0019F&\u0013(\u0018F&\u0014-\u0014\u001b=", "AuhUd");
        Entity.I[0x22 ^ 0x2E] = I(" =", "IYgYI");
        Entity.I[0x21 ^ 0x2C] = I("\u000f=", "fYQTs");
        Entity.I[0xF ^ 0x1] = I(" \u001b)", "ptZhc");
        Entity.I[0xB ^ 0x4] = I("\t! \u00187*", "DNTqX");
        Entity.I[0x4D ^ 0x5D] = I("\u0019\u0000!\u0014-\"\u0000;", "KoUuY");
        Entity.I[0x8D ^ 0x9C] = I("\u0004;\u0016\u000b\n+)\u000e\u0006 !?", "BZzgN");
        Entity.I[0x29 ^ 0x3B] = I("\u00101\u0018\u0004", "VXjap");
        Entity.I[0x31 ^ 0x22] = I("\u0004\"=", "EKOSD");
        Entity.I[0x3E ^ 0x2A] = I("\u00076.$'=6\r", "HXiVH");
        Entity.I[0x8C ^ 0x99] = I("\f\b\u001c<%;\b\u001e7", "HaqYK");
        Entity.I[0xAE ^ 0xB8] = I("=7=\f\u0005\u001a<9\u0018\u000b\u0018<", "tYKyi");
        Entity.I[0x72 ^ 0x65] = I("5\u0015\n\u001e\u0015\t9\u0017\u0005\u0018\u0001\u0015\u000f\u0004", "ezxjt");
        Entity.I[0x92 ^ 0x8A] = I("3#<\r$\t\u0005\u0001", "fvuIi");
        Entity.I[0x31 ^ 0x28] = I("#=\u0004\u000e>\u0013\t>>", "vhMJr");
        Entity.I[0x61 ^ 0x7B] = I("\u0002\u0010!-$,+34.", "AeRYK");
        Entity.I[0x8C ^ 0x97] = I("\u0015,=7(;\u0017/.\"\u00000=*%:<", "VYNCG");
        Entity.I[0x4A ^ 0x56] = I("4\u0019\b\u000b)\u0013", "gpdnG");
        Entity.I[0x2 ^ 0x1F] = I("9%%04\f", "kLAYZ");
        Entity.I[0x36 ^ 0x28] = I("\u001087*%$y$-?*-8c\u0005\u0001\r", "CYACK");
        Entity.I[0x38 ^ 0x27] = I("\u0015\u001e\u0000\"#)P\u0016.>>\u0017T86&\u0015\u0010", "PptKW");
        Entity.I[0x1D ^ 0x3D] = I("27\u0000", "bXsdT");
        Entity.I[0x68 ^ 0x49] = I("\u001b\u0017.+\u000e8", "VxZBa");
        Entity.I[0x57 ^ 0x75] = I("\u001f, \u0006\u001c$,:", "MCTgh");
        Entity.I[0x27 ^ 0x4] = I("<%\t#\u001d\u00137\u0011.7\u0019!", "zDeOY");
        Entity.I[0x9 ^ 0x2D] = I("\u0003-=\u0004", "EDOaN");
        Entity.I[0x61 ^ 0x44] = I("$\u0018\u001e", "eqlYJ");
        Entity.I[0x38 ^ 0x1E] = I("\u0018\u0019\u0003\u0016\u0004\"\u0019 ", "WwDdk");
        Entity.I[0xE6 ^ 0xC1] = I("&\u0004\u000e\u0016,\u0011\u0004\f\u001d", "bmcsB");
        Entity.I[0x20 ^ 0x8] = I("\u001f\u001f?\u0014\u000e8\u0014;\u0000\u0000:\u0014", "VqIab");
        Entity.I[0x3A ^ 0x13] = I("9\n\u00009+\u0005&\u001d\"&\r\n\u0005#", "ierMJ");
        Entity.I[0xBD ^ 0x97] = I("\u001c<\u0011\u0012:&\u001a,", "IiXVw");
        Entity.I[0x96 ^ 0xBD] = I("\u000f\u0017'\u0003<?#\u001d3", "ZBnGp");
        Entity.I[0x8F ^ 0xA3] = I("?/\r,\u0006\u0005\t0", "jzDhK");
        Entity.I[0x16 ^ 0x3B] = I("\u0006\u001f9%!6+\u0003\u0015", "SJpam");
        Entity.I[0x4 ^ 0x2A] = I("!\u001f:-", "tJsit");
        Entity.I[0x59 ^ 0x76] = I("\u0001\u0013\u0010-", "TFYiR");
        Entity.I[0xA ^ 0x3A] = I("\t\u0012\u001f\u001b=')\r\u00027", "JgloR");
        Entity.I[0x84 ^ 0xB5] = I("\u0012\"\u0015>$<\u0019\u0007'.", "QWfJK");
        Entity.I[0xF2 ^ 0xC0] = I("\u0001<9\u0002'/\u0007+\u001b-", "BIJvH");
        Entity.I[0x2A ^ 0x19] = I("\u0012\u0013\u0000?\u0006<(\u0012&\f\u0007\u000f\u0000\"\u000b=\u0003", "QfsKi");
        Entity.I[0x53 ^ 0x67] = I("1:'+\u0000\u0016", "bSKNn");
        Entity.I[0x8A ^ 0xBF] = I("\n\u00053*.(\rr+)2\u0003&7g\b(\u0006", "FjRNG");
        Entity.I[0x7A ^ 0x4C] = I("\u0012+\u001e%1.e\b),9\"J *6!\u000f(", "WEjLE");
        Entity.I[0x47 ^ 0x70] = I("\u000b.\b\b\u000b\u0005(", "lKfmy");
        Entity.I[0xBA ^ 0x82] = I("\u0017)9:\u0017\u000bi", "rGMSc");
        Entity.I[0xFE ^ 0xC7] = I("{)\u0017!\r", "UGvLh");
        Entity.I[0x39 ^ 0x3] = I("V04Rg\u0000d@P&_c\u0003HeV0HYb\u000b~J[p\u0015oO\f\u007fVm]\u0013nS9RPlA%2", "sCouB");
        Entity.I[0x6B ^ 0x50] = I("\u0013\u0000/?\u0000\u0013", "mNzsL");
        Entity.I[0x26 ^ 0x1A] = I("6-\u0017\"=0\u0001\u001f!?;6\u001f#4", "UEvLZ");
        Entity.I[0x30 ^ 0xD] = I("\".8\u0003\u00009?!\u0003\u001d", "PKHls");
        Entity.I[0x8E ^ 0xB0] = I("\u0018\n8\r\u0013\u000e\u0006:\u0005", "joTbr");
        Entity.I[0x29 ^ 0x16] = I("(\u00180::\u0014V\u0010*>\b", "mvDSN");
        Entity.I[0x6A ^ 0x2A] = I("\u000b\t<* 7G\u0001\u0007", "NgHCT");
        Entity.I[0x12 ^ 0x53] = I("2\u0001\u0019%\u0013\u000eO#-\n\u0012", "womLg");
        Entity.I[0xDE ^ 0x9C] = I("-\u000b5*\u0013\u0011B2c\"\u0010\u0004\"7G\u0004\n\"\"\u0013\u0001\n/", "heACg");
        Entity.I[0xD ^ 0x4E] = I("B{e\u000foGpy[%KurGq\u0001", "gUWiC");
        Entity.I[0xFA ^ 0xBE] = I("\b\u0016>\u001f\u00074_9V1!\u0017)\u001dS!\u0017)\u0017\u0007$\u0017$", "MxJvs");
        Entity.I[0x31 ^ 0x74] = I(",\u0002\u001b+5\u0010K\u001cb\f\u0006\u0001\n,5\u001c\u0001", "iloBA");
        Entity.I[0xC4 ^ 0x82] = I("`Wd)oe\\x}%iYsaq#", "EyVOC");
        Entity.I[0x3F ^ 0x78] = I("$=\"\f\r\u0018t%E+\b73\u0017", "aSVey");
        Entity.I[0x64 ^ 0x2C] = I("* 2\r&\u0016i5D\u0004\n&/\u0007>\n", "oNFdR");
        Entity.I[0x5F ^ 0x16] = I("1\u0010", "XtSPv");
        Entity.I[0x43 ^ 0x9] = I("\"-\u001c\u0007", "VTlbU");
        Entity.I[0xFC ^ 0xB7] = I(" *&5", "NKKPR");
    }
    
    public DataWatcher getDataWatcher() {
        return this.dataWatcher;
    }
    
    @Override
    public boolean equals(final Object o) {
        int n;
        if (o instanceof Entity) {
            if (((Entity)o).entityId == this.entityId) {
                n = " ".length();
                "".length();
                if (true != true) {
                    throw null;
                }
            }
            else {
                n = "".length();
                "".length();
                if (4 <= -1) {
                    throw null;
                }
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
}
