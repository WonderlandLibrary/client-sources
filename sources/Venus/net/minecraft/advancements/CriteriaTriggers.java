/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.advancements;

import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.criterion.BeeNestDestroyedTrigger;
import net.minecraft.advancements.criterion.BredAnimalsTrigger;
import net.minecraft.advancements.criterion.BrewedPotionTrigger;
import net.minecraft.advancements.criterion.ChangeDimensionTrigger;
import net.minecraft.advancements.criterion.ChanneledLightningTrigger;
import net.minecraft.advancements.criterion.ConstructBeaconTrigger;
import net.minecraft.advancements.criterion.ConsumeItemTrigger;
import net.minecraft.advancements.criterion.CuredZombieVillagerTrigger;
import net.minecraft.advancements.criterion.EffectsChangedTrigger;
import net.minecraft.advancements.criterion.EnchantedItemTrigger;
import net.minecraft.advancements.criterion.EnterBlockTrigger;
import net.minecraft.advancements.criterion.EntityHurtPlayerTrigger;
import net.minecraft.advancements.criterion.FilledBucketTrigger;
import net.minecraft.advancements.criterion.FishingRodHookedTrigger;
import net.minecraft.advancements.criterion.ImpossibleTrigger;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.advancements.criterion.ItemDurabilityTrigger;
import net.minecraft.advancements.criterion.KilledByCrossbowTrigger;
import net.minecraft.advancements.criterion.KilledTrigger;
import net.minecraft.advancements.criterion.LevitationTrigger;
import net.minecraft.advancements.criterion.NetherTravelTrigger;
import net.minecraft.advancements.criterion.PlacedBlockTrigger;
import net.minecraft.advancements.criterion.PlayerEntityInteractionTrigger;
import net.minecraft.advancements.criterion.PlayerGeneratesContainerLootTrigger;
import net.minecraft.advancements.criterion.PlayerHurtEntityTrigger;
import net.minecraft.advancements.criterion.PositionTrigger;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.advancements.criterion.RightClickBlockWithItemTrigger;
import net.minecraft.advancements.criterion.ShotCrossbowTrigger;
import net.minecraft.advancements.criterion.SlideDownBlockTrigger;
import net.minecraft.advancements.criterion.SummonedEntityTrigger;
import net.minecraft.advancements.criterion.TameAnimalTrigger;
import net.minecraft.advancements.criterion.TargetHitTrigger;
import net.minecraft.advancements.criterion.ThrownItemPickedUpByEntityTrigger;
import net.minecraft.advancements.criterion.TickTrigger;
import net.minecraft.advancements.criterion.UsedEnderEyeTrigger;
import net.minecraft.advancements.criterion.UsedTotemTrigger;
import net.minecraft.advancements.criterion.VillagerTradeTrigger;
import net.minecraft.util.ResourceLocation;

public class CriteriaTriggers {
    private static final Map<ResourceLocation, ICriterionTrigger<?>> REGISTRY = Maps.newHashMap();
    public static final ImpossibleTrigger IMPOSSIBLE = CriteriaTriggers.register(new ImpossibleTrigger());
    public static final KilledTrigger PLAYER_KILLED_ENTITY = CriteriaTriggers.register(new KilledTrigger(new ResourceLocation("player_killed_entity")));
    public static final KilledTrigger ENTITY_KILLED_PLAYER = CriteriaTriggers.register(new KilledTrigger(new ResourceLocation("entity_killed_player")));
    public static final EnterBlockTrigger ENTER_BLOCK = CriteriaTriggers.register(new EnterBlockTrigger());
    public static final InventoryChangeTrigger INVENTORY_CHANGED = CriteriaTriggers.register(new InventoryChangeTrigger());
    public static final RecipeUnlockedTrigger RECIPE_UNLOCKED = CriteriaTriggers.register(new RecipeUnlockedTrigger());
    public static final PlayerHurtEntityTrigger PLAYER_HURT_ENTITY = CriteriaTriggers.register(new PlayerHurtEntityTrigger());
    public static final EntityHurtPlayerTrigger ENTITY_HURT_PLAYER = CriteriaTriggers.register(new EntityHurtPlayerTrigger());
    public static final EnchantedItemTrigger ENCHANTED_ITEM = CriteriaTriggers.register(new EnchantedItemTrigger());
    public static final FilledBucketTrigger FILLED_BUCKET = CriteriaTriggers.register(new FilledBucketTrigger());
    public static final BrewedPotionTrigger BREWED_POTION = CriteriaTriggers.register(new BrewedPotionTrigger());
    public static final ConstructBeaconTrigger CONSTRUCT_BEACON = CriteriaTriggers.register(new ConstructBeaconTrigger());
    public static final UsedEnderEyeTrigger USED_ENDER_EYE = CriteriaTriggers.register(new UsedEnderEyeTrigger());
    public static final SummonedEntityTrigger SUMMONED_ENTITY = CriteriaTriggers.register(new SummonedEntityTrigger());
    public static final BredAnimalsTrigger BRED_ANIMALS = CriteriaTriggers.register(new BredAnimalsTrigger());
    public static final PositionTrigger LOCATION = CriteriaTriggers.register(new PositionTrigger(new ResourceLocation("location")));
    public static final PositionTrigger SLEPT_IN_BED = CriteriaTriggers.register(new PositionTrigger(new ResourceLocation("slept_in_bed")));
    public static final CuredZombieVillagerTrigger CURED_ZOMBIE_VILLAGER = CriteriaTriggers.register(new CuredZombieVillagerTrigger());
    public static final VillagerTradeTrigger VILLAGER_TRADE = CriteriaTriggers.register(new VillagerTradeTrigger());
    public static final ItemDurabilityTrigger ITEM_DURABILITY_CHANGED = CriteriaTriggers.register(new ItemDurabilityTrigger());
    public static final LevitationTrigger LEVITATION = CriteriaTriggers.register(new LevitationTrigger());
    public static final ChangeDimensionTrigger CHANGED_DIMENSION = CriteriaTriggers.register(new ChangeDimensionTrigger());
    public static final TickTrigger TICK = CriteriaTriggers.register(new TickTrigger());
    public static final TameAnimalTrigger TAME_ANIMAL = CriteriaTriggers.register(new TameAnimalTrigger());
    public static final PlacedBlockTrigger PLACED_BLOCK = CriteriaTriggers.register(new PlacedBlockTrigger());
    public static final ConsumeItemTrigger CONSUME_ITEM = CriteriaTriggers.register(new ConsumeItemTrigger());
    public static final EffectsChangedTrigger EFFECTS_CHANGED = CriteriaTriggers.register(new EffectsChangedTrigger());
    public static final UsedTotemTrigger USED_TOTEM = CriteriaTriggers.register(new UsedTotemTrigger());
    public static final NetherTravelTrigger NETHER_TRAVEL = CriteriaTriggers.register(new NetherTravelTrigger());
    public static final FishingRodHookedTrigger FISHING_ROD_HOOKED = CriteriaTriggers.register(new FishingRodHookedTrigger());
    public static final ChanneledLightningTrigger CHANNELED_LIGHTNING = CriteriaTriggers.register(new ChanneledLightningTrigger());
    public static final ShotCrossbowTrigger SHOT_CROSSBOW = CriteriaTriggers.register(new ShotCrossbowTrigger());
    public static final KilledByCrossbowTrigger KILLED_BY_CROSSBOW = CriteriaTriggers.register(new KilledByCrossbowTrigger());
    public static final PositionTrigger HERO_OF_THE_VILLAGE = CriteriaTriggers.register(new PositionTrigger(new ResourceLocation("hero_of_the_village")));
    public static final PositionTrigger VOLUNTARY_EXILE = CriteriaTriggers.register(new PositionTrigger(new ResourceLocation("voluntary_exile")));
    public static final SlideDownBlockTrigger SLIDE_DOWN_BLOCK = CriteriaTriggers.register(new SlideDownBlockTrigger());
    public static final BeeNestDestroyedTrigger BEE_NEST_DESTROYED = CriteriaTriggers.register(new BeeNestDestroyedTrigger());
    public static final TargetHitTrigger TARGET_HIT = CriteriaTriggers.register(new TargetHitTrigger());
    public static final RightClickBlockWithItemTrigger RIGHT_CLICK_BLOCK_WITH_ITEM = CriteriaTriggers.register(new RightClickBlockWithItemTrigger());
    public static final PlayerGeneratesContainerLootTrigger PLAYER_GENERATES_CONTAINER_LOOT = CriteriaTriggers.register(new PlayerGeneratesContainerLootTrigger());
    public static final ThrownItemPickedUpByEntityTrigger THROWN_ITEM_PICKED_UP_BY_ENTITY = CriteriaTriggers.register(new ThrownItemPickedUpByEntityTrigger());
    public static final PlayerEntityInteractionTrigger PLAYER_ENTITY_INTERACTION = CriteriaTriggers.register(new PlayerEntityInteractionTrigger());

    private static <T extends ICriterionTrigger<?>> T register(T t) {
        if (REGISTRY.containsKey(t.getId())) {
            throw new IllegalArgumentException("Duplicate criterion id " + t.getId());
        }
        REGISTRY.put(t.getId(), t);
        return t;
    }

    @Nullable
    public static <T extends ICriterionInstance> ICriterionTrigger<T> get(ResourceLocation resourceLocation) {
        return REGISTRY.get(resourceLocation);
    }

    public static Iterable<? extends ICriterionTrigger<?>> getAll() {
        return REGISTRY.values();
    }
}

