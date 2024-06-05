package net.minecraft.nbt;

import java.util.concurrent.Callable;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

class NBTTagCompound$2 implements Callable {
   final int val$expectedType;
   final NBTTagCompound this$0;

   NBTTagCompound$2(NBTTagCompound var1, int var2) {
      this.this$0 = var1;
      this.val$expectedType = var2;
   }

   public String call() throws Exception {
      return NBTBase.NBT_TYPES[this.val$expectedType];
   }
}
