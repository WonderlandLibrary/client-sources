package src.Wiksi.functions.impl.player;

import src.Wiksi.functions.api.Category;
import src.Wiksi.functions.api.Function;
import src.Wiksi.functions.api.FunctionRegister;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockRayTraceResult;

@FunctionRegister(name = "KTLeave", type = Category.Player)
public class KTLeave extends Function {

    @Override
    public void onEnable() {
        super.onEnable();
        mc.playerController.processRightClickBlock(mc.player, mc.world, Hand.MAIN_HAND, (BlockRayTraceResult) mc.player.pick(4.5f, 1, false));

        this.setState(false, false);
    }
}
