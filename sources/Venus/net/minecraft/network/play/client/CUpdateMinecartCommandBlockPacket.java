/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.client;

import java.io.IOException;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.minecart.CommandBlockMinecartEntity;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.IServerPlayNetHandler;
import net.minecraft.tileentity.CommandBlockLogic;
import net.minecraft.world.World;

public class CUpdateMinecartCommandBlockPacket
implements IPacket<IServerPlayNetHandler> {
    private int entityId;
    private String command;
    private boolean trackOutput;

    public CUpdateMinecartCommandBlockPacket() {
    }

    public CUpdateMinecartCommandBlockPacket(int n, String string, boolean bl) {
        this.entityId = n;
        this.command = string;
        this.trackOutput = bl;
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.entityId = packetBuffer.readVarInt();
        this.command = packetBuffer.readString(Short.MAX_VALUE);
        this.trackOutput = packetBuffer.readBoolean();
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarInt(this.entityId);
        packetBuffer.writeString(this.command);
        packetBuffer.writeBoolean(this.trackOutput);
    }

    @Override
    public void processPacket(IServerPlayNetHandler iServerPlayNetHandler) {
        iServerPlayNetHandler.processUpdateCommandMinecart(this);
    }

    @Nullable
    public CommandBlockLogic getCommandBlock(World world) {
        Entity entity2 = world.getEntityByID(this.entityId);
        return entity2 instanceof CommandBlockMinecartEntity ? ((CommandBlockMinecartEntity)entity2).getCommandBlockLogic() : null;
    }

    public String getCommand() {
        return this.command;
    }

    public boolean shouldTrackOutput() {
        return this.trackOutput;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IServerPlayNetHandler)iNetHandler);
    }
}

