package org.spongepowered.asm.mixin.transformer;

import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.lib.tree.FieldNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.injection.struct.Target;
import org.spongepowered.asm.mixin.struct.SourceMap;
import org.spongepowered.asm.mixin.transformer.ext.Extensions;
import org.spongepowered.asm.mixin.transformer.ext.ITargetClassContext;
import org.spongepowered.asm.util.Annotations;
import org.spongepowered.asm.util.Bytecode;
import org.spongepowered.asm.util.ClassSignature;

class TargetClassContext extends ClassContext implements ITargetClassContext {
   private static final Logger logger = LogManager.getLogger("mixin");
   private final MixinEnvironment env;
   private final Extensions extensions;
   private final String sessionId;
   private final String className;
   private final ClassNode classNode;
   private final ClassInfo classInfo;
   private final SourceMap sourceMap;
   private final ClassSignature signature;
   private final SortedSet mixins;
   private final Map targetMethods = new HashMap();
   private final Set mixinMethods = new HashSet();
   private int nextUniqueMethodIndex;
   private int nextUniqueFieldIndex;
   private boolean applied;
   private boolean forceExport;

   TargetClassContext(MixinEnvironment var1, Extensions var2, String var3, String var4, ClassNode var5, SortedSet var6) {
      this.env = var1;
      this.extensions = var2;
      this.sessionId = var3;
      this.className = var4;
      this.classNode = var5;
      this.classInfo = ClassInfo.fromClassNode(var5);
      this.signature = this.classInfo.getSignature();
      this.mixins = var6;
      this.sourceMap = new SourceMap(var5.sourceFile);
      this.sourceMap.addFile(this.classNode);
   }

   public String toString() {
      return this.className;
   }

   boolean isApplied() {
      return this.applied;
   }

   boolean isExportForced() {
      return this.forceExport;
   }

   Extensions getExtensions() {
      return this.extensions;
   }

   String getSessionId() {
      return this.sessionId;
   }

   String getClassRef() {
      return this.classNode.name;
   }

   String getClassName() {
      return this.className;
   }

   public ClassNode getClassNode() {
      return this.classNode;
   }

   List getMethods() {
      return this.classNode.methods;
   }

   List getFields() {
      return this.classNode.fields;
   }

   public ClassInfo getClassInfo() {
      return this.classInfo;
   }

   SortedSet getMixins() {
      return this.mixins;
   }

   SourceMap getSourceMap() {
      return this.sourceMap;
   }

   void mergeSignature(ClassSignature var1) {
      this.signature.merge(var1);
   }

   void addMixinMethod(MethodNode var1) {
      this.mixinMethods.add(var1);
   }

   void methodMerged(MethodNode var1) {
      if (!this.mixinMethods.remove(var1)) {
         logger.debug("Unexpected: Merged unregistered method {}{} in {}", new Object[]{var1.name, var1.desc, this});
      }

   }

   MethodNode findMethod(Deque var1, String var2) {
      return this.findAliasedMethod(var1, var2, true);
   }

   MethodNode findAliasedMethod(Deque var1, String var2) {
      return this.findAliasedMethod(var1, var2, false);
   }

   private MethodNode findAliasedMethod(Deque var1, String var2, boolean var3) {
      String var4 = (String)var1.poll();
      if (var4 == null) {
         return null;
      } else {
         Iterator var5 = this.classNode.methods.iterator();

         MethodNode var6;
         while(var5.hasNext()) {
            var6 = (MethodNode)var5.next();
            if (var6.name.equals(var4) && var6.desc.equals(var2)) {
               return var6;
            }
         }

         if (var3) {
            var5 = this.mixinMethods.iterator();

            while(var5.hasNext()) {
               var6 = (MethodNode)var5.next();
               if (var6.name.equals(var4) && var6.desc.equals(var2)) {
                  return var6;
               }
            }
         }

         return this.findAliasedMethod(var1, var2);
      }
   }

   FieldNode findAliasedField(Deque var1, String var2) {
      String var3 = (String)var1.poll();
      if (var3 == null) {
         return null;
      } else {
         Iterator var4 = this.classNode.fields.iterator();

         FieldNode var5;
         do {
            if (!var4.hasNext()) {
               return this.findAliasedField(var1, var2);
            }

            var5 = (FieldNode)var4.next();
         } while(!var5.name.equals(var3) || !var5.desc.equals(var2));

         return var5;
      }
   }

   Target getTargetMethod(MethodNode var1) {
      if (!this.classNode.methods.contains(var1)) {
         throw new IllegalArgumentException("Invalid target method supplied to getTargetMethod()");
      } else {
         String var2 = var1.name + var1.desc;
         Target var3 = (Target)this.targetMethods.get(var2);
         if (var3 == null) {
            var3 = new Target(this.classNode, var1);
            this.targetMethods.put(var2, var3);
         }

         return var3;
      }
   }

   String getUniqueName(MethodNode var1, boolean var2) {
      String var3 = Integer.toHexString(this.nextUniqueMethodIndex++);
      String var4 = var2 ? "%2$s_$md$%1$s$%3$s" : "md%s$%s$%s";
      return String.format(var4, this.sessionId.substring(30), var1.name, var3);
   }

   String getUniqueName(FieldNode var1) {
      String var2 = Integer.toHexString(this.nextUniqueFieldIndex++);
      return String.format("fd%s$%s$%s", this.sessionId.substring(30), var1.name, var2);
   }

   void applyMixins() {
      if (this.applied) {
         throw new IllegalStateException("Mixins already applied to target class " + this.className);
      } else {
         this.applied = true;
         MixinApplicatorStandard var1 = this.createApplicator();
         var1.apply(this.mixins);
         this.applySignature();
         this.upgradeMethods();
         this.checkMerges();
      }
   }

   private MixinApplicatorStandard createApplicator() {
      return (MixinApplicatorStandard)(this.classInfo.isInterface() ? new MixinApplicatorInterface(this) : new MixinApplicatorStandard(this));
   }

   private void applySignature() {
      this.getClassNode().signature = this.signature.toString();
   }

   private void checkMerges() {
      Iterator var1 = this.mixinMethods.iterator();

      while(var1.hasNext()) {
         MethodNode var2 = (MethodNode)var1.next();
         if (!var2.name.startsWith("<")) {
            logger.debug("Unexpected: Registered method {}{} in {} was not merged", new Object[]{var2.name, var2.desc, this});
         }
      }

   }

   void processDebugTasks() {
      if (this.env.getOption(MixinEnvironment.Option.DEBUG_VERBOSE)) {
         AnnotationNode var1 = Annotations.getVisible(this.classNode, Debug.class);
         if (var1 != null) {
            this.forceExport = Boolean.TRUE.equals(Annotations.getValue(var1, "export"));
            if (Boolean.TRUE.equals(Annotations.getValue(var1, "print"))) {
               Bytecode.textify((ClassNode)this.classNode, System.err);
            }
         }

         Iterator var2 = this.classNode.methods.iterator();

         while(var2.hasNext()) {
            MethodNode var3 = (MethodNode)var2.next();
            AnnotationNode var4 = Annotations.getVisible(var3, Debug.class);
            if (var4 != null && Boolean.TRUE.equals(Annotations.getValue(var4, "print"))) {
               Bytecode.textify((MethodNode)var3, System.err);
            }
         }

      }
   }
}
