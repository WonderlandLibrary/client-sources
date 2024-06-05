package Focus.Beta.IMPL.Module.impl.misc;

import Focus.Beta.API.EventHandler;
import Focus.Beta.API.events.world.EventPacketReceive;
import Focus.Beta.IMPL.Module.Module;
import Focus.Beta.IMPL.Module.Type;
import Focus.Beta.UTILS.helper.Helper;
import net.minecraft.network.play.server.S29PacketSoundEffect;

public class LightningTracker extends Module {
    public LightningTracker(){
        super("Lightning Tracker", new String[0], Type.MISC, "No");
    }

    @EventHandler
    public void onReceive(EventPacketReceive e){
        if(e.getPacket() instanceof S29PacketSoundEffect){
            S29PacketSoundEffect packetSoundEffect = (S29PacketSoundEffect) e.getPacket();
            if(packetSoundEffect.getSoundName().equals("ambient.weather.thunder")){
                int packetX = (int) packetSoundEffect.getX();
                int packetY = (int) (packetSoundEffect.getY() + (5.0D));
                int packetZ = (int) packetSoundEffect.getZ();
                Helper.sendMessage("Lightning detected " + packetX + " " + packetY + " " + packetZ);
            }
        }
    }
}
