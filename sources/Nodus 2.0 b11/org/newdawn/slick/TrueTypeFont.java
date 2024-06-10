/*   1:    */ package org.newdawn.slick;
/*   2:    */ 
/*   3:    */ import java.awt.FontMetrics;
/*   4:    */ import java.awt.Graphics2D;
/*   5:    */ import java.awt.RenderingHints;
/*   6:    */ import java.awt.image.BufferedImage;
/*   7:    */ import java.io.IOException;
/*   8:    */ import java.io.PrintStream;
/*   9:    */ import java.util.HashMap;
/*  10:    */ import java.util.Map;
/*  11:    */ import org.newdawn.slick.opengl.GLUtils;
/*  12:    */ import org.newdawn.slick.opengl.Texture;
/*  13:    */ import org.newdawn.slick.opengl.renderer.Renderer;
/*  14:    */ import org.newdawn.slick.opengl.renderer.SGL;
/*  15:    */ import org.newdawn.slick.util.BufferedImageUtil;
/*  16:    */ 
/*  17:    */ public class TrueTypeFont
/*  18:    */   implements Font
/*  19:    */ {
/*  20: 28 */   private static final SGL GL = ;
/*  21: 31 */   private IntObject[] charArray = new IntObject[256];
/*  22: 34 */   private Map customChars = new HashMap();
/*  23:    */   private boolean antiAlias;
/*  24: 40 */   private int fontSize = 0;
/*  25: 43 */   private int fontHeight = 0;
/*  26:    */   private Texture fontTexture;
/*  27: 49 */   private int textureWidth = 512;
/*  28: 52 */   private int textureHeight = 512;
/*  29:    */   private java.awt.Font font;
/*  30:    */   private FontMetrics fontMetrics;
/*  31:    */   
/*  32:    */   public TrueTypeFont(java.awt.Font font, boolean antiAlias, char[] additionalChars)
/*  33:    */   {
/*  34: 92 */     GLUtils.checkGLContext();
/*  35:    */     
/*  36: 94 */     this.font = font;
/*  37: 95 */     this.fontSize = font.getSize();
/*  38: 96 */     this.antiAlias = antiAlias;
/*  39:    */     
/*  40: 98 */     createSet(additionalChars);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public TrueTypeFont(java.awt.Font font, boolean antiAlias)
/*  44:    */   {
/*  45:112 */     this(font, antiAlias, null);
/*  46:    */   }
/*  47:    */   
/*  48:    */   private BufferedImage getFontImage(char ch)
/*  49:    */   {
/*  50:125 */     BufferedImage tempfontImage = new BufferedImage(1, 1, 
/*  51:126 */       2);
/*  52:127 */     Graphics2D g = (Graphics2D)tempfontImage.getGraphics();
/*  53:128 */     if (this.antiAlias) {
/*  54:129 */       g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
/*  55:130 */         RenderingHints.VALUE_ANTIALIAS_ON);
/*  56:    */     }
/*  57:132 */     g.setFont(this.font);
/*  58:133 */     this.fontMetrics = g.getFontMetrics();
/*  59:134 */     int charwidth = this.fontMetrics.charWidth(ch);
/*  60:136 */     if (charwidth <= 0) {
/*  61:137 */       charwidth = 1;
/*  62:    */     }
/*  63:139 */     int charheight = this.fontMetrics.getHeight();
/*  64:140 */     if (charheight <= 0) {
/*  65:141 */       charheight = this.fontSize;
/*  66:    */     }
/*  67:146 */     BufferedImage fontImage = new BufferedImage(charwidth, charheight, 
/*  68:147 */       2);
/*  69:148 */     Graphics2D gt = (Graphics2D)fontImage.getGraphics();
/*  70:149 */     if (this.antiAlias) {
/*  71:150 */       gt.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
/*  72:151 */         RenderingHints.VALUE_ANTIALIAS_ON);
/*  73:    */     }
/*  74:153 */     gt.setFont(this.font);
/*  75:    */     
/*  76:155 */     gt.setColor(java.awt.Color.WHITE);
/*  77:156 */     int charx = 0;
/*  78:157 */     int chary = 0;
/*  79:158 */     gt.drawString(String.valueOf(ch), charx, chary + 
/*  80:159 */       this.fontMetrics.getAscent());
/*  81:    */     
/*  82:161 */     return fontImage;
/*  83:    */   }
/*  84:    */   
/*  85:    */   private void createSet(char[] customCharsArray)
/*  86:    */   {
/*  87:172 */     if ((customCharsArray != null) && (customCharsArray.length > 0)) {
/*  88:173 */       this.textureWidth *= 2;
/*  89:    */     }
/*  90:    */     try
/*  91:    */     {
/*  92:182 */       BufferedImage imgTemp = new BufferedImage(this.textureWidth, this.textureHeight, 2);
/*  93:183 */       Graphics2D g = (Graphics2D)imgTemp.getGraphics();
/*  94:    */       
/*  95:185 */       g.setColor(new java.awt.Color(255, 255, 255, 1));
/*  96:186 */       g.fillRect(0, 0, this.textureWidth, this.textureHeight);
/*  97:    */       
/*  98:188 */       int rowHeight = 0;
/*  99:189 */       int positionX = 0;
/* 100:190 */       int positionY = 0;
/* 101:    */       
/* 102:192 */       int customCharsLength = customCharsArray != null ? customCharsArray.length : 0;
/* 103:194 */       for (int i = 0; i < 256 + customCharsLength; i++)
/* 104:    */       {
/* 105:197 */         char ch = i < 256 ? (char)i : customCharsArray[(i - 256)];
/* 106:    */         
/* 107:199 */         BufferedImage fontImage = getFontImage(ch);
/* 108:    */         
/* 109:201 */         IntObject newIntObject = new IntObject(null);
/* 110:    */         
/* 111:203 */         newIntObject.width = fontImage.getWidth();
/* 112:204 */         newIntObject.height = fontImage.getHeight();
/* 113:206 */         if (positionX + newIntObject.width >= this.textureWidth)
/* 114:    */         {
/* 115:207 */           positionX = 0;
/* 116:208 */           positionY += rowHeight;
/* 117:209 */           rowHeight = 0;
/* 118:    */         }
/* 119:212 */         newIntObject.storedX = positionX;
/* 120:213 */         newIntObject.storedY = positionY;
/* 121:215 */         if (newIntObject.height > this.fontHeight) {
/* 122:216 */           this.fontHeight = newIntObject.height;
/* 123:    */         }
/* 124:219 */         if (newIntObject.height > rowHeight) {
/* 125:220 */           rowHeight = newIntObject.height;
/* 126:    */         }
/* 127:224 */         g.drawImage(fontImage, positionX, positionY, null);
/* 128:    */         
/* 129:226 */         positionX += newIntObject.width;
/* 130:228 */         if (i < 256) {
/* 131:229 */           this.charArray[i] = newIntObject;
/* 132:    */         } else {
/* 133:231 */           this.customChars.put(new Character(ch), newIntObject);
/* 134:    */         }
/* 135:234 */         fontImage = null;
/* 136:    */       }
/* 137:237 */       this.fontTexture = 
/* 138:238 */         BufferedImageUtil.getTexture(this.font.toString(), imgTemp);
/* 139:    */     }
/* 140:    */     catch (IOException e)
/* 141:    */     {
/* 142:241 */       System.err.println("Failed to create font.");
/* 143:242 */       e.printStackTrace();
/* 144:    */     }
/* 145:    */   }
/* 146:    */   
/* 147:    */   private void drawQuad(float drawX, float drawY, float drawX2, float drawY2, float srcX, float srcY, float srcX2, float srcY2)
/* 148:    */   {
/* 149:269 */     float DrawWidth = drawX2 - drawX;
/* 150:270 */     float DrawHeight = drawY2 - drawY;
/* 151:271 */     float TextureSrcX = srcX / this.textureWidth;
/* 152:272 */     float TextureSrcY = srcY / this.textureHeight;
/* 153:273 */     float SrcWidth = srcX2 - srcX;
/* 154:274 */     float SrcHeight = srcY2 - srcY;
/* 155:275 */     float RenderWidth = SrcWidth / this.textureWidth;
/* 156:276 */     float RenderHeight = SrcHeight / this.textureHeight;
/* 157:    */     
/* 158:278 */     GL.glTexCoord2f(TextureSrcX, TextureSrcY);
/* 159:279 */     GL.glVertex2f(drawX, drawY);
/* 160:280 */     GL.glTexCoord2f(TextureSrcX, TextureSrcY + RenderHeight);
/* 161:281 */     GL.glVertex2f(drawX, drawY + DrawHeight);
/* 162:282 */     GL.glTexCoord2f(TextureSrcX + RenderWidth, TextureSrcY + RenderHeight);
/* 163:283 */     GL.glVertex2f(drawX + DrawWidth, drawY + DrawHeight);
/* 164:284 */     GL.glTexCoord2f(TextureSrcX + RenderWidth, TextureSrcY);
/* 165:285 */     GL.glVertex2f(drawX + DrawWidth, drawY);
/* 166:    */   }
/* 167:    */   
/* 168:    */   public int getWidth(String whatchars)
/* 169:    */   {
/* 170:297 */     int totalwidth = 0;
/* 171:298 */     IntObject intObject = null;
/* 172:299 */     int currentChar = 0;
/* 173:300 */     for (int i = 0; i < whatchars.length(); i++)
/* 174:    */     {
/* 175:301 */       currentChar = whatchars.charAt(i);
/* 176:302 */       if (currentChar < 256) {
/* 177:303 */         intObject = this.charArray[currentChar];
/* 178:    */       } else {
/* 179:305 */         intObject = (IntObject)this.customChars.get(new Character((char)currentChar));
/* 180:    */       }
/* 181:308 */       if (intObject != null) {
/* 182:309 */         totalwidth += intObject.width;
/* 183:    */       }
/* 184:    */     }
/* 185:311 */     return totalwidth;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public int getHeight()
/* 189:    */   {
/* 190:320 */     return this.fontHeight;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public int getHeight(String HeightString)
/* 194:    */   {
/* 195:329 */     return this.fontHeight;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public int getLineHeight()
/* 199:    */   {
/* 200:338 */     return this.fontHeight;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public void drawString(float x, float y, String whatchars, Color color)
/* 204:    */   {
/* 205:355 */     drawString(x, y, whatchars, color, 0, whatchars.length() - 1);
/* 206:    */   }
/* 207:    */   
/* 208:    */   public void drawString(float x, float y, String whatchars, Color color, int startIndex, int endIndex)
/* 209:    */   {
/* 210:363 */     color.bind();
/* 211:364 */     this.fontTexture.bind();
/* 212:    */     
/* 213:366 */     IntObject intObject = null;
/* 214:    */     
/* 215:    */ 
/* 216:369 */     GL.glBegin(7);
/* 217:    */     
/* 218:371 */     int totalwidth = 0;
/* 219:372 */     for (int i = 0; i < whatchars.length(); i++)
/* 220:    */     {
/* 221:373 */       int charCurrent = whatchars.charAt(i);
/* 222:374 */       if (charCurrent < 256) {
/* 223:375 */         intObject = this.charArray[charCurrent];
/* 224:    */       } else {
/* 225:377 */         intObject = (IntObject)this.customChars.get(new Character((char)charCurrent));
/* 226:    */       }
/* 227:380 */       if (intObject != null)
/* 228:    */       {
/* 229:381 */         if ((i >= startIndex) || (i <= endIndex)) {
/* 230:382 */           drawQuad(x + totalwidth, y, 
/* 231:383 */             x + totalwidth + intObject.width, 
/* 232:384 */             y + intObject.height, intObject.storedX, 
/* 233:385 */             intObject.storedY, intObject.storedX + intObject.width, 
/* 234:386 */             intObject.storedY + intObject.height);
/* 235:    */         }
/* 236:388 */         totalwidth += intObject.width;
/* 237:    */       }
/* 238:    */     }
/* 239:392 */     GL.glEnd();
/* 240:    */   }
/* 241:    */   
/* 242:    */   public void drawString(float x, float y, String whatchars)
/* 243:    */   {
/* 244:406 */     drawString(x, y, whatchars, Color.white);
/* 245:    */   }
/* 246:    */   
/* 247:    */   private class IntObject
/* 248:    */   {
/* 249:    */     public int width;
/* 250:    */     public int height;
/* 251:    */     public int storedX;
/* 252:    */     public int storedY;
/* 253:    */     
/* 254:    */     private IntObject() {}
/* 255:    */   }
/* 256:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.TrueTypeFont
 * JD-Core Version:    0.7.0.1
 */