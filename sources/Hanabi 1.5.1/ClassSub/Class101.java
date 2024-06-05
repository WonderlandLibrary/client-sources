package ClassSub;

import cn.Hanabi.events.*;
import cn.Hanabi.modules.Combat.*;
import net.minecraft.entity.player.*;

class Class101 extends Thread
{
    final EventAttack val$e;
    final TPHit this$0;
    
    
    Class101(final TPHit this$0, final EventAttack val$e) {
        this.this$0 = this$0;
        this.val$e = val$e;
    }
    
    @Override
    public void run() {
        try {
            Thread.sleep(175L);
        }
        catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        this.this$0.aacState = 2;
        TPHit.access$3500().playerController.attackEntity((EntityPlayer)TPHit.access$3400().thePlayer, this.val$e.getEntity());
        TPHit.access$3600().thePlayer.swingItem();
    }
}
