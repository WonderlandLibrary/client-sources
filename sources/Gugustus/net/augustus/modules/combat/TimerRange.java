package net.augustus.modules.combat;

import java.awt.Color;
import java.util.ArrayList;

import net.augustus.events.*;
import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.settings.BooleanValue;
import net.augustus.settings.DoubleValue;
import net.augustus.settings.StringValue;
import net.augustus.utils.TimeHelper;
import net.lenni0451.eventapi.reflection.EventTarget;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

public class TimerRange extends Module {
   private int counter = -1;
   public boolean freezing;
   private int last;
   private boolean pls_do_the_timer_momento;
   private boolean newFunny;
   private boolean reset;
   public final StringValue mode = new StringValue(9284, "Mode", this, "TimerRange", new String[]{"TimerRange", "Shidori", "Vestige"});
   private final DoubleValue ticks = new DoubleValue(2385, "Ticks", this, 10, 0, 40, 0);
   private final DoubleValue lowTimer = new DoubleValue(624, "FirstTimer", this, 0.3D, 0.1D, 20, 2);
   private final DoubleValue highTimer = new DoubleValue(73, "NextTimer", this, 3.0D, 0.1D, 20, 2);
   public BooleanValue onWorld = new BooleanValue(123, "DisableOnWorld", this, false);
   @EventTarget
   public void onWorld(EventWorld eventWorld) {
      if(onWorld.getBoolean()) {
         setToggled(false);
      }
   }

   public TimerRange() {
      super("TickBase", new Color(23, 233, 123), Categorys.COMBAT);
   }

   @Override
   public void onEnable() {
      counter = -1;
      freezing = false;
      try {
         last = mc.thePlayer.ticksExisted;
      } catch (NullPointerException exception) {
         last = 0;
      }
   }

   @EventTarget
   public void onRender(EventRender3D eventRender3D) {
      switch (mode.getSelected()) {
         case "Vestige": {
            if(freezing) {
               mc.timer.renderPartialTicks = 0F;
            }
            break;
         }
      }
   }

   public int getExtraTicks() {
      if(counter-- > 0) {
         return -1;
      } else {
         freezing = false;
      }

      if(mm.killAura.isToggled() && (KillAura.target == null || mc.thePlayer.getDistanceToEntity(KillAura.target) > mm.killAura.rangeSetting.getValue())) {
         if(mm.killAura.findTarget(true, mm.killAura.preRange.getValue() + 0.75) != null && mc.thePlayer.hurtTime <= 2) {
            return counter = (int) ticks.getValue();
         }
      }

      return 0;
   }

   @EventTarget
   public void onPost(EventPostMotion eventPostMotion) {
      switch (mode.getSelected()) {
         case "Vestige": {
            if(freezing) {
               mc.thePlayer.posX = mc.thePlayer.lastTickPosX;
               mc.thePlayer.posY = mc.thePlayer.lastTickPosY;
               mc.thePlayer.posZ = mc.thePlayer.lastTickPosZ;
            }
            break;
         }
      }
   }

   @EventTarget
   public void onUpdate(EventUpdate event) {
      switch (mode.getSelected()) {
         case "TimerRange": {
            if(mc.thePlayer.ticksExisted == 5) {
               reset = true;
               newFunny = false;
               pls_do_the_timer_momento = false;
               last = 0;
            }
            if(reset) {
               mc.timer.timerSpeed = 1F;
               reset = false;
            }
            if(newFunny) {
               mc.timer.timerSpeed = ((float) highTimer.getValue());
               newFunny = false;
               reset = true;
            }
            if(pls_do_the_timer_momento) {
               mc.timer.timerSpeed = ((float) lowTimer.getValue());
               pls_do_the_timer_momento = false;
               newFunny = true;
            }
            break;
         }
      }
   }
   public void attack() {
      if((mc.thePlayer.ticksExisted - last) >= (int)ticks.getValue()) {
         pls_do_the_timer_momento = true;
         last = mc.thePlayer.ticksExisted;
      }
   }
}
