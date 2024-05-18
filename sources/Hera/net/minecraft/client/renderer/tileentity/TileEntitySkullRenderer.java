/*     */ package net.minecraft.client.renderer.tileentity;
/*     */ 
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.authlib.minecraft.MinecraftProfileTexture;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.model.ModelHumanoidHead;
/*     */ import net.minecraft.client.model.ModelSkeletonHead;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.resources.DefaultPlayerSkin;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntitySkull;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ 
/*     */ public class TileEntitySkullRenderer
/*     */   extends TileEntitySpecialRenderer<TileEntitySkull>
/*     */ {
/*  22 */   private static final ResourceLocation SKELETON_TEXTURES = new ResourceLocation("textures/entity/skeleton/skeleton.png");
/*  23 */   private static final ResourceLocation WITHER_SKELETON_TEXTURES = new ResourceLocation("textures/entity/skeleton/wither_skeleton.png");
/*  24 */   private static final ResourceLocation ZOMBIE_TEXTURES = new ResourceLocation("textures/entity/zombie/zombie.png");
/*  25 */   private static final ResourceLocation CREEPER_TEXTURES = new ResourceLocation("textures/entity/creeper/creeper.png");
/*     */   public static TileEntitySkullRenderer instance;
/*  27 */   private final ModelSkeletonHead skeletonHead = new ModelSkeletonHead(0, 0, 64, 32);
/*  28 */   private final ModelSkeletonHead humanoidHead = (ModelSkeletonHead)new ModelHumanoidHead();
/*     */ 
/*     */   
/*     */   public void renderTileEntityAt(TileEntitySkull te, double x, double y, double z, float partialTicks, int destroyStage) {
/*  32 */     EnumFacing enumfacing = EnumFacing.getFront(te.getBlockMetadata() & 0x7);
/*  33 */     renderSkull((float)x, (float)y, (float)z, enumfacing, (te.getSkullRotation() * 360) / 16.0F, te.getSkullType(), te.getPlayerProfile(), destroyStage);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRendererDispatcher(TileEntityRendererDispatcher rendererDispatcherIn) {
/*  38 */     super.setRendererDispatcher(rendererDispatcherIn);
/*  39 */     instance = this;
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderSkull(float p_180543_1_, float p_180543_2_, float p_180543_3_, EnumFacing p_180543_4_, float p_180543_5_, int p_180543_6_, GameProfile p_180543_7_, int p_180543_8_) {
/*  44 */     ModelSkeletonHead modelSkeletonHead = this.skeletonHead;
/*     */     
/*  46 */     if (p_180543_8_ >= 0) {
/*     */       
/*  48 */       bindTexture(DESTROY_STAGES[p_180543_8_]);
/*  49 */       GlStateManager.matrixMode(5890);
/*  50 */       GlStateManager.pushMatrix();
/*  51 */       GlStateManager.scale(4.0F, 2.0F, 1.0F);
/*  52 */       GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
/*  53 */       GlStateManager.matrixMode(5888);
/*     */     } else {
/*     */       ResourceLocation resourcelocation;
/*     */       
/*  57 */       switch (p_180543_6_) {
/*     */ 
/*     */         
/*     */         default:
/*  61 */           bindTexture(SKELETON_TEXTURES);
/*     */           break;
/*     */         
/*     */         case 1:
/*  65 */           bindTexture(WITHER_SKELETON_TEXTURES);
/*     */           break;
/*     */         
/*     */         case 2:
/*  69 */           bindTexture(ZOMBIE_TEXTURES);
/*  70 */           modelSkeletonHead = this.humanoidHead;
/*     */           break;
/*     */         
/*     */         case 3:
/*  74 */           modelSkeletonHead = this.humanoidHead;
/*  75 */           resourcelocation = DefaultPlayerSkin.getDefaultSkinLegacy();
/*     */           
/*  77 */           if (p_180543_7_ != null) {
/*     */             
/*  79 */             Minecraft minecraft = Minecraft.getMinecraft();
/*  80 */             Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> map = minecraft.getSkinManager().loadSkinFromCache(p_180543_7_);
/*     */             
/*  82 */             if (map.containsKey(MinecraftProfileTexture.Type.SKIN)) {
/*     */               
/*  84 */               resourcelocation = minecraft.getSkinManager().loadSkin(map.get(MinecraftProfileTexture.Type.SKIN), MinecraftProfileTexture.Type.SKIN);
/*     */             }
/*     */             else {
/*     */               
/*  88 */               UUID uuid = EntityPlayer.getUUID(p_180543_7_);
/*  89 */               resourcelocation = DefaultPlayerSkin.getDefaultSkin(uuid);
/*     */             } 
/*     */           } 
/*     */           
/*  93 */           bindTexture(resourcelocation);
/*     */           break;
/*     */         
/*     */         case 4:
/*  97 */           bindTexture(CREEPER_TEXTURES);
/*     */           break;
/*     */       } 
/*     */     } 
/* 101 */     GlStateManager.pushMatrix();
/* 102 */     GlStateManager.disableCull();
/*     */     
/* 104 */     if (p_180543_4_ != EnumFacing.UP) {
/*     */       
/* 106 */       switch (p_180543_4_) {
/*     */         
/*     */         case NORTH:
/* 109 */           GlStateManager.translate(p_180543_1_ + 0.5F, p_180543_2_ + 0.25F, p_180543_3_ + 0.74F);
/*     */           break;
/*     */         
/*     */         case SOUTH:
/* 113 */           GlStateManager.translate(p_180543_1_ + 0.5F, p_180543_2_ + 0.25F, p_180543_3_ + 0.26F);
/* 114 */           p_180543_5_ = 180.0F;
/*     */           break;
/*     */         
/*     */         case WEST:
/* 118 */           GlStateManager.translate(p_180543_1_ + 0.74F, p_180543_2_ + 0.25F, p_180543_3_ + 0.5F);
/* 119 */           p_180543_5_ = 270.0F;
/*     */           break;
/*     */ 
/*     */         
/*     */         default:
/* 124 */           GlStateManager.translate(p_180543_1_ + 0.26F, p_180543_2_ + 0.25F, p_180543_3_ + 0.5F);
/* 125 */           p_180543_5_ = 90.0F;
/*     */           break;
/*     */       } 
/*     */     
/*     */     } else {
/* 130 */       GlStateManager.translate(p_180543_1_ + 0.5F, p_180543_2_, p_180543_3_ + 0.5F);
/*     */     } 
/*     */     
/* 133 */     float f = 0.0625F;
/* 134 */     GlStateManager.enableRescaleNormal();
/* 135 */     GlStateManager.scale(-1.0F, -1.0F, 1.0F);
/* 136 */     GlStateManager.enableAlpha();
/* 137 */     modelSkeletonHead.render(null, 0.0F, 0.0F, 0.0F, p_180543_5_, 0.0F, f);
/* 138 */     GlStateManager.popMatrix();
/*     */     
/* 140 */     if (p_180543_8_ >= 0) {
/*     */       
/* 142 */       GlStateManager.matrixMode(5890);
/* 143 */       GlStateManager.popMatrix();
/* 144 */       GlStateManager.matrixMode(5888);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\renderer\tileentity\TileEntitySkullRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */