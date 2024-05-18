// 
// Decompiled by Procyon v0.5.36
// 

package com.krazzzzymonkey.catalyst.module;

import java.util.Arrays;
import java.util.Base64;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public enum ModuleCategory
{
    PLAYER, 
    COMBAT, 
    EXPLOIT, 
    MISC, 
    MOVEMENT, 
    OTHER;
    
    private static final /* synthetic */ String[] lIllllIl;
    
    CHAT, 
    GUI, 
    RENDER;
    
    private static final /* synthetic */ int[] llIIIIIl;
    
    private static String lIIIllIIIl(final String lIlllllllIlIlIl, final String lIlllllllIlIllI) {
        try {
            final SecretKeySpec lIlllllllIllIlI = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(lIlllllllIlIllI.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            final Cipher lIlllllllIllIIl = Cipher.getInstance("Blowfish");
            lIlllllllIllIIl.init(ModuleCategory.llIIIIIl[2], lIlllllllIllIlI);
            return new String(lIlllllllIllIIl.doFinal(Base64.getDecoder().decode(lIlllllllIlIlIl.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lIlllllllIllIII) {
            lIlllllllIllIII.printStackTrace();
            return null;
        }
    }
    
    private static boolean lIIlIIIllI(final int lIllllllIIIIlII, final int lIllllllIIIIIll) {
        return lIllllllIIIIlII < lIllllllIIIIIll;
    }
    
    private static void lIIIlllIIl() {
        (lIllllIl = new String[ModuleCategory.llIIIIIl[9]])[ModuleCategory.llIIIIIl[0]] = lIIIllIIIl("kirnr3woY7o=", "BGVBb");
        ModuleCategory.lIllllIl[ModuleCategory.llIIIIIl[1]] = lIIIllIIIl("6CW1MqF+CLM=", "LwxBm");
        ModuleCategory.lIllllIl[ModuleCategory.llIIIIIl[2]] = lIIIllIllI("2gbEewMqbUc=", "KlWxt");
        ModuleCategory.lIllllIl[ModuleCategory.llIIIIIl[3]] = lIIIllIIIl("WCWDfPC3dwg=", "wWzLA");
        ModuleCategory.lIllllIl[ModuleCategory.llIIIIIl[4]] = lIIIllIlll("FTMYFA==", "XzKWO");
        ModuleCategory.lIllllIl[ModuleCategory.llIIIIIl[5]] = lIIIllIllI("MSEcrpC6ftcigJBNtRt0Mw==", "fJhXe");
        ModuleCategory.lIllllIl[ModuleCategory.llIIIIIl[6]] = lIIIllIlll("EwYAHTwR", "CJADy");
        ModuleCategory.lIllllIl[ModuleCategory.llIIIIIl[7]] = lIIIllIIIl("ZMuDjSzTNPw=", "Jyyfr");
        ModuleCategory.lIllllIl[ModuleCategory.llIIIIIl[8]] = lIIIllIlll("FxYtDB8=", "XBeIM");
    }
    
    private static String lIIIllIllI(final String lIllllllIllllll, final String lIllllllIlllllI) {
        try {
            final SecretKeySpec lIlllllllIIIIlI = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(lIllllllIlllllI.getBytes(StandardCharsets.UTF_8)), ModuleCategory.llIIIIIl[8]), "DES");
            final Cipher lIlllllllIIIIIl = Cipher.getInstance("DES");
            lIlllllllIIIIIl.init(ModuleCategory.llIIIIIl[2], lIlllllllIIIIlI);
            return new String(lIlllllllIIIIIl.doFinal(Base64.getDecoder().decode(lIllllllIllllll.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lIlllllllIIIIII) {
            lIlllllllIIIIII.printStackTrace();
            return null;
        }
    }
    
    private static String lIIIllIlll(String lIllllllIIlIlll, final String lIllllllIIllIll) {
        lIllllllIIlIlll = new String(Base64.getDecoder().decode(lIllllllIIlIlll.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        final StringBuilder lIllllllIIllIlI = new StringBuilder();
        final char[] lIllllllIIllIIl = lIllllllIIllIll.toCharArray();
        int lIllllllIIllIII = ModuleCategory.llIIIIIl[0];
        final float lIllllllIIlIIlI = (Object)lIllllllIIlIlll.toCharArray();
        final String lIllllllIIlIIIl = (String)lIllllllIIlIIlI.length;
        float lIllllllIIlIIII = ModuleCategory.llIIIIIl[0];
        while (lIIlIIIllI((int)lIllllllIIlIIII, (int)lIllllllIIlIIIl)) {
            final char lIllllllIIlllIl = lIllllllIIlIIlI[lIllllllIIlIIII];
            lIllllllIIllIlI.append((char)(lIllllllIIlllIl ^ lIllllllIIllIIl[lIllllllIIllIII % lIllllllIIllIIl.length]));
            "".length();
            ++lIllllllIIllIII;
            ++lIllllllIIlIIII;
            "".length();
            if ("   ".length() < ("   ".length() & ~"   ".length())) {
                return null;
            }
        }
        return String.valueOf(lIllllllIIllIlI);
    }
    
    static {
        lIIlIIIlIl();
        lIIIlllIIl();
        final ModuleCategory[] $values = new ModuleCategory[ModuleCategory.llIIIIIl[9]];
        $values[ModuleCategory.llIIIIIl[0]] = ModuleCategory.CHAT;
        $values[ModuleCategory.llIIIIIl[1]] = ModuleCategory.COMBAT;
        $values[ModuleCategory.llIIIIIl[2]] = ModuleCategory.EXPLOIT;
        $values[ModuleCategory.llIIIIIl[3]] = ModuleCategory.GUI;
        $values[ModuleCategory.llIIIIIl[4]] = ModuleCategory.MISC;
        $values[ModuleCategory.llIIIIIl[5]] = ModuleCategory.MOVEMENT;
        $values[ModuleCategory.llIIIIIl[6]] = ModuleCategory.PLAYER;
        $values[ModuleCategory.llIIIIIl[7]] = ModuleCategory.RENDER;
        $values[ModuleCategory.llIIIIIl[8]] = ModuleCategory.OTHER;
        $VALUES = $values;
    }
    
    private static void lIIlIIIlIl() {
        (llIIIIIl = new int[10])[0] = ((50 + 78 - 83 + 89 ^ 19 + 13 + 61 + 49) & (38 + 74 - 84 + 110 ^ 128 + 13 - 95 + 84 ^ -" ".length()));
        ModuleCategory.llIIIIIl[1] = " ".length();
        ModuleCategory.llIIIIIl[2] = "  ".length();
        ModuleCategory.llIIIIIl[3] = "   ".length();
        ModuleCategory.llIIIIIl[4] = (0xF1 ^ 0x8D ^ (0x2C ^ 0x54));
        ModuleCategory.llIIIIIl[5] = (0x3 ^ 0x6);
        ModuleCategory.llIIIIIl[6] = (65 + 42 + 76 + 4 ^ 16 + 41 - 30 + 162);
        ModuleCategory.llIIIIIl[7] = (0x45 ^ 0x42);
        ModuleCategory.llIIIIIl[8] = (0x30 ^ 0x38);
        ModuleCategory.llIIIIIl[9] = (0x6E ^ 0x67);
    }
}
