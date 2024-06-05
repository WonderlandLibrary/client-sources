package net;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class sr {
   public static NBTTagList a(BaseAttributeMap var0) {
      return SharedMonsterAttributes.writeBaseAttributeMapToNBT(var0);
   }

   public static void a(BaseAttributeMap var0, NBTTagList var1) {
      SharedMonsterAttributes.a(var0, var1);
   }

   public static AttributeModifier a(NBTTagCompound var0) {
      return SharedMonsterAttributes.readAttributeModifierFromNBT(var0);
   }
}
