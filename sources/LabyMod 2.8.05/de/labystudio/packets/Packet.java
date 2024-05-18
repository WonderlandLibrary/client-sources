package de.labystudio.packets;

import de.labystudio.handling.PacketHandler;

public abstract class Packet
{
    public abstract void read(PacketBuf var1);

    public abstract void write(PacketBuf var1);

    public abstract void handle(PacketHandler var1);
}
