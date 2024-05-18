package net.minecraft.network;

import net.minecraft.network.play.*;
import net.minecraft.server.*;
import com.google.common.primitives.*;
import net.minecraft.crash.*;
import java.util.concurrent.*;
import com.google.common.collect.*;
import net.minecraft.command.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.passive.*;
import org.apache.logging.log4j.*;
import net.minecraft.stats.*;
import org.apache.commons.lang3.*;
import net.minecraft.world.*;
import io.netty.util.concurrent.*;
import com.google.common.util.concurrent.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;
import io.netty.buffer.*;
import java.io.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.tileentity.*;
import net.minecraft.entity.*;
import net.minecraft.inventory.*;
import net.minecraft.command.server.*;
import java.util.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.play.client.*;
import net.minecraft.nbt.*;
import net.minecraft.server.management.*;

public class NetHandlerPlayServer implements INetHandlerPlayServer, ITickable
{
    private long lastPingTime;
    private int itemDropThreshold;
    private boolean hasMoved;
    private long lastSentPingPacket;
    private double lastPosX;
    private double lastPosZ;
    private static final String[] I;
    private int field_175090_f;
    private int chatSpamThresholdCount;
    private int floatingTickCount;
    public final NetworkManager netManager;
    private boolean field_147366_g;
    public EntityPlayerMP playerEntity;
    private static int[] $SWITCH_TABLE$net$minecraft$network$play$client$C07PacketPlayerDigging$Action;
    private double lastPosY;
    private int networkTickCount;
    private final MinecraftServer serverController;
    private int field_147378_h;
    private static int[] $SWITCH_TABLE$net$minecraft$network$play$client$C0BPacketEntityAction$Action;
    private static int[] $SWITCH_TABLE$net$minecraft$network$play$client$C16PacketClientStatus$EnumState;
    private IntHashMap<Short> field_147372_n;
    private static final Logger logger;
    
    public void setPlayerLocation(final double n, final double n2, final double n3, final float n4, final float n5) {
        this.setPlayerLocation(n, n2, n3, n4, n5, Collections.emptySet());
    }
    
    @Override
    public void processPlayer(final C03PacketPlayer c03PacketPlayer) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)c03PacketPlayer, this, this.playerEntity.getServerForPlayer());
        if (this.func_183006_b(c03PacketPlayer)) {
            this.kickPlayerFromServer(NetHandlerPlayServer.I["  ".length()]);
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        else {
            final WorldServer worldServerForDimension = this.serverController.worldServerForDimension(this.playerEntity.dimension);
            this.field_147366_g = (" ".length() != 0);
            if (!this.playerEntity.playerConqueredTheEnd) {
                final double posX = this.playerEntity.posX;
                final double posY = this.playerEntity.posY;
                final double posZ = this.playerEntity.posZ;
                double n = 0.0;
                final double n2 = c03PacketPlayer.getPositionX() - this.lastPosX;
                final double n3 = c03PacketPlayer.getPositionY() - this.lastPosY;
                final double n4 = c03PacketPlayer.getPositionZ() - this.lastPosZ;
                if (c03PacketPlayer.isMoving()) {
                    n = n2 * n2 + n3 * n3 + n4 * n4;
                    if (!this.hasMoved && n < 0.25) {
                        this.hasMoved = (" ".length() != 0);
                    }
                }
                if (this.hasMoved) {
                    this.field_175090_f = this.networkTickCount;
                    if (this.playerEntity.ridingEntity != null) {
                        float n5 = this.playerEntity.rotationYaw;
                        float n6 = this.playerEntity.rotationPitch;
                        this.playerEntity.ridingEntity.updateRiderPosition();
                        final double posX2 = this.playerEntity.posX;
                        final double posY2 = this.playerEntity.posY;
                        final double posZ2 = this.playerEntity.posZ;
                        if (c03PacketPlayer.getRotating()) {
                            n5 = c03PacketPlayer.getYaw();
                            n6 = c03PacketPlayer.getPitch();
                        }
                        this.playerEntity.onGround = c03PacketPlayer.isOnGround();
                        this.playerEntity.onUpdateEntity();
                        this.playerEntity.setPositionAndRotation(posX2, posY2, posZ2, n5, n6);
                        if (this.playerEntity.ridingEntity != null) {
                            this.playerEntity.ridingEntity.updateRiderPosition();
                        }
                        this.serverController.getConfigurationManager().serverUpdateMountedMovingPlayer(this.playerEntity);
                        if (this.playerEntity.ridingEntity != null) {
                            if (n > 4.0) {
                                this.playerEntity.playerNetServerHandler.sendPacket(new S18PacketEntityTeleport(this.playerEntity.ridingEntity));
                                this.setPlayerLocation(this.playerEntity.posX, this.playerEntity.posY, this.playerEntity.posZ, this.playerEntity.rotationYaw, this.playerEntity.rotationPitch);
                            }
                            this.playerEntity.ridingEntity.isAirBorne = (" ".length() != 0);
                        }
                        if (this.hasMoved) {
                            this.lastPosX = this.playerEntity.posX;
                            this.lastPosY = this.playerEntity.posY;
                            this.lastPosZ = this.playerEntity.posZ;
                        }
                        worldServerForDimension.updateEntity(this.playerEntity);
                        return;
                    }
                    if (this.playerEntity.isPlayerSleeping()) {
                        this.playerEntity.onUpdateEntity();
                        this.playerEntity.setPositionAndRotation(this.lastPosX, this.lastPosY, this.lastPosZ, this.playerEntity.rotationYaw, this.playerEntity.rotationPitch);
                        worldServerForDimension.updateEntity(this.playerEntity);
                        return;
                    }
                    final double posY3 = this.playerEntity.posY;
                    this.lastPosX = this.playerEntity.posX;
                    this.lastPosY = this.playerEntity.posY;
                    this.lastPosZ = this.playerEntity.posZ;
                    double n7 = this.playerEntity.posX;
                    double n8 = this.playerEntity.posY;
                    double n9 = this.playerEntity.posZ;
                    float n10 = this.playerEntity.rotationYaw;
                    float n11 = this.playerEntity.rotationPitch;
                    if (c03PacketPlayer.isMoving() && c03PacketPlayer.getPositionY() == -999.0) {
                        c03PacketPlayer.setMoving("".length() != 0);
                    }
                    if (c03PacketPlayer.isMoving()) {
                        n7 = c03PacketPlayer.getPositionX();
                        n8 = c03PacketPlayer.getPositionY();
                        n9 = c03PacketPlayer.getPositionZ();
                        if (Math.abs(c03PacketPlayer.getPositionX()) > 3.0E7 || Math.abs(c03PacketPlayer.getPositionZ()) > 3.0E7) {
                            this.kickPlayerFromServer(NetHandlerPlayServer.I["   ".length()]);
                            return;
                        }
                    }
                    if (c03PacketPlayer.getRotating()) {
                        n10 = c03PacketPlayer.getYaw();
                        n11 = c03PacketPlayer.getPitch();
                    }
                    this.playerEntity.onUpdateEntity();
                    this.playerEntity.setPositionAndRotation(this.lastPosX, this.lastPosY, this.lastPosZ, n10, n11);
                    if (!this.hasMoved) {
                        return;
                    }
                    final double n12 = n7 - this.playerEntity.posX;
                    final double n13 = n8 - this.playerEntity.posY;
                    final double n14 = n9 - this.playerEntity.posZ;
                    if (n12 * n12 + n13 * n13 + n14 * n14 - (this.playerEntity.motionX * this.playerEntity.motionX + this.playerEntity.motionY * this.playerEntity.motionY + this.playerEntity.motionZ * this.playerEntity.motionZ) > 100.0 && (!this.serverController.isSinglePlayer() || !this.serverController.getServerOwner().equals(this.playerEntity.getName()))) {
                        NetHandlerPlayServer.logger.warn(String.valueOf(this.playerEntity.getName()) + NetHandlerPlayServer.I[0x6 ^ 0x2] + n12 + NetHandlerPlayServer.I[0x72 ^ 0x77] + n13 + NetHandlerPlayServer.I[0x9 ^ 0xF] + n14 + NetHandlerPlayServer.I[0xC ^ 0xB] + n12 + NetHandlerPlayServer.I[0x94 ^ 0x9C] + n13 + NetHandlerPlayServer.I[0x8C ^ 0x85] + n14 + NetHandlerPlayServer.I[0xB2 ^ 0xB8]);
                        this.setPlayerLocation(this.lastPosX, this.lastPosY, this.lastPosZ, this.playerEntity.rotationYaw, this.playerEntity.rotationPitch);
                        return;
                    }
                    final float n15 = 0.0625f;
                    final boolean empty = worldServerForDimension.getCollidingBoundingBoxes(this.playerEntity, this.playerEntity.getEntityBoundingBox().contract(n15, n15, n15)).isEmpty();
                    if (this.playerEntity.onGround && !c03PacketPlayer.isOnGround() && n13 > 0.0) {
                        this.playerEntity.jump();
                    }
                    this.playerEntity.moveEntity(n12, n13, n14);
                    this.playerEntity.onGround = c03PacketPlayer.isOnGround();
                    final double n16 = n7 - this.playerEntity.posX;
                    double n17 = n8 - this.playerEntity.posY;
                    if (n17 > -0.5 || n17 < 0.5) {
                        n17 = 0.0;
                    }
                    final double n18 = n9 - this.playerEntity.posZ;
                    final double n19 = n16 * n16 + n17 * n17 + n18 * n18;
                    int n20 = "".length();
                    if (n19 > 0.0625 && !this.playerEntity.isPlayerSleeping() && !this.playerEntity.theItemInWorldManager.isCreative()) {
                        n20 = " ".length();
                        NetHandlerPlayServer.logger.warn(String.valueOf(this.playerEntity.getName()) + NetHandlerPlayServer.I[0x71 ^ 0x7A]);
                    }
                    this.playerEntity.setPositionAndRotation(n7, n8, n9, n10, n11);
                    this.playerEntity.addMovementStat(this.playerEntity.posX - posX, this.playerEntity.posY - posY, this.playerEntity.posZ - posZ);
                    if (!this.playerEntity.noClip) {
                        final boolean empty2 = worldServerForDimension.getCollidingBoundingBoxes(this.playerEntity, this.playerEntity.getEntityBoundingBox().contract(n15, n15, n15)).isEmpty();
                        if (empty && (n20 != 0 || !empty2) && !this.playerEntity.isPlayerSleeping()) {
                            this.setPlayerLocation(this.lastPosX, this.lastPosY, this.lastPosZ, n10, n11);
                            return;
                        }
                    }
                    final AxisAlignedBB addCoord = this.playerEntity.getEntityBoundingBox().expand(n15, n15, n15).addCoord(0.0, -0.55, 0.0);
                    if (!this.serverController.isFlightAllowed() && !this.playerEntity.capabilities.allowFlying && !worldServerForDimension.checkBlockCollision(addCoord)) {
                        if (n17 >= -0.03125) {
                            this.floatingTickCount += " ".length();
                            if (this.floatingTickCount > (0xDE ^ 0x8E)) {
                                NetHandlerPlayServer.logger.warn(String.valueOf(this.playerEntity.getName()) + NetHandlerPlayServer.I[0x94 ^ 0x98]);
                                this.kickPlayerFromServer(NetHandlerPlayServer.I[0x8A ^ 0x87]);
                                return;
                            }
                        }
                    }
                    else {
                        this.floatingTickCount = "".length();
                    }
                    this.playerEntity.onGround = c03PacketPlayer.isOnGround();
                    this.serverController.getConfigurationManager().serverUpdateMountedMovingPlayer(this.playerEntity);
                    this.playerEntity.handleFalling(this.playerEntity.posY - posY3, c03PacketPlayer.isOnGround());
                    "".length();
                    if (-1 >= 1) {
                        throw null;
                    }
                }
                else if (this.networkTickCount - this.field_175090_f > (0xB4 ^ 0xA0)) {
                    this.setPlayerLocation(this.lastPosX, this.lastPosY, this.lastPosZ, this.playerEntity.rotationYaw, this.playerEntity.rotationPitch);
                }
            }
        }
    }
    
    @Override
    public void handleResourcePackStatus(final C19PacketResourcePackStatus c19PacketResourcePackStatus) {
    }
    
    private boolean func_183006_b(final C03PacketPlayer c03PacketPlayer) {
        if (Doubles.isFinite(c03PacketPlayer.getPositionX()) && Doubles.isFinite(c03PacketPlayer.getPositionY()) && Doubles.isFinite(c03PacketPlayer.getPositionZ()) && Floats.isFinite(c03PacketPlayer.getPitch()) && Floats.isFinite(c03PacketPlayer.getYaw())) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    public void sendPacket(final Packet packet) {
        if (packet instanceof S02PacketChat) {
            final S02PacketChat s02PacketChat = (S02PacketChat)packet;
            final EntityPlayer.EnumChatVisibility chatVisibility = this.playerEntity.getChatVisibility();
            if (chatVisibility == EntityPlayer.EnumChatVisibility.HIDDEN) {
                return;
            }
            if (chatVisibility == EntityPlayer.EnumChatVisibility.SYSTEM && !s02PacketChat.isChat()) {
                return;
            }
        }
        try {
            this.netManager.sendPacket(packet);
            "".length();
            if (-1 == 0) {
                throw null;
            }
        }
        catch (Throwable t) {
            final CrashReport crashReport = CrashReport.makeCrashReport(t, NetHandlerPlayServer.I[0x84 ^ 0x97]);
            crashReport.makeCategory(NetHandlerPlayServer.I[0x48 ^ 0x5C]).addCrashSectionCallable(NetHandlerPlayServer.I[0x7C ^ 0x69], new Callable<String>(this, packet) {
                final NetHandlerPlayServer this$0;
                private final Packet val$packetIn;
                
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
                        if (3 < 0) {
                            throw null;
                        }
                    }
                    return sb.toString();
                }
                
                @Override
                public String call() throws Exception {
                    return this.val$packetIn.getClass().getCanonicalName();
                }
                
                @Override
                public Object call() throws Exception {
                    return this.call();
                }
            });
            throw new ReportedException(crashReport);
        }
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
            if (1 == 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void processUpdateSign(final C12PacketUpdateSign c12PacketUpdateSign) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)c12PacketUpdateSign, this, this.playerEntity.getServerForPlayer());
        this.playerEntity.markPlayerActive();
        final WorldServer worldServerForDimension = this.serverController.worldServerForDimension(this.playerEntity.dimension);
        final BlockPos position = c12PacketUpdateSign.getPosition();
        if (worldServerForDimension.isBlockLoaded(position)) {
            final TileEntity tileEntity = worldServerForDimension.getTileEntity(position);
            if (!(tileEntity instanceof TileEntitySign)) {
                return;
            }
            final TileEntitySign tileEntitySign = (TileEntitySign)tileEntity;
            if (!tileEntitySign.getIsEditable() || tileEntitySign.getPlayer() != this.playerEntity) {
                this.serverController.logWarning(NetHandlerPlayServer.I[0xB9 ^ 0x89] + this.playerEntity.getName() + NetHandlerPlayServer.I[0x5E ^ 0x6F]);
                return;
            }
            final IChatComponent[] lines = c12PacketUpdateSign.getLines();
            int i = "".length();
            "".length();
            if (4 == 2) {
                throw null;
            }
            while (i < lines.length) {
                tileEntitySign.signText[i] = new ChatComponentText(EnumChatFormatting.getTextWithoutFormattingCodes(lines[i].getUnformattedText()));
                ++i;
            }
            tileEntitySign.markDirty();
            worldServerForDimension.markBlockForUpdate(position);
        }
    }
    
    static int[] $SWITCH_TABLE$net$minecraft$network$play$client$C16PacketClientStatus$EnumState() {
        final int[] $switch_TABLE$net$minecraft$network$play$client$C16PacketClientStatus$EnumState = NetHandlerPlayServer.$SWITCH_TABLE$net$minecraft$network$play$client$C16PacketClientStatus$EnumState;
        if ($switch_TABLE$net$minecraft$network$play$client$C16PacketClientStatus$EnumState != null) {
            return $switch_TABLE$net$minecraft$network$play$client$C16PacketClientStatus$EnumState;
        }
        final int[] $switch_TABLE$net$minecraft$network$play$client$C16PacketClientStatus$EnumState2 = new int[C16PacketClientStatus.EnumState.values().length];
        try {
            $switch_TABLE$net$minecraft$network$play$client$C16PacketClientStatus$EnumState2[C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT.ordinal()] = "   ".length();
            "".length();
            if (2 == 3) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            $switch_TABLE$net$minecraft$network$play$client$C16PacketClientStatus$EnumState2[C16PacketClientStatus.EnumState.PERFORM_RESPAWN.ordinal()] = " ".length();
            "".length();
            if (1 < 1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            $switch_TABLE$net$minecraft$network$play$client$C16PacketClientStatus$EnumState2[C16PacketClientStatus.EnumState.REQUEST_STATS.ordinal()] = "  ".length();
            "".length();
            if (true != true) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        return NetHandlerPlayServer.$SWITCH_TABLE$net$minecraft$network$play$client$C16PacketClientStatus$EnumState = $switch_TABLE$net$minecraft$network$play$client$C16PacketClientStatus$EnumState2;
    }
    
    private long currentTimeMillis() {
        return System.nanoTime() / 1000000L;
    }
    
    @Override
    public void processPlayerAbilities(final C13PacketPlayerAbilities c13PacketPlayerAbilities) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)c13PacketPlayerAbilities, this, this.playerEntity.getServerForPlayer());
        final PlayerCapabilities capabilities = this.playerEntity.capabilities;
        int isFlying;
        if (c13PacketPlayerAbilities.isFlying() && this.playerEntity.capabilities.allowFlying) {
            isFlying = " ".length();
            "".length();
            if (2 < 0) {
                throw null;
            }
        }
        else {
            isFlying = "".length();
        }
        capabilities.isFlying = (isFlying != 0);
    }
    
    @Override
    public void processCloseWindow(final C0DPacketCloseWindow c0DPacketCloseWindow) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)c0DPacketCloseWindow, this, this.playerEntity.getServerForPlayer());
        this.playerEntity.closeContainer();
    }
    
    @Override
    public void processTabComplete(final C14PacketTabComplete c14PacketTabComplete) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)c14PacketTabComplete, this, this.playerEntity.getServerForPlayer());
        final ArrayList arrayList = Lists.newArrayList();
        final Iterator<String> iterator = this.serverController.getTabCompletions(this.playerEntity, c14PacketTabComplete.getMessage(), c14PacketTabComplete.getTargetBlock()).iterator();
        "".length();
        if (-1 != -1) {
            throw null;
        }
        while (iterator.hasNext()) {
            arrayList.add(iterator.next());
        }
        this.playerEntity.playerNetServerHandler.sendPacket(new S3APacketTabComplete(arrayList.toArray(new String[arrayList.size()])));
    }
    
    @Override
    public void processHeldItemChange(final C09PacketHeldItemChange c09PacketHeldItemChange) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)c09PacketHeldItemChange, this, this.playerEntity.getServerForPlayer());
        if (c09PacketHeldItemChange.getSlotId() >= 0 && c09PacketHeldItemChange.getSlotId() < InventoryPlayer.getHotbarSize()) {
            this.playerEntity.inventory.currentItem = c09PacketHeldItemChange.getSlotId();
            this.playerEntity.markPlayerActive();
            "".length();
            if (0 < 0) {
                throw null;
            }
        }
        else {
            NetHandlerPlayServer.logger.warn(String.valueOf(this.playerEntity.getName()) + NetHandlerPlayServer.I[0x18 ^ 0xE]);
        }
    }
    
    @Override
    public void processEntityAction(final C0BPacketEntityAction c0BPacketEntityAction) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)c0BPacketEntityAction, this, this.playerEntity.getServerForPlayer());
        this.playerEntity.markPlayerActive();
        switch ($SWITCH_TABLE$net$minecraft$network$play$client$C0BPacketEntityAction$Action()[c0BPacketEntityAction.getAction().ordinal()]) {
            case 1: {
                this.playerEntity.setSneaking(" ".length() != 0);
                "".length();
                if (-1 != -1) {
                    throw null;
                }
                break;
            }
            case 2: {
                this.playerEntity.setSneaking("".length() != 0);
                "".length();
                if (2 <= 0) {
                    throw null;
                }
                break;
            }
            case 4: {
                this.playerEntity.setSprinting(" ".length() != 0);
                "".length();
                if (0 >= 3) {
                    throw null;
                }
                break;
            }
            case 5: {
                this.playerEntity.setSprinting("".length() != 0);
                "".length();
                if (3 != 3) {
                    throw null;
                }
                break;
            }
            case 3: {
                this.playerEntity.wakeUpPlayer("".length() != 0, " ".length() != 0, " ".length() != 0);
                this.hasMoved = ("".length() != 0);
                "".length();
                if (-1 >= 0) {
                    throw null;
                }
                break;
            }
            case 6: {
                if (!(this.playerEntity.ridingEntity instanceof EntityHorse)) {
                    break;
                }
                ((EntityHorse)this.playerEntity.ridingEntity).setJumpPower(c0BPacketEntityAction.getAuxData());
                "".length();
                if (2 != 2) {
                    throw null;
                }
                break;
            }
            case 7: {
                if (!(this.playerEntity.ridingEntity instanceof EntityHorse)) {
                    break;
                }
                ((EntityHorse)this.playerEntity.ridingEntity).openGUI(this.playerEntity);
                "".length();
                if (4 != 4) {
                    throw null;
                }
                break;
            }
            default: {
                throw new IllegalArgumentException(NetHandlerPlayServer.I[0x85 ^ 0x99]);
            }
        }
    }
    
    @Override
    public void handleAnimation(final C0APacketAnimation c0APacketAnimation) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)c0APacketAnimation, this, this.playerEntity.getServerForPlayer());
        this.playerEntity.markPlayerActive();
        this.playerEntity.swingItem();
    }
    
    @Override
    public void processKeepAlive(final C00PacketKeepAlive c00PacketKeepAlive) {
        if (c00PacketKeepAlive.getKey() == this.field_147378_h) {
            this.playerEntity.ping = (this.playerEntity.ping * "   ".length() + (int)(this.currentTimeMillis() - this.lastPingTime)) / (0x86 ^ 0x82);
        }
    }
    
    static {
        I();
        logger = LogManager.getLogger();
    }
    
    @Override
    public void processEnchantItem(final C11PacketEnchantItem c11PacketEnchantItem) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)c11PacketEnchantItem, this, this.playerEntity.getServerForPlayer());
        this.playerEntity.markPlayerActive();
        if (this.playerEntity.openContainer.windowId == c11PacketEnchantItem.getWindowId() && this.playerEntity.openContainer.getCanCraft(this.playerEntity) && !this.playerEntity.isSpectator()) {
            this.playerEntity.openContainer.enchantItem(this.playerEntity, c11PacketEnchantItem.getButton());
            this.playerEntity.openContainer.detectAndSendChanges();
        }
    }
    
    public NetworkManager getNetworkManager() {
        return this.netManager;
    }
    
    @Override
    public void update() {
        this.field_147366_g = ("".length() != 0);
        this.networkTickCount += " ".length();
        this.serverController.theProfiler.startSection(NetHandlerPlayServer.I["".length()]);
        if (this.networkTickCount - this.lastSentPingPacket > 40L) {
            this.lastSentPingPacket = this.networkTickCount;
            this.lastPingTime = this.currentTimeMillis();
            this.field_147378_h = (int)this.lastPingTime;
            this.sendPacket(new S00PacketKeepAlive(this.field_147378_h));
        }
        this.serverController.theProfiler.endSection();
        if (this.chatSpamThresholdCount > 0) {
            this.chatSpamThresholdCount -= " ".length();
        }
        if (this.itemDropThreshold > 0) {
            this.itemDropThreshold -= " ".length();
        }
        if (this.playerEntity.getLastActiveTime() > 0L && this.serverController.getMaxPlayerIdleMinutes() > 0 && MinecraftServer.getCurrentTimeMillis() - this.playerEntity.getLastActiveTime() > this.serverController.getMaxPlayerIdleMinutes() * (360 + 710 - 655 + 585) * (0x93 ^ 0xAF)) {
            this.kickPlayerFromServer(NetHandlerPlayServer.I[" ".length()]);
        }
    }
    
    @Override
    public void processClientStatus(final C16PacketClientStatus c16PacketClientStatus) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)c16PacketClientStatus, this, this.playerEntity.getServerForPlayer());
        this.playerEntity.markPlayerActive();
        switch ($SWITCH_TABLE$net$minecraft$network$play$client$C16PacketClientStatus$EnumState()[c16PacketClientStatus.getStatus().ordinal()]) {
            case 1: {
                if (this.playerEntity.playerConqueredTheEnd) {
                    this.playerEntity = this.serverController.getConfigurationManager().recreatePlayerEntity(this.playerEntity, "".length(), " ".length() != 0);
                    "".length();
                    if (3 <= -1) {
                        throw null;
                    }
                    break;
                }
                else if (this.playerEntity.getServerForPlayer().getWorldInfo().isHardcoreModeEnabled()) {
                    if (this.serverController.isSinglePlayer() && this.playerEntity.getName().equals(this.serverController.getServerOwner())) {
                        this.playerEntity.playerNetServerHandler.kickPlayerFromServer(NetHandlerPlayServer.I[0x50 ^ 0x70]);
                        this.serverController.deleteWorldAndStopServer();
                        "".length();
                        if (-1 == 3) {
                            throw null;
                        }
                        break;
                    }
                    else {
                        ((UserList<K, UserListBansEntry>)this.serverController.getConfigurationManager().getBannedPlayers()).addEntry(new UserListBansEntry(this.playerEntity.getGameProfile(), null, NetHandlerPlayServer.I[0x10 ^ 0x31], null, NetHandlerPlayServer.I[0x57 ^ 0x75]));
                        this.playerEntity.playerNetServerHandler.kickPlayerFromServer(NetHandlerPlayServer.I[0xB6 ^ 0x95]);
                        "".length();
                        if (1 >= 2) {
                            throw null;
                        }
                        break;
                    }
                }
                else {
                    if (this.playerEntity.getHealth() > 0.0f) {
                        return;
                    }
                    this.playerEntity = this.serverController.getConfigurationManager().recreatePlayerEntity(this.playerEntity, "".length(), "".length() != 0);
                    "".length();
                    if (2 != 2) {
                        throw null;
                    }
                    break;
                }
                break;
            }
            case 2: {
                this.playerEntity.getStatFile().func_150876_a(this.playerEntity);
                "".length();
                if (0 >= 2) {
                    throw null;
                }
                break;
            }
            case 3: {
                this.playerEntity.triggerAchievement(AchievementList.openInventory);
                break;
            }
        }
    }
    
    @Override
    public void processInput(final C0CPacketInput c0CPacketInput) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)c0CPacketInput, this, this.playerEntity.getServerForPlayer());
        this.playerEntity.setEntityActionState(c0CPacketInput.getStrafeSpeed(), c0CPacketInput.getForwardSpeed(), c0CPacketInput.isJumping(), c0CPacketInput.isSneaking());
    }
    
    @Override
    public void processChatMessage(final C01PacketChatMessage c01PacketChatMessage) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)c01PacketChatMessage, this, this.playerEntity.getServerForPlayer());
        if (this.playerEntity.getChatVisibility() == EntityPlayer.EnumChatVisibility.HIDDEN) {
            final ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation(NetHandlerPlayServer.I[0x99 ^ 0x8E], new Object["".length()]);
            chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.RED);
            this.sendPacket(new S02PacketChat(chatComponentTranslation));
            "".length();
            if (1 >= 4) {
                throw null;
            }
        }
        else {
            this.playerEntity.markPlayerActive();
            final String normalizeSpace = StringUtils.normalizeSpace(c01PacketChatMessage.getMessage());
            int i = "".length();
            "".length();
            if (1 < -1) {
                throw null;
            }
            while (i < normalizeSpace.length()) {
                if (!ChatAllowedCharacters.isAllowedCharacter(normalizeSpace.charAt(i))) {
                    this.kickPlayerFromServer(NetHandlerPlayServer.I[0xAC ^ 0xB4]);
                    return;
                }
                ++i;
            }
            if (normalizeSpace.startsWith(NetHandlerPlayServer.I[0xF ^ 0x16])) {
                this.handleSlashCommand(normalizeSpace);
                "".length();
                if (0 <= -1) {
                    throw null;
                }
            }
            else {
                final String s = NetHandlerPlayServer.I[0xDA ^ 0xC0];
                final Object[] array = new Object["  ".length()];
                array["".length()] = this.playerEntity.getDisplayName();
                array[" ".length()] = normalizeSpace;
                this.serverController.getConfigurationManager().sendChatMsgImpl(new ChatComponentTranslation(s, array), "".length() != 0);
            }
            this.chatSpamThresholdCount += (0x67 ^ 0x73);
            if (this.chatSpamThresholdCount > 52 + 105 - 25 + 68 && !this.serverController.getConfigurationManager().canSendCommands(this.playerEntity.getGameProfile())) {
                this.kickPlayerFromServer(NetHandlerPlayServer.I[0x43 ^ 0x58]);
            }
        }
    }
    
    static int[] $SWITCH_TABLE$net$minecraft$network$play$client$C07PacketPlayerDigging$Action() {
        final int[] $switch_TABLE$net$minecraft$network$play$client$C07PacketPlayerDigging$Action = NetHandlerPlayServer.$SWITCH_TABLE$net$minecraft$network$play$client$C07PacketPlayerDigging$Action;
        if ($switch_TABLE$net$minecraft$network$play$client$C07PacketPlayerDigging$Action != null) {
            return $switch_TABLE$net$minecraft$network$play$client$C07PacketPlayerDigging$Action;
        }
        final int[] $switch_TABLE$net$minecraft$network$play$client$C07PacketPlayerDigging$Action2 = new int[C07PacketPlayerDigging.Action.values().length];
        try {
            $switch_TABLE$net$minecraft$network$play$client$C07PacketPlayerDigging$Action2[C07PacketPlayerDigging.Action.ABORT_DESTROY_BLOCK.ordinal()] = "  ".length();
            "".length();
            if (1 <= -1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            $switch_TABLE$net$minecraft$network$play$client$C07PacketPlayerDigging$Action2[C07PacketPlayerDigging.Action.DROP_ALL_ITEMS.ordinal()] = (0x9C ^ 0x98);
            "".length();
            if (0 >= 1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            $switch_TABLE$net$minecraft$network$play$client$C07PacketPlayerDigging$Action2[C07PacketPlayerDigging.Action.DROP_ITEM.ordinal()] = (0x49 ^ 0x4C);
            "".length();
            if (0 >= 3) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            $switch_TABLE$net$minecraft$network$play$client$C07PacketPlayerDigging$Action2[C07PacketPlayerDigging.Action.RELEASE_USE_ITEM.ordinal()] = (0xB ^ 0xD);
            "".length();
            if (3 == 0) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
        try {
            $switch_TABLE$net$minecraft$network$play$client$C07PacketPlayerDigging$Action2[C07PacketPlayerDigging.Action.START_DESTROY_BLOCK.ordinal()] = " ".length();
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError5) {}
        try {
            $switch_TABLE$net$minecraft$network$play$client$C07PacketPlayerDigging$Action2[C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK.ordinal()] = "   ".length();
            "".length();
            if (4 < -1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError6) {}
        return NetHandlerPlayServer.$SWITCH_TABLE$net$minecraft$network$play$client$C07PacketPlayerDigging$Action = $switch_TABLE$net$minecraft$network$play$client$C07PacketPlayerDigging$Action2;
    }
    
    private static void I() {
        (I = new String[0xC0 ^ 0x8B])["".length()] = I(")\u001c=:*.\u0010./", "ByXJk");
        NetHandlerPlayServer.I[" ".length()] = I("4#?I:\f:/I0\b)$I;\t /I4\u0002>j\u001d=\u0002l&\u0006<\nm", "mLJiR");
        NetHandlerPlayServer.I["  ".length()] = I("(\u0001\"\u0011\n\b\u000bt\u001d\t\u0017\nt\u0000\u0007\u0002\u00041\u0004F\u0013\n7\u0015\u000f\u0017\n0", "aoTpf");
        NetHandlerPlayServer.I["   ".length()] = I("8\"\r7\u0002\u0010\"A\"\n\u0002'\u0015;\n\u001f", "qNaRe");
        NetHandlerPlayServer.I[0x27 ^ 0x23] = I("s\u001b\u000e\u0014*7V\u0015\r s\u0007\u0014\u000b,8\u001a\u0018Co", "SvabO");
        NetHandlerPlayServer.I[0xC0 ^ 0xC5] = I("a", "MFTLN");
        NetHandlerPlayServer.I[0xBF ^ 0xB9] = I("\\", "pgdJQ");
        NetHandlerPlayServer.I[0x7E ^ 0x79] = I("z}", "ZUGBe");
        NetHandlerPlayServer.I[0x96 ^ 0x9E] = I("Kq", "gQasW");
        NetHandlerPlayServer.I[0x30 ^ 0x39] = I("~T", "RtjBL");
        NetHandlerPlayServer.I[0x23 ^ 0x29] = I("\u007f", "VSLCa");
        NetHandlerPlayServer.I[0xB6 ^ 0xBD] = I("b\u0004\u001a\u0014\u001f&I\u0002\u0010\u0015,\u000e\u0019\u001b[", "Biubz");
        NetHandlerPlayServer.I[0x99 ^ 0x95] = I("P\u0000\u0005&f\u001b\u001e\u0007>#\u0014W\u0002:4P\u0011\b:'\u0004\u001e\n2f\u0004\u0018\u000bu*\u001f\u0019\u0003t", "pwdUF");
        NetHandlerPlayServer.I[0xC ^ 0x1] = I(".*\u0000\u0007/\u000ff\u0010\u001da\u0006)\rN$\u0006'\u001b\u0002$\ff\u0016\u0000a\u001c.\u0010\u001da\u001b#\u000b\u0018$\u001a", "hFynA");
        NetHandlerPlayServer.I[0x90 ^ 0x9E] = I("\u000b6\u00193$+<O\"$#!\n h#;\u001b;',", "BXoRH");
        NetHandlerPlayServer.I[0x77 ^ 0x78] = I("$4,\u0002'h5*\u0001\u000b/&-", "FAEnC");
        NetHandlerPlayServer.I[0x8A ^ 0x9A] = I("s\u0007\u0000!\u0000s\b\u0000<\u001a6\b\u001b;\u001b=QO", "SkoRt");
        NetHandlerPlayServer.I[0x47 ^ 0x56] = I("\u001a-+2\u0013\u00074&?\u001f\u0005v7*\u001b\u000e=5h\u0016\u0012>3", "wXGFz");
        NetHandlerPlayServer.I[0x68 ^ 0x7A] = I("\u0011\u001a)\u0017\u0016+\u0000!G\u0015+\u0000!\u000b\u00032\u0002'\u001e\u00030N5\u0002\u00144\u000b4G\u00071N6\u000b\u0007;\u000b4G\n-\t!\u0002\u0002b\u00013\u0013", "BnFgf");
        NetHandlerPlayServer.I[0x53 ^ 0x40] = I(")2\u001c<=\u00140R(5\u0019<\u0017,", "zWrXT");
        NetHandlerPlayServer.I[0x2C ^ 0x38] = I("\u0011\u0003\u0010\u000705B\u0011\t</\u0005S\u001f0/\u0016", "AbslU");
        NetHandlerPlayServer.I[0x8D ^ 0x98] = I("7\u0018\f8\r\u0013Y\f?\t\u0014\n", "gyoSh");
        NetHandlerPlayServer.I[0x9F ^ 0x89] = I("r<\u0003\u001f\f6h\u0005\u0019I!-\u0005V\b<h\u0018\u0018\u001f3$\u0018\u0012I1)\u0003\u0004\u00007,Q\u001f\u001d7%", "RHqvi");
        NetHandlerPlayServer.I[0x9E ^ 0x89] = I("(\u0006\u0014\u0005_(\u000f\u001b\u001f\u001e?=\u0010\u001f\u0015", "Knuqq");
        NetHandlerPlayServer.I[0x68 ^ 0x70] = I("\r\u0000\t&\u001e%\u0000E \u0011%\u001e\u0004 \r!\u001e\u0016c\u0010*L\u0006+\u00180", "DleCy");
        NetHandlerPlayServer.I[0x8D ^ 0x94] = I("X", "wmSiB");
        NetHandlerPlayServer.I[0x1D ^ 0x7] = I("-\u001b\u0016\u0013y:\n\u0007\u0002y:\u0016\u000f\u0013", "NswgW");
        NetHandlerPlayServer.I[0x57 ^ 0x4C] = I("\n: \u0011*\u0000=6\u00111@ #\u0013(", "nSSrE");
        NetHandlerPlayServer.I[0x4C ^ 0x50] = I("\f$\f/\u0003,.Z-\u0003,/\u0014:O&%\u0017#\u000e+.[", "EJzNo");
        NetHandlerPlayServer.I[0x22 ^ 0x3F] = I("\u0017\u0001\u0007\u0010\u001b&\u0001\u001a\u001b\u0011v\u0001\u001cU\u0017\"\u0001\u0012\u0016\u001dv\u0014\u001dU\u001f8\u0003\u0012\u0019\u001f2U\u0016\u001b\u0002?\u0001\n", "Vusuv");
        NetHandlerPlayServer.I[0x3D ^ 0x23] = I("\n*\t!=(f", "ZFhXX");
        NetHandlerPlayServer.I[0x47 ^ 0x58] = I("y3$\u0002'=g\"\u0004b83\"\n!2g7\u0005b0) \n.0#v\u000e,-.\"\u0012", "YGVkB");
        NetHandlerPlayServer.I[0xE0 ^ 0xC0] = I("\u000e>\u001fI 6'\u000fI,>4\u000eGh\u00100\u0007\fh8'\u000f\u001bdw<\u000b\u0007dw8\u001eN;w6\u000b\u0004-w>\u001c\f:v", "WQjiH");
        NetHandlerPlayServer.I[0xD ^ 0x2C] = I("I+?0j\u000b\u0007#1j\r\u001d#1j\u0015\u001a5e-\u0000\u001f5l", "arPEJ");
        NetHandlerPlayServer.I[0x2E ^ 0xC] = I("\u0011\u0015\u0004\u00072u\u0019\u000bS\u00124\u0002\u0001\u00105'\u0015", "UpesZ");
        NetHandlerPlayServer.I[0x67 ^ 0x44] = I("1::S\r\t#*S\u0001\u00010+]E/4\"\u0016E\u0007#*\u0001IH8.\u001dIH<;T\u0016H2.\u001e\u0000H:9\u0016\u0017I", "hUOse");
        NetHandlerPlayServer.I[0x37 ^ 0x13] = I("\b=\u000b4\u001e\u000f?\u0010>\u00013\u0005\u00050", "JQdWu");
        NetHandlerPlayServer.I[0x5C ^ 0x79] = I("1<=\u0006<6>&\f#\n\u00043\u0002", "sPReW");
        NetHandlerPlayServer.I[0x11 ^ 0x37] = I("\u000e", "vcrsq");
        NetHandlerPlayServer.I[0xA9 ^ 0x8E] = I("(", "QAKzz");
        NetHandlerPlayServer.I[0x3F ^ 0x17] = I("\u0011", "kusgv");
        NetHandlerPlayServer.I[0x81 ^ 0xA8] = I("6", "NTEuf");
        NetHandlerPlayServer.I[0x70 ^ 0x5A] = I("\u0015", "ldJkJ");
        NetHandlerPlayServer.I[0x2D ^ 0x6] = I("1", "KeuGi");
        NetHandlerPlayServer.I[0x1C ^ 0x30] = I("\u0010", "hQUGc");
        NetHandlerPlayServer.I[0x4C ^ 0x61] = I("\u0000", "yGWMY");
        NetHandlerPlayServer.I[0x16 ^ 0x38] = I("\u001f", "eYZhU");
        NetHandlerPlayServer.I[0x43 ^ 0x6C] = I(")\u001665\u001d.\u0014-?\u0002\u0012.81", "kzYVv");
        NetHandlerPlayServer.I[0x9F ^ 0xAF] = I("\u0018+().:g", "HGIPK");
        NetHandlerPlayServer.I[0x3C ^ 0xD] = I("y\u0004\u001d95y\u001a\u001a#$=N\u001c%a:\u0006\t$&<N\u0006%/t\u000b\f#58\f\u0004/a*\u0007\u000f$", "YnhJA");
        NetHandlerPlayServer.I[0x8 ^ 0x3A] = I("!\b.\u0015\u001d\b\"&", "lKRWX");
        NetHandlerPlayServer.I[0x1A ^ 0x29] = I("\"<?\u0015\"\u00026i\u0016!\u00049i\u0000/\fs", "kRItN");
        NetHandlerPlayServer.I[0xAE ^ 0x9A] = I("\u0006\u001b\u0004\f\u0001", "vzcir");
        NetHandlerPlayServer.I[0x60 ^ 0x55] = I("\u001a\u0018%0\u0016", "jyBUe");
        NetHandlerPlayServer.I[0xB0 ^ 0x86] = I("6\u0017\u0005=&\u001b_\u0004q*\u0014\u0016\u0014='U\u001a\u001f>)U\u0011\u001e7-", "uxpQB");
        NetHandlerPlayServer.I[0x7E ^ 0x49] = I("#\u0006,\u00051\u0007\">", "nEPGb");
        NetHandlerPlayServer.I[0xA1 ^ 0x99] = I("\u0011\u000b2'\u00161\u0001d$\u00157\u000ed2\u001b?D", "XeDFz");
        NetHandlerPlayServer.I[0x70 ^ 0x49] = I("\t\u0016?/\u0000\u001a", "hcKGo");
        NetHandlerPlayServer.I[0x7C ^ 0x46] = I("\r=59$", "yTAUA");
        NetHandlerPlayServer.I[0x56 ^ 0x6D] = I("1\u001c\u0005\u001d\u0017", "Euqqr");
        NetHandlerPlayServer.I[0x70 ^ 0x4C] = I("\u0007\u000b*,$", "wjMIW");
        NetHandlerPlayServer.I[0x59 ^ 0x64] = I("\u0001+\u000e\u0000\u001e", "qJiem");
        NetHandlerPlayServer.I[0x29 ^ 0x17] = I("\"'\u001e4.\u000fo\u001fx9\b/\u0005x(\u000e'\u0000", "aHkXJ");
        NetHandlerPlayServer.I[0x70 ^ 0x4F] = I(">&\u00136' \u0000\u0003", "seobU");
        NetHandlerPlayServer.I[0xCC ^ 0x8C] = I("\u001b.:\r)6f;A>=-*\u00029x5=\u0000)=", "XAOaM");
        NetHandlerPlayServer.I[0x5D ^ 0x1C] = I("4,\u001f\u0019,\u000f,\u00075", "yocXH");
        NetHandlerPlayServer.I[0x46 ^ 0x4] = I("\"4\u0001\u001a\u0003'5Y9\u00037\u0015\u00196\u000e/5\u0013", "CPwWl");
        NetHandlerPlayServer.I[0x3D ^ 0x7E] = I("", "tJKbS");
        NetHandlerPlayServer.I[0x69 ^ 0x2D] = I("#%9<5&$a\u0002?6\u0002 \u001c7#/+_)7\",\u0014)1", "BAOqZ");
        NetHandlerPlayServer.I[0xE5 ^ 0xA0] = I(";\u001a\u0018\u001a)\u0016R\u0019V>\u001d\u0001M\u0015\"\u0015\u0018\f\u0018)X\u0017\u0001\u0019.\u0013", "xumvM");
        NetHandlerPlayServer.I[0x19 ^ 0x5F] = I("8.0<\u0015=/h\u001f\u0015-\u000b*\u001d\u0015./\"", "YJFqz");
        NetHandlerPlayServer.I[0xDD ^ 0x9A] = I("4\f.'\u000e\u0018,=\u000b", "yORek");
        NetHandlerPlayServer.I[0x14 ^ 0x5C] = I("$7\r\u000f(\t\u007f\fC?\u0002,X\u0001)\u0006;\u0017\r", "gXxcL");
        NetHandlerPlayServer.I[0x5A ^ 0x13] = I("*\f1 \u0015\u0002\"\u0003\b\f\u0002", "gOMia");
        NetHandlerPlayServer.I[0x56 ^ 0x1C] = I("", "sVcGn");
    }
    
    @Override
    public void handleSpectate(final C18PacketSpectate c18PacketSpectate) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)c18PacketSpectate, this, this.playerEntity.getServerForPlayer());
        if (this.playerEntity.isSpectator()) {
            Entity entity = null;
            final WorldServer[] worldServers;
            final int length = (worldServers = this.serverController.worldServers).length;
            int i = "".length();
            "".length();
            if (1 >= 3) {
                throw null;
            }
            while (i < length) {
                final WorldServer worldServer = worldServers[i];
                if (worldServer != null) {
                    entity = c18PacketSpectate.getEntity(worldServer);
                    if (entity != null) {
                        "".length();
                        if (1 <= -1) {
                            throw null;
                        }
                        break;
                    }
                }
                ++i;
            }
            if (entity != null) {
                this.playerEntity.setSpectatingEntity(this.playerEntity);
                this.playerEntity.mountEntity(null);
                if (entity.worldObj != this.playerEntity.worldObj) {
                    final WorldServer serverForPlayer = this.playerEntity.getServerForPlayer();
                    final WorldServer worldServer2 = (WorldServer)entity.worldObj;
                    this.playerEntity.dimension = entity.dimension;
                    this.sendPacket(new S07PacketRespawn(this.playerEntity.dimension, serverForPlayer.getDifficulty(), serverForPlayer.getWorldInfo().getTerrainType(), this.playerEntity.theItemInWorldManager.getGameType()));
                    serverForPlayer.removePlayerEntityDangerously(this.playerEntity);
                    this.playerEntity.isDead = ("".length() != 0);
                    this.playerEntity.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch);
                    if (this.playerEntity.isEntityAlive()) {
                        serverForPlayer.updateEntityWithOptionalForce(this.playerEntity, "".length() != 0);
                        worldServer2.spawnEntityInWorld(this.playerEntity);
                        worldServer2.updateEntityWithOptionalForce(this.playerEntity, "".length() != 0);
                    }
                    this.playerEntity.setWorld(worldServer2);
                    this.serverController.getConfigurationManager().preparePlayer(this.playerEntity, serverForPlayer);
                    this.playerEntity.setPositionAndUpdate(entity.posX, entity.posY, entity.posZ);
                    this.playerEntity.theItemInWorldManager.setWorld(worldServer2);
                    this.serverController.getConfigurationManager().updateTimeAndWeatherForPlayer(this.playerEntity, worldServer2);
                    this.serverController.getConfigurationManager().syncPlayerInventory(this.playerEntity);
                    "".length();
                    if (1 == 2) {
                        throw null;
                    }
                }
                else {
                    this.playerEntity.setPositionAndUpdate(entity.posX, entity.posY, entity.posZ);
                }
            }
        }
    }
    
    private void handleSlashCommand(final String s) {
        this.serverController.getCommandManager().executeCommand(this.playerEntity, s);
    }
    
    @Override
    public void processClientSettings(final C15PacketClientSettings c15PacketClientSettings) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)c15PacketClientSettings, this, this.playerEntity.getServerForPlayer());
        this.playerEntity.handleClientSettings(c15PacketClientSettings);
    }
    
    public void kickPlayerFromServer(final String s) {
        final ChatComponentText chatComponentText = new ChatComponentText(s);
        this.netManager.sendPacket(new S40PacketDisconnect(chatComponentText), (GenericFutureListener<? extends Future<? super Void>>)new GenericFutureListener<Future<? super Void>>(this, chatComponentText) {
            final NetHandlerPlayServer this$0;
            private final ChatComponentText val$chatcomponenttext;
            
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
                    if (2 == 4) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            public void operationComplete(final Future<? super Void> future) throws Exception {
                this.this$0.netManager.closeChannel(this.val$chatcomponenttext);
            }
        }, (GenericFutureListener<? extends Future<? super Void>>[])new GenericFutureListener["".length()]);
        this.netManager.disableAutoRead();
        Futures.getUnchecked((java.util.concurrent.Future)this.serverController.addScheduledTask(new Runnable(this) {
            final NetHandlerPlayServer this$0;
            
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
                    if (-1 != -1) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            @Override
            public void run() {
                this.this$0.netManager.checkDisconnected();
            }
        }));
    }
    
    @Override
    public void processUseEntity(final C02PacketUseEntity c02PacketUseEntity) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)c02PacketUseEntity, this, this.playerEntity.getServerForPlayer());
        final Entity entityFromWorld = c02PacketUseEntity.getEntityFromWorld(this.serverController.worldServerForDimension(this.playerEntity.dimension));
        this.playerEntity.markPlayerActive();
        if (entityFromWorld != null) {
            final boolean canEntityBeSeen = this.playerEntity.canEntityBeSeen(entityFromWorld);
            double n = 36.0;
            if (!canEntityBeSeen) {
                n = 9.0;
            }
            if (this.playerEntity.getDistanceSqToEntity(entityFromWorld) < n) {
                if (c02PacketUseEntity.getAction() == C02PacketUseEntity.Action.INTERACT) {
                    this.playerEntity.interactWith(entityFromWorld);
                    "".length();
                    if (4 == -1) {
                        throw null;
                    }
                }
                else if (c02PacketUseEntity.getAction() == C02PacketUseEntity.Action.INTERACT_AT) {
                    entityFromWorld.interactAt(this.playerEntity, c02PacketUseEntity.getHitVec());
                    "".length();
                    if (3 == 0) {
                        throw null;
                    }
                }
                else if (c02PacketUseEntity.getAction() == C02PacketUseEntity.Action.ATTACK) {
                    if (entityFromWorld instanceof EntityItem || entityFromWorld instanceof EntityXPOrb || entityFromWorld instanceof EntityArrow || entityFromWorld == this.playerEntity) {
                        this.kickPlayerFromServer(NetHandlerPlayServer.I[0x69 ^ 0x74]);
                        this.serverController.logWarning(NetHandlerPlayServer.I[0x4 ^ 0x1A] + this.playerEntity.getName() + NetHandlerPlayServer.I[0x83 ^ 0x9C]);
                        return;
                    }
                    this.playerEntity.attackTargetEntityWithCurrentItem(entityFromWorld);
                }
            }
        }
    }
    
    @Override
    public void processConfirmTransaction(final C0FPacketConfirmTransaction c0FPacketConfirmTransaction) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)c0FPacketConfirmTransaction, this, this.playerEntity.getServerForPlayer());
        final Short n = this.field_147372_n.lookup(this.playerEntity.openContainer.windowId);
        if (n != null && c0FPacketConfirmTransaction.getUid() == n && this.playerEntity.openContainer.windowId == c0FPacketConfirmTransaction.getWindowId() && !this.playerEntity.openContainer.getCanCraft(this.playerEntity) && !this.playerEntity.isSpectator()) {
            this.playerEntity.openContainer.setCanCraft(this.playerEntity, " ".length() != 0);
        }
    }
    
    @Override
    public void processPlayerBlockPlacement(final C08PacketPlayerBlockPlacement c08PacketPlayerBlockPlacement) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)c08PacketPlayerBlockPlacement, this, this.playerEntity.getServerForPlayer());
        final WorldServer worldServerForDimension = this.serverController.worldServerForDimension(this.playerEntity.dimension);
        final ItemStack currentItem = this.playerEntity.inventory.getCurrentItem();
        int n = "".length();
        final BlockPos position = c08PacketPlayerBlockPlacement.getPosition();
        final EnumFacing front = EnumFacing.getFront(c08PacketPlayerBlockPlacement.getPlacedBlockDirection());
        this.playerEntity.markPlayerActive();
        if (c08PacketPlayerBlockPlacement.getPlacedBlockDirection() == 38 + 244 - 96 + 69) {
            if (currentItem == null) {
                return;
            }
            this.playerEntity.theItemInWorldManager.tryUseItem(this.playerEntity, worldServerForDimension, currentItem);
            "".length();
            if (4 == 1) {
                throw null;
            }
        }
        else if (position.getY() < this.serverController.getBuildLimit() - " ".length() || (front != EnumFacing.UP && position.getY() < this.serverController.getBuildLimit())) {
            if (this.hasMoved && this.playerEntity.getDistanceSq(position.getX() + 0.5, position.getY() + 0.5, position.getZ() + 0.5) < 64.0 && !this.serverController.isBlockProtected(worldServerForDimension, position, this.playerEntity) && worldServerForDimension.getWorldBorder().contains(position)) {
                this.playerEntity.theItemInWorldManager.activateBlockOrUseItem(this.playerEntity, worldServerForDimension, currentItem, position, front, c08PacketPlayerBlockPlacement.getPlacedBlockOffsetX(), c08PacketPlayerBlockPlacement.getPlacedBlockOffsetY(), c08PacketPlayerBlockPlacement.getPlacedBlockOffsetZ());
            }
            n = " ".length();
            "".length();
            if (2 <= -1) {
                throw null;
            }
        }
        else {
            final String s = NetHandlerPlayServer.I[0xA5 ^ 0xAA];
            final Object[] array = new Object[" ".length()];
            array["".length()] = this.serverController.getBuildLimit();
            final ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation(s, array);
            chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.RED);
            this.playerEntity.playerNetServerHandler.sendPacket(new S02PacketChat(chatComponentTranslation));
            n = " ".length();
        }
        if (n != 0) {
            this.playerEntity.playerNetServerHandler.sendPacket(new S23PacketBlockChange(worldServerForDimension, position));
            this.playerEntity.playerNetServerHandler.sendPacket(new S23PacketBlockChange(worldServerForDimension, position.offset(front)));
        }
        ItemStack currentItem2 = this.playerEntity.inventory.getCurrentItem();
        if (currentItem2 != null && currentItem2.stackSize == 0) {
            this.playerEntity.inventory.mainInventory[this.playerEntity.inventory.currentItem] = null;
            currentItem2 = null;
        }
        if (currentItem2 == null || currentItem2.getMaxItemUseDuration() == 0) {
            this.playerEntity.isChangingQuantityOnly = (" ".length() != 0);
            this.playerEntity.inventory.mainInventory[this.playerEntity.inventory.currentItem] = ItemStack.copyItemStack(this.playerEntity.inventory.mainInventory[this.playerEntity.inventory.currentItem]);
            final Slot slotFromInventory = this.playerEntity.openContainer.getSlotFromInventory(this.playerEntity.inventory, this.playerEntity.inventory.currentItem);
            this.playerEntity.openContainer.detectAndSendChanges();
            this.playerEntity.isChangingQuantityOnly = ("".length() != 0);
            if (!ItemStack.areItemStacksEqual(this.playerEntity.inventory.getCurrentItem(), c08PacketPlayerBlockPlacement.getStack())) {
                this.sendPacket(new S2FPacketSetSlot(this.playerEntity.openContainer.windowId, slotFromInventory.slotNumber, this.playerEntity.inventory.getCurrentItem()));
            }
        }
    }
    
    @Override
    public void processPlayerDigging(final C07PacketPlayerDigging c07PacketPlayerDigging) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)c07PacketPlayerDigging, this, this.playerEntity.getServerForPlayer());
        final WorldServer worldServerForDimension = this.serverController.worldServerForDimension(this.playerEntity.dimension);
        final BlockPos position = c07PacketPlayerDigging.getPosition();
        this.playerEntity.markPlayerActive();
        switch ($SWITCH_TABLE$net$minecraft$network$play$client$C07PacketPlayerDigging$Action()[c07PacketPlayerDigging.getStatus().ordinal()]) {
            case 5: {
                if (!this.playerEntity.isSpectator()) {
                    this.playerEntity.dropOneItem("".length() != 0);
                }
            }
            case 4: {
                if (!this.playerEntity.isSpectator()) {
                    this.playerEntity.dropOneItem(" ".length() != 0);
                }
            }
            case 6: {
                this.playerEntity.stopUsingItem();
            }
            case 1:
            case 2:
            case 3: {
                final double n = this.playerEntity.posX - (position.getX() + 0.5);
                final double n2 = this.playerEntity.posY - (position.getY() + 0.5) + 1.5;
                final double n3 = this.playerEntity.posZ - (position.getZ() + 0.5);
                if (n * n + n2 * n2 + n3 * n3 > 36.0) {
                    return;
                }
                if (position.getY() >= this.serverController.getBuildLimit()) {
                    return;
                }
                if (c07PacketPlayerDigging.getStatus() == C07PacketPlayerDigging.Action.START_DESTROY_BLOCK) {
                    if (!this.serverController.isBlockProtected(worldServerForDimension, position, this.playerEntity) && worldServerForDimension.getWorldBorder().contains(position)) {
                        this.playerEntity.theItemInWorldManager.onBlockClicked(position, c07PacketPlayerDigging.getFacing());
                        "".length();
                        if (3 != 3) {
                            throw null;
                        }
                    }
                    else {
                        this.playerEntity.playerNetServerHandler.sendPacket(new S23PacketBlockChange(worldServerForDimension, position));
                        "".length();
                        if (1 < 0) {
                            throw null;
                        }
                    }
                }
                else {
                    if (c07PacketPlayerDigging.getStatus() == C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK) {
                        this.playerEntity.theItemInWorldManager.blockRemoving(position);
                        "".length();
                        if (3 <= 0) {
                            throw null;
                        }
                    }
                    else if (c07PacketPlayerDigging.getStatus() == C07PacketPlayerDigging.Action.ABORT_DESTROY_BLOCK) {
                        this.playerEntity.theItemInWorldManager.cancelDestroyingBlock();
                    }
                    if (worldServerForDimension.getBlockState(position).getBlock().getMaterial() != Material.air) {
                        this.playerEntity.playerNetServerHandler.sendPacket(new S23PacketBlockChange(worldServerForDimension, position));
                    }
                }
            }
            default: {
                throw new IllegalArgumentException(NetHandlerPlayServer.I[0x75 ^ 0x7B]);
            }
        }
    }
    
    @Override
    public void processClickWindow(final C0EPacketClickWindow c0EPacketClickWindow) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)c0EPacketClickWindow, this, this.playerEntity.getServerForPlayer());
        this.playerEntity.markPlayerActive();
        if (this.playerEntity.openContainer.windowId == c0EPacketClickWindow.getWindowId() && this.playerEntity.openContainer.getCanCraft(this.playerEntity)) {
            if (this.playerEntity.isSpectator()) {
                final ArrayList arrayList = Lists.newArrayList();
                int i = "".length();
                "".length();
                if (false) {
                    throw null;
                }
                while (i < this.playerEntity.openContainer.inventorySlots.size()) {
                    arrayList.add(this.playerEntity.openContainer.inventorySlots.get(i).getStack());
                    ++i;
                }
                this.playerEntity.updateCraftingInventory(this.playerEntity.openContainer, arrayList);
                "".length();
                if (0 >= 1) {
                    throw null;
                }
            }
            else if (ItemStack.areItemStacksEqual(c0EPacketClickWindow.getClickedItem(), this.playerEntity.openContainer.slotClick(c0EPacketClickWindow.getSlotId(), c0EPacketClickWindow.getUsedButton(), c0EPacketClickWindow.getMode(), this.playerEntity))) {
                this.playerEntity.playerNetServerHandler.sendPacket(new S32PacketConfirmTransaction(c0EPacketClickWindow.getWindowId(), c0EPacketClickWindow.getActionNumber(), (boolean)(" ".length() != 0)));
                this.playerEntity.isChangingQuantityOnly = (" ".length() != 0);
                this.playerEntity.openContainer.detectAndSendChanges();
                this.playerEntity.updateHeldItem();
                this.playerEntity.isChangingQuantityOnly = ("".length() != 0);
                "".length();
                if (2 != 2) {
                    throw null;
                }
            }
            else {
                this.field_147372_n.addKey(this.playerEntity.openContainer.windowId, c0EPacketClickWindow.getActionNumber());
                this.playerEntity.playerNetServerHandler.sendPacket(new S32PacketConfirmTransaction(c0EPacketClickWindow.getWindowId(), c0EPacketClickWindow.getActionNumber(), (boolean)("".length() != 0)));
                this.playerEntity.openContainer.setCanCraft(this.playerEntity, "".length() != 0);
                final ArrayList arrayList2 = Lists.newArrayList();
                int j = "".length();
                "".length();
                if (3 == 2) {
                    throw null;
                }
                while (j < this.playerEntity.openContainer.inventorySlots.size()) {
                    arrayList2.add(this.playerEntity.openContainer.inventorySlots.get(j).getStack());
                    ++j;
                }
                this.playerEntity.updateCraftingInventory(this.playerEntity.openContainer, arrayList2);
            }
        }
    }
    
    @Override
    public void processVanilla250Packet(final C17PacketCustomPayload c17PacketCustomPayload) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)c17PacketCustomPayload, this, this.playerEntity.getServerForPlayer());
        if (NetHandlerPlayServer.I[0x8D ^ 0xBF].equals(c17PacketCustomPayload.getChannelName())) {
            final PacketBuffer packetBuffer = new PacketBuffer(Unpooled.wrappedBuffer((ByteBuf)c17PacketCustomPayload.getBufferData()));
            try {
                final ItemStack itemStackFromBuffer = packetBuffer.readItemStackFromBuffer();
                if (itemStackFromBuffer != null) {
                    if (!ItemWritableBook.isNBTValid(itemStackFromBuffer.getTagCompound())) {
                        throw new IOException(NetHandlerPlayServer.I[0x33 ^ 0x0]);
                    }
                    final ItemStack currentItem = this.playerEntity.inventory.getCurrentItem();
                    if (currentItem == null) {
                        return;
                    }
                    if (itemStackFromBuffer.getItem() == Items.writable_book && itemStackFromBuffer.getItem() == currentItem.getItem()) {
                        currentItem.setTagInfo(NetHandlerPlayServer.I[0x29 ^ 0x1D], itemStackFromBuffer.getTagCompound().getTagList(NetHandlerPlayServer.I[0x40 ^ 0x75], 0x87 ^ 0x8F));
                    }
                    return;
                }
            }
            catch (Exception ex) {
                NetHandlerPlayServer.logger.error(NetHandlerPlayServer.I[0x62 ^ 0x54], (Throwable)ex);
                return;
            }
            finally {
                packetBuffer.release();
            }
            packetBuffer.release();
            return;
        }
        if (NetHandlerPlayServer.I[0xB6 ^ 0x81].equals(c17PacketCustomPayload.getChannelName())) {
            final PacketBuffer packetBuffer2 = new PacketBuffer(Unpooled.wrappedBuffer((ByteBuf)c17PacketCustomPayload.getBufferData()));
            try {
                final ItemStack itemStackFromBuffer2 = packetBuffer2.readItemStackFromBuffer();
                if (itemStackFromBuffer2 != null) {
                    if (!ItemEditableBook.validBookTagContents(itemStackFromBuffer2.getTagCompound())) {
                        throw new IOException(NetHandlerPlayServer.I[0x21 ^ 0x19]);
                    }
                    final ItemStack currentItem2 = this.playerEntity.inventory.getCurrentItem();
                    if (currentItem2 == null) {
                        return;
                    }
                    if (itemStackFromBuffer2.getItem() == Items.written_book && currentItem2.getItem() == Items.writable_book) {
                        currentItem2.setTagInfo(NetHandlerPlayServer.I[0xE ^ 0x37], new NBTTagString(this.playerEntity.getName()));
                        currentItem2.setTagInfo(NetHandlerPlayServer.I[0xB8 ^ 0x82], new NBTTagString(itemStackFromBuffer2.getTagCompound().getString(NetHandlerPlayServer.I[0x1C ^ 0x27])));
                        currentItem2.setTagInfo(NetHandlerPlayServer.I[0x82 ^ 0xBE], itemStackFromBuffer2.getTagCompound().getTagList(NetHandlerPlayServer.I[0x4 ^ 0x39], 0xCD ^ 0xC5));
                        currentItem2.setItem(Items.written_book);
                    }
                    return;
                }
            }
            catch (Exception ex2) {
                NetHandlerPlayServer.logger.error(NetHandlerPlayServer.I[0x82 ^ 0xBC], (Throwable)ex2);
                return;
            }
            finally {
                packetBuffer2.release();
            }
            packetBuffer2.release();
            return;
        }
        if (NetHandlerPlayServer.I[0x5B ^ 0x64].equals(c17PacketCustomPayload.getChannelName())) {
            try {
                final int int1 = c17PacketCustomPayload.getBufferData().readInt();
                final Container openContainer = this.playerEntity.openContainer;
                if (!(openContainer instanceof ContainerMerchant)) {
                    return;
                }
                ((ContainerMerchant)openContainer).setCurrentRecipeIndex(int1);
                "".length();
                if (0 == 2) {
                    throw null;
                }
                return;
            }
            catch (Exception ex3) {
                NetHandlerPlayServer.logger.error(NetHandlerPlayServer.I[0x82 ^ 0xC2], (Throwable)ex3);
                "".length();
                if (3 < 1) {
                    throw null;
                }
                return;
            }
        }
        if (NetHandlerPlayServer.I[0x76 ^ 0x37].equals(c17PacketCustomPayload.getChannelName())) {
            if (!this.serverController.isCommandBlockEnabled()) {
                this.playerEntity.addChatMessage(new ChatComponentTranslation(NetHandlerPlayServer.I[0xD ^ 0x4F], new Object["".length()]));
                "".length();
                if (3 < 1) {
                    throw null;
                }
            }
            else if (this.playerEntity.canCommandSenderUseCommand("  ".length(), NetHandlerPlayServer.I[0x29 ^ 0x6A]) && this.playerEntity.capabilities.isCreativeMode) {
                final PacketBuffer bufferData = c17PacketCustomPayload.getBufferData();
                try {
                    final byte byte1 = bufferData.readByte();
                    CommandBlockLogic commandBlockLogic = null;
                    if (byte1 == 0) {
                        final TileEntity tileEntity = this.playerEntity.worldObj.getTileEntity(new BlockPos(bufferData.readInt(), bufferData.readInt(), bufferData.readInt()));
                        if (tileEntity instanceof TileEntityCommandBlock) {
                            commandBlockLogic = ((TileEntityCommandBlock)tileEntity).getCommandBlockLogic();
                            "".length();
                            if (2 >= 3) {
                                throw null;
                            }
                        }
                    }
                    else if (byte1 == " ".length()) {
                        final Entity entityByID = this.playerEntity.worldObj.getEntityByID(bufferData.readInt());
                        if (entityByID instanceof EntityMinecartCommandBlock) {
                            commandBlockLogic = ((EntityMinecartCommandBlock)entityByID).getCommandBlockLogic();
                        }
                    }
                    final String stringFromBuffer = bufferData.readStringFromBuffer(bufferData.readableBytes());
                    final boolean boolean1 = bufferData.readBoolean();
                    if (commandBlockLogic != null) {
                        commandBlockLogic.setCommand(stringFromBuffer);
                        commandBlockLogic.setTrackOutput(boolean1);
                        if (!boolean1) {
                            commandBlockLogic.setLastOutput(null);
                        }
                        commandBlockLogic.updateCommand();
                        final EntityPlayerMP playerEntity = this.playerEntity;
                        final String s = NetHandlerPlayServer.I[0x24 ^ 0x60];
                        final Object[] array = new Object[" ".length()];
                        array["".length()] = stringFromBuffer;
                        playerEntity.addChatMessage(new ChatComponentTranslation(s, array));
                        "".length();
                        if (2 < -1) {
                            throw null;
                        }
                    }
                }
                catch (Exception ex4) {
                    NetHandlerPlayServer.logger.error(NetHandlerPlayServer.I[0x70 ^ 0x35], (Throwable)ex4);
                    bufferData.release();
                    "".length();
                    if (3 < 3) {
                        throw null;
                    }
                    return;
                }
                finally {
                    bufferData.release();
                }
                bufferData.release();
                "".length();
                if (2 <= 0) {
                    throw null;
                }
            }
            else {
                this.playerEntity.addChatMessage(new ChatComponentTranslation(NetHandlerPlayServer.I[0x49 ^ 0xF], new Object["".length()]));
                "".length();
                if (0 >= 1) {
                    throw null;
                }
            }
        }
        else {
            if (NetHandlerPlayServer.I[0xC2 ^ 0x85].equals(c17PacketCustomPayload.getChannelName())) {
                if (!(this.playerEntity.openContainer instanceof ContainerBeacon)) {
                    return;
                }
                try {
                    final PacketBuffer bufferData2 = c17PacketCustomPayload.getBufferData();
                    final int int2 = bufferData2.readInt();
                    final int int3 = bufferData2.readInt();
                    final ContainerBeacon containerBeacon = (ContainerBeacon)this.playerEntity.openContainer;
                    final Slot slot = containerBeacon.getSlot("".length());
                    if (!slot.getHasStack()) {
                        return;
                    }
                    slot.decrStackSize(" ".length());
                    final IInventory func_180611_e = containerBeacon.func_180611_e();
                    func_180611_e.setField(" ".length(), int2);
                    func_180611_e.setField("  ".length(), int3);
                    func_180611_e.markDirty();
                    "".length();
                    if (-1 >= 3) {
                        throw null;
                    }
                    return;
                }
                catch (Exception ex5) {
                    NetHandlerPlayServer.logger.error(NetHandlerPlayServer.I[0x7B ^ 0x33], (Throwable)ex5);
                    "".length();
                    if (3 == 1) {
                        throw null;
                    }
                    return;
                }
            }
            if (NetHandlerPlayServer.I[0x44 ^ 0xD].equals(c17PacketCustomPayload.getChannelName()) && this.playerEntity.openContainer instanceof ContainerRepair) {
                final ContainerRepair containerRepair = (ContainerRepair)this.playerEntity.openContainer;
                if (c17PacketCustomPayload.getBufferData() != null && c17PacketCustomPayload.getBufferData().readableBytes() >= " ".length()) {
                    final String filterAllowedCharacters = ChatAllowedCharacters.filterAllowedCharacters(c17PacketCustomPayload.getBufferData().readStringFromBuffer(27784 + 12752 - 31943 + 24174));
                    if (filterAllowedCharacters.length() <= (0x89 ^ 0x97)) {
                        containerRepair.updateItemName(filterAllowedCharacters);
                        "".length();
                        if (-1 == 4) {
                            throw null;
                        }
                    }
                }
                else {
                    containerRepair.updateItemName(NetHandlerPlayServer.I[0xE1 ^ 0xAB]);
                }
            }
        }
    }
    
    public NetHandlerPlayServer(final MinecraftServer serverController, final NetworkManager netManager, final EntityPlayerMP playerEntity) {
        this.field_147372_n = new IntHashMap<Short>();
        this.hasMoved = (" ".length() != 0);
        this.serverController = serverController;
        (this.netManager = netManager).setNetHandler(this);
        this.playerEntity = playerEntity;
        playerEntity.playerNetServerHandler = this;
    }
    
    public void setPlayerLocation(final double lastPosX, final double lastPosY, final double lastPosZ, final float n, final float n2, final Set<S08PacketPlayerPosLook.EnumFlags> set) {
        this.hasMoved = ("".length() != 0);
        this.lastPosX = lastPosX;
        this.lastPosY = lastPosY;
        this.lastPosZ = lastPosZ;
        if (set.contains(S08PacketPlayerPosLook.EnumFlags.X)) {
            this.lastPosX += this.playerEntity.posX;
        }
        if (set.contains(S08PacketPlayerPosLook.EnumFlags.Y)) {
            this.lastPosY += this.playerEntity.posY;
        }
        if (set.contains(S08PacketPlayerPosLook.EnumFlags.Z)) {
            this.lastPosZ += this.playerEntity.posZ;
        }
        float n3 = n;
        float n4 = n2;
        if (set.contains(S08PacketPlayerPosLook.EnumFlags.Y_ROT)) {
            n3 = n + this.playerEntity.rotationYaw;
        }
        if (set.contains(S08PacketPlayerPosLook.EnumFlags.X_ROT)) {
            n4 = n2 + this.playerEntity.rotationPitch;
        }
        this.playerEntity.setPositionAndRotation(this.lastPosX, this.lastPosY, this.lastPosZ, n3, n4);
        this.playerEntity.playerNetServerHandler.sendPacket(new S08PacketPlayerPosLook(lastPosX, lastPosY, lastPosZ, n, n2, set));
    }
    
    static int[] $SWITCH_TABLE$net$minecraft$network$play$client$C0BPacketEntityAction$Action() {
        final int[] $switch_TABLE$net$minecraft$network$play$client$C0BPacketEntityAction$Action = NetHandlerPlayServer.$SWITCH_TABLE$net$minecraft$network$play$client$C0BPacketEntityAction$Action;
        if ($switch_TABLE$net$minecraft$network$play$client$C0BPacketEntityAction$Action != null) {
            return $switch_TABLE$net$minecraft$network$play$client$C0BPacketEntityAction$Action;
        }
        final int[] $switch_TABLE$net$minecraft$network$play$client$C0BPacketEntityAction$Action2 = new int[C0BPacketEntityAction.Action.values().length];
        try {
            $switch_TABLE$net$minecraft$network$play$client$C0BPacketEntityAction$Action2[C0BPacketEntityAction.Action.OPEN_INVENTORY.ordinal()] = (0x7A ^ 0x7D);
            "".length();
            if (-1 < -1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            $switch_TABLE$net$minecraft$network$play$client$C0BPacketEntityAction$Action2[C0BPacketEntityAction.Action.RIDING_JUMP.ordinal()] = (0x9B ^ 0x9D);
            "".length();
            if (3 < 0) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            $switch_TABLE$net$minecraft$network$play$client$C0BPacketEntityAction$Action2[C0BPacketEntityAction.Action.START_SNEAKING.ordinal()] = " ".length();
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            $switch_TABLE$net$minecraft$network$play$client$C0BPacketEntityAction$Action2[C0BPacketEntityAction.Action.START_SPRINTING.ordinal()] = (0x22 ^ 0x26);
            "".length();
            if (1 >= 4) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
        try {
            $switch_TABLE$net$minecraft$network$play$client$C0BPacketEntityAction$Action2[C0BPacketEntityAction.Action.STOP_SLEEPING.ordinal()] = "   ".length();
            "".length();
            if (0 >= 4) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError5) {}
        try {
            $switch_TABLE$net$minecraft$network$play$client$C0BPacketEntityAction$Action2[C0BPacketEntityAction.Action.STOP_SNEAKING.ordinal()] = "  ".length();
            "".length();
            if (2 == 3) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError6) {}
        try {
            $switch_TABLE$net$minecraft$network$play$client$C0BPacketEntityAction$Action2[C0BPacketEntityAction.Action.STOP_SPRINTING.ordinal()] = (0x55 ^ 0x50);
            "".length();
            if (3 <= -1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError7) {}
        return NetHandlerPlayServer.$SWITCH_TABLE$net$minecraft$network$play$client$C0BPacketEntityAction$Action = $switch_TABLE$net$minecraft$network$play$client$C0BPacketEntityAction$Action2;
    }
    
    @Override
    public void processCreativeInventoryAction(final C10PacketCreativeInventoryAction c10PacketCreativeInventoryAction) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)c10PacketCreativeInventoryAction, this, this.playerEntity.getServerForPlayer());
        if (this.playerEntity.theItemInWorldManager.isCreative()) {
            int n;
            if (c10PacketCreativeInventoryAction.getSlotId() < 0) {
                n = " ".length();
                "".length();
                if (2 == 4) {
                    throw null;
                }
            }
            else {
                n = "".length();
            }
            final int n2 = n;
            final ItemStack stack = c10PacketCreativeInventoryAction.getStack();
            if (stack != null && stack.hasTagCompound() && stack.getTagCompound().hasKey(NetHandlerPlayServer.I[0x76 ^ 0x52], 0x28 ^ 0x22)) {
                final NBTTagCompound compoundTag = stack.getTagCompound().getCompoundTag(NetHandlerPlayServer.I[0xA0 ^ 0x85]);
                if (compoundTag.hasKey(NetHandlerPlayServer.I[0x84 ^ 0xA2]) && compoundTag.hasKey(NetHandlerPlayServer.I[0x42 ^ 0x65]) && compoundTag.hasKey(NetHandlerPlayServer.I[0x43 ^ 0x6B])) {
                    final TileEntity tileEntity = this.playerEntity.worldObj.getTileEntity(new BlockPos(compoundTag.getInteger(NetHandlerPlayServer.I[0x8F ^ 0xA6]), compoundTag.getInteger(NetHandlerPlayServer.I[0x75 ^ 0x5F]), compoundTag.getInteger(NetHandlerPlayServer.I[0x4D ^ 0x66])));
                    if (tileEntity != null) {
                        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
                        tileEntity.writeToNBT(nbtTagCompound);
                        nbtTagCompound.removeTag(NetHandlerPlayServer.I[0xA1 ^ 0x8D]);
                        nbtTagCompound.removeTag(NetHandlerPlayServer.I[0x93 ^ 0xBE]);
                        nbtTagCompound.removeTag(NetHandlerPlayServer.I[0xB3 ^ 0x9D]);
                        stack.setTagInfo(NetHandlerPlayServer.I[0x1E ^ 0x31], nbtTagCompound);
                    }
                }
            }
            int n3;
            if (c10PacketCreativeInventoryAction.getSlotId() >= " ".length() && c10PacketCreativeInventoryAction.getSlotId() < (0x83 ^ 0xA7) + InventoryPlayer.getHotbarSize()) {
                n3 = " ".length();
                "".length();
                if (3 <= -1) {
                    throw null;
                }
            }
            else {
                n3 = "".length();
            }
            final int n4 = n3;
            int n5;
            if (stack != null && stack.getItem() == null) {
                n5 = "".length();
                "".length();
                if (3 < 1) {
                    throw null;
                }
            }
            else {
                n5 = " ".length();
            }
            final int n6 = n5;
            int n7;
            if (stack != null && (stack.getMetadata() < 0 || stack.stackSize > (0xC ^ 0x4C) || stack.stackSize <= 0)) {
                n7 = "".length();
                "".length();
                if (3 == -1) {
                    throw null;
                }
            }
            else {
                n7 = " ".length();
            }
            final int n8 = n7;
            if (n4 != 0 && n6 != 0 && n8 != 0) {
                if (stack == null) {
                    this.playerEntity.inventoryContainer.putStackInSlot(c10PacketCreativeInventoryAction.getSlotId(), null);
                    "".length();
                    if (4 <= 3) {
                        throw null;
                    }
                }
                else {
                    this.playerEntity.inventoryContainer.putStackInSlot(c10PacketCreativeInventoryAction.getSlotId(), stack);
                }
                this.playerEntity.inventoryContainer.setCanCraft(this.playerEntity, " ".length() != 0);
                "".length();
                if (2 <= 1) {
                    throw null;
                }
            }
            else if (n2 != 0 && n6 != 0 && n8 != 0 && this.itemDropThreshold < 141 + 123 - 258 + 194) {
                this.itemDropThreshold += (0x28 ^ 0x3C);
                final EntityItem dropPlayerItemWithRandomChoice = this.playerEntity.dropPlayerItemWithRandomChoice(stack, " ".length() != 0);
                if (dropPlayerItemWithRandomChoice != null) {
                    dropPlayerItemWithRandomChoice.setAgeToCreativeDespawnTime();
                }
            }
        }
    }
    
    @Override
    public void onDisconnect(final IChatComponent chatComponent) {
        NetHandlerPlayServer.logger.info(String.valueOf(this.playerEntity.getName()) + NetHandlerPlayServer.I[0x17 ^ 0x7] + chatComponent);
        this.serverController.refreshStatusNextTick();
        final String s = NetHandlerPlayServer.I[0xBD ^ 0xAC];
        final Object[] array = new Object[" ".length()];
        array["".length()] = this.playerEntity.getDisplayName();
        final ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation(s, array);
        chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.YELLOW);
        this.serverController.getConfigurationManager().sendChatMsg(chatComponentTranslation);
        this.playerEntity.mountEntityAndWakeUp();
        this.serverController.getConfigurationManager().playerLoggedOut(this.playerEntity);
        if (this.serverController.isSinglePlayer() && this.playerEntity.getName().equals(this.serverController.getServerOwner())) {
            NetHandlerPlayServer.logger.info(NetHandlerPlayServer.I[0x37 ^ 0x25]);
            this.serverController.initiateShutdown();
        }
    }
}
