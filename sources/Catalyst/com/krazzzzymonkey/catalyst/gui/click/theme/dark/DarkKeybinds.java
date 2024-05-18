// 
// Decompiled by Procyon v0.5.36
// 

package com.krazzzzymonkey.catalyst.gui.click.theme.dark;

import net.minecraft.client.gui.FontRenderer;
import org.lwjgl.input.Keyboard;
import com.krazzzzymonkey.catalyst.utils.visual.RenderUtils;
import com.krazzzzymonkey.catalyst.utils.visual.ColorUtils;
import com.krazzzzymonkey.catalyst.module.modules.gui.ClickGui;
import com.krazzzzymonkey.catalyst.gui.click.elements.KeybindMods;
import com.krazzzzymonkey.catalyst.gui.click.base.Component;
import com.krazzzzymonkey.catalyst.gui.click.base.ComponentType;
import com.krazzzzymonkey.catalyst.gui.click.theme.Theme;
import java.util.Base64;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import com.krazzzzymonkey.catalyst.gui.click.base.ComponentRenderer;

public class DarkKeybinds extends ComponentRenderer
{
    private static final /* synthetic */ int[] lIIIIIII;
    private static final /* synthetic */ String[] lllllIl;
    
    private static String llIIlIIIl(final String llIlIIIIlIlllIl, final String llIlIIIIlIlllII) {
        try {
            final SecretKeySpec llIlIIIIllIIIII = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(llIlIIIIlIlllII.getBytes(StandardCharsets.UTF_8)), DarkKeybinds.lIIIIIII[11]), "DES");
            final Cipher llIlIIIIlIlllll = Cipher.getInstance("DES");
            llIlIIIIlIlllll.init(DarkKeybinds.lIIIIIII[3], llIlIIIIllIIIII);
            return new String(llIlIIIIlIlllll.doFinal(Base64.getDecoder().decode(llIlIIIIlIlllIl.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception llIlIIIIlIllllI) {
            llIlIIIIlIllllI.printStackTrace();
            return null;
        }
    }
    
    private static boolean llIlIIIII(final int llIlIIIIIllIIII, final int llIlIIIIIlIllll) {
        return llIlIIIIIllIIII == llIlIIIIIlIllll;
    }
    
    private static boolean llIlIIIlI(final int llIlIIIIIlIllII, final int llIlIIIIIlIlIll) {
        return llIlIIIIIlIllII < llIlIIIIIlIlIll;
    }
    
    static {
        llIIlllIl();
        llIIllIII();
    }
    
    private static void llIIlllIl() {
        (lIIIIIII = new int[12])[0] = 157 + 146 - 167 + 119;
        DarkKeybinds.lIIIIIII[1] = ((0x53 ^ 0x5D) & ~(0x81 ^ 0x8F));
        DarkKeybinds.lIIIIIII[2] = (0x70 ^ 0x26 ^ (0x62 ^ 0x30));
        DarkKeybinds.lIIIIIII[3] = "  ".length();
        DarkKeybinds.lIIIIIII[4] = " ".length();
        DarkKeybinds.lIIIIIII[5] = (0x31 ^ 0x36);
        DarkKeybinds.lIIIIIII[6] = (0x67 ^ 0x6B);
        DarkKeybinds.lIIIIIII[7] = -" ".length();
        DarkKeybinds.lIIIIIII[8] = "   ".length();
        DarkKeybinds.lIIIIIII[9] = (95 + 3 - 53 + 86 ^ 114 + 22 - 2 + 0);
        DarkKeybinds.lIIIIIII[10] = (0xD1 ^ 0x91 ^ (0x22 ^ 0x64));
        DarkKeybinds.lIIIIIII[11] = (70 + 45 + 43 + 15 ^ 14 + 58 + 40 + 53);
    }
    
    private static void llIIllIII() {
        (lllllIl = new String[DarkKeybinds.lIIIIIII[10]])[DarkKeybinds.lIIIIIII[1]] = llIIlIIIl("9PLnFyyHZxU=", "dGqwM");
        DarkKeybinds.lllllIl[DarkKeybinds.lIIIIIII[4]] = llIIlIllI("JxYW", "lsoEv");
        DarkKeybinds.lllllIl[DarkKeybinds.lIIIIIII[3]] = llIIlIlll("6rIvQzsZmRw=", "kIjzF");
        DarkKeybinds.lllllIl[DarkKeybinds.lIIIIIII[8]] = llIIlIIIl("W6qJCCzVaCs=", "Qrgas");
        DarkKeybinds.lllllIl[DarkKeybinds.lIIIIIII[2]] = llIIlIIIl("Wd1mSmOD9hc=", "VKoQB");
        DarkKeybinds.lllllIl[DarkKeybinds.lIIIIIII[9]] = llIIlIlll("/8PP+X5EPOE=", "MhHLZ");
    }
    
    private static boolean llIIlllll(final int llIlIIIIIlIlIIl) {
        return llIlIIIIIlIlIIl != 0;
    }
    
    public DarkKeybinds(final Theme llIlIIIIlllllIl) {
        super(ComponentType.KEYBIND, llIlIIIIlllllIl);
    }
    
    @Override
    public void doInteractions(final Component llIlIIIIllIIlll, final int llIlIIIIllIIllI, final int llIlIIIIllIIlIl) {
    }
    
    @Override
    public void drawComponent(final Component llIlIIIIllIllIl, final int llIlIIIIlllIlII, final int llIlIIIIlllIIll) {
        final KeybindMods llIlIIIIlllIIlI = (KeybindMods)llIlIIIIllIllIl;
        int n;
        if (llIIlllll(ClickGui.isLight ? 1 : 0)) {
            n = ColorUtils.color(DarkKeybinds.lIIIIIII[0], DarkKeybinds.lIIIIIII[0], DarkKeybinds.lIIIIIII[0], DarkKeybinds.lIIIIIII[0]);
            "".length();
            if (" ".length() == -" ".length()) {
                return;
            }
        }
        else {
            n = ColorUtils.color(DarkKeybinds.lIIIIIII[1], DarkKeybinds.lIIIIIII[1], DarkKeybinds.lIIIIIII[1], DarkKeybinds.lIIIIIII[0]);
        }
        final int llIlIIIIlllIIIl = n;
        int n2;
        if (llIIlllll(ClickGui.isLight ? 1 : 0)) {
            n2 = ColorUtils.color(DarkKeybinds.lIIIIIII[1], DarkKeybinds.lIIIIIII[1], DarkKeybinds.lIIIIIII[1], DarkKeybinds.lIIIIIII[0]);
            "".length();
            if (-" ".length() < -" ".length()) {
                return;
            }
        }
        else {
            n2 = ColorUtils.color(DarkKeybinds.lIIIIIII[0], DarkKeybinds.lIIIIIII[0], DarkKeybinds.lIIIIIII[0], DarkKeybinds.lIIIIIII[0]);
        }
        final int llIlIIIIlllIIII = n2;
        this.theme.fontRenderer.drawString(DarkKeybinds.lllllIl[DarkKeybinds.lIIIIIII[1]], llIlIIIIlllIIlI.getX() + DarkKeybinds.lIIIIIII[2], llIlIIIIlllIIlI.getY() + DarkKeybinds.lIIIIIII[3], llIlIIIIlllIIII);
        "".length();
        final int llIlIIIIllIllll = this.theme.fontRenderer.getStringWidth(DarkKeybinds.lllllIl[DarkKeybinds.lIIIIIII[4]]) + DarkKeybinds.lIIIIIII[5];
        RenderUtils.drawRect((float)(llIlIIIIlllIIlI.getX() + llIlIIIIllIllll), (float)llIlIIIIlllIIlI.getY(), (float)(llIlIIIIlllIIlI.getX() + llIlIIIIlllIIlI.getDimension().width), (float)(llIlIIIIlllIIlI.getY() + DarkKeybinds.lIIIIIII[6]), llIlIIIIlllIIIl);
        if (llIlIIIII(llIlIIIIlllIIlI.getMod().getKey(), DarkKeybinds.lIIIIIII[7])) {
            final FontRenderer fontRenderer = this.theme.fontRenderer;
            String s;
            if (llIIlllll(llIlIIIIlllIIlI.isEditing() ? 1 : 0)) {
                s = DarkKeybinds.lllllIl[DarkKeybinds.lIIIIIII[3]];
                "".length();
                if (" ".length() < " ".length()) {
                    return;
                }
            }
            else {
                s = DarkKeybinds.lllllIl[DarkKeybinds.lIIIIIII[8]];
            }
            final int n3 = llIlIIIIlllIIlI.getX() + llIlIIIIlllIIlI.getDimension().width / DarkKeybinds.lIIIIIII[3] + llIlIIIIllIllll / DarkKeybinds.lIIIIIII[3] - this.theme.fontRenderer.getStringWidth(DarkKeybinds.lllllIl[DarkKeybinds.lIIIIIII[2]]) / DarkKeybinds.lIIIIIII[3];
            final int n4 = llIlIIIIlllIIlI.getY() + DarkKeybinds.lIIIIIII[3];
            int color;
            if (llIIlllll(llIlIIIIlllIIlI.isEditing() ? 1 : 0)) {
                color = llIlIIIIlllIIII;
                "".length();
                if (-" ".length() > -" ".length()) {
                    return;
                }
            }
            else {
                color = ColorUtils.color(0.6f, 0.6f, 0.6f, 1.0f);
            }
            fontRenderer.drawString(s, n3, n4, color);
            "".length();
            "".length();
            if (null != null) {
                return;
            }
        }
        else {
            final FontRenderer fontRenderer2 = this.theme.fontRenderer;
            String keyName;
            if (llIIlllll(llIlIIIIlllIIlI.isEditing() ? 1 : 0)) {
                keyName = DarkKeybinds.lllllIl[DarkKeybinds.lIIIIIII[9]];
                "".length();
                if ("  ".length() > (0x21 ^ 0x25)) {
                    return;
                }
            }
            else {
                keyName = Keyboard.getKeyName(llIlIIIIlllIIlI.getMod().getKey());
            }
            final int n5 = llIlIIIIlllIIlI.getX() + llIlIIIIlllIIlI.getDimension().width / DarkKeybinds.lIIIIIII[3] + llIlIIIIllIllll / DarkKeybinds.lIIIIIII[3] - this.theme.fontRenderer.getStringWidth(Keyboard.getKeyName(llIlIIIIlllIIlI.getMod().getKey())) / DarkKeybinds.lIIIIIII[3];
            final int n6 = llIlIIIIlllIIlI.getY() + DarkKeybinds.lIIIIIII[3];
            int n7;
            if (llIIlllll(llIlIIIIlllIIlI.isEditing() ? 1 : 0)) {
                n7 = llIlIIIIlllIIII;
                "".length();
                if ("   ".length() != "   ".length()) {
                    return;
                }
            }
            else {
                n7 = llIlIIIIlllIIII;
            }
            fontRenderer2.drawString(keyName, n5, n6, n7);
            "".length();
        }
    }
    
    private static String llIIlIlll(final String llIlIIIIIlllIII, final String llIlIIIIIllIlIl) {
        try {
            final SecretKeySpec llIlIIIIIlllIll = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(llIlIIIIIllIlIl.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            final Cipher llIlIIIIIlllIlI = Cipher.getInstance("Blowfish");
            llIlIIIIIlllIlI.init(DarkKeybinds.lIIIIIII[3], llIlIIIIIlllIll);
            return new String(llIlIIIIIlllIlI.doFinal(Base64.getDecoder().decode(llIlIIIIIlllIII.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception llIlIIIIIlllIIl) {
            llIlIIIIIlllIIl.printStackTrace();
            return null;
        }
    }
    
    private static String llIIlIllI(String llIlIIIIlIIlIII, final String llIlIIIIlIIIlll) {
        llIlIIIIlIIlIII = new String(Base64.getDecoder().decode(llIlIIIIlIIlIII.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        final StringBuilder llIlIIIIlIIlIll = new StringBuilder();
        final char[] llIlIIIIlIIlIlI = llIlIIIIlIIIlll.toCharArray();
        int llIlIIIIlIIlIIl = DarkKeybinds.lIIIIIII[1];
        final short llIlIIIIlIIIIll = (Object)llIlIIIIlIIlIII.toCharArray();
        final double llIlIIIIlIIIIlI = llIlIIIIlIIIIll.length;
        String llIlIIIIlIIIIIl = (String)DarkKeybinds.lIIIIIII[1];
        while (llIlIIIlI((int)llIlIIIIlIIIIIl, (int)llIlIIIIlIIIIlI)) {
            final char llIlIIIIlIIlllI = llIlIIIIlIIIIll[llIlIIIIlIIIIIl];
            llIlIIIIlIIlIll.append((char)(llIlIIIIlIIlllI ^ llIlIIIIlIIlIlI[llIlIIIIlIIlIIl % llIlIIIIlIIlIlI.length]));
            "".length();
            ++llIlIIIIlIIlIIl;
            ++llIlIIIIlIIIIIl;
            "".length();
            if ("  ".length() <= 0) {
                return null;
            }
        }
        return String.valueOf(llIlIIIIlIIlIll);
    }
}
