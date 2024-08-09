/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.server.management;

import java.util.Objects;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CommandBlockBlock;
import net.minecraft.block.JigsawBlock;
import net.minecraft.block.StructureBlock;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.network.play.client.CPlayerDiggingPacket;
import net.minecraft.network.play.server.SPlayerDiggingPacket;
import net.minecraft.network.play.server.SPlayerListItemPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.GameType;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PlayerInteractionManager {
    private static final Logger field_225418_c = LogManager.getLogger();
    public ServerWorld world;
    public ServerPlayerEntity player;
    private GameType gameType = GameType.NOT_SET;
    private GameType field_241813_e_ = GameType.NOT_SET;
    private boolean isDestroyingBlock;
    private int initialDamage;
    private BlockPos destroyPos = BlockPos.ZERO;
    private int ticks;
    private boolean receivedFinishDiggingPacket;
    private BlockPos delayedDestroyPos = BlockPos.ZERO;
    private int initialBlockDamage;
    private int durabilityRemainingOnBlock = -1;

    public PlayerInteractionManager(ServerWorld serverWorld) {
        this.world = serverWorld;
    }

    public void setGameType(GameType gameType) {
        this.func_241820_a(gameType, gameType != this.gameType ? this.gameType : this.field_241813_e_);
    }

    public void func_241820_a(GameType gameType, GameType gameType2) {
        this.field_241813_e_ = gameType2;
        this.gameType = gameType;
        gameType.configurePlayerCapabilities(this.player.abilities);
        this.player.sendPlayerAbilities();
        this.player.server.getPlayerList().sendPacketToAllPlayers(new SPlayerListItemPacket(SPlayerListItemPacket.Action.UPDATE_GAME_MODE, this.player));
        this.world.updateAllPlayersSleepingFlag();
    }

    public GameType getGameType() {
        return this.gameType;
    }

    public GameType func_241815_c_() {
        return this.field_241813_e_;
    }

    public boolean survivalOrAdventure() {
        return this.gameType.isSurvivalOrAdventure();
    }

    public boolean isCreative() {
        return this.gameType.isCreative();
    }

    public void initializeGameType(GameType gameType) {
        if (this.gameType == GameType.NOT_SET) {
            this.gameType = gameType;
        }
        this.setGameType(this.gameType);
    }

    public void tick() {
        ++this.ticks;
        if (this.receivedFinishDiggingPacket) {
            BlockState blockState = this.world.getBlockState(this.delayedDestroyPos);
            if (blockState.isAir()) {
                this.receivedFinishDiggingPacket = false;
            } else {
                float f = this.func_229859_a_(blockState, this.delayedDestroyPos, this.initialBlockDamage);
                if (f >= 1.0f) {
                    this.receivedFinishDiggingPacket = false;
                    this.tryHarvestBlock(this.delayedDestroyPos);
                }
            }
        } else if (this.isDestroyingBlock) {
            BlockState blockState = this.world.getBlockState(this.destroyPos);
            if (blockState.isAir()) {
                this.world.sendBlockBreakProgress(this.player.getEntityId(), this.destroyPos, -1);
                this.durabilityRemainingOnBlock = -1;
                this.isDestroyingBlock = false;
            } else {
                this.func_229859_a_(blockState, this.destroyPos, this.initialDamage);
            }
        }
    }

    private float func_229859_a_(BlockState blockState, BlockPos blockPos, int n) {
        int n2 = this.ticks - n;
        float f = blockState.getPlayerRelativeBlockHardness(this.player, this.player.world, blockPos) * (float)(n2 + 1);
        int n3 = (int)(f * 10.0f);
        if (n3 != this.durabilityRemainingOnBlock) {
            this.world.sendBlockBreakProgress(this.player.getEntityId(), blockPos, n3);
            this.durabilityRemainingOnBlock = n3;
        }
        return f;
    }

    public void func_225416_a(BlockPos blockPos, CPlayerDiggingPacket.Action action, Direction direction, int n) {
        double d;
        double d2;
        double d3 = this.player.getPosX() - ((double)blockPos.getX() + 0.5);
        double d4 = d3 * d3 + (d2 = this.player.getPosY() - ((double)blockPos.getY() + 0.5) + 1.5) * d2 + (d = this.player.getPosZ() - ((double)blockPos.getZ() + 0.5)) * d;
        if (d4 > 36.0) {
            this.player.connection.sendPacket(new SPlayerDiggingPacket(blockPos, this.world.getBlockState(blockPos), action, false, "too far"));
        } else if (blockPos.getY() >= n) {
            this.player.connection.sendPacket(new SPlayerDiggingPacket(blockPos, this.world.getBlockState(blockPos), action, false, "too high"));
        } else if (action == CPlayerDiggingPacket.Action.START_DESTROY_BLOCK) {
            if (!this.world.isBlockModifiable(this.player, blockPos)) {
                this.player.connection.sendPacket(new SPlayerDiggingPacket(blockPos, this.world.getBlockState(blockPos), action, false, "may not interact"));
                return;
            }
            if (this.isCreative()) {
                this.func_229860_a_(blockPos, action, "creative destroy");
                return;
            }
            if (this.player.blockActionRestricted(this.world, blockPos, this.gameType)) {
                this.player.connection.sendPacket(new SPlayerDiggingPacket(blockPos, this.world.getBlockState(blockPos), action, false, "block action restricted"));
                return;
            }
            this.initialDamage = this.ticks;
            float f = 1.0f;
            BlockState blockState = this.world.getBlockState(blockPos);
            if (!blockState.isAir()) {
                blockState.onBlockClicked(this.world, blockPos, this.player);
                f = blockState.getPlayerRelativeBlockHardness(this.player, this.player.world, blockPos);
            }
            if (!blockState.isAir() && f >= 1.0f) {
                this.func_229860_a_(blockPos, action, "insta mine");
            } else {
                if (this.isDestroyingBlock) {
                    this.player.connection.sendPacket(new SPlayerDiggingPacket(this.destroyPos, this.world.getBlockState(this.destroyPos), CPlayerDiggingPacket.Action.START_DESTROY_BLOCK, false, "abort destroying since another started (client insta mine, server disagreed)"));
                }
                this.isDestroyingBlock = true;
                this.destroyPos = blockPos.toImmutable();
                int n2 = (int)(f * 10.0f);
                this.world.sendBlockBreakProgress(this.player.getEntityId(), blockPos, n2);
                this.player.connection.sendPacket(new SPlayerDiggingPacket(blockPos, this.world.getBlockState(blockPos), action, true, "actual start of destroying"));
                this.durabilityRemainingOnBlock = n2;
            }
        } else if (action == CPlayerDiggingPacket.Action.STOP_DESTROY_BLOCK) {
            if (blockPos.equals(this.destroyPos)) {
                int n3 = this.ticks - this.initialDamage;
                BlockState blockState = this.world.getBlockState(blockPos);
                if (!blockState.isAir()) {
                    float f = blockState.getPlayerRelativeBlockHardness(this.player, this.player.world, blockPos) * (float)(n3 + 1);
                    if (f >= 0.7f) {
                        this.isDestroyingBlock = false;
                        this.world.sendBlockBreakProgress(this.player.getEntityId(), blockPos, -1);
                        this.func_229860_a_(blockPos, action, "destroyed");
                        return;
                    }
                    if (!this.receivedFinishDiggingPacket) {
                        this.isDestroyingBlock = false;
                        this.receivedFinishDiggingPacket = true;
                        this.delayedDestroyPos = blockPos;
                        this.initialBlockDamage = this.initialDamage;
                    }
                }
            }
            this.player.connection.sendPacket(new SPlayerDiggingPacket(blockPos, this.world.getBlockState(blockPos), action, true, "stopped destroying"));
        } else if (action == CPlayerDiggingPacket.Action.ABORT_DESTROY_BLOCK) {
            this.isDestroyingBlock = false;
            if (!Objects.equals(this.destroyPos, blockPos)) {
                field_225418_c.warn("Mismatch in destroy block pos: " + this.destroyPos + " " + blockPos);
                this.world.sendBlockBreakProgress(this.player.getEntityId(), this.destroyPos, -1);
                this.player.connection.sendPacket(new SPlayerDiggingPacket(this.destroyPos, this.world.getBlockState(this.destroyPos), action, true, "aborted mismatched destroying"));
            }
            this.world.sendBlockBreakProgress(this.player.getEntityId(), blockPos, -1);
            this.player.connection.sendPacket(new SPlayerDiggingPacket(blockPos, this.world.getBlockState(blockPos), action, true, "aborted destroying"));
        }
    }

    public void func_229860_a_(BlockPos blockPos, CPlayerDiggingPacket.Action action, String string) {
        if (this.tryHarvestBlock(blockPos)) {
            this.player.connection.sendPacket(new SPlayerDiggingPacket(blockPos, this.world.getBlockState(blockPos), action, true, string));
        } else {
            this.player.connection.sendPacket(new SPlayerDiggingPacket(blockPos, this.world.getBlockState(blockPos), action, false, string));
        }
    }

    public boolean tryHarvestBlock(BlockPos blockPos) {
        BlockState blockState = this.world.getBlockState(blockPos);
        if (!this.player.getHeldItemMainhand().getItem().canPlayerBreakBlockWhileHolding(blockState, this.world, blockPos, this.player)) {
            return true;
        }
        TileEntity tileEntity = this.world.getTileEntity(blockPos);
        Block block = blockState.getBlock();
        if ((block instanceof CommandBlockBlock || block instanceof StructureBlock || block instanceof JigsawBlock) && !this.player.canUseCommandBlock()) {
            this.world.notifyBlockUpdate(blockPos, blockState, blockState, 3);
            return true;
        }
        if (this.player.blockActionRestricted(this.world, blockPos, this.gameType)) {
            return true;
        }
        block.onBlockHarvested(this.world, blockPos, blockState, this.player);
        boolean bl = this.world.removeBlock(blockPos, true);
        if (bl) {
            block.onPlayerDestroy(this.world, blockPos, blockState);
        }
        if (this.isCreative()) {
            return false;
        }
        ItemStack itemStack = this.player.getHeldItemMainhand();
        ItemStack itemStack2 = itemStack.copy();
        boolean bl2 = this.player.func_234569_d_(blockState);
        itemStack.onBlockDestroyed(this.world, blockState, blockPos, this.player);
        if (bl && bl2) {
            block.harvestBlock(this.world, this.player, blockPos, blockState, tileEntity, itemStack2);
        }
        return false;
    }

    public ActionResultType processRightClick(ServerPlayerEntity serverPlayerEntity, World world, ItemStack itemStack, Hand hand) {
        if (this.gameType == GameType.SPECTATOR) {
            return ActionResultType.PASS;
        }
        if (serverPlayerEntity.getCooldownTracker().hasCooldown(itemStack.getItem())) {
            return ActionResultType.PASS;
        }
        int n = itemStack.getCount();
        int n2 = itemStack.getDamage();
        ActionResult<ItemStack> actionResult = itemStack.useItemRightClick(world, serverPlayerEntity, hand);
        ItemStack itemStack2 = actionResult.getResult();
        if (itemStack2 == itemStack && itemStack2.getCount() == n && itemStack2.getUseDuration() <= 0 && itemStack2.getDamage() == n2) {
            return actionResult.getType();
        }
        if (actionResult.getType() == ActionResultType.FAIL && itemStack2.getUseDuration() > 0 && !serverPlayerEntity.isHandActive()) {
            return actionResult.getType();
        }
        serverPlayerEntity.setHeldItem(hand, itemStack2);
        if (this.isCreative()) {
            itemStack2.setCount(n);
            if (itemStack2.isDamageable() && itemStack2.getDamage() != n2) {
                itemStack2.setDamage(n2);
            }
        }
        if (itemStack2.isEmpty()) {
            serverPlayerEntity.setHeldItem(hand, ItemStack.EMPTY);
        }
        if (!serverPlayerEntity.isHandActive()) {
            serverPlayerEntity.sendContainerToPlayer(serverPlayerEntity.container);
        }
        return actionResult.getType();
    }

    public ActionResultType func_219441_a(ServerPlayerEntity serverPlayerEntity, World world, ItemStack itemStack, Hand hand, BlockRayTraceResult blockRayTraceResult) {
        Object object;
        BlockPos blockPos = blockRayTraceResult.getPos();
        BlockState blockState = world.getBlockState(blockPos);
        if (this.gameType == GameType.SPECTATOR) {
            INamedContainerProvider iNamedContainerProvider = blockState.getContainer(world, blockPos);
            if (iNamedContainerProvider != null) {
                serverPlayerEntity.openContainer(iNamedContainerProvider);
                return ActionResultType.SUCCESS;
            }
            return ActionResultType.PASS;
        }
        boolean bl = !serverPlayerEntity.getHeldItemMainhand().isEmpty() || !serverPlayerEntity.getHeldItemOffhand().isEmpty();
        boolean bl2 = serverPlayerEntity.isSecondaryUseActive() && bl;
        ItemStack itemStack2 = itemStack.copy();
        if (!bl2 && (object = blockState.onBlockActivated(world, serverPlayerEntity, hand, blockRayTraceResult)).isSuccessOrConsume()) {
            CriteriaTriggers.RIGHT_CLICK_BLOCK_WITH_ITEM.test(serverPlayerEntity, blockPos, itemStack2);
            return object;
        }
        if (!itemStack.isEmpty() && !serverPlayerEntity.getCooldownTracker().hasCooldown(itemStack.getItem())) {
            ActionResultType actionResultType;
            object = new ItemUseContext(serverPlayerEntity, hand, blockRayTraceResult);
            if (this.isCreative()) {
                int n = itemStack.getCount();
                actionResultType = itemStack.onItemUse((ItemUseContext)object);
                itemStack.setCount(n);
            } else {
                actionResultType = itemStack.onItemUse((ItemUseContext)object);
            }
            if (actionResultType.isSuccessOrConsume()) {
                CriteriaTriggers.RIGHT_CLICK_BLOCK_WITH_ITEM.test(serverPlayerEntity, blockPos, itemStack2);
            }
            return actionResultType;
        }
        return ActionResultType.PASS;
    }

    public void setWorld(ServerWorld serverWorld) {
        this.world = serverWorld;
    }
}

