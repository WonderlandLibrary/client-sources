package net.minecraft.world.storage;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public abstract interface IPlayerFileData
{
  public abstract void writePlayerData(EntityPlayer paramEntityPlayer);
  
  public abstract NBTTagCompound readPlayerData(EntityPlayer paramEntityPlayer);
  
  public abstract String[] getAvailablePlayerDat();
}


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.storage.IPlayerFileData
 * JD-Core Version:    0.7.0.1
 */