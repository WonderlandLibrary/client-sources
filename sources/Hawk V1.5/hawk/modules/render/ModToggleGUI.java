package hawk.modules.render;

import hawk.modules.Module;
import hawk.settings.ColorSetting;
import hawk.settings.ModeSetting;
import hawk.settings.NumberSetting;
import hawk.settings.Setting;
import hawk.ui.clickgui.HawkClickGUI;
import hawk.util.JColor;

public class ModToggleGUI extends Module {
   public ColorSetting inactiveColor = new ColorSetting("Disabled Color", new JColor(255, 255, 255, 255));
   public ColorSetting settingBackgroundColor = new ColorSetting("settinBgColor", new JColor(54, 57, 63, 255));
   public ColorSetting fontColor = new ColorSetting("fontColor", new JColor(214, 215, 220, 255));
   public ModeSetting colorModel = new ModeSetting("Color Model", this, "RGB", new String[]{"RGB", "HSB"});
   public NumberSetting animationSpeed = new NumberSetting("Animation Speed", 200.0D, 10.0D, 500.0D, 10.0D, this);
   public ColorSetting activeColor = new ColorSetting("enabledColor", new JColor(114, 137, 218, 255));
   public static ModToggleGUI INSTANCE = new ModToggleGUI();
   public ColorSetting outlineColor = new ColorSetting("Outline Color", new JColor(28, 28, 28, 255));
   public NumberSetting opacity = new NumberSetting("Opacity", 255.0D, 0.0D, 255.0D, 10.0D, this);
   public ColorSetting backgroundColor = new ColorSetting("Background", new JColor(35, 39, 42, 255));

   public ModToggleGUI() {
      super("ClickGUI", 54, Module.Category.RENDER);
      this.addSettings(new Setting[]{this.animationSpeed, this.fontColor, this.outlineColor, this.opacity, this.backgroundColor, this.activeColor, this.inactiveColor, this.settingBackgroundColor});
   }

   public void onEnable() {
      HawkClickGUI.instance.enterGUI();
      this.toggle();
   }
}
