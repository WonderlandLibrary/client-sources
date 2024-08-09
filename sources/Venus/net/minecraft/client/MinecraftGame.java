/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client;

import com.mojang.bridge.Bridge;
import com.mojang.bridge.game.GameSession;
import com.mojang.bridge.game.GameVersion;
import com.mojang.bridge.game.Language;
import com.mojang.bridge.game.PerformanceMetrics;
import com.mojang.bridge.game.RunningGame;
import com.mojang.bridge.launcher.Launcher;
import com.mojang.bridge.launcher.SessionEventListener;
import javax.annotation.Nullable;
import net.minecraft.client.ClientGameSession;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.FrameTimer;
import net.minecraft.util.SharedConstants;

public class MinecraftGame
implements RunningGame {
    private final Minecraft gameInstance;
    @Nullable
    private final Launcher launcher;
    private SessionEventListener sessionListener = SessionEventListener.NONE;

    public MinecraftGame(Minecraft minecraft) {
        this.gameInstance = minecraft;
        this.launcher = Bridge.getLauncher();
        if (this.launcher != null) {
            this.launcher.registerGame(this);
        }
    }

    @Override
    public GameVersion getVersion() {
        return SharedConstants.getVersion();
    }

    @Override
    public Language getSelectedLanguage() {
        return this.gameInstance.getLanguageManager().getCurrentLanguage();
    }

    @Override
    @Nullable
    public GameSession getCurrentSession() {
        ClientWorld clientWorld = this.gameInstance.world;
        return clientWorld == null ? null : new ClientGameSession(clientWorld, this.gameInstance.player, this.gameInstance.player.connection);
    }

    @Override
    public PerformanceMetrics getPerformanceMetrics() {
        FrameTimer frameTimer = this.gameInstance.getFrameTimer();
        long l = Integer.MAX_VALUE;
        long l2 = Integer.MIN_VALUE;
        long l3 = 0L;
        for (long l4 : frameTimer.getFrames()) {
            l = Math.min(l, l4);
            l2 = Math.max(l2, l4);
            l3 += l4;
        }
        return new MinecraftPerformanceMetrics((int)l, (int)l2, (int)(l3 / (long)frameTimer.getFrames().length), frameTimer.getFrames().length);
    }

    @Override
    public void setSessionEventListener(SessionEventListener sessionEventListener) {
        this.sessionListener = sessionEventListener;
    }

    public void startGameSession() {
        this.sessionListener.onStartGameSession(this.getCurrentSession());
    }

    public void leaveGameSession() {
        this.sessionListener.onLeaveGameSession(this.getCurrentSession());
    }

    static class MinecraftPerformanceMetrics
    implements PerformanceMetrics {
        private final int minTime;
        private final int maxTime;
        private final int averageTime;
        private final int sampleCount;

        public MinecraftPerformanceMetrics(int n, int n2, int n3, int n4) {
            this.minTime = n;
            this.maxTime = n2;
            this.averageTime = n3;
            this.sampleCount = n4;
        }

        @Override
        public int getMinTime() {
            return this.minTime;
        }

        @Override
        public int getMaxTime() {
            return this.maxTime;
        }

        @Override
        public int getAverageTime() {
            return this.averageTime;
        }

        @Override
        public int getSampleCount() {
            return this.sampleCount;
        }
    }
}

