/*    */ package nightmare.module.render;
/*    */ 
/*    */ import nightmare.module.Category;
/*    */ import nightmare.module.Module;
/*    */ 
/*    */ public class Fullbright
/*    */   extends Module {
/*    */   public Fullbright() {
/*  9 */     super("Fullbright", 0, Category.RENDER);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onEnable() {
/* 14 */     super.onEnable();
/* 15 */     mc.field_71474_y.field_74333_Y = 10.0F;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onDisable() {
/* 20 */     mc.field_71474_y.field_74333_Y = 1.0F;
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\module\render\Fullbright.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */