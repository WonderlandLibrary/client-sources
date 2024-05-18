/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 */
package net.minecraft.network.play.server;

import io.netty.buffer.ByteBuf;
import java.io.IOException;
import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S30PacketWindowItems
implements Packet {
    private int field_148914_a;
    private ItemStack[] field_148913_b;
    private static final String __OBFID = "CL_00001294";

    public S30PacketWindowItems() {
    }

    public S30PacketWindowItems(int p_i45186_1_, List p_i45186_2_) {
        this.field_148914_a = p_i45186_1_;
        this.field_148913_b = new ItemStack[p_i45186_2_.size()];
        int var3 = 0;
        while (var3 < this.field_148913_b.length) {
            ItemStack var4 = (ItemStack)p_i45186_2_.get(var3);
            this.field_148913_b[var3] = var4 == null ? null : var4.copy();
            ++var3;
        }
    }

    @Override
    public void readPacketData(PacketBuffer data) throws IOException {
        this.field_148914_a = data.readUnsignedByte();
        int var2 = data.readShort();
        this.field_148913_b = new ItemStack[var2];
        int var3 = 0;
        while (var3 < var2) {
            this.field_148913_b[var3] = data.readItemStackFromBuffer();
            ++var3;
        }
    }

    @Override
    public void writePacketData(PacketBuffer data) throws IOException {
        data.writeByte(this.field_148914_a);
        data.writeShort(this.field_148913_b.length);
        ItemStack[] var2 = this.field_148913_b;
        int var3 = var2.length;
        int var4 = 0;
        while (var4 < var3) {
            ItemStack var5 = var2[var4];
            data.writeItemStackToBuffer(var5);
            ++var4;
        }
    }

    public void func_180732_a(INetHandlerPlayClient p_180732_1_) {
        p_180732_1_.handleWindowItems(this);
    }

    public int func_148911_c() {
        return this.field_148914_a;
    }

    public ItemStack[] func_148910_d() {
        return this.field_148913_b;
    }

    @Override
    public void processPacket(INetHandler handler) {
        this.func_180732_a((INetHandlerPlayClient)handler);
    }
}

