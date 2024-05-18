package net.minecraft.item;

import net.minecraft.dispenser.*;
import net.minecraft.block.*;
import com.google.common.base.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.nbt.*;
import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.init.*;

public class ItemArmor extends Item
{
    public final int renderIndex;
    public static final String[] EMPTY_SLOT_NAMES;
    private static final int[] maxDamageArray;
    private final ArmorMaterial material;
    public final int damageReduceAmount;
    private static final IBehaviorDispenseItem dispenserBehavior;
    public final int armorType;
    private static final String[] I;
    
    public int getColor(final ItemStack itemStack) {
        if (this.material != ArmorMaterial.LEATHER) {
            return -" ".length();
        }
        final NBTTagCompound tagCompound = itemStack.getTagCompound();
        if (tagCompound != null) {
            final NBTTagCompound compoundTag = tagCompound.getCompoundTag(ItemArmor.I[0x2C ^ 0x2B]);
            if (compoundTag != null && compoundTag.hasKey(ItemArmor.I[0x40 ^ 0x48], "   ".length())) {
                return compoundTag.getInteger(ItemArmor.I[0xD ^ 0x4]);
            }
        }
        return 8946512 + 2831472 - 7124638 + 5858334;
    }
    
    private static void I() {
        (I = new String[0xB0 ^ 0xA2])["".length()] = I(" %\t#\n?-\u00012S$8\u0002+\u001ab)\n6\u001d4\u0013\u00064\u0004\">85\u0005\"88.\f!!\u00022", "MLgFi");
        ItemArmor.I[" ".length()] = I("\u001e*+1*\u0001\"# s\u001a7 9:\\&($=\n\u001c$&$\u001c1\u001a'%\u001c7\u001a7!\u001601$%\u00127 ", "sCETI");
        ItemArmor.I["  ".length()] = I("\u0019.\u0014\u0015%\u0006&\u001c\u0004|\u001d3\u001f\u001d5[\"\u0017\u00002\r\u0018\u001b\u0002+\u001b5%\u0003*\u001b3%\u001c#\u0013 \u0013\u001e!\u0007", "tGzpF");
        ItemArmor.I["   ".length()] = I("\u0014\r'\u00147\u000b\u0005/\u0005n\u0010\u0010,\u001c'V\u0001$\u0001 \u0000;(\u00039\u0016\u0016\u0016\u00028\u0016\u0010\u0016\u0013;\u0016\u0010:", "ydIqT");
        ItemArmor.I[0x1 ^ 0x5] = I("\u001c\r*6-\u0019\u001d", "xdYFA");
        ItemArmor.I[0x5 ^ 0x0] = I("4\n>\u0000\u000f1\u001a", "PcMpc");
        ItemArmor.I[0x32 ^ 0x34] = I("\u0012\u0001:\u001c\u0016", "qnVsd");
        ItemArmor.I[0x47 ^ 0x40] = I("\u00053616\u0000#", "aZEAZ");
        ItemArmor.I[0x35 ^ 0x3D] = I("\u000f=\u0006\u001f'", "lRjpU");
        ItemArmor.I[0xA5 ^ 0xAC] = I("\u0005\t!\u0000#", "ffMoQ");
        ItemArmor.I[0xBF ^ 0xB5] = I("4/\u001c>?1?", "PFoNS");
        ItemArmor.I[0x0 ^ 0xB] = I("\u0002\u0018\u0000$>", "awlKL");
        ItemArmor.I[0x9 ^ 0x5] = I("$\f\u000f&9", "GccIK");
        ItemArmor.I[0x9A ^ 0x97] = I("\u0002\u0003\u0014}\u0017a\u0006\u0003?C/\r\u0014w\u000f$\u0003\u000e2\u00063C", "AbzZc");
        ItemArmor.I[0xA8 ^ 0xA6] = I("\u0007!\u0011;(\u00021", "cHbKD");
        ItemArmor.I[0x62 ^ 0x6D] = I("\u0000\u000f\u0019\n\u0018\u0005\u001f", "dfjzt");
        ItemArmor.I[0x96 ^ 0x86] = I("!0\t\u0014\"$ ", "EYzdN");
        ItemArmor.I[0x96 ^ 0x87] = I("&\u0003\t#$", "EleLV");
    }
    
    static {
        I();
        final int[] maxDamageArray2 = new int[0x5 ^ 0x1];
        maxDamageArray2["".length()] = (0x16 ^ 0x1D);
        maxDamageArray2[" ".length()] = (0x4A ^ 0x5A);
        maxDamageArray2["  ".length()] = (0x21 ^ 0x2E);
        maxDamageArray2["   ".length()] = (0x7C ^ 0x71);
        maxDamageArray = maxDamageArray2;
        final String[] empty_SLOT_NAMES = new String[0x89 ^ 0x8D];
        empty_SLOT_NAMES["".length()] = ItemArmor.I["".length()];
        empty_SLOT_NAMES[" ".length()] = ItemArmor.I[" ".length()];
        empty_SLOT_NAMES["  ".length()] = ItemArmor.I["  ".length()];
        empty_SLOT_NAMES["   ".length()] = ItemArmor.I["   ".length()];
        EMPTY_SLOT_NAMES = empty_SLOT_NAMES;
        dispenserBehavior = new BehaviorDefaultDispenseItem() {
            @Override
            protected ItemStack dispenseStack(final IBlockSource blockSource, final ItemStack itemStack) {
                final BlockPos offset = blockSource.getBlockPos().offset(BlockDispenser.getFacing(blockSource.getBlockMetadata()));
                final int x = offset.getX();
                final int y = offset.getY();
                final int z = offset.getZ();
                final List<Entity> entitiesWithinAABB = blockSource.getWorld().getEntitiesWithinAABB((Class<? extends Entity>)EntityLivingBase.class, new AxisAlignedBB(x, y, z, x + " ".length(), y + " ".length(), z + " ".length()), (com.google.common.base.Predicate<? super Entity>)Predicates.and((Predicate)EntitySelectors.NOT_SPECTATING, (Predicate)new EntitySelectors.ArmoredMob(itemStack)));
                if (entitiesWithinAABB.size() > 0) {
                    final EntityLivingBase entityLivingBase = entitiesWithinAABB.get("".length());
                    int n;
                    if (entityLivingBase instanceof EntityPlayer) {
                        n = " ".length();
                        "".length();
                        if (2 != 2) {
                            throw null;
                        }
                    }
                    else {
                        n = "".length();
                    }
                    final int n2 = n;
                    final int armorPosition = EntityLiving.getArmorPosition(itemStack);
                    final ItemStack copy = itemStack.copy();
                    copy.stackSize = " ".length();
                    entityLivingBase.setCurrentItemOrArmor(armorPosition - n2, copy);
                    if (entityLivingBase instanceof EntityLiving) {
                        ((EntityLiving)entityLivingBase).setEquipmentDropChance(armorPosition, 2.0f);
                    }
                    itemStack.stackSize -= " ".length();
                    return itemStack;
                }
                return super.dispenseStack(blockSource, itemStack);
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
                    if (3 < 0) {
                        throw null;
                    }
                }
                return sb.toString();
            }
        };
    }
    
    @Override
    public int getColorFromItemStack(final ItemStack itemStack, final int n) {
        if (n > 0) {
            return 11509878 + 3799234 - 7608594 + 9076697;
        }
        int color = this.getColor(itemStack);
        if (color < 0) {
            color = 12606738 + 13585486 - 21877468 + 12462459;
        }
        return color;
    }
    
    public void setColor(final ItemStack itemStack, final int n) {
        if (this.material != ArmorMaterial.LEATHER) {
            throw new UnsupportedOperationException(ItemArmor.I[0x30 ^ 0x3D]);
        }
        NBTTagCompound tagCompound = itemStack.getTagCompound();
        if (tagCompound == null) {
            tagCompound = new NBTTagCompound();
            itemStack.setTagCompound(tagCompound);
        }
        final NBTTagCompound compoundTag = tagCompound.getCompoundTag(ItemArmor.I[0xB4 ^ 0xBA]);
        if (!tagCompound.hasKey(ItemArmor.I[0x95 ^ 0x9A], 0x7 ^ 0xD)) {
            tagCompound.setTag(ItemArmor.I[0x66 ^ 0x76], compoundTag);
        }
        compoundTag.setInteger(ItemArmor.I[0x85 ^ 0x94], n);
    }
    
    @Override
    public int getItemEnchantability() {
        return this.material.getEnchantability();
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
            if (3 < 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public boolean hasColor(final ItemStack itemStack) {
        int n;
        if (this.material != ArmorMaterial.LEATHER) {
            n = "".length();
            "".length();
            if (true != true) {
                throw null;
            }
        }
        else if (!itemStack.hasTagCompound()) {
            n = "".length();
            "".length();
            if (false) {
                throw null;
            }
        }
        else if (!itemStack.getTagCompound().hasKey(ItemArmor.I[0x46 ^ 0x42], 0x76 ^ 0x7C)) {
            n = "".length();
            "".length();
            if (0 < -1) {
                throw null;
            }
        }
        else {
            n = (itemStack.getTagCompound().getCompoundTag(ItemArmor.I[0x24 ^ 0x21]).hasKey(ItemArmor.I[0x46 ^ 0x40], "   ".length()) ? 1 : 0);
        }
        return n != 0;
    }
    
    public ItemArmor(final ArmorMaterial material, final int renderIndex, final int armorType) {
        this.material = material;
        this.armorType = armorType;
        this.renderIndex = renderIndex;
        this.damageReduceAmount = material.getDamageReductionAmount(armorType);
        this.setMaxDamage(material.getDurability(armorType));
        this.maxStackSize = " ".length();
        this.setCreativeTab(CreativeTabs.tabCombat);
        BlockDispenser.dispenseBehaviorRegistry.putObject(this, ItemArmor.dispenserBehavior);
    }
    
    public ArmorMaterial getArmorMaterial() {
        return this.material;
    }
    
    static int[] access$0() {
        return ItemArmor.maxDamageArray;
    }
    
    @Override
    public boolean getIsRepairable(final ItemStack itemStack, final ItemStack itemStack2) {
        int n;
        if (this.material.getRepairItem() == itemStack2.getItem()) {
            n = " ".length();
            "".length();
            if (-1 >= 3) {
                throw null;
            }
        }
        else {
            n = (super.getIsRepairable(itemStack, itemStack2) ? 1 : 0);
        }
        return n != 0;
    }
    
    public void removeColor(final ItemStack itemStack) {
        if (this.material == ArmorMaterial.LEATHER) {
            final NBTTagCompound tagCompound = itemStack.getTagCompound();
            if (tagCompound != null) {
                final NBTTagCompound compoundTag = tagCompound.getCompoundTag(ItemArmor.I[0x72 ^ 0x78]);
                if (compoundTag.hasKey(ItemArmor.I[0x60 ^ 0x6B])) {
                    compoundTag.removeTag(ItemArmor.I[0x95 ^ 0x99]);
                }
            }
        }
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        final int n = EntityLiving.getArmorPosition(itemStack) - " ".length();
        if (entityPlayer.getCurrentArmor(n) == null) {
            entityPlayer.setCurrentItemOrArmor(n, itemStack.copy());
            itemStack.stackSize = "".length();
        }
        return itemStack;
    }
    
    public enum ArmorMaterial
    {
        private static final ArmorMaterial[] ENUM$VALUES;
        
        GOLD(s7, length4, s8, n4, array4, 0x0 ^ 0x19);
        
        private final int enchantability;
        private final int[] damageReductionAmountArray;
        private final String name;
        
        DIAMOND(s9, n5, s10, n6, array5, 0x5E ^ 0x54), 
        IRON(s5, length3, s6, n3, array3, 0x6D ^ 0x64);
        
        private static final String[] I;
        
        LEATHER(s, length, s2, n, array, 0xBB ^ 0xB4);
        
        private final int maxDamageFactor;
        
        CHAIN(s3, length2, s4, n2, array2, 0xA5 ^ 0xA9);
        
        private ArmorMaterial(final String s, final int n, final String name, final int maxDamageFactor, final int[] damageReductionAmountArray, final int enchantability) {
            this.name = name;
            this.maxDamageFactor = maxDamageFactor;
            this.damageReductionAmountArray = damageReductionAmountArray;
            this.enchantability = enchantability;
        }
        
        public int getEnchantability() {
            return this.enchantability;
        }
        
        private static void I() {
            (I = new String[0x99 ^ 0x93])["".length()] = I("\r26=>\u0004%", "Awwiv");
            ArmorMaterial.I[" ".length()] = I("\u001b\u0007\u0006\u0006.\u0012\u0010", "wbgrF");
            ArmorMaterial.I["  ".length()] = I("!\"\f\f\t", "bjMEG");
            ArmorMaterial.I["   ".length()] = I("3>\u0010,\u001e=7\u0018)", "PVqEp");
            ArmorMaterial.I[0x45 ^ 0x41] = I("!\u0019\u001e\u0001", "hKQOO");
            ArmorMaterial.I[0x7A ^ 0x7F] = I("\u000e\u0004'9", "gvHWm");
            ArmorMaterial.I[0xA ^ 0xC] = I("\u0012\t\u00145", "UFXqR");
            ArmorMaterial.I[0x7C ^ 0x7B] = I("\u0013*:6", "tEVRF");
            ArmorMaterial.I[0xBC ^ 0xB4] = I("<\u00192(*6\u0014", "xPsee");
            ArmorMaterial.I[0x3F ^ 0x36] = I("\u0006\u00079\t\u000e\f\n", "bnXda");
        }
        
        static {
            I();
            final String s = ArmorMaterial.I["".length()];
            final int length = "".length();
            final String s2 = ArmorMaterial.I[" ".length()];
            final int n = 0x22 ^ 0x27;
            final int[] array = new int[0x72 ^ 0x76];
            array["".length()] = " ".length();
            array[" ".length()] = "   ".length();
            array["  ".length()] = "  ".length();
            array["   ".length()] = " ".length();
            final String s3 = ArmorMaterial.I["  ".length()];
            final int length2 = " ".length();
            final String s4 = ArmorMaterial.I["   ".length()];
            final int n2 = 0x42 ^ 0x4D;
            final int[] array2 = new int[0x38 ^ 0x3C];
            array2["".length()] = "  ".length();
            array2[" ".length()] = (0x84 ^ 0x81);
            array2["  ".length()] = (0x97 ^ 0x93);
            array2["   ".length()] = " ".length();
            final String s5 = ArmorMaterial.I[0x6C ^ 0x68];
            final int length3 = "  ".length();
            final String s6 = ArmorMaterial.I[0x7F ^ 0x7A];
            final int n3 = 0x58 ^ 0x57;
            final int[] array3 = new int[0x1B ^ 0x1F];
            array3["".length()] = "  ".length();
            array3[" ".length()] = (0x25 ^ 0x23);
            array3["  ".length()] = (0x58 ^ 0x5D);
            array3["   ".length()] = "  ".length();
            final String s7 = ArmorMaterial.I[0x80 ^ 0x86];
            final int length4 = "   ".length();
            final String s8 = ArmorMaterial.I[0x42 ^ 0x45];
            final int n4 = 0x8C ^ 0x8B;
            final int[] array4 = new int[0xBE ^ 0xBA];
            array4["".length()] = "  ".length();
            array4[" ".length()] = (0x3B ^ 0x3E);
            array4["  ".length()] = "   ".length();
            array4["   ".length()] = " ".length();
            final String s9 = ArmorMaterial.I[0x5A ^ 0x52];
            final int n5 = 0x63 ^ 0x67;
            final String s10 = ArmorMaterial.I[0x72 ^ 0x7B];
            final int n6 = 0x9B ^ 0xBA;
            final int[] array5 = new int[0xAB ^ 0xAF];
            array5["".length()] = "   ".length();
            array5[" ".length()] = (0x6 ^ 0xE);
            array5["  ".length()] = (0x8C ^ 0x8A);
            array5["   ".length()] = "   ".length();
            final ArmorMaterial[] enum$VALUES = new ArmorMaterial[0x46 ^ 0x43];
            enum$VALUES["".length()] = ArmorMaterial.LEATHER;
            enum$VALUES[" ".length()] = ArmorMaterial.CHAIN;
            enum$VALUES["  ".length()] = ArmorMaterial.IRON;
            enum$VALUES["   ".length()] = ArmorMaterial.GOLD;
            enum$VALUES[0x61 ^ 0x65] = ArmorMaterial.DIAMOND;
            ENUM$VALUES = enum$VALUES;
        }
        
        public Item getRepairItem() {
            Item item;
            if (this == ArmorMaterial.LEATHER) {
                item = Items.leather;
                "".length();
                if (2 < -1) {
                    throw null;
                }
            }
            else if (this == ArmorMaterial.CHAIN) {
                item = Items.iron_ingot;
                "".length();
                if (2 >= 3) {
                    throw null;
                }
            }
            else if (this == ArmorMaterial.GOLD) {
                item = Items.gold_ingot;
                "".length();
                if (4 < 2) {
                    throw null;
                }
            }
            else if (this == ArmorMaterial.IRON) {
                item = Items.iron_ingot;
                "".length();
                if (1 <= -1) {
                    throw null;
                }
            }
            else if (this == ArmorMaterial.DIAMOND) {
                item = Items.diamond;
                "".length();
                if (4 == 3) {
                    throw null;
                }
            }
            else {
                item = null;
            }
            return item;
        }
        
        public int getDamageReductionAmount(final int n) {
            return this.damageReductionAmountArray[n];
        }
        
        public String getName() {
            return this.name;
        }
        
        public int getDurability(final int n) {
            return ItemArmor.access$0()[n] * this.maxDamageFactor;
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
                if (-1 == 2) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
}
