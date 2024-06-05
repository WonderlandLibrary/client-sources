package ClassSub;

import net.minecraft.client.*;
import cn.Hanabi.value.*;
import cn.Hanabi.injection.interfaces.*;
import net.minecraft.client.entity.*;

public class Class131
{
    Minecraft mc;
    Value<Double> timer;
    
    
    public Class131() {
        this.mc = Minecraft.getMinecraft();
        this.timer = new Value<Double>("Fly_MotionSpeed", 1.0, 1.0, 10.0, 1.0);
    }
    
    public void onPre() {
        this.mc.thePlayer.motionY = 0.0;
        if (Class200.MovementInput()) {
            Class200.setSpeed(this.timer.getValueState() * 0.5);
        }
        else {
            Class200.setSpeed(0.0);
        }
        if (((IKeyBinding)this.mc.gameSettings.keyBindSneak).getPress()) {
            final EntityPlayerSP thePlayer = this.mc.thePlayer;
            --thePlayer.motionY;
        }
        else if (((IKeyBinding)this.mc.gameSettings.keyBindJump).getPress()) {
            final EntityPlayerSP thePlayer2 = this.mc.thePlayer;
            ++thePlayer2.motionY;
        }
    }
}
