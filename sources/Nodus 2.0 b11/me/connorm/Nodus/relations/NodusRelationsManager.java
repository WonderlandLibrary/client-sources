/*  1:   */ package me.connorm.Nodus.relations;
/*  2:   */ 
/*  3:   */ import me.connorm.Nodus.relations.enemy.NodusEnemyManager;
/*  4:   */ import me.connorm.Nodus.relations.friend.NodusFriendManager;
/*  5:   */ 
/*  6:   */ public class NodusRelationsManager
/*  7:   */ {
/*  8:   */   public NodusFriendManager friendManager;
/*  9:   */   public NodusEnemyManager enemyManager;
/* 10:   */   
/* 11:   */   public NodusRelationsManager()
/* 12:   */   {
/* 13:13 */     this.friendManager = new NodusFriendManager();
/* 14:14 */     this.enemyManager = new NodusEnemyManager();
/* 15:   */   }
/* 16:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.relations.NodusRelationsManager
 * JD-Core Version:    0.7.0.1
 */