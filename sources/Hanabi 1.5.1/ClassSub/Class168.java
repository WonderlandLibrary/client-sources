package ClassSub;

import cn.Hanabi.events.*;
import cn.Hanabi.modules.Combat.*;
import net.minecraft.entity.player.*;

class Class168 extends Thread
{
    final EventAttack val$e;
    final TPHit this$0;
    
    
    Class168(final TPHit this$0, final EventAttack val$e) {
        this.this$0 = this$0;
        this.val$e = val$e;
    }
    
    @Override
    public void run() {
        try {
            Thread.sleep(1000L);
        }
        catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        this.this$0.aacCount = 0;
        TPHit.access$3800().playerController.attackEntity((EntityPlayer)TPHit.access$3700().thePlayer, this.val$e.getEntity());
        TPHit.access$3900().thePlayer.swingItem();
    }
}
