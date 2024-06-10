/*   1:    */ package me.connorm.Nodus.ui.gui.theme.nodus;
/*   2:    */ 
/*   3:    */ import java.awt.Color;
/*   4:    */ import java.awt.Dimension;
/*   5:    */ import java.awt.Point;
/*   6:    */ import java.awt.Rectangle;
/*   7:    */ import me.connorm.Nodus.Nodus;
/*   8:    */ import me.connorm.Nodus.ui.gui.NodusGuiManager;
/*   9:    */ import net.minecraft.client.gui.FontRenderer;
/*  10:    */ import org.darkstorm.minecraft.gui.component.Button;
/*  11:    */ import org.darkstorm.minecraft.gui.component.Component;
/*  12:    */ import org.darkstorm.minecraft.gui.theme.AbstractComponentUI;
/*  13:    */ import org.darkstorm.minecraft.gui.util.RenderUtil;
/*  14:    */ import org.lwjgl.opengl.GL11;
/*  15:    */ 
/*  16:    */ public class NodusButtonUI
/*  17:    */   extends AbstractComponentUI<Button>
/*  18:    */ {
/*  19:    */   private final NodusTheme theme;
/*  20:    */   private boolean shouldShade;
/*  21:    */   
/*  22:    */   NodusButtonUI(NodusTheme theme)
/*  23:    */   {
/*  24: 37 */     super(Button.class);
/*  25: 38 */     this.theme = theme;
/*  26:    */     
/*  27: 40 */     this.foreground = Color.gray;
/*  28: 41 */     this.background = new Color(128, 128, 128, 192);
/*  29:    */   }
/*  30:    */   
/*  31:    */   protected void renderComponent(Button button)
/*  32:    */   {
/*  33: 47 */     translateComponent(button, false);
/*  34: 48 */     Rectangle area = button.getArea();
/*  35:    */     
/*  36: 50 */     Point mouse = RenderUtil.calculateMouseLocation();
/*  37: 51 */     Component parent = button.getParent();
/*  38: 53 */     while (parent != null)
/*  39:    */     {
/*  40: 55 */       mouse.x -= parent.getX();
/*  41: 56 */       mouse.y -= parent.getY();
/*  42: 57 */       parent = parent.getParent();
/*  43:    */     }
/*  44: 60 */     GL11.glEnable(3553);
/*  45: 62 */     if (!Nodus.theNodus.guiManager.isNewUI) {
/*  46: 63 */       this.theme.getFontRenderer().drawStringWithShadow(button.getText(), area.width / 2 - this.theme.getFontRenderer().getStringWidth(button.getText()) / 2, area.height / 2 - this.theme.getFontRenderer().FONT_HEIGHT / 2, ColorUtils.primaryColor);
/*  47:    */     }
/*  48: 65 */     if (area.contains(mouse)) {
/*  49: 67 */       if (Nodus.theNodus.guiManager.isNewUI) {
/*  50: 69 */         NodusUtils.drawSmallNodusButton(1.0F, 1.0F, area.width - 1, area.height - 2, ColorUtils.secondaryColor);
/*  51:    */       } else {
/*  52: 72 */         this.theme.getFontRenderer().drawStringWithShadow(button.getText(), area.width / 2 - this.theme.getFontRenderer().getStringWidth(button.getText()) / 2, area.height / 2 - this.theme.getFontRenderer().FONT_HEIGHT / 2, ColorUtils.secondaryColor);
/*  53:    */       }
/*  54:    */     }
/*  55: 75 */     if (Nodus.theNodus.guiManager.isNewUI) {
/*  56: 76 */       this.theme.getFontRenderer().drawStringWithShadow(button.getText(), area.width / 2 - this.theme.getFontRenderer().getStringWidth(button.getText()) / 2, area.height / 2 - this.theme.getFontRenderer().FONT_HEIGHT / 2, ColorUtils.primaryColor);
/*  57:    */     }
/*  58: 78 */     GL11.glEnable(2884);
/*  59: 79 */     GL11.glDisable(3042);
/*  60: 80 */     translateComponent(button, true);
/*  61:    */   }
/*  62:    */   
/*  63:    */   protected Dimension getDefaultComponentSize(Button component)
/*  64:    */   {
/*  65: 86 */     return new Dimension(this.theme.getFontRenderer().getStringWidth(component.getText()) + 4, this.theme.getFontRenderer().FONT_HEIGHT + 4);
/*  66:    */   }
/*  67:    */   
/*  68:    */   protected Rectangle[] getInteractableComponentRegions(Button component)
/*  69:    */   {
/*  70: 92 */     return new Rectangle[] { new Rectangle(0, 0, component.getWidth(), 
/*  71: 93 */       component.getHeight()) };
/*  72:    */   }
/*  73:    */   
/*  74:    */   protected void handleComponentInteraction(Button component, Point location, int button)
/*  75:    */   {
/*  76: 99 */     if ((location.x <= component.getWidth()) && 
/*  77:100 */       (location.y <= component.getHeight()) && (button == 0)) {
/*  78:101 */       component.press();
/*  79:    */     }
/*  80:    */   }
/*  81:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.ui.gui.theme.nodus.NodusButtonUI
 * JD-Core Version:    0.7.0.1
 */