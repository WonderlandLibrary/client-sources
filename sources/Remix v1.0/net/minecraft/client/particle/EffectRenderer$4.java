package net.minecraft.client.particle;

import java.util.concurrent.*;

class EffectRenderer$4 implements Callable {
    final /* synthetic */ int val$var8;
    
    @Override
    public String call() {
        return (this.val$var8 == 0) ? "MISC_TEXTURE" : ((this.val$var8 == 1) ? "TERRAIN_TEXTURE" : ((this.val$var8 == 3) ? "ENTITY_PARTICLE_TEXTURE" : ("Unknown - " + this.val$var8)));
    }
}