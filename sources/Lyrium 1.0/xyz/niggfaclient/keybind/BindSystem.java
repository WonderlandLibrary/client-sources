// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.keybind;

import java.util.Iterator;
import java.util.HashSet;
import xyz.niggfaclient.module.Module;
import java.util.Collection;
import xyz.niggfaclient.eventbus.annotations.EventLink;
import xyz.niggfaclient.events.impl.KeyEvent;
import xyz.niggfaclient.eventbus.Listener;
import java.util.Set;

public class BindSystem
{
    public Set<Bindable> objects;
    @EventLink
    public final Listener<KeyEvent> keyEventListener;
    
    public BindSystem(final Collection<Module> modules) {
        final Iterator<Bindable> iterator;
        Bindable object;
        this.keyEventListener = (e -> {
            this.objects.iterator();
            while (iterator.hasNext()) {
                object = iterator.next();
                if (object.getKey() == e.getKey()) {
                    object.onPress();
                }
            }
            return;
        });
        this.objects = new HashSet<Bindable>(modules);
    }
    
    public void register(final Bindable object) {
        this.objects.add(object);
    }
}
