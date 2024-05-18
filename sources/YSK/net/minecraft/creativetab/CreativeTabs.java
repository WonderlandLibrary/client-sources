package net.minecraft.creativetab;

import net.minecraft.enchantment.*;
import net.minecraft.item.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.block.*;

public abstract class CreativeTabs
{
    public static final CreativeTabs tabTransport;
    private static final String[] I;
    public static final CreativeTabs tabFood;
    private final int tabIndex;
    private ItemStack iconItemStack;
    private String theTexture;
    public static final CreativeTabs tabBlock;
    public static final CreativeTabs tabRedstone;
    public static final CreativeTabs tabBrewing;
    private boolean hasScrollbar;
    public static final CreativeTabs tabMisc;
    private final String tabLabel;
    private EnumEnchantmentType[] enchantmentTypes;
    public static final CreativeTabs tabInventory;
    public static final CreativeTabs[] creativeTabArray;
    public static final CreativeTabs tabMaterials;
    public static final CreativeTabs tabCombat;
    public static final CreativeTabs tabTools;
    public static final CreativeTabs tabDecorations;
    private boolean drawTitle;
    public static final CreativeTabs tabAllSearch;
    
    public String getTabLabel() {
        return this.tabLabel;
    }
    
    public ItemStack getIconItemStack() {
        if (this.iconItemStack == null) {
            this.iconItemStack = new ItemStack(this.getTabIconItem(), " ".length(), this.getIconItemDamage());
        }
        return this.iconItemStack;
    }
    
    public int getTabIndex() {
        return this.tabIndex;
    }
    
    public boolean isTabInFirstRow() {
        if (this.tabIndex < (0xB7 ^ 0xB1)) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public int getTabColumn() {
        return this.tabIndex % (0xAE ^ 0xA8);
    }
    
    private static void I() {
        (I = new String[0xBA ^ 0xAA])["".length()] = I(" >%\u0006++%+(#-('\u0019", "BKLjO");
        CreativeTabs.I[" ".length()] = I("\u00140\u0014.\u0015\u0011!\u001e.\t\u0003", "pUwAg");
        CreativeTabs.I["  ".length()] = I("'<\b<\u0007:7\t", "UYlOs");
        CreativeTabs.I["   ".length()] = I("1\u0000\u0012\u0005\u00025\u001d\u0001\u001f\u00101\u001b\u001c\u0005", "Erskq");
        CreativeTabs.I[0xC3 ^ 0xC7] = I("\u0003=\u0004\u0004", "nTwgr");
        CreativeTabs.I[0x51 ^ 0x54] = I("\u0018\u001d\u000f\u0002$\u0003", "kxnpG");
        CreativeTabs.I[0x86 ^ 0x80] = I("\u000f01\u001a,\u0015!5\u0005\u0010\u000ej$\u0019\u0014", "fDTws");
        CreativeTabs.I[0x1F ^ 0x18] = I("\u0003 \u0018-", "eOwIk");
        CreativeTabs.I[0x6F ^ 0x67] = I("\u0017\"-#\u0003", "cMBOp");
        CreativeTabs.I[0x2A ^ 0x23] = I("/\u0016\u0007\u0014\u00028", "Lyjvc");
        CreativeTabs.I[0x30 ^ 0x3A] = I(".>#.0\"+", "LLFYY");
        CreativeTabs.I[0x65 ^ 0x6E] = I(":*'(\u000b>*?>", "WKSMy");
        CreativeTabs.I[0x66 ^ 0x6A] = I("\u001f\r\u00077\u0018\u0002\f\u0003+", "vcqRv");
        CreativeTabs.I[0x25 ^ 0x28] = I("<\u000b2\u0012\u001e!\n6\u000e^%\u000b#", "UeDwp");
        CreativeTabs.I[0x58 ^ 0x56] = I("\u001c;24;[?9>", "uOWYH");
        CreativeTabs.I[0xB3 ^ 0xBC] = I("\u00065)\u0003\u0017\u001d.9\u001e~", "oALnP");
    }
    
    public boolean hasRelevantEnchantmentType(final EnumEnchantmentType enumEnchantmentType) {
        if (this.enchantmentTypes == null) {
            return "".length() != 0;
        }
        final EnumEnchantmentType[] enchantmentTypes;
        final int length = (enchantmentTypes = this.enchantmentTypes).length;
        int i = "".length();
        "".length();
        if (0 >= 1) {
            throw null;
        }
        while (i < length) {
            if (enchantmentTypes[i] == enumEnchantmentType) {
                return " ".length() != 0;
            }
            ++i;
        }
        return "".length() != 0;
    }
    
    public boolean shouldHidePlayerInventory() {
        return this.hasScrollbar;
    }
    
    public CreativeTabs setNoScrollbar() {
        this.hasScrollbar = ("".length() != 0);
        return this;
    }
    
    public CreativeTabs setBackgroundImageName(final String theTexture) {
        this.theTexture = theTexture;
        return this;
    }
    
    public void addEnchantmentBooksToList(final List<ItemStack> list, final EnumEnchantmentType... array) {
        final Enchantment[] enchantmentsBookList;
        final int length = (enchantmentsBookList = Enchantment.enchantmentsBookList).length;
        int i = "".length();
        "".length();
        if (0 <= -1) {
            throw null;
        }
        while (i < length) {
            final Enchantment enchantment = enchantmentsBookList[i];
            if (enchantment != null && enchantment.type != null) {
                int n = "".length();
                int length2 = "".length();
                "".length();
                if (true != true) {
                    throw null;
                }
                while (length2 < array.length && n == 0) {
                    if (enchantment.type == array[length2]) {
                        n = " ".length();
                    }
                    ++length2;
                }
                if (n != 0) {
                    list.add(Items.enchanted_book.getEnchantedItemStack(new EnchantmentData(enchantment, enchantment.getMaxLevel())));
                }
            }
            ++i;
        }
    }
    
    public int getIconItemDamage() {
        return "".length();
    }
    
    public boolean drawInForegroundOfTab() {
        return this.drawTitle;
    }
    
    public CreativeTabs(final int tabIndex, final String tabLabel) {
        this.theTexture = CreativeTabs.I[0x77 ^ 0x79];
        this.hasScrollbar = (" ".length() != 0);
        this.drawTitle = (" ".length() != 0);
        this.tabIndex = tabIndex;
        this.tabLabel = tabLabel;
        CreativeTabs.creativeTabArray[tabIndex] = this;
    }
    
    public CreativeTabs setRelevantEnchantmentTypes(final EnumEnchantmentType... enchantmentTypes) {
        this.enchantmentTypes = enchantmentTypes;
        return this;
    }
    
    public EnumEnchantmentType[] getRelevantEnchantmentTypes() {
        return this.enchantmentTypes;
    }
    
    public void displayAllReleventItems(final List<ItemStack> list) {
        final Iterator<Item> iterator = Item.itemRegistry.iterator();
        "".length();
        if (-1 == 1) {
            throw null;
        }
        while (iterator.hasNext()) {
            final Item item = iterator.next();
            if (item != null && item.getCreativeTab() == this) {
                item.getSubItems(item, this, list);
            }
        }
        if (this.getRelevantEnchantmentTypes() != null) {
            this.addEnchantmentBooksToList(list, this.getRelevantEnchantmentTypes());
        }
    }
    
    public String getBackgroundImageName() {
        return this.theTexture;
    }
    
    public CreativeTabs setNoTitle() {
        this.drawTitle = ("".length() != 0);
        return this;
    }
    
    public abstract Item getTabIconItem();
    
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
            if (3 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public String getTranslatedTabLabel() {
        return CreativeTabs.I[0x44 ^ 0x4B] + this.getTabLabel();
    }
    
    static {
        I();
        creativeTabArray = new CreativeTabs[0x6B ^ 0x67];
        tabBlock = new CreativeTabs(CreativeTabs.I["".length()]) {
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
                    if (2 == 1) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            @Override
            public Item getTabIconItem() {
                return Item.getItemFromBlock(Blocks.brick_block);
            }
        };
        tabDecorations = new CreativeTabs(CreativeTabs.I[" ".length()]) {
            @Override
            public int getIconItemDamage() {
                return BlockDoublePlant.EnumPlantType.PAEONIA.getMeta();
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
                    if (2 >= 3) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            @Override
            public Item getTabIconItem() {
                return Item.getItemFromBlock(Blocks.double_plant);
            }
        };
        tabRedstone = new CreativeTabs(CreativeTabs.I["  ".length()]) {
            @Override
            public Item getTabIconItem() {
                return Items.redstone;
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
                    if (true != true) {
                        throw null;
                    }
                }
                return sb.toString();
            }
        };
        tabTransport = new CreativeTabs(CreativeTabs.I["   ".length()]) {
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
                    if (2 == 0) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            @Override
            public Item getTabIconItem() {
                return Item.getItemFromBlock(Blocks.golden_rail);
            }
        };
        final CreativeTabs creativeTabs = new CreativeTabs(CreativeTabs.I[0x77 ^ 0x73]) {
            @Override
            public Item getTabIconItem() {
                return Items.lava_bucket;
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
                    if (4 < 0) {
                        throw null;
                    }
                }
                return sb.toString();
            }
        };
        final EnumEnchantmentType[] relevantEnchantmentTypes = new EnumEnchantmentType[" ".length()];
        relevantEnchantmentTypes["".length()] = EnumEnchantmentType.ALL;
        tabMisc = creativeTabs.setRelevantEnchantmentTypes(relevantEnchantmentTypes);
        tabAllSearch = new CreativeTabs(CreativeTabs.I[0xBC ^ 0xB9]) {
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
                    if (4 != 4) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            @Override
            public Item getTabIconItem() {
                return Items.compass;
            }
        }.setBackgroundImageName(CreativeTabs.I[0x84 ^ 0x82]);
        tabFood = new CreativeTabs(CreativeTabs.I[0xAB ^ 0xAC]) {
            @Override
            public Item getTabIconItem() {
                return Items.apple;
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
                    if (!true) {
                        throw null;
                    }
                }
                return sb.toString();
            }
        };
        final CreativeTabs creativeTabs2 = new CreativeTabs(CreativeTabs.I[0x38 ^ 0x30]) {
            @Override
            public Item getTabIconItem() {
                return Items.iron_axe;
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
                    if (4 <= 1) {
                        throw null;
                    }
                }
                return sb.toString();
            }
        };
        final EnumEnchantmentType[] relevantEnchantmentTypes2 = new EnumEnchantmentType["   ".length()];
        relevantEnchantmentTypes2["".length()] = EnumEnchantmentType.DIGGER;
        relevantEnchantmentTypes2[" ".length()] = EnumEnchantmentType.FISHING_ROD;
        relevantEnchantmentTypes2["  ".length()] = EnumEnchantmentType.BREAKABLE;
        tabTools = creativeTabs2.setRelevantEnchantmentTypes(relevantEnchantmentTypes2);
        final CreativeTabs creativeTabs3 = new CreativeTabs(CreativeTabs.I[0xCB ^ 0xC2]) {
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
                    if (3 >= 4) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            @Override
            public Item getTabIconItem() {
                return Items.golden_sword;
            }
        };
        final EnumEnchantmentType[] relevantEnchantmentTypes3 = new EnumEnchantmentType[0x56 ^ 0x51];
        relevantEnchantmentTypes3["".length()] = EnumEnchantmentType.ARMOR;
        relevantEnchantmentTypes3[" ".length()] = EnumEnchantmentType.ARMOR_FEET;
        relevantEnchantmentTypes3["  ".length()] = EnumEnchantmentType.ARMOR_HEAD;
        relevantEnchantmentTypes3["   ".length()] = EnumEnchantmentType.ARMOR_LEGS;
        relevantEnchantmentTypes3[0x85 ^ 0x81] = EnumEnchantmentType.ARMOR_TORSO;
        relevantEnchantmentTypes3[0x23 ^ 0x26] = EnumEnchantmentType.BOW;
        relevantEnchantmentTypes3[0x94 ^ 0x92] = EnumEnchantmentType.WEAPON;
        tabCombat = creativeTabs3.setRelevantEnchantmentTypes(relevantEnchantmentTypes3);
        tabBrewing = new CreativeTabs(CreativeTabs.I[0xBD ^ 0xB7]) {
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
                    if (4 < 3) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            @Override
            public Item getTabIconItem() {
                return Items.potionitem;
            }
        };
        tabMaterials = new CreativeTabs(CreativeTabs.I[0x7B ^ 0x70]) {
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
                    if (4 <= 3) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            @Override
            public Item getTabIconItem() {
                return Items.stick;
            }
        };
        tabInventory = new CreativeTabs(CreativeTabs.I[0xB9 ^ 0xB5]) {
            @Override
            public Item getTabIconItem() {
                return Item.getItemFromBlock(Blocks.chest);
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
                    if (-1 != -1) {
                        throw null;
                    }
                }
                return sb.toString();
            }
        }.setBackgroundImageName(CreativeTabs.I[0x94 ^ 0x99]).setNoScrollbar().setNoTitle();
    }
}
