/*   1:    */ package net.minecraft.client.renderer.entity;
/*   2:    */ 
/*   3:    */ import java.util.Collection;
/*   4:    */ import java.util.HashMap;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.Map;
/*   7:    */ import net.minecraft.block.Block;
/*   8:    */ import net.minecraft.client.gui.FontRenderer;
/*   9:    */ import net.minecraft.client.model.ModelChicken;
/*  10:    */ import net.minecraft.client.model.ModelCow;
/*  11:    */ import net.minecraft.client.model.ModelHorse;
/*  12:    */ import net.minecraft.client.model.ModelOcelot;
/*  13:    */ import net.minecraft.client.model.ModelPig;
/*  14:    */ import net.minecraft.client.model.ModelSheep1;
/*  15:    */ import net.minecraft.client.model.ModelSheep2;
/*  16:    */ import net.minecraft.client.model.ModelSlime;
/*  17:    */ import net.minecraft.client.model.ModelSquid;
/*  18:    */ import net.minecraft.client.model.ModelWolf;
/*  19:    */ import net.minecraft.client.model.ModelZombie;
/*  20:    */ import net.minecraft.client.renderer.ItemRenderer;
/*  21:    */ import net.minecraft.client.renderer.OpenGlHelper;
/*  22:    */ import net.minecraft.client.renderer.RenderGlobal;
/*  23:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*  24:    */ import net.minecraft.client.renderer.texture.TextureManager;
/*  25:    */ import net.minecraft.client.renderer.tileentity.RenderEnderCrystal;
/*  26:    */ import net.minecraft.client.renderer.tileentity.RenderItemFrame;
/*  27:    */ import net.minecraft.client.renderer.tileentity.RenderWitherSkull;
/*  28:    */ import net.minecraft.client.settings.GameSettings;
/*  29:    */ import net.minecraft.crash.CrashReport;
/*  30:    */ import net.minecraft.crash.CrashReportCategory;
/*  31:    */ import net.minecraft.entity.Entity;
/*  32:    */ import net.minecraft.entity.EntityLeashKnot;
/*  33:    */ import net.minecraft.entity.EntityLivingBase;
/*  34:    */ import net.minecraft.entity.ai.EntityMinecartMobSpawner;
/*  35:    */ import net.minecraft.entity.boss.EntityDragon;
/*  36:    */ import net.minecraft.entity.boss.EntityWither;
/*  37:    */ import net.minecraft.entity.effect.EntityLightningBolt;
/*  38:    */ import net.minecraft.entity.item.EntityBoat;
/*  39:    */ import net.minecraft.entity.item.EntityEnderCrystal;
/*  40:    */ import net.minecraft.entity.item.EntityEnderEye;
/*  41:    */ import net.minecraft.entity.item.EntityEnderPearl;
/*  42:    */ import net.minecraft.entity.item.EntityExpBottle;
/*  43:    */ import net.minecraft.entity.item.EntityFallingBlock;
/*  44:    */ import net.minecraft.entity.item.EntityFireworkRocket;
/*  45:    */ import net.minecraft.entity.item.EntityItem;
/*  46:    */ import net.minecraft.entity.item.EntityItemFrame;
/*  47:    */ import net.minecraft.entity.item.EntityMinecart;
/*  48:    */ import net.minecraft.entity.item.EntityMinecartTNT;
/*  49:    */ import net.minecraft.entity.item.EntityPainting;
/*  50:    */ import net.minecraft.entity.item.EntityTNTPrimed;
/*  51:    */ import net.minecraft.entity.item.EntityXPOrb;
/*  52:    */ import net.minecraft.entity.monster.EntityBlaze;
/*  53:    */ import net.minecraft.entity.monster.EntityCaveSpider;
/*  54:    */ import net.minecraft.entity.monster.EntityCreeper;
/*  55:    */ import net.minecraft.entity.monster.EntityEnderman;
/*  56:    */ import net.minecraft.entity.monster.EntityGhast;
/*  57:    */ import net.minecraft.entity.monster.EntityGiantZombie;
/*  58:    */ import net.minecraft.entity.monster.EntityIronGolem;
/*  59:    */ import net.minecraft.entity.monster.EntityMagmaCube;
/*  60:    */ import net.minecraft.entity.monster.EntitySilverfish;
/*  61:    */ import net.minecraft.entity.monster.EntitySkeleton;
/*  62:    */ import net.minecraft.entity.monster.EntitySlime;
/*  63:    */ import net.minecraft.entity.monster.EntitySnowman;
/*  64:    */ import net.minecraft.entity.monster.EntitySpider;
/*  65:    */ import net.minecraft.entity.monster.EntityWitch;
/*  66:    */ import net.minecraft.entity.monster.EntityZombie;
/*  67:    */ import net.minecraft.entity.passive.EntityBat;
/*  68:    */ import net.minecraft.entity.passive.EntityChicken;
/*  69:    */ import net.minecraft.entity.passive.EntityCow;
/*  70:    */ import net.minecraft.entity.passive.EntityHorse;
/*  71:    */ import net.minecraft.entity.passive.EntityMooshroom;
/*  72:    */ import net.minecraft.entity.passive.EntityOcelot;
/*  73:    */ import net.minecraft.entity.passive.EntityPig;
/*  74:    */ import net.minecraft.entity.passive.EntitySheep;
/*  75:    */ import net.minecraft.entity.passive.EntitySquid;
/*  76:    */ import net.minecraft.entity.passive.EntityVillager;
/*  77:    */ import net.minecraft.entity.passive.EntityWolf;
/*  78:    */ import net.minecraft.entity.player.EntityPlayer;
/*  79:    */ import net.minecraft.entity.projectile.EntityArrow;
/*  80:    */ import net.minecraft.entity.projectile.EntityEgg;
/*  81:    */ import net.minecraft.entity.projectile.EntityFishHook;
/*  82:    */ import net.minecraft.entity.projectile.EntityLargeFireball;
/*  83:    */ import net.minecraft.entity.projectile.EntityPotion;
/*  84:    */ import net.minecraft.entity.projectile.EntitySmallFireball;
/*  85:    */ import net.minecraft.entity.projectile.EntitySnowball;
/*  86:    */ import net.minecraft.entity.projectile.EntityWitherSkull;
/*  87:    */ import net.minecraft.init.Blocks;
/*  88:    */ import net.minecraft.init.Items;
/*  89:    */ import net.minecraft.util.AABBPool;
/*  90:    */ import net.minecraft.util.AxisAlignedBB;
/*  91:    */ import net.minecraft.util.MathHelper;
/*  92:    */ import net.minecraft.util.ReportedException;
/*  93:    */ import net.minecraft.world.World;
/*  94:    */ import org.lwjgl.opengl.GL11;
/*  95:    */ 
/*  96:    */ public class RenderManager
/*  97:    */ {
/*  98: 97 */   private Map entityRenderMap = new HashMap();
/*  99:100 */   public static RenderManager instance = new RenderManager();
/* 100:    */   private FontRenderer fontRenderer;
/* 101:    */   public static double renderPosX;
/* 102:    */   public static double renderPosY;
/* 103:    */   public static double renderPosZ;
/* 104:    */   public TextureManager renderEngine;
/* 105:    */   public ItemRenderer itemRenderer;
/* 106:    */   public World worldObj;
/* 107:    */   public EntityLivingBase livingPlayer;
/* 108:    */   public Entity field_147941_i;
/* 109:    */   public float playerViewY;
/* 110:    */   public float playerViewX;
/* 111:    */   public GameSettings options;
/* 112:    */   public double viewerPosX;
/* 113:    */   public double viewerPosY;
/* 114:    */   public double viewerPosZ;
/* 115:    */   public static boolean field_85095_o;
/* 116:    */   private static final String __OBFID = "CL_00000991";
/* 117:    */   
/* 118:    */   private RenderManager()
/* 119:    */   {
/* 120:129 */     this.entityRenderMap.put(EntityCaveSpider.class, new RenderCaveSpider());
/* 121:130 */     this.entityRenderMap.put(EntitySpider.class, new RenderSpider());
/* 122:131 */     this.entityRenderMap.put(EntityPig.class, new RenderPig(new ModelPig(), new ModelPig(0.5F), 0.7F));
/* 123:132 */     this.entityRenderMap.put(EntitySheep.class, new RenderSheep(new ModelSheep2(), new ModelSheep1(), 0.7F));
/* 124:133 */     this.entityRenderMap.put(EntityCow.class, new RenderCow(new ModelCow(), 0.7F));
/* 125:134 */     this.entityRenderMap.put(EntityMooshroom.class, new RenderMooshroom(new ModelCow(), 0.7F));
/* 126:135 */     this.entityRenderMap.put(EntityWolf.class, new RenderWolf(new ModelWolf(), new ModelWolf(), 0.5F));
/* 127:136 */     this.entityRenderMap.put(EntityChicken.class, new RenderChicken(new ModelChicken(), 0.3F));
/* 128:137 */     this.entityRenderMap.put(EntityOcelot.class, new RenderOcelot(new ModelOcelot(), 0.4F));
/* 129:138 */     this.entityRenderMap.put(EntitySilverfish.class, new RenderSilverfish());
/* 130:139 */     this.entityRenderMap.put(EntityCreeper.class, new RenderCreeper());
/* 131:140 */     this.entityRenderMap.put(EntityEnderman.class, new RenderEnderman());
/* 132:141 */     this.entityRenderMap.put(EntitySnowman.class, new RenderSnowMan());
/* 133:142 */     this.entityRenderMap.put(EntitySkeleton.class, new RenderSkeleton());
/* 134:143 */     this.entityRenderMap.put(EntityWitch.class, new RenderWitch());
/* 135:144 */     this.entityRenderMap.put(EntityBlaze.class, new RenderBlaze());
/* 136:145 */     this.entityRenderMap.put(EntityZombie.class, new RenderZombie());
/* 137:146 */     this.entityRenderMap.put(EntitySlime.class, new RenderSlime(new ModelSlime(16), new ModelSlime(0), 0.25F));
/* 138:147 */     this.entityRenderMap.put(EntityMagmaCube.class, new RenderMagmaCube());
/* 139:148 */     this.entityRenderMap.put(EntityPlayer.class, new RenderPlayer());
/* 140:149 */     this.entityRenderMap.put(EntityGiantZombie.class, new RenderGiantZombie(new ModelZombie(), 0.5F, 6.0F));
/* 141:150 */     this.entityRenderMap.put(EntityGhast.class, new RenderGhast());
/* 142:151 */     this.entityRenderMap.put(EntitySquid.class, new RenderSquid(new ModelSquid(), 0.7F));
/* 143:152 */     this.entityRenderMap.put(EntityVillager.class, new RenderVillager());
/* 144:153 */     this.entityRenderMap.put(EntityIronGolem.class, new RenderIronGolem());
/* 145:154 */     this.entityRenderMap.put(EntityBat.class, new RenderBat());
/* 146:155 */     this.entityRenderMap.put(EntityDragon.class, new RenderDragon());
/* 147:156 */     this.entityRenderMap.put(EntityEnderCrystal.class, new RenderEnderCrystal());
/* 148:157 */     this.entityRenderMap.put(EntityWither.class, new RenderWither());
/* 149:158 */     this.entityRenderMap.put(Entity.class, new RenderEntity());
/* 150:159 */     this.entityRenderMap.put(EntityPainting.class, new RenderPainting());
/* 151:160 */     this.entityRenderMap.put(EntityItemFrame.class, new RenderItemFrame());
/* 152:161 */     this.entityRenderMap.put(EntityLeashKnot.class, new RenderLeashKnot());
/* 153:162 */     this.entityRenderMap.put(EntityArrow.class, new RenderArrow());
/* 154:163 */     this.entityRenderMap.put(EntitySnowball.class, new RenderSnowball(Items.snowball));
/* 155:164 */     this.entityRenderMap.put(EntityEnderPearl.class, new RenderSnowball(Items.ender_pearl));
/* 156:165 */     this.entityRenderMap.put(EntityEnderEye.class, new RenderSnowball(Items.ender_eye));
/* 157:166 */     this.entityRenderMap.put(EntityEgg.class, new RenderSnowball(Items.egg));
/* 158:167 */     this.entityRenderMap.put(EntityPotion.class, new RenderSnowball(Items.potionitem, 16384));
/* 159:168 */     this.entityRenderMap.put(EntityExpBottle.class, new RenderSnowball(Items.experience_bottle));
/* 160:169 */     this.entityRenderMap.put(EntityFireworkRocket.class, new RenderSnowball(Items.fireworks));
/* 161:170 */     this.entityRenderMap.put(EntityLargeFireball.class, new RenderFireball(2.0F));
/* 162:171 */     this.entityRenderMap.put(EntitySmallFireball.class, new RenderFireball(0.5F));
/* 163:172 */     this.entityRenderMap.put(EntityWitherSkull.class, new RenderWitherSkull());
/* 164:173 */     this.entityRenderMap.put(EntityItem.class, new RenderItem());
/* 165:174 */     this.entityRenderMap.put(EntityXPOrb.class, new RenderXPOrb());
/* 166:175 */     this.entityRenderMap.put(EntityTNTPrimed.class, new RenderTNTPrimed());
/* 167:176 */     this.entityRenderMap.put(EntityFallingBlock.class, new RenderFallingBlock());
/* 168:177 */     this.entityRenderMap.put(EntityMinecartTNT.class, new RenderTntMinecart());
/* 169:178 */     this.entityRenderMap.put(EntityMinecartMobSpawner.class, new RenderMinecartMobSpawner());
/* 170:179 */     this.entityRenderMap.put(EntityMinecart.class, new RenderMinecart());
/* 171:180 */     this.entityRenderMap.put(EntityBoat.class, new RenderBoat());
/* 172:181 */     this.entityRenderMap.put(EntityFishHook.class, new RenderFish());
/* 173:182 */     this.entityRenderMap.put(EntityHorse.class, new RenderHorse(new ModelHorse(), 0.75F));
/* 174:183 */     this.entityRenderMap.put(EntityLightningBolt.class, new RenderLightningBolt());
/* 175:184 */     Iterator var1 = this.entityRenderMap.values().iterator();
/* 176:186 */     while (var1.hasNext())
/* 177:    */     {
/* 178:188 */       Render var2 = (Render)var1.next();
/* 179:189 */       var2.setRenderManager(this);
/* 180:    */     }
/* 181:    */   }
/* 182:    */   
/* 183:    */   public Render getEntityClassRenderObject(Class par1Class)
/* 184:    */   {
/* 185:195 */     Render var2 = (Render)this.entityRenderMap.get(par1Class);
/* 186:197 */     if ((var2 == null) && (par1Class != Entity.class))
/* 187:    */     {
/* 188:199 */       var2 = getEntityClassRenderObject(par1Class.getSuperclass());
/* 189:200 */       this.entityRenderMap.put(par1Class, var2);
/* 190:    */     }
/* 191:203 */     return var2;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public Render getEntityRenderObject(Entity par1Entity)
/* 195:    */   {
/* 196:208 */     return getEntityClassRenderObject(par1Entity.getClass());
/* 197:    */   }
/* 198:    */   
/* 199:    */   public void func_147938_a(World p_147938_1_, TextureManager p_147938_2_, FontRenderer p_147938_3_, EntityLivingBase p_147938_4_, Entity p_147938_5_, GameSettings p_147938_6_, float p_147938_7_)
/* 200:    */   {
/* 201:213 */     this.worldObj = p_147938_1_;
/* 202:214 */     this.renderEngine = p_147938_2_;
/* 203:215 */     this.options = p_147938_6_;
/* 204:216 */     this.livingPlayer = p_147938_4_;
/* 205:217 */     this.field_147941_i = p_147938_5_;
/* 206:218 */     this.fontRenderer = p_147938_3_;
/* 207:220 */     if (p_147938_4_.isPlayerSleeping())
/* 208:    */     {
/* 209:222 */       Block var8 = p_147938_1_.getBlock(MathHelper.floor_double(p_147938_4_.posX), MathHelper.floor_double(p_147938_4_.posY), MathHelper.floor_double(p_147938_4_.posZ));
/* 210:224 */       if (var8 == Blocks.bed)
/* 211:    */       {
/* 212:226 */         int var9 = p_147938_1_.getBlockMetadata(MathHelper.floor_double(p_147938_4_.posX), MathHelper.floor_double(p_147938_4_.posY), MathHelper.floor_double(p_147938_4_.posZ));
/* 213:227 */         int var10 = var9 & 0x3;
/* 214:228 */         this.playerViewY = (var10 * 90 + 180);
/* 215:229 */         this.playerViewX = 0.0F;
/* 216:    */       }
/* 217:    */     }
/* 218:    */     else
/* 219:    */     {
/* 220:234 */       this.playerViewY = (p_147938_4_.prevRotationYaw + (p_147938_4_.rotationYaw - p_147938_4_.prevRotationYaw) * p_147938_7_);
/* 221:235 */       this.playerViewX = (p_147938_4_.prevRotationPitch + (p_147938_4_.rotationPitch - p_147938_4_.prevRotationPitch) * p_147938_7_);
/* 222:    */     }
/* 223:238 */     if (p_147938_6_.thirdPersonView == 2) {
/* 224:240 */       this.playerViewY += 180.0F;
/* 225:    */     }
/* 226:243 */     this.viewerPosX = (p_147938_4_.lastTickPosX + (p_147938_4_.posX - p_147938_4_.lastTickPosX) * p_147938_7_);
/* 227:244 */     this.viewerPosY = (p_147938_4_.lastTickPosY + (p_147938_4_.posY - p_147938_4_.lastTickPosY) * p_147938_7_);
/* 228:245 */     this.viewerPosZ = (p_147938_4_.lastTickPosZ + (p_147938_4_.posZ - p_147938_4_.lastTickPosZ) * p_147938_7_);
/* 229:    */   }
/* 230:    */   
/* 231:    */   public boolean func_147937_a(Entity p_147937_1_, float p_147937_2_)
/* 232:    */   {
/* 233:250 */     return func_147936_a(p_147937_1_, p_147937_2_, false);
/* 234:    */   }
/* 235:    */   
/* 236:    */   public boolean func_147936_a(Entity p_147936_1_, float p_147936_2_, boolean p_147936_3_)
/* 237:    */   {
/* 238:255 */     if (p_147936_1_.ticksExisted == 0)
/* 239:    */     {
/* 240:257 */       p_147936_1_.lastTickPosX = p_147936_1_.posX;
/* 241:258 */       p_147936_1_.lastTickPosY = p_147936_1_.posY;
/* 242:259 */       p_147936_1_.lastTickPosZ = p_147936_1_.posZ;
/* 243:    */     }
/* 244:262 */     double var4 = p_147936_1_.lastTickPosX + (p_147936_1_.posX - p_147936_1_.lastTickPosX) * p_147936_2_;
/* 245:263 */     double var6 = p_147936_1_.lastTickPosY + (p_147936_1_.posY - p_147936_1_.lastTickPosY) * p_147936_2_;
/* 246:264 */     double var8 = p_147936_1_.lastTickPosZ + (p_147936_1_.posZ - p_147936_1_.lastTickPosZ) * p_147936_2_;
/* 247:265 */     float var10 = p_147936_1_.prevRotationYaw + (p_147936_1_.rotationYaw - p_147936_1_.prevRotationYaw) * p_147936_2_;
/* 248:266 */     int var11 = p_147936_1_.getBrightnessForRender(p_147936_2_);
/* 249:268 */     if (p_147936_1_.isBurning()) {
/* 250:270 */       var11 = 15728880;
/* 251:    */     }
/* 252:273 */     int var12 = var11 % 65536;
/* 253:274 */     int var13 = var11 / 65536;
/* 254:275 */     OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var12 / 1.0F, var13 / 1.0F);
/* 255:276 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 256:277 */     return func_147939_a(p_147936_1_, var4 - renderPosX, var6 - renderPosY, var8 - renderPosZ, var10, p_147936_2_, p_147936_3_);
/* 257:    */   }
/* 258:    */   
/* 259:    */   public boolean func_147940_a(Entity p_147940_1_, double p_147940_2_, double p_147940_4_, double p_147940_6_, float p_147940_8_, float p_147940_9_)
/* 260:    */   {
/* 261:282 */     return func_147939_a(p_147940_1_, p_147940_2_, p_147940_4_, p_147940_6_, p_147940_8_, p_147940_9_, false);
/* 262:    */   }
/* 263:    */   
/* 264:    */   public boolean func_147939_a(Entity p_147939_1_, double p_147939_2_, double p_147939_4_, double p_147939_6_, float p_147939_8_, float p_147939_9_, boolean p_147939_10_)
/* 265:    */   {
/* 266:287 */     Render var11 = null;
/* 267:    */     try
/* 268:    */     {
/* 269:291 */       var11 = getEntityRenderObject(p_147939_1_);
/* 270:293 */       if ((var11 != null) && (this.renderEngine != null))
/* 271:    */       {
/* 272:295 */         if ((!var11.func_147905_a()) || (p_147939_10_))
/* 273:    */         {
/* 274:    */           try
/* 275:    */           {
/* 276:299 */             var11.doRender(p_147939_1_, p_147939_2_, p_147939_4_, p_147939_6_, p_147939_8_, p_147939_9_);
/* 277:    */           }
/* 278:    */           catch (Throwable var18)
/* 279:    */           {
/* 280:303 */             throw new ReportedException(CrashReport.makeCrashReport(var18, "Rendering entity in world"));
/* 281:    */           }
/* 282:    */           try
/* 283:    */           {
/* 284:308 */             var11.doRenderShadowAndFire(p_147939_1_, p_147939_2_, p_147939_4_, p_147939_6_, p_147939_8_, p_147939_9_);
/* 285:    */           }
/* 286:    */           catch (Throwable var17)
/* 287:    */           {
/* 288:312 */             throw new ReportedException(CrashReport.makeCrashReport(var17, "Post-rendering entity in world"));
/* 289:    */           }
/* 290:315 */           if ((field_85095_o) && (!p_147939_1_.isInvisible()) && (!p_147939_10_)) {
/* 291:    */             try
/* 292:    */             {
/* 293:319 */               func_85094_b(p_147939_1_, p_147939_2_, p_147939_4_, p_147939_6_, p_147939_8_, p_147939_9_);
/* 294:    */             }
/* 295:    */             catch (Throwable var16)
/* 296:    */             {
/* 297:323 */               throw new ReportedException(CrashReport.makeCrashReport(var16, "Rendering entity hitbox in world"));
/* 298:    */             }
/* 299:    */           }
/* 300:    */         }
/* 301:    */       }
/* 302:328 */       else if (this.renderEngine != null) {
/* 303:330 */         return false;
/* 304:    */       }
/* 305:333 */       return true;
/* 306:    */     }
/* 307:    */     catch (Throwable var19)
/* 308:    */     {
/* 309:337 */       CrashReport var13 = CrashReport.makeCrashReport(var19, "Rendering entity in world");
/* 310:338 */       CrashReportCategory var14 = var13.makeCategory("Entity being rendered");
/* 311:339 */       p_147939_1_.addEntityCrashInfo(var14);
/* 312:340 */       CrashReportCategory var15 = var13.makeCategory("Renderer details");
/* 313:341 */       var15.addCrashSection("Assigned renderer", var11);
/* 314:342 */       var15.addCrashSection("Location", CrashReportCategory.func_85074_a(p_147939_2_, p_147939_4_, p_147939_6_));
/* 315:343 */       var15.addCrashSection("Rotation", Float.valueOf(p_147939_8_));
/* 316:344 */       var15.addCrashSection("Delta", Float.valueOf(p_147939_9_));
/* 317:345 */       throw new ReportedException(var13);
/* 318:    */     }
/* 319:    */   }
/* 320:    */   
/* 321:    */   private void func_85094_b(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
/* 322:    */   {
/* 323:351 */     GL11.glDepthMask(false);
/* 324:352 */     GL11.glDisable(3553);
/* 325:353 */     GL11.glDisable(2896);
/* 326:354 */     GL11.glDisable(2884);
/* 327:355 */     GL11.glDisable(3042);
/* 328:356 */     float var10 = par1Entity.width / 2.0F;
/* 329:357 */     AxisAlignedBB var11 = AxisAlignedBB.getAABBPool().getAABB(par2 - var10, par4, par6 - var10, par2 + var10, par4 + par1Entity.height, par6 + var10);
/* 330:358 */     RenderGlobal.drawOutlinedBoundingBox(var11, 16777215);
/* 331:359 */     GL11.glEnable(3553);
/* 332:360 */     GL11.glEnable(2896);
/* 333:361 */     GL11.glEnable(2884);
/* 334:362 */     GL11.glDisable(3042);
/* 335:363 */     GL11.glDepthMask(true);
/* 336:    */   }
/* 337:    */   
/* 338:    */   public void set(World par1World)
/* 339:    */   {
/* 340:371 */     this.worldObj = par1World;
/* 341:    */   }
/* 342:    */   
/* 343:    */   public double getDistanceToCamera(double par1, double par3, double par5)
/* 344:    */   {
/* 345:376 */     double var7 = par1 - this.viewerPosX;
/* 346:377 */     double var9 = par3 - this.viewerPosY;
/* 347:378 */     double var11 = par5 - this.viewerPosZ;
/* 348:379 */     return var7 * var7 + var9 * var9 + var11 * var11;
/* 349:    */   }
/* 350:    */   
/* 351:    */   public FontRenderer getFontRenderer()
/* 352:    */   {
/* 353:387 */     return this.fontRenderer;
/* 354:    */   }
/* 355:    */   
/* 356:    */   public void updateIcons(IIconRegister par1IconRegister)
/* 357:    */   {
/* 358:392 */     Iterator var2 = this.entityRenderMap.values().iterator();
/* 359:394 */     while (var2.hasNext())
/* 360:    */     {
/* 361:396 */       Render var3 = (Render)var2.next();
/* 362:397 */       var3.updateIcons(par1IconRegister);
/* 363:    */     }
/* 364:    */   }
/* 365:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.entity.RenderManager
 * JD-Core Version:    0.7.0.1
 */