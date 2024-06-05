package hawk.ui.clickgui;

import com.lukflug.panelstudio.ClickGUI;
import com.lukflug.panelstudio.CollapsibleContainer;
import com.lukflug.panelstudio.DraggableContainer;
import com.lukflug.panelstudio.SettingsAnimation;
import com.lukflug.panelstudio.mc8forge.MinecraftGUI;
import com.lukflug.panelstudio.settings.BooleanComponent;
import com.lukflug.panelstudio.settings.EnumComponent;
import com.lukflug.panelstudio.settings.KeybindComponent;
import com.lukflug.panelstudio.settings.NumberComponent;
import com.lukflug.panelstudio.settings.SimpleToggleable;
import com.lukflug.panelstudio.settings.Toggleable;
import com.lukflug.panelstudio.theme.DescriptionRenderer;
import com.lukflug.panelstudio.theme.SettingsColorScheme;
import com.lukflug.panelstudio.theme.Theme;
import hawk.Client;
import hawk.modules.Module;
import hawk.modules.render.ModToggleGUI;
import hawk.settings.BooleanSetting;
import hawk.settings.ColorSetting;
import hawk.settings.KeyBindSetting;
import hawk.settings.ModeSetting;
import hawk.settings.NumberSetting;
import hawk.settings.Setting;
import java.awt.Color;
import java.awt.Point;
import java.util.Iterator;
import net.minecraft.client.Minecraft;

public class HawkClickGUI extends MinecraftGUI {
   public static HawkClickGUI instance = new HawkClickGUI();
   private final Theme theme;
   public final int DISTANCE = 10;
   public final int HUD_BORDER = 2;
   public final int HEIGHT = 12;
   private final ClickGUI gui;
   public final int width = 100;
   private final MinecraftGUI.GUIInterface guiInterface = new MinecraftGUI.GUIInterface(this, this, true) {
      final HawkClickGUI this$0;

      {
         this.this$0 = var1;
      }

      protected String getResourcePrefix() {
         return "monsoon:gui/";
      }

      public int getFontWidth(String var1) {
         return Minecraft.getMinecraft().fontRendererObj.getStringWidth(var1);
      }

      public void drawString(Point var1, String var2, Color var3) {
         end();
         double var10002 = (double)var1.x;
         Minecraft.getMinecraft().fontRendererObj.drawString(var2, var10002, (double)var1.y, var3.getRGB());
         begin();
      }

      public int getFontHeight() {
         return Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT;
      }
   };
   private Toggleable colorToggle = new Toggleable(this) {
      final HawkClickGUI this$0;

      public void toggle() {
      }

      public boolean isOn() {
         return ModToggleGUI.INSTANCE.colorModel.is("RGB");
      }

      {
         this.this$0 = var1;
      }
   };

   protected int getScrollSpeed() {
      return 10;
   }

   protected MinecraftGUI.GUIInterface getInterface() {
      return this.guiInterface;
   }

   public HawkClickGUI() {
      this.theme = new MonsoonTheme(new SettingsColorScheme(ModToggleGUI.INSTANCE.activeColor, ModToggleGUI.INSTANCE.backgroundColor, ModToggleGUI.INSTANCE.settingBackgroundColor, ModToggleGUI.INSTANCE.outlineColor, ModToggleGUI.INSTANCE.fontColor, ModToggleGUI.INSTANCE.opacity), false, 12, 2);
      this.gui = new ClickGUI(this.guiInterface, (DescriptionRenderer)null);
      Module.Category[] var4;
      int var3 = (var4 = Module.Category.values()).length;

      for(int var2 = 0; var2 < var3; ++var2) {
         Module.Category var1 = var4[var2];
         if (!var1.name.equalsIgnoreCase("Hidden")) {
            DraggableContainer var5 = new DraggableContainer(String.valueOf((new StringBuilder(" ")).append(var1.name)), (String)null, this.theme.getPanelRenderer(), new SimpleToggleable(false), new SettingsAnimation(ModToggleGUI.INSTANCE.animationSpeed), (Toggleable)null, new Point(var1.pos, 10), 100);
            this.gui.addComponent(var5);
            Iterator var7 = Client.getModulesByCategory(var1).iterator();

            while(var7.hasNext()) {
               Module var6 = (Module)var7.next();
               CollapsibleContainer var8 = new CollapsibleContainer(String.valueOf((new StringBuilder(" ")).append(var6.name)), (String)null, this.theme.getContainerRenderer(), new SimpleToggleable(false), new SettingsAnimation(ModToggleGUI.INSTANCE.animationSpeed), var6);
               var5.addComponent(var8);
               Iterator var10 = var6.settings.iterator();

               while(var10.hasNext()) {
                  Setting var9 = (Setting)var10.next();
                  if (var9 instanceof BooleanSetting) {
                     var8.addComponent(new BooleanComponent(String.valueOf((new StringBuilder()).append(var9.name)), (String)null, this.theme.getComponentRenderer(), (BooleanSetting)var9));
                  } else if (var9 instanceof NumberSetting) {
                     var8.addComponent(new NumberComponent(String.valueOf((new StringBuilder()).append(var9.name)), (String)null, this.theme.getComponentRenderer(), (NumberSetting)var9, ((NumberSetting)var9).getMinimum(), ((NumberSetting)var9).getMaximum()));
                  } else if (var9 instanceof ModeSetting) {
                     var8.addComponent(new EnumComponent(String.valueOf((new StringBuilder()).append(var9.name)), (String)null, this.theme.getComponentRenderer(), (ModeSetting)var9));
                  } else if (var9 instanceof ColorSetting) {
                     var8.addComponent(new SyncableColorComponent(this.theme, (ColorSetting)var9, this.colorToggle, new SettingsAnimation(ModToggleGUI.INSTANCE.animationSpeed)));
                  } else if (var9 instanceof KeyBindSetting) {
                     var8.addComponent(new KeybindComponent(this.theme.getComponentRenderer(), (KeyBindSetting)var9));
                  }
               }
            }
         }
      }

   }

   protected ClickGUI getGUI() {
      return this.gui;
   }
}
