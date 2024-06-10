/*    */ package nightmare.fonts.impl;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.ints.Int2ObjectAVLTreeMap;
/*    */ import java.awt.Font;
/*    */ import nightmare.fonts.api.FontFamily;
/*    */ import nightmare.fonts.api.FontRenderer;
/*    */ import nightmare.fonts.api.FontType;
/*    */ 
/*    */ final class SimpleFontFamily extends Int2ObjectAVLTreeMap<FontRenderer> implements FontFamily {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private final FontType fontType;
/*    */   private final Font awtFont;
/*    */   
/*    */   private SimpleFontFamily(FontType fontType, Font awtFont) {
/* 15 */     this.fontType = fontType;
/* 16 */     this.awtFont = awtFont;
/*    */   }
/*    */   
/*    */   static FontFamily create(FontType fontType, Font awtFont) {
/* 20 */     return new SimpleFontFamily(fontType, awtFont);
/*    */   }
/*    */ 
/*    */   
/*    */   public FontRenderer ofSize(int size) {
/* 25 */     return (FontRenderer)computeIfAbsent(size, ignored -> SimpleFontRenderer.create(this.awtFont.deriveFont(0, size)));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public FontType font() {
/* 31 */     return this.fontType;
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\fonts\impl\SimpleFontFamily.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */