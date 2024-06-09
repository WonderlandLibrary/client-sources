// 
// Decompiled by Procyon v0.5.36
// 

package de.liquiddev.ircclient.api;

class IrcApiManager$3
{
    int t;
    final /* synthetic */ IrcApiManager this$0;
    
    IrcApiManager$3(final IrcApiManager this$0) {
        this.this$0 = this$0;
    }
    
    @Override
    public String toString() {
        final byte[] bytes = new byte[8];
        this.t = 473717384;
        bytes[0] = (byte)(this.t >>> 16);
        this.t = -912206484;
        bytes[1] = (byte)(this.t >>> 18);
        this.t = 1934552376;
        bytes[2] = (byte)(this.t >>> 5);
        this.t = 1482960021;
        bytes[3] = (byte)(this.t >>> 16);
        this.t = -712363504;
        bytes[4] = (byte)(this.t >>> 7);
        this.t = -216378678;
        bytes[5] = (byte)(this.t >>> 1);
        this.t = -1657317645;
        bytes[6] = (byte)(this.t >>> 15);
        this.t = -1443827926;
        bytes[7] = (byte)(this.t >>> 19);
        return new String(bytes);
    }
}
