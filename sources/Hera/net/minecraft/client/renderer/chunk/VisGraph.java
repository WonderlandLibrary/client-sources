/*     */ package net.minecraft.client.renderer.chunk;
/*     */ 
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.BitSet;
/*     */ import java.util.EnumSet;
/*     */ import java.util.Set;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import optfine.IntegerCache;
/*     */ 
/*     */ 
/*     */ public class VisGraph
/*     */ {
/*  14 */   private static final int field_178616_a = (int)Math.pow(16.0D, 0.0D);
/*  15 */   private static final int field_178614_b = (int)Math.pow(16.0D, 1.0D);
/*  16 */   private static final int field_178615_c = (int)Math.pow(16.0D, 2.0D);
/*  17 */   private final BitSet field_178612_d = new BitSet(4096);
/*  18 */   private static final int[] field_178613_e = new int[1352];
/*  19 */   private int field_178611_f = 4096;
/*     */   
/*     */   private static final String __OBFID = "CL_00002450";
/*     */   
/*     */   public void func_178606_a(BlockPos pos) {
/*  24 */     this.field_178612_d.set(getIndex(pos), true);
/*  25 */     this.field_178611_f--;
/*     */   }
/*     */ 
/*     */   
/*     */   private static int getIndex(BlockPos pos) {
/*  30 */     return getIndex(pos.getX() & 0xF, pos.getY() & 0xF, pos.getZ() & 0xF);
/*     */   }
/*     */ 
/*     */   
/*     */   private static int getIndex(int x, int y, int z) {
/*  35 */     return x << 0 | y << 8 | z << 4;
/*     */   }
/*     */ 
/*     */   
/*     */   public SetVisibility computeVisibility() {
/*  40 */     SetVisibility setvisibility = new SetVisibility();
/*     */     
/*  42 */     if (4096 - this.field_178611_f < 256) {
/*     */       
/*  44 */       setvisibility.setAllVisible(true);
/*     */     }
/*  46 */     else if (this.field_178611_f == 0) {
/*     */       
/*  48 */       setvisibility.setAllVisible(false);
/*     */     } else {
/*     */       byte b; int i;
/*     */       int[] arrayOfInt;
/*  52 */       for (i = (arrayOfInt = field_178613_e).length, b = 0; b < i; ) { int j = arrayOfInt[b];
/*     */         
/*  54 */         if (!this.field_178612_d.get(j))
/*     */         {
/*  56 */           setvisibility.setManyVisible(func_178604_a(j));
/*     */         }
/*     */         b++; }
/*     */     
/*     */     } 
/*  61 */     return setvisibility;
/*     */   }
/*     */ 
/*     */   
/*     */   public Set func_178609_b(BlockPos pos) {
/*  66 */     return func_178604_a(getIndex(pos));
/*     */   }
/*     */ 
/*     */   
/*     */   private Set func_178604_a(int p_178604_1_) {
/*  71 */     EnumSet<EnumFacing> enumset = EnumSet.noneOf(EnumFacing.class);
/*  72 */     ArrayDeque<Integer> arraydeque = new ArrayDeque(384);
/*  73 */     arraydeque.add(IntegerCache.valueOf(p_178604_1_));
/*  74 */     this.field_178612_d.set(p_178604_1_, true);
/*     */     
/*  76 */     while (!arraydeque.isEmpty()) {
/*     */       
/*  78 */       int i = ((Integer)arraydeque.poll()).intValue();
/*  79 */       func_178610_a(i, enumset); byte b; int j;
/*     */       EnumFacing[] arrayOfEnumFacing;
/*  81 */       for (j = (arrayOfEnumFacing = EnumFacing.VALUES).length, b = 0; b < j; ) { EnumFacing enumfacing = arrayOfEnumFacing[b];
/*     */         
/*  83 */         int k = func_178603_a(i, enumfacing);
/*     */         
/*  85 */         if (k >= 0 && !this.field_178612_d.get(k)) {
/*     */           
/*  87 */           this.field_178612_d.set(k, true);
/*  88 */           arraydeque.add(IntegerCache.valueOf(k));
/*     */         } 
/*     */         b++; }
/*     */     
/*     */     } 
/*  93 */     return enumset;
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_178610_a(int p_178610_1_, Set<EnumFacing> p_178610_2_) {
/*  98 */     int i = p_178610_1_ >> 0 & 0xF;
/*     */     
/* 100 */     if (i == 0) {
/*     */       
/* 102 */       p_178610_2_.add(EnumFacing.WEST);
/*     */     }
/* 104 */     else if (i == 15) {
/*     */       
/* 106 */       p_178610_2_.add(EnumFacing.EAST);
/*     */     } 
/*     */     
/* 109 */     int j = p_178610_1_ >> 8 & 0xF;
/*     */     
/* 111 */     if (j == 0) {
/*     */       
/* 113 */       p_178610_2_.add(EnumFacing.DOWN);
/*     */     }
/* 115 */     else if (j == 15) {
/*     */       
/* 117 */       p_178610_2_.add(EnumFacing.UP);
/*     */     } 
/*     */     
/* 120 */     int k = p_178610_1_ >> 4 & 0xF;
/*     */     
/* 122 */     if (k == 0) {
/*     */       
/* 124 */       p_178610_2_.add(EnumFacing.NORTH);
/*     */     }
/* 126 */     else if (k == 15) {
/*     */       
/* 128 */       p_178610_2_.add(EnumFacing.SOUTH);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private int func_178603_a(int p_178603_1_, EnumFacing p_178603_2_) {
/* 134 */     switch (VisGraph$1.field_178617_a[p_178603_2_.ordinal()]) {
/*     */       
/*     */       case 1:
/* 137 */         if ((p_178603_1_ >> 8 & 0xF) == 0)
/*     */         {
/* 139 */           return -1;
/*     */         }
/*     */         
/* 142 */         return p_178603_1_ - field_178615_c;
/*     */       
/*     */       case 2:
/* 145 */         if ((p_178603_1_ >> 8 & 0xF) == 15)
/*     */         {
/* 147 */           return -1;
/*     */         }
/*     */         
/* 150 */         return p_178603_1_ + field_178615_c;
/*     */       
/*     */       case 3:
/* 153 */         if ((p_178603_1_ >> 4 & 0xF) == 0)
/*     */         {
/* 155 */           return -1;
/*     */         }
/*     */         
/* 158 */         return p_178603_1_ - field_178614_b;
/*     */       
/*     */       case 4:
/* 161 */         if ((p_178603_1_ >> 4 & 0xF) == 15)
/*     */         {
/* 163 */           return -1;
/*     */         }
/*     */         
/* 166 */         return p_178603_1_ + field_178614_b;
/*     */       
/*     */       case 5:
/* 169 */         if ((p_178603_1_ >> 0 & 0xF) == 0)
/*     */         {
/* 171 */           return -1;
/*     */         }
/*     */         
/* 174 */         return p_178603_1_ - field_178616_a;
/*     */       
/*     */       case 6:
/* 177 */         if ((p_178603_1_ >> 0 & 0xF) == 15)
/*     */         {
/* 179 */           return -1;
/*     */         }
/*     */         
/* 182 */         return p_178603_1_ + field_178616_a;
/*     */     } 
/*     */     
/* 185 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 191 */     boolean flag = false;
/* 192 */     boolean flag1 = true;
/* 193 */     int i = 0;
/*     */     
/* 195 */     for (int j = 0; j < 16; j++) {
/*     */       
/* 197 */       for (int k = 0; k < 16; k++) {
/*     */         
/* 199 */         for (int l = 0; l < 16; l++) {
/*     */           
/* 201 */           if (j == 0 || j == 15 || k == 0 || k == 15 || l == 0 || l == 15)
/*     */           {
/* 203 */             field_178613_e[i++] = getIndex(j, k, l);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   static final class VisGraph$1
/*     */   {
/* 212 */     static final int[] field_178617_a = new int[(EnumFacing.values()).length];
/*     */     
/*     */     private static final String __OBFID = "CL_00002449";
/*     */ 
/*     */     
/*     */     static {
/*     */       try {
/* 219 */         field_178617_a[EnumFacing.DOWN.ordinal()] = 1;
/*     */       }
/* 221 */       catch (NoSuchFieldError noSuchFieldError) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 228 */         field_178617_a[EnumFacing.UP.ordinal()] = 2;
/*     */       }
/* 230 */       catch (NoSuchFieldError noSuchFieldError) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 237 */         field_178617_a[EnumFacing.NORTH.ordinal()] = 3;
/*     */       }
/* 239 */       catch (NoSuchFieldError noSuchFieldError) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 246 */         field_178617_a[EnumFacing.SOUTH.ordinal()] = 4;
/*     */       }
/* 248 */       catch (NoSuchFieldError noSuchFieldError) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 255 */         field_178617_a[EnumFacing.WEST.ordinal()] = 5;
/*     */       }
/* 257 */       catch (NoSuchFieldError noSuchFieldError) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 264 */         field_178617_a[EnumFacing.EAST.ordinal()] = 6;
/*     */       }
/* 266 */       catch (NoSuchFieldError noSuchFieldError) {}
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\renderer\chunk\VisGraph.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */