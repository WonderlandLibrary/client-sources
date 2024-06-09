package HORIZON-6-0-SKIDPROTECTION;

import org.w3c.dom.Element;

public class UseProcessor implements ElementProcessor
{
    @Override
    public boolean HorizonCode_Horizon_È(final Element element) {
        return element.getNodeName().equals("use");
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Loader loader, final Element element, final Diagram diagram, final Transform transform) throws ParsingException {
        final String ref = element.getAttributeNS("http://www.w3.org/1999/xlink", "href");
        final String href = Util.HorizonCode_Horizon_È(ref);
        final Figure referenced = diagram.Ý(href);
        if (referenced == null) {
            throw new ParsingException(element, "Unable to locate referenced element: " + href);
        }
        final Transform local = Util.Ý(element);
        final Transform trans = local.HorizonCode_Horizon_È(referenced.HorizonCode_Horizon_È());
        final NonGeometricData data = Util.HorizonCode_Horizon_È(element);
        final Shape shape = referenced.Ý().HorizonCode_Horizon_È(trans);
        data.HorizonCode_Horizon_È("fill", referenced.Ø­áŒŠá().Â("fill"));
        data.HorizonCode_Horizon_È("stroke", referenced.Ø­áŒŠá().Â("stroke"));
        data.HorizonCode_Horizon_È("opacity", referenced.Ø­áŒŠá().Â("opacity"));
        data.HorizonCode_Horizon_È("stroke-width", referenced.Ø­áŒŠá().Â("stroke-width"));
        final Figure figure = new Figure(referenced.Â(), shape, data, trans);
        diagram.HorizonCode_Horizon_È(figure);
    }
}
