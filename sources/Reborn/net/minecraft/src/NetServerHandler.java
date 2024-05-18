package net.minecraft.src;

import net.minecraft.server.*;
import java.util.concurrent.*;
import java.util.*;
import java.io.*;

public class NetServerHandler extends NetHandler
{
    public final INetworkManager netManager;
    private final MinecraftServer mcServer;
    public boolean connectionClosed;
    public EntityPlayerMP playerEntity;
    private int currentTicks;
    private int ticksForFloatKick;
    private boolean field_72584_h;
    private int keepAliveRandomID;
    private long keepAliveTimeSent;
    private static Random randomGenerator;
    private long ticksOfLastKeepAlive;
    private int chatSpamThresholdCount;
    private int creativeItemCreationSpamThresholdTally;
    private double lastPosX;
    private double lastPosY;
    private double lastPosZ;
    private boolean hasMoved;
    private IntHashMap field_72586_s;
    
    static {
        NetServerHandler.randomGenerator = new Random();
    }
    
    public NetServerHandler(final MinecraftServer par1, final INetworkManager par2, final EntityPlayerMP par3) {
        this.connectionClosed = false;
        this.chatSpamThresholdCount = 0;
        this.creativeItemCreationSpamThresholdTally = 0;
        this.hasMoved = true;
        this.field_72586_s = new IntHashMap();
        this.mcServer = par1;
        (this.netManager = par2).setNetHandler(this);
        this.playerEntity = par3;
        par3.playerNetServerHandler = this;
    }
    
    public void networkTick() {
        this.field_72584_h = false;
        ++this.currentTicks;
        this.mcServer.theProfiler.startSection("packetflow");
        this.netManager.processReadPackets();
        this.mcServer.theProfiler.endStartSection("keepAlive");
        if (this.currentTicks - this.ticksOfLastKeepAlive > 20L) {
            this.ticksOfLastKeepAlive = this.currentTicks;
            this.keepAliveTimeSent = System.nanoTime() / 1000000L;
            this.keepAliveRandomID = NetServerHandler.randomGenerator.nextInt();
            this.sendPacketToPlayer(new Packet0KeepAlive(this.keepAliveRandomID));
        }
        if (this.chatSpamThresholdCount > 0) {
            --this.chatSpamThresholdCount;
        }
        if (this.creativeItemCreationSpamThresholdTally > 0) {
            --this.creativeItemCreationSpamThresholdTally;
        }
        this.mcServer.theProfiler.endStartSection("playerTick");
        this.mcServer.theProfiler.endSection();
    }
    
    public void kickPlayerFromServer(final String par1Str) {
        if (!this.connectionClosed) {
            this.playerEntity.mountEntityAndWakeUp();
            this.sendPacketToPlayer(new Packet255KickDisconnect(par1Str));
            this.netManager.serverShutdown();
            this.mcServer.getConfigurationManager().sendPacketToAllPlayers(new Packet3Chat(EnumChatFormatting.YELLOW + this.playerEntity.username + " left the game."));
            this.mcServer.getConfigurationManager().playerLoggedOut(this.playerEntity);
            this.connectionClosed = true;
        }
    }
    
    @Override
    public void handleFlying(final Packet10Flying par1Packet10Flying) {
        final WorldServer var2 = this.mcServer.worldServerForDimension(this.playerEntity.dimension);
        this.field_72584_h = true;
        if (!this.playerEntity.playerConqueredTheEnd) {
            if (!this.hasMoved) {
                final double var3 = par1Packet10Flying.yPosition - this.lastPosY;
                if (par1Packet10Flying.xPosition == this.lastPosX && var3 * var3 < 0.01 && par1Packet10Flying.zPosition == this.lastPosZ) {
                    this.hasMoved = true;
                }
            }
            if (this.hasMoved) {
                if (this.playerEntity.ridingEntity != null) {
                    float var4 = this.playerEntity.rotationYaw;
                    float var5 = this.playerEntity.rotationPitch;
                    this.playerEntity.ridingEntity.updateRiderPosition();
                    final double var6 = this.playerEntity.posX;
                    final double var7 = this.playerEntity.posY;
                    final double var8 = this.playerEntity.posZ;
                    double var9 = 0.0;
                    double var10 = 0.0;
                    if (par1Packet10Flying.rotating) {
                        var4 = par1Packet10Flying.yaw;
                        var5 = par1Packet10Flying.pitch;
                    }
                    if (par1Packet10Flying.moving && par1Packet10Flying.yPosition == -999.0 && par1Packet10Flying.stance == -999.0) {
                        if (Math.abs(par1Packet10Flying.xPosition) > 1.0 || Math.abs(par1Packet10Flying.zPosition) > 1.0) {
                            System.err.println(String.valueOf(this.playerEntity.username) + " was caught trying to crash the server with an invalid position.");
                            this.kickPlayerFromServer("Nope!");
                            return;
                        }
                        var9 = par1Packet10Flying.xPosition;
                        var10 = par1Packet10Flying.zPosition;
                    }
                    this.playerEntity.onGround = par1Packet10Flying.onGround;
                    this.playerEntity.onUpdateEntity();
                    this.playerEntity.moveEntity(var9, 0.0, var10);
                    this.playerEntity.setPositionAndRotation(var6, var7, var8, var4, var5);
                    this.playerEntity.motionX = var9;
                    this.playerEntity.motionZ = var10;
                    if (this.playerEntity.ridingEntity != null) {
                        var2.uncheckedUpdateEntity(this.playerEntity.ridingEntity, true);
                    }
                    if (this.playerEntity.ridingEntity != null) {
                        this.playerEntity.ridingEntity.updateRiderPosition();
                    }
                    this.mcServer.getConfigurationManager().serverUpdateMountedMovingPlayer(this.playerEntity);
                    this.lastPosX = this.playerEntity.posX;
                    this.lastPosY = this.playerEntity.posY;
                    this.lastPosZ = this.playerEntity.posZ;
                    var2.updateEntity(this.playerEntity);
                    return;
                }
                if (this.playerEntity.isPlayerSleeping()) {
                    this.playerEntity.onUpdateEntity();
                    this.playerEntity.setPositionAndRotation(this.lastPosX, this.lastPosY, this.lastPosZ, this.playerEntity.rotationYaw, this.playerEntity.rotationPitch);
                    var2.updateEntity(this.playerEntity);
                    return;
                }
                final double var3 = this.playerEntity.posY;
                this.lastPosX = this.playerEntity.posX;
                this.lastPosY = this.playerEntity.posY;
                this.lastPosZ = this.playerEntity.posZ;
                double var6 = this.playerEntity.posX;
                double var7 = this.playerEntity.posY;
                double var8 = this.playerEntity.posZ;
                float var4 = this.playerEntity.rotationYaw;
                float var5 = this.playerEntity.rotationPitch;
                if (par1Packet10Flying.moving && par1Packet10Flying.yPosition == -999.0 && par1Packet10Flying.stance == -999.0) {
                    par1Packet10Flying.moving = false;
                }
                if (par1Packet10Flying.moving) {
                    var6 = par1Packet10Flying.xPosition;
                    var7 = par1Packet10Flying.yPosition;
                    var8 = par1Packet10Flying.zPosition;
                    final double var10 = par1Packet10Flying.stance - par1Packet10Flying.yPosition;
                    if (!this.playerEntity.isPlayerSleeping() && (var10 > 1.65 || var10 < 0.1)) {
                        this.kickPlayerFromServer("Illegal stance");
                        this.mcServer.getLogAgent().logWarning(String.valueOf(this.playerEntity.username) + " had an illegal stance: " + var10);
                        return;
                    }
                    if (Math.abs(par1Packet10Flying.xPosition) > 3.2E7 || Math.abs(par1Packet10Flying.zPosition) > 3.2E7) {
                        this.kickPlayerFromServer("Illegal position");
                        return;
                    }
                }
                if (par1Packet10Flying.rotating) {
                    var4 = par1Packet10Flying.yaw;
                    var5 = par1Packet10Flying.pitch;
                }
                this.playerEntity.onUpdateEntity();
                this.playerEntity.ySize = 0.0f;
                this.playerEntity.setPositionAndRotation(this.lastPosX, this.lastPosY, this.lastPosZ, var4, var5);
                if (!this.hasMoved) {
                    return;
                }
                double var10 = var6 - this.playerEntity.posX;
                double var9 = var7 - this.playerEntity.posY;
                double var11 = var8 - this.playerEntity.posZ;
                final double var12 = Math.min(Math.abs(var10), Math.abs(this.playerEntity.motionX));
                final double var13 = Math.min(Math.abs(var9), Math.abs(this.playerEntity.motionY));
                final double var14 = Math.min(Math.abs(var11), Math.abs(this.playerEntity.motionZ));
                double var15 = var12 * var12 + var13 * var13 + var14 * var14;
                if (var15 > 100.0 && (!this.mcServer.isSinglePlayer() || !this.mcServer.getServerOwner().equals(this.playerEntity.username))) {
                    this.mcServer.getLogAgent().logWarning(String.valueOf(this.playerEntity.username) + " moved too quickly! " + var10 + "," + var9 + "," + var11 + " (" + var12 + ", " + var13 + ", " + var14 + ")");
                    this.setPlayerLocation(this.lastPosX, this.lastPosY, this.lastPosZ, this.playerEntity.rotationYaw, this.playerEntity.rotationPitch);
                    return;
                }
                final float var16 = 0.0625f;
                final boolean var17 = var2.getCollidingBoundingBoxes(this.playerEntity, this.playerEntity.boundingBox.copy().contract(var16, var16, var16)).isEmpty();
                if (this.playerEntity.onGround && !par1Packet10Flying.onGround && var9 > 0.0) {
                    this.playerEntity.addExhaustion(0.2f);
                }
                this.playerEntity.moveEntity(var10, var9, var11);
                this.playerEntity.onGround = par1Packet10Flying.onGround;
                this.playerEntity.addMovementStat(var10, var9, var11);
                final double var18 = var9;
                var10 = var6 - this.playerEntity.posX;
                var9 = var7 - this.playerEntity.posY;
                if (var9 > -0.5 || var9 < 0.5) {
                    var9 = 0.0;
                }
                var11 = var8 - this.playerEntity.posZ;
                var15 = var10 * var10 + var9 * var9 + var11 * var11;
                boolean var19 = false;
                if (var15 > 0.0625 && !this.playerEntity.isPlayerSleeping() && !this.playerEntity.theItemInWorldManager.isCreative()) {
                    var19 = true;
                    this.mcServer.getLogAgent().logWarning(String.valueOf(this.playerEntity.username) + " moved wrongly!");
                }
                this.playerEntity.setPositionAndRotation(var6, var7, var8, var4, var5);
                final boolean var20 = var2.getCollidingBoundingBoxes(this.playerEntity, this.playerEntity.boundingBox.copy().contract(var16, var16, var16)).isEmpty();
                if (var17 && (var19 || !var20) && !this.playerEntity.isPlayerSleeping()) {
                    this.setPlayerLocation(this.lastPosX, this.lastPosY, this.lastPosZ, var4, var5);
                    return;
                }
                final AxisAlignedBB var21 = this.playerEntity.boundingBox.copy().expand(var16, var16, var16).addCoord(0.0, -0.55, 0.0);
                if (!this.mcServer.isFlightAllowed() && !this.playerEntity.theItemInWorldManager.isCreative() && !var2.checkBlockCollision(var21)) {
                    if (var18 >= -0.03125) {
                        ++this.ticksForFloatKick;
                        if (this.ticksForFloatKick > 80) {
                            this.mcServer.getLogAgent().logWarning(String.valueOf(this.playerEntity.username) + " was kicked for floating too long!");
                            this.kickPlayerFromServer("Flying is not enabled on this server");
                            return;
                        }
                    }
                }
                else {
                    this.ticksForFloatKick = 0;
                }
                this.playerEntity.onGround = par1Packet10Flying.onGround;
                this.mcServer.getConfigurationManager().serverUpdateMountedMovingPlayer(this.playerEntity);
                this.playerEntity.updateFlyingState(this.playerEntity.posY - var3, par1Packet10Flying.onGround);
            }
        }
    }
    
    public void setPlayerLocation(final double par1, final double par3, final double par5, final float par7, final float par8) {
        this.hasMoved = false;
        this.lastPosX = par1;
        this.lastPosY = par3;
        this.lastPosZ = par5;
        this.playerEntity.setPositionAndRotation(par1, par3, par5, par7, par8);
        this.playerEntity.playerNetServerHandler.sendPacketToPlayer(new Packet13PlayerLookMove(par1, par3 + 1.6200000047683716, par3, par5, par7, par8, false));
    }
    
    @Override
    public void handleBlockDig(final Packet14BlockDig par1Packet14BlockDig) {
        final WorldServer var2 = this.mcServer.worldServerForDimension(this.playerEntity.dimension);
        if (par1Packet14BlockDig.status == 4) {
            this.playerEntity.dropOneItem(false);
        }
        else if (par1Packet14BlockDig.status == 3) {
            this.playerEntity.dropOneItem(true);
        }
        else if (par1Packet14BlockDig.status == 5) {
            this.playerEntity.stopUsingItem();
        }
        else {
            boolean var3 = false;
            if (par1Packet14BlockDig.status == 0) {
                var3 = true;
            }
            if (par1Packet14BlockDig.status == 1) {
                var3 = true;
            }
            if (par1Packet14BlockDig.status == 2) {
                var3 = true;
            }
            final int var4 = par1Packet14BlockDig.xPosition;
            final int var5 = par1Packet14BlockDig.yPosition;
            final int var6 = par1Packet14BlockDig.zPosition;
            if (var3) {
                final double var7 = this.playerEntity.posX - (var4 + 0.5);
                final double var8 = this.playerEntity.posY - (var5 + 0.5) + 1.5;
                final double var9 = this.playerEntity.posZ - (var6 + 0.5);
                final double var10 = var7 * var7 + var8 * var8 + var9 * var9;
                if (var10 > 36.0) {
                    return;
                }
                if (var5 >= this.mcServer.getBuildLimit()) {
                    return;
                }
            }
            if (par1Packet14BlockDig.status == 0) {
                if (!this.mcServer.func_96290_a(var2, var4, var5, var6, this.playerEntity)) {
                    this.playerEntity.theItemInWorldManager.onBlockClicked(var4, var5, var6, par1Packet14BlockDig.face);
                }
                else {
                    this.playerEntity.playerNetServerHandler.sendPacketToPlayer(new Packet53BlockChange(var4, var5, var6, var2));
                }
            }
            else if (par1Packet14BlockDig.status == 2) {
                this.playerEntity.theItemInWorldManager.uncheckedTryHarvestBlock(var4, var5, var6);
                if (var2.getBlockId(var4, var5, var6) != 0) {
                    this.playerEntity.playerNetServerHandler.sendPacketToPlayer(new Packet53BlockChange(var4, var5, var6, var2));
                }
            }
            else if (par1Packet14BlockDig.status == 1) {
                this.playerEntity.theItemInWorldManager.cancelDestroyingBlock(var4, var5, var6);
                if (var2.getBlockId(var4, var5, var6) != 0) {
                    this.playerEntity.playerNetServerHandler.sendPacketToPlayer(new Packet53BlockChange(var4, var5, var6, var2));
                }
            }
        }
    }
    
    @Override
    public void handlePlace(final Packet15Place par1Packet15Place) {
        final WorldServer var2 = this.mcServer.worldServerForDimension(this.playerEntity.dimension);
        ItemStack var3 = this.playerEntity.inventory.getCurrentItem();
        boolean var4 = false;
        int var5 = par1Packet15Place.getXPosition();
        int var6 = par1Packet15Place.getYPosition();
        int var7 = par1Packet15Place.getZPosition();
        final int var8 = par1Packet15Place.getDirection();
        if (par1Packet15Place.getDirection() == 255) {
            if (var3 == null) {
                return;
            }
            this.playerEntity.theItemInWorldManager.tryUseItem(this.playerEntity, var2, var3);
        }
        else if (par1Packet15Place.getYPosition() >= this.mcServer.getBuildLimit() - 1 && (par1Packet15Place.getDirection() == 1 || par1Packet15Place.getYPosition() >= this.mcServer.getBuildLimit())) {
            this.playerEntity.playerNetServerHandler.sendPacketToPlayer(new Packet3Chat(EnumChatFormatting.GRAY + "Height limit for building is " + this.mcServer.getBuildLimit()));
            var4 = true;
        }
        else {
            if (this.hasMoved && this.playerEntity.getDistanceSq(var5 + 0.5, var6 + 0.5, var7 + 0.5) < 64.0 && !this.mcServer.func_96290_a(var2, var5, var6, var7, this.playerEntity)) {
                this.playerEntity.theItemInWorldManager.activateBlockOrUseItem(this.playerEntity, var2, var3, var5, var6, var7, var8, par1Packet15Place.getXOffset(), par1Packet15Place.getYOffset(), par1Packet15Place.getZOffset());
            }
            var4 = true;
        }
        if (var4) {
            this.playerEntity.playerNetServerHandler.sendPacketToPlayer(new Packet53BlockChange(var5, var6, var7, var2));
            if (var8 == 0) {
                --var6;
            }
            if (var8 == 1) {
                ++var6;
            }
            if (var8 == 2) {
                --var7;
            }
            if (var8 == 3) {
                ++var7;
            }
            if (var8 == 4) {
                --var5;
            }
            if (var8 == 5) {
                ++var5;
            }
            this.playerEntity.playerNetServerHandler.sendPacketToPlayer(new Packet53BlockChange(var5, var6, var7, var2));
        }
        var3 = this.playerEntity.inventory.getCurrentItem();
        if (var3 != null && var3.stackSize == 0) {
            this.playerEntity.inventory.mainInventory[this.playerEntity.inventory.currentItem] = null;
            var3 = null;
        }
        if (var3 == null || var3.getMaxItemUseDuration() == 0) {
            this.playerEntity.playerInventoryBeingManipulated = true;
            this.playerEntity.inventory.mainInventory[this.playerEntity.inventory.currentItem] = ItemStack.copyItemStack(this.playerEntity.inventory.mainInventory[this.playerEntity.inventory.currentItem]);
            final Slot var9 = this.playerEntity.openContainer.getSlotFromInventory(this.playerEntity.inventory, this.playerEntity.inventory.currentItem);
            this.playerEntity.openContainer.detectAndSendChanges();
            this.playerEntity.playerInventoryBeingManipulated = false;
            if (!ItemStack.areItemStacksEqual(this.playerEntity.inventory.getCurrentItem(), par1Packet15Place.getItemStack())) {
                this.sendPacketToPlayer(new Packet103SetSlot(this.playerEntity.openContainer.windowId, var9.slotNumber, this.playerEntity.inventory.getCurrentItem()));
            }
        }
    }
    
    @Override
    public void handleErrorMessage(final String par1Str, final Object[] par2ArrayOfObj) {
        this.mcServer.getLogAgent().logInfo(String.valueOf(this.playerEntity.username) + " lost connection: " + par1Str);
        this.mcServer.getConfigurationManager().sendPacketToAllPlayers(new Packet3Chat(EnumChatFormatting.YELLOW + this.playerEntity.getTranslatedEntityName() + " left the game."));
        this.mcServer.getConfigurationManager().playerLoggedOut(this.playerEntity);
        this.connectionClosed = true;
        if (this.mcServer.isSinglePlayer() && this.playerEntity.username.equals(this.mcServer.getServerOwner())) {
            this.mcServer.getLogAgent().logInfo("Stopping singleplayer server as player logged out");
            this.mcServer.initiateShutdown();
        }
    }
    
    @Override
    public void unexpectedPacket(final Packet par1Packet) {
        this.mcServer.getLogAgent().logWarning(this.getClass() + " wasn't prepared to deal with a " + par1Packet.getClass());
        this.kickPlayerFromServer("Protocol error, unexpected packet");
    }
    
    public void sendPacketToPlayer(final Packet par1Packet) {
        if (par1Packet instanceof Packet3Chat) {
            final Packet3Chat var2 = (Packet3Chat)par1Packet;
            final int var3 = this.playerEntity.getChatVisibility();
            if (var3 == 2) {
                return;
            }
            if (var3 == 1 && !var2.getIsServer()) {
                return;
            }
        }
        try {
            this.netManager.addToSendQueue(par1Packet);
        }
        catch (Throwable var5) {
            final CrashReport var4 = CrashReport.makeCrashReport(var5, "Sending packet");
            final CrashReportCategory var6 = var4.makeCategory("Packet being sent");
            var6.addCrashSectionCallable("Packet ID", new CallablePacketID(this, par1Packet));
            var6.addCrashSectionCallable("Packet class", new CallablePacketClass(this, par1Packet));
            throw new ReportedException(var4);
        }
    }
    
    @Override
    public void handleBlockItemSwitch(final Packet16BlockItemSwitch par1Packet16BlockItemSwitch) {
        if (par1Packet16BlockItemSwitch.id >= 0 && par1Packet16BlockItemSwitch.id < InventoryPlayer.getHotbarSize()) {
            this.playerEntity.inventory.currentItem = par1Packet16BlockItemSwitch.id;
        }
        else {
            this.mcServer.getLogAgent().logWarning(String.valueOf(this.playerEntity.username) + " tried to set an invalid carried item");
        }
    }
    
    @Override
    public void handleChat(final Packet3Chat par1Packet3Chat) {
        if (this.playerEntity.getChatVisibility() == 2) {
            this.sendPacketToPlayer(new Packet3Chat("Cannot send chat message."));
        }
        else {
            String var2 = par1Packet3Chat.message;
            if (var2.length() > 100) {
                this.kickPlayerFromServer("Chat message too long");
            }
            else {
                var2 = var2.trim();
                for (int var3 = 0; var3 < var2.length(); ++var3) {
                    if (!ChatAllowedCharacters.isAllowedCharacter(var2.charAt(var3))) {
                        this.kickPlayerFromServer("Illegal characters in chat");
                        return;
                    }
                }
                if (var2.startsWith("/")) {
                    this.handleSlashCommand(var2);
                }
                else {
                    if (this.playerEntity.getChatVisibility() == 1) {
                        this.sendPacketToPlayer(new Packet3Chat("Cannot send chat message."));
                        return;
                    }
                    var2 = "<" + this.playerEntity.getTranslatedEntityName() + "> " + var2;
                    this.mcServer.getLogAgent().logInfo(var2);
                    this.mcServer.getConfigurationManager().sendPacketToAllPlayers(new Packet3Chat(var2, false));
                }
                this.chatSpamThresholdCount += 20;
                if (this.chatSpamThresholdCount > 200 && !this.mcServer.getConfigurationManager().areCommandsAllowed(this.playerEntity.username)) {
                    this.kickPlayerFromServer("disconnect.spam");
                }
            }
        }
    }
    
    private void handleSlashCommand(final String par1Str) {
        this.mcServer.getCommandManager().executeCommand(this.playerEntity, par1Str);
    }
    
    @Override
    public void handleAnimation(final Packet18Animation par1Packet18Animation) {
        if (par1Packet18Animation.animate == 1) {
            this.playerEntity.swingItem();
        }
    }
    
    @Override
    public void handleEntityAction(final Packet19EntityAction par1Packet19EntityAction) {
        if (par1Packet19EntityAction.state == 1) {
            this.playerEntity.setSneaking(true);
        }
        else if (par1Packet19EntityAction.state == 2) {
            this.playerEntity.setSneaking(false);
        }
        else if (par1Packet19EntityAction.state == 4) {
            this.playerEntity.setSprinting(true);
        }
        else if (par1Packet19EntityAction.state == 5) {
            this.playerEntity.setSprinting(false);
        }
        else if (par1Packet19EntityAction.state == 3) {
            this.playerEntity.wakeUpPlayer(false, true, true);
            this.hasMoved = false;
        }
    }
    
    @Override
    public void handleKickDisconnect(final Packet255KickDisconnect par1Packet255KickDisconnect) {
        this.netManager.networkShutdown("disconnect.quitting", new Object[0]);
    }
    
    public int packetSize() {
        return this.netManager.packetSize();
    }
    
    @Override
    public void handleUseEntity(final Packet7UseEntity par1Packet7UseEntity) {
        final WorldServer var2 = this.mcServer.worldServerForDimension(this.playerEntity.dimension);
        final Entity var3 = var2.getEntityByID(par1Packet7UseEntity.targetEntity);
        if (var3 != null) {
            final boolean var4 = this.playerEntity.canEntityBeSeen(var3);
            double var5 = 36.0;
            if (!var4) {
                var5 = 9.0;
            }
            if (this.playerEntity.getDistanceSqToEntity(var3) < var5) {
                if (par1Packet7UseEntity.isLeftClick == 0) {
                    this.playerEntity.interactWith(var3);
                }
                else if (par1Packet7UseEntity.isLeftClick == 1) {
                    this.playerEntity.attackTargetEntityWithCurrentItem(var3);
                }
            }
        }
    }
    
    @Override
    public void handleClientCommand(final Packet205ClientCommand par1Packet205ClientCommand) {
        if (par1Packet205ClientCommand.forceRespawn == 1) {
            if (this.playerEntity.playerConqueredTheEnd) {
                this.playerEntity = this.mcServer.getConfigurationManager().respawnPlayer(this.playerEntity, 0, true);
            }
            else if (this.playerEntity.getServerForPlayer().getWorldInfo().isHardcoreModeEnabled()) {
                if (this.mcServer.isSinglePlayer() && this.playerEntity.username.equals(this.mcServer.getServerOwner())) {
                    this.playerEntity.playerNetServerHandler.kickPlayerFromServer("You have died. Game over, man, it's game over!");
                    this.mcServer.deleteWorldAndStopServer();
                }
                else {
                    final BanEntry var2 = new BanEntry(this.playerEntity.username);
                    var2.setBanReason("Death in Hardcore");
                    this.mcServer.getConfigurationManager().getBannedPlayers().put(var2);
                    this.playerEntity.playerNetServerHandler.kickPlayerFromServer("You have died. Game over, man, it's game over!");
                }
            }
            else {
                if (this.playerEntity.getHealth() > 0) {
                    return;
                }
                this.playerEntity = this.mcServer.getConfigurationManager().respawnPlayer(this.playerEntity, 0, false);
            }
        }
    }
    
    @Override
    public boolean canProcessPacketsAsync() {
        return true;
    }
    
    @Override
    public void handleRespawn(final Packet9Respawn par1Packet9Respawn) {
    }
    
    @Override
    public void handleCloseWindow(final Packet101CloseWindow par1Packet101CloseWindow) {
        this.playerEntity.closeInventory();
    }
    
    @Override
    public void handleWindowClick(final Packet102WindowClick par1Packet102WindowClick) {
        if (this.playerEntity.openContainer.windowId == par1Packet102WindowClick.window_Id && this.playerEntity.openContainer.isPlayerNotUsingContainer(this.playerEntity)) {
            final ItemStack var2 = this.playerEntity.openContainer.slotClick(par1Packet102WindowClick.inventorySlot, par1Packet102WindowClick.mouseClick, par1Packet102WindowClick.holdingShift, this.playerEntity);
            if (ItemStack.areItemStacksEqual(par1Packet102WindowClick.itemStack, var2)) {
                this.playerEntity.playerNetServerHandler.sendPacketToPlayer(new Packet106Transaction(par1Packet102WindowClick.window_Id, par1Packet102WindowClick.action, true));
                this.playerEntity.playerInventoryBeingManipulated = true;
                this.playerEntity.openContainer.detectAndSendChanges();
                this.playerEntity.updateHeldItem();
                this.playerEntity.playerInventoryBeingManipulated = false;
            }
            else {
                this.field_72586_s.addKey(this.playerEntity.openContainer.windowId, par1Packet102WindowClick.action);
                this.playerEntity.playerNetServerHandler.sendPacketToPlayer(new Packet106Transaction(par1Packet102WindowClick.window_Id, par1Packet102WindowClick.action, false));
                this.playerEntity.openContainer.setPlayerIsPresent(this.playerEntity, false);
                final ArrayList var3 = new ArrayList();
                for (int var4 = 0; var4 < this.playerEntity.openContainer.inventorySlots.size(); ++var4) {
                    var3.add(this.playerEntity.openContainer.inventorySlots.get(var4).getStack());
                }
                this.playerEntity.sendContainerAndContentsToPlayer(this.playerEntity.openContainer, var3);
            }
        }
    }
    
    @Override
    public void handleEnchantItem(final Packet108EnchantItem par1Packet108EnchantItem) {
        if (this.playerEntity.openContainer.windowId == par1Packet108EnchantItem.windowId && this.playerEntity.openContainer.isPlayerNotUsingContainer(this.playerEntity)) {
            this.playerEntity.openContainer.enchantItem(this.playerEntity, par1Packet108EnchantItem.enchantment);
            this.playerEntity.openContainer.detectAndSendChanges();
        }
    }
    
    @Override
    public void handleCreativeSetSlot(final Packet107CreativeSetSlot par1Packet107CreativeSetSlot) {
        if (this.playerEntity.theItemInWorldManager.isCreative()) {
            final boolean var2 = par1Packet107CreativeSetSlot.slot < 0;
            final ItemStack var3 = par1Packet107CreativeSetSlot.itemStack;
            final boolean var4 = par1Packet107CreativeSetSlot.slot >= 1 && par1Packet107CreativeSetSlot.slot < 36 + InventoryPlayer.getHotbarSize();
            final boolean var5 = var3 == null || (var3.itemID < Item.itemsList.length && var3.itemID >= 0 && Item.itemsList[var3.itemID] != null);
            final boolean var6 = var3 == null || (var3.getItemDamage() >= 0 && var3.getItemDamage() >= 0 && var3.stackSize <= 64 && var3.stackSize > 0);
            if (var4 && var5 && var6) {
                if (var3 == null) {
                    this.playerEntity.inventoryContainer.putStackInSlot(par1Packet107CreativeSetSlot.slot, null);
                }
                else {
                    this.playerEntity.inventoryContainer.putStackInSlot(par1Packet107CreativeSetSlot.slot, var3);
                }
                this.playerEntity.inventoryContainer.setPlayerIsPresent(this.playerEntity, true);
            }
            else if (var2 && var5 && var6 && this.creativeItemCreationSpamThresholdTally < 200) {
                this.creativeItemCreationSpamThresholdTally += 20;
                final EntityItem var7 = this.playerEntity.dropPlayerItem(var3);
                if (var7 != null) {
                    var7.setAgeToCreativeDespawnTime();
                }
            }
        }
    }
    
    @Override
    public void handleTransaction(final Packet106Transaction par1Packet106Transaction) {
        final Short var2 = (Short)this.field_72586_s.lookup(this.playerEntity.openContainer.windowId);
        if (var2 != null && par1Packet106Transaction.shortWindowId == var2 && this.playerEntity.openContainer.windowId == par1Packet106Transaction.windowId && !this.playerEntity.openContainer.isPlayerNotUsingContainer(this.playerEntity)) {
            this.playerEntity.openContainer.setPlayerIsPresent(this.playerEntity, true);
        }
    }
    
    @Override
    public void handleUpdateSign(final Packet130UpdateSign par1Packet130UpdateSign) {
        final WorldServer var2 = this.mcServer.worldServerForDimension(this.playerEntity.dimension);
        if (var2.blockExists(par1Packet130UpdateSign.xPosition, par1Packet130UpdateSign.yPosition, par1Packet130UpdateSign.zPosition)) {
            final TileEntity var3 = var2.getBlockTileEntity(par1Packet130UpdateSign.xPosition, par1Packet130UpdateSign.yPosition, par1Packet130UpdateSign.zPosition);
            if (var3 instanceof TileEntitySign) {
                final TileEntitySign var4 = (TileEntitySign)var3;
                if (!var4.isEditable()) {
                    this.mcServer.logWarning("Player " + this.playerEntity.username + " just tried to change non-editable sign");
                    return;
                }
            }
            for (int var5 = 0; var5 < 4; ++var5) {
                boolean var6 = true;
                if (par1Packet130UpdateSign.signLines[var5].length() > 15) {
                    var6 = false;
                }
                else {
                    for (int var7 = 0; var7 < par1Packet130UpdateSign.signLines[var5].length(); ++var7) {
                        if (ChatAllowedCharacters.allowedCharacters.indexOf(par1Packet130UpdateSign.signLines[var5].charAt(var7)) < 0) {
                            var6 = false;
                        }
                    }
                }
                if (!var6) {
                    par1Packet130UpdateSign.signLines[var5] = "!?";
                }
            }
            if (var3 instanceof TileEntitySign) {
                final int var5 = par1Packet130UpdateSign.xPosition;
                final int var8 = par1Packet130UpdateSign.yPosition;
                final int var7 = par1Packet130UpdateSign.zPosition;
                final TileEntitySign var9 = (TileEntitySign)var3;
                System.arraycopy(par1Packet130UpdateSign.signLines, 0, var9.signText, 0, 4);
                var9.onInventoryChanged();
                var2.markBlockForUpdate(var5, var8, var7);
            }
        }
    }
    
    @Override
    public void handleKeepAlive(final Packet0KeepAlive par1Packet0KeepAlive) {
        if (par1Packet0KeepAlive.randomId == this.keepAliveRandomID) {
            final int var2 = (int)(System.nanoTime() / 1000000L - this.keepAliveTimeSent);
            this.playerEntity.ping = (this.playerEntity.ping * 3 + var2) / 4;
        }
    }
    
    @Override
    public boolean isServerHandler() {
        return true;
    }
    
    @Override
    public void handlePlayerAbilities(final Packet202PlayerAbilities par1Packet202PlayerAbilities) {
        this.playerEntity.capabilities.isFlying = (par1Packet202PlayerAbilities.getFlying() && this.playerEntity.capabilities.allowFlying);
    }
    
    @Override
    public void handleAutoComplete(final Packet203AutoComplete par1Packet203AutoComplete) {
        final StringBuilder var2 = new StringBuilder();
        for (final String var4 : this.mcServer.getPossibleCompletions(this.playerEntity, par1Packet203AutoComplete.getText())) {
            if (var2.length() > 0) {
                var2.append("\u0000");
            }
            var2.append(var4);
        }
        this.playerEntity.playerNetServerHandler.sendPacketToPlayer(new Packet203AutoComplete(var2.toString()));
    }
    
    @Override
    public void handleClientInfo(final Packet204ClientInfo par1Packet204ClientInfo) {
        this.playerEntity.updateClientInfo(par1Packet204ClientInfo);
    }
    
    @Override
    public void handleCustomPayload(final Packet250CustomPayload par1Packet250CustomPayload) {
        if ("MC|BEdit".equals(par1Packet250CustomPayload.channel)) {
            try {
                final DataInputStream var2 = new DataInputStream(new ByteArrayInputStream(par1Packet250CustomPayload.data));
                final ItemStack var3 = Packet.readItemStack(var2);
                if (!ItemWritableBook.validBookTagPages(var3.getTagCompound())) {
                    throw new IOException("Invalid book tag!");
                }
                final ItemStack var4 = this.playerEntity.inventory.getCurrentItem();
                if (var3 != null && var3.itemID == Item.writableBook.itemID && var3.itemID == var4.itemID) {
                    var4.setTagInfo("pages", var3.getTagCompound().getTagList("pages"));
                }
            }
            catch (Exception var5) {
                var5.printStackTrace();
            }
        }
        else if ("MC|BSign".equals(par1Packet250CustomPayload.channel)) {
            try {
                final DataInputStream var2 = new DataInputStream(new ByteArrayInputStream(par1Packet250CustomPayload.data));
                final ItemStack var3 = Packet.readItemStack(var2);
                if (!ItemEditableBook.validBookTagContents(var3.getTagCompound())) {
                    throw new IOException("Invalid book tag!");
                }
                final ItemStack var4 = this.playerEntity.inventory.getCurrentItem();
                if (var3 != null && var3.itemID == Item.writtenBook.itemID && var4.itemID == Item.writableBook.itemID) {
                    var4.setTagInfo("author", new NBTTagString("author", this.playerEntity.username));
                    var4.setTagInfo("title", new NBTTagString("title", var3.getTagCompound().getString("title")));
                    var4.setTagInfo("pages", var3.getTagCompound().getTagList("pages"));
                    var4.itemID = Item.writtenBook.itemID;
                }
            }
            catch (Exception var6) {
                var6.printStackTrace();
            }
        }
        else if ("MC|TrSel".equals(par1Packet250CustomPayload.channel)) {
            try {
                final DataInputStream var2 = new DataInputStream(new ByteArrayInputStream(par1Packet250CustomPayload.data));
                final int var7 = var2.readInt();
                final Container var8 = this.playerEntity.openContainer;
                if (var8 instanceof ContainerMerchant) {
                    ((ContainerMerchant)var8).setCurrentRecipeIndex(var7);
                }
            }
            catch (Exception var9) {
                var9.printStackTrace();
            }
        }
        else if ("MC|AdvCdm".equals(par1Packet250CustomPayload.channel)) {
            if (!this.mcServer.isCommandBlockEnabled()) {
                this.playerEntity.sendChatToPlayer(this.playerEntity.translateString("advMode.notEnabled", new Object[0]));
            }
            else if (this.playerEntity.canCommandSenderUseCommand(2, "") && this.playerEntity.capabilities.isCreativeMode) {
                try {
                    final DataInputStream var2 = new DataInputStream(new ByteArrayInputStream(par1Packet250CustomPayload.data));
                    final int var7 = var2.readInt();
                    final int var10 = var2.readInt();
                    final int var11 = var2.readInt();
                    final String var12 = Packet.readString(var2, 256);
                    final TileEntity var13 = this.playerEntity.worldObj.getBlockTileEntity(var7, var10, var11);
                    if (var13 != null && var13 instanceof TileEntityCommandBlock) {
                        ((TileEntityCommandBlock)var13).setCommand(var12);
                        this.playerEntity.worldObj.markBlockForUpdate(var7, var10, var11);
                        this.playerEntity.sendChatToPlayer("Command set: " + var12);
                    }
                }
                catch (Exception var14) {
                    var14.printStackTrace();
                }
            }
            else {
                this.playerEntity.sendChatToPlayer(this.playerEntity.translateString("advMode.notAllowed", new Object[0]));
            }
        }
        else if ("MC|Beacon".equals(par1Packet250CustomPayload.channel)) {
            if (this.playerEntity.openContainer instanceof ContainerBeacon) {
                try {
                    final DataInputStream var2 = new DataInputStream(new ByteArrayInputStream(par1Packet250CustomPayload.data));
                    final int var7 = var2.readInt();
                    final int var10 = var2.readInt();
                    final ContainerBeacon var15 = (ContainerBeacon)this.playerEntity.openContainer;
                    final Slot var16 = var15.getSlot(0);
                    if (var16.getHasStack()) {
                        var16.decrStackSize(1);
                        final TileEntityBeacon var17 = var15.getBeacon();
                        var17.setPrimaryEffect(var7);
                        var17.setSecondaryEffect(var10);
                        var17.onInventoryChanged();
                    }
                }
                catch (Exception var18) {
                    var18.printStackTrace();
                }
            }
        }
        else if ("MC|ItemName".equals(par1Packet250CustomPayload.channel) && this.playerEntity.openContainer instanceof ContainerRepair) {
            final ContainerRepair var19 = (ContainerRepair)this.playerEntity.openContainer;
            if (par1Packet250CustomPayload.data != null && par1Packet250CustomPayload.data.length >= 1) {
                final String var12 = ChatAllowedCharacters.filerAllowedCharacters(new String(par1Packet250CustomPayload.data));
                if (var12.length() <= 30) {
                    var19.updateItemName(var12);
                }
            }
            else {
                var19.updateItemName("");
            }
        }
    }
}
