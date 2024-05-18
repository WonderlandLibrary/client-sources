package net.minecraft.world;

import net.minecraft.nbt.*;

public class LockCode
{
    public static final LockCode EMPTY_CODE;
    private static final String[] I;
    private final String lock;
    
    public String getLock() {
        return this.lock;
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
            if (4 == 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public boolean isEmpty() {
        if (this.lock != null && !this.lock.isEmpty()) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    private static void I() {
        (I = new String[0xE ^ 0xA])["".length()] = I("", "mXJds");
        LockCode.I[" ".length()] = I("\u0015;*-", "YTIFE");
        LockCode.I["  ".length()] = I("!:-1", "mUNZF");
        LockCode.I["   ".length()] = I("\u00028+\u0001", "NWHja");
    }
    
    public static LockCode fromNBT(final NBTTagCompound nbtTagCompound) {
        if (nbtTagCompound.hasKey(LockCode.I["  ".length()], 0x34 ^ 0x3C)) {
            return new LockCode(nbtTagCompound.getString(LockCode.I["   ".length()]));
        }
        return LockCode.EMPTY_CODE;
    }
    
    public void toNBT(final NBTTagCompound nbtTagCompound) {
        nbtTagCompound.setString(LockCode.I[" ".length()], this.lock);
    }
    
    public LockCode(final String lock) {
        this.lock = lock;
    }
    
    static {
        I();
        EMPTY_CODE = new LockCode(LockCode.I["".length()]);
    }
}
