// 
// Decompiled by Procyon v0.5.36
// 

package com.krazzzzymonkey.catalyst.gui.click.theme.dark;

import com.krazzzzymonkey.catalyst.utils.MathUtils;
import net.minecraft.client.gui.FontRenderer;
import java.awt.Color;
import com.krazzzzymonkey.catalyst.gui.click.base.ComponentType;
import com.krazzzzymonkey.catalyst.gui.click.theme.Theme;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Iterator;
import com.krazzzzymonkey.catalyst.utils.visual.RenderUtils;
import com.krazzzzymonkey.catalyst.utils.visual.ColorUtils;
import com.krazzzzymonkey.catalyst.module.modules.gui.ClickGui;
import java.awt.Dimension;
import com.krazzzzymonkey.catalyst.gui.click.elements.Frame;
import com.krazzzzymonkey.catalyst.gui.click.base.Component;
import com.krazzzzymonkey.catalyst.gui.click.base.ComponentRenderer;

public class DarkFrame extends ComponentRenderer
{
    private static final /* synthetic */ String[] llllI;
    private static final /* synthetic */ int[] lIIIII;
    
    @Override
    public void doInteractions(final Component lllIlIllIlIllll, final int lllIlIllIlIlllI, final int lllIlIllIlIllIl) {
        final Frame lllIlIllIlIllII = (Frame)lllIlIllIlIllll;
        final Dimension lllIlIllIlIlIll = lllIlIllIlIllII.getDimension();
        if (lllllll(lllIlIllIlIlllI, lllIlIllIlIllII.getX() + lllIlIllIlIlIll.width - DarkFrame.lIIIII[10]) && llllllI(lllIlIllIlIllII.isMaximizible() ? 1 : 0) && lllllll(lllIlIllIlIllIl, lllIlIllIlIllII.getY()) && lIIIIIII(lllIlIllIlIllIl, lllIlIllIlIllII.getY() + DarkFrame.lIIIII[10]) && lIIIIIII(lllIlIllIlIlllI, lllIlIllIlIllII.getX() + lllIlIllIlIlIll.width)) {
            final Frame frame = lllIlIllIlIllII;
            int maximized;
            if (lIIIIIll(lllIlIllIlIllII.isMaximized() ? 1 : 0)) {
                maximized = DarkFrame.lIIIII[3];
                "".length();
                if ((0x31 ^ 0x35) <= "   ".length()) {
                    return;
                }
            }
            else {
                maximized = DarkFrame.lIIIII[7];
            }
            frame.setMaximized((boolean)(maximized != 0));
        }
        if (lllllll(lllIlIllIlIlllI, lllIlIllIlIllII.getX() + lllIlIllIlIlIll.width - DarkFrame.lIIIII[11]) && lllllll(lllIlIllIlIllIl, lllIlIllIlIllII.getY()) && lIIIIIII(lllIlIllIlIllIl, lllIlIllIlIllII.getY() + DarkFrame.lIIIII[10]) && lIIIIIII(lllIlIllIlIlllI, lllIlIllIlIllII.getX() + lllIlIllIlIlIll.width - DarkFrame.lIIIII[10])) {
            final Frame frame2 = lllIlIllIlIllII;
            int pinned;
            if (lIIIIIll(lllIlIllIlIllII.isPinned() ? 1 : 0)) {
                pinned = DarkFrame.lIIIII[3];
                "".length();
                if (-" ".length() >= 0) {
                    return;
                }
            }
            else {
                pinned = DarkFrame.lIIIII[7];
            }
            frame2.setPinned((boolean)(pinned != 0));
        }
    }
    
    private static void llllIlI() {
        (llllI = new String[DarkFrame.lIIIII[12]])[DarkFrame.lIIIII[7]] = llllIII("QQ==", "lJumt");
        DarkFrame.llllI[DarkFrame.lIIIII[3]] = llllIII("YQ==", "JYflF");
    }
    
    private void isMaximized(final Frame lllIlIlllIIlIIl, final Dimension lllIlIlllIIlIII, final int lllIlIllIllllIl, final int lllIlIlllIIIllI) {
        int n;
        if (llllllI(ClickGui.isLight ? 1 : 0)) {
            n = ColorUtils.color(DarkFrame.lIIIII[5], DarkFrame.lIIIII[5], DarkFrame.lIIIII[5], DarkFrame.lIIIII[5]);
            "".length();
            if ((0x67 ^ 0x63) < "  ".length()) {
                return;
            }
        }
        else {
            n = ColorUtils.color(DarkFrame.lIIIII[7], DarkFrame.lIIIII[7], DarkFrame.lIIIII[7], DarkFrame.lIIIII[5]);
        }
        final int lllIlIlllIIIlIl = n;
        int n2;
        if (llllllI(ClickGui.isLight ? 1 : 0)) {
            n2 = ColorUtils.color(DarkFrame.lIIIII[7], DarkFrame.lIIIII[7], DarkFrame.lIIIII[7], DarkFrame.lIIIII[5]);
            "".length();
            if (((0x2A ^ 0x32 ^ (0x56 ^ 0x52)) & (72 + 88 + 16 + 43 ^ 0 + 124 + 74 + 1 ^ -" ".length())) >= (62 + 100 - 65 + 46 ^ 16 + 121 - 2 + 4)) {
                return;
            }
        }
        else {
            n2 = ColorUtils.color(DarkFrame.lIIIII[5], DarkFrame.lIIIII[5], DarkFrame.lIIIII[5], DarkFrame.lIIIII[5]);
        }
        final int lllIlIlllIIIlII = n2;
        final Iterator<Component> iterator = lllIlIlllIIlIIl.getComponents().iterator();
        while (llllllI(iterator.hasNext() ? 1 : 0)) {
            final Component lllIlIlllIIllII = iterator.next();
            lllIlIlllIIllII.setxPos(lllIlIlllIIlIIl.getX());
            "".length();
            if (-" ".length() >= (0xB0 ^ 0xB4)) {
                return;
            }
        }
        RenderUtils.drawRect((float)lllIlIlllIIlIIl.getX(), (float)(lllIlIlllIIlIIl.getY() + DarkFrame.lIIIII[3]), (float)(lllIlIlllIIlIIl.getX() + lllIlIlllIIlIII.width), (float)(lllIlIlllIIlIIl.getY() + lllIlIlllIIlIII.height), lllIlIlllIIIlIl);
        float lllIlIlllIIIIll = 5.0f;
        float lllIlIlllIIIIlI = 0.0f;
        lllIlIlllIIIIll = (float)(lllIlIlllIIlIII.height - DarkFrame.lIIIII[10]);
        final boolean lllIlIllIllIlll = (boolean)lllIlIlllIIlIIl.getComponents().iterator();
        while (llllllI(((Iterator)lllIlIllIllIlll).hasNext() ? 1 : 0)) {
            final Component lllIlIlllIIlIll = ((Iterator<Component>)lllIlIllIllIlll).next();
            lllIlIlllIIIIlI += lllIlIlllIIlIll.getDimension().height;
            "".length();
            if ((59 + 74 - 49 + 59 ^ 134 + 114 - 120 + 11) <= 0) {
                return;
            }
        }
        final float lllIlIlllIIIIIl = lllIlIlllIIIIll * (lllIlIlllIIIIll / lllIlIlllIIIIlI);
        double lllIlIlllIIIIII = (lllIlIlllIIlIIl.getDimension().getHeight() - 16.0 - lllIlIlllIIIIIl) * (lllIlIlllIIlIIl.getScrollAmmount() / (double)lllIlIlllIIlIIl.getMaxScroll());
        lllIlIlllIIIIII += lllIlIlllIIlIIl.getY() + DarkFrame.lIIIII[10];
        lllIlIlllIIlIIl.renderChildren(lllIlIllIllllIl, lllIlIlllIIIllI);
        if (lIIIIIlI(lIIIIIIl(lllIlIlllIIIIIl, lllIlIlllIIIIll))) {
            RenderUtils.drawRect((float)(int)(lllIlIlllIIlIIl.getX() + lllIlIlllIIlIII.getWidth() - 1.0), (float)(int)lllIlIlllIIIIII, (float)(int)(lllIlIlllIIlIIl.getX() + lllIlIlllIIlIIl.getDimension().getWidth()), (float)(int)(lllIlIlllIIIIII + lllIlIlllIIIIIl), ClickGui.getColor());
        }
    }
    
    private static boolean lllllll(final int lllIlIllIIIlIll, final int lllIlIllIIIlIlI) {
        return lllIlIllIIIlIll >= lllIlIllIIIlIlI;
    }
    
    private static boolean lIIIIlII(final int lllIlIllIIIIlll, final int lllIlIllIIIIllI) {
        return lllIlIllIIIIlll < lllIlIllIIIIllI;
    }
    
    private void isPinnable(final Frame lllIlIllllIllIl, final Dimension lllIlIllllIllII, final int lllIlIllllIlIll, final int lllIlIllllIlIlI) {
    }
    
    private static String llllIII(String lllIlIllIIlIllI, final String lllIlIllIIllIlI) {
        lllIlIllIIlIllI = new String(Base64.getDecoder().decode(lllIlIllIIlIllI.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        final StringBuilder lllIlIllIIllIIl = new StringBuilder();
        final char[] lllIlIllIIllIII = lllIlIllIIllIlI.toCharArray();
        int lllIlIllIIlIlll = DarkFrame.lIIIII[7];
        final int lllIlIllIIlIIIl = (Object)lllIlIllIIlIllI.toCharArray();
        final Exception lllIlIllIIlIIII = (Exception)lllIlIllIIlIIIl.length;
        double lllIlIllIIIllll = DarkFrame.lIIIII[7];
        while (lIIIIlII((int)lllIlIllIIIllll, (int)lllIlIllIIlIIII)) {
            final char lllIlIllIIlllII = lllIlIllIIlIIIl[lllIlIllIIIllll];
            lllIlIllIIllIIl.append((char)(lllIlIllIIlllII ^ lllIlIllIIllIII[lllIlIllIIlIlll % lllIlIllIIllIII.length]));
            "".length();
            ++lllIlIllIIlIlll;
            ++lllIlIllIIIllll;
            "".length();
            if (-(0x28 ^ 0x6A ^ (0xD0 ^ 0x96)) >= 0) {
                return null;
            }
        }
        return String.valueOf(lllIlIllIIllIIl);
    }
    
    private static int lIIIIIIl(final float n, final float n2) {
        return fcmpl(n, n2);
    }
    
    private static boolean lIIIIIll(final int lllIlIlIllllllI) {
        return lllIlIlIllllllI == 0;
    }
    
    private static boolean lIIIIIII(final int lllIlIllIIIIIll, final int lllIlIllIIIIIlI) {
        return lllIlIllIIIIIll <= lllIlIllIIIIIlI;
    }
    
    static {
        lllllIl();
        llllIlI();
    }
    
    private static void lllllIl() {
        (lIIIII = new int[13])[0] = (0xAA ^ 0x8A ^ (0x86 ^ 0xA9));
        DarkFrame.lIIIII[1] = (0xA2 ^ 0x93 ^ (0x53 ^ 0x66));
        DarkFrame.lIIIII[2] = (0xB1 ^ 0xBB);
        DarkFrame.lIIIII[3] = " ".length();
        DarkFrame.lIIIII[4] = (0x3F ^ 0x2C);
        DarkFrame.lIIIII[5] = 198 + 172 - 237 + 122;
        DarkFrame.lIIIII[6] = 14 + 97 + 21 + 23;
        DarkFrame.lIIIII[7] = ((0xCD ^ 0xAE) & ~(0xD3 ^ 0xB0));
        DarkFrame.lIIIII[8] = (0x5B ^ 0x57);
        DarkFrame.lIIIII[9] = "   ".length();
        DarkFrame.lIIIII[10] = (0xB7 ^ 0x9A ^ (0x53 ^ 0x6E));
        DarkFrame.lIIIII[11] = (0x46 ^ 0x4E ^ (0x8C ^ 0xA2));
        DarkFrame.lIIIII[12] = "  ".length();
    }
    
    private static boolean lIIIIIlI(final int lllIlIlIlllllII) {
        return lllIlIlIlllllII < 0;
    }
    
    private static boolean llllllI(final int lllIlIllIIIIIII) {
        return lllIlIllIIIIIII != 0;
    }
    
    public DarkFrame(final Theme lllIllIIIIIIIll) {
        super(ComponentType.FRAME, lllIllIIIIIIIll);
    }
    
    private void isMaximizible(final Frame lllIlIllllIIIIl, final Dimension lllIlIlllIllIlI, final int lllIlIlllIllIIl, final int lllIlIlllIllllI) {
        Color lllIlIlllIlllIl;
        if (lllllll(lllIlIlllIllIIl, lllIlIllllIIIIl.getX() + lllIlIlllIllIlI.width - DarkFrame.lIIIII[4]) && lllllll(lllIlIlllIllllI, lllIlIllllIIIIl.getY()) && lIIIIIII(lllIlIlllIllllI, lllIlIllllIIIIl.getY() + DarkFrame.lIIIII[4]) && lIIIIIII(lllIlIlllIllIIl, lllIlIllllIIIIl.getX() + lllIlIlllIllIlI.width)) {
            final Color lllIlIllllIIIll = new Color(DarkFrame.lIIIII[5], DarkFrame.lIIIII[5], DarkFrame.lIIIII[5], DarkFrame.lIIIII[5]);
            "".length();
            if ("   ".length() < " ".length()) {
                return;
            }
        }
        else {
            lllIlIlllIlllIl = new Color(DarkFrame.lIIIII[6], DarkFrame.lIIIII[6], DarkFrame.lIIIII[6], DarkFrame.lIIIII[5]);
        }
        final FontRenderer fontRenderer = this.theme.fontRenderer;
        String s;
        if (llllllI(lllIlIllllIIIIl.isMaximized() ? 1 : 0)) {
            s = DarkFrame.llllI[DarkFrame.lIIIII[7]];
            "".length();
            if (" ".length() < 0) {
                return;
            }
        }
        else {
            s = DarkFrame.llllI[DarkFrame.lIIIII[3]];
        }
        fontRenderer.drawStringWithShadow(s, (float)(lllIlIllllIIIIl.getX() + lllIlIlllIllIlI.width - DarkFrame.lIIIII[8]), (float)(lllIlIllllIIIIl.getY() + DarkFrame.lIIIII[9]), lllIlIlllIlllIl.getRGB());
        "".length();
    }
    
    @Override
    public void drawComponent(final Component lllIlIllllllIIl, final int lllIlIlllllIIlI, final int lllIlIlllllIlll) {
        final Frame lllIlIlllllIllI = (Frame)lllIlIllllllIIl;
        final Dimension lllIlIlllllIlIl = lllIlIlllllIllI.getDimension();
        if (llllllI(lllIlIlllllIllI.isMaximized() ? 1 : 0)) {
            this.isMaximized(lllIlIlllllIllI, lllIlIlllllIlIl, lllIlIlllllIIlI, lllIlIlllllIlll);
        }
        RenderUtils.drawRect((float)lllIlIlllllIllI.getX(), (float)lllIlIlllllIllI.getY(), (float)(lllIlIlllllIllI.getX() + lllIlIlllllIlIl.width), (float)(lllIlIlllllIllI.getY() + DarkFrame.lIIIII[0]), ClickGui.getColor());
        if (llllllI(lllIlIlllllIllI.isMaximizible() ? 1 : 0)) {
            this.isMaximizible(lllIlIlllllIllI, lllIlIlllllIlIl, lllIlIlllllIIlI, lllIlIlllllIlll);
        }
        this.theme.fontRenderer.drawStringWithShadow(lllIlIlllllIllI.getText(), (float)(lllIlIlllllIllI.getX() + DarkFrame.lIIIII[1]), (float)(MathUtils.getMiddle(lllIlIlllllIllI.getY(), lllIlIlllllIllI.getY() + DarkFrame.lIIIII[2]) - this.theme.fontRenderer.FONT_HEIGHT / DarkFrame.lIIIII[2] - DarkFrame.lIIIII[3]), ColorUtils.color(1.0f, 1.0f, 1.0f, 1.0f));
        "".length();
    }
}
