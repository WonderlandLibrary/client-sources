/*  1:   */ package net.minecraft.client.gui.inventory;
/*  2:   */ 
/*  3:   */ import net.minecraft.client.Minecraft;
/*  4:   */ import net.minecraft.client.gui.FontRenderer;
/*  5:   */ import net.minecraft.client.renderer.texture.TextureManager;
/*  6:   */ import net.minecraft.client.resources.I18n;
/*  7:   */ import net.minecraft.entity.player.InventoryPlayer;
/*  8:   */ import net.minecraft.inventory.ContainerBrewingStand;
/*  9:   */ import net.minecraft.tileentity.TileEntityBrewingStand;
/* 10:   */ import net.minecraft.util.ResourceLocation;
/* 11:   */ import org.lwjgl.opengl.GL11;
/* 12:   */ 
/* 13:   */ public class GuiBrewingStand
/* 14:   */   extends GuiContainer
/* 15:   */ {
/* 16:12 */   private static final ResourceLocation field_147014_u = new ResourceLocation("textures/gui/container/brewing_stand.png");
/* 17:   */   private TileEntityBrewingStand field_147013_v;
/* 18:   */   private static final String __OBFID = "CL_00000746";
/* 19:   */   
/* 20:   */   public GuiBrewingStand(InventoryPlayer par1InventoryPlayer, TileEntityBrewingStand par2TileEntityBrewingStand)
/* 21:   */   {
/* 22:18 */     super(new ContainerBrewingStand(par1InventoryPlayer, par2TileEntityBrewingStand));
/* 23:19 */     this.field_147013_v = par2TileEntityBrewingStand;
/* 24:   */   }
/* 25:   */   
/* 26:   */   protected void func_146979_b(int p_146979_1_, int p_146979_2_)
/* 27:   */   {
/* 28:24 */     String var3 = this.field_147013_v.isInventoryNameLocalized() ? this.field_147013_v.getInventoryName() : I18n.format(this.field_147013_v.getInventoryName(), new Object[0]);
/* 29:25 */     this.fontRendererObj.drawString(var3, this.field_146999_f / 2 - this.fontRendererObj.getStringWidth(var3) / 2, 6, 4210752);
/* 30:26 */     this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.field_147000_g - 96 + 2, 4210752);
/* 31:   */   }
/* 32:   */   
/* 33:   */   protected void func_146976_a(float p_146976_1_, int p_146976_2_, int p_146976_3_)
/* 34:   */   {
/* 35:31 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 36:32 */     this.mc.getTextureManager().bindTexture(field_147014_u);
/* 37:33 */     int var4 = (width - this.field_146999_f) / 2;
/* 38:34 */     int var5 = (height - this.field_147000_g) / 2;
/* 39:35 */     drawTexturedModalRect(var4, var5, 0, 0, this.field_146999_f, this.field_147000_g);
/* 40:36 */     int var6 = this.field_147013_v.func_145935_i();
/* 41:38 */     if (var6 > 0)
/* 42:   */     {
/* 43:40 */       int var7 = (int)(28.0F * (1.0F - var6 / 400.0F));
/* 44:42 */       if (var7 > 0) {
/* 45:44 */         drawTexturedModalRect(var4 + 97, var5 + 16, 176, 0, 9, var7);
/* 46:   */       }
/* 47:47 */       int var8 = var6 / 2 % 7;
/* 48:49 */       switch (var8)
/* 49:   */       {
/* 50:   */       case 0: 
/* 51:52 */         var7 = 29;
/* 52:53 */         break;
/* 53:   */       case 1: 
/* 54:56 */         var7 = 24;
/* 55:57 */         break;
/* 56:   */       case 2: 
/* 57:60 */         var7 = 20;
/* 58:61 */         break;
/* 59:   */       case 3: 
/* 60:64 */         var7 = 16;
/* 61:65 */         break;
/* 62:   */       case 4: 
/* 63:68 */         var7 = 11;
/* 64:69 */         break;
/* 65:   */       case 5: 
/* 66:72 */         var7 = 6;
/* 67:73 */         break;
/* 68:   */       case 6: 
/* 69:76 */         var7 = 0;
/* 70:   */       }
/* 71:79 */       if (var7 > 0) {
/* 72:81 */         drawTexturedModalRect(var4 + 65, var5 + 14 + 29 - var7, 185, 29 - var7, 12, var7);
/* 73:   */       }
/* 74:   */     }
/* 75:   */   }
/* 76:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.inventory.GuiBrewingStand
 * JD-Core Version:    0.7.0.1
 */