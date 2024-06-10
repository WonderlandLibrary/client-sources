/*     */ package nightmare.module.combat;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.concurrent.ThreadLocalRandom;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import nightmare.Nightmare;
/*     */ import nightmare.event.EventTarget;
/*     */ import nightmare.event.impl.EventLoadWorld;
/*     */ import nightmare.event.impl.EventPreMotionUpdate;
/*     */ import nightmare.gui.notification.NotificationManager;
/*     */ import nightmare.gui.notification.NotificationType;
/*     */ import nightmare.module.Category;
/*     */ import nightmare.module.Module;
/*     */ import nightmare.settings.Setting;
/*     */ import nightmare.utils.RotationUtils;
/*     */ import nightmare.utils.TimerUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LegitAura
/*     */   extends Module
/*     */ {
/*     */   private EntityLivingBase target;
/*  32 */   private TimerUtils timer = new TimerUtils();
/*     */   
/*     */   public LegitAura() {
/*  35 */     super("LegitAura", 0, Category.COMBAT);
/*     */     
/*  37 */     ArrayList<String> options = new ArrayList<>();
/*     */     
/*  39 */     options.add("Legit");
/*  40 */     options.add("Normal");
/*     */     
/*  42 */     Nightmare.instance.settingsManager.rSetting(new Setting("AutoDisable", this, false));
/*  43 */     Nightmare.instance.settingsManager.rSetting(new Setting("HeldItem", this, false));
/*  44 */     Nightmare.instance.settingsManager.rSetting(new Setting("Horizontal", this, 12.0D, 0.0D, 30.0D, false));
/*  45 */     Nightmare.instance.settingsManager.rSetting(new Setting("Vertical", this, 8.0D, 0.0D, 30.0D, false));
/*  46 */     Nightmare.instance.settingsManager.rSetting(new Setting("MinCPS", this, 12.0D, 1.0D, 20.0D, false));
/*  47 */     Nightmare.instance.settingsManager.rSetting(new Setting("MaxCPS", this, 15.0D, 1.0D, 20.0D, false));
/*  48 */     Nightmare.instance.settingsManager.rSetting(new Setting("Range", this, 4.2D, 1.0D, 8.0D, false));
/*  49 */     Nightmare.instance.settingsManager.rSetting(new Setting("FOV", this, 360.0D, 0.0D, 360.0D, true));
/*  50 */     Nightmare.instance.settingsManager.rSetting(new Setting("Teams", this, false));
/*  51 */     Nightmare.instance.settingsManager.rSetting(new Setting("Invisibles", this, false));
/*  52 */     Nightmare.instance.settingsManager.rSetting(new Setting("AttackMode", this, "Legit", options));
/*     */   }
/*     */ 
/*     */   
/*     */   @EventTarget
/*     */   public void onPre(EventPreMotionUpdate event) {
/*  58 */     float horizontalSpeed = (float)Nightmare.instance.settingsManager.getSettingByName(this, "Horizontal").getValDouble();
/*  59 */     float verticalSpeed = (float)Nightmare.instance.settingsManager.getSettingByName(this, "Vertical").getValDouble();
/*  60 */     String mode = Nightmare.instance.settingsManager.getSettingByName(this, "AttackMode").getValString();
/*     */     
/*  62 */     this.target = getClosest(Nightmare.instance.settingsManager.getSettingByName(this, "Range").getValDouble());
/*     */     
/*  64 */     if (this.target != null) {
/*     */       
/*  66 */       if (this.target.func_70005_c_().equals(mc.field_71439_g.func_70005_c_())) {
/*     */         return;
/*     */       }
/*     */       
/*  70 */       if (!Nightmare.instance.settingsManager.getSettingByName(this, "HeldItem").getValBoolean() || (mc.field_71439_g.func_70694_bm() != null && (mc.field_71439_g.func_70694_bm().func_77973_b() instanceof net.minecraft.item.ItemAxe || mc.field_71439_g.func_70694_bm().func_77973_b() instanceof net.minecraft.item.ItemSword))) {
/*  71 */         faceTarget((Entity)this.target, horizontalSpeed, verticalSpeed);
/*     */         
/*  73 */         if (this.timer.delay((1000 / ThreadLocalRandom.current().nextInt((int)Nightmare.instance.settingsManager.getSettingByName(this, "MinCPS").getValDouble(), (int)Nightmare.instance.settingsManager.getSettingByName(this, "MaxCPS").getValDouble() + 1)))) {
/*  74 */           mc.field_71439_g.func_71038_i();
/*     */           
/*  76 */           if (mode.equals("Legit")) {
/*  77 */             if (mc.field_71476_x != null && mc.field_71476_x.field_72308_g != null) {
/*  78 */               mc.field_71442_b.func_78764_a((EntityPlayer)mc.field_71439_g, (Entity)this.target);
/*     */             }
/*     */           } else {
/*  81 */             mc.field_71442_b.func_78764_a((EntityPlayer)mc.field_71439_g, (Entity)this.target);
/*     */           } 
/*     */           
/*  84 */           this.timer.reset();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventTarget
/*     */   public void onLoadWold(EventLoadWorld event) {
/*  92 */     if (Nightmare.instance.settingsManager.getSettingByName(this, "AutoDisable").getValBoolean()) {
/*  93 */       setToggled(false);
/*  94 */       NotificationManager.show(NotificationType.ERROR, "Module", EnumChatFormatting.RED + "Disable " + EnumChatFormatting.WHITE + "(AutoDisable) " + getName(), 2500);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void faceTarget(Entity target, float yawspeed, float pitchspeed) {
/*  99 */     EntityPlayerSP player = mc.field_71439_g;
/* 100 */     float yaw = RotationUtils.getAngles(target)[1];
/* 101 */     float pitch = RotationUtils.getAngles(target)[0];
/* 102 */     player.field_70177_z = RotationUtils.getRotation(player.field_70177_z, yaw, yawspeed);
/* 103 */     player.field_70125_A = RotationUtils.getRotation(player.field_70125_A, pitch, pitchspeed);
/*     */   }
/*     */   
/*     */   private EntityLivingBase getClosest(double range) {
/* 107 */     double dist = range;
/* 108 */     EntityLivingBase target = null;
/* 109 */     for (Object object : mc.field_71441_e.field_72996_f) {
/* 110 */       Entity entity = (Entity)object;
/* 111 */       if (entity instanceof EntityLivingBase) {
/* 112 */         EntityLivingBase player = (EntityLivingBase)entity;
/* 113 */         if (canAttack(player)) {
/* 114 */           double currentDist = mc.field_71439_g.func_70032_d((Entity)player);
/* 115 */           if (currentDist <= dist) {
/* 116 */             dist = currentDist;
/* 117 */             target = player;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 122 */     return target;
/*     */   }
/*     */   
/*     */   private boolean canAttack(EntityLivingBase player) {
/* 126 */     if (player instanceof net.minecraft.entity.passive.EntityVillager || player instanceof net.minecraft.entity.item.EntityArmorStand || player instanceof net.minecraft.entity.passive.EntityAnimal || player instanceof net.minecraft.entity.monster.EntityMob || player
/* 127 */       .func_145748_c_().func_150254_d().contains("[NPC]") || player.func_70005_c_().contains("#") || player.func_70005_c_().toLowerCase().contains("shop"))
/* 128 */       return false; 
/* 129 */     if (Nightmare.instance.settingsManager.getSettingByName(this, "Teams").getValBoolean() && player.func_142014_c((EntityLivingBase)mc.field_71439_g))
/* 130 */       return false; 
/* 131 */     if (player.func_82150_aj() && !Nightmare.instance.settingsManager.getSettingByName(this, "Invisibles").getValBoolean())
/* 132 */       return false; 
/* 133 */     if (!isInFOV(player, Nightmare.instance.settingsManager.getSettingByName(this, "FOV").getValDouble()))
/* 134 */       return false; 
/* 135 */     return (player != mc.field_71439_g && player.func_70089_S() && mc.field_71439_g.func_70032_d((Entity)player) <= Nightmare.instance.settingsManager.getSettingByName(this, "Range").getValDouble());
/*     */   }
/*     */   
/*     */   private boolean isInFOV(EntityLivingBase entity, double angle) {
/* 139 */     angle *= 0.5D;
/* 140 */     double angleDiff = getAngleDifference(mc.field_71439_g.field_70177_z, getRotations(entity.field_70165_t, entity.field_70163_u, entity.field_70161_v)[0]);
/* 141 */     return ((angleDiff > 0.0D && angleDiff < angle) || (-angle < angleDiff && angleDiff < 0.0D));
/*     */   }
/*     */   
/*     */   private float getAngleDifference(float dir, float yaw) {
/* 145 */     float f = Math.abs(yaw - dir) % 360.0F;
/* 146 */     float dist = (f > 180.0F) ? (360.0F - f) : f;
/* 147 */     return dist;
/*     */   }
/*     */   
/*     */   private float[] getRotations(double x, double y, double z) {
/* 151 */     double diffX = x + 0.5D - mc.field_71439_g.field_70165_t;
/* 152 */     double diffY = (y + 0.5D) / 2.0D - mc.field_71439_g.field_70163_u + mc.field_71439_g.func_70047_e();
/* 153 */     double diffZ = z + 0.5D - mc.field_71439_g.field_70161_v;
/*     */     
/* 155 */     double dist = MathHelper.func_76133_a(diffX * diffX + diffZ * diffZ);
/* 156 */     float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0D / Math.PI) - 90.0F;
/* 157 */     float pitch = (float)-(Math.atan2(diffY, dist) * 180.0D / Math.PI);
/*     */     
/* 159 */     return new float[] { yaw, pitch };
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\module\combat\LegitAura.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */