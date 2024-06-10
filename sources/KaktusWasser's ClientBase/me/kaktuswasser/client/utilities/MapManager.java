// 
// Decompiled by KaktusWasser
// https://www.youtube.com/KaktusWasserReal
// 

package me.kaktuswasser.client.utilities;

import java.util.Map;

public abstract class MapManager<T, U>
{
    protected Map<T, U> contents;
    
    public final Map<T, U> getContents() {
        return this.contents;
    }
    
    public abstract void setup();
}
