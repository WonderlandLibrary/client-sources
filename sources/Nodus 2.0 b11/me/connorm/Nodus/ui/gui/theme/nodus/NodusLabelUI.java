/*  1:   */ package me.connorm.Nodus.ui.gui.theme.nodus;
/*  2:   */ 
/*  3:   */ import java.awt.Color;
/*  4:   */ import java.awt.Dimension;
/*  5:   */ import net.minecraft.client.gui.FontRenderer;
/*  6:   */ import org.darkstorm.minecraft.gui.component.Label;
/*  7:   */ import org.darkstorm.minecraft.gui.theme.AbstractComponentUI;
/*  8:   */ import org.darkstorm.minecraft.gui.util.RenderUtil;
/*  9:   */ import org.lwjgl.opengl.GL11;
/* 10:   */ 
/* 11:   */ public class NodusLabelUI
/* 12:   */   extends AbstractComponentUI<Label>
/* 13:   */ {
/* 14:   */   private final NodusTheme theme;
/* 15:   */   
/* 16:   */   NodusLabelUI(NodusTheme theme)
/* 17:   */   {
/* 18:15 */     super(Label.class);
/* 19:16 */     this.theme = theme;
/* 20:   */     
/* 21:18 */     this.foreground = Color.WHITE;
/* 22:19 */     this.background = new Color(128, 128, 128, 128);
/* 23:   */   }
/* 24:   */   
/* 25:   */   protected void renderComponent(Label label)
/* 26:   */   {
/* 27:24 */     translateComponent(label, false);
/* 28:25 */     int x = 0;int y = 0;
/* 29:26 */     switch (label.getHorizontalAlignment())
/* 30:   */     {
/* 31:   */     case BOTTOM: 
/* 32:29 */       x = x + (label.getWidth() / 2 - this.theme.getFontRenderer().getStringWidth(label.getText()) / 
/* 33:30 */         2);
/* 34:31 */       break;
/* 35:   */     case LEFT: 
/* 36:35 */       x = x + (label.getWidth() - this.theme.getFontRenderer().getStringWidth(label.getText()) - 2);
/* 37:36 */       break;
/* 38:   */     case CENTER: 
/* 39:   */     default: 
/* 40:38 */       x += 2;
/* 41:   */     }
/* 42:40 */     switch (label.getVerticalAlignment())
/* 43:   */     {
/* 44:   */     case RIGHT: 
/* 45:42 */       y += 2;
/* 46:43 */       break;
/* 47:   */     case TOP: 
/* 48:45 */       y += label.getHeight() - this.theme.getFontRenderer().FONT_HEIGHT - 2;
/* 49:46 */       break;
/* 50:   */     default: 
/* 51:49 */       y = y + (label.getHeight() / 2 - this.theme.getFontRenderer().FONT_HEIGHT / 2);
/* 52:   */     }
/* 53:51 */     GL11.glEnable(3042);
/* 54:52 */     GL11.glEnable(3553);
/* 55:53 */     GL11.glDisable(2884);
/* 56:54 */     this.theme.getFontRenderer().drawString(label.getText(), x, y, 
/* 57:55 */       RenderUtil.toRGBA(label.getForegroundColor()));
/* 58:56 */     GL11.glEnable(2884);
/* 59:57 */     GL11.glEnable(3553);
/* 60:58 */     GL11.glDisable(3042);
/* 61:59 */     translateComponent(label, true);
/* 62:   */   }
/* 63:   */   
/* 64:   */   protected Dimension getDefaultComponentSize(Label component)
/* 65:   */   {
/* 66:65 */     return new Dimension(this.theme.getFontRenderer().getStringWidth(component.getText()) + 4, this.theme.getFontRenderer().FONT_HEIGHT + 4);
/* 67:   */   }
/* 68:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.ui.gui.theme.nodus.NodusLabelUI
 * JD-Core Version:    0.7.0.1
 */