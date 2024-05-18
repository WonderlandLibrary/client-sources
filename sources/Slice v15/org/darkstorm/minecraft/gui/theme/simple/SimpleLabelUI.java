package org.darkstorm.minecraft.gui.theme.simple;

import java.awt.Color;
import java.awt.Dimension;
import net.minecraft.client.gui.FontRenderer;
import org.darkstorm.minecraft.gui.component.Component;
import org.darkstorm.minecraft.gui.component.Label;
import org.darkstorm.minecraft.gui.theme.AbstractComponentUI;
import org.darkstorm.minecraft.gui.util.RenderUtil;
import org.lwjgl.opengl.GL11;


public class SimpleLabelUI
  extends AbstractComponentUI<Label>
{
  private final SimpleTheme theme;
  
  SimpleLabelUI(SimpleTheme theme)
  {
    super(Label.class);
    this.theme = theme;
    
    foreground = Color.WHITE;
    background = new Color(128, 128, 128, 128);
  }
  
  protected void renderComponent(Label label)
  {
    translateComponent(label, false);
    int x = 0;int y = 0;
    switch (label.getHorizontalAlignment())
    {

    case TOP: 
      x = x + (label.getWidth() / 2 - theme.getFontRenderer().getStringWidth(label.getText()) / 2);
      break;
    case CENTER: 
      x += label.getWidth() - theme.getFontRenderer().getStringWidth(label.getText()) - 2;
      break;
    case BOTTOM: case LEFT: 
    case RIGHT: default: 
      x += 2; }
    
    switch (label.getVerticalAlignment())
    {
    case LEFT: 
      y += 2;
      break;
    case RIGHT: 
      y += label.getHeight() - theme.getFontRenderer().FONT_HEIGHT - 2;
      break;
    default: 
      y += label.getHeight() / 2 - theme.getFontRenderer().FONT_HEIGHT / 2;
    }
    GL11.glEnable(3042);
    GL11.glEnable(3553);
    GL11.glDisable(2884);
    theme.getFontRenderer().drawString(label.getText(), x, y, 
      RenderUtil.toRGBA(label.getForegroundColor()));
    GL11.glEnable(2884);
    GL11.glEnable(3553);
    GL11.glDisable(3042);
    translateComponent(label, true);
  }
  
  protected Dimension getDefaultComponentSize(Label component)
  {
    return new Dimension(theme.getFontRenderer().getStringWidth(component.getText()) + 4, theme.getFontRenderer().FONT_HEIGHT + 4);
  }
  



  protected void renderComponent(Component component) {}
  


  protected Dimension getDefaultComponentSize(Component component)
  {
    return null;
  }
}
