package net.minecraft.nbt;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.nbt.JsonToNBT$Any;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagList;

class JsonToNBT$List extends JsonToNBT$Any {
   protected List field_150492_b = Lists.newArrayList();

   public JsonToNBT$List(String var1) {
      this.json = var1;
   }

   public NBTBase parse() throws NBTException {
      NBTTagList var1 = new NBTTagList();

      for(JsonToNBT$Any var3 : this.field_150492_b) {
         var1.appendTag(var3.parse());
      }

      return var1;
   }
}
