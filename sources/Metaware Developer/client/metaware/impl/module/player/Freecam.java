package client.metaware.impl.module.player;

import client.metaware.api.event.painfulniggerrapist.Listener;
import client.metaware.api.event.painfulniggerrapist.annotations.EventHandler;
import client.metaware.api.module.api.Category;
import client.metaware.api.module.api.Module;
import client.metaware.api.module.api.ModuleInfo;
import client.metaware.impl.event.impl.network.PacketEvent;
import client.metaware.impl.event.impl.player.UpdatePlayerEvent;
import client.metaware.impl.utils.util.player.MovementUtils;
import net.minecraft.network.play.client.C03PacketPlayer;

@ModuleInfo(renderName = "Freecam", name = "Freecam", category = Category.PLAYER)
public class Freecam extends Module {
    private double posX, posY, posZ;

    @EventHandler
    private Listener<PacketEvent> eventMoveListener = event -> {
        if(event.getPacket() instanceof C03PacketPlayer){
            event.setCancelled(true);
        }
    };


    @EventHandler
    private Listener<UpdatePlayerEvent> updatePlayerEventListener = event -> {
        if(mc.thePlayer.isMoving()){
            mc.thePlayer.noClip = true;
            mc.thePlayer.motionY = 0;
            MovementUtils.setSpeed(event, 2);

            if(mc.gameSettings.keyBindJump.pressed){
                mc.thePlayer.motionY += 0.5F;
            }

            if(mc.gameSettings.keyBindSneak.pressed){
                mc.thePlayer.motionY -= 0.5F;
            }
        }
    };

    public void onEnable() {
        super.onEnable();
        if(mc.theWorld != null) {
            this.posX = mc.thePlayer.posX;
            this.posY = mc.thePlayer.posY;
            this.posZ = mc.thePlayer.posZ;
        }
    }

    public void onDisable() {
        super.onDisable();
        if(mc.theWorld != null) {
            mc.thePlayer.setPosition(posX, posY, posZ);
            mc.thePlayer.motionX = 0;
            mc.thePlayer.motionY = 0;
            mc.thePlayer.motionZ = 0;
        }
    }
}
