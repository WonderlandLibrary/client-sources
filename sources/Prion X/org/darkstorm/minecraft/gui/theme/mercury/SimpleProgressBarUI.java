package org.darkstorm.minecraft.gui.theme.mercury;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import net.minecraft.client.gui.FontRenderer;
import org.darkstorm.minecraft.gui.component.ProgressBar;
import org.darkstorm.minecraft.gui.util.RenderUtil;
import org.lwjgl.opengl.GL11;

public class SimpleProgressBarUI extends org.darkstorm.minecraft.gui.theme.AbstractComponentUI<ProgressBar>
{
  private MercuryTheme theme;
  
  public SimpleProgressBarUI(MercuryTheme theme)
  {
    super(ProgressBar.class);
    this.theme = theme;
    
    foreground = Color.WHITE;
    background = new Color(Color.CYAN.getRed(), Color.CYAN.getGreen(), Color.CYAN.getBlue(), 64);
  }
  

  protected void renderComponent(ProgressBar component)
  {
    background = new Color(128, 128, 128, 192);
    Rectangle area = component.getArea();
    int fontSize = theme.getFontRenderer().FONT_HEIGHT;
    FontRenderer fontRenderer = theme.getFontRenderer();
    
    translateComponent(component, false);
    GL11.glEnable(3042);
    GL11.glDisable(2884);
    GL11.glDisable(3553);
    
    RenderUtil.setColor(component.getBackgroundColor());
    GL11.glLineWidth(0.9F);
    GL11.glBegin(2);
    
    GL11.glVertex2d(0.0D, 0.0D);
    GL11.glVertex2d(width, 0.0D);
    GL11.glVertex2d(width, height);
    GL11.glVertex2d(0.0D, height);
    
    GL11.glEnd();
    
    double barPercentage = (component.getValue() - component.getMinimumValue()) / (component.getMaximumValue() - component.getMinimumValue());
    RenderUtil.setColor(component.getForegroundColor());
    GL11.glBegin(7);
    
    GL11.glVertex2d(0.0D, 0.0D);
    GL11.glVertex2d(width * barPercentage, 0.0D);
    GL11.glVertex2d(width * barPercentage, height);
    GL11.glVertex2d(0.0D, height);
    
    GL11.glEnd();
    
    GL11.glEnable(3553);
    String content = null;
    switch (component.getValueDisplay()) {
    case DECIMAL: 
      content = String.format("%,.3f", new Object[] { Double.valueOf(component.getValue()) });
      break;
    case INTEGER: 
      content = String.format("%,d", new Object[] { Long.valueOf(Math.round(component.getValue())) });
      break;
    case NONE: 
      int percent = (int)Math.round((component.getValue() - component.getMinimumValue()) / (component.getMaximumValue() - component.getMinimumValue()) * 100.0D);
      content = String.format("%d%%", new Object[] { Integer.valueOf(percent) });
    }
    
    if (content != null) {
      GL11.glBlendFunc(775, 769);
      fontRenderer.drawString(content, component.getWidth() / 2 - fontRenderer.getStringWidth(content) / 2, component.getHeight() / 2 - fontSize / 2, RenderUtil.toRGBA(component.getForegroundColor()));
      GL11.glBlendFunc(770, 771);
    }
    GL11.glEnable(2884);
    GL11.glDisable(3042);
    translateComponent(component, true);
  }
  
  protected Dimension getDefaultComponentSize(ProgressBar component)
  {
    return new Dimension(100, 4 + theme.getFontRenderer().FONT_HEIGHT);
  }
  
  protected Rectangle[] getInteractableComponentRegions(ProgressBar component)
  {
    return new Rectangle[0];
  }
}
