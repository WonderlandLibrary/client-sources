/*  1:   */ package org.newdawn.slick.tests.xml;
/*  2:   */ 
/*  3:   */ import java.io.PrintStream;
/*  4:   */ import java.util.ArrayList;
/*  5:   */ 
/*  6:   */ public class GameData
/*  7:   */ {
/*  8:12 */   private ArrayList entities = new ArrayList();
/*  9:   */   
/* 10:   */   private void add(Entity entity)
/* 11:   */   {
/* 12:20 */     this.entities.add(entity);
/* 13:   */   }
/* 14:   */   
/* 15:   */   public void dump(String prefix)
/* 16:   */   {
/* 17:29 */     System.out.println(prefix + "GameData");
/* 18:30 */     for (int i = 0; i < this.entities.size(); i++) {
/* 19:31 */       ((Entity)this.entities.get(i)).dump(prefix + "\t");
/* 20:   */     }
/* 21:   */   }
/* 22:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.tests.xml.GameData
 * JD-Core Version:    0.7.0.1
 */