package net.minecraft.client.renderer;

import java.util.concurrent.*;
import net.minecraft.client.gui.*;

class EntityRenderer$3 implements Callable {
    final /* synthetic */ ScaledResolution val$var131;
    
    @Override
    public String call() {
        final String s = "Scaled: (%d, %d). Absolute: (%d, %d). Scale factor of %d";
        final Object[] array = { this.val$var131.getScaledWidth(), this.val$var131.getScaledHeight(), null, null, null };
        final int n = 2;
        final EntityRenderer this$0 = EntityRenderer.this;
        array[n] = EntityRenderer.access$000().displayWidth;
        final int n2 = 3;
        final EntityRenderer this$2 = EntityRenderer.this;
        array[n2] = EntityRenderer.access$000().displayHeight;
        array[4] = this.val$var131.getScaleFactor();
        return String.format(s, array);
    }
}