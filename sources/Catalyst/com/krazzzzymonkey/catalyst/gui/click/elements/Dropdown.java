// 
// Decompiled by Procyon v0.5.36
// 

package com.krazzzzymonkey.catalyst.gui.click.elements;

import java.util.Iterator;
import com.krazzzzymonkey.catalyst.gui.click.base.ComponentType;
import com.krazzzzymonkey.catalyst.gui.click.base.Component;
import com.krazzzzymonkey.catalyst.gui.click.base.Container;

public class Dropdown extends Container
{
    private /* synthetic */ int dropdownHeight;
    private /* synthetic */ boolean maximized;
    private static final /* synthetic */ int[] lIlIlIlI;
    
    @Override
    public void onMouseRelease(final int llIIIlIIIlIIIlI, final int llIIIlIIIlIIIIl, final int llIIIlIIIIlllII) {
        if (lIIIIlIIlI(this.isMouseOver(llIIIlIIIlIIIlI, llIIIlIIIlIIIIl) ? 1 : 0)) {
            final Exception llIIIlIIIIllIll = (Exception)this.getComponents().iterator();
            while (lIIIIlIIlI(((Iterator)llIIIlIIIIllIll).hasNext() ? 1 : 0)) {
                final Component llIIIlIIIlIIlII = ((Iterator<Component>)llIIIlIIIIllIll).next();
                if (lIIIIlIIlI(llIIIlIIIlIIlII.isMouseOver(llIIIlIIIlIIIlI, llIIIlIIIlIIIIl) ? 1 : 0)) {
                    llIIIlIIIlIIlII.onMouseRelease(llIIIlIIIlIIIlI, llIIIlIIIlIIIIl, llIIIlIIIIlllII);
                }
                "".length();
                if ("  ".length() == 0) {
                    return;
                }
            }
        }
    }
    
    public void setMaximized(final boolean llIIIlIIIIIIIlI) {
        this.maximized = llIIIlIIIIIIIlI;
    }
    
    private static boolean lIIIIlIIlI(final int llIIIIllllIlIIl) {
        return llIIIIllllIlIIl != 0;
    }
    
    private static void lIIIIlIIIl() {
        (lIlIlIlI = new int[2])[0] = ((0x31 ^ 0x63) & ~(0x13 ^ 0x41));
        Dropdown.lIlIlIlI[1] = " ".length();
    }
    
    public boolean isMaximized() {
        return this.maximized;
    }
    
    @Override
    public void render(final int llIIIlIIlIIllII, final int llIIIlIIlIIIlll) {
        int llIIIlIIlIIlIlI = this.dropdownHeight;
        if (lIIIIlIIlI(this.maximized ? 1 : 0)) {
            final float llIIIlIIlIIIlIl = (float)this.getComponents().iterator();
            while (lIIIIlIIlI(((Iterator)llIIIlIIlIIIlIl).hasNext() ? 1 : 0)) {
                final Component llIIIlIIlIIlllI = ((Iterator<Component>)llIIIlIIlIIIlIl).next();
                llIIIlIIlIIlllI.setxPos(this.getX());
                llIIIlIIlIIlllI.setyPos(this.getY() + llIIIlIIlIIlIlI + Dropdown.lIlIlIlI[1]);
                llIIIlIIlIIlIlI += llIIIlIIlIIlllI.getDimension().height;
                llIIIlIIlIIlllI.getDimension().setSize(this.getDimension().width, llIIIlIIlIIlllI.getDimension().height);
                "".length();
                if ("  ".length() == ((0x83 ^ 0xB3) & ~(0x17 ^ 0x27))) {
                    return;
                }
            }
        }
        this.getDimension().setSize(this.getDimension().width, llIIIlIIlIIlIlI);
        super.render(llIIIlIIlIIllII, llIIIlIIlIIIlll);
    }
    
    public Dropdown(final int llIIIlIIlIllIlI, final int llIIIlIIllIIIII, final int llIIIlIIlIllIII, final int llIIIlIIlIllllI, final Component llIIIlIIlIlIllI, final String llIIIlIIlIlIlIl) {
        super(llIIIlIIlIllIlI, llIIIlIIllIIIII, llIIIlIIlIllIII, Dropdown.lIlIlIlI[0], ComponentType.DROPDOWN, llIIIlIIlIlIllI, llIIIlIIlIlIlIl);
        this.maximized = (Dropdown.lIlIlIlI[0] != 0);
        this.dropdownHeight = llIIIlIIlIllllI;
    }
    
    @Override
    public void onMousePress(final int llIIIlIIIIlIIIl, final int llIIIlIIIIIllII, final int llIIIlIIIIIllll) {
        if (lIIIIlIIll(llIIIlIIIIlIIIl, this.getX()) && lIIIIlIIll(llIIIlIIIIIllII, this.getY()) && lIIIIlIlII(llIIIlIIIIlIIIl, this.getX() + this.getDimension().width) && lIIIIlIlII(llIIIlIIIIIllII, this.getY() + this.dropdownHeight)) {
            if (lIIIIlIlIl(llIIIlIIIIIllll, Dropdown.lIlIlIlI[1])) {
                int maximized;
                if (lIIIIlIllI(this.maximized ? 1 : 0)) {
                    maximized = Dropdown.lIlIlIlI[1];
                    "".length();
                    if (" ".length() == "   ".length()) {
                        return;
                    }
                }
                else {
                    maximized = Dropdown.lIlIlIlI[0];
                }
                this.maximized = (maximized != 0);
                "".length();
                if (-"   ".length() > 0) {
                    return;
                }
            }
        }
        else if (lIIIIlIIlI(this.isMouseOver(llIIIlIIIIlIIIl, llIIIlIIIIIllII) ? 1 : 0)) {
            final int llIIIlIIIIIlIlI = (int)this.getComponents().iterator();
            while (lIIIIlIIlI(((Iterator)llIIIlIIIIIlIlI).hasNext() ? 1 : 0)) {
                final Component llIIIlIIIIlIIll = ((Iterator<Component>)llIIIlIIIIIlIlI).next();
                if (lIIIIlIIlI(llIIIlIIIIlIIll.isMouseOver(llIIIlIIIIlIIIl, llIIIlIIIIIllII) ? 1 : 0)) {
                    llIIIlIIIIlIIll.onMousePress(llIIIlIIIIlIIIl, llIIIlIIIIIllII, llIIIlIIIIIllll);
                }
                "".length();
                if ("  ".length() < ((0xF4 ^ 0x9C ^ (0x97 ^ 0xA9)) & (0xB7 ^ 0x8D ^ (0x75 ^ 0x19) ^ -" ".length()))) {
                    return;
                }
            }
        }
    }
    
    @Override
    public void onUpdate() {
        int llIIIlIIIllllIl = this.dropdownHeight;
        if (lIIIIlIIlI(this.maximized ? 1 : 0)) {
            final boolean llIIIlIIIlllIlI = (boolean)this.getComponents().iterator();
            while (lIIIIlIIlI(((Iterator)llIIIlIIIlllIlI).hasNext() ? 1 : 0)) {
                final Component llIIIlIIIllllll = ((Iterator<Component>)llIIIlIIIlllIlI).next();
                llIIIlIIIllllll.setyPos(this.getY() + llIIIlIIIllllIl + Dropdown.lIlIlIlI[1]);
                llIIIlIIIllllIl += llIIIlIIIllllll.getDimension().height;
                llIIIlIIIllllll.getDimension().setSize(this.getDimension().width, llIIIlIIIllllll.getDimension().height);
                "".length();
                if (-(0x13 ^ 0x17) > 0) {
                    return;
                }
            }
        }
        this.getDimension().setSize(this.getDimension().width, llIIIlIIIllllIl);
    }
    
    private static boolean lIIIIlIlIl(final int llIIIIlllllIlII, final int llIIIIlllllIIll) {
        return llIIIIlllllIlII == llIIIIlllllIIll;
    }
    
    public int getDropdownHeight() {
        return this.dropdownHeight;
    }
    
    private static boolean lIIIIlIlII(final int llIIIIllllIllII, final int llIIIIllllIlIll) {
        return llIIIIllllIllII <= llIIIIllllIlIll;
    }
    
    private static boolean lIIIIlIllI(final int llIIIIllllIIlll) {
        return llIIIIllllIIlll == 0;
    }
    
    private static boolean lIIIIlIIll(final int llIIIIlllllIIII, final int llIIIIllllIllll) {
        return llIIIIlllllIIII >= llIIIIllllIllll;
    }
    
    public void setDropdownHeight(final int llIIIIlllllIlll) {
        this.dropdownHeight = llIIIIlllllIlll;
    }
    
    static {
        lIIIIlIIIl();
    }
    
    @Override
    public void onMouseDrag(final int llIIIlIIIllIIIl, final int llIIIlIIIllIIII) {
        if (lIIIIlIIlI(this.isMouseOver(llIIIlIIIllIIIl, llIIIlIIIllIIII) ? 1 : 0)) {
            final double llIIIlIIIlIllII = (double)this.getComponents().iterator();
            while (lIIIIlIIlI(((Iterator)llIIIlIIIlIllII).hasNext() ? 1 : 0)) {
                final Component llIIIlIIIllIIll = ((Iterator<Component>)llIIIlIIIlIllII).next();
                if (lIIIIlIIlI(llIIIlIIIllIIll.isMouseOver(llIIIlIIIllIIIl, llIIIlIIIllIIII) ? 1 : 0)) {
                    llIIIlIIIllIIll.onMouseDrag(llIIIlIIIllIIIl, llIIIlIIIllIIII);
                }
                "".length();
                if (null != null) {
                    return;
                }
            }
        }
    }
}
