package net.futureclient.client.modules.world.fastplace;

import net.minecraft.client.settings.KeyBinding;
import net.futureclient.client.IE;
import net.futureclient.loader.mixin.common.wrapper.IMinecraft;
import net.futureclient.client.events.EventUpdate;
import net.futureclient.client.events.Event;
import net.futureclient.client.modules.world.FastPlace;
import net.futureclient.client.lF;
import net.futureclient.client.n;

public class Listener1 extends n<lF>
{
    public final FastPlace k;
    
    public Listener1(final FastPlace k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((EventUpdate)event);
    }
    
    public void M(final EventUpdate eventUpdate) {
        if (((IMinecraft)FastPlace.getMinecraft()).getRightClickDelayTimer() > FastPlace.M(this.k).B().intValue()) {
            ((IMinecraft)FastPlace.getMinecraft4()).setRightClickDelayTimer(FastPlace.M(this.k).B().intValue());
        }
        if (FastPlace.M(this.k).M()) {
            ((IMinecraft)FastPlace.getMinecraft2()).clickMouse(IE.RD.D);
        }
        if (FastPlace.e(this.k).M()) {
            KeyBinding.setKeyBindState(FastPlace.getMinecraft5().gameSettings.keyBindJump.getKeyCode(), true);
        }
        if (FastPlace.b(this.k).M()) {
            int currentItem = FastPlace.getMinecraft3().player.inventory.currentItem;
            if (++currentItem == 9) {
                currentItem = 0;
            }
            FastPlace.getMinecraft1().player.inventory.currentItem = currentItem;
        }
    }
}
