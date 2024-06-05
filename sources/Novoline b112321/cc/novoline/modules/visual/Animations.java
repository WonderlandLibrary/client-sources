package cc.novoline.modules.visual;

import cc.novoline.events.EventTarget;
import cc.novoline.events.events.Render2DEvent;
import cc.novoline.events.events.TickUpdateEvent;
import cc.novoline.gui.screen.setting.Manager;
import cc.novoline.gui.screen.setting.Setting;
import cc.novoline.gui.screen.setting.SettingType;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.EnumModuleType;
import cc.novoline.modules.ModuleManager;
import cc.novoline.modules.configurations.annotation.Property;
import cc.novoline.modules.configurations.property.object.BooleanProperty;
import cc.novoline.modules.configurations.property.object.DoubleProperty;
import cc.novoline.modules.configurations.property.object.FloatProperty;
import cc.novoline.modules.configurations.property.object.IntProperty;
import cc.novoline.modules.configurations.property.object.PropertyFactory;
import cc.novoline.modules.configurations.property.object.StringProperty;
import cc.novoline.modules.visual.HUD;

public final class Animations extends AbstractModule {
   private float heightSmooth;
   @Property("type")
   private final StringProperty type = PropertyFactory.createString("Swank").acceptableValues(new String[]{"Swank", "Swing", "Swang", "Swong", "Swaing", "Punch", "Stella", "Styles", "Slide", "Interia", "Ethereal", "1.7", "Sigma", "Exhibition", "Smooth", "Spinning"});
   @Property("hit")
   private final StringProperty hit = PropertyFactory.createString("Vanilla").acceptableValues(new String[]{"Vanilla", "Smooth"});
   @Property("slowdown")
   private final IntProperty slowdown = (IntProperty)((IntProperty)PropertyFactory.createInt(Integer.valueOf(0)).minimum(Integer.valueOf(-4))).maximum(Integer.valueOf(6));
   @Property("downscale-factor")
   private final DoubleProperty downscaleFactor = (DoubleProperty)((DoubleProperty)PropertyFactory.createDouble(Double.valueOf(0.3D)).minimum(Double.valueOf(0.0D))).maximum(Double.valueOf(0.5D));
   @Property("hit-height")
   private final FloatProperty height = (FloatProperty)((FloatProperty)PropertyFactory.createFloat(Float.valueOf(10.0F)).minimum(Float.valueOf(-10.0F))).maximum(Float.valueOf(30.0F));
   @Property("block-height")
   private final FloatProperty block_height = (FloatProperty)((FloatProperty)PropertyFactory.createFloat(Float.valueOf(10.0F)).minimum(Float.valueOf(0.0F))).maximum(Float.valueOf(60.0F));
   @Property("rotating")
   private final BooleanProperty rotating = PropertyFactory.createBoolean(Boolean.valueOf(false));

   public Animations(ModuleManager var1) {
      super(var1, "Animations", EnumModuleType.VISUALS, "AutoBlock/Hit animations");
      Manager.put(new Setting("Anim_Mode", "Block", SettingType.COMBOBOX, this, this.type));
      Manager.put(new Setting("HIT_ANIM_MODE", "Hit", SettingType.COMBOBOX, this, this.hit));
      Manager.put(new Setting("SWING_SLOW", "Slowdown", SettingType.SLIDER, this, this.slowdown, 1.0D));
      Manager.put(new Setting("SCALE_FACTOR", "Downscale Factor", SettingType.SLIDER, this, this.downscaleFactor, 0.1D));
      Manager.put(new Setting("HIT_HEIGHT", "Hit Height", SettingType.SLIDER, this, this.height, 1.0D));
      Manager.put(new Setting("BLOCK_HEIGHT", "Block Height", SettingType.SLIDER, this, this.block_height, 1.0D));
      Manager.put(new Setting("BLOCK_ROTATING", "Rotating", SettingType.CHECKBOX, this, this.rotating));
   }

   @EventTarget
   public void shouldSlowSwing(TickUpdateEvent var1) {
      this.setSuffix((String)this.type.get());
   }

   @EventTarget
   public void onRender(Render2DEvent var1) {
      int var2 = HUD.e();
      float var3 = 20.0F - (this.mc.player.isSwingInProgress && this.mc.player.isBlocking()?((Float)this.block_height.get()).floatValue():(this.mc.player.getHeldItem() == null?0.0F:((Float)this.height.get()).floatValue()));
      if(this.heightSmooth < var3) {
         ++this.heightSmooth;
      }

      if(this.heightSmooth > var3) {
         --this.heightSmooth;
      }

   }

   public StringProperty getAnim() {
      return this.type;
   }

   public IntProperty getSlowdown() {
      return this.slowdown;
   }

   public DoubleProperty getDownscaleFactor() {
      return this.downscaleFactor;
   }

   public StringProperty getHit() {
      return this.hit;
   }

   public float getHeight() {
      return this.heightSmooth;
   }

   public boolean getRotating() {
      return ((Boolean)this.rotating.get()).booleanValue();
   }

   public void onEnable() {
      this.setSuffix((String)this.type.get());
   }
}
