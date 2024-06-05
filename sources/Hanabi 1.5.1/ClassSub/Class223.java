package ClassSub;

import cn.Hanabi.modules.Combat.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;

class Class223 extends Thread
{
    final TPHit this$0;
    
    
    Class223(final TPHit this$0) {
        this.this$0 = this$0;
    }
    
    @Override
    public void run() {
        try {
            Thread.sleep(1430L);
        }
        catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        TPHit.access$4300().getNetHandler().getNetworkManager().sendPacket((Packet)new C03PacketPlayer.C04PacketPlayerPosition(TPHit.access$4000().thePlayer.posX + 0.08, TPHit.access$4100().thePlayer.posY, TPHit.access$4200().thePlayer.posZ, false));
    }
}
