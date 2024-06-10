/*    */ package nightmare.module.combat;
/*    */ 
/*    */ import java.util.concurrent.ThreadLocalRandom;
/*    */ import net.minecraft.client.settings.KeyBinding;
/*    */ import nightmare.Nightmare;
/*    */ import nightmare.event.EventTarget;
/*    */ import nightmare.event.impl.EventTick;
/*    */ import nightmare.event.impl.EventUpdate;
/*    */ import nightmare.module.Category;
/*    */ import nightmare.module.Module;
/*    */ import nightmare.settings.Setting;
/*    */ import nightmare.utils.TimerUtils;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AutoClicker
/*    */   extends Module
/*    */ {
/* 19 */   private TimerUtils timer = new TimerUtils();
/*    */   
/*    */   public AutoClicker() {
/* 22 */     super("AutoClicker", 0, Category.COMBAT);
/*    */     
/* 24 */     Nightmare.instance.settingsManager.rSetting(new Setting("MinCPS", this, 12.0D, 1.0D, 20.0D, false));
/* 25 */     Nightmare.instance.settingsManager.rSetting(new Setting("MaxCPS", this, 15.0D, 1.0D, 20.0D, false));
/* 26 */     Nightmare.instance.settingsManager.rSetting(new Setting("Weapons Only", this, false));
/* 27 */     Nightmare.instance.settingsManager.rSetting(new Setting("JitterAim", this, false));
/*    */   }
/*    */   
/*    */   @EventTarget
/*    */   public void onTick(EventTick event) {
/* 32 */     if (mc.field_71474_y.field_74312_F.func_151470_d() && (!Nightmare.instance.settingsManager.getSettingByName(this, "Weapons Only").getValBoolean() || (mc.field_71439_g.func_70694_bm() != null && (mc.field_71439_g.func_70694_bm().func_77973_b() instanceof net.minecraft.item.ItemTool || mc.field_71439_g.func_70694_bm().func_77973_b() instanceof net.minecraft.item.ItemSword))) && 
/* 33 */       this.timer.delay((1000 / ThreadLocalRandom.current().nextInt((int)Nightmare.instance.settingsManager.getSettingByName(this, "MinCPS").getValDouble(), (int)Nightmare.instance.settingsManager.getSettingByName(this, "MaxCPS").getValDouble() + 1)))) {
/* 34 */       KeyBinding.func_74507_a(mc.field_71474_y.field_74312_F.func_151463_i());
/* 35 */       this.timer.reset();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @EventTarget
/*    */   public void onUpdate(EventUpdate event) {
/* 43 */     if (mc.field_71439_g == null) {
/*    */       return;
/*    */     }
/*    */     
/* 47 */     if (Nightmare.instance.settingsManager.getSettingByName(this, "JitterAim").getValBoolean() && 
/* 48 */       mc.field_71474_y.field_74312_F.func_151470_d() && (!Nightmare.instance.settingsManager.getSettingByName(this, "Weapons Only").getValBoolean() || (mc.field_71439_g.func_70694_bm() != null && (mc.field_71439_g.func_70694_bm().func_77973_b() instanceof net.minecraft.item.ItemTool || mc.field_71439_g.func_70694_bm().func_77973_b() instanceof net.minecraft.item.ItemSword))))
/* 49 */       mc.field_71439_g.field_70125_A += ThreadLocalRandom.current().nextInt(-1, 2); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\module\combat\AutoClicker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */