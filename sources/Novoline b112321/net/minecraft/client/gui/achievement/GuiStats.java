package net.minecraft.client.gui.achievement;

import java.io.IOException;
import net._3;
import net._s;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.gui.IProgressMeter;
import net.minecraft.client.gui.achievement.GuiStats$StatsGeneral;
import net.minecraft.client.gui.achievement.GuiStats$StatsMobsList;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.network.play.client.C16PacketClientStatus$EnumState;
import net.minecraft.stats.StatFileWriter;

public class GuiStats extends GuiScreen implements IProgressMeter {
   protected GuiScreen parentScreen;
   protected String screenTitle = "Select world";
   private GuiStats$StatsGeneral generalStats;
   private _s z;
   private _3 A;
   private GuiStats$StatsMobsList mobStats;
   private StatFileWriter field_146546_t;
   private GuiSlot displaySlot;
   private boolean doesGuiPauseGame = true;

   public GuiStats(GuiScreen var1, StatFileWriter var2) {
      this.parentScreen = var1;
      this.field_146546_t = var2;
   }

   public void initGui() {
      this.screenTitle = I18n.format("gui.stats", new Object[0]);
      this.doesGuiPauseGame = true;
      this.mc.getNetHandler().b(new C16PacketClientStatus(C16PacketClientStatus$EnumState.REQUEST_STATS));
   }

   public void handleMouseInput() throws IOException {
      super.handleMouseInput();
      if(this.displaySlot != null) {
         this.displaySlot.handleMouseInput();
      }

   }

   public void func_175366_f() {
      this.generalStats = new GuiStats$StatsGeneral(this, this.mc);
      this.generalStats.registerScrollButtons(1, 1);
      this.z = new _s(this, this.mc);
      this.z.registerScrollButtons(1, 1);
      this.A = new _3(this, this.mc);
      this.A.registerScrollButtons(1, 1);
      this.mobStats = new GuiStats$StatsMobsList(this, this.mc);
      this.mobStats.registerScrollButtons(1, 1);
   }

   public void createButtons() {
      this.buttonList.add(new GuiButton(0, this.width / 2 + 4, this.height - 28, 150, 20, I18n.format("gui.done", new Object[0])));
      this.buttonList.add(new GuiButton(1, this.width / 2 - 160, this.height - 52, 80, 20, I18n.format("stat.generalButton", new Object[0])));
      GuiButton var1;
      this.buttonList.add(var1 = new GuiButton(2, this.width / 2 - 80, this.height - 52, 80, 20, I18n.format("stat.blocksButton", new Object[0])));
      GuiButton var2;
      this.buttonList.add(var2 = new GuiButton(3, this.width / 2, this.height - 52, 80, 20, I18n.format("stat.itemsButton", new Object[0])));
      GuiButton var3;
      this.buttonList.add(var3 = new GuiButton(4, this.width / 2 + 80, this.height - 52, 80, 20, I18n.format("stat.mobsButton", new Object[0])));
      if(this.A.getSize() == 0) {
         var1.enabled = false;
      }

      if(this.z.getSize() == 0) {
         var2.enabled = false;
      }

      if(this.mobStats.getSize() == 0) {
         var3.enabled = false;
      }

   }

   protected void actionPerformed(GuiButton var1) throws IOException {
      if(var1.enabled) {
         if(var1.id == 0) {
            this.mc.displayGuiScreen(this.parentScreen);
         } else if(var1.id == 1) {
            this.displaySlot = this.generalStats;
         } else if(var1.id == 3) {
            this.displaySlot = this.z;
         } else if(var1.id == 2) {
            this.displaySlot = this.A;
         } else if(var1.id == 4) {
            this.displaySlot = this.mobStats;
         } else {
            this.displaySlot.actionPerformed(var1);
         }
      }

   }

   public void drawScreen(int var1, int var2, float var3) {
      if(this.doesGuiPauseGame) {
         this.drawDefaultBackground();
         this.drawCenteredString(this.fontRendererObj, I18n.format("multiplayer.downloadingStats", new Object[0]), this.width / 2, this.height / 2, 16777215);
         this.drawCenteredString(this.fontRendererObj, lanSearchStates[(int)(Minecraft.getSystemTime() / 150L % (long)lanSearchStates.length)], this.width / 2, this.height / 2 + this.fontRendererObj.getHeight() * 2, 16777215);
      } else {
         this.displaySlot.drawScreen(var1, var2, var3);
         this.drawCenteredString(this.fontRendererObj, this.screenTitle, this.width / 2, 20, 16777215);
         super.drawScreen(var1, var2, var3);
      }

   }

   public void doneLoading() {
      if(this.doesGuiPauseGame) {
         this.func_175366_f();
         this.createButtons();
         this.displaySlot = this.generalStats;
         this.doesGuiPauseGame = false;
      }

   }

   public boolean doesGuiPauseGame() {
      return !this.doesGuiPauseGame;
   }

   private void drawStatsScreen(int var1, int var2, Item var3) {
      this.drawButtonBackground(var1 + 1, var2 + 1);
      GlStateManager.enableRescaleNormal();
      RenderHelper.enableGUIStandardItemLighting();
      this.itemRender.renderItemIntoGUI(new ItemStack(var3, 1, 0), (float)(var1 + 2), (float)(var2 + 2));
      RenderHelper.disableStandardItemLighting();
      GlStateManager.disableRescaleNormal();
   }

   private void drawButtonBackground(int var1, int var2) {
      this.drawSprite(var1, var2, 0, 0);
   }

   private void drawSprite(int var1, int var2, int var3, int var4) {
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      this.mc.getTextureManager().bindTexture(statIcons);
      float var5 = 0.0078125F;
      float var6 = 0.0078125F;
      boolean var7 = true;
      boolean var8 = true;
      Tessellator var9 = Tessellator.getInstance();
      WorldRenderer var10 = var9.getWorldRenderer();
      var10.begin(7, DefaultVertexFormats.POSITION_TEX);
      var10.pos((double)(var1 + 0), (double)(var2 + 18), (double)this.zLevel).tex((double)((float)(var3 + 0) * 0.0078125F), (double)((float)(var4 + 18) * 0.0078125F)).endVertex();
      var10.pos((double)(var1 + 18), (double)(var2 + 18), (double)this.zLevel).tex((double)((float)(var3 + 18) * 0.0078125F), (double)((float)(var4 + 18) * 0.0078125F)).endVertex();
      var10.pos((double)(var1 + 18), (double)(var2 + 0), (double)this.zLevel).tex((double)((float)(var3 + 18) * 0.0078125F), (double)((float)(var4 + 0) * 0.0078125F)).endVertex();
      var10.pos((double)(var1 + 0), (double)(var2 + 0), (double)this.zLevel).tex((double)((float)(var3 + 0) * 0.0078125F), (double)((float)(var4 + 0) * 0.0078125F)).endVertex();
      var9.draw();
   }

   static void access$000(GuiStats var0, int var1, int var2, int var3, int var4) {
      var0.drawSprite(var1, var2, var3, var4);
   }

   static StatFileWriter access$100(GuiStats var0) {
      return var0.field_146546_t;
   }

   static FontRenderer p(GuiStats var0) {
      return var0.fontRendererObj;
   }

   static FontRenderer g(GuiStats var0) {
      return var0.fontRendererObj;
   }

   static FontRenderer b(GuiStats var0) {
      return var0.fontRendererObj;
   }

   static FontRenderer o(GuiStats var0) {
      return var0.fontRendererObj;
   }

   static FontRenderer f(GuiStats var0) {
      return var0.fontRendererObj;
   }

   static void access$700(GuiStats var0, float var1, float var2, float var3, float var4, int var5, int var6) {
      var0.drawGradientRect(var1, var2, var3, var4, var5, var6);
   }

   static FontRenderer a(GuiStats var0) {
      return var0.fontRendererObj;
   }

   static FontRenderer q(GuiStats var0) {
      return var0.fontRendererObj;
   }

   static void access$1000(GuiStats var0, float var1, float var2, float var3, float var4, int var5, int var6) {
      var0.drawGradientRect(var1, var2, var3, var4, var5, var6);
   }

   static FontRenderer r(GuiStats var0) {
      return var0.fontRendererObj;
   }

   static void access$1200(GuiStats var0, int var1, int var2, Item var3) {
      var0.drawStatsScreen(var1, var2, var3);
   }

   static FontRenderer access$1300(GuiStats var0) {
      return var0.fontRendererObj;
   }

   static FontRenderer access$1400(GuiStats var0) {
      return var0.fontRendererObj;
   }

   static FontRenderer access$1500(GuiStats var0) {
      return var0.fontRendererObj;
   }

   static FontRenderer access$1600(GuiStats var0) {
      return var0.fontRendererObj;
   }

   static FontRenderer access$1700(GuiStats var0) {
      return var0.fontRendererObj;
   }

   static FontRenderer access$1800(GuiStats var0) {
      return var0.fontRendererObj;
   }

   static FontRenderer access$1900(GuiStats var0) {
      return var0.fontRendererObj;
   }

   static FontRenderer access$2000(GuiStats var0) {
      return var0.fontRendererObj;
   }

   static FontRenderer access$2100(GuiStats var0) {
      return var0.fontRendererObj;
   }

   static FontRenderer access$2200(GuiStats var0) {
      return var0.fontRendererObj;
   }

   private static IOException a(IOException var0) {
      return var0;
   }
}
