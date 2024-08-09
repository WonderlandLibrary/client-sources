package net.minecraft.network;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.client.network.login.IClientLoginNetHandler;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.client.network.status.IClientStatusNetHandler;
import net.minecraft.network.handshake.IHandshakeNetHandler;
import net.minecraft.network.handshake.client.CHandshakePacket;
import net.minecraft.network.login.IServerLoginNetHandler;
import net.minecraft.network.login.client.CCustomPayloadLoginPacket;
import net.minecraft.network.login.client.CEncryptionResponsePacket;
import net.minecraft.network.login.client.CLoginStartPacket;
import net.minecraft.network.login.server.*;
import net.minecraft.network.play.IServerPlayNetHandler;
import net.minecraft.network.play.client.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.status.IServerStatusNetHandler;
import net.minecraft.network.status.client.CPingPacket;
import net.minecraft.network.status.client.CServerQueryPacket;
import net.minecraft.network.status.server.SPongPacket;
import net.minecraft.network.status.server.SServerInfoPacket;
import net.minecraft.util.Util;
import org.apache.logging.log4j.LogManager;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public enum ProtocolType
{
    HANDSHAKING(-1, func_229714_b_().func_229724_a_(PacketDirection.SERVERBOUND, (new ProtocolType.PacketList<IHandshakeNetHandler>()).func_229721_a_(CHandshakePacket.class, CHandshakePacket::new))),
    PLAY(0, func_229714_b_().func_229724_a_(PacketDirection.CLIENTBOUND, (new ProtocolType.PacketList<IClientPlayNetHandler>()).func_229721_a_(SSpawnObjectPacket.class, SSpawnObjectPacket::new).func_229721_a_(SSpawnExperienceOrbPacket.class, SSpawnExperienceOrbPacket::new).func_229721_a_(SSpawnMobPacket.class, SSpawnMobPacket::new).func_229721_a_(SSpawnPaintingPacket.class, SSpawnPaintingPacket::new).func_229721_a_(SSpawnPlayerPacket.class, SSpawnPlayerPacket::new).func_229721_a_(SAnimateHandPacket.class, SAnimateHandPacket::new).func_229721_a_(SStatisticsPacket.class, SStatisticsPacket::new).func_229721_a_(SPlayerDiggingPacket.class, SPlayerDiggingPacket::new).func_229721_a_(SAnimateBlockBreakPacket.class, SAnimateBlockBreakPacket::new).func_229721_a_(SUpdateTileEntityPacket.class, SUpdateTileEntityPacket::new).func_229721_a_(SBlockActionPacket.class, SBlockActionPacket::new).func_229721_a_(SChangeBlockPacket.class, SChangeBlockPacket::new).func_229721_a_(SUpdateBossInfoPacket.class, SUpdateBossInfoPacket::new).func_229721_a_(SServerDifficultyPacket.class, SServerDifficultyPacket::new).func_229721_a_(SChatPacket.class, SChatPacket::new).func_229721_a_(STabCompletePacket.class, STabCompletePacket::new).func_229721_a_(SCommandListPacket.class, SCommandListPacket::new).func_229721_a_(SConfirmTransactionPacket.class, SConfirmTransactionPacket::new).func_229721_a_(SCloseWindowPacket.class, SCloseWindowPacket::new).func_229721_a_(SWindowItemsPacket.class, SWindowItemsPacket::new).func_229721_a_(SWindowPropertyPacket.class, SWindowPropertyPacket::new).func_229721_a_(SSetSlotPacket.class, SSetSlotPacket::new).func_229721_a_(SCooldownPacket.class, SCooldownPacket::new).func_229721_a_(SCustomPayloadPlayPacket.class, SCustomPayloadPlayPacket::new).func_229721_a_(SPlaySoundPacket.class, SPlaySoundPacket::new).func_229721_a_(SDisconnectPacket.class, SDisconnectPacket::new).func_229721_a_(SEntityStatusPacket.class, SEntityStatusPacket::new).func_229721_a_(SExplosionPacket.class, SExplosionPacket::new).func_229721_a_(SUnloadChunkPacket.class, SUnloadChunkPacket::new).func_229721_a_(SChangeGameStatePacket.class, SChangeGameStatePacket::new).func_229721_a_(SOpenHorseWindowPacket.class, SOpenHorseWindowPacket::new).func_229721_a_(SKeepAlivePacket.class, SKeepAlivePacket::new).func_229721_a_(SChunkDataPacket.class, SChunkDataPacket::new).func_229721_a_(SPlaySoundEventPacket.class, SPlaySoundEventPacket::new).func_229721_a_(SSpawnParticlePacket.class, SSpawnParticlePacket::new).func_229721_a_(SUpdateLightPacket.class, SUpdateLightPacket::new).func_229721_a_(SJoinGamePacket.class, SJoinGamePacket::new).func_229721_a_(SMapDataPacket.class, SMapDataPacket::new).func_229721_a_(SMerchantOffersPacket.class, SMerchantOffersPacket::new).func_229721_a_(SEntityPacket.RelativeMovePacket.class, SEntityPacket.RelativeMovePacket::new).func_229721_a_(SEntityPacket.MovePacket.class, SEntityPacket.MovePacket::new).func_229721_a_(SEntityPacket.LookPacket.class, SEntityPacket.LookPacket::new).func_229721_a_(SEntityPacket.class, SEntityPacket::new).func_229721_a_(SMoveVehiclePacket.class, SMoveVehiclePacket::new).func_229721_a_(SOpenBookWindowPacket.class, SOpenBookWindowPacket::new).func_229721_a_(SOpenWindowPacket.class, SOpenWindowPacket::new).func_229721_a_(SOpenSignMenuPacket.class, SOpenSignMenuPacket::new).func_229721_a_(SPlaceGhostRecipePacket.class, SPlaceGhostRecipePacket::new).func_229721_a_(SPlayerAbilitiesPacket.class, SPlayerAbilitiesPacket::new).func_229721_a_(SCombatPacket.class, SCombatPacket::new).func_229721_a_(SPlayerListItemPacket.class, SPlayerListItemPacket::new).func_229721_a_(SPlayerLookPacket.class, SPlayerLookPacket::new).func_229721_a_(SPlayerPositionLookPacket.class, SPlayerPositionLookPacket::new).func_229721_a_(SRecipeBookPacket.class, SRecipeBookPacket::new).func_229721_a_(SDestroyEntitiesPacket.class, SDestroyEntitiesPacket::new).func_229721_a_(SRemoveEntityEffectPacket.class, SRemoveEntityEffectPacket::new).func_229721_a_(SSendResourcePackPacket.class, SSendResourcePackPacket::new).func_229721_a_(SRespawnPacket.class, SRespawnPacket::new).func_229721_a_(SEntityHeadLookPacket.class, SEntityHeadLookPacket::new).func_229721_a_(SMultiBlockChangePacket.class, SMultiBlockChangePacket::new).func_229721_a_(SSelectAdvancementsTabPacket.class, SSelectAdvancementsTabPacket::new).func_229721_a_(SWorldBorderPacket.class, SWorldBorderPacket::new).func_229721_a_(SCameraPacket.class, SCameraPacket::new).func_229721_a_(SHeldItemChangePacket.class, SHeldItemChangePacket::new).func_229721_a_(SUpdateChunkPositionPacket.class, SUpdateChunkPositionPacket::new).func_229721_a_(SUpdateViewDistancePacket.class, SUpdateViewDistancePacket::new).func_229721_a_(SWorldSpawnChangedPacket.class, SWorldSpawnChangedPacket::new).func_229721_a_(SDisplayObjectivePacket.class, SDisplayObjectivePacket::new).func_229721_a_(SEntityMetadataPacket.class, SEntityMetadataPacket::new).func_229721_a_(SMountEntityPacket.class, SMountEntityPacket::new).func_229721_a_(SEntityVelocityPacket.class, SEntityVelocityPacket::new).func_229721_a_(SEntityEquipmentPacket.class, SEntityEquipmentPacket::new).func_229721_a_(SSetExperiencePacket.class, SSetExperiencePacket::new).func_229721_a_(SUpdateHealthPacket.class, SUpdateHealthPacket::new).func_229721_a_(SScoreboardObjectivePacket.class, SScoreboardObjectivePacket::new).func_229721_a_(SSetPassengersPacket.class, SSetPassengersPacket::new).func_229721_a_(STeamsPacket.class, STeamsPacket::new).func_229721_a_(SUpdateScorePacket.class, SUpdateScorePacket::new).func_229721_a_(SUpdateTimePacket.class, SUpdateTimePacket::new).func_229721_a_(STitlePacket.class, STitlePacket::new).func_229721_a_(SSpawnMovingSoundEffectPacket.class, SSpawnMovingSoundEffectPacket::new).func_229721_a_(SPlaySoundEffectPacket.class, SPlaySoundEffectPacket::new).func_229721_a_(SStopSoundPacket.class, SStopSoundPacket::new).func_229721_a_(SPlayerListHeaderFooterPacket.class, SPlayerListHeaderFooterPacket::new).func_229721_a_(SQueryNBTResponsePacket.class, SQueryNBTResponsePacket::new).func_229721_a_(SCollectItemPacket.class, SCollectItemPacket::new).func_229721_a_(SEntityTeleportPacket.class, SEntityTeleportPacket::new).func_229721_a_(SAdvancementInfoPacket.class, SAdvancementInfoPacket::new).func_229721_a_(SEntityPropertiesPacket.class, SEntityPropertiesPacket::new).func_229721_a_(SPlayEntityEffectPacket.class, SPlayEntityEffectPacket::new).func_229721_a_(SUpdateRecipesPacket.class, SUpdateRecipesPacket::new).func_229721_a_(STagsListPacket.class, STagsListPacket::new)).func_229724_a_(PacketDirection.SERVERBOUND, (new ProtocolType.PacketList<IServerPlayNetHandler>()).func_229721_a_(CConfirmTeleportPacket.class, CConfirmTeleportPacket::new).func_229721_a_(CQueryTileEntityNBTPacket.class, CQueryTileEntityNBTPacket::new).func_229721_a_(CSetDifficultyPacket.class, CSetDifficultyPacket::new).func_229721_a_(CChatMessagePacket.class, CChatMessagePacket::new).func_229721_a_(CClientStatusPacket.class, CClientStatusPacket::new).func_229721_a_(CClientSettingsPacket.class, CClientSettingsPacket::new).func_229721_a_(CTabCompletePacket.class, CTabCompletePacket::new).func_229721_a_(CConfirmTransactionPacket.class, CConfirmTransactionPacket::new).func_229721_a_(CEnchantItemPacket.class, CEnchantItemPacket::new).func_229721_a_(CClickWindowPacket.class, CClickWindowPacket::new).func_229721_a_(CCloseWindowPacket.class, CCloseWindowPacket::new).func_229721_a_(CCustomPayloadPacket.class, CCustomPayloadPacket::new).func_229721_a_(CEditBookPacket.class, CEditBookPacket::new).func_229721_a_(CQueryEntityNBTPacket.class, CQueryEntityNBTPacket::new).func_229721_a_(CUseEntityPacket.class, CUseEntityPacket::new).func_229721_a_(CJigsawBlockGeneratePacket.class, CJigsawBlockGeneratePacket::new).func_229721_a_(CKeepAlivePacket.class, CKeepAlivePacket::new).func_229721_a_(CLockDifficultyPacket.class, CLockDifficultyPacket::new).func_229721_a_(CPlayerPacket.PositionPacket.class, CPlayerPacket.PositionPacket::new).func_229721_a_(CPlayerPacket.PositionRotationPacket.class, CPlayerPacket.PositionRotationPacket::new).func_229721_a_(CPlayerPacket.RotationPacket.class, CPlayerPacket.RotationPacket::new).func_229721_a_(CPlayerPacket.class, CPlayerPacket::new).func_229721_a_(CMoveVehiclePacket.class, CMoveVehiclePacket::new).func_229721_a_(CSteerBoatPacket.class, CSteerBoatPacket::new).func_229721_a_(CPickItemPacket.class, CPickItemPacket::new).func_229721_a_(CPlaceRecipePacket.class, CPlaceRecipePacket::new).func_229721_a_(CPlayerAbilitiesPacket.class, CPlayerAbilitiesPacket::new).func_229721_a_(CPlayerDiggingPacket.class, CPlayerDiggingPacket::new).func_229721_a_(CEntityActionPacket.class, CEntityActionPacket::new).func_229721_a_(CInputPacket.class, CInputPacket::new).func_229721_a_(CUpdateRecipeBookStatusPacket.class, CUpdateRecipeBookStatusPacket::new).func_229721_a_(CMarkRecipeSeenPacket.class, CMarkRecipeSeenPacket::new).func_229721_a_(CRenameItemPacket.class, CRenameItemPacket::new).func_229721_a_(CResourcePackStatusPacket.class, CResourcePackStatusPacket::new).func_229721_a_(CSeenAdvancementsPacket.class, CSeenAdvancementsPacket::new).func_229721_a_(CSelectTradePacket.class, CSelectTradePacket::new).func_229721_a_(CUpdateBeaconPacket.class, CUpdateBeaconPacket::new).func_229721_a_(CHeldItemChangePacket.class, CHeldItemChangePacket::new).func_229721_a_(CUpdateCommandBlockPacket.class, CUpdateCommandBlockPacket::new).func_229721_a_(CUpdateMinecartCommandBlockPacket.class, CUpdateMinecartCommandBlockPacket::new).func_229721_a_(CCreativeInventoryActionPacket.class, CCreativeInventoryActionPacket::new).func_229721_a_(CUpdateJigsawBlockPacket.class, CUpdateJigsawBlockPacket::new).func_229721_a_(CUpdateStructureBlockPacket.class, CUpdateStructureBlockPacket::new).func_229721_a_(CUpdateSignPacket.class, CUpdateSignPacket::new).func_229721_a_(CAnimateHandPacket.class, CAnimateHandPacket::new).func_229721_a_(CSpectatePacket.class, CSpectatePacket::new).func_229721_a_(CPlayerTryUseItemOnBlockPacket.class, CPlayerTryUseItemOnBlockPacket::new).func_229721_a_(CPlayerTryUseItemPacket.class, CPlayerTryUseItemPacket::new))),
    STATUS(1, func_229714_b_().func_229724_a_(PacketDirection.SERVERBOUND, (new ProtocolType.PacketList<IServerStatusNetHandler>()).func_229721_a_(CServerQueryPacket.class, CServerQueryPacket::new).func_229721_a_(CPingPacket.class, CPingPacket::new)).func_229724_a_(PacketDirection.CLIENTBOUND, (new ProtocolType.PacketList<IClientStatusNetHandler>()).func_229721_a_(SServerInfoPacket.class, SServerInfoPacket::new).func_229721_a_(SPongPacket.class, SPongPacket::new))),
    LOGIN(2, func_229714_b_().func_229724_a_(PacketDirection.CLIENTBOUND, (new ProtocolType.PacketList<IClientLoginNetHandler>()).func_229721_a_(SDisconnectLoginPacket.class, SDisconnectLoginPacket::new).func_229721_a_(SEncryptionRequestPacket.class, SEncryptionRequestPacket::new).func_229721_a_(SLoginSuccessPacket.class, SLoginSuccessPacket::new).func_229721_a_(SEnableCompressionPacket.class, SEnableCompressionPacket::new).func_229721_a_(SCustomPayloadLoginPacket.class, SCustomPayloadLoginPacket::new)).func_229724_a_(PacketDirection.SERVERBOUND, (new ProtocolType.PacketList<IServerLoginNetHandler>()).func_229721_a_(CLoginStartPacket.class, CLoginStartPacket::new).func_229721_a_(CEncryptionResponsePacket.class, CEncryptionResponsePacket::new).func_229721_a_(CCustomPayloadLoginPacket.class, CCustomPayloadLoginPacket::new)));

    private static final ProtocolType[] STATES_BY_ID = new ProtocolType[4];
    private static final Map < Class <? extends IPacket<? >> , ProtocolType > STATES_BY_CLASS = Maps.newHashMap();
    private final int id;
    private final Map < PacketDirection, ? extends ProtocolType.PacketList<? >> field_229711_h_;

    private static ProtocolType.PacketRegistry func_229714_b_()
    {
        return new ProtocolType.PacketRegistry();
    }

    private ProtocolType(int p_i226083_3_, ProtocolType.PacketRegistry p_i226083_4_)
    {
        this.id = p_i226083_3_;
        this.field_229711_h_ = p_i226083_4_.field_229722_a_;
    }

    @Nullable
    public Integer getPacketId(PacketDirection direction, IPacket<?> packetIn)
    {
        return this.field_229711_h_.get(direction).func_229720_a_(packetIn.getClass());
    }

    @Nullable
    public IPacket<?> getPacket(PacketDirection direction, int packetId)
    {
        return this.field_229711_h_.get(direction).func_229718_a_(packetId);
    }

    public int getId()
    {
        return this.id;
    }

    @Nullable
    public static ProtocolType getById(int stateId)
    {
        return stateId >= -1 && stateId <= 2 ? STATES_BY_ID[stateId - -1] : null;
    }

    public static ProtocolType getFromPacket(IPacket<?> packetIn)
    {
        return STATES_BY_CLASS.get(packetIn.getClass());
    }

    static {
        for (ProtocolType protocoltype : values())
        {
            int i = protocoltype.getId();

            if (i < -1 || i > 2)
            {
                throw new Error("Invalid protocol ID " + Integer.toString(i));
            }

            STATES_BY_ID[i - -1] = protocoltype;
            protocoltype.field_229711_h_.forEach((p_229713_1_, p_229713_2_) ->
            {
                p_229713_2_.func_229717_a_().forEach((p_229712_1_) -> {
                    if (STATES_BY_CLASS.containsKey(p_229712_1_) && STATES_BY_CLASS.get(p_229712_1_) != protocoltype)
                    {
                        throw new IllegalStateException("Packet " + p_229712_1_ + " is already assigned to protocol " + STATES_BY_CLASS.get(p_229712_1_) + " - can't reassign to " + protocoltype);
                    }
                    else {
                        STATES_BY_CLASS.put(p_229712_1_, protocoltype);
                    }
                });
            });
        }
    }

    static class PacketList<T extends INetHandler> {
        private final Object2IntMap < Class <? extends IPacket<T >>> field_229715_a_ = Util.make(new Object2IntOpenHashMap<>(), (p_229719_0_) -> {
            p_229719_0_.defaultReturnValue(-1);
        });
        private final List < Supplier <? extends IPacket<T >>> field_229716_b_ = Lists.newArrayList();

        private PacketList()
        {
        }

        public <P extends IPacket<T>> ProtocolType.PacketList<T> func_229721_a_(Class<P> p_229721_1_, Supplier<P> p_229721_2_)
        {
            int i = this.field_229716_b_.size();
            int j = this.field_229715_a_.put(p_229721_1_, i);

            if (j != -1)
            {
                String s = "Packet " + p_229721_1_ + " is already registered to ID " + j;
                LogManager.getLogger().fatal(s);
                throw new IllegalArgumentException(s);
            }
            else
            {
                this.field_229716_b_.add(p_229721_2_);
                return this;
            }
        }

        @Nullable
        public Integer func_229720_a_(Class<?> p_229720_1_)
        {
            int i = this.field_229715_a_.getInt(p_229720_1_);
            return i == -1 ? null : i;
        }

        @Nullable
        public IPacket<?> func_229718_a_(int p_229718_1_)
        {
            Supplier <? extends IPacket<T >> supplier = this.field_229716_b_.get(p_229718_1_);
            return supplier != null ? supplier.get() : null;
        }

        public Iterable < Class <? extends IPacket<? >>> func_229717_a_()
        {
            return Iterables.unmodifiableIterable(this.field_229715_a_.keySet());
        }
    }

    static class PacketRegistry {
        private final Map < PacketDirection, ProtocolType.PacketList<? >> field_229722_a_ = Maps.newEnumMap(PacketDirection.class);

        private PacketRegistry()
        {
        }

        public <T extends INetHandler> ProtocolType.PacketRegistry func_229724_a_(PacketDirection p_229724_1_, ProtocolType.PacketList<T> p_229724_2_)
        {
            this.field_229722_a_.put(p_229724_1_, p_229724_2_);
            return this;
        }
    }
}
