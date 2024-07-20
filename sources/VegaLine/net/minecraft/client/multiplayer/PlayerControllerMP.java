/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.multiplayer;

import io.netty.buffer.Unpooled;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCommandBlock;
import net.minecraft.block.BlockStructure;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.CPacketClickWindow;
import net.minecraft.network.play.client.CPacketCreativeInventoryAction;
import net.minecraft.network.play.client.CPacketCustomPayload;
import net.minecraft.network.play.client.CPacketEnchantItem;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlaceRecipe;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.stats.RecipeBook;
import net.minecraft.stats.StatisticsManager;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameType;
import net.minecraft.world.World;
import ru.govno.client.Client;
import ru.govno.client.event.events.EventAttackSilent;
import ru.govno.client.module.modules.Criticals;
import ru.govno.client.module.modules.ESP;
import ru.govno.client.module.modules.HitBubble;
import ru.govno.client.module.modules.MiddleClick;
import ru.govno.client.module.modules.NoClip;
import ru.govno.client.module.modules.Nuker;
import ru.govno.client.module.modules.PlayerHelper;
import ru.govno.client.utils.Command.impl.Panic;

public class PlayerControllerMP {
    private final Minecraft mc;
    private final NetHandlerPlayClient connection;
    public BlockPos currentBlock = new BlockPos(-1, -1, -1);
    private ItemStack currentItemHittingBlock = ItemStack.field_190927_a;
    public float curBlockDamageMP;
    private float stepSoundTickCounter;
    public int blockHitDelay;
    public boolean isHittingBlock;
    private GameType currentGameType = GameType.SURVIVAL;
    private int currentPlayerItem;
    public float extendedReach;
    public Object getBlockReachDistance;

    public PlayerControllerMP(Minecraft mcIn, NetHandlerPlayClient netHandler) {
        this.mc = mcIn;
        this.connection = netHandler;
    }

    public static void clickBlockCreative(Minecraft mcIn, PlayerControllerMP playerController, BlockPos pos, EnumFacing facing) {
        if (!mcIn.world.extinguishFire(Minecraft.player, pos, facing)) {
            playerController.onPlayerDestroyBlock(pos);
        }
    }

    public void setPlayerCapabilities(EntityPlayer player) {
        this.currentGameType.configurePlayerCapabilities(player.capabilities);
    }

    public boolean isSpectator() {
        return this.currentGameType == GameType.SPECTATOR;
    }

    public void setGameType(GameType type2) {
        this.currentGameType = type2;
        this.currentGameType.configurePlayerCapabilities(Minecraft.player.capabilities);
    }

    public void flipPlayer(EntityPlayer playerIn) {
        playerIn.rotationYaw = -180.0f;
    }

    public boolean shouldDrawHUD() {
        return this.currentGameType.isSurvivalOrAdventure();
    }

    public boolean onPlayerDestroyBlock(BlockPos pos) {
        ItemStack itemstack1;
        if (this.currentGameType.isAdventure()) {
            if (this.currentGameType == GameType.SPECTATOR) {
                return false;
            }
            if (!Minecraft.player.isAllowEdit()) {
                ItemStack itemstack = Minecraft.player.getHeldItemMainhand();
                if (itemstack.func_190926_b()) {
                    return false;
                }
                if (!itemstack.canDestroy(this.mc.world.getBlockState(pos).getBlock())) {
                    return false;
                }
            }
        }
        if (this.currentGameType.isCreative() && !Minecraft.player.getHeldItemMainhand().func_190926_b() && Minecraft.player.getHeldItemMainhand().getItem() instanceof ItemSword) {
            return false;
        }
        WorldClient world = this.mc.world;
        IBlockState iblockstate = world.getBlockState(pos);
        Block block = iblockstate.getBlock();
        if ((block instanceof BlockCommandBlock || block instanceof BlockStructure) && !Minecraft.player.canUseCommandBlock()) {
            return false;
        }
        if (iblockstate.getMaterial() == Material.AIR) {
            return false;
        }
        world.playEvent(2001, pos, Block.getStateId(iblockstate));
        block.onBlockHarvested(world, pos, iblockstate, Minecraft.player);
        boolean flag = ((World)world).setBlockState(pos, Blocks.AIR.getDefaultState(), 11);
        if (flag) {
            block.onBlockDestroyedByPlayer(world, pos, iblockstate);
        }
        this.currentBlock = new BlockPos(this.currentBlock.getX(), -1, this.currentBlock.getZ());
        if (!this.currentGameType.isCreative() && !(itemstack1 = Minecraft.player.getHeldItemMainhand()).func_190926_b()) {
            itemstack1.onBlockDestroyed(world, iblockstate, pos, Minecraft.player);
            if (itemstack1.func_190926_b()) {
                Minecraft.player.setHeldItem(EnumHand.MAIN_HAND, ItemStack.field_190927_a);
            }
        }
        return flag;
    }

    public boolean clickBlock(BlockPos loc, EnumFacing face) {
        if (this.currentGameType.isAdventure()) {
            ItemStack itemstack;
            if (this.currentGameType == GameType.SPECTATOR) {
                return false;
            }
            if (!Minecraft.player.isAllowEdit() && !(itemstack = Minecraft.player.getHeldItemMainhand()).canDestroy(this.mc.world.getBlockState(loc).getBlock())) {
                return false;
            }
        }
        if (!this.mc.world.getWorldBorder().contains(loc)) {
            return false;
        }
        if (this.currentGameType.isCreative()) {
            this.mc.func_193032_ao().func_193294_a(this.mc.world, loc, this.mc.world.getBlockState(loc), 1.0f);
            this.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, loc, face));
            PlayerControllerMP.clickBlockCreative(this.mc, this, loc, face);
            this.blockHitDelay = 5;
        } else if (!this.isHittingBlock || !this.isHittingPosition(loc)) {
            boolean flag;
            if (this.isHittingBlock) {
                this.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, this.currentBlock, face));
            }
            IBlockState iblockstate = this.mc.world.getBlockState(loc);
            this.mc.func_193032_ao().func_193294_a(this.mc.world, loc, iblockstate, 0.0f);
            this.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, loc, face));
            boolean bl = flag = iblockstate.getMaterial() != Material.AIR;
            if (flag && this.curBlockDamageMP == 0.0f) {
                iblockstate.getBlock().onBlockClicked(this.mc.world, loc, Minecraft.player);
            }
            if (flag && iblockstate.getPlayerRelativeBlockHardness(Minecraft.player, Minecraft.player.world, loc) >= 1.0f) {
                this.onPlayerDestroyBlock(loc);
            } else {
                this.isHittingBlock = true;
                this.currentBlock = loc;
                this.currentItemHittingBlock = Minecraft.player.getHeldItemMainhand();
                this.curBlockDamageMP = 0.0f;
                this.stepSoundTickCounter = 0.0f;
                this.mc.world.sendBlockBreakProgress(Minecraft.player.getEntityId(), this.currentBlock, (int)(this.curBlockDamageMP * 10.0f) - 1);
            }
        }
        return true;
    }

    public void resetBlockRemoving() {
        if (this.isHittingBlock) {
            this.mc.func_193032_ao().func_193294_a(this.mc.world, this.currentBlock, this.mc.world.getBlockState(this.currentBlock), -1.0f);
            this.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, this.currentBlock, EnumFacing.DOWN));
            this.isHittingBlock = false;
            this.curBlockDamageMP = 0.0f;
            this.mc.world.sendBlockBreakProgress(Minecraft.player.getEntityId(), this.currentBlock, -1);
            Minecraft.player.resetCooldown();
        }
    }

    public boolean onPlayerDamageBlock(BlockPos posBlock, EnumFacing directionFacing) {
        if (directionFacing == null) {
            directionFacing = EnumFacing.NORTH;
        }
        if (!(Panic.stop || NoClip.get != null && NoClip.get.actived)) {
            PlayerHelper.currentBlock = posBlock;
            Nuker.renderPosition = posBlock;
            ESP.get.posOver = posBlock;
        }
        this.syncCurrentPlayItem();
        if (this.blockHitDelay > 0) {
            --this.blockHitDelay;
            return true;
        }
        if (this.currentGameType.isCreative() && this.mc.world.getWorldBorder().contains(posBlock)) {
            this.blockHitDelay = 5;
            this.mc.func_193032_ao().func_193294_a(this.mc.world, posBlock, this.mc.world.getBlockState(posBlock), 1.0f);
            this.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, posBlock, directionFacing));
            PlayerControllerMP.clickBlockCreative(this.mc, this, posBlock, directionFacing);
            return true;
        }
        if (this.isHittingPosition(posBlock)) {
            IBlockState iblockstate = this.mc.world.getBlockState(posBlock);
            Block block = iblockstate.getBlock();
            if (iblockstate.getMaterial() == Material.AIR) {
                this.isHittingBlock = false;
                return false;
            }
            this.curBlockDamageMP += iblockstate.getPlayerRelativeBlockHardness(Minecraft.player, Minecraft.player.world, posBlock);
            if (this.stepSoundTickCounter % 4.0f == 0.0f) {
                SoundType soundtype = block.getSoundType();
                this.mc.getSoundHandler().playSound(new PositionedSoundRecord(soundtype.getHitSound(), SoundCategory.NEUTRAL, (soundtype.getVolume() + 1.0f) / 8.0f, soundtype.getPitch() * 0.5f, posBlock));
            }
            this.stepSoundTickCounter += 1.0f;
            this.mc.func_193032_ao().func_193294_a(this.mc.world, posBlock, iblockstate, MathHelper.clamp(this.curBlockDamageMP, 0.0f, 1.0f));
            if (this.curBlockDamageMP >= 1.0f) {
                this.isHittingBlock = false;
                this.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, posBlock, directionFacing));
                this.onPlayerDestroyBlock(posBlock);
                this.curBlockDamageMP = 0.0f;
                this.stepSoundTickCounter = 0.0f;
                this.blockHitDelay = 5;
            }
            this.mc.world.sendBlockBreakProgress(Minecraft.player.getEntityId(), this.currentBlock, (int)(this.curBlockDamageMP * 10.0f) - 1);
            return true;
        }
        return this.clickBlock(posBlock, directionFacing);
    }

    public float getBlockReachDistance() {
        return this.currentGameType.isCreative() ? 5.0f : 4.5f;
    }

    public void updateController() {
        this.syncCurrentPlayItem();
        if (this.connection.getNetworkManager().isChannelOpen()) {
            this.connection.getNetworkManager().processReceivedPackets();
        } else {
            this.connection.getNetworkManager().checkDisconnected();
        }
    }

    private boolean isHittingPosition(BlockPos pos) {
        boolean flag;
        ItemStack itemstack = Minecraft.player.getHeldItemMainhand();
        boolean bl = flag = this.currentItemHittingBlock.func_190926_b() && itemstack.func_190926_b();
        if (!this.currentItemHittingBlock.func_190926_b() && !itemstack.func_190926_b()) {
            flag = itemstack.getItem() == this.currentItemHittingBlock.getItem() && ItemStack.areItemStackTagsEqual(itemstack, this.currentItemHittingBlock) && (itemstack.isItemStackDamageable() || itemstack.getMetadata() == this.currentItemHittingBlock.getMetadata());
        }
        return pos.equals(this.currentBlock) && flag;
    }

    public void syncCurrentPlayItem() {
        int i = Minecraft.player.inventory.currentItem;
        if (i != this.currentPlayerItem) {
            this.currentPlayerItem = i;
            this.connection.sendPacket(new CPacketHeldItemChange(this.currentPlayerItem));
        }
    }

    public EnumActionResult processRightClickBlock(EntityPlayerSP player, WorldClient worldIn, BlockPos stack, EnumFacing pos, Vec3d facing, EnumHand vec) {
        this.syncCurrentPlayItem();
        ItemStack itemstack = player.getHeldItem(vec);
        float f = (float)(facing.xCoord - (double)stack.getX());
        float f1 = (float)(facing.yCoord - (double)stack.getY());
        float f2 = (float)(facing.zCoord - (double)stack.getZ());
        boolean flag = false;
        if (!this.mc.world.getWorldBorder().contains(stack)) {
            return EnumActionResult.FAIL;
        }
        if (this.currentGameType != GameType.SPECTATOR) {
            ItemBlock itemblock;
            Item item;
            IBlockState iblockstate = worldIn.getBlockState(stack);
            if ((!player.isSneaking() || player.isNewSneak && !this.mc.gameSettings.keyBindSneak.isKeyDown() || player.getHeldItemMainhand().func_190926_b() && player.getHeldItemOffhand().func_190926_b()) && iblockstate.getBlock().onBlockActivated(worldIn, stack, iblockstate, player, vec, pos, f, f1, f2)) {
                flag = true;
            }
            if (!flag && (item = itemstack.getItem()) instanceof ItemBlock && !(itemblock = (ItemBlock)item).canPlaceBlockOnSide(worldIn, stack, pos, player, itemstack)) {
                return EnumActionResult.FAIL;
            }
        }
        this.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(stack, pos, vec, f, f1, f2));
        if (!flag && this.currentGameType != GameType.SPECTATOR) {
            Block block;
            if (itemstack.func_190926_b()) {
                return EnumActionResult.PASS;
            }
            if (player.getCooldownTracker().hasCooldown(itemstack.getItem())) {
                return EnumActionResult.PASS;
            }
            if (itemstack.getItem() instanceof ItemBlock && !player.canUseCommandBlock() && ((block = ((ItemBlock)itemstack.getItem()).getBlock()) instanceof BlockCommandBlock || block instanceof BlockStructure)) {
                return EnumActionResult.FAIL;
            }
            if (this.currentGameType.isCreative()) {
                int i = itemstack.getMetadata();
                int j = itemstack.func_190916_E();
                EnumActionResult enumactionresult = itemstack.onItemUse(player, worldIn, stack, vec, pos, f, f1, f2);
                itemstack.setItemDamage(i);
                itemstack.func_190920_e(j);
                return enumactionresult;
            }
            return itemstack.onItemUse(player, worldIn, stack, vec, pos, f, f1, f2);
        }
        return EnumActionResult.SUCCESS;
    }

    public EnumActionResult processRightClick(EntityPlayer player, World worldIn, EnumHand stack) {
        Item item;
        boolean stackCooledByClient;
        if (this.currentGameType == GameType.SPECTATOR) {
            return EnumActionResult.PASS;
        }
        this.syncCurrentPlayItem();
        ItemStack itemstack = player.getHeldItem(stack);
        boolean bl = stackCooledByClient = !Panic.stop && PlayerHelper.get.actived && (itemstack.getItem() instanceof ItemAppleGold && PlayerHelper.checkApple || itemstack.getItem() instanceof ItemEnderPearl && PlayerHelper.checkPearl);
        if (!stackCooledByClient) {
            this.connection.sendPacket(new CPacketPlayerTryUseItem(stack));
        }
        if ((item = itemstack.getItem()) instanceof ItemEnderPearl) {
            ItemEnderPearl pearl = (ItemEnderPearl)item;
            if (Minecraft.player.getCooldownTracker().getCooldown(pearl, 0.0f) == 0.0f && !Panic.stop && PlayerHelper.get.actived && PlayerHelper.get.PearlThrowCooldown.bValue && !PlayerHelper.checkPearl && PlayerHelper.canCooldown()) {
                PlayerHelper.trackPearl();
            }
        }
        if (player.getCooldownTracker().hasCooldown(itemstack.getItem()) || stackCooledByClient) {
            return EnumActionResult.PASS;
        }
        int i = itemstack.func_190916_E();
        ActionResult<ItemStack> actionresult = itemstack.useItemRightClick(worldIn, player, stack);
        ItemStack itemstack1 = actionresult.getResult();
        if (itemstack1 != itemstack || itemstack1.func_190916_E() != i) {
            player.setHeldItem(stack, itemstack1);
        }
        return actionresult.getType();
    }

    public EntityPlayerSP func_192830_a(World p_192830_1_, StatisticsManager p_192830_2_, RecipeBook p_192830_3_) {
        return new EntityPlayerSP(this.mc, p_192830_1_, this.connection, p_192830_2_, p_192830_3_);
    }

    public void attackEntity(EntityPlayer playerIn, Entity targetEntity) {
        if (playerIn instanceof EntityPlayerSP) {
            Criticals.crits(targetEntity);
            if (HitBubble.get != null && HitBubble.get.actived) {
                HitBubble.onAttack(targetEntity);
            }
        }
        EventAttackSilent attackEvent = new EventAttackSilent(targetEntity);
        attackEvent.call();
        if (attackEvent.isCancelled()) {
            return;
        }
        if (MiddleClick.get.NoHitFriend.bValue && Client.friendManager.isFriend(targetEntity.getName())) {
            return;
        }
        this.syncCurrentPlayItem();
        this.connection.sendPacket(new CPacketUseEntity(targetEntity));
        if (this.currentGameType != GameType.SPECTATOR) {
            playerIn.attackTargetEntityWithCurrentItem(targetEntity);
            playerIn.resetCooldown();
        }
    }

    public void attackEntity2(EntityPlayer playerIn, EntityLivingBase targetEntity) {
        if (playerIn instanceof EntityPlayerSP) {
            Criticals.crits(targetEntity);
        }
        if (MiddleClick.get.NoHitFriend.bValue && Client.friendManager.isFriend(targetEntity.getName())) {
            return;
        }
        if (this.currentGameType != GameType.SPECTATOR) {
            this.syncCurrentPlayItem();
            this.connection.sendPacket(new CPacketUseEntity(targetEntity));
            playerIn.attackTargetEntityWithCurrentItem(targetEntity);
            playerIn.resetCooldown();
        }
    }

    public EnumActionResult interactWithEntity(EntityPlayer player, Entity target, EnumHand heldItem) {
        this.syncCurrentPlayItem();
        this.connection.sendPacket(new CPacketUseEntity(target, heldItem));
        return this.currentGameType == GameType.SPECTATOR ? EnumActionResult.PASS : player.func_190775_a(target, heldItem);
    }

    public EnumActionResult interactWithEntity(EntityPlayer player, Entity target, RayTraceResult raytrace, EnumHand heldItem) {
        this.syncCurrentPlayItem();
        Vec3d vec3d = new Vec3d(raytrace.hitVec.xCoord - target.posX, raytrace.hitVec.yCoord - target.posY, raytrace.hitVec.zCoord - target.posZ);
        this.connection.sendPacket(new CPacketUseEntity(target, heldItem, vec3d));
        return this.currentGameType == GameType.SPECTATOR ? EnumActionResult.PASS : target.applyPlayerInteraction(player, vec3d, heldItem);
    }

    public ItemStack windowClick(int windowId, int slotId, int mouseButton, ClickType type2, EntityPlayer player) {
        short short1 = player.openContainer.getNextTransactionID(player.inventory);
        ItemStack itemstack = player.openContainer.slotClick(slotId, mouseButton, type2, player);
        this.connection.sendPacket(new CPacketClickWindow(windowId, slotId, mouseButton, type2, itemstack, short1));
        return itemstack;
    }

    public void windowClickMemory(int windowId, int slotId, int mouseButton, ClickType type2, EntityPlayer player, int timeWait) {
        EntityPlayerSP.windowClickMemory.add(new EntityPlayerSP.WindowClickMemory(windowId, slotId, mouseButton, type2, player, timeWait));
    }

    public void func_194338_a(int p_194338_1_, IRecipe p_194338_2_, boolean p_194338_3_, EntityPlayer p_194338_4_) {
        this.connection.sendPacket(new CPacketPlaceRecipe(p_194338_1_, p_194338_2_, p_194338_3_));
    }

    public void sendEnchantPacket(int windowID, int button) {
        this.connection.sendPacket(new CPacketEnchantItem(windowID, button));
    }

    public void sendSlotPacket(ItemStack itemStackIn, int slotId) {
        if (this.currentGameType.isCreative()) {
            this.connection.sendPacket(new CPacketCreativeInventoryAction(slotId, itemStackIn));
        }
    }

    public void sendPacketDropItem(ItemStack itemStackIn) {
        if (this.currentGameType.isCreative() && !itemStackIn.func_190926_b()) {
            this.connection.sendPacket(new CPacketCreativeInventoryAction(-1, itemStackIn));
        }
    }

    public void onStoppedUsingItem(EntityPlayer playerIn) {
        this.syncCurrentPlayItem();
        this.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
        playerIn.stopActiveHand();
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
        return Minecraft.player.isRiding() && Minecraft.player.getRidingEntity() instanceof AbstractHorse;
    }

    public boolean isSpectatorMode() {
        return this.currentGameType == GameType.SPECTATOR;
    }

    public GameType getCurrentGameType() {
        return this.currentGameType;
    }

    public boolean getIsHittingBlock() {
        return this.isHittingBlock;
    }

    public void pickItem(int index) {
        this.connection.sendPacket(new CPacketCustomPayload("MC|PickItem", new PacketBuffer(Unpooled.buffer()).writeVarIntToBuffer(index)));
    }
}

