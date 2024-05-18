package net.minecraft.src;

import java.util.*;

public class PacketCount
{
    public static boolean allowCounting;
    private static final Map packetCountForID;
    private static final Map sizeCountForID;
    private static final Object lock;
    
    static {
        PacketCount.allowCounting = true;
        packetCountForID = new HashMap();
        sizeCountForID = new HashMap();
        lock = new Object();
    }
    
    public static void countPacket(final int par0, final long par1) {
        if (PacketCount.allowCounting) {
            final Object var3 = PacketCount.lock;
            synchronized (PacketCount.lock) {
                if (PacketCount.packetCountForID.containsKey(par0)) {
                    PacketCount.packetCountForID.put(par0, PacketCount.packetCountForID.get(par0) + 1L);
                    PacketCount.sizeCountForID.put(par0, PacketCount.sizeCountForID.get(par0) + par1);
                }
                else {
                    PacketCount.packetCountForID.put(par0, 1L);
                    PacketCount.sizeCountForID.put(par0, par1);
                }
            }
            // monitorexit(PacketCount.lock)
        }
    }
}
