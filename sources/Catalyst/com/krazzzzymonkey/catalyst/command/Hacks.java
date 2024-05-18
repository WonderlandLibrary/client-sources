// 
// Decompiled by Procyon v0.5.36
// 

package com.krazzzzymonkey.catalyst.command;

import java.util.Iterator;
import com.krazzzzymonkey.catalyst.utils.visual.ChatUtils;
import com.krazzzzymonkey.catalyst.module.Modules;
import com.krazzzzymonkey.catalyst.managers.HackManager;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Hacks extends Command
{
    private static final /* synthetic */ String[] lIlIlIl;
    private static final /* synthetic */ int[] lIllIII;
    
    private static String lIIIlIIII(String llIllIlllllIlII, final String llIllIllllIlllI) {
        llIllIlllllIlII = new String(Base64.getDecoder().decode(llIllIlllllIlII.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        final StringBuilder llIllIlllllIIlI = new StringBuilder();
        final char[] llIllIlllllIIIl = llIllIllllIlllI.toCharArray();
        int llIllIlllllIIII = Hacks.lIllIII[0];
        final float llIllIllllIlIlI = (Object)llIllIlllllIlII.toCharArray();
        final long llIllIllllIlIIl = llIllIllllIlIlI.length;
        float llIllIllllIlIII = Hacks.lIllIII[0];
        while (lIIIlllII((int)llIllIllllIlIII, (int)llIllIllllIlIIl)) {
            final char llIllIlllllIlIl = llIllIllllIlIlI[llIllIllllIlIII];
            llIllIlllllIIlI.append((char)(llIllIlllllIlIl ^ llIllIlllllIIIl[llIllIlllllIIII % llIllIlllllIIIl.length]));
            "".length();
            ++llIllIlllllIIII;
            ++llIllIllllIlIII;
            "".length();
            if ((0x13 ^ 0x35 ^ (0xB6 ^ 0x94)) == 0x0) {
                return null;
            }
        }
        return String.valueOf(llIllIlllllIIlI);
    }
    
    private static void lIIIllIlI() {
        (lIllIII = new int[8])[0] = ((0xF7 ^ 0xA4) & ~(0x94 ^ 0xC7));
        Hacks.lIllIII[1] = " ".length();
        Hacks.lIllIII[2] = (0x54 ^ 0x50);
        Hacks.lIllIII[3] = "  ".length();
        Hacks.lIllIII[4] = "   ".length();
        Hacks.lIllIII[5] = (0x7C ^ 0x65 ^ (0x34 ^ 0x28));
        Hacks.lIllIII[6] = (55 + 13 - 46 + 119 ^ 127 + 80 - 106 + 38);
        Hacks.lIllIII[7] = (56 + 28 + 44 + 30 ^ 71 + 31 + 15 + 33);
    }
    
    private static String lIIIlIIIl(final String llIlllIIIIIIIlI, final String llIlllIIIIIIIll) {
        try {
            final SecretKeySpec llIlllIIIIIIlll = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(llIlllIIIIIIIll.getBytes(StandardCharsets.UTF_8)), Hacks.lIllIII[7]), "DES");
            final Cipher llIlllIIIIIIllI = Cipher.getInstance("DES");
            llIlllIIIIIIllI.init(Hacks.lIllIII[3], llIlllIIIIIIlll);
            return new String(llIlllIIIIIIllI.doFinal(Base64.getDecoder().decode(llIlllIIIIIIIlI.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception llIlllIIIIIIlIl) {
            llIlllIIIIIIlIl.printStackTrace();
            return null;
        }
    }
    
    private static boolean lIIIllIll(final int llIllIllllIIIIl) {
        return llIllIllllIIIIl != 0;
    }
    
    @Override
    public String getDescription() {
        return Hacks.lIlIlIl[Hacks.lIllIII[2]];
    }
    
    private static String lIIIlIIlI(final String llIlllIIIIlIIIl, final String llIlllIIIIlIIII) {
        try {
            final SecretKeySpec llIlllIIIIlIlII = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(llIlllIIIIlIIII.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            final Cipher llIlllIIIIlIIll = Cipher.getInstance("Blowfish");
            llIlllIIIIlIIll.init(Hacks.lIllIII[3], llIlllIIIIlIlII);
            return new String(llIlllIIIIlIIll.doFinal(Base64.getDecoder().decode(llIlllIIIIlIIIl.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception llIlllIIIIlIIlI) {
            llIlllIIIIlIIlI.printStackTrace();
            return null;
        }
    }
    
    public Hacks() {
        super(Hacks.lIlIlIl[Hacks.lIllIII[0]]);
    }
    
    private static void lIIIlIllI() {
        (lIlIlIl = new String[Hacks.lIllIII[6]])[Hacks.lIllIII[0]] = lIIIlIIII("KiYkGhU=", "BGGqf");
        Hacks.lIlIlIl[Hacks.lIllIII[1]] = lIIIlIIIl("7blGeUbi1Mfhh8RCmiPDVcvLiLUWpcci2X1afFXsllm/ZH1Gv8h2mA==", "jfQDh");
        Hacks.lIlIlIl[Hacks.lIllIII[3]] = lIIIlIIlI("WFg57yzktm4=", "FldKB");
        Hacks.lIlIlIl[Hacks.lIllIII[4]] = lIIIlIIII("QgsRFRkRbQ==", "bCpvr");
        Hacks.lIlIlIl[Hacks.lIllIII[2]] = lIIIlIIlI("abSbmsN/vSY9ADMl5Oej+7HorWwz3oAO", "tmgSu");
        Hacks.lIlIlIl[Hacks.lIllIII[5]] = lIIIlIIII("Gi4RITQ=", "rOrJG");
    }
    
    private static boolean lIIIlllII(final int llIllIllllIIlII, final int llIllIllllIIIll) {
        return llIllIllllIIlII < llIllIllllIIIll;
    }
    
    @Override
    public String getSyntax() {
        return Hacks.lIlIlIl[Hacks.lIllIII[5]];
    }
    
    @Override
    public void runCommand(final String llIlllIIIIllllI, final String[] llIlllIIIIlllIl) {
        final int llIlllIIIIlllII = (int)HackManager.getHacks().iterator();
        while (lIIIllIll(((Iterator)llIlllIIIIlllII).hasNext() ? 1 : 0)) {
            final Modules llIlllIIIlIIIII = ((Iterator<Modules>)llIlllIIIIlllII).next();
            final String s = Hacks.lIlIlIl[Hacks.lIllIII[1]];
            final Object[] array = new Object[Hacks.lIllIII[2]];
            array[Hacks.lIllIII[0]] = llIlllIIIlIIIII.getName();
            array[Hacks.lIllIII[1]] = llIlllIIIlIIIII.getCategory();
            array[Hacks.lIllIII[3]] = llIlllIIIlIIIII.getKey();
            array[Hacks.lIllIII[4]] = llIlllIIIlIIIII.isToggled();
            ChatUtils.message(String.format(s, array));
            "".length();
            if (null != null) {
                return;
            }
        }
        ChatUtils.message(String.valueOf(new StringBuilder().append(Hacks.lIlIlIl[Hacks.lIllIII[3]]).append(HackManager.getHacks().size()).append(Hacks.lIlIlIl[Hacks.lIllIII[4]])));
    }
    
    static {
        lIIIllIlI();
        lIIIlIllI();
    }
}
