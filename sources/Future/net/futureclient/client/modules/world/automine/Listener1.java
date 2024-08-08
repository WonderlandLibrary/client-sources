package net.futureclient.client.modules.world.automine;

import net.minecraft.client.settings.KeyBinding;
import net.futureclient.client.events.EventUpdate;
import net.futureclient.client.events.Event;
import net.futureclient.client.modules.world.AutoMine;
import net.futureclient.client.lF;
import net.futureclient.client.n;

public class Listener1 extends n<lF>
{
    public final AutoMine k;
    
    public Listener1(final AutoMine k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((EventUpdate)event);
    }
    
    public void M(final EventUpdate eventUpdate) {
        KeyBinding.setKeyBindState(AutoMine.getMinecraft().gameSettings.keyBindAttack.getKeyCode(), true);
    }
}
