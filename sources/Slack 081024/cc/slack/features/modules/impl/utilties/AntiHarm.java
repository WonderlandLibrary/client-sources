package cc.slack.features.modules.impl.utilties;

import cc.slack.events.State;
import cc.slack.events.impl.player.CollideEvent;
import cc.slack.events.impl.player.MotionEvent;
import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.impl.BooleanValue;
import cc.slack.utils.other.MathUtil;
import io.github.nevalackin.radbus.Listen;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCactus;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

@ModuleInfo(
        name = "AntiHarm",
        category = Category.UTILITIES
)
public class AntiHarm extends Module {

    private final BooleanValue antiCactus = new BooleanValue("AntiCactus", false);
    private final BooleanValue antiLava = new BooleanValue("AntiLava", false);
    private final BooleanValue antiAfk = new BooleanValue("AntiAfk", false);

    public AntiHarm() { addSettings(antiCactus, antiLava, antiAfk);}

    @Listen
    public void onCollide (CollideEvent event) {
        if (event.getBlock() instanceof BlockCactus && antiCactus.getValue()) {
            event.setBoundingBox(AxisAlignedBB.fromBounds(event.getX(), event.getY(), event.getZ(), event.getX() + 1, event.getY() + 1, event.getZ() + 1));
        }
    }

    @Listen
    public void onMotion (MotionEvent event) {
        if (event.getState() != State.POST && antiLava.getValue()) {
            Block block = mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX + mc.thePlayer.motionX, mc.thePlayer.posY + (mc.thePlayer.onGround ? Math.max(0.0, mc.thePlayer.motionY) : mc.thePlayer.motionY), mc.thePlayer.posZ + mc.thePlayer.motionZ)).getBlock();
            if (block == Blocks.lava || block == Blocks.flowing_lava) {
                event.setY(event.getY() + MathUtil.randomDouble(0.5, 0.85));
            }
        }
    }

    @Listen
    public void onUpdate (UpdateEvent event) {
        if (!antiAfk.getValue()) return;
        switch (mc.thePlayer.ticksExisted % 60) {
            case 0:
                mc.gameSettings.keyBindForward.pressed = true;
                break;
            case 1:
                mc.gameSettings.keyBindForward.pressed = GameSettings.isKeyDown(mc.gameSettings.keyBindForward);
                mc.gameSettings.keyBindBack.pressed = true;
                break;
            case 2:
                mc.gameSettings.keyBindBack.pressed = GameSettings.isKeyDown(mc.gameSettings.keyBindBack);
                break;
        }
    }

}
