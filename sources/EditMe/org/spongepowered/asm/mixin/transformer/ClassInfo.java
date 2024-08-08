package org.spongepowered.asm.mixin.transformer;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.lib.tree.FieldInsnNode;
import org.spongepowered.asm.lib.tree.FieldNode;
import org.spongepowered.asm.lib.tree.FrameNode;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.service.MixinService;
import org.spongepowered.asm.util.Annotations;
import org.spongepowered.asm.util.ClassSignature;
import org.spongepowered.asm.util.perf.Profiler;

public final class ClassInfo {
   public static final int INCLUDE_PRIVATE = 2;
   public static final int INCLUDE_STATIC = 8;
   public static final int INCLUDE_ALL = 10;
   private static final Logger logger = LogManager.getLogger("mixin");
   private static final Profiler profiler = MixinEnvironment.getProfiler();
   private static final String JAVA_LANG_OBJECT = "java/lang/Object";
   private static final Map cache = new HashMap();
   private static final ClassInfo OBJECT = new ClassInfo();
   private final String name;
   private final String superName;
   private final String outerName;
   private final boolean isProbablyStatic;
   private final Set interfaces;
   private final Set methods;
   private final Set fields;
   private final Set mixins = new HashSet();
   private final Map correspondingTypes = new HashMap();
   private final MixinInfo mixin;
   private final MethodMapper methodMapper;
   private final boolean isMixin;
   private final boolean isInterface;
   private final int access;
   private ClassInfo superClass;
   private ClassInfo outerClass;
   private ClassSignature signature;

   private ClassInfo() {
      this.name = "java/lang/Object";
      this.superName = null;
      this.outerName = null;
      this.isProbablyStatic = true;
      this.methods = ImmutableSet.of(new ClassInfo.Method(this, "getClass", "()Ljava/lang/Class;"), new ClassInfo.Method(this, "hashCode", "()I"), new ClassInfo.Method(this, "equals", "(Ljava/lang/Object;)Z"), new ClassInfo.Method(this, "clone", "()Ljava/lang/Object;"), new ClassInfo.Method(this, "toString", "()Ljava/lang/String;"), new ClassInfo.Method(this, "notify", "()V"), new ClassInfo.Method[]{new ClassInfo.Method(this, "notifyAll", "()V"), new ClassInfo.Method(this, "wait", "(J)V"), new ClassInfo.Method(this, "wait", "(JI)V"), new ClassInfo.Method(this, "wait", "()V"), new ClassInfo.Method(this, "finalize", "()V")});
      this.fields = Collections.emptySet();
      this.isInterface = false;
      this.interfaces = Collections.emptySet();
      this.access = 1;
      this.isMixin = false;
      this.mixin = null;
      this.methodMapper = null;
   }

   private ClassInfo(ClassNode var1) {
      Profiler.Section var2 = profiler.begin(1, (String)"class.meta");

      try {
         this.name = var1.name;
         this.superName = var1.superName != null ? var1.superName : "java/lang/Object";
         this.methods = new HashSet();
         this.fields = new HashSet();
         this.isInterface = (var1.access & 512) != 0;
         this.interfaces = new HashSet();
         this.access = var1.access;
         this.isMixin = var1 instanceof MixinInfo.MixinClassNode;
         this.mixin = this.isMixin ? ((MixinInfo.MixinClassNode)var1).getMixin() : null;
         this.interfaces.addAll(var1.interfaces);
         Iterator var3 = var1.methods.iterator();

         while(var3.hasNext()) {
            MethodNode var4 = (MethodNode)var3.next();
            this.addMethod(var4, this.isMixin);
         }

         boolean var10 = true;
         String var11 = var1.outerClass;

         FieldNode var6;
         for(Iterator var5 = var1.fields.iterator(); var5.hasNext(); this.fields.add(new ClassInfo.Field(this, var6, this.isMixin))) {
            var6 = (FieldNode)var5.next();
            if ((var6.access & 4096) != 0 && var6.name.startsWith("this$")) {
               var10 = false;
               if (var11 == null) {
                  var11 = var6.desc;
                  if (var11 != null && var11.startsWith("L")) {
                     var11 = var11.substring(1, var11.length() - 1);
                  }
               }
            }
         }

         this.isProbablyStatic = var10;
         this.outerName = var11;
         this.methodMapper = new MethodMapper(MixinEnvironment.getCurrentEnvironment(), this);
         this.signature = ClassSignature.ofLazy(var1);
      } finally {
         var2.end();
      }

   }

   void addInterface(String var1) {
      this.interfaces.add(var1);
      this.getSignature().addInterface(var1);
   }

   void addMethod(MethodNode var1) {
      this.addMethod(var1, true);
   }

   private void addMethod(MethodNode var1, boolean var2) {
      if (!var1.name.startsWith("<")) {
         this.methods.add(new ClassInfo.Method(this, var1, var2));
      }

   }

   void addMixin(MixinInfo var1) {
      if (this.isMixin) {
         throw new IllegalArgumentException("Cannot add target " + this.name + " for " + var1.getClassName() + " because the target is a mixin");
      } else {
         this.mixins.add(var1);
      }
   }

   public Set getMixins() {
      return Collections.unmodifiableSet(this.mixins);
   }

   public boolean isMixin() {
      return this.isMixin;
   }

   public boolean isPublic() {
      return (this.access & 1) != 0;
   }

   public boolean isAbstract() {
      return (this.access & 1024) != 0;
   }

   public boolean isSynthetic() {
      return (this.access & 4096) != 0;
   }

   public boolean isProbablyStatic() {
      return this.isProbablyStatic;
   }

   public boolean isInner() {
      return this.outerName != null;
   }

   public boolean isInterface() {
      return this.isInterface;
   }

   public Set getInterfaces() {
      return Collections.unmodifiableSet(this.interfaces);
   }

   public String toString() {
      return this.name;
   }

   public MethodMapper getMethodMapper() {
      return this.methodMapper;
   }

   public int getAccess() {
      return this.access;
   }

   public String getName() {
      return this.name;
   }

   public String getClassName() {
      return this.name.replace('/', '.');
   }

   public String getSuperName() {
      return this.superName;
   }

   public ClassInfo getSuperClass() {
      if (this.superClass == null && this.superName != null) {
         this.superClass = forName(this.superName);
      }

      return this.superClass;
   }

   public String getOuterName() {
      return this.outerName;
   }

   public ClassInfo getOuterClass() {
      if (this.outerClass == null && this.outerName != null) {
         this.outerClass = forName(this.outerName);
      }

      return this.outerClass;
   }

   public ClassSignature getSignature() {
      return this.signature.wake();
   }

   List getTargets() {
      if (this.mixin != null) {
         ArrayList var1 = new ArrayList();
         var1.add(this);
         var1.addAll(this.mixin.getTargets());
         return var1;
      } else {
         return ImmutableList.of(this);
      }
   }

   public Set getMethods() {
      return Collections.unmodifiableSet(this.methods);
   }

   public Set getInterfaceMethods(boolean var1) {
      HashSet var2 = new HashSet();
      ClassInfo var3 = this.addMethodsRecursive(var2, var1);
      if (!this.isInterface) {
         while(var3 != null && var3 != OBJECT) {
            var3 = var3.addMethodsRecursive(var2, var1);
         }
      }

      Iterator var4 = var2.iterator();

      while(var4.hasNext()) {
         if (!((ClassInfo.Method)var4.next()).isAbstract()) {
            var4.remove();
         }
      }

      return Collections.unmodifiableSet(var2);
   }

   private ClassInfo addMethodsRecursive(Set var1, boolean var2) {
      Iterator var3;
      ClassInfo.Method var4;
      if (this.isInterface) {
         for(var3 = this.methods.iterator(); var3.hasNext(); var1.add(var4)) {
            var4 = (ClassInfo.Method)var3.next();
            if (!var4.isAbstract()) {
               var1.remove(var4);
            }
         }
      } else if (!this.isMixin && var2) {
         var3 = this.mixins.iterator();

         while(var3.hasNext()) {
            MixinInfo var5 = (MixinInfo)var3.next();
            var5.getClassInfo().addMethodsRecursive(var1, var2);
         }
      }

      var3 = this.interfaces.iterator();

      while(var3.hasNext()) {
         String var6 = (String)var3.next();
         forName(var6).addMethodsRecursive(var1, var2);
      }

      return this.getSuperClass();
   }

   public boolean hasSuperClass(String var1) {
      return this.hasSuperClass(var1, ClassInfo.Traversal.NONE);
   }

   public boolean hasSuperClass(String var1, ClassInfo.Traversal var2) {
      if ("java/lang/Object".equals(var1)) {
         return true;
      } else {
         return this.findSuperClass(var1, var2) != null;
      }
   }

   public boolean hasSuperClass(ClassInfo var1) {
      return this.hasSuperClass(var1, ClassInfo.Traversal.NONE, false);
   }

   public boolean hasSuperClass(ClassInfo var1, ClassInfo.Traversal var2) {
      return this.hasSuperClass(var1, var2, false);
   }

   public boolean hasSuperClass(ClassInfo var1, ClassInfo.Traversal var2, boolean var3) {
      if (OBJECT == var1) {
         return true;
      } else {
         return this.findSuperClass(var1.name, var2, var3) != null;
      }
   }

   public ClassInfo findSuperClass(String var1) {
      return this.findSuperClass(var1, ClassInfo.Traversal.NONE);
   }

   public ClassInfo findSuperClass(String var1, ClassInfo.Traversal var2) {
      return this.findSuperClass(var1, var2, false, new HashSet());
   }

   public ClassInfo findSuperClass(String var1, ClassInfo.Traversal var2, boolean var3) {
      return OBJECT.name.equals(var1) ? null : this.findSuperClass(var1, var2, var3, new HashSet());
   }

   private ClassInfo findSuperClass(String var1, ClassInfo.Traversal var2, boolean var3, Set var4) {
      ClassInfo var5 = this.getSuperClass();
      Iterator var6;
      if (var5 != null) {
         var6 = var5.getTargets().iterator();

         while(var6.hasNext()) {
            ClassInfo var7 = (ClassInfo)var6.next();
            if (var1.equals(var7.getName())) {
               return var5;
            }

            ClassInfo var8 = var7.findSuperClass(var1, var2.next(), var3, var4);
            if (var8 != null) {
               return var8;
            }
         }
      }

      if (var3) {
         ClassInfo var11 = this.findInterface(var1);
         if (var11 != null) {
            return var11;
         }
      }

      if (var2.canTraverse()) {
         var6 = this.mixins.iterator();

         while(var6.hasNext()) {
            MixinInfo var12 = (MixinInfo)var6.next();
            String var13 = var12.getClassName();
            if (!var4.contains(var13)) {
               var4.add(var13);
               ClassInfo var9 = var12.getClassInfo();
               if (var1.equals(var9.getName())) {
                  return var9;
               }

               ClassInfo var10 = var9.findSuperClass(var1, ClassInfo.Traversal.ALL, var3, var4);
               if (var10 != null) {
                  return var10;
               }
            }
         }
      }

      return null;
   }

   private ClassInfo findInterface(String var1) {
      Iterator var2 = this.getInterfaces().iterator();

      ClassInfo var5;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         String var3 = (String)var2.next();
         ClassInfo var4 = forName(var3);
         if (var1.equals(var3)) {
            return var4;
         }

         var5 = var4.findInterface(var1);
      } while(var5 == null);

      return var5;
   }

   ClassInfo findCorrespondingType(ClassInfo var1) {
      if (var1 != null && var1.isMixin && !this.isMixin) {
         ClassInfo var2 = (ClassInfo)this.correspondingTypes.get(var1);
         if (var2 == null) {
            var2 = this.findSuperTypeForMixin(var1);
            this.correspondingTypes.put(var1, var2);
         }

         return var2;
      } else {
         return null;
      }
   }

   private ClassInfo findSuperTypeForMixin(ClassInfo var1) {
      for(ClassInfo var2 = this; var2 != null && var2 != OBJECT; var2 = var2.getSuperClass()) {
         Iterator var3 = var2.mixins.iterator();

         while(var3.hasNext()) {
            MixinInfo var4 = (MixinInfo)var3.next();
            if (var4.getClassInfo().equals(var1)) {
               return var2;
            }
         }
      }

      return null;
   }

   public boolean hasMixinInHierarchy() {
      if (!this.isMixin) {
         return false;
      } else {
         for(ClassInfo var1 = this.getSuperClass(); var1 != null && var1 != OBJECT; var1 = var1.getSuperClass()) {
            if (var1.isMixin) {
               return true;
            }
         }

         return false;
      }
   }

   public boolean hasMixinTargetInHierarchy() {
      if (this.isMixin) {
         return false;
      } else {
         for(ClassInfo var1 = this.getSuperClass(); var1 != null && var1 != OBJECT; var1 = var1.getSuperClass()) {
            if (var1.mixins.size() > 0) {
               return true;
            }
         }

         return false;
      }
   }

   public ClassInfo.Method findMethodInHierarchy(MethodNode var1, ClassInfo.SearchType var2) {
      return this.findMethodInHierarchy(var1.name, var1.desc, var2, ClassInfo.Traversal.NONE);
   }

   public ClassInfo.Method findMethodInHierarchy(MethodNode var1, ClassInfo.SearchType var2, int var3) {
      return this.findMethodInHierarchy(var1.name, var1.desc, var2, ClassInfo.Traversal.NONE, var3);
   }

   public ClassInfo.Method findMethodInHierarchy(MethodInsnNode var1, ClassInfo.SearchType var2) {
      return this.findMethodInHierarchy(var1.name, var1.desc, var2, ClassInfo.Traversal.NONE);
   }

   public ClassInfo.Method findMethodInHierarchy(MethodInsnNode var1, ClassInfo.SearchType var2, int var3) {
      return this.findMethodInHierarchy(var1.name, var1.desc, var2, ClassInfo.Traversal.NONE, var3);
   }

   public ClassInfo.Method findMethodInHierarchy(String var1, String var2, ClassInfo.SearchType var3) {
      return this.findMethodInHierarchy(var1, var2, var3, ClassInfo.Traversal.NONE);
   }

   public ClassInfo.Method findMethodInHierarchy(String var1, String var2, ClassInfo.SearchType var3, ClassInfo.Traversal var4) {
      return this.findMethodInHierarchy(var1, var2, var3, var4, 0);
   }

   public ClassInfo.Method findMethodInHierarchy(String var1, String var2, ClassInfo.SearchType var3, ClassInfo.Traversal var4, int var5) {
      return (ClassInfo.Method)this.findInHierarchy(var1, var2, var3, var4, var5, ClassInfo.Member.Type.METHOD);
   }

   public ClassInfo.Field findFieldInHierarchy(FieldNode var1, ClassInfo.SearchType var2) {
      return this.findFieldInHierarchy(var1.name, var1.desc, var2, ClassInfo.Traversal.NONE);
   }

   public ClassInfo.Field findFieldInHierarchy(FieldNode var1, ClassInfo.SearchType var2, int var3) {
      return this.findFieldInHierarchy(var1.name, var1.desc, var2, ClassInfo.Traversal.NONE, var3);
   }

   public ClassInfo.Field findFieldInHierarchy(FieldInsnNode var1, ClassInfo.SearchType var2) {
      return this.findFieldInHierarchy(var1.name, var1.desc, var2, ClassInfo.Traversal.NONE);
   }

   public ClassInfo.Field findFieldInHierarchy(FieldInsnNode var1, ClassInfo.SearchType var2, int var3) {
      return this.findFieldInHierarchy(var1.name, var1.desc, var2, ClassInfo.Traversal.NONE, var3);
   }

   public ClassInfo.Field findFieldInHierarchy(String var1, String var2, ClassInfo.SearchType var3) {
      return this.findFieldInHierarchy(var1, var2, var3, ClassInfo.Traversal.NONE);
   }

   public ClassInfo.Field findFieldInHierarchy(String var1, String var2, ClassInfo.SearchType var3, ClassInfo.Traversal var4) {
      return this.findFieldInHierarchy(var1, var2, var3, var4, 0);
   }

   public ClassInfo.Field findFieldInHierarchy(String var1, String var2, ClassInfo.SearchType var3, ClassInfo.Traversal var4, int var5) {
      return (ClassInfo.Field)this.findInHierarchy(var1, var2, var3, var4, var5, ClassInfo.Member.Type.FIELD);
   }

   private ClassInfo.Member findInHierarchy(String var1, String var2, ClassInfo.SearchType var3, ClassInfo.Traversal var4, int var5, ClassInfo.Member.Type var6) {
      Iterator var8;
      ClassInfo.Member var10;
      if (var3 == ClassInfo.SearchType.ALL_CLASSES) {
         ClassInfo.Member var7 = this.findMember(var1, var2, var5, var6);
         if (var7 != null) {
            return var7;
         }

         if (var4.canTraverse()) {
            var8 = this.mixins.iterator();

            while(var8.hasNext()) {
               MixinInfo var9 = (MixinInfo)var8.next();
               var10 = var9.getClassInfo().findMember(var1, var2, var5, var6);
               if (var10 != null) {
                  return this.cloneMember(var10);
               }
            }
         }
      }

      ClassInfo var12 = this.getSuperClass();
      if (var12 != null) {
         var8 = var12.getTargets().iterator();

         while(var8.hasNext()) {
            ClassInfo var13 = (ClassInfo)var8.next();
            var10 = var13.findInHierarchy(var1, var2, ClassInfo.SearchType.ALL_CLASSES, var4.next(), var5 & -3, var6);
            if (var10 != null) {
               return var10;
            }
         }
      }

      if (var6 == ClassInfo.Member.Type.METHOD && (this.isInterface || MixinEnvironment.getCompatibilityLevel().supportsMethodsInInterfaces())) {
         var8 = this.interfaces.iterator();

         while(var8.hasNext()) {
            String var14 = (String)var8.next();
            ClassInfo var15 = forName(var14);
            if (var15 == null) {
               logger.debug("Failed to resolve declared interface {} on {}", new Object[]{var14, this.name});
            } else {
               ClassInfo.Member var11 = var15.findInHierarchy(var1, var2, ClassInfo.SearchType.ALL_CLASSES, var4.next(), var5 & -3, var6);
               if (var11 != null) {
                  return (ClassInfo.Member)(this.isInterface ? var11 : new ClassInfo.InterfaceMethod(this, var11));
               }
            }
         }
      }

      return null;
   }

   private ClassInfo.Member cloneMember(ClassInfo.Member var1) {
      return (ClassInfo.Member)(var1 instanceof ClassInfo.Method ? new ClassInfo.Method(this, var1) : new ClassInfo.Field(this, var1));
   }

   public ClassInfo.Method findMethod(MethodNode var1) {
      return this.findMethod(var1.name, var1.desc, var1.access);
   }

   public ClassInfo.Method findMethod(MethodNode var1, int var2) {
      return this.findMethod(var1.name, var1.desc, var2);
   }

   public ClassInfo.Method findMethod(MethodInsnNode var1) {
      return this.findMethod(var1.name, var1.desc, 0);
   }

   public ClassInfo.Method findMethod(MethodInsnNode var1, int var2) {
      return this.findMethod(var1.name, var1.desc, var2);
   }

   public ClassInfo.Method findMethod(String var1, String var2, int var3) {
      return (ClassInfo.Method)this.findMember(var1, var2, var3, ClassInfo.Member.Type.METHOD);
   }

   public ClassInfo.Field findField(FieldNode var1) {
      return this.findField(var1.name, var1.desc, var1.access);
   }

   public ClassInfo.Field findField(FieldInsnNode var1, int var2) {
      return this.findField(var1.name, var1.desc, var2);
   }

   public ClassInfo.Field findField(String var1, String var2, int var3) {
      return (ClassInfo.Field)this.findMember(var1, var2, var3, ClassInfo.Member.Type.FIELD);
   }

   private ClassInfo.Member findMember(String var1, String var2, int var3, ClassInfo.Member.Type var4) {
      Set var5 = var4 == ClassInfo.Member.Type.METHOD ? this.methods : this.fields;
      Iterator var6 = var5.iterator();

      ClassInfo.Member var7;
      do {
         if (!var6.hasNext()) {
            return null;
         }

         var7 = (ClassInfo.Member)var6.next();
      } while(!var7.equals(var1, var2) || !var7.matchesFlags(var3));

      return var7;
   }

   public boolean equals(Object var1) {
      return !(var1 instanceof ClassInfo) ? false : ((ClassInfo)var1).name.equals(this.name);
   }

   public int hashCode() {
      return this.name.hashCode();
   }

   static ClassInfo fromClassNode(ClassNode var0) {
      ClassInfo var1 = (ClassInfo)cache.get(var0.name);
      if (var1 == null) {
         var1 = new ClassInfo(var0);
         cache.put(var0.name, var1);
      }

      return var1;
   }

   public static ClassInfo forName(String var0) {
      var0 = var0.replace('.', '/');
      ClassInfo var1 = (ClassInfo)cache.get(var0);
      if (var1 == null) {
         try {
            ClassNode var2 = MixinService.getService().getBytecodeProvider().getClassNode(var0);
            var1 = new ClassInfo(var2);
         } catch (Exception var3) {
            logger.catching(Level.TRACE, var3);
            logger.warn("Error loading class: {} ({}: {})", new Object[]{var0, var3.getClass().getName(), var3.getMessage()});
         }

         cache.put(var0, var1);
         logger.trace("Added class metadata for {} to metadata cache", new Object[]{var0});
      }

      return var1;
   }

   public static ClassInfo forType(org.spongepowered.asm.lib.Type var0) {
      if (var0.getSort() == 9) {
         return forType(var0.getElementType());
      } else {
         return var0.getSort() < 9 ? null : forName(var0.getClassName().replace('.', '/'));
      }
   }

   public static ClassInfo getCommonSuperClass(String var0, String var1) {
      return var0 != null && var1 != null ? getCommonSuperClass(forName(var0), forName(var1)) : OBJECT;
   }

   public static ClassInfo getCommonSuperClass(org.spongepowered.asm.lib.Type var0, org.spongepowered.asm.lib.Type var1) {
      return var0 != null && var1 != null && var0.getSort() == 10 && var1.getSort() == 10 ? getCommonSuperClass(forType(var0), forType(var1)) : OBJECT;
   }

   private static ClassInfo getCommonSuperClass(ClassInfo var0, ClassInfo var1) {
      return getCommonSuperClass(var0, var1, false);
   }

   public static ClassInfo getCommonSuperClassOrInterface(String var0, String var1) {
      return var0 != null && var1 != null ? getCommonSuperClassOrInterface(forName(var0), forName(var1)) : OBJECT;
   }

   public static ClassInfo getCommonSuperClassOrInterface(org.spongepowered.asm.lib.Type var0, org.spongepowered.asm.lib.Type var1) {
      return var0 != null && var1 != null && var0.getSort() == 10 && var1.getSort() == 10 ? getCommonSuperClassOrInterface(forType(var0), forType(var1)) : OBJECT;
   }

   public static ClassInfo getCommonSuperClassOrInterface(ClassInfo var0, ClassInfo var1) {
      return getCommonSuperClass(var0, var1, true);
   }

   private static ClassInfo getCommonSuperClass(ClassInfo var0, ClassInfo var1, boolean var2) {
      if (var0.hasSuperClass(var1, ClassInfo.Traversal.NONE, var2)) {
         return var1;
      } else if (var1.hasSuperClass(var0, ClassInfo.Traversal.NONE, var2)) {
         return var0;
      } else if (!var0.isInterface() && !var1.isInterface()) {
         do {
            var0 = var0.getSuperClass();
            if (var0 == null) {
               return OBJECT;
            }
         } while(!var1.hasSuperClass(var0, ClassInfo.Traversal.NONE, var2));

         return var0;
      } else {
         return OBJECT;
      }
   }

   static {
      cache.put("java/lang/Object", OBJECT);
   }

   class Field extends ClassInfo.Member {
      final ClassInfo this$0;

      public Field(ClassInfo var1, ClassInfo.Member var2) {
         super(var2);
         this.this$0 = var1;
      }

      public Field(ClassInfo var1, FieldNode var2) {
         this(var1, var2, false);
      }

      public Field(ClassInfo var1, FieldNode var2, boolean var3) {
         super(ClassInfo.Member.Type.FIELD, var2.name, var2.desc, var2.access, var3);
         this.this$0 = var1;
         this.setUnique(Annotations.getVisible(var2, Unique.class) != null);
         if (Annotations.getVisible(var2, Shadow.class) != null) {
            boolean var4 = Annotations.getVisible(var2, Final.class) != null;
            boolean var5 = Annotations.getVisible(var2, Mutable.class) != null;
            this.setDecoratedFinal(var4, var5);
         }

      }

      public Field(ClassInfo var1, String var2, String var3, int var4) {
         super(ClassInfo.Member.Type.FIELD, var2, var3, var4, false);
         this.this$0 = var1;
      }

      public Field(ClassInfo var1, String var2, String var3, int var4, boolean var5) {
         super(ClassInfo.Member.Type.FIELD, var2, var3, var4, var5);
         this.this$0 = var1;
      }

      public ClassInfo getOwner() {
         return this.this$0;
      }

      public boolean equals(Object var1) {
         return !(var1 instanceof ClassInfo.Field) ? false : super.equals(var1);
      }

      protected String getDisplayFormat() {
         return "%s:%s";
      }
   }

   public class InterfaceMethod extends ClassInfo.Method {
      private final ClassInfo owner;
      final ClassInfo this$0;

      public InterfaceMethod(ClassInfo var1, ClassInfo.Member var2) {
         super(var1, var2);
         this.this$0 = var1;
         this.owner = var2.getOwner();
      }

      public ClassInfo getOwner() {
         return this.owner;
      }

      public ClassInfo getImplementor() {
         return this.this$0;
      }
   }

   public class Method extends ClassInfo.Member {
      private final List frames;
      private boolean isAccessor;
      final ClassInfo this$0;

      public Method(ClassInfo var1, ClassInfo.Member var2) {
         super(var2);
         this.this$0 = var1;
         this.frames = var2 instanceof ClassInfo.Method ? ((ClassInfo.Method)var2).frames : null;
      }

      public Method(ClassInfo var1, MethodNode var2) {
         this(var1, var2, false);
         this.setUnique(Annotations.getVisible(var2, Unique.class) != null);
         this.isAccessor = Annotations.getSingleVisible(var2, Accessor.class, Invoker.class) != null;
      }

      public Method(ClassInfo var1, MethodNode var2, boolean var3) {
         super(ClassInfo.Member.Type.METHOD, var2.name, var2.desc, var2.access, var3);
         this.this$0 = var1;
         this.frames = this.gatherFrames(var2);
         this.setUnique(Annotations.getVisible(var2, Unique.class) != null);
         this.isAccessor = Annotations.getSingleVisible(var2, Accessor.class, Invoker.class) != null;
      }

      public Method(ClassInfo var1, String var2, String var3) {
         super(ClassInfo.Member.Type.METHOD, var2, var3, 1, false);
         this.this$0 = var1;
         this.frames = null;
      }

      public Method(ClassInfo var1, String var2, String var3, int var4) {
         super(ClassInfo.Member.Type.METHOD, var2, var3, var4, false);
         this.this$0 = var1;
         this.frames = null;
      }

      public Method(ClassInfo var1, String var2, String var3, int var4, boolean var5) {
         super(ClassInfo.Member.Type.METHOD, var2, var3, var4, var5);
         this.this$0 = var1;
         this.frames = null;
      }

      private List gatherFrames(MethodNode var1) {
         ArrayList var2 = new ArrayList();
         ListIterator var3 = var1.instructions.iterator();

         while(var3.hasNext()) {
            AbstractInsnNode var4 = (AbstractInsnNode)var3.next();
            if (var4 instanceof FrameNode) {
               var2.add(new ClassInfo.FrameData(var1.instructions.indexOf(var4), (FrameNode)var4));
            }
         }

         return var2;
      }

      public List getFrames() {
         return this.frames;
      }

      public ClassInfo getOwner() {
         return this.this$0;
      }

      public boolean isAccessor() {
         return this.isAccessor;
      }

      public boolean equals(Object var1) {
         return !(var1 instanceof ClassInfo.Method) ? false : super.equals(var1);
      }

      public String toString() {
         return super.toString();
      }

      public int hashCode() {
         return super.hashCode();
      }

      public boolean equals(String var1, String var2) {
         return super.equals(var1, var2);
      }

      public String remapTo(String var1) {
         return super.remapTo(var1);
      }

      public String renameTo(String var1) {
         return super.renameTo(var1);
      }

      public int getAccess() {
         return super.getAccess();
      }

      public ClassInfo getImplementor() {
         return super.getImplementor();
      }

      public boolean matchesFlags(int var1) {
         return super.matchesFlags(var1);
      }

      public void setDecoratedFinal(boolean var1, boolean var2) {
         super.setDecoratedFinal(var1, var2);
      }

      public boolean isDecoratedMutable() {
         return super.isDecoratedMutable();
      }

      public boolean isDecoratedFinal() {
         return super.isDecoratedFinal();
      }

      public void setUnique(boolean var1) {
         super.setUnique(var1);
      }

      public boolean isUnique() {
         return super.isUnique();
      }

      public boolean isSynthetic() {
         return super.isSynthetic();
      }

      public boolean isFinal() {
         return super.isFinal();
      }

      public boolean isAbstract() {
         return super.isAbstract();
      }

      public boolean isStatic() {
         return super.isStatic();
      }

      public boolean isPrivate() {
         return super.isPrivate();
      }

      public boolean isRemapped() {
         return super.isRemapped();
      }

      public boolean isRenamed() {
         return super.isRenamed();
      }

      public boolean isInjected() {
         return super.isInjected();
      }

      public String getDesc() {
         return super.getDesc();
      }

      public String getOriginalDesc() {
         return super.getOriginalDesc();
      }

      public String getName() {
         return super.getName();
      }

      public String getOriginalName() {
         return super.getOriginalName();
      }
   }

   abstract static class Member {
      private final ClassInfo.Member.Type type;
      private final String memberName;
      private final String memberDesc;
      private final boolean isInjected;
      private final int modifiers;
      private String currentName;
      private String currentDesc;
      private boolean decoratedFinal;
      private boolean decoratedMutable;
      private boolean unique;

      protected Member(ClassInfo.Member var1) {
         this(var1.type, var1.memberName, var1.memberDesc, var1.modifiers, var1.isInjected);
         this.currentName = var1.currentName;
         this.currentDesc = var1.currentDesc;
         this.unique = var1.unique;
      }

      protected Member(ClassInfo.Member.Type var1, String var2, String var3, int var4) {
         this(var1, var2, var3, var4, false);
      }

      protected Member(ClassInfo.Member.Type var1, String var2, String var3, int var4, boolean var5) {
         this.type = var1;
         this.memberName = var2;
         this.memberDesc = var3;
         this.isInjected = var5;
         this.currentName = var2;
         this.currentDesc = var3;
         this.modifiers = var4;
      }

      public String getOriginalName() {
         return this.memberName;
      }

      public String getName() {
         return this.currentName;
      }

      public String getOriginalDesc() {
         return this.memberDesc;
      }

      public String getDesc() {
         return this.currentDesc;
      }

      public boolean isInjected() {
         return this.isInjected;
      }

      public boolean isRenamed() {
         return !this.currentName.equals(this.memberName);
      }

      public boolean isRemapped() {
         return !this.currentDesc.equals(this.memberDesc);
      }

      public boolean isPrivate() {
         return (this.modifiers & 2) != 0;
      }

      public boolean isStatic() {
         return (this.modifiers & 8) != 0;
      }

      public boolean isAbstract() {
         return (this.modifiers & 1024) != 0;
      }

      public boolean isFinal() {
         return (this.modifiers & 16) != 0;
      }

      public boolean isSynthetic() {
         return (this.modifiers & 4096) != 0;
      }

      public boolean isUnique() {
         return this.unique;
      }

      public void setUnique(boolean var1) {
         this.unique = var1;
      }

      public boolean isDecoratedFinal() {
         return this.decoratedFinal;
      }

      public boolean isDecoratedMutable() {
         return this.decoratedMutable;
      }

      public void setDecoratedFinal(boolean var1, boolean var2) {
         this.decoratedFinal = var1;
         this.decoratedMutable = var2;
      }

      public boolean matchesFlags(int var1) {
         return ((~this.modifiers | var1 & 2) & 2) != 0 && ((~this.modifiers | var1 & 8) & 8) != 0;
      }

      public abstract ClassInfo getOwner();

      public ClassInfo getImplementor() {
         return this.getOwner();
      }

      public int getAccess() {
         return this.modifiers;
      }

      public String renameTo(String var1) {
         this.currentName = var1;
         return var1;
      }

      public String remapTo(String var1) {
         this.currentDesc = var1;
         return var1;
      }

      public boolean equals(String var1, String var2) {
         return (this.memberName.equals(var1) || this.currentName.equals(var1)) && (this.memberDesc.equals(var2) || this.currentDesc.equals(var2));
      }

      public boolean equals(Object var1) {
         if (!(var1 instanceof ClassInfo.Member)) {
            return false;
         } else {
            ClassInfo.Member var2 = (ClassInfo.Member)var1;
            return (var2.memberName.equals(this.memberName) || var2.currentName.equals(this.currentName)) && (var2.memberDesc.equals(this.memberDesc) || var2.currentDesc.equals(this.currentDesc));
         }
      }

      public int hashCode() {
         return this.toString().hashCode();
      }

      public String toString() {
         return String.format(this.getDisplayFormat(), this.memberName, this.memberDesc);
      }

      protected String getDisplayFormat() {
         return "%s%s";
      }

      static enum Type {
         METHOD,
         FIELD;

         private static final ClassInfo.Member.Type[] $VALUES = new ClassInfo.Member.Type[]{METHOD, FIELD};
      }
   }

   public static class FrameData {
      private static final String[] FRAMETYPES = new String[]{"NEW", "FULL", "APPEND", "CHOP", "SAME", "SAME1"};
      public final int index;
      public final int type;
      public final int locals;

      FrameData(int var1, int var2, int var3) {
         this.index = var1;
         this.type = var2;
         this.locals = var3;
      }

      FrameData(int var1, FrameNode var2) {
         this.index = var1;
         this.type = var2.type;
         this.locals = var2.local != null ? var2.local.size() : 0;
      }

      public String toString() {
         return String.format("FrameData[index=%d, type=%s, locals=%d]", this.index, FRAMETYPES[this.type + 1], this.locals);
      }
   }

   public static enum Traversal {
      NONE((ClassInfo.Traversal)null, false, ClassInfo.SearchType.SUPER_CLASSES_ONLY),
      ALL((ClassInfo.Traversal)null, true, ClassInfo.SearchType.ALL_CLASSES),
      IMMEDIATE(NONE, true, ClassInfo.SearchType.SUPER_CLASSES_ONLY),
      SUPER(ALL, false, ClassInfo.SearchType.SUPER_CLASSES_ONLY);

      private final ClassInfo.Traversal next;
      private final boolean traverse;
      private final ClassInfo.SearchType searchType;
      private static final ClassInfo.Traversal[] $VALUES = new ClassInfo.Traversal[]{NONE, ALL, IMMEDIATE, SUPER};

      private Traversal(ClassInfo.Traversal var3, boolean var4, ClassInfo.SearchType var5) {
         this.next = var3 != null ? var3 : this;
         this.traverse = var4;
         this.searchType = var5;
      }

      public ClassInfo.Traversal next() {
         return this.next;
      }

      public boolean canTraverse() {
         return this.traverse;
      }

      public ClassInfo.SearchType getSearchType() {
         return this.searchType;
      }
   }

   public static enum SearchType {
      ALL_CLASSES,
      SUPER_CLASSES_ONLY;

      private static final ClassInfo.SearchType[] $VALUES = new ClassInfo.SearchType[]{ALL_CLASSES, SUPER_CLASSES_ONLY};
   }
}
