/*  1:   */ package net.minecraft.block;
/*  2:   */ 
/*  3:   */ import net.minecraft.client.renderer.texture.IIconRegister;
/*  4:   */ import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
/*  5:   */ import net.minecraft.dispenser.IBehaviorDispenseItem;
/*  6:   */ import net.minecraft.inventory.IInventory;
/*  7:   */ import net.minecraft.item.ItemStack;
/*  8:   */ import net.minecraft.tileentity.TileEntity;
/*  9:   */ import net.minecraft.tileentity.TileEntityDispenser;
/* 10:   */ import net.minecraft.tileentity.TileEntityDropper;
/* 11:   */ import net.minecraft.tileentity.TileEntityHopper;
/* 12:   */ import net.minecraft.world.World;
/* 13:   */ 
/* 14:   */ public class BlockDropper
/* 15:   */   extends BlockDispenser
/* 16:   */ {
/* 17:17 */   private final IBehaviorDispenseItem field_149947_P = new BehaviorDefaultDispenseItem();
/* 18:   */   private static final String __OBFID = "CL_00000233";
/* 19:   */   
/* 20:   */   public void registerBlockIcons(IIconRegister p_149651_1_)
/* 21:   */   {
/* 22:22 */     this.blockIcon = p_149651_1_.registerIcon("furnace_side");
/* 23:23 */     this.field_149944_M = p_149651_1_.registerIcon("furnace_top");
/* 24:24 */     this.field_149945_N = p_149651_1_.registerIcon(getTextureName() + "_front_horizontal");
/* 25:25 */     this.field_149946_O = p_149651_1_.registerIcon(getTextureName() + "_front_vertical");
/* 26:   */   }
/* 27:   */   
/* 28:   */   protected IBehaviorDispenseItem func_149940_a(ItemStack p_149940_1_)
/* 29:   */   {
/* 30:30 */     return this.field_149947_P;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
/* 34:   */   {
/* 35:38 */     return new TileEntityDropper();
/* 36:   */   }
/* 37:   */   
/* 38:   */   protected void func_149941_e(World p_149941_1_, int p_149941_2_, int p_149941_3_, int p_149941_4_)
/* 39:   */   {
/* 40:43 */     BlockSourceImpl var5 = new BlockSourceImpl(p_149941_1_, p_149941_2_, p_149941_3_, p_149941_4_);
/* 41:44 */     TileEntityDispenser var6 = (TileEntityDispenser)var5.getBlockTileEntity();
/* 42:46 */     if (var6 != null)
/* 43:   */     {
/* 44:48 */       int var7 = var6.func_146017_i();
/* 45:50 */       if (var7 < 0)
/* 46:   */       {
/* 47:52 */         p_149941_1_.playAuxSFX(1001, p_149941_2_, p_149941_3_, p_149941_4_, 0);
/* 48:   */       }
/* 49:   */       else
/* 50:   */       {
/* 51:56 */         ItemStack var8 = var6.getStackInSlot(var7);
/* 52:57 */         int var9 = p_149941_1_.getBlockMetadata(p_149941_2_, p_149941_3_, p_149941_4_) & 0x7;
/* 53:58 */         IInventory var10 = TileEntityHopper.func_145893_b(p_149941_1_, p_149941_2_ + net.minecraft.util.Facing.offsetsXForSide[var9], p_149941_3_ + net.minecraft.util.Facing.offsetsYForSide[var9], p_149941_4_ + net.minecraft.util.Facing.offsetsZForSide[var9]);
/* 54:   */         ItemStack var11;
/* 55:61 */         if (var10 != null)
/* 56:   */         {
/* 57:63 */           ItemStack var11 = TileEntityHopper.func_145889_a(var10, var8.copy().splitStack(1), net.minecraft.util.Facing.oppositeSide[var9]);
/* 58:65 */           if (var11 == null)
/* 59:   */           {
/* 60:67 */             var11 = var8.copy();
/* 61:69 */             if (--var11.stackSize == 0) {
/* 62:71 */               var11 = null;
/* 63:   */             }
/* 64:   */           }
/* 65:   */           else
/* 66:   */           {
/* 67:76 */             var11 = var8.copy();
/* 68:   */           }
/* 69:   */         }
/* 70:   */         else
/* 71:   */         {
/* 72:81 */           var11 = this.field_149947_P.dispense(var5, var8);
/* 73:83 */           if ((var11 != null) && (var11.stackSize == 0)) {
/* 74:85 */             var11 = null;
/* 75:   */           }
/* 76:   */         }
/* 77:89 */         var6.setInventorySlotContents(var7, var11);
/* 78:   */       }
/* 79:   */     }
/* 80:   */   }
/* 81:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockDropper
 * JD-Core Version:    0.7.0.1
 */