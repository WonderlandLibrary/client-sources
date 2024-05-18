// 
// Decompiled by Procyon v0.5.36
// 

package com.krazzzzymonkey.catalyst.gui.click.base;

import java.awt.Dimension;

public class Interactable
{
    private /* synthetic */ int xPos;
    private /* synthetic */ int yBase;
    private static final /* synthetic */ int[] lIlIlIII;
    private /* synthetic */ Dimension dimension;
    private /* synthetic */ int yPos;
    
    public int getX() {
        return this.xPos;
    }
    
    public int getyBase() {
        return this.yBase;
    }
    
    public void setDimension(final Dimension llIIIlIllIIlIlI) {
        this.dimension = llIIIlIllIIlIlI;
    }
    
    public void onMouseRelease(final int llIIIllIIlIIIIl, final int llIIIllIIlIIIII, final int llIIIllIIIlllll) {
    }
    
    public Dimension getDimension() {
        return this.dimension;
    }
    
    private static boolean lIIIIIlllI(final int llIIIlIlIlllIIl, final int llIIIlIlIlllIII) {
        return llIIIlIlIlllIIl <= llIIIlIlIlllIII;
    }
    
    private static void lIIIIIlIlI() {
        (lIlIlIII = new int[2])[0] = " ".length();
        Interactable.lIlIlIII[1] = ((0x35 ^ 0x14) & ~(0xA5 ^ 0x84));
    }
    
    public boolean isMouseOver(final int llIIIllIIIlIIIl, final int llIIIllIIIlIlII) {
        int n;
        if (lIIIIIllIl(llIIIllIIIlIIIl, this.xPos) && lIIIIIllIl(llIIIllIIIlIlII, this.yPos) && lIIIIIlllI(llIIIllIIIlIIIl, this.xPos + this.dimension.width) && lIIIIIlllI(llIIIllIIIlIlII, this.yPos + this.dimension.height)) {
            n = Interactable.lIlIlIII[0];
            "".length();
            if (-"  ".length() >= 0) {
                return ((0xE9 ^ 0x91 ^ (0x6B ^ 0x43)) & (0xCB ^ 0x9F ^ (0x4F ^ 0x4B) ^ -" ".length())) != 0x0;
            }
        }
        else {
            n = Interactable.lIlIlIII[1];
        }
        return n != 0;
    }
    
    public void onMouseDrag(final int llIIIllIIIlllIl, final int llIIIllIIIlllII) {
    }
    
    public void onMouseScroll(final int llIIIllIIIllIlI) {
    }
    
    static {
        lIIIIIlIlI();
    }
    
    public int getY() {
        return this.yPos;
    }
    
    private static boolean lIIIIIllIl(final int llIIIlIllIIIIII, final int llIIIlIlIllllll) {
        return llIIIlIllIIIIII >= llIIIlIlIllllll;
    }
    
    public void onMousePress(final int llIIIllIIlIIlIl, final int llIIIllIIlIIlII, final int llIIIllIIlIIIll) {
    }
    
    public void setxPos(final int llIIIlIllllIlIl) {
        this.xPos = llIIIlIllllIlIl;
    }
    
    public void onKeyReleased(final int llIIIllIIIIlIII, final char llIIIllIIIIIlll) {
    }
    
    public void setyPos(final int llIIIlIlllIlIll) {
        this.yPos = llIIIlIlllIlIll;
    }
    
    public void onKeyPressed(final int llIIIllIIIIllII, final char llIIIllIIIIlIll) {
    }
    
    public Interactable(final int llIIIllIIlIllll, final int llIIIllIIlIlIIl, final int llIIIllIIlIlIII, final int llIIIllIIlIllII) {
        this.xPos = llIIIllIIlIllll;
        this.yPos = llIIIllIIlIlIIl;
        this.dimension = new Dimension(llIIIllIIlIlIII, llIIIllIIlIllII);
    }
    
    public void setyBase(final int llIIIlIllIllIIl) {
        this.yBase = llIIIlIllIllIIl;
    }
}
