package Focus.Beta.IMPL.Module.impl.move;

import Focus.Beta.API.EventHandler;
import Focus.Beta.API.events.world.EventPreUpdate;
import Focus.Beta.IMPL.Module.Module;
import Focus.Beta.IMPL.Module.Type;
import Focus.Beta.IMPL.set.Mode;
import Focus.Beta.UTILS.helper.Helper;
import Focus.Beta.UTILS.world.MovementUtil;
import Focus.Beta.UTILS.world.Timer;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C03PacketPlayer;

public class HighJump extends Module {
    public Mode<Enum> mode = new Mode<>("Mode", "Mode", Modes.values(), Modes.Verus);
        public Mode<Enum> verusMode = new Mode<>("Verus Mode", "Verus Mode", VerusMode.values(), VerusMode.Packet);
    
    
    //Verus
    public boolean damagedPacket = false;
    public boolean canJump = false;
    public Timer packettimer = new Timer();
    public Timer packettimer2 = new Timer();

    
    public HighJump(){
        super("HighJump", new String[0], Type.MOVE, "No");
        this.addValues(mode, verusMode);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        this.damagedPacket = false;
        this.canJump = false;
    }

    @Override
    public void onDisable(){
        super.onDisable();
        this.packettimer.reset();
        this.packettimer2.reset();
    }
    
    @EventHandler
    public void onEvent(EventPreUpdate e){
        switch (mode.getModeAsString()){
            case "Verus":
                if(verusMode.getModeAsString().equalsIgnoreCase("Packet")) {
                    if (!damagedPacket && packettimer.hasElapsed(1000, false)) {

                        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 3.0001, mc.thePlayer.posZ, false));
                        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
                        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));
                    }

                    if(mc.thePlayer.hurtTime > 0){
                            mc.thePlayer.motionY += 0.16f;
                        damagedPacket = true;
                    }
                }
                break;
        }
    }

    
    enum VerusMode{
        Bow, Packet
    }
    enum Modes{
        Verus
    }
}
