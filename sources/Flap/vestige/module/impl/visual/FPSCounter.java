package vestige.module.impl.visual;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.awt.Color;
import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import vestige.Flap;
import vestige.event.Listener;
import vestige.font.VestigeFontRenderer;
import vestige.module.AlignType;
import vestige.module.Category;
import vestige.module.HUDModule;
import vestige.setting.AbstractSetting;
import vestige.setting.impl.ModeSetting;
import vestige.util.render.RenderUtils2;

public class FPSCounter extends HUDModule {
   private final ModeSetting background = new ModeSetting("Background", "Enabled", new String[]{"Disabled", "Enabled"});
   private VestigeFontRenderer productSans;
   private ClientTheme theme;
   private boolean initialised;
   ScaledResolution sr;

   public FPSCounter() {
      super("FPSDisplay", Category.VISUAL, 450.0D, 4.0D, 100, 100, AlignType.LEFT);
      this.sr = new ScaledResolution(mc);
      this.addSettings(new AbstractSetting[]{this.background});
   }

   @Listener
   public void onEnable() {
   }

   private void initialise() {
      this.productSans = Flap.instance.getFontManager().getProductSans();
      this.theme = (ClientTheme)Flap.instance.getModuleManager().getModule(ClientTheme.class);
   }

   protected void renderModule(boolean inChat) {
      if (!this.initialised) {
         this.initialise();
         this.initialised = true;
      }

      if (!mc.gameSettings.showDebugInfo) {
         this.renderNew();
      }
   }

   private void renderNew() {
      Objects.requireNonNull(Flap.instance);
      String clientName = "Flap";
      (new StringBuilder()).append(String.valueOf(clientName.charAt(0))).append(ChatFormatting.WHITE).append(clientName.substring(1, clientName.length())).toString();
      StringBuilder var10000 = (new StringBuilder()).append(" ");
      Minecraft var10001 = mc;
      String watermark = var10000.append(Minecraft.getDebugFPS()).append(" FPS  ").toString();
      double watermarkWidth = this.getStringWidth(watermark);
      float x = (float)this.posX.getValue();
      float y = (float)this.posY.getValue();
      if (this.background.getMode().equals("Enabled")) {
         RenderUtils2.drawRect((double)x, (double)y, (double)(x + 2.0F + (float)((int)watermarkWidth)), (double)(y + 16.5F), (new Color(0, 0, 0, 50)).getRGB());
      }

      int textWidth = (int)this.productSans.getStringWidth(watermark);
      int textHeight = 9;
      float textX = x + (float)(2 + (int)watermarkWidth - textWidth) / 2.0F;
      float textY = y + (16.5F - (float)textHeight) / 2.0F;
      this.productSans.drawStringWithShadow(watermark, textX, textY, this.theme.getColor(0));
      this.width = (int)(watermarkWidth + 3.0D);
      this.height = 15;
   }

   private void drawStringWithShadow(String text, float x, float y, int color) {
      mc.fontRendererObj.drawStringWithShadow(text, x, y, color);
   }

   private double getStringWidth(String s) {
      return (double)mc.fontRendererObj.getStringWidth(s);
   }
}
