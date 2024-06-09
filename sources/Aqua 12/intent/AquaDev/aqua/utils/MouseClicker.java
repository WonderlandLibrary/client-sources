// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.utils;

import org.lwjgl.input.Mouse;

public class MouseClicker
{
    private boolean next;
    
    public MouseClicker() {
        this.next = true;
    }
    
    public void stop() {
        this.next = false;
    }
    
    public boolean isNext() {
        return this.next;
    }
    
    public void release(final int button) {
        if (!Mouse.isButtonDown(button)) {
            this.next = true;
        }
    }
}
