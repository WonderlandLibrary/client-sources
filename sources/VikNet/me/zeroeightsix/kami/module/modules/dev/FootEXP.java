package me.zeroeightsix.kami.module.modules.dev;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import me.zeroeightsix.kami.event.KamiEvent;
import me.zeroeightsix.kami.event.events.PacketEvent;
import me.zeroeightsix.kami.module.Module;
import net.minecraft.item.ItemExpBottle;
import net.minecraft.network.play.client.CPacketPlayer;

@Module.Info(name = "FootEXP", category = Module.Category.DEV)
public class FootEXP extends Module {

    @Override
    protected void onEnable() {
        super.onEnable();
    }

    @Override
    protected void onDisable() {
        super.onDisable();
    }

    @EventHandler
    public Listener<PacketEvent.Send> sendPacket = new Listener<>(event ->{
        if(event.getEra() == KamiEvent.Era.PRE){
            if(event.getPacket() instanceof CPacketPlayer.Rotation){
                if(mc.player.getHeldItemMainhand().getItem() instanceof ItemExpBottle){
                    ((CPacketPlayer.Rotation) event.getPacket()).pitch = 90.0f;
                }
            }
            if(event.getPacket() instanceof CPacketPlayer.PositionRotation){
                if(mc.player.getHeldItemMainhand().getItem() instanceof ItemExpBottle){
                    ((CPacketPlayer.PositionRotation) event.getPacket()).pitch = 90.0f;
                }
            }
        }
    });
}