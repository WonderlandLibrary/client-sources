package xyz.cucumber.base.events.ext;

import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import xyz.cucumber.base.events.Event;

public class EventRenderHotbar extends Event {
   private GuiIngame guiIngame;
   private float partialTicks;
   private ScaledResolution sr;

   public GuiIngame getGuiIngame() {
      return this.guiIngame;
   }

   public void setGuiIngame(GuiIngame guiIngame) {
      this.guiIngame = guiIngame;
   }

   public EventRenderHotbar(GuiIngame guiIngame, float partialTicks, ScaledResolution sr) {
      this.guiIngame = guiIngame;
      this.sr = sr;
      this.partialTicks = partialTicks;
   }

   public float getPartialTicks() {
      return this.partialTicks;
   }

   public void setPartialTicks(float partialTicks) {
      this.partialTicks = partialTicks;
   }

   public ScaledResolution getScaledResolution() {
      return this.sr;
   }

   public void setScaledResolution(ScaledResolution sr) {
      this.sr = sr;
   }
}
