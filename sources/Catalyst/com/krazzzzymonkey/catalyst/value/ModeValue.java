// 
// Decompiled by Procyon v0.5.36
// 

package com.krazzzzymonkey.catalyst.value;

import java.util.Base64;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class ModeValue extends Value<Mode>
{
    private static final /* synthetic */ int[] lIlIIIIl;
    private static final /* synthetic */ String[] lIIllllI;
    private /* synthetic */ String modeName;
    private /* synthetic */ Mode[] modes;
    
    private static void lIIIIIIIII() {
        (lIlIIIIl = new int[3])[0] = ((0x2E ^ 0x6) & ~(0x85 ^ 0xAD));
        ModeValue.lIlIIIIl[1] = " ".length();
        ModeValue.lIlIIIIl[2] = "  ".length();
    }
    
    public ModeValue(final String llIIIllllIlIIlI, final Mode... llIIIllllIlIIIl) {
        super(String.valueOf(new StringBuilder().append(ModeValue.lIIllllI[ModeValue.lIlIIIIl[0]]).append(llIIIllllIlIIlI)), null);
        this.modeName = llIIIllllIlIIlI;
        this.modes = llIIIllllIlIIIl;
    }
    
    private static String llllllllI(final String llIIIlllIllIlII, final String llIIIlllIllIIIl) {
        try {
            final SecretKeySpec llIIIlllIllIlll = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(llIIIlllIllIIIl.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            final Cipher llIIIlllIllIllI = Cipher.getInstance("Blowfish");
            llIIIlllIllIllI.init(ModeValue.lIlIIIIl[2], llIIIlllIllIlll);
            return new String(llIIIlllIllIllI.doFinal(Base64.getDecoder().decode(llIIIlllIllIlII.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception llIIIlllIllIlIl) {
            llIIIlllIllIlIl.printStackTrace();
            return null;
        }
    }
    
    public Mode[] getModes() {
        return this.modes;
    }
    
    static {
        lIIIIIIIII();
        lllllllll();
    }
    
    private static boolean lIIIIIIIIl(final int llIIIlllIlIllII, final int llIIIlllIlIlIll) {
        return llIIIlllIlIllII < llIIIlllIlIlIll;
    }
    
    public Mode getMode(final String llIIIllllIIIlII) {
        Mode llIIIllllIIIllI = null;
        final byte llIIIllllIIIIlI = (Object)this.modes;
        final short llIIIllllIIIIIl = (short)llIIIllllIIIIlI.length;
        short llIIIllllIIIIII = (short)ModeValue.lIlIIIIl[0];
        while (lIIIIIIIIl(llIIIllllIIIIII, llIIIllllIIIIIl)) {
            final Mode llIIIllllIIlIIl = llIIIllllIIIIlI[llIIIllllIIIIII];
            if (lIIIIIIIlI(llIIIllllIIlIIl.getName().equals(llIIIllllIIIlII) ? 1 : 0)) {
                llIIIllllIIIllI = llIIIllllIIlIIl;
            }
            ++llIIIllllIIIIII;
            "".length();
            if (((((0x10 ^ 0x5) & ~(0x96 ^ 0x83)) ^ (0x5C ^ 0xD)) & (0x79 ^ 0x37 ^ (0x63 ^ 0x7C) ^ -" ".length())) < 0) {
                return null;
            }
        }
        return llIIIllllIIIllI;
    }
    
    private static boolean lIIIIIIIlI(final int llIIIlllIlIlIIl) {
        return llIIIlllIlIlIIl != 0;
    }
    
    public String getModeName() {
        return this.modeName;
    }
    
    private static void lllllllll() {
        (lIIllllI = new String[ModeValue.lIlIIIIl[1]])[ModeValue.lIlIIIIl[0]] = llllllllI("/dXnKr+mN+E=", "TZfMc");
    }
}
