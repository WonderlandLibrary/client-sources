package net.minecraft.enchantment;

import net.minecraft.entity.*;
import net.minecraft.util.*;
import com.google.common.collect.*;
import java.util.*;
import net.minecraft.item.*;

public abstract class Enchantment
{
    public static final Enchantment thorns;
    public static final Enchantment smite;
    public static final Enchantment projectileProtection;
    public static final Enchantment looting;
    public static final Enchantment knockback;
    public final int effectId;
    public static final Enchantment featherFalling;
    public static final Enchantment fortune;
    public static final Enchantment blastProtection;
    public static final Enchantment depthStrider;
    public static final Enchantment infinity;
    public static final Enchantment fireAspect;
    public static final Enchantment luckOfTheSea;
    protected String name;
    public static final Enchantment protection;
    public EnumEnchantmentType type;
    public static final Enchantment efficiency;
    public static final Enchantment respiration;
    public static final Enchantment fireProtection;
    public static final Enchantment silkTouch;
    public static final Enchantment sharpness;
    public static final Enchantment power;
    public static final Enchantment punch;
    private static final String[] I;
    public static final Enchantment baneOfArthropods;
    private static final Enchantment[] enchantmentsList;
    private final int weight;
    public static final Enchantment aquaAffinity;
    public static final Enchantment[] enchantmentsBookList;
    public static final Enchantment flame;
    private static final Map<ResourceLocation, Enchantment> locationEnchantments;
    public static final Enchantment lure;
    public static final Enchantment unbreaking;
    
    public float calcDamageByCreature(final int n, final EnumCreatureAttribute enumCreatureAttribute) {
        return 0.0f;
    }
    
    public String getTranslatedName(final int n) {
        return String.valueOf(StatCollector.translateToLocal(this.getName())) + Enchantment.I[0x86 ^ 0x9D] + StatCollector.translateToLocal(Enchantment.I[0x67 ^ 0x7B] + n);
    }
    
    public String getName() {
        return Enchantment.I[0x36 ^ 0x2C] + this.name;
    }
    
    public void onEntityDamaged(final EntityLivingBase entityLivingBase, final Entity entity, final int n) {
    }
    
    public void onUserHurt(final EntityLivingBase entityLivingBase, final Entity entity, final int n) {
    }
    
    public static Set<ResourceLocation> func_181077_c() {
        return Enchantment.locationEnchantments.keySet();
    }
    
    private static void I() {
        (I = new String[0x98 ^ 0x85])["".length()] = I("\"\u0015*\u0015\u000f1\u0013,\u000e\u0004", "RgEaj");
        Enchantment.I[" ".length()] = I("(\u0013<\u0011\u000b>\b!\u00001-\u000e'\u001b:", "NzNtT");
        Enchantment.I["  ".length()] = I("\u000b=\r\u0010\u0005\b*3\u0002\f\u00014\u0005\n\n", "mXldm");
        Enchantment.I["   ".length()] = I("\f=\u0019\u001601!\n\n0\u000b2\f\f+\u0000", "nQxeD");
        Enchantment.I[0xA8 ^ 0xAC] = I("\u0005!\u0017!\u0006\u0016'\u0011'\u0006*#\n$\u0017\u00100\f\"\f\u001b", "uSxKc");
        Enchantment.I[0xBD ^ 0xB8] = I("'\u00064\u001d>'\u00023\u00048;", "UcGmW");
        Enchantment.I[0x34 ^ 0x32] = I("#\u0014\u001d.\n#\u0003\u000e&;+\u0011\u0011", "BehOU");
        Enchantment.I[0x6D ^ 0x6A] = I("\u0018*\u001d\b'\u001f", "lBrzI");
        Enchantment.I[0xCE ^ 0xC6] = I("\b(\u0018\u0002\u000b3>\u001c\u0004\n\b(\u001a", "lMhvc");
        Enchantment.I[0x37 ^ 0x3E] = I("\u0007!5\u00192\u001a,'\u0018", "tITkB");
        Enchantment.I[0xCE ^ 0xC4] = I("\u0015?\"\u000e\u0007", "fRKzb");
        Enchantment.I[0xB7 ^ 0xBC] = I("\b\u0016\u001a\u0014\u0012\u0005\u0011+\u0010?\u001e\u001f\u0006\u001e=\u0005\u0013\u0007", "jwtqM");
        Enchantment.I[0x30 ^ 0x3C] = I("/-\u001e,$&\"\u0012$", "DCqOO");
        Enchantment.I[0x1A ^ 0x17] = I(".(\u0003<\u001b)2\u0001<'<", "HAqYD");
        Enchantment.I[0x91 ^ 0x9F] = I(">\u001b\n>9<\u0013", "RteJP");
        Enchantment.I[0x5A ^ 0x55] = I("5$\u0010\u0019$9'\u0018\u0013>", "PBvpG");
        Enchantment.I[0x76 ^ 0x66] = I("0 %\u001d+7&<\u0015\u001c", "CIIvt");
        Enchantment.I[0x3F ^ 0x2E] = I(":\u0007\t\u0005\u0003.\u0002\u0002\u0019\u0001", "Oikwf");
        Enchantment.I[0x8 ^ 0x1A] = I("'\u000e\u0019\u0002\u0005/\u0004", "Aakvp");
        Enchantment.I[0x8D ^ 0x9E] = I("=+?\u0003&", "MDHfT");
        Enchantment.I[0x9E ^ 0x8A] = I("\u0007\u001e$/\u0000", "wkJLh");
        Enchantment.I[0x66 ^ 0x73] = I("$\u0016.\u000b\u000f", "BzOfj");
        Enchantment.I[0x71 ^ 0x67] = I("/\u000b*/\u0002/\u00115", "FeLFl");
        Enchantment.I[0xA5 ^ 0xB2] = I(" \u0017\u000f.+#\u000431\u001c)=\u001f \u0015", "LblEt");
        Enchantment.I[0x5C ^ 0x44] = I("(93\u0003", "DLAfx");
        Enchantment.I[0x4C ^ 0x55] = I(">8:\u001f\u001d\u0019,>\u0016T\u001f#)\u001b\u0015\u00149'\u0016\u001a\u000em#\u0017U", "zMJst");
        Enchantment.I[0x5E ^ 0x44] = I("\b)%\u0019\u001b\u00033+\u0014\u0014\u0019i", "mGFqz");
        Enchantment.I[0xA9 ^ 0xB2] = I("d", "DByiB");
        Enchantment.I[0x46 ^ 0x5A] = I("<\u000b.\u0001*7\u0011 \f%-K!\f=<\tc", "YeMiK");
    }
    
    public int getMaxLevel() {
        return " ".length();
    }
    
    public Enchantment setName(final String name) {
        this.name = name;
        return this;
    }
    
    public int getWeight() {
        return this.weight;
    }
    
    protected Enchantment(final int effectId, final ResourceLocation resourceLocation, final int weight, final EnumEnchantmentType type) {
        this.effectId = effectId;
        this.weight = weight;
        this.type = type;
        if (Enchantment.enchantmentsList[effectId] != null) {
            throw new IllegalArgumentException(Enchantment.I[0x66 ^ 0x7F]);
        }
        Enchantment.enchantmentsList[effectId] = this;
        Enchantment.locationEnchantments.put(resourceLocation, this);
    }
    
    public int getMaxEnchantability(final int n) {
        return this.getMinEnchantability(n) + (0xE ^ 0xB);
    }
    
    public boolean canApplyTogether(final Enchantment enchantment) {
        if (this != enchantment) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public int getMinLevel() {
        return " ".length();
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
            if (1 >= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public static Enchantment getEnchantmentByLocation(final String s) {
        return Enchantment.locationEnchantments.get(new ResourceLocation(s));
    }
    
    public int calcModifierDamage(final int n, final DamageSource damageSource) {
        return "".length();
    }
    
    public int getMinEnchantability(final int n) {
        return " ".length() + n * (0x82 ^ 0x88);
    }
    
    static {
        I();
        enchantmentsList = new Enchantment[187 + 125 - 118 + 62];
        locationEnchantments = Maps.newHashMap();
        protection = new EnchantmentProtection("".length(), new ResourceLocation(Enchantment.I["".length()]), 0x5D ^ 0x57, "".length());
        fireProtection = new EnchantmentProtection(" ".length(), new ResourceLocation(Enchantment.I[" ".length()]), 0xA9 ^ 0xAC, " ".length());
        featherFalling = new EnchantmentProtection("  ".length(), new ResourceLocation(Enchantment.I["  ".length()]), 0x72 ^ 0x77, "  ".length());
        blastProtection = new EnchantmentProtection("   ".length(), new ResourceLocation(Enchantment.I["   ".length()]), "  ".length(), "   ".length());
        projectileProtection = new EnchantmentProtection(0x7E ^ 0x7A, new ResourceLocation(Enchantment.I[0x9F ^ 0x9B]), 0x4B ^ 0x4E, 0x60 ^ 0x64);
        respiration = new EnchantmentOxygen(0xB ^ 0xE, new ResourceLocation(Enchantment.I[0x76 ^ 0x73]), "  ".length());
        aquaAffinity = new EnchantmentWaterWorker(0x0 ^ 0x6, new ResourceLocation(Enchantment.I[0x61 ^ 0x67]), "  ".length());
        thorns = new EnchantmentThorns(0xB4 ^ 0xB3, new ResourceLocation(Enchantment.I[0xAA ^ 0xAD]), " ".length());
        depthStrider = new EnchantmentWaterWalker(0x12 ^ 0x1A, new ResourceLocation(Enchantment.I[0xCF ^ 0xC7]), "  ".length());
        sharpness = new EnchantmentDamage(0xB3 ^ 0xA3, new ResourceLocation(Enchantment.I[0xAD ^ 0xA4]), 0x49 ^ 0x43, "".length());
        smite = new EnchantmentDamage(0x56 ^ 0x47, new ResourceLocation(Enchantment.I[0x53 ^ 0x59]), 0x79 ^ 0x7C, " ".length());
        baneOfArthropods = new EnchantmentDamage(0x9C ^ 0x8E, new ResourceLocation(Enchantment.I[0xCD ^ 0xC6]), 0x27 ^ 0x22, "  ".length());
        knockback = new EnchantmentKnockback(0x39 ^ 0x2A, new ResourceLocation(Enchantment.I[0x5F ^ 0x53]), 0xAA ^ 0xAF);
        fireAspect = new EnchantmentFireAspect(0x22 ^ 0x36, new ResourceLocation(Enchantment.I[0x7C ^ 0x71]), "  ".length());
        looting = new EnchantmentLootBonus(0xA ^ 0x1F, new ResourceLocation(Enchantment.I[0x19 ^ 0x17]), "  ".length(), EnumEnchantmentType.WEAPON);
        efficiency = new EnchantmentDigging(0x3F ^ 0x1F, new ResourceLocation(Enchantment.I[0x8 ^ 0x7]), 0x32 ^ 0x38);
        silkTouch = new EnchantmentUntouching(0x6A ^ 0x4B, new ResourceLocation(Enchantment.I[0x9D ^ 0x8D]), " ".length());
        unbreaking = new EnchantmentDurability(0xE4 ^ 0xC6, new ResourceLocation(Enchantment.I[0x95 ^ 0x84]), 0x29 ^ 0x2C);
        fortune = new EnchantmentLootBonus(0x38 ^ 0x1B, new ResourceLocation(Enchantment.I[0x4 ^ 0x16]), "  ".length(), EnumEnchantmentType.DIGGER);
        power = new EnchantmentArrowDamage(0x26 ^ 0x16, new ResourceLocation(Enchantment.I[0x58 ^ 0x4B]), 0x1B ^ 0x11);
        punch = new EnchantmentArrowKnockback(0x3E ^ 0xF, new ResourceLocation(Enchantment.I[0x33 ^ 0x27]), "  ".length());
        flame = new EnchantmentArrowFire(0x5D ^ 0x6F, new ResourceLocation(Enchantment.I[0x3A ^ 0x2F]), "  ".length());
        infinity = new EnchantmentArrowInfinite(0x36 ^ 0x5, new ResourceLocation(Enchantment.I[0x33 ^ 0x25]), " ".length());
        luckOfTheSea = new EnchantmentLootBonus(0x33 ^ 0xE, new ResourceLocation(Enchantment.I[0x6C ^ 0x7B]), "  ".length(), EnumEnchantmentType.FISHING_ROD);
        lure = new EnchantmentFishingSpeed(0xAA ^ 0x94, new ResourceLocation(Enchantment.I[0x7A ^ 0x62]), "  ".length(), EnumEnchantmentType.FISHING_ROD);
        final ArrayList arrayList = Lists.newArrayList();
        final Enchantment[] enchantmentsList2;
        final int length = (enchantmentsList2 = Enchantment.enchantmentsList).length;
        int i = "".length();
        "".length();
        if (3 < 2) {
            throw null;
        }
        while (i < length) {
            final Enchantment enchantment = enchantmentsList2[i];
            if (enchantment != null) {
                arrayList.add(enchantment);
            }
            ++i;
        }
        enchantmentsBookList = arrayList.toArray(new Enchantment[arrayList.size()]);
    }
    
    public boolean canApply(final ItemStack itemStack) {
        return this.type.canEnchantItem(itemStack.getItem());
    }
    
    public static Enchantment getEnchantmentById(final int n) {
        Enchantment enchantment;
        if (n >= 0 && n < Enchantment.enchantmentsList.length) {
            enchantment = Enchantment.enchantmentsList[n];
            "".length();
            if (2 == 3) {
                throw null;
            }
        }
        else {
            enchantment = null;
        }
        return enchantment;
    }
}
