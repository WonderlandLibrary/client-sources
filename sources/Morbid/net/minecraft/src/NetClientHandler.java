package net.minecraft.src;

import net.minecraft.client.*;
import java.math.*;
import java.security.*;
import javax.crypto.*;
import java.net.*;
import me.enrythebest.reborn.cracked.*;
import java.io.*;
import org.lwjgl.input.*;
import java.util.*;

public class NetClientHandler extends NetHandler
{
    private boolean disconnected;
    private INetworkManager netManager;
    public String field_72560_a;
    private Minecraft mc;
    private WorldClient worldClient;
    private boolean doneLoadingTerrain;
    public MapStorage mapStorage;
    public Map playerInfoMap;
    public List playerInfoList;
    public int currentServerMaxPlayers;
    private GuiScreen field_98183_l;
    Random rand;
    
    public NetClientHandler(final Minecraft par1Minecraft, final String par2Str, final int par3) throws IOException {
        this.disconnected = false;
        this.doneLoadingTerrain = false;
        this.mapStorage = new MapStorage(null);
        this.playerInfoMap = new HashMap();
        this.playerInfoList = new ArrayList();
        this.currentServerMaxPlayers = 20;
        this.field_98183_l = null;
        this.rand = new Random();
        this.mc = par1Minecraft;
        final Socket var4 = new Socket(InetAddress.getByName(par2Str), par3);
        this.netManager = new TcpConnection(par1Minecraft.getLogAgent(), var4, "Client", this);
    }
    
    public NetClientHandler(final Minecraft par1Minecraft, final String par2Str, final int par3, final GuiScreen par4GuiScreen) throws IOException {
        this.disconnected = false;
        this.doneLoadingTerrain = false;
        this.mapStorage = new MapStorage(null);
        this.playerInfoMap = new HashMap();
        this.playerInfoList = new ArrayList();
        this.currentServerMaxPlayers = 20;
        this.field_98183_l = null;
        this.rand = new Random();
        this.mc = par1Minecraft;
        this.field_98183_l = par4GuiScreen;
        final Socket var5 = new Socket(InetAddress.getByName(par2Str), par3);
        this.netManager = new TcpConnection(par1Minecraft.getLogAgent(), var5, "Client", this);
    }
    
    public NetClientHandler(final Minecraft par1Minecraft, final IntegratedServer par2IntegratedServer) throws IOException {
        this.disconnected = false;
        this.doneLoadingTerrain = false;
        this.mapStorage = new MapStorage(null);
        this.playerInfoMap = new HashMap();
        this.playerInfoList = new ArrayList();
        this.currentServerMaxPlayers = 20;
        this.field_98183_l = null;
        this.rand = new Random();
        this.mc = par1Minecraft;
        this.netManager = new MemoryConnection(par1Minecraft.getLogAgent(), this);
        par2IntegratedServer.getServerListeningThread().func_71754_a((MemoryConnection)this.netManager, par1Minecraft.session.username);
    }
    
    public void cleanup() {
        if (this.netManager != null) {
            this.netManager.wakeThreads();
        }
        this.netManager = null;
        this.worldClient = null;
    }
    
    public void processReadPackets() {
        if (!this.disconnected && this.netManager != null) {
            this.netManager.processReadPackets();
        }
        if (this.netManager != null) {
            this.netManager.wakeThreads();
        }
    }
    
    @Override
    public void handleServerAuthData(final Packet253ServerAuthData par1Packet253ServerAuthData) {
        final String var2 = par1Packet253ServerAuthData.getServerId().trim();
        final PublicKey var3 = par1Packet253ServerAuthData.getPublicKey();
        final SecretKey var4 = CryptManager.createNewSharedKey();
        if (!"-".equals(var2)) {
            final String var5 = new BigInteger(CryptManager.getServerIdHash(var2, var3, var4)).toString(16);
            final String var6 = this.sendSessionRequest(this.mc.session.username, this.mc.session.sessionId, var5);
            if (!"ok".equalsIgnoreCase(var6)) {
                this.netManager.networkShutdown("disconnect.loginFailedInfo", var6);
                return;
            }
        }
        this.addToSendQueue(new Packet252SharedKey(var4, var3, par1Packet253ServerAuthData.getVerifyToken()));
    }
    
    private String sendSessionRequest(final String par1Str, final String par2Str, final String par3Str) {
        try {
            final URL var4 = new URL("http://mcssv0.craftlandia.com.br/game/joinserver?user=" + urlEncode(par1Str) + "&sessionId=" + urlEncode(par2Str) + "&serverId=" + urlEncode(par3Str));
            final BufferedReader var5 = new BufferedReader(new InputStreamReader(var4.openStream()));
            final String var6 = var5.readLine();
            var5.close();
            return var6;
        }
        catch (IOException var7) {
            return var7.toString();
        }
    }
    
    private static String urlEncode(final String par0Str) throws IOException {
        return URLEncoder.encode(par0Str, "UTF-8");
    }
    
    @Override
    public void handleSharedKey(final Packet252SharedKey par1Packet252SharedKey) {
        this.addToSendQueue(new Packet205ClientCommand(0));
    }
    
    @Override
    public void handleLogin(final Packet1Login par1Packet1Login) {
        this.mc.playerController = new PlayerControllerMP(this.mc, this);
        this.mc.statFileWriter.readStat(StatList.joinMultiplayerStat, 1);
        this.worldClient = new WorldClient(this, new WorldSettings(0L, par1Packet1Login.gameType, false, par1Packet1Login.hardcoreMode, par1Packet1Login.terrainType), par1Packet1Login.dimension, par1Packet1Login.difficultySetting, this.mc.mcProfiler, this.mc.getLogAgent());
        this.worldClient.isRemote = true;
        this.mc.loadWorld(this.worldClient);
        Minecraft.thePlayer.dimension = par1Packet1Login.dimension;
        this.mc.displayGuiScreen(new GuiDownloadTerrain(this));
        Minecraft.thePlayer.entityId = par1Packet1Login.clientEntityId;
        this.currentServerMaxPlayers = par1Packet1Login.maxPlayers;
        this.mc.playerController.setGameType(par1Packet1Login.gameType);
        this.mc.gameSettings.sendSettingsToServer();
    }
    
    @Override
    public void handleVehicleSpawn(final Packet23VehicleSpawn par1Packet23VehicleSpawn) {
        final double var2 = par1Packet23VehicleSpawn.xPosition / 32.0;
        final double var3 = par1Packet23VehicleSpawn.yPosition / 32.0;
        final double var4 = par1Packet23VehicleSpawn.zPosition / 32.0;
        Object var5 = null;
        if (par1Packet23VehicleSpawn.type == 10) {
            var5 = EntityMinecart.createMinecart(this.worldClient, var2, var3, var4, par1Packet23VehicleSpawn.throwerEntityId);
        }
        else if (par1Packet23VehicleSpawn.type == 90) {
            final Entity var6 = this.getEntityByID(par1Packet23VehicleSpawn.throwerEntityId);
            if (var6 instanceof EntityPlayer) {
                var5 = new EntityFishHook(this.worldClient, var2, var3, var4, (EntityPlayer)var6);
            }
            par1Packet23VehicleSpawn.throwerEntityId = 0;
        }
        else if (par1Packet23VehicleSpawn.type == 60) {
            var5 = new EntityArrow(this.worldClient, var2, var3, var4);
        }
        else if (par1Packet23VehicleSpawn.type == 61) {
            var5 = new EntitySnowball(this.worldClient, var2, var3, var4);
        }
        else if (par1Packet23VehicleSpawn.type == 71) {
            var5 = new EntityItemFrame(this.worldClient, (int)var2, (int)var3, (int)var4, par1Packet23VehicleSpawn.throwerEntityId);
            par1Packet23VehicleSpawn.throwerEntityId = 0;
        }
        else if (par1Packet23VehicleSpawn.type == 65) {
            var5 = new EntityEnderPearl(this.worldClient, var2, var3, var4);
        }
        else if (par1Packet23VehicleSpawn.type == 72) {
            var5 = new EntityEnderEye(this.worldClient, var2, var3, var4);
        }
        else if (par1Packet23VehicleSpawn.type == 76) {
            var5 = new EntityFireworkRocket(this.worldClient, var2, var3, var4, null);
        }
        else if (par1Packet23VehicleSpawn.type == 63) {
            var5 = new EntityLargeFireball(this.worldClient, var2, var3, var4, par1Packet23VehicleSpawn.speedX / 8000.0, par1Packet23VehicleSpawn.speedY / 8000.0, par1Packet23VehicleSpawn.speedZ / 8000.0);
            par1Packet23VehicleSpawn.throwerEntityId = 0;
        }
        else if (par1Packet23VehicleSpawn.type == 64) {
            var5 = new EntitySmallFireball(this.worldClient, var2, var3, var4, par1Packet23VehicleSpawn.speedX / 8000.0, par1Packet23VehicleSpawn.speedY / 8000.0, par1Packet23VehicleSpawn.speedZ / 8000.0);
            par1Packet23VehicleSpawn.throwerEntityId = 0;
        }
        else if (par1Packet23VehicleSpawn.type == 66) {
            var5 = new EntityWitherSkull(this.worldClient, var2, var3, var4, par1Packet23VehicleSpawn.speedX / 8000.0, par1Packet23VehicleSpawn.speedY / 8000.0, par1Packet23VehicleSpawn.speedZ / 8000.0);
            par1Packet23VehicleSpawn.throwerEntityId = 0;
        }
        else if (par1Packet23VehicleSpawn.type == 62) {
            var5 = new EntityEgg(this.worldClient, var2, var3, var4);
        }
        else if (par1Packet23VehicleSpawn.type == 73) {
            var5 = new EntityPotion(this.worldClient, var2, var3, var4, par1Packet23VehicleSpawn.throwerEntityId);
            par1Packet23VehicleSpawn.throwerEntityId = 0;
        }
        else if (par1Packet23VehicleSpawn.type == 75) {
            var5 = new EntityExpBottle(this.worldClient, var2, var3, var4);
            par1Packet23VehicleSpawn.throwerEntityId = 0;
        }
        else if (par1Packet23VehicleSpawn.type == 1) {
            var5 = new EntityBoat(this.worldClient, var2, var3, var4);
        }
        else if (par1Packet23VehicleSpawn.type == 50) {
            var5 = new EntityTNTPrimed(this.worldClient, var2, var3, var4, null);
        }
        else if (par1Packet23VehicleSpawn.type == 51) {
            var5 = new EntityEnderCrystal(this.worldClient, var2, var3, var4);
        }
        else if (par1Packet23VehicleSpawn.type == 2) {
            var5 = new EntityItem(this.worldClient, var2, var3, var4);
        }
        else if (par1Packet23VehicleSpawn.type == 70) {
            var5 = new EntityFallingSand(this.worldClient, var2, var3, var4, par1Packet23VehicleSpawn.throwerEntityId & 0xFFFF, par1Packet23VehicleSpawn.throwerEntityId >> 16);
            par1Packet23VehicleSpawn.throwerEntityId = 0;
        }
        if (var5 != null) {
            ((Entity)var5).serverPosX = par1Packet23VehicleSpawn.xPosition;
            ((Entity)var5).serverPosY = par1Packet23VehicleSpawn.yPosition;
            ((Entity)var5).serverPosZ = par1Packet23VehicleSpawn.zPosition;
            ((Entity)var5).rotationPitch = par1Packet23VehicleSpawn.pitch * 360 / 256.0f;
            ((Entity)var5).rotationYaw = par1Packet23VehicleSpawn.yaw * 360 / 256.0f;
            final Entity[] var7 = ((Entity)var5).getParts();
            if (var7 != null) {
                final int var8 = par1Packet23VehicleSpawn.entityId - ((Entity)var5).entityId;
                for (int var9 = 0; var9 < var7.length; ++var9) {
                    final Entity entity = var7[var9];
                    entity.entityId += var8;
                }
            }
            ((Entity)var5).entityId = par1Packet23VehicleSpawn.entityId;
            this.worldClient.addEntityToWorld(par1Packet23VehicleSpawn.entityId, (Entity)var5);
            if (par1Packet23VehicleSpawn.throwerEntityId > 0) {
                if (par1Packet23VehicleSpawn.type == 60) {
                    final Entity var10 = this.getEntityByID(par1Packet23VehicleSpawn.throwerEntityId);
                    if (var10 instanceof EntityLiving) {
                        final EntityArrow var11 = (EntityArrow)var5;
                        var11.shootingEntity = var10;
                    }
                }
                ((Entity)var5).setVelocity(par1Packet23VehicleSpawn.speedX / 8000.0, par1Packet23VehicleSpawn.speedY / 8000.0, par1Packet23VehicleSpawn.speedZ / 8000.0);
            }
        }
    }
    
    @Override
    public void handleEntityExpOrb(final Packet26EntityExpOrb par1Packet26EntityExpOrb) {
        final EntityXPOrb var2 = new EntityXPOrb(this.worldClient, par1Packet26EntityExpOrb.posX, par1Packet26EntityExpOrb.posY, par1Packet26EntityExpOrb.posZ, par1Packet26EntityExpOrb.xpValue);
        var2.serverPosX = par1Packet26EntityExpOrb.posX;
        var2.serverPosY = par1Packet26EntityExpOrb.posY;
        var2.serverPosZ = par1Packet26EntityExpOrb.posZ;
        var2.rotationYaw = 0.0f;
        var2.rotationPitch = 0.0f;
        var2.entityId = par1Packet26EntityExpOrb.entityId;
        this.worldClient.addEntityToWorld(par1Packet26EntityExpOrb.entityId, var2);
    }
    
    @Override
    public void handleWeather(final Packet71Weather par1Packet71Weather) {
        final double var2 = par1Packet71Weather.posX / 32.0;
        final double var3 = par1Packet71Weather.posY / 32.0;
        final double var4 = par1Packet71Weather.posZ / 32.0;
        EntityLightningBolt var5 = null;
        if (par1Packet71Weather.isLightningBolt == 1) {
            var5 = new EntityLightningBolt(this.worldClient, var2, var3, var4);
        }
        if (var5 != null) {
            var5.serverPosX = par1Packet71Weather.posX;
            var5.serverPosY = par1Packet71Weather.posY;
            var5.serverPosZ = par1Packet71Weather.posZ;
            var5.rotationYaw = 0.0f;
            var5.rotationPitch = 0.0f;
            var5.entityId = par1Packet71Weather.entityID;
            this.worldClient.addWeatherEffect(var5);
        }
    }
    
    @Override
    public void handleEntityPainting(final Packet25EntityPainting par1Packet25EntityPainting) {
        final EntityPainting var2 = new EntityPainting(this.worldClient, par1Packet25EntityPainting.xPosition, par1Packet25EntityPainting.yPosition, par1Packet25EntityPainting.zPosition, par1Packet25EntityPainting.direction, par1Packet25EntityPainting.title);
        this.worldClient.addEntityToWorld(par1Packet25EntityPainting.entityId, var2);
    }
    
    @Override
    public void handleEntityVelocity(final Packet28EntityVelocity par1Packet28EntityVelocity) {
        if (!Morbid.getHookManager().onVelocityPacket(par1Packet28EntityVelocity)) {
            final Entity var2 = this.getEntityByID(par1Packet28EntityVelocity.entityId);
            if (var2 != null) {
                var2.setVelocity(par1Packet28EntityVelocity.motionX / 8000.0, par1Packet28EntityVelocity.motionY / 8000.0, par1Packet28EntityVelocity.motionZ / 8000.0);
            }
        }
    }
    
    @Override
    public void handleEntityMetadata(final Packet40EntityMetadata par1Packet40EntityMetadata) {
        final Entity var2 = this.getEntityByID(par1Packet40EntityMetadata.entityId);
        if (var2 != null && par1Packet40EntityMetadata.getMetadata() != null) {
            var2.getDataWatcher().updateWatchedObjectsFromList(par1Packet40EntityMetadata.getMetadata());
        }
    }
    
    @Override
    public void handleNamedEntitySpawn(final Packet20NamedEntitySpawn par1Packet20NamedEntitySpawn) {
        final double var2 = par1Packet20NamedEntitySpawn.xPosition / 32.0;
        final double var3 = par1Packet20NamedEntitySpawn.yPosition / 32.0;
        final double var4 = par1Packet20NamedEntitySpawn.zPosition / 32.0;
        final float var5 = par1Packet20NamedEntitySpawn.rotation * 360 / 256.0f;
        final float var6 = par1Packet20NamedEntitySpawn.pitch * 360 / 256.0f;
        final EntityOtherPlayerMP entityOtherPlayerMP3;
        final EntityOtherPlayerMP entityOtherPlayerMP2;
        final EntityOtherPlayerMP entityOtherPlayerMP;
        final EntityOtherPlayerMP var7 = entityOtherPlayerMP = (entityOtherPlayerMP2 = (entityOtherPlayerMP3 = new EntityOtherPlayerMP(Minecraft.theWorld, par1Packet20NamedEntitySpawn.name)));
        final int xPosition = par1Packet20NamedEntitySpawn.xPosition;
        entityOtherPlayerMP.serverPosX = xPosition;
        final double n = xPosition;
        entityOtherPlayerMP2.lastTickPosX = n;
        entityOtherPlayerMP3.prevPosX = n;
        final EntityOtherPlayerMP entityOtherPlayerMP4 = var7;
        final EntityOtherPlayerMP entityOtherPlayerMP5 = var7;
        final EntityOtherPlayerMP entityOtherPlayerMP6 = var7;
        final int yPosition = par1Packet20NamedEntitySpawn.yPosition;
        entityOtherPlayerMP6.serverPosY = yPosition;
        final double n2 = yPosition;
        entityOtherPlayerMP5.lastTickPosY = n2;
        entityOtherPlayerMP4.prevPosY = n2;
        final EntityOtherPlayerMP entityOtherPlayerMP7 = var7;
        final EntityOtherPlayerMP entityOtherPlayerMP8 = var7;
        final EntityOtherPlayerMP entityOtherPlayerMP9 = var7;
        final int zPosition = par1Packet20NamedEntitySpawn.zPosition;
        entityOtherPlayerMP9.serverPosZ = zPosition;
        final double n3 = zPosition;
        entityOtherPlayerMP8.lastTickPosZ = n3;
        entityOtherPlayerMP7.prevPosZ = n3;
        final int var8 = par1Packet20NamedEntitySpawn.currentItem;
        if (var8 == 0) {
            var7.inventory.mainInventory[var7.inventory.currentItem] = null;
        }
        else {
            var7.inventory.mainInventory[var7.inventory.currentItem] = new ItemStack(var8, 1, 0);
        }
        var7.setPositionAndRotation(var2, var3, var4, var5, var6);
        this.worldClient.addEntityToWorld(par1Packet20NamedEntitySpawn.entityId, var7);
        final List var9 = par1Packet20NamedEntitySpawn.getWatchedMetadata();
        if (var9 != null) {
            var7.getDataWatcher().updateWatchedObjectsFromList(var9);
        }
    }
    
    @Override
    public void handleEntityTeleport(final Packet34EntityTeleport par1Packet34EntityTeleport) {
        final Entity var2 = this.getEntityByID(par1Packet34EntityTeleport.entityId);
        if (var2 != null) {
            var2.serverPosX = par1Packet34EntityTeleport.xPosition;
            var2.serverPosY = par1Packet34EntityTeleport.yPosition;
            var2.serverPosZ = par1Packet34EntityTeleport.zPosition;
            final double var3 = var2.serverPosX / 32.0;
            final double var4 = var2.serverPosY / 32.0 + 0.015625;
            final double var5 = var2.serverPosZ / 32.0;
            final float var6 = par1Packet34EntityTeleport.yaw * 360 / 256.0f;
            final float var7 = par1Packet34EntityTeleport.pitch * 360 / 256.0f;
            var2.setPositionAndRotation2(var3, var4, var5, var6, var7, 3);
        }
    }
    
    @Override
    public void handleBlockItemSwitch(final Packet16BlockItemSwitch par1Packet16BlockItemSwitch) {
        if (par1Packet16BlockItemSwitch.id >= 0 && par1Packet16BlockItemSwitch.id < InventoryPlayer.getHotbarSize()) {
            Minecraft.thePlayer.inventory.currentItem = par1Packet16BlockItemSwitch.id;
        }
    }
    
    @Override
    public void handleEntity(final Packet30Entity par1Packet30Entity) {
        final Entity var2 = this.getEntityByID(par1Packet30Entity.entityId);
        if (var2 != null) {
            final Entity entity = var2;
            entity.serverPosX += par1Packet30Entity.xPosition;
            final Entity entity2 = var2;
            entity2.serverPosY += par1Packet30Entity.yPosition;
            final Entity entity3 = var2;
            entity3.serverPosZ += par1Packet30Entity.zPosition;
            final double var3 = var2.serverPosX / 32.0;
            final double var4 = var2.serverPosY / 32.0;
            final double var5 = var2.serverPosZ / 32.0;
            final float var6 = par1Packet30Entity.rotating ? (par1Packet30Entity.yaw * 360 / 256.0f) : var2.rotationYaw;
            final float var7 = par1Packet30Entity.rotating ? (par1Packet30Entity.pitch * 360 / 256.0f) : var2.rotationPitch;
            var2.setPositionAndRotation2(var3, var4, var5, var6, var7, 3);
        }
    }
    
    @Override
    public void handleEntityHeadRotation(final Packet35EntityHeadRotation par1Packet35EntityHeadRotation) {
        final Entity var2 = this.getEntityByID(par1Packet35EntityHeadRotation.entityId);
        if (var2 != null) {
            final float var3 = par1Packet35EntityHeadRotation.headRotationYaw * 360 / 256.0f;
            var2.setRotationYawHead(var3);
        }
    }
    
    @Override
    public void handleDestroyEntity(final Packet29DestroyEntity par1Packet29DestroyEntity) {
        for (int var2 = 0; var2 < par1Packet29DestroyEntity.entityId.length; ++var2) {
            this.worldClient.removeEntityFromWorld(par1Packet29DestroyEntity.entityId[var2]);
        }
    }
    
    @Override
    public void handleFlying(final Packet10Flying par1Packet10Flying) {
        final EntityClientPlayerMP var2 = Minecraft.thePlayer;
        double var3 = var2.posX;
        double var4 = var2.posY;
        double var5 = var2.posZ;
        float var6 = var2.rotationYaw;
        float var7 = var2.rotationPitch;
        if (par1Packet10Flying.moving) {
            var3 = par1Packet10Flying.xPosition;
            var4 = par1Packet10Flying.yPosition;
            var5 = par1Packet10Flying.zPosition;
        }
        if (par1Packet10Flying.rotating) {
            var6 = par1Packet10Flying.yaw;
            var7 = par1Packet10Flying.pitch;
        }
        var2.ySize = 0.0f;
        final EntityClientPlayerMP entityClientPlayerMP = var2;
        final EntityClientPlayerMP entityClientPlayerMP2 = var2;
        final EntityClientPlayerMP entityClientPlayerMP3 = var2;
        final double motionX = 0.0;
        entityClientPlayerMP3.motionZ = motionX;
        entityClientPlayerMP2.motionY = motionX;
        entityClientPlayerMP.motionX = motionX;
        var2.setPositionAndRotation(var3, var4, var5, var6, var7);
        par1Packet10Flying.xPosition = var2.posX;
        par1Packet10Flying.yPosition = var2.boundingBox.minY;
        par1Packet10Flying.zPosition = var2.posZ;
        par1Packet10Flying.stance = var2.posY;
        this.netManager.addToSendQueue(par1Packet10Flying);
        if (!this.doneLoadingTerrain) {
            Minecraft.thePlayer.prevPosX = Minecraft.thePlayer.posX;
            Minecraft.thePlayer.prevPosY = Minecraft.thePlayer.posY;
            Minecraft.thePlayer.prevPosZ = Minecraft.thePlayer.posZ;
            this.doneLoadingTerrain = true;
            this.mc.displayGuiScreen(null);
        }
    }
    
    @Override
    public void handleMultiBlockChange(final Packet52MultiBlockChange par1Packet52MultiBlockChange) {
        final int var2 = par1Packet52MultiBlockChange.xPosition * 16;
        final int var3 = par1Packet52MultiBlockChange.zPosition * 16;
        if (par1Packet52MultiBlockChange.metadataArray != null) {
            final DataInputStream var4 = new DataInputStream(new ByteArrayInputStream(par1Packet52MultiBlockChange.metadataArray));
            try {
                for (int var5 = 0; var5 < par1Packet52MultiBlockChange.size; ++var5) {
                    final short var6 = var4.readShort();
                    final short var7 = var4.readShort();
                    final int var8 = var7 >> 4 & 0xFFF;
                    final int var9 = var7 & 0xF;
                    final int var10 = var6 >> 12 & 0xF;
                    final int var11 = var6 >> 8 & 0xF;
                    final int var12 = var6 & 0xFF;
                    this.worldClient.setBlockAndMetadataAndInvalidate(var10 + var2, var12, var11 + var3, var8, var9);
                }
            }
            catch (IOException ex) {}
        }
    }
    
    @Override
    public void handleMapChunk(final Packet51MapChunk par1Packet51MapChunk) {
        if (par1Packet51MapChunk.includeInitialize) {
            if (par1Packet51MapChunk.yChMin == 0) {
                this.worldClient.doPreChunk(par1Packet51MapChunk.xCh, par1Packet51MapChunk.zCh, false);
                return;
            }
            this.worldClient.doPreChunk(par1Packet51MapChunk.xCh, par1Packet51MapChunk.zCh, true);
        }
        this.worldClient.invalidateBlockReceiveRegion(par1Packet51MapChunk.xCh << 4, 0, par1Packet51MapChunk.zCh << 4, (par1Packet51MapChunk.xCh << 4) + 15, 256, (par1Packet51MapChunk.zCh << 4) + 15);
        Chunk var2 = this.worldClient.getChunkFromChunkCoords(par1Packet51MapChunk.xCh, par1Packet51MapChunk.zCh);
        if (par1Packet51MapChunk.includeInitialize && var2 == null) {
            this.worldClient.doPreChunk(par1Packet51MapChunk.xCh, par1Packet51MapChunk.zCh, true);
            var2 = this.worldClient.getChunkFromChunkCoords(par1Packet51MapChunk.xCh, par1Packet51MapChunk.zCh);
        }
        if (var2 != null) {
            var2.fillChunk(par1Packet51MapChunk.getCompressedChunkData(), par1Packet51MapChunk.yChMin, par1Packet51MapChunk.yChMax, par1Packet51MapChunk.includeInitialize);
            this.worldClient.markBlockRangeForRenderUpdate(par1Packet51MapChunk.xCh << 4, 0, par1Packet51MapChunk.zCh << 4, (par1Packet51MapChunk.xCh << 4) + 15, 256, (par1Packet51MapChunk.zCh << 4) + 15);
            if (!par1Packet51MapChunk.includeInitialize || !(this.worldClient.provider instanceof WorldProviderSurface)) {
                var2.resetRelightChecks();
            }
        }
    }
    
    @Override
    public void handleBlockChange(final Packet53BlockChange par1Packet53BlockChange) {
        this.worldClient.setBlockAndMetadataAndInvalidate(par1Packet53BlockChange.xPosition, par1Packet53BlockChange.yPosition, par1Packet53BlockChange.zPosition, par1Packet53BlockChange.type, par1Packet53BlockChange.metadata);
    }
    
    @Override
    public void handleKickDisconnect(final Packet255KickDisconnect par1Packet255KickDisconnect) {
        this.netManager.networkShutdown("disconnect.kicked", new Object[0]);
        this.disconnected = true;
        this.mc.loadWorld(null);
        if (this.field_98183_l != null) {
            this.mc.displayGuiScreen(new GuiScreenDisconnectedOnline(this.field_98183_l, "disconnect.disconnected", "disconnect.genericReason", new Object[] { par1Packet255KickDisconnect.reason }));
        }
        else {
            this.mc.displayGuiScreen(new GuiDisconnected(new GuiMultiplayer(new GuiMainMenu()), "disconnect.disconnected", "disconnect.genericReason", new Object[] { par1Packet255KickDisconnect.reason }));
        }
    }
    
    @Override
    public void handleErrorMessage(final String par1Str, final Object[] par2ArrayOfObj) {
        if (!this.disconnected) {
            this.disconnected = true;
            this.mc.loadWorld(null);
            if (this.field_98183_l != null) {
                this.mc.displayGuiScreen(new GuiScreenDisconnectedOnline(this.field_98183_l, "disconnect.lost", par1Str, par2ArrayOfObj));
            }
            else {
                this.mc.displayGuiScreen(new GuiDisconnected(new GuiMultiplayer(new GuiMainMenu()), "disconnect.lost", par1Str, par2ArrayOfObj));
            }
        }
    }
    
    public void quitWithPacket(final Packet par1Packet) {
        if (!this.disconnected) {
            this.netManager.addToSendQueue(par1Packet);
            this.netManager.serverShutdown();
        }
    }
    
    public void addToSendQueue(final Packet par1Packet) {
        if (!this.disconnected) {
            this.netManager.addToSendQueue(par1Packet);
        }
    }
    
    @Override
    public void handleCollect(final Packet22Collect par1Packet22Collect) {
        final Entity var2 = this.getEntityByID(par1Packet22Collect.collectedEntityId);
        Object var3 = this.getEntityByID(par1Packet22Collect.collectorEntityId);
        if (var3 == null) {
            var3 = Minecraft.thePlayer;
        }
        if (var2 != null) {
            if (var2 instanceof EntityXPOrb) {
                this.worldClient.playSoundAtEntity(var2, "random.orb", 0.2f, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7f + 1.0f) * 2.0f);
            }
            else {
                this.worldClient.playSoundAtEntity(var2, "random.pop", 0.2f, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7f + 1.0f) * 2.0f);
            }
            this.mc.effectRenderer.addEffect(new EntityPickupFX(Minecraft.theWorld, var2, (Entity)var3, -0.5f));
            this.worldClient.removeEntityFromWorld(par1Packet22Collect.collectedEntityId);
        }
    }
    
    @Override
    public void handleChat(final Packet3Chat par1Packet3Chat) {
        this.mc.ingameGUI.getChatGUI().printChatMessage(par1Packet3Chat.message);
    }
    
    @Override
    public void handleAnimation(final Packet18Animation par1Packet18Animation) {
        final Entity var2 = this.getEntityByID(par1Packet18Animation.entityId);
        if (var2 != null) {
            if (par1Packet18Animation.animate == 1) {
                final EntityLiving var3 = (EntityLiving)var2;
                var3.swingItem();
            }
            else if (par1Packet18Animation.animate == 2) {
                var2.performHurtAnimation();
            }
            else if (par1Packet18Animation.animate == 3) {
                final EntityPlayer var4 = (EntityPlayer)var2;
                var4.wakeUpPlayer(false, false, false);
            }
            else if (par1Packet18Animation.animate != 4) {
                if (par1Packet18Animation.animate == 6) {
                    this.mc.effectRenderer.addEffect(new EntityCrit2FX(Minecraft.theWorld, var2));
                }
                else if (par1Packet18Animation.animate == 7) {
                    final EntityCrit2FX var5 = new EntityCrit2FX(Minecraft.theWorld, var2, "magicCrit");
                    this.mc.effectRenderer.addEffect(var5);
                }
                else if (par1Packet18Animation.animate != 5 || var2 instanceof EntityOtherPlayerMP) {}
            }
        }
    }
    
    @Override
    public void handleSleep(final Packet17Sleep par1Packet17Sleep) {
        final Entity var2 = this.getEntityByID(par1Packet17Sleep.entityID);
        if (var2 != null && par1Packet17Sleep.field_73622_e == 0) {
            final EntityPlayer var3 = (EntityPlayer)var2;
            var3.sleepInBedAt(par1Packet17Sleep.bedX, par1Packet17Sleep.bedY, par1Packet17Sleep.bedZ);
        }
    }
    
    public void disconnect() {
        this.disconnected = true;
        this.netManager.wakeThreads();
        this.netManager.networkShutdown("disconnect.closed", new Object[0]);
    }
    
    @Override
    public void handleMobSpawn(final Packet24MobSpawn par1Packet24MobSpawn) {
        final double var2 = par1Packet24MobSpawn.xPosition / 32.0;
        final double var3 = par1Packet24MobSpawn.yPosition / 32.0;
        final double var4 = par1Packet24MobSpawn.zPosition / 32.0;
        final float var5 = par1Packet24MobSpawn.yaw * 360 / 256.0f;
        final float var6 = par1Packet24MobSpawn.pitch * 360 / 256.0f;
        final EntityLiving var7 = (EntityLiving)EntityList.createEntityByID(par1Packet24MobSpawn.type, Minecraft.theWorld);
        var7.serverPosX = par1Packet24MobSpawn.xPosition;
        var7.serverPosY = par1Packet24MobSpawn.yPosition;
        var7.serverPosZ = par1Packet24MobSpawn.zPosition;
        var7.rotationYawHead = par1Packet24MobSpawn.headYaw * 360 / 256.0f;
        final Entity[] var8 = var7.getParts();
        if (var8 != null) {
            final int var9 = par1Packet24MobSpawn.entityId - var7.entityId;
            for (int var10 = 0; var10 < var8.length; ++var10) {
                final Entity entity = var8[var10];
                entity.entityId += var9;
            }
        }
        var7.entityId = par1Packet24MobSpawn.entityId;
        var7.setPositionAndRotation(var2, var3, var4, var5, var6);
        var7.motionX = par1Packet24MobSpawn.velocityX / 8000.0f;
        var7.motionY = par1Packet24MobSpawn.velocityY / 8000.0f;
        var7.motionZ = par1Packet24MobSpawn.velocityZ / 8000.0f;
        this.worldClient.addEntityToWorld(par1Packet24MobSpawn.entityId, var7);
        final List var11 = par1Packet24MobSpawn.getMetadata();
        if (var11 != null) {
            var7.getDataWatcher().updateWatchedObjectsFromList(var11);
        }
    }
    
    @Override
    public void handleUpdateTime(final Packet4UpdateTime par1Packet4UpdateTime) {
        Minecraft.theWorld.func_82738_a(par1Packet4UpdateTime.worldAge);
        Minecraft.theWorld.setWorldTime(par1Packet4UpdateTime.time);
    }
    
    @Override
    public void handleSpawnPosition(final Packet6SpawnPosition par1Packet6SpawnPosition) {
        Minecraft.thePlayer.setSpawnChunk(new ChunkCoordinates(par1Packet6SpawnPosition.xPosition, par1Packet6SpawnPosition.yPosition, par1Packet6SpawnPosition.zPosition), true);
        Minecraft.theWorld.getWorldInfo().setSpawnPosition(par1Packet6SpawnPosition.xPosition, par1Packet6SpawnPosition.yPosition, par1Packet6SpawnPosition.zPosition);
    }
    
    @Override
    public void handleAttachEntity(final Packet39AttachEntity par1Packet39AttachEntity) {
        Object var2 = this.getEntityByID(par1Packet39AttachEntity.entityId);
        final Entity var3 = this.getEntityByID(par1Packet39AttachEntity.vehicleEntityId);
        if (par1Packet39AttachEntity.entityId == Minecraft.thePlayer.entityId) {
            var2 = Minecraft.thePlayer;
            if (var3 instanceof EntityBoat) {
                ((EntityBoat)var3).func_70270_d(false);
            }
        }
        else if (var3 instanceof EntityBoat) {
            ((EntityBoat)var3).func_70270_d(true);
        }
        if (var2 != null) {
            ((Entity)var2).mountEntity(var3);
        }
    }
    
    @Override
    public void handleEntityStatus(final Packet38EntityStatus par1Packet38EntityStatus) {
        final Entity var2 = this.getEntityByID(par1Packet38EntityStatus.entityId);
        if (var2 != null) {
            var2.handleHealthUpdate(par1Packet38EntityStatus.entityStatus);
        }
    }
    
    private Entity getEntityByID(final int par1) {
        return (par1 == Minecraft.thePlayer.entityId) ? Minecraft.thePlayer : this.worldClient.getEntityByID(par1);
    }
    
    @Override
    public void handleUpdateHealth(final Packet8UpdateHealth par1Packet8UpdateHealth) {
        Minecraft.thePlayer.setHealth(par1Packet8UpdateHealth.healthMP);
        Minecraft.thePlayer.getFoodStats().setFoodLevel(par1Packet8UpdateHealth.food);
        Minecraft.thePlayer.getFoodStats().setFoodSaturationLevel(par1Packet8UpdateHealth.foodSaturation);
    }
    
    @Override
    public void handleExperience(final Packet43Experience par1Packet43Experience) {
        Minecraft.thePlayer.setXPStats(par1Packet43Experience.experience, par1Packet43Experience.experienceTotal, par1Packet43Experience.experienceLevel);
    }
    
    @Override
    public void handleRespawn(final Packet9Respawn par1Packet9Respawn) {
        if (par1Packet9Respawn.respawnDimension != Minecraft.thePlayer.dimension) {
            this.doneLoadingTerrain = false;
            final Scoreboard var2 = this.worldClient.getScoreboard();
            (this.worldClient = new WorldClient(this, new WorldSettings(0L, par1Packet9Respawn.gameType, false, Minecraft.theWorld.getWorldInfo().isHardcoreModeEnabled(), par1Packet9Respawn.terrainType), par1Packet9Respawn.respawnDimension, par1Packet9Respawn.difficulty, this.mc.mcProfiler, this.mc.getLogAgent())).func_96443_a(var2);
            this.worldClient.isRemote = true;
            this.mc.loadWorld(this.worldClient);
            Minecraft.thePlayer.dimension = par1Packet9Respawn.respawnDimension;
            this.mc.displayGuiScreen(new GuiDownloadTerrain(this));
        }
        this.mc.setDimensionAndSpawnPlayer(par1Packet9Respawn.respawnDimension);
        this.mc.playerController.setGameType(par1Packet9Respawn.gameType);
    }
    
    @Override
    public void handleExplosion(final Packet60Explosion par1Packet60Explosion) {
        final Explosion var2 = new Explosion(Minecraft.theWorld, null, par1Packet60Explosion.explosionX, par1Packet60Explosion.explosionY, par1Packet60Explosion.explosionZ, par1Packet60Explosion.explosionSize);
        var2.affectedBlockPositions = par1Packet60Explosion.chunkPositionRecords;
        var2.doExplosionB(true);
        final EntityClientPlayerMP thePlayer = Minecraft.thePlayer;
        thePlayer.motionX += par1Packet60Explosion.getPlayerVelocityX();
        final EntityClientPlayerMP thePlayer2 = Minecraft.thePlayer;
        thePlayer2.motionY += par1Packet60Explosion.getPlayerVelocityY();
        final EntityClientPlayerMP thePlayer3 = Minecraft.thePlayer;
        thePlayer3.motionZ += par1Packet60Explosion.getPlayerVelocityZ();
    }
    
    @Override
    public void handleOpenWindow(final Packet100OpenWindow par1Packet100OpenWindow) {
        final EntityClientPlayerMP var2 = Minecraft.thePlayer;
        switch (par1Packet100OpenWindow.inventoryType) {
            case 0: {
                var2.displayGUIChest(new InventoryBasic(par1Packet100OpenWindow.windowTitle, par1Packet100OpenWindow.useProvidedWindowTitle, par1Packet100OpenWindow.slotsCount));
                var2.openContainer.windowId = par1Packet100OpenWindow.windowId;
                break;
            }
            case 1: {
                var2.displayGUIWorkbench(MathHelper.floor_double(var2.posX), MathHelper.floor_double(var2.posY), MathHelper.floor_double(var2.posZ));
                var2.openContainer.windowId = par1Packet100OpenWindow.windowId;
                break;
            }
            case 2: {
                final TileEntityFurnace var3 = new TileEntityFurnace();
                if (par1Packet100OpenWindow.useProvidedWindowTitle) {
                    var3.func_94129_a(par1Packet100OpenWindow.windowTitle);
                }
                var2.displayGUIFurnace(var3);
                var2.openContainer.windowId = par1Packet100OpenWindow.windowId;
                break;
            }
            case 3: {
                final TileEntityDispenser var4 = new TileEntityDispenser();
                if (par1Packet100OpenWindow.useProvidedWindowTitle) {
                    var4.setCustomName(par1Packet100OpenWindow.windowTitle);
                }
                var2.displayGUIDispenser(var4);
                var2.openContainer.windowId = par1Packet100OpenWindow.windowId;
                break;
            }
            case 4: {
                var2.displayGUIEnchantment(MathHelper.floor_double(var2.posX), MathHelper.floor_double(var2.posY), MathHelper.floor_double(var2.posZ), par1Packet100OpenWindow.useProvidedWindowTitle ? par1Packet100OpenWindow.windowTitle : null);
                var2.openContainer.windowId = par1Packet100OpenWindow.windowId;
                break;
            }
            case 5: {
                final TileEntityBrewingStand var5 = new TileEntityBrewingStand();
                if (par1Packet100OpenWindow.useProvidedWindowTitle) {
                    var5.func_94131_a(par1Packet100OpenWindow.windowTitle);
                }
                var2.displayGUIBrewingStand(var5);
                var2.openContainer.windowId = par1Packet100OpenWindow.windowId;
                break;
            }
            case 6: {
                var2.displayGUIMerchant(new NpcMerchant(var2), par1Packet100OpenWindow.useProvidedWindowTitle ? par1Packet100OpenWindow.windowTitle : null);
                var2.openContainer.windowId = par1Packet100OpenWindow.windowId;
                break;
            }
            case 7: {
                final TileEntityBeacon var6 = new TileEntityBeacon();
                var2.displayGUIBeacon(var6);
                if (par1Packet100OpenWindow.useProvidedWindowTitle) {
                    var6.func_94047_a(par1Packet100OpenWindow.windowTitle);
                }
                var2.openContainer.windowId = par1Packet100OpenWindow.windowId;
                break;
            }
            case 8: {
                var2.displayGUIAnvil(MathHelper.floor_double(var2.posX), MathHelper.floor_double(var2.posY), MathHelper.floor_double(var2.posZ));
                var2.openContainer.windowId = par1Packet100OpenWindow.windowId;
                break;
            }
            case 9: {
                final TileEntityHopper var7 = new TileEntityHopper();
                if (par1Packet100OpenWindow.useProvidedWindowTitle) {
                    var7.setInventoryName(par1Packet100OpenWindow.windowTitle);
                }
                var2.displayGUIHopper(var7);
                var2.openContainer.windowId = par1Packet100OpenWindow.windowId;
                break;
            }
            case 10: {
                final TileEntityDropper var8 = new TileEntityDropper();
                if (par1Packet100OpenWindow.useProvidedWindowTitle) {
                    var8.setCustomName(par1Packet100OpenWindow.windowTitle);
                }
                var2.displayGUIDispenser(var8);
                var2.openContainer.windowId = par1Packet100OpenWindow.windowId;
                break;
            }
        }
    }
    
    @Override
    public void handleSetSlot(final Packet103SetSlot par1Packet103SetSlot) {
        final EntityClientPlayerMP var2 = Minecraft.thePlayer;
        if (par1Packet103SetSlot.windowId == -1) {
            var2.inventory.setItemStack(par1Packet103SetSlot.myItemStack);
        }
        else {
            boolean var3 = false;
            if (Minecraft.currentScreen instanceof GuiContainerCreative) {
                final GuiContainerCreative var4 = (GuiContainerCreative)Minecraft.currentScreen;
                var3 = (var4.func_74230_h() != CreativeTabs.tabInventory.getTabIndex());
            }
            if (par1Packet103SetSlot.windowId == 0 && par1Packet103SetSlot.itemSlot >= 36 && par1Packet103SetSlot.itemSlot < 45) {
                final ItemStack var5 = var2.inventoryContainer.getSlot(par1Packet103SetSlot.itemSlot).getStack();
                if (par1Packet103SetSlot.myItemStack != null && (var5 == null || var5.stackSize < par1Packet103SetSlot.myItemStack.stackSize)) {
                    par1Packet103SetSlot.myItemStack.animationsToGo = 5;
                }
                var2.inventoryContainer.putStackInSlot(par1Packet103SetSlot.itemSlot, par1Packet103SetSlot.myItemStack);
            }
            else if (par1Packet103SetSlot.windowId == var2.openContainer.windowId && (par1Packet103SetSlot.windowId != 0 || !var3)) {
                var2.openContainer.putStackInSlot(par1Packet103SetSlot.itemSlot, par1Packet103SetSlot.myItemStack);
            }
        }
    }
    
    @Override
    public void handleTransaction(final Packet106Transaction par1Packet106Transaction) {
        Container var2 = null;
        final EntityClientPlayerMP var3 = Minecraft.thePlayer;
        if (par1Packet106Transaction.windowId == 0) {
            var2 = var3.inventoryContainer;
        }
        else if (par1Packet106Transaction.windowId == var3.openContainer.windowId) {
            var2 = var3.openContainer;
        }
        if (var2 != null && !par1Packet106Transaction.accepted) {
            this.addToSendQueue(new Packet106Transaction(par1Packet106Transaction.windowId, par1Packet106Transaction.shortWindowId, true));
        }
    }
    
    @Override
    public void handleWindowItems(final Packet104WindowItems par1Packet104WindowItems) {
        final EntityClientPlayerMP var2 = Minecraft.thePlayer;
        if (par1Packet104WindowItems.windowId == 0) {
            var2.inventoryContainer.putStacksInSlots(par1Packet104WindowItems.itemStack);
        }
        else if (par1Packet104WindowItems.windowId == var2.openContainer.windowId) {
            var2.openContainer.putStacksInSlots(par1Packet104WindowItems.itemStack);
        }
    }
    
    @Override
    public void handleUpdateSign(final Packet130UpdateSign par1Packet130UpdateSign) {
        boolean var2 = false;
        if (Minecraft.theWorld.blockExists(par1Packet130UpdateSign.xPosition, par1Packet130UpdateSign.yPosition, par1Packet130UpdateSign.zPosition)) {
            final TileEntity var3 = Minecraft.theWorld.getBlockTileEntity(par1Packet130UpdateSign.xPosition, par1Packet130UpdateSign.yPosition, par1Packet130UpdateSign.zPosition);
            if (var3 instanceof TileEntitySign) {
                final TileEntitySign var4 = (TileEntitySign)var3;
                if (var4.isEditable()) {
                    for (int var5 = 0; var5 < 4; ++var5) {
                        var4.signText[var5] = par1Packet130UpdateSign.signLines[var5];
                    }
                    var4.onInventoryChanged();
                }
                var2 = true;
            }
        }
        if (!var2 && Minecraft.thePlayer != null) {
            Minecraft.thePlayer.sendChatToPlayer("Unable to locate sign at " + par1Packet130UpdateSign.xPosition + ", " + par1Packet130UpdateSign.yPosition + ", " + par1Packet130UpdateSign.zPosition);
        }
    }
    
    @Override
    public void handleTileEntityData(final Packet132TileEntityData par1Packet132TileEntityData) {
        if (Minecraft.theWorld.blockExists(par1Packet132TileEntityData.xPosition, par1Packet132TileEntityData.yPosition, par1Packet132TileEntityData.zPosition)) {
            final TileEntity var2 = Minecraft.theWorld.getBlockTileEntity(par1Packet132TileEntityData.xPosition, par1Packet132TileEntityData.yPosition, par1Packet132TileEntityData.zPosition);
            if (var2 != null) {
                if (par1Packet132TileEntityData.actionType == 1 && var2 instanceof TileEntityMobSpawner) {
                    var2.readFromNBT(par1Packet132TileEntityData.customParam1);
                }
                else if (par1Packet132TileEntityData.actionType == 2 && var2 instanceof TileEntityCommandBlock) {
                    var2.readFromNBT(par1Packet132TileEntityData.customParam1);
                }
                else if (par1Packet132TileEntityData.actionType == 3 && var2 instanceof TileEntityBeacon) {
                    var2.readFromNBT(par1Packet132TileEntityData.customParam1);
                }
                else if (par1Packet132TileEntityData.actionType == 4 && var2 instanceof TileEntitySkull) {
                    var2.readFromNBT(par1Packet132TileEntityData.customParam1);
                }
            }
        }
    }
    
    @Override
    public void handleUpdateProgressbar(final Packet105UpdateProgressbar par1Packet105UpdateProgressbar) {
        final EntityClientPlayerMP var2 = Minecraft.thePlayer;
        this.unexpectedPacket(par1Packet105UpdateProgressbar);
        if (var2.openContainer != null && var2.openContainer.windowId == par1Packet105UpdateProgressbar.windowId) {
            var2.openContainer.updateProgressBar(par1Packet105UpdateProgressbar.progressBar, par1Packet105UpdateProgressbar.progressBarValue);
        }
    }
    
    @Override
    public void handlePlayerInventory(final Packet5PlayerInventory par1Packet5PlayerInventory) {
        final Entity var2 = this.getEntityByID(par1Packet5PlayerInventory.entityID);
        if (var2 != null) {
            var2.setCurrentItemOrArmor(par1Packet5PlayerInventory.slot, par1Packet5PlayerInventory.getItemSlot());
        }
    }
    
    @Override
    public void handleCloseWindow(final Packet101CloseWindow par1Packet101CloseWindow) {
        Minecraft.thePlayer.func_92015_f();
    }
    
    @Override
    public void handleBlockEvent(final Packet54PlayNoteBlock par1Packet54PlayNoteBlock) {
        Minecraft.theWorld.addBlockEvent(par1Packet54PlayNoteBlock.xLocation, par1Packet54PlayNoteBlock.yLocation, par1Packet54PlayNoteBlock.zLocation, par1Packet54PlayNoteBlock.blockId, par1Packet54PlayNoteBlock.instrumentType, par1Packet54PlayNoteBlock.pitch);
    }
    
    @Override
    public void handleBlockDestroy(final Packet55BlockDestroy par1Packet55BlockDestroy) {
        Minecraft.theWorld.destroyBlockInWorldPartially(par1Packet55BlockDestroy.getEntityId(), par1Packet55BlockDestroy.getPosX(), par1Packet55BlockDestroy.getPosY(), par1Packet55BlockDestroy.getPosZ(), par1Packet55BlockDestroy.getDestroyedStage());
    }
    
    @Override
    public void handleMapChunks(final Packet56MapChunks par1Packet56MapChunks) {
        for (int var2 = 0; var2 < par1Packet56MapChunks.getNumberOfChunkInPacket(); ++var2) {
            final int var3 = par1Packet56MapChunks.getChunkPosX(var2);
            final int var4 = par1Packet56MapChunks.getChunkPosZ(var2);
            this.worldClient.doPreChunk(var3, var4, true);
            this.worldClient.invalidateBlockReceiveRegion(var3 << 4, 0, var4 << 4, (var3 << 4) + 15, 256, (var4 << 4) + 15);
            Chunk var5 = this.worldClient.getChunkFromChunkCoords(var3, var4);
            if (var5 == null) {
                this.worldClient.doPreChunk(var3, var4, true);
                var5 = this.worldClient.getChunkFromChunkCoords(var3, var4);
            }
            if (var5 != null) {
                var5.fillChunk(par1Packet56MapChunks.getChunkCompressedData(var2), par1Packet56MapChunks.field_73590_a[var2], par1Packet56MapChunks.field_73588_b[var2], true);
                this.worldClient.markBlockRangeForRenderUpdate(var3 << 4, 0, var4 << 4, (var3 << 4) + 15, 256, (var4 << 4) + 15);
                if (!(this.worldClient.provider instanceof WorldProviderSurface)) {
                    var5.resetRelightChecks();
                }
            }
        }
    }
    
    @Override
    public boolean canProcessPacketsAsync() {
        return this.mc != null && Minecraft.theWorld != null && Minecraft.thePlayer != null && this.worldClient != null;
    }
    
    @Override
    public void handleGameEvent(final Packet70GameEvent par1Packet70GameEvent) {
        final EntityClientPlayerMP var2 = Minecraft.thePlayer;
        final int var3 = par1Packet70GameEvent.eventType;
        final int var4 = par1Packet70GameEvent.gameMode;
        if (var3 >= 0 && var3 < Packet70GameEvent.clientMessage.length && Packet70GameEvent.clientMessage[var3] != null) {
            var2.addChatMessage(Packet70GameEvent.clientMessage[var3]);
        }
        if (var3 == 1) {
            this.worldClient.getWorldInfo().setRaining(true);
            this.worldClient.setRainStrength(0.0f);
        }
        else if (var3 == 2) {
            this.worldClient.getWorldInfo().setRaining(false);
            this.worldClient.setRainStrength(1.0f);
        }
        else if (var3 == 3) {
            this.mc.playerController.setGameType(EnumGameType.getByID(var4));
        }
        else if (var3 == 4) {
            this.mc.displayGuiScreen(new GuiWinGame());
        }
        else if (var3 == 5) {
            final GameSettings var5 = this.mc.gameSettings;
            if (var4 == 0) {
                this.mc.displayGuiScreen(new GuiScreenDemo());
            }
            else if (var4 == 101) {
                this.mc.ingameGUI.getChatGUI().addTranslatedMessage("demo.help.movement", Keyboard.getKeyName(var5.keyBindForward.keyCode), Keyboard.getKeyName(var5.keyBindLeft.keyCode), Keyboard.getKeyName(var5.keyBindBack.keyCode), Keyboard.getKeyName(var5.keyBindRight.keyCode));
            }
            else if (var4 == 102) {
                this.mc.ingameGUI.getChatGUI().addTranslatedMessage("demo.help.jump", Keyboard.getKeyName(var5.keyBindJump.keyCode));
            }
            else if (var4 == 103) {
                this.mc.ingameGUI.getChatGUI().addTranslatedMessage("demo.help.inventory", Keyboard.getKeyName(var5.keyBindInventory.keyCode));
            }
        }
        else if (var3 == 6) {
            this.worldClient.playSound(var2.posX, var2.posY + var2.getEyeHeight(), var2.posZ, "random.successful_hit", 0.18f, 0.45f, false);
        }
    }
    
    @Override
    public void handleMapData(final Packet131MapData par1Packet131MapData) {
        if (par1Packet131MapData.itemID == Item.map.itemID) {
            ItemMap.getMPMapData(par1Packet131MapData.uniqueID, Minecraft.theWorld).updateMPMapData(par1Packet131MapData.itemData);
        }
        else {
            this.mc.getLogAgent().logWarning("Unknown itemid: " + par1Packet131MapData.uniqueID);
        }
    }
    
    @Override
    public void handleDoorChange(final Packet61DoorChange par1Packet61DoorChange) {
        if (par1Packet61DoorChange.getRelativeVolumeDisabled()) {
            Minecraft.theWorld.func_82739_e(par1Packet61DoorChange.sfxID, par1Packet61DoorChange.posX, par1Packet61DoorChange.posY, par1Packet61DoorChange.posZ, par1Packet61DoorChange.auxData);
        }
        else {
            Minecraft.theWorld.playAuxSFX(par1Packet61DoorChange.sfxID, par1Packet61DoorChange.posX, par1Packet61DoorChange.posY, par1Packet61DoorChange.posZ, par1Packet61DoorChange.auxData);
        }
    }
    
    @Override
    public void handleStatistic(final Packet200Statistic par1Packet200Statistic) {
        Minecraft.thePlayer.incrementStat(StatList.getOneShotStat(par1Packet200Statistic.statisticId), par1Packet200Statistic.amount);
    }
    
    @Override
    public void handleEntityEffect(final Packet41EntityEffect par1Packet41EntityEffect) {
        final Entity var2 = this.getEntityByID(par1Packet41EntityEffect.entityId);
        if (var2 instanceof EntityLiving) {
            final PotionEffect var3 = new PotionEffect(par1Packet41EntityEffect.effectId, par1Packet41EntityEffect.duration, par1Packet41EntityEffect.effectAmplifier);
            var3.setPotionDurationMax(par1Packet41EntityEffect.isDurationMax());
            ((EntityLiving)var2).addPotionEffect(var3);
        }
    }
    
    @Override
    public void handleRemoveEntityEffect(final Packet42RemoveEntityEffect par1Packet42RemoveEntityEffect) {
        final Entity var2 = this.getEntityByID(par1Packet42RemoveEntityEffect.entityId);
        if (var2 instanceof EntityLiving) {
            ((EntityLiving)var2).removePotionEffectClient(par1Packet42RemoveEntityEffect.effectId);
        }
    }
    
    @Override
    public boolean isServerHandler() {
        return false;
    }
    
    @Override
    public void handlePlayerInfo(final Packet201PlayerInfo par1Packet201PlayerInfo) {
        GuiPlayerInfo var2 = this.playerInfoMap.get(par1Packet201PlayerInfo.playerName);
        if (var2 == null && par1Packet201PlayerInfo.isConnected) {
            var2 = new GuiPlayerInfo(par1Packet201PlayerInfo.playerName);
            this.playerInfoMap.put(par1Packet201PlayerInfo.playerName, var2);
            this.playerInfoList.add(var2);
        }
        if (var2 != null && !par1Packet201PlayerInfo.isConnected) {
            this.playerInfoMap.remove(par1Packet201PlayerInfo.playerName);
            this.playerInfoList.remove(var2);
        }
        if (par1Packet201PlayerInfo.isConnected && var2 != null) {
            var2.responseTime = par1Packet201PlayerInfo.ping;
        }
    }
    
    @Override
    public void handleKeepAlive(final Packet0KeepAlive par1Packet0KeepAlive) {
        this.addToSendQueue(new Packet0KeepAlive(par1Packet0KeepAlive.randomId));
    }
    
    @Override
    public void handlePlayerAbilities(final Packet202PlayerAbilities par1Packet202PlayerAbilities) {
        final EntityClientPlayerMP var2 = Minecraft.thePlayer;
        var2.capabilities.isFlying = par1Packet202PlayerAbilities.getFlying();
        var2.capabilities.isCreativeMode = par1Packet202PlayerAbilities.isCreativeMode();
        var2.capabilities.disableDamage = par1Packet202PlayerAbilities.getDisableDamage();
        var2.capabilities.allowFlying = par1Packet202PlayerAbilities.getAllowFlying();
        var2.capabilities.setFlySpeed(par1Packet202PlayerAbilities.getFlySpeed());
        var2.capabilities.setPlayerWalkSpeed(par1Packet202PlayerAbilities.getWalkSpeed());
    }
    
    @Override
    public void handleAutoComplete(final Packet203AutoComplete par1Packet203AutoComplete) {
        final String[] var2 = par1Packet203AutoComplete.getText().split("\u0000");
        if (Minecraft.currentScreen instanceof GuiChat) {
            final GuiChat var3 = (GuiChat)Minecraft.currentScreen;
            var3.func_73894_a(var2);
        }
    }
    
    @Override
    public void handleLevelSound(final Packet62LevelSound par1Packet62LevelSound) {
        Minecraft.theWorld.playSound(par1Packet62LevelSound.getEffectX(), par1Packet62LevelSound.getEffectY(), par1Packet62LevelSound.getEffectZ(), par1Packet62LevelSound.getSoundName(), par1Packet62LevelSound.getVolume(), par1Packet62LevelSound.getPitch(), false);
    }
    
    @Override
    public void handleCustomPayload(final Packet250CustomPayload par1Packet250CustomPayload) {
        if ("MC|TPack".equals(par1Packet250CustomPayload.channel)) {
            final String[] var2 = new String(par1Packet250CustomPayload.data).split("\u0000");
            final String var3 = var2[0];
            if (var2[1].equals("16")) {
                if (this.mc.texturePackList.getAcceptsTextures()) {
                    this.mc.texturePackList.requestDownloadOfTexture(var3);
                }
                else if (this.mc.texturePackList.func_77300_f()) {
                    this.mc.displayGuiScreen(new GuiYesNo(new NetClientWebTextures(this, var3), StringTranslate.getInstance().translateKey("multiplayer.texturePrompt.line1"), StringTranslate.getInstance().translateKey("multiplayer.texturePrompt.line2"), 0));
                }
            }
        }
        else if ("MC|TrList".equals(par1Packet250CustomPayload.channel)) {
            final DataInputStream var4 = new DataInputStream(new ByteArrayInputStream(par1Packet250CustomPayload.data));
            try {
                final int var5 = var4.readInt();
                final GuiScreen var6 = Minecraft.currentScreen;
                if (var6 != null && var6 instanceof GuiMerchant && var5 == Minecraft.thePlayer.openContainer.windowId) {
                    final IMerchant var7 = ((GuiMerchant)var6).getIMerchant();
                    final MerchantRecipeList var8 = MerchantRecipeList.readRecipiesFromStream(var4);
                    var7.setRecipes(var8);
                }
            }
            catch (IOException var9) {
                var9.printStackTrace();
            }
        }
    }
    
    @Override
    public void handleSetObjective(final Packet206SetObjective par1Packet206SetObjective) {
        final Scoreboard var2 = this.worldClient.getScoreboard();
        if (par1Packet206SetObjective.change == 0) {
            final ScoreObjective var3 = var2.func_96535_a(par1Packet206SetObjective.objectiveName, ScoreObjectiveCriteria.field_96641_b);
            var3.setDisplayName(par1Packet206SetObjective.objectiveDisplayName);
        }
        else {
            final ScoreObjective var3 = var2.getObjective(par1Packet206SetObjective.objectiveName);
            if (par1Packet206SetObjective.change == 1) {
                var2.func_96519_k(var3);
            }
            else if (par1Packet206SetObjective.change == 2) {
                var3.setDisplayName(par1Packet206SetObjective.objectiveDisplayName);
            }
        }
    }
    
    @Override
    public void handleSetScore(final Packet207SetScore par1Packet207SetScore) {
        final Scoreboard var2 = this.worldClient.getScoreboard();
        final ScoreObjective var3 = var2.getObjective(par1Packet207SetScore.scoreName);
        if (par1Packet207SetScore.updateOrRemove == 0) {
            final Score var4 = var2.func_96529_a(par1Packet207SetScore.itemName, var3);
            var4.func_96647_c(par1Packet207SetScore.value);
        }
        else if (par1Packet207SetScore.updateOrRemove == 1) {
            var2.func_96515_c(par1Packet207SetScore.itemName);
        }
    }
    
    @Override
    public void handleSetDisplayObjective(final Packet208SetDisplayObjective par1Packet208SetDisplayObjective) {
        final Scoreboard var2 = this.worldClient.getScoreboard();
        if (par1Packet208SetDisplayObjective.scoreName.length() == 0) {
            var2.func_96530_a(par1Packet208SetDisplayObjective.scoreboardPosition, null);
        }
        else {
            final ScoreObjective var3 = var2.getObjective(par1Packet208SetDisplayObjective.scoreName);
            var2.func_96530_a(par1Packet208SetDisplayObjective.scoreboardPosition, var3);
        }
    }
    
    @Override
    public void handleSetPlayerTeam(final Packet209SetPlayerTeam par1Packet209SetPlayerTeam) {
        final Scoreboard var2 = this.worldClient.getScoreboard();
        ScorePlayerTeam var3;
        if (par1Packet209SetPlayerTeam.mode == 0) {
            var3 = var2.func_96527_f(par1Packet209SetPlayerTeam.teamName);
        }
        else {
            var3 = var2.func_96508_e(par1Packet209SetPlayerTeam.teamName);
        }
        if (par1Packet209SetPlayerTeam.mode == 0 || par1Packet209SetPlayerTeam.mode == 2) {
            var3.func_96664_a(par1Packet209SetPlayerTeam.teamDisplayName);
            var3.func_96666_b(par1Packet209SetPlayerTeam.teamPrefix);
            var3.func_96662_c(par1Packet209SetPlayerTeam.teamSuffix);
            var3.func_98298_a(par1Packet209SetPlayerTeam.friendlyFire);
        }
        if (par1Packet209SetPlayerTeam.mode == 0 || par1Packet209SetPlayerTeam.mode == 3) {
            for (final String var5 : par1Packet209SetPlayerTeam.playerNames) {
                var2.func_96521_a(var5, var3);
            }
        }
        if (par1Packet209SetPlayerTeam.mode == 4) {
            for (final String var5 : par1Packet209SetPlayerTeam.playerNames) {
                var2.removePlayerFromTeam(var5, var3);
            }
        }
        if (par1Packet209SetPlayerTeam.mode == 1) {
            var2.func_96511_d(var3);
        }
    }
    
    @Override
    public void handleWorldParticles(final Packet63WorldParticles par1Packet63WorldParticles) {
        for (int var2 = 0; var2 < par1Packet63WorldParticles.getQuantity(); ++var2) {
            final double var3 = this.rand.nextGaussian() * par1Packet63WorldParticles.getOffsetX();
            final double var4 = this.rand.nextGaussian() * par1Packet63WorldParticles.getOffsetY();
            final double var5 = this.rand.nextGaussian() * par1Packet63WorldParticles.getOffsetZ();
            final double var6 = this.rand.nextGaussian() * par1Packet63WorldParticles.getSpeed();
            final double var7 = this.rand.nextGaussian() * par1Packet63WorldParticles.getSpeed();
            final double var8 = this.rand.nextGaussian() * par1Packet63WorldParticles.getSpeed();
            this.worldClient.spawnParticle(par1Packet63WorldParticles.getParticleName(), par1Packet63WorldParticles.getPositionX() + var3, par1Packet63WorldParticles.getPositionY() + var4, par1Packet63WorldParticles.getPositionZ() + var5, var6, var7, var8);
        }
    }
    
    public INetworkManager getNetManager() {
        return this.netManager;
    }
}
