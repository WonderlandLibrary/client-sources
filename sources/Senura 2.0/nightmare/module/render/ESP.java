/*    */ package nightmare.module.render;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import nightmare.Nightmare;
/*    */ import nightmare.event.EventTarget;
/*    */ import nightmare.event.impl.EventRender3D;
/*    */ import nightmare.mixin.mixins.accessor.RenderManagerAccessor;
/*    */ import nightmare.module.Category;
/*    */ import nightmare.module.Module;
/*    */ import nightmare.settings.Setting;
/*    */ import nightmare.utils.ColorUtils;
/*    */ import nightmare.utils.render.Render3DUtils;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ESP
/*    */   extends Module
/*    */ {
/*    */   public ESP() {
/* 21 */     super("ESP", 0, Category.RENDER);
/*    */     
/* 23 */     Nightmare.instance.settingsManager.rSetting(new Setting("Animals", this, false));
/* 24 */     Nightmare.instance.settingsManager.rSetting(new Setting("Mobs", this, false));
/* 25 */     Nightmare.instance.settingsManager.rSetting(new Setting("Players", this, true));
/* 26 */     Nightmare.instance.settingsManager.rSetting(new Setting("Villagers", this, true));
/* 27 */     Nightmare.instance.settingsManager.rSetting(new Setting("LineWidth", this, 2.0D, 0.5D, 5.0D, false));
/*    */   }
/*    */   
/*    */   @EventTarget
/*    */   public void onRender3D(EventRender3D event) {
/* 32 */     for (Entity entity : mc.field_71441_e.field_72996_f) {
/*    */       
/* 34 */       double posX = entity.field_70142_S + (entity.field_70165_t - entity.field_70142_S) * event.getPartialTicks() - ((RenderManagerAccessor)mc.func_175598_ae()).getRenderPosX();
/* 35 */       double posY = entity.field_70137_T + (entity.field_70163_u - entity.field_70137_T) * event.getPartialTicks() - ((RenderManagerAccessor)mc.func_175598_ae()).getRenderPosY();
/* 36 */       double posZ = entity.field_70136_U + (entity.field_70161_v - entity.field_70136_U) * event.getPartialTicks() - ((RenderManagerAccessor)mc.func_175598_ae()).getRenderPosZ();
/*    */       
/* 38 */       if (entity == mc.field_71439_g) {
/*    */         continue;
/*    */       }
/*    */       
/* 42 */       if (isValid(entity)) {
/* 43 */         Render3DUtils.drawBox(posX, posY, posZ, 0.4D, (entity.func_174813_aQ()).field_72337_e - (entity.func_174813_aQ()).field_72338_b, (float)Nightmare.instance.settingsManager.getSettingByName(this, "LineWidth").getValDouble(), ColorUtils.getClientColorRaw().getRed(), ColorUtils.getClientColorRaw().getGreen(), ColorUtils.getClientColorRaw().getBlue(), 255.0F);
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private boolean isValid(Entity entity) {
/* 50 */     if (Nightmare.instance.settingsManager.getSettingByName(this, "Animals").getValBoolean() && entity instanceof net.minecraft.entity.passive.EntityAnimal) {
/* 51 */       return true;
/*    */     }
/*    */     
/* 54 */     if (Nightmare.instance.settingsManager.getSettingByName(this, "Mobs").getValBoolean() && entity instanceof net.minecraft.entity.monster.EntityMob) {
/* 55 */       return true;
/*    */     }
/*    */     
/* 58 */     if (Nightmare.instance.settingsManager.getSettingByName(this, "Players").getValBoolean() && entity instanceof net.minecraft.entity.player.EntityPlayer) {
/* 59 */       return true;
/*    */     }
/*    */     
/* 62 */     if (Nightmare.instance.settingsManager.getSettingByName(this, "Villagers").getValBoolean() && entity instanceof net.minecraft.entity.passive.EntityVillager) {
/* 63 */       return true;
/*    */     }
/*    */     
/* 66 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\module\render\ESP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */