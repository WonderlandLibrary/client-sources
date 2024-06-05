package cc.novoline.modules.combat;

import cc.novoline.events.EventTarget;
import cc.novoline.events.events.LoadWorldEvent;
import cc.novoline.events.events.PacketDirection;
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
import cc.novoline.modules.configurations.property.object.BooleanProperty;
import cc.novoline.modules.configurations.property.object.IntProperty;
import cc.novoline.modules.configurations.property.object.PropertyFactory;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;

public final class Velocity extends AbstractModule {
   int z;
   @Property("horizontal")
   private final IntProperty horizontal = (IntProperty)((IntProperty)PropertyFactory.createInt(Integer.valueOf(0)).minimum(Integer.valueOf(-100))).maximum(Integer.valueOf(100));
   @Property("vertical")
   private final IntProperty vertical = (IntProperty)((IntProperty)PropertyFactory.createInt(Integer.valueOf(0)).minimum(Integer.valueOf(0))).maximum(Integer.valueOf(100));
   @Property("chance")
   private final IntProperty chance = (IntProperty)((IntProperty)PropertyFactory.createInt(Integer.valueOf(100)).minimum(Integer.valueOf(0))).maximum(Integer.valueOf(100));
   @Property("skip")
   private final BooleanProperty alerts = PropertyFactory.booleanFalse();

   public Velocity(ModuleManager var1) {
      super(var1, "Velocity", "Velocity", 0, EnumModuleType.COMBAT, "Don\'t take knockback");
      Manager.put(new Setting("VEL_HOR", "Horizontal", SettingType.SLIDER, this, this.horizontal, 5.0D));
      Manager.put(new Setting("VEL_VER", "Vertical", SettingType.SLIDER, this, this.vertical, 5.0D));
      Manager.put(new Setting("VEL_CHANCE", "Chance", SettingType.SLIDER, this, this.chance, 5.0D));
      Manager.put(new Setting("VEL_SKIP", "Skip", SettingType.CHECKBOX, this, this.alerts));
   }

   @EventTarget
   private void b(PacketEvent var1) {
      int[] var2 = KillAura.Q();
      if(var1.getDirection().equals(PacketDirection.INCOMING) && Math.random() <= (double)(((Integer)this.chance.get()).intValue() / 100)) {
         this.a(var1, ((Integer)this.horizontal.get()).intValue(), ((Integer)this.vertical.get()).intValue());
      }

   }

   public void a(PacketEvent var1, int var2, int var3) {
      int[] var4 = KillAura.Q();
      if(var1.getPacket() instanceof S12PacketEntityVelocity) {
         S12PacketEntityVelocity var5 = (S12PacketEntityVelocity)var1.getPacket();
         if(var5.getEntityID() == this.mc.player.getEntityID()) {
            ++this.z;
            if(!((Boolean)this.alerts.get()).booleanValue() || this.z % 2 == 0) {
               double var6 = (double)(var5.getMotionX() * var2 / 100);
               double var8 = (double)(var5.getMotionY() * var3 / 100);
               double var10 = (double)(var5.getMotionZ() * var2 / 100);
               if(var2 != 0) {
                  this.mc.player.motionX = var6 / 8000.0D;
                  this.mc.player.motionZ = var10 / 8000.0D;
               }

               if(var3 != 0) {
                  this.mc.player.motionY = var8 / 8000.0D;
               }

               var1.setCancelled(true);
            }
         }
      }

      if(var1.getPacket() instanceof S27PacketExplosion) {
         S27PacketExplosion var12 = (S27PacketExplosion)var1.getPacket();
         if(!((Boolean)this.alerts.get()).booleanValue() || this.z % 2 == 0) {
            double var13 = (double)(var12.getMotionX() * (float)var2 / 100.0F);
            double var14 = (double)(var12.getMotionY() * (float)var3 / 100.0F);
            double var15 = (double)(var12.getMotionZ() * (float)var2 / 100.0F);
            if(var2 != 0) {
               this.mc.player.motionX += var13;
               this.mc.player.motionZ += var15;
            }

            if(var3 != 0) {
               this.mc.player.motionY += var14;
            }

            var1.setCancelled(true);
         }
      }

   }

   @EventTarget
   public void onUpdate(TickUpdateEvent var1) {
      this.setSuffix(this.horizontal.get() + ".0% " + this.vertical.get() + ".0%");
   }

   @EventTarget
   public void a(LoadWorldEvent var1) {
      this.z = 0;
   }

   public void onEnable() {
      this.setSuffix(this.horizontal.get() + ".0% " + this.vertical.get() + ".0%");
   }

   public void onDisable() {
      this.z = 0;
   }
}
