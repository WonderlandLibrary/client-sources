package cc.novoline.modules.move;

import cc.novoline.Initializer;
import cc.novoline.events.EventTarget;
import cc.novoline.events.events.PlayerUpdateEvent;
import cc.novoline.events.events.StepConfirmEvent;
import cc.novoline.events.events.TickUpdateEvent;
import cc.novoline.gui.screen.setting.Manager;
import cc.novoline.gui.screen.setting.Setting;
import cc.novoline.gui.screen.setting.SettingType;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.EnumModuleType;
import cc.novoline.modules.ModuleManager;
import cc.novoline.modules.configurations.annotation.Property;
import cc.novoline.modules.configurations.property.object.FloatProperty;
import cc.novoline.modules.configurations.property.object.PropertyFactory;
import cc.novoline.modules.exploits.Phase;
import cc.novoline.modules.move.Scaffold;
import cc.novoline.modules.move.Speed;
import cc.novoline.modules.move.WaterWalk;
import cc.novoline.modules.player.GameSpeed;
import cc.novoline.utils.ServerUtils;
import cc.novoline.utils.Timer;
import net.minecraft.network.play.client.C03PacketPlayer$C04PacketPlayerPosition;

public class Step extends AbstractModule {
   private boolean resetTimer;
   private Timer timer = new Timer();
   private double[] x = new double[]{0.41999998688698D, 0.7531999805212D};
   private double[] D = new double[]{0.42D, 0.753D, 1.001D, 1.084D, 1.006D};
   private double[] z = new double[]{0.425D, 0.821D, 0.699D, 0.599D, 1.022D, 1.372D, 1.652D, 1.869D};
   private double[] E = new double[]{0.425D, 0.821D, 0.699D, 0.599D, 1.022D, 1.372D, 1.652D, 1.869D, 2.019D, 1.907D};
   private double[] A;
   @Property("height")
   private FloatProperty C;
   @Property("timer-speed")
   private FloatProperty F;

   public FloatProperty a() {
      return this.C;
   }

   public Step(ModuleManager var1) {
      super(var1, "Step", EnumModuleType.MOVEMENT, "");
      this.A = Initializer.getInstance().e;
      this.C = (FloatProperty)((FloatProperty)PropertyFactory.createFloat(Float.valueOf(1.5F)).minimum(Float.valueOf(1.0F))).maximum(Float.valueOf(2.5F));
      this.F = (FloatProperty)((FloatProperty)PropertyFactory.createFloat(Float.valueOf(0.5F)).minimum(Float.valueOf(0.4F))).maximum(Float.valueOf(1.0F));
      Manager.put(new Setting("STEP_HEIGHT", "Height", SettingType.SLIDER, this, this.C, 0.5D));
      Manager.put(new Setting("STEP_TIMER_SPEED", "Timer Speed", SettingType.SLIDER, this, this.F, 0.10000000149011612D));
   }

   public void onEnable() {
      this.setSuffix(((Float)this.C.get()).toString());
   }

   @EventTarget
   public void a(TickUpdateEvent var1) {
      this.setSuffix(((Float)this.C.get()).toString());
   }

   public void onDisable() {
      this.mc.timer.timerSpeed = 1.0F;
      this.mc.player.stepHeight = 0.625F;
   }

   @EventTarget
   public void onUpdate(PlayerUpdateEvent var1) {
      String var2 = Scaffold.r();
      if(this.isEnabled(Phase.class) || this.isEnabled(WaterWalk.class) && this.mc.player.N()) {
         this.mc.player.stepHeight = 0.015625F;
      }

      if(this.mc.player.isInLiquid() || this.mc.player.N() || this.isEnabled(Speed.class) || !this.mc.player.onGround) {
         this.mc.player.stepHeight = 0.625F;
      }

      this.mc.player.stepHeight = ((Float)this.C.get()).floatValue();
      if(this.resetTimer && !this.isEnabled(GameSpeed.class)) {
         this.mc.timer.timerSpeed = 1.0F;
         this.resetTimer = false;
      }

   }

   @EventTarget
   public void a(StepConfirmEvent var1) {
      double var3 = this.mc.player.posY;
      double var5 = this.mc.player.posX;
      Scaffold.r();
      double var7 = this.mc.player.posZ;
      double var9 = this.mc.player.getEntityBoundingBox().minY - var3;
      if(var9 > 0.625D && var9 <= 2.5D) {
         this.resetTimer = true;
         if(var9 <= 1.0D) {
            this.mc.timer.timerSpeed = ((Float)this.F.get()).floatValue();
            double var11 = 0.41999998688698D;
            double var13 = 0.7531999805212D;
            if(var9 != 1.0D) {
               var11 *= var9;
               var13 *= var9;
               if(var11 > 0.425D) {
                  var11 = 0.425D;
               }

               if(var13 > 0.78D) {
                  var13 = 0.78D;
               }

               if(var13 < 0.49D) {
                  var13 = 0.49D;
               }
            }

            this.sendPacket(new C03PacketPlayer$C04PacketPlayerPosition(var5, var3 + var11, var7, false));
            this.sendPacket(new C03PacketPlayer$C04PacketPlayerPosition(var5, var3 + var13, var7, false));
         }

         if(var9 <= 1.5D) {
            this.mc.timer.timerSpeed = ((Float)this.F.get()).floatValue() - 0.075F;
            double[] var16 = ServerUtils.isHypixel()?this.A:this.D;
            int var12 = var16.length;
            int var21 = 0;
            if(var21 < var12) {
               double var14 = var16[var21];
               this.sendPacket(new C03PacketPlayer$C04PacketPlayerPosition(var5, var3 + var14, var7, false));
               ++var21;
            }
         }

         if(var9 <= 2.0D) {
            this.mc.timer.timerSpeed = ((Float)this.F.get()).floatValue() - 0.15F;
            double[] var17 = this.z;
            int var19 = var17.length;
            int var23 = 0;
            if(var23 < var19) {
               double var27 = var17[var23];
               this.sendPacket(new C03PacketPlayer$C04PacketPlayerPosition(var5, var3 + var27, var7, false));
               ++var23;
            }
         }

         if(var9 > 2.0D) {
            this.mc.timer.timerSpeed = ((Float)this.F.get()).floatValue() - 0.225F;
            double[] var18 = this.E;
            int var20 = var18.length;
            int var25 = 0;
            if(var25 < var20) {
               double var28 = var18[var25];
               this.sendPacket(new C03PacketPlayer$C04PacketPlayerPosition(var5, var3 + var28, var7, false));
               ++var25;
            }
         }
      }

   }
}
