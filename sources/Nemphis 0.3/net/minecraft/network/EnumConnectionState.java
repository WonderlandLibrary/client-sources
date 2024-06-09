/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.BiMap
 *  com.google.common.collect.HashBiMap
 *  com.google.common.collect.Maps
 *  gnu.trove.map.TIntObjectMap
 *  gnu.trove.map.hash.TIntObjectHashMap
 *  org.apache.logging.log4j.LogManager
 */
package net.minecraft.network;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import java.util.Map;
import java.util.Set;
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.Packet;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.client.C00PacketLoginStart;
import net.minecraft.network.login.client.C01PacketEncryptionResponse;
import net.minecraft.network.login.server.S00PacketDisconnect;
import net.minecraft.network.login.server.S01PacketEncryptionRequest;
import net.minecraft.network.login.server.S02PacketLoginSuccess;
import net.minecraft.network.login.server.S03PacketEnableCompression;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0CPacketInput;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C0EPacketClickWindow;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.play.client.C10PacketCreativeInventoryAction;
import net.minecraft.network.play.client.C11PacketEnchantItem;
import net.minecraft.network.play.client.C12PacketUpdateSign;
import net.minecraft.network.play.client.C13PacketPlayerAbilities;
import net.minecraft.network.play.client.C14PacketTabComplete;
import net.minecraft.network.play.client.C15PacketClientSettings;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.network.play.client.C18PacketSpectate;
import net.minecraft.network.play.client.C19PacketResourcePackStatus;
import net.minecraft.network.play.server.S00PacketKeepAlive;
import net.minecraft.network.play.server.S01PacketJoinGame;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S03PacketTimeUpdate;
import net.minecraft.network.play.server.S04PacketEntityEquipment;
import net.minecraft.network.play.server.S05PacketSpawnPosition;
import net.minecraft.network.play.server.S06PacketUpdateHealth;
import net.minecraft.network.play.server.S07PacketRespawn;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S09PacketHeldItemChange;
import net.minecraft.network.play.server.S0APacketUseBed;
import net.minecraft.network.play.server.S0BPacketAnimation;
import net.minecraft.network.play.server.S0CPacketSpawnPlayer;
import net.minecraft.network.play.server.S0DPacketCollectItem;
import net.minecraft.network.play.server.S0EPacketSpawnObject;
import net.minecraft.network.play.server.S0FPacketSpawnMob;
import net.minecraft.network.play.server.S10PacketSpawnPainting;
import net.minecraft.network.play.server.S11PacketSpawnExperienceOrb;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S13PacketDestroyEntities;
import net.minecraft.network.play.server.S14PacketEntity;
import net.minecraft.network.play.server.S18PacketEntityTeleport;
import net.minecraft.network.play.server.S19PacketEntityHeadLook;
import net.minecraft.network.play.server.S19PacketEntityStatus;
import net.minecraft.network.play.server.S1BPacketEntityAttach;
import net.minecraft.network.play.server.S1CPacketEntityMetadata;
import net.minecraft.network.play.server.S1DPacketEntityEffect;
import net.minecraft.network.play.server.S1EPacketRemoveEntityEffect;
import net.minecraft.network.play.server.S1FPacketSetExperience;
import net.minecraft.network.play.server.S20PacketEntityProperties;
import net.minecraft.network.play.server.S21PacketChunkData;
import net.minecraft.network.play.server.S22PacketMultiBlockChange;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.network.play.server.S24PacketBlockAction;
import net.minecraft.network.play.server.S25PacketBlockBreakAnim;
import net.minecraft.network.play.server.S26PacketMapChunkBulk;
import net.minecraft.network.play.server.S27PacketExplosion;
import net.minecraft.network.play.server.S28PacketEffect;
import net.minecraft.network.play.server.S29PacketSoundEffect;
import net.minecraft.network.play.server.S2APacketParticles;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.network.play.server.S2CPacketSpawnGlobalEntity;
import net.minecraft.network.play.server.S2DPacketOpenWindow;
import net.minecraft.network.play.server.S2EPacketCloseWindow;
import net.minecraft.network.play.server.S2FPacketSetSlot;
import net.minecraft.network.play.server.S30PacketWindowItems;
import net.minecraft.network.play.server.S31PacketWindowProperty;
import net.minecraft.network.play.server.S32PacketConfirmTransaction;
import net.minecraft.network.play.server.S33PacketUpdateSign;
import net.minecraft.network.play.server.S34PacketMaps;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.network.play.server.S36PacketSignEditorOpen;
import net.minecraft.network.play.server.S37PacketStatistics;
import net.minecraft.network.play.server.S38PacketPlayerListItem;
import net.minecraft.network.play.server.S39PacketPlayerAbilities;
import net.minecraft.network.play.server.S3APacketTabComplete;
import net.minecraft.network.play.server.S3BPacketScoreboardObjective;
import net.minecraft.network.play.server.S3CPacketUpdateScore;
import net.minecraft.network.play.server.S3DPacketDisplayScoreboard;
import net.minecraft.network.play.server.S3EPacketTeams;
import net.minecraft.network.play.server.S3FPacketCustomPayload;
import net.minecraft.network.play.server.S40PacketDisconnect;
import net.minecraft.network.play.server.S41PacketServerDifficulty;
import net.minecraft.network.play.server.S42PacketCombatEvent;
import net.minecraft.network.play.server.S43PacketCamera;
import net.minecraft.network.play.server.S44PacketWorldBorder;
import net.minecraft.network.play.server.S45PacketTitle;
import net.minecraft.network.play.server.S46PacketSetCompressionLevel;
import net.minecraft.network.play.server.S47PacketPlayerListHeaderFooter;
import net.minecraft.network.play.server.S48PacketResourcePackSend;
import net.minecraft.network.play.server.S49PacketUpdateEntityNBT;
import net.minecraft.network.status.client.C00PacketServerQuery;
import net.minecraft.network.status.client.C01PacketPing;
import net.minecraft.network.status.server.S00PacketServerInfo;
import net.minecraft.network.status.server.S01PacketPong;
import org.apache.logging.log4j.LogManager;

public enum EnumConnectionState {
    HANDSHAKING("HANDSHAKING", 0, -1, (Object)null){
        private static final String __OBFID = "CL_00001246";
    }
    ,
    PLAY("PLAY", 1, 0, (Object)null){
        private static final String __OBFID = "CL_00001250";
    }
    ,
    STATUS("STATUS", 2, 1, (Object)null){
        private static final String __OBFID = "CL_00001247";
    }
    ,
    LOGIN("LOGIN", 3, 2, (Object)null){
        private static final String __OBFID = "CL_00001249";
    };
    
    private static final TIntObjectMap STATES_BY_ID;
    private static final Map STATES_BY_CLASS;
    private final int id;
    private final Map directionMaps;
    private static final EnumConnectionState[] $VALUES;
    private static final String __OBFID = "CL_00001245";

    static {
        STATES_BY_ID = new TIntObjectHashMap();
        STATES_BY_CLASS = Maps.newHashMap();
        $VALUES = new EnumConnectionState[]{HANDSHAKING, PLAY, STATUS, LOGIN};
        EnumConnectionState[] var0 = EnumConnectionState.values();
        int var1 = var0.length;
        int var2 = 0;
        while (var2 < var1) {
            EnumConnectionState var3 = var0[var2];
            STATES_BY_ID.put(var3.getId(), (Object)var3);
            for (EnumPacketDirection var5 : var3.directionMaps.keySet()) {
                for (Class var7 : ((BiMap)var3.directionMaps.get((Object)var5)).values()) {
                    if (STATES_BY_CLASS.containsKey(var7) && STATES_BY_CLASS.get(var7) != var3) {
                        throw new Error("Packet " + var7 + " is already assigned to protocol " + STATES_BY_CLASS.get(var7) + " - can't reassign to " + (Object)((Object)var3));
                    }
                    try {
                        var7.newInstance();
                    }
                    catch (Throwable var9) {
                        throw new Error("Packet " + var7 + " fails instantiation checks! " + var7);
                    }
                    STATES_BY_CLASS.put(var7, var3);
                }
            }
            ++var2;
        }
    }

    private EnumConnectionState(String p_i45152_1_, int p_i45152_2_, String protocolId, int n2, int n3) {
        this.directionMaps = Maps.newEnumMap(EnumPacketDirection.class);
        this.id = protocolId;
    }

    protected EnumConnectionState registerPacket(EnumPacketDirection direction, Class packetClass) {
        BiMap var3 = (BiMap)this.directionMaps.get((Object)direction);
        if (var3 == null) {
            var3 = HashBiMap.create();
            this.directionMaps.put(direction, var3);
        }
        if (var3.containsValue((Object)packetClass)) {
            String var4 = (Object)((Object)direction) + " packet " + packetClass + " is already known to ID " + var3.inverse().get((Object)packetClass);
            LogManager.getLogger().fatal(var4);
            throw new IllegalArgumentException(var4);
        }
        var3.put((Object)var3.size(), (Object)packetClass);
        return this;
    }

    public Integer getPacketId(EnumPacketDirection direction, Packet packetIn) {
        return (Integer)((BiMap)this.directionMaps.get((Object)direction)).inverse().get(packetIn.getClass());
    }

    public Packet getPacket(EnumPacketDirection direction, int packetId) throws InstantiationException, IllegalAccessException {
        Class var3 = (Class)((BiMap)this.directionMaps.get((Object)direction)).get((Object)packetId);
        return var3 == null ? null : (Packet)var3.newInstance();
    }

    public int getId() {
        return this.id;
    }

    public static EnumConnectionState getById(int stateId) {
        return (EnumConnectionState)((Object)STATES_BY_ID.get(stateId));
    }

    public static EnumConnectionState getFromPacket(Packet packetIn) {
        return (EnumConnectionState)((Object)STATES_BY_CLASS.get(packetIn.getClass()));
    }

    private EnumConnectionState(String p_i46000_1_, int p_i46000_2_, String p_i46000_3_, int p_i46000_4_, int n2, Object object) {
        this(string, n, p_i46000_1_, p_i46000_2_, (int)p_i46000_3_);
    }

    /* synthetic */ EnumConnectionState(String string, int n, String string2, int n2, int n3, Object object, EnumConnectionState enumConnectionState) {
        EnumConnectionState enumConnectionState2;
        enumConnectionState2(string, n, string2, n2, n3, object);
    }

}

