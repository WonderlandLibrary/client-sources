/*  1:   */ package org.darkstorm.minecraft.gui.theme.simple;
/*  2:   */ 
/*  3:   */ import java.awt.Font;
/*  4:   */ import me.connorm.Nodus.font.UnicodeFontRenderer;
/*  5:   */ import net.minecraft.client.gui.FontRenderer;
/*  6:   */ import org.darkstorm.minecraft.gui.theme.AbstractTheme;
/*  7:   */ 
/*  8:   */ public class SimpleTheme
/*  9:   */   extends AbstractTheme
/* 10:   */ {
/* 11:   */   private final FontRenderer fontRenderer;
/* 12:   */   
/* 13:   */   public SimpleTheme()
/* 14:   */   {
/* 15:14 */     this.fontRenderer = new UnicodeFontRenderer(new Font("Trebuchet MS", 0, 15));
/* 16:   */     
/* 17:16 */     installUI(new SimpleFrameUI(this));
/* 18:17 */     installUI(new SimplePanelUI(this));
/* 19:18 */     installUI(new SimpleLabelUI(this));
/* 20:19 */     installUI(new SimpleButtonUI(this));
/* 21:20 */     installUI(new SimpleCheckButtonUI(this));
/* 22:21 */     installUI(new SimpleComboBoxUI(this));
/* 23:22 */     installUI(new SimpleSliderUI(this));
/* 24:23 */     installUI(new SimpleProgressBarUI(this));
/* 25:   */   }
/* 26:   */   
/* 27:   */   public FontRenderer getFontRenderer()
/* 28:   */   {
/* 29:27 */     return this.fontRenderer;
/* 30:   */   }
/* 31:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.darkstorm.minecraft.gui.theme.simple.SimpleTheme
 * JD-Core Version:    0.7.0.1
 */