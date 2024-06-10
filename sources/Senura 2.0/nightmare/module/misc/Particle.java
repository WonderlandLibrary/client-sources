/*    */ package nightmare.module.misc;
/*    */ 
/*    */ import net.minecraft.client.entity.EntityPlayerSP;
/*    */ import net.minecraft.enchantment.EnchantmentHelper;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.potion.Potion;
/*    */ import net.minecraft.util.EnumParticleTypes;
/*    */ import nightmare.Nightmare;
/*    */ import nightmare.event.EventTarget;
/*    */ import nightmare.event.impl.EventAttackEntity;
/*    */ import nightmare.event.impl.EventTick;
/*    */ import nightmare.gui.notification.NotificationManager;
/*    */ import nightmare.gui.notification.NotificationType;
/*    */ import nightmare.module.Category;
/*    */ import nightmare.module.Module;
/*    */ import nightmare.settings.Setting;
/*    */ 
/*    */ public class Particle extends Module {
/*    */   public Particle() {
/* 21 */     super("Particle", 0, Category.MISC);
/*    */     
/* 23 */     Nightmare.instance.settingsManager.rSetting(new Setting("AlwaysSharpness", this, false));
/* 24 */     Nightmare.instance.settingsManager.rSetting(new Setting("AlwaysCriticals", this, false));
/* 25 */     Nightmare.instance.settingsManager.rSetting(new Setting("Criticals", this, false));
/* 26 */     Nightmare.instance.settingsManager.rSetting(new Setting("SharpnessVal", this, 4.0D, 1.0D, 10.0D, true));
/* 27 */     Nightmare.instance.settingsManager.rSetting(new Setting("CriticalsVal", this, 4.0D, 1.0D, 10.0D, true));
/*    */   }
/*    */   
/*    */   @EventTarget
/*    */   public void onTick(EventTick event) {
/* 32 */     if (!Nightmare.instance.settingsManager.getSettingByName(this, "Criticals").getValBoolean() && Nightmare.instance.settingsManager.getSettingByName(this, "AlwaysCriticals").getValBoolean()) {
/* 33 */       Nightmare.instance.settingsManager.getSettingByName(this, "AlwaysCriticals").setValBoolean(false);
/* 34 */       NotificationManager.show(NotificationType.WARNING, "Module", "Please toggle Criticals Setting", 3000);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   @EventTarget
/*    */   public void onAttackEntity(EventAttackEntity event) {
/* 41 */     int sharpnessVal = (int)Nightmare.instance.settingsManager.getSettingByName(this, "SharpnessVal").getValDouble();
/* 42 */     int criticalsVal = (int)Nightmare.instance.settingsManager.getSettingByName(this, "CriticalsVal").getValDouble();
/*    */     
/* 44 */     EntityPlayerSP entityPlayerSP = mc.field_71439_g;
/*    */     
/* 46 */     boolean isCritical = (((EntityPlayer)entityPlayerSP).field_70143_R > 0.0F && !((EntityPlayer)entityPlayerSP).field_70122_E && !entityPlayerSP.func_70617_f_() && !entityPlayerSP.func_70090_H() && !entityPlayerSP.func_70644_a(Potion.field_76440_q) && ((EntityPlayer)entityPlayerSP).field_70154_o == null);
/*    */     
/* 48 */     if (!(event.entity instanceof EntityLivingBase)) {
/*    */       return;
/*    */     }
/*    */     
/* 52 */     if (Nightmare.instance.settingsManager.getSettingByName(this, "AlwaysSharpness").getValBoolean() || EnchantmentHelper.func_152377_a(entityPlayerSP.func_70694_bm(), ((EntityLivingBase)event.entity).func_70668_bt()) > 0.0F) {
/* 53 */       for (int i = 0; i < sharpnessVal; i++) {
/* 54 */         mc.field_71452_i.func_178926_a(event.entity, EnumParticleTypes.CRIT_MAGIC);
/*    */       }
/*    */     }
/*    */     
/* 58 */     if ((Nightmare.instance.settingsManager.getSettingByName(this, "Criticals").getValBoolean() && Nightmare.instance.settingsManager.getSettingByName(this, "AlwaysCriticals").getValBoolean()) || (Nightmare.instance.settingsManager.getSettingByName(this, "Criticals").getValBoolean() && isCritical))
/* 59 */       for (int i = 0; i < criticalsVal; i++)
/* 60 */         mc.field_71452_i.func_178926_a(event.entity, EnumParticleTypes.CRIT);  
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\module\misc\Particle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */