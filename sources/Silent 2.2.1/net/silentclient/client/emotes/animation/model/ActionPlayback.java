package net.silentclient.client.emotes.animation.model;

import net.silentclient.client.emotes.bobj.BOBJAction;
import net.silentclient.client.emotes.bobj.BOBJArmature;
import net.silentclient.client.emotes.bobj.BOBJBone;
import net.silentclient.client.emotes.bobj.BOBJGroup;

public class ActionPlayback {
   public BOBJAction action;
   public float ticks;
   public int duration;
   public int fade;
   public float speed = 1.0F;
   public boolean looping = false;
   public int repeat = -1;
   public boolean fading = false;
   public boolean playing = true;
   public int priority;

   public ActionPlayback(BOBJAction bobjaction) {
      this(bobjaction, false);
   }

   public ActionPlayback(BOBJAction bobjaction, boolean flag) {
      this.action = bobjaction;
      this.duration = bobjaction.getDuration();
      this.looping = flag;
   }

   public ActionPlayback(BOBJAction bobjaction, boolean flag, int i) {
      this(bobjaction, flag);
      this.priority = i;
   }

   public boolean isFinished() {
      return this.looping ? this.repeat == 0 && this.repeat >= 0 : this.ticks > (float) this.duration;
   }

   public void update() {
      if (this.playing) {
         if (this.looping || !this.looping && this.duration <= this.duration) {
            this.ticks += this.speed;
         }

         if (this.looping) {
            if (this.ticks >= (float) this.duration && this.speed > 0.0F) {
               this.ticks -= (float) this.duration;
               if (this.repeat > 0) {
                  --this.repeat;
               }
            } else if (this.ticks < 0.0F && this.speed < 0.0F) {
               this.ticks += (float) this.duration;
               if (this.repeat > 0) {
                  --this.repeat;
               }
            }
         }
      }
   }

   public float getTick(float f) {
      float f1 = this.ticks + f * this.speed;
      if (this.looping) {
         if (f1 >= (float) this.duration && this.speed > 0.0F) {
            f1 -= (float) this.duration;
            if (this.repeat > 0) {
               --this.repeat;
            }
         } else if (this.ticks < 0.0F && this.speed < 0.0F) {
            f1 += (float) this.duration;
            if (this.repeat > 0) {
               --this.repeat;
            }
         }
      }

      return f1;
   }

   public void apply(BOBJArmature bobjarmature, float f) {
      for (BOBJGroup bobjgroup : this.action.groups.values()) {
         BOBJBone bobjbone = bobjarmature.bones.get(bobjgroup.name);
         if (bobjbone != null) {
            bobjgroup.apply(bobjbone, this.getTick(f));
         }
      }
   }
}
