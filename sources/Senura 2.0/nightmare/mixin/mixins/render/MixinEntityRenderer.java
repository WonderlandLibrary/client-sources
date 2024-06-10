/*     */ package nightmare.mixin.mixins.render;
/*     */ 
/*     */ import com.google.common.base.Predicates;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.EntityRenderer;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.shader.ShaderGroup;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EntitySelectors;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.Vec3;
/*     */ import nightmare.Nightmare;
/*     */ import nightmare.event.impl.EventRender3D;
/*     */ import nightmare.module.combat.Reach;
/*     */ import nightmare.utils.motionblur.MotionBlurUtils;
/*     */ import org.spongepowered.asm.mixin.Mixin;
/*     */ import org.spongepowered.asm.mixin.Overwrite;
/*     */ import org.spongepowered.asm.mixin.Shadow;
/*     */ import org.spongepowered.asm.mixin.injection.At;
/*     */ import org.spongepowered.asm.mixin.injection.Inject;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
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
/*     */ @Mixin({EntityRenderer.class})
/*     */ public class MixinEntityRenderer
/*     */ {
/*     */   @Shadow
/*     */   private Entity field_78528_u;
/*     */   @Shadow
/*     */   private Minecraft field_78531_r;
/*     */   @Shadow
/*     */   private ShaderGroup field_147707_d;
/*     */   @Shadow
/*     */   private boolean field_175083_ad;
/*     */   
/*     */   @Inject(method = {"updateCameraAndRender"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/shader/Framebuffer;bindFramebuffer(Z)V", shift = At.Shift.BEFORE)})
/*     */   public void updateCameraAndRender(float partialTicks, long nanoTime, CallbackInfo ci) {
/*  51 */     List<ShaderGroup> shaders = new ArrayList<>();
/*     */     
/*  53 */     if (this.field_147707_d != null && this.field_175083_ad)
/*     */     {
/*  55 */       shaders.add(this.field_147707_d);
/*     */     }
/*     */     
/*  58 */     ShaderGroup motionBlur = MotionBlurUtils.instance.getShader();
/*     */     
/*  60 */     if (Nightmare.instance.moduleManager.getModuleByName("MotionBlur").isToggled()) {
/*  61 */       if (motionBlur != null)
/*     */       {
/*  63 */         shaders.add(motionBlur);
/*     */       }
/*     */       
/*  66 */       for (ShaderGroup shader : shaders) {
/*     */         
/*  68 */         GlStateManager.func_179094_E();
/*  69 */         GlStateManager.func_179096_D();
/*  70 */         shader.func_148018_a(partialTicks);
/*  71 */         GlStateManager.func_179121_F();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Inject(method = {"updateShaderGroupSize"}, at = {@At("RETURN")})
/*     */   public void updateShaderGroupSize(int width, int height, CallbackInfo ci) {
/*  79 */     if (this.field_78531_r.field_71441_e == null) {
/*     */       return;
/*     */     }
/*     */     
/*  83 */     if (OpenGlHelper.field_148824_g) {
/*  84 */       ShaderGroup motionBlur = MotionBlurUtils.instance.getShader();
/*     */       
/*  86 */       if (motionBlur != null)
/*     */       {
/*  88 */         motionBlur.func_148026_a(width, height);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   @Overwrite
/*     */   public void func_78473_a(float p_getMouseOver_1_) {
/*  95 */     Entity entity = this.field_78531_r.func_175606_aa();
/*  96 */     if (entity != null && this.field_78531_r.field_71441_e != null) {
/*  97 */       this.field_78531_r.field_71424_I.func_76320_a("pick");
/*  98 */       this.field_78531_r.field_147125_j = null;
/*     */       
/* 100 */       double d0 = Nightmare.instance.moduleManager.getModuleByName("Reach").isToggled() ? Reach.getMaxRange() : this.field_78531_r.field_71442_b.func_78757_d();
/* 101 */       this.field_78531_r.field_71476_x = entity.func_174822_a(Nightmare.instance.moduleManager.getModuleByName("Reach").isToggled() ? this.field_78531_r.field_71442_b.func_78757_d() : d0, p_getMouseOver_1_);
/* 102 */       double d1 = d0;
/* 103 */       Vec3 vec3 = entity.func_174824_e(p_getMouseOver_1_);
/* 104 */       boolean flag = false;
/* 105 */       if (this.field_78531_r.field_71442_b.func_78749_i()) {
/* 106 */         d0 = 6.0D;
/* 107 */         d1 = 6.0D;
/* 108 */       } else if (d0 > 3.0D) {
/* 109 */         flag = true;
/*     */       } 
/*     */       
/* 112 */       if (this.field_78531_r.field_71476_x != null) {
/* 113 */         d1 = this.field_78531_r.field_71476_x.field_72307_f.func_72438_d(vec3);
/*     */       }
/*     */       
/* 116 */       if (Nightmare.instance.moduleManager.getModuleByName("Reach").isToggled()) {
/*     */         
/* 118 */         d1 = Nightmare.instance.settingsManager.getSettingByName(Nightmare.instance.moduleManager.getModuleByName("Reach"), "Range").getValDouble();
/*     */         
/* 120 */         MovingObjectPosition movingObjectPosition = entity.func_174822_a(d1, p_getMouseOver_1_);
/*     */         
/* 122 */         if (movingObjectPosition != null) d1 = movingObjectPosition.field_72307_f.func_72438_d(vec3);
/*     */       
/*     */       } 
/* 125 */       Vec3 vec31 = entity.func_70676_i(p_getMouseOver_1_);
/* 126 */       Vec3 vec32 = vec3.func_72441_c(vec31.field_72450_a * d0, vec31.field_72448_b * d0, vec31.field_72449_c * d0);
/* 127 */       this.field_78528_u = null;
/* 128 */       Vec3 vec33 = null;
/* 129 */       float f = 1.0F;
/* 130 */       List<Entity> list = this.field_78531_r.field_71441_e.func_175674_a(entity, entity.func_174813_aQ().func_72321_a(vec31.field_72450_a * d0, vec31.field_72448_b * d0, vec31.field_72449_c * d0).func_72314_b(f, f, f), Predicates.and(EntitySelectors.field_180132_d, Entity::func_70067_L));
/* 131 */       double d2 = d1;
/*     */       
/* 133 */       for (Entity entity1 : list) {
/* 134 */         float f1 = entity1.func_70111_Y();
/* 135 */         AxisAlignedBB axisalignedbb = entity1.func_174813_aQ().func_72314_b(f1, f1, f1);
/* 136 */         MovingObjectPosition movingobjectposition = axisalignedbb.func_72327_a(vec3, vec32);
/* 137 */         if (axisalignedbb.func_72318_a(vec3)) {
/* 138 */           if (d2 >= 0.0D) {
/* 139 */             this.field_78528_u = entity1;
/* 140 */             vec33 = (movingobjectposition == null) ? vec3 : movingobjectposition.field_72307_f;
/* 141 */             d2 = 0.0D;
/*     */           }  continue;
/* 143 */         }  if (movingobjectposition != null) {
/* 144 */           double d3 = vec3.func_72438_d(movingobjectposition.field_72307_f);
/* 145 */           if (d3 < d2 || d2 == 0.0D) {
/* 146 */             if (entity1 == entity.field_70154_o && !entity.canRiderInteract()) {
/* 147 */               if (d2 == 0.0D) {
/* 148 */                 this.field_78528_u = entity1;
/* 149 */                 vec33 = movingobjectposition.field_72307_f;
/*     */               }  continue;
/*     */             } 
/* 152 */             this.field_78528_u = entity1;
/* 153 */             vec33 = movingobjectposition.field_72307_f;
/* 154 */             d2 = d3;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 160 */       if (this.field_78528_u != null && flag && vec3.func_72438_d(vec33) > (Nightmare.instance.moduleManager.getModuleByName("Reach").isToggled() ? Nightmare.instance.settingsManager.getSettingByName(Nightmare.instance.moduleManager.getModuleByName("Reach"), "Range").getValDouble() : 3.0D)) {
/* 161 */         this.field_78528_u = null;
/* 162 */         this.field_78531_r.field_71476_x = new MovingObjectPosition(MovingObjectPosition.MovingObjectType.MISS, vec33, null, new BlockPos(vec33));
/*     */       } 
/*     */       
/* 165 */       if (this.field_78528_u != null && (d2 < d1 || this.field_78531_r.field_71476_x == null)) {
/* 166 */         this.field_78531_r.field_71476_x = new MovingObjectPosition(this.field_78528_u, vec33);
/* 167 */         if (this.field_78528_u instanceof net.minecraft.entity.EntityLivingBase || this.field_78528_u instanceof net.minecraft.entity.item.EntityItemFrame) {
/* 168 */           this.field_78531_r.field_147125_j = this.field_78528_u;
/*     */         }
/*     */       } 
/*     */       
/* 172 */       this.field_78531_r.field_71424_I.func_76319_b();
/*     */     } 
/*     */   }
/*     */   
/*     */   @Inject(method = {"renderWorldPass"}, at = {@At(value = "FIELD", target = "Lnet/minecraft/client/renderer/EntityRenderer;renderHand:Z", shift = At.Shift.BEFORE)})
/*     */   private void renderWorldPass(int pass, float partialTicks, long finishTimeNano, CallbackInfo ci) {
/* 178 */     EventRender3D event = new EventRender3D(partialTicks);
/* 179 */     event.call();
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\mixin\mixins\render\MixinEntityRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */