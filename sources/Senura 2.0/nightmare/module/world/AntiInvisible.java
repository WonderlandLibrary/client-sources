/*    */ package nightmare.module.world;
/*    */ 
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.potion.Potion;
/*    */ import nightmare.Nightmare;
/*    */ import nightmare.event.EventTarget;
/*    */ import nightmare.event.impl.EventUpdate;
/*    */ import nightmare.module.Category;
/*    */ import nightmare.module.Module;
/*    */ import nightmare.settings.Setting;
/*    */ 
/*    */ public class AntiInvisible extends Module {
/*    */   public AntiInvisible() {
/* 14 */     super("AntiInvisible", 0, Category.WORLD);
/*    */     
/* 16 */     Nightmare.instance.settingsManager.rSetting(new Setting("OnlyPotion", this, true));
/*    */   }
/*    */   
/*    */   @EventTarget
/*    */   public void onUpdate(EventUpdate event) {
/* 21 */     mc.field_71441_e.field_73010_i.stream()
/* 22 */       .filter(player -> (player != mc.field_71439_g && player.func_70644_a(Potion.field_76441_p) && (Nightmare.instance.settingsManager.getSettingByName(this, "OnlyPotion").getValBoolean() || player.func_82150_aj())))
/* 23 */       .forEach(player -> {
/*    */           player.func_82170_o(Potion.field_76441_p.func_76396_c());
/*    */           player.func_82142_c(false);
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\module\world\AntiInvisible.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */