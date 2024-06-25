package cc.slack.features.modules.impl.ghost;

import cc.slack.events.State;
import cc.slack.events.impl.player.MotionEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.Value;
import cc.slack.features.modules.api.settings.impl.BooleanValue;
import cc.slack.features.modules.api.settings.impl.NumberValue;
import cc.slack.utils.client.mc;
import cc.slack.utils.other.BlockUtils;
import io.github.nevalackin.radbus.Listen;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockSign;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

@ModuleInfo(
   name = "LegitNofall",
   category = Category.GHOST
)
public class LegitNofall extends Module {
   private final BooleanValue silentAim = new BooleanValue("Silent Aim", true);
   private final BooleanValue switchToItem = new BooleanValue("Switch to Item", true);
   private final NumberValue<Float> minFallDist = new NumberValue("Minimum fall dist", 5.0F, 3.0F, 20.0F, 0.5F);
   private float prevPitch;

   public LegitNofall() {
      this.addSettings(new Value[]{this.silentAim, this.switchToItem, this.minFallDist});
   }

   @Listen
   public void onMotion(MotionEvent e) {
      if (e.getState() == State.PRE) {
         MovingObjectPosition rayCast = wrayCast((double)mc.getPlayerController().getBlockReachDistance(), e.getYaw(), 90.0F);
         if (this.inPosition() && rayCast != null && rayCast.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && this.holdWaterBucket((Boolean)this.switchToItem.getValue()) || this.inPosition() && rayCast != null && rayCast.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && this.holdSpiderweb((Boolean)this.switchToItem.getValue())) {
            if ((Boolean)this.silentAim.getValue()) {
               e.setPitch(90.0F);
            } else {
               this.prevPitch = mc.getPlayer().rotationPitch;
               mc.getPlayer().rotationPitch = 90.0F;
            }

            this.sendPlace();
         }

         if (mc.getPlayer().onGround && mc.getPlayer().isInWater()) {
            this.sendPlace();
            if (!(Boolean)this.silentAim.getValue()) {
               mc.getPlayer().rotationPitch = this.prevPitch;
            }
         }
      }

   }

   private boolean inPosition() {
      return !mc.getPlayer().capabilities.isFlying && !mc.getPlayer().capabilities.isCreativeMode && !mc.getPlayer().onGround && mc.getPlayer().motionY < -0.6D && !mc.getPlayer().isInWater() && this.fallDistance() <= 2 && mc.getPlayer().fallDistance >= (Float)this.minFallDist.getValue();
   }

   private boolean holdWaterBucket(boolean setSlot) {
      if (this.containsItem(mc.getPlayer().getHeldItem(), Item.getItemFromBlock(Blocks.web))) {
         return true;
      } else {
         for(int i = 0; i < InventoryPlayer.getHotbarSize(); ++i) {
            if (this.containsItem(mc.getPlayer().inventory.mainInventory[i], Item.getItemFromBlock(Blocks.web)) && setSlot) {
               mc.getPlayer().inventory.currentItem = i;
               return true;
            }
         }

         return false;
      }
   }

   private boolean holdSpiderweb(boolean setSlot) {
      if (this.containsItem(mc.getPlayer().getHeldItem(), Items.water_bucket)) {
         return true;
      } else {
         for(int i = 0; i < InventoryPlayer.getHotbarSize(); ++i) {
            if (this.containsItem(mc.getPlayer().inventory.mainInventory[i], Items.water_bucket) && setSlot) {
               mc.getPlayer().inventory.currentItem = i;
               return true;
            }
         }

         return false;
      }
   }

   private boolean containsItem(ItemStack itemStack, Item item) {
      return itemStack != null && itemStack.getItem() == item;
   }

   private void sendPlace() {
      mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(mc.getPlayer().getHeldItem()));
   }

   private int fallDistance() {
      int fallDist = -1;
      Vec3 pos = new Vec3(mc.getPlayer().posX, mc.getPlayer().posY, mc.getPlayer().posZ);
      int y = (int)Math.floor(pos.yCoord);
      if (pos.yCoord % 1.0D == 0.0D) {
         --y;
      }

      for(int i = y; i > -1; --i) {
         Block block = BlockUtils.getBlock(new BlockPos((int)Math.floor(pos.xCoord), i, (int)Math.floor(pos.zCoord)));
         if (!(block instanceof BlockAir) && !(block instanceof BlockSign)) {
            fallDist = y - i;
            break;
         }
      }

      return fallDist;
   }

   public static MovingObjectPosition wrayCast(double n, float n2, float n3) {
      Vec3 getPositionEyes = mc.getPlayer().getPositionEyes(1.0F);
      float n4 = -n2 * 0.017453292F;
      float n5 = -n3 * 0.017453292F;
      float cos = MathHelper.cos(n4 - 3.1415927F);
      float sin = MathHelper.sin(n4 - 3.1415927F);
      float n6 = -MathHelper.cos(n5);
      Vec3 vec3 = new Vec3((double)(sin * n6), (double)MathHelper.sin(n5), (double)(cos * n6));
      return mc.getWorld().rayTraceBlocks(getPositionEyes, getPositionEyes.addVector(vec3.xCoord * n, vec3.yCoord * n, vec3.zCoord * n), false, false, false);
   }
}
