/*   1:    */ package org.darkstorm.minecraft.gui.theme.simple;
/*   2:    */ 
/*   3:    */ import java.awt.Color;
/*   4:    */ import java.awt.Dimension;
/*   5:    */ import java.awt.Point;
/*   6:    */ import java.awt.Rectangle;
/*   7:    */ import net.minecraft.client.gui.FontRenderer;
/*   8:    */ import org.darkstorm.minecraft.gui.component.CheckButton;
/*   9:    */ import org.darkstorm.minecraft.gui.component.Component;
/*  10:    */ import org.darkstorm.minecraft.gui.theme.AbstractComponentUI;
/*  11:    */ import org.darkstorm.minecraft.gui.util.RenderUtil;
/*  12:    */ import org.lwjgl.input.Mouse;
/*  13:    */ import org.lwjgl.opengl.GL11;
/*  14:    */ 
/*  15:    */ public class SimpleCheckButtonUI
/*  16:    */   extends AbstractComponentUI<CheckButton>
/*  17:    */ {
/*  18:    */   private final SimpleTheme theme;
/*  19:    */   
/*  20:    */   SimpleCheckButtonUI(SimpleTheme theme)
/*  21:    */   {
/*  22: 18 */     super(CheckButton.class);
/*  23: 19 */     this.theme = theme;
/*  24:    */     
/*  25: 21 */     this.foreground = Color.WHITE;
/*  26: 22 */     this.background = new Color(128, 128, 128, 192);
/*  27:    */   }
/*  28:    */   
/*  29:    */   protected void renderComponent(CheckButton button)
/*  30:    */   {
/*  31: 27 */     translateComponent(button, false);
/*  32: 28 */     Rectangle area = button.getArea();
/*  33: 29 */     GL11.glEnable(3042);
/*  34: 30 */     GL11.glDisable(2884);
/*  35:    */     
/*  36: 32 */     GL11.glDisable(3553);
/*  37: 33 */     RenderUtil.setColor(button.getBackgroundColor());
/*  38: 34 */     int size = area.height - 4;
/*  39: 35 */     GL11.glBegin(7);
/*  40:    */     
/*  41: 37 */     GL11.glVertex2d(2.0D, 2.0D);
/*  42: 38 */     GL11.glVertex2d(size + 2, 2.0D);
/*  43: 39 */     GL11.glVertex2d(size + 2, size + 2);
/*  44: 40 */     GL11.glVertex2d(2.0D, size + 2);
/*  45:    */     
/*  46: 42 */     GL11.glEnd();
/*  47: 43 */     if (button.isSelected())
/*  48:    */     {
/*  49: 44 */       GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.5F);
/*  50: 45 */       GL11.glBegin(7);
/*  51:    */       
/*  52: 47 */       GL11.glVertex2d(3.0D, 3.5D);
/*  53: 48 */       GL11.glVertex2d(size + 0.5D, 3.5D);
/*  54: 49 */       GL11.glVertex2d(size + 0.5D, size + 1);
/*  55: 50 */       GL11.glVertex2d(3.0D, size + 1);
/*  56:    */       
/*  57: 52 */       GL11.glEnd();
/*  58:    */     }
/*  59: 54 */     GL11.glLineWidth(1.0F);
/*  60: 55 */     GL11.glColor4f(0.0F, 0.0F, 0.0F, 1.0F);
/*  61: 56 */     GL11.glBegin(2);
/*  62:    */     
/*  63: 58 */     GL11.glVertex2d(2.0D, 2.0D);
/*  64: 59 */     GL11.glVertex2d(size + 2, 2.0D);
/*  65: 60 */     GL11.glVertex2d(size + 2, size + 2);
/*  66: 61 */     GL11.glVertex2d(1.5D, size + 2);
/*  67:    */     
/*  68: 63 */     GL11.glEnd();
/*  69: 64 */     Point mouse = RenderUtil.calculateMouseLocation();
/*  70: 65 */     Component parent = button.getParent();
/*  71: 66 */     while (parent != null)
/*  72:    */     {
/*  73: 67 */       mouse.x -= parent.getX();
/*  74: 68 */       mouse.y -= parent.getY();
/*  75: 69 */       parent = parent.getParent();
/*  76:    */     }
/*  77: 71 */     if (area.contains(mouse))
/*  78:    */     {
/*  79: 72 */       GL11.glColor4f(0.0F, 0.0F, 0.0F, Mouse.isButtonDown(0) ? 0.5F : 0.3F);
/*  80: 73 */       GL11.glBegin(7);
/*  81:    */       
/*  82: 75 */       GL11.glVertex2d(0.0D, 0.0D);
/*  83: 76 */       GL11.glVertex2d(area.width, 0.0D);
/*  84: 77 */       GL11.glVertex2d(area.width, area.height);
/*  85: 78 */       GL11.glVertex2d(0.0D, area.height);
/*  86:    */       
/*  87: 80 */       GL11.glEnd();
/*  88:    */     }
/*  89: 82 */     GL11.glEnable(3553);
/*  90:    */     
/*  91: 84 */     String text = button.getText();
/*  92: 85 */     this.theme.getFontRenderer().drawString(text, size + 4, 
/*  93: 86 */       area.height / 2 - this.theme.getFontRenderer().FONT_HEIGHT / 2, 
/*  94: 87 */       RenderUtil.toRGBA(button.getForegroundColor()));
/*  95:    */     
/*  96: 89 */     GL11.glEnable(2884);
/*  97: 90 */     GL11.glDisable(3042);
/*  98: 91 */     translateComponent(button, true);
/*  99:    */   }
/* 100:    */   
/* 101:    */   protected Dimension getDefaultComponentSize(CheckButton component)
/* 102:    */   {
/* 103: 97 */     return new Dimension(this.theme.getFontRenderer().getStringWidth(component.getText()) + this.theme.getFontRenderer().FONT_HEIGHT + 6, this.theme.getFontRenderer().FONT_HEIGHT + 4);
/* 104:    */   }
/* 105:    */   
/* 106:    */   protected Rectangle[] getInteractableComponentRegions(CheckButton component)
/* 107:    */   {
/* 108:104 */     return new Rectangle[] { new Rectangle(0, 0, component.getWidth(), 
/* 109:105 */       component.getHeight()) };
/* 110:    */   }
/* 111:    */   
/* 112:    */   protected void handleComponentInteraction(CheckButton component, Point location, int button)
/* 113:    */   {
/* 114:111 */     if ((location.x <= component.getWidth()) && 
/* 115:112 */       (location.y <= component.getHeight()) && (button == 0)) {
/* 116:113 */       component.press();
/* 117:    */     }
/* 118:    */   }
/* 119:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.darkstorm.minecraft.gui.theme.simple.SimpleCheckButtonUI
 * JD-Core Version:    0.7.0.1
 */