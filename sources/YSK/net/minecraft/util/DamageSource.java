package net.minecraft.util;

import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;

public class DamageSource
{
    public static DamageSource magic;
    public static DamageSource onFire;
    public static DamageSource cactus;
    private float hungerDamage;
    public static DamageSource inWall;
    private static final String[] I;
    public static DamageSource anvil;
    public String damageType;
    public static DamageSource outOfWorld;
    private boolean magicDamage;
    private boolean difficultyScaled;
    private boolean projectile;
    public static DamageSource generic;
    private boolean damageIsAbsolute;
    public static DamageSource starve;
    public static DamageSource drown;
    private boolean fireDamage;
    private boolean isUnblockable;
    public static DamageSource fallingBlock;
    private boolean isDamageAllowedInCreativeMode;
    private boolean explosion;
    public static DamageSource fall;
    public static DamageSource wither;
    public static DamageSource inFire;
    public static DamageSource lightningBolt;
    public static DamageSource lava;
    
    public DamageSource setExplosion() {
        this.explosion = (" ".length() != 0);
        return this;
    }
    
    public static DamageSource causeThornsDamage(final Entity entity) {
        return new EntityDamageSource(DamageSource.I[0x46 ^ 0x50], entity).setIsThornsDamage().setMagicDamage();
    }
    
    public DamageSource setProjectile() {
        this.projectile = (" ".length() != 0);
        return this;
    }
    
    public static DamageSource setExplosionSource(final Explosion explosion) {
        DamageSource damageSource;
        if (explosion != null && explosion.getExplosivePlacedBy() != null) {
            damageSource = new EntityDamageSource(DamageSource.I[0x9C ^ 0x8B], explosion.getExplosivePlacedBy()).setDifficultyScaled().setExplosion();
            "".length();
            if (3 <= -1) {
                throw null;
            }
        }
        else {
            damageSource = new DamageSource(DamageSource.I[0x9C ^ 0x84]).setDifficultyScaled().setExplosion();
        }
        return damageSource;
    }
    
    public float getHungerDamage() {
        return this.hungerDamage;
    }
    
    protected DamageSource setDamageIsAbsolute() {
        this.damageIsAbsolute = (" ".length() != 0);
        this.hungerDamage = 0.0f;
        return this;
    }
    
    protected DamageSource setFireDamage() {
        this.fireDamage = (" ".length() != 0);
        return this;
    }
    
    protected DamageSource(final String damageType) {
        this.hungerDamage = 0.3f;
        this.damageType = damageType;
    }
    
    public static DamageSource causeMobDamage(final EntityLivingBase entityLivingBase) {
        return new EntityDamageSource(DamageSource.I[0x52 ^ 0x5D], entityLivingBase);
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
            if (2 <= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public IChatComponent getDeathMessage(final EntityLivingBase entityLivingBase) {
        final EntityLivingBase func_94060_bK = entityLivingBase.func_94060_bK();
        final String string = DamageSource.I[0x3B ^ 0x22] + this.damageType;
        final String string2 = String.valueOf(string) + DamageSource.I[0x42 ^ 0x58];
        ChatComponentTranslation chatComponentTranslation;
        if (func_94060_bK != null && StatCollector.canTranslate(string2)) {
            final String s;
            final Object[] array;
            chatComponentTranslation = new ChatComponentTranslation(s, array);
            s = string2;
            array = new Object["  ".length()];
            array["".length()] = entityLivingBase.getDisplayName();
            array[" ".length()] = func_94060_bK.getDisplayName();
            "".length();
            if (3 == 0) {
                throw null;
            }
        }
        else {
            final String s2;
            final Object[] array2;
            chatComponentTranslation = new ChatComponentTranslation(s2, array2);
            s2 = string;
            array2 = new Object[" ".length()];
            array2["".length()] = entityLivingBase.getDisplayName();
        }
        return chatComponentTranslation;
    }
    
    protected DamageSource setDamageBypassesArmor() {
        this.isUnblockable = (" ".length() != 0);
        this.hungerDamage = 0.0f;
        return this;
    }
    
    public boolean isUnblockable() {
        return this.isUnblockable;
    }
    
    public static DamageSource causeIndirectMagicDamage(final Entity entity, final Entity entity2) {
        return new EntityDamageSourceIndirect(DamageSource.I[0x0 ^ 0x15], entity, entity2).setDamageBypassesArmor().setMagicDamage();
    }
    
    public String getDamageType() {
        return this.damageType;
    }
    
    public boolean isCreativePlayer() {
        final Entity entity = this.getEntity();
        if (entity instanceof EntityPlayer && ((EntityPlayer)entity).capabilities.isCreativeMode) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public boolean isMagicDamage() {
        return this.magicDamage;
    }
    
    protected DamageSource setDamageAllowedInCreativeMode() {
        this.isDamageAllowedInCreativeMode = (" ".length() != 0);
        return this;
    }
    
    public Entity getSourceOfDamage() {
        return this.getEntity();
    }
    
    public static DamageSource causeThrownDamage(final Entity entity, final Entity entity2) {
        return new EntityDamageSourceIndirect(DamageSource.I[0x71 ^ 0x65], entity, entity2).setProjectile();
    }
    
    public boolean isProjectile() {
        return this.projectile;
    }
    
    public boolean canHarmInCreative() {
        return this.isDamageAllowedInCreativeMode;
    }
    
    public DamageSource setDifficultyScaled() {
        this.difficultyScaled = (" ".length() != 0);
        return this;
    }
    
    public static DamageSource causeArrowDamage(final EntityArrow entityArrow, final Entity entity) {
        return new EntityDamageSourceIndirect(DamageSource.I[0x74 ^ 0x65], entityArrow, entity).setProjectile();
    }
    
    public boolean isDifficultyScaled() {
        return this.difficultyScaled;
    }
    
    private static void I() {
        (I = new String[0x74 ^ 0x6F])["".length()] = I("\b\u001f>\u00135\u0004", "aqxzG");
        DamageSource.I[" ".length()] = I("\u001b%\u000b\u00121\u0019%\u0002\u001d\u0007\u0018 \u0018", "wLlzE");
        DamageSource.I["  ".length()] = I("*\u0003\u001f%\u0014 ", "EmYLf");
        DamageSource.I["   ".length()] = I("( \u0002&", "DAtGY");
        DamageSource.I[0x85 ^ 0x81] = I("\u0019\u0006\u00079$\u001c", "phPXH");
        DamageSource.I[0x69 ^ 0x6C] = I(",1:\u0016\u0000", "HCUan");
        DamageSource.I[0x93 ^ 0x95] = I("\u001c-\f\u001b?\n", "oYmiI");
        DamageSource.I[0x41 ^ 0x46] = I("\u000f\u001b\f\u0018\u0002\u001f", "lzolw");
        DamageSource.I[0xBA ^ 0xB2] = I("\u00037\u0006\u0015", "eVjyI");
        DamageSource.I[0x8 ^ 0x1] = I("\u0003&.\u001d\u0001;<(>\u0003", "lSZRg");
        DamageSource.I[0xBF ^ 0xB5] = I("\u001e\u000b\"\n<\u0010\r", "ynLoN");
        DamageSource.I[0x9B ^ 0x90] = I("#\t\u0000,\u0007", "NhgEd");
        DamageSource.I[0x3 ^ 0xF] = I("/\u00113\u0011\u0011*", "XxGyt");
        DamageSource.I[0xE ^ 0x3] = I("\u0016\u000b\u001a( ", "welAL");
        DamageSource.I[0x93 ^ 0x9D] = I("6\"&\u00063>$\b\u000653(", "PCJjZ");
        DamageSource.I[0x13 ^ 0x1C] = I("/\u001e3", "BqQkf");
        DamageSource.I[0xA1 ^ 0xB1] = I("?\u001e\u0000/'=", "OraVB");
        DamageSource.I[0x88 ^ 0x99] = I("8\u0003\u001c\u0004\u001d", "Yqnkj");
        DamageSource.I[0x8 ^ 0x1A] = I("\u0004\u0004\u0012\"\u0004\u000e", "kjTKv");
        DamageSource.I[0x47 ^ 0x54] = I(",>\u001e\u0001\u0011+;\u0000", "JWlds");
        DamageSource.I[0x15 ^ 0x1] = I("\u0006#>\u001e\u0012\u001c", "rKLqe");
        DamageSource.I[0xA4 ^ 0xB1] = I("\u0005*5?\u001c\t'%\u001b\u000f\u000b-2", "lDQVn");
        DamageSource.I[0x3C ^ 0x2A] = I(";*\u001d\u0001\u000f<", "OBrsa");
        DamageSource.I[0xE ^ 0x19] = I("\u0016>\u0011>7\u0000/\u000e<v\u0003*\u0000+=\u0001", "sFaRX");
        DamageSource.I[0x55 ^ 0x4D] = I("\t\u0014\u0000 \r\u001f\u0005\u001f\"", "llpLb");
        DamageSource.I[0x76 ^ 0x6F] = I("2$\u00136\u0001x \u00066\b5*\\", "VArBi");
        DamageSource.I[0x5B ^ 0x41] = I("]%6+5\u0016'", "sUZJL");
    }
    
    public static DamageSource causeFireballDamage(final EntityFireball entityFireball, final Entity entity) {
        DamageSource damageSource;
        if (entity == null) {
            damageSource = new EntityDamageSourceIndirect(DamageSource.I[0x68 ^ 0x7A], entityFireball, entityFireball).setFireDamage().setProjectile();
            "".length();
            if (3 <= -1) {
                throw null;
            }
        }
        else {
            damageSource = new EntityDamageSourceIndirect(DamageSource.I[0x80 ^ 0x93], entityFireball, entity).setFireDamage().setProjectile();
        }
        return damageSource;
    }
    
    public boolean isDamageAbsolute() {
        return this.damageIsAbsolute;
    }
    
    static {
        I();
        DamageSource.inFire = new DamageSource(DamageSource.I["".length()]).setFireDamage();
        DamageSource.lightningBolt = new DamageSource(DamageSource.I[" ".length()]);
        DamageSource.onFire = new DamageSource(DamageSource.I["  ".length()]).setDamageBypassesArmor().setFireDamage();
        DamageSource.lava = new DamageSource(DamageSource.I["   ".length()]).setFireDamage();
        DamageSource.inWall = new DamageSource(DamageSource.I[0x9D ^ 0x99]).setDamageBypassesArmor();
        DamageSource.drown = new DamageSource(DamageSource.I[0x8E ^ 0x8B]).setDamageBypassesArmor();
        DamageSource.starve = new DamageSource(DamageSource.I[0xC2 ^ 0xC4]).setDamageBypassesArmor().setDamageIsAbsolute();
        DamageSource.cactus = new DamageSource(DamageSource.I[0xAA ^ 0xAD]);
        DamageSource.fall = new DamageSource(DamageSource.I[0x6 ^ 0xE]).setDamageBypassesArmor();
        DamageSource.outOfWorld = new DamageSource(DamageSource.I[0x53 ^ 0x5A]).setDamageBypassesArmor().setDamageAllowedInCreativeMode();
        DamageSource.generic = new DamageSource(DamageSource.I[0xA7 ^ 0xAD]).setDamageBypassesArmor();
        DamageSource.magic = new DamageSource(DamageSource.I[0x4B ^ 0x40]).setDamageBypassesArmor().setMagicDamage();
        DamageSource.wither = new DamageSource(DamageSource.I[0x66 ^ 0x6A]).setDamageBypassesArmor();
        DamageSource.anvil = new DamageSource(DamageSource.I[0x87 ^ 0x8A]);
        DamageSource.fallingBlock = new DamageSource(DamageSource.I[0x39 ^ 0x37]);
    }
    
    public boolean isExplosion() {
        return this.explosion;
    }
    
    public boolean isFireDamage() {
        return this.fireDamage;
    }
    
    public DamageSource setMagicDamage() {
        this.magicDamage = (" ".length() != 0);
        return this;
    }
    
    public Entity getEntity() {
        return null;
    }
    
    public static DamageSource causePlayerDamage(final EntityPlayer entityPlayer) {
        return new EntityDamageSource(DamageSource.I[0x45 ^ 0x55], entityPlayer);
    }
}
