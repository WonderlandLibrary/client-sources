/*   1:    */ package org.newdawn.slick.font;
/*   2:    */ 
/*   3:    */ import java.io.BufferedReader;
/*   4:    */ import java.io.File;
/*   5:    */ import java.io.FileOutputStream;
/*   6:    */ import java.io.IOException;
/*   7:    */ import java.io.InputStream;
/*   8:    */ import java.io.InputStreamReader;
/*   9:    */ import java.io.PrintStream;
/*  10:    */ import java.util.ArrayList;
/*  11:    */ import java.util.Iterator;
/*  12:    */ import java.util.List;
/*  13:    */ import org.newdawn.slick.SlickException;
/*  14:    */ import org.newdawn.slick.font.effects.ConfigurableEffect;
/*  15:    */ import org.newdawn.slick.font.effects.ConfigurableEffect.Value;
/*  16:    */ import org.newdawn.slick.util.ResourceLoader;
/*  17:    */ 
/*  18:    */ public class HieroSettings
/*  19:    */ {
/*  20: 28 */   private int fontSize = 12;
/*  21: 30 */   private boolean bold = false;
/*  22: 32 */   private boolean italic = false;
/*  23:    */   private int paddingTop;
/*  24:    */   private int paddingLeft;
/*  25:    */   private int paddingBottom;
/*  26:    */   private int paddingRight;
/*  27:    */   private int paddingAdvanceX;
/*  28:    */   private int paddingAdvanceY;
/*  29: 46 */   private int glyphPageWidth = 512;
/*  30: 48 */   private int glyphPageHeight = 512;
/*  31: 50 */   private final List effects = new ArrayList();
/*  32:    */   
/*  33:    */   public HieroSettings() {}
/*  34:    */   
/*  35:    */   public HieroSettings(String hieroFileRef)
/*  36:    */     throws SlickException
/*  37:    */   {
/*  38: 65 */     this(ResourceLoader.getResourceAsStream(hieroFileRef));
/*  39:    */   }
/*  40:    */   
/*  41:    */   public HieroSettings(InputStream in)
/*  42:    */     throws SlickException
/*  43:    */   {
/*  44:    */     try
/*  45:    */     {
/*  46: 76 */       BufferedReader reader = new BufferedReader(new InputStreamReader(in));
/*  47:    */       for (;;)
/*  48:    */       {
/*  49: 78 */         String line = reader.readLine();
/*  50: 79 */         if (line == null) {
/*  51:    */           break;
/*  52:    */         }
/*  53: 80 */         line = line.trim();
/*  54: 81 */         if (line.length() != 0)
/*  55:    */         {
/*  56: 82 */           String[] pieces = line.split("=", 2);
/*  57: 83 */           String name = pieces[0].trim();
/*  58: 84 */           String value = pieces[1];
/*  59: 85 */           if (name.equals("font.size"))
/*  60:    */           {
/*  61: 86 */             this.fontSize = Integer.parseInt(value);
/*  62:    */           }
/*  63: 87 */           else if (name.equals("font.bold"))
/*  64:    */           {
/*  65: 88 */             this.bold = Boolean.valueOf(value).booleanValue();
/*  66:    */           }
/*  67: 89 */           else if (name.equals("font.italic"))
/*  68:    */           {
/*  69: 90 */             this.italic = Boolean.valueOf(value).booleanValue();
/*  70:    */           }
/*  71: 91 */           else if (name.equals("pad.top"))
/*  72:    */           {
/*  73: 92 */             this.paddingTop = Integer.parseInt(value);
/*  74:    */           }
/*  75: 93 */           else if (name.equals("pad.right"))
/*  76:    */           {
/*  77: 94 */             this.paddingRight = Integer.parseInt(value);
/*  78:    */           }
/*  79: 95 */           else if (name.equals("pad.bottom"))
/*  80:    */           {
/*  81: 96 */             this.paddingBottom = Integer.parseInt(value);
/*  82:    */           }
/*  83: 97 */           else if (name.equals("pad.left"))
/*  84:    */           {
/*  85: 98 */             this.paddingLeft = Integer.parseInt(value);
/*  86:    */           }
/*  87: 99 */           else if (name.equals("pad.advance.x"))
/*  88:    */           {
/*  89:100 */             this.paddingAdvanceX = Integer.parseInt(value);
/*  90:    */           }
/*  91:101 */           else if (name.equals("pad.advance.y"))
/*  92:    */           {
/*  93:102 */             this.paddingAdvanceY = Integer.parseInt(value);
/*  94:    */           }
/*  95:103 */           else if (name.equals("glyph.page.width"))
/*  96:    */           {
/*  97:104 */             this.glyphPageWidth = Integer.parseInt(value);
/*  98:    */           }
/*  99:105 */           else if (name.equals("glyph.page.height"))
/* 100:    */           {
/* 101:106 */             this.glyphPageHeight = Integer.parseInt(value);
/* 102:    */           }
/* 103:107 */           else if (name.equals("effect.class"))
/* 104:    */           {
/* 105:    */             try
/* 106:    */             {
/* 107:109 */               this.effects.add(Class.forName(value).newInstance());
/* 108:    */             }
/* 109:    */             catch (Exception ex)
/* 110:    */             {
/* 111:111 */               throw new SlickException("Unable to create effect instance: " + value, ex);
/* 112:    */             }
/* 113:    */           }
/* 114:113 */           else if (name.startsWith("effect."))
/* 115:    */           {
/* 116:115 */             name = name.substring(7);
/* 117:116 */             ConfigurableEffect effect = (ConfigurableEffect)this.effects.get(this.effects.size() - 1);
/* 118:117 */             List values = effect.getValues();
/* 119:118 */             for (Iterator iter = values.iterator(); iter.hasNext();)
/* 120:    */             {
/* 121:119 */               ConfigurableEffect.Value effectValue = (ConfigurableEffect.Value)iter.next();
/* 122:120 */               if (effectValue.getName().equals(name))
/* 123:    */               {
/* 124:121 */                 effectValue.setString(value);
/* 125:122 */                 break;
/* 126:    */               }
/* 127:    */             }
/* 128:125 */             effect.setValues(values);
/* 129:    */           }
/* 130:    */         }
/* 131:    */       }
/* 132:128 */       reader.close();
/* 133:    */     }
/* 134:    */     catch (Exception ex)
/* 135:    */     {
/* 136:130 */       throw new SlickException("Unable to load Hiero font file", ex);
/* 137:    */     }
/* 138:    */   }
/* 139:    */   
/* 140:    */   public int getPaddingTop()
/* 141:    */   {
/* 142:140 */     return this.paddingTop;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void setPaddingTop(int paddingTop)
/* 146:    */   {
/* 147:149 */     this.paddingTop = paddingTop;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public int getPaddingLeft()
/* 151:    */   {
/* 152:158 */     return this.paddingLeft;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void setPaddingLeft(int paddingLeft)
/* 156:    */   {
/* 157:167 */     this.paddingLeft = paddingLeft;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public int getPaddingBottom()
/* 161:    */   {
/* 162:176 */     return this.paddingBottom;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void setPaddingBottom(int paddingBottom)
/* 166:    */   {
/* 167:185 */     this.paddingBottom = paddingBottom;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public int getPaddingRight()
/* 171:    */   {
/* 172:194 */     return this.paddingRight;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void setPaddingRight(int paddingRight)
/* 176:    */   {
/* 177:203 */     this.paddingRight = paddingRight;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public int getPaddingAdvanceX()
/* 181:    */   {
/* 182:212 */     return this.paddingAdvanceX;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public void setPaddingAdvanceX(int paddingAdvanceX)
/* 186:    */   {
/* 187:221 */     this.paddingAdvanceX = paddingAdvanceX;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public int getPaddingAdvanceY()
/* 191:    */   {
/* 192:230 */     return this.paddingAdvanceY;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public void setPaddingAdvanceY(int paddingAdvanceY)
/* 196:    */   {
/* 197:239 */     this.paddingAdvanceY = paddingAdvanceY;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public int getGlyphPageWidth()
/* 201:    */   {
/* 202:248 */     return this.glyphPageWidth;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public void setGlyphPageWidth(int glyphPageWidth)
/* 206:    */   {
/* 207:257 */     this.glyphPageWidth = glyphPageWidth;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public int getGlyphPageHeight()
/* 211:    */   {
/* 212:266 */     return this.glyphPageHeight;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public void setGlyphPageHeight(int glyphPageHeight)
/* 216:    */   {
/* 217:275 */     this.glyphPageHeight = glyphPageHeight;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public int getFontSize()
/* 221:    */   {
/* 222:285 */     return this.fontSize;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public void setFontSize(int fontSize)
/* 226:    */   {
/* 227:295 */     this.fontSize = fontSize;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public boolean isBold()
/* 231:    */   {
/* 232:305 */     return this.bold;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public void setBold(boolean bold)
/* 236:    */   {
/* 237:315 */     this.bold = bold;
/* 238:    */   }
/* 239:    */   
/* 240:    */   public boolean isItalic()
/* 241:    */   {
/* 242:325 */     return this.italic;
/* 243:    */   }
/* 244:    */   
/* 245:    */   public void setItalic(boolean italic)
/* 246:    */   {
/* 247:335 */     this.italic = italic;
/* 248:    */   }
/* 249:    */   
/* 250:    */   public List getEffects()
/* 251:    */   {
/* 252:344 */     return this.effects;
/* 253:    */   }
/* 254:    */   
/* 255:    */   public void save(File file)
/* 256:    */     throws IOException
/* 257:    */   {
/* 258:354 */     PrintStream out = new PrintStream(new FileOutputStream(file));
/* 259:355 */     out.println("font.size=" + this.fontSize);
/* 260:356 */     out.println("font.bold=" + this.bold);
/* 261:357 */     out.println("font.italic=" + this.italic);
/* 262:358 */     out.println();
/* 263:359 */     out.println("pad.top=" + this.paddingTop);
/* 264:360 */     out.println("pad.right=" + this.paddingRight);
/* 265:361 */     out.println("pad.bottom=" + this.paddingBottom);
/* 266:362 */     out.println("pad.left=" + this.paddingLeft);
/* 267:363 */     out.println("pad.advance.x=" + this.paddingAdvanceX);
/* 268:364 */     out.println("pad.advance.y=" + this.paddingAdvanceY);
/* 269:365 */     out.println();
/* 270:366 */     out.println("glyph.page.width=" + this.glyphPageWidth);
/* 271:367 */     out.println("glyph.page.height=" + this.glyphPageHeight);
/* 272:368 */     out.println();
/* 273:369 */     for (Iterator iter = this.effects.iterator(); iter.hasNext();)
/* 274:    */     {
/* 275:370 */       ConfigurableEffect effect = (ConfigurableEffect)iter.next();
/* 276:371 */       out.println("effect.class=" + effect.getClass().getName());
/* 277:372 */       for (Iterator iter2 = effect.getValues().iterator(); iter2.hasNext();)
/* 278:    */       {
/* 279:373 */         ConfigurableEffect.Value value = (ConfigurableEffect.Value)iter2.next();
/* 280:374 */         out.println("effect." + value.getName() + "=" + value.getString());
/* 281:    */       }
/* 282:376 */       out.println();
/* 283:    */     }
/* 284:378 */     out.close();
/* 285:    */   }
/* 286:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.font.HieroSettings
 * JD-Core Version:    0.7.0.1
 */