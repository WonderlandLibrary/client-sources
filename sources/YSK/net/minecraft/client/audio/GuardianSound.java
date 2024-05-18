package net.minecraft.client.audio;

import net.minecraft.entity.monster.*;
import net.minecraft.util.*;

public class GuardianSound extends MovingSound
{
    private static final String[] I;
    private final EntityGuardian guardian;
    
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
            if (1 == 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public GuardianSound(final EntityGuardian guardian) {
        super(new ResourceLocation(GuardianSound.I["".length()]));
        this.guardian = guardian;
        this.attenuationType = ISound.AttenuationType.NONE;
        this.repeat = (" ".length() != 0);
        this.repeatDelay = "".length();
    }
    
    @Override
    public void update() {
        if (!this.guardian.isDead && this.guardian.hasTargetedEntity()) {
            this.xPosF = (float)this.guardian.posX;
            this.yPosF = (float)this.guardian.posY;
            this.zPosF = (float)this.guardian.posZ;
            final float func_175477_p = this.guardian.func_175477_p(0.0f);
            this.volume = 0.0f + 1.0f * func_175477_p * func_175477_p;
            this.pitch = 0.7f + 0.5f * func_175477_p;
            "".length();
            if (0 >= 1) {
                throw null;
            }
        }
        else {
            this.donePlaying = (" ".length() != 0);
        }
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("<\u0003&#\u0007#\u000b.2^<\u0005*h\u0003$\u000b:\"\r0\u0004f'\u0010%\u000b+-", "QjHFd");
    }
}
