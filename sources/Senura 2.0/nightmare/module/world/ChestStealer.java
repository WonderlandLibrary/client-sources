/*    */ package nightmare.module.world;
/*    */ 
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.ContainerChest;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import nightmare.Nightmare;
/*    */ import nightmare.event.EventTarget;
/*    */ import nightmare.event.impl.EventTick;
/*    */ import nightmare.event.impl.EventUpdate;
/*    */ import nightmare.module.Category;
/*    */ import nightmare.module.Module;
/*    */ import nightmare.settings.Setting;
/*    */ import nightmare.utils.TimerUtils;
/*    */ 
/*    */ public class ChestStealer extends Module {
/* 16 */   private TimerUtils timer = new TimerUtils();
/*    */   
/*    */   public ChestStealer() {
/* 19 */     super("ChestStealer", 0, Category.WORLD);
/*    */     
/* 21 */     Nightmare.instance.settingsManager.rSetting(new Setting("Delay", this, 50.0D, 0.0D, 1000.0D, false));
/*    */   }
/*    */   
/*    */   @EventTarget
/*    */   public void onUpdate(EventUpdate event) {
/* 26 */     if (!isToggled()) {
/*    */       return;
/*    */     }
/* 29 */     if (mc.field_71439_g.field_71070_bA != null && mc.field_71439_g.field_71070_bA instanceof ContainerChest) {
/* 30 */       ContainerChest chest = (ContainerChest)mc.field_71439_g.field_71070_bA;
/* 31 */       for (int i = 0; i < chest.func_85151_d().func_70302_i_(); i++) {
/* 32 */         if (chest.func_85151_d().func_70301_a(i) != null && this.timer.hasReached(Nightmare.instance.settingsManager.getSettingByName(this, "Delay").getValDouble())) {
/* 33 */           mc.field_71442_b.func_78753_a(chest.field_75152_c, i, 0, 1, (EntityPlayer)mc.field_71439_g);
/* 34 */           this.timer.reset();
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   @EventTarget
/*    */   private void onTick(EventTick event) {
/* 42 */     if (mc.field_71441_e == null || isFullInv()) {
/*    */       return;
/*    */     }
/*    */   }
/*    */   
/*    */   public static boolean isFullInv() {
/* 48 */     for (int i = 9; i < 45; i++) {
/* 49 */       ItemStack itemStack = mc.field_71439_g.field_71069_bz.func_75139_a(i).func_75211_c();
/* 50 */       if (itemStack == null || itemStack.func_77977_a().contains("air")) {
/* 51 */         return false;
/*    */       }
/*    */     } 
/* 54 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\module\world\ChestStealer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */