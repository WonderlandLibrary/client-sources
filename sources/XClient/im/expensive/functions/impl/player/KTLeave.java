package im.expensive.functions.impl.player;

import im.expensive.functions.api.Category;
import im.expensive.functions.api.Function;
import im.expensive.functions.api.FunctionRegister;
import net.minecraft.network.play.client.CConfirmTeleportPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockRayTraceResult;

@FunctionRegister(name = "KTLeave", type = Category.PLAYER)
public class KTLeave extends Function {

    @Override
    public void onEnable() {
        super.onEnable();
       // mc.playerController.processRightClickBlock(mc.player, mc.world, Hand.MAIN_HAND, (BlockRayTraceResult) mc.player.pick(4.5f, 1, false));

        this.setState(false, false);
    }
}
