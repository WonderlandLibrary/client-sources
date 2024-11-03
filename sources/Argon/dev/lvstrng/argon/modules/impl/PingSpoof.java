package dev.lvstrng.argon.modules.impl;

import dev.lvstrng.argon.event.events.PacketReceiveEvent;
import dev.lvstrng.argon.event.listeners.PacketReceiveListener;
import dev.lvstrng.argon.modules.Category;
import dev.lvstrng.argon.modules.Module;
import dev.lvstrng.argon.modules.setting.Setting;
import dev.lvstrng.argon.modules.setting.settings.BooleanSetting;
import dev.lvstrng.argon.modules.setting.settings.IntSetting;
import dev.lvstrng.argon.utils.RandomUtil;
import net.minecraft.network.packet.c2s.common.KeepAliveC2SPacket;
import net.minecraft.network.packet.s2c.common.KeepAliveS2CPacket;

public final class PingSpoof extends Module implements PacketReceiveListener {
    private final IntSetting targetPingSetting;
    private final BooleanSetting randomPingSetting;

    public PingSpoof() {
        super("Ping Spoof", "Holds back packets making the server think your internet connection is bad.", 0, Category.MISC);
        this.targetPingSetting = new IntSetting("Ping", 0.0, 1000.0, 0.0, 1.0).setDescription("The ping you want to achieve");
        this.randomPingSetting = new BooleanSetting("Random ping", false).setDescription("Random ping ranging from 100 to 600ms");
        this.addSettings(new Setting[]{this.targetPingSetting, this.randomPingSetting});
    }

    @Override
    public void onEnable() {
        this.eventBus.registerPriorityListener(PacketReceiveListener.class, this);
        super.onEnable();
    }

    @Override
    public void onDisable() {
        this.eventBus.unregister(PacketReceiveListener.class, this);
        super.onDisable();
    }

    @Override
    public void onPacketReceive(final PacketReceiveEvent event) {
        if (event.packet instanceof KeepAliveS2CPacket keepalive) {
            new Thread(() -> spoofPing(keepalive)).start();
            event.cancelEvent();
        }
    }

    private void spoofPing(final KeepAliveS2CPacket keepAlivePacket) {
        try {
            int sleepTime = randomPingSetting.getValue()
                    ? RandomUtil.getRandom(100, 600)
                    : targetPingSetting.getValueInt();

            Thread.sleep(sleepTime);
            this.mc.getNetworkHandler().getConnection().send(new KeepAliveC2SPacket(keepAlivePacket.getId()));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}