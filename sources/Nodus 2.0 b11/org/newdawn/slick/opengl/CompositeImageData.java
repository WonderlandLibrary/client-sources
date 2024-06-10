/*   1:    */ package org.newdawn.slick.opengl;
/*   2:    */ 
/*   3:    */ import java.io.BufferedInputStream;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.io.InputStream;
/*   6:    */ import java.nio.ByteBuffer;
/*   7:    */ import java.util.ArrayList;
/*   8:    */ import org.newdawn.slick.util.Log;
/*   9:    */ 
/*  10:    */ public class CompositeImageData
/*  11:    */   implements LoadableImageData
/*  12:    */ {
/*  13: 19 */   private ArrayList sources = new ArrayList();
/*  14:    */   private LoadableImageData picked;
/*  15:    */   
/*  16:    */   public void add(LoadableImageData data)
/*  17:    */   {
/*  18: 29 */     this.sources.add(data);
/*  19:    */   }
/*  20:    */   
/*  21:    */   public ByteBuffer loadImage(InputStream fis)
/*  22:    */     throws IOException
/*  23:    */   {
/*  24: 36 */     return loadImage(fis, false, null);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public ByteBuffer loadImage(InputStream fis, boolean flipped, int[] transparent)
/*  28:    */     throws IOException
/*  29:    */   {
/*  30: 43 */     return loadImage(fis, flipped, false, transparent);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public ByteBuffer loadImage(InputStream is, boolean flipped, boolean forceAlpha, int[] transparent)
/*  34:    */     throws IOException
/*  35:    */   {
/*  36: 50 */     CompositeIOException exception = new CompositeIOException();
/*  37: 51 */     ByteBuffer buffer = null;
/*  38:    */     
/*  39: 53 */     BufferedInputStream in = new BufferedInputStream(is, is.available());
/*  40: 54 */     in.mark(is.available());
/*  41: 57 */     for (int i = 0; i < this.sources.size(); i++)
/*  42:    */     {
/*  43: 58 */       in.reset();
/*  44:    */       try
/*  45:    */       {
/*  46: 60 */         LoadableImageData data = (LoadableImageData)this.sources.get(i);
/*  47:    */         
/*  48: 62 */         buffer = data.loadImage(in, flipped, forceAlpha, transparent);
/*  49: 63 */         this.picked = data;
/*  50:    */       }
/*  51:    */       catch (Exception e)
/*  52:    */       {
/*  53: 66 */         Log.warn(this.sources.get(i).getClass() + " failed to read the data", e);
/*  54: 67 */         exception.addException(e);
/*  55:    */       }
/*  56:    */     }
/*  57: 71 */     if (this.picked == null) {
/*  58: 72 */       throw exception;
/*  59:    */     }
/*  60: 75 */     return buffer;
/*  61:    */   }
/*  62:    */   
/*  63:    */   private void checkPicked()
/*  64:    */   {
/*  65: 83 */     if (this.picked == null) {
/*  66: 84 */       throw new RuntimeException("Attempt to make use of uninitialised or invalid composite image data");
/*  67:    */     }
/*  68:    */   }
/*  69:    */   
/*  70:    */   public int getDepth()
/*  71:    */   {
/*  72: 92 */     checkPicked();
/*  73:    */     
/*  74: 94 */     return this.picked.getDepth();
/*  75:    */   }
/*  76:    */   
/*  77:    */   public int getHeight()
/*  78:    */   {
/*  79:101 */     checkPicked();
/*  80:    */     
/*  81:103 */     return this.picked.getHeight();
/*  82:    */   }
/*  83:    */   
/*  84:    */   public ByteBuffer getImageBufferData()
/*  85:    */   {
/*  86:110 */     checkPicked();
/*  87:    */     
/*  88:112 */     return this.picked.getImageBufferData();
/*  89:    */   }
/*  90:    */   
/*  91:    */   public int getTexHeight()
/*  92:    */   {
/*  93:119 */     checkPicked();
/*  94:    */     
/*  95:121 */     return this.picked.getTexHeight();
/*  96:    */   }
/*  97:    */   
/*  98:    */   public int getTexWidth()
/*  99:    */   {
/* 100:128 */     checkPicked();
/* 101:    */     
/* 102:130 */     return this.picked.getTexWidth();
/* 103:    */   }
/* 104:    */   
/* 105:    */   public int getWidth()
/* 106:    */   {
/* 107:137 */     checkPicked();
/* 108:    */     
/* 109:139 */     return this.picked.getWidth();
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void configureEdging(boolean edging)
/* 113:    */   {
/* 114:146 */     for (int i = 0; i < this.sources.size(); i++) {
/* 115:147 */       ((LoadableImageData)this.sources.get(i)).configureEdging(edging);
/* 116:    */     }
/* 117:    */   }
/* 118:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.opengl.CompositeImageData
 * JD-Core Version:    0.7.0.1
 */