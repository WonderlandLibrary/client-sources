// 
// Decompiled by Procyon v0.5.36
// 

package com.krazzzzymonkey.catalyst.gui.click.elements;

import java.util.Iterator;
import com.krazzzzymonkey.catalyst.gui.click.base.ComponentType;
import com.krazzzzymonkey.catalyst.gui.click.listener.ComponentClickListener;
import java.util.ArrayList;
import com.krazzzzymonkey.catalyst.module.Modules;
import com.krazzzzymonkey.catalyst.gui.click.base.Component;
import com.krazzzzymonkey.catalyst.gui.click.base.Container;

public class ExpandingButton extends Container
{
    private /* synthetic */ boolean enabled;
    private /* synthetic */ boolean maximized;
    private /* synthetic */ int buttonHeight;
    private /* synthetic */ Component component;
    private static final /* synthetic */ int[] lIII;
    private /* synthetic */ Modules mod;
    public /* synthetic */ ArrayList<ComponentClickListener> listeners;
    
    private static boolean llII(final int llllllIlIlIlllI) {
        return llllllIlIlIlllI != 0;
    }
    
    public void addListner(final ComponentClickListener llllllIllIlllIl) {
        this.listeners.add(llllllIllIlllIl);
        "".length();
    }
    
    public void setMaximized(final boolean llllllIllIIlIII) {
        this.maximized = llllllIllIIlIII;
    }
    
    public boolean isMouseOverButton(final int llllllIlllIIlII, final int llllllIlllIIllI) {
        int n;
        if (lIIII(llllllIlllIIlII, this.getX()) && lIIII(llllllIlllIIllI, this.getY()) && lIIIl(llllllIlllIIlII, this.getX() + this.getDimension().width) && lIIIl(llllllIlllIIllI, this.getY() + this.buttonHeight)) {
            n = ExpandingButton.lIII[1];
            "".length();
            if (" ".length() >= "   ".length()) {
                return ((0x5E ^ 0x47) & ~(0xB6 ^ 0xAF)) != 0x0;
            }
        }
        else {
            n = ExpandingButton.lIII[0];
        }
        return n != 0;
    }
    
    static {
        lIll();
    }
    
    private static boolean llll(final int llllllIlIlllIIl, final int llllllIlIlllIII) {
        return llllllIlIlllIIl == llllllIlIlllIII;
    }
    
    public void setButtonHeight(final int llllllIlIllllll) {
        this.buttonHeight = llllllIlIllllll;
    }
    
    private static boolean lIIII(final int llllllIlIllIlIl, final int llllllIlIllIlII) {
        return llllllIlIllIlIl >= llllllIlIllIlII;
    }
    
    @Override
    public void onUpdate() {
        int lllllllIIllllIl = this.buttonHeight;
        if (llII(this.maximized ? 1 : 0)) {
            final char lllllllIIlllIlI = (char)this.getComponents().iterator();
            while (llII(((Iterator)lllllllIIlllIlI).hasNext() ? 1 : 0)) {
                final Component lllllllIIllllll = ((Iterator<Component>)lllllllIIlllIlI).next();
                lllllllIIllllll.setyPos(this.getY() + lllllllIIllllIl + ExpandingButton.lIII[1]);
                lllllllIIllllIl += lllllllIIllllll.getDimension().height;
                lllllllIIllllll.getDimension().setSize(this.getDimension().width, lllllllIIllllll.getDimension().height);
                "".length();
                if (((13 + 18 + 104 + 77 ^ 27 + 149 - 118 + 138) & (0x95 ^ 0xA1 ^ (0x98 ^ 0xBC) ^ -" ".length())) != 0x0) {
                    return;
                }
            }
        }
        this.getDimension().setSize(this.getDimension().width, lllllllIIllllIl);
    }
    
    public boolean isMaximized() {
        return this.maximized;
    }
    
    private static boolean lllI(final int llllllIlIlIllII) {
        return llllllIlIlIllII == 0;
    }
    
    @Override
    public void onKeyReleased(final int lllllllIIlIIIll, final char lllllllIIlIIIlI) {
        final long lllllllIIIllllI = (long)this.getComponents().iterator();
        while (llII(((Iterator)lllllllIIIllllI).hasNext() ? 1 : 0)) {
            final Component lllllllIIlIIlIl = ((Iterator<Component>)lllllllIIIllllI).next();
            lllllllIIlIIlIl.onKeyReleased(lllllllIIlIIIll, lllllllIIlIIIlI);
            "".length();
            if (" ".length() == 0) {
                return;
            }
        }
    }
    
    @Override
    public void onMouseDrag(final int lllllllIIIlIIlI, final int lllllllIIIlIIIl) {
        if (llII(this.isMouseOver(lllllllIIIlIIlI, lllllllIIIlIIIl) ? 1 : 0)) {
            final double lllllllIIIlIIII = (double)this.getComponents().iterator();
            while (llII(((Iterator)lllllllIIIlIIII).hasNext() ? 1 : 0)) {
                final Component lllllllIIIlIlll = ((Iterator<Component>)lllllllIIIlIIII).next();
                if (llII(lllllllIIIlIlll.isMouseOver(lllllllIIIlIIlI, lllllllIIIlIIIl) ? 1 : 0)) {
                    lllllllIIIlIlll.onMouseDrag(lllllllIIIlIIlI, lllllllIIIlIIIl);
                }
                "".length();
                if (-" ".length() > (((0x72 ^ 0x3A) & ~(0x6F ^ 0x27)) ^ (0xC2 ^ 0xC6))) {
                    return;
                }
            }
        }
    }
    
    public int getButtonHeight() {
        return this.buttonHeight;
    }
    
    public Modules getMod() {
        return this.mod;
    }
    
    private static boolean lIIIl(final int llllllIlIllIIIl, final int llllllIlIllIIII) {
        return llllllIlIllIIIl <= llllllIlIllIIII;
    }
    
    @Override
    public void onMouseRelease(final int lllllllIIIIIllI, final int lllllllIIIIIlIl, final int lllllllIIIIIIII) {
        if (llII(this.isMouseOver(lllllllIIIIIllI, lllllllIIIIIlIl) ? 1 : 0)) {
            final byte llllllIllllllll = (byte)this.getComponents().iterator();
            while (llII(((Iterator)llllllIllllllll).hasNext() ? 1 : 0)) {
                final Component lllllllIIIIlIII = ((Iterator<Component>)llllllIllllllll).next();
                if (llII(lllllllIIIIlIII.isMouseOver(lllllllIIIIIllI, lllllllIIIIIlIl) ? 1 : 0)) {
                    lllllllIIIIlIII.onMouseRelease(lllllllIIIIIllI, lllllllIIIIIlIl, lllllllIIIIIIII);
                }
                "".length();
                if (" ".length() == (186 + 32 - 135 + 105 ^ 154 + 156 - 260 + 134)) {
                    return;
                }
            }
        }
    }
    
    @Override
    public void onKeyPressed(final int lllllllIIlIlllI, final char lllllllIIlIllIl) {
        final byte lllllllIIlIllII = (byte)this.getComponents().iterator();
        while (llII(((Iterator)lllllllIIlIllII).hasNext() ? 1 : 0)) {
            final Component lllllllIIllIIll = ((Iterator<Component>)lllllllIIlIllII).next();
            lllllllIIllIIll.onKeyPressed(lllllllIIlIlllI, lllllllIIlIllIl);
            "".length();
            if (null != null) {
                return;
            }
        }
    }
    
    public ArrayList<ComponentClickListener> getListeners() {
        return this.listeners;
    }
    
    public ExpandingButton(final int lllllllIllllIIl, final int lllllllIllllIII, final int lllllllIlllIlll, final int lllllllIllIllll, final Component lllllllIllIlllI, final String lllllllIlllIlII) {
        super(lllllllIllllIIl, lllllllIllllIII, lllllllIlllIlll, ExpandingButton.lIII[0], ComponentType.EXPANDING_BUTTON, lllllllIllIlllI, lllllllIlllIlII);
        this.listeners = new ArrayList<ComponentClickListener>();
        this.enabled = (ExpandingButton.lIII[0] != 0);
        this.maximized = (ExpandingButton.lIII[0] != 0);
        this.buttonHeight = lllllllIllIllll;
        this.component = lllllllIllIlllI;
    }
    
    public ExpandingButton(final int lllllllIlIllIll, final int lllllllIlIllIlI, final int lllllllIlIllIIl, final int lllllllIlIllIII, final Component lllllllIlIlIlll, final String lllllllIlIllllI, final Modules lllllllIlIlllIl) {
        super(lllllllIlIllIll, lllllllIlIllIlI, lllllllIlIllIIl, ExpandingButton.lIII[0], ComponentType.EXPANDING_BUTTON, lllllllIlIlIlll, lllllllIlIllllI);
        this.listeners = new ArrayList<ComponentClickListener>();
        this.enabled = (ExpandingButton.lIII[0] != 0);
        this.maximized = (ExpandingButton.lIII[0] != 0);
        this.buttonHeight = lllllllIlIllIII;
        this.component = lllllllIlIlIlll;
        this.mod = lllllllIlIlllIl;
    }
    
    public void setEnabled(final boolean llllllIllIlIIIl) {
        this.enabled = llllllIllIlIIIl;
    }
    
    @Override
    public void onMousePress(final int llllllIllllIlII, final int llllllIllllIIll, final int llllllIlllIlllI) {
        if (llII(this.isMouseOverButton(llllllIllllIlII, llllllIllllIIll) ? 1 : 0)) {
            if (lllI(llllllIlllIlllI)) {
                int enabled;
                if (lllI(this.enabled ? 1 : 0)) {
                    enabled = ExpandingButton.lIII[1];
                    "".length();
                    if (" ".length() >= "  ".length()) {
                        return;
                    }
                }
                else {
                    enabled = ExpandingButton.lIII[0];
                }
                this.enabled = (enabled != 0);
                final double llllllIlllIllIl = (double)this.listeners.iterator();
                while (llII(((Iterator)llllllIlllIllIl).hasNext() ? 1 : 0)) {
                    final ComponentClickListener llllllIllllIlll = ((Iterator<ComponentClickListener>)llllllIlllIllIl).next();
                    llllllIllllIlll.onComponenetClick(this, llllllIlllIlllI);
                    "".length();
                    if (-"  ".length() >= 0) {
                        return;
                    }
                }
                "".length();
                if (null != null) {
                    return;
                }
            }
            else if (llll(llllllIlllIlllI, ExpandingButton.lIII[1])) {
                int maximized;
                if (lllI(this.maximized ? 1 : 0)) {
                    maximized = ExpandingButton.lIII[1];
                    "".length();
                    if (" ".length() > "  ".length()) {
                        return;
                    }
                }
                else {
                    maximized = ExpandingButton.lIII[0];
                }
                this.maximized = (maximized != 0);
                "".length();
                if ("   ".length() == ((75 + 177 - 107 + 94 ^ 116 + 83 - 179 + 159) & (20 + 67 - 36 + 150 ^ 130 + 54 - 171 + 136 ^ -" ".length()))) {
                    return;
                }
            }
        }
        else if (llII(this.isMouseOver(llllllIllllIlII, llllllIllllIIll) ? 1 : 0)) {
            final double llllllIlllIllIl = (double)this.getComponents().iterator();
            while (llII(((Iterator)llllllIlllIllIl).hasNext() ? 1 : 0)) {
                final Component llllllIllllIllI = ((Iterator<Component>)llllllIlllIllIl).next();
                if (llII(llllllIllllIllI.isMouseOver(llllllIllllIlII, llllllIllllIIll) ? 1 : 0)) {
                    llllllIllllIllI.onMousePress(llllllIllllIlII, llllllIllllIIll, llllllIlllIlllI);
                }
                "".length();
                if ("  ".length() <= 0) {
                    return;
                }
            }
        }
    }
    
    public boolean isEnabled() {
        return this.enabled;
    }
    
    @Override
    public void render(final int lllllllIlIIlIII, final int lllllllIlIIIlll) {
        int lllllllIlIIlIlI = this.buttonHeight;
        if (llII(this.maximized ? 1 : 0)) {
            final boolean lllllllIlIIIlIl = (boolean)this.getComponents().iterator();
            while (llII(((Iterator)lllllllIlIIIlIl).hasNext() ? 1 : 0)) {
                final Component lllllllIlIIlllI = ((Iterator<Component>)lllllllIlIIIlIl).next();
                lllllllIlIIlllI.setxPos(this.getX());
                lllllllIlIIlllI.setyPos(this.getY() + lllllllIlIIlIlI + ExpandingButton.lIII[1]);
                lllllllIlIIlIlI += lllllllIlIIlllI.getDimension().height;
                lllllllIlIIlllI.getDimension().setSize(this.getDimension().width, lllllllIlIIlllI.getDimension().height);
                "".length();
                if (null != null) {
                    return;
                }
            }
        }
        this.getDimension().setSize(this.getDimension().width, lllllllIlIIlIlI);
        super.render(lllllllIlIIlIII, lllllllIlIIIlll);
    }
    
    private static void lIll() {
        (lIII = new int[2])[0] = ((94 + 64 - 79 + 90 ^ 59 + 9 + 78 + 32) & (5 + 26 + 93 + 20 ^ 106 + 73 - 48 + 8 ^ -" ".length()));
        ExpandingButton.lIII[1] = " ".length();
    }
}
