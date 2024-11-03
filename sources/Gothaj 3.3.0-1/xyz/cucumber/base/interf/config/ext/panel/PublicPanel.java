package xyz.cucumber.base.interf.config.ext.panel;

import xyz.cucumber.base.utils.cfgs.ConfigFileUtils;

public class PublicPanel extends Panel {
   private String json;

   public PublicPanel(String name, String json) {
      this.name = name;
      this.creator = "Gothaj Team";
      this.time = ConfigFileUtils.getConfigDate(json);
      this.json = json;
   }

   @Override
   public void load() {
      ConfigFileUtils.load(this.name, this.json, true);
   }

   @Override
   public void save() {
   }
}
