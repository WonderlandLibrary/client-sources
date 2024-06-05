package cc.novoline.utils.fonts.api;

public interface FontRenderer {
   float drawString(CharSequence var1, double var2, double var4, int var6, boolean var7);

   String trimStringToWidth(CharSequence var1, int var2, boolean var3);

   int stringWidth(CharSequence var1);

   float charWidth(char var1);

   String getName();

   int getHeight();

   boolean isAntiAlias();

   boolean isFractionalMetrics();

   default float drawString(CharSequence var1, float var2, float var3, int var4) {
      return this.drawString(var1, (double)var2, (double)var3, var4, false);
   }

   default String trimStringToWidth(CharSequence var1, int var2) {
      return this.trimStringToWidth(var1, var2, false);
   }

   default float drawCenteredString(CharSequence var1, float var2, float var3, int var4, boolean var5) {
      return this.drawString(var1, (double)(var2 - (float)this.stringWidth(var1) / 2.0F), (double)var3, var4, var5);
   }

   default float drawCenteredString(CharSequence var1, float var2, float var3, int var4) {
      return this.drawCenteredString(var1, var2, var3, var4, false);
   }
}
