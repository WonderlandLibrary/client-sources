package net.futureclient.client;

import java.util.Iterator;
import net.futureclient.client.events.EventWorld;
import net.futureclient.client.events.Event;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.client.Minecraft;
import java.util.List;

public class hG implements b
{
    private List<n> D;
    private Minecraft k;
    
    public hG() {
        super();
        this.D = new CopyOnWriteArrayList<n>();
        this.k = Minecraft.getMinecraft();
    }
    
    @Override
    public void e(final n n) {
        if (n != null && this.M(n)) {
            this.D.remove(n);
        }
    }
    
    @Override
    public void M(final Event event) {
        if (!this.D.isEmpty() && event != null) {
            final Iterator<n> iterator = this.D.iterator();
            while (iterator.hasNext()) {
                final n n;
                if ((n = iterator.next()).M().equals(event.getClass()) && (event instanceof gf || event instanceof BF || event instanceof EventWorld || (this.k.player != null && this.k.world != null))) {
                    n.M(event);
                }
            }
        }
    }
    
    @Override
    public void M(final n n) {
        if (n != null && !this.M(n)) {
            this.D.add(n);
        }
    }
    
    @Override
    public void M() {
        if (!this.D.isEmpty()) {
            this.D.clear();
        }
    }
    
    public boolean M(final n n) {
        return this.D.contains(n);
    }
}
