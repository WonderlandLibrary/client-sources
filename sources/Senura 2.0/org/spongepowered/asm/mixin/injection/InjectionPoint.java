/*     */ package org.spongepowered.asm.mixin.injection;
/*     */ 
/*     */ import com.google.common.base.Joiner;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import java.lang.annotation.ElementType;
/*     */ import java.lang.annotation.Retention;
/*     */ import java.lang.annotation.RetentionPolicy;
/*     */ import java.lang.annotation.Target;
/*     */ import java.lang.reflect.Array;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.spongepowered.asm.lib.tree.AbstractInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.AnnotationNode;
/*     */ import org.spongepowered.asm.lib.tree.InsnList;
/*     */ import org.spongepowered.asm.lib.tree.MethodNode;
/*     */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*     */ import org.spongepowered.asm.mixin.injection.modify.AfterStoreLocal;
/*     */ import org.spongepowered.asm.mixin.injection.modify.BeforeLoadLocal;
/*     */ import org.spongepowered.asm.mixin.injection.points.AfterInvoke;
/*     */ import org.spongepowered.asm.mixin.injection.points.BeforeConstant;
/*     */ import org.spongepowered.asm.mixin.injection.points.BeforeFieldAccess;
/*     */ import org.spongepowered.asm.mixin.injection.points.BeforeFinalReturn;
/*     */ import org.spongepowered.asm.mixin.injection.points.BeforeInvoke;
/*     */ import org.spongepowered.asm.mixin.injection.points.BeforeNew;
/*     */ import org.spongepowered.asm.mixin.injection.points.BeforeReturn;
/*     */ import org.spongepowered.asm.mixin.injection.points.BeforeStringInvoke;
/*     */ import org.spongepowered.asm.mixin.injection.points.JumpInsnPoint;
/*     */ import org.spongepowered.asm.mixin.injection.points.MethodHead;
/*     */ import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;
/*     */ import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
/*     */ import org.spongepowered.asm.mixin.refmap.IMixinContext;
/*     */ import org.spongepowered.asm.mixin.transformer.MixinTargetContext;
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
/*     */ public abstract class InjectionPoint
/*     */ {
/*     */   public static final int DEFAULT_ALLOWED_SHIFT_BY = 0;
/*     */   public static final int MAX_ALLOWED_SHIFT_BY = 5;
/*     */   
/*     */   @Retention(RetentionPolicy.RUNTIME)
/*     */   @Target({ElementType.TYPE})
/*     */   public static @interface AtCode
/*     */   {
/*     */     String value();
/*     */   }
/*     */   
/*     */   public enum Selector
/*     */   {
/* 112 */     FIRST,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 117 */     LAST,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 124 */     ONE;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 130 */     public static final Selector DEFAULT = FIRST;
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/*     */     
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   enum ShiftByViolationBehaviour
/*     */   {
/* 142 */     IGNORE,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 147 */     WARN,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 152 */     ERROR;
/*     */   }
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
/* 170 */   private static Map<String, Class<? extends InjectionPoint>> types = new HashMap<String, Class<? extends InjectionPoint>>();
/*     */   private final String slice;
/*     */   
/*     */   static {
/* 174 */     register((Class)BeforeFieldAccess.class);
/* 175 */     register((Class)BeforeInvoke.class);
/* 176 */     register((Class)BeforeNew.class);
/* 177 */     register((Class)BeforeReturn.class);
/* 178 */     register((Class)BeforeStringInvoke.class);
/* 179 */     register((Class)JumpInsnPoint.class);
/* 180 */     register((Class)MethodHead.class);
/* 181 */     register((Class)AfterInvoke.class);
/* 182 */     register((Class)BeforeLoadLocal.class);
/* 183 */     register((Class)AfterStoreLocal.class);
/* 184 */     register((Class)BeforeFinalReturn.class);
/* 185 */     register((Class)BeforeConstant.class);
/*     */   }
/*     */ 
/*     */   
/*     */   private final Selector selector;
/*     */   private final String id;
/*     */   
/*     */   protected InjectionPoint() {
/* 193 */     this("", Selector.DEFAULT, null);
/*     */   }
/*     */   
/*     */   protected InjectionPoint(InjectionPointData data) {
/* 197 */     this(data.getSlice(), data.getSelector(), data.getId());
/*     */   }
/*     */   
/*     */   public InjectionPoint(String slice, Selector selector, String id) {
/* 201 */     this.slice = slice;
/* 202 */     this.selector = selector;
/* 203 */     this.id = id;
/*     */   }
/*     */   
/*     */   public String getSlice() {
/* 207 */     return this.slice;
/*     */   }
/*     */   
/*     */   public Selector getSelector() {
/* 211 */     return this.selector;
/*     */   }
/*     */   
/*     */   public String getId() {
/* 215 */     return this.id;
/*     */   }
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
/*     */   public boolean checkPriority(int targetPriority, int mixinPriority) {
/* 231 */     return (targetPriority < mixinPriority);
/*     */   }
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
/*     */   public String toString() {
/* 253 */     return String.format("@At(\"%s\")", new Object[] { getAtCode() });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static AbstractInsnNode nextNode(InsnList insns, AbstractInsnNode insn) {
/* 265 */     int index = insns.indexOf(insn) + 1;
/* 266 */     if (index > 0 && index < insns.size()) {
/* 267 */       return insns.get(index);
/*     */     }
/* 269 */     return insn;
/*     */   }
/*     */ 
/*     */   
/*     */   static abstract class CompositeInjectionPoint
/*     */     extends InjectionPoint
/*     */   {
/*     */     protected final InjectionPoint[] components;
/*     */ 
/*     */     
/*     */     protected CompositeInjectionPoint(InjectionPoint... components) {
/* 280 */       if (components == null || components.length < 2) {
/* 281 */         throw new IllegalArgumentException("Must supply two or more component injection points for composite point!");
/*     */       }
/*     */       
/* 284 */       this.components = components;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/* 292 */       return "CompositeInjectionPoint(" + getClass().getSimpleName() + ")[" + Joiner.on(',').join((Object[])this.components) + "]";
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final class Intersection
/*     */     extends CompositeInjectionPoint
/*     */   {
/*     */     public Intersection(InjectionPoint... points) {
/* 303 */       super(points);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean find(String desc, InsnList insns, Collection<AbstractInsnNode> nodes) {
/* 309 */       boolean found = false;
/*     */       
/* 311 */       ArrayList[] arrayOfArrayList = (ArrayList[])Array.newInstance(ArrayList.class, this.components.length);
/*     */       
/* 313 */       for (int i = 0; i < this.components.length; i++) {
/* 314 */         arrayOfArrayList[i] = new ArrayList();
/* 315 */         this.components[i].find(desc, insns, arrayOfArrayList[i]);
/*     */       } 
/*     */       
/* 318 */       ArrayList<AbstractInsnNode> alpha = arrayOfArrayList[0];
/* 319 */       for (int nodeIndex = 0; nodeIndex < alpha.size(); nodeIndex++) {
/* 320 */         AbstractInsnNode node = alpha.get(nodeIndex);
/* 321 */         boolean in = true;
/*     */         
/* 323 */         for (int b = 1; b < arrayOfArrayList.length && 
/* 324 */           arrayOfArrayList[b].contains(node); b++);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 329 */         if (in) {
/*     */ 
/*     */ 
/*     */           
/* 333 */           nodes.add(node);
/* 334 */           found = true;
/*     */         } 
/*     */       } 
/* 337 */       return found;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final class Union
/*     */     extends CompositeInjectionPoint
/*     */   {
/*     */     public Union(InjectionPoint... points) {
/* 348 */       super(points);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean find(String desc, InsnList insns, Collection<AbstractInsnNode> nodes) {
/* 353 */       LinkedHashSet<AbstractInsnNode> allNodes = new LinkedHashSet<AbstractInsnNode>();
/*     */       
/* 355 */       for (int i = 0; i < this.components.length; i++) {
/* 356 */         this.components[i].find(desc, insns, allNodes);
/*     */       }
/*     */       
/* 359 */       nodes.addAll(allNodes);
/*     */       
/* 361 */       return (allNodes.size() > 0);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static final class Shift
/*     */     extends InjectionPoint
/*     */   {
/*     */     private final InjectionPoint input;
/*     */     
/*     */     private final int shift;
/*     */ 
/*     */     
/*     */     public Shift(InjectionPoint input, int shift) {
/* 375 */       if (input == null) {
/* 376 */         throw new IllegalArgumentException("Must supply an input injection point for SHIFT");
/*     */       }
/*     */       
/* 379 */       this.input = input;
/* 380 */       this.shift = shift;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/* 388 */       return "InjectionPoint(" + getClass().getSimpleName() + ")[" + this.input + "]";
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean find(String desc, InsnList insns, Collection<AbstractInsnNode> nodes) {
/* 393 */       List<AbstractInsnNode> list = (nodes instanceof List) ? (List<AbstractInsnNode>)nodes : new ArrayList<AbstractInsnNode>(nodes);
/*     */       
/* 395 */       this.input.find(desc, insns, nodes);
/*     */       
/* 397 */       for (int i = 0; i < list.size(); i++) {
/* 398 */         list.set(i, insns.get(insns.indexOf(list.get(i)) + this.shift));
/*     */       }
/*     */       
/* 401 */       if (nodes != list) {
/* 402 */         nodes.clear();
/* 403 */         nodes.addAll(list);
/*     */       } 
/*     */       
/* 406 */       return (nodes.size() > 0);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static InjectionPoint and(InjectionPoint... operands) {
/* 418 */     return new Intersection(operands);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static InjectionPoint or(InjectionPoint... operands) {
/* 429 */     return new Union(operands);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static InjectionPoint after(InjectionPoint point) {
/* 440 */     return new Shift(point, 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static InjectionPoint before(InjectionPoint point) {
/* 451 */     return new Shift(point, -1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static InjectionPoint shift(InjectionPoint point, int count) {
/* 463 */     return new Shift(point, count);
/*     */   }
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
/*     */   public static List<InjectionPoint> parse(IInjectionPointContext owner, List<AnnotationNode> ats) {
/* 477 */     return parse(owner.getContext(), owner.getMethod(), owner.getAnnotation(), ats);
/*     */   }
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
/*     */   public static List<InjectionPoint> parse(IMixinContext context, MethodNode method, AnnotationNode parent, List<AnnotationNode> ats) {
/* 493 */     ImmutableList.Builder<InjectionPoint> injectionPoints = ImmutableList.builder();
/* 494 */     for (AnnotationNode at : ats) {
/* 495 */       InjectionPoint injectionPoint = parse(context, method, parent, at);
/* 496 */       if (injectionPoint != null) {
/* 497 */         injectionPoints.add(injectionPoint);
/*     */       }
/*     */     } 
/* 500 */     return (List<InjectionPoint>)injectionPoints.build();
/*     */   }
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
/*     */   public static InjectionPoint parse(IInjectionPointContext owner, At at) {
/* 513 */     return parse(owner.getContext(), owner.getMethod(), owner.getAnnotation(), at.value(), at.shift(), at.by(), 
/* 514 */         Arrays.asList(at.args()), at.target(), at.slice(), at.ordinal(), at.opcode(), at.id());
/*     */   }
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
/*     */   public static InjectionPoint parse(IMixinContext context, MethodNode method, AnnotationNode parent, At at) {
/* 529 */     return parse(context, method, parent, at.value(), at.shift(), at.by(), Arrays.asList(at.args()), at.target(), at.slice(), at
/* 530 */         .ordinal(), at.opcode(), at.id());
/*     */   }
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
/*     */   public static InjectionPoint parse(IInjectionPointContext owner, AnnotationNode node) {
/* 544 */     return parse(owner.getContext(), owner.getMethod(), owner.getAnnotation(), node);
/*     */   }
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
/*     */   public static InjectionPoint parse(IMixinContext context, MethodNode method, AnnotationNode parent, AnnotationNode node) {
/*     */     ImmutableList immutableList;
/* 560 */     String at = (String)Annotations.getValue(node, "value");
/* 561 */     List<String> args = (List<String>)Annotations.getValue(node, "args");
/* 562 */     String target = (String)Annotations.getValue(node, "target", "");
/* 563 */     String slice = (String)Annotations.getValue(node, "slice", "");
/* 564 */     At.Shift shift = (At.Shift)Annotations.getValue(node, "shift", At.Shift.class, At.Shift.NONE);
/* 565 */     int by = ((Integer)Annotations.getValue(node, "by", Integer.valueOf(0))).intValue();
/* 566 */     int ordinal = ((Integer)Annotations.getValue(node, "ordinal", Integer.valueOf(-1))).intValue();
/* 567 */     int opcode = ((Integer)Annotations.getValue(node, "opcode", Integer.valueOf(0))).intValue();
/* 568 */     String id = (String)Annotations.getValue(node, "id");
/*     */     
/* 570 */     if (args == null) {
/* 571 */       immutableList = ImmutableList.of();
/*     */     }
/*     */     
/* 574 */     return parse(context, method, parent, at, shift, by, (List<String>)immutableList, target, slice, ordinal, opcode, id);
/*     */   }
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
/*     */   public static InjectionPoint parse(IMixinContext context, MethodNode method, AnnotationNode parent, String at, At.Shift shift, int by, List<String> args, String target, String slice, int ordinal, int opcode, String id) {
/* 599 */     InjectionPointData data = new InjectionPointData(context, method, parent, at, args, target, slice, ordinal, opcode, id);
/* 600 */     Class<? extends InjectionPoint> ipClass = findClass(context, data);
/* 601 */     InjectionPoint point = create(context, data, ipClass);
/* 602 */     return shift(context, method, parent, point, shift, by);
/*     */   }
/*     */ 
/*     */   
/*     */   private static Class<? extends InjectionPoint> findClass(IMixinContext context, InjectionPointData data) {
/* 607 */     String type = data.getType();
/* 608 */     Class<? extends InjectionPoint> ipClass = types.get(type);
/* 609 */     if (ipClass == null) {
/* 610 */       if (type.matches("^([A-Za-z_][A-Za-z0-9_]*\\.)+[A-Za-z_][A-Za-z0-9_]*$")) {
/*     */         try {
/* 612 */           ipClass = (Class)Class.forName(type);
/* 613 */           types.put(type, ipClass);
/* 614 */         } catch (Exception ex) {
/* 615 */           throw new InvalidInjectionException(context, data + " could not be loaded or is not a valid InjectionPoint", ex);
/*     */         } 
/*     */       } else {
/* 618 */         throw new InvalidInjectionException(context, data + " is not a valid injection point specifier");
/*     */       } 
/*     */     }
/* 621 */     return ipClass;
/*     */   }
/*     */   
/*     */   private static InjectionPoint create(IMixinContext context, InjectionPointData data, Class<? extends InjectionPoint> ipClass) {
/* 625 */     Constructor<? extends InjectionPoint> ipCtor = null;
/*     */     try {
/* 627 */       ipCtor = ipClass.getDeclaredConstructor(new Class[] { InjectionPointData.class });
/* 628 */       ipCtor.setAccessible(true);
/* 629 */     } catch (NoSuchMethodException ex) {
/* 630 */       throw new InvalidInjectionException(context, ipClass.getName() + " must contain a constructor which accepts an InjectionPointData", ex);
/*     */     } 
/*     */     
/* 633 */     InjectionPoint point = null;
/*     */     try {
/* 635 */       point = ipCtor.newInstance(new Object[] { data });
/* 636 */     } catch (Exception ex) {
/* 637 */       throw new InvalidInjectionException(context, "Error whilst instancing injection point " + ipClass.getName() + " for " + data.getAt(), ex);
/*     */     } 
/*     */     
/* 640 */     return point;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static InjectionPoint shift(IMixinContext context, MethodNode method, AnnotationNode parent, InjectionPoint point, At.Shift shift, int by) {
/* 646 */     if (point != null) {
/* 647 */       if (shift == At.Shift.BEFORE)
/* 648 */         return before(point); 
/* 649 */       if (shift == At.Shift.AFTER)
/* 650 */         return after(point); 
/* 651 */       if (shift == At.Shift.BY) {
/* 652 */         validateByValue(context, method, parent, point, by);
/* 653 */         return shift(point, by);
/*     */       } 
/*     */     } 
/*     */     
/* 657 */     return point;
/*     */   }
/*     */   
/*     */   private static void validateByValue(IMixinContext context, MethodNode method, AnnotationNode parent, InjectionPoint point, int by) {
/* 661 */     MixinEnvironment env = context.getMixin().getConfig().getEnvironment();
/* 662 */     ShiftByViolationBehaviour err = (ShiftByViolationBehaviour)env.getOption(MixinEnvironment.Option.SHIFT_BY_VIOLATION_BEHAVIOUR, ShiftByViolationBehaviour.WARN);
/* 663 */     if (err == ShiftByViolationBehaviour.IGNORE) {
/*     */       return;
/*     */     }
/*     */     
/* 667 */     String limitBreached = "the maximum allowed value: ";
/* 668 */     String advice = "Increase the value of maxShiftBy to suppress this warning.";
/* 669 */     int allowed = 0;
/* 670 */     if (context instanceof MixinTargetContext) {
/* 671 */       allowed = ((MixinTargetContext)context).getMaxShiftByValue();
/*     */     }
/*     */     
/* 674 */     if (by <= allowed) {
/*     */       return;
/*     */     }
/*     */     
/* 678 */     if (by > 5) {
/* 679 */       limitBreached = "MAX_ALLOWED_SHIFT_BY=";
/* 680 */       advice = "You must use an alternate query or a custom injection point.";
/* 681 */       allowed = 5;
/*     */     } 
/*     */     
/* 684 */     String message = String.format("@%s(%s) Shift.BY=%d on %s::%s exceeds %s%d. %s", new Object[] { Bytecode.getSimpleName(parent), point, 
/* 685 */           Integer.valueOf(by), context, method.name, limitBreached, Integer.valueOf(allowed), advice });
/*     */     
/* 687 */     if (err == ShiftByViolationBehaviour.WARN && allowed < 5) {
/* 688 */       LogManager.getLogger("mixin").warn(message);
/*     */       
/*     */       return;
/*     */     } 
/* 692 */     throw new InvalidInjectionException(context, message);
/*     */   }
/*     */   
/*     */   protected String getAtCode() {
/* 696 */     AtCode code = getClass().<AtCode>getAnnotation(AtCode.class);
/* 697 */     return (code == null) ? getClass().getName() : code.value();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void register(Class<? extends InjectionPoint> type) {
/* 707 */     AtCode code = type.<AtCode>getAnnotation(AtCode.class);
/* 708 */     if (code == null) {
/* 709 */       throw new IllegalArgumentException("Injection point class " + type + " is not annotated with @AtCode");
/*     */     }
/*     */     
/* 712 */     Class<? extends InjectionPoint> existing = types.get(code.value());
/* 713 */     if (existing != null && !existing.equals(type)) {
/* 714 */       LogManager.getLogger("mixin").debug("Overriding InjectionPoint {} with {} (previously {})", new Object[] { code.value(), type.getName(), existing
/* 715 */             .getName() });
/*     */     }
/*     */     
/* 718 */     types.put(code.value(), type);
/*     */   }
/*     */   
/*     */   public abstract boolean find(String paramString, InsnList paramInsnList, Collection<AbstractInsnNode> paramCollection);
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\mixin\injection\InjectionPoint.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */