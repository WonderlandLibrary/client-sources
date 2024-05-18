package net.minecraft.client.network;

import net.minecraft.network.play.*;
import com.mojang.authlib.*;
import net.minecraft.client.settings.*;
import net.minecraft.client.resources.*;
import net.minecraft.village.*;
import net.minecraft.init.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.monster.*;
import net.minecraft.client.audio.*;
import io.netty.buffer.*;
import net.minecraft.client.*;
import net.minecraft.client.particle.*;
import net.minecraft.client.entity.*;
import net.minecraft.item.*;
import net.minecraft.world.storage.*;
import net.minecraft.stats.*;
import java.util.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.block.*;
import net.minecraft.entity.item.*;
import org.apache.logging.log4j.*;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.*;
import net.minecraft.inventory.*;
import net.minecraft.client.player.inventory.*;
import net.minecraft.client.stream.*;
import net.minecraft.realms.*;
import net.minecraft.util.*;
import net.minecraft.scoreboard.*;
import net.minecraft.world.chunk.*;
import com.google.common.collect.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.world.*;
import net.minecraft.tileentity.*;
import java.io.*;
import com.google.common.util.concurrent.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.client.gui.*;
import net.minecraft.entity.effect.*;
import net.minecraft.potion.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.play.server.*;

public class NetHandlerPlayClient implements INetHandlerPlayClient
{
    public int currentServerMaxPlayers;
    private WorldClient clientWorldController;
    private final GuiScreen guiScreenServer;
    private boolean field_147308_k;
    private boolean doneLoadingTerrain;
    private final GameProfile profile;
    private static final String[] I;
    private final Map<UUID, NetworkPlayerInfo> playerInfoMap;
    private static int[] $SWITCH_TABLE$net$minecraft$network$play$server$S38PacketPlayerListItem$Action;
    private static final Logger logger;
    private final NetworkManager netManager;
    private final Random avRandomizer;
    private static int[] $SWITCH_TABLE$net$minecraft$network$play$server$S45PacketTitle$Type;
    private Minecraft gameController;
    
    @Override
    public void handleSpawnPainting(final S10PacketSpawnPainting s10PacketSpawnPainting) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayClient>)s10PacketSpawnPainting, this, this.gameController);
        this.clientWorldController.addEntityToWorld(s10PacketSpawnPainting.getEntityID(), new EntityPainting(this.clientWorldController, s10PacketSpawnPainting.getPosition(), s10PacketSpawnPainting.getFacing(), s10PacketSpawnPainting.getTitle()));
    }
    
    public void addToSendQueue(final Packet packet) {
        this.netManager.sendPacket(packet);
    }
    
    @Override
    public void handleUseBed(final S0APacketUseBed s0APacketUseBed) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayClient>)s0APacketUseBed, this, this.gameController);
        s0APacketUseBed.getPlayer(this.clientWorldController).trySleep(s0APacketUseBed.getBedPosition());
    }
    
    @Override
    public void handleEntityHeadLook(final S19PacketEntityHeadLook s19PacketEntityHeadLook) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayClient>)s19PacketEntityHeadLook, this, this.gameController);
        final Entity entity = s19PacketEntityHeadLook.getEntity(this.clientWorldController);
        if (entity != null) {
            entity.setRotationYawHead(s19PacketEntityHeadLook.getYaw() * (101 + 222 - 47 + 84) / 256.0f);
        }
    }
    
    public GameProfile getGameProfile() {
        return this.profile;
    }
    
    @Override
    public void handleHeldItemChange(final S09PacketHeldItemChange s09PacketHeldItemChange) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayClient>)s09PacketHeldItemChange, this, this.gameController);
        if (s09PacketHeldItemChange.getHeldItemHotbarIndex() >= 0 && s09PacketHeldItemChange.getHeldItemHotbarIndex() < InventoryPlayer.getHotbarSize()) {
            this.gameController.thePlayer.inventory.currentItem = s09PacketHeldItemChange.getHeldItemHotbarIndex();
        }
    }
    
    @Override
    public void handleEntityMovement(final S14PacketEntity s14PacketEntity) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayClient>)s14PacketEntity, this, this.gameController);
        final Entity entity = s14PacketEntity.getEntity(this.clientWorldController);
        if (entity != null) {
            final Entity entity2 = entity;
            entity2.serverPosX += s14PacketEntity.func_149062_c();
            final Entity entity3 = entity;
            entity3.serverPosY += s14PacketEntity.func_149061_d();
            final Entity entity4 = entity;
            entity4.serverPosZ += s14PacketEntity.func_149064_e();
            final double n = entity.serverPosX / 32.0;
            final double n2 = entity.serverPosY / 32.0;
            final double n3 = entity.serverPosZ / 32.0;
            float rotationYaw;
            if (s14PacketEntity.func_149060_h()) {
                rotationYaw = s14PacketEntity.func_149066_f() * (12 + 109 - 70 + 309) / 256.0f;
                "".length();
                if (4 < 3) {
                    throw null;
                }
            }
            else {
                rotationYaw = entity.rotationYaw;
            }
            final float n4 = rotationYaw;
            float rotationPitch;
            if (s14PacketEntity.func_149060_h()) {
                rotationPitch = s14PacketEntity.func_149063_g() * (49 + 14 - 18 + 315) / 256.0f;
                "".length();
                if (false) {
                    throw null;
                }
            }
            else {
                rotationPitch = entity.rotationPitch;
            }
            entity.setPositionAndRotation2(n, n2, n3, n4, rotationPitch, "   ".length(), "".length() != 0);
            entity.onGround = s14PacketEntity.getOnGround();
        }
    }
    
    @Override
    public void handleBlockBreakAnim(final S25PacketBlockBreakAnim s25PacketBlockBreakAnim) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayClient>)s25PacketBlockBreakAnim, this, this.gameController);
        this.gameController.theWorld.sendBlockBreakProgress(s25PacketBlockBreakAnim.getBreakerId(), s25PacketBlockBreakAnim.getPosition(), s25PacketBlockBreakAnim.getProgress());
    }
    
    @Override
    public void handleMultiBlockChange(final S22PacketMultiBlockChange s22PacketMultiBlockChange) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayClient>)s22PacketMultiBlockChange, this, this.gameController);
        final S22PacketMultiBlockChange.BlockUpdateData[] changedBlocks;
        final int length = (changedBlocks = s22PacketMultiBlockChange.getChangedBlocks()).length;
        int i = "".length();
        "".length();
        if (false) {
            throw null;
        }
        while (i < length) {
            final S22PacketMultiBlockChange.BlockUpdateData blockUpdateData = changedBlocks[i];
            this.clientWorldController.invalidateRegionAndSetBlock(blockUpdateData.getPos(), blockUpdateData.getBlockState());
            ++i;
        }
    }
    
    public NetworkPlayerInfo getPlayerInfo(final UUID uuid) {
        return this.playerInfoMap.get(uuid);
    }
    
    public NetworkManager getNetworkManager() {
        return this.netManager;
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
            if (1 < -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void handleTimeUpdate(final S03PacketTimeUpdate s03PacketTimeUpdate) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayClient>)s03PacketTimeUpdate, this, this.gameController);
        this.gameController.theWorld.setTotalWorldTime(s03PacketTimeUpdate.getTotalWorldTime());
        this.gameController.theWorld.setWorldTime(s03PacketTimeUpdate.getWorldTime());
    }
    
    @Override
    public void handleEntityEquipment(final S04PacketEntityEquipment s04PacketEntityEquipment) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayClient>)s04PacketEntityEquipment, this, this.gameController);
        final Entity entityByID = this.clientWorldController.getEntityByID(s04PacketEntityEquipment.getEntityID());
        if (entityByID != null) {
            entityByID.setCurrentItemOrArmor(s04PacketEntityEquipment.getEquipmentSlot(), s04PacketEntityEquipment.getItemStack());
        }
    }
    
    @Override
    public void handleUpdateHealth(final S06PacketUpdateHealth s06PacketUpdateHealth) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayClient>)s06PacketUpdateHealth, this, this.gameController);
        this.gameController.thePlayer.setPlayerSPHealth(s06PacketUpdateHealth.getHealth());
        this.gameController.thePlayer.getFoodStats().setFoodLevel(s06PacketUpdateHealth.getFoodLevel());
        this.gameController.thePlayer.getFoodStats().setFoodSaturationLevel(s06PacketUpdateHealth.getSaturationLevel());
    }
    
    @Override
    public void handleEntityAttach(final S1BPacketEntityAttach s1BPacketEntityAttach) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayClient>)s1BPacketEntityAttach, this, this.gameController);
        Entity entity = this.clientWorldController.getEntityByID(s1BPacketEntityAttach.getEntityId());
        final Entity entityByID = this.clientWorldController.getEntityByID(s1BPacketEntityAttach.getVehicleEntityId());
        if (s1BPacketEntityAttach.getLeash() == 0) {
            int length = "".length();
            if (s1BPacketEntityAttach.getEntityId() == this.gameController.thePlayer.getEntityId()) {
                entity = this.gameController.thePlayer;
                if (entityByID instanceof EntityBoat) {
                    ((EntityBoat)entityByID).setIsBoatEmpty("".length() != 0);
                }
                int n;
                if (entity.ridingEntity == null && entityByID != null) {
                    n = " ".length();
                    "".length();
                    if (4 != 4) {
                        throw null;
                    }
                }
                else {
                    n = "".length();
                }
                length = n;
                "".length();
                if (0 == 2) {
                    throw null;
                }
            }
            else if (entityByID instanceof EntityBoat) {
                ((EntityBoat)entityByID).setIsBoatEmpty(" ".length() != 0);
            }
            if (entity == null) {
                return;
            }
            entity.mountEntity(entityByID);
            if (length != 0) {
                final GameSettings gameSettings = this.gameController.gameSettings;
                final GuiIngame ingameGUI = this.gameController.ingameGUI;
                final String s = NetHandlerPlayClient.I[0x55 ^ 0x53];
                final Object[] array = new Object[" ".length()];
                array["".length()] = GameSettings.getKeyDisplayString(gameSettings.keyBindSneak.getKeyCode());
                ingameGUI.setRecordPlaying(I18n.format(s, array), "".length() != 0);
                "".length();
                if (0 == 4) {
                    throw null;
                }
            }
        }
        else if (s1BPacketEntityAttach.getLeash() == " ".length() && entity instanceof EntityLiving) {
            if (entityByID != null) {
                ((EntityLiving)entity).setLeashedToEntity(entityByID, "".length() != 0);
                "".length();
                if (4 == 3) {
                    throw null;
                }
            }
            else {
                ((EntityLiving)entity).clearLeashed("".length() != 0, "".length() != 0);
            }
        }
    }
    
    @Override
    public void handleCloseWindow(final S2EPacketCloseWindow s2EPacketCloseWindow) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayClient>)s2EPacketCloseWindow, this, this.gameController);
        this.gameController.thePlayer.closeScreenAndDropStack();
    }
    
    static int[] $SWITCH_TABLE$net$minecraft$network$play$server$S38PacketPlayerListItem$Action() {
        final int[] $switch_TABLE$net$minecraft$network$play$server$S38PacketPlayerListItem$Action = NetHandlerPlayClient.$SWITCH_TABLE$net$minecraft$network$play$server$S38PacketPlayerListItem$Action;
        if ($switch_TABLE$net$minecraft$network$play$server$S38PacketPlayerListItem$Action != null) {
            return $switch_TABLE$net$minecraft$network$play$server$S38PacketPlayerListItem$Action;
        }
        final int[] $switch_TABLE$net$minecraft$network$play$server$S38PacketPlayerListItem$Action2 = new int[S38PacketPlayerListItem.Action.values().length];
        try {
            $switch_TABLE$net$minecraft$network$play$server$S38PacketPlayerListItem$Action2[S38PacketPlayerListItem.Action.ADD_PLAYER.ordinal()] = " ".length();
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            $switch_TABLE$net$minecraft$network$play$server$S38PacketPlayerListItem$Action2[S38PacketPlayerListItem.Action.REMOVE_PLAYER.ordinal()] = (0x2B ^ 0x2E);
            "".length();
            if (3 <= 0) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            $switch_TABLE$net$minecraft$network$play$server$S38PacketPlayerListItem$Action2[S38PacketPlayerListItem.Action.UPDATE_DISPLAY_NAME.ordinal()] = (0x92 ^ 0x96);
            "".length();
            if (1 == -1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            $switch_TABLE$net$minecraft$network$play$server$S38PacketPlayerListItem$Action2[S38PacketPlayerListItem.Action.UPDATE_GAME_MODE.ordinal()] = "  ".length();
            "".length();
            if (1 == 2) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
        try {
            $switch_TABLE$net$minecraft$network$play$server$S38PacketPlayerListItem$Action2[S38PacketPlayerListItem.Action.UPDATE_LATENCY.ordinal()] = "   ".length();
            "".length();
            if (-1 == 1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError5) {}
        return NetHandlerPlayClient.$SWITCH_TABLE$net$minecraft$network$play$server$S38PacketPlayerListItem$Action = $switch_TABLE$net$minecraft$network$play$server$S38PacketPlayerListItem$Action2;
    }
    
    @Override
    public void handleRespawn(final S07PacketRespawn s07PacketRespawn) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayClient>)s07PacketRespawn, this, this.gameController);
        if (s07PacketRespawn.getDimensionID() != this.gameController.thePlayer.dimension) {
            this.doneLoadingTerrain = ("".length() != 0);
            (this.clientWorldController = new WorldClient(this, new WorldSettings(0L, s07PacketRespawn.getGameType(), "".length() != 0, this.gameController.theWorld.getWorldInfo().isHardcoreModeEnabled(), s07PacketRespawn.getWorldType()), s07PacketRespawn.getDimensionID(), s07PacketRespawn.getDifficulty(), this.gameController.mcProfiler)).setWorldScoreboard(this.clientWorldController.getScoreboard());
            this.gameController.loadWorld(this.clientWorldController);
            this.gameController.thePlayer.dimension = s07PacketRespawn.getDimensionID();
            this.gameController.displayGuiScreen(new GuiDownloadTerrain(this));
        }
        this.gameController.setDimensionAndSpawnPlayer(s07PacketRespawn.getDimensionID());
        this.gameController.playerController.setGameType(s07PacketRespawn.getGameType());
    }
    
    @Override
    public void handleCustomPayload(final S3FPacketCustomPayload s3FPacketCustomPayload) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayClient>)s3FPacketCustomPayload, this, this.gameController);
        if (NetHandlerPlayClient.I[0x14 ^ 0xC].equals(s3FPacketCustomPayload.getChannelName())) {
            final PacketBuffer bufferData = s3FPacketCustomPayload.getBufferData();
            try {
                final int int1 = bufferData.readInt();
                final GuiScreen currentScreen = this.gameController.currentScreen;
                if (currentScreen != null && currentScreen instanceof GuiMerchant && int1 == this.gameController.thePlayer.openContainer.windowId) {
                    ((GuiMerchant)currentScreen).getMerchant().setRecipes(MerchantRecipeList.readFromBuf(bufferData));
                    "".length();
                    if (-1 < -1) {
                        throw null;
                    }
                }
            }
            catch (IOException ex) {
                NetHandlerPlayClient.logger.error(NetHandlerPlayClient.I[0xDB ^ 0xC2], (Throwable)ex);
                bufferData.release();
                "".length();
                if (2 >= 3) {
                    throw null;
                }
                return;
            }
            finally {
                bufferData.release();
            }
            bufferData.release();
            "".length();
            if (-1 >= 2) {
                throw null;
            }
        }
        else if (NetHandlerPlayClient.I[0x17 ^ 0xD].equals(s3FPacketCustomPayload.getChannelName())) {
            this.gameController.thePlayer.setClientBrand(s3FPacketCustomPayload.getBufferData().readStringFromBuffer(8879 + 22392 - 22332 + 23828));
            "".length();
            if (3 < 3) {
                throw null;
            }
        }
        else if (NetHandlerPlayClient.I[0x63 ^ 0x78].equals(s3FPacketCustomPayload.getChannelName())) {
            final ItemStack currentEquippedItem = this.gameController.thePlayer.getCurrentEquippedItem();
            if (currentEquippedItem != null && currentEquippedItem.getItem() == Items.written_book) {
                this.gameController.displayGuiScreen(new GuiScreenBook(this.gameController.thePlayer, currentEquippedItem, (boolean)("".length() != 0)));
            }
        }
    }
    
    @Override
    public void handleSetSlot(final S2FPacketSetSlot s2FPacketSetSlot) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayClient>)s2FPacketSetSlot, this, this.gameController);
        final EntityPlayerSP thePlayer = this.gameController.thePlayer;
        if (s2FPacketSetSlot.func_149175_c() == -" ".length()) {
            thePlayer.inventory.setItemStack(s2FPacketSetSlot.func_149174_e());
            "".length();
            if (0 < 0) {
                throw null;
            }
        }
        else {
            int length = "".length();
            if (this.gameController.currentScreen instanceof GuiContainerCreative) {
                int n;
                if (((GuiContainerCreative)this.gameController.currentScreen).getSelectedTabIndex() != CreativeTabs.tabInventory.getTabIndex()) {
                    n = " ".length();
                    "".length();
                    if (2 < 0) {
                        throw null;
                    }
                }
                else {
                    n = "".length();
                }
                length = n;
            }
            if (s2FPacketSetSlot.func_149175_c() == 0 && s2FPacketSetSlot.func_149173_d() >= (0x41 ^ 0x65) && s2FPacketSetSlot.func_149173_d() < (0x48 ^ 0x65)) {
                final ItemStack stack = thePlayer.inventoryContainer.getSlot(s2FPacketSetSlot.func_149173_d()).getStack();
                if (s2FPacketSetSlot.func_149174_e() != null && (stack == null || stack.stackSize < s2FPacketSetSlot.func_149174_e().stackSize)) {
                    s2FPacketSetSlot.func_149174_e().animationsToGo = (0xB3 ^ 0xB6);
                }
                thePlayer.inventoryContainer.putStackInSlot(s2FPacketSetSlot.func_149173_d(), s2FPacketSetSlot.func_149174_e());
                "".length();
                if (3 != 3) {
                    throw null;
                }
            }
            else if (s2FPacketSetSlot.func_149175_c() == thePlayer.openContainer.windowId && (s2FPacketSetSlot.func_149175_c() != 0 || length == 0)) {
                thePlayer.openContainer.putStackInSlot(s2FPacketSetSlot.func_149173_d(), s2FPacketSetSlot.func_149174_e());
            }
        }
    }
    
    @Override
    public void handleParticles(final S2APacketParticles s2APacketParticles) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayClient>)s2APacketParticles, this, this.gameController);
        if (s2APacketParticles.getParticleCount() == 0) {
            final double n = s2APacketParticles.getParticleSpeed() * s2APacketParticles.getXOffset();
            final double n2 = s2APacketParticles.getParticleSpeed() * s2APacketParticles.getYOffset();
            final double n3 = s2APacketParticles.getParticleSpeed() * s2APacketParticles.getZOffset();
            try {
                this.clientWorldController.spawnParticle(s2APacketParticles.getParticleType(), s2APacketParticles.isLongDistance(), s2APacketParticles.getXCoordinate(), s2APacketParticles.getYCoordinate(), s2APacketParticles.getZCoordinate(), n, n2, n3, s2APacketParticles.getParticleArgs());
                "".length();
                if (true != true) {
                    throw null;
                }
                return;
            }
            catch (Throwable t) {
                NetHandlerPlayClient.logger.warn(NetHandlerPlayClient.I[0xB7 ^ 0xAB] + s2APacketParticles.getParticleType());
                "".length();
                if (0 == 4) {
                    throw null;
                }
                return;
            }
        }
        int i = "".length();
        "".length();
        if (-1 != -1) {
            throw null;
        }
        while (i < s2APacketParticles.getParticleCount()) {
            final double n4 = this.avRandomizer.nextGaussian() * s2APacketParticles.getXOffset();
            final double n5 = this.avRandomizer.nextGaussian() * s2APacketParticles.getYOffset();
            final double n6 = this.avRandomizer.nextGaussian() * s2APacketParticles.getZOffset();
            final double n7 = this.avRandomizer.nextGaussian() * s2APacketParticles.getParticleSpeed();
            final double n8 = this.avRandomizer.nextGaussian() * s2APacketParticles.getParticleSpeed();
            final double n9 = this.avRandomizer.nextGaussian() * s2APacketParticles.getParticleSpeed();
            try {
                this.clientWorldController.spawnParticle(s2APacketParticles.getParticleType(), s2APacketParticles.isLongDistance(), s2APacketParticles.getXCoordinate() + n4, s2APacketParticles.getYCoordinate() + n5, s2APacketParticles.getZCoordinate() + n6, n7, n8, n9, s2APacketParticles.getParticleArgs());
                "".length();
                if (3 <= 0) {
                    throw null;
                }
            }
            catch (Throwable t2) {
                NetHandlerPlayClient.logger.warn(NetHandlerPlayClient.I[0xB ^ 0x16] + s2APacketParticles.getParticleType());
                return;
            }
            ++i;
        }
    }
    
    @Override
    public void handleEntityVelocity(final S12PacketEntityVelocity s12PacketEntityVelocity) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayClient>)s12PacketEntityVelocity, this, this.gameController);
        final Entity entityByID = this.clientWorldController.getEntityByID(s12PacketEntityVelocity.getEntityID());
        if (entityByID != null) {
            entityByID.setVelocity(s12PacketEntityVelocity.getMotionX() / 8000.0, s12PacketEntityVelocity.getMotionY() / 8000.0, s12PacketEntityVelocity.getMotionZ() / 8000.0);
        }
    }
    
    static void access$4(final NetHandlerPlayClient netHandlerPlayClient, final Minecraft gameController) {
        netHandlerPlayClient.gameController = gameController;
    }
    
    @Override
    public void handleEntityStatus(final S19PacketEntityStatus s19PacketEntityStatus) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayClient>)s19PacketEntityStatus, this, this.gameController);
        final Entity entity = s19PacketEntityStatus.getEntity(this.clientWorldController);
        if (entity != null) {
            if (s19PacketEntityStatus.getOpCode() == (0x2 ^ 0x17)) {
                this.gameController.getSoundHandler().playSound(new GuardianSound((EntityGuardian)entity));
                "".length();
                if (2 != 2) {
                    throw null;
                }
            }
            else {
                entity.handleStatusUpdate(s19PacketEntityStatus.getOpCode());
            }
        }
    }
    
    @Override
    public void handleJoinGame(final S01PacketJoinGame s01PacketJoinGame) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayClient>)s01PacketJoinGame, this, this.gameController);
        this.gameController.playerController = new PlayerControllerMP(this.gameController, this);
        this.clientWorldController = new WorldClient(this, new WorldSettings(0L, s01PacketJoinGame.getGameType(), "".length() != 0, s01PacketJoinGame.isHardcoreMode(), s01PacketJoinGame.getWorldType()), s01PacketJoinGame.getDimension(), s01PacketJoinGame.getDifficulty(), this.gameController.mcProfiler);
        this.gameController.gameSettings.difficulty = s01PacketJoinGame.getDifficulty();
        this.gameController.loadWorld(this.clientWorldController);
        this.gameController.thePlayer.dimension = s01PacketJoinGame.getDimension();
        this.gameController.displayGuiScreen(new GuiDownloadTerrain(this));
        this.gameController.thePlayer.setEntityId(s01PacketJoinGame.getEntityId());
        this.currentServerMaxPlayers = s01PacketJoinGame.getMaxPlayers();
        this.gameController.thePlayer.setReducedDebug(s01PacketJoinGame.isReducedDebugInfo());
        this.gameController.playerController.setGameType(s01PacketJoinGame.getGameType());
        this.gameController.gameSettings.sendSettingsToServer();
        this.netManager.sendPacket(new C17PacketCustomPayload(NetHandlerPlayClient.I["".length()], new PacketBuffer(Unpooled.buffer()).writeString(ClientBrandRetriever.getClientModName())));
    }
    
    static Minecraft access$3(final NetHandlerPlayClient netHandlerPlayClient) {
        return netHandlerPlayClient.gameController;
    }
    
    @Override
    public void handlePlayerAbilities(final S39PacketPlayerAbilities s39PacketPlayerAbilities) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayClient>)s39PacketPlayerAbilities, this, this.gameController);
        final EntityPlayerSP thePlayer = this.gameController.thePlayer;
        thePlayer.capabilities.isFlying = s39PacketPlayerAbilities.isFlying();
        thePlayer.capabilities.isCreativeMode = s39PacketPlayerAbilities.isCreativeMode();
        thePlayer.capabilities.disableDamage = s39PacketPlayerAbilities.isInvulnerable();
        thePlayer.capabilities.allowFlying = s39PacketPlayerAbilities.isAllowFlying();
        thePlayer.capabilities.setFlySpeed(s39PacketPlayerAbilities.getFlySpeed());
        thePlayer.capabilities.setPlayerWalkSpeed(s39PacketPlayerAbilities.getWalkSpeed());
    }
    
    static int[] $SWITCH_TABLE$net$minecraft$network$play$server$S45PacketTitle$Type() {
        final int[] $switch_TABLE$net$minecraft$network$play$server$S45PacketTitle$Type = NetHandlerPlayClient.$SWITCH_TABLE$net$minecraft$network$play$server$S45PacketTitle$Type;
        if ($switch_TABLE$net$minecraft$network$play$server$S45PacketTitle$Type != null) {
            return $switch_TABLE$net$minecraft$network$play$server$S45PacketTitle$Type;
        }
        final int[] $switch_TABLE$net$minecraft$network$play$server$S45PacketTitle$Type2 = new int[S45PacketTitle.Type.values().length];
        try {
            $switch_TABLE$net$minecraft$network$play$server$S45PacketTitle$Type2[S45PacketTitle.Type.CLEAR.ordinal()] = (0x13 ^ 0x17);
            "".length();
            if (false) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            $switch_TABLE$net$minecraft$network$play$server$S45PacketTitle$Type2[S45PacketTitle.Type.RESET.ordinal()] = (0x95 ^ 0x90);
            "".length();
            if (0 < -1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            $switch_TABLE$net$minecraft$network$play$server$S45PacketTitle$Type2[S45PacketTitle.Type.SUBTITLE.ordinal()] = "  ".length();
            "".length();
            if (2 <= -1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            $switch_TABLE$net$minecraft$network$play$server$S45PacketTitle$Type2[S45PacketTitle.Type.TIMES.ordinal()] = "   ".length();
            "".length();
            if (1 < 0) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
        try {
            $switch_TABLE$net$minecraft$network$play$server$S45PacketTitle$Type2[S45PacketTitle.Type.TITLE.ordinal()] = " ".length();
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError5) {}
        return NetHandlerPlayClient.$SWITCH_TABLE$net$minecraft$network$play$server$S45PacketTitle$Type = $switch_TABLE$net$minecraft$network$play$server$S45PacketTitle$Type2;
    }
    
    private static void I() {
        (I = new String[0x1B ^ 0x3B])["".length()] = I("\u001d\u0001>;\u001e1,&", "PBByl");
        NetHandlerPlayClient.I[" ".length()] = I("\u001e$#7\u0016\u0014#57\rT!?'\r", "zMPTy");
        NetHandlerPlayClient.I["  ".length()] = I("\u0014\n\u0002\n>\u001e\r\u0014\n%^\u000f\u001e\u001a%", "pcqiQ");
        NetHandlerPlayClient.I["   ".length()] = I("/\u0006\u0007'?%\u0001\u0011'$e\u0003\u001b7$", "KotDP");
        NetHandlerPlayClient.I[0x7D ^ 0x79] = I("\u001c7\r\u00039\u0003x\f\u00154", "nVcgV");
        NetHandlerPlayClient.I[0x76 ^ 0x73] = I("\u0017.\b\"5\ba\u0016)*", "eOfFZ");
        NetHandlerPlayClient.I[0x66 ^ 0x60] = I("\u001b\f,/\u001bX\f7#\u0000\u0017\u0011=", "vcYAo");
        NetHandlerPlayClient.I[0x49 ^ 0x4E] = I("\u001b1\u0004/4\u00049\f>m\u00157\u0004>6\u001f6\u000f8", "vXjJW");
        NetHandlerPlayClient.I[0x21 ^ 0x29] = I("\u00050\f\u0011-\u001a8\u0004\u0000t\u001e0\u000e\u0018/\u000f<\u0010", "hYbtN");
        NetHandlerPlayClient.I[0x16 ^ 0x1F] = I("'+\u0013\u000e%\u001b\r\b\u0015\"\u0007", "bEggQ");
        NetHandlerPlayClient.I[0x17 ^ 0x1D] = I("\r\t\t\u001a\b=G\u001c\u0017D4\b\u000b\u0019\u0010=G\u001b\u0011\u00036G\t\fD", "Xghxd");
        NetHandlerPlayClient.I[0x6D ^ 0x66] = I("@b", "lBJEI");
        NetHandlerPlayClient.I[0x98 ^ 0x94] = I("Mx", "aXCEH");
        NetHandlerPlayClient.I[0x6D ^ 0x60] = I("6\u001d+\u0016k:\u001d*\tk?\u00170\u001c(7\u00162", "RxFyE");
        NetHandlerPlayClient.I[0x53 ^ 0x5D] = I("4\u001d=\u000bZ8\u001d<\u0014Z:\r=\u0014", "PxPdt");
        NetHandlerPlayClient.I[0x46 ^ 0x49] = I("\u0003#)\u0001j\u000f#(\u001ej\u000e(2\u000b*\u0013)6\u0017", "gFDnD");
        NetHandlerPlayClient.I[0x92 ^ 0x82] = I("+\u000e\u00185\u00064A\u0005$\n:\n\u0005\"\u000f,\u0003)9\u0000-", "YovQi");
        NetHandlerPlayClient.I[0x17 ^ 0x6] = I("?%#_\u0017'+3\u0015\u00193$o\u0012\u0005 9$", "RJAqp");
        NetHandlerPlayClient.I[0x2C ^ 0x3E] = I("", "bNLCZ");
        NetHandlerPlayClient.I[0x93 ^ 0x80] = I("", "NVkyM");
        NetHandlerPlayClient.I[0x44 ^ 0x50] = I("", "nYHOB");
        NetHandlerPlayClient.I[0x31 ^ 0x24] = I("6\n3,\u001d`@j", "ZoEIq");
        NetHandlerPlayClient.I[0xAC ^ 0xBA] = I("\u0003/'\u0004%Ue~", "oJQaI");
        NetHandlerPlayClient.I[0x4B ^ 0x5C] = I("\u001d\u001b\u0007\r&", "nzqhU");
        NetHandlerPlayClient.I[0xBA ^ 0xA2] = I("\u0003/&!\u0005\u0002\u0005)\u0001", "NlZuw");
        NetHandlerPlayClient.I[0x4 ^ 0x1D] = I("\u00109\r*\u0003=q\ff\u000b<7\u001cf\u0013!7\u001c#G:8\u001e)", "SVxFg");
        NetHandlerPlayClient.I[0x84 ^ 0x9E] = I("\u001b\u001a='#77%", "VYAeQ");
        NetHandlerPlayClient.I[0x23 ^ 0x38] = I("=\"0/9\u0000\u0004\"", "paLmv");
        NetHandlerPlayClient.I[0x3F ^ 0x23] = I("37\u001a\r\u0011P6\u0000\u0015U\u0003(\u000e\u0016\u001bP(\u000e\u0013\u0001\u0019;\u0003\u0004U\u0015>\t\u0004\u0016\u0004x", "pXoau");
        NetHandlerPlayClient.I[0x7E ^ 0x63] = I("'#\u0012?'D\"\b'c\u0017<\u0006$-D<\u0006!7\r/\u000b6c\u0001*\u00016 \u0010l", "dLgSC");
        NetHandlerPlayClient.I[0x7C ^ 0x62] = I("#\f\u001b1?\u0002I\u001d53\u0015\rI35P\u001c\u0019#;\u0004\fI&.\u0004\u001b\u0000%/\u0004\f\u001ag5\u0016I\bg4\u001f\u0007D+3\u0006\u0000\u0007 z\u0015\u0007\u001d..\tIA&9\u0004\u001c\b+6\tSI", "piiGZ");
        NetHandlerPlayClient.I[0x21 ^ 0x3E] = I("}", "TpLlm");
    }
    
    @Override
    public void handleAnimation(final S0BPacketAnimation s0BPacketAnimation) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayClient>)s0BPacketAnimation, this, this.gameController);
        final Entity entityByID = this.clientWorldController.getEntityByID(s0BPacketAnimation.getEntityID());
        if (entityByID != null) {
            if (s0BPacketAnimation.getAnimationType() == 0) {
                ((EntityPlayer)entityByID).swingItem();
                "".length();
                if (0 >= 3) {
                    throw null;
                }
            }
            else if (s0BPacketAnimation.getAnimationType() == " ".length()) {
                entityByID.performHurtAnimation();
                "".length();
                if (2 == 0) {
                    throw null;
                }
            }
            else if (s0BPacketAnimation.getAnimationType() == "  ".length()) {
                ((EntityPlayer)entityByID).wakeUpPlayer("".length() != 0, "".length() != 0, "".length() != 0);
                "".length();
                if (2 != 2) {
                    throw null;
                }
            }
            else if (s0BPacketAnimation.getAnimationType() == (0x86 ^ 0x82)) {
                this.gameController.effectRenderer.emitParticleAtEntity(entityByID, EnumParticleTypes.CRIT);
                "".length();
                if (true != true) {
                    throw null;
                }
            }
            else if (s0BPacketAnimation.getAnimationType() == (0x89 ^ 0x8C)) {
                this.gameController.effectRenderer.emitParticleAtEntity(entityByID, EnumParticleTypes.CRIT_MAGIC);
            }
        }
    }
    
    @Override
    public void handleBlockChange(final S23PacketBlockChange s23PacketBlockChange) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayClient>)s23PacketBlockChange, this, this.gameController);
        this.clientWorldController.invalidateRegionAndSetBlock(s23PacketBlockChange.getBlockPosition(), s23PacketBlockChange.getBlockState());
    }
    
    static NetworkManager access$2(final NetHandlerPlayClient netHandlerPlayClient) {
        return netHandlerPlayClient.netManager;
    }
    
    @Override
    public void handlePlayerListHeaderFooter(final S47PacketPlayerListHeaderFooter s47PacketPlayerListHeaderFooter) {
        final GuiPlayerTabOverlay tabList = this.gameController.ingameGUI.getTabList();
        IChatComponent header;
        if (s47PacketPlayerListHeaderFooter.getHeader().getFormattedText().length() == 0) {
            header = null;
            "".length();
            if (2 == -1) {
                throw null;
            }
        }
        else {
            header = s47PacketPlayerListHeaderFooter.getHeader();
        }
        tabList.setHeader(header);
        final GuiPlayerTabOverlay tabList2 = this.gameController.ingameGUI.getTabList();
        IChatComponent footer;
        if (s47PacketPlayerListHeaderFooter.getFooter().getFormattedText().length() == 0) {
            footer = null;
            "".length();
            if (3 <= 2) {
                throw null;
            }
        }
        else {
            footer = s47PacketPlayerListHeaderFooter.getFooter();
        }
        tabList2.setFooter(footer);
    }
    
    @Override
    public void handleBlockAction(final S24PacketBlockAction s24PacketBlockAction) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayClient>)s24PacketBlockAction, this, this.gameController);
        this.gameController.theWorld.addBlockEvent(s24PacketBlockAction.getBlockPosition(), s24PacketBlockAction.getBlockType(), s24PacketBlockAction.getData1(), s24PacketBlockAction.getData2());
    }
    
    @Override
    public void handleChangeGameState(final S2BPacketChangeGameState s2BPacketChangeGameState) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayClient>)s2BPacketChangeGameState, this, this.gameController);
        final EntityPlayerSP thePlayer = this.gameController.thePlayer;
        final int gameState = s2BPacketChangeGameState.getGameState();
        final float func_149137_d = s2BPacketChangeGameState.func_149137_d();
        final int floor_float = MathHelper.floor_float(func_149137_d + 0.5f);
        if (gameState >= 0 && gameState < S2BPacketChangeGameState.MESSAGE_NAMES.length && S2BPacketChangeGameState.MESSAGE_NAMES[gameState] != null) {
            thePlayer.addChatComponentMessage(new ChatComponentTranslation(S2BPacketChangeGameState.MESSAGE_NAMES[gameState], new Object["".length()]));
        }
        if (gameState == " ".length()) {
            this.clientWorldController.getWorldInfo().setRaining(" ".length() != 0);
            this.clientWorldController.setRainStrength(0.0f);
            "".length();
            if (1 >= 3) {
                throw null;
            }
        }
        else if (gameState == "  ".length()) {
            this.clientWorldController.getWorldInfo().setRaining("".length() != 0);
            this.clientWorldController.setRainStrength(1.0f);
            "".length();
            if (-1 >= 1) {
                throw null;
            }
        }
        else if (gameState == "   ".length()) {
            this.gameController.playerController.setGameType(WorldSettings.GameType.getByID(floor_float));
            "".length();
            if (1 >= 2) {
                throw null;
            }
        }
        else if (gameState == (0x1E ^ 0x1A)) {
            this.gameController.displayGuiScreen(new GuiWinGame());
            "".length();
            if (3 == 4) {
                throw null;
            }
        }
        else if (gameState == (0xB7 ^ 0xB2)) {
            final GameSettings gameSettings = this.gameController.gameSettings;
            if (func_149137_d == 0.0f) {
                this.gameController.displayGuiScreen(new GuiScreenDemo());
                "".length();
                if (2 != 2) {
                    throw null;
                }
            }
            else if (func_149137_d == 101.0f) {
                final GuiNewChat chatGUI = this.gameController.ingameGUI.getChatGUI();
                final String s = NetHandlerPlayClient.I[0x72 ^ 0x7F];
                final Object[] array = new Object[0xA ^ 0xE];
                array["".length()] = GameSettings.getKeyDisplayString(gameSettings.keyBindForward.getKeyCode());
                array[" ".length()] = GameSettings.getKeyDisplayString(gameSettings.keyBindLeft.getKeyCode());
                array["  ".length()] = GameSettings.getKeyDisplayString(gameSettings.keyBindBack.getKeyCode());
                array["   ".length()] = GameSettings.getKeyDisplayString(gameSettings.keyBindRight.getKeyCode());
                chatGUI.printChatMessage(new ChatComponentTranslation(s, array));
                "".length();
                if (4 <= -1) {
                    throw null;
                }
            }
            else if (func_149137_d == 102.0f) {
                final GuiNewChat chatGUI2 = this.gameController.ingameGUI.getChatGUI();
                final String s2 = NetHandlerPlayClient.I[0x3D ^ 0x33];
                final Object[] array2 = new Object[" ".length()];
                array2["".length()] = GameSettings.getKeyDisplayString(gameSettings.keyBindJump.getKeyCode());
                chatGUI2.printChatMessage(new ChatComponentTranslation(s2, array2));
                "".length();
                if (-1 >= 4) {
                    throw null;
                }
            }
            else if (func_149137_d == 103.0f) {
                final GuiNewChat chatGUI3 = this.gameController.ingameGUI.getChatGUI();
                final String s3 = NetHandlerPlayClient.I[0x5C ^ 0x53];
                final Object[] array3 = new Object[" ".length()];
                array3["".length()] = GameSettings.getKeyDisplayString(gameSettings.keyBindInventory.getKeyCode());
                chatGUI3.printChatMessage(new ChatComponentTranslation(s3, array3));
                "".length();
                if (4 <= 1) {
                    throw null;
                }
            }
        }
        else if (gameState == (0x22 ^ 0x24)) {
            this.clientWorldController.playSound(thePlayer.posX, thePlayer.posY + thePlayer.getEyeHeight(), thePlayer.posZ, NetHandlerPlayClient.I[0x9A ^ 0x8A], 0.18f, 0.45f, "".length() != 0);
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        else if (gameState == (0x92 ^ 0x95)) {
            this.clientWorldController.setRainStrength(func_149137_d);
            "".length();
            if (0 >= 3) {
                throw null;
            }
        }
        else if (gameState == (0x91 ^ 0x99)) {
            this.clientWorldController.setThunderStrength(func_149137_d);
            "".length();
            if (3 < 0) {
                throw null;
            }
        }
        else if (gameState == (0x14 ^ 0x1E)) {
            this.clientWorldController.spawnParticle(EnumParticleTypes.MOB_APPEARANCE, thePlayer.posX, thePlayer.posY, thePlayer.posZ, 0.0, 0.0, 0.0, new int["".length()]);
            this.clientWorldController.playSound(thePlayer.posX, thePlayer.posY, thePlayer.posZ, NetHandlerPlayClient.I[0x19 ^ 0x8], 1.0f, 1.0f, "".length() != 0);
        }
    }
    
    @Override
    public void handleUpdateScore(final S3CPacketUpdateScore s3CPacketUpdateScore) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayClient>)s3CPacketUpdateScore, this, this.gameController);
        final Scoreboard scoreboard = this.clientWorldController.getScoreboard();
        final ScoreObjective objective = scoreboard.getObjective(s3CPacketUpdateScore.getObjectiveName());
        if (s3CPacketUpdateScore.getScoreAction() == S3CPacketUpdateScore.Action.CHANGE) {
            scoreboard.getValueFromObjective(s3CPacketUpdateScore.getPlayerName(), objective).setScorePoints(s3CPacketUpdateScore.getScoreValue());
            "".length();
            if (4 < 4) {
                throw null;
            }
        }
        else if (s3CPacketUpdateScore.getScoreAction() == S3CPacketUpdateScore.Action.REMOVE) {
            if (StringUtils.isNullOrEmpty(s3CPacketUpdateScore.getObjectiveName())) {
                scoreboard.removeObjectiveFromEntity(s3CPacketUpdateScore.getPlayerName(), null);
                "".length();
                if (-1 != -1) {
                    throw null;
                }
            }
            else if (objective != null) {
                scoreboard.removeObjectiveFromEntity(s3CPacketUpdateScore.getPlayerName(), objective);
            }
        }
    }
    
    @Override
    public void handleCollectItem(final S0DPacketCollectItem s0DPacketCollectItem) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayClient>)s0DPacketCollectItem, this, this.gameController);
        final Entity entityByID = this.clientWorldController.getEntityByID(s0DPacketCollectItem.getCollectedItemEntityID());
        EntityLivingBase thePlayer = (EntityLivingBase)this.clientWorldController.getEntityByID(s0DPacketCollectItem.getEntityID());
        if (thePlayer == null) {
            thePlayer = this.gameController.thePlayer;
        }
        if (entityByID != null) {
            if (entityByID instanceof EntityXPOrb) {
                this.clientWorldController.playSoundAtEntity(entityByID, NetHandlerPlayClient.I[0xA ^ 0xE], 0.2f, ((this.avRandomizer.nextFloat() - this.avRandomizer.nextFloat()) * 0.7f + 1.0f) * 2.0f);
                "".length();
                if (1 <= 0) {
                    throw null;
                }
            }
            else {
                this.clientWorldController.playSoundAtEntity(entityByID, NetHandlerPlayClient.I[0x98 ^ 0x9D], 0.2f, ((this.avRandomizer.nextFloat() - this.avRandomizer.nextFloat()) * 0.7f + 1.0f) * 2.0f);
            }
            this.gameController.effectRenderer.addEffect(new EntityPickupFX(this.clientWorldController, entityByID, thePlayer, 0.5f));
            this.clientWorldController.removeEntityFromWorld(s0DPacketCollectItem.getCollectedItemEntityID());
        }
    }
    
    @Override
    public void handleDisconnect(final S40PacketDisconnect s40PacketDisconnect) {
        this.netManager.closeChannel(s40PacketDisconnect.getReason());
    }
    
    @Override
    public void handleScoreboardObjective(final S3BPacketScoreboardObjective s3BPacketScoreboardObjective) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayClient>)s3BPacketScoreboardObjective, this, this.gameController);
        final Scoreboard scoreboard = this.clientWorldController.getScoreboard();
        if (s3BPacketScoreboardObjective.func_149338_e() == 0) {
            final ScoreObjective addScoreObjective = scoreboard.addScoreObjective(s3BPacketScoreboardObjective.func_149339_c(), IScoreObjectiveCriteria.DUMMY);
            addScoreObjective.setDisplayName(s3BPacketScoreboardObjective.func_149337_d());
            addScoreObjective.setRenderType(s3BPacketScoreboardObjective.func_179817_d());
            "".length();
            if (1 < 0) {
                throw null;
            }
        }
        else {
            final ScoreObjective objective = scoreboard.getObjective(s3BPacketScoreboardObjective.func_149339_c());
            if (s3BPacketScoreboardObjective.func_149338_e() == " ".length()) {
                scoreboard.removeObjective(objective);
                "".length();
                if (4 <= 3) {
                    throw null;
                }
            }
            else if (s3BPacketScoreboardObjective.func_149338_e() == "  ".length()) {
                objective.setDisplayName(s3BPacketScoreboardObjective.func_149337_d());
                objective.setRenderType(s3BPacketScoreboardObjective.func_179817_d());
            }
        }
    }
    
    @Override
    public void handleCamera(final S43PacketCamera s43PacketCamera) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayClient>)s43PacketCamera, this, this.gameController);
        final Entity entity = s43PacketCamera.getEntity(this.clientWorldController);
        if (entity != null) {
            this.gameController.setRenderViewEntity(entity);
        }
    }
    
    public void cleanup() {
        this.clientWorldController = null;
    }
    
    @Override
    public void handleSpawnPlayer(final S0CPacketSpawnPlayer s0CPacketSpawnPlayer) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayClient>)s0CPacketSpawnPlayer, this, this.gameController);
        final double n = s0CPacketSpawnPlayer.getX() / 32.0;
        final double n2 = s0CPacketSpawnPlayer.getY() / 32.0;
        final double n3 = s0CPacketSpawnPlayer.getZ() / 32.0;
        final float n4 = s0CPacketSpawnPlayer.getYaw() * (66 + 124 - 8 + 178) / 256.0f;
        final float n5 = s0CPacketSpawnPlayer.getPitch() * (208 + 214 - 375 + 313) / 256.0f;
        final EntityOtherPlayerMP entityOtherPlayerMP4;
        final EntityOtherPlayerMP entityOtherPlayerMP3;
        final EntityOtherPlayerMP entityOtherPlayerMP2;
        final EntityOtherPlayerMP entityOtherPlayerMP = entityOtherPlayerMP2 = (entityOtherPlayerMP3 = (entityOtherPlayerMP4 = new EntityOtherPlayerMP(this.gameController.theWorld, this.getPlayerInfo(s0CPacketSpawnPlayer.getPlayer()).getGameProfile())));
        final int x = s0CPacketSpawnPlayer.getX();
        entityOtherPlayerMP2.serverPosX = x;
        final double n6 = x;
        entityOtherPlayerMP3.lastTickPosX = n6;
        entityOtherPlayerMP4.prevPosX = n6;
        final EntityOtherPlayerMP entityOtherPlayerMP5 = entityOtherPlayerMP;
        final EntityOtherPlayerMP entityOtherPlayerMP6 = entityOtherPlayerMP;
        final EntityOtherPlayerMP entityOtherPlayerMP7 = entityOtherPlayerMP;
        final int y = s0CPacketSpawnPlayer.getY();
        entityOtherPlayerMP7.serverPosY = y;
        final double n7 = y;
        entityOtherPlayerMP6.lastTickPosY = n7;
        entityOtherPlayerMP5.prevPosY = n7;
        final EntityOtherPlayerMP entityOtherPlayerMP8 = entityOtherPlayerMP;
        final EntityOtherPlayerMP entityOtherPlayerMP9 = entityOtherPlayerMP;
        final EntityOtherPlayerMP entityOtherPlayerMP10 = entityOtherPlayerMP;
        final int z = s0CPacketSpawnPlayer.getZ();
        entityOtherPlayerMP10.serverPosZ = z;
        final double n8 = z;
        entityOtherPlayerMP9.lastTickPosZ = n8;
        entityOtherPlayerMP8.prevPosZ = n8;
        final int currentItemID = s0CPacketSpawnPlayer.getCurrentItemID();
        if (currentItemID == 0) {
            entityOtherPlayerMP.inventory.mainInventory[entityOtherPlayerMP.inventory.currentItem] = null;
            "".length();
            if (0 >= 1) {
                throw null;
            }
        }
        else {
            entityOtherPlayerMP.inventory.mainInventory[entityOtherPlayerMP.inventory.currentItem] = new ItemStack(Item.getItemById(currentItemID), " ".length(), "".length());
        }
        entityOtherPlayerMP.setPositionAndRotation(n, n2, n3, n4, n5);
        this.clientWorldController.addEntityToWorld(s0CPacketSpawnPlayer.getEntityID(), entityOtherPlayerMP);
        final List<DataWatcher.WatchableObject> func_148944_c = s0CPacketSpawnPlayer.func_148944_c();
        if (func_148944_c != null) {
            entityOtherPlayerMP.getDataWatcher().updateWatchedObjectsFromList(func_148944_c);
        }
    }
    
    @Override
    public void handleSpawnPosition(final S05PacketSpawnPosition s05PacketSpawnPosition) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayClient>)s05PacketSpawnPosition, this, this.gameController);
        this.gameController.thePlayer.setSpawnPoint(s05PacketSpawnPosition.getSpawnPos(), " ".length() != 0);
        this.gameController.theWorld.getWorldInfo().setSpawn(s05PacketSpawnPosition.getSpawnPos());
    }
    
    @Override
    public void handleChat(final S02PacketChat s02PacketChat) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayClient>)s02PacketChat, this, this.gameController);
        if (s02PacketChat.getType() == "  ".length()) {
            this.gameController.ingameGUI.setRecordPlaying(s02PacketChat.getChatComponent(), "".length() != 0);
            "".length();
            if (0 >= 3) {
                throw null;
            }
        }
        else {
            this.gameController.ingameGUI.getChatGUI().printChatMessage(s02PacketChat.getChatComponent());
        }
    }
    
    @Override
    public void handleTitle(final S45PacketTitle s45PacketTitle) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayClient>)s45PacketTitle, this, this.gameController);
        final S45PacketTitle.Type type = s45PacketTitle.getType();
        String s = null;
        String s2 = null;
        String formattedText;
        if (s45PacketTitle.getMessage() != null) {
            formattedText = s45PacketTitle.getMessage().getFormattedText();
            "".length();
            if (3 <= 0) {
                throw null;
            }
        }
        else {
            formattedText = NetHandlerPlayClient.I[0x6F ^ 0x7D];
        }
        final String s3 = formattedText;
        switch ($SWITCH_TABLE$net$minecraft$network$play$server$S45PacketTitle$Type()[type.ordinal()]) {
            case 1: {
                s = s3;
                "".length();
                if (1 < 1) {
                    throw null;
                }
                break;
            }
            case 2: {
                s2 = s3;
                "".length();
                if (4 == 1) {
                    throw null;
                }
                break;
            }
            case 5: {
                this.gameController.ingameGUI.displayTitle(NetHandlerPlayClient.I[0x6C ^ 0x7F], NetHandlerPlayClient.I[0x3A ^ 0x2E], -" ".length(), -" ".length(), -" ".length());
                this.gameController.ingameGUI.func_175177_a();
                return;
            }
        }
        this.gameController.ingameGUI.displayTitle(s, s2, s45PacketTitle.getFadeInTime(), s45PacketTitle.getDisplayTime(), s45PacketTitle.getFadeOutTime());
    }
    
    public Collection<NetworkPlayerInfo> getPlayerInfoMap() {
        return this.playerInfoMap.values();
    }
    
    @Override
    public void handleMaps(final S34PacketMaps s34PacketMaps) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayClient>)s34PacketMaps, this, this.gameController);
        final MapData loadMapData = ItemMap.loadMapData(s34PacketMaps.getMapId(), this.gameController.theWorld);
        s34PacketMaps.setMapdataTo(loadMapData);
        this.gameController.entityRenderer.getMapItemRenderer().updateMapTexture(loadMapData);
    }
    
    @Override
    public void handleServerDifficulty(final S41PacketServerDifficulty s41PacketServerDifficulty) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayClient>)s41PacketServerDifficulty, this, this.gameController);
        this.gameController.theWorld.getWorldInfo().setDifficulty(s41PacketServerDifficulty.getDifficulty());
        this.gameController.theWorld.getWorldInfo().setDifficultyLocked(s41PacketServerDifficulty.isDifficultyLocked());
    }
    
    @Override
    public void handleStatistics(final S37PacketStatistics s37PacketStatistics) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayClient>)s37PacketStatistics, this, this.gameController);
        int n = "".length();
        final Iterator<Map.Entry<StatBase, Integer>> iterator = s37PacketStatistics.func_148974_c().entrySet().iterator();
        "".length();
        if (3 <= -1) {
            throw null;
        }
        while (iterator.hasNext()) {
            final Map.Entry<StatBase, Integer> entry = iterator.next();
            final StatBase statBase = entry.getKey();
            final int intValue = entry.getValue();
            if (statBase.isAchievement() && intValue > 0) {
                if (this.field_147308_k && this.gameController.thePlayer.getStatFileWriter().readStat(statBase) == 0) {
                    final Achievement achievement = (Achievement)statBase;
                    this.gameController.guiAchievement.displayAchievement(achievement);
                    this.gameController.getTwitchStream().func_152911_a(new MetadataAchievement(achievement), 0L);
                    if (statBase == AchievementList.openInventory) {
                        this.gameController.gameSettings.showInventoryAchievementHint = ("".length() != 0);
                        this.gameController.gameSettings.saveOptions();
                    }
                }
                n = " ".length();
            }
            this.gameController.thePlayer.getStatFileWriter().unlockAchievement(this.gameController.thePlayer, statBase, intValue);
        }
        if (!this.field_147308_k && n == 0 && this.gameController.gameSettings.showInventoryAchievementHint) {
            this.gameController.guiAchievement.displayUnformattedAchievement(AchievementList.openInventory);
        }
        this.field_147308_k = (" ".length() != 0);
        if (this.gameController.currentScreen instanceof IProgressMeter) {
            ((IProgressMeter)this.gameController.currentScreen).doneLoading();
        }
    }
    
    @Override
    public void handleTabComplete(final S3APacketTabComplete s3APacketTabComplete) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayClient>)s3APacketTabComplete, this, this.gameController);
        final String[] func_149630_c = s3APacketTabComplete.func_149630_c();
        if (this.gameController.currentScreen instanceof GuiChat) {
            ((GuiChat)this.gameController.currentScreen).onAutocompleteResponse(func_149630_c);
        }
    }
    
    @Override
    public void handleConfirmTransaction(final S32PacketConfirmTransaction s32PacketConfirmTransaction) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayClient>)s32PacketConfirmTransaction, this, this.gameController);
        Container container = null;
        final EntityPlayerSP thePlayer = this.gameController.thePlayer;
        if (s32PacketConfirmTransaction.getWindowId() == 0) {
            container = thePlayer.inventoryContainer;
            "".length();
            if (-1 >= 1) {
                throw null;
            }
        }
        else if (s32PacketConfirmTransaction.getWindowId() == thePlayer.openContainer.windowId) {
            container = thePlayer.openContainer;
        }
        if (container != null && !s32PacketConfirmTransaction.func_148888_e()) {
            this.addToSendQueue(new C0FPacketConfirmTransaction(s32PacketConfirmTransaction.getWindowId(), s32PacketConfirmTransaction.getActionNumber(), (boolean)(" ".length() != 0)));
        }
    }
    
    @Override
    public void handleSpawnObject(final S0EPacketSpawnObject s0EPacketSpawnObject) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayClient>)s0EPacketSpawnObject, this, this.gameController);
        final double n = s0EPacketSpawnObject.getX() / 32.0;
        final double n2 = s0EPacketSpawnObject.getY() / 32.0;
        final double n3 = s0EPacketSpawnObject.getZ() / 32.0;
        Entity func_180458_a = null;
        if (s0EPacketSpawnObject.getType() == (0xA4 ^ 0xAE)) {
            func_180458_a = EntityMinecart.func_180458_a(this.clientWorldController, n, n2, n3, EntityMinecart.EnumMinecartType.byNetworkID(s0EPacketSpawnObject.func_149009_m()));
            "".length();
            if (4 < -1) {
                throw null;
            }
        }
        else if (s0EPacketSpawnObject.getType() == (0x54 ^ 0xE)) {
            final Entity entityByID = this.clientWorldController.getEntityByID(s0EPacketSpawnObject.func_149009_m());
            if (entityByID instanceof EntityPlayer) {
                func_180458_a = new EntityFishHook(this.clientWorldController, n, n2, n3, (EntityPlayer)entityByID);
            }
            s0EPacketSpawnObject.func_149002_g("".length());
            "".length();
            if (4 <= 1) {
                throw null;
            }
        }
        else if (s0EPacketSpawnObject.getType() == (0x9B ^ 0xA7)) {
            func_180458_a = new EntityArrow(this.clientWorldController, n, n2, n3);
            "".length();
            if (3 == 0) {
                throw null;
            }
        }
        else if (s0EPacketSpawnObject.getType() == (0x4D ^ 0x70)) {
            func_180458_a = new EntitySnowball(this.clientWorldController, n, n2, n3);
            "".length();
            if (2 == 0) {
                throw null;
            }
        }
        else if (s0EPacketSpawnObject.getType() == (0x1A ^ 0x5D)) {
            func_180458_a = new EntityItemFrame(this.clientWorldController, new BlockPos(MathHelper.floor_double(n), MathHelper.floor_double(n2), MathHelper.floor_double(n3)), EnumFacing.getHorizontal(s0EPacketSpawnObject.func_149009_m()));
            s0EPacketSpawnObject.func_149002_g("".length());
            "".length();
            if (-1 >= 3) {
                throw null;
            }
        }
        else if (s0EPacketSpawnObject.getType() == (0x39 ^ 0x74)) {
            func_180458_a = new EntityLeashKnot(this.clientWorldController, new BlockPos(MathHelper.floor_double(n), MathHelper.floor_double(n2), MathHelper.floor_double(n3)));
            s0EPacketSpawnObject.func_149002_g("".length());
            "".length();
            if (1 <= 0) {
                throw null;
            }
        }
        else if (s0EPacketSpawnObject.getType() == (0xD0 ^ 0x91)) {
            func_180458_a = new EntityEnderPearl(this.clientWorldController, n, n2, n3);
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        else if (s0EPacketSpawnObject.getType() == (0x44 ^ 0xC)) {
            func_180458_a = new EntityEnderEye(this.clientWorldController, n, n2, n3);
            "".length();
            if (-1 >= 3) {
                throw null;
            }
        }
        else if (s0EPacketSpawnObject.getType() == (0xFE ^ 0xB2)) {
            func_180458_a = new EntityFireworkRocket(this.clientWorldController, n, n2, n3, null);
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        else if (s0EPacketSpawnObject.getType() == (0xA6 ^ 0x99)) {
            func_180458_a = new EntityLargeFireball(this.clientWorldController, n, n2, n3, s0EPacketSpawnObject.getSpeedX() / 8000.0, s0EPacketSpawnObject.getSpeedY() / 8000.0, s0EPacketSpawnObject.getSpeedZ() / 8000.0);
            s0EPacketSpawnObject.func_149002_g("".length());
            "".length();
            if (0 == 3) {
                throw null;
            }
        }
        else if (s0EPacketSpawnObject.getType() == (0xC4 ^ 0x84)) {
            func_180458_a = new EntitySmallFireball(this.clientWorldController, n, n2, n3, s0EPacketSpawnObject.getSpeedX() / 8000.0, s0EPacketSpawnObject.getSpeedY() / 8000.0, s0EPacketSpawnObject.getSpeedZ() / 8000.0);
            s0EPacketSpawnObject.func_149002_g("".length());
            "".length();
            if (4 < 4) {
                throw null;
            }
        }
        else if (s0EPacketSpawnObject.getType() == (0x16 ^ 0x54)) {
            func_180458_a = new EntityWitherSkull(this.clientWorldController, n, n2, n3, s0EPacketSpawnObject.getSpeedX() / 8000.0, s0EPacketSpawnObject.getSpeedY() / 8000.0, s0EPacketSpawnObject.getSpeedZ() / 8000.0);
            s0EPacketSpawnObject.func_149002_g("".length());
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        else if (s0EPacketSpawnObject.getType() == (0x5E ^ 0x60)) {
            func_180458_a = new EntityEgg(this.clientWorldController, n, n2, n3);
            "".length();
            if (3 == 1) {
                throw null;
            }
        }
        else if (s0EPacketSpawnObject.getType() == (0xD8 ^ 0x91)) {
            func_180458_a = new EntityPotion(this.clientWorldController, n, n2, n3, s0EPacketSpawnObject.func_149009_m());
            s0EPacketSpawnObject.func_149002_g("".length());
            "".length();
            if (2 < 2) {
                throw null;
            }
        }
        else if (s0EPacketSpawnObject.getType() == (0xC4 ^ 0x8F)) {
            func_180458_a = new EntityExpBottle(this.clientWorldController, n, n2, n3);
            s0EPacketSpawnObject.func_149002_g("".length());
            "".length();
            if (4 <= 2) {
                throw null;
            }
        }
        else if (s0EPacketSpawnObject.getType() == " ".length()) {
            func_180458_a = new EntityBoat(this.clientWorldController, n, n2, n3);
            "".length();
            if (3 < 2) {
                throw null;
            }
        }
        else if (s0EPacketSpawnObject.getType() == (0xBE ^ 0x8C)) {
            func_180458_a = new EntityTNTPrimed(this.clientWorldController, n, n2, n3, null);
            "".length();
            if (2 >= 4) {
                throw null;
            }
        }
        else if (s0EPacketSpawnObject.getType() == (0x0 ^ 0x4E)) {
            func_180458_a = new EntityArmorStand(this.clientWorldController, n, n2, n3);
            "".length();
            if (1 >= 3) {
                throw null;
            }
        }
        else if (s0EPacketSpawnObject.getType() == (0xD ^ 0x3E)) {
            func_180458_a = new EntityEnderCrystal(this.clientWorldController, n, n2, n3);
            "".length();
            if (-1 >= 3) {
                throw null;
            }
        }
        else if (s0EPacketSpawnObject.getType() == "  ".length()) {
            func_180458_a = new EntityItem(this.clientWorldController, n, n2, n3);
            "".length();
            if (3 == -1) {
                throw null;
            }
        }
        else if (s0EPacketSpawnObject.getType() == (0x0 ^ 0x46)) {
            func_180458_a = new EntityFallingBlock(this.clientWorldController, n, n2, n3, Block.getStateById(s0EPacketSpawnObject.func_149009_m() & 59257 + 18205 - 20776 + 8849));
            s0EPacketSpawnObject.func_149002_g("".length());
        }
        if (func_180458_a != null) {
            func_180458_a.serverPosX = s0EPacketSpawnObject.getX();
            func_180458_a.serverPosY = s0EPacketSpawnObject.getY();
            func_180458_a.serverPosZ = s0EPacketSpawnObject.getZ();
            func_180458_a.rotationPitch = s0EPacketSpawnObject.getPitch() * (280 + 358 - 480 + 202) / 256.0f;
            func_180458_a.rotationYaw = s0EPacketSpawnObject.getYaw() * (13 + 178 + 6 + 163) / 256.0f;
            final Entity[] parts = func_180458_a.getParts();
            if (parts != null) {
                final int n4 = s0EPacketSpawnObject.getEntityID() - func_180458_a.getEntityId();
                int i = "".length();
                "".length();
                if (1 >= 4) {
                    throw null;
                }
                while (i < parts.length) {
                    parts[i].setEntityId(parts[i].getEntityId() + n4);
                    ++i;
                }
            }
            func_180458_a.setEntityId(s0EPacketSpawnObject.getEntityID());
            this.clientWorldController.addEntityToWorld(s0EPacketSpawnObject.getEntityID(), func_180458_a);
            if (s0EPacketSpawnObject.func_149009_m() > 0) {
                if (s0EPacketSpawnObject.getType() == (0x93 ^ 0xAF)) {
                    final Entity entityByID2 = this.clientWorldController.getEntityByID(s0EPacketSpawnObject.func_149009_m());
                    if (entityByID2 instanceof EntityLivingBase && func_180458_a instanceof EntityArrow) {
                        ((EntityArrow)func_180458_a).shootingEntity = entityByID2;
                    }
                }
                func_180458_a.setVelocity(s0EPacketSpawnObject.getSpeedX() / 8000.0, s0EPacketSpawnObject.getSpeedY() / 8000.0, s0EPacketSpawnObject.getSpeedZ() / 8000.0);
            }
        }
    }
    
    @Override
    public void handleUpdateSign(final S33PacketUpdateSign s33PacketUpdateSign) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayClient>)s33PacketUpdateSign, this, this.gameController);
        int n = "".length();
        if (this.gameController.theWorld.isBlockLoaded(s33PacketUpdateSign.getPos())) {
            final TileEntity tileEntity = this.gameController.theWorld.getTileEntity(s33PacketUpdateSign.getPos());
            if (tileEntity instanceof TileEntitySign) {
                final TileEntitySign tileEntitySign = (TileEntitySign)tileEntity;
                if (tileEntitySign.getIsEditable()) {
                    System.arraycopy(s33PacketUpdateSign.getLines(), "".length(), tileEntitySign.signText, "".length(), 0x9E ^ 0x9A);
                    tileEntitySign.markDirty();
                }
                n = " ".length();
            }
        }
        if (n == 0 && this.gameController.thePlayer != null) {
            this.gameController.thePlayer.addChatMessage(new ChatComponentText(NetHandlerPlayClient.I[0xA1 ^ 0xAB] + s33PacketUpdateSign.getPos().getX() + NetHandlerPlayClient.I[0x4F ^ 0x44] + s33PacketUpdateSign.getPos().getY() + NetHandlerPlayClient.I[0x4D ^ 0x41] + s33PacketUpdateSign.getPos().getZ()));
        }
    }
    
    static {
        I();
        logger = LogManager.getLogger();
    }
    
    @Override
    public void handleSpawnMob(final S0FPacketSpawnMob s0FPacketSpawnMob) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayClient>)s0FPacketSpawnMob, this, this.gameController);
        final double n = s0FPacketSpawnMob.getX() / 32.0;
        final double n2 = s0FPacketSpawnMob.getY() / 32.0;
        final double n3 = s0FPacketSpawnMob.getZ() / 32.0;
        final float n4 = s0FPacketSpawnMob.getYaw() * (22 + 316 - 124 + 146) / 256.0f;
        final float n5 = s0FPacketSpawnMob.getPitch() * (327 + 126 - 423 + 330) / 256.0f;
        final EntityLivingBase entityLivingBase = (EntityLivingBase)EntityList.createEntityByID(s0FPacketSpawnMob.getEntityType(), this.gameController.theWorld);
        entityLivingBase.serverPosX = s0FPacketSpawnMob.getX();
        entityLivingBase.serverPosY = s0FPacketSpawnMob.getY();
        entityLivingBase.serverPosZ = s0FPacketSpawnMob.getZ();
        final EntityLivingBase entityLivingBase2 = entityLivingBase;
        final EntityLivingBase entityLivingBase3 = entityLivingBase;
        final float n6 = s0FPacketSpawnMob.getHeadPitch() * (292 + 158 - 336 + 246) / 256.0f;
        entityLivingBase3.rotationYawHead = n6;
        entityLivingBase2.renderYawOffset = n6;
        final Entity[] parts = entityLivingBase.getParts();
        if (parts != null) {
            final int n7 = s0FPacketSpawnMob.getEntityID() - entityLivingBase.getEntityId();
            int i = "".length();
            "".length();
            if (-1 != -1) {
                throw null;
            }
            while (i < parts.length) {
                parts[i].setEntityId(parts[i].getEntityId() + n7);
                ++i;
            }
        }
        entityLivingBase.setEntityId(s0FPacketSpawnMob.getEntityID());
        entityLivingBase.setPositionAndRotation(n, n2, n3, n4, n5);
        entityLivingBase.motionX = s0FPacketSpawnMob.getVelocityX() / 8000.0f;
        entityLivingBase.motionY = s0FPacketSpawnMob.getVelocityY() / 8000.0f;
        entityLivingBase.motionZ = s0FPacketSpawnMob.getVelocityZ() / 8000.0f;
        this.clientWorldController.addEntityToWorld(s0FPacketSpawnMob.getEntityID(), entityLivingBase);
        final List<DataWatcher.WatchableObject> func_149027_c = s0FPacketSpawnMob.func_149027_c();
        if (func_149027_c != null) {
            entityLivingBase.getDataWatcher().updateWatchedObjectsFromList(func_149027_c);
        }
    }
    
    @Override
    public void handleSignEditorOpen(final S36PacketSignEditorOpen s36PacketSignEditorOpen) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayClient>)s36PacketSignEditorOpen, this, this.gameController);
        TileEntity tileEntity = this.clientWorldController.getTileEntity(s36PacketSignEditorOpen.getSignPosition());
        if (!(tileEntity instanceof TileEntitySign)) {
            tileEntity = new TileEntitySign();
            tileEntity.setWorldObj(this.clientWorldController);
            tileEntity.setPos(s36PacketSignEditorOpen.getSignPosition());
        }
        this.gameController.thePlayer.openEditSign((TileEntitySign)tileEntity);
    }
    
    @Override
    public void handleOpenWindow(final S2DPacketOpenWindow s2DPacketOpenWindow) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayClient>)s2DPacketOpenWindow, this, this.gameController);
        final EntityPlayerSP thePlayer = this.gameController.thePlayer;
        if (NetHandlerPlayClient.I[0xC2 ^ 0xC5].equals(s2DPacketOpenWindow.getGuiId())) {
            thePlayer.displayGUIChest(new InventoryBasic(s2DPacketOpenWindow.getWindowTitle(), s2DPacketOpenWindow.getSlotCount()));
            thePlayer.openContainer.windowId = s2DPacketOpenWindow.getWindowId();
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        else if (NetHandlerPlayClient.I[0x24 ^ 0x2C].equals(s2DPacketOpenWindow.getGuiId())) {
            thePlayer.displayVillagerTradeGui(new NpcMerchant(thePlayer, s2DPacketOpenWindow.getWindowTitle()));
            thePlayer.openContainer.windowId = s2DPacketOpenWindow.getWindowId();
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        else if (NetHandlerPlayClient.I[0x8E ^ 0x87].equals(s2DPacketOpenWindow.getGuiId())) {
            final Entity entityByID = this.clientWorldController.getEntityByID(s2DPacketOpenWindow.getEntityId());
            if (entityByID instanceof EntityHorse) {
                thePlayer.displayGUIHorse((EntityHorse)entityByID, new AnimalChest(s2DPacketOpenWindow.getWindowTitle(), s2DPacketOpenWindow.getSlotCount()));
                thePlayer.openContainer.windowId = s2DPacketOpenWindow.getWindowId();
                "".length();
                if (2 >= 3) {
                    throw null;
                }
            }
        }
        else if (!s2DPacketOpenWindow.hasSlots()) {
            thePlayer.displayGui(new LocalBlockIntercommunication(s2DPacketOpenWindow.getGuiId(), s2DPacketOpenWindow.getWindowTitle()));
            thePlayer.openContainer.windowId = s2DPacketOpenWindow.getWindowId();
            "".length();
            if (3 == 1) {
                throw null;
            }
        }
        else {
            thePlayer.displayGUIChest(new ContainerLocalMenu(s2DPacketOpenWindow.getGuiId(), s2DPacketOpenWindow.getWindowTitle(), s2DPacketOpenWindow.getSlotCount()));
            thePlayer.openContainer.windowId = s2DPacketOpenWindow.getWindowId();
        }
    }
    
    @Override
    public void handleWindowItems(final S30PacketWindowItems s30PacketWindowItems) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayClient>)s30PacketWindowItems, this, this.gameController);
        final EntityPlayerSP thePlayer = this.gameController.thePlayer;
        if (s30PacketWindowItems.func_148911_c() == 0) {
            thePlayer.inventoryContainer.putStacksInSlots(s30PacketWindowItems.getItemStacks());
            "".length();
            if (4 <= 1) {
                throw null;
            }
        }
        else if (s30PacketWindowItems.func_148911_c() == thePlayer.openContainer.windowId) {
            thePlayer.openContainer.putStacksInSlots(s30PacketWindowItems.getItemStacks());
        }
    }
    
    @Override
    public void handleDestroyEntities(final S13PacketDestroyEntities s13PacketDestroyEntities) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayClient>)s13PacketDestroyEntities, this, this.gameController);
        int i = "".length();
        "".length();
        if (1 <= 0) {
            throw null;
        }
        while (i < s13PacketDestroyEntities.getEntityIDs().length) {
            this.clientWorldController.removeEntityFromWorld(s13PacketDestroyEntities.getEntityIDs()[i]);
            ++i;
        }
    }
    
    @Override
    public void handleCombatEvent(final S42PacketCombatEvent s42PacketCombatEvent) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayClient>)s42PacketCombatEvent, this, this.gameController);
        final Entity entityByID = this.clientWorldController.getEntityByID(s42PacketCombatEvent.field_179775_c);
        EntityLivingBase entityLivingBase;
        if (entityByID instanceof EntityLivingBase) {
            entityLivingBase = (EntityLivingBase)entityByID;
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        else {
            entityLivingBase = null;
        }
        final EntityLivingBase entityLivingBase2 = entityLivingBase;
        if (s42PacketCombatEvent.eventType == S42PacketCombatEvent.Event.END_COMBAT) {
            this.gameController.getTwitchStream().func_176026_a(new MetadataCombat(this.gameController.thePlayer, entityLivingBase2), 0L - (220 + 174 + 280 + 326) * s42PacketCombatEvent.field_179772_d / (0x21 ^ 0x35), 0L);
            "".length();
            if (0 >= 2) {
                throw null;
            }
        }
        else if (s42PacketCombatEvent.eventType == S42PacketCombatEvent.Event.ENTITY_DIED) {
            final Entity entityByID2 = this.clientWorldController.getEntityByID(s42PacketCombatEvent.field_179774_b);
            if (entityByID2 instanceof EntityPlayer) {
                final MetadataPlayerDeath metadataPlayerDeath = new MetadataPlayerDeath((EntityLivingBase)entityByID2, entityLivingBase2);
                metadataPlayerDeath.func_152807_a(s42PacketCombatEvent.deathMessage);
                this.gameController.getTwitchStream().func_152911_a(metadataPlayerDeath, 0L);
            }
        }
    }
    
    @Override
    public void handleKeepAlive(final S00PacketKeepAlive s00PacketKeepAlive) {
        this.addToSendQueue(new C00PacketKeepAlive(s00PacketKeepAlive.func_149134_c()));
    }
    
    @Override
    public void handleSetCompressionLevel(final S46PacketSetCompressionLevel s46PacketSetCompressionLevel) {
        if (!this.netManager.isLocalChannel()) {
            this.netManager.setCompressionTreshold(s46PacketSetCompressionLevel.func_179760_a());
        }
    }
    
    @Override
    public void handleRemoveEntityEffect(final S1EPacketRemoveEntityEffect s1EPacketRemoveEntityEffect) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayClient>)s1EPacketRemoveEntityEffect, this, this.gameController);
        final Entity entityByID = this.clientWorldController.getEntityByID(s1EPacketRemoveEntityEffect.getEntityId());
        if (entityByID instanceof EntityLivingBase) {
            ((EntityLivingBase)entityByID).removePotionEffectClient(s1EPacketRemoveEntityEffect.getEffectId());
        }
    }
    
    @Override
    public void handleEntityMetadata(final S1CPacketEntityMetadata s1CPacketEntityMetadata) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayClient>)s1CPacketEntityMetadata, this, this.gameController);
        final Entity entityByID = this.clientWorldController.getEntityByID(s1CPacketEntityMetadata.getEntityId());
        if (entityByID != null && s1CPacketEntityMetadata.func_149376_c() != null) {
            entityByID.getDataWatcher().updateWatchedObjectsFromList(s1CPacketEntityMetadata.func_149376_c());
        }
    }
    
    @Override
    public void onDisconnect(final IChatComponent chatComponent) {
        this.gameController.loadWorld(null);
        if (this.guiScreenServer != null) {
            if (this.guiScreenServer instanceof GuiScreenRealmsProxy) {
                this.gameController.displayGuiScreen(new DisconnectedRealmsScreen(((GuiScreenRealmsProxy)this.guiScreenServer).func_154321_a(), NetHandlerPlayClient.I[" ".length()], chatComponent).getProxy());
                "".length();
                if (2 >= 4) {
                    throw null;
                }
            }
            else {
                this.gameController.displayGuiScreen(new GuiDisconnected(this.guiScreenServer, NetHandlerPlayClient.I["  ".length()], chatComponent));
                "".length();
                if (3 < 1) {
                    throw null;
                }
            }
        }
        else {
            this.gameController.displayGuiScreen(new GuiDisconnected(new GuiMultiplayer(new GuiMainMenu()), NetHandlerPlayClient.I["   ".length()], chatComponent));
        }
    }
    
    @Override
    public void handleDisplayScoreboard(final S3DPacketDisplayScoreboard s3DPacketDisplayScoreboard) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayClient>)s3DPacketDisplayScoreboard, this, this.gameController);
        final Scoreboard scoreboard = this.clientWorldController.getScoreboard();
        if (s3DPacketDisplayScoreboard.func_149370_d().length() == 0) {
            scoreboard.setObjectiveInDisplaySlot(s3DPacketDisplayScoreboard.func_149371_c(), null);
            "".length();
            if (-1 >= 0) {
                throw null;
            }
        }
        else {
            scoreboard.setObjectiveInDisplaySlot(s3DPacketDisplayScoreboard.func_149371_c(), scoreboard.getObjective(s3DPacketDisplayScoreboard.func_149370_d()));
        }
    }
    
    @Override
    public void handleTeams(final S3EPacketTeams s3EPacketTeams) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayClient>)s3EPacketTeams, this, this.gameController);
        final Scoreboard scoreboard = this.clientWorldController.getScoreboard();
        ScorePlayerTeam scorePlayerTeam;
        if (s3EPacketTeams.func_149307_h() == 0) {
            scorePlayerTeam = scoreboard.createTeam(s3EPacketTeams.func_149312_c());
            "".length();
            if (1 == 2) {
                throw null;
            }
        }
        else {
            scorePlayerTeam = scoreboard.getTeam(s3EPacketTeams.func_149312_c());
        }
        if (s3EPacketTeams.func_149307_h() == 0 || s3EPacketTeams.func_149307_h() == "  ".length()) {
            scorePlayerTeam.setTeamName(s3EPacketTeams.func_149306_d());
            scorePlayerTeam.setNamePrefix(s3EPacketTeams.func_149311_e());
            scorePlayerTeam.setNameSuffix(s3EPacketTeams.func_149309_f());
            scorePlayerTeam.setChatFormat(EnumChatFormatting.func_175744_a(s3EPacketTeams.func_179813_h()));
            scorePlayerTeam.func_98298_a(s3EPacketTeams.func_149308_i());
            final Team.EnumVisible func_178824_a = Team.EnumVisible.func_178824_a(s3EPacketTeams.func_179814_i());
            if (func_178824_a != null) {
                scorePlayerTeam.setNameTagVisibility(func_178824_a);
            }
        }
        if (s3EPacketTeams.func_149307_h() == 0 || s3EPacketTeams.func_149307_h() == "   ".length()) {
            final Iterator<String> iterator = s3EPacketTeams.func_149310_g().iterator();
            "".length();
            if (true != true) {
                throw null;
            }
            while (iterator.hasNext()) {
                scoreboard.addPlayerToTeam(iterator.next(), s3EPacketTeams.func_149312_c());
            }
        }
        if (s3EPacketTeams.func_149307_h() == (0x62 ^ 0x66)) {
            final Iterator<String> iterator2 = s3EPacketTeams.func_149310_g().iterator();
            "".length();
            if (1 >= 2) {
                throw null;
            }
            while (iterator2.hasNext()) {
                scoreboard.removePlayerFromTeam(iterator2.next(), scorePlayerTeam);
            }
        }
        if (s3EPacketTeams.func_149307_h() == " ".length()) {
            scoreboard.removeTeam(scorePlayerTeam);
        }
    }
    
    @Override
    public void handleSpawnExperienceOrb(final S11PacketSpawnExperienceOrb s11PacketSpawnExperienceOrb) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayClient>)s11PacketSpawnExperienceOrb, this, this.gameController);
        final EntityXPOrb entityXPOrb = new EntityXPOrb(this.clientWorldController, s11PacketSpawnExperienceOrb.getX() / 32.0, s11PacketSpawnExperienceOrb.getY() / 32.0, s11PacketSpawnExperienceOrb.getZ() / 32.0, s11PacketSpawnExperienceOrb.getXPValue());
        entityXPOrb.serverPosX = s11PacketSpawnExperienceOrb.getX();
        entityXPOrb.serverPosY = s11PacketSpawnExperienceOrb.getY();
        entityXPOrb.serverPosZ = s11PacketSpawnExperienceOrb.getZ();
        entityXPOrb.rotationYaw = 0.0f;
        entityXPOrb.rotationPitch = 0.0f;
        entityXPOrb.setEntityId(s11PacketSpawnExperienceOrb.getEntityID());
        this.clientWorldController.addEntityToWorld(s11PacketSpawnExperienceOrb.getEntityID(), entityXPOrb);
    }
    
    @Override
    public void handleWindowProperty(final S31PacketWindowProperty s31PacketWindowProperty) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayClient>)s31PacketWindowProperty, this, this.gameController);
        final EntityPlayerSP thePlayer = this.gameController.thePlayer;
        if (thePlayer.openContainer != null && thePlayer.openContainer.windowId == s31PacketWindowProperty.getWindowId()) {
            thePlayer.openContainer.updateProgressBar(s31PacketWindowProperty.getVarIndex(), s31PacketWindowProperty.getVarValue());
        }
    }
    
    @Override
    public void handleChunkData(final S21PacketChunkData s21PacketChunkData) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayClient>)s21PacketChunkData, this, this.gameController);
        if (s21PacketChunkData.func_149274_i()) {
            if (s21PacketChunkData.getExtractedSize() == 0) {
                this.clientWorldController.doPreChunk(s21PacketChunkData.getChunkX(), s21PacketChunkData.getChunkZ(), "".length() != 0);
                return;
            }
            this.clientWorldController.doPreChunk(s21PacketChunkData.getChunkX(), s21PacketChunkData.getChunkZ(), " ".length() != 0);
        }
        this.clientWorldController.invalidateBlockReceiveRegion(s21PacketChunkData.getChunkX() << (0x38 ^ 0x3C), "".length(), s21PacketChunkData.getChunkZ() << (0x95 ^ 0x91), (s21PacketChunkData.getChunkX() << (0x4 ^ 0x0)) + (0x6 ^ 0x9), 30 + 127 - 91 + 190, (s21PacketChunkData.getChunkZ() << (0x88 ^ 0x8C)) + (0xBB ^ 0xB4));
        final Chunk chunkFromChunkCoords = this.clientWorldController.getChunkFromChunkCoords(s21PacketChunkData.getChunkX(), s21PacketChunkData.getChunkZ());
        chunkFromChunkCoords.fillChunk(s21PacketChunkData.func_149272_d(), s21PacketChunkData.getExtractedSize(), s21PacketChunkData.func_149274_i());
        this.clientWorldController.markBlockRangeForRenderUpdate(s21PacketChunkData.getChunkX() << (0x1A ^ 0x1E), "".length(), s21PacketChunkData.getChunkZ() << (0x4A ^ 0x4E), (s21PacketChunkData.getChunkX() << (0x18 ^ 0x1C)) + (0x4D ^ 0x42), 143 + 107 - 158 + 164, (s21PacketChunkData.getChunkZ() << (0xC1 ^ 0xC5)) + (0x77 ^ 0x78));
        if (!s21PacketChunkData.func_149274_i() || !(this.clientWorldController.provider instanceof WorldProviderSurface)) {
            chunkFromChunkCoords.resetRelightChecks();
        }
    }
    
    public NetHandlerPlayClient(final Minecraft gameController, final GuiScreen guiScreenServer, final NetworkManager netManager, final GameProfile profile) {
        this.playerInfoMap = (Map<UUID, NetworkPlayerInfo>)Maps.newHashMap();
        this.currentServerMaxPlayers = (0xBD ^ 0xA9);
        this.field_147308_k = ("".length() != 0);
        this.avRandomizer = new Random();
        this.gameController = gameController;
        this.guiScreenServer = guiScreenServer;
        this.netManager = netManager;
        this.profile = profile;
    }
    
    @Override
    public void handleEntityProperties(final S20PacketEntityProperties s20PacketEntityProperties) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayClient>)s20PacketEntityProperties, this, this.gameController);
        final Entity entityByID = this.clientWorldController.getEntityByID(s20PacketEntityProperties.getEntityId());
        if (entityByID != null) {
            if (!(entityByID instanceof EntityLivingBase)) {
                throw new IllegalStateException(NetHandlerPlayClient.I[0xA4 ^ 0xBA] + entityByID + NetHandlerPlayClient.I[0x9A ^ 0x85]);
            }
            final BaseAttributeMap attributeMap = ((EntityLivingBase)entityByID).getAttributeMap();
            final Iterator<S20PacketEntityProperties.Snapshot> iterator = s20PacketEntityProperties.func_149441_d().iterator();
            "".length();
            if (1 < 1) {
                throw null;
            }
            while (iterator.hasNext()) {
                final S20PacketEntityProperties.Snapshot snapshot = iterator.next();
                IAttributeInstance attributeInstance = attributeMap.getAttributeInstanceByName(snapshot.func_151409_a());
                if (attributeInstance == null) {
                    attributeInstance = attributeMap.registerAttribute(new RangedAttribute(null, snapshot.func_151409_a(), 0.0, Double.MIN_NORMAL, Double.MAX_VALUE));
                }
                attributeInstance.setBaseValue(snapshot.func_151410_b());
                attributeInstance.removeAllModifiers();
                final Iterator<AttributeModifier> iterator2 = snapshot.func_151408_c().iterator();
                "".length();
                if (2 < 1) {
                    throw null;
                }
                while (iterator2.hasNext()) {
                    attributeInstance.applyModifier(iterator2.next());
                }
            }
        }
    }
    
    @Override
    public void handleExplosion(final S27PacketExplosion s27PacketExplosion) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayClient>)s27PacketExplosion, this, this.gameController);
        new Explosion(this.gameController.theWorld, null, s27PacketExplosion.getX(), s27PacketExplosion.getY(), s27PacketExplosion.getZ(), s27PacketExplosion.getStrength(), s27PacketExplosion.getAffectedBlockPositions()).doExplosionB(" ".length() != 0);
        final EntityPlayerSP thePlayer = this.gameController.thePlayer;
        thePlayer.motionX += s27PacketExplosion.func_149149_c();
        final EntityPlayerSP thePlayer2 = this.gameController.thePlayer;
        thePlayer2.motionY += s27PacketExplosion.func_149144_d();
        final EntityPlayerSP thePlayer3 = this.gameController.thePlayer;
        thePlayer3.motionZ += s27PacketExplosion.func_149147_e();
    }
    
    @Override
    public void handleUpdateTileEntity(final S35PacketUpdateTileEntity s35PacketUpdateTileEntity) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayClient>)s35PacketUpdateTileEntity, this, this.gameController);
        if (this.gameController.theWorld.isBlockLoaded(s35PacketUpdateTileEntity.getPos())) {
            final TileEntity tileEntity = this.gameController.theWorld.getTileEntity(s35PacketUpdateTileEntity.getPos());
            final int tileEntityType = s35PacketUpdateTileEntity.getTileEntityType();
            if ((tileEntityType == " ".length() && tileEntity instanceof TileEntityMobSpawner) || (tileEntityType == "  ".length() && tileEntity instanceof TileEntityCommandBlock) || (tileEntityType == "   ".length() && tileEntity instanceof TileEntityBeacon) || (tileEntityType == (0x23 ^ 0x27) && tileEntity instanceof TileEntitySkull) || (tileEntityType == (0x93 ^ 0x96) && tileEntity instanceof TileEntityFlowerPot) || (tileEntityType == (0x9F ^ 0x99) && tileEntity instanceof TileEntityBanner)) {
                tileEntity.readFromNBT(s35PacketUpdateTileEntity.getNbtCompound());
            }
        }
    }
    
    @Override
    public void handleEntityNBT(final S49PacketUpdateEntityNBT s49PacketUpdateEntityNBT) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayClient>)s49PacketUpdateEntityNBT, this, this.gameController);
        final Entity entity = s49PacketUpdateEntityNBT.getEntity(this.clientWorldController);
        if (entity != null) {
            entity.clientUpdateEntityNBT(s49PacketUpdateEntityNBT.getTagCompound());
        }
    }
    
    @Override
    public void handleMapChunkBulk(final S26PacketMapChunkBulk s26PacketMapChunkBulk) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayClient>)s26PacketMapChunkBulk, this, this.gameController);
        int i = "".length();
        "".length();
        if (4 <= 3) {
            throw null;
        }
        while (i < s26PacketMapChunkBulk.getChunkCount()) {
            final int chunkX = s26PacketMapChunkBulk.getChunkX(i);
            final int chunkZ = s26PacketMapChunkBulk.getChunkZ(i);
            this.clientWorldController.doPreChunk(chunkX, chunkZ, " ".length() != 0);
            this.clientWorldController.invalidateBlockReceiveRegion(chunkX << (0x2C ^ 0x28), "".length(), chunkZ << (0x5E ^ 0x5A), (chunkX << (0x2B ^ 0x2F)) + (0x25 ^ 0x2A), 5 + 44 - 28 + 235, (chunkZ << (0x0 ^ 0x4)) + (0x45 ^ 0x4A));
            final Chunk chunkFromChunkCoords = this.clientWorldController.getChunkFromChunkCoords(chunkX, chunkZ);
            chunkFromChunkCoords.fillChunk(s26PacketMapChunkBulk.getChunkBytes(i), s26PacketMapChunkBulk.getChunkSize(i), " ".length() != 0);
            this.clientWorldController.markBlockRangeForRenderUpdate(chunkX << (0x49 ^ 0x4D), "".length(), chunkZ << (0x42 ^ 0x46), (chunkX << (0xC2 ^ 0xC6)) + (0x76 ^ 0x79), 122 + 63 + 62 + 9, (chunkZ << (0x75 ^ 0x71)) + (0x1F ^ 0x10));
            if (!(this.clientWorldController.provider instanceof WorldProviderSurface)) {
                chunkFromChunkCoords.resetRelightChecks();
            }
            ++i;
        }
    }
    
    @Override
    public void handleSetExperience(final S1FPacketSetExperience s1FPacketSetExperience) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayClient>)s1FPacketSetExperience, this, this.gameController);
        this.gameController.thePlayer.setXPStats(s1FPacketSetExperience.func_149397_c(), s1FPacketSetExperience.getTotalExperience(), s1FPacketSetExperience.getLevel());
    }
    
    @Override
    public void handleResourcePack(final S48PacketResourcePackSend s48PacketResourcePackSend) {
        final String url = s48PacketResourcePackSend.getURL();
        final String hash = s48PacketResourcePackSend.getHash();
        if (url.startsWith(NetHandlerPlayClient.I[0x23 ^ 0x36])) {
            final File resourcePackInstance = new File(new File(this.gameController.mcDataDir, NetHandlerPlayClient.I[0x39 ^ 0x2E]), url.substring(NetHandlerPlayClient.I[0x19 ^ 0xF].length()));
            if (resourcePackInstance.isFile()) {
                this.netManager.sendPacket(new C19PacketResourcePackStatus(hash, C19PacketResourcePackStatus.Action.ACCEPTED));
                Futures.addCallback((ListenableFuture)this.gameController.getResourcePackRepository().setResourcePackInstance(resourcePackInstance), (FutureCallback)new FutureCallback<Object>(this, hash) {
                    final NetHandlerPlayClient this$0;
                    private final String val$s1;
                    
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
                            if (4 == 3) {
                                throw null;
                            }
                        }
                        return sb.toString();
                    }
                    
                    public void onSuccess(final Object o) {
                        NetHandlerPlayClient.access$2(this.this$0).sendPacket(new C19PacketResourcePackStatus(this.val$s1, C19PacketResourcePackStatus.Action.SUCCESSFULLY_LOADED));
                    }
                    
                    public void onFailure(final Throwable t) {
                        NetHandlerPlayClient.access$2(this.this$0).sendPacket(new C19PacketResourcePackStatus(this.val$s1, C19PacketResourcePackStatus.Action.FAILED_DOWNLOAD));
                    }
                });
                "".length();
                if (4 < 1) {
                    throw null;
                }
            }
            else {
                this.netManager.sendPacket(new C19PacketResourcePackStatus(hash, C19PacketResourcePackStatus.Action.FAILED_DOWNLOAD));
                "".length();
                if (4 < 2) {
                    throw null;
                }
            }
        }
        else if (this.gameController.getCurrentServerData() != null && this.gameController.getCurrentServerData().getResourceMode() == ServerData.ServerResourceMode.ENABLED) {
            this.netManager.sendPacket(new C19PacketResourcePackStatus(hash, C19PacketResourcePackStatus.Action.ACCEPTED));
            Futures.addCallback((ListenableFuture)this.gameController.getResourcePackRepository().downloadResourcePack(url, hash), (FutureCallback)new FutureCallback<Object>(this, hash) {
                final NetHandlerPlayClient this$0;
                private final String val$s1;
                
                public void onFailure(final Throwable t) {
                    NetHandlerPlayClient.access$2(this.this$0).sendPacket(new C19PacketResourcePackStatus(this.val$s1, C19PacketResourcePackStatus.Action.FAILED_DOWNLOAD));
                }
                
                public void onSuccess(final Object o) {
                    NetHandlerPlayClient.access$2(this.this$0).sendPacket(new C19PacketResourcePackStatus(this.val$s1, C19PacketResourcePackStatus.Action.SUCCESSFULLY_LOADED));
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
                        if (1 >= 2) {
                            throw null;
                        }
                    }
                    return sb.toString();
                }
            });
            "".length();
            if (2 < 2) {
                throw null;
            }
        }
        else if (this.gameController.getCurrentServerData() != null && this.gameController.getCurrentServerData().getResourceMode() != ServerData.ServerResourceMode.PROMPT) {
            this.netManager.sendPacket(new C19PacketResourcePackStatus(hash, C19PacketResourcePackStatus.Action.DECLINED));
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        else {
            this.gameController.addScheduledTask(new Runnable(this, hash, url) {
                private final String val$s1;
                private final String val$s;
                private static final String[] I;
                final NetHandlerPlayClient this$0;
                
                static {
                    I();
                }
                
                private static void I() {
                    (I = new String["  ".length()])["".length()] = I("\b>8\u000e.\u0015'5\u0003\"\u0017e \u001f?\u0011>&\u001f\u0017\u0017$9\n3K'=\u0014\"T", "eKTzG");
                    NetHandlerPlayClient$3.I[" ".length()] = I("#\u00026-8>\u001b; 4<Y.<):\u0002(<\u0001<\u00187)%`\u001b374|", "NwZYQ");
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
                        if (4 != 4) {
                            throw null;
                        }
                    }
                    return sb.toString();
                }
                
                @Override
                public void run() {
                    NetHandlerPlayClient.access$3(this.this$0).displayGuiScreen(new GuiYesNo(new GuiYesNoCallback(this, this.val$s1, this.val$s) {
                        private final String val$s;
                        private final String val$s1;
                        final NetHandlerPlayClient$3 this$1;
                        
                        static NetHandlerPlayClient$3 access$0(final NetHandlerPlayClient$3$1 guiYesNoCallback) {
                            return guiYesNoCallback.this$1;
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
                                if (1 >= 3) {
                                    throw null;
                                }
                            }
                            return sb.toString();
                        }
                        
                        @Override
                        public void confirmClicked(final boolean b, final int n) {
                            NetHandlerPlayClient.access$4(NetHandlerPlayClient$3.access$0(this.this$1), Minecraft.getMinecraft());
                            if (b) {
                                if (NetHandlerPlayClient.access$3(NetHandlerPlayClient$3.access$0(this.this$1)).getCurrentServerData() != null) {
                                    NetHandlerPlayClient.access$3(NetHandlerPlayClient$3.access$0(this.this$1)).getCurrentServerData().setResourceMode(ServerData.ServerResourceMode.ENABLED);
                                }
                                NetHandlerPlayClient.access$2(NetHandlerPlayClient$3.access$0(this.this$1)).sendPacket(new C19PacketResourcePackStatus(this.val$s1, C19PacketResourcePackStatus.Action.ACCEPTED));
                                Futures.addCallback((ListenableFuture)NetHandlerPlayClient.access$3(NetHandlerPlayClient$3.access$0(this.this$1)).getResourcePackRepository().downloadResourcePack(this.val$s, this.val$s1), (FutureCallback)new FutureCallback<Object>(this, this.val$s1) {
                                    final NetHandlerPlayClient$3$1 this$2;
                                    private final String val$s1;
                                    
                                    public void onSuccess(final Object o) {
                                        NetHandlerPlayClient.access$2(NetHandlerPlayClient$3.access$0(NetHandlerPlayClient$3$1.access$0(this.this$2))).sendPacket(new C19PacketResourcePackStatus(this.val$s1, C19PacketResourcePackStatus.Action.SUCCESSFULLY_LOADED));
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
                                            if (-1 >= 0) {
                                                throw null;
                                            }
                                        }
                                        return sb.toString();
                                    }
                                    
                                    public void onFailure(final Throwable t) {
                                        NetHandlerPlayClient.access$2(NetHandlerPlayClient$3.access$0(NetHandlerPlayClient$3$1.access$0(this.this$2))).sendPacket(new C19PacketResourcePackStatus(this.val$s1, C19PacketResourcePackStatus.Action.FAILED_DOWNLOAD));
                                    }
                                });
                                "".length();
                                if (1 == 4) {
                                    throw null;
                                }
                            }
                            else {
                                if (NetHandlerPlayClient.access$3(NetHandlerPlayClient$3.access$0(this.this$1)).getCurrentServerData() != null) {
                                    NetHandlerPlayClient.access$3(NetHandlerPlayClient$3.access$0(this.this$1)).getCurrentServerData().setResourceMode(ServerData.ServerResourceMode.DISABLED);
                                }
                                NetHandlerPlayClient.access$2(NetHandlerPlayClient$3.access$0(this.this$1)).sendPacket(new C19PacketResourcePackStatus(this.val$s1, C19PacketResourcePackStatus.Action.DECLINED));
                            }
                            ServerList.func_147414_b(NetHandlerPlayClient.access$3(NetHandlerPlayClient$3.access$0(this.this$1)).getCurrentServerData());
                            NetHandlerPlayClient.access$3(NetHandlerPlayClient$3.access$0(this.this$1)).displayGuiScreen(null);
                        }
                    }, I18n.format(NetHandlerPlayClient$3.I["".length()], new Object["".length()]), I18n.format(NetHandlerPlayClient$3.I[" ".length()], new Object["".length()]), "".length()));
                }
                
                static NetHandlerPlayClient access$0(final NetHandlerPlayClient$3 runnable) {
                    return runnable.this$0;
                }
            });
        }
    }
    
    @Override
    public void handleSoundEffect(final S29PacketSoundEffect s29PacketSoundEffect) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayClient>)s29PacketSoundEffect, this, this.gameController);
        this.gameController.theWorld.playSound(s29PacketSoundEffect.getX(), s29PacketSoundEffect.getY(), s29PacketSoundEffect.getZ(), s29PacketSoundEffect.getSoundName(), s29PacketSoundEffect.getVolume(), s29PacketSoundEffect.getPitch(), "".length() != 0);
    }
    
    @Override
    public void handleSpawnGlobalEntity(final S2CPacketSpawnGlobalEntity s2CPacketSpawnGlobalEntity) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayClient>)s2CPacketSpawnGlobalEntity, this, this.gameController);
        final double n = s2CPacketSpawnGlobalEntity.func_149051_d() / 32.0;
        final double n2 = s2CPacketSpawnGlobalEntity.func_149050_e() / 32.0;
        final double n3 = s2CPacketSpawnGlobalEntity.func_149049_f() / 32.0;
        Entity entity = null;
        if (s2CPacketSpawnGlobalEntity.func_149053_g() == " ".length()) {
            entity = new EntityLightningBolt(this.clientWorldController, n, n2, n3);
        }
        if (entity != null) {
            entity.serverPosX = s2CPacketSpawnGlobalEntity.func_149051_d();
            entity.serverPosY = s2CPacketSpawnGlobalEntity.func_149050_e();
            entity.serverPosZ = s2CPacketSpawnGlobalEntity.func_149049_f();
            entity.rotationYaw = 0.0f;
            entity.rotationPitch = 0.0f;
            entity.setEntityId(s2CPacketSpawnGlobalEntity.func_149052_c());
            this.clientWorldController.addWeatherEffect(entity);
        }
    }
    
    @Override
    public void handleEffect(final S28PacketEffect s28PacketEffect) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayClient>)s28PacketEffect, this, this.gameController);
        if (s28PacketEffect.isSoundServerwide()) {
            this.gameController.theWorld.playBroadcastSound(s28PacketEffect.getSoundType(), s28PacketEffect.getSoundPos(), s28PacketEffect.getSoundData());
            "".length();
            if (0 >= 1) {
                throw null;
            }
        }
        else {
            this.gameController.theWorld.playAuxSFX(s28PacketEffect.getSoundType(), s28PacketEffect.getSoundPos(), s28PacketEffect.getSoundData());
        }
    }
    
    public NetworkPlayerInfo getPlayerInfo(final String s) {
        final Iterator<NetworkPlayerInfo> iterator = this.playerInfoMap.values().iterator();
        "".length();
        if (4 <= 3) {
            throw null;
        }
        while (iterator.hasNext()) {
            final NetworkPlayerInfo networkPlayerInfo = iterator.next();
            if (networkPlayerInfo.getGameProfile().getName().equals(s)) {
                return networkPlayerInfo;
            }
        }
        return null;
    }
    
    @Override
    public void handleEntityTeleport(final S18PacketEntityTeleport s18PacketEntityTeleport) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayClient>)s18PacketEntityTeleport, this, this.gameController);
        final Entity entityByID = this.clientWorldController.getEntityByID(s18PacketEntityTeleport.getEntityId());
        if (entityByID != null) {
            entityByID.serverPosX = s18PacketEntityTeleport.getX();
            entityByID.serverPosY = s18PacketEntityTeleport.getY();
            entityByID.serverPosZ = s18PacketEntityTeleport.getZ();
            final double n = entityByID.serverPosX / 32.0;
            final double n2 = entityByID.serverPosY / 32.0;
            final double n3 = entityByID.serverPosZ / 32.0;
            final float n4 = s18PacketEntityTeleport.getYaw() * (350 + 329 - 622 + 303) / 256.0f;
            final float n5 = s18PacketEntityTeleport.getPitch() * (108 + 203 + 20 + 29) / 256.0f;
            if (Math.abs(entityByID.posX - n) < 0.03125 && Math.abs(entityByID.posY - n2) < 0.015625 && Math.abs(entityByID.posZ - n3) < 0.03125) {
                entityByID.setPositionAndRotation2(entityByID.posX, entityByID.posY, entityByID.posZ, n4, n5, "   ".length(), " ".length() != 0);
                "".length();
                if (2 != 2) {
                    throw null;
                }
            }
            else {
                entityByID.setPositionAndRotation2(n, n2, n3, n4, n5, "   ".length(), " ".length() != 0);
            }
            entityByID.onGround = s18PacketEntityTeleport.getOnGround();
        }
    }
    
    @Override
    public void handleEntityEffect(final S1DPacketEntityEffect s1DPacketEntityEffect) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayClient>)s1DPacketEntityEffect, this, this.gameController);
        final Entity entityByID = this.clientWorldController.getEntityByID(s1DPacketEntityEffect.getEntityId());
        if (entityByID instanceof EntityLivingBase) {
            final PotionEffect potionEffect = new PotionEffect(s1DPacketEntityEffect.getEffectId(), s1DPacketEntityEffect.getDuration(), s1DPacketEntityEffect.getAmplifier(), "".length() != 0, s1DPacketEntityEffect.func_179707_f());
            potionEffect.setPotionDurationMax(s1DPacketEntityEffect.func_149429_c());
            ((EntityLivingBase)entityByID).addPotionEffect(potionEffect);
        }
    }
    
    @Override
    public void handlePlayerListItem(final S38PacketPlayerListItem s38PacketPlayerListItem) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayClient>)s38PacketPlayerListItem, this, this.gameController);
        final Iterator<S38PacketPlayerListItem.AddPlayerData> iterator = s38PacketPlayerListItem.func_179767_a().iterator();
        "".length();
        if (4 == -1) {
            throw null;
        }
        while (iterator.hasNext()) {
            final S38PacketPlayerListItem.AddPlayerData addPlayerData = iterator.next();
            if (s38PacketPlayerListItem.func_179768_b() == S38PacketPlayerListItem.Action.REMOVE_PLAYER) {
                this.playerInfoMap.remove(addPlayerData.getProfile().getId());
                "".length();
                if (2 <= 1) {
                    throw null;
                }
                continue;
            }
            else {
                NetworkPlayerInfo networkPlayerInfo = this.playerInfoMap.get(addPlayerData.getProfile().getId());
                if (s38PacketPlayerListItem.func_179768_b() == S38PacketPlayerListItem.Action.ADD_PLAYER) {
                    networkPlayerInfo = new NetworkPlayerInfo(addPlayerData);
                    this.playerInfoMap.put(networkPlayerInfo.getGameProfile().getId(), networkPlayerInfo);
                }
                if (networkPlayerInfo == null) {
                    continue;
                }
                switch ($SWITCH_TABLE$net$minecraft$network$play$server$S38PacketPlayerListItem$Action()[s38PacketPlayerListItem.func_179768_b().ordinal()]) {
                    case 1: {
                        networkPlayerInfo.setGameType(addPlayerData.getGameMode());
                        networkPlayerInfo.setResponseTime(addPlayerData.getPing());
                        "".length();
                        if (-1 >= 2) {
                            throw null;
                        }
                        continue;
                    }
                    case 2: {
                        networkPlayerInfo.setGameType(addPlayerData.getGameMode());
                        "".length();
                        if (1 >= 2) {
                            throw null;
                        }
                        continue;
                    }
                    case 3: {
                        networkPlayerInfo.setResponseTime(addPlayerData.getPing());
                        "".length();
                        if (1 == -1) {
                            throw null;
                        }
                        continue;
                    }
                    case 4: {
                        networkPlayerInfo.setDisplayName(addPlayerData.getDisplayName());
                    }
                    default: {
                        continue;
                    }
                }
            }
        }
    }
    
    @Override
    public void handlePlayerPosLook(final S08PacketPlayerPosLook s08PacketPlayerPosLook) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayClient>)s08PacketPlayerPosLook, this, this.gameController);
        final EntityPlayerSP thePlayer = this.gameController.thePlayer;
        double x = s08PacketPlayerPosLook.getX();
        double y = s08PacketPlayerPosLook.getY();
        double z = s08PacketPlayerPosLook.getZ();
        float yaw = s08PacketPlayerPosLook.getYaw();
        float pitch = s08PacketPlayerPosLook.getPitch();
        if (s08PacketPlayerPosLook.func_179834_f().contains(S08PacketPlayerPosLook.EnumFlags.X)) {
            x += thePlayer.posX;
            "".length();
            if (0 == 4) {
                throw null;
            }
        }
        else {
            thePlayer.motionX = 0.0;
        }
        if (s08PacketPlayerPosLook.func_179834_f().contains(S08PacketPlayerPosLook.EnumFlags.Y)) {
            y += thePlayer.posY;
            "".length();
            if (1 == -1) {
                throw null;
            }
        }
        else {
            thePlayer.motionY = 0.0;
        }
        if (s08PacketPlayerPosLook.func_179834_f().contains(S08PacketPlayerPosLook.EnumFlags.Z)) {
            z += thePlayer.posZ;
            "".length();
            if (true != true) {
                throw null;
            }
        }
        else {
            thePlayer.motionZ = 0.0;
        }
        if (s08PacketPlayerPosLook.func_179834_f().contains(S08PacketPlayerPosLook.EnumFlags.X_ROT)) {
            pitch += thePlayer.rotationPitch;
        }
        if (s08PacketPlayerPosLook.func_179834_f().contains(S08PacketPlayerPosLook.EnumFlags.Y_ROT)) {
            yaw += thePlayer.rotationYaw;
        }
        thePlayer.setPositionAndRotation(x, y, z, yaw, pitch);
        this.netManager.sendPacket(new C03PacketPlayer.C06PacketPlayerPosLook(thePlayer.posX, thePlayer.getEntityBoundingBox().minY, thePlayer.posZ, thePlayer.rotationYaw, thePlayer.rotationPitch, (boolean)("".length() != 0)));
        if (!this.doneLoadingTerrain) {
            this.gameController.thePlayer.prevPosX = this.gameController.thePlayer.posX;
            this.gameController.thePlayer.prevPosY = this.gameController.thePlayer.posY;
            this.gameController.thePlayer.prevPosZ = this.gameController.thePlayer.posZ;
            this.doneLoadingTerrain = (" ".length() != 0);
            this.gameController.displayGuiScreen(null);
        }
    }
    
    @Override
    public void handleWorldBorder(final S44PacketWorldBorder s44PacketWorldBorder) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayClient>)s44PacketWorldBorder, this, this.gameController);
        s44PacketWorldBorder.func_179788_a(this.clientWorldController.getWorldBorder());
    }
}
