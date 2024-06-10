/*   1:    */ package net.minecraft.client.renderer;
/*   2:    */ 
/*   3:    */ import java.nio.ByteBuffer;
/*   4:    */ import java.nio.ByteOrder;
/*   5:    */ import java.nio.FloatBuffer;
/*   6:    */ import java.nio.IntBuffer;
/*   7:    */ import java.nio.ShortBuffer;
/*   8:    */ import java.util.PriorityQueue;
/*   9:    */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*  10:    */ import net.minecraft.client.shader.TesselatorVertexState;
/*  11:    */ import net.minecraft.client.util.QuadComparator;
/*  12:    */ import net.minecraft.src.Config;
/*  13:    */ import net.minecraft.src.VertexData;
/*  14:    */ import org.lwjgl.opengl.GL11;
/*  15:    */ 
/*  16:    */ public class Tessellator
/*  17:    */ {
/*  18:    */   private ByteBuffer byteBuffer;
/*  19:    */   private IntBuffer intBuffer;
/*  20:    */   private FloatBuffer floatBuffer;
/*  21:    */   private ShortBuffer shortBuffer;
/*  22:    */   private int[] rawBuffer;
/*  23:    */   private int vertexCount;
/*  24:    */   private double textureU;
/*  25:    */   private double textureV;
/*  26:    */   private int brightness;
/*  27:    */   private int color;
/*  28:    */   private boolean hasColor;
/*  29:    */   private boolean hasTexture;
/*  30:    */   private boolean hasBrightness;
/*  31:    */   private boolean hasNormals;
/*  32:    */   private int rawBufferIndex;
/*  33:    */   private int addedVertices;
/*  34:    */   private boolean isColorDisabled;
/*  35:    */   public int drawMode;
/*  36:    */   public double xOffset;
/*  37:    */   public double yOffset;
/*  38:    */   public double zOffset;
/*  39:    */   private int normal;
/*  40:100 */   public static Tessellator instance = new Tessellator(524288);
/*  41:    */   public boolean isDrawing;
/*  42:    */   private int bufferSize;
/*  43:    */   private boolean renderingChunk;
/*  44:108 */   private static boolean littleEndianByteOrder = ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN;
/*  45:109 */   public static boolean renderingWorldRenderer = false;
/*  46:    */   public boolean defaultTexture;
/*  47:    */   public int textureID;
/*  48:    */   public boolean autoGrow;
/*  49:    */   private VertexData[] vertexDatas;
/*  50:    */   private boolean[] drawnIcons;
/*  51:    */   private TextureAtlasSprite[] vertexQuadIcons;
/*  52:    */   private static final String __OBFID = "CL_00000960";
/*  53:    */   
/*  54:    */   public Tessellator()
/*  55:    */   {
/*  56:120 */     this(65536);
/*  57:121 */     this.defaultTexture = false;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public Tessellator(int par1)
/*  61:    */   {
/*  62:126 */     this.renderingChunk = false;
/*  63:127 */     this.defaultTexture = true;
/*  64:128 */     this.textureID = 0;
/*  65:129 */     this.autoGrow = true;
/*  66:130 */     this.vertexDatas = null;
/*  67:131 */     this.drawnIcons = new boolean[256];
/*  68:132 */     this.vertexQuadIcons = null;
/*  69:133 */     this.bufferSize = par1;
/*  70:134 */     this.byteBuffer = GLAllocation.createDirectByteBuffer(par1 * 4);
/*  71:135 */     this.intBuffer = this.byteBuffer.asIntBuffer();
/*  72:136 */     this.floatBuffer = this.byteBuffer.asFloatBuffer();
/*  73:137 */     this.shortBuffer = this.byteBuffer.asShortBuffer();
/*  74:138 */     this.rawBuffer = new int[par1];
/*  75:139 */     this.vertexDatas = new VertexData[4];
/*  76:141 */     for (int ix = 0; ix < this.vertexDatas.length; ix++) {
/*  77:143 */       this.vertexDatas[ix] = new VertexData();
/*  78:    */     }
/*  79:    */   }
/*  80:    */   
/*  81:    */   public int draw()
/*  82:    */   {
/*  83:152 */     if (!this.isDrawing) {
/*  84:154 */       throw new IllegalStateException("Not tesselating!");
/*  85:    */     }
/*  86:158 */     this.isDrawing = false;
/*  87:160 */     if ((this.vertexCount > 0) && ((!this.renderingChunk) || (!Config.isMultiTexture())))
/*  88:    */     {
/*  89:162 */       this.intBuffer.clear();
/*  90:163 */       this.intBuffer.put(this.rawBuffer, 0, this.rawBufferIndex);
/*  91:164 */       this.byteBuffer.position(0);
/*  92:165 */       this.byteBuffer.limit(this.rawBufferIndex * 4);
/*  93:167 */       if (this.hasTexture)
/*  94:    */       {
/*  95:169 */         this.floatBuffer.position(3);
/*  96:170 */         GL11.glTexCoordPointer(2, 32, this.floatBuffer);
/*  97:171 */         GL11.glEnableClientState(32888);
/*  98:    */       }
/*  99:174 */       if (this.hasBrightness)
/* 100:    */       {
/* 101:176 */         OpenGlHelper.setClientActiveTexture(OpenGlHelper.lightmapTexUnit);
/* 102:177 */         this.shortBuffer.position(14);
/* 103:178 */         GL11.glTexCoordPointer(2, 32, this.shortBuffer);
/* 104:179 */         GL11.glEnableClientState(32888);
/* 105:180 */         OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
/* 106:    */       }
/* 107:183 */       if (this.hasColor)
/* 108:    */       {
/* 109:185 */         this.byteBuffer.position(20);
/* 110:186 */         GL11.glColorPointer(4, true, 32, this.byteBuffer);
/* 111:187 */         GL11.glEnableClientState(32886);
/* 112:    */       }
/* 113:190 */       if (this.hasNormals)
/* 114:    */       {
/* 115:192 */         this.byteBuffer.position(24);
/* 116:193 */         GL11.glNormalPointer(32, this.byteBuffer);
/* 117:194 */         GL11.glEnableClientState(32885);
/* 118:    */       }
/* 119:197 */       this.floatBuffer.position(0);
/* 120:198 */       GL11.glVertexPointer(3, 32, this.floatBuffer);
/* 121:199 */       GL11.glEnableClientState(32884);
/* 122:200 */       GL11.glDrawArrays(this.drawMode, 0, this.vertexCount);
/* 123:201 */       GL11.glDisableClientState(32884);
/* 124:203 */       if (this.hasTexture) {
/* 125:205 */         GL11.glDisableClientState(32888);
/* 126:    */       }
/* 127:208 */       if (this.hasBrightness)
/* 128:    */       {
/* 129:210 */         OpenGlHelper.setClientActiveTexture(OpenGlHelper.lightmapTexUnit);
/* 130:211 */         GL11.glDisableClientState(32888);
/* 131:212 */         OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
/* 132:    */       }
/* 133:215 */       if (this.hasColor) {
/* 134:217 */         GL11.glDisableClientState(32886);
/* 135:    */       }
/* 136:220 */       if (this.hasNormals) {
/* 137:222 */         GL11.glDisableClientState(32885);
/* 138:    */       }
/* 139:    */     }
/* 140:226 */     int var1 = this.rawBufferIndex * 4;
/* 141:227 */     reset();
/* 142:228 */     return var1;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public TesselatorVertexState getVertexState(float p_147564_1_, float p_147564_2_, float p_147564_3_)
/* 146:    */   {
/* 147:234 */     if (this.rawBufferIndex < 1) {
/* 148:236 */       return null;
/* 149:    */     }
/* 150:240 */     int[] var4 = new int[this.rawBufferIndex];
/* 151:241 */     PriorityQueue var5 = new PriorityQueue(this.rawBufferIndex, new QuadComparator(this.rawBuffer, p_147564_1_ + (float)this.xOffset, p_147564_2_ + (float)this.yOffset, p_147564_3_ + (float)this.zOffset));
/* 152:242 */     byte var6 = 32;
/* 153:245 */     for (int var7 = 0; var7 < this.rawBufferIndex; var7 += var6) {
/* 154:247 */       var5.add(Integer.valueOf(var7));
/* 155:    */     }
/* 156:250 */     for (var7 = 0; !var5.isEmpty(); var7 += var6)
/* 157:    */     {
/* 158:252 */       int var8 = ((Integer)var5.remove()).intValue();
/* 159:254 */       for (int var9 = 0; var9 < var6; var9++) {
/* 160:256 */         var4[(var7 + var9)] = this.rawBuffer[(var8 + var9)];
/* 161:    */       }
/* 162:    */     }
/* 163:260 */     System.arraycopy(var4, 0, this.rawBuffer, 0, var4.length);
/* 164:261 */     return new TesselatorVertexState(var4, this.rawBufferIndex, this.vertexCount, this.hasTexture, this.hasBrightness, this.hasNormals, this.hasColor);
/* 165:    */   }
/* 166:    */   
/* 167:    */   public void setVertexState(TesselatorVertexState p_147565_1_)
/* 168:    */   {
/* 169:267 */     System.arraycopy(p_147565_1_.getRawBuffer(), 0, this.rawBuffer, 0, p_147565_1_.getRawBuffer().length);
/* 170:268 */     this.rawBufferIndex = p_147565_1_.getRawBufferIndex();
/* 171:269 */     this.vertexCount = p_147565_1_.getVertexCount();
/* 172:270 */     this.hasTexture = p_147565_1_.getHasTexture();
/* 173:271 */     this.hasBrightness = p_147565_1_.getHasBrightness();
/* 174:272 */     this.hasColor = p_147565_1_.getHasColor();
/* 175:273 */     this.hasNormals = p_147565_1_.getHasNormals();
/* 176:    */   }
/* 177:    */   
/* 178:    */   private void reset()
/* 179:    */   {
/* 180:281 */     this.vertexCount = 0;
/* 181:282 */     this.byteBuffer.clear();
/* 182:283 */     this.rawBufferIndex = 0;
/* 183:284 */     this.addedVertices = 0;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public void startDrawingQuads()
/* 187:    */   {
/* 188:292 */     startDrawing(7);
/* 189:    */   }
/* 190:    */   
/* 191:    */   public void startDrawing(int par1)
/* 192:    */   {
/* 193:300 */     if (this.isDrawing) {
/* 194:302 */       throw new IllegalStateException("Already tesselating!");
/* 195:    */     }
/* 196:306 */     this.isDrawing = true;
/* 197:307 */     reset();
/* 198:308 */     this.drawMode = par1;
/* 199:309 */     this.hasNormals = false;
/* 200:310 */     this.hasColor = false;
/* 201:311 */     this.hasTexture = false;
/* 202:312 */     this.hasBrightness = false;
/* 203:313 */     this.isColorDisabled = false;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public void setTextureUV(double par1, double par3)
/* 207:    */   {
/* 208:322 */     this.hasTexture = true;
/* 209:323 */     this.textureU = par1;
/* 210:324 */     this.textureV = par3;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public void setBrightness(int par1)
/* 214:    */   {
/* 215:329 */     this.hasBrightness = true;
/* 216:330 */     this.brightness = par1;
/* 217:    */   }
/* 218:    */   
/* 219:    */   public void setColorOpaque_F(float par1, float par2, float par3)
/* 220:    */   {
/* 221:338 */     setColorOpaque((int)(par1 * 255.0F), (int)(par2 * 255.0F), (int)(par3 * 255.0F));
/* 222:    */   }
/* 223:    */   
/* 224:    */   public void setColorRGBA_F(float par1, float par2, float par3, float par4)
/* 225:    */   {
/* 226:346 */     setColorRGBA((int)(par1 * 255.0F), (int)(par2 * 255.0F), (int)(par3 * 255.0F), (int)(par4 * 255.0F));
/* 227:    */   }
/* 228:    */   
/* 229:    */   public void setColorOpaque(int par1, int par2, int par3)
/* 230:    */   {
/* 231:354 */     setColorRGBA(par1, par2, par3, 255);
/* 232:    */   }
/* 233:    */   
/* 234:    */   public void setColorRGBA(int par1, int par2, int par3, int par4)
/* 235:    */   {
/* 236:362 */     if (!this.isColorDisabled)
/* 237:    */     {
/* 238:364 */       if (par1 > 255) {
/* 239:366 */         par1 = 255;
/* 240:    */       }
/* 241:369 */       if (par2 > 255) {
/* 242:371 */         par2 = 255;
/* 243:    */       }
/* 244:374 */       if (par3 > 255) {
/* 245:376 */         par3 = 255;
/* 246:    */       }
/* 247:379 */       if (par4 > 255) {
/* 248:381 */         par4 = 255;
/* 249:    */       }
/* 250:384 */       if (par1 < 0) {
/* 251:386 */         par1 = 0;
/* 252:    */       }
/* 253:389 */       if (par2 < 0) {
/* 254:391 */         par2 = 0;
/* 255:    */       }
/* 256:394 */       if (par3 < 0) {
/* 257:396 */         par3 = 0;
/* 258:    */       }
/* 259:399 */       if (par4 < 0) {
/* 260:401 */         par4 = 0;
/* 261:    */       }
/* 262:404 */       this.hasColor = true;
/* 263:406 */       if (littleEndianByteOrder) {
/* 264:408 */         this.color = (par4 << 24 | par3 << 16 | par2 << 8 | par1);
/* 265:    */       } else {
/* 266:412 */         this.color = (par1 << 24 | par2 << 16 | par3 << 8 | par4);
/* 267:    */       }
/* 268:    */     }
/* 269:    */   }
/* 270:    */   
/* 271:    */   public void addVertexWithUV(double x, double y, double z, double u, double v)
/* 272:    */   {
/* 273:422 */     setTextureUV(u, v);
/* 274:423 */     addVertex(x, y, z);
/* 275:    */   }
/* 276:    */   
/* 277:    */   public void addVertex(double par1, double par3, double par5)
/* 278:    */   {
/* 279:432 */     if ((this.autoGrow) && (this.rawBufferIndex >= this.bufferSize - 32))
/* 280:    */     {
/* 281:434 */       Config.dbg("Expand tessellator buffer, old: " + this.bufferSize + ", new: " + this.bufferSize * 2);
/* 282:435 */       this.bufferSize *= 2;
/* 283:436 */       int[] newRawBuffer = new int[this.bufferSize];
/* 284:437 */       System.arraycopy(this.rawBuffer, 0, newRawBuffer, 0, this.rawBuffer.length);
/* 285:438 */       this.rawBuffer = newRawBuffer;
/* 286:439 */       this.byteBuffer = GLAllocation.createDirectByteBuffer(this.bufferSize * 4);
/* 287:440 */       this.intBuffer = this.byteBuffer.asIntBuffer();
/* 288:441 */       this.floatBuffer = this.byteBuffer.asFloatBuffer();
/* 289:442 */       this.shortBuffer = this.byteBuffer.asShortBuffer();
/* 290:444 */       if (this.vertexQuadIcons != null)
/* 291:    */       {
/* 292:446 */         TextureAtlasSprite[] newVertexQuadIcons = new TextureAtlasSprite[this.bufferSize / 4];
/* 293:447 */         System.arraycopy(this.vertexQuadIcons, 0, newVertexQuadIcons, 0, this.vertexQuadIcons.length);
/* 294:448 */         this.vertexQuadIcons = newVertexQuadIcons;
/* 295:    */       }
/* 296:    */     }
/* 297:452 */     this.addedVertices += 1;
/* 298:454 */     if (this.hasTexture)
/* 299:    */     {
/* 300:456 */       this.rawBuffer[(this.rawBufferIndex + 3)] = Float.floatToRawIntBits((float)this.textureU);
/* 301:457 */       this.rawBuffer[(this.rawBufferIndex + 4)] = Float.floatToRawIntBits((float)this.textureV);
/* 302:    */     }
/* 303:460 */     if (this.hasBrightness) {
/* 304:462 */       this.rawBuffer[(this.rawBufferIndex + 7)] = this.brightness;
/* 305:    */     }
/* 306:465 */     if (this.hasColor) {
/* 307:467 */       this.rawBuffer[(this.rawBufferIndex + 5)] = this.color;
/* 308:    */     }
/* 309:470 */     if (this.hasNormals) {
/* 310:472 */       this.rawBuffer[(this.rawBufferIndex + 6)] = this.normal;
/* 311:    */     }
/* 312:475 */     this.rawBuffer[(this.rawBufferIndex + 0)] = Float.floatToRawIntBits((float)(par1 + this.xOffset));
/* 313:476 */     this.rawBuffer[(this.rawBufferIndex + 1)] = Float.floatToRawIntBits((float)(par3 + this.yOffset));
/* 314:477 */     this.rawBuffer[(this.rawBufferIndex + 2)] = Float.floatToRawIntBits((float)(par5 + this.zOffset));
/* 315:478 */     this.rawBufferIndex += 8;
/* 316:479 */     this.vertexCount += 1;
/* 317:481 */     if ((!this.autoGrow) && (this.addedVertices % 4 == 0) && (this.rawBufferIndex >= this.bufferSize - 32))
/* 318:    */     {
/* 319:483 */       draw();
/* 320:484 */       this.isDrawing = true;
/* 321:    */     }
/* 322:    */   }
/* 323:    */   
/* 324:    */   public void setColorOpaque_I(int par1)
/* 325:    */   {
/* 326:493 */     int var2 = par1 >> 16 & 0xFF;
/* 327:494 */     int var3 = par1 >> 8 & 0xFF;
/* 328:495 */     int var4 = par1 & 0xFF;
/* 329:496 */     setColorOpaque(var2, var3, var4);
/* 330:    */   }
/* 331:    */   
/* 332:    */   public void setColorRGBA_I(int par1, int par2)
/* 333:    */   {
/* 334:504 */     int var3 = par1 >> 16 & 0xFF;
/* 335:505 */     int var4 = par1 >> 8 & 0xFF;
/* 336:506 */     int var5 = par1 & 0xFF;
/* 337:507 */     setColorRGBA(var3, var4, var5, par2);
/* 338:    */   }
/* 339:    */   
/* 340:    */   public void disableColor()
/* 341:    */   {
/* 342:515 */     this.isColorDisabled = true;
/* 343:    */   }
/* 344:    */   
/* 345:    */   public void setNormal(float par1, float par2, float par3)
/* 346:    */   {
/* 347:523 */     this.hasNormals = true;
/* 348:524 */     byte var4 = (byte)(int)(par1 * 127.0F);
/* 349:525 */     byte var5 = (byte)(int)(par2 * 127.0F);
/* 350:526 */     byte var6 = (byte)(int)(par3 * 127.0F);
/* 351:527 */     this.normal = (var4 & 0xFF | (var5 & 0xFF) << 8 | (var6 & 0xFF) << 16);
/* 352:    */   }
/* 353:    */   
/* 354:    */   public void setTranslation(double par1, double par3, double par5)
/* 355:    */   {
/* 356:535 */     this.xOffset = par1;
/* 357:536 */     this.yOffset = par3;
/* 358:537 */     this.zOffset = par5;
/* 359:    */   }
/* 360:    */   
/* 361:    */   public void addTranslation(float par1, float par2, float par3)
/* 362:    */   {
/* 363:545 */     this.xOffset += par1;
/* 364:546 */     this.yOffset += par2;
/* 365:547 */     this.zOffset += par3;
/* 366:    */   }
/* 367:    */   
/* 368:    */   public boolean isRenderingChunk()
/* 369:    */   {
/* 370:552 */     return this.renderingChunk;
/* 371:    */   }
/* 372:    */   
/* 373:    */   public void setRenderingChunk(boolean renderingChunk)
/* 374:    */   {
/* 375:557 */     this.renderingChunk = renderingChunk;
/* 376:    */   }
/* 377:    */   
/* 378:    */   private void draw(int startQuadVertex, int endQuadVertex)
/* 379:    */   {
/* 380:562 */     int vxQuadCount = endQuadVertex - startQuadVertex;
/* 381:564 */     if (vxQuadCount > 0)
/* 382:    */     {
/* 383:566 */       int startVertex = startQuadVertex * 4;
/* 384:567 */       int vxCount = vxQuadCount * 4;
/* 385:568 */       this.floatBuffer.position(3);
/* 386:569 */       GL11.glTexCoordPointer(2, 32, this.floatBuffer);
/* 387:570 */       OpenGlHelper.setClientActiveTexture(OpenGlHelper.lightmapTexUnit);
/* 388:571 */       this.shortBuffer.position(14);
/* 389:572 */       GL11.glTexCoordPointer(2, 32, this.shortBuffer);
/* 390:573 */       GL11.glEnableClientState(32888);
/* 391:574 */       OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
/* 392:575 */       this.byteBuffer.position(20);
/* 393:576 */       GL11.glColorPointer(4, true, 32, this.byteBuffer);
/* 394:577 */       this.floatBuffer.position(0);
/* 395:578 */       GL11.glVertexPointer(3, 32, this.floatBuffer);
/* 396:579 */       GL11.glDrawArrays(this.drawMode, startVertex, vxCount);
/* 397:    */     }
/* 398:    */   }
/* 399:    */   
/* 400:    */   private int drawForIcon(TextureAtlasSprite icon, int startQuadPos)
/* 401:    */   {
/* 402:585 */     icon.bindOwnTexture();
/* 403:586 */     int firstRegionEnd = -1;
/* 404:587 */     int lastPos = -1;
/* 405:588 */     int numQuads = this.addedVertices / 4;
/* 406:590 */     for (int i = startQuadPos; i < numQuads; i++)
/* 407:    */     {
/* 408:592 */       TextureAtlasSprite ts = this.vertexQuadIcons[i];
/* 409:594 */       if (ts == icon)
/* 410:    */       {
/* 411:596 */         if (lastPos < 0) {
/* 412:598 */           lastPos = i;
/* 413:    */         }
/* 414:    */       }
/* 415:601 */       else if (lastPos >= 0)
/* 416:    */       {
/* 417:603 */         draw(lastPos, i);
/* 418:604 */         lastPos = -1;
/* 419:606 */         if (firstRegionEnd < 0) {
/* 420:608 */           firstRegionEnd = i;
/* 421:    */         }
/* 422:    */       }
/* 423:    */     }
/* 424:613 */     if (lastPos >= 0) {
/* 425:615 */       draw(lastPos, numQuads);
/* 426:    */     }
/* 427:618 */     if (firstRegionEnd < 0) {
/* 428:620 */       firstRegionEnd = numQuads;
/* 429:    */     }
/* 430:623 */     return firstRegionEnd;
/* 431:    */   }
/* 432:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.Tessellator
 * JD-Core Version:    0.7.0.1
 */