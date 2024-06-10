/*   1:    */ package org.newdawn.slick.opengl;
/*   2:    */ 
/*   3:    */ import java.io.BufferedInputStream;
/*   4:    */ import java.io.DataInputStream;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.io.InputStream;
/*   7:    */ import java.nio.ByteBuffer;
/*   8:    */ import java.nio.ByteOrder;
/*   9:    */ import org.lwjgl.BufferUtils;
/*  10:    */ 
/*  11:    */ public class TGAImageData
/*  12:    */   implements LoadableImageData
/*  13:    */ {
/*  14:    */   private int texWidth;
/*  15:    */   private int texHeight;
/*  16:    */   private int width;
/*  17:    */   private int height;
/*  18:    */   private short pixelDepth;
/*  19:    */   
/*  20:    */   private short flipEndian(short signedShort)
/*  21:    */   {
/*  22: 47 */     int input = signedShort & 0xFFFF;
/*  23: 48 */     return (short)(input << 8 | (input & 0xFF00) >>> 8);
/*  24:    */   }
/*  25:    */   
/*  26:    */   public int getDepth()
/*  27:    */   {
/*  28: 55 */     return this.pixelDepth;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public int getWidth()
/*  32:    */   {
/*  33: 62 */     return this.width;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public int getHeight()
/*  37:    */   {
/*  38: 69 */     return this.height;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public int getTexWidth()
/*  42:    */   {
/*  43: 76 */     return this.texWidth;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public int getTexHeight()
/*  47:    */   {
/*  48: 83 */     return this.texHeight;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public ByteBuffer loadImage(InputStream fis)
/*  52:    */     throws IOException
/*  53:    */   {
/*  54: 90 */     return loadImage(fis, true, null);
/*  55:    */   }
/*  56:    */   
/*  57:    */   public ByteBuffer loadImage(InputStream fis, boolean flipped, int[] transparent)
/*  58:    */     throws IOException
/*  59:    */   {
/*  60: 97 */     return loadImage(fis, flipped, false, transparent);
/*  61:    */   }
/*  62:    */   
/*  63:    */   public ByteBuffer loadImage(InputStream fis, boolean flipped, boolean forceAlpha, int[] transparent)
/*  64:    */     throws IOException
/*  65:    */   {
/*  66:104 */     if (transparent != null) {
/*  67:105 */       forceAlpha = true;
/*  68:    */     }
/*  69:107 */     byte red = 0;
/*  70:108 */     byte green = 0;
/*  71:109 */     byte blue = 0;
/*  72:110 */     byte alpha = 0;
/*  73:    */     
/*  74:112 */     BufferedInputStream bis = new BufferedInputStream(fis, 100000);
/*  75:113 */     DataInputStream dis = new DataInputStream(bis);
/*  76:    */     
/*  77:    */ 
/*  78:116 */     short idLength = (short)dis.read();
/*  79:117 */     short colorMapType = (short)dis.read();
/*  80:118 */     short imageType = (short)dis.read();
/*  81:119 */     short cMapStart = flipEndian(dis.readShort());
/*  82:120 */     short cMapLength = flipEndian(dis.readShort());
/*  83:121 */     short cMapDepth = (short)dis.read();
/*  84:122 */     short xOffset = flipEndian(dis.readShort());
/*  85:123 */     short yOffset = flipEndian(dis.readShort());
/*  86:125 */     if (imageType != 2) {
/*  87:126 */       throw new IOException("Slick only supports uncompressed RGB(A) TGA images");
/*  88:    */     }
/*  89:129 */     this.width = flipEndian(dis.readShort());
/*  90:130 */     this.height = flipEndian(dis.readShort());
/*  91:131 */     this.pixelDepth = ((short)dis.read());
/*  92:132 */     if (this.pixelDepth == 32) {
/*  93:133 */       forceAlpha = false;
/*  94:    */     }
/*  95:136 */     this.texWidth = get2Fold(this.width);
/*  96:137 */     this.texHeight = get2Fold(this.height);
/*  97:    */     
/*  98:139 */     short imageDescriptor = (short)dis.read();
/*  99:140 */     if ((imageDescriptor & 0x20) == 0) {
/* 100:141 */       flipped = !flipped;
/* 101:    */     }
/* 102:145 */     if (idLength > 0) {
/* 103:146 */       bis.skip(idLength);
/* 104:    */     }
/* 105:149 */     byte[] rawData = null;
/* 106:150 */     if ((this.pixelDepth == 32) || (forceAlpha))
/* 107:    */     {
/* 108:151 */       this.pixelDepth = 32;
/* 109:152 */       rawData = new byte[this.texWidth * this.texHeight * 4];
/* 110:    */     }
/* 111:153 */     else if (this.pixelDepth == 24)
/* 112:    */     {
/* 113:154 */       rawData = new byte[this.texWidth * this.texHeight * 3];
/* 114:    */     }
/* 115:    */     else
/* 116:    */     {
/* 117:156 */       throw new RuntimeException("Only 24 and 32 bit TGAs are supported");
/* 118:    */     }
/* 119:159 */     if (this.pixelDepth == 24)
/* 120:    */     {
/* 121:160 */       if (flipped) {
/* 122:161 */         for (int i = this.height - 1; i >= 0; i--) {
/* 123:162 */           for (int j = 0; j < this.width; j++)
/* 124:    */           {
/* 125:163 */             blue = dis.readByte();
/* 126:164 */             green = dis.readByte();
/* 127:165 */             red = dis.readByte();
/* 128:    */             
/* 129:167 */             int ofs = (j + i * this.texWidth) * 3;
/* 130:168 */             rawData[ofs] = red;
/* 131:169 */             rawData[(ofs + 1)] = green;
/* 132:170 */             rawData[(ofs + 2)] = blue;
/* 133:    */           }
/* 134:    */         }
/* 135:    */       } else {
/* 136:174 */         for (int i = 0; i < this.height; i++) {
/* 137:175 */           for (int j = 0; j < this.width; j++)
/* 138:    */           {
/* 139:176 */             blue = dis.readByte();
/* 140:177 */             green = dis.readByte();
/* 141:178 */             red = dis.readByte();
/* 142:    */             
/* 143:180 */             int ofs = (j + i * this.texWidth) * 3;
/* 144:181 */             rawData[ofs] = red;
/* 145:182 */             rawData[(ofs + 1)] = green;
/* 146:183 */             rawData[(ofs + 2)] = blue;
/* 147:    */           }
/* 148:    */         }
/* 149:    */       }
/* 150:    */     }
/* 151:187 */     else if (this.pixelDepth == 32) {
/* 152:188 */       if (flipped) {
/* 153:189 */         for (int i = this.height - 1; i >= 0; i--) {
/* 154:190 */           for (int j = 0; j < this.width; j++)
/* 155:    */           {
/* 156:191 */             blue = dis.readByte();
/* 157:192 */             green = dis.readByte();
/* 158:193 */             red = dis.readByte();
/* 159:194 */             if (forceAlpha) {
/* 160:195 */               alpha = -1;
/* 161:    */             } else {
/* 162:197 */               alpha = dis.readByte();
/* 163:    */             }
/* 164:200 */             int ofs = (j + i * this.texWidth) * 4;
/* 165:    */             
/* 166:202 */             rawData[ofs] = red;
/* 167:203 */             rawData[(ofs + 1)] = green;
/* 168:204 */             rawData[(ofs + 2)] = blue;
/* 169:205 */             rawData[(ofs + 3)] = alpha;
/* 170:207 */             if (alpha == 0)
/* 171:    */             {
/* 172:208 */               rawData[(ofs + 2)] = 0;
/* 173:209 */               rawData[(ofs + 1)] = 0;
/* 174:210 */               rawData[ofs] = 0;
/* 175:    */             }
/* 176:    */           }
/* 177:    */         }
/* 178:    */       } else {
/* 179:215 */         for (int i = 0; i < this.height; i++) {
/* 180:216 */           for (int j = 0; j < this.width; j++)
/* 181:    */           {
/* 182:217 */             blue = dis.readByte();
/* 183:218 */             green = dis.readByte();
/* 184:219 */             red = dis.readByte();
/* 185:220 */             if (forceAlpha) {
/* 186:221 */               alpha = -1;
/* 187:    */             } else {
/* 188:223 */               alpha = dis.readByte();
/* 189:    */             }
/* 190:226 */             int ofs = (j + i * this.texWidth) * 4;
/* 191:228 */             if (ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN)
/* 192:    */             {
/* 193:229 */               rawData[ofs] = red;
/* 194:230 */               rawData[(ofs + 1)] = green;
/* 195:231 */               rawData[(ofs + 2)] = blue;
/* 196:232 */               rawData[(ofs + 3)] = alpha;
/* 197:    */             }
/* 198:    */             else
/* 199:    */             {
/* 200:234 */               rawData[ofs] = red;
/* 201:235 */               rawData[(ofs + 1)] = green;
/* 202:236 */               rawData[(ofs + 2)] = blue;
/* 203:237 */               rawData[(ofs + 3)] = alpha;
/* 204:    */             }
/* 205:240 */             if (alpha == 0)
/* 206:    */             {
/* 207:241 */               rawData[(ofs + 2)] = 0;
/* 208:242 */               rawData[(ofs + 1)] = 0;
/* 209:243 */               rawData[ofs] = 0;
/* 210:    */             }
/* 211:    */           }
/* 212:    */         }
/* 213:    */       }
/* 214:    */     }
/* 215:249 */     fis.close();
/* 216:251 */     if (transparent != null) {
/* 217:252 */       for (int i = 0; i < rawData.length; i += 4)
/* 218:    */       {
/* 219:253 */         boolean match = true;
/* 220:254 */         for (int c = 0; c < 3; c++) {
/* 221:255 */           if (rawData[(i + c)] != transparent[c]) {
/* 222:256 */             match = false;
/* 223:    */           }
/* 224:    */         }
/* 225:260 */         if (match) {
/* 226:261 */           rawData[(i + 3)] = 0;
/* 227:    */         }
/* 228:    */       }
/* 229:    */     }
/* 230:267 */     ByteBuffer scratch = BufferUtils.createByteBuffer(rawData.length);
/* 231:268 */     scratch.put(rawData);
/* 232:    */     
/* 233:270 */     int perPixel = this.pixelDepth / 8;
/* 234:271 */     if (this.height < this.texHeight - 1)
/* 235:    */     {
/* 236:272 */       int topOffset = (this.texHeight - 1) * (this.texWidth * perPixel);
/* 237:273 */       int bottomOffset = (this.height - 1) * (this.texWidth * perPixel);
/* 238:274 */       for (int x = 0; x < this.texWidth * perPixel; x++)
/* 239:    */       {
/* 240:275 */         scratch.put(topOffset + x, scratch.get(x));
/* 241:276 */         scratch.put(bottomOffset + this.texWidth * perPixel + x, scratch.get(this.texWidth * perPixel + x));
/* 242:    */       }
/* 243:    */     }
/* 244:279 */     if (this.width < this.texWidth - 1) {
/* 245:280 */       for (int y = 0; y < this.texHeight; y++) {
/* 246:281 */         for (int i = 0; i < perPixel; i++)
/* 247:    */         {
/* 248:282 */           scratch.put((y + 1) * (this.texWidth * perPixel) - perPixel + i, scratch.get(y * (this.texWidth * perPixel) + i));
/* 249:283 */           scratch.put(y * (this.texWidth * perPixel) + this.width * perPixel + i, scratch.get(y * (this.texWidth * perPixel) + (this.width - 1) * perPixel + i));
/* 250:    */         }
/* 251:    */       }
/* 252:    */     }
/* 253:288 */     scratch.flip();
/* 254:    */     
/* 255:290 */     return scratch;
/* 256:    */   }
/* 257:    */   
/* 258:    */   private int get2Fold(int fold)
/* 259:    */   {
/* 260:300 */     int ret = 2;
/* 261:301 */     while (ret < fold) {
/* 262:302 */       ret *= 2;
/* 263:    */     }
/* 264:304 */     return ret;
/* 265:    */   }
/* 266:    */   
/* 267:    */   public ByteBuffer getImageBufferData()
/* 268:    */   {
/* 269:311 */     throw new RuntimeException("TGAImageData doesn't store it's image.");
/* 270:    */   }
/* 271:    */   
/* 272:    */   public void configureEdging(boolean edging) {}
/* 273:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.opengl.TGAImageData
 * JD-Core Version:    0.7.0.1
 */