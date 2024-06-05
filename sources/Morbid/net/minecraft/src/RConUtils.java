package net.minecraft.src;

public class RConUtils
{
    public static char[] hexDigits;
    
    static {
        RConUtils.hexDigits = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
    }
    
    public static String getBytesAsString(final byte[] par0ArrayOfByte, final int par1, final int par2) {
        int var3;
        int var4;
        for (var3 = par2 - 1, var4 = ((par1 > var3) ? var3 : par1); par0ArrayOfByte[var4] != 0 && var4 < var3; ++var4) {}
        return new String(par0ArrayOfByte, par1, var4 - par1);
    }
    
    public static int getRemainingBytesAsLEInt(final byte[] par0ArrayOfByte, final int par1) {
        return getBytesAsLEInt(par0ArrayOfByte, par1, par0ArrayOfByte.length);
    }
    
    public static int getBytesAsLEInt(final byte[] par0ArrayOfByte, final int par1, final int par2) {
        return (par2 - par1 - 4 < 0) ? 0 : (par0ArrayOfByte[par1 + 3] << 24 | (par0ArrayOfByte[par1 + 2] & 0xFF) << 16 | (par0ArrayOfByte[par1 + 1] & 0xFF) << 8 | (par0ArrayOfByte[par1] & 0xFF));
    }
    
    public static int getBytesAsBEint(final byte[] par0ArrayOfByte, final int par1, final int par2) {
        return (par2 - par1 - 4 < 0) ? 0 : (par0ArrayOfByte[par1] << 24 | (par0ArrayOfByte[par1 + 1] & 0xFF) << 16 | (par0ArrayOfByte[par1 + 2] & 0xFF) << 8 | (par0ArrayOfByte[par1 + 3] & 0xFF));
    }
    
    public static String getByteAsHexString(final byte par0) {
        return new StringBuilder().append(RConUtils.hexDigits[(par0 & 0xF0) >>> 4]).append(RConUtils.hexDigits[par0 & 0xF]).toString();
    }
}
