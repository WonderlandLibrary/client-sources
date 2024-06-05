package net.minecraft.client.gui.inventory;

import io.netty.buffer.Unpooled;
import java.io.IOException;
import net.aHz;
import net.aZO;
import net.aZt;
import net.aZx;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ContainerBeacon;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.util.ResourceLocation;

public class GuiBeacon extends aHz {
   private static final ResourceLocation beaconGuiTextures = new ResourceLocation("textures/gui/container/beacon.png");
   private final IInventory tileBeacon;
   private aZt ai;
   private boolean buttonsNotDrawn;

   public GuiBeacon(InventoryPlayer var1, IInventory var2) {
      super(new ContainerBeacon(var1, var2));
      this.tileBeacon = var2;
      this.y = 230;
      this.ab = 219;
   }

   public void initGui() {
      super.initGui();
      this.buttonList.add(this.ai = new aZt(this, -1, this.R + 164, this.W + 107));
      this.buttonList.add(new aZO(this, -2, this.R + 190, this.W + 107));
      this.buttonsNotDrawn = true;
      this.ai.enabled = false;
   }

   public void updateScreen() {
      super.updateScreen();
      int var1 = this.tileBeacon.getField(0);
      int var2 = this.tileBeacon.getField(1);
      int var3 = this.tileBeacon.getField(2);
      if(this.buttonsNotDrawn) {
         this.buttonsNotDrawn = false;

         for(int var4 = 0; var4 <= 2; ++var4) {
            int var5 = TileEntityBeacon.effectsList[var4].length;
            int var6 = var5 * 22 + (var5 - 1) * 2;

            for(int var7 = 0; var7 < var5; ++var7) {
               int var8 = TileEntityBeacon.effectsList[var4][var7].getId();
               aZx var9 = new aZx(this, var4 << 8 | var8, this.R + 76 + var7 * 24 - var6 / 2, this.W + 22 + var4 * 25, var8, var4);
               this.buttonList.add(var9);
               if(var4 >= var1) {
                  var9.enabled = false;
               } else if(var8 == var2) {
                  var9.func_146140_b(true);
               }
            }
         }

         boolean var10 = true;
         int var11 = TileEntityBeacon.effectsList[3].length + 1;
         int var12 = var11 * 22 + (var11 - 1) * 2;

         for(int var13 = 0; var13 < var11 - 1; ++var13) {
            int var15 = TileEntityBeacon.effectsList[3][var13].getId();
            aZx var16 = new aZx(this, 768 | var15, this.R + 167 + var13 * 24 - var12 / 2, this.W + 47, var15, 3);
            this.buttonList.add(var16);
            if(3 >= var1) {
               var16.enabled = false;
            } else if(var15 == var3) {
               var16.func_146140_b(true);
            }
         }

         aZx var14 = new aZx(this, 768 | var2, this.R + 167 + (var11 - 1) * 24 - var12 / 2, this.W + 47, var2, 3);
         this.buttonList.add(var14);
         if(3 >= var1) {
            var14.enabled = false;
         } else if(var2 == var3) {
            var14.func_146140_b(true);
         }
      }

      this.ai.enabled = this.tileBeacon.getStackInSlot(0) != null;
   }

   protected void actionPerformed(GuiButton var1) throws IOException {
      if(var1.id == -2) {
         this.mc.displayGuiScreen((GuiScreen)null);
      } else if(var1.id == -1) {
         String var2 = "MC|Beacon";
         PacketBuffer var3 = new PacketBuffer(Unpooled.buffer());
         var3.writeInt(this.tileBeacon.getField(1));
         var3.writeInt(this.tileBeacon.getField(2));
         this.mc.getNetHandler().b(new C17PacketCustomPayload("MC|Beacon", var3));
         this.mc.displayGuiScreen((GuiScreen)null);
      } else if(var1 instanceof aZx) {
         if(((aZx)var1).func_146141_c()) {
            return;
         }

         int var5 = var1.id;
         int var6 = var5 & 255;
         int var4 = var5 >> 8;
         if(var4 < 3) {
            this.tileBeacon.setField(1, var6);
         } else {
            this.tileBeacon.setField(2, var6);
         }

         this.buttonList.clear();
         this.initGui();
         this.updateScreen();
      }

   }

   protected void drawGuiContainerForegroundLayer(int var1, int var2) {
      RenderHelper.disableStandardItemLighting();
      this.drawCenteredString(this.fontRendererObj, I18n.format("tile.beacon.primary", new Object[0]), 62, 10, 14737632);
      this.drawCenteredString(this.fontRendererObj, I18n.format("tile.beacon.secondary", new Object[0]), 169, 10, 14737632);

      for(GuiButton var4 : this.buttonList) {
         if(var4.isMouseOver()) {
            var4.drawButtonForegroundLayer(var1 - this.R, var2 - this.W);
            break;
         }
      }

      RenderHelper.enableGUIStandardItemLighting();
   }

   protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      this.mc.getTextureManager().bindTexture(beaconGuiTextures);
      int var4 = (this.width - this.y) / 2;
      int var5 = (this.height - this.ab) / 2;
      this.drawTexturedModalRect(var4, var5, 0, 0, this.y, this.ab);
      this.itemRender.zLevel = 100.0F;
      this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Items.emerald), (float)(var4 + 42), (float)(var5 + 109));
      this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Items.diamond), (float)(var4 + 42 + 22), (float)(var5 + 109));
      this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Items.gold_ingot), (float)(var4 + 42 + 44), (float)(var5 + 109));
      this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Items.iron_ingot), (float)(var4 + 42 + 66), (float)(var5 + 109));
      this.itemRender.zLevel = 0.0F;
   }

   static ResourceLocation access$000() {
      return beaconGuiTextures;
   }

   static void a(GuiBeacon var0, String var1, int var2, int var3) {
      var0.drawCreativeTabHoveringText(var1, var2, var3);
   }

   static void b(GuiBeacon var0, String var1, int var2, int var3) {
      var0.drawCreativeTabHoveringText(var1, var2, var3);
   }

   static void c(GuiBeacon var0, String var1, int var2, int var3) {
      var0.drawCreativeTabHoveringText(var1, var2, var3);
   }

   private static IOException a(IOException var0) {
      return var0;
   }
}
