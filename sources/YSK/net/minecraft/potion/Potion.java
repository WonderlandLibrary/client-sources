package net.minecraft.potion;

import net.minecraft.entity.player.*;
import com.google.common.collect.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.entity.ai.attributes.*;

public class Potion
{
    private final boolean isBadEffect;
    public static final Potion jump;
    private String name;
    public static final Potion digSpeed;
    public static final Potion confusion;
    public static final Potion poison;
    public static final Potion wither;
    public static final Potion field_180146_G;
    private final int liquidColor;
    private static final String[] I;
    public static final Potion field_180151_b;
    public static final Potion damageBoost;
    public static final Potion resistance;
    public static final Potion absorption;
    public static final Potion field_180147_A;
    public static final Potion field_180149_C;
    public static final Potion blindness;
    public static final Potion moveSlowdown;
    public static final Potion waterBreathing;
    public static final Potion field_180143_D;
    public static final Potion invisibility;
    public static final Potion field_180145_F;
    public static final Potion heal;
    public static final Potion nightVision;
    public static final Potion fireResistance;
    public static final Potion[] potionTypes;
    private double effectiveness;
    private static final Map<ResourceLocation, Potion> field_180150_I;
    public static final Potion harm;
    public static final Potion hunger;
    public static final Potion field_180148_B;
    public static final Potion moveSpeed;
    private boolean usable;
    public final int id;
    private int statusIconIndex;
    public static final Potion regeneration;
    public static final Potion weakness;
    public static final Potion healthBoost;
    public static final Potion field_180153_z;
    public static final Potion field_180144_E;
    public static final Potion saturation;
    private final Map<IAttribute, AttributeModifier> attributeModifierMap;
    public static final Potion digSlowdown;
    
    public void performEffect(final EntityLivingBase entityLivingBase, final int n) {
        if (this.id == Potion.regeneration.id) {
            if (entityLivingBase.getHealth() < entityLivingBase.getMaxHealth()) {
                entityLivingBase.heal(1.0f);
                "".length();
                if (-1 != -1) {
                    throw null;
                }
            }
        }
        else if (this.id == Potion.poison.id) {
            if (entityLivingBase.getHealth() > 1.0f) {
                entityLivingBase.attackEntityFrom(DamageSource.magic, 1.0f);
                "".length();
                if (0 >= 2) {
                    throw null;
                }
            }
        }
        else if (this.id == Potion.wither.id) {
            entityLivingBase.attackEntityFrom(DamageSource.wither, 1.0f);
            "".length();
            if (0 < 0) {
                throw null;
            }
        }
        else if (this.id == Potion.hunger.id && entityLivingBase instanceof EntityPlayer) {
            ((EntityPlayer)entityLivingBase).addExhaustion(0.025f * (n + " ".length()));
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        else if (this.id == Potion.saturation.id && entityLivingBase instanceof EntityPlayer) {
            if (!entityLivingBase.worldObj.isRemote) {
                ((EntityPlayer)entityLivingBase).getFoodStats().addStats(n + " ".length(), 1.0f);
                "".length();
                if (true != true) {
                    throw null;
                }
            }
        }
        else if ((this.id != Potion.heal.id || entityLivingBase.isEntityUndead()) && (this.id != Potion.harm.id || !entityLivingBase.isEntityUndead())) {
            if ((this.id == Potion.harm.id && !entityLivingBase.isEntityUndead()) || (this.id == Potion.heal.id && entityLivingBase.isEntityUndead())) {
                entityLivingBase.attackEntityFrom(DamageSource.magic, (0x5F ^ 0x59) << n);
                "".length();
                if (4 <= 0) {
                    throw null;
                }
            }
        }
        else {
            entityLivingBase.heal(Math.max((0xB ^ 0xF) << n, "".length()));
        }
    }
    
    public static Potion getPotionFromResourceLocation(final String s) {
        return Potion.field_180150_I.get(new ResourceLocation(s));
    }
    
    public boolean isUsable() {
        return this.usable;
    }
    
    public double getEffectiveness() {
        return this.effectiveness;
    }
    
    protected Potion setEffectiveness(final double effectiveness) {
        this.effectiveness = effectiveness;
        return this;
    }
    
    static {
        I();
        potionTypes = new Potion[0x34 ^ 0x14];
        field_180150_I = Maps.newHashMap();
        field_180151_b = null;
        moveSpeed = new Potion(" ".length(), new ResourceLocation(Potion.I["".length()]), "".length() != 0, 7357574 + 6800769 - 6822841 + 835960).setPotionName(Potion.I[" ".length()]).setIconIndex("".length(), "".length()).registerPotionAttributeModifier(SharedMonsterAttributes.movementSpeed, Potion.I["  ".length()], 0.20000000298023224, "  ".length());
        moveSlowdown = new Potion("  ".length(), new ResourceLocation(Potion.I["   ".length()]), " ".length() != 0, 1250134 + 718602 + 2466433 + 1490848).setPotionName(Potion.I[0x37 ^ 0x33]).setIconIndex(" ".length(), "".length()).registerPotionAttributeModifier(SharedMonsterAttributes.movementSpeed, Potion.I[0x42 ^ 0x47], -0.15000000596046448, "  ".length());
        digSpeed = new Potion("   ".length(), new ResourceLocation(Potion.I[0x90 ^ 0x96]), "".length() != 0, 669846 + 9127076 - 7545307 + 12018916).setPotionName(Potion.I[0x8B ^ 0x8C]).setIconIndex("  ".length(), "".length()).setEffectiveness(1.5);
        digSlowdown = new Potion(0x51 ^ 0x55, new ResourceLocation(Potion.I[0x1B ^ 0x13]), " ".length() != 0, 1150707 + 3291410 - 213871 + 638337).setPotionName(Potion.I[0x21 ^ 0x28]).setIconIndex("   ".length(), "".length());
        damageBoost = new PotionAttackDamage(0x4C ^ 0x49, new ResourceLocation(Potion.I[0xA7 ^ 0xAD]), "".length() != 0, 762486 + 4032151 + 1378726 + 3469680).setPotionName(Potion.I[0x7A ^ 0x71]).setIconIndex(0x43 ^ 0x47, "".length()).registerPotionAttributeModifier(SharedMonsterAttributes.attackDamage, Potion.I[0xAA ^ 0xA6], 2.5, "  ".length());
        heal = new PotionHealth(0x20 ^ 0x26, new ResourceLocation(Potion.I[0xCC ^ 0xC1]), "".length() != 0, 11899755 + 10945322 - 10546106 + 3963208).setPotionName(Potion.I[0x60 ^ 0x6E]);
        harm = new PotionHealth(0x9B ^ 0x9C, new ResourceLocation(Potion.I[0x3C ^ 0x33]), " ".length() != 0, 2081824 + 401200 + 1600834 + 309623).setPotionName(Potion.I[0x8C ^ 0x9C]);
        jump = new Potion(0x95 ^ 0x9D, new ResourceLocation(Potion.I[0x25 ^ 0x34]), "".length() != 0, 708363 + 1357961 - 153595 + 380851).setPotionName(Potion.I[0x46 ^ 0x54]).setIconIndex("  ".length(), " ".length());
        confusion = new Potion(0x4C ^ 0x45, new ResourceLocation(Potion.I[0x3D ^ 0x2E]), " ".length() != 0, 4175649 + 3376089 - 3844696 + 1871016).setPotionName(Potion.I[0xB8 ^ 0xAC]).setIconIndex("   ".length(), " ".length()).setEffectiveness(0.25);
        regeneration = new Potion(0x53 ^ 0x59, new ResourceLocation(Potion.I[0x80 ^ 0x95]), "".length() != 0, 12849251 + 4275567 - 13743206 + 10076991).setPotionName(Potion.I[0x24 ^ 0x32]).setIconIndex(0xAF ^ 0xA8, "".length()).setEffectiveness(0.25);
        resistance = new Potion(0x2A ^ 0x21, new ResourceLocation(Potion.I[0x6A ^ 0x7D]), "".length() != 0, 3254328 + 4081788 - 1420391 + 4129005).setPotionName(Potion.I[0x72 ^ 0x6A]).setIconIndex(0x57 ^ 0x51, " ".length());
        fireResistance = new Potion(0x98 ^ 0x94, new ResourceLocation(Potion.I[0x7E ^ 0x67]), "".length() != 0, 4031824 + 4345981 + 1729503 + 4874382).setPotionName(Potion.I[0x86 ^ 0x9C]).setIconIndex(0x64 ^ 0x63, " ".length());
        waterBreathing = new Potion(0xA4 ^ 0xA9, new ResourceLocation(Potion.I[0x15 ^ 0xE]), "".length() != 0, 719015 + 2008617 - 234994 + 543163).setPotionName(Potion.I[0x42 ^ 0x5E]).setIconIndex("".length(), "  ".length());
        invisibility = new Potion(0xCF ^ 0xC1, new ResourceLocation(Potion.I[0xD9 ^ 0xC4]), "".length() != 0, 5004917 + 5802238 - 2755355 + 304954).setPotionName(Potion.I[0xA3 ^ 0xBD]).setIconIndex("".length(), " ".length());
        blindness = new Potion(0x6F ^ 0x60, new ResourceLocation(Potion.I[0x14 ^ 0xB]), " ".length() != 0, 1593738 + 860569 - 1695750 + 1281030).setPotionName(Potion.I[0x5A ^ 0x7A]).setIconIndex(0x20 ^ 0x25, " ".length()).setEffectiveness(0.25);
        nightVision = new Potion(0xD5 ^ 0xC5, new ResourceLocation(Potion.I[0x87 ^ 0xA6]), "".length() != 0, 1645978 + 5362 - 231739 + 620112).setPotionName(Potion.I[0x86 ^ 0xA4]).setIconIndex(0x3E ^ 0x3A, " ".length());
        hunger = new Potion(0xB4 ^ 0xA5, new ResourceLocation(Potion.I[0x8B ^ 0xA8]), " ".length() != 0, 5082041 + 4108094 - 7301416 + 3908740).setPotionName(Potion.I[0xE2 ^ 0xC6]).setIconIndex(" ".length(), " ".length());
        weakness = new PotionAttackDamage(0x5 ^ 0x17, new ResourceLocation(Potion.I[0xBE ^ 0x9B]), " ".length() != 0, 74170 + 108615 - 71263 + 4626854).setPotionName(Potion.I[0xD ^ 0x2B]).setIconIndex(0x9A ^ 0x9F, "".length()).registerPotionAttributeModifier(SharedMonsterAttributes.attackDamage, Potion.I[0x85 ^ 0xA2], 2.0, "".length());
        poison = new Potion(0x62 ^ 0x71, new ResourceLocation(Potion.I[0x9C ^ 0xB4]), " ".length() != 0, 2240895 + 3556336 - 1266691 + 618949).setPotionName(Potion.I[0x7A ^ 0x53]).setIconIndex(0x37 ^ 0x31, "".length()).setEffectiveness(0.25);
        wither = new Potion(0xC ^ 0x18, new ResourceLocation(Potion.I[0x75 ^ 0x5F]), " ".length() != 0, 652811 + 188642 + 2343023 + 299723).setPotionName(Potion.I[0xB4 ^ 0x9F]).setIconIndex(" ".length(), "  ".length()).setEffectiveness(0.25);
        healthBoost = new PotionHealthBoost(0xAB ^ 0xBE, new ResourceLocation(Potion.I[0x24 ^ 0x8]), "".length() != 0, 10339416 + 4844111 - 2244299 + 3345735).setPotionName(Potion.I[0x55 ^ 0x78]).setIconIndex("  ".length(), "  ".length()).registerPotionAttributeModifier(SharedMonsterAttributes.maxHealth, Potion.I[0x64 ^ 0x4A], 4.0, "".length());
        absorption = new PotionAbsorption(0x4B ^ 0x5D, new ResourceLocation(Potion.I[0xAB ^ 0x84]), "".length() != 0, 107926 + 2346566 - 1025644 + 1017141).setPotionName(Potion.I[0x46 ^ 0x76]).setIconIndex("  ".length(), "  ".length());
        saturation = new PotionHealth(0xAC ^ 0xBB, new ResourceLocation(Potion.I[0x7C ^ 0x4D]), "".length() != 0, 9204776 + 12011467 - 14266660 + 9312596).setPotionName(Potion.I[0x7C ^ 0x4E]);
        field_180153_z = null;
        field_180147_A = null;
        field_180148_B = null;
        field_180149_C = null;
        field_180143_D = null;
        field_180144_E = null;
        field_180145_F = null;
        field_180146_G = null;
    }
    
    public String getName() {
        return this.name;
    }
    
    public boolean isInstant() {
        return "".length() != 0;
    }
    
    protected Potion setIconIndex(final int n, final int n2) {
        this.statusIconIndex = n + n2 * (0xF ^ 0x7);
        return this;
    }
    
    public Potion setPotionName(final String name) {
        this.name = name;
        return this;
    }
    
    public Map<IAttribute, AttributeModifier> getAttributeModifierMap() {
        return this.attributeModifierMap;
    }
    
    public boolean hasStatusIcon() {
        if (this.statusIconIndex >= 0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public static String getDurationString(final PotionEffect potionEffect) {
        if (potionEffect.getIsPotionDurationMax()) {
            return Potion.I[0x51 ^ 0x65];
        }
        return StringUtils.ticksToElapsedTime(potionEffect.getDuration());
    }
    
    protected Potion(final int id, final ResourceLocation resourceLocation, final boolean isBadEffect, final int liquidColor) {
        this.attributeModifierMap = (Map<IAttribute, AttributeModifier>)Maps.newHashMap();
        this.name = Potion.I[0x4A ^ 0x79];
        this.statusIconIndex = -" ".length();
        this.id = id;
        Potion.potionTypes[id] = this;
        Potion.field_180150_I.put(resourceLocation, this);
        this.isBadEffect = isBadEffect;
        if (isBadEffect) {
            this.effectiveness = 0.5;
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        else {
            this.effectiveness = 1.0;
        }
        this.liquidColor = liquidColor;
    }
    
    public static Set<ResourceLocation> func_181168_c() {
        return Potion.field_180150_I.keySet();
    }
    
    public boolean isReady(final int n, final int n2) {
        if (this.id == Potion.regeneration.id) {
            final int n3 = (0x58 ^ 0x6A) >> n2;
            int n4;
            if (n3 > 0) {
                if (n % n3 == 0) {
                    n4 = " ".length();
                    "".length();
                    if (2 <= -1) {
                        throw null;
                    }
                }
                else {
                    n4 = "".length();
                    "".length();
                    if (0 < -1) {
                        throw null;
                    }
                }
            }
            else {
                n4 = " ".length();
            }
            return n4 != 0;
        }
        if (this.id == Potion.poison.id) {
            final int n5 = (0xB7 ^ 0xAE) >> n2;
            int n6;
            if (n5 > 0) {
                if (n % n5 == 0) {
                    n6 = " ".length();
                    "".length();
                    if (1 >= 3) {
                        throw null;
                    }
                }
                else {
                    n6 = "".length();
                    "".length();
                    if (3 == 4) {
                        throw null;
                    }
                }
            }
            else {
                n6 = " ".length();
            }
            return n6 != 0;
        }
        if (this.id == Potion.wither.id) {
            final int n7 = (0x5C ^ 0x74) >> n2;
            int n8;
            if (n7 > 0) {
                if (n % n7 == 0) {
                    n8 = " ".length();
                    "".length();
                    if (2 != 2) {
                        throw null;
                    }
                }
                else {
                    n8 = "".length();
                    "".length();
                    if (-1 >= 4) {
                        throw null;
                    }
                }
            }
            else {
                n8 = " ".length();
            }
            return n8 != 0;
        }
        if (this.id == Potion.hunger.id) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public void affectEntity(final Entity entity, final Entity entity2, final EntityLivingBase entityLivingBase, final int n, final double n2) {
        if ((this.id != Potion.heal.id || entityLivingBase.isEntityUndead()) && (this.id != Potion.harm.id || !entityLivingBase.isEntityUndead())) {
            if ((this.id == Potion.harm.id && !entityLivingBase.isEntityUndead()) || (this.id == Potion.heal.id && entityLivingBase.isEntityUndead())) {
                final int n3 = (int)(n2 * ((0x5F ^ 0x59) << n) + 0.5);
                if (entity == null) {
                    entityLivingBase.attackEntityFrom(DamageSource.magic, n3);
                    "".length();
                    if (1 < -1) {
                        throw null;
                    }
                }
                else {
                    entityLivingBase.attackEntityFrom(DamageSource.causeIndirectMagicDamage(entity, entity2), n3);
                    "".length();
                    if (1 >= 4) {
                        throw null;
                    }
                }
            }
        }
        else {
            entityLivingBase.heal((int)(n2 * ((0x1E ^ 0x1A) << n) + 0.5));
        }
    }
    
    public int getLiquidColor() {
        return this.liquidColor;
    }
    
    public boolean isBadEffect() {
        return this.isBadEffect;
    }
    
    public int getStatusIconIndex() {
        return this.statusIconIndex;
    }
    
    public Potion registerPotionAttributeModifier(final IAttribute attribute, final String s, final double n, final int n2) {
        this.attributeModifierMap.put(attribute, new AttributeModifier(UUID.fromString(s), this.getName(), n, n2));
        return this;
    }
    
    private static void I() {
        (I = new String[0x48 ^ 0x7E])["".length()] = I("\u0002;+/1", "qKNJU");
        Potion.I[" ".length()] = I("\n7\u0013\u0018#\u0014v\n\u001e:\u001f\u000b\u0017\u0014)\u001e", "zXgqL");
        Potion.I["  ".length()] = I("XY\u00046\u0016 ]s^dV^\u0007^cUQ}^nR]\u0007^e'_\u0003EoQ_uEdT", "ahEsW");
        Potion.I["   ".length()] = I("\u0017\u0000'\u0002;\u0001\u001f;", "dlHuU");
        Potion.I[0xB8 ^ 0xBC] = I(")?\u0017\r\u00157~\u000e\u000b\f<\u0003\u000f\u000b\r=?\u0014\n", "YPcdz");
        Potion.I[0x89 ^ 0x8C] = I("[U\u007f]\u001c)Q\nGo/!wGl\\W\u007fGaXT\nGm]P\f[\u001e]R\u007fRa\\", "ldOjX");
        Potion.I[0xD ^ 0xB] = I("*\u0015 \"0", "BtSVU");
        Potion.I[0x33 ^ 0x34] = I("\u001e\u00180\u0010\u0004\u0000Y \u0010\f=\u0007!\u001c\u000f", "nwDyk");
        Potion.I[0x3E ^ 0x36] = I(" .#*\u000f*\u0018+\"\u0015$ 8&", "MGMCa");
        Potion.I[0xA5 ^ 0xAC] = I("\u0003\b\u000e9+\u001dI\u001e9# \u000b\u0015'\u0000\u001c\u0010\u0014", "sgzPD");
        Potion.I[0x2C ^ 0x26] = I("\u001a=$\u0010 \u000e=>", "iIVuN");
        Potion.I[0x61 ^ 0x6A] = I("\u001d\u0002\"9>\u0003C21<\f\n3\u0012>\u0002\u001e\"", "mmVPQ");
        Potion.I[0x42 ^ 0x4E] = I("\u007fEK\u001eUyGGwT\bGCwV\u000fDJwZ\b36w!{2Ai#\u007f57m#p", "IqsZb");
        Potion.I[0x2C ^ 0x21] = I("\u00009\u001e\u00151\u0007#2\t5\b;\u0019\t", "iWmaP");
        Potion.I[0xB7 ^ 0xB9] = I("\u0019\"\u001f\u0005\u001d\u0007c\u0003\t\u0013\u0005", "iMklr");
        Potion.I[0x31 ^ 0x3E] = I("\u0003:\u001c\u0004.\u0004 0\u0014.\u00075\b\u0015", "jTopO");
        Potion.I[0x61 ^ 0x71] = I("\u0013?1\u0010#\r~-\u0018>\u000e", "cPEyL");
        Potion.I[0x32 ^ 0x23] = I("\b>:\u0003\u000b\u0000$8\u0000 ", "bKWsT");
        Potion.I[0x9D ^ 0x8F] = I("4 < &*a\"<$4", "DOHII");
        Potion.I[0x75 ^ 0x66] = I("4\u000e6\u0004';", "ZoCwB");
        Potion.I[0xAE ^ 0xBA] = I(")<='%7}*!$?&:'%7", "YSINJ");
        Potion.I[0x29 ^ 0x3C] = I("\u0011'\u0003\u00157\u00060\u0005\u00040\f,", "cBdpY");
        Potion.I[0x29 ^ 0x3F] = I("&\u001a\u00168\u001e8[\u00104\u00163\u001b\u0007#\u0010\"\u001c\r?", "VubQq");
        Potion.I[0x8D ^ 0x9A] = I("\u0004&=\f\u0003\u0002\" \u0006\u0015", "vCNep");
        Potion.I[0x24 ^ 0x3C] = I("#+>\"\u000b=j8.\u0017:7>*\n0!", "SDJKd");
        Potion.I[0x79 ^ 0x60] = I("+?3\u0017'?32\u001b\u000b97/\u0011\u001d", "MVArx");
        Potion.I[0x7 ^ 0x1D] = I("\u001c\u0018\u0015\u000e:\u0002Y\u0007\u000e'\t%\u0004\u0014<\u001f\u0003\u0000\t6\t", "lwagU");
        Potion.I[0x3E ^ 0x25] = I("\r+\u0001<0%(\u0007<#\u000e\"\u001c7%", "zJuYB");
        Potion.I[0xB2 ^ 0xAE] = I("%\u000b<+);J?#20\u0016\n0#4\u0010 +(2", "UdHBF");
        Potion.I[0xB4 ^ 0xA9] = I("*\u000b<\u0018**\u0007#\u001d07\u001c", "CeJqY");
        Potion.I[0x56 ^ 0x48] = I("$$1;):e,<0=8,0/8\"1+", "TKERF");
        Potion.I[0x66 ^ 0x79] = I("\u0003\u0018\b\u0003'\u000f\u0011\u0012\u001e", "atamC");
        Potion.I[0xAC ^ 0x8C] = I("\u0011\t\u00079\t\u000fH\u0011<\u000f\u000f\u0002\u001d5\u0015\u0012", "afsPf");
        Potion.I[0x16 ^ 0x37] = I("\u0016\u00115\u0006\u001f'\u000e;\u001d\u0002\u0017\u0016", "xxRnk");
        Potion.I[0x54 ^ 0x76] = I("\u001a(\u0001;\b\u0004i\u001b;\u0000\u00023#;\u0014\u0003(\u001b", "jGuRg");
        Potion.I[0x74 ^ 0x57] = I("\u000f98\u001d\b\u0015", "gLVzm");
        Potion.I[0xB6 ^ 0x92] = I("!!?\u0004\t?`#\u0018\b6+9", "QNKmf");
        Potion.I[0x45 ^ 0x60] = I(".\u0003\u0012\u001d\u0014<\u0015\u0000", "Yfsvz");
        Potion.I[0x4A ^ 0x6C] = I("\u0006\u0018\u00058\t\u0018Y\u00064\u0007\u001d\u0019\u0014\"\u0015", "vwqQf");
        Potion.I[0x7F ^ 0x58] = I("CXeZc3RjBa@\\\u0016BdH.\u0010Bi3\\\u0011BiH]b[hH(f-\u0015D", "qjSoP");
        Potion.I[0x94 ^ 0xBC] = I("\u001d,/7\u001a\u0003", "mCFDu");
        Potion.I[0x70 ^ 0x59] = I("\u001a\u001d;9\u0000\u0004\\??\u0006\u0019\u001d!", "jrOPo");
        Potion.I[0x48 ^ 0x62] = I("\u0007 \u0017?&\u0002", "pIcWC");
        Potion.I[0x88 ^ 0xA3] = I("1\u0007;<8/F8<#)\r=", "AhOUW");
        Potion.I[0x5C ^ 0x70] = I("%?\u0004&8%\u0005\u0007%#>.", "MZeJL");
        Potion.I[0x24 ^ 0x9] = I("4\u000e6&,*O**\"(\u0015*\r,+\u00126", "DaBOC");
        Potion.I[0x15 ^ 0x3B] = I("g=w\u000by\u00108s`xcAw`}d8\u0002`\u000bj@w`\ndH\u0002x\n\u0017<xt\n\u0011", "RyAMI");
        Potion.I[0x8B ^ 0xA4] = I(".\u00126\u001a\u0011?\u0004,\u001a\r", "OpEuc");
        Potion.I[0x7D ^ 0x4D] = I("\u0015\"\u0017\u001a\u000b\u000bc\u0002\u0011\u0017\n?\u0013\u0007\r\n#", "eMcsd");
        Potion.I[0x77 ^ 0x46] = I("\u001b#\u0010!8\t6\r;$", "hBdTJ");
        Potion.I[0x78 ^ 0x4A] = I("3#\u000e\u0003\u0007-b\t\u000b\u001c6>\u001b\u001e\u0001,\"", "CLzjh");
        Potion.I[0xF ^ 0x3C] = I("", "bFVfo");
        Potion.I[0x61 ^ 0x55] = I("|pTKD", "VZnan");
        Potion.I[0x72 ^ 0x47] = I("z", "ZYlVK");
    }
    
    public void applyAttributesModifiersToEntity(final EntityLivingBase entityLivingBase, final BaseAttributeMap baseAttributeMap, final int n) {
        final Iterator<Map.Entry<IAttribute, AttributeModifier>> iterator = this.attributeModifierMap.entrySet().iterator();
        "".length();
        if (2 == -1) {
            throw null;
        }
        while (iterator.hasNext()) {
            final Map.Entry<IAttribute, AttributeModifier> entry = iterator.next();
            final IAttributeInstance attributeInstance = baseAttributeMap.getAttributeInstance(entry.getKey());
            if (attributeInstance != null) {
                final AttributeModifier attributeModifier = entry.getValue();
                attributeInstance.removeModifier(attributeModifier);
                attributeInstance.applyModifier(new AttributeModifier(attributeModifier.getID(), String.valueOf(this.getName()) + Potion.I[0xF2 ^ 0xC7] + n, this.getAttributeModifierAmount(n, attributeModifier), attributeModifier.getOperation()));
            }
        }
    }
    
    public void removeAttributesModifiersFromEntity(final EntityLivingBase entityLivingBase, final BaseAttributeMap baseAttributeMap, final int n) {
        final Iterator<Map.Entry<IAttribute, AttributeModifier>> iterator = this.attributeModifierMap.entrySet().iterator();
        "".length();
        if (3 != 3) {
            throw null;
        }
        while (iterator.hasNext()) {
            final Map.Entry<IAttribute, AttributeModifier> entry = iterator.next();
            final IAttributeInstance attributeInstance = baseAttributeMap.getAttributeInstance(entry.getKey());
            if (attributeInstance != null) {
                attributeInstance.removeModifier(entry.getValue());
            }
        }
    }
    
    public double getAttributeModifierAmount(final int n, final AttributeModifier attributeModifier) {
        return attributeModifier.getAmount() * (n + " ".length());
    }
    
    public int getId() {
        return this.id;
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
            if (2 < 1) {
                throw null;
            }
        }
        return sb.toString();
    }
}
