package rina.turok.bope.bopemod.guiscreen.hud;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import rina.turok.bope.bopemod.guiscreen.render.pinnables.BopePinnable;

public class BopeGappleCount extends BopePinnable {
   int gapples = 0;

   public BopeGappleCount() {
      super("Gapple Count", "GappleCount", 1.0F, 0, 0);
   }

   public void render() {
      if (this.mc.player != null) {
         if (this.is_on_gui()) {
            this.background();
         }

         GlStateManager.pushMatrix();
         RenderHelper.enableGUIStandardItemLighting();
         this.gapples = this.mc.player.inventory.mainInventory.stream().filter((stackx) -> {
            return stackx.getItem() == Items.GOLDEN_APPLE;
         }).mapToInt(ItemStack::getCount).sum();
         int off = 0;

         for(int i = 0; i < 45; ++i) {
            ItemStack stack = this.mc.player.inventory.getStackInSlot(i);
            ItemStack off_h = this.mc.player.getHeldItemOffhand();
            if (off_h.getItem() == Items.GOLDEN_APPLE) {
               off = off_h.stackSize;
            } else {
               off = 0;
            }

            if (stack.getItem() == Items.GOLDEN_APPLE) {
               this.mc.getRenderItem().renderItemAndEffectIntoGUI(stack, this.get_x() + this.docking(0, 16), this.get_y());
               this.create_line(Integer.toString(this.gapples + off), 18, 14 - this.get(Integer.toString(this.gapples + off), "height"));
            }
         }

         this.mc.getRenderItem().zLevel = 0.0F;
         RenderHelper.disableStandardItemLighting();
         GlStateManager.popMatrix();
         this.set_width(16 + this.get(Integer.toString(this.gapples) + off, "width") + 2);
         this.set_height(16);
      }

   }
}
