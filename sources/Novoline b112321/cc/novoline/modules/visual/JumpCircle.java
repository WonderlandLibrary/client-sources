package cc.novoline.modules.visual;

import cc.novoline.events.EventTarget;
import cc.novoline.events.events.PlayerUpdateEvent;
import cc.novoline.events.events.Render3DEvent;
import cc.novoline.gui.screen.setting.Manager;
import cc.novoline.gui.screen.setting.Setting;
import cc.novoline.gui.screen.setting.SettingType;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.EnumModuleType;
import cc.novoline.modules.ModuleManager;
import cc.novoline.modules.configurations.annotation.Property;
import cc.novoline.modules.configurations.property.object.ColorProperty;
import cc.novoline.modules.configurations.property.object.PropertyFactory;
import cc.novoline.modules.visual.HUD;
import java.awt.Color;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import net.vE;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.NotNull;

public class JumpCircle extends AbstractModule {
   private List y = new CopyOnWriteArrayList();
   private boolean z;
   @Property("color")
   private ColorProperty x = PropertyFactory.createColor(Integer.valueOf((new Color(255, 110, 110)).getRGB()));

   public JumpCircle(@NotNull ModuleManager var1) {
      super(var1, EnumModuleType.VISUALS, "JumpCircle", "Jump Circle");
      Manager.put(new Setting("JC_COLOR", "Color", SettingType.COLOR_PICKER, this, this.x, (EnumSet)null));
   }

   public void b() {
      this.y.add(new vE(this));
   }

   public void a(double var1, double var3, double var5) {
      this.y.add(new vE(this, var1, var3, var5));
   }

   @EventTarget
   public void a(Render3DEvent var1) {
      HUD.e();
      Iterator var3 = this.y.iterator();
      if(var3.hasNext()) {
         vE var4 = (vE)var3.next();
         vE.a(var4);
         if(vE.b(var4) <= 0.0F) {
            this.y.remove(var4);
         }
      }

   }

   @EventTarget
   public void a(PlayerUpdateEvent var1) {
      int var2 = HUD.e();
      if(!this.mc.player.onGround) {
         this.z = true;
      }

      if(this.z) {
         this.b();
         this.z = false;
      }

   }

   static Minecraft k(JumpCircle var0) {
      return var0.mc;
   }

   static Minecraft g(JumpCircle var0) {
      return var0.mc;
   }

   static Minecraft h(JumpCircle var0) {
      return var0.mc;
   }

   static ColorProperty c(JumpCircle var0) {
      return var0.x;
   }

   static Minecraft a(JumpCircle var0) {
      return var0.mc;
   }

   static Minecraft b(JumpCircle var0) {
      return var0.mc;
   }

   static Minecraft e(JumpCircle var0) {
      return var0.mc;
   }

   static Minecraft f(JumpCircle var0) {
      return var0.mc;
   }

   static Minecraft i(JumpCircle var0) {
      return var0.mc;
   }

   static Minecraft j(JumpCircle var0) {
      return var0.mc;
   }

   static Minecraft d(JumpCircle var0) {
      return var0.mc;
   }
}
