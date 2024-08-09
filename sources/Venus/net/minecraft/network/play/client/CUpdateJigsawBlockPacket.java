/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.IServerPlayNetHandler;
import net.minecraft.tileentity.JigsawTileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class CUpdateJigsawBlockPacket
implements IPacket<IServerPlayNetHandler> {
    private BlockPos field_218790_a;
    private ResourceLocation field_240847_b_;
    private ResourceLocation field_240848_c_;
    private ResourceLocation field_240849_d_;
    private String field_218793_d;
    private JigsawTileEntity.OrientationType field_240850_f_;

    public CUpdateJigsawBlockPacket() {
    }

    public CUpdateJigsawBlockPacket(BlockPos blockPos, ResourceLocation resourceLocation, ResourceLocation resourceLocation2, ResourceLocation resourceLocation3, String string, JigsawTileEntity.OrientationType orientationType) {
        this.field_218790_a = blockPos;
        this.field_240847_b_ = resourceLocation;
        this.field_240848_c_ = resourceLocation2;
        this.field_240849_d_ = resourceLocation3;
        this.field_218793_d = string;
        this.field_240850_f_ = orientationType;
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.field_218790_a = packetBuffer.readBlockPos();
        this.field_240847_b_ = packetBuffer.readResourceLocation();
        this.field_240848_c_ = packetBuffer.readResourceLocation();
        this.field_240849_d_ = packetBuffer.readResourceLocation();
        this.field_218793_d = packetBuffer.readString(Short.MAX_VALUE);
        this.field_240850_f_ = JigsawTileEntity.OrientationType.func_235673_a_(packetBuffer.readString(Short.MAX_VALUE)).orElse(JigsawTileEntity.OrientationType.ALIGNED);
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeBlockPos(this.field_218790_a);
        packetBuffer.writeResourceLocation(this.field_240847_b_);
        packetBuffer.writeResourceLocation(this.field_240848_c_);
        packetBuffer.writeResourceLocation(this.field_240849_d_);
        packetBuffer.writeString(this.field_218793_d);
        packetBuffer.writeString(this.field_240850_f_.getString());
    }

    @Override
    public void processPacket(IServerPlayNetHandler iServerPlayNetHandler) {
        iServerPlayNetHandler.func_217262_a(this);
    }

    public BlockPos func_218789_b() {
        return this.field_218790_a;
    }

    public ResourceLocation func_240851_c_() {
        return this.field_240847_b_;
    }

    public ResourceLocation func_240852_d_() {
        return this.field_240848_c_;
    }

    public ResourceLocation func_240853_e_() {
        return this.field_240849_d_;
    }

    public String func_218788_e() {
        return this.field_218793_d;
    }

    public JigsawTileEntity.OrientationType func_240854_g_() {
        return this.field_240850_f_;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IServerPlayNetHandler)iNetHandler);
    }
}

