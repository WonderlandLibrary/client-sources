/*   1:    */ package org.newdawn.slick.tests;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import org.newdawn.slick.AppGameContainer;
/*   5:    */ import org.newdawn.slick.BasicGame;
/*   6:    */ import org.newdawn.slick.Color;
/*   7:    */ import org.newdawn.slick.GameContainer;
/*   8:    */ import org.newdawn.slick.Graphics;
/*   9:    */ import org.newdawn.slick.SlickException;
/*  10:    */ import org.newdawn.slick.geom.Circle;
/*  11:    */ import org.newdawn.slick.geom.Ellipse;
/*  12:    */ import org.newdawn.slick.geom.Polygon;
/*  13:    */ import org.newdawn.slick.geom.Rectangle;
/*  14:    */ import org.newdawn.slick.geom.RoundedRectangle;
/*  15:    */ import org.newdawn.slick.geom.Shape;
/*  16:    */ import org.newdawn.slick.opengl.renderer.Renderer;
/*  17:    */ 
/*  18:    */ public class ShapeTest
/*  19:    */   extends BasicGame
/*  20:    */ {
/*  21:    */   private Rectangle rect;
/*  22:    */   private RoundedRectangle roundRect;
/*  23:    */   private Ellipse ellipse;
/*  24:    */   private Circle circle;
/*  25:    */   private Polygon polygon;
/*  26:    */   private ArrayList shapes;
/*  27:    */   private boolean[] keys;
/*  28:    */   private char[] lastChar;
/*  29: 43 */   private Polygon randomShape = new Polygon();
/*  30:    */   
/*  31:    */   public ShapeTest()
/*  32:    */   {
/*  33: 49 */     super("Geom Test");
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void createPoly(float x, float y)
/*  37:    */   {
/*  38: 53 */     int size = 20;
/*  39: 54 */     int change = 10;
/*  40:    */     
/*  41: 56 */     this.randomShape = new Polygon();
/*  42:    */     
/*  43: 58 */     this.randomShape.addPoint(0 + (int)(Math.random() * change), 0 + (int)(Math.random() * change));
/*  44: 59 */     this.randomShape.addPoint(size - (int)(Math.random() * change), 0 + (int)(Math.random() * change));
/*  45: 60 */     this.randomShape.addPoint(size - (int)(Math.random() * change), size - (int)(Math.random() * change));
/*  46: 61 */     this.randomShape.addPoint(0 + (int)(Math.random() * change), size - (int)(Math.random() * change));
/*  47:    */     
/*  48:    */ 
/*  49: 64 */     this.randomShape.setCenterX(x);
/*  50: 65 */     this.randomShape.setCenterY(y);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void init(GameContainer container)
/*  54:    */     throws SlickException
/*  55:    */   {
/*  56: 72 */     this.shapes = new ArrayList();
/*  57: 73 */     this.rect = new Rectangle(10.0F, 10.0F, 100.0F, 80.0F);
/*  58: 74 */     this.shapes.add(this.rect);
/*  59: 75 */     this.roundRect = new RoundedRectangle(150.0F, 10.0F, 60.0F, 80.0F, 20.0F);
/*  60: 76 */     this.shapes.add(this.roundRect);
/*  61: 77 */     this.ellipse = new Ellipse(350.0F, 40.0F, 50.0F, 30.0F);
/*  62: 78 */     this.shapes.add(this.ellipse);
/*  63: 79 */     this.circle = new Circle(470.0F, 60.0F, 50.0F);
/*  64: 80 */     this.shapes.add(this.circle);
/*  65: 81 */     this.polygon = new Polygon(new float[] { 550.0F, 10.0F, 600.0F, 40.0F, 620.0F, 100.0F, 570.0F, 130.0F });
/*  66: 82 */     this.shapes.add(this.polygon);
/*  67:    */     
/*  68: 84 */     this.keys = new boolean[256];
/*  69: 85 */     this.lastChar = new char[256];
/*  70: 86 */     createPoly(200.0F, 200.0F);
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void render(GameContainer container, Graphics g)
/*  74:    */   {
/*  75: 93 */     g.setColor(Color.green);
/*  76: 95 */     for (int i = 0; i < this.shapes.size(); i++) {
/*  77: 96 */       g.fill((Shape)this.shapes.get(i));
/*  78:    */     }
/*  79: 98 */     g.fill(this.randomShape);
/*  80: 99 */     g.setColor(Color.black);
/*  81:100 */     g.setAntiAlias(true);
/*  82:101 */     g.draw(this.randomShape);
/*  83:102 */     g.setAntiAlias(false);
/*  84:    */     
/*  85:104 */     g.setColor(Color.white);
/*  86:105 */     g.drawString("keys", 10.0F, 300.0F);
/*  87:106 */     g.drawString("wasd - move rectangle", 10.0F, 315.0F);
/*  88:107 */     g.drawString("WASD - resize rectangle", 10.0F, 330.0F);
/*  89:108 */     g.drawString("tgfh - move rounded rectangle", 10.0F, 345.0F);
/*  90:109 */     g.drawString("TGFH - resize rounded rectangle", 10.0F, 360.0F);
/*  91:110 */     g.drawString("ry - resize corner radius on rounded rectangle", 10.0F, 375.0F);
/*  92:111 */     g.drawString("ikjl - move ellipse", 10.0F, 390.0F);
/*  93:112 */     g.drawString("IKJL - resize ellipse", 10.0F, 405.0F);
/*  94:113 */     g.drawString("Arrows - move circle", 10.0F, 420.0F);
/*  95:114 */     g.drawString("Page Up/Page Down - resize circle", 10.0F, 435.0F);
/*  96:115 */     g.drawString("numpad 8546 - move polygon", 10.0F, 450.0F);
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void update(GameContainer container, int delta)
/* 100:    */   {
/* 101:123 */     createPoly(200.0F, 200.0F);
/* 102:124 */     if (this.keys[1] != 0) {
/* 103:125 */       System.exit(0);
/* 104:    */     }
/* 105:127 */     if (this.keys[17] != 0) {
/* 106:128 */       if (this.lastChar[17] == 'w') {
/* 107:129 */         this.rect.setY(this.rect.getY() - 1.0F);
/* 108:    */       } else {
/* 109:132 */         this.rect.setHeight(this.rect.getHeight() - 1.0F);
/* 110:    */       }
/* 111:    */     }
/* 112:135 */     if (this.keys[31] != 0) {
/* 113:136 */       if (this.lastChar[31] == 's') {
/* 114:137 */         this.rect.setY(this.rect.getY() + 1.0F);
/* 115:    */       } else {
/* 116:140 */         this.rect.setHeight(this.rect.getHeight() + 1.0F);
/* 117:    */       }
/* 118:    */     }
/* 119:143 */     if (this.keys[30] != 0) {
/* 120:144 */       if (this.lastChar[30] == 'a') {
/* 121:145 */         this.rect.setX(this.rect.getX() - 1.0F);
/* 122:    */       } else {
/* 123:148 */         this.rect.setWidth(this.rect.getWidth() - 1.0F);
/* 124:    */       }
/* 125:    */     }
/* 126:151 */     if (this.keys[32] != 0) {
/* 127:152 */       if (this.lastChar[32] == 'd') {
/* 128:153 */         this.rect.setX(this.rect.getX() + 1.0F);
/* 129:    */       } else {
/* 130:156 */         this.rect.setWidth(this.rect.getWidth() + 1.0F);
/* 131:    */       }
/* 132:    */     }
/* 133:159 */     if (this.keys[20] != 0) {
/* 134:160 */       if (this.lastChar[20] == 't') {
/* 135:161 */         this.roundRect.setY(this.roundRect.getY() - 1.0F);
/* 136:    */       } else {
/* 137:164 */         this.roundRect.setHeight(this.roundRect.getHeight() - 1.0F);
/* 138:    */       }
/* 139:    */     }
/* 140:167 */     if (this.keys[34] != 0) {
/* 141:168 */       if (this.lastChar[34] == 'g') {
/* 142:169 */         this.roundRect.setY(this.roundRect.getY() + 1.0F);
/* 143:    */       } else {
/* 144:172 */         this.roundRect.setHeight(this.roundRect.getHeight() + 1.0F);
/* 145:    */       }
/* 146:    */     }
/* 147:175 */     if (this.keys[33] != 0) {
/* 148:176 */       if (this.lastChar[33] == 'f') {
/* 149:177 */         this.roundRect.setX(this.roundRect.getX() - 1.0F);
/* 150:    */       } else {
/* 151:180 */         this.roundRect.setWidth(this.roundRect.getWidth() - 1.0F);
/* 152:    */       }
/* 153:    */     }
/* 154:183 */     if (this.keys[35] != 0) {
/* 155:184 */       if (this.lastChar[35] == 'h') {
/* 156:185 */         this.roundRect.setX(this.roundRect.getX() + 1.0F);
/* 157:    */       } else {
/* 158:188 */         this.roundRect.setWidth(this.roundRect.getWidth() + 1.0F);
/* 159:    */       }
/* 160:    */     }
/* 161:191 */     if (this.keys[19] != 0) {
/* 162:192 */       this.roundRect.setCornerRadius(this.roundRect.getCornerRadius() - 1.0F);
/* 163:    */     }
/* 164:194 */     if (this.keys[21] != 0) {
/* 165:195 */       this.roundRect.setCornerRadius(this.roundRect.getCornerRadius() + 1.0F);
/* 166:    */     }
/* 167:197 */     if (this.keys[23] != 0) {
/* 168:198 */       if (this.lastChar[23] == 'i') {
/* 169:199 */         this.ellipse.setCenterY(this.ellipse.getCenterY() - 1.0F);
/* 170:    */       } else {
/* 171:202 */         this.ellipse.setRadius2(this.ellipse.getRadius2() - 1.0F);
/* 172:    */       }
/* 173:    */     }
/* 174:205 */     if (this.keys[37] != 0) {
/* 175:206 */       if (this.lastChar[37] == 'k') {
/* 176:207 */         this.ellipse.setCenterY(this.ellipse.getCenterY() + 1.0F);
/* 177:    */       } else {
/* 178:210 */         this.ellipse.setRadius2(this.ellipse.getRadius2() + 1.0F);
/* 179:    */       }
/* 180:    */     }
/* 181:213 */     if (this.keys[36] != 0) {
/* 182:214 */       if (this.lastChar[36] == 'j') {
/* 183:215 */         this.ellipse.setCenterX(this.ellipse.getCenterX() - 1.0F);
/* 184:    */       } else {
/* 185:218 */         this.ellipse.setRadius1(this.ellipse.getRadius1() - 1.0F);
/* 186:    */       }
/* 187:    */     }
/* 188:221 */     if (this.keys[38] != 0) {
/* 189:222 */       if (this.lastChar[38] == 'l') {
/* 190:223 */         this.ellipse.setCenterX(this.ellipse.getCenterX() + 1.0F);
/* 191:    */       } else {
/* 192:226 */         this.ellipse.setRadius1(this.ellipse.getRadius1() + 1.0F);
/* 193:    */       }
/* 194:    */     }
/* 195:229 */     if (this.keys['È'] != 0) {
/* 196:230 */       this.circle.setCenterY(this.circle.getCenterY() - 1.0F);
/* 197:    */     }
/* 198:232 */     if (this.keys['Ð'] != 0) {
/* 199:233 */       this.circle.setCenterY(this.circle.getCenterY() + 1.0F);
/* 200:    */     }
/* 201:235 */     if (this.keys['Ë'] != 0) {
/* 202:236 */       this.circle.setCenterX(this.circle.getCenterX() - 1.0F);
/* 203:    */     }
/* 204:238 */     if (this.keys['Í'] != 0) {
/* 205:239 */       this.circle.setCenterX(this.circle.getCenterX() + 1.0F);
/* 206:    */     }
/* 207:241 */     if (this.keys['É'] != 0) {
/* 208:242 */       this.circle.setRadius(this.circle.getRadius() - 1.0F);
/* 209:    */     }
/* 210:244 */     if (this.keys['Ñ'] != 0) {
/* 211:245 */       this.circle.setRadius(this.circle.getRadius() + 1.0F);
/* 212:    */     }
/* 213:247 */     if (this.keys[72] != 0) {
/* 214:248 */       this.polygon.setY(this.polygon.getY() - 1.0F);
/* 215:    */     }
/* 216:250 */     if (this.keys[76] != 0) {
/* 217:251 */       this.polygon.setY(this.polygon.getY() + 1.0F);
/* 218:    */     }
/* 219:253 */     if (this.keys[75] != 0) {
/* 220:254 */       this.polygon.setX(this.polygon.getX() - 1.0F);
/* 221:    */     }
/* 222:256 */     if (this.keys[77] != 0) {
/* 223:257 */       this.polygon.setX(this.polygon.getX() + 1.0F);
/* 224:    */     }
/* 225:    */   }
/* 226:    */   
/* 227:    */   public void keyPressed(int key, char c)
/* 228:    */   {
/* 229:265 */     this.keys[key] = true;
/* 230:266 */     this.lastChar[key] = c;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public void keyReleased(int key, char c)
/* 234:    */   {
/* 235:273 */     this.keys[key] = false;
/* 236:    */   }
/* 237:    */   
/* 238:    */   public static void main(String[] argv)
/* 239:    */   {
/* 240:    */     try
/* 241:    */     {
/* 242:283 */       Renderer.setRenderer(2);
/* 243:284 */       AppGameContainer container = new AppGameContainer(new ShapeTest());
/* 244:285 */       container.setDisplayMode(800, 600, false);
/* 245:286 */       container.start();
/* 246:    */     }
/* 247:    */     catch (SlickException e)
/* 248:    */     {
/* 249:288 */       e.printStackTrace();
/* 250:    */     }
/* 251:    */   }
/* 252:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.tests.ShapeTest
 * JD-Core Version:    0.7.0.1
 */