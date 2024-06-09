package exhibition.gui.screen.impl.mainmenu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.GL11;

import exhibition.Client;
import exhibition.gui.altmanager.GuiAltManager;
import exhibition.gui.screen.component.GuiMenuButton;
import exhibition.management.ColorManager;
import exhibition.util.RenderingUtil;
import exhibition.util.render.ColorContainer;
import exhibition.util.render.Colors;
import exhibition.util.security.Crypto;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLanguage;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

public class GuiModdedMainMenu extends GuiMainMenu {
   private ResourceLocation uparrow = new ResourceLocation(new String(new byte[]{116, 101, 120, 116, 117, 114, 101, 115, 47, 115, 107, 101, 101, 116, 99, 104, 97, 105, 110, 109, 97, 105, 108, 46, 112, 110, 103}));
   private ResourceLocation finalTexture = null;
   private ResourceLocation texture1;
   private ResourceLocation texture2;
   private ResourceLocation texture3;
   private ResourceLocation texture4;
   
   public GuiModdedMainMenu() {
	   
   }

   public void initGui() {
      super.initGui();
      this.buttonList.clear();
      String strSSP = I18n.format("Single");
      String strSMP = I18n.format("Multi");
      String strOptions = I18n.format("Options");
      String strQuit = I18n.format("Exit Game");
      String strLang = I18n.format("Language");
      String strAccounts = "Accounts";
      int initHeight = this.height / 4 + 48;
      int objHeight = 17;
      int objWidth = 63;
      int xMid = this.width / 2 - objWidth / 2;
      this.buttonList.add(new GuiMenuButton(0, xMid, initHeight, objWidth, objHeight, strSSP));
      this.buttonList.add(new GuiMenuButton(1, xMid, initHeight + 20, objWidth, objHeight, strSMP));
      this.buttonList.add(new GuiMenuButton(2, xMid, initHeight + 40, objWidth, objHeight, strOptions));
      this.buttonList.add(new GuiMenuButton(3, xMid, initHeight + 60, objWidth, objHeight, strLang));
      this.buttonList.add(new GuiMenuButton(4, xMid, initHeight + 80, objWidth, objHeight, strAccounts));
      this.buttonList.add(new GuiMenuButton(5, xMid, initHeight + 100, objWidth, objHeight, strQuit));

      try {
         this.texture1 = new ResourceLocation("textures/1.png");
         this.texture2 = new ResourceLocation("textures/2.png");
         this.texture3 = new ResourceLocation("textures/3.png");
         this.texture4 = new ResourceLocation("textures/4.png");
         List list = new ArrayList();
         list.add(this.texture1);
         list.add(this.texture2);
         list.add(this.texture3);
         list.add(this.texture4);
         Random random = new Random();
         this.finalTexture = (ResourceLocation)list.get(random.nextInt(list.size()));
      } catch (Exception var14) {
         var14.printStackTrace();
      }

   }

   protected void actionPerformed(GuiButton button) throws IOException {
      if (button.id == 0) {
         this.mc.displayGuiScreen(new GuiSelectWorld(this));
      } else if (button.id == 1) {
         this.mc.displayGuiScreen(new GuiMultiplayer(this));
      } else if (button.id == 2) {
         this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
      } else if (button.id == 3) {
         this.mc.displayGuiScreen(new GuiLanguage(this, this.mc.gameSettings, this.mc.getLanguageManager()));
      } else if (button.id == 4) {
         this.mc.displayGuiScreen(new GuiAltManager());
      } else if (button.id == 5) {
         this.mc.shutdown();
      }

   }

   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      super.renderSkybox(mouseX, mouseY, partialTicks);
      ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
      String username = null;

      try {
         username = "Arithmo";
      } catch (Exception var14) {
    	  var14.printStackTrace();
      }

      if (username.equals("Kaxon") && this.finalTexture != null) {
         GlStateManager.pushMatrix();
         GlStateManager.color(2.0F, 2.0F, 1.0F);
         this.mc.getTextureManager().bindTexture(this.finalTexture);
         this.drawIcon(0.0D, 0.0D, 0.0F, 0.0F, (double)sr.getScaledWidth(), (double)sr.getScaledHeight(), (float)sr.getScaledWidth(), (float)sr.getScaledHeight());
         GlStateManager.popMatrix();
      }

      RenderingUtil.rectangleBordered((double)(sr.getScaledWidth() / 2 - 62) - 0.5D, (double)(this.height / 4 + 30) - 0.3D, (double)(sr.getScaledWidth() / 2 + 62) + 0.5D, (double)(this.height / 4 + 175) + 0.3D, 0.5D, Colors.getColor(60), Colors.getColor(10));
      RenderingUtil.rectangleBordered((double)(sr.getScaledWidth() / 2 - 62) + 0.5D, (double)(this.height / 4 + 30) + 0.6D, (double)(sr.getScaledWidth() / 2 + 62) - 0.5D, (double)(this.height / 4 + 175) - 0.6D, 1.3D, Colors.getColor(60), Colors.getColor(40));
      RenderingUtil.rectangleBordered((double)(sr.getScaledWidth() / 2 - 62) + 2.5D, (double)(this.height / 4 + 30) + 2.5D, (double)(sr.getScaledWidth() / 2 + 62) - 2.5D, (double)(this.height / 4 + 175) - 2.5D, 0.5D, Colors.getColor(22), Colors.getColor(12));
      GlStateManager.pushMatrix();
      GlStateManager.color(2.0F, 2.0F, 1.0F);
      this.mc.getTextureManager().bindTexture(this.uparrow);
      GlStateManager.translate((float)(sr.getScaledWidth() / 2 - 60), (float)(this.height / 4 + 30), 0.0F);
      this.drawIcon(1.0D, 3.0D, 0.0F, 0.0F, 118.0D, 139.0D, 325.0F, 275.0F);
      GlStateManager.popMatrix();
      RenderingUtil.drawGradientSideways((double)(sr.getScaledWidth() / 2 - 62 + 3), (double)(this.height / 4 + 30 + 3), (double)(sr.getScaledWidth() / 2), (double)(this.height / 4 + 30 + 4), Colors.getColor(55, 177, 218), Colors.getColor(204, 77, 198));
      RenderingUtil.drawGradientSideways((double)(sr.getScaledWidth() / 2), (double)(this.height / 4 + 30 + 3), (double)(sr.getScaledWidth() / 2 + 62 - 3), (double)(this.height / 4 + 30 + 4), Colors.getColor(204, 77, 198), Colors.getColor(204, 227, 53));
      RenderingUtil.rectangle((double)(sr.getScaledWidth() / 2 - 62 + 3), (double)(this.height / 4 + 30) + 3.5D, (double)(sr.getScaledWidth() / 2 + 62 - 3), (double)(this.height / 4 + 30 + 4), Colors.getColor(0, 110));
      RenderingUtil.rectangleBordered((double)(sr.getScaledWidth() / 2 - 62 + 6), (double)(this.height / 4 + 30 + 8), (double)(sr.getScaledWidth() / 2 + 62) - 6.5D, (double)(this.height / 4 + 169), 0.3D, Colors.getColor(48), Colors.getColor(10));
      RenderingUtil.rectangle((double)(sr.getScaledWidth() / 2 - 62 + 6 + 1), (double)(this.height / 4 + 30 + 9), (double)(sr.getScaledWidth() / 2 + 62) - 7.5D, (double)(this.height / 4 + 169 - 1), Colors.getColor(17));
      RenderingUtil.rectangle((double)((float)(sr.getScaledWidth() / 2 - 62 + 6) + 4.5F), (double)(this.height / 4 + 30 + 8), (double)(sr.getScaledWidth() / 2 - 62 + 35), (double)(this.height / 4 + 30 + 9), Colors.getColor(17));
      GlStateManager.pushMatrix();
      GlStateManager.translate((float)(sr.getScaledWidth() / 2 - 62 + 6 + 5), (float)(this.height / 4 + 30 + 8), 0.0F);
      GlStateManager.scale(0.5D, 0.5D, 0.5D);
      this.mc.fontRendererObj.drawStringWithShadow("Main Menu", 0.0F, 0.0F, -1);
      GlStateManager.popMatrix();
      GL11.glColor3f(1.0F, 1.0F, 1.0F);
      int width = 150;
      int hei = 26;
      boolean override = false;

      for(int i = 0; i < this.buttonList.size(); ++i) {
         GuiButton g = (GuiButton)this.buttonList.get(i);
         if (!override) {
            g.drawButton(this.mc, mouseX, mouseY);
         } else {
            int x = g.xPosition;
            int y = g.yPosition;
            boolean over = mouseX >= x && mouseY >= y && mouseX < x + g.getButtonWidth() && mouseY < y + hei;
            if (over) {
               fillHorizontalGrad((double)x, (double)y, (double)width, (double)hei, new ColorContainer(5, 40, 85, 255), new ColorContainer(0, 0, 0, 0));
            } else {
               fillHorizontalGrad((double)x, (double)y, (double)width, (double)hei, new ColorContainer(0, 0, 0, 255), new ColorContainer(0, 0, 0, 0));
            }

            this.fontRendererObj.drawString(g.displayString, (float)(g.xPosition + 10), (float)(g.yPosition + hei / 2 - 3), -1);
         }
      }

      GL11.glColor3f(1.0F, 1.0F, 1.0F);
      GlStateManager.pushMatrix();
      GlStateManager.translate((float)(this.width / 2 - this.mc.fontRendererObj.getStringWidth("Exhibition") * 2), 50.0F, 0.0F);
      GlStateManager.scale(4.0F, 4.0F, 4.0F);
      this.mc.fontRendererObj.drawStringWithShadow("Exhibition", 0.0F, 0.0F, ColorManager.hudColor.getColorInt());
      GlStateManager.popMatrix();
      String welcome = "Welcome back, §e" + Crypto.decryptPublicNew(Client.authUser.getEncryptedUsername());
      this.mc.fontRendererObj.drawStringWithShadow(welcome, (float)(this.width - this.mc.fontRendererObj.getStringWidth(welcome) - 2), (float)(this.height - 24), -1);
      String currentBuld = "Your current build is: " + (Client.version.equals(Client.parsedVersion) ? "§aLatest§f!" : "§cOutdated§f!");
      this.mc.fontRendererObj.drawStringWithShadow(currentBuld, (float)(this.width - this.mc.fontRendererObj.getStringWidth(currentBuld) - 2), (float)(this.height - 12), -1);
   }

   private void drawIcon(double x, double y, float u, float v, double width, double height, float textureWidth, float textureHeight) {
      float var8 = 1.0F / textureWidth;
      float var9 = 1.0F / textureHeight;
      Tessellator var10 = Tessellator.getInstance();
      WorldRenderer var11 = var10.getWorldRenderer();
      var11.startDrawingQuads();
      var11.addVertexWithUV(x, y + height, 0.0D, (double)(u * var8), (double)((v + (float)height) * var9));
      var11.addVertexWithUV(x + width, y + height, 0.0D, (double)((u + (float)width) * var8), (double)((v + (float)height) * var9));
      var11.addVertexWithUV(x + width, y, 0.0D, (double)((u + (float)width) * var8), (double)(v * var9));
      var11.addVertexWithUV(x, y, 0.0D, (double)(u * var8), (double)(v * var9));
      var10.draw();
   }
}
