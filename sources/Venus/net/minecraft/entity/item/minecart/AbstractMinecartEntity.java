/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.item.minecart;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractRailBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PoweredRailBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.Pose;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.entity.item.minecart.ChestMinecartEntity;
import net.minecraft.entity.item.minecart.CommandBlockMinecartEntity;
import net.minecraft.entity.item.minecart.FurnaceMinecartEntity;
import net.minecraft.entity.item.minecart.HopperMinecartEntity;
import net.minecraft.entity.item.minecart.MinecartEntity;
import net.minecraft.entity.item.minecart.SpawnerMinecartEntity;
import net.minecraft.entity.item.minecart.TNTMinecartEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SSpawnObjectPacket;
import net.minecraft.state.properties.RailShape;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.TeleportationRepositioner;
import net.minecraft.util.TransportationHelper;
import net.minecraft.util.Util;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public abstract class AbstractMinecartEntity
extends Entity {
    private static final DataParameter<Integer> ROLLING_AMPLITUDE = EntityDataManager.createKey(AbstractMinecartEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> ROLLING_DIRECTION = EntityDataManager.createKey(AbstractMinecartEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Float> DAMAGE = EntityDataManager.createKey(AbstractMinecartEntity.class, DataSerializers.FLOAT);
    private static final DataParameter<Integer> DISPLAY_TILE = EntityDataManager.createKey(AbstractMinecartEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> DISPLAY_TILE_OFFSET = EntityDataManager.createKey(AbstractMinecartEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> SHOW_BLOCK = EntityDataManager.createKey(AbstractMinecartEntity.class, DataSerializers.BOOLEAN);
    private static final ImmutableMap<Pose, ImmutableList<Integer>> field_234627_am_ = ImmutableMap.of(Pose.STANDING, ImmutableList.of(0, 1, -1), Pose.CROUCHING, ImmutableList.of(0, 1, -1), Pose.SWIMMING, ImmutableList.of(0, 1));
    private boolean isInReverse;
    private static final Map<RailShape, Pair<Vector3i, Vector3i>> MATRIX = Util.make(Maps.newEnumMap(RailShape.class), AbstractMinecartEntity::lambda$static$0);
    private int turnProgress;
    private double minecartX;
    private double minecartY;
    private double minecartZ;
    private double minecartYaw;
    private double minecartPitch;
    private double velocityX;
    private double velocityY;
    private double velocityZ;

    protected AbstractMinecartEntity(EntityType<?> entityType, World world) {
        super(entityType, world);
        this.preventEntitySpawning = true;
    }

    protected AbstractMinecartEntity(EntityType<?> entityType, World world, double d, double d2, double d3) {
        this(entityType, world);
        this.setPosition(d, d2, d3);
        this.setMotion(Vector3d.ZERO);
        this.prevPosX = d;
        this.prevPosY = d2;
        this.prevPosZ = d3;
    }

    public static AbstractMinecartEntity create(World world, double d, double d2, double d3, Type type) {
        if (type == Type.CHEST) {
            return new ChestMinecartEntity(world, d, d2, d3);
        }
        if (type == Type.FURNACE) {
            return new FurnaceMinecartEntity(world, d, d2, d3);
        }
        if (type == Type.TNT) {
            return new TNTMinecartEntity(world, d, d2, d3);
        }
        if (type == Type.SPAWNER) {
            return new SpawnerMinecartEntity(world, d, d2, d3);
        }
        if (type == Type.HOPPER) {
            return new HopperMinecartEntity(world, d, d2, d3);
        }
        return type == Type.COMMAND_BLOCK ? new CommandBlockMinecartEntity(world, d, d2, d3) : new MinecartEntity(world, d, d2, d3);
    }

    @Override
    protected boolean canTriggerWalking() {
        return true;
    }

    @Override
    protected void registerData() {
        this.dataManager.register(ROLLING_AMPLITUDE, 0);
        this.dataManager.register(ROLLING_DIRECTION, 1);
        this.dataManager.register(DAMAGE, Float.valueOf(0.0f));
        this.dataManager.register(DISPLAY_TILE, Block.getStateId(Blocks.AIR.getDefaultState()));
        this.dataManager.register(DISPLAY_TILE_OFFSET, 6);
        this.dataManager.register(SHOW_BLOCK, false);
    }

    @Override
    public boolean canCollide(Entity entity2) {
        return BoatEntity.func_242378_a(this, entity2);
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
        return 0.0;
    }

    @Override
    public Vector3d func_230268_c_(LivingEntity livingEntity) {
        Direction direction = this.getAdjustedHorizontalFacing();
        if (direction.getAxis() == Direction.Axis.Y) {
            return super.func_230268_c_(livingEntity);
        }
        int[][] nArray = TransportationHelper.func_234632_a_(direction);
        BlockPos blockPos = this.getPosition();
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        ImmutableList<Pose> immutableList = livingEntity.getAvailablePoses();
        for (Pose pose : immutableList) {
            EntitySize entitySize = livingEntity.getSize(pose);
            float f = Math.min(entitySize.width, 1.0f) / 2.0f;
            Iterator iterator2 = field_234627_am_.get((Object)pose).iterator();
            while (iterator2.hasNext()) {
                int n = (Integer)iterator2.next();
                for (int[] nArray2 : nArray) {
                    Vector3d vector3d;
                    AxisAlignedBB axisAlignedBB;
                    mutable.setPos(blockPos.getX() + nArray2[0], blockPos.getY() + n, blockPos.getZ() + nArray2[1]);
                    double d = this.world.func_242402_a(TransportationHelper.func_242380_a(this.world, mutable), () -> this.lambda$func_230268_c_$1(mutable));
                    if (!TransportationHelper.func_234630_a_(d) || !TransportationHelper.func_234631_a_(this.world, livingEntity, (axisAlignedBB = new AxisAlignedBB(-f, 0.0, -f, f, entitySize.height, f)).offset(vector3d = Vector3d.copyCenteredWithVerticalOffset(mutable, d)))) continue;
                    livingEntity.setPose(pose);
                    return vector3d;
                }
            }
        }
        double d = this.getBoundingBox().maxY;
        mutable.setPos((double)blockPos.getX(), d, (double)blockPos.getZ());
        for (Pose pose : immutableList) {
            double d2 = livingEntity.getSize((Pose)pose).height;
            int n = MathHelper.ceil(d - (double)mutable.getY() + d2);
            double d3 = TransportationHelper.func_242383_a(mutable, n, this::lambda$func_230268_c_$2);
            if (!(d + d2 <= d3)) continue;
            livingEntity.setPose(pose);
            break;
        }
        return super.func_230268_c_(livingEntity);
    }

    @Override
    public boolean attackEntityFrom(DamageSource damageSource, float f) {
        if (!this.world.isRemote && !this.removed) {
            boolean bl;
            if (this.isInvulnerableTo(damageSource)) {
                return true;
            }
            this.setRollingDirection(-this.getRollingDirection());
            this.setRollingAmplitude(10);
            this.markVelocityChanged();
            this.setDamage(this.getDamage() + f * 10.0f);
            boolean bl2 = bl = damageSource.getTrueSource() instanceof PlayerEntity && ((PlayerEntity)damageSource.getTrueSource()).abilities.isCreativeMode;
            if (bl || this.getDamage() > 40.0f) {
                this.removePassengers();
                if (bl && !this.hasCustomName()) {
                    this.remove();
                } else {
                    this.killMinecart(damageSource);
                }
            }
            return false;
        }
        return false;
    }

    @Override
    protected float getSpeedFactor() {
        BlockState blockState = this.world.getBlockState(this.getPosition());
        return blockState.isIn(BlockTags.RAILS) ? 1.0f : super.getSpeedFactor();
    }

    public void killMinecart(DamageSource damageSource) {
        this.remove();
        if (this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
            ItemStack itemStack = new ItemStack(Items.MINECART);
            if (this.hasCustomName()) {
                itemStack.setDisplayName(this.getCustomName());
            }
            this.entityDropItem(itemStack);
        }
    }

    @Override
    public void performHurtAnimation() {
        this.setRollingDirection(-this.getRollingDirection());
        this.setRollingAmplitude(10);
        this.setDamage(this.getDamage() + this.getDamage() * 10.0f);
    }

    @Override
    public boolean canBeCollidedWith() {
        return !this.removed;
    }

    private static Pair<Vector3i, Vector3i> getMovementMatrixForShape(RailShape railShape) {
        return MATRIX.get(railShape);
    }

    @Override
    public Direction getAdjustedHorizontalFacing() {
        return this.isInReverse ? this.getHorizontalFacing().getOpposite().rotateY() : this.getHorizontalFacing().rotateY();
    }

    @Override
    public void tick() {
        if (this.getRollingAmplitude() > 0) {
            this.setRollingAmplitude(this.getRollingAmplitude() - 1);
        }
        if (this.getDamage() > 0.0f) {
            this.setDamage(this.getDamage() - 1.0f);
        }
        if (this.getPosY() < -64.0) {
            this.outOfWorld();
        }
        this.updatePortal();
        if (this.world.isRemote) {
            if (this.turnProgress > 0) {
                double d = this.getPosX() + (this.minecartX - this.getPosX()) / (double)this.turnProgress;
                double d2 = this.getPosY() + (this.minecartY - this.getPosY()) / (double)this.turnProgress;
                double d3 = this.getPosZ() + (this.minecartZ - this.getPosZ()) / (double)this.turnProgress;
                double d4 = MathHelper.wrapDegrees(this.minecartYaw - (double)this.rotationYaw);
                this.rotationYaw = (float)((double)this.rotationYaw + d4 / (double)this.turnProgress);
                this.rotationPitch = (float)((double)this.rotationPitch + (this.minecartPitch - (double)this.rotationPitch) / (double)this.turnProgress);
                --this.turnProgress;
                this.setPosition(d, d2, d3);
                this.setRotation(this.rotationYaw, this.rotationPitch);
            } else {
                this.recenterBoundingBox();
                this.setRotation(this.rotationYaw, this.rotationPitch);
            }
        } else {
            double d;
            BlockPos blockPos;
            BlockState blockState;
            int n;
            int n2;
            int n3;
            if (!this.hasNoGravity()) {
                this.setMotion(this.getMotion().add(0.0, -0.04, 0.0));
            }
            if (this.world.getBlockState(new BlockPos(n3 = MathHelper.floor(this.getPosX()), (n2 = MathHelper.floor(this.getPosY())) - 1, n = MathHelper.floor(this.getPosZ()))).isIn(BlockTags.RAILS)) {
                --n2;
            }
            if (AbstractRailBlock.isRail(blockState = this.world.getBlockState(blockPos = new BlockPos(n3, n2, n)))) {
                this.moveAlongTrack(blockPos, blockState);
                if (blockState.isIn(Blocks.ACTIVATOR_RAIL)) {
                    this.onActivatorRailPass(n3, n2, n, blockState.get(PoweredRailBlock.POWERED));
                }
            } else {
                this.moveDerailedMinecart();
            }
            this.doBlockCollisions();
            this.rotationPitch = 0.0f;
            double d5 = this.prevPosX - this.getPosX();
            double d6 = this.prevPosZ - this.getPosZ();
            if (d5 * d5 + d6 * d6 > 0.001) {
                this.rotationYaw = (float)(MathHelper.atan2(d6, d5) * 180.0 / Math.PI);
                if (this.isInReverse) {
                    this.rotationYaw += 180.0f;
                }
            }
            if ((d = (double)MathHelper.wrapDegrees(this.rotationYaw - this.prevRotationYaw)) < -170.0 || d >= 170.0) {
                this.rotationYaw += 180.0f;
                this.isInReverse = !this.isInReverse;
            }
            this.setRotation(this.rotationYaw, this.rotationPitch);
            if (this.getMinecartType() == Type.RIDEABLE && AbstractMinecartEntity.horizontalMag(this.getMotion()) > 0.01) {
                List<Entity> list = this.world.getEntitiesInAABBexcluding(this, this.getBoundingBox().grow(0.2f, 0.0, 0.2f), EntityPredicates.pushableBy(this));
                if (!list.isEmpty()) {
                    for (int i = 0; i < list.size(); ++i) {
                        Entity entity2 = list.get(i);
                        if (!(entity2 instanceof PlayerEntity || entity2 instanceof IronGolemEntity || entity2 instanceof AbstractMinecartEntity || this.isBeingRidden() || entity2.isPassenger())) {
                            entity2.startRiding(this);
                            continue;
                        }
                        entity2.applyEntityCollision(this);
                    }
                }
            } else {
                for (Entity entity3 : this.world.getEntitiesWithinAABBExcludingEntity(this, this.getBoundingBox().grow(0.2f, 0.0, 0.2f))) {
                    if (this.isPassenger(entity3) || !entity3.canBePushed() || !(entity3 instanceof AbstractMinecartEntity)) continue;
                    entity3.applyEntityCollision(this);
                }
            }
            this.func_233566_aG_();
            if (this.isInLava()) {
                this.setOnFireFromLava();
                this.fallDistance *= 0.5f;
            }
            this.firstUpdate = false;
        }
    }

    protected double getMaximumSpeed() {
        return 0.4;
    }

    public void onActivatorRailPass(int n, int n2, int n3, boolean bl) {
    }

    protected void moveDerailedMinecart() {
        double d = this.getMaximumSpeed();
        Vector3d vector3d = this.getMotion();
        this.setMotion(MathHelper.clamp(vector3d.x, -d, d), vector3d.y, MathHelper.clamp(vector3d.z, -d, d));
        if (this.onGround) {
            this.setMotion(this.getMotion().scale(0.5));
        }
        this.move(MoverType.SELF, this.getMotion());
        if (!this.onGround) {
            this.setMotion(this.getMotion().scale(0.95));
        }
    }

    protected void moveAlongTrack(BlockPos blockPos, BlockState blockState) {
        double d;
        Vector3d vector3d;
        double d2;
        double d3;
        double d4;
        Entity entity2;
        this.fallDistance = 0.0f;
        double d5 = this.getPosX();
        double d6 = this.getPosY();
        double d7 = this.getPosZ();
        Vector3d vector3d2 = this.getPos(d5, d6, d7);
        d6 = blockPos.getY();
        boolean bl = false;
        boolean bl2 = false;
        AbstractRailBlock abstractRailBlock = (AbstractRailBlock)blockState.getBlock();
        if (abstractRailBlock == Blocks.POWERED_RAIL) {
            bl = blockState.get(PoweredRailBlock.POWERED);
            bl2 = !bl;
        }
        double d8 = 0.0078125;
        Vector3d vector3d3 = this.getMotion();
        RailShape railShape = blockState.get(abstractRailBlock.getShapeProperty());
        switch (1.$SwitchMap$net$minecraft$state$properties$RailShape[railShape.ordinal()]) {
            case 1: {
                this.setMotion(vector3d3.add(-0.0078125, 0.0, 0.0));
                d6 += 1.0;
                break;
            }
            case 2: {
                this.setMotion(vector3d3.add(0.0078125, 0.0, 0.0));
                d6 += 1.0;
                break;
            }
            case 3: {
                this.setMotion(vector3d3.add(0.0, 0.0, 0.0078125));
                d6 += 1.0;
                break;
            }
            case 4: {
                this.setMotion(vector3d3.add(0.0, 0.0, -0.0078125));
                d6 += 1.0;
            }
        }
        vector3d3 = this.getMotion();
        Pair<Vector3i, Vector3i> pair = AbstractMinecartEntity.getMovementMatrixForShape(railShape);
        Vector3i vector3i = pair.getFirst();
        Vector3i vector3i2 = pair.getSecond();
        double d9 = vector3i2.getX() - vector3i.getX();
        double d10 = vector3i2.getZ() - vector3i.getZ();
        double d11 = Math.sqrt(d9 * d9 + d10 * d10);
        double d12 = vector3d3.x * d9 + vector3d3.z * d10;
        if (d12 < 0.0) {
            d9 = -d9;
            d10 = -d10;
        }
        double d13 = Math.min(2.0, Math.sqrt(AbstractMinecartEntity.horizontalMag(vector3d3)));
        vector3d3 = new Vector3d(d13 * d9 / d11, vector3d3.y, d13 * d10 / d11);
        this.setMotion(vector3d3);
        Entity entity3 = entity2 = this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
        if (entity2 instanceof PlayerEntity) {
            Vector3d vector3d4 = entity2.getMotion();
            double d14 = AbstractMinecartEntity.horizontalMag(vector3d4);
            double d15 = AbstractMinecartEntity.horizontalMag(this.getMotion());
            if (d14 > 1.0E-4 && d15 < 0.01) {
                this.setMotion(this.getMotion().add(vector3d4.x * 0.1, 0.0, vector3d4.z * 0.1));
                bl2 = false;
            }
        }
        if (bl2) {
            double d16 = Math.sqrt(AbstractMinecartEntity.horizontalMag(this.getMotion()));
            if (d16 < 0.03) {
                this.setMotion(Vector3d.ZERO);
            } else {
                this.setMotion(this.getMotion().mul(0.5, 0.0, 0.5));
            }
        }
        double d17 = (double)blockPos.getX() + 0.5 + (double)vector3i.getX() * 0.5;
        double d18 = (double)blockPos.getZ() + 0.5 + (double)vector3i.getZ() * 0.5;
        double d19 = (double)blockPos.getX() + 0.5 + (double)vector3i2.getX() * 0.5;
        double d20 = (double)blockPos.getZ() + 0.5 + (double)vector3i2.getZ() * 0.5;
        d9 = d19 - d17;
        d10 = d20 - d18;
        if (d9 == 0.0) {
            d4 = d7 - (double)blockPos.getZ();
        } else if (d10 == 0.0) {
            d4 = d5 - (double)blockPos.getX();
        } else {
            d3 = d5 - d17;
            d2 = d7 - d18;
            d4 = (d3 * d9 + d2 * d10) * 2.0;
        }
        d5 = d17 + d9 * d4;
        d7 = d18 + d10 * d4;
        this.setPosition(d5, d6, d7);
        d3 = this.isBeingRidden() ? 0.75 : 1.0;
        d2 = this.getMaximumSpeed();
        vector3d3 = this.getMotion();
        this.move(MoverType.SELF, new Vector3d(MathHelper.clamp(d3 * vector3d3.x, -d2, d2), 0.0, MathHelper.clamp(d3 * vector3d3.z, -d2, d2)));
        if (vector3i.getY() != 0 && MathHelper.floor(this.getPosX()) - blockPos.getX() == vector3i.getX() && MathHelper.floor(this.getPosZ()) - blockPos.getZ() == vector3i.getZ()) {
            this.setPosition(this.getPosX(), this.getPosY() + (double)vector3i.getY(), this.getPosZ());
        } else if (vector3i2.getY() != 0 && MathHelper.floor(this.getPosX()) - blockPos.getX() == vector3i2.getX() && MathHelper.floor(this.getPosZ()) - blockPos.getZ() == vector3i2.getZ()) {
            this.setPosition(this.getPosX(), this.getPosY() + (double)vector3i2.getY(), this.getPosZ());
        }
        this.applyDrag();
        Vector3d vector3d5 = this.getPos(this.getPosX(), this.getPosY(), this.getPosZ());
        if (vector3d5 != null && vector3d2 != null) {
            double d21 = (vector3d2.y - vector3d5.y) * 0.05;
            vector3d = this.getMotion();
            d = Math.sqrt(AbstractMinecartEntity.horizontalMag(vector3d));
            if (d > 0.0) {
                this.setMotion(vector3d.mul((d + d21) / d, 1.0, (d + d21) / d));
            }
            this.setPosition(this.getPosX(), vector3d5.y, this.getPosZ());
        }
        int n = MathHelper.floor(this.getPosX());
        int n2 = MathHelper.floor(this.getPosZ());
        if (n != blockPos.getX() || n2 != blockPos.getZ()) {
            vector3d = this.getMotion();
            d = Math.sqrt(AbstractMinecartEntity.horizontalMag(vector3d));
            this.setMotion(d * (double)(n - blockPos.getX()), vector3d.y, d * (double)(n2 - blockPos.getZ()));
        }
        if (bl) {
            vector3d = this.getMotion();
            d = Math.sqrt(AbstractMinecartEntity.horizontalMag(vector3d));
            if (d > 0.01) {
                double d22 = 0.06;
                this.setMotion(vector3d.add(vector3d.x / d * 0.06, 0.0, vector3d.z / d * 0.06));
            } else {
                Vector3d vector3d6 = this.getMotion();
                double d23 = vector3d6.x;
                double d24 = vector3d6.z;
                if (railShape == RailShape.EAST_WEST) {
                    if (this.isNormalCube(blockPos.west())) {
                        d23 = 0.02;
                    } else if (this.isNormalCube(blockPos.east())) {
                        d23 = -0.02;
                    }
                } else {
                    if (railShape != RailShape.NORTH_SOUTH) {
                        return;
                    }
                    if (this.isNormalCube(blockPos.north())) {
                        d24 = 0.02;
                    } else if (this.isNormalCube(blockPos.south())) {
                        d24 = -0.02;
                    }
                }
                this.setMotion(d23, vector3d6.y, d24);
            }
        }
    }

    private boolean isNormalCube(BlockPos blockPos) {
        return this.world.getBlockState(blockPos).isNormalCube(this.world, blockPos);
    }

    protected void applyDrag() {
        double d = this.isBeingRidden() ? 0.997 : 0.96;
        this.setMotion(this.getMotion().mul(d, 0.0, d));
    }

    @Nullable
    public Vector3d getPosOffset(double d, double d2, double d3, double d4) {
        BlockState blockState;
        int n;
        int n2;
        int n3 = MathHelper.floor(d);
        if (this.world.getBlockState(new BlockPos(n3, (n2 = MathHelper.floor(d2)) - 1, n = MathHelper.floor(d3))).isIn(BlockTags.RAILS)) {
            --n2;
        }
        if (AbstractRailBlock.isRail(blockState = this.world.getBlockState(new BlockPos(n3, n2, n)))) {
            RailShape railShape = blockState.get(((AbstractRailBlock)blockState.getBlock()).getShapeProperty());
            d2 = n2;
            if (railShape.isAscending()) {
                d2 = n2 + 1;
            }
            Pair<Vector3i, Vector3i> pair = AbstractMinecartEntity.getMovementMatrixForShape(railShape);
            Vector3i vector3i = pair.getFirst();
            Vector3i vector3i2 = pair.getSecond();
            double d5 = vector3i2.getX() - vector3i.getX();
            double d6 = vector3i2.getZ() - vector3i.getZ();
            double d7 = Math.sqrt(d5 * d5 + d6 * d6);
            if (vector3i.getY() != 0 && MathHelper.floor(d += (d5 /= d7) * d4) - n3 == vector3i.getX() && MathHelper.floor(d3 += (d6 /= d7) * d4) - n == vector3i.getZ()) {
                d2 += (double)vector3i.getY();
            } else if (vector3i2.getY() != 0 && MathHelper.floor(d) - n3 == vector3i2.getX() && MathHelper.floor(d3) - n == vector3i2.getZ()) {
                d2 += (double)vector3i2.getY();
            }
            return this.getPos(d, d2, d3);
        }
        return null;
    }

    @Nullable
    public Vector3d getPos(double d, double d2, double d3) {
        BlockState blockState;
        int n;
        int n2;
        int n3 = MathHelper.floor(d);
        if (this.world.getBlockState(new BlockPos(n3, (n2 = MathHelper.floor(d2)) - 1, n = MathHelper.floor(d3))).isIn(BlockTags.RAILS)) {
            --n2;
        }
        if (AbstractRailBlock.isRail(blockState = this.world.getBlockState(new BlockPos(n3, n2, n)))) {
            double d4;
            RailShape railShape = blockState.get(((AbstractRailBlock)blockState.getBlock()).getShapeProperty());
            Pair<Vector3i, Vector3i> pair = AbstractMinecartEntity.getMovementMatrixForShape(railShape);
            Vector3i vector3i = pair.getFirst();
            Vector3i vector3i2 = pair.getSecond();
            double d5 = (double)n3 + 0.5 + (double)vector3i.getX() * 0.5;
            double d6 = (double)n2 + 0.0625 + (double)vector3i.getY() * 0.5;
            double d7 = (double)n + 0.5 + (double)vector3i.getZ() * 0.5;
            double d8 = (double)n3 + 0.5 + (double)vector3i2.getX() * 0.5;
            double d9 = (double)n2 + 0.0625 + (double)vector3i2.getY() * 0.5;
            double d10 = (double)n + 0.5 + (double)vector3i2.getZ() * 0.5;
            double d11 = d8 - d5;
            double d12 = (d9 - d6) * 2.0;
            double d13 = d10 - d7;
            if (d11 == 0.0) {
                d4 = d3 - (double)n;
            } else if (d13 == 0.0) {
                d4 = d - (double)n3;
            } else {
                double d14 = d - d5;
                double d15 = d3 - d7;
                d4 = (d14 * d11 + d15 * d13) * 2.0;
            }
            d = d5 + d11 * d4;
            d2 = d6 + d12 * d4;
            d3 = d7 + d13 * d4;
            if (d12 < 0.0) {
                d2 += 1.0;
            } else if (d12 > 0.0) {
                d2 += 0.5;
            }
            return new Vector3d(d, d2, d3);
        }
        return null;
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        AxisAlignedBB axisAlignedBB = this.getBoundingBox();
        return this.hasDisplayTile() ? axisAlignedBB.grow((double)Math.abs(this.getDisplayTileOffset()) / 16.0) : axisAlignedBB;
    }

    @Override
    protected void readAdditional(CompoundNBT compoundNBT) {
        if (compoundNBT.getBoolean("CustomDisplayTile")) {
            this.setDisplayTile(NBTUtil.readBlockState(compoundNBT.getCompound("DisplayState")));
            this.setDisplayTileOffset(compoundNBT.getInt("DisplayOffset"));
        }
    }

    @Override
    protected void writeAdditional(CompoundNBT compoundNBT) {
        if (this.hasDisplayTile()) {
            compoundNBT.putBoolean("CustomDisplayTile", false);
            compoundNBT.put("DisplayState", NBTUtil.writeBlockState(this.getDisplayTile()));
            compoundNBT.putInt("DisplayOffset", this.getDisplayTileOffset());
        }
    }

    @Override
    public void applyEntityCollision(Entity entity2) {
        double d;
        double d2;
        double d3;
        if (!(this.world.isRemote || entity2.noClip || this.noClip || this.isPassenger(entity2) || !((d3 = (d2 = entity2.getPosX() - this.getPosX()) * d2 + (d = entity2.getPosZ() - this.getPosZ()) * d) >= (double)1.0E-4f))) {
            d3 = MathHelper.sqrt(d3);
            d2 /= d3;
            d /= d3;
            double d4 = 1.0 / d3;
            if (d4 > 1.0) {
                d4 = 1.0;
            }
            d2 *= d4;
            d *= d4;
            d2 *= (double)0.1f;
            d *= (double)0.1f;
            d2 *= (double)(1.0f - this.entityCollisionReduction);
            d *= (double)(1.0f - this.entityCollisionReduction);
            d2 *= 0.5;
            d *= 0.5;
            if (entity2 instanceof AbstractMinecartEntity) {
                Vector3d vector3d;
                double d5;
                double d6 = entity2.getPosX() - this.getPosX();
                Vector3d vector3d2 = new Vector3d(d6, 0.0, d5 = entity2.getPosZ() - this.getPosZ()).normalize();
                double d7 = Math.abs(vector3d2.dotProduct(vector3d = new Vector3d(MathHelper.cos(this.rotationYaw * ((float)Math.PI / 180)), 0.0, MathHelper.sin(this.rotationYaw * ((float)Math.PI / 180))).normalize()));
                if (d7 < (double)0.8f) {
                    return;
                }
                Vector3d vector3d3 = this.getMotion();
                Vector3d vector3d4 = entity2.getMotion();
                if (((AbstractMinecartEntity)entity2).getMinecartType() == Type.FURNACE && this.getMinecartType() != Type.FURNACE) {
                    this.setMotion(vector3d3.mul(0.2, 1.0, 0.2));
                    this.addVelocity(vector3d4.x - d2, 0.0, vector3d4.z - d);
                    entity2.setMotion(vector3d4.mul(0.95, 1.0, 0.95));
                } else if (((AbstractMinecartEntity)entity2).getMinecartType() != Type.FURNACE && this.getMinecartType() == Type.FURNACE) {
                    entity2.setMotion(vector3d4.mul(0.2, 1.0, 0.2));
                    entity2.addVelocity(vector3d3.x + d2, 0.0, vector3d3.z + d);
                    this.setMotion(vector3d3.mul(0.95, 1.0, 0.95));
                } else {
                    double d8 = (vector3d4.x + vector3d3.x) / 2.0;
                    double d9 = (vector3d4.z + vector3d3.z) / 2.0;
                    this.setMotion(vector3d3.mul(0.2, 1.0, 0.2));
                    this.addVelocity(d8 - d2, 0.0, d9 - d);
                    entity2.setMotion(vector3d4.mul(0.2, 1.0, 0.2));
                    entity2.addVelocity(d8 + d2, 0.0, d9 + d);
                }
            } else {
                this.addVelocity(-d2, 0.0, -d);
                entity2.addVelocity(d2 / 4.0, 0.0, d / 4.0);
            }
        }
    }

    @Override
    public void setPositionAndRotationDirect(double d, double d2, double d3, float f, float f2, int n, boolean bl) {
        this.minecartX = d;
        this.minecartY = d2;
        this.minecartZ = d3;
        this.minecartYaw = f;
        this.minecartPitch = f2;
        this.turnProgress = n + 2;
        this.setMotion(this.velocityX, this.velocityY, this.velocityZ);
    }

    @Override
    public void setVelocity(double d, double d2, double d3) {
        this.velocityX = d;
        this.velocityY = d2;
        this.velocityZ = d3;
        this.setMotion(this.velocityX, this.velocityY, this.velocityZ);
    }

    public void setDamage(float f) {
        this.dataManager.set(DAMAGE, Float.valueOf(f));
    }

    public float getDamage() {
        return this.dataManager.get(DAMAGE).floatValue();
    }

    public void setRollingAmplitude(int n) {
        this.dataManager.set(ROLLING_AMPLITUDE, n);
    }

    public int getRollingAmplitude() {
        return this.dataManager.get(ROLLING_AMPLITUDE);
    }

    public void setRollingDirection(int n) {
        this.dataManager.set(ROLLING_DIRECTION, n);
    }

    public int getRollingDirection() {
        return this.dataManager.get(ROLLING_DIRECTION);
    }

    public abstract Type getMinecartType();

    public BlockState getDisplayTile() {
        return !this.hasDisplayTile() ? this.getDefaultDisplayTile() : Block.getStateById(this.getDataManager().get(DISPLAY_TILE));
    }

    public BlockState getDefaultDisplayTile() {
        return Blocks.AIR.getDefaultState();
    }

    public int getDisplayTileOffset() {
        return !this.hasDisplayTile() ? this.getDefaultDisplayTileOffset() : this.getDataManager().get(DISPLAY_TILE_OFFSET).intValue();
    }

    public int getDefaultDisplayTileOffset() {
        return 1;
    }

    public void setDisplayTile(BlockState blockState) {
        this.getDataManager().set(DISPLAY_TILE, Block.getStateId(blockState));
        this.setHasDisplayTile(false);
    }

    public void setDisplayTileOffset(int n) {
        this.getDataManager().set(DISPLAY_TILE_OFFSET, n);
        this.setHasDisplayTile(false);
    }

    public boolean hasDisplayTile() {
        return this.getDataManager().get(SHOW_BLOCK);
    }

    public void setHasDisplayTile(boolean bl) {
        this.getDataManager().set(SHOW_BLOCK, bl);
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return new SSpawnObjectPacket(this);
    }

    private VoxelShape lambda$func_230268_c_$2(BlockPos blockPos) {
        return this.world.getBlockState(blockPos).getCollisionShape(this.world, blockPos);
    }

    private VoxelShape lambda$func_230268_c_$1(BlockPos.Mutable mutable) {
        return TransportationHelper.func_242380_a(this.world, (BlockPos)mutable.down());
    }

    private static void lambda$static$0(EnumMap enumMap) {
        Vector3i vector3i = Direction.WEST.getDirectionVec();
        Vector3i vector3i2 = Direction.EAST.getDirectionVec();
        Vector3i vector3i3 = Direction.NORTH.getDirectionVec();
        Vector3i vector3i4 = Direction.SOUTH.getDirectionVec();
        Vector3i vector3i5 = vector3i.down();
        Vector3i vector3i6 = vector3i2.down();
        Vector3i vector3i7 = vector3i3.down();
        Vector3i vector3i8 = vector3i4.down();
        enumMap.put(RailShape.NORTH_SOUTH, Pair.of(vector3i3, vector3i4));
        enumMap.put(RailShape.EAST_WEST, Pair.of(vector3i, vector3i2));
        enumMap.put(RailShape.ASCENDING_EAST, Pair.of(vector3i5, vector3i2));
        enumMap.put(RailShape.ASCENDING_WEST, Pair.of(vector3i, vector3i6));
        enumMap.put(RailShape.ASCENDING_NORTH, Pair.of(vector3i3, vector3i8));
        enumMap.put(RailShape.ASCENDING_SOUTH, Pair.of(vector3i7, vector3i4));
        enumMap.put(RailShape.SOUTH_EAST, Pair.of(vector3i4, vector3i2));
        enumMap.put(RailShape.SOUTH_WEST, Pair.of(vector3i4, vector3i));
        enumMap.put(RailShape.NORTH_WEST, Pair.of(vector3i3, vector3i));
        enumMap.put(RailShape.NORTH_EAST, Pair.of(vector3i3, vector3i2));
    }

    public static enum Type {
        RIDEABLE,
        CHEST,
        FURNACE,
        TNT,
        SPAWNER,
        HOPPER,
        COMMAND_BLOCK;

    }
}

