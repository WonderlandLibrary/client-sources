package org.spongepowered.asm.obfuscation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.spongepowered.asm.mixin.extensibility.IRemapper;

public class RemapperChain implements IRemapper {
   private final List remappers = new ArrayList();

   public String toString() {
      return String.format("RemapperChain[%d]", this.remappers.size());
   }

   public RemapperChain add(IRemapper var1) {
      this.remappers.add(var1);
      return this;
   }

   public String mapMethodName(String var1, String var2, String var3) {
      Iterator var4 = this.remappers.iterator();

      while(var4.hasNext()) {
         IRemapper var5 = (IRemapper)var4.next();
         String var6 = var5.mapMethodName(var1, var2, var3);
         if (var6 != null && !var6.equals(var2)) {
            var2 = var6;
         }
      }

      return var2;
   }

   public String mapFieldName(String var1, String var2, String var3) {
      Iterator var4 = this.remappers.iterator();

      while(var4.hasNext()) {
         IRemapper var5 = (IRemapper)var4.next();
         String var6 = var5.mapFieldName(var1, var2, var3);
         if (var6 != null && !var6.equals(var2)) {
            var2 = var6;
         }
      }

      return var2;
   }

   public String map(String var1) {
      Iterator var2 = this.remappers.iterator();

      while(var2.hasNext()) {
         IRemapper var3 = (IRemapper)var2.next();
         String var4 = var3.map(var1);
         if (var4 != null && !var4.equals(var1)) {
            var1 = var4;
         }
      }

      return var1;
   }

   public String unmap(String var1) {
      Iterator var2 = this.remappers.iterator();

      while(var2.hasNext()) {
         IRemapper var3 = (IRemapper)var2.next();
         String var4 = var3.unmap(var1);
         if (var4 != null && !var4.equals(var1)) {
            var1 = var4;
         }
      }

      return var1;
   }

   public String mapDesc(String var1) {
      Iterator var2 = this.remappers.iterator();

      while(var2.hasNext()) {
         IRemapper var3 = (IRemapper)var2.next();
         String var4 = var3.mapDesc(var1);
         if (var4 != null && !var4.equals(var1)) {
            var1 = var4;
         }
      }

      return var1;
   }

   public String unmapDesc(String var1) {
      Iterator var2 = this.remappers.iterator();

      while(var2.hasNext()) {
         IRemapper var3 = (IRemapper)var2.next();
         String var4 = var3.unmapDesc(var1);
         if (var4 != null && !var4.equals(var1)) {
            var1 = var4;
         }
      }

      return var1;
   }
}
