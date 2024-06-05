package hawk.modules.player;

import hawk.events.Event;
import hawk.events.listeners.EventMotion;
import hawk.events.listeners.EventUpdate;
import hawk.modules.Module;
import hawk.util.BlockUtils2;
import hawk.util.RotationUtils;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;

public class Scaffold extends Module {
   private final List validBlocks;
   private final Random rng;
   private final List invalidBlocks;
   private final EnumFacing[] facings;
   public boolean rel = false;
   private final BlockPos[] blockPositions;
   private boolean rotating;
   private int slot;
   private float[] angles;

   public Scaffold() {
      super("Scaffold", 46, Module.Category.MOVEMENT);
      this.invalidBlocks = Arrays.asList(Blocks.enchanting_table, Blocks.furnace, Blocks.carpet, Blocks.crafting_table, Blocks.trapped_chest, Blocks.chest, Blocks.dispenser, Blocks.air, Blocks.water, Blocks.lava, Blocks.flowing_water, Blocks.flowing_lava, Blocks.sand, Blocks.snow_layer, Blocks.torch, Blocks.anvil, Blocks.jukebox, Blocks.stone_button, Blocks.wooden_button, Blocks.lever, Blocks.noteblock, Blocks.stone_pressure_plate, Blocks.light_weighted_pressure_plate, Blocks.wooden_pressure_plate, Blocks.heavy_weighted_pressure_plate, Blocks.stone_slab, Blocks.wooden_slab, Blocks.stone_slab2, Blocks.red_mushroom, Blocks.brown_mushroom, Blocks.yellow_flower, Blocks.red_flower, Blocks.anvil, Blocks.glass_pane, Blocks.stained_glass_pane, Blocks.iron_bars, Blocks.cactus, Blocks.ladder, Blocks.web);
      this.validBlocks = Arrays.asList(Blocks.air, Blocks.water, Blocks.flowing_water, Blocks.lava, Blocks.flowing_lava);
      this.blockPositions = new BlockPos[]{new BlockPos(-1, 0, 0), new BlockPos(1, 0, 0), new BlockPos(0, 0, -1)};
      this.facings = new EnumFacing[]{EnumFacing.EAST, EnumFacing.WEST, EnumFacing.SOUTH, EnumFacing.NORTH};
      this.rng = new Random();
      this.angles = new float[2];
   }

   public void onEnable() {
      this.slot = this.mc.thePlayer.inventory.currentItem;
   }

   private Vec3 getVec3(BlockUtils2.BlockData var1) {
      BlockPos var2 = var1.position;
      EnumFacing var3 = var1.face;
      double var4 = (double)var2.getX() + 0.5D;
      double var6 = (double)var2.getY() + 0.5D;
      double var8 = (double)var2.getZ() + 0.5D;
      var4 += (double)var3.getFrontOffsetX() / 2.0D;
      var8 += (double)var3.getFrontOffsetZ() / 2.0D;
      var6 += (double)var3.getFrontOffsetY() / 2.0D;
      if (var3 != EnumFacing.UP && var3 != EnumFacing.DOWN) {
         var6 += this.randomNumber(0.49D, 0.5D);
      } else {
         var4 += this.randomNumber(0.3D, -0.3D);
         var8 += this.randomNumber(0.3D, -0.3D);
      }

      if (var3 == EnumFacing.WEST || var3 == EnumFacing.EAST) {
         var8 += this.randomNumber(0.3D, -0.3D);
      }

      if (var3 == EnumFacing.SOUTH || var3 == EnumFacing.NORTH) {
         var4 += this.randomNumber(0.3D, -0.3D);
      }

      return new Vec3(var4, var6, var8);
   }

   public void onEvent(Event var1) {
      if (var1 instanceof EventMotion) {
         EventMotion var2 = (EventMotion)var1;
         EntityPlayerSP var3 = this.mc.thePlayer;
         WorldClient var4 = this.mc.theWorld;
         double var5 = 1.0D;
         BlockUtils2.BlockData var7 = null;

         for(double var8 = var3.posY - 1.0D; var8 > 0.0D; --var8) {
            BlockUtils2.BlockData var10 = this.getBlockData(new BlockPos(var3.posX, var8, var3.posZ));
            if (var10 != null) {
               var5 = var3.posY - var8;
               if (var5 <= 3.0D) {
                  var7 = var10;
                  break;
               }
            }
         }

         float[] var13 = RotationUtils.getRotationFromPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ);
         if (var7 != null) {
            BlockPos var9 = var7.position;
            Block var12 = var4.getBlockState(var9.offset(var7.face)).getBlock();
            Vec3 var11 = this.getVec3(var7);
            this.mc.playerController.func_178890_a(var3, var4, var3.getCurrentEquippedItem(), var9, var7.face, var11);
            this.mc.playerController.func_178890_a(var3, var4, var3.getCurrentEquippedItem(), var9, var7.face, var11);
            this.mc.playerController.func_178890_a(var3, var4, var3.getCurrentEquippedItem(), var9, var7.face, var11);
            this.mc.thePlayer.setPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ);
         }
      }

      if (var1 instanceof EventUpdate) {
         this.mc.timer.timerSpeed = 1.1F;
      }

   }

   private double randomNumber(double var1, double var3) {
      return Math.random() * (var1 - var3) + var3;
   }

   private BlockUtils2.BlockData getBlockData(BlockPos var1) {
      BlockPos[] var2 = this.blockPositions;
      EnumFacing[] var3 = this.facings;
      WorldClient var4 = this.mc.theWorld;
      BlockPos var5 = new BlockPos(0, -1, 0);
      List var6 = this.validBlocks;
      if (!var6.contains(var4.getBlockState(var1.add(var5)).getBlock())) {
         return new BlockUtils2.BlockData(var1.add(var5), EnumFacing.UP);
      } else {
         int var7 = 0;

         for(int var8 = var2.length; var7 < var8; ++var7) {
            BlockPos var9 = var1.add(var2[var7]);
            if (!var6.contains(var4.getBlockState(var9).getBlock())) {
               return new BlockUtils2.BlockData(var9, var3[var7]);
            }

            for(int var10 = 0; var10 < var8; ++var10) {
               BlockPos var11 = var1.add(var2[var10]);
               BlockPos var12 = var9.add(var2[var10]);
               if (!var6.contains(var4.getBlockState(var11).getBlock())) {
                  return new BlockUtils2.BlockData(var11, var3[var10]);
               }

               if (!var6.contains(var4.getBlockState(var12).getBlock())) {
                  return new BlockUtils2.BlockData(var12, var3[var10]);
               }
            }
         }

         return null;
      }
   }

   public void onDisable() {
      this.mc.thePlayer.inventory.currentItem = this.slot;
      this.mc.gameSettings.keyBindUseItem.pressed = false;
      this.mc.timer.timerSpeed = 1.0F;
   }
}
