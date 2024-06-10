/*  1:   */ package net.minecraft.src;
/*  2:   */ 
/*  3:   */ import java.awt.image.BufferedImage;
/*  4:   */ import java.io.PrintStream;
/*  5:   */ import java.net.HttpURLConnection;
/*  6:   */ import java.net.URL;
/*  7:   */ import javax.imageio.ImageIO;
/*  8:   */ import net.minecraft.client.Minecraft;
/*  9:   */ import net.minecraft.client.renderer.IImageBuffer;
/* 10:   */ import net.minecraft.client.renderer.ThreadDownloadImageData;
/* 11:   */ 
/* 12:   */ public class ThreadDownloadImage
/* 13:   */   extends Thread
/* 14:   */ {
/* 15:   */   private ThreadDownloadImageData parent;
/* 16:   */   private String urlStr;
/* 17:   */   private IImageBuffer imageBuffer;
/* 18:   */   
/* 19:   */   public ThreadDownloadImage(ThreadDownloadImageData parent, String urlStr, IImageBuffer imageBuffer)
/* 20:   */   {
/* 21:19 */     this.parent = parent;
/* 22:20 */     this.urlStr = urlStr;
/* 23:21 */     this.imageBuffer = imageBuffer;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void run()
/* 27:   */   {
/* 28:26 */     HttpURLConnection conn = null;
/* 29:   */     try
/* 30:   */     {
/* 31:30 */       URL e = new URL(this.urlStr);
/* 32:31 */       conn = (HttpURLConnection)e.openConnection(Minecraft.getMinecraft().getProxy());
/* 33:32 */       conn.setDoInput(true);
/* 34:33 */       conn.setDoOutput(false);
/* 35:34 */       conn.connect();
/* 36:36 */       if (conn.getResponseCode() / 100 != 2) {
/* 37:38 */         return;
/* 38:   */       }
/* 39:41 */       BufferedImage var2 = ImageIO.read(conn.getInputStream());
/* 40:43 */       if (this.imageBuffer != null) {
/* 41:45 */         var2 = this.imageBuffer.parseUserSkin(var2);
/* 42:   */       }
/* 43:48 */       this.parent.func_147641_a(var2);
/* 44:49 */       return;
/* 45:   */     }
/* 46:   */     catch (Exception var7)
/* 47:   */     {
/* 48:53 */       System.out.println(var7.getClass().getName() + ": " + var7.getMessage());
/* 49:   */     }
/* 50:   */     finally
/* 51:   */     {
/* 52:57 */       if (conn != null) {
/* 53:59 */         conn.disconnect();
/* 54:   */       }
/* 55:   */     }
/* 56:   */   }
/* 57:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.src.ThreadDownloadImage
 * JD-Core Version:    0.7.0.1
 */