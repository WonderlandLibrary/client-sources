package club.strifeclient.module.implementations.player;

import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import club.strifeclient.event.implementations.player.MotionEvent;
import club.strifeclient.module.Category;
import club.strifeclient.module.Module;
import club.strifeclient.module.ModuleInfo;
import club.strifeclient.setting.implementations.DoubleSetting;
import club.strifeclient.util.networking.PacketUtil;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;

import java.util.function.Supplier;

@ModuleInfo(name = "SpeedMine", description = "Mine blocks faster.", category = Category.PLAYER)
public final class SpeedMine extends Module {

    private final DoubleSetting speedSetting = new DoubleSetting("Speed", 3, 0, 10, 1);

    @EventHandler
    private final Listener<MotionEvent> motionEventListener = e -> {
        final MovingObjectPosition over = mc.objectMouseOver;
        if(over == null || over.sideHit == null || over.getBlockPos() == null) return;
        final BlockPos pos = over.getBlockPos();
        if (mc.playerController.isNotCreative() && mc.playerController.getIsHittingBlock() && mc.playerController.curBlockDamageMP >
                (speedSetting.getMax() - speedSetting.getInt()) / 10f) {
            PacketUtil.sendPacketNoEvent(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, pos, mc.objectMouseOver.sideHit));
            PacketUtil.sendPacketNoEvent(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, pos, mc.objectMouseOver.sideHit));
            mc.theWorld.setBlockState(pos, Blocks.air.getDefaultState(), 11);
        }
    };

    @Override
    public Supplier<Object> getSuffix() {
        return speedSetting::getInt;
    }

}
