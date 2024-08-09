//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package src.Wiksi.functions.impl.player;

import com.google.common.eventbus.Subscribe;
import src.Wiksi.events.EventUpdate;
import src.Wiksi.functions.api.Category;
import src.Wiksi.functions.api.Function;
import src.Wiksi.functions.api.FunctionRegister;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;

@FunctionRegister(
        name = "AutoClan",
        type = Category.Player
)
public class AutoPilot2 extends Function {
    public AutoPilot2() {
    }

    @Subscribe
    private void onUpdate(EventUpdate event) {
        if (event instanceof EventUpdate) {
            mc.gameSettings.keyBindUseItem.setPressed(true);
            mc.playerController.onPlayerDamageBlock(new BlockPos(mc.player.getPosX(), mc.player.getPosY(), mc.player.getPosZ()), mc.player.getHorizontalFacing());
            mc.player.swingArm(Hand.MAIN_HAND);
            mc.playerController.onPlayerDamageBlock(new BlockPos(mc.player.getPosX(), mc.player.getPosY(), mc.player.getPosZ()), mc.player.getHorizontalFacing());
            mc.player.swingArm(Hand.MAIN_HAND);
            mc.playerController.onPlayerDamageBlock(new BlockPos(mc.player.getPosX(), mc.player.getPosY(), mc.player.getPosZ()), mc.player.getHorizontalFacing());
            mc.player.swingArm(Hand.MAIN_HAND);
            mc.playerController.onPlayerDamageBlock(new BlockPos(mc.player.getPosX(), mc.player.getPosY(), mc.player.getPosZ()), mc.player.getHorizontalFacing());
            mc.player.swingArm(Hand.MAIN_HAND);
        }

    }

    public void onDisable() {
        mc.gameSettings.keyBindUseItem.setPressed(true);
        super.onDisable();
    }
}
