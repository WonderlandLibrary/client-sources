package net.minecraft.client.renderer.texture;

import java.util.concurrent.*;

class TextureMap$2 implements Callable {
    final /* synthetic */ TextureAtlasSprite val$var281;
    
    @Override
    public String call() {
        return this.val$var281.getIconWidth() + " x " + this.val$var281.getIconHeight();
    }
}