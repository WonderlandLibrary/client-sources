/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.scoreboard;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SDisplayObjectivePacket;
import net.minecraft.network.play.server.SScoreboardObjectivePacket;
import net.minecraft.network.play.server.STeamsPacket;
import net.minecraft.network.play.server.SUpdateScorePacket;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.server.MinecraftServer;

public class ServerScoreboard
extends Scoreboard {
    private final MinecraftServer server;
    private final Set<ScoreObjective> addedObjectives = Sets.newHashSet();
    private Runnable[] dirtyRunnables = new Runnable[0];

    public ServerScoreboard(MinecraftServer minecraftServer) {
        this.server = minecraftServer;
    }

    @Override
    public void onScoreChanged(Score score) {
        super.onScoreChanged(score);
        if (this.addedObjectives.contains(score.getObjective())) {
            this.server.getPlayerList().sendPacketToAllPlayers(new SUpdateScorePacket(Action.CHANGE, score.getObjective().getName(), score.getPlayerName(), score.getScorePoints()));
        }
        this.markSaveDataDirty();
    }

    @Override
    public void onPlayerRemoved(String string) {
        super.onPlayerRemoved(string);
        this.server.getPlayerList().sendPacketToAllPlayers(new SUpdateScorePacket(Action.REMOVE, null, string, 0));
        this.markSaveDataDirty();
    }

    @Override
    public void onPlayerScoreRemoved(String string, ScoreObjective scoreObjective) {
        super.onPlayerScoreRemoved(string, scoreObjective);
        if (this.addedObjectives.contains(scoreObjective)) {
            this.server.getPlayerList().sendPacketToAllPlayers(new SUpdateScorePacket(Action.REMOVE, scoreObjective.getName(), string, 0));
        }
        this.markSaveDataDirty();
    }

    @Override
    public void setObjectiveInDisplaySlot(int n, @Nullable ScoreObjective scoreObjective) {
        ScoreObjective scoreObjective2 = this.getObjectiveInDisplaySlot(n);
        super.setObjectiveInDisplaySlot(n, scoreObjective);
        if (scoreObjective2 != scoreObjective && scoreObjective2 != null) {
            if (this.getObjectiveDisplaySlotCount(scoreObjective2) > 0) {
                this.server.getPlayerList().sendPacketToAllPlayers(new SDisplayObjectivePacket(n, scoreObjective));
            } else {
                this.sendDisplaySlotRemovalPackets(scoreObjective2);
            }
        }
        if (scoreObjective != null) {
            if (this.addedObjectives.contains(scoreObjective)) {
                this.server.getPlayerList().sendPacketToAllPlayers(new SDisplayObjectivePacket(n, scoreObjective));
            } else {
                this.addObjective(scoreObjective);
            }
        }
        this.markSaveDataDirty();
    }

    @Override
    public boolean addPlayerToTeam(String string, ScorePlayerTeam scorePlayerTeam) {
        if (super.addPlayerToTeam(string, scorePlayerTeam)) {
            this.server.getPlayerList().sendPacketToAllPlayers(new STeamsPacket(scorePlayerTeam, Arrays.asList(string), 3));
            this.markSaveDataDirty();
            return false;
        }
        return true;
    }

    @Override
    public void removePlayerFromTeam(String string, ScorePlayerTeam scorePlayerTeam) {
        super.removePlayerFromTeam(string, scorePlayerTeam);
        this.server.getPlayerList().sendPacketToAllPlayers(new STeamsPacket(scorePlayerTeam, Arrays.asList(string), 4));
        this.markSaveDataDirty();
    }

    @Override
    public void onObjectiveAdded(ScoreObjective scoreObjective) {
        super.onObjectiveAdded(scoreObjective);
        this.markSaveDataDirty();
    }

    @Override
    public void onObjectiveChanged(ScoreObjective scoreObjective) {
        super.onObjectiveChanged(scoreObjective);
        if (this.addedObjectives.contains(scoreObjective)) {
            this.server.getPlayerList().sendPacketToAllPlayers(new SScoreboardObjectivePacket(scoreObjective, 2));
        }
        this.markSaveDataDirty();
    }

    @Override
    public void onObjectiveRemoved(ScoreObjective scoreObjective) {
        super.onObjectiveRemoved(scoreObjective);
        if (this.addedObjectives.contains(scoreObjective)) {
            this.sendDisplaySlotRemovalPackets(scoreObjective);
        }
        this.markSaveDataDirty();
    }

    @Override
    public void onTeamAdded(ScorePlayerTeam scorePlayerTeam) {
        super.onTeamAdded(scorePlayerTeam);
        this.server.getPlayerList().sendPacketToAllPlayers(new STeamsPacket(scorePlayerTeam, 0));
        this.markSaveDataDirty();
    }

    @Override
    public void onTeamChanged(ScorePlayerTeam scorePlayerTeam) {
        super.onTeamChanged(scorePlayerTeam);
        this.server.getPlayerList().sendPacketToAllPlayers(new STeamsPacket(scorePlayerTeam, 2));
        this.markSaveDataDirty();
    }

    @Override
    public void onTeamRemoved(ScorePlayerTeam scorePlayerTeam) {
        super.onTeamRemoved(scorePlayerTeam);
        this.server.getPlayerList().sendPacketToAllPlayers(new STeamsPacket(scorePlayerTeam, 1));
        this.markSaveDataDirty();
    }

    public void addDirtyRunnable(Runnable runnable) {
        this.dirtyRunnables = Arrays.copyOf(this.dirtyRunnables, this.dirtyRunnables.length + 1);
        this.dirtyRunnables[this.dirtyRunnables.length - 1] = runnable;
    }

    protected void markSaveDataDirty() {
        for (Runnable runnable : this.dirtyRunnables) {
            runnable.run();
        }
    }

    public List<IPacket<?>> getCreatePackets(ScoreObjective scoreObjective) {
        ArrayList<IPacket<?>> arrayList = Lists.newArrayList();
        arrayList.add(new SScoreboardObjectivePacket(scoreObjective, 0));
        for (int i = 0; i < 19; ++i) {
            if (this.getObjectiveInDisplaySlot(i) != scoreObjective) continue;
            arrayList.add(new SDisplayObjectivePacket(i, scoreObjective));
        }
        for (Score score : this.getSortedScores(scoreObjective)) {
            arrayList.add(new SUpdateScorePacket(Action.CHANGE, score.getObjective().getName(), score.getPlayerName(), score.getScorePoints()));
        }
        return arrayList;
    }

    public void addObjective(ScoreObjective scoreObjective) {
        List<IPacket<?>> list = this.getCreatePackets(scoreObjective);
        for (ServerPlayerEntity serverPlayerEntity : this.server.getPlayerList().getPlayers()) {
            for (IPacket<?> iPacket : list) {
                serverPlayerEntity.connection.sendPacket(iPacket);
            }
        }
        this.addedObjectives.add(scoreObjective);
    }

    public List<IPacket<?>> getDestroyPackets(ScoreObjective scoreObjective) {
        ArrayList<IPacket<?>> arrayList = Lists.newArrayList();
        arrayList.add(new SScoreboardObjectivePacket(scoreObjective, 1));
        for (int i = 0; i < 19; ++i) {
            if (this.getObjectiveInDisplaySlot(i) != scoreObjective) continue;
            arrayList.add(new SDisplayObjectivePacket(i, scoreObjective));
        }
        return arrayList;
    }

    public void sendDisplaySlotRemovalPackets(ScoreObjective scoreObjective) {
        List<IPacket<?>> list = this.getDestroyPackets(scoreObjective);
        for (ServerPlayerEntity serverPlayerEntity : this.server.getPlayerList().getPlayers()) {
            for (IPacket<?> iPacket : list) {
                serverPlayerEntity.connection.sendPacket(iPacket);
            }
        }
        this.addedObjectives.remove(scoreObjective);
    }

    public int getObjectiveDisplaySlotCount(ScoreObjective scoreObjective) {
        int n = 0;
        for (int i = 0; i < 19; ++i) {
            if (this.getObjectiveInDisplaySlot(i) != scoreObjective) continue;
            ++n;
        }
        return n;
    }

    public static enum Action {
        CHANGE,
        REMOVE;

    }
}

