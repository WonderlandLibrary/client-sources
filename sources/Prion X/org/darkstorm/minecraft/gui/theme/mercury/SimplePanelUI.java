package org.darkstorm.minecraft.gui.theme.mercury;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import org.darkstorm.minecraft.gui.component.Component;
import org.darkstorm.minecraft.gui.component.Panel;
import org.darkstorm.minecraft.gui.layout.Constraint;
import org.lwjgl.opengl.GL11;

public class SimplePanelUI extends org.darkstorm.minecraft.gui.theme.AbstractComponentUI<Panel>
{
  private final MercuryTheme theme;
  
  SimplePanelUI(MercuryTheme theme)
  {
    super(Panel.class);
    this.theme = theme;
    
    foreground = Color.WHITE;
    background = new Color(128, 128, 128, 192);
  }
  


  protected void renderComponent(Panel component)
  {
    background = new Color(128, 128, 128, 192);
    if (component.getParent() != null)
      return;
    Rectangle area = component.getArea();
    translateComponent(component, false);
    GL11.glEnable(3042);
    GL11.glDisable(3553);
    GL11.glDisable(2884);
    GL11.glBlendFunc(770, 771);
    org.darkstorm.minecraft.gui.util.RenderUtil.setColor(component.getBackgroundColor());
    GL11.glBegin(7);
    
    GL11.glVertex2d(0.0D, 0.0D);
    GL11.glVertex2d(width, 0.0D);
    GL11.glVertex2d(width, height);
    GL11.glVertex2d(0.0D, height);
    
    GL11.glEnd();
    GL11.glEnable(2884);
    GL11.glEnable(3553);
    GL11.glDisable(3042);
    translateComponent(component, true);
  }
  
  protected Dimension getDefaultComponentSize(Panel component)
  {
    Component[] children = component.getChildren();
    Rectangle[] areas = new Rectangle[children.length];
    Constraint[][] constraints = new Constraint[children.length][];
    for (int i = 0; i < children.length; i++) {
      Component child = children[i];
      Dimension size = child.getTheme().getUIForComponent(child)
        .getDefaultSize(child);
      areas[i] = new Rectangle(0, 0, width, height);
      constraints[i] = component.getConstraints(child);
    }
    return component.getLayoutManager().getOptimalPositionedSize(areas, 
      constraints);
  }
}
