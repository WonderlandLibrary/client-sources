package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.world.storage.*;
import java.io.*;
import net.minecraft.network.*;

public class S34PacketMaps implements Packet<INetHandlerPlayClient>
{
    private int mapId;
    private int mapMaxY;
    private static final String[] I;
    private int mapMinX;
    private int mapMinY;
    private byte[] mapDataBytes;
    private int mapMaxX;
    private Vec4b[] mapVisiblePlayersVec4b;
    private byte mapScale;
    
    public S34PacketMaps(final int mapId, final byte mapScale, final Collection<Vec4b> collection, final byte[] array, final int mapMinX, final int mapMinY, final int mapMaxX, final int mapMaxY) {
        this.mapId = mapId;
        this.mapScale = mapScale;
        this.mapVisiblePlayersVec4b = collection.toArray(new Vec4b[collection.size()]);
        this.mapMinX = mapMinX;
        this.mapMinY = mapMinY;
        this.mapMaxX = mapMaxX;
        this.mapMaxY = mapMaxY;
        this.mapDataBytes = new byte[mapMaxX * mapMaxY];
        int i = "".length();
        "".length();
        if (2 == 0) {
            throw null;
        }
        while (i < mapMaxX) {
            int j = "".length();
            "".length();
            if (4 <= 0) {
                throw null;
            }
            while (j < mapMaxY) {
                this.mapDataBytes[i + j * mapMaxX] = array[mapMinX + i + (mapMinY + j) * (70 + 59 - 97 + 96)];
                ++j;
            }
            ++i;
        }
    }
    
    public void setMapdataTo(final MapData mapData) {
        mapData.scale = this.mapScale;
        mapData.mapDecorations.clear();
        int i = "".length();
        "".length();
        if (3 == 1) {
            throw null;
        }
        while (i < this.mapVisiblePlayersVec4b.length) {
            mapData.mapDecorations.put(S34PacketMaps.I["".length()] + i, this.mapVisiblePlayersVec4b[i]);
            ++i;
        }
        int j = "".length();
        "".length();
        if (2 >= 4) {
            throw null;
        }
        while (j < this.mapMaxX) {
            int k = "".length();
            "".length();
            if (1 >= 3) {
                throw null;
            }
            while (k < this.mapMaxY) {
                mapData.colors[this.mapMinX + j + (this.mapMinY + k) * (116 + 24 - 57 + 45)] = this.mapDataBytes[j + k * this.mapMaxX];
                ++k;
            }
            ++j;
        }
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.mapId);
        packetBuffer.writeByte(this.mapScale);
        packetBuffer.writeVarIntToBuffer(this.mapVisiblePlayersVec4b.length);
        final Vec4b[] mapVisiblePlayersVec4b;
        final int length = (mapVisiblePlayersVec4b = this.mapVisiblePlayersVec4b).length;
        int i = "".length();
        "".length();
        if (3 <= 1) {
            throw null;
        }
        while (i < length) {
            final Vec4b vec4b = mapVisiblePlayersVec4b[i];
            packetBuffer.writeByte((vec4b.func_176110_a() & (0xAA ^ 0xA5)) << (0xBF ^ 0xBB) | (vec4b.func_176111_d() & (0x9 ^ 0x6)));
            packetBuffer.writeByte(vec4b.func_176112_b());
            packetBuffer.writeByte(vec4b.func_176113_c());
            ++i;
        }
        packetBuffer.writeByte(this.mapMaxX);
        if (this.mapMaxX > 0) {
            packetBuffer.writeByte(this.mapMaxY);
            packetBuffer.writeByte(this.mapMinX);
            packetBuffer.writeByte(this.mapMinY);
            packetBuffer.writeByteArray(this.mapDataBytes);
        }
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("3\u0017\u001d\u001fl", "ZtrqA");
    }
    
    public int getMapId() {
        return this.mapId;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.mapId = packetBuffer.readVarIntFromBuffer();
        this.mapScale = packetBuffer.readByte();
        this.mapVisiblePlayersVec4b = new Vec4b[packetBuffer.readVarIntFromBuffer()];
        int i = "".length();
        "".length();
        if (2 >= 3) {
            throw null;
        }
        while (i < this.mapVisiblePlayersVec4b.length) {
            final short n = packetBuffer.readByte();
            this.mapVisiblePlayersVec4b[i] = new Vec4b((byte)(n >> (0x4E ^ 0x4A) & (0x57 ^ 0x58)), packetBuffer.readByte(), packetBuffer.readByte(), (byte)(n & (0x1A ^ 0x15)));
            ++i;
        }
        this.mapMaxX = packetBuffer.readUnsignedByte();
        if (this.mapMaxX > 0) {
            this.mapMaxY = packetBuffer.readUnsignedByte();
            this.mapMinX = packetBuffer.readUnsignedByte();
            this.mapMinY = packetBuffer.readUnsignedByte();
            this.mapDataBytes = packetBuffer.readByteArray();
        }
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (0 >= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public S34PacketMaps() {
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    @Override
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleMaps(this);
    }
    
    static {
        I();
    }
}
