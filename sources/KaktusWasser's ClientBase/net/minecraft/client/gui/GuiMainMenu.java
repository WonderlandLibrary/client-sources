package net.minecraft.client.gui;

import java.awt.Desktop;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.util.glu.Project;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;

import me.kaktuswasser.altmanager.GuiAltManager;
import me.kaktuswasser.client.Client;
import me.kaktuswasser.client.utilities.RenderHelper;
import me.kaktuswasser.client.utilities.UnicodeFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import net.minecraft.realms.RealmsBridge;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.demo.DemoWorldServer;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.WorldInfo;

public class GuiMainMenu
  extends GuiScreen
  implements GuiYesNoCallback
{
  private static final AtomicInteger field_175373_f = new AtomicInteger(0);
  private static final Logger logger = LogManager.getLogger();
  private static final Random field_175374_h = new Random();
  private float updateCounter;
  private String splashText;
  private GuiButton buttonResetDemo;
  private int panoramaTimer;
  private DynamicTexture viewportTexture;
  private boolean field_175375_v;
  private final Object field_104025_t;
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
  private final UnicodeFontRenderer hugeText;
  private final UnicodeFontRenderer guiText;
  
  /* Error */
  public GuiMainMenu() {
      this.field_175375_v = true;
      this.field_104025_t = new Object();
      this.hugeText = new UnicodeFontRenderer(new Font("Impact", 0, 102));
      this.guiText = new UnicodeFontRenderer(new Font("Verdana", 0, 22));
      this.field_146972_A = GuiMainMenu.field_96138_a;
      this.splashText = "missingno";
      BufferedReader var1 = null;
      try {
          final ArrayList var2 = Lists.newArrayList();
          var1 = new BufferedReader(new InputStreamReader(Minecraft.getMinecraft().getResourceManager().getResource(GuiMainMenu.splashTexts).getInputStream(), Charsets.UTF_8));
          String var3;
          while ((var3 = var1.readLine()) != null) {
              var3 = var3.trim();
              if (!var3.isEmpty()) {
                  var2.add(var3);
              }
          }
          if (!var2.isEmpty()) {
              do {
                  this.splashText = (String) var2.get(GuiMainMenu.field_175374_h.nextInt(var2.size()));
              } while (this.splashText.hashCode() == 125780783);
          }
      }
      catch (IOException ex) {}
      finally {
          if (var1 != null) {
              try {
                  var1.close();
              }
              catch (IOException ex2) {}
          }
      }
      if (var1 != null) {
          try {
              var1.close();
          }
          catch (IOException ex3) {}
      }
      this.updateCounter = GuiMainMenu.field_175374_h.nextFloat();
      this.field_92025_p = "";
      if (!GLContext.getCapabilities().OpenGL20 && !OpenGlHelper.areShadersSupported()) {
          this.field_92025_p = I18n.format("title.oldgl1", new Object[0]);
          this.field_146972_A = I18n.format("title.oldgl2", new Object[0]);
          this.field_104024_v = "https://help.mojang.com/customer/portal/articles/325948?ref=game";
      }
  }
  
  public void updateScreen()
  {
    this.panoramaTimer += 1;
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
    this.viewportTexture = new DynamicTexture(256, 256);
    this.field_110351_G = this.mc.getTextureManager().getDynamicTextureLocation("background", this.viewportTexture);
    Calendar var1 = Calendar.getInstance();
    var1.setTime(new Date());
    if ((var1.get(2) + 1 == 11) && (var1.get(5) == 9)) {
      this.splashText = "Happy birthday, ez!";
    } else if ((var1.get(2) + 1 == 6) && (var1.get(5) == 1)) {
      this.splashText = "Happy birthday, Notch!";
    } else if ((var1.get(2) + 1 == 12) && (var1.get(5) == 24)) {
      this.splashText = "Merry X-mas!";
    } else if ((var1.get(2) + 1 == 1) && (var1.get(5) == 1)) {
      this.splashText = "Happy new year!";
    } else if ((var1.get(2) + 1 == 10) && (var1.get(5) == 31)) {
      this.splashText = "OOoooOOOoooo! Spooky!";
    }
    boolean var2 = true;
    int var3 = this.height / 4 + 48;
    if (this.mc.isDemo()) {
      addDemoButtons(var3, 24);
    } else {
      addSingleplayerMultiplayerButtons(var3, 24);
    }
    this.buttonList.add(new GuiButton(0, this.width / 2 - 100, var3 + 72 + 12, 98, 20, I18n.format("menu.options", new Object[0])));
    this.buttonList.add(new GuiButton(4, this.width / 2 + 2, var3 + 72 + 12, 98, 20, I18n.format("menu.quit", new Object[0])));
    if (Client.getEventManager().isCancelled()) {
      this.buttonList.add(new GuiButtonLanguage(5, this.width / 2 - 124, var3 + 72 + 12));
    }
    Object var4 = this.field_104025_t;
    synchronized (this.field_104025_t)
    {
      this.field_92023_s = this.fontRendererObj.getStringWidth(this.field_92025_p);
      this.field_92024_r = this.fontRendererObj.getStringWidth(this.field_146972_A);
      int var5 = Math.max(this.field_92023_s, this.field_92024_r);
      this.field_92022_t = ((this.width - var5) / 2);
      this.field_92021_u = (((GuiButton)this.buttonList.get(0)).yPosition - 24);
      this.field_92020_v = (this.field_92022_t + var5);
      this.field_92019_w = (this.field_92021_u + 24);
    }
  }
  
  private void addSingleplayerMultiplayerButtons(int p_73969_1_, int p_73969_2_)
  {
    this.buttonList.add(new GuiButton(1, this.width / 2 - 100, p_73969_1_, I18n.format("menu.singleplayer", new Object[0])));
    this.buttonList.add(new GuiButton(2, this.width / 2 - 100, p_73969_1_ + p_73969_2_ * 1, I18n.format("menu.multiplayer", new Object[0])));
    this.buttonList.add(this.field_175372_K = new GuiButton(14, this.width / 2 - 100, p_73969_1_ + p_73969_2_ * 2, I18n.format(
      Client.getEventManager().isCancelled() ? "menu.online" : "Alt Manager", new Object[0])));
    this.buttonList.add(new GuiButton(444, this.width / 2 - 100, p_73969_1_ + p_73969_2_ * 4 + 10, I18n.format("�2KaktusWasser", new Object[0])));
  }
  
  private void addDemoButtons(int p_73972_1_, int p_73972_2_)
  {
    this.buttonList.add(new GuiButton(11, this.width / 2 - 100, p_73972_1_, I18n.format("menu.playdemo", new Object[0])));
    this.buttonList.add(this.buttonResetDemo = new GuiButton(12, this.width / 2 - 100, p_73972_1_ + p_73972_2_ * 1, I18n.format("menu.resetdemo", new Object[0])));
    ISaveFormat var3 = this.mc.getSaveLoader();
    WorldInfo var4 = var3.getWorldInfo("Demo_World");
    if (var4 == null) {
      this.buttonResetDemo.enabled = false;
    }
  }
  
  protected void actionPerformed(GuiButton button)
    throws IOException
  {
    if (button.id == 0) {
      this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
    }
    if (button.id == 5) {
      this.mc.displayGuiScreen(new GuiLanguage(this, this.mc.gameSettings, this.mc.getLanguageManager()));
    }
    if (button.id == 1) {
      this.mc.displayGuiScreen(new GuiSelectWorld(this));
    }
    if (button.id == 2) {
      this.mc.displayGuiScreen(new GuiMultiplayer(this));
    }
    if ((button.id == 14) && (this.field_175372_K.visible)) {
      if (Client.getEventManager().isCancelled()) {
        switchToRealms();
      } else {
        this.mc.displayGuiScreen(new GuiAltManager());
      }
    }
    if (button.id == 4) {
      this.mc.shutdown();
    }
    if (button.id == 11) {
      this.mc.launchIntegratedServer("Demo_World", "Demo_World", DemoWorldServer.demoWorldSettings);
    }
    if (button.id == 12)
    {
      ISaveFormat var2 = this.mc.getSaveLoader();
      WorldInfo var3 = var2.getWorldInfo("Demo_World");
      if (var3 != null)
      {
        GuiYesNo var4 = GuiSelectWorld.func_152129_a(this, var3.getWorldName(), 12);
        this.mc.displayGuiScreen(var4);
      }
    }
    if (button.id == 444) {
        openWebpage("https://www.youtube.com/KaktusWasserReal");
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
      ISaveFormat var6 = this.mc.getSaveLoader();
      var6.flushCache();
      var6.deleteWorldDirectory("Demo_World");
      this.mc.displayGuiScreen(this);
    }
    else if (id == 13)
    {
      if (result) {
        try
        {
          Class var3 = Class.forName("java.awt.Desktop");
          Object var4 = var3.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
          var3.getMethod("browse", new Class[] { URI.class }).invoke(var4, new Object[] { new URI(this.field_104024_v) });
        }
        catch (Throwable var5)
        {
          logger.error("Couldn't open link", var5);
        }
      }
      this.mc.displayGuiScreen(this);
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
      GlStateManager.rotate(MathHelper.sin((this.panoramaTimer + p_73970_3_) / 400.0F) * 25.0F + 20.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.rotate(-(this.panoramaTimer + p_73970_3_) * 0.1F, 0.0F, 1.0F, 0.0F);
      for (int var11 = 0; var11 < 6; var11++)
      {
        GlStateManager.pushMatrix();
        if (var11 == 1) {
          GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
        }
        if (var11 == 2) {
          GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
        }
        if (var11 == 3) {
          GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
        }
        if (var11 == 4) {
          GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
        }
        if (var11 == 5) {
          GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
        }
        this.mc.getTextureManager().bindTexture(titlePanoramaPaths[var11]);
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
    this.mc.getTextureManager().bindTexture(this.field_110351_G);
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
      int var6 = this.width;
      int var7 = this.height;
      float var8 = (var5 - var4 / 2) / 256.0F;
      var3.addVertexWithUV(var6, var7, this.zLevel, 0.0F + var8, 1.0D);
      var3.addVertexWithUV(var6, 0.0D, this.zLevel, 1.0F + var8, 1.0D);
      var3.addVertexWithUV(0.0D, 0.0D, this.zLevel, 1.0F + var8, 0.0D);
      var3.addVertexWithUV(0.0D, var7, this.zLevel, 0.0F + var8, 0.0D);
    }
    var2.draw();
    GlStateManager.enableAlpha();
    GlStateManager.colorMask(true, true, true, true);
  }
  
  private void renderSkybox(int p_73971_1_, int p_73971_2_, float p_73971_3_)
  {
    this.mc.getFramebuffer().unbindFramebuffer();
    GlStateManager.viewport(0, 0, 256, 256);
    drawPanorama(p_73971_1_, p_73971_2_, p_73971_3_);
    rotateAndBlurSkybox(p_73971_3_);
    rotateAndBlurSkybox(p_73971_3_);
    rotateAndBlurSkybox(p_73971_3_);
    rotateAndBlurSkybox(p_73971_3_);
    rotateAndBlurSkybox(p_73971_3_);
    rotateAndBlurSkybox(p_73971_3_);
    rotateAndBlurSkybox(p_73971_3_);
    this.mc.getFramebuffer().bindFramebuffer(true);
    GlStateManager.viewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
    Tessellator var4 = Tessellator.getInstance();
    WorldRenderer var5 = var4.getWorldRenderer();
    var5.startDrawingQuads();
    float var6 = this.width > this.height ? 120.0F / this.width : 120.0F / this.height;
    float var7 = this.height * var6 / 256.0F;
    float var8 = this.width * var6 / 256.0F;
    var5.func_178960_a(1.0F, 1.0F, 1.0F, 1.0F);
    int var9 = this.width;
    int var10 = this.height;
    var5.addVertexWithUV(0.0D, var10, this.zLevel, 0.5F - var7, 0.5F + var8);
    var5.addVertexWithUV(var9, var10, this.zLevel, 0.5F - var7, 0.5F - var8);
    var5.addVertexWithUV(var9, 0.0D, this.zLevel, 0.5F + var7, 0.5F - var8);
    var5.addVertexWithUV(0.0D, 0.0D, this.zLevel, 0.5F + var7, 0.5F + var8);
    var4.draw();
  }
  
  public void drawScreen(int mouseX, int mouseY, float partialTicks)
  {
    if (Client.getEventManager().isCancelled())
    {
      GlStateManager.disableAlpha();
      renderSkybox(mouseX, mouseY, partialTicks);
      GlStateManager.enableAlpha();
      Tessellator var4 = Tessellator.getInstance();
      WorldRenderer var5 = var4.getWorldRenderer();
      short var6 = 274;
      int var7 = this.width / 2 - var6 / 2;
      byte var8 = 30;
      drawGradientRect(0, 0, this.width, this.height, -2130706433, 16777215);
      drawGradientRect(0, 0, this.width, this.height, 0, Integer.MIN_VALUE);
      this.mc.getTextureManager().bindTexture(minecraftTitleTextures);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      if (this.updateCounter < 1.0E-4D)
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
      GlStateManager.translate(this.width / 2 + 90, 70.0F, 0.0F);
      GlStateManager.rotate(-20.0F, 0.0F, 0.0F, 1.0F);
      float var9 = 1.8F - MathHelper.abs(MathHelper.sin((float)(Minecraft.getSystemTime() % 1000L) / 1000.0F * 3.1415927F * 2.0F) * 0.1F);
      var9 = var9 * 100.0F / (this.fontRendererObj.getStringWidth(this.splashText) + 32);
      GlStateManager.scale(var9, var9, var9);
      drawCenteredString(this.fontRendererObj, this.splashText, 0, -8, 65280);
      GlStateManager.popMatrix();
      String var10 = "Minecraft 1.8";
      if (this.mc.isDemo()) {
        var10 = var10 + " Demo";
      }
      drawString(this.fontRendererObj, var10, 2, this.height - 10, -1);
      String var11 = "Copyright Mojang AB. Do not distribute!";
      drawString(this.fontRendererObj, var11, this.width - this.fontRendererObj.getStringWidth(var11) - 2, this.height - 10, -1);
      if ((this.field_92025_p != null) && (this.field_92025_p.length() > 0))
      {
        drawRect(this.field_92022_t - 2, this.field_92021_u - 2, this.field_92020_v + 2, this.field_92019_w - 1, 1428160512);
        drawString(this.fontRendererObj, this.field_92025_p, this.field_92022_t, this.field_92021_u, -1);
        drawString(this.fontRendererObj, this.field_146972_A, (this.width - this.field_92024_r) / 2, ((GuiButton)this.buttonList.get(0)).yPosition - 12, -1);
      }
    }
    else if (!Client.getEventManager().isCancelled())
    {
      Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("textures/icarusbg.png"));
      int h = RenderHelper.getScaledRes().getScaledHeight();
      int w = RenderHelper.getScaledRes().getScaledWidth();
      Gui.drawScaledCustomSizeModalRect(0, 0, 0.0F, 0.0F, w + 2, h, w + 2, h, w + 2, h);
      
      String text = Client.getName() + " b" + Client.getBuild();
      
      this.hugeText.drawStringWithShadow(text, w / 2 - this.hugeText.getStringWidth(text) / 4, 20, -1);
      this.guiText.drawStringWithShadow("Moddet Icarus SRC", 2, h - 14, Client.isLatestVersion() ? -14614784 : -65536);
      String waterMark = "Src fixed & cleaned by KaktusWasser";
      this.guiText.drawStringWithShadow(waterMark, w - this.guiText.getStringWidth(waterMark) / 2, h - 14, -1);
    }
    super.drawScreen(mouseX, mouseY, partialTicks);
  }
  
  protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    throws IOException
  {
    super.mouseClicked(mouseX, mouseY, mouseButton);
    Object var4 = this.field_104025_t;
    synchronized (this.field_104025_t)
    {
      if ((this.field_92025_p.length() > 0) && (mouseX >= this.field_92022_t) && (mouseX <= this.field_92020_v) && (mouseY >= this.field_92021_u) && (mouseY <= this.field_92019_w))
      {
        GuiConfirmOpenLink var5 = new GuiConfirmOpenLink(this, this.field_104024_v, 13, true);
        var5.disableSecurityWarning();
        this.mc.displayGuiScreen(var5);
      }
    }
  }
  
  public static void openWebpage(String urlString) {
	    try {
	        Desktop.getDesktop().browse(new URL(urlString).toURI());
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
}
