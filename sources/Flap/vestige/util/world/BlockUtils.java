package vestige.util.world;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockAnvil;
import net.minecraft.block.BlockBasePressurePlate;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockBrewingStand;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockDropper;
import net.minecraft.block.BlockEnchantmentTable;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockFlowerPot;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.BlockHopper;
import net.minecraft.block.BlockJukebox;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.BlockNote;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.BlockWorkbench;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import vestige.util.IMinecraft;

public class BlockUtils {
   public static boolean replaceable(BlockPos blockPos) {
      return getBlock(blockPos).isReplaceable(IMinecraft.mc.theWorld, blockPos);
   }

   public static Block getBlock(BlockPos blockPos) {
      return getBlockState(blockPos).getBlock();
   }

   public static Block getBlock(double x, double y, double z) {
      return getBlockState(new BlockPos(x, y, z)).getBlock();
   }

   public static IBlockState getBlockState(BlockPos blockPos) {
      return IMinecraft.mc.theWorld.getBlockState(blockPos);
   }

   public static boolean isInteractable(Block block) {
      return block instanceof BlockFurnace || block instanceof BlockTrapDoor || block instanceof BlockDoor || block instanceof BlockJukebox || block instanceof BlockFenceGate || block instanceof BlockChest || block instanceof BlockEnderChest || block instanceof BlockEnchantmentTable || block instanceof BlockBrewingStand || block instanceof BlockBed || block instanceof BlockDropper || block instanceof BlockDispenser || block instanceof BlockHopper || block instanceof BlockAnvil || block instanceof BlockNote || block instanceof BlockWorkbench;
   }

   public static boolean isFullBlock(BlockPos blockPos) {
      AxisAlignedBB axisAlignedBB = getBlock(blockPos) != null ? getBlock(blockPos).getCollisionBoundingBox(IMinecraft.mc.theWorld, blockPos, getState(blockPos)) : null;
      if (axisAlignedBB == null) {
         return false;
      } else {
         return axisAlignedBB.maxX - axisAlignedBB.minX == 1.0D && axisAlignedBB.maxY - axisAlignedBB.minY == 1.0D && axisAlignedBB.maxZ - axisAlignedBB.minZ == 1.0D;
      }
   }

   public static IBlockState getState(BlockPos blockPos) {
      World mc = Minecraft.getMinecraft().theWorld;
      return mc.getBlockState(blockPos);
   }

   public static BlockPos getLowestSolidBlockUnderPlayer(EntityPlayer player) {
      World world = player.worldObj;
      BlockPos playerPos = new BlockPos(player.posX, Math.floor(player.posY), player.posZ);
      int minY = Math.max(playerPos.getY() - 3, 0);
      playerPos.down();

      for(int y = playerPos.getY() - 1; y >= minY; --y) {
         BlockPos blockPos = new BlockPos(playerPos.getX(), y, playerPos.getZ());
         IBlockState blockState = world.getBlockState(blockPos);
         if (blockState.getBlock().isFullBlock()) {
            return blockPos;
         }
      }

      return null;
   }

   public static boolean notFull(Block block) {
      return block instanceof BlockFenceGate || block instanceof BlockLadder || block instanceof BlockFlowerPot || block instanceof BlockBasePressurePlate || isFluid(block) || block instanceof BlockFence || block instanceof BlockAnvil || block instanceof BlockEnchantmentTable || block instanceof BlockChest;
   }

   public static boolean insideBlock() {
      return IMinecraft.mc.thePlayer.ticksExisted < 5 ? false : insideBlock(IMinecraft.mc.thePlayer.getEntityBoundingBox());
   }

   public static boolean insideBlock(@NotNull AxisAlignedBB bb) {
      if (bb == null) {
         $$$reportNull$$$0(0);
      }

      WorldClient world = IMinecraft.mc.theWorld;

      for(int x = MathHelper.floor_double(bb.minX); x < MathHelper.floor_double(bb.maxX) + 1; ++x) {
         for(int y = MathHelper.floor_double(bb.minY); y < MathHelper.floor_double(bb.maxY) + 1; ++y) {
            for(int z = MathHelper.floor_double(bb.minZ); z < MathHelper.floor_double(bb.maxZ) + 1; ++z) {
               Block block = world.getBlockState(new BlockPos(x, y, z)).getBlock();
               AxisAlignedBB boundingBox;
               if (block != null && !(block instanceof BlockAir) && (boundingBox = block.getCollisionBoundingBox(world, new BlockPos(x, y, z), world.getBlockState(new BlockPos(x, y, z)))) != null && bb.intersectsWith(boundingBox)) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   public static boolean isFluid(Block block) {
      return block.getMaterial() == Material.lava || block.getMaterial() == Material.water;
   }

   public static boolean isBlockUnder(int distance) {
      for(int y = (int)IMinecraft.mc.thePlayer.posY; y >= (int)IMinecraft.mc.thePlayer.posY - distance; --y) {
         if (!(IMinecraft.mc.theWorld.getBlockState(new BlockPos(IMinecraft.mc.thePlayer.posX, (double)y, IMinecraft.mc.thePlayer.posZ)).getBlock() instanceof BlockAir)) {
            return true;
         }
      }

      return false;
   }

   public static float getBlockHardness(Block block, ItemStack itemStack, boolean ignoreSlow, boolean ignoreGround) {
      float getBlockHardness = block.getBlockHardness(IMinecraft.mc.theWorld, (BlockPos)null);
      if (getBlockHardness < 0.0F) {
         return 0.0F;
      } else {
         return !block.getMaterial().isToolNotRequired() && (itemStack == null || !itemStack.canHarvestBlock(block)) ? getToolDigEfficiency(itemStack, block, ignoreSlow, ignoreGround) / getBlockHardness / 100.0F : getToolDigEfficiency(itemStack, block, ignoreSlow, ignoreGround) / getBlockHardness / 30.0F;
      }
   }

   public static float getToolDigEfficiency(ItemStack itemStack, Block block, boolean ignoreSlow, boolean ignoreGround) {
      float n = itemStack == null ? 1.0F : itemStack.getItem().getStrVsBlock(itemStack, block);
      if (n > 1.0F) {
         int getEnchantmentLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, itemStack);
         if (getEnchantmentLevel > 0 && itemStack != null) {
            n += (float)(getEnchantmentLevel * getEnchantmentLevel + 1);
         }
      }

      if (IMinecraft.mc.thePlayer.isPotionActive(Potion.digSpeed)) {
         n *= 1.0F + (float)(IMinecraft.mc.thePlayer.getActivePotionEffect(Potion.digSpeed).getAmplifier() + 1) * 0.2F;
      }

      if (!ignoreSlow) {
         if (IMinecraft.mc.thePlayer.isPotionActive(Potion.digSlowdown)) {
            float n2;
            switch(IMinecraft.mc.thePlayer.getActivePotionEffect(Potion.digSlowdown).getAmplifier()) {
            case 0:
               n2 = 0.3F;
               break;
            case 1:
               n2 = 0.09F;
               break;
            case 2:
               n2 = 0.0027F;
               break;
            default:
               n2 = 8.1E-4F;
            }

            n *= n2;
         }

         if (IMinecraft.mc.thePlayer.isInsideOfMaterial(Material.water) && !EnchantmentHelper.getAquaAffinityModifier(IMinecraft.mc.thePlayer)) {
            n /= 5.0F;
         }

         if (!IMinecraft.mc.thePlayer.onGround && !ignoreGround) {
            n /= 5.0F;
         }
      }

      return n;
   }

   public static boolean isBlockUnder() {
      if (IMinecraft.mc.thePlayer.posY < 0.0D) {
         return false;
      } else {
         for(int offset = 0; offset < (int)IMinecraft.mc.thePlayer.posY + 2; offset += 2) {
            AxisAlignedBB bb = IMinecraft.mc.thePlayer.getEntityBoundingBox().offset(0.0D, (double)(-offset), 0.0D);
            if (!IMinecraft.mc.theWorld.getCollidingBoundingBoxes(IMinecraft.mc.thePlayer, bb).isEmpty()) {
               return true;
            }
         }

         return false;
      }
   }

   // $FF: synthetic method
   private static void $$$reportNull$$$0(int var0) {
      throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "bb", "vestige/util/world/BlockUtils", "insideBlock"));
   }
}
