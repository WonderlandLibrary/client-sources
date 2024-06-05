package net.minecraft.network;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import java.util.Iterator;
import java.util.Map;
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
   private static final EnumConnectionState[] ENUM$VALUES = new EnumConnectionState[]{HANDSHAKING, PLAY, STATUS, LOGIN};
   private static final TIntObjectMap STATES_BY_ID = new TIntObjectHashMap();
   private final Map directionMaps;
   private static final EnumConnectionState[] $VALUES = new EnumConnectionState[]{HANDSHAKING, PLAY, STATUS, LOGIN};
   LOGIN("LOGIN", 3, 2, (Object)null) {
      private static final String __OBFID = "CL_00001249";

      {
         this.registerPacket(EnumPacketDirection.CLIENTBOUND, S00PacketDisconnect.class);
         this.registerPacket(EnumPacketDirection.CLIENTBOUND, S01PacketEncryptionRequest.class);
         this.registerPacket(EnumPacketDirection.CLIENTBOUND, S02PacketLoginSuccess.class);
         this.registerPacket(EnumPacketDirection.CLIENTBOUND, S03PacketEnableCompression.class);
         this.registerPacket(EnumPacketDirection.SERVERBOUND, C00PacketLoginStart.class);
         this.registerPacket(EnumPacketDirection.SERVERBOUND, C01PacketEncryptionResponse.class);
      }
   },
   STATUS("STATUS", 2, 1, (Object)null) {
      private static final String __OBFID = "CL_00001247";

      {
         this.registerPacket(EnumPacketDirection.SERVERBOUND, C00PacketServerQuery.class);
         this.registerPacket(EnumPacketDirection.CLIENTBOUND, S00PacketServerInfo.class);
         this.registerPacket(EnumPacketDirection.SERVERBOUND, C01PacketPing.class);
         this.registerPacket(EnumPacketDirection.CLIENTBOUND, S01PacketPong.class);
      }
   };

   private static final String __OBFID = "CL_00001245";
   HANDSHAKING("HANDSHAKING", 0, -1, (Object)null) {
      private static final String __OBFID = "CL_00001246";

      {
         this.registerPacket(EnumPacketDirection.SERVERBOUND, C00Handshake.class);
      }
   };

   private final int id;
   private static final Map STATES_BY_CLASS = Maps.newHashMap();
   PLAY("PLAY", 1, 0, (Object)null) {
      private static final String __OBFID = "CL_00001250";

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

   static {
      EnumConnectionState[] var0 = values();
      int var1 = var0.length;

      for(int var2 = 0; var2 < var1; ++var2) {
         EnumConnectionState var3 = var0[var2];
         STATES_BY_ID.put(var3.getId(), var3);
         Iterator var4 = var3.directionMaps.keySet().iterator();

         while(var4.hasNext()) {
            EnumPacketDirection var5 = (EnumPacketDirection)var4.next();

            Class var6;
            for(Iterator var7 = ((BiMap)var3.directionMaps.get(var5)).values().iterator(); var7.hasNext(); STATES_BY_CLASS.put(var6, var3)) {
               var6 = (Class)var7.next();
               if (STATES_BY_CLASS.containsKey(var6) && STATES_BY_CLASS.get(var6) != var3) {
                  throw new Error(String.valueOf((new StringBuilder("Packet ")).append(var6).append(" is already assigned to protocol ").append(STATES_BY_CLASS.get(var6)).append(" - can't reassign to ").append(var3)));
               }

               try {
                  var6.newInstance();
               } catch (Throwable var9) {
                  throw new Error(String.valueOf((new StringBuilder("Packet ")).append(var6).append(" fails instantiation checks! ").append(var6)));
               }
            }
         }
      }

   }

   public static EnumConnectionState getFromPacket(Packet var0) {
      return (EnumConnectionState)STATES_BY_CLASS.get(var0.getClass());
   }

   EnumConnectionState(String var3, int var4, int var5, Object var6, EnumConnectionState var7) {
      this(var3, var4, var5, var6);
   }

   private EnumConnectionState(String var3, int var4, int var5) {
      this.directionMaps = Maps.newEnumMap(EnumPacketDirection.class);
      this.id = var5;
   }

   public int getId() {
      return this.id;
   }

   public Packet getPacket(EnumPacketDirection var1, int var2) throws InstantiationException, IllegalAccessException {
      Class var3 = (Class)((BiMap)this.directionMaps.get(var1)).get(var2);
      return var3 == null ? null : (Packet)var3.newInstance();
   }

   protected EnumConnectionState registerPacket(EnumPacketDirection var1, Class var2) {
      Object var3 = (BiMap)this.directionMaps.get(var1);
      if (var3 == null) {
         var3 = HashBiMap.create();
         this.directionMaps.put(var1, var3);
      }

      if (((BiMap)var3).containsValue(var2)) {
         String var4 = String.valueOf((new StringBuilder()).append(var1).append(" packet ").append(var2).append(" is already known to ID ").append(((BiMap)var3).inverse().get(var2)));
         LogManager.getLogger().fatal(var4);
         throw new IllegalArgumentException(var4);
      } else {
         ((BiMap)var3).put(((BiMap)var3).size(), var2);
         return this;
      }
   }

   public Integer getPacketId(EnumPacketDirection var1, Packet var2) {
      return (Integer)((BiMap)this.directionMaps.get(var1)).inverse().get(var2.getClass());
   }

   private EnumConnectionState(String var3, int var4, int var5, Object var6) {
      this(var3, var4, var5);
   }

   public static EnumConnectionState getById(int var0) {
      return (EnumConnectionState)STATES_BY_ID.get(var0);
   }
}
