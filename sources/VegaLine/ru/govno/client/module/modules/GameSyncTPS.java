/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module.modules;

import ru.govno.client.module.Module;
import ru.govno.client.module.modules.HitAura;
import ru.govno.client.module.settings.Settings;
import ru.govno.client.utils.Math.MathUtils;
import ru.govno.client.utils.TPSDetect;

public class GameSyncTPS
extends Module {
    public static GameSyncTPS instance;
    public Settings SyncPercent;
    public Settings OnlyAura;

    public GameSyncTPS() {
        super("GameSyncTPS", 0, Module.Category.PLAYER);
        instance = this;
        this.SyncPercent = new Settings("SyncPercent", 0.15f, 1.0f, 0.0f, this);
        this.settings.add(this.SyncPercent);
        this.OnlyAura = new Settings("OnlyAura", true, (Module)this);
        this.settings.add(this.OnlyAura);
    }

    public static double getConpenseMath(double val, float strenghZeroToOne) {
        return val - MathUtils.getDifferenceOf((double)(TPSDetect.getTPSServer() / 20.0f), val) * (double)strenghZeroToOne;
    }

    public static double getGameConpense(double prevTimerSpeed, float percentCompense) {
        if (GameSyncTPS.instance.actived && (!GameSyncTPS.instance.OnlyAura.bValue || HitAura.TARGET != null)) {
            return GameSyncTPS.getConpenseMath(prevTimerSpeed, percentCompense);
        }
        return prevTimerSpeed;
    }

    @Override
    public String getDisplayName() {
        return this.getDisplayByDouble(this.SyncPercent.fValue);
    }
}

