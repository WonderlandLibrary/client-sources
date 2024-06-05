package net.shoreline.client.impl.module.movement;

import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.common.KeepAliveC2SPacket;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import net.minecraft.network.packet.c2s.play.ClientStatusC2SPacket;
import net.minecraft.network.packet.c2s.play.TeleportConfirmC2SPacket;
import net.shoreline.client.api.config.Config;
import net.shoreline.client.api.config.setting.BooleanConfig;
import net.shoreline.client.api.config.setting.EnumConfig;
import net.shoreline.client.api.event.EventStage;
import net.shoreline.client.api.event.listener.EventListener;
import net.shoreline.client.api.module.ModuleCategory;
import net.shoreline.client.api.module.ToggleModule;
import net.shoreline.client.impl.event.TickEvent;
import net.shoreline.client.impl.event.network.DisconnectEvent;
import net.shoreline.client.impl.event.network.PacketEvent;
import net.shoreline.client.util.world.FakePlayerEntity;

import java.util.HashSet;
import java.util.Set;

/**
 * @author linus
 * @since 1.0
 */
public class FakeLagModule extends ToggleModule {

    //
    Config<LagMode> modeConfig = new EnumConfig<>("Mode", "The mode for caching packets", LagMode.BLINK, LagMode.values());
    Config<Boolean> renderConfig = new BooleanConfig("Render", "Renders the serverside player postion", true);
    //
    private FakePlayerEntity serverModel;
    //
    private final Set<Packet<?>> packets = new HashSet<>();

    /**
     *
     */
    public FakeLagModule() {
        super("FakeLag", "Withholds packets from the server, creating clientside lag", ModuleCategory.MOVEMENT);
    }

    @Override
    public void onEnable() {
        if (renderConfig.getValue()) {
            serverModel = new FakePlayerEntity(mc.player);
            serverModel.spawnPlayer();
        }
    }

    @Override
    public void onDisable() {
        if (mc.player == null) {
            return;
        }
        if (!packets.isEmpty()) {
            for (Packet<?> p : packets) {
                mc.player.networkHandler.sendPacket(p);
            }
            packets.clear();
        }
        if (serverModel != null) {
            serverModel.despawnPlayer();
        }
    }

    @EventListener
    public void onTick(TickEvent event) {
        if (event.getStage() == EventStage.PRE) {
            // packets.add()
        }
    }

    @EventListener
    public void onDisconnectEvent(DisconnectEvent event) {
        // packets.clear();
        disable();
    }

    @EventListener
    public void onPacketOutbound(PacketEvent.Outbound event) {
        if (mc.player == null || mc.player.isRiding()) {
            return;
        }
        if (!(event.getPacket() instanceof ChatMessageC2SPacket
                || event.getPacket() instanceof TeleportConfirmC2SPacket
                || event.getPacket() instanceof KeepAliveC2SPacket
                || event.getPacket() instanceof ClientStatusC2SPacket)) {
            event.cancel();
            packets.add(event.getPacket());
        }
    }

    public enum LagMode {
        BLINK
    }
}
