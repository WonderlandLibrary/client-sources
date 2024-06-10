/*  1:   */ package org.newdawn.slick.util.xml;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import java.util.Collection;
/*  5:   */ 
/*  6:   */ public class XMLElementList
/*  7:   */ {
/*  8:13 */   private ArrayList list = new ArrayList();
/*  9:   */   
/* 10:   */   public void add(XMLElement element)
/* 11:   */   {
/* 12:28 */     this.list.add(element);
/* 13:   */   }
/* 14:   */   
/* 15:   */   public int size()
/* 16:   */   {
/* 17:37 */     return this.list.size();
/* 18:   */   }
/* 19:   */   
/* 20:   */   public XMLElement get(int i)
/* 21:   */   {
/* 22:47 */     return (XMLElement)this.list.get(i);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public boolean contains(XMLElement element)
/* 26:   */   {
/* 27:57 */     return this.list.contains(element);
/* 28:   */   }
/* 29:   */   
/* 30:   */   public void addAllTo(Collection collection)
/* 31:   */   {
/* 32:66 */     collection.addAll(this.list);
/* 33:   */   }
/* 34:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.util.xml.XMLElementList
 * JD-Core Version:    0.7.0.1
 */