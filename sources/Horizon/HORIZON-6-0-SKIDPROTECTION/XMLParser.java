package HORIZON-6-0-SKIDPROTECTION;

import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import java.io.InputStream;
import javax.xml.parsers.DocumentBuilderFactory;

public class XMLParser
{
    private static DocumentBuilderFactory HorizonCode_Horizon_È;
    
    public XMLElement HorizonCode_Horizon_È(final String ref) throws SlickException {
        return this.HorizonCode_Horizon_È(ref, ResourceLoader.HorizonCode_Horizon_È(ref));
    }
    
    public XMLElement HorizonCode_Horizon_È(final String name, final InputStream in) throws SlickXMLException {
        try {
            if (XMLParser.HorizonCode_Horizon_È == null) {
                XMLParser.HorizonCode_Horizon_È = DocumentBuilderFactory.newInstance();
            }
            final DocumentBuilder builder = XMLParser.HorizonCode_Horizon_È.newDocumentBuilder();
            final Document doc = builder.parse(in);
            return new XMLElement(doc.getDocumentElement());
        }
        catch (Exception e) {
            throw new SlickXMLException("Failed to parse document: " + name, e);
        }
    }
}
