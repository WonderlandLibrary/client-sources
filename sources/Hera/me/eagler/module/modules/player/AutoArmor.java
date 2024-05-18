/*     */ package me.eagler.module.modules.player;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import me.eagler.module.Category;
/*     */ import me.eagler.module.Module;
/*     */ import me.eagler.setting.Setting;
/*     */ import me.eagler.utils.TimeHelper;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemArmor;
/*     */ import net.minecraft.item.ItemStack;
/*     */ 
/*     */ public class AutoArmor extends Module {
/*  13 */   public TimeHelper time = new TimeHelper();
/*     */   
/*  15 */   int ticks = 0;
/*     */   
/*     */   int delay;
/*     */   
/*     */   public AutoArmor() {
/*  20 */     super("AutoArmor", Category.Player);
/*  21 */     this.delay = 0;
/*  22 */     ArrayList<String> options = new ArrayList<String>();
/*  23 */     options.add("Normal");
/*  24 */     options.add("OpenInv");
/*  25 */     options.add("AAC");
/*  26 */     this.settingManager.addSetting(new Setting("AAMode", this, "AAC", options));
/*  27 */     this.settingManager.addSetting(new Setting("AutoArmorDelay", this, 125.0D, 0.0D, 500.0D, true));
/*     */   }
/*     */   
/*     */   public void onEnabled() {
/*  31 */     this.time.reset();
/*     */   }
/*     */   
/*     */   public void onUpdate() {
/*  35 */     this.delay = (int)this.settingManager.getSettingByName("AAMode").getValue();
/*  36 */     String mode = this.settingManager.getSettingByName("AAMode").getMode();
/*     */     
/*  38 */     setExtraTag(mode);
/*     */     
/*  40 */     if (this.settingManager.getSettingByName("AAMode").getMode().equalsIgnoreCase("AAC")) {
/*  41 */       if (!(this.mc.currentScreen instanceof net.minecraft.client.gui.inventory.GuiInventory))
/*     */         return; 
/*  43 */       if (this.mc.thePlayer.moveForward >= 0.5D || this.mc.thePlayer.moveStrafing >= 0.5D)
/*     */         return; 
/*  45 */     } else if (this.settingManager.getSettingByName("AAMode").getMode().equalsIgnoreCase("OpenInv")) {
/*  46 */       if (!(this.mc.currentScreen instanceof net.minecraft.client.gui.inventory.GuiInventory))
/*     */         return; 
/*     */     } else {
/*  49 */       this.settingManager.getSettingByName("AAMode").getMode().equalsIgnoreCase("Normal");
/*     */     } 
/*  51 */     if (this.time.hasReached(this.delay)) {
/*  52 */       if (this.ticks == 0)
/*  53 */         check("boot"); 
/*  54 */       if (this.ticks == 1)
/*  55 */         check("leggings"); 
/*  56 */       if (this.ticks == 2)
/*  57 */         check("chestplate"); 
/*  58 */       if (this.ticks == 3)
/*  59 */         check("helmet"); 
/*  60 */       this.ticks++;
/*  61 */       if (this.ticks > 4)
/*  62 */         this.ticks = 0; 
/*  63 */       this.time.reset();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void check(String type) {
/*  68 */     ItemStack item = null;
/*     */     try {
/*  70 */       item = this.mc.thePlayer.inventory.armorItemInSlot(this.ticks);
/*  71 */     } catch (Exception exception) {}
/*     */     
/*  73 */     int[] armorIus = getBestArmor(type);
/*  74 */     if (item != null && item.getItem() instanceof ItemArmor) {
/*  75 */       ItemArmor item2 = (ItemArmor)item.getItem();
/*  76 */       int newD = item2.getMaxDamage();
/*  77 */       if (item2.getUnlocalizedName().toLowerCase().contains("iron"))
/*  78 */         newD++; 
/*  79 */       if (newD < armorIus[1]) {
/*  80 */         this.mc.playerController.windowClick(0, 8 - this.ticks, 0, 4, (EntityPlayer)this.mc.thePlayer);
/*  81 */         this.mc.playerController.updateController();
/*     */       } 
/*  83 */     } else if (armorIus[0] != 0) {
/*  84 */       this.mc.playerController.windowClick(0, armorIus[0], 0, 1, (EntityPlayer)this.mc.thePlayer);
/*     */     } 
/*     */   }
/*     */   
/*     */   public int[] getBestArmor(String type) {
/*  89 */     int oDur = 0;
/*  90 */     int slotId = 0;
/*  91 */     for (int i = 9; i < 45; i++) {
/*  92 */       ItemStack stack = this.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
/*  93 */       if (stack != null && stack.getItem() instanceof ItemArmor) {
/*  94 */         ItemArmor item = (ItemArmor)stack.getItem();
/*  95 */         int nDur = item.getMaxDamage();
/*  96 */         if (item.getUnlocalizedName().toLowerCase().contains("iron"))
/*  97 */           nDur++; 
/*  98 */         if (nDur > oDur && item.getUnlocalizedName().toLowerCase().contains(type)) {
/*  99 */           slotId = i;
/* 100 */           oDur = nDur;
/*     */         } 
/*     */       } 
/*     */     } 
/* 104 */     return new int[] { slotId, oDur };
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\me\eagler\module\modules\player\AutoArmor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */