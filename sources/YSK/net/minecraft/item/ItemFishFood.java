package net.minecraft.item;

import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.potion.*;
import net.minecraft.creativetab.*;
import java.util.*;
import com.google.common.collect.*;

public class ItemFishFood extends ItemFood
{
    private final boolean cooked;
    private static final String[] I;
    
    public ItemFishFood(final boolean cooked) {
        super("".length(), 0.0f, "".length() != 0);
        this.cooked = cooked;
    }
    
    @Override
    public String getPotionEffect(final ItemStack itemStack) {
        String s;
        if (FishType.byItemStack(itemStack) == FishType.PUFFERFISH) {
            s = ItemFishFood.I["".length()];
            "".length();
            if (2 == 3) {
                throw null;
            }
        }
        else {
            s = null;
        }
        return s;
    }
    
    @Override
    protected void onFoodEaten(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        if (FishType.byItemStack(itemStack) == FishType.PUFFERFISH) {
            entityPlayer.addPotionEffect(new PotionEffect(Potion.poison.id, 1170 + 743 - 1811 + 1098, "   ".length()));
            entityPlayer.addPotionEffect(new PotionEffect(Potion.hunger.id, 170 + 30 + 42 + 58, "  ".length()));
            entityPlayer.addPotionEffect(new PotionEffect(Potion.confusion.id, 35 + 92 + 113 + 60, " ".length()));
        }
        super.onFoodEaten(itemStack, world, entityPlayer);
    }
    
    @Override
    public String getUnlocalizedName(final ItemStack itemStack) {
        final FishType byItemStack = FishType.byItemStack(itemStack);
        final StringBuilder append = new StringBuilder(String.valueOf(this.getUnlocalizedName())).append(ItemFishFood.I[" ".length()]).append(byItemStack.getUnlocalizedName()).append(ItemFishFood.I["  ".length()]);
        String s;
        if (this.cooked && byItemStack.canCook()) {
            s = ItemFishFood.I["   ".length()];
            "".length();
            if (-1 == 0) {
                throw null;
            }
        }
        else {
            s = ItemFishFood.I[0x2A ^ 0x2E];
        }
        return append.append(s).toString();
    }
    
    @Override
    public float getSaturationModifier(final ItemStack itemStack) {
        final FishType byItemStack = FishType.byItemStack(itemStack);
        float n;
        if (this.cooked && byItemStack.canCook()) {
            n = byItemStack.getCookedSaturationModifier();
            "".length();
            if (0 >= 2) {
                throw null;
            }
        }
        else {
            n = byItemStack.getUncookedSaturationModifier();
        }
        return n;
    }
    
    @Override
    public int getHealAmount(final ItemStack itemStack) {
        final FishType byItemStack = FishType.byItemStack(itemStack);
        int n;
        if (this.cooked && byItemStack.canCook()) {
            n = byItemStack.getCookedHealAmount();
            "".length();
            if (3 <= 1) {
                throw null;
            }
        }
        else {
            n = byItemStack.getUncookedHealAmount();
        }
        return n;
    }
    
    @Override
    public void getSubItems(final Item item, final CreativeTabs creativeTabs, final List<ItemStack> list) {
        final FishType[] values;
        final int length = (values = FishType.values()).length;
        int i = "".length();
        "".length();
        if (-1 >= 4) {
            throw null;
        }
        while (i < length) {
            final FishType fishType = values[i];
            if (!this.cooked || fishType.canCook()) {
                list.add(new ItemStack(this, " ".length(), fishType.getMetadata()));
            }
            ++i;
        }
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
            if (-1 >= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private static void I() {
        (I = new String[0xC0 ^ 0xC5])["".length()] = I("O[Lz^V@R`DWMUfA", "dkaKu");
        ItemFishFood.I[" ".length()] = I("@", "nHoTq");
        ItemFishFood.I["  ".length()] = I("e", "KQtMm");
        ItemFishFood.I["   ".length()] = I("0>\u0000<<7", "SQoWY");
        ItemFishFood.I[0xA ^ 0xE] = I("8;#", "JZTHv");
    }
    
    static {
        I();
    }
    
    public enum FishType
    {
        COD(FishType.I["".length()], "".length(), "".length(), FishType.I[" ".length()], "  ".length(), 0.1f, 0x2D ^ 0x28, 0.6f);
        
        private static final String[] I;
        private static final FishType[] ENUM$VALUES;
        
        PUFFERFISH(FishType.I[0x23 ^ 0x25], "   ".length(), "   ".length(), FishType.I[0x19 ^ 0x1E], " ".length(), 0.1f);
        
        private final float cookedSaturationModifier;
        
        SALMON(FishType.I["  ".length()], " ".length(), " ".length(), FishType.I["   ".length()], "  ".length(), 0.1f, 0xE ^ 0x8, 0.8f);
        
        private final int uncookedHealAmount;
        
        CLOWNFISH(FishType.I[0x75 ^ 0x71], "  ".length(), "  ".length(), FishType.I[0x8E ^ 0x8B], " ".length(), 0.1f);
        
        private final int meta;
        private final float uncookedSaturationModifier;
        private static final Map<Integer, FishType> META_LOOKUP;
        private final int cookedHealAmount;
        private boolean cookable;
        private final String unlocalizedName;
        
        public int getCookedHealAmount() {
            return this.cookedHealAmount;
        }
        
        public static FishType byMetadata(final int n) {
            final FishType fishType = FishType.META_LOOKUP.get(n);
            FishType cod;
            if (fishType == null) {
                cod = FishType.COD;
                "".length();
                if (3 < 1) {
                    throw null;
                }
            }
            else {
                cod = fishType;
            }
            return cod;
        }
        
        public float getUncookedSaturationModifier() {
            return this.uncookedSaturationModifier;
        }
        
        public int getUncookedHealAmount() {
            return this.uncookedHealAmount;
        }
        
        public static FishType byItemStack(final ItemStack itemStack) {
            FishType fishType;
            if (itemStack.getItem() instanceof ItemFishFood) {
                fishType = byMetadata(itemStack.getMetadata());
                "".length();
                if (0 >= 1) {
                    throw null;
                }
            }
            else {
                fishType = FishType.COD;
            }
            return fishType;
        }
        
        static {
            I();
            final FishType[] enum$VALUES = new FishType[0x1E ^ 0x1A];
            enum$VALUES["".length()] = FishType.COD;
            enum$VALUES[" ".length()] = FishType.SALMON;
            enum$VALUES["  ".length()] = FishType.CLOWNFISH;
            enum$VALUES["   ".length()] = FishType.PUFFERFISH;
            ENUM$VALUES = enum$VALUES;
            META_LOOKUP = Maps.newHashMap();
            final FishType[] values;
            final int length = (values = values()).length;
            int i = "".length();
            "".length();
            if (1 < 0) {
                throw null;
            }
            while (i < length) {
                final FishType fishType = values[i];
                FishType.META_LOOKUP.put(fishType.getMetadata(), fishType);
                ++i;
            }
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
                if (false == true) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public float getCookedSaturationModifier() {
            return this.cookedSaturationModifier;
        }
        
        public String getUnlocalizedName() {
            return this.unlocalizedName;
        }
        
        private static void I() {
            (I = new String[0x32 ^ 0x3A])["".length()] = I("&)7", "efsbb");
            FishType.I[" ".length()] = I("\u0007-2", "dBVie");
            FishType.I["  ".length()] = I("*\u00196\u001d\u001e7", "yXzPQ");
            FishType.I["   ".length()] = I("\n\u0003%\u001b\u0004\u0017", "ybIvk");
            FishType.I[0xC7 ^ 0xC3] = I("0\u0004#\u0014\u001b5\u0001?\u000b", "sHlCU");
            FishType.I[0x49 ^ 0x4C] = I("*\"\u0001\u0010\u0004/'\u001d\u000f", "INngj");
            FishType.I[0x59 ^ 0x5F] = I("*\u0004)\u00075(\u0017&\u00128", "zQoAp");
            FishType.I[0x6 ^ 0x1] = I("\u001b83+\u001d\u0019+<>\u0010", "kMUMx");
        }
        
        public int getMetadata() {
            return this.meta;
        }
        
        private FishType(final String s, final int n, final int meta, final String unlocalizedName, final int uncookedHealAmount, final float uncookedSaturationModifier, final int cookedHealAmount, final float cookedSaturationModifier) {
            this.cookable = ("".length() != 0);
            this.meta = meta;
            this.unlocalizedName = unlocalizedName;
            this.uncookedHealAmount = uncookedHealAmount;
            this.uncookedSaturationModifier = uncookedSaturationModifier;
            this.cookedHealAmount = cookedHealAmount;
            this.cookedSaturationModifier = cookedSaturationModifier;
            this.cookable = (" ".length() != 0);
        }
        
        private FishType(final String s, final int n, final int meta, final String unlocalizedName, final int uncookedHealAmount, final float uncookedSaturationModifier) {
            this.cookable = ("".length() != 0);
            this.meta = meta;
            this.unlocalizedName = unlocalizedName;
            this.uncookedHealAmount = uncookedHealAmount;
            this.uncookedSaturationModifier = uncookedSaturationModifier;
            this.cookedHealAmount = "".length();
            this.cookedSaturationModifier = 0.0f;
            this.cookable = ("".length() != 0);
        }
        
        public boolean canCook() {
            return this.cookable;
        }
    }
}
