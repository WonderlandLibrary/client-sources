// 
// Decompiled by Procyon v0.5.36
// 

package com.krazzzzymonkey.catalyst.gui.click.theme.dark;

import com.krazzzzymonkey.catalyst.gui.click.base.ComponentRenderer;
import com.krazzzzymonkey.catalyst.gui.click.base.ComponentType;
import com.krazzzzymonkey.catalyst.utils.system.Wrapper;
import java.util.Base64;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import com.krazzzzymonkey.catalyst.gui.click.theme.Theme;

public class DarkTheme extends Theme
{
    private static final /* synthetic */ String[] lllIlI;
    private static final /* synthetic */ int[] lllIll;
    
    private static String llIlIIIl(final String lllIIlIIIlIllII, final String lllIIlIIIlIllIl) {
        try {
            final SecretKeySpec lllIIlIIIllIIIl = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(lllIIlIIIlIllIl.getBytes(StandardCharsets.UTF_8)), DarkTheme.lllIll[2]), "DES");
            final Cipher lllIIlIIIllIIII = Cipher.getInstance("DES");
            lllIIlIIIllIIII.init(DarkTheme.lllIll[3], lllIIlIIIllIIIl);
            return new String(lllIIlIIIllIIII.doFinal(Base64.getDecoder().decode(lllIIlIIIlIllII.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lllIIlIIIlIllll) {
            lllIIlIIIlIllll.printStackTrace();
            return null;
        }
    }
    
    public DarkTheme() {
        super(DarkTheme.lllIlI[DarkTheme.lllIll[0]]);
        this.fontRenderer = Wrapper.INSTANCE.fontRenderer();
        this.addRenderer(ComponentType.FRAME, new DarkFrame(this));
        this.addRenderer(ComponentType.BUTTON, new DarkButton(this));
        this.addRenderer(ComponentType.SLIDER, new DarkSlider(this));
        this.addRenderer(ComponentType.CHECK_BUTTON, new DarkCheckButton(this));
        this.addRenderer(ComponentType.EXPANDING_BUTTON, new DarkExpandingButton(this));
        this.addRenderer(ComponentType.TEXT, new DarkText(this));
        this.addRenderer(ComponentType.KEYBIND, new DarkKeybinds(this));
        this.addRenderer(ComponentType.DROPDOWN, new DarkDropDown(this));
        this.addRenderer(ComponentType.COMBO_BOX, new DarkComboBox(this));
    }
    
    static {
        llIlIIll();
        llIlIIlI();
    }
    
    private static void llIlIIlI() {
        (lllIlI = new String[DarkTheme.lllIll[1]])[DarkTheme.lllIll[0]] = llIlIIIl("WEWSt/o7FsiOZpgXKs3pmQ==", "RCYql");
    }
    
    private static void llIlIIll() {
        (lllIll = new int[4])[0] = ((0xFB ^ 0xB6 ^ (0x1F ^ 0x1A)) & (0xB6 ^ 0xA3 ^ (0x17 ^ 0x4A) ^ -" ".length()));
        DarkTheme.lllIll[1] = " ".length();
        DarkTheme.lllIll[2] = (0x1D ^ 0x15);
        DarkTheme.lllIll[3] = "  ".length();
    }
}
