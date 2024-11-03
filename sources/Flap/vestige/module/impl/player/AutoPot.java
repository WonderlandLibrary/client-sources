package vestige.module.impl.player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLadder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import vestige.event.Listener;
import vestige.event.impl.MotionEvent;
import vestige.module.Category;
import vestige.module.Module;
import vestige.util.player.PlayerUtil;
import vestige.util.player.RotationsUtil;

public class AutoPot extends Module {
   private int ticksSinceLastSplash;
   private int ticksSinceCanSplash;
   private int oldSlot;
   private boolean needSplash;
   private boolean switchBack;
   private final ArrayList<Integer> acceptedPotions = new ArrayList(Arrays.asList(6, 1, 5, 8, 14, 12, 10, 16));

   public AutoPot() {
      super("AutoPot", Category.ULTILITY);
   }

   @Listener
   public void onMotion(MotionEvent event) {
      ++this.ticksSinceLastSplash;
      Block blockBelow = PlayerUtil.getBlock(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.0D, mc.thePlayer.posZ));
      if (!mc.thePlayer.isInWater() && !mc.thePlayer.isInLava() && !(blockBelow instanceof BlockAir) && !(blockBelow instanceof BlockLadder)) {
         ++this.ticksSinceCanSplash;
      } else {
         this.ticksSinceCanSplash = 0;
      }

      if (this.switchBack) {
         if (mc.thePlayer.isUsingItem()) {
            mc.thePlayer.stopUsingItem();
         }

         mc.thePlayer.inventory.currentItem = this.oldSlot;
         this.switchBack = false;
      } else if (this.ticksSinceCanSplash > 1 && mc.thePlayer.onGround) {
         this.oldSlot = mc.thePlayer.inventory.currentItem;

         for(int i = 36; i < 45; ++i) {
            ItemStack itemStack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            if (itemStack != null && mc.currentScreen == null) {
               Item item = itemStack.getItem();
               if (item instanceof ItemPotion) {
                  ItemPotion p = (ItemPotion)item;
                  if (ItemPotion.isSplash(itemStack.getMetadata()) && p.getEffects(itemStack.getMetadata()) != null) {
                     int potionID = ((PotionEffect)p.getEffects(itemStack.getMetadata()).get(0)).getPotionID();
                     boolean hasPotionIDActive = false;
                     Iterator var9 = mc.thePlayer.getActivePotionEffects().iterator();

                     while(var9.hasNext()) {
                        PotionEffect potion = (PotionEffect)var9.next();
                        if (potion.getPotionID() == potionID && potion.getDuration() > 0) {
                           hasPotionIDActive = true;
                           break;
                        }
                     }

                     if (this.acceptedPotions.contains(potionID) && !hasPotionIDActive && this.ticksSinceLastSplash > 20) {
                        String effectName = ((PotionEffect)p.getEffects(itemStack.getMetadata()).get(0)).getEffectName();
                        if (!effectName.contains("regeneration") && !effectName.contains("heal") || !(mc.thePlayer.getHealth() > 16.0F)) {
                           if (!this.needSplash) {
                              this.needSplash = true;
                           } else {
                              mc.thePlayer.inventory.currentItem = i - 36;
                              MovingObjectPosition hitResult = RotationsUtil.rayCast(1.0D, event.getPitch(), event.getYaw());
                              this.switchBack = true;
                              event.setPitch(90.0F);
                              mc.gameSettings.keyBindUseItem.pressed = true;
                              this.ticksSinceLastSplash = 0;
                              this.needSplash = false;
                           }

                           return;
                        }
                     }
                  }
               }
            }
         }

      }
   }
}
