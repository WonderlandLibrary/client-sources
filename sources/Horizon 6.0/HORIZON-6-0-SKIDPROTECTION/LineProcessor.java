package HORIZON-6-0-SKIDPROTECTION;

import java.util.StringTokenizer;
import org.w3c.dom.Element;

public class LineProcessor implements ElementProcessor
{
    private static int HorizonCode_Horizon_È(final Polygon poly, final Element element, final StringTokenizer tokens) throws ParsingException {
        int count = 0;
        while (tokens.hasMoreTokens()) {
            final String nextToken = tokens.nextToken();
            if (nextToken.equals("L")) {
                continue;
            }
            if (nextToken.equals("z")) {
                break;
            }
            if (nextToken.equals("M")) {
                continue;
            }
            if (nextToken.equals("C")) {
                return 0;
            }
            final String tokenX = nextToken;
            final String tokenY = tokens.nextToken();
            try {
                final float x = Float.parseFloat(tokenX);
                final float y = Float.parseFloat(tokenY);
                poly.Â(x, y);
                ++count;
            }
            catch (NumberFormatException e) {
                throw new ParsingException(element.getAttribute("id"), "Invalid token in points list", e);
            }
        }
        return count;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Loader loader, final Element element, final Diagram diagram, final Transform t) throws ParsingException {
        Transform transform = Util.Ý(element);
        transform = new Transform(t, transform);
        float x1;
        float x2;
        float y1;
        float y2;
        if (element.getNodeName().equals("line")) {
            x1 = Float.parseFloat(element.getAttribute("x1"));
            x2 = Float.parseFloat(element.getAttribute("x2"));
            y1 = Float.parseFloat(element.getAttribute("y1"));
            y2 = Float.parseFloat(element.getAttribute("y2"));
        }
        else {
            final String points = element.getAttribute("d");
            final StringTokenizer tokens = new StringTokenizer(points, ", ");
            final Polygon poly = new Polygon();
            if (HorizonCode_Horizon_È(poly, element, tokens) != 2) {
                return;
            }
            x1 = poly.HorizonCode_Horizon_È(0)[0];
            y1 = poly.HorizonCode_Horizon_È(0)[1];
            x2 = poly.HorizonCode_Horizon_È(1)[0];
            y2 = poly.HorizonCode_Horizon_È(1)[1];
        }
        final float[] in = { x1, y1, x2, y2 };
        final float[] out = new float[4];
        transform.HorizonCode_Horizon_È(in, 0, out, 0, 2);
        final Line line = new Line(out[0], out[1], out[2], out[3]);
        final NonGeometricData data = Util.HorizonCode_Horizon_È(element);
        data.HorizonCode_Horizon_È("x1", new StringBuilder().append(x1).toString());
        data.HorizonCode_Horizon_È("x2", new StringBuilder().append(x2).toString());
        data.HorizonCode_Horizon_È("y1", new StringBuilder().append(y1).toString());
        data.HorizonCode_Horizon_È("y2", new StringBuilder().append(y2).toString());
        diagram.HorizonCode_Horizon_È(new Figure(2, line, data, transform));
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final Element element) {
        return element.getNodeName().equals("line") || (element.getNodeName().equals("path") && !"arc".equals(element.getAttributeNS("http://sodipodi.sourceforge.net/DTD/sodipodi-0.dtd", "type")));
    }
}
