package net.augustus.modules.movement;

import net.augustus.events.EventUpdate;
import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.settings.DoubleValue;
import net.augustus.settings.StringValue;
import net.augustus.utils.MoveUtil;
import net.lenni0451.eventapi.reflection.EventTarget;

import java.awt.*;

public class LongJump extends Module {
   public StringValue mode = new StringValue(6932235, "Mode", this, "Vanilla", new String[]{"Vanilla"});
   public DoubleValue speed = new DoubleValue(44, "Speed", this, 3.4, 0, 9, 2);
   private boolean flag;

   public LongJump() {
      super("LongJump", Color.ORANGE, Categorys.MOVEMENT);
   }

   @Override
   public void onEnable() {
      super.onEnable();
      flag = false;
      switch (mode.getSelected()) {
         case "Vanilla": {
            mc.thePlayer.jump();
            break;
         }
      }
   }
   @EventTarget
   public void onUpdate(EventUpdate eventUpdate) {
      if(mc.thePlayer.onGround) {
         if (flag) {
            toggle();
         }
         flag = true;
      }
      switch (mode.getSelected()) {
         case "Vanilla": {
            if(mc.thePlayer.onGround) {
               MoveUtil.strafe(speed.getValue());
            } else {
               MoveUtil.strafe();
            }
            break;
         }
      }
   }
}