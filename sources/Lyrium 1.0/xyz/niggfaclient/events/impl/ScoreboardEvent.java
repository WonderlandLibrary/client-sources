// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.events.impl;

import xyz.niggfaclient.events.Event;

public class ScoreboardEvent implements Event
{
    public boolean pre;
    
    public ScoreboardEvent() {
        this.pre = true;
    }
    
    public boolean isPre() {
        return this.pre;
    }
    
    public void setPost() {
        this.pre = false;
    }
}
