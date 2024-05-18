// 
// Decompiled by Procyon v0.5.36
// 

package com.krazzzzymonkey.catalyst.utils.system;

import java.util.Arrays;
import java.util.Base64;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import net.minecraft.client.Minecraft;

public class Mapping
{
    private static final /* synthetic */ int[] lIllIll;
    public static /* synthetic */ String yaw;
    private static final /* synthetic */ String[] lIlIlll;
    public static /* synthetic */ String session;
    public static /* synthetic */ String currentGameType;
    public static /* synthetic */ String isHittingBlock;
    public static /* synthetic */ String curBlockDamageMP;
    public static /* synthetic */ String pitch;
    public static /* synthetic */ String connection;
    public static /* synthetic */ String isInWeb;
    public static /* synthetic */ String onUpdateWalkingPlayer;
    public static /* synthetic */ String blockHitDelay;
    
    public static boolean isNotObfuscated() {
        try {
            int n;
            if (lIIlIIlIl(Minecraft.class.getDeclaredField(Mapping.lIlIlll[Mapping.lIllIll[0]]))) {
                n = Mapping.lIllIll[1];
                "".length();
                if ("  ".length() < "  ".length()) {
                    return ((0xFF ^ 0x81 ^ (0x58 ^ 0x2A)) & (78 + 111 - 173 + 112 ^ 121 + 132 - 222 + 109 ^ -" ".length())) != 0x0;
                }
            }
            else {
                n = Mapping.lIllIll[0];
            }
            return n != 0;
        }
        catch (Exception llIllIlIllllIII) {
            return Mapping.lIllIll[0] != 0;
        }
    }
    
    private static void lIIlIIlII() {
        (lIllIll = new int[22])[0] = ((0x0 ^ 0x12) & ~(0x66 ^ 0x74));
        Mapping.lIllIll[1] = " ".length();
        Mapping.lIllIll[2] = "  ".length();
        Mapping.lIllIll[3] = "   ".length();
        Mapping.lIllIll[4] = (0xBB ^ 0x96 ^ (0xAB ^ 0x82));
        Mapping.lIllIll[5] = (0x60 ^ 0x4F ^ (0xB9 ^ 0x93));
        Mapping.lIllIll[6] = (0xC ^ 0x72 ^ (0x1A ^ 0x62));
        Mapping.lIllIll[7] = (0xA6 ^ 0xA1);
        Mapping.lIllIll[8] = (37 + 106 - 113 + 99 ^ 49 + 49 - 55 + 94);
        Mapping.lIllIll[9] = (0x65 ^ 0x6C);
        Mapping.lIllIll[10] = (0x13 ^ 0x19);
        Mapping.lIllIll[11] = (0x2 ^ 0x9);
        Mapping.lIllIll[12] = (0x7D ^ 0x71);
        Mapping.lIllIll[13] = (0x1F ^ 0x12);
        Mapping.lIllIll[14] = (0x1A ^ 0x14);
        Mapping.lIllIll[15] = (0xCE ^ 0xC1);
        Mapping.lIllIll[16] = (0xA3 ^ 0xB3);
        Mapping.lIllIll[17] = (0x71 ^ 0x60);
        Mapping.lIllIll[18] = (0x7D ^ 0x6F ^ ((0xC9 ^ 0xC2) & ~(0x25 ^ 0x2E)));
        Mapping.lIllIll[19] = (0x86 ^ 0xBA ^ (0x80 ^ 0xAF));
        Mapping.lIllIll[20] = (0x9F ^ 0x8B);
        Mapping.lIllIll[21] = (0x39 ^ 0x34 ^ (0x42 ^ 0x5A));
    }
    
    private static void lIIlIIIIl() {
        (lIlIlll = new String[Mapping.lIllIll[21]])[Mapping.lIllIll[0]] = lIIIlIlIl("Liw5BxEpIS8=", "GBJsp");
        Mapping.lIlIlll[Mapping.lIllIll[1]] = lIIIlIlIl("FBUrAwcIHg==", "gpXpn");
        Mapping.lIlIlll[Mapping.lIllIll[2]] = lIIIlIlll("ufzgXticWmT9ZT5fufk3kQ==", "Qvjwe");
        Mapping.lIlIlll[Mapping.lIllIll[3]] = lIIIlIlll("z7Dwsq0yjsw=", "fKCnJ");
        Mapping.lIlIlll[Mapping.lIllIll[4]] = lIIIlIlIl("DAwUNCg1VEVheF1TLj0=", "jeqXL");
        Mapping.lIlIlll[Mapping.lIllIll[5]] = lIIIlIlIl("MgYiFA4=", "BoVwf");
        Mapping.lIlIlll[Mapping.lIllIll[6]] = lIIIlIlll("XCQ/u9QU8qFGo/tFfsicIg==", "TAsyy");
        Mapping.lIlIlll[Mapping.lIllIll[7]] = lIIIlIlll("tNLS6CaKFiMfycRKya33Uw==", "jlwHz");
        Mapping.lIlIlll[Mapping.lIllIll[8]] = lIIIllIIl("MGOR7+Tf+/Sd+KpiFQLsUA==", "KVTdJ");
        Mapping.lIlIlll[Mapping.lIllIll[9]] = lIIIlIlll("FkTp+UeNtmxcQZosG3zwtg==", "wgibk");
        Mapping.lIlIlll[Mapping.lIllIll[10]] = lIIIlIlIl("KTgiPTwQZn9mb3sOJQ==", "OQGQX");
        Mapping.lIlIlll[Mapping.lIllIll[11]] = lIIIlIlll("dKl28moFIoMfeMvyXO4dkg==", "kLadn");
        Mapping.lIlIlll[Mapping.lIllIll[12]] = lIIIlIlIl("EDgWGCApZktDfEcOGg==", "vQstD");
        Mapping.lIlIlll[Mapping.lIllIll[13]] = lIIIlIlll("Ih8gZrH65+U=", "uiioY");
        Mapping.lIlIlll[Mapping.lIllIll[14]] = lIIIllIIl("15R7NFfACKkYPOYu/84W1A==", "qehPg");
        Mapping.lIlIlll[Mapping.lIllIll[15]] = lIIIllIIl("slu3c+ptYj1+paWKEsOlhPkX2j8867qu", "ZAFmY");
        Mapping.lIlIlll[Mapping.lIllIll[16]] = lIIIlIlIl("KioRICATdEx7c3wcEg==", "LCtLD");
        Mapping.lIlIlll[Mapping.lIllIll[17]] = lIIIlIlll("aEclfVwUf6xp1u+lkqOrbw==", "KgAgc");
        Mapping.lIlIlll[Mapping.lIllIll[18]] = lIIIlIlIl("AD8/OiU5YWJhdl4JMA==", "fVZVA");
        Mapping.lIlIlll[Mapping.lIllIll[19]] = lIIIlIlIl("LBQzPxEiDgMYFC8RDyESExYHNhAx", "CzfOu");
        Mapping.lIlIlll[Mapping.lIllIll[20]] = lIIIlIlll("cUU+g2zQx+nIHdIOVoMVqQ==", "llmcm");
    }
    
    private static boolean lIIlIIlll(final int llIllIlIlIIIIlI, final int llIllIlIlIIIIIl) {
        return llIllIlIlIIIIlI < llIllIlIlIIIIIl;
    }
    
    static {
        lIIlIIlII();
        lIIlIIIIl();
        String session;
        if (lIIlIIllI(isNotObfuscated() ? 1 : 0)) {
            session = Mapping.lIlIlll[Mapping.lIllIll[1]];
            "".length();
            if ((0x1B ^ 0x1F) <= 0) {
                return;
            }
        }
        else {
            session = Mapping.lIlIlll[Mapping.lIllIll[2]];
        }
        Mapping.session = session;
        String yaw;
        if (lIIlIIllI(isNotObfuscated() ? 1 : 0)) {
            yaw = Mapping.lIlIlll[Mapping.lIllIll[3]];
            "".length();
            if ("   ".length() <= -" ".length()) {
                return;
            }
        }
        else {
            yaw = Mapping.lIlIlll[Mapping.lIllIll[4]];
        }
        Mapping.yaw = yaw;
        String pitch;
        if (lIIlIIllI(isNotObfuscated() ? 1 : 0)) {
            pitch = Mapping.lIlIlll[Mapping.lIllIll[5]];
            "".length();
            if (null != null) {
                return;
            }
        }
        else {
            pitch = Mapping.lIlIlll[Mapping.lIllIll[6]];
        }
        Mapping.pitch = pitch;
        String currentGameType;
        if (lIIlIIllI(isNotObfuscated() ? 1 : 0)) {
            currentGameType = Mapping.lIlIlll[Mapping.lIllIll[7]];
            "".length();
            if (((0xF5 ^ 0xC2 ^ (0xAF ^ 0x9F)) & (0x15 ^ 0x77 ^ (0x44 ^ 0x21) ^ -" ".length())) > 0) {
                return;
            }
        }
        else {
            currentGameType = Mapping.lIlIlll[Mapping.lIllIll[8]];
        }
        Mapping.currentGameType = currentGameType;
        String connection;
        if (lIIlIIllI(isNotObfuscated() ? 1 : 0)) {
            connection = Mapping.lIlIlll[Mapping.lIllIll[9]];
            "".length();
            if ((72 + 77 - 18 + 16 ^ 119 + 108 - 139 + 63) <= ((0x9B ^ 0x93 ^ (0xEC ^ 0xB1)) & (0x79 ^ 0xD ^ (0xAF ^ 0x8E) ^ -" ".length()))) {
                return;
            }
        }
        else {
            connection = Mapping.lIlIlll[Mapping.lIllIll[10]];
        }
        Mapping.connection = connection;
        String blockHitDelay;
        if (lIIlIIllI(isNotObfuscated() ? 1 : 0)) {
            blockHitDelay = Mapping.lIlIlll[Mapping.lIllIll[11]];
            "".length();
            if (-" ".length() >= 0) {
                return;
            }
        }
        else {
            blockHitDelay = Mapping.lIlIlll[Mapping.lIllIll[12]];
        }
        Mapping.blockHitDelay = blockHitDelay;
        String isInWeb;
        if (lIIlIIllI(isNotObfuscated() ? 1 : 0)) {
            isInWeb = Mapping.lIlIlll[Mapping.lIllIll[13]];
            "".length();
            if ((0x38 ^ 0x3C) <= 0) {
                return;
            }
        }
        else {
            isInWeb = Mapping.lIlIlll[Mapping.lIllIll[14]];
        }
        Mapping.isInWeb = isInWeb;
        String curBlockDamageMP;
        if (lIIlIIllI(isNotObfuscated() ? 1 : 0)) {
            curBlockDamageMP = Mapping.lIlIlll[Mapping.lIllIll[15]];
            "".length();
            if ((0x7 ^ 0x3) != (0x7A ^ 0x7E)) {
                return;
            }
        }
        else {
            curBlockDamageMP = Mapping.lIlIlll[Mapping.lIllIll[16]];
        }
        Mapping.curBlockDamageMP = curBlockDamageMP;
        String isHittingBlock;
        if (lIIlIIllI(isNotObfuscated() ? 1 : 0)) {
            isHittingBlock = Mapping.lIlIlll[Mapping.lIllIll[17]];
            "".length();
            if ("   ".length() <= ((0x75 ^ 0x2F ^ (0xDE ^ 0xA5)) & (0x78 ^ 0x12 ^ (0xE4 ^ 0xAF) ^ -" ".length()))) {
                return;
            }
        }
        else {
            isHittingBlock = Mapping.lIlIlll[Mapping.lIllIll[18]];
        }
        Mapping.isHittingBlock = isHittingBlock;
        String onUpdateWalkingPlayer;
        if (lIIlIIllI(isNotObfuscated() ? 1 : 0)) {
            onUpdateWalkingPlayer = Mapping.lIlIlll[Mapping.lIllIll[19]];
            "".length();
            if ("  ".length() >= "   ".length()) {
                return;
            }
        }
        else {
            onUpdateWalkingPlayer = Mapping.lIlIlll[Mapping.lIllIll[20]];
        }
        Mapping.onUpdateWalkingPlayer = onUpdateWalkingPlayer;
    }
    
    private static String lIIIllIIl(final String llIllIlIllIIIII, final String llIllIlIlIlllll) {
        try {
            final SecretKeySpec llIllIlIllIIlIl = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(llIllIlIlIlllll.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            final Cipher llIllIlIllIIlII = Cipher.getInstance("Blowfish");
            llIllIlIllIIlII.init(Mapping.lIllIll[2], llIllIlIllIIlIl);
            return new String(llIllIlIllIIlII.doFinal(Base64.getDecoder().decode(llIllIlIllIIIII.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception llIllIlIllIIIll) {
            llIllIlIllIIIll.printStackTrace();
            return null;
        }
    }
    
    private static boolean lIIlIIlIl(final Object llIllIlIIllllll) {
        return llIllIlIIllllll != null;
    }
    
    private static boolean lIIlIIllI(final int llIllIlIIllllIl) {
        return llIllIlIIllllIl != 0;
    }
    
    private static String lIIIlIlIl(String llIllIlIlIIllIl, final String llIllIlIlIIllII) {
        llIllIlIlIIllIl = new String(Base64.getDecoder().decode(llIllIlIlIIllIl.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        final StringBuilder llIllIlIlIlIIII = new StringBuilder();
        final char[] llIllIlIlIIllll = llIllIlIlIIllII.toCharArray();
        int llIllIlIlIIlllI = Mapping.lIllIll[0];
        final char llIllIlIlIIlIII = (Object)llIllIlIlIIllIl.toCharArray();
        final double llIllIlIlIIIlll = llIllIlIlIIlIII.length;
        int llIllIlIlIIIllI = Mapping.lIllIll[0];
        while (lIIlIIlll(llIllIlIlIIIllI, (int)llIllIlIlIIIlll)) {
            final char llIllIlIlIlIIll = llIllIlIlIIlIII[llIllIlIlIIIllI];
            llIllIlIlIlIIII.append((char)(llIllIlIlIlIIll ^ llIllIlIlIIllll[llIllIlIlIIlllI % llIllIlIlIIllll.length]));
            "".length();
            ++llIllIlIlIIlllI;
            ++llIllIlIlIIIllI;
            "".length();
            if ((0xE4 ^ 0x96 ^ (0xF8 ^ 0x8E)) < 0) {
                return null;
            }
        }
        return String.valueOf(llIllIlIlIlIIII);
    }
    
    private static String lIIIlIlll(final String llIllIlIllIllll, final String llIllIlIllIllII) {
        try {
            final SecretKeySpec llIllIlIlllIIlI = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(llIllIlIllIllII.getBytes(StandardCharsets.UTF_8)), Mapping.lIllIll[8]), "DES");
            final Cipher llIllIlIlllIIIl = Cipher.getInstance("DES");
            llIllIlIlllIIIl.init(Mapping.lIllIll[2], llIllIlIlllIIlI);
            return new String(llIllIlIlllIIIl.doFinal(Base64.getDecoder().decode(llIllIlIllIllll.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception llIllIlIlllIIII) {
            llIllIlIlllIIII.printStackTrace();
            return null;
        }
    }
}
