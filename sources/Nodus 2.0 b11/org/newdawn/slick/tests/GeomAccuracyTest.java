/*   1:    */ package org.newdawn.slick.tests;
/*   2:    */ 
/*   3:    */ import org.newdawn.slick.AppGameContainer;
/*   4:    */ import org.newdawn.slick.BasicGame;
/*   5:    */ import org.newdawn.slick.Color;
/*   6:    */ import org.newdawn.slick.GameContainer;
/*   7:    */ import org.newdawn.slick.Graphics;
/*   8:    */ import org.newdawn.slick.Image;
/*   9:    */ import org.newdawn.slick.Input;
/*  10:    */ import org.newdawn.slick.SlickException;
/*  11:    */ import org.newdawn.slick.geom.Ellipse;
/*  12:    */ import org.newdawn.slick.geom.Rectangle;
/*  13:    */ import org.newdawn.slick.geom.RoundedRectangle;
/*  14:    */ 
/*  15:    */ public class GeomAccuracyTest
/*  16:    */   extends BasicGame
/*  17:    */ {
/*  18:    */   private GameContainer container;
/*  19:    */   private Color geomColor;
/*  20:    */   private Color overlayColor;
/*  21:    */   private boolean hideOverlay;
/*  22:    */   private int colorIndex;
/*  23:    */   private int curTest;
/*  24:    */   private static final int NUMTESTS = 3;
/*  25:    */   private Image magImage;
/*  26:    */   
/*  27:    */   public GeomAccuracyTest()
/*  28:    */   {
/*  29: 49 */     super("Geometry Accuracy Tests");
/*  30:    */   }
/*  31:    */   
/*  32:    */   public void init(GameContainer container)
/*  33:    */     throws SlickException
/*  34:    */   {
/*  35: 56 */     this.container = container;
/*  36:    */     
/*  37: 58 */     this.geomColor = Color.magenta;
/*  38: 59 */     this.overlayColor = Color.white;
/*  39:    */     
/*  40: 61 */     this.magImage = new Image(21, 21);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void render(GameContainer container, Graphics g)
/*  44:    */   {
/*  45: 69 */     String text = new String();
/*  46: 71 */     switch (this.curTest)
/*  47:    */     {
/*  48:    */     case 0: 
/*  49: 74 */       text = "Rectangles";
/*  50: 75 */       rectTest(g);
/*  51: 76 */       break;
/*  52:    */     case 1: 
/*  53: 79 */       text = "Ovals";
/*  54: 80 */       ovalTest(g);
/*  55: 81 */       break;
/*  56:    */     case 2: 
/*  57: 84 */       text = "Arcs";
/*  58: 85 */       arcTest(g);
/*  59:    */     }
/*  60: 89 */     g.setColor(Color.white);
/*  61: 90 */     g.drawString("Press T to toggle overlay", 200.0F, 55.0F);
/*  62: 91 */     g.drawString("Press N to switch tests", 200.0F, 35.0F);
/*  63: 92 */     g.drawString("Press C to cycle drawing colors", 200.0F, 15.0F);
/*  64: 93 */     g.drawString("Current Test:", 400.0F, 35.0F);
/*  65: 94 */     g.setColor(Color.blue);
/*  66: 95 */     g.drawString(text, 485.0F, 35.0F);
/*  67:    */     
/*  68: 97 */     g.setColor(Color.white);
/*  69: 98 */     g.drawString("Normal:", 10.0F, 150.0F);
/*  70: 99 */     g.drawString("Filled:", 10.0F, 300.0F);
/*  71:    */     
/*  72:101 */     g.drawString("Drawn with Graphics context", 125.0F, 400.0F);
/*  73:102 */     g.drawString("Drawn using Shapes", 450.0F, 400.0F);
/*  74:    */     
/*  75:    */ 
/*  76:105 */     g.copyArea(this.magImage, container.getInput().getMouseX() - 10, container.getInput().getMouseY() - 10);
/*  77:106 */     this.magImage.draw(351.0F, 451.0F, 5.0F);
/*  78:107 */     g.drawString("Mag Area -", 250.0F, 475.0F);
/*  79:108 */     g.setColor(Color.darkGray);
/*  80:109 */     g.drawRect(350.0F, 450.0F, 106.0F, 106.0F);
/*  81:    */     
/*  82:111 */     g.setColor(Color.white);
/*  83:112 */     g.drawString("NOTE:", 500.0F, 450.0F);
/*  84:113 */     g.drawString("lines should be flush with edges", 525.0F, 470.0F);
/*  85:114 */     g.drawString("corners should be symetric", 525.0F, 490.0F);
/*  86:    */   }
/*  87:    */   
/*  88:    */   void arcTest(Graphics g)
/*  89:    */   {
/*  90:124 */     if (!this.hideOverlay)
/*  91:    */     {
/*  92:125 */       g.setColor(this.overlayColor);
/*  93:126 */       g.drawLine(198.0F, 100.0F, 198.0F, 198.0F);
/*  94:127 */       g.drawLine(100.0F, 198.0F, 198.0F, 198.0F);
/*  95:    */     }
/*  96:130 */     g.setColor(this.geomColor);
/*  97:131 */     g.drawArc(100.0F, 100.0F, 99.0F, 99.0F, 0.0F, 90.0F);
/*  98:    */   }
/*  99:    */   
/* 100:    */   void ovalTest(Graphics g)
/* 101:    */   {
/* 102:142 */     g.setColor(this.geomColor);
/* 103:143 */     g.drawOval(100.0F, 100.0F, 99.0F, 99.0F);
/* 104:144 */     g.fillOval(100.0F, 250.0F, 99.0F, 99.0F);
/* 105:    */     
/* 106:    */ 
/* 107:147 */     Ellipse elip = new Ellipse(449.0F, 149.0F, 49.0F, 49.0F);
/* 108:148 */     g.draw(elip);
/* 109:149 */     elip = new Ellipse(449.0F, 299.0F, 49.0F, 49.0F);
/* 110:150 */     g.fill(elip);
/* 111:152 */     if (!this.hideOverlay)
/* 112:    */     {
/* 113:153 */       g.setColor(this.overlayColor);
/* 114:154 */       g.drawLine(100.0F, 149.0F, 198.0F, 149.0F);
/* 115:155 */       g.drawLine(149.0F, 100.0F, 149.0F, 198.0F);
/* 116:    */       
/* 117:157 */       g.drawLine(100.0F, 299.0F, 198.0F, 299.0F);
/* 118:158 */       g.drawLine(149.0F, 250.0F, 149.0F, 348.0F);
/* 119:    */       
/* 120:160 */       g.drawLine(400.0F, 149.0F, 498.0F, 149.0F);
/* 121:161 */       g.drawLine(449.0F, 100.0F, 449.0F, 198.0F);
/* 122:    */       
/* 123:163 */       g.drawLine(400.0F, 299.0F, 498.0F, 299.0F);
/* 124:164 */       g.drawLine(449.0F, 250.0F, 449.0F, 348.0F);
/* 125:    */     }
/* 126:    */   }
/* 127:    */   
/* 128:    */   void rectTest(Graphics g)
/* 129:    */   {
/* 130:176 */     g.setColor(this.geomColor);
/* 131:    */     
/* 132:    */ 
/* 133:179 */     g.drawRect(100.0F, 100.0F, 99.0F, 99.0F);
/* 134:180 */     g.fillRect(100.0F, 250.0F, 99.0F, 99.0F);
/* 135:    */     
/* 136:182 */     g.drawRoundRect(250.0F, 100.0F, 99.0F, 99.0F, 10);
/* 137:183 */     g.fillRoundRect(250.0F, 250.0F, 99.0F, 99.0F, 10);
/* 138:    */     
/* 139:    */ 
/* 140:186 */     Rectangle rect = new Rectangle(400.0F, 100.0F, 99.0F, 99.0F);
/* 141:187 */     g.draw(rect);
/* 142:188 */     rect = new Rectangle(400.0F, 250.0F, 99.0F, 99.0F);
/* 143:189 */     g.fill(rect);
/* 144:    */     
/* 145:191 */     RoundedRectangle rrect = new RoundedRectangle(550.0F, 100.0F, 99.0F, 99.0F, 10.0F);
/* 146:192 */     g.draw(rrect);
/* 147:193 */     rrect = new RoundedRectangle(550.0F, 250.0F, 99.0F, 99.0F, 10.0F);
/* 148:194 */     g.fill(rrect);
/* 149:197 */     if (!this.hideOverlay)
/* 150:    */     {
/* 151:198 */       g.setColor(this.overlayColor);
/* 152:    */       
/* 153:    */ 
/* 154:201 */       g.drawLine(100.0F, 149.0F, 198.0F, 149.0F);
/* 155:202 */       g.drawLine(149.0F, 100.0F, 149.0F, 198.0F);
/* 156:    */       
/* 157:204 */       g.drawLine(250.0F, 149.0F, 348.0F, 149.0F);
/* 158:205 */       g.drawLine(299.0F, 100.0F, 299.0F, 198.0F);
/* 159:    */       
/* 160:207 */       g.drawLine(400.0F, 149.0F, 498.0F, 149.0F);
/* 161:208 */       g.drawLine(449.0F, 100.0F, 449.0F, 198.0F);
/* 162:    */       
/* 163:210 */       g.drawLine(550.0F, 149.0F, 648.0F, 149.0F);
/* 164:211 */       g.drawLine(599.0F, 100.0F, 599.0F, 198.0F);
/* 165:    */       
/* 166:    */ 
/* 167:214 */       g.drawLine(100.0F, 299.0F, 198.0F, 299.0F);
/* 168:215 */       g.drawLine(149.0F, 250.0F, 149.0F, 348.0F);
/* 169:    */       
/* 170:217 */       g.drawLine(250.0F, 299.0F, 348.0F, 299.0F);
/* 171:218 */       g.drawLine(299.0F, 250.0F, 299.0F, 348.0F);
/* 172:    */       
/* 173:220 */       g.drawLine(400.0F, 299.0F, 498.0F, 299.0F);
/* 174:221 */       g.drawLine(449.0F, 250.0F, 449.0F, 348.0F);
/* 175:    */       
/* 176:223 */       g.drawLine(550.0F, 299.0F, 648.0F, 299.0F);
/* 177:224 */       g.drawLine(599.0F, 250.0F, 599.0F, 348.0F);
/* 178:    */     }
/* 179:    */   }
/* 180:    */   
/* 181:    */   public void update(GameContainer container, int delta) {}
/* 182:    */   
/* 183:    */   public void keyPressed(int key, char c)
/* 184:    */   {
/* 185:239 */     if (key == 1) {
/* 186:240 */       System.exit(0);
/* 187:    */     }
/* 188:243 */     if (key == 49)
/* 189:    */     {
/* 190:244 */       this.curTest += 1;
/* 191:245 */       this.curTest %= 3;
/* 192:    */     }
/* 193:248 */     if (key == 46)
/* 194:    */     {
/* 195:249 */       this.colorIndex += 1;
/* 196:    */       
/* 197:251 */       this.colorIndex %= 4;
/* 198:252 */       setColors();
/* 199:    */     }
/* 200:255 */     if (key == 20) {
/* 201:256 */       this.hideOverlay = (!this.hideOverlay);
/* 202:    */     }
/* 203:    */   }
/* 204:    */   
/* 205:    */   private void setColors()
/* 206:    */   {
/* 207:266 */     switch (this.colorIndex)
/* 208:    */     {
/* 209:    */     case 0: 
/* 210:269 */       this.overlayColor = Color.white;
/* 211:270 */       this.geomColor = Color.magenta;
/* 212:271 */       break;
/* 213:    */     case 1: 
/* 214:274 */       this.overlayColor = Color.magenta;
/* 215:275 */       this.geomColor = Color.white;
/* 216:276 */       break;
/* 217:    */     case 2: 
/* 218:279 */       this.overlayColor = Color.red;
/* 219:280 */       this.geomColor = Color.green;
/* 220:281 */       break;
/* 221:    */     case 3: 
/* 222:284 */       this.overlayColor = Color.red;
/* 223:285 */       this.geomColor = Color.white;
/* 224:    */     }
/* 225:    */   }
/* 226:    */   
/* 227:    */   public static void main(String[] argv)
/* 228:    */   {
/* 229:    */     try
/* 230:    */     {
/* 231:298 */       AppGameContainer container = new AppGameContainer(new GeomAccuracyTest());
/* 232:299 */       container.setDisplayMode(800, 600, false);
/* 233:300 */       container.start();
/* 234:    */     }
/* 235:    */     catch (SlickException e)
/* 236:    */     {
/* 237:302 */       e.printStackTrace();
/* 238:    */     }
/* 239:    */   }
/* 240:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.tests.GeomAccuracyTest
 * JD-Core Version:    0.7.0.1
 */