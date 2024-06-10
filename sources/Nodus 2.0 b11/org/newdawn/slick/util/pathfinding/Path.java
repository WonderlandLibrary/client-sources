/*   1:    */ package org.newdawn.slick.util.pathfinding;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ 
/*   6:    */ public class Path
/*   7:    */   implements Serializable
/*   8:    */ {
/*   9:    */   private static final long serialVersionUID = 1L;
/*  10: 18 */   private ArrayList steps = new ArrayList();
/*  11:    */   
/*  12:    */   public int getLength()
/*  13:    */   {
/*  14: 33 */     return this.steps.size();
/*  15:    */   }
/*  16:    */   
/*  17:    */   public Step getStep(int index)
/*  18:    */   {
/*  19: 44 */     return (Step)this.steps.get(index);
/*  20:    */   }
/*  21:    */   
/*  22:    */   public int getX(int index)
/*  23:    */   {
/*  24: 54 */     return getStep(index).x;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public int getY(int index)
/*  28:    */   {
/*  29: 64 */     return getStep(index).y;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public void appendStep(int x, int y)
/*  33:    */   {
/*  34: 74 */     this.steps.add(new Step(x, y));
/*  35:    */   }
/*  36:    */   
/*  37:    */   public void prependStep(int x, int y)
/*  38:    */   {
/*  39: 84 */     this.steps.add(0, new Step(x, y));
/*  40:    */   }
/*  41:    */   
/*  42:    */   public boolean contains(int x, int y)
/*  43:    */   {
/*  44: 95 */     return this.steps.contains(new Step(x, y));
/*  45:    */   }
/*  46:    */   
/*  47:    */   public class Step
/*  48:    */     implements Serializable
/*  49:    */   {
/*  50:    */     private int x;
/*  51:    */     private int y;
/*  52:    */     
/*  53:    */     public Step(int x, int y)
/*  54:    */     {
/*  55:116 */       this.x = x;
/*  56:117 */       this.y = y;
/*  57:    */     }
/*  58:    */     
/*  59:    */     public int getX()
/*  60:    */     {
/*  61:126 */       return this.x;
/*  62:    */     }
/*  63:    */     
/*  64:    */     public int getY()
/*  65:    */     {
/*  66:135 */       return this.y;
/*  67:    */     }
/*  68:    */     
/*  69:    */     public int hashCode()
/*  70:    */     {
/*  71:142 */       return this.x * this.y;
/*  72:    */     }
/*  73:    */     
/*  74:    */     public boolean equals(Object other)
/*  75:    */     {
/*  76:149 */       if ((other instanceof Step))
/*  77:    */       {
/*  78:150 */         Step o = (Step)other;
/*  79:    */         
/*  80:152 */         return (o.x == this.x) && (o.y == this.y);
/*  81:    */       }
/*  82:155 */       return false;
/*  83:    */     }
/*  84:    */   }
/*  85:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.util.pathfinding.Path
 * JD-Core Version:    0.7.0.1
 */