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

public class CPlaceRecipePacket
implements IPacket<IServerPlayNetHandler> {
    private int windowId;
    private ResourceLocation recipeId;
    private boolean placeAll;

    public CPlaceRecipePacket() {
    }

    public CPlaceRecipePacket(int n, IRecipe<?> iRecipe, boolean bl) {
        this.windowId = n;
        this.recipeId = iRecipe.getId();
        this.placeAll = bl;
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.windowId = packetBuffer.readByte();
        this.recipeId = packetBuffer.readResourceLocation();
        this.placeAll = packetBuffer.readBoolean();
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeByte(this.windowId);
        packetBuffer.writeResourceLocation(this.recipeId);
        packetBuffer.writeBoolean(this.placeAll);
    }

    @Override
    public void processPacket(IServerPlayNetHandler iServerPlayNetHandler) {
        iServerPlayNetHandler.processPlaceRecipe(this);
    }

    public int getWindowId() {
        return this.windowId;
    }

    public ResourceLocation getRecipeId() {
        return this.recipeId;
    }

    public boolean shouldPlaceAll() {
        return this.placeAll;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IServerPlayNetHandler)iNetHandler);
    }
}

