package rina.turok.bope.bopemod.hacks.combat;

import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemEndCrystal;
import net.minecraft.item.ItemExpBottle;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.network.play.client.CPacketPlayerDigging.Action;
import net.minecraft.util.math.BlockPos;
import rina.turok.bope.bopemod.BopeModule;
import rina.turok.bope.bopemod.guiscreen.settings.BopeSetting;
import rina.turok.bope.bopemod.hacks.BopeCategory;

public class BopeFastUtil extends BopeModule {
   BopeSetting place = this.create("Place", "BopeFastPlace", true);
   BopeSetting break_ = this.create("Break", "BopeFastBreak", true);
   BopeSetting use = this.create("Use", "BopeFastUse", true);
   BopeSetting crystal = this.create("Crystal", "BopeFastCrystal", true);
   BopeSetting exp = this.create("EXP", "BopeFastExp", true);
   BopeSetting bow = this.create("Bow", "BopeFastBow", false);

   public BopeFastUtil() {
      super(BopeCategory.BOPE_COMBAT);
      this.name = "Fast Util";
      this.tag = "FastUtil";
      this.description = "Fast util for events and things in Minecraft.";
      this.release("B.O.P.E - Module - B.O.P.E");
   }

   public void update() {
      Item main = this.mc.player.getHeldItemMainhand().getItem();
      Item off = this.mc.player.getHeldItemOffhand().getItem();
      boolean main_exp = main instanceof ItemExpBottle;
      boolean off_exp = off instanceof ItemExpBottle;
      boolean main_cry = main instanceof ItemEndCrystal;
      boolean off_cry = off instanceof ItemEndCrystal;
      if (main_exp | off_exp && this.exp.get_value(true)) {
         this.mc.rightClickDelayTimer = 0;
      }

      if (main_cry | off_cry && this.crystal.get_value(true)) {
         this.mc.rightClickDelayTimer = 0;
      }

      if (!(main_exp | off_exp | main_cry | off_cry) && this.place.get_value(true)) {
         this.mc.rightClickDelayTimer = 0;
      }

      if (this.break_.get_value(true)) {
         this.mc.playerController.blockHitDelay = 0;
      }

      if (this.mc.player.inventory.getCurrentItem().getItem() instanceof ItemBow && this.bow.get_value(true) && this.mc.player.isHandActive() && this.mc.player.getItemInUseMaxCount() >= 3) {
         this.mc.player.connection.sendPacket(new CPacketPlayerDigging(Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, this.mc.player.getHorizontalFacing()));
         this.mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(this.mc.player.getActiveHand()));
         this.mc.player.stopActiveHand();
      }

   }
}
