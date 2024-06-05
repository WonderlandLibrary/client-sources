package cc.novoline.modules.visual;

import cc.novoline.events.EventTarget;
import cc.novoline.events.events.MotionUpdateEvent$State;
import cc.novoline.events.events.RenderEntityEvent;
import cc.novoline.gui.screen.setting.Manager;
import cc.novoline.gui.screen.setting.Setting;
import cc.novoline.gui.screen.setting.SettingType;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.EnumModuleType;
import cc.novoline.modules.ModuleManager;
import cc.novoline.modules.configurations.annotation.Property;
import cc.novoline.modules.configurations.property.object.BooleanProperty;
import cc.novoline.modules.configurations.property.object.ColorProperty;
import cc.novoline.modules.configurations.property.object.IntProperty;
import cc.novoline.modules.configurations.property.object.PropertyFactory;
import cc.novoline.modules.visual.HUD;
import java.awt.Color;
import java.util.EnumSet;
import java.util.function.Supplier;
import net.RU;
import org.jetbrains.annotations.NotNull;

public class Wings extends AbstractModule {
   private RU y = new RU();
   @Property("scale")
   private IntProperty z = (IntProperty)((IntProperty)PropertyFactory.createInt(Integer.valueOf(100)).minimum(Integer.valueOf(50))).maximum(Integer.valueOf(250));
   @Property("color")
   private ColorProperty x = PropertyFactory.createColor(Integer.valueOf(Color.WHITE.getRGB()));
   @Property("chroma")
   private BooleanProperty A = PropertyFactory.createBoolean(Boolean.valueOf(false));

   public Wings(@NotNull ModuleManager var1) {
      super(var1, EnumModuleType.VISUALS, "Wings");
      Manager.put(new Setting("wngs_scale", "Scale", SettingType.SLIDER, this, this.z, 5.0D));
      Manager.put(new Setting("wngs_color", "Color", SettingType.COLOR_PICKER, this, this.x, (EnumSet)null, this::lambda$new$0));
      Manager.put(new Setting("wngs_chroma", "Chroma", SettingType.CHECKBOX, this, this.A));
   }

   @EventTarget
   public void a(RenderEntityEvent var1) {
      int var2 = HUD.h();
      if(var1.f() == MotionUpdateEvent$State.PRE && var1.a() == this.mc.player) {
         this.y.a(this.mc.player, this.mc.timer.renderPartialTicks, ((Integer)this.z.get()).intValue(), ((Boolean)this.A.get()).booleanValue()?this.a(0, 10):this.x.getAwtColor());
      }

   }

   public Color a(int var1, int var2) {
      double var3 = Math.ceil((double)(System.currentTimeMillis() - (long)var1 * (long)var2)) / 11.0D;
      var3 = var3 % 360.0D;
      Color var5 = Color.getHSBColor((float)(var3 / 360.0D), this.x.getHSB()[1], this.x.getHSB()[2]);
      return new Color(var5.getRed(), var5.getGreen(), var5.getBlue());
   }

   private Boolean lambda$new$0() {
      int var1 = HUD.h();
      return Boolean.valueOf(!((Boolean)this.A.get()).booleanValue());
   }
}
