package xyz.cucumber.base.interf.clientsettings.ext.adds;

import xyz.cucumber.base.interf.clientsettings.ext.Setting;
import xyz.cucumber.base.interf.clientsettings.ext.impl.ClientSetting;
import xyz.cucumber.base.interf.clientsettings.ext.impl.NumberClientSetting;
import xyz.cucumber.base.module.settings.NumberSettings;

public class BloomSetting extends Setting {
   public NumberSettings radius = new NumberSettings("Radius", 7.0, 1.0, 15.0, 0.5);
   public NumberSettings compression = new NumberSettings("Compression", 1.0, 1.0, 10.0, 0.5);
   public NumberSettings saturation = new NumberSettings("Saturation", 2.0, 1.0, 15.0, 0.5);

   public BloomSetting() {
      super("Bloom");
      this.addSettings(
         new ClientSetting[]{new NumberClientSetting(this.radius), new NumberClientSetting(this.saturation), new NumberClientSetting(this.compression)}
      );
   }
}
