// 
// Decompiled by KaktusWasser
// https://www.youtube.com/KaktusWasserReal

package me.kaktuswasser.client.event.events;

import me.kaktuswasser.client.event.Event;

public class MouseClicked extends Event
{
    private int button;
    
    public MouseClicked(final int button) {
        this.button = button;
    }
    
    public int getButton() {
        return this.button;
    }
}
