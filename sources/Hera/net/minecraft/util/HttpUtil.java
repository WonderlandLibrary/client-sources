/*     */ package net.minecraft.util;
/*     */ 
/*     */ import com.google.common.util.concurrent.ListenableFuture;
/*     */ import com.google.common.util.concurrent.ListeningExecutorService;
/*     */ import com.google.common.util.concurrent.MoreExecutors;
/*     */ import com.google.common.util.concurrent.ThreadFactoryBuilder;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.Proxy;
/*     */ import java.net.ServerSocket;
/*     */ import java.net.URL;
/*     */ import java.net.URLEncoder;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import org.apache.commons.io.FileUtils;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class HttpUtil
/*     */ {
/*  33 */   public static final ListeningExecutorService field_180193_a = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool((new ThreadFactoryBuilder()).setDaemon(true).setNameFormat("Downloader %d").build()));
/*     */ 
/*     */   
/*  36 */   private static final AtomicInteger downloadThreadsStarted = new AtomicInteger(0);
/*  37 */   private static final Logger logger = LogManager.getLogger();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String buildPostString(Map<String, Object> data) {
/*  44 */     StringBuilder stringbuilder = new StringBuilder();
/*     */     
/*  46 */     for (Map.Entry<String, Object> entry : data.entrySet()) {
/*     */       
/*  48 */       if (stringbuilder.length() > 0)
/*     */       {
/*  50 */         stringbuilder.append('&');
/*     */       }
/*     */ 
/*     */       
/*     */       try {
/*  55 */         stringbuilder.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
/*     */       }
/*  57 */       catch (UnsupportedEncodingException unsupportedencodingexception1) {
/*     */         
/*  59 */         unsupportedencodingexception1.printStackTrace();
/*     */       } 
/*     */       
/*  62 */       if (entry.getValue() != null) {
/*     */         
/*  64 */         stringbuilder.append('=');
/*     */ 
/*     */         
/*     */         try {
/*  68 */           stringbuilder.append(URLEncoder.encode(entry.getValue().toString(), "UTF-8"));
/*     */         }
/*  70 */         catch (UnsupportedEncodingException unsupportedencodingexception) {
/*     */           
/*  72 */           unsupportedencodingexception.printStackTrace();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  77 */     return stringbuilder.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String postMap(URL url, Map<String, Object> data, boolean skipLoggingErrors) {
/*  85 */     return post(url, buildPostString(data), skipLoggingErrors);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String post(URL url, String content, boolean skipLoggingErrors) {
/*     */     try {
/*  95 */       Proxy proxy = (MinecraftServer.getServer() == null) ? null : MinecraftServer.getServer().getServerProxy();
/*     */       
/*  97 */       if (proxy == null)
/*     */       {
/*  99 */         proxy = Proxy.NO_PROXY;
/*     */       }
/*     */       
/* 102 */       HttpURLConnection httpurlconnection = (HttpURLConnection)url.openConnection(proxy);
/* 103 */       httpurlconnection.setRequestMethod("POST");
/* 104 */       httpurlconnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
/* 105 */       httpurlconnection.setRequestProperty("Content-Length", (content.getBytes()).length);
/* 106 */       httpurlconnection.setRequestProperty("Content-Language", "en-US");
/* 107 */       httpurlconnection.setUseCaches(false);
/* 108 */       httpurlconnection.setDoInput(true);
/* 109 */       httpurlconnection.setDoOutput(true);
/* 110 */       DataOutputStream dataoutputstream = new DataOutputStream(httpurlconnection.getOutputStream());
/* 111 */       dataoutputstream.writeBytes(content);
/* 112 */       dataoutputstream.flush();
/* 113 */       dataoutputstream.close();
/* 114 */       BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(httpurlconnection.getInputStream()));
/* 115 */       StringBuffer stringbuffer = new StringBuffer();
/*     */       
/*     */       String s;
/* 118 */       while ((s = bufferedreader.readLine()) != null) {
/*     */         
/* 120 */         stringbuffer.append(s);
/* 121 */         stringbuffer.append('\r');
/*     */       } 
/*     */       
/* 124 */       bufferedreader.close();
/* 125 */       return stringbuffer.toString();
/*     */     }
/* 127 */     catch (Exception exception) {
/*     */       
/* 129 */       if (!skipLoggingErrors)
/*     */       {
/* 131 */         logger.error("Could not post to " + url, exception);
/*     */       }
/*     */       
/* 134 */       return "";
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static ListenableFuture<Object> downloadResourcePack(final File saveFile, final String packUrl, final Map<String, String> p_180192_2_, final int maxSize, final IProgressUpdate p_180192_4_, final Proxy p_180192_5_) {
/* 140 */     ListenableFuture<?> listenablefuture = field_180193_a.submit(new Runnable()
/*     */         {
/*     */           public void run()
/*     */           {
/* 144 */             HttpURLConnection httpurlconnection = null;
/* 145 */             InputStream inputstream = null;
/* 146 */             OutputStream outputstream = null;
/*     */             
/* 148 */             if (p_180192_4_ != null) {
/*     */               
/* 150 */               p_180192_4_.resetProgressAndMessage("Downloading Resource Pack");
/* 151 */               p_180192_4_.displayLoadingString("Making Request...");
/*     */             } 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             try {
/* 158 */               byte[] abyte = new byte[4096];
/* 159 */               URL url = new URL(packUrl);
/* 160 */               httpurlconnection = (HttpURLConnection)url.openConnection(p_180192_5_);
/* 161 */               float f = 0.0F;
/* 162 */               float f1 = p_180192_2_.entrySet().size();
/*     */               
/* 164 */               for (Map.Entry<String, String> entry : (Iterable<Map.Entry<String, String>>)p_180192_2_.entrySet()) {
/*     */                 
/* 166 */                 httpurlconnection.setRequestProperty(entry.getKey(), entry.getValue());
/*     */                 
/* 168 */                 if (p_180192_4_ != null)
/*     */                 {
/* 170 */                   p_180192_4_.setLoadingProgress((int)(++f / f1 * 100.0F));
/*     */                 }
/*     */               } 
/*     */               
/* 174 */               inputstream = httpurlconnection.getInputStream();
/* 175 */               f1 = httpurlconnection.getContentLength();
/* 176 */               int i = httpurlconnection.getContentLength();
/*     */               
/* 178 */               if (p_180192_4_ != null)
/*     */               {
/* 180 */                 p_180192_4_.displayLoadingString(String.format("Downloading file (%.2f MB)...", new Object[] { Float.valueOf(f1 / 1000.0F / 1000.0F) }));
/*     */               }
/*     */               
/* 183 */               if (saveFile.exists()) {
/*     */                 
/* 185 */                 long j = saveFile.length();
/*     */                 
/* 187 */                 if (j == i) {
/*     */                   
/* 189 */                   if (p_180192_4_ != null)
/*     */                   {
/* 191 */                     p_180192_4_.setDoneWorking();
/*     */                   }
/*     */                   
/*     */                   return;
/*     */                 } 
/*     */                 
/* 197 */                 HttpUtil.logger.warn("Deleting " + saveFile + " as it does not match what we currently have (" + i + " vs our " + j + ").");
/* 198 */                 FileUtils.deleteQuietly(saveFile);
/*     */               }
/* 200 */               else if (saveFile.getParentFile() != null) {
/*     */                 
/* 202 */                 saveFile.getParentFile().mkdirs();
/*     */               } 
/*     */               
/* 205 */               outputstream = new DataOutputStream(new FileOutputStream(saveFile));
/*     */               
/* 207 */               if (maxSize > 0 && f1 > maxSize) {
/*     */                 
/* 209 */                 if (p_180192_4_ != null)
/*     */                 {
/* 211 */                   p_180192_4_.setDoneWorking();
/*     */                 }
/*     */                 
/* 214 */                 throw new IOException("Filesize is bigger than maximum allowed (file is " + f + ", limit is " + maxSize + ")");
/*     */               } 
/*     */               
/* 217 */               int k = 0;
/*     */               
/* 219 */               while ((k = inputstream.read(abyte)) >= 0) {
/*     */                 
/* 221 */                 f += k;
/*     */                 
/* 223 */                 if (p_180192_4_ != null)
/*     */                 {
/* 225 */                   p_180192_4_.setLoadingProgress((int)(f / f1 * 100.0F));
/*     */                 }
/*     */                 
/* 228 */                 if (maxSize > 0 && f > maxSize) {
/*     */                   
/* 230 */                   if (p_180192_4_ != null)
/*     */                   {
/* 232 */                     p_180192_4_.setDoneWorking();
/*     */                   }
/*     */                   
/* 235 */                   throw new IOException("Filesize was bigger than maximum allowed (got >= " + f + ", limit was " + maxSize + ")");
/*     */                 } 
/*     */                 
/* 238 */                 if (Thread.interrupted()) {
/*     */                   
/* 240 */                   HttpUtil.logger.error("INTERRUPTED");
/*     */                   
/* 242 */                   if (p_180192_4_ != null)
/*     */                   {
/* 244 */                     p_180192_4_.setDoneWorking();
/*     */                   }
/*     */                   
/*     */                   return;
/*     */                 } 
/*     */                 
/* 250 */                 outputstream.write(abyte, 0, k);
/*     */               } 
/*     */               
/* 253 */               if (p_180192_4_ != null) {
/*     */                 
/* 255 */                 p_180192_4_.setDoneWorking();
/*     */                 
/*     */                 return;
/*     */               } 
/* 259 */             } catch (Throwable throwable) {
/*     */               
/* 261 */               throwable.printStackTrace();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             }
/*     */             finally {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 286 */               IOUtils.closeQuietly(inputstream);
/* 287 */               IOUtils.closeQuietly(outputstream);
/*     */             } 
/*     */           }
/*     */         });
/* 291 */     return (ListenableFuture)listenablefuture;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getSuitableLanPort() throws IOException {
/* 296 */     ServerSocket serversocket = null;
/* 297 */     int i = -1;
/*     */ 
/*     */     
/*     */     try {
/* 301 */       serversocket = new ServerSocket(0);
/* 302 */       i = serversocket.getLocalPort();
/*     */     } finally {
/*     */ 
/*     */       
/*     */       try {
/*     */         
/* 308 */         if (serversocket != null)
/*     */         {
/* 310 */           serversocket.close();
/*     */         }
/*     */       }
/* 313 */       catch (IOException iOException) {}
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 319 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String get(URL url) throws IOException {
/* 327 */     HttpURLConnection httpurlconnection = (HttpURLConnection)url.openConnection();
/* 328 */     httpurlconnection.setRequestMethod("GET");
/* 329 */     BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(httpurlconnection.getInputStream()));
/* 330 */     StringBuilder stringbuilder = new StringBuilder();
/*     */     
/*     */     String s;
/* 333 */     while ((s = bufferedreader.readLine()) != null) {
/*     */       
/* 335 */       stringbuilder.append(s);
/* 336 */       stringbuilder.append('\r');
/*     */     } 
/*     */     
/* 339 */     bufferedreader.close();
/* 340 */     return stringbuilder.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraf\\util\HttpUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */