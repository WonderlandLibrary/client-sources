/*    */ package nightmare.fonts.api;
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface FontRenderer
/*    */ {
/*    */   float drawString(CharSequence paramCharSequence, double paramDouble1, double paramDouble2, int paramInt, boolean paramBoolean);
/*    */   
/*    */   String trimStringToWidth(CharSequence paramCharSequence, int paramInt, boolean paramBoolean);
/*    */   
/*    */   int getStringWidth(CharSequence paramCharSequence);
/*    */   
/*    */   float getCharWidth(char paramChar);
/*    */   
/*    */   default float drawString(CharSequence text, float x, float y, int color) {
/* 16 */     return drawString(text, x, y, color, false);
/*    */   } String getName(); int getHeight(); boolean isAntiAlias();
/*    */   boolean isFractionalMetrics();
/*    */   default String trimStringToWidth(CharSequence text, int width) {
/* 20 */     return trimStringToWidth(text, width, false);
/*    */   }
/*    */   
/*    */   default float drawCenteredString(CharSequence text, float x, float y, int color, boolean dropShadow) {
/* 24 */     return drawString(text, (x - getStringWidth(text) / 2.0F), y, color, dropShadow);
/*    */   }
/*    */   
/*    */   default float drawCenteredString(CharSequence text, float x, float y, int color) {
/* 28 */     return drawCenteredString(text, x, y, color, false);
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\fonts\api\FontRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */