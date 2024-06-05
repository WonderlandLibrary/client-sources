package Focus.Beta.IMPL.Module.impl.misc;

import Focus.Beta.API.EventHandler;
import Focus.Beta.API.events.world.EventPacketReceive;
import Focus.Beta.API.events.world.EventPreUpdate;
import Focus.Beta.IMPL.Module.Module;
import Focus.Beta.IMPL.Module.Type;
import Focus.Beta.UTILS.world.PacketUtil;
import Focus.Beta.UTILS.world.Timer;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.network.play.server.S02PacketChat;

public class AutoRegister extends Module {
    private boolean A;
    private boolean A2;
    private Timer y = new Timer();
    private String password = "Niggerslikes";
    public AutoRegister(){
        super("AutoRegister", new String[0], Type.MISC, "Automatically Register/login on server");
    }

    @EventHandler
    public void ReceivePacket(EventPacketReceive e){
        if(e.getPacket() instanceof S02PacketChat){
            S02PacketChat packet = (S02PacketChat) e.getPacket();
            String receivedMessage = packet.getChatComponent().getUnformattedText();
            if(receivedMessage.equals("/register <password> <password>")){
                y.reset();
                A = true;
            }
            if(receivedMessage.equals("Wrong password.")){

            }
            if(receivedMessage.equals("/login <password>")){
                y.reset();;
                A2 = true;
            }
        }
    }

    @EventHandler
    public void onUpdate(EventPreUpdate e){
        if(this.mc.thePlayer != null && this.mc.theWorld != null && (this.A2 && this.y.hasElapsed(250, false) || this.A && this.y.hasElapsed(3100, false))){
            if(this.A){
                PacketUtil.sendPacketNoEvent(new C01PacketChatMessage("/register " + this.password + " " + this.password));

                this.A = false;
            }
            if(this.A2){
                PacketUtil.sendPacketNoEvent(new C01PacketChatMessage("/login " + this.password));
                this.A2 = false;
            }
        }
    }
}
