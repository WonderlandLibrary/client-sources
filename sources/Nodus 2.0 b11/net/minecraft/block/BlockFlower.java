/*   1:    */ package net.minecraft.block;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import net.minecraft.block.material.Material;
/*   5:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*   6:    */ import net.minecraft.creativetab.CreativeTabs;
/*   7:    */ import net.minecraft.init.Blocks;
/*   8:    */ import net.minecraft.item.Item;
/*   9:    */ import net.minecraft.item.ItemStack;
/*  10:    */ import net.minecraft.util.IIcon;
/*  11:    */ 
/*  12:    */ public class BlockFlower
/*  13:    */   extends BlockBush
/*  14:    */ {
/*  15: 14 */   private static final String[][] field_149860_M = { { "flower_dandelion" }, { "flower_rose", "flower_blue_orchid", "flower_allium", "flower_houstonia", "flower_tulip_red", "flower_tulip_orange", "flower_tulip_white", "flower_tulip_pink", "flower_oxeye_daisy" } };
/*  16: 15 */   public static final String[] field_149859_a = { "poppy", "blueOrchid", "allium", "houstonia", "tulipRed", "tulipOrange", "tulipWhite", "tulipPink", "oxeyeDaisy" };
/*  17: 16 */   public static final String[] field_149858_b = { "dandelion" };
/*  18:    */   private IIcon[] field_149861_N;
/*  19:    */   private int field_149862_O;
/*  20:    */   private static final String __OBFID = "CL_00000246";
/*  21:    */   
/*  22:    */   protected BlockFlower(int par1)
/*  23:    */   {
/*  24: 23 */     super(Material.plants);
/*  25: 24 */     this.field_149862_O = par1;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public IIcon getIcon(int p_149691_1_, int p_149691_2_)
/*  29:    */   {
/*  30: 32 */     if (p_149691_2_ >= this.field_149861_N.length) {
/*  31: 34 */       p_149691_2_ = 0;
/*  32:    */     }
/*  33: 37 */     return this.field_149861_N[p_149691_2_];
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void registerBlockIcons(IIconRegister p_149651_1_)
/*  37:    */   {
/*  38: 42 */     this.field_149861_N = new IIcon[field_149860_M[this.field_149862_O].length];
/*  39: 44 */     for (int var2 = 0; var2 < this.field_149861_N.length; var2++) {
/*  40: 46 */       this.field_149861_N[var2] = p_149651_1_.registerIcon(field_149860_M[this.field_149862_O][var2]);
/*  41:    */     }
/*  42:    */   }
/*  43:    */   
/*  44:    */   public int damageDropped(int p_149692_1_)
/*  45:    */   {
/*  46: 55 */     return p_149692_1_;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_)
/*  50:    */   {
/*  51: 60 */     for (int var4 = 0; var4 < this.field_149861_N.length; var4++) {
/*  52: 62 */       p_149666_3_.add(new ItemStack(p_149666_1_, 1, var4));
/*  53:    */     }
/*  54:    */   }
/*  55:    */   
/*  56:    */   public static BlockFlower func_149857_e(String p_149857_0_)
/*  57:    */   {
/*  58: 68 */     String[] var1 = field_149858_b;
/*  59: 69 */     int var2 = var1.length;
/*  60: 73 */     for (int var3 = 0; var3 < var2; var3++)
/*  61:    */     {
/*  62: 75 */       String var4 = var1[var3];
/*  63: 77 */       if (var4.equals(p_149857_0_)) {
/*  64: 79 */         return Blocks.yellow_flower;
/*  65:    */       }
/*  66:    */     }
/*  67: 83 */     var1 = field_149859_a;
/*  68: 84 */     var2 = var1.length;
/*  69: 86 */     for (var3 = 0; var3 < var2; var3++)
/*  70:    */     {
/*  71: 88 */       String var4 = var1[var3];
/*  72: 90 */       if (var4.equals(p_149857_0_)) {
/*  73: 92 */         return Blocks.red_flower;
/*  74:    */       }
/*  75:    */     }
/*  76: 96 */     return null;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public static int func_149856_f(String p_149856_0_)
/*  80:    */   {
/*  81:103 */     for (int var1 = 0; var1 < field_149858_b.length; var1++) {
/*  82:105 */       if (field_149858_b[var1].equals(p_149856_0_)) {
/*  83:107 */         return var1;
/*  84:    */       }
/*  85:    */     }
/*  86:111 */     for (var1 = 0; var1 < field_149859_a.length; var1++) {
/*  87:113 */       if (field_149859_a[var1].equals(p_149856_0_)) {
/*  88:115 */         return var1;
/*  89:    */       }
/*  90:    */     }
/*  91:119 */     return 0;
/*  92:    */   }
/*  93:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockFlower
 * JD-Core Version:    0.7.0.1
 */