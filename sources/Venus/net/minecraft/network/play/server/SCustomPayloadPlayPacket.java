/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

public class SCustomPayloadPlayPacket
implements IPacket<IClientPlayNetHandler> {
    public static final ResourceLocation BRAND = new ResourceLocation("brand");
    public static final ResourceLocation DEBUG_PATH = new ResourceLocation("debug/path");
    public static final ResourceLocation DEBUG_NEIGHBORS_UPDATE = new ResourceLocation("debug/neighbors_update");
    public static final ResourceLocation DEBUG_CAVES = new ResourceLocation("debug/caves");
    public static final ResourceLocation DEBUG_STRUCTURES = new ResourceLocation("debug/structures");
    public static final ResourceLocation DEBUG_WORLDGEN_ATTEMPT = new ResourceLocation("debug/worldgen_attempt");
    public static final ResourceLocation DEBUG_POI_TICKET_COUNT = new ResourceLocation("debug/poi_ticket_count");
    public static final ResourceLocation DEBUG_POI_ADDED = new ResourceLocation("debug/poi_added");
    public static final ResourceLocation DEBUG_POI_REMOVED = new ResourceLocation("debug/poi_removed");
    public static final ResourceLocation DEBUG_VILLAGE_SECTIONS = new ResourceLocation("debug/village_sections");
    public static final ResourceLocation DEBUG_GOAL_SELECTOR = new ResourceLocation("debug/goal_selector");
    public static final ResourceLocation DEBUG_BRAIN = new ResourceLocation("debug/brain");
    public static final ResourceLocation field_229727_m_ = new ResourceLocation("debug/bee");
    public static final ResourceLocation field_229728_n_ = new ResourceLocation("debug/hive");
    public static final ResourceLocation field_229729_o_ = new ResourceLocation("debug/game_test_add_marker");
    public static final ResourceLocation field_229730_p_ = new ResourceLocation("debug/game_test_clear");
    public static final ResourceLocation DEBUG_RAIDS = new ResourceLocation("debug/raids");
    private ResourceLocation channel;
    private PacketBuffer data;

    public SCustomPayloadPlayPacket() {
    }

    public SCustomPayloadPlayPacket(ResourceLocation resourceLocation, PacketBuffer packetBuffer) {
        this.channel = resourceLocation;
        this.data = packetBuffer;
        if (packetBuffer.writerIndex() > 0x100000) {
            throw new IllegalArgumentException("Payload may not be larger than 1048576 bytes");
        }
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.channel = packetBuffer.readResourceLocation();
        int n = packetBuffer.readableBytes();
        if (n < 0 || n > 0x100000) {
            throw new IOException("Payload may not be larger than 1048576 bytes");
        }
        this.data = new PacketBuffer(packetBuffer.readBytes(n));
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeResourceLocation(this.channel);
        packetBuffer.writeBytes(this.data.copy());
    }

    @Override
    public void processPacket(IClientPlayNetHandler iClientPlayNetHandler) {
        iClientPlayNetHandler.handleCustomPayload(this);
    }

    public ResourceLocation getChannelName() {
        return this.channel;
    }

    public PacketBuffer getBufferData() {
        return new PacketBuffer(this.data.copy());
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IClientPlayNetHandler)iNetHandler);
    }
}

