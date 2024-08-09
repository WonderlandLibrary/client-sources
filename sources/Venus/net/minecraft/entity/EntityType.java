/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity;

import com.google.common.collect.ImmutableSet;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.entity.item.EnderCrystalEntity;
import net.minecraft.entity.item.EnderPearlEntity;
import net.minecraft.entity.item.ExperienceBottleEntity;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.item.ItemFrameEntity;
import net.minecraft.entity.item.LeashKnotEntity;
import net.minecraft.entity.item.PaintingEntity;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.entity.item.minecart.ChestMinecartEntity;
import net.minecraft.entity.item.minecart.CommandBlockMinecartEntity;
import net.minecraft.entity.item.minecart.FurnaceMinecartEntity;
import net.minecraft.entity.item.minecart.HopperMinecartEntity;
import net.minecraft.entity.item.minecart.MinecartEntity;
import net.minecraft.entity.item.minecart.SpawnerMinecartEntity;
import net.minecraft.entity.item.minecart.TNTMinecartEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.merchant.villager.WanderingTraderEntity;
import net.minecraft.entity.monster.BlazeEntity;
import net.minecraft.entity.monster.CaveSpiderEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.DrownedEntity;
import net.minecraft.entity.monster.ElderGuardianEntity;
import net.minecraft.entity.monster.EndermanEntity;
import net.minecraft.entity.monster.EndermiteEntity;
import net.minecraft.entity.monster.EvokerEntity;
import net.minecraft.entity.monster.GhastEntity;
import net.minecraft.entity.monster.GiantEntity;
import net.minecraft.entity.monster.GuardianEntity;
import net.minecraft.entity.monster.HoglinEntity;
import net.minecraft.entity.monster.HuskEntity;
import net.minecraft.entity.monster.IllusionerEntity;
import net.minecraft.entity.monster.MagmaCubeEntity;
import net.minecraft.entity.monster.PhantomEntity;
import net.minecraft.entity.monster.PillagerEntity;
import net.minecraft.entity.monster.RavagerEntity;
import net.minecraft.entity.monster.ShulkerEntity;
import net.minecraft.entity.monster.SilverfishEntity;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.entity.monster.SpiderEntity;
import net.minecraft.entity.monster.StrayEntity;
import net.minecraft.entity.monster.VexEntity;
import net.minecraft.entity.monster.VindicatorEntity;
import net.minecraft.entity.monster.WitchEntity;
import net.minecraft.entity.monster.WitherSkeletonEntity;
import net.minecraft.entity.monster.ZoglinEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.monster.ZombieVillagerEntity;
import net.minecraft.entity.monster.ZombifiedPiglinEntity;
import net.minecraft.entity.monster.piglin.PiglinBruteEntity;
import net.minecraft.entity.monster.piglin.PiglinEntity;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.DolphinEntity;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.MooshroomEntity;
import net.minecraft.entity.passive.OcelotEntity;
import net.minecraft.entity.passive.PandaEntity;
import net.minecraft.entity.passive.ParrotEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.passive.PolarBearEntity;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.entity.passive.SquidEntity;
import net.minecraft.entity.passive.StriderEntity;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.passive.fish.CodEntity;
import net.minecraft.entity.passive.fish.PufferfishEntity;
import net.minecraft.entity.passive.fish.SalmonEntity;
import net.minecraft.entity.passive.fish.TropicalFishEntity;
import net.minecraft.entity.passive.horse.DonkeyEntity;
import net.minecraft.entity.passive.horse.HorseEntity;
import net.minecraft.entity.passive.horse.LlamaEntity;
import net.minecraft.entity.passive.horse.MuleEntity;
import net.minecraft.entity.passive.horse.SkeletonHorseEntity;
import net.minecraft.entity.passive.horse.TraderLlamaEntity;
import net.minecraft.entity.passive.horse.ZombieHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.DragonFireballEntity;
import net.minecraft.entity.projectile.EggEntity;
import net.minecraft.entity.projectile.EvokerFangsEntity;
import net.minecraft.entity.projectile.EyeOfEnderEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.entity.projectile.LlamaSpitEntity;
import net.minecraft.entity.projectile.PotionEntity;
import net.minecraft.entity.projectile.ShulkerBulletEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.entity.projectile.SnowballEntity;
import net.minecraft.entity.projectile.SpectralArrowEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.entity.projectile.WitherSkullEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.datafix.TypeReferences;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EntityType<T extends Entity> {
    private static final Logger LOGGER = LogManager.getLogger();
    public static final EntityType<AreaEffectCloudEntity> AREA_EFFECT_CLOUD = EntityType.register("area_effect_cloud", Builder.create(AreaEffectCloudEntity::new, EntityClassification.MISC).immuneToFire().size(6.0f, 0.5f).trackingRange(10).func_233608_b_(Integer.MAX_VALUE));
    public static final EntityType<ArmorStandEntity> ARMOR_STAND = EntityType.register("armor_stand", Builder.create(ArmorStandEntity::new, EntityClassification.MISC).size(0.5f, 1.975f).trackingRange(10));
    public static final EntityType<ArrowEntity> ARROW = EntityType.register("arrow", Builder.create(ArrowEntity::new, EntityClassification.MISC).size(0.5f, 0.5f).trackingRange(4).func_233608_b_(20));
    public static final EntityType<BatEntity> BAT = EntityType.register("bat", Builder.create(BatEntity::new, EntityClassification.AMBIENT).size(0.5f, 0.9f).trackingRange(5));
    public static final EntityType<BeeEntity> BEE = EntityType.register("bee", Builder.create(BeeEntity::new, EntityClassification.CREATURE).size(0.7f, 0.6f).trackingRange(8));
    public static final EntityType<BlazeEntity> BLAZE = EntityType.register("blaze", Builder.create(BlazeEntity::new, EntityClassification.MONSTER).immuneToFire().size(0.6f, 1.8f).trackingRange(8));
    public static final EntityType<BoatEntity> BOAT = EntityType.register("boat", Builder.create(BoatEntity::new, EntityClassification.MISC).size(1.375f, 0.5625f).trackingRange(10));
    public static final EntityType<CatEntity> CAT = EntityType.register("cat", Builder.create(CatEntity::new, EntityClassification.CREATURE).size(0.6f, 0.7f).trackingRange(8));
    public static final EntityType<CaveSpiderEntity> CAVE_SPIDER = EntityType.register("cave_spider", Builder.create(CaveSpiderEntity::new, EntityClassification.MONSTER).size(0.7f, 0.5f).trackingRange(8));
    public static final EntityType<ChickenEntity> CHICKEN = EntityType.register("chicken", Builder.create(ChickenEntity::new, EntityClassification.CREATURE).size(0.4f, 0.7f).trackingRange(10));
    public static final EntityType<CodEntity> COD = EntityType.register("cod", Builder.create(CodEntity::new, EntityClassification.WATER_AMBIENT).size(0.5f, 0.3f).trackingRange(4));
    public static final EntityType<CowEntity> COW = EntityType.register("cow", Builder.create(CowEntity::new, EntityClassification.CREATURE).size(0.9f, 1.4f).trackingRange(10));
    public static final EntityType<CreeperEntity> CREEPER = EntityType.register("creeper", Builder.create(CreeperEntity::new, EntityClassification.MONSTER).size(0.6f, 1.7f).trackingRange(8));
    public static final EntityType<DolphinEntity> DOLPHIN = EntityType.register("dolphin", Builder.create(DolphinEntity::new, EntityClassification.WATER_CREATURE).size(0.9f, 0.6f));
    public static final EntityType<DonkeyEntity> DONKEY = EntityType.register("donkey", Builder.create(DonkeyEntity::new, EntityClassification.CREATURE).size(1.3964844f, 1.5f).trackingRange(10));
    public static final EntityType<DragonFireballEntity> DRAGON_FIREBALL = EntityType.register("dragon_fireball", Builder.create(DragonFireballEntity::new, EntityClassification.MISC).size(1.0f, 1.0f).trackingRange(4).func_233608_b_(10));
    public static final EntityType<DrownedEntity> DROWNED = EntityType.register("drowned", Builder.create(DrownedEntity::new, EntityClassification.MONSTER).size(0.6f, 1.95f).trackingRange(8));
    public static final EntityType<ElderGuardianEntity> ELDER_GUARDIAN = EntityType.register("elder_guardian", Builder.create(ElderGuardianEntity::new, EntityClassification.MONSTER).size(1.9975f, 1.9975f).trackingRange(10));
    public static final EntityType<EnderCrystalEntity> END_CRYSTAL = EntityType.register("end_crystal", Builder.create(EnderCrystalEntity::new, EntityClassification.MISC).size(2.0f, 2.0f).trackingRange(16).func_233608_b_(Integer.MAX_VALUE));
    public static final EntityType<EnderDragonEntity> ENDER_DRAGON = EntityType.register("ender_dragon", Builder.create(EnderDragonEntity::new, EntityClassification.MONSTER).immuneToFire().size(16.0f, 8.0f).trackingRange(10));
    public static final EntityType<EndermanEntity> ENDERMAN = EntityType.register("enderman", Builder.create(EndermanEntity::new, EntityClassification.MONSTER).size(0.6f, 2.9f).trackingRange(8));
    public static final EntityType<EndermiteEntity> ENDERMITE = EntityType.register("endermite", Builder.create(EndermiteEntity::new, EntityClassification.MONSTER).size(0.4f, 0.3f).trackingRange(8));
    public static final EntityType<EvokerEntity> EVOKER = EntityType.register("evoker", Builder.create(EvokerEntity::new, EntityClassification.MONSTER).size(0.6f, 1.95f).trackingRange(8));
    public static final EntityType<EvokerFangsEntity> EVOKER_FANGS = EntityType.register("evoker_fangs", Builder.create(EvokerFangsEntity::new, EntityClassification.MISC).size(0.5f, 0.8f).trackingRange(6).func_233608_b_(2));
    public static final EntityType<ExperienceOrbEntity> EXPERIENCE_ORB = EntityType.register("experience_orb", Builder.create(ExperienceOrbEntity::new, EntityClassification.MISC).size(0.5f, 0.5f).trackingRange(6).func_233608_b_(20));
    public static final EntityType<EyeOfEnderEntity> EYE_OF_ENDER = EntityType.register("eye_of_ender", Builder.create(EyeOfEnderEntity::new, EntityClassification.MISC).size(0.25f, 0.25f).trackingRange(4).func_233608_b_(4));
    public static final EntityType<FallingBlockEntity> FALLING_BLOCK = EntityType.register("falling_block", Builder.create(FallingBlockEntity::new, EntityClassification.MISC).size(0.98f, 0.98f).trackingRange(10).func_233608_b_(20));
    public static final EntityType<FireworkRocketEntity> FIREWORK_ROCKET = EntityType.register("firework_rocket", Builder.create(FireworkRocketEntity::new, EntityClassification.MISC).size(0.25f, 0.25f).trackingRange(4).func_233608_b_(10));
    public static final EntityType<FoxEntity> FOX = EntityType.register("fox", Builder.create(FoxEntity::new, EntityClassification.CREATURE).size(0.6f, 0.7f).trackingRange(8).func_233607_a_(Blocks.SWEET_BERRY_BUSH));
    public static final EntityType<GhastEntity> GHAST = EntityType.register("ghast", Builder.create(GhastEntity::new, EntityClassification.MONSTER).immuneToFire().size(4.0f, 4.0f).trackingRange(10));
    public static final EntityType<GiantEntity> GIANT = EntityType.register("giant", Builder.create(GiantEntity::new, EntityClassification.MONSTER).size(3.6f, 12.0f).trackingRange(10));
    public static final EntityType<GuardianEntity> GUARDIAN = EntityType.register("guardian", Builder.create(GuardianEntity::new, EntityClassification.MONSTER).size(0.85f, 0.85f).trackingRange(8));
    public static final EntityType<HoglinEntity> HOGLIN = EntityType.register("hoglin", Builder.create(HoglinEntity::new, EntityClassification.MONSTER).size(1.3964844f, 1.4f).trackingRange(8));
    public static final EntityType<HorseEntity> HORSE = EntityType.register("horse", Builder.create(HorseEntity::new, EntityClassification.CREATURE).size(1.3964844f, 1.6f).trackingRange(10));
    public static final EntityType<HuskEntity> HUSK = EntityType.register("husk", Builder.create(HuskEntity::new, EntityClassification.MONSTER).size(0.6f, 1.95f).trackingRange(8));
    public static final EntityType<IllusionerEntity> ILLUSIONER = EntityType.register("illusioner", Builder.create(IllusionerEntity::new, EntityClassification.MONSTER).size(0.6f, 1.95f).trackingRange(8));
    public static final EntityType<IronGolemEntity> IRON_GOLEM = EntityType.register("iron_golem", Builder.create(IronGolemEntity::new, EntityClassification.MISC).size(1.4f, 2.7f).trackingRange(10));
    public static final EntityType<ItemEntity> ITEM = EntityType.register("item", Builder.create(ItemEntity::new, EntityClassification.MISC).size(0.25f, 0.25f).trackingRange(6).func_233608_b_(20));
    public static final EntityType<ItemFrameEntity> ITEM_FRAME = EntityType.register("item_frame", Builder.create(ItemFrameEntity::new, EntityClassification.MISC).size(0.5f, 0.5f).trackingRange(10).func_233608_b_(Integer.MAX_VALUE));
    public static final EntityType<FireballEntity> FIREBALL = EntityType.register("fireball", Builder.create(FireballEntity::new, EntityClassification.MISC).size(1.0f, 1.0f).trackingRange(4).func_233608_b_(10));
    public static final EntityType<LeashKnotEntity> LEASH_KNOT = EntityType.register("leash_knot", Builder.create(LeashKnotEntity::new, EntityClassification.MISC).disableSerialization().size(0.5f, 0.5f).trackingRange(10).func_233608_b_(Integer.MAX_VALUE));
    public static final EntityType<LightningBoltEntity> LIGHTNING_BOLT = EntityType.register("lightning_bolt", Builder.create(LightningBoltEntity::new, EntityClassification.MISC).disableSerialization().size(0.0f, 0.0f).trackingRange(16).func_233608_b_(Integer.MAX_VALUE));
    public static final EntityType<LlamaEntity> LLAMA = EntityType.register("llama", Builder.create(LlamaEntity::new, EntityClassification.CREATURE).size(0.9f, 1.87f).trackingRange(10));
    public static final EntityType<LlamaSpitEntity> LLAMA_SPIT = EntityType.register("llama_spit", Builder.create(LlamaSpitEntity::new, EntityClassification.MISC).size(0.25f, 0.25f).trackingRange(4).func_233608_b_(10));
    public static final EntityType<MagmaCubeEntity> MAGMA_CUBE = EntityType.register("magma_cube", Builder.create(MagmaCubeEntity::new, EntityClassification.MONSTER).immuneToFire().size(2.04f, 2.04f).trackingRange(8));
    public static final EntityType<MinecartEntity> MINECART = EntityType.register("minecart", Builder.create(MinecartEntity::new, EntityClassification.MISC).size(0.98f, 0.7f).trackingRange(8));
    public static final EntityType<ChestMinecartEntity> CHEST_MINECART = EntityType.register("chest_minecart", Builder.create(ChestMinecartEntity::new, EntityClassification.MISC).size(0.98f, 0.7f).trackingRange(8));
    public static final EntityType<CommandBlockMinecartEntity> COMMAND_BLOCK_MINECART = EntityType.register("command_block_minecart", Builder.create(CommandBlockMinecartEntity::new, EntityClassification.MISC).size(0.98f, 0.7f).trackingRange(8));
    public static final EntityType<FurnaceMinecartEntity> FURNACE_MINECART = EntityType.register("furnace_minecart", Builder.create(FurnaceMinecartEntity::new, EntityClassification.MISC).size(0.98f, 0.7f).trackingRange(8));
    public static final EntityType<HopperMinecartEntity> HOPPER_MINECART = EntityType.register("hopper_minecart", Builder.create(HopperMinecartEntity::new, EntityClassification.MISC).size(0.98f, 0.7f).trackingRange(8));
    public static final EntityType<SpawnerMinecartEntity> SPAWNER_MINECART = EntityType.register("spawner_minecart", Builder.create(SpawnerMinecartEntity::new, EntityClassification.MISC).size(0.98f, 0.7f).trackingRange(8));
    public static final EntityType<TNTMinecartEntity> TNT_MINECART = EntityType.register("tnt_minecart", Builder.create(TNTMinecartEntity::new, EntityClassification.MISC).size(0.98f, 0.7f).trackingRange(8));
    public static final EntityType<MuleEntity> MULE = EntityType.register("mule", Builder.create(MuleEntity::new, EntityClassification.CREATURE).size(1.3964844f, 1.6f).trackingRange(8));
    public static final EntityType<MooshroomEntity> MOOSHROOM = EntityType.register("mooshroom", Builder.create(MooshroomEntity::new, EntityClassification.CREATURE).size(0.9f, 1.4f).trackingRange(10));
    public static final EntityType<OcelotEntity> OCELOT = EntityType.register("ocelot", Builder.create(OcelotEntity::new, EntityClassification.CREATURE).size(0.6f, 0.7f).trackingRange(10));
    public static final EntityType<PaintingEntity> PAINTING = EntityType.register("painting", Builder.create(PaintingEntity::new, EntityClassification.MISC).size(0.5f, 0.5f).trackingRange(10).func_233608_b_(Integer.MAX_VALUE));
    public static final EntityType<PandaEntity> PANDA = EntityType.register("panda", Builder.create(PandaEntity::new, EntityClassification.CREATURE).size(1.3f, 1.25f).trackingRange(10));
    public static final EntityType<ParrotEntity> PARROT = EntityType.register("parrot", Builder.create(ParrotEntity::new, EntityClassification.CREATURE).size(0.5f, 0.9f).trackingRange(8));
    public static final EntityType<PhantomEntity> PHANTOM = EntityType.register("phantom", Builder.create(PhantomEntity::new, EntityClassification.MONSTER).size(0.9f, 0.5f).trackingRange(8));
    public static final EntityType<PigEntity> PIG = EntityType.register("pig", Builder.create(PigEntity::new, EntityClassification.CREATURE).size(0.9f, 0.9f).trackingRange(10));
    public static final EntityType<PiglinEntity> PIGLIN = EntityType.register("piglin", Builder.create(PiglinEntity::new, EntityClassification.MONSTER).size(0.6f, 1.95f).trackingRange(8));
    public static final EntityType<PiglinBruteEntity> field_242287_aj = EntityType.register("piglin_brute", Builder.create(PiglinBruteEntity::new, EntityClassification.MONSTER).size(0.6f, 1.95f).trackingRange(8));
    public static final EntityType<PillagerEntity> PILLAGER = EntityType.register("pillager", Builder.create(PillagerEntity::new, EntityClassification.MONSTER).func_225435_d().size(0.6f, 1.95f).trackingRange(8));
    public static final EntityType<PolarBearEntity> POLAR_BEAR = EntityType.register("polar_bear", Builder.create(PolarBearEntity::new, EntityClassification.CREATURE).size(1.4f, 1.4f).trackingRange(10));
    public static final EntityType<TNTEntity> TNT = EntityType.register("tnt", Builder.create(TNTEntity::new, EntityClassification.MISC).immuneToFire().size(0.98f, 0.98f).trackingRange(10).func_233608_b_(10));
    public static final EntityType<PufferfishEntity> PUFFERFISH = EntityType.register("pufferfish", Builder.create(PufferfishEntity::new, EntityClassification.WATER_AMBIENT).size(0.7f, 0.7f).trackingRange(4));
    public static final EntityType<RabbitEntity> RABBIT = EntityType.register("rabbit", Builder.create(RabbitEntity::new, EntityClassification.CREATURE).size(0.4f, 0.5f).trackingRange(8));
    public static final EntityType<RavagerEntity> RAVAGER = EntityType.register("ravager", Builder.create(RavagerEntity::new, EntityClassification.MONSTER).size(1.95f, 2.2f).trackingRange(10));
    public static final EntityType<SalmonEntity> SALMON = EntityType.register("salmon", Builder.create(SalmonEntity::new, EntityClassification.WATER_AMBIENT).size(0.7f, 0.4f).trackingRange(4));
    public static final EntityType<SheepEntity> SHEEP = EntityType.register("sheep", Builder.create(SheepEntity::new, EntityClassification.CREATURE).size(0.9f, 1.3f).trackingRange(10));
    public static final EntityType<ShulkerEntity> SHULKER = EntityType.register("shulker", Builder.create(ShulkerEntity::new, EntityClassification.MONSTER).immuneToFire().func_225435_d().size(1.0f, 1.0f).trackingRange(10));
    public static final EntityType<ShulkerBulletEntity> SHULKER_BULLET = EntityType.register("shulker_bullet", Builder.create(ShulkerBulletEntity::new, EntityClassification.MISC).size(0.3125f, 0.3125f).trackingRange(8));
    public static final EntityType<SilverfishEntity> SILVERFISH = EntityType.register("silverfish", Builder.create(SilverfishEntity::new, EntityClassification.MONSTER).size(0.4f, 0.3f).trackingRange(8));
    public static final EntityType<SkeletonEntity> SKELETON = EntityType.register("skeleton", Builder.create(SkeletonEntity::new, EntityClassification.MONSTER).size(0.6f, 1.99f).trackingRange(8));
    public static final EntityType<SkeletonHorseEntity> SKELETON_HORSE = EntityType.register("skeleton_horse", Builder.create(SkeletonHorseEntity::new, EntityClassification.CREATURE).size(1.3964844f, 1.6f).trackingRange(10));
    public static final EntityType<SlimeEntity> SLIME = EntityType.register("slime", Builder.create(SlimeEntity::new, EntityClassification.MONSTER).size(2.04f, 2.04f).trackingRange(10));
    public static final EntityType<SmallFireballEntity> SMALL_FIREBALL = EntityType.register("small_fireball", Builder.create(SmallFireballEntity::new, EntityClassification.MISC).size(0.3125f, 0.3125f).trackingRange(4).func_233608_b_(10));
    public static final EntityType<SnowGolemEntity> SNOW_GOLEM = EntityType.register("snow_golem", Builder.create(SnowGolemEntity::new, EntityClassification.MISC).size(0.7f, 1.9f).trackingRange(8));
    public static final EntityType<SnowballEntity> SNOWBALL = EntityType.register("snowball", Builder.create(SnowballEntity::new, EntityClassification.MISC).size(0.25f, 0.25f).trackingRange(4).func_233608_b_(10));
    public static final EntityType<SpectralArrowEntity> SPECTRAL_ARROW = EntityType.register("spectral_arrow", Builder.create(SpectralArrowEntity::new, EntityClassification.MISC).size(0.5f, 0.5f).trackingRange(4).func_233608_b_(20));
    public static final EntityType<SpiderEntity> SPIDER = EntityType.register("spider", Builder.create(SpiderEntity::new, EntityClassification.MONSTER).size(1.4f, 0.9f).trackingRange(8));
    public static final EntityType<SquidEntity> SQUID = EntityType.register("squid", Builder.create(SquidEntity::new, EntityClassification.WATER_CREATURE).size(0.8f, 0.8f).trackingRange(8));
    public static final EntityType<StrayEntity> STRAY = EntityType.register("stray", Builder.create(StrayEntity::new, EntityClassification.MONSTER).size(0.6f, 1.99f).trackingRange(8));
    public static final EntityType<StriderEntity> STRIDER = EntityType.register("strider", Builder.create(StriderEntity::new, EntityClassification.CREATURE).immuneToFire().size(0.9f, 1.7f).trackingRange(10));
    public static final EntityType<EggEntity> EGG = EntityType.register("egg", Builder.create(EggEntity::new, EntityClassification.MISC).size(0.25f, 0.25f).trackingRange(4).func_233608_b_(10));
    public static final EntityType<EnderPearlEntity> ENDER_PEARL = EntityType.register("ender_pearl", Builder.create(EnderPearlEntity::new, EntityClassification.MISC).size(0.25f, 0.25f).trackingRange(4).func_233608_b_(10));
    public static final EntityType<ExperienceBottleEntity> EXPERIENCE_BOTTLE = EntityType.register("experience_bottle", Builder.create(ExperienceBottleEntity::new, EntityClassification.MISC).size(0.25f, 0.25f).trackingRange(4).func_233608_b_(10));
    public static final EntityType<PotionEntity> POTION = EntityType.register("potion", Builder.create(PotionEntity::new, EntityClassification.MISC).size(0.25f, 0.25f).trackingRange(4).func_233608_b_(10));
    public static final EntityType<TridentEntity> TRIDENT = EntityType.register("trident", Builder.create(TridentEntity::new, EntityClassification.MISC).size(0.5f, 0.5f).trackingRange(4).func_233608_b_(20));
    public static final EntityType<TraderLlamaEntity> TRADER_LLAMA = EntityType.register("trader_llama", Builder.create(TraderLlamaEntity::new, EntityClassification.CREATURE).size(0.9f, 1.87f).trackingRange(10));
    public static final EntityType<TropicalFishEntity> TROPICAL_FISH = EntityType.register("tropical_fish", Builder.create(TropicalFishEntity::new, EntityClassification.WATER_AMBIENT).size(0.5f, 0.4f).trackingRange(4));
    public static final EntityType<TurtleEntity> TURTLE = EntityType.register("turtle", Builder.create(TurtleEntity::new, EntityClassification.CREATURE).size(1.2f, 0.4f).trackingRange(10));
    public static final EntityType<VexEntity> VEX = EntityType.register("vex", Builder.create(VexEntity::new, EntityClassification.MONSTER).immuneToFire().size(0.4f, 0.8f).trackingRange(8));
    public static final EntityType<VillagerEntity> VILLAGER = EntityType.register("villager", Builder.create(VillagerEntity::new, EntityClassification.MISC).size(0.6f, 1.95f).trackingRange(10));
    public static final EntityType<VindicatorEntity> VINDICATOR = EntityType.register("vindicator", Builder.create(VindicatorEntity::new, EntityClassification.MONSTER).size(0.6f, 1.95f).trackingRange(8));
    public static final EntityType<WanderingTraderEntity> WANDERING_TRADER = EntityType.register("wandering_trader", Builder.create(WanderingTraderEntity::new, EntityClassification.CREATURE).size(0.6f, 1.95f).trackingRange(10));
    public static final EntityType<WitchEntity> WITCH = EntityType.register("witch", Builder.create(WitchEntity::new, EntityClassification.MONSTER).size(0.6f, 1.95f).trackingRange(8));
    public static final EntityType<WitherEntity> WITHER = EntityType.register("wither", Builder.create(WitherEntity::new, EntityClassification.MONSTER).immuneToFire().func_233607_a_(Blocks.WITHER_ROSE).size(0.9f, 3.5f).trackingRange(10));
    public static final EntityType<WitherSkeletonEntity> WITHER_SKELETON = EntityType.register("wither_skeleton", Builder.create(WitherSkeletonEntity::new, EntityClassification.MONSTER).immuneToFire().func_233607_a_(Blocks.WITHER_ROSE).size(0.7f, 2.4f).trackingRange(8));
    public static final EntityType<WitherSkullEntity> WITHER_SKULL = EntityType.register("wither_skull", Builder.create(WitherSkullEntity::new, EntityClassification.MISC).size(0.3125f, 0.3125f).trackingRange(4).func_233608_b_(10));
    public static final EntityType<WolfEntity> WOLF = EntityType.register("wolf", Builder.create(WolfEntity::new, EntityClassification.CREATURE).size(0.6f, 0.85f).trackingRange(10));
    public static final EntityType<ZoglinEntity> ZOGLIN = EntityType.register("zoglin", Builder.create(ZoglinEntity::new, EntityClassification.MONSTER).immuneToFire().size(1.3964844f, 1.4f).trackingRange(8));
    public static final EntityType<ZombieEntity> ZOMBIE = EntityType.register("zombie", Builder.create(ZombieEntity::new, EntityClassification.MONSTER).size(0.6f, 1.95f).trackingRange(8));
    public static final EntityType<ZombieHorseEntity> ZOMBIE_HORSE = EntityType.register("zombie_horse", Builder.create(ZombieHorseEntity::new, EntityClassification.CREATURE).size(1.3964844f, 1.6f).trackingRange(10));
    public static final EntityType<ZombieVillagerEntity> ZOMBIE_VILLAGER = EntityType.register("zombie_villager", Builder.create(ZombieVillagerEntity::new, EntityClassification.MONSTER).size(0.6f, 1.95f).trackingRange(8));
    public static final EntityType<ZombifiedPiglinEntity> ZOMBIFIED_PIGLIN = EntityType.register("zombified_piglin", Builder.create(ZombifiedPiglinEntity::new, EntityClassification.MONSTER).immuneToFire().size(0.6f, 1.95f).trackingRange(8));
    public static final EntityType<PlayerEntity> PLAYER = EntityType.register("player", Builder.create(EntityClassification.MISC).disableSerialization().disableSummoning().size(0.6f, 1.8f).trackingRange(32).func_233608_b_(2));
    public static final EntityType<FishingBobberEntity> FISHING_BOBBER = EntityType.register("fishing_bobber", Builder.create(EntityClassification.MISC).disableSerialization().disableSummoning().size(0.25f, 0.25f).trackingRange(4).func_233608_b_(5));
    private final IFactory<T> factory;
    private final EntityClassification classification;
    private final ImmutableSet<Block> field_233593_bg_;
    private final boolean serializable;
    private final boolean summonable;
    private final boolean immuneToFire;
    private final boolean field_225438_be;
    private final int defaultTrackingRange;
    private final int defaultUpdateInterval;
    @Nullable
    private String translationKey;
    @Nullable
    private ITextComponent name;
    @Nullable
    private ResourceLocation lootTable;
    private final EntitySize size;

    private static <T extends Entity> EntityType<T> register(String string, Builder<T> builder) {
        return Registry.register(Registry.ENTITY_TYPE, string, builder.build(string));
    }

    public static ResourceLocation getKey(EntityType<?> entityType) {
        return Registry.ENTITY_TYPE.getKey(entityType);
    }

    public static Optional<EntityType<?>> byKey(String string) {
        return Registry.ENTITY_TYPE.getOptional(ResourceLocation.tryCreate(string));
    }

    public EntityType(IFactory<T> iFactory, EntityClassification entityClassification, boolean bl, boolean bl2, boolean bl3, boolean bl4, ImmutableSet<Block> immutableSet, EntitySize entitySize, int n, int n2) {
        this.factory = iFactory;
        this.classification = entityClassification;
        this.field_225438_be = bl4;
        this.serializable = bl;
        this.summonable = bl2;
        this.immuneToFire = bl3;
        this.field_233593_bg_ = immutableSet;
        this.size = entitySize;
        this.defaultTrackingRange = n;
        this.defaultUpdateInterval = n2;
    }

    @Nullable
    public Entity spawn(ServerWorld serverWorld, @Nullable ItemStack itemStack, @Nullable PlayerEntity playerEntity, BlockPos blockPos, SpawnReason spawnReason, boolean bl, boolean bl2) {
        return this.spawn(serverWorld, itemStack == null ? null : itemStack.getTag(), itemStack != null && itemStack.hasDisplayName() ? itemStack.getDisplayName() : null, playerEntity, blockPos, spawnReason, bl, bl2);
    }

    @Nullable
    public T spawn(ServerWorld serverWorld, @Nullable CompoundNBT compoundNBT, @Nullable ITextComponent iTextComponent, @Nullable PlayerEntity playerEntity, BlockPos blockPos, SpawnReason spawnReason, boolean bl, boolean bl2) {
        T t = this.create(serverWorld, compoundNBT, iTextComponent, playerEntity, blockPos, spawnReason, bl, bl2);
        if (t != null) {
            serverWorld.func_242417_l((Entity)t);
        }
        return t;
    }

    @Nullable
    public T create(ServerWorld serverWorld, @Nullable CompoundNBT compoundNBT, @Nullable ITextComponent iTextComponent, @Nullable PlayerEntity playerEntity, BlockPos blockPos, SpawnReason spawnReason, boolean bl, boolean bl2) {
        double d;
        T t = this.create(serverWorld);
        if (t == null) {
            return (T)((Entity)null);
        }
        if (bl) {
            ((Entity)t).setPosition((double)blockPos.getX() + 0.5, blockPos.getY() + 1, (double)blockPos.getZ() + 0.5);
            d = EntityType.func_208051_a(serverWorld, blockPos, bl2, ((Entity)t).getBoundingBox());
        } else {
            d = 0.0;
        }
        ((Entity)t).setLocationAndAngles((double)blockPos.getX() + 0.5, (double)blockPos.getY() + d, (double)blockPos.getZ() + 0.5, MathHelper.wrapDegrees(serverWorld.rand.nextFloat() * 360.0f), 0.0f);
        if (t instanceof MobEntity) {
            MobEntity mobEntity = (MobEntity)t;
            mobEntity.rotationYawHead = mobEntity.rotationYaw;
            mobEntity.renderYawOffset = mobEntity.rotationYaw;
            mobEntity.onInitialSpawn(serverWorld, serverWorld.getDifficultyForLocation(mobEntity.getPosition()), spawnReason, null, compoundNBT);
            mobEntity.playAmbientSound();
        }
        if (iTextComponent != null && t instanceof LivingEntity) {
            ((Entity)t).setCustomName(iTextComponent);
        }
        EntityType.applyItemNBT(serverWorld, playerEntity, t, compoundNBT);
        return t;
    }

    protected static double func_208051_a(IWorldReader iWorldReader, BlockPos blockPos, boolean bl, AxisAlignedBB axisAlignedBB) {
        AxisAlignedBB axisAlignedBB2 = new AxisAlignedBB(blockPos);
        if (bl) {
            axisAlignedBB2 = axisAlignedBB2.expand(0.0, -1.0, 0.0);
        }
        Stream<VoxelShape> stream = iWorldReader.func_234867_d_(null, axisAlignedBB2, EntityType::lambda$func_208051_a$0);
        return 1.0 + VoxelShapes.getAllowedOffset(Direction.Axis.Y, axisAlignedBB, stream, bl ? -2.0 : -1.0);
    }

    public static void applyItemNBT(World world, @Nullable PlayerEntity playerEntity, @Nullable Entity entity2, @Nullable CompoundNBT compoundNBT) {
        MinecraftServer minecraftServer;
        if (compoundNBT != null && compoundNBT.contains("EntityTag", 1) && (minecraftServer = world.getServer()) != null && entity2 != null && (world.isRemote || !entity2.ignoreItemEntityData() || playerEntity != null && minecraftServer.getPlayerList().canSendCommands(playerEntity.getGameProfile()))) {
            CompoundNBT compoundNBT2 = entity2.writeWithoutTypeId(new CompoundNBT());
            UUID uUID = entity2.getUniqueID();
            compoundNBT2.merge(compoundNBT.getCompound("EntityTag"));
            entity2.setUniqueId(uUID);
            entity2.read(compoundNBT2);
        }
    }

    public boolean isSerializable() {
        return this.serializable;
    }

    public boolean isSummonable() {
        return this.summonable;
    }

    public boolean isImmuneToFire() {
        return this.immuneToFire;
    }

    public boolean func_225437_d() {
        return this.field_225438_be;
    }

    public EntityClassification getClassification() {
        return this.classification;
    }

    public String getTranslationKey() {
        if (this.translationKey == null) {
            this.translationKey = Util.makeTranslationKey("entity", Registry.ENTITY_TYPE.getKey(this));
        }
        return this.translationKey;
    }

    public ITextComponent getName() {
        if (this.name == null) {
            this.name = new TranslationTextComponent(this.getTranslationKey());
        }
        return this.name;
    }

    public String toString() {
        return this.getTranslationKey();
    }

    public ResourceLocation getLootTable() {
        if (this.lootTable == null) {
            ResourceLocation resourceLocation = Registry.ENTITY_TYPE.getKey(this);
            this.lootTable = new ResourceLocation(resourceLocation.getNamespace(), "entities/" + resourceLocation.getPath());
        }
        return this.lootTable;
    }

    public float getWidth() {
        return this.size.width;
    }

    public float getHeight() {
        return this.size.height;
    }

    @Nullable
    public T create(World world) {
        return this.factory.create(this, world);
    }

    @Nullable
    public static Entity create(int n, World world) {
        return EntityType.create(world, Registry.ENTITY_TYPE.getByValue(n));
    }

    public static Optional<Entity> loadEntityUnchecked(CompoundNBT compoundNBT, World world) {
        return Util.acceptOrElse(EntityType.readEntityType(compoundNBT).map(arg_0 -> EntityType.lambda$loadEntityUnchecked$1(world, arg_0)), arg_0 -> EntityType.lambda$loadEntityUnchecked$2(compoundNBT, arg_0), () -> EntityType.lambda$loadEntityUnchecked$3(compoundNBT));
    }

    @Nullable
    private static Entity create(World world, @Nullable EntityType<?> entityType) {
        return entityType == null ? null : (Entity)entityType.create(world);
    }

    public AxisAlignedBB getBoundingBoxWithSizeApplied(double d, double d2, double d3) {
        float f = this.getWidth() / 2.0f;
        return new AxisAlignedBB(d - (double)f, d2, d3 - (double)f, d + (double)f, d2 + (double)this.getHeight(), d3 + (double)f);
    }

    public boolean func_233597_a_(BlockState blockState) {
        if (this.field_233593_bg_.contains(blockState.getBlock())) {
            return true;
        }
        if (this.immuneToFire || !blockState.isIn(BlockTags.FIRE) && !blockState.isIn(Blocks.MAGMA_BLOCK) && !CampfireBlock.isLit(blockState) && !blockState.isIn(Blocks.LAVA)) {
            return blockState.isIn(Blocks.WITHER_ROSE) || blockState.isIn(Blocks.SWEET_BERRY_BUSH) || blockState.isIn(Blocks.CACTUS);
        }
        return false;
    }

    public EntitySize getSize() {
        return this.size;
    }

    public static Optional<EntityType<?>> readEntityType(CompoundNBT compoundNBT) {
        return Registry.ENTITY_TYPE.getOptional(new ResourceLocation(compoundNBT.getString("id")));
    }

    @Nullable
    public static Entity loadEntityAndExecute(CompoundNBT compoundNBT, World world, Function<Entity, Entity> function) {
        return EntityType.loadEntity(compoundNBT, world).map(function).map(arg_0 -> EntityType.lambda$loadEntityAndExecute$4(compoundNBT, world, function, arg_0)).orElse(null);
    }

    private static Optional<Entity> loadEntity(CompoundNBT compoundNBT, World world) {
        try {
            return EntityType.loadEntityUnchecked(compoundNBT, world);
        } catch (RuntimeException runtimeException) {
            LOGGER.warn("Exception loading entity: ", (Throwable)runtimeException);
            return Optional.empty();
        }
    }

    public int func_233602_m_() {
        return this.defaultTrackingRange;
    }

    public int getUpdateFrequency() {
        return this.defaultUpdateInterval;
    }

    public boolean shouldSendVelocityUpdates() {
        return this != PLAYER && this != LLAMA_SPIT && this != WITHER && this != BAT && this != ITEM_FRAME && this != LEASH_KNOT && this != PAINTING && this != END_CRYSTAL && this != EVOKER_FANGS;
    }

    public boolean isContained(ITag<EntityType<?>> iTag) {
        return iTag.contains(this);
    }

    private static Entity lambda$loadEntityAndExecute$4(CompoundNBT compoundNBT, World world, Function function, Entity entity2) {
        if (compoundNBT.contains("Passengers", 0)) {
            ListNBT listNBT = compoundNBT.getList("Passengers", 10);
            for (int i = 0; i < listNBT.size(); ++i) {
                Entity entity3 = EntityType.loadEntityAndExecute(listNBT.getCompound(i), world, function);
                if (entity3 == null) continue;
                entity3.startRiding(entity2, false);
            }
        }
        return entity2;
    }

    private static void lambda$loadEntityUnchecked$3(CompoundNBT compoundNBT) {
        LOGGER.warn("Skipping Entity with id {}", (Object)compoundNBT.getString("id"));
    }

    private static void lambda$loadEntityUnchecked$2(CompoundNBT compoundNBT, Entity entity2) {
        entity2.read(compoundNBT);
    }

    private static Entity lambda$loadEntityUnchecked$1(World world, EntityType entityType) {
        return entityType.create(world);
    }

    private static boolean lambda$func_208051_a$0(Entity entity2) {
        return false;
    }

    public static class Builder<T extends Entity> {
        private final IFactory<T> factory;
        private final EntityClassification classification;
        private ImmutableSet<Block> field_233603_c_ = ImmutableSet.of();
        private boolean serializable = true;
        private boolean summonable = true;
        private boolean immuneToFire;
        private boolean field_225436_f;
        private int field_233604_h_ = 5;
        private int field_233605_i_ = 3;
        private EntitySize size = EntitySize.flexible(0.6f, 1.8f);

        private Builder(IFactory<T> iFactory, EntityClassification entityClassification) {
            this.factory = iFactory;
            this.classification = entityClassification;
            this.field_225436_f = entityClassification == EntityClassification.CREATURE || entityClassification == EntityClassification.MISC;
        }

        public static <T extends Entity> Builder<T> create(IFactory<T> iFactory, EntityClassification entityClassification) {
            return new Builder<T>(iFactory, entityClassification);
        }

        public static <T extends Entity> Builder<T> create(EntityClassification entityClassification) {
            return new Builder<Entity>(Builder::lambda$create$0, entityClassification);
        }

        public Builder<T> size(float f, float f2) {
            this.size = EntitySize.flexible(f, f2);
            return this;
        }

        public Builder<T> disableSummoning() {
            this.summonable = false;
            return this;
        }

        public Builder<T> disableSerialization() {
            this.serializable = false;
            return this;
        }

        public Builder<T> immuneToFire() {
            this.immuneToFire = true;
            return this;
        }

        public Builder<T> func_233607_a_(Block ... blockArray) {
            this.field_233603_c_ = ImmutableSet.copyOf(blockArray);
            return this;
        }

        public Builder<T> func_225435_d() {
            this.field_225436_f = true;
            return this;
        }

        public Builder<T> trackingRange(int n) {
            this.field_233604_h_ = n;
            return this;
        }

        public Builder<T> func_233608_b_(int n) {
            this.field_233605_i_ = n;
            return this;
        }

        public EntityType<T> build(String string) {
            if (this.serializable) {
                Util.attemptDataFix(TypeReferences.ENTITY_TYPE, string);
            }
            return new EntityType<T>(this.factory, this.classification, this.serializable, this.summonable, this.immuneToFire, this.field_225436_f, this.field_233603_c_, this.size, this.field_233604_h_, this.field_233605_i_);
        }

        private static Entity lambda$create$0(EntityType entityType, World world) {
            return null;
        }
    }

    public static interface IFactory<T extends Entity> {
        public T create(EntityType<T> var1, World var2);
    }
}

