package cc.novoline.modules.visual;

import cc.novoline.Novoline;
import cc.novoline.events.EventTarget;
import cc.novoline.events.events.Render3DEvent;
import cc.novoline.events.events.SettingEvent;
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
import cc.novoline.modules.configurations.property.object.IntProperty;
import cc.novoline.modules.configurations.property.object.ListProperty;
import cc.novoline.modules.configurations.property.object.PropertyFactory;
import cc.novoline.modules.visual.HUD;
import cc.novoline.utils.ColorUtils;
import cc.novoline.utils.RenderUtils;
import cc.novoline.utils.Timer;
import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Supplier;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;

public final class XRay extends AbstractModule {
   public static int alpha;
   public static boolean isEnabled;
   public static List blockIdList = Lists.newArrayList(new Integer[]{Integer.valueOf(10), Integer.valueOf(11), Integer.valueOf(8), Integer.valueOf(9), Integer.valueOf(14), Integer.valueOf(15), Integer.valueOf(16), Integer.valueOf(21), Integer.valueOf(41), Integer.valueOf(42), Integer.valueOf(46), Integer.valueOf(48), Integer.valueOf(52), Integer.valueOf(56), Integer.valueOf(57), Integer.valueOf(61), Integer.valueOf(62), Integer.valueOf(73), Integer.valueOf(74), Integer.valueOf(84), Integer.valueOf(89), Integer.valueOf(103), Integer.valueOf(116), Integer.valueOf(117), Integer.valueOf(118), Integer.valueOf(120), Integer.valueOf(129), Integer.valueOf(133), Integer.valueOf(137), Integer.valueOf(145), Integer.valueOf(152), Integer.valueOf(153), Integer.valueOf(154)});
   public static List blockPosList = new CopyOnWriteArrayList();
   private Timer timer = new Timer();
   @Property("opacity")
   private final IntProperty opacity = (IntProperty)((IntProperty)PropertyFactory.createInt(Integer.valueOf(160)).minimum(Integer.valueOf(0))).maximum(Integer.valueOf(255));
   @Property("esp")
   private final BooleanProperty esp = PropertyFactory.booleanTrue();
   @Property("tracers")
   private final BooleanProperty tracers = PropertyFactory.booleanTrue();
   @Property("esp-ores")
   private final ListProperty ores = PropertyFactory.createList((Object)"Diamond").acceptableValues((Object[])(new String[]{"Redstone", "Diamond", "Emerald", "Lapis", "Iron", "Coal", "Gold"}));
   @Property("distance")
   private final IntProperty distance = (IntProperty)((IntProperty)PropertyFactory.createInt(Integer.valueOf(42)).minimum(Integer.valueOf(16))).maximum(Integer.valueOf(64));
   @Property("chunk-update")
   private final BooleanProperty update = PropertyFactory.booleanFalse();
   @Property("delay")
   private DoubleProperty delay = (DoubleProperty)((DoubleProperty)PropertyFactory.createDouble(Double.valueOf(10.0D)).minimum(Double.valueOf(1.0D))).maximum(Double.valueOf(30.0D));

   public XRay(ModuleManager var1) {
      super(var1, "XRay", "XRay", 0, EnumModuleType.VISUALS);
      Manager.put(new Setting("ESP_ORES", "ESP Ores", SettingType.SELECTBOX, this, this.ores, this::lambda$new$0));
      Manager.put(new Setting("OPACITY", "Opacity", SettingType.SLIDER, this, this.opacity, 5.0D));
      Manager.put(new Setting("XRAY_DISTANCE", "Distance", SettingType.SLIDER, this, this.distance, 4.0D));
      Manager.put(new Setting("XR_TRACERS", "Tracers", SettingType.CHECKBOX, this, this.tracers));
      Manager.put(new Setting("XR_ESP", "ESP", SettingType.CHECKBOX, this, this.esp));
      Manager.put(new Setting("XR_UPDATE", "Chunks Update", SettingType.CHECKBOX, this, this.update));
      SettingType var10004 = SettingType.SLIDER;
      DoubleProperty var10006 = this.delay;
      BooleanProperty var10008 = this.update;
      this.update.getClass();
      Manager.put(new Setting("XR_DELAY", "Update Delay", var10004, this, var10006, 0.5D, var10008::get));
   }

   public void onEnable() {
      this.onToggle(true);
   }

   public void onDisable() {
      this.onToggle(false);
      this.timer.reset();
   }

   private void onToggle(boolean var1) {
      blockPosList.clear();
      this.mc.renderGlobal.loadRenderers();
      isEnabled = var1;
   }

   @EventTarget
   public void update(TickUpdateEvent var1) {
      int var2 = HUD.e();
      if(alpha != ((Integer)this.opacity.get()).intValue()) {
         this.mc.renderGlobal.loadRenderers();
         alpha = ((Integer)this.opacity.get()).intValue();
      }

      if(((Boolean)this.update.get()).booleanValue() && this.timer.delay(1000.0D * ((Double)this.delay.get()).doubleValue())) {
         this.mc.renderGlobal.loadRenderers();
         this.timer.reset();
      }

   }

   @EventTarget
   public void onRender3D(Render3DEvent var1) {
      int var2 = HUD.h();
      if(((Boolean)this.esp.get()).booleanValue()) {
         Iterator var3 = blockPosList.iterator();
         if(var3.hasNext()) {
            BlockPos var4 = (BlockPos)var3.next();
            if(this.mc.player.getDistance((double)var4.getX(), (double)var4.getZ()) <= (double)((Integer)this.distance.get()).intValue()) {
               Block var5 = this.mc.world.getBlockState(var4).getBlock();
               if(var5 == Blocks.diamond_ore && this.ores.contains("Diamond")) {
                  this.render3D(var4, 0, 255, 255);
               }

               if(var5 == Blocks.iron_ore && this.ores.contains("Iron")) {
                  this.render3D(var4, 225, 225, 225);
               }

               if(var5 == Blocks.lapis_ore && this.ores.contains("Lapis")) {
                  this.render3D(var4, 0, 0, 255);
               }

               if(var5 == Blocks.redstone_ore && this.ores.contains("Redstone")) {
                  this.render3D(var4, 255, 0, 0);
               }

               if(var5 == Blocks.coal_ore && this.ores.contains("Coal")) {
                  this.render3D(var4, 0, 30, 30);
               }

               if(var5 == Blocks.emerald_ore && this.ores.contains("Emerald")) {
                  this.render3D(var4, 0, 255, 0);
               }

               if(var5 == Blocks.gold_ore && this.ores.contains("Gold")) {
                  this.render3D(var4, 255, 255, 0);
               }
            }
         }
      }

   }

   private void render3D(BlockPos var1, int var2, int var3, int var4) {
      int var5 = HUD.e();
      if(((Boolean)this.esp.get()).booleanValue()) {
         RenderUtils.drawSolidBlockESP(var1, ColorUtils.getColor(var2, var3, var4));
      }

      if(((Boolean)this.tracers.get()).booleanValue()) {
         RenderUtils.drawLine(var1, ColorUtils.getColor(var2, var3, var4));
      }

   }

   @EventTarget
   public void onSetting(SettingEvent var1) {
      int var2 = HUD.e();
      if(var1.getSettingName().equals("XR_ESP") || var1.getSettingName().equals("XR_TRACERS")) {
         blockPosList.clear();
         this.mc.renderGlobal.loadRenderers();
      }

   }

   public static boolean showESP() {
      return ((Boolean)((XRay)Novoline.getInstance().getModuleManager().getModule(XRay.class)).esp.get()).booleanValue();
   }

   public static int getDistance() {
      return ((Integer)((XRay)Novoline.getInstance().getModuleManager().getModule(XRay.class)).distance.get()).intValue();
   }

   private Boolean lambda$new$0() {
      int var1 = HUD.h();
      return Boolean.valueOf(((Boolean)this.esp.get()).booleanValue() || ((Boolean)this.tracers.get()).booleanValue());
   }
}
