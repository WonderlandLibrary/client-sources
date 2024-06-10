/*   1:    */ package net.minecraft.client.renderer.tileentity;
/*   2:    */ 
/*   3:    */ import java.util.Collection;
/*   4:    */ import java.util.HashMap;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.Map;
/*   7:    */ import net.minecraft.client.gui.FontRenderer;
/*   8:    */ import net.minecraft.client.renderer.OpenGlHelper;
/*   9:    */ import net.minecraft.client.renderer.entity.RenderEnchantmentTable;
/*  10:    */ import net.minecraft.client.renderer.texture.TextureManager;
/*  11:    */ import net.minecraft.crash.CrashReport;
/*  12:    */ import net.minecraft.crash.CrashReportCategory;
/*  13:    */ import net.minecraft.entity.EntityLivingBase;
/*  14:    */ import net.minecraft.tileentity.TileEntity;
/*  15:    */ import net.minecraft.tileentity.TileEntityBeacon;
/*  16:    */ import net.minecraft.tileentity.TileEntityChest;
/*  17:    */ import net.minecraft.tileentity.TileEntityEnchantmentTable;
/*  18:    */ import net.minecraft.tileentity.TileEntityEndPortal;
/*  19:    */ import net.minecraft.tileentity.TileEntityEnderChest;
/*  20:    */ import net.minecraft.tileentity.TileEntityMobSpawner;
/*  21:    */ import net.minecraft.tileentity.TileEntityPiston;
/*  22:    */ import net.minecraft.tileentity.TileEntitySign;
/*  23:    */ import net.minecraft.tileentity.TileEntitySkull;
/*  24:    */ import net.minecraft.util.ReportedException;
/*  25:    */ import net.minecraft.world.World;
/*  26:    */ import org.lwjgl.opengl.GL11;
/*  27:    */ 
/*  28:    */ public class TileEntityRendererDispatcher
/*  29:    */ {
/*  30: 29 */   private Map mapSpecialRenderers = new HashMap();
/*  31: 30 */   public static TileEntityRendererDispatcher instance = new TileEntityRendererDispatcher();
/*  32:    */   private FontRenderer field_147557_n;
/*  33:    */   public static double staticPlayerX;
/*  34:    */   public static double staticPlayerY;
/*  35:    */   public static double staticPlayerZ;
/*  36:    */   public TextureManager field_147553_e;
/*  37:    */   public World field_147550_f;
/*  38:    */   public EntityLivingBase field_147551_g;
/*  39:    */   public float field_147562_h;
/*  40:    */   public float field_147563_i;
/*  41:    */   public double field_147560_j;
/*  42:    */   public double field_147561_k;
/*  43:    */   public double field_147558_l;
/*  44:    */   private static final String __OBFID = "CL_00000963";
/*  45:    */   
/*  46:    */   private TileEntityRendererDispatcher()
/*  47:    */   {
/*  48: 47 */     this.mapSpecialRenderers.put(TileEntitySign.class, new TileEntitySignRenderer());
/*  49: 48 */     this.mapSpecialRenderers.put(TileEntityMobSpawner.class, new TileEntityMobSpawnerRenderer());
/*  50: 49 */     this.mapSpecialRenderers.put(TileEntityPiston.class, new TileEntityRendererPiston());
/*  51: 50 */     this.mapSpecialRenderers.put(TileEntityChest.class, new TileEntityChestRenderer());
/*  52: 51 */     this.mapSpecialRenderers.put(TileEntityEnderChest.class, new TileEntityEnderChestRenderer());
/*  53: 52 */     this.mapSpecialRenderers.put(TileEntityEnchantmentTable.class, new RenderEnchantmentTable());
/*  54: 53 */     this.mapSpecialRenderers.put(TileEntityEndPortal.class, new RenderEndPortal());
/*  55: 54 */     this.mapSpecialRenderers.put(TileEntityBeacon.class, new TileEntityBeaconRenderer());
/*  56: 55 */     this.mapSpecialRenderers.put(TileEntitySkull.class, new TileEntitySkullRenderer());
/*  57: 56 */     Iterator var1 = this.mapSpecialRenderers.values().iterator();
/*  58: 58 */     while (var1.hasNext())
/*  59:    */     {
/*  60: 60 */       TileEntitySpecialRenderer var2 = (TileEntitySpecialRenderer)var1.next();
/*  61: 61 */       var2.func_147497_a(this);
/*  62:    */     }
/*  63:    */   }
/*  64:    */   
/*  65:    */   public TileEntitySpecialRenderer getSpecialRendererByClass(Class p_147546_1_)
/*  66:    */   {
/*  67: 67 */     TileEntitySpecialRenderer var2 = (TileEntitySpecialRenderer)this.mapSpecialRenderers.get(p_147546_1_);
/*  68: 69 */     if ((var2 == null) && (p_147546_1_ != TileEntity.class))
/*  69:    */     {
/*  70: 71 */       var2 = getSpecialRendererByClass(p_147546_1_.getSuperclass());
/*  71: 72 */       this.mapSpecialRenderers.put(p_147546_1_, var2);
/*  72:    */     }
/*  73: 75 */     return var2;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public boolean hasSpecialRenderer(TileEntity p_147545_1_)
/*  77:    */   {
/*  78: 80 */     return getSpecialRenderer(p_147545_1_) != null;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public TileEntitySpecialRenderer getSpecialRenderer(TileEntity p_147547_1_)
/*  82:    */   {
/*  83: 85 */     return p_147547_1_ == null ? null : getSpecialRendererByClass(p_147547_1_.getClass());
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void func_147542_a(World p_147542_1_, TextureManager p_147542_2_, FontRenderer p_147542_3_, EntityLivingBase p_147542_4_, float p_147542_5_)
/*  87:    */   {
/*  88: 90 */     if (this.field_147550_f != p_147542_1_) {
/*  89: 92 */       func_147543_a(p_147542_1_);
/*  90:    */     }
/*  91: 95 */     this.field_147553_e = p_147542_2_;
/*  92: 96 */     this.field_147551_g = p_147542_4_;
/*  93: 97 */     this.field_147557_n = p_147542_3_;
/*  94: 98 */     this.field_147562_h = (p_147542_4_.prevRotationYaw + (p_147542_4_.rotationYaw - p_147542_4_.prevRotationYaw) * p_147542_5_);
/*  95: 99 */     this.field_147563_i = (p_147542_4_.prevRotationPitch + (p_147542_4_.rotationPitch - p_147542_4_.prevRotationPitch) * p_147542_5_);
/*  96:100 */     this.field_147560_j = (p_147542_4_.lastTickPosX + (p_147542_4_.posX - p_147542_4_.lastTickPosX) * p_147542_5_);
/*  97:101 */     this.field_147561_k = (p_147542_4_.lastTickPosY + (p_147542_4_.posY - p_147542_4_.lastTickPosY) * p_147542_5_);
/*  98:102 */     this.field_147558_l = (p_147542_4_.lastTickPosZ + (p_147542_4_.posZ - p_147542_4_.lastTickPosZ) * p_147542_5_);
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void func_147544_a(TileEntity p_147544_1_, float p_147544_2_)
/* 102:    */   {
/* 103:107 */     if (p_147544_1_.getDistanceFrom(this.field_147560_j, this.field_147561_k, this.field_147558_l) < p_147544_1_.getMaxRenderDistanceSquared())
/* 104:    */     {
/* 105:109 */       int var3 = this.field_147550_f.getLightBrightnessForSkyBlocks(p_147544_1_.field_145851_c, p_147544_1_.field_145848_d, p_147544_1_.field_145849_e, 0);
/* 106:110 */       int var4 = var3 % 65536;
/* 107:111 */       int var5 = var3 / 65536;
/* 108:112 */       OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var4 / 1.0F, var5 / 1.0F);
/* 109:113 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 110:114 */       func_147549_a(p_147544_1_, p_147544_1_.field_145851_c - staticPlayerX, p_147544_1_.field_145848_d - staticPlayerY, p_147544_1_.field_145849_e - staticPlayerZ, p_147544_2_);
/* 111:    */     }
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void func_147549_a(TileEntity p_147549_1_, double p_147549_2_, double p_147549_4_, double p_147549_6_, float p_147549_8_)
/* 115:    */   {
/* 116:120 */     TileEntitySpecialRenderer var9 = getSpecialRenderer(p_147549_1_);
/* 117:122 */     if (var9 != null) {
/* 118:    */       try
/* 119:    */       {
/* 120:126 */         var9.renderTileEntityAt(p_147549_1_, p_147549_2_, p_147549_4_, p_147549_6_, p_147549_8_);
/* 121:    */       }
/* 122:    */       catch (Throwable var13)
/* 123:    */       {
/* 124:130 */         CrashReport var11 = CrashReport.makeCrashReport(var13, "Rendering Block Entity");
/* 125:131 */         CrashReportCategory var12 = var11.makeCategory("Block Entity Details");
/* 126:132 */         p_147549_1_.func_145828_a(var12);
/* 127:133 */         throw new ReportedException(var11);
/* 128:    */       }
/* 129:    */     }
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void func_147543_a(World p_147543_1_)
/* 133:    */   {
/* 134:140 */     this.field_147550_f = p_147543_1_;
/* 135:141 */     Iterator var2 = this.mapSpecialRenderers.values().iterator();
/* 136:143 */     while (var2.hasNext())
/* 137:    */     {
/* 138:145 */       TileEntitySpecialRenderer var3 = (TileEntitySpecialRenderer)var2.next();
/* 139:147 */       if (var3 != null) {
/* 140:149 */         var3.func_147496_a(p_147543_1_);
/* 141:    */       }
/* 142:    */     }
/* 143:    */   }
/* 144:    */   
/* 145:    */   public FontRenderer func_147548_a()
/* 146:    */   {
/* 147:156 */     return this.field_147557_n;
/* 148:    */   }
/* 149:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher
 * JD-Core Version:    0.7.0.1
 */