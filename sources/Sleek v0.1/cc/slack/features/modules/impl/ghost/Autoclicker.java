package cc.slack.features.modules.impl.ghost;

import cc.slack.events.impl.render.RenderEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.Value;
import cc.slack.features.modules.api.settings.impl.BooleanValue;
import cc.slack.features.modules.api.settings.impl.ModeValue;
import cc.slack.features.modules.api.settings.impl.NumberValue;
import cc.slack.utils.client.mc;
import cc.slack.utils.other.TimeUtil;
import cc.slack.utils.player.AttackUtil;
import io.github.nevalackin.radbus.Listen;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemSword;

@ModuleInfo(
   name = "Autoclicker",
   category = Category.GHOST
)
public class Autoclicker extends Module {
   public final NumberValue<Float> targetCPS = new NumberValue("Target CPS", 11.0F, 0.0F, 30.0F, 0.1F);
   public final NumberValue<Float> randomizeAmount = new NumberValue("Randomization Amount", 1.5F, 0.0F, 4.0F, 0.1F);
   public final ModeValue<String> randomizeMode = new ModeValue("Randomization Pattern", new String[]{"NEW", "OLD", "EXTRA", "PATTERN1", "PATTERN2", "NONE"});
   public final BooleanValue onlySword = new BooleanValue("Only Sword", false);
   public final ModeValue<String> autoblockMode = new ModeValue("Autoblock", new String[]{"Off", "Click", "Normal", "BlockHit"});
   public final BooleanValue autoblockOnClick = new BooleanValue("Block On Mouse Down", true);
   public final BooleanValue triggerBot = new BooleanValue("Trigger Bot", false);
   private final TimeUtil leftClickTimer = new TimeUtil();
   private long leftClickDelay = 0L;

   public Autoclicker() {
      this.addSettings(new Value[]{this.targetCPS, this.randomizeAmount, this.randomizeMode, this.onlySword, this.autoblockMode, this.autoblockOnClick, this.triggerBot});
   }

   @Listen
   public void onRender(RenderEvent event) {
      if ((GameSettings.isKeyDown(mc.getGameSettings().keyBindAttack) || (Boolean)this.triggerBot.getValue() && mc.getMinecraft().objectMouseOver.entityHit != null) && (!(Boolean)this.onlySword.getValue() || mc.getPlayer().getHeldItem() != null && mc.getPlayer().getHeldItem().getItem() instanceof ItemSword) && !mc.getPlayerController().isHittingBlock) {
         if (this.leftClickTimer.hasReached(this.leftClickDelay)) {
            this.leftClickTimer.reset();
            this.leftClickDelay = this.updateDelay((Float)this.targetCPS.getValue(), (Float)this.randomizeAmount.getValue());
            KeyBinding.onTick(mc.getGameSettings().keyBindAttack.getKeyCode());
            if (((String)this.autoblockMode.getValue()).equals("Click") && (!(Boolean)this.autoblockOnClick.getValue() || GameSettings.isKeyDown(mc.getGameSettings().keyBindUseItem))) {
               KeyBinding.onTick(mc.getGameSettings().keyBindUseItem.getKeyCode());
            }
         }

         boolean rightMouseDown = GameSettings.isKeyDown(mc.getGameSettings().keyBindUseItem);
         String var3 = ((String)this.autoblockMode.getValue()).toLowerCase();
         byte var4 = -1;
         switch(var3.hashCode()) {
         case -1039745817:
            if (var3.equals("normal")) {
               var4 = 2;
            }
            break;
         case -664573978:
            if (var3.equals("blockhit")) {
               var4 = 3;
            }
            break;
         case 109935:
            if (var3.equals("off")) {
               var4 = 0;
            }
            break;
         case 94750088:
            if (var3.equals("click")) {
               var4 = 1;
            }
         }

         switch(var4) {
         case 0:
         default:
            break;
         case 1:
            rightMouseDown = false;
            break;
         case 2:
            rightMouseDown = (double)this.leftClickTimer.elapsed() > 0.1D * (double)this.leftClickDelay && (double)this.leftClickTimer.elapsed() < 0.65D * (double)this.leftClickDelay;
            break;
         case 3:
            rightMouseDown = (double)this.leftClickTimer.elapsed() < 0.4D * (double)this.leftClickDelay;
         }

         mc.getGameSettings().keyBindUseItem.pressed = rightMouseDown && (!(Boolean)this.autoblockOnClick.getValue() || GameSettings.isKeyDown(mc.getGameSettings().keyBindUseItem));
      }

   }

   private long updateDelay(Float cps, Float rand) {
      String var3 = ((String)this.randomizeMode.getValue()).toLowerCase();
      byte var4 = -1;
      switch(var3.hashCode()) {
      case 108960:
         if (var3.equals("new")) {
            var4 = 2;
         }
         break;
      case 110119:
         if (var3.equals("old")) {
            var4 = 1;
         }
         break;
      case 3387192:
         if (var3.equals("none")) {
            var4 = 0;
         }
         break;
      case 96965648:
         if (var3.equals("extra")) {
            var4 = 3;
         }
      }

      switch(var4) {
      case 0:
         return (long)(1000.0F / cps);
      case 1:
         return (long)(1000.0D / AttackUtil.getOldRandomization((double)cps, (double)rand));
      case 2:
         return (long)(1000.0D / AttackUtil.getNewRandomization((double)cps, (double)rand));
      case 3:
         return (long)(1000.0D / AttackUtil.getExtraRandomization((double)cps, (double)rand));
      default:
         return 0L;
      }
   }
}
