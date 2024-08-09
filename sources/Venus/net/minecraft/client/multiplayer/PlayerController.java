/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.multiplayer;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import mpp.venusfr.events.AttackEvent;
import mpp.venusfr.functions.api.FunctionRegistry;
import mpp.venusfr.functions.impl.combat.KillAura;
import mpp.venusfr.functions.impl.player.NoInteract;
import mpp.venusfr.venusfr;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CommandBlockBlock;
import net.minecraft.block.JigsawBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.StructureBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.client.util.ClientRecipeBook;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.network.play.client.CClickWindowPacket;
import net.minecraft.network.play.client.CCreativeInventoryActionPacket;
import net.minecraft.network.play.client.CEnchantItemPacket;
import net.minecraft.network.play.client.CEntityActionPacket;
import net.minecraft.network.play.client.CHeldItemChangePacket;
import net.minecraft.network.play.client.CPickItemPacket;
import net.minecraft.network.play.client.CPlaceRecipePacket;
import net.minecraft.network.play.client.CPlayerDiggingPacket;
import net.minecraft.network.play.client.CPlayerTryUseItemOnBlockPacket;
import net.minecraft.network.play.client.CPlayerTryUseItemPacket;
import net.minecraft.network.play.client.CUseEntityPacket;
import net.minecraft.stats.StatisticsManager;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.GameType;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PlayerController {
    private static final Logger LOGGER = LogManager.getLogger();
    private final Minecraft mc;
    private final ClientPlayNetHandler connection;
    private BlockPos currentBlock = new BlockPos(-1, -1, -1);
    private ItemStack currentItemHittingBlock = ItemStack.EMPTY;
    private float curBlockDamageMP;
    private float stepSoundTickCounter;
    private int blockHitDelay;
    private boolean isHittingBlock;
    private GameType currentGameType = GameType.SURVIVAL;
    private GameType field_239166_k_ = GameType.NOT_SET;
    private final Object2ObjectLinkedOpenHashMap<Pair<BlockPos, CPlayerDiggingPacket.Action>, Vector3d> unacknowledgedDiggingPackets = new Object2ObjectLinkedOpenHashMap();
    private int currentPlayerItem;
    private final AttackEvent event = new AttackEvent(null);

    public PlayerController(Minecraft minecraft, ClientPlayNetHandler clientPlayNetHandler) {
        this.mc = minecraft;
        this.connection = clientPlayNetHandler;
    }

    public void setPlayerCapabilities(PlayerEntity playerEntity) {
        this.currentGameType.configurePlayerCapabilities(playerEntity.abilities);
    }

    public void func_241675_a_(GameType gameType) {
        this.field_239166_k_ = gameType;
    }

    public void setGameType(GameType gameType) {
        if (gameType != this.currentGameType) {
            this.field_239166_k_ = this.currentGameType;
        }
        this.currentGameType = gameType;
        this.currentGameType.configurePlayerCapabilities(this.mc.player.abilities);
    }

    public boolean shouldDrawHUD() {
        return this.currentGameType.isSurvivalOrAdventure();
    }

    public boolean onPlayerDestroyBlock(BlockPos blockPos) {
        if (this.mc.player.blockActionRestricted(this.mc.world, blockPos, this.currentGameType)) {
            return true;
        }
        ClientWorld clientWorld = this.mc.world;
        BlockState blockState = clientWorld.getBlockState(blockPos);
        if (!this.mc.player.getHeldItemMainhand().getItem().canPlayerBreakBlockWhileHolding(blockState, clientWorld, blockPos, this.mc.player)) {
            return true;
        }
        Block block = blockState.getBlock();
        if ((block instanceof CommandBlockBlock || block instanceof StructureBlock || block instanceof JigsawBlock) && !this.mc.player.canUseCommandBlock()) {
            return true;
        }
        if (blockState.isAir()) {
            return true;
        }
        block.onBlockHarvested(clientWorld, blockPos, blockState, this.mc.player);
        FluidState fluidState = clientWorld.getFluidState(blockPos);
        boolean bl = ((World)clientWorld).setBlockState(blockPos, fluidState.getBlockState(), 0);
        if (bl) {
            block.onPlayerDestroy(clientWorld, blockPos, blockState);
        }
        return bl;
    }

    public boolean clickBlock(BlockPos blockPos, Direction direction) {
        if (this.mc.player.blockActionRestricted(this.mc.world, blockPos, this.currentGameType)) {
            return true;
        }
        if (!this.mc.world.getWorldBorder().contains(blockPos)) {
            return true;
        }
        if (this.currentGameType.isCreative()) {
            BlockState blockState = this.mc.world.getBlockState(blockPos);
            this.mc.getTutorial().onHitBlock(this.mc.world, blockPos, blockState, 1.0f);
            this.sendDiggingPacket(CPlayerDiggingPacket.Action.START_DESTROY_BLOCK, blockPos, direction);
            this.onPlayerDestroyBlock(blockPos);
            this.blockHitDelay = 5;
        } else if (!this.isHittingBlock || !this.isHittingPosition(blockPos)) {
            boolean bl;
            if (this.isHittingBlock) {
                this.sendDiggingPacket(CPlayerDiggingPacket.Action.ABORT_DESTROY_BLOCK, this.currentBlock, direction);
            }
            BlockState blockState = this.mc.world.getBlockState(blockPos);
            this.mc.getTutorial().onHitBlock(this.mc.world, blockPos, blockState, 0.0f);
            this.sendDiggingPacket(CPlayerDiggingPacket.Action.START_DESTROY_BLOCK, blockPos, direction);
            boolean bl2 = bl = !blockState.isAir();
            if (bl && this.curBlockDamageMP == 0.0f) {
                blockState.onBlockClicked(this.mc.world, blockPos, this.mc.player);
            }
            if (bl && blockState.getPlayerRelativeBlockHardness(this.mc.player, this.mc.player.world, blockPos) >= 1.0f) {
                this.onPlayerDestroyBlock(blockPos);
            } else {
                this.isHittingBlock = true;
                this.currentBlock = blockPos;
                this.currentItemHittingBlock = this.mc.player.getHeldItemMainhand();
                this.curBlockDamageMP = 0.0f;
                this.stepSoundTickCounter = 0.0f;
                this.mc.world.sendBlockBreakProgress(this.mc.player.getEntityId(), this.currentBlock, (int)(this.curBlockDamageMP * 10.0f) - 1);
            }
        }
        return false;
    }

    public void resetBlockRemoving() {
        if (this.isHittingBlock) {
            BlockState blockState = this.mc.world.getBlockState(this.currentBlock);
            this.mc.getTutorial().onHitBlock(this.mc.world, this.currentBlock, blockState, -1.0f);
            this.sendDiggingPacket(CPlayerDiggingPacket.Action.ABORT_DESTROY_BLOCK, this.currentBlock, Direction.DOWN);
            this.isHittingBlock = false;
            this.curBlockDamageMP = 0.0f;
            this.mc.world.sendBlockBreakProgress(this.mc.player.getEntityId(), this.currentBlock, -1);
            this.mc.player.resetCooldown();
        }
    }

    public boolean onPlayerDamageBlock(BlockPos blockPos, Direction direction) {
        this.syncCurrentPlayItem();
        if (this.blockHitDelay > 0) {
            --this.blockHitDelay;
            return false;
        }
        if (this.currentGameType.isCreative() && this.mc.world.getWorldBorder().contains(blockPos)) {
            this.blockHitDelay = 5;
            BlockState blockState = this.mc.world.getBlockState(blockPos);
            this.mc.getTutorial().onHitBlock(this.mc.world, blockPos, blockState, 1.0f);
            this.sendDiggingPacket(CPlayerDiggingPacket.Action.START_DESTROY_BLOCK, blockPos, direction);
            this.onPlayerDestroyBlock(blockPos);
            return false;
        }
        if (this.isHittingPosition(blockPos)) {
            BlockState blockState = this.mc.world.getBlockState(blockPos);
            if (blockState.isAir()) {
                this.isHittingBlock = false;
                return true;
            }
            this.curBlockDamageMP += blockState.getPlayerRelativeBlockHardness(this.mc.player, this.mc.player.world, blockPos);
            if (this.stepSoundTickCounter % 4.0f == 0.0f) {
                SoundType soundType = blockState.getSoundType();
                this.mc.getSoundHandler().play(new SimpleSound(soundType.getHitSound(), SoundCategory.BLOCKS, (soundType.getVolume() + 1.0f) / 8.0f, soundType.getPitch() * 0.5f, blockPos));
            }
            this.stepSoundTickCounter += 1.0f;
            this.mc.getTutorial().onHitBlock(this.mc.world, blockPos, blockState, MathHelper.clamp(this.curBlockDamageMP, 0.0f, 1.0f));
            if (this.curBlockDamageMP >= 1.0f) {
                this.isHittingBlock = false;
                this.sendDiggingPacket(CPlayerDiggingPacket.Action.STOP_DESTROY_BLOCK, blockPos, direction);
                this.onPlayerDestroyBlock(blockPos);
                this.curBlockDamageMP = 0.0f;
                this.stepSoundTickCounter = 0.0f;
                this.blockHitDelay = 5;
            }
            this.mc.world.sendBlockBreakProgress(this.mc.player.getEntityId(), this.currentBlock, (int)(this.curBlockDamageMP * 10.0f) - 1);
            return false;
        }
        return this.clickBlock(blockPos, direction);
    }

    public float getBlockReachDistance() {
        return this.currentGameType.isCreative() ? 5.0f : 4.5f;
    }

    public void tick() {
        this.syncCurrentPlayItem();
        if (this.connection.getNetworkManager().isChannelOpen()) {
            this.connection.getNetworkManager().tick();
        } else {
            this.connection.getNetworkManager().handleDisconnection();
        }
    }

    private boolean isHittingPosition(BlockPos blockPos) {
        boolean bl;
        ItemStack itemStack = this.mc.player.getHeldItemMainhand();
        boolean bl2 = bl = this.currentItemHittingBlock.isEmpty() && itemStack.isEmpty();
        if (!this.currentItemHittingBlock.isEmpty() && !itemStack.isEmpty()) {
            bl = itemStack.getItem() == this.currentItemHittingBlock.getItem() && ItemStack.areItemStackTagsEqual(itemStack, this.currentItemHittingBlock) && (itemStack.isDamageable() || itemStack.getDamage() == this.currentItemHittingBlock.getDamage());
        }
        return blockPos.equals(this.currentBlock) && bl;
    }

    public void syncCurrentPlayItem() {
        int n = this.mc.player.inventory.currentItem;
        if (n != this.currentPlayerItem) {
            this.currentPlayerItem = n;
            this.connection.sendPacket(new CHeldItemChangePacket(this.currentPlayerItem));
        }
    }

    public ActionResultType processRightClickBlock(ClientPlayerEntity clientPlayerEntity, ClientWorld clientWorld, Hand hand, BlockRayTraceResult blockRayTraceResult) {
        Object object;
        boolean bl;
        this.syncCurrentPlayItem();
        BlockPos blockPos = blockRayTraceResult.getPos();
        if (!this.mc.world.getWorldBorder().contains(blockPos)) {
            return ActionResultType.FAIL;
        }
        ItemStack itemStack = clientPlayerEntity.getHeldItem(hand);
        if (this.currentGameType == GameType.SPECTATOR) {
            this.connection.sendPacket(new CPlayerTryUseItemOnBlockPacket(hand, blockRayTraceResult));
            return ActionResultType.SUCCESS;
        }
        FunctionRegistry functionRegistry = venusfr.getInstance().getFunctionRegistry();
        KillAura killAura = functionRegistry.getKillAura();
        NoInteract noInteract = functionRegistry.getNoInteract();
        boolean bl2 = !clientPlayerEntity.getHeldItemMainhand().isEmpty() || !clientPlayerEntity.getHeldItemOffhand().isEmpty();
        boolean bl3 = clientPlayerEntity.isSecondaryUseActive() && bl2;
        boolean bl4 = bl = noInteract.isState() && (Boolean)noInteract.allBlocks.get() != false || noInteract.isState() && noInteract.getBlocks().contains(clientWorld.getBlockState(blockRayTraceResult.getPos()).getBlockId()) || killAura.getTarget() != null;
        if (!bl) {
            if (!bl3 && (object = clientWorld.getBlockState(blockPos).onBlockActivated(clientWorld, clientPlayerEntity, hand, blockRayTraceResult)).isSuccessOrConsume()) {
                this.connection.sendPacket(new CPlayerTryUseItemOnBlockPacket(hand, blockRayTraceResult));
                return object;
            }
            this.connection.sendPacket(new CPlayerTryUseItemOnBlockPacket(hand, blockRayTraceResult));
        }
        if (!itemStack.isEmpty() && !clientPlayerEntity.getCooldownTracker().hasCooldown(itemStack.getItem())) {
            ActionResultType actionResultType;
            object = new ItemUseContext(clientPlayerEntity, hand, blockRayTraceResult);
            if (this.currentGameType.isCreative()) {
                int n = itemStack.getCount();
                actionResultType = itemStack.onItemUse((ItemUseContext)object);
                itemStack.setCount(n);
            } else {
                actionResultType = itemStack.onItemUse((ItemUseContext)object);
            }
            return actionResultType;
        }
        return ActionResultType.PASS;
    }

    public ActionResultType processRightClick(PlayerEntity playerEntity, World world, Hand hand) {
        if (this.currentGameType == GameType.SPECTATOR) {
            return ActionResultType.PASS;
        }
        this.syncCurrentPlayItem();
        this.connection.sendPacket(new CPlayerTryUseItemPacket(hand));
        ItemStack itemStack = playerEntity.getHeldItem(hand);
        if (playerEntity.getCooldownTracker().hasCooldown(itemStack.getItem())) {
            return ActionResultType.PASS;
        }
        int n = itemStack.getCount();
        ActionResult<ItemStack> actionResult = itemStack.useItemRightClick(world, playerEntity, hand);
        ItemStack itemStack2 = actionResult.getResult();
        if (itemStack2 != itemStack) {
            playerEntity.setHeldItem(hand, itemStack2);
        }
        return actionResult.getType();
    }

    public ClientPlayerEntity createPlayer(ClientWorld clientWorld, StatisticsManager statisticsManager, ClientRecipeBook clientRecipeBook) {
        return this.func_239167_a_(clientWorld, statisticsManager, clientRecipeBook, false, true);
    }

    public ClientPlayerEntity func_239167_a_(ClientWorld clientWorld, StatisticsManager statisticsManager, ClientRecipeBook clientRecipeBook, boolean bl, boolean bl2) {
        return new ClientPlayerEntity(this.mc, clientWorld, this.connection, statisticsManager, clientRecipeBook, bl, bl2);
    }

    public void attackEntity(PlayerEntity playerEntity, Entity entity2) {
        this.event.entity = entity2;
        venusfr.getInstance().getEventBus().post(this.event);
        this.syncCurrentPlayItem();
        if (this.mc.player.serverSprintState && !this.mc.player.isInWater()) {
            this.mc.player.connection.sendPacket(new CEntityActionPacket(this.mc.player, CEntityActionPacket.Action.STOP_SPRINTING));
        }
        this.connection.sendPacket(new CUseEntityPacket(entity2, playerEntity.isSneaking()));
        if (this.currentGameType != GameType.SPECTATOR) {
            playerEntity.attackTargetEntityWithCurrentItem(entity2);
            playerEntity.resetCooldown();
        }
        if (this.mc.player.serverSprintState && !this.mc.player.isInWater()) {
            this.mc.player.connection.sendPacket(new CEntityActionPacket(this.mc.player, CEntityActionPacket.Action.START_SPRINTING));
        }
    }

    public ActionResultType interactWithEntity(PlayerEntity playerEntity, Entity entity2, Hand hand) {
        this.syncCurrentPlayItem();
        this.connection.sendPacket(new CUseEntityPacket(entity2, hand, playerEntity.isSneaking()));
        return this.currentGameType == GameType.SPECTATOR ? ActionResultType.PASS : playerEntity.interactOn(entity2, hand);
    }

    public ActionResultType interactWithEntity(PlayerEntity playerEntity, Entity entity2, EntityRayTraceResult entityRayTraceResult, Hand hand) {
        this.syncCurrentPlayItem();
        Vector3d vector3d = entityRayTraceResult.getHitVec().subtract(entity2.getPosX(), entity2.getPosY(), entity2.getPosZ());
        this.connection.sendPacket(new CUseEntityPacket(entity2, hand, vector3d, playerEntity.isSneaking()));
        return this.currentGameType == GameType.SPECTATOR ? ActionResultType.PASS : entity2.applyPlayerInteraction(playerEntity, vector3d, hand);
    }

    public ItemStack windowClick(int n, int n2, int n3, ClickType clickType, PlayerEntity playerEntity) {
        System.out.println(n2);
        short s = playerEntity.openContainer.getNextTransactionID(playerEntity.inventory);
        ItemStack itemStack = playerEntity.openContainer.slotClick(n2, n3, clickType, playerEntity);
        System.out.println(itemStack.getTag());
        this.connection.sendPacket(new CClickWindowPacket(n, n2, n3, clickType, itemStack, s));
        return itemStack;
    }

    public void windowClickFixed(int n, int n2, int n3, ClickType clickType, PlayerEntity playerEntity, int n4) {
        this.mc.player.windowClickMemory.add(new ClientPlayerEntity.WindowClickMemory(n, n2, n3, clickType, playerEntity, n4));
    }

    public void sendPlaceRecipePacket(int n, IRecipe<?> iRecipe, boolean bl) {
        this.connection.sendPacket(new CPlaceRecipePacket(n, iRecipe, bl));
    }

    public void sendEnchantPacket(int n, int n2) {
        this.connection.sendPacket(new CEnchantItemPacket(n, n2));
    }

    public void sendSlotPacket(ItemStack itemStack, int n) {
        if (this.currentGameType.isCreative()) {
            this.connection.sendPacket(new CCreativeInventoryActionPacket(n, itemStack));
        }
    }

    public void sendPacketDropItem(ItemStack itemStack) {
        if (this.currentGameType.isCreative() && !itemStack.isEmpty()) {
            this.connection.sendPacket(new CCreativeInventoryActionPacket(-1, itemStack));
        }
    }

    public void onStoppedUsingItem(PlayerEntity playerEntity) {
        this.syncCurrentPlayItem();
        this.connection.sendPacket(new CPlayerDiggingPacket(CPlayerDiggingPacket.Action.RELEASE_USE_ITEM, BlockPos.ZERO, Direction.DOWN));
        playerEntity.stopActiveHand();
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
        return this.mc.player.isPassenger() && this.mc.player.getRidingEntity() instanceof AbstractHorseEntity;
    }

    public boolean isSpectatorMode() {
        return this.currentGameType == GameType.SPECTATOR;
    }

    public GameType func_241822_k() {
        return this.field_239166_k_;
    }

    public GameType getCurrentGameType() {
        return this.currentGameType;
    }

    public boolean getIsHittingBlock() {
        return this.isHittingBlock;
    }

    public void pickItem(int n) {
        this.connection.sendPacket(new CPickItemPacket(n));
    }

    private void sendDiggingPacket(CPlayerDiggingPacket.Action action, BlockPos blockPos, Direction direction) {
        ClientPlayerEntity clientPlayerEntity = this.mc.player;
        this.unacknowledgedDiggingPackets.put(Pair.of(blockPos, action), clientPlayerEntity.getPositionVec());
        this.connection.sendPacket(new CPlayerDiggingPacket(action, blockPos, direction));
    }

    public void acknowledgePlayerDiggingReceived(ClientWorld clientWorld, BlockPos blockPos, BlockState blockState, CPlayerDiggingPacket.Action action, boolean bl) {
        Object object;
        Vector3d vector3d = this.unacknowledgedDiggingPackets.remove(Pair.of(blockPos, action));
        BlockState blockState2 = clientWorld.getBlockState(blockPos);
        if ((vector3d == null || !bl || action != CPlayerDiggingPacket.Action.START_DESTROY_BLOCK && blockState2 != blockState) && blockState2 != blockState) {
            clientWorld.invalidateRegionAndSetBlock(blockPos, blockState);
            object = this.mc.player;
            if (vector3d != null && clientWorld == ((PlayerEntity)object).world && ((Entity)object).func_242278_a(blockPos, blockState)) {
                ((Entity)object).func_242281_f(vector3d.x, vector3d.y, vector3d.z);
            }
        }
        while (this.unacknowledgedDiggingPackets.size() >= 50) {
            object = this.unacknowledgedDiggingPackets.firstKey();
            this.unacknowledgedDiggingPackets.removeFirst();
            LOGGER.error("Too many unacked block actions, dropping " + (Pair)object);
        }
    }
}

