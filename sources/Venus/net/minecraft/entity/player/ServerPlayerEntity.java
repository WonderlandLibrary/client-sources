/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.player;

import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.datafixers.util.Either;
import io.netty.util.concurrent.Future;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Random;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.NetherPortalBlock;
import net.minecraft.block.PortalInfo;
import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ReportedException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IAngerable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.player.ChatVisibility;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.SpawnLocationHelper;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.CraftingResultSlot;
import net.minecraft.inventory.container.HorseInventoryContainer;
import net.minecraft.inventory.container.IContainerListener;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.AbstractMapItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MerchantOffers;
import net.minecraft.item.WrittenBookItem;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ServerRecipeBook;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.NBTDynamicOps;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.ServerPlayNetHandler;
import net.minecraft.network.play.client.CClientSettingsPacket;
import net.minecraft.network.play.server.SAnimateHandPacket;
import net.minecraft.network.play.server.SCameraPacket;
import net.minecraft.network.play.server.SChangeGameStatePacket;
import net.minecraft.network.play.server.SChatPacket;
import net.minecraft.network.play.server.SCloseWindowPacket;
import net.minecraft.network.play.server.SCombatPacket;
import net.minecraft.network.play.server.SDestroyEntitiesPacket;
import net.minecraft.network.play.server.SEntityStatusPacket;
import net.minecraft.network.play.server.SMerchantOffersPacket;
import net.minecraft.network.play.server.SOpenBookWindowPacket;
import net.minecraft.network.play.server.SOpenHorseWindowPacket;
import net.minecraft.network.play.server.SOpenSignMenuPacket;
import net.minecraft.network.play.server.SOpenWindowPacket;
import net.minecraft.network.play.server.SPlayEntityEffectPacket;
import net.minecraft.network.play.server.SPlaySoundEffectPacket;
import net.minecraft.network.play.server.SPlaySoundEventPacket;
import net.minecraft.network.play.server.SPlayerAbilitiesPacket;
import net.minecraft.network.play.server.SPlayerLookPacket;
import net.minecraft.network.play.server.SRemoveEntityEffectPacket;
import net.minecraft.network.play.server.SRespawnPacket;
import net.minecraft.network.play.server.SSendResourcePackPacket;
import net.minecraft.network.play.server.SServerDifficultyPacket;
import net.minecraft.network.play.server.SSetExperiencePacket;
import net.minecraft.network.play.server.SSetSlotPacket;
import net.minecraft.network.play.server.SSpawnPlayerPacket;
import net.minecraft.network.play.server.SUnloadChunkPacket;
import net.minecraft.network.play.server.SUpdateHealthPacket;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.network.play.server.SWindowItemsPacket;
import net.minecraft.network.play.server.SWindowPropertyPacket;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreCriteria;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerInteractionManager;
import net.minecraft.server.management.PlayerList;
import net.minecraft.stats.ServerStatisticsManager;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.CommandBlockTileEntity;
import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.CooldownTracker;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.NonNullList;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.ServerCooldownTracker;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.TeleportationRepositioner;
import net.minecraft.util.Unit;
import net.minecraft.util.Util;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.SectionPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraft.util.text.filter.IChatFilter;
import net.minecraft.world.GameRules;
import net.minecraft.world.GameType;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeManager;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.IWorldInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerPlayerEntity
extends PlayerEntity
implements IContainerListener {
    private static final Logger LOGGER = LogManager.getLogger();
    public ServerPlayNetHandler connection;
    public final MinecraftServer server;
    public final PlayerInteractionManager interactionManager;
    private final List<Integer> entityRemoveQueue = Lists.newLinkedList();
    private final PlayerAdvancements advancements;
    private final ServerStatisticsManager stats;
    private float lastHealthScore = Float.MIN_VALUE;
    private int lastFoodScore = Integer.MIN_VALUE;
    private int lastAirScore = Integer.MIN_VALUE;
    private int lastArmorScore = Integer.MIN_VALUE;
    private int lastLevelScore = Integer.MIN_VALUE;
    private int lastExperienceScore = Integer.MIN_VALUE;
    private float lastHealth = -1.0E8f;
    private int lastFoodLevel = -99999999;
    private boolean wasHungry = true;
    private int lastExperience = -99999999;
    private int respawnInvulnerabilityTicks = 60;
    private ChatVisibility chatVisibility;
    private boolean chatColours = true;
    private long playerLastActiveTime = Util.milliTime();
    private Entity spectatingEntity;
    private boolean invulnerableDimensionChange;
    private boolean seenCredits;
    private final ServerRecipeBook recipeBook = new ServerRecipeBook();
    private Vector3d levitationStartPos;
    private int levitatingSince;
    private boolean disconnected;
    @Nullable
    private Vector3d enteredNetherPosition;
    private SectionPos managedSectionPos = SectionPos.of(0, 0, 0);
    private RegistryKey<World> field_241137_cq_ = World.OVERWORLD;
    @Nullable
    private BlockPos field_241138_cr_;
    private boolean field_241139_cs_;
    private float field_242108_cn;
    @Nullable
    private final IChatFilter field_244528_co;
    private int currentWindowId;
    public boolean isChangingQuantityOnly;
    public int ping;
    public boolean queuedEndExit;

    public ServerPlayerEntity(MinecraftServer minecraftServer, ServerWorld serverWorld, GameProfile gameProfile, PlayerInteractionManager playerInteractionManager) {
        super(serverWorld, serverWorld.getSpawnPoint(), serverWorld.func_242107_v(), gameProfile);
        playerInteractionManager.player = this;
        this.interactionManager = playerInteractionManager;
        this.server = minecraftServer;
        this.stats = minecraftServer.getPlayerList().getPlayerStats(this);
        this.advancements = minecraftServer.getPlayerList().getPlayerAdvancements(this);
        this.stepHeight = 1.0f;
        this.func_205734_a(serverWorld);
        this.field_244528_co = minecraftServer.func_244435_a(this);
    }

    private void func_205734_a(ServerWorld serverWorld) {
        BlockPos blockPos = serverWorld.getSpawnPoint();
        if (serverWorld.getDimensionType().hasSkyLight() && serverWorld.getServer().func_240793_aU_().getGameType() != GameType.ADVENTURE) {
            long l;
            long l2;
            int n = Math.max(0, this.server.getSpawnRadius(serverWorld));
            int n2 = MathHelper.floor(serverWorld.getWorldBorder().getClosestDistance(blockPos.getX(), blockPos.getZ()));
            if (n2 < n) {
                n = n2;
            }
            if (n2 <= 1) {
                n = 1;
            }
            int n3 = (l2 = (l = (long)(n * 2 + 1)) * l) > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int)l2;
            int n4 = this.func_205735_q(n3);
            int n5 = new Random().nextInt(n3);
            for (int i = 0; i < n3; ++i) {
                int n6 = (n5 + n4 * i) % n3;
                int n7 = n6 % (n * 2 + 1);
                int n8 = n6 / (n * 2 + 1);
                BlockPos blockPos2 = SpawnLocationHelper.func_241092_a_(serverWorld, blockPos.getX() + n7 - n, blockPos.getZ() + n8 - n, false);
                if (blockPos2 == null) continue;
                this.moveToBlockPosAndAngles(blockPos2, 0.0f, 0.0f);
                if (!serverWorld.hasNoCollisions(this)) {
                    continue;
                }
                break;
            }
        } else {
            this.moveToBlockPosAndAngles(blockPos, 0.0f, 0.0f);
            while (!serverWorld.hasNoCollisions(this) && this.getPosY() < 255.0) {
                this.setPosition(this.getPosX(), this.getPosY() + 1.0, this.getPosZ());
            }
        }
    }

    private int func_205735_q(int n) {
        return n <= 16 ? n - 1 : 17;
    }

    @Override
    public void readAdditional(CompoundNBT compoundNBT) {
        super.readAdditional(compoundNBT);
        if (compoundNBT.contains("playerGameType", 0)) {
            if (this.getServer().getForceGamemode()) {
                this.interactionManager.func_241820_a(this.getServer().getGameType(), GameType.NOT_SET);
            } else {
                this.interactionManager.func_241820_a(GameType.getByID(compoundNBT.getInt("playerGameType")), compoundNBT.contains("previousPlayerGameType", 0) ? GameType.getByID(compoundNBT.getInt("previousPlayerGameType")) : GameType.NOT_SET);
            }
        }
        if (compoundNBT.contains("enteredNetherPosition", 1)) {
            CompoundNBT compoundNBT2 = compoundNBT.getCompound("enteredNetherPosition");
            this.enteredNetherPosition = new Vector3d(compoundNBT2.getDouble("x"), compoundNBT2.getDouble("y"), compoundNBT2.getDouble("z"));
        }
        this.seenCredits = compoundNBT.getBoolean("seenCredits");
        if (compoundNBT.contains("recipeBook", 1)) {
            this.recipeBook.read(compoundNBT.getCompound("recipeBook"), this.server.getRecipeManager());
        }
        if (this.isSleeping()) {
            this.wakeUp();
        }
        if (compoundNBT.contains("SpawnX", 0) && compoundNBT.contains("SpawnY", 0) && compoundNBT.contains("SpawnZ", 0)) {
            this.field_241138_cr_ = new BlockPos(compoundNBT.getInt("SpawnX"), compoundNBT.getInt("SpawnY"), compoundNBT.getInt("SpawnZ"));
            this.field_241139_cs_ = compoundNBT.getBoolean("SpawnForced");
            this.field_242108_cn = compoundNBT.getFloat("SpawnAngle");
            if (compoundNBT.contains("SpawnDimension")) {
                this.field_241137_cq_ = World.CODEC.parse(NBTDynamicOps.INSTANCE, compoundNBT.get("SpawnDimension")).resultOrPartial(LOGGER::error).orElse(World.OVERWORLD);
            }
        }
    }

    @Override
    public void writeAdditional(CompoundNBT compoundNBT) {
        Object object;
        super.writeAdditional(compoundNBT);
        compoundNBT.putInt("playerGameType", this.interactionManager.getGameType().getID());
        compoundNBT.putInt("previousPlayerGameType", this.interactionManager.func_241815_c_().getID());
        compoundNBT.putBoolean("seenCredits", this.seenCredits);
        if (this.enteredNetherPosition != null) {
            object = new CompoundNBT();
            ((CompoundNBT)object).putDouble("x", this.enteredNetherPosition.x);
            ((CompoundNBT)object).putDouble("y", this.enteredNetherPosition.y);
            ((CompoundNBT)object).putDouble("z", this.enteredNetherPosition.z);
            compoundNBT.put("enteredNetherPosition", (INBT)object);
        }
        object = this.getLowestRidingEntity();
        Entity entity2 = this.getRidingEntity();
        if (entity2 != null && object != this && ((Entity)object).isOnePlayerRiding()) {
            CompoundNBT compoundNBT2 = new CompoundNBT();
            CompoundNBT compoundNBT3 = new CompoundNBT();
            ((Entity)object).writeUnlessPassenger(compoundNBT3);
            compoundNBT2.putUniqueId("Attach", entity2.getUniqueID());
            compoundNBT2.put("Entity", compoundNBT3);
            compoundNBT.put("RootVehicle", compoundNBT2);
        }
        compoundNBT.put("recipeBook", this.recipeBook.write());
        compoundNBT.putString("Dimension", this.world.getDimensionKey().getLocation().toString());
        if (this.field_241138_cr_ != null) {
            compoundNBT.putInt("SpawnX", this.field_241138_cr_.getX());
            compoundNBT.putInt("SpawnY", this.field_241138_cr_.getY());
            compoundNBT.putInt("SpawnZ", this.field_241138_cr_.getZ());
            compoundNBT.putBoolean("SpawnForced", this.field_241139_cs_);
            compoundNBT.putFloat("SpawnAngle", this.field_242108_cn);
            ResourceLocation.CODEC.encodeStart(NBTDynamicOps.INSTANCE, this.field_241137_cq_.getLocation()).resultOrPartial(LOGGER::error).ifPresent(arg_0 -> ServerPlayerEntity.lambda$writeAdditional$0(compoundNBT, arg_0));
        }
    }

    public void func_195394_a(int n) {
        float f = this.xpBarCap();
        float f2 = (f - 1.0f) / f;
        this.experience = MathHelper.clamp((float)n / f, 0.0f, f2);
        this.lastExperience = -1;
    }

    public void setExperienceLevel(int n) {
        this.experienceLevel = n;
        this.lastExperience = -1;
    }

    @Override
    public void addExperienceLevel(int n) {
        super.addExperienceLevel(n);
        this.lastExperience = -1;
    }

    @Override
    public void onEnchant(ItemStack itemStack, int n) {
        super.onEnchant(itemStack, n);
        this.lastExperience = -1;
    }

    public void addSelfToInternalCraftingInventory() {
        this.openContainer.addListener(this);
    }

    @Override
    public void sendEnterCombat() {
        super.sendEnterCombat();
        this.connection.sendPacket(new SCombatPacket(this.getCombatTracker(), SCombatPacket.Event.ENTER_COMBAT));
    }

    @Override
    public void sendEndCombat() {
        super.sendEndCombat();
        this.connection.sendPacket(new SCombatPacket(this.getCombatTracker(), SCombatPacket.Event.END_COMBAT));
    }

    @Override
    protected void onInsideBlock(BlockState blockState) {
        CriteriaTriggers.ENTER_BLOCK.trigger(this, blockState);
    }

    @Override
    protected CooldownTracker createCooldownTracker() {
        return new ServerCooldownTracker(this);
    }

    @Override
    public void tick() {
        this.interactionManager.tick();
        --this.respawnInvulnerabilityTicks;
        if (this.hurtResistantTime > 0) {
            --this.hurtResistantTime;
        }
        this.openContainer.detectAndSendChanges();
        if (!this.world.isRemote && !this.openContainer.canInteractWith(this)) {
            this.closeScreen();
            this.openContainer = this.container;
        }
        while (!this.entityRemoveQueue.isEmpty()) {
            int n = Math.min(this.entityRemoveQueue.size(), Integer.MAX_VALUE);
            int[] nArray = new int[n];
            Iterator<Integer> iterator2 = this.entityRemoveQueue.iterator();
            int n2 = 0;
            while (iterator2.hasNext() && n2 < n) {
                nArray[n2++] = iterator2.next();
                iterator2.remove();
            }
            this.connection.sendPacket(new SDestroyEntitiesPacket(nArray));
        }
        Entity entity2 = this.getSpectatingEntity();
        if (entity2 != this) {
            if (entity2.isAlive()) {
                this.setPositionAndRotation(entity2.getPosX(), entity2.getPosY(), entity2.getPosZ(), entity2.rotationYaw, entity2.rotationPitch);
                this.getServerWorld().getChunkProvider().updatePlayerPosition(this);
                if (this.wantsToStopRiding()) {
                    this.setSpectatingEntity(this);
                }
            } else {
                this.setSpectatingEntity(this);
            }
        }
        CriteriaTriggers.TICK.trigger(this);
        if (this.levitationStartPos != null) {
            CriteriaTriggers.LEVITATION.trigger(this, this.levitationStartPos, this.ticksExisted - this.levitatingSince);
        }
        this.advancements.flushDirty(this);
    }

    public void playerTick() {
        try {
            if (!this.isSpectator() || this.world.isBlockLoaded(this.getPosition())) {
                super.tick();
            }
            for (int i = 0; i < this.inventory.getSizeInventory(); ++i) {
                IPacket<?> iPacket;
                ItemStack itemStack = this.inventory.getStackInSlot(i);
                if (!itemStack.getItem().isComplex() || (iPacket = ((AbstractMapItem)itemStack.getItem()).getUpdatePacket(itemStack, this.world, this)) == null) continue;
                this.connection.sendPacket(iPacket);
            }
            if (this.getHealth() != this.lastHealth || this.lastFoodLevel != this.foodStats.getFoodLevel() || this.foodStats.getSaturationLevel() == 0.0f != this.wasHungry) {
                this.connection.sendPacket(new SUpdateHealthPacket(this.getHealth(), this.foodStats.getFoodLevel(), this.foodStats.getSaturationLevel()));
                this.lastHealth = this.getHealth();
                this.lastFoodLevel = this.foodStats.getFoodLevel();
                boolean bl = this.wasHungry = this.foodStats.getSaturationLevel() == 0.0f;
            }
            if (this.getHealth() + this.getAbsorptionAmount() != this.lastHealthScore) {
                this.lastHealthScore = this.getHealth() + this.getAbsorptionAmount();
                this.updateScorePoints(ScoreCriteria.HEALTH, MathHelper.ceil(this.lastHealthScore));
            }
            if (this.foodStats.getFoodLevel() != this.lastFoodScore) {
                this.lastFoodScore = this.foodStats.getFoodLevel();
                this.updateScorePoints(ScoreCriteria.FOOD, MathHelper.ceil(this.lastFoodScore));
            }
            if (this.getAir() != this.lastAirScore) {
                this.lastAirScore = this.getAir();
                this.updateScorePoints(ScoreCriteria.AIR, MathHelper.ceil(this.lastAirScore));
            }
            if (this.getTotalArmorValue() != this.lastArmorScore) {
                this.lastArmorScore = this.getTotalArmorValue();
                this.updateScorePoints(ScoreCriteria.ARMOR, MathHelper.ceil(this.lastArmorScore));
            }
            if (this.experienceTotal != this.lastExperienceScore) {
                this.lastExperienceScore = this.experienceTotal;
                this.updateScorePoints(ScoreCriteria.XP, MathHelper.ceil(this.lastExperienceScore));
            }
            if (this.experienceLevel != this.lastLevelScore) {
                this.lastLevelScore = this.experienceLevel;
                this.updateScorePoints(ScoreCriteria.LEVEL, MathHelper.ceil(this.lastLevelScore));
            }
            if (this.experienceTotal != this.lastExperience) {
                this.lastExperience = this.experienceTotal;
                this.connection.sendPacket(new SSetExperiencePacket(this.experience, this.experienceTotal, this.experienceLevel));
            }
            if (this.ticksExisted % 20 == 0) {
                CriteriaTriggers.LOCATION.trigger(this);
            }
        } catch (Throwable throwable) {
            CrashReport crashReport = CrashReport.makeCrashReport(throwable, "Ticking player");
            CrashReportCategory crashReportCategory = crashReport.makeCategory("Player being ticked");
            this.fillCrashReport(crashReportCategory);
            throw new ReportedException(crashReport);
        }
    }

    private void updateScorePoints(ScoreCriteria scoreCriteria, int n) {
        this.getWorldScoreboard().forAllObjectives(scoreCriteria, this.getScoreboardName(), arg_0 -> ServerPlayerEntity.lambda$updateScorePoints$1(n, arg_0));
    }

    @Override
    public void onDeath(DamageSource damageSource) {
        Object object;
        boolean bl = this.world.getGameRules().getBoolean(GameRules.SHOW_DEATH_MESSAGES);
        if (bl) {
            object = this.getCombatTracker().getDeathMessage();
            this.connection.sendPacket(new SCombatPacket(this.getCombatTracker(), SCombatPacket.Event.ENTITY_DIED, (ITextComponent)object), arg_0 -> this.lambda$onDeath$3((ITextComponent)object, arg_0));
            Team team = this.getTeam();
            if (team != null && team.getDeathMessageVisibility() != Team.Visible.ALWAYS) {
                if (team.getDeathMessageVisibility() == Team.Visible.HIDE_FOR_OTHER_TEAMS) {
                    this.server.getPlayerList().sendMessageToAllTeamMembers(this, (ITextComponent)object);
                } else if (team.getDeathMessageVisibility() == Team.Visible.HIDE_FOR_OWN_TEAM) {
                    this.server.getPlayerList().sendMessageToTeamOrAllPlayers(this, (ITextComponent)object);
                }
            } else {
                this.server.getPlayerList().func_232641_a_((ITextComponent)object, ChatType.SYSTEM, Util.DUMMY_UUID);
            }
        } else {
            this.connection.sendPacket(new SCombatPacket(this.getCombatTracker(), SCombatPacket.Event.ENTITY_DIED));
        }
        this.spawnShoulderEntities();
        if (this.world.getGameRules().getBoolean(GameRules.FORGIVE_DEAD_PLAYERS)) {
            this.func_241157_eT_();
        }
        if (!this.isSpectator()) {
            this.spawnDrops(damageSource);
        }
        this.getWorldScoreboard().forAllObjectives(ScoreCriteria.DEATH_COUNT, this.getScoreboardName(), Score::incrementScore);
        object = this.getAttackingEntity();
        if (object != null) {
            this.addStat(Stats.ENTITY_KILLED_BY.get(((Entity)object).getType()));
            ((Entity)object).awardKillScore(this, this.scoreValue, damageSource);
            this.createWitherRose((LivingEntity)object);
        }
        this.world.setEntityState(this, (byte)3);
        this.addStat(Stats.DEATHS);
        this.takeStat(Stats.CUSTOM.get(Stats.TIME_SINCE_DEATH));
        this.takeStat(Stats.CUSTOM.get(Stats.TIME_SINCE_REST));
        this.extinguish();
        this.setFlag(0, true);
        this.getCombatTracker().reset();
    }

    private void func_241157_eT_() {
        AxisAlignedBB axisAlignedBB = new AxisAlignedBB(this.getPosition()).grow(32.0, 10.0, 32.0);
        this.world.getLoadedEntitiesWithinAABB(MobEntity.class, axisAlignedBB).stream().filter(ServerPlayerEntity::lambda$func_241157_eT_$4).forEach(this::lambda$func_241157_eT_$5);
    }

    @Override
    public void awardKillScore(Entity entity2, int n, DamageSource damageSource) {
        if (entity2 != this) {
            super.awardKillScore(entity2, n, damageSource);
            this.addScore(n);
            String string = this.getScoreboardName();
            String string2 = entity2.getScoreboardName();
            this.getWorldScoreboard().forAllObjectives(ScoreCriteria.TOTAL_KILL_COUNT, string, Score::incrementScore);
            if (entity2 instanceof PlayerEntity) {
                this.addStat(Stats.PLAYER_KILLS);
                this.getWorldScoreboard().forAllObjectives(ScoreCriteria.PLAYER_KILL_COUNT, string, Score::incrementScore);
            } else {
                this.addStat(Stats.MOB_KILLS);
            }
            this.handleTeamKill(string, string2, ScoreCriteria.TEAM_KILL);
            this.handleTeamKill(string2, string, ScoreCriteria.KILLED_BY_TEAM);
            CriteriaTriggers.PLAYER_KILLED_ENTITY.trigger(this, entity2, damageSource);
        }
    }

    private void handleTeamKill(String string, String string2, ScoreCriteria[] scoreCriteriaArray) {
        int n;
        ScorePlayerTeam scorePlayerTeam = this.getWorldScoreboard().getPlayersTeam(string2);
        if (scorePlayerTeam != null && (n = scorePlayerTeam.getColor().getColorIndex()) >= 0 && n < scoreCriteriaArray.length) {
            this.getWorldScoreboard().forAllObjectives(scoreCriteriaArray[n], string, Score::incrementScore);
        }
    }

    @Override
    public boolean attackEntityFrom(DamageSource damageSource, float f) {
        boolean bl;
        if (this.isInvulnerableTo(damageSource)) {
            return true;
        }
        boolean bl2 = bl = this.server.isDedicatedServer() && this.canPlayersAttack() && "fall".equals(damageSource.damageType);
        if (!bl && this.respawnInvulnerabilityTicks > 0 && damageSource != DamageSource.OUT_OF_WORLD) {
            return true;
        }
        if (damageSource instanceof EntityDamageSource) {
            AbstractArrowEntity abstractArrowEntity;
            Entity entity2;
            Entity entity3 = damageSource.getTrueSource();
            if (entity3 instanceof PlayerEntity && !this.canAttackPlayer((PlayerEntity)entity3)) {
                return true;
            }
            if (entity3 instanceof AbstractArrowEntity && (entity2 = (abstractArrowEntity = (AbstractArrowEntity)entity3).func_234616_v_()) instanceof PlayerEntity && !this.canAttackPlayer((PlayerEntity)entity2)) {
                return true;
            }
        }
        return super.attackEntityFrom(damageSource, f);
    }

    @Override
    public boolean canAttackPlayer(PlayerEntity playerEntity) {
        return !this.canPlayersAttack() ? false : super.canAttackPlayer(playerEntity);
    }

    private boolean canPlayersAttack() {
        return this.server.isPVPEnabled();
    }

    @Override
    @Nullable
    protected PortalInfo func_241829_a(ServerWorld serverWorld) {
        PortalInfo portalInfo = super.func_241829_a(serverWorld);
        if (portalInfo != null && this.world.getDimensionKey() == World.OVERWORLD && serverWorld.getDimensionKey() == World.THE_END) {
            Vector3d vector3d = portalInfo.pos.add(0.0, -1.0, 0.0);
            return new PortalInfo(vector3d, Vector3d.ZERO, 90.0f, 0.0f);
        }
        return portalInfo;
    }

    @Override
    @Nullable
    public Entity changeDimension(ServerWorld serverWorld) {
        this.invulnerableDimensionChange = true;
        ServerWorld serverWorld2 = this.getServerWorld();
        RegistryKey<World> registryKey = serverWorld2.getDimensionKey();
        if (registryKey == World.THE_END && serverWorld.getDimensionKey() == World.OVERWORLD) {
            this.detach();
            this.getServerWorld().removePlayer(this);
            if (!this.queuedEndExit) {
                this.queuedEndExit = true;
                this.connection.sendPacket(new SChangeGameStatePacket(SChangeGameStatePacket.field_241768_e_, this.seenCredits ? 0.0f : 1.0f));
                this.seenCredits = true;
            }
            return this;
        }
        IWorldInfo iWorldInfo = serverWorld.getWorldInfo();
        this.connection.sendPacket(new SRespawnPacket(serverWorld.getDimensionType(), serverWorld.getDimensionKey(), BiomeManager.getHashedSeed(serverWorld.getSeed()), this.interactionManager.getGameType(), this.interactionManager.func_241815_c_(), serverWorld.isDebug(), serverWorld.func_241109_A_(), true));
        this.connection.sendPacket(new SServerDifficultyPacket(iWorldInfo.getDifficulty(), iWorldInfo.isDifficultyLocked()));
        PlayerList playerList = this.server.getPlayerList();
        playerList.updatePermissionLevel(this);
        serverWorld2.removePlayer(this);
        this.removed = false;
        PortalInfo portalInfo = this.func_241829_a(serverWorld);
        if (portalInfo != null) {
            serverWorld2.getProfiler().startSection("moving");
            if (registryKey == World.OVERWORLD && serverWorld.getDimensionKey() == World.THE_NETHER) {
                this.enteredNetherPosition = this.getPositionVec();
            } else if (serverWorld.getDimensionKey() == World.THE_END) {
                this.func_242110_a(serverWorld, new BlockPos(portalInfo.pos));
            }
            serverWorld2.getProfiler().endSection();
            serverWorld2.getProfiler().startSection("placing");
            this.setWorld(serverWorld);
            serverWorld.addDuringPortalTeleport(this);
            this.setRotation(portalInfo.rotationYaw, portalInfo.rotationPitch);
            this.moveForced(portalInfo.pos.x, portalInfo.pos.y, portalInfo.pos.z);
            serverWorld2.getProfiler().endSection();
            this.func_213846_b(serverWorld2);
            this.interactionManager.setWorld(serverWorld);
            this.connection.sendPacket(new SPlayerAbilitiesPacket(this.abilities));
            playerList.sendWorldInfo(this, serverWorld);
            playerList.sendInventory(this);
            for (EffectInstance effectInstance : this.getActivePotionEffects()) {
                this.connection.sendPacket(new SPlayEntityEffectPacket(this.getEntityId(), effectInstance));
            }
            this.connection.sendPacket(new SPlaySoundEventPacket(1032, BlockPos.ZERO, 0, false));
            this.lastExperience = -1;
            this.lastHealth = -1.0f;
            this.lastFoodLevel = -1;
        }
        return this;
    }

    private void func_242110_a(ServerWorld serverWorld, BlockPos blockPos) {
        BlockPos.Mutable mutable = blockPos.toMutable();
        for (int i = -2; i <= 2; ++i) {
            for (int j = -2; j <= 2; ++j) {
                for (int k = -1; k < 3; ++k) {
                    BlockState blockState = k == -1 ? Blocks.OBSIDIAN.getDefaultState() : Blocks.AIR.getDefaultState();
                    serverWorld.setBlockState(mutable.setPos(blockPos).move(j, k, i), blockState);
                }
            }
        }
    }

    @Override
    protected Optional<TeleportationRepositioner.Result> func_241830_a(ServerWorld serverWorld, BlockPos blockPos, boolean bl) {
        Optional<TeleportationRepositioner.Result> optional = super.func_241830_a(serverWorld, blockPos, bl);
        if (optional.isPresent()) {
            return optional;
        }
        Direction.Axis axis = this.world.getBlockState(this.field_242271_ac).func_235903_d_(NetherPortalBlock.AXIS).orElse(Direction.Axis.X);
        Optional<TeleportationRepositioner.Result> optional2 = serverWorld.getDefaultTeleporter().makePortal(blockPos, axis);
        if (!optional2.isPresent()) {
            LOGGER.error("Unable to create a portal, likely target out of worldborder");
        }
        return optional2;
    }

    private void func_213846_b(ServerWorld serverWorld) {
        RegistryKey<World> registryKey = serverWorld.getDimensionKey();
        RegistryKey<World> registryKey2 = this.world.getDimensionKey();
        CriteriaTriggers.CHANGED_DIMENSION.testForAll(this, registryKey, registryKey2);
        if (registryKey == World.THE_NETHER && registryKey2 == World.OVERWORLD && this.enteredNetherPosition != null) {
            CriteriaTriggers.NETHER_TRAVEL.trigger(this, this.enteredNetherPosition);
        }
        if (registryKey2 != World.THE_NETHER) {
            this.enteredNetherPosition = null;
        }
    }

    @Override
    public boolean isSpectatedByPlayer(ServerPlayerEntity serverPlayerEntity) {
        if (serverPlayerEntity.isSpectator()) {
            return this.getSpectatingEntity() == this;
        }
        return this.isSpectator() ? false : super.isSpectatedByPlayer(serverPlayerEntity);
    }

    private void sendTileEntityUpdate(TileEntity tileEntity) {
        SUpdateTileEntityPacket sUpdateTileEntityPacket;
        if (tileEntity != null && (sUpdateTileEntityPacket = tileEntity.getUpdatePacket()) != null) {
            this.connection.sendPacket(sUpdateTileEntityPacket);
        }
    }

    @Override
    public void onItemPickup(Entity entity2, int n) {
        super.onItemPickup(entity2, n);
        this.openContainer.detectAndSendChanges();
    }

    @Override
    public Either<PlayerEntity.SleepResult, Unit> trySleep(BlockPos blockPos) {
        Direction direction = this.world.getBlockState(blockPos).get(HorizontalBlock.HORIZONTAL_FACING);
        if (!this.isSleeping() && this.isAlive()) {
            if (!this.world.getDimensionType().isNatural()) {
                return Either.left(PlayerEntity.SleepResult.NOT_POSSIBLE_HERE);
            }
            if (!this.func_241147_a_(blockPos, direction)) {
                return Either.left(PlayerEntity.SleepResult.TOO_FAR_AWAY);
            }
            if (this.func_241156_b_(blockPos, direction)) {
                return Either.left(PlayerEntity.SleepResult.OBSTRUCTED);
            }
            this.func_242111_a(this.world.getDimensionKey(), blockPos, this.rotationYaw, false, false);
            if (this.world.isDaytime()) {
                return Either.left(PlayerEntity.SleepResult.NOT_POSSIBLE_NOW);
            }
            if (!this.isCreative()) {
                double d = 8.0;
                double d2 = 5.0;
                Vector3d vector3d = Vector3d.copyCenteredHorizontally(blockPos);
                List<MonsterEntity> list = this.world.getEntitiesWithinAABB(MonsterEntity.class, new AxisAlignedBB(vector3d.getX() - 8.0, vector3d.getY() - 5.0, vector3d.getZ() - 8.0, vector3d.getX() + 8.0, vector3d.getY() + 5.0, vector3d.getZ() + 8.0), this::lambda$trySleep$6);
                if (!list.isEmpty()) {
                    return Either.left(PlayerEntity.SleepResult.NOT_SAFE);
                }
            }
            Either<PlayerEntity.SleepResult, Unit> either = super.trySleep(blockPos).ifRight(this::lambda$trySleep$7);
            ((ServerWorld)this.world).updateAllPlayersSleepingFlag();
            return either;
        }
        return Either.left(PlayerEntity.SleepResult.OTHER_PROBLEM);
    }

    @Override
    public void startSleeping(BlockPos blockPos) {
        this.takeStat(Stats.CUSTOM.get(Stats.TIME_SINCE_REST));
        super.startSleeping(blockPos);
    }

    private boolean func_241147_a_(BlockPos blockPos, Direction direction) {
        return this.func_241158_g_(blockPos) || this.func_241158_g_(blockPos.offset(direction.getOpposite()));
    }

    private boolean func_241158_g_(BlockPos blockPos) {
        Vector3d vector3d = Vector3d.copyCenteredHorizontally(blockPos);
        return Math.abs(this.getPosX() - vector3d.getX()) <= 3.0 && Math.abs(this.getPosY() - vector3d.getY()) <= 2.0 && Math.abs(this.getPosZ() - vector3d.getZ()) <= 3.0;
    }

    private boolean func_241156_b_(BlockPos blockPos, Direction direction) {
        BlockPos blockPos2 = blockPos.up();
        return !this.isNormalCube(blockPos2) || !this.isNormalCube(blockPos2.offset(direction.getOpposite()));
    }

    @Override
    public void stopSleepInBed(boolean bl, boolean bl2) {
        if (this.isSleeping()) {
            this.getServerWorld().getChunkProvider().sendToTrackingAndSelf(this, new SAnimateHandPacket(this, 2));
        }
        super.stopSleepInBed(bl, bl2);
        if (this.connection != null) {
            this.connection.setPlayerLocation(this.getPosX(), this.getPosY(), this.getPosZ(), this.rotationYaw, this.rotationPitch);
        }
    }

    @Override
    public boolean startRiding(Entity entity2, boolean bl) {
        Entity entity3 = this.getRidingEntity();
        if (!super.startRiding(entity2, bl)) {
            return true;
        }
        Entity entity4 = this.getRidingEntity();
        if (entity4 != entity3 && this.connection != null) {
            this.connection.setPlayerLocation(this.getPosX(), this.getPosY(), this.getPosZ(), this.rotationYaw, this.rotationPitch);
        }
        return false;
    }

    @Override
    public void stopRiding() {
        Entity entity2 = this.getRidingEntity();
        super.stopRiding();
        Entity entity3 = this.getRidingEntity();
        if (entity3 != entity2 && this.connection != null) {
            this.connection.setPlayerLocation(this.getPosX(), this.getPosY(), this.getPosZ(), this.rotationYaw, this.rotationPitch);
        }
    }

    @Override
    public boolean isInvulnerableTo(DamageSource damageSource) {
        return super.isInvulnerableTo(damageSource) || this.isInvulnerableDimensionChange() || this.abilities.disableDamage && damageSource == DamageSource.WITHER;
    }

    @Override
    protected void updateFallState(double d, boolean bl, BlockState blockState, BlockPos blockPos) {
    }

    @Override
    protected void frostWalk(BlockPos blockPos) {
        if (!this.isSpectator()) {
            super.frostWalk(blockPos);
        }
    }

    public void handleFalling(double d, boolean bl) {
        BlockPos blockPos = this.getOnPosition();
        if (this.world.isBlockLoaded(blockPos)) {
            super.updateFallState(d, bl, this.world.getBlockState(blockPos), blockPos);
        }
    }

    @Override
    public void openSignEditor(SignTileEntity signTileEntity) {
        signTileEntity.setPlayer(this);
        this.connection.sendPacket(new SOpenSignMenuPacket(signTileEntity.getPos()));
    }

    private void getNextWindowId() {
        this.currentWindowId = this.currentWindowId % 100 + 1;
    }

    @Override
    public OptionalInt openContainer(@Nullable INamedContainerProvider iNamedContainerProvider) {
        if (iNamedContainerProvider == null) {
            return OptionalInt.empty();
        }
        if (this.openContainer != this.container) {
            this.closeScreen();
        }
        this.getNextWindowId();
        Container container = iNamedContainerProvider.createMenu(this.currentWindowId, this.inventory, this);
        if (container == null) {
            if (this.isSpectator()) {
                this.sendStatusMessage(new TranslationTextComponent("container.spectatorCantOpen").mergeStyle(TextFormatting.RED), false);
            }
            return OptionalInt.empty();
        }
        this.connection.sendPacket(new SOpenWindowPacket(container.windowId, container.getType(), iNamedContainerProvider.getDisplayName()));
        container.addListener(this);
        this.openContainer = container;
        return OptionalInt.of(this.currentWindowId);
    }

    @Override
    public void openMerchantContainer(int n, MerchantOffers merchantOffers, int n2, int n3, boolean bl, boolean bl2) {
        this.connection.sendPacket(new SMerchantOffersPacket(n, merchantOffers, n2, n3, bl, bl2));
    }

    @Override
    public void openHorseInventory(AbstractHorseEntity abstractHorseEntity, IInventory iInventory) {
        if (this.openContainer != this.container) {
            this.closeScreen();
        }
        this.getNextWindowId();
        this.connection.sendPacket(new SOpenHorseWindowPacket(this.currentWindowId, iInventory.getSizeInventory(), abstractHorseEntity.getEntityId()));
        this.openContainer = new HorseInventoryContainer(this.currentWindowId, this.inventory, iInventory, abstractHorseEntity);
        this.openContainer.addListener(this);
    }

    @Override
    public void openBook(ItemStack itemStack, Hand hand) {
        Item item = itemStack.getItem();
        if (item == Items.WRITTEN_BOOK) {
            if (WrittenBookItem.resolveContents(itemStack, this.getCommandSource(), this)) {
                this.openContainer.detectAndSendChanges();
            }
            this.connection.sendPacket(new SOpenBookWindowPacket(hand));
        }
    }

    @Override
    public void openCommandBlock(CommandBlockTileEntity commandBlockTileEntity) {
        commandBlockTileEntity.setSendToClient(false);
        this.sendTileEntityUpdate(commandBlockTileEntity);
    }

    @Override
    public void sendSlotContents(Container container, int n, ItemStack itemStack) {
        if (!(container.getSlot(n) instanceof CraftingResultSlot)) {
            if (container == this.container) {
                CriteriaTriggers.INVENTORY_CHANGED.test(this, this.inventory, itemStack);
            }
            if (!this.isChangingQuantityOnly) {
                this.connection.sendPacket(new SSetSlotPacket(container.windowId, n, itemStack));
            }
        }
    }

    public void sendContainerToPlayer(Container container) {
        this.sendAllContents(container, container.getInventory());
    }

    @Override
    public void sendAllContents(Container container, NonNullList<ItemStack> nonNullList) {
        this.connection.sendPacket(new SWindowItemsPacket(container.windowId, nonNullList));
        this.connection.sendPacket(new SSetSlotPacket(-1, -1, this.inventory.getItemStack()));
    }

    @Override
    public void sendWindowProperty(Container container, int n, int n2) {
        this.connection.sendPacket(new SWindowPropertyPacket(container.windowId, n, n2));
    }

    @Override
    public void closeScreen() {
        this.connection.sendPacket(new SCloseWindowPacket(this.openContainer.windowId));
        this.closeContainer();
    }

    public void updateHeldItem() {
        if (!this.isChangingQuantityOnly) {
            this.connection.sendPacket(new SSetSlotPacket(-1, -1, this.inventory.getItemStack()));
        }
    }

    public void closeContainer() {
        this.openContainer.onContainerClosed(this);
        this.openContainer = this.container;
    }

    public void setEntityActionState(float f, float f2, boolean bl, boolean bl2) {
        if (this.isPassenger()) {
            if (f >= -1.0f && f <= 1.0f) {
                this.moveStrafing = f;
            }
            if (f2 >= -1.0f && f2 <= 1.0f) {
                this.moveForward = f2;
            }
            this.isJumping = bl;
            this.setSneaking(bl2);
        }
    }

    @Override
    public void addStat(Stat<?> stat, int n) {
        this.stats.increment(this, stat, n);
        this.getWorldScoreboard().forAllObjectives(stat, this.getScoreboardName(), arg_0 -> ServerPlayerEntity.lambda$addStat$8(n, arg_0));
    }

    @Override
    public void takeStat(Stat<?> stat) {
        this.stats.setValue(this, stat, 0);
        this.getWorldScoreboard().forAllObjectives(stat, this.getScoreboardName(), Score::reset);
    }

    @Override
    public int unlockRecipes(Collection<IRecipe<?>> collection) {
        return this.recipeBook.add(collection, this);
    }

    @Override
    public void unlockRecipes(ResourceLocation[] resourceLocationArray) {
        ArrayList<IRecipe<?>> arrayList = Lists.newArrayList();
        for (ResourceLocation resourceLocation : resourceLocationArray) {
            this.server.getRecipeManager().getRecipe(resourceLocation).ifPresent(arrayList::add);
        }
        this.unlockRecipes(arrayList);
    }

    @Override
    public int resetRecipes(Collection<IRecipe<?>> collection) {
        return this.recipeBook.remove(collection, this);
    }

    @Override
    public void giveExperiencePoints(int n) {
        super.giveExperiencePoints(n);
        this.lastExperience = -1;
    }

    public void disconnect() {
        this.disconnected = true;
        this.removePassengers();
        if (this.isSleeping()) {
            this.stopSleepInBed(true, true);
        }
    }

    public boolean hasDisconnected() {
        return this.disconnected;
    }

    public void setPlayerHealthUpdated() {
        this.lastHealth = -1.0E8f;
    }

    @Override
    public void sendStatusMessage(ITextComponent iTextComponent, boolean bl) {
        this.connection.sendPacket(new SChatPacket(iTextComponent, bl ? ChatType.GAME_INFO : ChatType.CHAT, Util.DUMMY_UUID));
    }

    @Override
    protected void onItemUseFinish() {
        if (!this.activeItemStack.isEmpty() && this.isHandActive()) {
            this.connection.sendPacket(new SEntityStatusPacket(this, 9));
            super.onItemUseFinish();
        }
    }

    @Override
    public void lookAt(EntityAnchorArgument.Type type, Vector3d vector3d) {
        super.lookAt(type, vector3d);
        this.connection.sendPacket(new SPlayerLookPacket(type, vector3d.x, vector3d.y, vector3d.z));
    }

    public void lookAt(EntityAnchorArgument.Type type, Entity entity2, EntityAnchorArgument.Type type2) {
        Vector3d vector3d = type2.apply(entity2);
        super.lookAt(type, vector3d);
        this.connection.sendPacket(new SPlayerLookPacket(type, entity2, type2));
    }

    public void copyFrom(ServerPlayerEntity serverPlayerEntity, boolean bl) {
        if (bl) {
            this.inventory.copyInventory(serverPlayerEntity.inventory);
            this.setHealth(serverPlayerEntity.getHealth());
            this.foodStats = serverPlayerEntity.foodStats;
            this.experienceLevel = serverPlayerEntity.experienceLevel;
            this.experienceTotal = serverPlayerEntity.experienceTotal;
            this.experience = serverPlayerEntity.experience;
            this.setScore(serverPlayerEntity.getScore());
            this.field_242271_ac = serverPlayerEntity.field_242271_ac;
        } else if (this.world.getGameRules().getBoolean(GameRules.KEEP_INVENTORY) || serverPlayerEntity.isSpectator()) {
            this.inventory.copyInventory(serverPlayerEntity.inventory);
            this.experienceLevel = serverPlayerEntity.experienceLevel;
            this.experienceTotal = serverPlayerEntity.experienceTotal;
            this.experience = serverPlayerEntity.experience;
            this.setScore(serverPlayerEntity.getScore());
        }
        this.xpSeed = serverPlayerEntity.xpSeed;
        this.enterChestInventory = serverPlayerEntity.enterChestInventory;
        this.getDataManager().set(PLAYER_MODEL_FLAG, (Byte)serverPlayerEntity.getDataManager().get(PLAYER_MODEL_FLAG));
        this.lastExperience = -1;
        this.lastHealth = -1.0f;
        this.lastFoodLevel = -1;
        this.recipeBook.copyFrom(serverPlayerEntity.recipeBook);
        this.entityRemoveQueue.addAll(serverPlayerEntity.entityRemoveQueue);
        this.seenCredits = serverPlayerEntity.seenCredits;
        this.enteredNetherPosition = serverPlayerEntity.enteredNetherPosition;
        this.setLeftShoulderEntity(serverPlayerEntity.getLeftShoulderEntity());
        this.setRightShoulderEntity(serverPlayerEntity.getRightShoulderEntity());
    }

    @Override
    protected void onNewPotionEffect(EffectInstance effectInstance) {
        super.onNewPotionEffect(effectInstance);
        this.connection.sendPacket(new SPlayEntityEffectPacket(this.getEntityId(), effectInstance));
        if (effectInstance.getPotion() == Effects.LEVITATION) {
            this.levitatingSince = this.ticksExisted;
            this.levitationStartPos = this.getPositionVec();
        }
        CriteriaTriggers.EFFECTS_CHANGED.trigger(this);
    }

    @Override
    protected void onChangedPotionEffect(EffectInstance effectInstance, boolean bl) {
        super.onChangedPotionEffect(effectInstance, bl);
        this.connection.sendPacket(new SPlayEntityEffectPacket(this.getEntityId(), effectInstance));
        CriteriaTriggers.EFFECTS_CHANGED.trigger(this);
    }

    @Override
    protected void onFinishedPotionEffect(EffectInstance effectInstance) {
        super.onFinishedPotionEffect(effectInstance);
        this.connection.sendPacket(new SRemoveEntityEffectPacket(this.getEntityId(), effectInstance.getPotion()));
        if (effectInstance.getPotion() == Effects.LEVITATION) {
            this.levitationStartPos = null;
        }
        CriteriaTriggers.EFFECTS_CHANGED.trigger(this);
    }

    @Override
    public void setPositionAndUpdate(double d, double d2, double d3) {
        this.connection.setPlayerLocation(d, d2, d3, this.rotationYaw, this.rotationPitch);
    }

    @Override
    public void moveForced(double d, double d2, double d3) {
        this.setPositionAndUpdate(d, d2, d3);
        this.connection.captureCurrentPosition();
    }

    @Override
    public void onCriticalHit(Entity entity2) {
        this.getServerWorld().getChunkProvider().sendToTrackingAndSelf(this, new SAnimateHandPacket(entity2, 4));
    }

    @Override
    public void onEnchantmentCritical(Entity entity2) {
        this.getServerWorld().getChunkProvider().sendToTrackingAndSelf(this, new SAnimateHandPacket(entity2, 5));
    }

    @Override
    public void sendPlayerAbilities() {
        if (this.connection != null) {
            this.connection.sendPacket(new SPlayerAbilitiesPacket(this.abilities));
            this.updatePotionMetadata();
        }
    }

    public ServerWorld getServerWorld() {
        return (ServerWorld)this.world;
    }

    @Override
    public void setGameType(GameType gameType) {
        this.interactionManager.setGameType(gameType);
        this.connection.sendPacket(new SChangeGameStatePacket(SChangeGameStatePacket.field_241767_d_, gameType.getID()));
        if (gameType == GameType.SPECTATOR) {
            this.spawnShoulderEntities();
            this.stopRiding();
        } else {
            this.setSpectatingEntity(this);
        }
        this.sendPlayerAbilities();
        this.markPotionsDirty();
    }

    @Override
    public boolean isSpectator() {
        return this.interactionManager.getGameType() == GameType.SPECTATOR;
    }

    @Override
    public boolean isCreative() {
        return this.interactionManager.getGameType() == GameType.CREATIVE;
    }

    @Override
    public void sendMessage(ITextComponent iTextComponent, UUID uUID) {
        this.func_241151_a_(iTextComponent, ChatType.SYSTEM, uUID);
    }

    public void func_241151_a_(ITextComponent iTextComponent, ChatType chatType, UUID uUID) {
        this.connection.sendPacket(new SChatPacket(iTextComponent, chatType, uUID), arg_0 -> this.lambda$func_241151_a_$9(chatType, iTextComponent, uUID, arg_0));
    }

    public String getPlayerIP() {
        String string = this.connection.netManager.getRemoteAddress().toString();
        string = string.substring(string.indexOf("/") + 1);
        return string.substring(0, string.indexOf(":"));
    }

    public void handleClientSettings(CClientSettingsPacket cClientSettingsPacket) {
        this.chatVisibility = cClientSettingsPacket.getChatVisibility();
        this.chatColours = cClientSettingsPacket.isColorsEnabled();
        this.getDataManager().set(PLAYER_MODEL_FLAG, (byte)cClientSettingsPacket.getModelPartFlags());
        this.getDataManager().set(MAIN_HAND, (byte)(cClientSettingsPacket.getMainHand() != HandSide.LEFT ? 1 : 0));
    }

    public ChatVisibility getChatVisibility() {
        return this.chatVisibility;
    }

    public void loadResourcePack(String string, String string2) {
        this.connection.sendPacket(new SSendResourcePackPacket(string, string2));
    }

    @Override
    protected int getPermissionLevel() {
        return this.server.getPermissionLevel(this.getGameProfile());
    }

    public void markPlayerActive() {
        this.playerLastActiveTime = Util.milliTime();
    }

    public ServerStatisticsManager getStats() {
        return this.stats;
    }

    public ServerRecipeBook getRecipeBook() {
        return this.recipeBook;
    }

    public void removeEntity(Entity entity2) {
        if (entity2 instanceof PlayerEntity) {
            this.connection.sendPacket(new SDestroyEntitiesPacket(entity2.getEntityId()));
        } else {
            this.entityRemoveQueue.add(entity2.getEntityId());
        }
    }

    public void addEntity(Entity entity2) {
        this.entityRemoveQueue.remove((Object)entity2.getEntityId());
    }

    @Override
    protected void updatePotionMetadata() {
        if (this.isSpectator()) {
            this.resetPotionEffectMetadata();
            this.setInvisible(false);
        } else {
            super.updatePotionMetadata();
        }
    }

    public Entity getSpectatingEntity() {
        return this.spectatingEntity == null ? this : this.spectatingEntity;
    }

    public void setSpectatingEntity(Entity entity2) {
        Entity entity3 = this.getSpectatingEntity();
        Entity entity4 = this.spectatingEntity = entity2 == null ? this : entity2;
        if (entity3 != this.spectatingEntity) {
            this.connection.sendPacket(new SCameraPacket(this.spectatingEntity));
            this.setPositionAndUpdate(this.spectatingEntity.getPosX(), this.spectatingEntity.getPosY(), this.spectatingEntity.getPosZ());
        }
    }

    @Override
    protected void decrementTimeUntilPortal() {
        if (!this.invulnerableDimensionChange) {
            super.decrementTimeUntilPortal();
        }
    }

    @Override
    public void attackTargetEntityWithCurrentItem(Entity entity2) {
        if (this.interactionManager.getGameType() == GameType.SPECTATOR) {
            this.setSpectatingEntity(entity2);
        } else {
            super.attackTargetEntityWithCurrentItem(entity2);
        }
    }

    public long getLastActiveTime() {
        return this.playerLastActiveTime;
    }

    @Nullable
    public ITextComponent getTabListDisplayName() {
        return null;
    }

    @Override
    public void swingArm(Hand hand) {
        super.swingArm(hand);
        this.resetCooldown();
    }

    public boolean isInvulnerableDimensionChange() {
        return this.invulnerableDimensionChange;
    }

    public void clearInvulnerableDimensionChange() {
        this.invulnerableDimensionChange = false;
    }

    public PlayerAdvancements getAdvancements() {
        return this.advancements;
    }

    public void teleport(ServerWorld serverWorld, double d, double d2, double d3, float f, float f2) {
        this.setSpectatingEntity(this);
        this.stopRiding();
        if (serverWorld == this.world) {
            this.connection.setPlayerLocation(d, d2, d3, f, f2);
        } else {
            ServerWorld serverWorld2 = this.getServerWorld();
            IWorldInfo iWorldInfo = serverWorld.getWorldInfo();
            this.connection.sendPacket(new SRespawnPacket(serverWorld.getDimensionType(), serverWorld.getDimensionKey(), BiomeManager.getHashedSeed(serverWorld.getSeed()), this.interactionManager.getGameType(), this.interactionManager.func_241815_c_(), serverWorld.isDebug(), serverWorld.func_241109_A_(), true));
            this.connection.sendPacket(new SServerDifficultyPacket(iWorldInfo.getDifficulty(), iWorldInfo.isDifficultyLocked()));
            this.server.getPlayerList().updatePermissionLevel(this);
            serverWorld2.removePlayer(this);
            this.removed = false;
            this.setLocationAndAngles(d, d2, d3, f, f2);
            this.setWorld(serverWorld);
            serverWorld.addDuringCommandTeleport(this);
            this.func_213846_b(serverWorld2);
            this.connection.setPlayerLocation(d, d2, d3, f, f2);
            this.interactionManager.setWorld(serverWorld);
            this.server.getPlayerList().sendWorldInfo(this, serverWorld);
            this.server.getPlayerList().sendInventory(this);
        }
    }

    @Nullable
    public BlockPos func_241140_K_() {
        return this.field_241138_cr_;
    }

    public float func_242109_L() {
        return this.field_242108_cn;
    }

    public RegistryKey<World> func_241141_L_() {
        return this.field_241137_cq_;
    }

    public boolean func_241142_M_() {
        return this.field_241139_cs_;
    }

    public void func_242111_a(RegistryKey<World> registryKey, @Nullable BlockPos blockPos, float f, boolean bl, boolean bl2) {
        if (blockPos != null) {
            boolean bl3;
            boolean bl4 = bl3 = blockPos.equals(this.field_241138_cr_) && registryKey.equals(this.field_241137_cq_);
            if (bl2 && !bl3) {
                this.sendMessage(new TranslationTextComponent("block.minecraft.set_spawn"), Util.DUMMY_UUID);
            }
            this.field_241138_cr_ = blockPos;
            this.field_241137_cq_ = registryKey;
            this.field_242108_cn = f;
            this.field_241139_cs_ = bl;
        } else {
            this.field_241138_cr_ = null;
            this.field_241137_cq_ = World.OVERWORLD;
            this.field_242108_cn = 0.0f;
            this.field_241139_cs_ = false;
        }
    }

    public void sendChunkLoad(ChunkPos chunkPos, IPacket<?> iPacket, IPacket<?> iPacket2) {
        this.connection.sendPacket(iPacket2);
        this.connection.sendPacket(iPacket);
    }

    public void sendChunkUnload(ChunkPos chunkPos) {
        if (this.isAlive()) {
            this.connection.sendPacket(new SUnloadChunkPacket(chunkPos.x, chunkPos.z));
        }
    }

    public SectionPos getManagedSectionPos() {
        return this.managedSectionPos;
    }

    public void setManagedSectionPos(SectionPos sectionPos) {
        this.managedSectionPos = sectionPos;
    }

    @Override
    public void playSound(SoundEvent soundEvent, SoundCategory soundCategory, float f, float f2) {
        this.connection.sendPacket(new SPlaySoundEffectPacket(soundEvent, soundCategory, this.getPosX(), this.getPosY(), this.getPosZ(), f, f2));
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return new SSpawnPlayerPacket(this);
    }

    @Override
    public ItemEntity dropItem(ItemStack itemStack, boolean bl, boolean bl2) {
        ItemEntity itemEntity = super.dropItem(itemStack, bl, bl2);
        if (itemEntity == null) {
            return null;
        }
        this.world.addEntity(itemEntity);
        ItemStack itemStack2 = itemEntity.getItem();
        if (bl2) {
            if (!itemStack2.isEmpty()) {
                this.addStat(Stats.ITEM_DROPPED.get(itemStack2.getItem()), itemStack.getCount());
            }
            this.addStat(Stats.DROP);
        }
        return itemEntity;
    }

    @Nullable
    public IChatFilter func_244529_Q() {
        return this.field_244528_co;
    }

    private void lambda$func_241151_a_$9(ChatType chatType, ITextComponent iTextComponent, UUID uUID, Future future) throws Exception {
        if (!(future.isSuccess() || chatType != ChatType.GAME_INFO && chatType != ChatType.SYSTEM)) {
            int n = 256;
            String string = iTextComponent.getStringTruncated(256);
            IFormattableTextComponent iFormattableTextComponent = new StringTextComponent(string).mergeStyle(TextFormatting.YELLOW);
            this.connection.sendPacket(new SChatPacket(new TranslationTextComponent("multiplayer.message_not_delivered", iFormattableTextComponent).mergeStyle(TextFormatting.RED), ChatType.SYSTEM, uUID));
        }
    }

    private static void lambda$addStat$8(int n, Score score) {
        score.increaseScore(n);
    }

    private void lambda$trySleep$7(Unit unit) {
        this.addStat(Stats.SLEEP_IN_BED);
        CriteriaTriggers.SLEPT_IN_BED.trigger(this);
    }

    private boolean lambda$trySleep$6(MonsterEntity monsterEntity) {
        return monsterEntity.func_230292_f_(this);
    }

    private void lambda$func_241157_eT_$5(MobEntity mobEntity) {
        ((IAngerable)((Object)mobEntity)).func_233681_b_(this);
    }

    private static boolean lambda$func_241157_eT_$4(MobEntity mobEntity) {
        return mobEntity instanceof IAngerable;
    }

    private void lambda$onDeath$3(ITextComponent iTextComponent, Future future) throws Exception {
        if (!future.isSuccess()) {
            int n = 256;
            String string = iTextComponent.getStringTruncated(256);
            TranslationTextComponent translationTextComponent = new TranslationTextComponent("death.attack.message_too_long", new StringTextComponent(string).mergeStyle(TextFormatting.YELLOW));
            IFormattableTextComponent iFormattableTextComponent = new TranslationTextComponent("death.attack.even_more_magic", this.getDisplayName()).modifyStyle(arg_0 -> ServerPlayerEntity.lambda$onDeath$2(translationTextComponent, arg_0));
            this.connection.sendPacket(new SCombatPacket(this.getCombatTracker(), SCombatPacket.Event.ENTITY_DIED, iFormattableTextComponent));
        }
    }

    private static Style lambda$onDeath$2(ITextComponent iTextComponent, Style style) {
        return style.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, iTextComponent));
    }

    private static void lambda$updateScorePoints$1(int n, Score score) {
        score.setScorePoints(n);
    }

    private static void lambda$writeAdditional$0(CompoundNBT compoundNBT, INBT iNBT) {
        compoundNBT.put("SpawnDimension", iNBT);
    }
}

