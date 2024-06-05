package cc.novoline.modules.visual;

import cc.novoline.events.EventTarget;
import cc.novoline.events.events.Render2DEvent;
import cc.novoline.gui.screen.setting.Manager;
import cc.novoline.gui.screen.setting.Setting;
import cc.novoline.gui.screen.setting.SettingType;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.EnumModuleType;
import cc.novoline.modules.ModuleManager;
import cc.novoline.modules.configurations.annotation.Property;
import cc.novoline.modules.configurations.property.object.ColorProperty;
import cc.novoline.modules.configurations.property.object.IntProperty;
import cc.novoline.modules.configurations.property.object.ListProperty;
import cc.novoline.modules.configurations.property.object.PropertyFactory;
import cc.novoline.modules.configurations.property.object.StringProperty;
import cc.novoline.modules.visual.HUD;
import cc.novoline.utils.RenderUtils;
import java.awt.Color;
import java.util.EnumSet;
import java.util.function.Supplier;
import net.yS;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;

public final class ESP extends AbstractModule {
   yS D = new yS();
   @Property("mode")
   private StringProperty y = PropertyFactory.createString("Outline").acceptableValues(new String[]{"Shader", "Outline"});
   @Property("radius")
   private IntProperty C = (IntProperty)((IntProperty)PropertyFactory.createInt(Integer.valueOf(15)).minimum(Integer.valueOf(1))).maximum(Integer.valueOf(50));
   @Property("color")
   private final ColorProperty A = PropertyFactory.createColor(Integer.valueOf(-5751202));
   @Property("alpha")
   private final IntProperty x = (IntProperty)((IntProperty)PropertyFactory.createInt(Integer.valueOf(50)).minimum(Integer.valueOf(50))).maximum(Integer.valueOf(255));
   @Property("color-outline-mode")
   private final StringProperty z = PropertyFactory.createString("Static").acceptableValues(new String[]{"Team", "Static", "Rainbow", "Astolfo"});
   @Property("color-shader-mode")
   private final StringProperty B = PropertyFactory.createString("Static").acceptableValues(new String[]{"Static", "Rainbow", "Astolfo"});
   @Property("targets")
   private final ListProperty targets = PropertyFactory.createList((Object)"Players").acceptableValues((Object[])(new String[]{"Players", "Animals", "Mobs", "Passives"}));

   public ESP(ModuleManager var1) {
      super(var1, EnumModuleType.VISUALS, "Glow");
      Manager.put(new Setting("GLOW_MODE", "Mode", SettingType.COMBOBOX, this, this.y));
      Manager.put(new Setting("GLOW_TARGETS", "Targets", SettingType.SELECTBOX, this, this.targets));
      Manager.put(new Setting("GLOW_COLOR_MODE", "Outline Mode", SettingType.COMBOBOX, this, this.z, this::lambda$new$0));
      Manager.put(new Setting("GLOW_SHADER_MODE", "Shader Mode", SettingType.COMBOBOX, this, this.B, this::lambda$new$1));
      Manager.put(new Setting("GLOW_COLOR", "Color", SettingType.COLOR_PICKER, this, this.A, (EnumSet)null));
      Manager.put(new Setting("GLOW_SHADER_RADIUS", "Radius", SettingType.SLIDER, this, this.C, 1.0D, this::lambda$new$2));
      Manager.put(new Setting("CLOW_ALPHA", "Alpha", SettingType.SLIDER, this, this.x, 5.0D));
   }

   private boolean a(Entity var1) {
      int var2 = HUD.h();
      return this.targets.contains("Players") && var1 instanceof EntityPlayer || this.targets.contains("Mobs") && (var1 instanceof EntityMob || var1 instanceof EntitySlime) || this.targets.contains("Passives") && (var1 instanceof EntityVillager || var1 instanceof EntityGolem) || this.targets.contains("Animals") && var1 instanceof EntityAnimal;
   }

   public boolean b(Entity var1) {
      int var2 = HUD.h();
      return var1 != null && var1.isEntityAlive() && !var1.isInvisible() && var1 != this.mc.player && this.a(var1);
   }

   @EventTarget
   public void a(Render2DEvent var1) {
      int var2 = HUD.h();
      if(this.y.equals("Shader") && !this.targets.isEmpty()) {
         this.D.a(this, var1);
      }

   }

   public int[] j() {
      float[] var1 = this.A.getHSB();
      Color var2 = Color.getHSBColor(var1[0], var1[1], var1[2]);
      return new int[]{var2.getRed(), var2.getGreen(), var2.getBlue()};
   }

   public int[] f() {
      return RenderUtils.a(this.A);
   }

   public int[] h() {
      return RenderUtils.b(this.A);
   }

   public ColorProperty e() {
      return this.A;
   }

   public IntProperty a() {
      return this.x;
   }

   public StringProperty k() {
      return this.z;
   }

   public ListProperty d() {
      return this.targets;
   }

   public IntProperty g() {
      return this.C;
   }

   public StringProperty i() {
      return this.y;
   }

   public boolean b() {
      int var1 = HUD.h();
      return this.isEnabled() && this.y.equals("Shader") && !this.targets.isEmpty();
   }

   public StringProperty c() {
      return this.B;
   }

   private Boolean lambda$new$2() {
      return Boolean.valueOf(this.y.equals("Shader"));
   }

   private Boolean lambda$new$1() {
      return Boolean.valueOf(this.y.equals("Shader"));
   }

   private Boolean lambda$new$0() {
      return Boolean.valueOf(this.y.equals("Outline"));
   }
}
