/*     */ package me.eagler.module.modules.combat;
/*     */ 
/*     */ import java.util.Random;
/*     */ import me.eagler.Client;
/*     */ import me.eagler.event.EventManager;
/*     */ import me.eagler.event.EventTarget;
/*     */ import me.eagler.event.events.PacketEvent;
/*     */ import me.eagler.gui.GuiFriends;
/*     */ import me.eagler.module.Category;
/*     */ import me.eagler.module.Module;
/*     */ import me.eagler.setting.Setting;
/*     */ import me.eagler.utils.RayCastUtil;
/*     */ import me.eagler.utils.TimeHelper;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.C03PacketPlayer;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.Vec3;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Killaura
/*     */   extends Module
/*     */ {
/*  36 */   public TimeHelper time = new TimeHelper();
/*     */   
/*  38 */   private float[] lastRotations = new float[2];
/*     */   
/*  40 */   private int rotationSpeed = 1;
/*     */   
/*     */   private boolean rotated = false;
/*     */   
/*  44 */   public static EntityLivingBase attackedEntity = null;
/*     */   
/*     */   public Killaura() {
/*  47 */     super("Killaura", Category.Combat);
/*     */     
/*  49 */     this.settingManager.addSetting(new Setting("Range", this, 3.95D, 1.0D, 6.0D, false));
/*  50 */     this.settingManager.addSetting(new Setting("Delay", this, 200.0D, 1.0D, 1000.0D, true));
/*  51 */     this.settingManager.addSetting(new Setting("CPS", this, 0.0D, 0.0D, 15.0D, true));
/*  52 */     this.settingManager.addSetting(new Setting("AutoBlock", this, false));
/*  53 */     this.settingManager.addSetting(new Setting("Player", this, true));
/*  54 */     this.settingManager.addSetting(new Setting("Animals", this, true));
/*  55 */     this.settingManager.addSetting(new Setting("Mobs", this, true));
/*  56 */     this.settingManager.addSetting(new Setting("RayCast", this, true));
/*  57 */     this.settingManager.addSetting(new Setting("Villager", this, false));
/*  58 */     this.settingManager.addSetting(new Setting("Invisible", this, false));
/*  59 */     this.settingManager.addSetting(new Setting("CantSee", this, false));
/*  60 */     this.settingManager.addSetting(new Setting("NoFriends", this, false));
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEnable() {
/*  65 */     EventManager.register(this);
/*  66 */     EventManager.register(this);
/*     */     
/*  68 */     this.time.reset();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDisable() {
/*  74 */     EventManager.unregister(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  80 */     double range = this.settingManager.getSettingByName("Range").getValue();
/*  81 */     double delay = this.settingManager.getSettingByName("Delay").getValue();
/*  82 */     double cps = this.settingManager.getSettingByName("CPS").getValue();
/*     */     
/*  84 */     setExtraTag(round(range, 1));
/*     */     
/*  86 */     for (Entity e : this.mc.theWorld.loadedEntityList) {
/*     */       
/*  88 */       if (e instanceof EntityLivingBase)
/*     */       {
/*  90 */         if (isValid(e)) {
/*     */           
/*  92 */           Random rdm = new Random();
/*     */           
/*  94 */           double ddelay = delay;
/*     */           
/*  96 */           if (cps != 0.0D) {
/*     */             
/*  98 */             ddelay = 1000.0D / cps;
/*     */           
/*     */           }
/*     */           else {
/*     */ 
/*     */             
/* 104 */             ddelay = rdm.nextInt((int)delay);
/*     */           } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 114 */           if (this.settingManager.getSettingByName("AutoBlock").getBoolean())
/*     */           {
/* 116 */             if (this.mc.thePlayer.getHeldItem() != null)
/*     */             {
/* 118 */               if (this.mc.thePlayer.getHeldItem().getItem() instanceof net.minecraft.item.ItemSword)
/*     */               {
/* 120 */                 this.mc.thePlayer.setItemInUse(this.mc.thePlayer.getHeldItem(), 200);
/*     */               }
/*     */             }
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 128 */           if (this.time.hasReached((long)ddelay)) {
/*     */             
/* 130 */             if (((EntityLivingBase)e).deathTime == 0) {
/*     */               
/* 132 */               EntityLivingBase entityLivingBase = (EntityLivingBase)e;
/*     */               
/*     */               try {
/* 135 */                 if (this.settingManager.getSettingByName("RayCast").getBoolean())
/*     */                 {
/* 137 */                   MovingObjectPosition objectMouseOver = RayCastUtil.getMouseOver(this.lastRotations[0], 
/* 138 */                       this.lastRotations[1], (float)this.settingManager.getSettingByName("Range").getValue());
/*     */                   
/* 140 */                   if (objectMouseOver.entityHit != null && e != objectMouseOver.entityHit && 
/* 141 */                     objectMouseOver.entityHit instanceof EntityLivingBase) {
/* 142 */                     entityLivingBase = (EntityLivingBase)objectMouseOver.entityHit;
/*     */                   }
/*     */                 }
/*     */               
/*     */               }
/* 147 */               catch (Exception exception) {}
/*     */               
/* 149 */               if (this.settingManager.getSettingByName("AutoBlock").getBoolean())
/*     */               {
/* 151 */                 this.mc.gameSettings.keyBindUseItem.pressed = false;
/*     */               }
/*     */ 
/*     */               
/* 155 */               this.mc.playerController.attackEntity((EntityPlayer)this.mc.thePlayer, (Entity)entityLivingBase);
/*     */               
/* 157 */               attackedEntity = entityLivingBase;
/*     */               
/* 159 */               this.mc.thePlayer.swingItem();
/*     */ 
/*     */ 
/*     */               
/* 163 */               this.time.reset();
/*     */             } 
/*     */             
/*     */             continue;
/*     */           } 
/*     */           
/* 169 */           if (this.settingManager.getSettingByName("AutoBlock").getBoolean())
/*     */           {
/* 171 */             if (this.mc.thePlayer.getHeldItem() != null) {
/*     */               
/* 173 */               if (this.mc.thePlayer.getHeldItem().getItem() instanceof net.minecraft.item.ItemSword) {
/*     */                 
/* 175 */                 this.mc.gameSettings.keyBindUseItem.pressed = true;
/*     */                 
/*     */                 continue;
/*     */               } 
/* 179 */               this.mc.gameSettings.keyBindUseItem.pressed = false;
/*     */             } 
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 195 */     if (this.settingManager.getSettingByName("AutoBlock").getBoolean())
/*     */     {
/* 197 */       if (this.mc.thePlayer.getHeldItem() != null)
/*     */       {
/* 199 */         if (this.mc.thePlayer.getHeldItem().getItem() instanceof net.minecraft.item.ItemSword)
/*     */         {
/* 201 */           this.mc.gameSettings.keyBindUseItem.pressed = false;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventTarget
/*     */   public void onPacket(PacketEvent event) {
/* 214 */     Packet packet = event.getPacket();
/*     */     
/* 216 */     if (packet instanceof C03PacketPlayer)
/*     */     {
/* 218 */       for (Object j : this.mc.theWorld.loadedEntityList) {
/*     */         
/* 220 */         Entity e = (Entity)j;
/*     */         
/* 222 */         if (isValid(e)) {
/*     */           
/* 224 */           if (e == null) {
/*     */             
/* 226 */             this.lastRotations[0] = this.mc.thePlayer.rotationYaw;
/* 227 */             this.lastRotations[1] = this.mc.thePlayer.rotationPitch;
/*     */ 
/*     */             
/*     */             return;
/*     */           } 
/*     */           
/* 233 */           this.rotated = false;
/*     */           
/* 235 */           float[] facing = getRotations(getCenter(e.getEntityBoundingBox()));
/*     */           
/* 237 */           this.lastRotations[0] = facing[0];
/*     */           
/* 239 */           this.lastRotations[1] = facing[1];
/*     */           
/* 241 */           if (((EntityLivingBase)e).deathTime == 0) {
/*     */             
/* 243 */             C03PacketPlayer.yaw = facing[0];
/* 244 */             C03PacketPlayer.pitch = facing[1];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 254 */             this.mc.thePlayer.rotationYawHead = facing[0];
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValid(Entity e) {
/* 266 */     double range = this.settingManager.getSettingByName("Range").getValue();
/*     */     
/* 268 */     if (this.mc.thePlayer.getDistanceToEntity(e) > range)
/*     */     {
/* 270 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 274 */     if (!(e instanceof EntityLivingBase))
/*     */     {
/* 276 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 280 */     if (e.isDead)
/*     */     {
/* 282 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 286 */     if (e == this.mc.thePlayer)
/*     */     {
/* 288 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 292 */     if (!this.settingManager.getSettingByName("Player").getBoolean())
/*     */     {
/* 294 */       if (e instanceof EntityPlayer)
/*     */       {
/* 296 */         return false;
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 302 */     if (!this.settingManager.getSettingByName("Animals").getBoolean())
/*     */     {
/* 304 */       if (e instanceof net.minecraft.entity.passive.EntityAnimal)
/*     */       {
/* 306 */         return false;
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 312 */     if (!this.settingManager.getSettingByName("Mobs").getBoolean())
/*     */     {
/* 314 */       if (e instanceof net.minecraft.entity.monster.EntityMob)
/*     */       {
/* 316 */         return false;
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 322 */     if (!this.settingManager.getSettingByName("Villager").getBoolean())
/*     */     {
/* 324 */       if (e instanceof net.minecraft.entity.passive.EntityVillager)
/*     */       {
/* 326 */         return false;
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 332 */     if (!this.settingManager.getSettingByName("Invisible").getBoolean())
/*     */     {
/* 334 */       if (e.isInvisible() || e.isInvisibleToPlayer((EntityPlayer)this.mc.thePlayer))
/*     */       {
/* 336 */         return false;
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 342 */     if (!this.settingManager.getSettingByName("CantSee").getBoolean())
/*     */     {
/* 344 */       if (!this.mc.thePlayer.canEntityBeSeen(e))
/*     */       {
/* 346 */         return false;
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 352 */     if (!this.settingManager.getSettingByName("NoFriends").getBoolean())
/*     */     {
/* 354 */       if (GuiFriends.friends.size() != 0)
/*     */       {
/* 356 */         for (int i = 0; i < GuiFriends.friends.size(); i++) {
/*     */           
/* 358 */           String name = GuiFriends.friends.get(i);
/*     */           
/* 360 */           if (name.toLowerCase().equalsIgnoreCase(e.getName().toLowerCase()))
/*     */           {
/* 362 */             return false;
/*     */           }
/*     */         } 
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 372 */     if (Client.instance.getModuleManager().getModuleByName("Teams").isEnabled())
/*     */     {
/* 374 */       if (e instanceof EntityPlayer)
/*     */       {
/* 376 */         if (Teams.teams.contains(e))
/*     */         {
/* 378 */           return false;
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 386 */     if (e instanceof net.minecraft.entity.item.EntityArmorStand)
/*     */     {
/* 388 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 392 */     if (this.mc.currentScreen instanceof net.minecraft.client.gui.Gui)
/*     */     {
/* 394 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 398 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Vec3 getCenter(AxisAlignedBB bb) {
/* 404 */     return new Vec3(bb.minX + (bb.maxX - bb.minX) * 0.5D, bb.minY + (bb.maxY - bb.minY) * 0.5D, 
/* 405 */         bb.minZ + (bb.maxZ - bb.minZ) * 0.5D);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] getRotations(Vec3 vec) {
/* 411 */     Vec3 eyesPos = getEyesPos();
/*     */     
/* 413 */     double diffX = vec.xCoord - eyesPos.xCoord;
/* 414 */     double diffY = vec.yCoord - eyesPos.yCoord;
/* 415 */     double diffZ = vec.zCoord - eyesPos.zCoord;
/*     */     
/* 417 */     double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
/*     */     
/* 419 */     float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0F;
/* 420 */     float pitch = (float)-Math.toDegrees(Math.atan2(diffY, diffXZ));
/*     */     
/* 422 */     return new float[] { MathHelper.wrapAngleTo180_float(yaw), MathHelper.wrapAngleTo180_float(pitch) };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Vec3 getEyesPos() {
/* 428 */     return new Vec3(this.mc.thePlayer.posX, this.mc.thePlayer.posY + this.mc.thePlayer.getEyeHeight(), this.mc.thePlayer.posZ);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static double round(double wert, int stellen) {
/* 434 */     return Math.round(wert * Math.pow(10.0D, stellen)) / Math.pow(10.0D, stellen);
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\me\eagler\module\modules\combat\Killaura.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */