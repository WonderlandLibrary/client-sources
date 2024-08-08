package org.spongepowered.asm.lib.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.spongepowered.asm.lib.AnnotationVisitor;
import org.spongepowered.asm.lib.Attribute;
import org.spongepowered.asm.lib.ClassVisitor;
import org.spongepowered.asm.lib.FieldVisitor;
import org.spongepowered.asm.lib.MethodVisitor;
import org.spongepowered.asm.lib.TypePath;

public class ClassNode extends ClassVisitor {
   public int version;
   public int access;
   public String name;
   public String signature;
   public String superName;
   public List interfaces;
   public String sourceFile;
   public String sourceDebug;
   public String outerClass;
   public String outerMethod;
   public String outerMethodDesc;
   public List visibleAnnotations;
   public List invisibleAnnotations;
   public List visibleTypeAnnotations;
   public List invisibleTypeAnnotations;
   public List attrs;
   public List innerClasses;
   public List fields;
   public List methods;

   public ClassNode() {
      this(327680);
      if (this.getClass() != ClassNode.class) {
         throw new IllegalStateException();
      }
   }

   public ClassNode(int var1) {
      super(var1);
      this.interfaces = new ArrayList();
      this.innerClasses = new ArrayList();
      this.fields = new ArrayList();
      this.methods = new ArrayList();
   }

   public void visit(int var1, int var2, String var3, String var4, String var5, String[] var6) {
      this.version = var1;
      this.access = var2;
      this.name = var3;
      this.signature = var4;
      this.superName = var5;
      if (var6 != null) {
         this.interfaces.addAll(Arrays.asList(var6));
      }

   }

   public void visitSource(String var1, String var2) {
      this.sourceFile = var1;
      this.sourceDebug = var2;
   }

   public void visitOuterClass(String var1, String var2, String var3) {
      this.outerClass = var1;
      this.outerMethod = var2;
      this.outerMethodDesc = var3;
   }

   public AnnotationVisitor visitAnnotation(String var1, boolean var2) {
      AnnotationNode var3 = new AnnotationNode(var1);
      if (var2) {
         if (this.visibleAnnotations == null) {
            this.visibleAnnotations = new ArrayList(1);
         }

         this.visibleAnnotations.add(var3);
      } else {
         if (this.invisibleAnnotations == null) {
            this.invisibleAnnotations = new ArrayList(1);
         }

         this.invisibleAnnotations.add(var3);
      }

      return var3;
   }

   public AnnotationVisitor visitTypeAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      TypeAnnotationNode var5 = new TypeAnnotationNode(var1, var2, var3);
      if (var4) {
         if (this.visibleTypeAnnotations == null) {
            this.visibleTypeAnnotations = new ArrayList(1);
         }

         this.visibleTypeAnnotations.add(var5);
      } else {
         if (this.invisibleTypeAnnotations == null) {
            this.invisibleTypeAnnotations = new ArrayList(1);
         }

         this.invisibleTypeAnnotations.add(var5);
      }

      return var5;
   }

   public void visitAttribute(Attribute var1) {
      if (this.attrs == null) {
         this.attrs = new ArrayList(1);
      }

      this.attrs.add(var1);
   }

   public void visitInnerClass(String var1, String var2, String var3, int var4) {
      InnerClassNode var5 = new InnerClassNode(var1, var2, var3, var4);
      this.innerClasses.add(var5);
   }

   public FieldVisitor visitField(int var1, String var2, String var3, String var4, Object var5) {
      FieldNode var6 = new FieldNode(var1, var2, var3, var4, var5);
      this.fields.add(var6);
      return var6;
   }

   public MethodVisitor visitMethod(int var1, String var2, String var3, String var4, String[] var5) {
      MethodNode var6 = new MethodNode(var1, var2, var3, var4, var5);
      this.methods.add(var6);
      return var6;
   }

   public void visitEnd() {
   }

   public void check(int var1) {
      if (var1 == 262144) {
         if (this.visibleTypeAnnotations != null && this.visibleTypeAnnotations.size() > 0) {
            throw new RuntimeException();
         }

         if (this.invisibleTypeAnnotations != null && this.invisibleTypeAnnotations.size() > 0) {
            throw new RuntimeException();
         }

         Iterator var2 = this.fields.iterator();

         while(var2.hasNext()) {
            FieldNode var3 = (FieldNode)var2.next();
            var3.check(var1);
         }

         var2 = this.methods.iterator();

         while(var2.hasNext()) {
            MethodNode var4 = (MethodNode)var2.next();
            var4.check(var1);
         }
      }

   }

   public void accept(ClassVisitor var1) {
      String[] var2 = new String[this.interfaces.size()];
      this.interfaces.toArray(var2);
      var1.visit(this.version, this.access, this.name, this.signature, this.superName, var2);
      if (this.sourceFile != null || this.sourceDebug != null) {
         var1.visitSource(this.sourceFile, this.sourceDebug);
      }

      if (this.outerClass != null) {
         var1.visitOuterClass(this.outerClass, this.outerMethod, this.outerMethodDesc);
      }

      int var3 = this.visibleAnnotations == null ? 0 : this.visibleAnnotations.size();

      int var4;
      AnnotationNode var5;
      for(var4 = 0; var4 < var3; ++var4) {
         var5 = (AnnotationNode)this.visibleAnnotations.get(var4);
         var5.accept(var1.visitAnnotation(var5.desc, true));
      }

      var3 = this.invisibleAnnotations == null ? 0 : this.invisibleAnnotations.size();

      for(var4 = 0; var4 < var3; ++var4) {
         var5 = (AnnotationNode)this.invisibleAnnotations.get(var4);
         var5.accept(var1.visitAnnotation(var5.desc, false));
      }

      var3 = this.visibleTypeAnnotations == null ? 0 : this.visibleTypeAnnotations.size();

      TypeAnnotationNode var6;
      for(var4 = 0; var4 < var3; ++var4) {
         var6 = (TypeAnnotationNode)this.visibleTypeAnnotations.get(var4);
         var6.accept(var1.visitTypeAnnotation(var6.typeRef, var6.typePath, var6.desc, true));
      }

      var3 = this.invisibleTypeAnnotations == null ? 0 : this.invisibleTypeAnnotations.size();

      for(var4 = 0; var4 < var3; ++var4) {
         var6 = (TypeAnnotationNode)this.invisibleTypeAnnotations.get(var4);
         var6.accept(var1.visitTypeAnnotation(var6.typeRef, var6.typePath, var6.desc, false));
      }

      var3 = this.attrs == null ? 0 : this.attrs.size();

      for(var4 = 0; var4 < var3; ++var4) {
         var1.visitAttribute((Attribute)this.attrs.get(var4));
      }

      for(var4 = 0; var4 < this.innerClasses.size(); ++var4) {
         ((InnerClassNode)this.innerClasses.get(var4)).accept(var1);
      }

      for(var4 = 0; var4 < this.fields.size(); ++var4) {
         ((FieldNode)this.fields.get(var4)).accept(var1);
      }

      for(var4 = 0; var4 < this.methods.size(); ++var4) {
         ((MethodNode)this.methods.get(var4)).accept(var1);
      }

      var1.visitEnd();
   }
}
