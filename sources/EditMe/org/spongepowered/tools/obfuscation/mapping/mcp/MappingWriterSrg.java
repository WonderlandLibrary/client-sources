package org.spongepowered.tools.obfuscation.mapping.mcp;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.ObfuscationType;
import org.spongepowered.tools.obfuscation.mapping.IMappingConsumer;
import org.spongepowered.tools.obfuscation.mapping.common.MappingWriter;

public class MappingWriterSrg extends MappingWriter {
   public MappingWriterSrg(Messager var1, Filer var2) {
      super(var1, var2);
   }

   public void write(String var1, ObfuscationType var2, IMappingConsumer.MappingSet var3, IMappingConsumer.MappingSet var4) {
      if (var1 != null) {
         PrintWriter var5 = null;

         try {
            var5 = this.openFileWriter(var1, var2 + " output SRGs");
            this.writeFieldMappings(var5, var3);
            this.writeMethodMappings(var5, var4);
         } catch (IOException var15) {
            var15.printStackTrace();
         } finally {
            if (var5 != null) {
               try {
                  var5.close();
               } catch (Exception var14) {
               }
            }

         }

      }
   }

   protected void writeFieldMappings(PrintWriter var1, IMappingConsumer.MappingSet var2) {
      Iterator var3 = var2.iterator();

      while(var3.hasNext()) {
         IMappingConsumer.MappingSet.Pair var4 = (IMappingConsumer.MappingSet.Pair)var3.next();
         var1.println(this.formatFieldMapping(var4));
      }

   }

   protected void writeMethodMappings(PrintWriter var1, IMappingConsumer.MappingSet var2) {
      Iterator var3 = var2.iterator();

      while(var3.hasNext()) {
         IMappingConsumer.MappingSet.Pair var4 = (IMappingConsumer.MappingSet.Pair)var3.next();
         var1.println(this.formatMethodMapping(var4));
      }

   }

   protected String formatFieldMapping(IMappingConsumer.MappingSet.Pair var1) {
      return String.format("FD: %s/%s %s/%s", ((MappingField)var1.from).getOwner(), ((MappingField)var1.from).getName(), ((MappingField)var1.to).getOwner(), ((MappingField)var1.to).getName());
   }

   protected String formatMethodMapping(IMappingConsumer.MappingSet.Pair var1) {
      return String.format("MD: %s %s %s %s", ((MappingMethod)var1.from).getName(), ((MappingMethod)var1.from).getDesc(), ((MappingMethod)var1.to).getName(), ((MappingMethod)var1.to).getDesc());
   }
}
