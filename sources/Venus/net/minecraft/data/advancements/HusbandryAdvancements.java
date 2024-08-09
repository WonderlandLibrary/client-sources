/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.data.advancements;

import java.util.function.Consumer;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.IRequirementsStrategy;
import net.minecraft.advancements.criterion.BeeNestDestroyedTrigger;
import net.minecraft.advancements.criterion.BlockPredicate;
import net.minecraft.advancements.criterion.BredAnimalsTrigger;
import net.minecraft.advancements.criterion.ConsumeItemTrigger;
import net.minecraft.advancements.criterion.EnchantmentPredicate;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.FilledBucketTrigger;
import net.minecraft.advancements.criterion.FishingRodHookedTrigger;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.LocationPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.advancements.criterion.PlacedBlockTrigger;
import net.minecraft.advancements.criterion.RightClickBlockWithItemTrigger;
import net.minecraft.advancements.criterion.TameAnimalTrigger;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class HusbandryAdvancements
implements Consumer<Consumer<Advancement>> {
    private static final EntityType<?>[] BREEDABLE_ANIMALS = new EntityType[]{EntityType.HORSE, EntityType.DONKEY, EntityType.MULE, EntityType.SHEEP, EntityType.COW, EntityType.MOOSHROOM, EntityType.PIG, EntityType.CHICKEN, EntityType.WOLF, EntityType.OCELOT, EntityType.RABBIT, EntityType.LLAMA, EntityType.CAT, EntityType.PANDA, EntityType.FOX, EntityType.BEE, EntityType.HOGLIN, EntityType.STRIDER};
    private static final Item[] FISH_ITEMS = new Item[]{Items.COD, Items.TROPICAL_FISH, Items.PUFFERFISH, Items.SALMON};
    private static final Item[] FISH_BUCKETS = new Item[]{Items.COD_BUCKET, Items.TROPICAL_FISH_BUCKET, Items.PUFFERFISH_BUCKET, Items.SALMON_BUCKET};
    private static final Item[] BALANCED_DIET = new Item[]{Items.APPLE, Items.MUSHROOM_STEW, Items.BREAD, Items.PORKCHOP, Items.COOKED_PORKCHOP, Items.GOLDEN_APPLE, Items.ENCHANTED_GOLDEN_APPLE, Items.COD, Items.SALMON, Items.TROPICAL_FISH, Items.PUFFERFISH, Items.COOKED_COD, Items.COOKED_SALMON, Items.COOKIE, Items.MELON_SLICE, Items.BEEF, Items.COOKED_BEEF, Items.CHICKEN, Items.COOKED_CHICKEN, Items.ROTTEN_FLESH, Items.SPIDER_EYE, Items.CARROT, Items.POTATO, Items.BAKED_POTATO, Items.POISONOUS_POTATO, Items.GOLDEN_CARROT, Items.PUMPKIN_PIE, Items.RABBIT, Items.COOKED_RABBIT, Items.RABBIT_STEW, Items.MUTTON, Items.COOKED_MUTTON, Items.CHORUS_FRUIT, Items.BEETROOT, Items.BEETROOT_SOUP, Items.DRIED_KELP, Items.SUSPICIOUS_STEW, Items.SWEET_BERRIES, Items.HONEY_BOTTLE};

    @Override
    public void accept(Consumer<Advancement> consumer) {
        Advancement advancement = Advancement.Builder.builder().withDisplay(Blocks.HAY_BLOCK, (ITextComponent)new TranslationTextComponent("advancements.husbandry.root.title"), (ITextComponent)new TranslationTextComponent("advancements.husbandry.root.description"), new ResourceLocation("textures/gui/advancements/backgrounds/husbandry.png"), FrameType.TASK, false, false, true).withCriterion("consumed_item", ConsumeItemTrigger.Instance.any()).register(consumer, "husbandry/root");
        Advancement advancement2 = Advancement.Builder.builder().withParent(advancement).withDisplay(Items.WHEAT, (ITextComponent)new TranslationTextComponent("advancements.husbandry.plant_seed.title"), (ITextComponent)new TranslationTextComponent("advancements.husbandry.plant_seed.description"), (ResourceLocation)null, FrameType.TASK, true, true, true).withRequirementsStrategy(IRequirementsStrategy.OR).withCriterion("wheat", PlacedBlockTrigger.Instance.placedBlock(Blocks.WHEAT)).withCriterion("pumpkin_stem", PlacedBlockTrigger.Instance.placedBlock(Blocks.PUMPKIN_STEM)).withCriterion("melon_stem", PlacedBlockTrigger.Instance.placedBlock(Blocks.MELON_STEM)).withCriterion("beetroots", PlacedBlockTrigger.Instance.placedBlock(Blocks.BEETROOTS)).withCriterion("nether_wart", PlacedBlockTrigger.Instance.placedBlock(Blocks.NETHER_WART)).register(consumer, "husbandry/plant_seed");
        Advancement advancement3 = Advancement.Builder.builder().withParent(advancement).withDisplay(Items.WHEAT, (ITextComponent)new TranslationTextComponent("advancements.husbandry.breed_an_animal.title"), (ITextComponent)new TranslationTextComponent("advancements.husbandry.breed_an_animal.description"), (ResourceLocation)null, FrameType.TASK, true, true, true).withRequirementsStrategy(IRequirementsStrategy.OR).withCriterion("bred", BredAnimalsTrigger.Instance.any()).register(consumer, "husbandry/breed_an_animal");
        this.makeBalancedDiet(Advancement.Builder.builder()).withParent(advancement2).withDisplay(Items.APPLE, (ITextComponent)new TranslationTextComponent("advancements.husbandry.balanced_diet.title"), (ITextComponent)new TranslationTextComponent("advancements.husbandry.balanced_diet.description"), (ResourceLocation)null, FrameType.CHALLENGE, true, true, true).withRewards(AdvancementRewards.Builder.experience(100)).register(consumer, "husbandry/balanced_diet");
        Advancement.Builder.builder().withParent(advancement2).withDisplay(Items.NETHERITE_HOE, (ITextComponent)new TranslationTextComponent("advancements.husbandry.netherite_hoe.title"), (ITextComponent)new TranslationTextComponent("advancements.husbandry.netherite_hoe.description"), (ResourceLocation)null, FrameType.CHALLENGE, true, true, true).withRewards(AdvancementRewards.Builder.experience(100)).withCriterion("netherite_hoe", InventoryChangeTrigger.Instance.forItems(Items.NETHERITE_HOE)).register(consumer, "husbandry/obtain_netherite_hoe");
        Advancement advancement4 = Advancement.Builder.builder().withParent(advancement).withDisplay(Items.LEAD, (ITextComponent)new TranslationTextComponent("advancements.husbandry.tame_an_animal.title"), (ITextComponent)new TranslationTextComponent("advancements.husbandry.tame_an_animal.description"), (ResourceLocation)null, FrameType.TASK, true, true, true).withCriterion("tamed_animal", TameAnimalTrigger.Instance.any()).register(consumer, "husbandry/tame_an_animal");
        this.makeBredAllAnimals(Advancement.Builder.builder()).withParent(advancement3).withDisplay(Items.GOLDEN_CARROT, (ITextComponent)new TranslationTextComponent("advancements.husbandry.breed_all_animals.title"), (ITextComponent)new TranslationTextComponent("advancements.husbandry.breed_all_animals.description"), (ResourceLocation)null, FrameType.CHALLENGE, true, true, true).withRewards(AdvancementRewards.Builder.experience(100)).register(consumer, "husbandry/bred_all_animals");
        Advancement advancement5 = this.makeFish(Advancement.Builder.builder()).withParent(advancement).withRequirementsStrategy(IRequirementsStrategy.OR).withDisplay(Items.FISHING_ROD, (ITextComponent)new TranslationTextComponent("advancements.husbandry.fishy_business.title"), (ITextComponent)new TranslationTextComponent("advancements.husbandry.fishy_business.description"), (ResourceLocation)null, FrameType.TASK, true, true, true).register(consumer, "husbandry/fishy_business");
        this.makeFishBucket(Advancement.Builder.builder()).withParent(advancement5).withRequirementsStrategy(IRequirementsStrategy.OR).withDisplay(Items.PUFFERFISH_BUCKET, (ITextComponent)new TranslationTextComponent("advancements.husbandry.tactical_fishing.title"), (ITextComponent)new TranslationTextComponent("advancements.husbandry.tactical_fishing.description"), (ResourceLocation)null, FrameType.TASK, true, true, true).register(consumer, "husbandry/tactical_fishing");
        this.makeCompleteAdvancement(Advancement.Builder.builder()).withParent(advancement4).withDisplay(Items.COD, (ITextComponent)new TranslationTextComponent("advancements.husbandry.complete_catalogue.title"), (ITextComponent)new TranslationTextComponent("advancements.husbandry.complete_catalogue.description"), (ResourceLocation)null, FrameType.CHALLENGE, true, true, true).withRewards(AdvancementRewards.Builder.experience(50)).register(consumer, "husbandry/complete_catalogue");
        Advancement.Builder.builder().withParent(advancement).withCriterion("safely_harvest_honey", RightClickBlockWithItemTrigger.Instance.create(LocationPredicate.Builder.builder().block(BlockPredicate.Builder.createBuilder().setTag(BlockTags.BEEHIVES).build()).smokey(true), ItemPredicate.Builder.create().item(Items.GLASS_BOTTLE))).withDisplay(Items.HONEY_BOTTLE, (ITextComponent)new TranslationTextComponent("advancements.husbandry.safely_harvest_honey.title"), (ITextComponent)new TranslationTextComponent("advancements.husbandry.safely_harvest_honey.description"), (ResourceLocation)null, FrameType.TASK, true, true, true).register(consumer, "husbandry/safely_harvest_honey");
        Advancement.Builder.builder().withParent(advancement).withCriterion("silk_touch_nest", BeeNestDestroyedTrigger.Instance.createNewInstance(Blocks.BEE_NEST, ItemPredicate.Builder.create().enchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.atLeast(1))), MinMaxBounds.IntBound.exactly(3))).withDisplay(Blocks.BEE_NEST, (ITextComponent)new TranslationTextComponent("advancements.husbandry.silk_touch_nest.title"), (ITextComponent)new TranslationTextComponent("advancements.husbandry.silk_touch_nest.description"), (ResourceLocation)null, FrameType.TASK, true, true, true).register(consumer, "husbandry/silk_touch_nest");
    }

    private Advancement.Builder makeBalancedDiet(Advancement.Builder builder) {
        for (Item item : BALANCED_DIET) {
            builder.withCriterion(Registry.ITEM.getKey(item).getPath(), ConsumeItemTrigger.Instance.forItem(item));
        }
        return builder;
    }

    private Advancement.Builder makeBredAllAnimals(Advancement.Builder builder) {
        for (EntityType<?> entityType : BREEDABLE_ANIMALS) {
            builder.withCriterion(EntityType.getKey(entityType).toString(), BredAnimalsTrigger.Instance.forParent(EntityPredicate.Builder.create().type(entityType)));
        }
        builder.withCriterion(EntityType.getKey(EntityType.TURTLE).toString(), BredAnimalsTrigger.Instance.forAll(EntityPredicate.Builder.create().type(EntityType.TURTLE).build(), EntityPredicate.Builder.create().type(EntityType.TURTLE).build(), EntityPredicate.ANY));
        return builder;
    }

    private Advancement.Builder makeFishBucket(Advancement.Builder builder) {
        for (Item item : FISH_BUCKETS) {
            builder.withCriterion(Registry.ITEM.getKey(item).getPath(), FilledBucketTrigger.Instance.forItem(ItemPredicate.Builder.create().item(item).build()));
        }
        return builder;
    }

    private Advancement.Builder makeFish(Advancement.Builder builder) {
        for (Item item : FISH_ITEMS) {
            builder.withCriterion(Registry.ITEM.getKey(item).getPath(), FishingRodHookedTrigger.Instance.create(ItemPredicate.ANY, EntityPredicate.ANY, ItemPredicate.Builder.create().item(item).build()));
        }
        return builder;
    }

    private Advancement.Builder makeCompleteAdvancement(Advancement.Builder builder) {
        CatEntity.TEXTURE_BY_ID.forEach((arg_0, arg_1) -> HusbandryAdvancements.lambda$makeCompleteAdvancement$0(builder, arg_0, arg_1));
        return builder;
    }

    @Override
    public void accept(Object object) {
        this.accept((Consumer)object);
    }

    private static void lambda$makeCompleteAdvancement$0(Advancement.Builder builder, Integer n, ResourceLocation resourceLocation) {
        builder.withCriterion(resourceLocation.getPath(), TameAnimalTrigger.Instance.create(EntityPredicate.Builder.create().catType(resourceLocation).build()));
    }
}

