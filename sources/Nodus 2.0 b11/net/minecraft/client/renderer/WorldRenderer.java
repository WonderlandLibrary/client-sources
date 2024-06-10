/*   1:    */ package net.minecraft.client.renderer;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.HashSet;
/*   5:    */ import java.util.List;
/*   6:    */ import net.minecraft.block.Block;
/*   7:    */ import net.minecraft.block.material.Material;
/*   8:    */ import net.minecraft.client.Minecraft;
/*   9:    */ import net.minecraft.client.renderer.culling.ICamera;
/*  10:    */ import net.minecraft.client.renderer.entity.RenderItem;
/*  11:    */ import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
/*  12:    */ import net.minecraft.client.shader.TesselatorVertexState;
/*  13:    */ import net.minecraft.entity.Entity;
/*  14:    */ import net.minecraft.entity.EntityLivingBase;
/*  15:    */ import net.minecraft.src.Config;
/*  16:    */ import net.minecraft.src.Reflector;
/*  17:    */ import net.minecraft.src.ReflectorClass;
/*  18:    */ import net.minecraft.src.ReflectorMethod;
/*  19:    */ import net.minecraft.tileentity.TileEntity;
/*  20:    */ import net.minecraft.util.AABBPool;
/*  21:    */ import net.minecraft.util.AxisAlignedBB;
/*  22:    */ import net.minecraft.util.MathHelper;
/*  23:    */ import net.minecraft.world.ChunkCache;
/*  24:    */ import net.minecraft.world.World;
/*  25:    */ import net.minecraft.world.chunk.Chunk;
/*  26:    */ import org.lwjgl.opengl.GL11;
/*  27:    */ 
/*  28:    */ public class WorldRenderer
/*  29:    */ {
/*  30:    */   protected TesselatorVertexState vertexState;
/*  31:    */   public World worldObj;
/*  32:    */   protected int glRenderList;
/*  33:    */   protected Tessellator tessellator;
/*  34:    */   public static volatile int chunksUpdated;
/*  35:    */   public int posX;
/*  36:    */   public int posY;
/*  37:    */   public int posZ;
/*  38:    */   public int posXMinus;
/*  39:    */   public int posYMinus;
/*  40:    */   public int posZMinus;
/*  41:    */   public int posXClip;
/*  42:    */   public int posYClip;
/*  43:    */   public int posZClip;
/*  44:    */   public boolean isInFrustum;
/*  45:    */   public boolean[] skipRenderPass;
/*  46:    */   public int posXPlus;
/*  47:    */   public int posYPlus;
/*  48:    */   public int posZPlus;
/*  49:    */   public volatile boolean needsUpdate;
/*  50:    */   public AxisAlignedBB rendererBoundingBox;
/*  51:    */   public int chunkIndex;
/*  52:    */   public boolean isVisible;
/*  53:    */   public boolean isWaitingOnOcclusionQuery;
/*  54:    */   public int glOcclusionQuery;
/*  55:    */   public boolean isChunkLit;
/*  56:    */   protected boolean isInitialized;
/*  57:    */   public List tileEntityRenderers;
/*  58:    */   protected List tileEntities;
/*  59:    */   protected int bytesDrawn;
/*  60:    */   private static final String __OBFID = "CL_00000942";
/*  61:    */   public boolean isVisibleFromPosition;
/*  62:    */   public double visibleFromX;
/*  63:    */   public double visibleFromY;
/*  64:    */   public double visibleFromZ;
/*  65:    */   public boolean isInFrustrumFully;
/*  66:    */   protected boolean needsBoxUpdate;
/*  67:    */   public volatile boolean isUpdating;
/*  68:    */   public float sortDistanceToEntitySquared;
/*  69:    */   protected boolean skipAllRenderPasses;
/*  70:    */   public boolean inSortedWorldRenderers;
/*  71:    */   public boolean needVertexStateResort;
/*  72:    */   public RenderGlobal renderGlobal;
/*  73:108 */   public static int globalChunkOffsetX = 0;
/*  74:109 */   public static int globalChunkOffsetZ = 0;
/*  75:    */   
/*  76:    */   public WorldRenderer(World par1World, List par2List, int par3, int par4, int par5, int par6)
/*  77:    */   {
/*  78:113 */     this.tessellator = Tessellator.instance;
/*  79:114 */     this.skipRenderPass = new boolean[] { true, true };
/*  80:115 */     this.tileEntityRenderers = new ArrayList();
/*  81:116 */     this.isVisibleFromPosition = false;
/*  82:117 */     this.isInFrustrumFully = false;
/*  83:118 */     this.needsBoxUpdate = false;
/*  84:119 */     this.isUpdating = false;
/*  85:120 */     this.skipAllRenderPasses = true;
/*  86:121 */     this.renderGlobal = Minecraft.getMinecraft().renderGlobal;
/*  87:122 */     this.glRenderList = -1;
/*  88:123 */     this.isInFrustum = false;
/*  89:124 */     this.isVisible = true;
/*  90:125 */     this.isInitialized = false;
/*  91:126 */     this.worldObj = par1World;
/*  92:127 */     this.vertexState = null;
/*  93:128 */     this.tileEntities = par2List;
/*  94:129 */     this.glRenderList = par6;
/*  95:130 */     this.posX = -999;
/*  96:131 */     setPosition(par3, par4, par5);
/*  97:132 */     this.needsUpdate = false;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void setPosition(int par1, int par2, int par3)
/* 101:    */   {
/* 102:140 */     if ((par1 != this.posX) || (par2 != this.posY) || (par3 != this.posZ))
/* 103:    */     {
/* 104:142 */       setDontDraw();
/* 105:143 */       this.posX = par1;
/* 106:144 */       this.posY = par2;
/* 107:145 */       this.posZ = par3;
/* 108:146 */       this.posXPlus = (par1 + 8);
/* 109:147 */       this.posYPlus = (par2 + 8);
/* 110:148 */       this.posZPlus = (par3 + 8);
/* 111:149 */       this.posXClip = (par1 & 0x3FF);
/* 112:150 */       this.posYClip = par2;
/* 113:151 */       this.posZClip = (par3 & 0x3FF);
/* 114:152 */       this.posXMinus = (par1 - this.posXClip);
/* 115:153 */       this.posYMinus = (par2 - this.posYClip);
/* 116:154 */       this.posZMinus = (par3 - this.posZClip);
/* 117:155 */       this.rendererBoundingBox = AxisAlignedBB.getBoundingBox(par1, par2, par3, par1 + 16, par2 + 16, par3 + 16);
/* 118:156 */       this.needsBoxUpdate = true;
/* 119:157 */       markDirty();
/* 120:158 */       this.isVisibleFromPosition = false;
/* 121:    */     }
/* 122:    */   }
/* 123:    */   
/* 124:    */   protected void setupGLTranslation()
/* 125:    */   {
/* 126:164 */     GL11.glTranslatef(this.posXClip, this.posYClip, this.posZClip);
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void updateRenderer(EntityLivingBase p_147892_1_)
/* 130:    */   {
/* 131:172 */     if (this.worldObj != null)
/* 132:    */     {
/* 133:174 */       if (this.needsBoxUpdate)
/* 134:    */       {
/* 135:176 */         GL11.glNewList(this.glRenderList + 2, 4864);
/* 136:177 */         RenderItem.renderAABB(AxisAlignedBB.getAABBPool().getAABB(this.posXClip, this.posYClip, this.posZClip, this.posXClip + 16, this.posYClip + 16, this.posZClip + 16));
/* 137:178 */         GL11.glEndList();
/* 138:179 */         this.needsBoxUpdate = false;
/* 139:    */       }
/* 140:182 */       this.isVisible = true;
/* 141:183 */       this.isVisibleFromPosition = false;
/* 142:185 */       if (this.needsUpdate)
/* 143:    */       {
/* 144:187 */         this.needsUpdate = false;
/* 145:188 */         int xMin = this.posX;
/* 146:189 */         int yMin = this.posY;
/* 147:190 */         int zMain = this.posZ;
/* 148:191 */         int xMax = this.posX + 16;
/* 149:192 */         int yMax = this.posY + 16;
/* 150:193 */         int zMax = this.posZ + 16;
/* 151:195 */         for (int var26 = 0; var26 < 2; var26++) {
/* 152:197 */           this.skipRenderPass[var26] = true;
/* 153:    */         }
/* 154:200 */         this.skipAllRenderPasses = true;
/* 155:202 */         if (Reflector.LightCache.exists())
/* 156:    */         {
/* 157:204 */           Object var29 = Reflector.getFieldValue(Reflector.LightCache_cache);
/* 158:205 */           Reflector.callVoid(var29, Reflector.LightCache_clear, new Object[0]);
/* 159:206 */           Reflector.callVoid(Reflector.BlockCoord_resetPool, new Object[0]);
/* 160:    */         }
/* 161:209 */         Chunk.isLit = false;
/* 162:210 */         HashSet var30 = new HashSet();
/* 163:211 */         var30.addAll(this.tileEntityRenderers);
/* 164:212 */         this.tileEntityRenderers.clear();
/* 165:213 */         Minecraft var9 = Minecraft.getMinecraft();
/* 166:214 */         EntityLivingBase var10 = var9.renderViewEntity;
/* 167:215 */         int viewEntityPosX = MathHelper.floor_double(var10.posX);
/* 168:216 */         int viewEntityPosY = MathHelper.floor_double(var10.posY);
/* 169:217 */         int viewEntityPosZ = MathHelper.floor_double(var10.posZ);
/* 170:218 */         byte var14 = 1;
/* 171:219 */         ChunkCache chunkcache = new ChunkCache(this.worldObj, xMin - var14, yMin - var14, zMain - var14, xMax + var14, yMax + var14, zMax + var14, var14);
/* 172:221 */         if (!chunkcache.extendedLevelsInChunkCache())
/* 173:    */         {
/* 174:223 */           chunksUpdated += 1;
/* 175:224 */           RenderBlocks var27 = new RenderBlocks(chunkcache);
/* 176:225 */           this.bytesDrawn = 0;
/* 177:226 */           this.vertexState = null;
/* 178:227 */           this.tessellator = Tessellator.instance;
/* 179:228 */           boolean hasForge = Reflector.ForgeHooksClient.exists();
/* 180:230 */           for (int renderPass = 0; renderPass < 2; renderPass++)
/* 181:    */           {
/* 182:232 */             boolean renderNextPass = false;
/* 183:233 */             boolean hasRenderedBlocks = false;
/* 184:234 */             boolean hasGlList = false;
/* 185:236 */             for (int y = yMin; y < yMax; y++) {
/* 186:238 */               for (int z = zMain; z < zMax; z++) {
/* 187:240 */                 for (int x = xMin; x < xMax; x++)
/* 188:    */                 {
/* 189:242 */                   Block block = chunkcache.getBlock(x, y, z);
/* 190:244 */                   if (block.getMaterial() != Material.air)
/* 191:    */                   {
/* 192:246 */                     if (!hasGlList)
/* 193:    */                     {
/* 194:248 */                       hasGlList = true;
/* 195:249 */                       preRenderBlocks(renderPass);
/* 196:    */                     }
/* 197:252 */                     boolean hasTileEntity = false;
/* 198:254 */                     if (hasForge) {
/* 199:256 */                       hasTileEntity = Reflector.callBoolean(block, Reflector.ForgeBlock_hasTileEntity, new Object[] { Integer.valueOf(chunkcache.getBlockMetadata(x, y, z)) });
/* 200:    */                     } else {
/* 201:260 */                       hasTileEntity = block.hasTileEntity();
/* 202:    */                     }
/* 203:263 */                     if ((renderPass == 0) && (hasTileEntity))
/* 204:    */                     {
/* 205:265 */                       TileEntity blockPass = chunkcache.getTileEntity(x, y, z);
/* 206:267 */                       if (TileEntityRendererDispatcher.instance.hasSpecialRenderer(blockPass)) {
/* 207:269 */                         this.tileEntityRenderers.add(blockPass);
/* 208:    */                       }
/* 209:    */                     }
/* 210:273 */                     int var32 = block.getRenderBlockPass();
/* 211:275 */                     if (var32 > renderPass) {
/* 212:277 */                       renderNextPass = true;
/* 213:    */                     }
/* 214:280 */                     boolean canRender = var32 == renderPass;
/* 215:282 */                     if (Reflector.ForgeBlock_canRenderInPass.exists()) {
/* 216:284 */                       canRender = Reflector.callBoolean(block, Reflector.ForgeBlock_canRenderInPass, new Object[] { Integer.valueOf(renderPass) });
/* 217:    */                     }
/* 218:287 */                     if (canRender)
/* 219:    */                     {
/* 220:289 */                       hasRenderedBlocks |= var27.renderBlockByRenderType(block, x, y, z);
/* 221:291 */                       if ((block.getRenderType() == 0) && (x == viewEntityPosX) && (y == viewEntityPosY) && (z == viewEntityPosZ))
/* 222:    */                       {
/* 223:293 */                         var27.setRenderFromInside(true);
/* 224:294 */                         var27.setRenderAllFaces(true);
/* 225:295 */                         var27.renderBlockByRenderType(block, x, y, z);
/* 226:296 */                         var27.setRenderFromInside(false);
/* 227:297 */                         var27.setRenderAllFaces(false);
/* 228:    */                       }
/* 229:    */                     }
/* 230:    */                   }
/* 231:    */                 }
/* 232:    */               }
/* 233:    */             }
/* 234:305 */             if (hasRenderedBlocks) {
/* 235:307 */               this.skipRenderPass[renderPass] = false;
/* 236:    */             }
/* 237:310 */             if (hasGlList) {
/* 238:312 */               postRenderBlocks(renderPass, p_147892_1_);
/* 239:    */             } else {
/* 240:316 */               hasRenderedBlocks = false;
/* 241:    */             }
/* 242:319 */             if (!renderNextPass) {
/* 243:    */               break;
/* 244:    */             }
/* 245:    */           }
/* 246:    */         }
/* 247:326 */         HashSet var31 = new HashSet();
/* 248:327 */         var31.addAll(this.tileEntityRenderers);
/* 249:328 */         var31.removeAll(var30);
/* 250:329 */         this.tileEntities.addAll(var31);
/* 251:330 */         var30.removeAll(this.tileEntityRenderers);
/* 252:331 */         this.tileEntities.removeAll(var30);
/* 253:332 */         this.isChunkLit = Chunk.isLit;
/* 254:333 */         this.isInitialized = true;
/* 255:334 */         this.skipAllRenderPasses = ((this.skipRenderPass[0] != 0) && (this.skipRenderPass[1] != 0));
/* 256:335 */         updateFinished();
/* 257:    */       }
/* 258:    */     }
/* 259:    */   }
/* 260:    */   
/* 261:    */   protected void preRenderBlocks(int renderpass)
/* 262:    */   {
/* 263:342 */     GL11.glNewList(this.glRenderList + renderpass, 4864);
/* 264:343 */     this.tessellator.setRenderingChunk(true);
/* 265:345 */     if (Config.isFastRender())
/* 266:    */     {
/* 267:347 */       this.tessellator.startDrawingQuads();
/* 268:348 */       this.tessellator.setTranslation(-globalChunkOffsetX, 0.0D, -globalChunkOffsetZ);
/* 269:    */     }
/* 270:    */     else
/* 271:    */     {
/* 272:352 */       GL11.glPushMatrix();
/* 273:353 */       setupGLTranslation();
/* 274:354 */       float var2 = 1.000001F;
/* 275:355 */       GL11.glTranslatef(-8.0F, -8.0F, -8.0F);
/* 276:356 */       GL11.glScalef(var2, var2, var2);
/* 277:357 */       GL11.glTranslatef(8.0F, 8.0F, 8.0F);
/* 278:358 */       this.tessellator.startDrawingQuads();
/* 279:359 */       this.tessellator.setTranslation(-this.posX, -this.posY, -this.posZ);
/* 280:    */     }
/* 281:    */   }
/* 282:    */   
/* 283:    */   protected void postRenderBlocks(int renderpass, EntityLivingBase entityLiving)
/* 284:    */   {
/* 285:365 */     if ((Config.isTranslucentBlocksFancy()) && (renderpass == 1) && (this.skipRenderPass[renderpass] == 0)) {
/* 286:367 */       this.vertexState = this.tessellator.getVertexState((float)entityLiving.posX, (float)entityLiving.posY, (float)entityLiving.posZ);
/* 287:    */     }
/* 288:370 */     this.bytesDrawn += this.tessellator.draw();
/* 289:371 */     this.tessellator.setRenderingChunk(false);
/* 290:373 */     if (!Config.isFastRender()) {
/* 291:375 */       GL11.glPopMatrix();
/* 292:    */     }
/* 293:378 */     GL11.glEndList();
/* 294:379 */     this.tessellator.setTranslation(0.0D, 0.0D, 0.0D);
/* 295:    */   }
/* 296:    */   
/* 297:    */   public void updateRendererSort(EntityLivingBase p_147889_1_)
/* 298:    */   {
/* 299:384 */     if ((this.vertexState != null) && (this.skipRenderPass[1] == 0))
/* 300:    */     {
/* 301:386 */       this.tessellator = Tessellator.instance;
/* 302:387 */       preRenderBlocks(1);
/* 303:388 */       this.tessellator.setVertexState(this.vertexState);
/* 304:389 */       postRenderBlocks(1, p_147889_1_);
/* 305:    */     }
/* 306:    */   }
/* 307:    */   
/* 308:    */   public float distanceToEntitySquared(Entity par1Entity)
/* 309:    */   {
/* 310:399 */     float var2 = (float)(par1Entity.posX - this.posXPlus);
/* 311:400 */     float var3 = (float)(par1Entity.posY - this.posYPlus);
/* 312:401 */     float var4 = (float)(par1Entity.posZ - this.posZPlus);
/* 313:402 */     return var2 * var2 + var3 * var3 + var4 * var4;
/* 314:    */   }
/* 315:    */   
/* 316:    */   public void setDontDraw()
/* 317:    */   {
/* 318:410 */     for (int var1 = 0; var1 < 2; var1++) {
/* 319:412 */       this.skipRenderPass[var1] = true;
/* 320:    */     }
/* 321:415 */     this.skipAllRenderPasses = true;
/* 322:416 */     this.isInFrustum = false;
/* 323:417 */     this.isInitialized = false;
/* 324:418 */     this.vertexState = null;
/* 325:    */   }
/* 326:    */   
/* 327:    */   public void stopRendering()
/* 328:    */   {
/* 329:423 */     setDontDraw();
/* 330:424 */     this.worldObj = null;
/* 331:    */   }
/* 332:    */   
/* 333:    */   public int getGLCallListForPass(int par1)
/* 334:    */   {
/* 335:432 */     return this.glRenderList + par1;
/* 336:    */   }
/* 337:    */   
/* 338:    */   public void updateInFrustum(ICamera par1ICamera)
/* 339:    */   {
/* 340:437 */     this.isInFrustum = par1ICamera.isBoundingBoxInFrustum(this.rendererBoundingBox);
/* 341:439 */     if ((this.isInFrustum) && (Config.isOcclusionFancy())) {
/* 342:441 */       this.isInFrustrumFully = par1ICamera.isBoundingBoxInFrustumFully(this.rendererBoundingBox);
/* 343:    */     } else {
/* 344:445 */       this.isInFrustrumFully = false;
/* 345:    */     }
/* 346:    */   }
/* 347:    */   
/* 348:    */   public void callOcclusionQueryList()
/* 349:    */   {
/* 350:454 */     GL11.glCallList(this.glRenderList + 2);
/* 351:    */   }
/* 352:    */   
/* 353:    */   public boolean skipAllRenderPasses()
/* 354:    */   {
/* 355:462 */     return this.skipAllRenderPasses;
/* 356:    */   }
/* 357:    */   
/* 358:    */   public void markDirty()
/* 359:    */   {
/* 360:470 */     this.needsUpdate = true;
/* 361:    */   }
/* 362:    */   
/* 363:    */   protected void updateFinished()
/* 364:    */   {
/* 365:475 */     if ((!this.skipAllRenderPasses) && (!this.inSortedWorldRenderers)) {
/* 366:477 */       Minecraft.getMinecraft().renderGlobal.addToSortedWorldRenderers(this);
/* 367:    */     }
/* 368:    */   }
/* 369:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.WorldRenderer
 * JD-Core Version:    0.7.0.1
 */