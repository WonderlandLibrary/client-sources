/*    */ package nightmare.fonts.impl;
/*    */ 
/*    */ import nightmare.Nightmare;
/*    */ import nightmare.fonts.api.FontFamily;
/*    */ import nightmare.fonts.api.FontManager;
/*    */ import nightmare.fonts.api.FontRenderer;
/*    */ import nightmare.fonts.api.FontType;
/*    */ 
/*    */ public interface Fonts
/*    */ {
/* 11 */   public static final FontManager FONT_MANAGER = Nightmare.instance.fontManager;
/*    */   
/*    */   public static interface REGULAR
/*    */   {
/* 15 */     public static final FontFamily REGULAR = Fonts.FONT_MANAGER.fontFamily(FontType.REGULAR);
/*    */     
/* 17 */     public static final class REGULAR_14 { public static final FontRenderer REGULAR_14 = Fonts.REGULAR.REGULAR.ofSize(14); }
/* 18 */     public static final class REGULAR_15 { public static final FontRenderer REGULAR_15 = Fonts.REGULAR.REGULAR.ofSize(15); }
/* 19 */     public static final class REGULAR_16 { public static final FontRenderer REGULAR_16 = Fonts.REGULAR.REGULAR.ofSize(16); }
/* 20 */     public static final class REGULAR_17 { public static final FontRenderer REGULAR_17 = Fonts.REGULAR.REGULAR.ofSize(17); }
/* 21 */     public static final class REGULAR_18 { public static final FontRenderer REGULAR_18 = Fonts.REGULAR.REGULAR.ofSize(18); }
/* 22 */     public static final class REGULAR_19 { public static final FontRenderer REGULAR_19 = Fonts.REGULAR.REGULAR.ofSize(19); }
/* 23 */     public static final class REGULAR_20 { public static final FontRenderer REGULAR_20 = Fonts.REGULAR.REGULAR.ofSize(20); }
/* 24 */     public static final class REGULAR_21 { public static final FontRenderer REGULAR_21 = Fonts.REGULAR.REGULAR.ofSize(21); }
/* 25 */     public static final class REGULAR_22 { public static final FontRenderer REGULAR_22 = Fonts.REGULAR.REGULAR.ofSize(22); }
/* 26 */     public static final class REGULAR_23 { public static final FontRenderer REGULAR_23 = Fonts.REGULAR.REGULAR.ofSize(23); }
/* 27 */     public static final class REGULAR_24 { public static final FontRenderer REGULAR_24 = Fonts.REGULAR.REGULAR.ofSize(24); }
/* 28 */     public static final class REGULAR_25 { public static final FontRenderer REGULAR_25 = Fonts.REGULAR.REGULAR.ofSize(25); }
/* 29 */     public static final class REGULAR_26 { public static final FontRenderer REGULAR_26 = Fonts.REGULAR.REGULAR.ofSize(26); }
/* 30 */     public static final class REGULAR_27 { public static final FontRenderer REGULAR_27 = Fonts.REGULAR.REGULAR.ofSize(27); }
/* 31 */     public static final class REGULAR_28 { public static final FontRenderer REGULAR_28 = Fonts.REGULAR.REGULAR.ofSize(28); }
/* 32 */     public static final class REGULAR_29 { public static final FontRenderer REGULAR_29 = Fonts.REGULAR.REGULAR.ofSize(29); }
/* 33 */     public static final class REGULAR_30 { public static final FontRenderer REGULAR_30 = Fonts.REGULAR.REGULAR.ofSize(30); }
/* 34 */     public static final class REGULAR_31 { public static final FontRenderer REGULAR_31 = Fonts.REGULAR.REGULAR.ofSize(31); }
/* 35 */     public static final class REGULAR_35 { public static final FontRenderer REGULAR_35 = Fonts.REGULAR.REGULAR.ofSize(31); }
/* 36 */     public static final class REGULAR_40 { public static final FontRenderer REGULAR_40 = Fonts.REGULAR.REGULAR.ofSize(40); }
/* 37 */     public static final class REGULAR_50 { public static final FontRenderer REGULAR_50 = Fonts.REGULAR.REGULAR.ofSize(45); }
/*    */   
/*    */   }
/*    */   
/*    */   public static interface ICON {
/* 42 */     public static final FontFamily ICON = Fonts.FONT_MANAGER.fontFamily(FontType.ICON);
/*    */     
/* 44 */     public static final class ICON_16 { public static final FontRenderer ICON_16 = Fonts.ICON.ICON.ofSize(16); }
/* 45 */     public static final class ICON_20 { public static final FontRenderer ICON_20 = Fonts.ICON.ICON.ofSize(20); }
/* 46 */     public static final class ICON_24 { public static final FontRenderer ICON_24 = Fonts.ICON.ICON.ofSize(24); }
/* 47 */     public static final class ICON_35 { public static final FontRenderer ICON_35 = Fonts.ICON.ICON.ofSize(35); }
/* 48 */     public static final class ICON_40 { public static final FontRenderer ICON_40 = Fonts.ICON.ICON.ofSize(40); }
/* 49 */     public static final class ICON_45 { public static final FontRenderer ICON_45 = Fonts.ICON.ICON.ofSize(45); }
/* 50 */     public static final class ICON_50 { public static final FontRenderer ICON_50 = Fonts.ICON.ICON.ofSize(50); }
/*    */   
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\fonts\impl\Fonts.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */