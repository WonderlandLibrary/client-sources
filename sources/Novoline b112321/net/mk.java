package net;

import net.minecraft.entity.item.EntityMinecart$EnumMinecartType;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.util.WeightedRandom$Item;

public class mk extends WeightedRandom$Item {
   private final NBTTagCompound b;
   private final String c;
   final MobSpawnerBaseLogic d;

   public mk(MobSpawnerBaseLogic var1, NBTTagCompound var2) {
      this(var1, var2.getCompoundTag("Properties"), var2.getString("Type"), var2.getInteger("Weight"));
   }

   public mk(MobSpawnerBaseLogic var1, NBTTagCompound var2, String var3) {
      this(var1, var2, var3, 1);
   }

   private mk(MobSpawnerBaseLogic var1, NBTTagCompound var2, String var3, int var4) {
      super(var4);
      this.d = var1;
      if(var3.equals("Minecart")) {
         var3 = EntityMinecart$EnumMinecartType.byNetworkID(var2.getInteger("Type")).getName();
      }

      this.b = var2;
      this.c = var3;
   }

   public NBTTagCompound a() {
      NBTTagCompound var1 = new NBTTagCompound();
      var1.setTag("Properties", this.b);
      var1.setString("Type", this.c);
      var1.setInteger("Weight", this.itemWeight);
      return var1;
   }

   static String a(mk var0) {
      return var0.c;
   }

   static NBTTagCompound b(mk var0) {
      return var0.b;
   }
}
