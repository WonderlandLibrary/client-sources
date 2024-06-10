/*   1:    */ package org.newdawn.slick.tiled;
/*   2:    */ 
/*   3:    */ import java.io.ByteArrayInputStream;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.util.Properties;
/*   6:    */ import java.util.zip.GZIPInputStream;
/*   7:    */ import org.newdawn.slick.SlickException;
/*   8:    */ import org.newdawn.slick.SpriteSheet;
/*   9:    */ import org.newdawn.slick.util.Log;
/*  10:    */ import org.w3c.dom.Element;
/*  11:    */ import org.w3c.dom.Node;
/*  12:    */ import org.w3c.dom.NodeList;
/*  13:    */ 
/*  14:    */ public class Layer
/*  15:    */ {
/*  16: 21 */   private static byte[] baseCodes = new byte[256];
/*  17:    */   private final TiledMap map;
/*  18:    */   public int index;
/*  19:    */   public String name;
/*  20:    */   public int[][][] data;
/*  21:    */   public int width;
/*  22:    */   public int height;
/*  23:    */   public Properties props;
/*  24:    */   
/*  25:    */   static
/*  26:    */   {
/*  27: 27 */     for (int i = 0; i < 256; i++) {
/*  28: 28 */       baseCodes[i] = -1;
/*  29:    */     }
/*  30: 29 */     for (int i = 65; i <= 90; i++) {
/*  31: 30 */       baseCodes[i] = ((byte)(i - 65));
/*  32:    */     }
/*  33: 31 */     for (int i = 97; i <= 122; i++) {
/*  34: 32 */       baseCodes[i] = ((byte)(26 + i - 97));
/*  35:    */     }
/*  36: 33 */     for (int i = 48; i <= 57; i++) {
/*  37: 34 */       baseCodes[i] = ((byte)(52 + i - 48));
/*  38:    */     }
/*  39: 35 */     baseCodes[43] = 62;
/*  40: 36 */     baseCodes[47] = 63;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public Layer(TiledMap map, Element element)
/*  44:    */     throws SlickException
/*  45:    */   {
/*  46: 69 */     this.map = map;
/*  47: 70 */     this.name = element.getAttribute("name");
/*  48: 71 */     this.width = Integer.parseInt(element.getAttribute("width"));
/*  49: 72 */     this.height = Integer.parseInt(element.getAttribute("height"));
/*  50: 73 */     this.data = new int[this.width][this.height][3];
/*  51:    */     
/*  52:    */ 
/*  53: 76 */     Element propsElement = (Element)element.getElementsByTagName(
/*  54: 77 */       "properties").item(0);
/*  55: 78 */     if (propsElement != null)
/*  56:    */     {
/*  57: 79 */       NodeList properties = propsElement.getElementsByTagName("property");
/*  58: 80 */       if (properties != null)
/*  59:    */       {
/*  60: 81 */         this.props = new Properties();
/*  61: 82 */         for (int p = 0; p < properties.getLength(); p++)
/*  62:    */         {
/*  63: 83 */           Element propElement = (Element)properties.item(p);
/*  64:    */           
/*  65: 85 */           String name = propElement.getAttribute("name");
/*  66: 86 */           String value = propElement.getAttribute("value");
/*  67: 87 */           this.props.setProperty(name, value);
/*  68:    */         }
/*  69:    */       }
/*  70:    */     }
/*  71: 92 */     Element dataNode = (Element)element.getElementsByTagName("data").item(
/*  72: 93 */       0);
/*  73: 94 */     String encoding = dataNode.getAttribute("encoding");
/*  74: 95 */     String compression = dataNode.getAttribute("compression");
/*  75: 97 */     if ((encoding.equals("base64")) && (compression.equals("gzip"))) {
/*  76:    */       try
/*  77:    */       {
/*  78: 99 */         Node cdata = dataNode.getFirstChild();
/*  79:100 */         char[] enc = cdata.getNodeValue().trim().toCharArray();
/*  80:101 */         byte[] dec = decodeBase64(enc);
/*  81:102 */         GZIPInputStream is = new GZIPInputStream(
/*  82:103 */           new ByteArrayInputStream(dec));
/*  83:105 */         for (int y = 0; y < this.height; y++) {
/*  84:106 */           for (int x = 0; x < this.width; x++)
/*  85:    */           {
/*  86:107 */             int tileId = 0;
/*  87:108 */             tileId |= is.read();
/*  88:109 */             tileId |= is.read() << 8;
/*  89:110 */             tileId |= is.read() << 16;
/*  90:111 */             tileId |= is.read() << 24;
/*  91:113 */             if (tileId == 0)
/*  92:    */             {
/*  93:114 */               this.data[x][y][0] = -1;
/*  94:115 */               this.data[x][y][1] = 0;
/*  95:116 */               this.data[x][y][2] = 0;
/*  96:    */             }
/*  97:    */             else
/*  98:    */             {
/*  99:118 */               TileSet set = map.findTileSet(tileId);
/* 100:120 */               if (set != null)
/* 101:    */               {
/* 102:121 */                 this.data[x][y][0] = set.index;
/* 103:122 */                 this.data[x][y][1] = (tileId - set.firstGID);
/* 104:    */               }
/* 105:124 */               this.data[x][y][2] = tileId;
/* 106:    */             }
/* 107:    */           }
/* 108:    */         }
/* 109:    */       }
/* 110:    */       catch (IOException e)
/* 111:    */       {
/* 112:129 */         Log.error(e);
/* 113:130 */         throw new SlickException("Unable to decode base 64 block");
/* 114:    */       }
/* 115:    */     } else {
/* 116:133 */       throw new SlickException("Unsupport tiled map type: " + encoding + 
/* 117:134 */         "," + compression + " (only gzip base64 supported)");
/* 118:    */     }
/* 119:    */   }
/* 120:    */   
/* 121:    */   public int getTileID(int x, int y)
/* 122:    */   {
/* 123:148 */     return this.data[x][y][2];
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void setTileID(int x, int y, int tile)
/* 127:    */   {
/* 128:162 */     if (tile == 0)
/* 129:    */     {
/* 130:163 */       this.data[x][y][0] = -1;
/* 131:164 */       this.data[x][y][1] = 0;
/* 132:165 */       this.data[x][y][2] = 0;
/* 133:    */     }
/* 134:    */     else
/* 135:    */     {
/* 136:167 */       TileSet set = this.map.findTileSet(tile);
/* 137:    */       
/* 138:169 */       this.data[x][y][0] = set.index;
/* 139:170 */       this.data[x][y][1] = (tile - set.firstGID);
/* 140:171 */       this.data[x][y][2] = tile;
/* 141:    */     }
/* 142:    */   }
/* 143:    */   
/* 144:    */   public void render(int x, int y, int sx, int sy, int width, int ty, boolean lineByLine, int mapTileWidth, int mapTileHeight)
/* 145:    */   {
/* 146:200 */     for (int tileset = 0; tileset < this.map.getTileSetCount(); tileset++)
/* 147:    */     {
/* 148:201 */       TileSet set = null;
/* 149:203 */       for (int tx = 0; tx < width; tx++) {
/* 150:204 */         if ((sx + tx >= 0) && (sy + ty >= 0)) {
/* 151:207 */           if ((sx + tx < this.width) && (sy + ty < this.height)) {
/* 152:211 */             if (this.data[(sx + tx)][(sy + ty)][0] == tileset)
/* 153:    */             {
/* 154:212 */               if (set == null)
/* 155:    */               {
/* 156:213 */                 set = this.map.getTileSet(tileset);
/* 157:214 */                 set.tiles.startUse();
/* 158:    */               }
/* 159:217 */               int sheetX = set.getTileX(this.data[(sx + tx)][(sy + ty)][1]);
/* 160:218 */               int sheetY = set.getTileY(this.data[(sx + tx)][(sy + ty)][1]);
/* 161:    */               
/* 162:220 */               int tileOffsetY = set.tileHeight - mapTileHeight;
/* 163:    */               
/* 164:    */ 
/* 165:    */ 
/* 166:224 */               set.tiles.renderInUse(x + tx * mapTileWidth, y + 
/* 167:225 */                 ty * mapTileHeight - tileOffsetY, sheetX, 
/* 168:226 */                 sheetY);
/* 169:    */             }
/* 170:    */           }
/* 171:    */         }
/* 172:    */       }
/* 173:230 */       if (lineByLine)
/* 174:    */       {
/* 175:231 */         if (set != null)
/* 176:    */         {
/* 177:232 */           set.tiles.endUse();
/* 178:233 */           set = null;
/* 179:    */         }
/* 180:235 */         this.map.renderedLine(ty, ty + sy, this.index);
/* 181:    */       }
/* 182:238 */       if (set != null) {
/* 183:239 */         set.tiles.endUse();
/* 184:    */       }
/* 185:    */     }
/* 186:    */   }
/* 187:    */   
/* 188:    */   private byte[] decodeBase64(char[] data)
/* 189:    */   {
/* 190:252 */     int temp = data.length;
/* 191:253 */     for (int ix = 0; ix < data.length; ix++) {
/* 192:254 */       if ((data[ix] > 'ÿ') || (baseCodes[data[ix]] < 0)) {
/* 193:255 */         temp--;
/* 194:    */       }
/* 195:    */     }
/* 196:259 */     int len = temp / 4 * 3;
/* 197:260 */     if (temp % 4 == 3) {
/* 198:261 */       len += 2;
/* 199:    */     }
/* 200:262 */     if (temp % 4 == 2) {
/* 201:263 */       len++;
/* 202:    */     }
/* 203:265 */     byte[] out = new byte[len];
/* 204:    */     
/* 205:267 */     int shift = 0;
/* 206:268 */     int accum = 0;
/* 207:269 */     int index = 0;
/* 208:271 */     for (int ix = 0; ix < data.length; ix++)
/* 209:    */     {
/* 210:272 */       int value = data[ix] > 'ÿ' ? -1 : baseCodes[data[ix]];
/* 211:274 */       if (value >= 0)
/* 212:    */       {
/* 213:275 */         accum <<= 6;
/* 214:276 */         shift += 6;
/* 215:277 */         accum |= value;
/* 216:278 */         if (shift >= 8)
/* 217:    */         {
/* 218:279 */           shift -= 8;
/* 219:280 */           out[(index++)] = ((byte)(accum >> shift & 0xFF));
/* 220:    */         }
/* 221:    */       }
/* 222:    */     }
/* 223:285 */     if (index != out.length) {
/* 224:286 */       throw new RuntimeException(
/* 225:287 */         "Data length appears to be wrong (wrote " + index + 
/* 226:288 */         " should be " + out.length + ")");
/* 227:    */     }
/* 228:291 */     return out;
/* 229:    */   }
/* 230:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.tiled.Layer
 * JD-Core Version:    0.7.0.1
 */