package cn.Hanabi.modules.Movement;

import cn.Hanabi.value.*;
import cn.Hanabi.modules.*;
import cn.Hanabi.modules.Combat.*;
import ClassSub.*;
import net.minecraft.util.*;
import net.minecraft.network.*;
import com.darkmagician6.eventapi.*;
import cn.Hanabi.events.*;
import net.minecraft.network.play.client.*;

public class NoSlow extends Mod
{
    public Value<String> mode;
    
    
    public NoSlow() {
        super("NoSlow", Category.MOVEMENT);
        (this.mode = new Value<String>("NoSlow", "Mode", 0)).addValue("Hypixel");
        this.mode.addValue("NCP");
        this.mode.addValue("AAC");
    }
    
    @EventTarget
    public void onPre(final EventPreMotion eventPreMotion) {
        if (NoSlow.mc.thePlayer.isUsingItem() && Class200.isMoving() && Class180.isOnGround(0.42) && KillAura.target == null && (this.mode.isCurrentMode("AAC") || this.mode.isCurrentMode("NCP"))) {
            if (this.mode.isCurrentMode("AAC")) {
                Class211.getTimer().timerSpeed = 0.7f;
            }
            final double posX = NoSlow.mc.thePlayer.posX;
            final double posY = NoSlow.mc.thePlayer.posY;
            final double posZ = NoSlow.mc.thePlayer.posZ;
            NoSlow.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
        }
        if (!NoSlow.mc.thePlayer.isUsingItem() && this.mode.isCurrentMode("AAC")) {
            Class211.getTimer().timerSpeed = 1.0f;
        }
    }
    
    @EventTarget
    public void onPost(final EventPostMotion eventPostMotion) {
        if (NoSlow.mc.thePlayer.isUsingItem() && Class200.isMoving() && Class180.isOnGround(0.42) && KillAura.target == null && (this.mode.isCurrentMode("AAC") || this.mode.isCurrentMode("NCP"))) {
            final double posX = NoSlow.mc.thePlayer.posX;
            final double posY = NoSlow.mc.thePlayer.posY;
            final double posZ = NoSlow.mc.thePlayer.posZ;
            NoSlow.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C08PacketPlayerBlockPlacement(NoSlow.mc.thePlayer.inventory.getCurrentItem()));
        }
    }
}
