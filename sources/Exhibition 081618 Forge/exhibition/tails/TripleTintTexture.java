package exhibition.tails;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

public class TripleTintTexture extends AbstractTexture {
   private final String namespace;
   private final String texturename;
   private final int tint1;
   private final int tint2;
   private final int tint3;
   private static final int MINBRIGHTNESS = 22;

   public TripleTintTexture(String namespace, String texturename, int tint1, int tint2, int tint3) {
      this.namespace = namespace;
      this.texturename = texturename;
      this.tint1 = tint1;
      this.tint2 = tint2;
      this.tint3 = tint3;
   }

   public void loadTexture(IResourceManager p_110551_1_) throws IOException {
      this.deleteGlTexture();

      try {
         if (this.texturename != null) {
            InputStream inputstream = p_110551_1_.getResource(new ResourceLocation(this.namespace, this.texturename)).getInputStream();
            BufferedImage texture = ImageIO.read(inputstream);
            int w = texture.getWidth();
            int h = texture.getHeight();
            int length = w * h;
            int[] pixeldata = new int[w * h];
            texture.getRGB(0, 0, w, h, pixeldata, 0, w);

            for(int i = 0; i < length; ++i) {
               int c = pixeldata[i];
               int a = this.alpha(c);
               int r = this.red(c);
               int g = this.green(c);
               int b = this.blue(c);
               pixeldata[i] = this.colourise(r, this.tint1, g, this.tint2, b, this.tint3, a);
            }

            texture.setRGB(0, 0, w, h, pixeldata, 0, w);
            TextureUtil.uploadTextureImage(this.getGlTextureId(), texture);
         }
      } catch (IOException var14) {
         ;
      }

   }

   private int colourise(int tone, int c1, int weight1, int c2, int weight2, int c3, int a) {
      double w2 = (double)weight1 / 255.0D;
      double w3 = (double)weight2 / 255.0D;
      w2 *= 1.0D - w3;
      double w1 = 1.0D - (w2 + w3);
      double r1 = (double)this.scale(this.red(c1), 22) / 255.0D;
      double g1 = (double)this.scale(this.green(c1), 22) / 255.0D;
      double b1 = (double)this.scale(this.blue(c1), 22) / 255.0D;
      double r2 = (double)this.scale(this.red(c2), 22) / 255.0D;
      double g2 = (double)this.scale(this.green(c2), 22) / 255.0D;
      double b2 = (double)this.scale(this.blue(c2), 22) / 255.0D;
      double r3 = (double)this.scale(this.red(c3), 22) / 255.0D;
      double g3 = (double)this.scale(this.green(c3), 22) / 255.0D;
      double b3 = (double)this.scale(this.blue(c3), 22) / 255.0D;
      int rfinal = (int)Math.floor((double)tone * (r1 * w1 + r2 * w2 + r3 * w3));
      int gfinal = (int)Math.floor((double)tone * (g1 * w1 + g2 * w2 + g3 * w3));
      int bfinal = (int)Math.floor((double)tone * (b1 * w1 + b2 * w2 + b3 * w3));
      return this.compose(rfinal, gfinal, bfinal, a);
   }

   private int compose(int r, int g, int b, int a) {
      int rgb = (a << 8) + r;
      rgb = (rgb << 8) + g;
      rgb = (rgb << 8) + b;
      return rgb;
   }

   private int alpha(int c) {
      return c >> 24 & 255;
   }

   private int red(int c) {
      return c >> 16 & 255;
   }

   private int green(int c) {
      return c >> 8 & 255;
   }

   private int blue(int c) {
      return c & 255;
   }

   private int scale(int c, int min) {
      return min + (int)Math.floor((double)c * ((double)(255 - min) / 255.0D));
   }
}
