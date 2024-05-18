/*     */ package optfine;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStream;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.URL;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.Minecraft;
/*     */ 
/*     */ public class HttpUtils
/*     */ {
/*     */   public static final String SERVER_URL = "http://s.optifine.net";
/*     */   public static final String POST_URL = "http://optifine.net";
/*     */   
/*     */   public static byte[] get(String p_get_0_) throws IOException {
/*     */     byte[] abyte1;
/*  20 */     HttpURLConnection httpurlconnection = null;
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  25 */       URL url = new URL(p_get_0_);
/*  26 */       httpurlconnection = (HttpURLConnection)url.openConnection(Minecraft.getMinecraft().getProxy());
/*  27 */       httpurlconnection.setDoInput(true);
/*  28 */       httpurlconnection.setDoOutput(false);
/*  29 */       httpurlconnection.connect();
/*     */       
/*  31 */       if (httpurlconnection.getResponseCode() / 100 != 2)
/*     */       {
/*  33 */         throw new IOException("HTTP response: " + httpurlconnection.getResponseCode());
/*     */       }
/*     */       
/*  36 */       InputStream inputstream = httpurlconnection.getInputStream();
/*  37 */       byte[] abyte = new byte[httpurlconnection.getContentLength()];
/*  38 */       int i = 0;
/*     */ 
/*     */       
/*     */       do {
/*  42 */         int j = inputstream.read(abyte, i, abyte.length - i);
/*     */         
/*  44 */         if (j < 0)
/*     */         {
/*  46 */           throw new IOException("Input stream closed: " + p_get_0_);
/*     */         }
/*     */         
/*  49 */         i += j;
/*     */       }
/*  51 */       while (i < abyte.length);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  57 */       abyte1 = abyte;
/*     */     }
/*     */     finally {
/*     */       
/*  61 */       if (httpurlconnection != null)
/*     */       {
/*  63 */         httpurlconnection.disconnect();
/*     */       }
/*     */     } 
/*     */     
/*  67 */     return abyte1;
/*     */   }
/*     */   
/*     */   public static String post(String p_post_0_, Map p_post_1_, byte[] p_post_2_) throws IOException {
/*     */     String s3;
/*  72 */     HttpURLConnection httpurlconnection = null;
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  77 */       URL url = new URL(p_post_0_);
/*  78 */       httpurlconnection = (HttpURLConnection)url.openConnection(Minecraft.getMinecraft().getProxy());
/*  79 */       httpurlconnection.setRequestMethod("POST");
/*     */       
/*  81 */       if (p_post_1_ != null)
/*     */       {
/*  83 */         for (Object s : p_post_1_.keySet()) {
/*     */           
/*  85 */           String s1 = (String)p_post_1_.get(s);
/*  86 */           httpurlconnection.setRequestProperty((String)s, s1);
/*     */         } 
/*     */       }
/*     */       
/*  90 */       httpurlconnection.setRequestProperty("Content-Type", "text/plain");
/*  91 */       httpurlconnection.setRequestProperty("Content-Length", p_post_2_.length);
/*  92 */       httpurlconnection.setRequestProperty("Content-Language", "en-US");
/*  93 */       httpurlconnection.setUseCaches(false);
/*  94 */       httpurlconnection.setDoInput(true);
/*  95 */       httpurlconnection.setDoOutput(true);
/*  96 */       OutputStream outputstream = httpurlconnection.getOutputStream();
/*  97 */       outputstream.write(p_post_2_);
/*  98 */       outputstream.flush();
/*  99 */       outputstream.close();
/* 100 */       InputStream inputstream = httpurlconnection.getInputStream();
/* 101 */       InputStreamReader inputstreamreader = new InputStreamReader(inputstream, "ASCII");
/* 102 */       BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
/* 103 */       StringBuffer stringbuffer = new StringBuffer();
/*     */       
/*     */       String s2;
/* 106 */       while ((s2 = bufferedreader.readLine()) != null) {
/*     */         
/* 108 */         stringbuffer.append(s2);
/* 109 */         stringbuffer.append('\r');
/*     */       } 
/*     */       
/* 112 */       bufferedreader.close();
/* 113 */       s3 = stringbuffer.toString();
/*     */     }
/*     */     finally {
/*     */       
/* 117 */       if (httpurlconnection != null)
/*     */       {
/* 119 */         httpurlconnection.disconnect();
/*     */       }
/*     */     } 
/*     */     
/* 123 */     return s3;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\optfine\HttpUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */