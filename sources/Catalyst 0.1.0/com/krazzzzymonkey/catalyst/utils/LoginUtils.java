// 
// Decompiled by Procyon v0.5.36
// 

package com.krazzzzymonkey.catalyst.utils;

import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.lang.reflect.Field;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
import net.minecraft.util.Session;
import com.krazzzzymonkey.catalyst.utils.system.Wrapper;
import com.krazzzzymonkey.catalyst.utils.system.Mapping;
import net.minecraft.client.Minecraft;
import com.mojang.authlib.Agent;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import java.net.Proxy;

public class LoginUtils
{
    private static final /* synthetic */ String[] lllIIIlI;
    private static final /* synthetic */ int[] lllIIlIl;
    
    private static boolean lIlIlIllII(final int lIllIIlIIIllIII, final int lIllIIlIIIlIlll) {
        return lIllIIlIIIllIII < lIllIIlIIIlIlll;
    }
    
    static {
        lIlIlIlIIl();
        lIlIlIIIlI();
    }
    
    public static String loginAlt(final String lIllIIlIllIlIII, final String lIllIIlIllIIlll) {
        final YggdrasilAuthenticationService lIllIIlIllIlIll = new YggdrasilAuthenticationService(Proxy.NO_PROXY, LoginUtils.lllIIIlI[LoginUtils.lllIIlIl[0]]);
        final YggdrasilUserAuthentication lIllIIlIllIlIlI = (YggdrasilUserAuthentication)lIllIIlIllIlIll.createUserAuthentication(Agent.MINECRAFT);
        lIllIIlIllIlIlI.setUsername(lIllIIlIllIlIII);
        lIllIIlIllIlIlI.setPassword(lIllIIlIllIIlll);
        String lIllIIlIllIlIIl = null;
        try {
            lIllIIlIllIlIlI.logIn();
            try {
                final Field lIllIIlIlllIIlI = Minecraft.class.getDeclaredField(Mapping.session);
                lIllIIlIlllIIlI.setAccessible((boolean)(LoginUtils.lllIIlIl[1] != 0));
                lIllIIlIlllIIlI.set(Wrapper.INSTANCE.mc(), new Session(lIllIIlIllIlIlI.getSelectedProfile().getName(), lIllIIlIllIlIlI.getSelectedProfile().getId().toString(), lIllIIlIllIlIlI.getAuthenticatedToken(), LoginUtils.lllIIIlI[LoginUtils.lllIIlIl[1]]));
                lIllIIlIllIlIIl = String.valueOf(new StringBuilder().append(LoginUtils.lllIIIlI[LoginUtils.lllIIlIl[2]]).append(Wrapper.INSTANCE.mc().getSession().getUsername()));
                "".length();
                if (-"  ".length() > 0) {
                    return null;
                }
            }
            catch (Exception lIllIIlIlllIIIl) {
                lIllIIlIllIlIIl = LoginUtils.lllIIIlI[LoginUtils.lllIIlIl[3]];
                lIllIIlIlllIIIl.printStackTrace();
            }
            "".length();
            if (((0x3 ^ 0x36) & ~(0x62 ^ 0x57)) != 0x0) {
                return null;
            }
        }
        catch (AuthenticationUnavailableException lIllIIlIlllIIII) {
            lIllIIlIllIlIIl = LoginUtils.lllIIIlI[LoginUtils.lllIIlIl[4]];
            "".length();
            if (((131 + 153 - 257 + 182 ^ 38 + 116 - 94 + 76) & (185 + 82 - 231 + 160 ^ 51 + 46 - 72 + 132 ^ -" ".length())) != 0x0) {
                return null;
            }
        }
        catch (AuthenticationException lIllIIlIllIllll) {
            if (!lIlIlIlIlI(lIllIIlIllIllll.getMessage().contains(LoginUtils.lllIIIlI[LoginUtils.lllIIlIl[5]]) ? 1 : 0) || lIlIlIlIll(lIllIIlIllIllll.getMessage().toLowerCase().contains(LoginUtils.lllIIIlI[LoginUtils.lllIIlIl[6]]) ? 1 : 0)) {
                lIllIIlIllIlIIl = LoginUtils.lllIIIlI[LoginUtils.lllIIlIl[7]];
                "".length();
                if ("   ".length() != "   ".length()) {
                    return null;
                }
            }
            else {
                lIllIIlIllIlIIl = LoginUtils.lllIIIlI[LoginUtils.lllIIlIl[8]];
            }
            "".length();
            if (-" ".length() >= 0) {
                return null;
            }
        }
        catch (NullPointerException lIllIIlIllIlllI) {
            lIllIIlIllIlIIl = LoginUtils.lllIIIlI[LoginUtils.lllIIlIl[9]];
        }
        return lIllIIlIllIlIIl;
    }
    
    private static String lIlIIlllIl(String lIllIIlIIllllIl, final String lIllIIlIIllllII) {
        lIllIIlIIllllIl = (byte)new String(Base64.getDecoder().decode(((String)lIllIIlIIllllIl).getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        final StringBuilder lIllIIlIlIIIIII = new StringBuilder();
        final char[] lIllIIlIIllllll = lIllIIlIIllllII.toCharArray();
        int lIllIIlIIlllllI = LoginUtils.lllIIlIl[0];
        final double lIllIIlIIlllIII = (Object)((String)lIllIIlIIllllIl).toCharArray();
        final float lIllIIlIIllIlll = lIllIIlIIlllIII.length;
        short lIllIIlIIllIllI = (short)LoginUtils.lllIIlIl[0];
        while (lIlIlIllII(lIllIIlIIllIllI, (int)lIllIIlIIllIlll)) {
            final char lIllIIlIlIIIIll = lIllIIlIIlllIII[lIllIIlIIllIllI];
            lIllIIlIlIIIIII.append((char)(lIllIIlIlIIIIll ^ lIllIIlIIllllll[lIllIIlIIlllllI % lIllIIlIIllllll.length]));
            "".length();
            ++lIllIIlIIlllllI;
            ++lIllIIlIIllIllI;
            "".length();
            if (((0x66 ^ 0x7 ^ (0x2E ^ 0x71)) & (0xAD ^ 0xB3 ^ (0x9A ^ 0xBA) ^ -" ".length())) < 0) {
                return null;
            }
        }
        return String.valueOf(lIllIIlIlIIIIII);
    }
    
    public static String getName(final String lIllIIlIlIlllII, final String lIllIIlIlIllIll) {
        final YggdrasilAuthenticationService lIllIIlIlIllIlI = new YggdrasilAuthenticationService(Proxy.NO_PROXY, LoginUtils.lllIIIlI[LoginUtils.lllIIlIl[10]]);
        final YggdrasilUserAuthentication lIllIIlIlIllIIl = (YggdrasilUserAuthentication)lIllIIlIlIllIlI.createUserAuthentication(Agent.MINECRAFT);
        lIllIIlIlIllIIl.setUsername(lIllIIlIlIlllII);
        lIllIIlIlIllIIl.setPassword(lIllIIlIlIllIll);
        try {
            lIllIIlIlIllIIl.logIn();
            return lIllIIlIlIllIIl.getSelectedProfile().getName();
        }
        catch (Exception lIllIIlIlIlllIl) {
            return null;
        }
    }
    
    private static boolean lIlIlIlIlI(final int lIllIIlIIIlIIll) {
        return lIllIIlIIIlIIll == 0;
    }
    
    private static String lIlIIlllII(final String lIllIIlIIlIIIII, final String lIllIIlIIIlllll) {
        try {
            final SecretKeySpec lIllIIlIIlIIIll = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(lIllIIlIIIlllll.getBytes(StandardCharsets.UTF_8)), LoginUtils.lllIIlIl[8]), "DES");
            final Cipher lIllIIlIIlIIIlI = Cipher.getInstance("DES");
            lIllIIlIIlIIIlI.init(LoginUtils.lllIIlIl[2], lIllIIlIIlIIIll);
            return new String(lIllIIlIIlIIIlI.doFinal(Base64.getDecoder().decode(lIllIIlIIlIIIII.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lIllIIlIIlIIIIl) {
            lIllIIlIIlIIIIl.printStackTrace();
            return null;
        }
    }
    
    private static void lIlIlIlIIl() {
        (lllIIlIl = new int[15])[0] = ((0x16 ^ 0x6 ^ (0x8D ^ 0x92)) & (22 + 49 - 4 + 64 ^ 5 + 16 + 62 + 57 ^ -" ".length()));
        LoginUtils.lllIIlIl[1] = " ".length();
        LoginUtils.lllIIlIl[2] = "  ".length();
        LoginUtils.lllIIlIl[3] = "   ".length();
        LoginUtils.lllIIlIl[4] = (0xBD ^ 0xB0 ^ (0x62 ^ 0x6B));
        LoginUtils.lllIIlIl[5] = (0xAF ^ 0x95 ^ (0xAF ^ 0x90));
        LoginUtils.lllIIlIl[6] = (0x2F ^ 0x29);
        LoginUtils.lllIIlIl[7] = (0x13 ^ 0x9 ^ (0x97 ^ 0x8A));
        LoginUtils.lllIIlIl[8] = (0x2F ^ 0x27);
        LoginUtils.lllIIlIl[9] = (0xED ^ 0xA4 ^ (0xFD ^ 0xBD));
        LoginUtils.lllIIlIl[10] = (0x58 ^ 0x52);
        LoginUtils.lllIIlIl[11] = (0xC ^ 0x54 ^ (0x45 ^ 0x16));
        LoginUtils.lllIIlIl[12] = (0x15 ^ 0x19);
        LoginUtils.lllIIlIl[13] = (0xB3 ^ 0xBE);
        LoginUtils.lllIIlIl[14] = (0x96 ^ 0x98);
    }
    
    private static String lIlIIllIlI(final String lIllIIlIIlIllIl, final String lIllIIlIIlIlIlI) {
        try {
            final SecretKeySpec lIllIIlIIllIIII = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(lIllIIlIIlIlIlI.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            final Cipher lIllIIlIIlIllll = Cipher.getInstance("Blowfish");
            lIllIIlIIlIllll.init(LoginUtils.lllIIlIl[2], lIllIIlIIllIIII);
            return new String(lIllIIlIIlIllll.doFinal(Base64.getDecoder().decode(lIllIIlIIlIllIl.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lIllIIlIIlIlllI) {
            lIllIIlIIlIlllI.printStackTrace();
            return null;
        }
    }
    
    private static void lIlIlIIIlI() {
        (lllIIIlI = new String[LoginUtils.lllIIlIl[14]])[LoginUtils.lllIIlIl[0]] = lIlIIllIlI("eTrhGGJFhJ4=", "siBhd");
        LoginUtils.lllIIIlI[LoginUtils.lllIIlIl[1]] = lIlIIllIlI("XsbTfcDgH4s=", "qPYCD");
        LoginUtils.lllIIIlI[LoginUtils.lllIIlIl[2]] = lIlIIllIlI("xQ1/N9EGblzbfOYPQS5ThtN+P0HeB1m2", "iFBaL");
        LoginUtils.lllIIIlI[LoginUtils.lllIIlIl[3]] = lIlIIlllII("TyFZgTbguBzDqRYI3F3FDg==", "ZAlfC");
        LoginUtils.lllIIIlI[LoginUtils.lllIIlIl[4]] = lIlIIlllIl("EigmHzclaSseNiUoKwV4MDw8GT0/PSESOSUgJx94Iiw6Bz0jaA==", "QIHqX");
        LoginUtils.lllIIIlI[LoginUtils.lllIIlIl[5]] = lIlIIlllIl("AyUYLTYjL045KS85AC03L2sBPno6Kh0/LSU5CmI=", "JKnLZ");
        LoginUtils.lllIIIlI[LoginUtils.lllIIlIl[6]] = lIlIIllIlI("HCrBVP1FIpke6QG70la9iu3cEY1vDcSL", "fIXgh");
        LoginUtils.lllIIIlI[LoginUtils.lllIIlIl[7]] = lIlIIllIlI("L+KQGyFlxjnZDO2+dqq2pg==", "bpVwf");
        LoginUtils.lllIIIlI[LoginUtils.lllIIlIl[8]] = lIlIIllIlI("ycIu4NHPiIo0G0e5SkCw4Q6z5pkjkrxcwbTmjw7lkwMOURHzDp2ALw==", "tNWil");
        LoginUtils.lllIIIlI[LoginUtils.lllIIlIl[9]] = lIlIIlllIl("DSsYKg16KRY3GS02BSBL", "ZYwDj");
        LoginUtils.lllIIIlI[LoginUtils.lllIIlIl[10]] = lIlIIllIlI("mUpyQQlM0q8=", "AVVZl");
        LoginUtils.lllIIIlI[LoginUtils.lllIIlIl[11]] = lIlIIlllIl("", "xoepz");
        LoginUtils.lllIIIlI[LoginUtils.lllIIlIl[12]] = lIlIIllIlI("padTifF/FNw=", "tvaHB");
        LoginUtils.lllIIIlI[LoginUtils.lllIIlIl[13]] = lIlIIlllII("vSU19XSJb1E=", "ezQrB");
    }
    
    public static void changeCrackedName(final String lIllIIlIlIIllll) {
        try {
            final Field lIllIIlIlIlIIIl = Minecraft.class.getDeclaredField(Mapping.session);
            lIllIIlIlIlIIIl.setAccessible((boolean)(LoginUtils.lllIIlIl[1] != 0));
            lIllIIlIlIlIIIl.set(Wrapper.INSTANCE.mc(), new Session(lIllIIlIlIIllll, LoginUtils.lllIIIlI[LoginUtils.lllIIlIl[11]], LoginUtils.lllIIIlI[LoginUtils.lllIIlIl[12]], LoginUtils.lllIIIlI[LoginUtils.lllIIlIl[13]]));
            "".length();
            if (null != null) {
                return;
            }
        }
        catch (Exception lIllIIlIlIlIIII) {
            lIllIIlIlIlIIII.printStackTrace();
        }
    }
    
    private static boolean lIlIlIlIll(final int lIllIIlIIIlIlIl) {
        return lIllIIlIIIlIlIl != 0;
    }
}
