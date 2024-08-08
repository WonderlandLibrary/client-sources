package net.futureclient.client.modules.render;

import net.futureclient.client.rC;
import net.futureclient.client.qA;
import net.futureclient.client.modules.render.viewclip.Listener1;
import net.futureclient.client.n;
import net.futureclient.client.utils.Value;
import net.futureclient.client.Category;
import net.futureclient.client.utils.NumberValue;
import net.futureclient.client.Ea;

public class ViewClip extends Ea
{
    public NumberValue distance;
    
    public ViewClip() {
        super("ViewClip", new String[] { "ViewClip", "F5", "CameraClip" }, true, -546453, Category.RENDER);
        this.distance = new NumberValue(3.5f, 0.0f, 10.0f, 1.273197475E-314, new String[] { "Distance", "Length", "Lenght", "Far", "d", "l" });
        this.M(new Value[] { this.distance });
        this.M(new n[] { new Listener1(this) });
        this.M(new qA[] { new rC(this, "Normal") });
    }
}
