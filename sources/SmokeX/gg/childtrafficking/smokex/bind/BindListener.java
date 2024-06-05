// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.bind;

import java.util.Iterator;
import gg.childtrafficking.smokex.SmokeXClient;
import gg.childtrafficking.smokex.event.events.system.EventKeyPress;
import gg.childtrafficking.smokex.event.EventListener;

public final class BindListener
{
    private final EventListener<EventKeyPress> keyPressEventEventCallback;
    
    public BindListener() {
        this.keyPressEventEventCallback = (event -> {
            SmokeXClient.getInstance().getBindManager().getBindables().iterator();
            final Iterator iterator;
            while (iterator.hasNext()) {
                final Bindable bindable = iterator.next();
                if (bindable.getKey() == event.getKey()) {
                    bindable.press();
                }
            }
        });
    }
}
