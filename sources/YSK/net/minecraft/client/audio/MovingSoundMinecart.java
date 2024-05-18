package net.minecraft.client.audio;

import net.minecraft.entity.item.*;
import net.minecraft.util.*;

public class MovingSoundMinecart extends MovingSound
{
    private float distance;
    private static final String[] I;
    private final EntityMinecart minecart;
    
    public MovingSoundMinecart(final EntityMinecart minecart) {
        super(new ResourceLocation(MovingSoundMinecart.I["".length()]));
        this.distance = 0.0f;
        this.minecart = minecart;
        this.repeat = (" ".length() != 0);
        this.repeatDelay = "".length();
    }
    
    @Override
    public void update() {
        if (this.minecart.isDead) {
            this.donePlaying = (" ".length() != 0);
            "".length();
            if (3 <= -1) {
                throw null;
            }
        }
        else {
            this.xPosF = (float)this.minecart.posX;
            this.yPosF = (float)this.minecart.posY;
            this.zPosF = (float)this.minecart.posZ;
            final float sqrt_double = MathHelper.sqrt_double(this.minecart.motionX * this.minecart.motionX + this.minecart.motionZ * this.minecart.motionZ);
            if (sqrt_double >= 0.01) {
                this.distance = MathHelper.clamp_float(this.distance + 0.0025f, 0.0f, 1.0f);
                this.volume = 0.0f + MathHelper.clamp_float(sqrt_double, 0.0f, 0.5f) * 0.7f;
                "".length();
                if (2 >= 3) {
                    throw null;
                }
            }
            else {
                this.distance = 0.0f;
                this.volume = 0.0f;
            }
        }
    }
    
    static {
        I();
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("'#\u0019\b*8+\u0011\u0019s'#\u0019\b*+8\u0003C++9\u0012", "JJwmI");
    }
    
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
            if (2 == 1) {
                throw null;
            }
        }
        return sb.toString();
    }
}
