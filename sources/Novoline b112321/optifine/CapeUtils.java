package optifine;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import net.Uv;
import net.aeQ;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.util.ResourceLocation;
import optifine.CapeUtils$1;
import optifine.MatchBlock;
import org.apache.commons.io.FilenameUtils;

public class CapeUtils {
   public static void downloadCape(AbstractClientPlayer var0) {
      String var1 = var0.getNameClear();
      if(!var1.isEmpty()) {
         String var2 = "http://s.optifine.net/capes/" + var1 + ".png";
         String var3 = FilenameUtils.getBaseName(var2);
         ResourceLocation var4 = new ResourceLocation("capeof/" + var3);
         Uv var5 = Minecraft.getMinecraft().ab();
         ITextureObject var6 = var5.b(var4);
         if(var6 instanceof aeQ) {
            aeQ var7 = (aeQ)var6;
            if(var7.r != null) {
               if(var7.r.booleanValue()) {
                  var0.setLocationOfCape(var4);
               }

               return;
            }
         }

         CapeUtils$1 var9 = new CapeUtils$1(var0, var4);
         aeQ var8 = new aeQ((File)null, var2, (ResourceLocation)null, var9);
         var8.o = true;
         var5.a((ResourceLocation)var4, (ITextureObject)var8);
      }

   }

   public static BufferedImage parseCape(BufferedImage var0) {
      int var2 = 64;
      int var3 = 32;
      MatchBlock.b();
      int var4 = var0.getWidth();
      int var5 = var0.getHeight();
      if(var2 < var4 || var3 < var5) {
         var2 *= 2;
         var3 *= 2;
      }

      BufferedImage var7 = new BufferedImage(var2, var3, 2);
      Graphics var6 = var7.getGraphics();
      var6.drawImage(var0, 0, 0, (ImageObserver)null);
      var6.dispose();
      return var7;
   }
}
