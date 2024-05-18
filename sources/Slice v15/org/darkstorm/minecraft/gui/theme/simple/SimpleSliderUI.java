package org.darkstorm.minecraft.gui.theme.simple;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import net.SliceClient.Utils.RenderUtils;
import net.minecraft.client.gui.FontRenderer;
import org.darkstorm.minecraft.gui.component.Component;
import org.darkstorm.minecraft.gui.component.Container;
import org.darkstorm.minecraft.gui.component.Slider;
import org.darkstorm.minecraft.gui.theme.AbstractComponentUI;
import org.darkstorm.minecraft.gui.util.RenderUtil;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;



public class SimpleSliderUI
  extends AbstractComponentUI<Slider>
{
  private SimpleTheme theme;
  
  public SimpleSliderUI(SimpleTheme theme)
  {
    super(Slider.class);
    this.theme = theme;
    
    foreground = Color.LIGHT_GRAY;
    background = new Color(128, 128, 128, 192);
  }
  
  protected void renderComponent(Slider component)
  {
    translateComponent(component, false);
    GL11.glEnable(3042);
    GL11.glDisable(2884);
    Rectangle area = component.getArea();
    int fontSize = theme.getFontRenderer().FONT_HEIGHT;
    FontRenderer fontRenderer = theme.getFontRenderer();
    fontRenderer.drawString(component.getText(), 0, 0, RenderUtil.toRGBA(component.getForegroundColor()));
    String content = null;
    switch (component.getValueDisplay())
    {
    case DECIMAL: 
      content = String.format("%,.2f", new Object[] { Double.valueOf(component.getValue()) });
      break;
    case INTEGER: 
      content = String.format("%,d", new Object[] { Long.valueOf(Math.round(component.getValue())) });
      break;
    case PERCENTAGE: 
      int percent = (int)Math.round((component.getValue() - component.getMinimumValue()) / (
        component.getMaximumValue() - component.getMinimumValue()) * 100.0D);
      content = String.format("%d%%", new Object[] { Integer.valueOf(percent) });
    }
    if (content != null)
    {
      String suffix = component.getContentSuffix();
      if ((suffix != null) && (!suffix.trim().isEmpty())) {
        content = content.concat(" ").concat(suffix);
      }
      fontRenderer.drawString(content, component.getWidth() - fontRenderer.getStringWidth(content), 0, 
        RenderUtil.toRGBA(component.getForegroundColor()));
    }
    GL11.glDisable(3553);
    
    double sliderPercentage = (component.getValue() - component.getMinimumValue()) / (
      component.getMaximumValue() - component.getMinimumValue());
    
    RenderUtils.drawRect((int)(0.0D * sliderPercentage - 1.0D), fontSize + 3, 
      (int)((width - 6) * sliderPercentage + 7.0D), height - 1, new Color(5760649).getRGB());
    RenderUtils.drawRect((int)((width - 6) * sliderPercentage - 1.0D), fontSize + 1, 
      (int)((width - 6) * sliderPercentage + 7.0D), height + 1, new Color(7462584).getRGB());
    
    GL11.glEnable(3553);
    translateComponent(component, true);
  }
  
  protected Dimension getDefaultComponentSize(Slider component)
  {
    return new Dimension(106, 8 + theme.getFontRenderer().FONT_HEIGHT);
  }
  
  protected Rectangle[] getInteractableComponentRegions(Slider component)
  {
    return new Rectangle[] { new Rectangle(0, theme.getFontRenderer().FONT_HEIGHT + 2, component.getWidth(), 
      component.getHeight() - theme.getFontRenderer().FONT_HEIGHT) };
  }
  
  protected void handleComponentInteraction(Slider component, Point location, int button)
  {
    if ((getInteractableComponentRegions(component)[0].contains(location)) && (button == 0)) {
      if ((Mouse.isButtonDown(button)) && (!component.isValueChanging())) {
        component.setValueChanging(true);
      } else if ((!Mouse.isButtonDown(button)) && (component.isValueChanging())) {
        component.setValueChanging(false);
      }
    }
  }
  
  protected void handleComponentUpdate(Slider component)
  {
    if (component.isValueChanging())
    {
      if (!Mouse.isButtonDown(0))
      {
        component.setValueChanging(false);
        return;
      }
      Point mouse = RenderUtil.calculateMouseLocation();
      Container parent = component.getParent();
      if (parent != null) {
        mouse.translate(-parent.getX(), -parent.getY());
      }
      double percent = (x - 4) / (component.getWidth() - 6);
      double value = component.getMinimumValue() + 
        percent * (component.getMaximumValue() - component.getMinimumValue());
      component.setValue(value);
    }
  }
  



  protected void renderComponent(Component component) {}
  


  protected Dimension getDefaultComponentSize(Component component)
  {
    return null;
  }
}
