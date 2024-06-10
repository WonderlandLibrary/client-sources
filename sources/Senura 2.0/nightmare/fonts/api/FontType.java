/*    */ package nightmare.fonts.api;
/*    */ 
/*    */ public enum FontType
/*    */ {
/*  5 */   REGULAR("regular.ttf"),
/*  6 */   ICON("icon.ttf");
/*    */   
/*    */   private final String fileName;
/*    */   
/*    */   FontType(String fileName) {
/* 11 */     this.fileName = fileName;
/*    */   }
/*    */   public String fileName() {
/* 14 */     return this.fileName;
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\fonts\api\FontType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */