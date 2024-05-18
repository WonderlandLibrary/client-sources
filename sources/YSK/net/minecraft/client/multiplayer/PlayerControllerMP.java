package net.minecraft.client.multiplayer;

import net.minecraft.client.network.*;
import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import net.minecraft.world.*;
import net.minecraft.stats.*;
import net.minecraft.client.entity.*;
import net.minecraft.block.state.*;
import net.minecraft.item.*;
import net.minecraft.block.material.*;
import net.minecraft.block.*;
import net.minecraft.entity.passive.*;
import net.minecraft.network.play.client.*;
import net.minecraft.client.audio.*;
import net.minecraft.util.*;

public class PlayerControllerMP
{
    private float curBlockDamageMP;
    private WorldSettings.GameType currentGameType;
    private final NetHandlerPlayClient netClientHandler;
    private boolean isHittingBlock;
    private int currentPlayerItem;
    private float stepSoundTickCounter;
    private BlockPos currentBlock;
    private final Minecraft mc;
    private int blockHitDelay;
    private ItemStack currentItemHittingBlock;
    
    public boolean isSpectatorMode() {
        if (this.currentGameType == WorldSettings.GameType.SPECTATOR) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public boolean shouldDrawHUD() {
        return this.currentGameType.isSurvivalOrAdventure();
    }
    
    private boolean isHittingPosition(final BlockPos blockPos) {
        final ItemStack heldItem = this.mc.thePlayer.getHeldItem();
        int n;
        if (this.currentItemHittingBlock == null && heldItem == null) {
            n = " ".length();
            "".length();
            if (0 < 0) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        int n2 = n;
        if (this.currentItemHittingBlock != null && heldItem != null) {
            int n3;
            if (heldItem.getItem() == this.currentItemHittingBlock.getItem() && ItemStack.areItemStackTagsEqual(heldItem, this.currentItemHittingBlock) && (heldItem.isItemStackDamageable() || heldItem.getMetadata() == this.currentItemHittingBlock.getMetadata())) {
                n3 = " ".length();
                "".length();
                if (2 >= 4) {
                    throw null;
                }
            }
            else {
                n3 = "".length();
            }
            n2 = n3;
        }
        if (blockPos.equals(this.currentBlock) && n2 != 0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public void attackEntity(final EntityPlayer entityPlayer, final Entity entity) {
        this.syncCurrentPlayItem();
        this.netClientHandler.addToSendQueue(new C02PacketUseEntity(entity, C02PacketUseEntity.Action.ATTACK));
        if (this.currentGameType != WorldSettings.GameType.SPECTATOR) {
            entityPlayer.attackTargetEntityWithCurrentItem(entity);
        }
    }
    
    public void onStoppedUsingItem(final EntityPlayer entityPlayer) {
        this.syncCurrentPlayItem();
        this.netClientHandler.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
        entityPlayer.stopUsingItem();
    }
    
    public boolean sendUseItem(final EntityPlayer entityPlayer, final World world, final ItemStack itemStack) {
        if (this.currentGameType == WorldSettings.GameType.SPECTATOR) {
            return "".length() != 0;
        }
        this.syncCurrentPlayItem();
        this.netClientHandler.addToSendQueue(new C08PacketPlayerBlockPlacement(entityPlayer.inventory.getCurrentItem()));
        final int stackSize = itemStack.stackSize;
        final ItemStack useItemRightClick = itemStack.useItemRightClick(world, entityPlayer);
        if (useItemRightClick != itemStack || (useItemRightClick != null && useItemRightClick.stackSize != stackSize)) {
            entityPlayer.inventory.mainInventory[entityPlayer.inventory.currentItem] = useItemRightClick;
            if (useItemRightClick.stackSize == 0) {
                entityPlayer.inventory.mainInventory[entityPlayer.inventory.currentItem] = null;
            }
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public boolean isSpectator() {
        if (this.currentGameType == WorldSettings.GameType.SPECTATOR) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public void updateController() {
        this.syncCurrentPlayItem();
        if (this.netClientHandler.getNetworkManager().isChannelOpen()) {
            this.netClientHandler.getNetworkManager().processReceivedPackets();
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        else {
            this.netClientHandler.getNetworkManager().checkDisconnected();
        }
    }
    
    public EntityPlayerSP func_178892_a(final World world, final StatFileWriter statFileWriter) {
        return new EntityPlayerSP(this.mc, world, this.netClientHandler, statFileWriter);
    }
    
    public void setPlayerCapabilities(final EntityPlayer entityPlayer) {
        this.currentGameType.configurePlayerCapabilities(entityPlayer.capabilities);
    }
    
    public static void clickBlockCreative(final Minecraft minecraft, final PlayerControllerMP playerControllerMP, final BlockPos blockPos, final EnumFacing enumFacing) {
        if (!minecraft.theWorld.extinguishFire(minecraft.thePlayer, blockPos, enumFacing)) {
            playerControllerMP.onPlayerDestroyBlock(blockPos, enumFacing);
        }
    }
    
    public ItemStack windowClick(final int n, final int n2, final int n3, final int n4, final EntityPlayer entityPlayer) {
        final short nextTransactionID = entityPlayer.openContainer.getNextTransactionID(entityPlayer.inventory);
        final ItemStack slotClick = entityPlayer.openContainer.slotClick(n2, n3, n4, entityPlayer);
        this.netClientHandler.addToSendQueue(new C0EPacketClickWindow(n, n2, n3, n4, slotClick, nextTransactionID));
        return slotClick;
    }
    
    public void setGameType(final WorldSettings.GameType currentGameType) {
        (this.currentGameType = currentGameType).configurePlayerCapabilities(this.mc.thePlayer.capabilities);
    }
    
    public void sendPacketDropItem(final ItemStack itemStack) {
        if (this.currentGameType.isCreative() && itemStack != null) {
            this.netClientHandler.addToSendQueue(new C10PacketCreativeInventoryAction(-" ".length(), itemStack));
        }
    }
    
    public boolean interactWithEntitySendPacket(final EntityPlayer entityPlayer, final Entity entity) {
        this.syncCurrentPlayItem();
        this.netClientHandler.addToSendQueue(new C02PacketUseEntity(entity, C02PacketUseEntity.Action.INTERACT));
        if (this.currentGameType != WorldSettings.GameType.SPECTATOR && entityPlayer.interactWith(entity)) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public boolean onPlayerRightClick(final EntityPlayerSP entityPlayerSP, final WorldClient worldClient, final ItemStack itemStack, final BlockPos blockPos, final EnumFacing enumFacing, final Vec3 vec3) {
        this.syncCurrentPlayItem();
        final float n = (float)(vec3.xCoord - blockPos.getX());
        final float n2 = (float)(vec3.yCoord - blockPos.getY());
        final float n3 = (float)(vec3.zCoord - blockPos.getZ());
        int n4 = "".length();
        if (!this.mc.theWorld.getWorldBorder().contains(blockPos)) {
            return "".length() != 0;
        }
        if (this.currentGameType != WorldSettings.GameType.SPECTATOR) {
            final IBlockState blockState = worldClient.getBlockState(blockPos);
            if ((!entityPlayerSP.isSneaking() || entityPlayerSP.getHeldItem() == null) && blockState.getBlock().onBlockActivated(worldClient, blockPos, blockState, entityPlayerSP, enumFacing, n, n2, n3)) {
                n4 = " ".length();
            }
            if (n4 == 0 && itemStack != null && itemStack.getItem() instanceof ItemBlock && !((ItemBlock)itemStack.getItem()).canPlaceBlockOnSide(worldClient, blockPos, enumFacing, entityPlayerSP, itemStack)) {
                return "".length() != 0;
            }
        }
        this.netClientHandler.addToSendQueue(new C08PacketPlayerBlockPlacement(blockPos, enumFacing.getIndex(), entityPlayerSP.inventory.getCurrentItem(), n, n2, n3));
        if (n4 != 0 || this.currentGameType == WorldSettings.GameType.SPECTATOR) {
            return " ".length() != 0;
        }
        if (itemStack == null) {
            return "".length() != 0;
        }
        if (this.currentGameType.isCreative()) {
            final int metadata = itemStack.getMetadata();
            final int stackSize = itemStack.stackSize;
            final boolean onItemUse = itemStack.onItemUse(entityPlayerSP, worldClient, blockPos, enumFacing, n, n2, n3);
            itemStack.setItemDamage(metadata);
            itemStack.stackSize = stackSize;
            return onItemUse;
        }
        return itemStack.onItemUse(entityPlayerSP, worldClient, blockPos, enumFacing, n, n2, n3);
    }
    
    public boolean extendedReach() {
        return this.currentGameType.isCreative();
    }
    
    public boolean isNotCreative() {
        int n;
        if (this.currentGameType.isCreative()) {
            n = "".length();
            "".length();
            if (4 == -1) {
                throw null;
            }
        }
        else {
            n = " ".length();
        }
        return n != 0;
    }
    
    public WorldSettings.GameType getCurrentGameType() {
        return this.currentGameType;
    }
    
    public boolean onPlayerDestroyBlock(final BlockPos blockToAir, final EnumFacing enumFacing) {
        if (this.currentGameType.isAdventure()) {
            if (this.currentGameType == WorldSettings.GameType.SPECTATOR) {
                return "".length() != 0;
            }
            if (!this.mc.thePlayer.isAllowEdit()) {
                final Block block = this.mc.theWorld.getBlockState(blockToAir).getBlock();
                final ItemStack currentEquippedItem = this.mc.thePlayer.getCurrentEquippedItem();
                if (currentEquippedItem == null) {
                    return "".length() != 0;
                }
                if (!currentEquippedItem.canDestroy(block)) {
                    return "".length() != 0;
                }
            }
        }
        if (this.currentGameType.isCreative() && this.mc.thePlayer.getHeldItem() != null && this.mc.thePlayer.getHeldItem().getItem() instanceof ItemSword) {
            return "".length() != 0;
        }
        final WorldClient theWorld = this.mc.theWorld;
        final IBlockState blockState = theWorld.getBlockState(blockToAir);
        final Block block2 = blockState.getBlock();
        if (block2.getMaterial() == Material.air) {
            return "".length() != 0;
        }
        theWorld.playAuxSFX(1633 + 1768 - 1854 + 454, blockToAir, Block.getStateId(blockState));
        final boolean setBlockToAir = theWorld.setBlockToAir(blockToAir);
        if (setBlockToAir) {
            block2.onBlockDestroyedByPlayer(theWorld, blockToAir, blockState);
        }
        this.currentBlock = new BlockPos(this.currentBlock.getX(), -" ".length(), this.currentBlock.getZ());
        if (!this.currentGameType.isCreative()) {
            final ItemStack currentEquippedItem2 = this.mc.thePlayer.getCurrentEquippedItem();
            if (currentEquippedItem2 != null) {
                currentEquippedItem2.onBlockDestroyed(theWorld, block2, blockToAir, this.mc.thePlayer);
                if (currentEquippedItem2.stackSize == 0) {
                    this.mc.thePlayer.destroyCurrentEquippedItem();
                }
            }
        }
        return setBlockToAir;
    }
    
    public boolean isRidingHorse() {
        if (this.mc.thePlayer.isRiding() && this.mc.thePlayer.ridingEntity instanceof EntityHorse) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public boolean gameIsSurvivalOrAdventure() {
        return this.currentGameType.isSurvivalOrAdventure();
    }
    
    private void syncCurrentPlayItem() {
        final int currentItem = this.mc.thePlayer.inventory.currentItem;
        if (currentItem != this.currentPlayerItem) {
            this.currentPlayerItem = currentItem;
            this.netClientHandler.addToSendQueue(new C09PacketHeldItemChange(this.currentPlayerItem));
        }
    }
    
    public void resetBlockRemoving() {
        if (this.isHittingBlock) {
            this.netClientHandler.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, this.currentBlock, EnumFacing.DOWN));
            this.isHittingBlock = ("".length() != 0);
            this.curBlockDamageMP = 0.0f;
            this.mc.theWorld.sendBlockBreakProgress(this.mc.thePlayer.getEntityId(), this.currentBlock, -" ".length());
        }
    }
    
    public boolean func_181040_m() {
        return this.isHittingBlock;
    }
    
    public void sendSlotPacket(final ItemStack itemStack, final int n) {
        if (this.currentGameType.isCreative()) {
            this.netClientHandler.addToSendQueue(new C10PacketCreativeInventoryAction(n, itemStack));
        }
    }
    
    public void sendEnchantPacket(final int n, final int n2) {
        this.netClientHandler.addToSendQueue(new C11PacketEnchantItem(n, n2));
    }
    
    public boolean onPlayerDamageBlock(final BlockPos blockPos, final EnumFacing enumFacing) {
        this.syncCurrentPlayItem();
        if (this.blockHitDelay > 0) {
            this.blockHitDelay -= " ".length();
            return " ".length() != 0;
        }
        if (this.currentGameType.isCreative() && this.mc.theWorld.getWorldBorder().contains(blockPos)) {
            this.blockHitDelay = (0xC0 ^ 0xC5);
            this.netClientHandler.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, blockPos, enumFacing));
            clickBlockCreative(this.mc, this, blockPos, enumFacing);
            return " ".length() != 0;
        }
        if (!this.isHittingPosition(blockPos)) {
            return this.clickBlock(blockPos, enumFacing);
        }
        final Block block = this.mc.theWorld.getBlockState(blockPos).getBlock();
        if (block.getMaterial() == Material.air) {
            this.isHittingBlock = ("".length() != 0);
            return "".length() != 0;
        }
        this.curBlockDamageMP += block.getPlayerRelativeBlockHardness(this.mc.thePlayer, this.mc.thePlayer.worldObj, blockPos);
        if (this.stepSoundTickCounter % 4.0f == 0.0f) {
            this.mc.getSoundHandler().playSound(new PositionedSoundRecord(new ResourceLocation(block.stepSound.getStepSound()), (block.stepSound.getVolume() + 1.0f) / 8.0f, block.stepSound.getFrequency() * 0.5f, blockPos.getX() + 0.5f, blockPos.getY() + 0.5f, blockPos.getZ() + 0.5f));
        }
        ++this.stepSoundTickCounter;
        if (this.curBlockDamageMP >= 1.0f) {
            this.isHittingBlock = ("".length() != 0);
            this.netClientHandler.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, blockPos, enumFacing));
            this.onPlayerDestroyBlock(blockPos, enumFacing);
            this.curBlockDamageMP = 0.0f;
            this.stepSoundTickCounter = 0.0f;
            this.blockHitDelay = (0xB5 ^ 0xB0);
        }
        this.mc.theWorld.sendBlockBreakProgress(this.mc.thePlayer.getEntityId(), this.currentBlock, (int)(this.curBlockDamageMP * 10.0f) - " ".length());
        return " ".length() != 0;
    }
    
    public boolean isInCreativeMode() {
        return this.currentGameType.isCreative();
    }
    
    public boolean func_178894_a(final EntityPlayer entityPlayer, final Entity entity, final MovingObjectPosition movingObjectPosition) {
        this.syncCurrentPlayItem();
        final Vec3 vec3 = new Vec3(movingObjectPosition.hitVec.xCoord - entity.posX, movingObjectPosition.hitVec.yCoord - entity.posY, movingObjectPosition.hitVec.zCoord - entity.posZ);
        this.netClientHandler.addToSendQueue(new C02PacketUseEntity(entity, vec3));
        if (this.currentGameType != WorldSettings.GameType.SPECTATOR && entity.interactAt(entityPlayer, vec3)) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public void flipPlayer(final EntityPlayer entityPlayer) {
        entityPlayer.rotationYaw = -180.0f;
    }
    
    public boolean clickBlock(final BlockPos currentBlock, final EnumFacing enumFacing) {
        if (this.currentGameType.isAdventure()) {
            if (this.currentGameType == WorldSettings.GameType.SPECTATOR) {
                return "".length() != 0;
            }
            if (!this.mc.thePlayer.isAllowEdit()) {
                final Block block = this.mc.theWorld.getBlockState(currentBlock).getBlock();
                final ItemStack currentEquippedItem = this.mc.thePlayer.getCurrentEquippedItem();
                if (currentEquippedItem == null) {
                    return "".length() != 0;
                }
                if (!currentEquippedItem.canDestroy(block)) {
                    return "".length() != 0;
                }
            }
        }
        if (!this.mc.theWorld.getWorldBorder().contains(currentBlock)) {
            return "".length() != 0;
        }
        if (this.currentGameType.isCreative()) {
            this.netClientHandler.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, currentBlock, enumFacing));
            clickBlockCreative(this.mc, this, currentBlock, enumFacing);
            this.blockHitDelay = (0x93 ^ 0x96);
            "".length();
            if (4 == 2) {
                throw null;
            }
        }
        else if (!this.isHittingBlock || !this.isHittingPosition(currentBlock)) {
            if (this.isHittingBlock) {
                this.netClientHandler.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, this.currentBlock, enumFacing));
            }
            this.netClientHandler.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, currentBlock, enumFacing));
            final Block block2 = this.mc.theWorld.getBlockState(currentBlock).getBlock();
            int n;
            if (block2.getMaterial() != Material.air) {
                n = " ".length();
                "".length();
                if (2 < 1) {
                    throw null;
                }
            }
            else {
                n = "".length();
            }
            final int n2 = n;
            if (n2 != 0 && this.curBlockDamageMP == 0.0f) {
                block2.onBlockClicked(this.mc.theWorld, currentBlock, this.mc.thePlayer);
            }
            if (n2 != 0 && block2.getPlayerRelativeBlockHardness(this.mc.thePlayer, this.mc.thePlayer.worldObj, currentBlock) >= 1.0f) {
                this.onPlayerDestroyBlock(currentBlock, enumFacing);
                "".length();
                if (1 == -1) {
                    throw null;
                }
            }
            else {
                this.isHittingBlock = (" ".length() != 0);
                this.currentBlock = currentBlock;
                this.currentItemHittingBlock = this.mc.thePlayer.getHeldItem();
                this.curBlockDamageMP = 0.0f;
                this.stepSoundTickCounter = 0.0f;
                this.mc.theWorld.sendBlockBreakProgress(this.mc.thePlayer.getEntityId(), this.currentBlock, (int)(this.curBlockDamageMP * 10.0f) - " ".length());
            }
        }
        return " ".length() != 0;
    }
    
    public float getBlockReachDistance() {
        float n;
        if (this.currentGameType.isCreative()) {
            n = 5.0f;
            "".length();
            if (1 >= 3) {
                throw null;
            }
        }
        else {
            n = 4.5f;
        }
        return n;
    }
    
    public PlayerControllerMP(final Minecraft mc, final NetHandlerPlayClient netClientHandler) {
        this.currentBlock = new BlockPos(-" ".length(), -" ".length(), -" ".length());
        this.currentGameType = WorldSettings.GameType.SURVIVAL;
        this.mc = mc;
        this.netClientHandler = netClientHandler;
    }
    
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
            if (2 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
}
