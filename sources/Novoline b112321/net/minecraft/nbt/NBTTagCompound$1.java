package net.minecraft.nbt;

import java.util.concurrent.Callable;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

class NBTTagCompound$1 implements Callable {
   final String val$key;
   final NBTTagCompound this$0;

   NBTTagCompound$1(NBTTagCompound var1, String var2) {
      this.this$0 = var1;
      this.val$key = var2;
   }

   public String call() throws Exception {
      return NBTBase.NBT_TYPES[((NBTBase)NBTTagCompound.access$000(this.this$0).get(this.val$key)).getId()];
   }
}
