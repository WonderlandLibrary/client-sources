package net.minecraft.src;

import java.util.concurrent.*;

class CallableScreenSize implements Callable
{
    final ScaledResolution theScaledResolution;
    final EntityRenderer theEntityRenderer;
    
    CallableScreenSize(final EntityRenderer par1EntityRenderer, final ScaledResolution par2ScaledResolution) {
        this.theEntityRenderer = par1EntityRenderer;
        this.theScaledResolution = par2ScaledResolution;
    }
    
    public String callScreenSize() {
        return String.format("Scaled: (%d, %d). Absolute: (%d, %d). Scale factor of %d", ScaledResolution.getScaledWidth(), ScaledResolution.getScaledHeight(), EntityRenderer.getRendererMinecraft(this.theEntityRenderer).displayWidth, EntityRenderer.getRendererMinecraft(this.theEntityRenderer).displayHeight, this.theScaledResolution.getScaleFactor());
    }
    
    @Override
    public Object call() {
        return this.callScreenSize();
    }
}
