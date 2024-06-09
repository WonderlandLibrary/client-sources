/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  com.google.common.util.concurrent.ListenableFuture
 */
package net.minecraft.network;

import com.google.common.util.concurrent.ListenableFuture;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.ThreadQuickExitException;
import net.minecraft.util.IThreadListener;

public class PacketThreadUtil {
    private static final String __OBFID = "CL_00002306";

    public static void func_180031_a(Packet p_180031_0_, final INetHandler p_180031_1_, IThreadListener p_180031_2_) {
        if (!p_180031_2_.isCallingFromMinecraftThread()) {
            p_180031_2_.addScheduledTask(new Runnable(){
                private static final String __OBFID = "CL_00002305";

                @Override
                public void run() {
                    Packet.this.processPacket(p_180031_1_);
                }
            });
            throw ThreadQuickExitException.field_179886_a;
        }
    }

}

