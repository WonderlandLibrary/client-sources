package cn.Hanabi.injection.interfaces;

import net.minecraft.network.*;

public interface INetworkManager
{
    void sendPacketNoEvent(final Packet p0);
}
