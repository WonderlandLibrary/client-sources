package vestige.module.impl.visual;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.awt.Color;
import java.util.Objects;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import vestige.Flap;
import vestige.font.VestigeFontRenderer;
import vestige.module.AlignType;
import vestige.module.Category;
import vestige.module.HUDModule;
import vestige.setting.AbstractSetting;
import vestige.setting.impl.BooleanSetting;
import vestige.setting.impl.DoubleSetting;
import vestige.setting.impl.ModeSetting;
import vestige.shaders.impl.GaussianBlur;
import vestige.util.network.ServerUtil;
import vestige.util.render.DrawUtil;
import vestige.util.render.RenderUtils2;
import vestige.util.util.ClientSession;

public class Watermark extends HUDModule {
   private VestigeFontRenderer productSan;
   private VestigeFontRenderer productSans;
   private VestigeFontRenderer productSansBold;
   private VestigeFontRenderer productSans172;
   private VestigeFontRenderer productSansBold2;
   private ClientTheme theme;
   private boolean initialised;
   private final ModeSetting mode = new ModeSetting("Mode", "Text", new String[]{"Simple", "New", "Logo", "VapeV4", "Text"});
   private final BooleanSetting categories = new BooleanSetting("Add Categories", true);
   private final DoubleSetting yoffset = new DoubleSetting("Cat Offset Y", () -> {
      return this.categories.isEnabled();
   }, -2.0D, -20.0D, 100.0D, 1.0D);
   ScaledResolution sr;

   public Watermark() {
      super("Watermark", Category.VISUAL, 10.0D, 10.0D, 100, 100, AlignType.LEFT);
      this.sr = new ScaledResolution(mc);
      this.addSettings(new AbstractSetting[]{this.mode, this.categories, this.yoffset});
      this.setEnabledSilently(true);
   }

   private void initialise() {
      this.productSan = Flap.instance.getFontManager().getProductSan();
      this.productSans = Flap.instance.getFontManager().getProductSans();
      this.productSansBold = Flap.instance.getFontManager().getProductSansBold20();
      this.productSans172 = Flap.instance.getFontManager().getProductSans30();
      this.productSansBold2 = Flap.instance.getFontManager().getProductSansBold30();
      this.theme = (ClientTheme)Flap.instance.getModuleManager().getModule(ClientTheme.class);
   }

   protected void renderModule(boolean inChat) {
      if (!this.initialised) {
         this.initialise();
         this.initialised = true;
      }

      if (!mc.gameSettings.showDebugInfo) {
         if (this.categories.isEnabled()) {
            this.categoryRender();
         }

         String var2 = this.mode.getMode();
         byte var3 = -1;
         switch(var2.hashCode()) {
         case -1818419758:
            if (var2.equals("Simple")) {
               var3 = 2;
            }
            break;
         case -1739842402:
            if (var2.equals("VapeV4")) {
               var3 = 3;
            }
            break;
         case 78208:
            if (var2.equals("New")) {
               var3 = 0;
            }
            break;
         case 2374091:
            if (var2.equals("Logo")) {
               var3 = 1;
            }
            break;
         case 2603341:
            if (var2.equals("Text")) {
               var3 = 4;
            }
         }

         switch(var3) {
         case 0:
            this.renderNew();
            break;
         case 1:
            this.renderLogo();
            break;
         case 2:
            this.renderSimple();
            break;
         case 3:
            this.renderBapeV4();
            break;
         case 4:
            this.textRender();
         }

      }
   }

   private void categoryRender() {
      float x = (float)this.posX.getValue();
      float y = (float)((double)((float)this.posY.getValue()) + this.yoffset.getValue());
      int offset = 0;
      Category[] var4 = Category.values();
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Category category = var4[var6];
         String categoryName = category.name().toString().toLowerCase();
         String capital = categoryName.substring(0, 1).toUpperCase();
         String rest = categoryName.substring(1);
         RenderUtils2.drawBloomShadow(x, y + 23.0F + (float)offset, x + 75.0F, y + 25.0F + (float)offset + 11.0F, 10, 0, (new Color(0, 0, 0, 50)).getRGB(), false);
         GaussianBlur.startBlur();
         RenderUtils2.drawRoundOutline((double)x, (double)(y + 25.0F + (float)offset), 75.0D, 12.0D, 0.0D, 0.0D, new Color(0, 0, 0, 45), new Color(0, 0, 0, 100));
         GaussianBlur.endBlur(2.0F, 2.0F);
         if (category.name() == "COMBAT") {
            RenderUtils2.drawRect((double)x, (double)(y + 23.0F + (float)offset), (double)(x + 75.0F), (double)(y + 25.0F + (float)offset + 11.0F), this.theme.getColor(1));
         }

         RenderUtils2.drawRect((double)x, (double)(y + 23.0F + (float)offset), (double)(x + 75.0F), (double)(y + 25.0F + (float)offset + 11.0F), (new Color(0, 0, 0, 60)).getRGB());
         this.productSan.drawString(capital + rest, x + 4.0F, y + 25.0F + (float)offset + 1.0F, -1);
         offset += 13;
      }

   }

   private void renderBapeV4() {
      Objects.requireNonNull(Flap.instance);
      String clientName = "Flap";
      (new StringBuilder()).append(String.valueOf(clientName.charAt(0))).append(ChatFormatting.WHITE).append(clientName.substring(1, clientName.length())).toString();
      float x = (float)this.posX.getValue();
      float y = (float)this.posY.getValue();
      DrawUtil.drawImage(new ResourceLocation("flap/v4black.png"), (int)x + 3, (int)y + 1, 70, 39);
      this.drawStringWithShadow(" ", x + 1.0F, y + 2.5F, this.theme.getColor(1));
      DrawUtil.drawImage(new ResourceLocation("flap/v4.png"), (int)x + 2, (int)y, 70, 39);
      this.height = 15;
   }

   private void textRender() {
      float x = (float)this.posX.getValue();
      float y = (float)this.posY.getValue();
      RenderUtils2.drawBloomShadow(x, y, (float)((double)x + this.productSans172.getStringWidth("Flap")), y + (float)this.productSans172.getStringHeight("Flap"), 10, 120, (new Color(0, 0, 0, 40)).getRGB(), false);
      this.productSansBold2.drawStringWithShadow("F", x, y, this.theme.getColor(1));
      this.productSans172.drawStringWithShadow("lap", (double)x + this.productSansBold2.getStringWidth("F"), (double)y, -1);
   }

   private void renderNew() {
      Objects.requireNonNull(Flap.instance);
      String clientName = "Flap";
      (new StringBuilder()).append(String.valueOf(clientName.charAt(0))).append(ChatFormatting.WHITE).append(clientName.substring(1, clientName.length())).toString();
      String watermark = "Flap | Public (" + ClientSession.username + ") |" + ServerUtil.getCurrentServer() + "       ";
      double watermarkWidth = this.getStringWidth(watermark);
      float x = (float)this.posX.getValue();
      float y = (float)this.posY.getValue();
      RenderUtils2.drawBloomShadow(x, y + 3.5F, (float)(watermarkWidth + (double)x + 5.0D), 19.5F + y, 5, 6, (new Color(0, 0, 0, 40)).getRGB(), false);
      GaussianBlur.startBlur();
      RenderUtils2.drawRoundOutline((double)x, (double)y, watermarkWidth + 5.0D, 19.5D, 30.0D, 0.0D, new Color(0, 0, 0, 45), new Color(0, 0, 0, 100));
      GaussianBlur.endBlur(2.0F, 2.0F);
      RenderUtils2.drawRoundOutline((double)x, (double)y, watermarkWidth + 5.0D, 3.5D, 1.0D, 0.0D, this.theme.getColortocolor(0), new Color(100, 100, 110, 0));
      RenderUtils2.drawRoundOutline((double)x, (double)y, watermarkWidth + 5.0D, 20.0D, 1.0D, 0.0D, new Color(0, 0, 0, 50), new Color(100, 100, 110, 0));
      RenderUtils2.drawBloomShadow(x, y, (float)(watermarkWidth + (double)x + 3.4D), 2.0F + y, 10, 4, this.theme.getColor(100), false);
      this.productSans.drawString(" §7|§f Public §7(" + ClientSession.username + ") §7|§f " + ServerUtil.getCurrentServer() + "  ", (double)(x + 18.0F) + this.productSans.getStringWidth("§fFlap"), (double)(y + 6.5F), Color.WHITE.getRGB());
      this.productSansBold.drawString("Flap", x + 18.0F, y + 6.5F, this.theme.getColor(0));
      DrawUtil.drawImage(new ResourceLocation("flap/flap.png"), (int)x + 5, (int)((double)((int)y) + 5.5D), 12, 12);
      this.width = (int)(watermarkWidth + 3.0D);
      this.height = 15;
   }

   private void renderLogo() {
      Objects.requireNonNull(Flap.instance);
      String clientName = "Flap";
      (new StringBuilder()).append(String.valueOf(clientName.charAt(0))).append(ChatFormatting.WHITE).append(clientName.substring(1, clientName.length())).toString();
      float x = (float)this.posX.getValue();
      float y = (float)this.posY.getValue();
      this.drawStringWithShadow(" ", x + 1.0F, y + 2.5F, this.theme.getColor(0));
      DrawUtil.drawImage(new ResourceLocation("flap/flap.png"), (int)x + 2, (int)y, 30, 30);
      this.height = 15;
   }

   private void renderSimple() {
      Objects.requireNonNull(Flap.instance);
      String clientName = "Flap";
      (new StringBuilder()).append(String.valueOf(clientName.charAt(0))).append(ChatFormatting.WHITE).append(clientName.substring(1, clientName.length())).toString();
      String watermark = "      " + clientName + " ";
      double watermarkWidth = this.getStringWidth(watermark);
      float x = (float)this.posX.getValue();
      float y = (float)this.posY.getValue();
      this.productSansBold2.drawStringWithShadow(watermark, x + 3.0F, y + 6.5F, this.theme.getColor(0));
      DrawUtil.drawImage(new ResourceLocation("flap/flap.png"), (int)x + 5, (int)y + 4, 20, 20);
      this.width = (int)(watermarkWidth + 3.0D);
      this.height = 15;
   }

   private void drawStringWithShadow(String text, float x, float y, int color) {
      String var5 = this.mode.getMode();
      byte var6 = -1;
      switch(var5.hashCode()) {
      case -1818419758:
         if (var5.equals("Simple")) {
            var6 = 0;
         }
         break;
      case 78208:
         if (var5.equals("New")) {
            var6 = 1;
         }
         break;
      case 2374091:
         if (var5.equals("Logo")) {
            var6 = 2;
         }
      }

      switch(var6) {
      case 0:
         mc.fontRendererObj.drawStringWithShadow(text, x, y, color);
         break;
      case 1:
      case 2:
         this.productSans.drawStringWithShadow(text, x, y, color);
      }

   }

   private double getStringWidth(String s) {
      String var2 = this.mode.getMode();
      byte var3 = -1;
      switch(var2.hashCode()) {
      case -1818419758:
         if (var2.equals("Simple")) {
            var3 = 0;
         }
         break;
      case 78208:
         if (var2.equals("New")) {
            var3 = 1;
         }
         break;
      case 2374091:
         if (var2.equals("Logo")) {
            var3 = 2;
         }
      }

      switch(var3) {
      case 0:
         return (double)mc.fontRendererObj.getStringWidth(s);
      case 1:
      case 2:
         return this.productSans.getStringWidth(s);
      default:
         return (double)mc.fontRendererObj.getStringWidth(s);
      }
   }
}
