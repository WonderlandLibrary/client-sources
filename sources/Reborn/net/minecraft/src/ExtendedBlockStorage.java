package net.minecraft.src;

public class ExtendedBlockStorage
{
    private int yBase;
    private int blockRefCount;
    private int tickRefCount;
    private byte[] blockLSBArray;
    private NibbleArray blockMSBArray;
    private NibbleArray blockMetadataArray;
    private NibbleArray blocklightArray;
    private NibbleArray skylightArray;
    
    public ExtendedBlockStorage(final int par1, final boolean par2) {
        this.yBase = par1;
        this.blockLSBArray = new byte[4096];
        this.blockMetadataArray = new NibbleArray(this.blockLSBArray.length, 4);
        this.blocklightArray = new NibbleArray(this.blockLSBArray.length, 4);
        if (par2) {
            this.skylightArray = new NibbleArray(this.blockLSBArray.length, 4);
        }
    }
    
    public int getExtBlockID(final int par1, final int par2, final int par3) {
        final int var4 = this.blockLSBArray[par2 << 8 | par3 << 4 | par1] & 0xFF;
        return (this.blockMSBArray != null) ? (this.blockMSBArray.get(par1, par2, par3) << 8 | var4) : var4;
    }
    
    public void setExtBlockID(final int par1, final int par2, final int par3, final int par4) {
        int var5 = this.blockLSBArray[par2 << 8 | par3 << 4 | par1] & 0xFF;
        if (this.blockMSBArray != null) {
            var5 |= this.blockMSBArray.get(par1, par2, par3) << 8;
        }
        if (var5 == 0 && par4 != 0) {
            ++this.blockRefCount;
            if (Block.blocksList[par4] != null && Block.blocksList[par4].getTickRandomly()) {
                ++this.tickRefCount;
            }
        }
        else if (var5 != 0 && par4 == 0) {
            --this.blockRefCount;
            if (Block.blocksList[var5] != null && Block.blocksList[var5].getTickRandomly()) {
                --this.tickRefCount;
            }
        }
        else if (Block.blocksList[var5] != null && Block.blocksList[var5].getTickRandomly() && (Block.blocksList[par4] == null || !Block.blocksList[par4].getTickRandomly())) {
            --this.tickRefCount;
        }
        else if ((Block.blocksList[var5] == null || !Block.blocksList[var5].getTickRandomly()) && Block.blocksList[par4] != null && Block.blocksList[par4].getTickRandomly()) {
            ++this.tickRefCount;
        }
        this.blockLSBArray[par2 << 8 | par3 << 4 | par1] = (byte)(par4 & 0xFF);
        if (par4 > 255) {
            if (this.blockMSBArray == null) {
                this.blockMSBArray = new NibbleArray(this.blockLSBArray.length, 4);
            }
            this.blockMSBArray.set(par1, par2, par3, (par4 & 0xF00) >> 8);
        }
        else if (this.blockMSBArray != null) {
            this.blockMSBArray.set(par1, par2, par3, 0);
        }
    }
    
    public int getExtBlockMetadata(final int par1, final int par2, final int par3) {
        return this.blockMetadataArray.get(par1, par2, par3);
    }
    
    public void setExtBlockMetadata(final int par1, final int par2, final int par3, final int par4) {
        this.blockMetadataArray.set(par1, par2, par3, par4);
    }
    
    public boolean isEmpty() {
        return this.blockRefCount == 0;
    }
    
    public boolean getNeedsRandomTick() {
        return this.tickRefCount > 0;
    }
    
    public int getYLocation() {
        return this.yBase;
    }
    
    public void setExtSkylightValue(final int par1, final int par2, final int par3, final int par4) {
        this.skylightArray.set(par1, par2, par3, par4);
    }
    
    public int getExtSkylightValue(final int par1, final int par2, final int par3) {
        return this.skylightArray.get(par1, par2, par3);
    }
    
    public void setExtBlocklightValue(final int par1, final int par2, final int par3, final int par4) {
        this.blocklightArray.set(par1, par2, par3, par4);
    }
    
    public int getExtBlocklightValue(final int par1, final int par2, final int par3) {
        return this.blocklightArray.get(par1, par2, par3);
    }
    
    public void removeInvalidBlocks() {
        this.blockRefCount = 0;
        this.tickRefCount = 0;
        for (int var1 = 0; var1 < 16; ++var1) {
            for (int var2 = 0; var2 < 16; ++var2) {
                for (int var3 = 0; var3 < 16; ++var3) {
                    final int var4 = this.getExtBlockID(var1, var2, var3);
                    if (var4 > 0) {
                        if (Block.blocksList[var4] == null) {
                            this.blockLSBArray[var2 << 8 | var3 << 4 | var1] = 0;
                            if (this.blockMSBArray != null) {
                                this.blockMSBArray.set(var1, var2, var3, 0);
                            }
                        }
                        else {
                            ++this.blockRefCount;
                            if (Block.blocksList[var4].getTickRandomly()) {
                                ++this.tickRefCount;
                            }
                        }
                    }
                }
            }
        }
    }
    
    public byte[] getBlockLSBArray() {
        return this.blockLSBArray;
    }
    
    public void clearMSBArray() {
        this.blockMSBArray = null;
    }
    
    public NibbleArray getBlockMSBArray() {
        return this.blockMSBArray;
    }
    
    public NibbleArray getMetadataArray() {
        return this.blockMetadataArray;
    }
    
    public NibbleArray getBlocklightArray() {
        return this.blocklightArray;
    }
    
    public NibbleArray getSkylightArray() {
        return this.skylightArray;
    }
    
    public void setBlockLSBArray(final byte[] par1ArrayOfByte) {
        this.blockLSBArray = par1ArrayOfByte;
    }
    
    public void setBlockMSBArray(final NibbleArray par1NibbleArray) {
        this.blockMSBArray = par1NibbleArray;
    }
    
    public void setBlockMetadataArray(final NibbleArray par1NibbleArray) {
        this.blockMetadataArray = par1NibbleArray;
    }
    
    public void setBlocklightArray(final NibbleArray par1NibbleArray) {
        this.blocklightArray = par1NibbleArray;
    }
    
    public void setSkylightArray(final NibbleArray par1NibbleArray) {
        this.skylightArray = par1NibbleArray;
    }
    
    public NibbleArray createBlockMSBArray() {
        return this.blockMSBArray = new NibbleArray(this.blockLSBArray.length, 4);
    }
}
