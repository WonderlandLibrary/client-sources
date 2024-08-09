/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.server;

import java.io.IOException;
import javax.annotation.Nullable;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;

public class SOpenWindowPacket
implements IPacket<IClientPlayNetHandler> {
    private int windowId;
    private int menuId;
    private ITextComponent title;

    public SOpenWindowPacket() {
    }

    public SOpenWindowPacket(int n, ContainerType<?> containerType, ITextComponent iTextComponent) {
        this.windowId = n;
        this.menuId = Registry.MENU.getId(containerType);
        this.title = iTextComponent;
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.windowId = packetBuffer.readVarInt();
        this.menuId = packetBuffer.readVarInt();
        this.title = packetBuffer.readTextComponent();
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarInt(this.windowId);
        packetBuffer.writeVarInt(this.menuId);
        packetBuffer.writeTextComponent(this.title);
    }

    @Override
    public void processPacket(IClientPlayNetHandler iClientPlayNetHandler) {
        iClientPlayNetHandler.handleOpenWindowPacket(this);
    }

    public int getWindowId() {
        return this.windowId;
    }

    @Nullable
    public ContainerType<?> getContainerType() {
        return (ContainerType)Registry.MENU.getByValue(this.menuId);
    }

    public ITextComponent getTitle() {
        return this.title;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IClientPlayNetHandler)iNetHandler);
    }
}

