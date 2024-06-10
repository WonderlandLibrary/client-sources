/*   1:    */ package org.newdawn.slick.opengl;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import java.nio.ByteBuffer;
/*   6:    */ import org.lwjgl.BufferUtils;
/*   7:    */ 
/*   8:    */ public class PNGImageData
/*   9:    */   implements LoadableImageData
/*  10:    */ {
/*  11:    */   private int width;
/*  12:    */   private int height;
/*  13:    */   private int texHeight;
/*  14:    */   private int texWidth;
/*  15:    */   private PNGDecoder decoder;
/*  16:    */   private int bitDepth;
/*  17:    */   private ByteBuffer scratch;
/*  18:    */   
/*  19:    */   public int getDepth()
/*  20:    */   {
/*  21: 34 */     return this.bitDepth;
/*  22:    */   }
/*  23:    */   
/*  24:    */   public ByteBuffer getImageBufferData()
/*  25:    */   {
/*  26: 41 */     return this.scratch;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public int getTexHeight()
/*  30:    */   {
/*  31: 48 */     return this.texHeight;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public int getTexWidth()
/*  35:    */   {
/*  36: 55 */     return this.texWidth;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public ByteBuffer loadImage(InputStream fis)
/*  40:    */     throws IOException
/*  41:    */   {
/*  42: 62 */     return loadImage(fis, false, null);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public ByteBuffer loadImage(InputStream fis, boolean flipped, int[] transparent)
/*  46:    */     throws IOException
/*  47:    */   {
/*  48: 69 */     return loadImage(fis, flipped, false, transparent);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public ByteBuffer loadImage(InputStream fis, boolean flipped, boolean forceAlpha, int[] transparent)
/*  52:    */     throws IOException
/*  53:    */   {
/*  54: 76 */     if (transparent != null)
/*  55:    */     {
/*  56: 77 */       forceAlpha = true;
/*  57: 78 */       throw new IOException("Transparent color not support in custom PNG Decoder");
/*  58:    */     }
/*  59: 81 */     PNGDecoder decoder = new PNGDecoder(fis);
/*  60: 83 */     if (!decoder.isRGB()) {
/*  61: 84 */       throw new IOException("Only RGB formatted images are supported by the PNGLoader");
/*  62:    */     }
/*  63: 87 */     this.width = decoder.getWidth();
/*  64: 88 */     this.height = decoder.getHeight();
/*  65: 89 */     this.texWidth = get2Fold(this.width);
/*  66: 90 */     this.texHeight = get2Fold(this.height);
/*  67:    */     
/*  68: 92 */     int perPixel = decoder.hasAlpha() ? 4 : 3;
/*  69: 93 */     this.bitDepth = (decoder.hasAlpha() ? 32 : 24);
/*  70:    */     
/*  71:    */ 
/*  72: 96 */     this.scratch = BufferUtils.createByteBuffer(this.texWidth * this.texHeight * perPixel);
/*  73: 97 */     decoder.decode(this.scratch, this.texWidth * perPixel, perPixel == 4 ? PNGDecoder.RGBA : PNGDecoder.RGB);
/*  74: 99 */     if (this.height < this.texHeight - 1)
/*  75:    */     {
/*  76:100 */       int topOffset = (this.texHeight - 1) * (this.texWidth * perPixel);
/*  77:101 */       int bottomOffset = (this.height - 1) * (this.texWidth * perPixel);
/*  78:102 */       for (int x = 0; x < this.texWidth; x++) {
/*  79:103 */         for (int i = 0; i < perPixel; i++)
/*  80:    */         {
/*  81:104 */           this.scratch.put(topOffset + x + i, this.scratch.get(x + i));
/*  82:105 */           this.scratch.put(bottomOffset + this.texWidth * perPixel + x + i, this.scratch.get(bottomOffset + x + i));
/*  83:    */         }
/*  84:    */       }
/*  85:    */     }
/*  86:109 */     if (this.width < this.texWidth - 1) {
/*  87:110 */       for (int y = 0; y < this.texHeight; y++) {
/*  88:111 */         for (int i = 0; i < perPixel; i++)
/*  89:    */         {
/*  90:112 */           this.scratch.put((y + 1) * (this.texWidth * perPixel) - perPixel + i, this.scratch.get(y * (this.texWidth * perPixel) + i));
/*  91:113 */           this.scratch.put(y * (this.texWidth * perPixel) + this.width * perPixel + i, this.scratch.get(y * (this.texWidth * perPixel) + (this.width - 1) * perPixel + i));
/*  92:    */         }
/*  93:    */       }
/*  94:    */     }
/*  95:118 */     if ((!decoder.hasAlpha()) && (forceAlpha))
/*  96:    */     {
/*  97:119 */       ByteBuffer temp = BufferUtils.createByteBuffer(this.texWidth * this.texHeight * 4);
/*  98:120 */       for (int x = 0; x < this.texWidth; x++) {
/*  99:121 */         for (int y = 0; y < this.texHeight; y++)
/* 100:    */         {
/* 101:122 */           int srcOffset = y * 3 + x * this.texHeight * 3;
/* 102:123 */           int dstOffset = y * 4 + x * this.texHeight * 4;
/* 103:    */           
/* 104:125 */           temp.put(dstOffset, this.scratch.get(srcOffset));
/* 105:126 */           temp.put(dstOffset + 1, this.scratch.get(srcOffset + 1));
/* 106:127 */           temp.put(dstOffset + 2, this.scratch.get(srcOffset + 2));
/* 107:128 */           if ((x < getHeight()) && (y < getWidth())) {
/* 108:129 */             temp.put(dstOffset + 3, (byte)-1);
/* 109:    */           } else {
/* 110:131 */             temp.put(dstOffset + 3, (byte)0);
/* 111:    */           }
/* 112:    */         }
/* 113:    */       }
/* 114:136 */       this.bitDepth = 32;
/* 115:137 */       this.scratch = temp;
/* 116:    */     }
/* 117:140 */     if (transparent != null) {
/* 118:141 */       for (int i = 0; i < this.texWidth * this.texHeight * 4; i += 4)
/* 119:    */       {
/* 120:142 */         boolean match = true;
/* 121:143 */         for (int c = 0; c < 3; c++) {
/* 122:144 */           if (toInt(this.scratch.get(i + c)) != transparent[c]) {
/* 123:145 */             match = false;
/* 124:    */           }
/* 125:    */         }
/* 126:149 */         if (match) {
/* 127:150 */           this.scratch.put(i + 3, (byte)0);
/* 128:    */         }
/* 129:    */       }
/* 130:    */     }
/* 131:155 */     this.scratch.position(0);
/* 132:    */     
/* 133:157 */     return this.scratch;
/* 134:    */   }
/* 135:    */   
/* 136:    */   private int toInt(byte b)
/* 137:    */   {
/* 138:167 */     if (b < 0) {
/* 139:168 */       return 256 + b;
/* 140:    */     }
/* 141:171 */     return b;
/* 142:    */   }
/* 143:    */   
/* 144:    */   private int get2Fold(int fold)
/* 145:    */   {
/* 146:181 */     int ret = 2;
/* 147:182 */     while (ret < fold) {
/* 148:183 */       ret *= 2;
/* 149:    */     }
/* 150:185 */     return ret;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void configureEdging(boolean edging) {}
/* 154:    */   
/* 155:    */   public int getWidth()
/* 156:    */   {
/* 157:195 */     return this.width;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public int getHeight()
/* 161:    */   {
/* 162:199 */     return this.height;
/* 163:    */   }
/* 164:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.opengl.PNGImageData
 * JD-Core Version:    0.7.0.1
 */