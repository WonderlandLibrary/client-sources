/*  1:   */ package me.connorm.Nodus.account;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ 
/*  5:   */ public class Account
/*  6:   */ {
/*  7: 7 */   public static ArrayList<Account> accountList = new ArrayList();
/*  8:   */   private String username;
/*  9:   */   private String password;
/* 10:   */   private boolean premium;
/* 11:   */   
/* 12:   */   public Account(String username)
/* 13:   */   {
/* 14:14 */     this.username = username;
/* 15:15 */     this.password = "";
/* 16:16 */     this.premium = false;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public Account(String username, String password)
/* 20:   */   {
/* 21:20 */     this.username = username;
/* 22:21 */     this.password = password;
/* 23:22 */     if ((password != null) && (!password.equals(""))) {
/* 24:23 */       this.premium = true;
/* 25:   */     } else {
/* 26:25 */       this.premium = false;
/* 27:   */     }
/* 28:   */   }
/* 29:   */   
/* 30:   */   public String getUsername()
/* 31:   */   {
/* 32:30 */     return this.username;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public String getPassword()
/* 36:   */   {
/* 37:34 */     return this.password;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public boolean isPremium()
/* 41:   */   {
/* 42:38 */     return this.premium;
/* 43:   */   }
/* 44:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.account.Account
 * JD-Core Version:    0.7.0.1
 */