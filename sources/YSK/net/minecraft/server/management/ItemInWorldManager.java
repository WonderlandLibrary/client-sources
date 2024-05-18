package net.minecraft.server.management;

import net.minecraft.block.material.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.*;
import net.minecraft.network.play.server.*;
import net.minecraft.block.state.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.tileentity.*;
import net.minecraft.block.*;
import net.minecraft.inventory.*;
import net.minecraft.world.*;

public class ItemInWorldManager
{
    private int initialDamage;
    public EntityPlayerMP thisPlayerMP;
    private int curblockDamage;
    private boolean receivedFinishDiggingPacket;
    private BlockPos field_180240_f;
    private BlockPos field_180241_i;
    private boolean isDestroyingBlock;
    private WorldSettings.GameType gameType;
    private int initialBlockDamage;
    public World theWorld;
    private int durabilityRemainingOnBlock;
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public ItemInWorldManager(final World theWorld) {
        this.gameType = WorldSettings.GameType.NOT_SET;
        this.field_180240_f = BlockPos.ORIGIN;
        this.field_180241_i = BlockPos.ORIGIN;
        this.durabilityRemainingOnBlock = -" ".length();
        this.theWorld = theWorld;
    }
    
    public void cancelDestroyingBlock() {
        this.isDestroyingBlock = ("".length() != 0);
        this.theWorld.sendBlockBreakProgress(this.thisPlayerMP.getEntityId(), this.field_180240_f, -" ".length());
    }
    
    public void updateBlockRemoving() {
        this.curblockDamage += " ".length();
        if (this.receivedFinishDiggingPacket) {
            final int n = this.curblockDamage - this.initialBlockDamage;
            final Block block = this.theWorld.getBlockState(this.field_180241_i).getBlock();
            if (block.getMaterial() == Material.air) {
                this.receivedFinishDiggingPacket = ("".length() != 0);
                "".length();
                if (2 < -1) {
                    throw null;
                }
            }
            else {
                final float n2 = block.getPlayerRelativeBlockHardness(this.thisPlayerMP, this.thisPlayerMP.worldObj, this.field_180241_i) * (n + " ".length());
                final int durabilityRemainingOnBlock = (int)(n2 * 10.0f);
                if (durabilityRemainingOnBlock != this.durabilityRemainingOnBlock) {
                    this.theWorld.sendBlockBreakProgress(this.thisPlayerMP.getEntityId(), this.field_180241_i, durabilityRemainingOnBlock);
                    this.durabilityRemainingOnBlock = durabilityRemainingOnBlock;
                }
                if (n2 >= 1.0f) {
                    this.receivedFinishDiggingPacket = ("".length() != 0);
                    this.tryHarvestBlock(this.field_180241_i);
                    "".length();
                    if (4 < 4) {
                        throw null;
                    }
                }
            }
        }
        else if (this.isDestroyingBlock) {
            final Block block2 = this.theWorld.getBlockState(this.field_180240_f).getBlock();
            if (block2.getMaterial() == Material.air) {
                this.theWorld.sendBlockBreakProgress(this.thisPlayerMP.getEntityId(), this.field_180240_f, -" ".length());
                this.durabilityRemainingOnBlock = -" ".length();
                this.isDestroyingBlock = ("".length() != 0);
                "".length();
                if (-1 >= 4) {
                    throw null;
                }
            }
            else {
                final int durabilityRemainingOnBlock2 = (int)(block2.getPlayerRelativeBlockHardness(this.thisPlayerMP, this.thisPlayerMP.worldObj, this.field_180241_i) * (this.curblockDamage - this.initialDamage + " ".length()) * 10.0f);
                if (durabilityRemainingOnBlock2 != this.durabilityRemainingOnBlock) {
                    this.theWorld.sendBlockBreakProgress(this.thisPlayerMP.getEntityId(), this.field_180240_f, durabilityRemainingOnBlock2);
                    this.durabilityRemainingOnBlock = durabilityRemainingOnBlock2;
                }
            }
        }
    }
    
    public boolean survivalOrAdventure() {
        return this.gameType.isSurvivalOrAdventure();
    }
    
    public void setGameType(final WorldSettings.GameType gameType) {
        (this.gameType = gameType).configurePlayerCapabilities(this.thisPlayerMP.capabilities);
        this.thisPlayerMP.sendPlayerAbilities();
        final ServerConfigurationManager configurationManager = this.thisPlayerMP.mcServer.getConfigurationManager();
        final S38PacketPlayerListItem.Action update_GAME_MODE = S38PacketPlayerListItem.Action.UPDATE_GAME_MODE;
        final EntityPlayerMP[] array = new EntityPlayerMP[" ".length()];
        array["".length()] = this.thisPlayerMP;
        configurationManager.sendPacketToAllPlayers(new S38PacketPlayerListItem(update_GAME_MODE, array));
    }
    
    public boolean tryHarvestBlock(final BlockPos blockPos) {
        if (this.gameType.isCreative() && this.thisPlayerMP.getHeldItem() != null && this.thisPlayerMP.getHeldItem().getItem() instanceof ItemSword) {
            return "".length() != 0;
        }
        final IBlockState blockState = this.theWorld.getBlockState(blockPos);
        final TileEntity tileEntity = this.theWorld.getTileEntity(blockPos);
        if (this.gameType.isAdventure()) {
            if (this.gameType == WorldSettings.GameType.SPECTATOR) {
                return "".length() != 0;
            }
            if (!this.thisPlayerMP.isAllowEdit()) {
                final ItemStack currentEquippedItem = this.thisPlayerMP.getCurrentEquippedItem();
                if (currentEquippedItem == null) {
                    return "".length() != 0;
                }
                if (!currentEquippedItem.canDestroy(blockState.getBlock())) {
                    return "".length() != 0;
                }
            }
        }
        this.theWorld.playAuxSFXAtEntity(this.thisPlayerMP, 1495 + 116 + 99 + 291, blockPos, Block.getStateId(blockState));
        final boolean removeBlock = this.removeBlock(blockPos);
        if (this.isCreative()) {
            this.thisPlayerMP.playerNetServerHandler.sendPacket(new S23PacketBlockChange(this.theWorld, blockPos));
            "".length();
            if (1 >= 3) {
                throw null;
            }
        }
        else {
            final ItemStack currentEquippedItem2 = this.thisPlayerMP.getCurrentEquippedItem();
            final boolean canHarvestBlock = this.thisPlayerMP.canHarvestBlock(blockState.getBlock());
            if (currentEquippedItem2 != null) {
                currentEquippedItem2.onBlockDestroyed(this.theWorld, blockState.getBlock(), blockPos, this.thisPlayerMP);
                if (currentEquippedItem2.stackSize == 0) {
                    this.thisPlayerMP.destroyCurrentEquippedItem();
                }
            }
            if (removeBlock && canHarvestBlock) {
                blockState.getBlock().harvestBlock(this.theWorld, this.thisPlayerMP, blockPos, blockState, tileEntity);
            }
        }
        return removeBlock;
    }
    
    public WorldSettings.GameType getGameType() {
        return this.gameType;
    }
    
    public void blockRemoving(final BlockPos field_180241_i) {
        if (field_180241_i.equals(this.field_180240_f)) {
            final int n = this.curblockDamage - this.initialDamage;
            final Block block = this.theWorld.getBlockState(field_180241_i).getBlock();
            if (block.getMaterial() != Material.air) {
                if (block.getPlayerRelativeBlockHardness(this.thisPlayerMP, this.thisPlayerMP.worldObj, field_180241_i) * (n + " ".length()) >= 0.7f) {
                    this.isDestroyingBlock = ("".length() != 0);
                    this.theWorld.sendBlockBreakProgress(this.thisPlayerMP.getEntityId(), field_180241_i, -" ".length());
                    this.tryHarvestBlock(field_180241_i);
                    "".length();
                    if (-1 != -1) {
                        throw null;
                    }
                }
                else if (!this.receivedFinishDiggingPacket) {
                    this.isDestroyingBlock = ("".length() != 0);
                    this.receivedFinishDiggingPacket = (" ".length() != 0);
                    this.field_180241_i = field_180241_i;
                    this.initialBlockDamage = this.initialDamage;
                }
            }
        }
    }
    
    public void initializeGameType(final WorldSettings.GameType gameType) {
        if (this.gameType == WorldSettings.GameType.NOT_SET) {
            this.gameType = gameType;
        }
        this.setGameType(this.gameType);
    }
    
    public void onBlockClicked(final BlockPos field_180240_f, final EnumFacing enumFacing) {
        if (this.isCreative()) {
            if (!this.theWorld.extinguishFire(null, field_180240_f, enumFacing)) {
                this.tryHarvestBlock(field_180240_f);
                "".length();
                if (2 == 3) {
                    throw null;
                }
            }
        }
        else {
            final Block block = this.theWorld.getBlockState(field_180240_f).getBlock();
            if (this.gameType.isAdventure()) {
                if (this.gameType == WorldSettings.GameType.SPECTATOR) {
                    return;
                }
                if (!this.thisPlayerMP.isAllowEdit()) {
                    final ItemStack currentEquippedItem = this.thisPlayerMP.getCurrentEquippedItem();
                    if (currentEquippedItem == null) {
                        return;
                    }
                    if (!currentEquippedItem.canDestroy(block)) {
                        return;
                    }
                }
            }
            this.theWorld.extinguishFire(null, field_180240_f, enumFacing);
            this.initialDamage = this.curblockDamage;
            float playerRelativeBlockHardness = 1.0f;
            if (block.getMaterial() != Material.air) {
                block.onBlockClicked(this.theWorld, field_180240_f, this.thisPlayerMP);
                playerRelativeBlockHardness = block.getPlayerRelativeBlockHardness(this.thisPlayerMP, this.thisPlayerMP.worldObj, field_180240_f);
            }
            if (block.getMaterial() != Material.air && playerRelativeBlockHardness >= 1.0f) {
                this.tryHarvestBlock(field_180240_f);
                "".length();
                if (3 == 0) {
                    throw null;
                }
            }
            else {
                this.isDestroyingBlock = (" ".length() != 0);
                this.field_180240_f = field_180240_f;
                final int durabilityRemainingOnBlock = (int)(playerRelativeBlockHardness * 10.0f);
                this.theWorld.sendBlockBreakProgress(this.thisPlayerMP.getEntityId(), field_180240_f, durabilityRemainingOnBlock);
                this.durabilityRemainingOnBlock = durabilityRemainingOnBlock;
            }
        }
    }
    
    public boolean activateBlockOrUseItem(final EntityPlayer entityPlayer, final World world, final ItemStack itemStack, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (this.gameType == WorldSettings.GameType.SPECTATOR) {
            final TileEntity tileEntity = world.getTileEntity(blockPos);
            if (tileEntity instanceof ILockableContainer) {
                final Block block = world.getBlockState(blockPos).getBlock();
                ILockableContainer lockableContainer = (ILockableContainer)tileEntity;
                if (lockableContainer instanceof TileEntityChest && block instanceof BlockChest) {
                    lockableContainer = ((BlockChest)block).getLockableContainer(world, blockPos);
                }
                if (lockableContainer != null) {
                    entityPlayer.displayGUIChest(lockableContainer);
                    return " ".length() != 0;
                }
            }
            else if (tileEntity instanceof IInventory) {
                entityPlayer.displayGUIChest((IInventory)tileEntity);
                return " ".length() != 0;
            }
            return "".length() != 0;
        }
        if (!entityPlayer.isSneaking() || entityPlayer.getHeldItem() == null) {
            final IBlockState blockState = world.getBlockState(blockPos);
            if (blockState.getBlock().onBlockActivated(world, blockPos, blockState, entityPlayer, enumFacing, n, n2, n3)) {
                return " ".length() != 0;
            }
        }
        if (itemStack == null) {
            return "".length() != 0;
        }
        if (this.isCreative()) {
            final int metadata = itemStack.getMetadata();
            final int stackSize = itemStack.stackSize;
            final boolean onItemUse = itemStack.onItemUse(entityPlayer, world, blockPos, enumFacing, n, n2, n3);
            itemStack.setItemDamage(metadata);
            itemStack.stackSize = stackSize;
            return onItemUse;
        }
        return itemStack.onItemUse(entityPlayer, world, blockPos, enumFacing, n, n2, n3);
    }
    
    public boolean isCreative() {
        return this.gameType.isCreative();
    }
    
    private boolean removeBlock(final BlockPos blockToAir) {
        final IBlockState blockState = this.theWorld.getBlockState(blockToAir);
        blockState.getBlock().onBlockHarvested(this.theWorld, blockToAir, blockState, this.thisPlayerMP);
        final boolean setBlockToAir = this.theWorld.setBlockToAir(blockToAir);
        if (setBlockToAir) {
            blockState.getBlock().onBlockDestroyedByPlayer(this.theWorld, blockToAir, blockState);
        }
        return setBlockToAir;
    }
    
    public boolean tryUseItem(final EntityPlayer entityPlayer, final World world, final ItemStack itemStack) {
        if (this.gameType == WorldSettings.GameType.SPECTATOR) {
            return "".length() != 0;
        }
        final int stackSize = itemStack.stackSize;
        final int metadata = itemStack.getMetadata();
        final ItemStack useItemRightClick = itemStack.useItemRightClick(world, entityPlayer);
        if (useItemRightClick != itemStack || (useItemRightClick != null && (useItemRightClick.stackSize != stackSize || useItemRightClick.getMaxItemUseDuration() > 0 || useItemRightClick.getMetadata() != metadata))) {
            entityPlayer.inventory.mainInventory[entityPlayer.inventory.currentItem] = useItemRightClick;
            if (this.isCreative()) {
                useItemRightClick.stackSize = stackSize;
                if (useItemRightClick.isItemStackDamageable()) {
                    useItemRightClick.setItemDamage(metadata);
                }
            }
            if (useItemRightClick.stackSize == 0) {
                entityPlayer.inventory.mainInventory[entityPlayer.inventory.currentItem] = null;
            }
            if (!entityPlayer.isUsingItem()) {
                ((EntityPlayerMP)entityPlayer).sendContainerToPlayer(entityPlayer.inventoryContainer);
            }
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public void setWorld(final WorldServer theWorld) {
        this.theWorld = theWorld;
    }
}
