package lol.point.returnclient.module.impl.player;

import lol.point.returnclient.events.impl.update.EventUpdate;
import lol.point.returnclient.module.Category;
import lol.point.returnclient.module.Module;
import lol.point.returnclient.module.ModuleInfo;
import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;
import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;

import java.util.Comparator;
import java.util.stream.IntStream;

@ModuleInfo(
        name = "AutoTool",
        description = "automatically switches to the fastest tool",
        category = Category.PLAYER
)
public class AutoTool extends Module {

    @Subscribe
    private final Listener<EventUpdate> onUpdate = new Listener<>(eventUpdate -> {
        if (!mc.gameSettings.keyBindAttack.isKeyDown()) return;

        BlockPos position = mc.objectMouseOver.getBlockPos();
        if (position == null) return;

        Block block = mc.theWorld.getBlockState(position).getBlock();
        if (block == null) return;

        int slot = IntStream.range(0, 9)
                .filter(index -> mc.thePlayer.inventory.getStackInSlot(index) != null)
                .boxed()
                .max(Comparator.comparingInt(index ->
                        (int) mc.thePlayer.inventory.getStackInSlot(index).getStrVsBlock(block)))
                .orElse(1000);

        if (slot == 1000) return;

        mc.thePlayer.inventory.currentItem = slot;
    });

}
