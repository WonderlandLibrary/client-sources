package net.minecraft.src;

public class NibbleArrayReader
{
    public final byte[] data;
    private final int depthBits;
    private final int depthBitsPlusFour;
    
    public NibbleArrayReader(final byte[] par1ArrayOfByte, final int par2) {
        this.data = par1ArrayOfByte;
        this.depthBits = par2;
        this.depthBitsPlusFour = par2 + 4;
    }
    
    public int get(final int par1, final int par2, final int par3) {
        final int var4 = par1 << this.depthBitsPlusFour | par3 << this.depthBits | par2;
        final int var5 = var4 >> 1;
        final int var6 = var4 & 0x1;
        return (var6 == 0) ? (this.data[var5] & 0xF) : (this.data[var5] >> 4 & 0xF);
    }
}
