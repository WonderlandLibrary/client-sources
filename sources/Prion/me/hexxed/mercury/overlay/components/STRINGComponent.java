package me.hexxed.mercury.overlay.components;

import me.hexxed.mercury.overlay.OverlayComponent;
import me.hexxed.mercury.overlay.Placeholders;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;

public class STRINGComponent extends OverlayComponent
{
  private String text;
  
  public STRINGComponent(String text, int x, int y, boolean chat, String xy)
  {
    super("String", x, y, chat, xy);
    this.text = text;
  }
  


  public void renderComponent()
  {
    int x = getX();
    int y = getY();
    ScaledResolution sr = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
    int width = sr.getScaledWidth();
    int height = sr.getScaledHeight();
    FontRenderer fr = mc.fontRendererObj;
    Double scale = Double.valueOf(1.0D);
    String output = text;
    for (Placeholders p : Placeholders.values()) {
      output = output.replaceAll(p.getReplace(), String.valueOf(p.getOutput()));
    }
    if (output.startsWith("%scale")) {
      scale = Double.valueOf(output.substring(1).split("%")[0].replaceAll("scale", ""));
      
      output = output.substring(1).split("%")[1];
    }
    output = me.hexxed.mercury.util.ChatColor.translateAlternateColorCodes('&', output);
    GL11.glPushMatrix();
    net.minecraft.client.renderer.GlStateManager.scale(scale.doubleValue(), scale.doubleValue(), scale.doubleValue());
    fr.func_175063_a(output, x, y, 16777215);
    net.minecraft.client.renderer.GlStateManager.scale(1.0D / scale.doubleValue(), 1.0D / scale.doubleValue(), 1.0D / scale.doubleValue());
    GL11.glPopMatrix();
  }
  
  public String getText() {
    return text;
  }
}
