package org.spongepowered.tools.obfuscation.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import java.util.Set;
import javax.tools.Diagnostic.Kind;
import org.spongepowered.tools.obfuscation.ObfuscationType;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;

public final class ObfuscationServices {
   private static ObfuscationServices instance;
   private final ServiceLoader serviceLoader = ServiceLoader.load(IObfuscationService.class, this.getClass().getClassLoader());
   private final Set services = new HashSet();

   private ObfuscationServices() {
   }

   public static ObfuscationServices getInstance() {
      if (instance == null) {
         instance = new ObfuscationServices();
      }

      return instance;
   }

   public void initProviders(IMixinAnnotationProcessor var1) {
      try {
         Iterator var2 = this.serviceLoader.iterator();

         while(true) {
            String var4;
            Collection var5;
            do {
               IObfuscationService var3;
               do {
                  if (!var2.hasNext()) {
                     return;
                  }

                  var3 = (IObfuscationService)var2.next();
               } while(this.services.contains(var3));

               this.services.add(var3);
               var4 = var3.getClass().getSimpleName();
               var5 = var3.getObfuscationTypes();
            } while(var5 == null);

            Iterator var6 = var5.iterator();

            while(var6.hasNext()) {
               ObfuscationTypeDescriptor var7 = (ObfuscationTypeDescriptor)var6.next();

               try {
                  ObfuscationType var8 = ObfuscationType.create(var7, var1);
                  var1.printMessage(Kind.NOTE, var4 + " supports type: \"" + var8 + "\"");
               } catch (Exception var9) {
                  var9.printStackTrace();
               }
            }
         }
      } catch (ServiceConfigurationError var10) {
         var1.printMessage(Kind.ERROR, var10.getClass().getSimpleName() + ": " + var10.getMessage());
         var10.printStackTrace();
      }
   }

   public Set getSupportedOptions() {
      HashSet var1 = new HashSet();
      Iterator var2 = this.serviceLoader.iterator();

      while(var2.hasNext()) {
         IObfuscationService var3 = (IObfuscationService)var2.next();
         Set var4 = var3.getSupportedOptions();
         if (var4 != null) {
            var1.addAll(var4);
         }
      }

      return var1;
   }

   public IObfuscationService getService(Class var1) {
      Iterator var2 = this.serviceLoader.iterator();

      IObfuscationService var3;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         var3 = (IObfuscationService)var2.next();
      } while(!var1.getName().equals(var3.getClass().getName()));

      return var3;
   }
}
