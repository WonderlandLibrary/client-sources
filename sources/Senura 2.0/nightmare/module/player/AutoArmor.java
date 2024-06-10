/*     */ package nightmare.module.player;
/*     */ 
/*     */ import net.minecraft.enchantment.Enchantment;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.item.ItemArmor;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import nightmare.Nightmare;
/*     */ import nightmare.event.EventTarget;
/*     */ import nightmare.event.impl.EventUpdate;
/*     */ import nightmare.module.Category;
/*     */ import nightmare.module.Module;
/*     */ import nightmare.settings.Setting;
/*     */ import nightmare.utils.PlayerUtils;
/*     */ import nightmare.utils.TimerUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AutoArmor
/*     */   extends Module
/*     */ {
/*  25 */   TimerUtils timer = new TimerUtils();
/*     */   
/*     */   public AutoArmor() {
/*  28 */     super("AutoArmor", 0, Category.PLAYER);
/*     */     
/*  30 */     Nightmare.instance.settingsManager.rSetting(new Setting("Delay", this, 3.0D, 1.0D, 10.0D, false));
/*  31 */     Nightmare.instance.settingsManager.rSetting(new Setting("InvOnly", this, false));
/*     */   }
/*     */   
/*     */   @EventTarget
/*     */   public void onUpdate(EventUpdate event) {
/*  36 */     if ((Nightmare.instance.settingsManager.getSettingByName(this, "InvOnly").getValBoolean() && mc.field_71462_r instanceof net.minecraft.client.gui.inventory.GuiInventory) || !Nightmare.instance.settingsManager.getSettingByName(this, "InvOnly").getValBoolean()) {
/*  37 */       for (int type = 1; type < 5; type++) {
/*  38 */         if (mc.field_71439_g.field_71069_bz.func_75139_a(4 + type).func_75216_d()) {
/*  39 */           ItemStack slotStack = mc.field_71439_g.field_71069_bz.func_75139_a(4 + type).func_75211_c();
/*     */           
/*  41 */           if (isBestArmor(slotStack, type)) {
/*     */             continue;
/*     */           }
/*  44 */           PlayerUtils.drop(4 + type);
/*     */         } 
/*     */ 
/*     */         
/*  48 */         for (int i = 9; i < 45; i++) {
/*  49 */           if (mc.field_71439_g.field_71069_bz.func_75139_a(i).func_75216_d()) {
/*  50 */             ItemStack slotStack = mc.field_71439_g.field_71069_bz.func_75139_a(i).func_75211_c();
/*     */             
/*  52 */             if (isBestArmor(slotStack, type) && getProtection(slotStack) > 0.0F && 
/*  53 */               this.timer.check(((int)Nightmare.instance.settingsManager.getSettingByName(this, "Delay").getValDouble() * 50))) {
/*  54 */               PlayerUtils.shiftClick(i);
/*  55 */               this.timer.reset();
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         continue;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private float getProtection(ItemStack stack) {
/*  65 */     float protection = 0.0F;
/*     */     
/*  67 */     if (stack.func_77973_b() instanceof ItemArmor) {
/*  68 */       ItemArmor armor = (ItemArmor)stack.func_77973_b();
/*     */       
/*  70 */       protection = (float)(protection + armor.field_77879_b + ((100 - armor.field_77879_b) * EnchantmentHelper.func_77506_a(Enchantment.field_180310_c.field_77352_x, stack)) * 0.0075D);
/*     */       
/*  72 */       protection = (float)(protection + EnchantmentHelper.func_77506_a(Enchantment.field_77327_f.field_77352_x, stack) / 100.0D);
/*  73 */       protection = (float)(protection + EnchantmentHelper.func_77506_a(Enchantment.field_77329_d.field_77352_x, stack) / 100.0D);
/*  74 */       protection = (float)(protection + EnchantmentHelper.func_77506_a(Enchantment.field_92091_k.field_77352_x, stack) / 100.0D);
/*  75 */       protection = (float)(protection + EnchantmentHelper.func_77506_a(Enchantment.field_77347_r.field_77352_x, stack) / 50.0D);
/*  76 */       protection = (float)(protection + EnchantmentHelper.func_77506_a(Enchantment.field_180308_g.field_77352_x, stack) / 100.0D);
/*     */     } 
/*     */     
/*  79 */     return protection;
/*     */   }
/*     */   
/*     */   public boolean isWorking() {
/*  83 */     return !this.timer.check(((int)Nightmare.instance.settingsManager.getSettingByName(this, "Delay").getValDouble() * 100));
/*     */   }
/*     */   
/*     */   boolean isBestArmor(ItemStack stack, int type) {
/*  87 */     String strType = "";
/*     */     
/*  89 */     switch (type) {
/*     */       case 1:
/*  91 */         strType = "helmet";
/*     */         break;
/*     */       case 2:
/*  94 */         strType = "chestplate";
/*     */         break;
/*     */       case 3:
/*  97 */         strType = "leggings";
/*     */         break;
/*     */       case 4:
/* 100 */         strType = "boots";
/*     */         break;
/*     */     } 
/*     */     
/* 104 */     if (!stack.func_77977_a().contains(strType)) {
/* 105 */       return false;
/*     */     }
/*     */     
/* 108 */     float protection = getProtection(stack);
/*     */     
/* 110 */     for (int i = 5; i < 45; i++) {
/* 111 */       if (mc.field_71439_g.field_71069_bz.func_75139_a(i).func_75216_d()) {
/* 112 */         ItemStack is = mc.field_71439_g.field_71069_bz.func_75139_a(i).func_75211_c();
/* 113 */         if (getProtection(is) > protection && is.func_77977_a().contains(strType)) return false;
/*     */       
/*     */       } 
/*     */     } 
/* 117 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\module\player\AutoArmor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */