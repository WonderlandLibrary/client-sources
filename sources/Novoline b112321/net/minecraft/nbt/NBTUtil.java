package net.minecraft.nbt;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import java.util.UUID;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.StringUtils;

public final class NBTUtil {
   public static GameProfile readGameProfileFromNBT(NBTTagCompound var0) {
      String var1 = null;
      String var2 = null;
      if(var0.hasKey("Name", 8)) {
         var1 = var0.getString("Name");
      }

      if(var0.hasKey("Id", 8)) {
         var2 = var0.getString("Id");
      }

      if(StringUtils.isNullOrEmpty(var1) && StringUtils.isNullOrEmpty(var2)) {
         return null;
      } else {
         String var10000 = var2;

         UUID var3;
         try {
            var3 = UUID.fromString(var10000);
         } catch (Throwable var12) {
            var3 = null;
         }

         GameProfile var4 = new GameProfile(var3, var1);
         if(var0.hasKey("Properties", 10)) {
            NBTTagCompound var5 = var0.getCompoundTag("Properties");

            for(String var7 : var5.getKeySet()) {
               NBTTagList var8 = var5.getTagList(var7, 10);

               for(int var9 = 0; var9 < var8.tagCount(); ++var9) {
                  NBTTagCompound var10 = var8.getCompoundTagAt(var9);
                  String var11 = var10.getString("Value");
                  if(var10.hasKey("Signature", 8)) {
                     var4.getProperties().put(var7, new Property(var7, var11, var10.getString("Signature")));
                  } else {
                     var4.getProperties().put(var7, new Property(var7, var11));
                  }
               }
            }
         }

         return var4;
      }
   }

   public static NBTTagCompound writeGameProfile(NBTTagCompound var0, GameProfile var1) {
      if(!StringUtils.isNullOrEmpty(var1.getName())) {
         var0.setString("Name", var1.getName());
      }

      if(var1.getId() != null) {
         var0.setString("Id", var1.getId().toString());
      }

      if(!var1.getProperties().isEmpty()) {
         NBTTagCompound var2 = new NBTTagCompound();

         for(String var4 : var1.getProperties().keySet()) {
            NBTTagList var5 = new NBTTagList();

            for(Property var7 : var1.getProperties().get(var4)) {
               NBTTagCompound var8 = new NBTTagCompound();
               var8.setString("Value", var7.getValue());
               if(var7.hasSignature()) {
                  var8.setString("Signature", var7.getSignature());
               }

               var5.appendTag(var8);
            }

            var2.setTag(var4, var5);
         }

         var0.setTag("Properties", var2);
      }

      return var0;
   }

   public static boolean a(NBTBase var0, NBTBase var1, boolean var2) {
      return var0 == var1?true:true;
   }

   private static Throwable a(Throwable var0) {
      return var0;
   }
}
