package HORIZON-6-0-SKIDPROTECTION;

import org.w3c.dom.NodeList;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Element;
import java.io.IOException;
import org.xml.sax.SAXException;
import java.io.ByteArrayInputStream;
import org.xml.sax.InputSource;
import org.xml.sax.EntityResolver;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

public class TiledMap
{
    private static boolean ˆÏ­;
    protected int HorizonCode_Horizon_È;
    protected int Â;
    protected int Ý;
    protected int Ø­áŒŠá;
    protected String Âµá€;
    protected Properties Ó;
    protected ArrayList à;
    protected ArrayList Ø;
    protected ArrayList áŒŠÆ;
    protected static final int áˆºÑ¢Õ = 1;
    protected static final int ÂµÈ = 2;
    protected int á;
    private boolean £á;
    
    private static void HorizonCode_Horizon_È(final boolean h) {
        TiledMap.ˆÏ­ = h;
    }
    
    public TiledMap(final String ref) throws SlickException {
        this(ref, true);
    }
    
    public TiledMap(String ref, final boolean loadTileSets) throws SlickException {
        this.à = new ArrayList();
        this.Ø = new ArrayList();
        this.áŒŠÆ = new ArrayList();
        this.£á = true;
        this.£á = loadTileSets;
        ref = ref.replace('\\', '/');
        this.HorizonCode_Horizon_È(ResourceLoader.HorizonCode_Horizon_È(ref), ref.substring(0, ref.lastIndexOf("/")));
    }
    
    public TiledMap(final String ref, final String tileSetsLocation) throws SlickException {
        this.à = new ArrayList();
        this.Ø = new ArrayList();
        this.áŒŠÆ = new ArrayList();
        this.£á = true;
        this.HorizonCode_Horizon_È(ResourceLoader.HorizonCode_Horizon_È(ref), tileSetsLocation);
    }
    
    public TiledMap(final InputStream in) throws SlickException {
        this.à = new ArrayList();
        this.Ø = new ArrayList();
        this.áŒŠÆ = new ArrayList();
        this.£á = true;
        this.HorizonCode_Horizon_È(in, "");
    }
    
    public TiledMap(final InputStream in, final String tileSetsLocation) throws SlickException {
        this.à = new ArrayList();
        this.Ø = new ArrayList();
        this.áŒŠÆ = new ArrayList();
        this.£á = true;
        this.HorizonCode_Horizon_È(in, tileSetsLocation);
    }
    
    public String HorizonCode_Horizon_È() {
        return this.Âµá€;
    }
    
    public int HorizonCode_Horizon_È(final String name) {
        final int idx = 0;
        for (int i = 0; i < this.Ø.size(); ++i) {
            final Layer layer = this.Ø.get(i);
            if (layer.Â.equals(name)) {
                return i;
            }
        }
        return -1;
    }
    
    public Image HorizonCode_Horizon_È(final int x, final int y, final int layerIndex) {
        final Layer layer = this.Ø.get(layerIndex);
        final int tileSetIndex = layer.Ý[x][y][0];
        if (tileSetIndex >= 0 && tileSetIndex < this.à.size()) {
            final TileSet tileSet = this.à.get(tileSetIndex);
            final int sheetX = tileSet.Â(layer.Ý[x][y][1]);
            final int sheetY = tileSet.Ý(layer.Ý[x][y][1]);
            return tileSet.à.Ø­áŒŠá(sheetX, sheetY);
        }
        return null;
    }
    
    public int Â() {
        return this.HorizonCode_Horizon_È;
    }
    
    public int Ý() {
        return this.Â;
    }
    
    public int Ø­áŒŠá() {
        return this.Ø­áŒŠá;
    }
    
    public int Âµá€() {
        return this.Ý;
    }
    
    public int Â(final int x, final int y, final int layerIndex) {
        final Layer layer = this.Ø.get(layerIndex);
        return layer.HorizonCode_Horizon_È(x, y);
    }
    
    public void HorizonCode_Horizon_È(final int x, final int y, final int layerIndex, final int tileid) {
        final Layer layer = this.Ø.get(layerIndex);
        layer.HorizonCode_Horizon_È(x, y, tileid);
    }
    
    public String HorizonCode_Horizon_È(final String propertyName, final String def) {
        if (this.Ó == null) {
            return def;
        }
        return this.Ó.getProperty(propertyName, def);
    }
    
    public String HorizonCode_Horizon_È(final int layerIndex, final String propertyName, final String def) {
        final Layer layer = this.Ø.get(layerIndex);
        if (layer == null || layer.Ó == null) {
            return def;
        }
        return layer.Ó.getProperty(propertyName, def);
    }
    
    public String Â(final int tileID, final String propertyName, final String def) {
        if (tileID == 0) {
            return def;
        }
        final TileSet set = this.Ý(tileID);
        final Properties props = set.HorizonCode_Horizon_È(tileID);
        if (props == null) {
            return def;
        }
        return props.getProperty(propertyName, def);
    }
    
    public void HorizonCode_Horizon_È(final int x, final int y) {
        this.HorizonCode_Horizon_È(x, y, 0, 0, this.HorizonCode_Horizon_È, this.Â, false);
    }
    
    public void Ý(final int x, final int y, final int layer) {
        this.HorizonCode_Horizon_È(x, y, 0, 0, this.Â(), this.Ý(), layer, false);
    }
    
    public void HorizonCode_Horizon_È(final int x, final int y, final int sx, final int sy, final int width, final int height) {
        this.HorizonCode_Horizon_È(x, y, sx, sy, width, height, false);
    }
    
    public void HorizonCode_Horizon_È(final int x, final int y, final int sx, final int sy, final int width, final int height, final int l, final boolean lineByLine) {
        final Layer layer = this.Ø.get(l);
        switch (this.á) {
            case 1: {
                for (int ty = 0; ty < height; ++ty) {
                    layer.HorizonCode_Horizon_È(x, y, sx, sy, width, ty, lineByLine, this.Ý, this.Ø­áŒŠá);
                }
                break;
            }
            case 2: {
                this.HorizonCode_Horizon_È(x, y, sx, sy, width, height, layer, lineByLine);
                break;
            }
        }
    }
    
    public void HorizonCode_Horizon_È(final int x, final int y, final int sx, final int sy, final int width, final int height, final boolean lineByLine) {
        switch (this.á) {
            case 1: {
                for (int ty = 0; ty < height; ++ty) {
                    for (int i = 0; i < this.Ø.size(); ++i) {
                        final Layer layer = this.Ø.get(i);
                        layer.HorizonCode_Horizon_È(x, y, sx, sy, width, ty, lineByLine, this.Ý, this.Ø­áŒŠá);
                    }
                }
                break;
            }
            case 2: {
                this.HorizonCode_Horizon_È(x, y, sx, sy, width, height, null, lineByLine);
                break;
            }
        }
    }
    
    protected void HorizonCode_Horizon_È(final int x, final int y, final int sx, final int sy, final int width, final int height, final Layer layer, final boolean lineByLine) {
        ArrayList drawLayers = this.Ø;
        if (layer != null) {
            drawLayers = new ArrayList();
            drawLayers.add(layer);
        }
        final int maxCount = width * height;
        int allCount = 0;
        boolean allProcessed = false;
        int initialLineX = x;
        int initialLineY = y;
        int startLineTileX = 0;
        int startLineTileY = 0;
        while (!allProcessed) {
            int currentTileX = startLineTileX;
            int currentTileY = startLineTileY;
            int currentLineX = initialLineX;
            int min = 0;
            if (height > width) {
                min = ((startLineTileY < width - 1) ? startLineTileY : ((width - currentTileX < height) ? (width - currentTileX - 1) : (width - 1)));
            }
            else {
                min = ((startLineTileY < height - 1) ? startLineTileY : ((width - currentTileX < height) ? (width - currentTileX - 1) : (height - 1)));
            }
            for (int burner = 0; burner <= min; ++burner) {
                for (int layerIdx = 0; layerIdx < drawLayers.size(); ++layerIdx) {
                    final Layer currentLayer = drawLayers.get(layerIdx);
                    currentLayer.HorizonCode_Horizon_È(currentLineX, initialLineY, currentTileX, currentTileY, 1, 0, lineByLine, this.Ý, this.Ø­áŒŠá);
                }
                currentLineX += this.Ý;
                ++allCount;
                ++currentTileX;
                --currentTileY;
            }
            if (startLineTileY < height - 1) {
                ++startLineTileY;
                initialLineX -= this.Ý / 2;
                initialLineY += this.Ø­áŒŠá / 2;
            }
            else {
                ++startLineTileX;
                initialLineX += this.Ý / 2;
                initialLineY += this.Ø­áŒŠá / 2;
            }
            if (allCount >= maxCount) {
                allProcessed = true;
            }
        }
    }
    
    public int Ó() {
        return this.Ø.size();
    }
    
    private int Â(final String value) {
        try {
            return Integer.parseInt(value);
        }
        catch (NumberFormatException e) {
            return 0;
        }
    }
    
    private void HorizonCode_Horizon_È(final InputStream in, final String tileSetsLocation) throws SlickException {
        this.Âµá€ = tileSetsLocation;
        try {
            final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(false);
            final DocumentBuilder builder = factory.newDocumentBuilder();
            builder.setEntityResolver(new EntityResolver() {
                @Override
                public InputSource resolveEntity(final String publicId, final String systemId) throws SAXException, IOException {
                    return new InputSource(new ByteArrayInputStream(new byte[0]));
                }
            });
            final Document doc = builder.parse(in);
            final Element docElement = doc.getDocumentElement();
            if (docElement.getAttribute("orientation").equals("orthogonal")) {
                this.á = 1;
            }
            else {
                this.á = 2;
            }
            this.HorizonCode_Horizon_È = this.Â(docElement.getAttribute("width"));
            this.Â = this.Â(docElement.getAttribute("height"));
            this.Ý = this.Â(docElement.getAttribute("tilewidth"));
            this.Ø­áŒŠá = this.Â(docElement.getAttribute("tileheight"));
            final Element propsElement = (Element)docElement.getElementsByTagName("properties").item(0);
            if (propsElement != null) {
                final NodeList properties = propsElement.getElementsByTagName("property");
                if (properties != null) {
                    this.Ó = new Properties();
                    for (int p = 0; p < properties.getLength(); ++p) {
                        final Element propElement = (Element)properties.item(p);
                        final String name = propElement.getAttribute("name");
                        final String value = propElement.getAttribute("value");
                        this.Ó.setProperty(name, value);
                    }
                }
            }
            if (this.£á) {
                TileSet tileSet = null;
                TileSet lastSet = null;
                final NodeList setNodes = docElement.getElementsByTagName("tileset");
                for (int i = 0; i < setNodes.getLength(); ++i) {
                    final Element current = (Element)setNodes.item(i);
                    tileSet = new TileSet(this, current, !TiledMap.ˆÏ­);
                    tileSet.HorizonCode_Horizon_È = i;
                    if (lastSet != null) {
                        lastSet.Ø­áŒŠá(tileSet.Ý - 1);
                    }
                    lastSet = tileSet;
                    this.à.add(tileSet);
                }
            }
            final NodeList layerNodes = docElement.getElementsByTagName("layer");
            for (int j = 0; j < layerNodes.getLength(); ++j) {
                final Element current2 = (Element)layerNodes.item(j);
                final Layer layer = new Layer(this, current2);
                layer.HorizonCode_Horizon_È = j;
                this.Ø.add(layer);
            }
            final NodeList objectGroupNodes = docElement.getElementsByTagName("objectgroup");
            for (int k = 0; k < objectGroupNodes.getLength(); ++k) {
                final Element current3 = (Element)objectGroupNodes.item(k);
                final Â objectGroup = new Â(current3);
                objectGroup.HorizonCode_Horizon_È = k;
                this.áŒŠÆ.add(objectGroup);
            }
        }
        catch (Exception e) {
            Log.HorizonCode_Horizon_È(e);
            throw new SlickException("Failed to parse tilemap", e);
        }
    }
    
    public int à() {
        return this.à.size();
    }
    
    public TileSet HorizonCode_Horizon_È(final int index) {
        return this.à.get(index);
    }
    
    public TileSet Â(final int gid) {
        for (int i = 0; i < this.à.size(); ++i) {
            final TileSet set = this.à.get(i);
            if (set.Âµá€(gid)) {
                return set;
            }
        }
        return null;
    }
    
    public TileSet Ý(final int gid) {
        for (int i = 0; i < this.à.size(); ++i) {
            final TileSet set = this.à.get(i);
            if (set.Âµá€(gid)) {
                return set;
            }
        }
        return null;
    }
    
    protected void Ø­áŒŠá(final int visualY, final int mapY, final int layer) {
    }
    
    public int Ø() {
        return this.áŒŠÆ.size();
    }
    
    public int Ø­áŒŠá(final int groupID) {
        if (groupID >= 0 && groupID < this.áŒŠÆ.size()) {
            final Â grp = this.áŒŠÆ.get(groupID);
            return grp.Ý.size();
        }
        return -1;
    }
    
    public String Â(final int groupID, final int objectID) {
        if (groupID >= 0 && groupID < this.áŒŠÆ.size()) {
            final Â grp = this.áŒŠÆ.get(groupID);
            if (objectID >= 0 && objectID < grp.Ý.size()) {
                final HorizonCode_Horizon_È object = grp.Ý.get(objectID);
                return object.Â;
            }
        }
        return null;
    }
    
    public String Ý(final int groupID, final int objectID) {
        if (groupID >= 0 && groupID < this.áŒŠÆ.size()) {
            final Â grp = this.áŒŠÆ.get(groupID);
            if (objectID >= 0 && objectID < grp.Ý.size()) {
                final HorizonCode_Horizon_È object = grp.Ý.get(objectID);
                return object.Ý;
            }
        }
        return null;
    }
    
    public int Ø­áŒŠá(final int groupID, final int objectID) {
        if (groupID >= 0 && groupID < this.áŒŠÆ.size()) {
            final Â grp = this.áŒŠÆ.get(groupID);
            if (objectID >= 0 && objectID < grp.Ý.size()) {
                final HorizonCode_Horizon_È object = grp.Ý.get(objectID);
                return object.Ø­áŒŠá;
            }
        }
        return -1;
    }
    
    public int Âµá€(final int groupID, final int objectID) {
        if (groupID >= 0 && groupID < this.áŒŠÆ.size()) {
            final Â grp = this.áŒŠÆ.get(groupID);
            if (objectID >= 0 && objectID < grp.Ý.size()) {
                final HorizonCode_Horizon_È object = grp.Ý.get(objectID);
                return object.Âµá€;
            }
        }
        return -1;
    }
    
    public int Ó(final int groupID, final int objectID) {
        if (groupID >= 0 && groupID < this.áŒŠÆ.size()) {
            final Â grp = this.áŒŠÆ.get(groupID);
            if (objectID >= 0 && objectID < grp.Ý.size()) {
                final HorizonCode_Horizon_È object = grp.Ý.get(objectID);
                return object.Ó;
            }
        }
        return -1;
    }
    
    public int à(final int groupID, final int objectID) {
        if (groupID >= 0 && groupID < this.áŒŠÆ.size()) {
            final Â grp = this.áŒŠÆ.get(groupID);
            if (objectID >= 0 && objectID < grp.Ý.size()) {
                final HorizonCode_Horizon_È object = grp.Ý.get(objectID);
                return object.à;
            }
        }
        return -1;
    }
    
    public String Ø(final int groupID, final int objectID) {
        if (groupID >= 0 && groupID < this.áŒŠÆ.size()) {
            final Â grp = this.áŒŠÆ.get(groupID);
            if (objectID >= 0 && objectID < grp.Ý.size()) {
                final HorizonCode_Horizon_È object = grp.Ý.get(objectID);
                if (object == null) {
                    return null;
                }
                return object.áˆºÑ¢Õ;
            }
        }
        return null;
    }
    
    public String HorizonCode_Horizon_È(final int groupID, final int objectID, final String propertyName, final String def) {
        if (groupID >= 0 && groupID < this.áŒŠÆ.size()) {
            final Â grp = this.áŒŠÆ.get(groupID);
            if (objectID >= 0 && objectID < grp.Ý.size()) {
                final HorizonCode_Horizon_È object = grp.Ý.get(objectID);
                if (object == null) {
                    return def;
                }
                if (object.Ø == null) {
                    return def;
                }
                return object.Ø.getProperty(propertyName, def);
            }
        }
        return def;
    }
    
    protected class Â
    {
        public int HorizonCode_Horizon_È;
        public String Â;
        public ArrayList Ý;
        public int Ø­áŒŠá;
        public int Âµá€;
        public Properties Ó;
        
        public Â(final Element element) throws SlickException {
            this.Â = element.getAttribute("name");
            this.Ø­áŒŠá = Integer.parseInt(element.getAttribute("width"));
            this.Âµá€ = Integer.parseInt(element.getAttribute("height"));
            this.Ý = new ArrayList();
            final Element propsElement = (Element)element.getElementsByTagName("properties").item(0);
            if (propsElement != null) {
                final NodeList properties = propsElement.getElementsByTagName("property");
                if (properties != null) {
                    this.Ó = new Properties();
                    for (int p = 0; p < properties.getLength(); ++p) {
                        final Element propElement = (Element)properties.item(p);
                        final String name = propElement.getAttribute("name");
                        final String value = propElement.getAttribute("value");
                        this.Ó.setProperty(name, value);
                    }
                }
            }
            final NodeList objectNodes = element.getElementsByTagName("object");
            for (int i = 0; i < objectNodes.getLength(); ++i) {
                final Element objElement = (Element)objectNodes.item(i);
                final HorizonCode_Horizon_È object = new HorizonCode_Horizon_È(objElement);
                object.HorizonCode_Horizon_È = i;
                this.Ý.add(object);
            }
        }
    }
    
    protected class HorizonCode_Horizon_È
    {
        public int HorizonCode_Horizon_È;
        public String Â;
        public String Ý;
        public int Ø­áŒŠá;
        public int Âµá€;
        public int Ó;
        public int à;
        private String áˆºÑ¢Õ;
        public Properties Ø;
        
        public HorizonCode_Horizon_È(final Element element) throws SlickException {
            this.Â = element.getAttribute("name");
            this.Ý = element.getAttribute("type");
            this.Ø­áŒŠá = Integer.parseInt(element.getAttribute("x"));
            this.Âµá€ = Integer.parseInt(element.getAttribute("y"));
            this.Ó = Integer.parseInt(element.getAttribute("width"));
            this.à = Integer.parseInt(element.getAttribute("height"));
            final Element imageElement = (Element)element.getElementsByTagName("image").item(0);
            if (imageElement != null) {
                this.áˆºÑ¢Õ = imageElement.getAttribute("source");
            }
            final Element propsElement = (Element)element.getElementsByTagName("properties").item(0);
            if (propsElement != null) {
                final NodeList properties = propsElement.getElementsByTagName("property");
                if (properties != null) {
                    this.Ø = new Properties();
                    for (int p = 0; p < properties.getLength(); ++p) {
                        final Element propElement = (Element)properties.item(p);
                        final String name = propElement.getAttribute("name");
                        final String value = propElement.getAttribute("value");
                        this.Ø.setProperty(name, value);
                    }
                }
            }
        }
    }
}
