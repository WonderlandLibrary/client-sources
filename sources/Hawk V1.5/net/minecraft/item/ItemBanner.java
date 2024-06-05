package net.minecraft.item;

import java.util.List;
import net.minecraft.block.BlockStandingSign;
import net.minecraft.block.BlockWallSign;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemBanner extends ItemBlock {
   private static final String __OBFID = "CL_00002181";

   public CreativeTabs getCreativeTab() {
      return CreativeTabs.tabDecorations;
   }

   private EnumDyeColor func_179225_h(ItemStack var1) {
      NBTTagCompound var2 = var1.getSubCompound("BlockEntityTag", false);
      EnumDyeColor var3 = null;
      if (var2 != null && var2.hasKey("Base")) {
         var3 = EnumDyeColor.func_176766_a(var2.getInteger("Base"));
      } else {
         var3 = EnumDyeColor.func_176766_a(var1.getMetadata());
      }

      return var3;
   }

   public ItemBanner() {
      super(Blocks.standing_banner);
      this.maxStackSize = 16;
      this.setCreativeTab(CreativeTabs.tabDecorations);
      this.setHasSubtypes(true);
      this.setMaxDamage(0);
   }

   public String getItemStackDisplayName(ItemStack var1) {
      String var2 = "item.banner.";
      EnumDyeColor var3 = this.func_179225_h(var1);
      var2 = String.valueOf((new StringBuilder(String.valueOf(var2))).append(var3.func_176762_d()).append(".name"));
      return StatCollector.translateToLocal(var2);
   }

   public int getColorFromItemStack(ItemStack var1, int var2) {
      if (var2 == 0) {
         return 16777215;
      } else {
         EnumDyeColor var3 = this.func_179225_h(var1);
         return var3.func_176768_e().colorValue;
      }
   }

   public boolean onItemUse(ItemStack var1, EntityPlayer var2, World var3, BlockPos var4, EnumFacing var5, float var6, float var7, float var8) {
      if (var5 == EnumFacing.DOWN) {
         return false;
      } else if (!var3.getBlockState(var4).getBlock().getMaterial().isSolid()) {
         return false;
      } else {
         var4 = var4.offset(var5);
         if (!var2.func_175151_a(var4, var5, var1)) {
            return false;
         } else if (!Blocks.standing_banner.canPlaceBlockAt(var3, var4)) {
            return false;
         } else if (var3.isRemote) {
            return true;
         } else {
            if (var5 == EnumFacing.UP) {
               int var9 = MathHelper.floor_double((double)((var2.rotationYaw + 180.0F) * 16.0F / 360.0F) + 0.5D) & 15;
               var3.setBlockState(var4, Blocks.standing_banner.getDefaultState().withProperty(BlockStandingSign.ROTATION_PROP, var9), 3);
            } else {
               var3.setBlockState(var4, Blocks.wall_banner.getDefaultState().withProperty(BlockWallSign.field_176412_a, var5), 3);
            }

            --var1.stackSize;
            TileEntity var10 = var3.getTileEntity(var4);
            if (var10 instanceof TileEntityBanner) {
               ((TileEntityBanner)var10).setItemValues(var1);
            }

            return true;
         }
      }
   }

   public void getSubItems(Item var1, CreativeTabs var2, List var3) {
      EnumDyeColor[] var4 = EnumDyeColor.values();
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         EnumDyeColor var7 = var4[var6];
         var3.add(new ItemStack(var1, 1, var7.getDyeColorDamage()));
      }

   }

   public void addInformation(ItemStack var1, EntityPlayer var2, List var3, boolean var4) {
      NBTTagCompound var5 = var1.getSubCompound("BlockEntityTag", false);
      if (var5 != null && var5.hasKey("Patterns")) {
         NBTTagList var6 = var5.getTagList("Patterns", 10);

         for(int var7 = 0; var7 < var6.tagCount() && var7 < 6; ++var7) {
            NBTTagCompound var8 = var6.getCompoundTagAt(var7);
            EnumDyeColor var9 = EnumDyeColor.func_176766_a(var8.getInteger("Color"));
            TileEntityBanner.EnumBannerPattern var10 = TileEntityBanner.EnumBannerPattern.func_177268_a(var8.getString("Pattern"));
            if (var10 != null) {
               var3.add(StatCollector.translateToLocal(String.valueOf((new StringBuilder("item.banner.")).append(var10.func_177271_a()).append(".").append(var9.func_176762_d()))));
            }
         }
      }

   }
}
