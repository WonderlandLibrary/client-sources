package vestige.module.impl.visual;

import java.awt.Color;
import vestige.Flap;
import vestige.event.Listener;
import vestige.event.impl.RenderEvent;
import vestige.module.Category;
import vestige.module.Module;
import vestige.setting.AbstractSetting;
import vestige.setting.impl.BooleanSetting;
import vestige.setting.impl.DoubleSetting;
import vestige.setting.impl.IntegerSetting;
import vestige.setting.impl.ModeSetting;
import vestige.ui.click.dropdown.DropdownClickGUI;
import vestige.util.render.ColorUtil;

public class ClickGuiModule extends Module {
   private DropdownClickGUI dropdownClickGUI;
   private final ModeSetting color = new ModeSetting("Color", "Client theme", new String[]{"Client theme", "Custom static", "Custom fade", "Custom 3 colors", "Rainbow"});
   public final ModeSetting style = new ModeSetting("Gui Style", "Drop Down", new String[]{"Drop Down"});
   private final IntegerSetting red = new IntegerSetting("Red", () -> {
      return this.color.getMode().startsWith("Custom");
   }, 0, 0, 255, 5);
   private final IntegerSetting green = new IntegerSetting("Green", () -> {
      return this.color.getMode().startsWith("Custom");
   }, 0, 0, 255, 5);
   private final IntegerSetting blue = new IntegerSetting("Blue", () -> {
      return this.color.getMode().startsWith("Custom");
   }, 255, 0, 255, 5);
   private final IntegerSetting red2 = new IntegerSetting("Red 2", () -> {
      return this.color.is("Custom fade") || this.color.is("Custom 3 colors");
   }, 0, 0, 255, 5);
   private final IntegerSetting green2 = new IntegerSetting("Green 2", () -> {
      return this.color.is("Custom fade") || this.color.is("Custom 3 colors");
   }, 255, 0, 255, 5);
   private final IntegerSetting blue2 = new IntegerSetting("Blue 2", () -> {
      return this.color.is("Custom fade") || this.color.is("Custom 3 colors");
   }, 255, 0, 255, 5);
   private final IntegerSetting red3 = new IntegerSetting("Red 3", () -> {
      return this.color.is("Custom 3 colors");
   }, 0, 0, 255, 5);
   private final IntegerSetting green3 = new IntegerSetting("Green 3", () -> {
      return this.color.is("Custom 3 colors");
   }, 255, 0, 255, 5);
   private final IntegerSetting blue3 = new IntegerSetting("Blue 3", () -> {
      return this.color.is("Custom 3 colors");
   }, 255, 0, 255, 5);
   private final DoubleSetting saturation = new DoubleSetting("Saturation", () -> {
      return this.color.is("Rainbow");
   }, 0.9D, 0.05D, 1.0D, 0.05D);
   private final DoubleSetting brightness = new DoubleSetting("Brightness", () -> {
      return this.color.is("Rainbow");
   }, 0.9D, 0.05D, 1.0D, 0.05D);
   public final BooleanSetting boxOnHover = new BooleanSetting("Box on hover", false);
   public final BooleanSetting boxOnSettings = new BooleanSetting("Box on settings", () -> {
      return this.boxOnHover.isEnabled();
   }, false);
   private Color color1;
   private Color color2;
   private Color color3;
   private ClientTheme theme;

   public ClickGuiModule() {
      super("ClickGUI", Category.VISUAL);
      this.setKey(54);
      this.addSettings(new AbstractSetting[]{this.style, this.color, this.red, this.green, this.blue, this.red2, this.green2, this.blue2, this.red3, this.green3, this.blue3, this.saturation, this.brightness, this.boxOnHover, this.boxOnSettings});
   }

   public void onEnable() {
      if (this.dropdownClickGUI == null) {
         this.dropdownClickGUI = new DropdownClickGUI(this);
      }

      mc.displayGuiScreen(this.dropdownClickGUI);
      this.setColors();
   }

   public void onClientStarted() {
      this.theme = (ClientTheme)Flap.instance.getModuleManager().getModule(ClientTheme.class);
   }

   @Listener
   public void onRender(RenderEvent event) {
      this.setColors();
   }

   public int getColor(int offset) {
      String var2 = this.color.getMode();
      byte var3 = -1;
      switch(var2.hashCode()) {
      case -1656737386:
         if (var2.equals("Rainbow")) {
            var3 = 4;
         }
         break;
      case -168271445:
         if (var2.equals("Custom fade")) {
            var3 = 2;
         }
         break;
      case 1566281044:
         if (var2.equals("Client theme")) {
            var3 = 0;
         }
         break;
      case 1889552861:
         if (var2.equals("Custom static")) {
            var3 = 1;
         }
         break;
      case 2030268844:
         if (var2.equals("Custom 3 colors")) {
            var3 = 3;
         }
      }

      switch(var3) {
      case 0:
         return this.theme.getColor(offset);
      case 1:
         return this.color1.getRGB();
      case 2:
         return ColorUtil.getColor(this.color1, this.color2, 2500L, offset);
      case 3:
         return ColorUtil.getColor(this.color1, this.color2, this.color3, 3000L, offset);
      case 4:
         return ColorUtil.getRainbow(4500L, (int)((double)offset * 0.85D), (float)this.saturation.getValue(), (float)this.brightness.getValue());
      default:
         return -1;
      }
   }

   private void setColors() {
      this.color1 = new Color(this.red.getValue(), this.green.getValue(), this.blue.getValue());
      this.color2 = new Color(this.red2.getValue(), this.green2.getValue(), this.blue2.getValue());
      this.color3 = new Color(this.red3.getValue(), this.green3.getValue(), this.blue3.getValue());
   }
}
