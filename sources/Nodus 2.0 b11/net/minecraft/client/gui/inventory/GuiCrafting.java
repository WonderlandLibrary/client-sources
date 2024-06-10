/*  1:   */ package net.minecraft.client.gui.inventory;
/*  2:   */ 
/*  3:   */ import net.minecraft.client.Minecraft;
/*  4:   */ import net.minecraft.client.gui.FontRenderer;
/*  5:   */ import net.minecraft.client.renderer.texture.TextureManager;
/*  6:   */ import net.minecraft.client.resources.I18n;
/*  7:   */ import net.minecraft.entity.player.InventoryPlayer;
/*  8:   */ import net.minecraft.inventory.ContainerWorkbench;
/*  9:   */ import net.minecraft.util.ResourceLocation;
/* 10:   */ import net.minecraft.world.World;
/* 11:   */ import org.lwjgl.opengl.GL11;
/* 12:   */ 
/* 13:   */ public class GuiCrafting
/* 14:   */   extends GuiContainer
/* 15:   */ {
/* 16:12 */   private static final ResourceLocation field_147019_u = new ResourceLocation("textures/gui/container/crafting_table.png");
/* 17:   */   private static final String __OBFID = "CL_00000750";
/* 18:   */   
/* 19:   */   public GuiCrafting(InventoryPlayer par1InventoryPlayer, World par2World, int par3, int par4, int par5)
/* 20:   */   {
/* 21:17 */     super(new ContainerWorkbench(par1InventoryPlayer, par2World, par3, par4, par5));
/* 22:   */   }
/* 23:   */   
/* 24:   */   protected void func_146979_b(int p_146979_1_, int p_146979_2_)
/* 25:   */   {
/* 26:22 */     this.fontRendererObj.drawString(I18n.format("container.crafting", new Object[0]), 28, 6, 4210752);
/* 27:23 */     this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.field_147000_g - 96 + 2, 4210752);
/* 28:   */   }
/* 29:   */   
/* 30:   */   protected void func_146976_a(float p_146976_1_, int p_146976_2_, int p_146976_3_)
/* 31:   */   {
/* 32:28 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 33:29 */     this.mc.getTextureManager().bindTexture(field_147019_u);
/* 34:30 */     int var4 = (width - this.field_146999_f) / 2;
/* 35:31 */     int var5 = (height - this.field_147000_g) / 2;
/* 36:32 */     drawTexturedModalRect(var4, var5, 0, 0, this.field_146999_f, this.field_147000_g);
/* 37:   */   }
/* 38:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.inventory.GuiCrafting
 * JD-Core Version:    0.7.0.1
 */