package net.minecraft.network;

import net.minecraft.network.handshake.client.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.status.client.*;
import net.minecraft.network.status.server.*;
import net.minecraft.network.login.server.*;
import net.minecraft.network.login.client.*;
import java.util.*;
import com.google.common.collect.*;
import org.apache.logging.log4j.*;

public enum EnumConnectionState
{
    LOGIN("   ".length(), "  ".length()) {
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
                if (2 < 1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        {
            this.registerPacket(EnumPacketDirection.CLIENTBOUND, S00PacketDisconnect.class);
            this.registerPacket(EnumPacketDirection.CLIENTBOUND, S01PacketEncryptionRequest.class);
            this.registerPacket(EnumPacketDirection.CLIENTBOUND, S02PacketLoginSuccess.class);
            this.registerPacket(EnumPacketDirection.CLIENTBOUND, S03PacketEnableCompression.class);
            this.registerPacket(EnumPacketDirection.SERVERBOUND, C00PacketLoginStart.class);
            this.registerPacket(EnumPacketDirection.SERVERBOUND, C01PacketEncryptionResponse.class);
        }
    };
    
    private final Map<EnumPacketDirection, BiMap<Integer, Class<? extends Packet>>> directionMaps;
    private final int id;
    private static int field_181137_f;
    
    PLAY(" ".length(), "".length()) {
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
                if (4 == -1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        {
            this.registerPacket(EnumPacketDirection.CLIENTBOUND, S00PacketKeepAlive.class);
            this.registerPacket(EnumPacketDirection.CLIENTBOUND, S01PacketJoinGame.class);
            this.registerPacket(EnumPacketDirection.CLIENTBOUND, S02PacketChat.class);
            this.registerPacket(EnumPacketDirection.CLIENTBOUND, S03PacketTimeUpdate.class);
            this.registerPacket(EnumPacketDirection.CLIENTBOUND, S04PacketEntityEquipment.class);
            this.registerPacket(EnumPacketDirection.CLIENTBOUND, S05PacketSpawnPosition.class);
            this.registerPacket(EnumPacketDirection.CLIENTBOUND, S06PacketUpdateHealth.class);
            this.registerPacket(EnumPacketDirection.CLIENTBOUND, S07PacketRespawn.class);
            this.registerPacket(EnumPacketDirection.CLIENTBOUND, S08PacketPlayerPosLook.class);
            this.registerPacket(EnumPacketDirection.CLIENTBOUND, S09PacketHeldItemChange.class);
            this.registerPacket(EnumPacketDirection.CLIENTBOUND, S0APacketUseBed.class);
            this.registerPacket(EnumPacketDirection.CLIENTBOUND, S0BPacketAnimation.class);
            this.registerPacket(EnumPacketDirection.CLIENTBOUND, S0CPacketSpawnPlayer.class);
            this.registerPacket(EnumPacketDirection.CLIENTBOUND, S0DPacketCollectItem.class);
            this.registerPacket(EnumPacketDirection.CLIENTBOUND, S0EPacketSpawnObject.class);
            this.registerPacket(EnumPacketDirection.CLIENTBOUND, S0FPacketSpawnMob.class);
            this.registerPacket(EnumPacketDirection.CLIENTBOUND, S10PacketSpawnPainting.class);
            this.registerPacket(EnumPacketDirection.CLIENTBOUND, S11PacketSpawnExperienceOrb.class);
            this.registerPacket(EnumPacketDirection.CLIENTBOUND, S12PacketEntityVelocity.class);
            this.registerPacket(EnumPacketDirection.CLIENTBOUND, S13PacketDestroyEntities.class);
            this.registerPacket(EnumPacketDirection.CLIENTBOUND, S14PacketEntity.class);
            this.registerPacket(EnumPacketDirection.CLIENTBOUND, S14PacketEntity.S15PacketEntityRelMove.class);
            this.registerPacket(EnumPacketDirection.CLIENTBOUND, S14PacketEntity.S16PacketEntityLook.class);
            this.registerPacket(EnumPacketDirection.CLIENTBOUND, S14PacketEntity.S17PacketEntityLookMove.class);
            this.registerPacket(EnumPacketDirection.CLIENTBOUND, S18PacketEntityTeleport.class);
            this.registerPacket(EnumPacketDirection.CLIENTBOUND, S19PacketEntityHeadLook.class);
            this.registerPacket(EnumPacketDirection.CLIENTBOUND, S19PacketEntityStatus.class);
            this.registerPacket(EnumPacketDirection.CLIENTBOUND, S1BPacketEntityAttach.class);
            this.registerPacket(EnumPacketDirection.CLIENTBOUND, S1CPacketEntityMetadata.class);
            this.registerPacket(EnumPacketDirection.CLIENTBOUND, S1DPacketEntityEffect.class);
            this.registerPacket(EnumPacketDirection.CLIENTBOUND, S1EPacketRemoveEntityEffect.class);
            this.registerPacket(EnumPacketDirection.CLIENTBOUND, S1FPacketSetExperience.class);
            this.registerPacket(EnumPacketDirection.CLIENTBOUND, S20PacketEntityProperties.class);
            this.registerPacket(EnumPacketDirection.CLIENTBOUND, S21PacketChunkData.class);
            this.registerPacket(EnumPacketDirection.CLIENTBOUND, S22PacketMultiBlockChange.class);
            this.registerPacket(EnumPacketDirection.CLIENTBOUND, S23PacketBlockChange.class);
            this.registerPacket(EnumPacketDirection.CLIENTBOUND, S24PacketBlockAction.class);
            this.registerPacket(EnumPacketDirection.CLIENTBOUND, S25PacketBlockBreakAnim.class);
            this.registerPacket(EnumPacketDirection.CLIENTBOUND, S26PacketMapChunkBulk.class);
            this.registerPacket(EnumPacketDirection.CLIENTBOUND, S27PacketExplosion.class);
            this.registerPacket(EnumPacketDirection.CLIENTBOUND, S28PacketEffect.class);
            this.registerPacket(EnumPacketDirection.CLIENTBOUND, S29PacketSoundEffect.class);
            this.registerPacket(EnumPacketDirection.CLIENTBOUND, S2APacketParticles.class);
            this.registerPacket(EnumPacketDirection.CLIENTBOUND, S2BPacketChangeGameState.class);
            this.registerPacket(EnumPacketDirection.CLIENTBOUND, S2CPacketSpawnGlobalEntity.class);
            this.registerPacket(EnumPacketDirection.CLIENTBOUND, S2DPacketOpenWindow.class);
            this.registerPacket(EnumPacketDirection.CLIENTBOUND, S2EPacketCloseWindow.class);
            this.registerPacket(EnumPacketDirection.CLIENTBOUND, S2FPacketSetSlot.class);
            this.registerPacket(EnumPacketDirection.CLIENTBOUND, S30PacketWindowItems.class);
            this.registerPacket(EnumPacketDirection.CLIENTBOUND, S31PacketWindowProperty.class);
            this.registerPacket(EnumPacketDirection.CLIENTBOUND, S32PacketConfirmTransaction.class);
            this.registerPacket(EnumPacketDirection.CLIENTBOUND, S33PacketUpdateSign.class);
            this.registerPacket(EnumPacketDirection.CLIENTBOUND, S34PacketMaps.class);
            this.registerPacket(EnumPacketDirection.CLIENTBOUND, S35PacketUpdateTileEntity.class);
            this.registerPacket(EnumPacketDirection.CLIENTBOUND, S36PacketSignEditorOpen.class);
            this.registerPacket(EnumPacketDirection.CLIENTBOUND, S37PacketStatistics.class);
            this.registerPacket(EnumPacketDirection.CLIENTBOUND, S38PacketPlayerListItem.class);
            this.registerPacket(EnumPacketDirection.CLIENTBOUND, S39PacketPlayerAbilities.class);
            this.registerPacket(EnumPacketDirection.CLIENTBOUND, S3APacketTabComplete.class);
            this.registerPacket(EnumPacketDirection.CLIENTBOUND, S3BPacketScoreboardObjective.class);
            this.registerPacket(EnumPacketDirection.CLIENTBOUND, S3CPacketUpdateScore.class);
            this.registerPacket(EnumPacketDirection.CLIENTBOUND, S3DPacketDisplayScoreboard.class);
            this.registerPacket(EnumPacketDirection.CLIENTBOUND, S3EPacketTeams.class);
            this.registerPacket(EnumPacketDirection.CLIENTBOUND, S3FPacketCustomPayload.class);
            this.registerPacket(EnumPacketDirection.CLIENTBOUND, S40PacketDisconnect.class);
            this.registerPacket(EnumPacketDirection.CLIENTBOUND, S41PacketServerDifficulty.class);
            this.registerPacket(EnumPacketDirection.CLIENTBOUND, S42PacketCombatEvent.class);
            this.registerPacket(EnumPacketDirection.CLIENTBOUND, S43PacketCamera.class);
            this.registerPacket(EnumPacketDirection.CLIENTBOUND, S44PacketWorldBorder.class);
            this.registerPacket(EnumPacketDirection.CLIENTBOUND, S45PacketTitle.class);
            this.registerPacket(EnumPacketDirection.CLIENTBOUND, S46PacketSetCompressionLevel.class);
            this.registerPacket(EnumPacketDirection.CLIENTBOUND, S47PacketPlayerListHeaderFooter.class);
            this.registerPacket(EnumPacketDirection.CLIENTBOUND, S48PacketResourcePackSend.class);
            this.registerPacket(EnumPacketDirection.CLIENTBOUND, S49PacketUpdateEntityNBT.class);
            this.registerPacket(EnumPacketDirection.SERVERBOUND, C00PacketKeepAlive.class);
            this.registerPacket(EnumPacketDirection.SERVERBOUND, C01PacketChatMessage.class);
            this.registerPacket(EnumPacketDirection.SERVERBOUND, C02PacketUseEntity.class);
            this.registerPacket(EnumPacketDirection.SERVERBOUND, C03PacketPlayer.class);
            this.registerPacket(EnumPacketDirection.SERVERBOUND, C03PacketPlayer.C04PacketPlayerPosition.class);
            this.registerPacket(EnumPacketDirection.SERVERBOUND, C03PacketPlayer.C05PacketPlayerLook.class);
            this.registerPacket(EnumPacketDirection.SERVERBOUND, C03PacketPlayer.C06PacketPlayerPosLook.class);
            this.registerPacket(EnumPacketDirection.SERVERBOUND, C07PacketPlayerDigging.class);
            this.registerPacket(EnumPacketDirection.SERVERBOUND, C08PacketPlayerBlockPlacement.class);
            this.registerPacket(EnumPacketDirection.SERVERBOUND, C09PacketHeldItemChange.class);
            this.registerPacket(EnumPacketDirection.SERVERBOUND, C0APacketAnimation.class);
            this.registerPacket(EnumPacketDirection.SERVERBOUND, C0BPacketEntityAction.class);
            this.registerPacket(EnumPacketDirection.SERVERBOUND, C0CPacketInput.class);
            this.registerPacket(EnumPacketDirection.SERVERBOUND, C0DPacketCloseWindow.class);
            this.registerPacket(EnumPacketDirection.SERVERBOUND, C0EPacketClickWindow.class);
            this.registerPacket(EnumPacketDirection.SERVERBOUND, C0FPacketConfirmTransaction.class);
            this.registerPacket(EnumPacketDirection.SERVERBOUND, C10PacketCreativeInventoryAction.class);
            this.registerPacket(EnumPacketDirection.SERVERBOUND, C11PacketEnchantItem.class);
            this.registerPacket(EnumPacketDirection.SERVERBOUND, C12PacketUpdateSign.class);
            this.registerPacket(EnumPacketDirection.SERVERBOUND, C13PacketPlayerAbilities.class);
            this.registerPacket(EnumPacketDirection.SERVERBOUND, C14PacketTabComplete.class);
            this.registerPacket(EnumPacketDirection.SERVERBOUND, C15PacketClientSettings.class);
            this.registerPacket(EnumPacketDirection.SERVERBOUND, C16PacketClientStatus.class);
            this.registerPacket(EnumPacketDirection.SERVERBOUND, C17PacketCustomPayload.class);
            this.registerPacket(EnumPacketDirection.SERVERBOUND, C18PacketSpectate.class);
            this.registerPacket(EnumPacketDirection.SERVERBOUND, C19PacketResourcePackStatus.class);
        }
    };
    
    private static final EnumConnectionState[] STATES_BY_ID;
    private static final Map<Class<? extends Packet>, EnumConnectionState> STATES_BY_CLASS;
    
    STATUS("  ".length(), " ".length()) {
        {
            this.registerPacket(EnumPacketDirection.SERVERBOUND, C00PacketServerQuery.class);
            this.registerPacket(EnumPacketDirection.CLIENTBOUND, S00PacketServerInfo.class);
            this.registerPacket(EnumPacketDirection.SERVERBOUND, C01PacketPing.class);
            this.registerPacket(EnumPacketDirection.CLIENTBOUND, S01PacketPong.class);
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
                if (1 >= 4) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }, 
    HANDSHAKING("".length(), -" ".length()) {
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
        
        {
            this.registerPacket(EnumPacketDirection.SERVERBOUND, C00Handshake.class);
        }
    };
    
    private static final EnumConnectionState[] ENUM$VALUES;
    private static int field_181136_e;
    private static final String[] I;
    
    EnumConnectionState(final String s, final int n, final int n2, final EnumConnectionState enumConnectionState) {
        this(s, n, n2);
    }
    
    static {
        I();
        final EnumConnectionState[] enum$VALUES = new EnumConnectionState[0x4A ^ 0x4E];
        enum$VALUES["".length()] = EnumConnectionState.HANDSHAKING;
        enum$VALUES[" ".length()] = EnumConnectionState.PLAY;
        enum$VALUES["  ".length()] = EnumConnectionState.STATUS;
        enum$VALUES["   ".length()] = EnumConnectionState.LOGIN;
        ENUM$VALUES = enum$VALUES;
        EnumConnectionState.field_181136_e = -" ".length();
        EnumConnectionState.field_181137_f = "  ".length();
        STATES_BY_ID = new EnumConnectionState[EnumConnectionState.field_181137_f - EnumConnectionState.field_181136_e + " ".length()];
        STATES_BY_CLASS = Maps.newHashMap();
        final EnumConnectionState[] values;
        final int length = (values = values()).length;
        int i = "".length();
        "".length();
        if (4 != 4) {
            throw null;
        }
        while (i < length) {
            final EnumConnectionState enumConnectionState = values[i];
            final int id = enumConnectionState.getId();
            if (id < EnumConnectionState.field_181136_e || id > EnumConnectionState.field_181137_f) {
                throw new Error(EnumConnectionState.I[0x6F ^ 0x6B] + Integer.toString(id));
            }
            EnumConnectionState.STATES_BY_ID[id - EnumConnectionState.field_181136_e] = enumConnectionState;
            final Iterator<EnumPacketDirection> iterator = enumConnectionState.directionMaps.keySet().iterator();
            "".length();
            if (-1 == 0) {
                throw null;
            }
            while (iterator.hasNext()) {
                final Iterator iterator2 = enumConnectionState.directionMaps.get(iterator.next()).values().iterator();
                "".length();
                if (1 >= 2) {
                    throw null;
                }
                while (iterator2.hasNext()) {
                    final Class<? extends Packet> clazz = iterator2.next();
                    if (EnumConnectionState.STATES_BY_CLASS.containsKey(clazz) && EnumConnectionState.STATES_BY_CLASS.get(clazz) != enumConnectionState) {
                        throw new Error(EnumConnectionState.I[0xA5 ^ 0xA0] + clazz + EnumConnectionState.I[0x94 ^ 0x92] + EnumConnectionState.STATES_BY_CLASS.get(clazz) + EnumConnectionState.I[0xB3 ^ 0xB4] + enumConnectionState);
                    }
                    try {
                        clazz.newInstance();
                        "".length();
                        if (4 < 3) {
                            throw null;
                        }
                    }
                    catch (Throwable t) {
                        throw new Error(EnumConnectionState.I[0x6C ^ 0x64] + clazz + EnumConnectionState.I[0x3C ^ 0x35] + clazz);
                    }
                    EnumConnectionState.STATES_BY_CLASS.put(clazz, enumConnectionState);
                }
            }
            ++i;
        }
    }
    
    public Integer getPacketId(final EnumPacketDirection enumPacketDirection, final Packet packet) {
        return (Integer)this.directionMaps.get(enumPacketDirection).inverse().get((Object)packet.getClass());
    }
    
    protected EnumConnectionState registerPacket(final EnumPacketDirection enumPacketDirection, final Class<? extends Packet> clazz) {
        Object create = this.directionMaps.get(enumPacketDirection);
        if (create == null) {
            create = HashBiMap.create();
            this.directionMaps.put(enumPacketDirection, (BiMap<Integer, Class<? extends Packet>>)create);
        }
        if (((BiMap)create).containsValue((Object)clazz)) {
            final String string = enumPacketDirection + EnumConnectionState.I[0x32 ^ 0x38] + clazz + EnumConnectionState.I[0x9C ^ 0x97] + ((BiMap)create).inverse().get((Object)clazz);
            LogManager.getLogger().fatal(string);
            throw new IllegalArgumentException(string);
        }
        ((BiMap)create).put((Object)((BiMap)create).size(), (Object)clazz);
        return this;
    }
    
    public static EnumConnectionState getFromPacket(final Packet packet) {
        return EnumConnectionState.STATES_BY_CLASS.get(packet.getClass());
    }
    
    private EnumConnectionState(final String s, final int n, final int id) {
        this.directionMaps = (Map<EnumPacketDirection, BiMap<Integer, Class<? extends Packet>>>)Maps.newEnumMap((Class)EnumPacketDirection.class);
        this.id = id;
    }
    
    public int getId() {
        return this.id;
    }
    
    public static EnumConnectionState getById(final int n) {
        EnumConnectionState enumConnectionState;
        if (n >= EnumConnectionState.field_181136_e && n <= EnumConnectionState.field_181137_f) {
            enumConnectionState = EnumConnectionState.STATES_BY_ID[n - EnumConnectionState.field_181136_e];
            "".length();
            if (0 >= 3) {
                throw null;
            }
        }
        else {
            enumConnectionState = null;
        }
        return enumConnectionState;
    }
    
    private static void I() {
        (I = new String[0x43 ^ 0x4F])["".length()] = I("?\u0012\u0000\u000b>?\u0012\u0005\u0006#0", "wSNOm");
        EnumConnectionState.I[" ".length()] = I("\u0014?\u0012*", "DsSsF");
        EnumConnectionState.I["  ".length()] = I("\u0016\u001c\u001684\u0016", "EHWla");
        EnumConnectionState.I["   ".length()] = I("\"(?\"\u001a", "ngxkT");
        EnumConnectionState.I[0x50 ^ 0x54] = I("\u0005\u001f#5\u000e%\u0015u$\u0010#\u0005:7\r Q\u001c\u0010B", "LqUTb");
        EnumConnectionState.I[0xC4 ^ 0xC1] = I("\u0012\r\u0012\u0006\u00006L", "Blqme");
        EnumConnectionState.I[0x64 ^ 0x62] = I("P\r\u0004H\u0005\u001c\u0016\u0012\t\u0000\tD\u0016\u001b\u0017\u0019\u0003\u0019\r\u0000P\u0010\u0018H\u0014\u0002\u000b\u0003\u0007\u0007\u001f\bW", "pdwhd");
        EnumConnectionState.I[0x17 ^ 0x10] = I("tyA\u0010,:s\u0015S?15\u0012\u0000$3:A\u0007\"t", "TTasM");
        EnumConnectionState.I[0x6B ^ 0x63] = I("\u001f\u000e6#\t;O", "OoUHl");
        EnumConnectionState.I[0x87 ^ 0x8E] = I("t#;\u00015'e3\u0006* $4\u001c0513\u00077t&2\r:?6{H", "TEZhY");
        EnumConnectionState.I[0xB ^ 0x1] = I("J\u001b\"\u0006#\u000f\u001fc", "jkCeH");
        EnumConnectionState.I[0x4F ^ 0x44] = I("D\"$W\u001b\b92\u0016\u001e\u001dk<\u0019\u0015\u0013%w\u0003\u0015D\u0002\u0013W", "dKWwz");
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
            if (2 != 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public Packet getPacket(final EnumPacketDirection enumPacketDirection, final int n) throws InstantiationException, IllegalAccessException {
        final Class clazz = (Class)this.directionMaps.get(enumPacketDirection).get((Object)n);
        Packet packet;
        if (clazz == null) {
            packet = null;
            "".length();
            if (2 < 0) {
                throw null;
            }
        }
        else {
            packet = clazz.newInstance();
        }
        return packet;
    }
}
