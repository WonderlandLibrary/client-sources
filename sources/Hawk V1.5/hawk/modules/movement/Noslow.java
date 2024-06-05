package hawk.modules.movement;

import hawk.events.Event;
import hawk.events.listeners.EventUpdate;
import hawk.modules.Module;
import hawk.settings.ModeSetting;
import hawk.settings.Setting;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class Noslow extends Module {
   public static boolean isnoslow = false;
   public ModeSetting mode = new ModeSetting("Mode", this, "NCP", new String[]{"Vanilla", "NCP"});

   public void onDisable() {
      isnoslow = false;
   }

   public void onEnable() {
      isnoslow = true;
   }

   public void onEvent(Event var1) {
      if (var1 instanceof EventUpdate && this.mode.is("NCP") && this.mc.thePlayer.isBlocking()) {
         if (var1.isPre()) {
            this.mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.UP));
         } else {
            this.mc.thePlayer.setItemInUse(this.mc.thePlayer.getCurrentEquippedItem(), 71999999);
            this.mc.playerController.sendUseItem(this.mc.thePlayer, this.mc.theWorld, this.mc.thePlayer.getCurrentEquippedItem());
         }
      }

   }

   public Noslow() {
      super("Noslow", 0, Module.Category.MOVEMENT);
      this.addSettings(new Setting[]{this.mode});
   }
}
