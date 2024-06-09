package com.client.glowclient.sponge.mixin;

import net.minecraftforge.fml.relauncher.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraft.client.renderer.chunk.*;
import org.spongepowered.asm.mixin.*;

@SideOnly(Side.CLIENT)
@Mixin({ VisGraph.class })
public abstract class MixinVisGraph
{
    public MixinVisGraph() {
        super();
    }
    
    @Inject(method = { "setOpaqueCube" }, at = { @At("HEAD") }, cancellable = true)
    public void preSetOpaqueCube(final CallbackInfo callbackInfo) {
        callbackInfo.cancel();
    }
    
    @Overwrite
    public SetVisibility computeVisibility() {
        final SetVisibility setVisibility = new SetVisibility();
        setVisibility.setAllVisible(true);
        return setVisibility;
    }
}
