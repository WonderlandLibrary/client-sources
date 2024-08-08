package net.futureclient.client.modules.world.noglitchblocks;

import net.futureclient.client.events.Event;
import net.futureclient.client.modules.world.NoGlitchBlocks;
import net.futureclient.client.QF;
import net.futureclient.client.n;

public class Listener2 extends n<QF>
{
    public final NoGlitchBlocks k;
    
    public Listener2(final NoGlitchBlocks k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((QF)event);
    }
    
    @Override
    public void M(final QF qf) {
        if (!NoGlitchBlocks.e(this.k).M() || !NoGlitchBlocks.getMinecraft().player.onGround) {
            return;
        }
        qf.M(true);
    }
}
