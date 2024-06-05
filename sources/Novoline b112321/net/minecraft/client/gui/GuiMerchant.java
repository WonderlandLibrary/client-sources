package net.minecraft.client.gui;

import io.netty.buffer.Unpooled;
import java.io.IOException;
import net.aHz;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMerchant$MerchantButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerMerchant;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.world.World;

public class GuiMerchant extends aHz {
   private static final ResourceLocation MERCHANT_GUI_TEXTURE = new ResourceLocation("textures/gui/container/villager.png");
   private final IMerchant merchant;
   private GuiMerchant$MerchantButton nextButton;
   private GuiMerchant$MerchantButton previousButton;
   private int selectedMerchantRecipe;
   private final IChatComponent chatComponent;

   public GuiMerchant(InventoryPlayer var1, IMerchant var2, World var3) {
      super(new ContainerMerchant(var1, var2, var3));
      this.merchant = var2;
      this.chatComponent = var2.getDisplayName();
   }

   public void initGui() {
      super.initGui();
      int var1 = (this.width - this.y) / 2;
      int var2 = (this.height - this.ab) / 2;
      this.buttonList.add(this.nextButton = new GuiMerchant$MerchantButton(1, var1 + 120 + 27, var2 + 24 - 1, true));
      this.buttonList.add(this.previousButton = new GuiMerchant$MerchantButton(2, var1 + 36 - 19, var2 + 24 - 1, false));
      this.nextButton.enabled = false;
      this.previousButton.enabled = false;
   }

   protected void drawGuiContainerForegroundLayer(int var1, int var2) {
      String var3 = this.chatComponent.getUnformattedText();
      this.fontRendererObj.drawString(var3, (float)(this.y / 2 - this.fontRendererObj.d(var3) / 2), 6.0F, 4210752);
      this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8.0F, (float)(this.ab - 96 + 2), 4210752);
   }

   public void updateScreen() {
      super.updateScreen();
      MerchantRecipeList var1 = this.merchant.getRecipes(this.mc.player);
      this.nextButton.enabled = this.selectedMerchantRecipe < var1.size() - 1;
      this.previousButton.enabled = this.selectedMerchantRecipe > 0;
   }

   protected void actionPerformed(GuiButton var1) throws IOException {
      boolean var2 = false;
      if(var1 == this.nextButton) {
         ++this.selectedMerchantRecipe;
         MerchantRecipeList var3 = this.merchant.getRecipes(this.mc.player);
         if(this.selectedMerchantRecipe >= var3.size()) {
            this.selectedMerchantRecipe = var3.size() - 1;
         }

         var2 = true;
      } else if(var1 == this.previousButton) {
         --this.selectedMerchantRecipe;
         if(this.selectedMerchantRecipe < 0) {
            this.selectedMerchantRecipe = 0;
         }

         var2 = true;
      }

      ((ContainerMerchant)this.V).setCurrentRecipeIndex(this.selectedMerchantRecipe);
      PacketBuffer var6 = new PacketBuffer(Unpooled.buffer());
      var6.writeInt(this.selectedMerchantRecipe);
      this.mc.getNetHandler().b(new C17PacketCustomPayload("MC|TrSel", var6));
   }

   protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      this.mc.getTextureManager().bindTexture(MERCHANT_GUI_TEXTURE);
      int var4 = (this.width - this.y) / 2;
      int var5 = (this.height - this.ab) / 2;
      this.drawTexturedModalRect(var4, var5, 0, 0, this.y, this.ab);
      MerchantRecipeList var6 = this.merchant.getRecipes(this.mc.player);
      if(!var6.isEmpty()) {
         int var7 = this.selectedMerchantRecipe;
         if(var7 >= var6.size()) {
            return;
         }

         MerchantRecipe var8 = (MerchantRecipe)var6.get(var7);
         if(var8.isRecipeDisabled()) {
            this.mc.getTextureManager().bindTexture(MERCHANT_GUI_TEXTURE);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.disableLighting();
            this.drawTexturedModalRect(this.R + 83, this.W + 21, 212, 0, 28, 21);
            this.drawTexturedModalRect(this.R + 83, this.W + 51, 212, 0, 28, 21);
         }
      }

   }

   public void drawScreen(int var1, int var2, float var3) {
      super.drawScreen(var1, var2, var3);
      MerchantRecipeList var4 = this.merchant.getRecipes(this.mc.player);
      if(!var4.isEmpty()) {
         int var5 = (this.width - this.y) / 2;
         int var6 = (this.height - this.ab) / 2;
         int var7 = this.selectedMerchantRecipe;
         MerchantRecipe var8 = (MerchantRecipe)var4.get(var7);
         ItemStack var9 = var8.getItemToBuy();
         ItemStack var10 = var8.getSecondItemToBuy();
         ItemStack var11 = var8.getItemToSell();
         GlStateManager.pushMatrix();
         RenderHelper.enableGUIStandardItemLighting();
         GlStateManager.disableLighting();
         GlStateManager.enableRescaleNormal();
         GlStateManager.enableColorMaterial();
         GlStateManager.enableLighting();
         this.itemRender.zLevel = 100.0F;
         this.itemRender.renderItemAndEffectIntoGUI(var9, (float)(var5 + 36), (float)(var6 + 24));
         this.itemRender.renderItemOverlays(this.fontRendererObj, var9, var5 + 36, var6 + 24);
         this.itemRender.renderItemAndEffectIntoGUI(var10, (float)(var5 + 62), (float)(var6 + 24));
         this.itemRender.renderItemOverlays(this.fontRendererObj, var10, var5 + 62, var6 + 24);
         this.itemRender.renderItemAndEffectIntoGUI(var11, (float)(var5 + 120), (float)(var6 + 24));
         this.itemRender.renderItemOverlays(this.fontRendererObj, var11, var5 + 120, var6 + 24);
         this.itemRender.zLevel = 0.0F;
         GlStateManager.disableLighting();
         if(this.a(36, 24, 16, 16, var1, var2)) {
            this.renderToolTip(var9, var1, var2);
         } else if(this.a(62, 24, 16, 16, var1, var2)) {
            this.renderToolTip(var10, var1, var2);
         } else if(this.a(120, 24, 16, 16, var1, var2)) {
            this.renderToolTip(var11, var1, var2);
         } else if(var8.isRecipeDisabled() && (this.a(83, 21, 28, 21, var1, var2) || this.a(83, 51, 28, 21, var1, var2))) {
            this.drawCreativeTabHoveringText(I18n.format("merchant.deprecated", new Object[0]), var1, var2);
         }

         GlStateManager.popMatrix();
         GlStateManager.enableLighting();
         GlStateManager.enableDepth();
         RenderHelper.enableStandardItemLighting();
      }

   }

   public IMerchant getMerchant() {
      return this.merchant;
   }

   static ResourceLocation access$000() {
      return MERCHANT_GUI_TEXTURE;
   }

   private static IOException a(IOException var0) {
      return var0;
   }
}
