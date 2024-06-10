/*  1:   */ package net.minecraft.util;
/*  2:   */ 
/*  3:   */ import com.mojang.authlib.GameProfile;
/*  4:   */ 
/*  5:   */ public class Session
/*  6:   */ {
/*  7:   */   private final String username;
/*  8:   */   private final String playerID;
/*  9:   */   private final String token;
/* 10:   */   private static final String __OBFID = "CL_00000659";
/* 11:   */   
/* 12:   */   public Session(String p_i45006_1_, String p_i45006_2_, String p_i45006_3_)
/* 13:   */   {
/* 14:14 */     this.username = p_i45006_1_;
/* 15:15 */     this.playerID = p_i45006_2_;
/* 16:16 */     this.token = p_i45006_3_;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public String getSessionID()
/* 20:   */   {
/* 21:21 */     return "token:" + this.token + ":" + this.playerID;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public String getPlayerID()
/* 25:   */   {
/* 26:26 */     return this.playerID;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public String getUsername()
/* 30:   */   {
/* 31:31 */     return this.username;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public String getToken()
/* 35:   */   {
/* 36:36 */     return this.token;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public GameProfile func_148256_e()
/* 40:   */   {
/* 41:41 */     return new GameProfile(getPlayerID(), getUsername());
/* 42:   */   }
/* 43:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.util.Session
 * JD-Core Version:    0.7.0.1
 */