package org.newdawn.slick.tiled;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.util.Log;
import org.newdawn.slick.util.ResourceLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;












public class TiledMap
{
  private static boolean headless;
  protected int width;
  protected int height;
  protected int tileWidth;
  protected int tileHeight;
  protected String tilesLocation;
  protected Properties props;
  
  private static void setHeadless(boolean h)
  {
    headless = h;
  }
  
















  protected ArrayList tileSets = new ArrayList();
  
  protected ArrayList layers = new ArrayList();
  
  protected ArrayList objectGroups = new ArrayList();
  

  protected static final int ORTHOGONAL = 1;
  

  protected static final int ISOMETRIC = 2;
  

  protected int orientation;
  
  private boolean loadTileSets = true;
  






  public TiledMap(String ref)
    throws SlickException
  {
    this(ref, true);
  }
  








  public TiledMap(String ref, boolean loadTileSets)
    throws SlickException
  {
    this.loadTileSets = loadTileSets;
    ref = ref.replace('\\', '/');
    load(ResourceLoader.getResourceAsStream(ref), 
      ref.substring(0, ref.lastIndexOf("/")));
  }
  









  public TiledMap(String ref, String tileSetsLocation)
    throws SlickException
  {
    load(ResourceLoader.getResourceAsStream(ref), tileSetsLocation);
  }
  






  public TiledMap(InputStream in)
    throws SlickException
  {
    load(in, "");
  }
  









  public TiledMap(InputStream in, String tileSetsLocation)
    throws SlickException
  {
    load(in, tileSetsLocation);
  }
  





  public String getTilesLocation()
  {
    return tilesLocation;
  }
  






  public int getLayerIndex(String name)
  {
    int idx = 0;
    
    for (int i = 0; i < layers.size(); i++) {
      Layer layer = (Layer)layers.get(i);
      
      if (name.equals(name)) {
        return i;
      }
    }
    
    return -1;
  }
  












  public Image getTileImage(int x, int y, int layerIndex)
  {
    Layer layer = (Layer)layers.get(layerIndex);
    
    int tileSetIndex = data[x][y][0];
    if ((tileSetIndex >= 0) && (tileSetIndex < tileSets.size())) {
      TileSet tileSet = (TileSet)tileSets.get(tileSetIndex);
      
      int sheetX = tileSet.getTileX(data[x][y][1]);
      int sheetY = tileSet.getTileY(data[x][y][1]);
      
      return tiles.getSprite(sheetX, sheetY);
    }
    
    return null;
  }
  




  public int getWidth()
  {
    return width;
  }
  




  public int getHeight()
  {
    return height;
  }
  




  public int getTileHeight()
  {
    return tileHeight;
  }
  




  public int getTileWidth()
  {
    return tileWidth;
  }
  










  public int getTileId(int x, int y, int layerIndex)
  {
    Layer layer = (Layer)layers.get(layerIndex);
    return layer.getTileID(x, y);
  }
  











  public void setTileId(int x, int y, int layerIndex, int tileid)
  {
    Layer layer = (Layer)layers.get(layerIndex);
    layer.setTileID(x, y, tileid);
  }
  











  public String getMapProperty(String propertyName, String def)
  {
    if (props == null)
      return def;
    return props.getProperty(propertyName, def);
  }
  














  public String getLayerProperty(int layerIndex, String propertyName, String def)
  {
    Layer layer = (Layer)layers.get(layerIndex);
    if ((layer == null) || (props == null))
      return def;
    return props.getProperty(propertyName, def);
  }
  













  public String getTileProperty(int tileID, String propertyName, String def)
  {
    if (tileID == 0) {
      return def;
    }
    
    TileSet set = findTileSet(tileID);
    
    Properties props = set.getProperties(tileID);
    if (props == null) {
      return def;
    }
    return props.getProperty(propertyName, def);
  }
  







  public void render(int x, int y)
  {
    render(x, y, 0, 0, width, height, false);
  }
  









  public void render(int x, int y, int layer)
  {
    render(x, y, 0, 0, getWidth(), getHeight(), layer, false);
  }
  















  public void render(int x, int y, int sx, int sy, int width, int height)
  {
    render(x, y, sx, sy, width, height, false);
  }
  






















  public void render(int x, int y, int sx, int sy, int width, int height, int l, boolean lineByLine)
  {
    Layer layer = (Layer)layers.get(l);
    
    switch (orientation) {
    case 1: 
      for (int ty = 0; ty < height; ty++) {
        layer.render(x, y, sx, sy, width, ty, lineByLine, tileWidth, 
          tileHeight);
      }
      break;
    case 2: 
      renderIsometricMap(x, y, sx, sy, width, height, layer, lineByLine);
    }
    
  }
  






















  public void render(int x, int y, int sx, int sy, int width, int height, boolean lineByLine)
  {
    switch (orientation) {
    case 1: 
      for (int ty = 0; ty < height; ty++) {
        for (int i = 0; i < layers.size(); i++) {
          Layer layer = (Layer)layers.get(i);
          layer.render(x, y, sx, sy, width, ty, lineByLine, 
            tileWidth, tileHeight);
        }
      }
      break;
    case 2: 
      renderIsometricMap(x, y, sx, sy, width, height, null, lineByLine);
    }
    
  }
  




























  protected void renderIsometricMap(int x, int y, int sx, int sy, int width, int height, Layer layer, boolean lineByLine)
  {
    ArrayList drawLayers = layers;
    if (layer != null) {
      drawLayers = new ArrayList();
      drawLayers.add(layer);
    }
    
    int maxCount = width * height;
    int allCount = 0;
    
    boolean allProcessed = false;
    
    int initialLineX = x;
    int initialLineY = y;
    
    int startLineTileX = 0;
    int startLineTileY = 0;
    while (!allProcessed)
    {
      int currentTileX = startLineTileX;
      int currentTileY = startLineTileY;
      int currentLineX = initialLineX;
      
      int min = 0;
      if (height > width) {
        min = 
          width - currentTileX < height ? width - currentTileX - 1 : startLineTileY < width - 1 ? startLineTileY : 
          width - 1;
      } else {
        min = 
          width - currentTileX < height ? width - currentTileX - 1 : startLineTileY < height - 1 ? startLineTileY : 
          height - 1;
      }
      for (int burner = 0; burner <= min; burner++) {
        for (int layerIdx = 0; layerIdx < drawLayers.size(); layerIdx++) {
          Layer currentLayer = (Layer)drawLayers.get(layerIdx);
          currentLayer.render(currentLineX, initialLineY, 
            currentTileX, currentTileY, 1, 0, lineByLine, 
            tileWidth, tileHeight);
        }
        currentLineX += tileWidth;
        
        allCount++;currentTileX++;currentTileY--;
      }
      





      if (startLineTileY < height - 1) {
        startLineTileY++;
        initialLineX -= tileWidth / 2;
        initialLineY += tileHeight / 2;
      } else {
        startLineTileX++;
        initialLineX += tileWidth / 2;
        initialLineY += tileHeight / 2;
      }
      
      if (allCount >= maxCount) {
        allProcessed = true;
      }
    }
  }
  



  public int getLayerCount()
  {
    return layers.size();
  }
  





  private int parseInt(String value)
  {
    try
    {
      return Integer.parseInt(value);
    } catch (NumberFormatException e) {}
    return 0;
  }
  










  private void load(InputStream in, String tileSetsLocation)
    throws SlickException
  {
    tilesLocation = tileSetsLocation;
    try
    {
      DocumentBuilderFactory factory = 
        DocumentBuilderFactory.newInstance();
      factory.setValidating(false);
      DocumentBuilder builder = factory.newDocumentBuilder();
      builder.setEntityResolver(new EntityResolver()
      {
        public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
          return new InputSource(
            new ByteArrayInputStream(new byte[0]));
        }
        
      });
      Document doc = builder.parse(in);
      Element docElement = doc.getDocumentElement();
      
      if (docElement.getAttribute("orientation").equals("orthogonal")) {
        orientation = 1;
      } else {
        orientation = 2;
      }
      




      width = parseInt(docElement.getAttribute("width"));
      height = parseInt(docElement.getAttribute("height"));
      tileWidth = parseInt(docElement.getAttribute("tilewidth"));
      tileHeight = parseInt(docElement.getAttribute("tileheight"));
      

      Element propsElement = (Element)docElement.getElementsByTagName(
        "properties").item(0);
      if (propsElement != null) {
        NodeList properties = propsElement
          .getElementsByTagName("property");
        if (properties != null) {
          props = new Properties();
          for (int p = 0; p < properties.getLength(); p++) {
            Element propElement = (Element)properties.item(p);
            
            String name = propElement.getAttribute("name");
            String value = propElement.getAttribute("value");
            props.setProperty(name, value);
          }
        }
      }
      
      if (loadTileSets) {
        TileSet tileSet = null;
        TileSet lastSet = null;
        
        NodeList setNodes = docElement.getElementsByTagName("tileset");
        for (int i = 0; i < setNodes.getLength(); i++) {
          Element current = (Element)setNodes.item(i);
          
          tileSet = new TileSet(this, current, !headless);
          index = i;
          
          if (lastSet != null) {
            lastSet.setLimit(firstGID - 1);
          }
          lastSet = tileSet;
          
          tileSets.add(tileSet);
        }
      }
      
      NodeList layerNodes = docElement.getElementsByTagName("layer");
      for (int i = 0; i < layerNodes.getLength(); i++) {
        Element current = (Element)layerNodes.item(i);
        Layer layer = new Layer(this, current);
        index = i;
        
        layers.add(layer);
      }
      

      NodeList objectGroupNodes = docElement
        .getElementsByTagName("objectgroup");
      
      for (int i = 0; i < objectGroupNodes.getLength(); i++) {
        Element current = (Element)objectGroupNodes.item(i);
        ObjectGroup objectGroup = new ObjectGroup(current);
        index = i;
        
        objectGroups.add(objectGroup);
      }
    } catch (Exception e) {
      Log.error(e);
      throw new SlickException("Failed to parse tilemap", e);
    }
  }
  




  public int getTileSetCount()
  {
    return tileSets.size();
  }
  






  public TileSet getTileSet(int index)
  {
    return (TileSet)tileSets.get(index);
  }
  






  public TileSet getTileSetByGID(int gid)
  {
    for (int i = 0; i < tileSets.size(); i++) {
      TileSet set = (TileSet)tileSets.get(i);
      
      if (set.contains(gid)) {
        return set;
      }
    }
    
    return null;
  }
  







  public TileSet findTileSet(int gid)
  {
    for (int i = 0; i < tileSets.size(); i++) {
      TileSet set = (TileSet)tileSets.get(i);
      
      if (set.contains(gid)) {
        return set;
      }
    }
    
    return null;
  }
  








  protected void renderedLine(int visualY, int mapY, int layer) {}
  








  public int getObjectGroupCount()
  {
    return objectGroups.size();
  }
  







  public int getObjectCount(int groupID)
  {
    if ((groupID >= 0) && (groupID < objectGroups.size())) {
      ObjectGroup grp = (ObjectGroup)objectGroups.get(groupID);
      return objects.size();
    }
    return -1;
  }
  








  public String getObjectName(int groupID, int objectID)
  {
    if ((groupID >= 0) && (groupID < objectGroups.size())) {
      ObjectGroup grp = (ObjectGroup)objectGroups.get(groupID);
      if ((objectID >= 0) && (objectID < objects.size())) {
        GroupObject object = (GroupObject)objects.get(objectID);
        return name;
      }
    }
    return null;
  }
  








  public String getObjectType(int groupID, int objectID)
  {
    if ((groupID >= 0) && (groupID < objectGroups.size())) {
      ObjectGroup grp = (ObjectGroup)objectGroups.get(groupID);
      if ((objectID >= 0) && (objectID < objects.size())) {
        GroupObject object = (GroupObject)objects.get(objectID);
        return type;
      }
    }
    return null;
  }
  








  public int getObjectX(int groupID, int objectID)
  {
    if ((groupID >= 0) && (groupID < objectGroups.size())) {
      ObjectGroup grp = (ObjectGroup)objectGroups.get(groupID);
      if ((objectID >= 0) && (objectID < objects.size())) {
        GroupObject object = (GroupObject)objects.get(objectID);
        return x;
      }
    }
    return -1;
  }
  








  public int getObjectY(int groupID, int objectID)
  {
    if ((groupID >= 0) && (groupID < objectGroups.size())) {
      ObjectGroup grp = (ObjectGroup)objectGroups.get(groupID);
      if ((objectID >= 0) && (objectID < objects.size())) {
        GroupObject object = (GroupObject)objects.get(objectID);
        return y;
      }
    }
    return -1;
  }
  








  public int getObjectWidth(int groupID, int objectID)
  {
    if ((groupID >= 0) && (groupID < objectGroups.size())) {
      ObjectGroup grp = (ObjectGroup)objectGroups.get(groupID);
      if ((objectID >= 0) && (objectID < objects.size())) {
        GroupObject object = (GroupObject)objects.get(objectID);
        return width;
      }
    }
    return -1;
  }
  








  public int getObjectHeight(int groupID, int objectID)
  {
    if ((groupID >= 0) && (groupID < objectGroups.size())) {
      ObjectGroup grp = (ObjectGroup)objectGroups.get(groupID);
      if ((objectID >= 0) && (objectID < objects.size())) {
        GroupObject object = (GroupObject)objects.get(objectID);
        return height;
      }
    }
    return -1;
  }
  








  public String getObjectImage(int groupID, int objectID)
  {
    if ((groupID >= 0) && (groupID < objectGroups.size())) {
      ObjectGroup grp = (ObjectGroup)objectGroups.get(groupID);
      if ((objectID >= 0) && (objectID < objects.size())) {
        GroupObject object = (GroupObject)objects.get(objectID);
        
        if (object == null) {
          return null;
        }
        
        return image;
      }
    }
    
    return null;
  }
  















  public String getObjectProperty(int groupID, int objectID, String propertyName, String def)
  {
    if ((groupID >= 0) && (groupID < objectGroups.size())) {
      ObjectGroup grp = (ObjectGroup)objectGroups.get(groupID);
      if ((objectID >= 0) && (objectID < objects.size())) {
        GroupObject object = (GroupObject)objects.get(objectID);
        
        if (object == null) {
          return def;
        }
        if (props == null) {
          return def;
        }
        
        return props.getProperty(propertyName, def);
      }
    }
    return def;
  }
  



  protected class ObjectGroup
  {
    public int index;
    


    public String name;
    


    public ArrayList objects;
    

    public int width;
    

    public int height;
    

    public Properties props;
    


    public ObjectGroup(Element element)
      throws SlickException
    {
      this.name = element.getAttribute("name");
      width = Integer.parseInt(element.getAttribute("width"));
      height = Integer.parseInt(element.getAttribute("height"));
      objects = new ArrayList();
      

      Element propsElement = (Element)element.getElementsByTagName(
        "properties").item(0);
      if (propsElement != null) {
        NodeList properties = propsElement
          .getElementsByTagName("property");
        if (properties != null) {
          props = new Properties();
          for (int p = 0; p < properties.getLength(); p++) {
            Element propElement = (Element)properties.item(p);
            
            String name = propElement.getAttribute("name");
            String value = propElement.getAttribute("value");
            props.setProperty(name, value);
          }
        }
      }
      
      NodeList objectNodes = element.getElementsByTagName("object");
      for (int i = 0; i < objectNodes.getLength(); i++) {
        Element objElement = (Element)objectNodes.item(i);
        TiledMap.GroupObject object = new TiledMap.GroupObject(TiledMap.this, objElement);
        index = i;
        objects.add(object);
      }
    }
  }
  


  protected class GroupObject
  {
    public int index;
    

    public String name;
    

    public String type;
    

    public int x;
    

    public int y;
    

    public int width;
    

    public int height;
    

    private String image;
    

    public Properties props;
    


    public GroupObject(Element element)
      throws SlickException
    {
      this.name = element.getAttribute("name");
      type = element.getAttribute("type");
      x = Integer.parseInt(element.getAttribute("x"));
      y = Integer.parseInt(element.getAttribute("y"));
      width = Integer.parseInt(element.getAttribute("width"));
      height = Integer.parseInt(element.getAttribute("height"));
      
      Element imageElement = (Element)element.getElementsByTagName(
        "image").item(0);
      if (imageElement != null) {
        image = imageElement.getAttribute("source");
      }
      

      Element propsElement = (Element)element.getElementsByTagName(
        "properties").item(0);
      if (propsElement != null) {
        NodeList properties = propsElement
          .getElementsByTagName("property");
        if (properties != null) {
          props = new Properties();
          for (int p = 0; p < properties.getLength(); p++) {
            Element propElement = (Element)properties.item(p);
            
            String name = propElement.getAttribute("name");
            String value = propElement.getAttribute("value");
            props.setProperty(name, value);
          }
        }
      }
    }
  }
}
