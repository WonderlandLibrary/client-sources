/*    */ package net.minecraft.client.renderer;
/*    */ 
/*    */ import com.google.common.primitives.Floats;
/*    */ import java.util.Comparator;
/*    */ 
/*    */ class WorldRenderer$1
/*    */   implements Comparator
/*    */ {
/*    */   final float[] field_181659_a;
/*    */   final WorldRenderer field_181660_b;
/*    */   
/*    */   WorldRenderer$1(WorldRenderer p_i46500_1_, float[] p_i46500_2_) {
/* 13 */     this.field_181660_b = p_i46500_1_;
/* 14 */     this.field_181659_a = p_i46500_2_;
/*    */   }
/*    */ 
/*    */   
/*    */   public int compare(Integer p_compare_1_, Integer p_compare_2_) {
/* 19 */     return Floats.compare(this.field_181659_a[p_compare_2_.intValue()], this.field_181659_a[p_compare_1_.intValue()]);
/*    */   }
/*    */ 
/*    */   
/*    */   public int compare(Object p_compare_1_, Object p_compare_2_) {
/* 24 */     return compare((Integer)p_compare_1_, (Integer)p_compare_2_);
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\renderer\WorldRenderer$1.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */