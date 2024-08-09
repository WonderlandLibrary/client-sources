/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.merchant.villager;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.schedule.Activity;
import net.minecraft.entity.ai.brain.schedule.Schedule;
import net.minecraft.entity.ai.brain.sensor.GolemLastSeenSensor;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.brain.task.VillagerTasks;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.merchant.IReputationTracking;
import net.minecraft.entity.merchant.IReputationType;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.merchant.villager.VillagerData;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.entity.monster.WitchEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.villager.IVillagerDataHolder;
import net.minecraft.entity.villager.VillagerType;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MerchantOffer;
import net.minecraft.item.MerchantOffers;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.NBTDynamicOps;
import net.minecraft.network.DebugPacketSender;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.server.MinecraftServer;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.village.GossipManager;
import net.minecraft.village.GossipType;
import net.minecraft.village.PointOfInterestManager;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.raid.Raid;
import net.minecraft.world.server.ServerWorld;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class VillagerEntity
extends AbstractVillagerEntity
implements IReputationTracking,
IVillagerDataHolder {
    private static final DataParameter<VillagerData> VILLAGER_DATA = EntityDataManager.createKey(VillagerEntity.class, DataSerializers.VILLAGER_DATA);
    public static final Map<Item, Integer> FOOD_VALUES = ImmutableMap.of(Items.BREAD, 4, Items.POTATO, 1, Items.CARROT, 1, Items.BEETROOT, 1);
    private static final Set<Item> ALLOWED_INVENTORY_ITEMS = ImmutableSet.of(Items.BREAD, Items.POTATO, Items.CARROT, Items.WHEAT, Items.WHEAT_SEEDS, Items.BEETROOT, Items.BEETROOT_SEEDS);
    private int timeUntilReset;
    private boolean leveledUp;
    @Nullable
    private PlayerEntity previousCustomer;
    private byte foodLevel;
    private final GossipManager gossip = new GossipManager();
    private long lastGossipTime;
    private long lastGossipDecay;
    private int xp;
    private long lastRestock;
    private int restocksToday;
    private long lastRestockDayTime;
    private boolean assignProfessionWhenSpawned;
    private static final ImmutableList<MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(MemoryModuleType.HOME, MemoryModuleType.JOB_SITE, MemoryModuleType.POTENTIAL_JOB_SITE, MemoryModuleType.MEETING_POINT, MemoryModuleType.MOBS, MemoryModuleType.VISIBLE_MOBS, MemoryModuleType.VISIBLE_VILLAGER_BABIES, MemoryModuleType.NEAREST_PLAYERS, MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER, MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM, MemoryModuleType.WALK_TARGET, MemoryModuleType.LOOK_TARGET, MemoryModuleType.INTERACTION_TARGET, MemoryModuleType.BREED_TARGET, MemoryModuleType.PATH, MemoryModuleType.OPENED_DOORS, MemoryModuleType.NEAREST_BED, MemoryModuleType.HURT_BY, MemoryModuleType.HURT_BY_ENTITY, MemoryModuleType.NEAREST_HOSTILE, MemoryModuleType.SECONDARY_JOB_SITE, MemoryModuleType.HIDING_PLACE, MemoryModuleType.HEARD_BELL_TIME, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleType.LAST_SLEPT, MemoryModuleType.LAST_WOKEN, MemoryModuleType.LAST_WORKED_AT_POI, MemoryModuleType.GOLEM_DETECTED_RECENTLY);
    private static final ImmutableList<SensorType<? extends Sensor<? super VillagerEntity>>> SENSOR_TYPES = ImmutableList.of(SensorType.NEAREST_LIVING_ENTITIES, SensorType.NEAREST_PLAYERS, SensorType.NEAREST_ITEMS, SensorType.NEAREST_BED, SensorType.HURT_BY, SensorType.VILLAGER_HOSTILES, SensorType.VILLAGER_BABIES, SensorType.SECONDARY_POIS, SensorType.GOLEM_DETECTED);
    public static final Map<MemoryModuleType<GlobalPos>, BiPredicate<VillagerEntity, PointOfInterestType>> JOB_SITE_PREDICATE_MAP = ImmutableMap.of(MemoryModuleType.HOME, VillagerEntity::lambda$static$0, MemoryModuleType.JOB_SITE, VillagerEntity::lambda$static$1, MemoryModuleType.POTENTIAL_JOB_SITE, VillagerEntity::lambda$static$2, MemoryModuleType.MEETING_POINT, VillagerEntity::lambda$static$3);

    public VillagerEntity(EntityType<? extends VillagerEntity> entityType, World world) {
        this(entityType, world, VillagerType.PLAINS);
    }

    public VillagerEntity(EntityType<? extends VillagerEntity> entityType, World world, VillagerType villagerType) {
        super((EntityType<? extends AbstractVillagerEntity>)entityType, world);
        ((GroundPathNavigator)this.getNavigator()).setBreakDoors(false);
        this.getNavigator().setCanSwim(false);
        this.setCanPickUpLoot(false);
        this.setVillagerData(this.getVillagerData().withType(villagerType).withProfession(VillagerProfession.NONE));
    }

    public Brain<VillagerEntity> getBrain() {
        return super.getBrain();
    }

    protected Brain.BrainCodec<VillagerEntity> getBrainCodec() {
        return Brain.createCodec(MEMORY_TYPES, SENSOR_TYPES);
    }

    @Override
    protected Brain<?> createBrain(Dynamic<?> dynamic) {
        Brain<VillagerEntity> brain = this.getBrainCodec().deserialize(dynamic);
        this.initBrain(brain);
        return brain;
    }

    public void resetBrain(ServerWorld serverWorld) {
        Brain<VillagerEntity> brain = this.getBrain();
        brain.stopAllTasks(serverWorld, this);
        this.brain = brain.copy();
        this.initBrain(this.getBrain());
    }

    private void initBrain(Brain<VillagerEntity> brain) {
        VillagerProfession villagerProfession = this.getVillagerData().getProfession();
        if (this.isChild()) {
            brain.setSchedule(Schedule.VILLAGER_BABY);
            brain.registerActivity(Activity.PLAY, VillagerTasks.play(0.5f));
        } else {
            brain.setSchedule(Schedule.VILLAGER_DEFAULT);
            brain.registerActivity(Activity.WORK, VillagerTasks.work(villagerProfession, 0.5f), ImmutableSet.of(Pair.of(MemoryModuleType.JOB_SITE, MemoryModuleStatus.VALUE_PRESENT)));
        }
        brain.registerActivity(Activity.CORE, VillagerTasks.core(villagerProfession, 0.5f));
        brain.registerActivity(Activity.MEET, VillagerTasks.meet(villagerProfession, 0.5f), ImmutableSet.of(Pair.of(MemoryModuleType.MEETING_POINT, MemoryModuleStatus.VALUE_PRESENT)));
        brain.registerActivity(Activity.REST, VillagerTasks.rest(villagerProfession, 0.5f));
        brain.registerActivity(Activity.IDLE, VillagerTasks.idle(villagerProfession, 0.5f));
        brain.registerActivity(Activity.PANIC, VillagerTasks.panic(villagerProfession, 0.5f));
        brain.registerActivity(Activity.PRE_RAID, VillagerTasks.preRaid(villagerProfession, 0.5f));
        brain.registerActivity(Activity.RAID, VillagerTasks.raid(villagerProfession, 0.5f));
        brain.registerActivity(Activity.HIDE, VillagerTasks.hide(villagerProfession, 0.5f));
        brain.setDefaultActivities(ImmutableSet.of(Activity.CORE));
        brain.setFallbackActivity(Activity.IDLE);
        brain.switchTo(Activity.IDLE);
        brain.updateActivity(this.world.getDayTime(), this.world.getGameTime());
    }

    @Override
    protected void onGrowingAdult() {
        super.onGrowingAdult();
        if (this.world instanceof ServerWorld) {
            this.resetBrain((ServerWorld)this.world);
        }
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.5).createMutableAttribute(Attributes.FOLLOW_RANGE, 48.0);
    }

    public boolean shouldAssignProfessionOnSpawn() {
        return this.assignProfessionWhenSpawned;
    }

    @Override
    protected void updateAITasks() {
        Raid raid;
        this.world.getProfiler().startSection("villagerBrain");
        this.getBrain().tick((ServerWorld)this.world, this);
        this.world.getProfiler().endSection();
        if (this.assignProfessionWhenSpawned) {
            this.assignProfessionWhenSpawned = false;
        }
        if (!this.hasCustomer() && this.timeUntilReset > 0) {
            --this.timeUntilReset;
            if (this.timeUntilReset <= 0) {
                if (this.leveledUp) {
                    this.levelUp();
                    this.leveledUp = false;
                }
                this.addPotionEffect(new EffectInstance(Effects.REGENERATION, 200, 0));
            }
        }
        if (this.previousCustomer != null && this.world instanceof ServerWorld) {
            ((ServerWorld)this.world).updateReputation(IReputationType.TRADE, this.previousCustomer, this);
            this.world.setEntityState(this, (byte)14);
            this.previousCustomer = null;
        }
        if (!this.isAIDisabled() && this.rand.nextInt(100) == 0 && (raid = ((ServerWorld)this.world).findRaid(this.getPosition())) != null && raid.isActive() && !raid.isOver()) {
            this.world.setEntityState(this, (byte)42);
        }
        if (this.getVillagerData().getProfession() == VillagerProfession.NONE && this.hasCustomer()) {
            this.resetCustomer();
        }
        super.updateAITasks();
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getShakeHeadTicks() > 0) {
            this.setShakeHeadTicks(this.getShakeHeadTicks() - 1);
        }
        this.tickGossip();
    }

    @Override
    public ActionResultType func_230254_b_(PlayerEntity playerEntity, Hand hand) {
        ItemStack itemStack = playerEntity.getHeldItem(hand);
        if (itemStack.getItem() != Items.VILLAGER_SPAWN_EGG && this.isAlive() && !this.hasCustomer() && !this.isSleeping()) {
            if (this.isChild()) {
                this.shakeHead();
                return ActionResultType.func_233537_a_(this.world.isRemote);
            }
            boolean bl = this.getOffers().isEmpty();
            if (hand == Hand.MAIN_HAND) {
                if (bl && !this.world.isRemote) {
                    this.shakeHead();
                }
                playerEntity.addStat(Stats.TALKED_TO_VILLAGER);
            }
            if (bl) {
                return ActionResultType.func_233537_a_(this.world.isRemote);
            }
            if (!this.world.isRemote && !this.offers.isEmpty()) {
                this.displayMerchantGui(playerEntity);
            }
            return ActionResultType.func_233537_a_(this.world.isRemote);
        }
        return super.func_230254_b_(playerEntity, hand);
    }

    private void shakeHead() {
        this.setShakeHeadTicks(40);
        if (!this.world.isRemote()) {
            this.playSound(SoundEvents.ENTITY_VILLAGER_NO, this.getSoundVolume(), this.getSoundPitch());
        }
    }

    private void displayMerchantGui(PlayerEntity playerEntity) {
        this.recalculateSpecialPricesFor(playerEntity);
        this.setCustomer(playerEntity);
        this.openMerchantContainer(playerEntity, this.getDisplayName(), this.getVillagerData().getLevel());
    }

    @Override
    public void setCustomer(@Nullable PlayerEntity playerEntity) {
        boolean bl = this.getCustomer() != null && playerEntity == null;
        super.setCustomer(playerEntity);
        if (bl) {
            this.resetCustomer();
        }
    }

    @Override
    protected void resetCustomer() {
        super.resetCustomer();
        this.resetAllSpecialPrices();
    }

    private void resetAllSpecialPrices() {
        for (MerchantOffer merchantOffer : this.getOffers()) {
            merchantOffer.resetSpecialPrice();
        }
    }

    @Override
    public boolean canRestockTrades() {
        return false;
    }

    public void restock() {
        this.calculateDemandOfOffers();
        for (MerchantOffer merchantOffer : this.getOffers()) {
            merchantOffer.resetUses();
        }
        this.lastRestock = this.world.getGameTime();
        ++this.restocksToday;
    }

    private boolean hasUsedOffer() {
        for (MerchantOffer merchantOffer : this.getOffers()) {
            if (!merchantOffer.hasBeenUsed()) continue;
            return false;
        }
        return true;
    }

    private boolean canRestock() {
        return this.restocksToday == 0 || this.restocksToday < 2 && this.world.getGameTime() > this.lastRestock + 2400L;
    }

    public boolean canResetStock() {
        long l = this.lastRestock + 12000L;
        long l2 = this.world.getGameTime();
        boolean bl = l2 > l;
        long l3 = this.world.getDayTime();
        if (this.lastRestockDayTime > 0L) {
            long l4 = l3 / 24000L;
            long l5 = this.lastRestockDayTime / 24000L;
            bl |= l4 > l5;
        }
        this.lastRestockDayTime = l3;
        if (bl) {
            this.lastRestock = l2;
            this.func_223718_eH();
        }
        return this.canRestock() && this.hasUsedOffer();
    }

    private void resetOffersAndAdjustForDemand() {
        int n = 2 - this.restocksToday;
        if (n > 0) {
            for (MerchantOffer merchantOffer : this.getOffers()) {
                merchantOffer.resetUses();
            }
        }
        for (int i = 0; i < n; ++i) {
            this.calculateDemandOfOffers();
        }
    }

    private void calculateDemandOfOffers() {
        for (MerchantOffer merchantOffer : this.getOffers()) {
            merchantOffer.calculateDemand();
        }
    }

    private void recalculateSpecialPricesFor(PlayerEntity playerEntity) {
        int n = this.getPlayerReputation(playerEntity);
        if (n != 0) {
            for (MerchantOffer merchantOffer : this.getOffers()) {
                merchantOffer.increaseSpecialPrice(-MathHelper.floor((float)n * merchantOffer.getPriceMultiplier()));
            }
        }
        if (playerEntity.isPotionActive(Effects.HERO_OF_THE_VILLAGE)) {
            EffectInstance effectInstance = playerEntity.getActivePotionEffect(Effects.HERO_OF_THE_VILLAGE);
            int n2 = effectInstance.getAmplifier();
            for (MerchantOffer merchantOffer : this.getOffers()) {
                double d = 0.3 + 0.0625 * (double)n2;
                int n3 = (int)Math.floor(d * (double)merchantOffer.getBuyingStackFirst().getCount());
                merchantOffer.increaseSpecialPrice(-Math.max(n3, 1));
            }
        }
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(VILLAGER_DATA, new VillagerData(VillagerType.PLAINS, VillagerProfession.NONE, 1));
    }

    @Override
    public void writeAdditional(CompoundNBT compoundNBT) {
        super.writeAdditional(compoundNBT);
        VillagerData.CODEC.encodeStart(NBTDynamicOps.INSTANCE, this.getVillagerData()).resultOrPartial(LOGGER::error).ifPresent(arg_0 -> VillagerEntity.lambda$writeAdditional$4(compoundNBT, arg_0));
        compoundNBT.putByte("FoodLevel", this.foodLevel);
        compoundNBT.put("Gossips", this.gossip.write(NBTDynamicOps.INSTANCE).getValue());
        compoundNBT.putInt("Xp", this.xp);
        compoundNBT.putLong("LastRestock", this.lastRestock);
        compoundNBT.putLong("LastGossipDecay", this.lastGossipDecay);
        compoundNBT.putInt("RestocksToday", this.restocksToday);
        if (this.assignProfessionWhenSpawned) {
            compoundNBT.putBoolean("AssignProfessionWhenSpawned", false);
        }
    }

    @Override
    public void readAdditional(CompoundNBT compoundNBT) {
        Object object;
        super.readAdditional(compoundNBT);
        if (compoundNBT.contains("VillagerData", 1)) {
            object = VillagerData.CODEC.parse(new Dynamic<INBT>(NBTDynamicOps.INSTANCE, compoundNBT.get("VillagerData")));
            ((DataResult)object).resultOrPartial(LOGGER::error).ifPresent(this::setVillagerData);
        }
        if (compoundNBT.contains("Offers", 1)) {
            this.offers = new MerchantOffers(compoundNBT.getCompound("Offers"));
        }
        if (compoundNBT.contains("FoodLevel", 0)) {
            this.foodLevel = compoundNBT.getByte("FoodLevel");
        }
        object = compoundNBT.getList("Gossips", 10);
        this.gossip.read(new Dynamic<Object>(NBTDynamicOps.INSTANCE, object));
        if (compoundNBT.contains("Xp", 0)) {
            this.xp = compoundNBT.getInt("Xp");
        }
        this.lastRestock = compoundNBT.getLong("LastRestock");
        this.lastGossipDecay = compoundNBT.getLong("LastGossipDecay");
        this.setCanPickUpLoot(false);
        if (this.world instanceof ServerWorld) {
            this.resetBrain((ServerWorld)this.world);
        }
        this.restocksToday = compoundNBT.getInt("RestocksToday");
        if (compoundNBT.contains("AssignProfessionWhenSpawned")) {
            this.assignProfessionWhenSpawned = compoundNBT.getBoolean("AssignProfessionWhenSpawned");
        }
    }

    @Override
    public boolean canDespawn(double d) {
        return true;
    }

    @Override
    @Nullable
    protected SoundEvent getAmbientSound() {
        if (this.isSleeping()) {
            return null;
        }
        return this.hasCustomer() ? SoundEvents.ENTITY_VILLAGER_TRADE : SoundEvents.ENTITY_VILLAGER_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.ENTITY_VILLAGER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_VILLAGER_DEATH;
    }

    public void playWorkstationSound() {
        SoundEvent soundEvent = this.getVillagerData().getProfession().getSound();
        if (soundEvent != null) {
            this.playSound(soundEvent, this.getSoundVolume(), this.getSoundPitch());
        }
    }

    public void setVillagerData(VillagerData villagerData) {
        VillagerData villagerData2 = this.getVillagerData();
        if (villagerData2.getProfession() != villagerData.getProfession()) {
            this.offers = null;
        }
        this.dataManager.set(VILLAGER_DATA, villagerData);
    }

    @Override
    public VillagerData getVillagerData() {
        return this.dataManager.get(VILLAGER_DATA);
    }

    @Override
    protected void onVillagerTrade(MerchantOffer merchantOffer) {
        int n = 3 + this.rand.nextInt(4);
        this.xp += merchantOffer.getGivenExp();
        this.previousCustomer = this.getCustomer();
        if (this.canLevelUp()) {
            this.timeUntilReset = 40;
            this.leveledUp = true;
            n += 5;
        }
        if (merchantOffer.getDoesRewardExp()) {
            this.world.addEntity(new ExperienceOrbEntity(this.world, this.getPosX(), this.getPosY() + 0.5, this.getPosZ(), n));
        }
    }

    @Override
    public void setRevengeTarget(@Nullable LivingEntity livingEntity) {
        if (livingEntity != null && this.world instanceof ServerWorld) {
            ((ServerWorld)this.world).updateReputation(IReputationType.VILLAGER_HURT, livingEntity, this);
            if (this.isAlive() && livingEntity instanceof PlayerEntity) {
                this.world.setEntityState(this, (byte)13);
            }
        }
        super.setRevengeTarget(livingEntity);
    }

    @Override
    public void onDeath(DamageSource damageSource) {
        LOGGER.info("Villager {} died, message: '{}'", (Object)this, (Object)damageSource.getDeathMessage(this).getString());
        Entity entity2 = damageSource.getTrueSource();
        if (entity2 != null) {
            this.sawMurder(entity2);
        }
        this.func_242369_fq();
        super.onDeath(damageSource);
    }

    private void func_242369_fq() {
        this.resetMemoryPoint(MemoryModuleType.HOME);
        this.resetMemoryPoint(MemoryModuleType.JOB_SITE);
        this.resetMemoryPoint(MemoryModuleType.POTENTIAL_JOB_SITE);
        this.resetMemoryPoint(MemoryModuleType.MEETING_POINT);
    }

    private void sawMurder(Entity entity2) {
        Optional<List<LivingEntity>> optional;
        if (this.world instanceof ServerWorld && (optional = this.brain.getMemory(MemoryModuleType.VISIBLE_MOBS)).isPresent()) {
            ServerWorld serverWorld = (ServerWorld)this.world;
            optional.get().stream().filter(VillagerEntity::lambda$sawMurder$5).forEach(arg_0 -> VillagerEntity.lambda$sawMurder$6(serverWorld, entity2, arg_0));
        }
    }

    public void resetMemoryPoint(MemoryModuleType<GlobalPos> memoryModuleType) {
        if (this.world instanceof ServerWorld) {
            MinecraftServer minecraftServer = ((ServerWorld)this.world).getServer();
            this.brain.getMemory(memoryModuleType).ifPresent(arg_0 -> this.lambda$resetMemoryPoint$7(minecraftServer, memoryModuleType, arg_0));
        }
    }

    @Override
    public boolean canBreed() {
        return this.foodLevel + this.getFoodValueFromInventory() >= 12 && this.getGrowingAge() == 0;
    }

    private boolean isHungry() {
        return this.foodLevel < 12;
    }

    private void eat() {
        if (this.isHungry() && this.getFoodValueFromInventory() != 0) {
            for (int i = 0; i < this.getVillagerInventory().getSizeInventory(); ++i) {
                int n;
                Integer n2;
                ItemStack itemStack = this.getVillagerInventory().getStackInSlot(i);
                if (itemStack.isEmpty() || (n2 = FOOD_VALUES.get(itemStack.getItem())) == null) continue;
                for (int j = n = itemStack.getCount(); j > 0; --j) {
                    this.foodLevel = (byte)(this.foodLevel + n2);
                    this.getVillagerInventory().decrStackSize(i, 1);
                    if (this.isHungry()) continue;
                    return;
                }
            }
        }
    }

    public int getPlayerReputation(PlayerEntity playerEntity) {
        return this.gossip.getReputation(playerEntity.getUniqueID(), VillagerEntity::lambda$getPlayerReputation$8);
    }

    private void decrFoodLevel(int n) {
        this.foodLevel = (byte)(this.foodLevel - n);
    }

    public void func_223346_ep() {
        this.eat();
        this.decrFoodLevel(12);
    }

    public void setOffers(MerchantOffers merchantOffers) {
        this.offers = merchantOffers;
    }

    private boolean canLevelUp() {
        int n = this.getVillagerData().getLevel();
        return VillagerData.canLevelUp(n) && this.xp >= VillagerData.getExperienceNext(n);
    }

    private void levelUp() {
        this.setVillagerData(this.getVillagerData().withLevel(this.getVillagerData().getLevel() + 1));
        this.populateTradeData();
    }

    @Override
    protected ITextComponent getProfessionName() {
        return new TranslationTextComponent(this.getType().getTranslationKey() + "." + Registry.VILLAGER_PROFESSION.getKey(this.getVillagerData().getProfession()).getPath());
    }

    @Override
    public void handleStatusUpdate(byte by) {
        if (by == 12) {
            this.spawnParticles(ParticleTypes.HEART);
        } else if (by == 13) {
            this.spawnParticles(ParticleTypes.ANGRY_VILLAGER);
        } else if (by == 14) {
            this.spawnParticles(ParticleTypes.HAPPY_VILLAGER);
        } else if (by == 42) {
            this.spawnParticles(ParticleTypes.SPLASH);
        } else {
            super.handleStatusUpdate(by);
        }
    }

    @Override
    @Nullable
    public ILivingEntityData onInitialSpawn(IServerWorld iServerWorld, DifficultyInstance difficultyInstance, SpawnReason spawnReason, @Nullable ILivingEntityData iLivingEntityData, @Nullable CompoundNBT compoundNBT) {
        if (spawnReason == SpawnReason.BREEDING) {
            this.setVillagerData(this.getVillagerData().withProfession(VillagerProfession.NONE));
        }
        if (spawnReason == SpawnReason.COMMAND || spawnReason == SpawnReason.SPAWN_EGG || spawnReason == SpawnReason.SPAWNER || spawnReason == SpawnReason.DISPENSER) {
            this.setVillagerData(this.getVillagerData().withType(VillagerType.func_242371_a(iServerWorld.func_242406_i(this.getPosition()))));
        }
        if (spawnReason == SpawnReason.STRUCTURE) {
            this.assignProfessionWhenSpawned = true;
        }
        return super.onInitialSpawn(iServerWorld, difficultyInstance, spawnReason, iLivingEntityData, compoundNBT);
    }

    @Override
    public VillagerEntity func_241840_a(ServerWorld serverWorld, AgeableEntity ageableEntity) {
        double d = this.rand.nextDouble();
        VillagerType villagerType = d < 0.5 ? VillagerType.func_242371_a(serverWorld.func_242406_i(this.getPosition())) : (d < 0.75 ? this.getVillagerData().getType() : ((VillagerEntity)ageableEntity).getVillagerData().getType());
        VillagerEntity villagerEntity = new VillagerEntity(EntityType.VILLAGER, serverWorld, villagerType);
        villagerEntity.onInitialSpawn(serverWorld, serverWorld.getDifficultyForLocation(villagerEntity.getPosition()), SpawnReason.BREEDING, null, null);
        return villagerEntity;
    }

    @Override
    public void func_241841_a(ServerWorld serverWorld, LightningBoltEntity lightningBoltEntity) {
        if (serverWorld.getDifficulty() != Difficulty.PEACEFUL) {
            LOGGER.info("Villager {} was struck by lightning {}.", (Object)this, (Object)lightningBoltEntity);
            WitchEntity witchEntity = EntityType.WITCH.create(serverWorld);
            witchEntity.setLocationAndAngles(this.getPosX(), this.getPosY(), this.getPosZ(), this.rotationYaw, this.rotationPitch);
            witchEntity.onInitialSpawn(serverWorld, serverWorld.getDifficultyForLocation(witchEntity.getPosition()), SpawnReason.CONVERSION, null, null);
            witchEntity.setNoAI(this.isAIDisabled());
            if (this.hasCustomName()) {
                witchEntity.setCustomName(this.getCustomName());
                witchEntity.setCustomNameVisible(this.isCustomNameVisible());
            }
            witchEntity.enablePersistence();
            serverWorld.func_242417_l(witchEntity);
            this.func_242369_fq();
            this.remove();
        } else {
            super.func_241841_a(serverWorld, lightningBoltEntity);
        }
    }

    @Override
    protected void updateEquipmentIfNeeded(ItemEntity itemEntity) {
        ItemStack itemStack = itemEntity.getItem();
        if (this.func_230293_i_(itemStack)) {
            Inventory inventory = this.getVillagerInventory();
            boolean bl = inventory.func_233541_b_(itemStack);
            if (!bl) {
                return;
            }
            this.triggerItemPickupTrigger(itemEntity);
            this.onItemPickup(itemEntity, itemStack.getCount());
            ItemStack itemStack2 = inventory.addItem(itemStack);
            if (itemStack2.isEmpty()) {
                itemEntity.remove();
            } else {
                itemStack.setCount(itemStack2.getCount());
            }
        }
    }

    @Override
    public boolean func_230293_i_(ItemStack itemStack) {
        Item item = itemStack.getItem();
        return (ALLOWED_INVENTORY_ITEMS.contains(item) || this.getVillagerData().getProfession().getSpecificItems().contains(item)) && this.getVillagerInventory().func_233541_b_(itemStack);
    }

    public boolean canAbondonItems() {
        return this.getFoodValueFromInventory() >= 24;
    }

    public boolean wantsMoreFood() {
        return this.getFoodValueFromInventory() < 12;
    }

    private int getFoodValueFromInventory() {
        Inventory inventory = this.getVillagerInventory();
        return FOOD_VALUES.entrySet().stream().mapToInt(arg_0 -> VillagerEntity.lambda$getFoodValueFromInventory$9(inventory, arg_0)).sum();
    }

    public boolean isFarmItemInInventory() {
        return this.getVillagerInventory().hasAny(ImmutableSet.of(Items.WHEAT_SEEDS, Items.POTATO, Items.CARROT, Items.BEETROOT_SEEDS));
    }

    @Override
    protected void populateTradeData() {
        VillagerTrades.ITrade[] iTradeArray;
        VillagerData villagerData = this.getVillagerData();
        Int2ObjectMap<VillagerTrades.ITrade[]> int2ObjectMap = VillagerTrades.VILLAGER_DEFAULT_TRADES.get(villagerData.getProfession());
        if (int2ObjectMap != null && !int2ObjectMap.isEmpty() && (iTradeArray = (VillagerTrades.ITrade[])int2ObjectMap.get(villagerData.getLevel())) != null) {
            MerchantOffers merchantOffers = this.getOffers();
            this.addTrades(merchantOffers, iTradeArray, 2);
        }
    }

    public void func_242368_a(ServerWorld serverWorld, VillagerEntity villagerEntity, long l) {
        if (!(l >= this.lastGossipTime && l < this.lastGossipTime + 1200L || l >= villagerEntity.lastGossipTime && l < villagerEntity.lastGossipTime + 1200L)) {
            this.gossip.transferFrom(villagerEntity.gossip, this.rand, 10);
            this.lastGossipTime = l;
            villagerEntity.lastGossipTime = l;
            this.func_242367_a(serverWorld, l, 5);
        }
    }

    private void tickGossip() {
        long l = this.world.getGameTime();
        if (this.lastGossipDecay == 0L) {
            this.lastGossipDecay = l;
        } else if (l >= this.lastGossipDecay + 24000L) {
            this.gossip.tick();
            this.lastGossipDecay = l;
        }
    }

    public void func_242367_a(ServerWorld serverWorld, long l, int n) {
        IronGolemEntity ironGolemEntity;
        AxisAlignedBB axisAlignedBB;
        List<VillagerEntity> list;
        List list2;
        if (this.canSpawnGolems(l) && (list2 = (list = serverWorld.getEntitiesWithinAABB(VillagerEntity.class, axisAlignedBB = this.getBoundingBox().grow(10.0, 10.0, 10.0))).stream().filter(arg_0 -> VillagerEntity.lambda$func_242367_a$10(l, arg_0)).limit(5L).collect(Collectors.toList())).size() >= n && (ironGolemEntity = this.trySpawnGolem(serverWorld)) != null) {
            list.forEach(GolemLastSeenSensor::reset);
        }
    }

    public boolean canSpawnGolems(long l) {
        if (!this.hasSleptAndWorkedRecently(this.world.getGameTime())) {
            return true;
        }
        return !this.brain.hasMemory(MemoryModuleType.GOLEM_DETECTED_RECENTLY);
    }

    @Nullable
    private IronGolemEntity trySpawnGolem(ServerWorld serverWorld) {
        BlockPos blockPos = this.getPosition();
        for (int i = 0; i < 10; ++i) {
            IronGolemEntity ironGolemEntity;
            double d;
            double d2 = serverWorld.rand.nextInt(16) - 8;
            BlockPos blockPos2 = this.getValidGolemSpawnPosition(blockPos, d2, d = (double)(serverWorld.rand.nextInt(16) - 8));
            if (blockPos2 == null || (ironGolemEntity = EntityType.IRON_GOLEM.create(serverWorld, null, null, null, blockPos2, SpawnReason.MOB_SUMMONED, false, true)) == null) continue;
            if (ironGolemEntity.canSpawn(serverWorld, SpawnReason.MOB_SUMMONED) && ironGolemEntity.isNotColliding(serverWorld)) {
                serverWorld.func_242417_l(ironGolemEntity);
                return ironGolemEntity;
            }
            ironGolemEntity.remove();
        }
        return null;
    }

    @Nullable
    private BlockPos getValidGolemSpawnPosition(BlockPos blockPos, double d, double d2) {
        int n = 6;
        BlockPos blockPos2 = blockPos.add(d, 6.0, d2);
        BlockState blockState = this.world.getBlockState(blockPos2);
        for (int i = 6; i >= -6; --i) {
            BlockPos blockPos3 = blockPos2;
            BlockState blockState2 = blockState;
            blockPos2 = blockPos2.down();
            blockState = this.world.getBlockState(blockPos2);
            if (!blockState2.isAir() && !blockState2.getMaterial().isLiquid() || !blockState.getMaterial().isOpaque()) continue;
            return blockPos3;
        }
        return null;
    }

    @Override
    public void updateReputation(IReputationType iReputationType, Entity entity2) {
        if (iReputationType == IReputationType.ZOMBIE_VILLAGER_CURED) {
            this.gossip.add(entity2.getUniqueID(), GossipType.MAJOR_POSITIVE, 20);
            this.gossip.add(entity2.getUniqueID(), GossipType.MINOR_POSITIVE, 25);
        } else if (iReputationType == IReputationType.TRADE) {
            this.gossip.add(entity2.getUniqueID(), GossipType.TRADING, 2);
        } else if (iReputationType == IReputationType.VILLAGER_HURT) {
            this.gossip.add(entity2.getUniqueID(), GossipType.MINOR_NEGATIVE, 25);
        } else if (iReputationType == IReputationType.VILLAGER_KILLED) {
            this.gossip.add(entity2.getUniqueID(), GossipType.MAJOR_NEGATIVE, 25);
        }
    }

    @Override
    public int getXp() {
        return this.xp;
    }

    public void setXp(int n) {
        this.xp = n;
    }

    private void func_223718_eH() {
        this.resetOffersAndAdjustForDemand();
        this.restocksToday = 0;
    }

    public GossipManager getGossip() {
        return this.gossip;
    }

    public void setGossips(INBT iNBT) {
        this.gossip.read(new Dynamic<INBT>(NBTDynamicOps.INSTANCE, iNBT));
    }

    @Override
    protected void sendDebugPackets() {
        super.sendDebugPackets();
        DebugPacketSender.sendLivingEntity(this);
    }

    @Override
    public void startSleeping(BlockPos blockPos) {
        super.startSleeping(blockPos);
        this.brain.setMemory(MemoryModuleType.LAST_SLEPT, this.world.getGameTime());
        this.brain.removeMemory(MemoryModuleType.WALK_TARGET);
        this.brain.removeMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
    }

    @Override
    public void wakeUp() {
        super.wakeUp();
        this.brain.setMemory(MemoryModuleType.LAST_WOKEN, this.world.getGameTime());
    }

    private boolean hasSleptAndWorkedRecently(long l) {
        Optional<Long> optional = this.brain.getMemory(MemoryModuleType.LAST_SLEPT);
        if (optional.isPresent()) {
            return l - optional.get() < 24000L;
        }
        return true;
    }

    @Override
    public AgeableEntity func_241840_a(ServerWorld serverWorld, AgeableEntity ageableEntity) {
        return this.func_241840_a(serverWorld, ageableEntity);
    }

    private static boolean lambda$func_242367_a$10(long l, VillagerEntity villagerEntity) {
        return villagerEntity.canSpawnGolems(l);
    }

    private static int lambda$getFoodValueFromInventory$9(Inventory inventory, Map.Entry entry) {
        return inventory.count((Item)entry.getKey()) * (Integer)entry.getValue();
    }

    private static boolean lambda$getPlayerReputation$8(GossipType gossipType) {
        return false;
    }

    private void lambda$resetMemoryPoint$7(MinecraftServer minecraftServer, MemoryModuleType memoryModuleType, GlobalPos globalPos) {
        ServerWorld serverWorld = minecraftServer.getWorld(globalPos.getDimension());
        if (serverWorld != null) {
            PointOfInterestManager pointOfInterestManager = serverWorld.getPointOfInterestManager();
            Optional<PointOfInterestType> optional = pointOfInterestManager.getType(globalPos.getPos());
            BiPredicate<VillagerEntity, PointOfInterestType> biPredicate = JOB_SITE_PREDICATE_MAP.get(memoryModuleType);
            if (optional.isPresent() && biPredicate.test(this, optional.get())) {
                pointOfInterestManager.release(globalPos.getPos());
                DebugPacketSender.func_218801_c(serverWorld, globalPos.getPos());
            }
        }
    }

    private static void lambda$sawMurder$6(ServerWorld serverWorld, Entity entity2, LivingEntity livingEntity) {
        serverWorld.updateReputation(IReputationType.VILLAGER_KILLED, entity2, (IReputationTracking)((Object)livingEntity));
    }

    private static boolean lambda$sawMurder$5(LivingEntity livingEntity) {
        return livingEntity instanceof IReputationTracking;
    }

    private static void lambda$writeAdditional$4(CompoundNBT compoundNBT, INBT iNBT) {
        compoundNBT.put("VillagerData", iNBT);
    }

    private static boolean lambda$static$3(VillagerEntity villagerEntity, PointOfInterestType pointOfInterestType) {
        return pointOfInterestType == PointOfInterestType.MEETING;
    }

    private static boolean lambda$static$2(VillagerEntity villagerEntity, PointOfInterestType pointOfInterestType) {
        return PointOfInterestType.ANY_VILLAGER_WORKSTATION.test(pointOfInterestType);
    }

    private static boolean lambda$static$1(VillagerEntity villagerEntity, PointOfInterestType pointOfInterestType) {
        return villagerEntity.getVillagerData().getProfession().getPointOfInterest() == pointOfInterestType;
    }

    private static boolean lambda$static$0(VillagerEntity villagerEntity, PointOfInterestType pointOfInterestType) {
        return pointOfInterestType == PointOfInterestType.HOME;
    }
}

