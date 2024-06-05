package net.minecraft.client.multiplayer;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0EPacketClickWindow;
import net.minecraft.network.play.client.C10PacketCreativeInventoryAction;
import net.minecraft.network.play.client.C11PacketEnchantItem;
import net.minecraft.stats.StatFileWriter;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings;

public class PlayerControllerMP {
   private ItemStack currentItemHittingBlock;
   private int currentPlayerItem;
   private BlockPos field_178895_c = new BlockPos(-1, -1, -1);
   private final NetHandlerPlayClient netClientHandler;
   private int blockHitDelay;
   private float curBlockDamageMP;
   private float stepSoundTickCounter;
   private WorldSettings.GameType currentGameType;
   private static final String __OBFID = "CL_00000881";
   private boolean isHittingBlock;
   private final Minecraft mc;

   private boolean func_178893_a(BlockPos var1) {
      ItemStack var2 = this.mc.thePlayer.getHeldItem();
      boolean var3 = this.currentItemHittingBlock == null && var2 == null;
      if (this.currentItemHittingBlock != null && var2 != null) {
         var3 = var2.getItem() == this.currentItemHittingBlock.getItem() && ItemStack.areItemStackTagsEqual(var2, this.currentItemHittingBlock) && (var2.isItemStackDamageable() || var2.getMetadata() == this.currentItemHittingBlock.getMetadata());
      }

      return var1.equals(this.field_178895_c) && var3;
   }

   public PlayerControllerMP(Minecraft var1, NetHandlerPlayClient var2) {
      this.currentGameType = WorldSettings.GameType.SURVIVAL;
      this.mc = var1;
      this.netClientHandler = var2;
   }

   public boolean gameIsSurvivalOrAdventure() {
      return this.currentGameType.isSurvivalOrAdventure();
   }

   public boolean shouldDrawHUD() {
      return this.currentGameType.isSurvivalOrAdventure();
   }

   public EntityPlayerSP func_178892_a(World var1, StatFileWriter var2) {
      return new EntityPlayerSP(this.mc, var1, this.netClientHandler, var2);
   }

   public boolean func_180512_c(BlockPos var1, EnumFacing var2) {
      this.syncCurrentPlayItem();
      if (this.blockHitDelay > 0) {
         --this.blockHitDelay;
         return true;
      } else if (this.currentGameType.isCreative() && this.mc.theWorld.getWorldBorder().contains(var1)) {
         this.blockHitDelay = 5;
         this.netClientHandler.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, var1, var2));
         func_178891_a(this.mc, this, var1, var2);
         return true;
      } else if (this.func_178893_a(var1)) {
         Block var3 = this.mc.theWorld.getBlockState(var1).getBlock();
         if (var3.getMaterial() == Material.air) {
            this.isHittingBlock = false;
            return false;
         } else {
            this.curBlockDamageMP += var3.getPlayerRelativeBlockHardness(this.mc.thePlayer, this.mc.thePlayer.worldObj, var1);
            if (this.stepSoundTickCounter % 4.0F == 0.0F) {
               this.mc.getSoundHandler().playSound(new PositionedSoundRecord(new ResourceLocation(var3.stepSound.getStepSound()), (var3.stepSound.getVolume() + 1.0F) / 8.0F, var3.stepSound.getFrequency() * 0.5F, (float)var1.getX() + 0.5F, (float)var1.getY() + 0.5F, (float)var1.getZ() + 0.5F));
            }

            ++this.stepSoundTickCounter;
            if (this.curBlockDamageMP >= 1.0F) {
               this.isHittingBlock = false;
               this.netClientHandler.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, var1, var2));
               this.func_178888_a(var1, var2);
               this.curBlockDamageMP = 0.0F;
               this.stepSoundTickCounter = 0.0F;
               this.blockHitDelay = 5;
            }

            this.mc.theWorld.sendBlockBreakProgress(this.mc.thePlayer.getEntityId(), this.field_178895_c, (int)(this.curBlockDamageMP * 10.0F) - 1);
            return true;
         }
      } else {
         return this.func_180511_b(var1, var2);
      }
   }

   public float getBlockReachDistance() {
      return this.currentGameType.isCreative() ? 5.0F : 4.5F;
   }

   public boolean sendUseItem(EntityPlayer var1, World var2, ItemStack var3) {
      if (this.currentGameType == WorldSettings.GameType.SPECTATOR) {
         return false;
      } else {
         this.syncCurrentPlayItem();
         this.netClientHandler.addToSendQueue(new C08PacketPlayerBlockPlacement(var1.inventory.getCurrentItem()));
         int var4 = var3.stackSize;
         ItemStack var5 = var3.useItemRightClick(var2, var1);
         if (var5 == var3 && (var5 == null || var5.stackSize == var4)) {
            return false;
         } else {
            var1.inventory.mainInventory[var1.inventory.currentItem] = var5;
            if (var5.stackSize == 0) {
               var1.inventory.mainInventory[var1.inventory.currentItem] = null;
            }

            return true;
         }
      }
   }

   public boolean enableEverythingIsScrewedUpMode() {
      return this.currentGameType == WorldSettings.GameType.SPECTATOR;
   }

   private void syncCurrentPlayItem() {
      int var1 = this.mc.thePlayer.inventory.currentItem;
      if (var1 != this.currentPlayerItem) {
         this.currentPlayerItem = var1;
         this.netClientHandler.addToSendQueue(new C09PacketHeldItemChange(this.currentPlayerItem));
      }

   }

   public void onStoppedUsingItem(EntityPlayer var1) {
      this.syncCurrentPlayItem();
      this.netClientHandler.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
      var1.stopUsingItem();
   }

   public boolean isInCreativeMode() {
      return this.currentGameType.isCreative();
   }

   public void sendEnchantPacket(int var1, int var2) {
      this.netClientHandler.addToSendQueue(new C11PacketEnchantItem(var1, var2));
   }

   public WorldSettings.GameType func_178889_l() {
      return this.currentGameType;
   }

   public boolean isSpectatorMode() {
      return this.currentGameType == WorldSettings.GameType.SPECTATOR;
   }

   public boolean func_178888_a(BlockPos var1, EnumFacing var2) {
      if (this.currentGameType.isAdventure()) {
         if (this.currentGameType == WorldSettings.GameType.SPECTATOR) {
            return false;
         }

         if (!this.mc.thePlayer.func_175142_cm()) {
            Block var3 = this.mc.theWorld.getBlockState(var1).getBlock();
            ItemStack var4 = this.mc.thePlayer.getCurrentEquippedItem();
            if (var4 == null) {
               return false;
            }

            if (!var4.canDestroy(var3)) {
               return false;
            }
         }
      }

      if (this.currentGameType.isCreative() && this.mc.thePlayer.getHeldItem() != null && this.mc.thePlayer.getHeldItem().getItem() instanceof ItemSword) {
         return false;
      } else {
         WorldClient var8 = this.mc.theWorld;
         IBlockState var9 = var8.getBlockState(var1);
         Block var5 = var9.getBlock();
         if (var5.getMaterial() == Material.air) {
            return false;
         } else {
            var8.playAuxSFX(2001, var1, Block.getStateId(var9));
            boolean var6 = var8.setBlockToAir(var1);
            if (var6) {
               var5.onBlockDestroyedByPlayer(var8, var1, var9);
            }

            this.field_178895_c = new BlockPos(this.field_178895_c.getX(), -1, this.field_178895_c.getZ());
            if (!this.currentGameType.isCreative()) {
               ItemStack var7 = this.mc.thePlayer.getCurrentEquippedItem();
               if (var7 != null) {
                  var7.onBlockDestroyed(var8, var5, var1, this.mc.thePlayer);
                  if (var7.stackSize == 0) {
                     this.mc.thePlayer.destroyCurrentEquippedItem();
                  }
               }
            }

            return var6;
         }
      }
   }

   public void resetBlockRemoving() {
      if (this.isHittingBlock) {
         this.netClientHandler.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, this.field_178895_c, EnumFacing.DOWN));
         this.isHittingBlock = false;
         this.curBlockDamageMP = 0.0F;
         this.mc.theWorld.sendBlockBreakProgress(this.mc.thePlayer.getEntityId(), this.field_178895_c, -1);
      }

   }

   public void updateController() {
      this.syncCurrentPlayItem();
      if (this.netClientHandler.getNetworkManager().isChannelOpen()) {
         this.netClientHandler.getNetworkManager().processReceivedPackets();
      } else {
         this.netClientHandler.getNetworkManager().checkDisconnected();
      }

   }

   public void setPlayerCapabilities(EntityPlayer var1) {
      this.currentGameType.configurePlayerCapabilities(var1.capabilities);
   }

   public boolean isRidingHorse() {
      return this.mc.thePlayer.isRiding() && this.mc.thePlayer.ridingEntity instanceof EntityHorse;
   }

   public ItemStack windowClick(int var1, int var2, int var3, int var4, EntityPlayer var5) {
      short var6 = var5.openContainer.getNextTransactionID(var5.inventory);
      ItemStack var7 = var5.openContainer.slotClick(var2, var3, var4, var5);
      this.netClientHandler.addToSendQueue(new C0EPacketClickWindow(var1, var2, var3, var4, var7, var6));
      return var7;
   }

   public void flipPlayer(EntityPlayer var1) {
      var1.rotationYaw = -180.0F;
   }

   public boolean extendedReach() {
      return this.currentGameType.isCreative();
   }

   public void sendSlotPacket(ItemStack var1, int var2) {
      if (this.currentGameType.isCreative()) {
         this.netClientHandler.addToSendQueue(new C10PacketCreativeInventoryAction(var2, var1));
      }

   }

   public void sendPacketDropItem(ItemStack var1) {
      if (this.currentGameType.isCreative() && var1 != null) {
         this.netClientHandler.addToSendQueue(new C10PacketCreativeInventoryAction(-1, var1));
      }

   }

   public static void func_178891_a(Minecraft var0, PlayerControllerMP var1, BlockPos var2, EnumFacing var3) {
      if (!var0.theWorld.func_175719_a(var0.thePlayer, var2, var3)) {
         var1.func_178888_a(var2, var3);
      }

   }

   public boolean interactWithEntitySendPacket(EntityPlayer var1, Entity var2) {
      this.syncCurrentPlayItem();
      this.netClientHandler.addToSendQueue(new C02PacketUseEntity(var2, C02PacketUseEntity.Action.INTERACT));
      return this.currentGameType != WorldSettings.GameType.SPECTATOR && var1.interactWith(var2);
   }

   public boolean func_178890_a(EntityPlayerSP var1, WorldClient var2, ItemStack var3, BlockPos var4, EnumFacing var5, Vec3 var6) {
      this.syncCurrentPlayItem();
      float var7 = (float)(var6.xCoord - (double)var4.getX());
      float var8 = (float)(var6.yCoord - (double)var4.getY());
      float var9 = (float)(var6.zCoord - (double)var4.getZ());
      boolean var10 = false;
      if (!this.mc.theWorld.getWorldBorder().contains(var4)) {
         return false;
      } else {
         if (this.currentGameType != WorldSettings.GameType.SPECTATOR) {
            IBlockState var11 = var2.getBlockState(var4);
            if ((!var1.isSneaking() || var1.getHeldItem() == null) && var11.getBlock().onBlockActivated(var2, var4, var11, var1, var5, var7, var8, var9)) {
               var10 = true;
            }

            if (!var10 && var3 != null && var3.getItem() instanceof ItemBlock) {
               ItemBlock var12 = (ItemBlock)var3.getItem();
               if (!var12.canPlaceBlockOnSide(var2, var4, var5, var1, var3)) {
                  return false;
               }
            }
         }

         this.netClientHandler.addToSendQueue(new C08PacketPlayerBlockPlacement(var4, var5.getIndex(), var1.inventory.getCurrentItem(), var7, var8, var9));
         if (!var10 && this.currentGameType != WorldSettings.GameType.SPECTATOR) {
            if (var3 == null) {
               return false;
            } else if (this.currentGameType.isCreative()) {
               int var14 = var3.getMetadata();
               int var15 = var3.stackSize;
               boolean var13 = var3.onItemUse(var1, var2, var4, var5, var7, var8, var9);
               var3.setItemDamage(var14);
               var3.stackSize = var15;
               return var13;
            } else {
               return var3.onItemUse(var1, var2, var4, var5, var7, var8, var9);
            }
         } else {
            return true;
         }
      }
   }

   public void attackEntity(EntityPlayer var1, Entity var2) {
      this.syncCurrentPlayItem();
      this.netClientHandler.addToSendQueue(new C02PacketUseEntity(var2, C02PacketUseEntity.Action.ATTACK));
      if (this.currentGameType != WorldSettings.GameType.SPECTATOR) {
         var1.attackTargetEntityWithCurrentItem(var2);
      }

   }

   public boolean func_180511_b(BlockPos var1, EnumFacing var2) {
      Block var3;
      if (this.currentGameType.isAdventure()) {
         if (this.currentGameType == WorldSettings.GameType.SPECTATOR) {
            return false;
         }

         if (!this.mc.thePlayer.func_175142_cm()) {
            var3 = this.mc.theWorld.getBlockState(var1).getBlock();
            ItemStack var4 = this.mc.thePlayer.getCurrentEquippedItem();
            if (var4 == null) {
               return false;
            }

            if (!var4.canDestroy(var3)) {
               return false;
            }
         }
      }

      if (!this.mc.theWorld.getWorldBorder().contains(var1)) {
         return false;
      } else {
         if (this.currentGameType.isCreative()) {
            this.netClientHandler.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, var1, var2));
            func_178891_a(this.mc, this, var1, var2);
            this.blockHitDelay = 5;
         } else if (!this.isHittingBlock || !this.func_178893_a(var1)) {
            if (this.isHittingBlock) {
               this.netClientHandler.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, this.field_178895_c, var2));
            }

            this.netClientHandler.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, var1, var2));
            var3 = this.mc.theWorld.getBlockState(var1).getBlock();
            boolean var5 = var3.getMaterial() != Material.air;
            if (var5 && this.curBlockDamageMP == 0.0F) {
               var3.onBlockClicked(this.mc.theWorld, var1, this.mc.thePlayer);
            }

            if (var5 && var3.getPlayerRelativeBlockHardness(this.mc.thePlayer, this.mc.thePlayer.worldObj, var1) >= 1.0F) {
               this.func_178888_a(var1, var2);
            } else {
               this.isHittingBlock = true;
               this.field_178895_c = var1;
               this.currentItemHittingBlock = this.mc.thePlayer.getHeldItem();
               this.curBlockDamageMP = 0.0F;
               this.stepSoundTickCounter = 0.0F;
               this.mc.theWorld.sendBlockBreakProgress(this.mc.thePlayer.getEntityId(), this.field_178895_c, (int)(this.curBlockDamageMP * 10.0F) - 1);
            }
         }

         return true;
      }
   }

   public boolean func_178894_a(EntityPlayer var1, Entity var2, MovingObjectPosition var3) {
      this.syncCurrentPlayItem();
      Vec3 var4 = new Vec3(var3.hitVec.xCoord - var2.posX, var3.hitVec.yCoord - var2.posY, var3.hitVec.zCoord - var2.posZ);
      this.netClientHandler.addToSendQueue(new C02PacketUseEntity(var2, var4));
      return this.currentGameType != WorldSettings.GameType.SPECTATOR && var2.func_174825_a(var1, var4);
   }

   public boolean isNotCreative() {
      return !this.currentGameType.isCreative();
   }

   public void setGameType(WorldSettings.GameType var1) {
      this.currentGameType = var1;
      this.currentGameType.configurePlayerCapabilities(this.mc.thePlayer.capabilities);
   }
}
