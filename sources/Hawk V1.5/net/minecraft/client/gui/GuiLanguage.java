package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.Language;
import net.minecraft.client.resources.LanguageManager;
import net.minecraft.client.settings.GameSettings;

public class GuiLanguage extends GuiScreen {
   private GuiOptionButton field_146455_i;
   private final LanguageManager field_146454_h;
   private static final String __OBFID = "CL_00000698";
   private GuiOptionButton field_146452_r;
   private GuiLanguage.List field_146450_f;
   private final GameSettings game_settings_3;
   protected GuiScreen field_146453_a;

   public void handleMouseInput() throws IOException {
      super.handleMouseInput();
      this.field_146450_f.func_178039_p();
   }

   static GuiOptionButton access$2(GuiLanguage var0) {
      return var0.field_146452_r;
   }

   public GuiLanguage(GuiScreen var1, GameSettings var2, LanguageManager var3) {
      this.field_146453_a = var1;
      this.game_settings_3 = var2;
      this.field_146454_h = var3;
   }

   static LanguageManager access$0(GuiLanguage var0) {
      return var0.field_146454_h;
   }

   static GuiOptionButton access$3(GuiLanguage var0) {
      return var0.field_146455_i;
   }

   protected void actionPerformed(GuiButton var1) throws IOException {
      if (var1.enabled) {
         switch(var1.id) {
         case 5:
            break;
         case 6:
            this.mc.displayGuiScreen(this.field_146453_a);
            break;
         case 100:
            if (var1 instanceof GuiOptionButton) {
               this.game_settings_3.setOptionValue(((GuiOptionButton)var1).returnEnumOptions(), 1);
               var1.displayString = this.game_settings_3.getKeyBinding(GameSettings.Options.FORCE_UNICODE_FONT);
               ScaledResolution var2 = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
               int var3 = var2.getScaledWidth();
               int var4 = var2.getScaledHeight();
               this.setWorldAndResolution(this.mc, var3, var4);
            }
            break;
         default:
            this.field_146450_f.actionPerformed(var1);
         }
      }

   }

   public void initGui() {
      this.buttonList.add(this.field_146455_i = new GuiOptionButton(100, this.width / 2 - 155, this.height - 38, GameSettings.Options.FORCE_UNICODE_FONT, this.game_settings_3.getKeyBinding(GameSettings.Options.FORCE_UNICODE_FONT)));
      this.buttonList.add(this.field_146452_r = new GuiOptionButton(6, this.width / 2 - 155 + 160, this.height - 38, I18n.format("gui.done")));
      this.field_146450_f = new GuiLanguage.List(this, this.mc);
      this.field_146450_f.registerScrollButtons(7, 8);
   }

   public void drawScreen(int var1, int var2, float var3) {
      this.field_146450_f.drawScreen(var1, var2, var3);
      this.drawCenteredString(this.fontRendererObj, I18n.format("options.language"), this.width / 2, 16, 16777215);
      this.drawCenteredString(this.fontRendererObj, String.valueOf((new StringBuilder("(")).append(I18n.format("options.languageWarning")).append(")")), this.width / 2, this.height - 56, 8421504);
      super.drawScreen(var1, var2, var3);
   }

   static GameSettings access$1(GuiLanguage var0) {
      return var0.game_settings_3;
   }

   class List extends GuiSlot {
      private static final String __OBFID = "CL_00000699";
      private final Map field_148177_m;
      private final java.util.List field_148176_l;
      final GuiLanguage this$0;

      protected void elementClicked(int var1, boolean var2, int var3, int var4) {
         Language var5 = (Language)this.field_148177_m.get(this.field_148176_l.get(var1));
         GuiLanguage.access$0(this.this$0).setCurrentLanguage(var5);
         GuiLanguage.access$1(this.this$0).language = var5.getLanguageCode();
         this.mc.refreshResources();
         this.this$0.fontRendererObj.setUnicodeFlag(GuiLanguage.access$0(this.this$0).isCurrentLocaleUnicode() || GuiLanguage.access$1(this.this$0).forceUnicodeFont);
         this.this$0.fontRendererObj.setBidiFlag(GuiLanguage.access$0(this.this$0).isCurrentLanguageBidirectional());
         GuiLanguage.access$2(this.this$0).displayString = I18n.format("gui.done");
         GuiLanguage.access$3(this.this$0).displayString = GuiLanguage.access$1(this.this$0).getKeyBinding(GameSettings.Options.FORCE_UNICODE_FONT);
         GuiLanguage.access$1(this.this$0).saveOptions();
      }

      public List(GuiLanguage var1, Minecraft var2) {
         super(var2, var1.width, var1.height, 32, var1.height - 65 + 4, 18);
         this.this$0 = var1;
         this.field_148176_l = Lists.newArrayList();
         this.field_148177_m = Maps.newHashMap();
         Iterator var3 = GuiLanguage.access$0(var1).getLanguages().iterator();

         while(var3.hasNext()) {
            Language var4 = (Language)var3.next();
            this.field_148177_m.put(var4.getLanguageCode(), var4);
            this.field_148176_l.add(var4.getLanguageCode());
         }

      }

      protected int getSize() {
         return this.field_148176_l.size();
      }

      protected void drawSlot(int var1, int var2, int var3, int var4, int var5, int var6) {
         this.this$0.fontRendererObj.setBidiFlag(true);
         this.this$0.drawCenteredString(this.this$0.fontRendererObj, ((Language)this.field_148177_m.get(this.field_148176_l.get(var1))).toString(), this.width / 2, var3 + 1, 16777215);
         this.this$0.fontRendererObj.setBidiFlag(GuiLanguage.access$0(this.this$0).getCurrentLanguage().isBidirectional());
      }

      protected void drawBackground() {
         this.this$0.drawDefaultBackground();
      }

      protected int getContentHeight() {
         return this.getSize() * 18;
      }

      protected boolean isSelected(int var1) {
         return ((String)this.field_148176_l.get(var1)).equals(GuiLanguage.access$0(this.this$0).getCurrentLanguage().getLanguageCode());
      }
   }
}
