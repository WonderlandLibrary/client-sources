package net.futureclient.client.modules.movement.autowalk;

import net.minecraft.client.settings.KeyBinding;
import net.futureclient.client.events.EventUpdate;
import net.futureclient.client.events.Event;
import net.futureclient.client.modules.movement.AutoWalk;
import net.futureclient.client.lF;
import net.futureclient.client.n;

public class Listener1 extends n<lF>
{
    public final AutoWalk k;
    
    public Listener1(final AutoWalk k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((EventUpdate)event);
    }
    
    public void M(final EventUpdate eventUpdate) {
        if (!AutoWalk.getMinecraft().gameSettings.keyBindForward.isKeyDown()) {
            KeyBinding.setKeyBindState(AutoWalk.getMinecraft1().gameSettings.keyBindForward.getKeyCode(), true);
        }
    }
}
