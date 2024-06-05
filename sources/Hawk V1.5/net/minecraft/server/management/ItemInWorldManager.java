package net.minecraft.server.management;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.network.play.server.S38PacketPlayerListItem;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldSettings;

public class ItemInWorldManager {
   private int curblockDamage;
   private boolean isDestroyingBlock;
   private int initialBlockDamage;
   private BlockPos field_180240_f;
   public World theWorld;
   public EntityPlayerMP thisPlayerMP;
   private int durabilityRemainingOnBlock;
   private BlockPos field_180241_i;
   private static final String __OBFID = "CL_00001442";
   private WorldSettings.GameType gameType;
   private int initialDamage;
   private boolean receivedFinishDiggingPacket;

   public boolean func_180237_b(BlockPos var1) {
      if (this.gameType.isCreative() && this.thisPlayerMP.getHeldItem() != null && this.thisPlayerMP.getHeldItem().getItem() instanceof ItemSword) {
         return false;
      } else {
         IBlockState var2 = this.theWorld.getBlockState(var1);
         TileEntity var3 = this.theWorld.getTileEntity(var1);
         if (this.gameType.isAdventure()) {
            if (this.gameType == WorldSettings.GameType.SPECTATOR) {
               return false;
            }

            if (!this.thisPlayerMP.func_175142_cm()) {
               ItemStack var4 = this.thisPlayerMP.getCurrentEquippedItem();
               if (var4 == null) {
                  return false;
               }

               if (!var4.canDestroy(var2.getBlock())) {
                  return false;
               }
            }
         }

         this.theWorld.playAuxSFXAtEntity(this.thisPlayerMP, 2001, var1, Block.getStateId(var2));
         boolean var7 = this.func_180235_c(var1);
         if (this.isCreative()) {
            this.thisPlayerMP.playerNetServerHandler.sendPacket(new S23PacketBlockChange(this.theWorld, var1));
         } else {
            ItemStack var5 = this.thisPlayerMP.getCurrentEquippedItem();
            boolean var6 = this.thisPlayerMP.canHarvestBlock(var2.getBlock());
            if (var5 != null) {
               var5.onBlockDestroyed(this.theWorld, var2.getBlock(), var1, this.thisPlayerMP);
               if (var5.stackSize == 0) {
                  this.thisPlayerMP.destroyCurrentEquippedItem();
               }
            }

            if (var7 && var6) {
               var2.getBlock().harvestBlock(this.theWorld, this.thisPlayerMP, var1, var2, var3);
            }
         }

         return var7;
      }
   }

   public void initializeGameType(WorldSettings.GameType var1) {
      if (this.gameType == WorldSettings.GameType.NOT_SET) {
         this.gameType = var1;
      }

      this.setGameType(this.gameType);
   }

   public void setWorld(WorldServer var1) {
      this.theWorld = var1;
   }

   public boolean tryUseItem(EntityPlayer var1, World var2, ItemStack var3) {
      if (this.gameType == WorldSettings.GameType.SPECTATOR) {
         return false;
      } else {
         int var4 = var3.stackSize;
         int var5 = var3.getMetadata();
         ItemStack var6 = var3.useItemRightClick(var2, var1);
         if (var6 != var3 || var6 != null && (var6.stackSize != var4 || var6.getMaxItemUseDuration() > 0 || var6.getMetadata() != var5)) {
            var1.inventory.mainInventory[var1.inventory.currentItem] = var6;
            if (this.isCreative()) {
               var6.stackSize = var4;
               if (var6.isItemStackDamageable()) {
                  var6.setItemDamage(var5);
               }
            }

            if (var6.stackSize == 0) {
               var1.inventory.mainInventory[var1.inventory.currentItem] = null;
            }

            if (!var1.isUsingItem()) {
               ((EntityPlayerMP)var1).sendContainerToPlayer(var1.inventoryContainer);
            }

            return true;
         } else {
            return false;
         }
      }
   }

   public boolean func_180239_c() {
      return this.gameType.isSurvivalOrAdventure();
   }

   public WorldSettings.GameType getGameType() {
      return this.gameType;
   }

   public ItemInWorldManager(World var1) {
      this.gameType = WorldSettings.GameType.NOT_SET;
      this.field_180240_f = BlockPos.ORIGIN;
      this.field_180241_i = BlockPos.ORIGIN;
      this.durabilityRemainingOnBlock = -1;
      this.theWorld = var1;
   }

   private boolean func_180235_c(BlockPos var1) {
      IBlockState var2 = this.theWorld.getBlockState(var1);
      var2.getBlock().onBlockHarvested(this.theWorld, var1, var2, this.thisPlayerMP);
      boolean var3 = this.theWorld.setBlockToAir(var1);
      if (var3) {
         var2.getBlock().onBlockDestroyedByPlayer(this.theWorld, var1, var2);
      }

      return var3;
   }

   public boolean isCreative() {
      return this.gameType.isCreative();
   }

   public void func_180784_a(BlockPos var1, EnumFacing var2) {
      if (this.isCreative()) {
         if (!this.theWorld.func_175719_a((EntityPlayer)null, var1, var2)) {
            this.func_180237_b(var1);
         }
      } else {
         Block var3 = this.theWorld.getBlockState(var1).getBlock();
         if (this.gameType.isAdventure()) {
            if (this.gameType == WorldSettings.GameType.SPECTATOR) {
               return;
            }

            if (!this.thisPlayerMP.func_175142_cm()) {
               ItemStack var4 = this.thisPlayerMP.getCurrentEquippedItem();
               if (var4 == null) {
                  return;
               }

               if (!var4.canDestroy(var3)) {
                  return;
               }
            }
         }

         this.theWorld.func_175719_a((EntityPlayer)null, var1, var2);
         this.initialDamage = this.curblockDamage;
         float var6 = 1.0F;
         if (var3.getMaterial() != Material.air) {
            var3.onBlockClicked(this.theWorld, var1, this.thisPlayerMP);
            var6 = var3.getPlayerRelativeBlockHardness(this.thisPlayerMP, this.thisPlayerMP.worldObj, var1);
         }

         if (var3.getMaterial() != Material.air && var6 >= 1.0F) {
            this.func_180237_b(var1);
         } else {
            this.isDestroyingBlock = true;
            this.field_180240_f = var1;
            int var5 = (int)(var6 * 10.0F);
            this.theWorld.sendBlockBreakProgress(this.thisPlayerMP.getEntityId(), var1, var5);
            this.durabilityRemainingOnBlock = var5;
         }
      }

   }

   public boolean func_180236_a(EntityPlayer var1, World var2, ItemStack var3, BlockPos var4, EnumFacing var5, float var6, float var7, float var8) {
      if (this.gameType == WorldSettings.GameType.SPECTATOR) {
         TileEntity var13 = var2.getTileEntity(var4);
         if (var13 instanceof ILockableContainer) {
            Block var14 = var2.getBlockState(var4).getBlock();
            ILockableContainer var15 = (ILockableContainer)var13;
            if (var15 instanceof TileEntityChest && var14 instanceof BlockChest) {
               var15 = ((BlockChest)var14).getLockableContainer(var2, var4);
            }

            if (var15 != null) {
               var1.displayGUIChest(var15);
               return true;
            }
         } else if (var13 instanceof IInventory) {
            var1.displayGUIChest((IInventory)var13);
            return true;
         }

         return false;
      } else {
         if (!var1.isSneaking() || var1.getHeldItem() == null) {
            IBlockState var9 = var2.getBlockState(var4);
            if (var9.getBlock().onBlockActivated(var2, var4, var9, var1, var5, var6, var7, var8)) {
               return true;
            }
         }

         if (var3 == null) {
            return false;
         } else if (this.isCreative()) {
            int var12 = var3.getMetadata();
            int var10 = var3.stackSize;
            boolean var11 = var3.onItemUse(var1, var2, var4, var5, var6, var7, var8);
            var3.setItemDamage(var12);
            var3.stackSize = var10;
            return var11;
         } else {
            return var3.onItemUse(var1, var2, var4, var5, var6, var7, var8);
         }
      }
   }

   public void func_180785_a(BlockPos var1) {
      if (var1.equals(this.field_180240_f)) {
         int var2 = this.curblockDamage - this.initialDamage;
         Block var3 = this.theWorld.getBlockState(var1).getBlock();
         if (var3.getMaterial() != Material.air) {
            float var4 = var3.getPlayerRelativeBlockHardness(this.thisPlayerMP, this.thisPlayerMP.worldObj, var1) * (float)(var2 + 1);
            if (var4 >= 0.7F) {
               this.isDestroyingBlock = false;
               this.theWorld.sendBlockBreakProgress(this.thisPlayerMP.getEntityId(), var1, -1);
               this.func_180237_b(var1);
            } else if (!this.receivedFinishDiggingPacket) {
               this.isDestroyingBlock = false;
               this.receivedFinishDiggingPacket = true;
               this.field_180241_i = var1;
               this.initialBlockDamage = this.initialDamage;
            }
         }
      }

   }

   public void updateBlockRemoving() {
      ++this.curblockDamage;
      float var1;
      int var2;
      if (this.receivedFinishDiggingPacket) {
         int var3 = this.curblockDamage - this.initialBlockDamage;
         Block var4 = this.theWorld.getBlockState(this.field_180241_i).getBlock();
         if (var4.getMaterial() == Material.air) {
            this.receivedFinishDiggingPacket = false;
         } else {
            var1 = var4.getPlayerRelativeBlockHardness(this.thisPlayerMP, this.thisPlayerMP.worldObj, this.field_180241_i) * (float)(var3 + 1);
            var2 = (int)(var1 * 10.0F);
            if (var2 != this.durabilityRemainingOnBlock) {
               this.theWorld.sendBlockBreakProgress(this.thisPlayerMP.getEntityId(), this.field_180241_i, var2);
               this.durabilityRemainingOnBlock = var2;
            }

            if (var1 >= 1.0F) {
               this.receivedFinishDiggingPacket = false;
               this.func_180237_b(this.field_180241_i);
            }
         }
      } else if (this.isDestroyingBlock) {
         Block var5 = this.theWorld.getBlockState(this.field_180240_f).getBlock();
         if (var5.getMaterial() == Material.air) {
            this.theWorld.sendBlockBreakProgress(this.thisPlayerMP.getEntityId(), this.field_180240_f, -1);
            this.durabilityRemainingOnBlock = -1;
            this.isDestroyingBlock = false;
         } else {
            int var6 = this.curblockDamage - this.initialDamage;
            var1 = var5.getPlayerRelativeBlockHardness(this.thisPlayerMP, this.thisPlayerMP.worldObj, this.field_180241_i) * (float)(var6 + 1);
            var2 = (int)(var1 * 10.0F);
            if (var2 != this.durabilityRemainingOnBlock) {
               this.theWorld.sendBlockBreakProgress(this.thisPlayerMP.getEntityId(), this.field_180240_f, var2);
               this.durabilityRemainingOnBlock = var2;
            }
         }
      }

   }

   public void func_180238_e() {
      this.isDestroyingBlock = false;
      this.theWorld.sendBlockBreakProgress(this.thisPlayerMP.getEntityId(), this.field_180240_f, -1);
   }

   public void setGameType(WorldSettings.GameType var1) {
      this.gameType = var1;
      var1.configurePlayerCapabilities(this.thisPlayerMP.capabilities);
      this.thisPlayerMP.sendPlayerAbilities();
      this.thisPlayerMP.mcServer.getConfigurationManager().sendPacketToAllPlayers(new S38PacketPlayerListItem(S38PacketPlayerListItem.Action.UPDATE_GAME_MODE, new EntityPlayerMP[]{this.thisPlayerMP}));
   }
}
