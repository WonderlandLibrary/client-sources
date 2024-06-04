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
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.network.play.server.S38PacketPlayerListItem;
import net.minecraft.network.play.server.S38PacketPlayerListItem.Action;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldSettings.GameType;








public class ItemInWorldManager
{
  public World theWorld;
  public EntityPlayerMP thisPlayerMP;
  private WorldSettings.GameType gameType;
  private boolean isDestroyingBlock;
  private int initialDamage;
  private BlockPos field_180240_f;
  private int curblockDamage;
  private boolean receivedFinishDiggingPacket;
  private BlockPos field_180241_i;
  private int initialBlockDamage;
  private int durabilityRemainingOnBlock;
  private static final String __OBFID = "CL_00001442";
  
  public ItemInWorldManager(World worldIn)
  {
    gameType = WorldSettings.GameType.NOT_SET;
    field_180240_f = BlockPos.ORIGIN;
    field_180241_i = BlockPos.ORIGIN;
    durabilityRemainingOnBlock = -1;
    theWorld = worldIn;
  }
  
  public void setGameType(WorldSettings.GameType p_73076_1_)
  {
    gameType = p_73076_1_;
    p_73076_1_.configurePlayerCapabilities(thisPlayerMP.capabilities);
    thisPlayerMP.sendPlayerAbilities();
    thisPlayerMP.mcServer.getConfigurationManager().sendPacketToAllPlayers(new S38PacketPlayerListItem(S38PacketPlayerListItem.Action.UPDATE_GAME_MODE, new EntityPlayerMP[] { thisPlayerMP }));
  }
  
  public WorldSettings.GameType getGameType()
  {
    return gameType;
  }
  
  public boolean func_180239_c()
  {
    return gameType.isSurvivalOrAdventure();
  }
  



  public boolean isCreative()
  {
    return gameType.isCreative();
  }
  



  public void initializeGameType(WorldSettings.GameType p_73077_1_)
  {
    if (gameType == WorldSettings.GameType.NOT_SET)
    {
      gameType = p_73077_1_;
    }
    
    setGameType(gameType);
  }
  
  public void updateBlockRemoving()
  {
    curblockDamage += 1;
    


    if (receivedFinishDiggingPacket)
    {
      int var1 = curblockDamage - initialBlockDamage;
      Block var2 = theWorld.getBlockState(field_180241_i).getBlock();
      
      if (var2.getMaterial() == Material.air)
      {
        receivedFinishDiggingPacket = false;
      }
      else
      {
        float var3 = var2.getPlayerRelativeBlockHardness(thisPlayerMP, thisPlayerMP.worldObj, field_180241_i) * (var1 + 1);
        int var4 = (int)(var3 * 10.0F);
        
        if (var4 != durabilityRemainingOnBlock)
        {
          theWorld.sendBlockBreakProgress(thisPlayerMP.getEntityId(), field_180241_i, var4);
          durabilityRemainingOnBlock = var4;
        }
        
        if (var3 >= 1.0F)
        {
          receivedFinishDiggingPacket = false;
          func_180237_b(field_180241_i);
        }
      }
    }
    else if (isDestroyingBlock)
    {
      Block var5 = theWorld.getBlockState(field_180240_f).getBlock();
      
      if (var5.getMaterial() == Material.air)
      {
        theWorld.sendBlockBreakProgress(thisPlayerMP.getEntityId(), field_180240_f, -1);
        durabilityRemainingOnBlock = -1;
        isDestroyingBlock = false;
      }
      else
      {
        int var6 = curblockDamage - initialDamage;
        float var3 = var5.getPlayerRelativeBlockHardness(thisPlayerMP, thisPlayerMP.worldObj, field_180241_i) * (var6 + 1);
        int var4 = (int)(var3 * 10.0F);
        
        if (var4 != durabilityRemainingOnBlock)
        {
          theWorld.sendBlockBreakProgress(thisPlayerMP.getEntityId(), field_180240_f, var4);
          durabilityRemainingOnBlock = var4;
        }
      }
    }
  }
  
  public void func_180784_a(BlockPos p_180784_1_, EnumFacing p_180784_2_)
  {
    if (isCreative())
    {
      if (!theWorld.func_175719_a(null, p_180784_1_, p_180784_2_))
      {
        func_180237_b(p_180784_1_);
      }
    }
    else
    {
      Block var3 = theWorld.getBlockState(p_180784_1_).getBlock();
      
      if (gameType.isAdventure())
      {
        if (gameType == WorldSettings.GameType.SPECTATOR)
        {
          return;
        }
        
        if (!thisPlayerMP.func_175142_cm())
        {
          ItemStack var4 = thisPlayerMP.getCurrentEquippedItem();
          
          if (var4 == null)
          {
            return;
          }
          
          if (!var4.canDestroy(var3))
          {
            return;
          }
        }
      }
      
      theWorld.func_175719_a(null, p_180784_1_, p_180784_2_);
      initialDamage = curblockDamage;
      float var6 = 1.0F;
      
      if (var3.getMaterial() != Material.air)
      {
        var3.onBlockClicked(theWorld, p_180784_1_, thisPlayerMP);
        var6 = var3.getPlayerRelativeBlockHardness(thisPlayerMP, thisPlayerMP.worldObj, p_180784_1_);
      }
      
      if ((var3.getMaterial() != Material.air) && (var6 >= 1.0F))
      {
        func_180237_b(p_180784_1_);
      }
      else
      {
        isDestroyingBlock = true;
        field_180240_f = p_180784_1_;
        int var5 = (int)(var6 * 10.0F);
        theWorld.sendBlockBreakProgress(thisPlayerMP.getEntityId(), p_180784_1_, var5);
        durabilityRemainingOnBlock = var5;
      }
    }
  }
  
  public void func_180785_a(BlockPos p_180785_1_)
  {
    if (p_180785_1_.equals(field_180240_f))
    {
      int var2 = curblockDamage - initialDamage;
      Block var3 = theWorld.getBlockState(p_180785_1_).getBlock();
      
      if (var3.getMaterial() != Material.air)
      {
        float var4 = var3.getPlayerRelativeBlockHardness(thisPlayerMP, thisPlayerMP.worldObj, p_180785_1_) * (var2 + 1);
        
        if (var4 >= 0.7F)
        {
          isDestroyingBlock = false;
          theWorld.sendBlockBreakProgress(thisPlayerMP.getEntityId(), p_180785_1_, -1);
          func_180237_b(p_180785_1_);
        }
        else if (!receivedFinishDiggingPacket)
        {
          isDestroyingBlock = false;
          receivedFinishDiggingPacket = true;
          field_180241_i = p_180785_1_;
          initialBlockDamage = initialDamage;
        }
      }
    }
  }
  
  public void func_180238_e()
  {
    isDestroyingBlock = false;
    theWorld.sendBlockBreakProgress(thisPlayerMP.getEntityId(), field_180240_f, -1);
  }
  
  private boolean func_180235_c(BlockPos p_180235_1_)
  {
    IBlockState var2 = theWorld.getBlockState(p_180235_1_);
    var2.getBlock().onBlockHarvested(theWorld, p_180235_1_, var2, thisPlayerMP);
    boolean var3 = theWorld.setBlockToAir(p_180235_1_);
    
    if (var3)
    {
      var2.getBlock().onBlockDestroyedByPlayer(theWorld, p_180235_1_, var2);
    }
    
    return var3;
  }
  
  public boolean func_180237_b(BlockPos p_180237_1_)
  {
    if ((gameType.isCreative()) && (thisPlayerMP.getHeldItem() != null) && ((thisPlayerMP.getHeldItem().getItem() instanceof ItemSword)))
    {
      return false;
    }
    

    IBlockState var2 = theWorld.getBlockState(p_180237_1_);
    TileEntity var3 = theWorld.getTileEntity(p_180237_1_);
    
    if (gameType.isAdventure())
    {
      if (gameType == WorldSettings.GameType.SPECTATOR)
      {
        return false;
      }
      
      if (!thisPlayerMP.func_175142_cm())
      {
        ItemStack var4 = thisPlayerMP.getCurrentEquippedItem();
        
        if (var4 == null)
        {
          return false;
        }
        
        if (!var4.canDestroy(var2.getBlock()))
        {
          return false;
        }
      }
    }
    
    theWorld.playAuxSFXAtEntity(thisPlayerMP, 2001, p_180237_1_, Block.getStateId(var2));
    boolean var7 = func_180235_c(p_180237_1_);
    
    if (isCreative())
    {
      thisPlayerMP.playerNetServerHandler.sendPacket(new S23PacketBlockChange(theWorld, p_180237_1_));
    }
    else
    {
      ItemStack var5 = thisPlayerMP.getCurrentEquippedItem();
      boolean var6 = thisPlayerMP.canHarvestBlock(var2.getBlock());
      
      if (var5 != null)
      {
        var5.onBlockDestroyed(theWorld, var2.getBlock(), p_180237_1_, thisPlayerMP);
        
        if (stackSize == 0)
        {
          thisPlayerMP.destroyCurrentEquippedItem();
        }
      }
      
      if ((var7) && (var6))
      {
        var2.getBlock().harvestBlock(theWorld, thisPlayerMP, p_180237_1_, var2, var3);
      }
    }
    
    return var7;
  }
  




  public boolean tryUseItem(EntityPlayer p_73085_1_, World worldIn, ItemStack p_73085_3_)
  {
    if (gameType == WorldSettings.GameType.SPECTATOR)
    {
      return false;
    }
    

    int var4 = stackSize;
    int var5 = p_73085_3_.getMetadata();
    ItemStack var6 = p_73085_3_.useItemRightClick(worldIn, p_73085_1_);
    
    if ((var6 == p_73085_3_) && ((var6 == null) || ((stackSize == var4) && (var6.getMaxItemUseDuration() <= 0) && (var6.getMetadata() == var5))))
    {
      return false;
    }
    

    inventory.mainInventory[inventory.currentItem] = var6;
    
    if (isCreative())
    {
      stackSize = var4;
      
      if (var6.isItemStackDamageable())
      {
        var6.setItemDamage(var5);
      }
    }
    
    if (stackSize == 0)
    {
      inventory.mainInventory[inventory.currentItem] = null;
    }
    
    if (!p_73085_1_.isUsingItem())
    {
      ((EntityPlayerMP)p_73085_1_).sendContainerToPlayer(inventoryContainer);
    }
    
    return true;
  }
  


  public boolean func_180236_a(EntityPlayer p_180236_1_, World worldIn, ItemStack p_180236_3_, BlockPos p_180236_4_, EnumFacing p_180236_5_, float p_180236_6_, float p_180236_7_, float p_180236_8_)
  {
    if (gameType == WorldSettings.GameType.SPECTATOR)
    {
      TileEntity var13 = worldIn.getTileEntity(p_180236_4_);
      
      if ((var13 instanceof ILockableContainer))
      {
        Block var14 = worldIn.getBlockState(p_180236_4_).getBlock();
        ILockableContainer var15 = (ILockableContainer)var13;
        
        if (((var15 instanceof TileEntityChest)) && ((var14 instanceof BlockChest)))
        {
          var15 = ((BlockChest)var14).getLockableContainer(worldIn, p_180236_4_);
        }
        
        if (var15 != null)
        {
          p_180236_1_.displayGUIChest(var15);
          return true;
        }
      }
      else if ((var13 instanceof IInventory))
      {
        p_180236_1_.displayGUIChest((IInventory)var13);
        return true;
      }
      
      return false;
    }
    

    if ((!p_180236_1_.isSneaking()) || (p_180236_1_.getHeldItem() == null))
    {
      IBlockState var9 = worldIn.getBlockState(p_180236_4_);
      
      if (var9.getBlock().onBlockActivated(worldIn, p_180236_4_, var9, p_180236_1_, p_180236_5_, p_180236_6_, p_180236_7_, p_180236_8_))
      {
        return true;
      }
    }
    
    if (p_180236_3_ == null)
    {
      return false;
    }
    if (isCreative())
    {
      int var12 = p_180236_3_.getMetadata();
      int var10 = stackSize;
      boolean var11 = p_180236_3_.onItemUse(p_180236_1_, worldIn, p_180236_4_, p_180236_5_, p_180236_6_, p_180236_7_, p_180236_8_);
      p_180236_3_.setItemDamage(var12);
      stackSize = var10;
      return var11;
    }
    

    return p_180236_3_.onItemUse(p_180236_1_, worldIn, p_180236_4_, p_180236_5_, p_180236_6_, p_180236_7_, p_180236_8_);
  }
  





  public void setWorld(WorldServer p_73080_1_)
  {
    theWorld = p_73080_1_;
  }
}
