package cc.novoline.modules.visual;

import cc.novoline.events.EventTarget;
import cc.novoline.events.events.MotionUpdateEvent$State;
import cc.novoline.events.events.RenderEntityEvent;
import cc.novoline.events.events.TickUpdateEvent;
import cc.novoline.gui.screen.setting.Manager;
import cc.novoline.gui.screen.setting.Setting;
import cc.novoline.gui.screen.setting.SettingType;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.EnumModuleType;
import cc.novoline.modules.ModuleManager;
import cc.novoline.modules.PlayerManager$EnumPlayerType;
import cc.novoline.modules.configurations.annotation.Property;
import cc.novoline.modules.configurations.property.object.BooleanProperty;
import cc.novoline.modules.configurations.property.object.ColorProperty;
import cc.novoline.modules.configurations.property.object.FloatProperty;
import cc.novoline.modules.configurations.property.object.ListProperty;
import cc.novoline.modules.configurations.property.object.PropertyFactory;
import cc.novoline.modules.visual.HUD;
import cc.novoline.utils.minecraft.FakeEntityPlayer;
import java.util.EnumSet;
import java.util.function.Supplier;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

public final class Chams extends AbstractModule {
   @Property("chams-visible")
   private final ColorProperty visible = PropertyFactory.createColor(Integer.valueOf(-7697665));
   @Property("chams-hidden")
   private final ColorProperty hidden = PropertyFactory.createColor(Integer.valueOf(-7697665));
   @Property("chams-hand")
   private final ColorProperty handColor = PropertyFactory.createColor(Integer.valueOf(-7697665));
   @Property("visible-alpha")
   private final FloatProperty visibleAlpha = (FloatProperty)((FloatProperty)PropertyFactory.createFloat(Float.valueOf(255.0F)).minimum(Float.valueOf(50.0F))).maximum(Float.valueOf(255.0F));
   @Property("pulse-speed")
   private final FloatProperty pulseSpeed = (FloatProperty)((FloatProperty)PropertyFactory.createFloat(Float.valueOf(10.0F)).minimum(Float.valueOf(5.0F))).maximum(Float.valueOf(20.0F));
   @Property("rainbow")
   private final BooleanProperty rainbow = PropertyFactory.booleanFalse();
   @Property("colored")
   private final BooleanProperty colored = PropertyFactory.booleanTrue();
   @Property("pulse")
   private final BooleanProperty pulse = PropertyFactory.booleanFalse();
   @Property("hand")
   private final BooleanProperty hand = PropertyFactory.booleanFalse();
   @Property("targets")
   private final ListProperty targets = PropertyFactory.createList((Object)"Players").acceptableValues((Object[])(new String[]{"Players", "Animals", "Mobs", "Passives"}));
   @Property("chams-onlyTargets")
   private final BooleanProperty onlyTargets = PropertyFactory.booleanFalse();
   @Property("chams-material")
   private final BooleanProperty material = PropertyFactory.booleanFalse();
   private boolean isF = false;
   private float pulseAlpha;

   public BooleanProperty isColored() {
      return this.colored;
   }

   public BooleanProperty isMaterial() {
      return this.material;
   }

   public Chams(ModuleManager var1) {
      super(var1, EnumModuleType.VISUALS, "Chams");
      Manager.put(new Setting("CTARGETS", "Targets", SettingType.SELECTBOX, this, this.targets));
      Manager.put(new Setting("ChamsColored", "Colored", SettingType.CHECKBOX, this, this.colored));
      Manager.put(new Setting("VISIBLE_CHAMS", "Visible", SettingType.COLOR_PICKER, this, this.visible, (EnumSet)null, this::lambda$new$0));
      Manager.put(new Setting("HIDDEN_CHAMS", "Hidden", SettingType.COLOR_PICKER, this, this.hidden, (EnumSet)null, this::lambda$new$1));
      SettingType var10004 = SettingType.SLIDER;
      FloatProperty var10006 = this.visibleAlpha;
      BooleanProperty var10008 = this.colored;
      this.colored.getClass();
      Manager.put(new Setting("VAlpha", "Alpha", var10004, this, var10006, 5.0D, var10008::get));
      var10004 = SettingType.CHECKBOX;
      BooleanProperty var6 = this.material;
      BooleanProperty var10007 = this.colored;
      this.colored.getClass();
      Manager.put(new Setting("CHAMS_MT", "Material", var10004, this, var6, var10007::get));
      var10004 = SettingType.CHECKBOX;
      var6 = this.rainbow;
      var10007 = this.colored;
      this.colored.getClass();
      Manager.put(new Setting("ChamsRainbow", "Rainbow", var10004, this, var6, var10007::get));
      var10004 = SettingType.CHECKBOX;
      var6 = this.pulse;
      var10007 = this.colored;
      this.colored.getClass();
      Manager.put(new Setting("PULSE", "Pulse", var10004, this, var6, var10007::get));
      var10004 = SettingType.CHECKBOX;
      var6 = this.hand;
      var10007 = this.colored;
      this.colored.getClass();
      Manager.put(new Setting("HAND", "Hand", var10004, this, var6, var10007::get));
      Manager.put(new Setting("HAND_CHAMS", "Hand color", SettingType.COLOR_PICKER, this, this.handColor, (EnumSet)null, this::lambda$new$2));
      Manager.put(new Setting("PS", "Pulse speed", SettingType.SLIDER, this, this.pulseSpeed, 1.0D, this::lambda$new$3));
      Manager.put(new Setting("ONLYTARGETS", "Targets Only", SettingType.CHECKBOX, this, this.onlyTargets));
   }

   public boolean isValid(EntityLivingBase var1) {
      int var2 = HUD.h();
      return !(var1 instanceof FakeEntityPlayer) && this.isValidType(var1) && var1.isEntityAlive() && !var1.isInvisible() && var1 != this.mc.player && (!((Boolean)this.onlyTargets.get()).booleanValue() || this.novoline.getPlayerManager().hasType(var1.getName(), PlayerManager$EnumPlayerType.TARGET));
   }

   private boolean isValidType(EntityLivingBase var1) {
      int var2 = HUD.e();
      return this.targets.contains("Players") && var1 instanceof EntityPlayer || this.targets.contains("Mobs") && (var1 instanceof EntityMob || var1 instanceof EntitySlime) || this.targets.contains("Passives") && (var1 instanceof EntityVillager || var1 instanceof EntityGolem) || this.targets.contains("Animals") && var1 instanceof EntityAnimal;
   }

   @EventTarget
   public void a(RenderEntityEvent var1) {
      int var2 = HUD.e();
      if(!((Boolean)this.colored.get()).booleanValue() && this.isValid((EntityLivingBase)var1.a())) {
         if(var1.f() == MotionUpdateEvent$State.PRE) {
            GL11.glEnable('耷');
            GL11.glPolygonOffset(1.0F, -1100000.0F);
         }

         GL11.glDisable('耷');
         GL11.glPolygonOffset(1.0F, 1100000.0F);
      }

   }

   @EventTarget
   public void onTick(TickUpdateEvent var1) {
      HUD.e();
      this.pulseAlpha = MathHelper.clamp_float(this.pulseAlpha, 0.0F, ((Float)this.visibleAlpha.get()).floatValue());
      if(!this.isF && this.pulseAlpha < ((Float)this.visibleAlpha.get()).floatValue()) {
         this.pulseAlpha = MathHelper.clamp_float(this.pulseAlpha + ((Float)this.pulseSpeed.get()).floatValue(), 0.0F, ((Float)this.visibleAlpha.get()).floatValue());
      }

      if(!this.isF && this.pulseAlpha == ((Float)this.visibleAlpha.get()).floatValue()) {
         this.isF = true;
      }

      if(this.isF && this.pulseAlpha > 0.0F) {
         this.pulseAlpha = MathHelper.clamp_float(this.pulseAlpha - ((Float)this.pulseSpeed.get()).floatValue(), 0.0F, ((Float)this.visibleAlpha.get()).floatValue());
      }

      if(this.isF && this.pulseAlpha == 0.0F) {
         this.isF = false;
      }

   }

   public ColorProperty getVisible() {
      return this.visible;
   }

   public ColorProperty getHidden() {
      return this.hidden;
   }

   public ColorProperty getHandColor() {
      return this.handColor;
   }

   public BooleanProperty getHand() {
      return this.hand;
   }

   public float getVisibleAlpha() {
      int var1 = HUD.e();
      return ((Boolean)this.pulse.get()).booleanValue()?this.pulseAlpha:((Float)this.visibleAlpha.get()).floatValue();
   }

   public BooleanProperty getRainbow() {
      return this.rainbow;
   }

   public ListProperty getTargets() {
      return this.targets;
   }

   public void onEnable() {
      this.pulseAlpha = 0.0F;
      this.isF = false;
   }

   private Boolean lambda$new$3() {
      int var1 = HUD.h();
      return Boolean.valueOf(((Boolean)this.pulse.get()).booleanValue() && ((Boolean)this.colored.get()).booleanValue());
   }

   private Boolean lambda$new$2() {
      int var1 = HUD.h();
      return Boolean.valueOf(!((Boolean)this.rainbow.get()).booleanValue() && ((Boolean)this.colored.get()).booleanValue() && ((Boolean)this.hand.get()).booleanValue());
   }

   private Boolean lambda$new$1() {
      int var1 = HUD.e();
      return Boolean.valueOf(!((Boolean)this.rainbow.get()).booleanValue() && ((Boolean)this.colored.get()).booleanValue());
   }

   private Boolean lambda$new$0() {
      int var1 = HUD.e();
      return Boolean.valueOf(!((Boolean)this.rainbow.get()).booleanValue() && ((Boolean)this.colored.get()).booleanValue());
   }
}
