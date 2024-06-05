package net.minecraft.client.renderer;

import java.util.concurrent.*;
import org.lwjgl.input.*;

class EntityRenderer$2 implements Callable {
    final /* synthetic */ int val$var161;
    final /* synthetic */ int val$var171;
    
    @Override
    public String call() {
        return String.format("Scaled: (%d, %d). Absolute: (%d, %d)", this.val$var161, this.val$var171, Mouse.getX(), Mouse.getY());
    }
}