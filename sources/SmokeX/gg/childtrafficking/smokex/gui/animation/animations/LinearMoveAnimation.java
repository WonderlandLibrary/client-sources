// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.gui.animation.animations;

import gg.childtrafficking.smokex.gui.element.Element;
import gg.childtrafficking.smokex.gui.animation.Animation;

public class LinearMoveAnimation extends Animation
{
    public LinearMoveAnimation(final float x, final float y, final long duration) {
        super(duration);
        this.x = x;
        this.y = y;
    }
    
    @Override
    public boolean process(final double partialTicks, final Element element) {
        if (element.isAtPosition(this.x, this.y)) {
            return true;
        }
        final float completion = Math.min(this.timerUtil.elapsed() / (float)this.duration, 1.0f);
        element.setPosition(this.startX + (this.x - this.startX) * completion, this.startY + (this.y - this.startY) * completion);
        if (completion == 1.0f) {
            element.setPosition(this.x, this.y);
        }
        return false;
    }
}
