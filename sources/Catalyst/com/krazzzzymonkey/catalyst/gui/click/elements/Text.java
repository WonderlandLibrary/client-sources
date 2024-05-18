// 
// Decompiled by Procyon v0.5.36
// 

package com.krazzzzymonkey.catalyst.gui.click.elements;

import java.util.Base64;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import com.krazzzzymonkey.catalyst.gui.click.base.ComponentType;
import com.krazzzzymonkey.catalyst.gui.click.base.Component;

public class Text extends Component
{
    private static final /* synthetic */ String[] lIIIl;
    private static final /* synthetic */ int[] lIlII;
    private /* synthetic */ String[] text;
    
    private static void lIlIIIl() {
        (lIIIl = new String[Text.lIlII[1]])[Text.lIlII[0]] = lIIllll("oosSSkkdBjA=", "WWcqL");
    }
    
    static {
        lIllIIl();
        lIlIIIl();
    }
    
    public Text(final int llllIlIlIllIIlI, final int llllIlIlIlIIllI, final int llllIlIlIlIlllI, final int llllIlIlIlIIlII, final Component llllIlIlIlIlIll, final String[] llllIlIlIlIlIIl) {
        super(llllIlIlIllIIlI, llllIlIlIlIIllI, llllIlIlIlIlllI, llllIlIlIlIIlII, ComponentType.TEXT, llllIlIlIlIlIll, Text.lIIIl[Text.lIlII[0]]);
        this.text = llllIlIlIlIlIIl;
    }
    
    private static void lIllIIl() {
        (lIlII = new int[3])[0] = ((0xB7 ^ 0x94) & ~(0x0 ^ 0x23));
        Text.lIlII[1] = " ".length();
        Text.lIlII[2] = "  ".length();
    }
    
    public String[] getMessage() {
        return this.text;
    }
    
    private static String lIIllll(final String llllIlIlIIIIllI, final String llllIlIlIIIIlIl) {
        try {
            final SecretKeySpec llllIlIlIIIlIll = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(llllIlIlIIIIlIl.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            final Cipher llllIlIlIIIlIlI = Cipher.getInstance("Blowfish");
            llllIlIlIIIlIlI.init(Text.lIlII[2], llllIlIlIIIlIll);
            return new String(llllIlIlIIIlIlI.doFinal(Base64.getDecoder().decode(llllIlIlIIIIllI.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception llllIlIlIIIlIIl) {
            llllIlIlIIIlIIl.printStackTrace();
            return null;
        }
    }
}
