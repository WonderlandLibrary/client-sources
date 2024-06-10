/*   1:    */ package net.minecraft.block;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import net.minecraft.block.material.MapColor;
/*   5:    */ import net.minecraft.block.material.Material;
/*   6:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*   7:    */ import net.minecraft.creativetab.CreativeTabs;
/*   8:    */ import net.minecraft.item.Item;
/*   9:    */ import net.minecraft.item.ItemStack;
/*  10:    */ import net.minecraft.util.IIcon;
/*  11:    */ import net.minecraft.world.World;
/*  12:    */ 
/*  13:    */ public class BlockQuartz
/*  14:    */   extends Block
/*  15:    */ {
/*  16: 15 */   public static final String[] field_150191_a = { "default", "chiseled", "lines" };
/*  17: 16 */   private static final String[] field_150189_b = { "side", "chiseled", "lines" };
/*  18:    */   private IIcon[] field_150192_M;
/*  19:    */   private IIcon field_150193_N;
/*  20:    */   private IIcon field_150194_O;
/*  21:    */   private IIcon field_150190_P;
/*  22:    */   private IIcon field_150188_Q;
/*  23:    */   private static final String __OBFID = "CL_00000292";
/*  24:    */   
/*  25:    */   public BlockQuartz()
/*  26:    */   {
/*  27: 26 */     super(Material.rock);
/*  28: 27 */     setCreativeTab(CreativeTabs.tabBlock);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public IIcon getIcon(int p_149691_1_, int p_149691_2_)
/*  32:    */   {
/*  33: 35 */     if ((p_149691_2_ != 2) && (p_149691_2_ != 3) && (p_149691_2_ != 4))
/*  34:    */     {
/*  35: 37 */       if ((p_149691_1_ != 1) && ((p_149691_1_ != 0) || (p_149691_2_ != 1)))
/*  36:    */       {
/*  37: 39 */         if (p_149691_1_ == 0) {
/*  38: 41 */           return this.field_150188_Q;
/*  39:    */         }
/*  40: 45 */         if ((p_149691_2_ < 0) || (p_149691_2_ >= this.field_150192_M.length)) {
/*  41: 47 */           p_149691_2_ = 0;
/*  42:    */         }
/*  43: 50 */         return this.field_150192_M[p_149691_2_];
/*  44:    */       }
/*  45: 55 */       return p_149691_2_ == 1 ? this.field_150193_N : this.field_150190_P;
/*  46:    */     }
/*  47: 60 */     return (p_149691_2_ == 4) && ((p_149691_1_ == 2) || (p_149691_1_ == 3)) ? this.field_150194_O : (p_149691_2_ == 3) && ((p_149691_1_ == 5) || (p_149691_1_ == 4)) ? this.field_150194_O : (p_149691_2_ == 2) && ((p_149691_1_ == 1) || (p_149691_1_ == 0)) ? this.field_150194_O : this.field_150192_M[p_149691_2_];
/*  48:    */   }
/*  49:    */   
/*  50:    */   public int onBlockPlaced(World p_149660_1_, int p_149660_2_, int p_149660_3_, int p_149660_4_, int p_149660_5_, float p_149660_6_, float p_149660_7_, float p_149660_8_, int p_149660_9_)
/*  51:    */   {
/*  52: 66 */     if (p_149660_9_ == 2) {
/*  53: 68 */       switch (p_149660_5_)
/*  54:    */       {
/*  55:    */       case 0: 
/*  56:    */       case 1: 
/*  57: 72 */         p_149660_9_ = 2;
/*  58: 73 */         break;
/*  59:    */       case 2: 
/*  60:    */       case 3: 
/*  61: 77 */         p_149660_9_ = 4;
/*  62: 78 */         break;
/*  63:    */       case 4: 
/*  64:    */       case 5: 
/*  65: 82 */         p_149660_9_ = 3;
/*  66:    */       }
/*  67:    */     }
/*  68: 86 */     return p_149660_9_;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public int damageDropped(int p_149692_1_)
/*  72:    */   {
/*  73: 94 */     return (p_149692_1_ != 3) && (p_149692_1_ != 4) ? p_149692_1_ : 2;
/*  74:    */   }
/*  75:    */   
/*  76:    */   protected ItemStack createStackedBlock(int p_149644_1_)
/*  77:    */   {
/*  78:103 */     return (p_149644_1_ != 3) && (p_149644_1_ != 4) ? super.createStackedBlock(p_149644_1_) : new ItemStack(Item.getItemFromBlock(this), 1, 2);
/*  79:    */   }
/*  80:    */   
/*  81:    */   public int getRenderType()
/*  82:    */   {
/*  83:111 */     return 39;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_)
/*  87:    */   {
/*  88:116 */     p_149666_3_.add(new ItemStack(p_149666_1_, 1, 0));
/*  89:117 */     p_149666_3_.add(new ItemStack(p_149666_1_, 1, 1));
/*  90:118 */     p_149666_3_.add(new ItemStack(p_149666_1_, 1, 2));
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void registerBlockIcons(IIconRegister p_149651_1_)
/*  94:    */   {
/*  95:123 */     this.field_150192_M = new IIcon[field_150189_b.length];
/*  96:125 */     for (int var2 = 0; var2 < this.field_150192_M.length; var2++) {
/*  97:127 */       if (field_150189_b[var2] == null) {
/*  98:129 */         this.field_150192_M[var2] = this.field_150192_M[(var2 - 1)];
/*  99:    */       } else {
/* 100:133 */         this.field_150192_M[var2] = p_149651_1_.registerIcon(getTextureName() + "_" + field_150189_b[var2]);
/* 101:    */       }
/* 102:    */     }
/* 103:137 */     this.field_150190_P = p_149651_1_.registerIcon(getTextureName() + "_" + "top");
/* 104:138 */     this.field_150193_N = p_149651_1_.registerIcon(getTextureName() + "_" + "chiseled_top");
/* 105:139 */     this.field_150194_O = p_149651_1_.registerIcon(getTextureName() + "_" + "lines_top");
/* 106:140 */     this.field_150188_Q = p_149651_1_.registerIcon(getTextureName() + "_" + "bottom");
/* 107:    */   }
/* 108:    */   
/* 109:    */   public MapColor getMapColor(int p_149728_1_)
/* 110:    */   {
/* 111:145 */     return MapColor.field_151677_p;
/* 112:    */   }
/* 113:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockQuartz
 * JD-Core Version:    0.7.0.1
 */