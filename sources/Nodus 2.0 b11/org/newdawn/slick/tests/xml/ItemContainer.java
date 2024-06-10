/*  1:   */ package org.newdawn.slick.tests.xml;
/*  2:   */ 
/*  3:   */ import java.io.PrintStream;
/*  4:   */ import java.util.ArrayList;
/*  5:   */ 
/*  6:   */ public class ItemContainer
/*  7:   */   extends Item
/*  8:   */ {
/*  9:12 */   private ArrayList items = new ArrayList();
/* 10:   */   
/* 11:   */   private void add(Item item)
/* 12:   */   {
/* 13:20 */     this.items.add(item);
/* 14:   */   }
/* 15:   */   
/* 16:   */   private void setName(String name)
/* 17:   */   {
/* 18:31 */     this.name = name;
/* 19:   */   }
/* 20:   */   
/* 21:   */   private void setCondition(int condition)
/* 22:   */   {
/* 23:42 */     this.condition = condition;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void dump(String prefix)
/* 27:   */   {
/* 28:51 */     System.out.println(prefix + "Item Container " + this.name + "," + this.condition);
/* 29:52 */     for (int i = 0; i < this.items.size(); i++) {
/* 30:53 */       ((Item)this.items.get(i)).dump(prefix + "\t");
/* 31:   */     }
/* 32:   */   }
/* 33:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.tests.xml.ItemContainer
 * JD-Core Version:    0.7.0.1
 */