/*     */ package nightmare.utils.staffanalyser;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.PrintWriter;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import org.apache.commons.io.Charsets;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.apache.commons.lang3.Validate;
/*     */ 
/*     */ public class HttpUtils
/*     */ {
/*     */   public static HttpURLConnection createUrlConnection(URL url) throws IOException {
/*  18 */     Validate.notNull(url);
/*  19 */     HttpURLConnection connection = (HttpURLConnection)url.openConnection();
/*  20 */     connection.setConnectTimeout(15000);
/*  21 */     connection.setReadTimeout(15000);
/*  22 */     connection.setUseCaches(false);
/*  23 */     return connection;
/*     */   }
/*     */   
/*     */   public static String performGetRequest(URL url, boolean withKey) throws IOException {
/*  27 */     return (new HttpUtils()).performGetRequestWithoutStatic(url, withKey);
/*     */   }
/*     */   
/*     */   public static String performGetRequest(URL url) throws IOException {
/*  31 */     return (new HttpUtils()).performGetRequestWithoutStatic(url, false);
/*     */   }
/*     */   public String performGetRequestWithoutStatic(URL url, boolean withKey) throws IOException {
/*     */     String var6;
/*  35 */     Validate.notNull(url);
/*     */     
/*  37 */     HttpURLConnection connection = createUrlConnection(url);
/*  38 */     InputStream inputStream = null;
/*  39 */     connection.setRequestProperty("user-agent", "Mozilla/5.0 AppIeWebKit");
/*     */     
/*  41 */     if (withKey) {
/*  42 */       connection.setRequestProperty("xf-api-key", "LnM-qSeQqtJlJmJnVt76GhU-SoiolWs9");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  49 */       inputStream = connection.getInputStream();
/*  50 */       return IOUtils.toString(inputStream, Charsets.UTF_8);
/*  51 */     } catch (IOException var10) {
/*  52 */       IOUtils.closeQuietly(inputStream);
/*  53 */       inputStream = connection.getErrorStream();
/*  54 */       if (inputStream == null) {
/*  55 */         throw var10;
/*     */       }
/*     */ 
/*     */       
/*  59 */       String result = IOUtils.toString(inputStream, Charsets.UTF_8);
/*  60 */       var6 = result;
/*     */     } finally {
/*  62 */       IOUtils.closeQuietly(inputStream);
/*     */     } 
/*     */     
/*  65 */     return var6;
/*     */   }
/*     */   
/*     */   public static String sendPost(String url, String param) {
/*  69 */     PrintWriter out = null;
/*  70 */     BufferedReader in = null;
/*  71 */     String result = "";
/*     */     try {
/*  73 */       URL realUrl = new URL(url);
/*  74 */       URLConnection conn = realUrl.openConnection();
/*  75 */       conn.setRequestProperty("accept", "*/*");
/*  76 */       conn.setRequestProperty("connection", "Keep-Alive");
/*  77 */       conn.setRequestProperty("user-agent", "Mozilla/5.0 AppIeWebKit");
/*  78 */       conn.setDoOutput(true);
/*  79 */       conn.setDoInput(true);
/*  80 */       out = new PrintWriter(conn.getOutputStream());
/*  81 */       out.print(param);
/*  82 */       out.flush();
/*  83 */       in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
/*     */       String line;
/*  85 */       while ((line = in.readLine()) != null) {
/*  86 */         result = result + line;
/*     */       }
/*  88 */     } catch (Exception e) {
/*  89 */       e.printStackTrace();
/*     */     } finally {
/*     */       
/*     */       try {
/*  93 */         if (out != null) {
/*  94 */           out.close();
/*     */         }
/*  96 */         if (in != null) {
/*  97 */           in.close();
/*     */         }
/*     */       }
/* 100 */       catch (IOException ex) {
/* 101 */         ex.printStackTrace();
/*     */       } 
/*     */     } 
/* 104 */     return result;
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmar\\utils\staffanalyser\HttpUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */