// 
// Decompiled by Procyon v0.5.36
// 

package com.krazzzzymonkey.catalyst.gui.click;

import java.util.Iterator;
import com.krazzzzymonkey.catalyst.gui.click.base.Container;
import com.krazzzzymonkey.catalyst.gui.click.elements.Slider;
import com.krazzzzymonkey.catalyst.gui.click.base.Component;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.Minecraft;
import com.krazzzzymonkey.catalyst.gui.click.theme.Theme;
import javax.vecmath.Vector2f;
import com.krazzzzymonkey.catalyst.gui.click.elements.Frame;
import java.util.ArrayList;

public class ClickGui extends ClickGuiScreen
{
    private static /* synthetic */ ArrayList<Frame> frames;
    private /* synthetic */ boolean dragging;
    private /* synthetic */ Vector2f draggingOffset;
    private static final /* synthetic */ int[] lIlllll;
    private static /* synthetic */ Theme theme;
    private /* synthetic */ Frame currentFrame;
    
    public void onMouseScroll(final int llIllIIlllIlllI) {
        final float llIllIIlllIllII = (float)ClickGui.frames.iterator();
        while (lIIlIlIll(((Iterator)llIllIIlllIllII).hasNext() ? 1 : 0)) {
            final Frame llIllIIllllIIII = ((Iterator<Frame>)llIllIIlllIllII).next();
            if (lIIlIlIll(llIllIIllllIIII.isMouseOver(ClickGui.mouse[ClickGui.lIlllll[0]], ClickGui.mouse[ClickGui.lIlllll[1]]) ? 1 : 0)) {
                llIllIIllllIIII.scrollFrame(llIllIIlllIlllI * ClickGui.lIlllll[2]);
            }
            llIllIIllllIIII.onMouseScroll(llIllIIlllIlllI * ClickGui.lIlllll[2]);
            "".length();
            if (null != null) {
                return;
            }
        }
    }
    
    public void setTheme(final Theme llIllIlIIIllIlI) {
        ClickGui.theme = llIllIlIIIllIlI;
    }
    
    private static boolean lIIlIllII(final Object llIllIIlIlIIlll) {
        return llIllIIlIlIIlll != null;
    }
    
    public void onMouseRelease(final int llIllIIlllIIIII, final int llIllIIlllIIIlI) {
        final byte llIllIIllIllllI = (byte)ClickGui.frames.iterator();
        while (lIIlIlIll(((Iterator)llIllIIllIllllI).hasNext() ? 1 : 0)) {
            final Frame llIllIIlllIIlIl = ((Iterator<Frame>)llIllIIllIllllI).next();
            if (lIIlIlIll(llIllIIlllIIlIl.isMouseOver(llIllIIlllIIIII, llIllIIlllIIIlI) ? 1 : 0)) {
                this.currentFrame = llIllIIlllIIlIl;
                if (lIIlIlIll(llIllIIlllIIlIl.isMouseOverBar(llIllIIlllIIIII, llIllIIlllIIIlI) ? 1 : 0)) {
                    this.dragging = (ClickGui.lIlllll[0] != 0);
                }
                llIllIIlllIIlIl.onMouseRelease(llIllIIlllIIIII, llIllIIlllIIIlI, ClickGui.lIlllll[0]);
            }
            "".length();
            if (((0xEA ^ 0xB5) & ~(0x43 ^ 0x1C)) != 0x0) {
                return;
            }
        }
    }
    
    static {
        lIIlIlIlI();
        ClickGui.frames = new ArrayList<Frame>();
    }
    
    private static void lIIlIlIlI() {
        (lIlllll = new int[3])[0] = ((0x96 ^ 0x87) & ~(0x20 ^ 0x31));
        ClickGui.lIlllll[1] = " ".length();
        ClickGui.lIlllll[2] = (0xBD ^ 0xB9);
    }
    
    public ArrayList<Frame> getFrames() {
        return ClickGui.frames;
    }
    
    public void onUpdate() {
        final Exception llIllIIllIIIlll = (Exception)ClickGui.frames.iterator();
        while (lIIlIlIll(((Iterator)llIllIIllIIIlll).hasNext() ? 1 : 0)) {
            final Frame llIllIIllIIlIIl = ((Iterator<Frame>)llIllIIllIIIlll).next();
            llIllIIllIIlIIl.updateComponents();
            "".length();
            if ("   ".length() <= "  ".length()) {
                return;
            }
        }
    }
    
    public void onKeyRelease(final int llIllIIlIlllIII, final char llIllIIlIllIlll) {
        final boolean llIllIIlIllIllI = (boolean)ClickGui.frames.iterator();
        while (lIIlIlIll(((Iterator)llIllIIlIllIllI).hasNext() ? 1 : 0)) {
            final Frame llIllIIlIllllII = ((Iterator<Frame>)llIllIIlIllIllI).next();
            llIllIIlIllllII.onKeyReleased(llIllIIlIlllIII, llIllIIlIllIlll);
            "".length();
            if (((0x1A ^ 0x45 ^ (0x8B ^ 0x8C)) & (56 + 30 - 1 + 148 ^ 97 + 163 - 256 + 173 ^ -" ".length()) & (((0xAB ^ 0xBB ^ (0x9D ^ 0xBA)) & (0x21 ^ 0x30 ^ (0x79 ^ 0x5F) ^ -" ".length())) ^ -" ".length())) == "   ".length()) {
                return;
            }
        }
    }
    
    public void onMouseClick(final int llIllIIllIlIlII, final int llIllIIllIIllll, final int llIllIIllIlIIlI) {
        final Exception llIllIIllIIllIl = (Exception)ClickGui.frames.iterator();
        while (lIIlIlIll(((Iterator)llIllIIllIIllIl).hasNext() ? 1 : 0)) {
            final Frame llIllIIllIlIllI = ((Iterator<Frame>)llIllIIllIIllIl).next();
            if (lIIlIlIll(llIllIIllIlIllI.isMouseOver(llIllIIllIlIlII, llIllIIllIIllll) ? 1 : 0)) {
                this.currentFrame = llIllIIllIlIllI;
                if (lIIlIlIll(llIllIIllIlIllI.isMouseOverBar(llIllIIllIlIlII, llIllIIllIIllll) ? 1 : 0)) {
                    this.dragging = (ClickGui.lIlllll[1] != 0);
                    this.draggingOffset = new Vector2f((float)(llIllIIllIlIlII - llIllIIllIlIllI.getX()), (float)(llIllIIllIIllll - llIllIIllIlIllI.getY()));
                }
                llIllIIllIlIllI.onMousePress(llIllIIllIlIlII, llIllIIllIIllll, llIllIIllIlIIlI);
            }
            "".length();
            if ((0x43 ^ 0x15 ^ (0x44 ^ 0x16)) < ((0x36 ^ 0x16 ^ (0x2F ^ 0x43)) & (0x72 ^ 0x5 ^ (0x14 ^ 0x2F) ^ -" ".length()))) {
                return;
            }
        }
    }
    
    public void render() {
        final double llIllIlIIIlIlII = (double)ClickGui.frames.iterator();
        while (lIIlIlIll(((Iterator)llIllIlIIIlIlII).hasNext() ? 1 : 0)) {
            final Frame llIllIlIIIlIllI = ((Iterator<Frame>)llIllIlIIIlIlII).next();
            llIllIlIIIlIllI.render(ClickGui.mouse[ClickGui.lIlllll[0]], ClickGui.mouse[ClickGui.lIlllll[1]]);
            "".length();
            if ((0x64 ^ 0x60) != (0x87 ^ 0x83)) {
                return;
            }
        }
    }
    
    public static void renderPinned() {
        final Minecraft llIllIlIIlIIlII = Minecraft.getMinecraft();
        final ScaledResolution llIllIlIIlIIIll = new ScaledResolution(llIllIlIIlIIlII);
        final float llIllIlIIlIIIlI = llIllIlIIlIIIll.getScaleFactor() / (float)Math.pow(llIllIlIIlIIIll.getScaleFactor(), 2.0);
        GlStateManager.pushMatrix();
        GlStateManager.translate(0.0f, 0.0f, 1000.0f);
        GlStateManager.scale(llIllIlIIlIIIlI * 2.0f, llIllIlIIlIIIlI * 2.0f, llIllIlIIlIIIlI * 2.0f);
        final String llIllIlIIIllllI = (String)ClickGui.frames.iterator();
        while (lIIlIlIll(((Iterator)llIllIlIIIllllI).hasNext() ? 1 : 0)) {
            final Frame llIllIlIIlIIlIl = ((Iterator<Frame>)llIllIlIIIllllI).next();
            if (lIIlIlIll(llIllIlIIlIIlIl.isPinned() ? 1 : 0)) {
                llIllIlIIlIIlIl.render(ClickGui.mouse[ClickGui.lIlllll[0]], ClickGui.mouse[ClickGui.lIlllll[1]]);
            }
            "".length();
            if (-" ".length() >= "   ".length()) {
                return;
            }
        }
        GlStateManager.popMatrix();
    }
    
    public static Theme getTheme() {
        return ClickGui.theme;
    }
    
    public void onkeyPressed(final int llIllIIlIlIlllI, final char llIllIIlIlIlIll) {
        final byte llIllIIlIlIlIlI = (byte)ClickGui.frames.iterator();
        while (lIIlIlIll(((Iterator)llIllIIlIlIlIlI).hasNext() ? 1 : 0)) {
            final Frame llIllIIlIllIIII = ((Iterator<Frame>)llIllIIlIlIlIlI).next();
            llIllIIlIllIIII.onKeyPressed(llIllIIlIlIlllI, llIllIIlIlIlIll);
            "".length();
            if ("  ".length() >= (0x49 ^ 0x27 ^ (0xFD ^ 0x97))) {
                return;
            }
        }
    }
    
    public void onMouseUpdate(final int llIllIIllllllII, final int llIllIIlllllIll) {
        final short llIllIIlllllIlI = (short)ClickGui.frames.iterator();
        while (lIIlIlIll(((Iterator)llIllIIlllllIlI).hasNext() ? 1 : 0)) {
            final Frame llIllIlIIIIIllI = ((Iterator<Frame>)llIllIIlllllIlI).next();
            final byte llIllIIlllllIII = (byte)llIllIlIIIIIllI.getComponents().iterator();
            while (lIIlIlIll(((Iterator)llIllIIlllllIII).hasNext() ? 1 : 0)) {
                final Component llIllIlIIIIIlll = ((Iterator<Component>)llIllIIlllllIII).next();
                if (lIIlIlIll(llIllIlIIIIIlll.isMouseOver(llIllIIllllllII, llIllIIlllllIll) ? 1 : 0)) {
                    llIllIlIIIIIlll.onMouseDrag(llIllIIllllllII, llIllIIlllllIll);
                    "".length();
                    if ("   ".length() < 0) {
                        return;
                    }
                }
                else if (lIIlIlIll((llIllIlIIIIIlll instanceof Slider) ? 1 : 0)) {
                    final Slider llIllIlIIIIlIII = (Slider)llIllIlIIIIIlll;
                    llIllIlIIIIlIII.dragging = (ClickGui.lIlllll[0] != 0);
                }
                "".length();
                if ((0x64 ^ 0x4C ^ (0x4D ^ 0x60)) <= 0) {
                    return;
                }
            }
            "".length();
            if (-"  ".length() >= 0) {
                return;
            }
        }
        if (lIIlIlIll(this.dragging ? 1 : 0) && lIIlIllII(this.currentFrame)) {
            final int llIllIlIIIIIIIl = (int)(llIllIIlllllIll - this.draggingOffset.getY() - this.currentFrame.getY());
            this.currentFrame.setxPos((int)(llIllIIllllllII - this.draggingOffset.getX()));
            this.currentFrame.setyPos((int)(llIllIIlllllIll - this.draggingOffset.getY()));
            final byte llIllIIlllllIIl = (byte)this.currentFrame.getComponents().iterator();
            while (lIIlIlIll(((Iterator)llIllIIlllllIIl).hasNext() ? 1 : 0)) {
                final Component llIllIlIIIIIIlI = ((Iterator<Component>)llIllIIlllllIIl).next();
                llIllIlIIIIIIlI.setyBase(llIllIlIIIIIIlI.getyBase() + llIllIlIIIIIIIl);
                if (lIIlIlIll((llIllIlIIIIIIlI instanceof Container) ? 1 : 0)) {
                    final Container llIllIlIIIIIlII = (Container)llIllIlIIIIIIlI;
                    int llIllIlIIIIIIll = ClickGui.lIlllll[0];
                    final String llIllIIllllIlIl = (String)llIllIlIIIIIlII.getComponents().iterator();
                    while (lIIlIlIll(((Iterator)llIllIIllllIlIl).hasNext() ? 1 : 0)) {
                        final Component llIllIlIIIIIlIl = ((Iterator<Component>)llIllIIllllIlIl).next();
                        llIllIlIIIIIlIl.setxPos(llIllIlIIIIIIlI.getX());
                        llIllIlIIIIIlIl.setyPos(llIllIlIIIIIIlI.getY());
                        llIllIlIIIIIIll += llIllIlIIIIIlIl.getDimension().height;
                        "".length();
                        if ("  ".length() < 0) {
                            return;
                        }
                    }
                }
                "".length();
                if ("  ".length() >= (118 + 105 - 197 + 128 ^ 122 + 89 - 160 + 107)) {
                    return;
                }
            }
        }
    }
    
    public ClickGui() {
        this.dragging = (ClickGui.lIlllll[0] != 0);
    }
    
    private static boolean lIIlIlIll(final int llIllIIlIlIIlIl) {
        return llIllIIlIlIIlIl != 0;
    }
    
    public void addFrame(final Frame llIllIIllIIIIlI) {
        ClickGui.frames.add(llIllIIllIIIIlI);
        "".length();
    }
}
