package net.minecraft.entity.player;

import net.minecraft.nbt.*;

public class PlayerCapabilities
{
    private float walkSpeed;
    public boolean disableDamage;
    private float flySpeed;
    public boolean allowFlying;
    public boolean isFlying;
    public boolean isCreativeMode;
    private static final String[] I;
    public boolean allowEdit;
    
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
            if (4 <= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public void setPlayerWalkSpeed(final float walkSpeed) {
        this.walkSpeed = walkSpeed;
    }
    
    public float getFlySpeed() {
        return this.flySpeed;
    }
    
    public void writeCapabilitiesToNBT(final NBTTagCompound nbtTagCompound) {
        final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
        nbtTagCompound2.setBoolean(PlayerCapabilities.I["".length()], this.disableDamage);
        nbtTagCompound2.setBoolean(PlayerCapabilities.I[" ".length()], this.isFlying);
        nbtTagCompound2.setBoolean(PlayerCapabilities.I["  ".length()], this.allowFlying);
        nbtTagCompound2.setBoolean(PlayerCapabilities.I["   ".length()], this.isCreativeMode);
        nbtTagCompound2.setBoolean(PlayerCapabilities.I[0x51 ^ 0x55], this.allowEdit);
        nbtTagCompound2.setFloat(PlayerCapabilities.I[0xB0 ^ 0xB5], this.flySpeed);
        nbtTagCompound2.setFloat(PlayerCapabilities.I[0x1A ^ 0x1C], this.walkSpeed);
        nbtTagCompound.setTag(PlayerCapabilities.I[0x4C ^ 0x4B], nbtTagCompound2);
    }
    
    public PlayerCapabilities() {
        this.allowEdit = (" ".length() != 0);
        this.flySpeed = 0.05f;
        this.walkSpeed = 0.1f;
    }
    
    static {
        I();
    }
    
    public float getWalkSpeed() {
        return this.walkSpeed;
    }
    
    private static void I() {
        (I = new String[0x7F ^ 0x6C])["".length()] = I("\u0007\u0003\u0004\f-\u0000\b\u0000\u0018#\u0002\b", "nmryA");
        PlayerCapabilities.I[" ".length()] = I("2\u001f\u0010 \u00003", "TsiIn");
        PlayerCapabilities.I["  ".length()] = I("\u0015\u0000=\u0002=\u0001", "xaDdQ");
        PlayerCapabilities.I["   ".length()] = I("\f\u0003)56\u0007\u00183-3", "emZAW");
        PlayerCapabilities.I[0xC6 ^ 0xC2] = I("\b\u0016\b\t0\f\u001b\u0015", "ewqKE");
        PlayerCapabilities.I[0xA7 ^ 0xA2] = I("5\u0001>\u001636\b#", "SmGEC");
        PlayerCapabilities.I[0x20 ^ 0x26] = I("2\u0005\u0018*\n5\u0001\u0011%", "EdtAY");
        PlayerCapabilities.I[0xAB ^ 0xAC] = I("\u0017.&%0\u0002%*:", "vLOIY");
        PlayerCapabilities.I[0xE ^ 0x6] = I(",\r'\u001a\u001d9\u0006+\u0005", "MoNvt");
        PlayerCapabilities.I[0x6C ^ 0x65] = I("\u0013#<\u000f?\u0006(0\u0010", "rAUcV");
        PlayerCapabilities.I[0x9F ^ 0x95] = I("\u00056#\u001f\u0000\u0002='\u000b\u000e\u0000=", "lXUjl");
        PlayerCapabilities.I[0x2B ^ 0x20] = I("6\"\u0017\u0010)7", "PNnyG");
        PlayerCapabilities.I[0x92 ^ 0x9E] = I(".+4\u000e\u0018:", "CJMht");
        PlayerCapabilities.I[0xA8 ^ 0xA5] = I(".-\u0000\u00033%6\u001a\u001b6", "GCswR");
        PlayerCapabilities.I[0x53 ^ 0x5D] = I("\"\u0004\u0003)*!\r\u001e", "DhzzZ");
        PlayerCapabilities.I[0x63 ^ 0x6C] = I("\u0004\u001e\u0015\"8\u0007\u0017\b", "brlqH");
        PlayerCapabilities.I[0x80 ^ 0x90] = I("?-$\u001e*8)-\u0011", "HLHuy");
        PlayerCapabilities.I[0xB5 ^ 0xA4] = I("9&\u00158\u0016=+\b", "TGlzc");
        PlayerCapabilities.I[0xB2 ^ 0xA0] = I(",*7,\u001b('*", "AKNnn");
    }
    
    public void readCapabilitiesFromNBT(final NBTTagCompound nbtTagCompound) {
        if (nbtTagCompound.hasKey(PlayerCapabilities.I[0x29 ^ 0x21], 0xAC ^ 0xA6)) {
            final NBTTagCompound compoundTag = nbtTagCompound.getCompoundTag(PlayerCapabilities.I[0x27 ^ 0x2E]);
            this.disableDamage = compoundTag.getBoolean(PlayerCapabilities.I[0x57 ^ 0x5D]);
            this.isFlying = compoundTag.getBoolean(PlayerCapabilities.I[0x11 ^ 0x1A]);
            this.allowFlying = compoundTag.getBoolean(PlayerCapabilities.I[0x83 ^ 0x8F]);
            this.isCreativeMode = compoundTag.getBoolean(PlayerCapabilities.I[0xB1 ^ 0xBC]);
            if (compoundTag.hasKey(PlayerCapabilities.I[0x0 ^ 0xE], 0x4C ^ 0x2F)) {
                this.flySpeed = compoundTag.getFloat(PlayerCapabilities.I[0x17 ^ 0x18]);
                this.walkSpeed = compoundTag.getFloat(PlayerCapabilities.I[0x18 ^ 0x8]);
            }
            if (compoundTag.hasKey(PlayerCapabilities.I[0x9B ^ 0x8A], " ".length())) {
                this.allowEdit = compoundTag.getBoolean(PlayerCapabilities.I[0x31 ^ 0x23]);
            }
        }
    }
    
    public void setFlySpeed(final float flySpeed) {
        this.flySpeed = flySpeed;
    }
}
