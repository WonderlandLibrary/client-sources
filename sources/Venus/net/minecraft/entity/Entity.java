/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import it.unimi.dsi.fastutil.objects.Object2DoubleArrayMap;
import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import mpp.venusfr.events.EventStartRiding;
import mpp.venusfr.events.MovingEvent;
import mpp.venusfr.events.PostMoveEvent;
import mpp.venusfr.functions.api.FunctionRegistry;
import mpp.venusfr.functions.impl.combat.Hitbox;
import mpp.venusfr.functions.impl.render.Trails;
import mpp.venusfr.scripts.lua.classes.EntityClass;
import mpp.venusfr.venusfr;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.HoneyBlock;
import net.minecraft.block.PortalInfo;
import net.minecraft.block.PortalSize;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.PushReaction;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.command.CommandSource;
import net.minecraft.command.ICommandSource;
import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ReportedException;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.ProtectionEnchantment;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.Pose;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.DoubleNBT;
import net.minecraft.nbt.FloatNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.MinecraftServer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ITag;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.INameable;
import net.minecraft.util.Mirror;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.ReuseableStream;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.TeleportationRepositioner;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraft.world.DimensionType;
import net.minecraft.world.Explosion;
import net.minecraft.world.GameRules;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.server.TicketType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class Entity
implements INameable,
ICommandSource {
    protected static final Logger LOGGER = LogManager.getLogger();
    private static final AtomicInteger NEXT_ENTITY_ID = new AtomicInteger();
    private static final List<ItemStack> EMPTY_EQUIPMENT = Collections.emptyList();
    private static final AxisAlignedBB ZERO_AABB = new AxisAlignedBB(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
    private static double renderDistanceWeight = 1.0;
    private final EntityType<?> type;
    private int entityId = NEXT_ENTITY_ID.incrementAndGet();
    public boolean preventEntitySpawning;
    private final List<Entity> passengers = Lists.newArrayList();
    protected int rideCooldown;
    public ArrayList<Trails.Point> points = new ArrayList();
    @Nullable
    private Entity ridingEntity;
    public boolean forceSpawn;
    public World world;
    public double prevPosX;
    public double prevPosY;
    public double prevPosZ;
    private Vector3d positionVec;
    private BlockPos position;
    public Vector3d motion = Vector3d.ZERO;
    public float rotationYaw;
    public float rotationYawOffset = -2.14748365E9f;
    public float rotationPitch;
    public float prevRotationYaw;
    public float prevRotationPitch;
    private AxisAlignedBB boundingBox = ZERO_AABB;
    protected boolean onGround;
    public boolean collidedHorizontally;
    public boolean collidedVertically;
    public boolean velocityChanged;
    protected Vector3d motionMultiplier = Vector3d.ZERO;
    public boolean removed;
    public float prevDistanceWalkedModified;
    public float distanceWalkedModified;
    public float distanceWalkedOnStepModified;
    public float fallDistance;
    public float nextFallDistance;
    private float nextStepDistance = 1.0f;
    private float nextFlap = 1.0f;
    public double lastTickPosX;
    public double lastTickPosY;
    public double lastTickPosZ;
    public float stepHeight;
    public boolean noClip;
    public float entityCollisionReduction;
    protected final Random rand = new Random();
    public int ticksExisted;
    private int fire = -this.getFireImmuneTicks();
    public boolean inWater;
    protected Object2DoubleMap<ITag<Fluid>> eyesFluidLevel = new Object2DoubleArrayMap<ITag<Fluid>>(2);
    protected boolean eyesInWater;
    @Nullable
    protected ITag<Fluid> field_241335_O_;
    public int hurtResistantTime;
    protected boolean firstUpdate = true;
    protected final EntityDataManager dataManager;
    protected static final DataParameter<Byte> FLAGS = EntityDataManager.createKey(Entity.class, DataSerializers.BYTE);
    private static final DataParameter<Integer> AIR = EntityDataManager.createKey(Entity.class, DataSerializers.VARINT);
    private static final DataParameter<Optional<ITextComponent>> CUSTOM_NAME = EntityDataManager.createKey(Entity.class, DataSerializers.OPTIONAL_TEXT_COMPONENT);
    private static final DataParameter<Boolean> CUSTOM_NAME_VISIBLE = EntityDataManager.createKey(Entity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> SILENT = EntityDataManager.createKey(Entity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> NO_GRAVITY = EntityDataManager.createKey(Entity.class, DataSerializers.BOOLEAN);
    protected static final DataParameter<Pose> POSE = EntityDataManager.createKey(Entity.class, DataSerializers.POSE);
    public boolean addedToChunk;
    public int chunkCoordX;
    public int chunkCoordY;
    public int chunkCoordZ;
    private boolean isLoaded;
    private Vector3d field_242272_av;
    public boolean ignoreFrustumCheck;
    public boolean isAirBorne;
    private int field_242273_aw;
    protected boolean inPortal;
    protected int portalCounter;
    protected BlockPos field_242271_ac;
    private boolean invulnerable;
    protected UUID entityUniqueID = MathHelper.getRandomUUID(this.rand);
    protected String cachedUniqueIdString = this.entityUniqueID.toString();
    protected boolean glowing;
    private final Set<String> tags = Sets.newHashSet();
    private boolean isPositionDirty;
    private final double[] pistonDeltas = new double[]{0.0, 0.0, 0.0};
    private long pistonDeltasGameTime;
    private EntitySize size;
    private float eyeHeight;
    private EntityClass luaClass = new EntityClass(this);

    public Entity(EntityType<?> entityType, World world) {
        this.type = entityType;
        this.world = world;
        this.size = entityType.getSize();
        this.positionVec = Vector3d.ZERO;
        this.position = BlockPos.ZERO;
        this.field_242272_av = Vector3d.ZERO;
        this.setPosition(0.0, 0.0, 0.0);
        this.dataManager = new EntityDataManager(this);
        this.dataManager.register(FLAGS, (byte)0);
        this.dataManager.register(AIR, this.getMaxAir());
        this.dataManager.register(CUSTOM_NAME_VISIBLE, false);
        this.dataManager.register(CUSTOM_NAME, Optional.empty());
        this.dataManager.register(SILENT, false);
        this.dataManager.register(NO_GRAVITY, false);
        this.dataManager.register(POSE, Pose.STANDING);
        this.registerData();
        this.eyeHeight = this.getEyeHeight(Pose.STANDING, this.size);
    }

    public double getDistanceEyePos(LivingEntity livingEntity) {
        double d = livingEntity.getWidth() / 2.0f;
        double d2 = MathHelper.clamp(this.getPosX() - livingEntity.getPosX(), -d, d);
        double d3 = MathHelper.clamp(this.getPosY() + (double)this.getEyeHeight() - livingEntity.getPosY(), 0.0, (double)livingEntity.getHeight());
        double d4 = MathHelper.clamp(this.getPosZ() - livingEntity.getPosZ(), -d, d);
        return this.getEyePosition(1.0f).distanceTo(livingEntity.getPositionVec().add(d2, d3, d4));
    }

    public boolean func_242278_a(BlockPos blockPos, BlockState blockState) {
        VoxelShape voxelShape = blockState.getCollisionShape(this.world, blockPos, ISelectionContext.forEntity(this));
        VoxelShape voxelShape2 = voxelShape.withOffset(blockPos.getX(), blockPos.getY(), blockPos.getZ());
        return VoxelShapes.compare(voxelShape2, VoxelShapes.create(this.getBoundingBox()), IBooleanFunction.AND);
    }

    public int getTeamColor() {
        Team team = this.getTeam();
        return team != null && team.getColor().getColor() != null ? team.getColor().getColor() : 0xFFFFFF;
    }

    public boolean isSpectator() {
        return true;
    }

    public final void detach() {
        if (this.isBeingRidden()) {
            this.removePassengers();
        }
        if (this.isPassenger()) {
            this.stopRiding();
        }
    }

    public void setPacketCoordinates(double d, double d2, double d3) {
        this.func_242277_a(new Vector3d(d, d2, d3));
    }

    public void func_242277_a(Vector3d vector3d) {
        this.field_242272_av = vector3d;
    }

    public Vector3d func_242274_V() {
        return this.field_242272_av;
    }

    public EntityType<?> getType() {
        return this.type;
    }

    public int getEntityId() {
        return this.entityId;
    }

    public void setEntityId(int n) {
        this.entityId = n;
    }

    public Set<String> getTags() {
        return this.tags;
    }

    public boolean addTag(String string) {
        return this.tags.size() >= 1024 ? false : this.tags.add(string);
    }

    public boolean removeTag(String string) {
        return this.tags.remove(string);
    }

    public void onKillCommand() {
        this.remove();
    }

    protected abstract void registerData();

    public EntityDataManager getDataManager() {
        return this.dataManager;
    }

    public boolean equals(Object object) {
        if (object instanceof Entity) {
            return ((Entity)object).entityId == this.entityId;
        }
        return true;
    }

    public int hashCode() {
        return this.entityId;
    }

    protected void preparePlayerToSpawn() {
        if (this.world != null) {
            for (double d = this.getPosY(); d > 0.0 && d < 256.0; d += 1.0) {
                this.setPosition(this.getPosX(), d, this.getPosZ());
                if (this.world.hasNoCollisions(this)) break;
            }
            this.setMotion(Vector3d.ZERO);
            this.rotationPitch = 0.0f;
        }
    }

    public void remove() {
        this.removed = true;
    }

    public void setPose(Pose pose) {
        this.dataManager.set(POSE, pose);
    }

    public Pose getPose() {
        return this.dataManager.get(POSE);
    }

    public boolean isEntityInRange(Entity entity2, double d) {
        double d2 = entity2.positionVec.x - this.positionVec.x;
        double d3 = entity2.positionVec.y - this.positionVec.y;
        double d4 = entity2.positionVec.z - this.positionVec.z;
        return d2 * d2 + d3 * d3 + d4 * d4 < d * d;
    }

    protected void setRotation(float f, float f2) {
        this.rotationYaw = f % 360.0f;
        this.rotationPitch = f2 % 360.0f;
    }

    public void setPosition(double d, double d2, double d3) {
        this.setRawPosition(d, d2, d3);
        this.setBoundingBox(this.size.func_242285_a(d, d2, d3));
    }

    protected void recenterBoundingBox() {
        this.setPosition(this.positionVec.x, this.positionVec.y, this.positionVec.z);
    }

    public void rotateTowards(double d, double d2) {
        double d3 = d2 * 0.15;
        double d4 = d * 0.15;
        this.rotationPitch = (float)((double)this.rotationPitch + d3);
        this.rotationYaw = (float)((double)this.rotationYaw + d4);
        this.rotationPitch = MathHelper.clamp(this.rotationPitch, -90.0f, 90.0f);
        this.prevRotationPitch = (float)((double)this.prevRotationPitch + d3);
        this.prevRotationYaw = (float)((double)this.prevRotationYaw + d4);
        this.prevRotationPitch = MathHelper.clamp(this.prevRotationPitch, -90.0f, 90.0f);
        if (this.ridingEntity != null) {
            this.ridingEntity.applyOrientationToEntity(this);
        }
    }

    public void tick() {
        if (!this.world.isRemote) {
            this.setFlag(6, this.isGlowing());
        }
        this.baseTick();
    }

    public void baseTick() {
        this.world.getProfiler().startSection("entityBaseTick");
        if (this.isPassenger() && this.getRidingEntity().removed) {
            this.stopRiding();
        }
        if (this.rideCooldown > 0) {
            --this.rideCooldown;
        }
        this.prevDistanceWalkedModified = this.distanceWalkedModified;
        this.prevRotationPitch = this.rotationPitch;
        this.prevRotationYaw = this.rotationYaw;
        this.updatePortal();
        if (this.func_230269_aK_()) {
            this.func_233569_aL_();
        }
        this.func_233566_aG_();
        this.updateEyesInWater();
        this.updateSwimming();
        if (this.world.isRemote) {
            this.extinguish();
        } else if (this.fire > 0) {
            if (this.isImmuneToFire()) {
                this.forceFireTicks(this.fire - 4);
                if (this.fire < 0) {
                    this.extinguish();
                }
            } else {
                if (this.fire % 20 == 0 && !this.isInLava()) {
                    this.attackEntityFrom(DamageSource.ON_FIRE, 1.0f);
                }
                this.forceFireTicks(this.fire - 1);
            }
        }
        if (this.isInLava()) {
            this.setOnFireFromLava();
            this.fallDistance *= 0.5f;
        }
        if (this.getPosY() < -64.0) {
            this.outOfWorld();
        }
        if (!this.world.isRemote) {
            this.setFlag(0, this.fire > 0);
        }
        this.firstUpdate = false;
        this.world.getProfiler().endSection();
    }

    public void func_242279_ag() {
        this.field_242273_aw = this.getPortalCooldown();
    }

    public boolean func_242280_ah() {
        return this.field_242273_aw > 0;
    }

    protected void decrementTimeUntilPortal() {
        if (this.func_242280_ah()) {
            --this.field_242273_aw;
        }
    }

    public int getMaxInPortalTime() {
        return 1;
    }

    protected void setOnFireFromLava() {
        if (!this.isImmuneToFire()) {
            this.setFire(15);
            this.attackEntityFrom(DamageSource.LAVA, 4.0f);
        }
    }

    public void setFire(int n) {
        int n2 = n * 20;
        if (this instanceof LivingEntity) {
            n2 = ProtectionEnchantment.getFireTimeForEntity((LivingEntity)this, n2);
        }
        if (this.fire < n2) {
            this.forceFireTicks(n2);
        }
    }

    public void forceFireTicks(int n) {
        this.fire = n;
    }

    public int getFireTimer() {
        return this.fire;
    }

    public void extinguish() {
        this.forceFireTicks(0);
    }

    protected void outOfWorld() {
        this.remove();
    }

    public boolean isOffsetPositionInLiquid(double d, double d2, double d3) {
        return this.isLiquidPresentInAABB(this.getBoundingBox().offset(d, d2, d3));
    }

    private boolean isLiquidPresentInAABB(AxisAlignedBB axisAlignedBB) {
        return this.world.hasNoCollisions(this, axisAlignedBB) && !this.world.containsAnyLiquid(axisAlignedBB);
    }

    public void setOnGround(boolean bl) {
        this.onGround = bl;
    }

    public boolean isOnGround() {
        return this.onGround;
    }

    public void move(MoverType moverType, Vector3d vector3d) {
        double d = this.getPosX();
        double d2 = this.getPosZ();
        if (this.noClip) {
            this.setBoundingBox(this.getBoundingBox().offset(vector3d));
            this.resetPositionToBB();
        } else {
            Object object;
            Object object2;
            Object object3;
            Vector3d vector3d2;
            if (moverType == MoverType.PISTON && (vector3d = this.handlePistonMovement(vector3d)).equals(Vector3d.ZERO)) {
                return;
            }
            this.world.getProfiler().startSection("move");
            if (this.motionMultiplier.lengthSquared() > 1.0E-7) {
                vector3d = vector3d.mul(this.motionMultiplier);
                this.motionMultiplier = Vector3d.ZERO;
                this.setMotion(Vector3d.ZERO);
            }
            boolean bl = false;
            boolean bl2 = false;
            vector3d = this.maybeBackOffFromEdge(vector3d, moverType);
            Vector3d vector3d3 = this.maybeBackOffFromEdge(vector3d, moverType);
            Vector3d vector3d4 = this.getAllowedMovement(vector3d3, false, true);
            if (this instanceof ClientPlayerEntity) {
                vector3d2 = this.getPositionVec().add(vector3d4);
                object3 = this.getPositionVec();
                boolean bl3 = vector3d3.y != vector3d4.y;
                boolean bl4 = !MathHelper.epsilonEquals(vector3d3.x, vector3d4.x) || !MathHelper.epsilonEquals(vector3d3.z, vector3d4.z);
                boolean bl5 = bl3 && vector3d3.y < 0.0;
                object2 = this.getBoundingBox();
                if (bl5) {
                    this.nextFallDistance = 0.0f;
                } else if (vector3d4.y < 0.0) {
                    this.nextFallDistance = (float)((double)this.nextFallDistance - vector3d4.y);
                }
                object = new MovingEvent((Vector3d)object3, vector3d2, vector3d, bl5, bl4, bl3, (AxisAlignedBB)object2);
                venusfr.getInstance().getEventBus().post(object);
                vector3d = ((MovingEvent)object).getMotion();
                bl2 = ((MovingEvent)object).isIgnoreHorizontal();
                bl = ((MovingEvent)object).isIgnoreVertical();
            }
            if ((vector3d2 = this.getAllowedMovement(vector3d, bl2, bl)).lengthSquared() > 1.0E-7) {
                this.setBoundingBox(this.getBoundingBox().offset(vector3d2));
                this.resetPositionToBB();
            }
            this.world.getProfiler().endSection();
            this.world.getProfiler().startSection("rest");
            this.collidedHorizontally = !MathHelper.epsilonEquals(vector3d.x, vector3d2.x) || !MathHelper.epsilonEquals(vector3d.z, vector3d2.z);
            this.collidedVertically = vector3d.y != vector3d2.y;
            boolean bl6 = this.onGround = this.collidedVertically && vector3d.y < 0.0;
            if (this instanceof ClientPlayerEntity) {
                double d3 = this.getPosX() - d;
                double d4 = this.getPosZ() - d2;
                object2 = new PostMoveEvent(Math.sqrt(d3 * d3 + d4 * d4));
                venusfr.getInstance().getEventBus().post(object2);
            }
            object3 = this.getOnPosition();
            BlockState blockState = this.world.getBlockState((BlockPos)object3);
            this.updateFallState(vector3d2.y, this.onGround, blockState, (BlockPos)object3);
            Vector3d vector3d5 = this.getMotion();
            if (vector3d.x != vector3d2.x) {
                this.setMotion(0.0, vector3d5.y, vector3d5.z);
            }
            if (vector3d.z != vector3d2.z) {
                this.setMotion(vector3d5.x, vector3d5.y, 0.0);
            }
            Block block = blockState.getBlock();
            if (vector3d.y != vector3d2.y) {
                block.onLanded(this.world, this);
            }
            if (this.onGround && !this.isSteppingCarefully()) {
                block.onEntityWalk(this.world, (BlockPos)object3, this);
            }
            if (this.canTriggerWalking() && !this.isPassenger()) {
                double d5 = vector3d2.x;
                double d6 = vector3d2.y;
                double d7 = vector3d2.z;
                if (!block.isIn(BlockTags.CLIMBABLE)) {
                    d6 = 0.0;
                }
                this.distanceWalkedModified = (float)((double)this.distanceWalkedModified + (double)MathHelper.sqrt(Entity.horizontalMag(vector3d2)) * 0.6);
                this.distanceWalkedOnStepModified = (float)((double)this.distanceWalkedOnStepModified + (double)MathHelper.sqrt(d5 * d5 + d6 * d6 + d7 * d7) * 0.6);
                if (this.distanceWalkedOnStepModified > this.nextStepDistance && !blockState.isAir()) {
                    this.nextStepDistance = this.determineNextStepDistance();
                    if (this.isInWater()) {
                        Entity entity2 = this.isBeingRidden() && this.getControllingPassenger() != null ? this.getControllingPassenger() : this;
                        float f = entity2 == this ? 0.35f : 0.4f;
                        Vector3d vector3d6 = entity2.getMotion();
                        float f2 = MathHelper.sqrt(vector3d6.x * vector3d6.x * (double)0.2f + vector3d6.y * vector3d6.y + vector3d6.z * vector3d6.z * (double)0.2f) * f;
                        if (f2 > 1.0f) {
                            f2 = 1.0f;
                        }
                        this.playSwimSound(f2);
                    } else {
                        this.playStepSound((BlockPos)object3, blockState);
                    }
                } else if (this.distanceWalkedOnStepModified > this.nextFlap && this.makeFlySound() && blockState.isAir()) {
                    this.nextFlap = this.playFlySound(this.distanceWalkedOnStepModified);
                }
            }
            try {
                this.doBlockCollisions();
            } catch (Throwable throwable) {
                object = CrashReport.makeCrashReport(throwable, "Checking entity block collision");
                CrashReportCategory crashReportCategory = ((CrashReport)object).makeCategory("Entity being checked for collision");
                this.fillCrashReport(crashReportCategory);
                throw new ReportedException((CrashReport)object);
            }
            float f = this.getSpeedFactor();
            this.setMotion(this.getMotion().mul(f, 1.0, f));
            if (this.world.getStatesInArea(this.getBoundingBox().shrink(0.001)).noneMatch(Entity::lambda$move$0) && this.fire <= 0) {
                this.forceFireTicks(-this.getFireImmuneTicks());
            }
            if (this.isInWaterRainOrBubbleColumn() && this.isBurning()) {
                this.playSound(SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, 0.7f, 1.6f + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4f);
                this.forceFireTicks(-this.getFireImmuneTicks());
            }
            this.world.getProfiler().endSection();
        }
    }

    protected BlockPos getOnPosition() {
        BlockPos blockPos;
        BlockState blockState;
        Block block;
        int n;
        int n2;
        int n3 = MathHelper.floor(this.positionVec.x);
        BlockPos blockPos2 = new BlockPos(n3, n2 = MathHelper.floor(this.positionVec.y - (double)0.2f), n = MathHelper.floor(this.positionVec.z));
        if (this.world.getBlockState(blockPos2).isAir() && ((block = (blockState = this.world.getBlockState(blockPos = blockPos2.down())).getBlock()).isIn(BlockTags.FENCES) || block.isIn(BlockTags.WALLS) || block instanceof FenceGateBlock)) {
            return blockPos;
        }
        return blockPos2;
    }

    protected float getJumpFactor() {
        float f = this.world.getBlockState(this.getPosition()).getBlock().getJumpFactor();
        float f2 = this.world.getBlockState(this.getPositionUnderneath()).getBlock().getJumpFactor();
        return (double)f == 1.0 ? f2 : f;
    }

    protected float getSpeedFactor() {
        Block block = this.world.getBlockState(this.getPosition()).getBlock();
        float f = block.getSpeedFactor();
        if (block != Blocks.WATER && block != Blocks.BUBBLE_COLUMN) {
            return (double)f == 1.0 ? this.world.getBlockState(this.getPositionUnderneath()).getBlock().getSpeedFactor() : f;
        }
        return f;
    }

    protected BlockPos getPositionUnderneath() {
        return new BlockPos(this.positionVec.x, this.getBoundingBox().minY - 0.5000001, this.positionVec.z);
    }

    protected Vector3d maybeBackOffFromEdge(Vector3d vector3d, MoverType moverType) {
        return vector3d;
    }

    protected Vector3d handlePistonMovement(Vector3d vector3d) {
        if (vector3d.lengthSquared() <= 1.0E-7) {
            return vector3d;
        }
        long l = this.world.getGameTime();
        if (l != this.pistonDeltasGameTime) {
            Arrays.fill(this.pistonDeltas, 0.0);
            this.pistonDeltasGameTime = l;
        }
        if (vector3d.x != 0.0) {
            double d = this.calculatePistonDeltas(Direction.Axis.X, vector3d.x);
            return Math.abs(d) <= (double)1.0E-5f ? Vector3d.ZERO : new Vector3d(d, 0.0, 0.0);
        }
        if (vector3d.y != 0.0) {
            double d = this.calculatePistonDeltas(Direction.Axis.Y, vector3d.y);
            return Math.abs(d) <= (double)1.0E-5f ? Vector3d.ZERO : new Vector3d(0.0, d, 0.0);
        }
        if (vector3d.z != 0.0) {
            double d = this.calculatePistonDeltas(Direction.Axis.Z, vector3d.z);
            return Math.abs(d) <= (double)1.0E-5f ? Vector3d.ZERO : new Vector3d(0.0, 0.0, d);
        }
        return Vector3d.ZERO;
    }

    private double calculatePistonDeltas(Direction.Axis axis, double d) {
        int n = axis.ordinal();
        double d2 = MathHelper.clamp(d + this.pistonDeltas[n], -0.51, 0.51);
        d = d2 - this.pistonDeltas[n];
        this.pistonDeltas[n] = d2;
        return d;
    }

    private Vector3d getAllowedMovement(Vector3d vector3d, boolean bl, boolean bl2) {
        boolean bl3;
        AxisAlignedBB axisAlignedBB = this.getBoundingBox();
        ISelectionContext iSelectionContext = ISelectionContext.forEntity(this);
        VoxelShape voxelShape = this.world.getWorldBorder().getShape();
        Stream<Object> stream = VoxelShapes.compare(voxelShape, VoxelShapes.create(axisAlignedBB.shrink(1.0E-7)), IBooleanFunction.AND) ? Stream.empty() : Stream.of(voxelShape);
        Stream<VoxelShape> stream2 = this.world.func_230318_c_(this, axisAlignedBB.expand(vector3d), Entity::lambda$getAllowedMovement$1);
        ReuseableStream<VoxelShape> reuseableStream = new ReuseableStream<VoxelShape>(Stream.concat(stream2, stream));
        Vector3d vector3d2 = vector3d.lengthSquared() == 0.0 ? vector3d : Entity.collideBoundingBoxHeuristically(this, vector3d, axisAlignedBB, this.world, iSelectionContext, reuseableStream, bl, bl2);
        boolean bl4 = vector3d.x != vector3d2.x;
        boolean bl5 = vector3d.y != vector3d2.y;
        boolean bl6 = vector3d.z != vector3d2.z;
        boolean bl7 = bl3 = this.onGround || bl5 && vector3d.y < 0.0;
        if (this.stepHeight > 0.0f && bl3 && (bl4 || bl6)) {
            Vector3d vector3d3;
            Vector3d vector3d4 = Entity.collideBoundingBoxHeuristically(this, new Vector3d(vector3d.x, this.stepHeight, vector3d.z), axisAlignedBB, this.world, iSelectionContext, reuseableStream, bl, bl2);
            Vector3d vector3d5 = Entity.collideBoundingBoxHeuristically(this, new Vector3d(0.0, this.stepHeight, 0.0), axisAlignedBB.expand(vector3d.x, 0.0, vector3d.z), this.world, iSelectionContext, reuseableStream, bl, bl2);
            if (vector3d5.y < (double)this.stepHeight && Entity.horizontalMag(vector3d3 = Entity.collideBoundingBoxHeuristically(this, new Vector3d(vector3d.x, 0.0, vector3d.z), axisAlignedBB.offset(vector3d5), this.world, iSelectionContext, reuseableStream, bl, bl2).add(vector3d5)) > Entity.horizontalMag(vector3d4)) {
                vector3d4 = vector3d3;
            }
            if (Entity.horizontalMag(vector3d4) > Entity.horizontalMag(vector3d2)) {
                return vector3d4.add(Entity.collideBoundingBoxHeuristically(this, new Vector3d(0.0, -vector3d4.y + vector3d.y, 0.0), axisAlignedBB.offset(vector3d4), this.world, iSelectionContext, reuseableStream, bl, bl2));
            }
        }
        return vector3d2;
    }

    public static double horizontalMag(Vector3d vector3d) {
        return vector3d.x * vector3d.x + vector3d.z * vector3d.z;
    }

    public static Vector3d collideBoundingBoxHeuristically(@Nullable Entity entity2, Vector3d vector3d, AxisAlignedBB axisAlignedBB, World world, ISelectionContext iSelectionContext, ReuseableStream<VoxelShape> reuseableStream, boolean bl, boolean bl2) {
        boolean bl3;
        boolean bl4 = vector3d.x == 0.0;
        boolean bl5 = vector3d.y == 0.0;
        boolean bl6 = bl3 = vector3d.z == 0.0;
        if (!(bl4 && bl5 || bl4 && bl3 || bl5 && bl3)) {
            ReuseableStream<VoxelShape> reuseableStream2 = new ReuseableStream<VoxelShape>(Stream.concat(reuseableStream.createStream(), world.getCollisionShapes(entity2, axisAlignedBB.expand(vector3d))));
            return Entity.collideBoundingBox(vector3d, axisAlignedBB, reuseableStream2, bl, bl2);
        }
        return Entity.getAllowedMovement(vector3d, axisAlignedBB, world, iSelectionContext, reuseableStream);
    }

    public static Vector3d collideBoundingBox(Vector3d vector3d, AxisAlignedBB axisAlignedBB, ReuseableStream<VoxelShape> reuseableStream, boolean bl, boolean bl2) {
        double d = vector3d.x;
        double d2 = vector3d.y;
        double d3 = vector3d.z;
        if (!bl2 && d2 != 0.0 && (d2 = VoxelShapes.getAllowedOffset(Direction.Axis.Y, axisAlignedBB, reuseableStream.createStream(), d2)) != 0.0) {
            axisAlignedBB = axisAlignedBB.offset(0.0, d2, 0.0);
        }
        if (!bl) {
            boolean bl3;
            boolean bl4 = bl3 = Math.abs(d) < Math.abs(d3);
            if (bl3 && d3 != 0.0 && (d3 = VoxelShapes.getAllowedOffset(Direction.Axis.Z, axisAlignedBB, reuseableStream.createStream(), d3)) != 0.0) {
                axisAlignedBB = axisAlignedBB.offset(0.0, 0.0, d3);
            }
            if (d != 0.0) {
                d = VoxelShapes.getAllowedOffset(Direction.Axis.X, axisAlignedBB, reuseableStream.createStream(), d);
                if (!bl3 && d != 0.0) {
                    axisAlignedBB = axisAlignedBB.offset(d, 0.0, 0.0);
                }
            }
            if (!bl3 && d3 != 0.0) {
                d3 = VoxelShapes.getAllowedOffset(Direction.Axis.Z, axisAlignedBB, reuseableStream.createStream(), d3);
            }
        }
        return new Vector3d(d, d2, d3);
    }

    public static Vector3d getAllowedMovement(Vector3d vector3d, AxisAlignedBB axisAlignedBB, IWorldReader iWorldReader, ISelectionContext iSelectionContext, ReuseableStream<VoxelShape> reuseableStream) {
        boolean bl;
        double d = vector3d.x;
        double d2 = vector3d.y;
        double d3 = vector3d.z;
        if (d2 != 0.0 && (d2 = VoxelShapes.getAllowedOffset(Direction.Axis.Y, axisAlignedBB, iWorldReader, d2, iSelectionContext, reuseableStream.createStream())) != 0.0) {
            axisAlignedBB = axisAlignedBB.offset(0.0, d2, 0.0);
        }
        boolean bl2 = bl = Math.abs(d) < Math.abs(d3);
        if (bl && d3 != 0.0 && (d3 = VoxelShapes.getAllowedOffset(Direction.Axis.Z, axisAlignedBB, iWorldReader, d3, iSelectionContext, reuseableStream.createStream())) != 0.0) {
            axisAlignedBB = axisAlignedBB.offset(0.0, 0.0, d3);
        }
        if (d != 0.0) {
            d = VoxelShapes.getAllowedOffset(Direction.Axis.X, axisAlignedBB, iWorldReader, d, iSelectionContext, reuseableStream.createStream());
            if (!bl && d != 0.0) {
                axisAlignedBB = axisAlignedBB.offset(d, 0.0, 0.0);
            }
        }
        if (!bl && d3 != 0.0) {
            d3 = VoxelShapes.getAllowedOffset(Direction.Axis.Z, axisAlignedBB, iWorldReader, d3, iSelectionContext, reuseableStream.createStream());
        }
        return new Vector3d(d, d2, d3);
    }

    protected float determineNextStepDistance() {
        return (int)this.distanceWalkedOnStepModified + 1;
    }

    public void resetPositionToBB() {
        AxisAlignedBB axisAlignedBB = this.getBoundingBox();
        this.setRawPosition((axisAlignedBB.minX + axisAlignedBB.maxX) / 2.0, axisAlignedBB.minY, (axisAlignedBB.minZ + axisAlignedBB.maxZ) / 2.0);
    }

    protected SoundEvent getSwimSound() {
        return SoundEvents.ENTITY_GENERIC_SWIM;
    }

    protected SoundEvent getSplashSound() {
        return SoundEvents.ENTITY_GENERIC_SPLASH;
    }

    protected SoundEvent getHighspeedSplashSound() {
        return SoundEvents.ENTITY_GENERIC_SPLASH;
    }

    protected void doBlockCollisions() {
        AxisAlignedBB axisAlignedBB = this.getBoundingBox();
        BlockPos blockPos = new BlockPos(axisAlignedBB.minX + 0.001, axisAlignedBB.minY + 0.001, axisAlignedBB.minZ + 0.001);
        BlockPos blockPos2 = new BlockPos(axisAlignedBB.maxX - 0.001, axisAlignedBB.maxY - 0.001, axisAlignedBB.maxZ - 0.001);
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        if (this.world.isAreaLoaded(blockPos, blockPos2)) {
            for (int i = blockPos.getX(); i <= blockPos2.getX(); ++i) {
                for (int j = blockPos.getY(); j <= blockPos2.getY(); ++j) {
                    for (int k = blockPos.getZ(); k <= blockPos2.getZ(); ++k) {
                        mutable.setPos(i, j, k);
                        BlockState blockState = this.world.getBlockState(mutable);
                        try {
                            blockState.onEntityCollision(this.world, mutable, this);
                            this.onInsideBlock(blockState);
                            continue;
                        } catch (Throwable throwable) {
                            CrashReport crashReport = CrashReport.makeCrashReport(throwable, "Colliding entity with block");
                            CrashReportCategory crashReportCategory = crashReport.makeCategory("Block being collided with");
                            CrashReportCategory.addBlockInfo(crashReportCategory, mutable, blockState);
                            throw new ReportedException(crashReport);
                        }
                    }
                }
            }
        }
    }

    protected void onInsideBlock(BlockState blockState) {
    }

    protected void playStepSound(BlockPos blockPos, BlockState blockState) {
        if (!blockState.getMaterial().isLiquid()) {
            BlockState blockState2 = this.world.getBlockState(blockPos.up());
            SoundType soundType = blockState2.isIn(Blocks.SNOW) ? blockState2.getSoundType() : blockState.getSoundType();
            this.playSound(soundType.getStepSound(), soundType.getVolume() * 0.15f, soundType.getPitch());
        }
    }

    protected void playSwimSound(float f) {
        this.playSound(this.getSwimSound(), f, 1.0f + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4f);
    }

    protected float playFlySound(float f) {
        return 0.0f;
    }

    protected boolean makeFlySound() {
        return true;
    }

    public void playSound(SoundEvent soundEvent, float f, float f2) {
        if (!this.isSilent()) {
            this.world.playSound(null, this.getPosX(), this.getPosY(), this.getPosZ(), soundEvent, this.getSoundCategory(), f, f2);
        }
    }

    public boolean isSilent() {
        return this.dataManager.get(SILENT);
    }

    public void setSilent(boolean bl) {
        this.dataManager.set(SILENT, bl);
    }

    public boolean hasNoGravity() {
        return this.dataManager.get(NO_GRAVITY);
    }

    public void setNoGravity(boolean bl) {
        this.dataManager.set(NO_GRAVITY, bl);
    }

    protected boolean canTriggerWalking() {
        return false;
    }

    protected void updateFallState(double d, boolean bl, BlockState blockState, BlockPos blockPos) {
        if (bl) {
            if (this.fallDistance > 0.0f) {
                blockState.getBlock().onFallenUpon(this.world, blockPos, this, this.fallDistance);
            }
            this.fallDistance = 0.0f;
        } else if (d < 0.0) {
            this.fallDistance = (float)((double)this.fallDistance - d);
        }
    }

    public boolean isImmuneToFire() {
        return this.getType().isImmuneToFire();
    }

    public boolean onLivingFall(float f, float f2) {
        if (this.isBeingRidden()) {
            for (Entity entity2 : this.getPassengers()) {
                entity2.onLivingFall(f, f2);
            }
        }
        return true;
    }

    public boolean isInWater() {
        return this.inWater;
    }

    private boolean isInRain() {
        BlockPos blockPos = this.getPosition();
        return this.world.isRainingAt(blockPos) || this.world.isRainingAt(new BlockPos((double)blockPos.getX(), this.getBoundingBox().maxY, (double)blockPos.getZ()));
    }

    private boolean isInBubbleColumn() {
        return this.world.getBlockState(this.getPosition()).isIn(Blocks.BUBBLE_COLUMN);
    }

    public boolean isWet() {
        return this.isInWater() || this.isInRain();
    }

    public boolean isInWaterRainOrBubbleColumn() {
        return this.isInWater() || this.isInRain() || this.isInBubbleColumn();
    }

    public boolean isInWaterOrBubbleColumn() {
        return this.isInWater() || this.isInBubbleColumn();
    }

    public boolean canSwim() {
        return this.eyesInWater && this.isInWater();
    }

    public void updateSwimming() {
        if (this.isSwimming()) {
            this.setSwimming(this.isSprinting() && this.isInWater() && !this.isPassenger());
        } else {
            this.setSwimming(this.isSprinting() && this.canSwim() && !this.isPassenger());
        }
    }

    protected boolean func_233566_aG_() {
        this.eyesFluidLevel.clear();
        this.func_233567_aH_();
        double d = this.world.getDimensionType().isUltrawarm() ? 0.007 : 0.0023333333333333335;
        boolean bl = this.handleFluidAcceleration(FluidTags.LAVA, d);
        return this.isInWater() || bl;
    }

    void func_233567_aH_() {
        if (this.getRidingEntity() instanceof BoatEntity) {
            this.inWater = false;
        } else if (this.handleFluidAcceleration(FluidTags.WATER, 0.014)) {
            if (!this.inWater && !this.firstUpdate) {
                this.doWaterSplashEffect();
            }
            this.fallDistance = 0.0f;
            this.inWater = true;
            this.extinguish();
        } else {
            this.inWater = false;
        }
    }

    private void updateEyesInWater() {
        Object object;
        this.eyesInWater = this.areEyesInFluid(FluidTags.WATER);
        this.field_241335_O_ = null;
        double d = this.getPosYEye() - 0.1111111119389534;
        Entity entity2 = this.getRidingEntity();
        if (entity2 instanceof BoatEntity && !((BoatEntity)(object = (BoatEntity)entity2)).canSwim() && ((Entity)object).getBoundingBox().maxY >= d && ((Entity)object).getBoundingBox().minY <= d) {
            return;
        }
        object = new BlockPos(this.getPosX(), d, this.getPosZ());
        FluidState fluidState = this.world.getFluidState((BlockPos)object);
        for (ITag iTag : FluidTags.getAllTags()) {
            if (!fluidState.isTagged(iTag)) continue;
            double d2 = (float)((Vector3i)object).getY() + fluidState.getActualHeight(this.world, (BlockPos)object);
            if (d2 > d) {
                this.field_241335_O_ = iTag;
            }
            return;
        }
    }

    protected void doWaterSplashEffect() {
        double d;
        double d2;
        Entity entity2 = this.isBeingRidden() && this.getControllingPassenger() != null ? this.getControllingPassenger() : this;
        float f = entity2 == this ? 0.2f : 0.9f;
        Vector3d vector3d = entity2.getMotion();
        float f2 = MathHelper.sqrt(vector3d.x * vector3d.x * (double)0.2f + vector3d.y * vector3d.y + vector3d.z * vector3d.z * (double)0.2f) * f;
        if (f2 > 1.0f) {
            f2 = 1.0f;
        }
        if ((double)f2 < 0.25) {
            this.playSound(this.getSplashSound(), f2, 1.0f + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4f);
        } else {
            this.playSound(this.getHighspeedSplashSound(), f2, 1.0f + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4f);
        }
        float f3 = MathHelper.floor(this.getPosY());
        int n = 0;
        while ((float)n < 1.0f + this.size.width * 20.0f) {
            d2 = (this.rand.nextDouble() * 2.0 - 1.0) * (double)this.size.width;
            d = (this.rand.nextDouble() * 2.0 - 1.0) * (double)this.size.width;
            this.world.addParticle(ParticleTypes.BUBBLE, this.getPosX() + d2, f3 + 1.0f, this.getPosZ() + d, vector3d.x, vector3d.y - this.rand.nextDouble() * (double)0.2f, vector3d.z);
            ++n;
        }
        n = 0;
        while ((float)n < 1.0f + this.size.width * 20.0f) {
            d2 = (this.rand.nextDouble() * 2.0 - 1.0) * (double)this.size.width;
            d = (this.rand.nextDouble() * 2.0 - 1.0) * (double)this.size.width;
            this.world.addParticle(ParticleTypes.SPLASH, this.getPosX() + d2, f3 + 1.0f, this.getPosZ() + d, vector3d.x, vector3d.y, vector3d.z);
            ++n;
        }
    }

    protected BlockState getStateBelow() {
        return this.world.getBlockState(this.getOnPosition());
    }

    public boolean func_230269_aK_() {
        return this.isSprinting() && !this.isInWater() && !this.isSpectator() && !this.isCrouching() && !this.isInLava() && this.isAlive();
    }

    protected void func_233569_aL_() {
        int n;
        int n2;
        int n3 = MathHelper.floor(this.getPosX());
        BlockPos blockPos = new BlockPos(n3, n2 = MathHelper.floor(this.getPosY() - (double)0.2f), n = MathHelper.floor(this.getPosZ()));
        BlockState blockState = this.world.getBlockState(blockPos);
        if (blockState.getRenderType() != BlockRenderType.INVISIBLE) {
            Vector3d vector3d = this.getMotion();
            this.world.addParticle(new BlockParticleData(ParticleTypes.BLOCK, blockState), this.getPosX() + (this.rand.nextDouble() - 0.5) * (double)this.size.width, this.getPosY() + 0.1, this.getPosZ() + (this.rand.nextDouble() - 0.5) * (double)this.size.width, vector3d.x * -4.0, 1.5, vector3d.z * -4.0);
        }
    }

    public boolean areEyesInFluid(ITag<Fluid> iTag) {
        return this.field_241335_O_ == iTag;
    }

    public boolean isInLava() {
        return !this.firstUpdate && this.eyesFluidLevel.getDouble(FluidTags.LAVA) > 0.0;
    }

    public void moveRelative(float f, Vector3d vector3d) {
        Vector3d vector3d2 = Entity.getAbsoluteMotion(vector3d, f, this.rotationYawOffset == -2.14748365E9f ? this.rotationYaw : this.rotationYawOffset);
        this.setMotion(this.getMotion().add(vector3d2));
    }

    private static Vector3d getAbsoluteMotion(Vector3d vector3d, float f, float f2) {
        double d = vector3d.lengthSquared();
        if (d < 1.0E-7) {
            return Vector3d.ZERO;
        }
        Vector3d vector3d2 = (d > 1.0 ? vector3d.normalize() : vector3d).scale(f);
        float f3 = MathHelper.sin(f2 * ((float)Math.PI / 180));
        float f4 = MathHelper.cos(f2 * ((float)Math.PI / 180));
        return new Vector3d(vector3d2.x * (double)f4 - vector3d2.z * (double)f3, vector3d2.y, vector3d2.z * (double)f4 + vector3d2.x * (double)f3);
    }

    public float getBrightness() {
        BlockPos.Mutable mutable = new BlockPos.Mutable(this.getPosX(), 0.0, this.getPosZ());
        if (this.world.isBlockLoaded(mutable)) {
            mutable.setY(MathHelper.floor(this.getPosYEye()));
            return this.world.getBrightness(mutable);
        }
        return 0.0f;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public void setPositionAndRotation(double d, double d2, double d3, float f, float f2) {
        this.func_242281_f(d, d2, d3);
        this.rotationYaw = f % 360.0f;
        this.rotationPitch = MathHelper.clamp(f2, -90.0f, 90.0f) % 360.0f;
        this.prevRotationYaw = this.rotationYaw;
        this.prevRotationPitch = this.rotationPitch;
    }

    public void func_242281_f(double d, double d2, double d3) {
        double d4 = MathHelper.clamp(d, -3.0E7, 3.0E7);
        double d5 = MathHelper.clamp(d3, -3.0E7, 3.0E7);
        this.prevPosX = d4;
        this.prevPosY = d2;
        this.prevPosZ = d5;
        this.setPosition(d4, d2, d5);
    }

    public void moveForced(Vector3d vector3d) {
        this.moveForced(vector3d.x, vector3d.y, vector3d.z);
    }

    public void moveForced(double d, double d2, double d3) {
        this.setLocationAndAngles(d, d2, d3, this.rotationYaw, this.rotationPitch);
    }

    public void moveToBlockPosAndAngles(BlockPos blockPos, float f, float f2) {
        this.setLocationAndAngles((double)blockPos.getX() + 0.5, blockPos.getY(), (double)blockPos.getZ() + 0.5, f, f2);
    }

    public void setLocationAndAngles(double d, double d2, double d3, float f, float f2) {
        this.forceSetPosition(d, d2, d3);
        this.rotationYaw = f;
        this.rotationPitch = f2;
        this.recenterBoundingBox();
    }

    public void forceSetPosition(double d, double d2, double d3) {
        this.setRawPosition(d, d2, d3);
        this.prevPosX = d;
        this.prevPosY = d2;
        this.prevPosZ = d3;
        this.lastTickPosX = d;
        this.lastTickPosY = d2;
        this.lastTickPosZ = d3;
    }

    public float getDistance(Entity entity2) {
        float f = (float)(this.getPosX() - entity2.getPosX());
        float f2 = (float)(this.getPosY() - entity2.getPosY());
        float f3 = (float)(this.getPosZ() - entity2.getPosZ());
        return MathHelper.sqrt(f * f + f2 * f2 + f3 * f3);
    }

    public float getDistance(BlockPos blockPos) {
        float f = (float)(this.getPosX() - (double)blockPos.getX());
        float f2 = (float)(this.getPosY() - (double)blockPos.getY());
        float f3 = (float)(this.getPosZ() - (double)blockPos.getZ());
        return MathHelper.sqrt(f * f + f2 * f2 + f3 * f3);
    }

    public double getDistanceSq(double d, double d2, double d3) {
        double d4 = this.getPosX() - d;
        double d5 = this.getPosY() - d2;
        double d6 = this.getPosZ() - d3;
        return d4 * d4 + d5 * d5 + d6 * d6;
    }

    public double getDistanceSq(Entity entity2) {
        return this.getDistanceSq(entity2.getPositionVec());
    }

    public double getDistanceSq(Vector3d vector3d) {
        double d = this.getPosX() - vector3d.x;
        double d2 = this.getPosY() - vector3d.y;
        double d3 = this.getPosZ() - vector3d.z;
        return d * d + d2 * d2 + d3 * d3;
    }

    public void onCollideWithPlayer(PlayerEntity playerEntity) {
    }

    public void applyEntityCollision(Entity entity2) {
        double d;
        double d2;
        double d3;
        if (!this.isRidingSameEntity(entity2) && !entity2.noClip && !this.noClip && (d3 = MathHelper.absMax(d2 = entity2.getPosX() - this.getPosX(), d = entity2.getPosZ() - this.getPosZ())) >= (double)0.01f) {
            d3 = MathHelper.sqrt(d3);
            d2 /= d3;
            d /= d3;
            double d4 = 1.0 / d3;
            if (d4 > 1.0) {
                d4 = 1.0;
            }
            d2 *= d4;
            d *= d4;
            d2 *= (double)0.05f;
            d *= (double)0.05f;
            d2 *= (double)(1.0f - this.entityCollisionReduction);
            d *= (double)(1.0f - this.entityCollisionReduction);
            if (!this.isBeingRidden()) {
                this.addVelocity(-d2, 0.0, -d);
            }
            if (!entity2.isBeingRidden()) {
                entity2.addVelocity(d2, 0.0, d);
            }
        }
    }

    public void addVelocity(double d, double d2, double d3) {
        this.setMotion(this.getMotion().add(d, d2, d3));
        this.isAirBorne = true;
    }

    protected void markVelocityChanged() {
        this.velocityChanged = true;
    }

    public boolean attackEntityFrom(DamageSource damageSource, float f) {
        if (this.isInvulnerableTo(damageSource)) {
            return true;
        }
        this.markVelocityChanged();
        return true;
    }

    public final Vector3d getLook(float f) {
        return this.getVectorForRotation(this.getPitch(f), this.getYaw(f));
    }

    public float getPitch(float f) {
        return f == 1.0f ? this.rotationPitch : MathHelper.lerp(f, this.prevRotationPitch, this.rotationPitch);
    }

    public float getYaw(float f) {
        return f == 1.0f ? this.rotationYaw : MathHelper.lerp(f, this.prevRotationYaw, this.rotationYaw);
    }

    public final Vector3d getVectorForRotation(float f, float f2) {
        float f3 = f * ((float)Math.PI / 180);
        float f4 = -f2 * ((float)Math.PI / 180);
        float f5 = MathHelper.cos(f4);
        float f6 = MathHelper.sin(f4);
        float f7 = MathHelper.cos(f3);
        float f8 = MathHelper.sin(f3);
        return new Vector3d(f6 * f7, -f8, f5 * f7);
    }

    public final Vector3d getUpVector(float f) {
        return this.calculateUpVector(this.getPitch(f), this.getYaw(f));
    }

    protected final Vector3d calculateUpVector(float f, float f2) {
        return this.getVectorForRotation(f - 90.0f, f2);
    }

    public final Vector3d getEyePosition(float f) {
        if (f == 1.0f) {
            return new Vector3d(this.getPosX(), this.getPosYEye(), this.getPosZ());
        }
        double d = MathHelper.lerp((double)f, this.prevPosX, this.getPosX());
        double d2 = MathHelper.lerp((double)f, this.prevPosY, this.getPosY()) + (double)this.getEyeHeight();
        double d3 = MathHelper.lerp((double)f, this.prevPosZ, this.getPosZ());
        return new Vector3d(d, d2, d3);
    }

    public Vector3d func_241842_k(float f) {
        return this.getEyePosition(f);
    }

    public final Vector3d func_242282_l(float f) {
        double d = MathHelper.lerp((double)f, this.prevPosX, this.getPosX());
        double d2 = MathHelper.lerp((double)f, this.prevPosY, this.getPosY());
        double d3 = MathHelper.lerp((double)f, this.prevPosZ, this.getPosZ());
        return new Vector3d(d, d2, d3);
    }

    public RayTraceResult pick(double d, float f, boolean bl) {
        Vector3d vector3d = this.getEyePosition(f);
        Vector3d vector3d2 = this.getLook(f);
        Vector3d vector3d3 = vector3d.add(vector3d2.x * d, vector3d2.y * d, vector3d2.z * d);
        return this.world.rayTraceBlocks(new RayTraceContext(vector3d, vector3d3, RayTraceContext.BlockMode.OUTLINE, bl ? RayTraceContext.FluidMode.ANY : RayTraceContext.FluidMode.NONE, this));
    }

    public boolean canBeCollidedWith() {
        return true;
    }

    public boolean canBePushed() {
        return true;
    }

    public void awardKillScore(Entity entity2, int n, DamageSource damageSource) {
        if (entity2 instanceof ServerPlayerEntity) {
            CriteriaTriggers.ENTITY_KILLED_PLAYER.trigger((ServerPlayerEntity)entity2, this, damageSource);
        }
    }

    public boolean isInRangeToRender3d(double d, double d2, double d3) {
        double d4 = this.getPosX() - d;
        double d5 = this.getPosY() - d2;
        double d6 = this.getPosZ() - d3;
        double d7 = d4 * d4 + d5 * d5 + d6 * d6;
        return this.isInRangeToRenderDist(d7);
    }

    public boolean isInRangeToRenderDist(double d) {
        double d2 = this.getBoundingBox().getAverageEdgeLength();
        if (Double.isNaN(d2)) {
            d2 = 1.0;
        }
        return d < (d2 = d2 * 64.0 * renderDistanceWeight) * d2;
    }

    public boolean writeUnlessRemoved(CompoundNBT compoundNBT) {
        String string = this.getEntityString();
        if (!this.removed && string != null) {
            compoundNBT.putString("id", string);
            this.writeWithoutTypeId(compoundNBT);
            return false;
        }
        return true;
    }

    public boolean writeUnlessPassenger(CompoundNBT compoundNBT) {
        return this.isPassenger() ? false : this.writeUnlessRemoved(compoundNBT);
    }

    public CompoundNBT writeWithoutTypeId(CompoundNBT compoundNBT) {
        try {
            ListNBT listNBT;
            if (this.ridingEntity != null) {
                compoundNBT.put("Pos", this.newDoubleNBTList(this.ridingEntity.getPosX(), this.getPosY(), this.ridingEntity.getPosZ()));
            } else {
                compoundNBT.put("Pos", this.newDoubleNBTList(this.getPosX(), this.getPosY(), this.getPosZ()));
            }
            Vector3d vector3d = this.getMotion();
            compoundNBT.put("Motion", this.newDoubleNBTList(vector3d.x, vector3d.y, vector3d.z));
            compoundNBT.put("Rotation", this.newFloatNBTList(this.rotationYaw, this.rotationPitch));
            compoundNBT.putFloat("FallDistance", this.fallDistance);
            compoundNBT.putShort("Fire", (short)this.fire);
            compoundNBT.putShort("Air", (short)this.getAir());
            compoundNBT.putBoolean("OnGround", this.onGround);
            compoundNBT.putBoolean("Invulnerable", this.invulnerable);
            compoundNBT.putInt("PortalCooldown", this.field_242273_aw);
            compoundNBT.putUniqueId("UUID", this.getUniqueID());
            ITextComponent iTextComponent = this.getCustomName();
            if (iTextComponent != null) {
                compoundNBT.putString("CustomName", ITextComponent.Serializer.toJson(iTextComponent));
            }
            if (this.isCustomNameVisible()) {
                compoundNBT.putBoolean("CustomNameVisible", this.isCustomNameVisible());
            }
            if (this.isSilent()) {
                compoundNBT.putBoolean("Silent", this.isSilent());
            }
            if (this.hasNoGravity()) {
                compoundNBT.putBoolean("NoGravity", this.hasNoGravity());
            }
            if (this.glowing) {
                compoundNBT.putBoolean("Glowing", this.glowing);
            }
            if (!this.tags.isEmpty()) {
                listNBT = new ListNBT();
                for (String object : this.tags) {
                    listNBT.add(StringNBT.valueOf(object));
                }
                compoundNBT.put("Tags", listNBT);
            }
            this.writeAdditional(compoundNBT);
            if (this.isBeingRidden()) {
                listNBT = new ListNBT();
                for (Entity entity2 : this.getPassengers()) {
                    CompoundNBT compoundNBT2;
                    if (!entity2.writeUnlessRemoved(compoundNBT2 = new CompoundNBT())) continue;
                    listNBT.add(compoundNBT2);
                }
                if (!listNBT.isEmpty()) {
                    compoundNBT.put("Passengers", listNBT);
                }
            }
            return compoundNBT;
        } catch (Throwable throwable) {
            CrashReport crashReport = CrashReport.makeCrashReport(throwable, "Saving entity NBT");
            CrashReportCategory crashReportCategory = crashReport.makeCategory("Entity being saved");
            this.fillCrashReport(crashReportCategory);
            throw new ReportedException(crashReport);
        }
    }

    public void read(CompoundNBT compoundNBT) {
        block11: {
            try {
                ListNBT listNBT = compoundNBT.getList("Pos", 6);
                ListNBT listNBT2 = compoundNBT.getList("Motion", 6);
                ListNBT listNBT3 = compoundNBT.getList("Rotation", 5);
                double d = listNBT2.getDouble(0);
                double d2 = listNBT2.getDouble(1);
                double d3 = listNBT2.getDouble(2);
                this.setMotion(Math.abs(d) > 10.0 ? 0.0 : d, Math.abs(d2) > 10.0 ? 0.0 : d2, Math.abs(d3) > 10.0 ? 0.0 : d3);
                this.forceSetPosition(listNBT.getDouble(0), listNBT.getDouble(1), listNBT.getDouble(2));
                this.rotationYaw = listNBT3.getFloat(0);
                this.rotationPitch = listNBT3.getFloat(1);
                this.prevRotationYaw = this.rotationYaw;
                this.prevRotationPitch = this.rotationPitch;
                this.setRotationYawHead(this.rotationYaw);
                this.setRenderYawOffset(this.rotationYaw);
                this.fallDistance = compoundNBT.getFloat("FallDistance");
                this.fire = compoundNBT.getShort("Fire");
                this.setAir(compoundNBT.getShort("Air"));
                this.onGround = compoundNBT.getBoolean("OnGround");
                this.invulnerable = compoundNBT.getBoolean("Invulnerable");
                this.field_242273_aw = compoundNBT.getInt("PortalCooldown");
                if (compoundNBT.hasUniqueId("UUID")) {
                    this.entityUniqueID = compoundNBT.getUniqueId("UUID");
                    this.cachedUniqueIdString = this.entityUniqueID.toString();
                }
                if (Double.isFinite(this.getPosX()) && Double.isFinite(this.getPosY()) && Double.isFinite(this.getPosZ())) {
                    if (Double.isFinite(this.rotationYaw) && Double.isFinite(this.rotationPitch)) {
                        Object object;
                        this.recenterBoundingBox();
                        this.setRotation(this.rotationYaw, this.rotationPitch);
                        if (compoundNBT.contains("CustomName", 1)) {
                            object = compoundNBT.getString("CustomName");
                            try {
                                this.setCustomName(ITextComponent.Serializer.getComponentFromJson((String)object));
                            } catch (Exception exception) {
                                LOGGER.warn("Failed to parse entity custom name {}", object, (Object)exception);
                            }
                        }
                        this.setCustomNameVisible(compoundNBT.getBoolean("CustomNameVisible"));
                        this.setSilent(compoundNBT.getBoolean("Silent"));
                        this.setNoGravity(compoundNBT.getBoolean("NoGravity"));
                        this.setGlowing(compoundNBT.getBoolean("Glowing"));
                        if (compoundNBT.contains("Tags", 0)) {
                            this.tags.clear();
                            object = compoundNBT.getList("Tags", 8);
                            int n = Math.min(((ListNBT)object).size(), 1024);
                            for (int i = 0; i < n; ++i) {
                                this.tags.add(((ListNBT)object).getString(i));
                            }
                        }
                        this.readAdditional(compoundNBT);
                        if (this.shouldSetPosAfterLoading()) {
                            this.recenterBoundingBox();
                        }
                        break block11;
                    }
                    throw new IllegalStateException("Entity has invalid rotation");
                }
                throw new IllegalStateException("Entity has invalid position");
            } catch (Throwable throwable) {
                CrashReport crashReport = CrashReport.makeCrashReport(throwable, "Loading entity NBT");
                CrashReportCategory crashReportCategory = crashReport.makeCategory("Entity being loaded");
                this.fillCrashReport(crashReportCategory);
                throw new ReportedException(crashReport);
            }
        }
    }

    protected boolean shouldSetPosAfterLoading() {
        return false;
    }

    @Nullable
    protected final String getEntityString() {
        EntityType<?> entityType = this.getType();
        ResourceLocation resourceLocation = EntityType.getKey(entityType);
        return entityType.isSerializable() && resourceLocation != null ? resourceLocation.toString() : null;
    }

    protected abstract void readAdditional(CompoundNBT var1);

    protected abstract void writeAdditional(CompoundNBT var1);

    protected ListNBT newDoubleNBTList(double ... dArray) {
        ListNBT listNBT = new ListNBT();
        for (double d : dArray) {
            listNBT.add(DoubleNBT.valueOf(d));
        }
        return listNBT;
    }

    protected ListNBT newFloatNBTList(float ... fArray) {
        ListNBT listNBT = new ListNBT();
        for (float f : fArray) {
            listNBT.add(FloatNBT.valueOf(f));
        }
        return listNBT;
    }

    @Nullable
    public ItemEntity entityDropItem(IItemProvider iItemProvider) {
        return this.entityDropItem(iItemProvider, 0);
    }

    @Nullable
    public ItemEntity entityDropItem(IItemProvider iItemProvider, int n) {
        return this.entityDropItem(new ItemStack(iItemProvider), (float)n);
    }

    @Nullable
    public ItemEntity entityDropItem(ItemStack itemStack) {
        return this.entityDropItem(itemStack, 0.0f);
    }

    @Nullable
    public ItemEntity entityDropItem(ItemStack itemStack, float f) {
        if (itemStack.isEmpty()) {
            return null;
        }
        if (this.world.isRemote) {
            return null;
        }
        ItemEntity itemEntity = new ItemEntity(this.world, this.getPosX(), this.getPosY() + (double)f, this.getPosZ(), itemStack);
        itemEntity.setDefaultPickupDelay();
        this.world.addEntity(itemEntity);
        return itemEntity;
    }

    public boolean isAlive() {
        return !this.removed;
    }

    public boolean isEntityInsideOpaqueBlock() {
        if (this.noClip) {
            return true;
        }
        float f = 0.1f;
        float f2 = this.size.width * 0.8f;
        AxisAlignedBB axisAlignedBB = AxisAlignedBB.withSizeAtOrigin(f2, 0.1f, f2).offset(this.getPosX(), this.getPosYEye(), this.getPosZ());
        return this.world.func_241457_a_(this, axisAlignedBB, this::lambda$isEntityInsideOpaqueBlock$2).findAny().isPresent();
    }

    public ActionResultType processInitialInteract(PlayerEntity playerEntity, Hand hand) {
        return ActionResultType.PASS;
    }

    public boolean canCollide(Entity entity2) {
        return entity2.func_241845_aY() && !this.isRidingSameEntity(entity2);
    }

    public boolean func_241845_aY() {
        return true;
    }

    public void updateRidden() {
        this.setMotion(Vector3d.ZERO);
        this.tick();
        if (this.isPassenger()) {
            this.getRidingEntity().updatePassenger(this);
        }
    }

    public void updatePassenger(Entity entity2) {
        this.positionRider(entity2, Entity::setPosition);
    }

    private void positionRider(Entity entity2, IMoveCallback iMoveCallback) {
        if (this.isPassenger(entity2)) {
            double d = this.getPosY() + this.getMountedYOffset() + entity2.getYOffset();
            iMoveCallback.accept(entity2, this.getPosX(), d, this.getPosZ());
        }
    }

    public void applyOrientationToEntity(Entity entity2) {
    }

    public double getYOffset() {
        return 0.0;
    }

    public double getMountedYOffset() {
        return (double)this.size.height * 0.75;
    }

    public boolean startRiding(Entity entity2) {
        return this.startRiding(entity2, true);
    }

    public boolean isLiving() {
        return this instanceof LivingEntity;
    }

    public boolean startRiding(Entity entity2, boolean bl) {
        venusfr.getInstance().getEventBus().post(new EventStartRiding(entity2));
        Entity entity3 = entity2;
        while (entity3.ridingEntity != null) {
            if (entity3.ridingEntity == this) {
                return true;
            }
            entity3 = entity3.ridingEntity;
        }
        if (bl || this.canBeRidden(entity2) && entity2.canFitPassenger(this)) {
            if (this.isPassenger()) {
                this.stopRiding();
            }
            this.setPose(Pose.STANDING);
            this.ridingEntity = entity2;
            this.ridingEntity.addPassenger(this);
            return false;
        }
        return true;
    }

    protected boolean canBeRidden(Entity entity2) {
        return !this.isSneaking() && this.rideCooldown <= 0;
    }

    protected boolean isPoseClear(Pose pose) {
        return this.world.hasNoCollisions(this, this.getBoundingBox(pose).shrink(1.0E-7));
    }

    public void removePassengers() {
        for (int i = this.passengers.size() - 1; i >= 0; --i) {
            this.passengers.get(i).stopRiding();
        }
    }

    public void dismount() {
        if (this.ridingEntity != null) {
            Entity entity2 = this.ridingEntity;
            this.ridingEntity = null;
            entity2.removePassenger(this);
        }
    }

    public void stopRiding() {
        this.dismount();
    }

    protected void addPassenger(Entity entity2) {
        if (entity2.getRidingEntity() != this) {
            throw new IllegalStateException("Use x.startRiding(y), not y.addPassenger(x)");
        }
        if (!this.world.isRemote && entity2 instanceof PlayerEntity && !(this.getControllingPassenger() instanceof PlayerEntity)) {
            this.passengers.add(0, entity2);
        } else {
            this.passengers.add(entity2);
        }
    }

    protected void removePassenger(Entity entity2) {
        if (entity2.getRidingEntity() == this) {
            throw new IllegalStateException("Use x.stopRiding(y), not y.removePassenger(x)");
        }
        this.passengers.remove(entity2);
        entity2.rideCooldown = 60;
    }

    protected boolean canFitPassenger(Entity entity2) {
        return this.getPassengers().size() < 1;
    }

    public void setPositionAndRotationDirect(double d, double d2, double d3, float f, float f2, int n, boolean bl) {
        this.setPosition(d, d2, d3);
        this.setRotation(f, f2);
    }

    public void setHeadRotation(float f, int n) {
        this.setRotationYawHead(f);
    }

    public float getCollisionBorderSize() {
        FunctionRegistry functionRegistry = venusfr.getInstance().getFunctionRegistry();
        Hitbox hitbox = functionRegistry.getHitbox();
        if (hitbox.isState() && !((Boolean)hitbox.visible.get()).booleanValue()) {
            return ((Float)hitbox.size.get()).floatValue();
        }
        return 0.0f;
    }

    public Vector3d getLookVec() {
        return this.getVectorForRotation(this.rotationPitch, this.rotationYaw);
    }

    public Vector2f getPitchYaw() {
        return new Vector2f(this.rotationPitch, this.rotationYaw);
    }

    public Vector3d getForward() {
        return Vector3d.fromPitchYaw(this.getPitchYaw());
    }

    public void setPortal(BlockPos blockPos) {
        if (this.func_242280_ah()) {
            this.func_242279_ag();
        } else {
            if (!this.world.isRemote && !blockPos.equals(this.field_242271_ac)) {
                this.field_242271_ac = blockPos.toImmutable();
            }
            this.inPortal = true;
        }
    }

    protected void updatePortal() {
        if (this.world instanceof ServerWorld) {
            int n = this.getMaxInPortalTime();
            ServerWorld serverWorld = (ServerWorld)this.world;
            if (this.inPortal) {
                RegistryKey<World> registryKey;
                MinecraftServer minecraftServer = serverWorld.getServer();
                ServerWorld serverWorld2 = minecraftServer.getWorld(registryKey = this.world.getDimensionKey() == World.THE_NETHER ? World.OVERWORLD : World.THE_NETHER);
                if (serverWorld2 != null && minecraftServer.getAllowNether() && !this.isPassenger() && this.portalCounter++ >= n) {
                    this.world.getProfiler().startSection("portal");
                    this.portalCounter = n;
                    this.func_242279_ag();
                    this.changeDimension(serverWorld2);
                    this.world.getProfiler().endSection();
                }
                this.inPortal = false;
            } else {
                if (this.portalCounter > 0) {
                    this.portalCounter -= 4;
                }
                if (this.portalCounter < 0) {
                    this.portalCounter = 0;
                }
            }
            this.decrementTimeUntilPortal();
        }
    }

    public int getPortalCooldown() {
        return 1;
    }

    public void setVelocity(double d, double d2, double d3) {
        this.setMotion(d, d2, d3);
    }

    public void handleStatusUpdate(byte by) {
        switch (by) {
            case 53: {
                HoneyBlock.entitySlideParticles(this);
            }
        }
    }

    public void performHurtAnimation() {
    }

    public Iterable<ItemStack> getHeldEquipment() {
        return EMPTY_EQUIPMENT;
    }

    public Iterable<ItemStack> getArmorInventoryList() {
        return EMPTY_EQUIPMENT;
    }

    public Iterable<ItemStack> getEquipmentAndArmor() {
        return Iterables.concat(this.getHeldEquipment(), this.getArmorInventoryList());
    }

    public void setItemStackToSlot(EquipmentSlotType equipmentSlotType, ItemStack itemStack) {
    }

    public boolean isBurning() {
        boolean bl = this.world != null && this.world.isRemote;
        return !this.isImmuneToFire() && (this.fire > 0 || bl && this.getFlag(1));
    }

    public boolean isPassenger() {
        return this.getRidingEntity() != null;
    }

    public boolean isBeingRidden() {
        return !this.getPassengers().isEmpty();
    }

    public boolean canBeRiddenInWater() {
        return false;
    }

    public void setSneaking(boolean bl) {
        this.setFlag(1, bl);
    }

    public boolean isSneaking() {
        return this.getFlag(0);
    }

    public boolean isSteppingCarefully() {
        return this.isSneaking();
    }

    public boolean isSuppressingBounce() {
        return this.isSneaking();
    }

    public boolean isDiscrete() {
        return this.isSneaking();
    }

    public boolean isDescending() {
        return this.isSneaking();
    }

    public boolean isCrouching() {
        return this.getPose() == Pose.CROUCHING;
    }

    public boolean isSprinting() {
        return this.getFlag(0);
    }

    public void setSprinting(boolean bl) {
        this.setFlag(3, bl);
    }

    public boolean isSwimming() {
        return this.getFlag(1);
    }

    public boolean isActualySwimming() {
        return this.getPose() == Pose.SWIMMING;
    }

    public boolean isVisuallySwimming() {
        return this.isActualySwimming() && !this.isInWater();
    }

    public void setSwimming(boolean bl) {
        this.setFlag(4, bl);
    }

    public boolean isGlowing() {
        return this.glowing || this.world.isRemote && this.getFlag(1);
    }

    public void setGlowing(boolean bl) {
        this.glowing = bl;
        if (!this.world.isRemote) {
            this.setFlag(6, this.glowing);
        }
    }

    public boolean isInvisible() {
        return this.getFlag(0);
    }

    public boolean isInvisibleToPlayer(PlayerEntity playerEntity) {
        if (playerEntity.isSpectator()) {
            return true;
        }
        Team team = this.getTeam();
        return team != null && playerEntity != null && playerEntity.getTeam() == team && team.getSeeFriendlyInvisiblesEnabled() ? false : this.isInvisible();
    }

    @Nullable
    public Team getTeam() {
        return this.world.getScoreboard().getPlayersTeam(this.getScoreboardName());
    }

    public boolean isOnSameTeam(Entity entity2) {
        return this.isOnScoreboardTeam(entity2.getTeam());
    }

    public boolean isOnScoreboardTeam(Team team) {
        return this.getTeam() != null ? this.getTeam().isSameTeam(team) : false;
    }

    public void setInvisible(boolean bl) {
        this.setFlag(5, bl);
    }

    public boolean getFlag(int n) {
        return (this.dataManager.get(FLAGS) & 1 << n) != 0;
    }

    public void setFlag(int n, boolean bl) {
        byte by = this.dataManager.get(FLAGS);
        if (bl) {
            this.dataManager.set(FLAGS, (byte)(by | 1 << n));
        } else {
            this.dataManager.set(FLAGS, (byte)(by & ~(1 << n)));
        }
    }

    public int getMaxAir() {
        return 1;
    }

    public int getAir() {
        return this.dataManager.get(AIR);
    }

    public void setAir(int n) {
        this.dataManager.set(AIR, n);
    }

    public void func_241841_a(ServerWorld serverWorld, LightningBoltEntity lightningBoltEntity) {
        this.forceFireTicks(this.fire + 1);
        if (this.fire == 0) {
            this.setFire(8);
        }
        this.attackEntityFrom(DamageSource.LIGHTNING_BOLT, 5.0f);
    }

    public void onEnterBubbleColumnWithAirAbove(boolean bl) {
        Vector3d vector3d = this.getMotion();
        double d = bl ? Math.max(-0.9, vector3d.y - 0.03) : Math.min(1.8, vector3d.y + 0.1);
        this.setMotion(vector3d.x, d, vector3d.z);
    }

    public void onEnterBubbleColumn(boolean bl) {
        Vector3d vector3d = this.getMotion();
        double d = bl ? Math.max(-0.3, vector3d.y - 0.03) : Math.min(0.7, vector3d.y + 0.06);
        this.setMotion(vector3d.x, d, vector3d.z);
        this.fallDistance = 0.0f;
    }

    public void func_241847_a(ServerWorld serverWorld, LivingEntity livingEntity) {
    }

    protected void pushOutOfBlocks(double d, double d2, double d3) {
        BlockPos blockPos = new BlockPos(d, d2, d3);
        Vector3d vector3d = new Vector3d(d - (double)blockPos.getX(), d2 - (double)blockPos.getY(), d3 - (double)blockPos.getZ());
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        Direction direction = Direction.UP;
        double d4 = Double.MAX_VALUE;
        for (Direction direction2 : new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST, Direction.UP}) {
            double d5;
            mutable.setAndMove(blockPos, direction2);
            if (this.world.getBlockState(mutable).hasOpaqueCollisionShape(this.world, mutable)) continue;
            double d6 = vector3d.getCoordinate(direction2.getAxis());
            double d7 = d5 = direction2.getAxisDirection() == Direction.AxisDirection.POSITIVE ? 1.0 - d6 : d6;
            if (!(d5 < d4)) continue;
            d4 = d5;
            direction = direction2;
        }
        float f = this.rand.nextFloat() * 0.2f + 0.1f;
        float f2 = direction.getAxisDirection().getOffset();
        Vector3d vector3d2 = this.getMotion().scale(0.75);
        if (direction.getAxis() == Direction.Axis.X) {
            this.setMotion(f2 * f, vector3d2.y, vector3d2.z);
        } else if (direction.getAxis() == Direction.Axis.Y) {
            this.setMotion(vector3d2.x, f2 * f, vector3d2.z);
        } else if (direction.getAxis() == Direction.Axis.Z) {
            this.setMotion(vector3d2.x, vector3d2.y, f2 * f);
        }
    }

    public void setMotionMultiplier(BlockState blockState, Vector3d vector3d) {
        this.fallDistance = 0.0f;
        this.motionMultiplier = vector3d;
    }

    private static ITextComponent func_233573_b_(ITextComponent iTextComponent) {
        IFormattableTextComponent iFormattableTextComponent = iTextComponent.copyRaw().setStyle(iTextComponent.getStyle().setClickEvent(null));
        for (ITextComponent iTextComponent2 : iTextComponent.getSiblings()) {
            iFormattableTextComponent.append(Entity.func_233573_b_(iTextComponent2));
        }
        return iFormattableTextComponent;
    }

    @Override
    public ITextComponent getName() {
        ITextComponent iTextComponent = this.getCustomName();
        return iTextComponent != null ? Entity.func_233573_b_(iTextComponent) : this.getProfessionName();
    }

    protected ITextComponent getProfessionName() {
        return this.type.getName();
    }

    public boolean isEntityEqual(Entity entity2) {
        return this == entity2;
    }

    public float getRotationYawHead() {
        return 0.0f;
    }

    public void setRotationYawHead(float f) {
    }

    public void setRenderYawOffset(float f) {
    }

    public boolean canBeAttackedWithItem() {
        return false;
    }

    public boolean hitByEntity(Entity entity2) {
        return true;
    }

    public String toString() {
        return String.format(Locale.ROOT, "%s['%s'/%d, l='%s', x=%.2f, y=%.2f, z=%.2f]", this.getClass().getSimpleName(), this.getName().getString(), this.entityId, this.world == null ? "~NULL~" : this.world.toString(), this.getPosX(), this.getPosY(), this.getPosZ());
    }

    public boolean isInvulnerableTo(DamageSource damageSource) {
        return this.invulnerable && damageSource != DamageSource.OUT_OF_WORLD && !damageSource.isCreativePlayer();
    }

    public boolean isInvulnerable() {
        return this.invulnerable;
    }

    public void setInvulnerable(boolean bl) {
        this.invulnerable = bl;
    }

    public void copyLocationAndAnglesFrom(Entity entity2) {
        this.setLocationAndAngles(entity2.getPosX(), entity2.getPosY(), entity2.getPosZ(), entity2.rotationYaw, entity2.rotationPitch);
    }

    public void copyDataFromOld(Entity entity2) {
        CompoundNBT compoundNBT = entity2.writeWithoutTypeId(new CompoundNBT());
        compoundNBT.remove("Dimension");
        this.read(compoundNBT);
        this.field_242273_aw = entity2.field_242273_aw;
        this.field_242271_ac = entity2.field_242271_ac;
    }

    @Nullable
    public Entity changeDimension(ServerWorld serverWorld) {
        if (this.world instanceof ServerWorld && !this.removed) {
            this.world.getProfiler().startSection("changeDimension");
            this.detach();
            this.world.getProfiler().startSection("reposition");
            PortalInfo portalInfo = this.func_241829_a(serverWorld);
            if (portalInfo == null) {
                return null;
            }
            this.world.getProfiler().endStartSection("reloading");
            Object obj = this.getType().create(serverWorld);
            if (obj != null) {
                ((Entity)obj).copyDataFromOld(this);
                ((Entity)obj).setLocationAndAngles(portalInfo.pos.x, portalInfo.pos.y, portalInfo.pos.z, portalInfo.rotationYaw, ((Entity)obj).rotationPitch);
                ((Entity)obj).setMotion(portalInfo.motion);
                serverWorld.addFromAnotherDimension((Entity)obj);
                if (serverWorld.getDimensionKey() == World.THE_END) {
                    ServerWorld.func_241121_a_(serverWorld);
                }
            }
            this.setDead();
            this.world.getProfiler().endSection();
            ((ServerWorld)this.world).resetUpdateEntityTick();
            serverWorld.resetUpdateEntityTick();
            this.world.getProfiler().endSection();
            return obj;
        }
        return null;
    }

    protected void setDead() {
        this.removed = true;
    }

    @Nullable
    protected PortalInfo func_241829_a(ServerWorld serverWorld) {
        boolean bl;
        boolean bl2 = this.world.getDimensionKey() == World.THE_END && serverWorld.getDimensionKey() == World.OVERWORLD;
        boolean bl3 = bl = serverWorld.getDimensionKey() == World.THE_END;
        if (!bl2 && !bl) {
            boolean bl4;
            boolean bl5 = bl4 = serverWorld.getDimensionKey() == World.THE_NETHER;
            if (this.world.getDimensionKey() != World.THE_NETHER && !bl4) {
                return null;
            }
            WorldBorder worldBorder = serverWorld.getWorldBorder();
            double d = Math.max(-2.9999872E7, worldBorder.minX() + 16.0);
            double d2 = Math.max(-2.9999872E7, worldBorder.minZ() + 16.0);
            double d3 = Math.min(2.9999872E7, worldBorder.maxX() - 16.0);
            double d4 = Math.min(2.9999872E7, worldBorder.maxZ() - 16.0);
            double d5 = DimensionType.getCoordinateDifference(this.world.getDimensionType(), serverWorld.getDimensionType());
            BlockPos blockPos = new BlockPos(MathHelper.clamp(this.getPosX() * d5, d, d3), this.getPosY(), MathHelper.clamp(this.getPosZ() * d5, d2, d4));
            return this.func_241830_a(serverWorld, blockPos, bl4).map(arg_0 -> this.lambda$func_241829_a$4(serverWorld, arg_0)).orElse(null);
        }
        BlockPos blockPos = bl ? ServerWorld.field_241108_a_ : serverWorld.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, serverWorld.getSpawnPoint());
        return new PortalInfo(new Vector3d((double)blockPos.getX() + 0.5, blockPos.getY(), (double)blockPos.getZ() + 0.5), this.getMotion(), this.rotationYaw, this.rotationPitch);
    }

    protected Vector3d func_241839_a(Direction.Axis axis, TeleportationRepositioner.Result result) {
        return PortalSize.func_242973_a(result, axis, this.getPositionVec(), this.getSize(this.getPose()));
    }

    protected Optional<TeleportationRepositioner.Result> func_241830_a(ServerWorld serverWorld, BlockPos blockPos, boolean bl) {
        return serverWorld.getDefaultTeleporter().getExistingPortal(blockPos, bl);
    }

    public boolean isNonBoss() {
        return false;
    }

    public float getExplosionResistance(Explosion explosion, IBlockReader iBlockReader, BlockPos blockPos, BlockState blockState, FluidState fluidState, float f) {
        return f;
    }

    public boolean canExplosionDestroyBlock(Explosion explosion, IBlockReader iBlockReader, BlockPos blockPos, BlockState blockState, float f) {
        return false;
    }

    public int getMaxFallHeight() {
        return 0;
    }

    public boolean doesEntityNotTriggerPressurePlate() {
        return true;
    }

    public void fillCrashReport(CrashReportCategory crashReportCategory) {
        crashReportCategory.addDetail("Entity Type", this::lambda$fillCrashReport$5);
        crashReportCategory.addDetail("Entity ID", this.entityId);
        crashReportCategory.addDetail("Entity Name", this::lambda$fillCrashReport$6);
        crashReportCategory.addDetail("Entity's Exact location", String.format(Locale.ROOT, "%.2f, %.2f, %.2f", this.getPosX(), this.getPosY(), this.getPosZ()));
        crashReportCategory.addDetail("Entity's Block location", CrashReportCategory.getCoordinateInfo(MathHelper.floor(this.getPosX()), MathHelper.floor(this.getPosY()), MathHelper.floor(this.getPosZ())));
        Vector3d vector3d = this.getMotion();
        crashReportCategory.addDetail("Entity's Momentum", String.format(Locale.ROOT, "%.2f, %.2f, %.2f", vector3d.x, vector3d.y, vector3d.z));
        crashReportCategory.addDetail("Entity's Passengers", this::lambda$fillCrashReport$7);
        crashReportCategory.addDetail("Entity's Vehicle", this::lambda$fillCrashReport$8);
    }

    public boolean canRenderOnFire() {
        return this.isBurning() && !this.isSpectator();
    }

    public void setUniqueId(UUID uUID) {
        this.entityUniqueID = uUID;
        this.cachedUniqueIdString = this.entityUniqueID.toString();
    }

    public UUID getUniqueID() {
        return this.entityUniqueID;
    }

    public String getCachedUniqueIdString() {
        return this.cachedUniqueIdString;
    }

    public String getScoreboardName() {
        return this.cachedUniqueIdString;
    }

    public boolean isPushedByWater() {
        return false;
    }

    public static double getRenderDistanceWeight() {
        return renderDistanceWeight;
    }

    public static void setRenderDistanceWeight(double d) {
        renderDistanceWeight = d;
    }

    @Override
    public ITextComponent getDisplayName() {
        return ScorePlayerTeam.formatPlayerName(this.getTeam(), this.getName()).modifyStyle(this::lambda$getDisplayName$9);
    }

    public void setCustomName(@Nullable ITextComponent iTextComponent) {
        this.dataManager.set(CUSTOM_NAME, Optional.ofNullable(iTextComponent));
    }

    @Override
    @Nullable
    public ITextComponent getCustomName() {
        return this.dataManager.get(CUSTOM_NAME).orElse(null);
    }

    @Override
    public boolean hasCustomName() {
        return this.dataManager.get(CUSTOM_NAME).isPresent();
    }

    public void setCustomNameVisible(boolean bl) {
        this.dataManager.set(CUSTOM_NAME_VISIBLE, bl);
    }

    public boolean isCustomNameVisible() {
        return this.dataManager.get(CUSTOM_NAME_VISIBLE);
    }

    public final void teleportKeepLoaded(double d, double d2, double d3) {
        if (this.world instanceof ServerWorld) {
            ChunkPos chunkPos = new ChunkPos(new BlockPos(d, d2, d3));
            ((ServerWorld)this.world).getChunkProvider().registerTicket(TicketType.POST_TELEPORT, chunkPos, 0, this.getEntityId());
            this.world.getChunk(chunkPos.x, chunkPos.z);
            this.setPositionAndUpdate(d, d2, d3);
        }
    }

    public void setPositionAndUpdate(double d, double d2, double d3) {
        if (this.world instanceof ServerWorld) {
            ServerWorld serverWorld = (ServerWorld)this.world;
            this.setLocationAndAngles(d, d2, d3, this.rotationYaw, this.rotationPitch);
            this.getSelfAndPassengers().forEach(arg_0 -> Entity.lambda$setPositionAndUpdate$10(serverWorld, arg_0));
        }
    }

    public boolean getAlwaysRenderNameTagForRender() {
        return this.isCustomNameVisible();
    }

    public void notifyDataManagerChange(DataParameter<?> dataParameter) {
        if (POSE.equals(dataParameter)) {
            this.recalculateSize();
        }
    }

    public void recalculateSize() {
        EntitySize entitySize;
        EntitySize entitySize2 = this.size;
        Pose pose = this.getPose();
        this.size = entitySize = this.getSize(pose);
        this.eyeHeight = this.getEyeHeight(pose, entitySize);
        if (entitySize.width < entitySize2.width) {
            double d = (double)entitySize.width / 2.0;
            this.setBoundingBox(new AxisAlignedBB(this.getPosX() - d, this.getPosY(), this.getPosZ() - d, this.getPosX() + d, this.getPosY() + (double)entitySize.height, this.getPosZ() + d));
        } else {
            AxisAlignedBB axisAlignedBB = this.getBoundingBox();
            this.setBoundingBox(new AxisAlignedBB(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ, axisAlignedBB.minX + (double)entitySize.width, axisAlignedBB.minY + (double)entitySize.height, axisAlignedBB.minZ + (double)entitySize.width));
            if (entitySize.width > entitySize2.width && !this.firstUpdate && !this.world.isRemote) {
                float f = entitySize2.width - entitySize.width;
                this.move(MoverType.SELF, new Vector3d(f, 0.0, f));
            }
        }
    }

    public Direction getHorizontalFacing() {
        return Direction.fromAngle(this.rotationYaw);
    }

    public Direction getAdjustedHorizontalFacing() {
        return this.getHorizontalFacing();
    }

    protected HoverEvent getHoverEvent() {
        return new HoverEvent(HoverEvent.Action.SHOW_ENTITY, new HoverEvent.EntityHover(this.getType(), this.getUniqueID(), this.getName()));
    }

    public boolean isSpectatedByPlayer(ServerPlayerEntity serverPlayerEntity) {
        return false;
    }

    public AxisAlignedBB getBoundingBox() {
        return this.boundingBox;
    }

    public AxisAlignedBB getRenderBoundingBox() {
        return this.getBoundingBox();
    }

    protected AxisAlignedBB getBoundingBox(Pose pose) {
        EntitySize entitySize = this.getSize(pose);
        float f = entitySize.width / 2.0f;
        Vector3d vector3d = new Vector3d(this.getPosX() - (double)f, this.getPosY(), this.getPosZ() - (double)f);
        Vector3d vector3d2 = new Vector3d(this.getPosX() + (double)f, this.getPosY() + (double)entitySize.height, this.getPosZ() + (double)f);
        return new AxisAlignedBB(vector3d, vector3d2);
    }

    protected AxisAlignedBB getBoundingBox(float f) {
        Vector3d vector3d = new Vector3d(this.getPosX() - (double)f, this.getPosY(), this.getPosZ() - (double)f);
        Vector3d vector3d2 = new Vector3d(this.getPosX() + (double)f, this.getPosY() + (double)this.getHeight(), this.getPosZ() + (double)f);
        return new AxisAlignedBB(vector3d, vector3d2);
    }

    public void setBoundingBox(AxisAlignedBB axisAlignedBB) {
        this.boundingBox = axisAlignedBB;
    }

    protected float getEyeHeight(Pose pose, EntitySize entitySize) {
        return entitySize.height * 0.85f;
    }

    public float getEyeHeight(Pose pose) {
        return this.getEyeHeight(pose, this.getSize(pose));
    }

    public final float getEyeHeight() {
        return this.eyeHeight;
    }

    public Vector3d func_241205_ce_() {
        return new Vector3d(0.0, this.getEyeHeight(), this.getWidth() * 0.4f);
    }

    public boolean replaceItemInInventory(int n, ItemStack itemStack) {
        return true;
    }

    @Override
    public void sendMessage(ITextComponent iTextComponent, UUID uUID) {
    }

    public World getEntityWorld() {
        return this.world;
    }

    @Nullable
    public MinecraftServer getServer() {
        return this.world.getServer();
    }

    public ActionResultType applyPlayerInteraction(PlayerEntity playerEntity, Vector3d vector3d, Hand hand) {
        return ActionResultType.PASS;
    }

    public boolean isImmuneToExplosions() {
        return true;
    }

    public void applyEnchantments(LivingEntity livingEntity, Entity entity2) {
        if (entity2 instanceof LivingEntity) {
            EnchantmentHelper.applyThornEnchantments((LivingEntity)entity2, livingEntity);
        }
        EnchantmentHelper.applyArthropodEnchantments(livingEntity, entity2);
    }

    public void addTrackingPlayer(ServerPlayerEntity serverPlayerEntity) {
    }

    public void removeTrackingPlayer(ServerPlayerEntity serverPlayerEntity) {
    }

    public float getRotatedYaw(Rotation rotation) {
        float f = MathHelper.wrapDegrees(this.rotationYaw);
        switch (rotation) {
            case CLOCKWISE_180: {
                return f + 180.0f;
            }
            case COUNTERCLOCKWISE_90: {
                return f + 270.0f;
            }
            case CLOCKWISE_90: {
                return f + 90.0f;
            }
        }
        return f;
    }

    public float getMirroredYaw(Mirror mirror) {
        float f = MathHelper.wrapDegrees(this.rotationYaw);
        switch (mirror) {
            case LEFT_RIGHT: {
                return -f;
            }
            case FRONT_BACK: {
                return 180.0f - f;
            }
        }
        return f;
    }

    public boolean ignoreItemEntityData() {
        return true;
    }

    public boolean func_233577_ch_() {
        boolean bl = this.isPositionDirty;
        this.isPositionDirty = false;
        return bl;
    }

    public boolean func_233578_ci_() {
        boolean bl = this.isLoaded;
        this.isLoaded = false;
        return bl;
    }

    @Nullable
    public Entity getControllingPassenger() {
        return null;
    }

    public List<Entity> getPassengers() {
        return this.passengers.isEmpty() ? Collections.emptyList() : Lists.newArrayList(this.passengers);
    }

    public boolean isPassenger(Entity entity2) {
        for (Entity entity3 : this.getPassengers()) {
            if (!entity3.equals(entity2)) continue;
            return false;
        }
        return true;
    }

    public boolean isPassenger(Class<? extends Entity> clazz) {
        for (Entity entity2 : this.getPassengers()) {
            if (!clazz.isAssignableFrom(entity2.getClass())) continue;
            return false;
        }
        return true;
    }

    public Collection<Entity> getRecursivePassengers() {
        HashSet<Entity> hashSet = Sets.newHashSet();
        for (Entity entity2 : this.getPassengers()) {
            hashSet.add(entity2);
            entity2.getRecursivePassengers(false, hashSet);
        }
        return hashSet;
    }

    public Stream<Entity> getSelfAndPassengers() {
        return Stream.concat(Stream.of(this), this.passengers.stream().flatMap(Entity::getSelfAndPassengers));
    }

    public boolean isOnePlayerRiding() {
        HashSet<Entity> hashSet = Sets.newHashSet();
        this.getRecursivePassengers(true, hashSet);
        return hashSet.size() == 1;
    }

    private void getRecursivePassengers(boolean bl, Set<Entity> set) {
        for (Entity entity2 : this.getPassengers()) {
            if (!bl || ServerPlayerEntity.class.isAssignableFrom(entity2.getClass())) {
                set.add(entity2);
            }
            entity2.getRecursivePassengers(bl, set);
        }
    }

    public Entity getLowestRidingEntity() {
        Entity entity2 = this;
        while (entity2.isPassenger()) {
            entity2 = entity2.getRidingEntity();
        }
        return entity2;
    }

    public boolean isRidingSameEntity(Entity entity2) {
        return this.getLowestRidingEntity() == entity2.getLowestRidingEntity();
    }

    public boolean isRidingOrBeingRiddenBy(Entity entity2) {
        for (Entity entity3 : this.getPassengers()) {
            if (entity3.equals(entity2)) {
                return false;
            }
            if (!entity3.isRidingOrBeingRiddenBy(entity2)) continue;
            return false;
        }
        return true;
    }

    public boolean canPassengerSteer() {
        Entity entity2 = this.getControllingPassenger();
        if (entity2 instanceof PlayerEntity) {
            return ((PlayerEntity)entity2).isUser();
        }
        return !this.world.isRemote;
    }

    protected static Vector3d func_233559_a_(double d, double d2, float f) {
        double d3 = (d + d2 + (double)1.0E-5f) / 2.0;
        float f2 = -MathHelper.sin(f * ((float)Math.PI / 180));
        float f3 = MathHelper.cos(f * ((float)Math.PI / 180));
        float f4 = Math.max(Math.abs(f2), Math.abs(f3));
        return new Vector3d((double)f2 * d3 / (double)f4, 0.0, (double)f3 * d3 / (double)f4);
    }

    public Vector3d func_230268_c_(LivingEntity livingEntity) {
        return new Vector3d(this.getPosX(), this.getBoundingBox().maxY, this.getPosZ());
    }

    @Nullable
    public Entity getRidingEntity() {
        return this.ridingEntity;
    }

    public PushReaction getPushReaction() {
        return PushReaction.NORMAL;
    }

    public SoundCategory getSoundCategory() {
        return SoundCategory.NEUTRAL;
    }

    protected int getFireImmuneTicks() {
        return 0;
    }

    public CommandSource getCommandSource() {
        return new CommandSource(this, this.getPositionVec(), this.getPitchYaw(), this.world instanceof ServerWorld ? (ServerWorld)this.world : null, this.getPermissionLevel(), this.getName().getString(), this.getDisplayName(), this.world.getServer(), this);
    }

    protected int getPermissionLevel() {
        return 1;
    }

    public boolean hasPermissionLevel(int n) {
        return this.getPermissionLevel() >= n;
    }

    @Override
    public boolean shouldReceiveFeedback() {
        return this.world.getGameRules().getBoolean(GameRules.SEND_COMMAND_FEEDBACK);
    }

    @Override
    public boolean shouldReceiveErrors() {
        return false;
    }

    @Override
    public boolean allowLogging() {
        return false;
    }

    public void lookAt(EntityAnchorArgument.Type type, Vector3d vector3d) {
        Vector3d vector3d2 = type.apply(this);
        double d = vector3d.x - vector3d2.x;
        double d2 = vector3d.y - vector3d2.y;
        double d3 = vector3d.z - vector3d2.z;
        double d4 = MathHelper.sqrt(d * d + d3 * d3);
        this.rotationPitch = MathHelper.wrapDegrees((float)(-(MathHelper.atan2(d2, d4) * 57.2957763671875)));
        this.rotationYaw = MathHelper.wrapDegrees((float)(MathHelper.atan2(d3, d) * 57.2957763671875) - 90.0f);
        this.setRotationYawHead(this.rotationYaw);
        this.prevRotationPitch = this.rotationPitch;
        this.prevRotationYaw = this.rotationYaw;
    }

    public boolean handleFluidAcceleration(ITag<Fluid> iTag, double d) {
        int n;
        AxisAlignedBB axisAlignedBB = this.getBoundingBox().shrink(0.001);
        int n2 = MathHelper.floor(axisAlignedBB.minX);
        int n3 = MathHelper.ceil(axisAlignedBB.maxX);
        int n4 = MathHelper.floor(axisAlignedBB.minY);
        int n5 = MathHelper.ceil(axisAlignedBB.maxY);
        int n6 = MathHelper.floor(axisAlignedBB.minZ);
        if (!this.world.isAreaLoaded(n2, n4, n6, n3, n5, n = MathHelper.ceil(axisAlignedBB.maxZ))) {
            return true;
        }
        double d2 = 0.0;
        boolean bl = this.isPushedByWater();
        boolean bl2 = false;
        Vector3d vector3d = Vector3d.ZERO;
        int n7 = 0;
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (int i = n2; i < n3; ++i) {
            for (int j = n4; j < n5; ++j) {
                for (int k = n6; k < n; ++k) {
                    double d3;
                    mutable.setPos(i, j, k);
                    FluidState fluidState = this.world.getFluidState(mutable);
                    if (!fluidState.isTagged(iTag) || !((d3 = (double)((float)j + fluidState.getActualHeight(this.world, mutable))) >= axisAlignedBB.minY)) continue;
                    bl2 = true;
                    d2 = Math.max(d3 - axisAlignedBB.minY, d2);
                    if (!bl) continue;
                    Vector3d vector3d2 = fluidState.getFlow(this.world, mutable);
                    if (d2 < 0.4) {
                        vector3d2 = vector3d2.scale(d2);
                    }
                    vector3d = vector3d.add(vector3d2);
                    ++n7;
                }
            }
        }
        if (vector3d.length() > 0.0) {
            if (n7 > 0) {
                vector3d = vector3d.scale(1.0 / (double)n7);
            }
            if (!(this instanceof PlayerEntity)) {
                vector3d = vector3d.normalize();
            }
            Vector3d vector3d3 = this.getMotion();
            vector3d = vector3d.scale(d * 1.0);
            double d4 = 0.003;
            if (Math.abs(vector3d3.x) < 0.003 && Math.abs(vector3d3.z) < 0.003 && vector3d.length() < 0.0045000000000000005) {
                vector3d = vector3d.normalize().scale(0.0045000000000000005);
            }
            this.setMotion(this.getMotion().add(vector3d));
        }
        this.eyesFluidLevel.put(iTag, d2);
        return bl2;
    }

    public double func_233571_b_(ITag<Fluid> iTag) {
        return this.eyesFluidLevel.getDouble(iTag);
    }

    public double func_233579_cu_() {
        return (double)this.getEyeHeight() < 0.4 ? 0.0 : 0.4;
    }

    public final float getWidth() {
        return this.size.width;
    }

    public final float getHeight() {
        return this.size.height;
    }

    public abstract IPacket<?> createSpawnPacket();

    public EntitySize getSize(Pose pose) {
        return this.type.getSize();
    }

    public Vector3d getPositionVec() {
        return this.positionVec;
    }

    public BlockPos getPosition() {
        return this.position;
    }

    public Vector3d getMotion() {
        return this.motion;
    }

    public void setMotion(Vector3d vector3d) {
        this.motion = vector3d;
    }

    public void setMotion(double d, double d2, double d3) {
        this.setMotion(new Vector3d(d, d2, d3));
    }

    public final double getPosX() {
        return this.positionVec.x;
    }

    public double getPosXWidth(double d) {
        return this.positionVec.x + (double)this.getWidth() * d;
    }

    public double getPosXRandom(double d) {
        return this.getPosXWidth((2.0 * this.rand.nextDouble() - 1.0) * d);
    }

    public final double getPosY() {
        return this.positionVec.y;
    }

    public double getPosYHeight(double d) {
        return this.positionVec.y + (double)this.getHeight() * d;
    }

    public double getPosYRandom() {
        return this.getPosYHeight(this.rand.nextDouble());
    }

    public double getPosYEye() {
        return this.positionVec.y + (double)this.eyeHeight;
    }

    public final double getPosZ() {
        return this.positionVec.z;
    }

    public double getPosZWidth(double d) {
        return this.positionVec.z + (double)this.getWidth() * d;
    }

    public double getPosZRandom(double d) {
        return this.getPosZWidth((2.0 * this.rand.nextDouble() - 1.0) * d);
    }

    public void setRawPosition(double d, double d2, double d3) {
        if (this.positionVec.x != d || this.positionVec.y != d2 || this.positionVec.z != d3) {
            this.positionVec = new Vector3d(d, d2, d3);
            int n = MathHelper.floor(d);
            int n2 = MathHelper.floor(d2);
            int n3 = MathHelper.floor(d3);
            if (n != this.position.getX() || n2 != this.position.getY() || n3 != this.position.getZ()) {
                this.position = new BlockPos(n, n2, n3);
            }
            this.isLoaded = true;
        }
    }

    public void checkDespawn() {
    }

    public Vector3d getLeashPosition(float f) {
        return this.func_242282_l(f).add(0.0, (double)this.eyeHeight * 0.7, 0.0);
    }

    public String getStringPosition() {
        return "X: " + (int)this.getPosX() + " Y: " + (int)this.getPosY() + " Z: " + (int)this.getPosZ();
    }

    public EntityClass getLuaClass() {
        return this.luaClass;
    }

    private static void lambda$setPositionAndUpdate$10(ServerWorld serverWorld, Entity entity2) {
        serverWorld.chunkCheck(entity2);
        entity2.isPositionDirty = true;
        for (Entity entity3 : entity2.passengers) {
            entity2.positionRider(entity3, Entity::moveForced);
        }
    }

    private Style lambda$getDisplayName$9(Style style) {
        return style.setHoverEvent(this.getHoverEvent()).setInsertion(this.getCachedUniqueIdString());
    }

    private String lambda$fillCrashReport$8() throws Exception {
        return this.getRidingEntity().toString();
    }

    private String lambda$fillCrashReport$7() throws Exception {
        return this.getPassengers().toString();
    }

    private String lambda$fillCrashReport$6() throws Exception {
        return this.getName().getString();
    }

    private String lambda$fillCrashReport$5() throws Exception {
        return EntityType.getKey(this.getType()) + " (" + this.getClass().getCanonicalName() + ")";
    }

    private PortalInfo lambda$func_241829_a$4(ServerWorld serverWorld, TeleportationRepositioner.Result result) {
        Vector3d vector3d;
        Direction.Axis axis;
        BlockState blockState = this.world.getBlockState(this.field_242271_ac);
        if (blockState.hasProperty(BlockStateProperties.HORIZONTAL_AXIS)) {
            axis = blockState.get(BlockStateProperties.HORIZONTAL_AXIS);
            TeleportationRepositioner.Result result2 = TeleportationRepositioner.findLargestRectangle(this.field_242271_ac, axis, 21, Direction.Axis.Y, 21, arg_0 -> this.lambda$func_241829_a$3(blockState, arg_0));
            vector3d = this.func_241839_a(axis, result2);
        } else {
            axis = Direction.Axis.X;
            vector3d = new Vector3d(0.5, 0.0, 0.0);
        }
        return PortalSize.func_242963_a(serverWorld, result, axis, vector3d, this.getSize(this.getPose()), this.getMotion(), this.rotationYaw, this.rotationPitch);
    }

    private boolean lambda$func_241829_a$3(BlockState blockState, BlockPos blockPos) {
        return this.world.getBlockState(blockPos) == blockState;
    }

    private boolean lambda$isEntityInsideOpaqueBlock$2(BlockState blockState, BlockPos blockPos) {
        return blockState.isSuffocating(this.world, blockPos);
    }

    private static boolean lambda$getAllowedMovement$1(Entity entity2) {
        return false;
    }

    private static boolean lambda$move$0(BlockState blockState) {
        return blockState.isIn(BlockTags.FIRE) || blockState.isIn(Blocks.LAVA);
    }

    @FunctionalInterface
    public static interface IMoveCallback {
        public void accept(Entity var1, double var2, double var4, double var6);
    }
}

