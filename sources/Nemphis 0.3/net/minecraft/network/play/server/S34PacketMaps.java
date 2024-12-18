/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 */
package net.minecraft.network.play.server;

import io.netty.buffer.ByteBuf;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.Vec4b;
import net.minecraft.world.storage.MapData;

public class S34PacketMaps
implements Packet {
    private int mapId;
    private byte field_179739_b;
    private Vec4b[] field_179740_c;
    private int field_179737_d;
    private int field_179738_e;
    private int field_179735_f;
    private int field_179736_g;
    private byte[] field_179741_h;
    private static final String __OBFID = "CL_00001311";

    public S34PacketMaps() {
    }

    public S34PacketMaps(int p_i45975_1_, byte p_i45975_2_, Collection p_i45975_3_, byte[] p_i45975_4_, int p_i45975_5_, int p_i45975_6_, int p_i45975_7_, int p_i45975_8_) {
        this.mapId = p_i45975_1_;
        this.field_179739_b = p_i45975_2_;
        this.field_179740_c = p_i45975_3_.toArray(new Vec4b[p_i45975_3_.size()]);
        this.field_179737_d = p_i45975_5_;
        this.field_179738_e = p_i45975_6_;
        this.field_179735_f = p_i45975_7_;
        this.field_179736_g = p_i45975_8_;
        this.field_179741_h = new byte[p_i45975_7_ * p_i45975_8_];
        int var9 = 0;
        while (var9 < p_i45975_7_) {
            int var10 = 0;
            while (var10 < p_i45975_8_) {
                this.field_179741_h[var9 + var10 * p_i45975_7_] = p_i45975_4_[p_i45975_5_ + var9 + (p_i45975_6_ + var10) * 128];
                ++var10;
            }
            ++var9;
        }
    }

    @Override
    public void readPacketData(PacketBuffer data) throws IOException {
        this.mapId = data.readVarIntFromBuffer();
        this.field_179739_b = data.readByte();
        this.field_179740_c = new Vec4b[data.readVarIntFromBuffer()];
        int var2 = 0;
        while (var2 < this.field_179740_c.length) {
            short var3 = data.readByte();
            this.field_179740_c[var2] = new Vec4b((byte)(var3 >> 4 & 15), data.readByte(), data.readByte(), (byte)(var3 & 15));
            ++var2;
        }
        this.field_179735_f = data.readUnsignedByte();
        if (this.field_179735_f > 0) {
            this.field_179736_g = data.readUnsignedByte();
            this.field_179737_d = data.readUnsignedByte();
            this.field_179738_e = data.readUnsignedByte();
            this.field_179741_h = data.readByteArray();
        }
    }

    @Override
    public void writePacketData(PacketBuffer data) throws IOException {
        data.writeVarIntToBuffer(this.mapId);
        data.writeByte(this.field_179739_b);
        data.writeVarIntToBuffer(this.field_179740_c.length);
        Vec4b[] var2 = this.field_179740_c;
        int var3 = var2.length;
        int var4 = 0;
        while (var4 < var3) {
            Vec4b var5 = var2[var4];
            data.writeByte((var5.func_176110_a() & 15) << 4 | var5.func_176111_d() & 15);
            data.writeByte(var5.func_176112_b());
            data.writeByte(var5.func_176113_c());
            ++var4;
        }
        data.writeByte(this.field_179735_f);
        if (this.field_179735_f > 0) {
            data.writeByte(this.field_179736_g);
            data.writeByte(this.field_179737_d);
            data.writeByte(this.field_179738_e);
            data.writeByteArray(this.field_179741_h);
        }
    }

    public void func_180741_a(INetHandlerPlayClient p_180741_1_) {
        p_180741_1_.handleMaps(this);
    }

    public int getMapId() {
        return this.mapId;
    }

    public void func_179734_a(MapData p_179734_1_) {
        p_179734_1_.scale = this.field_179739_b;
        p_179734_1_.playersVisibleOnMap.clear();
        int var2 = 0;
        while (var2 < this.field_179740_c.length) {
            Vec4b var3 = this.field_179740_c[var2];
            p_179734_1_.playersVisibleOnMap.put("icon-" + var2, var3);
            ++var2;
        }
        var2 = 0;
        while (var2 < this.field_179735_f) {
            int var4 = 0;
            while (var4 < this.field_179736_g) {
                p_179734_1_.colors[this.field_179737_d + var2 + (this.field_179738_e + var4) * 128] = this.field_179741_h[var2 + var4 * this.field_179735_f];
                ++var4;
            }
            ++var2;
        }
    }

    @Override
    public void processPacket(INetHandler handler) {
        this.func_180741_a((INetHandlerPlayClient)handler);
    }
}

