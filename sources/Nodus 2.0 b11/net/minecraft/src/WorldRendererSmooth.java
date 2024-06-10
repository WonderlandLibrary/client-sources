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
/*  25:    */ public class WorldRendererSmooth
/*  26:    */   extends WorldRenderer
/*  27:    */ {
/*  28: 26 */   private WrUpdateState updateState = new WrUpdateState();
/*  29: 27 */   public int activeSet = 0;
/*  30: 28 */   public int[] activeListIndex = new int[2];
/*  31: 29 */   public int[][][] glWorkLists = new int[2][2][16];
/*  32: 30 */   public boolean[] tempSkipRenderPass = new boolean[2];
/*  33:    */   public TesselatorVertexState tempVertexState;
/*  34:    */   
/*  35:    */   public WorldRendererSmooth(World par1World, List par2List, int par3, int par4, int par5, int par6, int rendererIndex)
/*  36:    */   {
/*  37: 35 */     super(par1World, par2List, par3, par4, par5, par6);
/*  38: 36 */     RenderGlobal renderGlobal = Minecraft.getMinecraft().renderGlobal;
/*  39: 37 */     int glWorkBase = renderGlobal.glExtendedListBase + rendererIndex * 2 * 2 * 16;
/*  40: 39 */     for (int set = 0; set < 2; set++)
/*  41:    */     {
/*  42: 41 */       int setBase = glWorkBase + set * 2 * 16;
/*  43: 43 */       for (int pass = 0; pass < 2; pass++)
/*  44:    */       {
/*  45: 45 */         int passBase = setBase + pass * 16;
/*  46: 47 */         for (int t = 0; t < 16; t++) {
/*  47: 49 */           this.glWorkLists[set][pass][t] = (passBase + t);
/*  48:    */         }
/*  49:    */       }
/*  50:    */     }
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void setPosition(int px, int py, int pz)
/*  54:    */   {
/*  55: 60 */     if (this.isUpdating) {
/*  56: 62 */       WrUpdates.finishCurrentUpdate();
/*  57:    */     }
/*  58: 65 */     super.setPosition(px, py, pz);
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void updateRenderer()
/*  62:    */   {
/*  63: 70 */     if (this.worldObj != null)
/*  64:    */     {
/*  65: 72 */       updateRenderer(0L);
/*  66: 73 */       finishUpdate();
/*  67:    */     }
/*  68:    */   }
/*  69:    */   
/*  70:    */   public boolean updateRenderer(long finishTime)
/*  71:    */   {
/*  72: 79 */     if (this.worldObj == null) {
/*  73: 81 */       return true;
/*  74:    */     }
/*  75: 85 */     this.needsUpdate = false;
/*  76: 87 */     if (!this.isUpdating)
/*  77:    */     {
/*  78: 89 */       if (this.needsBoxUpdate)
/*  79:    */       {
/*  80: 91 */         GL11.glNewList(this.glRenderList + 2, 4864);
/*  81: 92 */         RenderItem.renderAABB(AxisAlignedBB.getAABBPool().getAABB(this.posXClip, this.posYClip, this.posZClip, this.posXClip + 16, this.posYClip + 16, this.posZClip + 16));
/*  82: 93 */         GL11.glEndList();
/*  83: 94 */         this.needsBoxUpdate = false;
/*  84:    */       }
/*  85: 97 */       if (Reflector.LightCache.exists())
/*  86:    */       {
/*  87: 99 */         Object xMin = Reflector.getFieldValue(Reflector.LightCache_cache);
/*  88:100 */         Reflector.callVoid(xMin, Reflector.LightCache_clear, new Object[0]);
/*  89:101 */         Reflector.callVoid(Reflector.BlockCoord_resetPool, new Object[0]);
/*  90:    */       }
/*  91:104 */       Chunk.isLit = false;
/*  92:    */     }
/*  93:107 */     int var27 = this.posX;
/*  94:108 */     int yMin = this.posY;
/*  95:109 */     int zMin = this.posZ;
/*  96:110 */     int xMax = this.posX + 16;
/*  97:111 */     int yMax = this.posY + 16;
/*  98:112 */     int zMax = this.posZ + 16;
/*  99:113 */     ChunkCache chunkcache = null;
/* 100:114 */     RenderBlocks renderblocks = null;
/* 101:115 */     HashSet setOldEntityRenders = null;
/* 102:116 */     int viewEntityPosX = 0;
/* 103:117 */     int viewEntityPosY = 0;
/* 104:118 */     int viewEntityPosZ = 0;
/* 105:120 */     if (!this.isUpdating)
/* 106:    */     {
/* 107:122 */       for (int setNewEntityRenderers = 0; setNewEntityRenderers < 2; setNewEntityRenderers++) {
/* 108:124 */         this.tempSkipRenderPass[setNewEntityRenderers] = true;
/* 109:    */       }
/* 110:127 */       this.tempVertexState = null;
/* 111:128 */       Minecraft var31 = Minecraft.getMinecraft();
/* 112:129 */       EntityLivingBase renderPass = var31.renderViewEntity;
/* 113:130 */       viewEntityPosX = MathHelper.floor_double(renderPass.posX);
/* 114:131 */       viewEntityPosY = MathHelper.floor_double(renderPass.posY);
/* 115:132 */       viewEntityPosZ = MathHelper.floor_double(renderPass.posZ);
/* 116:133 */       byte renderNextPass = 1;
/* 117:134 */       chunkcache = new ChunkCache(this.worldObj, var27 - renderNextPass, yMin - renderNextPass, zMin - renderNextPass, xMax + renderNextPass, yMax + renderNextPass, zMax + renderNextPass, renderNextPass);
/* 118:135 */       renderblocks = new RenderBlocks(chunkcache);
/* 119:136 */       setOldEntityRenders = new HashSet();
/* 120:137 */       setOldEntityRenders.addAll(this.tileEntityRenderers);
/* 121:138 */       this.tileEntityRenderers.clear();
/* 122:    */     }
/* 123:141 */     if ((this.isUpdating) || (!chunkcache.extendedLevelsInChunkCache()))
/* 124:    */     {
/* 125:143 */       this.bytesDrawn = 0;
/* 126:144 */       this.tessellator = Tessellator.instance;
/* 127:145 */       boolean var30 = Reflector.ForgeHooksClient.exists();
/* 128:147 */       for (int var28 = 0; var28 < 2; var28++)
/* 129:    */       {
/* 130:149 */         boolean var32 = false;
/* 131:150 */         boolean hasRenderedBlocks = false;
/* 132:151 */         boolean hasGlList = false;
/* 133:153 */         for (int y = yMin; y < yMax; y++)
/* 134:    */         {
/* 135:155 */           if (this.isUpdating)
/* 136:    */           {
/* 137:157 */             this.isUpdating = false;
/* 138:158 */             chunkcache = this.updateState.chunkcache;
/* 139:159 */             renderblocks = this.updateState.renderblocks;
/* 140:160 */             setOldEntityRenders = this.updateState.setOldEntityRenders;
/* 141:161 */             viewEntityPosX = this.updateState.viewEntityPosX;
/* 142:162 */             viewEntityPosY = this.updateState.viewEntityPosY;
/* 143:163 */             viewEntityPosZ = this.updateState.viewEntityPosZ;
/* 144:164 */             var28 = this.updateState.renderPass;
/* 145:165 */             y = this.updateState.y;
/* 146:166 */             var32 = this.updateState.flag;
/* 147:167 */             hasRenderedBlocks = this.updateState.hasRenderedBlocks;
/* 148:168 */             hasGlList = this.updateState.hasGlList;
/* 149:170 */             if (hasGlList) {
/* 150:172 */               preRenderBlocksSmooth(var28);
/* 151:    */             }
/* 152:    */           }
/* 153:175 */           else if ((hasGlList) && (finishTime != 0L) && (System.nanoTime() - finishTime > 0L) && (this.activeListIndex[var28] < 15))
/* 154:    */           {
/* 155:177 */             if (hasRenderedBlocks) {
/* 156:179 */               this.tempSkipRenderPass[var28] = false;
/* 157:    */             }
/* 158:182 */             postRenderBlocksSmooth(var28, this.renderGlobal.renderViewEntity, false);
/* 159:183 */             this.activeListIndex[var28] += 1;
/* 160:184 */             this.updateState.chunkcache = chunkcache;
/* 161:185 */             this.updateState.renderblocks = renderblocks;
/* 162:186 */             this.updateState.setOldEntityRenders = setOldEntityRenders;
/* 163:187 */             this.updateState.viewEntityPosX = viewEntityPosX;
/* 164:188 */             this.updateState.viewEntityPosY = viewEntityPosY;
/* 165:189 */             this.updateState.viewEntityPosZ = viewEntityPosZ;
/* 166:190 */             this.updateState.renderPass = var28;
/* 167:191 */             this.updateState.y = y;
/* 168:192 */             this.updateState.flag = var32;
/* 169:193 */             this.updateState.hasRenderedBlocks = hasRenderedBlocks;
/* 170:194 */             this.updateState.hasGlList = hasGlList;
/* 171:195 */             this.isUpdating = true;
/* 172:196 */             return false;
/* 173:    */           }
/* 174:199 */           for (int z = zMin; z < zMax; z++) {
/* 175:201 */             for (int x = var27; x < xMax; x++)
/* 176:    */             {
/* 177:203 */               Block block = chunkcache.getBlock(x, y, z);
/* 178:205 */               if (block.getMaterial() != Material.air)
/* 179:    */               {
/* 180:207 */                 if (!hasGlList)
/* 181:    */                 {
/* 182:209 */                   hasGlList = true;
/* 183:210 */                   preRenderBlocksSmooth(var28);
/* 184:    */                 }
/* 185:213 */                 boolean hasTileEntity = false;
/* 186:215 */                 if (var30) {
/* 187:217 */                   hasTileEntity = Reflector.callBoolean(block, Reflector.ForgeBlock_hasTileEntity, new Object[] { Integer.valueOf(chunkcache.getBlockMetadata(x, y, z)) });
/* 188:    */                 } else {
/* 189:221 */                   hasTileEntity = block.hasTileEntity();
/* 190:    */                 }
/* 191:224 */                 if ((var28 == 0) && (hasTileEntity))
/* 192:    */                 {
/* 193:226 */                   TileEntity blockPass = chunkcache.getTileEntity(x, y, z);
/* 194:228 */                   if (TileEntityRendererDispatcher.instance.hasSpecialRenderer(blockPass)) {
/* 195:230 */                     this.tileEntityRenderers.add(blockPass);
/* 196:    */                   }
/* 197:    */                 }
/* 198:234 */                 int var33 = block.getRenderBlockPass();
/* 199:236 */                 if (var33 > var28) {
/* 200:238 */                   var32 = true;
/* 201:    */                 }
/* 202:241 */                 boolean canRender = var33 == var28;
/* 203:243 */                 if (Reflector.ForgeBlock_canRenderInPass.exists()) {
/* 204:245 */                   canRender = Reflector.callBoolean(block, Reflector.ForgeBlock_canRenderInPass, new Object[] { Integer.valueOf(var28) });
/* 205:    */                 }
/* 206:248 */                 if (canRender)
/* 207:    */                 {
/* 208:250 */                   hasRenderedBlocks |= renderblocks.renderBlockByRenderType(block, x, y, z);
/* 209:252 */                   if ((block.getRenderType() == 0) && (x == viewEntityPosX) && (y == viewEntityPosY) && (z == viewEntityPosZ))
/* 210:    */                   {
/* 211:254 */                     renderblocks.setRenderFromInside(true);
/* 212:255 */                     renderblocks.setRenderAllFaces(true);
/* 213:256 */                     renderblocks.renderBlockByRenderType(block, x, y, z);
/* 214:257 */                     renderblocks.setRenderFromInside(false);
/* 215:258 */                     renderblocks.setRenderAllFaces(false);
/* 216:    */                   }
/* 217:    */                 }
/* 218:    */               }
/* 219:    */             }
/* 220:    */           }
/* 221:    */         }
/* 222:266 */         if (hasRenderedBlocks) {
/* 223:268 */           this.tempSkipRenderPass[var28] = false;
/* 224:    */         }
/* 225:271 */         if (hasGlList) {
/* 226:273 */           postRenderBlocksSmooth(var28, this.renderGlobal.renderViewEntity, true);
/* 227:    */         } else {
/* 228:277 */           hasRenderedBlocks = false;
/* 229:    */         }
/* 230:280 */         if (!var32) {
/* 231:    */           break;
/* 232:    */         }
/* 233:    */       }
/* 234:    */     }
/* 235:287 */     HashSet var29 = new HashSet();
/* 236:288 */     var29.addAll(this.tileEntityRenderers);
/* 237:289 */     var29.removeAll(setOldEntityRenders);
/* 238:290 */     this.tileEntities.addAll(var29);
/* 239:291 */     setOldEntityRenders.removeAll(this.tileEntityRenderers);
/* 240:292 */     this.tileEntities.removeAll(setOldEntityRenders);
/* 241:293 */     this.isChunkLit = Chunk.isLit;
/* 242:294 */     this.isInitialized = true;
/* 243:295 */     chunksUpdated += 1;
/* 244:296 */     this.isVisible = true;
/* 245:297 */     this.isVisibleFromPosition = false;
/* 246:298 */     this.skipRenderPass[0] = this.tempSkipRenderPass[0];
/* 247:299 */     this.skipRenderPass[1] = this.tempSkipRenderPass[1];
/* 248:300 */     this.skipAllRenderPasses = ((this.skipRenderPass[0] != 0) && (this.skipRenderPass[1] != 0));
/* 249:301 */     this.vertexState = this.tempVertexState;
/* 250:302 */     this.isUpdating = false;
/* 251:303 */     updateFinished();
/* 252:304 */     return true;
/* 253:    */   }
/* 254:    */   
/* 255:    */   protected void preRenderBlocksSmooth(int renderpass)
/* 256:    */   {
/* 257:310 */     GL11.glNewList(this.glWorkLists[this.activeSet][renderpass][this.activeListIndex[renderpass]], 4864);
/* 258:311 */     this.tessellator.setRenderingChunk(true);
/* 259:313 */     if (Config.isFastRender())
/* 260:    */     {
/* 261:315 */       this.tessellator.startDrawingQuads();
/* 262:316 */       this.tessellator.setTranslation(-globalChunkOffsetX, 0.0D, -globalChunkOffsetZ);
/* 263:    */     }
/* 264:    */     else
/* 265:    */     {
/* 266:320 */       GL11.glPushMatrix();
/* 267:321 */       setupGLTranslation();
/* 268:322 */       float var2 = 1.000001F;
/* 269:323 */       GL11.glTranslatef(-8.0F, -8.0F, -8.0F);
/* 270:324 */       GL11.glScalef(var2, var2, var2);
/* 271:325 */       GL11.glTranslatef(8.0F, 8.0F, 8.0F);
/* 272:326 */       this.tessellator.startDrawingQuads();
/* 273:327 */       this.tessellator.setTranslation(-this.posX, -this.posY, -this.posZ);
/* 274:    */     }
/* 275:    */   }
/* 276:    */   
/* 277:    */   protected void postRenderBlocksSmooth(int renderpass, EntityLivingBase entityLiving, boolean updateFinished)
/* 278:    */   {
/* 279:333 */     if ((Config.isTranslucentBlocksFancy()) && (renderpass == 1) && (this.tempSkipRenderPass[renderpass] == 0))
/* 280:    */     {
/* 281:335 */       TesselatorVertexState tsv = this.tessellator.getVertexState((float)entityLiving.posX, (float)entityLiving.posY, (float)entityLiving.posZ);
/* 282:337 */       if (this.tempVertexState == null) {
/* 283:339 */         this.tempVertexState = tsv;
/* 284:    */       } else {
/* 285:343 */         this.tempVertexState.addTessellatorVertexState(tsv);
/* 286:    */       }
/* 287:    */     }
/* 288:347 */     this.bytesDrawn += this.tessellator.draw();
/* 289:348 */     this.tessellator.setRenderingChunk(false);
/* 290:350 */     if (!Config.isFastRender()) {
/* 291:352 */       GL11.glPopMatrix();
/* 292:    */     }
/* 293:355 */     GL11.glEndList();
/* 294:356 */     this.tessellator.setTranslation(0.0D, 0.0D, 0.0D);
/* 295:    */   }
/* 296:    */   
/* 297:    */   public void finishUpdate()
/* 298:    */   {
/* 299:365 */     for (int pass = 0; pass < 2; pass++) {
/* 300:367 */       if (this.skipRenderPass[pass] == 0)
/* 301:    */       {
/* 302:369 */         GL11.glNewList(this.glRenderList + pass, 4864);
/* 303:371 */         for (int i = 0; i <= this.activeListIndex[pass]; i++)
/* 304:    */         {
/* 305:373 */           int list = this.glWorkLists[this.activeSet][pass][i];
/* 306:374 */           GL11.glCallList(list);
/* 307:    */         }
/* 308:377 */         GL11.glEndList();
/* 309:    */       }
/* 310:    */     }
/* 311:381 */     if (this.activeSet == 0) {
/* 312:383 */       this.activeSet = 1;
/* 313:    */     } else {
/* 314:387 */       this.activeSet = 0;
/* 315:    */     }
/* 316:390 */     for (pass = 0; pass < 2; pass++) {
/* 317:392 */       if (this.skipRenderPass[pass] == 0) {
/* 318:394 */         for (int i = 0; i <= this.activeListIndex[pass]; i++)
/* 319:    */         {
/* 320:396 */           int list = this.glWorkLists[this.activeSet][pass][i];
/* 321:397 */           GL11.glNewList(list, 4864);
/* 322:398 */           GL11.glEndList();
/* 323:    */         }
/* 324:    */       }
/* 325:    */     }
/* 326:403 */     for (pass = 0; pass < 2; pass++) {
/* 327:405 */       this.activeListIndex[pass] = 0;
/* 328:    */     }
/* 329:    */   }
/* 330:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.src.WorldRendererSmooth
 * JD-Core Version:    0.7.0.1
 */