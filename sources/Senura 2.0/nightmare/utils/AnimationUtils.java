/*    */ package nightmare.utils;
/*    */ 
/*    */ 
/*    */ public class AnimationUtils
/*    */ {
/*    */   public static double delta;
/*    */   
/*    */   public static float setAnimation(float animation, float finalState, float speed) {
/*  9 */     float add = (float)(delta * (speed / 1000.0F));
/*    */     
/* 11 */     if (animation < finalState) {
/* 12 */       if (animation + add < finalState) {
/* 13 */         animation += add;
/*    */       } else {
/* 15 */         animation = finalState;
/*    */       } 
/* 17 */     } else if (animation - add > finalState) {
/* 18 */       animation -= add;
/*    */     } else {
/* 20 */       animation = finalState;
/*    */     } 
/* 22 */     return animation;
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmar\\utils\AnimationUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */