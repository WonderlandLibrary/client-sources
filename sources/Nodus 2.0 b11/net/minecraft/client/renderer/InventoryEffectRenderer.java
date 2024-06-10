/*  1:   */ package net.minecraft.client.renderer;
/*  2:   */ 
/*  3:   */ import java.util.Collection;
/*  4:   */ import java.util.Iterator;
/*  5:   */ import net.minecraft.client.Minecraft;
/*  6:   */ import net.minecraft.client.entity.EntityClientPlayerMP;
/*  7:   */ import net.minecraft.client.gui.FontRenderer;
/*  8:   */ import net.minecraft.client.gui.inventory.GuiContainer;
/*  9:   */ import net.minecraft.client.renderer.texture.TextureManager;
/* 10:   */ import net.minecraft.client.resources.I18n;
/* 11:   */ import net.minecraft.inventory.Container;
/* 12:   */ import net.minecraft.potion.Potion;
/* 13:   */ import net.minecraft.potion.PotionEffect;
/* 14:   */ import org.lwjgl.opengl.GL11;
/* 15:   */ 
/* 16:   */ public abstract class InventoryEffectRenderer
/* 17:   */   extends GuiContainer
/* 18:   */ {
/* 19:   */   private boolean field_147045_u;
/* 20:   */   private static final String __OBFID = "CL_00000755";
/* 21:   */   
/* 22:   */   public InventoryEffectRenderer(Container par1Container)
/* 23:   */   {
/* 24:19 */     super(par1Container);
/* 25:   */   }
/* 26:   */   
/* 27:   */   public void initGui()
/* 28:   */   {
/* 29:27 */     super.initGui();
/* 30:29 */     if (!this.mc.thePlayer.getActivePotionEffects().isEmpty())
/* 31:   */     {
/* 32:31 */       this.field_147003_i = (160 + (width - this.field_146999_f - 200) / 2);
/* 33:32 */       this.field_147045_u = true;
/* 34:   */     }
/* 35:   */   }
/* 36:   */   
/* 37:   */   public void drawScreen(int par1, int par2, float par3)
/* 38:   */   {
/* 39:41 */     super.drawScreen(par1, par2, par3);
/* 40:43 */     if (this.field_147045_u) {
/* 41:45 */       func_147044_g();
/* 42:   */     }
/* 43:   */   }
/* 44:   */   
/* 45:   */   private void func_147044_g()
/* 46:   */   {
/* 47:51 */     int var1 = this.field_147003_i - 124;
/* 48:52 */     int var2 = this.field_147009_r;
/* 49:53 */     boolean var3 = true;
/* 50:54 */     Collection var4 = this.mc.thePlayer.getActivePotionEffects();
/* 51:56 */     if (!var4.isEmpty())
/* 52:   */     {
/* 53:58 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 54:59 */       GL11.glDisable(2896);
/* 55:60 */       int var5 = 33;
/* 56:62 */       if (var4.size() > 5) {
/* 57:64 */         var5 = 132 / (var4.size() - 1);
/* 58:   */       }
/* 59:67 */       for (Iterator var6 = this.mc.thePlayer.getActivePotionEffects().iterator(); var6.hasNext(); var2 += var5)
/* 60:   */       {
/* 61:69 */         PotionEffect var7 = (PotionEffect)var6.next();
/* 62:70 */         Potion var8 = Potion.potionTypes[var7.getPotionID()];
/* 63:71 */         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 64:72 */         this.mc.getTextureManager().bindTexture(field_147001_a);
/* 65:73 */         drawTexturedModalRect(var1, var2, 0, 166, 140, 32);
/* 66:75 */         if (var8.hasStatusIcon())
/* 67:   */         {
/* 68:77 */           int var9 = var8.getStatusIconIndex();
/* 69:78 */           drawTexturedModalRect(var1 + 6, var2 + 7, 0 + var9 % 8 * 18, 198 + var9 / 8 * 18, 18, 18);
/* 70:   */         }
/* 71:81 */         String var11 = I18n.format(var8.getName(), new Object[0]);
/* 72:83 */         if (var7.getAmplifier() == 1) {
/* 73:85 */           var11 = var11 + " II";
/* 74:87 */         } else if (var7.getAmplifier() == 2) {
/* 75:89 */           var11 = var11 + " III";
/* 76:91 */         } else if (var7.getAmplifier() == 3) {
/* 77:93 */           var11 = var11 + " IV";
/* 78:   */         }
/* 79:96 */         this.fontRendererObj.drawStringWithShadow(var11, var1 + 10 + 18, var2 + 6, 16777215);
/* 80:97 */         String var10 = Potion.getDurationString(var7);
/* 81:98 */         this.fontRendererObj.drawStringWithShadow(var10, var1 + 10 + 18, var2 + 6 + 10, 8355711);
/* 82:   */       }
/* 83:   */     }
/* 84:   */   }
/* 85:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.InventoryEffectRenderer
 * JD-Core Version:    0.7.0.1
 */