package net.futureclient.client.modules.movement.sprint;

import net.futureclient.client.events.Event;
import net.minecraft.client.settings.KeyBinding;
import net.futureclient.client.hC;
import net.futureclient.client.id;
import net.futureclient.client.events.EventUpdate;
import net.futureclient.client.modules.movement.Sprint;
import net.futureclient.client.lF;
import net.futureclient.client.n;

public class Listener1 extends n<lF>
{
    public final Sprint k;
    
    public Listener1(final Sprint k) {
        this.k = k;
        super();
    }
    
    public void M(final EventUpdate eventUpdate) {
        this.k.e(String.format("Sprint §7[§F%s§7]", Sprint.M(this.k).M()));
        switch (id.k[((hC.OA)Sprint.M(this.k).M()).ordinal()]) {
            case 1:
                KeyBinding.setKeyBindState(Sprint.getMinecraft().gameSettings.keyBindSprint.getKeyCode(), true);
            case 2:
                if (Sprint.M(this.k)) {
                    Sprint.getMinecraft1().player.setSprinting(true);
                    break;
                }
                break;
        }
    }
    
    public void M(final Event event) {
        this.M((EventUpdate)event);
    }
}
