package net.minecraft.client.gui;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import me.hexxed.mercury.Mercury;
import me.hexxed.mercury.gui.GuiDirectLogin;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.realms.RealmsBridge;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.WorldInfo;
import org.apache.commons.io.Charsets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.util.glu.Project;

public class GuiMainMenu extends GuiScreen implements GuiYesNoCallback
{
  private static final AtomicInteger field_175373_f = new AtomicInteger(0);
  private static final Logger logger = LogManager.getLogger();
  private static final Random field_175374_h = new Random();
  

  private float updateCounter;
  

  private String splashText;
  

  private GuiButton buttonResetDemo;
  

  private int panoramaTimer;
  
  private DynamicTexture viewportTexture;
  
  private boolean field_175375_v = true;
  private final Object field_104025_t = new Object();
  private String field_92025_p;
  private String field_146972_A;
  private String field_104024_v;
  private static final ResourceLocation splashTexts = new ResourceLocation("texts/splashes.txt");
  private static final ResourceLocation minecraftTitleTextures = new ResourceLocation("textures/gui/title/minecraft.png");
  

  private static final ResourceLocation[] titlePanoramaPaths = { new ResourceLocation("textures/gui/title/background/panorama_0.png"), new ResourceLocation("textures/gui/title/background/panorama_1.png"), new ResourceLocation("textures/gui/title/background/panorama_2.png"), new ResourceLocation("textures/gui/title/background/panorama_3.png"), new ResourceLocation("textures/gui/title/background/panorama_4.png"), new ResourceLocation("textures/gui/title/background/panorama_5.png") };
  public static final String field_96138_a = "Please click " + EnumChatFormatting.UNDERLINE + "here" + EnumChatFormatting.RESET + " for more information.";
  private int field_92024_r;
  private int field_92023_s;
  private int field_92022_t;
  private int field_92021_u;
  private int field_92020_v;
  private int field_92019_w;
  private ResourceLocation field_110351_G;
  private GuiButton field_175372_K;
  private static final String __OBFID = "CL_00001154";
  
  public GuiMainMenu()
  {
    field_146972_A = field_96138_a;
    splashText = "Skid is lyfe";
    BufferedReader var1 = null;
    
    try
    {
      ArrayList var2 = com.google.common.collect.Lists.newArrayList();
      var1 = new BufferedReader(new java.io.InputStreamReader(Minecraft.getMinecraft().getResourceManager().getResource(splashTexts).getInputStream(), Charsets.UTF_8));
      
      String var3;
      while ((var3 = var1.readLine()) != null)
      {
        String var3 = var3.trim();
        
        if (!var3.isEmpty())
        {
          var2.add(var3);
        }
      }
      
      if (!var2.isEmpty())
      {
        do
        {
          splashText = ((String)var2.get(field_175374_h.nextInt(var2.size())));
        }
        while (splashText.hashCode() == 125780783);

      }
      

    }
    catch (IOException localIOException)
    {

      if (var1 != null)
      {
        try
        {
          var1.close();
        }
        catch (IOException localIOException1) {}
      }
    }
    finally
    {
      if (var1 != null)
      {
        try
        {
          var1.close();
        }
        catch (IOException localIOException2) {}
      }
    }
    



    updateCounter = field_175374_h.nextFloat();
    field_92025_p = "";
    
    if ((!getCapabilitiesOpenGL20) && (!OpenGlHelper.areShadersSupported()))
    {
      field_92025_p = I18n.format("title.oldgl1", new Object[0]);
      field_146972_A = I18n.format("title.oldgl2", new Object[0]);
      field_104024_v = "https://help.mojang.com/customer/portal/articles/325948?ref=game";
    }
  }
  



  public void updateScreen()
  {
    panoramaTimer += 1;
  }
  



  public boolean doesGuiPauseGame()
  {
    return false;
  }
  



  protected void keyTyped(char typedChar, int keyCode)
    throws IOException
  {}
  


  public void initGui()
  {
    viewportTexture = new DynamicTexture(256, 256);
    field_110351_G = mc.getTextureManager().getDynamicTextureLocation("background", viewportTexture);
    Calendar var1 = Calendar.getInstance();
    var1.setTime(new Date());
    
    if ((var1.get(2) + 1 == 11) && (var1.get(5) == 9))
    {
      splashText = "It's the best element!";
    }
    else if ((var1.get(2) + 1 == 6) && (var1.get(5) == 1))
    {
      splashText = "It's the best element!";
    }
    else if ((var1.get(2) + 1 == 12) && (var1.get(5) == 24))
    {
      splashText = "It's the best element!";
    }
    else if ((var1.get(2) + 1 == 1) && (var1.get(5) == 1))
    {
      splashText = "It's the best element!";
    }
    else if ((var1.get(2) + 1 == 10) && (var1.get(5) == 31))
    {
      splashText = "It's the best element!";
    }
    
    boolean var2 = true;
    int var3 = height / 4 + 48;
    
    if (mc.isDemo())
    {
      addDemoButtons(var3, 24);
    }
    else
    {
      addSingleplayerMultiplayerButtons(var3, 24);
    }
    
    buttonList.add(new GuiButton(0, width / 2 - 100, var3 + 72 + 30, 98, 20, I18n.format("menu.options", new Object[0])));
    buttonList.add(new GuiButton(4, width / 2 + 2, var3 + 72 + 30, 98, 20, I18n.format("menu.quit", new Object[0])));
    buttonList.add(new GuiButtonLanguage(5, width / 2 - 124, var3 + 72 + 30));
    Object var4 = field_104025_t;
    
    synchronized (field_104025_t)
    {
      field_92023_s = fontRendererObj.getStringWidth(field_92025_p);
      field_92024_r = fontRendererObj.getStringWidth(field_146972_A);
      int var5 = Math.max(field_92023_s, field_92024_r);
      field_92022_t = ((width - var5) / 2);
      field_92021_u = (buttonList.get(0)).yPosition - 24);
      field_92020_v = (field_92022_t + var5);
      field_92019_w = (field_92021_u + 24);
    }
  }
  



  private void addSingleplayerMultiplayerButtons(int p_73969_1_, int p_73969_2_)
  {
    buttonList.add(new GuiButton(1, width / 2 - 100, p_73969_1_, I18n.format("menu.singleplayer", new Object[0])));
    buttonList.add(new GuiButton(2, width / 2 - 100, p_73969_1_ + p_73969_2_ * 1, I18n.format("menu.multiplayer", new Object[0])));
    buttonList.add(new GuiButton(900, width / 2 - 100, p_73969_1_ + p_73969_2_ * 3, "Accounts"));
    buttonList.add(this.field_175372_K = new GuiButton(14, width / 2 - 100, p_73969_1_ + p_73969_2_ * 2, I18n.format("menu.online", new Object[0])));
  }
  



  private void addDemoButtons(int p_73972_1_, int p_73972_2_)
  {
    buttonList.add(new GuiButton(11, width / 2 - 100, p_73972_1_, I18n.format("menu.playdemo", new Object[0])));
    buttonList.add(this.buttonResetDemo = new GuiButton(12, width / 2 - 100, p_73972_1_ + p_73972_2_ * 1, I18n.format("menu.resetdemo", new Object[0])));
    ISaveFormat var3 = mc.getSaveLoader();
    WorldInfo var4 = var3.getWorldInfo("Demo_World");
    
    if (var4 == null)
    {
      buttonResetDemo.enabled = false;
    }
  }
  
  protected void actionPerformed(GuiButton button) throws IOException
  {
    if (id == 0)
    {
      mc.displayGuiScreen(new GuiOptions(this, mc.gameSettings));
    }
    
    if (id == 5)
    {
      mc.displayGuiScreen(new GuiLanguage(this, mc.gameSettings, mc.getLanguageManager()));
    }
    
    if (id == 1)
    {
      mc.displayGuiScreen(new GuiSelectWorld(this));
    }
    
    if (id == 2)
    {
      mc.displayGuiScreen(new GuiMultiplayer(this));
    }
    
    if ((id == 14) && (field_175372_K.visible))
    {
      switchToRealms();
    }
    
    if (id == 4)
    {
      mc.shutdown();
    }
    
    if (id == 11)
    {
      mc.launchIntegratedServer("Demo_World", "Demo_World", net.minecraft.world.demo.DemoWorldServer.demoWorldSettings);
    }
    
    if (id == 12)
    {
      ISaveFormat var2 = mc.getSaveLoader();
      WorldInfo var3 = var2.getWorldInfo("Demo_World");
      
      if (var3 != null)
      {
        GuiYesNo var4 = GuiSelectWorld.func_152129_a(this, var3.getWorldName(), 12);
        mc.displayGuiScreen(var4);
      }
    }
    
    if (id == 900) {
      mc.displayGuiScreen(new GuiDirectLogin(this));
    }
  }
  
  private void switchToRealms()
  {
    RealmsBridge var1 = new RealmsBridge();
    var1.switchToRealms(this);
  }
  
  public void confirmClicked(boolean result, int id)
  {
    if ((result) && (id == 12))
    {
      ISaveFormat var6 = mc.getSaveLoader();
      var6.flushCache();
      var6.deleteWorldDirectory("Demo_World");
      mc.displayGuiScreen(this);
    }
    else if (id == 13)
    {
      if (result)
      {
        try
        {
          Class var3 = Class.forName("java.awt.Desktop");
          Object var4 = var3.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
          var3.getMethod("browse", new Class[] { URI.class }).invoke(var4, new Object[] { new URI(field_104024_v) });
        }
        catch (Throwable var5)
        {
          logger.error("Couldn't open link", var5);
        }
      }
      
      mc.displayGuiScreen(this);
    }
  }
  



  private void drawPanorama(int p_73970_1_, int p_73970_2_, float p_73970_3_)
  {
    Tessellator var4 = Tessellator.getInstance();
    WorldRenderer var5 = var4.getWorldRenderer();
    GlStateManager.matrixMode(5889);
    GlStateManager.pushMatrix();
    GlStateManager.loadIdentity();
    Project.gluPerspective(120.0F, 1.0F, 0.05F, 10.0F);
    GlStateManager.matrixMode(5888);
    GlStateManager.pushMatrix();
    GlStateManager.loadIdentity();
    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
    GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
    GlStateManager.enableBlend();
    GlStateManager.disableAlpha();
    GlStateManager.disableCull();
    GlStateManager.depthMask(false);
    GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
    byte var6 = 8;
    
    for (int var7 = 0; var7 < var6 * var6; var7++)
    {
      GlStateManager.pushMatrix();
      float var8 = (var7 % var6 / var6 - 0.5F) / 64.0F;
      float var9 = (var7 / var6 / var6 - 0.5F) / 64.0F;
      float var10 = 0.0F;
      GlStateManager.translate(var8, var9, var10);
      GlStateManager.rotate(MathHelper.sin((panoramaTimer + p_73970_3_) / 400.0F) * 25.0F + 20.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.rotate(-(panoramaTimer + p_73970_3_) * 0.1F, 0.0F, 1.0F, 0.0F);
      
      for (int var11 = 0; var11 < 6; var11++)
      {
        GlStateManager.pushMatrix();
        
        if (var11 == 1)
        {
          GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
        }
        
        if (var11 == 2)
        {
          GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
        }
        
        if (var11 == 3)
        {
          GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
        }
        
        if (var11 == 4)
        {
          GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
        }
        
        if (var11 == 5)
        {
          GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
        }
        
        mc.getTextureManager().bindTexture(titlePanoramaPaths[var11]);
        var5.startDrawingQuads();
        var5.func_178974_a(16777215, 255 / (var7 + 1));
        float var12 = 0.0F;
        var5.addVertexWithUV(-1.0D, -1.0D, 1.0D, 0.0F + var12, 0.0F + var12);
        var5.addVertexWithUV(1.0D, -1.0D, 1.0D, 1.0F - var12, 0.0F + var12);
        var5.addVertexWithUV(1.0D, 1.0D, 1.0D, 1.0F - var12, 1.0F - var12);
        var5.addVertexWithUV(-1.0D, 1.0D, 1.0D, 0.0F + var12, 1.0F - var12);
        var4.draw();
        GlStateManager.popMatrix();
      }
      
      GlStateManager.popMatrix();
      GlStateManager.colorMask(true, true, true, false);
    }
    
    var5.setTranslation(0.0D, 0.0D, 0.0D);
    GlStateManager.colorMask(true, true, true, true);
    GlStateManager.matrixMode(5889);
    GlStateManager.popMatrix();
    GlStateManager.matrixMode(5888);
    GlStateManager.popMatrix();
    GlStateManager.depthMask(true);
    GlStateManager.enableCull();
    GlStateManager.enableDepth();
  }
  



  private void rotateAndBlurSkybox(float p_73968_1_)
  {
    mc.getTextureManager().bindTexture(field_110351_G);
    GL11.glTexParameteri(3553, 10241, 9729);
    GL11.glTexParameteri(3553, 10240, 9729);
    GL11.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, 256, 256);
    GlStateManager.enableBlend();
    GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
    GlStateManager.colorMask(true, true, true, false);
    Tessellator var2 = Tessellator.getInstance();
    WorldRenderer var3 = var2.getWorldRenderer();
    var3.startDrawingQuads();
    GlStateManager.disableAlpha();
    byte var4 = 3;
    
    for (int var5 = 0; var5 < var4; var5++)
    {
      var3.func_178960_a(1.0F, 1.0F, 1.0F, 1.0F / (var5 + 1));
      int var6 = width;
      int var7 = height;
      float var8 = (var5 - var4 / 2) / 256.0F;
      var3.addVertexWithUV(var6, var7, zLevel, 0.0F + var8, 1.0D);
      var3.addVertexWithUV(var6, 0.0D, zLevel, 1.0F + var8, 1.0D);
      var3.addVertexWithUV(0.0D, 0.0D, zLevel, 1.0F + var8, 0.0D);
      var3.addVertexWithUV(0.0D, var7, zLevel, 0.0F + var8, 0.0D);
    }
    
    var2.draw();
    GlStateManager.enableAlpha();
    GlStateManager.colorMask(true, true, true, true);
  }
  



  private void renderSkybox(int p_73971_1_, int p_73971_2_, float p_73971_3_)
  {
    mc.getFramebuffer().unbindFramebuffer();
    GlStateManager.viewport(0, 0, 256, 256);
    drawPanorama(p_73971_1_, p_73971_2_, p_73971_3_);
    rotateAndBlurSkybox(p_73971_3_);
    rotateAndBlurSkybox(p_73971_3_);
    rotateAndBlurSkybox(p_73971_3_);
    rotateAndBlurSkybox(p_73971_3_);
    rotateAndBlurSkybox(p_73971_3_);
    rotateAndBlurSkybox(p_73971_3_);
    rotateAndBlurSkybox(p_73971_3_);
    mc.getFramebuffer().bindFramebuffer(true);
    GlStateManager.viewport(0, 0, mc.displayWidth, mc.displayHeight);
    Tessellator var4 = Tessellator.getInstance();
    WorldRenderer var5 = var4.getWorldRenderer();
    var5.startDrawingQuads();
    float var6 = width > height ? 120.0F / width : 120.0F / height;
    float var7 = height * var6 / 256.0F;
    float var8 = width * var6 / 256.0F;
    var5.func_178960_a(1.0F, 1.0F, 1.0F, 1.0F);
    int var9 = width;
    int var10 = height;
    var5.addVertexWithUV(0.0D, var10, zLevel, 0.5F - var7, 0.5F + var8);
    var5.addVertexWithUV(var9, var10, zLevel, 0.5F - var7, 0.5F - var8);
    var5.addVertexWithUV(var9, 0.0D, zLevel, 0.5F + var7, 0.5F - var8);
    var5.addVertexWithUV(0.0D, 0.0D, zLevel, 0.5F + var7, 0.5F + var8);
    var4.draw();
  }
  



  public void drawScreen(int mouseX, int mouseY, float partialTicks)
  {
    GlStateManager.disableAlpha();
    renderSkybox(mouseX, mouseY, partialTicks);
    GlStateManager.enableAlpha();
    Tessellator var4 = Tessellator.getInstance();
    WorldRenderer var5 = var4.getWorldRenderer();
    short var6 = 274;
    int var7 = width / 2 - var6 / 2;
    byte var8 = 30;
    drawGradientRect(0, 0, width, height, -2130706433, 16777215);
    drawGradientRect(0, 0, width, height, 0, Integer.MIN_VALUE);
    mc.getTextureManager().bindTexture(minecraftTitleTextures);
    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    
    if (updateCounter < 1.0E-4D)
    {
      drawTexturedModalRect(var7 + 0, var8 + 0, 0, 0, 99, 44);
      drawTexturedModalRect(var7 + 99, var8 + 0, 129, 0, 27, 44);
      drawTexturedModalRect(var7 + 99 + 26, var8 + 0, 126, 0, 3, 44);
      drawTexturedModalRect(var7 + 99 + 26 + 3, var8 + 0, 99, 0, 26, 44);
      drawTexturedModalRect(var7 + 155, var8 + 0, 0, 45, 155, 44);
    }
    else
    {
      drawTexturedModalRect(var7 + 0, var8 + 0, 0, 0, 155, 44);
      drawTexturedModalRect(var7 + 155, var8 + 0, 0, 45, 155, 44);
    }
    
    var5.func_178991_c(-1);
    GlStateManager.pushMatrix();
    GlStateManager.translate(width / 2 + 90, 70.0F, 0.0F);
    GlStateManager.rotate(-20.0F, 0.0F, 0.0F, 1.0F);
    float var9 = 1.8F - MathHelper.abs(MathHelper.sin((float)(Minecraft.getSystemTime() % 1000L) / 1000.0F * 3.1415927F * 2.0F) * 0.1F);
    var9 = var9 * 100.0F / (fontRendererObj.getStringWidth(splashText) + 32);
    GlStateManager.scale(var9, var9, var9);
    drawCenteredString(fontRendererObj, splashText, 0, -8, 65280);
    GlStateManager.popMatrix();
    
    String var10 = Mercury.getInstance().getName() + " " + Mercury.getInstance().getVersion();
    
    if (mc.isDemo())
    {
      var10 = var10 + " Demo";
    }
    
    drawString(fontRendererObj, var10, 2, height - 10, -1);
    
    String var11 = "Created by " + Mercury.getInstance().getAuthor();
    drawString(fontRendererObj, var11, width - fontRendererObj.getStringWidth(var11) - 2, height - 10, -1);
    
    if ((field_92025_p != null) && (field_92025_p.length() > 0))
    {
      drawRect(field_92022_t - 2, field_92021_u - 2, field_92020_v + 2, field_92019_w - 1, 1428160512);
      drawString(fontRendererObj, field_92025_p, field_92022_t, field_92021_u, -1);
      drawString(fontRendererObj, field_146972_A, (width - field_92024_r) / 2, buttonList.get(0)).yPosition - 12, -1);
    }
    
    super.drawScreen(mouseX, mouseY, partialTicks);
  }
  


  protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    throws IOException
  {
    super.mouseClicked(mouseX, mouseY, mouseButton);
    Object var4 = field_104025_t;
    
    synchronized (field_104025_t)
    {
      if ((field_92025_p.length() > 0) && (mouseX >= field_92022_t) && (mouseX <= field_92020_v) && (mouseY >= field_92021_u) && (mouseY <= field_92019_w))
      {
        GuiConfirmOpenLink var5 = new GuiConfirmOpenLink(this, field_104024_v, 13, true);
        var5.disableSecurityWarning();
        mc.displayGuiScreen(var5);
      }
    }
  }
}
