/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.client;

import java.io.IOException;
import javax.annotation.Nullable;
import net.minecraft.advancements.Advancement;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.IServerPlayNetHandler;
import net.minecraft.util.ResourceLocation;

public class CSeenAdvancementsPacket
implements IPacket<IServerPlayNetHandler> {
    private Action action;
    private ResourceLocation tab;

    public CSeenAdvancementsPacket() {
    }

    public CSeenAdvancementsPacket(Action action, @Nullable ResourceLocation resourceLocation) {
        this.action = action;
        this.tab = resourceLocation;
    }

    public static CSeenAdvancementsPacket openedTab(Advancement advancement) {
        return new CSeenAdvancementsPacket(Action.OPENED_TAB, advancement.getId());
    }

    public static CSeenAdvancementsPacket closedScreen() {
        return new CSeenAdvancementsPacket(Action.CLOSED_SCREEN, null);
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.action = packetBuffer.readEnumValue(Action.class);
        if (this.action == Action.OPENED_TAB) {
            this.tab = packetBuffer.readResourceLocation();
        }
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeEnumValue(this.action);
        if (this.action == Action.OPENED_TAB) {
            packetBuffer.writeResourceLocation(this.tab);
        }
    }

    @Override
    public void processPacket(IServerPlayNetHandler iServerPlayNetHandler) {
        iServerPlayNetHandler.handleSeenAdvancements(this);
    }

    public Action getAction() {
        return this.action;
    }

    public ResourceLocation getTab() {
        return this.tab;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IServerPlayNetHandler)iNetHandler);
    }

    public static enum Action {
        OPENED_TAB,
        CLOSED_SCREEN;

    }
}

