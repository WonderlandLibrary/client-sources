// 
// Decompiled by Procyon v0.5.36
// 

package com.krazzzzymonkey.catalyst.managers;

import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import com.krazzzzymonkey.catalyst.utils.LoginUtils;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class LoginManager
{
    private static final /* synthetic */ int[] lIllIIll;
    private /* synthetic */ boolean favourites;
    private /* synthetic */ String name;
    private static final /* synthetic */ String[] lIllIIII;
    private /* synthetic */ String email;
    private /* synthetic */ String password;
    private /* synthetic */ boolean cracked;
    
    private static boolean lIIIlIIIlI(final int llIIIIlIIIlllll) {
        return llIIIIlIIIlllll != 0;
    }
    
    private static void lIIIIllIll() {
        (lIllIIII = new String[LoginManager.lIllIIll[2]])[LoginManager.lIllIIll[1]] = lIIIIllIII("", "guAOL");
        LoginManager.lIllIIII[LoginManager.lIllIIll[0]] = lIIIIllIIl("T2jn+YLyCWA=", "irySv");
    }
    
    public boolean isCracked() {
        return this.cracked;
    }
    
    public String getEmail() {
        return this.email;
    }
    
    public LoginManager(final String llIIIIllIIlIIll, String llIIIIllIIlIIlI, final String llIIIIllIIlIllI, final boolean llIIIIllIIlIIII) {
        this.email = llIIIIllIIlIIll;
        this.favourites = llIIIIllIIlIIII;
        if (!lIIIlIIIIl(llIIIIllIIlIllI) || lIIIlIIIlI(llIIIIllIIlIllI.isEmpty() ? 1 : 0)) {
            llIIIIllIIlIIlI = (int)llIIIIllIIlIIll;
            this.password = null;
            this.cracked = (LoginManager.lIllIIll[0] != 0);
            "".length();
            if ("  ".length() < 0) {
                throw null;
            }
        }
        else {
            this.name = (String)llIIIIllIIlIIlI;
            this.password = llIIIIllIIlIllI;
            this.cracked = (LoginManager.lIllIIll[1] != 0);
        }
    }
    
    private static String lIIIIllIII(String llIIIIlIIlIllll, final String llIIIIlIIllIIll) {
        llIIIIlIIlIllll = (double)new String(Base64.getDecoder().decode(((String)llIIIIlIIlIllll).getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        final StringBuilder llIIIIlIIllIIlI = new StringBuilder();
        final char[] llIIIIlIIllIIIl = llIIIIlIIllIIll.toCharArray();
        int llIIIIlIIllIIII = LoginManager.lIllIIll[1];
        final double llIIIIlIIlIlIlI = (Object)((String)llIIIIlIIlIllll).toCharArray();
        final char llIIIIlIIlIlIIl = (char)llIIIIlIIlIlIlI.length;
        short llIIIIlIIlIlIII = (short)LoginManager.lIllIIll[1];
        while (lIIIlIIIll(llIIIIlIIlIlIII, llIIIIlIIlIlIIl)) {
            final char llIIIIlIIllIlIl = llIIIIlIIlIlIlI[llIIIIlIIlIlIII];
            llIIIIlIIllIIlI.append((char)(llIIIIlIIllIlIl ^ llIIIIlIIllIIIl[llIIIIlIIllIIII % llIIIIlIIllIIIl.length]));
            "".length();
            ++llIIIIlIIllIIII;
            ++llIIIIlIIlIlIII;
            "".length();
            if (null != null) {
                return null;
            }
        }
        return String.valueOf(llIIIIlIIllIIlI);
    }
    
    static {
        lIIIlIIIII();
        lIIIIllIll();
    }
    
    private static void lIIIlIIIII() {
        (lIllIIll = new int[3])[0] = " ".length();
        LoginManager.lIllIIll[1] = ((116 + 53 - 107 + 74 ^ 89 + 140 - 69 + 21) & (123 + 99 - 184 + 146 ^ 116 + 68 - 170 + 119 ^ -" ".length()));
        LoginManager.lIllIIll[2] = "  ".length();
    }
    
    public LoginManager(final String llIIIIllIIIlIII, final boolean llIIIIllIIIIlll) {
        this.email = llIIIIllIIIlIII;
        this.name = llIIIIllIIIlIII;
        this.password = null;
        this.cracked = (LoginManager.lIllIIll[0] != 0);
        this.favourites = llIIIIllIIIIlll;
    }
    
    public void setName(final String llIIIIlIllIllll) {
        this.name = llIIIIlIllIllll;
    }
    
    public LoginManager(final String llIIIIllIIIIIll) {
        this(llIIIIllIIIIIll, (boolean)(LoginManager.lIllIIll[1] != 0));
    }
    
    private static boolean lIIIlIIIll(final int llIIIIlIIlIIlII, final int llIIIIlIIlIIIll) {
        return llIIIIlIIlIIlII < llIIIIlIIlIIIll;
    }
    
    public String getName() {
        if (lIIIlIIIIl(this.name)) {
            return this.name;
        }
        if (lIIIlIIIIl(this.email)) {
            return this.email;
        }
        return LoginManager.lIllIIII[LoginManager.lIllIIll[1]];
    }
    
    public void setPassword(String llIIIIlIllIIIll) {
        this.password = llIIIIlIllIIIll;
        if (!lIIIlIIIIl(llIIIIlIllIIIll) || lIIIlIIIlI(llIIIIlIllIIIll.isEmpty() ? 1 : 0)) {
            this.name = this.email;
            llIIIIlIllIIIll = null;
            this.cracked = (LoginManager.lIllIIll[0] != 0);
            "".length();
            if (null != null) {
                return;
            }
        }
        else {
            this.name = LoginUtils.getName(this.email, llIIIIlIllIIIll);
            this.password = llIIIIlIllIIIll;
            this.cracked = (LoginManager.lIllIIll[1] != 0);
        }
    }
    
    public boolean isFavourites() {
        return this.favourites;
    }
    
    private static boolean lIIIlIIIIl(final Object llIIIIlIIlIIIIl) {
        return llIIIIlIIlIIIIl != null;
    }
    
    public void setFavourites(final boolean llIIIIlIlIlIIIl) {
        this.favourites = llIIIIlIlIlIIIl;
    }
    
    private static String lIIIIllIIl(final String llIIIIlIlIIIlII, final String llIIIIlIlIIIIll) {
        try {
            final SecretKeySpec llIIIIlIlIIIlll = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(llIIIIlIlIIIIll.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            final Cipher llIIIIlIlIIIllI = Cipher.getInstance("Blowfish");
            llIIIIlIlIIIllI.init(LoginManager.lIllIIll[2], llIIIIlIlIIIlll);
            return new String(llIIIIlIlIIIllI.doFinal(Base64.getDecoder().decode(llIIIIlIlIIIlII.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception llIIIIlIlIIIlIl) {
            llIIIIlIlIIIlIl.printStackTrace();
            return null;
        }
    }
    
    public LoginManager(final String llIIIIllIlIIIll, final String llIIIIllIIlllll) {
        this(llIIIIllIlIIIll, llIIIIllIIlllll, (boolean)(LoginManager.lIllIIll[1] != 0));
    }
    
    public void setCracked() {
        this.name = this.email;
        this.password = null;
        this.cracked = (LoginManager.lIllIIll[0] != 0);
    }
    
    public LoginManager(final String llIIIIllIlIlllI, final String llIIIIllIlIlIIl, final boolean llIIIIllIlIllII) {
        this.email = llIIIIllIlIlllI;
        this.favourites = llIIIIllIlIllII;
        if (!lIIIlIIIIl(llIIIIllIlIlIIl) || lIIIlIIIlI(llIIIIllIlIlIIl.isEmpty() ? 1 : 0)) {
            this.name = llIIIIllIlIlllI;
            this.password = null;
            this.cracked = (LoginManager.lIllIIll[0] != 0);
            "".length();
            if ((0xC1 ^ 0xB0 ^ (0xCD ^ 0xB8)) > (0x7D ^ 0x10 ^ (0xC7 ^ 0xAE))) {
                throw null;
            }
        }
        else {
            this.name = LoginUtils.getName(llIIIIllIlIlllI, llIIIIllIlIlIIl);
            this.password = llIIIIllIlIlIIl;
            this.cracked = (LoginManager.lIllIIll[1] != 0);
        }
    }
    
    public void setEmail(final String llIIIIlIllllIlI) {
        this.email = llIIIIlIllllIlI;
        if (!lIIIlIIIIl(this.password) || lIIIlIIIlI(this.password.isEmpty() ? 1 : 0)) {
            this.name = llIIIIlIllllIlI;
            this.password = null;
            this.cracked = (LoginManager.lIllIIll[0] != 0);
            "".length();
            if ("   ".length() == "  ".length()) {
                return;
            }
        }
        else {
            this.name = LoginUtils.getName(llIIIIlIllllIlI, this.password);
            this.cracked = (LoginManager.lIllIIll[1] != 0);
        }
    }
    
    public String getPassword() {
        if (!lIIIlIIIIl(this.password) || lIIIlIIIlI(this.password.isEmpty() ? 1 : 0)) {
            this.cracked = (LoginManager.lIllIIll[0] != 0);
            return LoginManager.lIllIIII[LoginManager.lIllIIll[0]];
        }
        return this.password;
    }
}
