package me.finz0.osiris.module.modules.combat;

import de.Hero.settings.Setting;
import me.finz0.osiris.AuroraMod;
import me.finz0.osiris.module.Module;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.math.BlockPos;


public class FastBow extends Module {
    public FastBow() {
        super("FastBow", Category.COMBAT, "Spams your bow");
    }
    public void setup(){

    }
    @Override
    public void onUpdate() {
        if (mc.player.inventory.getCurrentItem().getItem() instanceof net.minecraft.item.ItemBow &&
                mc.player.isHandActive() && mc.player.getItemInUseMaxCount() >= 3) {
            mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, mc.player.getHorizontalFacing()));
            mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(mc.player.getActiveHand()));
            mc.player.stopActiveHand();
        }
    }
}
