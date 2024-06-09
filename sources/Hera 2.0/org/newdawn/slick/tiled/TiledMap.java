/*      */ package org.newdawn.slick.tiled;
/*      */ 
/*      */ import java.io.ByteArrayInputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Properties;
/*      */ import javax.xml.parsers.DocumentBuilder;
/*      */ import javax.xml.parsers.DocumentBuilderFactory;
/*      */ import org.newdawn.slick.Image;
/*      */ import org.newdawn.slick.SlickException;
/*      */ import org.newdawn.slick.util.Log;
/*      */ import org.newdawn.slick.util.ResourceLoader;
/*      */ import org.w3c.dom.Document;
/*      */ import org.w3c.dom.Element;
/*      */ import org.w3c.dom.NodeList;
/*      */ import org.xml.sax.EntityResolver;
/*      */ import org.xml.sax.InputSource;
/*      */ import org.xml.sax.SAXException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class TiledMap
/*      */ {
/*      */   private static boolean headless;
/*      */   protected int width;
/*      */   protected int height;
/*      */   protected int tileWidth;
/*      */   protected int tileHeight;
/*      */   protected String tilesLocation;
/*      */   protected Properties props;
/*      */   
/*      */   private static void setHeadless(boolean h) {
/*   45 */     headless = h;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   64 */   protected ArrayList tileSets = new ArrayList();
/*      */   
/*   66 */   protected ArrayList layers = new ArrayList();
/*      */   
/*   68 */   protected ArrayList objectGroups = new ArrayList();
/*      */ 
/*      */ 
/*      */   
/*      */   protected static final int ORTHOGONAL = 1;
/*      */ 
/*      */ 
/*      */   
/*      */   protected static final int ISOMETRIC = 2;
/*      */ 
/*      */ 
/*      */   
/*      */   protected int orientation;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean loadTileSets = true;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TiledMap(String ref) throws SlickException {
/*   90 */     this(ref, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TiledMap(String ref, boolean loadTileSets) throws SlickException {
/*  104 */     this.loadTileSets = loadTileSets;
/*  105 */     ref = ref.replace('\\', '/');
/*  106 */     load(ResourceLoader.getResourceAsStream(ref), ref.substring(0, ref.lastIndexOf("/")));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TiledMap(String ref, String tileSetsLocation) throws SlickException {
/*  122 */     load(ResourceLoader.getResourceAsStream(ref), tileSetsLocation);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TiledMap(InputStream in) throws SlickException {
/*  134 */     load(in, "");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TiledMap(InputStream in, String tileSetsLocation) throws SlickException {
/*  149 */     load(in, tileSetsLocation);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getTilesLocation() {
/*  159 */     return this.tilesLocation;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getLayerIndex(String name) {
/*  170 */     int idx = 0;
/*      */     
/*  172 */     for (int i = 0; i < this.layers.size(); i++) {
/*  173 */       Layer layer = this.layers.get(i);
/*      */       
/*  175 */       if (layer.name.equals(name)) {
/*  176 */         return i;
/*      */       }
/*      */     } 
/*      */     
/*  180 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Image getTileImage(int x, int y, int layerIndex) {
/*  197 */     Layer layer = this.layers.get(layerIndex);
/*      */     
/*  199 */     int tileSetIndex = layer.data[x][y][0];
/*  200 */     if (tileSetIndex >= 0 && tileSetIndex < this.tileSets.size()) {
/*  201 */       TileSet tileSet = this.tileSets.get(tileSetIndex);
/*      */       
/*  203 */       int sheetX = tileSet.getTileX(layer.data[x][y][1]);
/*  204 */       int sheetY = tileSet.getTileY(layer.data[x][y][1]);
/*      */       
/*  206 */       return tileSet.tiles.getSprite(sheetX, sheetY);
/*      */     } 
/*      */     
/*  209 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getWidth() {
/*  218 */     return this.width;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getHeight() {
/*  227 */     return this.height;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getTileHeight() {
/*  236 */     return this.tileHeight;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getTileWidth() {
/*  245 */     return this.tileWidth;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getTileId(int x, int y, int layerIndex) {
/*  260 */     Layer layer = this.layers.get(layerIndex);
/*  261 */     return layer.getTileID(x, y);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTileId(int x, int y, int layerIndex, int tileid) {
/*  277 */     Layer layer = this.layers.get(layerIndex);
/*  278 */     layer.setTileID(x, y, tileid);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getMapProperty(String propertyName, String def) {
/*  294 */     if (this.props == null)
/*  295 */       return def; 
/*  296 */     return this.props.getProperty(propertyName, def);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getLayerProperty(int layerIndex, String propertyName, String def) {
/*  315 */     Layer layer = this.layers.get(layerIndex);
/*  316 */     if (layer == null || layer.props == null)
/*  317 */       return def; 
/*  318 */     return layer.props.getProperty(propertyName, def);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getTileProperty(int tileID, String propertyName, String def) {
/*  336 */     if (tileID == 0) {
/*  337 */       return def;
/*      */     }
/*      */     
/*  340 */     TileSet set = findTileSet(tileID);
/*      */     
/*  342 */     Properties props = set.getProperties(tileID);
/*  343 */     if (props == null) {
/*  344 */       return def;
/*      */     }
/*  346 */     return props.getProperty(propertyName, def);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void render(int x, int y) {
/*  358 */     render(x, y, 0, 0, this.width, this.height, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void render(int x, int y, int layer) {
/*  372 */     render(x, y, 0, 0, getWidth(), getHeight(), layer, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void render(int x, int y, int sx, int sy, int width, int height) {
/*  392 */     render(x, y, sx, sy, width, height, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void render(int x, int y, int sx, int sy, int width, int height, int l, boolean lineByLine) {
/*      */     int ty;
/*  419 */     Layer layer = this.layers.get(l);
/*      */     
/*  421 */     switch (this.orientation) {
/*      */       case 1:
/*  423 */         for (ty = 0; ty < height; ty++) {
/*  424 */           layer.render(x, y, sx, sy, width, ty, lineByLine, this.tileWidth, this.tileHeight);
/*      */         }
/*      */         break;
/*      */       
/*      */       case 2:
/*  429 */         renderIsometricMap(x, y, sx, sy, width, height, layer, lineByLine);
/*      */         break;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void render(int x, int y, int sx, int sy, int width, int height, boolean lineByLine) {
/*      */     int ty;
/*  458 */     switch (this.orientation) {
/*      */       case 1:
/*  460 */         for (ty = 0; ty < height; ty++) {
/*  461 */           for (int i = 0; i < this.layers.size(); i++) {
/*  462 */             Layer layer = this.layers.get(i);
/*  463 */             layer.render(x, y, sx, sy, width, ty, lineByLine, this.tileWidth, this.tileHeight);
/*      */           } 
/*      */         } 
/*      */         break;
/*      */       
/*      */       case 2:
/*  469 */         renderIsometricMap(x, y, sx, sy, width, height, null, lineByLine);
/*      */         break;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void renderIsometricMap(int x, int y, int sx, int sy, int width, int height, Layer layer, boolean lineByLine) {
/*  504 */     ArrayList<Layer> drawLayers = this.layers;
/*  505 */     if (layer != null) {
/*  506 */       drawLayers = new ArrayList();
/*  507 */       drawLayers.add(layer);
/*      */     } 
/*      */     
/*  510 */     int maxCount = width * height;
/*  511 */     int allCount = 0;
/*      */     
/*  513 */     boolean allProcessed = false;
/*      */     
/*  515 */     int initialLineX = x;
/*  516 */     int initialLineY = y;
/*      */     
/*  518 */     int startLineTileX = 0;
/*  519 */     int startLineTileY = 0;
/*  520 */     while (!allProcessed) {
/*      */       
/*  522 */       int currentTileX = startLineTileX;
/*  523 */       int currentTileY = startLineTileY;
/*  524 */       int currentLineX = initialLineX;
/*      */       
/*  526 */       int min = 0;
/*  527 */       if (height > width) {
/*  528 */         min = (startLineTileY < width - 1) ? startLineTileY : ((width - currentTileX < height) ? (width - currentTileX - 1) : (width - 1));
/*      */       }
/*      */       else {
/*      */         
/*  532 */         min = (startLineTileY < height - 1) ? startLineTileY : ((width - currentTileX < height) ? (width - currentTileX - 1) : (height - 1));
/*      */       } 
/*      */ 
/*      */       
/*  536 */       for (int burner = 0; burner <= min; currentTileX++, currentTileY--, burner++) {
/*  537 */         for (int layerIdx = 0; layerIdx < drawLayers.size(); layerIdx++) {
/*  538 */           Layer currentLayer = drawLayers.get(layerIdx);
/*  539 */           currentLayer.render(currentLineX, initialLineY, currentTileX, currentTileY, 1, 0, lineByLine, this.tileWidth, this.tileHeight);
/*      */         } 
/*      */ 
/*      */         
/*  543 */         currentLineX += this.tileWidth;
/*      */         
/*  545 */         allCount++;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  551 */       if (startLineTileY < height - 1) {
/*  552 */         startLineTileY++;
/*  553 */         initialLineX -= this.tileWidth / 2;
/*  554 */         initialLineY += this.tileHeight / 2;
/*      */       } else {
/*  556 */         startLineTileX++;
/*  557 */         initialLineX += this.tileWidth / 2;
/*  558 */         initialLineY += this.tileHeight / 2;
/*      */       } 
/*      */       
/*  561 */       if (allCount >= maxCount) {
/*  562 */         allProcessed = true;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getLayerCount() {
/*  572 */     return this.layers.size();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int parseInt(String value) {
/*      */     try {
/*  584 */       return Integer.parseInt(value);
/*  585 */     } catch (NumberFormatException e) {
/*  586 */       return 0;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void load(InputStream in, String tileSetsLocation) throws SlickException {
/*  602 */     this.tilesLocation = tileSetsLocation;
/*      */     
/*      */     try {
/*  605 */       DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
/*      */       
/*  607 */       factory.setValidating(false);
/*  608 */       DocumentBuilder builder = factory.newDocumentBuilder();
/*  609 */       builder.setEntityResolver(new EntityResolver()
/*      */           {
/*      */             public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
/*  612 */               return new InputSource(new ByteArrayInputStream(new byte[0]));
/*      */             }
/*      */           });
/*      */ 
/*      */       
/*  617 */       Document doc = builder.parse(in);
/*  618 */       Element docElement = doc.getDocumentElement();
/*      */       
/*  620 */       if (docElement.getAttribute("orientation").equals("orthogonal")) {
/*  621 */         this.orientation = 1;
/*      */       } else {
/*  623 */         this.orientation = 2;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  630 */       this.width = parseInt(docElement.getAttribute("width"));
/*  631 */       this.height = parseInt(docElement.getAttribute("height"));
/*  632 */       this.tileWidth = parseInt(docElement.getAttribute("tilewidth"));
/*  633 */       this.tileHeight = parseInt(docElement.getAttribute("tileheight"));
/*      */ 
/*      */       
/*  636 */       Element propsElement = (Element)docElement.getElementsByTagName("properties").item(0);
/*      */       
/*  638 */       if (propsElement != null) {
/*  639 */         NodeList properties = propsElement.getElementsByTagName("property");
/*      */         
/*  641 */         if (properties != null) {
/*  642 */           this.props = new Properties();
/*  643 */           for (int p = 0; p < properties.getLength(); p++) {
/*  644 */             Element propElement = (Element)properties.item(p);
/*      */             
/*  646 */             String name = propElement.getAttribute("name");
/*  647 */             String value = propElement.getAttribute("value");
/*  648 */             this.props.setProperty(name, value);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/*  653 */       if (this.loadTileSets) {
/*  654 */         TileSet tileSet = null;
/*  655 */         TileSet lastSet = null;
/*      */         
/*  657 */         NodeList setNodes = docElement.getElementsByTagName("tileset");
/*  658 */         for (int k = 0; k < setNodes.getLength(); k++) {
/*  659 */           Element current = (Element)setNodes.item(k);
/*      */           
/*  661 */           tileSet = new TileSet(this, current, !headless);
/*  662 */           tileSet.index = k;
/*      */           
/*  664 */           if (lastSet != null) {
/*  665 */             lastSet.setLimit(tileSet.firstGID - 1);
/*      */           }
/*  667 */           lastSet = tileSet;
/*      */           
/*  669 */           this.tileSets.add(tileSet);
/*      */         } 
/*      */       } 
/*      */       
/*  673 */       NodeList layerNodes = docElement.getElementsByTagName("layer");
/*  674 */       for (int i = 0; i < layerNodes.getLength(); i++) {
/*  675 */         Element current = (Element)layerNodes.item(i);
/*  676 */         Layer layer = new Layer(this, current);
/*  677 */         layer.index = i;
/*      */         
/*  679 */         this.layers.add(layer);
/*      */       } 
/*      */ 
/*      */       
/*  683 */       NodeList objectGroupNodes = docElement.getElementsByTagName("objectgroup");
/*      */ 
/*      */       
/*  686 */       for (int j = 0; j < objectGroupNodes.getLength(); j++) {
/*  687 */         Element current = (Element)objectGroupNodes.item(j);
/*  688 */         ObjectGroup objectGroup = new ObjectGroup(current);
/*  689 */         objectGroup.index = j;
/*      */         
/*  691 */         this.objectGroups.add(objectGroup);
/*      */       } 
/*  693 */     } catch (Exception e) {
/*  694 */       Log.error(e);
/*  695 */       throw new SlickException("Failed to parse tilemap", e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getTileSetCount() {
/*  705 */     return this.tileSets.size();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TileSet getTileSet(int index) {
/*  716 */     return this.tileSets.get(index);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TileSet getTileSetByGID(int gid) {
/*  727 */     for (int i = 0; i < this.tileSets.size(); i++) {
/*  728 */       TileSet set = this.tileSets.get(i);
/*      */       
/*  730 */       if (set.contains(gid)) {
/*  731 */         return set;
/*      */       }
/*      */     } 
/*      */     
/*  735 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TileSet findTileSet(int gid) {
/*  747 */     for (int i = 0; i < this.tileSets.size(); i++) {
/*  748 */       TileSet set = this.tileSets.get(i);
/*      */       
/*  750 */       if (set.contains(gid)) {
/*  751 */         return set;
/*      */       }
/*      */     } 
/*      */     
/*  755 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void renderedLine(int visualY, int mapY, int layer) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getObjectGroupCount() {
/*  778 */     return this.objectGroups.size();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getObjectCount(int groupID) {
/*  790 */     if (groupID >= 0 && groupID < this.objectGroups.size()) {
/*  791 */       ObjectGroup grp = this.objectGroups.get(groupID);
/*  792 */       return grp.objects.size();
/*      */     } 
/*  794 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getObjectName(int groupID, int objectID) {
/*  807 */     if (groupID >= 0 && groupID < this.objectGroups.size()) {
/*  808 */       ObjectGroup grp = this.objectGroups.get(groupID);
/*  809 */       if (objectID >= 0 && objectID < grp.objects.size()) {
/*  810 */         GroupObject object = grp.objects.get(objectID);
/*  811 */         return object.name;
/*      */       } 
/*      */     } 
/*  814 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getObjectType(int groupID, int objectID) {
/*  827 */     if (groupID >= 0 && groupID < this.objectGroups.size()) {
/*  828 */       ObjectGroup grp = this.objectGroups.get(groupID);
/*  829 */       if (objectID >= 0 && objectID < grp.objects.size()) {
/*  830 */         GroupObject object = grp.objects.get(objectID);
/*  831 */         return object.type;
/*      */       } 
/*      */     } 
/*  834 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getObjectX(int groupID, int objectID) {
/*  847 */     if (groupID >= 0 && groupID < this.objectGroups.size()) {
/*  848 */       ObjectGroup grp = this.objectGroups.get(groupID);
/*  849 */       if (objectID >= 0 && objectID < grp.objects.size()) {
/*  850 */         GroupObject object = grp.objects.get(objectID);
/*  851 */         return object.x;
/*      */       } 
/*      */     } 
/*  854 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getObjectY(int groupID, int objectID) {
/*  867 */     if (groupID >= 0 && groupID < this.objectGroups.size()) {
/*  868 */       ObjectGroup grp = this.objectGroups.get(groupID);
/*  869 */       if (objectID >= 0 && objectID < grp.objects.size()) {
/*  870 */         GroupObject object = grp.objects.get(objectID);
/*  871 */         return object.y;
/*      */       } 
/*      */     } 
/*  874 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getObjectWidth(int groupID, int objectID) {
/*  887 */     if (groupID >= 0 && groupID < this.objectGroups.size()) {
/*  888 */       ObjectGroup grp = this.objectGroups.get(groupID);
/*  889 */       if (objectID >= 0 && objectID < grp.objects.size()) {
/*  890 */         GroupObject object = grp.objects.get(objectID);
/*  891 */         return object.width;
/*      */       } 
/*      */     } 
/*  894 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getObjectHeight(int groupID, int objectID) {
/*  907 */     if (groupID >= 0 && groupID < this.objectGroups.size()) {
/*  908 */       ObjectGroup grp = this.objectGroups.get(groupID);
/*  909 */       if (objectID >= 0 && objectID < grp.objects.size()) {
/*  910 */         GroupObject object = grp.objects.get(objectID);
/*  911 */         return object.height;
/*      */       } 
/*      */     } 
/*  914 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getObjectImage(int groupID, int objectID) {
/*  927 */     if (groupID >= 0 && groupID < this.objectGroups.size()) {
/*  928 */       ObjectGroup grp = this.objectGroups.get(groupID);
/*  929 */       if (objectID >= 0 && objectID < grp.objects.size()) {
/*  930 */         GroupObject object = grp.objects.get(objectID);
/*      */         
/*  932 */         if (object == null) {
/*  933 */           return null;
/*      */         }
/*      */         
/*  936 */         return object.image;
/*      */       } 
/*      */     } 
/*      */     
/*  940 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getObjectProperty(int groupID, int objectID, String propertyName, String def) {
/*  960 */     if (groupID >= 0 && groupID < this.objectGroups.size()) {
/*  961 */       ObjectGroup grp = this.objectGroups.get(groupID);
/*  962 */       if (objectID >= 0 && objectID < grp.objects.size()) {
/*  963 */         GroupObject object = grp.objects.get(objectID);
/*      */         
/*  965 */         if (object == null) {
/*  966 */           return def;
/*      */         }
/*  968 */         if (object.props == null) {
/*  969 */           return def;
/*      */         }
/*      */         
/*  972 */         return object.props.getProperty(propertyName, def);
/*      */       } 
/*      */     } 
/*  975 */     return def;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected class ObjectGroup
/*      */   {
/*      */     public int index;
/*      */ 
/*      */ 
/*      */     
/*      */     public String name;
/*      */ 
/*      */ 
/*      */     
/*      */     public ArrayList objects;
/*      */ 
/*      */ 
/*      */     
/*      */     public int width;
/*      */ 
/*      */ 
/*      */     
/*      */     public int height;
/*      */ 
/*      */     
/*      */     public Properties props;
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectGroup(Element element) throws SlickException {
/* 1007 */       this.name = element.getAttribute("name");
/* 1008 */       this.width = Integer.parseInt(element.getAttribute("width"));
/* 1009 */       this.height = Integer.parseInt(element.getAttribute("height"));
/* 1010 */       this.objects = new ArrayList();
/*      */ 
/*      */       
/* 1013 */       Element propsElement = (Element)element.getElementsByTagName("properties").item(0);
/*      */       
/* 1015 */       if (propsElement != null) {
/* 1016 */         NodeList properties = propsElement.getElementsByTagName("property");
/*      */         
/* 1018 */         if (properties != null) {
/* 1019 */           this.props = new Properties();
/* 1020 */           for (int p = 0; p < properties.getLength(); p++) {
/* 1021 */             Element propElement = (Element)properties.item(p);
/*      */             
/* 1023 */             String name = propElement.getAttribute("name");
/* 1024 */             String value = propElement.getAttribute("value");
/* 1025 */             this.props.setProperty(name, value);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 1030 */       NodeList objectNodes = element.getElementsByTagName("object");
/* 1031 */       for (int i = 0; i < objectNodes.getLength(); i++) {
/* 1032 */         Element objElement = (Element)objectNodes.item(i);
/* 1033 */         TiledMap.GroupObject object = new TiledMap.GroupObject(objElement);
/* 1034 */         object.index = i;
/* 1035 */         this.objects.add(object);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected class GroupObject
/*      */   {
/*      */     public int index;
/*      */ 
/*      */ 
/*      */     
/*      */     public String name;
/*      */ 
/*      */     
/*      */     public String type;
/*      */ 
/*      */     
/*      */     public int x;
/*      */ 
/*      */     
/*      */     public int y;
/*      */ 
/*      */     
/*      */     public int width;
/*      */ 
/*      */     
/*      */     public int height;
/*      */ 
/*      */     
/*      */     private String image;
/*      */ 
/*      */     
/*      */     public Properties props;
/*      */ 
/*      */ 
/*      */     
/*      */     public GroupObject(Element element) throws SlickException {
/* 1075 */       this.name = element.getAttribute("name");
/* 1076 */       this.type = element.getAttribute("type");
/* 1077 */       this.x = Integer.parseInt(element.getAttribute("x"));
/* 1078 */       this.y = Integer.parseInt(element.getAttribute("y"));
/* 1079 */       this.width = Integer.parseInt(element.getAttribute("width"));
/* 1080 */       this.height = Integer.parseInt(element.getAttribute("height"));
/*      */       
/* 1082 */       Element imageElement = (Element)element.getElementsByTagName("image").item(0);
/*      */       
/* 1084 */       if (imageElement != null) {
/* 1085 */         this.image = imageElement.getAttribute("source");
/*      */       }
/*      */ 
/*      */       
/* 1089 */       Element propsElement = (Element)element.getElementsByTagName("properties").item(0);
/*      */       
/* 1091 */       if (propsElement != null) {
/* 1092 */         NodeList properties = propsElement.getElementsByTagName("property");
/*      */         
/* 1094 */         if (properties != null) {
/* 1095 */           this.props = new Properties();
/* 1096 */           for (int p = 0; p < properties.getLength(); p++) {
/* 1097 */             Element propElement = (Element)properties.item(p);
/*      */             
/* 1099 */             String name = propElement.getAttribute("name");
/* 1100 */             String value = propElement.getAttribute("value");
/* 1101 */             this.props.setProperty(name, value);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\tiled\TiledMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */