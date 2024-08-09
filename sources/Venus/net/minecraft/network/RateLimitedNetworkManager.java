/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network;

import io.netty.util.concurrent.Future;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.PacketDirection;
import net.minecraft.network.play.server.SDisconnectPacket;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RateLimitedNetworkManager
extends NetworkManager {
    private static final Logger field_244274_g = LogManager.getLogger();
    private static final ITextComponent field_244275_h = new TranslationTextComponent("disconnect.exceeded_packet_rate");
    private final int field_244276_i;

    public RateLimitedNetworkManager(int n) {
        super(PacketDirection.SERVERBOUND);
        this.field_244276_i = n;
    }

    @Override
    protected void func_241877_b() {
        super.func_241877_b();
        float f = this.getPacketsReceived();
        if (f > (float)this.field_244276_i) {
            field_244274_g.warn("Player exceeded rate-limit (sent {} packets per second)", (Object)Float.valueOf(f));
            this.sendPacket(new SDisconnectPacket(field_244275_h), this::lambda$func_241877_b$0);
            this.disableAutoRead();
        }
    }

    private void lambda$func_241877_b$0(Future future) throws Exception {
        this.closeChannel(field_244275_h);
    }
}

