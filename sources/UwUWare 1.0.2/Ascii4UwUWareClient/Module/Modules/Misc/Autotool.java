package Ascii4UwUWareClient.Module.Modules.Misc;

import Ascii4UwUWareClient.API.EventHandler;
import Ascii4UwUWareClient.API.Events.World.EventPreUpdate;
import Ascii4UwUWareClient.Module.Module;
import Ascii4UwUWareClient.Module.ModuleType;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;

public class Autotool extends Module {
    public Autotool() {
        super("AutoTool", new String[]{"tool", "teel", "auto"}, ModuleType.Misc);
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
            ItemStack itemStack = Minecraft.thePlayer.inventory.mainInventory[i];
            if (itemStack == null || itemStack.getStrVsBlock(block) <= strength) continue;
            strength = itemStack.getStrVsBlock(block);
            bestItemIndex = i;
        }
        if (bestItemIndex != -1) {
            Minecraft.thePlayer.inventory.currentItem = bestItemIndex;
        }
    }
}
