package net.minecraft.block;

import java.util.Random;
import net.iV;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockFlower$EnumFlowerColor;
import net.minecraft.block.BlockFlower$EnumFlowerType;
import net.minecraft.block.BlockFlowerPot$1;
import net.minecraft.block.BlockFlowerPot$EnumFlowerType;
import net.minecraft.block.BlockPlanks$EnumType;
import net.minecraft.block.BlockTallGrass$EnumType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFlowerPot;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.StatCollector;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockFlowerPot extends BlockContainer {
   public static final iV Q = iV.a("legacy_data", 0, 15);
   public static final PropertyEnum CONTENTS = PropertyEnum.create("contents", BlockFlowerPot$EnumFlowerType.class);

   public BlockFlowerPot() {
      super(Material.circuits);
      this.setDefaultState(this.blockState.getBaseState().withProperty(CONTENTS, BlockFlowerPot$EnumFlowerType.EMPTY).withProperty(Q, Integer.valueOf(0)));
      this.setBlockBoundsForItemRender();
   }

   public String getLocalizedName() {
      return StatCollector.translateToLocal("item.flowerPot.name");
   }

   public void setBlockBoundsForItemRender() {
      float var1 = 0.375F;
      float var2 = 0.1875F;
      this.setBlockBounds(0.3125F, 0.0F, 0.3125F, 0.6875F, 0.375F, 0.6875F);
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public int getRenderType() {
      return 3;
   }

   public boolean isFullCube() {
      return false;
   }

   public int colorMultiplier(IBlockAccess var1, BlockPos var2, int var3) {
      TileEntity var4 = var1.getTileEntity(var2);
      if(var4 instanceof TileEntityFlowerPot) {
         Item var5 = ((TileEntityFlowerPot)var4).getFlowerPotItem();
         if(var5 instanceof ItemBlock) {
            return Block.getBlockFromItem(var5).colorMultiplier(var1, var2, var3);
         }
      }

      return 16777215;
   }

   public boolean onBlockActivated(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4, EnumFacing var5, float var6, float var7, float var8) {
      ItemStack var9 = var4.inventory.getCurrentItem();
      if(var9.getItem() instanceof ItemBlock) {
         TileEntityFlowerPot var10 = this.getTileEntity(var1, var2);
         return false;
      } else {
         return false;
      }
   }

   private boolean canNotContain(Block var1, int var2) {
      return var1 == Blocks.yellow_flower || var1 == Blocks.red_flower || var1 == Blocks.cactus || var1 == Blocks.brown_mushroom || var1 == Blocks.red_mushroom || var1 == Blocks.sapling || var1 == Blocks.deadbush || var1 == Blocks.tallgrass && var2 == BlockTallGrass$EnumType.FERN.getMeta();
   }

   public Item getItem(World var1, BlockPos var2) {
      TileEntityFlowerPot var3 = this.getTileEntity(var1, var2);
      return var3.getFlowerPotItem() != null?var3.getFlowerPotItem():Items.flower_pot;
   }

   public int getDamageValue(World var1, BlockPos var2) {
      TileEntityFlowerPot var3 = this.getTileEntity(var1, var2);
      return var3.getFlowerPotItem() != null?var3.getFlowerPotData():0;
   }

   public boolean isFlowerPot() {
      return true;
   }

   public boolean canPlaceBlockAt(World var1, BlockPos var2) {
      return super.canPlaceBlockAt(var1, var2) && World.doesBlockHaveSolidTopSurface(var1, var2.down());
   }

   public void onNeighborBlockChange(World var1, BlockPos var2, IBlockState var3, Block var4) {
      if(!World.doesBlockHaveSolidTopSurface(var1, var2.down())) {
         this.dropBlockAsItem(var1, var2, var3, 0);
         var1.setBlockToAir(var2);
      }

   }

   public void breakBlock(World var1, BlockPos var2, IBlockState var3) {
      TileEntityFlowerPot var4 = this.getTileEntity(var1, var2);
      if(var4.getFlowerPotItem() != null) {
         spawnAsEntity(var1, var2, new ItemStack(var4.getFlowerPotItem(), 1, var4.getFlowerPotData()));
      }

      super.breakBlock(var1, var2, var3);
   }

   public void onBlockHarvested(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4) {
      super.onBlockHarvested(var1, var2, var3, var4);
      if(var4.abilities.isCreative()) {
         TileEntityFlowerPot var5 = this.getTileEntity(var1, var2);
         var5.setFlowerPotData((Item)null, 0);
      }

   }

   public Item getItemDropped(IBlockState var1, Random var2, int var3) {
      return Items.flower_pot;
   }

   private TileEntityFlowerPot getTileEntity(World var1, BlockPos var2) {
      TileEntity var3 = var1.getTileEntity(var2);
      return var3 instanceof TileEntityFlowerPot?(TileEntityFlowerPot)var3:null;
   }

   public TileEntity createNewTileEntity(World var1, int var2) {
      Object var3 = null;
      int var4 = 0;
      switch(var2) {
      case 1:
         var3 = Blocks.red_flower;
         var4 = BlockFlower$EnumFlowerType.POPPY.getMeta();
         break;
      case 2:
         var3 = Blocks.yellow_flower;
         break;
      case 3:
         var3 = Blocks.sapling;
         var4 = BlockPlanks$EnumType.OAK.getMetadata();
         break;
      case 4:
         var3 = Blocks.sapling;
         var4 = BlockPlanks$EnumType.SPRUCE.getMetadata();
         break;
      case 5:
         var3 = Blocks.sapling;
         var4 = BlockPlanks$EnumType.BIRCH.getMetadata();
         break;
      case 6:
         var3 = Blocks.sapling;
         var4 = BlockPlanks$EnumType.JUNGLE.getMetadata();
         break;
      case 7:
         var3 = Blocks.red_mushroom;
         break;
      case 8:
         var3 = Blocks.brown_mushroom;
         break;
      case 9:
         var3 = Blocks.cactus;
         break;
      case 10:
         var3 = Blocks.deadbush;
         break;
      case 11:
         var3 = Blocks.tallgrass;
         var4 = BlockTallGrass$EnumType.FERN.getMeta();
         break;
      case 12:
         var3 = Blocks.sapling;
         var4 = BlockPlanks$EnumType.ACACIA.getMetadata();
         break;
      case 13:
         var3 = Blocks.sapling;
         var4 = BlockPlanks$EnumType.DARK_OAK.getMetadata();
      }

      return new TileEntityFlowerPot(Item.getItemFromBlock((Block)var3), var4);
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{CONTENTS, Q});
   }

   public int getMetaFromState(IBlockState var1) {
      return ((Integer)var1.getValue(Q)).intValue();
   }

   public IBlockState getActualState(IBlockState var1, IBlockAccess var2, BlockPos var3) {
      BlockFlowerPot$EnumFlowerType var4 = BlockFlowerPot$EnumFlowerType.EMPTY;
      TileEntity var5 = var2.getTileEntity(var3);
      if(var5 instanceof TileEntityFlowerPot) {
         TileEntityFlowerPot var6 = (TileEntityFlowerPot)var5;
         Item var7 = var6.getFlowerPotItem();
         if(var7 instanceof ItemBlock) {
            int var8 = var6.getFlowerPotData();
            Block var9 = Block.getBlockFromItem(var7);
            if(var9 == Blocks.sapling) {
               switch(BlockFlowerPot$1.$SwitchMap$net$minecraft$block$BlockPlanks$EnumType[BlockPlanks$EnumType.byMetadata(var8).ordinal()]) {
               case 1:
                  var4 = BlockFlowerPot$EnumFlowerType.OAK_SAPLING;
                  break;
               case 2:
                  var4 = BlockFlowerPot$EnumFlowerType.SPRUCE_SAPLING;
                  break;
               case 3:
                  var4 = BlockFlowerPot$EnumFlowerType.BIRCH_SAPLING;
                  break;
               case 4:
                  var4 = BlockFlowerPot$EnumFlowerType.JUNGLE_SAPLING;
                  break;
               case 5:
                  var4 = BlockFlowerPot$EnumFlowerType.ACACIA_SAPLING;
                  break;
               case 6:
                  var4 = BlockFlowerPot$EnumFlowerType.DARK_OAK_SAPLING;
                  break;
               default:
                  var4 = BlockFlowerPot$EnumFlowerType.EMPTY;
               }
            } else if(var9 == Blocks.tallgrass) {
               switch(var8) {
               case 0:
                  var4 = BlockFlowerPot$EnumFlowerType.DEAD_BUSH;
                  break;
               case 2:
                  var4 = BlockFlowerPot$EnumFlowerType.FERN;
                  break;
               default:
                  var4 = BlockFlowerPot$EnumFlowerType.EMPTY;
               }
            } else if(var9 == Blocks.yellow_flower) {
               var4 = BlockFlowerPot$EnumFlowerType.DANDELION;
            } else if(var9 == Blocks.red_flower) {
               switch(BlockFlowerPot$1.$SwitchMap$net$minecraft$block$BlockFlower$EnumFlowerType[BlockFlower$EnumFlowerType.getType(BlockFlower$EnumFlowerColor.RED, var8).ordinal()]) {
               case 1:
                  var4 = BlockFlowerPot$EnumFlowerType.POPPY;
                  break;
               case 2:
                  var4 = BlockFlowerPot$EnumFlowerType.BLUE_ORCHID;
                  break;
               case 3:
                  var4 = BlockFlowerPot$EnumFlowerType.ALLIUM;
                  break;
               case 4:
                  var4 = BlockFlowerPot$EnumFlowerType.HOUSTONIA;
                  break;
               case 5:
                  var4 = BlockFlowerPot$EnumFlowerType.RED_TULIP;
                  break;
               case 6:
                  var4 = BlockFlowerPot$EnumFlowerType.ORANGE_TULIP;
                  break;
               case 7:
                  var4 = BlockFlowerPot$EnumFlowerType.WHITE_TULIP;
                  break;
               case 8:
                  var4 = BlockFlowerPot$EnumFlowerType.PINK_TULIP;
                  break;
               case 9:
                  var4 = BlockFlowerPot$EnumFlowerType.OXEYE_DAISY;
                  break;
               default:
                  var4 = BlockFlowerPot$EnumFlowerType.EMPTY;
               }
            } else if(var9 == Blocks.red_mushroom) {
               var4 = BlockFlowerPot$EnumFlowerType.MUSHROOM_RED;
            } else if(var9 == Blocks.brown_mushroom) {
               var4 = BlockFlowerPot$EnumFlowerType.MUSHROOM_BROWN;
            } else if(var9 == Blocks.deadbush) {
               var4 = BlockFlowerPot$EnumFlowerType.DEAD_BUSH;
            } else if(var9 == Blocks.cactus) {
               var4 = BlockFlowerPot$EnumFlowerType.CACTUS;
            }
         }
      }

      return var1.withProperty(CONTENTS, var4);
   }

   public EnumWorldBlockLayer getBlockLayer() {
      return EnumWorldBlockLayer.CUTOUT;
   }
}
