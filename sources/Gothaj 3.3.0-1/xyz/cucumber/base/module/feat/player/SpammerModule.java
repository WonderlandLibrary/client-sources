package xyz.cucumber.base.module.feat.player;

import java.util.Random;
import xyz.cucumber.base.events.EventListener;
import xyz.cucumber.base.events.ext.EventTick;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;
import xyz.cucumber.base.utils.StringUtils;
import xyz.cucumber.base.utils.Timer;

@ModuleInfo(
   category = Category.PLAYER,
   description = "",
   name = "Spammer"
)
public class SpammerModule extends Mod {
   private Timer t = new Timer();

   @Override
   public void onEnable() {
      this.t.reset();
   }

   @EventListener
   public void onTick(EventTick e) {
      String[] text = new String[]{
         "Co se tu deje? Cerez od Gothaje 3,0? Ja nevim, ale zaÂ§i si to sam. Kupuj Gothaj na buy,gothaj,vip",
         "NemÂ§ salÂ§mek?  Kupuj Gothaj na buy,gothaj,vip! 3,0 bude o chvili tady.",
         "Kveplej ma vypornej anticheat na cheatovani. Kupuj Gothaj na buy,gothaj,vip",
         "Profik bulÂ§ jak mala holka.  Kupuj Gothaj na buy,gothaj,vip"
      };
      if (this.t.hasTimeElapsed(25000.0, true)) {
         String chars = "";

         for (int i = 0; i < 6; i++) {
            chars = chars + StringUtils.generateRandomCharacterFromAlphaBet();
         }

         this.mc.thePlayer.sendChatMessage("!" + text[new Random().nextInt(text.length)] + " " + chars);
      }
   }
}
