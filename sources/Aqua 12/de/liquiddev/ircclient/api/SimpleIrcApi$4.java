// 
// Decompiled by Procyon v0.5.36
// 

package de.liquiddev.ircclient.api;

class SimpleIrcApi$4
{
    int t;
    
    @Override
    public String toString() {
        final byte[] bytes = new byte[3];
        this.t = 483473602;
        bytes[0] = (byte)(this.t >>> 8);
        this.t = 781622860;
        bytes[1] = (byte)(this.t >>> 23);
        this.t = 135004107;
        bytes[2] = (byte)(this.t >>> 22);
        return new String(bytes);
    }
}
