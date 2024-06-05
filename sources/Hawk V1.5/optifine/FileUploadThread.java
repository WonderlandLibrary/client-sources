package optifine;

import java.util.Map;

public class FileUploadThread extends Thread {
   private Map headers;
   private String urlString;
   private byte[] content;
   private IFileUploadListener listener;

   public IFileUploadListener getListener() {
      return this.listener;
   }

   public void run() {
      try {
         HttpUtils.post(this.urlString, this.headers, this.content);
         this.listener.fileUploadFinished(this.urlString, this.content, (Throwable)null);
      } catch (Exception var2) {
         this.listener.fileUploadFinished(this.urlString, this.content, var2);
      }

   }

   public FileUploadThread(String var1, Map var2, byte[] var3, IFileUploadListener var4) {
      this.urlString = var1;
      this.headers = var2;
      this.content = var3;
      this.listener = var4;
   }

   public byte[] getContent() {
      return this.content;
   }

   public String getUrlString() {
      return this.urlString;
   }
}
