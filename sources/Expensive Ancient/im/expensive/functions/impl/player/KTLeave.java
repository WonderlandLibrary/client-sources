package im.expensive.functions.impl.player;

import im.expensive.functions.api.Category;
import im.expensive.functions.api.Function;
import im.expensive.functions.api.FunctionRegister;

@FunctionRegister(name = "KTLeave", type = Category.Player)
public class KTLeave extends Function {

    @Override
    public void onEnable() {
        super.onEnable();
       // mc.playerController.processRightClickBlock(mc.player, mc.world, Hand.MAIN_HAND, (BlockRayTraceResult) mc.player.pick(4.5f, 1, false));
    }
}
