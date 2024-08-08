package org.spongepowered.tools.obfuscation;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import java.lang.reflect.Constructor;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.interfaces.IOptionProvider;
import org.spongepowered.tools.obfuscation.service.ObfuscationTypeDescriptor;

public final class ObfuscationType {
   private static final Map types = new LinkedHashMap();
   private final String key;
   private final ObfuscationTypeDescriptor descriptor;
   private final IMixinAnnotationProcessor ap;
   private final IOptionProvider options;

   private ObfuscationType(ObfuscationTypeDescriptor var1, IMixinAnnotationProcessor var2) {
      this.key = var1.getKey();
      this.descriptor = var1;
      this.ap = var2;
      this.options = var2;
   }

   public final ObfuscationEnvironment createEnvironment() {
      try {
         Class var1 = this.descriptor.getEnvironmentType();
         Constructor var2 = var1.getDeclaredConstructor(ObfuscationType.class);
         var2.setAccessible(true);
         return (ObfuscationEnvironment)var2.newInstance(this);
      } catch (Exception var3) {
         throw new RuntimeException(var3);
      }
   }

   public String toString() {
      return this.key;
   }

   public String getKey() {
      return this.key;
   }

   public ObfuscationTypeDescriptor getConfig() {
      return this.descriptor;
   }

   public IMixinAnnotationProcessor getAnnotationProcessor() {
      return this.ap;
   }

   public boolean isDefault() {
      String var1 = this.options.getOption("defaultObfuscationEnv");
      return var1 == null && this.key.equals("searge") || var1 != null && this.key.equals(var1.toLowerCase());
   }

   public boolean isSupported() {
      return this.getInputFileNames().size() > 0;
   }

   public List getInputFileNames() {
      Builder var1 = ImmutableList.builder();
      String var2 = this.options.getOption(this.descriptor.getInputFileOption());
      if (var2 != null) {
         var1.add(var2);
      }

      String var3 = this.options.getOption(this.descriptor.getExtraInputFilesOption());
      if (var3 != null) {
         String[] var4 = var3.split(";");
         int var5 = var4.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            String var7 = var4[var6];
            var1.add(var7.trim());
         }
      }

      return var1.build();
   }

   public String getOutputFileName() {
      return this.options.getOption(this.descriptor.getOutputFileOption());
   }

   public static Iterable types() {
      return types.values();
   }

   public static ObfuscationType create(ObfuscationTypeDescriptor var0, IMixinAnnotationProcessor var1) {
      String var2 = var0.getKey();
      if (types.containsKey(var2)) {
         throw new IllegalArgumentException("Obfuscation type with key " + var2 + " was already registered");
      } else {
         ObfuscationType var3 = new ObfuscationType(var0, var1);
         types.put(var2, var3);
         return var3;
      }
   }

   public static ObfuscationType get(String var0) {
      ObfuscationType var1 = (ObfuscationType)types.get(var0);
      if (var1 == null) {
         throw new IllegalArgumentException("Obfuscation type with key " + var0 + " was not registered");
      } else {
         return var1;
      }
   }
}
