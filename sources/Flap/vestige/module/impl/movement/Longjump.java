package vestige.module.impl.movement;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Timer;
import vestige.event.Listener;
import vestige.event.impl.MoveEvent;
import vestige.event.impl.PreMotionEvent;
import vestige.module.Category;
import vestige.module.Module;
import vestige.setting.AbstractSetting;
import vestige.setting.impl.ModeSetting;
import vestige.util.player.MovementUtil;

public class Longjump extends Module {
   public final ModeSetting mode = new ModeSetting("Mode", "Hypixel", new String[]{"Hypixel", "None"});
   public final ModeSetting submode = new ModeSetting("Sub Mode", () -> {
      return this.mode.is("Hypixel");
   }, "Keep Y", new String[]{"Keep Y", "None"});
   private int ticks = 0;
   private boolean start;
   private boolean done;
   public static boolean stopModules;
   private boolean waitForDamage = false;
   private int aimedTicks = Integer.MAX_VALUE;

   public Longjump() {
      super("Longjump", Category.MOVEMENT);
      this.addSettings(new AbstractSetting[]{this.mode, this.submode});
   }

   public void onEnable() {
      this.waitForDamage = true;
   }

   public boolean onDisable() {
      this.start = false;
      this.done = false;
      this.waitForDamage = false;
      this.aimedTicks = Integer.MAX_VALUE;
      this.ticks = 0;
      stopModules = false;
      return false;
   }

   @Listener
   public void onPremotion(PreMotionEvent event) {
      String var2 = this.submode.getMode();
      byte var3 = -1;
      switch(var2.hashCode()) {
      case -2051387746:
         if (var2.equals("Keep Y")) {
            var3 = 0;
         }
      }

      switch(var3) {
      case 0:
         if (!this.waitForDamage) {
            event.setYaw(mc.thePlayer.rotationYaw - 180.0F);
            event.setPitch(89.0F);
            if (this.aimedTicks == Integer.MAX_VALUE) {
               this.aimedTicks = mc.thePlayer.ticksExisted;
            }
         }

         if (!this.waitForDamage && mc.thePlayer.ticksExisted - this.aimedTicks >= 2) {
            int shouldSlot = this.getFireball();
            if (shouldSlot != mc.thePlayer.inventory.currentItem) {
               mc.thePlayer.inventory.currentItem = shouldSlot;
            } else {
               mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getCurrentItem());
               this.waitForDamage = true;
            }
         }

         Timer var10000;
         if (mc.thePlayer.hurtTime >= 2) {
            this.start = true;
            var10000 = mc.timer;
            var10000.timerSpeed = 0.7F;
            EntityPlayerSP var5 = mc.thePlayer;
            var5.motionX *= 1.1D;
            var5 = mc.thePlayer;
            var5.motionZ *= 1.1D;
         }

         if (this.start) {
            ++this.ticks;
         }

         if (this.ticks > 0 && this.ticks < 20) {
            var10000 = mc.timer;
            var10000.timerSpeed = 1.0F;
            MovementUtil.strafe();
         } else if (this.ticks >= 30) {
            this.done = true;
            this.start = false;
         }

         if (mc.thePlayer.hurtTime == 0 && this.done) {
            this.toggle();
            var10000 = mc.timer;
            var10000.timerSpeed = 1.0F;
         }
      default:
      }
   }

   public int getFireball() {
      int a = -1;

      for(int i = 0; i < 9; ++i) {
         ItemStack getStackInSlot = mc.thePlayer.inventory.getStackInSlot(i);
         if (getStackInSlot != null && getStackInSlot.getItem() == Items.fire_charge) {
            a = i;
            break;
         }
      }

      return a;
   }

   @Listener
   public void moveEvent(MoveEvent event) {
   }

   public String getSuffix() {
      return this.mode.getMode();
   }
}
