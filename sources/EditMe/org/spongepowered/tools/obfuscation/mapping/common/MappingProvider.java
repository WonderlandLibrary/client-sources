package org.spongepowered.tools.obfuscation.mapping.common;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.mapping.IMappingProvider;

public abstract class MappingProvider implements IMappingProvider {
   protected final Messager messager;
   protected final Filer filer;
   protected final BiMap packageMap = HashBiMap.create();
   protected final BiMap classMap = HashBiMap.create();
   protected final BiMap fieldMap = HashBiMap.create();
   protected final BiMap methodMap = HashBiMap.create();

   public MappingProvider(Messager var1, Filer var2) {
      this.messager = var1;
      this.filer = var2;
   }

   public void clear() {
      this.packageMap.clear();
      this.classMap.clear();
      this.fieldMap.clear();
      this.methodMap.clear();
   }

   public boolean isEmpty() {
      return this.packageMap.isEmpty() && this.classMap.isEmpty() && this.fieldMap.isEmpty() && this.methodMap.isEmpty();
   }

   public MappingMethod getMethodMapping(MappingMethod var1) {
      return (MappingMethod)this.methodMap.get(var1);
   }

   public MappingField getFieldMapping(MappingField var1) {
      return (MappingField)this.fieldMap.get(var1);
   }

   public String getClassMapping(String var1) {
      return (String)this.classMap.get(var1);
   }

   public String getPackageMapping(String var1) {
      return (String)this.packageMap.get(var1);
   }
}
