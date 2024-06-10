/*   1:    */ package org.newdawn.slick.font;
/*   2:    */ 
/*   3:    */ import java.awt.AlphaComposite;
/*   4:    */ import java.awt.Graphics2D;
/*   5:    */ import java.awt.RenderingHints;
/*   6:    */ import java.awt.font.FontRenderContext;
/*   7:    */ import java.awt.image.BufferedImage;
/*   8:    */ import java.awt.image.WritableRaster;
/*   9:    */ import java.nio.ByteBuffer;
/*  10:    */ import java.nio.ByteOrder;
/*  11:    */ import java.nio.IntBuffer;
/*  12:    */ import java.util.ArrayList;
/*  13:    */ import java.util.Iterator;
/*  14:    */ import java.util.List;
/*  15:    */ import java.util.ListIterator;
/*  16:    */ import org.newdawn.slick.Image;
/*  17:    */ import org.newdawn.slick.SlickException;
/*  18:    */ import org.newdawn.slick.UnicodeFont;
/*  19:    */ import org.newdawn.slick.font.effects.Effect;
/*  20:    */ import org.newdawn.slick.opengl.TextureImpl;
/*  21:    */ import org.newdawn.slick.opengl.renderer.Renderer;
/*  22:    */ import org.newdawn.slick.opengl.renderer.SGL;
/*  23:    */ 
/*  24:    */ public class GlyphPage
/*  25:    */ {
/*  26: 34 */   private static final SGL GL = ;
/*  27:    */   public static final int MAX_GLYPH_SIZE = 256;
/*  28: 40 */   private static ByteBuffer scratchByteBuffer = ByteBuffer.allocateDirect(262144);
/*  29:    */   private static IntBuffer scratchIntBuffer;
/*  30:    */   private static BufferedImage scratchImage;
/*  31:    */   private static Graphics2D scratchGraphics;
/*  32:    */   
/*  33:    */   static
/*  34:    */   {
/*  35: 43 */     scratchByteBuffer.order(ByteOrder.LITTLE_ENDIAN);
/*  36:    */     
/*  37:    */ 
/*  38:    */ 
/*  39: 47 */     scratchIntBuffer = scratchByteBuffer.asIntBuffer();
/*  40:    */     
/*  41:    */ 
/*  42:    */ 
/*  43: 51 */     scratchImage = new BufferedImage(256, 256, 2);
/*  44:    */     
/*  45: 53 */     scratchGraphics = (Graphics2D)scratchImage.getGraphics();
/*  46:    */     
/*  47:    */ 
/*  48: 56 */     scratchGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
/*  49: 57 */     scratchGraphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
/*  50: 58 */     scratchGraphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
/*  51:    */   }
/*  52:    */   
/*  53: 62 */   public static FontRenderContext renderContext = scratchGraphics.getFontRenderContext();
/*  54:    */   private final UnicodeFont unicodeFont;
/*  55:    */   private final int pageWidth;
/*  56:    */   private final int pageHeight;
/*  57:    */   private final Image pageImage;
/*  58:    */   private int pageX;
/*  59:    */   private int pageY;
/*  60:    */   private int rowHeight;
/*  61:    */   private boolean orderAscending;
/*  62:    */   
/*  63:    */   public static Graphics2D getScratchGraphics()
/*  64:    */   {
/*  65: 70 */     return scratchGraphics;
/*  66:    */   }
/*  67:    */   
/*  68: 90 */   private final List pageGlyphs = new ArrayList(32);
/*  69:    */   
/*  70:    */   public GlyphPage(UnicodeFont unicodeFont, int pageWidth, int pageHeight)
/*  71:    */     throws SlickException
/*  72:    */   {
/*  73:101 */     this.unicodeFont = unicodeFont;
/*  74:102 */     this.pageWidth = pageWidth;
/*  75:103 */     this.pageHeight = pageHeight;
/*  76:    */     
/*  77:105 */     this.pageImage = new Image(pageWidth, pageHeight);
/*  78:    */   }
/*  79:    */   
/*  80:    */   public int loadGlyphs(List glyphs, int maxGlyphsToLoad)
/*  81:    */     throws SlickException
/*  82:    */   {
/*  83:120 */     if ((this.rowHeight != 0) && (maxGlyphsToLoad == -1))
/*  84:    */     {
/*  85:122 */       int testX = this.pageX;
/*  86:123 */       int testY = this.pageY;
/*  87:124 */       int testRowHeight = this.rowHeight;
/*  88:125 */       for (Iterator iter = getIterator(glyphs); iter.hasNext();)
/*  89:    */       {
/*  90:126 */         Glyph glyph = (Glyph)iter.next();
/*  91:127 */         int width = glyph.getWidth();
/*  92:128 */         int height = glyph.getHeight();
/*  93:129 */         if (testX + width >= this.pageWidth)
/*  94:    */         {
/*  95:130 */           testX = 0;
/*  96:131 */           testY += testRowHeight;
/*  97:132 */           testRowHeight = height;
/*  98:    */         }
/*  99:133 */         else if (height > testRowHeight)
/* 100:    */         {
/* 101:134 */           testRowHeight = height;
/* 102:    */         }
/* 103:136 */         if (testY + testRowHeight >= this.pageWidth) {
/* 104:136 */           return 0;
/* 105:    */         }
/* 106:137 */         testX += width;
/* 107:    */       }
/* 108:    */     }
/* 109:141 */     org.newdawn.slick.Color.white.bind();
/* 110:142 */     this.pageImage.bind();
/* 111:    */     
/* 112:144 */     int i = 0;
/* 113:145 */     for (Iterator iter = getIterator(glyphs); iter.hasNext();)
/* 114:    */     {
/* 115:146 */       Glyph glyph = (Glyph)iter.next();
/* 116:147 */       int width = Math.min(256, glyph.getWidth());
/* 117:148 */       int height = Math.min(256, glyph.getHeight());
/* 118:150 */       if (this.rowHeight == 0)
/* 119:    */       {
/* 120:152 */         this.rowHeight = height;
/* 121:    */       }
/* 122:155 */       else if (this.pageX + width >= this.pageWidth)
/* 123:    */       {
/* 124:156 */         if (this.pageY + this.rowHeight + height >= this.pageHeight) {
/* 125:    */           break;
/* 126:    */         }
/* 127:157 */         this.pageX = 0;
/* 128:158 */         this.pageY += this.rowHeight;
/* 129:159 */         this.rowHeight = height;
/* 130:    */       }
/* 131:160 */       else if (height > this.rowHeight)
/* 132:    */       {
/* 133:161 */         if (this.pageY + height >= this.pageHeight) {
/* 134:    */           break;
/* 135:    */         }
/* 136:162 */         this.rowHeight = height;
/* 137:    */       }
/* 138:166 */       renderGlyph(glyph, width, height);
/* 139:167 */       this.pageGlyphs.add(glyph);
/* 140:    */       
/* 141:169 */       this.pageX += width;
/* 142:    */       
/* 143:171 */       iter.remove();
/* 144:172 */       i++;
/* 145:173 */       if (i == maxGlyphsToLoad)
/* 146:    */       {
/* 147:175 */         this.orderAscending = (!this.orderAscending);
/* 148:176 */         break;
/* 149:    */       }
/* 150:    */     }
/* 151:180 */     TextureImpl.bindNone();
/* 152:    */     
/* 153:    */ 
/* 154:183 */     this.orderAscending = (!this.orderAscending);
/* 155:    */     
/* 156:185 */     return i;
/* 157:    */   }
/* 158:    */   
/* 159:    */   private void renderGlyph(Glyph glyph, int width, int height)
/* 160:    */     throws SlickException
/* 161:    */   {
/* 162:198 */     scratchGraphics.setComposite(AlphaComposite.Clear);
/* 163:199 */     scratchGraphics.fillRect(0, 0, 256, 256);
/* 164:200 */     scratchGraphics.setComposite(AlphaComposite.SrcOver);
/* 165:201 */     scratchGraphics.setColor(java.awt.Color.white);
/* 166:202 */     for (Iterator iter = this.unicodeFont.getEffects().iterator(); iter.hasNext();) {
/* 167:203 */       ((Effect)iter.next()).draw(scratchImage, scratchGraphics, this.unicodeFont, glyph);
/* 168:    */     }
/* 169:204 */     glyph.setShape(null);
/* 170:    */     
/* 171:206 */     WritableRaster raster = scratchImage.getRaster();
/* 172:207 */     int[] row = new int[width];
/* 173:208 */     for (int y = 0; y < height; y++)
/* 174:    */     {
/* 175:209 */       raster.getDataElements(0, y, width, 1, row);
/* 176:210 */       scratchIntBuffer.put(row);
/* 177:    */     }
/* 178:212 */     GL.glTexSubImage2D(3553, 0, this.pageX, this.pageY, width, height, 32993, 5121, 
/* 179:213 */       scratchByteBuffer);
/* 180:214 */     scratchIntBuffer.clear();
/* 181:    */     
/* 182:216 */     glyph.setImage(this.pageImage.getSubImage(this.pageX, this.pageY, width, height));
/* 183:    */   }
/* 184:    */   
/* 185:    */   private Iterator getIterator(List glyphs)
/* 186:    */   {
/* 187:226 */     if (this.orderAscending) {
/* 188:226 */       return glyphs.iterator();
/* 189:    */     }
/* 190:227 */     final ListIterator iter = glyphs.listIterator(glyphs.size());
/* 191:228 */     new Iterator()
/* 192:    */     {
/* 193:    */       public boolean hasNext()
/* 194:    */       {
/* 195:230 */         return iter.hasPrevious();
/* 196:    */       }
/* 197:    */       
/* 198:    */       public Object next()
/* 199:    */       {
/* 200:234 */         return iter.previous();
/* 201:    */       }
/* 202:    */       
/* 203:    */       public void remove()
/* 204:    */       {
/* 205:238 */         iter.remove();
/* 206:    */       }
/* 207:    */     };
/* 208:    */   }
/* 209:    */   
/* 210:    */   public List getGlyphs()
/* 211:    */   {
/* 212:249 */     return this.pageGlyphs;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public Image getImage()
/* 216:    */   {
/* 217:258 */     return this.pageImage;
/* 218:    */   }
/* 219:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.font.GlyphPage
 * JD-Core Version:    0.7.0.1
 */