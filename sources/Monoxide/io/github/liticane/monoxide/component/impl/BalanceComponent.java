package io.github.liticane.monoxide.component.impl;

import lombok.Getter;
import net.minecraft.network.play.client.C03PacketPlayer;
import io.github.liticane.monoxide.listener.event.minecraft.network.PacketEvent;
import io.github.liticane.monoxide.listener.event.minecraft.world.WorldLoadEvent;
import io.github.liticane.monoxide.listener.radbus.Listen;
import io.github.liticane.monoxide.component.api.Component;
import io.github.liticane.monoxide.component.api.ComponentInfo;

@Getter
@ComponentInfo(name = "BalanceComponent")
public class BalanceComponent extends Component {

    private long balance, lastPacket;

    @Listen
    public void onPacket(PacketEvent e) {
        if (e.getPacket() instanceof C03PacketPlayer) {
            if (lastPacket == 0) lastPacket = System.currentTimeMillis();
            long delay = System.currentTimeMillis() - lastPacket;
            balance += e.isCancelled() ? -delay : 50 - delay;
            lastPacket = System.currentTimeMillis();
        }
    }

    @Listen
    public void onWorldLoad(WorldLoadEvent e) {
        balance = lastPacket = 0;
    }


}
