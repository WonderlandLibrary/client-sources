/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play;

import com.google.common.collect.Lists;
import com.google.common.primitives.Doubles;
import com.google.common.primitives.Floats;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.suggestion.Suggestions;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import it.unimi.dsi.fastutil.ints.Int2ShortMap;
import it.unimi.dsi.fastutil.ints.Int2ShortOpenHashMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CommandBlockBlock;
import net.minecraft.command.CommandSource;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ReportedException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IJumpingMount;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.player.ChatVisibility;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.container.BeaconContainer;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.MerchantContainer;
import net.minecraft.inventory.container.RecipeBookContainer;
import net.minecraft.inventory.container.RepairContainer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.WritableBookItem;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.PacketThreadUtil;
import net.minecraft.network.play.IServerPlayNetHandler;
import net.minecraft.network.play.client.CAnimateHandPacket;
import net.minecraft.network.play.client.CChatMessagePacket;
import net.minecraft.network.play.client.CClickWindowPacket;
import net.minecraft.network.play.client.CClientSettingsPacket;
import net.minecraft.network.play.client.CClientStatusPacket;
import net.minecraft.network.play.client.CCloseWindowPacket;
import net.minecraft.network.play.client.CConfirmTeleportPacket;
import net.minecraft.network.play.client.CConfirmTransactionPacket;
import net.minecraft.network.play.client.CCreativeInventoryActionPacket;
import net.minecraft.network.play.client.CCustomPayloadPacket;
import net.minecraft.network.play.client.CEditBookPacket;
import net.minecraft.network.play.client.CEnchantItemPacket;
import net.minecraft.network.play.client.CEntityActionPacket;
import net.minecraft.network.play.client.CHeldItemChangePacket;
import net.minecraft.network.play.client.CInputPacket;
import net.minecraft.network.play.client.CJigsawBlockGeneratePacket;
import net.minecraft.network.play.client.CKeepAlivePacket;
import net.minecraft.network.play.client.CLockDifficultyPacket;
import net.minecraft.network.play.client.CMarkRecipeSeenPacket;
import net.minecraft.network.play.client.CMoveVehiclePacket;
import net.minecraft.network.play.client.CPickItemPacket;
import net.minecraft.network.play.client.CPlaceRecipePacket;
import net.minecraft.network.play.client.CPlayerAbilitiesPacket;
import net.minecraft.network.play.client.CPlayerDiggingPacket;
import net.minecraft.network.play.client.CPlayerPacket;
import net.minecraft.network.play.client.CPlayerTryUseItemOnBlockPacket;
import net.minecraft.network.play.client.CPlayerTryUseItemPacket;
import net.minecraft.network.play.client.CQueryEntityNBTPacket;
import net.minecraft.network.play.client.CQueryTileEntityNBTPacket;
import net.minecraft.network.play.client.CRenameItemPacket;
import net.minecraft.network.play.client.CResourcePackStatusPacket;
import net.minecraft.network.play.client.CSeenAdvancementsPacket;
import net.minecraft.network.play.client.CSelectTradePacket;
import net.minecraft.network.play.client.CSetDifficultyPacket;
import net.minecraft.network.play.client.CSpectatePacket;
import net.minecraft.network.play.client.CSteerBoatPacket;
import net.minecraft.network.play.client.CTabCompletePacket;
import net.minecraft.network.play.client.CUpdateBeaconPacket;
import net.minecraft.network.play.client.CUpdateCommandBlockPacket;
import net.minecraft.network.play.client.CUpdateJigsawBlockPacket;
import net.minecraft.network.play.client.CUpdateMinecartCommandBlockPacket;
import net.minecraft.network.play.client.CUpdateRecipeBookStatusPacket;
import net.minecraft.network.play.client.CUpdateSignPacket;
import net.minecraft.network.play.client.CUpdateStructureBlockPacket;
import net.minecraft.network.play.client.CUseEntityPacket;
import net.minecraft.network.play.server.SChangeBlockPacket;
import net.minecraft.network.play.server.SChatPacket;
import net.minecraft.network.play.server.SConfirmTransactionPacket;
import net.minecraft.network.play.server.SDisconnectPacket;
import net.minecraft.network.play.server.SHeldItemChangePacket;
import net.minecraft.network.play.server.SKeepAlivePacket;
import net.minecraft.network.play.server.SMoveVehiclePacket;
import net.minecraft.network.play.server.SPlayerPositionLookPacket;
import net.minecraft.network.play.server.SQueryNBTResponsePacket;
import net.minecraft.network.play.server.SSetSlotPacket;
import net.minecraft.network.play.server.STabCompletePacket;
import net.minecraft.potion.Effects;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.CommandBlockLogic;
import net.minecraft.tileentity.CommandBlockTileEntity;
import net.minecraft.tileentity.JigsawTileEntity;
import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.tileentity.StructureBlockTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.StringUtils;
import net.minecraft.util.Util;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.util.text.filter.IChatFilter;
import net.minecraft.world.GameRules;
import net.minecraft.world.GameType;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerPlayNetHandler
implements IServerPlayNetHandler {
    private static final Logger LOGGER = LogManager.getLogger();
    public final NetworkManager netManager;
    private final MinecraftServer server;
    public ServerPlayerEntity player;
    private int networkTickCount;
    private long keepAliveTime;
    private boolean keepAlivePending;
    private long keepAliveKey;
    private int chatSpamThresholdCount;
    private int itemDropThreshold;
    private final Int2ShortMap pendingTransactions = new Int2ShortOpenHashMap();
    private double firstGoodX;
    private double firstGoodY;
    private double firstGoodZ;
    private double lastGoodX;
    private double lastGoodY;
    private double lastGoodZ;
    private Entity lowestRiddenEnt;
    private double lowestRiddenX;
    private double lowestRiddenY;
    private double lowestRiddenZ;
    private double lowestRiddenX1;
    private double lowestRiddenY1;
    private double lowestRiddenZ1;
    private Vector3d targetPos;
    private int teleportId;
    private int lastPositionUpdate;
    private boolean floating;
    private int floatingTickCount;
    private boolean vehicleFloating;
    private int vehicleFloatingTickCount;
    private int movePacketCounter;
    private int lastMovePacketCounter;

    public ServerPlayNetHandler(MinecraftServer minecraftServer, NetworkManager networkManager, ServerPlayerEntity serverPlayerEntity) {
        this.server = minecraftServer;
        this.netManager = networkManager;
        networkManager.setNetHandler(this);
        this.player = serverPlayerEntity;
        serverPlayerEntity.connection = this;
        IChatFilter iChatFilter = serverPlayerEntity.func_244529_Q();
        if (iChatFilter != null) {
            iChatFilter.func_244800_a();
        }
    }

    public void tick() {
        this.captureCurrentPosition();
        this.player.prevPosX = this.player.getPosX();
        this.player.prevPosY = this.player.getPosY();
        this.player.prevPosZ = this.player.getPosZ();
        this.player.playerTick();
        this.player.setPositionAndRotation(this.firstGoodX, this.firstGoodY, this.firstGoodZ, this.player.rotationYaw, this.player.rotationPitch);
        ++this.networkTickCount;
        this.lastMovePacketCounter = this.movePacketCounter;
        if (this.floating && !this.player.isSleeping()) {
            if (++this.floatingTickCount > 80) {
                LOGGER.warn("{} was kicked for floating too long!", (Object)this.player.getName().getString());
                this.disconnect(new TranslationTextComponent("multiplayer.disconnect.flying"));
                return;
            }
        } else {
            this.floating = false;
            this.floatingTickCount = 0;
        }
        this.lowestRiddenEnt = this.player.getLowestRidingEntity();
        if (this.lowestRiddenEnt != this.player && this.lowestRiddenEnt.getControllingPassenger() == this.player) {
            this.lowestRiddenX = this.lowestRiddenEnt.getPosX();
            this.lowestRiddenY = this.lowestRiddenEnt.getPosY();
            this.lowestRiddenZ = this.lowestRiddenEnt.getPosZ();
            this.lowestRiddenX1 = this.lowestRiddenEnt.getPosX();
            this.lowestRiddenY1 = this.lowestRiddenEnt.getPosY();
            this.lowestRiddenZ1 = this.lowestRiddenEnt.getPosZ();
            if (this.vehicleFloating && this.player.getLowestRidingEntity().getControllingPassenger() == this.player) {
                if (++this.vehicleFloatingTickCount > 80) {
                    LOGGER.warn("{} was kicked for floating a vehicle too long!", (Object)this.player.getName().getString());
                    this.disconnect(new TranslationTextComponent("multiplayer.disconnect.flying"));
                    return;
                }
            } else {
                this.vehicleFloating = false;
                this.vehicleFloatingTickCount = 0;
            }
        } else {
            this.lowestRiddenEnt = null;
            this.vehicleFloating = false;
            this.vehicleFloatingTickCount = 0;
        }
        this.server.getProfiler().startSection("keepAlive");
        long l = Util.milliTime();
        if (l - this.keepAliveTime >= 15000L) {
            if (this.keepAlivePending) {
                this.disconnect(new TranslationTextComponent("disconnect.timeout"));
            } else {
                this.keepAlivePending = true;
                this.keepAliveTime = l;
                this.keepAliveKey = l;
                this.sendPacket(new SKeepAlivePacket(this.keepAliveKey));
            }
        }
        this.server.getProfiler().endSection();
        if (this.chatSpamThresholdCount > 0) {
            --this.chatSpamThresholdCount;
        }
        if (this.itemDropThreshold > 0) {
            --this.itemDropThreshold;
        }
        if (this.player.getLastActiveTime() > 0L && this.server.getMaxPlayerIdleMinutes() > 0 && Util.milliTime() - this.player.getLastActiveTime() > (long)(this.server.getMaxPlayerIdleMinutes() * 1000 * 60)) {
            this.disconnect(new TranslationTextComponent("multiplayer.disconnect.idling"));
        }
    }

    public void captureCurrentPosition() {
        this.firstGoodX = this.player.getPosX();
        this.firstGoodY = this.player.getPosY();
        this.firstGoodZ = this.player.getPosZ();
        this.lastGoodX = this.player.getPosX();
        this.lastGoodY = this.player.getPosY();
        this.lastGoodZ = this.player.getPosZ();
    }

    @Override
    public NetworkManager getNetworkManager() {
        return this.netManager;
    }

    private boolean func_217264_d() {
        return this.server.isServerOwner(this.player.getGameProfile());
    }

    public void disconnect(ITextComponent iTextComponent) {
        this.netManager.sendPacket(new SDisconnectPacket(iTextComponent), arg_0 -> this.lambda$disconnect$0(iTextComponent, arg_0));
        this.netManager.disableAutoRead();
        this.server.runImmediately(this.netManager::handleDisconnection);
    }

    private <T> void func_244533_a(T t, Consumer<T> consumer, BiFunction<IChatFilter, T, CompletableFuture<Optional<T>>> biFunction) {
        MinecraftServer minecraftServer = this.player.getServerWorld().getServer();
        Consumer<Object> consumer2 = arg_0 -> this.lambda$func_244533_a$1(consumer, arg_0);
        IChatFilter iChatFilter = this.player.func_244529_Q();
        if (iChatFilter != null) {
            biFunction.apply(iChatFilter, (IChatFilter)t).thenAcceptAsync(arg_0 -> ServerPlayNetHandler.lambda$func_244533_a$2(consumer2, arg_0), (Executor)minecraftServer);
        } else {
            minecraftServer.execute(() -> ServerPlayNetHandler.lambda$func_244533_a$3(consumer2, t));
        }
    }

    private void func_244535_a(String string, Consumer<String> consumer) {
        this.func_244533_a(string, consumer, IChatFilter::func_244432_a);
    }

    private void func_244537_a(List<String> list, Consumer<List<String>> consumer) {
        this.func_244533_a(list, consumer, IChatFilter::func_244433_a);
    }

    @Override
    public void processInput(CInputPacket cInputPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(cInputPacket, this, this.player.getServerWorld());
        this.player.setEntityActionState(cInputPacket.getStrafeSpeed(), cInputPacket.getForwardSpeed(), cInputPacket.isJumping(), cInputPacket.isSneaking());
    }

    private static boolean isMovePlayerPacketInvalid(CPlayerPacket cPlayerPacket) {
        if (Doubles.isFinite(cPlayerPacket.getX(0.0)) && Doubles.isFinite(cPlayerPacket.getY(0.0)) && Doubles.isFinite(cPlayerPacket.getZ(0.0)) && Floats.isFinite(cPlayerPacket.getPitch(0.0f)) && Floats.isFinite(cPlayerPacket.getYaw(0.0f))) {
            return Math.abs(cPlayerPacket.getX(0.0)) > 3.0E7 || Math.abs(cPlayerPacket.getY(0.0)) > 3.0E7 || Math.abs(cPlayerPacket.getZ(0.0)) > 3.0E7;
        }
        return false;
    }

    private static boolean isMoveVehiclePacketInvalid(CMoveVehiclePacket cMoveVehiclePacket) {
        return !Doubles.isFinite(cMoveVehiclePacket.getX()) || !Doubles.isFinite(cMoveVehiclePacket.getY()) || !Doubles.isFinite(cMoveVehiclePacket.getZ()) || !Floats.isFinite(cMoveVehiclePacket.getPitch()) || !Floats.isFinite(cMoveVehiclePacket.getYaw());
    }

    @Override
    public void processVehicleMove(CMoveVehiclePacket cMoveVehiclePacket) {
        PacketThreadUtil.checkThreadAndEnqueue(cMoveVehiclePacket, this, this.player.getServerWorld());
        if (ServerPlayNetHandler.isMoveVehiclePacketInvalid(cMoveVehiclePacket)) {
            this.disconnect(new TranslationTextComponent("multiplayer.disconnect.invalid_vehicle_movement"));
        } else {
            Entity entity2 = this.player.getLowestRidingEntity();
            if (entity2 != this.player && entity2.getControllingPassenger() == this.player && entity2 == this.lowestRiddenEnt) {
                ServerWorld serverWorld = this.player.getServerWorld();
                double d = entity2.getPosX();
                double d2 = entity2.getPosY();
                double d3 = entity2.getPosZ();
                double d4 = cMoveVehiclePacket.getX();
                double d5 = cMoveVehiclePacket.getY();
                double d6 = cMoveVehiclePacket.getZ();
                float f = cMoveVehiclePacket.getYaw();
                float f2 = cMoveVehiclePacket.getPitch();
                double d7 = d4 - this.lowestRiddenX;
                double d8 = d5 - this.lowestRiddenY;
                double d9 = d6 - this.lowestRiddenZ;
                double d10 = d7 * d7 + d8 * d8 + d9 * d9;
                double d11 = entity2.getMotion().lengthSquared();
                if (d10 - d11 > 100.0 && !this.func_217264_d()) {
                    LOGGER.warn("{} (vehicle of {}) moved too quickly! {},{},{}", (Object)entity2.getName().getString(), (Object)this.player.getName().getString(), (Object)d7, (Object)d8, (Object)d9);
                    this.netManager.sendPacket(new SMoveVehiclePacket(entity2));
                    return;
                }
                boolean bl = serverWorld.hasNoCollisions(entity2, entity2.getBoundingBox().shrink(0.0625));
                d7 = d4 - this.lowestRiddenX1;
                d8 = d5 - this.lowestRiddenY1 - 1.0E-6;
                d9 = d6 - this.lowestRiddenZ1;
                entity2.move(MoverType.PLAYER, new Vector3d(d7, d8, d9));
                d7 = d4 - entity2.getPosX();
                d8 = d5 - entity2.getPosY();
                if (d8 > -0.5 || d8 < 0.5) {
                    d8 = 0.0;
                }
                d9 = d6 - entity2.getPosZ();
                d10 = d7 * d7 + d8 * d8 + d9 * d9;
                boolean bl2 = false;
                if (d10 > 0.0625) {
                    bl2 = true;
                    LOGGER.warn("{} (vehicle of {}) moved wrongly! {}", (Object)entity2.getName().getString(), (Object)this.player.getName().getString(), (Object)Math.sqrt(d10));
                }
                entity2.setPositionAndRotation(d4, d5, d6, f, f2);
                boolean bl3 = serverWorld.hasNoCollisions(entity2, entity2.getBoundingBox().shrink(0.0625));
                if (bl && (bl2 || !bl3)) {
                    entity2.setPositionAndRotation(d, d2, d3, f, f2);
                    this.netManager.sendPacket(new SMoveVehiclePacket(entity2));
                    return;
                }
                this.player.getServerWorld().getChunkProvider().updatePlayerPosition(this.player);
                this.player.addMovementStat(this.player.getPosX() - d, this.player.getPosY() - d2, this.player.getPosZ() - d3);
                this.vehicleFloating = d8 >= -0.03125 && !this.server.isFlightAllowed() && this.func_241162_a_(entity2);
                this.lowestRiddenX1 = entity2.getPosX();
                this.lowestRiddenY1 = entity2.getPosY();
                this.lowestRiddenZ1 = entity2.getPosZ();
            }
        }
    }

    private boolean func_241162_a_(Entity entity2) {
        return entity2.world.func_234853_a_(entity2.getBoundingBox().grow(0.0625).expand(0.0, -0.55, 0.0)).allMatch(AbstractBlock.AbstractBlockState::isAir);
    }

    @Override
    public void processConfirmTeleport(CConfirmTeleportPacket cConfirmTeleportPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(cConfirmTeleportPacket, this, this.player.getServerWorld());
        if (cConfirmTeleportPacket.getTeleportId() == this.teleportId) {
            this.player.setPositionAndRotation(this.targetPos.x, this.targetPos.y, this.targetPos.z, this.player.rotationYaw, this.player.rotationPitch);
            this.lastGoodX = this.targetPos.x;
            this.lastGoodY = this.targetPos.y;
            this.lastGoodZ = this.targetPos.z;
            if (this.player.isInvulnerableDimensionChange()) {
                this.player.clearInvulnerableDimensionChange();
            }
            this.targetPos = null;
        }
    }

    @Override
    public void handleRecipeBookUpdate(CMarkRecipeSeenPacket cMarkRecipeSeenPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(cMarkRecipeSeenPacket, this, this.player.getServerWorld());
        this.server.getRecipeManager().getRecipe(cMarkRecipeSeenPacket.func_244321_b()).ifPresent(this.player.getRecipeBook()::markSeen);
    }

    @Override
    public void func_241831_a(CUpdateRecipeBookStatusPacket cUpdateRecipeBookStatusPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(cUpdateRecipeBookStatusPacket, this, this.player.getServerWorld());
        this.player.getRecipeBook().func_242144_a(cUpdateRecipeBookStatusPacket.func_244317_b(), cUpdateRecipeBookStatusPacket.func_244318_c(), cUpdateRecipeBookStatusPacket.func_244319_d());
    }

    @Override
    public void handleSeenAdvancements(CSeenAdvancementsPacket cSeenAdvancementsPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(cSeenAdvancementsPacket, this, this.player.getServerWorld());
        if (cSeenAdvancementsPacket.getAction() == CSeenAdvancementsPacket.Action.OPENED_TAB) {
            ResourceLocation resourceLocation = cSeenAdvancementsPacket.getTab();
            Advancement advancement = this.server.getAdvancementManager().getAdvancement(resourceLocation);
            if (advancement != null) {
                this.player.getAdvancements().setSelectedTab(advancement);
            }
        }
    }

    @Override
    public void processTabComplete(CTabCompletePacket cTabCompletePacket) {
        PacketThreadUtil.checkThreadAndEnqueue(cTabCompletePacket, this, this.player.getServerWorld());
        StringReader stringReader = new StringReader(cTabCompletePacket.getCommand());
        if (stringReader.canRead() && stringReader.peek() == '/') {
            stringReader.skip();
        }
        ParseResults<CommandSource> parseResults = this.server.getCommandManager().getDispatcher().parse(stringReader, this.player.getCommandSource());
        this.server.getCommandManager().getDispatcher().getCompletionSuggestions(parseResults).thenAccept(arg_0 -> this.lambda$processTabComplete$4(cTabCompletePacket, arg_0));
    }

    @Override
    public void processUpdateCommandBlock(CUpdateCommandBlockPacket cUpdateCommandBlockPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(cUpdateCommandBlockPacket, this, this.player.getServerWorld());
        if (!this.server.isCommandBlockEnabled()) {
            this.player.sendMessage(new TranslationTextComponent("advMode.notEnabled"), Util.DUMMY_UUID);
        } else if (!this.player.canUseCommandBlock()) {
            this.player.sendMessage(new TranslationTextComponent("advMode.notAllowed"), Util.DUMMY_UUID);
        } else {
            CommandBlockLogic commandBlockLogic = null;
            CommandBlockTileEntity commandBlockTileEntity = null;
            BlockPos blockPos = cUpdateCommandBlockPacket.getPos();
            TileEntity tileEntity = this.player.world.getTileEntity(blockPos);
            if (tileEntity instanceof CommandBlockTileEntity) {
                commandBlockTileEntity = (CommandBlockTileEntity)tileEntity;
                commandBlockLogic = commandBlockTileEntity.getCommandBlockLogic();
            }
            String string = cUpdateCommandBlockPacket.getCommand();
            boolean bl = cUpdateCommandBlockPacket.shouldTrackOutput();
            if (commandBlockLogic != null) {
                CommandBlockTileEntity.Mode mode = commandBlockTileEntity.getMode();
                Direction direction = this.player.world.getBlockState(blockPos).get(CommandBlockBlock.FACING);
                switch (cUpdateCommandBlockPacket.getMode()) {
                    case SEQUENCE: {
                        BlockState blockState = Blocks.CHAIN_COMMAND_BLOCK.getDefaultState();
                        this.player.world.setBlockState(blockPos, (BlockState)((BlockState)blockState.with(CommandBlockBlock.FACING, direction)).with(CommandBlockBlock.CONDITIONAL, cUpdateCommandBlockPacket.isConditional()), 1);
                        break;
                    }
                    case AUTO: {
                        BlockState blockState = Blocks.REPEATING_COMMAND_BLOCK.getDefaultState();
                        this.player.world.setBlockState(blockPos, (BlockState)((BlockState)blockState.with(CommandBlockBlock.FACING, direction)).with(CommandBlockBlock.CONDITIONAL, cUpdateCommandBlockPacket.isConditional()), 1);
                        break;
                    }
                    default: {
                        BlockState blockState = Blocks.COMMAND_BLOCK.getDefaultState();
                        this.player.world.setBlockState(blockPos, (BlockState)((BlockState)blockState.with(CommandBlockBlock.FACING, direction)).with(CommandBlockBlock.CONDITIONAL, cUpdateCommandBlockPacket.isConditional()), 1);
                    }
                }
                tileEntity.validate();
                this.player.world.setTileEntity(blockPos, tileEntity);
                commandBlockLogic.setCommand(string);
                commandBlockLogic.setTrackOutput(bl);
                if (!bl) {
                    commandBlockLogic.setLastOutput(null);
                }
                commandBlockTileEntity.setAuto(cUpdateCommandBlockPacket.isAuto());
                if (mode != cUpdateCommandBlockPacket.getMode()) {
                    commandBlockTileEntity.func_226987_h_();
                }
                commandBlockLogic.updateCommand();
                if (!StringUtils.isNullOrEmpty(string)) {
                    this.player.sendMessage(new TranslationTextComponent("advMode.setCommand.success", string), Util.DUMMY_UUID);
                }
            }
        }
    }

    @Override
    public void processUpdateCommandMinecart(CUpdateMinecartCommandBlockPacket cUpdateMinecartCommandBlockPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(cUpdateMinecartCommandBlockPacket, this, this.player.getServerWorld());
        if (!this.server.isCommandBlockEnabled()) {
            this.player.sendMessage(new TranslationTextComponent("advMode.notEnabled"), Util.DUMMY_UUID);
        } else if (!this.player.canUseCommandBlock()) {
            this.player.sendMessage(new TranslationTextComponent("advMode.notAllowed"), Util.DUMMY_UUID);
        } else {
            CommandBlockLogic commandBlockLogic = cUpdateMinecartCommandBlockPacket.getCommandBlock(this.player.world);
            if (commandBlockLogic != null) {
                commandBlockLogic.setCommand(cUpdateMinecartCommandBlockPacket.getCommand());
                commandBlockLogic.setTrackOutput(cUpdateMinecartCommandBlockPacket.shouldTrackOutput());
                if (!cUpdateMinecartCommandBlockPacket.shouldTrackOutput()) {
                    commandBlockLogic.setLastOutput(null);
                }
                commandBlockLogic.updateCommand();
                this.player.sendMessage(new TranslationTextComponent("advMode.setCommand.success", cUpdateMinecartCommandBlockPacket.getCommand()), Util.DUMMY_UUID);
            }
        }
    }

    @Override
    public void processPickItem(CPickItemPacket cPickItemPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(cPickItemPacket, this, this.player.getServerWorld());
        this.player.inventory.pickItem(cPickItemPacket.getPickIndex());
        this.player.connection.sendPacket(new SSetSlotPacket(-2, this.player.inventory.currentItem, this.player.inventory.getStackInSlot(this.player.inventory.currentItem)));
        this.player.connection.sendPacket(new SSetSlotPacket(-2, cPickItemPacket.getPickIndex(), this.player.inventory.getStackInSlot(cPickItemPacket.getPickIndex())));
        this.player.connection.sendPacket(new SHeldItemChangePacket(this.player.inventory.currentItem));
    }

    @Override
    public void processRenameItem(CRenameItemPacket cRenameItemPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(cRenameItemPacket, this, this.player.getServerWorld());
        if (this.player.openContainer instanceof RepairContainer) {
            RepairContainer repairContainer = (RepairContainer)this.player.openContainer;
            String string = SharedConstants.filterAllowedCharacters(cRenameItemPacket.getName());
            if (string.length() <= 35) {
                repairContainer.updateItemName(string);
            }
        }
    }

    @Override
    public void processUpdateBeacon(CUpdateBeaconPacket cUpdateBeaconPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(cUpdateBeaconPacket, this, this.player.getServerWorld());
        if (this.player.openContainer instanceof BeaconContainer) {
            ((BeaconContainer)this.player.openContainer).func_216966_c(cUpdateBeaconPacket.getPrimaryEffect(), cUpdateBeaconPacket.getSecondaryEffect());
        }
    }

    @Override
    public void processUpdateStructureBlock(CUpdateStructureBlockPacket cUpdateStructureBlockPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(cUpdateStructureBlockPacket, this, this.player.getServerWorld());
        if (this.player.canUseCommandBlock()) {
            BlockPos blockPos = cUpdateStructureBlockPacket.getPos();
            BlockState blockState = this.player.world.getBlockState(blockPos);
            TileEntity tileEntity = this.player.world.getTileEntity(blockPos);
            if (tileEntity instanceof StructureBlockTileEntity) {
                StructureBlockTileEntity structureBlockTileEntity = (StructureBlockTileEntity)tileEntity;
                structureBlockTileEntity.setMode(cUpdateStructureBlockPacket.getMode());
                structureBlockTileEntity.setName(cUpdateStructureBlockPacket.getName());
                structureBlockTileEntity.setPosition(cUpdateStructureBlockPacket.getPosition());
                structureBlockTileEntity.setSize(cUpdateStructureBlockPacket.getSize());
                structureBlockTileEntity.setMirror(cUpdateStructureBlockPacket.getMirror());
                structureBlockTileEntity.setRotation(cUpdateStructureBlockPacket.getRotation());
                structureBlockTileEntity.setMetadata(cUpdateStructureBlockPacket.getMetadata());
                structureBlockTileEntity.setIgnoresEntities(cUpdateStructureBlockPacket.shouldIgnoreEntities());
                structureBlockTileEntity.setShowAir(cUpdateStructureBlockPacket.shouldShowAir());
                structureBlockTileEntity.setShowBoundingBox(cUpdateStructureBlockPacket.shouldShowBoundingBox());
                structureBlockTileEntity.setIntegrity(cUpdateStructureBlockPacket.getIntegrity());
                structureBlockTileEntity.setSeed(cUpdateStructureBlockPacket.getSeed());
                if (structureBlockTileEntity.hasName()) {
                    String string = structureBlockTileEntity.getName();
                    if (cUpdateStructureBlockPacket.func_210384_b() == StructureBlockTileEntity.UpdateCommand.SAVE_AREA) {
                        if (structureBlockTileEntity.save()) {
                            this.player.sendStatusMessage(new TranslationTextComponent("structure_block.save_success", string), true);
                        } else {
                            this.player.sendStatusMessage(new TranslationTextComponent("structure_block.save_failure", string), true);
                        }
                    } else if (cUpdateStructureBlockPacket.func_210384_b() == StructureBlockTileEntity.UpdateCommand.LOAD_AREA) {
                        if (!structureBlockTileEntity.isStructureLoadable()) {
                            this.player.sendStatusMessage(new TranslationTextComponent("structure_block.load_not_found", string), true);
                        } else if (structureBlockTileEntity.func_242687_a(this.player.getServerWorld())) {
                            this.player.sendStatusMessage(new TranslationTextComponent("structure_block.load_success", string), true);
                        } else {
                            this.player.sendStatusMessage(new TranslationTextComponent("structure_block.load_prepare", string), true);
                        }
                    } else if (cUpdateStructureBlockPacket.func_210384_b() == StructureBlockTileEntity.UpdateCommand.SCAN_AREA) {
                        if (structureBlockTileEntity.detectSize()) {
                            this.player.sendStatusMessage(new TranslationTextComponent("structure_block.size_success", string), true);
                        } else {
                            this.player.sendStatusMessage(new TranslationTextComponent("structure_block.size_failure"), true);
                        }
                    }
                } else {
                    this.player.sendStatusMessage(new TranslationTextComponent("structure_block.invalid_structure_name", cUpdateStructureBlockPacket.getName()), true);
                }
                structureBlockTileEntity.markDirty();
                this.player.world.notifyBlockUpdate(blockPos, blockState, blockState, 3);
            }
        }
    }

    @Override
    public void func_217262_a(CUpdateJigsawBlockPacket cUpdateJigsawBlockPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(cUpdateJigsawBlockPacket, this, this.player.getServerWorld());
        if (this.player.canUseCommandBlock()) {
            BlockPos blockPos = cUpdateJigsawBlockPacket.func_218789_b();
            BlockState blockState = this.player.world.getBlockState(blockPos);
            TileEntity tileEntity = this.player.world.getTileEntity(blockPos);
            if (tileEntity instanceof JigsawTileEntity) {
                JigsawTileEntity jigsawTileEntity = (JigsawTileEntity)tileEntity;
                jigsawTileEntity.func_235664_a_(cUpdateJigsawBlockPacket.func_240851_c_());
                jigsawTileEntity.func_235666_b_(cUpdateJigsawBlockPacket.func_240852_d_());
                jigsawTileEntity.func_235667_c_(cUpdateJigsawBlockPacket.func_240853_e_());
                jigsawTileEntity.setFinalState(cUpdateJigsawBlockPacket.func_218788_e());
                jigsawTileEntity.func_235662_a_(cUpdateJigsawBlockPacket.func_240854_g_());
                jigsawTileEntity.markDirty();
                this.player.world.notifyBlockUpdate(blockPos, blockState, blockState, 3);
            }
        }
    }

    @Override
    public void func_230549_a_(CJigsawBlockGeneratePacket cJigsawBlockGeneratePacket) {
        BlockPos blockPos;
        TileEntity tileEntity;
        PacketThreadUtil.checkThreadAndEnqueue(cJigsawBlockGeneratePacket, this, this.player.getServerWorld());
        if (this.player.canUseCommandBlock() && (tileEntity = this.player.world.getTileEntity(blockPos = cJigsawBlockGeneratePacket.func_240844_b_())) instanceof JigsawTileEntity) {
            JigsawTileEntity jigsawTileEntity = (JigsawTileEntity)tileEntity;
            jigsawTileEntity.func_235665_a_(this.player.getServerWorld(), cJigsawBlockGeneratePacket.func_240845_c_(), cJigsawBlockGeneratePacket.func_240846_d_());
        }
    }

    @Override
    public void processSelectTrade(CSelectTradePacket cSelectTradePacket) {
        PacketThreadUtil.checkThreadAndEnqueue(cSelectTradePacket, this, this.player.getServerWorld());
        int n = cSelectTradePacket.func_210353_a();
        Container container = this.player.openContainer;
        if (container instanceof MerchantContainer) {
            MerchantContainer merchantContainer = (MerchantContainer)container;
            merchantContainer.setCurrentRecipeIndex(n);
            merchantContainer.func_217046_g(n);
        }
    }

    @Override
    public void processEditBook(CEditBookPacket cEditBookPacket) {
        CompoundNBT compoundNBT;
        ItemStack itemStack = cEditBookPacket.getStack();
        if (itemStack.getItem() == Items.WRITABLE_BOOK && WritableBookItem.isNBTValid(compoundNBT = itemStack.getTag())) {
            int n;
            ArrayList<String> arrayList = Lists.newArrayList();
            boolean bl = cEditBookPacket.shouldUpdateAll();
            if (bl) {
                arrayList.add(compoundNBT.getString("title"));
            }
            ListNBT listNBT = compoundNBT.getList("pages", 8);
            for (n = 0; n < listNBT.size(); ++n) {
                arrayList.add(listNBT.getString(n));
            }
            n = cEditBookPacket.func_244708_d();
            if (PlayerInventory.isHotbar(n) || n == 40) {
                this.func_244537_a(arrayList, bl ? arg_0 -> this.lambda$processEditBook$5(n, arg_0) : arg_0 -> this.lambda$processEditBook$6(n, arg_0));
            }
        }
    }

    private void func_244536_a(List<String> list, int n) {
        ItemStack itemStack = this.player.inventory.getStackInSlot(n);
        if (itemStack.getItem() == Items.WRITABLE_BOOK) {
            ListNBT listNBT = new ListNBT();
            list.stream().map(StringNBT::valueOf).forEach(listNBT::add);
            itemStack.setTagInfo("pages", listNBT);
        }
    }

    private void func_244534_a(String string, List<String> list, int n) {
        ItemStack itemStack = this.player.inventory.getStackInSlot(n);
        if (itemStack.getItem() == Items.WRITABLE_BOOK) {
            ItemStack itemStack2 = new ItemStack(Items.WRITTEN_BOOK);
            CompoundNBT compoundNBT = itemStack.getTag();
            if (compoundNBT != null) {
                itemStack2.setTag(compoundNBT.copy());
            }
            itemStack2.setTagInfo("author", StringNBT.valueOf(this.player.getName().getString()));
            itemStack2.setTagInfo("title", StringNBT.valueOf(string));
            ListNBT listNBT = new ListNBT();
            for (String string2 : list) {
                StringTextComponent stringTextComponent = new StringTextComponent(string2);
                String string3 = ITextComponent.Serializer.toJson(stringTextComponent);
                listNBT.add(StringNBT.valueOf(string3));
            }
            itemStack2.setTagInfo("pages", listNBT);
            this.player.inventory.setInventorySlotContents(n, itemStack2);
        }
    }

    @Override
    public void processNBTQueryEntity(CQueryEntityNBTPacket cQueryEntityNBTPacket) {
        Entity entity2;
        PacketThreadUtil.checkThreadAndEnqueue(cQueryEntityNBTPacket, this, this.player.getServerWorld());
        if (this.player.hasPermissionLevel(1) && (entity2 = this.player.getServerWorld().getEntityByID(cQueryEntityNBTPacket.getEntityId())) != null) {
            CompoundNBT compoundNBT = entity2.writeWithoutTypeId(new CompoundNBT());
            this.player.connection.sendPacket(new SQueryNBTResponsePacket(cQueryEntityNBTPacket.getTransactionId(), compoundNBT));
        }
    }

    @Override
    public void processNBTQueryBlockEntity(CQueryTileEntityNBTPacket cQueryTileEntityNBTPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(cQueryTileEntityNBTPacket, this, this.player.getServerWorld());
        if (this.player.hasPermissionLevel(1)) {
            TileEntity tileEntity = this.player.getServerWorld().getTileEntity(cQueryTileEntityNBTPacket.getPosition());
            CompoundNBT compoundNBT = tileEntity != null ? tileEntity.write(new CompoundNBT()) : null;
            this.player.connection.sendPacket(new SQueryNBTResponsePacket(cQueryTileEntityNBTPacket.getTransactionId(), compoundNBT));
        }
    }

    @Override
    public void processPlayer(CPlayerPacket cPlayerPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(cPlayerPacket, this, this.player.getServerWorld());
        if (ServerPlayNetHandler.isMovePlayerPacketInvalid(cPlayerPacket)) {
            this.disconnect(new TranslationTextComponent("multiplayer.disconnect.invalid_player_movement"));
        } else {
            ServerWorld serverWorld = this.player.getServerWorld();
            if (!this.player.queuedEndExit) {
                if (this.networkTickCount == 0) {
                    this.captureCurrentPosition();
                }
                if (this.targetPos != null) {
                    if (this.networkTickCount - this.lastPositionUpdate > 20) {
                        this.lastPositionUpdate = this.networkTickCount;
                        this.setPlayerLocation(this.targetPos.x, this.targetPos.y, this.targetPos.z, this.player.rotationYaw, this.player.rotationPitch);
                    }
                } else {
                    this.lastPositionUpdate = this.networkTickCount;
                    if (this.player.isPassenger()) {
                        this.player.setPositionAndRotation(this.player.getPosX(), this.player.getPosY(), this.player.getPosZ(), cPlayerPacket.getYaw(this.player.rotationYaw), cPlayerPacket.getPitch(this.player.rotationPitch));
                        this.player.getServerWorld().getChunkProvider().updatePlayerPosition(this.player);
                    } else {
                        double d = this.player.getPosX();
                        double d2 = this.player.getPosY();
                        double d3 = this.player.getPosZ();
                        double d4 = this.player.getPosY();
                        double d5 = cPlayerPacket.getX(this.player.getPosX());
                        double d6 = cPlayerPacket.getY(this.player.getPosY());
                        double d7 = cPlayerPacket.getZ(this.player.getPosZ());
                        float f = cPlayerPacket.getYaw(this.player.rotationYaw);
                        float f2 = cPlayerPacket.getPitch(this.player.rotationPitch);
                        double d8 = d5 - this.firstGoodX;
                        double d9 = d6 - this.firstGoodY;
                        double d10 = d7 - this.firstGoodZ;
                        double d11 = this.player.getMotion().lengthSquared();
                        double d12 = d8 * d8 + d9 * d9 + d10 * d10;
                        if (this.player.isSleeping()) {
                            if (d12 > 1.0) {
                                this.setPlayerLocation(this.player.getPosX(), this.player.getPosY(), this.player.getPosZ(), cPlayerPacket.getYaw(this.player.rotationYaw), cPlayerPacket.getPitch(this.player.rotationPitch));
                            }
                        } else {
                            boolean bl;
                            ++this.movePacketCounter;
                            int n = this.movePacketCounter - this.lastMovePacketCounter;
                            if (n > 5) {
                                LOGGER.debug("{} is sending move packets too frequently ({} packets since last tick)", (Object)this.player.getName().getString(), (Object)n);
                                n = 1;
                            }
                            if (!(this.player.isInvulnerableDimensionChange() || this.player.getServerWorld().getGameRules().getBoolean(GameRules.DISABLE_ELYTRA_MOVEMENT_CHECK) && this.player.isElytraFlying())) {
                                float f3;
                                float f4 = f3 = this.player.isElytraFlying() ? 300.0f : 100.0f;
                                if (d12 - d11 > (double)(f3 * (float)n) && !this.func_217264_d()) {
                                    LOGGER.warn("{} moved too quickly! {},{},{}", (Object)this.player.getName().getString(), (Object)d8, (Object)d9, (Object)d10);
                                    this.setPlayerLocation(this.player.getPosX(), this.player.getPosY(), this.player.getPosZ(), this.player.rotationYaw, this.player.rotationPitch);
                                    return;
                                }
                            }
                            AxisAlignedBB axisAlignedBB = this.player.getBoundingBox();
                            d8 = d5 - this.lastGoodX;
                            d9 = d6 - this.lastGoodY;
                            d10 = d7 - this.lastGoodZ;
                            boolean bl2 = bl = d9 > 0.0;
                            if (this.player.isOnGround() && !cPlayerPacket.isOnGround() && bl) {
                                this.player.jump();
                            }
                            this.player.move(MoverType.PLAYER, new Vector3d(d8, d9, d10));
                            d8 = d5 - this.player.getPosX();
                            d9 = d6 - this.player.getPosY();
                            if (d9 > -0.5 || d9 < 0.5) {
                                d9 = 0.0;
                            }
                            d10 = d7 - this.player.getPosZ();
                            d12 = d8 * d8 + d9 * d9 + d10 * d10;
                            boolean bl3 = false;
                            if (!this.player.isInvulnerableDimensionChange() && d12 > 0.0625 && !this.player.isSleeping() && !this.player.interactionManager.isCreative() && this.player.interactionManager.getGameType() != GameType.SPECTATOR) {
                                bl3 = true;
                                LOGGER.warn("{} moved wrongly!", (Object)this.player.getName().getString());
                            }
                            this.player.setPositionAndRotation(d5, d6, d7, f, f2);
                            if (this.player.noClip || this.player.isSleeping() || (!bl3 || !serverWorld.hasNoCollisions(this.player, axisAlignedBB)) && !this.func_241163_a_(serverWorld, axisAlignedBB)) {
                                this.floating = d9 >= -0.03125 && this.player.interactionManager.getGameType() != GameType.SPECTATOR && !this.server.isFlightAllowed() && !this.player.abilities.allowFlying && !this.player.isPotionActive(Effects.LEVITATION) && !this.player.isElytraFlying() && this.func_241162_a_(this.player);
                                this.player.getServerWorld().getChunkProvider().updatePlayerPosition(this.player);
                                this.player.handleFalling(this.player.getPosY() - d4, cPlayerPacket.isOnGround());
                                this.player.setOnGround(cPlayerPacket.isOnGround());
                                if (bl) {
                                    this.player.fallDistance = 0.0f;
                                }
                                this.player.addMovementStat(this.player.getPosX() - d, this.player.getPosY() - d2, this.player.getPosZ() - d3);
                                this.lastGoodX = this.player.getPosX();
                                this.lastGoodY = this.player.getPosY();
                                this.lastGoodZ = this.player.getPosZ();
                            } else {
                                this.setPlayerLocation(d, d2, d3, f, f2);
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean func_241163_a_(IWorldReader iWorldReader, AxisAlignedBB axisAlignedBB) {
        Stream<VoxelShape> stream = iWorldReader.func_234867_d_(this.player, this.player.getBoundingBox().shrink(1.0E-5f), ServerPlayNetHandler::lambda$func_241163_a_$7);
        VoxelShape voxelShape = VoxelShapes.create(axisAlignedBB.shrink(1.0E-5f));
        return stream.anyMatch(arg_0 -> ServerPlayNetHandler.lambda$func_241163_a_$8(voxelShape, arg_0));
    }

    public void setPlayerLocation(double d, double d2, double d3, float f, float f2) {
        this.setPlayerLocation(d, d2, d3, f, f2, Collections.emptySet());
    }

    public void setPlayerLocation(double d, double d2, double d3, float f, float f2, Set<SPlayerPositionLookPacket.Flags> set) {
        double d4 = set.contains((Object)SPlayerPositionLookPacket.Flags.X) ? this.player.getPosX() : 0.0;
        double d5 = set.contains((Object)SPlayerPositionLookPacket.Flags.Y) ? this.player.getPosY() : 0.0;
        double d6 = set.contains((Object)SPlayerPositionLookPacket.Flags.Z) ? this.player.getPosZ() : 0.0;
        float f3 = set.contains((Object)SPlayerPositionLookPacket.Flags.Y_ROT) ? this.player.rotationYaw : 0.0f;
        float f4 = set.contains((Object)SPlayerPositionLookPacket.Flags.X_ROT) ? this.player.rotationPitch : 0.0f;
        this.targetPos = new Vector3d(d, d2, d3);
        if (++this.teleportId == Integer.MAX_VALUE) {
            this.teleportId = 0;
        }
        this.lastPositionUpdate = this.networkTickCount;
        this.player.setPositionAndRotation(d, d2, d3, f, f2);
        this.player.connection.sendPacket(new SPlayerPositionLookPacket(d - d4, d2 - d5, d3 - d6, f - f3, f2 - f4, set, this.teleportId));
    }

    @Override
    public void processPlayerDigging(CPlayerDiggingPacket cPlayerDiggingPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(cPlayerDiggingPacket, this, this.player.getServerWorld());
        BlockPos blockPos = cPlayerDiggingPacket.getPosition();
        this.player.markPlayerActive();
        CPlayerDiggingPacket.Action action = cPlayerDiggingPacket.getAction();
        switch (action) {
            case SWAP_ITEM_WITH_OFFHAND: {
                if (!this.player.isSpectator()) {
                    ItemStack itemStack = this.player.getHeldItem(Hand.OFF_HAND);
                    this.player.setHeldItem(Hand.OFF_HAND, this.player.getHeldItem(Hand.MAIN_HAND));
                    this.player.setHeldItem(Hand.MAIN_HAND, itemStack);
                    this.player.resetActiveHand();
                }
                return;
            }
            case DROP_ITEM: {
                if (!this.player.isSpectator()) {
                    this.player.drop(true);
                }
                return;
            }
            case DROP_ALL_ITEMS: {
                if (!this.player.isSpectator()) {
                    this.player.drop(false);
                }
                return;
            }
            case RELEASE_USE_ITEM: {
                this.player.stopActiveHand();
                return;
            }
            case START_DESTROY_BLOCK: 
            case ABORT_DESTROY_BLOCK: 
            case STOP_DESTROY_BLOCK: {
                this.player.interactionManager.func_225416_a(blockPos, action, cPlayerDiggingPacket.getFacing(), this.server.getBuildLimit());
                return;
            }
        }
        throw new IllegalArgumentException("Invalid player action");
    }

    private static boolean func_241166_a_(ServerPlayerEntity serverPlayerEntity, ItemStack itemStack) {
        if (itemStack.isEmpty()) {
            return true;
        }
        Item item = itemStack.getItem();
        return (item instanceof BlockItem || item instanceof BucketItem) && !serverPlayerEntity.getCooldownTracker().hasCooldown(item);
    }

    @Override
    public void processTryUseItemOnBlock(CPlayerTryUseItemOnBlockPacket cPlayerTryUseItemOnBlockPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(cPlayerTryUseItemOnBlockPacket, this, this.player.getServerWorld());
        ServerWorld serverWorld = this.player.getServerWorld();
        Hand hand = cPlayerTryUseItemOnBlockPacket.getHand();
        ItemStack itemStack = this.player.getHeldItem(hand);
        BlockRayTraceResult blockRayTraceResult = cPlayerTryUseItemOnBlockPacket.func_218794_c();
        BlockPos blockPos = blockRayTraceResult.getPos();
        Direction direction = blockRayTraceResult.getFace();
        this.player.markPlayerActive();
        if (blockPos.getY() < this.server.getBuildLimit()) {
            if (this.targetPos == null && this.player.getDistanceSq((double)blockPos.getX() + 0.5, (double)blockPos.getY() + 0.5, (double)blockPos.getZ() + 0.5) < 64.0 && serverWorld.isBlockModifiable(this.player, blockPos)) {
                ActionResultType actionResultType = this.player.interactionManager.func_219441_a(this.player, serverWorld, itemStack, hand, blockRayTraceResult);
                if (direction == Direction.UP && !actionResultType.isSuccessOrConsume() && blockPos.getY() >= this.server.getBuildLimit() - 1 && ServerPlayNetHandler.func_241166_a_(this.player, itemStack)) {
                    IFormattableTextComponent iFormattableTextComponent = new TranslationTextComponent("build.tooHigh", this.server.getBuildLimit()).mergeStyle(TextFormatting.RED);
                    this.player.connection.sendPacket(new SChatPacket(iFormattableTextComponent, ChatType.GAME_INFO, Util.DUMMY_UUID));
                } else if (actionResultType.isSuccess()) {
                    this.player.swing(hand, false);
                }
            }
        } else {
            IFormattableTextComponent iFormattableTextComponent = new TranslationTextComponent("build.tooHigh", this.server.getBuildLimit()).mergeStyle(TextFormatting.RED);
            this.player.connection.sendPacket(new SChatPacket(iFormattableTextComponent, ChatType.GAME_INFO, Util.DUMMY_UUID));
        }
        this.player.connection.sendPacket(new SChangeBlockPacket(serverWorld, blockPos));
        this.player.connection.sendPacket(new SChangeBlockPacket(serverWorld, blockPos.offset(direction)));
    }

    @Override
    public void processTryUseItem(CPlayerTryUseItemPacket cPlayerTryUseItemPacket) {
        ActionResultType actionResultType;
        PacketThreadUtil.checkThreadAndEnqueue(cPlayerTryUseItemPacket, this, this.player.getServerWorld());
        ServerWorld serverWorld = this.player.getServerWorld();
        Hand hand = cPlayerTryUseItemPacket.getHand();
        ItemStack itemStack = this.player.getHeldItem(hand);
        this.player.markPlayerActive();
        if (!itemStack.isEmpty() && (actionResultType = this.player.interactionManager.processRightClick(this.player, serverWorld, itemStack, hand)).isSuccess()) {
            this.player.swing(hand, false);
        }
    }

    @Override
    public void handleSpectate(CSpectatePacket cSpectatePacket) {
        PacketThreadUtil.checkThreadAndEnqueue(cSpectatePacket, this, this.player.getServerWorld());
        if (this.player.isSpectator()) {
            for (ServerWorld serverWorld : this.server.getWorlds()) {
                Entity entity2 = cSpectatePacket.getEntity(serverWorld);
                if (entity2 == null) continue;
                this.player.teleport(serverWorld, entity2.getPosX(), entity2.getPosY(), entity2.getPosZ(), entity2.rotationYaw, entity2.rotationPitch);
                return;
            }
        }
    }

    @Override
    public void handleResourcePackStatus(CResourcePackStatusPacket cResourcePackStatusPacket) {
    }

    @Override
    public void processSteerBoat(CSteerBoatPacket cSteerBoatPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(cSteerBoatPacket, this, this.player.getServerWorld());
        Entity entity2 = this.player.getRidingEntity();
        if (entity2 instanceof BoatEntity) {
            ((BoatEntity)entity2).setPaddleState(cSteerBoatPacket.getLeft(), cSteerBoatPacket.getRight());
        }
    }

    @Override
    public void onDisconnect(ITextComponent iTextComponent) {
        LOGGER.info("{} lost connection: {}", (Object)this.player.getName().getString(), (Object)iTextComponent.getString());
        this.server.refreshStatusNextTick();
        this.server.getPlayerList().func_232641_a_(new TranslationTextComponent("multiplayer.player.left", this.player.getDisplayName()).mergeStyle(TextFormatting.YELLOW), ChatType.SYSTEM, Util.DUMMY_UUID);
        this.player.disconnect();
        this.server.getPlayerList().playerLoggedOut(this.player);
        IChatFilter iChatFilter = this.player.func_244529_Q();
        if (iChatFilter != null) {
            iChatFilter.func_244434_b();
        }
        if (this.func_217264_d()) {
            LOGGER.info("Stopping singleplayer server as player logged out");
            this.server.initiateShutdown(true);
        }
    }

    public void sendPacket(IPacket<?> iPacket) {
        this.sendPacket(iPacket, null);
    }

    public void sendPacket(IPacket<?> iPacket, @Nullable GenericFutureListener<? extends Future<? super Void>> genericFutureListener) {
        Object object;
        if (iPacket instanceof SChatPacket) {
            SChatPacket sChatPacket = (SChatPacket)iPacket;
            object = this.player.getChatVisibility();
            if (object == ChatVisibility.HIDDEN && sChatPacket.getType() != ChatType.GAME_INFO) {
                return;
            }
            if (object == ChatVisibility.SYSTEM && !sChatPacket.isSystem()) {
                return;
            }
        }
        try {
            this.netManager.sendPacket(iPacket, genericFutureListener);
        } catch (Throwable throwable) {
            object = CrashReport.makeCrashReport(throwable, "Sending packet");
            CrashReportCategory crashReportCategory = ((CrashReport)object).makeCategory("Packet being sent");
            crashReportCategory.addDetail("Packet class", () -> ServerPlayNetHandler.lambda$sendPacket$9(iPacket));
            throw new ReportedException((CrashReport)object);
        }
    }

    @Override
    public void processHeldItemChange(CHeldItemChangePacket cHeldItemChangePacket) {
        PacketThreadUtil.checkThreadAndEnqueue(cHeldItemChangePacket, this, this.player.getServerWorld());
        if (cHeldItemChangePacket.getSlotId() >= 0 && cHeldItemChangePacket.getSlotId() < PlayerInventory.getHotbarSize()) {
            if (this.player.inventory.currentItem != cHeldItemChangePacket.getSlotId() && this.player.getActiveHand() == Hand.MAIN_HAND) {
                this.player.resetActiveHand();
            }
            this.player.inventory.currentItem = cHeldItemChangePacket.getSlotId();
            this.player.markPlayerActive();
        } else {
            LOGGER.warn("{} tried to set an invalid carried item", (Object)this.player.getName().getString());
        }
    }

    @Override
    public void processChatMessage(CChatMessagePacket cChatMessagePacket) {
        String string = org.apache.commons.lang3.StringUtils.normalizeSpace(cChatMessagePacket.getMessage());
        if (string.startsWith("/")) {
            PacketThreadUtil.checkThreadAndEnqueue(cChatMessagePacket, this, this.player.getServerWorld());
            this.func_244548_c(string);
        } else {
            this.func_244535_a(string, this::func_244548_c);
        }
    }

    private void func_244548_c(String string) {
        if (this.player.getChatVisibility() == ChatVisibility.HIDDEN) {
            this.sendPacket(new SChatPacket(new TranslationTextComponent("chat.cannotSend").mergeStyle(TextFormatting.RED), ChatType.SYSTEM, Util.DUMMY_UUID));
        } else {
            this.player.markPlayerActive();
            for (int i = 0; i < string.length(); ++i) {
                if (SharedConstants.isAllowedCharacter(string.charAt(i))) continue;
                this.disconnect(new TranslationTextComponent("multiplayer.disconnect.illegal_characters"));
                return;
            }
            if (string.startsWith("/")) {
                this.handleSlashCommand(string);
            } else {
                TranslationTextComponent translationTextComponent = new TranslationTextComponent("chat.type.text", this.player.getDisplayName(), string);
                this.server.getPlayerList().func_232641_a_(translationTextComponent, ChatType.CHAT, this.player.getUniqueID());
            }
            this.chatSpamThresholdCount += 20;
            if (this.chatSpamThresholdCount > 200 && !this.server.getPlayerList().canSendCommands(this.player.getGameProfile())) {
                this.disconnect(new TranslationTextComponent("disconnect.spam"));
            }
        }
    }

    private void handleSlashCommand(String string) {
        this.server.getCommandManager().handleCommand(this.player.getCommandSource(), string);
    }

    @Override
    public void handleAnimation(CAnimateHandPacket cAnimateHandPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(cAnimateHandPacket, this, this.player.getServerWorld());
        this.player.markPlayerActive();
        this.player.swingArm(cAnimateHandPacket.getHand());
    }

    @Override
    public void processEntityAction(CEntityActionPacket cEntityActionPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(cEntityActionPacket, this, this.player.getServerWorld());
        this.player.markPlayerActive();
        switch (cEntityActionPacket.getAction()) {
            case PRESS_SHIFT_KEY: {
                this.player.setSneaking(false);
                break;
            }
            case RELEASE_SHIFT_KEY: {
                this.player.setSneaking(true);
                break;
            }
            case START_SPRINTING: {
                this.player.setSprinting(false);
                break;
            }
            case STOP_SPRINTING: {
                this.player.setSprinting(true);
                break;
            }
            case STOP_SLEEPING: {
                if (!this.player.isSleeping()) break;
                this.player.stopSleepInBed(false, false);
                this.targetPos = this.player.getPositionVec();
                break;
            }
            case START_RIDING_JUMP: {
                if (!(this.player.getRidingEntity() instanceof IJumpingMount)) break;
                IJumpingMount iJumpingMount = (IJumpingMount)((Object)this.player.getRidingEntity());
                int n = cEntityActionPacket.getAuxData();
                if (!iJumpingMount.canJump() || n <= 0) break;
                iJumpingMount.handleStartJump(n);
                break;
            }
            case STOP_RIDING_JUMP: {
                if (!(this.player.getRidingEntity() instanceof IJumpingMount)) break;
                IJumpingMount iJumpingMount = (IJumpingMount)((Object)this.player.getRidingEntity());
                iJumpingMount.handleStopJump();
                break;
            }
            case OPEN_INVENTORY: {
                if (!(this.player.getRidingEntity() instanceof AbstractHorseEntity)) break;
                ((AbstractHorseEntity)this.player.getRidingEntity()).openGUI(this.player);
                break;
            }
            case START_FALL_FLYING: {
                if (this.player.tryToStartFallFlying()) break;
                this.player.stopFallFlying();
                break;
            }
            default: {
                throw new IllegalArgumentException("Invalid client command!");
            }
        }
    }

    @Override
    public void processUseEntity(CUseEntityPacket cUseEntityPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(cUseEntityPacket, this, this.player.getServerWorld());
        ServerWorld serverWorld = this.player.getServerWorld();
        Entity entity2 = cUseEntityPacket.getEntityFromWorld(serverWorld);
        this.player.markPlayerActive();
        this.player.setSneaking(cUseEntityPacket.func_241792_e_());
        if (entity2 != null) {
            double d = 36.0;
            if (this.player.getDistanceSq(entity2) < 36.0) {
                Hand hand = cUseEntityPacket.getHand();
                ItemStack itemStack = hand != null ? this.player.getHeldItem(hand).copy() : ItemStack.EMPTY;
                Optional<Object> optional = Optional.empty();
                if (cUseEntityPacket.getAction() == CUseEntityPacket.Action.INTERACT) {
                    optional = Optional.of(this.player.interactOn(entity2, hand));
                } else if (cUseEntityPacket.getAction() == CUseEntityPacket.Action.INTERACT_AT) {
                    optional = Optional.of(entity2.applyPlayerInteraction(this.player, cUseEntityPacket.getHitVec(), hand));
                } else if (cUseEntityPacket.getAction() == CUseEntityPacket.Action.ATTACK) {
                    if (entity2 instanceof ItemEntity || entity2 instanceof ExperienceOrbEntity || entity2 instanceof AbstractArrowEntity || entity2 == this.player) {
                        this.disconnect(new TranslationTextComponent("multiplayer.disconnect.invalid_entity_attacked"));
                        LOGGER.warn("Player {} tried to attack an invalid entity", (Object)this.player.getName().getString());
                        return;
                    }
                    this.player.attackTargetEntityWithCurrentItem(entity2);
                }
                if (optional.isPresent() && ((ActionResultType)((Object)optional.get())).isSuccessOrConsume()) {
                    CriteriaTriggers.PLAYER_ENTITY_INTERACTION.test(this.player, itemStack, entity2);
                    if (((ActionResultType)((Object)optional.get())).isSuccess()) {
                        this.player.swing(hand, false);
                    }
                }
            }
        }
    }

    @Override
    public void processClientStatus(CClientStatusPacket cClientStatusPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(cClientStatusPacket, this, this.player.getServerWorld());
        this.player.markPlayerActive();
        CClientStatusPacket.State state = cClientStatusPacket.getStatus();
        switch (state) {
            case PERFORM_RESPAWN: {
                if (this.player.queuedEndExit) {
                    this.player.queuedEndExit = false;
                    this.player = this.server.getPlayerList().func_232644_a_(this.player, false);
                    CriteriaTriggers.CHANGED_DIMENSION.testForAll(this.player, World.THE_END, World.OVERWORLD);
                    break;
                }
                if (this.player.getHealth() > 0.0f) {
                    return;
                }
                this.player = this.server.getPlayerList().func_232644_a_(this.player, true);
                if (!this.server.isHardcore()) break;
                this.player.setGameType(GameType.SPECTATOR);
                this.player.getServerWorld().getGameRules().get(GameRules.SPECTATORS_GENERATE_CHUNKS).set(false, this.server);
                break;
            }
            case REQUEST_STATS: {
                this.player.getStats().sendStats(this.player);
            }
        }
    }

    @Override
    public void processCloseWindow(CCloseWindowPacket cCloseWindowPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(cCloseWindowPacket, this, this.player.getServerWorld());
        this.player.closeContainer();
    }

    @Override
    public void processClickWindow(CClickWindowPacket cClickWindowPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(cClickWindowPacket, this, this.player.getServerWorld());
        this.player.markPlayerActive();
        if (this.player.openContainer.windowId == cClickWindowPacket.getWindowId() && this.player.openContainer.getCanCraft(this.player)) {
            if (this.player.isSpectator()) {
                NonNullList<ItemStack> nonNullList = NonNullList.create();
                for (int i = 0; i < this.player.openContainer.inventorySlots.size(); ++i) {
                    nonNullList.add(this.player.openContainer.inventorySlots.get(i).getStack());
                }
                this.player.sendAllContents(this.player.openContainer, nonNullList);
            } else {
                ItemStack itemStack = this.player.openContainer.slotClick(cClickWindowPacket.getSlotId(), cClickWindowPacket.getUsedButton(), cClickWindowPacket.getClickType(), this.player);
                if (ItemStack.areItemStacksEqual(cClickWindowPacket.getClickedItem(), itemStack)) {
                    this.player.connection.sendPacket(new SConfirmTransactionPacket(cClickWindowPacket.getWindowId(), cClickWindowPacket.getActionNumber(), true));
                    this.player.isChangingQuantityOnly = true;
                    this.player.openContainer.detectAndSendChanges();
                    this.player.updateHeldItem();
                    this.player.isChangingQuantityOnly = false;
                } else {
                    this.pendingTransactions.put(this.player.openContainer.windowId, cClickWindowPacket.getActionNumber());
                    this.player.connection.sendPacket(new SConfirmTransactionPacket(cClickWindowPacket.getWindowId(), cClickWindowPacket.getActionNumber(), false));
                    this.player.openContainer.setCanCraft(this.player, true);
                    NonNullList<ItemStack> nonNullList = NonNullList.create();
                    for (int i = 0; i < this.player.openContainer.inventorySlots.size(); ++i) {
                        ItemStack itemStack2 = this.player.openContainer.inventorySlots.get(i).getStack();
                        nonNullList.add(itemStack2.isEmpty() ? ItemStack.EMPTY : itemStack2);
                    }
                    this.player.sendAllContents(this.player.openContainer, nonNullList);
                }
            }
        }
    }

    @Override
    public void processPlaceRecipe(CPlaceRecipePacket cPlaceRecipePacket) {
        PacketThreadUtil.checkThreadAndEnqueue(cPlaceRecipePacket, this, this.player.getServerWorld());
        this.player.markPlayerActive();
        if (!this.player.isSpectator() && this.player.openContainer.windowId == cPlaceRecipePacket.getWindowId() && this.player.openContainer.getCanCraft(this.player) && this.player.openContainer instanceof RecipeBookContainer) {
            this.server.getRecipeManager().getRecipe(cPlaceRecipePacket.getRecipeId()).ifPresent(arg_0 -> this.lambda$processPlaceRecipe$10(cPlaceRecipePacket, arg_0));
        }
    }

    @Override
    public void processEnchantItem(CEnchantItemPacket cEnchantItemPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(cEnchantItemPacket, this, this.player.getServerWorld());
        this.player.markPlayerActive();
        if (this.player.openContainer.windowId == cEnchantItemPacket.getWindowId() && this.player.openContainer.getCanCraft(this.player) && !this.player.isSpectator()) {
            this.player.openContainer.enchantItem(this.player, cEnchantItemPacket.getButton());
            this.player.openContainer.detectAndSendChanges();
        }
    }

    @Override
    public void processCreativeInventoryAction(CCreativeInventoryActionPacket cCreativeInventoryActionPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(cCreativeInventoryActionPacket, this, this.player.getServerWorld());
        if (this.player.interactionManager.isCreative()) {
            boolean bl;
            BlockPos blockPos;
            TileEntity tileEntity;
            boolean bl2 = cCreativeInventoryActionPacket.getSlotId() < 0;
            ItemStack itemStack = cCreativeInventoryActionPacket.getStack();
            CompoundNBT compoundNBT = itemStack.getChildTag("BlockEntityTag");
            if (!itemStack.isEmpty() && compoundNBT != null && compoundNBT.contains("x") && compoundNBT.contains("y") && compoundNBT.contains("z") && (tileEntity = this.player.world.getTileEntity(blockPos = new BlockPos(compoundNBT.getInt("x"), compoundNBT.getInt("y"), compoundNBT.getInt("z")))) != null) {
                CompoundNBT compoundNBT2 = tileEntity.write(new CompoundNBT());
                compoundNBT2.remove("x");
                compoundNBT2.remove("y");
                compoundNBT2.remove("z");
                itemStack.setTagInfo("BlockEntityTag", compoundNBT2);
            }
            boolean bl3 = cCreativeInventoryActionPacket.getSlotId() >= 1 && cCreativeInventoryActionPacket.getSlotId() <= 45;
            boolean bl4 = bl = itemStack.isEmpty() || itemStack.getDamage() >= 0 && itemStack.getCount() <= 64 && !itemStack.isEmpty();
            if (bl3 && bl) {
                if (itemStack.isEmpty()) {
                    this.player.container.putStackInSlot(cCreativeInventoryActionPacket.getSlotId(), ItemStack.EMPTY);
                } else {
                    this.player.container.putStackInSlot(cCreativeInventoryActionPacket.getSlotId(), itemStack);
                }
                this.player.container.setCanCraft(this.player, false);
                this.player.container.detectAndSendChanges();
            } else if (bl2 && bl && this.itemDropThreshold < 200) {
                this.itemDropThreshold += 20;
                this.player.dropItem(itemStack, false);
            }
        }
    }

    @Override
    public void processConfirmTransaction(CConfirmTransactionPacket cConfirmTransactionPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(cConfirmTransactionPacket, this, this.player.getServerWorld());
        int n = this.player.openContainer.windowId;
        if (n == cConfirmTransactionPacket.getWindowId() && this.pendingTransactions.getOrDefault(n, (short)(cConfirmTransactionPacket.getUid() + 1)) == cConfirmTransactionPacket.getUid() && !this.player.openContainer.getCanCraft(this.player) && !this.player.isSpectator()) {
            this.player.openContainer.setCanCraft(this.player, false);
        }
    }

    @Override
    public void processUpdateSign(CUpdateSignPacket cUpdateSignPacket) {
        List<String> list = Stream.of(cUpdateSignPacket.getLines()).map(TextFormatting::getTextWithoutFormattingCodes).collect(Collectors.toList());
        this.func_244537_a(list, arg_0 -> this.lambda$processUpdateSign$11(cUpdateSignPacket, arg_0));
    }

    private void func_244542_a(CUpdateSignPacket cUpdateSignPacket, List<String> list) {
        this.player.markPlayerActive();
        ServerWorld serverWorld = this.player.getServerWorld();
        BlockPos blockPos = cUpdateSignPacket.getPosition();
        if (serverWorld.isBlockLoaded(blockPos)) {
            BlockState blockState = serverWorld.getBlockState(blockPos);
            TileEntity tileEntity = serverWorld.getTileEntity(blockPos);
            if (!(tileEntity instanceof SignTileEntity)) {
                return;
            }
            SignTileEntity signTileEntity = (SignTileEntity)tileEntity;
            if (!signTileEntity.getIsEditable() || signTileEntity.getPlayer() != this.player) {
                LOGGER.warn("Player {} just tried to change non-editable sign", (Object)this.player.getName().getString());
                return;
            }
            for (int i = 0; i < list.size(); ++i) {
                signTileEntity.setText(i, new StringTextComponent(list.get(i)));
            }
            signTileEntity.markDirty();
            serverWorld.notifyBlockUpdate(blockPos, blockState, blockState, 3);
        }
    }

    @Override
    public void processKeepAlive(CKeepAlivePacket cKeepAlivePacket) {
        if (this.keepAlivePending && cKeepAlivePacket.getKey() == this.keepAliveKey) {
            int n = (int)(Util.milliTime() - this.keepAliveTime);
            this.player.ping = (this.player.ping * 3 + n) / 4;
            this.keepAlivePending = false;
        } else if (!this.func_217264_d()) {
            this.disconnect(new TranslationTextComponent("disconnect.timeout"));
        }
    }

    @Override
    public void processPlayerAbilities(CPlayerAbilitiesPacket cPlayerAbilitiesPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(cPlayerAbilitiesPacket, this, this.player.getServerWorld());
        this.player.abilities.isFlying = cPlayerAbilitiesPacket.isFlying() && this.player.abilities.allowFlying;
    }

    @Override
    public void processClientSettings(CClientSettingsPacket cClientSettingsPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(cClientSettingsPacket, this, this.player.getServerWorld());
        this.player.handleClientSettings(cClientSettingsPacket);
    }

    @Override
    public void processCustomPayload(CCustomPayloadPacket cCustomPayloadPacket) {
    }

    @Override
    public void func_217263_a(CSetDifficultyPacket cSetDifficultyPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(cSetDifficultyPacket, this, this.player.getServerWorld());
        if (this.player.hasPermissionLevel(1) || this.func_217264_d()) {
            this.server.setDifficultyForAllWorlds(cSetDifficultyPacket.func_218773_b(), true);
        }
    }

    @Override
    public void func_217261_a(CLockDifficultyPacket cLockDifficultyPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(cLockDifficultyPacket, this, this.player.getServerWorld());
        if (this.player.hasPermissionLevel(1) || this.func_217264_d()) {
            this.server.setDifficultyLocked(cLockDifficultyPacket.func_218776_b());
        }
    }

    private void lambda$processUpdateSign$11(CUpdateSignPacket cUpdateSignPacket, List list) {
        this.func_244542_a(cUpdateSignPacket, list);
    }

    private void lambda$processPlaceRecipe$10(CPlaceRecipePacket cPlaceRecipePacket, IRecipe iRecipe) {
        ((RecipeBookContainer)this.player.openContainer).func_217056_a(cPlaceRecipePacket.shouldPlaceAll(), iRecipe, this.player);
    }

    private static String lambda$sendPacket$9(IPacket iPacket) throws Exception {
        return iPacket.getClass().getCanonicalName();
    }

    private static boolean lambda$func_241163_a_$8(VoxelShape voxelShape, VoxelShape voxelShape2) {
        return !VoxelShapes.compare(voxelShape2, voxelShape, IBooleanFunction.AND);
    }

    private static boolean lambda$func_241163_a_$7(Entity entity2) {
        return false;
    }

    private void lambda$processEditBook$6(int n, List list) {
        this.func_244536_a(list, n);
    }

    private void lambda$processEditBook$5(int n, List list) {
        this.func_244534_a((String)list.get(0), list.subList(1, list.size()), n);
    }

    private void lambda$processTabComplete$4(CTabCompletePacket cTabCompletePacket, Suggestions suggestions) {
        this.netManager.sendPacket(new STabCompletePacket(cTabCompletePacket.getTransactionId(), suggestions));
    }

    private static void lambda$func_244533_a$3(Consumer consumer, Object object) {
        consumer.accept(object);
    }

    private static void lambda$func_244533_a$2(Consumer consumer, Optional optional) {
        optional.ifPresent(consumer);
    }

    private void lambda$func_244533_a$1(Consumer consumer, Object object) {
        if (this.getNetworkManager().isChannelOpen()) {
            consumer.accept(object);
        } else {
            LOGGER.debug("Ignoring packet due to disconnection");
        }
    }

    private void lambda$disconnect$0(ITextComponent iTextComponent, Future future) throws Exception {
        this.netManager.closeChannel(iTextComponent);
    }
}

