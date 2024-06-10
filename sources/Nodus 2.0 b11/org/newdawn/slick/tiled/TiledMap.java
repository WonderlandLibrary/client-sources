/*    1:     */ package org.newdawn.slick.tiled;
/*    2:     */ 
/*    3:     */ import java.io.ByteArrayInputStream;
/*    4:     */ import java.io.IOException;
/*    5:     */ import java.io.InputStream;
/*    6:     */ import java.util.ArrayList;
/*    7:     */ import java.util.Properties;
/*    8:     */ import javax.xml.parsers.DocumentBuilder;
/*    9:     */ import javax.xml.parsers.DocumentBuilderFactory;
/*   10:     */ import org.newdawn.slick.Image;
/*   11:     */ import org.newdawn.slick.SlickException;
/*   12:     */ import org.newdawn.slick.SpriteSheet;
/*   13:     */ import org.newdawn.slick.util.Log;
/*   14:     */ import org.newdawn.slick.util.ResourceLoader;
/*   15:     */ import org.w3c.dom.Document;
/*   16:     */ import org.w3c.dom.Element;
/*   17:     */ import org.w3c.dom.NodeList;
/*   18:     */ import org.xml.sax.EntityResolver;
/*   19:     */ import org.xml.sax.InputSource;
/*   20:     */ import org.xml.sax.SAXException;
/*   21:     */ 
/*   22:     */ public class TiledMap
/*   23:     */ {
/*   24:     */   private static boolean headless;
/*   25:     */   protected int width;
/*   26:     */   protected int height;
/*   27:     */   protected int tileWidth;
/*   28:     */   protected int tileHeight;
/*   29:     */   protected String tilesLocation;
/*   30:     */   protected Properties props;
/*   31:     */   
/*   32:     */   private static void setHeadless(boolean h)
/*   33:     */   {
/*   34:  45 */     headless = h;
/*   35:     */   }
/*   36:     */   
/*   37:  64 */   protected ArrayList tileSets = new ArrayList();
/*   38:  66 */   protected ArrayList layers = new ArrayList();
/*   39:  68 */   protected ArrayList objectGroups = new ArrayList();
/*   40:     */   protected static final int ORTHOGONAL = 1;
/*   41:     */   protected static final int ISOMETRIC = 2;
/*   42:     */   protected int orientation;
/*   43:  79 */   private boolean loadTileSets = true;
/*   44:     */   
/*   45:     */   public TiledMap(String ref)
/*   46:     */     throws SlickException
/*   47:     */   {
/*   48:  90 */     this(ref, true);
/*   49:     */   }
/*   50:     */   
/*   51:     */   public TiledMap(String ref, boolean loadTileSets)
/*   52:     */     throws SlickException
/*   53:     */   {
/*   54: 104 */     this.loadTileSets = loadTileSets;
/*   55: 105 */     ref = ref.replace('\\', '/');
/*   56: 106 */     load(ResourceLoader.getResourceAsStream(ref), 
/*   57: 107 */       ref.substring(0, ref.lastIndexOf("/")));
/*   58:     */   }
/*   59:     */   
/*   60:     */   public TiledMap(String ref, String tileSetsLocation)
/*   61:     */     throws SlickException
/*   62:     */   {
/*   63: 122 */     load(ResourceLoader.getResourceAsStream(ref), tileSetsLocation);
/*   64:     */   }
/*   65:     */   
/*   66:     */   public TiledMap(InputStream in)
/*   67:     */     throws SlickException
/*   68:     */   {
/*   69: 134 */     load(in, "");
/*   70:     */   }
/*   71:     */   
/*   72:     */   public TiledMap(InputStream in, String tileSetsLocation)
/*   73:     */     throws SlickException
/*   74:     */   {
/*   75: 149 */     load(in, tileSetsLocation);
/*   76:     */   }
/*   77:     */   
/*   78:     */   public String getTilesLocation()
/*   79:     */   {
/*   80: 159 */     return this.tilesLocation;
/*   81:     */   }
/*   82:     */   
/*   83:     */   public int getLayerIndex(String name)
/*   84:     */   {
/*   85: 170 */     int idx = 0;
/*   86: 172 */     for (int i = 0; i < this.layers.size(); i++)
/*   87:     */     {
/*   88: 173 */       Layer layer = (Layer)this.layers.get(i);
/*   89: 175 */       if (layer.name.equals(name)) {
/*   90: 176 */         return i;
/*   91:     */       }
/*   92:     */     }
/*   93: 180 */     return -1;
/*   94:     */   }
/*   95:     */   
/*   96:     */   public Image getTileImage(int x, int y, int layerIndex)
/*   97:     */   {
/*   98: 197 */     Layer layer = (Layer)this.layers.get(layerIndex);
/*   99:     */     
/*  100: 199 */     int tileSetIndex = layer.data[x][y][0];
/*  101: 200 */     if ((tileSetIndex >= 0) && (tileSetIndex < this.tileSets.size()))
/*  102:     */     {
/*  103: 201 */       TileSet tileSet = (TileSet)this.tileSets.get(tileSetIndex);
/*  104:     */       
/*  105: 203 */       int sheetX = tileSet.getTileX(layer.data[x][y][1]);
/*  106: 204 */       int sheetY = tileSet.getTileY(layer.data[x][y][1]);
/*  107:     */       
/*  108: 206 */       return tileSet.tiles.getSprite(sheetX, sheetY);
/*  109:     */     }
/*  110: 209 */     return null;
/*  111:     */   }
/*  112:     */   
/*  113:     */   public int getWidth()
/*  114:     */   {
/*  115: 218 */     return this.width;
/*  116:     */   }
/*  117:     */   
/*  118:     */   public int getHeight()
/*  119:     */   {
/*  120: 227 */     return this.height;
/*  121:     */   }
/*  122:     */   
/*  123:     */   public int getTileHeight()
/*  124:     */   {
/*  125: 236 */     return this.tileHeight;
/*  126:     */   }
/*  127:     */   
/*  128:     */   public int getTileWidth()
/*  129:     */   {
/*  130: 245 */     return this.tileWidth;
/*  131:     */   }
/*  132:     */   
/*  133:     */   public int getTileId(int x, int y, int layerIndex)
/*  134:     */   {
/*  135: 260 */     Layer layer = (Layer)this.layers.get(layerIndex);
/*  136: 261 */     return layer.getTileID(x, y);
/*  137:     */   }
/*  138:     */   
/*  139:     */   public void setTileId(int x, int y, int layerIndex, int tileid)
/*  140:     */   {
/*  141: 277 */     Layer layer = (Layer)this.layers.get(layerIndex);
/*  142: 278 */     layer.setTileID(x, y, tileid);
/*  143:     */   }
/*  144:     */   
/*  145:     */   public String getMapProperty(String propertyName, String def)
/*  146:     */   {
/*  147: 294 */     if (this.props == null) {
/*  148: 295 */       return def;
/*  149:     */     }
/*  150: 296 */     return this.props.getProperty(propertyName, def);
/*  151:     */   }
/*  152:     */   
/*  153:     */   public String getLayerProperty(int layerIndex, String propertyName, String def)
/*  154:     */   {
/*  155: 315 */     Layer layer = (Layer)this.layers.get(layerIndex);
/*  156: 316 */     if ((layer == null) || (layer.props == null)) {
/*  157: 317 */       return def;
/*  158:     */     }
/*  159: 318 */     return layer.props.getProperty(propertyName, def);
/*  160:     */   }
/*  161:     */   
/*  162:     */   public String getTileProperty(int tileID, String propertyName, String def)
/*  163:     */   {
/*  164: 336 */     if (tileID == 0) {
/*  165: 337 */       return def;
/*  166:     */     }
/*  167: 340 */     TileSet set = findTileSet(tileID);
/*  168:     */     
/*  169: 342 */     Properties props = set.getProperties(tileID);
/*  170: 343 */     if (props == null) {
/*  171: 344 */       return def;
/*  172:     */     }
/*  173: 346 */     return props.getProperty(propertyName, def);
/*  174:     */   }
/*  175:     */   
/*  176:     */   public void render(int x, int y)
/*  177:     */   {
/*  178: 358 */     render(x, y, 0, 0, this.width, this.height, false);
/*  179:     */   }
/*  180:     */   
/*  181:     */   public void render(int x, int y, int layer)
/*  182:     */   {
/*  183: 372 */     render(x, y, 0, 0, getWidth(), getHeight(), layer, false);
/*  184:     */   }
/*  185:     */   
/*  186:     */   public void render(int x, int y, int sx, int sy, int width, int height)
/*  187:     */   {
/*  188: 392 */     render(x, y, sx, sy, width, height, false);
/*  189:     */   }
/*  190:     */   
/*  191:     */   public void render(int x, int y, int sx, int sy, int width, int height, int l, boolean lineByLine)
/*  192:     */   {
/*  193: 419 */     Layer layer = (Layer)this.layers.get(l);
/*  194: 421 */     switch (this.orientation)
/*  195:     */     {
/*  196:     */     case 1: 
/*  197: 423 */       for (int ty = 0; ty < height; ty++) {
/*  198: 424 */         layer.render(x, y, sx, sy, width, ty, lineByLine, this.tileWidth, 
/*  199: 425 */           this.tileHeight);
/*  200:     */       }
/*  201: 427 */       break;
/*  202:     */     case 2: 
/*  203: 429 */       renderIsometricMap(x, y, sx, sy, width, height, layer, lineByLine);
/*  204:     */     }
/*  205:     */   }
/*  206:     */   
/*  207:     */   public void render(int x, int y, int sx, int sy, int width, int height, boolean lineByLine)
/*  208:     */   {
/*  209: 458 */     switch (this.orientation)
/*  210:     */     {
/*  211:     */     case 1: 
/*  212: 460 */       for (int ty = 0; ty < height; ty++) {
/*  213: 461 */         for (int i = 0; i < this.layers.size(); i++)
/*  214:     */         {
/*  215: 462 */           Layer layer = (Layer)this.layers.get(i);
/*  216: 463 */           layer.render(x, y, sx, sy, width, ty, lineByLine, 
/*  217: 464 */             this.tileWidth, this.tileHeight);
/*  218:     */         }
/*  219:     */       }
/*  220: 467 */       break;
/*  221:     */     case 2: 
/*  222: 469 */       renderIsometricMap(x, y, sx, sy, width, height, null, lineByLine);
/*  223:     */     }
/*  224:     */   }
/*  225:     */   
/*  226:     */   protected void renderIsometricMap(int x, int y, int sx, int sy, int width, int height, Layer layer, boolean lineByLine)
/*  227:     */   {
/*  228: 504 */     ArrayList drawLayers = this.layers;
/*  229: 505 */     if (layer != null)
/*  230:     */     {
/*  231: 506 */       drawLayers = new ArrayList();
/*  232: 507 */       drawLayers.add(layer);
/*  233:     */     }
/*  234: 510 */     int maxCount = width * height;
/*  235: 511 */     int allCount = 0;
/*  236:     */     
/*  237: 513 */     boolean allProcessed = false;
/*  238:     */     
/*  239: 515 */     int initialLineX = x;
/*  240: 516 */     int initialLineY = y;
/*  241:     */     
/*  242: 518 */     int startLineTileX = 0;
/*  243: 519 */     int startLineTileY = 0;
/*  244: 520 */     while (!allProcessed)
/*  245:     */     {
/*  246: 522 */       int currentTileX = startLineTileX;
/*  247: 523 */       int currentTileY = startLineTileY;
/*  248: 524 */       int currentLineX = initialLineX;
/*  249:     */       
/*  250: 526 */       int min = 0;
/*  251: 527 */       if (height > width) {
/*  252: 528 */         min = 
/*  253: 529 */           width - currentTileX < height ? width - currentTileX - 1 : startLineTileY < width - 1 ? startLineTileY : 
/*  254: 530 */           width - 1;
/*  255:     */       } else {
/*  256: 532 */         min = 
/*  257: 533 */           width - currentTileX < height ? width - currentTileX - 1 : startLineTileY < height - 1 ? startLineTileY : 
/*  258: 534 */           height - 1;
/*  259:     */       }
/*  260: 536 */       for (int burner = 0; burner <= min; burner++)
/*  261:     */       {
/*  262: 537 */         for (int layerIdx = 0; layerIdx < drawLayers.size(); layerIdx++)
/*  263:     */         {
/*  264: 538 */           Layer currentLayer = (Layer)drawLayers.get(layerIdx);
/*  265: 539 */           currentLayer.render(currentLineX, initialLineY, 
/*  266: 540 */             currentTileX, currentTileY, 1, 0, lineByLine, 
/*  267: 541 */             this.tileWidth, this.tileHeight);
/*  268:     */         }
/*  269: 543 */         currentLineX += this.tileWidth;
/*  270:     */         
/*  271: 545 */         allCount++;currentTileX++;currentTileY--;
/*  272:     */       }
/*  273: 551 */       if (startLineTileY < height - 1)
/*  274:     */       {
/*  275: 552 */         startLineTileY++;
/*  276: 553 */         initialLineX -= this.tileWidth / 2;
/*  277: 554 */         initialLineY += this.tileHeight / 2;
/*  278:     */       }
/*  279:     */       else
/*  280:     */       {
/*  281: 556 */         startLineTileX++;
/*  282: 557 */         initialLineX += this.tileWidth / 2;
/*  283: 558 */         initialLineY += this.tileHeight / 2;
/*  284:     */       }
/*  285: 561 */       if (allCount >= maxCount) {
/*  286: 562 */         allProcessed = true;
/*  287:     */       }
/*  288:     */     }
/*  289:     */   }
/*  290:     */   
/*  291:     */   public int getLayerCount()
/*  292:     */   {
/*  293: 572 */     return this.layers.size();
/*  294:     */   }
/*  295:     */   
/*  296:     */   private int parseInt(String value)
/*  297:     */   {
/*  298:     */     try
/*  299:     */     {
/*  300: 584 */       return Integer.parseInt(value);
/*  301:     */     }
/*  302:     */     catch (NumberFormatException e) {}
/*  303: 586 */     return 0;
/*  304:     */   }
/*  305:     */   
/*  306:     */   private void load(InputStream in, String tileSetsLocation)
/*  307:     */     throws SlickException
/*  308:     */   {
/*  309: 602 */     this.tilesLocation = tileSetsLocation;
/*  310:     */     try
/*  311:     */     {
/*  312: 605 */       DocumentBuilderFactory factory = 
/*  313: 606 */         DocumentBuilderFactory.newInstance();
/*  314: 607 */       factory.setValidating(false);
/*  315: 608 */       DocumentBuilder builder = factory.newDocumentBuilder();
/*  316: 609 */       builder.setEntityResolver(new EntityResolver()
/*  317:     */       {
/*  318:     */         public InputSource resolveEntity(String publicId, String systemId)
/*  319:     */           throws SAXException, IOException
/*  320:     */         {
/*  321: 612 */           return new InputSource(
/*  322: 613 */             new ByteArrayInputStream(new byte[0]));
/*  323:     */         }
/*  324: 616 */       });
/*  325: 617 */       Document doc = builder.parse(in);
/*  326: 618 */       Element docElement = doc.getDocumentElement();
/*  327: 620 */       if (docElement.getAttribute("orientation").equals("orthogonal")) {
/*  328: 621 */         this.orientation = 1;
/*  329:     */       } else {
/*  330: 623 */         this.orientation = 2;
/*  331:     */       }
/*  332: 630 */       this.width = parseInt(docElement.getAttribute("width"));
/*  333: 631 */       this.height = parseInt(docElement.getAttribute("height"));
/*  334: 632 */       this.tileWidth = parseInt(docElement.getAttribute("tilewidth"));
/*  335: 633 */       this.tileHeight = parseInt(docElement.getAttribute("tileheight"));
/*  336:     */       
/*  337:     */ 
/*  338: 636 */       Element propsElement = (Element)docElement.getElementsByTagName(
/*  339: 637 */         "properties").item(0);
/*  340: 638 */       if (propsElement != null)
/*  341:     */       {
/*  342: 639 */         NodeList properties = propsElement
/*  343: 640 */           .getElementsByTagName("property");
/*  344: 641 */         if (properties != null)
/*  345:     */         {
/*  346: 642 */           this.props = new Properties();
/*  347: 643 */           for (int p = 0; p < properties.getLength(); p++)
/*  348:     */           {
/*  349: 644 */             Element propElement = (Element)properties.item(p);
/*  350:     */             
/*  351: 646 */             String name = propElement.getAttribute("name");
/*  352: 647 */             String value = propElement.getAttribute("value");
/*  353: 648 */             this.props.setProperty(name, value);
/*  354:     */           }
/*  355:     */         }
/*  356:     */       }
/*  357: 653 */       if (this.loadTileSets)
/*  358:     */       {
/*  359: 654 */         TileSet tileSet = null;
/*  360: 655 */         TileSet lastSet = null;
/*  361:     */         
/*  362: 657 */         NodeList setNodes = docElement.getElementsByTagName("tileset");
/*  363: 658 */         for (int i = 0; i < setNodes.getLength(); i++)
/*  364:     */         {
/*  365: 659 */           Element current = (Element)setNodes.item(i);
/*  366:     */           
/*  367: 661 */           tileSet = new TileSet(this, current, !headless);
/*  368: 662 */           tileSet.index = i;
/*  369: 664 */           if (lastSet != null) {
/*  370: 665 */             lastSet.setLimit(tileSet.firstGID - 1);
/*  371:     */           }
/*  372: 667 */           lastSet = tileSet;
/*  373:     */           
/*  374: 669 */           this.tileSets.add(tileSet);
/*  375:     */         }
/*  376:     */       }
/*  377: 673 */       NodeList layerNodes = docElement.getElementsByTagName("layer");
/*  378: 674 */       for (int i = 0; i < layerNodes.getLength(); i++)
/*  379:     */       {
/*  380: 675 */         Element current = (Element)layerNodes.item(i);
/*  381: 676 */         Layer layer = new Layer(this, current);
/*  382: 677 */         layer.index = i;
/*  383:     */         
/*  384: 679 */         this.layers.add(layer);
/*  385:     */       }
/*  386: 683 */       NodeList objectGroupNodes = docElement
/*  387: 684 */         .getElementsByTagName("objectgroup");
/*  388: 686 */       for (int i = 0; i < objectGroupNodes.getLength(); i++)
/*  389:     */       {
/*  390: 687 */         Element current = (Element)objectGroupNodes.item(i);
/*  391: 688 */         ObjectGroup objectGroup = new ObjectGroup(current);
/*  392: 689 */         objectGroup.index = i;
/*  393:     */         
/*  394: 691 */         this.objectGroups.add(objectGroup);
/*  395:     */       }
/*  396:     */     }
/*  397:     */     catch (Exception e)
/*  398:     */     {
/*  399: 694 */       Log.error(e);
/*  400: 695 */       throw new SlickException("Failed to parse tilemap", e);
/*  401:     */     }
/*  402:     */   }
/*  403:     */   
/*  404:     */   public int getTileSetCount()
/*  405:     */   {
/*  406: 705 */     return this.tileSets.size();
/*  407:     */   }
/*  408:     */   
/*  409:     */   public TileSet getTileSet(int index)
/*  410:     */   {
/*  411: 716 */     return (TileSet)this.tileSets.get(index);
/*  412:     */   }
/*  413:     */   
/*  414:     */   public TileSet getTileSetByGID(int gid)
/*  415:     */   {
/*  416: 727 */     for (int i = 0; i < this.tileSets.size(); i++)
/*  417:     */     {
/*  418: 728 */       TileSet set = (TileSet)this.tileSets.get(i);
/*  419: 730 */       if (set.contains(gid)) {
/*  420: 731 */         return set;
/*  421:     */       }
/*  422:     */     }
/*  423: 735 */     return null;
/*  424:     */   }
/*  425:     */   
/*  426:     */   public TileSet findTileSet(int gid)
/*  427:     */   {
/*  428: 747 */     for (int i = 0; i < this.tileSets.size(); i++)
/*  429:     */     {
/*  430: 748 */       TileSet set = (TileSet)this.tileSets.get(i);
/*  431: 750 */       if (set.contains(gid)) {
/*  432: 751 */         return set;
/*  433:     */       }
/*  434:     */     }
/*  435: 755 */     return null;
/*  436:     */   }
/*  437:     */   
/*  438:     */   protected void renderedLine(int visualY, int mapY, int layer) {}
/*  439:     */   
/*  440:     */   public int getObjectGroupCount()
/*  441:     */   {
/*  442: 778 */     return this.objectGroups.size();
/*  443:     */   }
/*  444:     */   
/*  445:     */   public int getObjectCount(int groupID)
/*  446:     */   {
/*  447: 790 */     if ((groupID >= 0) && (groupID < this.objectGroups.size()))
/*  448:     */     {
/*  449: 791 */       ObjectGroup grp = (ObjectGroup)this.objectGroups.get(groupID);
/*  450: 792 */       return grp.objects.size();
/*  451:     */     }
/*  452: 794 */     return -1;
/*  453:     */   }
/*  454:     */   
/*  455:     */   public String getObjectName(int groupID, int objectID)
/*  456:     */   {
/*  457: 807 */     if ((groupID >= 0) && (groupID < this.objectGroups.size()))
/*  458:     */     {
/*  459: 808 */       ObjectGroup grp = (ObjectGroup)this.objectGroups.get(groupID);
/*  460: 809 */       if ((objectID >= 0) && (objectID < grp.objects.size()))
/*  461:     */       {
/*  462: 810 */         GroupObject object = (GroupObject)grp.objects.get(objectID);
/*  463: 811 */         return object.name;
/*  464:     */       }
/*  465:     */     }
/*  466: 814 */     return null;
/*  467:     */   }
/*  468:     */   
/*  469:     */   public String getObjectType(int groupID, int objectID)
/*  470:     */   {
/*  471: 827 */     if ((groupID >= 0) && (groupID < this.objectGroups.size()))
/*  472:     */     {
/*  473: 828 */       ObjectGroup grp = (ObjectGroup)this.objectGroups.get(groupID);
/*  474: 829 */       if ((objectID >= 0) && (objectID < grp.objects.size()))
/*  475:     */       {
/*  476: 830 */         GroupObject object = (GroupObject)grp.objects.get(objectID);
/*  477: 831 */         return object.type;
/*  478:     */       }
/*  479:     */     }
/*  480: 834 */     return null;
/*  481:     */   }
/*  482:     */   
/*  483:     */   public int getObjectX(int groupID, int objectID)
/*  484:     */   {
/*  485: 847 */     if ((groupID >= 0) && (groupID < this.objectGroups.size()))
/*  486:     */     {
/*  487: 848 */       ObjectGroup grp = (ObjectGroup)this.objectGroups.get(groupID);
/*  488: 849 */       if ((objectID >= 0) && (objectID < grp.objects.size()))
/*  489:     */       {
/*  490: 850 */         GroupObject object = (GroupObject)grp.objects.get(objectID);
/*  491: 851 */         return object.x;
/*  492:     */       }
/*  493:     */     }
/*  494: 854 */     return -1;
/*  495:     */   }
/*  496:     */   
/*  497:     */   public int getObjectY(int groupID, int objectID)
/*  498:     */   {
/*  499: 867 */     if ((groupID >= 0) && (groupID < this.objectGroups.size()))
/*  500:     */     {
/*  501: 868 */       ObjectGroup grp = (ObjectGroup)this.objectGroups.get(groupID);
/*  502: 869 */       if ((objectID >= 0) && (objectID < grp.objects.size()))
/*  503:     */       {
/*  504: 870 */         GroupObject object = (GroupObject)grp.objects.get(objectID);
/*  505: 871 */         return object.y;
/*  506:     */       }
/*  507:     */     }
/*  508: 874 */     return -1;
/*  509:     */   }
/*  510:     */   
/*  511:     */   public int getObjectWidth(int groupID, int objectID)
/*  512:     */   {
/*  513: 887 */     if ((groupID >= 0) && (groupID < this.objectGroups.size()))
/*  514:     */     {
/*  515: 888 */       ObjectGroup grp = (ObjectGroup)this.objectGroups.get(groupID);
/*  516: 889 */       if ((objectID >= 0) && (objectID < grp.objects.size()))
/*  517:     */       {
/*  518: 890 */         GroupObject object = (GroupObject)grp.objects.get(objectID);
/*  519: 891 */         return object.width;
/*  520:     */       }
/*  521:     */     }
/*  522: 894 */     return -1;
/*  523:     */   }
/*  524:     */   
/*  525:     */   public int getObjectHeight(int groupID, int objectID)
/*  526:     */   {
/*  527: 907 */     if ((groupID >= 0) && (groupID < this.objectGroups.size()))
/*  528:     */     {
/*  529: 908 */       ObjectGroup grp = (ObjectGroup)this.objectGroups.get(groupID);
/*  530: 909 */       if ((objectID >= 0) && (objectID < grp.objects.size()))
/*  531:     */       {
/*  532: 910 */         GroupObject object = (GroupObject)grp.objects.get(objectID);
/*  533: 911 */         return object.height;
/*  534:     */       }
/*  535:     */     }
/*  536: 914 */     return -1;
/*  537:     */   }
/*  538:     */   
/*  539:     */   public String getObjectImage(int groupID, int objectID)
/*  540:     */   {
/*  541: 927 */     if ((groupID >= 0) && (groupID < this.objectGroups.size()))
/*  542:     */     {
/*  543: 928 */       ObjectGroup grp = (ObjectGroup)this.objectGroups.get(groupID);
/*  544: 929 */       if ((objectID >= 0) && (objectID < grp.objects.size()))
/*  545:     */       {
/*  546: 930 */         GroupObject object = (GroupObject)grp.objects.get(objectID);
/*  547: 932 */         if (object == null) {
/*  548: 933 */           return null;
/*  549:     */         }
/*  550: 936 */         return object.image;
/*  551:     */       }
/*  552:     */     }
/*  553: 940 */     return null;
/*  554:     */   }
/*  555:     */   
/*  556:     */   public String getObjectProperty(int groupID, int objectID, String propertyName, String def)
/*  557:     */   {
/*  558: 960 */     if ((groupID >= 0) && (groupID < this.objectGroups.size()))
/*  559:     */     {
/*  560: 961 */       ObjectGroup grp = (ObjectGroup)this.objectGroups.get(groupID);
/*  561: 962 */       if ((objectID >= 0) && (objectID < grp.objects.size()))
/*  562:     */       {
/*  563: 963 */         GroupObject object = (GroupObject)grp.objects.get(objectID);
/*  564: 965 */         if (object == null) {
/*  565: 966 */           return def;
/*  566:     */         }
/*  567: 968 */         if (object.props == null) {
/*  568: 969 */           return def;
/*  569:     */         }
/*  570: 972 */         return object.props.getProperty(propertyName, def);
/*  571:     */       }
/*  572:     */     }
/*  573: 975 */     return def;
/*  574:     */   }
/*  575:     */   
/*  576:     */   protected class ObjectGroup
/*  577:     */   {
/*  578:     */     public int index;
/*  579:     */     public String name;
/*  580:     */     public ArrayList objects;
/*  581:     */     public int width;
/*  582:     */     public int height;
/*  583:     */     public Properties props;
/*  584:     */     
/*  585:     */     public ObjectGroup(Element element)
/*  586:     */       throws SlickException
/*  587:     */     {
/*  588:1007 */       this.name = element.getAttribute("name");
/*  589:1008 */       this.width = Integer.parseInt(element.getAttribute("width"));
/*  590:1009 */       this.height = Integer.parseInt(element.getAttribute("height"));
/*  591:1010 */       this.objects = new ArrayList();
/*  592:     */       
/*  593:     */ 
/*  594:1013 */       Element propsElement = (Element)element.getElementsByTagName(
/*  595:1014 */         "properties").item(0);
/*  596:1015 */       if (propsElement != null)
/*  597:     */       {
/*  598:1016 */         NodeList properties = propsElement
/*  599:1017 */           .getElementsByTagName("property");
/*  600:1018 */         if (properties != null)
/*  601:     */         {
/*  602:1019 */           this.props = new Properties();
/*  603:1020 */           for (int p = 0; p < properties.getLength(); p++)
/*  604:     */           {
/*  605:1021 */             Element propElement = (Element)properties.item(p);
/*  606:     */             
/*  607:1023 */             String name = propElement.getAttribute("name");
/*  608:1024 */             String value = propElement.getAttribute("value");
/*  609:1025 */             this.props.setProperty(name, value);
/*  610:     */           }
/*  611:     */         }
/*  612:     */       }
/*  613:1030 */       NodeList objectNodes = element.getElementsByTagName("object");
/*  614:1031 */       for (int i = 0; i < objectNodes.getLength(); i++)
/*  615:     */       {
/*  616:1032 */         Element objElement = (Element)objectNodes.item(i);
/*  617:1033 */         TiledMap.GroupObject object = new TiledMap.GroupObject(TiledMap.this, objElement);
/*  618:1034 */         object.index = i;
/*  619:1035 */         this.objects.add(object);
/*  620:     */       }
/*  621:     */     }
/*  622:     */   }
/*  623:     */   
/*  624:     */   protected class GroupObject
/*  625:     */   {
/*  626:     */     public int index;
/*  627:     */     public String name;
/*  628:     */     public String type;
/*  629:     */     public int x;
/*  630:     */     public int y;
/*  631:     */     public int width;
/*  632:     */     public int height;
/*  633:     */     private String image;
/*  634:     */     public Properties props;
/*  635:     */     
/*  636:     */     public GroupObject(Element element)
/*  637:     */       throws SlickException
/*  638:     */     {
/*  639:1075 */       this.name = element.getAttribute("name");
/*  640:1076 */       this.type = element.getAttribute("type");
/*  641:1077 */       this.x = Integer.parseInt(element.getAttribute("x"));
/*  642:1078 */       this.y = Integer.parseInt(element.getAttribute("y"));
/*  643:1079 */       this.width = Integer.parseInt(element.getAttribute("width"));
/*  644:1080 */       this.height = Integer.parseInt(element.getAttribute("height"));
/*  645:     */       
/*  646:1082 */       Element imageElement = (Element)element.getElementsByTagName(
/*  647:1083 */         "image").item(0);
/*  648:1084 */       if (imageElement != null) {
/*  649:1085 */         this.image = imageElement.getAttribute("source");
/*  650:     */       }
/*  651:1089 */       Element propsElement = (Element)element.getElementsByTagName(
/*  652:1090 */         "properties").item(0);
/*  653:1091 */       if (propsElement != null)
/*  654:     */       {
/*  655:1092 */         NodeList properties = propsElement
/*  656:1093 */           .getElementsByTagName("property");
/*  657:1094 */         if (properties != null)
/*  658:     */         {
/*  659:1095 */           this.props = new Properties();
/*  660:1096 */           for (int p = 0; p < properties.getLength(); p++)
/*  661:     */           {
/*  662:1097 */             Element propElement = (Element)properties.item(p);
/*  663:     */             
/*  664:1099 */             String name = propElement.getAttribute("name");
/*  665:1100 */             String value = propElement.getAttribute("value");
/*  666:1101 */             this.props.setProperty(name, value);
/*  667:     */           }
/*  668:     */         }
/*  669:     */       }
/*  670:     */     }
/*  671:     */   }
/*  672:     */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.tiled.TiledMap
 * JD-Core Version:    0.7.0.1
 */