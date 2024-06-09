package exhibition.module.impl.hud;

import exhibition.event.Event;
import exhibition.event.EventListener;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventRenderGui;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class ArmorStatus extends Module {
   public ArmorStatus(ModuleData data) {
      super(data);
   }

   public EventListener.Priority getPriority() {
      return EventListener.Priority.HIGH;
   }

   @RegisterEvent(
      events = {EventRenderGui.class}
   )
   public void onEvent(Event e) {
      EventRenderGui event = (EventRenderGui)e;
      GL11.glPushMatrix();
      List stuff = new ArrayList();
      boolean onwater = mc.thePlayer.isEntityAlive() && mc.thePlayer.isInsideOfMaterial(Material.water);
      int split = -3;

      ItemStack errything;
      for(int index = 3; index >= 0; --index) {
         errything = mc.thePlayer.inventory.armorInventory[index];
         if (errything != null) {
            stuff.add(errything);
         }
      }

      if (mc.thePlayer.getCurrentEquippedItem() != null) {
         stuff.add(mc.thePlayer.getCurrentEquippedItem());
      }

      Iterator var8 = stuff.iterator();

      while(var8.hasNext()) {
         errything = (ItemStack)var8.next();
         if (mc.theWorld != null) {
            RenderHelper.enableGUIStandardItemLighting();
            split += 16;
         }

         GlStateManager.pushMatrix();
         GlStateManager.disableAlpha();
         GlStateManager.clear(256);
         mc.getRenderItem().zLevel = -150.0F;
         mc.getRenderItem().func_180450_b(errything, split + event.getResolution().getScaledWidth() / 2 - 4, event.getResolution().getScaledHeight() - (onwater ? 65 : 55));
         mc.getRenderItem().renderItemOverlays(mc.fontRendererObj, errything, split + event.getResolution().getScaledWidth() / 2 - 4, event.getResolution().getScaledHeight() - (onwater ? 65 : 55));
         mc.getRenderItem().zLevel = 0.0F;
         GlStateManager.disableBlend();
         GlStateManager.scale(0.5D, 0.5D, 0.5D);
         GlStateManager.disableDepth();
         GlStateManager.disableLighting();
         GlStateManager.enableDepth();
         GlStateManager.scale(2.0F, 2.0F, 2.0F);
         GlStateManager.enableAlpha();
         GlStateManager.popMatrix();
         errything.getEnchantmentTagList();
      }

      GL11.glPopMatrix();
   }
}
