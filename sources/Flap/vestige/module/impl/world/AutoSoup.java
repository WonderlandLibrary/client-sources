package vestige.module.impl.world;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.item.ItemSoup;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import vestige.event.Listener;
import vestige.event.impl.UpdateEvent;
import vestige.module.Category;
import vestige.module.Module;
import vestige.setting.AbstractSetting;
import vestige.setting.impl.DoubleSetting;

public class AutoSoup extends Module {
   private final DoubleSetting healthheal = new DoubleSetting("Health Heal", 16.0D, 1.0D, 20.0D, 1.0D);
   Minecraft mc = Minecraft.getMinecraft();

   public AutoSoup() {
      super("AutoSoup", Category.WORLD);
      this.addSettings(new AbstractSetting[]{this.healthheal});
   }

   @Listener
   public void onUpdate(UpdateEvent event) {
      int health = (int)this.mc.thePlayer.getHealth();
      if ((double)health < this.healthheal.getValue()) {
         for(int i = 0; i < 9; ++i) {
            if (!(this.mc.currentScreen instanceof GuiInventory)) {
               ItemStack stack = this.mc.thePlayer.inventory.getStackInSlot(i);
               if (stack != null && stack.getItem() instanceof ItemSoup) {
                  this.mc.thePlayer.inventory.currentItem = i;
                  this.mc.playerController.sendUseItem(this.mc.thePlayer, this.mc.theWorld, stack);
                  this.mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.DROP_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                  this.mc.thePlayer.inventory.currentItem = 0;
                  break;
               }
            }
         }
      }

   }
}
