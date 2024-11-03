package vestige.module.impl.world;

import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import vestige.event.Listener;
import vestige.event.impl.UpdateEvent;
import vestige.module.Category;
import vestige.module.Module;

public class FastUse extends Module {
   public FastUse() {
      super("FastUse", Category.WORLD);
   }

   @Listener
   public void onUpdate(UpdateEvent event) {
      if (mc.thePlayer.getItemInUseDuration() > 0) {
         Item currentItem = mc.thePlayer.getItemInUse().getItem();
         int i;
         if (currentItem instanceof ItemFood || currentItem instanceof ItemPotion) {
            for(i = 0; i < 20; ++i) {
               mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(mc.thePlayer.onGround));
            }
         }

         if (currentItem instanceof ItemBow) {
            if (mc.thePlayer.getItemInUseDuration() >= 2) {
               for(i = 0; i < 20; ++i) {
                  mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.getHeldItem()));
               }

               mc.playerController.onStoppedUsingItem(mc.thePlayer);
            } else {
               for(i = 0; i < 20; ++i) {
                  mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(mc.thePlayer.onGround));
               }
            }
         }
      }

   }
}
