/*   1:    */ package net.minecraft.src;
/*   2:    */ 
/*   3:    */ import java.awt.Graphics2D;
/*   4:    */ import java.awt.RenderingHints;
/*   5:    */ import java.awt.image.BufferedImage;
/*   6:    */ import java.io.File;
/*   7:    */ import java.io.FileNotFoundException;
/*   8:    */ import java.io.IOException;
/*   9:    */ import java.io.InputStream;
/*  10:    */ import java.util.ArrayList;
/*  11:    */ import java.util.Arrays;
/*  12:    */ import java.util.Enumeration;
/*  13:    */ import java.util.Properties;
/*  14:    */ import java.util.zip.ZipEntry;
/*  15:    */ import java.util.zip.ZipFile;
/*  16:    */ import javax.imageio.ImageIO;
/*  17:    */ import net.minecraft.client.renderer.texture.ITextureObject;
/*  18:    */ import net.minecraft.client.resources.AbstractResourcePack;
/*  19:    */ import net.minecraft.client.resources.IResourcePack;
/*  20:    */ import net.minecraft.client.settings.GameSettings;
/*  21:    */ import net.minecraft.util.ResourceLocation;
/*  22:    */ 
/*  23:    */ public class TextureAnimations
/*  24:    */ {
/*  25: 26 */   private static TextureAnimation[] textureAnimations = null;
/*  26:    */   
/*  27:    */   public static void reset()
/*  28:    */   {
/*  29: 30 */     textureAnimations = null;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public static void update()
/*  33:    */   {
/*  34: 35 */     IResourcePack[] rps = Config.getResourcePacks();
/*  35: 36 */     textureAnimations = getTextureAnimations(rps);
/*  36: 37 */     updateAnimations();
/*  37:    */   }
/*  38:    */   
/*  39:    */   public static void updateCustomAnimations()
/*  40:    */   {
/*  41: 42 */     if (textureAnimations != null) {
/*  42: 44 */       if (Config.isAnimatedTextures()) {
/*  43: 46 */         updateAnimations();
/*  44:    */       }
/*  45:    */     }
/*  46:    */   }
/*  47:    */   
/*  48:    */   public static void updateAnimations()
/*  49:    */   {
/*  50: 53 */     if (textureAnimations != null) {
/*  51: 55 */       for (int i = 0; i < textureAnimations.length; i++)
/*  52:    */       {
/*  53: 57 */         TextureAnimation anim = textureAnimations[i];
/*  54: 58 */         anim.updateTexture();
/*  55:    */       }
/*  56:    */     }
/*  57:    */   }
/*  58:    */   
/*  59:    */   public static TextureAnimation[] getTextureAnimations(IResourcePack[] rps)
/*  60:    */   {
/*  61: 65 */     ArrayList list = new ArrayList();
/*  62: 67 */     for (int anims = 0; anims < rps.length; anims++)
/*  63:    */     {
/*  64: 69 */       IResourcePack rp = rps[anims];
/*  65: 70 */       TextureAnimation[] tas = getTextureAnimations(rp);
/*  66: 72 */       if (tas != null) {
/*  67: 74 */         list.addAll(Arrays.asList(tas));
/*  68:    */       }
/*  69:    */     }
/*  70: 78 */     TextureAnimation[] var5 = (TextureAnimation[])list.toArray(new TextureAnimation[list.size()]);
/*  71: 79 */     return var5;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public static TextureAnimation[] getTextureAnimations(IResourcePack rp)
/*  75:    */   {
/*  76: 84 */     if (!(rp instanceof AbstractResourcePack)) {
/*  77: 86 */       return null;
/*  78:    */     }
/*  79: 90 */     AbstractResourcePack arp = (AbstractResourcePack)rp;
/*  80: 91 */     File tpFile = ResourceUtils.getResourcePackFile(arp);
/*  81: 93 */     if (tpFile == null) {
/*  82: 95 */       return null;
/*  83:    */     }
/*  84: 97 */     if (!tpFile.exists()) {
/*  85: 99 */       return null;
/*  86:    */     }
/*  87:103 */     String[] animPropNames = null;
/*  88:105 */     if (tpFile.isFile()) {
/*  89:107 */       animPropNames = getAnimationPropertiesZip(tpFile);
/*  90:    */     } else {
/*  91:111 */       animPropNames = getAnimationPropertiesDir(tpFile);
/*  92:    */     }
/*  93:114 */     if (animPropNames == null) {
/*  94:116 */       return null;
/*  95:    */     }
/*  96:120 */     ArrayList list = new ArrayList();
/*  97:122 */     for (int anims = 0; anims < animPropNames.length; anims++)
/*  98:    */     {
/*  99:124 */       String propName = animPropNames[anims];
/* 100:125 */       Config.dbg("Texture animation: " + propName);
/* 101:    */       try
/* 102:    */       {
/* 103:129 */         ResourceLocation e = new ResourceLocation(propName);
/* 104:130 */         InputStream in = rp.getInputStream(e);
/* 105:131 */         Properties props = new Properties();
/* 106:132 */         props.load(in);
/* 107:133 */         TextureAnimation anim = makeTextureAnimation(props, e);
/* 108:135 */         if (anim != null)
/* 109:    */         {
/* 110:137 */           ResourceLocation locDstTex = new ResourceLocation(anim.getDstTex());
/* 111:139 */           if (Config.getDefiningResourcePack(locDstTex) != rp) {
/* 112:141 */             Config.dbg("Skipped: " + propName + ", target texture not loaded from same resource pack");
/* 113:    */           } else {
/* 114:145 */             list.add(anim);
/* 115:    */           }
/* 116:    */         }
/* 117:    */       }
/* 118:    */       catch (FileNotFoundException var12)
/* 119:    */       {
/* 120:151 */         Config.warn("File not found: " + var12.getMessage());
/* 121:    */       }
/* 122:    */       catch (IOException var13)
/* 123:    */       {
/* 124:155 */         var13.printStackTrace();
/* 125:    */       }
/* 126:    */     }
/* 127:159 */     TextureAnimation[] var14 = (TextureAnimation[])list.toArray(new TextureAnimation[list.size()]);
/* 128:160 */     return var14;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public static TextureAnimation makeTextureAnimation(Properties props, ResourceLocation propLoc)
/* 132:    */   {
/* 133:168 */     String texFrom = props.getProperty("from");
/* 134:169 */     String texTo = props.getProperty("to");
/* 135:170 */     int x = Config.parseInt(props.getProperty("x"), -1);
/* 136:171 */     int y = Config.parseInt(props.getProperty("y"), -1);
/* 137:172 */     int width = Config.parseInt(props.getProperty("w"), -1);
/* 138:173 */     int height = Config.parseInt(props.getProperty("h"), -1);
/* 139:175 */     if ((texFrom != null) && (texTo != null))
/* 140:    */     {
/* 141:177 */       if ((x >= 0) && (y >= 0) && (width >= 0) && (height >= 0))
/* 142:    */       {
/* 143:179 */         String basePath = TextureUtils.getBasePath(propLoc.getResourcePath());
/* 144:180 */         texFrom = TextureUtils.fixResourcePath(texFrom, basePath);
/* 145:181 */         texTo = TextureUtils.fixResourcePath(texTo, basePath);
/* 146:182 */         byte[] imageBytes = getCustomTextureData(texFrom, width);
/* 147:184 */         if (imageBytes == null)
/* 148:    */         {
/* 149:186 */           Config.warn("TextureAnimation: Source texture not found: " + texTo);
/* 150:187 */           return null;
/* 151:    */         }
/* 152:191 */         ResourceLocation locTexTo = new ResourceLocation(texTo);
/* 153:193 */         if (!Config.hasResource(locTexTo))
/* 154:    */         {
/* 155:195 */           Config.warn("TextureAnimation: Target texture not found: " + texTo);
/* 156:196 */           return null;
/* 157:    */         }
/* 158:200 */         ITextureObject destTex = TextureUtils.getTexture(locTexTo);
/* 159:202 */         if (destTex == null)
/* 160:    */         {
/* 161:204 */           Config.warn("TextureAnimation: Target texture not found: " + locTexTo);
/* 162:205 */           return null;
/* 163:    */         }
/* 164:209 */         int destTexId = destTex.getGlTextureId();
/* 165:210 */         TextureAnimation anim = new TextureAnimation(texFrom, imageBytes, texTo, destTexId, x, y, width, height, props, 1);
/* 166:211 */         return anim;
/* 167:    */       }
/* 168:218 */       Config.warn("TextureAnimation: Invalid coordinates");
/* 169:219 */       return null;
/* 170:    */     }
/* 171:224 */     Config.warn("TextureAnimation: Source or target texture not specified");
/* 172:225 */     return null;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public static String[] getAnimationPropertiesDir(File tpDir)
/* 176:    */   {
/* 177:231 */     File dirAnim = new File(tpDir, "anim");
/* 178:233 */     if (!dirAnim.exists()) {
/* 179:235 */       return null;
/* 180:    */     }
/* 181:237 */     if (!dirAnim.isDirectory()) {
/* 182:239 */       return null;
/* 183:    */     }
/* 184:243 */     File[] propFiles = dirAnim.listFiles();
/* 185:245 */     if (propFiles == null) {
/* 186:247 */       return null;
/* 187:    */     }
/* 188:251 */     ArrayList list = new ArrayList();
/* 189:253 */     for (int props = 0; props < propFiles.length; props++)
/* 190:    */     {
/* 191:255 */       File file = propFiles[props];
/* 192:256 */       String name = file.getName();
/* 193:258 */       if ((!name.startsWith("custom_")) && (name.endsWith(".properties")) && (file.isFile()) && (file.canRead()))
/* 194:    */       {
/* 195:260 */         Config.dbg("TextureAnimation: anim/" + file.getName());
/* 196:261 */         list.add("/anim/" + name);
/* 197:    */       }
/* 198:    */     }
/* 199:265 */     String[] var7 = (String[])list.toArray(new String[list.size()]);
/* 200:266 */     return var7;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public static String[] getAnimationPropertiesZip(File tpFile)
/* 204:    */   {
/* 205:    */     try
/* 206:    */     {
/* 207:275 */       ZipFile e = new ZipFile(tpFile);
/* 208:276 */       Enumeration en = e.entries();
/* 209:277 */       ArrayList list = new ArrayList();
/* 210:279 */       while (en.hasMoreElements())
/* 211:    */       {
/* 212:281 */         ZipEntry props = (ZipEntry)en.nextElement();
/* 213:282 */         String name = props.getName();
/* 214:284 */         if ((name.startsWith("assets/minecraft/mcpatcher/anim/")) && (!name.startsWith("assets/minecraft/mcpatcher/anim/custom_")) && (name.endsWith(".properties")))
/* 215:    */         {
/* 216:286 */           String assetsMcStr = "assets/minecraft/";
/* 217:287 */           name = name.substring(assetsMcStr.length());
/* 218:288 */           list.add(name);
/* 219:    */         }
/* 220:    */       }
/* 221:292 */       String[] props1 = (String[])list.toArray(new String[list.size()]);
/* 222:293 */       e.close();
/* 223:294 */       return props1;
/* 224:    */     }
/* 225:    */     catch (IOException var7)
/* 226:    */     {
/* 227:298 */       var7.printStackTrace();
/* 228:    */     }
/* 229:299 */     return null;
/* 230:    */   }
/* 231:    */   
/* 232:    */   public static byte[] getCustomTextureData(String imagePath, int tileWidth)
/* 233:    */   {
/* 234:305 */     byte[] imageBytes = loadImage(imagePath, tileWidth);
/* 235:307 */     if (imageBytes == null) {
/* 236:309 */       imageBytes = loadImage("/anim" + imagePath, tileWidth);
/* 237:    */     }
/* 238:312 */     return imageBytes;
/* 239:    */   }
/* 240:    */   
/* 241:    */   private static byte[] loadImage(String name, int targetWidth)
/* 242:    */   {
/* 243:317 */     GameSettings options = Config.getGameSettings();
/* 244:    */     try
/* 245:    */     {
/* 246:321 */       ResourceLocation e = new ResourceLocation(name);
/* 247:322 */       InputStream in = Config.getResourceStream(e);
/* 248:324 */       if (in == null) {
/* 249:326 */         return null;
/* 250:    */       }
/* 251:330 */       BufferedImage image = readTextureImage(in);
/* 252:332 */       if (image == null) {
/* 253:334 */         return null;
/* 254:    */       }
/* 255:338 */       if ((targetWidth > 0) && (image.getWidth() != targetWidth))
/* 256:    */       {
/* 257:340 */         double width = image.getHeight() / image.getWidth();
/* 258:341 */         int ai = (int)(targetWidth * width);
/* 259:342 */         image = scaleBufferedImage(image, targetWidth, ai);
/* 260:    */       }
/* 261:345 */       int var20 = image.getWidth();
/* 262:346 */       int height = image.getHeight();
/* 263:347 */       int[] var21 = new int[var20 * height];
/* 264:348 */       byte[] byteBuf = new byte[var20 * height * 4];
/* 265:349 */       image.getRGB(0, 0, var20, height, var21, 0, var20);
/* 266:351 */       for (int l = 0; l < var21.length; l++)
/* 267:    */       {
/* 268:353 */         int alpha = var21[l] >> 24 & 0xFF;
/* 269:354 */         int red = var21[l] >> 16 & 0xFF;
/* 270:355 */         int green = var21[l] >> 8 & 0xFF;
/* 271:356 */         int blue = var21[l] & 0xFF;
/* 272:358 */         if ((options != null) && (options.anaglyph))
/* 273:    */         {
/* 274:360 */           int j3 = (red * 30 + green * 59 + blue * 11) / 100;
/* 275:361 */           int l3 = (red * 30 + green * 70) / 100;
/* 276:362 */           int j4 = (red * 30 + blue * 70) / 100;
/* 277:363 */           red = j3;
/* 278:364 */           green = l3;
/* 279:365 */           blue = j4;
/* 280:    */         }
/* 281:368 */         byteBuf[(l * 4 + 0)] = ((byte)red);
/* 282:369 */         byteBuf[(l * 4 + 1)] = ((byte)green);
/* 283:370 */         byteBuf[(l * 4 + 2)] = ((byte)blue);
/* 284:371 */         byteBuf[(l * 4 + 3)] = ((byte)alpha);
/* 285:    */       }
/* 286:374 */       return byteBuf;
/* 287:    */     }
/* 288:    */     catch (FileNotFoundException var18)
/* 289:    */     {
/* 290:380 */       return null;
/* 291:    */     }
/* 292:    */     catch (Exception var19)
/* 293:    */     {
/* 294:384 */       var19.printStackTrace();
/* 295:    */     }
/* 296:385 */     return null;
/* 297:    */   }
/* 298:    */   
/* 299:    */   private static BufferedImage readTextureImage(InputStream par1InputStream)
/* 300:    */     throws IOException
/* 301:    */   {
/* 302:391 */     BufferedImage var2 = ImageIO.read(par1InputStream);
/* 303:392 */     par1InputStream.close();
/* 304:393 */     return var2;
/* 305:    */   }
/* 306:    */   
/* 307:    */   public static BufferedImage scaleBufferedImage(BufferedImage image, int width, int height)
/* 308:    */   {
/* 309:398 */     BufferedImage scaledImage = new BufferedImage(width, height, 2);
/* 310:399 */     Graphics2D gr = scaledImage.createGraphics();
/* 311:400 */     gr.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
/* 312:401 */     gr.drawImage(image, 0, 0, width, height, null);
/* 313:402 */     return scaledImage;
/* 314:    */   }
/* 315:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.src.TextureAnimations
 * JD-Core Version:    0.7.0.1
 */