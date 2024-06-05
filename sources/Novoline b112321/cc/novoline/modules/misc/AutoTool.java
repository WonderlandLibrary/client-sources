package cc.novoline.modules.misc;

import cc.novoline.events.EventTarget;
import cc.novoline.events.events.EventState;
import cc.novoline.events.events.MotionUpdateEvent;
import cc.novoline.gui.screen.setting.Manager;
import cc.novoline.gui.screen.setting.Setting;
import cc.novoline.gui.screen.setting.SettingType;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.EnumModuleType;
import cc.novoline.modules.ModuleManager;
import cc.novoline.modules.configurations.annotation.Property;
import cc.novoline.modules.configurations.property.object.BooleanProperty;
import cc.novoline.modules.configurations.property.object.PropertyFactory;
import cc.novoline.modules.misc.WindowedFullscreen;
import net.CD;
import net.minecraft.client.Minecraft;

public final class AutoTool extends AbstractModule {
   @Property("switch-back")
   private BooleanProperty switch_back = PropertyFactory.booleanTrue();
   private int z;
   private int tick;

   public AutoTool(ModuleManager var1) {
      super(var1, "AutoTool", "Auto Tool", 0, EnumModuleType.MISC, "Switches to the best tool");
      Manager.put(new Setting("AT_SWITCH_BACK", "Switch Back", SettingType.CHECKBOX, this, this.switch_back));
   }

   @EventTarget
   public void onPre(MotionUpdateEvent var1) {
      String[] var2 = WindowedFullscreen.a();
      if(var1.getState().equals(EventState.PRE)) {
         if(this.mc.at.f()) {
            ++this.tick;
            if(this.tick == 1) {
               this.z = this.mc.player.inventory.currentItem;
            }

            this.mc.player.updateTool(this.mc.objectMouseOver.getBlockPos());
         }

         if(this.tick > 0) {
            if(((Boolean)this.switch_back.get()).booleanValue()) {
               this.novoline.getTaskManager().queue(new CD(this, 100));
            }

            this.tick = 0;
         }
      }

   }

   static Minecraft b(AutoTool var0) {
      return var0.mc;
   }

   static Minecraft a(AutoTool var0) {
      return var0.mc;
   }

   static int c(AutoTool var0) {
      return var0.z;
   }
}
