package HORIZON-6-0-SKIDPROTECTION;

import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Element;

public class XMLElement
{
    private Element HorizonCode_Horizon_È;
    private XMLElementList Â;
    private String Ý;
    
    XMLElement(final Element xmlElement) {
        this.HorizonCode_Horizon_È = xmlElement;
        this.Ý = this.HorizonCode_Horizon_È.getTagName();
    }
    
    public String[] HorizonCode_Horizon_È() {
        final NamedNodeMap map = this.HorizonCode_Horizon_È.getAttributes();
        final String[] names = new String[map.getLength()];
        for (int i = 0; i < names.length; ++i) {
            names[i] = map.item(i).getNodeName();
        }
        return names;
    }
    
    public String Â() {
        return this.Ý;
    }
    
    public String HorizonCode_Horizon_È(final String name) {
        return this.HorizonCode_Horizon_È.getAttribute(name);
    }
    
    public String HorizonCode_Horizon_È(final String name, final String def) {
        final String value = this.HorizonCode_Horizon_È.getAttribute(name);
        if (value == null || value.length() == 0) {
            return def;
        }
        return value;
    }
    
    public int Â(final String name) throws SlickXMLException {
        try {
            return Integer.parseInt(this.HorizonCode_Horizon_È(name));
        }
        catch (NumberFormatException e) {
            throw new SlickXMLException("Value read: '" + this.HorizonCode_Horizon_È(name) + "' is not an integer", e);
        }
    }
    
    public int HorizonCode_Horizon_È(final String name, final int def) throws SlickXMLException {
        try {
            return Integer.parseInt(this.HorizonCode_Horizon_È(name, new StringBuilder().append(def).toString()));
        }
        catch (NumberFormatException e) {
            throw new SlickXMLException("Value read: '" + this.HorizonCode_Horizon_È(name, new StringBuilder().append(def).toString()) + "' is not an integer", e);
        }
    }
    
    public double Ý(final String name) throws SlickXMLException {
        try {
            return Double.parseDouble(this.HorizonCode_Horizon_È(name));
        }
        catch (NumberFormatException e) {
            throw new SlickXMLException("Value read: '" + this.HorizonCode_Horizon_È(name) + "' is not a double", e);
        }
    }
    
    public double HorizonCode_Horizon_È(final String name, final double def) throws SlickXMLException {
        try {
            return Double.parseDouble(this.HorizonCode_Horizon_È(name, new StringBuilder().append(def).toString()));
        }
        catch (NumberFormatException e) {
            throw new SlickXMLException("Value read: '" + this.HorizonCode_Horizon_È(name, new StringBuilder().append(def).toString()) + "' is not a double", e);
        }
    }
    
    public boolean Ø­áŒŠá(final String name) throws SlickXMLException {
        final String value = this.HorizonCode_Horizon_È(name);
        if (value.equalsIgnoreCase("true")) {
            return true;
        }
        if (value.equalsIgnoreCase("false")) {
            return false;
        }
        throw new SlickXMLException("Value read: '" + this.HorizonCode_Horizon_È(name) + "' is not a boolean");
    }
    
    public boolean HorizonCode_Horizon_È(final String name, final boolean def) throws SlickXMLException {
        final String value = this.HorizonCode_Horizon_È(name, new StringBuilder().append(def).toString());
        if (value.equalsIgnoreCase("true")) {
            return true;
        }
        if (value.equalsIgnoreCase("false")) {
            return false;
        }
        throw new SlickXMLException("Value read: '" + this.HorizonCode_Horizon_È(name, new StringBuilder().append(def).toString()) + "' is not a boolean");
    }
    
    public String Ý() {
        String content = "";
        final NodeList list = this.HorizonCode_Horizon_È.getChildNodes();
        for (int i = 0; i < list.getLength(); ++i) {
            if (list.item(i) instanceof Text) {
                content = String.valueOf(content) + list.item(i).getNodeValue();
            }
        }
        return content;
    }
    
    public XMLElementList Ø­áŒŠá() {
        if (this.Â != null) {
            return this.Â;
        }
        final NodeList list = this.HorizonCode_Horizon_È.getChildNodes();
        this.Â = new XMLElementList();
        for (int i = 0; i < list.getLength(); ++i) {
            if (list.item(i) instanceof Element) {
                this.Â.HorizonCode_Horizon_È(new XMLElement((Element)list.item(i)));
            }
        }
        return this.Â;
    }
    
    public XMLElementList Âµá€(final String name) {
        final XMLElementList selected = new XMLElementList();
        final XMLElementList children = this.Ø­áŒŠá();
        for (int i = 0; i < children.HorizonCode_Horizon_È(); ++i) {
            if (children.HorizonCode_Horizon_È(i).Â().equals(name)) {
                selected.HorizonCode_Horizon_È(children.HorizonCode_Horizon_È(i));
            }
        }
        return selected;
    }
    
    @Override
    public String toString() {
        String value = "[XML " + this.Â();
        final String[] attrs = this.HorizonCode_Horizon_È();
        for (int i = 0; i < attrs.length; ++i) {
            value = String.valueOf(value) + " " + attrs[i] + "=" + this.HorizonCode_Horizon_È(attrs[i]);
        }
        value = String.valueOf(value) + "]";
        return value;
    }
}
