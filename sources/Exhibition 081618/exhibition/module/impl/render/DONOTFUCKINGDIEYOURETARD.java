package exhibition.module.impl.render;

import exhibition.event.Event;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventRenderGui;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;
import java.awt.Color;

public class DONOTFUCKINGDIEYOURETARD extends Module {
   public DONOTFUCKINGDIEYOURETARD(ModuleData data) {
      super(data);
   }

   @RegisterEvent(
      events = {EventRenderGui.class}
   )
   public void onEvent(Event event) {
      if (event instanceof EventRenderGui) {
         EventRenderGui er = (EventRenderGui)event;
         int width = er.getResolution().getScaledWidth() / 2;
         int height = er.getResolution().getScaledHeight() / 2;
         String XD = "" + (int)mc.thePlayer.getHealth();
         int XDDD = mc.fontRendererObj.getStringWidth(XD);
         float health = mc.thePlayer.getHealth();
         if (health > 20.0F) {
            health = 20.0F;
         }

         int red = (int)Math.abs(health * 5.0F * 0.01F * 0.0F + (1.0F - health * 5.0F * 0.01F) * 255.0F);
         int green = (int)Math.abs(health * 5.0F * 0.01F * 255.0F + (1.0F - health * 5.0F * 0.01F) * 0.0F);
         Color customColor = (new Color(red, green, 0)).brighter();
         mc.fontRendererObj.drawStringWithShadow(XD, (float)(-XDDD / 2 + width), (float)(height - 17), customColor.getRGB());
      }

   }
}
