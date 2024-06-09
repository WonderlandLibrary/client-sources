package org.darkstorm.minecraft.gui.theme.mercury;

import java.awt.Font;
import net.minecraft.client.gui.FontRenderer;
import org.darkstorm.minecraft.gui.font.UnicodeFontRenderer;
import org.darkstorm.minecraft.gui.theme.AbstractTheme;


public class MercuryTheme
  extends AbstractTheme
{
  private final FontRenderer fontRenderer;
  
  public MercuryTheme()
  {
    fontRenderer = new UnicodeFontRenderer(new Font("Vendetta", 1, 15));
    
    installUI(new SimpleFrameUI(this));
    installUI(new SimplePanelUI(this));
    installUI(new SimpleLabelUI(this));
    installUI(new SimpleButtonUI(this));
    installUI(new SimpleCheckButtonUI(this));
    installUI(new SimpleComboBoxUI(this));
    installUI(new SimpleSliderUI(this));
    installUI(new SimpleProgressBarUI(this));
  }
  
  public FontRenderer getFontRenderer() {
    return fontRenderer;
  }
}
