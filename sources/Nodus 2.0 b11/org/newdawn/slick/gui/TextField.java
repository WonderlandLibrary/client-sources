/*   1:    */ package org.newdawn.slick.gui;
/*   2:    */ 
/*   3:    */ import org.lwjgl.Sys;
/*   4:    */ import org.newdawn.slick.Color;
/*   5:    */ import org.newdawn.slick.Font;
/*   6:    */ import org.newdawn.slick.Graphics;
/*   7:    */ import org.newdawn.slick.Input;
/*   8:    */ import org.newdawn.slick.geom.Rectangle;
/*   9:    */ 
/*  10:    */ public class TextField
/*  11:    */   extends AbstractComponent
/*  12:    */ {
/*  13:    */   private static final int INITIAL_KEY_REPEAT_INTERVAL = 400;
/*  14:    */   private static final int KEY_REPEAT_INTERVAL = 50;
/*  15:    */   private int width;
/*  16:    */   private int height;
/*  17:    */   protected int x;
/*  18:    */   protected int y;
/*  19: 34 */   private int maxCharacter = 10000;
/*  20: 37 */   private String value = "";
/*  21:    */   private Font font;
/*  22: 43 */   private Color border = Color.white;
/*  23: 46 */   private Color text = Color.white;
/*  24: 49 */   private Color background = new Color(0.0F, 0.0F, 0.0F, 0.5F);
/*  25:    */   private int cursorPos;
/*  26: 55 */   private boolean visibleCursor = true;
/*  27: 58 */   private int lastKey = -1;
/*  28: 61 */   private char lastChar = '\000';
/*  29:    */   private long repeatTimer;
/*  30:    */   private String oldText;
/*  31:    */   private int oldCursorPos;
/*  32: 73 */   private boolean consume = true;
/*  33:    */   
/*  34:    */   public TextField(GUIContext container, Font font, int x, int y, int width, int height, ComponentListener listener)
/*  35:    */   {
/*  36: 95 */     this(container, font, x, y, width, height);
/*  37: 96 */     addListener(listener);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public TextField(GUIContext container, Font font, int x, int y, int width, int height)
/*  41:    */   {
/*  42:117 */     super(container);
/*  43:    */     
/*  44:119 */     this.font = font;
/*  45:    */     
/*  46:121 */     setLocation(x, y);
/*  47:122 */     this.width = width;
/*  48:123 */     this.height = height;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void setConsumeEvents(boolean consume)
/*  52:    */   {
/*  53:132 */     this.consume = consume;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void deactivate()
/*  57:    */   {
/*  58:139 */     setFocus(false);
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void setLocation(int x, int y)
/*  62:    */   {
/*  63:151 */     this.x = x;
/*  64:152 */     this.y = y;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public int getX()
/*  68:    */   {
/*  69:161 */     return this.x;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public int getY()
/*  73:    */   {
/*  74:170 */     return this.y;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public int getWidth()
/*  78:    */   {
/*  79:179 */     return this.width;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public int getHeight()
/*  83:    */   {
/*  84:188 */     return this.height;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void setBackgroundColor(Color color)
/*  88:    */   {
/*  89:198 */     this.background = color;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void setBorderColor(Color color)
/*  93:    */   {
/*  94:208 */     this.border = color;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void setTextColor(Color color)
/*  98:    */   {
/*  99:218 */     this.text = color;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void render(GUIContext container, Graphics g)
/* 103:    */   {
/* 104:226 */     if (this.lastKey != -1) {
/* 105:227 */       if (this.input.isKeyDown(this.lastKey))
/* 106:    */       {
/* 107:228 */         if (this.repeatTimer < System.currentTimeMillis())
/* 108:    */         {
/* 109:229 */           this.repeatTimer = (System.currentTimeMillis() + 50L);
/* 110:230 */           keyPressed(this.lastKey, this.lastChar);
/* 111:    */         }
/* 112:    */       }
/* 113:    */       else {
/* 114:233 */         this.lastKey = -1;
/* 115:    */       }
/* 116:    */     }
/* 117:236 */     Rectangle oldClip = g.getClip();
/* 118:237 */     g.setWorldClip(this.x, this.y, this.width, this.height);
/* 119:    */     
/* 120:    */ 
/* 121:240 */     Color clr = g.getColor();
/* 122:242 */     if (this.background != null)
/* 123:    */     {
/* 124:243 */       g.setColor(this.background.multiply(clr));
/* 125:244 */       g.fillRect(this.x, this.y, this.width, this.height);
/* 126:    */     }
/* 127:246 */     g.setColor(this.text.multiply(clr));
/* 128:247 */     Font temp = g.getFont();
/* 129:    */     
/* 130:249 */     int cpos = this.font.getWidth(this.value.substring(0, this.cursorPos));
/* 131:250 */     int tx = 0;
/* 132:251 */     if (cpos > this.width) {
/* 133:252 */       tx = this.width - cpos - this.font.getWidth("_");
/* 134:    */     }
/* 135:255 */     g.translate(tx + 2, 0.0F);
/* 136:256 */     g.setFont(this.font);
/* 137:257 */     g.drawString(this.value, this.x + 1, this.y + 1);
/* 138:259 */     if ((hasFocus()) && (this.visibleCursor)) {
/* 139:260 */       g.drawString("_", this.x + 1 + cpos + 2, this.y + 1);
/* 140:    */     }
/* 141:263 */     g.translate(-tx - 2, 0.0F);
/* 142:265 */     if (this.border != null)
/* 143:    */     {
/* 144:266 */       g.setColor(this.border.multiply(clr));
/* 145:267 */       g.drawRect(this.x, this.y, this.width, this.height);
/* 146:    */     }
/* 147:269 */     g.setColor(clr);
/* 148:270 */     g.setFont(temp);
/* 149:271 */     g.clearWorldClip();
/* 150:272 */     g.setClip(oldClip);
/* 151:    */   }
/* 152:    */   
/* 153:    */   public String getText()
/* 154:    */   {
/* 155:281 */     return this.value;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public void setText(String value)
/* 159:    */   {
/* 160:291 */     this.value = value;
/* 161:292 */     if (this.cursorPos > value.length()) {
/* 162:293 */       this.cursorPos = value.length();
/* 163:    */     }
/* 164:    */   }
/* 165:    */   
/* 166:    */   public void setCursorPos(int pos)
/* 167:    */   {
/* 168:304 */     this.cursorPos = pos;
/* 169:305 */     if (this.cursorPos > this.value.length()) {
/* 170:306 */       this.cursorPos = this.value.length();
/* 171:    */     }
/* 172:    */   }
/* 173:    */   
/* 174:    */   public void setCursorVisible(boolean visibleCursor)
/* 175:    */   {
/* 176:317 */     this.visibleCursor = visibleCursor;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public void setMaxLength(int length)
/* 180:    */   {
/* 181:327 */     this.maxCharacter = length;
/* 182:328 */     if (this.value.length() > this.maxCharacter) {
/* 183:329 */       this.value = this.value.substring(0, this.maxCharacter);
/* 184:    */     }
/* 185:    */   }
/* 186:    */   
/* 187:    */   protected void doPaste(String text)
/* 188:    */   {
/* 189:339 */     recordOldPosition();
/* 190:341 */     for (int i = 0; i < text.length(); i++) {
/* 191:342 */       keyPressed(-1, text.charAt(i));
/* 192:    */     }
/* 193:    */   }
/* 194:    */   
/* 195:    */   protected void recordOldPosition()
/* 196:    */   {
/* 197:350 */     this.oldText = getText();
/* 198:351 */     this.oldCursorPos = this.cursorPos;
/* 199:    */   }
/* 200:    */   
/* 201:    */   protected void doUndo(int oldCursorPos, String oldText)
/* 202:    */   {
/* 203:361 */     if (oldText != null)
/* 204:    */     {
/* 205:362 */       setText(oldText);
/* 206:363 */       setCursorPos(oldCursorPos);
/* 207:    */     }
/* 208:    */   }
/* 209:    */   
/* 210:    */   public void keyPressed(int key, char c)
/* 211:    */   {
/* 212:371 */     if (hasFocus())
/* 213:    */     {
/* 214:372 */       if (key != -1)
/* 215:    */       {
/* 216:374 */         if ((key == 47) && (
/* 217:375 */           (this.input.isKeyDown(29)) || (this.input.isKeyDown(157))))
/* 218:    */         {
/* 219:376 */           String text = Sys.getClipboard();
/* 220:377 */           if (text != null) {
/* 221:378 */             doPaste(text);
/* 222:    */           }
/* 223:380 */           return;
/* 224:    */         }
/* 225:382 */         if ((key == 44) && (
/* 226:383 */           (this.input.isKeyDown(29)) || (this.input.isKeyDown(157))))
/* 227:    */         {
/* 228:384 */           if (this.oldText != null) {
/* 229:385 */             doUndo(this.oldCursorPos, this.oldText);
/* 230:    */           }
/* 231:387 */           return;
/* 232:    */         }
/* 233:391 */         if ((this.input.isKeyDown(29)) || (this.input.isKeyDown(157))) {
/* 234:392 */           return;
/* 235:    */         }
/* 236:394 */         if ((this.input.isKeyDown(56)) || (this.input.isKeyDown(184))) {
/* 237:395 */           return;
/* 238:    */         }
/* 239:    */       }
/* 240:399 */       if (this.lastKey != key)
/* 241:    */       {
/* 242:400 */         this.lastKey = key;
/* 243:401 */         this.repeatTimer = (System.currentTimeMillis() + 400L);
/* 244:    */       }
/* 245:    */       else
/* 246:    */       {
/* 247:403 */         this.repeatTimer = (System.currentTimeMillis() + 50L);
/* 248:    */       }
/* 249:405 */       this.lastChar = c;
/* 250:407 */       if (key == 203)
/* 251:    */       {
/* 252:408 */         if (this.cursorPos > 0) {
/* 253:409 */           this.cursorPos -= 1;
/* 254:    */         }
/* 255:412 */         if (this.consume) {
/* 256:413 */           this.container.getInput().consumeEvent();
/* 257:    */         }
/* 258:    */       }
/* 259:415 */       else if (key == 205)
/* 260:    */       {
/* 261:416 */         if (this.cursorPos < this.value.length()) {
/* 262:417 */           this.cursorPos += 1;
/* 263:    */         }
/* 264:420 */         if (this.consume) {
/* 265:421 */           this.container.getInput().consumeEvent();
/* 266:    */         }
/* 267:    */       }
/* 268:423 */       else if (key == 14)
/* 269:    */       {
/* 270:424 */         if ((this.cursorPos > 0) && (this.value.length() > 0))
/* 271:    */         {
/* 272:425 */           if (this.cursorPos < this.value.length()) {
/* 273:426 */             this.value = 
/* 274:427 */               (this.value.substring(0, this.cursorPos - 1) + this.value.substring(this.cursorPos));
/* 275:    */           } else {
/* 276:429 */             this.value = this.value.substring(0, this.cursorPos - 1);
/* 277:    */           }
/* 278:431 */           this.cursorPos -= 1;
/* 279:    */         }
/* 280:434 */         if (this.consume) {
/* 281:435 */           this.container.getInput().consumeEvent();
/* 282:    */         }
/* 283:    */       }
/* 284:437 */       else if (key == 211)
/* 285:    */       {
/* 286:438 */         if (this.value.length() > this.cursorPos) {
/* 287:439 */           this.value = (this.value.substring(0, this.cursorPos) + this.value.substring(this.cursorPos + 1));
/* 288:    */         }
/* 289:442 */         if (this.consume) {
/* 290:443 */           this.container.getInput().consumeEvent();
/* 291:    */         }
/* 292:    */       }
/* 293:445 */       else if ((c < '') && (c > '\037') && (this.value.length() < this.maxCharacter))
/* 294:    */       {
/* 295:446 */         if (this.cursorPos < this.value.length()) {
/* 296:447 */           this.value = 
/* 297:448 */             (this.value.substring(0, this.cursorPos) + c + this.value.substring(this.cursorPos));
/* 298:    */         } else {
/* 299:450 */           this.value = (this.value.substring(0, this.cursorPos) + c);
/* 300:    */         }
/* 301:452 */         this.cursorPos += 1;
/* 302:454 */         if (this.consume) {
/* 303:455 */           this.container.getInput().consumeEvent();
/* 304:    */         }
/* 305:    */       }
/* 306:457 */       else if (key == 28)
/* 307:    */       {
/* 308:458 */         notifyListeners();
/* 309:460 */         if (this.consume) {
/* 310:461 */           this.container.getInput().consumeEvent();
/* 311:    */         }
/* 312:    */       }
/* 313:    */     }
/* 314:    */   }
/* 315:    */   
/* 316:    */   public void setFocus(boolean focus)
/* 317:    */   {
/* 318:472 */     this.lastKey = -1;
/* 319:    */     
/* 320:474 */     super.setFocus(focus);
/* 321:    */   }
/* 322:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.gui.TextField
 * JD-Core Version:    0.7.0.1
 */