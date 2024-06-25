package cc.slack.features.modules.impl.player;

import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.Value;
import cc.slack.features.modules.api.settings.impl.ModeValue;
import cc.slack.utils.client.mc;
import cc.slack.utils.network.PacketUtil;
import cc.slack.utils.player.MovementUtil;
import io.github.nevalackin.radbus.Listen;
import net.minecraft.item.ItemBucketMilk;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.network.play.client.C03PacketPlayer;

@ModuleInfo(
   name = "FastEat",
   category = Category.PLAYER
)
public class FastEat extends Module {
   private final ModeValue<String> mode = new ModeValue(new String[]{"Instant", "Clip"});
   double startY;

   public FastEat() {
      this.addSettings(new Value[]{this.mode});
   }

   @Listen
   public void onUpdate(UpdateEvent event) {
      if (mc.getPlayer().isUsingItem() && (mc.getPlayer().getItemInUse().getItem() instanceof ItemFood || mc.getPlayer().getItemInUse().getItem() instanceof ItemPotion || mc.getPlayer().getItemInUse().getItem() instanceof ItemBucketMilk)) {
         String var2 = (String)this.mode.getValue();
         byte var3 = -1;
         switch(var2.hashCode()) {
         case -672743999:
            if (var2.equals("Instant")) {
               var3 = 0;
            }
            break;
         case 2103152:
            if (var2.equals("Clip")) {
               var3 = 1;
            }
         }

         switch(var3) {
         case 0:
            PacketUtil.sendNoEvent(new C03PacketPlayer(mc.getPlayer().onGround), 30);
            break;
         case 1:
            this.startY = mc.getPlayer().posY;
            MovementUtil.resetMotion(false);
            if (mc.getPlayer().onGround) {
               if (mc.getPlayer().posY <= this.startY) {
                  mc.getPlayer().setPosition(mc.getPlayer().posX, mc.getPlayer().posY - 1.0E-8D, mc.getPlayer().posZ);
               }
            } else if (mc.getPlayer().posY <= this.startY) {
               PacketUtil.sendNoEvent(new C03PacketPlayer(false), 3);
            }
         }
      }

   }
}
