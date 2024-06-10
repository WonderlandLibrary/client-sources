/*   1:    */ package me.connorm.Nodus.ui.gui.theme.nodus;
/*   2:    */ 
/*   3:    */ import java.awt.Color;
/*   4:    */ import java.awt.Dimension;
/*   5:    */ import java.awt.Point;
/*   6:    */ import java.awt.Rectangle;
/*   7:    */ import net.minecraft.client.gui.FontRenderer;
/*   8:    */ import org.darkstorm.minecraft.gui.component.Container;
/*   9:    */ import org.darkstorm.minecraft.gui.component.Slider;
/*  10:    */ import org.darkstorm.minecraft.gui.theme.AbstractComponentUI;
/*  11:    */ import org.darkstorm.minecraft.gui.util.RenderUtil;
/*  12:    */ import org.lwjgl.input.Mouse;
/*  13:    */ import org.lwjgl.opengl.GL11;
/*  14:    */ 
/*  15:    */ public class NodusSliderUI
/*  16:    */   extends AbstractComponentUI<Slider>
/*  17:    */ {
/*  18:    */   private NodusTheme theme;
/*  19:    */   
/*  20:    */   public NodusSliderUI(NodusTheme theme)
/*  21:    */   {
/*  22: 32 */     super(Slider.class);
/*  23: 33 */     this.theme = theme;
/*  24:    */     
/*  25: 35 */     this.foreground = Color.LIGHT_GRAY;
/*  26: 36 */     this.background = new Color(128, 128, 128, 192);
/*  27:    */   }
/*  28:    */   
/*  29:    */   protected void renderComponent(Slider component)
/*  30:    */   {
/*  31: 41 */     translateComponent(component, false);
/*  32: 42 */     GL11.glEnable(3042);
/*  33: 43 */     GL11.glDisable(2884);
/*  34: 44 */     Rectangle area = component.getArea();
/*  35: 45 */     int fontSize = this.theme.getFontRenderer().FONT_HEIGHT;
/*  36: 46 */     FontRenderer fontRenderer = this.theme.getFontRenderer();
/*  37: 47 */     fontRenderer.drawString(component.getText(), 0, 0, RenderUtil.toRGBA(component.getForegroundColor()));
/*  38: 48 */     String content = null;
/*  39: 49 */     switch (component.getValueDisplay())
/*  40:    */     {
/*  41:    */     case DECIMAL: 
/*  42: 51 */       content = String.format("%,.3f", new Object[] { Double.valueOf(component.getValue()) });
/*  43: 52 */       break;
/*  44:    */     case INTEGER: 
/*  45: 54 */       content = String.format("%,d", new Object[] { Long.valueOf(Math.round(component.getValue())) });
/*  46: 55 */       break;
/*  47:    */     case NONE: 
/*  48: 57 */       int percent = (int)Math.round((component.getValue() - component.getMinimumValue()) / (component.getMaximumValue() - component.getMinimumValue()) * 100.0D);
/*  49: 58 */       content = String.format("%d%%", new Object[] { Integer.valueOf(percent) });
/*  50:    */     }
/*  51: 61 */     if (content != null)
/*  52:    */     {
/*  53: 62 */       String suffix = component.getContentSuffix();
/*  54: 63 */       if ((suffix != null) && (!suffix.trim().isEmpty())) {
/*  55: 64 */         content = content.concat(" ").concat(suffix);
/*  56:    */       }
/*  57: 65 */       fontRenderer.drawString(content, component.getWidth() - fontRenderer.getStringWidth(content), 0, RenderUtil.toRGBA(component.getForegroundColor()));
/*  58:    */     }
/*  59: 67 */     GL11.glDisable(3553);
/*  60:    */     
/*  61: 69 */     RenderUtil.setColor(component.getBackgroundColor());
/*  62: 70 */     GL11.glLineWidth(0.9F);
/*  63: 71 */     GL11.glBegin(2);
/*  64:    */     
/*  65: 73 */     GL11.glVertex2d(0.0D, fontSize + 2.0D);
/*  66: 74 */     GL11.glVertex2d(area.width, fontSize + 2.0D);
/*  67: 75 */     GL11.glVertex2d(area.width, area.height);
/*  68: 76 */     GL11.glVertex2d(0.0D, area.height);
/*  69:    */     
/*  70: 78 */     GL11.glEnd();
/*  71:    */     
/*  72: 80 */     double sliderPercentage = (component.getValue() - component.getMinimumValue()) / (component.getMaximumValue() - component.getMinimumValue());
/*  73: 81 */     RenderUtil.setColor(component.getForegroundColor());
/*  74: 82 */     GL11.glBegin(7);
/*  75:    */     
/*  76: 84 */     GL11.glVertex2d(0.0D, fontSize + 2.0D);
/*  77: 85 */     GL11.glVertex2d(area.width * sliderPercentage, fontSize + 2.0D);
/*  78: 86 */     GL11.glVertex2d(area.width * sliderPercentage, area.height);
/*  79: 87 */     GL11.glVertex2d(0.0D, area.height);
/*  80:    */     
/*  81: 89 */     GL11.glEnd();
/*  82:    */     
/*  83: 91 */     GL11.glEnable(3553);
/*  84: 92 */     translateComponent(component, true);
/*  85:    */   }
/*  86:    */   
/*  87:    */   protected Dimension getDefaultComponentSize(Slider component)
/*  88:    */   {
/*  89: 97 */     return new Dimension(100, 8 + this.theme.getFontRenderer().FONT_HEIGHT);
/*  90:    */   }
/*  91:    */   
/*  92:    */   protected Rectangle[] getInteractableComponentRegions(Slider component)
/*  93:    */   {
/*  94:102 */     return new Rectangle[] { new Rectangle(0, this.theme.getFontRenderer().FONT_HEIGHT + 2, component.getWidth(), component.getHeight() - this.theme.getFontRenderer().FONT_HEIGHT) };
/*  95:    */   }
/*  96:    */   
/*  97:    */   protected void handleComponentInteraction(Slider component, Point location, int button)
/*  98:    */   {
/*  99:107 */     if ((getInteractableComponentRegions(component)[0].contains(location)) && (button == 0)) {
/* 100:108 */       if ((Mouse.isButtonDown(button)) && (!component.isValueChanging())) {
/* 101:109 */         component.setValueChanging(true);
/* 102:110 */       } else if ((!Mouse.isButtonDown(button)) && (component.isValueChanging())) {
/* 103:111 */         component.setValueChanging(false);
/* 104:    */       }
/* 105:    */     }
/* 106:    */   }
/* 107:    */   
/* 108:    */   protected void handleComponentUpdate(Slider component)
/* 109:    */   {
/* 110:116 */     if (component.isValueChanging())
/* 111:    */     {
/* 112:117 */       if (!Mouse.isButtonDown(0))
/* 113:    */       {
/* 114:118 */         component.setValueChanging(false);
/* 115:119 */         return;
/* 116:    */       }
/* 117:121 */       Point mouse = RenderUtil.calculateMouseLocation();
/* 118:122 */       Container parent = component.getParent();
/* 119:123 */       if (parent != null) {
/* 120:124 */         mouse.translate(-parent.getX(), -parent.getY());
/* 121:    */       }
/* 122:125 */       double percent = mouse.x / component.getWidth();
/* 123:126 */       double value = component.getMinimumValue() + percent * (component.getMaximumValue() - component.getMinimumValue());
/* 124:127 */       component.setValue(value);
/* 125:    */     }
/* 126:    */   }
/* 127:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.ui.gui.theme.nodus.NodusSliderUI
 * JD-Core Version:    0.7.0.1
 */