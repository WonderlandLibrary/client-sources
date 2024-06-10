/*    */ package nightmare.utils;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import net.minecraft.enchantment.Enchantment;
/*    */ import net.minecraft.enchantment.EnchantmentHelper;
/*    */ import net.minecraft.item.ItemPotion;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.item.ItemSword;
/*    */ import net.minecraft.potion.Potion;
/*    */ import net.minecraft.potion.PotionEffect;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class InventoryUtils
/*    */ {
/*    */   public static float getDamageLevel(ItemStack stack) {
/* 23 */     if (stack.func_77973_b() instanceof ItemSword) {
/* 24 */       ItemSword sword = (ItemSword)stack.func_77973_b();
/* 25 */       float sharpness = EnchantmentHelper.func_77506_a(Enchantment.field_180314_l.field_77352_x, stack) * 1.25F;
/* 26 */       float fireAspect = EnchantmentHelper.func_77506_a(Enchantment.field_77334_n.field_77352_x, stack) * 1.5F;
/* 27 */       return sword.func_150931_i() + sharpness + fireAspect;
/*    */     } 
/* 29 */     return 0.0F;
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean isValidItem(ItemStack itemStack) {
/* 34 */     return (itemStack.func_77973_b() instanceof net.minecraft.item.ItemArmor || itemStack.func_77973_b() instanceof ItemSword || itemStack
/* 35 */       .func_77973_b() instanceof net.minecraft.item.ItemTool || itemStack.func_77973_b() instanceof net.minecraft.item.ItemFood || itemStack
/* 36 */       .func_77973_b() instanceof net.minecraft.item.ItemEgg || itemStack.func_77973_b() instanceof net.minecraft.item.ItemSnowball || (itemStack
/* 37 */       .func_77973_b() instanceof ItemPotion && !isBadPotion(itemStack)) || itemStack
/* 38 */       .func_77973_b() instanceof net.minecraft.item.ItemBlock || itemStack.func_82833_r().contains("Play") || itemStack
/* 39 */       .func_82833_r().contains("Game") || itemStack.func_82833_r().contains("Right Click") || itemStack
/* 40 */       .func_77973_b() instanceof net.minecraft.item.ItemEnderPearl);
/*    */   }
/*    */   
/*    */   public static boolean isBadPotion(ItemStack stack) {
/* 44 */     if (stack != null && stack.func_77973_b() instanceof ItemPotion) {
/* 45 */       ItemPotion potion = (ItemPotion)stack.func_77973_b();
/* 46 */       if (ItemPotion.func_77831_g(stack.func_77952_i())) {
/* 47 */         Iterator<?> var2 = potion.func_77832_l(stack).iterator();
/*    */         
/* 49 */         while (var2.hasNext()) {
/* 50 */           Object o = var2.next();
/* 51 */           PotionEffect effect = (PotionEffect)o;
/* 52 */           if (effect.func_76456_a() == Potion.field_76436_u.func_76396_c() || effect.func_76456_a() == Potion.field_76433_i.func_76396_c() || effect.func_76456_a() == Potion.field_76421_d.func_76396_c() || effect.func_76456_a() == Potion.field_76437_t.func_76396_c()) {
/* 53 */             return true;
/*    */           }
/*    */         } 
/*    */       } 
/*    */     } 
/* 58 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmar\\utils\InventoryUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */