package org.spongepowered.asm.mixin.injection.struct;

import com.google.common.base.Strings;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.code.ISliceContext;
import org.spongepowered.asm.mixin.injection.code.Injector;
import org.spongepowered.asm.mixin.injection.code.InjectorTarget;
import org.spongepowered.asm.mixin.injection.code.MethodSlice;
import org.spongepowered.asm.mixin.injection.code.MethodSlices;
import org.spongepowered.asm.mixin.injection.throwables.InjectionError;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
import org.spongepowered.asm.mixin.refmap.IMixinContext;
import org.spongepowered.asm.mixin.struct.SpecialMethodInfo;
import org.spongepowered.asm.mixin.transformer.MixinTargetContext;
import org.spongepowered.asm.mixin.transformer.meta.MixinMerged;
import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;
import org.spongepowered.asm.util.Annotations;
import org.spongepowered.asm.util.Bytecode;

public abstract class InjectionInfo extends SpecialMethodInfo implements ISliceContext {
   protected final boolean isStatic;
   protected final Deque targets;
   protected final MethodSlices slices;
   protected final String atKey;
   protected final List injectionPoints;
   protected final Map targetNodes;
   protected Injector injector;
   protected InjectorGroupInfo group;
   private final List injectedMethods;
   private int expectedCallbackCount;
   private int requiredCallbackCount;
   private int maxCallbackCount;
   private int injectedCallbackCount;

   protected InjectionInfo(MixinTargetContext var1, MethodNode var2, AnnotationNode var3) {
      this(var1, var2, var3, "at");
   }

   protected InjectionInfo(MixinTargetContext var1, MethodNode var2, AnnotationNode var3, String var4) {
      super(var1, var2, var3);
      this.targets = new ArrayDeque();
      this.injectionPoints = new ArrayList();
      this.targetNodes = new LinkedHashMap();
      this.injectedMethods = new ArrayList(0);
      this.expectedCallbackCount = 1;
      this.requiredCallbackCount = 0;
      this.maxCallbackCount = Integer.MAX_VALUE;
      this.injectedCallbackCount = 0;
      this.isStatic = Bytecode.methodIsStatic(var2);
      this.slices = MethodSlices.parse(this);
      this.atKey = var4;
      this.readAnnotation();
   }

   protected void readAnnotation() {
      if (this.annotation != null) {
         String var1 = "@" + Bytecode.getSimpleName(this.annotation);
         List var2 = this.readInjectionPoints(var1);
         this.findMethods(this.parseTargets(var1), var1);
         this.parseInjectionPoints(var2);
         this.parseRequirements();
         this.injector = this.parseInjector(this.annotation);
      }
   }

   protected Set parseTargets(String var1) {
      List var2 = Annotations.getValue(this.annotation, "method", false);
      if (var2 == null) {
         throw new InvalidInjectionException(this, String.format("%s annotation on %s is missing method name", var1, this.method.name));
      } else {
         LinkedHashSet var3 = new LinkedHashSet();
         Iterator var4 = var2.iterator();

         while(var4.hasNext()) {
            String var5 = (String)var4.next();

            try {
               MemberInfo var6 = MemberInfo.parseAndValidate(var5, this.mixin);
               if (var6.owner != null && !var6.owner.equals(this.mixin.getTargetClassRef())) {
                  throw new InvalidInjectionException(this, String.format("%s annotation on %s specifies a target class '%s', which is not supported", var1, this.method.name, var6.owner));
               }

               var3.add(var6);
            } catch (InvalidMemberDescriptorException var7) {
               throw new InvalidInjectionException(this, String.format("%s annotation on %s, has invalid target descriptor: \"%s\". %s", var1, this.method.name, var5, this.mixin.getReferenceMapper().getStatus()));
            }
         }

         return var3;
      }
   }

   protected List readInjectionPoints(String var1) {
      List var2 = Annotations.getValue(this.annotation, this.atKey, false);
      if (var2 == null) {
         throw new InvalidInjectionException(this, String.format("%s annotation on %s is missing '%s' value(s)", var1, this.method.name, this.atKey));
      } else {
         return var2;
      }
   }

   protected void parseInjectionPoints(List var1) {
      this.injectionPoints.addAll(InjectionPoint.parse(this.mixin, this.method, this.annotation, (List)var1));
   }

   protected void parseRequirements() {
      this.group = this.mixin.getInjectorGroups().parseGroup(this.method, this.mixin.getDefaultInjectorGroup()).add(this);
      Integer var1 = (Integer)Annotations.getValue(this.annotation, "expect");
      if (var1 != null) {
         this.expectedCallbackCount = var1;
      }

      Integer var2 = (Integer)Annotations.getValue(this.annotation, "require");
      if (var2 != null && var2 > -1) {
         this.requiredCallbackCount = var2;
      } else if (this.group.isDefault()) {
         this.requiredCallbackCount = this.mixin.getDefaultRequiredInjections();
      }

      Integer var3 = (Integer)Annotations.getValue(this.annotation, "allow");
      if (var3 != null) {
         this.maxCallbackCount = Math.max(Math.max(this.requiredCallbackCount, 1), var3);
      }

   }

   protected abstract Injector parseInjector(AnnotationNode var1);

   public boolean isValid() {
      return this.targets.size() > 0 && this.injectionPoints.size() > 0;
   }

   public void prepare() {
      this.targetNodes.clear();
      Iterator var1 = this.targets.iterator();

      while(var1.hasNext()) {
         MethodNode var2 = (MethodNode)var1.next();
         Target var3 = this.mixin.getTargetMethod(var2);
         InjectorTarget var4 = new InjectorTarget(this, var3);
         this.targetNodes.put(var3, this.injector.find(var4, this.injectionPoints));
         var4.dispose();
      }

   }

   public void inject() {
      Iterator var1 = this.targetNodes.entrySet().iterator();

      while(var1.hasNext()) {
         Entry var2 = (Entry)var1.next();
         this.injector.inject((Target)var2.getKey(), (List)var2.getValue());
      }

      this.targets.clear();
   }

   public void postInject() {
      Iterator var1 = this.injectedMethods.iterator();

      while(var1.hasNext()) {
         MethodNode var2 = (MethodNode)var1.next();
         this.classNode.methods.add(var2);
      }

      String var4 = this.getDescription();
      String var5 = this.mixin.getReferenceMapper().getStatus();
      String var3 = this.getDynamicInfo();
      if (this.mixin.getEnvironment().getOption(MixinEnvironment.Option.DEBUG_INJECTORS) && this.injectedCallbackCount < this.expectedCallbackCount) {
         throw new InvalidInjectionException(this, String.format("Injection validation failed: %s %s%s in %s expected %d invocation(s) but %d succeeded. %s%s", var4, this.method.name, this.method.desc, this.mixin, this.expectedCallbackCount, this.injectedCallbackCount, var5, var3));
      } else if (this.injectedCallbackCount < this.requiredCallbackCount) {
         throw new InjectionError(String.format("Critical injection failure: %s %s%s in %s failed injection check, (%d/%d) succeeded. %s%s", var4, this.method.name, this.method.desc, this.mixin, this.injectedCallbackCount, this.requiredCallbackCount, var5, var3));
      } else if (this.injectedCallbackCount > this.maxCallbackCount) {
         throw new InjectionError(String.format("Critical injection failure: %s %s%s in %s failed injection check, %d succeeded of %d allowed.%s", var4, this.method.name, this.method.desc, this.mixin, this.injectedCallbackCount, this.maxCallbackCount, var3));
      }
   }

   public void notifyInjected(Target var1) {
   }

   protected String getDescription() {
      return "Callback method";
   }

   public String toString() {
      return describeInjector(this.mixin, this.annotation, this.method);
   }

   public Collection getTargets() {
      return this.targets;
   }

   public MethodSlice getSlice(String var1) {
      return this.slices.get(this.getSliceId(var1));
   }

   public String getSliceId(String var1) {
      return "";
   }

   public int getInjectedCallbackCount() {
      return this.injectedCallbackCount;
   }

   public MethodNode addMethod(int var1, String var2, String var3) {
      MethodNode var4 = new MethodNode(327680, var1 | 4096, var2, var3, (String)null, (String[])null);
      this.injectedMethods.add(var4);
      return var4;
   }

   public void addCallbackInvocation(MethodNode var1) {
      ++this.injectedCallbackCount;
   }

   private void findMethods(Set var1, String var2) {
      this.targets.clear();
      int var3 = this.mixin.getEnvironment().getOption(MixinEnvironment.Option.REFMAP_REMAP) ? 2 : 1;
      Iterator var4 = var1.iterator();

      while(var4.hasNext()) {
         MemberInfo var5 = (MemberInfo)var4.next();
         int var6 = 0;

         label82:
         for(int var7 = 0; var7 < var3 && var6 < 1; ++var7) {
            int var8 = 0;
            Iterator var9 = this.classNode.methods.iterator();

            while(true) {
               MethodNode var10;
               boolean var11;
               do {
                  do {
                     if (!var9.hasNext()) {
                        var5 = var5.transform((String)null);
                        continue label82;
                     }

                     var10 = (MethodNode)var9.next();
                  } while(!var5.matches(var10.name, var10.desc, var8));

                  var11 = Annotations.getVisible(var10, MixinMerged.class) != null;
               } while(var5.matchAll && (Bytecode.methodIsStatic(var10) != this.isStatic || var10 == this.method || var11));

               this.checkTarget(var10);
               this.targets.add(var10);
               ++var8;
               ++var6;
            }
         }
      }

      if (this.targets.size() == 0) {
         throw new InvalidInjectionException(this, String.format("%s annotation on %s could not find any targets matching %s in the target class %s. %s%s", var2, this.method.name, namesOf(var1), this.mixin.getTarget(), this.mixin.getReferenceMapper().getStatus(), this.getDynamicInfo()));
      }
   }

   private void checkTarget(MethodNode var1) {
      AnnotationNode var2 = Annotations.getVisible(var1, MixinMerged.class);
      if (var2 != null) {
         String var3 = (String)Annotations.getValue(var2, "mixin");
         int var4 = (Integer)Annotations.getValue(var2, "priority");
         if (var4 >= this.mixin.getPriority() && !var3.equals(this.mixin.getClassName())) {
            throw new InvalidInjectionException(this, String.format("%s cannot inject into %s::%s%s merged by %s with priority %d", this, this.classNode.name, var1.name, var1.desc, var3, var4));
         } else if (Annotations.getVisible(var1, Final.class) != null) {
            throw new InvalidInjectionException(this, String.format("%s cannot inject into @Final method %s::%s%s merged by %s", this, this.classNode.name, var1.name, var1.desc, var3));
         }
      }
   }

   protected String getDynamicInfo() {
      AnnotationNode var1 = Annotations.getInvisible(this.method, Dynamic.class);
      String var2 = Strings.nullToEmpty((String)Annotations.getValue(var1));
      Type var3 = (Type)Annotations.getValue(var1, "mixin");
      if (var3 != null) {
         var2 = String.format("{%s} %s", var3.getClassName(), var2).trim();
      }

      return var2.length() > 0 ? String.format(" Method is @Dynamic(%s)", var2) : "";
   }

   public static InjectionInfo parse(MixinTargetContext var0, MethodNode var1) {
      AnnotationNode var2 = getInjectorAnnotation(var0.getMixin(), var1);
      if (var2 == null) {
         return null;
      } else if (var2.desc.endsWith(Inject.class.getSimpleName() + ";")) {
         return new CallbackInjectionInfo(var0, var1, var2);
      } else if (var2.desc.endsWith(ModifyArg.class.getSimpleName() + ";")) {
         return new ModifyArgInjectionInfo(var0, var1, var2);
      } else if (var2.desc.endsWith(ModifyArgs.class.getSimpleName() + ";")) {
         return new ModifyArgsInjectionInfo(var0, var1, var2);
      } else if (var2.desc.endsWith(Redirect.class.getSimpleName() + ";")) {
         return new RedirectInjectionInfo(var0, var1, var2);
      } else if (var2.desc.endsWith(ModifyVariable.class.getSimpleName() + ";")) {
         return new ModifyVariableInjectionInfo(var0, var1, var2);
      } else {
         return var2.desc.endsWith(ModifyConstant.class.getSimpleName() + ";") ? new ModifyConstantInjectionInfo(var0, var1, var2) : null;
      }
   }

   public static AnnotationNode getInjectorAnnotation(IMixinInfo var0, MethodNode var1) {
      AnnotationNode var2 = null;

      try {
         var2 = Annotations.getSingleVisible(var1, Inject.class, ModifyArg.class, ModifyArgs.class, Redirect.class, ModifyVariable.class, ModifyConstant.class);
         return var2;
      } catch (IllegalArgumentException var4) {
         throw new InvalidMixinException(var0, String.format("Error parsing annotations on %s in %s: %s", var1.name, var0.getClassName(), var4.getMessage()));
      }
   }

   public static String getInjectorPrefix(AnnotationNode var0) {
      if (var0 != null) {
         if (var0.desc.endsWith(ModifyArg.class.getSimpleName() + ";")) {
            return "modify";
         }

         if (var0.desc.endsWith(ModifyArgs.class.getSimpleName() + ";")) {
            return "args";
         }

         if (var0.desc.endsWith(Redirect.class.getSimpleName() + ";")) {
            return "redirect";
         }

         if (var0.desc.endsWith(ModifyVariable.class.getSimpleName() + ";")) {
            return "localvar";
         }

         if (var0.desc.endsWith(ModifyConstant.class.getSimpleName() + ";")) {
            return "constant";
         }
      }

      return "handler";
   }

   static String describeInjector(IMixinContext var0, AnnotationNode var1, MethodNode var2) {
      return String.format("%s->@%s::%s%s", var0.toString(), Bytecode.getSimpleName(var1), var2.name, var2.desc);
   }

   private static String namesOf(Collection var0) {
      int var1 = 0;
      int var2 = var0.size();
      StringBuilder var3 = new StringBuilder();

      for(Iterator var4 = var0.iterator(); var4.hasNext(); ++var1) {
         MemberInfo var5 = (MemberInfo)var4.next();
         if (var1 > 0) {
            if (var1 == var2 - 1) {
               var3.append(" or ");
            } else {
               var3.append(", ");
            }
         }

         var3.append('\'').append(var5.name).append('\'');
      }

      return var3.toString();
   }
}
