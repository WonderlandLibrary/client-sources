package net;

import com.mojang.authlib.GameProfile;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;

public class rS {
   public static GameProfile a(NBTTagCompound var0) {
      return NBTUtil.readGameProfileFromNBT(var0);
   }

   public static NBTTagCompound a(NBTTagCompound var0, GameProfile var1) {
      return NBTUtil.writeGameProfile(var0, var1);
   }

   public static boolean a(NBTBase var0, NBTBase var1, boolean var2) {
      return NBTUtil.a(var0, var1, var2);
   }
}
