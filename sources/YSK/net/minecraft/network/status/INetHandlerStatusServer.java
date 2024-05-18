package net.minecraft.network.status;

import net.minecraft.network.*;
import net.minecraft.network.status.client.*;

public interface INetHandlerStatusServer extends INetHandler
{
    void processServerQuery(final C00PacketServerQuery p0);
    
    void processPing(final C01PacketPing p0);
}
