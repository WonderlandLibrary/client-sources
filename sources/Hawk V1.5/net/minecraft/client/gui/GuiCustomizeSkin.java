package net.minecraft.client.gui;

import java.io.IOException;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EnumPlayerModelParts;

public class GuiCustomizeSkin extends GuiScreen {
   private final GuiScreen field_175361_a;
   private static final String __OBFID = "CL_00001932";
   private String field_175360_f;

   public GuiCustomizeSkin(GuiScreen var1) {
      this.field_175361_a = var1;
   }

   public void initGui() {
      int var1 = 0;
      this.field_175360_f = I18n.format("options.skinCustomisation.title");
      EnumPlayerModelParts[] var2 = EnumPlayerModelParts.values();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         EnumPlayerModelParts var5 = var2[var4];
         this.buttonList.add(new GuiCustomizeSkin.ButtonPart(this, var5.func_179328_b(), this.width / 2 - 155 + var1 % 2 * 160, this.height / 6 + 24 * (var1 >> 1), 150, 20, var5, (Object)null));
         ++var1;
      }

      if (var1 % 2 == 1) {
         ++var1;
      }

      this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 24 * (var1 >> 1), I18n.format("gui.done")));
   }

   static String access$0(GuiCustomizeSkin var0, EnumPlayerModelParts var1) {
      return var0.func_175358_a(var1);
   }

   public void drawScreen(int var1, int var2, float var3) {
      this.drawDefaultBackground();
      this.drawCenteredString(this.fontRendererObj, this.field_175360_f, this.width / 2, 20, 16777215);
      super.drawScreen(var1, var2, var3);
   }

   private String func_175358_a(EnumPlayerModelParts var1) {
      String var2;
      if (this.mc.gameSettings.func_178876_d().contains(var1)) {
         var2 = I18n.format("options.on");
      } else {
         var2 = I18n.format("options.off");
      }

      return String.valueOf((new StringBuilder(String.valueOf(var1.func_179326_d().getFormattedText()))).append(": ").append(var2));
   }

   protected void actionPerformed(GuiButton var1) throws IOException {
      if (var1.enabled) {
         if (var1.id == 200) {
            this.mc.gameSettings.saveOptions();
            this.mc.displayGuiScreen(this.field_175361_a);
         } else if (var1 instanceof GuiCustomizeSkin.ButtonPart) {
            EnumPlayerModelParts var2 = GuiCustomizeSkin.ButtonPart.access$0((GuiCustomizeSkin.ButtonPart)var1);
            this.mc.gameSettings.func_178877_a(var2);
            var1.displayString = this.func_175358_a(var2);
         }
      }

   }

   class ButtonPart extends GuiButton {
      final GuiCustomizeSkin this$0;
      private static final String __OBFID = "CL_00001930";
      private final EnumPlayerModelParts field_175234_p;

      static EnumPlayerModelParts access$0(GuiCustomizeSkin.ButtonPart var0) {
         return var0.field_175234_p;
      }

      ButtonPart(GuiCustomizeSkin var1, int var2, int var3, int var4, int var5, int var6, EnumPlayerModelParts var7, Object var8) {
         this(var1, var2, var3, var4, var5, var6, var7);
      }

      private ButtonPart(GuiCustomizeSkin var1, int var2, int var3, int var4, int var5, int var6, EnumPlayerModelParts var7) {
         super(var2, var3, var4, var5, var6, GuiCustomizeSkin.access$0(var1, var7));
         this.this$0 = var1;
         this.field_175234_p = var7;
      }
   }
}
