package zamorozka.ui;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;

public class ZItem {
    public static boolean isNullOrEmpty(Item item) {
        return (item == null) || ((item instanceof ItemAir));
    }

    public static boolean isNullOrEmpty(ItemStack stack) {
        return (stack == null) || (stack.func_190926_b());
    }

    public static int getArmorType(ItemArmor armor) {
        return armor.armorType.ordinal() - 2;
    }

    public static float getArmorToughness(ItemArmor armor) {
        return armor.toughness;
    }

    public static boolean isThrowable(ItemStack stack) {
        Item item = stack.getItem();
        return ((item instanceof ItemBow)) || ((item instanceof ItemSnowball)) ||
                ((item instanceof ItemEgg)) || ((item instanceof ItemEnderPearl)) ||
                ((item instanceof ItemSplashPotion)) ||
                ((item instanceof ItemLingeringPotion)) ||
                ((item instanceof ItemFishingRod));
    }

    public static boolean isPotion(ItemStack stack) {
        return ((stack != null) && ((stack.getItem() instanceof ItemPotion))) ||
                ((stack.getItem() instanceof ItemSplashPotion));
    }

    public static Item getFromRegistry(ResourceLocation location) {
        return Item.REGISTRY.getObject(location);
    }

    public static int getStackSize(ItemStack stack) {
        return stack.func_190916_E();
    }

    public static float getDestroySpeed(ItemStack stack, IBlockState state) {
        return isNullOrEmpty(stack) ? 1.0F : stack.getStrVsBlock(state);
    }
}
