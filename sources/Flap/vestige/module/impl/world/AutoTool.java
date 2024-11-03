package vestige.module.impl.world;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.BlockPos;
import vestige.Flap;
import vestige.event.Listener;
import vestige.event.impl.UpdateEvent;
import vestige.module.Category;
import vestige.module.Module;
import vestige.setting.AbstractSetting;
import vestige.setting.impl.BooleanSetting;

public class AutoTool extends Module {
   private final Minecraft mc = Minecraft.getMinecraft();
   private int previousSlot = -1;
   private final BooleanSetting backitem = new BooleanSetting("Back to Old Item", true);
   private final BooleanSetting itemspoof = new BooleanSetting("Item Spoof", true);

   public AutoTool() {
      super("AutoTool", Category.WORLD);
      this.addSettings(new AbstractSetting[]{this.backitem, this.itemspoof});
   }

   @Listener
   public void onUpdate(UpdateEvent event) {
      if (this.mc.objectMouseOver != null && this.mc.gameSettings.keyBindAttack.isKeyDown()) {
         BlockPos blockPos = this.mc.objectMouseOver.getBlockPos();
         if (blockPos != null) {
            Block block = this.mc.theWorld.getBlockState(blockPos).getBlock();
            float bestSpeed = 1.0F;
            int bestSlot = -1;

            for(int i = 0; i < 9; ++i) {
               ItemStack stack = this.mc.thePlayer.inventory.getStackInSlot(i);
               if (stack != null && stack.getItem() instanceof ItemTool) {
                  float speed = stack.getStrVsBlock(block);
                  if (speed > bestSpeed) {
                     bestSpeed = speed;
                     bestSlot = i;
                  }
               }
            }

            if (bestSlot != -1 && bestSlot != this.mc.thePlayer.inventory.currentItem) {
               this.previousSlot = this.mc.thePlayer.inventory.currentItem;
               if (this.itemspoof.isEnabled()) {
                  Flap.instance.getSlotSpoofHandler().startSpoofing(this.previousSlot);
               }

               this.mc.thePlayer.inventory.currentItem = bestSlot;
            }
         }
      } else if (this.backitem.isEnabled()) {
         if (this.itemspoof.isEnabled()) {
            Flap.instance.getSlotSpoofHandler().stopSpoofing();
         }

         if (this.previousSlot != -1) {
            this.mc.thePlayer.inventory.currentItem = this.previousSlot;
            this.previousSlot = -1;
         }
      }

   }
}
