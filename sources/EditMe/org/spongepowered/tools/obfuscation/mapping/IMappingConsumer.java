package org.spongepowered.tools.obfuscation.mapping;

import com.google.common.base.Objects;
import java.util.LinkedHashSet;
import org.spongepowered.asm.obfuscation.mapping.IMapping;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.ObfuscationType;

public interface IMappingConsumer {
   void clear();

   void addFieldMapping(ObfuscationType var1, MappingField var2, MappingField var3);

   void addMethodMapping(ObfuscationType var1, MappingMethod var2, MappingMethod var3);

   IMappingConsumer.MappingSet getFieldMappings(ObfuscationType var1);

   IMappingConsumer.MappingSet getMethodMappings(ObfuscationType var1);

   public static class MappingSet extends LinkedHashSet {
      private static final long serialVersionUID = 1L;

      public static class Pair {
         public final IMapping from;
         public final IMapping to;

         public Pair(IMapping var1, IMapping var2) {
            this.from = var1;
            this.to = var2;
         }

         public boolean equals(Object var1) {
            if (!(var1 instanceof IMappingConsumer.MappingSet.Pair)) {
               return false;
            } else {
               IMappingConsumer.MappingSet.Pair var2 = (IMappingConsumer.MappingSet.Pair)var1;
               return Objects.equal(this.from, var2.from) && Objects.equal(this.to, var2.to);
            }
         }

         public int hashCode() {
            return Objects.hashCode(new Object[]{this.from, this.to});
         }

         public String toString() {
            return String.format("%s -> %s", this.from, this.to);
         }
      }
   }
}
