package org.spongepowered.tools.obfuscation;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic.Kind;
import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.asm.util.ObfuscationUtil;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.interfaces.IObfuscationEnvironment;
import org.spongepowered.tools.obfuscation.mapping.IMappingConsumer;
import org.spongepowered.tools.obfuscation.mapping.IMappingProvider;
import org.spongepowered.tools.obfuscation.mapping.IMappingWriter;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;

public abstract class ObfuscationEnvironment implements IObfuscationEnvironment {
   protected final ObfuscationType type;
   protected final IMappingProvider mappingProvider;
   protected final IMappingWriter mappingWriter;
   protected final ObfuscationEnvironment.RemapperProxy remapper = new ObfuscationEnvironment.RemapperProxy(this);
   protected final IMixinAnnotationProcessor ap;
   protected final String outFileName;
   protected final List inFileNames;
   private boolean initDone;

   protected ObfuscationEnvironment(ObfuscationType var1) {
      this.type = var1;
      this.ap = var1.getAnnotationProcessor();
      this.inFileNames = var1.getInputFileNames();
      this.outFileName = var1.getOutputFileName();
      this.mappingProvider = this.getMappingProvider(this.ap, this.ap.getProcessingEnvironment().getFiler());
      this.mappingWriter = this.getMappingWriter(this.ap, this.ap.getProcessingEnvironment().getFiler());
   }

   public String toString() {
      return this.type.toString();
   }

   protected abstract IMappingProvider getMappingProvider(Messager var1, Filer var2);

   protected abstract IMappingWriter getMappingWriter(Messager var1, Filer var2);

   private boolean initMappings() {
      if (!this.initDone) {
         this.initDone = true;
         if (this.inFileNames == null) {
            this.ap.printMessage(Kind.ERROR, "The " + this.type.getConfig().getInputFileOption() + " argument was not supplied, obfuscation processing will not occur");
            return false;
         }

         int var1 = 0;
         Iterator var2 = this.inFileNames.iterator();

         while(var2.hasNext()) {
            String var3 = (String)var2.next();
            File var4 = new File(var3);

            try {
               if (var4.isFile()) {
                  this.ap.printMessage(Kind.NOTE, "Loading " + this.type + " mappings from " + var4.getAbsolutePath());
                  this.mappingProvider.read(var4);
                  ++var1;
               }
            } catch (Exception var6) {
               var6.printStackTrace();
            }
         }

         if (var1 < 1) {
            this.ap.printMessage(Kind.ERROR, "No valid input files for " + this.type + " could be read, processing may not be sucessful.");
            this.mappingProvider.clear();
         }
      }

      return !this.mappingProvider.isEmpty();
   }

   public ObfuscationType getType() {
      return this.type;
   }

   public MappingMethod getObfMethod(MemberInfo var1) {
      MappingMethod var2 = this.getObfMethod(var1.asMethodMapping());
      if (var2 == null && var1.isFullyQualified()) {
         TypeHandle var3 = this.ap.getTypeProvider().getTypeHandle(var1.owner);
         if (var3 != null && !var3.isImaginary()) {
            TypeMirror var4 = var3.getElement().getSuperclass();
            if (var4.getKind() != TypeKind.DECLARED) {
               return null;
            } else {
               String var5 = ((TypeElement)((DeclaredType)var4).asElement()).getQualifiedName().toString();
               return this.getObfMethod(new MemberInfo(var1.name, var5.replace('.', '/'), var1.desc, var1.matchAll));
            }
         } else {
            return null;
         }
      } else {
         return var2;
      }
   }

   public MappingMethod getObfMethod(MappingMethod var1) {
      return this.getObfMethod(var1, true);
   }

   public MappingMethod getObfMethod(MappingMethod var1, boolean var2) {
      if (!this.initMappings()) {
         return null;
      } else {
         boolean var3 = true;
         MappingMethod var4 = null;

         for(MappingMethod var5 = var1; var5 != null && var4 == null; var5 = var5.getSuper()) {
            var4 = this.mappingProvider.getMethodMapping(var5);
         }

         if (var4 == null) {
            if (var2) {
               return null;
            }

            var4 = var1.copy();
            var3 = false;
         }

         String var7 = this.getObfClass(var4.getOwner());
         if (var7 != null && !var7.equals(var1.getOwner()) && !var7.equals(var4.getOwner())) {
            if (var3) {
               return var4.move(var7);
            } else {
               String var6 = ObfuscationUtil.mapDescriptor(var4.getDesc(), this.remapper);
               return new MappingMethod(var7, var4.getSimpleName(), var6);
            }
         } else {
            return var3 ? var4 : null;
         }
      }
   }

   public MemberInfo remapDescriptor(MemberInfo var1) {
      boolean var2 = false;
      String var3 = var1.owner;
      String var4;
      if (var3 != null) {
         var4 = this.remapper.map(var3);
         if (var4 != null) {
            var3 = var4;
            var2 = true;
         }
      }

      var4 = var1.desc;
      if (var4 != null) {
         String var5 = ObfuscationUtil.mapDescriptor(var1.desc, this.remapper);
         if (!var5.equals(var1.desc)) {
            var4 = var5;
            var2 = true;
         }
      }

      return var2 ? new MemberInfo(var1.name, var3, var4, var1.matchAll) : null;
   }

   public String remapDescriptor(String var1) {
      return ObfuscationUtil.mapDescriptor(var1, this.remapper);
   }

   public MappingField getObfField(MemberInfo var1) {
      return this.getObfField(var1.asFieldMapping(), true);
   }

   public MappingField getObfField(MappingField var1) {
      return this.getObfField(var1, true);
   }

   public MappingField getObfField(MappingField var1, boolean var2) {
      if (!this.initMappings()) {
         return null;
      } else {
         MappingField var3 = this.mappingProvider.getFieldMapping(var1);
         if (var3 == null) {
            if (var2) {
               return null;
            }

            var3 = var1;
         }

         String var4 = this.getObfClass(var3.getOwner());
         if (var4 != null && !var4.equals(var1.getOwner()) && !var4.equals(var3.getOwner())) {
            return var3.move(var4);
         } else {
            return var3 != var1 ? var3 : null;
         }
      }
   }

   public String getObfClass(String var1) {
      return !this.initMappings() ? null : this.mappingProvider.getClassMapping(var1);
   }

   public void writeMappings(Collection var1) {
      IMappingConsumer.MappingSet var2 = new IMappingConsumer.MappingSet();
      IMappingConsumer.MappingSet var3 = new IMappingConsumer.MappingSet();
      Iterator var4 = var1.iterator();

      while(var4.hasNext()) {
         IMappingConsumer var5 = (IMappingConsumer)var4.next();
         var2.addAll(var5.getFieldMappings(this.type));
         var3.addAll(var5.getMethodMappings(this.type));
      }

      this.mappingWriter.write(this.outFileName, this.type, var2, var3);
   }

   final class RemapperProxy implements ObfuscationUtil.IClassRemapper {
      final ObfuscationEnvironment this$0;

      RemapperProxy(ObfuscationEnvironment var1) {
         this.this$0 = var1;
      }

      public String map(String var1) {
         return this.this$0.mappingProvider == null ? null : this.this$0.mappingProvider.getClassMapping(var1);
      }

      public String unmap(String var1) {
         return this.this$0.mappingProvider == null ? null : this.this$0.mappingProvider.getClassMapping(var1);
      }
   }
}
