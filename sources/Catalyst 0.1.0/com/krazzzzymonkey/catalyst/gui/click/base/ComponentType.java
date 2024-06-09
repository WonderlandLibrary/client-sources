// 
// Decompiled by Procyon v0.5.36
// 

package com.krazzzzymonkey.catalyst.gui.click.base;

import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public enum ComponentType
{
    FRAME, 
    PANEL, 
    KEYBIND, 
    DROPDOWN, 
    CHECK_BUTTON, 
    SLIDER, 
    EXPANDING_BUTTON, 
    BUTTON, 
    TEXT, 
    COMBO_BOX;
    
    private static final /* synthetic */ String[] lIllIlI;
    private static final /* synthetic */ int[] llIIIIl;
    
    private static void lIIlIlllI() {
        (llIIIIl = new int[11])[0] = ((0x10 ^ 0x3D) & ~(0x8E ^ 0xA3));
        ComponentType.llIIIIl[1] = " ".length();
        ComponentType.llIIIIl[2] = "  ".length();
        ComponentType.llIIIIl[3] = "   ".length();
        ComponentType.llIIIIl[4] = (0x48 ^ 0x4C);
        ComponentType.llIIIIl[5] = (0x99 ^ 0x9C);
        ComponentType.llIIIIl[6] = (185 + 61 - 213 + 156 ^ 95 + 149 - 142 + 85);
        ComponentType.llIIIIl[7] = (0x24 ^ 0x23);
        ComponentType.llIIIIl[8] = (0x86 ^ 0x8E);
        ComponentType.llIIIIl[9] = (0xA3 ^ 0x80 ^ (0x7E ^ 0x54));
        ComponentType.llIIIIl[10] = (0x6 ^ 0xC);
    }
    
    private static String lIIlIIIlI(String llIllIIIlllIIIl, final String llIllIIIlllIIII) {
        llIllIIIlllIIIl = new String(Base64.getDecoder().decode(llIllIIIlllIIIl.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        final StringBuilder llIllIIIlllIlII = new StringBuilder();
        final char[] llIllIIIlllIIll = llIllIIIlllIIII.toCharArray();
        int llIllIIIlllIIlI = ComponentType.llIIIIl[0];
        final char llIllIIIllIllII = (Object)llIllIIIlllIIIl.toCharArray();
        final Exception llIllIIIllIlIll = (Exception)llIllIIIllIllII.length;
        int llIllIIIllIlIlI = ComponentType.llIIIIl[0];
        while (lIIlIllll(llIllIIIllIlIlI, (int)llIllIIIllIlIll)) {
            final char llIllIIIlllIlll = llIllIIIllIllII[llIllIIIllIlIlI];
            llIllIIIlllIlII.append((char)(llIllIIIlllIlll ^ llIllIIIlllIIll[llIllIIIlllIIlI % llIllIIIlllIIll.length]));
            "".length();
            ++llIllIIIlllIIlI;
            ++llIllIIIllIlIlI;
            "".length();
            if ("  ".length() <= " ".length()) {
                return null;
            }
        }
        return String.valueOf(llIllIIIlllIlII);
    }
    
    private static String lIIlIlIII(final String llIllIIlIIIIllI, final String llIllIIlIIIIlIl) {
        try {
            final SecretKeySpec llIllIIlIIIlIIl = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(llIllIIlIIIIlIl.getBytes(StandardCharsets.UTF_8)), ComponentType.llIIIIl[8]), "DES");
            final Cipher llIllIIlIIIlIII = Cipher.getInstance("DES");
            llIllIIlIIIlIII.init(ComponentType.llIIIIl[2], llIllIIlIIIlIIl);
            return new String(llIllIIlIIIlIII.doFinal(Base64.getDecoder().decode(llIllIIlIIIIllI.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception llIllIIlIIIIlll) {
            llIllIIlIIIIlll.printStackTrace();
            return null;
        }
    }
    
    private static void lIIlIlIIl() {
        (lIllIlI = new String[ComponentType.llIIIIl[10]])[ComponentType.llIIIIl[0]] = lIIlIIIlI("ExYtICQ=", "UDlma");
        ComponentType.lIllIlI[ComponentType.llIIIIl[1]] = lIIlIIIll("wX6//m6CrxQ=", "HdQjo");
        ComponentType.lIllIlI[ComponentType.llIIIIl[2]] = lIIlIIIll("h0fVvLM7DjE=", "kZWhA");
        ComponentType.lIllIlI[ComponentType.llIIIIl[3]] = lIIlIIIlI("MwkeGwcyGAAdFjQEGg4GOA==", "vQNZI");
        ComponentType.lIllIlI[ComponentType.llIIIIl[4]] = lIIlIIIlI("IAMEJi88CRQxMCwF", "cKAed");
        ComponentType.lIllIlI[ComponentType.llIIIIl[5]] = lIIlIIIlI("PQomPAc8", "nFoxB");
        ComponentType.lIllIlI[ComponentType.llIIIIl[6]] = lIIlIIIlI("LDcVDyApNg==", "grLMi");
        ComponentType.lIllIlI[ComponentType.llIIIIl[7]] = lIIlIIIll("fdMHjA9wL7o=", "EVOmh");
        ComponentType.lIllIlI[ComponentType.llIIIIl[8]] = lIIlIIIll("/AutPVktEh0GOZAqiGRpHA==", "QdnHs");
        ComponentType.lIllIlI[ComponentType.llIIIIl[9]] = lIIlIlIII("H7g0MNIJEBke3Yc2h6zVFA==", "axqTr");
    }
    
    private static boolean lIIlIllll(final int llIllIIIllIIllI, final int llIllIIIllIIlIl) {
        return llIllIIIllIIllI < llIllIIIllIIlIl;
    }
    
    private static String lIIlIIIll(final String llIllIIlIIlIIIl, final String llIllIIlIIlIIII) {
        try {
            final SecretKeySpec llIllIIlIIlIllI = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(llIllIIlIIlIIII.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            final Cipher llIllIIlIIlIlIl = Cipher.getInstance("Blowfish");
            llIllIIlIIlIlIl.init(ComponentType.llIIIIl[2], llIllIIlIIlIllI);
            return new String(llIllIIlIIlIlIl.doFinal(Base64.getDecoder().decode(llIllIIlIIlIIIl.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception llIllIIlIIlIlII) {
            llIllIIlIIlIlII.printStackTrace();
            return null;
        }
    }
    
    static {
        lIIlIlllI();
        lIIlIlIIl();
        final ComponentType[] $values = new ComponentType[ComponentType.llIIIIl[10]];
        $values[ComponentType.llIIIIl[0]] = ComponentType.FRAME;
        $values[ComponentType.llIIIIl[1]] = ComponentType.PANEL;
        $values[ComponentType.llIIIIl[2]] = ComponentType.BUTTON;
        $values[ComponentType.llIIIIl[3]] = ComponentType.EXPANDING_BUTTON;
        $values[ComponentType.llIIIIl[4]] = ComponentType.CHECK_BUTTON;
        $values[ComponentType.llIIIIl[5]] = ComponentType.SLIDER;
        $values[ComponentType.llIIIIl[6]] = ComponentType.KEYBIND;
        $values[ComponentType.llIIIIl[7]] = ComponentType.TEXT;
        $values[ComponentType.llIIIIl[8]] = ComponentType.DROPDOWN;
        $values[ComponentType.llIIIIl[9]] = ComponentType.COMBO_BOX;
        $VALUES = $values;
    }
}
