package cc.novoline.modules.move;

import cc.novoline.events.EventTarget;
import cc.novoline.events.events.CollideWithBlockEvent;
import cc.novoline.events.events.EventState;
import cc.novoline.events.events.MotionUpdateEvent;
import cc.novoline.events.events.MoveEvent;
import cc.novoline.events.events.TickUpdateEvent;
import cc.novoline.gui.screen.setting.Manager;
import cc.novoline.gui.screen.setting.Setting;
import cc.novoline.gui.screen.setting.SettingType;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.EnumModuleType;
import cc.novoline.modules.ModuleManager;
import cc.novoline.modules.configurations.annotation.Property;
import cc.novoline.modules.configurations.property.object.PropertyFactory;
import cc.novoline.modules.configurations.property.object.StringProperty;
import cc.novoline.modules.move.Scaffold;
import cc.novoline.modules.move.Speed;
import cc.novoline.utils.ServerUtils;
import cc.novoline.utils.Servers;
import io.netty.util.internal.ThreadLocalRandom;
import net.minecraft.block.BlockLiquid;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

public final class WaterWalk extends AbstractModule {
   private int y;
   private int A;
   private boolean z;
   @Property("mode")
   private StringProperty x = PropertyFactory.createString("Solid").acceptableValues(new String[]{"Solid", "Bounce", "Dolphin"});

   public WaterWalk(ModuleManager var1) {
      super(var1, "WaterWalk", "Water Walk", EnumModuleType.MOVEMENT, "");
      Manager.put(new Setting("WW_MODE", "Mode", SettingType.COMBOBOX, this, this.x));
   }

   public void onEnable() {
      this.setSuffix((String)this.x.get());
   }

   public void onDisable() {
      this.mc.player.stepHeight = 0.625F;
      this.z = false;
      this.A = 0;
   }

   @EventTarget
   public void a(MotionUpdateEvent var1) {
      String var2 = Scaffold.r();
      if(var1.getState().equals(EventState.PRE) && !this.isEnabled(Speed.class)) {
         if(this.x.equals("Solid")) {
            if(!this.mc.player.movementInput().sneak() && this.mc.player.N() && !this.mc.player.isInLiquid()) {
               if(this.mc.player.fallDistance != 0.0F) {
                  return;
               }

               this.mc.player.stepHeight = 0.015625F;
               ++this.y;
               if(ServerUtils.isHypixel()) {
                  if(this.y == 1) {
                     var1.setY(var1.getY() - ThreadLocalRandom.current().nextDouble(0.015525D, 0.015625D));
                  }

                  if(this.y == 2) {
                     var1.setY(var1.getY() + ThreadLocalRandom.current().nextDouble(0.0149D, 0.015D));
                  }

                  if(this.y == 3) {
                     var1.setY(var1.getY() + ThreadLocalRandom.current().nextDouble(0.0199D, 0.02D));
                  }

                  if(this.y >= 4) {
                     var1.setY(var1.getY() + 0.015625D);
                     this.y = 0;
                  }

                  var1.setOnGround(this.mc.player.c(var1.getY()));
               }

               if(this.y % 2 == 0) {
                  var1.setY(var1.getY() - 1.0E-13D);
               }

               var1.setY(var1.getY() + 1.0E-13D);
            }

            this.y = 0;
            this.mc.player.stepHeight = 0.625F;
            if(this.mc.player.movementInput().sneak() || !this.mc.player.isInLiquid() || !this.neededLevel(this.mc.player.posX, this.mc.player.posY, this.mc.player.posZ) && !this.a(this.mc.player.posY - 1.0D) && !this.a(this.mc.player.posY + 1.0D)) {
               return;
            }

            this.mc.player.motionY = 0.11999998688698D;
         }

         if(this.x.equals("Bounce") && this.mc.player.isInLiquid() && !this.mc.player.movementInput().sneak()) {
            this.mc.player.motionY = 0.11999998688698D;
         }

         if(this.x.equals("Dolphin") && !this.mc.player.movementInput().sneak()) {
            if(this.mc.player.onGround || this.mc.player.isOnLadder()) {
               this.z = false;
            }

            if(this.mc.player.motionY > 0.0D && this.z) {
               if(this.mc.player.motionY <= 0.1127D) {
                  this.mc.player.motionY *= 1.267D;
               }

               this.mc.player.motionY += 0.05172D;
            }

            if(this.mc.player.isInLiquid()) {
               if(!this.waterUpper(var1)) {
                  if(this.A < 3) {
                     this.mc.player.motionY = 0.13D;
                     ++this.A;
                     this.z = false;
                  }

                  this.mc.player.motionY = 0.5D;
                  this.A = 0;
                  this.z = true;
               }

               if(!this.mc.player.movementInput().jump()) {
                  this.mc.player.motionY = 0.09D;
               }
            }
         }
      }

   }

   @EventTarget
   public void a(MoveEvent var1) {
      String var2 = Scaffold.r();
      if(ServerUtils.isHypixel() && !this.mc.player.movementInput().sneak() && !this.isEnabled(Speed.class)) {
         if(this.x.equals("Solid")) {
            if(!this.mc.player.N() || this.mc.player.isInLiquid()) {
               return;
            }

            var1.setMoveSpeed(!ServerUtils.serverIs(Servers.UHC) && !ServerUtils.serverIs(Servers.SG)?this.mc.player.l(true) * 0.98D:(this.mc.player.l(true) + this.mc.player.l(false)) / 2.0D);
         }

         if(this.x.equals("Bounce") && this.a(MathHelper.incValue(this.mc.player.posY, 1.0D) - 0.001D) && !this.a(this.mc.player.posY + 0.5D)) {
            var1.setMoveSpeed(!ServerUtils.serverIs(Servers.UHC) && !ServerUtils.serverIs(Servers.SG)?this.mc.player.l(true) * 0.98D:(this.mc.player.l(true) + this.mc.player.l(false)) / 2.0D);
         }
      }

   }

   @EventTarget
   public void a(CollideWithBlockEvent var1) {
      String var2 = Scaffold.r();
      if(this.x.equals("Solid") && var1.getBlock().getMaterial().isLiquid() && !this.mc.player.isInLiquid() && !this.mc.player.movementInput().sneak() && this.neededLevel((double)var1.getPos().getX(), (double)var1.getPos().getY(), (double)var1.getPos().getZ()) && !this.isEnabled(Speed.class)) {
         var1.setBoundingBox(new AxisAlignedBB(var1.getPos(), var1.getPos().a(1, 1, 1)));
      }

   }

   @EventTarget
   public void a(TickUpdateEvent var1) {
      this.setSuffix((String)this.x.get());
   }

   private boolean waterUpper(MotionUpdateEvent var1) {
      return this.mc.world.getBlockState(new BlockPos(var1.getX(), this.mc.player.getEntityBoundingBox().maxY - 1.0D, var1.getZ())).getBlock() instanceof BlockLiquid;
   }

   private boolean neededLevel(double var1, double var3, double var5) {
      String var7 = Scaffold.r();
      return ((Integer)((Comparable)this.mc.world.getBlockState(new BlockPos(var1, var3, var5)).getProperties().get(BlockLiquid.P))).intValue() < 4;
   }

   private boolean a(double var1) {
      return this.mc.world.getBlockState(new BlockPos(this.mc.player.posX, var1, this.mc.player.posZ)).getBlock().getMaterial().isLiquid();
   }

   public StringProperty a() {
      return this.x;
   }
}
