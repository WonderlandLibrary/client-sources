/*     */ package org.newdawn.slick.tests;
/*     */ 
/*     */ import java.nio.FloatBuffer;
/*     */ import org.lwjgl.BufferUtils;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.newdawn.slick.AngelCodeFont;
/*     */ import org.newdawn.slick.Animation;
/*     */ import org.newdawn.slick.AppGameContainer;
/*     */ import org.newdawn.slick.BasicGame;
/*     */ import org.newdawn.slick.Game;
/*     */ import org.newdawn.slick.GameContainer;
/*     */ import org.newdawn.slick.Graphics;
/*     */ import org.newdawn.slick.Image;
/*     */ import org.newdawn.slick.SlickException;
/*     */ import org.newdawn.slick.SpriteSheet;
/*     */ import org.newdawn.slick.opengl.SlickCallable;
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
/*     */ public class SlickCallableTest
/*     */   extends BasicGame
/*     */ {
/*     */   private Image image;
/*     */   private Image back;
/*     */   private float rot;
/*     */   private AngelCodeFont font;
/*     */   private Animation homer;
/*     */   
/*     */   public SlickCallableTest() {
/*  39 */     super("Slick Callable Test");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(GameContainer container) throws SlickException {
/*  46 */     this.image = new Image("testdata/rocket.png");
/*  47 */     this.back = new Image("testdata/sky.jpg");
/*  48 */     this.font = new AngelCodeFont("testdata/hiero.fnt", "testdata/hiero.png");
/*  49 */     SpriteSheet sheet = new SpriteSheet("testdata/homeranim.png", 36, 65);
/*  50 */     this.homer = new Animation(sheet, 0, 0, 7, 0, true, 150, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(GameContainer container, Graphics g) throws SlickException {
/*  57 */     g.scale(2.0F, 2.0F);
/*  58 */     g.fillRect(0.0F, 0.0F, 800.0F, 600.0F, this.back, 0.0F, 0.0F);
/*  59 */     g.resetTransform();
/*     */     
/*  61 */     g.drawImage(this.image, 100.0F, 100.0F);
/*  62 */     this.image.draw(100.0F, 200.0F, 80.0F, 200.0F);
/*     */     
/*  64 */     this.font.drawString(100.0F, 200.0F, "Text Drawn before the callable");
/*     */     
/*  66 */     SlickCallable callable = new SlickCallable() {
/*     */         protected void performGLOperations() throws SlickException {
/*  68 */           SlickCallableTest.this.renderGL();
/*     */         }
/*     */       };
/*  71 */     callable.call();
/*     */     
/*  73 */     this.homer.draw(450.0F, 250.0F, 80.0F, 200.0F);
/*  74 */     this.font.drawString(150.0F, 300.0F, "Text Drawn after the callable");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderGL() {
/*  83 */     FloatBuffer pos = BufferUtils.createFloatBuffer(4);
/*  84 */     pos.put(new float[] { 5.0F, 5.0F, 10.0F, 0.0F }).flip();
/*  85 */     FloatBuffer red = BufferUtils.createFloatBuffer(4);
/*  86 */     red.put(new float[] { 0.8F, 0.1F, 0.0F, 1.0F }).flip();
/*     */     
/*  88 */     GL11.glLight(16384, 4611, pos);
/*  89 */     GL11.glEnable(16384);
/*     */     
/*  91 */     GL11.glEnable(2884);
/*  92 */     GL11.glEnable(2929);
/*  93 */     GL11.glEnable(2896);
/*     */     
/*  95 */     GL11.glMatrixMode(5889);
/*  96 */     GL11.glLoadIdentity();
/*  97 */     float h = 0.75F;
/*  98 */     GL11.glFrustum(-1.0D, 1.0D, -h, h, 5.0D, 60.0D);
/*  99 */     GL11.glMatrixMode(5888);
/* 100 */     GL11.glLoadIdentity();
/* 101 */     GL11.glTranslatef(0.0F, 0.0F, -40.0F);
/* 102 */     GL11.glRotatef(this.rot, 0.0F, 1.0F, 1.0F);
/*     */     
/* 104 */     GL11.glMaterial(1028, 5634, red);
/* 105 */     gear(0.5F, 2.0F, 2.0F, 10, 0.7F);
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
/*     */ 
/*     */ 
/*     */   
/*     */   private void gear(float inner_radius, float outer_radius, float width, int teeth, float tooth_depth) {
/* 123 */     float r0 = inner_radius;
/* 124 */     float r1 = outer_radius - tooth_depth / 2.0F;
/* 125 */     float r2 = outer_radius + tooth_depth / 2.0F;
/*     */     
/* 127 */     float da = 6.2831855F / teeth / 4.0F;
/*     */     
/* 129 */     GL11.glShadeModel(7424);
/*     */     
/* 131 */     GL11.glNormal3f(0.0F, 0.0F, 1.0F);
/*     */ 
/*     */     
/* 134 */     GL11.glBegin(8); int i;
/* 135 */     for (i = 0; i <= teeth; i++) {
/* 136 */       float angle = i * 2.0F * 3.1415927F / teeth;
/* 137 */       GL11.glVertex3f(r0 * (float)Math.cos(angle), r0 * (float)Math.sin(angle), width * 0.5F);
/* 138 */       GL11.glVertex3f(r1 * (float)Math.cos(angle), r1 * (float)Math.sin(angle), width * 0.5F);
/* 139 */       if (i < teeth) {
/* 140 */         GL11.glVertex3f(r0 * (float)Math.cos(angle), r0 * (float)Math.sin(angle), width * 0.5F);
/* 141 */         GL11.glVertex3f(r1 * (float)Math.cos((angle + 3.0F * da)), r1 * (float)Math.sin((angle + 3.0F * da)), width * 0.5F);
/*     */       } 
/*     */     } 
/*     */     
/* 145 */     GL11.glEnd();
/*     */ 
/*     */     
/* 148 */     GL11.glBegin(7);
/* 149 */     for (i = 0; i < teeth; i++) {
/* 150 */       float angle = i * 2.0F * 3.1415927F / teeth;
/* 151 */       GL11.glVertex3f(r1 * (float)Math.cos(angle), r1 * (float)Math.sin(angle), width * 0.5F);
/* 152 */       GL11.glVertex3f(r2 * (float)Math.cos((angle + da)), r2 * (float)Math.sin((angle + da)), width * 0.5F);
/* 153 */       GL11.glVertex3f(r2 * (float)Math.cos((angle + 2.0F * da)), r2 * (float)Math.sin((angle + 2.0F * da)), width * 0.5F);
/* 154 */       GL11.glVertex3f(r1 * (float)Math.cos((angle + 3.0F * da)), r1 * (float)Math.sin((angle + 3.0F * da)), width * 0.5F);
/*     */     } 
/* 156 */     GL11.glEnd();
/*     */ 
/*     */     
/* 159 */     GL11.glNormal3f(0.0F, 0.0F, -1.0F);
/* 160 */     GL11.glBegin(8);
/* 161 */     for (i = 0; i <= teeth; i++) {
/* 162 */       float angle = i * 2.0F * 3.1415927F / teeth;
/* 163 */       GL11.glVertex3f(r1 * (float)Math.cos(angle), r1 * (float)Math.sin(angle), -width * 0.5F);
/* 164 */       GL11.glVertex3f(r0 * (float)Math.cos(angle), r0 * (float)Math.sin(angle), -width * 0.5F);
/* 165 */       GL11.glVertex3f(r1 * (float)Math.cos((angle + 3.0F * da)), r1 * (float)Math.sin((angle + 3.0F * da)), -width * 0.5F);
/* 166 */       GL11.glVertex3f(r0 * (float)Math.cos(angle), r0 * (float)Math.sin(angle), -width * 0.5F);
/*     */     } 
/* 168 */     GL11.glEnd();
/*     */ 
/*     */     
/* 171 */     GL11.glBegin(7);
/* 172 */     for (i = 0; i < teeth; i++) {
/* 173 */       float angle = i * 2.0F * 3.1415927F / teeth;
/* 174 */       GL11.glVertex3f(r1 * (float)Math.cos((angle + 3.0F * da)), r1 * (float)Math.sin((angle + 3.0F * da)), -width * 0.5F);
/* 175 */       GL11.glVertex3f(r2 * (float)Math.cos((angle + 2.0F * da)), r2 * (float)Math.sin((angle + 2.0F * da)), -width * 0.5F);
/* 176 */       GL11.glVertex3f(r2 * (float)Math.cos((angle + da)), r2 * (float)Math.sin((angle + da)), -width * 0.5F);
/* 177 */       GL11.glVertex3f(r1 * (float)Math.cos(angle), r1 * (float)Math.sin(angle), -width * 0.5F);
/*     */     } 
/* 179 */     GL11.glEnd();
/* 180 */     GL11.glNormal3f(0.0F, 0.0F, 1.0F);
/*     */ 
/*     */     
/* 183 */     GL11.glBegin(8);
/* 184 */     for (i = 0; i < teeth; i++) {
/* 185 */       float angle = i * 2.0F * 3.1415927F / teeth;
/* 186 */       GL11.glVertex3f(r1 * (float)Math.cos(angle), r1 * (float)Math.sin(angle), width * 0.5F);
/* 187 */       GL11.glVertex3f(r1 * (float)Math.cos(angle), r1 * (float)Math.sin(angle), -width * 0.5F);
/* 188 */       float u = r2 * (float)Math.cos((angle + da)) - r1 * (float)Math.cos(angle);
/* 189 */       float v = r2 * (float)Math.sin((angle + da)) - r1 * (float)Math.sin(angle);
/* 190 */       float len = (float)Math.sqrt((u * u + v * v));
/* 191 */       u /= len;
/* 192 */       v /= len;
/* 193 */       GL11.glNormal3f(v, -u, 0.0F);
/* 194 */       GL11.glVertex3f(r2 * (float)Math.cos((angle + da)), r2 * (float)Math.sin((angle + da)), width * 0.5F);
/* 195 */       GL11.glVertex3f(r2 * (float)Math.cos((angle + da)), r2 * (float)Math.sin((angle + da)), -width * 0.5F);
/* 196 */       GL11.glNormal3f((float)Math.cos(angle), (float)Math.sin(angle), 0.0F);
/* 197 */       GL11.glVertex3f(r2 * (float)Math.cos((angle + 2.0F * da)), r2 * (float)Math.sin((angle + 2.0F * da)), width * 0.5F);
/* 198 */       GL11.glVertex3f(r2 * (float)Math.cos((angle + 2.0F * da)), r2 * (float)Math.sin((angle + 2.0F * da)), -width * 0.5F);
/* 199 */       u = r1 * (float)Math.cos((angle + 3.0F * da)) - r2 * (float)Math.cos((angle + 2.0F * da));
/* 200 */       v = r1 * (float)Math.sin((angle + 3.0F * da)) - r2 * (float)Math.sin((angle + 2.0F * da));
/* 201 */       GL11.glNormal3f(v, -u, 0.0F);
/* 202 */       GL11.glVertex3f(r1 * (float)Math.cos((angle + 3.0F * da)), r1 * (float)Math.sin((angle + 3.0F * da)), width * 0.5F);
/* 203 */       GL11.glVertex3f(r1 * (float)Math.cos((angle + 3.0F * da)), r1 * (float)Math.sin((angle + 3.0F * da)), -width * 0.5F);
/* 204 */       GL11.glNormal3f((float)Math.cos(angle), (float)Math.sin(angle), 0.0F);
/*     */     } 
/* 206 */     GL11.glVertex3f(r1 * (float)Math.cos(0.0D), r1 * (float)Math.sin(0.0D), width * 0.5F);
/* 207 */     GL11.glVertex3f(r1 * (float)Math.cos(0.0D), r1 * (float)Math.sin(0.0D), -width * 0.5F);
/* 208 */     GL11.glEnd();
/*     */     
/* 210 */     GL11.glShadeModel(7425);
/*     */ 
/*     */     
/* 213 */     GL11.glBegin(8);
/* 214 */     for (i = 0; i <= teeth; i++) {
/* 215 */       float angle = i * 2.0F * 3.1415927F / teeth;
/* 216 */       GL11.glNormal3f(-((float)Math.cos(angle)), -((float)Math.sin(angle)), 0.0F);
/* 217 */       GL11.glVertex3f(r0 * (float)Math.cos(angle), r0 * (float)Math.sin(angle), -width * 0.5F);
/* 218 */       GL11.glVertex3f(r0 * (float)Math.cos(angle), r0 * (float)Math.sin(angle), width * 0.5F);
/*     */     } 
/* 220 */     GL11.glEnd();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(GameContainer container, int delta) {
/* 227 */     this.rot += delta * 0.1F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] argv) {
/*     */     try {
/* 237 */       AppGameContainer container = new AppGameContainer((Game)new SlickCallableTest());
/* 238 */       container.setDisplayMode(800, 600, false);
/* 239 */       container.start();
/* 240 */     } catch (SlickException e) {
/* 241 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\tests\SlickCallableTest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */