package net.minecraft.client.renderer.texture;

import java.util.concurrent.*;

class TextureAtlasSprite$1 implements Callable {
    final /* synthetic */ int[][] val$var4;
    
    @Override
    public String call() {
        final StringBuilder var1 = new StringBuilder();
        for (final int[] var4 : this.val$var4) {
            if (var1.length() > 0) {
                var1.append(", ");
            }
            var1.append((var4 == null) ? "null" : Integer.valueOf(var4.length));
        }
        return var1.toString();
    }
}