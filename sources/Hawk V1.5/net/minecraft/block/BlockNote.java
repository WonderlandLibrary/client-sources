package net.minecraft.block;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityNote;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class BlockNote extends BlockContainer {
   private static final List field_176434_a = Lists.newArrayList(new String[]{"harp", "bd", "snare", "hat", "bassattack"});
   private static final String __OBFID = "CL_00000278";

   private String func_176433_b(int var1) {
      if (var1 < 0 || var1 >= field_176434_a.size()) {
         var1 = 0;
      }

      return (String)field_176434_a.get(var1);
   }

   public BlockNote() {
      super(Material.wood);
      this.setCreativeTab(CreativeTabs.tabRedstone);
   }

   public void onNeighborBlockChange(World var1, BlockPos var2, IBlockState var3, Block var4) {
      boolean var5 = var1.isBlockPowered(var2);
      TileEntity var6 = var1.getTileEntity(var2);
      if (var6 instanceof TileEntityNote) {
         TileEntityNote var7 = (TileEntityNote)var6;
         if (var7.previousRedstoneState != var5) {
            if (var5) {
               var7.func_175108_a(var1, var2);
            }

            var7.previousRedstoneState = var5;
         }
      }

   }

   public void onBlockClicked(World var1, BlockPos var2, EntityPlayer var3) {
      if (!var1.isRemote) {
         TileEntity var4 = var1.getTileEntity(var2);
         if (var4 instanceof TileEntityNote) {
            ((TileEntityNote)var4).func_175108_a(var1, var2);
         }
      }

   }

   public int getRenderType() {
      return 3;
   }

   public boolean onBlockActivated(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4, EnumFacing var5, float var6, float var7, float var8) {
      if (var1.isRemote) {
         return true;
      } else {
         TileEntity var9 = var1.getTileEntity(var2);
         if (var9 instanceof TileEntityNote) {
            TileEntityNote var10 = (TileEntityNote)var9;
            var10.changePitch();
            var10.func_175108_a(var1, var2);
         }

         return true;
      }
   }

   public TileEntity createNewTileEntity(World var1, int var2) {
      return new TileEntityNote();
   }

   public boolean onBlockEventReceived(World var1, BlockPos var2, IBlockState var3, int var4, int var5) {
      float var6 = (float)Math.pow(2.0D, (double)(var5 - 12) / 12.0D);
      var1.playSoundEffect((double)var2.getX() + 0.5D, (double)var2.getY() + 0.5D, (double)var2.getZ() + 0.5D, String.valueOf((new StringBuilder("note.")).append(this.func_176433_b(var4))), 3.0F, var6);
      var1.spawnParticle(EnumParticleTypes.NOTE, (double)var2.getX() + 0.5D, (double)var2.getY() + 1.2D, (double)var2.getZ() + 0.5D, (double)var5 / 24.0D, 0.0D, 0.0D);
      return true;
   }
}
