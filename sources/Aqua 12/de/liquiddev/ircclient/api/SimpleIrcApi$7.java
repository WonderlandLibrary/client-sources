// 
// Decompiled by Procyon v0.5.36
// 

package de.liquiddev.ircclient.api;

class SimpleIrcApi$7
{
    int t;
    final /* synthetic */ SimpleIrcApi this$0;
    
    SimpleIrcApi$7(final SimpleIrcApi this$0) {
        this.this$0 = this$0;
    }
    
    @Override
    public String toString() {
        final byte[] bytes = new byte[2];
        this.t = 483473346;
        bytes[0] = (byte)(this.t >>> 8);
        this.t = 337026636;
        bytes[1] = (byte)(this.t >>> 23);
        return new String(bytes);
    }
}
