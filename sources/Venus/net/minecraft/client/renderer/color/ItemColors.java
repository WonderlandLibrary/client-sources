/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.color;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ObjectIntIdentityMap;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.GrassColors;

public class ItemColors {
    private final ObjectIntIdentityMap<IItemColor> colors = new ObjectIntIdentityMap(32);

    public static ItemColors init(BlockColors blockColors) {
        ItemColors itemColors = new ItemColors();
        itemColors.register(ItemColors::lambda$init$0, Items.LEATHER_HELMET, Items.LEATHER_CHESTPLATE, Items.LEATHER_LEGGINGS, Items.LEATHER_BOOTS, Items.LEATHER_HORSE_ARMOR);
        itemColors.register(ItemColors::lambda$init$1, Blocks.TALL_GRASS, Blocks.LARGE_FERN);
        itemColors.register(ItemColors::lambda$init$2, Items.FIREWORK_STAR);
        itemColors.register(ItemColors::lambda$init$3, Items.POTION, Items.SPLASH_POTION, Items.LINGERING_POTION);
        for (SpawnEggItem spawnEggItem : SpawnEggItem.getEggs()) {
            itemColors.register((arg_0, arg_1) -> ItemColors.lambda$init$4(spawnEggItem, arg_0, arg_1), spawnEggItem);
        }
        itemColors.register((arg_0, arg_1) -> ItemColors.lambda$init$5(blockColors, arg_0, arg_1), Blocks.GRASS_BLOCK, Blocks.GRASS, Blocks.FERN, Blocks.VINE, Blocks.OAK_LEAVES, Blocks.SPRUCE_LEAVES, Blocks.BIRCH_LEAVES, Blocks.JUNGLE_LEAVES, Blocks.ACACIA_LEAVES, Blocks.DARK_OAK_LEAVES, Blocks.LILY_PAD);
        itemColors.register(ItemColors::lambda$init$6, Items.TIPPED_ARROW);
        itemColors.register(ItemColors::lambda$init$7, Items.FILLED_MAP);
        return itemColors;
    }

    public int getColor(ItemStack itemStack, int n) {
        IItemColor iItemColor = this.colors.getByValue(Registry.ITEM.getId(itemStack.getItem()));
        return iItemColor == null ? -1 : iItemColor.getColor(itemStack, n);
    }

    public void register(IItemColor iItemColor, IItemProvider ... iItemProviderArray) {
        for (IItemProvider iItemProvider : iItemProviderArray) {
            this.colors.put(iItemColor, Item.getIdFromItem(iItemProvider.asItem()));
        }
    }

    private static int lambda$init$7(ItemStack itemStack, int n) {
        return n == 0 ? -1 : FilledMapItem.getColor(itemStack);
    }

    private static int lambda$init$6(ItemStack itemStack, int n) {
        return n == 0 ? PotionUtils.getColor(itemStack) : -1;
    }

    private static int lambda$init$5(BlockColors blockColors, ItemStack itemStack, int n) {
        BlockState blockState = ((BlockItem)itemStack.getItem()).getBlock().getDefaultState();
        return blockColors.getColor(blockState, null, null, n);
    }

    private static int lambda$init$4(SpawnEggItem spawnEggItem, ItemStack itemStack, int n) {
        return spawnEggItem.getColor(n);
    }

    private static int lambda$init$3(ItemStack itemStack, int n) {
        return n > 0 ? -1 : PotionUtils.getColor(itemStack);
    }

    private static int lambda$init$2(ItemStack itemStack, int n) {
        int[] nArray;
        if (n != 1) {
            return 1;
        }
        CompoundNBT compoundNBT = itemStack.getChildTag("Explosion");
        int[] nArray2 = nArray = compoundNBT != null && compoundNBT.contains("Colors", 0) ? compoundNBT.getIntArray("Colors") : null;
        if (nArray != null && nArray.length != 0) {
            if (nArray.length == 1) {
                return nArray[0];
            }
            int n2 = 0;
            int n3 = 0;
            int n4 = 0;
            for (int n5 : nArray) {
                n2 += (n5 & 0xFF0000) >> 16;
                n3 += (n5 & 0xFF00) >> 8;
                n4 += (n5 & 0xFF) >> 0;
            }
            return (n2 /= nArray.length) << 16 | (n3 /= nArray.length) << 8 | (n4 /= nArray.length);
        }
        return 1;
    }

    private static int lambda$init$1(ItemStack itemStack, int n) {
        return GrassColors.get(0.5, 1.0);
    }

    private static int lambda$init$0(ItemStack itemStack, int n) {
        return n > 0 ? -1 : ((IDyeableArmorItem)((Object)itemStack.getItem())).getColor(itemStack);
    }
}

