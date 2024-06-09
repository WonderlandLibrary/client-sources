package exhibition.gui.screen;

import exhibition.util.render.ColorContainer;
import exhibition.util.render.Colors;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Project;

public class PanoramaScreen extends GuiScreen {
   private static final ResourceLocation[] titlePanoramaPaths = new ResourceLocation[]{new ResourceLocation("textures/gui/title/background/panorama_0.png"), new ResourceLocation("textures/gui/title/background/panorama_1.png"), new ResourceLocation("textures/gui/title/background/panorama_2.png"), new ResourceLocation("textures/gui/title/background/panorama_3.png"), new ResourceLocation("textures/gui/title/background/panorama_4.png"), new ResourceLocation("textures/gui/title/background/panorama_5.png")};
   protected static final ResourceLocation minecraftTitleTextures = new ResourceLocation("textures/gui/title/minecraft.png");
   private DynamicTexture viewportTexture;
   private ResourceLocation panoramaView;
   protected static int panoramaTimer;
   private static final ColorContainer c1 = new ColorContainer(0, 0, 0, 100);
   private static final ColorContainer c2 = new ColorContainer(0, 0, 0, 0);

   public void updateScreen() {
      ++panoramaTimer;
   }

   public void initGui() {
      this.viewportTexture = new DynamicTexture(256, 256);
      this.panoramaView = this.mc.getTextureManager().getDynamicTextureLocation("background", this.viewportTexture);
      super.initGui();
   }

   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      super.drawScreen(mouseX, mouseY, partialTicks);
      ++panoramaTimer;
      drawRect(-1, -1, this.width + 3, this.height + 3, Colors.getColor(0, 40));
      GL11.glColor3f(1.0F, 1.0F, 1.0F);
      this.drawButtons(mouseX, mouseY);
      GL11.glColor3f(1.0F, 1.0F, 1.0F);
      this.drawTitle();
   }

   protected void drawTitle() {
   }

   protected void drawButtons(int mouseX, int mouseY) {
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

   }

   public void drawPanorama(int p_73970_1_, int p_73970_2_, float p_73970_3_) {
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

      for(int var7 = 0; var7 < var6 * var6; ++var7) {
         GlStateManager.pushMatrix();
         float var8 = ((float)(var7 % var6) / (float)var6 - 0.5F) / 64.0F;
         float var9 = ((float)(var7 / var6) / (float)var6 - 0.5F) / 64.0F;
         float var10 = 0.0F;
         GlStateManager.translate(var8, var9, var10);
         GlStateManager.rotate(MathHelper.sin(((float)panoramaTimer + p_73970_3_) / 400.0F) * 25.0F + 20.0F, 1.0F, 0.0F, 0.0F);
         GlStateManager.rotate(-((float)panoramaTimer + p_73970_3_) * 0.1F, 0.0F, 1.0F, 0.0F);

         for(int var11 = 0; var11 < 6; ++var11) {
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
            var5.drawColor(16777215, 255 / (var7 + 1));
            float var12 = 0.0F;
            var5.addVertexWithUV(-1.0D, -1.0D, 1.0D, (double)(0.0F + var12), (double)(0.0F + var12));
            var5.addVertexWithUV(1.0D, -1.0D, 1.0D, (double)(1.0F - var12), (double)(0.0F + var12));
            var5.addVertexWithUV(1.0D, 1.0D, 1.0D, (double)(1.0F - var12), (double)(1.0F - var12));
            var5.addVertexWithUV(-1.0D, 1.0D, 1.0D, (double)(0.0F + var12), (double)(1.0F - var12));
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

   private void rotateAndBlurSkybox(float p_73968_1_) {
      this.mc.getTextureManager().bindTexture(this.panoramaView);
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

      for(int var5 = 0; var5 < var4; ++var5) {
         var3.setColorRGBA(1.0F, 1.0F, 1.0F, 1.0F / (float)(var5 + 1));
         int var6 = this.width;
         int var7 = this.height;
         float var8 = (float)(var5 - var4 / 2) / 256.0F;
         var3.addVertexWithUV((double)var6, (double)var7, (double)this.zLevel, (double)(0.0F + var8), 1.0D);
         var3.addVertexWithUV((double)var6, 0.0D, (double)this.zLevel, (double)(1.0F + var8), 1.0D);
         var3.addVertexWithUV(0.0D, 0.0D, (double)this.zLevel, (double)(1.0F + var8), 0.0D);
         var3.addVertexWithUV(0.0D, (double)var7, (double)this.zLevel, (double)(0.0F + var8), 0.0D);
      }

      var2.draw();
      GlStateManager.enableAlpha();
      GlStateManager.colorMask(true, true, true, true);
   }

   protected void renderSkybox(int p_73971_1_, int p_73971_2_, float p_73971_3_) {
      this.mc.getFramebuffer().unbindFramebuffer();
      GlStateManager.viewport(0, 0, 256, 256);
      this.drawPanorama(p_73971_1_, p_73971_2_, p_73971_3_);
      this.rotateAndBlurSkybox(p_73971_3_);
      this.rotateAndBlurSkybox(p_73971_3_);
      this.rotateAndBlurSkybox(p_73971_3_);
      this.rotateAndBlurSkybox(p_73971_3_);
      this.rotateAndBlurSkybox(p_73971_3_);
      this.rotateAndBlurSkybox(p_73971_3_);
      this.rotateAndBlurSkybox(p_73971_3_);
      this.mc.getFramebuffer().bindFramebuffer(true);
      GlStateManager.viewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
      Tessellator var4 = Tessellator.getInstance();
      WorldRenderer var5 = var4.getWorldRenderer();
      var5.startDrawingQuads();
      float var6 = this.width > this.height ? 120.0F / (float)this.width : 120.0F / (float)this.height;
      float var7 = (float)this.height * var6 / 256.0F;
      float var8 = (float)this.width * var6 / 256.0F;
      var5.setColorRGBA(1.0F, 1.0F, 1.0F, 1.0F);
      int var9 = this.width;
      int var10 = this.height;
      var5.addVertexWithUV(0.0D, (double)var10, (double)this.zLevel, (double)(0.5F - var7), (double)(0.5F + var8));
      var5.addVertexWithUV((double)var9, (double)var10, (double)this.zLevel, (double)(0.5F - var7), (double)(0.5F - var8));
      var5.addVertexWithUV((double)var9, 0.0D, (double)this.zLevel, (double)(0.5F + var7), (double)(0.5F - var8));
      var5.addVertexWithUV(0.0D, 0.0D, (double)this.zLevel, (double)(0.5F + var7), (double)(0.5F + var8));
      var4.draw();
   }
}
