/*
 * Decompiled with CFR 0.143.
 */
package net.minecraft.client.renderer.texture;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.util.MathHelper;
import net.minecraft.world.WorldProvider;

public class TextureClock
extends TextureAtlasSprite {
    private double field_94239_h;
    private double field_94240_i;
    private static final String __OBFID = "CL_00001070";

    public TextureClock(String p_i1285_1_) {
        super(p_i1285_1_);
    }

    @Override
    public void updateAnimation() {
        if (!this.framesTextureData.isEmpty()) {
            double var7;
            Minecraft var1 = Minecraft.getMinecraft();
            double var2 = 0.0;
            if (Minecraft.theWorld != null && Minecraft.thePlayer != null) {
                float var4 = Minecraft.theWorld.getCelestialAngle(1.0f);
                var2 = var4;
                if (!Minecraft.theWorld.provider.isSurfaceWorld()) {
                    var2 = Math.random();
                }
            }
            for (var7 = var2 - this.field_94239_h; var7 < -0.5; var7 += 1.0) {
            }
            while (var7 >= 0.5) {
                var7 -= 1.0;
            }
            var7 = MathHelper.clamp_double(var7, -1.0, 1.0);
            this.field_94240_i += var7 * 0.1;
            this.field_94240_i *= 0.8;
            this.field_94239_h += this.field_94240_i;
            int var6 = (int)((this.field_94239_h + 1.0) * (double)this.framesTextureData.size()) % this.framesTextureData.size();
            while (var6 < 0) {
                var6 = (var6 + this.framesTextureData.size()) % this.framesTextureData.size();
            }
            if (var6 != this.frameCounter) {
                this.frameCounter = var6;
                TextureUtil.uploadTextureMipmap((int[][])this.framesTextureData.get(this.frameCounter), this.width, this.height, this.originX, this.originY, false, false);
            }
        }
    }
}

