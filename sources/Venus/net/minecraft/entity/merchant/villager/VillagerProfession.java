/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.merchant.villager;

import com.google.common.collect.ImmutableSet;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.registry.Registry;
import net.minecraft.village.PointOfInterestType;

public class VillagerProfession {
    public static final VillagerProfession NONE = VillagerProfession.register("none", PointOfInterestType.UNEMPLOYED, null);
    public static final VillagerProfession ARMORER = VillagerProfession.register("armorer", PointOfInterestType.ARMORER, SoundEvents.ENTITY_VILLAGER_WORK_ARMORER);
    public static final VillagerProfession BUTCHER = VillagerProfession.register("butcher", PointOfInterestType.BUTCHER, SoundEvents.ENTITY_VILLAGER_WORK_BUTCHER);
    public static final VillagerProfession CARTOGRAPHER = VillagerProfession.register("cartographer", PointOfInterestType.CARTOGRAPHER, SoundEvents.ENTITY_VILLAGER_WORK_CARTOGRAPHER);
    public static final VillagerProfession CLERIC = VillagerProfession.register("cleric", PointOfInterestType.CLERIC, SoundEvents.ENTITY_VILLAGER_WORK_CLERIC);
    public static final VillagerProfession FARMER = VillagerProfession.register("farmer", PointOfInterestType.FARMER, ImmutableSet.of(Items.WHEAT, Items.WHEAT_SEEDS, Items.BEETROOT_SEEDS, Items.BONE_MEAL), ImmutableSet.of(Blocks.FARMLAND), SoundEvents.ENTITY_VILLAGER_WORK_FARMER);
    public static final VillagerProfession FISHERMAN = VillagerProfession.register("fisherman", PointOfInterestType.FISHERMAN, SoundEvents.ENTITY_VILLAGER_WORK_FISHERMAN);
    public static final VillagerProfession FLETCHER = VillagerProfession.register("fletcher", PointOfInterestType.FLETCHER, SoundEvents.ENTITY_VILLAGER_WORK_FLETCHER);
    public static final VillagerProfession LEATHERWORKER = VillagerProfession.register("leatherworker", PointOfInterestType.LEATHERWORKER, SoundEvents.ENTITY_VILLAGER_WORK_LEATHERWORKER);
    public static final VillagerProfession LIBRARIAN = VillagerProfession.register("librarian", PointOfInterestType.LIBRARIAN, SoundEvents.ENTITY_VILLAGER_WORK_LIBRARIAN);
    public static final VillagerProfession MASON = VillagerProfession.register("mason", PointOfInterestType.MASON, SoundEvents.ENTITY_VILLAGER_WORK_MASON);
    public static final VillagerProfession NITWIT = VillagerProfession.register("nitwit", PointOfInterestType.NITWIT, null);
    public static final VillagerProfession SHEPHERD = VillagerProfession.register("shepherd", PointOfInterestType.SHEPHERD, SoundEvents.ENTITY_VILLAGER_WORK_SHEPHERD);
    public static final VillagerProfession TOOLSMITH = VillagerProfession.register("toolsmith", PointOfInterestType.TOOLSMITH, SoundEvents.ENTITY_VILLAGER_WORK_TOOLSMITH);
    public static final VillagerProfession WEAPONSMITH = VillagerProfession.register("weaponsmith", PointOfInterestType.WEAPONSMITH, SoundEvents.ENTITY_VILLAGER_WORK_WEAPONSMITH);
    private final String name;
    private final PointOfInterestType pointOfInterest;
    private final ImmutableSet<Item> specificItems;
    private final ImmutableSet<Block> relatedWorldBlocks;
    @Nullable
    private final SoundEvent sound;

    private VillagerProfession(String string, PointOfInterestType pointOfInterestType, ImmutableSet<Item> immutableSet, ImmutableSet<Block> immutableSet2, @Nullable SoundEvent soundEvent) {
        this.name = string;
        this.pointOfInterest = pointOfInterestType;
        this.specificItems = immutableSet;
        this.relatedWorldBlocks = immutableSet2;
        this.sound = soundEvent;
    }

    public PointOfInterestType getPointOfInterest() {
        return this.pointOfInterest;
    }

    public ImmutableSet<Item> getSpecificItems() {
        return this.specificItems;
    }

    public ImmutableSet<Block> getRelatedWorldBlocks() {
        return this.relatedWorldBlocks;
    }

    @Nullable
    public SoundEvent getSound() {
        return this.sound;
    }

    public String toString() {
        return this.name;
    }

    static VillagerProfession register(String string, PointOfInterestType pointOfInterestType, @Nullable SoundEvent soundEvent) {
        return VillagerProfession.register(string, pointOfInterestType, ImmutableSet.of(), ImmutableSet.of(), soundEvent);
    }

    static VillagerProfession register(String string, PointOfInterestType pointOfInterestType, ImmutableSet<Item> immutableSet, ImmutableSet<Block> immutableSet2, @Nullable SoundEvent soundEvent) {
        return Registry.register(Registry.VILLAGER_PROFESSION, new ResourceLocation(string), new VillagerProfession(string, pointOfInterestType, immutableSet, immutableSet2, soundEvent));
    }
}

