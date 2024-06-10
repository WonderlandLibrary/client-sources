/*    */ package nightmare.module.misc;
/*    */ 
/*    */ import nightmare.event.EventTarget;
/*    */ import nightmare.event.impl.EventText;
/*    */ import nightmare.module.Category;
/*    */ import nightmare.module.Module;
/*    */ 
/*    */ public class NameProtect
/*    */   extends Module {
/*    */   public NameProtect() {
/* 11 */     super("NameProtect", 0, Category.MISC);
/*    */   }
/*    */   
/*    */   @EventTarget
/*    */   public void onText(EventText event) {
/* 16 */     if (mc.field_71439_g != null && mc.field_71441_e != null)
/* 17 */       event.replace(mc.field_71439_g.func_70005_c_(), "You"); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\module\misc\NameProtect.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */