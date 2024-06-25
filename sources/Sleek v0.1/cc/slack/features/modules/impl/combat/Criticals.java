package cc.slack.features.modules.impl.combat;

import cc.slack.events.impl.network.PacketEvent;
import cc.slack.events.impl.player.AttackEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.Value;
import cc.slack.features.modules.api.settings.impl.BooleanValue;
import cc.slack.features.modules.api.settings.impl.ModeValue;
import cc.slack.utils.client.mc;
import cc.slack.utils.network.PacketUtil;
import io.github.nevalackin.radbus.Listen;
import net.minecraft.network.play.client.C03PacketPlayer;

@ModuleInfo(
   name = "Criticals",
   category = Category.COMBAT
)
public class Criticals extends Module {
   public final ModeValue<String> criticalMode = new ModeValue(new String[]{"Edit", "Vulcan", "Packet", "Mini"});
   public final BooleanValue onlyGround = new BooleanValue("Only Ground", true);
   private boolean spoof = false;

   public Criticals() {
      this.addSettings(new Value[]{this.criticalMode, this.onlyGround});
   }

   @Listen
   public void onAttack(AttackEvent event) {
      String var2 = ((String)this.criticalMode.getValue()).toLowerCase();
      byte var3 = -1;
      switch(var2.hashCode()) {
      case -995865464:
         if (var2.equals("packet")) {
            var3 = 2;
         }
         break;
      case -805359837:
         if (var2.equals("vulcan")) {
            var3 = 1;
         }
         break;
      case 3108362:
         if (var2.equals("edit")) {
            var3 = 0;
         }
         break;
      case 3351639:
         if (var2.equals("mini")) {
            var3 = 3;
         }
      }

      switch(var3) {
      case 0:
         this.spoof = true;
         break;
      case 1:
         this.sendPacket(0.16477328182606651D, false);
         this.sendPacket(0.08307781780646721D, false);
         this.sendPacket(0.0030162615090425808D, false);
         break;
      case 2:
         this.sendPacket(-0.07840000152D, false);
         break;
      case 3:
         this.sendPacket(1.0E-4D, true);
         this.sendPacket(0.0D, false);
      }

   }

   @Listen
   public void onPacket(PacketEvent event) {
      if (event.getPacket() instanceof C03PacketPlayer && this.spoof) {
         ((C03PacketPlayer)event.getPacket()).onGround = false;
         this.spoof = false;
      }

   }

   private void sendPacket(double yOffset, boolean ground) {
      PacketUtil.send(new C03PacketPlayer.C04PacketPlayerPosition(mc.getPlayer().posX, mc.getPlayer().posY + yOffset, mc.getPlayer().posZ, ground));
   }
}
