/*     */ package nightmare.module.combat;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.stream.Collector;
/*     */ import java.util.stream.Collectors;
/*     */ import java.util.stream.Stream;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import nightmare.Nightmare;
/*     */ import nightmare.event.EventTarget;
/*     */ import nightmare.event.impl.EventTick;
/*     */ import nightmare.module.Category;
/*     */ import nightmare.module.Module;
/*     */ import nightmare.settings.Setting;
/*     */ import nightmare.utils.RotationUtils;
/*     */ import nightmare.utils.WorldUtils;
/*     */ 
/*     */ public class BowAimAssist
/*     */   extends Module
/*     */ {
/*     */   public EntityLivingBase target;
/*     */   
/*     */   public BowAimAssist() {
/*  29 */     super("BowAimAssist", 0, Category.COMBAT);
/*     */     
/*  31 */     ArrayList<String> options = new ArrayList<>();
/*     */     
/*  33 */     options.add("Angle");
/*  34 */     options.add("Range");
/*     */     
/*  36 */     Nightmare.instance.settingsManager.rSetting(new Setting("FOV", this, 180.0D, 10.0D, 360.0D, false));
/*  37 */     Nightmare.instance.settingsManager.rSetting(new Setting("Range", this, 100.0D, 1.0D, 200.0D, false));
/*  38 */     Nightmare.instance.settingsManager.rSetting(new Setting("Speed", this, 4.2D, 0.0D, 6.0D, false));
/*  39 */     Nightmare.instance.settingsManager.rSetting(new Setting("Mode", this, "Angle", options));
/*     */   }
/*     */ 
/*     */   
/*     */   @EventTarget
/*     */   public void onTick(EventTick event) {
/*  45 */     if (Nightmare.instance.moduleManager.getModuleByName("Blink").isToggled() || Nightmare.instance.moduleManager.getModuleByName("Freecam").isToggled()) {
/*     */       return;
/*     */     }
/*     */     
/*  49 */     if (mc.field_71474_y.field_74313_G.func_151470_d() && mc.field_71439_g.func_70694_bm() != null && mc.field_71439_g.func_70694_bm().func_77973_b() instanceof net.minecraft.item.ItemBow) {
/*  50 */       this.target = getTarget();
/*     */     } else {
/*  52 */       this.target = null;
/*     */     } 
/*     */     
/*  55 */     if (this.target == null) {
/*     */       return;
/*     */     }
/*     */     
/*  59 */     faceTarget((Entity)this.target, (float)Nightmare.instance.settingsManager.getSettingByName(this, "Speed").getValDouble());
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisable() {
/*  64 */     this.target = null;
/*  65 */     super.onDisable();
/*     */   }
/*     */ 
/*     */   
/*     */   protected float getRotation(float currentRotation, float targetRotation, float maxIncrement) {
/*  70 */     float deltaAngle = MathHelper.func_76142_g(targetRotation - currentRotation);
/*  71 */     if (deltaAngle > maxIncrement) {
/*  72 */       deltaAngle = maxIncrement;
/*     */     }
/*  74 */     if (deltaAngle < -maxIncrement) {
/*  75 */       deltaAngle = -maxIncrement;
/*     */     }
/*  77 */     return currentRotation + deltaAngle / 2.0F;
/*     */   }
/*     */   
/*     */   private void faceTarget(Entity target, float speed) {
/*  81 */     EntityPlayerSP player = mc.field_71439_g;
/*  82 */     float[] rotation = getPlayerRotations(target);
/*  83 */     float yaw = rotation[0];
/*  84 */     float pitch = rotation[1];
/*  85 */     player.field_70177_z = getRotation(player.field_70177_z, yaw, speed);
/*  86 */     player.field_70125_A = getRotation(player.field_70125_A, pitch, speed);
/*     */   }
/*     */   
/*     */   private float[] getPlayerRotations(Entity entity) {
/*  90 */     double distanceToEnt = mc.field_71439_g.func_70032_d(entity);
/*  91 */     double predictX = entity.field_70165_t + (entity.field_70165_t - entity.field_70142_S) * distanceToEnt * 0.8D;
/*  92 */     double predictZ = entity.field_70161_v + (entity.field_70161_v - entity.field_70136_U) * distanceToEnt * 0.8D;
/*     */     
/*  94 */     double x = predictX - mc.field_71439_g.field_70165_t;
/*  95 */     double z = predictZ - mc.field_71439_g.field_70161_v;
/*  96 */     double h = entity.field_70163_u + 1.0D - mc.field_71439_g.field_70163_u + mc.field_71439_g.func_70047_e();
/*     */     
/*  98 */     double h1 = Math.sqrt(x * x + z * z);
/*  99 */     float yaw = (float)(Math.atan2(z, x) * 180.0D / Math.PI) - 90.0F;
/*     */     
/* 101 */     float pitch = -RotationUtils.getTrajAngleSolutionLow((float)h1, (float)h, 1.0F);
/*     */     
/* 103 */     return new float[] { yaw, pitch };
/*     */   }
/*     */ 
/*     */   
/*     */   private EntityLivingBase getTarget() {
/* 108 */     String mode = Nightmare.instance.settingsManager.getSettingByName(this, "Mode").getValString();
/*     */     
/* 110 */     Stream<EntityPlayer> stream = WorldUtils.getLivingPlayers().stream().filter(mc.field_71439_g::func_70685_l).filter(e -> RotationUtils.isVisibleFOV((Entity)e, (float)Nightmare.instance.settingsManager.getSettingByName(this, "FOV").getValDouble()));
/*     */     
/* 112 */     if (mode.equals("Range")) {
/* 113 */       stream = stream.sorted(Comparator.comparingDouble(e -> e.func_70032_d((Entity)mc.field_71439_g)));
/* 114 */     } else if (mode.equals("Angle")) {
/* 115 */       stream = stream.sorted(Comparator.comparingDouble(RotationUtils::getYawToEntity));
/*     */     } 
/* 117 */     List<EntityPlayer> list = stream.collect((Collector)Collectors.toList());
/* 118 */     if (list.size() <= 0) {
/* 119 */       return null;
/*     */     }
/* 121 */     return (EntityLivingBase)list.get(0);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\module\combat\BowAimAssist.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */