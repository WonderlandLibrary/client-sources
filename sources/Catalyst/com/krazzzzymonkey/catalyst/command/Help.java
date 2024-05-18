// 
// Decompiled by Procyon v0.5.36
// 

package com.krazzzzymonkey.catalyst.command;

import java.util.Iterator;
import com.krazzzzymonkey.catalyst.utils.visual.ChatUtils;
import com.krazzzzymonkey.catalyst.managers.CommandManager;
import java.util.Base64;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class Help extends Command
{
    private static final /* synthetic */ int[] llIlll;
    private static final /* synthetic */ String[] llIlII;
    
    private static void llIIlIlI() {
        (llIlll = new int[9])[0] = ((0x29 ^ 0x3) & ~(0x40 ^ 0x6A) & ~((0x7 ^ 0x51) & ~(0x1C ^ 0x4A)));
        Help.llIlll[1] = " ".length();
        Help.llIlll[2] = "  ".length();
        Help.llIlll[3] = "   ".length();
        Help.llIlll[4] = (0x23 ^ 0x27);
        Help.llIlll[5] = (122 + 43 + 27 + 6 ^ 153 + 156 - 307 + 193);
        Help.llIlll[6] = (0xD8 ^ 0xAF ^ (0x12 ^ 0x63));
        Help.llIlll[7] = (0x29 ^ 0x2D ^ "   ".length());
        Help.llIlll[8] = (45 + 63 + 17 + 2 ^ (0x31 ^ 0x46));
    }
    
    @Override
    public String getSyntax() {
        return Help.llIlII[Help.llIlll[7]];
    }
    
    private static void llIIIlll() {
        (llIlII = new String[Help.llIlll[8]])[Help.llIlll[0]] = lIllllll("yxXTGSMsgSA=", "PNWYN");
        Help.llIlII[Help.llIlll[1]] = llIIIIII("8NddeBmud3Y=", "efsJA");
        Help.llIlII[Help.llIlll[2]] = llIIIIIl("TMOLXg==", "plgfD");
        Help.llIlII[Help.llIlll[3]] = llIIIIII("ddViRJWm+q8=", "quLVm");
        Help.llIlII[Help.llIlll[4]] = lIllllll("8jqlzp7ZHDY=", "ZYuAj");
        Help.llIlII[Help.llIlll[5]] = lIllllll("Ok2mwFb4fIQ=", "JWMOt");
        Help.llIlII[Help.llIlll[6]] = llIIIIIl("Ax4ZEARvFgYIVywYBwkWIRMZSg==", "Owjdw");
        Help.llIlII[Help.llIlll[7]] = llIIIIII("v8OnmamwLcQ=", "FXlwZ");
    }
    
    @Override
    public String getDescription() {
        return Help.llIlII[Help.llIlll[6]];
    }
    
    private static boolean llIIlIll(final int lllIIlIIlIllIII) {
        return lllIIlIIlIllIII != 0;
    }
    
    private static String llIIIIII(final String lllIIlIIllIIlIl, final String lllIIlIIllIIlII) {
        try {
            final SecretKeySpec lllIIlIIllIlIlI = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(lllIIlIIllIIlII.getBytes(StandardCharsets.UTF_8)), Help.llIlll[8]), "DES");
            final Cipher lllIIlIIllIlIIl = Cipher.getInstance("DES");
            lllIIlIIllIlIIl.init(Help.llIlll[2], lllIIlIIllIlIlI);
            return new String(lllIIlIIllIlIIl.doFinal(Base64.getDecoder().decode(lllIIlIIllIIlIl.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lllIIlIIllIlIII) {
            lllIIlIIllIlIII.printStackTrace();
            return null;
        }
    }
    
    private static boolean llIIllIl(final int lllIIlIIlIlllll, final int lllIIlIIlIllllI) {
        return lllIIlIIlIlllll < lllIIlIIlIllllI;
    }
    
    public Help() {
        super(Help.llIlII[Help.llIlll[0]]);
    }
    
    static {
        llIIlIlI();
        llIIIlll();
    }
    
    private static boolean llIIllII(final Object lllIIlIIlIllIll, final Object lllIIlIIlIllIlI) {
        return lllIIlIIlIllIll != lllIIlIIlIllIlI;
    }
    
    private static String llIIIIIl(String lllIIlIIlllIlll, final String lllIIlIIlllIllI) {
        lllIIlIIlllIlll = (char)new String(Base64.getDecoder().decode(((String)lllIIlIIlllIlll).getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        final StringBuilder lllIIlIIllllIlI = new StringBuilder();
        final char[] lllIIlIIllllIIl = lllIIlIIlllIllI.toCharArray();
        int lllIIlIIllllIII = Help.llIlll[0];
        final int lllIIlIIlllIIlI = (Object)((String)lllIIlIIlllIlll).toCharArray();
        final String lllIIlIIlllIIIl = (String)lllIIlIIlllIIlI.length;
        double lllIIlIIlllIIII = Help.llIlll[0];
        while (llIIllIl((int)lllIIlIIlllIIII, (int)lllIIlIIlllIIIl)) {
            final char lllIIlIIlllllIl = lllIIlIIlllIIlI[lllIIlIIlllIIII];
            lllIIlIIllllIlI.append((char)(lllIIlIIlllllIl ^ lllIIlIIllllIIl[lllIIlIIllllIII % lllIIlIIllllIIl.length]));
            "".length();
            ++lllIIlIIllllIII;
            ++lllIIlIIlllIIII;
            "".length();
            if (((0x49 ^ 0x75) & ~(0xA0 ^ 0x9C)) != 0x0) {
                return null;
            }
        }
        return String.valueOf(lllIIlIIllllIlI);
    }
    
    @Override
    public void runCommand(final String lllIIlIlIIllIlI, final String[] lllIIlIlIIllIIl) {
        final int lllIIlIlIIlIlll = (int)CommandManager.commands.iterator();
        while (llIIlIll(((Iterator)lllIIlIlIIlIlll).hasNext() ? 1 : 0)) {
            final Command lllIIlIlIIlllII = ((Iterator<Command>)lllIIlIlIIlIlll).next();
            if (llIIllII(lllIIlIlIIlllII, this)) {
                ChatUtils.message(String.valueOf(new StringBuilder().append(lllIIlIlIIlllII.getSyntax().replace(Help.llIlII[Help.llIlll[1]], Help.llIlII[Help.llIlll[2]]).replace(Help.llIlII[Help.llIlll[3]], Help.llIlII[Help.llIlll[4]])).append(Help.llIlII[Help.llIlll[5]]).append(lllIIlIlIIlllII.getDescription())));
            }
            "".length();
            if (-" ".length() == "   ".length()) {
                return;
            }
        }
    }
    
    private static String lIllllll(final String lllIIlIlIIIlIlI, final String lllIIlIlIIIlIll) {
        try {
            final SecretKeySpec lllIIlIlIIIllll = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(lllIIlIlIIIlIll.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            final Cipher lllIIlIlIIIlllI = Cipher.getInstance("Blowfish");
            lllIIlIlIIIlllI.init(Help.llIlll[2], lllIIlIlIIIllll);
            return new String(lllIIlIlIIIlllI.doFinal(Base64.getDecoder().decode(lllIIlIlIIIlIlI.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lllIIlIlIIIllIl) {
            lllIIlIlIIIllIl.printStackTrace();
            return null;
        }
    }
}
