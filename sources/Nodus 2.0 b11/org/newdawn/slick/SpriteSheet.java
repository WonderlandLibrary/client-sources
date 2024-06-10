/*   1:    */ package org.newdawn.slick;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import java.net.URL;
/*   6:    */ import org.newdawn.slick.opengl.Texture;
/*   7:    */ 
/*   8:    */ public class SpriteSheet
/*   9:    */   extends Image
/*  10:    */ {
/*  11:    */   private int tw;
/*  12:    */   private int th;
/*  13: 20 */   private int margin = 0;
/*  14:    */   private Image[][] subImages;
/*  15:    */   private int spacing;
/*  16:    */   private Image target;
/*  17:    */   
/*  18:    */   public SpriteSheet(URL ref, int tw, int th)
/*  19:    */     throws SlickException, IOException
/*  20:    */   {
/*  21: 38 */     this(new Image(ref.openStream(), ref.toString(), false), tw, th);
/*  22:    */   }
/*  23:    */   
/*  24:    */   public SpriteSheet(Image image, int tw, int th)
/*  25:    */   {
/*  26: 49 */     super(image);
/*  27:    */     
/*  28: 51 */     this.target = image;
/*  29: 52 */     this.tw = tw;
/*  30: 53 */     this.th = th;
/*  31:    */     
/*  32:    */ 
/*  33:    */ 
/*  34: 57 */     initImpl();
/*  35:    */   }
/*  36:    */   
/*  37:    */   public SpriteSheet(Image image, int tw, int th, int spacing, int margin)
/*  38:    */   {
/*  39: 70 */     super(image);
/*  40:    */     
/*  41: 72 */     this.target = image;
/*  42: 73 */     this.tw = tw;
/*  43: 74 */     this.th = th;
/*  44: 75 */     this.spacing = spacing;
/*  45: 76 */     this.margin = margin;
/*  46:    */     
/*  47:    */ 
/*  48:    */ 
/*  49: 80 */     initImpl();
/*  50:    */   }
/*  51:    */   
/*  52:    */   public SpriteSheet(Image image, int tw, int th, int spacing)
/*  53:    */   {
/*  54: 92 */     this(image, tw, th, spacing, 0);
/*  55:    */   }
/*  56:    */   
/*  57:    */   public SpriteSheet(String ref, int tw, int th, int spacing)
/*  58:    */     throws SlickException
/*  59:    */   {
/*  60:105 */     this(ref, tw, th, null, spacing);
/*  61:    */   }
/*  62:    */   
/*  63:    */   public SpriteSheet(String ref, int tw, int th)
/*  64:    */     throws SlickException
/*  65:    */   {
/*  66:117 */     this(ref, tw, th, null);
/*  67:    */   }
/*  68:    */   
/*  69:    */   public SpriteSheet(String ref, int tw, int th, Color col)
/*  70:    */     throws SlickException
/*  71:    */   {
/*  72:130 */     this(ref, tw, th, col, 0);
/*  73:    */   }
/*  74:    */   
/*  75:    */   public SpriteSheet(String ref, int tw, int th, Color col, int spacing)
/*  76:    */     throws SlickException
/*  77:    */   {
/*  78:144 */     super(ref, false, 2, col);
/*  79:    */     
/*  80:146 */     this.target = this;
/*  81:147 */     this.tw = tw;
/*  82:148 */     this.th = th;
/*  83:149 */     this.spacing = spacing;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public SpriteSheet(String name, InputStream ref, int tw, int th)
/*  87:    */     throws SlickException
/*  88:    */   {
/*  89:162 */     super(ref, name, false);
/*  90:    */     
/*  91:164 */     this.target = this;
/*  92:165 */     this.tw = tw;
/*  93:166 */     this.th = th;
/*  94:    */   }
/*  95:    */   
/*  96:    */   protected void initImpl()
/*  97:    */   {
/*  98:173 */     if (this.subImages != null) {
/*  99:174 */       return;
/* 100:    */     }
/* 101:177 */     int tilesAcross = (getWidth() - this.margin * 2 - this.tw) / (this.tw + this.spacing) + 1;
/* 102:178 */     int tilesDown = (getHeight() - this.margin * 2 - this.th) / (this.th + this.spacing) + 1;
/* 103:179 */     if ((getHeight() - this.th) % (this.th + this.spacing) != 0) {
/* 104:180 */       tilesDown++;
/* 105:    */     }
/* 106:183 */     this.subImages = new Image[tilesAcross][tilesDown];
/* 107:184 */     for (int x = 0; x < tilesAcross; x++) {
/* 108:185 */       for (int y = 0; y < tilesDown; y++) {
/* 109:186 */         this.subImages[x][y] = getSprite(x, y);
/* 110:    */       }
/* 111:    */     }
/* 112:    */   }
/* 113:    */   
/* 114:    */   public Image getSubImage(int x, int y)
/* 115:    */   {
/* 116:199 */     init();
/* 117:201 */     if ((x < 0) || (x >= this.subImages.length)) {
/* 118:202 */       throw new RuntimeException("SubImage out of sheet bounds: " + x + "," + y);
/* 119:    */     }
/* 120:204 */     if ((y < 0) || (y >= this.subImages[0].length)) {
/* 121:205 */       throw new RuntimeException("SubImage out of sheet bounds: " + x + "," + y);
/* 122:    */     }
/* 123:208 */     return this.subImages[x][y];
/* 124:    */   }
/* 125:    */   
/* 126:    */   public Image getSprite(int x, int y)
/* 127:    */   {
/* 128:219 */     this.target.init();
/* 129:220 */     initImpl();
/* 130:222 */     if ((x < 0) || (x >= this.subImages.length)) {
/* 131:223 */       throw new RuntimeException("SubImage out of sheet bounds: " + x + "," + y);
/* 132:    */     }
/* 133:225 */     if ((y < 0) || (y >= this.subImages[0].length)) {
/* 134:226 */       throw new RuntimeException("SubImage out of sheet bounds: " + x + "," + y);
/* 135:    */     }
/* 136:229 */     return this.target.getSubImage(x * (this.tw + this.spacing) + this.margin, y * (this.th + this.spacing) + this.margin, this.tw, this.th);
/* 137:    */   }
/* 138:    */   
/* 139:    */   public int getHorizontalCount()
/* 140:    */   {
/* 141:238 */     this.target.init();
/* 142:239 */     initImpl();
/* 143:    */     
/* 144:241 */     return this.subImages.length;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public int getVerticalCount()
/* 148:    */   {
/* 149:250 */     this.target.init();
/* 150:251 */     initImpl();
/* 151:    */     
/* 152:253 */     return this.subImages[0].length;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void renderInUse(int x, int y, int sx, int sy)
/* 156:    */   {
/* 157:268 */     this.subImages[sx][sy].drawEmbedded(x, y, this.tw, this.th);
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void endUse()
/* 161:    */   {
/* 162:275 */     if (this.target == this)
/* 163:    */     {
/* 164:276 */       super.endUse();
/* 165:277 */       return;
/* 166:    */     }
/* 167:279 */     this.target.endUse();
/* 168:    */   }
/* 169:    */   
/* 170:    */   public void startUse()
/* 171:    */   {
/* 172:286 */     if (this.target == this)
/* 173:    */     {
/* 174:287 */       super.startUse();
/* 175:288 */       return;
/* 176:    */     }
/* 177:290 */     this.target.startUse();
/* 178:    */   }
/* 179:    */   
/* 180:    */   public void setTexture(Texture texture)
/* 181:    */   {
/* 182:297 */     if (this.target == this)
/* 183:    */     {
/* 184:298 */       super.setTexture(texture);
/* 185:299 */       return;
/* 186:    */     }
/* 187:301 */     this.target.setTexture(texture);
/* 188:    */   }
/* 189:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.SpriteSheet
 * JD-Core Version:    0.7.0.1
 */