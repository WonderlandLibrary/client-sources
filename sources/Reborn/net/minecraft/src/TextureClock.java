package net.minecraft.src;

import net.minecraft.client.*;

public class TextureClock extends TextureStitched
{
    private double field_94239_h;
    private double field_94240_i;
    
    public TextureClock() {
        super("compass");
    }
    
    @Override
    public void updateAnimation() {
        final Minecraft var1 = Minecraft.getMinecraft();
        double var2 = 0.0;
        if (Minecraft.theWorld != null && Minecraft.thePlayer != null) {
            final float var3 = Minecraft.theWorld.getCelestialAngle(1.0f);
            var2 = var3;
            if (!Minecraft.theWorld.provider.isSurfaceWorld()) {
                var2 = Math.random();
            }
        }
        double var4;
        for (var4 = var2 - this.field_94239_h; var4 < -0.5; ++var4) {}
        while (var4 >= 0.5) {
            --var4;
        }
        if (var4 < -1.0) {
            var4 = -1.0;
        }
        if (var4 > 1.0) {
            var4 = 1.0;
        }
        this.field_94240_i += var4 * 0.1;
        this.field_94240_i *= 0.8;
        this.field_94239_h += this.field_94240_i;
        int var5;
        for (var5 = (int)((this.field_94239_h + 1.0) * this.textureList.size()) % this.textureList.size(); var5 < 0; var5 = (var5 + this.textureList.size()) % this.textureList.size()) {}
        if (var5 != this.frameCounter) {
            this.frameCounter = var5;
            this.textureSheet.copyFrom(this.originX, this.originY, this.textureList.get(this.frameCounter), this.rotated);
        }
    }
}
