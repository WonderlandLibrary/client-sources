package optifine;

import net.minecraft.client.Minecraft;

public class FileDownloadThread extends Thread {
   private IFileDownloadListener listener = null;
   private String urlString = null;

   public void run() {
      try {
         byte[] var1 = HttpPipeline.get(this.urlString, Minecraft.getMinecraft().getProxy());
         this.listener.fileDownloadFinished(this.urlString, var1, (Throwable)null);
      } catch (Exception var2) {
         this.listener.fileDownloadFinished(this.urlString, (byte[])null, var2);
      }

   }

   public FileDownloadThread(String var1, IFileDownloadListener var2) {
      this.urlString = var1;
      this.listener = var2;
   }

   public IFileDownloadListener getListener() {
      return this.listener;
   }

   public String getUrlString() {
      return this.urlString;
   }
}
