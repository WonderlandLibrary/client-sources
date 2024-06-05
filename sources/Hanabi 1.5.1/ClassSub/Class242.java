package ClassSub;

import cn.Hanabi.events.*;
import cn.Hanabi.modules.Combat.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.entity.player.*;

class Class242 extends Thread
{
    final EventAttack val$e;
    final TPHit this$0;
    
    
    Class242(final TPHit this$0, final EventAttack val$e) {
        this.this$0 = this$0;
        this.val$e = val$e;
    }
    
    @Override
    public void run() {
        try {
            Thread.sleep(10L);
        }
        catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        final int getX = this.val$e.getEntity().getPosition().getX();
        final int getY = this.val$e.getEntity().getPosition().getY();
        final int getZ = this.val$e.getEntity().getPosition().getZ();
        final double n = getX - TPHit.access$000().thePlayer.posX + 0.5;
        final double n2 = getY - TPHit.access$100().thePlayer.posY + 0.08;
        final double n3 = getZ - TPHit.access$200().thePlayer.posZ + 0.5;
        final double ceil = Math.ceil(Math.sqrt(Math.pow(n, 2.0) + Math.pow(n2, 2.0) + Math.pow(n3, 2.0)) / 9.8);
        TPHit.access$600().getNetHandler().getNetworkManager().sendPacket((Packet)new C03PacketPlayer.C04PacketPlayerPosition(TPHit.access$300().thePlayer.posX, TPHit.access$400().thePlayer.posY - 0.32, TPHit.access$500().thePlayer.posZ, false));
        TPHit.access$1000().getNetHandler().getNetworkManager().sendPacket((Packet)new C03PacketPlayer.C04PacketPlayerPosition(TPHit.access$700().thePlayer.posX, TPHit.access$800().thePlayer.posY - 0.32, TPHit.access$900().thePlayer.posZ, false));
        TPHit.access$1400().getNetHandler().getNetworkManager().sendPacket((Packet)new C03PacketPlayer.C04PacketPlayerPosition(TPHit.access$1100().thePlayer.posX, TPHit.access$1200().thePlayer.posY, TPHit.access$1300().thePlayer.posZ, false));
        TPHit.access$1800().getNetHandler().getNetworkManager().sendPacket((Packet)new C03PacketPlayer.C04PacketPlayerPosition(TPHit.access$1500().thePlayer.posX, TPHit.access$1600().thePlayer.posY, TPHit.access$1700().thePlayer.posZ, false));
        TPHit.access$2200().getNetHandler().getNetworkManager().sendPacket((Packet)new C03PacketPlayer.C04PacketPlayerPosition(TPHit.access$1900().thePlayer.posX, TPHit.access$2000().thePlayer.posY + 1.1, TPHit.access$2100().thePlayer.posZ, false));
        TPHit.access$2600().getNetHandler().getNetworkManager().sendPacket((Packet)new C03PacketPlayer.C04PacketPlayerPosition(TPHit.access$2300().thePlayer.posX, TPHit.access$2400().thePlayer.posY + 1.1, TPHit.access$2500().thePlayer.posZ, false));
        for (int n4 = 1; n4 <= ceil; ++n4) {
            TPHit.access$3000().getNetHandler().getNetworkManager().sendPacket((Packet)new C03PacketPlayer.C04PacketPlayerPosition(TPHit.access$2700().thePlayer.posX + n / ceil * n4, TPHit.access$2800().thePlayer.posY + n2 / ceil * n4, TPHit.access$2900().thePlayer.posZ + n3 / ceil * n4, false));
        }
        TPHit.access$3200().playerController.attackEntity((EntityPlayer)TPHit.access$3100().thePlayer, this.val$e.getEntity());
        TPHit.access$3300().thePlayer.swingItem();
    }
}
