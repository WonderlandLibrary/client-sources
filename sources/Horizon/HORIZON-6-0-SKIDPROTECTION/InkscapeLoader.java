package HORIZON-6-0-SKIDPROTECTION;

import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import java.io.IOException;
import org.xml.sax.SAXException;
import java.io.ByteArrayInputStream;
import org.xml.sax.InputSource;
import org.xml.sax.EntityResolver;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.ArrayList;

public class InkscapeLoader implements Loader
{
    public static int HorizonCode_Horizon_È;
    private static ArrayList Â;
    private Diagram Ý;
    
    static {
        InkscapeLoader.HorizonCode_Horizon_È = 1;
        InkscapeLoader.Â = new ArrayList();
        HorizonCode_Horizon_È(new RectProcessor());
        HorizonCode_Horizon_È(new EllipseProcessor());
        HorizonCode_Horizon_È(new PolygonProcessor());
        HorizonCode_Horizon_È(new PathProcessor());
        HorizonCode_Horizon_È(new LineProcessor());
        HorizonCode_Horizon_È(new GroupProcessor());
        HorizonCode_Horizon_È(new DefsProcessor());
        HorizonCode_Horizon_È(new UseProcessor());
    }
    
    public static void HorizonCode_Horizon_È(final ElementProcessor proc) {
        InkscapeLoader.Â.add(proc);
    }
    
    public static Diagram HorizonCode_Horizon_È(final String ref, final boolean offset) throws SlickException {
        return HorizonCode_Horizon_È(ResourceLoader.HorizonCode_Horizon_È(ref), offset);
    }
    
    public static Diagram HorizonCode_Horizon_È(final String ref) throws SlickException {
        return HorizonCode_Horizon_È(ResourceLoader.HorizonCode_Horizon_È(ref), false);
    }
    
    public static Diagram HorizonCode_Horizon_È(final InputStream in, final boolean offset) throws SlickException {
        return new InkscapeLoader().Â(in, offset);
    }
    
    private Diagram HorizonCode_Horizon_È(final InputStream in) throws SlickException {
        return this.Â(in, false);
    }
    
    private Diagram Â(final InputStream in, final boolean offset) throws SlickException {
        try {
            final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(false);
            factory.setNamespaceAware(true);
            final DocumentBuilder builder = factory.newDocumentBuilder();
            builder.setEntityResolver(new EntityResolver() {
                @Override
                public InputSource resolveEntity(final String publicId, final String systemId) throws SAXException, IOException {
                    return new InputSource(new ByteArrayInputStream(new byte[0]));
                }
            });
            final Document doc = builder.parse(in);
            final Element root = doc.getDocumentElement();
            String widthString;
            for (widthString = root.getAttribute("width"); Character.isLetter(widthString.charAt(widthString.length() - 1)); widthString = widthString.substring(0, widthString.length() - 1)) {}
            String heightString;
            for (heightString = root.getAttribute("height"); Character.isLetter(heightString.charAt(heightString.length() - 1)); heightString = heightString.substring(0, heightString.length() - 1)) {}
            final float docWidth = Float.parseFloat(widthString);
            float docHeight = Float.parseFloat(heightString);
            this.Ý = new Diagram(docWidth, docHeight);
            if (!offset) {
                docHeight = 0.0f;
            }
            this.HorizonCode_Horizon_È(root, Transform.HorizonCode_Horizon_È(0.0f, -docHeight));
            return this.Ý;
        }
        catch (Exception e) {
            throw new SlickException("Failed to load inkscape document", e);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Element element, final Transform t) throws ParsingException {
        final NodeList list = element.getChildNodes();
        for (int i = 0; i < list.getLength(); ++i) {
            if (list.item(i) instanceof Element) {
                this.Â((Element)list.item(i), t);
            }
        }
    }
    
    private void Â(final Element element, final Transform t) throws ParsingException {
        for (int i = 0; i < InkscapeLoader.Â.size(); ++i) {
            final ElementProcessor processor = InkscapeLoader.Â.get(i);
            if (processor.HorizonCode_Horizon_È(element)) {
                processor.HorizonCode_Horizon_È(this, element, this.Ý, t);
            }
        }
    }
}
