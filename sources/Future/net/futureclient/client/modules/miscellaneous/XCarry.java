package net.futureclient.client.modules.miscellaneous;

import net.futureclient.client.modules.miscellaneous.xcarry.Listener2;
import net.futureclient.client.modules.miscellaneous.xcarry.Listener1;
import net.futureclient.client.n;
import net.futureclient.client.Category;
import net.futureclient.client.utils.Value;
import net.futureclient.client.Ea;

public class XCarry extends Ea
{
    private final Value<Boolean> forceCancel;
    
    public XCarry() {
        super("XCarry", new String[] { "XCarry", "MoreCarry", "MoreInventory", "MoreInv" }, true, -4191950, Category.MISCELLANEOUS);
        this.forceCancel = new Value<Boolean>(false, new String[] { "ForceCancel", "Force" });
        this.M(new Value[] { this.forceCancel });
        this.M(new n[] { new Listener1(this), new Listener2(this) });
    }
    
    public static Value M(final XCarry xCarry) {
        return xCarry.forceCancel;
    }
}
