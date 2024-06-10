/*   1:    */ package net.minecraft.src;
/*   2:    */ 
/*   3:    */ import java.util.HashSet;
/*   4:    */ import java.util.List;
/*   5:    */ import net.minecraft.block.Block;
/*   6:    */ import net.minecraft.block.material.Material;
/*   7:    */ import net.minecraft.client.Minecraft;
/*   8:    */ import net.minecraft.client.renderer.RenderBlocks;
/*   9:    */ import net.minecraft.client.renderer.RenderGlobal;
/*  10:    */ import net.minecraft.client.renderer.Tessellator;
/*  11:    */ import net.minecraft.client.renderer.WorldRenderer;
/*  12:    */ import net.minecraft.client.renderer.entity.RenderItem;
/*  13:    */ import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
/*  14:    */ import net.minecraft.client.shader.TesselatorVertexState;
/*  15:    */ import net.minecraft.entity.EntityLivingBase;
/*  16:    */ import net.minecraft.tileentity.TileEntity;
/*  17:    */ import net.minecraft.util.AABBPool;
/*  18:    */ import net.minecraft.util.AxisAlignedBB;
/*  19:    */ import net.minecraft.util.MathHelper;
/*  20:    */ import net.minecraft.world.ChunkCache;
/*  21:    */ import net.minecraft.world.World;
/*  22:    */ import net.minecraft.world.chunk.Chunk;
/*  23:    */ import org.lwjgl.opengl.GL11;
/*  24:    */ 
/*  25:    */ public class WorldRendererThreaded
/*  26:    */   extends WorldRenderer
/*  27:    */ {
/*  28:    */   private int glRenderListWork;
/*  29:    */   private int glRenderListBoundingBox;
/*  30: 28 */   public boolean[] tempSkipRenderPass = new boolean[2];
/*  31:    */   public TesselatorVertexState tempVertexState;
/*  32: 30 */   private Tessellator tessellatorWork = null;
/*  33:    */   
/*  34:    */   public WorldRendererThreaded(World par1World, List par2List, int par3, int par4, int par5, int par6, int rendererIndex)
/*  35:    */   {
/*  36: 34 */     super(par1World, par2List, par3, par4, par5, par6);
/*  37: 35 */     RenderGlobal renderGlobal = Minecraft.getMinecraft().renderGlobal;
/*  38: 36 */     this.glRenderListWork = (renderGlobal.glExtendedListBase + rendererIndex * 2);
/*  39: 37 */     this.glRenderListBoundingBox = (this.glRenderList + 2);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void setPosition(int px, int py, int pz)
/*  43:    */   {
/*  44: 45 */     if (this.isUpdating) {
/*  45: 47 */       WrUpdates.finishCurrentUpdate();
/*  46:    */     }
/*  47: 50 */     super.setPosition(px, py, pz);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void updateRenderer()
/*  51:    */   {
/*  52: 55 */     if (this.worldObj != null)
/*  53:    */     {
/*  54: 57 */       updateRenderer(null);
/*  55: 58 */       finishUpdate();
/*  56:    */     }
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void updateRenderer(IWrUpdateListener updateListener)
/*  60:    */   {
/*  61: 64 */     if (this.worldObj != null)
/*  62:    */     {
/*  63: 66 */       this.needsUpdate = false;
/*  64: 67 */       int xMin = this.posX;
/*  65: 68 */       int yMin = this.posY;
/*  66: 69 */       int zMin = this.posZ;
/*  67: 70 */       int xMax = this.posX + 16;
/*  68: 71 */       int yMax = this.posY + 16;
/*  69: 72 */       int zMax = this.posZ + 16;
/*  70: 74 */       for (int hashset = 0; hashset < this.tempSkipRenderPass.length; hashset++) {
/*  71: 76 */         this.tempSkipRenderPass[hashset] = true;
/*  72:    */       }
/*  73: 79 */       Chunk.isLit = false;
/*  74: 80 */       HashSet var30 = new HashSet();
/*  75: 81 */       var30.addAll(this.tileEntityRenderers);
/*  76: 82 */       this.tileEntityRenderers.clear();
/*  77: 83 */       Minecraft var9 = Minecraft.getMinecraft();
/*  78: 84 */       EntityLivingBase var10 = var9.renderViewEntity;
/*  79: 85 */       int viewEntityPosX = MathHelper.floor_double(var10.posX);
/*  80: 86 */       int viewEntityPosY = MathHelper.floor_double(var10.posY);
/*  81: 87 */       int viewEntityPosZ = MathHelper.floor_double(var10.posZ);
/*  82: 88 */       byte one = 1;
/*  83: 89 */       ChunkCache chunkcache = new ChunkCache(this.worldObj, xMin - one, yMin - one, zMin - one, xMax + one, yMax + one, zMax + one, one);
/*  84: 91 */       if (!chunkcache.extendedLevelsInChunkCache())
/*  85:    */       {
/*  86: 93 */         chunksUpdated += 1;
/*  87: 94 */         RenderBlocks hashset1 = new RenderBlocks(chunkcache);
/*  88: 95 */         this.bytesDrawn = 0;
/*  89: 96 */         this.tempVertexState = null;
/*  90: 97 */         this.tessellator = Tessellator.instance;
/*  91: 98 */         boolean hasForge = Reflector.ForgeHooksClient.exists();
/*  92: 99 */         WrUpdateControl uc = new WrUpdateControl();
/*  93:101 */         for (int renderPass = 0; renderPass < 2; renderPass++)
/*  94:    */         {
/*  95:103 */           uc.setRenderPass(renderPass);
/*  96:104 */           boolean renderNextPass = false;
/*  97:105 */           boolean hasRenderedBlocks = false;
/*  98:106 */           boolean hasGlList = false;
/*  99:108 */           for (int y = yMin; y < yMax; y++)
/* 100:    */           {
/* 101:110 */             if ((hasRenderedBlocks) && (updateListener != null))
/* 102:    */             {
/* 103:112 */               updateListener.updating(uc);
/* 104:113 */               this.tessellator = Tessellator.instance;
/* 105:    */             }
/* 106:116 */             for (int z = zMin; z < zMax; z++) {
/* 107:118 */               for (int x = xMin; x < xMax; x++)
/* 108:    */               {
/* 109:120 */                 Block block = chunkcache.getBlock(x, y, z);
/* 110:122 */                 if (block.getMaterial() != Material.air)
/* 111:    */                 {
/* 112:124 */                   if (!hasGlList)
/* 113:    */                   {
/* 114:126 */                     hasGlList = true;
/* 115:127 */                     preRenderBlocksThreaded(renderPass);
/* 116:    */                   }
/* 117:130 */                   boolean hasTileEntity = false;
/* 118:132 */                   if (hasForge) {
/* 119:134 */                     hasTileEntity = Reflector.callBoolean(block, Reflector.ForgeBlock_hasTileEntity, new Object[] { Integer.valueOf(chunkcache.getBlockMetadata(x, y, z)) });
/* 120:    */                   } else {
/* 121:138 */                     hasTileEntity = block.hasTileEntity();
/* 122:    */                   }
/* 123:141 */                   if ((renderPass == 0) && (hasTileEntity))
/* 124:    */                   {
/* 125:143 */                     TileEntity blockPass = chunkcache.getTileEntity(x, y, z);
/* 126:145 */                     if (TileEntityRendererDispatcher.instance.hasSpecialRenderer(blockPass)) {
/* 127:147 */                       this.tileEntityRenderers.add(blockPass);
/* 128:    */                     }
/* 129:    */                   }
/* 130:151 */                   int var32 = block.getRenderBlockPass();
/* 131:153 */                   if (var32 > renderPass) {
/* 132:155 */                     renderNextPass = true;
/* 133:    */                   }
/* 134:158 */                   boolean canRender = var32 == renderPass;
/* 135:160 */                   if (Reflector.ForgeBlock_canRenderInPass.exists()) {
/* 136:162 */                     canRender = Reflector.callBoolean(block, Reflector.ForgeBlock_canRenderInPass, new Object[] { Integer.valueOf(renderPass) });
/* 137:    */                   }
/* 138:165 */                   if (canRender)
/* 139:    */                   {
/* 140:167 */                     hasRenderedBlocks |= hashset1.renderBlockByRenderType(block, x, y, z);
/* 141:169 */                     if ((block.getRenderType() == 0) && (x == viewEntityPosX) && (y == viewEntityPosY) && (z == viewEntityPosZ))
/* 142:    */                     {
/* 143:171 */                       hashset1.setRenderFromInside(true);
/* 144:172 */                       hashset1.setRenderAllFaces(true);
/* 145:173 */                       hashset1.renderBlockByRenderType(block, x, y, z);
/* 146:174 */                       hashset1.setRenderFromInside(false);
/* 147:175 */                       hashset1.setRenderAllFaces(false);
/* 148:    */                     }
/* 149:    */                   }
/* 150:    */                 }
/* 151:    */               }
/* 152:    */             }
/* 153:    */           }
/* 154:183 */           if (hasRenderedBlocks) {
/* 155:185 */             this.tempSkipRenderPass[renderPass] = false;
/* 156:    */           }
/* 157:188 */           if (hasGlList)
/* 158:    */           {
/* 159:190 */             if (updateListener != null) {
/* 160:192 */               updateListener.updating(uc);
/* 161:    */             }
/* 162:195 */             this.tessellator = Tessellator.instance;
/* 163:196 */             postRenderBlocksThreaded(renderPass, this.renderGlobal.renderViewEntity);
/* 164:    */           }
/* 165:    */           else
/* 166:    */           {
/* 167:200 */             hasRenderedBlocks = false;
/* 168:    */           }
/* 169:203 */           if (!renderNextPass) {
/* 170:    */             break;
/* 171:    */           }
/* 172:    */         }
/* 173:    */       }
/* 174:210 */       HashSet var31 = new HashSet();
/* 175:211 */       var31.addAll(this.tileEntityRenderers);
/* 176:212 */       var31.removeAll(var30);
/* 177:213 */       this.tileEntities.addAll(var31);
/* 178:214 */       var30.removeAll(this.tileEntityRenderers);
/* 179:215 */       this.tileEntities.removeAll(var30);
/* 180:216 */       this.isChunkLit = Chunk.isLit;
/* 181:217 */       this.isInitialized = true;
/* 182:    */     }
/* 183:    */   }
/* 184:    */   
/* 185:    */   protected void preRenderBlocksThreaded(int renderpass)
/* 186:    */   {
/* 187:223 */     GL11.glNewList(this.glRenderListWork + renderpass, 4864);
/* 188:224 */     this.tessellator.setRenderingChunk(true);
/* 189:226 */     if (Config.isFastRender())
/* 190:    */     {
/* 191:228 */       this.tessellator.startDrawingQuads();
/* 192:229 */       this.tessellator.setTranslation(-globalChunkOffsetX, 0.0D, -globalChunkOffsetZ);
/* 193:    */     }
/* 194:    */     else
/* 195:    */     {
/* 196:233 */       GL11.glPushMatrix();
/* 197:234 */       setupGLTranslation();
/* 198:235 */       float var2 = 1.000001F;
/* 199:236 */       GL11.glTranslatef(-8.0F, -8.0F, -8.0F);
/* 200:237 */       GL11.glScalef(var2, var2, var2);
/* 201:238 */       GL11.glTranslatef(8.0F, 8.0F, 8.0F);
/* 202:239 */       this.tessellator.startDrawingQuads();
/* 203:240 */       this.tessellator.setTranslation(-this.posX, -this.posY, -this.posZ);
/* 204:    */     }
/* 205:    */   }
/* 206:    */   
/* 207:    */   protected void postRenderBlocksThreaded(int renderpass, EntityLivingBase entityLiving)
/* 208:    */   {
/* 209:246 */     if ((Config.isTranslucentBlocksFancy()) && (renderpass == 1) && (this.tempSkipRenderPass[renderpass] == 0)) {
/* 210:248 */       this.tempVertexState = this.tessellator.getVertexState((float)entityLiving.posX, (float)entityLiving.posY, (float)entityLiving.posZ);
/* 211:    */     }
/* 212:251 */     this.bytesDrawn += this.tessellator.draw();
/* 213:252 */     this.tessellator.setRenderingChunk(false);
/* 214:254 */     if (!Config.isFastRender()) {
/* 215:256 */       GL11.glPopMatrix();
/* 216:    */     }
/* 217:259 */     GL11.glEndList();
/* 218:260 */     this.tessellator.setTranslation(0.0D, 0.0D, 0.0D);
/* 219:    */   }
/* 220:    */   
/* 221:    */   public void finishUpdate()
/* 222:    */   {
/* 223:265 */     int temp = this.glRenderList;
/* 224:266 */     this.glRenderList = this.glRenderListWork;
/* 225:267 */     this.glRenderListWork = temp;
/* 226:270 */     for (int lightCache = 0; lightCache < 2; lightCache++) {
/* 227:272 */       if (this.skipRenderPass[lightCache] == 0)
/* 228:    */       {
/* 229:274 */         GL11.glNewList(this.glRenderListWork + lightCache, 4864);
/* 230:275 */         GL11.glEndList();
/* 231:    */       }
/* 232:    */     }
/* 233:279 */     for (lightCache = 0; lightCache < 2; lightCache++) {
/* 234:281 */       this.skipRenderPass[lightCache] = this.tempSkipRenderPass[lightCache];
/* 235:    */     }
/* 236:284 */     this.skipAllRenderPasses = ((this.skipRenderPass[0] != 0) && (this.skipRenderPass[1] != 0));
/* 237:286 */     if ((this.needsBoxUpdate) && (!skipAllRenderPasses()))
/* 238:    */     {
/* 239:288 */       GL11.glNewList(this.glRenderListBoundingBox, 4864);
/* 240:289 */       RenderItem.renderAABB(AxisAlignedBB.getAABBPool().getAABB(this.posXClip, this.posYClip, this.posZClip, this.posXClip + 16, this.posYClip + 16, this.posZClip + 16));
/* 241:290 */       GL11.glEndList();
/* 242:291 */       this.needsBoxUpdate = false;
/* 243:    */     }
/* 244:294 */     this.vertexState = this.tempVertexState;
/* 245:295 */     this.isVisible = true;
/* 246:296 */     this.isVisibleFromPosition = false;
/* 247:298 */     if (Reflector.LightCache.exists())
/* 248:    */     {
/* 249:300 */       Object var3 = Reflector.getFieldValue(Reflector.LightCache_cache);
/* 250:301 */       Reflector.callVoid(var3, Reflector.LightCache_clear, new Object[0]);
/* 251:302 */       Reflector.callVoid(Reflector.BlockCoord_resetPool, new Object[0]);
/* 252:    */     }
/* 253:305 */     updateFinished();
/* 254:    */   }
/* 255:    */   
/* 256:    */   public void callOcclusionQueryList()
/* 257:    */   {
/* 258:313 */     GL11.glCallList(this.glRenderListBoundingBox);
/* 259:    */   }
/* 260:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.src.WorldRendererThreaded
 * JD-Core Version:    0.7.0.1
 */