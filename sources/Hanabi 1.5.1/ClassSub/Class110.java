package ClassSub;

import net.minecraft.client.*;
import cn.Hanabi.events.*;
import net.minecraft.client.entity.*;

public class Class110
{
    Minecraft mc;
    
    
    public Class110() {
        this.mc = Minecraft.getMinecraft();
    }
    
    public void onPre(final EventPreMotion eventPreMotion) {
        if (this.mc.thePlayer.onGround && Class200.MovementInput() && !this.mc.thePlayer.isInWater()) {
            Class211.getTimer().timerSpeed = 1.0f;
            this.mc.thePlayer.jump();
        }
        else if (Class200.MovementInput() && !this.mc.thePlayer.isInWater()) {
            Class200.setSpeed(0.8);
        }
        if (!Class200.MovementInput()) {
            final EntityPlayerSP thePlayer = this.mc.thePlayer;
            final EntityPlayerSP thePlayer2 = this.mc.thePlayer;
            final double n = 0.0;
            thePlayer2.motionZ = n;
            thePlayer.motionX = n;
        }
    }
}
