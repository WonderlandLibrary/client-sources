package best.actinium.module.impl.combat;

import best.actinium.event.EventType;
import best.actinium.event.api.Callback;
import best.actinium.event.impl.move.MotionEvent;
import best.actinium.event.impl.render.Render3DEvent;
import best.actinium.module.Module;
import best.actinium.module.ModuleCategory;
import best.actinium.module.api.data.ModuleInfo;
import best.actinium.util.player.RotationsUtils;
import best.actinium.util.render.ChatUtil;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@ModuleInfo(
        name = "Bot",
        description = "Plays Bedwars i think.",
        category = ModuleCategory.COMBAT
)
public class BedwarsBotModule extends Module {
    private BlockPos targetBlockPos;

    @Callback
    public void onMotion(MotionEvent event) {
        List<EntityItem> items = mc.theWorld.loadedEntityList.stream()
                .filter(entity -> entity instanceof EntityItem)
                .map(entity -> (EntityItem) entity)
                .filter(itemEntity -> isValidItem(itemEntity.getEntityItem()))
                .toList();

        if (!items.isEmpty()) {
            EntityItem closestItem = Collections.min(items, Comparator.comparingDouble(entity -> mc.thePlayer.getDistanceToEntity(entity)));

            this.targetBlockPos = closestItem.getPosition();
        } else {
            this.targetBlockPos = null;
        }

        if (this.targetBlockPos == null || event.getType() == EventType.POST) {
            return;
        }
        //store the possiton of the first iron thats the nearest to you and then just go there whjen u r not buying shit or attacking

        float[] rotations;
        rotations = RotationsUtils.getSmoothRotations(targetBlockPos, EnumFacing.UP,true,1,mc.thePlayer.rotationYaw);

        double distanceToBlock = mc.thePlayer.getDistance(this.targetBlockPos.getX(), this.targetBlockPos.getY(), this.targetBlockPos.getZ());

        if (mc.thePlayer.hurtTime == 0 && !mc.thePlayer.isCollidedHorizontally) {
            mc.gameSettings.keyBindBack.pressed = distanceToBlock > 1.0;
        }

        if(mc.thePlayer.isCollidedVertically) {
          //  mc.gameSettings.keyBindBack.pressed = false;
        }

        for (int i = 0; i < 9; i++) {
            ItemStack stack = mc.thePlayer.inventory.getStackInSlot(i);
            if(stack != null) {
                if(stack.getItem() == Items.iron_ingot && stack.stackSize > 63) {
                    ChatUtil.display("64 iron");
                }

                if(stack.getItem() == Items.gold_ingot && stack.stackSize > 12) {
                    ChatUtil.display("12 gold");
                }
            }
        }


       // mc.thePlayer.rotationYaw = mc.thePlayer.isCollidedHorizontally ? mc.thePlayer.rotationYaw - 180 : mc.thePlayer.rotationYaw;
       // mc.gameSettings.keyBindJump.pressed = mc.thePlayer.isCollidedHorizontally || mc.thePlayer.isInWater();
    }

    @Callback
    public void onRender3D(Render3DEvent event) {
        if (targetBlockPos == null) {
            return;
        }

        //RenderUtil.drawLine(RenderUtil.getX(mc.thePlayer), RenderUtil.getY(mc.thePlayer), RenderUtil.getZ(mc.thePlayer), RenderUtil.getX(),
         //       RenderUtil.getY(target), RenderUtil.getZ(target),1);
    }

    private boolean isValidItem(ItemStack itemStack) {
        return itemStack.getItem() == Items.iron_ingot || itemStack.getItem() == Items.gold_ingot;
    }
}
