package net.minecraft.enchantment;

import net.minecraft.item.*;

public enum EnumEnchantmentType
{
    ARMOR_TORSO(EnumEnchantmentType.I[0x7E ^ 0x7A], 0xAA ^ 0xAE), 
    BREAKABLE(EnumEnchantmentType.I[0x2B ^ 0x22], 0x46 ^ 0x4F), 
    FISHING_ROD(EnumEnchantmentType.I[0x6C ^ 0x64], 0x10 ^ 0x18);
    
    private static final EnumEnchantmentType[] ENUM$VALUES;
    
    ARMOR(EnumEnchantmentType.I[" ".length()], " ".length()), 
    ALL(EnumEnchantmentType.I["".length()], "".length());
    
    private static final String[] I;
    
    ARMOR_HEAD(EnumEnchantmentType.I[0x22 ^ 0x27], 0x8 ^ 0xD), 
    BOW(EnumEnchantmentType.I[0x18 ^ 0x12], 0x89 ^ 0x83), 
    WEAPON(EnumEnchantmentType.I[0x97 ^ 0x91], 0x66 ^ 0x60), 
    ARMOR_LEGS(EnumEnchantmentType.I["   ".length()], "   ".length()), 
    ARMOR_FEET(EnumEnchantmentType.I["  ".length()], "  ".length()), 
    DIGGER(EnumEnchantmentType.I[0x7B ^ 0x7C], 0xBC ^ 0xBB);
    
    static {
        I();
        final EnumEnchantmentType[] enum$VALUES = new EnumEnchantmentType[0x4 ^ 0xF];
        enum$VALUES["".length()] = EnumEnchantmentType.ALL;
        enum$VALUES[" ".length()] = EnumEnchantmentType.ARMOR;
        enum$VALUES["  ".length()] = EnumEnchantmentType.ARMOR_FEET;
        enum$VALUES["   ".length()] = EnumEnchantmentType.ARMOR_LEGS;
        enum$VALUES[0x7A ^ 0x7E] = EnumEnchantmentType.ARMOR_TORSO;
        enum$VALUES[0xA ^ 0xF] = EnumEnchantmentType.ARMOR_HEAD;
        enum$VALUES[0xB2 ^ 0xB4] = EnumEnchantmentType.WEAPON;
        enum$VALUES[0x6E ^ 0x69] = EnumEnchantmentType.DIGGER;
        enum$VALUES[0xC ^ 0x4] = EnumEnchantmentType.FISHING_ROD;
        enum$VALUES[0x4B ^ 0x42] = EnumEnchantmentType.BREAKABLE;
        enum$VALUES[0xB4 ^ 0xBE] = EnumEnchantmentType.BOW;
        ENUM$VALUES = enum$VALUES;
    }
    
    private EnumEnchantmentType(final String s, final int n) {
    }
    
    private static void I() {
        (I = new String[0x4B ^ 0x40])["".length()] = I("7\u0004\r", "vHAYQ");
        EnumEnchantmentType.I[" ".length()] = I("\r7'#>", "Lejll");
        EnumEnchantmentType.I["  ".length()] = I(" 3'$=>'/.;", "aajko");
        EnumEnchantmentType.I["   ".length()] = I("\u000e\u001b\u0002:3\u0010\u0005\n22", "OIOua");
        EnumEnchantmentType.I[0x22 ^ 0x26] = I("5\u0001\u001e'\u0003+\u0007\u001c:\u0002;", "tSShQ");
        EnumEnchantmentType.I[0x44 ^ 0x41] = I("#;\u0002(\u0000=!\n&\u0016", "biOgR");
        EnumEnchantmentType.I[0x6D ^ 0x6B] = I("5\u000e9\u001e\u0002,", "bKxNM");
        EnumEnchantmentType.I[0x58 ^ 0x5F] = I("7\u0004\u0013> !", "sMTye");
        EnumEnchantmentType.I[0xD ^ 0x5] = I("\u001e.\u0018\u001e9\u0016 \u0014\u0004?\u001c", "XgKVp");
        EnumEnchantmentType.I[0x80 ^ 0x89] = I("'\u001a\t9=$\n\u0000=", "eHLxv");
        EnumEnchantmentType.I[0x35 ^ 0x3F] = I("0 \r", "roZJR");
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
            if (3 != 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public boolean canEnchantItem(final Item item) {
        if (this == EnumEnchantmentType.ALL) {
            return " ".length() != 0;
        }
        if (this == EnumEnchantmentType.BREAKABLE && item.isDamageable()) {
            return " ".length() != 0;
        }
        if (!(item instanceof ItemArmor)) {
            int n;
            if (item instanceof ItemSword) {
                if (this == EnumEnchantmentType.WEAPON) {
                    n = " ".length();
                    "".length();
                    if (-1 == 4) {
                        throw null;
                    }
                }
                else {
                    n = "".length();
                    "".length();
                    if (4 < 3) {
                        throw null;
                    }
                }
            }
            else if (item instanceof ItemTool) {
                if (this == EnumEnchantmentType.DIGGER) {
                    n = " ".length();
                    "".length();
                    if (false) {
                        throw null;
                    }
                }
                else {
                    n = "".length();
                    "".length();
                    if (2 <= 0) {
                        throw null;
                    }
                }
            }
            else if (item instanceof ItemBow) {
                if (this == EnumEnchantmentType.BOW) {
                    n = " ".length();
                    "".length();
                    if (0 >= 1) {
                        throw null;
                    }
                }
                else {
                    n = "".length();
                    "".length();
                    if (3 < 2) {
                        throw null;
                    }
                }
            }
            else if (item instanceof ItemFishingRod) {
                if (this == EnumEnchantmentType.FISHING_ROD) {
                    n = " ".length();
                    "".length();
                    if (3 < 3) {
                        throw null;
                    }
                }
                else {
                    n = "".length();
                    "".length();
                    if (1 < 0) {
                        throw null;
                    }
                }
            }
            else {
                n = "".length();
            }
            return n != 0;
        }
        if (this == EnumEnchantmentType.ARMOR) {
            return " ".length() != 0;
        }
        final ItemArmor itemArmor = (ItemArmor)item;
        int n2;
        if (itemArmor.armorType == 0) {
            if (this == EnumEnchantmentType.ARMOR_HEAD) {
                n2 = " ".length();
                "".length();
                if (3 == 1) {
                    throw null;
                }
            }
            else {
                n2 = "".length();
                "".length();
                if (false) {
                    throw null;
                }
            }
        }
        else if (itemArmor.armorType == "  ".length()) {
            if (this == EnumEnchantmentType.ARMOR_LEGS) {
                n2 = " ".length();
                "".length();
                if (1 >= 4) {
                    throw null;
                }
            }
            else {
                n2 = "".length();
                "".length();
                if (true != true) {
                    throw null;
                }
            }
        }
        else if (itemArmor.armorType == " ".length()) {
            if (this == EnumEnchantmentType.ARMOR_TORSO) {
                n2 = " ".length();
                "".length();
                if (2 <= -1) {
                    throw null;
                }
            }
            else {
                n2 = "".length();
                "".length();
                if (-1 != -1) {
                    throw null;
                }
            }
        }
        else if (itemArmor.armorType == "   ".length()) {
            if (this == EnumEnchantmentType.ARMOR_FEET) {
                n2 = " ".length();
                "".length();
                if (-1 >= 4) {
                    throw null;
                }
            }
            else {
                n2 = "".length();
                "".length();
                if (3 < 1) {
                    throw null;
                }
            }
        }
        else {
            n2 = "".length();
        }
        return n2 != 0;
    }
}
