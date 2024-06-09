package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

@ModInfo(Ø­áŒŠá = Category.PLAYER, Ý = 0, Â = "Derp Derp Derp Derp...", HorizonCode_Horizon_È = "Derp")
public class Derp extends Mod
{
    @Handler
    public void HorizonCode_Horizon_È(final EventPacketSend ev) {
        if (ev.Ý() instanceof C03PacketPlayer) {
            final C03PacketPlayer packet = (C03PacketPlayer)ev.Ý();
            if (packet.Ø()) {
                packet.Âµá€ = new Random().nextInt(180);
                packet.Ø­áŒŠá = new Random().nextInt(180);
            }
        }
    }
}
