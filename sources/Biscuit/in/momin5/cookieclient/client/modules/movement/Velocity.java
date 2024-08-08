package in.momin5.cookieclient.client.modules.movement;

import in.momin5.cookieclient.CookieClient;
import in.momin5.cookieclient.api.event.events.PacketEvent;
import in.momin5.cookieclient.api.module.Category;
import in.momin5.cookieclient.api.module.Module;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketExplosion;
import net.minecraftforge.common.MinecraftForge;

public class Velocity extends Module {
    public Velocity(){
        super("Velocity", Category.MOVEMENT);
    }

    @Override
    public void onEnable(){
        MinecraftForge.EVENT_BUS.register(this);
        CookieClient.EVENT_BUS.subscribe(this);
    }

    public void onDisable(){
        MinecraftForge.EVENT_BUS.unregister(this);
        CookieClient.EVENT_BUS.unsubscribe(this);
    }

    public void onUpdate(){
    }

    @EventHandler
    private final Listener<PacketEvent.Receive> receiveListener = new Listener<>(event -> {
        if (event.getPacket() instanceof SPacketEntityVelocity){
            if (((SPacketEntityVelocity) event.getPacket()).getEntityID() == mc.player.getEntityId()) {
                event.cancel();
            }
        }
        if (event.getPacket() instanceof SPacketExplosion){
            event.cancel();
        }
    });
}
