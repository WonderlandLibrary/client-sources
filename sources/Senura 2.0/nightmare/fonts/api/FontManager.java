/*   */ package nightmare.fonts.api;
/*   */ 
/*   */ @FunctionalInterface
/*   */ public interface FontManager
/*   */ {
/*   */   FontFamily fontFamily(FontType paramFontType);
/*   */   
/*   */   default FontRenderer font(FontType fontType, int size) {
/* 9 */     return fontFamily(fontType).ofSize(size);
/*   */   }
/*   */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\fonts\api\FontManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */