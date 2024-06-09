package exhibition.module.impl.combat;

import exhibition.event.Event;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventMotionUpdate;
import exhibition.event.impl.EventMouse;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;
import exhibition.module.data.Setting;
import exhibition.util.Timer;
import net.minecraft.entity.EntityLivingBase;
import org.lwjgl.input.Mouse;

public class AutoClicker extends Module {
   public static final String DELAY = "DELAY";
   public static final String RANDOM = "RANDOM";
   public static final String MIN = "MINRAND";
   public static final String MAX = "MAXRAND";
   public static final String MOUSE = "ON-MOUSE";
   public EntityLivingBase targ;
   Timer timer = new Timer();

   public AutoClicker(ModuleData data) {
      super(data);
      this.settings.put("DELAY", new Setting("DELAY", Integer.valueOf(100), "Base click delay.", 25.0D, 50.0D, 500.0D));
      this.settings.put("RANDOM", new Setting("RANDOM", true, "Randomize click delay."));
      this.settings.put("MINRAND", new Setting("MINRAND", Integer.valueOf(50), "Minimum click randomization.", 25.0D, 25.0D, 200.0D));
      this.settings.put("MAXRAND", new Setting("MAXRAND", Integer.valueOf(100), "Maximum click randomization.", 25.0D, 25.0D, 200.0D));
      this.settings.put("ON-MOUSE", new Setting("ON-MOUSE", true, "Click when mouse is held down."));
   }

   public static int randomNumber(int max, int min) {
      int ii = -min + (int)(Math.random() * (double)(max - -min + 1));
      return ii;
   }

   @RegisterEvent(
      events = {EventMotionUpdate.class, EventMouse.class}
   )
   public void onEvent(Event event) {
      if (event instanceof EventMotionUpdate) {
         EventMotionUpdate em = (EventMotionUpdate)event;
         if (em.isPre() && mc.currentScreen == null && mc.thePlayer.isEntityAlive()) {
            if (((Boolean)((Setting)this.settings.get("ON-MOUSE")).getValue()).booleanValue() && !Mouse.isButtonDown(0)) {
               return;
            }

            int delay = ((Number)((Setting)this.settings.get("DELAY")).getValue()).intValue();
            int minran = ((Number)((Setting)this.settings.get("MINRAND")).getValue()).intValue();
            int maxran = ((Number)((Setting)this.settings.get("MAXRAND")).getValue()).intValue();
            boolean random = ((Boolean)((Setting)this.settings.get("RANDOM")).getValue()).booleanValue();
            if (this.timer.delay((float)(delay + (random ? randomNumber(minran, maxran) : 0)))) {
               if (mc.thePlayer.isUsingItem()) {
                  mc.playerController.onStoppedUsingItem(mc.thePlayer);
               }

               mc.thePlayer.swingItem();
               mc.clickMouse();
               this.timer.reset();
            }
         }
      }

      if (event instanceof EventMouse) {
         EventMouse em = (EventMouse)event;
         if (em.getButtonID() == 1) {
            ;
         }
      }

   }
}
