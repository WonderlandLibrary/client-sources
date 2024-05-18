// 
// Decompiled by Procyon v0.5.36
// 

package com.krazzzzymonkey.catalyst.utils;

import java.util.function.Predicate;
import net.minecraft.network.play.server.SPacketTimeUpdate;
import java.util.Arrays;
import net.minecraft.util.math.MathHelper;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.event.PacketEvent;
import me.zero.alpine.listener.Listener;
import java.util.EventListener;

public class LagCompensator implements EventListener
{
    private /* synthetic */ int nextIndex;
    private static final /* synthetic */ int[] llIIllII;
    @EventHandler
    /* synthetic */ Listener<PacketEvent.Receive> packetEventListener;
    public static /* synthetic */ LagCompensator INSTANCE;
    private /* synthetic */ long timeLastTimeUpdate;
    private final /* synthetic */ float[] tickRates;
    
    private static int lIIllIlIlI(final long n, final long n2) {
        return lcmp(n, n2);
    }
    
    private static boolean lIIllIlIIl(final int lIlllIIllIIIIIl) {
        return lIlllIIllIIIIIl > 0;
    }
    
    private static int lIIllIIlll(final float n, final float n2) {
        return fcmpl(n, n2);
    }
    
    private static boolean lIIllIlIll(final int lIlllIIllIIIIll) {
        return lIlllIIllIIIIll != 0;
    }
    
    public void onTimeUpdate() {
        if (lIIllIlIll(lIIllIlIlI(this.timeLastTimeUpdate, -1L))) {
            final float lIlllIIllIIllll = (System.currentTimeMillis() - this.timeLastTimeUpdate) / 1000.0f;
            this.tickRates[this.nextIndex % this.tickRates.length] = MathHelper.clamp(20.0f / lIlllIIllIIllll, 0.0f, 20.0f);
            this.nextIndex += LagCompensator.llIIllII[2];
        }
        this.timeLastTimeUpdate = System.currentTimeMillis();
    }
    
    public void reset() {
        this.nextIndex = LagCompensator.llIIllII[1];
        this.timeLastTimeUpdate = -1L;
        Arrays.fill(this.tickRates, 0.0f);
    }
    
    public float getTickRate() {
        float lIlllIIllIllIlI = 0.0f;
        float lIlllIIllIllIIl = 0.0f;
        final String lIlllIIllIlIlIl = (Object)this.tickRates;
        final double lIlllIIllIlIlII = lIlllIIllIlIlIl.length;
        float lIlllIIllIlIIll = LagCompensator.llIIllII[1];
        while (lIIllIlIII((int)lIlllIIllIlIIll, (int)lIlllIIllIlIlII)) {
            final float lIlllIIllIlllII = lIlllIIllIlIlIl[lIlllIIllIlIIll];
            if (lIIllIlIIl(lIIllIIlll(lIlllIIllIlllII, 0.0f))) {
                lIlllIIllIllIIl += lIlllIIllIlllII;
                ++lIlllIIllIllIlI;
            }
            ++lIlllIIllIlIIll;
            "".length();
            if (-(0x3B ^ 0x3F) > 0) {
                return 0.0f;
            }
        }
        return MathHelper.clamp(lIlllIIllIllIIl / lIlllIIllIllIlI, 0.0f, 20.0f);
    }
    
    static {
        lIIllIIllI();
    }
    
    public LagCompensator() {
        this.tickRates = new float[LagCompensator.llIIllII[0]];
        this.nextIndex = LagCompensator.llIIllII[1];
        this.packetEventListener = new Listener<PacketEvent.Receive>(lIlllIIllIIlIIl -> {
            if (lIIllIlIll((lIlllIIllIIlIIl.getPacket() instanceof SPacketTimeUpdate) ? 1 : 0)) {
                LagCompensator.INSTANCE.onTimeUpdate();
            }
            return;
        }, (Predicate<PacketEvent.Receive>[])new Predicate[LagCompensator.llIIllII[1]]);
        this.reset();
    }
    
    private static boolean lIIllIlIII(final int lIlllIIllIIIllI, final int lIlllIIllIIIlIl) {
        return lIlllIIllIIIllI < lIlllIIllIIIlIl;
    }
    
    private static void lIIllIIllI() {
        (llIIllII = new int[3])[0] = (19 + 5 + 159 + 8 ^ 138 + 129 - 187 + 91);
        LagCompensator.llIIllII[1] = ((0x3C ^ 0x39) & ~(0x2D ^ 0x28));
        LagCompensator.llIIllII[2] = " ".length();
    }
}
