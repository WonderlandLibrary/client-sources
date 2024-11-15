package exhibition.module.impl.player;

import exhibition.event.Event;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventPacket;
import exhibition.event.impl.EventTick;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;
import exhibition.module.data.Setting;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import org.lwjgl.input.Keyboard;

public class InventoryWalk extends Module {
   private String CARRY = "CARRY";

   public InventoryWalk(ModuleData data) {
      super(data);
      this.settings.put(this.CARRY, new Setting(this.CARRY, false, "Carry items in crafting slots."));
   }

   @RegisterEvent(
      events = {EventPacket.class, EventTick.class}
   )
   public void onEvent(Event event) {
      if (!(mc.currentScreen instanceof GuiChat)) {
         if (event instanceof EventTick && mc.currentScreen != null) {
            if (Keyboard.isKeyDown(200)) {
               mc.thePlayer.rotationPitch -= 0.25F;
            }

            if (Keyboard.isKeyDown(208)) {
               mc.thePlayer.rotationPitch += 0.25F;
            }

            if (Keyboard.isKeyDown(203)) {
               mc.thePlayer.rotationYaw -= 0.5F;
            }

            if (Keyboard.isKeyDown(205)) {
               mc.thePlayer.rotationYaw += 0.5F;
            }
         }

         if (event instanceof EventPacket && ((Boolean)((Setting)this.settings.get(this.CARRY)).getValue()).booleanValue()) {
            EventPacket ep = (EventPacket)event;
            if (ep.isOutgoing() && ep.getPacket() instanceof C0DPacketCloseWindow) {
               ep.setCancelled(true);
            }
         }

      }
   }
}
