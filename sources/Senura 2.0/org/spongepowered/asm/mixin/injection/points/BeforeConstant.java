/*     */ package org.spongepowered.asm.mixin.injection.points;
/*     */ 
/*     */ import com.google.common.primitives.Doubles;
/*     */ import com.google.common.primitives.Floats;
/*     */ import com.google.common.primitives.Ints;
/*     */ import com.google.common.primitives.Longs;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import java.util.Set;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.spongepowered.asm.lib.Type;
/*     */ import org.spongepowered.asm.lib.tree.AbstractInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.AnnotationNode;
/*     */ import org.spongepowered.asm.lib.tree.InsnList;
/*     */ import org.spongepowered.asm.mixin.injection.Constant;
/*     */ import org.spongepowered.asm.mixin.injection.InjectionPoint;
/*     */ import org.spongepowered.asm.mixin.injection.InjectionPoint.AtCode;
/*     */ import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;
/*     */ import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
/*     */ import org.spongepowered.asm.mixin.refmap.IMixinContext;
/*     */ import org.spongepowered.asm.util.Annotations;
/*     */ import org.spongepowered.asm.util.Bytecode;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @AtCode("CONSTANT")
/*     */ public class BeforeConstant
/*     */   extends InjectionPoint
/*     */ {
/* 126 */   private static final Logger logger = LogManager.getLogger("mixin");
/*     */   
/*     */   private final int ordinal;
/*     */   
/*     */   private final boolean nullValue;
/*     */   
/*     */   private final Integer intValue;
/*     */   
/*     */   private final Float floatValue;
/*     */   
/*     */   private final Long longValue;
/*     */   
/*     */   private final Double doubleValue;
/*     */   
/*     */   private final String stringValue;
/*     */   
/*     */   private final Type typeValue;
/*     */   private final int[] expandOpcodes;
/*     */   private final boolean expand;
/*     */   private final String matchByType;
/*     */   private final boolean log;
/*     */   
/*     */   public BeforeConstant(IMixinContext context, AnnotationNode node, String returnType) {
/* 149 */     super((String)Annotations.getValue(node, "slice", ""), InjectionPoint.Selector.DEFAULT, null);
/*     */     
/* 151 */     Boolean empty = (Boolean)Annotations.getValue(node, "nullValue", null);
/* 152 */     this.ordinal = ((Integer)Annotations.getValue(node, "ordinal", Integer.valueOf(-1))).intValue();
/* 153 */     this.nullValue = (empty != null && empty.booleanValue());
/* 154 */     this.intValue = (Integer)Annotations.getValue(node, "intValue", null);
/* 155 */     this.floatValue = (Float)Annotations.getValue(node, "floatValue", null);
/* 156 */     this.longValue = (Long)Annotations.getValue(node, "longValue", null);
/* 157 */     this.doubleValue = (Double)Annotations.getValue(node, "doubleValue", null);
/* 158 */     this.stringValue = (String)Annotations.getValue(node, "stringValue", null);
/* 159 */     this.typeValue = (Type)Annotations.getValue(node, "classValue", null);
/*     */     
/* 161 */     this.matchByType = validateDiscriminator(context, returnType, empty, "on @Constant annotation");
/* 162 */     this.expandOpcodes = parseExpandOpcodes(Annotations.getValue(node, "expandZeroConditions", true, Constant.Condition.class));
/* 163 */     this.expand = (this.expandOpcodes.length > 0);
/*     */     
/* 165 */     this.log = ((Boolean)Annotations.getValue(node, "log", Boolean.FALSE)).booleanValue();
/*     */   }
/*     */   
/*     */   public BeforeConstant(InjectionPointData data) {
/* 169 */     super(data);
/*     */     
/* 171 */     String strNullValue = data.get("nullValue", null);
/* 172 */     Boolean empty = (strNullValue != null) ? Boolean.valueOf(Boolean.parseBoolean(strNullValue)) : null;
/*     */     
/* 174 */     this.ordinal = data.getOrdinal();
/* 175 */     this.nullValue = (empty != null && empty.booleanValue());
/* 176 */     this.intValue = Ints.tryParse(data.get("intValue", ""));
/* 177 */     this.floatValue = Floats.tryParse(data.get("floatValue", ""));
/* 178 */     this.longValue = Longs.tryParse(data.get("longValue", ""));
/* 179 */     this.doubleValue = Doubles.tryParse(data.get("doubleValue", ""));
/* 180 */     this.stringValue = data.get("stringValue", null);
/* 181 */     String strClassValue = data.get("classValue", null);
/* 182 */     this.typeValue = (strClassValue != null) ? Type.getObjectType(strClassValue.replace('.', '/')) : null;
/*     */     
/* 184 */     this.matchByType = validateDiscriminator(data.getContext(), "V", empty, "in @At(\"CONSTANT\") args");
/* 185 */     if ("V".equals(this.matchByType)) {
/* 186 */       throw new InvalidInjectionException(data.getContext(), "No constant discriminator could be parsed in @At(\"CONSTANT\") args");
/*     */     }
/*     */     
/* 189 */     List<Constant.Condition> conditions = new ArrayList<Constant.Condition>();
/* 190 */     String strConditions = data.get("expandZeroConditions", "").toLowerCase();
/* 191 */     for (Constant.Condition condition : Constant.Condition.values()) {
/* 192 */       if (strConditions.contains(condition.name().toLowerCase())) {
/* 193 */         conditions.add(condition);
/*     */       }
/*     */     } 
/*     */     
/* 197 */     this.expandOpcodes = parseExpandOpcodes(conditions);
/* 198 */     this.expand = (this.expandOpcodes.length > 0);
/*     */     
/* 200 */     this.log = data.get("log", false);
/*     */   }
/*     */   
/*     */   private String validateDiscriminator(IMixinContext context, String returnType, Boolean empty, String type) {
/* 204 */     int c = count(new Object[] { empty, this.intValue, this.floatValue, this.longValue, this.doubleValue, this.stringValue, this.typeValue });
/* 205 */     if (c == 1) {
/* 206 */       returnType = null;
/* 207 */     } else if (c > 1) {
/* 208 */       throw new InvalidInjectionException(context, "Conflicting constant discriminators specified " + type + " for " + context);
/*     */     } 
/* 210 */     return returnType;
/*     */   }
/*     */   
/*     */   private int[] parseExpandOpcodes(List<Constant.Condition> conditions) {
/* 214 */     Set<Integer> opcodes = new HashSet<Integer>();
/* 215 */     for (Constant.Condition condition : conditions) {
/* 216 */       Constant.Condition actual = condition.getEquivalentCondition();
/* 217 */       for (int opcode : actual.getOpcodes()) {
/* 218 */         opcodes.add(Integer.valueOf(opcode));
/*     */       }
/*     */     } 
/* 221 */     return Ints.toArray(opcodes);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean find(String desc, InsnList insns, Collection<AbstractInsnNode> nodes) {
/* 226 */     boolean found = false;
/*     */     
/* 228 */     log("BeforeConstant is searching for constants in method with descriptor {}", new Object[] { desc });
/*     */     
/* 230 */     ListIterator<AbstractInsnNode> iter = insns.iterator(); int last;
/* 231 */     for (int ordinal = 0; iter.hasNext(); ) {
/* 232 */       AbstractInsnNode insn = iter.next();
/*     */       
/* 234 */       boolean matchesInsn = this.expand ? matchesConditionalInsn(last, insn) : matchesConstantInsn(insn);
/* 235 */       if (matchesInsn) {
/* 236 */         log("    BeforeConstant found a matching constant{} at ordinal {}", new Object[] { (this.matchByType != null) ? " TYPE" : " value", Integer.valueOf(ordinal) });
/* 237 */         if (this.ordinal == -1 || this.ordinal == ordinal) {
/* 238 */           log("      BeforeConstant found {}", new Object[] { Bytecode.describeNode(insn).trim() });
/* 239 */           nodes.add(insn);
/* 240 */           found = true;
/*     */         } 
/* 242 */         ordinal++;
/*     */       } 
/*     */       
/* 245 */       if (!(insn instanceof org.spongepowered.asm.lib.tree.LabelNode) && !(insn instanceof org.spongepowered.asm.lib.tree.FrameNode)) {
/* 246 */         last = insn.getOpcode();
/*     */       }
/*     */     } 
/*     */     
/* 250 */     return found;
/*     */   }
/*     */   
/*     */   private boolean matchesConditionalInsn(int last, AbstractInsnNode insn) {
/* 254 */     for (int conditionalOpcode : this.expandOpcodes) {
/* 255 */       int opcode = insn.getOpcode();
/* 256 */       if (opcode == conditionalOpcode) {
/* 257 */         if (last == 148 || last == 149 || last == 150 || last == 151 || last == 152) {
/* 258 */           log("  BeforeConstant is ignoring {} following {}", new Object[] { Bytecode.getOpcodeName(opcode), Bytecode.getOpcodeName(last) });
/* 259 */           return false;
/*     */         } 
/*     */         
/* 262 */         log("  BeforeConstant found {} instruction", new Object[] { Bytecode.getOpcodeName(opcode) });
/* 263 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 267 */     if (this.intValue != null && this.intValue.intValue() == 0 && Bytecode.isConstant(insn)) {
/* 268 */       Object value = Bytecode.getConstant(insn);
/* 269 */       log("  BeforeConstant found INTEGER constant: value = {}", new Object[] { value });
/* 270 */       return (value instanceof Integer && ((Integer)value).intValue() == 0);
/*     */     } 
/*     */     
/* 273 */     return false;
/*     */   }
/*     */   
/*     */   private boolean matchesConstantInsn(AbstractInsnNode insn) {
/* 277 */     if (!Bytecode.isConstant(insn)) {
/* 278 */       return false;
/*     */     }
/*     */     
/* 281 */     Object value = Bytecode.getConstant(insn);
/* 282 */     if (value == null) {
/* 283 */       log("  BeforeConstant found NULL constant: nullValue = {}", new Object[] { Boolean.valueOf(this.nullValue) });
/* 284 */       return (this.nullValue || "Ljava/lang/Object;".equals(this.matchByType));
/* 285 */     }  if (value instanceof Integer) {
/* 286 */       log("  BeforeConstant found INTEGER constant: value = {}, intValue = {}", new Object[] { value, this.intValue });
/* 287 */       return (value.equals(this.intValue) || "I".equals(this.matchByType));
/* 288 */     }  if (value instanceof Float) {
/* 289 */       log("  BeforeConstant found FLOAT constant: value = {}, floatValue = {}", new Object[] { value, this.floatValue });
/* 290 */       return (value.equals(this.floatValue) || "F".equals(this.matchByType));
/* 291 */     }  if (value instanceof Long) {
/* 292 */       log("  BeforeConstant found LONG constant: value = {}, longValue = {}", new Object[] { value, this.longValue });
/* 293 */       return (value.equals(this.longValue) || "J".equals(this.matchByType));
/* 294 */     }  if (value instanceof Double) {
/* 295 */       log("  BeforeConstant found DOUBLE constant: value = {}, doubleValue = {}", new Object[] { value, this.doubleValue });
/* 296 */       return (value.equals(this.doubleValue) || "D".equals(this.matchByType));
/* 297 */     }  if (value instanceof String) {
/* 298 */       log("  BeforeConstant found STRING constant: value = {}, stringValue = {}", new Object[] { value, this.stringValue });
/* 299 */       return (value.equals(this.stringValue) || "Ljava/lang/String;".equals(this.matchByType));
/* 300 */     }  if (value instanceof Type) {
/* 301 */       log("  BeforeConstant found CLASS constant: value = {}, typeValue = {}", new Object[] { value, this.typeValue });
/* 302 */       return (value.equals(this.typeValue) || "Ljava/lang/Class;".equals(this.matchByType));
/*     */     } 
/*     */     
/* 305 */     return false;
/*     */   }
/*     */   
/*     */   protected void log(String message, Object... params) {
/* 309 */     if (this.log) {
/* 310 */       logger.info(message, params);
/*     */     }
/*     */   }
/*     */   
/*     */   private static int count(Object... values) {
/* 315 */     int counter = 0;
/* 316 */     for (Object value : values) {
/* 317 */       if (value != null) {
/* 318 */         counter++;
/*     */       }
/*     */     } 
/* 321 */     return counter;
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\mixin\injection\points\BeforeConstant.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */