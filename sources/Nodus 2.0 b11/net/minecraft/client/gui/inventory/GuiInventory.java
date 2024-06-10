/*   1:    */ package net.minecraft.client.gui.inventory;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import me.connorm.Nodus.ui.NodusGuiButton;
/*   5:    */ import net.minecraft.client.Minecraft;
/*   6:    */ import net.minecraft.client.entity.EntityClientPlayerMP;
/*   7:    */ import net.minecraft.client.gui.FontRenderer;
/*   8:    */ import net.minecraft.client.gui.achievement.GuiAchievements;
/*   9:    */ import net.minecraft.client.gui.achievement.GuiStats;
/*  10:    */ import net.minecraft.client.multiplayer.PlayerControllerMP;
/*  11:    */ import net.minecraft.client.renderer.InventoryEffectRenderer;
/*  12:    */ import net.minecraft.client.renderer.OpenGlHelper;
/*  13:    */ import net.minecraft.client.renderer.RenderHelper;
/*  14:    */ import net.minecraft.client.renderer.entity.RenderManager;
/*  15:    */ import net.minecraft.client.renderer.texture.TextureManager;
/*  16:    */ import net.minecraft.client.resources.I18n;
/*  17:    */ import net.minecraft.entity.EntityLivingBase;
/*  18:    */ import net.minecraft.entity.player.EntityPlayer;
/*  19:    */ import org.lwjgl.opengl.GL11;
/*  20:    */ 
/*  21:    */ public class GuiInventory
/*  22:    */   extends InventoryEffectRenderer
/*  23:    */ {
/*  24:    */   private float field_147048_u;
/*  25:    */   private float field_147047_v;
/*  26:    */   private static final String __OBFID = "CL_00000761";
/*  27:    */   
/*  28:    */   public GuiInventory(EntityPlayer par1EntityPlayer)
/*  29:    */   {
/*  30: 25 */     super(par1EntityPlayer.inventoryContainer);
/*  31: 26 */     this.field_146291_p = true;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void updateScreen()
/*  35:    */   {
/*  36: 34 */     if (this.mc.playerController.isInCreativeMode()) {
/*  37: 36 */       this.mc.displayGuiScreen(new GuiContainerCreative(this.mc.thePlayer));
/*  38:    */     }
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void initGui()
/*  42:    */   {
/*  43: 45 */     this.buttonList.clear();
/*  44: 47 */     if (this.mc.playerController.isInCreativeMode()) {
/*  45: 49 */       this.mc.displayGuiScreen(new GuiContainerCreative(this.mc.thePlayer));
/*  46:    */     } else {
/*  47: 53 */       super.initGui();
/*  48:    */     }
/*  49:    */   }
/*  50:    */   
/*  51:    */   protected void func_146979_b(int p_146979_1_, int p_146979_2_)
/*  52:    */   {
/*  53: 59 */     this.fontRendererObj.drawString(I18n.format("container.crafting", new Object[0]), 86, 16, 4210752);
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void drawScreen(int par1, int par2, float par3)
/*  57:    */   {
/*  58: 67 */     super.drawScreen(par1, par2, par3);
/*  59: 68 */     this.field_147048_u = par1;
/*  60: 69 */     this.field_147047_v = par2;
/*  61:    */   }
/*  62:    */   
/*  63:    */   protected void func_146976_a(float p_146976_1_, int p_146976_2_, int p_146976_3_)
/*  64:    */   {
/*  65: 74 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  66: 75 */     this.mc.getTextureManager().bindTexture(field_147001_a);
/*  67: 76 */     int var4 = this.field_147003_i;
/*  68: 77 */     int var5 = this.field_147009_r;
/*  69: 78 */     drawTexturedModalRect(var4, var5, 0, 0, this.field_146999_f, this.field_147000_g);
/*  70: 79 */     func_147046_a(var4 + 51, var5 + 75, 30, var4 + 51 - this.field_147048_u, var5 + 75 - 50 - this.field_147047_v, this.mc.thePlayer);
/*  71:    */   }
/*  72:    */   
/*  73:    */   public static void func_147046_a(int p_147046_0_, int p_147046_1_, int p_147046_2_, float p_147046_3_, float p_147046_4_, EntityLivingBase p_147046_5_)
/*  74:    */   {
/*  75: 84 */     GL11.glEnable(2903);
/*  76: 85 */     GL11.glPushMatrix();
/*  77: 86 */     GL11.glTranslatef(p_147046_0_, p_147046_1_, 50.0F);
/*  78: 87 */     GL11.glScalef(-p_147046_2_, p_147046_2_, p_147046_2_);
/*  79: 88 */     GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
/*  80: 89 */     float var6 = p_147046_5_.renderYawOffset;
/*  81: 90 */     float var7 = p_147046_5_.rotationYaw;
/*  82: 91 */     float var8 = p_147046_5_.rotationPitch;
/*  83: 92 */     float var9 = p_147046_5_.prevRotationYawHead;
/*  84: 93 */     float var10 = p_147046_5_.rotationYawHead;
/*  85: 94 */     GL11.glRotatef(135.0F, 0.0F, 1.0F, 0.0F);
/*  86: 95 */     RenderHelper.enableStandardItemLighting();
/*  87: 96 */     GL11.glRotatef(-135.0F, 0.0F, 1.0F, 0.0F);
/*  88: 97 */     GL11.glRotatef(-(float)Math.atan(p_147046_4_ / 40.0F) * 20.0F, 1.0F, 0.0F, 0.0F);
/*  89: 98 */     p_147046_5_.renderYawOffset = ((float)Math.atan(p_147046_3_ / 40.0F) * 20.0F);
/*  90: 99 */     p_147046_5_.rotationYaw = ((float)Math.atan(p_147046_3_ / 40.0F) * 40.0F);
/*  91:100 */     p_147046_5_.rotationPitch = (-(float)Math.atan(p_147046_4_ / 40.0F) * 20.0F);
/*  92:101 */     p_147046_5_.rotationYawHead = p_147046_5_.rotationYaw;
/*  93:102 */     p_147046_5_.prevRotationYawHead = p_147046_5_.rotationYaw;
/*  94:103 */     GL11.glTranslatef(0.0F, p_147046_5_.yOffset, 0.0F);
/*  95:104 */     RenderManager.instance.playerViewY = 180.0F;
/*  96:105 */     RenderManager.instance.func_147940_a(p_147046_5_, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
/*  97:106 */     p_147046_5_.renderYawOffset = var6;
/*  98:107 */     p_147046_5_.rotationYaw = var7;
/*  99:108 */     p_147046_5_.rotationPitch = var8;
/* 100:109 */     p_147046_5_.prevRotationYawHead = var9;
/* 101:110 */     p_147046_5_.rotationYawHead = var10;
/* 102:111 */     GL11.glPopMatrix();
/* 103:112 */     RenderHelper.disableStandardItemLighting();
/* 104:113 */     GL11.glDisable(32826);
/* 105:114 */     OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
/* 106:115 */     GL11.glDisable(3553);
/* 107:116 */     OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
/* 108:    */   }
/* 109:    */   
/* 110:    */   protected void actionPerformed(NodusGuiButton p_146284_1_)
/* 111:    */   {
/* 112:121 */     if (p_146284_1_.id == 0) {
/* 113:123 */       this.mc.displayGuiScreen(new GuiAchievements(this, this.mc.thePlayer.func_146107_m()));
/* 114:    */     }
/* 115:126 */     if (p_146284_1_.id == 1) {
/* 116:128 */       this.mc.displayGuiScreen(new GuiStats(this, this.mc.thePlayer.func_146107_m()));
/* 117:    */     }
/* 118:    */   }
/* 119:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.inventory.GuiInventory
 * JD-Core Version:    0.7.0.1
 */