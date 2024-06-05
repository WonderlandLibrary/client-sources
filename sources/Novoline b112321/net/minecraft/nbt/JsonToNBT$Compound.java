package net.minecraft.nbt;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.nbt.JsonToNBT$Any;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;

class JsonToNBT$Compound extends JsonToNBT$Any {
   protected List field_150491_b = Lists.newArrayList();

   public JsonToNBT$Compound(String var1) {
      this.json = var1;
   }

   public NBTBase parse() throws NBTException {
      NBTTagCompound var1 = new NBTTagCompound();

      for(JsonToNBT$Any var3 : this.field_150491_b) {
         var1.setTag(var3.json, var3.parse());
      }

      return var1;
   }
}
