/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.bridge.launcher;

import com.mojang.bridge.game.GameSession;

public interface SessionEventListener {
    public static final SessionEventListener NONE = new SessionEventListener(){};

    default public void onStartGameSession(GameSession gameSession) {
    }

    default public void onLeaveGameSession(GameSession gameSession) {
    }
}

