package net.minecraft.world.storage;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public interface IPlayerFileData {
   String[] getAvailablePlayerDat();

   NBTTagCompound readPlayerData(EntityPlayer var1);

   void writePlayerData(EntityPlayer var1);
}
