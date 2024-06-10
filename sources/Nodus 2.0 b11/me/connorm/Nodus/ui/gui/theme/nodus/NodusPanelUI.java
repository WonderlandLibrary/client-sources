/*  1:   */ package me.connorm.Nodus.ui.gui.theme.nodus;
/*  2:   */ 
/*  3:   */ import java.awt.Color;
/*  4:   */ import java.awt.Dimension;
/*  5:   */ import java.awt.Rectangle;
/*  6:   */ import org.darkstorm.minecraft.gui.component.Component;
/*  7:   */ import org.darkstorm.minecraft.gui.component.Panel;
/*  8:   */ import org.darkstorm.minecraft.gui.layout.Constraint;
/*  9:   */ import org.darkstorm.minecraft.gui.layout.LayoutManager;
/* 10:   */ import org.darkstorm.minecraft.gui.theme.AbstractComponentUI;
/* 11:   */ import org.darkstorm.minecraft.gui.theme.ComponentUI;
/* 12:   */ import org.darkstorm.minecraft.gui.theme.Theme;
/* 13:   */ import org.darkstorm.minecraft.gui.util.RenderUtil;
/* 14:   */ import org.lwjgl.opengl.GL11;
/* 15:   */ 
/* 16:   */ public class NodusPanelUI
/* 17:   */   extends AbstractComponentUI<Panel>
/* 18:   */ {
/* 19:   */   private final NodusTheme theme;
/* 20:   */   
/* 21:   */   NodusPanelUI(NodusTheme theme)
/* 22:   */   {
/* 23:18 */     super(Panel.class);
/* 24:19 */     this.theme = theme;
/* 25:   */     
/* 26:21 */     this.foreground = Color.WHITE;
/* 27:22 */     this.background = new Color(128, 128, 128, 128);
/* 28:   */   }
/* 29:   */   
/* 30:   */   protected void renderComponent(Panel component)
/* 31:   */   {
/* 32:27 */     if (component.getParent() != null) {
/* 33:28 */       return;
/* 34:   */     }
/* 35:29 */     Rectangle area = component.getArea();
/* 36:30 */     translateComponent(component, false);
/* 37:31 */     GL11.glEnable(3042);
/* 38:32 */     GL11.glDisable(3553);
/* 39:33 */     GL11.glDisable(2884);
/* 40:34 */     GL11.glBlendFunc(770, 771);
/* 41:35 */     RenderUtil.setColor(component.getBackgroundColor());
/* 42:36 */     GL11.glBegin(7);
/* 43:   */     
/* 44:38 */     GL11.glVertex2d(0.0D, 0.0D);
/* 45:39 */     GL11.glVertex2d(area.width, 0.0D);
/* 46:40 */     GL11.glVertex2d(area.width, area.height);
/* 47:41 */     GL11.glVertex2d(0.0D, area.height);
/* 48:   */     
/* 49:43 */     GL11.glEnd();
/* 50:44 */     GL11.glEnable(2884);
/* 51:45 */     GL11.glEnable(3553);
/* 52:46 */     GL11.glDisable(3042);
/* 53:47 */     translateComponent(component, true);
/* 54:   */   }
/* 55:   */   
/* 56:   */   protected Dimension getDefaultComponentSize(Panel component)
/* 57:   */   {
/* 58:52 */     Component[] children = component.getChildren();
/* 59:53 */     Rectangle[] areas = new Rectangle[children.length];
/* 60:54 */     Constraint[][] constraints = new Constraint[children.length][];
/* 61:55 */     for (int i = 0; i < children.length; i++)
/* 62:   */     {
/* 63:56 */       Component child = children[i];
/* 64:57 */       Dimension size = child.getTheme().getUIForComponent(child)
/* 65:58 */         .getDefaultSize(child);
/* 66:59 */       areas[i] = new Rectangle(0, 0, size.width, size.height);
/* 67:60 */       constraints[i] = component.getConstraints(child);
/* 68:   */     }
/* 69:62 */     return component.getLayoutManager().getOptimalPositionedSize(areas, 
/* 70:63 */       constraints);
/* 71:   */   }
/* 72:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.ui.gui.theme.nodus.NodusPanelUI
 * JD-Core Version:    0.7.0.1
 */