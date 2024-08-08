package org.spongepowered.asm.obfuscation.mapping.mcp;

import org.spongepowered.asm.obfuscation.mapping.common.MappingField;

public class MappingFieldSrg extends MappingField {
   private final String srg;

   public MappingFieldSrg(String var1) {
      super(getOwnerFromSrg(var1), getNameFromSrg(var1), (String)null);
      this.srg = var1;
   }

   public MappingFieldSrg(MappingField var1) {
      super(var1.getOwner(), var1.getName(), (String)null);
      this.srg = var1.getOwner() + "/" + var1.getName();
   }

   public String serialise() {
      return this.srg;
   }

   private static String getNameFromSrg(String var0) {
      if (var0 == null) {
         return null;
      } else {
         int var1 = var0.lastIndexOf(47);
         return var1 > -1 ? var0.substring(var1 + 1) : var0;
      }
   }

   private static String getOwnerFromSrg(String var0) {
      if (var0 == null) {
         return null;
      } else {
         int var1 = var0.lastIndexOf(47);
         return var1 > -1 ? var0.substring(0, var1) : null;
      }
   }
}
