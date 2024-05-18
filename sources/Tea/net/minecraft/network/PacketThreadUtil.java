package net.minecraft.network;

import com.darkmagician6.eventapi.EventManager;

import me.swezedcode.client.utils.events.EventReadPacket;
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
                	EventReadPacket readPacket;
                	EventManager.call(readPacket = new EventReadPacket(p_180031_0_, EventReadPacket.packetState.PRE));
                	
                	if(readPacket.isCancelled())
                		return;
                	p_180031_0_.processPacket(p_180031_1_);
                	EventManager.call(readPacket = new EventReadPacket(p_180031_0_, EventReadPacket.packetState.POST));
                }
            });
            throw ThreadQuickExitException.field_179886_a;
        }
    }
}
