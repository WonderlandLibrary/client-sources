package Focus.Beta.IMPL.Module.impl.misc;

import Focus.Beta.API.EventHandler;
import Focus.Beta.API.events.world.EventPreUpdate;
import Focus.Beta.IMPL.Module.Module;
import Focus.Beta.IMPL.Module.Type;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;

public class Autotool extends Module {
    public Autotool() {
        super("AutoTool", new String[]{"tool", "teel", "auto"}, Type.MISC, "Switches to the best tool");
    }

    @EventHandler
    public void onUpdate(EventPreUpdate e) {
        if (!this.mc.gameSettings.keyBindAttack.pressed) {
            return;
        }
        if (this.mc.objectMouseOver == null) {
            return;
        }
        BlockPos pos = this.mc.objectMouseOver.getBlockPos();
        if (pos == null) {
            return;
        }
        this.updateTool(pos);
    }

    public void updateTool(BlockPos pos) {
        Block block = this.mc.theWorld.getBlockState(pos).getBlock();
        float strength = 1.0f;
        int bestItemIndex = -1;
        for (int i = 0; i < 9; ++i) {
            ItemStack itemStack = mc.thePlayer.inventory.mainInventory[i];
            if (itemStack == null || itemStack.getStrVsBlock(block) <= strength) continue;
            strength = itemStack.getStrVsBlock(block);
            bestItemIndex = i;
        }
        if (bestItemIndex != -1) {
            mc.thePlayer.inventory.currentItem = bestItemIndex;
        }
    }
}
