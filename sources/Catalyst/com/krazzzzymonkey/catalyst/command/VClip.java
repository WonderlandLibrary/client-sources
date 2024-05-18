// 
// Decompiled by Procyon v0.5.36
// 

package com.krazzzzymonkey.catalyst.command;

import java.util.Arrays;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import com.krazzzzymonkey.catalyst.utils.visual.ChatUtils;
import java.math.BigInteger;
import com.krazzzzymonkey.catalyst.utils.system.Wrapper;

public class VClip extends Command
{
    private static final /* synthetic */ int[] llIIlIlI;
    private static final /* synthetic */ String[] llIIIllI;
    
    public VClip() {
        super(VClip.llIIIllI[VClip.llIIlIlI[0]]);
    }
    
    @Override
    public void runCommand(final String lIllllIlIIlIIlI, final String[] lIllllIlIIIllll) {
        try {
            Wrapper.INSTANCE.player().setPosition(Wrapper.INSTANCE.player().posX, Wrapper.INSTANCE.player().posY + new BigInteger(lIllllIlIIIllll[VClip.llIIlIlI[0]]).longValue(), Wrapper.INSTANCE.player().posZ);
            ChatUtils.message(String.valueOf(new StringBuilder().append(VClip.llIIIllI[VClip.llIIlIlI[1]]).append(new BigInteger(lIllllIlIIIllll[VClip.llIIlIlI[0]]).longValue())));
            "".length();
            if (" ".length() >= (0x59 ^ 0x5D)) {
                return;
            }
        }
        catch (Exception lIllllIlIIlIlII) {
            ChatUtils.error(String.valueOf(new StringBuilder().append(VClip.llIIIllI[VClip.llIIlIlI[2]]).append(this.getSyntax())));
        }
    }
    
    @Override
    public String getSyntax() {
        return VClip.llIIIllI[VClip.llIIlIlI[4]];
    }
    
    private static void lIIlIlIlII() {
        (llIIIllI = new String[VClip.llIIlIlI[5]])[VClip.llIIlIlI[0]] = lIIlIlIIIl("bAHrL6VeR0g=", "jgBPG");
        VClip.llIIIllI[VClip.llIIlIlI[1]] = lIIlIlIIlI("JiYNFAAaYxAWBAszCwEcCydEBwdO", "nCdsh");
        VClip.llIIIllI[VClip.llIIlIlI[2]] = lIIlIlIIll("OftsPJdzDd0=", "LrIqu");
        VClip.llIIIllI[VClip.llIIlIlI[3]] = lIIlIlIIlI("NQA8DDQOFyQaZBgKJUkxEUo0BjMPSw==", "aePiD");
        VClip.llIIIllI[VClip.llIIlIlI[4]] = lIIlIlIIlI("LyQhJxd5eyUrDj4vOXA=", "YGMNg");
    }
    
    private static String lIIlIlIIlI(String lIllllIIlIIlIIl, final String lIllllIIlIIllIl) {
        lIllllIIlIIlIIl = new String(Base64.getDecoder().decode(lIllllIIlIIlIIl.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        final StringBuilder lIllllIIlIIllII = new StringBuilder();
        final char[] lIllllIIlIIlIll = lIllllIIlIIllIl.toCharArray();
        int lIllllIIlIIlIlI = VClip.llIIlIlI[0];
        final Exception lIllllIIlIIIlII = (Object)lIllllIIlIIlIIl.toCharArray();
        final char lIllllIIlIIIIll = (char)lIllllIIlIIIlII.length;
        String lIllllIIlIIIIlI = (String)VClip.llIIlIlI[0];
        while (lIIllIIlIl((int)lIllllIIlIIIIlI, lIllllIIlIIIIll)) {
            final char lIllllIIlIIllll = lIllllIIlIIIlII[lIllllIIlIIIIlI];
            lIllllIIlIIllII.append((char)(lIllllIIlIIllll ^ lIllllIIlIIlIll[lIllllIIlIIlIlI % lIllllIIlIIlIll.length]));
            "".length();
            ++lIllllIIlIIlIlI;
            ++lIllllIIlIIIIlI;
            "".length();
            if (" ".length() >= "   ".length()) {
                return null;
            }
        }
        return String.valueOf(lIllllIIlIIllII);
    }
    
    private static String lIIlIlIIll(final String lIllllIIllllIlI, final String lIllllIIllllIIl) {
        try {
            final SecretKeySpec lIllllIlIIIIIIl = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(lIllllIIllllIIl.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            final Cipher lIllllIlIIIIIII = Cipher.getInstance("Blowfish");
            lIllllIlIIIIIII.init(VClip.llIIlIlI[2], lIllllIlIIIIIIl);
            return new String(lIllllIlIIIIIII.doFinal(Base64.getDecoder().decode(lIllllIIllllIlI.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lIllllIIllllllI) {
            lIllllIIllllllI.printStackTrace();
            return null;
        }
    }
    
    private static void lIIllIIIIl() {
        (llIIlIlI = new int[7])[0] = ((0x18 ^ 0x30) & ~(0x8A ^ 0xA2));
        VClip.llIIlIlI[1] = " ".length();
        VClip.llIIlIlI[2] = "  ".length();
        VClip.llIIlIlI[3] = "   ".length();
        VClip.llIIlIlI[4] = (0x5D ^ 0x50 ^ (0x85 ^ 0x8C));
        VClip.llIIlIlI[5] = (0x6 ^ 0x3);
        VClip.llIIlIlI[6] = (0xC ^ 0x33 ^ (0x66 ^ 0x51));
    }
    
    private static String lIIlIlIIIl(final String lIllllIIllIIIll, final String lIllllIIllIIIII) {
        try {
            final SecretKeySpec lIllllIIllIIllI = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(lIllllIIllIIIII.getBytes(StandardCharsets.UTF_8)), VClip.llIIlIlI[6]), "DES");
            final Cipher lIllllIIllIIlIl = Cipher.getInstance("DES");
            lIllllIIllIIlIl.init(VClip.llIIlIlI[2], lIllllIIllIIllI);
            return new String(lIllllIIllIIlIl.doFinal(Base64.getDecoder().decode(lIllllIIllIIIll.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lIllllIIllIIlII) {
            lIllllIIllIIlII.printStackTrace();
            return null;
        }
    }
    
    private static boolean lIIllIIlIl(final int lIllllIIIlllIIl, final int lIllllIIIlllIII) {
        return lIllllIIIlllIIl < lIllllIIIlllIII;
    }
    
    static {
        lIIllIIIIl();
        lIIlIlIlII();
    }
    
    @Override
    public String getDescription() {
        return VClip.llIIIllI[VClip.llIIlIlI[3]];
    }
}
