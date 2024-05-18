/*     */ package org.newdawn.slick.tiled;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.Properties;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import org.newdawn.slick.Color;
/*     */ import org.newdawn.slick.Image;
/*     */ import org.newdawn.slick.SlickException;
/*     */ import org.newdawn.slick.SpriteSheet;
/*     */ import org.newdawn.slick.util.Log;
/*     */ import org.newdawn.slick.util.ResourceLoader;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.NodeList;
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
/*     */ public class TileSet
/*     */ {
/*     */   private final TiledMap map;
/*     */   public int index;
/*     */   public String name;
/*     */   public int firstGID;
/*  35 */   public int lastGID = Integer.MAX_VALUE;
/*     */ 
/*     */   
/*     */   public int tileWidth;
/*     */ 
/*     */   
/*     */   public int tileHeight;
/*     */   
/*     */   public SpriteSheet tiles;
/*     */   
/*     */   public int tilesAcross;
/*     */   
/*     */   public int tilesDown;
/*     */   
/*  49 */   private HashMap props = new HashMap<Object, Object>();
/*     */   
/*  51 */   protected int tileSpacing = 0;
/*     */   
/*  53 */   protected int tileMargin = 0;
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
/*     */   public TileSet(TiledMap map, Element element, boolean loadImage) throws SlickException {
/*  69 */     this.map = map;
/*  70 */     this.name = element.getAttribute("name");
/*  71 */     this.firstGID = Integer.parseInt(element.getAttribute("firstgid"));
/*  72 */     String source = element.getAttribute("source");
/*     */     
/*  74 */     if (source != null && !source.equals("")) {
/*     */       try {
/*  76 */         InputStream in = ResourceLoader.getResourceAsStream(map.getTilesLocation() + "/" + source);
/*     */         
/*  78 */         DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
/*     */         
/*  80 */         Document doc = builder.parse(in);
/*  81 */         Element docElement = doc.getDocumentElement();
/*  82 */         element = docElement;
/*     */       }
/*  84 */       catch (Exception e) {
/*  85 */         Log.error(e);
/*  86 */         throw new SlickException("Unable to load or parse sourced tileset: " + this.map.tilesLocation + "/" + source);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*  91 */     String tileWidthString = element.getAttribute("tilewidth");
/*  92 */     String tileHeightString = element.getAttribute("tileheight");
/*  93 */     if (tileWidthString.length() == 0 || tileHeightString.length() == 0) {
/*  94 */       throw new SlickException("TiledMap requires that the map be created with tilesets that use a single image.  Check the WiKi for more complete information.");
/*     */     }
/*     */ 
/*     */     
/*  98 */     this.tileWidth = Integer.parseInt(tileWidthString);
/*  99 */     this.tileHeight = Integer.parseInt(tileHeightString);
/*     */     
/* 101 */     String sv = element.getAttribute("spacing");
/* 102 */     if (sv != null && !sv.equals("")) {
/* 103 */       this.tileSpacing = Integer.parseInt(sv);
/*     */     }
/*     */     
/* 106 */     String mv = element.getAttribute("margin");
/* 107 */     if (mv != null && !mv.equals("")) {
/* 108 */       this.tileMargin = Integer.parseInt(mv);
/*     */     }
/*     */     
/* 111 */     NodeList list = element.getElementsByTagName("image");
/* 112 */     Element imageNode = (Element)list.item(0);
/* 113 */     String ref = imageNode.getAttribute("source");
/*     */     
/* 115 */     Color trans = null;
/* 116 */     String t = imageNode.getAttribute("trans");
/* 117 */     if (t != null && t.length() > 0) {
/* 118 */       int c = Integer.parseInt(t, 16);
/*     */       
/* 120 */       trans = new Color(c);
/*     */     } 
/*     */     
/* 123 */     if (loadImage) {
/* 124 */       Image image = new Image(map.getTilesLocation() + "/" + ref, false, 2, trans);
/*     */       
/* 126 */       setTileSetImage(image);
/*     */     } 
/*     */     
/* 129 */     NodeList pElements = element.getElementsByTagName("tile");
/* 130 */     for (int i = 0; i < pElements.getLength(); i++) {
/* 131 */       Element tileElement = (Element)pElements.item(i);
/*     */       
/* 133 */       int id = Integer.parseInt(tileElement.getAttribute("id"));
/* 134 */       id += this.firstGID;
/* 135 */       Properties tileProps = new Properties();
/*     */       
/* 137 */       Element propsElement = (Element)tileElement.getElementsByTagName("properties").item(0);
/*     */       
/* 139 */       NodeList properties = propsElement.getElementsByTagName("property");
/* 140 */       for (int p = 0; p < properties.getLength(); p++) {
/* 141 */         Element propElement = (Element)properties.item(p);
/*     */         
/* 143 */         String name = propElement.getAttribute("name");
/* 144 */         String value = propElement.getAttribute("value");
/*     */         
/* 146 */         tileProps.setProperty(name, value);
/*     */       } 
/*     */       
/* 149 */       this.props.put(new Integer(id), tileProps);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTileWidth() {
/* 159 */     return this.tileWidth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTileHeight() {
/* 168 */     return this.tileHeight;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTileSpacing() {
/* 177 */     return this.tileSpacing;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTileMargin() {
/* 186 */     return this.tileMargin;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTileSetImage(Image image) {
/* 196 */     this.tiles = new SpriteSheet(image, this.tileWidth, this.tileHeight, this.tileSpacing, this.tileMargin);
/*     */     
/* 198 */     this.tilesAcross = this.tiles.getHorizontalCount();
/* 199 */     this.tilesDown = this.tiles.getVerticalCount();
/*     */     
/* 201 */     if (this.tilesAcross <= 0) {
/* 202 */       this.tilesAcross = 1;
/*     */     }
/* 204 */     if (this.tilesDown <= 0) {
/* 205 */       this.tilesDown = 1;
/*     */     }
/*     */     
/* 208 */     this.lastGID = this.tilesAcross * this.tilesDown + this.firstGID - 1;
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
/*     */   public Properties getProperties(int globalID) {
/* 220 */     return (Properties)this.props.get(new Integer(globalID));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTileX(int id) {
/* 231 */     return id % this.tilesAcross;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTileY(int id) {
/* 242 */     return id / this.tilesAcross;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLimit(int limit) {
/* 252 */     this.lastGID = limit;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(int gid) {
/* 263 */     return (gid >= this.firstGID && gid <= this.lastGID);
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\tiled\TileSet.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */