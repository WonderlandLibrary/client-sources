/*     */ package net.minecraft.util;
/*     */ import com.google.common.base.Predicate;
/*     */ import com.google.common.collect.Iterators;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ public enum EnumFacing implements IStringSerializable { private final int index; private final int opposite; private final int horizontalIndex; private final String name; private final Axis axis; private final AxisDirection axisDirection; private final Vec3i directionVec; public static final EnumFacing[] VALUES; private static final EnumFacing[] HORIZONTALS; private static final Map NAME_LOOKUP; private static final EnumFacing[] $VALUES; private static final String __OBFID = "CL_00001201"; EnumFacing(String p_i13_3_, int p_i13_4_, int p_i13_5_, int p_i13_6_, int p_i13_7_, String p_i13_8_, AxisDirection p_i13_9_, Axis p_i13_10_, Vec3i p_i13_11_) { this.index = p_i13_5_; this.horizontalIndex = p_i13_7_; this.opposite = p_i13_6_; this.name = p_i13_8_; this.axis = p_i13_10_; this.axisDirection = p_i13_9_; this.directionVec = p_i13_11_; } public int getIndex() { return this.index; } public int getHorizontalIndex() { return this.horizontalIndex; } public AxisDirection getAxisDirection() { return this.axisDirection; } public EnumFacing getOpposite() { return getFront(this.opposite); } public EnumFacing rotateAround(Axis axis) { switch (EnumFacing$1.field_179515_a[axis.ordinal()]) { case 1: if (this != WEST && this != EAST)
/*     */           return rotateX();  return this;case 2: if (this != UP && this != DOWN)
/*     */           return rotateY();  return this;case 3: if (this != NORTH && this != SOUTH)
/*     */           return rotateZ();  return this; }  throw new IllegalStateException("Unable to get CW facing for axis " + axis); } public EnumFacing rotateY() { switch (EnumFacing$1.field_179513_b[ordinal()]) { case 1: return EAST;case 2: return SOUTH;case 3: return WEST;
/*  12 */       case 4: return NORTH; }  throw new IllegalStateException("Unable to get Y-rotated facing of " + this); } DOWN("DOWN", 0, 0, 1, -1, "down", AxisDirection.NEGATIVE, Axis.Y, new Vec3i(0, -1, 0)),
/*  13 */   UP("UP", 1, 1, 0, -1, "up", AxisDirection.POSITIVE, Axis.Y, new Vec3i(0, 1, 0)),
/*  14 */   NORTH("NORTH", 2, 2, 3, 2, "north", AxisDirection.NEGATIVE, Axis.Z, new Vec3i(0, 0, -1)),
/*  15 */   SOUTH("SOUTH", 3, 3, 2, 0, "south", AxisDirection.POSITIVE, Axis.Z, new Vec3i(0, 0, 1)),
/*  16 */   WEST("WEST", 4, 4, 5, 1, "west", AxisDirection.NEGATIVE, Axis.X, new Vec3i(-1, 0, 0)),
/*  17 */   EAST("EAST", 5, 5, 4, 3, "east", AxisDirection.POSITIVE, Axis.X, new Vec3i(1, 0, 0));
/*     */   private EnumFacing rotateX() { switch (EnumFacing$1.field_179513_b[ordinal()]) { case 1: return DOWN;
/*     */       default: throw new IllegalStateException("Unable to get X-rotated facing of " + this);
/*     */       case 3: return UP;
/*     */       case 5: return NORTH;
/*     */       case 6: break; }  return SOUTH; }
/*     */   private EnumFacing rotateZ() { switch (EnumFacing$1.field_179513_b[ordinal()]) { case 2: return DOWN;
/*     */       default: throw new IllegalStateException("Unable to get Z-rotated facing of " + this);
/*     */       case 4: return UP;
/*     */       case 5: return EAST;
/*     */       case 6: break; }  return WEST; }
/*     */   public EnumFacing rotateYCCW() { switch (EnumFacing$1.field_179513_b[ordinal()]) { case 1: return WEST;
/*     */       case 2: return NORTH;
/*     */       case 3: return EAST;
/*     */       case 4:
/*     */         return SOUTH; }  throw new IllegalStateException("Unable to get CCW facing of " + this); }
/*     */   public int getFrontOffsetX() { return (this.axis == Axis.X) ? this.axisDirection.getOffset() : 0; }
/*     */   public int getFrontOffsetY() { return (this.axis == Axis.Y) ? this.axisDirection.getOffset() : 0; }
/*  35 */   public int getFrontOffsetZ() { return (this.axis == Axis.Z) ? this.axisDirection.getOffset() : 0; } static { VALUES = new EnumFacing[6];
/*     */ 
/*     */     
/*  38 */     HORIZONTALS = new EnumFacing[4];
/*  39 */     NAME_LOOKUP = Maps.newHashMap();
/*  40 */     $VALUES = new EnumFacing[] { DOWN, UP, NORTH, SOUTH, WEST, EAST };
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
/*     */     byte b;
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
/*     */     int i;
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
/*     */     EnumFacing[] arrayOfEnumFacing;
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
/* 347 */     for (i = (arrayOfEnumFacing = values()).length, b = 0; b < i; ) { EnumFacing enumfacing = arrayOfEnumFacing[b];
/*     */       
/* 349 */       VALUES[enumfacing.index] = enumfacing;
/*     */       
/* 351 */       if (enumfacing.getAxis().isHorizontal())
/*     */       {
/* 353 */         HORIZONTALS[enumfacing.horizontalIndex] = enumfacing;
/*     */       }
/*     */       
/* 356 */       NAME_LOOKUP.put(enumfacing.getName2().toLowerCase(), enumfacing); b++; }
/*     */      } public String getName2() { return this.name; }
/*     */   public Axis getAxis() { return this.axis; }
/*     */   public static EnumFacing byName(String name) { return (name == null) ? null : (EnumFacing)NAME_LOOKUP.get(name.toLowerCase()); }
/*     */   public static EnumFacing getFront(int index) { return VALUES[MathHelper.abs_int(index % VALUES.length)]; }
/*     */   public static EnumFacing getHorizontal(int p_176731_0_) { return HORIZONTALS[MathHelper.abs_int(p_176731_0_ % HORIZONTALS.length)]; }
/*     */   public static EnumFacing fromAngle(double angle) { return getHorizontal(MathHelper.floor_double(angle / 90.0D + 0.5D) & 0x3); }
/*     */   public static EnumFacing random(Random rand) { return values()[rand.nextInt((values()).length)]; }
/*     */   public static EnumFacing getFacingFromVector(float p_176737_0_, float p_176737_1_, float p_176737_2_) { EnumFacing enumfacing = NORTH; float f = Float.MIN_VALUE; byte b; int i; EnumFacing[] arrayOfEnumFacing; for (i = (arrayOfEnumFacing = values()).length, b = 0; b < i; ) {
/*     */       EnumFacing enumfacing1 = arrayOfEnumFacing[b]; float f1 = p_176737_0_ * enumfacing1.directionVec.getX() + p_176737_1_ * enumfacing1.directionVec.getY() + p_176737_2_ * enumfacing1.directionVec.getZ(); if (f1 > f) {
/*     */         f = f1; enumfacing = enumfacing1;
/*     */       }  b++;
/*     */     } 
/*     */     return enumfacing; }
/*     */   public String toString() { return this.name; }
/*     */   public String getName() { return this.name; }
/*     */   public static EnumFacing func_181076_a(AxisDirection p_181076_0_, Axis p_181076_1_) { byte b;
/*     */     int i;
/*     */     EnumFacing[] arrayOfEnumFacing;
/*     */     for (i = (arrayOfEnumFacing = values()).length, b = 0; b < i; ) {
/*     */       EnumFacing enumfacing = arrayOfEnumFacing[b];
/*     */       if (enumfacing.getAxisDirection() == p_181076_0_ && enumfacing.getAxis() == p_181076_1_)
/*     */         return enumfacing; 
/*     */       b++;
/*     */     } 
/*     */     throw new IllegalArgumentException("No such direction: " + p_181076_0_ + " " + p_181076_1_); }
/*     */   public Vec3i getDirectionVec() { return this.directionVec; }
/* 383 */   static final class EnumFacing$1 { static final int[] field_179515_a; static final int[] field_179513_b = new int[(EnumFacing.values()).length]; static final int[] field_179514_c = new int[(EnumFacing.Plane.values()).length]; private static final String __OBFID = "CL_00002322";
/*     */     static {
/*     */       try {
/* 386 */         field_179513_b[EnumFacing.NORTH.ordinal()] = 1;
/*     */       }
/* 388 */       catch (NoSuchFieldError noSuchFieldError) {}
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 394 */         field_179513_b[EnumFacing.EAST.ordinal()] = 2;
/*     */       }
/* 396 */       catch (NoSuchFieldError noSuchFieldError) {}
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 402 */         field_179513_b[EnumFacing.SOUTH.ordinal()] = 3;
/*     */       }
/* 404 */       catch (NoSuchFieldError noSuchFieldError) {}
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 410 */         field_179513_b[EnumFacing.WEST.ordinal()] = 4;
/*     */       }
/* 412 */       catch (NoSuchFieldError noSuchFieldError) {}
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 418 */         field_179513_b[EnumFacing.UP.ordinal()] = 5;
/*     */       }
/* 420 */       catch (NoSuchFieldError noSuchFieldError) {}
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 426 */         field_179513_b[EnumFacing.DOWN.ordinal()] = 6;
/*     */       }
/* 428 */       catch (NoSuchFieldError noSuchFieldError) {}
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 433 */       field_179515_a = new int[(EnumFacing.Axis.values()).length];
/*     */       
/*     */       try {
/* 436 */         field_179515_a[EnumFacing.Axis.X.ordinal()] = 1;
/*     */       }
/* 438 */       catch (NoSuchFieldError noSuchFieldError) {}
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 444 */         field_179515_a[EnumFacing.Axis.Y.ordinal()] = 2;
/*     */       }
/* 446 */       catch (NoSuchFieldError noSuchFieldError) {}
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 452 */         field_179515_a[EnumFacing.Axis.Z.ordinal()] = 3;
/*     */       }
/* 454 */       catch (NoSuchFieldError noSuchFieldError) {}
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   public enum Axis
/*     */     implements Predicate, IStringSerializable
/*     */   {
/* 462 */     X("X", 0, "x", EnumFacing.Plane.HORIZONTAL),
/* 463 */     Y("Y", 1, "y", EnumFacing.Plane.VERTICAL),
/* 464 */     Z("Z", 2, "z", EnumFacing.Plane.HORIZONTAL);
/*     */     
/* 466 */     private static final Map NAME_LOOKUP = Maps.newHashMap();
/*     */     private final String name;
/*     */     private final EnumFacing.Plane plane;
/* 469 */     private static final Axis[] $VALUES = new Axis[] { X, Y, Z };
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
/*     */     private static final String __OBFID = "CL_00002321";
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
/*     */     static {
/*     */       byte b;
/*     */       int i;
/*     */       Axis[] arrayOfAxis;
/* 524 */       for (i = (arrayOfAxis = values()).length, b = 0; b < i; ) { Axis enumfacing$axis = arrayOfAxis[b];
/*     */         
/* 526 */         NAME_LOOKUP.put(enumfacing$axis.getName2().toLowerCase(), enumfacing$axis); b++; } 
/*     */     } Axis(String p_i10_3_, int p_i10_4_, String p_i10_5_, EnumFacing.Plane p_i10_6_) { this.name = p_i10_5_; this.plane = p_i10_6_; } public static Axis byName(String name) { return (name == null) ? null : (Axis)NAME_LOOKUP.get(name.toLowerCase()); } public String getName2() { return this.name; } public boolean isVertical() { return (this.plane == EnumFacing.Plane.VERTICAL); } public boolean isHorizontal() { return (this.plane == EnumFacing.Plane.HORIZONTAL); } public String toString() { return this.name; }
/*     */     public boolean apply(EnumFacing p_apply_1_) { return (p_apply_1_ != null && p_apply_1_.getAxis() == this); }
/*     */     public EnumFacing.Plane getPlane() { return this.plane; }
/*     */     public String getName() { return this.name; }
/*     */     public boolean apply(Object p_apply_1_) { return apply((EnumFacing)p_apply_1_); } }
/* 532 */   public enum AxisDirection { POSITIVE("POSITIVE", 0, 1, "Towards positive"),
/* 533 */     NEGATIVE("NEGATIVE", 1, -1, "Towards negative");
/*     */     
/*     */     private final int offset;
/*     */     private final String description;
/* 537 */     private static final AxisDirection[] $VALUES = new AxisDirection[] { POSITIVE, NEGATIVE }; private static final String __OBFID = "CL_00002320";
/*     */     static {
/*     */     
/*     */     }
/*     */     AxisDirection(String p_i11_3_, int p_i11_4_, int p_i11_5_, String p_i11_6_) {
/* 542 */       this.offset = p_i11_5_;
/* 543 */       this.description = p_i11_6_;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getOffset() {
/* 548 */       return this.offset;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 553 */       return this.description;
/*     */     } }
/*     */ 
/*     */   
/*     */   public enum Plane implements Predicate, Iterable {
/* 558 */     HORIZONTAL("HORIZONTAL", 0),
/* 559 */     VERTICAL("VERTICAL", 1);
/*     */     
/* 561 */     private static final Plane[] $VALUES = new Plane[] { HORIZONTAL, VERTICAL };
/*     */     
/*     */     private static final String __OBFID = "CL_00002319";
/*     */     
/*     */     static {
/*     */     
/*     */     }
/*     */     
/*     */     public EnumFacing[] facings() {
/* 570 */       switch (EnumFacing.EnumFacing$1.field_179514_c[ordinal()]) {
/*     */         
/*     */         case 1:
/* 573 */           return new EnumFacing[] { EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.SOUTH, EnumFacing.WEST };
/*     */         case 2:
/* 575 */           return new EnumFacing[] { EnumFacing.UP, EnumFacing.DOWN };
/*     */       } 
/* 577 */       throw new Error("Someone's been tampering with the universe!");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public EnumFacing random(Random rand) {
/* 583 */       EnumFacing[] aenumfacing = facings();
/* 584 */       return aenumfacing[rand.nextInt(aenumfacing.length)];
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean apply(EnumFacing p_apply_1_) {
/* 589 */       return (p_apply_1_ != null && p_apply_1_.getAxis().getPlane() == this);
/*     */     }
/*     */ 
/*     */     
/*     */     public Iterator iterator() {
/* 594 */       return (Iterator)Iterators.forArray((Object[])facings());
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean apply(Object p_apply_1_) {
/* 599 */       return apply((EnumFacing)p_apply_1_);
/*     */     }
/*     */   } }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraf\\util\EnumFacing.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */