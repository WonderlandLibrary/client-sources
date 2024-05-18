package net.minecraft.client.renderer.texture;

import net.minecraft.client.*;
import net.minecraft.util.*;

public class TextureClock extends TextureAtlasSprite
{
    private double field_94239_h;
    private double field_94240_i;
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (4 < 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void updateAnimation() {
        if (!this.framesTextureData.isEmpty()) {
            final Minecraft minecraft = Minecraft.getMinecraft();
            double random = 0.0;
            if (minecraft.theWorld != null && minecraft.thePlayer != null) {
                random = minecraft.theWorld.getCelestialAngle(1.0f);
                if (!minecraft.theWorld.provider.isSurfaceWorld()) {
                    random = Math.random();
                }
            }
            double n = random - this.field_94239_h;
            "".length();
            if (true != true) {
                throw null;
            }
            while (n < -0.5) {
                ++n;
            }
            "".length();
            if (-1 != -1) {
                throw null;
            }
            while (n >= 0.5) {
                --n;
            }
            this.field_94240_i += MathHelper.clamp_double(n, -1.0, 1.0) * 0.1;
            this.field_94240_i *= 0.8;
            this.field_94239_h += this.field_94240_i;
            int i = (int)((this.field_94239_h + 1.0) * this.framesTextureData.size()) % this.framesTextureData.size();
            "".length();
            if (3 < 2) {
                throw null;
            }
            while (i < 0) {
                i = (i + this.framesTextureData.size()) % this.framesTextureData.size();
            }
            if (i != this.frameCounter) {
                this.frameCounter = i;
                TextureUtil.uploadTextureMipmap(this.framesTextureData.get(this.frameCounter), this.width, this.height, this.originX, this.originY, "".length() != 0, "".length() != 0);
            }
        }
    }
    
    public TextureClock(final String s) {
        super(s);
    }
}
