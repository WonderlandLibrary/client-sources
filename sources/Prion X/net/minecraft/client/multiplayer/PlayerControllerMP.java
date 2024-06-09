package net.minecraft.client.multiplayer;

import me.hexxed.mercury.modulebase.Module;
import net.minecraft.block.Block;
import net.minecraft.block.Block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C02PacketUseEntity.Action;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C07PacketPlayerDigging.Action;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C10PacketCreativeInventoryAction;
import net.minecraft.stats.StatFileWriter;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings.GameType;
import net.minecraft.world.border.WorldBorder;

public class PlayerControllerMP
{
  private final Minecraft mc;
  private final NetHandlerPlayClient netClientHandler;
  private BlockPos field_178895_c = new BlockPos(-1, -1, -1);
  

  private ItemStack currentItemHittingBlock;
  

  public float curBlockDamageMP;
  

  private float stepSoundTickCounter;
  

  public int blockHitDelay;
  

  private boolean isHittingBlock;
  

  private WorldSettings.GameType currentGameType;
  

  private int currentPlayerItem;
  

  private static final String __OBFID = "CL_00000881";
  


  public PlayerControllerMP(Minecraft mcIn, NetHandlerPlayClient p_i45062_2_)
  {
    currentGameType = WorldSettings.GameType.SURVIVAL;
    mc = mcIn;
    netClientHandler = p_i45062_2_;
  }
  
  public static void func_178891_a(Minecraft mcIn, PlayerControllerMP p_178891_1_, BlockPos p_178891_2_, EnumFacing p_178891_3_)
  {
    if (!theWorld.func_175719_a(thePlayer, p_178891_2_, p_178891_3_))
    {
      p_178891_1_.func_178888_a(p_178891_2_, p_178891_3_);
    }
  }
  



  public void setPlayerCapabilities(EntityPlayer p_78748_1_)
  {
    currentGameType.configurePlayerCapabilities(capabilities);
  }
  






  public boolean enableEverythingIsScrewedUpMode()
  {
    return currentGameType == WorldSettings.GameType.SPECTATOR;
  }
  



  public void setGameType(WorldSettings.GameType p_78746_1_)
  {
    currentGameType = p_78746_1_;
    currentGameType.configurePlayerCapabilities(mc.thePlayer.capabilities);
  }
  



  public void flipPlayer(EntityPlayer playerIn)
  {
    rotationYaw = -180.0F;
  }
  
  public boolean shouldDrawHUD()
  {
    return currentGameType.isSurvivalOrAdventure();
  }
  
  public boolean func_178888_a(BlockPos p_178888_1_, EnumFacing p_178888_2_)
  {
    if (currentGameType.isAdventure())
    {
      if (currentGameType == WorldSettings.GameType.SPECTATOR)
      {
        return false;
      }
      
      if (!mc.thePlayer.func_175142_cm())
      {
        Block var3 = mc.theWorld.getBlockState(p_178888_1_).getBlock();
        ItemStack var4 = mc.thePlayer.getCurrentEquippedItem();
        
        if (var4 == null)
        {
          return false;
        }
        
        if (!var4.canDestroy(var3))
        {
          return false;
        }
      }
    }
    
    if ((currentGameType.isCreative()) && (mc.thePlayer.getHeldItem() != null) && ((mc.thePlayer.getHeldItem().getItem() instanceof net.minecraft.item.ItemSword)))
    {
      return false;
    }
    

    WorldClient var8 = mc.theWorld;
    IBlockState var9 = var8.getBlockState(p_178888_1_);
    Block var5 = var9.getBlock();
    
    if (var5.getMaterial() == Material.air)
    {
      return false;
    }
    

    var8.playAuxSFX(2001, p_178888_1_, Block.getStateId(var9));
    boolean var6 = var8.setBlockToAir(p_178888_1_);
    
    if (var6)
    {
      var5.onBlockDestroyedByPlayer(var8, p_178888_1_, var9);
    }
    
    field_178895_c = new BlockPos(field_178895_c.getX(), -1, field_178895_c.getZ());
    
    if (!currentGameType.isCreative())
    {
      ItemStack var7 = mc.thePlayer.getCurrentEquippedItem();
      
      if (var7 != null)
      {
        var7.onBlockDestroyed(var8, var5, p_178888_1_, mc.thePlayer);
        
        if (stackSize == 0)
        {
          mc.thePlayer.destroyCurrentEquippedItem();
        }
      }
    }
    
    return var6;
  }
  




  public boolean func_180511_b(BlockPos p_180511_1_, EnumFacing p_180511_2_)
  {
    for (Module m : me.hexxed.mercury.modulebase.ModuleManager.moduleList) {
      if (m.isEnabled()) {
        m.onClickBlock(p_180511_1_, p_180511_2_);
        if (blockbreakcancelled) {
          blockbreakcancelled = false;
          return false;
        }
      }
    }
    
    if (currentGameType.isAdventure())
    {
      if (currentGameType == WorldSettings.GameType.SPECTATOR)
      {
        return false;
      }
      
      if (!mc.thePlayer.func_175142_cm())
      {
        Block var3 = mc.theWorld.getBlockState(p_180511_1_).getBlock();
        ItemStack var4 = mc.thePlayer.getCurrentEquippedItem();
        
        if (var4 == null)
        {
          return false;
        }
        
        if (!var4.canDestroy(var3))
        {
          return false;
        }
      }
    }
    
    if (!mc.theWorld.getWorldBorder().contains(p_180511_1_))
    {
      return false;
    }
    

    if (currentGameType.isCreative())
    {
      netClientHandler.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, p_180511_1_, p_180511_2_));
      func_178891_a(mc, this, p_180511_1_, p_180511_2_);
      blockHitDelay = 5;
    }
    else if ((!isHittingBlock) || (!func_178893_a(p_180511_1_)))
    {
      if (isHittingBlock)
      {
        netClientHandler.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, field_178895_c, p_180511_2_));
      }
      
      netClientHandler.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, p_180511_1_, p_180511_2_));
      Block var3 = mc.theWorld.getBlockState(p_180511_1_).getBlock();
      boolean var5 = var3.getMaterial() != Material.air;
      
      if ((var5) && (curBlockDamageMP == 0.0F))
      {
        var3.onBlockClicked(mc.theWorld, p_180511_1_, mc.thePlayer);
      }
      
      if ((var5) && (var3.getPlayerRelativeBlockHardness(mc.thePlayer, mc.thePlayer.worldObj, p_180511_1_) >= 1.0F))
      {
        func_178888_a(p_180511_1_, p_180511_2_);
      }
      else
      {
        isHittingBlock = true;
        field_178895_c = p_180511_1_;
        currentItemHittingBlock = mc.thePlayer.getHeldItem();
        curBlockDamageMP = 0.0F;
        stepSoundTickCounter = 0.0F;
        mc.theWorld.sendBlockBreakProgress(mc.thePlayer.getEntityId(), field_178895_c, (int)(curBlockDamageMP * 10.0F) - 1);
      }
    }
    
    return true;
  }
  




  public void resetBlockRemoving()
  {
    if (isHittingBlock)
    {
      netClientHandler.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, field_178895_c, EnumFacing.DOWN));
      isHittingBlock = false;
      curBlockDamageMP = 0.0F;
      mc.theWorld.sendBlockBreakProgress(mc.thePlayer.getEntityId(), field_178895_c, -1);
    }
  }
  
  public boolean func_180512_c(BlockPos p_180512_1_, EnumFacing p_180512_2_)
  {
    syncCurrentPlayItem();
    
    if (blockHitDelay > 0)
    {
      blockHitDelay -= 1;
      return true;
    }
    if ((currentGameType.isCreative()) && (mc.theWorld.getWorldBorder().contains(p_180512_1_)))
    {
      blockHitDelay = 5;
      netClientHandler.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, p_180512_1_, p_180512_2_));
      func_178891_a(mc, this, p_180512_1_, p_180512_2_);
      return true;
    }
    if (func_178893_a(p_180512_1_))
    {
      Block var3 = mc.theWorld.getBlockState(p_180512_1_).getBlock();
      
      if (var3.getMaterial() == Material.air)
      {
        isHittingBlock = false;
        return false;
      }
      

      curBlockDamageMP += var3.getPlayerRelativeBlockHardness(mc.thePlayer, mc.thePlayer.worldObj, p_180512_1_);
      
      if (stepSoundTickCounter % 4.0F == 0.0F)
      {
        mc.getSoundHandler().playSound(new net.minecraft.client.audio.PositionedSoundRecord(new net.minecraft.util.ResourceLocation(stepSound.getStepSound()), (stepSound.getVolume() + 1.0F) / 8.0F, stepSound.getFrequency() * 0.5F, p_180512_1_.getX() + 0.5F, p_180512_1_.getY() + 0.5F, p_180512_1_.getZ() + 0.5F));
      }
      
      stepSoundTickCounter += 1.0F;
      
      if (curBlockDamageMP >= 1.0F)
      {
        isHittingBlock = false;
        netClientHandler.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, p_180512_1_, p_180512_2_));
        func_178888_a(p_180512_1_, p_180512_2_);
        curBlockDamageMP = 0.0F;
        stepSoundTickCounter = 0.0F;
        blockHitDelay = 5;
      }
      
      mc.theWorld.sendBlockBreakProgress(mc.thePlayer.getEntityId(), field_178895_c, (int)(curBlockDamageMP * 10.0F) - 1);
      return true;
    }
    


    return func_180511_b(p_180512_1_, p_180512_2_);
  }
  




  public float getBlockReachDistance()
  {
    return currentGameType.isCreative() ? 5.0F : 4.5F;
  }
  
  public void updateController()
  {
    syncCurrentPlayItem();
    
    if (netClientHandler.getNetworkManager().isChannelOpen())
    {
      netClientHandler.getNetworkManager().processReceivedPackets();
    }
    else
    {
      netClientHandler.getNetworkManager().checkDisconnected();
    }
  }
  
  private boolean func_178893_a(BlockPos p_178893_1_)
  {
    ItemStack var2 = mc.thePlayer.getHeldItem();
    boolean var3 = (currentItemHittingBlock == null) && (var2 == null);
    
    if ((currentItemHittingBlock != null) && (var2 != null))
    {
      var3 = (var2.getItem() == currentItemHittingBlock.getItem()) && (ItemStack.areItemStackTagsEqual(var2, currentItemHittingBlock)) && ((var2.isItemStackDamageable()) || (var2.getMetadata() == currentItemHittingBlock.getMetadata()));
    }
    
    return (p_178893_1_.equals(field_178895_c)) && (var3);
  }
  



  private void syncCurrentPlayItem()
  {
    int var1 = mc.thePlayer.inventory.currentItem;
    
    if (var1 != currentPlayerItem)
    {
      currentPlayerItem = var1;
      netClientHandler.addToSendQueue(new C09PacketHeldItemChange(currentPlayerItem));
    }
  }
  
  public boolean func_178890_a(EntityPlayerSP p_178890_1_, WorldClient p_178890_2_, ItemStack p_178890_3_, BlockPos p_178890_4_, EnumFacing p_178890_5_, Vec3 p_178890_6_)
  {
    syncCurrentPlayItem();
    float var7 = (float)(xCoord - p_178890_4_.getX());
    float var8 = (float)(yCoord - p_178890_4_.getY());
    float var9 = (float)(zCoord - p_178890_4_.getZ());
    boolean var10 = false;
    
    if (!mc.theWorld.getWorldBorder().contains(p_178890_4_))
    {
      return false;
    }
    

    if (currentGameType != WorldSettings.GameType.SPECTATOR)
    {
      IBlockState var11 = p_178890_2_.getBlockState(p_178890_4_);
      
      if (((!p_178890_1_.isSneaking()) || (p_178890_1_.getHeldItem() == null)) && (var11.getBlock().onBlockActivated(p_178890_2_, p_178890_4_, var11, p_178890_1_, p_178890_5_, var7, var8, var9)))
      {
        var10 = true;
      }
      
      if ((!var10) && (p_178890_3_ != null) && ((p_178890_3_.getItem() instanceof ItemBlock)))
      {
        ItemBlock var12 = (ItemBlock)p_178890_3_.getItem();
        
        if (!var12.canPlaceBlockOnSide(p_178890_2_, p_178890_4_, p_178890_5_, p_178890_1_, p_178890_3_))
        {
          return false;
        }
      }
    }
    
    netClientHandler.addToSendQueue(new C08PacketPlayerBlockPlacement(p_178890_4_, p_178890_5_.getIndex(), inventory.getCurrentItem(), var7, var8, var9));
    
    if ((!var10) && (currentGameType != WorldSettings.GameType.SPECTATOR))
    {
      if (p_178890_3_ == null)
      {
        return false;
      }
      if (currentGameType.isCreative())
      {
        int var14 = p_178890_3_.getMetadata();
        int var15 = stackSize;
        boolean var13 = p_178890_3_.onItemUse(p_178890_1_, p_178890_2_, p_178890_4_, p_178890_5_, var7, var8, var9);
        p_178890_3_.setItemDamage(var14);
        stackSize = var15;
        return var13;
      }
      

      return p_178890_3_.onItemUse(p_178890_1_, p_178890_2_, p_178890_4_, p_178890_5_, var7, var8, var9);
    }
    


    return true;
  }
  





  public boolean sendUseItem(EntityPlayer playerIn, World worldIn, ItemStack itemStackIn)
  {
    if (currentGameType == WorldSettings.GameType.SPECTATOR)
    {
      return false;
    }
    

    syncCurrentPlayItem();
    netClientHandler.addToSendQueue(new C08PacketPlayerBlockPlacement(inventory.getCurrentItem()));
    int var4 = stackSize;
    ItemStack var5 = itemStackIn.useItemRightClick(worldIn, playerIn);
    
    if ((var5 == itemStackIn) && ((var5 == null) || (stackSize == var4)))
    {
      return false;
    }
    

    inventory.mainInventory[inventory.currentItem] = var5;
    
    if (stackSize == 0)
    {
      inventory.mainInventory[inventory.currentItem] = null;
    }
    
    return true;
  }
  


  public EntityPlayerSP func_178892_a(World worldIn, StatFileWriter p_178892_2_)
  {
    return new EntityPlayerSP(mc, worldIn, netClientHandler, p_178892_2_);
  }
  



  public void attackEntity(EntityPlayer playerIn, Entity targetEntity)
  {
    syncCurrentPlayItem();
    netClientHandler.addToSendQueue(new C02PacketUseEntity(targetEntity, C02PacketUseEntity.Action.ATTACK));
    
    if (currentGameType != WorldSettings.GameType.SPECTATOR)
    {
      playerIn.attackTargetEntityWithCurrentItem(targetEntity);
    }
  }
  



  public boolean interactWithEntitySendPacket(EntityPlayer playerIn, Entity targetEntity)
  {
    syncCurrentPlayItem();
    netClientHandler.addToSendQueue(new C02PacketUseEntity(targetEntity, C02PacketUseEntity.Action.INTERACT));
    return (currentGameType != WorldSettings.GameType.SPECTATOR) && (playerIn.interactWith(targetEntity));
  }
  
  public boolean func_178894_a(EntityPlayer p_178894_1_, Entity p_178894_2_, MovingObjectPosition p_178894_3_)
  {
    syncCurrentPlayItem();
    Vec3 var4 = new Vec3(hitVec.xCoord - posX, hitVec.yCoord - posY, hitVec.zCoord - posZ);
    netClientHandler.addToSendQueue(new C02PacketUseEntity(p_178894_2_, var4));
    return (currentGameType != WorldSettings.GameType.SPECTATOR) && (p_178894_2_.func_174825_a(p_178894_1_, var4));
  }
  



  public ItemStack windowClick(int windowId, int slotId, int p_78753_3_, int p_78753_4_, EntityPlayer playerIn)
  {
    short var6 = openContainer.getNextTransactionID(inventory);
    ItemStack var7 = openContainer.slotClick(slotId, p_78753_3_, p_78753_4_, playerIn);
    netClientHandler.addToSendQueue(new net.minecraft.network.play.client.C0EPacketClickWindow(windowId, slotId, p_78753_3_, p_78753_4_, var7, var6));
    return var7;
  }
  




  public void sendEnchantPacket(int p_78756_1_, int p_78756_2_)
  {
    netClientHandler.addToSendQueue(new net.minecraft.network.play.client.C11PacketEnchantItem(p_78756_1_, p_78756_2_));
  }
  



  public void sendSlotPacket(ItemStack itemStackIn, int slotId)
  {
    if (currentGameType.isCreative())
    {
      netClientHandler.addToSendQueue(new C10PacketCreativeInventoryAction(slotId, itemStackIn));
    }
  }
  



  public void sendPacketDropItem(ItemStack itemStackIn)
  {
    if ((currentGameType.isCreative()) && (itemStackIn != null))
    {
      netClientHandler.addToSendQueue(new C10PacketCreativeInventoryAction(-1, itemStackIn));
    }
  }
  
  public void onStoppedUsingItem(EntityPlayer playerIn)
  {
    syncCurrentPlayItem();
    netClientHandler.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
    playerIn.stopUsingItem();
  }
  
  public boolean gameIsSurvivalOrAdventure()
  {
    return currentGameType.isSurvivalOrAdventure();
  }
  



  public boolean isNotCreative()
  {
    return !currentGameType.isCreative();
  }
  



  public boolean isInCreativeMode()
  {
    return currentGameType.isCreative();
  }
  



  public boolean extendedReach()
  {
    return currentGameType.isCreative();
  }
  



  public boolean isRidingHorse()
  {
    return (mc.thePlayer.isRiding()) && ((mc.thePlayer.ridingEntity instanceof EntityHorse));
  }
  
  public boolean isSpectatorMode()
  {
    return currentGameType == WorldSettings.GameType.SPECTATOR;
  }
  
  public WorldSettings.GameType func_178889_l()
  {
    return currentGameType;
  }
}
