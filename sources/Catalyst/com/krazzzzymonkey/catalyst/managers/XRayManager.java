// 
// Decompiled by Procyon v0.5.36
// 

package com.krazzzzymonkey.catalyst.managers;

import java.util.Iterator;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import com.krazzzzymonkey.catalyst.utils.visual.ChatUtils;
import net.minecraft.block.Block;
import com.krazzzzymonkey.catalyst.xray.XRayData;
import java.util.LinkedList;

public class XRayManager
{
    private static final /* synthetic */ String[] llIIlII;
    private static final /* synthetic */ int[] llIlIII;
    public static /* synthetic */ LinkedList<XRayData> xrayList;
    
    static {
        lIlIIIIlI();
        lIIlllllI();
        XRayManager.xrayList = new LinkedList<XRayData>();
    }
    
    public static XRayData getDataByMeta(final int llIlIllllIlIlIl) {
        XRayData llIlIllllIlIllI = null;
        final boolean llIlIllllIlIIll = (boolean)XRayManager.xrayList.iterator();
        while (lIlIIIlII(((Iterator)llIlIllllIlIIll).hasNext() ? 1 : 0)) {
            final XRayData llIlIllllIllIII = ((Iterator<XRayData>)llIlIllllIlIIll).next();
            if (lIlIIIlIl(llIlIllllIllIII.getMeta(), llIlIllllIlIlIl)) {
                llIlIllllIlIllI = llIlIllllIllIII;
            }
            "".length();
            if ("   ".length() <= " ".length()) {
                return null;
            }
        }
        return llIlIllllIlIllI;
    }
    
    public static void add(final XRayData llIlIllllIIlIlI) {
        if (lIlIIIllI(Block.getBlockById(llIlIllllIIlIlI.getId()))) {
            ChatUtils.error(XRayManager.llIIlII[XRayManager.llIlIII[1]]);
            return;
        }
        final LinkedList<XRayData> llIlIllllIIlIIl = getDataById(llIlIllllIIlIlI.getId());
        if (lIlIIIlII(llIlIllllIIlIIl.isEmpty() ? 1 : 0)) {
            addData(llIlIllllIIlIlI);
            return;
        }
        boolean llIlIllllIIlIII = XRayManager.llIlIII[0] != 0;
        boolean llIlIllllIIIlll = XRayManager.llIlIII[0] != 0;
        final double llIlIllllIIIIlI = (double)llIlIllllIIlIIl.iterator();
        while (lIlIIIlII(((Iterator)llIlIllllIIIIlI).hasNext() ? 1 : 0)) {
            final XRayData llIlIllllIIlIll = ((Iterator<XRayData>)llIlIllllIIIIlI).next();
            if (lIlIIIlIl(llIlIllllIIlIll.getId(), llIlIllllIIlIlI.getId())) {
                llIlIllllIIlIII = (XRayManager.llIlIII[1] != 0);
            }
            if (lIlIIIlIl(llIlIllllIIlIll.getMeta(), llIlIllllIIlIlI.getMeta())) {
                llIlIllllIIIlll = (XRayManager.llIlIII[1] != 0);
            }
            "".length();
            if ("  ".length() <= ((0x1F ^ 0xD) & ~(0xB7 ^ 0xA5))) {
                return;
            }
        }
        if (lIlIIIlII(llIlIllllIIlIII ? 1 : 0) && lIlIIIlII(llIlIllllIIIlll ? 1 : 0)) {
            return;
        }
        addData(llIlIllllIIlIlI);
    }
    
    private static String lIIlllIIl(String llIlIlllIIllIIl, final String llIlIlllIIllIII) {
        llIlIlllIIllIIl = (int)new String(Base64.getDecoder().decode(((String)llIlIlllIIllIIl).getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        final StringBuilder llIlIlllIIlllII = new StringBuilder();
        final char[] llIlIlllIIllIll = llIlIlllIIllIII.toCharArray();
        int llIlIlllIIllIlI = XRayManager.llIlIII[0];
        final Exception llIlIlllIIlIlII = (Object)((String)llIlIlllIIllIIl).toCharArray();
        final float llIlIlllIIlIIll = llIlIlllIIlIlII.length;
        String llIlIlllIIlIIlI = (String)XRayManager.llIlIII[0];
        while (lIlIIIlll((int)llIlIlllIIlIIlI, (int)llIlIlllIIlIIll)) {
            final char llIlIlllIIlllll = llIlIlllIIlIlII[llIlIlllIIlIIlI];
            llIlIlllIIlllII.append((char)(llIlIlllIIlllll ^ llIlIlllIIllIll[llIlIlllIIllIlI % llIlIlllIIllIll.length]));
            "".length();
            ++llIlIlllIIllIlI;
            ++llIlIlllIIlIIlI;
            "".length();
            if (" ".length() <= 0) {
                return null;
            }
        }
        return String.valueOf(llIlIlllIIlllII);
    }
    
    private static String lIIlllIlI(final String llIlIlllIlIlllI, final String llIlIlllIlIlIll) {
        try {
            final SecretKeySpec llIlIlllIllIIIl = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(llIlIlllIlIlIll.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            final Cipher llIlIlllIllIIII = Cipher.getInstance("Blowfish");
            llIlIlllIllIIII.init(XRayManager.llIlIII[2], llIlIlllIllIIIl);
            return new String(llIlIlllIllIIII.doFinal(Base64.getDecoder().decode(llIlIlllIlIlllI.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception llIlIlllIlIllll) {
            llIlIlllIlIllll.printStackTrace();
            return null;
        }
    }
    
    public static LinkedList<XRayData> getDataById(final int llIlIlllllIIIlI) {
        final LinkedList<XRayData> llIlIlllllIIIIl = new LinkedList<XRayData>();
        final char llIlIllllIllllI = (char)XRayManager.xrayList.iterator();
        while (lIlIIIlII(((Iterator)llIlIllllIllllI).hasNext() ? 1 : 0)) {
            final XRayData llIlIlllllIIIll = ((Iterator<XRayData>)llIlIllllIllllI).next();
            if (lIlIIIlIl(llIlIlllllIIIll.getId(), llIlIlllllIIIlI)) {
                llIlIlllllIIIIl.add(llIlIlllllIIIll);
                "".length();
            }
            "".length();
            if (((0x59 ^ 0x64) & ~(0x4F ^ 0x72)) <= -" ".length()) {
                return null;
            }
        }
        return llIlIlllllIIIIl;
    }
    
    private static boolean lIlIIIlIl(final int llIlIlllIIIlllI, final int llIlIlllIIIllIl) {
        return llIlIlllIIIlllI == llIlIlllIIIllIl;
    }
    
    public static void addData(final XRayData llIlIlllIllllll) {
        XRayManager.xrayList.add(llIlIlllIllllll);
        "".length();
        FileManager.saveXRayData();
        final String s = XRayManager.llIIlII[XRayManager.llIlIII[2]];
        final Object[] array = new Object[XRayManager.llIlIII[3]];
        array[XRayManager.llIlIII[0]] = llIlIlllIllllll.getId();
        array[XRayManager.llIlIII[1]] = llIlIlllIllllll.getMeta();
        array[XRayManager.llIlIII[2]] = Block.getBlockById(llIlIlllIllllll.getId()).getLocalizedName();
        array[XRayManager.llIlIII[4]] = llIlIlllIllllll.getRed();
        array[XRayManager.llIlIII[5]] = llIlIlllIllllll.getGreen();
        array[XRayManager.llIlIII[6]] = llIlIlllIllllll.getBlue();
        ChatUtils.message(String.format(s, array));
    }
    
    private static boolean lIlIIIlII(final int llIlIlllIIIIlIl) {
        return llIlIlllIIIIlIl != 0;
    }
    
    private static boolean lIlIIIlll(final int llIlIlllIIIlIlI, final int llIlIlllIIIlIIl) {
        return llIlIlllIIIlIlI < llIlIlllIIIlIIl;
    }
    
    private static boolean lIlIIIIll(final int llIlIlllIIIIIll) {
        return llIlIlllIIIIIll == 0;
    }
    
    public static void clear() {
        if (lIlIIIIll(XRayManager.xrayList.isEmpty() ? 1 : 0)) {
            XRayManager.xrayList.clear();
            FileManager.saveXRayData();
            ChatUtils.message(XRayManager.llIIlII[XRayManager.llIlIII[0]]);
        }
    }
    
    private static void lIlIIIIlI() {
        (llIlIII = new int[7])[0] = ((0x59 ^ 0x25 ^ (0xB8 ^ 0x85)) & (0x3F ^ 0x13 ^ (0xD2 ^ 0xBF) ^ -" ".length()));
        XRayManager.llIlIII[1] = " ".length();
        XRayManager.llIlIII[2] = "  ".length();
        XRayManager.llIlIII[3] = (101 + 10 - 55 + 101 ^ 2 + 107 - 52 + 98);
        XRayManager.llIlIII[4] = "   ".length();
        XRayManager.llIlIII[5] = (0xE0 ^ 0x8C ^ (0x4 ^ 0x6C));
        XRayManager.llIlIII[6] = (23 + 98 - 63 + 126 ^ 39 + 115 - 90 + 125);
    }
    
    private static void lIIlllllI() {
        (llIIlII = new String[XRayManager.llIlIII[5]])[XRayManager.llIlIII[0]] = lIIlllIIl("w60RHSMbM1XDokYWIwYxURkmECQDVA==", "JuEqz");
        XRayManager.llIIlII[XRayManager.llIlIII[1]] = lIIlllIlI("ejgUjl7VqZM4ZUn1sE3lgw==", "ewylb");
        XRayManager.llIIlII[XRayManager.llIlIII[2]] = lIIlllIlI("xkzZG8Kw7fdz9fPwdMrmHz0kLNjII3gE9S1m8azyt7wVboaASmLQZmZusmvX3o51qpdUihmZ8FVdC548eb3o/W/nRgKRIQrfcz0bzDpgESGMaiRHoPaHv1x6+xIeg1m3", "nIwOS");
        XRayManager.llIIlII[XRayManager.llIlIII[4]] = lIIlllIIl("w61mMBxPasO2Sn0GasO2ThY0BxRDeMOSeXQKeMOSfQM+Gk9qw7YafQbDrWZVeMOSK3QKw79CZnHDnmFQOXHDnm9YagM8FTocFD12", "JQyXu");
    }
    
    public static void removeData(final int llIlIlllIlllIIl) {
        final int llIlIlllIllIlll = (int)getDataById(llIlIlllIlllIIl).iterator();
        while (lIlIIIlII(((Iterator)llIlIlllIllIlll).hasNext() ? 1 : 0)) {
            final XRayData llIlIlllIlllIlI = ((Iterator<XRayData>)llIlIlllIllIlll).next();
            if (lIlIIIlII(XRayManager.xrayList.contains(llIlIlllIlllIlI) ? 1 : 0)) {
                XRayManager.xrayList.remove(llIlIlllIlllIlI);
                "".length();
                FileManager.saveXRayData();
                final String s = XRayManager.llIIlII[XRayManager.llIlIII[4]];
                final Object[] array = new Object[XRayManager.llIlIII[6]];
                array[XRayManager.llIlIII[0]] = llIlIlllIlllIlI.getId();
                array[XRayManager.llIlIII[1]] = Block.getBlockById(llIlIlllIlllIlI.getId()).getLocalizedName();
                array[XRayManager.llIlIII[2]] = llIlIlllIlllIlI.getRed();
                array[XRayManager.llIlIII[4]] = llIlIlllIlllIlI.getGreen();
                array[XRayManager.llIlIII[5]] = llIlIlllIlllIlI.getBlue();
                ChatUtils.message(String.format(s, array));
            }
            "".length();
            if (" ".length() > " ".length()) {
                return;
            }
        }
    }
    
    private static boolean lIlIIIllI(final Object llIlIlllIIIIlll) {
        return llIlIlllIIIIlll == null;
    }
}
