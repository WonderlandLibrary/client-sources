package io.github.liticane.monoxide.module.impl.misc;

import io.github.liticane.monoxide.module.api.Module;
import io.github.liticane.monoxide.module.api.data.ModuleData;
import io.github.liticane.monoxide.module.api.ModuleCategory;
import io.github.liticane.monoxide.value.impl.NumberValue;
import io.github.liticane.monoxide.listener.event.minecraft.network.PacketEvent;
import io.github.liticane.monoxide.listener.radbus.Listen;
import io.github.liticane.monoxide.util.interfaces.Methods;
import io.github.liticane.monoxide.util.math.random.RandomUtil;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.status.client.C01PacketPing;

@ModuleData(name = "PingSpoof", description = "Spoofs your ping", category = ModuleCategory.MISCELLANEOUS)
public class PingSpoofModule extends Module {
    private NumberValue<Long> minDelay = new NumberValue<>("Minimum Delay", this, 1500L, 0L, 5000L, 0),
            maxDelay = new NumberValue<>("Maximum Delay", this, 2000L, 0L, 5000L, 0);

    @Listen
    public void onPacket(PacketEvent packetEvent) {
    	if(Methods.mc.thePlayer == null || Methods.mc.theWorld == null)
    		return;
        if(packetEvent.getType() == PacketEvent.Type.OUTGOING) {
            final Packet<?> packet = packetEvent.getPacket();
            if (packet instanceof C00PacketKeepAlive || packet instanceof C01PacketPing || packet instanceof C0FPacketConfirmTransaction) {
                packetEvent.setCancelled(true);
                new Thread(() -> {
                    try {
                        Thread.sleep((long) RandomUtil.randomBetween(minDelay.getValue(), maxDelay.getValue()));
                    }
                    catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    Methods.mc.getNetHandler().getNetworkManager().channel.writeAndFlush(packet);
                }).start();
            }
        }
    }

    @Override
    public void onEnable() {}

    @Override
    public void onDisable() {}
}
