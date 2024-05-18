// 
// Decompiled by Procyon v0.5.36
// 

package com.krazzzzymonkey.catalyst.gui.click.theme.dark;

import com.krazzzzymonkey.catalyst.utils.visual.RenderUtils;
import com.krazzzzymonkey.catalyst.utils.visual.ColorUtils;
import com.krazzzzymonkey.catalyst.module.modules.gui.ClickGui;
import com.krazzzzymonkey.catalyst.gui.click.elements.Slider;
import java.util.Base64;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import com.krazzzzymonkey.catalyst.gui.click.base.ComponentType;
import com.krazzzzymonkey.catalyst.gui.click.theme.Theme;
import com.krazzzzymonkey.catalyst.gui.click.base.Component;
import com.krazzzzymonkey.catalyst.gui.click.base.ComponentRenderer;

public class DarkSlider extends ComponentRenderer
{
    private static final /* synthetic */ String[] lIllII;
    private static final /* synthetic */ int[] lIllIl;
    
    static {
        lIllIIlI();
        lIllIIIl();
    }
    
    @Override
    public void doInteractions(final Component lllIIllIllIIlII, final int lllIIllIllIIIll, final int lllIIllIllIIIlI) {
    }
    
    private static boolean lIllIIll(final int lllIIllIlIlIIll) {
        return lllIIllIlIlIIll != 0;
    }
    
    public DarkSlider(final Theme lllIIllIlllllII) {
        super(ComponentType.SLIDER, lllIIllIlllllII);
    }
    
    private static void lIllIIIl() {
        (lIllII = new String[DarkSlider.lIllIl[3]])[DarkSlider.lIllIl[1]] = lIllIIII("DOjbI9VjPYE=", "lgnCg");
        DarkSlider.lIllII[DarkSlider.lIllIl[4]] = lIllIIII("VxL+ep6GVpk=", "RHIPy");
    }
    
    private static void lIllIIlI() {
        (lIllIl = new int[7])[0] = (0x3D ^ 0x2) + (0x40 ^ 0x2F) - (0x40 ^ 0x61) + (0xF9 ^ 0x8B);
        DarkSlider.lIllIl[1] = ((((0x4D ^ 0x55) & ~(0x82 ^ 0x9A)) ^ (0xA2 ^ 0x9F)) & (91 + 55 - 10 + 2 ^ 124 + 115 - 127 + 71 ^ -" ".length()));
        DarkSlider.lIllIl[2] = (0x1A ^ 0x25 ^ (0xAE ^ 0x95));
        DarkSlider.lIllIl[3] = "  ".length();
        DarkSlider.lIllIl[4] = " ".length();
        DarkSlider.lIllIl[5] = "   ".length();
        DarkSlider.lIllIl[6] = (0xB0 ^ 0xB6);
    }
    
    private static String lIllIIII(final String lllIIllIlIllIII, final String lllIIllIlIllIIl) {
        try {
            final SecretKeySpec lllIIllIlIlllIl = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(lllIIllIlIllIIl.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            final Cipher lllIIllIlIlllII = Cipher.getInstance("Blowfish");
            lllIIllIlIlllII.init(DarkSlider.lIllIl[3], lllIIllIlIlllIl);
            return new String(lllIIllIlIlllII.doFinal(Base64.getDecoder().decode(lllIIllIlIllIII.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lllIIllIlIllIll) {
            lllIIllIlIllIll.printStackTrace();
            return null;
        }
    }
    
    @Override
    public void drawComponent(final Component lllIIllIllIlIlI, final int lllIIllIlllIIIl, final int lllIIllIlllIIII) {
        final Slider lllIIllIllIllll = (Slider)lllIIllIllIlIlI;
        final int lllIIllIllIlllI = (int)(lllIIllIllIllll.getDimension().getWidth() * lllIIllIllIllll.getPercent());
        int n;
        if (lIllIIll(ClickGui.isLight ? 1 : 0)) {
            n = ColorUtils.color(DarkSlider.lIllIl[0], DarkSlider.lIllIl[0], DarkSlider.lIllIl[0], DarkSlider.lIllIl[0]);
            "".length();
            if ((0x7A ^ 0x7E) < 0) {
                return;
            }
        }
        else {
            n = ColorUtils.color(DarkSlider.lIllIl[1], DarkSlider.lIllIl[1], DarkSlider.lIllIl[1], DarkSlider.lIllIl[0]);
        }
        final int lllIIllIllIllIl = n;
        int n2;
        if (lIllIIll(ClickGui.isLight ? 1 : 0)) {
            n2 = ColorUtils.color(DarkSlider.lIllIl[1], DarkSlider.lIllIl[1], DarkSlider.lIllIl[1], DarkSlider.lIllIl[0]);
            "".length();
            if ((0x62 ^ 0x9 ^ (0xAE ^ 0xC1)) < "   ".length()) {
                return;
            }
        }
        else {
            n2 = ColorUtils.color(DarkSlider.lIllIl[0], DarkSlider.lIllIl[0], DarkSlider.lIllIl[0], DarkSlider.lIllIl[0]);
        }
        final int lllIIllIllIllII = n2;
        this.theme.fontRenderer.drawString(lllIIllIllIllll.getText(), lllIIllIllIllll.getX() + DarkSlider.lIllIl[2], lllIIllIllIllll.getY() + DarkSlider.lIllIl[3], ColorUtils.color(0.5f, 0.5f, 0.5f, 1.0f));
        "".length();
        this.theme.fontRenderer.drawString(String.valueOf(new StringBuilder().append(lllIIllIllIllll.getValue()).append(DarkSlider.lIllII[DarkSlider.lIllIl[1]])), lllIIllIllIllll.getX() + lllIIllIllIllll.getDimension().width - this.theme.fontRenderer.getStringWidth(String.valueOf(new StringBuilder().append(lllIIllIllIllll.getValue()).append(DarkSlider.lIllII[DarkSlider.lIllIl[4]]))) - DarkSlider.lIllIl[3], lllIIllIllIllll.getY() + DarkSlider.lIllIl[3], lllIIllIllIllII);
        "".length();
        RenderUtils.drawRect((float)lllIIllIllIllll.getX(), (float)(lllIIllIllIllll.getY() + lllIIllIllIllll.getDimension().height / DarkSlider.lIllIl[3] + DarkSlider.lIllIl[5]), (float)(lllIIllIllIllll.getX() + lllIIllIllIlllI + DarkSlider.lIllIl[5]), (float)(lllIIllIllIllll.getY() + lllIIllIllIllll.getDimension().height / DarkSlider.lIllIl[3] + DarkSlider.lIllIl[6]), lllIIllIllIllII);
        RenderUtils.drawRect((float)lllIIllIllIllll.getX(), (float)(lllIIllIllIllll.getY() + lllIIllIllIllll.getDimension().height / DarkSlider.lIllIl[3] + DarkSlider.lIllIl[5]), (float)(lllIIllIllIllll.getX() + lllIIllIllIlllI), (float)(lllIIllIllIllll.getY() + lllIIllIllIllll.getDimension().height / DarkSlider.lIllIl[3] + DarkSlider.lIllIl[6]), ClickGui.getColor());
    }
}
