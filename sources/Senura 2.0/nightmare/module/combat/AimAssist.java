/*     */ package nightmare.module.combat;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.Vec3;
/*     */ import nightmare.Nightmare;
/*     */ import nightmare.event.EventTarget;
/*     */ import nightmare.event.impl.EventTick;
/*     */ import nightmare.module.Category;
/*     */ import nightmare.module.Module;
/*     */ import nightmare.settings.Setting;
/*     */ import nightmare.utils.RotationUtils;
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
/*     */ public class AimAssist
/*     */   extends Module
/*     */ {
/*  34 */   protected Random rand = new Random();
/*     */   
/*     */   public AimAssist() {
/*  37 */     super("AimAssist", 0, Category.COMBAT);
/*     */     
/*  39 */     ArrayList<String> options = new ArrayList<>();
/*     */     
/*  41 */     options.add("Head");
/*  42 */     options.add("Middle");
/*     */     
/*  44 */     Nightmare.instance.settingsManager.rSetting(new Setting("ClickAim", this, false));
/*  45 */     Nightmare.instance.settingsManager.rSetting(new Setting("BreakBlock", this, false));
/*  46 */     Nightmare.instance.settingsManager.rSetting(new Setting("HeldItem", this, false));
/*  47 */     Nightmare.instance.settingsManager.rSetting(new Setting("Horizontal", this, 4.2D, 0.0D, 6.0D, false));
/*  48 */     Nightmare.instance.settingsManager.rSetting(new Setting("Vertical", this, 2.4D, 0.0D, 6.0D, false));
/*  49 */     Nightmare.instance.settingsManager.rSetting(new Setting("Range", this, 4.2D, 1.0D, 8.0D, false));
/*  50 */     Nightmare.instance.settingsManager.rSetting(new Setting("FOV", this, 100.0D, 20.0D, 360.0D, false));
/*  51 */     Nightmare.instance.settingsManager.rSetting(new Setting("Strafe", this, false));
/*  52 */     Nightmare.instance.settingsManager.rSetting(new Setting("Team", this, false));
/*  53 */     Nightmare.instance.settingsManager.rSetting(new Setting("Mode", this, "Middle", options));
/*     */   }
/*     */ 
/*     */   
/*     */   @EventTarget
/*     */   public void onTick(EventTick event) {
/*  59 */     Entity entity = null;
/*  60 */     double maxDistance = 360.0D;
/*  61 */     double maxAngle = Nightmare.instance.settingsManager.getSettingByName(this, "FOV").getValDouble();
/*  62 */     double minAngle = 0.0D;
/*     */     
/*  64 */     if (Nightmare.instance.settingsManager.getSettingByName(this, "ClickAim").getValBoolean() && !mc.field_71474_y.field_74312_F.func_151470_d()) {
/*     */       return;
/*     */     }
/*     */     
/*  68 */     if (Nightmare.instance.settingsManager.getSettingByName(this, "BreakBlock").getValBoolean() && mc.field_71476_x != null) {
/*  69 */       BlockPos blockPos = mc.field_71476_x.func_178782_a();
/*  70 */       if (blockPos != null) {
/*  71 */         Block block = mc.field_71441_e.func_180495_p(blockPos).func_177230_c();
/*  72 */         if (block != Blocks.field_150350_a && !(block instanceof net.minecraft.block.BlockLiquid) && block instanceof Block) {
/*     */           return;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  78 */     if (Nightmare.instance.moduleManager.getModuleByName("Spin").isToggled()) {
/*     */       return;
/*     */     }
/*     */     
/*  82 */     if (mc.field_71441_e != null) {
/*  83 */       for (Object e : mc.field_71441_e.func_72910_y()) {
/*     */         
/*  85 */         Entity en = (Entity)e; double yawdistance;
/*  86 */         if (en == mc.field_71439_g || !isValid(en) || maxDistance <= (yawdistance = getDistanceBetweenAngles(RotationUtils.getAngles(en)[1], mc.field_71439_g.field_70177_z))) {
/*     */           continue;
/*     */         }
/*  89 */         entity = en;
/*  90 */         maxDistance = yawdistance;
/*     */       } 
/*     */     }
/*     */     
/*  94 */     if ((!Nightmare.instance.settingsManager.getSettingByName(this, "HeldItem").getValBoolean() || (mc.field_71439_g.func_70694_bm() != null && (mc.field_71439_g.func_70694_bm().func_77973_b() instanceof net.minecraft.item.ItemAxe || mc.field_71439_g.func_70694_bm().func_77973_b() instanceof net.minecraft.item.ItemSword))) && 
/*  95 */       entity != null) {
/*     */       
/*  97 */       double horizontalSpeed = Nightmare.instance.settingsManager.getSettingByName(this, "Horizontal").getValDouble() * 3.0D + ((Nightmare.instance.settingsManager.getSettingByName(this, "Horizontal").getValDouble() > 0.0D) ? this.rand.nextDouble() : 0.0D);
/*  98 */       double verticalSpeed = Nightmare.instance.settingsManager.getSettingByName(this, "Vertical").getValDouble() * 3.0D + ((Nightmare.instance.settingsManager.getSettingByName(this, "Vertical").getValDouble() > 0.0D) ? this.rand.nextDouble() : 0.0D);
/*  99 */       float yaw = RotationUtils.getAngles(entity)[1];
/* 100 */       float pitch = RotationUtils.getAngles(entity)[0];
/* 101 */       double yawdistance = getDistanceBetweenAngles(yaw, mc.field_71439_g.field_70177_z);
/* 102 */       double pitchdistance = getDistanceBetweenAngles(pitch, mc.field_71439_g.field_70125_A);
/* 103 */       if (pitchdistance <= maxAngle && yawdistance >= minAngle && yawdistance <= maxAngle) {
/*     */         
/* 105 */         if (Nightmare.instance.settingsManager.getSettingByName(this, "Strafe").getValBoolean() && mc.field_71439_g.field_70702_br != 0.0F) {
/* 106 */           horizontalSpeed *= 1.25D;
/*     */         }
/*     */         
/* 109 */         if (getEntity(24.0D) != null && getEntity(24.0D).equals(entity)) {
/* 110 */           horizontalSpeed *= 1.0D;
/* 111 */           verticalSpeed *= 1.0D;
/*     */         } 
/* 113 */         faceTarget(entity, (float)horizontalSpeed, (float)verticalSpeed);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static double getDistanceBetweenAngles(float angle1, float angle2) {
/* 120 */     float distance = Math.abs(angle1 - angle2) % 360.0F;
/* 121 */     if (distance > 180.0F) {
/* 122 */       distance = 360.0F - distance;
/*     */     }
/* 124 */     return distance;
/*     */   }
/*     */   
/*     */   private void faceTarget(Entity target, float yawspeed, float pitchspeed) {
/* 128 */     EntityPlayerSP player = mc.field_71439_g;
/* 129 */     float yaw = RotationUtils.getAngles(target)[1];
/* 130 */     float pitch = RotationUtils.getAngles(target)[0];
/* 131 */     String mode = Nightmare.instance.settingsManager.getSettingByName(this, "Mode").getValString();
/*     */     
/* 133 */     player.field_70177_z = RotationUtils.getRotation(player.field_70177_z, yaw, yawspeed);
/*     */     
/* 135 */     if (mode.equals("Head")) {
/* 136 */       player.field_70125_A = RotationUtils.getRotation(player.field_70125_A, pitch - 12.0F, pitchspeed);
/*     */     } else {
/* 138 */       player.field_70125_A = RotationUtils.getRotation(player.field_70125_A, pitch, pitchspeed);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static Entity getEntity(double distance) {
/* 143 */     if (getEntity(distance, 0.0D, 0.0F) == null) {
/* 144 */       return null;
/*     */     }
/* 146 */     return (Entity)getEntity(distance, 0.0D, 0.0F)[0];
/*     */   }
/*     */   
/*     */   public static Object[] getEntity(double distance, double expand, float partialTicks) {
/* 150 */     Entity entityView = mc.func_175606_aa();
/* 151 */     Entity entity = null;
/* 152 */     if (entityView != null && mc.field_71441_e != null) {
/*     */       
/* 154 */       mc.field_71424_I.func_76320_a("pick");
/* 155 */       double var3 = distance, var5 = var3;
/* 156 */       Vec3 var7 = entityView.func_174824_e(0.0F);
/* 157 */       Vec3 var8 = entityView.func_70676_i(0.0F);
/* 158 */       Vec3 var9 = var7.func_72441_c(var8.field_72450_a * var3, var8.field_72448_b * var3, var8.field_72449_c * var3);
/* 159 */       Vec3 var10 = null;
/* 160 */       float var11 = 1.0F;
/* 161 */       List<?> var12 = mc.field_71441_e.func_72839_b(entityView, entityView.func_174813_aQ().func_72321_a(var8.field_72450_a * var3, var8.field_72448_b * var3, var8.field_72449_c * var3).func_72314_b(var11, var11, var11));
/* 162 */       double var13 = var5;
/* 163 */       for (int var15 = 0; var15 < var12.size(); var15++) {
/*     */         
/* 165 */         Entity var16 = (Entity)var12.get(var15);
/* 166 */         if (var16.func_70067_L()) {
/*     */           
/* 168 */           float var17 = var16.func_70111_Y();
/* 169 */           AxisAlignedBB var18 = var16.func_174813_aQ().func_72314_b(var17, var17, var17);
/* 170 */           var18 = var18.func_72314_b(expand, expand, expand);
/* 171 */           MovingObjectPosition var19 = var18.func_72327_a(var7, var9);
/* 172 */           if (var18.func_72318_a(var7))
/* 173 */           { if (0.0D < var13 || var13 == 0.0D) {
/*     */               
/* 175 */               entity = var16;
/* 176 */               var10 = (var19 == null) ? var7 : var19.field_72307_f;
/* 177 */               var13 = 0.0D;
/*     */             }  }
/*     */           else
/* 180 */           { double var20; if (var19 != null && ((var20 = var7.func_72438_d(var19.field_72307_f)) < var13 || var13 == 0.0D))
/*     */             
/* 182 */             { boolean canRiderInteract = false;
/* 183 */               if (var16 == entityView.field_70154_o && !canRiderInteract)
/* 184 */               { if (var13 == 0.0D) {
/*     */                   
/* 186 */                   entity = var16;
/* 187 */                   var10 = var19.field_72307_f;
/*     */                 }  }
/*     */               else
/* 190 */               { entity = var16;
/* 191 */                 var10 = var19.field_72307_f;
/* 192 */                 var13 = var20; }  }  } 
/*     */         } 
/* 194 */       }  if (var13 < var5 && !(entity instanceof net.minecraft.entity.EntityLivingBase) && !(entity instanceof net.minecraft.entity.item.EntityItemFrame)) {
/* 195 */         entity = null;
/*     */       }
/* 197 */       mc.field_71424_I.func_76319_b();
/* 198 */       if (entity == null || var10 == null) {
/* 199 */         return null;
/*     */       }
/* 201 */       return new Object[] { entity, var10 };
/*     */     } 
/* 203 */     return null;
/*     */   }
/*     */   
/*     */   public boolean isValid(Entity e) {
/* 207 */     return !(e instanceof net.minecraft.entity.EntityLivingBase) ? false : ((e instanceof net.minecraft.entity.item.EntityArmorStand) ? false : ((e instanceof net.minecraft.entity.passive.EntityAnimal) ? false : ((e instanceof net.minecraft.entity.monster.EntityMob) ? false : (e.func_145748_c_().func_150254_d().contains("[NPC]") ? false : ((e == mc.field_71439_g) ? false : ((e instanceof net.minecraft.entity.passive.EntityVillager) ? false : ((mc.field_71439_g.func_70032_d(e) > Nightmare.instance.settingsManager.getSettingByName(this, "Range").getValDouble()) ? false : (e.func_70005_c_().contains("#") ? false : ((Nightmare.instance.settingsManager.getSettingByName(this, "Team").getValBoolean() && e.func_145748_c_().func_150254_d().startsWith("§" + mc.field_71439_g.func_145748_c_().func_150254_d().charAt(1))) ? false : (!e.func_70005_c_().toLowerCase().contains("shop")))))))))));
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\module\combat\AimAssist.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */