package Focus.Beta.IMPL.Module.impl.move;

import Focus.Beta.API.EventHandler;
import Focus.Beta.API.events.world.EventPacketSend;
import Focus.Beta.API.events.world.EventPreUpdate;
import Focus.Beta.IMPL.Module.Module;
import Focus.Beta.IMPL.Module.Type;
import Focus.Beta.IMPL.set.Mode;
import net.minecraft.network.play.client.C03PacketPlayer;

public class NoFall extends Module {
    private boolean needSpoof = false;
    private boolean packetModify = false;
    private int packet1Count = 0;

    Mode<Enum> modes = new Mode<>("Modes", "Mode", Modes.values(), Modes.Vanilla);
    public NoFall(){
        super("NoFall", new String[0], Type.MOVE, "No fall damage");
        this.addValues(modes);
    }

    @EventHandler
    public void onUpdate(EventPreUpdate e){
        setSuffix(modes.getModeAsString());
        switch (modes.getModeAsString()){
            case "Verus":
                if (mc.thePlayer.fallDistance - mc.thePlayer.motionY > 3) {
                    mc.thePlayer.motionY = 0.0;
                    mc.thePlayer.fallDistance = 0.0f;
                    mc.thePlayer.motionX *= 0.6;
                    mc.thePlayer.motionZ *= 0.6;
                    needSpoof = true;
                }

                if (mc.thePlayer.fallDistance / 3 > packet1Count) {
                    packet1Count = (int) (mc.thePlayer.fallDistance / 3);
                    packetModify = true;
                }
                if (mc.thePlayer.onGround) {
                    packet1Count = 0;
                }
                break;
        }
    }

    @EventHandler
    public void onPacketSend(EventPacketSend e){
        if(e.getPacket() instanceof C03PacketPlayer){
            switch (modes.getModeAsString()){
                case "Verus":
                    if(needSpoof){
                        ((C03PacketPlayer) e.getPacket()).onGround = true;
                        needSpoof = false;
                    }
                    break;
            }
        }
    }
    @Override
    public void onEnable(){
        needSpoof = false;
        packetModify = false;
        packet1Count = 0;
    }
    enum Modes{
        Verus, Vanilla
    }
}
