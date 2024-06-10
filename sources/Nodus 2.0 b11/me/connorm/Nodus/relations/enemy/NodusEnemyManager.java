/*  1:   */ package me.connorm.Nodus.relations.enemy;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import java.util.Iterator;
/*  5:   */ 
/*  6:   */ public class NodusEnemyManager
/*  7:   */ {
/*  8:10 */   private ArrayList<NodusEnemy> theEnemies = new ArrayList();
/*  9:   */   
/* 10:   */   public void addEnemy(String enemyName)
/* 11:   */   {
/* 12:14 */     this.theEnemies.add(new NodusEnemy(enemyName));
/* 13:   */   }
/* 14:   */   
/* 15:   */   public void removeEnemy(String enemyName)
/* 16:   */   {
/* 17:19 */     Iterator enemyIterator = this.theEnemies.iterator();
/* 18:22 */     while (enemyIterator.hasNext())
/* 19:   */     {
/* 20:27 */       NodusEnemy enemy = (NodusEnemy)enemyIterator.next();
/* 21:29 */       if (enemy.getName().equalsIgnoreCase(enemyName)) {
/* 22:33 */         this.theEnemies.remove(enemy);
/* 23:   */       }
/* 24:   */     }
/* 25:   */   }
/* 26:   */   
/* 27:   */   public boolean isEnemy(String enemyName)
/* 28:   */   {
/* 29:40 */     boolean isEnemy = false;
/* 30:41 */     Iterator enemyIterator = this.theEnemies.iterator();
/* 31:44 */     while (enemyIterator.hasNext())
/* 32:   */     {
/* 33:49 */       NodusEnemy enemy = (NodusEnemy)enemyIterator.next();
/* 34:51 */       if (enemy.getName().equalsIgnoreCase(enemyName)) {
/* 35:55 */         isEnemy = true;
/* 36:   */       }
/* 37:   */     }
/* 38:58 */     return isEnemy;
/* 39:   */   }
/* 40:   */   
/* 41:   */   public NodusEnemy[] getEnemies()
/* 42:   */   {
/* 43:63 */     return (NodusEnemy[])this.theEnemies.toArray(new NodusEnemy[this.theEnemies.size()]);
/* 44:   */   }
/* 45:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.relations.enemy.NodusEnemyManager
 * JD-Core Version:    0.7.0.1
 */