/*    */ package nightmare.gui.window;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import nightmare.gui.window.impl.Inventory;
/*    */ import nightmare.gui.window.impl.TargetHUD;
/*    */ 
/*    */ 
/*    */ public class WindowManager
/*    */ {
/* 10 */   private ArrayList<Window> windows = new ArrayList<>();
/*    */   
/*    */   public WindowManager() {
/* 13 */     this.windows.add(new Inventory());
/* 14 */     this.windows.add(new TargetHUD());
/*    */   }
/*    */   
/*    */   public ArrayList<Window> getWindows() {
/* 18 */     return this.windows;
/*    */   }
/*    */   
/*    */   public Window getWindowByName(String name) {
/* 22 */     return this.windows.stream().filter(module -> module.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\gui\window\WindowManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */