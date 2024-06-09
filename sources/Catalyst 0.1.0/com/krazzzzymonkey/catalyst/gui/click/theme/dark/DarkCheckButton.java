// 
// Decompiled by Procyon v0.5.36
// 

package com.krazzzzymonkey.catalyst.gui.click.theme.dark;

import com.krazzzzymonkey.catalyst.value.Mode;
import net.minecraft.client.gui.FontRenderer;
import com.krazzzzymonkey.catalyst.utils.MathUtils;
import com.krazzzzymonkey.catalyst.utils.visual.ColorUtils;
import com.krazzzzymonkey.catalyst.module.modules.gui.ClickGui;
import com.krazzzzymonkey.catalyst.gui.click.elements.CheckButton;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import com.krazzzzymonkey.catalyst.gui.click.base.ComponentType;
import com.krazzzzymonkey.catalyst.gui.click.theme.Theme;
import com.krazzzzymonkey.catalyst.gui.click.base.Component;
import com.krazzzzymonkey.catalyst.gui.click.base.ComponentRenderer;

public class DarkCheckButton extends ComponentRenderer
{
    private static final /* synthetic */ int[] llIIllll;
    private static final /* synthetic */ String[] lIlllIll;
    
    @Override
    public void doInteractions(final Component lIlllIIlIIIIIIl, final int lIlllIIlIIIIIII, final int lIlllIIIlllllll) {
    }
    
    public DarkCheckButton(final Theme lIlllIIlIlIIIII) {
        super(ComponentType.CHECK_BUTTON, lIlllIIlIlIIIII);
    }
    
    static {
        lIIllIllIl();
        lIIIllIIII();
    }
    
    private static String lIIIlIlIIl(String lIlllIIIllIIIlI, final String lIlllIIIllIIllI) {
        lIlllIIIllIIIlI = new String(Base64.getDecoder().decode(lIlllIIIllIIIlI.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        final StringBuilder lIlllIIIllIIlIl = new StringBuilder();
        final char[] lIlllIIIllIIlII = lIlllIIIllIIllI.toCharArray();
        int lIlllIIIllIIIll = DarkCheckButton.llIIllll[1];
        final boolean lIlllIIIlIlllIl = (Object)lIlllIIIllIIIlI.toCharArray();
        final float lIlllIIIlIlllII = lIlllIIIlIlllIl.length;
        int lIlllIIIlIllIll = DarkCheckButton.llIIllll[1];
        while (lIIlllIIII(lIlllIIIlIllIll, (int)lIlllIIIlIlllII)) {
            final char lIlllIIIllIlIII = lIlllIIIlIlllIl[lIlllIIIlIllIll];
            lIlllIIIllIIlIl.append((char)(lIlllIIIllIlIII ^ lIlllIIIllIIlII[lIlllIIIllIIIll % lIlllIIIllIIlII.length]));
            "".length();
            ++lIlllIIIllIIIll;
            ++lIlllIIIlIllIll;
            "".length();
            if (-" ".length() != -" ".length()) {
                return null;
            }
        }
        return String.valueOf(lIlllIIIllIIlIl);
    }
    
    private static void lIIIllIIII() {
        (lIlllIll = new String[DarkCheckButton.llIIllll[5]])[DarkCheckButton.llIIllll[1]] = lIIIlIlIIl("dVI=", "KrpyR");
        DarkCheckButton.lIlllIll[DarkCheckButton.llIIllll[4]] = lIIIlIllII("XEVXAh1J67U=", "ZKlYD");
    }
    
    private static String lIIIlIllII(final String lIlllIIIlllIlll, final String lIlllIIIlllIlII) {
        try {
            final SecretKeySpec lIlllIIIllllIlI = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(lIlllIIIlllIlII.getBytes(StandardCharsets.UTF_8)), DarkCheckButton.llIIllll[6]), "DES");
            final Cipher lIlllIIIllllIIl = Cipher.getInstance("DES");
            lIlllIIIllllIIl.init(DarkCheckButton.llIIllll[5], lIlllIIIllllIlI);
            return new String(lIlllIIIllllIIl.doFinal(Base64.getDecoder().decode(lIlllIIIlllIlll.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lIlllIIIllllIII) {
            lIlllIIIllllIII.printStackTrace();
            return null;
        }
    }
    
    private static boolean lIIllIlllI(final int lIlllIIIlIlIIlI) {
        return lIlllIIIlIlIIlI != 0;
    }
    
    private static boolean lIIllIllll(final Object lIlllIIIlIlIlII) {
        return lIlllIIIlIlIlII == null;
    }
    
    private static void lIIllIllIl() {
        (llIIllll = new int[7])[0] = (0x3D ^ 0x73) + (50 + 75 - 111 + 118) - -(0x7D ^ 0x66) + (0x65 ^ 0x77);
        DarkCheckButton.llIIllll[1] = ((0x72 ^ 0x3D) & ~(0xE1 ^ 0xAE));
        DarkCheckButton.llIIllll[2] = (9 + 126 - 72 + 115 ^ 1 + 29 + 90 + 63);
        DarkCheckButton.llIIllll[3] = "   ".length();
        DarkCheckButton.llIIllll[4] = " ".length();
        DarkCheckButton.llIIllll[5] = "  ".length();
        DarkCheckButton.llIIllll[6] = (0xB6 ^ 0x8A ^ (0x16 ^ 0x22));
    }
    
    @Override
    public void drawComponent(final Component lIlllIIlIIlIIll, final int lIlllIIlIIlIIlI, final int lIlllIIlIIlIIIl) {
        final CheckButton lIlllIIlIIlIIII = (CheckButton)lIlllIIlIIlIIll;
        final String lIlllIIlIIIllll = lIlllIIlIIlIIII.getText();
        int n;
        if (lIIllIlllI(ClickGui.isLight ? 1 : 0)) {
            n = ColorUtils.color(DarkCheckButton.llIIllll[0], DarkCheckButton.llIIllll[0], DarkCheckButton.llIIllll[0], DarkCheckButton.llIIllll[0]);
            "".length();
            if (-" ".length() > 0) {
                return;
            }
        }
        else {
            n = ColorUtils.color(DarkCheckButton.llIIllll[1], DarkCheckButton.llIIllll[1], DarkCheckButton.llIIllll[1], DarkCheckButton.llIIllll[0]);
        }
        final int lIlllIIlIIIlllI = n;
        int n2;
        if (lIIllIlllI(ClickGui.isLight ? 1 : 0)) {
            n2 = ColorUtils.color(DarkCheckButton.llIIllll[1], DarkCheckButton.llIIllll[1], DarkCheckButton.llIIllll[1], DarkCheckButton.llIIllll[0]);
            "".length();
            if (null != null) {
                return;
            }
        }
        else {
            n2 = ColorUtils.color(DarkCheckButton.llIIllll[0], DarkCheckButton.llIIllll[0], DarkCheckButton.llIIllll[0], DarkCheckButton.llIIllll[0]);
        }
        final int lIlllIIlIIIllIl = n2;
        if (lIIllIllll(lIlllIIlIIlIIII.getModeValue())) {
            final FontRenderer fontRenderer = this.theme.fontRenderer;
            final String value = String.valueOf(new StringBuilder().append(DarkCheckButton.lIlllIll[DarkCheckButton.llIIllll[1]]).append(lIlllIIlIIIllll));
            final int n3 = lIlllIIlIIlIIII.getX() + DarkCheckButton.llIIllll[2];
            final int n4 = MathUtils.getMiddle(lIlllIIlIIlIIII.getY(), lIlllIIlIIlIIII.getY() + lIlllIIlIIlIIII.getDimension().height) - this.theme.fontRenderer.FONT_HEIGHT / DarkCheckButton.llIIllll[3] - DarkCheckButton.llIIllll[4];
            int color;
            if (lIIllIlllI(lIlllIIlIIlIIII.isEnabled() ? 1 : 0)) {
                color = lIlllIIlIIIllIl;
                "".length();
                if (" ".length() < -" ".length()) {
                    return;
                }
            }
            else {
                color = ColorUtils.color(0.5f, 0.5f, 0.5f, 1.0f);
            }
            fontRenderer.drawString(value, n3, n4, color);
            "".length();
            return;
        }
        final short lIlllIIlIIIIllI = (Object)lIlllIIlIIlIIII.getModeValue().getModes();
        final byte lIlllIIlIIIIlIl = (byte)lIlllIIlIIIIllI.length;
        Exception lIlllIIlIIIIlII = (Exception)DarkCheckButton.llIIllll[1];
        while (lIIlllIIII((int)lIlllIIlIIIIlII, lIlllIIlIIIIlIl)) {
            final Mode lIlllIIlIIlIlIl = lIlllIIlIIIIllI[lIlllIIlIIIIlII];
            if (lIIllIlllI(lIlllIIlIIlIlIl.getName().equals(lIlllIIlIIIllll) ? 1 : 0)) {
                final FontRenderer fontRenderer2 = this.theme.fontRenderer;
                final String value2 = String.valueOf(new StringBuilder().append(DarkCheckButton.lIlllIll[DarkCheckButton.llIIllll[4]]).append(lIlllIIlIIIllll));
                final int n5 = lIlllIIlIIlIIII.getX() + DarkCheckButton.llIIllll[2];
                final int n6 = MathUtils.getMiddle(lIlllIIlIIlIIII.getY(), lIlllIIlIIlIIII.getY() + lIlllIIlIIlIIII.getDimension().height) - this.theme.fontRenderer.FONT_HEIGHT / DarkCheckButton.llIIllll[3] - DarkCheckButton.llIIllll[4];
                int color2;
                if (lIIllIlllI(lIlllIIlIIlIlIl.isToggled() ? 1 : 0)) {
                    color2 = lIlllIIlIIIllIl;
                    "".length();
                    if ("  ".length() == 0) {
                        return;
                    }
                }
                else {
                    color2 = ColorUtils.color(0.5f, 0.5f, 0.5f, 1.0f);
                }
                fontRenderer2.drawString(value2, n5, n6, color2);
                "".length();
            }
            ++lIlllIIlIIIIlII;
            "".length();
            if (((10 + 8 + 169 + 13 ^ 34 + 12 - 44 + 146) & (114 + 170 - 90 + 25 ^ 115 + 70 - 99 + 49 ^ -" ".length())) >= "  ".length()) {
                return;
            }
        }
    }
    
    private static boolean lIIlllIIII(final int lIlllIIIlIlIlll, final int lIlllIIIlIlIllI) {
        return lIlllIIIlIlIlll < lIlllIIIlIlIllI;
    }
}
