package cc.slack.features.modules.impl.combat;

import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.Value;
import cc.slack.features.modules.api.settings.impl.ModeValue;
import cc.slack.utils.client.mc;
import io.github.nevalackin.radbus.Listen;
import net.minecraft.item.ItemBow;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

@ModuleInfo(
   name = "FastBow",
   category = Category.COMBAT
)
public class FastBow extends Module {
   private final ModeValue<String> mode = new ModeValue(new String[]{"Vanilla"});

   public FastBow() {
      this.addSettings(new Value[]{this.mode});
   }

   @Listen
   public void onUpdate(UpdateEvent event) {
      String var2 = (String)this.mode.getValue();
      byte var3 = -1;
      switch(var2.hashCode()) {
      case 77115:
         if (var2.equals("NCP")) {
            var3 = 1;
         }
         break;
      case 1897755483:
         if (var2.equals("Vanilla")) {
            var3 = 0;
         }
      }

      switch(var3) {
      case 0:
         if (mc.getPlayer().getHealth() > 0.0F && (mc.getPlayer().onGround || mc.getPlayer().capabilities.isCreativeMode) && mc.getPlayer().inventory.getCurrentItem() != null && mc.getPlayer().inventory.getCurrentItem().getItem() instanceof ItemBow && mc.getGameSettings().keyBindUseItem.pressed) {
            mc.getPlayerController().sendUseItem(mc.getPlayer(), mc.getWorld(), mc.getPlayer().inventory.getCurrentItem());
            mc.getPlayer().inventory.getCurrentItem().getItem().onItemRightClick(mc.getPlayer().inventory.getCurrentItem(), mc.getWorld(), mc.getPlayer());

            for(int i = 0; i < 20; ++i) {
               mc.getPlayer().sendQueue.addToSendQueue(new C03PacketPlayer(false));
            }

            mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(0, 0, 0), EnumFacing.DOWN));
            mc.getPlayer().inventory.getCurrentItem().getItem().onPlayerStoppedUsing(mc.getPlayer().inventory.getCurrentItem(), mc.getWorld(), mc.getPlayer(), 0);
         }
      case 1:
      default:
      }
   }
}
