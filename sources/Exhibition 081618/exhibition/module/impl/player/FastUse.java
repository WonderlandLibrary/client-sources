package exhibition.module.impl.player;

import exhibition.event.Event;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventMotionUpdate;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;
import exhibition.module.data.Options;
import exhibition.module.data.Setting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class FastUse extends Module {
   private String MODE = "MODE";
   private String BOW = "BOW";
   private String TICKS = "TICKS";

   public FastUse(ModuleData data) {
      super(data);
      this.settings.put(this.MODE, new Setting(this.MODE, new Options("Use Mode", "Packet", new String[]{"Packet", "Timer"}), "Fast Use method."));
      this.settings.put(this.BOW, new Setting(this.BOW, false, "Fast Use with Bows."));
      this.settings.put(this.TICKS, new Setting(this.TICKS, Integer.valueOf(12), "Ticks reached to Fast Use.", 1.0D, 1.0D, 20.0D));
   }

   public void onEnable() {
      super.onEnable();
      mc.timer.timerSpeed = 1.0F;
   }

   public void onDisable() {
      super.onDisable();
      mc.timer.timerSpeed = 1.0F;
   }

   @RegisterEvent(
      events = {EventMotionUpdate.class}
   )
   public void onEvent(Event event) {
      if (event instanceof EventMotionUpdate) {
         EventMotionUpdate em = (EventMotionUpdate)event;
         if (em.isPre()) {
            String str = ((Options)((Setting)this.settings.get(this.MODE)).getValue()).getSelected();
            this.setSuffix(str);
            byte var5 = -1;
            switch(str.hashCode()) {
            case -1911998296:
               if (str.equals("Packet")) {
                  var5 = 1;
               }
               break;
            case 80811813:
               if (str.equals("Timer")) {
                  var5 = 0;
               }
            }

            switch(var5) {
            case 0:
               if (mc.thePlayer.getItemInUseDuration() > 13 && this.canUseItem(mc.thePlayer.getItemInUse().getItem())) {
                  mc.timer.timerSpeed = 1.3F;
               } else if (mc.timer.timerSpeed == 1.3F) {
                  mc.timer.timerSpeed = 1.0F;
               }
               break;
            case 1:
               if (mc.thePlayer.getItemInUseDuration() == ((Number)((Setting)this.settings.get(this.TICKS)).getValue()).intValue() && this.canUseItem(mc.thePlayer.getItemInUse().getItem())) {
                  for(int i = 0; i < 30; ++i) {
                     mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
                  }

                  mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                  mc.thePlayer.stopUsingItem();
               }
            }
         }
      }

   }

   private boolean canUseItem(Item item) {
      return !(item instanceof ItemSword) && (((Boolean)((Setting)this.settings.get(this.BOW)).getValue()).booleanValue() || !(item instanceof ItemBow));
   }
}
