package net.minecraft.client.gui;

import java.io.IOException;
import net._7;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiCreateWorld;
import net.minecraft.client.gui.GuiFlatPresets;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.world.gen.FlatGeneratorInfo;

public class GuiCreateFlatWorld extends GuiScreen {
   private final GuiCreateWorld createWorldGui;
   private FlatGeneratorInfo theFlatGeneratorInfo = FlatGeneratorInfo.getDefaultFlatGenerator();
   private String flatWorldTitle;
   private String field_146394_i;
   private String field_146391_r;
   private _7 z;
   private GuiButton field_146389_t;
   private GuiButton field_146388_u;
   private GuiButton field_146386_v;

   public GuiCreateFlatWorld(GuiCreateWorld var1, String var2) {
      this.createWorldGui = var1;
      this.func_146383_a(var2);
   }

   public String func_146384_e() {
      return this.theFlatGeneratorInfo.toString();
   }

   public void func_146383_a(String var1) {
      this.theFlatGeneratorInfo = FlatGeneratorInfo.a(var1);
   }

   public void initGui() {
      this.buttonList.clear();
      this.flatWorldTitle = I18n.format("createWorld.customize.flat.title", new Object[0]);
      this.field_146394_i = I18n.format("createWorld.customize.flat.tile", new Object[0]);
      this.field_146391_r = I18n.format("createWorld.customize.flat.height", new Object[0]);
      this.z = new _7(this);
      this.buttonList.add(this.field_146389_t = new GuiButton(2, this.width / 2 - 154, this.height - 52, 100, 20, I18n.format("createWorld.customize.flat.addLayer", new Object[0]) + " (NYI)"));
      this.buttonList.add(this.field_146388_u = new GuiButton(3, this.width / 2 - 50, this.height - 52, 100, 20, I18n.format("createWorld.customize.flat.editLayer", new Object[0]) + " (NYI)"));
      this.buttonList.add(this.field_146386_v = new GuiButton(4, this.width / 2 - 155, this.height - 52, 150, 20, I18n.format("createWorld.customize.flat.removeLayer", new Object[0])));
      this.buttonList.add(new GuiButton(0, this.width / 2 - 155, this.height - 28, 150, 20, I18n.format("gui.done", new Object[0])));
      this.buttonList.add(new GuiButton(5, this.width / 2 + 5, this.height - 52, 150, 20, I18n.format("createWorld.customize.presets", new Object[0])));
      this.buttonList.add(new GuiButton(1, this.width / 2 + 5, this.height - 28, 150, 20, I18n.format("gui.cancel", new Object[0])));
      this.field_146389_t.visible = this.field_146388_u.visible = false;
      this.theFlatGeneratorInfo.func_82645_d();
      this.func_146375_g();
   }

   public void handleMouseInput() throws IOException {
      super.handleMouseInput();
      this.z.handleMouseInput();
   }

   protected void actionPerformed(GuiButton var1) throws IOException {
      int var2 = this.theFlatGeneratorInfo.getFlatLayers().size() - this.z.x - 1;
      if(var1.id == 1) {
         this.mc.displayGuiScreen(this.createWorldGui);
      } else if(var1.id == 0) {
         this.createWorldGui.chunkProviderSettingsJson = this.func_146384_e();
         this.mc.displayGuiScreen(this.createWorldGui);
      } else if(var1.id == 5) {
         this.mc.displayGuiScreen(new GuiFlatPresets(this));
      } else if(var1.id == 4 && this.func_146382_i()) {
         this.theFlatGeneratorInfo.getFlatLayers().remove(var2);
         this.z.x = Math.min(this.z.x, this.theFlatGeneratorInfo.getFlatLayers().size() - 1);
      }

      this.theFlatGeneratorInfo.func_82645_d();
      this.func_146375_g();
   }

   public void func_146375_g() {
      boolean var1 = this.func_146382_i();
      this.field_146386_v.enabled = var1;
      this.field_146388_u.enabled = var1;
      this.field_146388_u.enabled = false;
      this.field_146389_t.enabled = false;
   }

   private boolean func_146382_i() {
      return this.z.x > -1 && this.z.x < this.theFlatGeneratorInfo.getFlatLayers().size();
   }

   public void drawScreen(int var1, int var2, float var3) {
      this.drawDefaultBackground();
      this.z.drawScreen(var1, var2, var3);
      this.drawCenteredString(this.fontRendererObj, this.flatWorldTitle, this.width / 2, 8, 16777215);
      int var4 = this.width / 2 - 92 - 16;
      this.drawString(this.fontRendererObj, this.field_146394_i, var4, 32, 16777215);
      this.drawString(this.fontRendererObj, this.field_146391_r, var4 + 2 + 213 - this.fontRendererObj.d(this.field_146391_r), 32, 16777215);
      super.drawScreen(var1, var2, var3);
   }

   static FlatGeneratorInfo access$000(GuiCreateFlatWorld var0) {
      return var0.theFlatGeneratorInfo;
   }

   private static IOException a(IOException var0) {
      return var0;
   }
}
