package HORIZON-6-0-SKIDPROTECTION;

import org.w3c.dom.NodeList;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import java.io.InputStream;
import java.util.Properties;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Element;
import java.util.HashMap;

public class TileSet
{
    private final TiledMap á;
    public int HorizonCode_Horizon_È;
    public String Â;
    public int Ý;
    public int Ø­áŒŠá;
    public int Âµá€;
    public int Ó;
    public SpriteSheet à;
    public int Ø;
    public int áŒŠÆ;
    private HashMap ˆÏ­;
    protected int áˆºÑ¢Õ;
    protected int ÂµÈ;
    
    public TileSet(final TiledMap map, Element element, final boolean loadImage) throws SlickException {
        this.Ø­áŒŠá = Integer.MAX_VALUE;
        this.ˆÏ­ = new HashMap();
        this.áˆºÑ¢Õ = 0;
        this.ÂµÈ = 0;
        this.á = map;
        this.Â = element.getAttribute("name");
        this.Ý = Integer.parseInt(element.getAttribute("firstgid"));
        final String source = element.getAttribute("source");
        if (source != null && !source.equals("")) {
            try {
                final InputStream in = ResourceLoader.HorizonCode_Horizon_È(String.valueOf(map.HorizonCode_Horizon_È()) + "/" + source);
                final DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                final Document doc = builder.parse(in);
                final Element docElement = element = doc.getDocumentElement();
            }
            catch (Exception e) {
                Log.HorizonCode_Horizon_È(e);
                throw new SlickException("Unable to load or parse sourced tileset: " + this.á.Âµá€ + "/" + source);
            }
        }
        final String tileWidthString = element.getAttribute("tilewidth");
        final String tileHeightString = element.getAttribute("tileheight");
        if (tileWidthString.length() == 0 || tileHeightString.length() == 0) {
            throw new SlickException("TiledMap requires that the map be created with tilesets that use a single image.  Check the WiKi for more complete information.");
        }
        this.Âµá€ = Integer.parseInt(tileWidthString);
        this.Ó = Integer.parseInt(tileHeightString);
        final String sv = element.getAttribute("spacing");
        if (sv != null && !sv.equals("")) {
            this.áˆºÑ¢Õ = Integer.parseInt(sv);
        }
        final String mv = element.getAttribute("margin");
        if (mv != null && !mv.equals("")) {
            this.ÂµÈ = Integer.parseInt(mv);
        }
        final NodeList list = element.getElementsByTagName("image");
        final Element imageNode = (Element)list.item(0);
        final String ref = imageNode.getAttribute("source");
        Color trans = null;
        final String t = imageNode.getAttribute("trans");
        if (t != null && t.length() > 0) {
            final int c = Integer.parseInt(t, 16);
            trans = new Color(c);
        }
        if (loadImage) {
            final Image image = new Image(String.valueOf(map.HorizonCode_Horizon_È()) + "/" + ref, false, 2, trans);
            this.HorizonCode_Horizon_È(image);
        }
        final NodeList pElements = element.getElementsByTagName("tile");
        for (int i = 0; i < pElements.getLength(); ++i) {
            final Element tileElement = (Element)pElements.item(i);
            int id = Integer.parseInt(tileElement.getAttribute("id"));
            id += this.Ý;
            final Properties tileProps = new Properties();
            final Element propsElement = (Element)tileElement.getElementsByTagName("properties").item(0);
            final NodeList properties = propsElement.getElementsByTagName("property");
            for (int p = 0; p < properties.getLength(); ++p) {
                final Element propElement = (Element)properties.item(p);
                final String name = propElement.getAttribute("name");
                final String value = propElement.getAttribute("value");
                tileProps.setProperty(name, value);
            }
            this.ˆÏ­.put(new Integer(id), tileProps);
        }
    }
    
    public int HorizonCode_Horizon_È() {
        return this.Âµá€;
    }
    
    public int Â() {
        return this.Ó;
    }
    
    public int Ý() {
        return this.áˆºÑ¢Õ;
    }
    
    public int Ø­áŒŠá() {
        return this.ÂµÈ;
    }
    
    public void HorizonCode_Horizon_È(final Image image) {
        this.à = new SpriteSheet(image, this.Âµá€, this.Ó, this.áˆºÑ¢Õ, this.ÂµÈ);
        this.Ø = this.à.HorizonCode_Horizon_È();
        this.áŒŠÆ = this.à.á();
        if (this.Ø <= 0) {
            this.Ø = 1;
        }
        if (this.áŒŠÆ <= 0) {
            this.áŒŠÆ = 1;
        }
        this.Ø­áŒŠá = this.Ø * this.áŒŠÆ + this.Ý - 1;
    }
    
    public Properties HorizonCode_Horizon_È(final int globalID) {
        return this.ˆÏ­.get(new Integer(globalID));
    }
    
    public int Â(final int id) {
        return id % this.Ø;
    }
    
    public int Ý(final int id) {
        return id / this.Ø;
    }
    
    public void Ø­áŒŠá(final int limit) {
        this.Ø­áŒŠá = limit;
    }
    
    public boolean Âµá€(final int gid) {
        return gid >= this.Ý && gid <= this.Ø­áŒŠá;
    }
}
