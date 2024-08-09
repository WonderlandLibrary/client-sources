/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.server;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.io.IOException;
import java.util.Map;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.stats.Stat;
import net.minecraft.stats.StatType;
import net.minecraft.util.registry.Registry;

public class SStatisticsPacket
implements IPacket<IClientPlayNetHandler> {
    private Object2IntMap<Stat<?>> statisticMap;

    public SStatisticsPacket() {
    }

    public SStatisticsPacket(Object2IntMap<Stat<?>> object2IntMap) {
        this.statisticMap = object2IntMap;
    }

    @Override
    public void processPacket(IClientPlayNetHandler iClientPlayNetHandler) {
        iClientPlayNetHandler.handleStatistics(this);
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        int n = packetBuffer.readVarInt();
        this.statisticMap = new Object2IntOpenHashMap(n);
        for (int i = 0; i < n; ++i) {
            this.readValues((StatType)Registry.STATS.getByValue(packetBuffer.readVarInt()), packetBuffer);
        }
    }

    private <T> void readValues(StatType<T> statType, PacketBuffer packetBuffer) {
        int n = packetBuffer.readVarInt();
        int n2 = packetBuffer.readVarInt();
        this.statisticMap.put((Stat<?>)statType.get(statType.getRegistry().getByValue(n)), n2);
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarInt(this.statisticMap.size());
        for (Object2IntMap.Entry entry : this.statisticMap.object2IntEntrySet()) {
            Stat stat = (Stat)entry.getKey();
            packetBuffer.writeVarInt(Registry.STATS.getId(stat.getType()));
            packetBuffer.writeVarInt(this.func_197683_a(stat));
            packetBuffer.writeVarInt(entry.getIntValue());
        }
    }

    private <T> int func_197683_a(Stat<T> stat) {
        return stat.getType().getRegistry().getId(stat.getValue());
    }

    public Map<Stat<?>, Integer> getStatisticMap() {
        return this.statisticMap;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IClientPlayNetHandler)iNetHandler);
    }
}

