/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.projectile;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SSpawnObjectPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class FishingBobberEntity
extends ProjectileEntity {
    private final Random field_234596_b_ = new Random();
    private boolean field_234597_c_;
    private int field_234598_d_;
    private static final DataParameter<Integer> DATA_HOOKED_ENTITY = EntityDataManager.createKey(FishingBobberEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> field_234599_f_ = EntityDataManager.createKey(FishingBobberEntity.class, DataSerializers.BOOLEAN);
    private int ticksInGround;
    private int ticksCatchable;
    private int ticksCaughtDelay;
    private int ticksCatchableDelay;
    private float fishApproachAngle;
    private boolean field_234595_aq_ = true;
    private Entity caughtEntity;
    private State currentState = State.FLYING;
    private final int luck;
    private final int lureSpeed;

    private FishingBobberEntity(World world, PlayerEntity playerEntity, int n, int n2) {
        super((EntityType<? extends ProjectileEntity>)EntityType.FISHING_BOBBER, world);
        this.ignoreFrustumCheck = true;
        this.setShooter(playerEntity);
        playerEntity.fishingBobber = this;
        this.luck = Math.max(0, n);
        this.lureSpeed = Math.max(0, n2);
    }

    public FishingBobberEntity(World world, PlayerEntity playerEntity, double d, double d2, double d3) {
        this(world, playerEntity, 0, 0);
        this.setPosition(d, d2, d3);
        this.prevPosX = this.getPosX();
        this.prevPosY = this.getPosY();
        this.prevPosZ = this.getPosZ();
    }

    public FishingBobberEntity(PlayerEntity playerEntity, World world, int n, int n2) {
        this(world, playerEntity, n, n2);
        float f = playerEntity.rotationPitch;
        float f2 = playerEntity.rotationYaw;
        float f3 = MathHelper.cos(-f2 * ((float)Math.PI / 180) - (float)Math.PI);
        float f4 = MathHelper.sin(-f2 * ((float)Math.PI / 180) - (float)Math.PI);
        float f5 = -MathHelper.cos(-f * ((float)Math.PI / 180));
        float f6 = MathHelper.sin(-f * ((float)Math.PI / 180));
        double d = playerEntity.getPosX() - (double)f4 * 0.3;
        double d2 = playerEntity.getPosYEye();
        double d3 = playerEntity.getPosZ() - (double)f3 * 0.3;
        this.setLocationAndAngles(d, d2, d3, f2, f);
        Vector3d vector3d = new Vector3d(-f4, MathHelper.clamp(-(f6 / f5), -5.0f, 5.0f), -f3);
        double d4 = vector3d.length();
        vector3d = vector3d.mul(0.6 / d4 + 0.5 + this.rand.nextGaussian() * 0.0045, 0.6 / d4 + 0.5 + this.rand.nextGaussian() * 0.0045, 0.6 / d4 + 0.5 + this.rand.nextGaussian() * 0.0045);
        this.setMotion(vector3d);
        this.rotationYaw = (float)(MathHelper.atan2(vector3d.x, vector3d.z) * 57.2957763671875);
        this.rotationPitch = (float)(MathHelper.atan2(vector3d.y, MathHelper.sqrt(FishingBobberEntity.horizontalMag(vector3d))) * 57.2957763671875);
        this.prevRotationYaw = this.rotationYaw;
        this.prevRotationPitch = this.rotationPitch;
    }

    @Override
    protected void registerData() {
        this.getDataManager().register(DATA_HOOKED_ENTITY, 0);
        this.getDataManager().register(field_234599_f_, false);
    }

    @Override
    public void notifyDataManagerChange(DataParameter<?> dataParameter) {
        if (DATA_HOOKED_ENTITY.equals(dataParameter)) {
            int n = this.getDataManager().get(DATA_HOOKED_ENTITY);
            Entity entity2 = this.caughtEntity = n > 0 ? this.world.getEntityByID(n - 1) : null;
        }
        if (field_234599_f_.equals(dataParameter)) {
            this.field_234597_c_ = this.getDataManager().get(field_234599_f_);
            if (this.field_234597_c_) {
                this.setMotion(this.getMotion().x, -0.4f * MathHelper.nextFloat(this.field_234596_b_, 0.6f, 1.0f), this.getMotion().z);
            }
        }
        super.notifyDataManagerChange(dataParameter);
    }

    @Override
    public boolean isInRangeToRenderDist(double d) {
        double d2 = 64.0;
        return d < 4096.0;
    }

    @Override
    public void setPositionAndRotationDirect(double d, double d2, double d3, float f, float f2, int n, boolean bl) {
    }

    @Override
    public void tick() {
        this.field_234596_b_.setSeed(this.getUniqueID().getLeastSignificantBits() ^ this.world.getGameTime());
        super.tick();
        PlayerEntity playerEntity = this.func_234606_i_();
        if (playerEntity == null) {
            this.remove();
        } else if (this.world.isRemote || !this.func_234600_a_(playerEntity)) {
            boolean bl;
            if (this.onGround) {
                ++this.ticksInGround;
                if (this.ticksInGround >= 1200) {
                    this.remove();
                    return;
                }
            } else {
                this.ticksInGround = 0;
            }
            float f = 0.0f;
            BlockPos blockPos = this.getPosition();
            FluidState fluidState = this.world.getFluidState(blockPos);
            if (fluidState.isTagged(FluidTags.WATER)) {
                f = fluidState.getActualHeight(this.world, blockPos);
            }
            boolean bl2 = bl = f > 0.0f;
            if (this.currentState == State.FLYING) {
                if (this.caughtEntity != null) {
                    this.setMotion(Vector3d.ZERO);
                    this.currentState = State.HOOKED_IN_ENTITY;
                    return;
                }
                if (bl) {
                    this.setMotion(this.getMotion().mul(0.3, 0.2, 0.3));
                    this.currentState = State.BOBBING;
                    return;
                }
                this.checkCollision();
            } else {
                if (this.currentState == State.HOOKED_IN_ENTITY) {
                    if (this.caughtEntity != null) {
                        if (this.caughtEntity.removed) {
                            this.caughtEntity = null;
                            this.currentState = State.FLYING;
                        } else {
                            this.setPosition(this.caughtEntity.getPosX(), this.caughtEntity.getPosYHeight(0.8), this.caughtEntity.getPosZ());
                        }
                    }
                    return;
                }
                if (this.currentState == State.BOBBING) {
                    Vector3d vector3d = this.getMotion();
                    double d = this.getPosY() + vector3d.y - (double)blockPos.getY() - (double)f;
                    if (Math.abs(d) < 0.01) {
                        d += Math.signum(d) * 0.1;
                    }
                    this.setMotion(vector3d.x * 0.9, vector3d.y - d * (double)this.rand.nextFloat() * 0.2, vector3d.z * 0.9);
                    if (this.ticksCatchable <= 0 && this.ticksCatchableDelay <= 0) {
                        this.field_234595_aq_ = true;
                    } else {
                        boolean bl3 = this.field_234595_aq_ = this.field_234595_aq_ && this.field_234598_d_ < 10 && this.func_234603_b_(blockPos);
                    }
                    if (bl) {
                        this.field_234598_d_ = Math.max(0, this.field_234598_d_ - 1);
                        if (this.field_234597_c_) {
                            this.setMotion(this.getMotion().add(0.0, -0.1 * (double)this.field_234596_b_.nextFloat() * (double)this.field_234596_b_.nextFloat(), 0.0));
                        }
                        if (!this.world.isRemote) {
                            this.catchingFish(blockPos);
                        }
                    } else {
                        this.field_234598_d_ = Math.min(10, this.field_234598_d_ + 1);
                    }
                }
            }
            if (!fluidState.isTagged(FluidTags.WATER)) {
                this.setMotion(this.getMotion().add(0.0, -0.03, 0.0));
            }
            this.move(MoverType.SELF, this.getMotion());
            this.func_234617_x_();
            if (this.currentState == State.FLYING && (this.onGround || this.collidedHorizontally)) {
                this.setMotion(Vector3d.ZERO);
            }
            double d = 0.92;
            this.setMotion(this.getMotion().scale(0.92));
            this.recenterBoundingBox();
        }
    }

    private boolean func_234600_a_(PlayerEntity playerEntity) {
        boolean bl;
        ItemStack itemStack = playerEntity.getHeldItemMainhand();
        ItemStack itemStack2 = playerEntity.getHeldItemOffhand();
        boolean bl2 = itemStack.getItem() == Items.FISHING_ROD;
        boolean bl3 = bl = itemStack2.getItem() == Items.FISHING_ROD;
        if (!playerEntity.removed && playerEntity.isAlive() && (bl2 || bl) && !(this.getDistanceSq(playerEntity) > 1024.0)) {
            return true;
        }
        this.remove();
        return false;
    }

    private void checkCollision() {
        RayTraceResult rayTraceResult = ProjectileHelper.func_234618_a_(this, this::func_230298_a_);
        this.onImpact(rayTraceResult);
    }

    @Override
    protected boolean func_230298_a_(Entity entity2) {
        return super.func_230298_a_(entity2) || entity2.isAlive() && entity2 instanceof ItemEntity;
    }

    @Override
    protected void onEntityHit(EntityRayTraceResult entityRayTraceResult) {
        super.onEntityHit(entityRayTraceResult);
        if (!this.world.isRemote) {
            this.caughtEntity = entityRayTraceResult.getEntity();
            this.setHookedEntity();
        }
    }

    @Override
    protected void func_230299_a_(BlockRayTraceResult blockRayTraceResult) {
        super.func_230299_a_(blockRayTraceResult);
        this.setMotion(this.getMotion().normalize().scale(blockRayTraceResult.func_237486_a_(this)));
    }

    private void setHookedEntity() {
        this.getDataManager().set(DATA_HOOKED_ENTITY, this.caughtEntity.getEntityId() + 1);
    }

    private void catchingFish(BlockPos blockPos) {
        ServerWorld serverWorld = (ServerWorld)this.world;
        int n = 1;
        BlockPos blockPos2 = blockPos.up();
        if (this.rand.nextFloat() < 0.25f && this.world.isRainingAt(blockPos2)) {
            ++n;
        }
        if (this.rand.nextFloat() < 0.5f && !this.world.canSeeSky(blockPos2)) {
            --n;
        }
        if (this.ticksCatchable > 0) {
            --this.ticksCatchable;
            if (this.ticksCatchable <= 0) {
                this.ticksCaughtDelay = 0;
                this.ticksCatchableDelay = 0;
                this.getDataManager().set(field_234599_f_, false);
            }
        } else if (this.ticksCatchableDelay > 0) {
            this.ticksCatchableDelay -= n;
            if (this.ticksCatchableDelay > 0) {
                double d;
                double d2;
                this.fishApproachAngle = (float)((double)this.fishApproachAngle + this.rand.nextGaussian() * 4.0);
                float f = this.fishApproachAngle * ((float)Math.PI / 180);
                float f2 = MathHelper.sin(f);
                float f3 = MathHelper.cos(f);
                double d3 = this.getPosX() + (double)(f2 * (float)this.ticksCatchableDelay * 0.1f);
                BlockState blockState = serverWorld.getBlockState(new BlockPos(d3, (d2 = (double)((float)MathHelper.floor(this.getPosY()) + 1.0f)) - 1.0, d = this.getPosZ() + (double)(f3 * (float)this.ticksCatchableDelay * 0.1f)));
                if (blockState.isIn(Blocks.WATER)) {
                    if (this.rand.nextFloat() < 0.15f) {
                        serverWorld.spawnParticle(ParticleTypes.BUBBLE, d3, d2 - (double)0.1f, d, 1, f2, 0.1, f3, 0.0);
                    }
                    float f4 = f2 * 0.04f;
                    float f5 = f3 * 0.04f;
                    serverWorld.spawnParticle(ParticleTypes.FISHING, d3, d2, d, 0, f5, 0.01, -f4, 1.0);
                    serverWorld.spawnParticle(ParticleTypes.FISHING, d3, d2, d, 0, -f5, 0.01, f4, 1.0);
                }
            } else {
                this.playSound(SoundEvents.ENTITY_FISHING_BOBBER_SPLASH, 0.25f, 1.0f + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4f);
                double d = this.getPosY() + 0.5;
                serverWorld.spawnParticle(ParticleTypes.BUBBLE, this.getPosX(), d, this.getPosZ(), (int)(1.0f + this.getWidth() * 20.0f), this.getWidth(), 0.0, this.getWidth(), 0.2f);
                serverWorld.spawnParticle(ParticleTypes.FISHING, this.getPosX(), d, this.getPosZ(), (int)(1.0f + this.getWidth() * 20.0f), this.getWidth(), 0.0, this.getWidth(), 0.2f);
                this.ticksCatchable = MathHelper.nextInt(this.rand, 20, 40);
                this.getDataManager().set(field_234599_f_, true);
            }
        } else if (this.ticksCaughtDelay > 0) {
            this.ticksCaughtDelay -= n;
            float f = 0.15f;
            if (this.ticksCaughtDelay < 20) {
                f = (float)((double)f + (double)(20 - this.ticksCaughtDelay) * 0.05);
            } else if (this.ticksCaughtDelay < 40) {
                f = (float)((double)f + (double)(40 - this.ticksCaughtDelay) * 0.02);
            } else if (this.ticksCaughtDelay < 60) {
                f = (float)((double)f + (double)(60 - this.ticksCaughtDelay) * 0.01);
            }
            if (this.rand.nextFloat() < f) {
                double d;
                double d4;
                float f6 = MathHelper.nextFloat(this.rand, 0.0f, 360.0f) * ((float)Math.PI / 180);
                float f7 = MathHelper.nextFloat(this.rand, 25.0f, 60.0f);
                double d5 = this.getPosX() + (double)(MathHelper.sin(f6) * f7 * 0.1f);
                BlockState blockState = serverWorld.getBlockState(new BlockPos(d5, (d4 = (double)((float)MathHelper.floor(this.getPosY()) + 1.0f)) - 1.0, d = this.getPosZ() + (double)(MathHelper.cos(f6) * f7 * 0.1f)));
                if (blockState.isIn(Blocks.WATER)) {
                    serverWorld.spawnParticle(ParticleTypes.SPLASH, d5, d4, d, 2 + this.rand.nextInt(2), 0.1f, 0.0, 0.1f, 0.0);
                }
            }
            if (this.ticksCaughtDelay <= 0) {
                this.fishApproachAngle = MathHelper.nextFloat(this.rand, 0.0f, 360.0f);
                this.ticksCatchableDelay = MathHelper.nextInt(this.rand, 20, 80);
            }
        } else {
            this.ticksCaughtDelay = MathHelper.nextInt(this.rand, 100, 600);
            this.ticksCaughtDelay -= this.lureSpeed * 20 * 5;
        }
    }

    private boolean func_234603_b_(BlockPos blockPos) {
        WaterType waterType = WaterType.INVALID;
        for (int i = -1; i <= 2; ++i) {
            WaterType waterType2 = this.func_234602_a_(blockPos.add(-2, i, -2), blockPos.add(2, i, 2));
            switch (1.$SwitchMap$net$minecraft$entity$projectile$FishingBobberEntity$WaterType[waterType2.ordinal()]) {
                case 1: {
                    return true;
                }
                case 2: {
                    if (waterType != WaterType.INVALID) break;
                    return true;
                }
                case 3: {
                    if (waterType != WaterType.ABOVE_WATER) break;
                    return true;
                }
            }
            waterType = waterType2;
        }
        return false;
    }

    private WaterType func_234602_a_(BlockPos blockPos, BlockPos blockPos2) {
        return BlockPos.getAllInBox(blockPos, blockPos2).map(this::func_234604_c_).reduce(FishingBobberEntity::lambda$func_234602_a_$0).orElse(WaterType.INVALID);
    }

    private WaterType func_234604_c_(BlockPos blockPos) {
        BlockState blockState = this.world.getBlockState(blockPos);
        if (!blockState.isAir() && !blockState.isIn(Blocks.LILY_PAD)) {
            FluidState fluidState = blockState.getFluidState();
            return fluidState.isTagged(FluidTags.WATER) && fluidState.isSource() && blockState.getCollisionShape(this.world, blockPos).isEmpty() ? WaterType.INSIDE_WATER : WaterType.INVALID;
        }
        return WaterType.ABOVE_WATER;
    }

    public boolean func_234605_g_() {
        return this.field_234595_aq_;
    }

    @Override
    public void writeAdditional(CompoundNBT compoundNBT) {
    }

    @Override
    public void readAdditional(CompoundNBT compoundNBT) {
    }

    public int handleHookRetraction(ItemStack itemStack) {
        PlayerEntity playerEntity = this.func_234606_i_();
        if (!this.world.isRemote && playerEntity != null) {
            int n = 0;
            if (this.caughtEntity != null) {
                this.bringInHookedEntity();
                CriteriaTriggers.FISHING_ROD_HOOKED.trigger((ServerPlayerEntity)playerEntity, itemStack, this, Collections.emptyList());
                this.world.setEntityState(this, (byte)31);
                n = this.caughtEntity instanceof ItemEntity ? 3 : 5;
            } else if (this.ticksCatchable > 0) {
                LootContext.Builder builder = new LootContext.Builder((ServerWorld)this.world).withParameter(LootParameters.field_237457_g_, this.getPositionVec()).withParameter(LootParameters.TOOL, itemStack).withParameter(LootParameters.THIS_ENTITY, this).withRandom(this.rand).withLuck((float)this.luck + playerEntity.getLuck());
                LootTable lootTable = this.world.getServer().getLootTableManager().getLootTableFromLocation(LootTables.GAMEPLAY_FISHING);
                List<ItemStack> list = lootTable.generate(builder.build(LootParameterSets.FISHING));
                CriteriaTriggers.FISHING_ROD_HOOKED.trigger((ServerPlayerEntity)playerEntity, itemStack, this, list);
                for (ItemStack itemStack2 : list) {
                    ItemEntity itemEntity = new ItemEntity(this.world, this.getPosX(), this.getPosY(), this.getPosZ(), itemStack2);
                    double d = playerEntity.getPosX() - this.getPosX();
                    double d2 = playerEntity.getPosY() - this.getPosY();
                    double d3 = playerEntity.getPosZ() - this.getPosZ();
                    double d4 = 0.1;
                    itemEntity.setMotion(d * 0.1, d2 * 0.1 + Math.sqrt(Math.sqrt(d * d + d2 * d2 + d3 * d3)) * 0.08, d3 * 0.1);
                    this.world.addEntity(itemEntity);
                    playerEntity.world.addEntity(new ExperienceOrbEntity(playerEntity.world, playerEntity.getPosX(), playerEntity.getPosY() + 0.5, playerEntity.getPosZ() + 0.5, this.rand.nextInt(6) + 1));
                    if (!itemStack2.getItem().isIn(ItemTags.FISHES)) continue;
                    playerEntity.addStat(Stats.FISH_CAUGHT, 1);
                }
                n = 1;
            }
            if (this.onGround) {
                n = 2;
            }
            this.remove();
            return n;
        }
        return 1;
    }

    @Override
    public void handleStatusUpdate(byte by) {
        if (by == 31 && this.world.isRemote && this.caughtEntity instanceof PlayerEntity && ((PlayerEntity)this.caughtEntity).isUser()) {
            this.bringInHookedEntity();
        }
        super.handleStatusUpdate(by);
    }

    protected void bringInHookedEntity() {
        Entity entity2 = this.func_234616_v_();
        if (entity2 != null) {
            Vector3d vector3d = new Vector3d(entity2.getPosX() - this.getPosX(), entity2.getPosY() - this.getPosY(), entity2.getPosZ() - this.getPosZ()).scale(0.1);
            this.caughtEntity.setMotion(this.caughtEntity.getMotion().add(vector3d));
        }
    }

    @Override
    protected boolean canTriggerWalking() {
        return true;
    }

    @Override
    public void remove() {
        super.remove();
        PlayerEntity playerEntity = this.func_234606_i_();
        if (playerEntity != null) {
            playerEntity.fishingBobber = null;
        }
    }

    @Nullable
    public PlayerEntity func_234606_i_() {
        Entity entity2 = this.func_234616_v_();
        return entity2 instanceof PlayerEntity ? (PlayerEntity)entity2 : null;
    }

    @Nullable
    public Entity func_234607_k_() {
        return this.caughtEntity;
    }

    @Override
    public boolean isNonBoss() {
        return true;
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        Entity entity2 = this.func_234616_v_();
        return new SSpawnObjectPacket(this, entity2 == null ? this.getEntityId() : entity2.getEntityId());
    }

    private static WaterType lambda$func_234602_a_$0(WaterType waterType, WaterType waterType2) {
        return waterType == waterType2 ? waterType : WaterType.INVALID;
    }

    static enum State {
        FLYING,
        HOOKED_IN_ENTITY,
        BOBBING;

    }

    static enum WaterType {
        ABOVE_WATER,
        INSIDE_WATER,
        INVALID;

    }
}

