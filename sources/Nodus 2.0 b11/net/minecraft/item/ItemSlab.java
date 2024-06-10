/*   1:    */ package net.minecraft.item;
/*   2:    */ 
/*   3:    */ import net.minecraft.block.Block;
/*   4:    */ import net.minecraft.block.Block.SoundType;
/*   5:    */ import net.minecraft.block.BlockSlab;
/*   6:    */ import net.minecraft.entity.player.EntityPlayer;
/*   7:    */ import net.minecraft.util.IIcon;
/*   8:    */ import net.minecraft.world.World;
/*   9:    */ 
/*  10:    */ public class ItemSlab
/*  11:    */   extends ItemBlock
/*  12:    */ {
/*  13:    */   private final boolean field_150948_b;
/*  14:    */   private final BlockSlab field_150949_c;
/*  15:    */   private final BlockSlab field_150947_d;
/*  16:    */   private static final String __OBFID = "CL_00000071";
/*  17:    */   
/*  18:    */   public ItemSlab(Block p_i45355_1_, BlockSlab p_i45355_2_, BlockSlab p_i45355_3_, boolean p_i45355_4_)
/*  19:    */   {
/*  20: 18 */     super(p_i45355_1_);
/*  21: 19 */     this.field_150949_c = p_i45355_2_;
/*  22: 20 */     this.field_150947_d = p_i45355_3_;
/*  23: 21 */     this.field_150948_b = p_i45355_4_;
/*  24: 22 */     setMaxDamage(0);
/*  25: 23 */     setHasSubtypes(true);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public IIcon getIconFromDamage(int par1)
/*  29:    */   {
/*  30: 31 */     return Block.getBlockFromItem(this).getIcon(2, par1);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public int getMetadata(int par1)
/*  34:    */   {
/*  35: 39 */     return par1;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public String getUnlocalizedName(ItemStack par1ItemStack)
/*  39:    */   {
/*  40: 48 */     return this.field_150949_c.func_150002_b(par1ItemStack.getItemDamage());
/*  41:    */   }
/*  42:    */   
/*  43:    */   public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
/*  44:    */   {
/*  45: 57 */     if (this.field_150948_b) {
/*  46: 59 */       return super.onItemUse(par1ItemStack, par2EntityPlayer, par3World, par4, par5, par6, par7, par8, par9, par10);
/*  47:    */     }
/*  48: 61 */     if (par1ItemStack.stackSize == 0) {
/*  49: 63 */       return false;
/*  50:    */     }
/*  51: 65 */     if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack)) {
/*  52: 67 */       return false;
/*  53:    */     }
/*  54: 71 */     Block var11 = par3World.getBlock(par4, par5, par6);
/*  55: 72 */     int var12 = par3World.getBlockMetadata(par4, par5, par6);
/*  56: 73 */     int var13 = var12 & 0x7;
/*  57: 74 */     boolean var14 = (var12 & 0x8) != 0;
/*  58: 76 */     if (((par7 == 1) && (!var14)) || ((par7 == 0) && (var14) && (var11 == this.field_150949_c) && (var13 == par1ItemStack.getItemDamage())))
/*  59:    */     {
/*  60: 78 */       if ((par3World.checkNoEntityCollision(this.field_150947_d.getCollisionBoundingBoxFromPool(par3World, par4, par5, par6))) && (par3World.setBlock(par4, par5, par6, this.field_150947_d, var13, 3)))
/*  61:    */       {
/*  62: 80 */         par3World.playSoundEffect(par4 + 0.5F, par5 + 0.5F, par6 + 0.5F, this.field_150947_d.stepSound.func_150496_b(), (this.field_150947_d.stepSound.func_150497_c() + 1.0F) / 2.0F, this.field_150947_d.stepSound.func_150494_d() * 0.8F);
/*  63: 81 */         par1ItemStack.stackSize -= 1;
/*  64:    */       }
/*  65: 84 */       return true;
/*  66:    */     }
/*  67: 88 */     return func_150946_a(par1ItemStack, par2EntityPlayer, par3World, par4, par5, par6, par7) ? true : super.onItemUse(par1ItemStack, par2EntityPlayer, par3World, par4, par5, par6, par7, par8, par9, par10);
/*  68:    */   }
/*  69:    */   
/*  70:    */   public boolean func_150936_a(World p_150936_1_, int p_150936_2_, int p_150936_3_, int p_150936_4_, int p_150936_5_, EntityPlayer p_150936_6_, ItemStack p_150936_7_)
/*  71:    */   {
/*  72: 95 */     int var8 = p_150936_2_;
/*  73: 96 */     int var9 = p_150936_3_;
/*  74: 97 */     int var10 = p_150936_4_;
/*  75: 98 */     Block var11 = p_150936_1_.getBlock(p_150936_2_, p_150936_3_, p_150936_4_);
/*  76: 99 */     int var12 = p_150936_1_.getBlockMetadata(p_150936_2_, p_150936_3_, p_150936_4_);
/*  77:100 */     int var13 = var12 & 0x7;
/*  78:101 */     boolean var14 = (var12 & 0x8) != 0;
/*  79:103 */     if (((p_150936_5_ == 1) && (!var14)) || ((p_150936_5_ == 0) && (var14) && (var11 == this.field_150949_c) && (var13 == p_150936_7_.getItemDamage()))) {
/*  80:105 */       return true;
/*  81:    */     }
/*  82:109 */     if (p_150936_5_ == 0) {
/*  83:111 */       p_150936_3_--;
/*  84:    */     }
/*  85:114 */     if (p_150936_5_ == 1) {
/*  86:116 */       p_150936_3_++;
/*  87:    */     }
/*  88:119 */     if (p_150936_5_ == 2) {
/*  89:121 */       p_150936_4_--;
/*  90:    */     }
/*  91:124 */     if (p_150936_5_ == 3) {
/*  92:126 */       p_150936_4_++;
/*  93:    */     }
/*  94:129 */     if (p_150936_5_ == 4) {
/*  95:131 */       p_150936_2_--;
/*  96:    */     }
/*  97:134 */     if (p_150936_5_ == 5) {
/*  98:136 */       p_150936_2_++;
/*  99:    */     }
/* 100:139 */     Block var15 = p_150936_1_.getBlock(p_150936_2_, p_150936_3_, p_150936_4_);
/* 101:140 */     int var16 = p_150936_1_.getBlockMetadata(p_150936_2_, p_150936_3_, p_150936_4_);
/* 102:141 */     var13 = var16 & 0x7;
/* 103:142 */     return (var15 == this.field_150949_c) && (var13 == p_150936_7_.getItemDamage()) ? true : super.func_150936_a(p_150936_1_, var8, var9, var10, p_150936_5_, p_150936_6_, p_150936_7_);
/* 104:    */   }
/* 105:    */   
/* 106:    */   private boolean func_150946_a(ItemStack p_150946_1_, EntityPlayer p_150946_2_, World p_150946_3_, int p_150946_4_, int p_150946_5_, int p_150946_6_, int p_150946_7_)
/* 107:    */   {
/* 108:148 */     if (p_150946_7_ == 0) {
/* 109:150 */       p_150946_5_--;
/* 110:    */     }
/* 111:153 */     if (p_150946_7_ == 1) {
/* 112:155 */       p_150946_5_++;
/* 113:    */     }
/* 114:158 */     if (p_150946_7_ == 2) {
/* 115:160 */       p_150946_6_--;
/* 116:    */     }
/* 117:163 */     if (p_150946_7_ == 3) {
/* 118:165 */       p_150946_6_++;
/* 119:    */     }
/* 120:168 */     if (p_150946_7_ == 4) {
/* 121:170 */       p_150946_4_--;
/* 122:    */     }
/* 123:173 */     if (p_150946_7_ == 5) {
/* 124:175 */       p_150946_4_++;
/* 125:    */     }
/* 126:178 */     Block var8 = p_150946_3_.getBlock(p_150946_4_, p_150946_5_, p_150946_6_);
/* 127:179 */     int var9 = p_150946_3_.getBlockMetadata(p_150946_4_, p_150946_5_, p_150946_6_);
/* 128:180 */     int var10 = var9 & 0x7;
/* 129:182 */     if ((var8 == this.field_150949_c) && (var10 == p_150946_1_.getItemDamage()))
/* 130:    */     {
/* 131:184 */       if ((p_150946_3_.checkNoEntityCollision(this.field_150947_d.getCollisionBoundingBoxFromPool(p_150946_3_, p_150946_4_, p_150946_5_, p_150946_6_))) && (p_150946_3_.setBlock(p_150946_4_, p_150946_5_, p_150946_6_, this.field_150947_d, var10, 3)))
/* 132:    */       {
/* 133:186 */         p_150946_3_.playSoundEffect(p_150946_4_ + 0.5F, p_150946_5_ + 0.5F, p_150946_6_ + 0.5F, this.field_150947_d.stepSound.func_150496_b(), (this.field_150947_d.stepSound.func_150497_c() + 1.0F) / 2.0F, this.field_150947_d.stepSound.func_150494_d() * 0.8F);
/* 134:187 */         p_150946_1_.stackSize -= 1;
/* 135:    */       }
/* 136:190 */       return true;
/* 137:    */     }
/* 138:194 */     return false;
/* 139:    */   }
/* 140:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.item.ItemSlab
 * JD-Core Version:    0.7.0.1
 */