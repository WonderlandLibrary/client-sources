/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.IServerPlayNetHandler;
import net.minecraft.util.ResourceLocation;

public class CMarkRecipeSeenPacket
implements IPacket<IServerPlayNetHandler> {
    private ResourceLocation field_244320_a;

    public CMarkRecipeSeenPacket() {
    }

    public CMarkRecipeSeenPacket(IRecipe<?> iRecipe) {
        this.field_244320_a = iRecipe.getId();
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.field_244320_a = packetBuffer.readResourceLocation();
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeResourceLocation(this.field_244320_a);
    }

    @Override
    public void processPacket(IServerPlayNetHandler iServerPlayNetHandler) {
        iServerPlayNetHandler.handleRecipeBookUpdate(this);
    }

    public ResourceLocation func_244321_b() {
        return this.field_244320_a;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IServerPlayNetHandler)iNetHandler);
    }
}

