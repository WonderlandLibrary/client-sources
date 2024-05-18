package org.darkstorm.minecraft.gui.theme.mercury;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import net.minecraft.client.gui.FontRenderer;
import org.darkstorm.minecraft.gui.component.Container;
import org.darkstorm.minecraft.gui.component.Slider;
import org.darkstorm.minecraft.gui.util.RenderUtil;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class SimpleSliderUI extends org.darkstorm.minecraft.gui.theme.AbstractComponentUI<Slider>
{
  private MercuryTheme theme;
  
  public SimpleSliderUI(MercuryTheme theme)
  {
    super(Slider.class);
    this.theme = theme;
    
    foreground = Color.WHITE;
    background = new Color(Color.CYAN.getRed(), Color.CYAN.getGreen(), Color.CYAN.getBlue(), 64);
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
    switch (component.getValueDisplay()) {
    case DECIMAL: 
      content = String.format("%,.1f", new Object[] { Double.valueOf(component.getValue()) });
      break;
    case INTEGER: 
      content = String.format("%,d", new Object[] { Long.valueOf(Math.round(component.getValue())) });
      break;
    case NONE: 
      int percent = (int)Math.round((component.getValue() - component.getMinimumValue()) / (component.getMaximumValue() - component.getMinimumValue()) * 100.0D);
      content = String.format("%d%%", new Object[] { Integer.valueOf(percent) });
    }
    
    if (content != null) {
      String suffix = component.getContentSuffix();
      if ((suffix != null) && (!suffix.trim().isEmpty()))
        content = content.concat(" ").concat(suffix);
      fontRenderer.drawString(content, component.getWidth() - fontRenderer.getStringWidth(content), 0, RenderUtil.toRGBA(component.getForegroundColor()));
    }
    GL11.glDisable(3553);
    
    RenderUtil.setColor(new Color(0, 200, 200, 255));
    GL11.glLineWidth(0.9F);
    GL11.glBegin(2);
    
    GL11.glVertex2d(0.0D, fontSize + 2.0D);
    GL11.glVertex2d(width, fontSize + 2.0D);
    GL11.glVertex2d(width, height);
    GL11.glVertex2d(0.0D, height);
    
    GL11.glEnd();
    RenderUtil.setColor(getBackgroundColor(component));
    
    double sliderPercentage = (component.getValue() - component.getMinimumValue()) / (component.getMaximumValue() - component.getMinimumValue());
    RenderUtil.setColor(component.getForegroundColor());
    GL11.glBegin(7);
    
    GL11.glVertex2d(1.0D, fontSize + 3.0D);
    GL11.glVertex2d(width * sliderPercentage, fontSize + 3.0D);
    GL11.glVertex2d(width * sliderPercentage, height - 0.7D);
    GL11.glVertex2d(1.0D, height - 0.7D);
    
    GL11.glEnd();
    
    GL11.glEnable(3553);
    translateComponent(component, true);
  }
  
  protected Dimension getDefaultComponentSize(Slider component)
  {
    return new Dimension(100, 8 + theme.getFontRenderer().FONT_HEIGHT);
  }
  
  protected Rectangle[] getInteractableComponentRegions(Slider component)
  {
    return new Rectangle[] { new Rectangle(0, theme.getFontRenderer().FONT_HEIGHT + 2, component.getWidth(), component.getHeight() - theme.getFontRenderer().FONT_HEIGHT) };
  }
  
  protected void handleComponentInteraction(Slider component, Point location, int button)
  {
    if (!me.hexxed.mercury.gui.GuiIngameHook.menu) {
      return;
    }
    if ((getInteractableComponentRegions(component)[0].contains(location)) && (button == 0)) {
      if ((Mouse.isButtonDown(button)) && (!component.isValueChanging())) {
        component.setValueChanging(true);
      } else if ((!Mouse.isButtonDown(button)) && (component.isValueChanging()))
        component.setValueChanging(false);
    }
  }
  
  protected void handleComponentUpdate(Slider component) {
    if (component.isValueChanging()) {
      if (!Mouse.isButtonDown(0)) {
        component.setValueChanging(false);
        return;
      }
      Point mouse = RenderUtil.calculateMouseLocation();
      Container parent = component.getParent();
      if (parent != null)
        mouse.translate(-parent.getX(), -parent.getY());
      double percent = x / component.getWidth();
      double value = component.getMinimumValue() + percent * (component.getMaximumValue() - component.getMinimumValue());
      component.setValue(value);
    }
  }
}
