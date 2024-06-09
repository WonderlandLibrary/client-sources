// 
// Decompiled by Procyon v0.5.36
// 

package de.liquiddev.ircclient.api;

class SimpleIrcApi$15
{
    int t;
    final /* synthetic */ SimpleIrcApi this$0;
    
    SimpleIrcApi$15(final SimpleIrcApi this$0) {
        this.this$0 = this$0;
    }
    
    @Override
    public String toString() {
        final byte[] bytes = new byte[3];
        this.t = 473489109;
        bytes[0] = (byte)(this.t >>> 16);
        this.t = 2025578817;
        bytes[1] = (byte)(this.t >>> 17);
        this.t = -1152875823;
        bytes[2] = (byte)(this.t >>> 10);
        return new String(bytes);
    }
}
