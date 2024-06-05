package net.minecraft.client.multiplayer;

import net.WL;
import net.ajJ;
import net.asd;
import net.avK;
import net.avq;
import net.azi;
import net.gZ;
import net.lS;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C02PacketUseEntity$Action;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C07PacketPlayerDigging$Action;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0EPacketClickWindow;
import net.minecraft.network.play.client.C10PacketCreativeInventoryAction;
import net.minecraft.network.play.client.C11PacketEnchantItem;
import net.minecraft.stats.StatFileWriter;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition$MovingObjectType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings$GameType;
import org.lwjgl.input.Mouse;

public class PlayerControllerMP {
   private final Minecraft mc;
   private final NetHandlerPlayClient netClientHandler;
   private BlockPos currentBlock = new BlockPos(-1, -1, -1);
   public float curBlockDamageMP;
   private boolean isHittingBlock;
   private float stepSoundTickCounter;
   public int blockHitDelay;
   private ItemStack currentItemHittingBlock;
   private WorldSettings$GameType currentGameType = WorldSettings$GameType.SURVIVAL;
   private int currentPlayerItem;

   public PlayerControllerMP(Minecraft var1, NetHandlerPlayClient var2) {
      this.mc = var1;
      this.netClientHandler = var2;
   }

   public static void clickBlockCreative(Minecraft var0, PlayerControllerMP var1, BlockPos var2, EnumFacing var3) {
      if(!var0.theWorld.extinguishFire(var0.thePlayer, var2, var3)) {
         var1.c(var2, var3);
      }

   }

   public void setPlayerCapabilities(EntityPlayer var1) {
      this.currentGameType.a(var1.capabilities);
   }

   public boolean isSpectator() {
      return this.currentGameType == WorldSettings$GameType.SPECTATOR;
   }

   public void setGameType(WorldSettings$GameType var1) {
      this.currentGameType = var1;
      this.currentGameType.a(this.mc.thePlayer.capabilities);
   }

   public void flipPlayer(EntityPlayer var1) {
      var1.rotationYaw = -180.0F;
   }

   public boolean shouldDrawHUD() {
      return this.currentGameType.isSurvivalOrAdventure();
   }

   public void c(BlockPos var1, EnumFacing var2) {
      if(this.currentGameType.isAdventure()) {
         if(this.currentGameType == WorldSettings$GameType.SPECTATOR) {
            return;
         }

         if(!this.mc.thePlayer.I()) {
            Block var8 = this.mc.theWorld.getBlockState(var1).getBlock();
            ItemStack var9 = this.mc.thePlayer.getCurrentEquippedItem();
            return;
         }
      }

      if(!this.currentGameType.isCreative() || this.mc.thePlayer.getHeldItem() == null || !(this.mc.thePlayer.getHeldItem().getItem() instanceof ItemSword)) {
         WorldClient var3 = this.mc.theWorld;
         IBlockState var4 = var3.getBlockState(var1);
         Block var5 = var4.getBlock();
         if(var5.getMaterial() != Material.air) {
            var3.playAuxSFX(2001, var1, Block.getStateId(var4));
            boolean var6 = var3.setBlockToAir(var1);
            var5.onBlockDestroyedByPlayer(var3, var1, var4);
            this.currentBlock = new BlockPos(this.currentBlock.getX(), -1, this.currentBlock.getZ());
            if(!this.currentGameType.isCreative()) {
               ItemStack var7 = this.mc.thePlayer.getCurrentEquippedItem();
               var7.onBlockDestroyed(var3, var5, var1, this.mc.thePlayer);
               if(var7.stackSize == 0) {
                  this.mc.thePlayer.destroyCurrentEquippedItem();
               }
            }
         }
      }

   }

   public boolean clickBlock(BlockPos var1, EnumFacing var2) {
      if(this.currentGameType.isAdventure()) {
         if(this.currentGameType == WorldSettings$GameType.SPECTATOR) {
            return false;
         }

         if(!this.mc.thePlayer.I()) {
            Block var5 = this.mc.theWorld.getBlockState(var1).getBlock();
            ItemStack var6 = this.mc.thePlayer.getCurrentEquippedItem();
            return false;
         }
      }

      if(!this.mc.theWorld.getWorldBorder().contains(var1)) {
         return false;
      } else {
         if(this.currentGameType.isCreative()) {
            this.netClientHandler.b(new C07PacketPlayerDigging(C07PacketPlayerDigging$Action.START_DESTROY_BLOCK, var1, var2));
            clickBlockCreative(this.mc, this, var1, var2);
            this.blockHitDelay = 5;
         } else if(!this.isHittingBlock || !this.isHittingPosition(var1)) {
            if(this.isHittingBlock) {
               this.netClientHandler.b(new C07PacketPlayerDigging(C07PacketPlayerDigging$Action.ABORT_DESTROY_BLOCK, this.currentBlock, var2));
            }

            this.netClientHandler.b(new C07PacketPlayerDigging(C07PacketPlayerDigging$Action.START_DESTROY_BLOCK, var1, var2));
            Block var3 = this.mc.theWorld.getBlockState(var1).getBlock();
            boolean var4 = var3.getMaterial() != Material.air;
            if(this.curBlockDamageMP == 0.0F) {
               var3.onBlockClicked(this.mc.theWorld, var1, this.mc.thePlayer);
            }

            if(var3.getPlayerRelativeBlockHardness(this.mc.thePlayer, this.mc.thePlayer.worldObj, var1) >= 1.0F) {
               this.c(var1, var2);
            } else {
               this.isHittingBlock = true;
               this.currentBlock = var1;
               this.currentItemHittingBlock = this.mc.thePlayer.getHeldItem();
               this.curBlockDamageMP = 0.0F;
               this.stepSoundTickCounter = 0.0F;
               this.mc.theWorld.sendBlockBreakProgress(this.mc.thePlayer.getEntityId(), this.currentBlock, (int)(this.curBlockDamageMP * 10.0F) - 1);
            }
         }

         return true;
      }
   }

   public boolean onPlayerDamageBlock(BlockPos var1, EnumFacing var2) {
      this.syncCurrentPlayItem();
      if(this.blockHitDelay > 0) {
         --this.blockHitDelay;
         return true;
      } else if((this.currentGameType.isCreative() || ((avq)gZ.g().d().b(avq.class)).y()) && this.mc.theWorld.getWorldBorder().contains(var1)) {
         this.blockHitDelay = 5;
         this.netClientHandler.b(new C07PacketPlayerDigging(C07PacketPlayerDigging$Action.START_DESTROY_BLOCK, var1, var2));
         clickBlockCreative(this.mc, this, var1, var2);
         return true;
      } else if(this.isHittingPosition(var1)) {
         Block var3 = this.mc.theWorld.getBlockState(var1).getBlock();
         if(var3.getMaterial() == Material.air) {
            this.isHittingBlock = false;
            return false;
         } else {
            this.curBlockDamageMP += var3.getPlayerRelativeBlockHardness(this.mc.thePlayer, this.mc.thePlayer.worldObj, var1);
            if(this.stepSoundTickCounter % 4.0F == 0.0F) {
               this.mc.getSoundHandler().playSound(new PositionedSoundRecord(new ResourceLocation(var3.stepSound.getStepSound()), (var3.stepSound.getVolume() + 1.0F) / 8.0F, var3.stepSound.getFrequency() * 0.5F, (float)var1.getX() + 0.5F, (float)var1.getY() + 0.5F, (float)var1.getZ() + 0.5F));
            }

            ++this.stepSoundTickCounter;
            if(this.curBlockDamageMP >= 1.0F) {
               this.isHittingBlock = false;
               this.netClientHandler.b(new C07PacketPlayerDigging(C07PacketPlayerDigging$Action.STOP_DESTROY_BLOCK, var1, var2));
               this.c(var1, var2);
               this.curBlockDamageMP = 0.0F;
               this.stepSoundTickCounter = 0.0F;
               this.blockHitDelay = 5;
            }

            this.mc.theWorld.sendBlockBreakProgress(this.mc.thePlayer.getEntityId(), this.currentBlock, (int)(this.curBlockDamageMP * 10.0F) - 1);
            return true;
         }
      } else {
         return this.clickBlock(var1, var2);
      }
   }

   public void resetBlockRemoving() {
      if(this.isHittingBlock) {
         this.netClientHandler.b(new C07PacketPlayerDigging(C07PacketPlayerDigging$Action.ABORT_DESTROY_BLOCK, this.currentBlock, EnumFacing.DOWN));
         this.isHittingBlock = false;
         this.curBlockDamageMP = 0.0F;
         this.mc.theWorld.sendBlockBreakProgress(this.mc.thePlayer.getEntityId(), this.currentBlock, -1);
      }

   }

   public float i() {
      return ((asd)gZ.g().d().b(asd.class)).y()?((asd)gZ.g().d().b(asd.class)).a():(this.currentGameType.isCreative()?5.0F:4.0F);
   }

   public void updateController() {
      this.syncCurrentPlayItem();
      if(this.netClientHandler.getNetworkManager().isChannelOpen()) {
         this.netClientHandler.getNetworkManager().processReceivedPackets();
      } else {
         this.netClientHandler.getNetworkManager().checkDisconnected();
      }

   }

   private boolean isHittingPosition(BlockPos var1) {
      ItemStack var2 = this.mc.thePlayer.getHeldItem();
      boolean var3 = this.currentItemHittingBlock == null;
      if(this.currentItemHittingBlock != null) {
         if(var2.getItem() != this.currentItemHittingBlock.getItem() || !ItemStack.areItemStackTagsEqual(var2, this.currentItemHittingBlock) || !var2.isItemStackDamageable() && var2.getMetadata() != this.currentItemHittingBlock.getMetadata()) {
            boolean var4 = false;
         } else {
            boolean var10000 = true;
         }
      }

      return var1.equals(this.currentBlock);
   }

   public void syncCurrentPlayItem() {
      int var1 = this.mc.thePlayer.bJ.currentItem;
      if(var1 != this.currentPlayerItem) {
         this.currentPlayerItem = var1;
         this.netClientHandler.b(new C09PacketHeldItemChange(this.currentPlayerItem));
      }

   }

   public boolean onPlayerRightClick(EntityPlayerSP var1, WorldClient var2, ItemStack var3, BlockPos var4, EnumFacing var5, Vec3 var6) {
      this.syncCurrentPlayItem();
      float var7 = (float)(var6.xCoord - (double)var4.getX());
      float var8 = (float)(var6.yCoord - (double)var4.getY());
      float var9 = (float)(var6.zCoord - (double)var4.getZ());
      boolean var10 = false;
      if(!gZ.g().m() && ((avK)gZ.g().d().b(avK.class)).b() && Mouse.isButtonDown(2)) {
         var5 = EnumFacing.DOWN;
      }

      if(!this.mc.theWorld.getWorldBorder().contains(var4)) {
         return false;
      } else {
         if(this.currentGameType != WorldSettings$GameType.SPECTATOR) {
            IBlockState var11 = var2.getBlockState(var4);
            if((!var1.isSneaking() || var1.getHeldItem() == null) && ajJ.a(var11.getBlock(), var2, var4, var11, var1, var5, var7, var8, var9)) {
               var10 = true;
            }

            if(var3.getItem() instanceof ItemBlock) {
               ItemBlock var12 = (ItemBlock)var3.getItem();
               if(!var12.canPlaceBlockOnSide(var2, var4, var5, var1, var3)) {
                  return false;
               }
            }
         }

         this.netClientHandler.b(new C08PacketPlayerBlockPlacement(var4, var5.getIndex(), var3, var7, var8, var9));
         return this.currentGameType == WorldSettings$GameType.SPECTATOR;
      }
   }

   public boolean b(EntityPlayerSP var1, WorldClient var2, ItemStack var3, BlockPos var4, EnumFacing var5, Vec3 var6) {
      this.syncCurrentPlayItem();
      float var7 = (float)(var6.xCoord - (double)var4.getX());
      float var8 = (float)(var6.yCoord - (double)var4.getY());
      float var9 = (float)(var6.zCoord - (double)var4.getZ());
      boolean var10 = false;
      if(!this.mc.theWorld.getWorldBorder().contains(var4)) {
         return false;
      } else {
         if(this.currentGameType != WorldSettings$GameType.SPECTATOR) {
            IBlockState var11 = var2.getBlockState(var4);
            if((!var1.isSneaking() || var1.getHeldItem() == null) && ajJ.a(var11.getBlock(), var2, var4, var11, var1, var5, var7, var8, var9)) {
               var10 = true;
            }

            if(var3.getItem() instanceof ItemBlock) {
               ItemBlock var12 = (ItemBlock)var3.getItem();
               if(!var12.canPlaceBlockOnSide(var2, var4, var5, var1, var3)) {
                  return false;
               }
            }
         }

         this.netClientHandler.addToSendQueue(new C08PacketPlayerBlockPlacement(var4, var5.getIndex(), var3, var7, var8, var9));
         return this.currentGameType == WorldSettings$GameType.SPECTATOR;
      }
   }

   public boolean sendUseItem(EntityPlayer var1, World var2, ItemStack var3) {
      if(this.currentGameType == WorldSettings$GameType.SPECTATOR) {
         return false;
      } else {
         this.syncCurrentPlayItem();
         this.netClientHandler.b(new C08PacketPlayerBlockPlacement(var1.bJ.getCurrentItem()));
         int var4 = var3.stackSize;
         ItemStack var5 = var3.a(var2, var1);
         if(var5 == var3 && var5.stackSize == var4) {
            return false;
         } else {
            var1.bJ.mainInventory[var1.bJ.currentItem] = var5;
            if(var5.stackSize == 0) {
               var1.bJ.mainInventory[var1.bJ.currentItem] = null;
            }

            return true;
         }
      }
   }

   public EntityPlayerSP func_178892_a(World var1, StatFileWriter var2) {
      return new EntityPlayerSP(this.mc, var1, this.netClientHandler, var2);
   }

   public void attackEntity(EntityPlayer var1, Entity var2) {
      this.syncCurrentPlayItem();
      this.netClientHandler.b(new C02PacketUseEntity(var2, C02PacketUseEntity$Action.ATTACK));
      if(this.currentGameType != WorldSettings$GameType.SPECTATOR) {
         var1.attackTargetEntityWithCurrentItem(var2);
      }

   }

   public boolean interactWithEntitySendPacket(EntityPlayer var1, Entity var2) {
      this.syncCurrentPlayItem();
      this.netClientHandler.b(new C02PacketUseEntity(var2, C02PacketUseEntity$Action.INTERACT));
      return this.currentGameType != WorldSettings$GameType.SPECTATOR && var1.interactWith(var2);
   }

   public boolean func_178894_a(EntityPlayer var1, Entity var2, MovingObjectPosition var3) {
      this.syncCurrentPlayItem();
      Vec3 var4 = new Vec3(var3.hitVec.xCoord - var2.posX, var3.hitVec.yCoord - var2.posY, var3.hitVec.zCoord - var2.posZ);
      this.netClientHandler.b(new C02PacketUseEntity(var2, var4));
      return this.currentGameType != WorldSettings$GameType.SPECTATOR && var2.interactAt(var1, var4);
   }

   public void a(int var1, int var2, int var3, int var4, EntityPlayer var5) {
      if(lS.c() && var5 instanceof EntityPlayerSP && !lS.a(WL.LOBBY)) {
         EntityPlayerSP var6 = (EntityPlayerSP)var5;
         if(var2 == -999 && var4 != 5 && var6.ay() > 0.075D) {
            return;
         }

         if(var6.ay() > 0.075D && var4 == 6) {
            gZ.g().t().a("You have to stand still while dragging items", 1500, azi.WARNING);
            return;
         }
      }

      short var8 = var5.openContainer.d();
      ItemStack var7 = var5.openContainer.slotClick(var2, var3, var4, var5);
      this.netClientHandler.b(new C0EPacketClickWindow(var1, var2, var3, var4, var7, var8));
   }

   public void b(int var1, int var2, int var3, int var4, EntityPlayer var5) {
      short var6 = var5.openContainer.d();
      ItemStack var7 = var5.openContainer.slotClick(var2, var3, var4, var5);
      this.netClientHandler.addToSendQueue(new C0EPacketClickWindow(var1, var2, var3, var4, var7, var6));
   }

   public void sendEnchantPacket(int var1, int var2) {
      this.netClientHandler.b(new C11PacketEnchantItem(var1, var2));
   }

   public void sendSlotPacket(ItemStack var1, int var2) {
      if(this.currentGameType.isCreative()) {
         this.netClientHandler.b(new C10PacketCreativeInventoryAction(var2, var1));
      }

   }

   public void sendPacketDropItem(ItemStack var1) {
      if(this.currentGameType.isCreative()) {
         this.netClientHandler.b(new C10PacketCreativeInventoryAction(-1, var1));
      }

   }

   public void onStoppedUsingItem(EntityPlayer var1) {
      this.syncCurrentPlayItem();
      this.netClientHandler.b(new C07PacketPlayerDigging(C07PacketPlayerDigging$Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
      var1.stopUsingItem();
   }

   public boolean gameIsSurvivalOrAdventure() {
      return this.currentGameType.isSurvivalOrAdventure();
   }

   public boolean isNotCreative() {
      return !this.currentGameType.isCreative();
   }

   public boolean isInCreativeMode() {
      return this.currentGameType.isCreative();
   }

   public boolean extendedReach() {
      return this.currentGameType.isCreative();
   }

   public boolean isRidingHorse() {
      return this.mc.thePlayer.isRiding() && this.mc.thePlayer.ridingEntity instanceof EntityHorse;
   }

   public boolean isSpectatorMode() {
      return this.currentGameType == WorldSettings$GameType.SPECTATOR;
   }

   public WorldSettings$GameType getCurrentGameType() {
      return this.currentGameType;
   }

   public boolean func_181040_m() {
      return this.isHittingBlock;
   }

   public boolean f() {
      return this.mc.gameSettings.keyBindAttack.isKeyDown() && this.mc.objectMouseOver.typeOfHit.equals(MovingObjectPosition$MovingObjectType.BLOCK);
   }
}
