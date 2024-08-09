/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.data.advancements;

import java.util.function.Consumer;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.IRequirementsStrategy;
import net.minecraft.advancements.criterion.ChangeDimensionTrigger;
import net.minecraft.advancements.criterion.CuredZombieVillagerTrigger;
import net.minecraft.advancements.criterion.DamagePredicate;
import net.minecraft.advancements.criterion.DamageSourcePredicate;
import net.minecraft.advancements.criterion.EnchantedItemTrigger;
import net.minecraft.advancements.criterion.EntityHurtPlayerTrigger;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.LocationPredicate;
import net.minecraft.advancements.criterion.PositionTrigger;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.structure.Structure;

public class StoryAdvancements
implements Consumer<Consumer<Advancement>> {
    @Override
    public void accept(Consumer<Advancement> consumer) {
        Advancement advancement = Advancement.Builder.builder().withDisplay(Blocks.GRASS_BLOCK, (ITextComponent)new TranslationTextComponent("advancements.story.root.title"), (ITextComponent)new TranslationTextComponent("advancements.story.root.description"), new ResourceLocation("textures/gui/advancements/backgrounds/stone.png"), FrameType.TASK, false, false, true).withCriterion("crafting_table", InventoryChangeTrigger.Instance.forItems(Blocks.CRAFTING_TABLE)).register(consumer, "story/root");
        Advancement advancement2 = Advancement.Builder.builder().withParent(advancement).withDisplay(Items.WOODEN_PICKAXE, (ITextComponent)new TranslationTextComponent("advancements.story.mine_stone.title"), (ITextComponent)new TranslationTextComponent("advancements.story.mine_stone.description"), (ResourceLocation)null, FrameType.TASK, true, true, true).withCriterion("get_stone", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().tag(ItemTags.STONE_TOOL_MATERIALS).build())).register(consumer, "story/mine_stone");
        Advancement advancement3 = Advancement.Builder.builder().withParent(advancement2).withDisplay(Items.STONE_PICKAXE, (ITextComponent)new TranslationTextComponent("advancements.story.upgrade_tools.title"), (ITextComponent)new TranslationTextComponent("advancements.story.upgrade_tools.description"), (ResourceLocation)null, FrameType.TASK, true, true, true).withCriterion("stone_pickaxe", InventoryChangeTrigger.Instance.forItems(Items.STONE_PICKAXE)).register(consumer, "story/upgrade_tools");
        Advancement advancement4 = Advancement.Builder.builder().withParent(advancement3).withDisplay(Items.IRON_INGOT, (ITextComponent)new TranslationTextComponent("advancements.story.smelt_iron.title"), (ITextComponent)new TranslationTextComponent("advancements.story.smelt_iron.description"), (ResourceLocation)null, FrameType.TASK, true, true, true).withCriterion("iron", InventoryChangeTrigger.Instance.forItems(Items.IRON_INGOT)).register(consumer, "story/smelt_iron");
        Advancement advancement5 = Advancement.Builder.builder().withParent(advancement4).withDisplay(Items.IRON_PICKAXE, (ITextComponent)new TranslationTextComponent("advancements.story.iron_tools.title"), (ITextComponent)new TranslationTextComponent("advancements.story.iron_tools.description"), (ResourceLocation)null, FrameType.TASK, true, true, true).withCriterion("iron_pickaxe", InventoryChangeTrigger.Instance.forItems(Items.IRON_PICKAXE)).register(consumer, "story/iron_tools");
        Advancement advancement6 = Advancement.Builder.builder().withParent(advancement5).withDisplay(Items.DIAMOND, (ITextComponent)new TranslationTextComponent("advancements.story.mine_diamond.title"), (ITextComponent)new TranslationTextComponent("advancements.story.mine_diamond.description"), (ResourceLocation)null, FrameType.TASK, true, true, true).withCriterion("diamond", InventoryChangeTrigger.Instance.forItems(Items.DIAMOND)).register(consumer, "story/mine_diamond");
        Advancement advancement7 = Advancement.Builder.builder().withParent(advancement4).withDisplay(Items.LAVA_BUCKET, (ITextComponent)new TranslationTextComponent("advancements.story.lava_bucket.title"), (ITextComponent)new TranslationTextComponent("advancements.story.lava_bucket.description"), (ResourceLocation)null, FrameType.TASK, true, true, true).withCriterion("lava_bucket", InventoryChangeTrigger.Instance.forItems(Items.LAVA_BUCKET)).register(consumer, "story/lava_bucket");
        Advancement advancement8 = Advancement.Builder.builder().withParent(advancement4).withDisplay(Items.IRON_CHESTPLATE, (ITextComponent)new TranslationTextComponent("advancements.story.obtain_armor.title"), (ITextComponent)new TranslationTextComponent("advancements.story.obtain_armor.description"), (ResourceLocation)null, FrameType.TASK, true, true, true).withRequirementsStrategy(IRequirementsStrategy.OR).withCriterion("iron_helmet", InventoryChangeTrigger.Instance.forItems(Items.IRON_HELMET)).withCriterion("iron_chestplate", InventoryChangeTrigger.Instance.forItems(Items.IRON_CHESTPLATE)).withCriterion("iron_leggings", InventoryChangeTrigger.Instance.forItems(Items.IRON_LEGGINGS)).withCriterion("iron_boots", InventoryChangeTrigger.Instance.forItems(Items.IRON_BOOTS)).register(consumer, "story/obtain_armor");
        Advancement.Builder.builder().withParent(advancement6).withDisplay(Items.ENCHANTED_BOOK, (ITextComponent)new TranslationTextComponent("advancements.story.enchant_item.title"), (ITextComponent)new TranslationTextComponent("advancements.story.enchant_item.description"), (ResourceLocation)null, FrameType.TASK, true, true, true).withCriterion("enchanted_item", EnchantedItemTrigger.Instance.any()).register(consumer, "story/enchant_item");
        Advancement advancement9 = Advancement.Builder.builder().withParent(advancement7).withDisplay(Blocks.OBSIDIAN, (ITextComponent)new TranslationTextComponent("advancements.story.form_obsidian.title"), (ITextComponent)new TranslationTextComponent("advancements.story.form_obsidian.description"), (ResourceLocation)null, FrameType.TASK, true, true, true).withCriterion("obsidian", InventoryChangeTrigger.Instance.forItems(Blocks.OBSIDIAN)).register(consumer, "story/form_obsidian");
        Advancement.Builder.builder().withParent(advancement8).withDisplay(Items.SHIELD, (ITextComponent)new TranslationTextComponent("advancements.story.deflect_arrow.title"), (ITextComponent)new TranslationTextComponent("advancements.story.deflect_arrow.description"), (ResourceLocation)null, FrameType.TASK, true, true, true).withCriterion("deflected_projectile", EntityHurtPlayerTrigger.Instance.forDamage(DamagePredicate.Builder.create().type(DamageSourcePredicate.Builder.damageType().isProjectile(true)).blocked(true))).register(consumer, "story/deflect_arrow");
        Advancement.Builder.builder().withParent(advancement6).withDisplay(Items.DIAMOND_CHESTPLATE, (ITextComponent)new TranslationTextComponent("advancements.story.shiny_gear.title"), (ITextComponent)new TranslationTextComponent("advancements.story.shiny_gear.description"), (ResourceLocation)null, FrameType.TASK, true, true, true).withRequirementsStrategy(IRequirementsStrategy.OR).withCriterion("diamond_helmet", InventoryChangeTrigger.Instance.forItems(Items.DIAMOND_HELMET)).withCriterion("diamond_chestplate", InventoryChangeTrigger.Instance.forItems(Items.DIAMOND_CHESTPLATE)).withCriterion("diamond_leggings", InventoryChangeTrigger.Instance.forItems(Items.DIAMOND_LEGGINGS)).withCriterion("diamond_boots", InventoryChangeTrigger.Instance.forItems(Items.DIAMOND_BOOTS)).register(consumer, "story/shiny_gear");
        Advancement advancement10 = Advancement.Builder.builder().withParent(advancement9).withDisplay(Items.FLINT_AND_STEEL, (ITextComponent)new TranslationTextComponent("advancements.story.enter_the_nether.title"), (ITextComponent)new TranslationTextComponent("advancements.story.enter_the_nether.description"), (ResourceLocation)null, FrameType.TASK, true, true, true).withCriterion("entered_nether", ChangeDimensionTrigger.Instance.toWorld(World.THE_NETHER)).register(consumer, "story/enter_the_nether");
        Advancement.Builder.builder().withParent(advancement10).withDisplay(Items.GOLDEN_APPLE, (ITextComponent)new TranslationTextComponent("advancements.story.cure_zombie_villager.title"), (ITextComponent)new TranslationTextComponent("advancements.story.cure_zombie_villager.description"), (ResourceLocation)null, FrameType.GOAL, true, true, true).withCriterion("cured_zombie", CuredZombieVillagerTrigger.Instance.any()).register(consumer, "story/cure_zombie_villager");
        Advancement advancement11 = Advancement.Builder.builder().withParent(advancement10).withDisplay(Items.ENDER_EYE, (ITextComponent)new TranslationTextComponent("advancements.story.follow_ender_eye.title"), (ITextComponent)new TranslationTextComponent("advancements.story.follow_ender_eye.description"), (ResourceLocation)null, FrameType.TASK, true, true, true).withCriterion("in_stronghold", PositionTrigger.Instance.forLocation(LocationPredicate.forFeature(Structure.field_236375_k_))).register(consumer, "story/follow_ender_eye");
        Advancement.Builder.builder().withParent(advancement11).withDisplay(Blocks.END_STONE, (ITextComponent)new TranslationTextComponent("advancements.story.enter_the_end.title"), (ITextComponent)new TranslationTextComponent("advancements.story.enter_the_end.description"), (ResourceLocation)null, FrameType.TASK, true, true, true).withCriterion("entered_end", ChangeDimensionTrigger.Instance.toWorld(World.THE_END)).register(consumer, "story/enter_the_end");
    }

    @Override
    public void accept(Object object) {
        this.accept((Consumer)object);
    }
}

