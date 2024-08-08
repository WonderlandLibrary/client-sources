package net.futureclient.client.modules.miscellaneous;

import net.futureclient.client.utils.Value;
import net.futureclient.client.Category;
import net.futureclient.client.utils.NumberValue;
import net.futureclient.client.Ea;

public class AutoReconnect extends Ea
{
    public NumberValue delay;
    
    public AutoReconnect() {
        super("AutoReconnect", new String[] { "AutoReconnect", "Reconnect", "AutoConnect" }, true, -11184743, Category.MISCELLANEOUS);
        this.delay = new NumberValue(5.0f, 0.0f, 100.0f, new String[] { "Delay", "Timer" });
        this.M(new Value[] { this.delay });
    }
}
