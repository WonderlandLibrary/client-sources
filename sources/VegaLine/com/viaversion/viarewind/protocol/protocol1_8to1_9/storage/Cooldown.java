/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viarewind.protocol.protocol1_8to1_9.storage;

import com.viaversion.viarewind.ViaRewind;
import com.viaversion.viarewind.api.ViaRewindConfig;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.Protocol1_8To1_9;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.storage.BlockPlaceDestroyTracker;
import com.viaversion.viarewind.utils.PacketUtil;
import com.viaversion.viarewind.utils.Tickable;
import com.viaversion.viaversion.api.connection.StoredObject;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.gson.JsonPrimitive;
import com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ClientboundPackets1_9;
import com.viaversion.viaversion.util.Pair;
import java.util.ArrayList;
import java.util.UUID;

public class Cooldown
extends StoredObject
implements Tickable {
    private double attackSpeed = 4.0;
    private long lastHit = 0L;
    private final ViaRewindConfig.CooldownIndicator cooldownIndicator;
    private UUID bossUUID;
    private boolean lastSend;
    private static final int max = 10;

    public Cooldown(UserConnection user) {
        super(user);
        ViaRewindConfig.CooldownIndicator indicator;
        try {
            indicator = ViaRewind.getConfig().getCooldownIndicator();
        } catch (IllegalArgumentException e) {
            ViaRewind.getPlatform().getLogger().warning("Invalid cooldown-indicator setting");
            indicator = ViaRewindConfig.CooldownIndicator.DISABLED;
        }
        this.cooldownIndicator = indicator;
    }

    @Override
    public void tick() {
        if (!this.hasCooldown()) {
            if (this.lastSend) {
                this.hide();
                this.lastSend = false;
            }
            return;
        }
        BlockPlaceDestroyTracker tracker = this.getUser().get(BlockPlaceDestroyTracker.class);
        if (tracker.isMining()) {
            this.lastHit = 0L;
            if (this.lastSend) {
                this.hide();
                this.lastSend = false;
            }
            return;
        }
        this.showCooldown();
        this.lastSend = true;
    }

    private void showCooldown() {
        if (this.cooldownIndicator == ViaRewindConfig.CooldownIndicator.TITLE) {
            this.sendTitle("", this.getTitle(), 0, 2, 5);
        } else if (this.cooldownIndicator == ViaRewindConfig.CooldownIndicator.ACTION_BAR) {
            this.sendActionBar(this.getTitle());
        } else if (this.cooldownIndicator == ViaRewindConfig.CooldownIndicator.BOSS_BAR) {
            this.sendBossBar((float)this.getCooldown());
        }
    }

    private void hide() {
        if (this.cooldownIndicator == ViaRewindConfig.CooldownIndicator.ACTION_BAR) {
            this.sendActionBar("\u00a7r");
        } else if (this.cooldownIndicator == ViaRewindConfig.CooldownIndicator.TITLE) {
            this.hideTitle();
        } else if (this.cooldownIndicator == ViaRewindConfig.CooldownIndicator.BOSS_BAR) {
            this.hideBossBar();
        }
    }

    private void hideBossBar() {
        if (this.bossUUID == null) {
            return;
        }
        PacketWrapper wrapper = PacketWrapper.create(ClientboundPackets1_9.BOSSBAR, null, this.getUser());
        wrapper.write(Type.UUID, this.bossUUID);
        wrapper.write(Type.VAR_INT, 1);
        PacketUtil.sendPacket(wrapper, Protocol1_8To1_9.class, false, true);
        this.bossUUID = null;
    }

    private void sendBossBar(float cooldown) {
        PacketWrapper wrapper = PacketWrapper.create(ClientboundPackets1_9.BOSSBAR, this.getUser());
        if (this.bossUUID == null) {
            this.bossUUID = UUID.randomUUID();
            wrapper.write(Type.UUID, this.bossUUID);
            wrapper.write(Type.VAR_INT, 0);
            wrapper.write(Type.COMPONENT, new JsonPrimitive(" "));
            wrapper.write(Type.FLOAT, Float.valueOf(cooldown));
            wrapper.write(Type.VAR_INT, 0);
            wrapper.write(Type.VAR_INT, 0);
            wrapper.write(Type.UNSIGNED_BYTE, (short)0);
        } else {
            wrapper.write(Type.UUID, this.bossUUID);
            wrapper.write(Type.VAR_INT, 2);
            wrapper.write(Type.FLOAT, Float.valueOf(cooldown));
        }
        PacketUtil.sendPacket(wrapper, Protocol1_8To1_9.class, false, true);
    }

    private void hideTitle() {
        PacketWrapper hide = PacketWrapper.create(ClientboundPackets1_8.TITLE, null, this.getUser());
        hide.write(Type.VAR_INT, 3);
        PacketUtil.sendPacket(hide, Protocol1_8To1_9.class);
    }

    private void sendTitle(String title, String subTitle, int fadeIn, int stay, int fadeOut) {
        PacketWrapper timePacket = PacketWrapper.create(ClientboundPackets1_8.TITLE, null, this.getUser());
        timePacket.write(Type.VAR_INT, 2);
        timePacket.write(Type.INT, fadeIn);
        timePacket.write(Type.INT, stay);
        timePacket.write(Type.INT, fadeOut);
        PacketWrapper titlePacket = PacketWrapper.create(ClientboundPackets1_8.TITLE, this.getUser());
        titlePacket.write(Type.VAR_INT, 0);
        titlePacket.write(Type.COMPONENT, new JsonPrimitive(title));
        PacketWrapper subtitlePacket = PacketWrapper.create(ClientboundPackets1_8.TITLE, this.getUser());
        subtitlePacket.write(Type.VAR_INT, 1);
        subtitlePacket.write(Type.COMPONENT, new JsonPrimitive(subTitle));
        PacketUtil.sendPacket(titlePacket, Protocol1_8To1_9.class);
        PacketUtil.sendPacket(subtitlePacket, Protocol1_8To1_9.class);
        PacketUtil.sendPacket(timePacket, Protocol1_8To1_9.class);
    }

    private void sendActionBar(String bar) {
        PacketWrapper actionBarPacket = PacketWrapper.create(ClientboundPackets1_8.CHAT_MESSAGE, this.getUser());
        actionBarPacket.write(Type.COMPONENT, new JsonPrimitive(bar));
        actionBarPacket.write(Type.BYTE, (byte)2);
        PacketUtil.sendPacket(actionBarPacket, Protocol1_8To1_9.class);
    }

    public boolean hasCooldown() {
        long time = System.currentTimeMillis() - this.lastHit;
        double cooldown = this.restrain((double)time * this.attackSpeed / 1000.0, 0.0, 1.5);
        return cooldown > 0.1 && cooldown < 1.1;
    }

    public double getCooldown() {
        long time = System.currentTimeMillis() - this.lastHit;
        return this.restrain((double)time * this.attackSpeed / 1000.0, 0.0, 1.0);
    }

    private double restrain(double x, double a, double b) {
        if (x < a) {
            return a;
        }
        return Math.min(x, b);
    }

    private String getTitle() {
        String symbol = this.cooldownIndicator == ViaRewindConfig.CooldownIndicator.ACTION_BAR ? "\u25a0" : "\u02d9";
        double cooldown = this.getCooldown();
        int green = (int)Math.floor(10.0 * cooldown);
        int grey = 10 - green;
        StringBuilder builder = new StringBuilder("\u00a78");
        while (green-- > 0) {
            builder.append(symbol);
        }
        builder.append("\u00a77");
        while (grey-- > 0) {
            builder.append(symbol);
        }
        return builder.toString();
    }

    public double getAttackSpeed() {
        return this.attackSpeed;
    }

    public void setAttackSpeed(double attackSpeed) {
        this.attackSpeed = attackSpeed;
    }

    public void setAttackSpeed(double base, ArrayList<Pair<Byte, Double>> modifiers) {
        int j;
        this.attackSpeed = base;
        for (j = 0; j < modifiers.size(); ++j) {
            if (modifiers.get(j).key() != 0) continue;
            this.attackSpeed += modifiers.get(j).value().doubleValue();
            modifiers.remove(j--);
        }
        for (j = 0; j < modifiers.size(); ++j) {
            if (modifiers.get(j).key() != 1) continue;
            this.attackSpeed += base * modifiers.get(j).value();
            modifiers.remove(j--);
        }
        for (j = 0; j < modifiers.size(); ++j) {
            if (modifiers.get(j).key() != 2) continue;
            this.attackSpeed *= 1.0 + modifiers.get(j).value();
            modifiers.remove(j--);
        }
    }

    public void hit() {
        this.lastHit = System.currentTimeMillis();
    }

    public void setLastHit(long lastHit) {
        this.lastHit = lastHit;
    }
}

