package cn.Hanabi.injection.mixins;

import cn.Hanabi.injection.interfaces.*;
import net.minecraft.client.renderer.entity.*;
import org.spongepowered.asm.mixin.*;

@Mixin({ RenderManager.class })
public class MixinRenderManager implements IRenderManager
{
    @Shadow
    private double renderPosX;
    @Shadow
    private double renderPosY;
    @Shadow
    private double renderPosZ;
    
    @Override
    public double getRenderPosX() {
        return this.renderPosX;
    }
    
    @Override
    public double getRenderPosY() {
        return this.renderPosY;
    }
    
    @Override
    public double getRenderPosZ() {
        return this.renderPosZ;
    }
}
