package net.minecraft.client.audio;

import net.minecraft.util.*;

public interface ISound
{
    float getVolume();
    
    float getPitch();
    
    float getYPosF();
    
    float getZPosF();
    
    AttenuationType getAttenuationType();
    
    ResourceLocation getSoundLocation();
    
    int getRepeatDelay();
    
    float getXPosF();
    
    boolean canRepeat();
    
    public enum AttenuationType
    {
        NONE(AttenuationType.I["".length()], "".length(), "".length()), 
        LINEAR(AttenuationType.I[" ".length()], " ".length(), "  ".length());
        
        private final int type;
        private static final AttenuationType[] ENUM$VALUES;
        private static final String[] I;
        
        private static void I() {
            (I = new String["  ".length()])["".length()] = I("#\u0015\u00171", "mZYtS");
            AttenuationType.I[" ".length()] = I("\u0004\u0011\u0017\u0011'\u001a", "HXYTf");
        }
        
        private AttenuationType(final String s, final int n, final int type) {
            this.type = type;
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
                if (1 <= 0) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        static {
            I();
            final AttenuationType[] enum$VALUES = new AttenuationType["  ".length()];
            enum$VALUES["".length()] = AttenuationType.NONE;
            enum$VALUES[" ".length()] = AttenuationType.LINEAR;
            ENUM$VALUES = enum$VALUES;
        }
        
        public int getTypeInt() {
            return this.type;
        }
    }
}
