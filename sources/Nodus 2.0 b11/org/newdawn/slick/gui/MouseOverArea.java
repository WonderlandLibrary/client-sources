/*   1:    */ package org.newdawn.slick.gui;
/*   2:    */ 
/*   3:    */ import org.newdawn.slick.Color;
/*   4:    */ import org.newdawn.slick.Graphics;
/*   5:    */ import org.newdawn.slick.Image;
/*   6:    */ import org.newdawn.slick.Input;
/*   7:    */ import org.newdawn.slick.Sound;
/*   8:    */ import org.newdawn.slick.geom.Rectangle;
/*   9:    */ import org.newdawn.slick.geom.Shape;
/*  10:    */ 
/*  11:    */ public class MouseOverArea
/*  12:    */   extends AbstractComponent
/*  13:    */ {
/*  14:    */   private static final int NORMAL = 1;
/*  15:    */   private static final int MOUSE_DOWN = 2;
/*  16:    */   private static final int MOUSE_OVER = 3;
/*  17:    */   private Image normalImage;
/*  18:    */   private Image mouseOverImage;
/*  19:    */   private Image mouseDownImage;
/*  20: 36 */   private Color normalColor = Color.white;
/*  21: 39 */   private Color mouseOverColor = Color.white;
/*  22: 42 */   private Color mouseDownColor = Color.white;
/*  23:    */   private Sound mouseOverSound;
/*  24:    */   private Sound mouseDownSound;
/*  25:    */   private Shape area;
/*  26:    */   private Image currentImage;
/*  27:    */   private Color currentColor;
/*  28:    */   private boolean over;
/*  29:    */   private boolean mouseDown;
/*  30: 66 */   private int state = 1;
/*  31:    */   private boolean mouseUp;
/*  32:    */   
/*  33:    */   public MouseOverArea(GUIContext container, Image image, int x, int y, ComponentListener listener)
/*  34:    */   {
/*  35: 86 */     this(container, image, x, y, image.getWidth(), image.getHeight());
/*  36: 87 */     addListener(listener);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public MouseOverArea(GUIContext container, Image image, int x, int y)
/*  40:    */   {
/*  41:103 */     this(container, image, x, y, image.getWidth(), image.getHeight());
/*  42:    */   }
/*  43:    */   
/*  44:    */   public MouseOverArea(GUIContext container, Image image, int x, int y, int width, int height, ComponentListener listener)
/*  45:    */   {
/*  46:126 */     this(container, image, x, y, width, height);
/*  47:127 */     addListener(listener);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public MouseOverArea(GUIContext container, Image image, int x, int y, int width, int height)
/*  51:    */   {
/*  52:148 */     this(container, image, new Rectangle(x, y, width, height));
/*  53:    */   }
/*  54:    */   
/*  55:    */   public MouseOverArea(GUIContext container, Image image, Shape shape)
/*  56:    */   {
/*  57:162 */     super(container);
/*  58:    */     
/*  59:164 */     this.area = shape;
/*  60:165 */     this.normalImage = image;
/*  61:166 */     this.currentImage = image;
/*  62:167 */     this.mouseOverImage = image;
/*  63:168 */     this.mouseDownImage = image;
/*  64:    */     
/*  65:170 */     this.currentColor = this.normalColor;
/*  66:    */     
/*  67:172 */     this.state = 1;
/*  68:173 */     Input input = container.getInput();
/*  69:174 */     this.over = this.area.contains(input.getMouseX(), input.getMouseY());
/*  70:175 */     this.mouseDown = input.isMouseButtonDown(0);
/*  71:176 */     updateImage();
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void setLocation(float x, float y)
/*  75:    */   {
/*  76:186 */     if (this.area != null)
/*  77:    */     {
/*  78:187 */       this.area.setX(x);
/*  79:188 */       this.area.setY(y);
/*  80:    */     }
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void setX(float x)
/*  84:    */   {
/*  85:198 */     this.area.setX(x);
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void setY(float y)
/*  89:    */   {
/*  90:207 */     this.area.setY(y);
/*  91:    */   }
/*  92:    */   
/*  93:    */   public int getX()
/*  94:    */   {
/*  95:216 */     return (int)this.area.getX();
/*  96:    */   }
/*  97:    */   
/*  98:    */   public int getY()
/*  99:    */   {
/* 100:225 */     return (int)this.area.getY();
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void setNormalColor(Color color)
/* 104:    */   {
/* 105:235 */     this.normalColor = color;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void setMouseOverColor(Color color)
/* 109:    */   {
/* 110:245 */     this.mouseOverColor = color;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void setMouseDownColor(Color color)
/* 114:    */   {
/* 115:255 */     this.mouseDownColor = color;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void setNormalImage(Image image)
/* 119:    */   {
/* 120:265 */     this.normalImage = image;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void setMouseOverImage(Image image)
/* 124:    */   {
/* 125:275 */     this.mouseOverImage = image;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void setMouseDownImage(Image image)
/* 129:    */   {
/* 130:285 */     this.mouseDownImage = image;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void render(GUIContext container, Graphics g)
/* 134:    */   {
/* 135:293 */     if (this.currentImage != null)
/* 136:    */     {
/* 137:295 */       int xp = (int)(this.area.getX() + (getWidth() - this.currentImage.getWidth()) / 2);
/* 138:296 */       int yp = (int)(this.area.getY() + (getHeight() - this.currentImage.getHeight()) / 2);
/* 139:    */       
/* 140:298 */       this.currentImage.draw(xp, yp, this.currentColor);
/* 141:    */     }
/* 142:    */     else
/* 143:    */     {
/* 144:300 */       g.setColor(this.currentColor);
/* 145:301 */       g.fill(this.area);
/* 146:    */     }
/* 147:303 */     updateImage();
/* 148:    */   }
/* 149:    */   
/* 150:    */   private void updateImage()
/* 151:    */   {
/* 152:310 */     if (!this.over)
/* 153:    */     {
/* 154:311 */       this.currentImage = this.normalImage;
/* 155:312 */       this.currentColor = this.normalColor;
/* 156:313 */       this.state = 1;
/* 157:314 */       this.mouseUp = false;
/* 158:    */     }
/* 159:    */     else
/* 160:    */     {
/* 161:316 */       if (this.mouseDown)
/* 162:    */       {
/* 163:317 */         if ((this.state != 2) && (this.mouseUp))
/* 164:    */         {
/* 165:318 */           if (this.mouseDownSound != null) {
/* 166:319 */             this.mouseDownSound.play();
/* 167:    */           }
/* 168:321 */           this.currentImage = this.mouseDownImage;
/* 169:322 */           this.currentColor = this.mouseDownColor;
/* 170:323 */           this.state = 2;
/* 171:    */           
/* 172:325 */           notifyListeners();
/* 173:326 */           this.mouseUp = false;
/* 174:    */         }
/* 175:329 */         return;
/* 176:    */       }
/* 177:331 */       this.mouseUp = true;
/* 178:332 */       if (this.state != 3)
/* 179:    */       {
/* 180:333 */         if (this.mouseOverSound != null) {
/* 181:334 */           this.mouseOverSound.play();
/* 182:    */         }
/* 183:336 */         this.currentImage = this.mouseOverImage;
/* 184:337 */         this.currentColor = this.mouseOverColor;
/* 185:338 */         this.state = 3;
/* 186:    */       }
/* 187:    */     }
/* 188:343 */     this.mouseDown = false;
/* 189:344 */     this.state = 1;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public void setMouseOverSound(Sound sound)
/* 193:    */   {
/* 194:354 */     this.mouseOverSound = sound;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public void setMouseDownSound(Sound sound)
/* 198:    */   {
/* 199:364 */     this.mouseDownSound = sound;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public void mouseMoved(int oldx, int oldy, int newx, int newy)
/* 203:    */   {
/* 204:371 */     this.over = this.area.contains(newx, newy);
/* 205:    */   }
/* 206:    */   
/* 207:    */   public void mouseDragged(int oldx, int oldy, int newx, int newy)
/* 208:    */   {
/* 209:378 */     mouseMoved(oldx, oldy, newx, newy);
/* 210:    */   }
/* 211:    */   
/* 212:    */   public void mousePressed(int button, int mx, int my)
/* 213:    */   {
/* 214:385 */     this.over = this.area.contains(mx, my);
/* 215:386 */     if (button == 0) {
/* 216:387 */       this.mouseDown = true;
/* 217:    */     }
/* 218:    */   }
/* 219:    */   
/* 220:    */   public void mouseReleased(int button, int mx, int my)
/* 221:    */   {
/* 222:395 */     this.over = this.area.contains(mx, my);
/* 223:396 */     if (button == 0) {
/* 224:397 */       this.mouseDown = false;
/* 225:    */     }
/* 226:    */   }
/* 227:    */   
/* 228:    */   public int getHeight()
/* 229:    */   {
/* 230:405 */     return (int)(this.area.getMaxY() - this.area.getY());
/* 231:    */   }
/* 232:    */   
/* 233:    */   public int getWidth()
/* 234:    */   {
/* 235:412 */     return (int)(this.area.getMaxX() - this.area.getX());
/* 236:    */   }
/* 237:    */   
/* 238:    */   public boolean isMouseOver()
/* 239:    */   {
/* 240:421 */     return this.over;
/* 241:    */   }
/* 242:    */   
/* 243:    */   public void setLocation(int x, int y)
/* 244:    */   {
/* 245:431 */     setLocation(x, y);
/* 246:    */   }
/* 247:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.gui.MouseOverArea
 * JD-Core Version:    0.7.0.1
 */