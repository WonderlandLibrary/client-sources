/*   1:    */ package org.newdawn.slick.opengl;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import org.newdawn.slick.loading.DeferredResource;
/*   6:    */ import org.newdawn.slick.loading.LoadingList;
/*   7:    */ 
/*   8:    */ public class DeferredTexture
/*   9:    */   extends TextureImpl
/*  10:    */   implements DeferredResource
/*  11:    */ {
/*  12:    */   private InputStream in;
/*  13:    */   private String resourceName;
/*  14:    */   private boolean flipped;
/*  15:    */   private int filter;
/*  16:    */   private TextureImpl target;
/*  17:    */   private int[] trans;
/*  18:    */   
/*  19:    */   public DeferredTexture(InputStream in, String resourceName, boolean flipped, int filter, int[] trans)
/*  20:    */   {
/*  21: 40 */     this.in = in;
/*  22: 41 */     this.resourceName = resourceName;
/*  23: 42 */     this.flipped = flipped;
/*  24: 43 */     this.filter = filter;
/*  25: 44 */     this.trans = trans;
/*  26:    */     
/*  27: 46 */     LoadingList.get().add(this);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void load()
/*  31:    */     throws IOException
/*  32:    */   {
/*  33: 53 */     boolean before = InternalTextureLoader.get().isDeferredLoading();
/*  34: 54 */     InternalTextureLoader.get().setDeferredLoading(false);
/*  35: 55 */     this.target = InternalTextureLoader.get().getTexture(this.in, this.resourceName, this.flipped, this.filter, this.trans);
/*  36: 56 */     InternalTextureLoader.get().setDeferredLoading(before);
/*  37:    */   }
/*  38:    */   
/*  39:    */   private void checkTarget()
/*  40:    */   {
/*  41: 63 */     if (this.target == null) {
/*  42:    */       try
/*  43:    */       {
/*  44: 65 */         load();
/*  45: 66 */         LoadingList.get().remove(this);
/*  46: 67 */         return;
/*  47:    */       }
/*  48:    */       catch (IOException e)
/*  49:    */       {
/*  50: 69 */         throw new RuntimeException("Attempt to use deferred texture before loading and resource not found: " + this.resourceName);
/*  51:    */       }
/*  52:    */     }
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void bind()
/*  56:    */   {
/*  57: 78 */     checkTarget();
/*  58:    */     
/*  59: 80 */     this.target.bind();
/*  60:    */   }
/*  61:    */   
/*  62:    */   public float getHeight()
/*  63:    */   {
/*  64: 87 */     checkTarget();
/*  65:    */     
/*  66: 89 */     return this.target.getHeight();
/*  67:    */   }
/*  68:    */   
/*  69:    */   public int getImageHeight()
/*  70:    */   {
/*  71: 96 */     checkTarget();
/*  72: 97 */     return this.target.getImageHeight();
/*  73:    */   }
/*  74:    */   
/*  75:    */   public int getImageWidth()
/*  76:    */   {
/*  77:104 */     checkTarget();
/*  78:105 */     return this.target.getImageWidth();
/*  79:    */   }
/*  80:    */   
/*  81:    */   public int getTextureHeight()
/*  82:    */   {
/*  83:112 */     checkTarget();
/*  84:113 */     return this.target.getTextureHeight();
/*  85:    */   }
/*  86:    */   
/*  87:    */   public int getTextureID()
/*  88:    */   {
/*  89:120 */     checkTarget();
/*  90:121 */     return this.target.getTextureID();
/*  91:    */   }
/*  92:    */   
/*  93:    */   public String getTextureRef()
/*  94:    */   {
/*  95:128 */     checkTarget();
/*  96:129 */     return this.target.getTextureRef();
/*  97:    */   }
/*  98:    */   
/*  99:    */   public int getTextureWidth()
/* 100:    */   {
/* 101:136 */     checkTarget();
/* 102:137 */     return this.target.getTextureWidth();
/* 103:    */   }
/* 104:    */   
/* 105:    */   public float getWidth()
/* 106:    */   {
/* 107:144 */     checkTarget();
/* 108:145 */     return this.target.getWidth();
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void release()
/* 112:    */   {
/* 113:152 */     checkTarget();
/* 114:153 */     this.target.release();
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void setAlpha(boolean alpha)
/* 118:    */   {
/* 119:160 */     checkTarget();
/* 120:161 */     this.target.setAlpha(alpha);
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void setHeight(int height)
/* 124:    */   {
/* 125:168 */     checkTarget();
/* 126:169 */     this.target.setHeight(height);
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void setTextureHeight(int texHeight)
/* 130:    */   {
/* 131:176 */     checkTarget();
/* 132:177 */     this.target.setTextureHeight(texHeight);
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void setTextureID(int textureID)
/* 136:    */   {
/* 137:184 */     checkTarget();
/* 138:185 */     this.target.setTextureID(textureID);
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void setTextureWidth(int texWidth)
/* 142:    */   {
/* 143:192 */     checkTarget();
/* 144:193 */     this.target.setTextureWidth(texWidth);
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void setWidth(int width)
/* 148:    */   {
/* 149:200 */     checkTarget();
/* 150:201 */     this.target.setWidth(width);
/* 151:    */   }
/* 152:    */   
/* 153:    */   public byte[] getTextureData()
/* 154:    */   {
/* 155:208 */     checkTarget();
/* 156:209 */     return this.target.getTextureData();
/* 157:    */   }
/* 158:    */   
/* 159:    */   public String getDescription()
/* 160:    */   {
/* 161:216 */     return this.resourceName;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public boolean hasAlpha()
/* 165:    */   {
/* 166:223 */     checkTarget();
/* 167:224 */     return this.target.hasAlpha();
/* 168:    */   }
/* 169:    */   
/* 170:    */   public void setTextureFilter(int textureFilter)
/* 171:    */   {
/* 172:231 */     checkTarget();
/* 173:232 */     this.target.setTextureFilter(textureFilter);
/* 174:    */   }
/* 175:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.opengl.DeferredTexture
 * JD-Core Version:    0.7.0.1
 */