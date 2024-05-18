package HORIZON-6-0-SKIDPROTECTION;

import org.w3c.dom.NodeList;
import java.util.ArrayList;
import org.w3c.dom.Element;

public class DefsProcessor implements ElementProcessor
{
    @Override
    public boolean HorizonCode_Horizon_È(final Element element) {
        return element.getNodeName().equals("defs");
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Loader loader, final Element element, final Diagram diagram, final Transform transform) throws ParsingException {
        final NodeList patterns = element.getElementsByTagName("pattern");
        for (int i = 0; i < patterns.getLength(); ++i) {
            final Element pattern = (Element)patterns.item(i);
            final NodeList list = pattern.getElementsByTagName("image");
            if (list.getLength() == 0) {
                Log.Â("Pattern 1981 does not specify an image. Only image patterns are supported.");
            }
            else {
                final Element image = (Element)list.item(0);
                final String patternName = pattern.getAttribute("id");
                final String ref = image.getAttributeNS("http://www.w3.org/1999/xlink", "href");
                diagram.HorizonCode_Horizon_È(patternName, ref);
            }
        }
        final NodeList linear = element.getElementsByTagName("linearGradient");
        final ArrayList toResolve = new ArrayList();
        for (int j = 0; j < linear.getLength(); ++j) {
            final Element lin = (Element)linear.item(j);
            final String name = lin.getAttribute("id");
            final Gradient gradient = new Gradient(name, false);
            gradient.HorizonCode_Horizon_È(Util.Â(lin, "gradientTransform"));
            if (this.HorizonCode_Horizon_È(lin.getAttribute("x1")) > 0) {
                gradient.Â(Float.parseFloat(lin.getAttribute("x1")));
            }
            if (this.HorizonCode_Horizon_È(lin.getAttribute("x2")) > 0) {
                gradient.Ý(Float.parseFloat(lin.getAttribute("x2")));
            }
            if (this.HorizonCode_Horizon_È(lin.getAttribute("y1")) > 0) {
                gradient.Ø­áŒŠá(Float.parseFloat(lin.getAttribute("y1")));
            }
            if (this.HorizonCode_Horizon_È(lin.getAttribute("y2")) > 0) {
                gradient.Âµá€(Float.parseFloat(lin.getAttribute("y2")));
            }
            final String ref2 = lin.getAttributeNS("http://www.w3.org/1999/xlink", "href");
            if (this.HorizonCode_Horizon_È(ref2) > 0) {
                gradient.HorizonCode_Horizon_È(ref2.substring(1));
                toResolve.add(gradient);
            }
            else {
                final NodeList steps = lin.getElementsByTagName("stop");
                for (int k = 0; k < steps.getLength(); ++k) {
                    final Element s = (Element)steps.item(k);
                    final float offset = Float.parseFloat(s.getAttribute("offset"));
                    final String colInt = Util.HorizonCode_Horizon_È(s.getAttribute("style"), "stop-color");
                    final String opaInt = Util.HorizonCode_Horizon_È(s.getAttribute("style"), "stop-opacity");
                    final int col = Integer.parseInt(colInt.substring(1), 16);
                    final Color stopColor = new Color(col);
                    stopColor.¥Æ = Float.parseFloat(opaInt);
                    gradient.HorizonCode_Horizon_È(offset, stopColor);
                }
                gradient.Ø­áŒŠá();
            }
            diagram.HorizonCode_Horizon_È(name, gradient);
        }
        final NodeList radial = element.getElementsByTagName("radialGradient");
        for (int l = 0; l < radial.getLength(); ++l) {
            final Element rad = (Element)radial.item(l);
            final String name2 = rad.getAttribute("id");
            final Gradient gradient2 = new Gradient(name2, true);
            gradient2.HorizonCode_Horizon_È(Util.Â(rad, "gradientTransform"));
            if (this.HorizonCode_Horizon_È(rad.getAttribute("cx")) > 0) {
                gradient2.Â(Float.parseFloat(rad.getAttribute("cx")));
            }
            if (this.HorizonCode_Horizon_È(rad.getAttribute("cy")) > 0) {
                gradient2.Ø­áŒŠá(Float.parseFloat(rad.getAttribute("cy")));
            }
            if (this.HorizonCode_Horizon_È(rad.getAttribute("fx")) > 0) {
                gradient2.Ý(Float.parseFloat(rad.getAttribute("fx")));
            }
            if (this.HorizonCode_Horizon_È(rad.getAttribute("fy")) > 0) {
                gradient2.Âµá€(Float.parseFloat(rad.getAttribute("fy")));
            }
            if (this.HorizonCode_Horizon_È(rad.getAttribute("r")) > 0) {
                gradient2.HorizonCode_Horizon_È(Float.parseFloat(rad.getAttribute("r")));
            }
            final String ref3 = rad.getAttributeNS("http://www.w3.org/1999/xlink", "href");
            if (this.HorizonCode_Horizon_È(ref3) > 0) {
                gradient2.HorizonCode_Horizon_È(ref3.substring(1));
                toResolve.add(gradient2);
            }
            else {
                final NodeList steps2 = rad.getElementsByTagName("stop");
                for (int m = 0; m < steps2.getLength(); ++m) {
                    final Element s2 = (Element)steps2.item(m);
                    final float offset2 = Float.parseFloat(s2.getAttribute("offset"));
                    final String colInt2 = Util.HorizonCode_Horizon_È(s2.getAttribute("style"), "stop-color");
                    final String opaInt2 = Util.HorizonCode_Horizon_È(s2.getAttribute("style"), "stop-opacity");
                    final int col2 = Integer.parseInt(colInt2.substring(1), 16);
                    final Color stopColor2 = new Color(col2);
                    stopColor2.¥Æ = Float.parseFloat(opaInt2);
                    gradient2.HorizonCode_Horizon_È(offset2, stopColor2);
                }
                gradient2.Ø­áŒŠá();
            }
            diagram.HorizonCode_Horizon_È(name2, gradient2);
        }
        for (int l = 0; l < toResolve.size(); ++l) {
            toResolve.get(l).HorizonCode_Horizon_È(diagram);
            toResolve.get(l).Ø­áŒŠá();
        }
    }
    
    private int HorizonCode_Horizon_È(final String value) {
        if (value == null) {
            return 0;
        }
        return value.length();
    }
}
