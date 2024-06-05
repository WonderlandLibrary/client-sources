package net;

import com.mojang.authlib.GameProfile;
import net.minecraft.tileentity.TileEntitySkull;

public class p1 {
   public static int b(TileEntitySkull var0) {
      return var0.getBlockMetadata();
   }

   public static int c(TileEntitySkull var0) {
      return var0.getSkullRotation();
   }

   public static int d(TileEntitySkull var0) {
      return var0.getSkullType();
   }

   public static GameProfile a(TileEntitySkull var0) {
      return var0.getPlayerProfile();
   }

   public static GameProfile a(GameProfile var0) {
      return TileEntitySkull.updateGameprofile(var0);
   }

   public static void a(TileEntitySkull var0, GameProfile var1) {
      var0.setPlayerProfile(var1);
   }

   public static void b(TileEntitySkull var0, int var1) {
      var0.setType(var1);
   }

   public static void a(TileEntitySkull var0, int var1) {
      var0.setSkullRotation(var1);
   }
}
