package HORIZON-6-0-SKIDPROTECTION;

import org.w3c.dom.NodeList;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.HashMap;

public class XMLPackedSheet
{
    private Image HorizonCode_Horizon_È;
    private HashMap Â;
    
    public XMLPackedSheet(final String imageRef, final String xmlRef) throws SlickException {
        this.Â = new HashMap();
        this.HorizonCode_Horizon_È = new Image(imageRef, false, 2);
        try {
            final DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            final Document doc = builder.parse(ResourceLoader.HorizonCode_Horizon_È(xmlRef));
            final NodeList list = doc.getElementsByTagName("sprite");
            for (int i = 0; i < list.getLength(); ++i) {
                final Element element = (Element)list.item(i);
                final String name = element.getAttribute("name");
                final int x = Integer.parseInt(element.getAttribute("x"));
                final int y = Integer.parseInt(element.getAttribute("y"));
                final int width = Integer.parseInt(element.getAttribute("width"));
                final int height = Integer.parseInt(element.getAttribute("height"));
                this.Â.put(name, this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(x, y, width, height));
            }
        }
        catch (Exception e) {
            throw new SlickException("Failed to parse sprite sheet XML", e);
        }
    }
    
    public Image HorizonCode_Horizon_È(final String name) {
        return this.Â.get(name);
    }
}
