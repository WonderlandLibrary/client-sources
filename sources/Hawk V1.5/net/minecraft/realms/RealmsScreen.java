package net.minecraft.realms;

import com.mojang.util.UUIDTypeAdapter;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreenRealmsProxy;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class RealmsScreen {
   public static final int SKIN_HAT_U = 40;
   public static final int SKIN_HEAD_V = 8;
   public static final int SKIN_HEAD_HEIGHT = 8;
   public static final int SKIN_HEAD_WIDTH = 8;
   public static final int SKIN_HAT_HEIGHT = 8;
   public static final int SKIN_HAT_WIDTH = 8;
   protected Minecraft minecraft;
   public static final int SKIN_TEX_HEIGHT = 64;
   public int height;
   public int width;
   private static final String __OBFID = "CL_00001898";
   public static final int SKIN_TEX_WIDTH = 64;
   public static final int SKIN_HEAD_U = 8;
   private GuiScreenRealmsProxy proxy = new GuiScreenRealmsProxy(this);
   public static final int SKIN_HAT_V = 8;

   public static String getLocalizedString(String var0) {
      return I18n.format(var0);
   }

   public void buttonsAdd(RealmsButton var1) {
      this.proxy.func_154327_a(var1);
   }

   public void fillGradient(int var1, int var2, int var3, int var4, int var5, int var6) {
      this.proxy.drawGradientRect(var1, var2, var3, var4, var5, var6);
   }

   public int width() {
      return this.proxy.width;
   }

   public void drawString(String var1, int var2, int var3, int var4) {
      this.proxy.func_154322_b(var1, var2, var3, var4);
   }

   public static RealmsButton newButton(int var0, int var1, int var2, String var3) {
      return new RealmsButton(var0, var1, var2, var3);
   }

   public RealmsEditBox newEditBox(int var1, int var2, int var3, int var4, int var5) {
      return new RealmsEditBox(var1, var2, var3, var4, var5);
   }

   public static void bind(String var0) {
      ResourceLocation var1 = new ResourceLocation(var0);
      Minecraft.getMinecraft().getTextureManager().bindTexture(var1);
   }

   public void mouseEvent() {
   }

   public void mouseDragged(int var1, int var2, int var3, long var4) {
   }

   public void drawCenteredString(String var1, int var2, int var3, int var4) {
      this.proxy.func_154325_a(var1, var2, var3, var4);
   }

   public void mouseClicked(int var1, int var2, int var3) {
   }

   public int height() {
      return this.proxy.height;
   }

   public void buttonsRemove(RealmsButton var1) {
      this.proxy.func_154328_b(var1);
   }

   public void tick() {
   }

   public void renderTooltip(List var1, int var2, int var3) {
      this.proxy.drawHoveringText(var1, var2, var3);
   }

   public boolean isPauseScreen() {
      return this.proxy.doesGuiPauseGame();
   }

   public void buttonsClear() {
      this.proxy.func_154324_i();
   }

   public void renderTooltip(String var1, int var2, int var3) {
      this.proxy.drawCreativeTabHoveringText(var1, var2, var3);
   }

   public void confirmResult(boolean var1, int var2) {
   }

   public void init() {
   }

   public void keyPressed(char var1, int var2) {
   }

   public void mouseReleased(int var1, int var2, int var3) {
   }

   public void removed() {
   }

   public static void blit(int var0, int var1, float var2, float var3, int var4, int var5, int var6, int var7, float var8, float var9) {
      Gui.drawScaledCustomSizeModalRect(var0, var1, var2, var3, var4, var5, var6, var7, var8, var9);
   }

   public GuiScreenRealmsProxy getProxy() {
      return this.proxy;
   }

   public void keyboardEvent() {
   }

   public int fontWidth(String var1) {
      return this.proxy.func_154326_c(var1);
   }

   public int fontLineHeight() {
      return this.proxy.func_154329_h();
   }

   public void renderBackground() {
      this.proxy.drawDefaultBackground();
   }

   public void fontDrawShadow(String var1, int var2, int var3, int var4) {
      this.proxy.func_154319_c(var1, var2, var3, var4);
   }

   public void init(Minecraft var1, int var2, int var3) {
   }

   public static RealmsButton newButton(int var0, int var1, int var2, int var3, int var4, String var5) {
      return new RealmsButton(var0, var1, var2, var3, var4, var5);
   }

   public void renderBackground(int var1) {
      this.proxy.drawWorldBackground(var1);
   }

   public List buttons() {
      return this.proxy.func_154320_j();
   }

   public static String getLocalizedString(String var0, Object... var1) {
      return I18n.format(var0, var1);
   }

   public void blit(int var1, int var2, int var3, int var4, int var5, int var6) {
      this.proxy.drawTexturedModalRect(var1, var2, var3, var4, var5, var6);
   }

   public void renderTooltip(ItemStack var1, int var2, int var3) {
      this.proxy.renderToolTip(var1, var2, var3);
   }

   public static void blit(int var0, int var1, float var2, float var3, int var4, int var5, float var6, float var7) {
      Gui.drawModalRectWithCustomSizedTexture(var0, var1, var2, var3, var4, var5, var6, var7);
   }

   public void buttonClicked(RealmsButton var1) {
   }

   public static void bindFace(String var0, String var1) {
      ResourceLocation var2 = AbstractClientPlayer.getLocationSkin(var1);
      if (var2 == null) {
         var2 = DefaultPlayerSkin.func_177334_a(UUIDTypeAdapter.fromString(var0));
      }

      AbstractClientPlayer.getDownloadImageSkin(var2, var1);
      Minecraft.getMinecraft().getTextureManager().bindTexture(var2);
   }

   public void render(int var1, int var2, float var3) {
      for(int var4 = 0; var4 < this.proxy.func_154320_j().size(); ++var4) {
         ((RealmsButton)this.proxy.func_154320_j().get(var4)).render(var1, var2);
      }

   }

   public List fontSplit(String var1, int var2) {
      return this.proxy.func_154323_a(var1, var2);
   }

   public RealmsAnvilLevelStorageSource getLevelStorageSource() {
      return new RealmsAnvilLevelStorageSource(Minecraft.getMinecraft().getSaveLoader());
   }
}
