package org.spongepowered.tools.obfuscation.mapping.mcp;

import com.google.common.base.Strings;
import com.google.common.collect.BiMap;
import com.google.common.io.Files;
import com.google.common.io.LineProcessor;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import org.spongepowered.asm.mixin.throwables.MixinException;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.asm.obfuscation.mapping.mcp.MappingFieldSrg;
import org.spongepowered.tools.obfuscation.mapping.common.MappingProvider;

public class MappingProviderSrg extends MappingProvider {
   public MappingProviderSrg(Messager var1, Filer var2) {
      super(var1, var2);
   }

   public void read(File var1) throws IOException {
      BiMap var2 = this.packageMap;
      BiMap var3 = this.classMap;
      BiMap var4 = this.fieldMap;
      BiMap var5 = this.methodMap;
      Files.readLines(var1, Charset.defaultCharset(), new LineProcessor(this, var2, var3, var4, var5, var1) {
         final BiMap val$packageMap;
         final BiMap val$classMap;
         final BiMap val$fieldMap;
         final BiMap val$methodMap;
         final File val$input;
         final MappingProviderSrg this$0;

         {
            this.this$0 = var1;
            this.val$packageMap = var2;
            this.val$classMap = var3;
            this.val$fieldMap = var4;
            this.val$methodMap = var5;
            this.val$input = var6;
         }

         public String getResult() {
            return null;
         }

         public boolean processLine(String var1) throws IOException {
            if (!Strings.isNullOrEmpty(var1) && !var1.startsWith("#")) {
               String var2 = var1.substring(0, 2);
               String[] var3 = var1.substring(4).split(" ");
               if (var2.equals("PK")) {
                  this.val$packageMap.forcePut(var3[0], var3[1]);
               } else if (var2.equals("CL")) {
                  this.val$classMap.forcePut(var3[0], var3[1]);
               } else if (var2.equals("FD")) {
                  this.val$fieldMap.forcePut((new MappingFieldSrg(var3[0])).copy(), (new MappingFieldSrg(var3[1])).copy());
               } else {
                  if (!var2.equals("MD")) {
                     throw new MixinException("Invalid SRG file: " + this.val$input);
                  }

                  this.val$methodMap.forcePut(new MappingMethod(var3[0], var3[1]), new MappingMethod(var3[2], var3[3]));
               }

               return true;
            } else {
               return true;
            }
         }

         public Object getResult() {
            return this.getResult();
         }
      });
   }

   public MappingField getFieldMapping(MappingField var1) {
      if (((MappingField)var1).getDesc() != null) {
         var1 = new MappingFieldSrg((MappingField)var1);
      }

      return (MappingField)this.fieldMap.get(var1);
   }
}
