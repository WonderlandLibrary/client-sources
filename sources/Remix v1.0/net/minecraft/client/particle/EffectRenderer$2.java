package net.minecraft.client.particle;

import java.util.concurrent.*;

class EffectRenderer$2 implements Callable {
    final /* synthetic */ int val$var5;
    
    @Override
    public String call() {
        return (this.val$var5 == 0) ? "MISC_TEXTURE" : ((this.val$var5 == 1) ? "TERRAIN_TEXTURE" : ((this.val$var5 == 3) ? "ENTITY_PARTICLE_TEXTURE" : ("Unknown - " + this.val$var5)));
    }
}