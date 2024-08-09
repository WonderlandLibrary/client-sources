package wtf.shiyeno.modules.impl.player;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockRayTraceResult;
import wtf.shiyeno.events.Event;
import wtf.shiyeno.events.impl.player.EventUpdate;
import wtf.shiyeno.modules.Function;
import wtf.shiyeno.modules.FunctionAnnotation;
import wtf.shiyeno.modules.Type;

@FunctionAnnotation(
        name = "AutoTool",
        type = Type.Player
)
public class AutoTool extends Function {
    private int oldSlot = -1;
    private boolean status;

    public AutoTool() {
    }

    public void onEvent(Event event) {
        if (mc.player != null && mc.world != null) {
            if (event instanceof EventUpdate) {
                if (mc.objectMouseOver != null && mc.gameSettings.keyBindAttack.pressed) {
                    int bestSlot = this.findBestSlot();
                    if (bestSlot == -1) {
                        return;
                    }

                    this.status = true;
                    if (this.oldSlot == -1) {
                        this.oldSlot = mc.player.inventory.currentItem;
                    }

                    mc.player.inventory.currentItem = this.findBestSlot();
                } else if (this.status) {
                    mc.player.inventory.currentItem = this.oldSlot;
                    this.reset();
                }
            }

        }
    }

    private void reset() {
        this.oldSlot = -1;
        this.status = false;
    }

    private int findBestSlot() {
        if (mc.objectMouseOver instanceof BlockRayTraceResult) {
            BlockRayTraceResult blockRayTraceResult = (BlockRayTraceResult)mc.objectMouseOver;
            Block block = mc.world.getBlockState(blockRayTraceResult.getPos()).getBlock();
            int bestSlot = -1;
            float bestSpeed = 1.0F;

            for(int slot = 0; slot < 9; ++slot) {
                ItemStack stack = mc.player.inventory.getStackInSlot(slot);
                float speed = stack.getDestroySpeed(block.getDefaultState());
                if (speed > bestSpeed) {
                    bestSpeed = speed;
                    bestSlot = slot;
                }
            }

            return bestSlot;
        } else {
            return -1;
        }
    }

    protected void onDisable() {
        this.reset();
        super.onDisable();
    }
}