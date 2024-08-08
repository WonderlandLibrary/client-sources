package org.spongepowered.tools.obfuscation;

import java.util.Iterator;
import java.util.List;
import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
import org.spongepowered.asm.obfuscation.mapping.IMapping;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.interfaces.IObfuscationDataProvider;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;

public class ObfuscationDataProvider implements IObfuscationDataProvider {
   private final IMixinAnnotationProcessor ap;
   private final List environments;

   public ObfuscationDataProvider(IMixinAnnotationProcessor var1, List var2) {
      this.ap = var1;
      this.environments = var2;
   }

   public ObfuscationData getObfEntryRecursive(MemberInfo var1) {
      MemberInfo var2 = var1;
      ObfuscationData var3 = this.getObfClass(var1.owner);
      ObfuscationData var4 = this.getObfEntry(var1);

      try {
         while(var4.isEmpty()) {
            TypeHandle var5 = this.ap.getTypeProvider().getTypeHandle(var2.owner);
            if (var5 == null) {
               return var4;
            }

            TypeHandle var6 = var5.getSuperclass();
            var4 = this.getObfEntryUsing(var2, var6);
            if (!var4.isEmpty()) {
               return applyParents(var3, var4);
            }

            Iterator var7 = var5.getInterfaces().iterator();

            while(var7.hasNext()) {
               TypeHandle var8 = (TypeHandle)var7.next();
               var4 = this.getObfEntryUsing(var2, var8);
               if (!var4.isEmpty()) {
                  return applyParents(var3, var4);
               }
            }

            if (var6 == null) {
               break;
            }

            var2 = var2.move(var6.getName());
         }

         return var4;
      } catch (Exception var9) {
         var9.printStackTrace();
         return this.getObfEntry(var1);
      }
   }

   private ObfuscationData getObfEntryUsing(MemberInfo var1, TypeHandle var2) {
      return var2 == null ? new ObfuscationData() : this.getObfEntry(var1.move(var2.getName()));
   }

   public ObfuscationData getObfEntry(MemberInfo var1) {
      return var1.isField() ? this.getObfField(var1) : this.getObfMethod(var1.asMethodMapping());
   }

   public ObfuscationData getObfEntry(IMapping var1) {
      if (var1 != null) {
         if (var1.getType() == IMapping.Type.FIELD) {
            return this.getObfField((MappingField)var1);
         }

         if (var1.getType() == IMapping.Type.METHOD) {
            return this.getObfMethod((MappingMethod)var1);
         }
      }

      return new ObfuscationData();
   }

   public ObfuscationData getObfMethodRecursive(MemberInfo var1) {
      return this.getObfEntryRecursive(var1);
   }

   public ObfuscationData getObfMethod(MemberInfo var1) {
      return this.getRemappedMethod(var1, var1.isConstructor());
   }

   public ObfuscationData getRemappedMethod(MemberInfo var1) {
      return this.getRemappedMethod(var1, true);
   }

   private ObfuscationData getRemappedMethod(MemberInfo var1, boolean var2) {
      ObfuscationData var3 = new ObfuscationData();
      Iterator var4 = this.environments.iterator();

      while(var4.hasNext()) {
         ObfuscationEnvironment var5 = (ObfuscationEnvironment)var4.next();
         MappingMethod var6 = var5.getObfMethod(var1);
         if (var6 != null) {
            var3.put(var5.getType(), var6);
         }
      }

      if (var3.isEmpty() && var2) {
         return this.remapDescriptor(var3, var1);
      } else {
         return var3;
      }
   }

   public ObfuscationData getObfMethod(MappingMethod var1) {
      return this.getRemappedMethod(var1, var1.isConstructor());
   }

   public ObfuscationData getRemappedMethod(MappingMethod var1) {
      return this.getRemappedMethod(var1, true);
   }

   private ObfuscationData getRemappedMethod(MappingMethod var1, boolean var2) {
      ObfuscationData var3 = new ObfuscationData();
      Iterator var4 = this.environments.iterator();

      while(var4.hasNext()) {
         ObfuscationEnvironment var5 = (ObfuscationEnvironment)var4.next();
         MappingMethod var6 = var5.getObfMethod(var1);
         if (var6 != null) {
            var3.put(var5.getType(), var6);
         }
      }

      if (var3.isEmpty() && var2) {
         return this.remapDescriptor(var3, new MemberInfo(var1));
      } else {
         return var3;
      }
   }

   public ObfuscationData remapDescriptor(ObfuscationData var1, MemberInfo var2) {
      Iterator var3 = this.environments.iterator();

      while(var3.hasNext()) {
         ObfuscationEnvironment var4 = (ObfuscationEnvironment)var3.next();
         MemberInfo var5 = var4.remapDescriptor(var2);
         if (var5 != null) {
            var1.put(var4.getType(), var5.asMethodMapping());
         }
      }

      return var1;
   }

   public ObfuscationData getObfFieldRecursive(MemberInfo var1) {
      return this.getObfEntryRecursive(var1);
   }

   public ObfuscationData getObfField(MemberInfo var1) {
      return this.getObfField(var1.asFieldMapping());
   }

   public ObfuscationData getObfField(MappingField var1) {
      ObfuscationData var2 = new ObfuscationData();
      Iterator var3 = this.environments.iterator();

      while(var3.hasNext()) {
         ObfuscationEnvironment var4 = (ObfuscationEnvironment)var3.next();
         MappingField var5 = var4.getObfField(var1);
         if (var5 != null) {
            if (var5.getDesc() == null && var1.getDesc() != null) {
               var5 = var5.transform(var4.remapDescriptor(var1.getDesc()));
            }

            var2.put(var4.getType(), var5);
         }
      }

      return var2;
   }

   public ObfuscationData getObfClass(TypeHandle var1) {
      return this.getObfClass(var1.getName());
   }

   public ObfuscationData getObfClass(String var1) {
      ObfuscationData var2 = new ObfuscationData(var1);
      Iterator var3 = this.environments.iterator();

      while(var3.hasNext()) {
         ObfuscationEnvironment var4 = (ObfuscationEnvironment)var3.next();
         String var5 = var4.getObfClass(var1);
         if (var5 != null) {
            var2.put(var4.getType(), var5);
         }
      }

      return var2;
   }

   private static ObfuscationData applyParents(ObfuscationData var0, ObfuscationData var1) {
      Iterator var2 = var1.iterator();

      while(var2.hasNext()) {
         ObfuscationType var3 = (ObfuscationType)var2.next();
         String var4 = (String)var0.get(var3);
         Object var5 = var1.get(var3);
         var1.put(var3, MemberInfo.fromMapping((IMapping)var5).move(var4).asMapping());
      }

      return var1;
   }
}
