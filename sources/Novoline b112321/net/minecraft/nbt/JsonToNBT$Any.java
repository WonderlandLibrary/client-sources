package net.minecraft.nbt;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTException;

abstract class JsonToNBT$Any {
   protected String json;

   public abstract NBTBase parse() throws NBTException;
}
