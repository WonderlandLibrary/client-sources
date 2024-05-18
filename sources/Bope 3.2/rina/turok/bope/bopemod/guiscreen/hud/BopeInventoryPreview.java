package rina.turok.bope.bopemod.guiscreen.hud;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import rina.turok.bope.bopemod.guiscreen.render.pinnables.BopePinnable;

public class BopeInventoryPreview extends BopePinnable {
   public BopeInventoryPreview() {
      super("Inventory Preview", "InventoryPreview", 1.0F, 0, 0);
   }

   public void render() {
      if (this.mc.player != null) {
         GlStateManager.pushMatrix();
         RenderHelper.enableGUIStandardItemLighting();
         this.background();
         this.set_width(144);
         this.set_height(48);

         for(int i = 0; i < 27; ++i) {
            ItemStack item_stack = (ItemStack)this.mc.player.inventory.mainInventory.get(i + 9);
            int item_position_x = this.get_x() + i % 9 * 16;
            int item_position_y = this.get_y() + i / 9 * 16;
            this.mc.getRenderItem().renderItemAndEffectIntoGUI(item_stack, item_position_x, item_position_y);
            this.mc.getRenderItem().renderItemOverlayIntoGUI(this.mc.fontRenderer, item_stack, item_position_x, item_position_y, (String)null);
         }

         this.mc.getRenderItem().zLevel = -5.0F;
         RenderHelper.disableStandardItemLighting();
         GlStateManager.popMatrix();
      }

   }
}
