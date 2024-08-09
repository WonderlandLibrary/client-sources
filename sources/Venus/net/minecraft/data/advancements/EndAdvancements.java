/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.data.advancements;

import java.util.function.Consumer;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.criterion.ChangeDimensionTrigger;
import net.minecraft.advancements.criterion.DistancePredicate;
import net.minecraft.advancements.criterion.EnterBlockTrigger;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.advancements.criterion.KilledTrigger;
import net.minecraft.advancements.criterion.LevitationTrigger;
import net.minecraft.advancements.criterion.LocationPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.advancements.criterion.PositionTrigger;
import net.minecraft.advancements.criterion.SummonedEntityTrigger;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.structure.Structure;

public class EndAdvancements
implements Consumer<Consumer<Advancement>> {
    @Override
    public void accept(Consumer<Advancement> consumer) {
        Advancement advancement = Advancement.Builder.builder().withDisplay(Blocks.END_STONE, (ITextComponent)new TranslationTextComponent("advancements.end.root.title"), (ITextComponent)new TranslationTextComponent("advancements.end.root.description"), new ResourceLocation("textures/gui/advancements/backgrounds/end.png"), FrameType.TASK, false, false, true).withCriterion("entered_end", ChangeDimensionTrigger.Instance.toWorld(World.THE_END)).register(consumer, "end/root");
        Advancement advancement2 = Advancement.Builder.builder().withParent(advancement).withDisplay(Blocks.DRAGON_HEAD, (ITextComponent)new TranslationTextComponent("advancements.end.kill_dragon.title"), (ITextComponent)new TranslationTextComponent("advancements.end.kill_dragon.description"), (ResourceLocation)null, FrameType.TASK, true, true, true).withCriterion("killed_dragon", KilledTrigger.Instance.playerKilledEntity(EntityPredicate.Builder.create().type(EntityType.ENDER_DRAGON))).register(consumer, "end/kill_dragon");
        Advancement advancement3 = Advancement.Builder.builder().withParent(advancement2).withDisplay(Items.ENDER_PEARL, (ITextComponent)new TranslationTextComponent("advancements.end.enter_end_gateway.title"), (ITextComponent)new TranslationTextComponent("advancements.end.enter_end_gateway.description"), (ResourceLocation)null, FrameType.TASK, true, true, true).withCriterion("entered_end_gateway", EnterBlockTrigger.Instance.forBlock(Blocks.END_GATEWAY)).register(consumer, "end/enter_end_gateway");
        Advancement.Builder.builder().withParent(advancement2).withDisplay(Items.END_CRYSTAL, (ITextComponent)new TranslationTextComponent("advancements.end.respawn_dragon.title"), (ITextComponent)new TranslationTextComponent("advancements.end.respawn_dragon.description"), (ResourceLocation)null, FrameType.GOAL, true, true, true).withCriterion("summoned_dragon", SummonedEntityTrigger.Instance.summonedEntity(EntityPredicate.Builder.create().type(EntityType.ENDER_DRAGON))).register(consumer, "end/respawn_dragon");
        Advancement advancement4 = Advancement.Builder.builder().withParent(advancement3).withDisplay(Blocks.PURPUR_BLOCK, (ITextComponent)new TranslationTextComponent("advancements.end.find_end_city.title"), (ITextComponent)new TranslationTextComponent("advancements.end.find_end_city.description"), (ResourceLocation)null, FrameType.TASK, true, true, true).withCriterion("in_city", PositionTrigger.Instance.forLocation(LocationPredicate.forFeature(Structure.field_236379_o_))).register(consumer, "end/find_end_city");
        Advancement.Builder.builder().withParent(advancement2).withDisplay(Items.DRAGON_BREATH, (ITextComponent)new TranslationTextComponent("advancements.end.dragon_breath.title"), (ITextComponent)new TranslationTextComponent("advancements.end.dragon_breath.description"), (ResourceLocation)null, FrameType.GOAL, true, true, true).withCriterion("dragon_breath", InventoryChangeTrigger.Instance.forItems(Items.DRAGON_BREATH)).register(consumer, "end/dragon_breath");
        Advancement.Builder.builder().withParent(advancement4).withDisplay(Items.SHULKER_SHELL, (ITextComponent)new TranslationTextComponent("advancements.end.levitate.title"), (ITextComponent)new TranslationTextComponent("advancements.end.levitate.description"), (ResourceLocation)null, FrameType.CHALLENGE, true, true, true).withRewards(AdvancementRewards.Builder.experience(50)).withCriterion("levitated", LevitationTrigger.Instance.forDistance(DistancePredicate.forVertical(MinMaxBounds.FloatBound.atLeast(50.0f)))).register(consumer, "end/levitate");
        Advancement.Builder.builder().withParent(advancement4).withDisplay(Items.ELYTRA, (ITextComponent)new TranslationTextComponent("advancements.end.elytra.title"), (ITextComponent)new TranslationTextComponent("advancements.end.elytra.description"), (ResourceLocation)null, FrameType.GOAL, true, true, true).withCriterion("elytra", InventoryChangeTrigger.Instance.forItems(Items.ELYTRA)).register(consumer, "end/elytra");
        Advancement.Builder.builder().withParent(advancement2).withDisplay(Blocks.DRAGON_EGG, (ITextComponent)new TranslationTextComponent("advancements.end.dragon_egg.title"), (ITextComponent)new TranslationTextComponent("advancements.end.dragon_egg.description"), (ResourceLocation)null, FrameType.GOAL, true, true, true).withCriterion("dragon_egg", InventoryChangeTrigger.Instance.forItems(Blocks.DRAGON_EGG)).register(consumer, "end/dragon_egg");
    }

    @Override
    public void accept(Object object) {
        this.accept((Consumer)object);
    }
}

