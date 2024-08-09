/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.server;

import java.io.IOException;
import java.util.Collection;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.storage.MapData;
import net.minecraft.world.storage.MapDecoration;

public class SMapDataPacket
implements IPacket<IClientPlayNetHandler> {
    private int mapId;
    private byte mapScale;
    private boolean trackingPosition;
    private boolean field_218730_d;
    private MapDecoration[] icons;
    private int minX;
    private int minZ;
    private int columns;
    private int rows;
    private byte[] mapDataBytes;

    public SMapDataPacket() {
    }

    public SMapDataPacket(int n, byte by, boolean bl, boolean bl2, Collection<MapDecoration> collection, byte[] byArray, int n2, int n3, int n4, int n5) {
        this.mapId = n;
        this.mapScale = by;
        this.trackingPosition = bl;
        this.field_218730_d = bl2;
        this.icons = collection.toArray(new MapDecoration[collection.size()]);
        this.minX = n2;
        this.minZ = n3;
        this.columns = n4;
        this.rows = n5;
        this.mapDataBytes = new byte[n4 * n5];
        for (int i = 0; i < n4; ++i) {
            for (int j = 0; j < n5; ++j) {
                this.mapDataBytes[i + j * n4] = byArray[n2 + i + (n3 + j) * 128];
            }
        }
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.mapId = packetBuffer.readVarInt();
        this.mapScale = packetBuffer.readByte();
        this.trackingPosition = packetBuffer.readBoolean();
        this.field_218730_d = packetBuffer.readBoolean();
        this.icons = new MapDecoration[packetBuffer.readVarInt()];
        for (int i = 0; i < this.icons.length; ++i) {
            MapDecoration.Type type = packetBuffer.readEnumValue(MapDecoration.Type.class);
            this.icons[i] = new MapDecoration(type, packetBuffer.readByte(), packetBuffer.readByte(), (byte)(packetBuffer.readByte() & 0xF), packetBuffer.readBoolean() ? packetBuffer.readTextComponent() : null);
        }
        this.columns = packetBuffer.readUnsignedByte();
        if (this.columns > 0) {
            this.rows = packetBuffer.readUnsignedByte();
            this.minX = packetBuffer.readUnsignedByte();
            this.minZ = packetBuffer.readUnsignedByte();
            this.mapDataBytes = packetBuffer.readByteArray();
        }
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarInt(this.mapId);
        packetBuffer.writeByte(this.mapScale);
        packetBuffer.writeBoolean(this.trackingPosition);
        packetBuffer.writeBoolean(this.field_218730_d);
        packetBuffer.writeVarInt(this.icons.length);
        for (MapDecoration mapDecoration : this.icons) {
            packetBuffer.writeEnumValue(mapDecoration.getType());
            packetBuffer.writeByte(mapDecoration.getX());
            packetBuffer.writeByte(mapDecoration.getY());
            packetBuffer.writeByte(mapDecoration.getRotation() & 0xF);
            if (mapDecoration.getCustomName() != null) {
                packetBuffer.writeBoolean(false);
                packetBuffer.writeTextComponent(mapDecoration.getCustomName());
                continue;
            }
            packetBuffer.writeBoolean(true);
        }
        packetBuffer.writeByte(this.columns);
        if (this.columns > 0) {
            packetBuffer.writeByte(this.rows);
            packetBuffer.writeByte(this.minX);
            packetBuffer.writeByte(this.minZ);
            packetBuffer.writeByteArray(this.mapDataBytes);
        }
    }

    @Override
    public void processPacket(IClientPlayNetHandler iClientPlayNetHandler) {
        iClientPlayNetHandler.handleMaps(this);
    }

    public int getMapId() {
        return this.mapId;
    }

    public void setMapdataTo(MapData mapData) {
        int n;
        mapData.scale = this.mapScale;
        mapData.trackingPosition = this.trackingPosition;
        mapData.locked = this.field_218730_d;
        mapData.mapDecorations.clear();
        for (n = 0; n < this.icons.length; ++n) {
            MapDecoration mapDecoration = this.icons[n];
            mapData.mapDecorations.put("icon-" + n, mapDecoration);
        }
        for (n = 0; n < this.columns; ++n) {
            for (int i = 0; i < this.rows; ++i) {
                mapData.colors[this.minX + n + (this.minZ + i) * 128] = this.mapDataBytes[n + i * this.columns];
            }
        }
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IClientPlayNetHandler)iNetHandler);
    }
}

