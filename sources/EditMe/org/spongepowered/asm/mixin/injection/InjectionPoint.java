package org.spongepowered.asm.mixin.injection;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.injection.modify.AfterStoreLocal;
import org.spongepowered.asm.mixin.injection.modify.BeforeLoadLocal;
import org.spongepowered.asm.mixin.injection.points.AfterInvoke;
import org.spongepowered.asm.mixin.injection.points.BeforeConstant;
import org.spongepowered.asm.mixin.injection.points.BeforeFieldAccess;
import org.spongepowered.asm.mixin.injection.points.BeforeFinalReturn;
import org.spongepowered.asm.mixin.injection.points.BeforeInvoke;
import org.spongepowered.asm.mixin.injection.points.BeforeNew;
import org.spongepowered.asm.mixin.injection.points.BeforeReturn;
import org.spongepowered.asm.mixin.injection.points.BeforeStringInvoke;
import org.spongepowered.asm.mixin.injection.points.JumpInsnPoint;
import org.spongepowered.asm.mixin.injection.points.MethodHead;
import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
import org.spongepowered.asm.mixin.refmap.IMixinContext;
import org.spongepowered.asm.mixin.transformer.MixinTargetContext;
import org.spongepowered.asm.util.Annotations;
import org.spongepowered.asm.util.Bytecode;

public abstract class InjectionPoint {
   public static final int DEFAULT_ALLOWED_SHIFT_BY = 0;
   public static final int MAX_ALLOWED_SHIFT_BY = 0;
   private static Map types = new HashMap();
   private final String slice;
   private final InjectionPoint.Selector selector;
   private final String id;

   protected InjectionPoint() {
      this("", InjectionPoint.Selector.DEFAULT, (String)null);
   }

   protected InjectionPoint(InjectionPointData var1) {
      this(var1.getSlice(), var1.getSelector(), var1.getId());
   }

   public InjectionPoint(String var1, InjectionPoint.Selector var2, String var3) {
      this.slice = var1;
      this.selector = var2;
      this.id = var3;
   }

   public String getSlice() {
      return this.slice;
   }

   public InjectionPoint.Selector getSelector() {
      return this.selector;
   }

   public String getId() {
      return this.id;
   }

   public abstract boolean find(String var1, InsnList var2, Collection var3);

   public String toString() {
      return String.format("@At(\"%s\")", this.getAtCode());
   }

   protected static AbstractInsnNode nextNode(InsnList var0, AbstractInsnNode var1) {
      int var2 = var0.indexOf(var1) + 1;
      return var2 > 0 && var2 < var0.size() ? var0.get(var2) : var1;
   }

   public static InjectionPoint and(InjectionPoint... var0) {
      return new InjectionPoint.Intersection(var0);
   }

   public static InjectionPoint or(InjectionPoint... var0) {
      return new InjectionPoint.Union(var0);
   }

   public static InjectionPoint after(InjectionPoint var0) {
      return new InjectionPoint.Shift(var0, 1);
   }

   public static InjectionPoint before(InjectionPoint var0) {
      return new InjectionPoint.Shift(var0, -1);
   }

   public static InjectionPoint shift(InjectionPoint var0, int var1) {
      return new InjectionPoint.Shift(var0, var1);
   }

   public static List parse(IInjectionPointContext var0, List var1) {
      return parse(var0.getContext(), var0.getMethod(), var0.getAnnotation(), var1);
   }

   public static List parse(IMixinContext var0, MethodNode var1, AnnotationNode var2, List var3) {
      Builder var4 = ImmutableList.builder();
      Iterator var5 = var3.iterator();

      while(var5.hasNext()) {
         AnnotationNode var6 = (AnnotationNode)var5.next();
         InjectionPoint var7 = parse(var0, var1, var2, var6);
         if (var7 != null) {
            var4.add(var7);
         }
      }

      return var4.build();
   }

   public static InjectionPoint parse(IInjectionPointContext var0, At var1) {
      return parse(var0.getContext(), var0.getMethod(), var0.getAnnotation(), var1.value(), var1.shift(), var1.by(), Arrays.asList(var1.args()), var1.target(), var1.slice(), var1.ordinal(), var1.opcode(), var1.id());
   }

   public static InjectionPoint parse(IMixinContext var0, MethodNode var1, AnnotationNode var2, At var3) {
      return parse(var0, var1, var2, var3.value(), var3.shift(), var3.by(), Arrays.asList(var3.args()), var3.target(), var3.slice(), var3.ordinal(), var3.opcode(), var3.id());
   }

   public static InjectionPoint parse(IInjectionPointContext var0, AnnotationNode var1) {
      return parse(var0.getContext(), var0.getMethod(), var0.getAnnotation(), var1);
   }

   public static InjectionPoint parse(IMixinContext var0, MethodNode var1, AnnotationNode var2, AnnotationNode var3) {
      String var4 = (String)Annotations.getValue(var3, "value");
      Object var5 = (List)Annotations.getValue(var3, "args");
      String var6 = (String)Annotations.getValue(var3, "target", (Object)"");
      String var7 = (String)Annotations.getValue(var3, "slice", (Object)"");
      At.Shift var8 = (At.Shift)Annotations.getValue(var3, "shift", At.Shift.class, At.Shift.NONE);
      int var9 = (Integer)Annotations.getValue(var3, "by", (int)0);
      int var10 = (Integer)Annotations.getValue(var3, "ordinal", (int)-1);
      int var11 = (Integer)Annotations.getValue(var3, "opcode", (int)0);
      String var12 = (String)Annotations.getValue(var3, "id");
      if (var5 == null) {
         var5 = ImmutableList.of();
      }

      return parse(var0, var1, var2, var4, var8, var9, (List)var5, var6, var7, var10, var11, var12);
   }

   public static InjectionPoint parse(IMixinContext var0, MethodNode var1, AnnotationNode var2, String var3, At.Shift var4, int var5, List var6, String var7, String var8, int var9, int var10, String var11) {
      InjectionPointData var12 = new InjectionPointData(var0, var1, var2, var3, var6, var7, var8, var9, var10, var11);
      Class var13 = findClass(var0, var12);
      InjectionPoint var14 = create(var0, var12, var13);
      return shift(var0, var1, var2, var14, var4, var5);
   }

   private static Class findClass(IMixinContext var0, InjectionPointData var1) {
      String var2 = var1.getType();
      Class var3 = (Class)types.get(var2);
      if (var3 == null) {
         if (!var2.matches("^([A-Za-z_][A-Za-z0-9_]*\\.)+[A-Za-z_][A-Za-z0-9_]*$")) {
            throw new InvalidInjectionException(var0, var1 + " is not a valid injection point specifier");
         }

         try {
            var3 = Class.forName(var2);
            types.put(var2, var3);
         } catch (Exception var5) {
            throw new InvalidInjectionException(var0, var1 + " could not be loaded or is not a valid InjectionPoint", var5);
         }
      }

      return var3;
   }

   private static InjectionPoint create(IMixinContext var0, InjectionPointData var1, Class var2) {
      Constructor var3 = null;

      try {
         var3 = var2.getDeclaredConstructor(InjectionPointData.class);
         var3.setAccessible(true);
      } catch (NoSuchMethodException var7) {
         throw new InvalidInjectionException(var0, var2.getName() + " must contain a constructor which accepts an InjectionPointData", var7);
      }

      InjectionPoint var4 = null;

      try {
         var4 = (InjectionPoint)var3.newInstance(var1);
         return var4;
      } catch (Exception var6) {
         throw new InvalidInjectionException(var0, "Error whilst instancing injection point " + var2.getName() + " for " + var1.getAt(), var6);
      }
   }

   private static InjectionPoint shift(IMixinContext var0, MethodNode var1, AnnotationNode var2, InjectionPoint var3, At.Shift var4, int var5) {
      if (var3 != null) {
         if (var4 == At.Shift.BEFORE) {
            return before(var3);
         }

         if (var4 == At.Shift.AFTER) {
            return after(var3);
         }

         if (var4 == At.Shift.BY) {
            validateByValue(var0, var1, var2, var3, var5);
            return shift(var3, var5);
         }
      }

      return var3;
   }

   private static void validateByValue(IMixinContext var0, MethodNode var1, AnnotationNode var2, InjectionPoint var3, int var4) {
      MixinEnvironment var5 = var0.getMixin().getConfig().getEnvironment();
      InjectionPoint.ShiftByViolationBehaviour var6 = (InjectionPoint.ShiftByViolationBehaviour)var5.getOption(MixinEnvironment.Option.SHIFT_BY_VIOLATION_BEHAVIOUR, InjectionPoint.ShiftByViolationBehaviour.WARN);
      if (var6 != InjectionPoint.ShiftByViolationBehaviour.IGNORE) {
         int var7 = 0;
         if (var0 instanceof MixinTargetContext) {
            var7 = ((MixinTargetContext)var0).getMaxShiftByValue();
         }

         if (var4 > var7) {
            String var8 = String.format("@%s(%s) Shift.BY=%d on %s::%s exceeds the maximum allowed value %d.", Bytecode.getSimpleName(var2), var3, var4, var0, var1.name, var7);
            if (var6 == InjectionPoint.ShiftByViolationBehaviour.WARN) {
               LogManager.getLogger("mixin").warn("{} Increase the value of maxShiftBy to suppress this warning.", new Object[]{var8});
            } else {
               throw new InvalidInjectionException(var0, var8);
            }
         }
      }
   }

   protected String getAtCode() {
      InjectionPoint.AtCode var1 = (InjectionPoint.AtCode)this.getClass().getAnnotation(InjectionPoint.AtCode.class);
      return var1 == null ? this.getClass().getName() : var1.value();
   }

   public static void register(Class var0) {
      InjectionPoint.AtCode var1 = (InjectionPoint.AtCode)var0.getAnnotation(InjectionPoint.AtCode.class);
      if (var1 == null) {
         throw new IllegalArgumentException("Injection point class " + var0 + " is not annotated with @AtCode");
      } else {
         Class var2 = (Class)types.get(var1.value());
         if (var2 != null && !var2.equals(var0)) {
            LogManager.getLogger("mixin").debug("Overriding InjectionPoint {} with {} (previously {})", new Object[]{var1.value(), var0.getName(), var2.getName()});
         }

         types.put(var1.value(), var0);
      }
   }

   static {
      register(BeforeFieldAccess.class);
      register(BeforeInvoke.class);
      register(BeforeNew.class);
      register(BeforeReturn.class);
      register(BeforeStringInvoke.class);
      register(JumpInsnPoint.class);
      register(MethodHead.class);
      register(AfterInvoke.class);
      register(BeforeLoadLocal.class);
      register(AfterStoreLocal.class);
      register(BeforeFinalReturn.class);
      register(BeforeConstant.class);
   }

   static final class Shift extends InjectionPoint {
      private final InjectionPoint input;
      private final int shift;

      public Shift(InjectionPoint var1, int var2) {
         if (var1 == null) {
            throw new IllegalArgumentException("Must supply an input injection point for SHIFT");
         } else {
            this.input = var1;
            this.shift = var2;
         }
      }

      public String toString() {
         return "InjectionPoint(" + this.getClass().getSimpleName() + ")[" + this.input + "]";
      }

      public boolean find(String var1, InsnList var2, Collection var3) {
         Object var4 = var3 instanceof List ? (List)var3 : new ArrayList(var3);
         this.input.find(var1, var2, var3);

         for(int var5 = 0; var5 < ((List)var4).size(); ++var5) {
            ((List)var4).set(var5, var2.get(var2.indexOf((AbstractInsnNode)((List)var4).get(var5)) + this.shift));
         }

         if (var3 != var4) {
            var3.clear();
            var3.addAll((Collection)var4);
         }

         return var3.size() > 0;
      }
   }

   static final class Union extends InjectionPoint.CompositeInjectionPoint {
      public Union(InjectionPoint... var1) {
         super(var1);
      }

      public boolean find(String var1, InsnList var2, Collection var3) {
         LinkedHashSet var4 = new LinkedHashSet();

         for(int var5 = 0; var5 < this.components.length; ++var5) {
            this.components[var5].find(var1, var2, var4);
         }

         var3.addAll(var4);
         return var4.size() > 0;
      }
   }

   static final class Intersection extends InjectionPoint.CompositeInjectionPoint {
      public Intersection(InjectionPoint... var1) {
         super(var1);
      }

      public boolean find(String var1, InsnList var2, Collection var3) {
         boolean var4 = false;
         ArrayList[] var5 = (ArrayList[])((ArrayList[])Array.newInstance(ArrayList.class, this.components.length));

         for(int var6 = 0; var6 < this.components.length; ++var6) {
            var5[var6] = new ArrayList();
            this.components[var6].find(var1, var2, var5[var6]);
         }

         ArrayList var11 = var5[0];

         for(int var7 = 0; var7 < var11.size(); ++var7) {
            AbstractInsnNode var8 = (AbstractInsnNode)var11.get(var7);
            boolean var9 = true;

            for(int var10 = 1; var10 < var5.length && var5[var10].contains(var8); ++var10) {
            }

            if (var9) {
               var3.add(var8);
               var4 = true;
            }
         }

         return var4;
      }
   }

   abstract static class CompositeInjectionPoint extends InjectionPoint {
      protected final InjectionPoint[] components;

      protected CompositeInjectionPoint(InjectionPoint... var1) {
         if (var1 != null && var1.length >= 2) {
            this.components = var1;
         } else {
            throw new IllegalArgumentException("Must supply two or more component injection points for composite point!");
         }
      }

      public String toString() {
         return "CompositeInjectionPoint(" + this.getClass().getSimpleName() + ")[" + Joiner.on(',').join(this.components) + "]";
      }
   }

   static enum ShiftByViolationBehaviour {
      IGNORE,
      WARN,
      ERROR;

      private static final InjectionPoint.ShiftByViolationBehaviour[] $VALUES = new InjectionPoint.ShiftByViolationBehaviour[]{IGNORE, WARN, ERROR};
   }

   public static enum Selector {
      FIRST,
      LAST,
      ONE;

      public static final InjectionPoint.Selector DEFAULT = FIRST;
      private static final InjectionPoint.Selector[] $VALUES = new InjectionPoint.Selector[]{FIRST, LAST, ONE};
   }

   @Retention(RetentionPolicy.RUNTIME)
   @Target({ElementType.TYPE})
   public @interface AtCode {
      String value();
   }
}
