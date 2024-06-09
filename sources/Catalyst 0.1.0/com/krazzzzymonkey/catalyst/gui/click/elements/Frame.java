// 
// Decompiled by Procyon v0.5.36
// 

package com.krazzzzymonkey.catalyst.gui.click.elements;

import java.util.Iterator;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import com.krazzzzymonkey.catalyst.gui.click.base.ComponentType;
import com.krazzzzymonkey.catalyst.gui.click.ClickGui;
import com.krazzzzymonkey.catalyst.gui.click.base.ComponentRenderer;
import com.krazzzzymonkey.catalyst.gui.click.base.Component;
import com.krazzzzymonkey.catalyst.gui.click.base.Container;

public class Frame extends Container
{
    private /* synthetic */ boolean maximizible;
    private /* synthetic */ int scrollAmmount;
    private /* synthetic */ boolean visable;
    private /* synthetic */ boolean pinnable;
    private /* synthetic */ boolean maximized;
    private /* synthetic */ int ticksSinceScroll;
    private /* synthetic */ boolean pinned;
    private static final /* synthetic */ int[] lIIIIII;
    
    public void setTicksSinceScroll(final int lllIIIIlllIIIIl) {
        this.ticksSinceScroll = lllIIIIlllIIIIl;
    }
    
    @Override
    public void onKeyReleased(final int lllIIIlIllIIIIl, final char lllIIIlIllIIIII) {
        final char lllIIIlIlIlllll = (char)this.getComponents().iterator();
        while (llIlIllI(((Iterator)lllIIIlIlIlllll).hasNext() ? 1 : 0)) {
            final Component lllIIIlIllIIllI = ((Iterator<Component>)lllIIIlIlIlllll).next();
            lllIIIlIllIIllI.onKeyReleased(lllIIIlIllIIIIl, lllIIIlIllIIIII);
            "".length();
            if (-(0xB9 ^ 0xBD) > 0) {
                return;
            }
        }
    }
    
    private static boolean llIlllll(final int lllIIIIlIlIllll) {
        return lllIIIIlIlIllll > 0;
    }
    
    private static void llIlIlIl() {
        (lIIIIII = new int[5])[0] = " ".length();
        Frame.lIIIIII[1] = (0x5A ^ 0x75 ^ (0x24 ^ 0x6F));
        Frame.lIIIIII[2] = ((2 + 26 - 14 + 168 ^ 148 + 147 - 288 + 177) & (0x17 ^ 0x31 ^ (0x5F ^ 0x77) ^ -" ".length()));
        Frame.lIIIIII[3] = (0xFFFFEC7B & 0x1F95);
        Frame.lIIIIII[4] = "  ".length();
    }
    
    static {
        llIlIlIl();
    }
    
    private static int llIlIlll(final double n, final double n2) {
        return dcmpg(n, n2);
    }
    
    public void setScrollAmmount(final int lllIIIIllIIllIl) {
        this.scrollAmmount = lllIIIIllIIllIl;
    }
    
    public boolean isMaximizible() {
        return this.maximizible;
    }
    
    public void setMaximized(final boolean lllIIIlIIlIIIII) {
        this.maximized = lllIIIlIIlIIIII;
    }
    
    public int getMaxY() {
        return (int)(this.getY() + this.getDimension().getHeight());
    }
    
    public void scrollFrame(final int lllIIIlIlIIllll) {
        this.scrollAmmount += lllIIIlIlIIllll;
        this.ticksSinceScroll = Frame.lIIIIII[2];
    }
    
    private static int llIlllII(final double n, final double n2) {
        return dcmpg(n, n2);
    }
    
    @Override
    public void onKeyPressed(final int lllIIIlIllIllll, final char lllIIIlIllIlllI) {
        final long lllIIIlIllIllIl = (long)this.getComponents().iterator();
        while (llIlIllI(((Iterator)lllIIIlIllIllIl).hasNext() ? 1 : 0)) {
            final Component lllIIIlIlllIlII = ((Iterator<Component>)lllIIIlIllIllIl).next();
            lllIIIlIlllIlII.onKeyPressed(lllIIIlIllIllll, lllIIIlIllIlllI);
            "".length();
            if ((0x71 ^ 0x75) <= "   ".length()) {
                return;
            }
        }
    }
    
    public boolean isPinned() {
        return this.pinned;
    }
    
    @Override
    public void onMousePress(final int lllIIIllIIlllIl, final int lllIIIllIlIIIII, final int lllIIIllIIllIll) {
        if (llIlIllI(this.isMouseOverBar(lllIIIllIIlllIl, lllIIIllIlIIIII) ? 1 : 0)) {
            ClickGui.getTheme().getRenderer().get(this.getComponentType()).doInteractions(this, lllIIIllIIlllIl, lllIIIllIlIIIII);
        }
        if (llIllIII(lllIIIllIIlllIl, this.getX()) && llIllIII(lllIIIllIlIIIII, this.getY() + this.getFrameBoxHeight()) && llIllIIl(llIlIlll(lllIIIllIIlllIl, this.getX() + this.getDimension().getWidth())) && llIllIIl(llIlIlll(lllIIIllIlIIIII, this.getY() + this.getDimension().getHeight()))) {
            final double lllIIIllIIllIlI = (double)this.getComponents().iterator();
            while (llIlIllI(((Iterator)lllIIIllIIllIlI).hasNext() ? 1 : 0)) {
                final Component lllIIIllIlIIIll = ((Iterator<Component>)lllIIIllIIllIlI).next();
                if (llIlIllI(lllIIIllIlIIIll.isMouseOver(lllIIIllIIlllIl, lllIIIllIlIIIII) ? 1 : 0) && llIlIllI(this.maximized ? 1 : 0)) {
                    lllIIIllIlIIIll.onMousePress(lllIIIllIIlllIl, lllIIIllIlIIIII, lllIIIllIIllIll);
                    ClickGui.getTheme().getRenderer().get(this.getComponentType()).doInteractions(this, lllIIIllIIlllIl, lllIIIllIlIIIII);
                }
                "".length();
                if (-(0x7 ^ 0x2) >= 0) {
                    return;
                }
            }
        }
    }
    
    public int getTicksSinceScroll() {
        return this.ticksSinceScroll;
    }
    
    private static boolean llIlIllI(final int lllIIIIlIllIlIl) {
        return lllIIIIlIllIlIl != 0;
    }
    
    public void updateComponents() {
        this.ticksSinceScroll += Frame.lIIIIII[0];
        if (llIllllI(this.scrollAmmount, this.getMaxScroll())) {
            this.scrollAmmount = this.getMaxScroll();
        }
        if (llIlllll(this.scrollAmmount)) {
            this.scrollAmmount = Frame.lIIIIII[2];
        }
        final String lllIIIlIlIIIIIl = (String)this.getComponents().iterator();
        while (llIlIllI(((Iterator)lllIIIlIlIIIIIl).hasNext() ? 1 : 0)) {
            final Component lllIIIlIlIIIlII = ((Iterator<Component>)lllIIIlIlIIIIIl).next();
            lllIIIlIlIIIlII.onUpdate();
            if (llIlIllI((lllIIIlIlIIIlII instanceof Container) ? 1 : 0)) {
                final Container lllIIIlIlIIIlll = (Container)lllIIIlIlIIIlII;
                final String lllIIIlIIlllllI = (String)lllIIIlIlIIIlll.getComponents().iterator();
                while (llIlIllI(((Iterator)lllIIIlIIlllllI).hasNext() ? 1 : 0)) {
                    final Component lllIIIlIlIIlIII = ((Iterator<Component>)lllIIIlIIlllllI).next();
                    lllIIIlIlIIlIII.onUpdate();
                    "".length();
                    if (-" ".length() == (0x25 ^ 0x32 ^ (0xB3 ^ 0xA0))) {
                        return;
                    }
                }
            }
            int lllIIIlIlIIIlIl = this.getY() + this.getFrameBoxHeight();
            final String lllIIIlIIlllllI = (String)this.getComponents().iterator();
            while (llIlIllI(((Iterator)lllIIIlIIlllllI).hasNext() ? 1 : 0)) {
                final Component lllIIIlIlIIIllI = ((Iterator<Component>)lllIIIlIIlllllI).next();
                if (llIllllI(this.getComponents().indexOf(lllIIIlIlIIIllI), this.getComponents().indexOf(lllIIIlIlIIIlII))) {
                    lllIIIlIlIIIlIl += (int)lllIIIlIlIIIllI.getDimension().getHeight();
                }
                "".length();
                if (((0xA3 ^ 0xBC) & ~(0xBB ^ 0xA4)) != 0x0) {
                    return;
                }
            }
            lllIIIlIlIIIlII.setyBase(lllIIIlIlIIIlIl);
            lllIIIlIlIIIlII.setyPos(lllIIIlIlIIIlII.getyBase() + this.scrollAmmount);
            "".length();
            if ("  ".length() <= ((204 + 195 - 267 + 109 ^ 115 + 122 - 208 + 131) & (104 + 233 - 310 + 208 ^ 54 + 102 - 132 + 162 ^ -" ".length()))) {
                return;
            }
        }
    }
    
    public int getScrollAmmount() {
        return this.scrollAmmount;
    }
    
    public void setPinned(final boolean lllIIIlIIlIlIIl) {
        this.pinned = lllIIIlIIlIlIIl;
    }
    
    public int getMaxScroll() {
        if (lllIIIII(this.getComponents().size())) {
            return Frame.lIIIIII[2];
        }
        final Component lllIIIlIIlllIII = this.getComponents().get(this.getComponents().size() - Frame.lIIIIII[0]);
        final int lllIIIlIIllIlll = (int)(lllIIIlIIlllIII.getyBase() + lllIIIlIIlllIII.getDimension().getHeight());
        return this.getMaxY() - lllIIIlIIllIlll;
    }
    
    private static boolean lllIIIII(final int lllIIIIlIllIIll) {
        return lllIIIIlIllIIll == 0;
    }
    
    private static boolean llIlllIl(final int lllIIIIlIlllIII, final int lllIIIIlIllIlll) {
        return lllIIIIlIlllIII <= lllIIIIlIllIlll;
    }
    
    private static boolean llIllIIl(final int lllIIIIlIllIIIl) {
        return lllIIIIlIllIIIl <= 0;
    }
    
    public void setPinnable(final boolean lllIIIIllllIlII) {
        this.pinnable = lllIIIIllllIlII;
    }
    
    public boolean isVisable() {
        return this.visable;
    }
    
    public Frame(final int lllIIIllIllllII, final int lllIIIllIlllIll, final int lllIIIllIlllIlI, final int lllIIIllIllllll, final String lllIIIllIlllIII) {
        super(lllIIIllIllllII, lllIIIllIlllIll, lllIIIllIlllIlI, lllIIIllIllllll, ComponentType.FRAME, null, lllIIIllIlllIII);
        this.maximizible = (Frame.lIIIIII[0] != 0);
        this.visable = (Frame.lIIIIII[0] != 0);
        this.pinnable = (Frame.lIIIIII[0] != 0);
        this.ticksSinceScroll = Frame.lIIIIII[1];
        this.scrollAmmount = Frame.lIIIIII[2];
    }
    
    public boolean isMouseOverBar(final int lllIIIlIlIlIllI, final int lllIIIlIlIlIlIl) {
        int n;
        if (llIllIII(lllIIIlIlIlIllI, this.getX()) && llIllIII(lllIIIlIlIlIlIl, this.getY()) && llIllIIl(llIlllII(lllIIIlIlIlIllI, this.getX() + this.getDimension().getWidth())) && llIlllIl(lllIIIlIlIlIlIl, this.getY() + this.getFrameBoxHeight())) {
            n = Frame.lIIIIII[0];
            "".length();
            if (((0x5D ^ 0x1E) & ~(0x19 ^ 0x5A)) <= -" ".length()) {
                return ((0x2E ^ 0x4C) & ~(0x27 ^ 0x45)) != 0x0;
            }
        }
        else {
            n = Frame.lIIIIII[2];
        }
        return n != 0;
    }
    
    public void setMaximizible(final boolean lllIIIlIIIlIlII) {
        this.maximizible = lllIIIlIIIlIlII;
    }
    
    @Override
    public void onMouseRelease(final int lllIIIllIIlIIII, final int lllIIIllIIIlIll, final int lllIIIllIIIlIlI) {
        if (llIllIII(lllIIIllIIlIIII, this.getX()) && llIllIII(lllIIIllIIIlIll, this.getY() + this.getFrameBoxHeight()) && llIllIIl(llIllIlI(lllIIIllIIlIIII, this.getX() + this.getDimension().getWidth())) && llIllIIl(llIllIlI(lllIIIllIIIlIll, this.getY() + this.getDimension().getHeight()))) {
            final char lllIIIllIIIlIIl = (char)this.getComponents().iterator();
            while (llIlIllI(((Iterator)lllIIIllIIIlIIl).hasNext() ? 1 : 0)) {
                final Component lllIIIllIIlIIlI = ((Iterator<Component>)lllIIIllIIIlIIl).next();
                if (llIlIllI(lllIIIllIIlIIlI.isMouseOver(lllIIIllIIlIIII, lllIIIllIIIlIll) ? 1 : 0) && llIlIllI(this.maximized ? 1 : 0)) {
                    lllIIIllIIlIIlI.onMouseRelease(lllIIIllIIlIIII, lllIIIllIIIlIll, lllIIIllIIIlIlI);
                }
                "".length();
                if (-(0x9D ^ 0x99) >= 0) {
                    return;
                }
            }
        }
    }
    
    private static int llIllIlI(final double n, final double n2) {
        return dcmpg(n, n2);
    }
    
    public void setVisable(final boolean lllIIIlIIIIlIIl) {
        this.visable = lllIIIlIIIIlIIl;
    }
    
    @Override
    public void onMouseDrag(final int lllIIIllIIIIIII, final int lllIIIlIlllllII) {
        if (llIlIllI(this.isMouseOverBar(lllIIIllIIIIIII, lllIIIlIlllllII) ? 1 : 0)) {
            ClickGui.getTheme().getRenderer().get(this.getComponentType()).doInteractions(this, lllIIIllIIIIIII, lllIIIlIlllllII);
        }
        if (llIllIII(lllIIIllIIIIIII, this.getX()) && llIllIII(lllIIIlIlllllII, this.getY() + this.getFrameBoxHeight()) && llIllIIl(llIllIll(lllIIIllIIIIIII, this.getX() + this.getDimension().getWidth())) && llIllIIl(llIllIll(lllIIIlIlllllII, this.getY() + this.getDimension().getHeight()))) {
            final short lllIIIlIllllIll = (short)this.getComponents().iterator();
            while (llIlIllI(((Iterator)lllIIIlIllllIll).hasNext() ? 1 : 0)) {
                final Component lllIIIllIIIIIlI = ((Iterator<Component>)lllIIIlIllllIll).next();
                if (llIlIllI(lllIIIllIIIIIlI.isMouseOver(lllIIIllIIIIIII, lllIIIlIlllllII) ? 1 : 0) && llIlIllI(this.maximized ? 1 : 0)) {
                    lllIIIllIIIIIlI.onMouseDrag(lllIIIllIIIIIII, lllIIIlIlllllII);
                    ClickGui.getTheme().getRenderer().get(this.getComponentType()).doInteractions(this, lllIIIllIIIIIII, lllIIIlIlllllII);
                }
                "".length();
                if (((0x60 ^ 0x3) & ~(0x38 ^ 0x5B)) != 0x0) {
                    return;
                }
            }
        }
    }
    
    public int getFrameBoxHeight() {
        return ClickGui.getTheme().getFrameHeight();
    }
    
    private static int llIllIll(final double n, final double n2) {
        return dcmpg(n, n2);
    }
    
    public boolean isMaximized() {
        return this.maximized;
    }
    
    public boolean isPinnable() {
        return this.pinnable;
    }
    
    private static boolean llIllllI(final int lllIIIIllIIIIII, final int lllIIIIlIllllll) {
        return lllIIIIllIIIIII < lllIIIIlIllllll;
    }
    
    private static boolean llIllIII(final int lllIIIIllIIIlll, final int lllIIIIllIIIlIl) {
        return lllIIIIllIIIlll >= lllIIIIllIIIlIl;
    }
    
    @Override
    public void renderChildren(final int lllIIIllIlIllIl, final int lllIIIllIlIllll) {
        if (llIlIllI(this.isMaximized() ? 1 : 0)) {
            GL11.glEnable(Frame.lIIIIII[3]);
            GL11.glScissor(this.getX() * Frame.lIIIIII[4], Display.getHeight() - (this.getY() + this.getDimension().height) * Frame.lIIIIII[4], this.getDimension().width * Frame.lIIIIII[4], (this.getDimension().height - this.getFrameBoxHeight()) * Frame.lIIIIII[4]);
            final double lllIIIllIlIlIll = (double)this.getComponents().iterator();
            while (llIlIllI(((Iterator)lllIIIllIlIlIll).hasNext() ? 1 : 0)) {
                final Component lllIIIllIllIIlI = ((Iterator<Component>)lllIIIllIlIlIll).next();
                lllIIIllIllIIlI.render(lllIIIllIlIllIl, lllIIIllIlIllll);
                "".length();
                if ("   ".length() < 0) {
                    return;
                }
            }
            GL11.glDisable(Frame.lIIIIII[3]);
        }
    }
}
