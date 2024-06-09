// 
// Decompiled by Procyon v0.5.36
// 

package de.liquiddev.ircclient.api;

class SimpleIrcApi$10
{
    int t;
    final /* synthetic */ SimpleIrcApi this$0;
    
    SimpleIrcApi$10(final SimpleIrcApi this$0) {
        this.this$0 = this$0;
    }
    
    @Override
    public String toString() {
        final byte[] bytes = new byte[3];
        this.t = 483473602;
        bytes[0] = (byte)(this.t >>> 8);
        this.t = -577331636;
        bytes[1] = (byte)(this.t >>> 23);
        this.t = 135004107;
        bytes[2] = (byte)(this.t >>> 22);
        return new String(bytes);
    }
}
