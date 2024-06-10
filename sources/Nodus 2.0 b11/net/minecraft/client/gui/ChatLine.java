/*  1:   */ package net.minecraft.client.gui;
/*  2:   */ 
/*  3:   */ import net.minecraft.util.IChatComponent;
/*  4:   */ 
/*  5:   */ public class ChatLine
/*  6:   */ {
/*  7:   */   private final int updateCounterCreated;
/*  8:   */   private final IChatComponent lineString;
/*  9:   */   private final int chatLineID;
/* 10:   */   private static final String __OBFID = "CL_00000627";
/* 11:   */   
/* 12:   */   public ChatLine(int p_i45000_1_, IChatComponent p_i45000_2_, int p_i45000_3_)
/* 13:   */   {
/* 14:19 */     this.lineString = p_i45000_2_;
/* 15:20 */     this.updateCounterCreated = p_i45000_1_;
/* 16:21 */     this.chatLineID = p_i45000_3_;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public IChatComponent func_151461_a()
/* 20:   */   {
/* 21:26 */     return this.lineString;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public int getUpdatedCounter()
/* 25:   */   {
/* 26:31 */     return this.updateCounterCreated;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public int getChatLineID()
/* 30:   */   {
/* 31:36 */     return this.chatLineID;
/* 32:   */   }
/* 33:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.ChatLine
 * JD-Core Version:    0.7.0.1
 */