/*   1:    */ package net.minecraft.util;
/*   2:    */ 
/*   3:    */ import java.io.BufferedReader;
/*   4:    */ import java.io.DataOutputStream;
/*   5:    */ import java.io.File;
/*   6:    */ import java.io.FileOutputStream;
/*   7:    */ import java.io.IOException;
/*   8:    */ import java.io.InputStream;
/*   9:    */ import java.io.InputStreamReader;
/*  10:    */ import java.io.UnsupportedEncodingException;
/*  11:    */ import java.net.HttpURLConnection;
/*  12:    */ import java.net.Proxy;
/*  13:    */ import java.net.ServerSocket;
/*  14:    */ import java.net.URL;
/*  15:    */ import java.net.URLConnection;
/*  16:    */ import java.net.URLEncoder;
/*  17:    */ import java.util.Iterator;
/*  18:    */ import java.util.Map;
/*  19:    */ import java.util.Map.Entry;
/*  20:    */ import java.util.Set;
/*  21:    */ import java.util.concurrent.atomic.AtomicInteger;
/*  22:    */ import net.minecraft.server.MinecraftServer;
/*  23:    */ import org.apache.logging.log4j.LogManager;
/*  24:    */ import org.apache.logging.log4j.Logger;
/*  25:    */ 
/*  26:    */ public class HttpUtil
/*  27:    */ {
/*  28: 28 */   private static final AtomicInteger downloadThreadsStarted = new AtomicInteger(0);
/*  29: 29 */   private static final Logger logger = LogManager.getLogger();
/*  30:    */   private static final String __OBFID = "CL_00001485";
/*  31:    */   
/*  32:    */   public static String buildPostString(Map par0Map)
/*  33:    */   {
/*  34: 37 */     StringBuilder var1 = new StringBuilder();
/*  35: 38 */     Iterator var2 = par0Map.entrySet().iterator();
/*  36: 40 */     while (var2.hasNext())
/*  37:    */     {
/*  38: 42 */       Map.Entry var3 = (Map.Entry)var2.next();
/*  39: 44 */       if (var1.length() > 0) {
/*  40: 46 */         var1.append('&');
/*  41:    */       }
/*  42:    */       try
/*  43:    */       {
/*  44: 51 */         var1.append(URLEncoder.encode((String)var3.getKey(), "UTF-8"));
/*  45:    */       }
/*  46:    */       catch (UnsupportedEncodingException var6)
/*  47:    */       {
/*  48: 55 */         var6.printStackTrace();
/*  49:    */       }
/*  50: 58 */       if (var3.getValue() != null)
/*  51:    */       {
/*  52: 60 */         var1.append('=');
/*  53:    */         try
/*  54:    */         {
/*  55: 64 */           var1.append(URLEncoder.encode(var3.getValue().toString(), "UTF-8"));
/*  56:    */         }
/*  57:    */         catch (UnsupportedEncodingException var5)
/*  58:    */         {
/*  59: 68 */           var5.printStackTrace();
/*  60:    */         }
/*  61:    */       }
/*  62:    */     }
/*  63: 73 */     return var1.toString();
/*  64:    */   }
/*  65:    */   
/*  66:    */   public static String func_151226_a(URL p_151226_0_, Map p_151226_1_, boolean p_151226_2_)
/*  67:    */   {
/*  68: 78 */     return func_151225_a(p_151226_0_, buildPostString(p_151226_1_), p_151226_2_);
/*  69:    */   }
/*  70:    */   
/*  71:    */   private static String func_151225_a(URL p_151225_0_, String p_151225_1_, boolean p_151225_2_)
/*  72:    */   {
/*  73:    */     try
/*  74:    */     {
/*  75: 85 */       Proxy var3 = MinecraftServer.getServer() == null ? null : MinecraftServer.getServer().getServerProxy();
/*  76: 87 */       if (var3 == null) {
/*  77: 89 */         var3 = Proxy.NO_PROXY;
/*  78:    */       }
/*  79: 92 */       HttpURLConnection var4 = (HttpURLConnection)p_151225_0_.openConnection(var3);
/*  80: 93 */       var4.setRequestMethod("POST");
/*  81: 94 */       var4.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
/*  82: 95 */       var4.setRequestProperty("Content-Length", p_151225_1_.getBytes().length);
/*  83: 96 */       var4.setRequestProperty("Content-Language", "en-US");
/*  84: 97 */       var4.setUseCaches(false);
/*  85: 98 */       var4.setDoInput(true);
/*  86: 99 */       var4.setDoOutput(true);
/*  87:100 */       DataOutputStream var5 = new DataOutputStream(var4.getOutputStream());
/*  88:101 */       var5.writeBytes(p_151225_1_);
/*  89:102 */       var5.flush();
/*  90:103 */       var5.close();
/*  91:104 */       BufferedReader var6 = new BufferedReader(new InputStreamReader(var4.getInputStream()));
/*  92:105 */       StringBuffer var8 = new StringBuffer();
/*  93:    */       String var7;
/*  94:108 */       while ((var7 = var6.readLine()) != null)
/*  95:    */       {
/*  96:    */         String var7;
/*  97:110 */         var8.append(var7);
/*  98:111 */         var8.append('\r');
/*  99:    */       }
/* 100:114 */       var6.close();
/* 101:115 */       return var8.toString();
/* 102:    */     }
/* 103:    */     catch (Exception var9)
/* 104:    */     {
/* 105:119 */       if (!p_151225_2_) {
/* 106:121 */         logger.error("Could not post to " + p_151225_0_, var9);
/* 107:    */       }
/* 108:    */     }
/* 109:124 */     return "";
/* 110:    */   }
/* 111:    */   
/* 112:    */   public static void func_151223_a(final File p_151223_0_, final String p_151223_1_, final DownloadListener p_151223_2_, final Map p_151223_3_, final int p_151223_4_, IProgressUpdate p_151223_5_, final Proxy p_151223_6_)
/* 113:    */   {
/* 114:130 */     Thread var7 = new Thread(new Runnable()
/* 115:    */     {
/* 116:    */       private static final String __OBFID = "CL_00001486";
/* 117:    */       
/* 118:    */       public void run()
/* 119:    */       {
/* 120:135 */         URLConnection var1 = null;
/* 121:136 */         InputStream var2 = null;
/* 122:137 */         DataOutputStream var3 = null;
/* 123:139 */         if (HttpUtil.this != null)
/* 124:    */         {
/* 125:141 */           HttpUtil.this.resetProgressAndMessage("Downloading Texture Pack");
/* 126:142 */           HttpUtil.this.resetProgresAndWorkingMessage("Making Request...");
/* 127:    */         }
/* 128:    */         try
/* 129:    */         {
/* 130:149 */           byte[] var4 = new byte[4096];
/* 131:150 */           URL var5 = new URL(p_151223_1_);
/* 132:151 */           var1 = var5.openConnection(p_151223_6_);
/* 133:152 */           float var6 = 0.0F;
/* 134:153 */           float var7 = p_151223_3_.entrySet().size();
/* 135:154 */           Iterator var8 = p_151223_3_.entrySet().iterator();
/* 136:156 */           while (var8.hasNext())
/* 137:    */           {
/* 138:158 */             Map.Entry var9 = (Map.Entry)var8.next();
/* 139:159 */             var1.setRequestProperty((String)var9.getKey(), (String)var9.getValue());
/* 140:161 */             if (HttpUtil.this != null) {
/* 141:163 */               HttpUtil.this.setLoadingProgress((int)(++var6 / var7 * 100.0F));
/* 142:    */             }
/* 143:    */           }
/* 144:167 */           var2 = var1.getInputStream();
/* 145:168 */           var7 = var1.getContentLength();
/* 146:169 */           int var28 = var1.getContentLength();
/* 147:171 */           if (HttpUtil.this != null) {
/* 148:173 */             HttpUtil.this.resetProgresAndWorkingMessage(String.format("Downloading file (%.2f MB)...", new Object[] { Float.valueOf(var7 / 1000.0F / 1000.0F) }));
/* 149:    */           }
/* 150:176 */           if (p_151223_0_.exists())
/* 151:    */           {
/* 152:178 */             long var29 = p_151223_0_.length();
/* 153:180 */             if (var29 == var28)
/* 154:    */             {
/* 155:182 */               p_151223_2_.func_148522_a(p_151223_0_);
/* 156:184 */               if (HttpUtil.this != null) {
/* 157:186 */                 HttpUtil.this.func_146586_a();
/* 158:    */               }
/* 159:189 */               return;
/* 160:    */             }
/* 161:192 */             HttpUtil.logger.warn("Deleting " + p_151223_0_ + " as it does not match what we currently have (" + var28 + " vs our " + var29 + ").");
/* 162:193 */             p_151223_0_.delete();
/* 163:    */           }
/* 164:195 */           else if (p_151223_0_.getParentFile() != null)
/* 165:    */           {
/* 166:197 */             p_151223_0_.getParentFile().mkdirs();
/* 167:    */           }
/* 168:200 */           var3 = new DataOutputStream(new FileOutputStream(p_151223_0_));
/* 169:202 */           if ((p_151223_4_ > 0) && (var7 > p_151223_4_))
/* 170:    */           {
/* 171:204 */             if (HttpUtil.this != null) {
/* 172:206 */               HttpUtil.this.func_146586_a();
/* 173:    */             }
/* 174:209 */             throw new IOException("Filesize is bigger than maximum allowed (file is " + var6 + ", limit is " + p_151223_4_ + ")");
/* 175:    */           }
/* 176:212 */           boolean var31 = false;
/* 177:    */           int var30;
/* 178:215 */           while ((var30 = var2.read(var4)) >= 0)
/* 179:    */           {
/* 180:    */             int var30;
/* 181:217 */             var6 += var30;
/* 182:219 */             if (HttpUtil.this != null) {
/* 183:221 */               HttpUtil.this.setLoadingProgress((int)(var6 / var7 * 100.0F));
/* 184:    */             }
/* 185:224 */             if ((p_151223_4_ > 0) && (var6 > p_151223_4_))
/* 186:    */             {
/* 187:226 */               if (HttpUtil.this != null) {
/* 188:228 */                 HttpUtil.this.func_146586_a();
/* 189:    */               }
/* 190:231 */               throw new IOException("Filesize was bigger than maximum allowed (got >= " + var6 + ", limit was " + p_151223_4_ + ")");
/* 191:    */             }
/* 192:234 */             var3.write(var4, 0, var30);
/* 193:    */           }
/* 194:237 */           p_151223_2_.func_148522_a(p_151223_0_);
/* 195:239 */           if (HttpUtil.this != null)
/* 196:    */           {
/* 197:241 */             HttpUtil.this.func_146586_a();
/* 198:242 */             return;
/* 199:    */           }
/* 200:    */         }
/* 201:    */         catch (Throwable var26)
/* 202:    */         {
/* 203:247 */           var26.printStackTrace();
/* 204:    */         }
/* 205:    */         finally
/* 206:    */         {
/* 207:    */           try
/* 208:    */           {
/* 209:254 */             if (var2 != null) {
/* 210:256 */               var2.close();
/* 211:    */             }
/* 212:    */           }
/* 213:    */           catch (IOException localIOException4) {}
/* 214:    */           try
/* 215:    */           {
/* 216:266 */             if (var3 != null) {
/* 217:268 */               var3.close();
/* 218:    */             }
/* 219:    */           }
/* 220:    */           catch (IOException localIOException5) {}
/* 221:    */         }
/* 222:    */         try
/* 223:    */         {
/* 224:254 */           if (var2 != null) {
/* 225:256 */             var2.close();
/* 226:    */           }
/* 227:    */         }
/* 228:    */         catch (IOException localIOException6) {}
/* 229:    */         try
/* 230:    */         {
/* 231:266 */           if (var3 != null) {
/* 232:268 */             var3.close();
/* 233:    */           }
/* 234:    */         }
/* 235:    */         catch (IOException localIOException7) {}
/* 236:    */       }
/* 237:277 */     }, "File Downloader #" + downloadThreadsStarted.incrementAndGet());
/* 238:278 */     var7.setDaemon(true);
/* 239:279 */     var7.start();
/* 240:    */   }
/* 241:    */   
/* 242:    */   public static int func_76181_a()
/* 243:    */     throws IOException
/* 244:    */   {
/* 245:284 */     ServerSocket var0 = null;
/* 246:285 */     boolean var1 = true;
/* 247:    */     int var10;
/* 248:    */     try
/* 249:    */     {
/* 250:290 */       var0 = new ServerSocket(0);
/* 251:291 */       var10 = var0.getLocalPort();
/* 252:    */     }
/* 253:    */     finally
/* 254:    */     {
/* 255:    */       try
/* 256:    */       {
/* 257:    */         int var10;
/* 258:297 */         if (var0 != null) {
/* 259:299 */           var0.close();
/* 260:    */         }
/* 261:    */       }
/* 262:    */       catch (IOException localIOException) {}
/* 263:    */     }
/* 264:308 */     return var10;
/* 265:    */   }
/* 266:    */   
/* 267:    */   public static abstract interface DownloadListener
/* 268:    */   {
/* 269:    */     public abstract void func_148522_a(File paramFile);
/* 270:    */   }
/* 271:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.util.HttpUtil
 * JD-Core Version:    0.7.0.1
 */