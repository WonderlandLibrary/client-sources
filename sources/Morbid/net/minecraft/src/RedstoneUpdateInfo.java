package net.minecraft.src;

class RedstoneUpdateInfo
{
    int x;
    int y;
    int z;
    long updateTime;
    
    public RedstoneUpdateInfo(final int par1, final int par2, final int par3, final long par4) {
        this.x = par1;
        this.y = par2;
        this.z = par3;
        this.updateTime = par4;
    }
}
