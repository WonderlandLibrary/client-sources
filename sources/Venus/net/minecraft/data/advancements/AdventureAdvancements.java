/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.data.advancements;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.IRequirementsStrategy;
import net.minecraft.advancements.criterion.ChanneledLightningTrigger;
import net.minecraft.advancements.criterion.DamagePredicate;
import net.minecraft.advancements.criterion.DamageSourcePredicate;
import net.minecraft.advancements.criterion.DistancePredicate;
import net.minecraft.advancements.criterion.EntityEquipmentPredicate;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.KilledByCrossbowTrigger;
import net.minecraft.advancements.criterion.KilledTrigger;
import net.minecraft.advancements.criterion.LocationPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.advancements.criterion.PlayerHurtEntityTrigger;
import net.minecraft.advancements.criterion.PositionTrigger;
import net.minecraft.advancements.criterion.ShotCrossbowTrigger;
import net.minecraft.advancements.criterion.SlideDownBlockTrigger;
import net.minecraft.advancements.criterion.SummonedEntityTrigger;
import net.minecraft.advancements.criterion.TargetHitTrigger;
import net.minecraft.advancements.criterion.UsedTotemTrigger;
import net.minecraft.advancements.criterion.VillagerTradeTrigger;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Items;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.raid.Raid;

public class AdventureAdvancements
implements Consumer<Consumer<Advancement>> {
    private static final List<RegistryKey<Biome>> EXPLORATION_BIOMES = ImmutableList.of(Biomes.BIRCH_FOREST_HILLS, Biomes.RIVER, Biomes.SWAMP, Biomes.DESERT, Biomes.WOODED_HILLS, Biomes.GIANT_TREE_TAIGA_HILLS, Biomes.SNOWY_TAIGA, Biomes.BADLANDS, Biomes.FOREST, Biomes.STONE_SHORE, Biomes.SNOWY_TUNDRA, Biomes.TAIGA_HILLS, Biomes.SNOWY_MOUNTAINS, Biomes.WOODED_BADLANDS_PLATEAU, Biomes.SAVANNA, Biomes.PLAINS, Biomes.FROZEN_RIVER, Biomes.GIANT_TREE_TAIGA, Biomes.SNOWY_BEACH, Biomes.JUNGLE_HILLS, Biomes.JUNGLE_EDGE, Biomes.MUSHROOM_FIELD_SHORE, Biomes.MOUNTAINS, Biomes.DESERT_HILLS, Biomes.JUNGLE, Biomes.BEACH, Biomes.SAVANNA_PLATEAU, Biomes.SNOWY_TAIGA_HILLS, Biomes.BADLANDS_PLATEAU, Biomes.DARK_FOREST, Biomes.TAIGA, Biomes.BIRCH_FOREST, Biomes.MUSHROOM_FIELDS, Biomes.WOODED_MOUNTAINS, Biomes.WARM_OCEAN, Biomes.LUKEWARM_OCEAN, Biomes.COLD_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN, Biomes.DEEP_COLD_OCEAN, Biomes.DEEP_FROZEN_OCEAN, Biomes.BAMBOO_JUNGLE, Biomes.BAMBOO_JUNGLE_HILLS);
    private static final EntityType<?>[] MOB_ENTITIES = new EntityType[]{EntityType.BLAZE, EntityType.CAVE_SPIDER, EntityType.CREEPER, EntityType.DROWNED, EntityType.ELDER_GUARDIAN, EntityType.ENDER_DRAGON, EntityType.ENDERMAN, EntityType.ENDERMITE, EntityType.EVOKER, EntityType.GHAST, EntityType.GUARDIAN, EntityType.HOGLIN, EntityType.HUSK, EntityType.MAGMA_CUBE, EntityType.PHANTOM, EntityType.PIGLIN, EntityType.field_242287_aj, EntityType.PILLAGER, EntityType.RAVAGER, EntityType.SHULKER, EntityType.SILVERFISH, EntityType.SKELETON, EntityType.SLIME, EntityType.SPIDER, EntityType.STRAY, EntityType.VEX, EntityType.VINDICATOR, EntityType.WITCH, EntityType.WITHER_SKELETON, EntityType.WITHER, EntityType.ZOGLIN, EntityType.ZOMBIE_VILLAGER, EntityType.ZOMBIE, EntityType.ZOMBIFIED_PIGLIN};

    @Override
    public void accept(Consumer<Advancement> consumer) {
        Advancement advancement = Advancement.Builder.builder().withDisplay(Items.MAP, (ITextComponent)new TranslationTextComponent("advancements.adventure.root.title"), (ITextComponent)new TranslationTextComponent("advancements.adventure.root.description"), new ResourceLocation("textures/gui/advancements/backgrounds/adventure.png"), FrameType.TASK, false, false, true).withRequirementsStrategy(IRequirementsStrategy.OR).withCriterion("killed_something", KilledTrigger.Instance.playerKilledEntity()).withCriterion("killed_by_something", KilledTrigger.Instance.entityKilledPlayer()).register(consumer, "adventure/root");
        Advancement advancement2 = Advancement.Builder.builder().withParent(advancement).withDisplay(Blocks.RED_BED, (ITextComponent)new TranslationTextComponent("advancements.adventure.sleep_in_bed.title"), (ITextComponent)new TranslationTextComponent("advancements.adventure.sleep_in_bed.description"), (ResourceLocation)null, FrameType.TASK, true, true, true).withCriterion("slept_in_bed", PositionTrigger.Instance.sleptInBed()).register(consumer, "adventure/sleep_in_bed");
        AdventureAdvancements.makeBiomesAdvancement(Advancement.Builder.builder(), EXPLORATION_BIOMES).withParent(advancement2).withDisplay(Items.DIAMOND_BOOTS, (ITextComponent)new TranslationTextComponent("advancements.adventure.adventuring_time.title"), (ITextComponent)new TranslationTextComponent("advancements.adventure.adventuring_time.description"), (ResourceLocation)null, FrameType.CHALLENGE, true, true, true).withRewards(AdvancementRewards.Builder.experience(500)).register(consumer, "adventure/adventuring_time");
        Advancement advancement3 = Advancement.Builder.builder().withParent(advancement).withDisplay(Items.EMERALD, (ITextComponent)new TranslationTextComponent("advancements.adventure.trade.title"), (ITextComponent)new TranslationTextComponent("advancements.adventure.trade.description"), (ResourceLocation)null, FrameType.TASK, true, true, true).withCriterion("traded", VillagerTradeTrigger.Instance.any()).register(consumer, "adventure/trade");
        Advancement advancement4 = this.makeMobAdvancement(Advancement.Builder.builder()).withParent(advancement).withDisplay(Items.IRON_SWORD, (ITextComponent)new TranslationTextComponent("advancements.adventure.kill_a_mob.title"), (ITextComponent)new TranslationTextComponent("advancements.adventure.kill_a_mob.description"), (ResourceLocation)null, FrameType.TASK, true, true, true).withRequirementsStrategy(IRequirementsStrategy.OR).register(consumer, "adventure/kill_a_mob");
        this.makeMobAdvancement(Advancement.Builder.builder()).withParent(advancement4).withDisplay(Items.DIAMOND_SWORD, (ITextComponent)new TranslationTextComponent("advancements.adventure.kill_all_mobs.title"), (ITextComponent)new TranslationTextComponent("advancements.adventure.kill_all_mobs.description"), (ResourceLocation)null, FrameType.CHALLENGE, true, true, true).withRewards(AdvancementRewards.Builder.experience(100)).register(consumer, "adventure/kill_all_mobs");
        Advancement advancement5 = Advancement.Builder.builder().withParent(advancement4).withDisplay(Items.BOW, (ITextComponent)new TranslationTextComponent("advancements.adventure.shoot_arrow.title"), (ITextComponent)new TranslationTextComponent("advancements.adventure.shoot_arrow.description"), (ResourceLocation)null, FrameType.TASK, true, true, true).withCriterion("shot_arrow", PlayerHurtEntityTrigger.Instance.forDamage(DamagePredicate.Builder.create().type(DamageSourcePredicate.Builder.damageType().isProjectile(true).direct(EntityPredicate.Builder.create().type(EntityTypeTags.ARROWS))))).register(consumer, "adventure/shoot_arrow");
        Advancement advancement6 = Advancement.Builder.builder().withParent(advancement4).withDisplay(Items.TRIDENT, (ITextComponent)new TranslationTextComponent("advancements.adventure.throw_trident.title"), (ITextComponent)new TranslationTextComponent("advancements.adventure.throw_trident.description"), (ResourceLocation)null, FrameType.TASK, true, true, true).withCriterion("shot_trident", PlayerHurtEntityTrigger.Instance.forDamage(DamagePredicate.Builder.create().type(DamageSourcePredicate.Builder.damageType().isProjectile(true).direct(EntityPredicate.Builder.create().type(EntityType.TRIDENT))))).register(consumer, "adventure/throw_trident");
        Advancement.Builder.builder().withParent(advancement6).withDisplay(Items.TRIDENT, (ITextComponent)new TranslationTextComponent("advancements.adventure.very_very_frightening.title"), (ITextComponent)new TranslationTextComponent("advancements.adventure.very_very_frightening.description"), (ResourceLocation)null, FrameType.TASK, true, true, true).withCriterion("struck_villager", ChanneledLightningTrigger.Instance.channeledLightning(EntityPredicate.Builder.create().type(EntityType.VILLAGER).build())).register(consumer, "adventure/very_very_frightening");
        Advancement.Builder.builder().withParent(advancement3).withDisplay(Blocks.CARVED_PUMPKIN, (ITextComponent)new TranslationTextComponent("advancements.adventure.summon_iron_golem.title"), (ITextComponent)new TranslationTextComponent("advancements.adventure.summon_iron_golem.description"), (ResourceLocation)null, FrameType.GOAL, true, true, true).withCriterion("summoned_golem", SummonedEntityTrigger.Instance.summonedEntity(EntityPredicate.Builder.create().type(EntityType.IRON_GOLEM))).register(consumer, "adventure/summon_iron_golem");
        Advancement.Builder.builder().withParent(advancement5).withDisplay(Items.ARROW, (ITextComponent)new TranslationTextComponent("advancements.adventure.sniper_duel.title"), (ITextComponent)new TranslationTextComponent("advancements.adventure.sniper_duel.description"), (ResourceLocation)null, FrameType.CHALLENGE, true, true, true).withRewards(AdvancementRewards.Builder.experience(50)).withCriterion("killed_skeleton", KilledTrigger.Instance.playerKilledEntity(EntityPredicate.Builder.create().type(EntityType.SKELETON).distance(DistancePredicate.forHorizontal(MinMaxBounds.FloatBound.atLeast(50.0f))), DamageSourcePredicate.Builder.damageType().isProjectile(true))).register(consumer, "adventure/sniper_duel");
        Advancement.Builder.builder().withParent(advancement4).withDisplay(Items.TOTEM_OF_UNDYING, (ITextComponent)new TranslationTextComponent("advancements.adventure.totem_of_undying.title"), (ITextComponent)new TranslationTextComponent("advancements.adventure.totem_of_undying.description"), (ResourceLocation)null, FrameType.GOAL, true, true, true).withCriterion("used_totem", UsedTotemTrigger.Instance.usedTotem(Items.TOTEM_OF_UNDYING)).register(consumer, "adventure/totem_of_undying");
        Advancement advancement7 = Advancement.Builder.builder().withParent(advancement).withDisplay(Items.CROSSBOW, (ITextComponent)new TranslationTextComponent("advancements.adventure.ol_betsy.title"), (ITextComponent)new TranslationTextComponent("advancements.adventure.ol_betsy.description"), (ResourceLocation)null, FrameType.TASK, true, true, true).withCriterion("shot_crossbow", ShotCrossbowTrigger.Instance.create(Items.CROSSBOW)).register(consumer, "adventure/ol_betsy");
        Advancement.Builder.builder().withParent(advancement7).withDisplay(Items.CROSSBOW, (ITextComponent)new TranslationTextComponent("advancements.adventure.whos_the_pillager_now.title"), (ITextComponent)new TranslationTextComponent("advancements.adventure.whos_the_pillager_now.description"), (ResourceLocation)null, FrameType.TASK, true, true, true).withCriterion("kill_pillager", KilledByCrossbowTrigger.Instance.fromBuilders(EntityPredicate.Builder.create().type(EntityType.PILLAGER))).register(consumer, "adventure/whos_the_pillager_now");
        Advancement.Builder.builder().withParent(advancement7).withDisplay(Items.CROSSBOW, (ITextComponent)new TranslationTextComponent("advancements.adventure.two_birds_one_arrow.title"), (ITextComponent)new TranslationTextComponent("advancements.adventure.two_birds_one_arrow.description"), (ResourceLocation)null, FrameType.CHALLENGE, true, true, true).withRewards(AdvancementRewards.Builder.experience(65)).withCriterion("two_birds", KilledByCrossbowTrigger.Instance.fromBuilders(EntityPredicate.Builder.create().type(EntityType.PHANTOM), EntityPredicate.Builder.create().type(EntityType.PHANTOM))).register(consumer, "adventure/two_birds_one_arrow");
        Advancement.Builder.builder().withParent(advancement7).withDisplay(Items.CROSSBOW, (ITextComponent)new TranslationTextComponent("advancements.adventure.arbalistic.title"), (ITextComponent)new TranslationTextComponent("advancements.adventure.arbalistic.description"), (ResourceLocation)null, FrameType.CHALLENGE, true, true, false).withRewards(AdvancementRewards.Builder.experience(85)).withCriterion("arbalistic", KilledByCrossbowTrigger.Instance.fromBounds(MinMaxBounds.IntBound.exactly(5))).register(consumer, "adventure/arbalistic");
        Advancement advancement8 = Advancement.Builder.builder().withParent(advancement).withDisplay(Raid.createIllagerBanner(), (ITextComponent)new TranslationTextComponent("advancements.adventure.voluntary_exile.title"), (ITextComponent)new TranslationTextComponent("advancements.adventure.voluntary_exile.description"), (ResourceLocation)null, FrameType.TASK, true, true, false).withCriterion("voluntary_exile", KilledTrigger.Instance.playerKilledEntity(EntityPredicate.Builder.create().type(EntityTypeTags.RAIDERS).equipment(EntityEquipmentPredicate.WEARING_ILLAGER_BANNER))).register(consumer, "adventure/voluntary_exile");
        Advancement.Builder.builder().withParent(advancement8).withDisplay(Raid.createIllagerBanner(), (ITextComponent)new TranslationTextComponent("advancements.adventure.hero_of_the_village.title"), (ITextComponent)new TranslationTextComponent("advancements.adventure.hero_of_the_village.description"), (ResourceLocation)null, FrameType.CHALLENGE, true, true, false).withRewards(AdvancementRewards.Builder.experience(100)).withCriterion("hero_of_the_village", PositionTrigger.Instance.villageHero()).register(consumer, "adventure/hero_of_the_village");
        Advancement.Builder.builder().withParent(advancement).withDisplay(Blocks.HONEY_BLOCK.asItem(), (ITextComponent)new TranslationTextComponent("advancements.adventure.honey_block_slide.title"), (ITextComponent)new TranslationTextComponent("advancements.adventure.honey_block_slide.description"), (ResourceLocation)null, FrameType.TASK, true, true, true).withCriterion("honey_block_slide", SlideDownBlockTrigger.Instance.create(Blocks.HONEY_BLOCK)).register(consumer, "adventure/honey_block_slide");
        Advancement.Builder.builder().withParent(advancement5).withDisplay(Blocks.TARGET.asItem(), (ITextComponent)new TranslationTextComponent("advancements.adventure.bullseye.title"), (ITextComponent)new TranslationTextComponent("advancements.adventure.bullseye.description"), (ResourceLocation)null, FrameType.CHALLENGE, true, true, true).withRewards(AdvancementRewards.Builder.experience(50)).withCriterion("bullseye", TargetHitTrigger.Instance.create(MinMaxBounds.IntBound.exactly(15), EntityPredicate.AndPredicate.createAndFromEntityCondition(EntityPredicate.Builder.create().distance(DistancePredicate.forHorizontal(MinMaxBounds.FloatBound.atLeast(30.0f))).build()))).register(consumer, "adventure/bullseye");
    }

    private Advancement.Builder makeMobAdvancement(Advancement.Builder builder) {
        for (EntityType<?> entityType : MOB_ENTITIES) {
            builder.withCriterion(Registry.ENTITY_TYPE.getKey(entityType).toString(), KilledTrigger.Instance.playerKilledEntity(EntityPredicate.Builder.create().type(entityType)));
        }
        return builder;
    }

    protected static Advancement.Builder makeBiomesAdvancement(Advancement.Builder builder, List<RegistryKey<Biome>> list) {
        for (RegistryKey<Biome> registryKey : list) {
            builder.withCriterion(registryKey.getLocation().toString(), PositionTrigger.Instance.forLocation(LocationPredicate.forBiome(registryKey)));
        }
        return builder;
    }

    @Override
    public void accept(Object object) {
        this.accept((Consumer)object);
    }
}

