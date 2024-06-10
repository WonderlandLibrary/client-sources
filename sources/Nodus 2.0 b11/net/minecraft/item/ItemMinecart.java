/*   1:    */ package net.minecraft.item;
/*   2:    */ 
/*   3:    */ import net.minecraft.block.Block;
/*   4:    */ import net.minecraft.block.BlockDispenser;
/*   5:    */ import net.minecraft.block.BlockRailBase;
/*   6:    */ import net.minecraft.block.material.Material;
/*   7:    */ import net.minecraft.creativetab.CreativeTabs;
/*   8:    */ import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
/*   9:    */ import net.minecraft.dispenser.IBehaviorDispenseItem;
/*  10:    */ import net.minecraft.dispenser.IBlockSource;
/*  11:    */ import net.minecraft.entity.item.EntityMinecart;
/*  12:    */ import net.minecraft.entity.player.EntityPlayer;
/*  13:    */ import net.minecraft.util.EnumFacing;
/*  14:    */ import net.minecraft.util.IRegistry;
/*  15:    */ import net.minecraft.world.World;
/*  16:    */ 
/*  17:    */ public class ItemMinecart
/*  18:    */   extends Item
/*  19:    */ {
/*  20: 18 */   private static final IBehaviorDispenseItem dispenserMinecartBehavior = new BehaviorDefaultDispenseItem()
/*  21:    */   {
/*  22: 20 */     private final BehaviorDefaultDispenseItem behaviourDefaultDispenseItem = new BehaviorDefaultDispenseItem();
/*  23:    */     private static final String __OBFID = "CL_00000050";
/*  24:    */     
/*  25:    */     public ItemStack dispenseStack(IBlockSource par1IBlockSource, ItemStack par2ItemStack)
/*  26:    */     {
/*  27: 24 */       EnumFacing var3 = BlockDispenser.func_149937_b(par1IBlockSource.getBlockMetadata());
/*  28: 25 */       World var4 = par1IBlockSource.getWorld();
/*  29: 26 */       double var5 = par1IBlockSource.getX() + var3.getFrontOffsetX() * 1.125F;
/*  30: 27 */       double var7 = par1IBlockSource.getY() + var3.getFrontOffsetY() * 1.125F;
/*  31: 28 */       double var9 = par1IBlockSource.getZ() + var3.getFrontOffsetZ() * 1.125F;
/*  32: 29 */       int var11 = par1IBlockSource.getXInt() + var3.getFrontOffsetX();
/*  33: 30 */       int var12 = par1IBlockSource.getYInt() + var3.getFrontOffsetY();
/*  34: 31 */       int var13 = par1IBlockSource.getZInt() + var3.getFrontOffsetZ();
/*  35: 32 */       Block var14 = var4.getBlock(var11, var12, var13);
/*  36:    */       double var15;
/*  37:    */       double var15;
/*  38: 35 */       if (BlockRailBase.func_150051_a(var14))
/*  39:    */       {
/*  40: 37 */         var15 = 0.0D;
/*  41:    */       }
/*  42:    */       else
/*  43:    */       {
/*  44: 41 */         if ((var14.getMaterial() != Material.air) || (!BlockRailBase.func_150051_a(var4.getBlock(var11, var12 - 1, var13)))) {
/*  45: 43 */           return this.behaviourDefaultDispenseItem.dispense(par1IBlockSource, par2ItemStack);
/*  46:    */         }
/*  47: 46 */         var15 = -1.0D;
/*  48:    */       }
/*  49: 49 */       EntityMinecart var17 = EntityMinecart.createMinecart(var4, var5, var7 + var15, var9, ((ItemMinecart)par2ItemStack.getItem()).minecartType);
/*  50: 51 */       if (par2ItemStack.hasDisplayName()) {
/*  51: 53 */         var17.setMinecartName(par2ItemStack.getDisplayName());
/*  52:    */       }
/*  53: 56 */       var4.spawnEntityInWorld(var17);
/*  54: 57 */       par2ItemStack.splitStack(1);
/*  55: 58 */       return par2ItemStack;
/*  56:    */     }
/*  57:    */     
/*  58:    */     protected void playDispenseSound(IBlockSource par1IBlockSource)
/*  59:    */     {
/*  60: 62 */       par1IBlockSource.getWorld().playAuxSFX(1000, par1IBlockSource.getXInt(), par1IBlockSource.getYInt(), par1IBlockSource.getZInt(), 0);
/*  61:    */     }
/*  62:    */   };
/*  63:    */   public int minecartType;
/*  64:    */   private static final String __OBFID = "CL_00000049";
/*  65:    */   
/*  66:    */   public ItemMinecart(int p_i45345_1_)
/*  67:    */   {
/*  68: 70 */     this.maxStackSize = 1;
/*  69: 71 */     this.minecartType = p_i45345_1_;
/*  70: 72 */     setCreativeTab(CreativeTabs.tabTransport);
/*  71: 73 */     BlockDispenser.dispenseBehaviorRegistry.putObject(this, dispenserMinecartBehavior);
/*  72:    */   }
/*  73:    */   
/*  74:    */   public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
/*  75:    */   {
/*  76: 82 */     if (BlockRailBase.func_150051_a(par3World.getBlock(par4, par5, par6)))
/*  77:    */     {
/*  78: 84 */       if (!par3World.isClient)
/*  79:    */       {
/*  80: 86 */         EntityMinecart var11 = EntityMinecart.createMinecart(par3World, par4 + 0.5F, par5 + 0.5F, par6 + 0.5F, this.minecartType);
/*  81: 88 */         if (par1ItemStack.hasDisplayName()) {
/*  82: 90 */           var11.setMinecartName(par1ItemStack.getDisplayName());
/*  83:    */         }
/*  84: 93 */         par3World.spawnEntityInWorld(var11);
/*  85:    */       }
/*  86: 96 */       par1ItemStack.stackSize -= 1;
/*  87: 97 */       return true;
/*  88:    */     }
/*  89:101 */     return false;
/*  90:    */   }
/*  91:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.item.ItemMinecart
 * JD-Core Version:    0.7.0.1
 */