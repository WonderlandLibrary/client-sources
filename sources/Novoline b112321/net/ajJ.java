package net;

import java.util.List;
import java.util.Random;
import net.acE;
import net.minecraft.block.Block;
import net.minecraft.block.Block$EnumOffsetType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class ajJ {
   private static String[] b;

   public static IBlockState E(Block var0) {
      return var0.getDefaultState();
   }

   public static MapColor d(Block var0, IBlockState var1) {
      return var0.getMapColor(var1);
   }

   public static Material D(Block var0) {
      return var0.getMaterial();
   }

   public static Block a(Item var0) {
      return Block.getBlockFromItem(var0);
   }

   public static EnumFacing a(BlockPos var0) {
      return Block.getFacingDirection(var0);
   }

   public static boolean c(Block var0, World var1, BlockPos var2) {
      return var0.isReplaceable(var1, var2);
   }

   public static IBlockState a(Block var0, World var1, BlockPos var2, EnumFacing var3, float var4, float var5, float var6, int var7, EntityLivingBase var8) {
      String[] var9 = b();
      IBlockState var10000 = var0.onBlockPlaced(var1, var2, var3, var4, var5, var6, var7, var8);
      if(acE.b() == null) {
         b(new String[2]);
      }

      return var10000;
   }

   public static void a(Block var0, World var1, BlockPos var2, IBlockState var3, EntityLivingBase var4, ItemStack var5) {
      var0.onBlockPlacedBy(var1, var2, var3, var4, var5);
   }

   public static float d(Block var0, World var1, BlockPos var2) {
      return var0.getBlockHardness(var1, var2);
   }

   public static boolean a(Block var0) {
      return var0.isFullBlock();
   }

   public static int e(Block var0) {
      return Block.getIdFromBlock(var0);
   }

   public static boolean B(Block var0) {
      return var0.isFullCube();
   }

   public static boolean m(Block var0) {
      return var0.isOpaqueCube();
   }

   public static EnumWorldBlockLayer F(Block var0) {
      return var0.getBlockLayer();
   }

   public static int p(Block var0) {
      return var0.getRenderType();
   }

   public static IBlockState a(Block var0, int var1) {
      return var0.getStateFromMeta(var1);
   }

   public static void b(Block var0, IBlockAccess var1, BlockPos var2) {
      var0.setBlockBoundsBasedOnState(var1, var2);
   }

   public static AxisAlignedBB b(Block var0, World var1, BlockPos var2, IBlockState var3) {
      return var0.getCollisionBoundingBox(var1, var2, var3);
   }

   public static int c(Block var0, IBlockState var1) {
      return var0.getMetaFromState(var1);
   }

   public static void a(Block var0, World var1, BlockPos var2, IBlockState var3, Entity var4) {
      var0.onEntityCollidedWithBlock(var1, var2, var3, var4);
   }

   public static Block a(String var0) {
      return Block.getBlockFromName(var0);
   }

   public static Block a(int var0) {
      return Block.getBlockById(var0);
   }

   public static Item b(Block var0, World var1, BlockPos var2) {
      return var0.getItem(var1, var2);
   }

   public static boolean h(Block var0) {
      return var0.isFlowerPot();
   }

   public static int a(Block var0, World var1, BlockPos var2) {
      return var0.getDamageValue(var1, var2);
   }

   public static boolean b(Block var0) {
      return var0.hasTileEntity();
   }

   public static int a(IBlockState var0) {
      return Block.getStateId(var0);
   }

   public static float a(Block var0, EntityPlayer var1, World var2, BlockPos var3) {
      return var0.getPlayerRelativeBlockHardness(var1, var2, var3);
   }

   public static double n(Block var0) {
      return var0.getBlockBoundsMinX();
   }

   public static double I(Block var0) {
      return var0.getBlockBoundsMaxX();
   }

   public static double t(Block var0) {
      return var0.getBlockBoundsMinY();
   }

   public static double l(Block var0) {
      return var0.getBlockBoundsMinZ();
   }

   public static double A(Block var0) {
      return var0.getBlockBoundsMaxZ();
   }

   public static int w(Block var0) {
      return var0.getLightValue();
   }

   public static IBlockState b(int var0) {
      return Block.getStateById(var0);
   }

   public static boolean g(Block var0, World var1, BlockPos var2) {
      return var0.canPlaceBlockAt(var1, var2);
   }

   public static CreativeTabs c(Block var0) {
      return var0.getCreativeTabToDisplayOn();
   }

   public static void a(Block var0, Item var1, CreativeTabs var2, List var3) {
      var0.getSubBlocks(var1, var2, var3);
   }

   public static int r(Block var0) {
      return var0.getLightOpacity();
   }

   public static boolean G(Block var0) {
      return var0.isNormalCube();
   }

   public static double J(Block var0) {
      return var0.getBlockBoundsMaxY();
   }

   public static int b(Block var0, IBlockState var1) {
      return var0.damageDropped(var1);
   }

   public static void b(Block var0, World var1, BlockPos var2, IBlockState var3, Random var4) {
      var0.randomDisplayTick(var1, var2, var3, var4);
   }

   public static boolean z(Block var0) {
      return var0.getTickRandomly();
   }

   public static BlockState x(Block var0) {
      return var0.getBlockState();
   }

   public static boolean u(Block var0) {
      return var0.isSolidFullCube();
   }

   public static IBlockState a(Block var0, IBlockState var1, IBlockAccess var2, BlockPos var3) {
      return var0.getActualState(var1, var2, var3);
   }

   public static boolean g(Block var0) {
      return var0.func_181623_g();
   }

   public static String o(Block var0) {
      return var0.getUnlocalizedName();
   }

   public static void c(Block var0, World var1, BlockPos var2, IBlockState var3) {
      var0.breakBlock(var1, var2, var3);
   }

   public static void a(Block var0, World var1, BlockPos var2, IBlockState var3) {
      var0.onBlockAdded(var1, var2, var3);
   }

   public static void a(Block var0, World var1, BlockPos var2, EntityPlayer var3) {
      var0.onBlockClicked(var1, var2, var3);
   }

   public static void a(Block var0, World var1, BlockPos var2, IBlockState var3, EntityPlayer var4) {
      var0.onBlockHarvested(var1, var2, var3, var4);
   }

   public static void d(Block var0, World var1, BlockPos var2, IBlockState var3) {
      var0.onBlockDestroyedByPlayer(var1, var2, var3);
   }

   public static void a(Block var0, World var1, EntityPlayer var2, BlockPos var3, IBlockState var4, TileEntity var5) {
      var0.harvestBlock(var1, var2, var3, var4, var5);
   }

   public static boolean a(Block var0, World var1, BlockPos var2, IBlockState var3, EntityPlayer var4, EnumFacing var5, float var6, float var7, float var8) {
      String[] var9 = b();
      return var0.onBlockActivated(var1, var2, var3, var4, var5, var6, var7, var8);
   }

   public static void e(Block var0, World var1, BlockPos var2) {
      var0.fillWithRain(var1, var2);
   }

   public static void a(Block var0, World var1, BlockPos var2, IBlockState var3, Random var4) {
      var0.randomTick(var1, var2, var3, var4);
   }

   public static boolean d(Block var0) {
      return var0.requiresUpdates();
   }

   public static void c(Block var0, World var1, BlockPos var2, IBlockState var3, Random var4) {
      var0.updateTick(var1, var2, var3, var4);
   }

   public static boolean a(Block var0, Block var1) {
      return Block.isEqualTo(var0, var1);
   }

   public static boolean a(Block var0, World var1, BlockPos var2, IBlockState var3, int var4, int var5) {
      return var0.onBlockEventReceived(var1, var2, var3, var4, var5);
   }

   public static String i(Block var0) {
      return var0.getLocalizedName();
   }

   public static int d(Block var0, IBlockAccess var1, BlockPos var2) {
      return var0.colorMultiplier(var1, var2);
   }

   public static int a(Block var0, IBlockState var1) {
      return var0.getRenderColor(var1);
   }

   public static boolean H(Block var0) {
      return var0.isVisuallyOpaque();
   }

   public static boolean a(Block var0, IBlockAccess var1, BlockPos var2) {
      return var0.isPassable(var1, var2);
   }

   public static float a(Block var0, Entity var1) {
      return var0.getExplosionResistance(var1);
   }

   public static boolean a(Block var0, Explosion var1) {
      return var0.canDropFromExplosion(var1);
   }

   public static void a(Block var0, World var1, BlockPos var2, IBlockState var3, float var4, int var5) {
      var0.dropBlockAsItemWithChance(var1, var2, var3, var4, var5);
   }

   public static void a(Block var0, World var1, BlockPos var2, Explosion var3) {
      var0.onBlockDestroyedByExplosion(var1, var2, var3);
   }

   public static void a(Block var0, World var1, Entity var2) {
      var0.onLanded(var1, var2);
   }

   public static void a(Block var0, World var1, BlockPos var2, Entity var3) {
      var0.onEntityCollidedWithBlock(var1, var2, var3);
   }

   public static void a(Block var0, World var1, BlockPos var2, Entity var3, float var4) {
      var0.onFallenUpon(var1, var2, var3, var4);
   }

   public static void a() {
      Block.registerBlocks();
   }

   public static boolean v(Block var0) {
      return var0.getUseNeighborBrightness();
   }

   public static int b(Block var0, IBlockAccess var1, BlockPos var2, IBlockState var3, EnumFacing var4) {
      return var0.getStrongPower(var1, var2, var3, var4);
   }

   public static int s(Block var0) {
      return var0.getMobilityFlag();
   }

   public static AxisAlignedBB f(Block var0, World var1, BlockPos var2) {
      return var0.getSelectedBoundingBox(var1, var2);
   }

   public static boolean a(Block var0, IBlockAccess var1, BlockPos var2, EnumFacing var3) {
      return var0.shouldSideBeRendered(var1, var2, var3);
   }

   public static int c(Block var0, IBlockAccess var1, BlockPos var2) {
      return var0.getMixedBrightnessForBlock(var1, var2);
   }

   public static Block$EnumOffsetType y(Block var0) {
      return var0.getOffsetType();
   }

   public static int a(Block var0, IBlockAccess var1, BlockPos var2, int var3) {
      return var0.colorMultiplier(var1, var2, var3);
   }

   public static void f(Block var0) {
      var0.setBlockBoundsForItemRender();
   }

   public static IBlockState e(Block var0, IBlockState var1) {
      return var0.getStateForEntityRender(var1);
   }

   public static float C(Block var0) {
      return var0.getAmbientOcclusionLightValue();
   }

   public static boolean q(Block var0) {
      return var0.isTranslucent();
   }

   public static boolean k(Block var0) {
      return var0.hasComparatorInputOverride();
   }

   public static void a(Block var0, World var1, BlockPos var2, IBlockState var3, int var4) {
      var0.dropBlockAsItem(var1, var2, var3, var4);
   }

   public static void a(Block var0, World var1, BlockPos var2, IBlockState var3, Block var4) {
      var0.onNeighborBlockChange(var1, var2, var3, var4);
   }

   public static boolean a(Block var0, IBlockState var1, boolean var2) {
      return var0.canCollideCheck(var1, var2);
   }

   public static MovingObjectPosition a(Block var0, World var1, BlockPos var2, Vec3 var3, Vec3 var4) {
      return var0.collisionRayTrace(var1, var2, var3, var4);
   }

   public static void a(Block var0, World var1, BlockPos var2, IBlockState var3, AxisAlignedBB var4, List var5, Entity var6) {
      var0.addCollisionBoxesToList(var1, var2, var3, var4, var5, var6);
   }

   public static Vec3 a(Block var0, World var1, BlockPos var2, Entity var3, Vec3 var4) {
      return var0.modifyAcceleration(var1, var2, var3, var4);
   }

   public static boolean a(Block var0, World var1, BlockPos var2, EnumFacing var3, ItemStack var4) {
      return var0.canReplace(var1, var2, var3, var4);
   }

   public static int a(Block var0, IBlockAccess var1, BlockPos var2, IBlockState var3, EnumFacing var4) {
      return var0.getWeakPower(var1, var2, var3, var4);
   }

   public static boolean a(Block var0, World var1, BlockPos var2, EnumFacing var3) {
      return var0.canPlaceBlockOnSide(var1, var2, var3);
   }

   public static void a(World var0, BlockPos var1, ItemStack var2) {
      Block.spawnAsEntity(var0, var1, var2);
   }

   public static boolean j(Block var0) {
      return var0.getEnableStats();
   }

   public static void b(String[] var0) {
      b = var0;
   }

   public static String[] b() {
      return b;
   }

   static {
      if(b() == null) {
         b(new String[5]);
      }

   }
}
