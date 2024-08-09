/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.server;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.Collection;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

public class STeamsPacket
implements IPacket<IClientPlayNetHandler> {
    private String name = "";
    private ITextComponent displayName = StringTextComponent.EMPTY;
    private ITextComponent prefix = StringTextComponent.EMPTY;
    private ITextComponent suffix = StringTextComponent.EMPTY;
    private String nameTagVisibility;
    private String collisionRule;
    private TextFormatting color;
    private final Collection<String> players;
    private int action;
    private int friendlyFlags;

    public STeamsPacket() {
        this.nameTagVisibility = Team.Visible.ALWAYS.internalName;
        this.collisionRule = Team.CollisionRule.ALWAYS.name;
        this.color = TextFormatting.RESET;
        this.players = Lists.newArrayList();
    }

    public STeamsPacket(ScorePlayerTeam scorePlayerTeam, int n) {
        this.nameTagVisibility = Team.Visible.ALWAYS.internalName;
        this.collisionRule = Team.CollisionRule.ALWAYS.name;
        this.color = TextFormatting.RESET;
        this.players = Lists.newArrayList();
        this.name = scorePlayerTeam.getName();
        this.action = n;
        if (n == 0 || n == 2) {
            this.displayName = scorePlayerTeam.getDisplayName();
            this.friendlyFlags = scorePlayerTeam.getFriendlyFlags();
            this.nameTagVisibility = scorePlayerTeam.getNameTagVisibility().internalName;
            this.collisionRule = scorePlayerTeam.getCollisionRule().name;
            this.color = scorePlayerTeam.getColor();
            this.prefix = scorePlayerTeam.getPrefix();
            this.suffix = scorePlayerTeam.getSuffix();
        }
        if (n == 0) {
            this.players.addAll(scorePlayerTeam.getMembershipCollection());
        }
    }

    public STeamsPacket(ScorePlayerTeam scorePlayerTeam, Collection<String> collection, int n) {
        this.nameTagVisibility = Team.Visible.ALWAYS.internalName;
        this.collisionRule = Team.CollisionRule.ALWAYS.name;
        this.color = TextFormatting.RESET;
        this.players = Lists.newArrayList();
        if (n != 3 && n != 4) {
            throw new IllegalArgumentException("Method must be join or leave for player constructor");
        }
        if (collection == null || collection.isEmpty()) {
            throw new IllegalArgumentException("Players cannot be null/empty");
        }
        this.action = n;
        this.name = scorePlayerTeam.getName();
        this.players.addAll(collection);
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.name = packetBuffer.readString(16);
        this.action = packetBuffer.readByte();
        if (this.action == 0 || this.action == 2) {
            this.displayName = packetBuffer.readTextComponent();
            this.friendlyFlags = packetBuffer.readByte();
            this.nameTagVisibility = packetBuffer.readString(40);
            this.collisionRule = packetBuffer.readString(40);
            this.color = packetBuffer.readEnumValue(TextFormatting.class);
            this.prefix = packetBuffer.readTextComponent();
            this.suffix = packetBuffer.readTextComponent();
        }
        if (this.action == 0 || this.action == 3 || this.action == 4) {
            int n = packetBuffer.readVarInt();
            for (int i = 0; i < n; ++i) {
                this.players.add(packetBuffer.readString(40));
            }
        }
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeString(this.name);
        packetBuffer.writeByte(this.action);
        if (this.action == 0 || this.action == 2) {
            packetBuffer.writeTextComponent(this.displayName);
            packetBuffer.writeByte(this.friendlyFlags);
            packetBuffer.writeString(this.nameTagVisibility);
            packetBuffer.writeString(this.collisionRule);
            packetBuffer.writeEnumValue(this.color);
            packetBuffer.writeTextComponent(this.prefix);
            packetBuffer.writeTextComponent(this.suffix);
        }
        if (this.action == 0 || this.action == 3 || this.action == 4) {
            packetBuffer.writeVarInt(this.players.size());
            for (String string : this.players) {
                packetBuffer.writeString(string);
            }
        }
    }

    @Override
    public void processPacket(IClientPlayNetHandler iClientPlayNetHandler) {
        iClientPlayNetHandler.handleTeams(this);
    }

    public String getName() {
        return this.name;
    }

    public ITextComponent getDisplayName() {
        return this.displayName;
    }

    public Collection<String> getPlayers() {
        return this.players;
    }

    public int getAction() {
        return this.action;
    }

    public int getFriendlyFlags() {
        return this.friendlyFlags;
    }

    public TextFormatting getColor() {
        return this.color;
    }

    public String getNameTagVisibility() {
        return this.nameTagVisibility;
    }

    public String getCollisionRule() {
        return this.collisionRule;
    }

    public ITextComponent getPrefix() {
        return this.prefix;
    }

    public ITextComponent getSuffix() {
        return this.suffix;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IClientPlayNetHandler)iNetHandler);
    }
}

