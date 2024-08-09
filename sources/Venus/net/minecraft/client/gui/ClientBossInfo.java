/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui;

import net.minecraft.network.play.server.SUpdateBossInfoPacket;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.BossInfo;

public class ClientBossInfo
extends BossInfo {
    protected float rawPercent;
    protected long percentSetTime;

    public ClientBossInfo(SUpdateBossInfoPacket sUpdateBossInfoPacket) {
        super(sUpdateBossInfoPacket.getUniqueId(), sUpdateBossInfoPacket.getName(), sUpdateBossInfoPacket.getColor(), sUpdateBossInfoPacket.getOverlay());
        this.rawPercent = sUpdateBossInfoPacket.getPercent();
        this.percent = sUpdateBossInfoPacket.getPercent();
        this.percentSetTime = Util.milliTime();
        this.setDarkenSky(sUpdateBossInfoPacket.shouldDarkenSky());
        this.setPlayEndBossMusic(sUpdateBossInfoPacket.shouldPlayEndBossMusic());
        this.setCreateFog(sUpdateBossInfoPacket.shouldCreateFog());
    }

    @Override
    public void setPercent(float f) {
        this.percent = this.getPercent();
        this.rawPercent = f;
        this.percentSetTime = Util.milliTime();
    }

    @Override
    public float getPercent() {
        long l = Util.milliTime() - this.percentSetTime;
        float f = MathHelper.clamp((float)l / 100.0f, 0.0f, 1.0f);
        return MathHelper.lerp(f, this.percent, this.rawPercent);
    }

    public void updateFromPacket(SUpdateBossInfoPacket sUpdateBossInfoPacket) {
        switch (1.$SwitchMap$net$minecraft$network$play$server$SUpdateBossInfoPacket$Operation[sUpdateBossInfoPacket.getOperation().ordinal()]) {
            case 1: {
                this.setName(sUpdateBossInfoPacket.getName());
                break;
            }
            case 2: {
                this.setPercent(sUpdateBossInfoPacket.getPercent());
                break;
            }
            case 3: {
                this.setColor(sUpdateBossInfoPacket.getColor());
                this.setOverlay(sUpdateBossInfoPacket.getOverlay());
                break;
            }
            case 4: {
                this.setDarkenSky(sUpdateBossInfoPacket.shouldDarkenSky());
                this.setPlayEndBossMusic(sUpdateBossInfoPacket.shouldPlayEndBossMusic());
            }
        }
    }
}

