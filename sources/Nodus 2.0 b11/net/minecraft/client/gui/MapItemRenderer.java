/*   1:    */ package net.minecraft.client.gui;
/*   2:    */ 
/*   3:    */ import com.google.common.collect.Maps;
/*   4:    */ import java.util.Collection;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.Map;
/*   7:    */ import net.minecraft.block.material.MapColor;
/*   8:    */ import net.minecraft.client.renderer.OpenGlHelper;
/*   9:    */ import net.minecraft.client.renderer.Tessellator;
/*  10:    */ import net.minecraft.client.renderer.texture.DynamicTexture;
/*  11:    */ import net.minecraft.client.renderer.texture.TextureManager;
/*  12:    */ import net.minecraft.util.ResourceLocation;
/*  13:    */ import net.minecraft.world.storage.MapData;
/*  14:    */ import net.minecraft.world.storage.MapData.MapCoord;
/*  15:    */ import org.lwjgl.opengl.GL11;
/*  16:    */ 
/*  17:    */ public class MapItemRenderer
/*  18:    */ {
/*  19: 17 */   private static final ResourceLocation field_148253_a = new ResourceLocation("textures/map/map_icons.png");
/*  20:    */   private final TextureManager field_148251_b;
/*  21: 19 */   private final Map field_148252_c = Maps.newHashMap();
/*  22:    */   private static final String __OBFID = "CL_00000663";
/*  23:    */   
/*  24:    */   public MapItemRenderer(TextureManager p_i45009_1_)
/*  25:    */   {
/*  26: 24 */     this.field_148251_b = p_i45009_1_;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public void func_148246_a(MapData p_148246_1_)
/*  30:    */   {
/*  31: 29 */     func_148248_b(p_148246_1_).func_148236_a();
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void func_148250_a(MapData p_148250_1_, boolean p_148250_2_)
/*  35:    */   {
/*  36: 34 */     func_148248_b(p_148250_1_).func_148237_a(p_148250_2_);
/*  37:    */   }
/*  38:    */   
/*  39:    */   private Instance func_148248_b(MapData p_148248_1_)
/*  40:    */   {
/*  41: 39 */     Instance var2 = (Instance)this.field_148252_c.get(p_148248_1_.mapName);
/*  42: 41 */     if (var2 == null)
/*  43:    */     {
/*  44: 43 */       var2 = new Instance(p_148248_1_, null);
/*  45: 44 */       this.field_148252_c.put(p_148248_1_.mapName, var2);
/*  46:    */     }
/*  47: 47 */     return var2;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void func_148249_a()
/*  51:    */   {
/*  52: 52 */     Iterator var1 = this.field_148252_c.values().iterator();
/*  53: 54 */     while (var1.hasNext())
/*  54:    */     {
/*  55: 56 */       Instance var2 = (Instance)var1.next();
/*  56: 57 */       this.field_148251_b.func_147645_c(var2.field_148240_d);
/*  57:    */     }
/*  58: 60 */     this.field_148252_c.clear();
/*  59:    */   }
/*  60:    */   
/*  61:    */   class Instance
/*  62:    */   {
/*  63:    */     private final MapData field_148242_b;
/*  64:    */     private final DynamicTexture field_148243_c;
/*  65:    */     private final ResourceLocation field_148240_d;
/*  66:    */     private final int[] field_148241_e;
/*  67:    */     private static final String __OBFID = "CL_00000665";
/*  68:    */     
/*  69:    */     private Instance(MapData p_i45007_2_)
/*  70:    */     {
/*  71: 73 */       this.field_148242_b = p_i45007_2_;
/*  72: 74 */       this.field_148243_c = new DynamicTexture(128, 128);
/*  73: 75 */       this.field_148241_e = this.field_148243_c.getTextureData();
/*  74: 76 */       this.field_148240_d = MapItemRenderer.this.field_148251_b.getDynamicTextureLocation("map/" + p_i45007_2_.mapName, this.field_148243_c);
/*  75: 78 */       for (int var3 = 0; var3 < this.field_148241_e.length; var3++) {
/*  76: 80 */         this.field_148241_e[var3] = 0;
/*  77:    */       }
/*  78:    */     }
/*  79:    */     
/*  80:    */     private void func_148236_a()
/*  81:    */     {
/*  82: 86 */       for (int var1 = 0; var1 < 16384; var1++)
/*  83:    */       {
/*  84: 88 */         int var2 = this.field_148242_b.colors[var1] & 0xFF;
/*  85: 90 */         if (var2 / 4 == 0) {
/*  86: 92 */           this.field_148241_e[var1] = ((var1 + var1 / 128 & 0x1) * 8 + 16 << 24);
/*  87:    */         } else {
/*  88: 96 */           this.field_148241_e[var1] = MapColor.mapColorArray[(var2 / 4)].func_151643_b(var2 & 0x3);
/*  89:    */         }
/*  90:    */       }
/*  91:100 */       this.field_148243_c.updateDynamicTexture();
/*  92:    */     }
/*  93:    */     
/*  94:    */     private void func_148237_a(boolean p_148237_1_)
/*  95:    */     {
/*  96:105 */       byte var2 = 0;
/*  97:106 */       byte var3 = 0;
/*  98:107 */       Tessellator var4 = Tessellator.instance;
/*  99:108 */       float var5 = 0.0F;
/* 100:109 */       MapItemRenderer.this.field_148251_b.bindTexture(this.field_148240_d);
/* 101:110 */       GL11.glEnable(3042);
/* 102:111 */       OpenGlHelper.glBlendFunc(1, 771, 0, 1);
/* 103:112 */       GL11.glDisable(3008);
/* 104:113 */       var4.startDrawingQuads();
/* 105:114 */       var4.addVertexWithUV(var2 + 0 + var5, var3 + 128 - var5, -0.009999999776482582D, 0.0D, 1.0D);
/* 106:115 */       var4.addVertexWithUV(var2 + 128 - var5, var3 + 128 - var5, -0.009999999776482582D, 1.0D, 1.0D);
/* 107:116 */       var4.addVertexWithUV(var2 + 128 - var5, var3 + 0 + var5, -0.009999999776482582D, 1.0D, 0.0D);
/* 108:117 */       var4.addVertexWithUV(var2 + 0 + var5, var3 + 0 + var5, -0.009999999776482582D, 0.0D, 0.0D);
/* 109:118 */       var4.draw();
/* 110:119 */       GL11.glEnable(3008);
/* 111:120 */       GL11.glDisable(3042);
/* 112:121 */       MapItemRenderer.this.field_148251_b.bindTexture(MapItemRenderer.field_148253_a);
/* 113:122 */       int var6 = 0;
/* 114:123 */       Iterator var7 = this.field_148242_b.playersVisibleOnMap.values().iterator();
/* 115:125 */       while (var7.hasNext())
/* 116:    */       {
/* 117:127 */         MapData.MapCoord var8 = (MapData.MapCoord)var7.next();
/* 118:129 */         if ((!p_148237_1_) || (var8.iconSize == 1))
/* 119:    */         {
/* 120:131 */           GL11.glPushMatrix();
/* 121:132 */           GL11.glTranslatef(var2 + var8.centerX / 2.0F + 64.0F, var3 + var8.centerZ / 2.0F + 64.0F, -0.02F);
/* 122:133 */           GL11.glRotatef(var8.iconRotation * 360 / 16.0F, 0.0F, 0.0F, 1.0F);
/* 123:134 */           GL11.glScalef(4.0F, 4.0F, 3.0F);
/* 124:135 */           GL11.glTranslatef(-0.125F, 0.125F, 0.0F);
/* 125:136 */           float var9 = (var8.iconSize % 4 + 0) / 4.0F;
/* 126:137 */           float var10 = (var8.iconSize / 4 + 0) / 4.0F;
/* 127:138 */           float var11 = (var8.iconSize % 4 + 1) / 4.0F;
/* 128:139 */           float var12 = (var8.iconSize / 4 + 1) / 4.0F;
/* 129:140 */           var4.startDrawingQuads();
/* 130:141 */           var4.addVertexWithUV(-1.0D, 1.0D, var6 * 0.001F, var9, var10);
/* 131:142 */           var4.addVertexWithUV(1.0D, 1.0D, var6 * 0.001F, var11, var10);
/* 132:143 */           var4.addVertexWithUV(1.0D, -1.0D, var6 * 0.001F, var11, var12);
/* 133:144 */           var4.addVertexWithUV(-1.0D, -1.0D, var6 * 0.001F, var9, var12);
/* 134:145 */           var4.draw();
/* 135:146 */           GL11.glPopMatrix();
/* 136:147 */           var6++;
/* 137:    */         }
/* 138:    */       }
/* 139:151 */       GL11.glPushMatrix();
/* 140:152 */       GL11.glTranslatef(0.0F, 0.0F, -0.04F);
/* 141:153 */       GL11.glScalef(1.0F, 1.0F, 1.0F);
/* 142:154 */       GL11.glPopMatrix();
/* 143:    */     }
/* 144:    */     
/* 145:    */     Instance(MapData p_i45008_2_, Object p_i45008_3_)
/* 146:    */     {
/* 147:159 */       this(p_i45008_2_);
/* 148:    */     }
/* 149:    */   }
/* 150:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.MapItemRenderer
 * JD-Core Version:    0.7.0.1
 */