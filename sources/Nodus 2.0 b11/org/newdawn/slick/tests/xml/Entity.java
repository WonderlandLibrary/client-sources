/*  1:   */ package org.newdawn.slick.tests.xml;
/*  2:   */ 
/*  3:   */ import java.io.PrintStream;
/*  4:   */ 
/*  5:   */ public class Entity
/*  6:   */ {
/*  7:   */   private float x;
/*  8:   */   private float y;
/*  9:   */   private Inventory invent;
/* 10:   */   private Stats stats;
/* 11:   */   
/* 12:   */   private void add(Inventory inventory)
/* 13:   */   {
/* 14:24 */     this.invent = inventory;
/* 15:   */   }
/* 16:   */   
/* 17:   */   private void add(Stats stats)
/* 18:   */   {
/* 19:33 */     this.stats = stats;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void dump(String prefix)
/* 23:   */   {
/* 24:42 */     System.out.println(prefix + "Entity " + this.x + "," + this.y);
/* 25:43 */     this.invent.dump(prefix + "\t");
/* 26:44 */     this.stats.dump(prefix + "\t");
/* 27:   */   }
/* 28:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.tests.xml.Entity
 * JD-Core Version:    0.7.0.1
 */