/*   1:    */ package org.newdawn.slick;
/*   2:    */ 
/*   3:    */ import java.nio.ByteBuffer;
/*   4:    */ import java.nio.ByteOrder;
/*   5:    */ import org.lwjgl.BufferUtils;
/*   6:    */ import org.newdawn.slick.opengl.ImageData;
/*   7:    */ 
/*   8:    */ public class ImageBuffer
/*   9:    */   implements ImageData
/*  10:    */ {
/*  11:    */   private int width;
/*  12:    */   private int height;
/*  13:    */   private int texWidth;
/*  14:    */   private int texHeight;
/*  15:    */   private byte[] rawData;
/*  16:    */   
/*  17:    */   public ImageBuffer(int width, int height)
/*  18:    */   {
/*  19: 40 */     this.width = width;
/*  20: 41 */     this.height = height;
/*  21:    */     
/*  22: 43 */     this.texWidth = get2Fold(width);
/*  23: 44 */     this.texHeight = get2Fold(height);
/*  24:    */     
/*  25: 46 */     this.rawData = new byte[this.texWidth * this.texHeight * 4];
/*  26:    */   }
/*  27:    */   
/*  28:    */   public byte[] getRGBA()
/*  29:    */   {
/*  30: 55 */     return this.rawData;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public int getDepth()
/*  34:    */   {
/*  35: 62 */     return 32;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public int getHeight()
/*  39:    */   {
/*  40: 69 */     return this.height;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public int getTexHeight()
/*  44:    */   {
/*  45: 76 */     return this.texHeight;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public int getTexWidth()
/*  49:    */   {
/*  50: 83 */     return this.texWidth;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public int getWidth()
/*  54:    */   {
/*  55: 90 */     return this.width;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public ByteBuffer getImageBufferData()
/*  59:    */   {
/*  60: 97 */     ByteBuffer scratch = BufferUtils.createByteBuffer(this.rawData.length);
/*  61: 98 */     scratch.put(this.rawData);
/*  62: 99 */     scratch.flip();
/*  63:    */     
/*  64:101 */     return scratch;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void setRGBA(int x, int y, int r, int g, int b, int a)
/*  68:    */   {
/*  69:115 */     if ((x < 0) || (x >= this.width) || (y < 0) || (y >= this.height)) {
/*  70:116 */       throw new RuntimeException("Specified location: " + x + "," + y + " outside of image");
/*  71:    */     }
/*  72:119 */     int ofs = (x + y * this.texWidth) * 4;
/*  73:121 */     if (ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN)
/*  74:    */     {
/*  75:122 */       this.rawData[ofs] = ((byte)b);
/*  76:123 */       this.rawData[(ofs + 1)] = ((byte)g);
/*  77:124 */       this.rawData[(ofs + 2)] = ((byte)r);
/*  78:125 */       this.rawData[(ofs + 3)] = ((byte)a);
/*  79:    */     }
/*  80:    */     else
/*  81:    */     {
/*  82:127 */       this.rawData[ofs] = ((byte)r);
/*  83:128 */       this.rawData[(ofs + 1)] = ((byte)g);
/*  84:129 */       this.rawData[(ofs + 2)] = ((byte)b);
/*  85:130 */       this.rawData[(ofs + 3)] = ((byte)a);
/*  86:    */     }
/*  87:    */   }
/*  88:    */   
/*  89:    */   public Image getImage()
/*  90:    */   {
/*  91:140 */     return new Image(this);
/*  92:    */   }
/*  93:    */   
/*  94:    */   public Image getImage(int filter)
/*  95:    */   {
/*  96:150 */     return new Image(this, filter);
/*  97:    */   }
/*  98:    */   
/*  99:    */   private int get2Fold(int fold)
/* 100:    */   {
/* 101:160 */     int ret = 2;
/* 102:161 */     while (ret < fold) {
/* 103:162 */       ret *= 2;
/* 104:    */     }
/* 105:164 */     return ret;
/* 106:    */   }
/* 107:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.ImageBuffer
 * JD-Core Version:    0.7.0.1
 */