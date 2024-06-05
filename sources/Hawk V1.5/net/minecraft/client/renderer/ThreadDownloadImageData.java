package net.minecraft.client.renderer;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.net.Proxy.Type;
import java.util.concurrent.atomic.AtomicInteger;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import optifine.Config;
import optifine.HttpPipeline;
import optifine.HttpRequest;
import optifine.HttpResponse;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ThreadDownloadImageData extends SimpleTexture {
   private static final Logger logger = LogManager.getLogger();
   private static final AtomicInteger threadDownloadCounter = new AtomicInteger(0);
   public boolean pipeline = false;
   private static final String __OBFID = "CL_00001049";
   private Thread imageThread;
   private final IImageBuffer imageBuffer;
   private boolean textureUploaded;
   private final String imageUrl;
   private final File field_152434_e;
   public Boolean imageFound = null;
   private BufferedImage bufferedImage;

   static File access$2(ThreadDownloadImageData var0) {
      return var0.field_152434_e;
   }

   static boolean access$3(ThreadDownloadImageData var0) {
      return var0.shouldPipeline();
   }

   static Logger access$0() {
      return logger;
   }

   private void checkTextureUploaded() {
      if (!this.textureUploaded && this.bufferedImage != null) {
         this.textureUploaded = true;
         if (this.textureLocation != null) {
            this.deleteGlTexture();
         }

         TextureUtil.uploadTextureImage(super.getGlTextureId(), this.bufferedImage);
      }

   }

   public void setBufferedImage(BufferedImage var1) {
      this.bufferedImage = var1;
      if (this.imageBuffer != null) {
         this.imageBuffer.func_152634_a();
      }

      this.imageFound = this.bufferedImage != null;
   }

   static BufferedImage access$5(ThreadDownloadImageData var0) {
      return var0.bufferedImage;
   }

   public void loadTexture(IResourceManager var1) throws IOException {
      if (this.bufferedImage == null && this.textureLocation != null) {
         super.loadTexture(var1);
      }

      if (this.imageThread == null) {
         if (this.field_152434_e != null && this.field_152434_e.isFile()) {
            logger.debug("Loading http texture from local cache ({})", new Object[]{this.field_152434_e});

            try {
               this.bufferedImage = ImageIO.read(this.field_152434_e);
               if (this.imageBuffer != null) {
                  this.setBufferedImage(this.imageBuffer.parseUserSkin(this.bufferedImage));
               }

               this.imageFound = this.bufferedImage != null;
            } catch (IOException var3) {
               logger.error(String.valueOf((new StringBuilder("Couldn't load skin ")).append(this.field_152434_e)), var3);
               this.func_152433_a();
            }
         } else {
            this.func_152433_a();
         }
      }

   }

   public int getGlTextureId() {
      this.checkTextureUploaded();
      return super.getGlTextureId();
   }

   private boolean shouldPipeline() {
      if (!this.pipeline) {
         return false;
      } else {
         Proxy var1 = Minecraft.getMinecraft().getProxy();
         return var1.type() != Type.DIRECT && var1.type() != Type.SOCKS ? false : this.imageUrl.startsWith("http://");
      }
   }

   private void loadPipelined() {
      boolean var9 = false;

      ThreadDownloadImageData var10000;
      boolean var10001;
      label140: {
         label141: {
            label134: {
               try {
                  try {
                     var9 = true;
                     HttpRequest var1 = HttpPipeline.makeRequest(this.imageUrl, Minecraft.getMinecraft().getProxy());
                     HttpResponse var2 = HttpPipeline.executeRequest(var1);
                     if (var2.getStatus() / 100 != 2) {
                        var9 = false;
                        break label134;
                     }

                     byte[] var3 = var2.getBody();
                     ByteArrayInputStream var4 = new ByteArrayInputStream(var3);
                     BufferedImage var5;
                     if (this.field_152434_e != null) {
                        FileUtils.copyInputStreamToFile(var4, this.field_152434_e);
                        var5 = ImageIO.read(this.field_152434_e);
                     } else {
                        var5 = TextureUtil.func_177053_a(var4);
                     }

                     if (this.imageBuffer != null) {
                        var5 = this.imageBuffer.parseUserSkin(var5);
                     }

                     this.setBufferedImage(var5);
                  } catch (Exception var10) {
                     logger.error(String.valueOf((new StringBuilder("Couldn't download http texture: ")).append(var10.getClass().getName()).append(": ").append(var10.getMessage())));
                     var9 = false;
                     break label141;
                  }

                  var10000 = this;
                  if (this.bufferedImage != null) {
                     var10001 = true;
                     var9 = false;
                     break label140;
                  }

                  var9 = false;
               } finally {
                  if (var9) {
                     this.imageFound = this.bufferedImage != null;
                  }
               }

               var10001 = false;
               break label140;
            }

            this.imageFound = this.bufferedImage != null;
            return;
         }

         this.imageFound = this.bufferedImage != null;
         return;
      }

      var10000.imageFound = var10001;
   }

   protected void func_152433_a() {
      this.imageThread = new Thread(this, String.valueOf((new StringBuilder("Texture Downloader #")).append(threadDownloadCounter.incrementAndGet()))) {
         final ThreadDownloadImageData this$0;

         public void run() {
            HttpURLConnection var1 = null;
            ThreadDownloadImageData.access$0().debug("Downloading http texture from {} to {}", new Object[]{ThreadDownloadImageData.access$1(this.this$0), ThreadDownloadImageData.access$2(this.this$0)});
            if (ThreadDownloadImageData.access$3(this.this$0)) {
               ThreadDownloadImageData.access$4(this.this$0);
            } else {
               try {
                  var1 = (HttpURLConnection)(new URL(ThreadDownloadImageData.access$1(this.this$0))).openConnection(Minecraft.getMinecraft().getProxy());
                  var1.setDoInput(true);
                  var1.setDoOutput(false);
                  var1.connect();
                  if (var1.getResponseCode() / 100 != 2) {
                     if (var1.getErrorStream() != null) {
                        Config.readAll(var1.getErrorStream());
                     }

                     return;
                  }

                  BufferedImage var2;
                  if (ThreadDownloadImageData.access$2(this.this$0) != null) {
                     FileUtils.copyInputStreamToFile(var1.getInputStream(), ThreadDownloadImageData.access$2(this.this$0));
                     var2 = ImageIO.read(ThreadDownloadImageData.access$2(this.this$0));
                  } else {
                     var2 = TextureUtil.func_177053_a(var1.getInputStream());
                  }

                  if (ThreadDownloadImageData.access$6(this.this$0) != null) {
                     var2 = ThreadDownloadImageData.access$6(this.this$0).parseUserSkin(var2);
                  }

                  this.this$0.setBufferedImage(var2);
                  return;
               } catch (Exception var6) {
                  ThreadDownloadImageData.access$0().error(String.valueOf((new StringBuilder("Couldn't download http texture: ")).append(var6.getClass().getName()).append(": ").append(var6.getMessage())));
               } finally {
                  if (var1 != null) {
                     var1.disconnect();
                  }

                  this.this$0.imageFound = ThreadDownloadImageData.access$5(this.this$0) != null;
               }

            }
         }

         {
            this.this$0 = var1;
         }
      };
      this.imageThread.setDaemon(true);
      this.imageThread.start();
   }

   static String access$1(ThreadDownloadImageData var0) {
      return var0.imageUrl;
   }

   public ThreadDownloadImageData(File var1, String var2, ResourceLocation var3, IImageBuffer var4) {
      super(var3);
      this.field_152434_e = var1;
      this.imageUrl = var2;
      this.imageBuffer = var4;
   }

   static void access$4(ThreadDownloadImageData var0) {
      var0.loadPipelined();
   }

   static IImageBuffer access$6(ThreadDownloadImageData var0) {
      return var0.imageBuffer;
   }
}
