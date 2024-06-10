/*   1:    */ package me.connorm.Nodus.ui.gui.theme.nodus;
/*   2:    */ 
/*   3:    */ import java.awt.Color;
/*   4:    */ import java.awt.Dimension;
/*   5:    */ import java.awt.Point;
/*   6:    */ import java.awt.Rectangle;
/*   7:    */ import me.connorm.Nodus.Nodus;
/*   8:    */ import me.connorm.Nodus.ui.UIRenderer;
/*   9:    */ import me.connorm.Nodus.ui.gui.NodusGuiManager;
/*  10:    */ import net.minecraft.client.gui.FontRenderer;
/*  11:    */ import org.darkstorm.minecraft.gui.component.CheckButton;
/*  12:    */ import org.darkstorm.minecraft.gui.component.Component;
/*  13:    */ import org.darkstorm.minecraft.gui.component.Frame;
/*  14:    */ import org.darkstorm.minecraft.gui.theme.AbstractComponentUI;
/*  15:    */ import org.darkstorm.minecraft.gui.util.RenderUtil;
/*  16:    */ import org.lwjgl.opengl.GL11;
/*  17:    */ 
/*  18:    */ public class NodusCheckButtonUI
/*  19:    */   extends AbstractComponentUI<CheckButton>
/*  20:    */ {
/*  21:    */   private final NodusTheme theme;
/*  22:    */   
/*  23:    */   NodusCheckButtonUI(NodusTheme theme)
/*  24:    */   {
/*  25: 27 */     super(CheckButton.class);
/*  26: 28 */     this.theme = theme;
/*  27:    */     
/*  28: 30 */     this.foreground = Color.WHITE;
/*  29: 31 */     this.background = new Color(128, 128, 128, 128);
/*  30:    */   }
/*  31:    */   
/*  32:    */   protected void renderComponent(CheckButton button)
/*  33:    */   {
/*  34: 36 */     translateComponent(button, false);
/*  35: 37 */     Rectangle area = button.getArea();
/*  36: 38 */     GL11.glEnable(3042);
/*  37: 39 */     GL11.glDisable(2884);
/*  38: 40 */     GL11.glDisable(3553);
/*  39:    */     
/*  40: 42 */     int size = area.height - 4;
/*  41: 44 */     if (((button.getIndex() == 1 ? 1 : 0) | (button.getIndex() == 2 ? 1 : 0)) != 0) {
/*  42: 45 */       NodusUtils.drawSmallNodusButton(1.0F, 1.0F, size + 1, size + 1, button.getIndex() == 1 ? ColorUtils.primaryColor : ColorUtils.secondaryColor);
/*  43:    */     }
/*  44: 46 */     if (button.getIndex() == 3) {
/*  45: 47 */       NodusUtils.drawSmallNodusButton(1.0F, 1.0F, size + 1, size + 1, UIRenderer.isTabGui ? -16777216 : -11184811);
/*  46:    */     }
/*  47: 48 */     if (button.getIndex() == 4) {
/*  48: 49 */       NodusUtils.drawSmallNodusButton(1.0F, 1.0F, size + 1, size + 1, Nodus.theNodus.guiManager.isClickGui ? -16777216 : -11184811);
/*  49:    */     }
/*  50: 50 */     if (button.getIndex() == 5) {
/*  51: 51 */       NodusUtils.drawSmallNodusButton(1.0F, 1.0F, size + 1, size + 1, Nodus.theNodus.guiManager.isNewUI ? -16777216 : -11184811);
/*  52:    */     }
/*  53: 52 */     if (button.getIndex() == 6) {
/*  54: 53 */       NodusUtils.drawSmallNodusButton(1.0F, 1.0F, size + 1, size + 1, ColorUtils.chestESPColor);
/*  55:    */     }
/*  56: 54 */     if (button.getIndex() == 7) {
/*  57: 55 */       NodusUtils.drawSmallNodusButton(1.0F, 1.0F, size + 1, size + 1, ColorUtils.aggressiveESPColor);
/*  58:    */     }
/*  59: 56 */     if (button.getIndex() == 8) {
/*  60: 57 */       NodusUtils.drawSmallNodusButton(1.0F, 1.0F, size + 1, size + 1, ColorUtils.passiveESPColor);
/*  61:    */     }
/*  62: 58 */     if (button.getIndex() == 9) {
/*  63: 59 */       NodusUtils.drawSmallNodusButton(1.0F, 1.0F, size + 1, size + 1, ColorUtils.playerESPColor);
/*  64:    */     }
/*  65: 61 */     Point mouse = RenderUtil.calculateMouseLocation();
/*  66: 62 */     Component parent = button.getParent();
/*  67: 63 */     while (parent != null)
/*  68:    */     {
/*  69: 64 */       mouse.x -= parent.getX();
/*  70: 65 */       mouse.y -= parent.getY();
/*  71: 66 */       parent = parent.getParent();
/*  72:    */     }
/*  73: 68 */     GL11.glEnable(3553);
/*  74:    */     
/*  75: 70 */     String text = button.getText();
/*  76: 71 */     this.theme.getFontRenderer().drawString(text, size + 6, 
/*  77: 72 */       area.height / 2 - this.theme.getFontRenderer().FONT_HEIGHT / 2, 
/*  78: 73 */       RenderUtil.toRGBA(button.getForegroundColor()));
/*  79:    */     
/*  80: 75 */     GL11.glEnable(2884);
/*  81: 76 */     GL11.glDisable(3042);
/*  82: 77 */     translateComponent(button, true);
/*  83:    */   }
/*  84:    */   
/*  85:    */   protected Dimension getDefaultComponentSize(CheckButton component)
/*  86:    */   {
/*  87: 83 */     return new Dimension(this.theme.getFontRenderer().getStringWidth(component.getText()) + this.theme.getFontRenderer().FONT_HEIGHT + 6, this.theme.getFontRenderer().FONT_HEIGHT + 4);
/*  88:    */   }
/*  89:    */   
/*  90:    */   protected Rectangle[] getInteractableComponentRegions(CheckButton component)
/*  91:    */   {
/*  92: 90 */     return new Rectangle[] { new Rectangle(0, 0, component.getWidth(), 
/*  93: 91 */       component.getHeight()) };
/*  94:    */   }
/*  95:    */   
/*  96:    */   protected void handleComponentInteraction(CheckButton component, Point location, int button)
/*  97:    */   {
/*  98: 97 */     if ((location.x <= component.getWidth()) && 
/*  99: 98 */       (location.y <= component.getHeight()) && (button == 0)) {
/* 100: 99 */       if (((component.getIndex() == 1 ? 1 : 0) | (component.getIndex() == 2 ? 1 : 0)) != 0) {
/* 101:100 */         ColorUtils.toggleColor(component.getIndex());
/* 102:    */       }
/* 103:    */     }
/* 104:101 */     if (component.getIndex() == 3) {
/* 105:102 */       UIRenderer.isTabGui = !UIRenderer.isTabGui;
/* 106:    */     }
/* 107:103 */     if (component.getIndex() == 4)
/* 108:    */     {
/* 109:105 */       Nodus.theNodus.guiManager.isClickGui = (!Nodus.theNodus.guiManager.isClickGui);
/* 110:106 */       for (Frame clickFrame : Nodus.theNodus.guiManager.getFrames()) {
/* 111:108 */         if (clickFrame.getTitle() != "Options") {
/* 112:110 */           clickFrame.setVisible(!clickFrame.isVisible());
/* 113:    */         }
/* 114:    */       }
/* 115:    */     }
/* 116:113 */     if (component.getIndex() == 5) {
/* 117:115 */       Nodus.theNodus.guiManager.isNewUI = (!Nodus.theNodus.guiManager.isNewUI);
/* 118:    */     }
/* 119:117 */     if (component.getIndex() == 6) {
/* 120:119 */       ColorUtils.toggleColor(3);
/* 121:    */     }
/* 122:121 */     if (component.getIndex() == 7) {
/* 123:123 */       ColorUtils.toggleColor(4);
/* 124:    */     }
/* 125:125 */     if (component.getIndex() == 8) {
/* 126:127 */       ColorUtils.toggleColor(5);
/* 127:    */     }
/* 128:129 */     if (component.getIndex() == 9) {
/* 129:131 */       ColorUtils.toggleColor(6);
/* 130:    */     }
/* 131:    */   }
/* 132:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.ui.gui.theme.nodus.NodusCheckButtonUI
 * JD-Core Version:    0.7.0.1
 */