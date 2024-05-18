package net.minecraft.client.resources;

import net.minecraft.util.*;
import java.util.*;

public class DefaultPlayerSkin
{
    private static final ResourceLocation TEXTURE_ALEX;
    private static final String[] I;
    private static final ResourceLocation TEXTURE_STEVE;
    
    public static ResourceLocation getDefaultSkin(final UUID uuid) {
        ResourceLocation resourceLocation;
        if (isSlimSkin(uuid)) {
            resourceLocation = DefaultPlayerSkin.TEXTURE_ALEX;
            "".length();
            if (3 <= 2) {
                throw null;
            }
        }
        else {
            resourceLocation = DefaultPlayerSkin.TEXTURE_STEVE;
        }
        return resourceLocation;
    }
    
    public static String getSkinType(final UUID uuid) {
        String s;
        if (isSlimSkin(uuid)) {
            s = DefaultPlayerSkin.I["  ".length()];
            "".length();
            if (1 <= 0) {
                throw null;
            }
        }
        else {
            s = DefaultPlayerSkin.I["   ".length()];
        }
        return s;
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
            if (2 == 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private static boolean isSlimSkin(final UUID uuid) {
        if ((uuid.hashCode() & " ".length()) == " ".length()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public static ResourceLocation getDefaultSkinLegacy() {
        return DefaultPlayerSkin.TEXTURE_STEVE;
    }
    
    static {
        I();
        TEXTURE_STEVE = new ResourceLocation(DefaultPlayerSkin.I["".length()]);
        TEXTURE_ALEX = new ResourceLocation(DefaultPlayerSkin.I[" ".length()]);
    }
    
    private static void I() {
        (I = new String[0xBC ^ 0xB8])["".length()] = I("\u0011\u0013*\u001c6\u0017\u0013!G&\u000b\u0002;\u001c:J\u0005&\r5\u0000X\"\u0006$", "evRhC");
        DefaultPlayerSkin.I[" ".length()] = I("#\u001f\u000e\u0000!%\u001f\u0005[19\u000e\u001f\u0000-x\u001b\u001a\u0011,y\n\u0018\u0013", "WzvtT");
        DefaultPlayerSkin.I["  ".length()] = I("><\u0007\u001d", "MPnpJ");
        DefaultPlayerSkin.I["   ".length()] = I("36\u000f%\u0007;'", "WSiDr");
    }
}
