/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Map;
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.texture.DynamicTexture;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.Vec4b;
/*     */ import net.minecraft.world.storage.MapData;
/*     */ 
/*     */ public class MapItemRenderer
/*     */ {
/*  18 */   private static final ResourceLocation mapIcons = new ResourceLocation("textures/map/map_icons.png");
/*     */   private final TextureManager textureManager;
/*  20 */   private final Map<String, Instance> loadedMaps = Maps.newHashMap();
/*     */ 
/*     */   
/*     */   public MapItemRenderer(TextureManager textureManagerIn) {
/*  24 */     this.textureManager = textureManagerIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateMapTexture(MapData mapdataIn) {
/*  32 */     getMapRendererInstance(mapdataIn).updateMapTexture();
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderMap(MapData mapdataIn, boolean p_148250_2_) {
/*  37 */     getMapRendererInstance(mapdataIn).render(p_148250_2_);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Instance getMapRendererInstance(MapData mapdataIn) {
/*  45 */     Instance mapitemrenderer$instance = this.loadedMaps.get(mapdataIn.mapName);
/*     */     
/*  47 */     if (mapitemrenderer$instance == null) {
/*     */       
/*  49 */       mapitemrenderer$instance = new Instance(mapdataIn, null);
/*  50 */       this.loadedMaps.put(mapdataIn.mapName, mapitemrenderer$instance);
/*     */     } 
/*     */     
/*  53 */     return mapitemrenderer$instance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearLoadedMaps() {
/*  61 */     for (Instance mapitemrenderer$instance : this.loadedMaps.values())
/*     */     {
/*  63 */       this.textureManager.deleteTexture(mapitemrenderer$instance.location);
/*     */     }
/*     */     
/*  66 */     this.loadedMaps.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   class Instance
/*     */   {
/*     */     private final MapData mapData;
/*     */     private final DynamicTexture mapTexture;
/*     */     private final ResourceLocation location;
/*     */     private final int[] mapTextureData;
/*     */     
/*     */     private Instance(MapData mapdataIn) {
/*  78 */       this.mapData = mapdataIn;
/*  79 */       this.mapTexture = new DynamicTexture(128, 128);
/*  80 */       this.mapTextureData = this.mapTexture.getTextureData();
/*  81 */       this.location = MapItemRenderer.this.textureManager.getDynamicTextureLocation("map/" + mapdataIn.mapName, this.mapTexture);
/*     */       
/*  83 */       for (int i = 0; i < this.mapTextureData.length; i++)
/*     */       {
/*  85 */         this.mapTextureData[i] = 0;
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     private void updateMapTexture() {
/*  91 */       for (int i = 0; i < 16384; i++) {
/*     */         
/*  93 */         int j = this.mapData.colors[i] & 0xFF;
/*     */         
/*  95 */         if (j / 4 == 0) {
/*     */           
/*  97 */           this.mapTextureData[i] = (i + i / 128 & 0x1) * 8 + 16 << 24;
/*     */         }
/*     */         else {
/*     */           
/* 101 */           this.mapTextureData[i] = MapColor.mapColorArray[j / 4].func_151643_b(j & 0x3);
/*     */         } 
/*     */       } 
/*     */       
/* 105 */       this.mapTexture.updateDynamicTexture();
/*     */     }
/*     */ 
/*     */     
/*     */     private void render(boolean noOverlayRendering) {
/* 110 */       int i = 0;
/* 111 */       int j = 0;
/* 112 */       Tessellator tessellator = Tessellator.getInstance();
/* 113 */       WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 114 */       float f = 0.0F;
/* 115 */       MapItemRenderer.this.textureManager.bindTexture(this.location);
/* 116 */       GlStateManager.enableBlend();
/* 117 */       GlStateManager.tryBlendFuncSeparate(1, 771, 0, 1);
/* 118 */       GlStateManager.disableAlpha();
/* 119 */       worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 120 */       worldrenderer.pos(((i + 0) + f), ((j + 128) - f), -0.009999999776482582D).tex(0.0D, 1.0D).endVertex();
/* 121 */       worldrenderer.pos(((i + 128) - f), ((j + 128) - f), -0.009999999776482582D).tex(1.0D, 1.0D).endVertex();
/* 122 */       worldrenderer.pos(((i + 128) - f), ((j + 0) + f), -0.009999999776482582D).tex(1.0D, 0.0D).endVertex();
/* 123 */       worldrenderer.pos(((i + 0) + f), ((j + 0) + f), -0.009999999776482582D).tex(0.0D, 0.0D).endVertex();
/* 124 */       tessellator.draw();
/* 125 */       GlStateManager.enableAlpha();
/* 126 */       GlStateManager.disableBlend();
/* 127 */       MapItemRenderer.this.textureManager.bindTexture(MapItemRenderer.mapIcons);
/* 128 */       int k = 0;
/*     */       
/* 130 */       for (Vec4b vec4b : this.mapData.mapDecorations.values()) {
/*     */         
/* 132 */         if (!noOverlayRendering || vec4b.func_176110_a() == 1) {
/*     */           
/* 134 */           GlStateManager.pushMatrix();
/* 135 */           GlStateManager.translate(i + vec4b.func_176112_b() / 2.0F + 64.0F, j + vec4b.func_176113_c() / 2.0F + 64.0F, -0.02F);
/* 136 */           GlStateManager.rotate((vec4b.func_176111_d() * 360) / 16.0F, 0.0F, 0.0F, 1.0F);
/* 137 */           GlStateManager.scale(4.0F, 4.0F, 3.0F);
/* 138 */           GlStateManager.translate(-0.125F, 0.125F, 0.0F);
/* 139 */           byte b0 = vec4b.func_176110_a();
/* 140 */           float f1 = (b0 % 4 + 0) / 4.0F;
/* 141 */           float f2 = (b0 / 4 + 0) / 4.0F;
/* 142 */           float f3 = (b0 % 4 + 1) / 4.0F;
/* 143 */           float f4 = (b0 / 4 + 1) / 4.0F;
/* 144 */           worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 145 */           float f5 = -0.001F;
/* 146 */           worldrenderer.pos(-1.0D, 1.0D, (k * -0.001F)).tex(f1, f2).endVertex();
/* 147 */           worldrenderer.pos(1.0D, 1.0D, (k * -0.001F)).tex(f3, f2).endVertex();
/* 148 */           worldrenderer.pos(1.0D, -1.0D, (k * -0.001F)).tex(f3, f4).endVertex();
/* 149 */           worldrenderer.pos(-1.0D, -1.0D, (k * -0.001F)).tex(f1, f4).endVertex();
/* 150 */           tessellator.draw();
/* 151 */           GlStateManager.popMatrix();
/* 152 */           k++;
/*     */         } 
/*     */       } 
/*     */       
/* 156 */       GlStateManager.pushMatrix();
/* 157 */       GlStateManager.translate(0.0F, 0.0F, -0.04F);
/* 158 */       GlStateManager.scale(1.0F, 1.0F, 1.0F);
/* 159 */       GlStateManager.popMatrix();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\gui\MapItemRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */