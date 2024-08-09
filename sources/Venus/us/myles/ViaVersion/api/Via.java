/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package us.myles.ViaVersion.api;

import io.netty.buffer.ByteBuf;
import java.util.SortedSet;
import java.util.UUID;
import us.myles.ViaVersion.api.ViaAPI;
import us.myles.ViaVersion.api.boss.BossBar;
import us.myles.ViaVersion.api.boss.BossColor;
import us.myles.ViaVersion.api.boss.BossStyle;

@Deprecated
public class Via<T>
implements ViaAPI<T> {
    private static final ViaAPI INSTANCE = new Via();

    private Via() {
    }

    @Deprecated
    public static ViaAPI getAPI() {
        return INSTANCE;
    }

    @Override
    public int getPlayerVersion(T t) {
        return com.viaversion.viaversion.api.Via.getAPI().getPlayerVersion(t);
    }

    @Override
    public int getPlayerVersion(UUID uUID) {
        return com.viaversion.viaversion.api.Via.getAPI().getPlayerVersion(uUID);
    }

    @Override
    public boolean isInjected(UUID uUID) {
        return com.viaversion.viaversion.api.Via.getAPI().isInjected(uUID);
    }

    @Override
    public String getVersion() {
        return com.viaversion.viaversion.api.Via.getAPI().getVersion();
    }

    @Override
    public void sendRawPacket(T t, ByteBuf byteBuf) {
        com.viaversion.viaversion.api.Via.getAPI().sendRawPacket(t, byteBuf);
    }

    @Override
    public void sendRawPacket(UUID uUID, ByteBuf byteBuf) {
        com.viaversion.viaversion.api.Via.getAPI().sendRawPacket(uUID, byteBuf);
    }

    @Override
    public BossBar createBossBar(String string, BossColor bossColor, BossStyle bossStyle) {
        return new BossBar(com.viaversion.viaversion.api.Via.getAPI().legacyAPI().createLegacyBossBar(string, com.viaversion.viaversion.api.legacy.bossbar.BossColor.values()[bossColor.ordinal()], com.viaversion.viaversion.api.legacy.bossbar.BossStyle.values()[bossStyle.ordinal()]));
    }

    @Override
    public BossBar createBossBar(String string, float f, BossColor bossColor, BossStyle bossStyle) {
        return new BossBar(com.viaversion.viaversion.api.Via.getAPI().legacyAPI().createLegacyBossBar(string, f, com.viaversion.viaversion.api.legacy.bossbar.BossColor.values()[bossColor.ordinal()], com.viaversion.viaversion.api.legacy.bossbar.BossStyle.values()[bossStyle.ordinal()]));
    }

    @Override
    public SortedSet<Integer> getSupportedVersions() {
        return com.viaversion.viaversion.api.Via.getAPI().getSupportedVersions();
    }

    @Override
    public SortedSet<Integer> getFullSupportedVersions() {
        return com.viaversion.viaversion.api.Via.getAPI().getFullSupportedVersions();
    }
}

