package net.minecraft.client.renderer;

import java.util.concurrent.*;

class EntityRenderer$1 implements Callable {
    @Override
    public String call() {
        final EntityRenderer this$0 = EntityRenderer.this;
        return EntityRenderer.access$000().currentScreen.getClass().getCanonicalName();
    }
}