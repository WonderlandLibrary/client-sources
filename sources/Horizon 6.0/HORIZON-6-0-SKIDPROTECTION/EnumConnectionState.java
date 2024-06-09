package HORIZON-6-0-SKIDPROTECTION;

import org.apache.logging.log4j.LogManager;
import com.google.common.collect.HashBiMap;
import java.util.Iterator;
import com.google.common.collect.BiMap;
import com.google.common.collect.Maps;
import gnu.trove.map.hash.TIntObjectHashMap;
import java.util.Map;
import gnu.trove.map.TIntObjectMap;

public enum EnumConnectionState
{
    HorizonCode_Horizon_È(0, "HANDSHAKING", 0, -1, (Object)null) {
        private static final String Âµá€ = "CL_00001246";
        
        {
            this.HorizonCode_Horizon_È(EnumPacketDirection.HorizonCode_Horizon_È, C00Handshake.class);
        }
    }, 
    Â(1, "PLAY", 1, 0, (Object)null) {
        private static final String Âµá€ = "CL_00001250";
        
        {
            this.HorizonCode_Horizon_È(EnumPacketDirection.Â, S00PacketKeepAlive.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.Â, S01PacketJoinGame.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.Â, S02PacketChat.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.Â, S03PacketTimeUpdate.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.Â, S04PacketEntityEquipment.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.Â, S05PacketSpawnPosition.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.Â, S06PacketUpdateHealth.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.Â, S07PacketRespawn.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.Â, S08PacketPlayerPosLook.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.Â, S09PacketHeldItemChange.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.Â, S0APacketUseBed.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.Â, S0BPacketAnimation.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.Â, S0CPacketSpawnPlayer.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.Â, S0DPacketCollectItem.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.Â, S0EPacketSpawnObject.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.Â, S0FPacketSpawnMob.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.Â, S10PacketSpawnPainting.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.Â, S11PacketSpawnExperienceOrb.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.Â, S12PacketEntityVelocity.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.Â, S13PacketDestroyEntities.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.Â, S14PacketEntity.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.Â, S14PacketEntity.HorizonCode_Horizon_È.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.Â, S14PacketEntity.Â.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.Â, S14PacketEntity.Ý.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.Â, S18PacketEntityTeleport.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.Â, S19PacketEntityHeadLook.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.Â, S19PacketEntityStatus.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.Â, S1BPacketEntityAttach.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.Â, S1CPacketEntityMetadata.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.Â, S1DPacketEntityEffect.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.Â, S1EPacketRemoveEntityEffect.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.Â, S1FPacketSetExperience.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.Â, S20PacketEntityProperties.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.Â, S21PacketChunkData.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.Â, S22PacketMultiBlockChange.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.Â, S23PacketBlockChange.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.Â, S24PacketBlockAction.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.Â, S25PacketBlockBreakAnim.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.Â, S26PacketMapChunkBulk.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.Â, S27PacketExplosion.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.Â, S28PacketEffect.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.Â, S29PacketSoundEffect.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.Â, S2APacketParticles.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.Â, S2BPacketChangeGameState.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.Â, S2CPacketSpawnGlobalEntity.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.Â, S2DPacketOpenWindow.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.Â, S2EPacketCloseWindow.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.Â, S2FPacketSetSlot.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.Â, S30PacketWindowItems.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.Â, S31PacketWindowProperty.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.Â, S32PacketConfirmTransaction.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.Â, S33PacketUpdateSign.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.Â, S34PacketMaps.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.Â, S35PacketUpdateTileEntity.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.Â, S36PacketSignEditorOpen.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.Â, S37PacketStatistics.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.Â, S38PacketPlayerListItem.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.Â, S39PacketPlayerAbilities.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.Â, S3APacketTabComplete.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.Â, S3BPacketScoreboardObjective.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.Â, S3CPacketUpdateScore.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.Â, S3DPacketDisplayScoreboard.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.Â, S3EPacketTeams.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.Â, S3FPacketCustomPayload.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.Â, S40PacketDisconnect.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.Â, S41PacketServerDifficulty.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.Â, S42PacketCombatEvent.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.Â, S43PacketCamera.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.Â, S44PacketWorldBorder.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.Â, S45PacketTitle.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.Â, S46PacketSetCompressionLevel.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.Â, S47PacketPlayerListHeaderFooter.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.Â, S48PacketResourcePackSend.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.Â, S49PacketUpdateEntityNBT.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.HorizonCode_Horizon_È, C00PacketKeepAlive.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.HorizonCode_Horizon_È, C01PacketChatMessage.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.HorizonCode_Horizon_È, C02PacketUseEntity.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.HorizonCode_Horizon_È, C03PacketPlayer.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.HorizonCode_Horizon_È, C03PacketPlayer.HorizonCode_Horizon_È.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.HorizonCode_Horizon_È, C03PacketPlayer.Â.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.HorizonCode_Horizon_È, C03PacketPlayer.Ý.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.HorizonCode_Horizon_È, C07PacketPlayerDigging.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.HorizonCode_Horizon_È, C08PacketPlayerBlockPlacement.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.HorizonCode_Horizon_È, C09PacketHeldItemChange.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.HorizonCode_Horizon_È, C0APacketAnimation.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.HorizonCode_Horizon_È, C0BPacketEntityAction.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.HorizonCode_Horizon_È, C0CPacketInput.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.HorizonCode_Horizon_È, C0DPacketCloseWindow.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.HorizonCode_Horizon_È, C0EPacketClickWindow.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.HorizonCode_Horizon_È, C0FPacketConfirmTransaction.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.HorizonCode_Horizon_È, C10PacketCreativeInventoryAction.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.HorizonCode_Horizon_È, C11PacketEnchantItem.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.HorizonCode_Horizon_È, C12PacketUpdateSign.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.HorizonCode_Horizon_È, C13PacketPlayerAbilities.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.HorizonCode_Horizon_È, C14PacketTabComplete.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.HorizonCode_Horizon_È, C15PacketClientSettings.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.HorizonCode_Horizon_È, C16PacketClientStatus.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.HorizonCode_Horizon_È, C17PacketCustomPayload.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.HorizonCode_Horizon_È, C18PacketSpectate.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.HorizonCode_Horizon_È, C19PacketResourcePackStatus.class);
        }
    }, 
    Ý(2, "STATUS", 2, 1, (Object)null) {
        private static final String Âµá€ = "CL_00001247";
        
        {
            this.HorizonCode_Horizon_È(EnumPacketDirection.HorizonCode_Horizon_È, C00PacketServerQuery.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.Â, S00PacketServerInfo.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.HorizonCode_Horizon_È, C01PacketPing.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.Â, S01PacketPong.class);
        }
    }, 
    Ø­áŒŠá(3, "LOGIN", 3, 2, (Object)null) {
        private static final String Âµá€ = "CL_00001249";
        
        {
            this.HorizonCode_Horizon_È(EnumPacketDirection.Â, S00PacketDisconnect.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.Â, S01PacketEncryptionRequest.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.Â, S02PacketLoginSuccess.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.Â, S03PacketEnableCompression.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.HorizonCode_Horizon_È, C00PacketLoginStart.class);
            this.HorizonCode_Horizon_È(EnumPacketDirection.HorizonCode_Horizon_È, C01PacketEncryptionResponse.class);
        }
    };
    
    private static final TIntObjectMap Âµá€;
    private static final Map Ó;
    private final int à;
    private final Map Ø;
    private static final EnumConnectionState[] áŒŠÆ;
    private static final String áˆºÑ¢Õ = "CL_00001245";
    
    static {
        ÂµÈ = new EnumConnectionState[] { EnumConnectionState.HorizonCode_Horizon_È, EnumConnectionState.Â, EnumConnectionState.Ý, EnumConnectionState.Ø­áŒŠá };
        Âµá€ = (TIntObjectMap)new TIntObjectHashMap();
        Ó = Maps.newHashMap();
        áŒŠÆ = new EnumConnectionState[] { EnumConnectionState.HorizonCode_Horizon_È, EnumConnectionState.Â, EnumConnectionState.Ý, EnumConnectionState.Ø­áŒŠá };
        for (final EnumConnectionState var4 : values()) {
            EnumConnectionState.Âµá€.put(var4.HorizonCode_Horizon_È(), (Object)var4);
            for (final EnumPacketDirection var6 : var4.Ø.keySet()) {
                for (final Class var8 : var4.Ø.get(var6).values()) {
                    if (EnumConnectionState.Ó.containsKey(var8) && EnumConnectionState.Ó.get(var8) != var4) {
                        throw new Error("Packet " + var8 + " is already assigned to protocol " + EnumConnectionState.Ó.get(var8) + " - can't reassign to " + var4);
                    }
                    try {
                        var8.newInstance();
                    }
                    catch (Throwable var9) {
                        throw new Error("Packet " + var8 + " fails instantiation checks! " + var8);
                    }
                    EnumConnectionState.Ó.put(var8, var4);
                }
            }
        }
    }
    
    private EnumConnectionState(final String s, final int n, final String p_i45152_1_, final int p_i45152_2_, final int protocolId) {
        this.Ø = Maps.newEnumMap((Class)EnumPacketDirection.class);
        this.à = protocolId;
    }
    
    protected EnumConnectionState HorizonCode_Horizon_È(final EnumPacketDirection direction, final Class packetClass) {
        Object var3 = this.Ø.get(direction);
        if (var3 == null) {
            var3 = HashBiMap.create();
            this.Ø.put(direction, var3);
        }
        if (((BiMap)var3).containsValue((Object)packetClass)) {
            final String var4 = direction + " packet " + packetClass + " is already known to ID " + ((BiMap)var3).inverse().get((Object)packetClass);
            LogManager.getLogger().fatal(var4);
            throw new IllegalArgumentException(var4);
        }
        ((BiMap)var3).put((Object)((BiMap)var3).size(), (Object)packetClass);
        return this;
    }
    
    public Integer HorizonCode_Horizon_È(final EnumPacketDirection direction, final Packet packetIn) {
        return (Integer)this.Ø.get(direction).inverse().get((Object)packetIn.getClass());
    }
    
    public Packet HorizonCode_Horizon_È(final EnumPacketDirection direction, final int packetId) throws InstantiationException, IllegalAccessException {
        final Class var3 = (Class)this.Ø.get(direction).get((Object)packetId);
        return (var3 == null) ? null : var3.newInstance();
    }
    
    public int HorizonCode_Horizon_È() {
        return this.à;
    }
    
    public static EnumConnectionState HorizonCode_Horizon_È(final int stateId) {
        return (EnumConnectionState)EnumConnectionState.Âµá€.get(stateId);
    }
    
    public static EnumConnectionState HorizonCode_Horizon_È(final Packet packetIn) {
        return EnumConnectionState.Ó.get(packetIn.getClass());
    }
    
    private EnumConnectionState(final String s, final int n, final String p_i46000_1_, final int p_i46000_2_, final int p_i46000_3_, final Object p_i46000_4_) {
        this(s, n, p_i46000_1_, p_i46000_2_, p_i46000_3_);
    }
}
