package HORIZON-6-0-SKIDPROTECTION;

import java.util.StringTokenizer;
import org.w3c.dom.Element;

public class Util
{
    public static final String HorizonCode_Horizon_È = "http://www.inkscape.org/namespaces/inkscape";
    public static final String Â = "http://sodipodi.sourceforge.net/DTD/sodipodi-0.dtd";
    public static final String Ý = "http://www.w3.org/1999/xlink";
    
    static NonGeometricData HorizonCode_Horizon_È(final Element element) {
        final String meta = Â(element);
        final NonGeometricData data = new InkscapeNonGeometricData(meta, element);
        data.HorizonCode_Horizon_È("id", element.getAttribute("id"));
        data.HorizonCode_Horizon_È("fill", HorizonCode_Horizon_È(element, "fill"));
        data.HorizonCode_Horizon_È("stroke", HorizonCode_Horizon_È(element, "stroke"));
        data.HorizonCode_Horizon_È("opacity", HorizonCode_Horizon_È(element, "opacity"));
        data.HorizonCode_Horizon_È("stroke-dasharray", HorizonCode_Horizon_È(element, "stroke-dasharray"));
        data.HorizonCode_Horizon_È("stroke-dashoffset", HorizonCode_Horizon_È(element, "stroke-dashoffset"));
        data.HorizonCode_Horizon_È("stroke-miterlimit", HorizonCode_Horizon_È(element, "stroke-miterlimit"));
        data.HorizonCode_Horizon_È("stroke-opacity", HorizonCode_Horizon_È(element, "stroke-opacity"));
        data.HorizonCode_Horizon_È("stroke-width", HorizonCode_Horizon_È(element, "stroke-width"));
        return data;
    }
    
    static String Â(final Element element) {
        final String label = element.getAttributeNS("http://www.inkscape.org/namespaces/inkscape", "label");
        if (label != null && !label.equals("")) {
            return label;
        }
        return element.getAttribute("id");
    }
    
    static String HorizonCode_Horizon_È(final Element element, final String styleName) {
        final String value = element.getAttribute(styleName);
        if (value != null && value.length() > 0) {
            return value;
        }
        final String style = element.getAttribute("style");
        return HorizonCode_Horizon_È(style, styleName);
    }
    
    static String HorizonCode_Horizon_È(final String style, final String attribute) {
        if (style == null) {
            return "";
        }
        final StringTokenizer tokens = new StringTokenizer(style, ";");
        while (tokens.hasMoreTokens()) {
            final String token = tokens.nextToken();
            final String key = token.substring(0, token.indexOf(58));
            if (key.equals(attribute)) {
                return token.substring(token.indexOf(58) + 1);
            }
        }
        return "";
    }
    
    static Transform Ý(final Element element) {
        return Â(element, "transform");
    }
    
    static Transform Â(final Element element, final String attribute) {
        String str = element.getAttribute(attribute);
        if (str == null) {
            return new Transform();
        }
        if (str.equals("")) {
            return new Transform();
        }
        if (str.startsWith("translate")) {
            str = str.substring(0, str.length() - 1);
            str = str.substring("translate(".length());
            final StringTokenizer tokens = new StringTokenizer(str, ", ");
            final float x = Float.parseFloat(tokens.nextToken());
            final float y = Float.parseFloat(tokens.nextToken());
            return Transform.HorizonCode_Horizon_È(x, y);
        }
        if (str.startsWith("matrix")) {
            final float[] pose = new float[6];
            str = str.substring(0, str.length() - 1);
            str = str.substring("matrix(".length());
            final StringTokenizer tokens2 = new StringTokenizer(str, ", ");
            final float[] tr = new float[6];
            for (int j = 0; j < tr.length; ++j) {
                tr[j] = Float.parseFloat(tokens2.nextToken());
            }
            pose[0] = tr[0];
            pose[1] = tr[2];
            pose[2] = tr[4];
            pose[3] = tr[1];
            pose[4] = tr[3];
            pose[5] = tr[5];
            return new Transform(pose);
        }
        return new Transform();
    }
    
    static float Ý(final Element element, final String attr) throws ParsingException {
        String cx = element.getAttribute(attr);
        if (cx == null || cx.equals("")) {
            cx = element.getAttributeNS("http://sodipodi.sourceforge.net/DTD/sodipodi-0.dtd", attr);
        }
        try {
            return Float.parseFloat(cx);
        }
        catch (NumberFormatException e) {
            throw new ParsingException(element, "Invalid value for: " + attr, e);
        }
    }
    
    public static String HorizonCode_Horizon_È(String value) {
        if (value.length() < 2) {
            return "";
        }
        value = value.substring(1, value.length());
        return value;
    }
}
