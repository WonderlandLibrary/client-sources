/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.item.MerchantOffers;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;

public class SMerchantOffersPacket
implements IPacket<IClientPlayNetHandler> {
    private int containerId;
    private MerchantOffers offers;
    private int level;
    private int xp;
    private boolean field_218740_e;
    private boolean field_223478_f;

    public SMerchantOffersPacket() {
    }

    public SMerchantOffersPacket(int n, MerchantOffers merchantOffers, int n2, int n3, boolean bl, boolean bl2) {
        this.containerId = n;
        this.offers = merchantOffers;
        this.level = n2;
        this.xp = n3;
        this.field_218740_e = bl;
        this.field_223478_f = bl2;
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.containerId = packetBuffer.readVarInt();
        this.offers = MerchantOffers.read(packetBuffer);
        this.level = packetBuffer.readVarInt();
        this.xp = packetBuffer.readVarInt();
        this.field_218740_e = packetBuffer.readBoolean();
        this.field_223478_f = packetBuffer.readBoolean();
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarInt(this.containerId);
        this.offers.write(packetBuffer);
        packetBuffer.writeVarInt(this.level);
        packetBuffer.writeVarInt(this.xp);
        packetBuffer.writeBoolean(this.field_218740_e);
        packetBuffer.writeBoolean(this.field_223478_f);
    }

    @Override
    public void processPacket(IClientPlayNetHandler iClientPlayNetHandler) {
        iClientPlayNetHandler.handleMerchantOffers(this);
    }

    public int getContainerId() {
        return this.containerId;
    }

    public MerchantOffers getOffers() {
        return this.offers;
    }

    public int getLevel() {
        return this.level;
    }

    public int getExp() {
        return this.xp;
    }

    public boolean func_218735_f() {
        return this.field_218740_e;
    }

    public boolean func_223477_g() {
        return this.field_223478_f;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IClientPlayNetHandler)iNetHandler);
    }
}

