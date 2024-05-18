package HORIZON-6-0-SKIDPROTECTION;

import org.w3c.dom.Element;

public class EllipseProcessor implements ElementProcessor
{
    @Override
    public void HorizonCode_Horizon_È(final Loader loader, final Element element, final Diagram diagram, final Transform t) throws ParsingException {
        Transform transform = Util.Ý(element);
        transform = new Transform(t, transform);
        final float x = Util.Ý(element, "cx");
        final float y = Util.Ý(element, "cy");
        final float rx = Util.Ý(element, "rx");
        final float ry = Util.Ý(element, "ry");
        final Ellipse ellipse = new Ellipse(x, y, rx, ry);
        final Shape shape = ellipse.HorizonCode_Horizon_È(transform);
        final NonGeometricData data = Util.HorizonCode_Horizon_È(element);
        data.HorizonCode_Horizon_È("cx", new StringBuilder().append(x).toString());
        data.HorizonCode_Horizon_È("cy", new StringBuilder().append(y).toString());
        data.HorizonCode_Horizon_È("rx", new StringBuilder().append(rx).toString());
        data.HorizonCode_Horizon_È("ry", new StringBuilder().append(ry).toString());
        diagram.HorizonCode_Horizon_È(new Figure(1, shape, data, transform));
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final Element element) {
        return element.getNodeName().equals("ellipse") || (element.getNodeName().equals("path") && "arc".equals(element.getAttributeNS("http://sodipodi.sourceforge.net/DTD/sodipodi-0.dtd", "type")));
    }
}
