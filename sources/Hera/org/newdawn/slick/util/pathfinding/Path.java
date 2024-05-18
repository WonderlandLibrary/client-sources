/*     */ package org.newdawn.slick.util.pathfinding;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Path
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  18 */   private ArrayList steps = new ArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLength() {
/*  33 */     return this.steps.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Step getStep(int index) {
/*  44 */     return this.steps.get(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getX(int index) {
/*  54 */     return (getStep(index)).x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getY(int index) {
/*  64 */     return (getStep(index)).y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void appendStep(int x, int y) {
/*  74 */     this.steps.add(new Step(x, y));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void prependStep(int x, int y) {
/*  84 */     this.steps.add(0, new Step(x, y));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(int x, int y) {
/*  95 */     return this.steps.contains(new Step(x, y));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public class Step
/*     */     implements Serializable
/*     */   {
/*     */     private int x;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private int y;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Step(int x, int y) {
/* 116 */       this.x = x;
/* 117 */       this.y = y;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getX() {
/* 126 */       return this.x;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getY() {
/* 135 */       return this.y;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 142 */       return this.x * this.y;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean equals(Object other) {
/* 149 */       if (other instanceof Step) {
/* 150 */         Step o = (Step)other;
/*     */         
/* 152 */         return (o.x == this.x && o.y == this.y);
/*     */       } 
/*     */       
/* 155 */       return false;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slic\\util\pathfinding\Path.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */