/*    */ package nightmare.module.combat;
/*    */ 
/*    */ import java.util.concurrent.ThreadLocalRandom;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import nightmare.Nightmare;
/*    */ import nightmare.event.EventTarget;
/*    */ import nightmare.event.impl.EventUpdate;
/*    */ import nightmare.module.Category;
/*    */ import nightmare.module.Module;
/*    */ import nightmare.settings.Setting;
/*    */ import nightmare.utils.TimerUtils;
/*    */ 
/*    */ 
/*    */ public class TriggerBot
/*    */   extends Module
/*    */ {
/* 17 */   private TimerUtils timer = new TimerUtils();
/*    */   
/*    */   public TriggerBot() {
/* 20 */     super("TriggerBot", 0, Category.COMBAT);
/*    */     
/* 22 */     Nightmare.instance.settingsManager.rSetting(new Setting("MinCPS", this, 12.0D, 1.0D, 20.0D, false));
/* 23 */     Nightmare.instance.settingsManager.rSetting(new Setting("MaxCPS", this, 15.0D, 1.0D, 20.0D, false));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @EventTarget
/*    */   public void onUpdate(EventUpdate event) {
/* 30 */     if (!(mc.field_71462_r instanceof net.minecraft.client.gui.Gui) && 
/* 31 */       this.timer.delay((1000 / ThreadLocalRandom.current().nextInt((int)Nightmare.instance.settingsManager.getSettingByName(this, "MinCPS").getValDouble(), (int)Nightmare.instance.settingsManager.getSettingByName(this, "MaxCPS").getValDouble() + 1))) && 
/* 32 */       mc.field_71476_x != null && mc.field_71476_x != null && mc.field_71476_x.field_72308_g instanceof net.minecraft.entity.EntityLivingBase) {
/* 33 */       mc.field_71439_g.func_71038_i();
/* 34 */       mc.field_71442_b.func_78764_a((EntityPlayer)mc.field_71439_g, mc.field_71476_x.field_72308_g);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\module\combat\TriggerBot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */