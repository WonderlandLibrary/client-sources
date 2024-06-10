/*  1:   */ package me.connorm.Nodus.ui.gui.theme.nodus;
/*  2:   */ 
/*  3:   */ import java.awt.Color;
/*  4:   */ import java.awt.Dimension;
/*  5:   */ import java.awt.Point;
/*  6:   */ import java.awt.Rectangle;
/*  7:   */ import me.connorm.Nodus.utils.RenderUtils;
/*  8:   */ import net.minecraft.client.gui.FontRenderer;
/*  9:   */ import org.darkstorm.minecraft.gui.component.Button;
/* 10:   */ import org.darkstorm.minecraft.gui.component.Component;
/* 11:   */ import org.darkstorm.minecraft.gui.theme.AbstractComponentUI;
/* 12:   */ import org.darkstorm.minecraft.gui.util.RenderUtil;
/* 13:   */ import org.lwjgl.opengl.GL11;
/* 14:   */ 
/* 15:   */ public class NodusOptionButtonUI
/* 16:   */   extends AbstractComponentUI<Button>
/* 17:   */ {
/* 18:   */   private final NodusTheme theme;
/* 19:   */   private boolean shouldShade;
/* 20:   */   
/* 21:   */   NodusOptionButtonUI(NodusTheme theme)
/* 22:   */   {
/* 23:29 */     super(Button.class);
/* 24:30 */     this.theme = theme;
/* 25:   */     
/* 26:32 */     this.foreground = Color.gray;
/* 27:33 */     this.background = new Color(128, 128, 128, 192);
/* 28:   */   }
/* 29:   */   
/* 30:   */   protected void renderComponent(Button button)
/* 31:   */   {
/* 32:38 */     translateComponent(button, false);
/* 33:39 */     Rectangle area = button.getArea();
/* 34:   */     
/* 35:41 */     Point mouse = RenderUtil.calculateMouseLocation();
/* 36:42 */     Component parent = button.getParent();
/* 37:44 */     while (parent != null)
/* 38:   */     {
/* 39:46 */       mouse.x -= parent.getX();
/* 40:47 */       mouse.y -= parent.getY();
/* 41:48 */       parent = parent.getParent();
/* 42:   */     }
/* 43:51 */     GL11.glEnable(3553);
/* 44:   */     
/* 45:53 */     this.theme.getFontRenderer().drawString(button.getText(), area.width / 2 - this.theme.getFontRenderer().getStringWidth(button.getText()) / 2, area.height / 2 - this.theme.getFontRenderer().FONT_HEIGHT / 2, 16777215);
/* 46:   */     
/* 47:55 */     RenderUtils.drawRect(0.0F, 0, area.width - 1, area.height - 1, -1879048192);
/* 48:   */     
/* 49:57 */     GL11.glEnable(2884);
/* 50:58 */     GL11.glDisable(3042);
/* 51:59 */     translateComponent(button, true);
/* 52:   */   }
/* 53:   */   
/* 54:   */   protected Dimension getDefaultComponentSize(Button component)
/* 55:   */   {
/* 56:65 */     return new Dimension(this.theme.getFontRenderer().getStringWidth(component.getText()) + 4, this.theme.getFontRenderer().FONT_HEIGHT + 4);
/* 57:   */   }
/* 58:   */   
/* 59:   */   protected Rectangle[] getInteractableComponentRegions(Button component)
/* 60:   */   {
/* 61:71 */     return new Rectangle[] { new Rectangle(0, 0, component.getWidth(), 
/* 62:72 */       component.getHeight()) };
/* 63:   */   }
/* 64:   */   
/* 65:   */   protected void handleComponentInteraction(Button component, Point location, int button)
/* 66:   */   {
/* 67:78 */     if ((location.x <= component.getWidth()) && 
/* 68:79 */       (location.y <= component.getHeight()) && (button == 0)) {
/* 69:80 */       component.press();
/* 70:   */     }
/* 71:   */   }
/* 72:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.ui.gui.theme.nodus.NodusOptionButtonUI
 * JD-Core Version:    0.7.0.1
 */