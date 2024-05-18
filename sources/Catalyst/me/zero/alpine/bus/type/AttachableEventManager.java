// 
// Decompiled by Procyon v0.5.36
// 

package me.zero.alpine.bus.type;

import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Listenable;
import java.util.ArrayList;
import me.zero.alpine.bus.EventBus;
import java.util.List;
import me.zero.alpine.bus.EventManager;

public class AttachableEventManager extends EventManager implements AttachableEventBus
{
    private final List<EventBus> attached;
    
    public AttachableEventManager() {
        this.attached = new ArrayList<EventBus>();
    }
    
    @Override
    public void subscribe(final Listenable listenable) {
        super.subscribe(listenable);
        if (!this.attached.isEmpty()) {
            this.attached.forEach(bus -> bus.subscribe(listenable));
        }
    }
    
    @Override
    public void subscribe(final Listener listener) {
        super.subscribe(listener);
        if (!this.attached.isEmpty()) {
            this.attached.forEach(bus -> bus.subscribe(listener));
        }
    }
    
    @Override
    public void unsubscribe(final Listenable listenable) {
        super.unsubscribe(listenable);
        if (!this.attached.isEmpty()) {
            this.attached.forEach(bus -> bus.unsubscribe(listenable));
        }
    }
    
    @Override
    public void unsubscribe(final Listener listener) {
        super.unsubscribe(listener);
        if (!this.attached.isEmpty()) {
            this.attached.forEach(bus -> bus.unsubscribe(listener));
        }
    }
    
    @Override
    public void post(final Object event) {
        super.post(event);
        if (!this.attached.isEmpty()) {
            this.attached.forEach(bus -> bus.post(event));
        }
    }
    
    @Override
    public void attach(final EventBus bus) {
        if (!this.attached.contains(bus)) {
            this.attached.add(bus);
        }
    }
    
    @Override
    public void detach(final EventBus bus) {
        this.attached.remove(bus);
    }
}
