package net.minecraft.client.audio;

import net.minecraft.entity.player.*;
import net.minecraft.entity.item.*;
import net.minecraft.util.*;

public class MovingSoundMinecartRiding extends MovingSound
{
    private static final String[] I;
    private final EntityPlayer player;
    private final EntityMinecart minecart;
    
    @Override
    public void update() {
        if (!this.minecart.isDead && this.player.isRiding() && this.player.ridingEntity == this.minecart) {
            final float sqrt_double = MathHelper.sqrt_double(this.minecart.motionX * this.minecart.motionX + this.minecart.motionZ * this.minecart.motionZ);
            if (sqrt_double >= 0.01) {
                this.volume = 0.0f + MathHelper.clamp_float(sqrt_double, 0.0f, 1.0f) * 0.75f;
                "".length();
                if (1 == 3) {
                    throw null;
                }
            }
            else {
                this.volume = 0.0f;
                "".length();
                if (4 == 2) {
                    throw null;
                }
            }
        }
        else {
            this.donePlaying = (" ".length() != 0);
        }
    }
    
    public MovingSoundMinecartRiding(final EntityPlayer player, final EntityMinecart minecart) {
        super(new ResourceLocation(MovingSoundMinecartRiding.I["".length()]));
        this.player = player;
        this.minecart = minecart;
        this.attenuationType = ISound.AttenuationType.NONE;
        this.repeat = (" ".length() != 0);
        this.repeatDelay = "".length();
    }
    
    static {
        I();
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
            if (4 != 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("/;\u001e\u001d\u000b03\u0016\fR/;\u001e\u001d\u000b# \u0004V\u0001,!\u0019\u001c\r", "BRpxh");
    }
}
