/*     */ package org.newdawn.slick.gui;
/*     */ 
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import org.newdawn.slick.Graphics;
/*     */ import org.newdawn.slick.Input;
/*     */ import org.newdawn.slick.InputListener;
/*     */ import org.newdawn.slick.SlickException;
/*     */ import org.newdawn.slick.geom.Rectangle;
/*     */ import org.newdawn.slick.util.InputAdapter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractComponent
/*     */   extends InputAdapter
/*     */ {
/*  21 */   private static AbstractComponent currentFocus = null;
/*     */ 
/*     */ 
/*     */   
/*     */   protected GUIContext container;
/*     */ 
/*     */ 
/*     */   
/*     */   protected Set listeners;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean focus = false;
/*     */ 
/*     */ 
/*     */   
/*     */   protected Input input;
/*     */ 
/*     */ 
/*     */   
/*     */   public AbstractComponent(GUIContext container) {
/*  42 */     this.container = container;
/*     */     
/*  44 */     this.listeners = new HashSet();
/*     */     
/*  46 */     this.input = container.getInput();
/*  47 */     this.input.addPrimaryListener((InputListener)this);
/*     */     
/*  49 */     setLocation(0, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addListener(ComponentListener listener) {
/*  61 */     this.listeners.add(listener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeListener(ComponentListener listener) {
/*  73 */     this.listeners.remove(listener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void notifyListeners() {
/*  80 */     Iterator<ComponentListener> it = this.listeners.iterator();
/*  81 */     while (it.hasNext()) {
/*  82 */       ((ComponentListener)it.next()).componentActivated(this);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void render(GUIContext paramGUIContext, Graphics paramGraphics) throws SlickException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void setLocation(int paramInt1, int paramInt2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract int getX();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract int getY();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract int getWidth();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract int getHeight();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFocus(boolean focus) {
/* 144 */     if (focus) {
/* 145 */       if (currentFocus != null) {
/* 146 */         currentFocus.setFocus(false);
/*     */       }
/* 148 */       currentFocus = this;
/*     */     }
/* 150 */     else if (currentFocus == this) {
/* 151 */       currentFocus = null;
/*     */     } 
/*     */     
/* 154 */     this.focus = focus;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasFocus() {
/* 163 */     return this.focus;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void consumeEvent() {
/* 170 */     this.input.consumeEvent();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseReleased(int button, int x, int y) {
/* 179 */     setFocus(Rectangle.contains(x, y, getX(), getY(), getWidth(), getHeight()));
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\gui\AbstractComponent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */