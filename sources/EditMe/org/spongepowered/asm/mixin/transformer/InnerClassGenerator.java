package org.spongepowered.asm.mixin.transformer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.lib.AnnotationVisitor;
import org.spongepowered.asm.lib.ClassReader;
import org.spongepowered.asm.lib.ClassVisitor;
import org.spongepowered.asm.lib.commons.ClassRemapper;
import org.spongepowered.asm.lib.commons.Remapper;
import org.spongepowered.asm.mixin.transformer.ext.IClassGenerator;
import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;
import org.spongepowered.asm.service.MixinService;
import org.spongepowered.asm.transformers.MixinClassWriter;

final class InnerClassGenerator implements IClassGenerator {
   private static final Logger logger = LogManager.getLogger("mixin");
   private final Map innerClassNames = new HashMap();
   private final Map innerClasses = new HashMap();

   public String registerInnerClass(MixinInfo var1, String var2, MixinTargetContext var3) {
      String var4 = String.format("%s%s", var2, var3);
      String var5 = (String)this.innerClassNames.get(var4);
      if (var5 == null) {
         var5 = getUniqueReference(var2, var3);
         this.innerClassNames.put(var4, var5);
         this.innerClasses.put(var5, new InnerClassGenerator.InnerClassInfo(var5, var2, var1, var3));
         logger.debug("Inner class {} in {} on {} gets unique name {}", new Object[]{var2, var1.getClassRef(), var3.getTargetClassRef(), var5});
      }

      return var5;
   }

   public byte[] generate(String var1) {
      String var2 = var1.replace('.', '/');
      InnerClassGenerator.InnerClassInfo var3 = (InnerClassGenerator.InnerClassInfo)this.innerClasses.get(var2);
      return var3 != null ? this.generate(var3) : null;
   }

   private byte[] generate(InnerClassGenerator.InnerClassInfo var1) {
      try {
         logger.debug("Generating mapped inner class {} (originally {})", new Object[]{var1.getName(), var1.getOriginalName()});
         ClassReader var2 = new ClassReader(var1.getClassBytes());
         MixinClassWriter var3 = new MixinClassWriter(var2, 0);
         var2.accept(new InnerClassGenerator.InnerClassAdapter(var3, var1), 8);
         return var3.toByteArray();
      } catch (InvalidMixinException var4) {
         throw var4;
      } catch (Exception var5) {
         logger.catching(var5);
         return null;
      }
   }

   private static String getUniqueReference(String var0, MixinTargetContext var1) {
      String var2 = var0.substring(var0.lastIndexOf(36) + 1);
      if (var2.matches("^[0-9]+$")) {
         var2 = "Anonymous";
      }

      return String.format("%s$%s$%s", var1.getTargetClassRef(), var2, UUID.randomUUID().toString().replace("-", ""));
   }

   static class InnerClassAdapter extends ClassRemapper {
      private final InnerClassGenerator.InnerClassInfo info;

      public InnerClassAdapter(ClassVisitor var1, InnerClassGenerator.InnerClassInfo var2) {
         super(327680, var1, var2);
         this.info = var2;
      }

      public void visitSource(String var1, String var2) {
         super.visitSource(var1, var2);
         AnnotationVisitor var3 = this.cv.visitAnnotation("Lorg/spongepowered/asm/mixin/transformer/meta/MixinInner;", false);
         var3.visit("mixin", this.info.getOwner().toString());
         var3.visit("name", this.info.getOriginalName().substring(this.info.getOriginalName().lastIndexOf(47) + 1));
         var3.visitEnd();
      }

      public void visitInnerClass(String var1, String var2, String var3, int var4) {
         if (var1.startsWith(this.info.getOriginalName() + "$")) {
            throw new InvalidMixinException(this.info.getOwner(), "Found unsupported nested inner class " + var1 + " in " + this.info.getOriginalName());
         } else {
            super.visitInnerClass(var1, var2, var3, var4);
         }
      }
   }

   static class InnerClassInfo extends Remapper {
      private final String name;
      private final String originalName;
      private final MixinInfo owner;
      private final MixinTargetContext target;
      private final String ownerName;
      private final String targetName;

      InnerClassInfo(String var1, String var2, MixinInfo var3, MixinTargetContext var4) {
         this.name = var1;
         this.originalName = var2;
         this.owner = var3;
         this.ownerName = var3.getClassRef();
         this.target = var4;
         this.targetName = var4.getTargetClassRef();
      }

      String getName() {
         return this.name;
      }

      String getOriginalName() {
         return this.originalName;
      }

      MixinInfo getOwner() {
         return this.owner;
      }

      MixinTargetContext getTarget() {
         return this.target;
      }

      String getOwnerName() {
         return this.ownerName;
      }

      String getTargetName() {
         return this.targetName;
      }

      byte[] getClassBytes() throws ClassNotFoundException, IOException {
         return MixinService.getService().getBytecodeProvider().getClassBytes(this.originalName, true);
      }

      public String mapMethodName(String var1, String var2, String var3) {
         if (this.ownerName.equalsIgnoreCase(var1)) {
            ClassInfo.Method var4 = this.owner.getClassInfo().findMethod(var2, var3, 10);
            if (var4 != null) {
               return var4.getName();
            }
         }

         return super.mapMethodName(var1, var2, var3);
      }

      public String map(String var1) {
         if (this.originalName.equals(var1)) {
            return this.name;
         } else {
            return this.ownerName.equals(var1) ? this.targetName : var1;
         }
      }

      public String toString() {
         return this.name;
      }
   }
}
