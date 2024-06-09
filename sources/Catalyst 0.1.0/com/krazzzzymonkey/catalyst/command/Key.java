// 
// Decompiled by Procyon v0.5.36
// 

package com.krazzzzymonkey.catalyst.command;

import java.util.Iterator;
import java.util.Arrays;
import com.krazzzzymonkey.catalyst.utils.visual.ChatUtils;
import org.lwjgl.input.Keyboard;
import com.krazzzzymonkey.catalyst.module.Modules;
import com.krazzzzymonkey.catalyst.managers.HackManager;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class Key extends Command
{
    private static final /* synthetic */ int[] llIIlI;
    private static final /* synthetic */ String[] lIlllI;
    
    private static String lIllIlIl(final String lllIIlIllllllII, final String lllIIlIlllllIll) {
        try {
            final SecretKeySpec lllIIlIllllllll = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(lllIIlIlllllIll.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            final Cipher lllIIlIlllllllI = Cipher.getInstance("Blowfish");
            lllIIlIlllllllI.init(Key.llIIlI[2], lllIIlIllllllll);
            return new String(lllIIlIlllllllI.doFinal(Base64.getDecoder().decode(lllIIlIllllllII.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lllIIlIllllllIl) {
            lllIIlIllllllIl.printStackTrace();
            return null;
        }
    }
    
    private static String lIllIlII(String lllIIllIIIllIIl, final String lllIIllIIIllIII) {
        lllIIllIIIllIIl = (boolean)new String(Base64.getDecoder().decode(((String)lllIIllIIIllIIl).getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        final StringBuilder lllIIllIIIlllII = new StringBuilder();
        final char[] lllIIllIIIllIll = lllIIllIIIllIII.toCharArray();
        int lllIIllIIIllIlI = Key.llIIlI[0];
        final Exception lllIIllIIIlIlII = (Object)((String)lllIIllIIIllIIl).toCharArray();
        final float lllIIllIIIlIIll = lllIIllIIIlIlII.length;
        double lllIIllIIIlIIlI = Key.llIIlI[0];
        while (lIllllIl((int)lllIIllIIIlIIlI, (int)lllIIllIIIlIIll)) {
            final char lllIIllIIIlllll = lllIIllIIIlIlII[lllIIllIIIlIIlI];
            lllIIllIIIlllII.append((char)(lllIIllIIIlllll ^ lllIIllIIIllIll[lllIIllIIIllIlI % lllIIllIIIllIll.length]));
            "".length();
            ++lllIIllIIIllIlI;
            ++lllIIllIIIlIIlI;
            "".length();
            if (null != null) {
                return null;
            }
        }
        return String.valueOf(lllIIllIIIlllII);
    }
    
    @Override
    public String getSyntax() {
        return Key.lIlllI[Key.llIIlI[4]];
    }
    
    @Override
    public String getDescription() {
        return Key.lIlllI[Key.llIIlI[3]];
    }
    
    private static boolean lIllllII(final int lllIIlIllllIIIl) {
        return lllIIlIllllIIIl != 0;
    }
    
    static {
        lIlllIll();
        lIllIlll();
    }
    
    private static boolean lIllllIl(final int lllIIlIllllIlII, final int lllIIlIllllIIll) {
        return lllIIlIllllIlII < lllIIlIllllIIll;
    }
    
    @Override
    public void runCommand(final String lllIIllIIllIIII, final String[] lllIIllIIlIllll) {
        try {
            final float lllIIllIIlIllII = (float)HackManager.getHacks().iterator();
            while (lIllllII(((Iterator)lllIIllIIlIllII).hasNext() ? 1 : 0)) {
                final Modules lllIIllIIllIIll = ((Iterator<Modules>)lllIIllIIlIllII).next();
                if (lIllllII(lllIIllIIllIIll.getName().equalsIgnoreCase(lllIIllIIlIllll[Key.llIIlI[1]]) ? 1 : 0)) {
                    lllIIllIIllIIll.setKey(Keyboard.getKeyIndex(lllIIllIIlIllll[Key.llIIlI[0]].toUpperCase()));
                    ChatUtils.message(String.valueOf(new StringBuilder().append(lllIIllIIllIIll.getName()).append(Key.lIlllI[Key.llIIlI[1]]).append(Keyboard.getKeyName(lllIIllIIllIIll.getKey()))));
                }
                "".length();
                if (" ".length() != " ".length()) {
                    return;
                }
            }
            "".length();
            if ("   ".length() <= 0) {
                return;
            }
        }
        catch (Exception lllIIllIIllIIlI) {
            ChatUtils.error(String.valueOf(new StringBuilder().append(Key.lIlllI[Key.llIIlI[2]]).append(this.getSyntax())));
        }
    }
    
    private static void lIlllIll() {
        (llIIlI = new int[7])[0] = ((0x4A ^ 0x5C) & ~(0x7E ^ 0x68));
        Key.llIIlI[1] = " ".length();
        Key.llIIlI[2] = "  ".length();
        Key.llIIlI[3] = "   ".length();
        Key.llIIlI[4] = (188 + 128 - 194 + 74 ^ 7 + 8 + 2 + 175);
        Key.llIIlI[5] = (0xE6 ^ 0xB6 ^ (0x1F ^ 0x4A));
        Key.llIIlI[6] = (0xCC ^ 0x96 ^ (0xCE ^ 0x9C));
    }
    
    public Key() {
        super(Key.lIlllI[Key.llIIlI[0]]);
    }
    
    private static String lIllIllI(final String lllIIllIIIIlIIl, final String lllIIllIIIIlIII) {
        try {
            final SecretKeySpec lllIIllIIIIllII = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(lllIIllIIIIlIII.getBytes(StandardCharsets.UTF_8)), Key.llIIlI[6]), "DES");
            final Cipher lllIIllIIIIlIll = Cipher.getInstance("DES");
            lllIIllIIIIlIll.init(Key.llIIlI[2], lllIIllIIIIllII);
            return new String(lllIIllIIIIlIll.doFinal(Base64.getDecoder().decode(lllIIllIIIIlIIl.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lllIIllIIIIlIlI) {
            lllIIllIIIIlIlI.printStackTrace();
            return null;
        }
    }
    
    private static void lIllIlll() {
        (lIlllI = new String[Key.llIIlI[5]])[Key.llIIlI[0]] = lIllIlII("BREY", "ntaLT");
        Key.lIlllI[Key.llIIlI[1]] = lIllIlII("VgksMXQVCigmMxMGaTw7VsOFcA==", "vbIHT");
        Key.lIlllI[Key.llIIlI[2]] = lIllIlIl("JT6ICQRxet0=", "vkkJl");
        Key.lIlllI[Key.llIIlI[3]] = lIllIllI("lQyxwdidh9X8qqC8LFnnnsuUuhFiI1TC", "MicUk");
        Key.lIlllI[Key.llIIlI[4]] = lIllIlIl("5fNulaRMhCphBN0+ftGcjycSjVy+3Kto", "rGoTm");
    }
}
