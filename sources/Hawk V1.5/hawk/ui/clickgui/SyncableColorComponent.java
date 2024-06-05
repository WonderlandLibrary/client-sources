package hawk.ui.clickgui;

import com.lukflug.panelstudio.Animation;
import com.lukflug.panelstudio.Context;
import com.lukflug.panelstudio.FocusableComponent;
import com.lukflug.panelstudio.settings.ColorComponent;
import com.lukflug.panelstudio.settings.Toggleable;
import com.lukflug.panelstudio.theme.ColorScheme;
import com.lukflug.panelstudio.theme.Renderer;
import com.lukflug.panelstudio.theme.Theme;
import hawk.modules.render.ModToggleGUI;
import hawk.settings.ColorSetting;
import net.minecraft.util.EnumChatFormatting;

public class SyncableColorComponent extends ColorComponent {
   public SyncableColorComponent(Theme var1, ColorSetting var2, Toggleable var3, Animation var4) {
      super(String.valueOf((new StringBuilder()).append(EnumChatFormatting.BOLD).append(var2.name)), (String)null, var1.getContainerRenderer(), var4, var1.getComponentRenderer(), var2, true, true, var3);
      if (var2 != ModToggleGUI.INSTANCE.activeColor) {
         this.addComponent(new SyncableColorComponent.SyncButton(this, var1.getComponentRenderer()));
      }

   }

   static com.lukflug.panelstudio.settings.ColorSetting access$1(SyncableColorComponent var0) {
      return var0.setting;
   }

   static ColorScheme access$0(SyncableColorComponent var0) {
      return var0.overrideScheme;
   }

   private class SyncButton extends FocusableComponent {
      final SyncableColorComponent this$0;

      public SyncButton(SyncableColorComponent var1, Renderer var2) {
         super("Sync Color", (String)null, var2);
         this.this$0 = var1;
      }

      public void handleButton(Context var1, int var2) {
         super.handleButton(var1, var2);
         if (var2 == 0 && var1.isClicked()) {
            SyncableColorComponent.access$1(this.this$0).setValue(ModToggleGUI.INSTANCE.activeColor.getColor());
            SyncableColorComponent.access$1(this.this$0).setRainbow(ModToggleGUI.INSTANCE.activeColor.getRainbow());
         }

      }

      public void render(Context var1) {
         super.render(var1);
         this.renderer.overrideColorScheme(SyncableColorComponent.access$0(this.this$0));
         this.renderer.renderTitle(var1, this.title, this.hasFocus(var1), false);
         this.renderer.restoreColorScheme();
      }
   }
}
