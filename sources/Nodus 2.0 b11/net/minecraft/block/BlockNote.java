/*   1:    */ package net.minecraft.block;
/*   2:    */ 
/*   3:    */ import net.minecraft.block.material.Material;
/*   4:    */ import net.minecraft.creativetab.CreativeTabs;
/*   5:    */ import net.minecraft.entity.player.EntityPlayer;
/*   6:    */ import net.minecraft.tileentity.TileEntity;
/*   7:    */ import net.minecraft.tileentity.TileEntityNote;
/*   8:    */ import net.minecraft.world.World;
/*   9:    */ 
/*  10:    */ public class BlockNote
/*  11:    */   extends BlockContainer
/*  12:    */ {
/*  13:    */   private static final String __OBFID = "CL_00000278";
/*  14:    */   
/*  15:    */   public BlockNote()
/*  16:    */   {
/*  17: 16 */     super(Material.wood);
/*  18: 17 */     setCreativeTab(CreativeTabs.tabRedstone);
/*  19:    */   }
/*  20:    */   
/*  21:    */   public void onNeighborBlockChange(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_)
/*  22:    */   {
/*  23: 22 */     boolean var6 = p_149695_1_.isBlockIndirectlyGettingPowered(p_149695_2_, p_149695_3_, p_149695_4_);
/*  24: 23 */     TileEntityNote var7 = (TileEntityNote)p_149695_1_.getTileEntity(p_149695_2_, p_149695_3_, p_149695_4_);
/*  25: 25 */     if ((var7 != null) && (var7.field_145880_i != var6))
/*  26:    */     {
/*  27: 27 */       if (var6) {
/*  28: 29 */         var7.func_145878_a(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_);
/*  29:    */       }
/*  30: 32 */       var7.field_145880_i = var6;
/*  31:    */     }
/*  32:    */   }
/*  33:    */   
/*  34:    */   public boolean onBlockActivated(World p_149727_1_, int p_149727_2_, int p_149727_3_, int p_149727_4_, EntityPlayer p_149727_5_, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
/*  35:    */   {
/*  36: 41 */     if (p_149727_1_.isClient) {
/*  37: 43 */       return true;
/*  38:    */     }
/*  39: 47 */     TileEntityNote var10 = (TileEntityNote)p_149727_1_.getTileEntity(p_149727_2_, p_149727_3_, p_149727_4_);
/*  40: 49 */     if (var10 != null)
/*  41:    */     {
/*  42: 51 */       var10.func_145877_a();
/*  43: 52 */       var10.func_145878_a(p_149727_1_, p_149727_2_, p_149727_3_, p_149727_4_);
/*  44:    */     }
/*  45: 55 */     return true;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void onBlockClicked(World p_149699_1_, int p_149699_2_, int p_149699_3_, int p_149699_4_, EntityPlayer p_149699_5_)
/*  49:    */   {
/*  50: 64 */     if (!p_149699_1_.isClient)
/*  51:    */     {
/*  52: 66 */       TileEntityNote var6 = (TileEntityNote)p_149699_1_.getTileEntity(p_149699_2_, p_149699_3_, p_149699_4_);
/*  53: 68 */       if (var6 != null) {
/*  54: 70 */         var6.func_145878_a(p_149699_1_, p_149699_2_, p_149699_3_, p_149699_4_);
/*  55:    */       }
/*  56:    */     }
/*  57:    */   }
/*  58:    */   
/*  59:    */   public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
/*  60:    */   {
/*  61: 80 */     return new TileEntityNote();
/*  62:    */   }
/*  63:    */   
/*  64:    */   public boolean onBlockEventReceived(World p_149696_1_, int p_149696_2_, int p_149696_3_, int p_149696_4_, int p_149696_5_, int p_149696_6_)
/*  65:    */   {
/*  66: 85 */     float var7 = (float)Math.pow(2.0D, (p_149696_6_ - 12) / 12.0D);
/*  67: 86 */     String var8 = "harp";
/*  68: 88 */     if (p_149696_5_ == 1) {
/*  69: 90 */       var8 = "bd";
/*  70:    */     }
/*  71: 93 */     if (p_149696_5_ == 2) {
/*  72: 95 */       var8 = "snare";
/*  73:    */     }
/*  74: 98 */     if (p_149696_5_ == 3) {
/*  75:100 */       var8 = "hat";
/*  76:    */     }
/*  77:103 */     if (p_149696_5_ == 4) {
/*  78:105 */       var8 = "bassattack";
/*  79:    */     }
/*  80:108 */     p_149696_1_.playSoundEffect(p_149696_2_ + 0.5D, p_149696_3_ + 0.5D, p_149696_4_ + 0.5D, "note." + var8, 3.0F, var7);
/*  81:109 */     p_149696_1_.spawnParticle("note", p_149696_2_ + 0.5D, p_149696_3_ + 1.2D, p_149696_4_ + 0.5D, p_149696_6_ / 24.0D, 0.0D, 0.0D);
/*  82:110 */     return true;
/*  83:    */   }
/*  84:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockNote
 * JD-Core Version:    0.7.0.1
 */