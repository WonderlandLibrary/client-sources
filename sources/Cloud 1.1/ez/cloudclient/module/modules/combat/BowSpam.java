package ez.cloudclient.module.modules.combat;

import ez.cloudclient.module.Module;
import ez.cloudclient.module.ModuleManager;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.math.BlockPos;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemBow;

public class BowSpam extends Module {

    public BowSpam() {
        super("BowSpam", Category.MOVEMENT, "Spams arrows from your bow.");
    }
	
	public void onUpdate() {
    	if(mc.player.inventory.getCurrentItem().getItem() instanceof ItemBow) {
    		if(mc.player.isHandActive() && mc.player.getItemInUseMaxCount() >= 3) {
    			mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, mc.player.getHorizontalFacing()));
    			mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(mc.player.getActiveHand()));
    			mc.player.stopActiveHand();
    		}
    	}
    }
}