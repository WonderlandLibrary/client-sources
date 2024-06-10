/*  1:   */ package net.minecraft.src;
/*  2:   */ 
/*  3:   */ import java.io.InputStream;
/*  4:   */ import java.net.HttpURLConnection;
/*  5:   */ import java.net.URL;
/*  6:   */ 
/*  7:   */ public class VersionCheckThread
/*  8:   */   extends Thread
/*  9:   */ {
/* 10:   */   public void run()
/* 11:   */   {
/* 12:11 */     HttpURLConnection conn = null;
/* 13:   */     try
/* 14:   */     {
/* 15:15 */       Config.dbg("Checking for new version");
/* 16:16 */       URL e = new URL("http://optifine.net/version/1.7.2/HD_U.txt");
/* 17:17 */       conn = (HttpURLConnection)e.openConnection();
/* 18:18 */       conn.setDoInput(true);
/* 19:19 */       conn.setDoOutput(false);
/* 20:20 */       conn.connect();
/* 21:   */       try
/* 22:   */       {
/* 23:24 */         InputStream in = conn.getInputStream();
/* 24:25 */         String verStr = Config.readInputStream(in);
/* 25:26 */         in.close();
/* 26:27 */         String[] verLines = Config.tokenize(verStr, "\n\r");
/* 27:29 */         if (verLines.length < 1) {
/* 28:31 */           return;
/* 29:   */         }
/* 30:34 */         String newVer = verLines[0];
/* 31:35 */         Config.dbg("Version found: " + newVer);
/* 32:37 */         if (Config.compareRelease(newVer, "C2") > 0)
/* 33:   */         {
/* 34:39 */           Config.setNewRelease(newVer);
/* 35:40 */           return;
/* 36:   */         }
/* 37:   */       }
/* 38:   */       finally
/* 39:   */       {
/* 40:45 */         if (conn != null) {
/* 41:47 */           conn.disconnect();
/* 42:   */         }
/* 43:   */       }
/* 44:45 */       if (conn != null) {
/* 45:47 */         conn.disconnect();
/* 46:   */       }
/* 47:   */     }
/* 48:   */     catch (Exception var11)
/* 49:   */     {
/* 50:53 */       Config.dbg(var11.getClass().getName() + ": " + var11.getMessage());
/* 51:   */     }
/* 52:   */   }
/* 53:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.src.VersionCheckThread
 * JD-Core Version:    0.7.0.1
 */