package net.futureclient.client.modules.render;

import net.futureclient.client.utils.Value;
import net.futureclient.client.Category;
import net.futureclient.client.utils.NumberValue;
import net.futureclient.client.Ea;

public class SmallShield extends Ea
{
    public NumberValue height;
    
    public SmallShield() {
        super("SmallShield", new String[] { "SmallShield", "SmallHand", "Small" }, true, -7217221, Category.RENDER);
        this.height = new NumberValue(0.5f, 0.0f, 1.0f, new String[] { "Height", "Heigh", "Hight", "High", "Size", "Scaling", "Scale" });
        this.M(new Value[] { this.height });
    }
}
