/*  1:   */ package org.darkstorm.minecraft.gui.theme.simple;
/*  2:   */ 
/*  3:   */ import java.awt.Color;
/*  4:   */ import java.awt.Dimension;
/*  5:   */ import java.awt.Point;
/*  6:   */ import java.awt.Rectangle;
/*  7:   */ import net.minecraft.client.gui.FontRenderer;
/*  8:   */ import org.darkstorm.minecraft.gui.component.Button;
/*  9:   */ import org.darkstorm.minecraft.gui.component.Component;
/* 10:   */ import org.darkstorm.minecraft.gui.theme.AbstractComponentUI;
/* 11:   */ import org.darkstorm.minecraft.gui.util.RenderUtil;
/* 12:   */ import org.lwjgl.input.Mouse;
/* 13:   */ import org.lwjgl.opengl.GL11;
/* 14:   */ 
/* 15:   */ public class SimpleButtonUI
/* 16:   */   extends AbstractComponentUI<Button>
/* 17:   */ {
/* 18:   */   private final SimpleTheme theme;
/* 19:   */   
/* 20:   */   SimpleButtonUI(SimpleTheme theme)
/* 21:   */   {
/* 22:18 */     super(Button.class);
/* 23:19 */     this.theme = theme;
/* 24:   */     
/* 25:21 */     this.foreground = Color.WHITE;
/* 26:22 */     this.background = new Color(128, 128, 128, 192);
/* 27:   */   }
/* 28:   */   
/* 29:   */   protected void renderComponent(Button button)
/* 30:   */   {
/* 31:27 */     translateComponent(button, false);
/* 32:28 */     Rectangle area = button.getArea();
/* 33:29 */     GL11.glEnable(3042);
/* 34:30 */     GL11.glDisable(2884);
/* 35:   */     
/* 36:32 */     GL11.glDisable(3553);
/* 37:33 */     RenderUtil.setColor(button.getBackgroundColor());
/* 38:34 */     GL11.glBegin(7);
/* 39:   */     
/* 40:36 */     GL11.glVertex2d(0.0D, 0.0D);
/* 41:37 */     GL11.glVertex2d(area.width, 0.0D);
/* 42:38 */     GL11.glVertex2d(area.width, area.height);
/* 43:39 */     GL11.glVertex2d(0.0D, area.height);
/* 44:   */     
/* 45:41 */     GL11.glEnd();
/* 46:42 */     Point mouse = RenderUtil.calculateMouseLocation();
/* 47:43 */     Component parent = button.getParent();
/* 48:44 */     while (parent != null)
/* 49:   */     {
/* 50:45 */       mouse.x -= parent.getX();
/* 51:46 */       mouse.y -= parent.getY();
/* 52:47 */       parent = parent.getParent();
/* 53:   */     }
/* 54:49 */     if (area.contains(mouse))
/* 55:   */     {
/* 56:50 */       GL11.glColor4f(0.0F, 0.0F, 0.0F, Mouse.isButtonDown(0) ? 0.5F : 0.3F);
/* 57:51 */       GL11.glBegin(7);
/* 58:   */       
/* 59:53 */       GL11.glVertex2d(0.0D, 0.0D);
/* 60:54 */       GL11.glVertex2d(area.width, 0.0D);
/* 61:55 */       GL11.glVertex2d(area.width, area.height);
/* 62:56 */       GL11.glVertex2d(0.0D, area.height);
/* 63:   */       
/* 64:58 */       GL11.glEnd();
/* 65:   */     }
/* 66:60 */     GL11.glEnable(3553);
/* 67:   */     
/* 68:62 */     String text = button.getText();
/* 69:63 */     this.theme.getFontRenderer().drawString(
/* 70:64 */       text, 
/* 71:65 */       area.width / 2 - this.theme.getFontRenderer().getStringWidth(text) / 
/* 72:66 */       2, 
/* 73:67 */       area.height / 2 - this.theme.getFontRenderer().FONT_HEIGHT / 2, 
/* 74:68 */       RenderUtil.toRGBA(button.getForegroundColor()));
/* 75:   */     
/* 76:70 */     GL11.glEnable(2884);
/* 77:71 */     GL11.glDisable(3042);
/* 78:72 */     translateComponent(button, true);
/* 79:   */   }
/* 80:   */   
/* 81:   */   protected Dimension getDefaultComponentSize(Button component)
/* 82:   */   {
/* 83:78 */     return new Dimension(this.theme.getFontRenderer().getStringWidth(component.getText()) + 4, this.theme.getFontRenderer().FONT_HEIGHT + 4);
/* 84:   */   }
/* 85:   */   
/* 86:   */   protected Rectangle[] getInteractableComponentRegions(Button component)
/* 87:   */   {
/* 88:84 */     return new Rectangle[] { new Rectangle(0, 0, component.getWidth(), 
/* 89:85 */       component.getHeight()) };
/* 90:   */   }
/* 91:   */   
/* 92:   */   protected void handleComponentInteraction(Button component, Point location, int button)
/* 93:   */   {
/* 94:91 */     if ((location.x <= component.getWidth()) && 
/* 95:92 */       (location.y <= component.getHeight()) && (button == 0)) {
/* 96:93 */       component.press();
/* 97:   */     }
/* 98:   */   }
/* 99:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.darkstorm.minecraft.gui.theme.simple.SimpleButtonUI
 * JD-Core Version:    0.7.0.1
 */