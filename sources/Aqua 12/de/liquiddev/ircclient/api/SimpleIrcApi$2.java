// 
// Decompiled by Procyon v0.5.36
// 

package de.liquiddev.ircclient.api;

class SimpleIrcApi$2
{
    int t;
    
    @Override
    public String toString() {
        final byte[] bytes = new byte[2];
        this.t = 483473602;
        bytes[0] = (byte)(this.t >>> 8);
        this.t = 764845644;
        bytes[1] = (byte)(this.t >>> 23);
        return new String(bytes);
    }
}
