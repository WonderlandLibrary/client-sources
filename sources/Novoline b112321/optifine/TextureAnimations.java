package optifine;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
import javax.imageio.ImageIO;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.util.ResourceLocation;
import optifine.Config;
import optifine.MatchBlock;
import optifine.ResUtils;
import optifine.TextureAnimation;
import optifine.TextureUtils;

public class TextureAnimations {
   private static TextureAnimation[] textureAnimations = null;

   public static void reset() {
      textureAnimations = null;
   }

   public static void update() {
      textureAnimations = null;
      IResourcePack[] var0 = Config.getResourcePacks();
      textureAnimations = getTextureAnimations(var0);
      if(Config.isAnimatedTextures()) {
         updateAnimations();
      }

   }

   public static void updateCustomAnimations() {
      if(textureAnimations != null && Config.isAnimatedTextures()) {
         updateAnimations();
      }

   }

   public static void updateAnimations() {
      PacketRemapper[] var0 = MatchBlock.b();
      if(textureAnimations != null) {
         int var1 = 0;
         if(var1 < textureAnimations.length) {
            TextureAnimation var2 = textureAnimations[var1];
            var2.updateTexture();
            ++var1;
         }
      }

   }

   public static TextureAnimation[] getTextureAnimations(IResourcePack[] var0) {
      MatchBlock.b();
      ArrayList var2 = new ArrayList();
      int var3 = 0;
      if(var3 < var0.length) {
         IResourcePack var4 = var0[var3];
         TextureAnimation[] var5 = getTextureAnimations(var4);
         if(var5 != null) {
            var2.addAll(Arrays.asList(var5));
         }

         ++var3;
      }

      TextureAnimation[] var7 = (TextureAnimation[])((TextureAnimation[])((TextureAnimation[])var2.toArray(new TextureAnimation[var2.size()])));
      return var7;
   }

   public static TextureAnimation[] getTextureAnimations(IResourcePack var0) {
      MatchBlock.b();
      String[] var2 = ResUtils.collectFiles(var0, "mcpatcher/anim", ".properties", (String[])null);
      if(var2.length <= 0) {
         return null;
      } else {
         ArrayList var3 = new ArrayList();
         int var4 = 0;
         if(var4 < var2.length) {
            String var5 = var2[var4];
            Config.dbg("Texture animation: " + var5);

            try {
               ResourceLocation var6 = new ResourceLocation(var5);
               InputStream var7 = var0.getInputStream(var6);
               Properties var8 = new Properties();
               var8.load(var7);
               TextureAnimation var9 = makeTextureAnimation(var8, var6);
               ResourceLocation var10 = new ResourceLocation(var9.getDstTex());
               if(Config.getDefiningResourcePack(var10) != var0) {
                  Config.dbg("Skipped: " + var5 + ", target texture not loaded from same resource pack");
               }

               var3.add(var9);
            } catch (FileNotFoundException var11) {
               Config.warn("File not found: " + var11.getMessage());
            } catch (IOException var12) {
               var12.printStackTrace();
            }

            ++var4;
         }

         TextureAnimation[] var14 = (TextureAnimation[])((TextureAnimation[])((TextureAnimation[])var3.toArray(new TextureAnimation[var3.size()])));
         return var14;
      }
   }

   public static TextureAnimation makeTextureAnimation(Properties var0, ResourceLocation var1) {
      String var3 = var0.getProperty("from");
      String var4 = var0.getProperty("to");
      int var5 = Config.parseInt(var0.getProperty("x"), -1);
      MatchBlock.b();
      int var6 = Config.parseInt(var0.getProperty("y"), -1);
      int var7 = Config.parseInt(var0.getProperty("w"), -1);
      int var8 = Config.parseInt(var0.getProperty("h"), -1);
      if(var3 != null && var4 != null) {
         if(var5 >= 0 && var6 >= 0 && var7 >= 0 && var8 >= 0) {
            var3 = var3.trim();
            var4 = var4.trim();
            String var9 = TextureUtils.getBasePath(var1.getResourcePath());
            var3 = TextureUtils.fixResourcePath(var3, var9);
            var4 = TextureUtils.fixResourcePath(var4, var9);
            byte[] var10 = getCustomTextureData(var3, var7);
            Config.warn("TextureAnimation: Source texture not found: " + var4);
            return null;
         } else {
            Config.warn("TextureAnimation: Invalid coordinates");
            return null;
         }
      } else {
         Config.warn("TextureAnimation: Source or target texture not specified");
         return null;
      }
   }

   public static byte[] getCustomTextureData(String var0, int var1) {
      MatchBlock.b();
      byte[] var3 = b(var0, var1);
      if(var3 == null) {
         var3 = b("/anim" + var0, var1);
      }

      return var3;
   }

   private static byte[] b(String param0, int param1) {
      // $FF: Couldn't be decompiled
   }

   private static BufferedImage readTextureImage(InputStream var0) throws IOException {
      BufferedImage var1 = ImageIO.read(var0);
      var0.close();
      return var1;
   }

   public static BufferedImage scaleBufferedImage(BufferedImage var0, int var1, int var2) {
      BufferedImage var3 = new BufferedImage(var1, var2, 2);
      Graphics2D var4 = var3.createGraphics();
      var4.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
      var4.drawImage(var0, 0, 0, var1, var2, (ImageObserver)null);
      return var3;
   }

   private static FileNotFoundException a(FileNotFoundException var0) {
      return var0;
   }
}
