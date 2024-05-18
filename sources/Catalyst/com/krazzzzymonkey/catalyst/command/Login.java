// 
// Decompiled by Procyon v0.5.36
// 

package com.krazzzzymonkey.catalyst.command;

import java.util.Arrays;
import com.krazzzzymonkey.catalyst.utils.system.Wrapper;
import com.krazzzzymonkey.catalyst.utils.visual.ChatUtils;
import com.krazzzzymonkey.catalyst.utils.LoginUtils;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Login extends Command
{
    private static final /* synthetic */ String[] lIIllIll;
    private static final /* synthetic */ int[] lIlIIIlI;
    
    @Override
    public String getSyntax() {
        return Login.lIIllIll[Login.lIlIIIlI[9]];
    }
    
    private static String llllllIIl(String llIIIllIlIlIlIl, final String llIIIllIlIlIlII) {
        llIIIllIlIlIlIl = (boolean)new String(Base64.getDecoder().decode(((String)llIIIllIlIlIlIl).getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        final StringBuilder llIIIllIlIllIII = new StringBuilder();
        final char[] llIIIllIlIlIlll = llIIIllIlIlIlII.toCharArray();
        int llIIIllIlIlIllI = Login.lIlIIIlI[0];
        final Exception llIIIllIlIlIIII = (Object)((String)llIIIllIlIlIlIl).toCharArray();
        final String llIIIllIlIIllll = (String)llIIIllIlIlIIII.length;
        boolean llIIIllIlIIlllI = Login.lIlIIIlI[0] != 0;
        while (lIIIIIIllI(llIIIllIlIIlllI ? 1 : 0, (int)llIIIllIlIIllll)) {
            final char llIIIllIlIllIll = llIIIllIlIlIIII[llIIIllIlIIlllI];
            llIIIllIlIllIII.append((char)(llIIIllIlIllIll ^ llIIIllIlIlIlll[llIIIllIlIlIllI % llIIIllIlIlIlll.length]));
            "".length();
            ++llIIIllIlIlIllI;
            ++llIIIllIlIIlllI;
            "".length();
            if ("   ".length() < ((0x79 ^ 0x23 ^ (0x5D ^ 0x2)) & (0x67 ^ 0x79 ^ (0xD8 ^ 0xC3) ^ -" ".length()))) {
                return null;
            }
        }
        return String.valueOf(llIIIllIlIllIII);
    }
    
    @Override
    public String getDescription() {
        return Login.lIIllIll[Login.lIlIIIlI[8]];
    }
    
    static {
        lIIIIIIIll();
        lllllllIl();
    }
    
    private static void lIIIIIIIll() {
        (lIlIIIlI = new int[11])[0] = ((117 + 21 + 7 + 72 ^ 74 + 148 - 111 + 41) & (4 + 94 - 59 + 166 ^ 81 + 91 - 117 + 85 ^ -" ".length()));
        Login.lIlIIIlI[1] = " ".length();
        Login.lIlIIIlI[2] = "  ".length();
        Login.lIlIIIlI[3] = "   ".length();
        Login.lIlIIIlI[4] = (0x95 ^ 0xC2 ^ (0xE7 ^ 0xB4));
        Login.lIlIIIlI[5] = (0x83 ^ 0xAB ^ (0x83 ^ 0xAE));
        Login.lIlIIIlI[6] = (15 + 77 + 43 + 0 ^ 118 + 41 - 136 + 106);
        Login.lIlIIIlI[7] = (0x99 ^ 0x9E);
        Login.lIlIIIlI[8] = (0x1A ^ 0x34 ^ (0x49 ^ 0x6F));
        Login.lIlIIIlI[9] = (0xA1 ^ 0xA8);
        Login.lIlIIIlI[10] = (167 + 132 - 190 + 69 ^ 156 + 145 - 169 + 52);
    }
    
    private static boolean lIIIIIIlIl(final int llIIIllIIllIllI) {
        return llIIIllIIllIllI != 0;
    }
    
    private static boolean lIIIIIIllI(final int llIIIllIIllllIl, final int llIIIllIIllllII) {
        return llIIIllIIllllIl < llIIIllIIllllII;
    }
    
    private static String lllllIIIl(final String llIIIllIllIlIlI, final String llIIIllIllIIlll) {
        try {
            final SecretKeySpec llIIIllIllIllIl = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(llIIIllIllIIlll.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            final Cipher llIIIllIllIllII = Cipher.getInstance("Blowfish");
            llIIIllIllIllII.init(Login.lIlIIIlI[2], llIIIllIllIllIl);
            return new String(llIIIllIllIllII.doFinal(Base64.getDecoder().decode(llIIIllIllIlIlI.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception llIIIllIllIlIll) {
            llIIIllIllIlIll.printStackTrace();
            return null;
        }
    }
    
    public Login() {
        super(Login.lIIllIll[Login.lIlIIIlI[0]]);
    }
    
    @Override
    public void runCommand(final String llIIIllIllllIlI, final String[] llIIIllIlllIlll) {
        try {
            if (!lIIIIIIlII(llIIIllIlllIlll.length, Login.lIlIIIlI[1]) || lIIIIIIlIl(llIIIllIlllIlll[Login.lIlIIIlI[0]].contains(Login.lIIllIll[Login.lIlIIIlI[1]]) ? 1 : 0)) {
                String llIIIllIlllllll = Login.lIIllIll[Login.lIlIIIlI[2]];
                String llIIIllIllllllI = Login.lIIllIll[Login.lIlIIIlI[3]];
                if (lIIIIIIlIl(llIIIllIlllIlll[Login.lIlIIIlI[0]].contains(Login.lIIllIll[Login.lIlIIIlI[4]]) ? 1 : 0)) {
                    final String[] llIIIlllIIIIIII = llIIIllIlllIlll[Login.lIlIIIlI[0]].split(Login.lIIllIll[Login.lIlIIIlI[5]], Login.lIlIIIlI[2]);
                    llIIIllIlllllll = llIIIlllIIIIIII[Login.lIlIIIlI[0]];
                    llIIIllIllllllI = llIIIlllIIIIIII[Login.lIlIIIlI[1]];
                    "".length();
                    if (((11 + 80 - 7 + 72 ^ 89 + 126 - 177 + 129) & (27 + 113 - 116 + 113 ^ 71 + 51 + 40 + 16 ^ -" ".length())) != 0x0) {
                        return;
                    }
                }
                else {
                    llIIIllIlllllll = llIIIllIlllIlll[Login.lIlIIIlI[0]];
                    llIIIllIllllllI = llIIIllIlllIlll[Login.lIlIIIlI[1]];
                }
                final String llIIIllIlllllIl = LoginUtils.loginAlt(llIIIllIlllllll, llIIIllIllllllI);
                ChatUtils.warning(llIIIllIlllllIl);
                "".length();
                if (((0x6C ^ 0x72) & ~(0x45 ^ 0x5B)) != 0x0) {
                    return;
                }
            }
            else {
                LoginUtils.changeCrackedName(llIIIllIlllIlll[Login.lIlIIIlI[0]]);
                ChatUtils.warning(String.valueOf(new StringBuilder().append(Login.lIIllIll[Login.lIlIIIlI[6]]).append(Wrapper.INSTANCE.mc().getSession().getUsername())));
            }
            "".length();
            if (" ".length() == (0xC5 ^ 0xC1)) {
                return;
            }
        }
        catch (Exception llIIIllIlllllII) {
            ChatUtils.error(String.valueOf(new StringBuilder().append(Login.lIIllIll[Login.lIlIIIlI[7]]).append(this.getSyntax())));
        }
    }
    
    private static String llllIllll(final String llIIIllIlIIIlIl, final String llIIIllIlIIIlII) {
        try {
            final SecretKeySpec llIIIllIlIIlIII = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(llIIIllIlIIIlII.getBytes(StandardCharsets.UTF_8)), Login.lIlIIIlI[8]), "DES");
            final Cipher llIIIllIlIIIlll = Cipher.getInstance("DES");
            llIIIllIlIIIlll.init(Login.lIlIIIlI[2], llIIIllIlIIlIII);
            return new String(llIIIllIlIIIlll.doFinal(Base64.getDecoder().decode(llIIIllIlIIIlIl.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception llIIIllIlIIIllI) {
            llIIIllIlIIIllI.printStackTrace();
            return null;
        }
    }
    
    private static void lllllllIl() {
        (lIIllIll = new String[Login.lIlIIIlI[10]])[Login.lIlIIIlI[0]] = llllIllll("dCIMOp1l85k=", "jRFiZ");
        Login.lIIllIll[Login.lIlIIIlI[1]] = llllIllll("GKKVMYoOX8E=", "xzEvi");
        Login.lIIllIll[Login.lIlIIIlI[2]] = lllllIIIl("xstbXA/RhAA=", "jINrb");
        Login.lIIllIll[Login.lIlIIIlI[3]] = llllIllll("zsWSRvaKlnU=", "CbXQQ");
        Login.lIIllIll[Login.lIlIIIlI[4]] = lllllIIIl("NQITMOOS9Tw=", "nuLqT");
        Login.lIIllIll[Login.lIlIIIlI[5]] = llllllIIl("dg==", "LnokS");
        Login.lIIllIll[Login.lIlIIIlI[6]] = llllIllll("hWGh6+EzvvUdLukeQfzNuDZf5m+n95+z", "lZIPE");
        Login.lIIllIll[Login.lIlIIIlI[7]] = lllllIIIl("mfrGtaO4edk=", "HptWU");
        Login.lIIllIll[Login.lIlIIIlI[8]] = llllllIIl("ECc0Iys2byYoPyAmOiNi", "SOUML");
        Login.lIIllIll[Login.lIlIIIlI[9]] = llllllIIl("IQISMDxtURA0MyQBS3luPQwGKiUiHxFnfXEDHDo5cw==", "MmuYR");
    }
    
    private static boolean lIIIIIIlII(final int llIIIllIIlllIIl, final int llIIIllIIlllIII) {
        return llIIIllIIlllIIl <= llIIIllIIlllIII;
    }
}
