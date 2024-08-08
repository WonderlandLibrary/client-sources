package net.futureclient.client.modules.movement;

import net.minecraft.client.Minecraft;
import net.futureclient.client.modules.movement.entitycontrol.Listener4;
import net.futureclient.client.modules.movement.entitycontrol.Listener3;
import net.futureclient.client.modules.movement.entitycontrol.Listener2;
import net.futureclient.client.modules.movement.entitycontrol.Listener1;
import net.futureclient.client.n;
import net.futureclient.client.Category;
import net.futureclient.client.mh;
import net.futureclient.client.utils.NumberValue;
import net.futureclient.client.utils.Value;
import net.futureclient.client.Ea;

public class EntityControl extends Ea
{
    private Value<Boolean> control;
    private NumberValue D;
    private Value<Boolean> noPigAI;
    
    public EntityControl() {
        super("EntityControl", new String[] { mh.M("\u001b.*)*9\u001d/04,/2"), "HorseJumpPower", mh.M("\u0016/,3;\n+-."), "HJ" }, true, -15424990, Category.MOVEMENT);
        this.D = new NumberValue(8.48798316E-315, 0.0, 0.0, 1.273197475E-314, new String[] { mh.M("\n+-.\u0013*2;.946"), "JumpHeight", mh.M("\u0013*2;.946"), "JumpStr", mh.M("\r4,") });
        this.control = new Value<Boolean>(true, new String[] { "Control", mh.M("\f):)0'\u001d/04,/2") });
        this.noPigAI = new Value<Boolean>(false, new String[] { "No Pig AI", mh.M("\u00107'~\u0001\u0017"), "antipigai", mh.M("0/.)9!7"), "pigai", mh.M("!7"), "pig" });
        this.M(new Value[] { this.control, this.D, this.noPigAI });
        this.M(new n[] { new Listener1(this), new Listener2(this), new Listener3(this), new Listener4(this) });
    }
    
    public static Value e(final EntityControl entityControl) {
        return entityControl.noPigAI;
    }
    
    public static Minecraft getMinecraft() {
        return EntityControl.D;
    }
    
    public static Value M(final EntityControl entityControl) {
        return entityControl.control;
    }
    
    public static NumberValue M(final EntityControl entityControl) {
        return entityControl.D;
    }
}
