/*   1:    */ package org.newdawn.slick.tests;
/*   2:    */ 
/*   3:    */ import java.nio.FloatBuffer;
/*   4:    */ import org.lwjgl.BufferUtils;
/*   5:    */ import org.lwjgl.opengl.GL11;
/*   6:    */ import org.newdawn.slick.AngelCodeFont;
/*   7:    */ import org.newdawn.slick.Animation;
/*   8:    */ import org.newdawn.slick.AppGameContainer;
/*   9:    */ import org.newdawn.slick.BasicGame;
/*  10:    */ import org.newdawn.slick.GameContainer;
/*  11:    */ import org.newdawn.slick.Graphics;
/*  12:    */ import org.newdawn.slick.Image;
/*  13:    */ import org.newdawn.slick.SlickException;
/*  14:    */ import org.newdawn.slick.SpriteSheet;
/*  15:    */ import org.newdawn.slick.opengl.SlickCallable;
/*  16:    */ 
/*  17:    */ public class SlickCallableTest
/*  18:    */   extends BasicGame
/*  19:    */ {
/*  20:    */   private Image image;
/*  21:    */   private Image back;
/*  22:    */   private float rot;
/*  23:    */   private AngelCodeFont font;
/*  24:    */   private Animation homer;
/*  25:    */   
/*  26:    */   public SlickCallableTest()
/*  27:    */   {
/*  28: 39 */     super("Slick Callable Test");
/*  29:    */   }
/*  30:    */   
/*  31:    */   public void init(GameContainer container)
/*  32:    */     throws SlickException
/*  33:    */   {
/*  34: 46 */     this.image = new Image("testdata/rocket.png");
/*  35: 47 */     this.back = new Image("testdata/sky.jpg");
/*  36: 48 */     this.font = new AngelCodeFont("testdata/hiero.fnt", "testdata/hiero.png");
/*  37: 49 */     SpriteSheet sheet = new SpriteSheet("testdata/homeranim.png", 36, 65);
/*  38: 50 */     this.homer = new Animation(sheet, 0, 0, 7, 0, true, 150, true);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void render(GameContainer container, Graphics g)
/*  42:    */     throws SlickException
/*  43:    */   {
/*  44: 57 */     g.scale(2.0F, 2.0F);
/*  45: 58 */     g.fillRect(0.0F, 0.0F, 800.0F, 600.0F, this.back, 0.0F, 0.0F);
/*  46: 59 */     g.resetTransform();
/*  47:    */     
/*  48: 61 */     g.drawImage(this.image, 100.0F, 100.0F);
/*  49: 62 */     this.image.draw(100.0F, 200.0F, 80.0F, 200.0F);
/*  50:    */     
/*  51: 64 */     this.font.drawString(100.0F, 200.0F, "Text Drawn before the callable");
/*  52:    */     
/*  53: 66 */     SlickCallable callable = new SlickCallable()
/*  54:    */     {
/*  55:    */       protected void performGLOperations()
/*  56:    */         throws SlickException
/*  57:    */       {
/*  58: 68 */         SlickCallableTest.this.renderGL();
/*  59:    */       }
/*  60: 70 */     };
/*  61: 71 */     callable.call();
/*  62:    */     
/*  63: 73 */     this.homer.draw(450.0F, 250.0F, 80.0F, 200.0F);
/*  64: 74 */     this.font.drawString(150.0F, 300.0F, "Text Drawn after the callable");
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void renderGL()
/*  68:    */   {
/*  69: 83 */     FloatBuffer pos = BufferUtils.createFloatBuffer(4);
/*  70: 84 */     pos.put(new float[] { 5.0F, 5.0F, 10.0F, 0.0F }).flip();
/*  71: 85 */     FloatBuffer red = BufferUtils.createFloatBuffer(4);
/*  72: 86 */     red.put(new float[] { 0.8F, 0.1F, 0.0F, 1.0F }).flip();
/*  73:    */     
/*  74: 88 */     GL11.glLight(16384, 4611, pos);
/*  75: 89 */     GL11.glEnable(16384);
/*  76:    */     
/*  77: 91 */     GL11.glEnable(2884);
/*  78: 92 */     GL11.glEnable(2929);
/*  79: 93 */     GL11.glEnable(2896);
/*  80:    */     
/*  81: 95 */     GL11.glMatrixMode(5889);
/*  82: 96 */     GL11.glLoadIdentity();
/*  83: 97 */     float h = 0.75F;
/*  84: 98 */     GL11.glFrustum(-1.0D, 1.0D, -h, h, 5.0D, 60.0D);
/*  85: 99 */     GL11.glMatrixMode(5888);
/*  86:100 */     GL11.glLoadIdentity();
/*  87:101 */     GL11.glTranslatef(0.0F, 0.0F, -40.0F);
/*  88:102 */     GL11.glRotatef(this.rot, 0.0F, 1.0F, 1.0F);
/*  89:    */     
/*  90:104 */     GL11.glMaterial(1028, 5634, red);
/*  91:105 */     gear(0.5F, 2.0F, 2.0F, 10, 0.7F);
/*  92:    */   }
/*  93:    */   
/*  94:    */   private void gear(float inner_radius, float outer_radius, float width, int teeth, float tooth_depth)
/*  95:    */   {
/*  96:123 */     float r0 = inner_radius;
/*  97:124 */     float r1 = outer_radius - tooth_depth / 2.0F;
/*  98:125 */     float r2 = outer_radius + tooth_depth / 2.0F;
/*  99:    */     
/* 100:127 */     float da = 6.283186F / teeth / 4.0F;
/* 101:    */     
/* 102:129 */     GL11.glShadeModel(7424);
/* 103:    */     
/* 104:131 */     GL11.glNormal3f(0.0F, 0.0F, 1.0F);
/* 105:    */     
/* 106:    */ 
/* 107:134 */     GL11.glBegin(8);
/* 108:135 */     for (int i = 0; i <= teeth; i++)
/* 109:    */     {
/* 110:136 */       float angle = i * 2.0F * 3.141593F / teeth;
/* 111:137 */       GL11.glVertex3f(r0 * (float)Math.cos(angle), r0 * (float)Math.sin(angle), width * 0.5F);
/* 112:138 */       GL11.glVertex3f(r1 * (float)Math.cos(angle), r1 * (float)Math.sin(angle), width * 0.5F);
/* 113:139 */       if (i < teeth)
/* 114:    */       {
/* 115:140 */         GL11.glVertex3f(r0 * (float)Math.cos(angle), r0 * (float)Math.sin(angle), width * 0.5F);
/* 116:141 */         GL11.glVertex3f(r1 * (float)Math.cos(angle + 3.0F * da), r1 * (float)Math.sin(angle + 3.0F * da), 
/* 117:142 */           width * 0.5F);
/* 118:    */       }
/* 119:    */     }
/* 120:145 */     GL11.glEnd();
/* 121:    */     
/* 122:    */ 
/* 123:148 */     GL11.glBegin(7);
/* 124:149 */     for (i = 0; i < teeth; i++)
/* 125:    */     {
/* 126:150 */       float angle = i * 2.0F * 3.141593F / teeth;
/* 127:151 */       GL11.glVertex3f(r1 * (float)Math.cos(angle), r1 * (float)Math.sin(angle), width * 0.5F);
/* 128:152 */       GL11.glVertex3f(r2 * (float)Math.cos(angle + da), r2 * (float)Math.sin(angle + da), width * 0.5F);
/* 129:153 */       GL11.glVertex3f(r2 * (float)Math.cos(angle + 2.0F * da), r2 * (float)Math.sin(angle + 2.0F * da), width * 0.5F);
/* 130:154 */       GL11.glVertex3f(r1 * (float)Math.cos(angle + 3.0F * da), r1 * (float)Math.sin(angle + 3.0F * da), width * 0.5F);
/* 131:    */     }
/* 132:156 */     GL11.glEnd();
/* 133:    */     
/* 134:    */ 
/* 135:159 */     GL11.glNormal3f(0.0F, 0.0F, -1.0F);
/* 136:160 */     GL11.glBegin(8);
/* 137:161 */     for (i = 0; i <= teeth; i++)
/* 138:    */     {
/* 139:162 */       float angle = i * 2.0F * 3.141593F / teeth;
/* 140:163 */       GL11.glVertex3f(r1 * (float)Math.cos(angle), r1 * (float)Math.sin(angle), -width * 0.5F);
/* 141:164 */       GL11.glVertex3f(r0 * (float)Math.cos(angle), r0 * (float)Math.sin(angle), -width * 0.5F);
/* 142:165 */       GL11.glVertex3f(r1 * (float)Math.cos(angle + 3.0F * da), r1 * (float)Math.sin(angle + 3.0F * da), -width * 0.5F);
/* 143:166 */       GL11.glVertex3f(r0 * (float)Math.cos(angle), r0 * (float)Math.sin(angle), -width * 0.5F);
/* 144:    */     }
/* 145:168 */     GL11.glEnd();
/* 146:    */     
/* 147:    */ 
/* 148:171 */     GL11.glBegin(7);
/* 149:172 */     for (i = 0; i < teeth; i++)
/* 150:    */     {
/* 151:173 */       float angle = i * 2.0F * 3.141593F / teeth;
/* 152:174 */       GL11.glVertex3f(r1 * (float)Math.cos(angle + 3.0F * da), r1 * (float)Math.sin(angle + 3.0F * da), -width * 0.5F);
/* 153:175 */       GL11.glVertex3f(r2 * (float)Math.cos(angle + 2.0F * da), r2 * (float)Math.sin(angle + 2.0F * da), -width * 0.5F);
/* 154:176 */       GL11.glVertex3f(r2 * (float)Math.cos(angle + da), r2 * (float)Math.sin(angle + da), -width * 0.5F);
/* 155:177 */       GL11.glVertex3f(r1 * (float)Math.cos(angle), r1 * (float)Math.sin(angle), -width * 0.5F);
/* 156:    */     }
/* 157:179 */     GL11.glEnd();
/* 158:180 */     GL11.glNormal3f(0.0F, 0.0F, 1.0F);
/* 159:    */     
/* 160:    */ 
/* 161:183 */     GL11.glBegin(8);
/* 162:184 */     for (i = 0; i < teeth; i++)
/* 163:    */     {
/* 164:185 */       float angle = i * 2.0F * 3.141593F / teeth;
/* 165:186 */       GL11.glVertex3f(r1 * (float)Math.cos(angle), r1 * (float)Math.sin(angle), width * 0.5F);
/* 166:187 */       GL11.glVertex3f(r1 * (float)Math.cos(angle), r1 * (float)Math.sin(angle), -width * 0.5F);
/* 167:188 */       float u = r2 * (float)Math.cos(angle + da) - r1 * (float)Math.cos(angle);
/* 168:189 */       float v = r2 * (float)Math.sin(angle + da) - r1 * (float)Math.sin(angle);
/* 169:190 */       float len = (float)Math.sqrt(u * u + v * v);
/* 170:191 */       u /= len;
/* 171:192 */       v /= len;
/* 172:193 */       GL11.glNormal3f(v, -u, 0.0F);
/* 173:194 */       GL11.glVertex3f(r2 * (float)Math.cos(angle + da), r2 * (float)Math.sin(angle + da), width * 0.5F);
/* 174:195 */       GL11.glVertex3f(r2 * (float)Math.cos(angle + da), r2 * (float)Math.sin(angle + da), -width * 0.5F);
/* 175:196 */       GL11.glNormal3f((float)Math.cos(angle), (float)Math.sin(angle), 0.0F);
/* 176:197 */       GL11.glVertex3f(r2 * (float)Math.cos(angle + 2.0F * da), r2 * (float)Math.sin(angle + 2.0F * da), width * 0.5F);
/* 177:198 */       GL11.glVertex3f(r2 * (float)Math.cos(angle + 2.0F * da), r2 * (float)Math.sin(angle + 2.0F * da), -width * 0.5F);
/* 178:199 */       u = r1 * (float)Math.cos(angle + 3.0F * da) - r2 * (float)Math.cos(angle + 2.0F * da);
/* 179:200 */       v = r1 * (float)Math.sin(angle + 3.0F * da) - r2 * (float)Math.sin(angle + 2.0F * da);
/* 180:201 */       GL11.glNormal3f(v, -u, 0.0F);
/* 181:202 */       GL11.glVertex3f(r1 * (float)Math.cos(angle + 3.0F * da), r1 * (float)Math.sin(angle + 3.0F * da), width * 0.5F);
/* 182:203 */       GL11.glVertex3f(r1 * (float)Math.cos(angle + 3.0F * da), r1 * (float)Math.sin(angle + 3.0F * da), -width * 0.5F);
/* 183:204 */       GL11.glNormal3f((float)Math.cos(angle), (float)Math.sin(angle), 0.0F);
/* 184:    */     }
/* 185:206 */     GL11.glVertex3f(r1 * (float)Math.cos(0.0D), r1 * (float)Math.sin(0.0D), width * 0.5F);
/* 186:207 */     GL11.glVertex3f(r1 * (float)Math.cos(0.0D), r1 * (float)Math.sin(0.0D), -width * 0.5F);
/* 187:208 */     GL11.glEnd();
/* 188:    */     
/* 189:210 */     GL11.glShadeModel(7425);
/* 190:    */     
/* 191:    */ 
/* 192:213 */     GL11.glBegin(8);
/* 193:214 */     for (i = 0; i <= teeth; i++)
/* 194:    */     {
/* 195:215 */       float angle = i * 2.0F * 3.141593F / teeth;
/* 196:216 */       GL11.glNormal3f(-(float)Math.cos(angle), -(float)Math.sin(angle), 0.0F);
/* 197:217 */       GL11.glVertex3f(r0 * (float)Math.cos(angle), r0 * (float)Math.sin(angle), -width * 0.5F);
/* 198:218 */       GL11.glVertex3f(r0 * (float)Math.cos(angle), r0 * (float)Math.sin(angle), width * 0.5F);
/* 199:    */     }
/* 200:220 */     GL11.glEnd();
/* 201:    */   }
/* 202:    */   
/* 203:    */   public void update(GameContainer container, int delta)
/* 204:    */   {
/* 205:227 */     this.rot += delta * 0.1F;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public static void main(String[] argv)
/* 209:    */   {
/* 210:    */     try
/* 211:    */     {
/* 212:237 */       AppGameContainer container = new AppGameContainer(new SlickCallableTest());
/* 213:238 */       container.setDisplayMode(800, 600, false);
/* 214:239 */       container.start();
/* 215:    */     }
/* 216:    */     catch (SlickException e)
/* 217:    */     {
/* 218:241 */       e.printStackTrace();
/* 219:    */     }
/* 220:    */   }
/* 221:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.tests.SlickCallableTest
 * JD-Core Version:    0.7.0.1
 */