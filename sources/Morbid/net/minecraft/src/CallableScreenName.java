package net.minecraft.src;

import java.util.concurrent.*;
import net.minecraft.client.*;

class CallableScreenName implements Callable
{
    final EntityRenderer entityRender;
    
    CallableScreenName(final EntityRenderer par1EntityRenderer) {
        this.entityRender = par1EntityRenderer;
    }
    
    public String callScreenName() {
        EntityRenderer.getRendererMinecraft(this.entityRender);
        return Minecraft.currentScreen.getClass().getCanonicalName();
    }
    
    @Override
    public Object call() {
        return this.callScreenName();
    }
}
