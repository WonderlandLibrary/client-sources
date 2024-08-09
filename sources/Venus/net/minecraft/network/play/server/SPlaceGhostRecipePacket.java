/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

public class SPlaceGhostRecipePacket
implements IPacket<IClientPlayNetHandler> {
    private int windowId;
    private ResourceLocation recipe;

    public SPlaceGhostRecipePacket() {
    }

    public SPlaceGhostRecipePacket(int n, IRecipe<?> iRecipe) {
        this.windowId = n;
        this.recipe = iRecipe.getId();
    }

    public ResourceLocation getRecipeId() {
        return this.recipe;
    }

    public int getWindowId() {
        return this.windowId;
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.windowId = packetBuffer.readByte();
        this.recipe = packetBuffer.readResourceLocation();
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeByte(this.windowId);
        packetBuffer.writeResourceLocation(this.recipe);
    }

    @Override
    public void processPacket(IClientPlayNetHandler iClientPlayNetHandler) {
        iClientPlayNetHandler.handlePlaceGhostRecipe(this);
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IClientPlayNetHandler)iNetHandler);
    }
}

