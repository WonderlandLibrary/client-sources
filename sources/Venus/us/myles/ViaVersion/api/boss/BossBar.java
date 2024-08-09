/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package us.myles.ViaVersion.api.boss;

import java.util.Set;
import java.util.UUID;
import us.myles.ViaVersion.api.boss.BossColor;
import us.myles.ViaVersion.api.boss.BossFlag;
import us.myles.ViaVersion.api.boss.BossStyle;

@Deprecated
public class BossBar<T> {
    private final com.viaversion.viaversion.api.legacy.bossbar.BossBar bossBar;

    public BossBar(com.viaversion.viaversion.api.legacy.bossbar.BossBar bossBar) {
        this.bossBar = bossBar;
    }

    public String getTitle() {
        return this.bossBar.getTitle();
    }

    public BossBar setTitle(String string) {
        this.bossBar.setTitle(string);
        return this;
    }

    public float getHealth() {
        return this.bossBar.getHealth();
    }

    public BossBar setHealth(float f) {
        this.bossBar.setHealth(f);
        return this;
    }

    public BossColor getColor() {
        return BossColor.values()[this.bossBar.getColor().ordinal()];
    }

    public BossBar setColor(BossColor bossColor) {
        this.bossBar.setColor(com.viaversion.viaversion.api.legacy.bossbar.BossColor.values()[bossColor.ordinal()]);
        return this;
    }

    public BossStyle getStyle() {
        return BossStyle.values()[this.bossBar.getStyle().ordinal()];
    }

    public BossBar setStyle(BossStyle bossStyle) {
        this.bossBar.setStyle(com.viaversion.viaversion.api.legacy.bossbar.BossStyle.values()[bossStyle.ordinal()]);
        return this;
    }

    @Deprecated
    public BossBar addPlayer(T t) {
        return this;
    }

    public BossBar addPlayer(UUID uUID) {
        this.bossBar.addPlayer(uUID);
        return this;
    }

    @Deprecated
    public BossBar addPlayers(T ... TArray) {
        return this;
    }

    @Deprecated
    public BossBar removePlayer(T t) {
        return this;
    }

    public BossBar removePlayer(UUID uUID) {
        this.bossBar.removePlayer(uUID);
        return this;
    }

    public BossBar addFlag(BossFlag bossFlag) {
        this.bossBar.addFlag(com.viaversion.viaversion.api.legacy.bossbar.BossFlag.values()[bossFlag.ordinal()]);
        return this;
    }

    public BossBar removeFlag(BossFlag bossFlag) {
        this.bossBar.removeFlag(com.viaversion.viaversion.api.legacy.bossbar.BossFlag.values()[bossFlag.ordinal()]);
        return this;
    }

    public boolean hasFlag(BossFlag bossFlag) {
        return this.bossBar.hasFlag(com.viaversion.viaversion.api.legacy.bossbar.BossFlag.values()[bossFlag.ordinal()]);
    }

    public Set<UUID> getPlayers() {
        return this.bossBar.getPlayers();
    }

    public BossBar show() {
        this.bossBar.show();
        return this;
    }

    public BossBar hide() {
        this.bossBar.hide();
        return this;
    }

    public boolean isVisible() {
        return this.bossBar.isVisible();
    }

    public UUID getId() {
        return this.bossBar.getId();
    }
}

