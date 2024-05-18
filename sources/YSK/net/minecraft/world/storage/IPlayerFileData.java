package net.minecraft.world.storage;

import net.minecraft.entity.player.*;
import net.minecraft.nbt.*;

public interface IPlayerFileData
{
    String[] getAvailablePlayerDat();
    
    void writePlayerData(final EntityPlayer p0);
    
    NBTTagCompound readPlayerData(final EntityPlayer p0);
}
