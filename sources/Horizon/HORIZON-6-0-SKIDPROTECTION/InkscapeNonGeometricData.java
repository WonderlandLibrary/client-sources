package HORIZON-6-0-SKIDPROTECTION;

import org.w3c.dom.Element;

public class InkscapeNonGeometricData extends NonGeometricData
{
    private Element ÂµÈ;
    
    public InkscapeNonGeometricData(final String metaData, final Element element) {
        super(metaData);
        this.ÂµÈ = element;
    }
    
    @Override
    public String Â(final String attribute) {
        String result = super.Â(attribute);
        if (result == null) {
            result = this.ÂµÈ.getAttribute(attribute);
        }
        return result;
    }
    
    public Element Ø­áŒŠá() {
        return this.ÂµÈ;
    }
}
