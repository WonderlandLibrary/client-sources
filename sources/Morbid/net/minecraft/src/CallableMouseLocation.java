package net.minecraft.src;

import java.util.concurrent.*;
import org.lwjgl.input.*;

class CallableMouseLocation implements Callable
{
    final int field_90026_a;
    final int field_90024_b;
    final EntityRenderer theEntityRenderer;
    
    CallableMouseLocation(final EntityRenderer par1EntityRenderer, final int par2, final int par3) {
        this.theEntityRenderer = par1EntityRenderer;
        this.field_90026_a = par2;
        this.field_90024_b = par3;
    }
    
    public String callMouseLocation() {
        return String.format("Scaled: (%d, %d). Absolute: (%d, %d)", this.field_90026_a, this.field_90024_b, Mouse.getX(), Mouse.getY());
    }
    
    @Override
    public Object call() {
        return this.callMouseLocation();
    }
}
