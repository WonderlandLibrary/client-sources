package exhibition.module.impl.render;

import exhibition.event.Event;
import exhibition.event.EventListener;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventRenderGui;
import exhibition.management.ColorManager;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;
import exhibition.module.data.Setting;
import exhibition.util.RenderingUtil;
import exhibition.util.render.Colors;
import net.minecraft.client.gui.ScaledResolution;

public class Crosshair extends Module {
   private String GAP = "GAP";
   private String WIDTH = "WIDTH";
   private String SIZE = "SIZE";
   private String DYNAMIC = "DYNAMIC";

   public Crosshair(ModuleData data) {
      super(data);
      this.settings.put(this.DYNAMIC, new Setting(this.DYNAMIC, true, "Expands when moving."));
      this.settings.put(this.GAP, new Setting(this.GAP, Integer.valueOf(5), "Crosshair Gap", 0.25D, 0.25D, 15.0D));
      this.settings.put(this.WIDTH, new Setting(this.WIDTH, Integer.valueOf(2), "Crosshair Width", 0.25D, 0.25D, 10.0D));
      this.settings.put(this.SIZE, new Setting(this.SIZE, Integer.valueOf(7), "Crosshair Size/Length", 0.25D, 0.25D, 15.0D));
   }

   public EventListener.Priority getPriority() {
      return EventListener.Priority.HIGH;
   }

   @RegisterEvent(
      events = {EventRenderGui.class}
   )
   public void onEvent(Event event) {
      double gap = ((Number)((Setting)this.settings.get(this.GAP)).getValue()).doubleValue();
      double width = ((Number)((Setting)this.settings.get(this.WIDTH)).getValue()).doubleValue();
      double size = ((Number)((Setting)this.settings.get(this.SIZE)).getValue()).doubleValue();
      ScaledResolution scaledRes = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
      RenderingUtil.rectangleBordered((double)(scaledRes.getScaledWidth() / 2) - width, (double)(scaledRes.getScaledHeight() / 2) - gap - size - (double)(this.isMoving() ? 2 : 0), (double)((float)(scaledRes.getScaledWidth() / 2) + 1.0F) + width, (double)(scaledRes.getScaledHeight() / 2) - gap - (double)(this.isMoving() ? 2 : 0), 0.5D, ColorManager.xhair.getColorInt(), Colors.getColor(0, 0, 0, ColorManager.xhair.alpha));
      RenderingUtil.rectangleBordered((double)(scaledRes.getScaledWidth() / 2) - width, (double)(scaledRes.getScaledHeight() / 2) + gap + 1.0D + (double)(this.isMoving() ? 2 : 0) - 0.15D, (double)((float)(scaledRes.getScaledWidth() / 2) + 1.0F) + width, (double)(scaledRes.getScaledHeight() / 2 + 1) + gap + size + (double)(this.isMoving() ? 2 : 0) - 0.15D, 0.5D, ColorManager.xhair.getColorInt(), Colors.getColor(0, 0, 0, ColorManager.xhair.alpha));
      RenderingUtil.rectangleBordered((double)(scaledRes.getScaledWidth() / 2) - gap - size - (double)(this.isMoving() ? 2 : 0) + 0.15D, (double)(scaledRes.getScaledHeight() / 2) - width, (double)(scaledRes.getScaledWidth() / 2) - gap - (double)(this.isMoving() ? 2 : 0) + 0.15D, (double)((float)(scaledRes.getScaledHeight() / 2) + 1.0F) + width, 0.5D, ColorManager.xhair.getColorInt(), Colors.getColor(0, 0, 0, ColorManager.xhair.alpha));
      RenderingUtil.rectangleBordered((double)(scaledRes.getScaledWidth() / 2 + 1) + gap + (double)(this.isMoving() ? 2 : 0), (double)(scaledRes.getScaledHeight() / 2) - width, (double)(scaledRes.getScaledWidth() / 2) + size + gap + 1.0D + (double)(this.isMoving() ? 2 : 0), (double)((float)(scaledRes.getScaledHeight() / 2) + 1.0F) + width, 0.5D, ColorManager.xhair.getColorInt(), Colors.getColor(0, 0, 0, ColorManager.xhair.alpha));
   }

   public boolean isMoving() {
      return ((Boolean)((Setting)this.settings.get(this.DYNAMIC)).getValue()).booleanValue() && !mc.thePlayer.isCollidedHorizontally && !mc.thePlayer.isSneaking() && (mc.thePlayer.movementInput.moveForward != 0.0F || mc.thePlayer.movementInput.moveStrafe != 0.0F);
   }
}
