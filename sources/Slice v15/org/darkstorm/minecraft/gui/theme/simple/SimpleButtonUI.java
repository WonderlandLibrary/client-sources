package org.darkstorm.minecraft.gui.theme.simple;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import net.minecraft.client.gui.FontRenderer;
import org.darkstorm.minecraft.gui.component.Button;
import org.darkstorm.minecraft.gui.component.Component;
import org.darkstorm.minecraft.gui.theme.AbstractComponentUI;
import org.darkstorm.minecraft.gui.util.RenderUtil;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class SimpleButtonUI
  extends AbstractComponentUI<Button>
{
  private final SimpleTheme theme;
  
  SimpleButtonUI(SimpleTheme theme)
  {
    super(Button.class);
    this.theme = theme;
    
    foreground = Color.WHITE;
    background = new Color(128, 128, 128, 192);
  }
  
  protected void renderComponent(Button button)
  {
    translateComponent(button, false);
    Rectangle area = button.getArea();
    GL11.glEnable(3042);
    GL11.glDisable(2884);
    
    GL11.glDisable(3553);
    RenderUtil.setColor(button.getBackgroundColor());
    GL11.glBegin(7);
    
    GL11.glVertex2d(0.0D, 0.0D);
    GL11.glVertex2d(width, 0.0D);
    GL11.glVertex2d(width, height);
    GL11.glVertex2d(0.0D, height);
    
    GL11.glEnd();
    Point mouse = RenderUtil.calculateMouseLocation();
    Component parent = button.getParent();
    while (parent != null)
    {
      x -= parent.getX();
      y -= parent.getY();
      parent = parent.getParent();
    }
    if (area.contains(mouse))
    {
      GL11.glColor4f(0.5F, 0.5F, 0.5F, Mouse.isButtonDown(0) ? 0.5F : 0.5F);
      GL11.glBegin(7);
      
      GL11.glVertex2d(0.0D, 0.0D);
      GL11.glVertex2d(width, 0.0D);
      GL11.glVertex2d(width, height);
      GL11.glVertex2d(0.0D, height);
      
      GL11.glEnd();
    }
    GL11.glEnable(3553);
    
    String text = button.getText();
    theme.getFontRenderer().drawString(
      text, 
      width / 2 - theme.getFontRenderer().getStringWidth(text) / 
      2, 
      height / 2 - theme.getFontRenderer().FONT_HEIGHT / 2, 
      RenderUtil.toRGBA(button.getForegroundColor()));
    
    GL11.glEnable(2884);
    GL11.glDisable(3042);
    translateComponent(button, true);
  }
  
  protected Dimension getDefaultComponentSize(Button component)
  {
    return new Dimension(theme.getFontRenderer().getStringWidth(component.getText()) + 4, theme.getFontRenderer().FONT_HEIGHT + 4);
  }
  
  protected Rectangle[] getInteractableComponentRegions(Button component)
  {
    return new Rectangle[] { new Rectangle(0, 0, component.getWidth(), 
      component.getHeight()) };
  }
  
  protected void handleComponentInteraction(Button component, Point location, int button)
  {
    if ((x <= component.getWidth()) && 
      (y <= component.getHeight()) && (button == 0)) {
      component.press();
    }
  }
  



  protected void renderComponent(Component component) {}
  


  protected Dimension getDefaultComponentSize(Component component)
  {
    return null;
  }
}
