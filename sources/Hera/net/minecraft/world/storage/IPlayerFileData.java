package net.minecraft.world.storage;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public interface IPlayerFileData {
  void writePlayerData(EntityPlayer paramEntityPlayer);
  
  NBTTagCompound readPlayerData(EntityPlayer paramEntityPlayer);
  
  String[] getAvailablePlayerDat();
}


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\world\storage\IPlayerFileData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */