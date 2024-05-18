package HORIZON-6-0-SKIDPROTECTION;

import org.w3c.dom.Element;

public class GroupProcessor implements ElementProcessor
{
    @Override
    public boolean HorizonCode_Horizon_È(final Element element) {
        return element.getNodeName().equals("g");
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Loader loader, final Element element, final Diagram diagram, final Transform t) throws ParsingException {
        Transform transform = Util.Ý(element);
        transform = new Transform(t, transform);
        loader.HorizonCode_Horizon_È(element, transform);
    }
}
