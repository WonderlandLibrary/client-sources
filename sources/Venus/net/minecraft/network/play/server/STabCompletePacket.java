/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.server;

import com.google.common.collect.Lists;
import com.mojang.brigadier.context.StringRange;
import com.mojang.brigadier.suggestion.Suggestion;
import com.mojang.brigadier.suggestion.Suggestions;
import java.io.IOException;
import java.util.ArrayList;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentUtils;

public class STabCompletePacket
implements IPacket<IClientPlayNetHandler> {
    private int transactionId;
    private Suggestions suggestions;

    public STabCompletePacket() {
    }

    public STabCompletePacket(int n, Suggestions suggestions) {
        this.transactionId = n;
        this.suggestions = suggestions;
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.transactionId = packetBuffer.readVarInt();
        int n = packetBuffer.readVarInt();
        int n2 = packetBuffer.readVarInt();
        StringRange stringRange = StringRange.between(n, n + n2);
        int n3 = packetBuffer.readVarInt();
        ArrayList<Suggestion> arrayList = Lists.newArrayListWithCapacity(n3);
        for (int i = 0; i < n3; ++i) {
            String string = packetBuffer.readString(Short.MAX_VALUE);
            ITextComponent iTextComponent = packetBuffer.readBoolean() ? packetBuffer.readTextComponent() : null;
            arrayList.add(new Suggestion(stringRange, string, iTextComponent));
        }
        this.suggestions = new Suggestions(stringRange, arrayList);
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarInt(this.transactionId);
        packetBuffer.writeVarInt(this.suggestions.getRange().getStart());
        packetBuffer.writeVarInt(this.suggestions.getRange().getLength());
        packetBuffer.writeVarInt(this.suggestions.getList().size());
        for (Suggestion suggestion : this.suggestions.getList()) {
            packetBuffer.writeString(suggestion.getText());
            packetBuffer.writeBoolean(suggestion.getTooltip() != null);
            if (suggestion.getTooltip() == null) continue;
            packetBuffer.writeTextComponent(TextComponentUtils.toTextComponent(suggestion.getTooltip()));
        }
    }

    @Override
    public void processPacket(IClientPlayNetHandler iClientPlayNetHandler) {
        iClientPlayNetHandler.handleTabComplete(this);
    }

    public int getTransactionId() {
        return this.transactionId;
    }

    public Suggestions getSuggestions() {
        return this.suggestions;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IClientPlayNetHandler)iNetHandler);
    }
}

