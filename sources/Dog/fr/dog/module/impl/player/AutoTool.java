package fr.dog.module.impl.player;


import fr.dog.event.annotations.SubscribeEvent;
import fr.dog.event.impl.world.TickEvent;
import fr.dog.module.Module;
import fr.dog.module.ModuleCategory;
import fr.dog.property.impl.BooleanProperty;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemTool;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;


public class AutoTool extends Module {
    public BooleanProperty onlyonsneak = BooleanProperty.newInstance("Only On Sneak", false);
    private boolean breaking = false;
    private int prevSlot = -1;

    public AutoTool() {
        super("AutoTool", ModuleCategory.PLAYER);
        this.registerProperties(onlyonsneak);
    }


    @SubscribeEvent
    private void onUpdate(TickEvent event) {
        if (mc.thePlayer.isEating() || mc.thePlayer.isBlocking())
            return;


        if (mc.objectMouseOver != null && mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && (!onlyonsneak.getValue() || mc.thePlayer.isSneaking())) {
            BlockPos pos = mc.objectMouseOver.getBlockPos();
            IBlockState state = mc.theWorld.getBlockState(pos);
            Block block = state.getBlock();

            int slot = getBestToolForBlock(block);

            boolean canSwap = mc.pointedEntity == null;


            if (slot != -1 && mc.gameSettings.keyBindAttack.pressed && canSwap) {
                if (slot != mc.thePlayer.inventory.currentItem) {
                    prevSlot = mc.thePlayer.inventory.currentItem;
                }

                mc.thePlayer.inventory.currentItem = slot;
                return;
            }

            breaking = false;
        }

        if (mc.thePlayer.inventory.currentItem != prevSlot && !breaking && prevSlot != -1) {
            mc.thePlayer.inventory.currentItem = prevSlot;
            prevSlot = -1;
        }
    }


    private int getBestToolForBlock(Block block) {
        int toolSlot = -1;
        float breakSpeed = 0;
        for (int i = 0; i < 9; i++) {
            if (mc.thePlayer.inventory.getStackInSlot(i) == null) {
                continue;
            }

            if (!(mc.thePlayer.inventory.getStackInSlot(i).getItem() instanceof ItemShears || mc.thePlayer.inventory.getStackInSlot(i).getItem() instanceof ItemTool)) {
                continue;
            }

            if (breakSpeed < mc.thePlayer.inventory.getStackInSlot(i).getStrVsBlock(block)) {
                toolSlot = i;
                breakSpeed = mc.thePlayer.inventory.getStackInSlot(i).getStrVsBlock(block);
            }

        }
        return toolSlot;
    }
}
