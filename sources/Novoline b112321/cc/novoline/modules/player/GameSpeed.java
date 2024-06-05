package cc.novoline.modules.player;

import cc.novoline.events.EventTarget;
import cc.novoline.events.events.PacketDirection;
import cc.novoline.events.events.PacketEvent;
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
import cc.novoline.modules.configurations.property.object.FloatProperty;
import cc.novoline.modules.configurations.property.object.PropertyFactory;
import cc.novoline.modules.move.Scaffold;
import cc.novoline.modules.player.Freecam;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.MathHelper;
import org.jetbrains.annotations.NotNull;

public class GameSpeed extends AbstractModule {
   @Property("timer-boost")
   private final FloatProperty x = (FloatProperty)((FloatProperty)PropertyFactory.createFloat(Float.valueOf(2.0F)).minimum(Float.valueOf(1.0F))).maximum(Float.valueOf(9.0F));
   @Property("lag-check")
   private final BooleanProperty y = PropertyFactory.createBoolean(Boolean.valueOf(true));

   public GameSpeed(@NotNull ModuleManager var1) {
      super(var1, EnumModuleType.PLAYER, "GameSpeed", "Game Speed");
      Manager.put(new Setting("GS_LAG_BACK_CHECK", "Lagback check", SettingType.CHECKBOX, this, this.y));
      Manager.put(new Setting("GS_TIMER_BOOST", "Timer Speed", SettingType.SLIDER, this, this.x, 0.20000000298023224D));
   }

   @EventTarget
   public void a(TickUpdateEvent var1) {
      this.setSuffix(String.valueOf(MathHelper.b((double)((Float)this.x.get()).floatValue(), 1)));
   }

   @EventTarget
   public void a(PlayerUpdateEvent var1) {
      int[] var2 = Freecam.a();
      if(!this.isEnabled(Scaffold.class)) {
         this.mc.timer.timerSpeed = ((Float)this.x.get()).floatValue();
      }

   }

   @EventTarget
   public void b(PacketEvent var1) {
      int[] var2 = Freecam.a();
      if(var1.getDirection().equals(PacketDirection.INCOMING) && ((Boolean)this.y.get()).booleanValue() && var1.getPacket() instanceof S08PacketPlayerPosLook) {
         this.checkModule(this.getClass());
      }

   }

   public void onEnable() {
      this.setSuffix(String.valueOf(MathHelper.b((double)((Float)this.x.get()).floatValue(), 1)));
   }

   public void onDisable() {
      this.mc.timer.timerSpeed = 1.0F;
   }

   public float a() {
      return ((Float)this.x.get()).floatValue();
   }
}
