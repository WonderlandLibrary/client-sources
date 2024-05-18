package org.darkstorm.minecraft.gui.theme.simple;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import net.minecraft.client.gui.FontRenderer;
import org.darkstorm.minecraft.gui.component.ComboBox;
import org.darkstorm.minecraft.gui.component.Component;
import org.darkstorm.minecraft.gui.theme.AbstractComponentUI;
import org.darkstorm.minecraft.gui.util.RenderUtil;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class SimpleComboBoxUI
  extends AbstractComponentUI<ComboBox>
{
  private final SimpleTheme theme;
  
  SimpleComboBoxUI(SimpleTheme theme)
  {
    super(ComboBox.class);
    this.theme = theme;
    
    foreground = Color.WHITE;
    background = new Color(128, 128, 128, 192);
  }
  
  protected void renderComponent(ComboBox component)
  {
    translateComponent(component, false);
    Rectangle area = component.getArea();
    GL11.glEnable(3042);
    GL11.glDisable(2884);
    
    GL11.glDisable(3553);
    int maxWidth = 0;
    String[] arrayOfString1;
    int j = (arrayOfString1 = component.getElements()).length;
    for (int i = 0; i < j; i++)
    {
      String element = arrayOfString1[i];
      maxWidth = Math.max(maxWidth, theme.getFontRenderer()
        .getStringWidth(element));
    }
    int extendedHeight = 0;
    if (component.isSelected())
    {
      String[] elements = component.getElements();
      for (int i = 0; i < elements.length - 1; i++) {
        extendedHeight += theme.getFontRenderer().FONT_HEIGHT + 2;
      }
      extendedHeight += 2;
    }
    RenderUtil.setColor(component.getBackgroundColor());
    GL11.glBegin(7);
    
    GL11.glVertex2d(0.0D, 0.0D);
    GL11.glVertex2d(width, 0.0D);
    GL11.glVertex2d(width, height + extendedHeight);
    GL11.glVertex2d(0.0D, height + extendedHeight);
    
    GL11.glEnd();
    Point mouse = RenderUtil.calculateMouseLocation();
    Object parent = component.getParent();
    while (parent != null)
    {
      x -= ((Component)parent).getX();
      y -= ((Component)parent).getY();
      parent = ((Component)parent).getParent();
    }
    GL11.glColor4f(0.0F, 0.0F, 0.0F, Mouse.isButtonDown(0) ? 0.5F : 0.3F);
    if (area.contains(mouse))
    {
      GL11.glBegin(7);
      
      GL11.glVertex2d(0.0D, 0.0D);
      GL11.glVertex2d(width, 0.0D);
      GL11.glVertex2d(width, height);
      GL11.glVertex2d(0.0D, height);
      
      GL11.glEnd();
    }
    else if ((component.isSelected()) && (x >= x) && 
      (x <= x + width))
    {
      int offset = component.getHeight();
      String[] elements = component.getElements();
      for (int i = 0; i < elements.length; i++) {
        if (i != component.getSelectedIndex())
        {
          int height = theme.getFontRenderer().FONT_HEIGHT + 2;
          if ((component.getSelectedIndex() == 0 ? i == 1 : i == 0) || 
            (component.getSelectedIndex() == elements.length - 1 ? i == elements.length - 2 : 
            i == elements.length - 1)) {
            height++;
          }
          if ((y >= y + offset) && 
            (y <= y + offset + height))
          {
            GL11.glBegin(7);
            
            GL11.glVertex2d(0.0D, offset);
            GL11.glVertex2d(0.0D, offset + height);
            GL11.glVertex2d(width, offset + height);
            GL11.glVertex2d(width, offset);
            
            GL11.glEnd();
            break;
          }
          offset += height;
        }
      }
    }
    int height = theme.getFontRenderer().FONT_HEIGHT + 4;
    GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.3F);
    GL11.glBegin(4);
    if (component.isSelected())
    {
      GL11.glVertex2d(maxWidth + 4 + height / 2.0D, height / 3.0D);
      GL11.glVertex2d(maxWidth + 4 + height / 3.0D, 2.0D * height / 3.0D);
      GL11.glVertex2d(maxWidth + 4 + 2.0D * height / 3.0D, 2.0D * height / 3.0D);
    }
    else
    {
      GL11.glVertex2d(maxWidth + 4 + height / 3.0D, height / 3.0D);
      GL11.glVertex2d(maxWidth + 4 + 2.0D * height / 3.0D, height / 3.0D);
      GL11.glVertex2d(maxWidth + 4 + height / 2.0D, 2.0D * height / 3.0D);
    }
    GL11.glEnd();
    GL11.glLineWidth(1.0F);
    GL11.glColor4f(0.0F, 0.0F, 0.0F, 1.0F);
    if (component.isSelected())
    {
      GL11.glBegin(1);
      
      GL11.glVertex2d(2.0D, height);
      GL11.glVertex2d(width - 2, height);
      
      GL11.glEnd();
    }
    GL11.glBegin(1);
    
    GL11.glVertex2d(maxWidth + 4, 2.0D);
    GL11.glVertex2d(maxWidth + 4, height - 2);
    
    GL11.glEnd();
    GL11.glBegin(2);
    if (component.isSelected())
    {
      GL11.glVertex2d(maxWidth + 4 + height / 2.0D, height / 3.0D);
      GL11.glVertex2d(maxWidth + 4 + height / 3.0D, 2.0D * height / 3.0D);
      GL11.glVertex2d(maxWidth + 4 + 2.0D * height / 3.0D, 2.0D * height / 3.0D);
    }
    else
    {
      GL11.glVertex2d(maxWidth + 4 + height / 3.0D, height / 3.0D);
      GL11.glVertex2d(maxWidth + 4 + 2.0D * height / 3.0D, height / 3.0D);
      GL11.glVertex2d(maxWidth + 4 + height / 2.0D, 2.0D * height / 3.0D);
    }
    GL11.glEnd();
    GL11.glEnable(3553);
    
    String text = component.getSelectedElement();
    theme.getFontRenderer().drawString(text, 2, 
      height / 2 - theme.getFontRenderer().FONT_HEIGHT / 2, 
      RenderUtil.toRGBA(component.getForegroundColor()));
    if (component.isSelected())
    {
      int offset = height + 2;
      String[] elements = component.getElements();
      for (int i = 0; i < elements.length; i++) {
        if (i != component.getSelectedIndex())
        {
          theme.getFontRenderer().drawString(elements[i], 2, offset, 
            RenderUtil.toRGBA(component.getForegroundColor()));
          offset += theme.getFontRenderer().FONT_HEIGHT + 2;
        }
      }
    }
    GL11.glEnable(2884);
    GL11.glDisable(3042);
    translateComponent(component, true);
  }
  
  protected Dimension getDefaultComponentSize(ComboBox component)
  {
    int maxWidth = 0;
    String[] arrayOfString;
    int j = (arrayOfString = component.getElements()).length;
    for (int i = 0; i < j; i++)
    {
      String element = arrayOfString[i];
      maxWidth = Math.max(maxWidth, theme.getFontRenderer()
        .getStringWidth(element));
    }
    return new Dimension(
      maxWidth + 8 + theme.getFontRenderer().FONT_HEIGHT, 
      theme.getFontRenderer().FONT_HEIGHT + 4);
  }
  
  protected Rectangle[] getInteractableComponentRegions(ComboBox component)
  {
    int height = component.getHeight();
    if (component.isSelected())
    {
      String[] elements = component.getElements();
      for (int i = 0; i < elements.length; i++) {
        height += theme.getFontRenderer().FONT_HEIGHT + 2;
      }
      height += 2;
    }
    return new Rectangle[] { new Rectangle(0, 0, component.getWidth(), 
      height) };
  }
  
  protected void handleComponentInteraction(ComboBox component, Point location, int button)
  {
    if (button != 0) {
      return;
    }
    if ((x <= component.getWidth()) && 
      (y <= component.getHeight()))
    {
      component.setSelected(!component.isSelected());
    }
    else if ((x <= component.getWidth()) && (component.isSelected()))
    {
      int offset = component.getHeight() + 2;
      String[] elements = component.getElements();
      for (int i = 0; i < elements.length; i++) {
        if (i != component.getSelectedIndex())
        {
          if ((y >= offset) && 
            (y <= offset + theme.getFontRenderer().FONT_HEIGHT))
          {
            component.setSelectedIndex(i);
            component.setSelected(false);
            break;
          }
          
          offset += theme.getFontRenderer().FONT_HEIGHT + 2;
        }
      }
    }
  }
  



  protected void renderComponent(Component component) {}
  


  protected Dimension getDefaultComponentSize(Component component)
  {
    return null;
  }
}
