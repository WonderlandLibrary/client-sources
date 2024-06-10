package Snipeware.module.impl.world;

import Snipeware.Client;
import Snipeware.api.annotations.Handler;
import Snipeware.events.player.EventMotionUpdate;
import Snipeware.module.Module;
import Snipeware.module.impl.movement.Flight;
import Snipeware.util.other.Logger;
import Snipeware.util.other.TimeHelper;
import Snipeware.value.impl.EnumValue;
import Snipeware.value.impl.NumberValue;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;

public class AntiFall extends Module {

    public NumberValue<Float> distance = new NumberValue<>("Distance", 6F, 1F, 10F, 3F);
    
    public boolean onetimeexec = false;

    public AntiFall() {
        super("AntiVoid", 0, ModuleCategory.WORLD);
     
        addValues(distance);
    }
 


    

    @Handler
	public void onMotionUpdate(final EventMotionUpdate eventMotion) {
        if (eventMotion.isPre()) {
      
            if (!Client.INSTANCE.getModuleManager().getModule("Flight").isEnabled() && mc.thePlayer.fallDistance > distance.getValue() && !isBlockUnder()) {
                eventMotion.setPosX(-999);
                eventMotion.setPosY(-999);
                eventMotion.setPosZ(-999);
                mc.thePlayer.fallDistance = 0;
                
            }
        }
    }

    private boolean isBlockUnder() {
        if (mc.thePlayer.posY < 0)
            return false;
        for (int offset = 0; offset < (int) mc.thePlayer.posY + 2; offset += 2) {
            AxisAlignedBB bb = mc.thePlayer.getEntityBoundingBox().offset(0, -offset, 0);
            if (!mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, bb).isEmpty()) {
                return true;
            }
        }
        return false;
    }
}
