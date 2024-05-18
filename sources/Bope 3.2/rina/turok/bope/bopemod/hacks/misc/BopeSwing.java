package rina.turok.bope.bopemod.hacks.misc;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import rina.turok.bope.bopemod.BopeModule;
import rina.turok.bope.bopemod.guiscreen.settings.BopeSetting;
import rina.turok.bope.bopemod.hacks.BopeCategory;

public class BopeSwing extends BopeModule {
   BopeSetting sword = this.create("Only Sword", "BopeSwingOnlySword", false);

   public BopeSwing() {
      super(BopeCategory.BOPE_MISC, false);
      this.name = "Swing";
      this.tag = "Swing";
      this.description = "What is swing??";
      this.release("B.O.P.E - module - B.O.P.E");
   }

   public void update() {
      EntityRenderer Entity;
      ItemRenderer Item;
      boolean boo;
      if (this.sword.get_value(true)) {
         label18: {
            EntityPlayerSP Sp = this.mc.player;
            ItemStack Stack = Sp.getHeldItemMainhand();
            if (Stack.getItem() instanceof ItemSword) {
               Entity = this.mc.entityRenderer;
               Item = Entity.itemRenderer;
               if ((double)Item.prevEquippedProgressMainHand >= 0.9D) {
                  boo = true;
                  break label18;
               }
            }

            ItemStack var70 = Sp.getHeldItemMainhand();
            boo = false;
         }
      } else {
         boo = true;
      }

      if (boo) {
         Entity = this.mc.entityRenderer;
         Item = Entity.itemRenderer;
         Item.equippedProgressMainHand = 1.0F;
         Entity = this.mc.entityRenderer;
         Item = Entity.itemRenderer;
         EntityPlayerSP var10001 = this.mc.player;
         Item.itemStackMainHand = var10001.getHeldItemMainhand();
      }

   }
}
