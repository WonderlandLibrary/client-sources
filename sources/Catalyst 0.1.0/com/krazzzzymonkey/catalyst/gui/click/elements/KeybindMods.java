// 
// Decompiled by Procyon v0.5.36
// 

package com.krazzzzymonkey.catalyst.gui.click.elements;

import org.lwjgl.input.Keyboard;
import com.krazzzzymonkey.catalyst.utils.system.Wrapper;
import com.krazzzzymonkey.catalyst.gui.click.base.ComponentType;
import java.util.Base64;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import com.krazzzzymonkey.catalyst.module.Modules;
import com.krazzzzymonkey.catalyst.gui.click.base.Component;

public class KeybindMods extends Component
{
    private static final /* synthetic */ String[] lllIIIl;
    private static final /* synthetic */ int[] lllIIll;
    private /* synthetic */ Modules mod;
    private /* synthetic */ boolean editing;
    
    private static String lIlllIIIl(final String llIlIIllIlIllII, final String llIlIIllIlIlIll) {
        try {
            final SecretKeySpec llIlIIllIllIIIl = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(llIlIIllIlIlIll.getBytes(StandardCharsets.UTF_8)), KeybindMods.lllIIll[6]), "DES");
            final Cipher llIlIIllIllIIII = Cipher.getInstance("DES");
            llIlIIllIllIIII.init(KeybindMods.lllIIll[5], llIlIIllIllIIIl);
            return new String(llIlIIllIllIIII.doFinal(Base64.getDecoder().decode(llIlIIllIlIllII.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception llIlIIllIlIllll) {
            llIlIIllIlIllll.printStackTrace();
            return null;
        }
    }
    
    private static boolean lIlllIlll(final int llIlIIllIIllIll) {
        return llIlIIllIIllIll != 0;
    }
    
    static {
        lIlllIllI();
        lIlllIIlI();
    }
    
    private static boolean lIllllIll(final int llIlIIllIlIIIlI, final int llIlIIllIlIIIIl) {
        return llIlIIllIlIIIlI < llIlIIllIlIIIIl;
    }
    
    public KeybindMods(final int llIlIIllllIllII, final int llIlIIlllllIIlI, final int llIlIIlllllIIIl, final int llIlIIllllIlIIl, final Component llIlIIllllIllll, final Modules llIlIIllllIIlll) {
        super(llIlIIllllIllII, llIlIIlllllIIlI, llIlIIlllllIIIl, llIlIIllllIlIIl, ComponentType.KEYBIND, llIlIIllllIllll, KeybindMods.lllIIIl[KeybindMods.lllIIll[0]]);
        this.mod = llIlIIllllIIlll;
    }
    
    private static boolean lIllllIlI(final int llIlIIllIIllllI, final int llIlIIllIIlllIl) {
        return llIlIIllIIllllI > llIlIIllIIlllIl;
    }
    
    public void setEditing(final boolean llIlIIlllIIlllI) {
        this.editing = llIlIIlllIIlllI;
    }
    
    private static void lIlllIIlI() {
        (lllIIIl = new String[KeybindMods.lllIIll[5]])[KeybindMods.lllIIll[0]] = lIlllIIII("", "ESTnN");
        KeybindMods.lllIIIl[KeybindMods.lllIIll[3]] = lIlllIIIl("MFbohSFjnoY=", "nLUFD");
    }
    
    private static boolean lIlllllII(final int llIlIIllIIllIIl) {
        return llIlIIllIIllIIl == 0;
    }
    
    public boolean isEditing() {
        return this.editing;
    }
    
    private static boolean lIllllIII(final int llIlIIllIlIIllI, final int llIlIIllIlIIlIl) {
        return llIlIIllIlIIllI == llIlIIllIlIIlIl;
    }
    
    @Override
    public void onMousePress(final int llIlIIlllIlllll, final int llIlIIlllIllIlI, final int llIlIIlllIlllIl) {
        if (lIllllIlI(llIlIIlllIlllll, this.getX() + Wrapper.INSTANCE.fontRenderer().getStringWidth(KeybindMods.lllIIIl[KeybindMods.lllIIll[3]]) + KeybindMods.lllIIll[4]) && lIllllIll(llIlIIlllIlllll, this.getX() + this.getDimension().width) && lIllllIlI(llIlIIlllIllIlI, this.getY()) && lIllllIll(llIlIIlllIllIlI, this.getY() + this.getDimension().height)) {
            int editing;
            if (lIlllllII(this.editing ? 1 : 0)) {
                editing = KeybindMods.lllIIll[3];
                "".length();
                if (" ".length() <= 0) {
                    return;
                }
            }
            else {
                editing = KeybindMods.lllIIll[0];
            }
            this.editing = (editing != 0);
        }
    }
    
    public Modules getMod() {
        return this.mod;
    }
    
    private static String lIlllIIII(String llIlIIllIlllllI, final String llIlIIllIllllIl) {
        llIlIIllIlllllI = (long)new String(Base64.getDecoder().decode(((String)llIlIIllIlllllI).getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        final StringBuilder llIlIIlllIIIIIl = new StringBuilder();
        final char[] llIlIIlllIIIIII = llIlIIllIllllIl.toCharArray();
        int llIlIIllIllllll = KeybindMods.lllIIll[0];
        final String llIlIIllIlllIIl = (Object)((String)llIlIIllIlllllI).toCharArray();
        final float llIlIIllIlllIII = llIlIIllIlllIIl.length;
        char llIlIIllIllIlll = (char)KeybindMods.lllIIll[0];
        while (lIllllIll(llIlIIllIllIlll, (int)llIlIIllIlllIII)) {
            final char llIlIIlllIIIlII = llIlIIllIlllIIl[llIlIIllIllIlll];
            llIlIIlllIIIIIl.append((char)(llIlIIlllIIIlII ^ llIlIIlllIIIIII[llIlIIllIllllll % llIlIIlllIIIIII.length]));
            "".length();
            ++llIlIIllIllllll;
            ++llIlIIllIllIlll;
            "".length();
            if ("  ".length() <= " ".length()) {
                return null;
            }
        }
        return String.valueOf(llIlIIlllIIIIIl);
    }
    
    @Override
    public void onUpdate() {
        if (lIlllIlll(Keyboard.getEventKeyState() ? 1 : 0) && lIlllIlll(this.editing ? 1 : 0)) {
            if (lIllllIII(Keyboard.getEventKey(), KeybindMods.lllIIll[1])) {
                this.mod.setKey(KeybindMods.lllIIll[2]);
                "".length();
                if (" ".length() <= 0) {
                    return;
                }
            }
            else {
                this.mod.setKey(Keyboard.getEventKey());
            }
            this.editing = (KeybindMods.lllIIll[0] != 0);
        }
    }
    
    private static void lIlllIllI() {
        (lllIIll = new int[7])[0] = ((0xA2 ^ 0xC0) & ~(0x8 ^ 0x6A));
        KeybindMods.lllIIll[1] = 190 + 72 - 97 + 46;
        KeybindMods.lllIIll[2] = -" ".length();
        KeybindMods.lllIIll[3] = " ".length();
        KeybindMods.lllIIll[4] = (0xE1 ^ 0xC4 ^ (0x6B ^ 0x48));
        KeybindMods.lllIIll[5] = "  ".length();
        KeybindMods.lllIIll[6] = (0xFD ^ 0xAA ^ (0x23 ^ 0x7C));
    }
}
