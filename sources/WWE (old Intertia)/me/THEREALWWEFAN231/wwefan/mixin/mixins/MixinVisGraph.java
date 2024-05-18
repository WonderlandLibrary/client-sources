package me.THEREALWWEFAN231.wwefan.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.renderer.chunk.*;
import net.minecraft.util.math.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import wwe.modules.render.*;
import wwe.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(value = { VisGraph.class }, priority = 9999)
public class MixinVisGraph
{
    public MixinVisGraph() {
        super();
    }
    
    @Inject(method = { "setOpaqueCube" }, at = { @At("HEAD") }, cancellable = true)
    public void setOpaqueCube(final BlockPos blockPos, final CallbackInfo callbackInfo) {
        try {
            if (vlwv.hNxW.qHPF((Class<Xray>)jCBS.class).IuiN()) {
                callbackInfo.cancel();
            }
        }
        catch (Exception ex) {}
    }
}
