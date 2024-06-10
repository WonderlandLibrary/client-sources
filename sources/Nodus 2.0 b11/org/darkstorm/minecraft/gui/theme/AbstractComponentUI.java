/*   1:    */ package org.darkstorm.minecraft.gui.theme;
/*   2:    */ 
/*   3:    */ import java.awt.Color;
/*   4:    */ import java.awt.Dimension;
/*   5:    */ import java.awt.Point;
/*   6:    */ import java.awt.Rectangle;
/*   7:    */ import org.darkstorm.minecraft.gui.component.Component;
/*   8:    */ import org.darkstorm.minecraft.gui.component.Container;
/*   9:    */ import org.lwjgl.opengl.GL11;
/*  10:    */ 
/*  11:    */ public abstract class AbstractComponentUI<T extends Component>
/*  12:    */   implements ComponentUI
/*  13:    */ {
/*  14:    */   protected final Class<T> handledComponentClass;
/*  15:    */   protected Color foreground;
/*  16:    */   protected Color background;
/*  17:    */   
/*  18:    */   public AbstractComponentUI(Class<T> handledComponentClass)
/*  19:    */   {
/*  20: 14 */     this.handledComponentClass = handledComponentClass;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public void render(Component component)
/*  24:    */   {
/*  25: 18 */     if (component == null) {
/*  26: 19 */       throw new NullPointerException();
/*  27:    */     }
/*  28: 20 */     if (!this.handledComponentClass.isInstance(component)) {
/*  29: 21 */       throw new IllegalArgumentException();
/*  30:    */     }
/*  31: 22 */     if (!component.isVisible()) {
/*  32: 23 */       return;
/*  33:    */     }
/*  34: 24 */     renderComponent((Component)this.handledComponentClass.cast(component));
/*  35:    */   }
/*  36:    */   
/*  37:    */   protected abstract void renderComponent(T paramT);
/*  38:    */   
/*  39:    */   public Rectangle getChildRenderArea(Container container)
/*  40:    */   {
/*  41: 31 */     if (!Container.class.isAssignableFrom(this.handledComponentClass)) {
/*  42: 32 */       throw new UnsupportedOperationException();
/*  43:    */     }
/*  44: 33 */     if (container == null) {
/*  45: 34 */       throw new NullPointerException();
/*  46:    */     }
/*  47: 35 */     if (!this.handledComponentClass.isInstance(container)) {
/*  48: 36 */       throw new IllegalArgumentException();
/*  49:    */     }
/*  50: 37 */     return getContainerChildRenderArea((Component)this.handledComponentClass.cast(container));
/*  51:    */   }
/*  52:    */   
/*  53:    */   protected Rectangle getContainerChildRenderArea(T container)
/*  54:    */   {
/*  55: 41 */     return new Rectangle(new Point(0, 0), container.getSize());
/*  56:    */   }
/*  57:    */   
/*  58:    */   public Dimension getDefaultSize(Component component)
/*  59:    */   {
/*  60: 46 */     if (component == null) {
/*  61: 47 */       throw new NullPointerException();
/*  62:    */     }
/*  63: 48 */     if (!this.handledComponentClass.isInstance(component)) {
/*  64: 49 */       throw new IllegalArgumentException();
/*  65:    */     }
/*  66: 50 */     return getDefaultComponentSize((Component)this.handledComponentClass.cast(component));
/*  67:    */   }
/*  68:    */   
/*  69:    */   protected abstract Dimension getDefaultComponentSize(T paramT);
/*  70:    */   
/*  71:    */   protected void translateComponent(Component component, boolean reverse)
/*  72:    */   {
/*  73: 56 */     Component parent = component.getParent();
/*  74: 57 */     while (parent != null)
/*  75:    */     {
/*  76: 58 */       GL11.glTranslated((reverse ? -1 : 1) * parent.getX(), (reverse ? -1 : 1) * parent.getY(), 0.0D);
/*  77: 59 */       parent = parent.getParent();
/*  78:    */     }
/*  79: 61 */     GL11.glTranslated((reverse ? -1 : 1) * component.getX(), (reverse ? -1 : 1) * component.getY(), 0.0D);
/*  80:    */   }
/*  81:    */   
/*  82:    */   public Color getDefaultBackgroundColor(Component component)
/*  83:    */   {
/*  84: 66 */     if (component == null) {
/*  85: 67 */       throw new NullPointerException();
/*  86:    */     }
/*  87: 68 */     if (!this.handledComponentClass.isInstance(component)) {
/*  88: 69 */       throw new IllegalArgumentException();
/*  89:    */     }
/*  90: 70 */     return getBackgroundColor((Component)this.handledComponentClass.cast(component));
/*  91:    */   }
/*  92:    */   
/*  93:    */   protected Color getBackgroundColor(T component)
/*  94:    */   {
/*  95: 74 */     return this.background;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public Color getDefaultForegroundColor(Component component)
/*  99:    */   {
/* 100: 79 */     if (component == null) {
/* 101: 80 */       throw new NullPointerException();
/* 102:    */     }
/* 103: 81 */     if (!this.handledComponentClass.isInstance(component)) {
/* 104: 82 */       throw new IllegalArgumentException();
/* 105:    */     }
/* 106: 83 */     return getForegroundColor((Component)this.handledComponentClass.cast(component));
/* 107:    */   }
/* 108:    */   
/* 109:    */   protected Color getForegroundColor(T component)
/* 110:    */   {
/* 111: 87 */     return this.foreground;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public Rectangle[] getInteractableRegions(Component component)
/* 115:    */   {
/* 116: 92 */     if (component == null) {
/* 117: 93 */       throw new NullPointerException();
/* 118:    */     }
/* 119: 94 */     if (!this.handledComponentClass.isInstance(component)) {
/* 120: 95 */       throw new IllegalArgumentException();
/* 121:    */     }
/* 122: 96 */     return getInteractableComponentRegions((Component)this.handledComponentClass.cast(component));
/* 123:    */   }
/* 124:    */   
/* 125:    */   protected Rectangle[] getInteractableComponentRegions(T component)
/* 126:    */   {
/* 127:100 */     return new Rectangle[0];
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void handleInteraction(Component component, Point location, int button)
/* 131:    */   {
/* 132:105 */     if (component == null) {
/* 133:106 */       throw new NullPointerException();
/* 134:    */     }
/* 135:107 */     if (!this.handledComponentClass.isInstance(component)) {
/* 136:108 */       throw new IllegalArgumentException();
/* 137:    */     }
/* 138:109 */     handleComponentInteraction((Component)this.handledComponentClass.cast(component), location, button);
/* 139:    */   }
/* 140:    */   
/* 141:    */   protected void handleComponentInteraction(T component, Point location, int button) {}
/* 142:    */   
/* 143:    */   public void handleUpdate(Component component)
/* 144:    */   {
/* 145:117 */     if (component == null) {
/* 146:118 */       throw new NullPointerException();
/* 147:    */     }
/* 148:119 */     if (!this.handledComponentClass.isInstance(component)) {
/* 149:120 */       throw new IllegalArgumentException();
/* 150:    */     }
/* 151:121 */     handleComponentUpdate((Component)this.handledComponentClass.cast(component));
/* 152:    */   }
/* 153:    */   
/* 154:    */   protected void handleComponentUpdate(T component) {}
/* 155:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.darkstorm.minecraft.gui.theme.AbstractComponentUI
 * JD-Core Version:    0.7.0.1
 */