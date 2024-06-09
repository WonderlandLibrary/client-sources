package org.darkstorm.minecraft.gui.theme.mercury;

import java.awt.Color;
import java.awt.Dimension;
import net.minecraft.client.gui.FontRenderer;
import org.darkstorm.minecraft.gui.component.Label;
import org.lwjgl.opengl.GL11;

public class SimpleLabelUI extends org.darkstorm.minecraft.gui.theme.AbstractComponentUI<Label>
{
  private final MercuryTheme theme;
  
  SimpleLabelUI(MercuryTheme theme)
  {
    super(Label.class);
    this.theme = theme;
    
    foreground = Color.WHITE;
    background = new Color(128, 128, 128, 192);
  }
  


  protected void renderComponent(Label label)
  {
    background = new Color(128, 128, 128, 192);
    translateComponent(label, false);
    int x = 0;int y = 0;
    switch (label.getHorizontalAlignment())
    {
    case BOTTOM: 
      x = x + (label.getWidth() / 2 - theme.getFontRenderer().getStringWidth(label.getText()) / 
        2);
      break;
    

    case LEFT: 
      x = x + (label.getWidth() - theme.getFontRenderer().getStringWidth(label.getText()) - 2);
      break;
    case CENTER: default: 
      x += 2;
    }
    switch (label.getVerticalAlignment()) {
    case RIGHT: 
      y += 2;
      break;
    case TOP: 
      y += label.getHeight() - theme.getFontRenderer().FONT_HEIGHT - 2;
      break;
    
    default: 
      y = y + (label.getHeight() / 2 - theme.getFontRenderer().FONT_HEIGHT / 2);
    }
    GL11.glEnable(3042);
    GL11.glEnable(3553);
    GL11.glDisable(2884);
    theme.getFontRenderer().drawString(label.getText(), x, y, 
      org.darkstorm.minecraft.gui.util.RenderUtil.toRGBA(label.getForegroundColor()));
    GL11.glEnable(2884);
    GL11.glEnable(3553);
    GL11.glDisable(3042);
    translateComponent(label, true);
  }
  

  protected Dimension getDefaultComponentSize(Label component)
  {
    return new Dimension(theme.getFontRenderer().getStringWidth(component.getText()) + 4, theme.getFontRenderer().FONT_HEIGHT + 4);
  }
}
