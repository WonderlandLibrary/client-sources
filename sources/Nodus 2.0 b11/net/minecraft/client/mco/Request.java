/*   1:    */ package net.minecraft.client.mco;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import java.io.OutputStream;
/*   6:    */ import java.net.HttpURLConnection;
/*   7:    */ import java.net.Proxy;
/*   8:    */ import java.net.URL;
/*   9:    */ 
/*  10:    */ public abstract class Request
/*  11:    */ {
/*  12:    */   protected HttpURLConnection field_148677_a;
/*  13:    */   private boolean field_148676_c;
/*  14:    */   protected String field_148675_b;
/*  15:    */   private static final String __OBFID = "CL_00001159";
/*  16:    */   
/*  17:    */   public Request(String par1Str, int par2, int par3)
/*  18:    */   {
/*  19:    */     try
/*  20:    */     {
/*  21: 21 */       this.field_148675_b = par1Str;
/*  22: 22 */       Proxy var4 = McoClientConfig.func_148685_a();
/*  23: 24 */       if (var4 != null) {
/*  24: 26 */         this.field_148677_a = ((HttpURLConnection)new URL(par1Str).openConnection(var4));
/*  25:    */       } else {
/*  26: 30 */         this.field_148677_a = ((HttpURLConnection)new URL(par1Str).openConnection());
/*  27:    */       }
/*  28: 33 */       this.field_148677_a.setConnectTimeout(par2);
/*  29: 34 */       this.field_148677_a.setReadTimeout(par3);
/*  30:    */     }
/*  31:    */     catch (Exception var5)
/*  32:    */     {
/*  33: 38 */       throw new ExceptionMcoHttp("Failed URL: " + par1Str, var5);
/*  34:    */     }
/*  35:    */   }
/*  36:    */   
/*  37:    */   public void func_148665_a(String p_148665_1_, String p_148665_2_)
/*  38:    */   {
/*  39: 44 */     String var3 = this.field_148677_a.getRequestProperty("Cookie");
/*  40: 46 */     if (var3 == null) {
/*  41: 48 */       this.field_148677_a.setRequestProperty("Cookie", p_148665_1_ + "=" + p_148665_2_);
/*  42:    */     } else {
/*  43: 52 */       this.field_148677_a.setRequestProperty("Cookie", var3 + ";" + p_148665_1_ + "=" + p_148665_2_);
/*  44:    */     }
/*  45:    */   }
/*  46:    */   
/*  47:    */   public int func_148671_a()
/*  48:    */   {
/*  49:    */     try
/*  50:    */     {
/*  51: 60 */       func_148667_e();
/*  52: 61 */       return this.field_148677_a.getResponseCode();
/*  53:    */     }
/*  54:    */     catch (Exception var2)
/*  55:    */     {
/*  56: 65 */       throw new ExceptionMcoHttp("Failed URL: " + this.field_148675_b, var2);
/*  57:    */     }
/*  58:    */   }
/*  59:    */   
/*  60:    */   public int func_148664_b()
/*  61:    */   {
/*  62: 71 */     String var1 = this.field_148677_a.getHeaderField("Retry-After");
/*  63:    */     try
/*  64:    */     {
/*  65: 75 */       return Integer.valueOf(var1).intValue();
/*  66:    */     }
/*  67:    */     catch (Exception var3) {}
/*  68: 79 */     return 5;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public String func_148659_d()
/*  72:    */   {
/*  73:    */     try
/*  74:    */     {
/*  75: 87 */       func_148667_e();
/*  76: 88 */       String var1 = func_148671_a() >= 400 ? func_148660_a(this.field_148677_a.getErrorStream()) : func_148660_a(this.field_148677_a.getInputStream());
/*  77: 89 */       func_148674_h();
/*  78: 90 */       return var1;
/*  79:    */     }
/*  80:    */     catch (IOException var2)
/*  81:    */     {
/*  82: 94 */       throw new ExceptionMcoHttp("Failed URL: " + this.field_148675_b, var2);
/*  83:    */     }
/*  84:    */   }
/*  85:    */   
/*  86:    */   private String func_148660_a(InputStream p_148660_1_)
/*  87:    */     throws IOException
/*  88:    */   {
/*  89:100 */     if (p_148660_1_ == null) {
/*  90:102 */       return "";
/*  91:    */     }
/*  92:106 */     StringBuilder var2 = new StringBuilder();
/*  93:108 */     for (int var3 = p_148660_1_.read(); var3 != -1; var3 = p_148660_1_.read()) {
/*  94:110 */       var2.append((char)var3);
/*  95:    */     }
/*  96:113 */     return var2.toString();
/*  97:    */   }
/*  98:    */   
/*  99:    */   private void func_148674_h()
/* 100:    */   {
/* 101:119 */     byte[] var1 = new byte[1024];
/* 102:    */     try
/* 103:    */     {
/* 104:124 */       boolean var2 = false;
/* 105:125 */       InputStream var3 = this.field_148677_a.getInputStream();
/* 106:129 */       while (var3.read(var1) > 0) {}
/* 107:131 */       var3.close();
/* 108:    */     }
/* 109:    */     catch (Exception var6)
/* 110:    */     {
/* 111:    */       try
/* 112:    */       {
/* 113:140 */         InputStream var3 = this.field_148677_a.getErrorStream();
/* 114:141 */         boolean var4 = false;
/* 115:143 */         if (var3 == null) {
/* 116:145 */           return;
/* 117:    */         }
/* 118:150 */         while (var3.read(var1) > 0) {}
/* 119:152 */         var3.close();
/* 120:    */       }
/* 121:    */       catch (IOException localIOException) {}
/* 122:    */     }
/* 123:    */   }
/* 124:    */   
/* 125:    */   protected Request func_148667_e()
/* 126:    */   {
/* 127:166 */     if (!this.field_148676_c)
/* 128:    */     {
/* 129:168 */       Request var1 = func_148662_f();
/* 130:169 */       this.field_148676_c = true;
/* 131:170 */       return var1;
/* 132:    */     }
/* 133:174 */     return this;
/* 134:    */   }
/* 135:    */   
/* 136:    */   protected abstract Request func_148662_f();
/* 137:    */   
/* 138:    */   public static Request func_148666_a(String p_148666_0_)
/* 139:    */   {
/* 140:182 */     return new Get(p_148666_0_, 5000, 10000);
/* 141:    */   }
/* 142:    */   
/* 143:    */   public static Request func_148670_a(String p_148670_0_, int p_148670_1_, int p_148670_2_)
/* 144:    */   {
/* 145:187 */     return new Get(p_148670_0_, p_148670_1_, p_148670_2_);
/* 146:    */   }
/* 147:    */   
/* 148:    */   public static Request func_148672_c(String p_148672_0_, String p_148672_1_)
/* 149:    */   {
/* 150:192 */     return new Post(p_148672_0_, p_148672_1_.getBytes(), 5000, 10000);
/* 151:    */   }
/* 152:    */   
/* 153:    */   public static Request func_148661_a(String p_148661_0_, String p_148661_1_, int p_148661_2_, int p_148661_3_)
/* 154:    */   {
/* 155:197 */     return new Post(p_148661_0_, p_148661_1_.getBytes(), p_148661_2_, p_148661_3_);
/* 156:    */   }
/* 157:    */   
/* 158:    */   public static Request func_148663_b(String p_148663_0_)
/* 159:    */   {
/* 160:202 */     return new Delete(p_148663_0_, 5000, 10000);
/* 161:    */   }
/* 162:    */   
/* 163:    */   public static Request func_148668_d(String p_148668_0_, String p_148668_1_)
/* 164:    */   {
/* 165:207 */     return new Put(p_148668_0_, p_148668_1_.getBytes(), 5000, 10000);
/* 166:    */   }
/* 167:    */   
/* 168:    */   public static Request func_148669_b(String p_148669_0_, String p_148669_1_, int p_148669_2_, int p_148669_3_)
/* 169:    */   {
/* 170:212 */     return new Put(p_148669_0_, p_148669_1_.getBytes(), p_148669_2_, p_148669_3_);
/* 171:    */   }
/* 172:    */   
/* 173:    */   public int func_148673_g()
/* 174:    */   {
/* 175:217 */     String var1 = this.field_148677_a.getHeaderField("Error-Code");
/* 176:    */     try
/* 177:    */     {
/* 178:221 */       return Integer.valueOf(var1).intValue();
/* 179:    */     }
/* 180:    */     catch (Exception var3) {}
/* 181:225 */     return -1;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public static class Delete
/* 185:    */     extends Request
/* 186:    */   {
/* 187:    */     private static final String __OBFID = "CL_00001160";
/* 188:    */     
/* 189:    */     public Delete(String par1Str, int par2, int par3)
/* 190:    */     {
/* 191:235 */       super(par2, par3);
/* 192:    */     }
/* 193:    */     
/* 194:    */     public Delete func_148662_f()
/* 195:    */     {
/* 196:    */       try
/* 197:    */       {
/* 198:242 */         this.field_148677_a.setDoOutput(true);
/* 199:243 */         this.field_148677_a.setRequestMethod("DELETE");
/* 200:244 */         this.field_148677_a.connect();
/* 201:245 */         return this;
/* 202:    */       }
/* 203:    */       catch (Exception var2)
/* 204:    */       {
/* 205:249 */         throw new ExceptionMcoHttp("Failed URL: " + this.field_148675_b, var2);
/* 206:    */       }
/* 207:    */     }
/* 208:    */   }
/* 209:    */   
/* 210:    */   public static class Put
/* 211:    */     extends Request
/* 212:    */   {
/* 213:    */     private byte[] field_148681_c;
/* 214:    */     private static final String __OBFID = "CL_00001163";
/* 215:    */     
/* 216:    */     public Put(String par1Str, byte[] par2ArrayOfByte, int par3, int par4)
/* 217:    */     {
/* 218:261 */       super(par3, par4);
/* 219:262 */       this.field_148681_c = par2ArrayOfByte;
/* 220:    */     }
/* 221:    */     
/* 222:    */     public Put func_148662_f()
/* 223:    */     {
/* 224:    */       try
/* 225:    */       {
/* 226:269 */         this.field_148677_a.setDoOutput(true);
/* 227:270 */         this.field_148677_a.setDoInput(true);
/* 228:271 */         this.field_148677_a.setRequestMethod("PUT");
/* 229:272 */         OutputStream var1 = this.field_148677_a.getOutputStream();
/* 230:273 */         var1.write(this.field_148681_c);
/* 231:274 */         var1.flush();
/* 232:275 */         return this;
/* 233:    */       }
/* 234:    */       catch (Exception var2)
/* 235:    */       {
/* 236:279 */         throw new ExceptionMcoHttp("Failed URL: " + this.field_148675_b, var2);
/* 237:    */       }
/* 238:    */     }
/* 239:    */   }
/* 240:    */   
/* 241:    */   public static class Get
/* 242:    */     extends Request
/* 243:    */   {
/* 244:    */     private static final String __OBFID = "CL_00001161";
/* 245:    */     
/* 246:    */     public Get(String par1Str, int par2, int par3)
/* 247:    */     {
/* 248:290 */       super(par2, par3);
/* 249:    */     }
/* 250:    */     
/* 251:    */     public Get func_148662_f()
/* 252:    */     {
/* 253:    */       try
/* 254:    */       {
/* 255:297 */         this.field_148677_a.setDoInput(true);
/* 256:298 */         this.field_148677_a.setDoOutput(true);
/* 257:299 */         this.field_148677_a.setUseCaches(false);
/* 258:300 */         this.field_148677_a.setRequestMethod("GET");
/* 259:301 */         return this;
/* 260:    */       }
/* 261:    */       catch (Exception var2)
/* 262:    */       {
/* 263:305 */         throw new ExceptionMcoHttp("Failed URL: " + this.field_148675_b, var2);
/* 264:    */       }
/* 265:    */     }
/* 266:    */   }
/* 267:    */   
/* 268:    */   public static class Post
/* 269:    */     extends Request
/* 270:    */   {
/* 271:    */     private byte[] field_148683_c;
/* 272:    */     private static final String __OBFID = "CL_00001162";
/* 273:    */     
/* 274:    */     public Post(String par1Str, byte[] par2ArrayOfByte, int par3, int par4)
/* 275:    */     {
/* 276:317 */       super(par3, par4);
/* 277:318 */       this.field_148683_c = par2ArrayOfByte;
/* 278:    */     }
/* 279:    */     
/* 280:    */     public Post func_148662_f()
/* 281:    */     {
/* 282:    */       try
/* 283:    */       {
/* 284:325 */         this.field_148677_a.setDoInput(true);
/* 285:326 */         this.field_148677_a.setDoOutput(true);
/* 286:327 */         this.field_148677_a.setUseCaches(false);
/* 287:328 */         this.field_148677_a.setRequestMethod("POST");
/* 288:329 */         OutputStream var1 = this.field_148677_a.getOutputStream();
/* 289:330 */         var1.write(this.field_148683_c);
/* 290:331 */         var1.flush();
/* 291:332 */         return this;
/* 292:    */       }
/* 293:    */       catch (Exception var2)
/* 294:    */       {
/* 295:336 */         throw new ExceptionMcoHttp("Failed URL: " + this.field_148675_b, var2);
/* 296:    */       }
/* 297:    */     }
/* 298:    */   }
/* 299:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.mco.Request
 * JD-Core Version:    0.7.0.1
 */