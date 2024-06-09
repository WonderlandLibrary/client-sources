// 
// Decompiled by Procyon v0.5.36
// 

package de.liquiddev.ircclient.api;

class SimpleIrcApi$16
{
    int t;
    final /* synthetic */ SimpleIrcApi this$0;
    
    SimpleIrcApi$16(final SimpleIrcApi this$0) {
        this.this$0 = this$0;
    }
    
    @Override
    public String toString() {
        final byte[] bytes = new byte[2];
        this.t = 473423573;
        bytes[0] = (byte)(this.t >>> 16);
        this.t = 2018632001;
        bytes[1] = (byte)(this.t >>> 17);
        return new String(bytes);
    }
}
