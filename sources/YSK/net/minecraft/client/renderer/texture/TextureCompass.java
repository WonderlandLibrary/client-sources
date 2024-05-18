package net.minecraft.client.renderer.texture;

import net.minecraft.client.*;
import net.minecraft.world.*;
import net.minecraft.util.*;

public class TextureCompass extends TextureAtlasSprite
{
    public double angleDelta;
    public double currentAngle;
    public static String field_176608_l;
    
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
            if (3 != 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void updateAnimation() {
        final Minecraft minecraft = Minecraft.getMinecraft();
        if (minecraft.theWorld != null && minecraft.thePlayer != null) {
            this.updateCompass(minecraft.theWorld, minecraft.thePlayer.posX, minecraft.thePlayer.posZ, minecraft.thePlayer.rotationYaw, "".length() != 0, "".length() != 0);
            "".length();
            if (4 == 1) {
                throw null;
            }
        }
        else {
            this.updateCompass(null, 0.0, 0.0, 0.0, " ".length() != 0, "".length() != 0);
        }
    }
    
    public void updateCompass(final World world, final double n, final double n2, double n3, final boolean b, final boolean b2) {
        if (!this.framesTextureData.isEmpty()) {
            double currentAngle = 0.0;
            if (world != null && !b) {
                final BlockPos spawnPoint = world.getSpawnPoint();
                final double n4 = spawnPoint.getX() - n;
                final double n5 = spawnPoint.getZ() - n2;
                n3 %= 360.0;
                currentAngle = -((n3 - 90.0) * 3.141592653589793 / 180.0 - Math.atan2(n5, n4));
                if (!world.provider.isSurfaceWorld()) {
                    currentAngle = Math.random() * 3.141592653589793 * 2.0;
                }
            }
            if (b2) {
                this.currentAngle = currentAngle;
                "".length();
                if (0 >= 2) {
                    throw null;
                }
            }
            else {
                double n6 = currentAngle - this.currentAngle;
                "".length();
                if (0 >= 1) {
                    throw null;
                }
                while (n6 < -3.141592653589793) {
                    n6 += 6.283185307179586;
                }
                "".length();
                if (2 == 0) {
                    throw null;
                }
                while (n6 >= 3.141592653589793) {
                    n6 -= 6.283185307179586;
                }
                this.angleDelta += MathHelper.clamp_double(n6, -1.0, 1.0) * 0.1;
                this.angleDelta *= 0.8;
                this.currentAngle += this.angleDelta;
            }
            int i = (int)((this.currentAngle / 6.283185307179586 + 1.0) * this.framesTextureData.size()) % this.framesTextureData.size();
            "".length();
            if (-1 >= 0) {
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
    
    public TextureCompass(final String field_176608_l) {
        super(field_176608_l);
        TextureCompass.field_176608_l = field_176608_l;
    }
}
