package exhibition.module.impl.hud;

import exhibition.Client;
import exhibition.event.Event;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventRenderGui;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;
import exhibition.util.RenderingUtil;
import exhibition.util.render.Colors;
import net.minecraft.client.gui.ScaledResolution;

public class EagleSoftware extends Module {
   public void onEnable() {
   }

   public void onDisable() {
   }

   public EagleSoftware(ModuleData data) {
      super(data);
   }

   @RegisterEvent(
      events = {EventRenderGui.class}
   )
   public void onEvent(Event event) {
      EventRenderGui er = (EventRenderGui)event;
      ScaledResolution r = er.getResolution();
      RenderingUtil.rectangle((double)(r.getScaledWidth() / 2 - 175), (double)(r.getScaledHeight() / 2 - 100), (double)(r.getScaledWidth() / 2 + 175), (double)(r.getScaledHeight() / 2 - 50), Colors.getColor(62, 225));
      RenderingUtil.rectangle((double)(r.getScaledWidth() / 2 - 175), (double)(r.getScaledHeight() / 2 - 50), (double)(r.getScaledWidth() / 2 + 175), (double)(r.getScaledHeight() / 2 + 150), Colors.getColor(34, 225));
      Client.eagleSoftware.drawCenteredString("a", (float)(r.getScaledWidth() / 2), (float)(r.getScaledHeight() / 2 - 75), -1);
      Client.eagleSoftware2.drawCenteredString("b c d i j", (float)(r.getScaledWidth() / 2), (float)(r.getScaledHeight() / 2 - 40), -1);
   }
}
