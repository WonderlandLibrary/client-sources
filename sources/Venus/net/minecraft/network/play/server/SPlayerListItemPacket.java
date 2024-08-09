/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.server;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import java.io.IOException;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.GameType;

public class SPlayerListItemPacket
implements IPacket<IClientPlayNetHandler> {
    private Action action;
    private final List<AddPlayerData> players = Lists.newArrayList();

    public SPlayerListItemPacket() {
    }

    public SPlayerListItemPacket(Action action, ServerPlayerEntity ... serverPlayerEntityArray) {
        this.action = action;
        for (ServerPlayerEntity serverPlayerEntity : serverPlayerEntityArray) {
            this.players.add(new AddPlayerData(serverPlayerEntity.getGameProfile(), serverPlayerEntity.ping, serverPlayerEntity.interactionManager.getGameType(), serverPlayerEntity.getTabListDisplayName()));
        }
    }

    public SPlayerListItemPacket(Action action, Iterable<ServerPlayerEntity> iterable) {
        this.action = action;
        for (ServerPlayerEntity serverPlayerEntity : iterable) {
            this.players.add(new AddPlayerData(serverPlayerEntity.getGameProfile(), serverPlayerEntity.ping, serverPlayerEntity.interactionManager.getGameType(), serverPlayerEntity.getTabListDisplayName()));
        }
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.action = packetBuffer.readEnumValue(Action.class);
        int n = packetBuffer.readVarInt();
        for (int i = 0; i < n; ++i) {
            GameProfile gameProfile = null;
            int n2 = 0;
            GameType gameType = null;
            ITextComponent iTextComponent = null;
            switch (1.$SwitchMap$net$minecraft$network$play$server$SPlayerListItemPacket$Action[this.action.ordinal()]) {
                case 1: {
                    gameProfile = new GameProfile(packetBuffer.readUniqueId(), packetBuffer.readString(16));
                    int n3 = packetBuffer.readVarInt();
                    for (int j = 0; j < n3; ++j) {
                        String string = packetBuffer.readString(Short.MAX_VALUE);
                        String string2 = packetBuffer.readString(Short.MAX_VALUE);
                        if (packetBuffer.readBoolean()) {
                            gameProfile.getProperties().put(string, new Property(string, string2, packetBuffer.readString(Short.MAX_VALUE)));
                            continue;
                        }
                        gameProfile.getProperties().put(string, new Property(string, string2));
                    }
                    gameType = GameType.getByID(packetBuffer.readVarInt());
                    n2 = packetBuffer.readVarInt();
                    if (!packetBuffer.readBoolean()) break;
                    iTextComponent = packetBuffer.readTextComponent();
                    break;
                }
                case 2: {
                    gameProfile = new GameProfile(packetBuffer.readUniqueId(), null);
                    gameType = GameType.getByID(packetBuffer.readVarInt());
                    break;
                }
                case 3: {
                    gameProfile = new GameProfile(packetBuffer.readUniqueId(), null);
                    n2 = packetBuffer.readVarInt();
                    break;
                }
                case 4: {
                    gameProfile = new GameProfile(packetBuffer.readUniqueId(), null);
                    if (!packetBuffer.readBoolean()) break;
                    iTextComponent = packetBuffer.readTextComponent();
                    break;
                }
                case 5: {
                    gameProfile = new GameProfile(packetBuffer.readUniqueId(), null);
                }
            }
            this.players.add(new AddPlayerData(gameProfile, n2, gameType, iTextComponent));
        }
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeEnumValue(this.action);
        packetBuffer.writeVarInt(this.players.size());
        for (AddPlayerData addPlayerData : this.players) {
            switch (1.$SwitchMap$net$minecraft$network$play$server$SPlayerListItemPacket$Action[this.action.ordinal()]) {
                case 1: {
                    packetBuffer.writeUniqueId(addPlayerData.getProfile().getId());
                    packetBuffer.writeString(addPlayerData.getProfile().getName());
                    packetBuffer.writeVarInt(addPlayerData.getProfile().getProperties().size());
                    for (Property property : addPlayerData.getProfile().getProperties().values()) {
                        packetBuffer.writeString(property.getName());
                        packetBuffer.writeString(property.getValue());
                        if (property.hasSignature()) {
                            packetBuffer.writeBoolean(false);
                            packetBuffer.writeString(property.getSignature());
                            continue;
                        }
                        packetBuffer.writeBoolean(true);
                    }
                    packetBuffer.writeVarInt(addPlayerData.getGameMode().getID());
                    packetBuffer.writeVarInt(addPlayerData.getPing());
                    if (addPlayerData.getDisplayName() == null) {
                        packetBuffer.writeBoolean(true);
                        break;
                    }
                    packetBuffer.writeBoolean(false);
                    packetBuffer.writeTextComponent(addPlayerData.getDisplayName());
                    break;
                }
                case 2: {
                    packetBuffer.writeUniqueId(addPlayerData.getProfile().getId());
                    packetBuffer.writeVarInt(addPlayerData.getGameMode().getID());
                    break;
                }
                case 3: {
                    packetBuffer.writeUniqueId(addPlayerData.getProfile().getId());
                    packetBuffer.writeVarInt(addPlayerData.getPing());
                    break;
                }
                case 4: {
                    packetBuffer.writeUniqueId(addPlayerData.getProfile().getId());
                    if (addPlayerData.getDisplayName() == null) {
                        packetBuffer.writeBoolean(true);
                        break;
                    }
                    packetBuffer.writeBoolean(false);
                    packetBuffer.writeTextComponent(addPlayerData.getDisplayName());
                    break;
                }
                case 5: {
                    packetBuffer.writeUniqueId(addPlayerData.getProfile().getId());
                }
            }
        }
    }

    @Override
    public void processPacket(IClientPlayNetHandler iClientPlayNetHandler) {
        iClientPlayNetHandler.handlePlayerListItem(this);
    }

    public List<AddPlayerData> getEntries() {
        return this.players;
    }

    public Action getAction() {
        return this.action;
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("action", (Object)this.action).add("entries", this.players).toString();
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IClientPlayNetHandler)iNetHandler);
    }

    public static enum Action {
        ADD_PLAYER,
        UPDATE_GAME_MODE,
        UPDATE_LATENCY,
        UPDATE_DISPLAY_NAME,
        REMOVE_PLAYER;

    }

    public static class AddPlayerData {
        private final int ping;
        private final GameType gamemode;
        private final GameProfile profile;
        private final ITextComponent displayName;

        public AddPlayerData(GameProfile gameProfile, int n, @Nullable GameType gameType, @Nullable ITextComponent iTextComponent) {
            this.profile = gameProfile;
            this.ping = n;
            this.gamemode = gameType;
            this.displayName = iTextComponent;
        }

        public GameProfile getProfile() {
            return this.profile;
        }

        public int getPing() {
            return this.ping;
        }

        public GameType getGameMode() {
            return this.gamemode;
        }

        @Nullable
        public ITextComponent getDisplayName() {
            return this.displayName;
        }

        public String toString() {
            return MoreObjects.toStringHelper(this).add("latency", this.ping).add("gameMode", (Object)this.gamemode).add("profile", this.profile).add("displayName", this.displayName == null ? null : ITextComponent.Serializer.toJson(this.displayName)).toString();
        }
    }
}

