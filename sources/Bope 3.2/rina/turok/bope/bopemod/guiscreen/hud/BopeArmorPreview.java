package rina.turok.bope.bopemod.guiscreen.hud;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import rina.turok.bope.bopemod.guiscreen.render.pinnables.BopePinnable;

public class BopeArmorPreview extends BopePinnable {
   public BopeArmorPreview() {
      super("Armor Preview", "ArmorPreview", 1.0F, 0, 0);
   }

   public void render() {
      if (this.mc.player != null) {
         if (this.is_on_gui()) {
            this.background();
         }

         GlStateManager.pushMatrix();
         RenderHelper.enableGUIStandardItemLighting();
         int width = 64;
         InventoryPlayer inventory = this.mc.player.inventory;
         ItemStack boots = inventory.armorItemInSlot(0);
         ItemStack leggings = inventory.armorItemInSlot(1);
         ItemStack chestplace = inventory.armorItemInSlot(2);
         ItemStack helmet = inventory.armorItemInSlot(3);
         this.mc.getRenderItem().zLevel = 200.0F;
         if (helmet != null) {
            this.mc.getRenderItem().renderItemAndEffectIntoGUI(helmet, this.get_x() + 48, this.get_y());
            this.mc.getRenderItem().renderItemOverlayIntoGUI(this.mc.fontRenderer, helmet, this.get_x() + 48, this.get_y(), "");
         }

         if (chestplace != null) {
            this.mc.getRenderItem().renderItemAndEffectIntoGUI(chestplace, this.get_x() + 32, this.get_y());
            this.mc.getRenderItem().renderItemOverlayIntoGUI(this.mc.fontRenderer, chestplace, this.get_x() + 32, this.get_y(), "");
         }

         if (leggings != null) {
            this.mc.getRenderItem().renderItemAndEffectIntoGUI(leggings, this.get_x() + 16, this.get_y());
            this.mc.getRenderItem().renderItemOverlayIntoGUI(this.mc.fontRenderer, leggings, this.get_x() + 16, this.get_y(), "");
         }

         if (boots != null) {
            this.mc.getRenderItem().renderItemAndEffectIntoGUI(boots, this.get_x(), this.get_y());
            this.mc.getRenderItem().renderItemOverlayIntoGUI(this.mc.fontRenderer, boots, this.get_x(), this.get_y(), "");
         }

         this.set_height(19);
         this.set_width(width);
         this.mc.getRenderItem().zLevel = 0.0F;
         RenderHelper.disableStandardItemLighting();
         GlStateManager.popMatrix();
      }

   }
}
