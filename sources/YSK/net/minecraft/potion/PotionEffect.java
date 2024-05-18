package net.minecraft.potion;

import net.minecraft.entity.*;
import org.apache.logging.log4j.*;
import net.minecraft.nbt.*;

public class PotionEffect
{
    private static final Logger LOGGER;
    private boolean isSplashPotion;
    private int duration;
    private int potionID;
    private static final String[] I;
    private boolean isAmbient;
    private boolean showParticles;
    private boolean isPotionDurationMax;
    private int amplifier;
    
    private static void I() {
        (I = new String[0xD2 ^ 0xC6])["".length()] = I("\u0003\f\u001e\u001bj:\u0001\u0003\u0000%3D\u0004\u0000%\"\b\u0013H%9\b\u000eH(2D\u0014\t&;\u0001\u0013H,8\u0016W\u0005+#\u0007\u001f\u0001$0D\u0012\u000e,2\u0007\u0003\u001bk", "WdwhJ");
        PotionEffect.I[" ".length()] = I("", "HjeDk");
        PotionEffect.I["  ".length()] = I("O.R", "oVrCB");
        PotionEffect.I["   ".length()] = I("kz\u0010\u0006\u0003&.=\u001c\u001f}z", "GZTsq");
        PotionEffect.I[0xF ^ 0xB] = I("Zl\u0005$\u0018\u00178(>\u0004Ll", "vLAQj");
        PotionEffect.I[0x7 ^ 0x2] = I("Ue6$\u0001\u00186\rnM\r7\u00101", "yEeTm");
        PotionEffect.I[0xD ^ 0xB] = I("Ya!20\u0001(\u0012?'\u0006{Q5#\u00192\u0014", "uAqSB");
        PotionEffect.I[0x7F ^ 0x78] = I("F", "nikfc");
        PotionEffect.I[0xB2 ^ 0xBA] = I("j", "CeOkG");
        PotionEffect.I[0x81 ^ 0x88] = I("->", "dZkhT");
        PotionEffect.I[0x1B ^ 0x11] = I("\"\u0018\u001e\r\f\u0005\u001c\u000b\u0013", "cunae");
        PotionEffect.I[0x44 ^ 0x4F] = I("\u000f;5\n \"!)", "KNGkT");
        PotionEffect.I[0x81 ^ 0x8D] = I("\r\u000e\u0003*<\"\u0017", "LcaCY");
        PotionEffect.I[0x57 ^ 0x5A] = I("\u0010\u0003(11\"\u00193/\u0002/\u000e4", "CkGFa");
        PotionEffect.I[0xCA ^ 0xC4] = I(". ", "gDQyY");
        PotionEffect.I[0xA7 ^ 0xA8] = I("\u0010\u000f8\u001f97\u000b-\u0001", "QbHsP");
        PotionEffect.I[0x9E ^ 0x8E] = I("\u0013\u00130\u0006\u001d>\t,", "WfBgi");
        PotionEffect.I[0x16 ^ 0x7] = I("\u001b\u001f\u0004\u001e-4\u0006", "ZrfwH");
        PotionEffect.I[0x85 ^ 0x97] = I("?1,<\u001d\r+7\".\u0000<0", "lYCKM");
        PotionEffect.I[0x7 ^ 0x14] = I("\u00152;;\u0006'( %5*?'", "FZTLV");
    }
    
    @Override
    public String toString() {
        final String s = PotionEffect.I[" ".length()];
        String s2;
        if (this.getAmplifier() > 0) {
            s2 = String.valueOf(this.getEffectName()) + PotionEffect.I["  ".length()] + (this.getAmplifier() + " ".length()) + PotionEffect.I["   ".length()] + this.getDuration();
            "".length();
            if (2 == -1) {
                throw null;
            }
        }
        else {
            s2 = String.valueOf(this.getEffectName()) + PotionEffect.I[0x8B ^ 0x8F] + this.getDuration();
        }
        if (this.isSplashPotion) {
            s2 = String.valueOf(s2) + PotionEffect.I[0x2E ^ 0x2B];
        }
        if (!this.showParticles) {
            s2 = String.valueOf(s2) + PotionEffect.I[0x40 ^ 0x46];
        }
        String string;
        if (Potion.potionTypes[this.potionID].isUsable()) {
            string = PotionEffect.I[0x6A ^ 0x6D] + s2 + PotionEffect.I[0xB9 ^ 0xB1];
            "".length();
            if (1 < -1) {
                throw null;
            }
        }
        else {
            string = s2;
        }
        return string;
    }
    
    private int deincrementDuration() {
        return this.duration -= " ".length();
    }
    
    public PotionEffect(final PotionEffect potionEffect) {
        this.potionID = potionEffect.potionID;
        this.duration = potionEffect.duration;
        this.amplifier = potionEffect.amplifier;
        this.isAmbient = potionEffect.isAmbient;
        this.showParticles = potionEffect.showParticles;
    }
    
    @Override
    public int hashCode() {
        return this.potionID;
    }
    
    public boolean getIsPotionDurationMax() {
        return this.isPotionDurationMax;
    }
    
    public void setPotionDurationMax(final boolean isPotionDurationMax) {
        this.isPotionDurationMax = isPotionDurationMax;
    }
    
    public void performEffect(final EntityLivingBase entityLivingBase) {
        if (this.duration > 0) {
            Potion.potionTypes[this.potionID].performEffect(entityLivingBase, this.amplifier);
        }
    }
    
    static {
        I();
        LOGGER = LogManager.getLogger();
    }
    
    public boolean getIsShowParticles() {
        return this.showParticles;
    }
    
    public int getAmplifier() {
        return this.amplifier;
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
    
    public PotionEffect(final int n, final int n2) {
        this(n, n2, "".length());
    }
    
    public NBTTagCompound writeCustomPotionEffectToNBT(final NBTTagCompound nbtTagCompound) {
        nbtTagCompound.setByte(PotionEffect.I[0xB6 ^ 0xBF], (byte)this.getPotionID());
        nbtTagCompound.setByte(PotionEffect.I[0x2C ^ 0x26], (byte)this.getAmplifier());
        nbtTagCompound.setInteger(PotionEffect.I[0x74 ^ 0x7F], this.getDuration());
        nbtTagCompound.setBoolean(PotionEffect.I[0xBA ^ 0xB6], this.getIsAmbient());
        nbtTagCompound.setBoolean(PotionEffect.I[0x42 ^ 0x4F], this.getIsShowParticles());
        return nbtTagCompound;
    }
    
    public int getDuration() {
        return this.duration;
    }
    
    public boolean onUpdate(final EntityLivingBase entityLivingBase) {
        if (this.duration > 0) {
            if (Potion.potionTypes[this.potionID].isReady(this.duration, this.amplifier)) {
                this.performEffect(entityLivingBase);
            }
            this.deincrementDuration();
        }
        if (this.duration > 0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public static PotionEffect readCustomPotionEffectFromNBT(final NBTTagCompound nbtTagCompound) {
        final byte byte1 = nbtTagCompound.getByte(PotionEffect.I[0x72 ^ 0x7C]);
        if (byte1 >= 0 && byte1 < Potion.potionTypes.length && Potion.potionTypes[byte1] != null) {
            final byte byte2 = nbtTagCompound.getByte(PotionEffect.I[0xB6 ^ 0xB9]);
            final int integer = nbtTagCompound.getInteger(PotionEffect.I[0xB6 ^ 0xA6]);
            final boolean boolean1 = nbtTagCompound.getBoolean(PotionEffect.I[0xB9 ^ 0xA8]);
            int n = " ".length();
            if (nbtTagCompound.hasKey(PotionEffect.I[0x2E ^ 0x3C], " ".length())) {
                n = (nbtTagCompound.getBoolean(PotionEffect.I[0x7C ^ 0x6F]) ? 1 : 0);
            }
            return new PotionEffect(byte1, integer, byte2, boolean1, n != 0);
        }
        return null;
    }
    
    public int getPotionID() {
        return this.potionID;
    }
    
    public void setSplashPotion(final boolean isSplashPotion) {
        this.isSplashPotion = isSplashPotion;
    }
    
    public void combine(final PotionEffect potionEffect) {
        if (this.potionID != potionEffect.potionID) {
            PotionEffect.LOGGER.warn(PotionEffect.I["".length()]);
        }
        if (potionEffect.amplifier > this.amplifier) {
            this.amplifier = potionEffect.amplifier;
            this.duration = potionEffect.duration;
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        else if (potionEffect.amplifier == this.amplifier && this.duration < potionEffect.duration) {
            this.duration = potionEffect.duration;
            "".length();
            if (1 <= 0) {
                throw null;
            }
        }
        else if (!potionEffect.isAmbient && this.isAmbient) {
            this.isAmbient = potionEffect.isAmbient;
        }
        this.showParticles = potionEffect.showParticles;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof PotionEffect)) {
            return "".length() != 0;
        }
        final PotionEffect potionEffect = (PotionEffect)o;
        if (this.potionID == potionEffect.potionID && this.amplifier == potionEffect.amplifier && this.duration == potionEffect.duration && this.isSplashPotion == potionEffect.isSplashPotion && this.isAmbient == potionEffect.isAmbient) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public boolean getIsAmbient() {
        return this.isAmbient;
    }
    
    public PotionEffect(final int potionID, final int duration, final int amplifier, final boolean isAmbient, final boolean showParticles) {
        this.potionID = potionID;
        this.duration = duration;
        this.amplifier = amplifier;
        this.isAmbient = isAmbient;
        this.showParticles = showParticles;
    }
    
    public String getEffectName() {
        return Potion.potionTypes[this.potionID].getName();
    }
    
    public PotionEffect(final int n, final int n2, final int n3) {
        this(n, n2, n3, "".length() != 0, " ".length() != 0);
    }
}
