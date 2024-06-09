/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  io.netty.buffer.ByteBuf
 */
package net.minecraft.network.play.server;

import com.google.common.collect.Lists;
import io.netty.buffer.ByteBuf;
import java.io.IOException;
import java.util.ArrayList;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.NibbleArray;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;

public class S21PacketChunkData
implements Packet {
    private int field_149284_a;
    private int field_149282_b;
    private Extracted field_179758_c;
    private boolean field_149279_g;
    private static final String __OBFID = "CL_00001304";

    public S21PacketChunkData() {
    }

    public S21PacketChunkData(Chunk p_i45196_1_, boolean p_i45196_2_, int p_i45196_3_) {
        this.field_149284_a = p_i45196_1_.xPosition;
        this.field_149282_b = p_i45196_1_.zPosition;
        this.field_149279_g = p_i45196_2_;
        this.field_179758_c = S21PacketChunkData.func_179756_a(p_i45196_1_, p_i45196_2_, !p_i45196_1_.getWorld().provider.getHasNoSky(), p_i45196_3_);
    }

    @Override
    public void readPacketData(PacketBuffer data) throws IOException {
        this.field_149284_a = data.readInt();
        this.field_149282_b = data.readInt();
        this.field_149279_g = data.readBoolean();
        this.field_179758_c = new Extracted();
        this.field_179758_c.field_150280_b = data.readShort();
        this.field_179758_c.field_150282_a = data.readByteArray();
    }

    @Override
    public void writePacketData(PacketBuffer data) throws IOException {
        data.writeInt(this.field_149284_a);
        data.writeInt(this.field_149282_b);
        data.writeBoolean(this.field_149279_g);
        data.writeShort((short)(this.field_179758_c.field_150280_b & 65535));
        data.writeByteArray(this.field_179758_c.field_150282_a);
    }

    public void processPacket(INetHandlerPlayClient handler) {
        handler.handleChunkData(this);
    }

    public byte[] func_149272_d() {
        return this.field_179758_c.field_150282_a;
    }

    protected static int func_180737_a(int p_180737_0_, boolean p_180737_1_, boolean p_180737_2_) {
        int var3 = p_180737_0_ * 2 * 16 * 16 * 16;
        int var4 = p_180737_0_ * 16 * 16 * 16 / 2;
        int var5 = p_180737_1_ ? p_180737_0_ * 16 * 16 * 16 / 2 : 0;
        int var6 = p_180737_2_ ? 256 : 0;
        return var3 + var4 + var5 + var6;
    }

    public static Extracted func_179756_a(Chunk p_179756_0_, boolean p_179756_1_, boolean p_179756_2_, int p_179756_3_) {
        ExtendedBlockStorage[] var4 = p_179756_0_.getBlockStorageArray();
        Extracted var5 = new Extracted();
        ArrayList var6 = Lists.newArrayList();
        int var7 = 0;
        while (var7 < var4.length) {
            ExtendedBlockStorage var8 = var4[var7];
            if (!(var8 == null || p_179756_1_ && var8.isEmpty() || (p_179756_3_ & 1 << var7) == 0)) {
                var5.field_150280_b |= 1 << var7;
                var6.add(var8);
            }
            ++var7;
        }
        var5.field_150282_a = new byte[S21PacketChunkData.func_180737_a(Integer.bitCount(var5.field_150280_b), p_179756_2_, p_179756_1_)];
        var7 = 0;
        for (ExtendedBlockStorage var922 : var6) {
            char[] var10;
            char[] var11 = var10 = var922.getData();
            int var12 = var10.length;
            int var13 = 0;
            while (var13 < var12) {
                char var14 = var11[var13];
                var5.field_150282_a[var7++] = (byte)(var14 & 255);
                var5.field_150282_a[var7++] = (byte)(var14 >> 8 & 255);
                ++var13;
            }
        }
        for (ExtendedBlockStorage var922 : var6) {
            var7 = S21PacketChunkData.func_179757_a(var922.getBlocklightArray().getData(), var5.field_150282_a, var7);
        }
        if (p_179756_2_) {
            for (ExtendedBlockStorage var922 : var6) {
                var7 = S21PacketChunkData.func_179757_a(var922.getSkylightArray().getData(), var5.field_150282_a, var7);
            }
        }
        if (p_179756_1_) {
            S21PacketChunkData.func_179757_a(p_179756_0_.getBiomeArray(), var5.field_150282_a, var7);
        }
        return var5;
    }

    private static int func_179757_a(byte[] p_179757_0_, byte[] p_179757_1_, int p_179757_2_) {
        System.arraycopy(p_179757_0_, 0, p_179757_1_, p_179757_2_, p_179757_0_.length);
        return p_179757_2_ + p_179757_0_.length;
    }

    public int func_149273_e() {
        return this.field_149284_a;
    }

    public int func_149271_f() {
        return this.field_149282_b;
    }

    public int func_149276_g() {
        return this.field_179758_c.field_150280_b;
    }

    public boolean func_149274_i() {
        return this.field_149279_g;
    }

    @Override
    public void processPacket(INetHandler handler) {
        this.processPacket((INetHandlerPlayClient)handler);
    }

    public static class Extracted {
        public byte[] field_150282_a;
        public int field_150280_b;
        private static final String __OBFID = "CL_00001305";
    }

}

