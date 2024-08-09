/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.raid;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.monster.AbstractRaiderEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.play.server.SPlaySoundEffectPacket;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.BannerPattern;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.SectionPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.BossInfo;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerBossInfo;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.spawner.WorldEntitySpawner;

public class Raid {
    private static final ITextComponent RAID = new TranslationTextComponent("event.minecraft.raid");
    private static final ITextComponent VICTORY = new TranslationTextComponent("event.minecraft.raid.victory");
    private static final ITextComponent DEFEAT = new TranslationTextComponent("event.minecraft.raid.defeat");
    private static final ITextComponent RAID_VICTORY = RAID.deepCopy().appendString(" - ").append(VICTORY);
    private static final ITextComponent RAID_DEFEAT = RAID.deepCopy().appendString(" - ").append(DEFEAT);
    private final Map<Integer, AbstractRaiderEntity> leaders = Maps.newHashMap();
    private final Map<Integer, Set<AbstractRaiderEntity>> raiders = Maps.newHashMap();
    private final Set<UUID> heroes = Sets.newHashSet();
    private long ticksActive;
    private BlockPos center;
    private final ServerWorld world;
    private boolean started;
    private final int id;
    private float totalHealth;
    private int badOmenLevel;
    private boolean active;
    private int groupsSpawned;
    private final ServerBossInfo bossInfo = new ServerBossInfo(RAID, BossInfo.Color.RED, BossInfo.Overlay.NOTCHED_10);
    private int postRaidTicks;
    private int preRaidTicks;
    private final Random random = new Random();
    private final int numGroups;
    private Status status;
    private int celebrationTicks;
    private Optional<BlockPos> waveSpawnPos = Optional.empty();

    public Raid(int n, ServerWorld serverWorld, BlockPos blockPos) {
        this.id = n;
        this.world = serverWorld;
        this.active = true;
        this.preRaidTicks = 300;
        this.bossInfo.setPercent(0.0f);
        this.center = blockPos;
        this.numGroups = this.getWaves(serverWorld.getDifficulty());
        this.status = Status.ONGOING;
    }

    public Raid(ServerWorld serverWorld, CompoundNBT compoundNBT) {
        this.world = serverWorld;
        this.id = compoundNBT.getInt("Id");
        this.started = compoundNBT.getBoolean("Started");
        this.active = compoundNBT.getBoolean("Active");
        this.ticksActive = compoundNBT.getLong("TicksActive");
        this.badOmenLevel = compoundNBT.getInt("BadOmenLevel");
        this.groupsSpawned = compoundNBT.getInt("GroupsSpawned");
        this.preRaidTicks = compoundNBT.getInt("PreRaidTicks");
        this.postRaidTicks = compoundNBT.getInt("PostRaidTicks");
        this.totalHealth = compoundNBT.getFloat("TotalHealth");
        this.center = new BlockPos(compoundNBT.getInt("CX"), compoundNBT.getInt("CY"), compoundNBT.getInt("CZ"));
        this.numGroups = compoundNBT.getInt("NumGroups");
        this.status = Status.getByName(compoundNBT.getString("Status"));
        this.heroes.clear();
        if (compoundNBT.contains("HeroesOfTheVillage", 0)) {
            ListNBT listNBT = compoundNBT.getList("HeroesOfTheVillage", 11);
            for (int i = 0; i < listNBT.size(); ++i) {
                this.heroes.add(NBTUtil.readUniqueId(listNBT.get(i)));
            }
        }
    }

    public boolean isOver() {
        return this.isVictory() || this.isLoss();
    }

    public boolean isBetweenWaves() {
        return this.func_221297_c() && this.getRaiderCount() == 0 && this.preRaidTicks > 0;
    }

    public boolean func_221297_c() {
        return this.groupsSpawned > 0;
    }

    public boolean isStopped() {
        return this.status == Status.STOPPED;
    }

    public boolean isVictory() {
        return this.status == Status.VICTORY;
    }

    public boolean isLoss() {
        return this.status == Status.LOSS;
    }

    public World getWorld() {
        return this.world;
    }

    public boolean isStarted() {
        return this.started;
    }

    public int getGroupsSpawned() {
        return this.groupsSpawned;
    }

    private Predicate<ServerPlayerEntity> getParticipantsPredicate() {
        return this::lambda$getParticipantsPredicate$0;
    }

    private void updateBossInfoVisibility() {
        HashSet<ServerPlayerEntity> hashSet = Sets.newHashSet(this.bossInfo.getPlayers());
        List<ServerPlayerEntity> list = this.world.getPlayers(this.getParticipantsPredicate());
        for (ServerPlayerEntity serverPlayerEntity : list) {
            if (hashSet.contains(serverPlayerEntity)) continue;
            this.bossInfo.addPlayer(serverPlayerEntity);
        }
        for (ServerPlayerEntity serverPlayerEntity : hashSet) {
            if (list.contains(serverPlayerEntity)) continue;
            this.bossInfo.removePlayer(serverPlayerEntity);
        }
    }

    public int getMaxLevel() {
        return 0;
    }

    public int getBadOmenLevel() {
        return this.badOmenLevel;
    }

    public void increaseLevel(PlayerEntity playerEntity) {
        if (playerEntity.isPotionActive(Effects.BAD_OMEN)) {
            this.badOmenLevel += playerEntity.getActivePotionEffect(Effects.BAD_OMEN).getAmplifier() + 1;
            this.badOmenLevel = MathHelper.clamp(this.badOmenLevel, 0, this.getMaxLevel());
        }
        playerEntity.removePotionEffect(Effects.BAD_OMEN);
    }

    public void stop() {
        this.active = false;
        this.bossInfo.removeAllPlayers();
        this.status = Status.STOPPED;
    }

    public void tick() {
        if (!this.isStopped()) {
            if (this.status == Status.ONGOING) {
                int n;
                boolean bl;
                boolean bl2 = this.active;
                this.active = this.world.isBlockLoaded(this.center);
                if (this.world.getDifficulty() == Difficulty.PEACEFUL) {
                    this.stop();
                    return;
                }
                if (bl2 != this.active) {
                    this.bossInfo.setVisible(this.active);
                }
                if (!this.active) {
                    return;
                }
                if (!this.world.isVillage(this.center)) {
                    this.moveRaidCenterToNearbyVillageSection();
                }
                if (!this.world.isVillage(this.center)) {
                    if (this.groupsSpawned > 0) {
                        this.status = Status.LOSS;
                    } else {
                        this.stop();
                    }
                }
                ++this.ticksActive;
                if (this.ticksActive >= 48000L) {
                    this.stop();
                    return;
                }
                int n2 = this.getRaiderCount();
                if (n2 == 0 && this.hasMoreWaves()) {
                    if (this.preRaidTicks <= 0) {
                        if (this.preRaidTicks == 0 && this.groupsSpawned > 0) {
                            this.preRaidTicks = 300;
                            this.bossInfo.setName(RAID);
                            return;
                        }
                    } else {
                        bl = this.waveSpawnPos.isPresent();
                        int n3 = n = !bl && this.preRaidTicks % 5 == 0 ? 1 : 0;
                        if (bl && !this.world.getChunkProvider().isChunkLoaded(new ChunkPos(this.waveSpawnPos.get()))) {
                            n = 1;
                        }
                        if (n != 0) {
                            int n4 = 0;
                            if (this.preRaidTicks < 100) {
                                n4 = 1;
                            } else if (this.preRaidTicks < 40) {
                                n4 = 2;
                            }
                            this.waveSpawnPos = this.getValidSpawnPos(n4);
                        }
                        if (this.preRaidTicks == 300 || this.preRaidTicks % 20 == 0) {
                            this.updateBossInfoVisibility();
                        }
                        --this.preRaidTicks;
                        this.bossInfo.setPercent(MathHelper.clamp((float)(300 - this.preRaidTicks) / 300.0f, 0.0f, 1.0f));
                    }
                }
                if (this.ticksActive % 20L == 0L) {
                    this.updateBossInfoVisibility();
                    this.updateRaiders();
                    if (n2 > 0) {
                        if (n2 <= 2) {
                            this.bossInfo.setName(RAID.deepCopy().appendString(" - ").append(new TranslationTextComponent("event.minecraft.raid.raiders_remaining", n2)));
                        } else {
                            this.bossInfo.setName(RAID);
                        }
                    } else {
                        this.bossInfo.setName(RAID);
                    }
                }
                bl = false;
                n = 0;
                while (this.shouldSpawnGroup()) {
                    BlockPos blockPos;
                    BlockPos blockPos2 = blockPos = this.waveSpawnPos.isPresent() ? this.waveSpawnPos.get() : this.findRandomSpawnPos(n, 20);
                    if (blockPos != null) {
                        this.started = true;
                        this.spawnNextWave(blockPos);
                        if (!bl) {
                            this.playWaveStartSound(blockPos);
                            bl = true;
                        }
                    } else {
                        ++n;
                    }
                    if (n <= 3) continue;
                    this.stop();
                    break;
                }
                if (this.isStarted() && !this.hasMoreWaves() && n2 == 0) {
                    if (this.postRaidTicks < 40) {
                        ++this.postRaidTicks;
                    } else {
                        this.status = Status.VICTORY;
                        for (UUID uUID : this.heroes) {
                            Entity entity2 = this.world.getEntityByUuid(uUID);
                            if (!(entity2 instanceof LivingEntity) || entity2.isSpectator()) continue;
                            LivingEntity livingEntity = (LivingEntity)entity2;
                            livingEntity.addPotionEffect(new EffectInstance(Effects.HERO_OF_THE_VILLAGE, 48000, this.badOmenLevel - 1, false, false, true));
                            if (!(livingEntity instanceof ServerPlayerEntity)) continue;
                            ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)livingEntity;
                            serverPlayerEntity.addStat(Stats.RAID_WIN);
                            CriteriaTriggers.HERO_OF_THE_VILLAGE.trigger(serverPlayerEntity);
                        }
                    }
                }
                this.markDirty();
            } else if (this.isOver()) {
                ++this.celebrationTicks;
                if (this.celebrationTicks >= 600) {
                    this.stop();
                    return;
                }
                if (this.celebrationTicks % 20 == 0) {
                    this.updateBossInfoVisibility();
                    this.bossInfo.setVisible(false);
                    if (this.isVictory()) {
                        this.bossInfo.setPercent(0.0f);
                        this.bossInfo.setName(RAID_VICTORY);
                    } else {
                        this.bossInfo.setName(RAID_DEFEAT);
                    }
                }
            }
        }
    }

    private void moveRaidCenterToNearbyVillageSection() {
        Stream<SectionPos> stream = SectionPos.getAllInBox(SectionPos.from(this.center), 2);
        stream.filter(this.world::isVillage).map(SectionPos::getCenter).min(Comparator.comparingDouble(this::lambda$moveRaidCenterToNearbyVillageSection$1)).ifPresent(this::setCenter);
    }

    private Optional<BlockPos> getValidSpawnPos(int n) {
        for (int i = 0; i < 3; ++i) {
            BlockPos blockPos = this.findRandomSpawnPos(n, 1);
            if (blockPos == null) continue;
            return Optional.of(blockPos);
        }
        return Optional.empty();
    }

    private boolean hasMoreWaves() {
        if (this.hasBonusWave()) {
            return !this.hasSpawnedBonusWave();
        }
        return !this.isFinalWave();
    }

    private boolean isFinalWave() {
        return this.getGroupsSpawned() == this.numGroups;
    }

    private boolean hasBonusWave() {
        return this.badOmenLevel > 1;
    }

    private boolean hasSpawnedBonusWave() {
        return this.getGroupsSpawned() > this.numGroups;
    }

    private boolean shouldSpawnBonusGroup() {
        return this.isFinalWave() && this.getRaiderCount() == 0 && this.hasBonusWave();
    }

    private void updateRaiders() {
        Iterator<Set<AbstractRaiderEntity>> iterator2 = this.raiders.values().iterator();
        HashSet<AbstractRaiderEntity> hashSet = Sets.newHashSet();
        while (iterator2.hasNext()) {
            Set<AbstractRaiderEntity> set = iterator2.next();
            Iterator object = set.iterator();
            while (object.hasNext()) {
                AbstractRaiderEntity abstractRaiderEntity = (AbstractRaiderEntity)object.next();
                BlockPos blockPos = abstractRaiderEntity.getPosition();
                if (!abstractRaiderEntity.removed && abstractRaiderEntity.world.getDimensionKey() == this.world.getDimensionKey() && !(this.center.distanceSq(blockPos) >= 12544.0)) {
                    if (abstractRaiderEntity.ticksExisted <= 600) continue;
                    if (this.world.getEntityByUuid(abstractRaiderEntity.getUniqueID()) == null) {
                        hashSet.add(abstractRaiderEntity);
                    }
                    if (!this.world.isVillage(blockPos) && abstractRaiderEntity.getIdleTime() > 2400) {
                        abstractRaiderEntity.setJoinDelay(abstractRaiderEntity.getJoinDelay() + 1);
                    }
                    if (abstractRaiderEntity.getJoinDelay() < 30) continue;
                    hashSet.add(abstractRaiderEntity);
                    continue;
                }
                hashSet.add(abstractRaiderEntity);
            }
        }
        for (AbstractRaiderEntity abstractRaiderEntity : hashSet) {
            this.leaveRaid(abstractRaiderEntity, false);
        }
    }

    private void playWaveStartSound(BlockPos blockPos) {
        float f = 13.0f;
        int n = 64;
        Collection<ServerPlayerEntity> collection = this.bossInfo.getPlayers();
        for (ServerPlayerEntity serverPlayerEntity : this.world.getPlayers()) {
            Vector3d vector3d = serverPlayerEntity.getPositionVec();
            Vector3d vector3d2 = Vector3d.copyCentered(blockPos);
            float f2 = MathHelper.sqrt((vector3d2.x - vector3d.x) * (vector3d2.x - vector3d.x) + (vector3d2.z - vector3d.z) * (vector3d2.z - vector3d.z));
            double d = vector3d.x + (double)(13.0f / f2) * (vector3d2.x - vector3d.x);
            double d2 = vector3d.z + (double)(13.0f / f2) * (vector3d2.z - vector3d.z);
            if (!(f2 <= 64.0f) && !collection.contains(serverPlayerEntity)) continue;
            serverPlayerEntity.connection.sendPacket(new SPlaySoundEffectPacket(SoundEvents.EVENT_RAID_HORN, SoundCategory.NEUTRAL, d, serverPlayerEntity.getPosY(), d2, 64.0f, 1.0f));
        }
    }

    private void spawnNextWave(BlockPos blockPos) {
        boolean bl = false;
        int n = this.groupsSpawned + 1;
        this.totalHealth = 0.0f;
        DifficultyInstance difficultyInstance = this.world.getDifficultyForLocation(blockPos);
        boolean bl2 = this.shouldSpawnBonusGroup();
        for (WaveMember waveMember : WaveMember.VALUES) {
            int n2 = this.getDefaultNumSpawns(waveMember, n, bl2) + this.getPotentialBonusSpawns(waveMember, this.random, n, difficultyInstance, bl2);
            int n3 = 0;
            for (int i = 0; i < n2; ++i) {
                AbstractRaiderEntity abstractRaiderEntity = waveMember.type.create(this.world);
                if (!bl && abstractRaiderEntity.canBeLeader()) {
                    abstractRaiderEntity.setLeader(false);
                    this.setLeader(n, abstractRaiderEntity);
                    bl = true;
                }
                this.joinRaid(n, abstractRaiderEntity, blockPos, true);
                if (waveMember.type != EntityType.RAVAGER) continue;
                AbstractRaiderEntity abstractRaiderEntity2 = null;
                if (n == this.getWaves(Difficulty.NORMAL)) {
                    abstractRaiderEntity2 = EntityType.PILLAGER.create(this.world);
                } else if (n >= this.getWaves(Difficulty.HARD)) {
                    abstractRaiderEntity2 = n3 == 0 ? (AbstractRaiderEntity)EntityType.EVOKER.create(this.world) : (AbstractRaiderEntity)EntityType.VINDICATOR.create(this.world);
                }
                ++n3;
                if (abstractRaiderEntity2 == null) continue;
                this.joinRaid(n, abstractRaiderEntity2, blockPos, true);
                abstractRaiderEntity2.moveToBlockPosAndAngles(blockPos, 0.0f, 0.0f);
                abstractRaiderEntity2.startRiding(abstractRaiderEntity);
            }
        }
        this.waveSpawnPos = Optional.empty();
        ++this.groupsSpawned;
        this.updateBarPercentage();
        this.markDirty();
    }

    public void joinRaid(int n, AbstractRaiderEntity abstractRaiderEntity, @Nullable BlockPos blockPos, boolean bl) {
        boolean bl2 = this.joinRaid(n, abstractRaiderEntity);
        if (bl2) {
            abstractRaiderEntity.setRaid(this);
            abstractRaiderEntity.setWave(n);
            abstractRaiderEntity.setCanJoinRaid(false);
            abstractRaiderEntity.setJoinDelay(0);
            if (!bl && blockPos != null) {
                abstractRaiderEntity.setPosition((double)blockPos.getX() + 0.5, (double)blockPos.getY() + 1.0, (double)blockPos.getZ() + 0.5);
                abstractRaiderEntity.onInitialSpawn(this.world, this.world.getDifficultyForLocation(blockPos), SpawnReason.EVENT, null, null);
                abstractRaiderEntity.applyWaveBonus(n, true);
                abstractRaiderEntity.setOnGround(false);
                this.world.func_242417_l(abstractRaiderEntity);
            }
        }
    }

    public void updateBarPercentage() {
        this.bossInfo.setPercent(MathHelper.clamp(this.getCurrentHealth() / this.totalHealth, 0.0f, 1.0f));
    }

    public float getCurrentHealth() {
        float f = 0.0f;
        for (Set<AbstractRaiderEntity> set : this.raiders.values()) {
            for (AbstractRaiderEntity abstractRaiderEntity : set) {
                f += abstractRaiderEntity.getHealth();
            }
        }
        return f;
    }

    private boolean shouldSpawnGroup() {
        return this.preRaidTicks == 0 && (this.groupsSpawned < this.numGroups || this.shouldSpawnBonusGroup()) && this.getRaiderCount() == 0;
    }

    public int getRaiderCount() {
        return this.raiders.values().stream().mapToInt(Set::size).sum();
    }

    public void leaveRaid(AbstractRaiderEntity abstractRaiderEntity, boolean bl) {
        boolean bl2;
        Set<AbstractRaiderEntity> set = this.raiders.get(abstractRaiderEntity.getWave());
        if (set != null && (bl2 = set.remove(abstractRaiderEntity))) {
            if (bl) {
                this.totalHealth -= abstractRaiderEntity.getHealth();
            }
            abstractRaiderEntity.setRaid(null);
            this.updateBarPercentage();
            this.markDirty();
        }
    }

    private void markDirty() {
        this.world.getRaids().markDirty();
    }

    public static ItemStack createIllagerBanner() {
        ItemStack itemStack = new ItemStack(Items.WHITE_BANNER);
        CompoundNBT compoundNBT = itemStack.getOrCreateChildTag("BlockEntityTag");
        ListNBT listNBT = new BannerPattern.Builder().setPatternWithColor(BannerPattern.RHOMBUS_MIDDLE, DyeColor.CYAN).setPatternWithColor(BannerPattern.STRIPE_BOTTOM, DyeColor.LIGHT_GRAY).setPatternWithColor(BannerPattern.STRIPE_CENTER, DyeColor.GRAY).setPatternWithColor(BannerPattern.BORDER, DyeColor.LIGHT_GRAY).setPatternWithColor(BannerPattern.STRIPE_MIDDLE, DyeColor.BLACK).setPatternWithColor(BannerPattern.HALF_HORIZONTAL, DyeColor.LIGHT_GRAY).setPatternWithColor(BannerPattern.CIRCLE_MIDDLE, DyeColor.LIGHT_GRAY).setPatternWithColor(BannerPattern.BORDER, DyeColor.BLACK).buildNBT();
        compoundNBT.put("Patterns", listNBT);
        itemStack.func_242395_a(ItemStack.TooltipDisplayFlags.ADDITIONAL);
        itemStack.setDisplayName(new TranslationTextComponent("block.minecraft.ominous_banner").mergeStyle(TextFormatting.GOLD));
        return itemStack;
    }

    @Nullable
    public AbstractRaiderEntity getLeader(int n) {
        return this.leaders.get(n);
    }

    @Nullable
    private BlockPos findRandomSpawnPos(int n, int n2) {
        int n3 = n == 0 ? 2 : 2 - n;
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (int i = 0; i < n2; ++i) {
            float f = this.world.rand.nextFloat() * ((float)Math.PI * 2);
            int n4 = this.center.getX() + MathHelper.floor(MathHelper.cos(f) * 32.0f * (float)n3) + this.world.rand.nextInt(5);
            int n5 = this.center.getZ() + MathHelper.floor(MathHelper.sin(f) * 32.0f * (float)n3) + this.world.rand.nextInt(5);
            int n6 = this.world.getHeight(Heightmap.Type.WORLD_SURFACE, n4, n5);
            mutable.setPos(n4, n6, n5);
            if (this.world.isVillage(mutable) && n < 2 || !this.world.isAreaLoaded(mutable.getX() - 10, mutable.getY() - 10, mutable.getZ() - 10, mutable.getX() + 10, mutable.getY() + 10, mutable.getZ() + 10) || !this.world.getChunkProvider().isChunkLoaded(new ChunkPos(mutable)) || !WorldEntitySpawner.canCreatureTypeSpawnAtLocation(EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, this.world, mutable, EntityType.RAVAGER) && (!this.world.getBlockState((BlockPos)mutable.down()).isIn(Blocks.SNOW) || !this.world.getBlockState(mutable).isAir())) continue;
            return mutable;
        }
        return null;
    }

    private boolean joinRaid(int n, AbstractRaiderEntity abstractRaiderEntity) {
        return this.joinRaid(n, abstractRaiderEntity, false);
    }

    public boolean joinRaid(int n, AbstractRaiderEntity abstractRaiderEntity, boolean bl) {
        this.raiders.computeIfAbsent(n, Raid::lambda$joinRaid$2);
        Set<AbstractRaiderEntity> set = this.raiders.get(n);
        AbstractRaiderEntity abstractRaiderEntity2 = null;
        for (AbstractRaiderEntity abstractRaiderEntity3 : set) {
            if (!abstractRaiderEntity3.getUniqueID().equals(abstractRaiderEntity.getUniqueID())) continue;
            abstractRaiderEntity2 = abstractRaiderEntity3;
            break;
        }
        if (abstractRaiderEntity2 != null) {
            set.remove(abstractRaiderEntity2);
            set.add(abstractRaiderEntity);
        }
        set.add(abstractRaiderEntity);
        if (bl) {
            this.totalHealth += abstractRaiderEntity.getHealth();
        }
        this.updateBarPercentage();
        this.markDirty();
        return false;
    }

    public void setLeader(int n, AbstractRaiderEntity abstractRaiderEntity) {
        this.leaders.put(n, abstractRaiderEntity);
        abstractRaiderEntity.setItemStackToSlot(EquipmentSlotType.HEAD, Raid.createIllagerBanner());
        abstractRaiderEntity.setDropChance(EquipmentSlotType.HEAD, 2.0f);
    }

    public void removeLeader(int n) {
        this.leaders.remove(n);
    }

    public BlockPos getCenter() {
        return this.center;
    }

    private void setCenter(BlockPos blockPos) {
        this.center = blockPos;
    }

    public int getId() {
        return this.id;
    }

    private int getDefaultNumSpawns(WaveMember waveMember, int n, boolean bl) {
        return bl ? waveMember.waveCounts[this.numGroups] : waveMember.waveCounts[n];
    }

    private int getPotentialBonusSpawns(WaveMember waveMember, Random random2, int n, DifficultyInstance difficultyInstance, boolean bl) {
        int n2;
        Difficulty difficulty = difficultyInstance.getDifficulty();
        boolean bl2 = difficulty == Difficulty.EASY;
        boolean bl3 = difficulty == Difficulty.NORMAL;
        switch (waveMember) {
            case WITCH: {
                if (bl2 || n <= 2 || n == 4) {
                    return 1;
                }
                n2 = 1;
                break;
            }
            case PILLAGER: 
            case VINDICATOR: {
                if (bl2) {
                    n2 = random2.nextInt(2);
                    break;
                }
                if (bl3) {
                    n2 = 1;
                    break;
                }
                n2 = 2;
                break;
            }
            case RAVAGER: {
                n2 = !bl2 && bl ? 1 : 0;
                break;
            }
            default: {
                return 1;
            }
        }
        return n2 > 0 ? random2.nextInt(n2 + 1) : 0;
    }

    public boolean isActive() {
        return this.active;
    }

    public CompoundNBT write(CompoundNBT compoundNBT) {
        compoundNBT.putInt("Id", this.id);
        compoundNBT.putBoolean("Started", this.started);
        compoundNBT.putBoolean("Active", this.active);
        compoundNBT.putLong("TicksActive", this.ticksActive);
        compoundNBT.putInt("BadOmenLevel", this.badOmenLevel);
        compoundNBT.putInt("GroupsSpawned", this.groupsSpawned);
        compoundNBT.putInt("PreRaidTicks", this.preRaidTicks);
        compoundNBT.putInt("PostRaidTicks", this.postRaidTicks);
        compoundNBT.putFloat("TotalHealth", this.totalHealth);
        compoundNBT.putInt("NumGroups", this.numGroups);
        compoundNBT.putString("Status", this.status.getName());
        compoundNBT.putInt("CX", this.center.getX());
        compoundNBT.putInt("CY", this.center.getY());
        compoundNBT.putInt("CZ", this.center.getZ());
        ListNBT listNBT = new ListNBT();
        for (UUID uUID : this.heroes) {
            listNBT.add(NBTUtil.func_240626_a_(uUID));
        }
        compoundNBT.put("HeroesOfTheVillage", listNBT);
        return compoundNBT;
    }

    public int getWaves(Difficulty difficulty) {
        switch (difficulty) {
            case EASY: {
                return 0;
            }
            case NORMAL: {
                return 0;
            }
            case HARD: {
                return 0;
            }
        }
        return 1;
    }

    public float getEnchantOdds() {
        int n = this.getBadOmenLevel();
        if (n == 2) {
            return 0.1f;
        }
        if (n == 3) {
            return 0.25f;
        }
        if (n == 4) {
            return 0.5f;
        }
        return n == 5 ? 0.75f : 0.0f;
    }

    public void addHero(Entity entity2) {
        this.heroes.add(entity2.getUniqueID());
    }

    private static Set lambda$joinRaid$2(Integer n) {
        return Sets.newHashSet();
    }

    private double lambda$moveRaidCenterToNearbyVillageSection$1(BlockPos blockPos) {
        return blockPos.distanceSq(this.center);
    }

    private boolean lambda$getParticipantsPredicate$0(ServerPlayerEntity serverPlayerEntity) {
        BlockPos blockPos = serverPlayerEntity.getPosition();
        return serverPlayerEntity.isAlive() && this.world.findRaid(blockPos) == this;
    }

    static enum Status {
        ONGOING,
        VICTORY,
        LOSS,
        STOPPED;

        private static final Status[] VALUES;

        private static Status getByName(String string) {
            for (Status status2 : VALUES) {
                if (!string.equalsIgnoreCase(status2.name())) continue;
                return status2;
            }
            return ONGOING;
        }

        public String getName() {
            return this.name().toLowerCase(Locale.ROOT);
        }

        static {
            VALUES = Status.values();
        }
    }

    static enum WaveMember {
        VINDICATOR(EntityType.VINDICATOR, new int[]{0, 0, 2, 0, 1, 4, 2, 5}),
        EVOKER(EntityType.EVOKER, new int[]{0, 0, 0, 0, 0, 1, 1, 2}),
        PILLAGER(EntityType.PILLAGER, new int[]{0, 4, 3, 3, 4, 4, 4, 2}),
        WITCH(EntityType.WITCH, new int[]{0, 0, 0, 0, 3, 0, 0, 1}),
        RAVAGER(EntityType.RAVAGER, new int[]{0, 0, 0, 1, 0, 1, 0, 2});

        private static final WaveMember[] VALUES;
        private final EntityType<? extends AbstractRaiderEntity> type;
        private final int[] waveCounts;

        private WaveMember(EntityType<? extends AbstractRaiderEntity> entityType, int[] nArray) {
            this.type = entityType;
            this.waveCounts = nArray;
        }

        static {
            VALUES = WaveMember.values();
        }
    }
}

