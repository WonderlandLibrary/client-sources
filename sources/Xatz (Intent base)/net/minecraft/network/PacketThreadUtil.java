package net.minecraft.network;

import me.xatzdevelopments.Xatz;
import me.xatzdevelopments.events.EventType;
import me.xatzdevelopments.events.listeners.EventReadPacket;
import net.minecraft.util.IThreadListener;

public class PacketThreadUtil
{
    private static final String __OBFID = "CL_00002306";

    public static void func_180031_a(final Packet p_180031_0_, final INetHandler p_180031_1_, IThreadListener p_180031_2_)
    {
        if (!p_180031_2_.isCallingFromMinecraftThread())
        {
            p_180031_2_.addScheduledTask(new Runnable()
            {
                private static final String __OBFID = "CL_00002305";
                public void run()
                {
                    final EventReadPacket e = new EventReadPacket(p_180031_0_);
                    e.setType(EventType.PRE);
                    Xatz.onEvent(e);
                    if (e.isCancelled()) {
                        return;
                    }
                    p_180031_0_.processPacket(p_180031_1_);
                    e.setType(EventType.POST);
                }
            });
            throw ThreadQuickExitException.field_179886_a;
        }
    }
}
