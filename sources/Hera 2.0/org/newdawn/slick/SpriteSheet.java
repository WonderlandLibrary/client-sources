/*     */ package org.newdawn.slick;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ import org.newdawn.slick.opengl.Texture;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SpriteSheet
/*     */   extends Image
/*     */ {
/*     */   private int tw;
/*     */   private int th;
/*  20 */   private int margin = 0;
/*     */ 
/*     */ 
/*     */   
/*     */   private Image[][] subImages;
/*     */ 
/*     */ 
/*     */   
/*     */   private int spacing;
/*     */ 
/*     */ 
/*     */   
/*     */   private Image target;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SpriteSheet(URL ref, int tw, int th) throws SlickException, IOException {
/*  38 */     this(new Image(ref.openStream(), ref.toString(), false), tw, th);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SpriteSheet(Image image, int tw, int th) {
/*  49 */     super(image);
/*     */     
/*  51 */     this.target = image;
/*  52 */     this.tw = tw;
/*  53 */     this.th = th;
/*     */ 
/*     */ 
/*     */     
/*  57 */     initImpl();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SpriteSheet(Image image, int tw, int th, int spacing, int margin) {
/*  70 */     super(image);
/*     */     
/*  72 */     this.target = image;
/*  73 */     this.tw = tw;
/*  74 */     this.th = th;
/*  75 */     this.spacing = spacing;
/*  76 */     this.margin = margin;
/*     */ 
/*     */ 
/*     */     
/*  80 */     initImpl();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SpriteSheet(Image image, int tw, int th, int spacing) {
/*  92 */     this(image, tw, th, spacing, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SpriteSheet(String ref, int tw, int th, int spacing) throws SlickException {
/* 105 */     this(ref, tw, th, (Color)null, spacing);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SpriteSheet(String ref, int tw, int th) throws SlickException {
/* 117 */     this(ref, tw, th, (Color)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SpriteSheet(String ref, int tw, int th, Color col) throws SlickException {
/* 130 */     this(ref, tw, th, col, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SpriteSheet(String ref, int tw, int th, Color col, int spacing) throws SlickException {
/* 144 */     super(ref, false, 2, col);
/*     */     
/* 146 */     this.target = this;
/* 147 */     this.tw = tw;
/* 148 */     this.th = th;
/* 149 */     this.spacing = spacing;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SpriteSheet(String name, InputStream ref, int tw, int th) throws SlickException {
/* 162 */     super(ref, name, false);
/*     */     
/* 164 */     this.target = this;
/* 165 */     this.tw = tw;
/* 166 */     this.th = th;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initImpl() {
/* 173 */     if (this.subImages != null) {
/*     */       return;
/*     */     }
/*     */     
/* 177 */     int tilesAcross = (getWidth() - this.margin * 2 - this.tw) / (this.tw + this.spacing) + 1;
/* 178 */     int tilesDown = (getHeight() - this.margin * 2 - this.th) / (this.th + this.spacing) + 1;
/* 179 */     if ((getHeight() - this.th) % (this.th + this.spacing) != 0) {
/* 180 */       tilesDown++;
/*     */     }
/*     */     
/* 183 */     this.subImages = new Image[tilesAcross][tilesDown];
/* 184 */     for (int x = 0; x < tilesAcross; x++) {
/* 185 */       for (int y = 0; y < tilesDown; y++) {
/* 186 */         this.subImages[x][y] = getSprite(x, y);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Image getSubImage(int x, int y) {
/* 199 */     init();
/*     */     
/* 201 */     if (x < 0 || x >= this.subImages.length) {
/* 202 */       throw new RuntimeException("SubImage out of sheet bounds: " + x + "," + y);
/*     */     }
/* 204 */     if (y < 0 || y >= (this.subImages[0]).length) {
/* 205 */       throw new RuntimeException("SubImage out of sheet bounds: " + x + "," + y);
/*     */     }
/*     */     
/* 208 */     return this.subImages[x][y];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Image getSprite(int x, int y) {
/* 219 */     this.target.init();
/* 220 */     initImpl();
/*     */     
/* 222 */     if (x < 0 || x >= this.subImages.length) {
/* 223 */       throw new RuntimeException("SubImage out of sheet bounds: " + x + "," + y);
/*     */     }
/* 225 */     if (y < 0 || y >= (this.subImages[0]).length) {
/* 226 */       throw new RuntimeException("SubImage out of sheet bounds: " + x + "," + y);
/*     */     }
/*     */     
/* 229 */     return this.target.getSubImage(x * (this.tw + this.spacing) + this.margin, y * (this.th + this.spacing) + this.margin, this.tw, this.th);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getHorizontalCount() {
/* 238 */     this.target.init();
/* 239 */     initImpl();
/*     */     
/* 241 */     return this.subImages.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getVerticalCount() {
/* 250 */     this.target.init();
/* 251 */     initImpl();
/*     */     
/* 253 */     return (this.subImages[0]).length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderInUse(int x, int y, int sx, int sy) {
/* 268 */     this.subImages[sx][sy].drawEmbedded(x, y, this.tw, this.th);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endUse() {
/* 275 */     if (this.target == this) {
/* 276 */       super.endUse();
/*     */       return;
/*     */     } 
/* 279 */     this.target.endUse();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startUse() {
/* 286 */     if (this.target == this) {
/* 287 */       super.startUse();
/*     */       return;
/*     */     } 
/* 290 */     this.target.startUse();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTexture(Texture texture) {
/* 297 */     if (this.target == this) {
/* 298 */       super.setTexture(texture);
/*     */       return;
/*     */     } 
/* 301 */     this.target.setTexture(texture);
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\SpriteSheet.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */