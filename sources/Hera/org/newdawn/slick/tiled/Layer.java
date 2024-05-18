/*     */ package org.newdawn.slick.tiled;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.util.Properties;
/*     */ import java.util.zip.GZIPInputStream;
/*     */ import org.newdawn.slick.SlickException;
/*     */ import org.newdawn.slick.util.Log;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Layer
/*     */ {
/*  21 */   private static byte[] baseCodes = new byte[256];
/*     */   
/*     */   private final TiledMap map;
/*     */   
/*     */   static {
/*     */     int i;
/*  27 */     for (i = 0; i < 256; i++)
/*  28 */       baseCodes[i] = -1; 
/*  29 */     for (i = 65; i <= 90; i++)
/*  30 */       baseCodes[i] = (byte)(i - 65); 
/*  31 */     for (i = 97; i <= 122; i++)
/*  32 */       baseCodes[i] = (byte)(26 + i - 97); 
/*  33 */     for (i = 48; i <= 57; i++)
/*  34 */       baseCodes[i] = (byte)(52 + i - 48); 
/*  35 */     baseCodes[43] = 62;
/*  36 */     baseCodes[47] = 63;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int index;
/*     */ 
/*     */ 
/*     */   
/*     */   public String name;
/*     */ 
/*     */ 
/*     */   
/*     */   public int[][][] data;
/*     */ 
/*     */ 
/*     */   
/*     */   public int width;
/*     */ 
/*     */ 
/*     */   
/*     */   public int height;
/*     */ 
/*     */ 
/*     */   
/*     */   public Properties props;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Layer(TiledMap map, Element element) throws SlickException {
/*  69 */     this.map = map;
/*  70 */     this.name = element.getAttribute("name");
/*  71 */     this.width = Integer.parseInt(element.getAttribute("width"));
/*  72 */     this.height = Integer.parseInt(element.getAttribute("height"));
/*  73 */     this.data = new int[this.width][this.height][3];
/*     */ 
/*     */     
/*  76 */     Element propsElement = (Element)element.getElementsByTagName("properties").item(0);
/*     */     
/*  78 */     if (propsElement != null) {
/*  79 */       NodeList properties = propsElement.getElementsByTagName("property");
/*  80 */       if (properties != null) {
/*  81 */         this.props = new Properties();
/*  82 */         for (int p = 0; p < properties.getLength(); p++) {
/*  83 */           Element propElement = (Element)properties.item(p);
/*     */           
/*  85 */           String name = propElement.getAttribute("name");
/*  86 */           String value = propElement.getAttribute("value");
/*  87 */           this.props.setProperty(name, value);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  92 */     Element dataNode = (Element)element.getElementsByTagName("data").item(0);
/*     */     
/*  94 */     String encoding = dataNode.getAttribute("encoding");
/*  95 */     String compression = dataNode.getAttribute("compression");
/*     */     
/*  97 */     if (encoding.equals("base64") && compression.equals("gzip")) {
/*     */       try {
/*  99 */         Node cdata = dataNode.getFirstChild();
/* 100 */         char[] enc = cdata.getNodeValue().trim().toCharArray();
/* 101 */         byte[] dec = decodeBase64(enc);
/* 102 */         GZIPInputStream is = new GZIPInputStream(new ByteArrayInputStream(dec));
/*     */ 
/*     */         
/* 105 */         for (int y = 0; y < this.height; y++) {
/* 106 */           for (int x = 0; x < this.width; x++) {
/* 107 */             int tileId = 0;
/* 108 */             tileId |= is.read();
/* 109 */             tileId |= is.read() << 8;
/* 110 */             tileId |= is.read() << 16;
/* 111 */             tileId |= is.read() << 24;
/*     */             
/* 113 */             if (tileId == 0) {
/* 114 */               this.data[x][y][0] = -1;
/* 115 */               this.data[x][y][1] = 0;
/* 116 */               this.data[x][y][2] = 0;
/*     */             } else {
/* 118 */               TileSet set = map.findTileSet(tileId);
/*     */               
/* 120 */               if (set != null) {
/* 121 */                 this.data[x][y][0] = set.index;
/* 122 */                 this.data[x][y][1] = tileId - set.firstGID;
/*     */               } 
/* 124 */               this.data[x][y][2] = tileId;
/*     */             } 
/*     */           } 
/*     */         } 
/* 128 */       } catch (IOException e) {
/* 129 */         Log.error(e);
/* 130 */         throw new SlickException("Unable to decode base 64 block");
/*     */       } 
/*     */     } else {
/* 133 */       throw new SlickException("Unsupport tiled map type: " + encoding + "," + compression + " (only gzip base64 supported)");
/*     */     } 
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
/*     */   public int getTileID(int x, int y) {
/* 148 */     return this.data[x][y][2];
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
/*     */   public void setTileID(int x, int y, int tile) {
/* 162 */     if (tile == 0) {
/* 163 */       this.data[x][y][0] = -1;
/* 164 */       this.data[x][y][1] = 0;
/* 165 */       this.data[x][y][2] = 0;
/*     */     } else {
/* 167 */       TileSet set = this.map.findTileSet(tile);
/*     */       
/* 169 */       this.data[x][y][0] = set.index;
/* 170 */       this.data[x][y][1] = tile - set.firstGID;
/* 171 */       this.data[x][y][2] = tile;
/*     */     } 
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
/*     */   public void render(int x, int y, int sx, int sy, int width, int ty, boolean lineByLine, int mapTileWidth, int mapTileHeight) {
/* 200 */     for (int tileset = 0; tileset < this.map.getTileSetCount(); tileset++) {
/* 201 */       TileSet set = null;
/*     */       
/* 203 */       for (int tx = 0; tx < width; tx++) {
/* 204 */         if (sx + tx >= 0 && sy + ty >= 0)
/*     */         {
/*     */           
/* 207 */           if (sx + tx < this.width && sy + ty < this.height)
/*     */           {
/*     */ 
/*     */             
/* 211 */             if (this.data[sx + tx][sy + ty][0] == tileset) {
/* 212 */               if (set == null) {
/* 213 */                 set = this.map.getTileSet(tileset);
/* 214 */                 set.tiles.startUse();
/*     */               } 
/*     */               
/* 217 */               int sheetX = set.getTileX(this.data[sx + tx][sy + ty][1]);
/* 218 */               int sheetY = set.getTileY(this.data[sx + tx][sy + ty][1]);
/*     */               
/* 220 */               int tileOffsetY = set.tileHeight - mapTileHeight;
/*     */ 
/*     */ 
/*     */               
/* 224 */               set.tiles.renderInUse(x + tx * mapTileWidth, y + ty * mapTileHeight - tileOffsetY, sheetX, sheetY);
/*     */             } 
/*     */           }
/*     */         }
/*     */       } 
/*     */       
/* 230 */       if (lineByLine) {
/* 231 */         if (set != null) {
/* 232 */           set.tiles.endUse();
/* 233 */           set = null;
/*     */         } 
/* 235 */         this.map.renderedLine(ty, ty + sy, this.index);
/*     */       } 
/*     */       
/* 238 */       if (set != null) {
/* 239 */         set.tiles.endUse();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private byte[] decodeBase64(char[] data) {
/* 252 */     int temp = data.length;
/* 253 */     for (int ix = 0; ix < data.length; ix++) {
/* 254 */       if (data[ix] > 'ÿ' || baseCodes[data[ix]] < 0) {
/* 255 */         temp--;
/*     */       }
/*     */     } 
/*     */     
/* 259 */     int len = temp / 4 * 3;
/* 260 */     if (temp % 4 == 3)
/* 261 */       len += 2; 
/* 262 */     if (temp % 4 == 2) {
/* 263 */       len++;
/*     */     }
/* 265 */     byte[] out = new byte[len];
/*     */     
/* 267 */     int shift = 0;
/* 268 */     int accum = 0;
/* 269 */     int index = 0;
/*     */     
/* 271 */     for (int i = 0; i < data.length; i++) {
/* 272 */       int value = (data[i] > 'ÿ') ? -1 : baseCodes[data[i]];
/*     */       
/* 274 */       if (value >= 0) {
/* 275 */         accum <<= 6;
/* 276 */         shift += 6;
/* 277 */         accum |= value;
/* 278 */         if (shift >= 8) {
/* 279 */           shift -= 8;
/* 280 */           out[index++] = (byte)(accum >> shift & 0xFF);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 285 */     if (index != out.length) {
/* 286 */       throw new RuntimeException("Data length appears to be wrong (wrote " + index + " should be " + out.length + ")");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 291 */     return out;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\tiled\Layer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */