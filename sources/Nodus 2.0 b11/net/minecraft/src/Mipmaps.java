/*   1:    */ package net.minecraft.src;
/*   2:    */ 
/*   3:    */ import java.awt.Dimension;
/*   4:    */ import java.nio.IntBuffer;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import net.minecraft.client.renderer.GLAllocation;
/*   7:    */ import org.lwjgl.opengl.GL11;
/*   8:    */ 
/*   9:    */ public class Mipmaps
/*  10:    */ {
/*  11:    */   private final String iconName;
/*  12:    */   private final int width;
/*  13:    */   private final int height;
/*  14:    */   private final int[] data;
/*  15:    */   private final boolean direct;
/*  16:    */   private int[][] mipmapDatas;
/*  17:    */   private IntBuffer[] mipmapBuffers;
/*  18:    */   private Dimension[] mipmapDimensions;
/*  19:    */   
/*  20:    */   public Mipmaps(String iconName, int width, int height, int[] data, boolean direct)
/*  21:    */   {
/*  22: 23 */     this.iconName = iconName;
/*  23: 24 */     this.width = width;
/*  24: 25 */     this.height = height;
/*  25: 26 */     this.data = data;
/*  26: 27 */     this.direct = direct;
/*  27: 28 */     this.mipmapDimensions = makeMipmapDimensions(width, height, iconName);
/*  28: 29 */     this.mipmapDatas = generateMipMapData(data, width, height, this.mipmapDimensions);
/*  29: 31 */     if (direct) {
/*  30: 33 */       this.mipmapBuffers = makeMipmapBuffers(this.mipmapDimensions, this.mipmapDatas);
/*  31:    */     }
/*  32:    */   }
/*  33:    */   
/*  34:    */   public static Dimension[] makeMipmapDimensions(int width, int height, String iconName)
/*  35:    */   {
/*  36: 39 */     int texWidth = TextureUtils.ceilPowerOfTwo(width);
/*  37: 40 */     int texHeight = TextureUtils.ceilPowerOfTwo(height);
/*  38: 42 */     if ((texWidth == width) && (texHeight == height))
/*  39:    */     {
/*  40: 44 */       ArrayList listDims = new ArrayList();
/*  41: 45 */       int mipWidth = texWidth;
/*  42: 46 */       int mipHeight = texHeight;
/*  43:    */       for (;;)
/*  44:    */       {
/*  45: 50 */         mipWidth /= 2;
/*  46: 51 */         mipHeight /= 2;
/*  47: 53 */         if ((mipWidth <= 0) && (mipHeight <= 0))
/*  48:    */         {
/*  49: 55 */           Dimension[] mipmapDimensions1 = (Dimension[])listDims.toArray(new Dimension[listDims.size()]);
/*  50: 56 */           return mipmapDimensions1;
/*  51:    */         }
/*  52: 59 */         if (mipWidth <= 0) {
/*  53: 61 */           mipWidth = 1;
/*  54:    */         }
/*  55: 64 */         if (mipHeight <= 0) {
/*  56: 66 */           mipHeight = 1;
/*  57:    */         }
/*  58: 69 */         int mipmapDimensions = mipWidth * mipHeight * 4;
/*  59: 70 */         Dimension dim = new Dimension(mipWidth, mipHeight);
/*  60: 71 */         listDims.add(dim);
/*  61:    */       }
/*  62:    */     }
/*  63: 76 */     Config.warn("Mipmaps not possible (power of 2 dimensions needed), texture: " + iconName + ", dim: " + width + "x" + height);
/*  64: 77 */     return new Dimension[0];
/*  65:    */   }
/*  66:    */   
/*  67:    */   public static int[][] generateMipMapData(int[] data, int width, int height, Dimension[] mipmapDimensions)
/*  68:    */   {
/*  69: 83 */     int[] parMipData = data;
/*  70: 84 */     int parWidth = width;
/*  71: 85 */     boolean scale = true;
/*  72: 86 */     int[][] mipmapDatas = new int[mipmapDimensions.length][];
/*  73: 88 */     for (int i = 0; i < mipmapDimensions.length; i++)
/*  74:    */     {
/*  75: 90 */       Dimension dim = mipmapDimensions[i];
/*  76: 91 */       int mipWidth = dim.width;
/*  77: 92 */       int mipHeight = dim.height;
/*  78: 93 */       int[] mipData = new int[mipWidth * mipHeight];
/*  79: 94 */       mipmapDatas[i] = mipData;
/*  80: 95 */       int level = i + 1;
/*  81: 97 */       if (scale) {
/*  82: 99 */         for (int mipX = 0; mipX < mipWidth; mipX++) {
/*  83:101 */           for (int mipY = 0; mipY < mipHeight; mipY++)
/*  84:    */           {
/*  85:103 */             int p1 = parMipData[(mipX * 2 + 0 + (mipY * 2 + 0) * parWidth)];
/*  86:104 */             int p2 = parMipData[(mipX * 2 + 1 + (mipY * 2 + 0) * parWidth)];
/*  87:105 */             int p3 = parMipData[(mipX * 2 + 1 + (mipY * 2 + 1) * parWidth)];
/*  88:106 */             int p4 = parMipData[(mipX * 2 + 0 + (mipY * 2 + 1) * parWidth)];
/*  89:107 */             int pixel = alphaBlend(p1, p2, p3, p4);
/*  90:108 */             mipData[(mipX + mipY * mipWidth)] = pixel;
/*  91:    */           }
/*  92:    */         }
/*  93:    */       }
/*  94:113 */       parMipData = mipData;
/*  95:114 */       parWidth = mipWidth;
/*  96:116 */       if ((mipWidth <= 1) || (mipHeight <= 1)) {
/*  97:118 */         scale = false;
/*  98:    */       }
/*  99:    */     }
/* 100:122 */     return mipmapDatas;
/* 101:    */   }
/* 102:    */   
/* 103:    */   private static int alphaBlend(int c1, int c2, int c3, int c4)
/* 104:    */   {
/* 105:127 */     int cx1 = alphaBlend(c1, c2);
/* 106:128 */     int cx2 = alphaBlend(c3, c4);
/* 107:129 */     int cx = alphaBlend(cx1, cx2);
/* 108:130 */     return cx;
/* 109:    */   }
/* 110:    */   
/* 111:    */   private static int alphaBlend(int c1, int c2)
/* 112:    */   {
/* 113:135 */     int a1 = (c1 & 0xFF000000) >> 24 & 0xFF;
/* 114:136 */     int a2 = (c2 & 0xFF000000) >> 24 & 0xFF;
/* 115:137 */     int ax = (a1 + a2) / 2;
/* 116:139 */     if ((a1 == 0) && (a2 == 0))
/* 117:    */     {
/* 118:141 */       a1 = 1;
/* 119:142 */       a2 = 1;
/* 120:    */     }
/* 121:    */     else
/* 122:    */     {
/* 123:146 */       if (a1 == 0)
/* 124:    */       {
/* 125:148 */         c1 = c2;
/* 126:149 */         ax /= 2;
/* 127:    */       }
/* 128:152 */       if (a2 == 0)
/* 129:    */       {
/* 130:154 */         c2 = c1;
/* 131:155 */         ax /= 2;
/* 132:    */       }
/* 133:    */     }
/* 134:159 */     int r1 = (c1 >> 16 & 0xFF) * a1;
/* 135:160 */     int g1 = (c1 >> 8 & 0xFF) * a1;
/* 136:161 */     int b1 = (c1 & 0xFF) * a1;
/* 137:162 */     int r2 = (c2 >> 16 & 0xFF) * a2;
/* 138:163 */     int g2 = (c2 >> 8 & 0xFF) * a2;
/* 139:164 */     int b2 = (c2 & 0xFF) * a2;
/* 140:165 */     int rx = (r1 + r2) / (a1 + a2);
/* 141:166 */     int gx = (g1 + g2) / (a1 + a2);
/* 142:167 */     int bx = (b1 + b2) / (a1 + a2);
/* 143:168 */     return ax << 24 | rx << 16 | gx << 8 | bx;
/* 144:    */   }
/* 145:    */   
/* 146:    */   private int averageColor(int i, int j)
/* 147:    */   {
/* 148:173 */     int k = (i & 0xFF000000) >> 24 & 0xFF;
/* 149:174 */     int l = (j & 0xFF000000) >> 24 & 0xFF;
/* 150:175 */     return (k + l >> 1 << 24) + ((i & 0xFEFEFE) + (j & 0xFEFEFE) >> 1);
/* 151:    */   }
/* 152:    */   
/* 153:    */   public static IntBuffer[] makeMipmapBuffers(Dimension[] mipmapDimensions, int[][] mipmapDatas)
/* 154:    */   {
/* 155:180 */     if (mipmapDimensions == null) {
/* 156:182 */       return null;
/* 157:    */     }
/* 158:186 */     IntBuffer[] mipmapBuffers = new IntBuffer[mipmapDimensions.length];
/* 159:188 */     for (int i = 0; i < mipmapDimensions.length; i++)
/* 160:    */     {
/* 161:190 */       Dimension dim = mipmapDimensions[i];
/* 162:191 */       int bufLen = dim.width * dim.height;
/* 163:192 */       IntBuffer buf = GLAllocation.createDirectIntBuffer(bufLen);
/* 164:193 */       int[] data = mipmapDatas[i];
/* 165:194 */       buf.clear();
/* 166:195 */       buf.put(data);
/* 167:196 */       buf.clear();
/* 168:197 */       mipmapBuffers[i] = buf;
/* 169:    */     }
/* 170:200 */     return mipmapBuffers;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public static void allocateMipmapTextures(int width, int height, String name)
/* 174:    */   {
/* 175:206 */     Dimension[] dims = makeMipmapDimensions(width, height, name);
/* 176:208 */     for (int i = 0; i < dims.length; i++)
/* 177:    */     {
/* 178:210 */       Dimension dim = dims[i];
/* 179:211 */       int mipWidth = dim.width;
/* 180:212 */       int mipHeight = dim.height;
/* 181:213 */       int level = i + 1;
/* 182:214 */       GL11.glTexImage2D(3553, level, 6408, mipWidth, mipHeight, 0, 32993, 33639, null);
/* 183:    */     }
/* 184:    */   }
/* 185:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.src.Mipmaps
 * JD-Core Version:    0.7.0.1
 */