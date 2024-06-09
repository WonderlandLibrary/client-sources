package host.kix.uzi.module.modules.misc;

import com.darkmagician6.eventapi.SubscribeEvent;
import host.kix.uzi.events.SentPacketEvent;
import host.kix.uzi.module.Module;
import net.minecraft.network.play.client.C03PacketPlayer;

/**
 * Created by k1x on 4/23/17.
 */
public class PotionSaver extends Module {

    public PotionSaver() {
        super("PotionSaver", 0, Category.MISC);
    }

    @SubscribeEvent
    public void packet(SentPacketEvent e){
        if(e.getPacket() instanceof C03PacketPlayer && canSave()){
            e.setCancelled(true);
        }
    }

    private boolean canSave() {
        boolean usingItem = mc.thePlayer.isUsingItem();
        boolean swinging = mc.thePlayer.isSwingInProgress;
        boolean moving = mc.thePlayer.motionX != 0 || !mc.thePlayer.isCollidedVertically || mc.gameSettings.keyBindJump.getIsKeyPressed() || mc.thePlayer.motionZ != 0;
        boolean hasPotions = mc.thePlayer.getActivePotionEffects().size() > 0;
        return !usingItem && !swinging && !moving && hasPotions;
    }


}
