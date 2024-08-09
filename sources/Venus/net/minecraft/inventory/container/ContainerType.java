/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.inventory.container;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.BeaconContainer;
import net.minecraft.inventory.container.BlastFurnaceContainer;
import net.minecraft.inventory.container.BrewingStandContainer;
import net.minecraft.inventory.container.CartographyContainer;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.DispenserContainer;
import net.minecraft.inventory.container.EnchantmentContainer;
import net.minecraft.inventory.container.FurnaceContainer;
import net.minecraft.inventory.container.GrindstoneContainer;
import net.minecraft.inventory.container.HopperContainer;
import net.minecraft.inventory.container.LecternContainer;
import net.minecraft.inventory.container.LoomContainer;
import net.minecraft.inventory.container.MerchantContainer;
import net.minecraft.inventory.container.RepairContainer;
import net.minecraft.inventory.container.ShulkerBoxContainer;
import net.minecraft.inventory.container.SmithingTableContainer;
import net.minecraft.inventory.container.SmokerContainer;
import net.minecraft.inventory.container.StonecutterContainer;
import net.minecraft.inventory.container.WorkbenchContainer;
import net.minecraft.util.registry.Registry;

public class ContainerType<T extends Container> {
    public static final ContainerType<ChestContainer> GENERIC_9X1 = ContainerType.register("generic_9x1", ChestContainer::createGeneric9X1);
    public static final ContainerType<ChestContainer> GENERIC_9X2 = ContainerType.register("generic_9x2", ChestContainer::createGeneric9X2);
    public static final ContainerType<ChestContainer> GENERIC_9X3 = ContainerType.register("generic_9x3", ChestContainer::createGeneric9X3);
    public static final ContainerType<ChestContainer> GENERIC_9X4 = ContainerType.register("generic_9x4", ChestContainer::createGeneric9X4);
    public static final ContainerType<ChestContainer> GENERIC_9X5 = ContainerType.register("generic_9x5", ChestContainer::createGeneric9X5);
    public static final ContainerType<ChestContainer> GENERIC_9X6 = ContainerType.register("generic_9x6", ChestContainer::createGeneric9X6);
    public static final ContainerType<DispenserContainer> GENERIC_3X3 = ContainerType.register("generic_3x3", DispenserContainer::new);
    public static final ContainerType<RepairContainer> ANVIL = ContainerType.register("anvil", RepairContainer::new);
    public static final ContainerType<BeaconContainer> BEACON = ContainerType.register("beacon", BeaconContainer::new);
    public static final ContainerType<BlastFurnaceContainer> BLAST_FURNACE = ContainerType.register("blast_furnace", BlastFurnaceContainer::new);
    public static final ContainerType<BrewingStandContainer> BREWING_STAND = ContainerType.register("brewing_stand", BrewingStandContainer::new);
    public static final ContainerType<WorkbenchContainer> CRAFTING = ContainerType.register("crafting", WorkbenchContainer::new);
    public static final ContainerType<EnchantmentContainer> ENCHANTMENT = ContainerType.register("enchantment", EnchantmentContainer::new);
    public static final ContainerType<FurnaceContainer> FURNACE = ContainerType.register("furnace", FurnaceContainer::new);
    public static final ContainerType<GrindstoneContainer> GRINDSTONE = ContainerType.register("grindstone", GrindstoneContainer::new);
    public static final ContainerType<HopperContainer> HOPPER = ContainerType.register("hopper", HopperContainer::new);
    public static final ContainerType<LecternContainer> LECTERN = ContainerType.register("lectern", ContainerType::lambda$static$0);
    public static final ContainerType<LoomContainer> LOOM = ContainerType.register("loom", LoomContainer::new);
    public static final ContainerType<MerchantContainer> MERCHANT = ContainerType.register("merchant", MerchantContainer::new);
    public static final ContainerType<ShulkerBoxContainer> SHULKER_BOX = ContainerType.register("shulker_box", ShulkerBoxContainer::new);
    public static final ContainerType<SmithingTableContainer> SMITHING = ContainerType.register("smithing", SmithingTableContainer::new);
    public static final ContainerType<SmokerContainer> SMOKER = ContainerType.register("smoker", SmokerContainer::new);
    public static final ContainerType<CartographyContainer> CARTOGRAPHY_TABLE = ContainerType.register("cartography_table", CartographyContainer::new);
    public static final ContainerType<StonecutterContainer> STONECUTTER = ContainerType.register("stonecutter", StonecutterContainer::new);
    private final IFactory<T> factory;

    private static <T extends Container> ContainerType<T> register(String string, IFactory<T> iFactory) {
        return Registry.register(Registry.MENU, string, new ContainerType<T>(iFactory));
    }

    private ContainerType(IFactory<T> iFactory) {
        this.factory = iFactory;
    }

    public T create(int n, PlayerInventory playerInventory) {
        return this.factory.create(n, playerInventory);
    }

    private static LecternContainer lambda$static$0(int n, PlayerInventory playerInventory) {
        return new LecternContainer(n);
    }

    static interface IFactory<T extends Container> {
        public T create(int var1, PlayerInventory var2);
    }
}

