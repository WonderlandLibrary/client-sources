/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.server;

import java.io.IOException;
import java.util.Collection;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.storage.MapData;
import net.minecraft.world.storage.MapDecoration;

public class SPacketMaps
implements Packet<INetHandlerPlayClient> {
    private int mapId;
    private byte mapScale;
    private boolean trackingPosition;
    private MapDecoration[] icons;
    private int minX;
    private int minZ;
    private int columns;
    private int rows;
    private byte[] mapDataBytes;

    public SPacketMaps() {
    }

    public SPacketMaps(int mapIdIn, byte mapScaleIn, boolean trackingPositionIn, Collection<MapDecoration> iconsIn, byte[] p_i46937_5_, int minXIn, int minZIn, int columnsIn, int rowsIn) {
        this.mapId = mapIdIn;
        this.mapScale = mapScaleIn;
        this.trackingPosition = trackingPositionIn;
        this.icons = iconsIn.toArray(new MapDecoration[iconsIn.size()]);
        this.minX = minXIn;
        this.minZ = minZIn;
        this.columns = columnsIn;
        this.rows = rowsIn;
        this.mapDataBytes = new byte[columnsIn * rowsIn];
        for (int i = 0; i < columnsIn; ++i) {
            for (int j = 0; j < rowsIn; ++j) {
                this.mapDataBytes[i + j * columnsIn] = p_i46937_5_[minXIn + i + (minZIn + j) * 128];
            }
        }
    }

    @Override
    public void readPacketData(PacketBuffer buf) throws IOException {
        this.mapId = buf.readVarIntFromBuffer();
        this.mapScale = buf.readByte();
        this.trackingPosition = buf.readBoolean();
        this.icons = new MapDecoration[buf.readVarIntFromBuffer()];
        for (int i = 0; i < this.icons.length; ++i) {
            short short1 = buf.readByte();
            this.icons[i] = new MapDecoration(MapDecoration.Type.func_191159_a((byte)(short1 >> 4 & 0xF)), buf.readByte(), buf.readByte(), (byte)(short1 & 0xF));
        }
        this.columns = buf.readUnsignedByte();
        if (this.columns > 0) {
            this.rows = buf.readUnsignedByte();
            this.minX = buf.readUnsignedByte();
            this.minZ = buf.readUnsignedByte();
            this.mapDataBytes = buf.readByteArray();
        }
    }

    @Override
    public void writePacketData(PacketBuffer buf) throws IOException {
        buf.writeVarIntToBuffer(this.mapId);
        buf.writeByte(this.mapScale);
        buf.writeBoolean(this.trackingPosition);
        buf.writeVarIntToBuffer(this.icons.length);
        for (MapDecoration mapdecoration : this.icons) {
            buf.writeByte((mapdecoration.getType() & 0xF) << 4 | mapdecoration.getRotation() & 0xF);
            buf.writeByte(mapdecoration.getX());
            buf.writeByte(mapdecoration.getY());
        }
        buf.writeByte(this.columns);
        if (this.columns > 0) {
            buf.writeByte(this.rows);
            buf.writeByte(this.minX);
            buf.writeByte(this.minZ);
            buf.writeByteArray(this.mapDataBytes);
        }
    }

    @Override
    public void processPacket(INetHandlerPlayClient handler) {
        handler.handleMaps(this);
    }

    public int getMapId() {
        return this.mapId;
    }

    public void setMapdataTo(MapData mapdataIn) {
        mapdataIn.scale = this.mapScale;
        mapdataIn.trackingPosition = this.trackingPosition;
        mapdataIn.mapDecorations.clear();
        for (int i = 0; i < this.icons.length; ++i) {
            MapDecoration mapdecoration = this.icons[i];
            mapdataIn.mapDecorations.put("icon-" + i, mapdecoration);
        }
        for (int j = 0; j < this.columns; ++j) {
            for (int k = 0; k < this.rows; ++k) {
                mapdataIn.colors[this.minX + j + (this.minZ + k) * 128] = this.mapDataBytes[j + k * this.columns];
            }
        }
    }
}

