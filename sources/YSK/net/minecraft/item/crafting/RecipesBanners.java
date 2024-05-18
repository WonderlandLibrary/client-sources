package net.minecraft.item.crafting;

import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.inventory.*;
import net.minecraft.world.*;
import net.minecraft.tileentity.*;
import net.minecraft.nbt.*;

public class RecipesBanners
{
    private static final String[] I;
    
    void addRecipes(final CraftingManager craftingManager) {
        final EnumDyeColor[] values;
        final int length = (values = EnumDyeColor.values()).length;
        int i = "".length();
        "".length();
        if (false) {
            throw null;
        }
        while (i < length) {
            final EnumDyeColor enumDyeColor = values[i];
            final ItemStack itemStack = new ItemStack(Items.banner, " ".length(), enumDyeColor.getDyeDamage());
            final Object[] array = new Object[0x7A ^ 0x7D];
            array["".length()] = RecipesBanners.I["".length()];
            array[" ".length()] = RecipesBanners.I[" ".length()];
            array["  ".length()] = RecipesBanners.I["  ".length()];
            array["   ".length()] = (char)(0xB8 ^ 0x9B);
            array[0x21 ^ 0x25] = new ItemStack(Blocks.wool, " ".length(), enumDyeColor.getMetadata());
            array[0xA4 ^ 0xA1] = (char)(0x7F ^ 0x3);
            array[0xD ^ 0xB] = Items.stick;
            craftingManager.addRecipe(itemStack, array);
            ++i;
        }
        craftingManager.addRecipe(new RecipeDuplicatePattern(null));
        craftingManager.addRecipe(new RecipeAddPattern(null));
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (0 == -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private static void I() {
        (I = new String["   ".length()])["".length()] = I("dHL", "Gkoaf");
        RecipesBanners.I[" ".length()] = I("lee", "OFFsH");
        RecipesBanners.I["  ".length()] = I("P6S", "pJsOc");
    }
    
    static {
        I();
    }
    
    static class RecipeAddPattern implements IRecipe
    {
        private static final String[] I;
        
        private RecipeAddPattern() {
        }
        
        private static void I() {
            (I = new String[0x8E ^ 0x88])["".length()] = I("\u0001\u000f:\u0007\u0002\u0006\r!\r\u001d:74\u0003", "CcUdi");
            RecipeAddPattern.I[" ".length()] = I("*/\u0003\u0006$\b \u0004", "zNwrA");
            RecipeAddPattern.I["  ".length()] = I("<&\u0000\r\u0007\u001e)\u0007", "lGtyb");
            RecipeAddPattern.I["   ".length()] = I("\u0004\u00001$,&\u000f6", "TaEPI");
            RecipeAddPattern.I[0x4 ^ 0x0] = I("\"\u000e5!\b\u0000\u0001", "roAUm");
            RecipeAddPattern.I[0xAD ^ 0xA8] = I("\u0014<\u001e\u0017\u0013", "WSrxa");
        }
        
        @Override
        public ItemStack[] getRemainingItems(final InventoryCrafting inventoryCrafting) {
            final ItemStack[] array = new ItemStack[inventoryCrafting.getSizeInventory()];
            int i = "".length();
            "".length();
            if (4 < 4) {
                throw null;
            }
            while (i < array.length) {
                final ItemStack stackInSlot = inventoryCrafting.getStackInSlot(i);
                if (stackInSlot != null && stackInSlot.getItem().hasContainerItem()) {
                    array[i] = new ItemStack(stackInSlot.getItem().getContainerItem());
                }
                ++i;
            }
            return array;
        }
        
        @Override
        public ItemStack getRecipeOutput() {
            return null;
        }
        
        RecipeAddPattern(final RecipeAddPattern recipeAddPattern) {
            this();
        }
        
        @Override
        public boolean matches(final InventoryCrafting inventoryCrafting, final World world) {
            int n = "".length();
            int i = "".length();
            "".length();
            if (2 < -1) {
                throw null;
            }
            while (i < inventoryCrafting.getSizeInventory()) {
                final ItemStack stackInSlot = inventoryCrafting.getStackInSlot(i);
                if (stackInSlot != null && stackInSlot.getItem() == Items.banner) {
                    if (n != 0) {
                        return "".length() != 0;
                    }
                    if (TileEntityBanner.getPatterns(stackInSlot) >= (0x9A ^ 0x9C)) {
                        return "".length() != 0;
                    }
                    n = " ".length();
                }
                ++i;
            }
            if (n == 0) {
                return "".length() != 0;
            }
            if (this.func_179533_c(inventoryCrafting) != null) {
                return " ".length() != 0;
            }
            return "".length() != 0;
        }
        
        @Override
        public int getRecipeSize() {
            return 0x96 ^ 0x9C;
        }
        
        static {
            I();
        }
        
        private static String I(final String s, final String s2) {
            final StringBuilder sb = new StringBuilder();
            final char[] charArray = s2.toCharArray();
            int length = "".length();
            final char[] charArray2 = s.toCharArray();
            final int length2 = charArray2.length;
            int i = "".length();
            while (i < length2) {
                sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                ++length;
                ++i;
                "".length();
                if (-1 >= 3) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        @Override
        public ItemStack getCraftingResult(final InventoryCrafting inventoryCrafting) {
            ItemStack copy = null;
            int i = "".length();
            "".length();
            if (2 == 3) {
                throw null;
            }
            while (i < inventoryCrafting.getSizeInventory()) {
                final ItemStack stackInSlot = inventoryCrafting.getStackInSlot(i);
                if (stackInSlot != null && stackInSlot.getItem() == Items.banner) {
                    copy = stackInSlot.copy();
                    copy.stackSize = " ".length();
                    "".length();
                    if (3 != 3) {
                        throw null;
                    }
                    break;
                }
                else {
                    ++i;
                }
            }
            final TileEntityBanner.EnumBannerPattern func_179533_c = this.func_179533_c(inventoryCrafting);
            if (func_179533_c != null) {
                int n = "".length();
                int j = "".length();
                "".length();
                if (1 < 0) {
                    throw null;
                }
                while (j < inventoryCrafting.getSizeInventory()) {
                    final ItemStack stackInSlot2 = inventoryCrafting.getStackInSlot(j);
                    if (stackInSlot2 != null && stackInSlot2.getItem() == Items.dye) {
                        n = stackInSlot2.getMetadata();
                        "".length();
                        if (3 < 3) {
                            throw null;
                        }
                        break;
                    }
                    else {
                        ++j;
                    }
                }
                final NBTTagCompound subCompound = copy.getSubCompound(RecipeAddPattern.I["".length()], " ".length() != 0);
                NBTTagList tagList;
                if (subCompound.hasKey(RecipeAddPattern.I[" ".length()], 0x7B ^ 0x72)) {
                    tagList = subCompound.getTagList(RecipeAddPattern.I["  ".length()], 0xB2 ^ 0xB8);
                    "".length();
                    if (-1 == 0) {
                        throw null;
                    }
                }
                else {
                    tagList = new NBTTagList();
                    subCompound.setTag(RecipeAddPattern.I["   ".length()], tagList);
                }
                final NBTTagCompound nbtTagCompound = new NBTTagCompound();
                nbtTagCompound.setString(RecipeAddPattern.I[0xA7 ^ 0xA3], func_179533_c.getPatternID());
                nbtTagCompound.setInteger(RecipeAddPattern.I[0x87 ^ 0x82], n);
                tagList.appendTag(nbtTagCompound);
            }
            return copy;
        }
        
        private TileEntityBanner.EnumBannerPattern func_179533_c(final InventoryCrafting inventoryCrafting) {
            final TileEntityBanner.EnumBannerPattern[] values;
            final int length = (values = TileEntityBanner.EnumBannerPattern.values()).length;
            int i = "".length();
            "".length();
            if (3 == -1) {
                throw null;
            }
            while (i < length) {
                final TileEntityBanner.EnumBannerPattern enumBannerPattern = values[i];
                if (enumBannerPattern.hasValidCrafting()) {
                    int n = " ".length();
                    if (enumBannerPattern.hasCraftingStack()) {
                        int n2 = "".length();
                        int n3 = "".length();
                        int length2 = "".length();
                        "".length();
                        if (4 < 4) {
                            throw null;
                        }
                        while (length2 < inventoryCrafting.getSizeInventory() && n != 0) {
                            final ItemStack stackInSlot = inventoryCrafting.getStackInSlot(length2);
                            if (stackInSlot != null && stackInSlot.getItem() != Items.banner) {
                                if (stackInSlot.getItem() == Items.dye) {
                                    if (n3 != 0) {
                                        n = "".length();
                                        "".length();
                                        if (3 <= 1) {
                                            throw null;
                                        }
                                        break;
                                    }
                                    else {
                                        n3 = " ".length();
                                        "".length();
                                        if (1 >= 2) {
                                            throw null;
                                        }
                                    }
                                }
                                else if (n2 != 0 || !stackInSlot.isItemEqual(enumBannerPattern.getCraftingStack())) {
                                    n = "".length();
                                    "".length();
                                    if (4 < 2) {
                                        throw null;
                                    }
                                    break;
                                }
                                else {
                                    n2 = " ".length();
                                }
                            }
                            ++length2;
                        }
                        if (n2 == 0) {
                            n = "".length();
                            "".length();
                            if (4 < 4) {
                                throw null;
                            }
                        }
                    }
                    else if (inventoryCrafting.getSizeInventory() == enumBannerPattern.getCraftingLayers().length * enumBannerPattern.getCraftingLayers()["".length()].length()) {
                        int metadata = -" ".length();
                        int j = "".length();
                        "".length();
                        if (-1 != -1) {
                            throw null;
                        }
                        while (j < inventoryCrafting.getSizeInventory()) {
                            if (n == 0) {
                                "".length();
                                if (1 <= 0) {
                                    throw null;
                                }
                                break;
                            }
                            else {
                                final int n4 = j / "   ".length();
                                final int n5 = j % "   ".length();
                                final ItemStack stackInSlot2 = inventoryCrafting.getStackInSlot(j);
                                if (stackInSlot2 != null && stackInSlot2.getItem() != Items.banner) {
                                    if (stackInSlot2.getItem() != Items.dye) {
                                        n = "".length();
                                        "".length();
                                        if (4 < 0) {
                                            throw null;
                                        }
                                        break;
                                    }
                                    else if (metadata != -" ".length() && metadata != stackInSlot2.getMetadata()) {
                                        n = "".length();
                                        "".length();
                                        if (4 <= 0) {
                                            throw null;
                                        }
                                        break;
                                    }
                                    else if (enumBannerPattern.getCraftingLayers()[n4].charAt(n5) == (0xE0 ^ 0xC0)) {
                                        n = "".length();
                                        "".length();
                                        if (-1 < -1) {
                                            throw null;
                                        }
                                        break;
                                    }
                                    else {
                                        metadata = stackInSlot2.getMetadata();
                                        "".length();
                                        if (2 == 1) {
                                            throw null;
                                        }
                                    }
                                }
                                else if (enumBannerPattern.getCraftingLayers()[n4].charAt(n5) != (0x4D ^ 0x6D)) {
                                    n = "".length();
                                    "".length();
                                    if (0 >= 4) {
                                        throw null;
                                    }
                                    break;
                                }
                                ++j;
                            }
                        }
                    }
                    else {
                        n = "".length();
                    }
                    if (n != 0) {
                        return enumBannerPattern;
                    }
                }
                ++i;
            }
            return null;
        }
    }
    
    static class RecipeDuplicatePattern implements IRecipe
    {
        private static String I(final String s, final String s2) {
            final StringBuilder sb = new StringBuilder();
            final char[] charArray = s2.toCharArray();
            int length = "".length();
            final char[] charArray2 = s.toCharArray();
            final int length2 = charArray2.length;
            int i = "".length();
            while (i < length2) {
                sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                ++length;
                ++i;
                "".length();
                if (2 != 2) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        @Override
        public ItemStack getRecipeOutput() {
            return null;
        }
        
        RecipeDuplicatePattern(final RecipeDuplicatePattern recipeDuplicatePattern) {
            this();
        }
        
        @Override
        public int getRecipeSize() {
            return "  ".length();
        }
        
        @Override
        public ItemStack getCraftingResult(final InventoryCrafting inventoryCrafting) {
            int i = "".length();
            "".length();
            if (2 == -1) {
                throw null;
            }
            while (i < inventoryCrafting.getSizeInventory()) {
                final ItemStack stackInSlot = inventoryCrafting.getStackInSlot(i);
                if (stackInSlot != null && TileEntityBanner.getPatterns(stackInSlot) > 0) {
                    final ItemStack copy = stackInSlot.copy();
                    copy.stackSize = " ".length();
                    return copy;
                }
                ++i;
            }
            return null;
        }
        
        private RecipeDuplicatePattern() {
        }
        
        @Override
        public boolean matches(final InventoryCrafting inventoryCrafting, final World world) {
            ItemStack itemStack = null;
            ItemStack itemStack2 = null;
            int i = "".length();
            "".length();
            if (1 <= -1) {
                throw null;
            }
            while (i < inventoryCrafting.getSizeInventory()) {
                final ItemStack stackInSlot = inventoryCrafting.getStackInSlot(i);
                if (stackInSlot != null) {
                    if (stackInSlot.getItem() != Items.banner) {
                        return "".length() != 0;
                    }
                    if (itemStack != null && itemStack2 != null) {
                        return "".length() != 0;
                    }
                    final int baseColor = TileEntityBanner.getBaseColor(stackInSlot);
                    int n;
                    if (TileEntityBanner.getPatterns(stackInSlot) > 0) {
                        n = " ".length();
                        "".length();
                        if (-1 >= 1) {
                            throw null;
                        }
                    }
                    else {
                        n = "".length();
                    }
                    final int n2 = n;
                    if (itemStack != null) {
                        if (n2 != 0) {
                            return "".length() != 0;
                        }
                        if (baseColor != TileEntityBanner.getBaseColor(itemStack)) {
                            return "".length() != 0;
                        }
                        itemStack2 = stackInSlot;
                        "".length();
                        if (4 == 1) {
                            throw null;
                        }
                    }
                    else if (itemStack2 != null) {
                        if (n2 == 0) {
                            return "".length() != 0;
                        }
                        if (baseColor != TileEntityBanner.getBaseColor(itemStack2)) {
                            return "".length() != 0;
                        }
                        itemStack = stackInSlot;
                        "".length();
                        if (4 < 0) {
                            throw null;
                        }
                    }
                    else if (n2 != 0) {
                        itemStack = stackInSlot;
                        "".length();
                        if (1 >= 4) {
                            throw null;
                        }
                    }
                    else {
                        itemStack2 = stackInSlot;
                    }
                }
                ++i;
            }
            if (itemStack != null && itemStack2 != null) {
                return " ".length() != 0;
            }
            return "".length() != 0;
        }
        
        @Override
        public ItemStack[] getRemainingItems(final InventoryCrafting inventoryCrafting) {
            final ItemStack[] array = new ItemStack[inventoryCrafting.getSizeInventory()];
            int i = "".length();
            "".length();
            if (2 <= -1) {
                throw null;
            }
            while (i < array.length) {
                final ItemStack stackInSlot = inventoryCrafting.getStackInSlot(i);
                if (stackInSlot != null) {
                    if (stackInSlot.getItem().hasContainerItem()) {
                        array[i] = new ItemStack(stackInSlot.getItem().getContainerItem());
                        "".length();
                        if (4 < 2) {
                            throw null;
                        }
                    }
                    else if (stackInSlot.hasTagCompound() && TileEntityBanner.getPatterns(stackInSlot) > 0) {
                        array[i] = stackInSlot.copy();
                        array[i].stackSize = " ".length();
                    }
                }
                ++i;
            }
            return array;
        }
    }
}
