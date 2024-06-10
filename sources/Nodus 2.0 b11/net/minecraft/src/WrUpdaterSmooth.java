/*   1:    */ package net.minecraft.src;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import net.minecraft.client.renderer.RenderGlobal;
/*   5:    */ import net.minecraft.client.renderer.WorldRenderer;
/*   6:    */ import net.minecraft.entity.EntityLivingBase;
/*   7:    */ import net.minecraft.world.World;
/*   8:    */ 
/*   9:    */ public class WrUpdaterSmooth
/*  10:    */   implements IWrUpdater
/*  11:    */ {
/*  12: 11 */   private long lastUpdateStartTimeNs = 0L;
/*  13: 12 */   private long updateStartTimeNs = 0L;
/*  14: 13 */   private long updateTimeNs = 10000000L;
/*  15: 14 */   private WorldRendererSmooth currentUpdateRenderer = null;
/*  16: 15 */   private int renderersUpdated = 0;
/*  17: 16 */   private int renderersFound = 0;
/*  18:    */   
/*  19:    */   public void initialize() {}
/*  20:    */   
/*  21:    */   public void terminate() {}
/*  22:    */   
/*  23:    */   public WorldRenderer makeWorldRenderer(World worldObj, List tileEntities, int x, int y, int z, int glRenderListBase, int rendererIndex)
/*  24:    */   {
/*  25: 24 */     return new WorldRendererSmooth(worldObj, tileEntities, x, y, z, glRenderListBase, rendererIndex);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public boolean updateRenderers(RenderGlobal rg, EntityLivingBase entityliving, boolean flag)
/*  29:    */   {
/*  30: 29 */     this.lastUpdateStartTimeNs = this.updateStartTimeNs;
/*  31: 30 */     this.updateStartTimeNs = System.nanoTime();
/*  32: 31 */     long finishTimeNs = this.updateStartTimeNs + this.updateTimeNs;
/*  33: 32 */     int maxNum = Config.getUpdatesPerFrame();
/*  34: 34 */     if ((Config.isDynamicUpdates()) && (!rg.isMoving(entityliving))) {
/*  35: 36 */       maxNum *= 3;
/*  36:    */     }
/*  37: 39 */     this.renderersUpdated = 0;
/*  38:    */     do
/*  39:    */     {
/*  40: 43 */       this.renderersFound = 0;
/*  41: 44 */       updateRenderersImpl(rg, entityliving, flag);
/*  42: 46 */     } while ((this.renderersFound > 0) && (System.nanoTime() - finishTimeNs < 0L));
/*  43: 48 */     if (this.renderersFound > 0)
/*  44:    */     {
/*  45: 50 */       maxNum = Math.min(maxNum, this.renderersFound);
/*  46: 51 */       long diff = 400000L;
/*  47: 53 */       if (this.renderersUpdated > maxNum) {
/*  48: 55 */         this.updateTimeNs -= 2L * diff;
/*  49:    */       }
/*  50: 58 */       if (this.renderersUpdated < maxNum) {
/*  51: 60 */         this.updateTimeNs += diff;
/*  52:    */       }
/*  53:    */     }
/*  54:    */     else
/*  55:    */     {
/*  56: 65 */       this.updateTimeNs = 0L;
/*  57: 66 */       this.updateTimeNs -= 200000L;
/*  58:    */     }
/*  59: 69 */     if (this.updateTimeNs < 0L) {
/*  60: 71 */       this.updateTimeNs = 0L;
/*  61:    */     }
/*  62: 74 */     return this.renderersUpdated > 0;
/*  63:    */   }
/*  64:    */   
/*  65:    */   private void updateRenderersImpl(RenderGlobal rg, EntityLivingBase entityliving, boolean flag)
/*  66:    */   {
/*  67: 79 */     this.renderersFound = 0;
/*  68: 80 */     boolean currentUpdateFinished = true;
/*  69: 82 */     if (this.currentUpdateRenderer != null)
/*  70:    */     {
/*  71: 84 */       this.renderersFound += 1;
/*  72: 85 */       currentUpdateFinished = updateRenderer(this.currentUpdateRenderer);
/*  73: 87 */       if (currentUpdateFinished) {
/*  74: 89 */         this.renderersUpdated += 1;
/*  75:    */       }
/*  76:    */     }
/*  77: 93 */     if (rg.worldRenderersToUpdate.size() > 0)
/*  78:    */     {
/*  79: 95 */       byte NOT_IN_FRUSTRUM_MUL = 4;
/*  80: 96 */       WorldRendererSmooth wrBest = null;
/*  81: 97 */       float distSqBest = 3.4028235E+38F;
/*  82: 98 */       int indexBest = -1;
/*  83:101 */       for (int dstIndex = 0; dstIndex < rg.worldRenderersToUpdate.size(); dstIndex++)
/*  84:    */       {
/*  85:103 */         WorldRendererSmooth i = (WorldRendererSmooth)rg.worldRenderersToUpdate.get(dstIndex);
/*  86:105 */         if (i != null)
/*  87:    */         {
/*  88:107 */           this.renderersFound += 1;
/*  89:109 */           if (!i.needsUpdate)
/*  90:    */           {
/*  91:111 */             rg.worldRenderersToUpdate.set(dstIndex, null);
/*  92:    */           }
/*  93:    */           else
/*  94:    */           {
/*  95:115 */             float wr = i.distanceToEntitySquared(entityliving);
/*  96:117 */             if ((wr <= 256.0F) && (rg.isActingNow()))
/*  97:    */             {
/*  98:119 */               i.updateRenderer();
/*  99:120 */               i.needsUpdate = false;
/* 100:121 */               rg.worldRenderersToUpdate.set(dstIndex, null);
/* 101:122 */               this.renderersUpdated += 1;
/* 102:    */             }
/* 103:    */             else
/* 104:    */             {
/* 105:126 */               if (!i.isInFrustum) {
/* 106:128 */                 wr *= NOT_IN_FRUSTRUM_MUL;
/* 107:    */               }
/* 108:131 */               if (wrBest == null)
/* 109:    */               {
/* 110:133 */                 wrBest = i;
/* 111:134 */                 distSqBest = wr;
/* 112:135 */                 indexBest = dstIndex;
/* 113:    */               }
/* 114:137 */               else if (wr < distSqBest)
/* 115:    */               {
/* 116:139 */                 wrBest = i;
/* 117:140 */                 distSqBest = wr;
/* 118:141 */                 indexBest = dstIndex;
/* 119:    */               }
/* 120:    */             }
/* 121:    */           }
/* 122:    */         }
/* 123:    */       }
/* 124:148 */       if ((this.currentUpdateRenderer == null) || (currentUpdateFinished))
/* 125:    */       {
/* 126:152 */         if (wrBest != null)
/* 127:    */         {
/* 128:154 */           rg.worldRenderersToUpdate.set(indexBest, null);
/* 129:156 */           if (!updateRenderer(wrBest)) {
/* 130:158 */             return;
/* 131:    */           }
/* 132:161 */           this.renderersUpdated += 1;
/* 133:163 */           if (System.nanoTime() > this.updateStartTimeNs + this.updateTimeNs) {
/* 134:165 */             return;
/* 135:    */           }
/* 136:168 */           float var14 = distSqBest / 5.0F;
/* 137:170 */           for (int var15 = 0; var15 < rg.worldRenderersToUpdate.size(); var15++)
/* 138:    */           {
/* 139:172 */             WorldRendererSmooth var16 = (WorldRendererSmooth)rg.worldRenderersToUpdate.get(var15);
/* 140:174 */             if (var16 != null)
/* 141:    */             {
/* 142:176 */               float distSq = var16.distanceToEntitySquared(entityliving);
/* 143:178 */               if (!var16.isInFrustum) {
/* 144:180 */                 distSq *= NOT_IN_FRUSTRUM_MUL;
/* 145:    */               }
/* 146:183 */               float diffDistSq = Math.abs(distSq - distSqBest);
/* 147:185 */               if (diffDistSq < var14)
/* 148:    */               {
/* 149:187 */                 rg.worldRenderersToUpdate.set(var15, null);
/* 150:189 */                 if (!updateRenderer(var16)) {
/* 151:191 */                   return;
/* 152:    */                 }
/* 153:194 */                 this.renderersUpdated += 1;
/* 154:196 */                 if (System.nanoTime() > this.updateStartTimeNs + this.updateTimeNs) {
/* 155:    */                   break;
/* 156:    */                 }
/* 157:    */               }
/* 158:    */             }
/* 159:    */           }
/* 160:    */         }
/* 161:205 */         if (this.renderersFound == 0) {
/* 162:207 */           rg.worldRenderersToUpdate.clear();
/* 163:    */         }
/* 164:210 */         if ((rg.worldRenderersToUpdate.size() > 100) && (this.renderersFound < rg.worldRenderersToUpdate.size() * 4 / 5))
/* 165:    */         {
/* 166:212 */           dstIndex = 0;
/* 167:214 */           for (int var15 = 0; var15 < rg.worldRenderersToUpdate.size(); var15++)
/* 168:    */           {
/* 169:216 */             Object var17 = rg.worldRenderersToUpdate.get(var15);
/* 170:218 */             if (var17 != null)
/* 171:    */             {
/* 172:220 */               if (var15 != dstIndex) {
/* 173:222 */                 rg.worldRenderersToUpdate.set(dstIndex, var17);
/* 174:    */               }
/* 175:225 */               dstIndex++;
/* 176:    */             }
/* 177:    */           }
/* 178:229 */           for (var15 = rg.worldRenderersToUpdate.size() - 1; var15 >= dstIndex; var15--) {
/* 179:231 */             rg.worldRenderersToUpdate.remove(var15);
/* 180:    */           }
/* 181:    */         }
/* 182:    */       }
/* 183:    */     }
/* 184:    */   }
/* 185:    */   
/* 186:    */   private boolean updateRenderer(WorldRendererSmooth wr)
/* 187:    */   {
/* 188:240 */     long finishTime = this.updateStartTimeNs + this.updateTimeNs;
/* 189:241 */     wr.needsUpdate = false;
/* 190:242 */     boolean ready = wr.updateRenderer(finishTime);
/* 191:244 */     if (!ready)
/* 192:    */     {
/* 193:246 */       this.currentUpdateRenderer = wr;
/* 194:247 */       return false;
/* 195:    */     }
/* 196:251 */     wr.finishUpdate();
/* 197:252 */     this.currentUpdateRenderer = null;
/* 198:253 */     return true;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public void finishCurrentUpdate()
/* 202:    */   {
/* 203:259 */     if (this.currentUpdateRenderer != null)
/* 204:    */     {
/* 205:261 */       this.currentUpdateRenderer.updateRenderer();
/* 206:262 */       this.currentUpdateRenderer = null;
/* 207:    */     }
/* 208:    */   }
/* 209:    */   
/* 210:    */   public void resumeBackgroundUpdates() {}
/* 211:    */   
/* 212:    */   public void pauseBackgroundUpdates() {}
/* 213:    */   
/* 214:    */   public void preRender(RenderGlobal rg, EntityLivingBase player) {}
/* 215:    */   
/* 216:    */   public void postRender() {}
/* 217:    */   
/* 218:    */   public void clearAllUpdates()
/* 219:    */   {
/* 220:276 */     finishCurrentUpdate();
/* 221:    */   }
/* 222:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.src.WrUpdaterSmooth
 * JD-Core Version:    0.7.0.1
 */