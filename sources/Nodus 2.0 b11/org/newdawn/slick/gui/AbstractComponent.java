/*   1:    */ package org.newdawn.slick.gui;
/*   2:    */ 
/*   3:    */ import java.util.HashSet;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.Set;
/*   6:    */ import org.newdawn.slick.Graphics;
/*   7:    */ import org.newdawn.slick.Input;
/*   8:    */ import org.newdawn.slick.SlickException;
/*   9:    */ import org.newdawn.slick.geom.Rectangle;
/*  10:    */ import org.newdawn.slick.util.InputAdapter;
/*  11:    */ 
/*  12:    */ public abstract class AbstractComponent
/*  13:    */   extends InputAdapter
/*  14:    */ {
/*  15: 21 */   private static AbstractComponent currentFocus = null;
/*  16:    */   protected GUIContext container;
/*  17:    */   protected Set listeners;
/*  18: 30 */   private boolean focus = false;
/*  19:    */   protected Input input;
/*  20:    */   
/*  21:    */   public AbstractComponent(GUIContext container)
/*  22:    */   {
/*  23: 42 */     this.container = container;
/*  24:    */     
/*  25: 44 */     this.listeners = new HashSet();
/*  26:    */     
/*  27: 46 */     this.input = container.getInput();
/*  28: 47 */     this.input.addPrimaryListener(this);
/*  29:    */     
/*  30: 49 */     setLocation(0, 0);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void addListener(ComponentListener listener)
/*  34:    */   {
/*  35: 61 */     this.listeners.add(listener);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public void removeListener(ComponentListener listener)
/*  39:    */   {
/*  40: 73 */     this.listeners.remove(listener);
/*  41:    */   }
/*  42:    */   
/*  43:    */   protected void notifyListeners()
/*  44:    */   {
/*  45: 80 */     Iterator it = this.listeners.iterator();
/*  46: 81 */     while (it.hasNext()) {
/*  47: 82 */       ((ComponentListener)it.next()).componentActivated(this);
/*  48:    */     }
/*  49:    */   }
/*  50:    */   
/*  51:    */   public abstract void render(GUIContext paramGUIContext, Graphics paramGraphics)
/*  52:    */     throws SlickException;
/*  53:    */   
/*  54:    */   public abstract void setLocation(int paramInt1, int paramInt2);
/*  55:    */   
/*  56:    */   public abstract int getX();
/*  57:    */   
/*  58:    */   public abstract int getY();
/*  59:    */   
/*  60:    */   public abstract int getWidth();
/*  61:    */   
/*  62:    */   public abstract int getHeight();
/*  63:    */   
/*  64:    */   public void setFocus(boolean focus)
/*  65:    */   {
/*  66:144 */     if (focus)
/*  67:    */     {
/*  68:145 */       if (currentFocus != null) {
/*  69:146 */         currentFocus.setFocus(false);
/*  70:    */       }
/*  71:148 */       currentFocus = this;
/*  72:    */     }
/*  73:150 */     else if (currentFocus == this)
/*  74:    */     {
/*  75:151 */       currentFocus = null;
/*  76:    */     }
/*  77:154 */     this.focus = focus;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public boolean hasFocus()
/*  81:    */   {
/*  82:163 */     return this.focus;
/*  83:    */   }
/*  84:    */   
/*  85:    */   protected void consumeEvent()
/*  86:    */   {
/*  87:170 */     this.input.consumeEvent();
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void mouseReleased(int button, int x, int y)
/*  91:    */   {
/*  92:179 */     setFocus(Rectangle.contains(x, y, getX(), getY(), getWidth(), 
/*  93:180 */       getHeight()));
/*  94:    */   }
/*  95:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.gui.AbstractComponent
 * JD-Core Version:    0.7.0.1
 */