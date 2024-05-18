package net.SliceClient.ui;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import net.SliceClient.Gui.ColorUtil;
import net.SliceClient.Slice;
import net.SliceClient.Utils.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Session;

public final class UIChangelog extends GuiScreen
{
  private final List<String> log = new ArrayList();
  
  public UIChangelog() {}
  
  protected void actionPerformed(GuiButton p_146284_1_) { mc.displayGuiScreen(null); }
  
  private String status;
  public void drawScreen(int mouseX, int mouseY, float partialTicks)
  {
    ScaledResolution sr = new ScaledResolution(mc, Minecraft.displayWidth, Minecraft.displayHeight);
    SimpleDateFormat formatDate = new SimpleDateFormat("hh:mm");
    String timeString = formatDate.format(new Date());
    
    ScaledResolution scaledRes = new ScaledResolution(mc, Minecraft.displayWidth, Minecraft.displayHeight);
    mc.getTextureManager().bindTexture(new ResourceLocation("textures/3.jpg"));
    Gui.drawScaledCustomSizeModalRect(0, 0, 0.0F, 0.0F, scaledRes.getScaledWidth(), scaledRes.getScaledHeight(), 
      scaledRes.getScaledWidth(), scaledRes.getScaledHeight(), scaledRes.getScaledWidth(), 
      scaledRes.getScaledHeight());
    buttonList.clear();
    int var3 = height / 4 + 48;
    RenderUtil.drawRect(width / 2 - 115, 78, width / 2 + 115, var3 + 72 + 17, ColorUtil.transparency(new Color(0, 0, 0).getRGB(), 0.5D));
    buttonList.add(new GuiButton(8, width / 2 - 100, var3 + 72 + 12 - 25, 200, 20, "Back"));
    drawCenteredString(fontRendererObj, "§7Informations", width / 2, 45, 1644825);
    Minecraft.fontRendererObj.drawStringWithShadow("§7• Youre Version: " + Slice.v, width / 2 - Minecraft.fontRendererObj.getStringWidth("Username / Email") - 15, 113.0F, -1);
    Minecraft.fontRendererObj.drawStringWithShadow("§7• Coder: " + Slice.coder, width / 2 - Minecraft.fontRendererObj.getStringWidth("Username / Email") - 15, 123.0F, -1);
    Minecraft.fontRendererObj.drawStringWithShadow("§7• Minecraft Version: 1.8", width / 2 - Minecraft.fontRendererObj.getStringWidth("Username / Email") - 15, 103.0F, -1);
    Minecraft.fontRendererObj.drawStringWithShadow("§7• Username: " + mc.session.username, width / 2 - Minecraft.fontRendererObj.getStringWidth("Username / Email") - 15, 93.0F, -1);
    Minecraft.fontRendererObj.drawStringWithShadow("§7• Time: " + timeString, width / 2 - Minecraft.fontRendererObj.getStringWidth("Username / Email") - 15, 133.0F, -1);
    super.drawScreen(mouseX, mouseY, partialTicks);
  }
}
