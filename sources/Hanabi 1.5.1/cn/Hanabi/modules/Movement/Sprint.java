package cn.Hanabi.modules.Movement;

import cn.Hanabi.modules.*;
import cn.Hanabi.events.*;
import ClassSub.*;
import net.minecraft.potion.*;
import com.darkmagician6.eventapi.*;

public class Sprint extends Mod
{
    public static boolean isSprinting;
    
    
    public Sprint() {
        super("Sprint", Category.MOVEMENT);
    }
    
    @EventTarget
    public void onUpdate(final EventUpdate eventUpdate) {
        if (Class346.abuses < 0 && Class200.MovementInput()) {
            Sprint.mc.thePlayer.addPotionEffect(new PotionEffect(Potion.confusion.getId(), 100, 1));
        }
        final boolean b = Sprint.mc.thePlayer.getFoodStats().getFoodLevel() > 6.0f || Sprint.mc.thePlayer.capabilities.allowFlying;
        if (Class200.MovementInput() && b) {
            Sprint.isSprinting = true;
            Sprint.mc.thePlayer.setSprinting(true);
        }
        else {
            Sprint.isSprinting = false;
        }
    }
    
    public void onDisable() {
        Sprint.isSprinting = false;
        Sprint.mc.thePlayer.setSprinting(false);
        super.onDisable();
    }
}
