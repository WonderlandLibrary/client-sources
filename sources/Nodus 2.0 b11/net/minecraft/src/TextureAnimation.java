/*   1:    */ package net.minecraft.src;
/*   2:    */ 
/*   3:    */ import java.nio.ByteBuffer;
/*   4:    */ import java.util.Properties;
/*   5:    */ import net.minecraft.client.renderer.GLAllocation;
/*   6:    */ import org.lwjgl.opengl.GL11;
/*   7:    */ 
/*   8:    */ public class TextureAnimation
/*   9:    */ {
/*  10: 10 */   private String srcTex = null;
/*  11: 11 */   private String dstTex = null;
/*  12: 12 */   private int dstTextId = -1;
/*  13: 13 */   private int dstX = 0;
/*  14: 14 */   private int dstY = 0;
/*  15: 15 */   private int frameWidth = 0;
/*  16: 16 */   private int frameHeight = 0;
/*  17: 17 */   private CustomAnimationFrame[] frames = null;
/*  18: 18 */   private int activeFrame = 0;
/*  19: 19 */   private ByteBuffer imageData = null;
/*  20:    */   
/*  21:    */   public TextureAnimation(String texFrom, byte[] srcData, String texTo, int dstTexId, int dstX, int dstY, int frameWidth, int frameHeight, Properties props, int durDef)
/*  22:    */   {
/*  23: 23 */     this.srcTex = texFrom;
/*  24: 24 */     this.dstTex = texTo;
/*  25: 25 */     this.dstTextId = dstTexId;
/*  26: 26 */     this.dstX = dstX;
/*  27: 27 */     this.dstY = dstY;
/*  28: 28 */     this.frameWidth = frameWidth;
/*  29: 29 */     this.frameHeight = frameHeight;
/*  30: 30 */     int frameLen = frameWidth * frameHeight * 4;
/*  31: 32 */     if (srcData.length % frameLen != 0) {
/*  32: 34 */       Config.warn("Invalid animated texture length: " + srcData.length + ", frameWidth: " + frameHeight + ", frameHeight: " + frameHeight);
/*  33:    */     }
/*  34: 37 */     this.imageData = GLAllocation.createDirectByteBuffer(srcData.length);
/*  35: 38 */     this.imageData.put(srcData);
/*  36: 39 */     int numFrames = srcData.length / frameLen;
/*  37: 41 */     if (props.get("tile.0") != null) {
/*  38: 43 */       for (int durationDefStr = 0; props.get("tile." + durationDefStr) != null; durationDefStr++) {
/*  39: 45 */         numFrames = durationDefStr + 1;
/*  40:    */       }
/*  41:    */     }
/*  42: 49 */     String var21 = (String)props.get("duration");
/*  43: 50 */     int durationDef = Config.parseInt(var21, durDef);
/*  44: 51 */     this.frames = new CustomAnimationFrame[numFrames];
/*  45: 53 */     for (int i = 0; i < this.frames.length; i++)
/*  46:    */     {
/*  47: 55 */       String indexStr = (String)props.get("tile." + i);
/*  48: 56 */       int index = Config.parseInt(indexStr, i);
/*  49: 57 */       String durationStr = (String)props.get("duration." + i);
/*  50: 58 */       int duration = Config.parseInt(durationStr, durationDef);
/*  51: 59 */       CustomAnimationFrame frm = new CustomAnimationFrame(index, duration);
/*  52: 60 */       this.frames[i] = frm;
/*  53:    */     }
/*  54:    */   }
/*  55:    */   
/*  56:    */   public boolean nextFrame()
/*  57:    */   {
/*  58: 66 */     if (this.frames.length <= 0) {
/*  59: 68 */       return false;
/*  60:    */     }
/*  61: 72 */     if (this.activeFrame >= this.frames.length) {
/*  62: 74 */       this.activeFrame = 0;
/*  63:    */     }
/*  64: 77 */     CustomAnimationFrame frame = this.frames[this.activeFrame];
/*  65: 78 */     frame.counter += 1;
/*  66: 80 */     if (frame.counter < frame.duration) {
/*  67: 82 */       return false;
/*  68:    */     }
/*  69: 86 */     frame.counter = 0;
/*  70: 87 */     this.activeFrame += 1;
/*  71: 89 */     if (this.activeFrame >= this.frames.length) {
/*  72: 91 */       this.activeFrame = 0;
/*  73:    */     }
/*  74: 94 */     return true;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public int getActiveFrameIndex()
/*  78:    */   {
/*  79:101 */     if (this.frames.length <= 0) {
/*  80:103 */       return 0;
/*  81:    */     }
/*  82:107 */     if (this.activeFrame >= this.frames.length) {
/*  83:109 */       this.activeFrame = 0;
/*  84:    */     }
/*  85:112 */     CustomAnimationFrame frame = this.frames[this.activeFrame];
/*  86:113 */     return frame.index;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public int getFrameCount()
/*  90:    */   {
/*  91:119 */     return this.frames.length;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public boolean updateTexture()
/*  95:    */   {
/*  96:124 */     if (!nextFrame()) {
/*  97:126 */       return false;
/*  98:    */     }
/*  99:130 */     int frameLen = this.frameWidth * this.frameHeight * 4;
/* 100:131 */     int imgNum = getActiveFrameIndex();
/* 101:132 */     int offset = frameLen * imgNum;
/* 102:134 */     if (offset + frameLen > this.imageData.capacity()) {
/* 103:136 */       return false;
/* 104:    */     }
/* 105:140 */     this.imageData.position(offset);
/* 106:141 */     GL11.glBindTexture(3553, this.dstTextId);
/* 107:142 */     GL11.glTexSubImage2D(3553, 0, this.dstX, this.dstY, this.frameWidth, this.frameHeight, 6408, 5121, this.imageData);
/* 108:143 */     return true;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public String getSrcTex()
/* 112:    */   {
/* 113:150 */     return this.srcTex;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public String getDstTex()
/* 117:    */   {
/* 118:155 */     return this.dstTex;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public int getDstTextId()
/* 122:    */   {
/* 123:160 */     return this.dstTextId;
/* 124:    */   }
/* 125:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.src.TextureAnimation
 * JD-Core Version:    0.7.0.1
 */