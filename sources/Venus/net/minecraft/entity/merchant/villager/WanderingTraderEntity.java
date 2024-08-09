/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.merchant.villager;

import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAtCustomerGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookAtWithoutMovingGoal;
import net.minecraft.entity.ai.goal.MoveTowardsRestrictionGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TradeWithPlayerGoal;
import net.minecraft.entity.ai.goal.UseItemGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.entity.monster.EvokerEntity;
import net.minecraft.entity.monster.IllusionerEntity;
import net.minecraft.entity.monster.PillagerEntity;
import net.minecraft.entity.monster.VexEntity;
import net.minecraft.entity.monster.VindicatorEntity;
import net.minecraft.entity.monster.ZoglinEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MerchantOffer;
import net.minecraft.item.MerchantOffers;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class WanderingTraderEntity
extends AbstractVillagerEntity {
    @Nullable
    private BlockPos wanderTarget;
    private int despawnDelay;

    public WanderingTraderEntity(EntityType<? extends WanderingTraderEntity> entityType, World world) {
        super((EntityType<? extends AbstractVillagerEntity>)entityType, world);
        this.forceSpawn = true;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(0, new UseItemGoal<WanderingTraderEntity>(this, PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.INVISIBILITY), SoundEvents.ENTITY_WANDERING_TRADER_DISAPPEARED, this::lambda$registerGoals$0));
        this.goalSelector.addGoal(0, new UseItemGoal<WanderingTraderEntity>(this, new ItemStack(Items.MILK_BUCKET), SoundEvents.ENTITY_WANDERING_TRADER_REAPPEARED, this::lambda$registerGoals$1));
        this.goalSelector.addGoal(1, new TradeWithPlayerGoal(this));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<ZombieEntity>(this, ZombieEntity.class, 8.0f, 0.5, 0.5));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<EvokerEntity>(this, EvokerEntity.class, 12.0f, 0.5, 0.5));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<VindicatorEntity>(this, VindicatorEntity.class, 8.0f, 0.5, 0.5));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<VexEntity>(this, VexEntity.class, 8.0f, 0.5, 0.5));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<PillagerEntity>(this, PillagerEntity.class, 15.0f, 0.5, 0.5));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<IllusionerEntity>(this, IllusionerEntity.class, 12.0f, 0.5, 0.5));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<ZoglinEntity>(this, ZoglinEntity.class, 10.0f, 0.5, 0.5));
        this.goalSelector.addGoal(1, new PanicGoal(this, 0.5));
        this.goalSelector.addGoal(1, new LookAtCustomerGoal(this));
        this.goalSelector.addGoal(2, new MoveToGoal(this, this, 2.0, 0.35));
        this.goalSelector.addGoal(4, new MoveTowardsRestrictionGoal(this, 0.35));
        this.goalSelector.addGoal(8, new WaterAvoidingRandomWalkingGoal(this, 0.35));
        this.goalSelector.addGoal(9, new LookAtWithoutMovingGoal(this, PlayerEntity.class, 3.0f, 1.0f));
        this.goalSelector.addGoal(10, new LookAtGoal(this, MobEntity.class, 8.0f));
    }

    @Override
    @Nullable
    public AgeableEntity func_241840_a(ServerWorld serverWorld, AgeableEntity ageableEntity) {
        return null;
    }

    @Override
    public boolean hasXPBar() {
        return true;
    }

    @Override
    public ActionResultType func_230254_b_(PlayerEntity playerEntity, Hand hand) {
        ItemStack itemStack = playerEntity.getHeldItem(hand);
        if (itemStack.getItem() != Items.VILLAGER_SPAWN_EGG && this.isAlive() && !this.hasCustomer() && !this.isChild()) {
            if (hand == Hand.MAIN_HAND) {
                playerEntity.addStat(Stats.TALKED_TO_VILLAGER);
            }
            if (this.getOffers().isEmpty()) {
                return ActionResultType.func_233537_a_(this.world.isRemote);
            }
            if (!this.world.isRemote) {
                this.setCustomer(playerEntity);
                this.openMerchantContainer(playerEntity, this.getDisplayName(), 1);
            }
            return ActionResultType.func_233537_a_(this.world.isRemote);
        }
        return super.func_230254_b_(playerEntity, hand);
    }

    @Override
    protected void populateTradeData() {
        VillagerTrades.ITrade[] iTradeArray = (VillagerTrades.ITrade[])VillagerTrades.field_221240_b.get(1);
        VillagerTrades.ITrade[] iTradeArray2 = (VillagerTrades.ITrade[])VillagerTrades.field_221240_b.get(2);
        if (iTradeArray != null && iTradeArray2 != null) {
            MerchantOffers merchantOffers = this.getOffers();
            this.addTrades(merchantOffers, iTradeArray, 5);
            int n = this.rand.nextInt(iTradeArray2.length);
            VillagerTrades.ITrade iTrade = iTradeArray2[n];
            MerchantOffer merchantOffer = iTrade.getOffer(this, this.rand);
            if (merchantOffer != null) {
                merchantOffers.add(merchantOffer);
            }
        }
    }

    @Override
    public void writeAdditional(CompoundNBT compoundNBT) {
        super.writeAdditional(compoundNBT);
        compoundNBT.putInt("DespawnDelay", this.despawnDelay);
        if (this.wanderTarget != null) {
            compoundNBT.put("WanderTarget", NBTUtil.writeBlockPos(this.wanderTarget));
        }
    }

    @Override
    public void readAdditional(CompoundNBT compoundNBT) {
        super.readAdditional(compoundNBT);
        if (compoundNBT.contains("DespawnDelay", 0)) {
            this.despawnDelay = compoundNBT.getInt("DespawnDelay");
        }
        if (compoundNBT.contains("WanderTarget")) {
            this.wanderTarget = NBTUtil.readBlockPos(compoundNBT.getCompound("WanderTarget"));
        }
        this.setGrowingAge(Math.max(0, this.getGrowingAge()));
    }

    @Override
    public boolean canDespawn(double d) {
        return true;
    }

    @Override
    protected void onVillagerTrade(MerchantOffer merchantOffer) {
        if (merchantOffer.getDoesRewardExp()) {
            int n = 3 + this.rand.nextInt(4);
            this.world.addEntity(new ExperienceOrbEntity(this.world, this.getPosX(), this.getPosY() + 0.5, this.getPosZ(), n));
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return this.hasCustomer() ? SoundEvents.ENTITY_WANDERING_TRADER_TRADE : SoundEvents.ENTITY_WANDERING_TRADER_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.ENTITY_WANDERING_TRADER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_WANDERING_TRADER_DEATH;
    }

    @Override
    protected SoundEvent getDrinkSound(ItemStack itemStack) {
        Item item = itemStack.getItem();
        return item == Items.MILK_BUCKET ? SoundEvents.ENTITY_WANDERING_TRADER_DRINK_MILK : SoundEvents.ENTITY_WANDERING_TRADER_DRINK_POTION;
    }

    @Override
    protected SoundEvent getVillagerYesNoSound(boolean bl) {
        return bl ? SoundEvents.ENTITY_WANDERING_TRADER_YES : SoundEvents.ENTITY_WANDERING_TRADER_NO;
    }

    @Override
    public SoundEvent getYesSound() {
        return SoundEvents.ENTITY_WANDERING_TRADER_YES;
    }

    public void setDespawnDelay(int n) {
        this.despawnDelay = n;
    }

    public int getDespawnDelay() {
        return this.despawnDelay;
    }

    @Override
    public void livingTick() {
        super.livingTick();
        if (!this.world.isRemote) {
            this.handleDespawn();
        }
    }

    private void handleDespawn() {
        if (this.despawnDelay > 0 && !this.hasCustomer() && --this.despawnDelay == 0) {
            this.remove();
        }
    }

    public void setWanderTarget(@Nullable BlockPos blockPos) {
        this.wanderTarget = blockPos;
    }

    @Nullable
    private BlockPos getWanderTarget() {
        return this.wanderTarget;
    }

    private boolean lambda$registerGoals$1(WanderingTraderEntity wanderingTraderEntity) {
        return this.world.isDaytime() && wanderingTraderEntity.isInvisible();
    }

    private boolean lambda$registerGoals$0(WanderingTraderEntity wanderingTraderEntity) {
        return this.world.isNightTime() && !wanderingTraderEntity.isInvisible();
    }

    static PathNavigator access$000(WanderingTraderEntity wanderingTraderEntity) {
        return wanderingTraderEntity.navigator;
    }

    static PathNavigator access$100(WanderingTraderEntity wanderingTraderEntity) {
        return wanderingTraderEntity.navigator;
    }

    static PathNavigator access$200(WanderingTraderEntity wanderingTraderEntity) {
        return wanderingTraderEntity.navigator;
    }

    static PathNavigator access$300(WanderingTraderEntity wanderingTraderEntity) {
        return wanderingTraderEntity.navigator;
    }

    class MoveToGoal
    extends Goal {
        final WanderingTraderEntity traderEntity;
        final double maxDistance;
        final double speed;
        final WanderingTraderEntity this$0;

        MoveToGoal(WanderingTraderEntity wanderingTraderEntity, WanderingTraderEntity wanderingTraderEntity2, double d, double d2) {
            this.this$0 = wanderingTraderEntity;
            this.traderEntity = wanderingTraderEntity2;
            this.maxDistance = d;
            this.speed = d2;
            this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public void resetTask() {
            this.traderEntity.setWanderTarget(null);
            WanderingTraderEntity.access$000(this.this$0).clearPath();
        }

        @Override
        public boolean shouldExecute() {
            BlockPos blockPos = this.traderEntity.getWanderTarget();
            return blockPos != null && this.isWithinDistance(blockPos, this.maxDistance);
        }

        @Override
        public void tick() {
            BlockPos blockPos = this.traderEntity.getWanderTarget();
            if (blockPos != null && WanderingTraderEntity.access$100(this.this$0).noPath()) {
                if (this.isWithinDistance(blockPos, 10.0)) {
                    Vector3d vector3d = new Vector3d((double)blockPos.getX() - this.traderEntity.getPosX(), (double)blockPos.getY() - this.traderEntity.getPosY(), (double)blockPos.getZ() - this.traderEntity.getPosZ()).normalize();
                    Vector3d vector3d2 = vector3d.scale(10.0).add(this.traderEntity.getPosX(), this.traderEntity.getPosY(), this.traderEntity.getPosZ());
                    WanderingTraderEntity.access$200(this.this$0).tryMoveToXYZ(vector3d2.x, vector3d2.y, vector3d2.z, this.speed);
                } else {
                    WanderingTraderEntity.access$300(this.this$0).tryMoveToXYZ(blockPos.getX(), blockPos.getY(), blockPos.getZ(), this.speed);
                }
            }
        }

        private boolean isWithinDistance(BlockPos blockPos, double d) {
            return !blockPos.withinDistance(this.traderEntity.getPositionVec(), d);
        }
    }
}

