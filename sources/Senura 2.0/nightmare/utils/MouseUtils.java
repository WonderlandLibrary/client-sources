/*   */ package nightmare.utils;
/*   */ 
/*   */ public class MouseUtils {
/*   */   public static boolean isInside(int mouseX, int mouseY, double x, double y, double x2, double y2) {
/* 5 */     return (mouseX > x && mouseX < x2 && mouseY > y && mouseY < y2);
/*   */   }
/*   */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmar\\utils\MouseUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */