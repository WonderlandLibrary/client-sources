package cc.slack.features.modules.impl.utilties;

import cc.slack.Slack;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.Value;
import cc.slack.features.modules.api.settings.impl.BooleanValue;
import cc.slack.features.modules.impl.render.ChestESP;
import cc.slack.features.modules.impl.render.ESP;
import cc.slack.features.modules.impl.render.HUD;
import cc.slack.features.modules.impl.render.TargetHUD;
import java.io.File;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.Display;

@ModuleInfo(
   name = "LegitMode",
   category = Category.UTILITIES
)
public class LegitMode extends Module {
   private final BooleanValue selfDestruct = new BooleanValue("Self Destruct", false);
   private final File logsDirectory;

   public LegitMode() {
      this.logsDirectory = new File(Minecraft.getMinecraft().mcDataDir + File.separator + "logs" + File.separator);
      this.addSettings(new Value[]{this.selfDestruct});
   }

   public void onEnable() {
      Display.setTitle("Minecraft 1.8.8");
      this.toggleModule(HUD.class);
      this.toggleModule(TargetHUD.class);
      this.toggleModule(ESP.class);
      this.toggleModule(ChestESP.class);
      if ((Boolean)this.selfDestruct.getValue()) {
         this.deleteLogs();
      }

   }

   public void onDisable() {
      Display.setTitle(Slack.getInstance().getInfo().getName() + " " + Slack.getInstance().getInfo().getVersion() + " | " + Slack.getInstance().getInfo().getType() + " Build");
   }

   private void toggleModule(Class<? extends Module> moduleClass) {
      Module module = Slack.getInstance().getModuleManager().getInstance(moduleClass);
      if (module.isToggle()) {
         module.toggle();
      }

   }

   private void deleteLogs() {
      if (this.logsDirectory.exists()) {
         File[] files = this.logsDirectory.listFiles();
         if (files == null) {
            return;
         }

         File[] var2 = files;
         int var3 = files.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            File file = var2[var4];
            if (file.getName().endsWith("log.gz")) {
               file.delete();
            }
         }
      }

   }
}
