/*  1:   */ package net.minecraft.util;
/*  2:   */ 
/*  3:   */ public class ChatComponentTranslationFormatException
/*  4:   */   extends IllegalArgumentException
/*  5:   */ {
/*  6:   */   private static final String __OBFID = "CL_00001271";
/*  7:   */   
/*  8:   */   public ChatComponentTranslationFormatException(ChatComponentTranslation p_i45161_1_, String p_i45161_2_)
/*  9:   */   {
/* 10: 9 */     super(String.format("Error parsing: %s: %s", new Object[] { p_i45161_1_, p_i45161_2_ }));
/* 11:   */   }
/* 12:   */   
/* 13:   */   public ChatComponentTranslationFormatException(ChatComponentTranslation p_i45162_1_, int p_i45162_2_)
/* 14:   */   {
/* 15:14 */     super(String.format("Invalid index %d requested for %s", new Object[] { Integer.valueOf(p_i45162_2_), p_i45162_1_ }));
/* 16:   */   }
/* 17:   */   
/* 18:   */   public ChatComponentTranslationFormatException(ChatComponentTranslation p_i45163_1_, Throwable p_i45163_2_)
/* 19:   */   {
/* 20:19 */     super(String.format("Error while parsing: %s", new Object[] { p_i45163_1_ }), p_i45163_2_);
/* 21:   */   }
/* 22:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.util.ChatComponentTranslationFormatException
 * JD-Core Version:    0.7.0.1
 */