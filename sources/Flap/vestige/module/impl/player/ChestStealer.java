package vestige.module.impl.player;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import vestige.Flap;
import vestige.event.Listener;
import vestige.event.impl.UpdateEvent;
import vestige.module.Category;
import vestige.module.Module;
import vestige.setting.AbstractSetting;
import vestige.setting.impl.BooleanSetting;
import vestige.setting.impl.IntegerSetting;

public class ChestStealer extends Module {
   private final IntegerSetting delay = new IntegerSetting("Delay", 1, 0, 10, 1);
   private final BooleanSetting instant = new BooleanSetting("Instant", true);
   private final BooleanSetting filter = new BooleanSetting("Filter", true);
   private final BooleanSetting autoClose = new BooleanSetting("Autoclose", true);
   private final BooleanSetting guiDetect = new BooleanSetting("Gui detect", true);
   private int counter;
   private InventoryManager invManager;

   public ChestStealer() {
      super("Chest Stealer", Category.ULTILITY);
      this.addSettings(new AbstractSetting[]{this.delay, this.instant, this.filter, this.autoClose, this.guiDetect});
   }

   public void onClientStarted() {
      this.invManager = (InventoryManager)Flap.instance.getModuleManager().getModule(InventoryManager.class);
   }

   @Listener
   public void onUpdate(UpdateEvent event) {
      if (mc.thePlayer.openContainer != null && mc.thePlayer.openContainer instanceof ContainerChest && (!this.isGUI() || !this.guiDetect.isEnabled())) {
         ContainerChest container = (ContainerChest)mc.thePlayer.openContainer;
         int i;
         ItemStack stack;
         if (this.instant.isEnabled()) {
            for(i = 0; i < container.getLowerChestInventory().getSizeInventory(); ++i) {
               stack = container.getLowerChestInventory().getStackInSlot(i);
               if (stack != null && !this.isUseless(stack)) {
                  mc.playerController.windowClick(container.windowId, i, 1, 1, mc.thePlayer);
               }
            }

            if (this.autoClose.isEnabled() && this.isChestEmpty(container)) {
               mc.thePlayer.closeScreen();
            }

            return;
         }

         for(i = 0; i < container.getLowerChestInventory().getSizeInventory(); ++i) {
            stack = container.getLowerChestInventory().getStackInSlot(i);
            if (stack != null && !this.isUseless(stack) && ++this.counter > this.delay.getValue()) {
               mc.playerController.windowClick(container.windowId, i, 1, 1, mc.thePlayer);
               this.counter = 0;
               return;
            }
         }

         if (this.autoClose.isEnabled() && this.isChestEmpty(container)) {
            mc.thePlayer.closeScreen();
         }
      }

   }

   private boolean isChestEmpty(ContainerChest container) {
      for(int i = 0; i < container.getLowerChestInventory().getSizeInventory(); ++i) {
         ItemStack stack = container.getLowerChestInventory().getStackInSlot(i);
         if (stack != null && !this.isUseless(stack)) {
            return false;
         }
      }

      return true;
   }

   private boolean isUseless(ItemStack stack) {
      return !this.filter.isEnabled() ? false : this.invManager.isUseless(stack);
   }

   private boolean isGUI() {
      for(double x = mc.thePlayer.posX - 5.0D; x <= mc.thePlayer.posX + 5.0D; ++x) {
         for(double y = mc.thePlayer.posY - 5.0D; y <= mc.thePlayer.posY + 5.0D; ++y) {
            for(double z = mc.thePlayer.posZ - 5.0D; z <= mc.thePlayer.posZ + 5.0D; ++z) {
               BlockPos pos = new BlockPos(x, y, z);
               Block block = mc.theWorld.getBlockState(pos).getBlock();
               if (block instanceof BlockChest || block instanceof BlockEnderChest) {
                  return false;
               }
            }
         }
      }

      return true;
   }
}
