package org.darkstorm.minecraft.gui.theme.mercury;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import net.minecraft.client.gui.FontRenderer;
import org.darkstorm.minecraft.gui.component.Component;
import org.darkstorm.minecraft.gui.component.Frame;
import org.darkstorm.minecraft.gui.util.RenderUtil;
import org.lwjgl.opengl.GL11;

public class SimpleFrameUI extends org.darkstorm.minecraft.gui.theme.AbstractComponentUI<Frame>
{
  private final MercuryTheme theme;
  
  SimpleFrameUI(MercuryTheme theme)
  {
    super(Frame.class);
    this.theme = theme;
    
    foreground = Color.WHITE;
    background = new Color(128, 128, 128, 192);
  }
  


  protected void renderComponent(Frame component)
  {
    background = new Color(128, 128, 128, 255);
    Rectangle area = new Rectangle(component.getArea());
    int fontHeight = theme.getFontRenderer().FONT_HEIGHT;
    translateComponent(component, false);
    GL11.glEnable(3042);
    GL11.glDisable(2884);
    GL11.glDisable(3553);
    GL11.glBlendFunc(770, 771);
    

    if (component.isMinimized()) {
      height = (fontHeight + 4);
    }
    RenderUtil.setColor(new Color(50, 50, 50, 255));
    int displayoffset = 5;
    GL11.glBegin(7);
    
    GL11.glVertex2d(-displayoffset, 0.0D);
    GL11.glVertex2d(width + displayoffset, 0.0D);
    GL11.glVertex2d(width + displayoffset, fontHeight + 4);
    GL11.glVertex2d(-displayoffset, fontHeight + 4);
    
    GL11.glEnd();
    RenderUtil.setColor(new Color(0, 0, 0, 255));
    GL11.glLineWidth(3.0F);
    GL11.glBegin(2);
    GL11.glVertex2d(-displayoffset, 0.0D);
    GL11.glVertex2d(width + displayoffset, 0.0D);
    GL11.glVertex2d(width + displayoffset, fontHeight + 4);
    GL11.glVertex2d(-displayoffset, fontHeight + 4);
    
    GL11.glEnd();
    RenderUtil.setColor(background);
    if (!component.isMinimized()) {
      GL11.glBegin(7);
      
      GL11.glVertex2d(0.0D, fontHeight + 4);
      GL11.glVertex2d(width, fontHeight + 4);
      GL11.glVertex2d(width, height);
      GL11.glVertex2d(0.0D, height);
      
      GL11.glEnd();
      RenderUtil.setColor(new Color(0, 0, 0, 255));
      GL11.glLineWidth(1.5F);
      GL11.glBegin(2);
      GL11.glVertex2d(0.0D, fontHeight + 4);
      GL11.glVertex2d(width, fontHeight + 4);
      GL11.glVertex2d(width, height);
      GL11.glVertex2d(0.0D, height);
      
      GL11.glEnd();
    }
    


    int offset = component.getWidth() - 2;
    Point mouse = RenderUtil.calculateMouseLocation();
    Component parent = component;
    while (parent != null) {
      x -= parent.getX();
      y -= parent.getY();
      parent = parent.getParent();
    }
    boolean[] checks = { component.isClosable(), 
      component.isPinnable(), component.isMinimizable() };
    boolean[] overlays = { false, component.isPinned(), 
      component.isMinimized() };
    for (int i = 0; i < checks.length; i++) {
      if (checks[i] != 0)
      {
        RenderUtil.setColor(new Color(80, 80, 80, 255));
        GL11.glBegin(7);
        
        GL11.glVertex2d(offset - fontHeight, 2.0D);
        GL11.glVertex2d(offset, 2.0D);
        GL11.glVertex2d(offset, fontHeight + 2);
        GL11.glVertex2d(offset - fontHeight, fontHeight + 2);
        
        GL11.glEnd();
        if (overlays[i] != 0) {
          GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.5F);
          GL11.glBegin(7);
          
          GL11.glVertex2d(offset - fontHeight, 2.0D);
          GL11.glVertex2d(offset, 2.0D);
          GL11.glVertex2d(offset, fontHeight + 2);
          GL11.glVertex2d(offset - fontHeight, fontHeight + 2);
          
          GL11.glEnd();
        }
        if ((x >= offset - fontHeight) && (x <= offset) && 
          (y >= 2) && (y <= fontHeight + 2)) {
          GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.3F);
          GL11.glBegin(7);
          
          GL11.glVertex2d(offset - fontHeight, 2.0D);
          GL11.glVertex2d(offset, 2.0D);
          GL11.glVertex2d(offset, fontHeight + 2);
          GL11.glVertex2d(offset - fontHeight, fontHeight + 2);
          
          GL11.glEnd();
        }
        GL11.glLineWidth(1.0F);
        GL11.glColor4f(0.0F, 0.0F, 0.0F, 1.0F);
        

        GL11.glBegin(2);
        
        GL11.glVertex2d(offset - fontHeight, 2.0D);
        GL11.glVertex2d(offset, 2.0D);
        GL11.glVertex2d(offset, fontHeight + 2);
        GL11.glVertex2d(offset - fontHeight - 0.5D, fontHeight + 2);
        
        GL11.glEnd();
        



















        offset -= fontHeight + 2;
      }
    }
    GL11.glColor4f(0.0F, 0.0F, 0.0F, 1.0F);
    GL11.glLineWidth(1.0F);
    GL11.glBegin(1);
    
    GL11.glVertex2d(2.0D, theme.getFontRenderer().FONT_HEIGHT + 4);
    GL11.glVertex2d(width - 2, theme.getFontRenderer().FONT_HEIGHT + 4);
    
    GL11.glEnd();
    GL11.glEnable(3553);
    theme.getFontRenderer().func_175063_a(component.getTitle(), 2 - displayoffset + 1, 
      2.0F, RenderUtil.toRGBA(component.getForegroundColor()));
    GL11.glEnable(2884);
    GL11.glDisable(3042);
    translateComponent(component, true);
  }
  
  protected Rectangle getContainerChildRenderArea(Frame container)
  {
    Rectangle area = new Rectangle(container.getArea());
    x = 2;
    y = (theme.getFontRenderer().FONT_HEIGHT + 6);
    width -= 4;
    height -= theme.getFontRenderer().FONT_HEIGHT + 8;
    return area;
  }
  
  protected Dimension getDefaultComponentSize(Frame component)
  {
    Component[] children = component.getChildren();
    Rectangle[] areas = new Rectangle[children.length];
    org.darkstorm.minecraft.gui.layout.Constraint[][] constraints = new org.darkstorm.minecraft.gui.layout.Constraint[children.length][];
    for (int i = 0; i < children.length; i++) {
      Component child = children[i];
      Dimension size = child.getTheme().getUIForComponent(child)
        .getDefaultSize(child);
      areas[i] = new Rectangle(0, 0, width, height);
      constraints[i] = component.getConstraints(child);
    }
    Dimension size = component.getLayoutManager().getOptimalPositionedSize(
      areas, constraints);
    width += 4;
    height += theme.getFontRenderer().FONT_HEIGHT + 8;
    return size;
  }
  
  protected Rectangle[] getInteractableComponentRegions(Frame component)
  {
    return new Rectangle[] { new Rectangle(0, 0, component.getWidth(), 
      theme.getFontRenderer().FONT_HEIGHT + 4) };
  }
  

  protected void handleComponentInteraction(Frame component, Point location, int button)
  {
    if (!me.hexxed.mercury.gui.GuiIngameHook.menu) {
      return;
    }
    if (button != 0)
      return;
    int offset = component.getWidth() - 2;
    int textHeight = theme.getFontRenderer().FONT_HEIGHT;
    if (component.isClosable()) {
      if ((x >= offset - textHeight) && (x <= offset) && 
        (y >= 2) && (y <= textHeight + 2)) {
        component.close();
        return;
      }
      offset -= textHeight + 2;
    }
    if (component.isPinnable()) {
      if ((x >= offset - textHeight) && (x <= offset) && 
        (y >= 2) && (y <= textHeight + 2)) {
        component.setPinned(!component.isPinned());
        return;
      }
      offset -= textHeight + 2;
    }
    if (component.isMinimizable()) {
      if ((x >= offset - textHeight) && (x <= offset) && 
        (y >= 2) && (y <= textHeight + 2)) {
        component.setMinimized(!component.isMinimized());
        return;
      }
      offset -= textHeight + 2;
    }
    if ((x >= 0) && (x <= offset) && (y >= 0) && 
      (y <= textHeight + 4)) {
      component.setDragging(true);
      return;
    }
  }
}
