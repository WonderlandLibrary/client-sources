package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import net.aG4;
import net.aHz;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.model.ModelBook;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerEnchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnchantmentNameParts;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IWorldNameable;
import net.minecraft.world.World;
import org.lwjgl.util.glu.Project;

public class GuiEnchantment extends aHz {
   private static final ResourceLocation ENCHANTMENT_TABLE_GUI_TEXTURE = new ResourceLocation("textures/gui/container/enchanting_table.png");
   private static final ResourceLocation ENCHANTMENT_TABLE_BOOK_TEXTURE = new ResourceLocation("textures/entity/enchanting_table_book.png");
   private static final ModelBook MODEL_BOOK = new ModelBook();
   private final InventoryPlayer playerInventory;
   private final Random random = new Random();
   private final ContainerEnchantment container;
   public int field_147073_u;
   public float field_147071_v;
   public float field_147069_w;
   public float field_147082_x;
   public float field_147081_y;
   public float field_147080_z;
   public float field_147076_A;
   ItemStack field_147077_B;
   private final IWorldNameable field_175380_I;

   public GuiEnchantment(InventoryPlayer var1, World var2, IWorldNameable var3) {
      super(new ContainerEnchantment(var1, var2));
      this.playerInventory = var1;
      this.container = (ContainerEnchantment)this.V;
      this.field_175380_I = var3;
   }

   protected void drawGuiContainerForegroundLayer(int var1, int var2) {
      this.fontRendererObj.drawString(this.field_175380_I.getDisplayName().getUnformattedText(), 12.0F, 5.0F, 4210752);
      this.fontRendererObj.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8.0F, (float)(this.ab - 96 + 2), 4210752);
   }

   public void updateScreen() {
      super.updateScreen();
      this.func_147068_g();
   }

   protected void mouseClicked(int var1, int var2, int var3) throws IOException {
      super.mouseClicked(var1, var2, var3);
      int var4 = (this.width - this.y) / 2;
      int var5 = (this.height - this.ab) / 2;

      for(int var6 = 0; var6 < 3; ++var6) {
         int var7 = var1 - (var4 + 60);
         int var8 = var2 - (var5 + 14 + 19 * var6);
         if(var7 < 108 && var8 < 19 && this.container.enchantItem(this.mc.player, var6)) {
            this.mc.at.a(this.container.windowId, var6);
         }
      }

   }

   protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      this.mc.getTextureManager().bindTexture(ENCHANTMENT_TABLE_GUI_TEXTURE);
      int var4 = (this.width - this.y) / 2;
      int var5 = (this.height - this.ab) / 2;
      this.drawTexturedModalRect(var4, var5, 0, 0, this.y, this.ab);
      GlStateManager.pushMatrix();
      GlStateManager.matrixMode(5889);
      GlStateManager.pushMatrix();
      GlStateManager.loadIdentity();
      ScaledResolution var6 = new ScaledResolution(this.mc);
      GlStateManager.viewport((var6.getScaledWidth() - 320) / 2 * var6.getScaleFactor(), (var6.getScaledHeight() - 240) / 2 * var6.getScaleFactor(), 320 * var6.getScaleFactor(), 240 * var6.getScaleFactor());
      GlStateManager.translate(-0.34F, 0.23F, 0.0F);
      Project.gluPerspective(90.0F, 1.3333334F, 9.0F, 80.0F);
      float var7 = 1.0F;
      GlStateManager.matrixMode(5888);
      GlStateManager.loadIdentity();
      RenderHelper.enableStandardItemLighting();
      GlStateManager.translate(0.0F, 3.3F, -16.0F);
      GlStateManager.scale(var7, var7, var7);
      float var8 = 5.0F;
      GlStateManager.scale(var8, var8, var8);
      GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
      this.mc.getTextureManager().bindTexture(ENCHANTMENT_TABLE_BOOK_TEXTURE);
      GlStateManager.rotate(20.0F, 1.0F, 0.0F, 0.0F);
      float var9 = this.field_147076_A + (this.field_147080_z - this.field_147076_A) * var1;
      GlStateManager.translate((1.0F - var9) * 0.2F, (1.0F - var9) * 0.1F, (1.0F - var9) * 0.25F);
      GlStateManager.rotate(-(1.0F - var9) * 90.0F - 90.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
      float var10 = this.field_147069_w + (this.field_147071_v - this.field_147069_w) * var1 + 0.25F;
      float var11 = this.field_147069_w + (this.field_147071_v - this.field_147069_w) * var1 + 0.75F;
      var10 = (var10 - (float)MathHelper.truncateDoubleToInt((double)var10)) * 1.6F - 0.3F;
      var11 = (var11 - (float)MathHelper.truncateDoubleToInt((double)var11)) * 1.6F - 0.3F;
      if(var10 < 0.0F) {
         var10 = 0.0F;
      }

      if(var11 < 0.0F) {
         var11 = 0.0F;
      }

      if(var10 > 1.0F) {
         var10 = 1.0F;
      }

      if(var11 > 1.0F) {
         var11 = 1.0F;
      }

      GlStateManager.enableRescaleNormal();
      aG4.a(MODEL_BOOK, (Entity)null, 0.0F, var10, var11, var9, 0.0F, 0.0625F);
      GlStateManager.disableRescaleNormal();
      RenderHelper.disableStandardItemLighting();
      GlStateManager.matrixMode(5889);
      GlStateManager.viewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
      GlStateManager.popMatrix();
      GlStateManager.matrixMode(5888);
      GlStateManager.popMatrix();
      RenderHelper.disableStandardItemLighting();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      EnchantmentNameParts.getInstance().reseedRandomGenerator((long)this.container.xpSeed);
      int var12 = this.container.getLapisAmount();

      for(int var13 = 0; var13 < 3; ++var13) {
         int var14 = var4 + 60;
         int var15 = var14 + 20;
         boolean var16 = true;
         String var17 = EnchantmentNameParts.getInstance().generateNewRandomName();
         this.zLevel = 0.0F;
         this.mc.getTextureManager().bindTexture(ENCHANTMENT_TABLE_GUI_TEXTURE);
         int var18 = this.container.enchantLevels[var13];
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         this.drawTexturedModalRect(var14, var5 + 14 + 19 * var13, 0, 185, 108, 19);
      }

   }

   public void drawScreen(int var1, int var2, float var3) {
      super.drawScreen(var1, var2, var3);
      boolean var4 = this.mc.player.abilities.isCreative();
      int var5 = this.container.getLapisAmount();

      for(int var6 = 0; var6 < 3; ++var6) {
         int var7 = this.container.enchantLevels[var6];
         int var8 = this.container.field_178151_h[var6];
         int var9 = var6 + 1;
         if(this.a(60, 14 + 19 * var6, 108, 17, var1, var2)) {
            ArrayList var10 = Lists.newArrayList();
            if(Enchantment.getEnchantmentById(var8 & 255) != null) {
               String var11 = Enchantment.getEnchantmentById(var8 & 255).getTranslatedName((var8 & '\uff00') >> 8);
               var10.add(EnumChatFormatting.WHITE.toString() + EnumChatFormatting.ITALIC.toString() + I18n.format("container.enchant.clue", new Object[]{var11}));
            }

            var10.add("");
            if(this.mc.player.experienceLevel < var7) {
               var10.add(EnumChatFormatting.RED.toString() + "Level Requirement: " + this.container.enchantLevels[var6]);
            } else {
               String var12 = "";
               if(var9 == 1) {
                  var12 = I18n.format("container.enchant.lapis.one", new Object[0]);
               } else {
                  var12 = I18n.format("container.enchant.lapis.many", new Object[]{Integer.valueOf(var9)});
               }

               if(var5 >= var9) {
                  var10.add(EnumChatFormatting.GRAY.toString() + "" + var12);
               } else {
                  var10.add(EnumChatFormatting.RED.toString() + "" + var12);
               }

               if(var9 == 1) {
                  var12 = I18n.format("container.enchant.level.one", new Object[0]);
               } else {
                  var12 = I18n.format("container.enchant.level.many", new Object[]{Integer.valueOf(var9)});
               }

               var10.add(EnumChatFormatting.GRAY.toString() + "" + var12);
            }

            this.drawHoveringText(var10, var1, var2);
            break;
         }
      }

   }

   public void func_147068_g() {
      ItemStack var1 = this.V.getSlot(0).getStack();
      if(!ItemStack.areItemStacksEqual(var1, this.field_147077_B)) {
         this.field_147077_B = var1;

         while(true) {
            this.field_147082_x += (float)(this.random.nextInt(4) - this.random.nextInt(4));
            if(this.field_147071_v > this.field_147082_x + 1.0F || this.field_147071_v < this.field_147082_x - 1.0F) {
               break;
            }
         }
      }

      ++this.field_147073_u;
      this.field_147069_w = this.field_147071_v;
      this.field_147076_A = this.field_147080_z;
      boolean var2 = false;

      for(int var3 = 0; var3 < 3; ++var3) {
         if(this.container.enchantLevels[var3] != 0) {
            var2 = true;
         }
      }

      this.field_147080_z += 0.2F;
      this.field_147080_z = MathHelper.clamp_float(this.field_147080_z, 0.0F, 1.0F);
      float var6 = (this.field_147082_x - this.field_147071_v) * 0.4F;
      float var4 = 0.2F;
      var6 = MathHelper.clamp_float(var6, -var4, var4);
      this.field_147081_y += (var6 - this.field_147081_y) * 0.9F;
      this.field_147071_v += this.field_147081_y;
   }

   private static IOException a(IOException var0) {
      return var0;
   }
}
