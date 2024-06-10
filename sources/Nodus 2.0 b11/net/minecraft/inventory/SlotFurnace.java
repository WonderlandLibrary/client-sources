/*   1:    */ package net.minecraft.inventory;
/*   2:    */ 
/*   3:    */ import net.minecraft.entity.item.EntityXPOrb;
/*   4:    */ import net.minecraft.entity.player.EntityPlayer;
/*   5:    */ import net.minecraft.init.Items;
/*   6:    */ import net.minecraft.item.ItemStack;
/*   7:    */ import net.minecraft.item.crafting.FurnaceRecipes;
/*   8:    */ import net.minecraft.stats.AchievementList;
/*   9:    */ import net.minecraft.util.MathHelper;
/*  10:    */ import net.minecraft.world.World;
/*  11:    */ 
/*  12:    */ public class SlotFurnace
/*  13:    */   extends Slot
/*  14:    */ {
/*  15:    */   private EntityPlayer thePlayer;
/*  16:    */   private int field_75228_b;
/*  17:    */   private static final String __OBFID = "CL_00001749";
/*  18:    */   
/*  19:    */   public SlotFurnace(EntityPlayer par1EntityPlayer, IInventory par2IInventory, int par3, int par4, int par5)
/*  20:    */   {
/*  21: 20 */     super(par2IInventory, par3, par4, par5);
/*  22: 21 */     this.thePlayer = par1EntityPlayer;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public boolean isItemValid(ItemStack par1ItemStack)
/*  26:    */   {
/*  27: 29 */     return false;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public ItemStack decrStackSize(int par1)
/*  31:    */   {
/*  32: 38 */     if (getHasStack()) {
/*  33: 40 */       this.field_75228_b += Math.min(par1, getStack().stackSize);
/*  34:    */     }
/*  35: 43 */     return super.decrStackSize(par1);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public void onPickupFromSlot(EntityPlayer par1EntityPlayer, ItemStack par2ItemStack)
/*  39:    */   {
/*  40: 48 */     onCrafting(par2ItemStack);
/*  41: 49 */     super.onPickupFromSlot(par1EntityPlayer, par2ItemStack);
/*  42:    */   }
/*  43:    */   
/*  44:    */   protected void onCrafting(ItemStack par1ItemStack, int par2)
/*  45:    */   {
/*  46: 58 */     this.field_75228_b += par2;
/*  47: 59 */     onCrafting(par1ItemStack);
/*  48:    */   }
/*  49:    */   
/*  50:    */   protected void onCrafting(ItemStack par1ItemStack)
/*  51:    */   {
/*  52: 67 */     par1ItemStack.onCrafting(this.thePlayer.worldObj, this.thePlayer, this.field_75228_b);
/*  53: 69 */     if (!this.thePlayer.worldObj.isClient)
/*  54:    */     {
/*  55: 71 */       int var2 = this.field_75228_b;
/*  56: 72 */       float var3 = FurnaceRecipes.smelting().func_151398_b(par1ItemStack);
/*  57: 75 */       if (var3 == 0.0F)
/*  58:    */       {
/*  59: 77 */         var2 = 0;
/*  60:    */       }
/*  61: 79 */       else if (var3 < 1.0F)
/*  62:    */       {
/*  63: 81 */         int var4 = MathHelper.floor_float(var2 * var3);
/*  64: 83 */         if ((var4 < MathHelper.ceiling_float_int(var2 * var3)) && ((float)Math.random() < var2 * var3 - var4)) {
/*  65: 85 */           var4++;
/*  66:    */         }
/*  67: 88 */         var2 = var4;
/*  68:    */       }
/*  69: 91 */       while (var2 > 0)
/*  70:    */       {
/*  71: 93 */         int var4 = EntityXPOrb.getXPSplit(var2);
/*  72: 94 */         var2 -= var4;
/*  73: 95 */         this.thePlayer.worldObj.spawnEntityInWorld(new EntityXPOrb(this.thePlayer.worldObj, this.thePlayer.posX, this.thePlayer.posY + 0.5D, this.thePlayer.posZ + 0.5D, var4));
/*  74:    */       }
/*  75:    */     }
/*  76: 99 */     this.field_75228_b = 0;
/*  77:101 */     if (par1ItemStack.getItem() == Items.iron_ingot) {
/*  78:103 */       this.thePlayer.addStat(AchievementList.acquireIron, 1);
/*  79:    */     }
/*  80:106 */     if (par1ItemStack.getItem() == Items.cooked_fished) {
/*  81:108 */       this.thePlayer.addStat(AchievementList.cookFish, 1);
/*  82:    */     }
/*  83:    */   }
/*  84:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.inventory.SlotFurnace
 * JD-Core Version:    0.7.0.1
 */