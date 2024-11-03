package vestige.font;

import java.awt.Font;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class FontManager {
   private VestigeFontRenderer productSan = new VestigeFontRenderer(this.getFontFromTTF("product_sans", 17.0F, 0), true, true);
   private VestigeFontRenderer productSanBold = new VestigeFontRenderer(this.getFontFromTTF("OpenSans-Bold", 17.0F, 0), true, true);
   private VestigeFontRenderer productSan14 = new VestigeFontRenderer(this.getFontFromTTF("product_sans", 14.0F, 0), true, true);
   private VestigeFontRenderer productSansBold30 = new VestigeFontRenderer(this.getFontFromTTF("OpenSans-Bold", 30.0F, 0), true, true);
   private VestigeFontRenderer productSans30 = new VestigeFontRenderer(this.getFontFromTTF("product_sans", 30.0F, 0), true, true);
   private VestigeFontRenderer productSans17 = new VestigeFontRenderer(this.getFontFromTTF("product_sans", 17.0F, 0), true, true);
   private VestigeFontRenderer productSansBold20 = new VestigeFontRenderer(this.getFontFromTTF("OpenSans-Bold", 20.0F, 0), true, true);
   private VestigeFontRenderer productSans = new VestigeFontRenderer(this.getFontFromTTF("product_sans", 20.0F, 0), true, true);
   private VestigeFontRenderer productSans19 = new VestigeFontRenderer(this.getFontFromTTF("product_sans", 19.0F, 0), true, true);
   private VestigeFontRenderer productSansTitle = new VestigeFontRenderer(this.getFontFromTTF("product_sans", 34.0F, 0), true, true);
   private VestigeFontRenderer comfortaa = new VestigeFontRenderer(this.getFontFromTTF("comfortaa", 19.0F, 0), true, true);
   private VestigeFontRenderer productSan15 = new VestigeFontRenderer(this.getFontFromTTF("product_sans", 15.0F, 0), true, true);

   public Font getFontFromTTF(String fontName, float fontSize, int fontType) {
      Font output = null;
      ResourceLocation fontLocation = new ResourceLocation("flap/fonts/" + fontName + ".ttf");

      try {
         output = Font.createFont(fontType, Minecraft.getMinecraft().getResourceManager().getResource(fontLocation).getInputStream());
         output = output.deriveFont(fontSize);
      } catch (Exception var7) {
         var7.printStackTrace();
      }

      return output;
   }

   public VestigeFontRenderer getProductSan() {
      return this.productSan;
   }

   public VestigeFontRenderer getProductSanBold() {
      return this.productSanBold;
   }

   public VestigeFontRenderer getProductSan14() {
      return this.productSan14;
   }

   public VestigeFontRenderer getProductSansBold30() {
      return this.productSansBold30;
   }

   public VestigeFontRenderer getProductSans30() {
      return this.productSans30;
   }

   public VestigeFontRenderer getProductSans17() {
      return this.productSans17;
   }

   public VestigeFontRenderer getProductSansBold20() {
      return this.productSansBold20;
   }

   public VestigeFontRenderer getProductSans() {
      return this.productSans;
   }

   public VestigeFontRenderer getProductSans19() {
      return this.productSans19;
   }

   public VestigeFontRenderer getProductSansTitle() {
      return this.productSansTitle;
   }

   public VestigeFontRenderer getComfortaa() {
      return this.comfortaa;
   }

   public VestigeFontRenderer getProductSan15() {
      return this.productSan15;
   }
}
