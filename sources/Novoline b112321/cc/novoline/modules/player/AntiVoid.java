package cc.novoline.modules.player;

import cc.novoline.events.EventTarget;
import cc.novoline.events.events.EventState;
import cc.novoline.events.events.MotionUpdateEvent;
import cc.novoline.events.events.TickUpdateEvent;
import cc.novoline.gui.screen.setting.Manager;
import cc.novoline.gui.screen.setting.Setting;
import cc.novoline.gui.screen.setting.SettingType;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.EnumModuleType;
import cc.novoline.modules.ModuleManager;
import cc.novoline.modules.configurations.annotation.Property;
import cc.novoline.modules.configurations.property.object.BooleanProperty;
import cc.novoline.modules.configurations.property.object.IntProperty;
import cc.novoline.modules.configurations.property.object.PropertyFactory;
import cc.novoline.modules.configurations.property.object.StringProperty;
import cc.novoline.modules.player.Freecam;
import net.minecraft.network.play.client.C03PacketPlayer$C04PacketPlayerPosition;

public final class AntiVoid extends AbstractModule {
   private boolean x;
   @Property("mode")
   private final StringProperty y = PropertyFactory.createString("Edit").acceptableValues(new String[]{"Motion", "Edit", "Packet"});
   @Property("fall-dist")
   private final IntProperty z = (IntProperty)((IntProperty)PropertyFactory.createInt(Integer.valueOf(20)).minimum(Integer.valueOf(5))).maximum(Integer.valueOf(50));
   @Property("void-check")
   private final BooleanProperty A = PropertyFactory.createBoolean(Boolean.valueOf(true));

   public AntiVoid(ModuleManager var1) {
      super(var1, "AntiVoid", "Anti Void", EnumModuleType.PLAYER, "do not fall retard");
      Manager.put(new Setting("AV_MODE", "Mode", SettingType.COMBOBOX, this, this.y));
      Manager.put(new Setting("AV_VOID_CHECK", "Void Check", SettingType.CHECKBOX, this, this.A));
      Manager.put(new Setting("AV_FALL_DIST", "Fall Distance", SettingType.SLIDER, this, this.z, 1.0D));
   }

   @EventTarget
   public void a(MotionUpdateEvent var1) {
      int[] var2 = Freecam.a();
      if(var1.getState() == EventState.PRE) {
         if((!this.mc.player.isBlockUnder() || !((Boolean)this.A.get()).booleanValue()) && this.mc.player.fallDistance > (float)((Integer)this.z.get()).intValue()) {
            if(!this.x) {
               return;
            }

            if(this.y.equals("Packet")) {
               this.sendPacketNoEvent(new C03PacketPlayer$C04PacketPlayerPosition(var1.getX(), var1.getY() + (double)this.mc.player.fallDistance, var1.getZ(), var1.isOnGround()));
            }

            if(this.y.equals("Edit")) {
               var1.setY(var1.getY() + (double)this.mc.player.fallDistance);
            }

            if(this.y.equals("Motion")) {
               this.mc.player.motionY = 4.0D;
            }

            this.x = false;
         }

         this.x = true;
      }

   }

   @EventTarget
   public void a(TickUpdateEvent var1) {
      this.setSuffix((String)this.y.get());
   }

   public void onEnable() {
      this.setSuffix((String)this.y.get());
   }
}
