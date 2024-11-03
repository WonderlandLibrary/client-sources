package xyz.cucumber.base.interf.config.ext.panel;

import i.dupx.launcher.CLAPI;
import java.io.File;
import xyz.cucumber.base.utils.cfgs.ConfigFileUtils;

public class LocalPanel extends Panel {
   private File file;

   public LocalPanel(File file) {
      this.file = file;
      this.name = file.getName().substring(0, file.getName().lastIndexOf(".json"));
      this.creator = CLAPI.getCLUsername();
      this.time = ConfigFileUtils.getConfigDate(file);
   }

   @Override
   public void load() {
      ConfigFileUtils.load(this.file, true, false);
   }

   @Override
   public void save() {
      ConfigFileUtils.save(this.file, true);
   }
}
