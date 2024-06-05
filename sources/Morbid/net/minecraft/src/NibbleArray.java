package net.minecraft.src;

public class NibbleArray
{
    public final byte[] data;
    private final int depthBits;
    private final int depthBitsPlusFour;
    
    public NibbleArray(final int par1, final int par2) {
        this.data = new byte[par1 >> 1];
        this.depthBits = par2;
        this.depthBitsPlusFour = par2 + 4;
    }
    
    public NibbleArray(final byte[] par1ArrayOfByte, final int par2) {
        this.data = par1ArrayOfByte;
        this.depthBits = par2;
        this.depthBitsPlusFour = par2 + 4;
    }
    
    public int get(final int par1, final int par2, final int par3) {
        final int var4 = par2 << this.depthBitsPlusFour | par3 << this.depthBits | par1;
        final int var5 = var4 >> 1;
        final int var6 = var4 & 0x1;
        return (var6 == 0) ? (this.data[var5] & 0xF) : (this.data[var5] >> 4 & 0xF);
    }
    
    public void set(final int par1, final int par2, final int par3, final int par4) {
        final int var5 = par2 << this.depthBitsPlusFour | par3 << this.depthBits | par1;
        final int var6 = var5 >> 1;
        final int var7 = var5 & 0x1;
        if (var7 == 0) {
            this.data[var6] = (byte)((this.data[var6] & 0xF0) | (par4 & 0xF));
        }
        else {
            this.data[var6] = (byte)((this.data[var6] & 0xF) | (par4 & 0xF) << 4);
        }
    }
}
