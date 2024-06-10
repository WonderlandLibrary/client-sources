/*  1:   */ package net.minecraft.client.util;
/*  2:   */ 
/*  3:   */ import java.util.Comparator;
/*  4:   */ 
/*  5:   */ public class QuadComparator
/*  6:   */   implements Comparator
/*  7:   */ {
/*  8:   */   private float field_147630_a;
/*  9:   */   private float field_147628_b;
/* 10:   */   private float field_147629_c;
/* 11:   */   private int[] field_147627_d;
/* 12:   */   private static final String __OBFID = "CL_00000958";
/* 13:   */   
/* 14:   */   public QuadComparator(int[] p_i45077_1_, float p_i45077_2_, float p_i45077_3_, float p_i45077_4_)
/* 15:   */   {
/* 16:15 */     this.field_147627_d = p_i45077_1_;
/* 17:16 */     this.field_147630_a = p_i45077_2_;
/* 18:17 */     this.field_147628_b = p_i45077_3_;
/* 19:18 */     this.field_147629_c = p_i45077_4_;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public int compare(Integer p_147626_1_, Integer p_147626_2_)
/* 23:   */   {
/* 24:23 */     float var3 = Float.intBitsToFloat(this.field_147627_d[p_147626_1_.intValue()]) - this.field_147630_a;
/* 25:24 */     float var4 = Float.intBitsToFloat(this.field_147627_d[(p_147626_1_.intValue() + 1)]) - this.field_147628_b;
/* 26:25 */     float var5 = Float.intBitsToFloat(this.field_147627_d[(p_147626_1_.intValue() + 2)]) - this.field_147629_c;
/* 27:26 */     float var6 = Float.intBitsToFloat(this.field_147627_d[(p_147626_1_.intValue() + 8)]) - this.field_147630_a;
/* 28:27 */     float var7 = Float.intBitsToFloat(this.field_147627_d[(p_147626_1_.intValue() + 9)]) - this.field_147628_b;
/* 29:28 */     float var8 = Float.intBitsToFloat(this.field_147627_d[(p_147626_1_.intValue() + 10)]) - this.field_147629_c;
/* 30:29 */     float var9 = Float.intBitsToFloat(this.field_147627_d[(p_147626_1_.intValue() + 16)]) - this.field_147630_a;
/* 31:30 */     float var10 = Float.intBitsToFloat(this.field_147627_d[(p_147626_1_.intValue() + 17)]) - this.field_147628_b;
/* 32:31 */     float var11 = Float.intBitsToFloat(this.field_147627_d[(p_147626_1_.intValue() + 18)]) - this.field_147629_c;
/* 33:32 */     float var12 = Float.intBitsToFloat(this.field_147627_d[(p_147626_1_.intValue() + 24)]) - this.field_147630_a;
/* 34:33 */     float var13 = Float.intBitsToFloat(this.field_147627_d[(p_147626_1_.intValue() + 25)]) - this.field_147628_b;
/* 35:34 */     float var14 = Float.intBitsToFloat(this.field_147627_d[(p_147626_1_.intValue() + 26)]) - this.field_147629_c;
/* 36:35 */     float var15 = Float.intBitsToFloat(this.field_147627_d[p_147626_2_.intValue()]) - this.field_147630_a;
/* 37:36 */     float var16 = Float.intBitsToFloat(this.field_147627_d[(p_147626_2_.intValue() + 1)]) - this.field_147628_b;
/* 38:37 */     float var17 = Float.intBitsToFloat(this.field_147627_d[(p_147626_2_.intValue() + 2)]) - this.field_147629_c;
/* 39:38 */     float var18 = Float.intBitsToFloat(this.field_147627_d[(p_147626_2_.intValue() + 8)]) - this.field_147630_a;
/* 40:39 */     float var19 = Float.intBitsToFloat(this.field_147627_d[(p_147626_2_.intValue() + 9)]) - this.field_147628_b;
/* 41:40 */     float var20 = Float.intBitsToFloat(this.field_147627_d[(p_147626_2_.intValue() + 10)]) - this.field_147629_c;
/* 42:41 */     float var21 = Float.intBitsToFloat(this.field_147627_d[(p_147626_2_.intValue() + 16)]) - this.field_147630_a;
/* 43:42 */     float var22 = Float.intBitsToFloat(this.field_147627_d[(p_147626_2_.intValue() + 17)]) - this.field_147628_b;
/* 44:43 */     float var23 = Float.intBitsToFloat(this.field_147627_d[(p_147626_2_.intValue() + 18)]) - this.field_147629_c;
/* 45:44 */     float var24 = Float.intBitsToFloat(this.field_147627_d[(p_147626_2_.intValue() + 24)]) - this.field_147630_a;
/* 46:45 */     float var25 = Float.intBitsToFloat(this.field_147627_d[(p_147626_2_.intValue() + 25)]) - this.field_147628_b;
/* 47:46 */     float var26 = Float.intBitsToFloat(this.field_147627_d[(p_147626_2_.intValue() + 26)]) - this.field_147629_c;
/* 48:47 */     float var27 = (var3 + var6 + var9 + var12) * 0.25F;
/* 49:48 */     float var28 = (var4 + var7 + var10 + var13) * 0.25F;
/* 50:49 */     float var29 = (var5 + var8 + var11 + var14) * 0.25F;
/* 51:50 */     float var30 = (var15 + var18 + var21 + var24) * 0.25F;
/* 52:51 */     float var31 = (var16 + var19 + var22 + var25) * 0.25F;
/* 53:52 */     float var32 = (var17 + var20 + var23 + var26) * 0.25F;
/* 54:53 */     float var33 = var27 * var27 + var28 * var28 + var29 * var29;
/* 55:54 */     float var34 = var30 * var30 + var31 * var31 + var32 * var32;
/* 56:55 */     return Float.compare(var34, var33);
/* 57:   */   }
/* 58:   */   
/* 59:   */   public int compare(Object par1Obj, Object par2Obj)
/* 60:   */   {
/* 61:60 */     return compare((Integer)par1Obj, (Integer)par2Obj);
/* 62:   */   }
/* 63:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.util.QuadComparator
 * JD-Core Version:    0.7.0.1
 */