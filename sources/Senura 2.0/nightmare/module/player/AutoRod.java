/*    */ package nightmare.module.player;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.world.World;
/*    */ import nightmare.Nightmare;
/*    */ import nightmare.event.EventTarget;
/*    */ import nightmare.event.impl.EventUpdate;
/*    */ import nightmare.module.Category;
/*    */ import nightmare.module.Module;
/*    */ import nightmare.settings.Setting;
/*    */ import nightmare.utils.TimerUtils;
/*    */ 
/*    */ public class AutoRod extends Module {
/* 16 */   private final TimerUtils timer = new TimerUtils();
/* 17 */   private final TimerUtils timer2 = new TimerUtils();
/*    */   
/*    */   private int oldCurrentItem;
/*    */   
/*    */   public AutoRod() {
/* 22 */     super("AutoRod", 0, Category.PLAYER);
/*    */     
/* 24 */     Nightmare.instance.settingsManager.rSetting(new Setting("Delay", this, 100.0D, 50.0D, 1000.0D, false));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onEnable() {
/* 30 */     if (mc.field_71439_g == null || mc.field_71441_e == null) {
/*    */       return;
/*    */     }
/*    */     
/* 34 */     this.oldCurrentItem = mc.field_71439_g.field_71071_by.field_70461_c;
/* 35 */     super.onEnable();
/*    */   }
/*    */ 
/*    */   
/*    */   @EventTarget
/*    */   private void onUpdate(EventUpdate event) {
/* 41 */     int item = (mc.field_71439_g.func_70694_bm() != null) ? Item.func_150891_b(mc.field_71439_g.func_70694_bm().func_77973_b()) : 0;
/* 42 */     float rodDelay = (float)Nightmare.instance.settingsManager.getSettingByName(this, "Delay").getValDouble();
/*    */     
/* 44 */     if (mc.field_71462_r != null) {
/*    */       return;
/*    */     }
/*    */     
/* 48 */     if (item == 346) {
/* 49 */       if (this.timer2.delay((rodDelay + 200.0F))) {
/* 50 */         Rod();
/* 51 */         this.timer2.reset();
/*    */       } 
/*    */       
/* 54 */       if (this.timer.delay(rodDelay)) {
/* 55 */         mc.field_71439_g.field_71071_by.field_70461_c = this.oldCurrentItem;
/* 56 */         this.timer.reset();
/* 57 */         toggle();
/*    */       } 
/* 59 */     } else if (this.timer.delay(100.0D)) {
/* 60 */       switchToRod();
/* 61 */       this.timer.reset();
/*    */     } 
/*    */   }
/*    */   
/*    */   private int findRod(int startSlot, int endSlot) {
/* 66 */     for (int i = startSlot; i < endSlot; i++) {
/* 67 */       ItemStack stack = mc.field_71439_g.field_71069_bz.func_75139_a(i).func_75211_c();
/*    */       
/* 69 */       if (stack != null && stack.func_77973_b() == Items.field_151112_aM) {
/* 70 */         return i;
/*    */       }
/*    */     } 
/*    */     
/* 74 */     return -1;
/*    */   }
/*    */   
/*    */   private void switchToRod() {
/* 78 */     for (int i = 36; i < 45; i++) {
/* 79 */       ItemStack itemstack = mc.field_71439_g.field_71069_bz.func_75139_a(i).func_75211_c();
/*    */       
/* 81 */       if (itemstack != null && Item.func_150891_b(itemstack.func_77973_b()) == 346) {
/* 82 */         mc.field_71439_g.field_71071_by.field_70461_c = i - 36;
/*    */         break;
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   private void Rod() {
/* 89 */     int rod = findRod(36, 45);
/* 90 */     mc.field_71442_b.func_78769_a((EntityPlayer)mc.field_71439_g, (World)mc.field_71441_e, mc.field_71439_g.field_71069_bz.func_75139_a(rod).func_75211_c());
/* 91 */     this.timer.reset();
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\module\player\AutoRod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */