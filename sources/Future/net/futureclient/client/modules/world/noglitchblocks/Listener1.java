package net.futureclient.client.modules.world.noglitchblocks;

import net.futureclient.client.events.Event;
import net.futureclient.client.modules.world.NoGlitchBlocks;
import net.futureclient.client.Be;
import net.futureclient.client.n;

public class Listener1 extends n<Be>
{
    public final NoGlitchBlocks k;
    
    public Listener1(final NoGlitchBlocks k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((Be)event);
    }
    
    @Override
    public void M(final Be be) {
        if (!NoGlitchBlocks.M(this.k).M()) {
            return;
        }
        be.M(true);
    }
}
