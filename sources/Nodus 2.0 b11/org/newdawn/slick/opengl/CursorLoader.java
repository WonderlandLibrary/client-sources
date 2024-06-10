/*   1:    */ package org.newdawn.slick.opengl;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.nio.ByteBuffer;
/*   5:    */ import java.nio.ByteOrder;
/*   6:    */ import java.nio.IntBuffer;
/*   7:    */ import org.lwjgl.LWJGLException;
/*   8:    */ import org.lwjgl.input.Cursor;
/*   9:    */ import org.newdawn.slick.util.Log;
/*  10:    */ import org.newdawn.slick.util.ResourceLoader;
/*  11:    */ 
/*  12:    */ public class CursorLoader
/*  13:    */ {
/*  14: 22 */   private static CursorLoader single = new CursorLoader();
/*  15:    */   
/*  16:    */   public static CursorLoader get()
/*  17:    */   {
/*  18: 30 */     return single;
/*  19:    */   }
/*  20:    */   
/*  21:    */   public Cursor getCursor(String ref, int x, int y)
/*  22:    */     throws IOException, LWJGLException
/*  23:    */   {
/*  24: 50 */     LoadableImageData imageData = null;
/*  25:    */     
/*  26: 52 */     imageData = ImageDataFactory.getImageDataFor(ref);
/*  27: 53 */     imageData.configureEdging(false);
/*  28:    */     
/*  29: 55 */     ByteBuffer buf = imageData.loadImage(ResourceLoader.getResourceAsStream(ref), true, true, null);
/*  30: 56 */     for (int i = 0; i < buf.limit(); i += 4)
/*  31:    */     {
/*  32: 57 */       byte red = buf.get(i);
/*  33: 58 */       byte green = buf.get(i + 1);
/*  34: 59 */       byte blue = buf.get(i + 2);
/*  35: 60 */       byte alpha = buf.get(i + 3);
/*  36:    */       
/*  37: 62 */       buf.put(i + 2, red);
/*  38: 63 */       buf.put(i + 1, green);
/*  39: 64 */       buf.put(i, blue);
/*  40: 65 */       buf.put(i + 3, alpha);
/*  41:    */     }
/*  42:    */     try
/*  43:    */     {
/*  44: 69 */       int yspot = imageData.getHeight() - y - 1;
/*  45: 70 */       if (yspot < 0) {
/*  46: 71 */         yspot = 0;
/*  47:    */       }
/*  48: 74 */       return new Cursor(imageData.getTexWidth(), imageData.getTexHeight(), x, yspot, 1, buf.asIntBuffer(), null);
/*  49:    */     }
/*  50:    */     catch (Throwable e)
/*  51:    */     {
/*  52: 76 */       Log.info("Chances are you cursor is too small for this platform");
/*  53: 77 */       throw new LWJGLException(e);
/*  54:    */     }
/*  55:    */   }
/*  56:    */   
/*  57:    */   public Cursor getCursor(ByteBuffer buf, int x, int y, int width, int height)
/*  58:    */     throws IOException, LWJGLException
/*  59:    */   {
/*  60: 95 */     for (int i = 0; i < buf.limit(); i += 4)
/*  61:    */     {
/*  62: 96 */       byte red = buf.get(i);
/*  63: 97 */       byte green = buf.get(i + 1);
/*  64: 98 */       byte blue = buf.get(i + 2);
/*  65: 99 */       byte alpha = buf.get(i + 3);
/*  66:    */       
/*  67:101 */       buf.put(i + 2, red);
/*  68:102 */       buf.put(i + 1, green);
/*  69:103 */       buf.put(i, blue);
/*  70:104 */       buf.put(i + 3, alpha);
/*  71:    */     }
/*  72:    */     try
/*  73:    */     {
/*  74:108 */       int yspot = height - y - 1;
/*  75:109 */       if (yspot < 0) {
/*  76:110 */         yspot = 0;
/*  77:    */       }
/*  78:112 */       return new Cursor(width, height, x, yspot, 1, buf.asIntBuffer(), null);
/*  79:    */     }
/*  80:    */     catch (Throwable e)
/*  81:    */     {
/*  82:114 */       Log.info("Chances are you cursor is too small for this platform");
/*  83:115 */       throw new LWJGLException(e);
/*  84:    */     }
/*  85:    */   }
/*  86:    */   
/*  87:    */   public Cursor getCursor(ImageData imageData, int x, int y)
/*  88:    */     throws IOException, LWJGLException
/*  89:    */   {
/*  90:130 */     ByteBuffer buf = imageData.getImageBufferData();
/*  91:131 */     for (int i = 0; i < buf.limit(); i += 4)
/*  92:    */     {
/*  93:132 */       byte red = buf.get(i);
/*  94:133 */       byte green = buf.get(i + 1);
/*  95:134 */       byte blue = buf.get(i + 2);
/*  96:135 */       byte alpha = buf.get(i + 3);
/*  97:    */       
/*  98:137 */       buf.put(i + 2, red);
/*  99:138 */       buf.put(i + 1, green);
/* 100:139 */       buf.put(i, blue);
/* 101:140 */       buf.put(i + 3, alpha);
/* 102:    */     }
/* 103:    */     try
/* 104:    */     {
/* 105:144 */       int yspot = imageData.getHeight() - y - 1;
/* 106:145 */       if (yspot < 0) {
/* 107:146 */         yspot = 0;
/* 108:    */       }
/* 109:148 */       return new Cursor(imageData.getTexWidth(), imageData.getTexHeight(), x, yspot, 1, buf.asIntBuffer(), null);
/* 110:    */     }
/* 111:    */     catch (Throwable e)
/* 112:    */     {
/* 113:150 */       Log.info("Chances are you cursor is too small for this platform");
/* 114:151 */       throw new LWJGLException(e);
/* 115:    */     }
/* 116:    */   }
/* 117:    */   
/* 118:    */   public Cursor getAnimatedCursor(String ref, int x, int y, int width, int height, int[] cursorDelays)
/* 119:    */     throws IOException, LWJGLException
/* 120:    */   {
/* 121:172 */     IntBuffer cursorDelaysBuffer = ByteBuffer.allocateDirect(cursorDelays.length * 4).order(ByteOrder.nativeOrder()).asIntBuffer();
/* 122:173 */     for (int i = 0; i < cursorDelays.length; i++) {
/* 123:174 */       cursorDelaysBuffer.put(cursorDelays[i]);
/* 124:    */     }
/* 125:176 */     cursorDelaysBuffer.flip();
/* 126:    */     
/* 127:178 */     LoadableImageData imageData = new TGAImageData();
/* 128:179 */     ByteBuffer buf = imageData.loadImage(ResourceLoader.getResourceAsStream(ref), false, null);
/* 129:    */     
/* 130:181 */     return new Cursor(width, height, x, y, cursorDelays.length, buf.asIntBuffer(), cursorDelaysBuffer);
/* 131:    */   }
/* 132:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.opengl.CursorLoader
 * JD-Core Version:    0.7.0.1
 */