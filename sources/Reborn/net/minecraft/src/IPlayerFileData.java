package net.minecraft.src;

public interface IPlayerFileData
{
    void writePlayerData(final EntityPlayer p0);
    
    NBTTagCompound readPlayerData(final EntityPlayer p0);
    
    String[] getAvailablePlayerDat();
}
