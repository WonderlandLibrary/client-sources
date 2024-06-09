// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.utilities;

import java.util.Map;

public abstract class MapManager<T, U>
{
    protected Map<T, U> contents;
    
    public final Map<T, U> getContents() {
        return this.contents;
    }
    
    public abstract void setup();
}
