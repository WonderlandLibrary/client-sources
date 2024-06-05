package Ascii4UwUWareClient.Module.Modules.Move;

import Ascii4UwUWareClient.API.EventHandler;
import Ascii4UwUWareClient.API.Value.Mode;
import Ascii4UwUWareClient.Module.Module;
import Ascii4UwUWareClient.Module.ModuleType;
import Ascii4UwUWareClient.Util.Helper;
import Ascii4UwUWareClient.Util.TimerUtil;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Phase extends Module {
    double yaw;
    double oldX;
    double oldZ;
    double d4;
    double d5;
    TimerUtil timer = new TimerUtil ();
    public Mode <Enum> mode = new Mode ( "Mode", "Mode", Phase.PhaseMode.values (),PhaseMode.Redesky );

    public Phase() {
        super("Phase", new String[]{"Phase", "Phase"}, ModuleType.Move);
    }
@Override
@EventHandler
    public void onEnable() {
        setSuffix ( mode.getModeAsString () );
        switch ( mode.getModeAsString ()) {

            case"Redesky":
            if (mc.thePlayer.isCollidedHorizontally) {
                mc.thePlayer.sendQueue.addToSendQueue ( new C03PacketPlayer.C06PacketPlayerPosLook ( mc.thePlayer.posX, mc.thePlayer.posY - 0.00000001, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, false ) );
                mc.thePlayer.sendQueue.addToSendQueue ( new C03PacketPlayer.C06PacketPlayerPosLook ( mc.thePlayer.posX, mc.thePlayer.posY - 0.000001, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, false ) );
            } else {
                Helper.sendMessage ( "Please sand close to Blocks for Phase to work" );
            }
            break;
        }
    }
    public enum PhaseMode{
        Redesky,
    }
    }
