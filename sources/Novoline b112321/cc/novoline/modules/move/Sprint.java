package cc.novoline.modules.move;

import cc.novoline.events.EventTarget;
import cc.novoline.events.events.MoveEvent;
import cc.novoline.events.events.PlayerUpdateEvent;
import cc.novoline.events.events.TickUpdateEvent;
import cc.novoline.gui.screen.setting.Manager;
import cc.novoline.gui.screen.setting.Setting;
import cc.novoline.gui.screen.setting.SettingType;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.EnumModuleType;
import cc.novoline.modules.ModuleManager;
import cc.novoline.modules.configurations.annotation.Property;
import cc.novoline.modules.configurations.property.object.BooleanProperty;
import cc.novoline.modules.configurations.property.object.PropertyFactory;
import cc.novoline.modules.move.FastSneak;
import cc.novoline.modules.move.Scaffold;

public final class Sprint extends AbstractModule {
   private int y;
   private boolean x;
   @Property("omni")
   private final BooleanProperty z = PropertyFactory.createBoolean(Boolean.valueOf(false));

   public Sprint(ModuleManager var1) {
      super(var1, "Sprint", "Sprint", 0, EnumModuleType.MOVEMENT);
      Manager.put(new Setting("OMNI", "Omni", SettingType.CHECKBOX, this, this.z));
   }

   private boolean a() {
      String var1 = Scaffold.r();
      return !this.isEnabled(FastSneak.class) && this.mc.player.movementInput().sneak();
   }

   public void onEnable() {
      this.setSuffix(((Boolean)this.z.get()).booleanValue()?"Omni":"");
   }

   @EventTarget
   public void a(TickUpdateEvent var1) {
      this.setSuffix(((Boolean)this.z.get()).booleanValue()?"Omni":"");
   }

   @EventTarget
   public void a(PlayerUpdateEvent var1) {
      String var2 = Scaffold.r();
      this.y = this.mc.player.onGround?this.y + 1:0;
      if(this.isEnabled(FastSneak.class) || !this.mc.player.movementInput().sneak()) {
         this.mc.player.setSprinting(this.mc.player.movementInput().getMoveForward() > 0.0F);
      }

   }

   @EventTarget
   public void a(MoveEvent var1) {
      String var2 = Scaffold.r();
      if(!this.isEnabled(Scaffold.class) && !this.mc.player.isCollidedHorizontally && ((Boolean)this.z.get()).booleanValue() && this.y > 1 && this.mc.player.isMoving() && !this.mc.player.isInLiquid() && !this.mc.player.N()) {
         var1.setMoveSpeed(this.mc.player.b(this.mc.player.m(true), 0.2D));
      }

   }
}
