package org.spongepowered.asm.mixin.injection.points;

import com.google.common.primitives.Doubles;
import com.google.common.primitives.Floats;
import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.FrameNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.LabelNode;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
import org.spongepowered.asm.mixin.refmap.IMixinContext;
import org.spongepowered.asm.util.Annotations;
import org.spongepowered.asm.util.Bytecode;

@InjectionPoint.AtCode("CONSTANT")
public class BeforeConstant extends InjectionPoint {
   private static final Logger logger = LogManager.getLogger("mixin");
   private final int ordinal;
   private final boolean nullValue;
   private final Integer intValue;
   private final Float floatValue;
   private final Long longValue;
   private final Double doubleValue;
   private final String stringValue;
   private final Type typeValue;
   private final int[] expandOpcodes;
   private final boolean expand;
   private final String matchByType;
   private final boolean log;

   public BeforeConstant(IMixinContext var1, AnnotationNode var2, String var3) {
      super((String)Annotations.getValue(var2, "slice", (Object)""), InjectionPoint.Selector.DEFAULT, (String)null);
      Boolean var4 = (Boolean)Annotations.getValue(var2, "nullValue", (Object)((Boolean)null));
      this.ordinal = (Integer)Annotations.getValue(var2, "ordinal", (int)-1);
      this.nullValue = var4 != null && var4;
      this.intValue = (Integer)Annotations.getValue(var2, "intValue", (Object)((Integer)null));
      this.floatValue = (Float)Annotations.getValue(var2, "floatValue", (Object)((Float)null));
      this.longValue = (Long)Annotations.getValue(var2, "longValue", (Object)((Long)null));
      this.doubleValue = (Double)Annotations.getValue(var2, "doubleValue", (Object)((Double)null));
      this.stringValue = (String)Annotations.getValue(var2, "stringValue", (Object)((String)null));
      this.typeValue = (Type)Annotations.getValue(var2, "classValue", (Object)((Type)null));
      this.matchByType = this.validateDiscriminator(var1, var3, var4, "on @Constant annotation");
      this.expandOpcodes = this.parseExpandOpcodes(Annotations.getValue(var2, "expandZeroConditions", true, Constant.Condition.class));
      this.expand = this.expandOpcodes.length > 0;
      this.log = (Boolean)Annotations.getValue(var2, "log", (Object)Boolean.FALSE);
   }

   public BeforeConstant(InjectionPointData var1) {
      super(var1);
      String var2 = var1.get("nullValue", (String)null);
      Boolean var3 = var2 != null ? Boolean.parseBoolean(var2) : null;
      this.ordinal = var1.getOrdinal();
      this.nullValue = var3 != null && var3;
      this.intValue = Ints.tryParse(var1.get("intValue", ""));
      this.floatValue = Floats.tryParse(var1.get("floatValue", ""));
      this.longValue = Longs.tryParse(var1.get("longValue", ""));
      this.doubleValue = Doubles.tryParse(var1.get("doubleValue", ""));
      this.stringValue = var1.get("stringValue", (String)null);
      String var4 = var1.get("classValue", (String)null);
      this.typeValue = var4 != null ? Type.getObjectType(var4.replace('.', '/')) : null;
      this.matchByType = this.validateDiscriminator(var1.getContext(), "V", var3, "in @At(\"CONSTANT\") args");
      if ("V".equals(this.matchByType)) {
         throw new InvalidInjectionException(var1.getContext(), "No constant discriminator could be parsed in @At(\"CONSTANT\") args");
      } else {
         ArrayList var5 = new ArrayList();
         String var6 = var1.get("expandZeroConditions", "").toLowerCase();
         Constant.Condition[] var7 = Constant.Condition.values();
         int var8 = var7.length;

         for(int var9 = 0; var9 < var8; ++var9) {
            Constant.Condition var10 = var7[var9];
            if (var6.contains(var10.name().toLowerCase())) {
               var5.add(var10);
            }
         }

         this.expandOpcodes = this.parseExpandOpcodes(var5);
         this.expand = this.expandOpcodes.length > 0;
         this.log = var1.get("log", false);
      }
   }

   private String validateDiscriminator(IMixinContext var1, String var2, Boolean var3, String var4) {
      int var5 = count(var3, this.intValue, this.floatValue, this.longValue, this.doubleValue, this.stringValue, this.typeValue);
      if (var5 == 1) {
         var2 = null;
      } else if (var5 > 1) {
         throw new InvalidInjectionException(var1, "Conflicting constant discriminators specified " + var4 + " for " + var1);
      }

      return var2;
   }

   private int[] parseExpandOpcodes(List var1) {
      HashSet var2 = new HashSet();
      Iterator var3 = var1.iterator();

      while(var3.hasNext()) {
         Constant.Condition var4 = (Constant.Condition)var3.next();
         Constant.Condition var5 = var4.getEquivalentCondition();
         int[] var6 = var5.getOpcodes();
         int var7 = var6.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            int var9 = var6[var8];
            var2.add(var9);
         }
      }

      return Ints.toArray(var2);
   }

   public boolean find(String var1, InsnList var2, Collection var3) {
      boolean var4 = false;
      this.log("BeforeConstant is searching for constants in method with descriptor {}", var1);
      ListIterator var5 = var2.iterator();
      int var6 = 0;
      int var7 = 0;

      while(var5.hasNext()) {
         AbstractInsnNode var8 = (AbstractInsnNode)var5.next();
         boolean var9 = this.expand ? this.matchesConditionalInsn(var7, var8) : this.matchesConstantInsn(var8);
         if (var9) {
            this.log("    BeforeConstant found a matching constant{} at ordinal {}", this.matchByType != null ? " TYPE" : " value", var6);
            if (this.ordinal == -1 || this.ordinal == var6) {
               this.log("      BeforeConstant found {}", Bytecode.describeNode(var8).trim());
               var3.add(var8);
               var4 = true;
            }

            ++var6;
         }

         if (!(var8 instanceof LabelNode) && !(var8 instanceof FrameNode)) {
            var7 = var8.getOpcode();
         }
      }

      return var4;
   }

   private boolean matchesConditionalInsn(int var1, AbstractInsnNode var2) {
      int[] var3 = this.expandOpcodes;
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         int var6 = var3[var5];
         int var7 = var2.getOpcode();
         if (var7 == var6) {
            if (var1 != 148 && var1 != 149 && var1 != 150 && var1 != 151 && var1 != 152) {
               this.log("  BeforeConstant found {} instruction", Bytecode.getOpcodeName(var7));
               return true;
            }

            this.log("  BeforeConstant is ignoring {} following {}", Bytecode.getOpcodeName(var7), Bytecode.getOpcodeName(var1));
            return false;
         }
      }

      if (this.intValue != null && this.intValue == 0 && Bytecode.isConstant(var2)) {
         Object var8 = Bytecode.getConstant(var2);
         this.log("  BeforeConstant found INTEGER constant: value = {}", var8);
         return var8 instanceof Integer && (Integer)var8 == 0;
      } else {
         return false;
      }
   }

   private boolean matchesConstantInsn(AbstractInsnNode var1) {
      if (!Bytecode.isConstant(var1)) {
         return false;
      } else {
         Object var2 = Bytecode.getConstant(var1);
         if (var2 == null) {
            this.log("  BeforeConstant found NULL constant: nullValue = {}", this.nullValue);
            return this.nullValue || "Ljava/lang/Object;".equals(this.matchByType);
         } else if (var2 instanceof Integer) {
            this.log("  BeforeConstant found INTEGER constant: value = {}, intValue = {}", var2, this.intValue);
            return var2.equals(this.intValue) || "I".equals(this.matchByType);
         } else if (var2 instanceof Float) {
            this.log("  BeforeConstant found FLOAT constant: value = {}, floatValue = {}", var2, this.floatValue);
            return var2.equals(this.floatValue) || "F".equals(this.matchByType);
         } else if (var2 instanceof Long) {
            this.log("  BeforeConstant found LONG constant: value = {}, longValue = {}", var2, this.longValue);
            return var2.equals(this.longValue) || "J".equals(this.matchByType);
         } else if (var2 instanceof Double) {
            this.log("  BeforeConstant found DOUBLE constant: value = {}, doubleValue = {}", var2, this.doubleValue);
            return var2.equals(this.doubleValue) || "D".equals(this.matchByType);
         } else if (var2 instanceof String) {
            this.log("  BeforeConstant found STRING constant: value = {}, stringValue = {}", var2, this.stringValue);
            return var2.equals(this.stringValue) || "Ljava/lang/String;".equals(this.matchByType);
         } else if (!(var2 instanceof Type)) {
            return false;
         } else {
            this.log("  BeforeConstant found CLASS constant: value = {}, typeValue = {}", var2, this.typeValue);
            return var2.equals(this.typeValue) || "Ljava/lang/Class;".equals(this.matchByType);
         }
      }
   }

   protected void log(String var1, Object... var2) {
      if (this.log) {
         logger.info(var1, var2);
      }

   }

   private static int count(Object... var0) {
      int var1 = 0;
      Object[] var2 = var0;
      int var3 = var0.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Object var5 = var2[var4];
         if (var5 != null) {
            ++var1;
         }
      }

      return var1;
   }
}
