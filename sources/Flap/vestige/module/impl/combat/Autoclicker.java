package vestige.module.impl.combat;

import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemSword;
import vestige.event.Listener;
import vestige.event.impl.RenderEvent;
import vestige.module.Category;
import vestige.module.Module;
import vestige.setting.AbstractSetting;
import vestige.setting.impl.BooleanSetting;
import vestige.setting.impl.IntegerSetting;
import vestige.setting.impl.ModeSetting;
import vestige.util.misc.TimerUtil;
import vestige.util.player.AttackUtil;
import vestige.util.util.TimeUtil;

public class Autoclicker extends Module {
   private boolean wasHoldingMouse;
   private boolean clickingTick;
   private final TimerUtil timer = new TimerUtil();
   private final IntegerSetting cpsvalue = new IntegerSetting("CPS", 17, 1, 25, 1);
   private final IntegerSetting randomizationamount = new IntegerSetting("Randomization Amount", 2, 0, 4, 1);
   private final ModeSetting randomization = new ModeSetting("Randomization", "Butterfly", new String[]{"Extra", "Butterfly", "Jitter", "NONE"});
   private final BooleanSetting onlysword = new BooleanSetting("Only Sword", false);
   private final BooleanSetting autoblockonclick = new BooleanSetting("Autoblock on Click", true);
   private final ModeSetting autoblock = new ModeSetting("Autoblock", "Off", new String[]{"Off", "Click", "Normal", "BlockHit"});
   private final BooleanSetting triggerbot = new BooleanSetting("Trigger Bot", false);
   public final TimeUtil leftClickTimer = new TimeUtil();
   public long leftClickDelay = 0L;

   public Autoclicker() {
      super("Autoclicker", Category.COMBAT);
      this.addSettings(new AbstractSetting[]{this.cpsvalue, this.randomization, this.randomizationamount, this.onlysword, this.autoblockonclick, this.autoblock, this.triggerbot});
   }

   public void onEnable() {
      this.wasHoldingMouse = false;
   }

   @Listener
   public void onRender(RenderEvent event) {
      if ((GameSettings.isKeyDown(mc.gameSettings.keyBindAttack) || this.triggerbot.isEnabled() && mc.objectMouseOver.entityHit != null) && (!this.onlysword.isEnabled() || mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemSword) && !mc.playerController.isHittingBlock) {
         TimeUtil var10000 = this.leftClickTimer;
         if (TimeUtil.hasReached(this.leftClickDelay)) {
            var10000 = this.leftClickTimer;
            TimeUtil.reset();
            this.leftClickDelay = this.updateDelay(this.cpsvalue.getValue(), (float)this.randomizationamount.getValue());
            KeyBinding.onTick(mc.gameSettings.keyBindAttack.getKeyCode());
            if (this.autoblock.getMode().equals("Click") && (!this.autoblockonclick.isEnabled() || GameSettings.isKeyDown(mc.gameSettings.keyBindUseItem))) {
               KeyBinding.onTick(mc.gameSettings.keyBindUseItem.getKeyCode());
            }
         }

         boolean rightMouseDown = GameSettings.isKeyDown(mc.gameSettings.keyBindUseItem);
         String var3 = this.autoblock.getMode().toLowerCase();
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
            boolean var5;
            label71: {
               var10000 = this.leftClickTimer;
               if ((double)TimeUtil.elapsed() > 0.1D * (double)this.leftClickDelay) {
                  var10000 = this.leftClickTimer;
                  if ((double)TimeUtil.elapsed() < 0.65D * (double)this.leftClickDelay) {
                     var5 = true;
                     break label71;
                  }
               }

               var5 = false;
            }

            rightMouseDown = var5;
            break;
         case 3:
            var10000 = this.leftClickTimer;
            rightMouseDown = (double)TimeUtil.elapsed() < 0.4D * (double)this.leftClickDelay;
         }

         mc.gameSettings.keyBindUseItem.pressed = rightMouseDown && (!this.autoblockonclick.isEnabled() || GameSettings.isKeyDown(mc.gameSettings.keyBindUseItem));
      }

   }

   private long updateDelay(int cps, Float rand) {
      String var3 = this.randomization.getMode().toLowerCase();
      byte var4 = -1;
      switch(var3.hashCode()) {
      case -1159737108:
         if (var3.equals("jitter")) {
            var4 = 2;
         }
         break;
      case 2467443:
         if (var3.equals("butterfly")) {
            var4 = 3;
         }
         break;
      case 3387192:
         if (var3.equals("none")) {
            var4 = 0;
         }
         break;
      case 96965648:
         if (var3.equals("extra")) {
            var4 = 1;
         }
      }

      switch(var4) {
      case 0:
         return (long)(1000 / cps);
      case 1:
         return (long)(1000.0D / AttackUtil.getExtraRandomization((double)cps, (double)rand));
      case 2:
         return (long)(1000.0D / AttackUtil.getPattern1Randomization((double)cps, (double)rand));
      case 3:
         return (long)(1000.0D / AttackUtil.getPattern2Randomization((double)cps, (double)rand));
      default:
         return 0L;
      }
   }
}
