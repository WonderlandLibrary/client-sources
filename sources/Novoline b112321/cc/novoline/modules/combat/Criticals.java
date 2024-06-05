package cc.novoline.modules.combat;

import cc.novoline.events.EventTarget;
import cc.novoline.events.events.EventState;
import cc.novoline.events.events.MotionUpdateEvent;
import cc.novoline.events.events.PacketEvent;
import cc.novoline.events.events.TickUpdateEvent;
import cc.novoline.gui.screen.setting.Manager;
import cc.novoline.gui.screen.setting.Setting;
import cc.novoline.gui.screen.setting.SettingType;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.EnumModuleType;
import cc.novoline.modules.ModuleManager;
import cc.novoline.modules.combat.KillAura;
import cc.novoline.modules.configurations.annotation.Property;
import cc.novoline.modules.configurations.property.object.PropertyFactory;
import cc.novoline.modules.configurations.property.object.StringProperty;
import cc.novoline.modules.exploits.Blink;
import cc.novoline.modules.move.Speed;
import cc.novoline.utils.ServerUtils;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.C03PacketPlayer$C04PacketPlayerPosition;
import net.minecraft.network.play.client.C0APacketAnimation;

public final class Criticals extends AbstractModule {
   @Property("mode")
   private StringProperty x = PropertyFactory.createString("Edit").acceptableValues(new String[]{"Edit", "Packet"});
   private float z;
   private float y;
   private double A;

   public Criticals(ModuleManager var1) {
      super(var1, "Criticals", EnumModuleType.COMBAT, "");
      Manager.put(new Setting("CRITICALS_MODE", "Mode", SettingType.COMBOBOX, this, this.x));
   }

   public boolean shouldCrit(KillAura var1) {
      int[] var2 = KillAura.Q();
      return this.isEnabled() && var1.shouldAttack() && var1.isValidEntity(var1.getTarget()) && !this.mc.at.f() && this.mc.player.onGround && !this.mc.player.N() && !this.mc.player.isInLiquid() && !this.mc.player.isInsideBlock() && !this.mc.player.movementInput().jump() && !((Blink)this.getModule(Blink.class)).isEnabled() && (!this.isEnabled(Speed.class) || !this.mc.player.isMoving());
   }

   private boolean a(Entity var1) {
      int[] var2 = KillAura.Q();
      return ((KillAura)this.getModule(KillAura.class)).p() <= 6L || var1.hurtResistantTime >= 18;
   }

   @EventTarget
   public void a(MotionUpdateEvent var1) {
      int[] var2 = KillAura.Q();
      if(var1.getState().equals(EventState.PRE) && this.x.equals("Edit") && this.isEnabled(KillAura.class)) {
         KillAura var3 = (KillAura)this.getModule(KillAura.class);
         if(this.shouldCrit(var3)) {
            Iterator var4 = var3.getTargetsFromRange().iterator();
            if(var4.hasNext()) {
               Entity var5 = (Entity)var4.next();
               int var6 = var5.hurtResistantTime;
               if(ServerUtils.isHypixel()) {
                  switch(var6) {
                  case 19:
                     var1.setOnGround(false);
                     var1.setY(var1.getY() + 0.0145D + ThreadLocalRandom.current().nextDouble(0.001D, 0.0011D));
                  case 18:
                     var1.setOnGround(false);
                     var1.setY(var1.getY() + 0.0105D + ThreadLocalRandom.current().nextDouble(0.001D, 0.0011D));
                  case 17:
                     var1.setOnGround(false);
                     var1.setY(var1.getY() + ThreadLocalRandom.current().nextDouble(0.001D, 0.0011D));
                  }
               }

               switch(var6) {
               case 17:
                  var1.setOnGround(false);
                  var1.setY(var1.getY() + ThreadLocalRandom.current().nextDouble(0.001D, 0.0011D));
               case 18:
                  var1.setOnGround(false);
                  var1.setY(var1.getY() + 0.0722435151D);
               case 19:
                  var1.setOnGround(false);
                  var1.setY(var1.getY() + ThreadLocalRandom.current().nextDouble(0.001D, 0.0011D));
               case 20:
                  var1.setOnGround(false);
                  var1.setY(var1.getY() + 0.0722435151D);
               }
            }
         }
      }

   }

   @EventTarget
   public void b(PacketEvent var1) {
      int[] var2 = KillAura.Q();
      if(this.x.equals("Packet") && var1.getPacket() instanceof C0APacketAnimation && this.isEnabled(KillAura.class)) {
         KillAura var3 = (KillAura)this.getModule(KillAura.class);
         if(this.shouldCrit(var3)) {
            Iterator var4 = var3.getTargetsFromRange().iterator();
            if(var4.hasNext()) {
               Entity var5 = (Entity)var4.next();
               if(this.a(var5)) {
                  if(ServerUtils.isHypixel() && !this.mc.isSingleplayer()) {
                     boolean var19 = true;
                  } else {
                     boolean var10000 = false;
                  }

                  double var7 = this.mc.player.posX;
                  double var9 = this.mc.player.posY;
                  double var11 = this.mc.player.posZ;
                  double var13 = 0.01125D;
                  double var15 = ThreadLocalRandom.current().nextDouble(0.001D, 0.0011D);
                  int var17 = 0;
                  if(var17 < 1) {
                     this.sendPacket(new C03PacketPlayer$C04PacketPlayerPosition(var7, var9 + var13 + var15, var11, false));
                     this.sendPacket(new C03PacketPlayer$C04PacketPlayerPosition(var7, var9 + var15, var11, false));
                     ++var17;
                  }
               }
            }
         }
      }

   }

   @EventTarget
   public void a(TickUpdateEvent var1) {
      this.setSuffix((String)this.x.get());
   }

   public void onEnable() {
      this.setSuffix((String)this.x.get());
   }

   public StringProperty a() {
      return this.x;
   }
}
