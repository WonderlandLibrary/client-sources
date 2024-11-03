package vestige.util.render;

public interface IFont {
   void drawString(String var1, double var2, double var4, int var6, boolean var7);

   void drawString(String var1, double var2, double var4, int var6);

   default void drawStringWithShadow(String text, double x, double y, int color) {
      this.drawString(text, x, y, color, true);
   }

   double width(String var1);

   void drawCenteredString(String var1, double var2, double var4, int var6);

   default void drawRightString(String text, double x, double y, int color) {
      this.drawString(text, x - (double)((int)this.width(text)), y, color, false);
   }

   double height();

   void drawString(String var1, double var2, double var4, CenterMode var6, boolean var7, int var8);
}
