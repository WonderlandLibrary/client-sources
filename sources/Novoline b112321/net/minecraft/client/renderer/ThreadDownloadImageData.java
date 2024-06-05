package net.minecraft.client.renderer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.util.concurrent.atomic.AtomicInteger;
import javax.imageio.ImageIO;
import net.aLZ;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ThreadDownloadImageData extends SimpleTexture {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final AtomicInteger threadDownloadCounter = new AtomicInteger(0);
   private final File cacheFile;
   private final String imageUrl;
   private final IImageBuffer imageBuffer;
   private BufferedImage bufferedImage;
   private Thread imageThread;
   private boolean textureUploaded;
   public Boolean imageFound = null;
   public boolean pipeline = false;

   public ThreadDownloadImageData(File var1, String var2, ResourceLocation var3, IImageBuffer var4) {
      super(var3);
      this.cacheFile = var1;
      this.imageUrl = var2;
      this.imageBuffer = var4;
   }

   private void checkTextureUploaded() {
      if(!this.textureUploaded && this.bufferedImage != null) {
         this.textureUploaded = true;
         if(this.textureLocation != null) {
            this.deleteGlTexture();
         }

         TextureUtil.uploadTextureImage(super.getGlTextureId(), this.bufferedImage);
      }

   }

   public int getGlTextureId() {
      this.checkTextureUploaded();
      return super.getGlTextureId();
   }

   public void setBufferedImage(BufferedImage var1) {
      this.bufferedImage = var1;
      if(this.imageBuffer != null) {
         this.imageBuffer.skinAvailable();
      }

      this.imageFound = Boolean.valueOf(this.bufferedImage != null);
   }

   public void loadTexture(IResourceManager var1) throws IOException {
      if(this.bufferedImage == null && this.textureLocation != null) {
         super.loadTexture(var1);
      }

      if(this.imageThread == null) {
         if(this.cacheFile != null && this.cacheFile.isFile()) {
            LOGGER.debug("Loading http texture from local cache ({})", new Object[]{this.cacheFile});

            try {
               this.bufferedImage = ImageIO.read(this.cacheFile);
               if(this.imageBuffer != null) {
                  this.setBufferedImage(this.imageBuffer.parseUserSkin(this.bufferedImage));
               }

               this.imageFound = Boolean.valueOf(this.bufferedImage != null);
            } catch (IOException var3) {
               LOGGER.error("Couldn\'t load skin " + this.cacheFile, var3);
               this.loadTextureFromServer();
            }
         } else {
            this.loadTextureFromServer();
         }
      }

   }

   protected void loadTextureFromServer() {
      this.imageThread = new aLZ(this, "Texture Downloader #" + threadDownloadCounter.incrementAndGet());
      this.imageThread.setDaemon(true);
      this.imageThread.start();
   }

   private boolean shouldPipeline() {
      if(!this.pipeline) {
         return false;
      } else {
         Proxy var1 = Minecraft.getInstance().getProxy();
         return (var1.type() == Type.DIRECT || var1.type() == Type.SOCKS) && this.imageUrl.startsWith("http://");
      }
   }

   private void loadPipelined() {
      // $FF: Couldn't be decompiled
   }

   static String access$000(ThreadDownloadImageData var0) {
      return var0.imageUrl;
   }

   static File access$100(ThreadDownloadImageData var0) {
      return var0.cacheFile;
   }

   static Logger access$200() {
      return LOGGER;
   }

   static boolean access$300(ThreadDownloadImageData var0) {
      return var0.shouldPipeline();
   }

   static void access$400(ThreadDownloadImageData var0) {
      var0.loadPipelined();
   }

   static IImageBuffer access$500(ThreadDownloadImageData var0) {
      return var0.imageBuffer;
   }

   static BufferedImage access$600(ThreadDownloadImageData var0) {
      return var0.bufferedImage;
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
