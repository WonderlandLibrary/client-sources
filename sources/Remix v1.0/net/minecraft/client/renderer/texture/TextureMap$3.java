package net.minecraft.client.renderer.texture;

import java.util.concurrent.*;

class TextureMap$3 implements Callable {
    final /* synthetic */ TextureAtlasSprite val$var281;
    
    @Override
    public String call() {
        return this.val$var281.getFrameCount() + " frames";
    }
}