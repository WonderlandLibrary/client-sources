/*   1:    */ package me.connorm.Nodus.ui.gui.theme.nodus;
/*   2:    */ 
/*   3:    */ import java.awt.Color;
/*   4:    */ import java.awt.Dimension;
/*   5:    */ import java.awt.Rectangle;
/*   6:    */ import net.minecraft.client.gui.FontRenderer;
/*   7:    */ import org.darkstorm.minecraft.gui.component.ProgressBar;
/*   8:    */ import org.darkstorm.minecraft.gui.theme.AbstractComponentUI;
/*   9:    */ import org.darkstorm.minecraft.gui.util.RenderUtil;
/*  10:    */ import org.lwjgl.opengl.GL11;
/*  11:    */ 
/*  12:    */ public class NodusProgressBarUI
/*  13:    */   extends AbstractComponentUI<ProgressBar>
/*  14:    */ {
/*  15:    */   private NodusTheme theme;
/*  16:    */   
/*  17:    */   public NodusProgressBarUI(NodusTheme theme)
/*  18:    */   {
/*  19: 34 */     super(ProgressBar.class);
/*  20: 35 */     this.theme = theme;
/*  21:    */     
/*  22: 37 */     this.foreground = Color.LIGHT_GRAY;
/*  23: 38 */     this.background = new Color(128, 128, 128, 192);
/*  24:    */   }
/*  25:    */   
/*  26:    */   protected void renderComponent(ProgressBar component)
/*  27:    */   {
/*  28: 43 */     Rectangle area = component.getArea();
/*  29: 44 */     int fontSize = this.theme.getFontRenderer().FONT_HEIGHT;
/*  30: 45 */     FontRenderer fontRenderer = this.theme.getFontRenderer();
/*  31:    */     
/*  32: 47 */     translateComponent(component, false);
/*  33: 48 */     GL11.glEnable(3042);
/*  34: 49 */     GL11.glDisable(2884);
/*  35: 50 */     GL11.glDisable(3553);
/*  36:    */     
/*  37: 52 */     RenderUtil.setColor(component.getBackgroundColor());
/*  38: 53 */     GL11.glLineWidth(0.9F);
/*  39: 54 */     GL11.glBegin(2);
/*  40:    */     
/*  41: 56 */     GL11.glVertex2d(0.0D, 0.0D);
/*  42: 57 */     GL11.glVertex2d(area.width, 0.0D);
/*  43: 58 */     GL11.glVertex2d(area.width, area.height);
/*  44: 59 */     GL11.glVertex2d(0.0D, area.height);
/*  45:    */     
/*  46: 61 */     GL11.glEnd();
/*  47:    */     
/*  48: 63 */     double barPercentage = (component.getValue() - component.getMinimumValue()) / (component.getMaximumValue() - component.getMinimumValue());
/*  49: 64 */     RenderUtil.setColor(component.getForegroundColor());
/*  50: 65 */     GL11.glBegin(7);
/*  51:    */     
/*  52: 67 */     GL11.glVertex2d(0.0D, 0.0D);
/*  53: 68 */     GL11.glVertex2d(area.width * barPercentage, 0.0D);
/*  54: 69 */     GL11.glVertex2d(area.width * barPercentage, area.height);
/*  55: 70 */     GL11.glVertex2d(0.0D, area.height);
/*  56:    */     
/*  57: 72 */     GL11.glEnd();
/*  58:    */     
/*  59: 74 */     GL11.glEnable(3553);
/*  60: 75 */     String content = null;
/*  61: 76 */     switch (component.getValueDisplay())
/*  62:    */     {
/*  63:    */     case DECIMAL: 
/*  64: 78 */       content = String.format("%,.3f", new Object[] { Double.valueOf(component.getValue()) });
/*  65: 79 */       break;
/*  66:    */     case INTEGER: 
/*  67: 81 */       content = String.format("%,d", new Object[] { Long.valueOf(Math.round(component.getValue())) });
/*  68: 82 */       break;
/*  69:    */     case NONE: 
/*  70: 84 */       int percent = (int)Math.round((component.getValue() - component.getMinimumValue()) / (component.getMaximumValue() - component.getMinimumValue()) * 100.0D);
/*  71: 85 */       content = String.format("%d%%", new Object[] { Integer.valueOf(percent) });
/*  72:    */     }
/*  73: 88 */     if (content != null)
/*  74:    */     {
/*  75: 89 */       GL11.glBlendFunc(775, 769);
/*  76: 90 */       fontRenderer.drawString(content, component.getWidth() / 2 - fontRenderer.getStringWidth(content) / 2, component.getHeight() / 2 - fontSize / 2, RenderUtil.toRGBA(component.getForegroundColor()));
/*  77: 91 */       GL11.glBlendFunc(770, 771);
/*  78:    */     }
/*  79: 93 */     GL11.glEnable(2884);
/*  80: 94 */     GL11.glDisable(3042);
/*  81: 95 */     translateComponent(component, true);
/*  82:    */   }
/*  83:    */   
/*  84:    */   protected Dimension getDefaultComponentSize(ProgressBar component)
/*  85:    */   {
/*  86:100 */     return new Dimension(100, 4 + this.theme.getFontRenderer().FONT_HEIGHT);
/*  87:    */   }
/*  88:    */   
/*  89:    */   protected Rectangle[] getInteractableComponentRegions(ProgressBar component)
/*  90:    */   {
/*  91:105 */     return new Rectangle[0];
/*  92:    */   }
/*  93:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.ui.gui.theme.nodus.NodusProgressBarUI
 * JD-Core Version:    0.7.0.1
 */