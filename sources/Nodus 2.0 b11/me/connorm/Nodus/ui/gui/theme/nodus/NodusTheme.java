/*  1:   */ package me.connorm.Nodus.ui.gui.theme.nodus;
/*  2:   */ 
/*  3:   */ import me.connorm.Nodus.Nodus;
/*  4:   */ import net.minecraft.client.Minecraft;
/*  5:   */ import net.minecraft.client.gui.FontRenderer;
/*  6:   */ import org.darkstorm.minecraft.gui.theme.AbstractTheme;
/*  7:   */ 
/*  8:   */ public class NodusTheme
/*  9:   */   extends AbstractTheme
/* 10:   */ {
/* 11:   */   private final FontRenderer fontRenderer;
/* 12:   */   
/* 13:   */   public NodusTheme()
/* 14:   */   {
/* 15:13 */     this.fontRenderer = Nodus.theNodus.getMinecraft().fontRenderer;
/* 16:   */     
/* 17:15 */     installUI(new NodusFrameUI(this));
/* 18:16 */     installUI(new NodusPanelUI(this));
/* 19:17 */     installUI(new NodusLabelUI(this));
/* 20:18 */     installUI(new NodusButtonUI(this));
/* 21:19 */     installUI(new NodusComboBoxUI(this));
/* 22:20 */     installUI(new NodusSliderUI(this));
/* 23:21 */     installUI(new NodusProgressBarUI(this));
/* 24:22 */     installUI(new NodusCheckButtonUI(this));
/* 25:   */   }
/* 26:   */   
/* 27:   */   public FontRenderer getFontRenderer()
/* 28:   */   {
/* 29:28 */     return this.fontRenderer;
/* 30:   */   }
/* 31:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.ui.gui.theme.nodus.NodusTheme
 * JD-Core Version:    0.7.0.1
 */