/*  1:   */ package me.connorm.Nodus.relations.friend;
/*  2:   */ 
/*  3:   */ import me.connorm.Nodus.relations.core.IEntity;
/*  4:   */ 
/*  5:   */ public class NodusFriend
/*  6:   */   implements IEntity
/*  7:   */ {
/*  8:   */   private String playerName;
/*  9:   */   
/* 10:   */   public NodusFriend(String playerName)
/* 11:   */   {
/* 12:11 */     this.playerName = playerName;
/* 13:   */   }
/* 14:   */   
/* 15:   */   public String getName()
/* 16:   */   {
/* 17:17 */     return this.playerName;
/* 18:   */   }
/* 19:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.relations.friend.NodusFriend
 * JD-Core Version:    0.7.0.1
 */