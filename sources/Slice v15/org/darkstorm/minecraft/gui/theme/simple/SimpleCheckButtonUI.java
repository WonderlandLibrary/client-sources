package org.darkstorm.minecraft.gui.theme.simple;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import net.minecraft.client.gui.FontRenderer;
import org.darkstorm.minecraft.gui.component.CheckButton;
import org.darkstorm.minecraft.gui.component.Component;
import org.darkstorm.minecraft.gui.theme.AbstractComponentUI;
import org.darkstorm.minecraft.gui.util.RenderUtil;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class SimpleCheckButtonUI
  extends AbstractComponentUI<CheckButton>
{
  private final SimpleTheme theme;
  
  SimpleCheckButtonUI(SimpleTheme theme)
  {
    super(CheckButton.class);
    this.theme = theme;
    
    foreground = Color.WHITE;
    background = new Color(87, 230, 137, 255);
  }
  
  protected void renderComponent(CheckButton button)
  {
    translateComponent(button, false);
    Rectangle area = button.getArea();
    GL11.glEnable(3042);
    GL11.glDisable(2884);
    
    GL11.glDisable(3553);
    RenderUtil.setColor(button.getBackgroundColor());
    int size = height - 4;
    GL11.glBegin(7);
    
    GL11.glVertex2d(2.0D, 2.0D);
    GL11.glVertex2d(size + 2, 2.0D);
    GL11.glVertex2d(size + 2, size + 2);
    GL11.glVertex2d(2.0D, size + 2);
    
    GL11.glEnd();
    if (button.isSelected())
    {
      GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.5F);
      GL11.glBegin(7);
      
      GL11.glVertex2d(3.0D, 3.5D);
      GL11.glVertex2d(size + 0.5D, 3.5D);
      GL11.glVertex2d(size + 0.5D, size + 1);
      GL11.glVertex2d(3.0D, size + 1);
      
      GL11.glEnd();
    }
    GL11.glLineWidth(1.0F);
    GL11.glColor4f(0.0F, 0.0F, 0.0F, 1.0F);
    GL11.glBegin(2);
    
    GL11.glVertex2d(2.0D, 2.0D);
    GL11.glVertex2d(size + 2, 2.0D);
    GL11.glVertex2d(size + 2, size + 2);
    GL11.glVertex2d(1.5D, size + 2);
    
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
    theme.getFontRenderer().drawString(text, size + 4, height / 2 - theme.getFontRenderer().FONT_HEIGHT / 2, 
      RenderUtil.toRGBA(button.getForegroundColor()));
    
    GL11.glEnable(2884);
    GL11.glDisable(3042);
    translateComponent(button, true);
  }
  
  protected Dimension getDefaultComponentSize(CheckButton component)
  {
    return new Dimension(
      theme.getFontRenderer().getStringWidth(component.getText()) + theme.getFontRenderer().FONT_HEIGHT + 6, 
      theme.getFontRenderer().FONT_HEIGHT + 4);
  }
  
  protected Rectangle[] getInteractableComponentRegions(CheckButton component)
  {
    return new Rectangle[] { new Rectangle(0, 0, component.getWidth(), component.getHeight()) };
  }
  
  protected void handleComponentInteraction(CheckButton component, Point location, int button)
  {
    if ((x <= component.getWidth()) && (y <= component.getHeight()) && (button == 0)) {
      component.press();
    }
  }
  



  protected void renderComponent(Component component) {}
  


  protected Dimension getDefaultComponentSize(Component component)
  {
    return null;
  }
}
