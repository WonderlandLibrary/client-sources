/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.server;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.server.SUpdateBossInfoPacket;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.BossInfo;

public class ServerBossInfo
extends BossInfo {
    private final Set<ServerPlayerEntity> players = Sets.newHashSet();
    private final Set<ServerPlayerEntity> readOnlyPlayers = Collections.unmodifiableSet(this.players);
    private boolean visible = true;

    public ServerBossInfo(ITextComponent iTextComponent, BossInfo.Color color, BossInfo.Overlay overlay) {
        super(MathHelper.getRandomUUID(), iTextComponent, color, overlay);
    }

    @Override
    public void setPercent(float f) {
        if (f != this.percent) {
            super.setPercent(f);
            this.sendUpdate(SUpdateBossInfoPacket.Operation.UPDATE_PCT);
        }
    }

    @Override
    public void setColor(BossInfo.Color color) {
        if (color != this.color) {
            super.setColor(color);
            this.sendUpdate(SUpdateBossInfoPacket.Operation.UPDATE_STYLE);
        }
    }

    @Override
    public void setOverlay(BossInfo.Overlay overlay) {
        if (overlay != this.overlay) {
            super.setOverlay(overlay);
            this.sendUpdate(SUpdateBossInfoPacket.Operation.UPDATE_STYLE);
        }
    }

    @Override
    public BossInfo setDarkenSky(boolean bl) {
        if (bl != this.darkenSky) {
            super.setDarkenSky(bl);
            this.sendUpdate(SUpdateBossInfoPacket.Operation.UPDATE_PROPERTIES);
        }
        return this;
    }

    @Override
    public BossInfo setPlayEndBossMusic(boolean bl) {
        if (bl != this.playEndBossMusic) {
            super.setPlayEndBossMusic(bl);
            this.sendUpdate(SUpdateBossInfoPacket.Operation.UPDATE_PROPERTIES);
        }
        return this;
    }

    @Override
    public BossInfo setCreateFog(boolean bl) {
        if (bl != this.createFog) {
            super.setCreateFog(bl);
            this.sendUpdate(SUpdateBossInfoPacket.Operation.UPDATE_PROPERTIES);
        }
        return this;
    }

    @Override
    public void setName(ITextComponent iTextComponent) {
        if (!Objects.equal(iTextComponent, this.name)) {
            super.setName(iTextComponent);
            this.sendUpdate(SUpdateBossInfoPacket.Operation.UPDATE_NAME);
        }
    }

    private void sendUpdate(SUpdateBossInfoPacket.Operation operation) {
        if (this.visible) {
            SUpdateBossInfoPacket sUpdateBossInfoPacket = new SUpdateBossInfoPacket(operation, this);
            for (ServerPlayerEntity serverPlayerEntity : this.players) {
                serverPlayerEntity.connection.sendPacket(sUpdateBossInfoPacket);
            }
        }
    }

    public void addPlayer(ServerPlayerEntity serverPlayerEntity) {
        if (this.players.add(serverPlayerEntity) && this.visible) {
            serverPlayerEntity.connection.sendPacket(new SUpdateBossInfoPacket(SUpdateBossInfoPacket.Operation.ADD, this));
        }
    }

    public void removePlayer(ServerPlayerEntity serverPlayerEntity) {
        if (this.players.remove(serverPlayerEntity) && this.visible) {
            serverPlayerEntity.connection.sendPacket(new SUpdateBossInfoPacket(SUpdateBossInfoPacket.Operation.REMOVE, this));
        }
    }

    public void removeAllPlayers() {
        if (!this.players.isEmpty()) {
            for (ServerPlayerEntity serverPlayerEntity : Lists.newArrayList(this.players)) {
                this.removePlayer(serverPlayerEntity);
            }
        }
    }

    public boolean isVisible() {
        return this.visible;
    }

    public void setVisible(boolean bl) {
        if (bl != this.visible) {
            this.visible = bl;
            for (ServerPlayerEntity serverPlayerEntity : this.players) {
                serverPlayerEntity.connection.sendPacket(new SUpdateBossInfoPacket(bl ? SUpdateBossInfoPacket.Operation.ADD : SUpdateBossInfoPacket.Operation.REMOVE, this));
            }
        }
    }

    public Collection<ServerPlayerEntity> getPlayers() {
        return this.readOnlyPlayers;
    }
}

