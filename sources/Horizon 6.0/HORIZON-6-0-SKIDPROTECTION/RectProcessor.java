package HORIZON-6-0-SKIDPROTECTION;

import org.w3c.dom.Element;

public class RectProcessor implements ElementProcessor
{
    @Override
    public void HorizonCode_Horizon_È(final Loader loader, final Element element, final Diagram diagram, final Transform t) throws ParsingException {
        Transform transform = Util.Ý(element);
        transform = new Transform(t, transform);
        final float width = Float.parseFloat(element.getAttribute("width"));
        final float height = Float.parseFloat(element.getAttribute("height"));
        final float x = Float.parseFloat(element.getAttribute("x"));
        final float y = Float.parseFloat(element.getAttribute("y"));
        final Rectangle rect = new Rectangle(x, y, width + 1.0f, height + 1.0f);
        final Shape shape = rect.HorizonCode_Horizon_È(transform);
        final NonGeometricData data = Util.HorizonCode_Horizon_È(element);
        data.HorizonCode_Horizon_È("width", new StringBuilder().append(width).toString());
        data.HorizonCode_Horizon_È("height", new StringBuilder().append(height).toString());
        data.HorizonCode_Horizon_È("x", new StringBuilder().append(x).toString());
        data.HorizonCode_Horizon_È("y", new StringBuilder().append(y).toString());
        diagram.HorizonCode_Horizon_È(new Figure(3, shape, data, transform));
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final Element element) {
        return element.getNodeName().equals("rect");
    }
}
