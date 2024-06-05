package net.minecraft.client.gui.stream;

import java.io.IOException;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.stream.GuiIngestServers$ServerList;
import net.minecraft.client.resources.I18n;

public class GuiIngestServers extends GuiScreen {
   private final GuiScreen field_152309_a;
   private String field_152310_f;
   private GuiIngestServers$ServerList field_152311_g;

   public GuiIngestServers(GuiScreen var1) {
      this.field_152309_a = var1;
   }

   public void initGui() {
      this.field_152310_f = I18n.format("options.stream.ingest.title", new Object[0]);
      this.field_152311_g = new GuiIngestServers$ServerList(this, this.mc);
      if(!this.mc.getTwitchStream().func_152908_z()) {
         this.mc.getTwitchStream().func_152909_x();
      }

      this.buttonList.add(new GuiButton(1, this.width / 2 - 155, this.height - 24 - 6, 150, 20, I18n.format("gui.done", new Object[0])));
      this.buttonList.add(new GuiButton(2, this.width / 2 + 5, this.height - 24 - 6, 150, 20, I18n.format("options.stream.ingest.reset", new Object[0])));
   }

   public void handleMouseInput() throws IOException {
      super.handleMouseInput();
      this.field_152311_g.handleMouseInput();
   }

   public void onGuiClosed() {
      if(this.mc.getTwitchStream().func_152908_z()) {
         this.mc.getTwitchStream().func_152932_y().func_153039_l();
      }

   }

   protected void actionPerformed(GuiButton var1) throws IOException {
      if(var1.enabled) {
         if(var1.id == 1) {
            this.mc.displayGuiScreen(this.field_152309_a);
         } else {
            this.mc.gameSettings.streamPreferredServer = "";
            this.mc.gameSettings.saveOptions();
         }
      }

   }

   public void drawScreen(int var1, int var2, float var3) {
      this.drawDefaultBackground();
      this.field_152311_g.drawScreen(var1, var2, var3);
      this.drawCenteredString(this.fontRendererObj, this.field_152310_f, this.width / 2, 20, 16777215);
      super.drawScreen(var1, var2, var3);
   }

   static FontRenderer access$000(GuiIngestServers var0) {
      return var0.fontRendererObj;
   }

   static FontRenderer access$100(GuiIngestServers var0) {
      return var0.fontRendererObj;
   }

   static FontRenderer access$200(GuiIngestServers var0) {
      return var0.fontRendererObj;
   }

   static FontRenderer access$300(GuiIngestServers var0) {
      return var0.fontRendererObj;
   }

   static FontRenderer access$400(GuiIngestServers var0) {
      return var0.fontRendererObj;
   }

   static FontRenderer access$500(GuiIngestServers var0) {
      return var0.fontRendererObj;
   }

   static FontRenderer access$600(GuiIngestServers var0) {
      return var0.fontRendererObj;
   }

   static FontRenderer access$700(GuiIngestServers var0) {
      return var0.fontRendererObj;
   }

   private static IOException a(IOException var0) {
      return var0;
   }
}
