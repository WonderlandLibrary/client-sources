/*  1:   */ package org.newdawn.slick.util.pathfinding.navmesh;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ 
/*  5:   */ public class NavPath
/*  6:   */ {
/*  7:12 */   private ArrayList links = new ArrayList();
/*  8:   */   
/*  9:   */   public void push(Link link)
/* 10:   */   {
/* 11:26 */     this.links.add(link);
/* 12:   */   }
/* 13:   */   
/* 14:   */   public int length()
/* 15:   */   {
/* 16:35 */     return this.links.size();
/* 17:   */   }
/* 18:   */   
/* 19:   */   public float getX(int step)
/* 20:   */   {
/* 21:45 */     return ((Link)this.links.get(step)).getX();
/* 22:   */   }
/* 23:   */   
/* 24:   */   public float getY(int step)
/* 25:   */   {
/* 26:55 */     return ((Link)this.links.get(step)).getY();
/* 27:   */   }
/* 28:   */   
/* 29:   */   public String toString()
/* 30:   */   {
/* 31:64 */     return "[Path length=" + length() + "]";
/* 32:   */   }
/* 33:   */   
/* 34:   */   public void remove(int i)
/* 35:   */   {
/* 36:73 */     this.links.remove(i);
/* 37:   */   }
/* 38:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.util.pathfinding.navmesh.NavPath
 * JD-Core Version:    0.7.0.1
 */