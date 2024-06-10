// 
// Decompiled by KaktusWasser
// https://www.youtube.com/KaktusWasserReal

package me.kaktuswasser.client.event.events;

import me.kaktuswasser.client.event.Event;

public class PreStep extends Event
{
    public double stepHeight;
    public boolean bypass;
    
    public PreStep(final double stepHeight) {
        this.stepHeight = stepHeight;
    }
}
