// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.gui.animation;

import gg.childtrafficking.smokex.gui.element.Element;
import gg.childtrafficking.smokex.utils.system.TimerUtil;

public class Animation
{
    public float startX;
    public float startY;
    public float startWidth;
    public float startHeight;
    public float x;
    public float y;
    public float width;
    public float height;
    public long duration;
    public TimerUtil timerUtil;
    
    public Animation(final long duration) {
        this.duration = duration;
        this.timerUtil = new TimerUtil();
    }
    
    public boolean process(final double partialTicks, final Element element) {
        return true;
    }
}
