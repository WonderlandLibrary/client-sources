package org.spongepowered.asm.mixin.transformer;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.lib.ClassReader;
import org.spongepowered.asm.lib.MethodVisitor;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.lib.tree.FieldNode;
import org.spongepowered.asm.lib.tree.InnerClassNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.extensibility.IMixinConfig;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.mixin.injection.Surrogate;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;
import org.spongepowered.asm.mixin.transformer.throwables.MixinReloadException;
import org.spongepowered.asm.mixin.transformer.throwables.MixinTargetAlreadyLoadedException;
import org.spongepowered.asm.service.IMixinService;
import org.spongepowered.asm.service.MixinService;
import org.spongepowered.asm.util.Annotations;
import org.spongepowered.asm.util.Bytecode;
import org.spongepowered.asm.util.perf.Profiler;

class MixinInfo implements Comparable, IMixinInfo {
   private static final IMixinService classLoaderUtil = MixinService.getService();
   static int mixinOrder = 0;
   private final transient Logger logger = LogManager.getLogger("mixin");
   private final transient Profiler profiler = MixinEnvironment.getProfiler();
   private final transient MixinConfig parent;
   private final String name;
   private final String className;
   private final int priority;
   private final boolean virtual;
   private final List targetClasses;
   private final List targetClassNames;
   private final transient int order;
   private final transient IMixinService service;
   private final transient IMixinConfigPlugin plugin;
   private final transient MixinEnvironment.Phase phase;
   private final transient ClassInfo info;
   private final transient MixinInfo.SubType type;
   private final transient boolean strict;
   private transient MixinInfo.State pendingState;
   private transient MixinInfo.State state;

   MixinInfo(IMixinService var1, MixinConfig var2, String var3, boolean var4, IMixinConfigPlugin var5, boolean var6) {
      this.order = mixinOrder++;
      this.service = var1;
      this.parent = var2;
      this.name = var3;
      this.className = var2.getMixinPackage() + var3;
      this.plugin = var5;
      this.phase = var2.getEnvironment().getPhase();
      this.strict = var2.getEnvironment().getOption(MixinEnvironment.Option.DEBUG_TARGETS);

      try {
         byte[] var7 = this.loadMixinClass(this.className, var4);
         this.pendingState = new MixinInfo.State(this, var7);
         this.info = this.pendingState.getClassInfo();
         this.type = MixinInfo.SubType.getTypeFor(this);
      } catch (InvalidMixinException var10) {
         throw var10;
      } catch (Exception var11) {
         throw new InvalidMixinException(this, var11);
      }

      if (!this.type.isLoadable()) {
         classLoaderUtil.registerInvalidClass(this.className);
      }

      try {
         this.priority = this.readPriority(this.pendingState.getClassNode());
         this.virtual = this.readPseudo(this.pendingState.getClassNode());
         this.targetClasses = this.readTargetClasses(this.pendingState.getClassNode(), var6);
         this.targetClassNames = Collections.unmodifiableList(Lists.transform(this.targetClasses, Functions.toStringFunction()));
      } catch (InvalidMixinException var8) {
         throw var8;
      } catch (Exception var9) {
         throw new InvalidMixinException(this, var9);
      }
   }

   void validate() {
      if (this.pendingState == null) {
         throw new IllegalStateException("No pending validation state for " + this);
      } else {
         try {
            this.pendingState.validate(this.type, this.targetClasses);
            this.state = this.pendingState;
         } finally {
            this.pendingState = null;
         }

      }
   }

   protected List readTargetClasses(MixinInfo.MixinClassNode var1, boolean var2) {
      if (var1 == null) {
         return Collections.emptyList();
      } else {
         AnnotationNode var3 = Annotations.getInvisible((ClassNode)var1, Mixin.class);
         if (var3 == null) {
            throw new InvalidMixinException(this, String.format("The mixin '%s' is missing an @Mixin annotation", this.className));
         } else {
            ArrayList var4 = new ArrayList();
            List var5 = (List)Annotations.getValue(var3, "value");
            List var6 = (List)Annotations.getValue(var3, "targets");
            if (var5 != null) {
               this.readTargets(var4, Lists.transform(var5, new Function(this) {
                  final MixinInfo this$0;

                  {
                     this.this$0 = var1;
                  }

                  public String apply(Type var1) {
                     return var1.getClassName();
                  }

                  public Object apply(Object var1) {
                     return this.apply((Type)var1);
                  }
               }), var2, false);
            }

            if (var6 != null) {
               this.readTargets(var4, Lists.transform(var6, new Function(this) {
                  final MixinInfo this$0;

                  {
                     this.this$0 = var1;
                  }

                  public String apply(String var1) {
                     return this.this$0.getParent().remapClassName(this.this$0.getClassRef(), var1);
                  }

                  public Object apply(Object var1) {
                     return this.apply((String)var1);
                  }
               }), var2, true);
            }

            return var4;
         }
      }
   }

   private void readTargets(Collection var1, Collection var2, boolean var3, boolean var4) {
      Iterator var5 = var2.iterator();

      while(var5.hasNext()) {
         String var6 = (String)var5.next();
         String var7 = var6.replace('/', '.');
         if (classLoaderUtil.isClassLoaded(var7) && !this.isReloading()) {
            String var8 = String.format("Critical problem: %s target %s was already transformed.", this, var7);
            if (this.parent.isRequired()) {
               throw new MixinTargetAlreadyLoadedException(this, var8, var7);
            }

            this.logger.error(var8);
         }

         if (this.shouldApplyMixin(var3, var7)) {
            ClassInfo var9 = this.getTarget(var7, var4);
            if (var9 != null && !var1.contains(var9)) {
               var1.add(var9);
               var9.addMixin(this);
            }
         }
      }

   }

   private boolean shouldApplyMixin(boolean var1, String var2) {
      Profiler.Section var3 = this.profiler.begin("plugin");
      boolean var4 = this.plugin == null || var1 || this.plugin.shouldApplyMixin(var2, this.className);
      var3.end();
      return var4;
   }

   private ClassInfo getTarget(String var1, boolean var2) throws InvalidMixinException {
      ClassInfo var3 = ClassInfo.forName(var1);
      if (var3 == null) {
         if (this.isVirtual()) {
            this.logger.debug("Skipping virtual target {} for {}", new Object[]{var1, this});
         } else {
            this.handleTargetError(String.format("@Mixin target %s was not found %s", var1, this));
         }

         return null;
      } else {
         this.type.validateTarget(var1, var3);
         if (var2 && var3.isPublic() && !this.isVirtual()) {
            this.handleTargetError(String.format("@Mixin target %s is public in %s and should be specified in value", var1, this));
         }

         return var3;
      }
   }

   private void handleTargetError(String var1) {
      if (this.strict) {
         this.logger.error(var1);
         throw new InvalidMixinException(this, var1);
      } else {
         this.logger.warn(var1);
      }
   }

   protected int readPriority(ClassNode var1) {
      if (var1 == null) {
         return this.parent.getDefaultMixinPriority();
      } else {
         AnnotationNode var2 = Annotations.getInvisible(var1, Mixin.class);
         if (var2 == null) {
            throw new InvalidMixinException(this, String.format("The mixin '%s' is missing an @Mixin annotation", this.className));
         } else {
            Integer var3 = (Integer)Annotations.getValue(var2, "priority");
            return var3 == null ? this.parent.getDefaultMixinPriority() : var3;
         }
      }
   }

   protected boolean readPseudo(ClassNode var1) {
      return Annotations.getInvisible(var1, Pseudo.class) != null;
   }

   private boolean isReloading() {
      return this.pendingState instanceof MixinInfo.Reloaded;
   }

   private MixinInfo.State getState() {
      return this.state != null ? this.state : this.pendingState;
   }

   ClassInfo getClassInfo() {
      return this.info;
   }

   public IMixinConfig getConfig() {
      return this.parent;
   }

   MixinConfig getParent() {
      return this.parent;
   }

   public int getPriority() {
      return this.priority;
   }

   public String getName() {
      return this.name;
   }

   public String getClassName() {
      return this.className;
   }

   public String getClassRef() {
      return this.getClassInfo().getName();
   }

   public byte[] getClassBytes() {
      return this.getState().getClassBytes();
   }

   public boolean isDetachedSuper() {
      return this.getState().isDetachedSuper();
   }

   public boolean isUnique() {
      return this.getState().isUnique();
   }

   public boolean isVirtual() {
      return this.virtual;
   }

   public boolean isAccessor() {
      return this.type instanceof MixinInfo.SubType.Accessor;
   }

   public boolean isLoadable() {
      return this.type.isLoadable();
   }

   public Level getLoggingLevel() {
      return this.parent.getLoggingLevel();
   }

   public MixinEnvironment.Phase getPhase() {
      return this.phase;
   }

   public MixinInfo.MixinClassNode getClassNode(int var1) {
      return this.getState().createClassNode(var1);
   }

   public List getTargetClasses() {
      return this.targetClassNames;
   }

   List getSoftImplements() {
      return Collections.unmodifiableList(this.getState().getSoftImplements());
   }

   Set getSyntheticInnerClasses() {
      return Collections.unmodifiableSet(this.getState().getSyntheticInnerClasses());
   }

   Set getInnerClasses() {
      return Collections.unmodifiableSet(this.getState().getInnerClasses());
   }

   List getTargets() {
      return Collections.unmodifiableList(this.targetClasses);
   }

   Set getInterfaces() {
      return this.getState().getInterfaces();
   }

   MixinTargetContext createContextFor(TargetClassContext var1) {
      MixinInfo.MixinClassNode var2 = this.getClassNode(8);
      Profiler.Section var3 = this.profiler.begin("pre");
      MixinTargetContext var4 = this.type.createPreProcessor(var2).prepare().createContextFor(var1);
      var3.end();
      return var4;
   }

   private byte[] loadMixinClass(String var1, boolean var2) throws ClassNotFoundException {
      Object var3 = null;

      try {
         byte[] var7 = this.service.getBytecodeProvider().getClassBytes(var1, var2);
         return var7;
      } catch (ClassNotFoundException var5) {
         throw new ClassNotFoundException(String.format("The specified mixin '%s' was not found", var1));
      } catch (IOException var6) {
         this.logger.warn("Failed to load mixin %s, the specified mixin will not be applied", new Object[]{var1});
         throw new InvalidMixinException(this, "An error was encountered whilst loading the mixin class", var6);
      }
   }

   void reloadMixin(byte[] var1) {
      if (this.pendingState != null) {
         throw new IllegalStateException("Cannot reload mixin while it is initialising");
      } else {
         this.pendingState = new MixinInfo.Reloaded(this, this.state, var1);
         this.validate();
      }
   }

   public int compareTo(MixinInfo var1) {
      if (var1 == null) {
         return 0;
      } else {
         return var1.priority == this.priority ? this.order - var1.order : this.priority - var1.priority;
      }
   }

   public void preApply(String var1, ClassNode var2) {
      if (this.plugin != null) {
         Profiler.Section var3 = this.profiler.begin("plugin");
         this.plugin.preApply(var1, var2, this.className, this);
         var3.end();
      }

   }

   public void postApply(String var1, ClassNode var2) {
      if (this.plugin != null) {
         Profiler.Section var3 = this.profiler.begin("plugin");
         this.plugin.postApply(var1, var2, this.className, this);
         var3.end();
      }

      this.parent.postApply(var1, var2);
   }

   public String toString() {
      return String.format("%s:%s", this.parent.getName(), this.name);
   }

   public int compareTo(Object var1) {
      return this.compareTo((MixinInfo)var1);
   }

   public ClassNode getClassNode(int var1) {
      return this.getClassNode(var1);
   }

   abstract static class SubType {
      protected final MixinInfo mixin;
      protected final String annotationType;
      protected final boolean targetMustBeInterface;
      protected boolean detached;

      SubType(MixinInfo var1, String var2, boolean var3) {
         this.mixin = var1;
         this.annotationType = var2;
         this.targetMustBeInterface = var3;
      }

      Collection getInterfaces() {
         return Collections.emptyList();
      }

      boolean isDetachedSuper() {
         return this.detached;
      }

      boolean isLoadable() {
         return false;
      }

      void validateTarget(String var1, ClassInfo var2) {
         boolean var3 = var2.isInterface();
         if (var3 != this.targetMustBeInterface) {
            String var4 = var3 ? "" : "not ";
            throw new InvalidMixinException(this.mixin, this.annotationType + " target type mismatch: " + var1 + " is " + var4 + "an interface in " + this);
         }
      }

      abstract void validate(MixinInfo.State var1, List var2);

      abstract MixinPreProcessorStandard createPreProcessor(MixinInfo.MixinClassNode var1);

      static MixinInfo.SubType getTypeFor(MixinInfo var0) {
         if (!var0.getClassInfo().isInterface()) {
            return new MixinInfo.SubType.Standard(var0);
         } else {
            boolean var1 = false;

            ClassInfo.Method var3;
            for(Iterator var2 = var0.getClassInfo().getMethods().iterator(); var2.hasNext(); var1 |= !var3.isAccessor()) {
               var3 = (ClassInfo.Method)var2.next();
            }

            if (var1) {
               return new MixinInfo.SubType.Interface(var0);
            } else {
               return new MixinInfo.SubType.Accessor(var0);
            }
         }
      }

      static class Accessor extends MixinInfo.SubType {
         private final Collection interfaces = new ArrayList();

         Accessor(MixinInfo var1) {
            super(var1, "@Mixin", false);
            this.interfaces.add(var1.getClassRef());
         }

         boolean isLoadable() {
            return true;
         }

         Collection getInterfaces() {
            return this.interfaces;
         }

         void validateTarget(String var1, ClassInfo var2) {
            boolean var3 = var2.isInterface();
            if (var3 && !MixinEnvironment.getCompatibilityLevel().supportsMethodsInInterfaces()) {
               throw new InvalidMixinException(this.mixin, "Accessor mixin targetting an interface is not supported in current enviromnment");
            }
         }

         void validate(MixinInfo.State var1, List var2) {
            MixinInfo.MixinClassNode var3 = var1.getClassNode();
            if (!"java/lang/Object".equals(var3.superName)) {
               throw new InvalidMixinException(this.mixin, "Super class of " + this + " is invalid, found " + var3.superName.replace('/', '.'));
            }
         }

         MixinPreProcessorStandard createPreProcessor(MixinInfo.MixinClassNode var1) {
            return new MixinPreProcessorAccessor(this.mixin, var1);
         }
      }

      static class Interface extends MixinInfo.SubType {
         Interface(MixinInfo var1) {
            super(var1, "@Mixin", true);
         }

         void validate(MixinInfo.State var1, List var2) {
            if (!MixinEnvironment.getCompatibilityLevel().supportsMethodsInInterfaces()) {
               throw new InvalidMixinException(this.mixin, "Interface mixin not supported in current enviromnment");
            } else {
               MixinInfo.MixinClassNode var3 = var1.getClassNode();
               if (!"java/lang/Object".equals(var3.superName)) {
                  throw new InvalidMixinException(this.mixin, "Super class of " + this + " is invalid, found " + var3.superName.replace('/', '.'));
               }
            }
         }

         MixinPreProcessorStandard createPreProcessor(MixinInfo.MixinClassNode var1) {
            return new MixinPreProcessorInterface(this.mixin, var1);
         }
      }

      static class Standard extends MixinInfo.SubType {
         Standard(MixinInfo var1) {
            super(var1, "@Mixin", false);
         }

         void validate(MixinInfo.State var1, List var2) {
            MixinInfo.MixinClassNode var3 = var1.getClassNode();
            Iterator var4 = var2.iterator();

            while(var4.hasNext()) {
               ClassInfo var5 = (ClassInfo)var4.next();
               if (!var3.superName.equals(var5.getSuperName())) {
                  if (!var5.hasSuperClass(var3.superName, ClassInfo.Traversal.SUPER)) {
                     ClassInfo var6 = ClassInfo.forName(var3.superName);
                     if (var6.isMixin()) {
                        Iterator var7 = var6.getTargets().iterator();

                        while(var7.hasNext()) {
                           ClassInfo var8 = (ClassInfo)var7.next();
                           if (var2.contains(var8)) {
                              throw new InvalidMixinException(this.mixin, "Illegal hierarchy detected. Derived mixin " + this + " targets the same class " + var8.getClassName() + " as its superclass " + var6.getClassName());
                           }
                        }
                     }

                     throw new InvalidMixinException(this.mixin, "Super class '" + var3.superName.replace('/', '.') + "' of " + this.mixin.getName() + " was not found in the hierarchy of target class '" + var5 + "'");
                  }

                  this.detached = true;
               }
            }

         }

         MixinPreProcessorStandard createPreProcessor(MixinInfo.MixinClassNode var1) {
            return new MixinPreProcessorStandard(this.mixin, var1);
         }
      }
   }

   class Reloaded extends MixinInfo.State {
      private final MixinInfo.State previous;
      final MixinInfo this$0;

      Reloaded(MixinInfo var1, MixinInfo.State var2, byte[] var3) {
         super(var1, var3, var2.getClassInfo());
         this.this$0 = var1;
         this.previous = var2;
      }

      protected void validateChanges(MixinInfo.SubType var1, List var2) {
         if (!this.syntheticInnerClasses.equals(this.previous.syntheticInnerClasses)) {
            throw new MixinReloadException(this.this$0, "Cannot change inner classes");
         } else if (!this.interfaces.equals(this.previous.interfaces)) {
            throw new MixinReloadException(this.this$0, "Cannot change interfaces");
         } else if (!(new HashSet(this.softImplements)).equals(new HashSet(this.previous.softImplements))) {
            throw new MixinReloadException(this.this$0, "Cannot change soft interfaces");
         } else {
            List var3 = this.this$0.readTargetClasses(this.classNode, true);
            if (!(new HashSet(var3)).equals(new HashSet(var2))) {
               throw new MixinReloadException(this.this$0, "Cannot change target classes");
            } else {
               int var4 = this.this$0.readPriority(this.classNode);
               if (var4 != this.this$0.getPriority()) {
                  throw new MixinReloadException(this.this$0, "Cannot change mixin priority");
               }
            }
         }
      }
   }

   class State {
      private byte[] mixinBytes;
      private final ClassInfo classInfo;
      private boolean detachedSuper;
      private boolean unique;
      protected final Set interfaces;
      protected final List softImplements;
      protected final Set syntheticInnerClasses;
      protected final Set innerClasses;
      protected MixinInfo.MixinClassNode classNode;
      final MixinInfo this$0;

      State(MixinInfo var1, byte[] var2) {
         this(var1, var2, (ClassInfo)null);
      }

      State(MixinInfo var1, byte[] var2, ClassInfo var3) {
         this.this$0 = var1;
         this.interfaces = new HashSet();
         this.softImplements = new ArrayList();
         this.syntheticInnerClasses = new HashSet();
         this.innerClasses = new HashSet();
         this.mixinBytes = var2;
         this.connect();
         this.classInfo = var3 != null ? var3 : ClassInfo.fromClassNode(this.getClassNode());
      }

      private void connect() {
         this.classNode = this.createClassNode(0);
      }

      private void complete() {
         this.classNode = null;
      }

      ClassInfo getClassInfo() {
         return this.classInfo;
      }

      byte[] getClassBytes() {
         return this.mixinBytes;
      }

      MixinInfo.MixinClassNode getClassNode() {
         return this.classNode;
      }

      boolean isDetachedSuper() {
         return this.detachedSuper;
      }

      boolean isUnique() {
         return this.unique;
      }

      List getSoftImplements() {
         return this.softImplements;
      }

      Set getSyntheticInnerClasses() {
         return this.syntheticInnerClasses;
      }

      Set getInnerClasses() {
         return this.innerClasses;
      }

      Set getInterfaces() {
         return this.interfaces;
      }

      MixinInfo.MixinClassNode createClassNode(int var1) {
         MixinInfo.MixinClassNode var2 = this.this$0.new MixinClassNode(this.this$0, this.this$0);
         ClassReader var3 = new ClassReader(this.mixinBytes);
         var3.accept(var2, var1);
         return var2;
      }

      void validate(MixinInfo.SubType var1, List var2) {
         MixinPreProcessorStandard var3 = var1.createPreProcessor(this.getClassNode()).prepare();
         Iterator var4 = var2.iterator();

         while(var4.hasNext()) {
            ClassInfo var5 = (ClassInfo)var4.next();
            var3.conform(var5);
         }

         var1.validate(this, var2);
         this.detachedSuper = var1.isDetachedSuper();
         this.unique = Annotations.getVisible((ClassNode)this.getClassNode(), Unique.class) != null;
         this.validateInner();
         this.validateClassVersion();
         this.validateRemappables(var2);
         this.readImplementations(var1);
         this.readInnerClasses();
         this.validateChanges(var1, var2);
         this.complete();
      }

      private void validateInner() {
         if (!this.classInfo.isProbablyStatic()) {
            throw new InvalidMixinException(this.this$0, "Inner class mixin must be declared static");
         }
      }

      private void validateClassVersion() {
         if (this.classNode.version > MixinEnvironment.getCompatibilityLevel().classVersion()) {
            String var1 = ".";
            MixinEnvironment.CompatibilityLevel[] var2 = MixinEnvironment.CompatibilityLevel.values();
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
               MixinEnvironment.CompatibilityLevel var5 = var2[var4];
               if (var5.classVersion() >= this.classNode.version) {
                  var1 = String.format(". Mixin requires compatibility level %s or above.", var5.name());
               }
            }

            throw new InvalidMixinException(this.this$0, "Unsupported mixin class version " + this.classNode.version + var1);
         }
      }

      private void validateRemappables(List var1) {
         if (var1.size() > 1) {
            Iterator var2 = this.classNode.fields.iterator();

            while(var2.hasNext()) {
               FieldNode var3 = (FieldNode)var2.next();
               this.validateRemappable(Shadow.class, var3.name, Annotations.getVisible(var3, Shadow.class));
            }

            var2 = this.classNode.methods.iterator();

            AnnotationNode var4;
            MethodNode var5;
            do {
               if (!var2.hasNext()) {
                  return;
               }

               var5 = (MethodNode)var2.next();
               this.validateRemappable(Shadow.class, var5.name, Annotations.getVisible(var5, Shadow.class));
               var4 = Annotations.getVisible(var5, Overwrite.class);
            } while(var4 == null || (var5.access & 8) != 0 && (var5.access & 1) != 0);

            throw new InvalidMixinException(this.this$0, "Found @Overwrite annotation on " + var5.name + " in " + this.this$0);
         }
      }

      private void validateRemappable(Class var1, String var2, AnnotationNode var3) {
         if (var3 != null && (Boolean)Annotations.getValue(var3, "remap", (Object)Boolean.TRUE)) {
            throw new InvalidMixinException(this.this$0, "Found a remappable @" + var1.getSimpleName() + " annotation on " + var2 + " in " + this);
         }
      }

      void readImplementations(MixinInfo.SubType var1) {
         this.interfaces.addAll(this.classNode.interfaces);
         this.interfaces.addAll(var1.getInterfaces());
         AnnotationNode var2 = Annotations.getInvisible((ClassNode)this.classNode, Implements.class);
         if (var2 != null) {
            List var3 = (List)Annotations.getValue(var2);
            if (var3 != null) {
               Iterator var4 = var3.iterator();

               while(var4.hasNext()) {
                  AnnotationNode var5 = (AnnotationNode)var4.next();
                  InterfaceInfo var6 = InterfaceInfo.fromAnnotation(this.this$0, var5);
                  this.softImplements.add(var6);
                  this.interfaces.add(var6.getInternalName());
                  if (!(this instanceof MixinInfo.Reloaded)) {
                     this.classInfo.addInterface(var6.getInternalName());
                  }
               }

            }
         }
      }

      void readInnerClasses() {
         Iterator var1 = this.classNode.innerClasses.iterator();

         while(true) {
            while(true) {
               InnerClassNode var2;
               ClassInfo var3;
               do {
                  if (!var1.hasNext()) {
                     return;
                  }

                  var2 = (InnerClassNode)var1.next();
                  var3 = ClassInfo.forName(var2.name);
               } while((var2.outerName == null || !var2.outerName.equals(this.classInfo.getName())) && !var2.name.startsWith(this.classNode.name + "$"));

               if (var3.isProbablyStatic() && var3.isSynthetic()) {
                  this.syntheticInnerClasses.add(var2.name);
               } else {
                  this.innerClasses.add(var2.name);
               }
            }
         }
      }

      protected void validateChanges(MixinInfo.SubType var1, List var2) {
         var1.createPreProcessor(this.classNode).prepare();
      }
   }

   class MixinClassNode extends ClassNode {
      public final List mixinMethods;
      final MixinInfo this$0;

      public MixinClassNode(MixinInfo var1, MixinInfo var2) {
         this(var1, 327680);
      }

      public MixinClassNode(MixinInfo var1, int var2) {
         super(var2);
         this.this$0 = var1;
         this.mixinMethods = (List)this.methods;
      }

      public MixinInfo getMixin() {
         return this.this$0;
      }

      public MethodVisitor visitMethod(int var1, String var2, String var3, String var4, String[] var5) {
         MixinInfo.MixinMethodNode var6 = this.this$0.new MixinMethodNode(this.this$0, var1, var2, var3, var4, var5);
         this.methods.add(var6);
         return var6;
      }
   }

   class MixinMethodNode extends MethodNode {
      private final String originalName;
      final MixinInfo this$0;

      public MixinMethodNode(MixinInfo var1, int var2, String var3, String var4, String var5, String[] var6) {
         super(327680, var2, var3, var4, var5, var6);
         this.this$0 = var1;
         this.originalName = var3;
      }

      public String toString() {
         return String.format("%s%s", this.originalName, this.desc);
      }

      public String getOriginalName() {
         return this.originalName;
      }

      public boolean isInjector() {
         return this.getInjectorAnnotation() != null || this.isSurrogate();
      }

      public boolean isSurrogate() {
         return this.getVisibleAnnotation(Surrogate.class) != null;
      }

      public boolean isSynthetic() {
         return Bytecode.hasFlag((MethodNode)this, 4096);
      }

      public AnnotationNode getVisibleAnnotation(Class var1) {
         return Annotations.getVisible((MethodNode)this, var1);
      }

      public AnnotationNode getInjectorAnnotation() {
         return InjectionInfo.getInjectorAnnotation(this.this$0, this);
      }

      public IMixinInfo getOwner() {
         return this.this$0;
      }
   }
}
