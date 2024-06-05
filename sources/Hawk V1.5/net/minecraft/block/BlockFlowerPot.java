package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
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
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockFlowerPot extends BlockContainer {
   private static final String __OBFID = "CL_00000247";
   public static final PropertyInteger field_176444_a = PropertyInteger.create("legacy_data", 0, 15);
   public static final PropertyEnum field_176443_b = PropertyEnum.create("contents", BlockFlowerPot.EnumFlowerType.class);

   public int getMetaFromState(IBlockState var1) {
      return (Integer)var1.getValue(field_176444_a);
   }

   public void setBlockBoundsForItemRender() {
      float var1 = 0.375F;
      float var2 = var1 / 2.0F;
      this.setBlockBounds(0.5F - var2, 0.0F, 0.5F - var2, 0.5F + var2, var1, 0.5F + var2);
   }

   public boolean isFullCube() {
      return false;
   }

   public BlockFlowerPot() {
      super(Material.circuits);
      this.setDefaultState(this.blockState.getBaseState().withProperty(field_176443_b, BlockFlowerPot.EnumFlowerType.EMPTY).withProperty(field_176444_a, 0));
      this.setBlockBoundsForItemRender();
   }

   public boolean canPlaceBlockAt(World var1, BlockPos var2) {
      return super.canPlaceBlockAt(var1, var2) && World.doesBlockHaveSolidTopSurface(var1, var2.offsetDown());
   }

   public Item getItemDropped(IBlockState var1, Random var2, int var3) {
      return Items.flower_pot;
   }

   private boolean func_149928_a(Block var1, int var2) {
      return var1 != Blocks.yellow_flower && var1 != Blocks.red_flower && var1 != Blocks.cactus && var1 != Blocks.brown_mushroom && var1 != Blocks.red_mushroom && var1 != Blocks.sapling && var1 != Blocks.deadbush ? var1 == Blocks.tallgrass && var2 == BlockTallGrass.EnumType.FERN.func_177044_a() : true;
   }

   public IBlockState getActualState(IBlockState var1, IBlockAccess var2, BlockPos var3) {
      BlockFlowerPot.EnumFlowerType var4 = BlockFlowerPot.EnumFlowerType.EMPTY;
      TileEntity var5 = var2.getTileEntity(var3);
      if (var5 instanceof TileEntityFlowerPot) {
         TileEntityFlowerPot var6 = (TileEntityFlowerPot)var5;
         Item var7 = var6.getFlowerPotItem();
         if (var7 instanceof ItemBlock) {
            int var8 = var6.getFlowerPotData();
            Block var9 = Block.getBlockFromItem(var7);
            if (var9 == Blocks.sapling) {
               switch(BlockPlanks.EnumType.func_176837_a(var8)) {
               case OAK:
                  var4 = BlockFlowerPot.EnumFlowerType.OAK_SAPLING;
                  break;
               case SPRUCE:
                  var4 = BlockFlowerPot.EnumFlowerType.SPRUCE_SAPLING;
                  break;
               case BIRCH:
                  var4 = BlockFlowerPot.EnumFlowerType.BIRCH_SAPLING;
                  break;
               case JUNGLE:
                  var4 = BlockFlowerPot.EnumFlowerType.JUNGLE_SAPLING;
                  break;
               case ACACIA:
                  var4 = BlockFlowerPot.EnumFlowerType.ACACIA_SAPLING;
                  break;
               case DARK_OAK:
                  var4 = BlockFlowerPot.EnumFlowerType.DARK_OAK_SAPLING;
                  break;
               default:
                  var4 = BlockFlowerPot.EnumFlowerType.EMPTY;
               }
            } else if (var9 == Blocks.tallgrass) {
               switch(var8) {
               case 0:
                  var4 = BlockFlowerPot.EnumFlowerType.DEAD_BUSH;
                  break;
               case 1:
               default:
                  var4 = BlockFlowerPot.EnumFlowerType.EMPTY;
                  break;
               case 2:
                  var4 = BlockFlowerPot.EnumFlowerType.FERN;
               }
            } else if (var9 == Blocks.yellow_flower) {
               var4 = BlockFlowerPot.EnumFlowerType.DANDELION;
            } else if (var9 == Blocks.red_flower) {
               switch(BlockFlower.EnumFlowerType.func_176967_a(BlockFlower.EnumFlowerColor.RED, var8)) {
               case POPPY:
                  var4 = BlockFlowerPot.EnumFlowerType.POPPY;
                  break;
               case BLUE_ORCHID:
                  var4 = BlockFlowerPot.EnumFlowerType.BLUE_ORCHID;
                  break;
               case ALLIUM:
                  var4 = BlockFlowerPot.EnumFlowerType.ALLIUM;
                  break;
               case HOUSTONIA:
                  var4 = BlockFlowerPot.EnumFlowerType.HOUSTONIA;
                  break;
               case RED_TULIP:
                  var4 = BlockFlowerPot.EnumFlowerType.RED_TULIP;
                  break;
               case ORANGE_TULIP:
                  var4 = BlockFlowerPot.EnumFlowerType.ORANGE_TULIP;
                  break;
               case WHITE_TULIP:
                  var4 = BlockFlowerPot.EnumFlowerType.WHITE_TULIP;
                  break;
               case PINK_TULIP:
                  var4 = BlockFlowerPot.EnumFlowerType.PINK_TULIP;
                  break;
               case OXEYE_DAISY:
                  var4 = BlockFlowerPot.EnumFlowerType.OXEYE_DAISY;
                  break;
               default:
                  var4 = BlockFlowerPot.EnumFlowerType.EMPTY;
               }
            } else if (var9 == Blocks.red_mushroom) {
               var4 = BlockFlowerPot.EnumFlowerType.MUSHROOM_RED;
            } else if (var9 == Blocks.brown_mushroom) {
               var4 = BlockFlowerPot.EnumFlowerType.MUSHROOM_BROWN;
            } else if (var9 == Blocks.deadbush) {
               var4 = BlockFlowerPot.EnumFlowerType.DEAD_BUSH;
            } else if (var9 == Blocks.cactus) {
               var4 = BlockFlowerPot.EnumFlowerType.CACTUS;
            }
         }
      }

      return var1.withProperty(field_176443_b, var4);
   }

   private TileEntityFlowerPot func_176442_d(World var1, BlockPos var2) {
      TileEntity var3 = var1.getTileEntity(var2);
      return var3 instanceof TileEntityFlowerPot ? (TileEntityFlowerPot)var3 : null;
   }

   public EnumWorldBlockLayer getBlockLayer() {
      return EnumWorldBlockLayer.CUTOUT;
   }

   public Item getItem(World var1, BlockPos var2) {
      TileEntityFlowerPot var3 = this.func_176442_d(var1, var2);
      return var3 != null && var3.getFlowerPotItem() != null ? var3.getFlowerPotItem() : Items.flower_pot;
   }

   public void onNeighborBlockChange(World var1, BlockPos var2, IBlockState var3, Block var4) {
      if (!World.doesBlockHaveSolidTopSurface(var1, var2.offsetDown())) {
         this.dropBlockAsItem(var1, var2, var3, 0);
         var1.setBlockToAir(var2);
      }

   }

   public void onBlockHarvested(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4) {
      super.onBlockHarvested(var1, var2, var3, var4);
      if (var4.capabilities.isCreativeMode) {
         TileEntityFlowerPot var5 = this.func_176442_d(var1, var2);
         if (var5 != null) {
            var5.func_145964_a((Item)null, 0);
         }
      }

   }

   public TileEntity createNewTileEntity(World var1, int var2) {
      Object var3 = null;
      int var4 = 0;
      switch(var2) {
      case 1:
         var3 = Blocks.red_flower;
         var4 = BlockFlower.EnumFlowerType.POPPY.func_176968_b();
         break;
      case 2:
         var3 = Blocks.yellow_flower;
         break;
      case 3:
         var3 = Blocks.sapling;
         var4 = BlockPlanks.EnumType.OAK.func_176839_a();
         break;
      case 4:
         var3 = Blocks.sapling;
         var4 = BlockPlanks.EnumType.SPRUCE.func_176839_a();
         break;
      case 5:
         var3 = Blocks.sapling;
         var4 = BlockPlanks.EnumType.BIRCH.func_176839_a();
         break;
      case 6:
         var3 = Blocks.sapling;
         var4 = BlockPlanks.EnumType.JUNGLE.func_176839_a();
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
         var4 = BlockTallGrass.EnumType.FERN.func_177044_a();
         break;
      case 12:
         var3 = Blocks.sapling;
         var4 = BlockPlanks.EnumType.ACACIA.func_176839_a();
         break;
      case 13:
         var3 = Blocks.sapling;
         var4 = BlockPlanks.EnumType.DARK_OAK.func_176839_a();
      }

      return new TileEntityFlowerPot(Item.getItemFromBlock((Block)var3), var4);
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public int colorMultiplier(IBlockAccess var1, BlockPos var2, int var3) {
      TileEntity var4 = var1.getTileEntity(var2);
      if (var4 instanceof TileEntityFlowerPot) {
         Item var5 = ((TileEntityFlowerPot)var4).getFlowerPotItem();
         if (var5 instanceof ItemBlock) {
            return Block.getBlockFromItem(var5).colorMultiplier(var1, var2, var3);
         }
      }

      return 16777215;
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{field_176443_b, field_176444_a});
   }

   public boolean onBlockActivated(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4, EnumFacing var5, float var6, float var7, float var8) {
      ItemStack var9 = var4.inventory.getCurrentItem();
      if (var9 != null && var9.getItem() instanceof ItemBlock) {
         TileEntityFlowerPot var10 = this.func_176442_d(var1, var2);
         if (var10 == null) {
            return false;
         } else if (var10.getFlowerPotItem() != null) {
            return false;
         } else {
            Block var11 = Block.getBlockFromItem(var9.getItem());
            if (!this.func_149928_a(var11, var9.getMetadata())) {
               return false;
            } else {
               var10.func_145964_a(var9.getItem(), var9.getMetadata());
               var10.markDirty();
               var1.markBlockForUpdate(var2);
               if (!var4.capabilities.isCreativeMode && --var9.stackSize <= 0) {
                  var4.inventory.setInventorySlotContents(var4.inventory.currentItem, (ItemStack)null);
               }

               return true;
            }
         }
      } else {
         return false;
      }
   }

   public int getDamageValue(World var1, BlockPos var2) {
      TileEntityFlowerPot var3 = this.func_176442_d(var1, var2);
      return var3 != null && var3.getFlowerPotItem() != null ? var3.getFlowerPotData() : 0;
   }

   public int getRenderType() {
      return 3;
   }

   public void breakBlock(World var1, BlockPos var2, IBlockState var3) {
      TileEntityFlowerPot var4 = this.func_176442_d(var1, var2);
      if (var4 != null && var4.getFlowerPotItem() != null) {
         spawnAsEntity(var1, var2, new ItemStack(var4.getFlowerPotItem(), 1, var4.getFlowerPotData()));
      }

      super.breakBlock(var1, var2, var3);
   }

   public boolean isFlowerPot() {
      return true;
   }

   public static enum EnumFlowerType implements IStringSerializable {
      WHITE_TULIP("WHITE_TULIP", 7, "white_tulip");

      private static final String __OBFID = "CL_00002115";
      MUSHROOM_BROWN("MUSHROOM_BROWN", 18, "mushroom_brown"),
      PINK_TULIP("PINK_TULIP", 8, "pink_tulip"),
      HOUSTONIA("HOUSTONIA", 4, "houstonia"),
      DARK_OAK_SAPLING("DARK_OAK_SAPLING", 16, "dark_oak_sapling");

      private static final BlockFlowerPot.EnumFlowerType[] $VALUES = new BlockFlowerPot.EnumFlowerType[]{EMPTY, POPPY, BLUE_ORCHID, ALLIUM, HOUSTONIA, RED_TULIP, ORANGE_TULIP, WHITE_TULIP, PINK_TULIP, OXEYE_DAISY, DANDELION, OAK_SAPLING, SPRUCE_SAPLING, BIRCH_SAPLING, JUNGLE_SAPLING, ACACIA_SAPLING, DARK_OAK_SAPLING, MUSHROOM_RED, MUSHROOM_BROWN, DEAD_BUSH, FERN, CACTUS};
      MUSHROOM_RED("MUSHROOM_RED", 17, "mushroom_red"),
      OAK_SAPLING("OAK_SAPLING", 11, "oak_sapling"),
      SPRUCE_SAPLING("SPRUCE_SAPLING", 12, "spruce_sapling");

      private static final BlockFlowerPot.EnumFlowerType[] ENUM$VALUES = new BlockFlowerPot.EnumFlowerType[]{EMPTY, POPPY, BLUE_ORCHID, ALLIUM, HOUSTONIA, RED_TULIP, ORANGE_TULIP, WHITE_TULIP, PINK_TULIP, OXEYE_DAISY, DANDELION, OAK_SAPLING, SPRUCE_SAPLING, BIRCH_SAPLING, JUNGLE_SAPLING, ACACIA_SAPLING, DARK_OAK_SAPLING, MUSHROOM_RED, MUSHROOM_BROWN, DEAD_BUSH, FERN, CACTUS};
      EMPTY("EMPTY", 0, "empty");

      private final String field_177006_w;
      DEAD_BUSH("DEAD_BUSH", 19, "dead_bush"),
      BLUE_ORCHID("BLUE_ORCHID", 2, "blue_orchid"),
      BIRCH_SAPLING("BIRCH_SAPLING", 13, "birch_sapling"),
      POPPY("POPPY", 1, "rose"),
      DANDELION("DANDELION", 10, "dandelion"),
      ACACIA_SAPLING("ACACIA_SAPLING", 15, "acacia_sapling"),
      OXEYE_DAISY("OXEYE_DAISY", 9, "oxeye_daisy"),
      ALLIUM("ALLIUM", 3, "allium"),
      RED_TULIP("RED_TULIP", 5, "red_tulip"),
      CACTUS("CACTUS", 21, "cactus"),
      JUNGLE_SAPLING("JUNGLE_SAPLING", 14, "jungle_sapling"),
      FERN("FERN", 20, "fern"),
      ORANGE_TULIP("ORANGE_TULIP", 6, "orange_tulip");

      public String getName() {
         return this.field_177006_w;
      }

      public String toString() {
         return this.field_177006_w;
      }

      private EnumFlowerType(String var3, int var4, String var5) {
         this.field_177006_w = var5;
      }
   }

   static final class SwitchEnumType {
      static final int[] field_180353_a;
      static final int[] field_180352_b = new int[BlockFlower.EnumFlowerType.values().length];
      private static final String __OBFID = "CL_00002116";

      static {
         try {
            field_180352_b[BlockFlower.EnumFlowerType.POPPY.ordinal()] = 1;
         } catch (NoSuchFieldError var15) {
         }

         try {
            field_180352_b[BlockFlower.EnumFlowerType.BLUE_ORCHID.ordinal()] = 2;
         } catch (NoSuchFieldError var14) {
         }

         try {
            field_180352_b[BlockFlower.EnumFlowerType.ALLIUM.ordinal()] = 3;
         } catch (NoSuchFieldError var13) {
         }

         try {
            field_180352_b[BlockFlower.EnumFlowerType.HOUSTONIA.ordinal()] = 4;
         } catch (NoSuchFieldError var12) {
         }

         try {
            field_180352_b[BlockFlower.EnumFlowerType.RED_TULIP.ordinal()] = 5;
         } catch (NoSuchFieldError var11) {
         }

         try {
            field_180352_b[BlockFlower.EnumFlowerType.ORANGE_TULIP.ordinal()] = 6;
         } catch (NoSuchFieldError var10) {
         }

         try {
            field_180352_b[BlockFlower.EnumFlowerType.WHITE_TULIP.ordinal()] = 7;
         } catch (NoSuchFieldError var9) {
         }

         try {
            field_180352_b[BlockFlower.EnumFlowerType.PINK_TULIP.ordinal()] = 8;
         } catch (NoSuchFieldError var8) {
         }

         try {
            field_180352_b[BlockFlower.EnumFlowerType.OXEYE_DAISY.ordinal()] = 9;
         } catch (NoSuchFieldError var7) {
         }

         field_180353_a = new int[BlockPlanks.EnumType.values().length];

         try {
            field_180353_a[BlockPlanks.EnumType.OAK.ordinal()] = 1;
         } catch (NoSuchFieldError var6) {
         }

         try {
            field_180353_a[BlockPlanks.EnumType.SPRUCE.ordinal()] = 2;
         } catch (NoSuchFieldError var5) {
         }

         try {
            field_180353_a[BlockPlanks.EnumType.BIRCH.ordinal()] = 3;
         } catch (NoSuchFieldError var4) {
         }

         try {
            field_180353_a[BlockPlanks.EnumType.JUNGLE.ordinal()] = 4;
         } catch (NoSuchFieldError var3) {
         }

         try {
            field_180353_a[BlockPlanks.EnumType.ACACIA.ordinal()] = 5;
         } catch (NoSuchFieldError var2) {
         }

         try {
            field_180353_a[BlockPlanks.EnumType.DARK_OAK.ordinal()] = 6;
         } catch (NoSuchFieldError var1) {
         }

      }
   }
}
