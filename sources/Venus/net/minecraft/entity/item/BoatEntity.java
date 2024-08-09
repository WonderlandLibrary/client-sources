/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LilyPadBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.Pose;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.WaterMobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.client.CSteerBoatPacket;
import net.minecraft.network.play.server.SSpawnObjectPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.TeleportationRepositioner;
import net.minecraft.util.TransportationHelper;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class BoatEntity
extends Entity {
    private static final DataParameter<Integer> TIME_SINCE_HIT = EntityDataManager.createKey(BoatEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> FORWARD_DIRECTION = EntityDataManager.createKey(BoatEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Float> DAMAGE_TAKEN = EntityDataManager.createKey(BoatEntity.class, DataSerializers.FLOAT);
    private static final DataParameter<Integer> BOAT_TYPE = EntityDataManager.createKey(BoatEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> LEFT_PADDLE = EntityDataManager.createKey(BoatEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> RIGHT_PADDLE = EntityDataManager.createKey(BoatEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> ROCKING_TICKS = EntityDataManager.createKey(BoatEntity.class, DataSerializers.VARINT);
    private final float[] paddlePositions = new float[2];
    private float momentum;
    private float outOfControlTicks;
    private float deltaRotation;
    private int lerpSteps;
    private double lerpX;
    private double lerpY;
    private double lerpZ;
    private double lerpYaw;
    private double lerpPitch;
    private boolean leftInputDown;
    private boolean rightInputDown;
    private boolean forwardInputDown;
    private boolean backInputDown;
    private double waterLevel;
    private float boatGlide;
    private Status status;
    private Status previousStatus;
    private double lastYd;
    private boolean rocking;
    private boolean downwards;
    private float rockingIntensity;
    private float rockingAngle;
    private float prevRockingAngle;

    public BoatEntity(EntityType<? extends BoatEntity> entityType, World world) {
        super(entityType, world);
        this.preventEntitySpawning = true;
    }

    public BoatEntity(World world, double d, double d2, double d3) {
        this((EntityType<? extends BoatEntity>)EntityType.BOAT, world);
        this.setPosition(d, d2, d3);
        this.setMotion(Vector3d.ZERO);
        this.prevPosX = d;
        this.prevPosY = d2;
        this.prevPosZ = d3;
    }

    @Override
    protected float getEyeHeight(Pose pose, EntitySize entitySize) {
        return entitySize.height;
    }

    @Override
    protected boolean canTriggerWalking() {
        return true;
    }

    @Override
    protected void registerData() {
        this.dataManager.register(TIME_SINCE_HIT, 0);
        this.dataManager.register(FORWARD_DIRECTION, 1);
        this.dataManager.register(DAMAGE_TAKEN, Float.valueOf(0.0f));
        this.dataManager.register(BOAT_TYPE, Type.OAK.ordinal());
        this.dataManager.register(LEFT_PADDLE, false);
        this.dataManager.register(RIGHT_PADDLE, false);
        this.dataManager.register(ROCKING_TICKS, 0);
    }

    @Override
    public boolean canCollide(Entity entity2) {
        return BoatEntity.func_242378_a(this, entity2);
    }

    public static boolean func_242378_a(Entity entity2, Entity entity3) {
        return (entity3.func_241845_aY() || entity3.canBePushed()) && !entity2.isRidingSameEntity(entity3);
    }

    @Override
    public boolean func_241845_aY() {
        return false;
    }

    @Override
    public boolean canBePushed() {
        return false;
    }

    @Override
    protected Vector3d func_241839_a(Direction.Axis axis, TeleportationRepositioner.Result result) {
        return LivingEntity.func_242288_h(super.func_241839_a(axis, result));
    }

    @Override
    public double getMountedYOffset() {
        return -0.1;
    }

    @Override
    public boolean attackEntityFrom(DamageSource damageSource, float f) {
        if (this.isInvulnerableTo(damageSource)) {
            return true;
        }
        if (!this.world.isRemote && !this.removed) {
            boolean bl;
            this.setForwardDirection(-this.getForwardDirection());
            this.setTimeSinceHit(10);
            this.setDamageTaken(this.getDamageTaken() + f * 10.0f);
            this.markVelocityChanged();
            boolean bl2 = bl = damageSource.getTrueSource() instanceof PlayerEntity && ((PlayerEntity)damageSource.getTrueSource()).abilities.isCreativeMode;
            if (bl || this.getDamageTaken() > 40.0f) {
                if (!bl && this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
                    this.entityDropItem(this.getItemBoat());
                }
                this.remove();
            }
            return false;
        }
        return false;
    }

    @Override
    public void onEnterBubbleColumnWithAirAbove(boolean bl) {
        if (!this.world.isRemote) {
            this.rocking = true;
            this.downwards = bl;
            if (this.getRockingTicks() == 0) {
                this.setRockingTicks(60);
            }
        }
        this.world.addParticle(ParticleTypes.SPLASH, this.getPosX() + (double)this.rand.nextFloat(), this.getPosY() + 0.7, this.getPosZ() + (double)this.rand.nextFloat(), 0.0, 0.0, 0.0);
        if (this.rand.nextInt(20) == 0) {
            this.world.playSound(this.getPosX(), this.getPosY(), this.getPosZ(), this.getSplashSound(), this.getSoundCategory(), 1.0f, 0.8f + 0.4f * this.rand.nextFloat(), true);
        }
    }

    @Override
    public void applyEntityCollision(Entity entity2) {
        if (entity2 instanceof BoatEntity) {
            if (entity2.getBoundingBox().minY < this.getBoundingBox().maxY) {
                super.applyEntityCollision(entity2);
            }
        } else if (entity2.getBoundingBox().minY <= this.getBoundingBox().minY) {
            super.applyEntityCollision(entity2);
        }
    }

    public Item getItemBoat() {
        switch (this.getBoatType()) {
            default: {
                return Items.OAK_BOAT;
            }
            case SPRUCE: {
                return Items.SPRUCE_BOAT;
            }
            case BIRCH: {
                return Items.BIRCH_BOAT;
            }
            case JUNGLE: {
                return Items.JUNGLE_BOAT;
            }
            case ACACIA: {
                return Items.ACACIA_BOAT;
            }
            case DARK_OAK: 
        }
        return Items.DARK_OAK_BOAT;
    }

    @Override
    public void performHurtAnimation() {
        this.setForwardDirection(-this.getForwardDirection());
        this.setTimeSinceHit(10);
        this.setDamageTaken(this.getDamageTaken() * 11.0f);
    }

    @Override
    public boolean canBeCollidedWith() {
        return !this.removed;
    }

    @Override
    public void setPositionAndRotationDirect(double d, double d2, double d3, float f, float f2, int n, boolean bl) {
        this.lerpX = d;
        this.lerpY = d2;
        this.lerpZ = d3;
        this.lerpYaw = f;
        this.lerpPitch = f2;
        this.lerpSteps = 10;
    }

    @Override
    public Direction getAdjustedHorizontalFacing() {
        return this.getHorizontalFacing().rotateY();
    }

    @Override
    public void tick() {
        this.previousStatus = this.status;
        this.status = this.getBoatStatus();
        this.outOfControlTicks = this.status != Status.UNDER_WATER && this.status != Status.UNDER_FLOWING_WATER ? 0.0f : (this.outOfControlTicks += 1.0f);
        if (!this.world.isRemote && this.outOfControlTicks >= 60.0f) {
            this.removePassengers();
        }
        if (this.getTimeSinceHit() > 0) {
            this.setTimeSinceHit(this.getTimeSinceHit() - 1);
        }
        if (this.getDamageTaken() > 0.0f) {
            this.setDamageTaken(this.getDamageTaken() - 1.0f);
        }
        super.tick();
        this.tickLerp();
        if (this.canPassengerSteer()) {
            if (this.getPassengers().isEmpty() || !(this.getPassengers().get(0) instanceof PlayerEntity)) {
                this.setPaddleState(false, true);
            }
            this.updateMotion();
            if (this.world.isRemote) {
                this.controlBoat();
                this.world.sendPacketToServer(new CSteerBoatPacket(this.getPaddleState(1), this.getPaddleState(0)));
            }
            this.move(MoverType.SELF, this.getMotion());
        } else {
            this.setMotion(Vector3d.ZERO);
        }
        this.updateRocking();
        for (int i = 0; i <= 1; ++i) {
            if (this.getPaddleState(i)) {
                SoundEvent soundEvent;
                if (!this.isSilent() && (double)(this.paddlePositions[i] % ((float)Math.PI * 2)) <= 0.7853981852531433 && ((double)this.paddlePositions[i] + (double)0.3926991f) % 6.2831854820251465 >= 0.7853981852531433 && (soundEvent = this.getPaddleSound()) != null) {
                    Vector3d vector3d = this.getLook(1.0f);
                    double d = i == 1 ? -vector3d.z : vector3d.z;
                    double d2 = i == 1 ? vector3d.x : -vector3d.x;
                    this.world.playSound(null, this.getPosX() + d, this.getPosY(), this.getPosZ() + d2, soundEvent, this.getSoundCategory(), 1.0f, 0.8f + 0.4f * this.rand.nextFloat());
                }
                this.paddlePositions[i] = (float)((double)this.paddlePositions[i] + (double)0.3926991f);
                continue;
            }
            this.paddlePositions[i] = 0.0f;
        }
        this.doBlockCollisions();
        List<Entity> list = this.world.getEntitiesInAABBexcluding(this, this.getBoundingBox().grow(0.2f, -0.01f, 0.2f), EntityPredicates.pushableBy(this));
        if (!list.isEmpty()) {
            boolean bl = !this.world.isRemote && !(this.getControllingPassenger() instanceof PlayerEntity);
            for (int i = 0; i < list.size(); ++i) {
                Entity entity2 = list.get(i);
                if (entity2.isPassenger(this)) continue;
                if (bl && this.getPassengers().size() < 2 && !entity2.isPassenger() && entity2.getWidth() < this.getWidth() && entity2 instanceof LivingEntity && !(entity2 instanceof WaterMobEntity) && !(entity2 instanceof PlayerEntity)) {
                    entity2.startRiding(this);
                    continue;
                }
                this.applyEntityCollision(entity2);
            }
        }
    }

    private void updateRocking() {
        if (this.world.isRemote) {
            int n = this.getRockingTicks();
            this.rockingIntensity = n > 0 ? (this.rockingIntensity += 0.05f) : (this.rockingIntensity -= 0.1f);
            this.rockingIntensity = MathHelper.clamp(this.rockingIntensity, 0.0f, 1.0f);
            this.prevRockingAngle = this.rockingAngle;
            this.rockingAngle = 10.0f * (float)Math.sin(0.5f * (float)this.world.getGameTime()) * this.rockingIntensity;
        } else {
            int n;
            if (!this.rocking) {
                this.setRockingTicks(0);
            }
            if ((n = this.getRockingTicks()) > 0) {
                this.setRockingTicks(--n);
                int n2 = 60 - n - 1;
                if (n2 > 0 && n == 0) {
                    this.setRockingTicks(0);
                    Vector3d vector3d = this.getMotion();
                    if (this.downwards) {
                        this.setMotion(vector3d.add(0.0, -0.7, 0.0));
                        this.removePassengers();
                    } else {
                        this.setMotion(vector3d.x, this.isPassenger(PlayerEntity.class) ? 2.7 : 0.6, vector3d.z);
                    }
                }
                this.rocking = false;
            }
        }
    }

    @Nullable
    protected SoundEvent getPaddleSound() {
        switch (this.getBoatStatus()) {
            case IN_WATER: 
            case UNDER_WATER: 
            case UNDER_FLOWING_WATER: {
                return SoundEvents.ENTITY_BOAT_PADDLE_WATER;
            }
            case ON_LAND: {
                return SoundEvents.ENTITY_BOAT_PADDLE_LAND;
            }
        }
        return null;
    }

    private void tickLerp() {
        if (this.canPassengerSteer()) {
            this.lerpSteps = 0;
            this.setPacketCoordinates(this.getPosX(), this.getPosY(), this.getPosZ());
        }
        if (this.lerpSteps > 0) {
            double d = this.getPosX() + (this.lerpX - this.getPosX()) / (double)this.lerpSteps;
            double d2 = this.getPosY() + (this.lerpY - this.getPosY()) / (double)this.lerpSteps;
            double d3 = this.getPosZ() + (this.lerpZ - this.getPosZ()) / (double)this.lerpSteps;
            double d4 = MathHelper.wrapDegrees(this.lerpYaw - (double)this.rotationYaw);
            this.rotationYaw = (float)((double)this.rotationYaw + d4 / (double)this.lerpSteps);
            this.rotationPitch = (float)((double)this.rotationPitch + (this.lerpPitch - (double)this.rotationPitch) / (double)this.lerpSteps);
            --this.lerpSteps;
            this.setPosition(d, d2, d3);
            this.setRotation(this.rotationYaw, this.rotationPitch);
        }
    }

    public void setPaddleState(boolean bl, boolean bl2) {
        this.dataManager.set(LEFT_PADDLE, bl);
        this.dataManager.set(RIGHT_PADDLE, bl2);
    }

    public float getRowingTime(int n, float f) {
        return this.getPaddleState(n) ? (float)MathHelper.clampedLerp((double)this.paddlePositions[n] - (double)0.3926991f, this.paddlePositions[n], f) : 0.0f;
    }

    private Status getBoatStatus() {
        Status status2 = this.getUnderwaterStatus();
        if (status2 != null) {
            this.waterLevel = this.getBoundingBox().maxY;
            return status2;
        }
        if (this.checkInWater()) {
            return Status.IN_WATER;
        }
        float f = this.getBoatGlide();
        if (f > 0.0f) {
            this.boatGlide = f;
            return Status.ON_LAND;
        }
        return Status.IN_AIR;
    }

    public float getWaterLevelAbove() {
        AxisAlignedBB axisAlignedBB = this.getBoundingBox();
        int n = MathHelper.floor(axisAlignedBB.minX);
        int n2 = MathHelper.ceil(axisAlignedBB.maxX);
        int n3 = MathHelper.floor(axisAlignedBB.maxY);
        int n4 = MathHelper.ceil(axisAlignedBB.maxY - this.lastYd);
        int n5 = MathHelper.floor(axisAlignedBB.minZ);
        int n6 = MathHelper.ceil(axisAlignedBB.maxZ);
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        block0: for (int i = n3; i < n4; ++i) {
            float f = 0.0f;
            for (int j = n; j < n2; ++j) {
                for (int k = n5; k < n6; ++k) {
                    mutable.setPos(j, i, k);
                    FluidState fluidState = this.world.getFluidState(mutable);
                    if (fluidState.isTagged(FluidTags.WATER)) {
                        f = Math.max(f, fluidState.getActualHeight(this.world, mutable));
                    }
                    if (f >= 1.0f) continue block0;
                }
            }
            if (!(f < 1.0f)) continue;
            return (float)mutable.getY() + f;
        }
        return n4 + 1;
    }

    public float getBoatGlide() {
        AxisAlignedBB axisAlignedBB = this.getBoundingBox();
        AxisAlignedBB axisAlignedBB2 = new AxisAlignedBB(axisAlignedBB.minX, axisAlignedBB.minY - 0.001, axisAlignedBB.minZ, axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        int n = MathHelper.floor(axisAlignedBB2.minX) - 1;
        int n2 = MathHelper.ceil(axisAlignedBB2.maxX) + 1;
        int n3 = MathHelper.floor(axisAlignedBB2.minY) - 1;
        int n4 = MathHelper.ceil(axisAlignedBB2.maxY) + 1;
        int n5 = MathHelper.floor(axisAlignedBB2.minZ) - 1;
        int n6 = MathHelper.ceil(axisAlignedBB2.maxZ) + 1;
        VoxelShape voxelShape = VoxelShapes.create(axisAlignedBB2);
        float f = 0.0f;
        int n7 = 0;
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (int i = n; i < n2; ++i) {
            for (int j = n5; j < n6; ++j) {
                int n8 = (i != n && i != n2 - 1 ? 0 : 1) + (j != n5 && j != n6 - 1 ? 0 : 1);
                if (n8 == 2) continue;
                for (int k = n3; k < n4; ++k) {
                    if (n8 > 0 && (k == n3 || k == n4 - 1)) continue;
                    mutable.setPos(i, k, j);
                    BlockState blockState = this.world.getBlockState(mutable);
                    if (blockState.getBlock() instanceof LilyPadBlock || !VoxelShapes.compare(blockState.getCollisionShape(this.world, mutable).withOffset(i, k, j), voxelShape, IBooleanFunction.AND)) continue;
                    f += blockState.getBlock().getSlipperiness();
                    ++n7;
                }
            }
        }
        return f / (float)n7;
    }

    private boolean checkInWater() {
        AxisAlignedBB axisAlignedBB = this.getBoundingBox();
        int n = MathHelper.floor(axisAlignedBB.minX);
        int n2 = MathHelper.ceil(axisAlignedBB.maxX);
        int n3 = MathHelper.floor(axisAlignedBB.minY);
        int n4 = MathHelper.ceil(axisAlignedBB.minY + 0.001);
        int n5 = MathHelper.floor(axisAlignedBB.minZ);
        int n6 = MathHelper.ceil(axisAlignedBB.maxZ);
        boolean bl = false;
        this.waterLevel = Double.MIN_VALUE;
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (int i = n; i < n2; ++i) {
            for (int j = n3; j < n4; ++j) {
                for (int k = n5; k < n6; ++k) {
                    mutable.setPos(i, j, k);
                    FluidState fluidState = this.world.getFluidState(mutable);
                    if (!fluidState.isTagged(FluidTags.WATER)) continue;
                    float f = (float)j + fluidState.getActualHeight(this.world, mutable);
                    this.waterLevel = Math.max((double)f, this.waterLevel);
                    bl |= axisAlignedBB.minY < (double)f;
                }
            }
        }
        return bl;
    }

    @Nullable
    private Status getUnderwaterStatus() {
        AxisAlignedBB axisAlignedBB = this.getBoundingBox();
        double d = axisAlignedBB.maxY + 0.001;
        int n = MathHelper.floor(axisAlignedBB.minX);
        int n2 = MathHelper.ceil(axisAlignedBB.maxX);
        int n3 = MathHelper.floor(axisAlignedBB.maxY);
        int n4 = MathHelper.ceil(d);
        int n5 = MathHelper.floor(axisAlignedBB.minZ);
        int n6 = MathHelper.ceil(axisAlignedBB.maxZ);
        boolean bl = false;
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (int i = n; i < n2; ++i) {
            for (int j = n3; j < n4; ++j) {
                for (int k = n5; k < n6; ++k) {
                    mutable.setPos(i, j, k);
                    FluidState fluidState = this.world.getFluidState(mutable);
                    if (!fluidState.isTagged(FluidTags.WATER) || !(d < (double)((float)mutable.getY() + fluidState.getActualHeight(this.world, mutable)))) continue;
                    if (!fluidState.isSource()) {
                        return Status.UNDER_FLOWING_WATER;
                    }
                    bl = true;
                }
            }
        }
        return bl ? Status.UNDER_WATER : null;
    }

    private void updateMotion() {
        double d = -0.04f;
        double d2 = this.hasNoGravity() ? 0.0 : (double)-0.04f;
        double d3 = 0.0;
        this.momentum = 0.05f;
        if (this.previousStatus == Status.IN_AIR && this.status != Status.IN_AIR && this.status != Status.ON_LAND) {
            this.waterLevel = this.getPosYHeight(1.0);
            this.setPosition(this.getPosX(), (double)(this.getWaterLevelAbove() - this.getHeight()) + 0.101, this.getPosZ());
            this.setMotion(this.getMotion().mul(1.0, 0.0, 1.0));
            this.lastYd = 0.0;
            this.status = Status.IN_WATER;
        } else {
            if (this.status == Status.IN_WATER) {
                d3 = (this.waterLevel - this.getPosY()) / (double)this.getHeight();
                this.momentum = 0.9f;
            } else if (this.status == Status.UNDER_FLOWING_WATER) {
                d2 = -7.0E-4;
                this.momentum = 0.9f;
            } else if (this.status == Status.UNDER_WATER) {
                d3 = 0.01f;
                this.momentum = 0.45f;
            } else if (this.status == Status.IN_AIR) {
                this.momentum = 0.9f;
            } else if (this.status == Status.ON_LAND) {
                this.momentum = this.boatGlide;
                if (this.getControllingPassenger() instanceof PlayerEntity) {
                    this.boatGlide /= 2.0f;
                }
            }
            Vector3d vector3d = this.getMotion();
            this.setMotion(vector3d.x * (double)this.momentum, vector3d.y + d2, vector3d.z * (double)this.momentum);
            this.deltaRotation *= this.momentum;
            if (d3 > 0.0) {
                Vector3d vector3d2 = this.getMotion();
                this.setMotion(vector3d2.x, (vector3d2.y + d3 * 0.06153846016296973) * 0.75, vector3d2.z);
            }
        }
    }

    private void controlBoat() {
        if (this.isBeingRidden()) {
            float f = 0.0f;
            if (this.leftInputDown) {
                this.deltaRotation -= 1.0f;
            }
            if (this.rightInputDown) {
                this.deltaRotation += 1.0f;
            }
            if (this.rightInputDown != this.leftInputDown && !this.forwardInputDown && !this.backInputDown) {
                f += 0.005f;
            }
            this.rotationYaw += this.deltaRotation;
            if (this.forwardInputDown) {
                f += 0.04f;
            }
            if (this.backInputDown) {
                f -= 0.005f;
            }
            Minecraft minecraft = Minecraft.getInstance();
            this.setMotion(this.getMotion().add(MathHelper.sin(-this.rotationYaw * ((float)Math.PI / 180)) * f, 0.0, MathHelper.cos(this.rotationYaw * ((float)Math.PI / 180)) * f));
            this.setPaddleState(this.rightInputDown && !this.leftInputDown || this.forwardInputDown, this.leftInputDown && !this.rightInputDown || this.forwardInputDown);
        }
    }

    @Override
    public void updatePassenger(Entity entity2) {
        if (this.isPassenger(entity2)) {
            float f = 0.0f;
            float f2 = (float)((this.removed ? (double)0.01f : this.getMountedYOffset()) + entity2.getYOffset());
            if (this.getPassengers().size() > 1) {
                int n = this.getPassengers().indexOf(entity2);
                f = n == 0 ? 0.2f : -0.6f;
                if (entity2 instanceof AnimalEntity) {
                    f = (float)((double)f + 0.2);
                }
            }
            Vector3d vector3d = new Vector3d(f, 0.0, 0.0).rotateYaw(-this.rotationYaw * ((float)Math.PI / 180) - 1.5707964f);
            entity2.setPosition(this.getPosX() + vector3d.x, this.getPosY() + (double)f2, this.getPosZ() + vector3d.z);
            entity2.rotationYaw += this.deltaRotation;
            entity2.setRotationYawHead(entity2.getRotationYawHead() + this.deltaRotation);
            this.applyYawToEntity(entity2);
            if (entity2 instanceof AnimalEntity && this.getPassengers().size() > 1) {
                int n = entity2.getEntityId() % 2 == 0 ? 90 : 270;
                entity2.setRenderYawOffset(((AnimalEntity)entity2).renderYawOffset + (float)n);
                entity2.setRotationYawHead(entity2.getRotationYawHead() + (float)n);
            }
        }
    }

    @Override
    public Vector3d func_230268_c_(LivingEntity livingEntity) {
        double d;
        Vector3d vector3d = BoatEntity.func_233559_a_(this.getWidth() * MathHelper.SQRT_2, livingEntity.getWidth(), this.rotationYaw);
        double d2 = this.getPosX() + vector3d.x;
        BlockPos blockPos = new BlockPos(d2, this.getBoundingBox().maxY, d = this.getPosZ() + vector3d.z);
        BlockPos blockPos2 = blockPos.down();
        if (!this.world.hasWater(blockPos2)) {
            double d3 = (double)blockPos.getY() + this.world.func_242403_h(blockPos);
            double d4 = (double)blockPos.getY() + this.world.func_242403_h(blockPos2);
            for (Pose pose : livingEntity.getAvailablePoses()) {
                Vector3d vector3d2 = TransportationHelper.func_242381_a(this.world, d2, d3, d, livingEntity, pose);
                if (vector3d2 != null) {
                    livingEntity.setPose(pose);
                    return vector3d2;
                }
                Vector3d vector3d3 = TransportationHelper.func_242381_a(this.world, d2, d4, d, livingEntity, pose);
                if (vector3d3 == null) continue;
                livingEntity.setPose(pose);
                return vector3d3;
            }
        }
        return super.func_230268_c_(livingEntity);
    }

    protected void applyYawToEntity(Entity entity2) {
        entity2.setRenderYawOffset(this.rotationYaw);
        float f = MathHelper.wrapDegrees(entity2.rotationYaw - this.rotationYaw);
        float f2 = MathHelper.clamp(f, -105.0f, 105.0f);
        entity2.prevRotationYaw += f2 - f;
        entity2.rotationYaw += f2 - f;
        entity2.setRotationYawHead(entity2.rotationYaw);
    }

    @Override
    public void applyOrientationToEntity(Entity entity2) {
        this.applyYawToEntity(entity2);
    }

    @Override
    protected void writeAdditional(CompoundNBT compoundNBT) {
        compoundNBT.putString("Type", this.getBoatType().getName());
    }

    @Override
    protected void readAdditional(CompoundNBT compoundNBT) {
        if (compoundNBT.contains("Type", 1)) {
            this.setBoatType(Type.getTypeFromString(compoundNBT.getString("Type")));
        }
    }

    @Override
    public ActionResultType processInitialInteract(PlayerEntity playerEntity, Hand hand) {
        if (playerEntity.isSecondaryUseActive()) {
            return ActionResultType.PASS;
        }
        if (this.outOfControlTicks < 60.0f) {
            if (!this.world.isRemote) {
                return playerEntity.startRiding(this) ? ActionResultType.CONSUME : ActionResultType.PASS;
            }
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.PASS;
    }

    @Override
    protected void updateFallState(double d, boolean bl, BlockState blockState, BlockPos blockPos) {
        this.lastYd = this.getMotion().y;
        if (!this.isPassenger()) {
            if (bl) {
                if (this.fallDistance > 3.0f) {
                    if (this.status != Status.ON_LAND) {
                        this.fallDistance = 0.0f;
                        return;
                    }
                    this.onLivingFall(this.fallDistance, 1.0f);
                    if (!this.world.isRemote && !this.removed) {
                        this.remove();
                        if (this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
                            int n;
                            for (n = 0; n < 3; ++n) {
                                this.entityDropItem(this.getBoatType().asPlank());
                            }
                            for (n = 0; n < 2; ++n) {
                                this.entityDropItem(Items.STICK);
                            }
                        }
                    }
                }
                this.fallDistance = 0.0f;
            } else if (!this.world.getFluidState(this.getPosition().down()).isTagged(FluidTags.WATER) && d < 0.0) {
                this.fallDistance = (float)((double)this.fallDistance - d);
            }
        }
    }

    public boolean getPaddleState(int n) {
        return this.dataManager.get(n == 0 ? LEFT_PADDLE : RIGHT_PADDLE) != false && this.getControllingPassenger() != null;
    }

    public void setDamageTaken(float f) {
        this.dataManager.set(DAMAGE_TAKEN, Float.valueOf(f));
    }

    public float getDamageTaken() {
        return this.dataManager.get(DAMAGE_TAKEN).floatValue();
    }

    public void setTimeSinceHit(int n) {
        this.dataManager.set(TIME_SINCE_HIT, n);
    }

    public int getTimeSinceHit() {
        return this.dataManager.get(TIME_SINCE_HIT);
    }

    private void setRockingTicks(int n) {
        this.dataManager.set(ROCKING_TICKS, n);
    }

    private int getRockingTicks() {
        return this.dataManager.get(ROCKING_TICKS);
    }

    public float getRockingAngle(float f) {
        return MathHelper.lerp(f, this.prevRockingAngle, this.rockingAngle);
    }

    public void setForwardDirection(int n) {
        this.dataManager.set(FORWARD_DIRECTION, n);
    }

    public int getForwardDirection() {
        return this.dataManager.get(FORWARD_DIRECTION);
    }

    public void setBoatType(Type type) {
        this.dataManager.set(BOAT_TYPE, type.ordinal());
    }

    public Type getBoatType() {
        return Type.byId(this.dataManager.get(BOAT_TYPE));
    }

    @Override
    protected boolean canFitPassenger(Entity entity2) {
        return this.getPassengers().size() < 2 && !this.areEyesInFluid(FluidTags.WATER);
    }

    @Override
    @Nullable
    public Entity getControllingPassenger() {
        List<Entity> list = this.getPassengers();
        return list.isEmpty() ? null : list.get(0);
    }

    public void updateInputs(boolean bl, boolean bl2, boolean bl3, boolean bl4) {
        this.leftInputDown = bl;
        this.rightInputDown = bl2;
        this.forwardInputDown = bl3;
        this.backInputDown = bl4;
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return new SSpawnObjectPacket(this);
    }

    @Override
    public boolean canSwim() {
        return this.status == Status.UNDER_WATER || this.status == Status.UNDER_FLOWING_WATER;
    }

    public static enum Type {
        OAK(Blocks.OAK_PLANKS, "oak"),
        SPRUCE(Blocks.SPRUCE_PLANKS, "spruce"),
        BIRCH(Blocks.BIRCH_PLANKS, "birch"),
        JUNGLE(Blocks.JUNGLE_PLANKS, "jungle"),
        ACACIA(Blocks.ACACIA_PLANKS, "acacia"),
        DARK_OAK(Blocks.DARK_OAK_PLANKS, "dark_oak");

        private final String name;
        private final Block block;

        private Type(Block block, String string2) {
            this.name = string2;
            this.block = block;
        }

        public String getName() {
            return this.name;
        }

        public Block asPlank() {
            return this.block;
        }

        public String toString() {
            return this.name;
        }

        public static Type byId(int n) {
            Type[] typeArray = Type.values();
            if (n < 0 || n >= typeArray.length) {
                n = 0;
            }
            return typeArray[n];
        }

        public static Type getTypeFromString(String string) {
            Type[] typeArray = Type.values();
            for (int i = 0; i < typeArray.length; ++i) {
                if (!typeArray[i].getName().equals(string)) continue;
                return typeArray[i];
            }
            return typeArray[0];
        }
    }

    public static enum Status {
        IN_WATER,
        UNDER_WATER,
        UNDER_FLOWING_WATER,
        ON_LAND,
        IN_AIR;

    }
}

