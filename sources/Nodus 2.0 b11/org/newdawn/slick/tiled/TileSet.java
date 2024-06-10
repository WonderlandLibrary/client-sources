/*   1:    */ package org.newdawn.slick.tiled;
/*   2:    */ 
/*   3:    */ import java.io.InputStream;
/*   4:    */ import java.util.HashMap;
/*   5:    */ import java.util.Properties;
/*   6:    */ import javax.xml.parsers.DocumentBuilder;
/*   7:    */ import javax.xml.parsers.DocumentBuilderFactory;
/*   8:    */ import org.newdawn.slick.Color;
/*   9:    */ import org.newdawn.slick.Image;
/*  10:    */ import org.newdawn.slick.SlickException;
/*  11:    */ import org.newdawn.slick.SpriteSheet;
/*  12:    */ import org.newdawn.slick.util.Log;
/*  13:    */ import org.newdawn.slick.util.ResourceLoader;
/*  14:    */ import org.w3c.dom.Document;
/*  15:    */ import org.w3c.dom.Element;
/*  16:    */ import org.w3c.dom.NodeList;
/*  17:    */ 
/*  18:    */ public class TileSet
/*  19:    */ {
/*  20:    */   private final TiledMap map;
/*  21:    */   public int index;
/*  22:    */   public String name;
/*  23:    */   public int firstGID;
/*  24: 35 */   public int lastGID = 2147483647;
/*  25:    */   public int tileWidth;
/*  26:    */   public int tileHeight;
/*  27:    */   public SpriteSheet tiles;
/*  28:    */   public int tilesAcross;
/*  29:    */   public int tilesDown;
/*  30: 49 */   private HashMap props = new HashMap();
/*  31: 51 */   protected int tileSpacing = 0;
/*  32: 53 */   protected int tileMargin = 0;
/*  33:    */   
/*  34:    */   public TileSet(TiledMap map, Element element, boolean loadImage)
/*  35:    */     throws SlickException
/*  36:    */   {
/*  37: 69 */     this.map = map;
/*  38: 70 */     this.name = element.getAttribute("name");
/*  39: 71 */     this.firstGID = Integer.parseInt(element.getAttribute("firstgid"));
/*  40: 72 */     String source = element.getAttribute("source");
/*  41: 74 */     if ((source != null) && (!source.equals(""))) {
/*  42:    */       try
/*  43:    */       {
/*  44: 76 */         InputStream in = ResourceLoader.getResourceAsStream(map
/*  45: 77 */           .getTilesLocation() + "/" + source);
/*  46: 78 */         DocumentBuilder builder = DocumentBuilderFactory.newInstance()
/*  47: 79 */           .newDocumentBuilder();
/*  48: 80 */         Document doc = builder.parse(in);
/*  49: 81 */         Element docElement = doc.getDocumentElement();
/*  50: 82 */         element = docElement;
/*  51:    */       }
/*  52:    */       catch (Exception e)
/*  53:    */       {
/*  54: 85 */         Log.error(e);
/*  55: 86 */         throw new SlickException(
/*  56: 87 */           "Unable to load or parse sourced tileset: " + 
/*  57: 88 */           this.map.tilesLocation + "/" + source);
/*  58:    */       }
/*  59:    */     }
/*  60: 91 */     String tileWidthString = element.getAttribute("tilewidth");
/*  61: 92 */     String tileHeightString = element.getAttribute("tileheight");
/*  62: 93 */     if ((tileWidthString.length() == 0) || (tileHeightString.length() == 0)) {
/*  63: 94 */       throw new SlickException(
/*  64: 95 */         "TiledMap requires that the map be created with tilesets that use a single image.  Check the WiKi for more complete information.");
/*  65:    */     }
/*  66: 98 */     this.tileWidth = Integer.parseInt(tileWidthString);
/*  67: 99 */     this.tileHeight = Integer.parseInt(tileHeightString);
/*  68:    */     
/*  69:101 */     String sv = element.getAttribute("spacing");
/*  70:102 */     if ((sv != null) && (!sv.equals(""))) {
/*  71:103 */       this.tileSpacing = Integer.parseInt(sv);
/*  72:    */     }
/*  73:106 */     String mv = element.getAttribute("margin");
/*  74:107 */     if ((mv != null) && (!mv.equals(""))) {
/*  75:108 */       this.tileMargin = Integer.parseInt(mv);
/*  76:    */     }
/*  77:111 */     NodeList list = element.getElementsByTagName("image");
/*  78:112 */     Element imageNode = (Element)list.item(0);
/*  79:113 */     String ref = imageNode.getAttribute("source");
/*  80:    */     
/*  81:115 */     Color trans = null;
/*  82:116 */     String t = imageNode.getAttribute("trans");
/*  83:117 */     if ((t != null) && (t.length() > 0))
/*  84:    */     {
/*  85:118 */       int c = Integer.parseInt(t, 16);
/*  86:    */       
/*  87:120 */       trans = new Color(c);
/*  88:    */     }
/*  89:123 */     if (loadImage)
/*  90:    */     {
/*  91:124 */       Image image = new Image(map.getTilesLocation() + "/" + ref, false, 
/*  92:125 */         2, trans);
/*  93:126 */       setTileSetImage(image);
/*  94:    */     }
/*  95:129 */     NodeList pElements = element.getElementsByTagName("tile");
/*  96:130 */     for (int i = 0; i < pElements.getLength(); i++)
/*  97:    */     {
/*  98:131 */       Element tileElement = (Element)pElements.item(i);
/*  99:    */       
/* 100:133 */       int id = Integer.parseInt(tileElement.getAttribute("id"));
/* 101:134 */       id += this.firstGID;
/* 102:135 */       Properties tileProps = new Properties();
/* 103:    */       
/* 104:137 */       Element propsElement = (Element)tileElement.getElementsByTagName(
/* 105:138 */         "properties").item(0);
/* 106:139 */       NodeList properties = propsElement.getElementsByTagName("property");
/* 107:140 */       for (int p = 0; p < properties.getLength(); p++)
/* 108:    */       {
/* 109:141 */         Element propElement = (Element)properties.item(p);
/* 110:    */         
/* 111:143 */         String name = propElement.getAttribute("name");
/* 112:144 */         String value = propElement.getAttribute("value");
/* 113:    */         
/* 114:146 */         tileProps.setProperty(name, value);
/* 115:    */       }
/* 116:149 */       this.props.put(new Integer(id), tileProps);
/* 117:    */     }
/* 118:    */   }
/* 119:    */   
/* 120:    */   public int getTileWidth()
/* 121:    */   {
/* 122:159 */     return this.tileWidth;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public int getTileHeight()
/* 126:    */   {
/* 127:168 */     return this.tileHeight;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public int getTileSpacing()
/* 131:    */   {
/* 132:177 */     return this.tileSpacing;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public int getTileMargin()
/* 136:    */   {
/* 137:186 */     return this.tileMargin;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public void setTileSetImage(Image image)
/* 141:    */   {
/* 142:196 */     this.tiles = new SpriteSheet(image, this.tileWidth, this.tileHeight, this.tileSpacing, 
/* 143:197 */       this.tileMargin);
/* 144:198 */     this.tilesAcross = this.tiles.getHorizontalCount();
/* 145:199 */     this.tilesDown = this.tiles.getVerticalCount();
/* 146:201 */     if (this.tilesAcross <= 0) {
/* 147:202 */       this.tilesAcross = 1;
/* 148:    */     }
/* 149:204 */     if (this.tilesDown <= 0) {
/* 150:205 */       this.tilesDown = 1;
/* 151:    */     }
/* 152:208 */     this.lastGID = (this.tilesAcross * this.tilesDown + this.firstGID - 1);
/* 153:    */   }
/* 154:    */   
/* 155:    */   public Properties getProperties(int globalID)
/* 156:    */   {
/* 157:220 */     return (Properties)this.props.get(new Integer(globalID));
/* 158:    */   }
/* 159:    */   
/* 160:    */   public int getTileX(int id)
/* 161:    */   {
/* 162:231 */     return id % this.tilesAcross;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public int getTileY(int id)
/* 166:    */   {
/* 167:242 */     return id / this.tilesAcross;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public void setLimit(int limit)
/* 171:    */   {
/* 172:252 */     this.lastGID = limit;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public boolean contains(int gid)
/* 176:    */   {
/* 177:263 */     return (gid >= this.firstGID) && (gid <= this.lastGID);
/* 178:    */   }
/* 179:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.tiled.TileSet
 * JD-Core Version:    0.7.0.1
 */