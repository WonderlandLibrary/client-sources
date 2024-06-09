// 
// Decompiled by Procyon v0.5.36
// 

package com.krazzzzymonkey.catalyst.xray;

public class XRayData
{
    private /* synthetic */ int id;
    private /* synthetic */ int blue;
    private /* synthetic */ int red;
    private /* synthetic */ int green;
    private /* synthetic */ int meta;
    
    public int getId() {
        return this.id;
    }
    
    public void setBlue(final int lllIlIIIlllIIII) {
        this.blue = lllIlIIIlllIIII;
    }
    
    public void setRed(final int lllIlIIlIIIIIlI) {
        this.red = lllIlIIlIIIIIlI;
    }
    
    public void setGreen(final int lllIlIIIlllIlll) {
        this.green = lllIlIIIlllIlll;
    }
    
    public XRayData(final int lllIlIIlIIlllll, final int lllIlIIlIIllIII, final int lllIlIIlIIlllIl, final int lllIlIIlIIlIllI, final int lllIlIIlIIllIll) {
        this.id = lllIlIIlIIlllll;
        this.meta = lllIlIIlIIllIII;
        this.red = lllIlIIlIIlllIl;
        this.green = lllIlIIlIIlIllI;
        this.blue = lllIlIIlIIllIll;
    }
    
    public int getBlue() {
        return this.blue;
    }
    
    public void setId(final int lllIlIIlIIIlIll) {
        this.id = lllIlIIlIIIlIll;
    }
    
    public int getGreen() {
        return this.green;
    }
    
    public int getRed() {
        return this.red;
    }
    
    public int getMeta() {
        return this.meta;
    }
}
